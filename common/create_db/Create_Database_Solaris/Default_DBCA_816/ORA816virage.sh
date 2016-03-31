#!/bin/sh
ORACLE_SID=ORA816
export ORACLE_SID

/u01/app/oracle/product/8.1.6/bin/svrmgrl << EOF
spool /u01/app/oracle/admin/ORA816/create/virinst.log;
connect internal/oracle
@/u01/app/oracle/product/8.1.6/ord/vir/admin/virinst.sql;
spool off
exit;

EOF
