-- Modificado Abner Aguilar, agregada la ip del server
col "DATOS INSTANCIA" format a50
COLUMN comments FORMAT A15
select
'INSTANCE_NUMBER: '||INSTANCE_NUMBER||chr(10)||
'INSTANCE_NAME: '||INSTANCE_NAME||chr(10)||
'HOST_NAME: '||HOST_NAME||chr(10)||
'IP_ADDRESS: '||(SELECT UTL_INADDR.get_host_address from dual)||chr(10)||
'VERSION: '||VERSION||chr(10)||
'STARTUP_TIME: '||to_char(STARTUP_TIME,'dd-mm-yyyy hh24:mi:ss')||chr(10)||
'STATUS: '||STATUS||chr(10)||
'PARALLEL: '||PARALLEL||chr(10)||
'THREAD#: '||THREAD#||chr(10)||
'ARCHIVER: '||ARCHIVER||chr(10)||
'LOG_SWITCH_WAIT: '||LOG_SWITCH_WAIT||chr(10)||
'LOGINS: '||decode(LOGINS,'RESTRICTED',' <<<<<===== RESTRICTED =====>>>>> ',LOGINS) ||chr(10)||
'SHUTDOWN_PENDING: '||SHUTDOWN_PENDING "DATOS INSTANCIA"
from v$instance
union
select
'DATABASE NAME: '||NAME||chr(10)||
'LOG MODE: '||LOG_MODE 
from v$database;
select comments, version, bundle_series
from sys.registry$history
where bundle_series = 'PSU'
order by action_time;