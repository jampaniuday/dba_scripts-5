alter session set nls_date_format='dd-mm-yyyy hh24:mi:ss';

select A.osuser OUSER , ' alter system kill session   '''||A.sid||','||A.serial#||''';  ' "SID_Serial", 'kill -9 '||B.spid||' '  SOID,A.username USERNAME,A.status STATUS,A.LOGON_TIME LOGON_TIME, A.machine MACHINE,A.program PROGRAM
from v$session A, V$process B
where paddr=addr
and status='ACTIVE'
and A.module=('&module')
--and status='KILLED'
order by ouser,username,machine
/