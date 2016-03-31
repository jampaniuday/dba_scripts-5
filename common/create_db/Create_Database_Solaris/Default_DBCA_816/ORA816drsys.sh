#!/bin/sh
ORACLE_SID=ORA816
export ORACLE_SID

/u01/app/oracle/product/8.1.6/bin/svrmgrl << EOF
spool /u01/app/oracle/admin/ORA816/create/ctxtbls.log;
connect internal/oracle
CREATE TABLESPACE DRSYS DATAFILE '/u10/app/oradata/ORA816/drsys01.dbf' SIZE 100M REUSE
 AUTOEXTEND OFF
MINIMUM EXTENT 64K
DEFAULT STORAGE ( INITIAL 64K NEXT 64K MINEXTENTS 1 MAXEXTENTS  UNLIMITED  PCTINCREASE 50);
spool off
exit;

EOF
