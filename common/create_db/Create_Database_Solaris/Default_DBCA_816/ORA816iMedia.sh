#!/bin/sh
ORACLE_SID=ORA816
export ORACLE_SID

/u01/app/oracle/product/8.1.6/bin/svrmgrl << EOF
spool /u01/app/oracle/admin/ORA816/create/iMediainst.log;
connect internal/oracle
@/u01/app/oracle/product/8.1.6/ord/im/admin/iminst.sql;
spool off
exit;

EOF
