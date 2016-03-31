set define on
ACCEPT STOREPROCEDURE CHAR PROMPT 'Tablespace: '
set verify off
set timing off
set feedback off
set pagesize 1000
col proy format a50
col proy2 format a125
col file_name format a200
define 1 = &&STOREPROCEDURE
select bytes/1024/1024 mb,bytes/1024/1024/1024 gb,file_id,
'alter database datafile '||lpad(file_id,4)||' resize '||lpad(to_char(trunc(bytes/1024/1024/1024)*1024+1024),6)||'m;' proy,
file_name,
'alter database datafile '''||file_name||''' resize '||lpad(to_char(trunc(bytes/1024/1024/1024)*1024+1024),6)||'m;' proy2
from dba_data_files where tablespace_name = upper(trim('&1'))
union
select bytes/1024/1024 mb,bytes/1024/1024/1024 gb,file_id,
'alter database tempfile '||lpad(file_id,4)||' resize '||lpad(to_char(trunc(bytes/1024/1024/1024)*1024+1024),6)||'m;' proy,
file_name,
'alter database tempfile '''||file_name||''' resize '||lpad(to_char(trunc(bytes/1024/1024/1024)*1024+1024),6)||'m;' proy2
from dba_temp_files where tablespace_name = upper(trim('&1'))
order by file_name
/
set verify on
set timing on
set feedback on