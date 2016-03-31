col SID_Serial format 999999
col SID_Serial format A20
col osuser format A20 
col username format A20
col machine format A30 
-- col osuser format A20 "OSUSER"
-- col osuser format A20 "OSUSER"
set linesize 9999
set  arraysize 999

alter session set nls_date_format='dd-mm-yyyy hh24:mi:ss';

select sid||','||serial# "SID_Serial",osuser OSUSER,username USERNAME,status STATUS ,LOGON_TIME LOGON_TIME,command COMMAND,machine MACHINE,program PROGRAM
from v$session
where status='ACTIVE'
/



