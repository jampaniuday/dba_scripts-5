mkdir C:\oracle\admin\NEWDB\bdump
mkdir C:\oracle\admin\NEWDB\cdump
mkdir C:\oracle\admin\NEWDB\create
mkdir C:\oracle\admin\NEWDB\pfile
mkdir C:\oracle\admin\NEWDB\udump
mkdir C:\oracle\ora920\database
mkdir C:\oracle\oradata\NEWDB
mkdir C:\oracle\oradata\NEWDB\archive
set ORACLE_SID=NEWDB
C:\oracle\ora920\bin\oradim.exe -new  -sid NEWDB -startmode m 
C:\oracle\ora920\bin\oradim.exe -edit  -sid NEWDB -startmode a 
C:\oracle\ora920\bin\orapwd.exe file=C:\oracle\ora920\database\PWDNEWDB.ora password=change_on_install
C:\oracle\ora920\bin\sqlplus /nolog @C:\oracle\admin\NEWDB\scripts\CreateDB.sql
C:\oracle\ora920\bin\sqlplus /nolog @C:\oracle\admin\NEWDB\scripts\CreateDBFiles.sql
C:\oracle\ora920\bin\sqlplus /nolog @C:\oracle\admin\NEWDB\scripts\CreateDBCatalog.sql
C:\oracle\ora920\bin\sqlplus /nolog @C:\oracle\admin\NEWDB\scripts\JServer.sql
C:\oracle\ora920\bin\sqlplus /nolog @C:\oracle\admin\NEWDB\scripts\ordinst.sql
C:\oracle\ora920\bin\sqlplus /nolog @C:\oracle\admin\NEWDB\scripts\interMedia.sql
C:\oracle\ora920\bin\sqlplus /nolog @C:\oracle\admin\NEWDB\scripts\context.sql
C:\oracle\ora920\bin\sqlplus /nolog @C:\oracle\admin\NEWDB\scripts\xdb_protocol.sql
C:\oracle\ora920\bin\sqlplus /nolog @C:\oracle\admin\NEWDB\scripts\spatial.sql
C:\oracle\ora920\bin\sqlplus /nolog @C:\oracle\admin\NEWDB\scripts\ultraSearch.sql
C:\oracle\ora920\bin\sqlplus /nolog @C:\oracle\admin\NEWDB\scripts\odm.sql
C:\oracle\ora920\bin\sqlplus /nolog @C:\oracle\admin\NEWDB\scripts\cwmlite.sql
C:\oracle\ora920\bin\sqlplus /nolog @C:\oracle\admin\NEWDB\scripts\postDBCreation.sql
