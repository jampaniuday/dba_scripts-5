
connect SYS/change_on_install as SYSDBA

define ORACLE_HOME = &1
define ORACLE_SID  = &2

set echo on

spool postDBCreation1.log

REM +--------------------------+
REM | RUN:   utlrp.sql         |
REM +--------------------------+

connect SYS/change_on_install as SYSDBA

@%ORACLE_HOME%\rdbms\admin\utlrp.sql;

REM +--------------------------+
REM | SHUTDOWN DATABASE        |
REM +--------------------------+

connect SYS/change_on_install as SYSDBA

shutdown;

REM +-----------------------------------+
REM | MOUNT THE DATABASE &              |
REM | PUT DATABASE IN ARCHIVE LOG MODE  |
REM | SHUTDOWN THE DATABASE AGAIN       |
REM +-----------------------------------+

connect SYS/change_on_install as SYSDBA

startup mount;
alter database archivelog;
alter database open;

connect SYS/change_on_install as SYSDBA

shutdown;

spool off


REM +-----------------------------------------------------+
REM | =================================================== |
REM | =================================================== |
REM +-----------------------------------------------------+


spool postDBCreation2.log

REM +------------------------------+
REM | CREATE SERVER PARAMETER FILE |
REM +------------------------------+

connect SYS/change_on_install as SYSDBA

set echo on

create spfile='&ORACLE_HOME\database\spfile&ORACLE_SID..ora' FROM pfile='&ORACLE_HOME\database\init&ORACLE_SID..ora';

host move &ORACLE_HOME\database\init&ORACLE_SID..ora &ORACLE_HOME\database\init&ORACLE_SID..ora.BACKUP

startup open;

spool off

exit;
