-- status de job x job

col interval for a30
col what for a60
col user for a10
col THIS_DATE for a10
col last_date for a10
col next_date for a10

alter session set nls_date_format='dd-mm-yyyy hh24:mi:ss';

select job,schema_user "user",FAILURES,last_date,this_date,next_date,broken,interval,what
from sys.dba_jobs
where job='&numero_Job'
order by job;
