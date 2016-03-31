#!/bin/sh
ORACLE_SID=DBADEV1
export ORACLE_SID

/u01/app/oracle/product/8.1.7/bin/svrmgrl << EOF
connect internal/oracle
alter user system default tablespace TOOLS;
alter user system temporary tablespace TEMP;

EOF
