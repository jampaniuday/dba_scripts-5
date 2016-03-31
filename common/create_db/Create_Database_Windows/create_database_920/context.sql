
connect SYS/change_on_install as SYSDBA

define ORACLE_HOME = &1
define ORACLE_SID  = &2

set echo on

spool context.log

@%ORACLE_HOME%\ctx\admin\dr0csys change_on_install DRSYS TEMP;

connect CTXSYS/change_on_install

@%ORACLE_HOME%\ctx\admin\dr0inst &ORACLE_HOME\bin\oractxx9.dll;
@%ORACLE_HOME%\ctx\admin\defaults\dr0defin.sql AMERICAN;

spool off

exit;
