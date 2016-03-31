connect SYS/change_on_install as SYSDBA
set echo on
spool /u01/app/oracle/product/9.0.1/assistants/dbca/logs/cwmlite.log
@/u01/app/oracle/product/9.0.1/cwmlite/admin/oneinstl.sql CWMLITE TEMP;
connect SYS/change_on_install as SYSDBA
@/u01/app/oracle/product/9.0.1/olap/admin/olap.sql CR901;
spool off
exit;
