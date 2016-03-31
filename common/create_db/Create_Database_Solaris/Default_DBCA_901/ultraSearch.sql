connect SYS/change_on_install as SYSDBA
set echo on
spool /u01/app/oracle/product/9.0.1/assistants/dbca/logs/ultraSearch.log
@/u01/app/oracle/product/9.0.1/ultrasearch/admin/wk0install.sql SYS change_on_install change_on_install CR901 1521 DRSYS TEMP;
spool off
exit;
