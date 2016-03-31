set linesize 600
set pagesize 600
set arraysize 999
col ownet format a10
--where table_name like upper('%&nombre_tabla%')
select table_owner,table_name,PARTITION_NAME, TABLESPACE_NAME, AVG_SPACE,NUM_ROWS,LAST_ANALYZED
from DBA_TAB_PARTITIONS
where table_name= upper('&nombre_tabla')
order by table_owner, PARTITION_NAME;