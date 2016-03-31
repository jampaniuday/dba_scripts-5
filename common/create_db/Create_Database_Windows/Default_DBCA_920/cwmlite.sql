set echo on
spool C:\oracle\ora920\assistants\dbca\logs\cwmlite.log
connect SYS/change_on_install as SYSDBA
@C:\oracle\ora920\olap\admin\olap.sql NEWDB.COMANAGE.NET;
connect SYS/change_on_install as SYSDBA
@C:\oracle\ora920\cwmlite\admin\oneinstl.sql CWMLITE TEMP;
spool off
exit;
