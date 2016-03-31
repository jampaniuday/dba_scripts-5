REM +------------------------------------------------+
REM | CHANGES:                                       |
REM +------------------------------------------------+
SET ORACLE_SID=O920NT
SET ORACLE_BASE=C:\oracle
SET ORACLE_HOME=C:\oracle\ORA920
REM +------------------------------------------------+

REM +------------------------------------------------+
REM | STOP THE ORACLE SERVICE FROM RUNNING           |
REM +------------------------------------------------+
net stop OracleService%ORACLE_SID%
oradim -delete -sid %ORACLE_SID%
REM +------------------------------------------------+

