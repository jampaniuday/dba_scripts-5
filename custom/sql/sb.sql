/* ---------------------------------------------------------------------------
 Filename: check_bloking_session.prc
 CR/TR#  : 
 Purpose : Find blocking sessions and create release statements for RAC and non RAC environment  
 
 Date    : 08.07.2005.
 Author  : Damir Vadas, damir.vadas@gmail.com
  
 Remarks : Tested on 10g only (should work on 11g as well and lower versions)
 
 Changes (DD.MM.YYYY, Name, CR/TR#):
          11.12.2009, Damir Vadas
                      added p_to and p_cc as parameters to avoid bad header encodding on hac server
                      (no success!) 
          30.12.2009, Damir Vadas
                      added checking (LENGTH(s_mail_text)+LENGTH(p_text)+1 <= 4000)
                      because "ORA-06502: PL/SQL: numeric or value error: character string buffer too small"
          06.01.2010, Damir Vadas
                      added 32k size of message and exception log handler
          30.04.2010, Damir Vadas
                      fixed bug with showing last blocker not first one in kill statement
                      added STATUS column to show if session is killed (needed for previous fixed)
          01.09.2010, Damir Vadas
                      fixed bug blockers and waiters to be 11g compatible
--------------------------------------------------------------------------- */ 
set serveroutput on size 123456
set linesize 140
set pagesize 100
set trimout on
 
declare
  cursor c_blockers IS
    SELECT DISTINCT
       nvl(username,'BLOCKER ???') blocker_user, 
       gvl.sid, 
       gvs.serial# serial, 
       gvl.inst_id, 
       gvl.ctime ,
       gvs.status STATUS,      
       module, 
       action,
       decode(gvl.type, 'MR', 'Media_recovery',
                        'RT', 'Redo_thread',
                        'UN', 'User_name',
                        'TX', 'Transaction',
                        'TM', 'Dml',
                        'UL', 'PLSQL User_lock',
                        'DX', 'Distrted_Transaxion',
                        'CF', 'Control_file',
                        'IS', 'Instance_state',
                        'FS', 'File_set',
                        'IR', 'Instance_recovery',
                        'ST', 'Diskspace Transaction',
                        'IV', 'Libcache_invalidation',
                        'LS', 'LogStaartORswitch',
                        'RW', 'Row_wait',
                        'SQ', 'Sequence_no',
                        'TE', 'Extend_table',
                        'TT', 'Temp_table',
                              'Nothing-'
       ) lock_type
     FROM gv$lock gvl, 
          gv$session gvs
    WHERE gvl.request=0
      AND gvl.block=1
      AND gvl.sid=gvs.sid
      AND gvl.inst_id=gvs.inst_id
  ORDER BY CTIME desc
  ;  
   
  CURSOR c_waiters (p_blocker_waiter_sid gv$lock.sid%TYPE, p_blockers_waiter_inst_id gv$lock.inst_id%TYPE) IS
    SELECT
      nvl(username,'WAITER ???') waiter_user, 
      gvl.sid, 
      gvs.serial# serial, 
      gvl.inst_id, 
      gvl.ctime , 
      gvs.status STATUS,
      module,
      decode(gvl.request, 
                         0, 'None',
                         1, 'NoLock',
                         2, 'Row-Share',
                         3, 'Row-Exclusive',
                         4, 'Share-Table',
                         5, 'Share-Row-Exclusive',
                         6, 'Exclusive',
                            'Nothing-'
      ) lock_req,
      action
     FROM gv$lock gvl, 
          gv$session gvs
    WHERE gvl.request>0
      AND gvl.sid=gvs.sid
      AND gvl.inst_id=gvs.inst_id
      AND gvs.blocking_session=p_blocker_waiter_sid
      AND gvl.inst_id=p_blockers_waiter_inst_id
  ORDER BY gvl.CTIME desc
  ;
 
  bHasAny  boolean;
  i        NUMBER := 0;
  v_sid    NUMBER := 0;
  v_serial NUMBER := 0;
  v_first_sid    NUMBER := -1;
  v_first_serial NUMBER := -1;
  v_instance        BINARY_INTEGER := 0;
  v_first_instance  BINARY_INTEGER := 0;
  db_ver            VARCHAR2(128);
  db_ver2           VARCHAR2(128);  
  s_job_command     VARCHAR2 (4000);
  s_mail_text       VARCHAR2 (32000);
  s_subject         VARCHAR2 (256);
  s_smtp_out_server VARCHAR2 (256);
  s_host_name       v$instance.host_name%TYPE;
  s_db_name         v$database.name%TYPE;
  s_instance_name   v$instance.instance_name%TYPE;
  s                 VARCHAR2(1024);
   
  PROCEDURE add_message_line (p_text VARCHAR2) is
  BEGIN
    -- for proceduire version with mail part
    -- s_mail_text := s_mail_text || p_text || chr(10);     
    dbms_output.put_line (p_text);
  END;
   
