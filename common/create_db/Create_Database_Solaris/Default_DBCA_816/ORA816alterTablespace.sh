#!/bin/sh
ORACLE_SID=ORA816
export ORACLE_SID

/u01/app/oracle/product/8.1.6/bin/svrmgrl << EOF
connect internal/oracle
alter user system default tablespace TOOLS;
alter user system temporary tablespace TEMP;

EOF
