SET TERMOUT OFF;
COLUMN current_instance NEW_VALUE current_instance NOPRINT;
SELECT rpad(instance_name, 17) current_instance FROM v$instance;
SET TERMOUT ON;

PROMPT 
PROMPT +------------------------------------------------------------------------+
PROMPT | Report         : Descripcion Espacio Ocupado por la Tabla              |
PROMPT | Instance       : &current_instance                                     |
PROMPT +------------------------------------------------------------------------+

PROMPT 
ACCEPT schema   CHAR PROMPT 'Enter schema     : '
ACCEPT tab_name CHAR PROMPT 'Enter table name : '

SET ECHO        OFF
SET FEEDBACK    6
SET HEADING     ON
SET LINESIZE    256
SET PAGESIZE    50000
SET TERMOUT     ON
SET TIMING      OFF
SET TRIMOUT     ON
SET TRIMSPOOL   ON
SET VERIFY      OFF

CLEAR COLUMNS
CLEAR BREAKS
CLEAR COMPUTES

BREAK ON report

declare
TOTAL_BLOCKS number;
TOTAL_BYTES number;
UNUSED_BLOCKS number;
UNUSED_BYTES number;
LAST_USED_EXTENT_FILE_ID number;
LAST_USED_EXTENT_BLOCK_ID number;
LAST_USED_BLOCK number;
begin
dbms_space.unused_space(segment_owner              => UPPER('&schema'),
						segment_name               => UPPER('&tab_name'),
						segment_type               => UPPER('TABLE'),
                        total_blocks               => TOTAL_BLOCKS,
                        total_bytes                => TOTAL_BYTES,
                        unused_blocks              => UNUSED_BLOCKS,
                        unused_bytes               => UNUSED_BYTES,
                        last_used_extent_file_id   => LAST_USED_EXTENT_FILE_ID,
                        last_used_extent_block_id  => LAST_USED_EXTENT_BLOCK_ID,
                        last_used_block            => LAST_USED_BLOCK);

dbms_output.put_line('OBJECT_NAME = &tab_name');
dbms_output.put_line('-----------------------------------');
dbms_output.put_line('TOTAL_BLOCKS = ' || TOTAL_BLOCKS);
dbms_output.put_line('TOTAL_BYTES = ' || TOTAL_BYTES);
dbms_output.put_line('UNUSED_BLOCKS = ' || UNUSED_BLOCKS);
dbms_output.put_line('UNUSED BYTES = ' || UNUSED_BYTES);
dbms_output.put_line('LAST_USED_EXTENT_FILE_ID = ' || LAST_USED_EXTENT_FILE_ID);
dbms_output.put_line('LAST_USED_EXTENT_BLOCK_ID = ' || LAST_USED_EXTENT_BLOCK_ID);
dbms_output.put_line('LAST_USED_BLOCK = ' || LAST_USED_BLOCK);
end;
/