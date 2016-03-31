connect SYSTEM/manager
set echo on
spool /u01/app/oracle/product/9.0.1/assistants/dbca/logs/spatial.log
@/u01/app/oracle/product/9.0.1/md/admin/mdinst.sql;
spool off
exit;
