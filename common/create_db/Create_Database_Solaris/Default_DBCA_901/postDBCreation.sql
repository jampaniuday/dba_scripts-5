connect SYS/change_on_install as SYSDBA
set echo on
spool /u01/app/oracle/product/9.0.1/assistants/dbca/logs/postDBCreation.log
create spfile='/u01/app/oracle/product/9.0.1/dbs/spfileCR901.ora' FROM pfile='/u01/app/oracle/admin/CR901/scripts/init.ora';
connect SYS/change_on_install as SYSDBA
set echo on
spool /u01/app/oracle/product/9.0.1/assistants/dbca/logs/postDBCreation.log
shutdown ;
startup ;
exit;
