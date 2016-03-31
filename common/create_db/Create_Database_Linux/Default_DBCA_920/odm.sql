connect SYS/change_on_install as SYSDBA
set echo on
spool /u01/app/oracle/product/9.2.0/assistants/dbca/logs/odm.log
@/u01/app/oracle/product/9.2.0/dm/admin/dminst.sql ODM TEMP /u01/app/oracle/product/9.2.0/assistants/dbca/logs/;
connect SYS/change_on_install as SYSDBA
revoke AQ_ADMINISTRATOR_ROLE from ODM;
spool off
exit;
