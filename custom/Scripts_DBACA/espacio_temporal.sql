col FILE_NAME format A40

select FILE_NAME, TABLESPACE_NAME,RELATIVE_FNO from dba_temp_files;

set linesize 9999
set  arraysize 999
col SID_Serial format 999999
col SID_Serial format A43
col tablespace format A10
col username format A15
col user format A15
col SEGRFNO# format 99999
col OSUSER format A15


SELECT   b.tablespace,b.SEGRFNO#,b.segfile#, b.segblk#, b.blocks, ' alter system kill session   '''||a.sid||','||a.serial#||''';  ' "SID_SERIAL",
            a.username, a.osuser, a.status
   FROM     v$session a,v$sort_usage b
   WHERE    a.saddr = b.session_addr
   ORDER BY b.tablespace, b.segfile#, b.segblk#, b.blocks,a.status;