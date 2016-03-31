#!/bin/sh
ORACLE_SID=DBADEV1
export ORACLE_SID

/u01/app/oracle/product/8.1.7/bin/svrmgrl << EOF
spool /u01/app/oracle/admin/DBADEV1/create/virinst.log;
connect internal/oracle
@/u01/app/oracle/product/8.1.7/ord/vir/admin/virinst.sql;
spool off
exit;

EOF
