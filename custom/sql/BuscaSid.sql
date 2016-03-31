set linesize 1000
set define on
col sesion format a50
select to_char(b.logon_time,'dd-mm-yyyy hh24:mi') logon,
'kill -9 '||a.spid    ser_pid,
b.process cli_pid,
a.pid     sys_pid,
'alter system kill session '''||b.sid||','||b.serial#||''';' sesion,
b.status,
b.username,
b.osuser,
b.program,
b.module,
b.MACHINE
from v$process a,v$session b
where a.addr = b.paddr
and b.sid in ( &id )
/