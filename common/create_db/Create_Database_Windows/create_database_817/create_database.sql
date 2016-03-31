-- +-------------------------------------------------------------------+
-- | FILE          : create_datebase.sql                               |
-- |                                                                   |
-- |  --------                                                         |
-- | |HISTORY |                                                        |
-- |  ---------------------------------------------------------------- |
-- | NAME DATE      DESCRIPTION                                        |
-- | ---- --------- -------------------------------------------------- |
-- | JMH  14-NOV-01 Created original file.                             |
-- +-------------------------------------------------------------------+

SPOOL  create_database.log

CONNECT internal/oracle

define ORACLE_HOME = &1
define ORACLE_SID  = &2

--
-- +-------------------------------------+
-- | Capture creation in this spool file |
-- +-------------------------------------+
--

STARTUP nomount

CREATE DATABASE &ORACLE_SID NOARCHIVELOG
    MAXLOGFILES             32
    MAXLOGMEMBERS           5
    MAXDATAFILES            254
    MAXINSTANCES            10
    MAXLOGHISTORY           1000
    CHARACTER SET           WE8ISO8859P1
    NATIONAL CHARACTER SET  WE8ISO8859P1
LOGFILE
  GROUP 1
 ('C:\oracle\oradata\&ORACLE_SID\redo_g01a.log') SIZE 1M,
  GROUP 2 
( 'C:\oracle\oradata\&ORACLE_SID\redo_g02a.log') SIZE 1M,
  GROUP 3 
( 'C:\oracle\oradata\&ORACLE_SID\redo_g03a.log') SIZE 1M
DATAFILE
  'C:\oracle\oradata\&ORACLE_SID\system01.dbf' size 350M AUTOEXTEND OFF
/


SELECT 'START TIME: ' || TO_CHAR(sysdate, 'DD-MON-YYYY HH24:MI:SS') AS Start_Time 
FROM dual;

--
-- +-------------------------------------------------+
-- | Need a basic rollback segment before proceeding |
-- +-------------------------------------------------+
--

CREATE ROLLBACK SEGMENT r000
  TABLESPACE system
  STORAGE (
    INITIAL    1M
    NEXT       1M
    MINEXTENTS 2
  );
ALTER ROLLBACK SEGMENT r000 ONLINE;

--
-- +-------------------------+
-- | ALTER SYSTEM TABLESPACE |
-- +-------------------------+
--

ALTER TABLESPACE system
  DEFAULT STORAGE (
    INITIAL 64K NEXT 64K MINEXTENTS 1 MAXEXTENTS UNLIMITED PCTINCREASE 50
  );

ALTER TABLESPACE SYSTEM MINIMUM EXTENT 64K;

--
-- +-----------------------------------------+
-- | Create tablespace for Rollback Segments |
-- +-----------------------------------------+
--


CREATE TABLESPACE "RBS"
    LOGGING
    DATAFILE 'C:\oracle\oradata\&ORACLE_SID\rbs_01.dbf' SIZE 100M
    REUSE
    AUTOEXTEND ON   NEXT 250M   MAXSIZE 1500M
    EXTENT MANAGEMENT LOCAL UNIFORM SIZE 1M
/

--
-- +--------------------------+
-- | Create Rollback Segments |
-- +--------------------------+
--


CREATE PUBLIC ROLLBACK SEGMENT rbs1
  TABLESPACE rbs
  STORAGE (
     MINEXTENTS  2
     MAXEXTENTS  unlimited
     OPTIMAL     4M
  );
ALTER ROLLBACK SEGMENT rbs1 ONLINE;


CREATE PUBLIC ROLLBACK SEGMENT rbs2
  TABLESPACE rbs
  STORAGE (
     MINEXTENTS  2
     MAXEXTENTS  unlimited
     OPTIMAL     4M
  );
ALTER ROLLBACK SEGMENT rbs2 ONLINE;


