connect SYS/change_on_install as SYSDBA
set echo on
spool /u01/app/oracle/product/9.2.0/assistants/dbca/logs/xdb_protocol.log
@/u01/app/oracle/product/9.2.0/rdbms/admin/catqm.sql change_on_install XDB TEMP;
connect SYS/change_on_install as SYSDBA
@/u01/app/oracle/product/9.2.0/rdbms/admin/catxdbj.sql;
spool off
exit;
