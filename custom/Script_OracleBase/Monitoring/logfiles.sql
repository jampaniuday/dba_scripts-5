-- -----------------------------------------------------------------------------------
-- File Name    : http://www.oracle-base.com/dba/monitoring/logfiles.sql
-- Author       : Tim Hall
-- Description  : Displays information about redo log files.
-- Requirements : Access to the V$ views.
-- Call Syntax  : @logfiles
-- Last Modified: 21/12/2004
-- -----------------------------------------------------------------------------------

SET LINESIZE 100
COLUMN member FORMAT A70

SELECT group#,
       member,
       type,
       status
FROM   v$logfile
ORDER BY group#, member;

SET LINESIZE 80