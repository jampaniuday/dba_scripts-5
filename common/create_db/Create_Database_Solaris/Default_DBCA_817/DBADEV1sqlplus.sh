#!/bin/sh
ORACLE_SID=DBADEV1
export ORACLE_SID


/u01/app/oracle/product/8.1.7/bin/sqlplus << EOF
system/manager
@/u01/app/oracle/product/8.1.7/sqlplus/admin/help/helpbld.sql helpus.sql
