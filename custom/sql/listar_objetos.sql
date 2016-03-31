-- | Example	: @listar_objetos
-- | 			  &Lista_Objetos colocar: 'objeto1','ojetos2','ojeto3'....
-- | -------------------------------------------------------------------------

SET VERIFY OFF
SET LINESIZE 200

COLUMN Schema FORMAT A20
COLUMN Nombre FORMAT A30
COLUMN Creado FORMAT A20
COLUMN Tipo_Objeto FORMAT A20

select owner Schema,object_name Nombre,timestamp Creado,object_type Tipo_Objeto
from SYS.dba_objects
where object_name
in(&Lista_Objetos)
order by object_type, object_name;