set lin 120 pages 500
col tablespace_name format a30 trunc
col total_usado_MB for 999,999,999
col Total_Total_MB for 999,999,999
col Total_Disponible_MB for 999,999,999
compute sum of total_usado_MB on report
compute sum of Total_Total_MB on report
compute sum of Total_Disponible_MB on report
compute sum of max_sz_mb on report
break on report
select tablespace_name,
round(sum(total_mb)-sum(free_mb),2) total_usado_MB,
round(sum(total_mb),2) Total_Total_MB,
round(sum(max_mb) - (sum(total_mb)-sum(free_mb)),2) Total_Disponible_MB
from (select tablespace_name,sum(bytes)/1024/1024 free_mb,0 total_mb,0 max_mb from DBA_FREE_SPACE group by tablespace_name
union select tablespace_name,0 current_mb,sum(bytes)/1024/1024 total_mb,sum(decode(maxbytes, 0, bytes, maxbytes))/1024/1024 max_mb
from DBA_DATA_FILES group by tablespace_name) a group by tablespace_name;
