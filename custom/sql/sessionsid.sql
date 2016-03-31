col SID_Serial format 999999
col SID_Serial format A20
col osuser format A10
col username format A15
col machine format A15
--col logon_time format DATE 'dd-mm-yyyy hh24:mi:ss'
-- col osuser format A20 "OSUSER"
-- col osuser format A20 "OSUSER"
set linesize 9999
set  arraysize 999
--select sid||','||serial# "SID_Serial",osuser OSUSER,username USERNAME,status STATUS , to_char(LOGON_TIME, 'Dy DD-Mon-YYYY
alter session set nls_date_format='dd-mm-yyyy hh24:mi:ss';
select 'ALTER SYSTEM KILL SESSION '||''''||SID||','||SERIAL#||''';' sesion,osuser OSUSER,username USERNAME,status STATUS , to_char(LOGON_TIME, 'Dy DD-Mon-YYYY HH24:MI:SS') LOGON_TIME,command COMMAND,machine MACHINE,program PROGRAM, module
from v$session
where sid in (&sid)
order by username, program, status;