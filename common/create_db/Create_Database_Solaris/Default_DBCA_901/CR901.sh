set ORACLE_SID=CR901
Add this entry in the oratab: CR901:/u01/app/oracle/product/9.0.1:Y
/u01/app/oracle/product/9.0.1/bin/sqlplus /nolog @/u01/app/oracle/admin/CR901/scripts/CreateDB.sql
/u01/app/oracle/product/9.0.1/bin/sqlplus /nolog @/u01/app/oracle/admin/CR901/scripts/CreateDBFiles.sql
/u01/app/oracle/product/9.0.1/bin/sqlplus /nolog @/u01/app/oracle/admin/CR901/scripts/CreateDBCatalog.sql
/u01/app/oracle/product/9.0.1/bin/sqlplus /nolog @/u01/app/oracle/admin/CR901/scripts/JServer.sql
/u01/app/oracle/product/9.0.1/bin/sqlplus /nolog @/u01/app/oracle/admin/CR901/scripts/ordinst.sql
/u01/app/oracle/product/9.0.1/bin/sqlplus /nolog @/u01/app/oracle/admin/CR901/scripts/interMedia.sql
/u01/app/oracle/product/9.0.1/bin/sqlplus /nolog @/u01/app/oracle/admin/CR901/scripts/context.sql
/u01/app/oracle/product/9.0.1/bin/sqlplus /nolog @/u01/app/oracle/admin/CR901/scripts/ordinst.sql
/u01/app/oracle/product/9.0.1/bin/sqlplus /nolog @/u01/app/oracle/admin/CR901/scripts/spatial.sql
/u01/app/oracle/product/9.0.1/bin/sqlplus /nolog @/u01/app/oracle/admin/CR901/scripts/ultraSearch.sql
/u01/app/oracle/product/9.0.1/bin/sqlplus /nolog @/u01/app/oracle/admin/CR901/scripts/cwmlite.sql
/u01/app/oracle/product/9.0.1/bin/sqlplus /nolog @/u01/app/oracle/admin/CR901/scripts/demoSchemas.sql
/u01/app/oracle/product/9.0.1/bin/sqlplus /nolog @/u01/app/oracle/admin/CR901/scripts/postDBCreation.sql
