set ORACLE_SID=ALEXDB
C:\oracle\RDBMS901\bin\oradim -new  -sid ALEXDB -startmode m  -pfile C:\oracle\RDBMS901\database\initALEXDB.ora
C:\oracle\RDBMS901\bin\oradim -edit  -sid ALEXDB -startmode a 
C:\oracle\RDBMS901\bin\sqlplus /nolog @C:\oracle\admin\ALEXDB\scripts\CreateDB.sql
C:\oracle\RDBMS901\bin\sqlplus /nolog @C:\oracle\admin\ALEXDB\scripts\CreateDBFiles.sql
C:\oracle\RDBMS901\bin\sqlplus /nolog @C:\oracle\admin\ALEXDB\scripts\CreateDBCatalog.sql
C:\oracle\RDBMS901\bin\sqlplus /nolog @C:\oracle\admin\ALEXDB\scripts\JServer.sql
C:\oracle\RDBMS901\bin\sqlplus /nolog @C:\oracle\admin\ALEXDB\scripts\ordinst.sql
C:\oracle\RDBMS901\bin\sqlplus /nolog @C:\oracle\admin\ALEXDB\scripts\interMedia.sql
C:\oracle\RDBMS901\bin\sqlplus /nolog @C:\oracle\admin\ALEXDB\scripts\context.sql
C:\oracle\RDBMS901\bin\sqlplus /nolog @C:\oracle\admin\ALEXDB\scripts\ordinst.sql
C:\oracle\RDBMS901\bin\sqlplus /nolog @C:\oracle\admin\ALEXDB\scripts\spatial.sql
C:\oracle\RDBMS901\bin\sqlplus /nolog @C:\oracle\admin\ALEXDB\scripts\ultraSearch.sql
C:\oracle\RDBMS901\bin\sqlplus /nolog @C:\oracle\admin\ALEXDB\scripts\cwmlite.sql
C:\oracle\RDBMS901\bin\sqlplus /nolog @C:\oracle\admin\ALEXDB\scripts\demoSchemas.sql
C:\oracle\RDBMS901\bin\sqlplus /nolog @C:\oracle\admin\ALEXDB\scripts\postDBCreation.sql
