@echo off
REM +--------------------------------------------------------------------------+
REM |                          Jeffrey M. Hunter                               |
REM |                      jhunter@idevelopment.info                           |
REM |                         www.idevelopment.info                            |
REM |--------------------------------------------------------------------------|
REM |    Copyright (c) 1998-2011 Jeffrey M. Hunter. All rights reserved.       |
REM |--------------------------------------------------------------------------|
REM | FILE       : rman_backup_hot_full_9i.bat                                 |
REM | CLASS      : WINDOWS Shell Scripts                                       |
REM | PURPOSE    : Used to perform a physical backup of an Oracle database     |
REM |              using RMAN. This script uses the database control file as   |
REM |              the RMAN repository. A command script will be dynamically   |
REM |              written to a temporary directory and run through RMAN.      |
REM |                                                                          |
REM | PARAMETERS : DBA_USERNAME       Database username RMAN will use to login |
REM |                                 to the database. This user must have     |
REM |                                 the SYSDBA role.                         |
REM |              DBA_PASSWORD       Database password RMAN will use to login |
REM |                                 to the database.                         |
REM |              TNS_ALIAS          TNS connect string to the target         |
REM |                                 database.                                |
REM | USAGE      :                                                             |
REM |                                                                          |
REM | rman_backup_hot_full_9i.bat  "DBA_USERNAME"  "DBA_PASSWORD"  "TNS_ALIAS" |
REM |                                                                          |
REM | NOTE       : As with any code, ensure to test this script in a           |
REM |              development environment before attempting to run it in      |
REM |              production.                                                 |
REM +--------------------------------------------------------------------------+

REM +--------------------------------------------------------------------------+
REM | VALIDATE COMMAND-LINE PARAMETERS                                         |
REM +--------------------------------------------------------------------------+

if (%1)==() goto USAGE
if (%2)==() goto USAGE
if (%3)==() goto USAGE


REM +--------------------------------------------------------------------------+
REM | VALIDATE ENVIRONMENT VARIABLES                                           |
REM +--------------------------------------------------------------------------+

REM   set ORALOG=C:\Oracle\dba_scripts\custom\log
REM   set ORATMP=C:\Oracle\dba_scripts\custom\temp

if (%ORALOG%)==() goto ENV_VARIABLES
if (%ORATMP%)==() goto ENV_VARIABLES


REM +--------------------------------------------------------------------------+
REM | DECLARE ALL GLOBAL VARIABLES.                                            |
REM +--------------------------------------------------------------------------+

set FILENAME=rman_backup_hot_full_9i
set DB_USERNAME=%1%
set DB_PASSWORD=%2%
set TNS_ALIAS=%3%
set CMDFILE=%ORATMP%\%FILENAME%_%TNS_ALIAS%.rcv
set LOGFILE=%ORALOG%\%FILENAME%_%TNS_ALIAS%.log


REM +--------------------------------------------------------------------------+
REM | REMOVE OLD LOG AND RMAN COMMAND FILES.                                   |
REM +--------------------------------------------------------------------------+

del /q %CMDFILE%
del /q %LOGFILE%


REM +--------------------------------------------------------------------------+
REM | WRITE RMAN COMMAND SCRIPT.                                               |
REM +--------------------------------------------------------------------------+

echo backup database plus archivelog delete input; > %CMDFILE%
REM echo crosscheck backup of database; >> %CMDFILE%
REM echo crosscheck backup of controlfile; >> %CMDFILE%
REM echo crosscheck archivelog all; >> %CMDFILE%

echo delete noprompt force obsolete;>> %CMDFILE%
REM echo delete force noprompt expired backup of database; >> %CMDFILE%
REM echo delete force noprompt expired backup of controlfile; >> %CMDFILE%
REM echo delete force noprompt expired archivelog all; >> %CMDFILE%

echo exit; >> %CMDFILE%


REM +--------------------------------------------------------------------------+
REM | PERFORM RMAN BACKUP.                                                     |
REM +--------------------------------------------------------------------------+

rman target %DB_USERNAME%/%DB_PASSWORD%@%TNS_ALIAS% nocatalog cmdfile=%CMDFILE% msglog %LOGFILE% 


REM +--------------------------------------------------------------------------+
REM | SCAN THE RMAN LOGFILE FOR ERRORS.                                        |
REM +--------------------------------------------------------------------------+

findstr /i "error" %LOGFILE%
if errorlevel 0 if not errorlevel 1 echo WARNING %FILENAME% %TNS_ALIAS% %COMPUTERNAME% %DATE% %TIME% %LOGFILE%

echo ...
echo END OF FILE REPORT
echo Filename      : %FILENAME%
echo Database      : %TNS_ALIAS%
echo Hostname      : %COMPUTERNAME%
echo Date          : %DATE%
echo Time          : %TIME%
echo RMAN Log File : %LOGFILE%


REM +--------------------------------------------------------------------------+
REM | END THIS SCRIPT.                                                         |
REM +--------------------------------------------------------------------------+

goto END



REM +==========================================================================+
REM |                    ***   END OF SCRIPT   ***                             |
REM +==========================================================================+

REM +--------------------------------------------------------------------------+
REM | LABEL DECLARATION SECTION.                                               |
REM +--------------------------------------------------------------------------+

:USAGE
echo Usage:  rman_backup_hot_full_9i.bat  DBA_USERNAME  DBA_PASSWORD  TNS_ALIAS
echo           DBA_USERNAME   = Oracle DBA Username - (Requires SYSDBA Role)
echo           DBA_PASSWORD   = Oracle DBA Password
echo           TNS_ALIAS      = Connect String to connect to the database (ex. ORCL)
goto END

:ENV_VARIABLES
echo ERROR:  You must set the following environment variables before
echo         running this script:
echo             ORALOG       = Directory used to write logfile to
echo             ORATMP       = Directory used to write temporary files to
goto END

:END
@echo on
