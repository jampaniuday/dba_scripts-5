connect SYS/change_on_install as SYSDBA
set echo on
spool /u01/app/oracle/product/9.2.0/assistants/dbca/logs/ultraSearch.log
@/u01/app/oracle/product/9.2.0/ultrasearch/admin/wk0install.sql SYS change_on_install change_on_install DRSYS TEMP "" PORTAL false;
spool off
exit;
