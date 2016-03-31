set linesize 600
set pagesize 600
set arraysize 999
col ownet format a10
select table_name, owner, TABLESPACE_NAME, AVG_SPACE,NUM_ROWS,LAST_ANALYZED
from DBA_ALL_TABLES
where table_name like upper('%&nombre_tabla%');
@ubica_tablap