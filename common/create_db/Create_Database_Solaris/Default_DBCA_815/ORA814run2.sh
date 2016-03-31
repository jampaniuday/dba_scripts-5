#!/bin/sh
ORACLE_SID=ORA814
export ORACLE_SID

/u01/app/oracle/product/8.1.5/bin/svrmgrl << EOF
spool /u01/app/oracle/product/8.1.5/install/crdb3.log
connect internal
@/u01/app/oracle/product/8.1.5/rdbms/admin/catproc.sql
@/u01/app/oracle/product/8.1.5/rdbms/admin/caths.sql
@/u01/app/oracle/product/8.1.5/rdbms/admin/otrcsvr.sql
connect system/manager
@/u01/app/oracle/product/8.1.5/sqlplus/admin/pupbld.sql

disconnect
spool off
exit


EOF
