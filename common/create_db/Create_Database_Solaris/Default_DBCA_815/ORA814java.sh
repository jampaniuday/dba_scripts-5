#!/bin/sh
ORACLE_SID=ORA814
export ORACLE_SID

/u01/app/oracle/product/8.1.5/bin/svrmgrl << EOF
spool /u01/app/oracle/product/8.1.5/install/jvminst.log;
connect internal/
@/u01/app/oracle/product/8.1.5/javavm/install/initjvm.sql;
spool off
exit;

EOF
