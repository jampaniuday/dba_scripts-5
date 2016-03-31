-- |--------------------------------------------------------------------------------|
-- | DATABASE     : Oracle                                                          |
-- | FILE         : details_pid.sql                                                 |
-- | CLASS        : Database Administration                                         |
-- | Call Syntax  : @details_pid (pid)									                     |
-- | PURPOSE      : Brinda detalles de un proceso oracle, tal como el sid, serial#, |
-- |                program, username, machine, terminal y sqltext y longops   		|
-- +--------------------------------------------------------------------------------+

SET TERMOUT OFF;
COLUMN current_instance NEW_VALUE current_instance NOPRINT;
SELECT rpad(instance_name, 17) current_instance FROM v$instance;
COLUMN INSTANCE_NUMBER NEW_VALUE current_node NOPRINT;
SELECT INSTANCE_NUMBER from v$instance;
SET TERMOUT ON;

PROMPT 
PROMPT +------------------------------------------------------------------------+
PROMPT | Report         : Details Sessions and SQL_TEXT                         |
PROMPT | Instance       : &current_instance                                     |
PROMPT +------------------------------------------------------------------------+

COLUMN v_sid NEW_VALUE v_sid NOPRINT;
COLUMN v_serial NEW_VALUE v_serial NOPRINT;

SET LINESIZE 	 80 
SET HEADING 	 OFF 
SET FEEDBACK 	 OFF
SET ECHO        OFF
SET TERMOUT     ON
SET TIMING      OFF
SET TRIMOUT     ON
SET TRIMSPOOL   ON
SET VERIFY      OFF

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
  --Descomentariar en oracle11
  --RPAD('PREV EXEC TIME   : ' || TO_CHAR(s.PREV_EXEC_START,'DD-MON-YYYY HH24:MI:SS'), 80) ||
  --RPAD('SECONDS IN WAIT  : ' || s.seconds_in_wait, 80) ||
  --RPAD('EVENT            : ' || s.event, 80) ||
 -- RPAD('BLOCKING STATUS  : ' || s.blocking_session_status, 80) ||
  RPAD('MACHINE          : ' || s.machine, 80) ||
  RPAD('TERMINAL         : ' || s.terminal, 80),
  s.sid v_sid, s.serial# v_serial
FROM v$session s
    ,v$process p
WHERE s.paddr          = p.addr
AND   p.pid           = '&1';

PROMPT
PROMPT +--------------------------------+
PROMPT | Details SQLTEXT                |
PROMPT +--------------------------------+

SET LINESIZE    100

select  
sql_text
from gv$sqlarea
where address = ( select sql_address
                  from   v$session
                  where  sid = &v_sid
                    and  inst_id = &current_node);

						  


PROMPT
PROMPT +--------------------------------+
PROMPT | Details LONGOPS                |
PROMPT +--------------------------------+

SET LINESIZE    999
SET HEADING     ON 
SET FEEDBACK    ON

COLUMN progress_pct FORMAT 99999999.00
COLUMN elapsed_min FORMAT A12
COLUMN remaining_min FORMAT A14

SELECT 
       ROUND(sl.elapsed_seconds/60) || ':' || MOD(sl.elapsed_seconds,60) elapsed_min,
       ROUND(sl.time_remaining/60) || ':' || MOD(sl.time_remaining,60) remaining_min,
       ROUND(sl.sofar/sl.totalwork*100, 2) progress_pct
FROM   v$session s,
       v$session_longops sl
WHERE  s.sid     = sl.sid
AND    s.serial# = sl.serial#
AND    s.sid = '&v_sid'
AND    s.serial# = '&v_serial';