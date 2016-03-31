compute sum of count(*) on report
break on report
select username,module,MACHINE,count(*)
from v$session
--where status='ACTIVE'
--group by username
group by username,module,MACHINE
/