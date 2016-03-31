connect SYS/change_on_install as SYSDBA
set echo on
spool /u01/app/oracle/product/9.0.1/assistants/dbca/logs/CreateDBCatalog.log
@/u01/app/oracle/product/9.0.1/rdbms/admin/catalog.sql;
@/u01/app/oracle/product/9.0.1/rdbms/admin/catexp7.sql;
@/u01/app/oracle/product/9.0.1/rdbms/admin/catblock.sql;
@/u01/app/oracle/product/9.0.1/rdbms/admin/catproc.sql;
@/u01/app/oracle/product/9.0.1/rdbms/admin/catoctk.sql;
@/u01/app/oracle/product/9.0.1/rdbms/admin/catobtk.sql;
@/u01/app/oracle/product/9.0.1/rdbms/admin/caths.sql;
@/u01/app/oracle/product/9.0.1/rdbms/admin/owminst.plb;
connect SYSTEM/manager
@/u01/app/oracle/product/9.0.1/sqlplus/admin/pupbld.sql;
connect SYSTEM/manager
set echo on
spool /u01/app/oracle/product/9.0.1/assistants/dbca/logs/sqlPlusHelp.log
@/u01/app/oracle/product/9.0.1/sqlplus/admin/help/hlpbld.sql helpus.sql;
spool off
spool off
exit;
