
connect SYS/change_on_install as SYSDBA

define ORACLE_HOME = &1
define ORACLE_SID  = &2

set echo on

spool interMedia.log

@%ORACLE_HOME%\ord\im\admin\iminst.sql;

spool off

exit;
