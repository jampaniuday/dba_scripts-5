col "Tablespace" for a32
col "MB_TOTAL"   for 99,999,999
col "MB_USED"    for 99,999,999.99
col "MB_FREE"    for 99,999,999.99
col "MB_PERC"    for 99,999,999.99
SELECT d.tablespace_name,
       D.STATUS,
       ROUND ( (A.BYTES / 1024 / 1024), 2) MB_TOTAL,
       ROUND (
          ( (A.BYTES - DECODE (F.BYTES, NULL, 0, F.BYTES)) / 1024 / 1024),
          2) MB_USED,
       ROUND (
          ( (A.BYTES / 1024 / 1024)
           - (A.BYTES - DECODE (F.BYTES, NULL, 0, F.BYTES)) / 1024 / 1024),
          2) MB_FREE,
       ROUND (
          ( ( (A.BYTES / 1024 / 1024)
             - (A.BYTES - DECODE (F.BYTES, NULL, 0, F.BYTES)) / 1024 / 1024)
           / (A.BYTES / 1024 / 1024))
          * 100.0,
          2) MB_PERC
  FROM SYS.DBA_TABLESPACES D, SYS.SM$TS_AVAIL A, SYS.SM$TS_FREE F
 WHERE D.TABLESPACE_NAME = A.TABLESPACE_NAME
       AND F.TABLESPACE_NAME(+) = D.TABLESPACE_NAME
UNION ALL
SELECT d.tablespace_name,
       d.status,
       ROUND ( (a.bytes / 1024 / 1024), 2),
       ROUND (NVL (t.bytes, 0) / 1024 / 1024, 2),
       ROUND ( (a.bytes / 1024 / 1024) - (NVL (t.bytes, 0) / 1024 / 1024), 2),
       ROUND (100 - (NVL (t.bytes / a.bytes * 100, 0)), 2)
  FROM sys.dba_tablespaces d,
       (  SELECT tablespace_name, SUM (bytes) bytes
            FROM dba_temp_files
        GROUP BY tablespace_name) a,
       (  SELECT tablespace_name, SUM (bytes_cached) bytes
            FROM SYS.v_$temp_extent_pool
        GROUP BY tablespace_name) t
 WHERE     d.tablespace_name = a.tablespace_name(+)
       AND d.tablespace_name = t.tablespace_name(+)
       AND d.extent_management LIKE 'LOCAL'
       AND d.contents LIKE 'TEMPORARY'
order by MB_FREE desc
--order by tablespace_name asc;