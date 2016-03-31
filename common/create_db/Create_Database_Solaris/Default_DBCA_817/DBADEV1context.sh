#!/bin/sh
ORACLE_SID=DBADEV1
export ORACLE_SID


cd /u01/app/oracle/product/8.1.7/ctx/admin

/u01/app/oracle/product/8.1.7/bin/sqlplus << EOF
internal
spool /u01/app/oracle/admin/DBADEV1/create/spoolctx.log;
@/u01/app/oracle/product/8.1.7/ctx/admin/dr0csys ctxsys DRSYS DRSYS
connect ctxsys/ctxsys
@/u01/app/oracle/product/8.1.7/ctx/admin/dr0inst /u01/app/oracle/product/8.1.7/ctx/lib/libctxx8.so
@/u01/app/oracle/product/8.1.7/ctx/admin/defaults/drdefus.sql
spool off
exit

EOF
