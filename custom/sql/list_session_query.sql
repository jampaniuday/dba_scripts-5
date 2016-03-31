COL event format A30
COL username format A18
COL "kill Session" format A53
select username, status, event, seconds_in_wait, LOGON_TIME, LAST_CALL_ET, 
'alter system kill session '''||sid||','||serial#||',@'||inst_id||''' immediate;' "Kill Session"
from gv$session
where username = UPPER(nvl('&username',username))
and sid = nvl('&sid',sid)
and sql_id = nvl('&sql_id',sql_id)
and event like nvl('%&event%','%more data from%')
order by LOGON_TIME;