#!/bin/ksh
#
#   Script para compactar la tabla dada, reconstruir sus indices, particionados o no, y recalcular sus estadisticas
#
#
# Formato llamada:  nohup ./mantenimiento_tablas_ppga.ksh <tabla> &
#

if [ $# -ne 1 ]
then
   echo "Uso del Programa incorrecto: Debe utilizar la siguiente sintaxis: mantenimiento_tablas_ppga.ksh <tabla>"
  exit 1
fi

> nohup.out

tabla=`echo $1|tr [a-z] [A-Z]`
nodo=ppga

echo
echo "[`date +%Y%m%d-%H:%M:%S`] ====  INICIO DEL MANTENIMIENTO DE LA TABLA $tabla  ===="
echo
echo "[`date +%Y%m%d-%H:%M:%S`] Tama+±e la tabla y sus indices antes del mantenimiento:"
echo "set lin 120 pages 500 heading on feedback off
col SEGMENT_NAME format a50
select round(sum(BYTES/1024/1024),2) MB, sum(BLOCKS) Block, sum(EXTENTS) Extent, SEGMENT_TYPE, SEGMENT_NAME
from DBA_SEGMENTS where SEGMENT_NAME like '%${tabla}%'
group by SEGMENT_TYPE, SEGMENT_NAME order by SEGMENT_TYPE, SEGMENT_NAME;
quit"|sqlplus -s ppga/temporal|grep -v "^$"
echo "[`date +%Y%m%d-%H:%M:%S`] Primero se borra los ficheros de una ejecucion anterior."
rm *txt tbs* assm local auto compact* ind* rbd* stats* 2>/dev/null



# Compactado de la tabla:

echo n > assm
echo "set heading off feedback off echo off lin 300 pages 999 long 90000
select 'select dbms_metadata.get_ddl(''TABLESPACE'','''||TABLESPACE_NAME||''') from dual;'
 from DBA_SEGMENTS where SEGMENT_NAME=upper('&tabla') and SEGMENT_TYPE like '%TABLE%' order by TABLESPACE_NAME;
$tabla
quit"|sqlplus -s ${nodo}/temporal|grep dbms_metadata > ddl_tbs_assm.txt
echo "[`date +%Y%m%d-%H:%M:%S`] Generado el fichero para consultar la creacion DDL de los tablespaces de la tabla $tabla:  ddl_tbs_assm.txt"
cat ddl_tbs_assm.txt|while read linea ; do
  tbs=`echo "$linea"|cut -d"'" -f4`
  echo "set heading off feedback off echo off lin 300 pages 999 long 90000
  $linea
  quit"|sqlplus -s ${nodo}/temporal|grep -v "^$" > tbs_assm_$tbs.sql
  grep -i LOCAL tbs_assm_$tbs.sql > local
  grep -i 'SPACE MANAGEMENT AUTO' tbs_assm_$tbs.sql > auto
  [[ -s local ]] && [[ -s auto ]] && echo y > assm
done
if [ $(cat assm) == 'y' ] ; then
echo "set heading off feedback off echo off lin 300 pages 999 long 90000
select 'alter table '||OWNER||'.'||TABLE_NAME||' enable row movement;'
 from DBA_TABLES where TABLE_NAME=upper('&tabla') and OWNER=upper('&nodo');
$tabla
$nodo
select 'alter table '||t.OWNER||'.'||t.TABLE_NAME||' modify partition '||s.PARTITION_NAME||' shrink space compact;'
 from DBA_TABLES t, DBA_SEGMENTS s where t.TABLE_NAME=s.SEGMENT_NAME and t.PARTITIONED='YES'
 and t.TABLE_NAME=upper('&tabla') and t.OWNER=upper('&nodo') order by s.PARTITION_NAME;
$tabla
$nodo
select 'alter table '||t.OWNER||'.'||t.TABLE_NAME||' shrink space compact;'
 from DBA_TABLES t, DBA_SEGMENTS s where t.TABLE_NAME=s.SEGMENT_NAME
 and t.PARTITIONED='NO' and t.TABLE_NAME=upper('&tabla') and t.OWNER=upper('&nodo');
$tabla
$nodo
select 'alter table '||t.OWNER||'.'||t.TABLE_NAME||' modify partition '||s.PARTITION_NAME||' shrink space;'
 from DBA_TABLES t, DBA_SEGMENTS s where t.TABLE_NAME=s.SEGMENT_NAME and t.PARTITIONED='YES'
 and t.TABLE_NAME=upper('&tabla') and t.OWNER=upper('&nodo') order by s.PARTITION_NAME;
$tabla
$nodo
select 'alter table '||t.OWNER||'.'||t.TABLE_NAME||' shrink space;'
 from DBA_TABLES t, DBA_SEGMENTS s where t.TABLE_NAME=s.SEGMENT_NAME
 and t.PARTITIONED='NO' and t.TABLE_NAME=upper('&tabla') and t.OWNER=upper('&nodo');
$tabla
$nodo
select 'alter table '||OWNER||'.'||TABLE_NAME||' disable row movement;'
 from DBA_TABLES where TABLE_NAME=upper('&tabla') and OWNER=upper('&nodo');
$tabla
$nodo
$tabla
quit"|sqlplus -s ${nodo}/temporal|grep alter > compact_$tabla.sql
else
echo "set heading off feedback off echo off lin 300 pages 999 long 90000
select 'alter table '||t.OWNER||'.'||t.TABLE_NAME||' partition '||s.PARTITION_NAME||' move;'
 from DBA_TABLES t, DBA_SEGMENTS s where t.TABLE_NAME=s.SEGMENT_NAME and t.PARTITIONED='YES'
 and t.TABLE_NAME=upper('&tabla') and t.OWNER=upper('&nodo');
$tabla
$nodo
select 'alter table '||t.OWNER||'.'||t.TABLE_NAME||' move;'
 from DBA_TABLES t, DBA_SEGMENTS s where t.TABLE_NAME=s.SEGMENT_NAME and t.PARTITIONED='NO'
 and t.TABLE_NAME=upper('&tabla') and t.OWNER=upper('&nodo');
$tabla
$nodo
quit"|sqlplus -s ${nodo}/temporal|grep alter > compact_imp_$tabla.sql
fi
echo "[`date +%Y%m%d-%H:%M:%S`] Generado el script SQL con las sentencias para compactar $tabla:  compact_$tabla.sql"
echo "[`date +%Y%m%d-%H:%M:%S`] NOTA: Este script estara adaptado para compactar tabla o particiones, segun este particionada $tabla o no,"
echo "[`date +%Y%m%d-%H:%M:%S`]       y con shrink, si el/los tablespace/s de $tabla son de tipo ASSM, o con move, si no lo son."


# Reconstruccion de indices, particionados o no:

echo "set heading off feedback off echo off lin 300 pages 999 long 90000
select INDEX_NAME from DBA_INDEXES
where TABLE_NAME=upper('&tabla') and OWNER=upper('&nodo') order by INDEX_NAME;
$tabla
$nodo
quit"|sqlplus -s ${nodo}/temporal|grep -v where|grep -v "^$" > indices_$tabla
if [ -s indices_$tabla ] ; then
cat indices_$tabla|while read line; do
ind=`echo ${line}`
echo "set heading off feedback off echo off lin 300 pages 999 long 90000
select 'alter index '||s.SEGMENT_NAME||' rebuild partition '||s.PARTITION_NAME||';'
 from DBA_INDEXES i, DBA_SEGMENTS s where i.INDEX_NAME=s.SEGMENT_NAME and i.PARTITIONED='YES'
 and s.SEGMENT_NAME=upper('&ind') and s.OWNER=upper('&nodo') order by s.PARTITION_NAME;
$ind
$nodo
select 'alter index '||s.SEGMENT_NAME||' rebuild;'
 from DBA_INDEXES i, DBA_SEGMENTS s where i.INDEX_NAME=s.SEGMENT_NAME and i.PARTITIONED='NO'
 and s.SEGMENT_NAME=upper('&ind') and s.OWNER=upper('&nodo') order by s.PARTITION_NAME;
$ind
$nodo
quit"|sqlplus -s ${nodo}/temporal|grep alter > rbd_$ind.sql
echo "[`date +%Y%m%d-%H:%M:%S`] Generado el script SQL para reconstruir el indice $ind de la tabla $tabla:   rbd_$ind.sql"
done
else
echo "[`date +%Y%m%d-%H:%M:%S`] La tabla $tabla no tiene indices; deberia contrastarse con el CVS."
fi


# Calculo de estadisticas:

echo "set heading off feedback off echo off lin 300 pages 999 long 90000
select 'exec dbms_stats.gather_table_stats(ownname => user, tabname => '''||TABLE_NAME||''', estimate_percent => 0.1, method_opt => ''for all columns size 1'', degree => 16, cascade => true);'
 from ALL_TABLES where TABLE_NAME not like 'PPGA_TAR%' and TABLE_NAME=upper('&tabla');
$tabla
select 'exec dbms_stats.gather_table_stats(ownname => user, tabname => '''||TABLE_NAME||''', estimate_percent => DBMS_STATS.AUTO_SAMPLE_SIZE, degree => 8, cascade => true, NO_INVALIDATE => true );'
 from ALL_TABLES where TABLE_NAME=upper('&tabla') and (TABLE_NAME like 'PPGA_TAR%' or TABLE_NAME='PPGA_BLOQUEOTUX');
$tabla
quit"|sqlplus -s ${nodo}/temporal|grep gather|grep -v select > stats_$tabla.sql
echo "[`date +%Y%m%d-%H:%M:%S`] Generado el script SQL con la sentencia para recalcular estadisticas sobre $tabla:  stats_$tabla.sql"


# Ejecucion de los scripts SQL de mantenimiento generados:

if [ -s compact_${tabla}.sql ]; then
echo "[`date +%Y%m%d-%H:%M:%S`] (PUNTO 1) Inicio de la ejecucion del script SQL para compactar $tabla"
echo "set lin 160 pages 500 timing on
@compact_$tabla.sql $nodo
quit"|sqlplus -s ${nodo}/temporal
echo "[`date +%Y%m%d-%H:%M:%S`] (PUNTO 1) Fin de la ejecucion del script SQL para compactar $tabla"
else
echo "[`date +%Y%m%d-%H:%M:%S`] (PUNTO 1) No se creo el script SQL para compactar $tabla"
fi

echo "[`date +%Y%m%d-%H:%M:%S`] (PUNTO 2) Inicio de la ejecucion de los scripts SQL, uno por cada indice, para reconstruir los respectivos indices de $tabla"
i=0
cat indices_${tabla}|while read line; do
i=$((1+${i})) && ind=${line}
if [ -s rbd_${ind}.sql ]; then
echo "[`date +%Y%m%d-%H:%M:%S`] (PUNTO 2.${i}) Inicio de la reconstruccion offline del indice $ind"
echo "set lin 160 pages 500 timing on
@rbd_${ind}.sql
quit"|sqlplus -s ${nodo}/temporal
echo "[`date +%Y%m%d-%H:%M:%S`] (PUNTO 2.${i}) Fin de la reconstruccion offline del indice $ind"
else
echo "[`date +%Y%m%d-%H:%M:%S`] (PUNTO 2.${i}) No se creo el script SQL para reconstruir el indice $ind"
fi
done
echo "[`date +%Y%m%d-%H:%M:%S`] (PUNTO 2) Fin de la ejecucion de los scripts SQL, uno por cada indice, para reconstruir los respectivos indices de $tabla"

if [ -s stats_${tabla}.sql ]; then
echo "[`date +%Y%m%d-%H:%M:%S`] (PUNTO 3) Inicio de la ejecucion del script SQL para recalcular estadisticas de $tabla"
echo "set lin 160 pages 500 timing on
@stats_${tabla}.sql
quit"|sqlplus -s ${nodo}/temporal
echo "[`date +%Y%m%d-%H:%M:%S`] (PUNTO 3) Fin de la ejecucion del script SQL para recalcular estadisticas de $tabla"
fi


echo "[`date +%Y%m%d-%H:%M:%S`] Tama+±e la tabla y sus indices despues del mantenimiento:"
echo "set lin 120 pages 500 heading on feedback off
col SEGMENT_NAME format a50
select round(sum(BYTES/1024/1024),2) MB, sum(BLOCKS) Block, sum(EXTENTS) Extent, SEGMENT_TYPE, SEGMENT_NAME
from DBA_SEGMENTS where SEGMENT_NAME like '%${tabla}%'
group by SEGMENT_TYPE, SEGMENT_NAME order by SEGMENT_TYPE, SEGMENT_NAME;
quit"|sqlplus -s ppga/temporal|grep -v "^$"
echo
echo "[`date +%Y%m%d-%H:%M:%S`] ====  FIN DEL MANTENIMIENTO DE LA TABLA $tabla  ===="
echo

cat nohup.out > ${tabla}_$(date +%Y%m%d%H%M%S).log