connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\RDBMS901\assistants\dbca\logs\ultraSearch.log
@C:\oracle\RDBMS901\ultrasearch\admin\wk0install.sql SYS change_on_install change_on_install ALEXDB 1521 DRSYS TEMP;
spool off
exit;
