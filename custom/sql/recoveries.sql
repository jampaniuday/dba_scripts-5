set time on
set linesize 9999
set linesize 600
set pagesize 600
set arraysize 999
col ownet format a10
select * from V$fast_start_transactions;
col percent format 999,999.999999
select state, undoblocksdone, undoblockstotal, (undoblocksdone / undoblockstotal * 100) as percent
from v$fast_start_transactions
order by percent asc;
commit work;
SELECT usn, state, undoblockstotal "Total", undoblocksdone "Done",
undoblockstotal-undoblocksdone "ToDo",
DECODE(cputime,0,'unknown',SYSDATE+(((undoblockstotal-undoblocksdone) / (undoblocksdone / cputime)) / 86400))
"Finish at"
FROM v$fast_start_transactions;
commit work;