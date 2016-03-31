prompt -- sesiones activas ----

compute sum of count(*) on report

break on report

select username,module,count(*)
from v$session
where status='ACTIVE'
group by username,module
/