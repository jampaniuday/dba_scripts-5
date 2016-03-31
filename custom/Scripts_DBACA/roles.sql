set define on
select granted_role,grantee,default_role from dba_role_privs
where grantee = trim(upper('&usuario'))
order by 1;