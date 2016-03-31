
column job format 99999
column schema format a15
column fail format 999
set pages 100
set line 200
select job, substr(SCHEMA_USER,1,12) schema,
       substr(TO_CHAR(NEXT_DATE,'DAY DD-MON-YY'),1,19) Next_Day, 
       NEXT_SEC, BROKEN b,
       FAILURES fail, substr(what,1,15) what, 
       decode(SUBSTR(INTERVAL,30,30),null,SUBSTR(INTERVAL,1,30),
                 SUBSTR(INTERVAL,30,30)) INTERVAL
from dba_jobs
ORDER BY NEXT_DATE, NEXT_SEC
/