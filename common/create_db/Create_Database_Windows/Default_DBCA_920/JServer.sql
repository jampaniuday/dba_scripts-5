connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\ora920\assistants\dbca\logs\JServer.log
@C:\oracle\ora920\javavm\install\initjvm.sql;
@C:\oracle\ora920\xdk\admin\initxml.sql;
@C:\oracle\ora920\xdk\admin\xmlja.sql;
@C:\oracle\ora920\rdbms\admin\catjava.sql;
spool off
exit;
