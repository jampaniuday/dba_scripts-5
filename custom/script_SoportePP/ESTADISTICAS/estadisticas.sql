CREATE TABLESPACE EST_4096K_DAT 
   DATAFILE '/ora125/ppcsslv/oradata/datafiles/EST_4096K_DAT_01.dbf' SIZE 1024M
   LOGGING;

CREATE TABLESPACE EST_4096K_IND 
   DATAFILE '/ora125/ppcsslv/oradata/indexfiles/EST_4096K_IND_01.dbf' SIZE 512M
   LOGGING;




CREATE TABLE PPCS.ESTADISTICAS (
        FECHA_ESCRITURA               DATE NOT NULL,                           
        MODULO_MINUTOS                NUMBER(3) NOT NULL,                      
        PID                           NUMBER(5) NOT NULL,                                  
	EVENTMISCELLANEOUS            NUMBER(10),                              
	MSORIGINATING                 NUMBER(10),                              
	CALLFORWARDING                NUMBER(10),                              
	MSTERMINATINGINROAMING        NUMBER(10),                              
	MSORIGROAMP                   NUMBER(10),                              
	MSORIGROAML                   NUMBER(10),                              
	CALL_SMS_USSD_ENQUIRYCREDIT   NUMBER(10),                              
	MSORIGINATINGSMS              NUMBER(10),                              
	MSORIGINATINGINROAMING        NUMBER(10),                              
	CALLFORWARDINGINROAMING       NUMBER(10),                              
	MSTERMINATING                 NUMBER(10),                              
	MSORIGINATINGMSGUNITS         NUMBER(10),                              
	MSTERMINATINGMSGUNITS         NUMBER(10),                              
	CONTENTCHARGING               NUMBER(10),                              
	SESSIONDIAMETER               NUMBER(10),                              
	NOBALANCECALLME               NUMBER(10),                              
	ENDBILLINGCYCLE               NUMBER(10),                              
	PROMOTIONEVENTMODULE          NUMBER(10)                               
)
	 PCTFREE 1
 	 PCTUSED 90
 	 INITRANS 10
  	 TABLESPACE EST_4096K_DAT
 	 STORAGE  (
		 INITIAL 32M
		 NEXT 32M
		 FREELISTS 10 
		 FREELIST GROUPS 1 
 	)
PARTITION BY RANGE (FECHA_ESCRITURA)
(
PARTITION ESTADISTICAS_121101
VALUES LESS THAN (TO_DATE('2012-08-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN'))
	);

COMMENT ON COLUMN PPCS.ESTADISTICAS.FECHA_ESCRITURA IS 'Telefono que realiza la operacion';                          
COMMENT ON COLUMN PPCS.ESTADISTICAS.MODULO_MINUTOS IS 'Fecha y hora de comienzo de la llamada';                      
COMMENT ON COLUMN PPCS.ESTADISTICAS.PID IS 'Numero identificador para el registro CDR';                              
COMMENT ON COLUMN PPCS.ESTADISTICAS.EVENTMISCELLANEOUS IS 'Dia del a±o de insercion del registro';                   
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSORIGINATING IS 'Codigo del tipo de llamada para la descripcion';               
COMMENT ON COLUMN PPCS.ESTADISTICAS.CALLFORWARDING IS 'Tipo de prepago';                                             
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSTERMINATINGINROAMING IS 'Tipo de numero';                                      
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSORIGROAMP IS 'Tipo de numero';                                                 
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSORIGROAML IS 'Tipo de numero';                                                 
COMMENT ON COLUMN PPCS.ESTADISTICAS.CALL_SMS_USSD_ENQUIRYCREDIT IS 'Indicador del plan de numeracion';               
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSORIGINATINGSMS IS 'Telefono desde el que se hace la llamada';                  
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSORIGINATINGINROAMING IS 'Tipo de numero';                                      
COMMENT ON COLUMN PPCS.ESTADISTICAS.CALLFORWARDINGINROAMING IS 'Indicador del plan de numeracion';                   
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSTERMINATING IS 'Telefono destino de la llamada';                               
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSORIGINATINGMSGUNITS IS 'Duracion de la llamada en segundos';                   
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSTERMINATINGMSGUNITS IS 'Coste de la llamada en euros.';                        
COMMENT ON COLUMN PPCS.ESTADISTICAS.CONTENTCHARGING IS 'Tipo de servicio GSM proporcionado';                         
COMMENT ON COLUMN PPCS.ESTADISTICAS.SESSIONDIAMETER IS 'Identificador de SDP que atendio la llamada';                
COMMENT ON COLUMN PPCS.ESTADISTICAS.NOBALANCECALLME IS 'Valor S si ambos numeros pertenecen a la misma tribu';       
COMMENT ON COLUMN PPCS.ESTADISTICAS.ENDBILLINGCYCLE IS 'Valor para el algoritmo de listas blancas y negras';         
COMMENT ON COLUMN PPCS.ESTADISTICAS.PROMOTIONEVENTMODULE IS 'Valor para el algoritmo de listas blancas y negras';    

