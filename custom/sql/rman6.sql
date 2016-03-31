COL STATUS FORMAT a9
COL hrs    FORMAT 999.99
SELECT SESSION_KEY, INPUT_TYPE, STATUS,
       TO_CHAR(START_TIME,'mm/dd/yy hh24:mi') start_time,
       TO_CHAR(END_TIME,'mm/dd/yy hh24:mi')   end_time,
       ELAPSED_SECONDS/3600                   hrs
FROM V$RMAN_BACKUP_JOB_DETAILS
ORDER BY SESSION_KEY;
[oracle@bat1bebdadm2 dba.gt]$ cat rman6.sql
SELECT type, records_total, records_used
FROM v$controlfile_record_section
WHERE type lIKE '%BACKUP%'
/

prompt
prompt Available automatic control files within all available (and expired) backup sets.
prompt 
SELECT
    bs.recid                                               bs_key
  , bp.piece#                                              piece#
  , bp.copy#                                               copy#
  , bp.recid                                               bp_key
  , DECODE(   bs.controlfile_included
            , 'NO', '-'
            , bs.controlfile_included)                     controlfile_included
  , TO_CHAR(bs.completion_time, 'DD-MON-YYYY HH24:MI:SS')  completion_time
  , DECODE(   status
            , 'A', 'Available'
            , 'D', 'Deleted'
            , 'X', 'Expired')                              status
  , handle                                                 handle
FROM
    v$backup_set    bs
  , v$backup_piece  bp
WHERE
      bs.set_stamp = bp.set_stamp
  AND bs.set_count = bp.set_count
  AND bp.status IN ('A', 'X')
  AND bs.controlfile_included != 'NO'
ORDER BY
    bs.recid
  , piece#
/

prompt
prompt Available automatic SPFILE files within all available (and expired) backup sets.
prompt 
SELECT
    bs.recid                                               bs_key
  , bp.piece#                                              piece#
  , bp.copy#                                               copy#
  , bp.recid                                               bp_key
  , sp.spfile_included                                     spfile_included
  , TO_CHAR(bs.completion_time, 'DD-MON-YYYY HH24:MI:SS')  completion_time
  , DECODE(   status
            , 'A', 'Available'
            , 'D', 'Deleted'
            , 'X', 'Expired')                              status
  , handle                                                 handle
FROM
    v$backup_set                                           bs
  , v$backup_piece                                         bp
  ,  (select distinct
          set_stamp
        , set_count
        , 'YES'     spfile_included
      from v$backup_spfile)                                sp
WHERE
      bs.set_stamp = bp.set_stamp
  AND bs.set_count = bp.set_count
  AND bp.status IN ('A', 'X')
  AND bs.set_stamp = sp.set_stamp
  AND bs.set_count = sp.set_count
ORDER BY
    bs.recid
  , piece#
/

prompt
prompt Available backup sets contained in the control file.
prompt Includes available and expired backup sets.
prompt 
SELECT
    bs.recid                                              bs_key
  , DECODE(backup_type
           , 'L', 'Archived Logs'
           , 'D', 'Datafile Full'
           , 'I', 'Incremental')                          backup_type
  , device_type                                           device_type
  , DECODE(   bs.controlfile_included
            , 'NO', null
            , bs.controlfile_included)                    controlfile_included
  , sp.spfile_included                                    spfile_included
  , bs.incremental_level                                  incremental_level
  , bs.pieces                                             pieces
  , TO_CHAR(bs.start_time, 'mm/dd/yy HH24:MI:SS')         start_time
  , TO_CHAR(bs.completion_time, 'mm/dd/yy HH24:MI:SS')    completion_time
  , bs.elapsed_seconds                                    elapsed_seconds
  , bp.tag                                                tag
  , bs.block_size                                         block_size
FROM
    v$backup_set                           bs
  , (select distinct
         set_stamp
       , set_count
       , tag
       , device_type
     from v$backup_piece
     where status in ('A', 'X'))           bp
 ,  (select distinct
         set_stamp
       , set_count
       , 'YES'     spfile_included
     from v$backup_spfile)                 sp
WHERE
      bs.set_stamp = bp.set_stamp
  AND bs.set_count = bp.set_count
  AND bs.set_stamp = sp.set_stamp (+)
  AND bs.set_count = sp.set_count (+)
ORDER BY
    bs.recid
/

prompt
prompt Available backup pieces contained in the control file.
prompt Includes available and expired backup sets.
prompt 

SELECT
    bs.recid                                            bs_key
  , bp.piece#                                           piece#
  , bp.copy#                                            copy#
  , bp.recid                                            bp_key
  , DECODE(   status
            , 'A', 'Available'
            , 'D', 'Deleted'
            , 'X', 'Expired')                           status
  , handle                                              handle
  , TO_CHAR(bp.start_time, 'mm/dd/yy HH24:MI:SS')       start_time
  , TO_CHAR(bp.completion_time, 'mm/dd/yy HH24:MI:SS')  completion_time
  , bp.elapsed_seconds                                  elapsed_seconds
FROM
    v$backup_set    bs
  , v$backup_piece  bp
WHERE
      bs.set_stamp = bp.set_stamp
  AND bs.set_count = bp.set_count
  AND bp.status IN ('A', 'X')
ORDER BY
    bs.recid
  , piece#
/