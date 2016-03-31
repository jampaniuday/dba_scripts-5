#!/bin/sh
ORACLE_SID=ORA816
export ORACLE_SID

/u01/app/oracle/product/8.1.6/bin/svrmgrl << EOF
spool /u01/app/oracle/admin/ORA816/create/crdb3.log
connect internal
@/u01/app/oracle/product/8.1.6/rdbms/admin/catproc.sql
@/u01/app/oracle/product/8.1.6/rdbms/admin/caths.sql
@/u01/app/oracle/product/8.1.6/rdbms/admin/otrcsvr.sql
connect system/manager
@/u01/app/oracle/product/8.1.6/sqlplus/admin/pupbld.sql

disconnect
spool off
exit


EOF
