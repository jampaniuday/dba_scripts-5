connect SYS/change_on_install as SYSDBA
set echo on
spool /u01/app/oracle/product/9.2.0/assistants/dbca/logs/ordinst.log
@/u01/app/oracle/product/9.2.0/ord/admin/ordinst.sql;
spool off
exit;
