select b.sid,a.event,b.username,b.program,b.module,'kill -9 '||c.spid ser_pid,'alter system kill session '''||b.sid||','||b.serial#||''';' kill
from v$session_wait a,v$session b, v$process c
where a.event <> 'SQL*Net message from client'
and a.sid = b.sid
and c.addr = b.paddr
and a.event in (
'single-task message',
'buffer busy waits',
'free buffer waits',
'cursor: pin S wait on X',
'cursor: pin X',
'db file scattered read',
'enq: HW - contention',
'enq: SS - contention',
'enq: ST - contention',
'enq: TX - row lock contention',
'enqueue',
'enq: SQ - contention',
'HS message to agent',
'kksfbc child completion',
'library cache load lock',
'library cache lock',
'library cache pin',
'pipe get',
'read by other session',
'SQL*Net message from dblink',
'TCP Socket (KGAS)',
'SQL*Net message to client',
'latch: session allocation',
'SQL*Net break/reset to client',
'PL/SQL lock timer'
)
order by a.event,b.program
/
