REM +------------------------------------------------+
REM | CHANGES:                                       |
REM +------------------------------------------------+
SET ORACLE_SID=TRUESRC
SET ORACLE_BASE=C:\oracle
SET ORACLE_HOME=C:\oracle\ora81
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
REM | INSTALL REPLICATION                            |
REM +------------------------------------------------+
REM %ORACLE_HOME%\bin\svrmgrl @replicate.sql
REM +-- END [ INSTALL JSERVER ] ---------------------+

REM +------------------------------------------------+
REM | INSTALL JSERVER                                |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\svrmgrl @java.sql
REM +-- END [ INSTALL JSERVER ] ---------------------+

REM +------------------------------------------------+
REM | INSTALL ORDINST                                |
REM | Creates the following users:                   |
REM |   - ORDSYS :    The user for Oracle cartridges.|
REM |                 This user ID is the standard   |
REM |                 Oracle database account with   |
REM |                 special privileges for data    |
REM |                 cartridges. Users will need to |
REM |                 decide on a password for the   |
REM |                 ORDSYS user as its default     |
REM |                 password is ORDSYS.            |
REM |  - ORDPLUGINS :                                |
REM |  - MDSYS      :                                |
REM |  - ORDSYS     :                                |
REM | This script also creates the roles for         |
REM | timeseries.                                    |
REM | This script also installs the UTL_RAW package. |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\svrmgrl @ordinst.sql
REM +-- END [ INSTALL ORDINST ] --------------------+

REM +------------------------------------------------+
REM | INSTALL interMedia                             |
REM | Make sure that ordinst.sql has been run before |
REM | attempting to run interMedia.                  |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\svrmgrl @iMedia.sql
REM +-- END [ INSTALL interMedia ] ------------------+

REM +------------------------------------------------+
REM | INSTALL CONTEXT                                |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\sqlplus /nolog @context.sql %ORACLE_HOME% %ORACLE_SID%
REM +-- END [ INSTALL CONTEXT ] ---------------------+

REM +------------------------------------------------+
REM | INSTALL SPATIAL                                |
REM +------------------------------------------------+
REM %ORACLE_HOME%\bin\sqlplus /nolog @spatial1.sql %ORACLE_HOME% %ORACLE_SID%
REM +-- END [ INSTALL SPATIAL ] ---------------------+

REM +------------------------------------------------+
REM | INSTALL SQLPLUS HELP FILES                     |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\sqlplus /nolog @sqlplus.sql %ORACLE_HOME% %ORACLE_SID%
REM +-- END [ INSTALL SPATIAL ] ---------------------+

REM +------------------------------------------------+
REM | INSTALL timeseries                             |
REM | Installed under the database user ORDSYS       |
REM | (Time Series Data Cartridge)                   |
REM +------------------------------------------------+
REM %ORACLE_HOME%\bin\svrmgrl @timeseries.sql
REM +-- END [ INSTALL interMedia ] ------------------+

REM +------------------------------------------------+
REM | INSTALL virage                                 |
REM | Installed under the database user ORDSYS       |
REM | Virage (VIR) Data Cartridge                    |
REM | (Visual Information Retrieval Cartridge)       |
REM | For more information about virage see:         |
REM |     http://www.virage.com                      |
REM +------------------------------------------------+
REM %ORACLE_HOME%\bin\svrmgrl @virage.sql
REM +-- END [ INSTALL interMedia ] ------------------+

REM +------------------------------------------------+
REM | INSTALL postDBCreation                         |
REM +------------------------------------------------+
%ORACLE_HOME%\bin\sqlplus /nolog @postDBCreation.sql %ORACLE_HOME% %ORACLE_SID%
REM +-- END [ INSTALL postDBCreation ] --------------+

REM +-- END [ MAIN SCRIPT - EXITING...] -------------+
