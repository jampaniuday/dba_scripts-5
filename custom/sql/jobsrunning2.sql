col SESION format a15
col kill format a50
col log_user format a11
col what format a45
SELECT v.program,j.LOG_USER,v.sid, r.job, r.this_date, r.this_sec, j.failures,
SUBSTR(what,1,75) what,'alter system kill session '''||v.sid||','||v.serial#||''';' kill
FROM dba_jobs_running r,dba_jobs j,v$session v
WHERE r.job = j.job
and   r.sid = v.sid
order by THIS_DATE,  THIS_SEC
/