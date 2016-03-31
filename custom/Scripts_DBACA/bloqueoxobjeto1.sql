/* Formatted on 05/06/15 11:26:13 a.m. (QP5 v5.256.13226.35538) Moficado por Abner Aguilar <abner.aguilar@telefonica.com> - Fecha - OT - */
-- bloqueos por objeto
-- sergio Cruz

COL SID_Serial FORMAT 999999
COL SID_Serial FORMAT A20
COL owner FORMAT A20
COL object FORMAT A30
COL type FORMAT A15
COL KILLSESSION FORMAT A50

ALTER SESSION SET nls_date_format='dd-mm-yyyy hh24:mi:ss';

PROMPT " alter system kill session '  ';

SELECT    ' alter system kill session  '''
       || C.sid
       || ','
       || c.serial#
       || ''';  '
          KILLSESSION,
       d.owner,
       d.object,
       d.TYPE,
       c.status,
       c.username,
       c.LOGON_TIME
  FROM v$access d, v$session c
 WHERE d.sid = c.sid AND object = ('&objeto')
--and status='ACTIVE'
;