#!/bin/sh
ORACLE_SID=ORA814
export ORACLE_SID

/u01/app/oracle/product/8.1.5/bin/svrmgrl << EOF
connect system/manager
@/u01/app/oracle/product/8.1.5/sqlplus/admin/help/helptbl.sql;
spool off
exit;

EOF
