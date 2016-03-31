col s.sql_text format A65
select /*+ ORDERED */ a.osuser,
a.username,
a.sid,
a.serial#,
p.spid,
s.sql_text,
s.sql_id
FROM v$process p,
v$session a,
v$sqltext s,
v$sqlarea sa
where s.address = a.prev_sql_addr
AND p.addr = a.paddr
AND s.address=sa.address
AND a.sid in(&Isid)
--AND a.serial# = 163
AND a.username is not null
order by a.username, a.osuser, a.sid, s.piece
;