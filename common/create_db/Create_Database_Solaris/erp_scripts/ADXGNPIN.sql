rem
rem modified by chaas 970602 to connect as sys
rem modified by chaas 970602 to use like instead of = in the where clause
rem
connect sys/oral1nux;
REM $Header: ADXGNPIN.sql 23.1 97/02/02 18:44:37 porting ship $
REM +======================================================================+
REM | Copyright (c) 1994 Oracle Corporation Redwood Shores, California, USA|
REM |                       All rights reserved.                           |
REM +======================================================================+
REM
REM NAME
REM   ADXGNPIN.sql - Generate package Pinning script
REM
REM DESCRIPTION
REM   Generate a SQL file (ADXSPPIN.sql) which will pin all packages and
REM   functions in a given schema and then run the generated script
REM
REM NOTES
REM   You must execute this after every restart of the database
REM   and once for each schema which should have pinned objects.
REM
REM     sqlplus sys/change_on_install @ADXGNPIN <schema name>
REM
REM   You must run once for each APPS schema.
REM
REM   Procedures do not pin so they are not included.
REM   The script explicitly does not compile or pin SYS or SYSTEM objects.
REM +======================================================================+

WHENEVER SQLERROR EXIT FAILURE
WHENEVER OSERROR  EXIT FAILURE

prompt Name of schema for which to pin objects: 
prompt &&1

set echo off
set feedback off
set pages 0 
set heading off
set lines 79
set verify off

spool /u01/app/oracle/common/sql/ADXSPPIN.sql

prompt set echo on
prompt spool /u01/app/oracle/common/sql/ADXSPPIN.log
prompt set serveroutput ON

select distinct
'execute dbms_shared_pool.keep('''||obj.owner||'.'||obj.object_name||''');'
from dba_objects obj
where 1=1
and obj.object_type in ('PACKAGE','PACKAGE BODY','FUNCTION')
and obj.owner not in ('SYS','SYSTEM')
and obj.owner like upper('&1')
and obj.object_name != 'PA_BUDGET_UPGRADE_PKG'
order by 1;

prompt spool off
prompt exit;;

spool off

@/u01/app/oracle/common/sql/ADXSPPIN.sql

exit;
