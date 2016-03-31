connect SYS/change_on_install as SYSDBA
set echo on
spool /u01/app/oracle/product/9.0.1/assistants/dbca/logs/interMedia.log
@/u01/app/oracle/product/9.0.1/ord/im/admin/iminst.sql;
spool off
exit;
