connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\ora920\assistants\dbca\logs\odm.log
@C:\oracle\ora920\dm\admin\dminst.sql ODM TEMP C:\oracle\ora920\assistants\dbca\logs\;
connect SYS/change_on_install as SYSDBA
revoke AQ_ADMINISTRATOR_ROLE from ODM;
spool off
exit;