CREATE UNIQUE INDEX PPCS.PK_ESCRITURA ON PPCS.ESTADISTICAS
(
       FECHA_ESCRITURA                ASC,
       MODULO_MINUTOS		      ASC,
       PID			      ASC
)
	 PCTFREE 1
  	 INITRANS 11
  	 TABLESPACE EST_4096K_IND
 	 STORAGE  (
		 PCTINCREASE 0 
		 FREELISTS 10 
		 FREELIST GROUPS 1 
 	)
LOCAL
(
PARTITION ESTADISTICAS_121001
	);

GRANT INSERT, UPDATE, DELETE ON PPCS.ESTADISTICAS TO PPCS_IUD;

GRANT SELECT ON PPCS.ESTADISTICAS TO PPCS_SEL;

CREATE SYNONYM CPPCS.ESTADISTICAS FOR PPCS.ESTADISTICAS;
CREATE SYNONYM EPPCS.ESTADISTICAS FOR PPCS.ESTADISTICAS;

GRANT ALTER ANY TABLE TO PPCS;

INSERT INTO PPCS.PP_CFGMANTPARTS (NOM_ESQUEMA,NOM_TABLA,COD_TIPO,NUM_MESES_VIGENCIA,IND_MANT_ACTIVO,TXT_PREFIJO_PARTICIONES)
VALUES ('PPCS','ESTADISTICAS',3,12,'S','ESTADISTICAS_');

set serverout on

DECLARE
   IDE pls_integer;
BEGIN
  PPCS.PP_MANT_PARTS_PQ.MANT_PARTS(IDE);
EXCEPTION
 WHEN OTHERS THEN
dbms_output.put_line('Valor resultante de operacion:' || IDE);   
END;
/

select partition_name from dba_segments where segment_name in ('ESTADISTICAS') order by 1;

SELECT sid into p_pid FROM V$SESSION WHERE audsid = SYS_CONTEXT('userenv','sessionid');

select p.spid 
into p_pid
from  v$session s, v$process p
where s.audsid=SYS_CONTEXT('userenv','sessionid')
and s.paddr=p.addr;


CREATE OR REPLACE TRIGGER PPCS_CONTADOR_EVENTOS
AFTER INSERT
ON PPCS_EVENTOS
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.EventMiscellaneous = A.EventMiscellaneous+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.EventMiscellaneous) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/


CREATE OR REPLACE TRIGGER PPCS_CONTADOR_LLAMADAS
AFTER INSERT
ON PPCS_LLAMADAS
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.MSOriginating = A.MSOriginating+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSOriginating) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/

CREATE OR REPLACE TRIGGER PPCS_CONTADOR_DESVIOS
AFTER INSERT
ON PPCS_DESVIOS
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.CallForwarding = A.CallForwarding+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.CallForwarding) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/


CREATE OR REPLACE TRIGGER PPCS_CONTADOR_ROAMT
AFTER INSERT
ON PPCS_ROAMT
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.MSTerminatingInRoaming = A.MSTerminatingInRoaming+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSTerminatingInRoaming) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/

CREATE OR REPLACE TRIGGER PPCS_CONTADOR_ROAMP
AFTER INSERT
ON PPCS_ROAMP
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.MSOrigRoamP = A.MSOrigRoamP+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSOrigRoamP) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/

