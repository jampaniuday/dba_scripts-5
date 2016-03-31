
connect internal/oracle

define ORACLE_HOME = &1
define ORACLE_SID  = &2

set echo on

spool postDBCreation.log

shutdown;

startup open;

REM startup mount;
REM alter database archivelog;
REM alter database open;
REM alter system archive log start;

spool off

exit;
