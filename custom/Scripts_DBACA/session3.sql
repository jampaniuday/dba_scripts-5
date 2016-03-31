col TERMINAL format a15
col SID_Serial format 999999
col SID_Serial format A15
col Ora_User format a15
select substr(vp.username, 1, 8) "O/S Usr", vp.spid "Srvr PID", vs.process "Clint PID", 
  vs.sid||','||vs.serial# SID_Serial, 
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
  and vs.paddr not in (select paddr from V$BGPROCESS)  -- exclude background processes
  and vs.status='ACTIVE' 
order by 7, 9 -- Status, Idle Time 
/ 
