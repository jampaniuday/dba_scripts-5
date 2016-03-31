connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\ora920\assistants\dbca\logs\xdb_protocol.log
@C:\oracle\ora920\rdbms\admin\catqm.sql change_on_install XDB TEMP;
connect SYS/change_on_install as SYSDBA
@C:\oracle\ora920\rdbms\admin\catxdbj.sql;
spool off
exit;
