# +-------------------------------------------------------------------+
# | FILE          : create_ORA806_datebase.sql                        |
# | CREATION DATE : 20-JUL-1998                                       |
# |                                                                   |
# |  --------                                                         |
# | |HISTORY |                                                        |
# |  ---------------------------------------------------------------- |
# | NAME DATE      DESCRIPTION                                        |
# | ---- --------- -------------------------------------------------- |
# | JMH  20-JUL-98 Created original file.                             |
# +-------------------------------------------------------------------+

set echo on
set time on

--
-- +-------------------------------------+
-- | Capture creation in this spool file |
-- +-------------------------------------+
--

spool  create_ORA806_database.log

!rm $ORACLE_HOME/dbs/orapwORA806
!$ORACLE_HOME/bin/orapwd file=$ORACLE_HOME/dbs/orapwORA806 password=change_on_install entries=50

--
-- +-----------------------------+
-- | Create the initial database |
-- +-----------------------------+
--

connect internal;
startup nomount; 

CREATE DATABASE "ORA806" NOARCHIVELOG
    MAXLOGFILES    32
    MAXLOGMEMBERS  5
    MAXDATAFILES   600
    MAXINSTANCES   10
    MAXLOGHISTORY  1000
    CHARACTER SET  WE8ISO8859P1
LOGFILE
  GROUP 1
 ('/u03/app/oradata/ORA806/redo_g01a.log',
  '/u04/app/oradata/ORA806/redo_g01b.log',
  '/u05/app/oradata/ORA806/redo_g01c.log') SIZE 5M,
  GROUP 2 
( '/u03/app/oradata/ORA806/redo_g02a.log',
  '/u04/app/oradata/ORA806/redo_g02b.log',
  '/u05/app/oradata/ORA806/redo_g02c.log') SIZE 5M,
  GROUP 3 
( '/u03/app/oradata/ORA806/redo_g03a.log',
  '/u04/app/oradata/ORA806/redo_g03b.log',
  '/u05/app/oradata/ORA806/redo_g03c.log') SIZE 5M
DATAFILE
  '/u08/app/oradata/ORA806/system01.dbf' size 300M;
/


--
-- +-------------------------------------------------+
-- | Need a basic rollback segment before proceeding |
-- +-------------------------------------------------+
--

CREATE ROLLBACK SEGMENT r000
  TABLESPACE system
  STORAGE (
    INITIAL    500K
    NEXT       500K
    MINEXTENTS 2
  );
ALTER ROLLBACK SEGMENT r000 ONLINE;

--
-- +--------------------+
-- | RUN ORACLE SCRIPTS |
-- +--------------------+
--

@$ORACLE_HOME/rdbms/admin/catalog.sql;
@$ORACLE_HOME/rdbms/admin/catproc.sql;
REM @$ORACLE_HOME/rdbms/admin/catparr.sql;
@$ORACLE_HOME/rdbms/admin/catblock.sql;
@$ORACLE_HOME/rdbms/admin/dbmspool.sql
@$ORACLE_HOME/rdbms/admin/prvtpool.plb

commit;

connect system/manager;
@$ORACLE_HOME/sqlplus/admin/pupbld.sql;
@$ORACLE_HOME/rdbms/admin/catdbsyn.sql;
commit;

connect internal;

--
-- +-----------------------------------------+
-- | Create tablespace for Rollback Segments |
-- +-----------------------------------------+
--

CREATE TABLESPACE rbs1 
  DATAFILE '/u06/app/oradata/ORA806/rbs1_01.dbf' size 100M;

CREATE TABLESPACE rbs2 
  DATAFILE '/u06/app/oradata/ORA806/rbs2_01.dbf' size 100M;

--
-- +--------------------------+
-- | Create Rollback Segments |
-- +--------------------------+
--

CREATE PUBLIC ROLLBACK SEGMENT rbs1 
  TABLESPACE rbs1
  STORAGE ( 
     INITIAL     1M 
     NEXT        1M
     MINEXTENTS  2 
     MAXEXTENTS  121 
     OPTIMAL     3M
  );
ALTER ROLLBACK SEGMENT rbs1 ONLINE;


CREATE PUBLIC ROLLBACK SEGMENT rbs2 
  TABLESPACE rbs2
  STORAGE ( 
    INITIAL     1M
    NEXT        1M
    MINEXTENTS  2 
    MAXEXTENTS  121 
    OPTIMAL     3M
  );
