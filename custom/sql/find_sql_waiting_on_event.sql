select event, sql_id, count(*) Cant,
avg(time_waited) avg_time_waited
from gv$active_session_history
where event like nvl('%&event'%,'%more data from%')
having count(*) >= nvl(&MayorA,0)
order by event, 3
/