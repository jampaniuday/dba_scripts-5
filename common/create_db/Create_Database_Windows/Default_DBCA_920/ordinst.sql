connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\ora920\assistants\dbca\logs\ordinst.log
@C:\oracle\ora920\ord\admin\ordinst.sql;
spool off
exit;