begin
  dbms_utility.db_version(db_ver,db_ver2);
  add_message_line ('Oracle version: '||db_ver|| ' ('||db_ver2||')');
  bHasAny := FALSE;  
  FOR v_blockers in c_blockers loop
    bHasAny := TRUE;     
    IF (i=0) THEN                 
      add_message_line (rpad('Blocker',12,' ')||'  '||'  Inst  '||rpad('SID',6,' ')||'  '||rpad('Serial',8,' ')||'  '||'  '||rpad('[sec]',6,' ')||' '|| 
                        lpad('Lock Type',10,' ')||' '|| lpad('Status',14, ' ') || lpad('Module',13,' ')
                        );
      add_message_line (rpad('-',120,'-'));
    END IF;
    v_sid := v_blockers.sid;
    v_serial := v_blockers.serial;
    v_instance := v_blockers.inst_id ;
    -- only first blocker (who is not killed!) is important!
    IF v_blockers.status != 'KILLED' AND v_first_sid = -1 THEN
      v_first_sid := v_blockers.sid;
      v_first_serial := v_blockers.serial;
      v_first_instance := v_blockers.inst_id ;
    END IF;
    add_message_line (LPAD(to_char(i+1),3,' ')||'. '||rpad(v_blockers.blocker_user,12,' ')||'  '||rpad(v_blockers.inst_id,4,' ')||'  '||rpad(v_blockers.SID,8,' ' )||'  '||lpad(v_blockers.serial,5,' ')||'  '||lpad(v_blockers.CTIME,7,' ')||' '||
                      lpad(v_blockers.lock_type,13,' ')||lpad(v_blockers.status,14,' ')||lpad(nvl(v_blockers.module,'?'),15,' ')
                     );
    FOR v_waiters IN c_waiters (v_blockers.sid,v_blockers.inst_id ) LOOP 
      add_message_line (lpad(chr(9),9)||rpad(v_waiters.waiter_user,10,' ')||'  '||rpad(to_char(v_waiters.inst_id),4,' ')||rpad(to_char(v_waiters.sid),4,' ')|| ' '||lpad(to_char(v_waiters.serial),12,' ')||' ' ||' '|| 
                  ' ' ||lpad(to_char(v_waiters.ctime),6,' ')||' '|| lpad(to_char(v_waiters.lock_req),13,' ')||' '|| lpad(v_blockers.status,14,' ')|| ' '|| lpad(to_char(nvl(v_waiters.module,'?')),15,' ')
                  );      
    END LOOP;
    IF bHasAny THEN
      i:=i+1;
    END IF;
  END LOOP;
  -- show any kind of blocker (killed one also...as info)
  IF bHasAny THEN
    -- show blocking rows info
    -- add_message_line (chr(9));
    -- show_blocked_records (s_blocked_rows);
    -- dbms_output.put_line(s_blocked_rows);
    -- add_message_line (s_blocked_rows);  
    -- for blockers that are valid (not killed!) create kill statement
    IF ( v_first_sid > 0 ) THEN
      SELECT instance_name, host_name INTO s_instance_name, s_host_name FROM v$instance;
      SELECT name INTO s_db_name FROM v$database; 
      add_message_line (chr(9));
      add_message_line ('To kill first from the list, perform: ');
      add_message_line (chr(9));
      IF db_ver like '10%' THEN
        add_message_line ('NON RAC (or RAC logged on that node):');
        add_message_line ('---------------------------------');
        add_message_line ('ALTER SYSTEM DISCONNECT  SESSION '''||v_first_sid||','||v_first_serial||''' IMMEDIATE;');
        add_message_line ('ALTER SYSTEM KILL        SESSION '''||v_first_sid||','||v_first_serial||''' IMMEDIATE;');
        add_message_line (chr(9));
        add_message_line (chr(9));
        s := '''begin execute immediate ''''ALTER SYSTEM KILL SESSION '''''''''||v_first_sid||','||v_first_serial||''''''''' IMMEDIATE''''; end; ''';     
        add_message_line ('RAC (logged on any node) :');
        add_message_line ('--------------------------');
        s_job_command := 'declare'|| chr(10)||
                         '  v_job binary_integer;'|| chr(10)|| 
                         'begin'|| chr(10)||
                         '  DBMS_JOB.submit ('||
                         ' job     =>v_job'||chr(10)||
                         ',what    =>'||s||chr(10)||
                         ',instance=>'||v_first_instance||chr(10)||
                         '); '||chr(10)||
                         '  commit;'||chr(10)||
                         'end;'|| chr(10) ||
                         '/'
        ;
        add_message_line (s_job_command);
      ELSIF db_ver like '11%' THEN
        add_message_line (chr(9));
        add_message_line ('ALTER SYSTEM DISCONNECT  SESSION '''||v_first_sid||','||v_first_serial||',@'||v_first_instance||''' IMMEDIATE;');
        add_message_line ('ALTER SYSTEM KILL        SESSION '''||v_first_sid||','||v_first_serial||',@'||v_first_instance||''' IMMEDIATE;');    
      ELSE
        add_message_line (chr(9));
        add_message_line ('Untested version... Try to logon as priveledged user on node ('||s_host_name||') and perform:');
        add_message_line ('ALTER SYSTEM DISCONNECT  SESSION '''||v_first_sid||','||v_first_serial||''' IMMEDIATE;');
        add_message_line ('ALTER SYSTEM KILL        SESSION '''||v_first_sid||','||v_first_serial||''' IMMEDIATE;');    
      END IF; 
    ELSE
      add_message_line ('All valid blocker sessions allready killed. Try kill with OS level command!');
    END IF;
  ELSE
    add_message_line ('No blocking sessions found!');
  END IF;
EXCEPTION
  WHEN OTHERS THEN
    -- auto_log_error_core('"check_blocking_session" procedure. ');
    null;
end;
/