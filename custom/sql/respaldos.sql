prompt ::::::::::::::
prompt respaldos1.sql
prompt ::::::::::::::
COL STATUS FORMAT a9
COL hrs    FORMAT 999.99
SELECT SESSION_KEY, INPUT_TYPE, STATUS,
       TO_CHAR(START_TIME,'mm/dd/yy hh24:mi') start_time,
       TO_CHAR(END_TIME,'mm/dd/yy hh24:mi')   end_time,
       ELAPSED_SECONDS/3600                   hrs
FROM V$RMAN_BACKUP_JOB_DETAILS
ORDER BY SESSION_KEY;

prompt ::::::::::::::
prompt respaldos2.sql
prompt ::::::::::::::
COL in_sec FORMAT a10
COL out_sec FORMAT a10
COL TIME_TAKEN_DISPLAY FORMAT a10
SELECT SESSION_KEY, 
       OPTIMIZED, 
       COMPRESSION_RATIO, 
       INPUT_BYTES_PER_SEC_DISPLAY in_sec,
       OUTPUT_BYTES_PER_SEC_DISPLAY out_sec, 
       TIME_TAKEN_DISPLAY
FROM   V$RMAN_BACKUP_JOB_DETAILS
ORDER BY SESSION_KEY;

prompt ::::::::::::::
prompt respaldos3.sql
prompt ::::::::::::::
COL in_size  FORMAT a10
COL out_size FORMAT a10
SELECT SESSION_KEY, 
       INPUT_TYPE,
       COMPRESSION_RATIO, 
       INPUT_BYTES_DISPLAY in_size,
       OUTPUT_BYTES_DISPLAY out_size
FROM   V$RMAN_BACKUP_JOB_DETAILS
ORDER BY SESSION_KEY;

prompt ::::::::::::::
prompt respaldos4.sql
prompt ::::::::::::::
COL BS_REC    FORMAT 99999
COL BP_REC    FORMAT 99999
COL MB        FORMAT 9999999
COL ENCRYPTED FORMAT A7
COL TAG       FORMAT A25

SELECT S.RECID AS "BS_REC", P.RECID AS "BP_REC", P.ENCRYPTED, 
       P.TAG, P.HANDLE AS "MEDIA_HANDLE"
FROM   V$BACKUP_PIECE P, V$BACKUP_SET S
WHERE  P.SET_STAMP = S.SET_STAMP
AND    P.SET_COUNT = S.SET_COUNT;