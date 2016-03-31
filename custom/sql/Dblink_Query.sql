SET TERMOUT OFF;
COLUMN current_instance NEW_VALUE current_instance NOPRINT;
SELECT rpad(instance_name, 17) current_instance FROM v$instance;
SET TERMOUT ON;

PROMPT 
PROMPT +------------------------------------------------------------------------+
PROMPT | Report   : Dblink del schema indicado..                                |
PROMPT | Instance : &current_instance                                           |
PROMPT +------------------------------------------------------------------------+

SET ECHO        OFF
SET FEEDBACK    6
SET HEADING     ON
SET LINESIZE    180
SET PAGESIZE    50000
SET TERMOUT     ON
SET TIMING      OFF
SET TRIMOUT     ON
SET TRIMSPOOL   ON
SET VERIFY      OFF

CLEAR COLUMNS
CLEAR BREAKS
CLEAR COMPUTES

COLUMN DB_LINK             FORMAT a30            HEADING 'DB_LINK_NAME'
COLUMN USERNAME            FORMAT a30            HEADING 'DB_LINK_NAME'
COLUMN HOST	               FORMAT a30            HEADING 'DB_LINK_NAME'
COLUMN CREATED             FORMAT a30            HEADING 'DB_LINK_NAME'

COLUMN current_schema NEW_VALUE current_schema NOPRINT;
select OWNER current_schema, DB_LINK, USERNAME, HOST, CREATED from dba_db_links
where owner='&Inserta_Schema';

PROMPT +------------------------------------------------------------------------+
PROMPT | Dblink del schema: &current_schema                                     |
PROMPT +------------------------------------------------------------------------+