-- col osuser format A20 "OSUSER"
-- col osuser format A20 "OSUSER"
-- col SID_Serial format 999999
col SID_Serial format A20
col osuser format A20
col username format A20
col machine format A30
col Cantidad format 999,999,999,999
col num_rows format 999,999,999,999
set pagesize 9999
set linesize 9999
set arraysize 500
set long 9999
--set timi on
alter session set nls_date_format='dd-mm-yyyy hh24:mi:ss';
--COMPUTE SUM of count(*) ON report;
COMPUTE SUM of Cantidad ON report;
clear breaks
break on report
prompt
prompt
prompt
prompt --SESIONES ACTIVAS
select USERNAME, module, machine, count(*) as Cantidad
from v$session
where status='ACTIVE'
group by username, module, machine
order by count(*)
/
prompt
prompt