#!/bin/sh
ORACLE_SID=ORA816
export ORACLE_SID


/u01/app/oracle/product/8.1.6/bin/sqlplus << EOF
system/manager
@/u01/app/oracle/product/8.1.6/sqlplus/admin/help/helpbld.sql helpus.sql
