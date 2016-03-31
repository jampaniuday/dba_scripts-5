
set pause ...more 
set pagesize 24 
set linesize 9999
set numwidth 5 
column SID_Serial format a45
column Idle format a5 
column LkMins format 999 
column Wait format a5 
column Ora_User format a17
column TERMINAL format a17
prompt alter system kill session   '   '; 
select substr(vp.username, 1, 8) "O/S Usr", vp.spid "Srvr PID", vs.process "Clint PID", 
  ' alter system kill session   '''||vs.sid||','||vs.serial#||''';  ' "SID_Serial" , 
  vs.terminal, substr(vs.username, 1, 10) "Ora_User", 
  vs.status, substr(to_char(vs.LOGON_TIME, 'dd/mon hh:mi'), 1, 12) "Logon Time", 
  to_char(trunc(sysdate) + (vs.LAST_CALL_ET/(24*3600)), 'hh24:mi') "Idle", 
  vl2.sid "LkSID", 
  to_char(trunc(sysdate) + (vl1.ctime/(24*3600)), 'hh24:mi') "Wait" 
from V$PROCESS vp, V$SESSION vs, V$LOCK vl1, V$LOCK vl2 
where vp.addr = vs.paddr 
  and vs.lockwait = vl1.kaddr(+) 
  and vl1.id1=vl2.id1(+) 
  and vl2.lmode(+)!=0 
  and vs.status='ACTIVE'
  and vs.username like upper('%&username%')
  and vs.paddr not in (select paddr from V$BGPROCESS)  -- exclude background processes 
order by 7, 9 -- Status, Idle Time 
/ 

set pause off 
set numwidth 10 
clear columns 
clear breaks

