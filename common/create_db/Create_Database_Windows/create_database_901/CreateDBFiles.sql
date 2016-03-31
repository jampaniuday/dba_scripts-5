
connect SYS/change_on_install as SYSDBA

define ORACLE_HOME = &1
define ORACLE_SID  = &2

set echo on

spool CreateDBFiles.log


/*
 * -----------------------------------------------------------
 * DRSYS
 * -----------------------------------------------------------
 *   Used to support both "Oracle Text (formerly interMedia Text)" 
 *   and "Workspace Manager". All segments in this tablespace 
 *   will be owned by "CTXSYS" and "WKSYS" respectively.
 *   After a default installation, the objects in this
 *   tablespace will consume about 10MB.
 * -----------------------------------------------------------
 */

CREATE TABLESPACE "DRSYS"
  LOGGING
  DATAFILE 'C:\oracle\oradata\&ORACLE_SID\drsys01.dbf' SIZE 50M
  REUSE
  AUTOEXTEND ON
  NEXT 50M
  MAXSIZE 500M
  EXTENT MANAGEMENT LOCAL
  SEGMENT SPACE MANAGEMENT AUTO
/



/*
 * -----------------------------------------------------------
 * CWMLITE
 * -----------------------------------------------------------
 *   Used to support Oracle OLAP Services. All segments in this 
 *   tablespace will be owned by "OLAPSYS".
 *   After a default installation, the objects in this
 *   tablespace will consume about 13.3MB.
 * -----------------------------------------------------------
 */

CREATE TABLESPACE "CWMLITE"
  LOGGING
  DATAFILE 'C:\oracle\oradata\&ORACLE_SID\cwmlite01.dbf' SIZE 50M
  REUSE
  AUTOEXTEND ON
  NEXT 50M
  MAXSIZE 500M
  EXTENT MANAGEMENT LOCAL
  SEGMENT SPACE MANAGEMENT AUTO
/



/*
 * -----------------------------------------------------------
 * ODM
 * -----------------------------------------------------------
 *   Used to support Oracle Data Mining. All segments in this 
 *   tablespace will be owned by both "ODM" and "ODM_MTR".
 *   After a default installation, the objects in this
 *   tablespace will consume about 9.7MB. 
 * -----------------------------------------------------------
 */

CREATE TABLESPACE "ODM"
  LOGGING
  DATAFILE 'C:\oracle\oradata\&ORACLE_SID\odm01.dbf' SIZE 50M
  REUSE
  AUTOEXTEND ON
  NEXT 50M
  MAXSIZE 500M
  EXTENT MANAGEMENT LOCAL
  SEGMENT SPACE MANAGEMENT AUTO
/



/*
 * -----------------------------------------------------------
 * XDB
 * -----------------------------------------------------------
 *   Used to support Oracle's SQL XML management. Better known
 *   as XML DB. All segments in this tablespace will be
 *   owned by the "XDB" user. After a default installation,
 *   the objects in this tablespace will consume about 47MB.
 * -----------------------------------------------------------
 */

CREATE TABLESPACE "XDB"
  LOGGING
  DATAFILE 'C:\oracle\oradata\&ORACLE_SID\xdb01.dbf' SIZE 50M
  REUSE
  AUTOEXTEND ON
  NEXT 50M
  MAXSIZE 500M
  EXTENT MANAGEMENT LOCAL
  SEGMENT SPACE MANAGEMENT AUTO
/



/*
 * -----------------------------------------------------------
 * USERS
 * -----------------------------------------------------------
 *   This tablespace is created for storing user specific
 *   data segments. For developers and other users, this 
 *   is generally used as their default tablespaces.
 * -----------------------------------------------------------
 */

CREATE TABLESPACE "USERS"
  LOGGING
  DATAFILE 'C:\oracle\oradata\&ORACLE_SID\users01.dbf' SIZE 50M
  REUSE
  AUTOEXTEND ON
  NEXT  50M
  MAXSIZE 500M
  EXTENT MANAGEMENT LOCAL
  SEGMENT SPACE MANAGEMENT AUTO
/



/*
 * -----------------------------------------------------------
 * INDX
 * -----------------------------------------------------------
 *   This tablespace is created for storing user specific
 *   index segments. These are generally indexes that are 
 *   created on tables in the USERS tablespaces.
 * -----------------------------------------------------------
 */

CREATE TABLESPACE "INDX"
  LOGGING
  DATAFILE 'C:\oracle\oradata\&ORACLE_SID\indx01.dbf' SIZE 50M
  REUSE
  AUTOEXTEND ON
  NEXT  50M
  MAXSIZE 500M
  EXTENT MANAGEMENT LOCAL
  SEGMENT SPACE MANAGEMENT AUTO
/



/*
 * -----------------------------------------------------------
 * TOOLS
 * -----------------------------------------------------------
 *   This tablespace is created for storing segments related
 *   to Oracle tools. There is generally no use for this
 *   tablespaces as most tools use tablespaces with more of
 *   a descriptive name.
 * -----------------------------------------------------------
 */

/*
CREATE TABLESPACE "TOOLS"
  LOGGING
  DATAFILE 'C:\oracle\oradata\&ORACLE_SID\tools01.dbf' SIZE 50M
  REUSE
  AUTOEXTEND ON
  NEXT 50M
  MAXSIZE 500M
  EXTENT MANAGEMENT LOCAL
  SEGMENT SPACE MANAGEMENT AUTO
/
*/



/*
 * -----------------------------------------------------------
 * EXAMPLE
 * -----------------------------------------------------------
 *   This tablespace is used for storing segments related
 *   to the Demo Schemas. The demo schemas are created using
 *   the script: demoSchemas.sql
 * -----------------------------------------------------------
 */

/*
CREATE TABLESPACE "EXAMPLE"
  LOGGING
  DATAFILE 'C:\oracle\oradata\&ORACLE_SID\example01.dbf' SIZE 50M
  REUSE
  AUTOEXTEND ON
  NEXT 50M
  MAXSIZE 500M
  EXTENT MANAGEMENT LOCAL
  SEGMENT SPACE MANAGEMENT AUTO
/
*/


spool off

exit;
