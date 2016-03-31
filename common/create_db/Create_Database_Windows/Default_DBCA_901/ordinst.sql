connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\RDBMS901\assistants\dbca\logs\ordinst.log
@C:\oracle\RDBMS901\ord\admin\ordinst.sql;
spool off
exit;
