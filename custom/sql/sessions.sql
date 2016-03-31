col SID_Serial format A20
col osuser     format A20
col username   format A20
col status     format A10
col machine    format A27
col module     format A50
col program    format A50
--col COMMAND    format 9999999
col Cantidad   format 999,999,999,999
col num_rows   format 999,999,999,999
set pagesize 9999
set linesize 9999
set arraysize 500
set long 9999
--set timing on
alter session set nls_date_format='dd-mm-yyyy hh24:mi:ss';
--COMPUTE count of sid ON report;
clear breaks
break on report
select sid||','||serial# "SID_Serial",
       osuser OSUSER, username USERNAME, status STATUS ,
       to_char(LOGON_TIME, 'Dy DD-Mon-YYYY HH24:MI:SS') LOGON_TIME,
       machine MACHINE, module MODULE, program PROGRAM
       --, command COMMAND
from v$session
where status='ACTIVE'
--where username='OPS$XPFACTUR'
--order by status ASC
order by machine,osuser,username,logon_time --osuser, module
;