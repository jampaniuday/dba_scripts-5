-- File Name    : list_table_mayor.sql
-- Author       : Abner Aguilar
-- Description  : Lista el top indicado o en su defecto 10, de las tablas de mayor consumo 
--				  de espacio del schema especificado o en su defecto todos los schemas.
--				  Tambien puede filtrar las tablas particionadas agregando un 3er parametro 
--				  el cual soporta ("",no), cabe notar que "" o vacio muestra las tablas particionadas
--				  y solo "no" las elimina de la lista.
-- Requirements : acceder con sysdba o system.
-- Call Syntax  : @list_table_mayor (top) (schema) (part)
-- Example		: @list_table_mayor 					     -- Muestra el top 10 de las tablas de mayor consumo de todos los schemas, con tablas particionadas
--				  @list_table_mayor 15 ""  				     -- Muestra el top 15 de las tablas de mayor consumo de todos los schemas, con tablas particionadas
--				  @list_table_mayor 15 app_aplicaciones      -- Muestra el top 15 de las tablas de mayor consumo del schema app_aplicaciones, con tablas particionadas
--				  @list_table_mayor "" app_aplicaciones      -- Muestra el top 10 de las tablas de mayor consumo del schema app_aplicaciones, con tablas particionadas
--				  @list_table_mayor "" app_aplicaciones no   -- Muestra el top 10 de las tablas de mayor consumo del schema app_aplicaciones, sin tablas particionadas
--				  @list_table_mayor "" "" no   				 -- Muestra el top 10 de las tablas de mayor consumo de todos los schemas, sin tablas particionadas
-- Last Modified: 06-MARZO-2015
-- -------------------------------------------------------------------------------------------------------------------------------------------------------------------

SET TERMOUT 	OFF
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

COLUMN owner FORMAT A20 JUSTIFY CENTER
COLUMN table_name FORMAT A30 HEADING 'TABLE NAME' JUSTIFY CENTER
COLUMN MB FORMAT 999,999,999 HEADING 'ESPACIO MB' JUSTIFY CENTER
COLUMN num_rows FORMAT 999,999,999,999 HEADING 'NUM. REGISTROS' JUSTIFY CENTER
COLUMN "LAST ANALYZED" JUSTIFY CENTER
COLUMN partitioned FORMAT A4 HEADING 'PART' JUSTIFY RIGHT

ACCEPT 1 PROMPT "Ingrese SCHEMA a validar (Schema/ALL) Default(ALL): " Default ALL
ACCEPT 2 PROMPT "Ingrese el TOP a listar (#) Default(10): " Default 10
ACCEPT 3 PROMPT "Ingrese el nombre de la tabla a mostrar (Table_name) Default(ALL): " Default ALL
ACCEPT 4 PROMPT "Mostrar tablas particionadas - (S/N) Default(S): " Default S

select a.owner, a.table_name, a.MB, b.num_rows, b.partitioned, NVL(to_char(b.last_analyzed,'dd/mm/yyyy hh:mm:ss PM'), '(****SIN ANALYZE*****)') "LAST ANALYZED"
from
(
	select owner, table_name, MB from
	(
		SELECT owner, table_name, round(sum(bytes)/1024/1024) MB
		FROM
		(
			SELECT segment_name table_name, owner, bytes
			FROM dba_segments
			WHERE segment_type in ('TABLE','TABLE PARTITION','TABLE SUBPARTITION')
				AND owner = Decode(Upper('&1'),'ALL',owner,Upper('&1'))
				AND segment_name = Decode(Upper('&3'),'ALL',segment_name,Upper('&3'))
			UNION ALL
			SELECT i.table_name, i.owner, s.bytes
			FROM dba_indexes i, dba_segments s
			WHERE s.segment_name = i.index_name
				AND s.owner = i.owner
				AND s.segment_type in ('INDEX','INDEX PARTITION','INDEX SUBPARTITION')
				AND s.owner = Decode(Upper('&1'),'ALL',s.owner,Upper('&1'))
				AND i.table_name = Decode(Upper('&3'),'ALL',i.table_name,Upper('&3'))
			UNION ALL
			SELECT l.table_name, l.owner, s.bytes
			FROM dba_lobs l, dba_segments s
			WHERE s.segment_name = l.segment_name
				AND s.owner = l.owner
				AND s.segment_type = 'LOBSEGMENT'
				AND l.owner = Decode(Upper('&1'),'ALL',l.owner,Upper('&1'))
				AND l.table_name = Decode(Upper('&3'),'ALL',l.table_name,Upper('&3'))
			UNION ALL
			SELECT l.table_name, l.owner, s.bytes
			FROM dba_lobs l, dba_segments s
			WHERE s.segment_name = l.index_name
				AND s.owner = l.owner
				AND s.segment_type = 'LOBINDEX'
				AND l.owner = Decode(Upper('&1'),'ALL',l.owner,Upper('&1'))
				AND l.table_name = Decode(Upper('&3'),'ALL',l.table_name,Upper('&3'))
		) 
		GROUP BY table_name, owner
		order by mb desc
	) 
	where rownum <= Decode(Upper('&2'),'10',10,Upper('&2'))
) a
inner join all_tables b
	on (b.owner = a.owner and b.table_name = a.table_name)
where b.partitioned = Decode(Upper('&4'),'S',b.partitioned,'N','','','')
order by a.mb desc
/
