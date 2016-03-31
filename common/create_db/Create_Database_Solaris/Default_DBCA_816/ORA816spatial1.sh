#!/bin/sh
ORACLE_SID=ORA816
export ORACLE_SID

/u01/app/oracle/product/8.1.6/bin/svrmgrl << EOF
spool /u01/app/oracle/admin/ORA816/create/mdinst.log;
connect internal/oracle
@/u01/app/oracle/product/8.1.6/md/admin/mdinst.sql
spool off
exit

EOF
