col file_name format A64
col SizeMB    format 999,999,999
col SizeMBusr format 999,999,999
SELECT tablespace_name, file_id ID, file_name,
 round(sum(BYTES/1024/1024),0) SizeMB, round(sum(MAXBYTES/1024/1024),0) MaxSizeMB
-- round(sum(USER_BYTES/1024/1024),0) SizeMBusr
 FROM dba_data_files b
 WHERE tablespace_name = '&ts'
 GROUP BY b.tablespace_name, b.file_name, b.file_id
 ORDER BY b.file_name;