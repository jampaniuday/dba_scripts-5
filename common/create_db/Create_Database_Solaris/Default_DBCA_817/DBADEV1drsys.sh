#!/bin/sh
ORACLE_SID=DBADEV1
export ORACLE_SID

/u01/app/oracle/product/8.1.7/bin/svrmgrl << EOF
spool /u01/app/oracle/admin/DBADEV1/create/ctxtbls.log;
connect internal/oracle
CREATE TABLESPACE DRSYS DATAFILE '/u10/app/oradata/DBADEV1/oradata/DBADEV1/drsys01.dbf' SIZE 84M REUSE
 AUTOEXTEND ON NEXT 640K
MINIMUM EXTENT 64K
DEFAULT STORAGE ( INITIAL 64K NEXT 64K MINEXTENTS 1 MAXEXTENTS  UNLIMITED  PCTINCREASE 50);
spool off
exit;

EOF
