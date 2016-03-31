#!/bin/sh
ORACLE_SID=ORA814
export ORACLE_SID


cd /u01/app/oracle/product/8.1.5/ctx/admin

/u01/app/oracle/product/8.1.5/bin/sqlplus << EOF
internal
spool /u01/app/oracle/product/8.1.5/install/spoolctx.log;
@/u01/app/oracle/product/8.1.5/ctx/admin/dr0csys ctxsys DRSYS DRSYS
connect ctxsys/ctxsys
@/u01/app/oracle/product/8.1.5/ctx/admin/dr0inst /u01/app/oracle/product/8.1.5/ctx/lib/libctxx8.so
@/u01/app/oracle/product/8.1.5/ctx/admin/defaults/drdefus.sql
spool off
exit

EOF
