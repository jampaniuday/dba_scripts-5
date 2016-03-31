-- status de job x job

col interval for a30
col what for a80
col user for a20
col THIS_DATE for a19
col last_date for a19
col next_date for a19
alter session set nls_date_format='dd-mm-yyyy hh24:mi:ss';
select job,schema_user "user",FAILURES,last_date,this_date,next_date,broken,interval,what
from dba_jobs where upper (what) like upper ('%&jobwhat%')
order by what, job;