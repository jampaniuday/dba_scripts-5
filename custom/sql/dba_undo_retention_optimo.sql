SELECT d.undo_size / (1024 * 1024) "ACTUAL UNDO SIZE [MByte]",
       Substr(e.VALUE,1,25) "ACTUAL UNDO RETENTION [Sec]",
       Round((d.undo_size / (To_number(f.VALUE) * g.undo_block_per_sec))) "UNDO RETENTION OPTIMO [Sec]"
FROM   (SELECT Sum(a.bytes) undo_size
        FROM   v$datafile a,
               v$tablespace b,
               dba_tablespaces c
        WHERE  c.contents = 'UNDO'
               AND c.status = 'ONLINE'
               AND b.name = c.tablespace_name
               AND a.ts# = b.ts#) d,
       v$parameter e,
       v$parameter f,
       (SELECT Max(undoblks / ((end_time - begin_time) * 3600 * 24)) undo_block_per_sec
        FROM   v$undostat) g
WHERE  e.name = 'undo_retention'
       AND f.name = 'db_block_size'