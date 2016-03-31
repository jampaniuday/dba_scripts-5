connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\ora920\assistants\dbca\logs\postDBCreation.log
@C:\oracle\ora920\rdbms\admin\utlrp.sql;
shutdown ;
startup mount pfile="C:\oracle\admin\NEWDB\scripts\init.ora";
alter database archivelog;
alter database open;
alter system archive log start;
shutdown ;
connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\ora920\assistants\dbca\logs\postDBCreation.log
create spfile='C:\oracle\ora920\database\spfileNEWDB.ora' FROM pfile='C:\oracle\admin\NEWDB\scripts\init.ora';
startup ;
exit;
