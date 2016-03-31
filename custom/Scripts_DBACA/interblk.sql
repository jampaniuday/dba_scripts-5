-- para checar interbloqueos 
-- Sergio Cruz

col LOGON_TIME format DATE heading "LOGON_TIME"
col SID_Serial format a45
col A.USERNAME format a15
compute sum of count(*) on report
break on report

select distinct A.sid,B.type,A.username,' alter system kill session   '''||A.sid||','||A.serial#||''';  ' "SID_Serial",A.status,B.block,A.lockwait,
        substr(to_char(A.LOGON_TIME, 'dd/mon hh:mi'), 1, 12) "Logon Time"
 FROM V$SESSION A,V$LOCK B
 WHERE A.SID=B.SID
 AND USERNAME IS NOT NULL
 AND lockwait is not null
/


SELECT * FROM DBA_BLOCKERS;

--SELECT * FROM DBA_WAITERS;


-- select distinct A.sid,A.username,A.status,B.block,A.lockwait,
--        substr(to_char(A.LOGON_TIME, 'dd/mon hh:mi'), 1, 12) "Logon Time"
-- FROM V$SESSION A,V$LOCK B
-- WHERE A.SID=B.SID
-- AND USERNAME IS NOT NULL
-- AND lockwait is not null
-- /

