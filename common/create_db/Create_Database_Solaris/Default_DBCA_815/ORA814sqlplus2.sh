#!/bin/sh
ORACLE_SID=ORA814
export ORACLE_SID


/u01/app/oracle/product/8.1.5/bin/sqlldr userid=system/manager control=/u01/app/oracle/product/8.1.5/sqlplus/admin/help/plushelp.ctl direct=true