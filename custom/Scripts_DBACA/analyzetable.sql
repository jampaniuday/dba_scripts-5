set define on
exec dbms_stats.gather_table_stats(user,trim('&TABLE'),null,&SAMPLE,null,'FOR ALL COLUMNS SIZE AUTO',5,'all',true);
