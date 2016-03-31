REM +------------------------------------------------+
REM | CHANGES:                                       |
REM +------------------------------------------------+
SET ORACLE_SID=O901NT
SET ORACLE_BASE=C:\oracle
SET ORACLE_HOME=C:\oracle\RDBMS901
REM +-- END [ CHANGES ] -----------------------------+

REM +------------------------------------------------+
REM | CREATE DATABASE FILES                          |
REM +------------------------------------------------+
mkdir %ORACLE_BASE%\oradata
mkdir %ORACLE_BASE%\oradata\%ORACLE_SID%
mkdir %ORACLE_BASE%\oradata\%ORACLE_SID%\archive

REM +------------------------------------------------+
REM | CREATE EXTERNAL PASSWORD FILE                  |
REM +------------------------------------------------+
del %ORACLE_HOME%\database\PWD%ORACLE_SID%.ORA
%ORACLE_HOME%\bin\orapwd file=%ORACLE_HOME%\database\PWD%ORACLE_SID%.ORA password=change_on_install entries=50
REM +-- END [ External Password File ] --------------+

REM +------------------------------------------------+
REM | CREATE THE OracleService FOR THIS SID          |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\oradim -new  -sid %ORACLE_SID% -startmode m -pfile %ORACLE_BASE%\admin\%ORACLE_SID%\pfile\init%ORACLE_SID%.ora
%ORACLE_HOME%\bin\oradim -edit -sid %ORACLE_SID% -startmode a
REM +-- END [ CREATE OracleService ] ----------------+

REM +------------------------------------------------+
REM | CREATE THE DATABASE                            |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\sqlplus /nolog @create_database.sql %ORACLE_HOME% %ORACLE_SID%
REM +-- END [ CREATE DATABASE ] ---------------------+

REM +------------------------------------------------+
REM | CREATE DB FILES                                |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\sqlplus /nolog @CreateDBFiles.sql %ORACLE_HOME% %ORACLE_SID%
REM +-- END [ CREATE DB FILES ] ---------------------+

REM +------------------------------------------------+
REM | CREATE DB CATALOG                              |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\sqlplus /nolog @CreateDBCatalog.sql %ORACLE_HOME% %ORACLE_SID%
REM +-- END [ CREATE DB CATALOG ] -------------------+

REM +------------------------------------------------+
REM | INSTALL JSERVER                                |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\sqlplus /nolog @JServer.sql %ORACLE_HOME% %ORACLE_SID%
REM +-- END [ INSTALL JSERVER ] ---------------------+

REM +------------------------------------------------+
REM | INSTALL ORDINST                                |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\sqlplus /nolog @ordinst.sql %ORACLE_HOME% %ORACLE_SID%
REM +-- END [ INSTALL ORDINST ] --------------------+

REM +------------------------------------------------+
REM | INSTALL interMedia                             |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\sqlplus /nolog @interMedia.sql %ORACLE_HOME% %ORACLE_SID%
REM +-- END [ INSTALL interMedia ] ------------------+

REM +------------------------------------------------+
REM | INSTALL CONTEXT                                |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\sqlplus /nolog @context.sql %ORACLE_HOME% %ORACLE_SID%
REM +-- END [ INSTALL CONTEXT ] ---------------------+

REM +------------------------------------------------+
REM | INSTALL ORDINST                                |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\sqlplus /nolog @ordinst.sql %ORACLE_HOME% %ORACLE_SID%
REM +-- END [ INSTALL ORDINST ] --------------------+

REM +------------------------------------------------+
REM | INSTALL SPATIAL                                |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\sqlplus /nolog @spatial.sql %ORACLE_HOME% %ORACLE_SID%
REM +-- END [ INSTALL SPATIAL ] ---------------------+

REM +------------------------------------------------+
REM | INSTALL ultraSearch                            |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\sqlplus /nolog @ultraSearch.sql %ORACLE_HOME% %ORACLE_SID%
REM +-- END [ INSTALL ultraSearch ] -----------------+

REM +------------------------------------------------+
REM | INSTALL CWMLITE                                |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\sqlplus /nolog @cwmlite.sql %ORACLE_HOME% %ORACLE_SID%
REM +-- END [ INSTALL CWMLITE ] ---------------------+

REM +------------------------------------------------+
REM | INSTALL demoSchemas                            |
REM +------------------------------------------------+
REM %ORACLE_HOME%\bin\sqlplus /nolog @demoSchemas.sql %ORACLE_HOME% %ORACLE_SID%
REM +-- END [ INSTALL demoSchemas ] -----------------+

REM +------------------------------------------------+
REM | INSTALL postDBCreation                         |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\sqlplus /nolog @postDBCreation.sql %ORACLE_HOME% %ORACLE_SID%
REM +-- END [ INSTALL postDBCreation ] --------------+

REM +-- END [ MAIN SCRIPT - EXITING...] -------------+
