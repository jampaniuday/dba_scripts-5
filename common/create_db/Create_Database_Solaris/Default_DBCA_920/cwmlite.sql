set echo on
spool /u01/app/oracle/product/9.2.0/assistants/dbca/logs/cwmlite.log
connect SYS/change_on_install as SYSDBA
@/u01/app/oracle/product/9.2.0/olap/admin/olap.sql newdb.COMANAGE.NET;
connect SYS/change_on_install as SYSDBA
@/u01/app/oracle/product/9.2.0/cwmlite/admin/oneinstl.sql CWMLITE TEMP;
spool off
exit;
