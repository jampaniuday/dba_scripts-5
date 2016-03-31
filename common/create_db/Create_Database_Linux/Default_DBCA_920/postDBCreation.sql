connect SYS/change_on_install as SYSDBA
set echo on
spool /u01/app/oracle/product/9.2.0/assistants/dbca/logs/postDBCreation.log
@/u01/app/oracle/product/9.2.0/rdbms/admin/utlrp.sql;
shutdown ;
startup mount pfile="/u01/app/oracle/admin/JEFFDB/scripts/init.ora";
alter database archivelog;
alter database open;
alter system archive log start;
shutdown ;
connect SYS/change_on_install as SYSDBA
set echo on
spool /u01/app/oracle/product/9.2.0/assistants/dbca/logs/postDBCreation.log
create spfile='/u01/app/oracle/product/9.2.0/dbs/spfileJEFFDB.ora' FROM pfile='/u01/app/oracle/admin/JEFFDB/scripts/init.ora';
startup ;
exit;
