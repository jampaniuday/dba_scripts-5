connect SYS/change_on_install as SYSDBA
set echo on
spool /u01/app/oracle/product/9.0.1/assistants/dbca/logs/JServer.log
@/u01/app/oracle/product/9.0.1/javavm/install/initjvm.sql;
@/u01/app/oracle/product/9.0.1/xdk/admin/initxml.sql;
@/u01/app/oracle/product/9.0.1/xdk/admin/xmlja.sql;
@/u01/app/oracle/product/9.0.1/javavm/install/init_jis.sql /u01/app/oracle/product/9.0.1;
@/u01/app/oracle/product/9.0.1/javavm/install/jisaephc.sql /u01/app/oracle/product/9.0.1;
@/u01/app/oracle/product/9.0.1/javavm/install/jisja.sql /u01/app/oracle/product/9.0.1;
@/u01/app/oracle/product/9.0.1/javavm/install/jisdr.sql 2481 2482;
@/u01/app/oracle/product/9.0.1/jsp/install/initjsp.sql;
@/u01/app/oracle/product/9.0.1/jsp/install/jspja.sql;
@/u01/app/oracle/product/9.0.1/rdbms/admin/initjms.sql;
@/u01/app/oracle/product/9.0.1/rdbms/admin/initrapi.sql;
@/u01/app/oracle/product/9.0.1/rdbms/admin/initsoxx.sql;
@/u01/app/oracle/product/9.0.1/rdbms/admin/initapcx.sql;
@/u01/app/oracle/product/9.0.1/rdbms/admin/initcdc.sql;
@/u01/app/oracle/product/9.0.1/rdbms/admin/initqsma.sql;
@/u01/app/oracle/product/9.0.1/rdbms/admin/initsjty.sql;
@/u01/app/oracle/product/9.0.1/rdbms/admin/initaqhp.sql;
spool off
exit;
