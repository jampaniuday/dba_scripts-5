#!/bin/sh
ORACLE_SID=ORA816
export ORACLE_SID

/u01/app/oracle/product/8.1.6/bin/svrmgrl << EOF
spool /u01/app/oracle/admin/ORA816/create/crdb1.log
connect internal
startup nomount pfile = "/u01/app/oracle/product/8.1.6/dbs/initORA816.ora"
CREATE DATABASE "ORA816"
   maxdatafiles 254
   maxinstances 8
   maxlogfiles 32
   character set WE8ISO8859P1
   national character set WE8ISO8859P1
DATAFILE '/u10/app/oradata/ORA816/system01.dbf' SIZE 150M AUTOEXTEND OFF
logfile '/u10/app/oradata/ORA816/redo01.log' SIZE 500K, 
    '/u10/app/oradata/ORA816/redo02.log' SIZE 500K, 
    '/u10/app/oradata/ORA816/redo03.log' SIZE 500K;
disconnect
spool off
exit


EOF
