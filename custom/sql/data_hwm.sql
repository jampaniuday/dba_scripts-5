-- |--------------------------------------------------------------------------------|
-- | DATABASE     : Oracle                                                          |
-- | FILE         : data_hwm.sql                                                    |
-- | CLASS        : Database Administration                                         |
-- | Actualizado  : 02/09/2014 - Filtrado por Tablespace                            |
-- | Actualizado  : 12/06/2015 - Eliminado param ALL, ahora lista todo sin Param    |
-- | Call Syntax  : @data_hwm (Tablespace or sin parametros)                        |
-- | PURPOSE      : Reporta el uso de espacio en todos los datafiles e              |
-- |                indica hasta donde esta la marca de agua (HWM).                 |
-- |                trabaja con Oracle8i or higher. E incluye el TEMPORARY          |
-- |                tablespaces. (i.e. "tempfiles")                                 |
-- +--------------------------------------------------------------------------------+

SET TERMOUT OFF;
COLUMN current_instance NEW_VALUE current_instance NOPRINT;
SELECT rpad(instance_name, 17) current_instance FROM v$instance;
COLUMN value NEW_VALUE bls NOPRINT;
select value from v$parameter where  name = 'db_block_size';
SET TERMOUT ON;

PROMPT 
PROMPT +------------------------------------------------------------------------+
PROMPT | Report         : Datafile high water mark                              |
PROMPT | Instance       : &current_instance                                     |
PROMPT | db_block_size  : &bls                                                 |
PROMPT +------------------------------------------------------------------------+

SET ECHO        OFF
SET FEEDBACK    6
SET HEADING     ON
SET LINESIZE    256
SET PAGESIZE    50000
SET TERMOUT     ON
SET TIMING      OFF
SET TRIMOUT     ON
SET TRIMSPOOL   ON
SET VERIFY      OFF

CLEAR COLUMNS
CLEAR BREAKS
CLEAR COMPUTES

COLUMN tablespace       FORMAT a18              HEADING 'Tablespace Name'
COLUMN filename         FORMAT a70              HEADING 'Filename'
COLUMN filesize         FORMAT 999,999,999.99   HEADING 'File Size(MB)'
COLUMN used             FORMAT 999,999,999.99   HEADING 'Used(MB)'
COLUMN pct_used         FORMAT 999999           HEADING 'Pct.Used%'
COLUMN shrink_poss      FORMAT 999,999,999.99   HEADING 'Recover MB'
COLUMN HWMMB            FORMAT 999,999,999.99   HEADING 'HWM (MB)'

BREAK ON report

COMPUTE sum OF filesize         ON report
COMPUTE sum OF used             ON report
COMPUTE avg OF pct_used         ON report
COMPUTE sum OF shrink_poss  	ON report
COMPUTE sum OF HWMMB  			ON report

SELECT /*+ ordered */
    d.tablespace_name                                           tablespace
  , d.file_name                                                 filename
--  , d.file_id                                                 file_id
  , d.bytes/1024/1024                                           filesize
  , NVL((d.bytes - s.bytes), d.bytes)/1024/1024     used
  , TRUNC(((NVL((d.bytes - s.bytes) , d.bytes)) / d.bytes) * 100)  pct_used
  , (d.blocks-b.hwm+1)*&bls/1024/1024 shrink_poss
  , ((b.hwm-1)*&bls)/1024/1024 HWMMB
FROM
    sys.dba_data_files d
  , v$datafile v
  , ( select file_id, SUM(bytes) bytes
      from sys.dba_free_space
      GROUP BY file_id) s
  ,     ( select file_id, max(block_id+blocks) hwm
      from dba_extents
      group by file_id ) b  
WHERE
      (s.file_id (+)= d.file_id)
  AND (d.file_id = b.file_id)
  AND (d.file_name = v.name)
  AND (d.tablespace_name = DECODE(UPPER('&1'), '', d.tablespace_name, UPPER('&1')))
UNION
SELECT
    d.tablespace_name                       tablespace 
  , d.file_name                             filename
--  , d.file_id                               file_id
  , d.bytes/1024/1024                       filesize
  , NVL(t.bytes_cached, 0)/1024/1024        used
  , TRUNC((t.bytes_cached / d.bytes) * 100) pct_used
  , (d.blocks-b.hwm+1)*&bls/1024/1024 shrink_poss
  , ((b.hwm-1)*&bls)/1024/1024 HWMMB
FROM
    sys.dba_temp_files d
  , v$temp_extent_pool t
  , v$tempfile v
  , ( select file_id, max(block_id+blocks) hwm
      from dba_extents
      group by file_id ) b 
WHERE 
      (t.file_id (+)= d.file_id)
  AND (d.file_id = b.file_id)
  AND (d.file_id = v.file#)
  AND (d.tablespace_name = DECODE(UPPER('&1'), '', d.tablespace_name, UPPER('&1')));

PROMPT +------------------------------------------------------------------------+
PROMPT | Lista de script para resize datafile pasado el HWM....                 |
PROMPT +------------------------------------------------------------------------+  

COLUMN tablespace_name NEW_VALUE TBS NOPRINT;
COLUMN ScriptResize             FORMAT a140                             HEADING 'Script Resize'

SELECT /*+ ordered */ d.tablespace_name, 'ALTER DATABASE DATAFILE '''||d.file_name||''' RESIZE '||ROUND(((b.hwm-1)*&bls)/1024/1024)||'M;  ' "ScriptResize"
FROM
    sys.dba_data_files d
  ,     ( select file_id, max(block_id+blocks) hwm
      from dba_extents
      group by file_id ) b  
WHERE
    (d.file_id = b.file_id)
        and d.tablespace_name = DECODE(UPPER('&1'), '', d.tablespace_name, UPPER('&1'))
UNION
SELECT /*+ ordered */ d.tablespace_name, 'ALTER DATABASE DATAFILE '''||d.file_name||''' RESIZE '||ROUND(((b.hwm-1)*&bls)/1024/1024)||'M;  ' "ScriptResize"
FROM
    sys.dba_temp_files d
  , ( select file_id, max(block_id+blocks) hwm
      from dba_extents
      group by file_id ) b 
WHERE 
    (d.file_id = b.file_id)
 and d.tablespace_name = DECODE(UPPER('&1'), '', d.tablespace_name, UPPER('&1'));