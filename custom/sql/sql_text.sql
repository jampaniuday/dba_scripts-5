select /*+ ORDERED */ a.osuser,
a.username,
a.sid,
a.serial#,
p.spid,
s.sql_text
FROM v$process p,
v$session a,
v$sqltext s,
v$sqlarea sa
where s.address = a.prev_sql_addr
AND p.addr = a.paddr
AND s.address=sa.address
AND a.sid = '&Sid'
AND a.serial# = '&Serial'
AND a.username is not null
order by a.username, a.osuser, a.sid, s.piece
/