ALTER ROLLBACK SEGMENT rbs2 ONLINE;


CREATE PUBLIC ROLLBACK SEGMENT rbs3 
  TABLESPACE rbs1
  STORAGE ( 
    INITIAL     1M
    NEXT        1M
    MINEXTENTS  2 
    MAXEXTENTS  121
    OPTIMAL     3M
  );
ALTER ROLLBACK SEGMENT rbs3 ONLINE;


CREATE PUBLIC ROLLBACK SEGMENT rbs4 
  TABLESPACE rbs2
  STORAGE ( 
    INITIAL     1M
    NEXT        1M
    MINEXTENTS  2
    MAXEXTENTS  121
    OPTIMAL     3M);
ALTER ROLLBACK SEGMENT rbs4 ONLINE;


CREATE PUBLIC ROLLBACK SEGMENT rbs5 
  TABLESPACE rbs1
  STORAGE (
    INITIAL     1M
    NEXT        1M
    MINEXTENTS  2
    MAXEXTENTS  121
    OPTIMAL     3M
  );
ALTER ROLLBACK SEGMENT rbs5 ONLINE;


CREATE PUBLIC ROLLBACK SEGMENT rbs6
  TABLESPACE rbs2
  STORAGE (
    INITIAL     1M
    NEXT        1M
    MINEXTENTS  2 
    MAXEXTENTS  121 
    OPTIMAL     3M);
ALTER ROLLBACK SEGMENT rbs6 ONLINE;


CREATE PUBLIC ROLLBACK SEGMENT rbs7 
  TABLESPACE rbs1
  STORAGE (
    INITIAL     1M
    NEXT        1M
    MINEXTENTS  2
    MAXEXTENTS  121 
    OPTIMAL     3M
  );
ALTER ROLLBACK SEGMENT rbs7 ONLINE;

CREATE PUBLIC ROLLBACK SEGMENT rbs8
  TABLESPACE rbs2
  STORAGE (
    INITIAL     1M 
    NEXT        1M
    MINEXTENTS  2
    MAXEXTENTS  121
    OPTIMAL     3M);
ALTER ROLLBACK SEGMENT rbs8 ONLINE;


--
-- +--------------------------+
-- | Create a TEMP tablespace |
-- +--------------------------+
--

CREATE TABLESPACE temp
  DATAFILE '/u07/app/oradata/ORA806/temp01.dbf' SIZE 500M
  DEFAULT STORAGE (
    MINEXTENTS   1
    MAXEXTENTS   500
    INITIAL      1M
    NEXT         1M
    PCTINCREASE  0
  )
  TEMPORARY;


--
-- +-----------------------------------------------+
-- | ALTER TEMPORARY TABLESPACE FOR SYS AND SYSTEM |
-- +-----------------------------------------------+
--
ALTER USER SYS  TEMPORARY TABLESPACE TEMP;
ALTER USER SYSTEM TEMPORARY TABLESPACE TEMP;

--
-- +-------------------------------------------+
-- | TAKE THE INITIAL ROLLBACK SEGMENT OFFLINE |
-- +-------------------------------------------+
--
ALTER ROLLBACK SEGMENT r000 OFFLINE;


--
-- +------------------------------------+
-- | ALTER PASSWORDS FOR SYS AND SYSTEM |
-- +------------------------------------+
--

-- ALTER USER SYS    IDENTIFIED BY xxxxxxxx;
-- ALTER USER SYSTEM IDENTIFIED BY xxxxxxxx;


--
-- +-------------------------+
-- | BRING DOWN THE DATABASE |
-- +-------------------------+
--

shutdown;

--
-- +-----------------------------------------+
-- | BRING THE DATABASE BACK UP, MOUNT IT    |
-- | FINALLY, PUT THE DATABASE IN            |
-- | "ARCHIVE LOG" MODE.                     |
-- +-----------------------------------------+
--

startup mount;
alter database archivelog;
alter database open;

--
-- +----------------------------------------+
-- | TURN SPOOLING OFF AND SET THE TIME OFF |
-- +----------------------------------------+
--

spool off
set time off

--
-- +------------------+
-- | EXIT MAIN SCRIPT |
-- +------------------+
--

exit;
