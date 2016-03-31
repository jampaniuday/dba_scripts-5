
connect SYS/change_on_install as SYSDBA

define ORACLE_HOME = &1
define ORACLE_SID  = &2

set echo on

spool JServer.log

prompt +-- RUNNING => javavm\install\initjvm.sql ...
@%ORACLE_HOME%\javavm\install\initjvm.sql;

prompt +-- RUNNING => xdk\admin\initxml.sql ...
@%ORACLE_HOME%\xdk\admin\initxml.sql;

prompt +-- RUNNING => xdk\admin\xmlja.sql ...
@%ORACLE_HOME%\xdk\admin\xmlja.sql;

prompt +-- RUNNING => rdbms\admin\catjava.sql ...
@%ORACLE_HOME%\rdbms\admin\catjava.sql;


spool off

exit;
