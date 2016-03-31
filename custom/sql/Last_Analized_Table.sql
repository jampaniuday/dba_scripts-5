--SET PAUSE ON
--SET PAUSE 'Press Return to Continue'
SET PAGESIZE 60
SET LINESIZE 300
SET VERIFY OFF
SET HEADING OFF
 
SELECT 
--	   t.owner,
	   t.table_name AS "Table Name", 
--       t.num_rows AS "Rows", 
--       t.avg_row_len AS "Avg Row Len", 
--       Trunc((t.blocks * p.value)/1024) AS "Size KB", 
       to_char(t.last_analyzed,'DD/MM/YYYY HH24:MM:SS') AS "Last Analyzed"
FROM   dba_tables t,
       v$parameter p
WHERE t.owner = Decode(Upper('&1'), 'ALL', t.owner, Upper('&1'))
AND t.table_name =Decode(Upper('&2'), 'ALL', t.table_name, Upper('&2'))
AND   p.name = 'db_block_size'
ORDER by t.owner,t.last_analyzed,t.table_name
/