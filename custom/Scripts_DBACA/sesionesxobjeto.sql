/* Formatted on 05/06/15 11:26:48 a.m. (QP5 v5.256.13226.35538) Moficado por Abner Aguilar <abner.aguilar@telefonica.com> - Fecha - OT - */
COL owner FOR a20
COL OBJECT FOR a30
COL TYPE FOR a10

SELECT d.*,
       c.status,
       c.USERNAME,
       SUBSTR (TO_CHAR (C.LOGON_TIME, 'dd/mon hh:mi'), 1, 12) "Logon Time",
       c.MACHINE
  FROM v$access d, v$session c
 WHERE d.sid = c.sid AND object LIKE UPPER ('%&objeto%');