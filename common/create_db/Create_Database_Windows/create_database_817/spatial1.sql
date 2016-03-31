spool spatial1.log;

connect internal/oracle

define ORACLE_HOME = &1
define ORACLE_SID  = &2

set echo on

@%ORACLE_HOME%\md\admin\mdinst.sql

spool off
exit
