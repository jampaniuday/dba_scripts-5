
connect SYSTEM/manager

define ORACLE_HOME = &1
define ORACLE_SID  = &2

set echo on

spool spatial.log

@%ORACLE_HOME%\md\admin\mdinst.sql;

spool off

exit;
