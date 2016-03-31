
connect SYS/change_on_install as SYSDBA

define ORACLE_HOME = &1
define ORACLE_SID  = &2

set echo on

spool cwmlite.log

@%ORACLE_HOME%\olap\admin\olap.sql &ORACLE_SID;

connect SYS/change_on_install as SYSDBA

@%ORACLE_HOME%\cwmlite\admin\oneinstl.sql CWMLITE TEMP;

spool off

exit;
