#!/bin/sh
ORACLE_SID=DBADEV1
export ORACLE_SID

/u01/app/oracle/product/8.1.7/bin/svrmgrl << EOF
spool /u01/app/oracle/admin/DBADEV1/create/spoolrep.log;
connect internal/oracle
@/u01/app/oracle/product/8.1.7/rdbms/admin/catrep.sql
spool off
exit

EOF
