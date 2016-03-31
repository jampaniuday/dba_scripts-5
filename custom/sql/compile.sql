COLUMN owner format a1 noprint
COLUMN object_name format a1 noprint
COLUMN order_col format a1 noprint
COLUMN cmd format a132

SET heading OFF
SET pagesize 0
SET linesize 180
SET echo OFF
SET feedback OFF

SPOOL $HOME/recompile.SQL

SELECT 'set echo on'
  FROM dual;

SELECT 'spool $HOME/recompile.lis'
  FROM dual;

SELECT DISTINCT 'alter session set current_schema=' ||
                owner ||
                ';' cmd,
                owner,
                1 order_col,
                NULL object_name
  FROM DBA_OBJECTS
 WHERE status = 'INVALID'
   AND object_type IN ('PACKAGE',
                       'PACKAGE BODY',
                       'VIEW',
                       'PROCEDURE',
                       'FUNCTION',
                       'TRIGGER')
   AND OWNER NOT IN ('SYS','SYSTEM','DBSNMP','OUTLN')                                      
UNION
SELECT 'ALTER ' ||
       DECODE (
          object_type,
          'PACKAGE BODY', 'PACKAGE',
          object_type
       ) ||
       ' ' ||
       owner ||
       '.' ||
       object_name ||
       ' COMPILE' ||
       DECODE (
          object_type,
          'PACKAGE BODY', ' BODY',
          ''
       ) ||
       ';' cmd,
       owner,
       2 order_col,
       object_name
  FROM DBA_OBJECTS outer
 WHERE status = 'INVALID'
   AND object_type IN ('PACKAGE',
                       'PACKAGE BODY',
                       'VIEW',
                       'PROCEDURE',
                       'FUNCTION',
                       'TRIGGER')
   AND OWNER NOT IN ('SYS','SYSTEM','DBSNMP','OUTLN')                                      
   AND  ( object_type <>
             'PACKAGE BODY'
       OR NOT EXISTS ( SELECT NULL
                         FROM DBA_OBJECTS
                        WHERE owner =
                                 outer.owner
                          AND object_name =
                                 outer.object_name
                          AND object_type =
                                 'PACKAGE'
                          AND status =
                                 'INVALID')
          )
 ORDER BY 2, 3, 4
/


select 'exit;' from dual;

SELECT 'SPOOL OFF;'
  FROM dual;

SPOOL OFF;

SET heading ON
SET feedback ON
SET pagesize 9999
SET linesize 80

@$HOME/recompile.SQL