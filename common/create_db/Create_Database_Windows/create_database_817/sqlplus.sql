spool sqlPlusHelp.log

connect SYSTEM/manager

define ORACLE_HOME = &1
define ORACLE_SID  = &2

set echo on

@%ORACLE_HOME%\sqlplus\admin\help\helpbld.sql helpus.sql

spool off

