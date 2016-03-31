set linesize 140
col TRIGGERING_EVENT for a20 trunc
col owner for a15 
col trigger_name for a25 
col TRIGGER_NAME for a30
col descr for a30 trunc
col TABLE_NAME for a15
select owner,TABLE_NAME,status,trigger_name,trigger_type,triggering_event,substr(description,1,30) descr 
from DBA_TRIGGERS where TABLE_NAME =upper('&tabla') order by 2,1,3;