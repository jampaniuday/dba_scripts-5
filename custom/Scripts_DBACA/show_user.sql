set echo off feedback off heading off
set pagesize 0
set linesize 180
col username for a25
col osuser for a12
col serial# for 99999
col sid for 99999
col spid for a8
col module for a10 trunc
col start_time for a20
col machine for a20 trunc
select 'Show users ordered by logon_time, username' from dual;
select 'OSUSER       OSPID    USERNAME                  SID   SERIAL#  LOGON_TIME             STATUS MACHINE              MODULE' from dual;
select '------------ -------- ------------------------  ----- -------- ------------------- --------- -------------------- ----------' from dual;
select s.osuser,p.spid,s.username,s.sid,s.serial#,to_char(s.logon_time,'Dy dd Mon HH24:MI:SS') start_time,s.status,s.machine,s.MODULE 
from V$PROCESS p,V$SESSION s where s.paddr = p.addr and s.username is not null order by logon_time,1;
--select username,sid,serial#,to_char(logon_time,'Dy dd Mon HH24:MI:SS') start_time,status,machine from V$SESSION order by logon_time,1;

