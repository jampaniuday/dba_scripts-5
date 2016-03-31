col SID_Serial format 999999
col SID_Serial format A20
col OUSER format A18 
col username format A20
col machine format A30 
col sid_serial format A50
-- col LOGON_TIME format A25
-- col osuser format A20 "OSUSER"
-- col osuser format A20 "OSUSER"
set linesize 9999
set  arraysize 999




alter session set nls_date_format='dd-mm-yyyy hh24:mi:ss';

select A.osuser OUSER , ' alter system kill session   '''||A.sid||','||A.serial#||''';  ' "SID_Serial", 'kill -9 '||B.spid||' '  SOID,
       A.username USERNAME,A.status STATUS,A.LOGON_TIME LOGON_TIME, A.machine MACHINE,A.program PROGRAM
from v$session A, V$process B
where paddr=addr
--and status='ACTIVE'
and STATUS='KILLED'
order by osuser
/



