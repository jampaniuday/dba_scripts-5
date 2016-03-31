  CREATE OR REPLACE PROCEDURE "PPGA"."RECARGAS_X_MINUTOS" (vMinutos IN NUMBER, v
Path IN VARCHAR2) AS

/*------------------------------*/
/* CURSORES                     */
/*------------------------------*/

CURSOR cOrigenes is
   select COD_SBACTUACION,DES_SBACTUACION
     from ppga_literalsbactuacion
    where cod_idioma='10'
      and cod_actuacio='RC';

CURSOR cOrigenes2 is
   select COD_ORIGRECA,DES_ORIGRECA
     from ppga_origreca;


/*------------------------------*/
/* VARIABLES                    */
/*------------------------------*/

-- Variables contenedoras de los registros de los cursores
reg_ORIGENES              cOrigenes%ROWTYPE;
reg_ORIGENES2             cOrigenes2%ROWTYPE;
vFicheroSalida            UTL_FILE.FILE_TYPE;
NombreFicheroSalida       VARCHAR2(25);
FormatoFecha              VARCHAR2(100);
vCod_sbactuacion          PPGA_ACTABOPRE.COD_SBACTUACION%TYPE;
vDes_sbactuacion          PPGA_LITERALSBACTUACION.DES_SBACTUACION%TYPE;
vFecha                    VARCHAR2(15);
vFecha1                   VARCHAR2(15);
vHora                     VARCHAR2(15);
vOrigen                   PPGA_RECARGAS.COD_ORIGRECA%TYPE;
vImporte                  PPGA_RECARGAS.IMP_RECARGA%TYPE;
vNumero                   NUMBER(15);
vContador                 NUMBER;
vCod_origreca             PPGA_ORIGRECA.COD_ORIGRECA%TYPE;
vCod_descripcion          PPGA_ORIGRECA.DES_ORIGRECA%TYPE;


/*------------------------------------------------------------------------------
------------*/
/*  SECCION EJECUTABLE.
            */
/*------------------------------------------------------------------------------
------------*/

