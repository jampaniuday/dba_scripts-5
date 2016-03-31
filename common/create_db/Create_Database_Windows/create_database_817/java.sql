
spool jvminst.log;

connect internal/oracle

@%ORACLE_HOME%\javavm\install\initjvm.sql;

spool off

spool initxml.log;
@%ORACLE_HOME%\oracore\admin\initxml.sql
spool off

spool catxsu.log;
@%ORACLE_HOME%\rdbms\admin\catxsu.sql
spool off

spool init_jis.log;
@%ORACLE_HOME%\javavm\install\init_jis.sql
spool off

spool jisja.log;
@%ORACLE_HOME%\javavm\install\jisja.sql
spool off

spool jisaephc.log;
@%ORACLE_HOME%\javavm\install\jisaephc.sql
spool off

spool initplgs.log;
@%ORACLE_HOME%\rdbms\admin\initplgs.sql
spool off

spool initjsp.log;
@%ORACLE_HOME%\jsp\install\initjsp.sql
spool off

spool jspja.log;
@%ORACLE_HOME%\jsp\install\jspja.sql
spool off

spool initplsj.log;
@%ORACLE_HOME%\rdbms\admin\initplsj.sql
spool off

spool initjms.log;
@%ORACLE_HOME%\rdbms\admin\initjms.sql
spool off

spool initrepapi.log;
@%ORACLE_HOME%\rdbms\admin\initrepapi.sql
spool off

spool initsoxx.log;
@%ORACLE_HOME%\rdbms\admin\initsoxx.sql
spool off

exit;
