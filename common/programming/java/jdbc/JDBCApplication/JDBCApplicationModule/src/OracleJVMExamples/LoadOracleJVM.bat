@echo off

REM +----------------------------------------------------------------------------+
REM | FILE  : LoadOracleJVM.bat                                                  |
REM | NOTES : Windows batch file to load Java objects to the Oracle JVM.         |
REM |         There are two methods for connecting to the database: oci8 and     |
REM |         thin. As an example, here are both methods:                        |
REM |                                                                            |
REM |  loadjava -user scott/tiger@jeffdb_melody.idevelopment.info -oci8 ...      |
REM |  loadjava -user scott/tiger@melody.idevelopment.info:1521:JEFFDB -thin ... |
REM -----------------------------------------------------------------------------+

set userName=%1
set userPassword=%2
set serviceName=%3

echo Running loadjava for Windows:
echo userName      = %1
echo userPassword  = %2
echo serviceName   = %3

loadjava -user %userName%/%userPassword%@%serviceName% -oci8 -resolve -verbose OracleConnection.java TestConnection.java
