connect SYS/change_on_install as SYSDBA
set echo on
spool /u01/app/oracle/product/9.0.1/assistants/dbca/logs/CreateDB.log
startup nomount pfile="/u01/app/oracle/admin/CR901/scripts/init.ora";
CREATE DATABASE CR901
MAXINSTANCES 1
MAXLOGHISTORY 1
MAXLOGFILES 5
MAXLOGMEMBERS 5
MAXDATAFILES 100
DATAFILE '/u01/app/oracle/oradata/CR901/system01.dbf' SIZE 325M REUSE AUTOEXTEND ON NEXT  10240K MAXSIZE UNLIMITED
UNDO TABLESPACE "UNDOTBS" DATAFILE '/u01/app/oracle/oradata/CR901/undotbs01.dbf' SIZE 200M REUSE AUTOEXTEND ON NEXT  5120K MAXSIZE UNLIMITED
CHARACTER SET WE8ISO8859P1
NATIONAL CHARACTER SET AL16UTF16
LOGFILE GROUP 1 ('/u01/app/oracle/oradata/CR901/redo01.log') SIZE 100M,
GROUP 2 ('/u01/app/oracle/oradata/CR901/redo02.log') SIZE 100M,
GROUP 3 ('/u01/app/oracle/oradata/CR901/redo03.log') SIZE 100M;
spool off
exit;
