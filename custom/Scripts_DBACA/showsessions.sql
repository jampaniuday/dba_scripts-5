set pause ...more 
set pagesize 24 
set linesize 132 
set numwidth 5 
column SID_Serial format a10 
column Idle format a5 
column LkMins format 999 
column Wait format a5 

select substr(vp.username, 1, 8) "O/S Usr", vp.spid "Srvr PID", vs.process "Clint PID", 
  vs.sid||','||vs.serial# SID_Serial, 
  vs.terminal, substr(vs.username, 1, 10) "Ora User", 
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
  and vs.paddr not in (select paddr from V$BGPROCESS)  -- exclude background processes 
order by 7, 9 -- Status, Idle Time 
/ 

-- set pause on 
-- col module format a20 
-- break on SID_Serial SKIP 1 on status 

-- select 
--   vs.sid||','||vs.serial# SID_Serial, 
--   vs.status, vs.module, st.sql_text 
-- from  V$SESSION vs, V$SQLTEXT st 
-- where st.address=decode(vs.sql_address, hextoraw('00'), vs.prev_sql_addr, vs.sql_address) 
--   and st.hash_value=decode(vs.sql_hash_value, 0, vs.prev_hash_value, vs.sql_hash_value) 
--   and vs.paddr not in (select paddr from V$BGPROCESS)  -- exclude background processes 
-- order by vs.status, vs.last_call_et, 1, st.piece 
-- / 

set pause off 
set numwidth 10 
clear columns 
clear breaks 