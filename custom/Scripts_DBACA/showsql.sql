set pause ...more 
set pagesize 24 
set linesize 132 
set numwidth 5 
column SID_Serial format a10 
column Idle format a5 
column LkMins format 999 
column Wait format a5 
-- showsql---

set pause on 
col module format a20 
break on SID_Serial SKIP 1 on status 

select 
  vs.sid||','||vs.serial# SID_Serial, 
  vs.status, vs.module, st.sql_text 
from  V$SESSION vs, V$SQLTEXT st 
 where st.address=decode(vs.sql_address, hextoraw('00'), vs.prev_sql_addr, vs.sql_address) 
  and st.hash_value=decode(vs.sql_hash_value, 0, vs.prev_hash_value, vs.sql_hash_value) 
  and vs.paddr not in (select paddr from V$BGPROCESS)  -- exclude background processes 
  and vs.SID='&SID'
order by vs.status, vs.last_call_et, 1, st.piece 
/ 

set pause off 
set numwidth 10 
clear columns 
clear breaks 