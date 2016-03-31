-- |--------------------------------------------------------------------------------|
-- | DATABASE     : Oracle                                                          |
-- | FILE         : details_session.sql                                             |
-- | CLASS        : Database Administration                                         |
-- | Call Syntax  : @details_session (sid)                                          |
-- | PURPOSE      : Brinda detalles de una sesion oracle, tal como el pid, serial#, |
-- |                program, username, machine, terminal, sqltext y longops         |
-- +--------------------------------------------------------------------------------+

SET LINESIZE    80 
SET HEADING     OFF 
SET FEEDBACK    OFF
SET ECHO        OFF
SET TERMOUT     ON
SET TIMING      OFF
SET TRIMOUT     ON
SET TRIMSPOOL   ON
SET VERIFY      OFF
SET PAGESIZE    10000

Define parsid = -1
Define pardegree = 0
Define v_sid = -1
Define v_serial = -1

SET TERMOUT OFF;
COL current_instance NEW_VALUE current_instance NOPRINT;
COL current_node NEW_VALUE current_node NOPRINT;
SELECT rpad(instance_name, 17) current_instance, INSTANCE_NUMBER current_node FROM v$instance;
SET TERMOUT ON;


PROMPT 
PROMPT +------------------------------------------------------------------------+
PROMPT | Report         : DETAILS SESSIONS, SQL_TEXT AND LONGOPS                |
PROMPT | Instance       : &current_instance                                     |
PROMPT +------------------------------------------------------------------------+
PROMPT +------------------------------------------------------------------------+
accept session_sid number prompt "| Enter number SID ==> "
PROMPT +------------------------------------------------------------------------+

SET TERMOUT OFF;
COL sid NEW_VALUE v_sid NOPRINT;
COL serial# NEW_VALUE v_serial NOPRINT;
COL sql_id NEW_VALUE v_sql_id NOPRINT;
COL QCSID NEW_VALUE parsid NOPRINT;
COL DEGREE NEW_VALUE pardegree NOPRINT;
SELECT qcsid, degree FROM V$PX_SESSION where sid=&session_sid;
SET TERMOUT ON;

PROMPT
SELECT
  RPAD('USERNAME         : ' || NVL(s.username, '(oracle)'), 80) ||
  RPAD('OSUSER           : ' || s.osuser, 80) ||
  RPAD('PROGRAM          : ' || s.program, 80) ||
  RPAD('SPID             : ' || p.spid, 80) ||
  RPAD('SID              : ' || s.sid, 80) ||
  RPAD('SERIAL#          : ' || s.serial#, 80) ||
  --RPAD('LOCK WAIT        : ' || s.lockwait, 80) ||
  RPAD('STATUS           : ' || s.status, 80) ||
  RPAD('TYPE             : ' || s.type, 80) ||
  --RPAD('SQL_ID           : ' || s.sql_id, 80) ||
  RPAD('LOGON TIME       : ' || TO_CHAR(s.logon_Time,'DD-MON-YYYY HH24:MI:SS'), 80) ||
  RPAD('TIME CONECTED    : ' || DECODE(TRUNC(SYSDATE - s.LOGON_TIME), 0, NULL, TRUNC(SYSDATE - s.LOGON_TIME) || ' Days' || ' + ') || TO_CHAR(TO_DATE(TRUNC(MOD(SYSDATE-s.LOGON_TIME,1) * 86400), 'SSSSS'), 'HH24:MI:SS'), 80) ||
  RPAD('LAST CALL        : ' || trunc(s.last_call_et/3600) || ' HRS ' || trunc(mod(s.last_call_et,3600)/60) || ' MIN ', 80) ||
  --Descomentariar en oracle11
  /*
  RPAD('PREV EXEC TIME   : ' || TO_CHAR(s.PREV_EXEC_START,'DD-MON-YYYY HH24:MI:SS'), 80) ||
  RPAD('SECONDS IN WAIT  : ' || s.seconds_in_wait, 80) ||
  RPAD('EVENT            : ' || s.event, 80) ||
  RPAD('BLOCKING STATUS  : ' || s.blocking_session_status, 80) ||
  */
  --Fin Comentario ...
  RPAD('MACHINE          : ' || s.machine, 80) ||
  RPAD('MASTER PARALLEL  : ' || decode(&parsid,-1,'N/A',0,'WITHOUT PARALLEL','SESSION #' || &parsid || ' WITH DEGREE ' || &pardegree || ''), 80) ||
  RPAD('TERMINAL         : ' || NVL(s.terminal,'UNKNOWN'), 80) ||
  RPAD('EVENT WAIT       : ' || a.event, 80) ||
  RPAD('TIME IN WAIT     : ' || trunc(a.seconds_in_wait / 60) || ' MIN', 80) ||
  RPAD('KILL SESSION     : ' || 'alter system kill session '''||s.sid||','||s.serial#||''' immediate;', 80) ||
  RPAD('KILL PROCESS     : ' || 'kill -9 ' || p.spid , 80),s.sid, s.serial#--, s.sql_id
FROM v$session s
    ,v$process p
    ,v$session_wait a
WHERE s.paddr   = p.addr
AND   a.sid     = s.sid
AND   s.sid     = &session_sid;
select decode(&v_sid,-1,'SESSION NO ENCONTRADA EN LA INSTANCIA ...','') MSS FROM dual;


PROMPT +------------------------------------------------------------------------+
PROMPT | Details SQLTEXT                                                        |
PROMPT +------------------------------------------------------------------------+

SELECT a.sql_text
FROM   v$sqltext a,
       v$session b
WHERE  a.address = b.sql_address
AND    a.hash_value = b.sql_hash_value
AND    b.sid = &v_sid
ORDER BY a.piece;

PROMPT
PROMPT +------------------------------------------------------------------------+
PROMPT | Details LONGOPS   (Time in MIN:SEG)                                    |
PROMPT +------------------------------------------------------------------------+

SET LINESIZE    300
SET HEADING     ON
SET FEEDBACK    ON

COL PROC FORMAT A4
COL INST FORMAT 9
COL SID FORMAT 9999
COL "% COMPLETE" FORMAT 999.99
COL START_TIME FORMAT A20
COL remaining FORMAT A9
COL MESSAGE FORMAT A85 JUSTIFY CENTER
COL elapsed FORMAT A7 JUSTIFY RIGHT

SELECT NVL(vp.server_name, 'MAST') PROC, sl.inst_id INST, sl.SID,
       ROUND (sl.sofar / sl.totalwork * 100, 2) "% COMPLETE",
       TO_CHAR (sl.START_TIME, 'DD-MON-YYYY HH24:MI:SS') START_TIME,
       ROUND (sl.time_remaining / 60) || ':' || MOD (sl.time_remaining, 60)
          remaining,
       sl.MESSAGE,
       ROUND (sl.elapsed_seconds / 60) || ':' || MOD (sl.elapsed_seconds, 60)
          elapsed
  FROM gv$session_longops sl left join GV$PX_PROCESS vp on sl.sid = vp.sid
 WHERE    sl.sid = (SELECT sid
                       FROM GV$PX_SESSION
                      WHERE qcsid = &parsid)
       OR sl.sid = &session_sid
order by PROC;

/*
PROMPT
PROMPT +------------------------------------------------------------------------+
PROMPT | EXPLAIN PLAN                                                           |
PROMPT +------------------------------------------------------------------------+

set verify off
set pages 9999
set lines 150
SET HEADING     OFF 
SET FEEDBACK    OFF
--col PLAN_TABLE_OUTPUT FORMAT 50
select * from table(dbms_xplan.display_cursor('&v_sql_id',null,''));
*/