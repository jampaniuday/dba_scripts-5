
connect SYS/change_on_install as SYSDBA

define ORACLE_HOME = &1
define ORACLE_SID  = &2

set echo on

spool CreateDBCatalog.log

@%ORACLE_HOME%\rdbms\admin\catalog.sql;
@%ORACLE_HOME%\rdbms\admin\catexp7.sql;
@%ORACLE_HOME%\rdbms\admin\catblock.sql;
@%ORACLE_HOME%\rdbms\admin\catproc.sql;
@%ORACLE_HOME%\rdbms\admin\catoctk.sql;
@%ORACLE_HOME%\rdbms\admin\owminst.plb;

connect SYSTEM/manager

@%ORACLE_HOME%\sqlplus\admin\pupbld.sql;

spool off

connect SYSTEM/manager
set echo on

spool sqlPlusHelp.log

@%ORACLE_HOME%\sqlplus\admin\help\hlpbld.sql helpus.sql;

spool off

exit;
