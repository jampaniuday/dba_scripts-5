/* Formatted on 05/06/15 11:36:40 a.m. (QP5 v5.256.13226.35538) Moficado por Abner Aguilar <abner.aguilar@telefonica.com> - Fecha - OT - */
SET LINESIZE 200
SET PAGESIZE 1000

COLUMN username FORMAT A20
COLUMN event FORMAT A30

  SELECT NVL (s.username, '(oracle)') AS username,
         s.sid,
         s.serial#,
         sw.event,
         sw.wait_time,
         sw.seconds_in_wait,
         sw.state,
         TO_CHAR (s.logon_Time, 'DD-MON-YYYY HH24:MI:SS') AS logon_time
    FROM v$session_wait sw, v$session s
   WHERE s.sid = sw.sid --and    s.status='ACTIVE'
         AND s.sid = '&SID'
ORDER BY sw.seconds_in_wait DESC;