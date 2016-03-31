set define on
exec dbms_stats.GATHER_INDEX_STATS(user,trim('&INDEX'),null,&SAMPLE,null,null,null,5,'AUTO');


