
-- checa el status de los jobs y los ordena por # de job

col interval for a30
col what for a80
col SCHEMA_USER for a16
col THIS_DATE for a20

select job,schema_user,last_date,this_date,next_date,broken,interval,what
from sys.dba_jobs
order by job;
