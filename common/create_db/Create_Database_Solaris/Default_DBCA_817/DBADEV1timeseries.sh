#!/bin/sh
ORACLE_SID=DBADEV1
export ORACLE_SID

/u01/app/oracle/product/8.1.7/bin/svrmgrl << EOF
spool /u01/app/oracle/admin/DBADEV1/create/tsinst.log;
connect internal/oracle
@/u01/app/oracle/product/8.1.7/ord/ts/admin/tsinst.sql;
spool off
exit;

EOF
