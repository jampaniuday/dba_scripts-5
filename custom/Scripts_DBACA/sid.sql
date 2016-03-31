col machine format a20
col KILL_SESSION format a43
col OSUSER format a11
col spid format a7

select spid,PID, status,' alter system kill session   '''||A.sid||','||A.serial#||''';' KILL_SESSION, osuser, machine,to_char(logon_time,'dd-mm-yyyy hh24:mi') logon_tim,MODULE A
from  v$session A,v$process B
where paddr=addr
and sid=&SID
/
