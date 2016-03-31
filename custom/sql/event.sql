col p1text format a15
col p2text format a15
col p3text format a15
col event format a35
col program format a50
col username format a20
col machine format a50
col module format a48
col program format a48
col WAIT_CLASS format a15
set lines 1000
set pages 1000
select nvl(b.module,b.program) module,nvl(b.username,'SYS') username,a.*,b.machine,b.program
from v$session_wait a,
     v$session b
where a.event <> 'SQL*Net message from client'
and a.sid = b.sid
order by 2,b.program
/