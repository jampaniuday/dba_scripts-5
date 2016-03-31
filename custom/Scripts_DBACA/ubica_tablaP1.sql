set linesize 600
set pagesize 600
set arraysize 999
col ownet format a10
select table_name,PARTITION_NAME, TABLESPACE_NAME, AVG_SPACE,NUM_ROWS,LAST_ANALYZED
from DBA_TAB_PARTITIONS 
where table_name=('&nombre_tabla')
order by PARTITION_NAME;
