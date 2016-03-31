col a  format 999,999,999.00 heading "Tablespace"
col b  format 999,999,999.00 heading "Size"
col c  format 999,999,999.00 heading "Used"
col d  format 999,999,999.00 heading "Free"
col e  format 999,999,999.00 heading "% Free"
col b1 format 999,999,999.00
col c1 format 999,999,999.00
col d1 format 999,999,999.00
compute sum of b on b1
compute sum of c on c1
compute sum of d on d1
set pages 1000
set lines 1000

SELECT
D.TABLESPACE_NAME a,
ROUND((A.BYTES/1024/1024),2) b,
ROUND(((A.BYTES-DECODE(F.BYTES,NULL,0,F.BYTES))/1024/1024),2) c,
ROUND(((A.BYTES/1024/1024)-(A.BYTES-DECODE(F.BYTES,NULL,0,F.BYTES))/1024/1024),2) d,
ROUND((((A.BYTES/1024/1024)-(A.BYTES-DECODE(F.BYTES,NULL,0,F.BYTES))/1024/1024)/(A.BYTES/1024/1024))*100.0,2) e
FROM SYS.DBA_TABLESPACES D,SYS.SM$TS_AVAIL A,SYS.SM$TS_FREE F
WHERE D.TABLESPACE_NAME = A.TABLESPACE_NAME
AND F.TABLESPACE_NAME(+)=D.TABLESPACE_NAME
/*UNION ALL
SELECT d.tablespace_name a,
ROUND((a.bytes / 1024 / 1024),2) b,
ROUND(NVL(t.bytes, 0)/1024/1024,2) c,
ROUND((a.bytes / 1024 / 1024) - (NVL(t.bytes, 0)/1024/1024),2) d,
ROUND(100 - (NVL(t.bytes /a.bytes * 100, 0)),2) e
FROM sys.dba_tablespaces d,
(select tablespace_name, sum(bytes) bytes from dba_temp_files group by tablespace_name) a,
(select tablespace_name, sum(bytes_cached) bytes  from SYS.v_$temp_extent_pool group by tablespace_name) t
WHERE d.tablespace_name = a.tablespace_name(+)
AND d.tablespace_name = t.tablespace_name(+)
AND d.extent_management like 'LOCAL'
AND d.contents like 'TEMPORARY'*/
order by e
/