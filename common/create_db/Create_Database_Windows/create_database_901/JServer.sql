
connect SYS/change_on_install as SYSDBA

define ORACLE_HOME = &1
define ORACLE_SID  = &2

set echo on

spool JServer.log

prompt +-- RUNNING => javavm\install\initjvm.sql ...
@%ORACLE_HOME%\javavm\install\initjvm.sql;

prompt +-- RUNNING => javavm\install\initjvm.sql ...
@%ORACLE_HOME%\xdk\admin\initxml.sql;

prompt +-- RUNNING => xdk\admin\xmlja.sql ...
@%ORACLE_HOME%\xdk\admin\xmlja.sql;

prompt +-- RUNNING => javavm\install\init_jis.sql (WITH) &ORACLE_HOME ...
@%ORACLE_HOME%\javavm\install\init_jis.sql &ORACLE_HOME;

prompt +-- RUNNING => javavm\install\jisaephc.sql (WITH) &ORACLE_HOME...
@%ORACLE_HOME%\javavm\install\jisaephc.sql &ORACLE_HOME;

prompt +-- RUNNING => javavm\install\jisja.sql (WITH) &ORACLE_HOME...
@%ORACLE_HOME%\javavm\install\jisja.sql &ORACLE_HOME;

prompt +-- RUNNING => javavm\install\jisja.sql (WITH) 2481 2482 ...
@%ORACLE_HOME%\javavm\install\jisdr.sql 2481 2482;

prompt +-- RUNNING => jsp\install\initjsp.sql ...
@%ORACLE_HOME%\jsp\install\initjsp.sql;

prompt +-- RUNNING => jsp\install\jspja.sql ...
@%ORACLE_HOME%\jsp\install\jspja.sql;

prompt +-- RUNNING => rdbms\admin\initjms.sql ...
@%ORACLE_HOME%\rdbms\admin\initjms.sql;

prompt +-- RUNNING => rdbms\admin\initrapi.sql ...
@%ORACLE_HOME%\rdbms\admin\initrapi.sql;

prompt +-- RUNNING => rdbms\admin\initsoxx.sql ...
@%ORACLE_HOME%\rdbms\admin\initsoxx.sql;

prompt +-- RUNNING => rdbms\admin\initapcx.sql ...
@%ORACLE_HOME%\rdbms\admin\initapcx.sql;

prompt +-- RUNNING => rdbms\admin\initcdc.sql ...
@%ORACLE_HOME%\rdbms\admin\initcdc.sql;

prompt +-- RUNNING => rdbms\admin\initqsma.sql ...
@%ORACLE_HOME%\rdbms\admin\initqsma.sql;

prompt +-- RUNNING => rdbms\admin\initsjty.sql ...
@%ORACLE_HOME%\rdbms\admin\initsjty.sql;

prompt +-- RUNNING => rdbms\admin\initaqhp.sql ...
@%ORACLE_HOME%\rdbms\admin\initaqhp.sql;

prompt +-- RUNNING => com\java\loadlib.sql ...
@%ORACLE_HOME%\com\java\loadlib.sql;

spool off

exit;
