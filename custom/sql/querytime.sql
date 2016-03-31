SET ECHO        OFF
SET FEEDBACK    6
SET HEADING     ON
SET LINESIZE    200
SET PAGESIZE    50000
SET TERMOUT     ON
SET TIMING      OFF
SET TRIMOUT     ON
SET TRIMSPOOL   ON
SET VERIFY      OFF

CLEAR COLUMNS
CLEAR BREAKS
CLEAR COMPUTES

COL CONECT_TIME format A18
COL LAST_ACT FORMAT A13
COL SESSION_KILL FORMAT A53
COL USERNAME FORMAT A16
COL OSUSER FORMAT A15
COL MACHINE FORMAT A22
COL SPID FORMAT A5
COL PROGRAM FORMAT A20
COL INST_ID FORMAT 9
--COL SID FORMAT 99999
--COL SERIAL# FORMAT 999999
--COL MODULE FORMAT A20

SET TERMOUT OFF;
COLUMN instance_number NEW_VALUE inst NOPRINT;
SELECT instance_number FROM v$instance;
SET TERMOUT ON;

accept time number prompt "Enter Time Logon (in Min)(60m) ==> " default 60
accept status char prompt "Session Status Active (S/N/ALL/Others)(ALL) ==> " default ALL
accept rac char prompt "Database is Rac (S/N)(N) ==> " default N
accept owner char prompt "Enter Username ==> "
accept mach char prompt "Enter Name Machine ==> "
accept type_kill char prompt "Select Type Killed Session (session/proc) ==> " default session

SELECT 
DECODE(TRUNC(SYSDATE - a.LOGON_TIME), 0, NULL, TRUNC(SYSDATE - a.LOGON_TIME) || 'D' || '+') || TO_CHAR(TO_DATE(TRUNC(MOD(SYSDATE-a.LOGON_TIME,1) * 86400), 'SSSSS'), 'HH24:MI:SS') CONECT_TIME,
DECODE(TRUNC(last_call_et/3600/24), 0, NULL, '(' || TRUNC(last_call_et/3600/24) || 'D) ') || floor(last_call_et/3600) || ':' || 
floor((last_call_et - floor(last_call_et/3600)*3600)/60) || ':' || 
round((last_call_et - floor(last_call_et/3600)*3600 - (floor((last_call_et - floor(last_call_et/3600)*3600)/60)*60) )) LAST_ACT,
--a.SID, 
--a.SERIAL#, 
--MODULE,
--a.INST_ID,
--b.SPID SPID,
a.USERNAME, a.STATUS,
--a.OSUSER, 
a.MACHINE, a.PROGRAM,
decode(UPPER('&type_kill'),'SESSION','alter system kill session '''||a.sid||','||a.serial#||decode('&&rac','N','','S',',@'||a.INST_ID)||''' immediate;','PROC','kill -9 ' || b.SPID) SESSION_KILL
FROM gv$session a, gv$process b
WHERE a.paddr = b.addr
AND a.inst_id = b.inst_id
AND a.inst_id = decode(upper('&&rac'),'N',&&inst,a.inst_id)
AND a.USERNAME = UPPER(decode('&&owner','',a.USERNAME,'&&owner'))
--AND a.MACHINE like decode(UPPER('&&mach'),'','%'||NVL (a.MACHINE, 'Unknown')||'%',UPPER('&&mach'))
AND a.status like decode(UPPER('&&status'),'',a.status,'S','ACTIVE','N','INACTIVE','ALL',a.status,UPPER('%&&status%'))
AND a.USERNAME IS NOT NULL AND type != 'BACKGROUND'
and a.USERNAME <> 'SYS'
and a.last_call_et > &&time*60
ORDER BY a.last_call_et ASC
/