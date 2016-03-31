connect SYS/change_on_install as SYSDBA

define ORACLE_HOME = &1
define ORACLE_SID  = &2

set echo on

spool xdb_protocol.log

@%ORACLE_HOME%\rdbms\admin\catqm.sql change_on_install XDB TEMP;

connect SYS/change_on_install as SYSDBA

@%ORACLE_HOME%\rdbms\admin\catxdbj.sql;

spool off

exit;
