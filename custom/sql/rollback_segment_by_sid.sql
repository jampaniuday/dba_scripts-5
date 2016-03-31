SET LINESIZE    240
SET verify off
COL username FORMAT A20
COL sesion FORMAT A40
COL Tamano_MB FORMAT 999,999,999.99
COL Offline_Stmt FORMAT A50
COL logon_time FORMAT A20
COL Tamano_MB FORMAT 999,999.99
COL STATUS FORMAT A10
COL NAME FORMAT A20

select d.sid, d.serial#, d.username, a.name, sum(e.bytes / 1024 / 1024) Tamano_MB,
b.status  , TO_CHAR(d.logon_Time,'DD-MON-YYYY HH24:MI:SS') logon_time,
'alter rollback segment '||'"'||e.segment_name||'"'||' offline;' Offline_Stmt
from v$rollname a, v$rollstat b, v$transaction c, v$session d, dba_undo_extents e
WHERE  a.usn = b.usn
AND    a.usn = c.xidusn
AND    c.ses_addr = d.saddr
AND    a.name = e.segment_name
AND    d.sid in(&1)
AND    a.name IN (
    SELECT segment_name
    FROM dba_segments
    WHERE tablespace_name like 'UNDOTBS%'
   )
group by a.name, d.username, b.status, d.sid, d.serial#, 'alter rollback segment '||'"'||e.segment_name||'"'||' offline;', TO_CHAR(d.logon_Time,'DD-MON-YYYY HH24:MI:SS')
order by logon_time asc;