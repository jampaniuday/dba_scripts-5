SET LINESIZE 2000
SET PAGESIZE 1000

COLUMN username FORMAT A20
COLUMN event FORMAT A30
COLUM logon_time format A20

SELECT NVL(s.username, '(oracle)') AS username,
       s.sid,
       s.serial#,
       sw.event,
       sw.wait_time,
       sw.seconds_in_wait,
       sw.state,
       TO_CHAR(s.logon_Time,'DD-MON-YYYY HH24:MI:SS') AS logon_time
FROM   v$session_wait sw,
       v$session s
WHERE  s.sid = sw.sid
and    s.status='ACTIVE'
ORDER BY sw.seconds_in_wait DESC;
