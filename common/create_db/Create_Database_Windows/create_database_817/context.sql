spool context.log;

connect internal/oracle

define ORACLE_HOME = &1
define ORACLE_SID  = &2

@%ORACLE_HOME%\ctx\admin\dr0csys ctxsys DRSYS DRSYS

connect ctxsys/ctxsys
@%ORACLE_HOME%\ctx\admin\dr0inst &ORACLE_HOME\bin\oractxx8.dll
@%ORACLE_HOME%\ctx\admin\defaults\drdefus.sql
spool off
exit
