-- |--------------------------------------------------------------------------------|
-- | DATABASE     : Oracle                                                          |
-- | FILE         : details_sid.sql                                                 |
-- | CLASS        : Database Administration                                         |
-- | Call Syntax  : @details_sid (sid)                                              |
-- | PURPOSE      : Brinda detalles de una sesion oracle, tal como el pid, serial#, |
-- |                program, username, machine, terminal, sqltext y longops         |
-- +--------------------------------------------------------------------------------+

SET TERMOUT OFF;
define parsid = -1
define pardegree = 0
define v_sid = -1
define v_serial = -1
COLUMN current_instance NEW_VALUE current_instance NOPRINT;
SELECT rpad(instance_name, 17) current_instance FROM v$instance;
COLUMN INSTANCE_NUMBER NEW_VALUE current_node NOPRINT;
SELECT INSTANCE_NUMBER from v$instance;
COLUMN qcsid NEW_VALUE parsid NOPRINT;
SELECT qcsid from v$session_longops where sid = UPPER('&1');
COLUMN degree NEW_VALUE pardegree NOPRINT;
SELECT degree FROM V$PX_SESSION where qcsid = &parsid and sid <> &parsid and rownum = 1;
SET TERMOUT ON;

PROMPT 
PROMPT +------------------------------------------------------------------------+
PROMPT | Report         : DETAILS SESSIONS, SQL_TEXT AND LONGOPS                |
PROMPT | Instance       : &current_instance                                     |
PROMPT +------------------------------------------------------------------------+

COLUMN v_sid NEW_VALUE v_sid NOPRINT;
COLUMN v_serial NEW_VALUE v_serial NOPRINT;

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

SELECT
  RPAD('USERNAME         : ' || NVL(s.username, '(oracle)'), 80) ||
  RPAD('OSUSER           : ' || s.osuser, 80) ||
  RPAD('PROGRAM          : ' || s.program, 80) ||
  RPAD('SPID             : ' || p.spid, 80) ||
  RPAD('SID              : ' || s.sid, 80) ||
  RPAD('SERIAL#          : ' || s.serial#, 80) ||
  RPAD('LOCK WAIT        : ' || s.lockwait, 80) ||
  RPAD('STATUS           : ' || s.status, 80) ||
  RPAD('TYPE             : ' || s.type, 80) ||
  RPAD('LOGON TIME       : ' || TO_CHAR(s.logon_Time,'DD-MON-YYYY HH24:MI:SS'), 80) ||
  RPAD('TIME CONECTED    : ' || DECODE(TRUNC(SYSDATE - s.LOGON_TIME), 0, NULL, TRUNC(SYSDATE - s.LOGON_TIME) || ' Days' || ' + ') || TO_CHAR(TO_DATE(TRUNC(MOD(SYSDATE-s.LOGON_TIME,1) * 86400), 'SSSSS'), 'HH24:MI:SS'), 80) ||
  RPAD('LAST CALL        : ' || trunc(s.last_call_et/3600) || ' HRS ' || trunc(mod(s.last_call_et,3600)/60) || ' MIN ', 80) ||
  --Descomentariar en oracle11
  --RPAD('PREV EXEC TIME   : ' || TO_CHAR(s.PREV_EXEC_START,'DD-MON-YYYY HH24:MI:SS'), 80) ||
  --RPAD('SECONDS IN WAIT  : ' || s.seconds_in_wait, 80) ||
  --RPAD('EVENT            : ' || s.event, 80) ||
  --RPAD('BLOCKING STATUS  : ' || s.blocking_session_status, 80) ||
  --RPAD('SQL_ID           : ' || s.sql_id, 80) ||
  RPAD('MACHINE          : ' || s.machine, 80) ||
  RPAD('MASTER PARALLEL  : ' || decode(&parsid,-1,'N/A',decode(&parsid,0,'WITHOUT PARALLEL',('SESSION #' || &parsid || ' WITH DEGREE ' || &pardegree || ''))), 80) ||
  RPAD('TERMINAL         : ' || NVL(s.terminal,'UNKNOWN'), 80) ||
  RPAD('EVENT WAIT       : ' || a.event, 80) ||
  RPAD('TIME IN WAIT     : ' || trunc(a.seconds_in_wait / 60) || ' MIN', 80) ||
  RPAD('KILL SESSION     : ' || 'alter system kill session '''||s.sid||','||s.serial#||''';', 80) ||
  RPAD('KILL PROCESS     : ' || 'kill -9 ' || p.spid , 80),s.sid v_sid, s.serial# v_serial
FROM v$session s
    ,v$process p
    ,v$session_wait a
WHERE s.paddr   = p.addr
AND   a.sid     = s.sid
AND   s.sid     = '&1';
select decode(&v_sid,-1,'SESSION NO ENCONTRADA EN LA INSTANCIA ...','') MSS FROM dual;

PROMPT
PROMPT +--------------------------------+
PROMPT | Details SQLTEXT                |
PROMPT +--------------------------------+

SET LINESIZE    100

/*
select  
sql_text
from gv$sqlarea
where address = (
	select sql_address
   from   v$session
   where  sid = &v_sid
   and  inst_id = &current_node );
*/   

SELECT a.sql_text
FROM   v$sqltext a,
       v$session b
WHERE  a.address = b.sql_address
AND    a.hash_value = b.sql_hash_value
AND    b.sid = &v_sid
ORDER BY a.piece;

PROMPT
PROMPT +--------------------------------+
PROMPT | Details LONGOPS                |
PROMPT | Details of Time in MIN:SEG     |
PROMPT +--------------------------------+


SET LINESIZE    300
SET HEADING     ON 
SET FEEDBACK    ON

--COLUMN SID FORMAT A4
COLUMN PROC FORMAT A4
COLUMN SID FORMAT 9999
COLUMN START_TIME FORMAT A20
COLUMN "% COMPLETE" FORMAT 999.00
COLUMN elapsed FORMAT A7
COLUMN remaining FORMAT A9
COLUMN MESSAGE FORMAT A85
--COLUMN oqcsid FORMAT 999999

SELECT 
       NVL(vp.server_name, 'MAST') PROC, 
       sl.SID,
       ROUND(sl.sofar/sl.totalwork*100, 2) "% COMPLETE",
       TO_CHAR(sl.START_TIME,'DD-MON-YYYY HH24:MI:SS') START_TIME,
       ROUND(sl.time_remaining/60) || ':' || MOD(sl.time_remaining,60) remaining,
       sl.MESSAGE,
       ROUND(sl.elapsed_seconds/60) || ':' || MOD(sl.elapsed_seconds,60) elapsed--, 
--       sl.qcsid oqcsid
FROM   v$session_longops sl left join V$PX_PROCESS vp on sl.sid = vp.sid
where sl.sid in (DECODE(&parsid, 0, &v_sid, sl.sid))
and sl.qcsid = &parsid
order by PROC;