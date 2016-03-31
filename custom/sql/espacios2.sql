col Datafile_Name for a70
prompt ALTER DATABASE DATAFILE 'Datafile_Name' RESIZE ###MB; 

select file_name Datafile_Name,file_id,tablespace_name,bytes/1024/1024 sizeMB from DBA_DATA_FILES
where tablespace_name='&TBS_NAME' order by file_name;