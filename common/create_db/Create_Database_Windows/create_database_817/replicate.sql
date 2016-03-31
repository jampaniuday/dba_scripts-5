spool replicate.log;

connect internal/oracle

@%ORACLE_HOME%\rdbms\admin\catrep.sql

spool off

exit
