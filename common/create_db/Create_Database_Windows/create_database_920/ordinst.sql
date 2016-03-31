
connect SYS/change_on_install as SYSDBA

define ORACLE_HOME = &1
define ORACLE_SID  = &2

set echo on

spool ordinst.log

@%ORACLE_HOME%\ord\admin\ordinst.sql;

spool off

exit;
