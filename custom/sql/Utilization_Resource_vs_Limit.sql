SELECT resource_name ,current_utilization, limit_value ,round((current_utilization/limit_value)*100,2) as P_Utilizacion
FROM v$resource_limit where resource_name in('processes','sessions');
