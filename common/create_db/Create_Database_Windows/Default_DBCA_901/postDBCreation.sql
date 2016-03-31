connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\RDBMS901\assistants\dbca\logs\postDBCreation.log
create spfile='C:\oracle\RDBMS901\database\spfileALEXDB.ora' FROM pfile='C:\oracle\admin\ALEXDB\scripts\init.ora';
connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\RDBMS901\assistants\dbca\logs\postDBCreation.log
shutdown ;
startup mount pfile="C:\oracle\RDBMS901\assistants\dbca\logs\pfile";
alter database archivelog;;
alter database open;;
alter system archive log start;;
exit;
