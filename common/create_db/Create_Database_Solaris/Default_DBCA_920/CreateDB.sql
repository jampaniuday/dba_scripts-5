connect SYS/change_on_install as SYSDBA
set echo on
spool /u01/app/oracle/product/9.2.0/assistants/dbca/logs/CreateDB.log
startup nomount pfile="/u01/app/oracle/admin/newdb/scripts/init.ora";
CREATE DATABASE newdb
MAXINSTANCES 1
MAXLOGHISTORY 1
MAXLOGFILES 5
MAXLOGMEMBERS 3
MAXDATAFILES 100
DATAFILE '/u01/app/oracle/oradata/newdb/system01.dbf' SIZE 250M REUSE AUTOEXTEND ON NEXT  10240K MAXSIZE UNLIMITED
EXTENT MANAGEMENT LOCAL
DEFAULT TEMPORARY TABLESPACE TEMP TEMPFILE '/u01/app/oracle/oradata/newdb/temp01.dbf' SIZE 40M REUSE AUTOEXTEND ON NEXT  640K MAXSIZE UNLIMITED
UNDO TABLESPACE "UNDOTBS1" DATAFILE '/u01/app/oracle/oradata/newdb/undotbs01.dbf' SIZE 200M REUSE AUTOEXTEND ON NEXT  5120K MAXSIZE UNLIMITED
CHARACTER SET WE8ISO8859P1
NATIONAL CHARACTER SET AL16UTF16
LOGFILE GROUP 1 ('/u01/app/oracle/oradata/newdb/redo01.log') SIZE 102400K,
GROUP 2 ('/u01/app/oracle/oradata/newdb/redo02.log') SIZE 102400K,
GROUP 3 ('/u01/app/oracle/oradata/newdb/redo03.log') SIZE 102400K;
spool off
exit;
