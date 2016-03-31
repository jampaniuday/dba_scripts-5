connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\ora920\assistants\dbca\logs\ultraSearch.log
@C:\oracle\ora920\ultrasearch\admin\wk0install.sql SYS change_on_install change_on_install DRSYS TEMP "" PORTAL false;
spool off
exit;
