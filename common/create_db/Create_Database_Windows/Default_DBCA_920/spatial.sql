connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\ora920\assistants\dbca\logs\spatial.log
@C:\oracle\ora920\md\admin\mdinst.sql;
spool off
exit;
