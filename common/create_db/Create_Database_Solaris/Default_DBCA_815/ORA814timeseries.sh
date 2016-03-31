#!/bin/sh
ORACLE_SID=ORA814
export ORACLE_SID

/u01/app/oracle/product/8.1.5/bin/svrmgrl << EOF
spool /u01/app/oracle/product/8.1.5/install/tsinst.log;
connect internal/
@/u01/app/oracle/product/8.1.5/ord/ts/admin/tsinst.sql;
spool off
exit;

EOF