CREATE PUBLIC ROLLBACK SEGMENT rbs3
  TABLESPACE rbs
  STORAGE (
     MINEXTENTS  2
     MAXEXTENTS  unlimited
     OPTIMAL     4M
  );
ALTER ROLLBACK SEGMENT rbs3 ONLINE;


CREATE PUBLIC ROLLBACK SEGMENT rbs4
  TABLESPACE rbs
  STORAGE (
     MINEXTENTS  2
     MAXEXTENTS  unlimited
     OPTIMAL     4M
  );
ALTER ROLLBACK SEGMENT rbs4 ONLINE;


CREATE PUBLIC ROLLBACK SEGMENT rbs5
  TABLESPACE rbs
  STORAGE (
     MINEXTENTS  2
     MAXEXTENTS  unlimited
     OPTIMAL     4M
  );
ALTER ROLLBACK SEGMENT rbs5 ONLINE;


CREATE PUBLIC ROLLBACK SEGMENT rbs6
  TABLESPACE rbs
  STORAGE (
     MINEXTENTS  2
     MAXEXTENTS  unlimited
     OPTIMAL     4M
  );
ALTER ROLLBACK SEGMENT rbs6 ONLINE;


CREATE PUBLIC ROLLBACK SEGMENT rbs7
  TABLESPACE rbs
  STORAGE (
     MINEXTENTS  2
     MAXEXTENTS  unlimited
     OPTIMAL     4M
  );
ALTER ROLLBACK SEGMENT rbs7 ONLINE;


CREATE PUBLIC ROLLBACK SEGMENT rbs8
  TABLESPACE rbs
  STORAGE (
     MINEXTENTS  2
     MAXEXTENTS  unlimited
     OPTIMAL     4M
  );
ALTER ROLLBACK SEGMENT rbs8 ONLINE;

--
-- +--------------------------+
-- | Create a TEMP tablespace |
-- +--------------------------+
--

CREATE TEMPORARY TABLESPACE "TEMP"
  TEMPFILE 'C:\oracle\oradata\&ORACLE_SID\temp01.dbf' SIZE 75M REUSE
  AUTOEXTEND ON NEXT 1M MAXSIZE UNLIMITED
  EXTENT MANAGEMENT LOCAL;

--
-- +-----------------------------------------------+
-- | ALTER TEMPORARY TABLESPACE FOR SYS AND SYSTEM |
-- +-----------------------------------------------+
--

ALTER USER SYS  TEMPORARY TABLESPACE TEMP;
ALTER USER SYSTEM TEMPORARY TABLESPACE TEMP;

--
-- +---------------------------+
-- | RUN ALL REMAINING SCRIPTS |
-- +---------------------------+
--

@%ORACLE_HOME%\rdbms\admin\catalog.sql
@%ORACLE_HOME%\rdbms\admin\catexp7.sql
@%ORACLE_HOME%\rdbms\admin\catproc.sql
@%ORACLE_HOME%\rdbms\admin\caths.sql

@%ORACLE_HOME%\rdbms\admin\catblock.sql
@%ORACLE_HOME%\rdbms\admin\dbmspool.sql

commit;

connect system/manager
@%ORACLE_HOME%\sqlplus\admin\pupbld.sql
commit;

connect internal

--
-- +-------------------------------------------+
-- | TAKE THE INITIAL ROLLBACK SEGMENT OFFLINE |
-- +-------------------------------------------+
--

ALTER ROLLBACK SEGMENT r000 OFFLINE;


--
-- +----------------------------------------+
-- | TURN SPOOLING OFF AND SET THE TIME OFF |
-- +----------------------------------------+
--

SELECT 'END TIME: ' || TO_CHAR(sysdate, 'DD-MON-YYYY HH24:MI:SS') AS End_Time
FROM dual;

REM spool off

--
-- +------------------+
-- | EXIT MAIN SCRIPT |
-- +------------------+
--

exit
