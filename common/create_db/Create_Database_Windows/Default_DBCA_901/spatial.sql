connect SYSTEM/manager
set echo on
spool C:\oracle\RDBMS901\assistants\dbca\logs\spatial.log
@C:\oracle\RDBMS901\md\admin\mdinst.sql;
spool off
exit;
