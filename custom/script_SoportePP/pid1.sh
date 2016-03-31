echo " set linesize 9999 pagesize 9999 UND off;
column sid format 99999;
column serial# format 999999;
column spid  format 99999;
column LOGON_TIME format a20;
column USERNAME format a20;
column MACHINE format a30;
column OSUSER format a15;
column terminal format a10;
select  distinct a.sid ,a.serial#, b.spid,status , a.LOGON_TIME,a.USERNAME,A.MACHINE,A.OSUSER,A.TERMINAL , sq.sql_text, a.program
from v\$session a, v\$process  b ,V\$SQL SQ
where a.PADDR= b.ADDR
and a.SQL_ADDRESS=sq.ADDRESS
and  b.SPID  in($1) order by 1;"| sqlplus -S -L / as sysdba;
