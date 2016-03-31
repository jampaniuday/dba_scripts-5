col TOTAL_MB format 999,999,999
col FREE_MB format 999,999,999
col PERC format 990.00
select NAME,TOTAL_MB,FREE_MB,FREE_MB/TOTAL_MB*100 PERC from v$asm_diskgroup;