BEGIN

   dbms_output.disable;
   dbms_output.enable(1000000);
   FormatoFecha := 'alter session set NLS_DATE_FORMAT=''YYYYMMDDHH24MISS''';
   EXECUTE IMMEDIATE FormatoFecha;

   vFecha1 :=to_char(SYSDATE,'hh24_ddmm');

   --- Generacion del fichero de salida.
   NombreFicheroSalida := 'RECARGAS_'||vFecha1||'.html';

   -- Apertura de fichero
   vFicheroSalida := UTL_FILE.FOPEN (vPath, NombreFicheroSalida,'W');

   dbms_output.put_line('El nombre del fichero vFicheroSalida : ' || NombreFiche
roSalida);
   dbms_output.put_line('La fecha sera tendra el formato: ' || sysdate);

   vContador := 0;

   OPEN cOrigenes;
   LOOP
      FETCH cOrigenes INTO reg_ORIGENES;
      EXIT WHEN cOrigenes%NOTFOUND;

      vCod_sbactuacion   :=reg_ORIGENES.COD_SBACTUACION;
      vDes_sbactuacion   :=reg_ORIGENES.DES_SBACTUACION;

   dbms_output.put_line('COD_SBACTUACION : ' || vCod_sbactuacion);

   BEGIN
      select to_char(SYSDATE,'hh24'),to_char(SYSDATE,'ddmmyyyy'),b.cod_origreca,

      (SUM(IMP_RECARGA) / 100), count(*)
        into vHora,vFecha,vOrigen,vImporte,vNumero
        from ppga_actabopre a, ppga_recargas b
       where b.sec_actuacion = a.sec_actuacion
         and a.COD_ACTUACIO='RC' and a.FEC_ACTUACION >= sysdate -1/24
         and a.cod_estarec='EJ'
         and a.cod_sbactuacion=vCod_sbactuacion
      group by b.cod_origreca;
       exception when others then
          vImporte:='0';
          vNumero:='0';

    END;

      --- Insertamos el registro cabecera y Contador es igual a 0
      IF vContador = 0 THEN

         UTL_FILE.PUTF(vFicheroSalida,'%s\n%s\n%s\n%s\n', '<HTML><HEAD><TITLE>IN
FORME ALTAS/RECARGAS COSTA RICA</TITLE></HEAD>',
                                                          '<BODY><H1>RECARGAS EN
 LA ULTIMA HORA</H1>',
                                                          '<TABLE BORDER=2 WIDTH
=500>',
                                                          '<TR><TD> HORA </TD><T
D> FECHA </TD><TD> TIPO RECARGA </TD><TD> IMPORTE </TD><TD> NUMERO </TD></TR>');

         UTL_FILE.FFLUSH(vFicheroSalida);
      END IF;

     vContador := vContador + 1;

     IF vImporte <> '0' THEN

     UTL_FILE.PUTF(vFicheroSalida,'%s\n','<TR><TD> '||vHora||' </TD><TD> '||vFec
ha||' </TD><TD> '||vDes_sbactuacion||' </TD><TD> '||LPAD(TO_CHAR(vImporte,'$999,
999,999.999'),16,' ')||' </TD><TD> '||vNumero||' </TD></TR>');
     UTL_FILE.FFLUSH(vFicheroSalida);
     END IF;

   END LOOP;
   CLOSE cOrigenes;

   UTL_FILE.PUTF(vFicheroSalida,'%s\n', '</TABLE>');
   UTL_FILE.FFLUSH(vFicheroSalida);

   UTL_FILE.PUTF(vFicheroSalida,'%s\n%s\n%s\n', '<H1>RECARGAS EN ACUMULADAS EN E
L DIA</H1>',
                                                '<TABLE BORDER=2 WIDTH=500>',
                                                '<TR><TD> FECHA </TD><TD> TIPO R
ECARGA </TD><TD> IMPORTE </TD><TD> NUMERO </TD></TR>');

   OPEN cOrigenes2;
   LOOP
      FETCH cOrigenes2 INTO reg_ORIGENES2;
      EXIT WHEN cOrigenes2%NOTFOUND;

      vCod_origreca    :=reg_ORIGENES2.COD_ORIGRECA;
      vCod_descripcion :=reg_ORIGENES2.DES_ORIGRECA;

      BEGIN
      select to_char(SYSDATE,'dd;ddmmyyyy'),b.cod_origreca,(SUM(IMP_RECARGA) / 1
00), count(*)
        into vFecha,vOrigen,vImporte,vNumero
        from ppga_actabopre a, ppga_recargas b
       where b.sec_actuacion = a.sec_actuacion
         and a.COD_ACTUACIO='RC'
         and b.cod_origreca=vCod_origreca
         and a.FEC_ACTUACION >= TRUNC(sysdate)
         and a.cod_estarec='EJ'
       group by b.cod_origreca;
       exception when others then
          vImporte:='0';
          vNumero:='0';
      END;

      IF vNumero <> '0' THEN

      UTL_FILE.PUTF(vFicheroSalida,'%s\n','<TR><TD> '||vFecha||' </TD><TD> '||vC
od_descripcion||' </TD><TD> '||LPAD(TO_CHAR(vImporte,'$999,999,999.999'),16,' ')
||' </TD><TD> '||vNumero||' </TD></TR>');
      UTL_FILE.FFLUSH(vFicheroSalida);
      END IF;

   END LOOP;
   CLOSE cOrigenes2;

   --- Insertamos el registro de final de fichero
      UTL_FILE.PUTF(vFicheroSalida,'%s\n', '</TABLE></BODY></HTML>');
   UTL_FILE.FFLUSH(vFicheroSalida);
   UTL_FILE.FCLOSE_ALL;

   -- Seccion de Tratamiento de excepciones para el Cuerpo del Procedimiento (bo
dy)
EXCEPTION

   WHEN NO_DATA_FOUND THEN
      UTL_FILE.PUT_LINE(vFicheroSalida, 'Fecha: [' || sysdate || '] No existen m
as registros');
      UTL_FILE.NEW_LINE(vFicheroSalida);
      UTL_FILE.FCLOSE_ALL;

   WHEN UTL_FILE.INVALID_OPERATION THEN
      UTL_FILE.PUT_LINE(vFicheroSalida, 'Fecha: [' || sysdate || '] Error en el
Fichero de Salida');
      UTL_FILE.NEW_LINE(vFicheroSalida);
      UTL_FILE.FCLOSE_ALL;

   WHEN UTL_FILE.INVALID_FILEHANDLE THEN
      UTL_FILE.PUT_LINE(vFicheroSalida, 'Fecha: [' || sysdate || '] Fichero Sali
da no encontrado');
      UTL_FILE.NEW_LINE(vFicheroSalida);
      UTL_FILE.FCLOSE_ALL;

   WHEN UTL_FILE.WRITE_ERROR THEN
      UTL_FILE.PUT_LINE(vFicheroSalida, 'Fecha: [' || sysdate || '] Error de esc
ritura en fichero');
      UTL_FILE.NEW_LINE(vFicheroSalida);
      UTL_FILE.FCLOSE_ALL;

   WHEN UTL_FILE.READ_ERROR THEN
      UTL_FILE.PUT_LINE(vFicheroSalida, 'Fecha: [' || sysdate || '] Error de lec
tura en fichero');
      UTL_FILE.NEW_LINE(vFicheroSalida);
      UTL_FILE.FCLOSE_ALL;

   WHEN OTHERS THEN
      UTL_FILE.PUT_LINE(vFicheroSalida, 'Fecha: [' || sysdate || '] Error no def
inido : [' || sqlcode || '] Descripcion : [' || sqlerrm || ']');
      UTL_FILE.NEW_LINE(vFicheroSalida);
      UTL_FILE.FCLOSE_ALL;

-- Fin Procedimiento
END recargas_X_minutos;