select a.logon_time,a.sid, a.serial#,status,program,machine
from v$session a where a.saddr in (
select b.session_addr from v$sort_usage b)
/

