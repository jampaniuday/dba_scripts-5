coste NUMBER(12);

alter table 
ppcs.estadisticas add
( 
MSORIGINATING_CO number(15) not null default 0,
CALLFORWARDING_CO number(15) not null default 0,
MSTERMINATINGINROAMING_CO number(15) not null default 0,
MSORIGROAMP_CO number(15) not null default 0,
MSORIGROAML_CO number(15)  not nulldefault 0,
MSORIGINATINGSMS_CO number(15)  not null default 0,
MSORIGINATINGINROAMING_CO number(15) not null default 0,
CALLFORWARDINGINROAMING_CO number(15) not null default 0,
MSTERMINATING_CO number(15) not null default 0,
MSORIGINATINGMSGUNITS_CO number(15) not null default 0,
MSTERMINATINGMSGUNITS_CO number(15) not null default 0,
CONTENTCHARGING_CO number(15) not null default 0,
SESSIONDIAMETER_CO number(15) not null default 0
);

             
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSORIGINATING_CO IS 'Llamadas costo';               
COMMENT ON COLUMN PPCS.ESTADISTICAS.CALLFORWARDING_CO IS 'Desvio costo';                                             
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSTERMINATINGINROAMING_CO IS 'RoamingT costo';                                      
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSORIGROAMP_CO IS 'RoamingP costo';                                                 
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSORIGROAML_CO IS  'RoamingL costo';                                                                
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSORIGINATINGSMS_CO IS 'Meco costo';                  
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSORIGINATINGINROAMING_CO IS 'RoamO costo';                                      
COMMENT ON COLUMN PPCS.ESTADISTICAS.CALLFORWARDINGINROAMING_CO IS 'RoamD costo';                   
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSTERMINATING_CO IS 'Terminadas costo';                               
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSORIGINATINGMSGUNITS_CO IS 'Mensajes orig costo';                   
COMMENT ON COLUMN PPCS.ESTADISTICAS.MSTERMINATINGMSGUNITS_CO IS 'Mensajes term costo';                        
COMMENT ON COLUMN PPCS.ESTADISTICAS.CONTENTCHARGING_CO IS 'Content costo';                         
COMMENT ON COLUMN PPCS.ESTADISTICAS.SESSIONDIAMETER_CO IS 'Diameter costo';                
   



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
         UPDATE  SET A.MSOriginating = A.MSOriginating+1, A.MSOriginating_CO =A.MSOriginating_CO + nvl(:new.coste,0)
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSOriginating,A.MSOriginating_co) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1,nvl(:new.coste,0)) ;
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
         UPDATE  SET A.CallForwarding = A.CallForwarding+1,A.CallForwarding_co = A.CallForwarding_co + nvl(:new.coste,0)
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.CallForwarding,A.CallForwarding_co) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1,nvl(:new.coste,0)) ;
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
         UPDATE  SET A.MSTerminatingInRoaming = A.MSTerminatingInRoaming+1,A.MSTerminatingInRoaming_co = A.MSTerminatingInRoaming_co + nvl(:new.coste,0)
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSTerminatingInRoaming,A.MSTerminatingInRoaming_co) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1,nvl(:new.coste,0)) ;
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
         UPDATE  SET A.MSOrigRoamP = A.MSOrigRoamP+1,A.MSOrigRoamP_CO =A.MSOrigRoamP_CO + nvl(:new.coste,0)
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSOrigRoamP,A.MSOrigRoamP_CO) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1,nvl(:new.coste,0)) ;
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
         UPDATE  SET A.MSOrigRoamL = A.MSOrigRoamL+1,A.MSOrigRoamL_co=A.MSOrigRoamL+ nvl(:new.coste,0)
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSOrigRoamL,A.MSOrigRoamL_co) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1,nvl(:new.coste,0)) ;
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
         UPDATE  SET A.MSOriginatingSMS = A.MSOriginatingSMS+1,A.MSOriginatingSMS_co = A.MSOriginatingSMS_co + nvl(:new.coste,0)
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSOriginatingSMS,A.MSOriginatingSMS_co) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1,nvl(:new.coste,0)) ;
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
         UPDATE  SET A.MSOriginatingInRoaming = A.MSOriginatingInRoaming+1,A.MSOriginatingInRoaming_co = A.MSOriginatingInRoaming_co + nvl(:new.coste,0)
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSOriginatingInRoaming,A.MSOriginatingInRoaming_co) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1,nvl(:new.coste,0)) ;
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
         UPDATE  SET A.CallForwardingInRoaming = A.CallForwardingInRoaming+1,A.CallForwardingInRoaming_co = A.CallForwardingInRoaming_co + nvl(:new.coste,0)
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.CallForwardingInRoaming,A.CallForwardingInRoaming_co) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1,nvl(:new.coste,0)) ;
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
         UPDATE  SET A.MSTerminating = A.MSTerminating+1,A.MSTerminating_co = A.MSTerminating_co + nvl(:new.coste,0)
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSTerminating,A.MSTerminating_co) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1,nvl(:new.coste,0)) ;
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
         UPDATE  SET A.MSOriginatingMsgUnits = A.MSOriginatingMsgUnits+1,A.MSOriginatingMsgUnits_co = A.MSOriginatingMsgUnits_co + nvl(:new.coste,0)
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSOriginatingMsgUnits,A.MSOriginatingMsgUnits_co) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1,nvl(:new.coste,0)) ;
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
         UPDATE  SET A.MSTerminatingMsgUnits = A.MSTerminatingMsgUnits+1,A.MSTerminatingMsgUnits_co = A.MSTerminatingMsgUnits_co + nvl(:new.coste,0)
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.MSTerminatingMsgUnits,A.MSTerminatingMsgUnits_co) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1,nvl(:new.coste,0)) ;
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
         UPDATE  SET A.ContentCharging = A.ContentCharging+1,A.ContentCharging_co = A.ContentCharging_co + nvl(:new.coste,0)
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.ContentCharging,A.ContentCharging_co) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1,nvl(:new.coste,0)) ;
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
         UPDATE  SET A.SessionDiameter = A.SessionDiameter+1,A.SessionDiameter_co = A.SessionDiameter_co + nvl(:new.coste,0)
     WHEN NOT MATCHED THEN
         INSERT (A.FECHA_ESCRITURA,A.MODULO_MINUTOS,A.PID,A.SessionDiameter,A.SessionDiameter_co) VALUES (P_FECHA_DIA,P_MODULO_MINUTOS,P_PID,1,nvl(:new.coste,0)) ;
    EXCEPTION
      WHEN OTHERS THEN
      NULL;
END;
/



