set define on
select *
from dba_sys_privs
where grantee = trim(upper('&usuario'))
order by 1;