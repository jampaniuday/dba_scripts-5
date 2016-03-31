col UNDO_SIZE format 999,999,999,999
Prompt Actual Undo Size
SELECT SUM(a.bytes) "UNDO_SIZE"
  FROM v$datafile a,
       v$tablespace b,
       dba_tablespaces c
 WHERE c.contents = 'UNDO'
   AND c.status = 'ONLINE'
   AND b.name = c.tablespace_name
   AND a.ts# = b.ts#;
col UNDO_BLOCK_PER_SEC format 999,999,999,999
Prompt Undo Blocks per Second
SELECT MAX(undoblks/((end_time-begin_time)*3600*24))
      "UNDO_BLOCK_PER_SEC"
FROM v$undostat;
col DB_BLOCK_SIZE_[KByte] format 999,999,999,999
Prompt DB Block Size
SELECT TO_NUMBER(value) "DB_BLOCK_SIZE_[KByte]"
 FROM v$parameter
WHERE name = 'db_block_size';
col "ACTUAL_UNDO_SIZE_[MByte]"     format 999,999,999,999
col "UNDO_RETENTION_[Sec]"         format 999,999,999,999
col "OPTIMAL_UNDO_RETENTION_[Sec]" format 999,999,999,999
Prompt Optimal Undo Retention
--209'715'200 / (3.12166667 * 4'096) = 16'401 [Sec]
--Using Inline Views, you can do all in one query!
SELECT d.undo_size/(1024*1024) "ACTUAL_UNDO_SIZE_[MByte]",
       SUBSTR(e.value,1,25) "UNDO_RETENTION_[Sec]",
       ROUND((d.undo_size / (to_number(f.value) *
       g.undo_block_per_sec))) "OPTIMAL_UNDO_RETENTION_[Sec]"
  FROM (
       SELECT SUM(a.bytes) undo_size
          FROM v$datafile a,
               v$tablespace b,
               dba_tablespaces c
         WHERE c.contents = 'UNDO'
           AND c.status = 'ONLINE'
           AND b.name = c.tablespace_name
           AND a.ts# = b.ts#
       ) d,
       v$parameter e,
       v$parameter f,
       (
       SELECT MAX(undoblks/((end_time-begin_time)*3600*24))
              undo_block_per_sec
         FROM v$undostat
       ) g
WHERE e.name = 'undo_retention'
  AND f.name = 'db_block_size'
;
col "NEEDED_UNDO_SIZE_[MByte]"     format 999,999,999,999
prompt  Calculate Needed UNDO Size for given Database Activity
SELECT d.undo_size/(1024*1024) "ACTUAL_UNDO_SIZE_[MByte]",
       SUBSTR(e.value,1,25) "UNDO_RETENTION_[Sec]",
       (TO_NUMBER(e.value) * TO_NUMBER(f.value) *
       g.undo_block_per_sec) / (1024*1024)
      "NEEDED_UNDO_SIZE_[MByte]"
  FROM (
       SELECT SUM(a.bytes) undo_size
         FROM v$datafile a,
              v$tablespace b,
              dba_tablespaces c
        WHERE c.contents = 'UNDO'
          AND c.status = 'ONLINE'
          AND b.name = c.tablespace_name
          AND a.ts# = b.ts#
       ) d,
      v$parameter e,
       v$parameter f,
       (
       SELECT MAX(undoblks/((end_time-begin_time)*3600*24))
         undo_block_per_sec
         FROM v$undostat
       ) g
 WHERE e.name = 'undo_retention'
  AND f.name = 'db_block_size'
;
Prompt The previous query may return a "NEEDED UNDO SIZE" that is less than the "ACTUAL UNDO SIZE".
Prompt If this is the case, you may be wasting space.
Prompt You can choose to resize your UNDO tablespace to a lesser value or increase your
Prompt UNDO_RETENTION parameter to use the additional space.
Prompt
Prompt
show parameter undo
prompt
prompt
col "UNDO_MB"     format 999,999,999,999
Prompt identify who's using undo
SELECT SID, Y.USERNAME, Y.OSUSER, X.LOG_IO, X.PHY_IO,
       ROUND(USED_UBLK*(SELECT VALUE/1024 FROM V$PARAMETER WHERE NAME = 'db_block_size')/1024,2) "UNDO_MB",
       X.START_TIME,
       Y.STATUS
FROM V$TRANSACTION X, V$SESSION Y
WHERE SADDR = SES_ADDR
order by UNDO_MB desc;