rem
rem modified by chaas 970602 to connect as sys
rem
connect sys/oral1nux;
REM $Header: ADXGNPNS.sql 23.0 97/05/06 14:03:36 porting ship $
REM +======================================================================+
REM | Copyright (c) 1997 Oracle Corporation Redwood Shores, California, USA|
REM |                       All rights reserved.                           |
REM +======================================================================+
REM
REM NAME
REM   ADXGNPNS.sql - Generate Pinning script for sequences
REM
REM DESCRIPTION
REM   Generate a SQL file (ADXSPPNS.sql) which will pin sequences
REM   in a given schema and then run the generated script
REM   This is needed for 7.3.2.3 (after applying 432251) and higher
REM
REM NOTES
REM   You must execute this after every restart of the database
REM   and once for each schema which should have pinned objects.
REM
REM     sqlplus sys/change_on_install @ADXGNPNS <schema name>
REM
REM   You must run once for each base product schema.
REM
REM   The script explicitly does not compile or pin SYS or SYSTEM objects.
REM
REM SEE ALSO
REM   ADXGNPIN.sql which pins PL/SQL objects
REM +======================================================================+

WHENEVER SQLERROR EXIT FAILURE
WHENEVER OSERROR  EXIT FAILURE

prompt Name of base product schema for which to pin sequences or % for all: 
prompt &&1

set echo off
set feedback off
set pages 0 
set heading off
set lines 79
set verify off

spool /u01/app/oracle/common/sql/ADXSPPNS.sql

prompt set echo on
prompt spool /u01/app/oracle/common/sql/ADXSPPNS.log
prompt set serveroutput ON


select distinct
'execute dbms_shared_pool.keep('''||obj.owner||'.'||obj.object_name||
''',''Q'');'
from dba_objects obj
where 1=1
and obj.object_type in ('SEQUENCE')
and obj.owner not in ('SYS','SYSTEM')
and obj.owner like upper('&1')
order by 1;


prompt spool off
prompt exit;;

spool off

@/u01/app/oracle/common/sql/ADXSPPNS.sql

exit;
