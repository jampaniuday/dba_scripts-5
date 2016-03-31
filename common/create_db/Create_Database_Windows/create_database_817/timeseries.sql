spool timeseries.log;

connect internal/oracle

@%ORACLE_HOME%\ord\ts\admin\tsinst.sql;

spool off

exit;
