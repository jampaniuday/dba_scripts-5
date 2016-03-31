/* Formatted on 05/06/15 11:29:24 a.m. (QP5 v5.256.13226.35538) Moficado por Abner Aguilar <abner.aguilar@telefonica.com> - Fecha - OT - */
SELECT SESSION_ID, LOCKED_MODE
  FROM V$LOCKED_OBJECT
 WHERE OBJECT_ID =
          (SELECT OBJECT_ID
             FROM DBA_OBJECTS
            WHERE     OBJECT_NAME = UPPER ('&objeto')
                  AND OBJECT_TYPE = 'TABLE'
--                  AND OWNER = 'SWBAPPS'
)
/