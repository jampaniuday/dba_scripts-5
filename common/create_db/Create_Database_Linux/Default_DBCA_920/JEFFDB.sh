#!/bin/sh

mkdir /u01/app/oracle/admin/JEFFDB/bdump
mkdir /u01/app/oracle/admin/JEFFDB/cdump
mkdir /u01/app/oracle/admin/JEFFDB/create
mkdir /u01/app/oracle/admin/JEFFDB/pfile
mkdir /u01/app/oracle/admin/JEFFDB/udump
mkdir /u01/app/oracle/oradata/JEFFDB
mkdir /u01/app/oracle/oradata/JEFFDB/archive
mkdir /u01/app/oracle/product/9.2.0/dbs
setenv ORACLE_SID JEFFDB
echo Add this entry in the oratab: JEFFDB:/u01/app/oracle/product/9.2.0:Y
/u01/app/oracle/product/9.2.0/bin/orapwd file=/u01/app/oracle/product/9.2.0/dbs/orapwJEFFDB password=change_on_install
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/JEFFDB/scripts/CreateDB.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/JEFFDB/scripts/CreateDBFiles.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/JEFFDB/scripts/CreateDBCatalog.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/JEFFDB/scripts/JServer.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/JEFFDB/scripts/ordinst.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/JEFFDB/scripts/interMedia.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/JEFFDB/scripts/context.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/JEFFDB/scripts/xdb_protocol.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/JEFFDB/scripts/spatial.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/JEFFDB/scripts/ultraSearch.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/JEFFDB/scripts/odm.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/JEFFDB/scripts/cwmlite.sql
/u01/app/oracle/product/9.2.0/bin/sqlplus /nolog @/u01/app/oracle/admin/JEFFDB/scripts/postDBCreation.sql
