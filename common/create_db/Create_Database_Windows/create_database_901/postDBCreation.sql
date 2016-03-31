
connect SYS/change_on_install as SYSDBA

define ORACLE_HOME = &1
define ORACLE_SID  = &2

set echo on

spool postDBCreation1.log

create spfile='&ORACLE_HOME\database\spfile&ORACLE_SID..ora' FROM pfile='&ORACLE_HOME\database\init&ORACLE_SID..ora';

host move &ORACLE_HOME\database\init&ORACLE_SID..ora &ORACLE_HOME\database\init&ORACLE_SID..ora.BACKUP

spool off

connect SYS/change_on_install as SYSDBA

set echo on

spool postDBCreation2.log

shutdown;

startup mount;

alter database archivelog;
alter database open;
alter system archive log start;

spool off

exit;
