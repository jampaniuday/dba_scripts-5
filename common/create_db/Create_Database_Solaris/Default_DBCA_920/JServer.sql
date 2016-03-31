connect SYS/change_on_install as SYSDBA
set echo on
spool /u01/app/oracle/product/9.2.0/assistants/dbca/logs/JServer.log
@/u01/app/oracle/product/9.2.0/javavm/install/initjvm.sql;
@/u01/app/oracle/product/9.2.0/xdk/admin/initxml.sql;
@/u01/app/oracle/product/9.2.0/xdk/admin/xmlja.sql;
@/u01/app/oracle/product/9.2.0/rdbms/admin/catjava.sql;
spool off
exit;