CREATE OR REPLACE TRIGGER PPCS_CONTADOR_ROAML
AFTER INSERT
ON PPCS_ROAML
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.MSOrigRoamL = A.MSOrigRoamL+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSOrigRoamL) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/


CREATE OR REPLACE TRIGGER PPCS_CONTADOR_CONSALDO
AFTER INSERT
ON PPCS_CONSALDO
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.Call_SMS_USSD_EnquiryCredit = A.Call_SMS_USSD_EnquiryCredit+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.Call_SMS_USSD_EnquiryCredit) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/

CREATE OR REPLACE TRIGGER PPCS_CONTADOR_MECO
AFTER INSERT
ON PPCS_MECO
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.MSOriginatingSMS = A.MSOriginatingSMS+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSOriginatingSMS) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/

CREATE OR REPLACE TRIGGER PPCS_CONTADOR_ROAMO
AFTER INSERT
ON PPCS_ROAMO
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.MSOriginatingInRoaming = A.MSOriginatingInRoaming+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSOriginatingInRoaming) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/


CREATE OR REPLACE TRIGGER PPCS_CONTADOR_ROAMD
AFTER INSERT
ON PPCS_ROAMD
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.CallForwardingInRoaming = A.CallForwardingInRoaming+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.CallForwardingInRoaming) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/

CREATE OR REPLACE TRIGGER PPCS_CONTADOR_TERMINADAS
AFTER INSERT
ON PPCS_TERMINADAS
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.MSTerminating = A.MSTerminating+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSTerminating) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/

CREATE OR REPLACE TRIGGER PPCS_CONTADOR_MECOORIG
AFTER INSERT
ON PPCS_MECOORIG
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.MSOriginatingMsgUnits = A.MSOriginatingMsgUnits+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSOriginatingMsgUnits) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/

CREATE OR REPLACE TRIGGER PPCS_CONTADOR_MECOTERM
AFTER INSERT
ON PPCS_MECOTERM
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.MSTerminatingMsgUnits = A.MSTerminatingMsgUnits+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSTerminatingMsgUnits) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/


CREATE OR REPLACE TRIGGER PPCS_CONTADOR_CONTENT
AFTER INSERT
ON PPCS_CONTENT
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.ContentCharging = A.ContentCharging+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.ContentCharging) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/

CREATE OR REPLACE TRIGGER PPCS_CONTADOR_DIAMETER
AFTER INSERT
ON PPCS_DIAMETER
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.SessionDiameter = A.SessionDiameter+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.SessionDiameter) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/


CREATE OR REPLACE TRIGGER PPCS_CONTADOR_CALLME
AFTER INSERT
ON PPCS_CALLME
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.NoBalanceCallMe = A.NoBalanceCallMe+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.NoBalanceCallMe) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/


CREATE OR REPLACE TRIGGER PPCS_CONT_FINCICLOFACTURACION
AFTER INSERT
ON PPCS_FINCICLOFACTURACION
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.EndBillingCycle = A.EndBillingCycle+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.EndBillingCycle) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/


CREATE OR REPLACE TRIGGER PPCS_CONTADOR_EVENTPROM
AFTER INSERT
ON PPCS_EVENTPROM
REFERENCING NEW AS New OLD AS Old
FOR EACH ROW
DECLARE
   p_modulo_minutos number :=0;
   p_pid number :=0;
   P_fecha_dia date:= null;
BEGIN
   -- llenado de variables
   p_modulo_minutos :=  trunc(mod(sysdate-trunc(sysdate),1)*288);
   p_fecha_dia := sysdate; 
   p_pid := SYS_CONTEXT('userenv','sessionid');
   -- insercion o actualizacion de la tabla
   MERGE INTO 
	PPCS.ESTADISTICAS A 
	USING DUAL B ON (A.FECHA_ESCRITURA = p_fecha_dia and A.MODULO_MINUTOS = p_modulo_minutos and A.PID=p_pid )
     WHEN MATCHED THEN
         UPDATE  SET A.PromotionEventModule = A.PromotionEventModule+1
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.PromotionEventModule) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/
