--select * from DBA_JOBS_RUNNING;
-- col what format a80
-- col THIS_DATE format a20
-- select d.*,c.WHAT from DBA_JOBS_RUNNING d, sys.dba_jobs c
-- where d.job=c.job;

---select * from DBA_JOBS_RUNNING;

--col SID_Serial format A17
col SID_Serial 	format A45
col what 		format A70
col USER 		format A10
col THIS_DATE 	format A10
select ' alter system kill session   '''||e.sid||','||e.serial#||''';  ' "SID_Serial", c.SCHEMA_USER "USER",d.JOB,d.FAILURES,d.LAST_DATE,d.LAST_SEC,
d.THIS_DATE,d.THIS_SEC ,c.WHAT 
from v$session e,DBA_JOBS_RUNNING d, sys.dba_jobs c 
where d.job=c.job
and e.sid=d.sid;