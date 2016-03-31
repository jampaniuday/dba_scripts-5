#!/bin/sh
ORACLE_SID=ORA814
export ORACLE_SID

/u01/app/oracle/product/8.1.5/bin/svrmgrl << EOF
spool /u01/app/oracle/product/8.1.5/install/crdb1.log
connect internal
startup nomount pfile = /u01/app/oracle/admin/ORA814/pfile/initORA814.ora
CREATE DATABASE "ORA814"
   maxdatafiles 254
   maxinstances 8
   maxlogfiles 32
   character set WE8ISO8859P1
   national character set WE8ISO8859P1
DATAFILE '/u10/oradata/ORA814/system01.dbf' SIZE 175M
logfile '/u10/oradata/ORA814/redo01.log' SIZE 500K, 
    '/u10/oradata/ORA814/redo02.log' SIZE 500K;
disconnect
spool off
exit


EOF
