column Def format 999,999,999,999.00 heading "DEF"
column free format 999,999,999.00 heading "LIBRES"
column ocupado format 999,999,999,999.00 heading "OCUPADO"
column Por format 999,999,999.00 heading "%"
select sum(a.bytes)/1024/1024  Def,sum(b.bytes)/1024/1024 free,
(sum(a.bytes)-sum(b.bytes))/1024/1024 Ocupado,(sum(b.bytes)/sum(a.bytes))*100 Por
from sys.dba_data_files a, sys.dba_free_space b
where a.tablespace_name = b.tablespace_name;