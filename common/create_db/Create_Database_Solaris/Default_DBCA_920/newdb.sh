#!/bin/sh

mkdir /u01/app/oracle/admin/newdb/bdump
mkdir /u01/app/oracle/admin/newdb/cdump
mkdir /u01/app/oracle/admin/newdb/create
mkdir /u01/app/oracle/admin/newdb/pfile
mkdir /u01/app/oracle/admin/newdb/udump
mkdir /u01/app/oracle/oradata/newdb
mkdir /u01/app/oracle/oradata/newdb/archive
mkdir /u01/app/oracle/product/9.2.0/dbs
setenv ORACLE_SID newdb
echo Add this entry in the oratab: newdb:/u01/app/oracle/product/9.2.0:Y
/u01/app/oracle/product/9.2.0/bin/orapwd file=/u01/app/oracle/product/9.2.0/dbs/orapwnewdb password=change_on_install
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/newdb/scripts/CreateDB.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/newdb/scripts/CreateDBFiles.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/newdb/scripts/CreateDBCatalog.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/newdb/scripts/JServer.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/newdb/scripts/ordinst.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/newdb/scripts/interMedia.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/newdb/scripts/context.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/newdb/scripts/xdb_protocol.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/newdb/scripts/spatial.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/newdb/scripts/ultraSearch.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/newdb/scripts/odm.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/newdb/scripts/cwmlite.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/newdb/scripts/postDBCreation.sql
