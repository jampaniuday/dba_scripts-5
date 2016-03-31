#!/bin/sh
ORACLE_SID=DBADEV1
export ORACLE_SID

/u01/app/oracle/product/8.1.7/bin/svrmgrl << EOF
spool /u01/app/oracle/admin/DBADEV1/create/crdb1.log
connect internal
startup nomount pfile = "/u01/app/oracle/admin/DBADEV1/pfile/initDBADEV1.ora"
CREATE DATABASE "DBADEV1"
   maxdatafiles 254
   maxinstances 8
   maxlogfiles 32
   character set WE8ISO8859P1
   national character set WE8ISO8859P1
DATAFILE '/u10/app/oradata/DBADEV1/oradata/DBADEV1/system01.dbf' SIZE 260M AUTOEXTEND ON NEXT 10240K
logfile '/u10/app/oradata/DBADEV1/oradata/DBADEV1/redo01.log' SIZE 500K, 
    '/u10/app/oradata/DBADEV1/oradata/DBADEV1/redo02.log' SIZE 500K, 
    '/u10/app/oradata/DBADEV1/oradata/DBADEV1/redo03.log' SIZE 500K;
disconnect
spool off
exit


EOF
