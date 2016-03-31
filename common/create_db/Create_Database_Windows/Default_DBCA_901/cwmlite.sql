connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\RDBMS901\assistants\dbca\logs\cwmlite.log
@C:\oracle\RDBMS901\cwmlite\admin\oneinstl.sql CWMLITE TEMP;
connect SYS/change_on_install as SYSDBA
@C:\oracle\RDBMS901\olap\admin\olap.sql ALEXDB;
spool off
exit;
