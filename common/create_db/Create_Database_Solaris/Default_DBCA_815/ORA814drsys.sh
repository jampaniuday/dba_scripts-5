#!/bin/sh
ORACLE_SID=ORA814
export ORACLE_SID

/u01/app/oracle/product/8.1.5/bin/svrmgrl << EOF
spool /u01/app/oracle/product/8.1.5/install/ctxtbls.log;
connect sys/change_on_install
CREATE TABLESPACE DRSYS DATAFILE '/u10/oradata/ORA814/drsys01.dbf' SIZE 80M;
spool off
exit;

EOF
