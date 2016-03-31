#!/bin/sh
ORACLE_SID=DBADEV1
export ORACLE_SID

/u01/app/oracle/product/8.1.7/bin/svrmgrl << EOF
spool /u01/app/oracle/admin/DBADEV1/create/crdb3.log
connect internal
@/u01/app/oracle/product/8.1.7/rdbms/admin/catproc.sql
@/u01/app/oracle/product/8.1.7/rdbms/admin/caths.sql
@/u01/app/oracle/product/8.1.7/rdbms/admin/otrcsvr.sql
connect system/manager
@/u01/app/oracle/product/8.1.7/sqlplus/admin/pupbld.sql

disconnect
spool off
exit


EOF
