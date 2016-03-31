COL STATUS             FORMAT a25
COL hrs                FORMAT 9999.99
COL in_sec             FORMAT a09
COL out_sec            FORMAT a09
COL TIME_TAKEN_DISPLAY FORMAT a08
COL in_size            FORMAT a09
COL out_size           FORMAT a09
col COMP_RATIO         format 99999.99
SELECT SESSION_KEY, INPUT_TYPE, STATUS,
       TO_CHAR(START_TIME,'mm/dd/yy hh24:mi') start_time,
       TO_CHAR(END_TIME,'mm/dd/yy hh24:mi')   end_time,
       ELAPSED_SECONDS/3600                   hrs,
       OPTIMIZED,
       COMPRESSION_RATIO as COMP_RATIO,
       INPUT_BYTES_PER_SEC_DISPLAY in_sec,
       OUTPUT_BYTES_PER_SEC_DISPLAY out_sec,
       TIME_TAKEN_DISPLAY,
       INPUT_BYTES_DISPLAY in_size,
       OUTPUT_BYTES_DISPLAY out_size
FROM V$RMAN_BACKUP_JOB_DETAILS
ORDER BY SESSION_KEY;