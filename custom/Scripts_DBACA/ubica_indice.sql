set linesize 600
set pagesize 600
set arraysize 999
col owner format a10
col TABLE_OWNER format a10
select owner,index_name,table_owner,table_name,tablespace_name
from all_indexes
where index_name like upper('%&nombre_indice%');