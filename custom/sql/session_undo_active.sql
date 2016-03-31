SET LINESIZE    240
set verify off
COLUMN username FORMAT A20
COLUMN sesion FORMAT A40
COLUMN Tamano_MB FORMAT 999,999,999.99

SELECT a.name, sum(e.bytes / 1024 / 1024) Tamano_MB, --e.status Seg_Status,
b.status , d.username , d.sid , d.serial#,d.status,TO_CHAR(d.logon_Time,'DD-MON-YYYY HH24:MI:SS') logon_time, 
'alter system kill session '''||d.sid||','||d.serial#||''' immediate;' sesion
FROM   v$rollname a, v$rollstat b, v$transaction c, v$session d, dba_undo_extents e
WHERE  a.usn = b.usn
AND    a.usn = c.xidusn
AND    c.ses_addr = d.saddr
AND    a.name = e.segment_name
--AND    e.status = 
AND    a.name IN (
    SELECT segment_name
    FROM dba_segments
    WHERE tablespace_name = UPPER('&1')
   )
group by a.name,d.username, b.status,d.sid,d.serial#,d.status, 'alter system kill session '''||d.sid||','||d.serial#||''' immediate;',TO_CHAR(d.logon_Time,'DD-MON-YYYY HH24:MI:SS')
--,e.status
order by logon_time desc;