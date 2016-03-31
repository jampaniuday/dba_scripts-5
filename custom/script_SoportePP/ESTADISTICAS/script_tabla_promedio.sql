CREATE TABLE PPCS.EST_PROMEDIO (                                                                                                                                                            
        MODULO_MINUTOS                NUMBER(3) NOT NULL,                                                                                                                                                                                 
	EVENTMISCELLANEOUS            NUMBER(10) DEFAULT '0' NOT NULL,                                                                         
	MSORIGINATING                 NUMBER(10) DEFAULT '0' NOT NULL,                                                                         
	CALLFORWARDING                NUMBER(10) DEFAULT '0' NOT NULL,                                                                         
	MSTERMINATINGINROAMING        NUMBER(10) DEFAULT '0' NOT NULL,                                                                         
	MSORIGROAMP                   NUMBER(10) DEFAULT '0' NOT NULL,                                                                         
	MSORIGROAML                   NUMBER(10) DEFAULT '0' NOT NULL,                                                                         
	CALL_SMS_USSD_ENQUIRYCREDIT   NUMBER(10) DEFAULT '0' NOT NULL,                                                                         
	MSORIGINATINGSMS              NUMBER(10) DEFAULT '0' NOT NULL,                                                                         
	MSORIGINATINGINROAMING        NUMBER(10) DEFAULT '0' NOT NULL,                                                                         
	CALLFORWARDINGINROAMING       NUMBER(10) DEFAULT '0' NOT NULL,                                                                         
	MSTERMINATING                 NUMBER(10) DEFAULT '0' NOT NULL,                                                                         
	MSORIGINATINGMSGUNITS         NUMBER(10) DEFAULT '0' NOT NULL,                                                                         
	MSTERMINATINGMSGUNITS         NUMBER(10) DEFAULT '0' NOT NULL,                                                                         
	CONTENTCHARGING               NUMBER(10) DEFAULT '0' NOT NULL,                                                                         
	SESSIONDIAMETER               NUMBER(10) DEFAULT '0' NOT NULL,                                                                         
	NOBALANCECALLME               NUMBER(10) DEFAULT '0' NOT NULL,                                                                         
	ENDBILLINGCYCLE               NUMBER(10) DEFAULT '0' NOT NULL,                                                                         
	PROMOTIONEVENTMODULE          NUMBER(10) DEFAULT '0' NOT NULL                                                                          
)
	 PCTFREE 1
 	 PCTUSED 90
 	 INITRANS 10
  	 TABLESPACE EST_4096K_DAT
 	 STORAGE  (INITIAL 1M
		 NEXT 1M
		 FREELISTS 10 
		 FREELIST GROUPS 1);
/

COMMENT ON COLUMN PPCS.EST_PROMEDIO.MODULO_MINUTOS IS 'Grupo de 5 minutos en los que se crea la estadistica';                      
COMMENT ON COLUMN PPCS.EST_PROMEDIO.EVENTMISCELLANEOUS IS 'CDR que registra los eventos del SG';                   
COMMENT ON COLUMN PPCS.EST_PROMEDIO.MSORIGINATING IS 'LLamada en el pais Origen del abonado';               
COMMENT ON COLUMN PPCS.EST_PROMEDIO.CALLFORWARDING IS 'Llamada desviada aun numero que no es gratuito';                                             
COMMENT ON COLUMN PPCS.EST_PROMEDIO.MSTERMINATINGINROAMING IS 'Llamadas recibidas en Roaming';                                      
COMMENT ON COLUMN PPCS.EST_PROMEDIO.MSORIGROAMP IS 'Trafico Roaming CPSA tramo originado';                                                 
COMMENT ON COLUMN PPCS.EST_PROMEDIO.MSORIGROAML IS 'Trafico Roaming CPSA tramo originante';                                                 
COMMENT ON COLUMN PPCS.EST_PROMEDIO.CALL_SMS_USSD_ENQUIRYCREDIT IS 'Eventos de Consulta de Saldo';               
COMMENT ON COLUMN PPCS.EST_PROMEDIO.MSORIGINATINGSMS IS 'SMS en el pais Origen del abonado';                  
COMMENT ON COLUMN PPCS.EST_PROMEDIO.MSORIGINATINGINROAMING IS 'Llamada originada en Roaming';                                      
COMMENT ON COLUMN PPCS.EST_PROMEDIO.CALLFORWARDINGINROAMING IS 'Desvio de llamada en Roaming';                   
COMMENT ON COLUMN PPCS.EST_PROMEDIO.MSTERMINATING IS 'Llamada recibida de tu propia red HPLMS';                               
COMMENT ON COLUMN PPCS.EST_PROMEDIO.MSORIGINATINGMSGUNITS IS 'Duracion de la llamada en segundos';                                              
COMMENT ON COLUMN PPCS.EST_PROMEDIO.MSTERMINATINGMSGUNITS IS 'Coste de la llamada en euros.';                                                   
COMMENT ON COLUMN PPCS.EST_PROMEDIO.CONTENTCHARGING IS 'Eventos de Tarificacion de Contenidos';                         
COMMENT ON COLUMN PPCS.EST_PROMEDIO.SESSIONDIAMETER IS 'Eventos de Tarificacion de Datos';                
COMMENT ON COLUMN PPCS.EST_PROMEDIO.NOBALANCECALLME IS 'Llamadas Realizadas sin Saldo y generan mensaje de Llamame';       
COMMENT ON COLUMN PPCS.EST_PROMEDIO.ENDBILLINGCYCLE IS 'Fin de ciclo de facturacion con resumen del mismo';         
COMMENT ON COLUMN PPCS.EST_PROMEDIO.PROMOTIONEVENTMODULE IS 'Eventos de Tarificacion sin coste asociados a una Promocion';    

CREATE UNIQUE INDEX PPCS.PK_EST_PROMEDIO ON PPCS.EST_PROMEDIO
(
       MODULO_MINUTOS		      ASC
)
	 PCTFREE 1
  	 INITRANS 11
  	 TABLESPACE EST_4096K_IND
 	 STORAGE  (
		 PCTINCREASE 0 
		 FREELISTS 10 
		 FREELIST GROUPS 1 
 	);
/

GRANT INSERT, UPDATE, DELETE ON PPCS.EST_PROMEDIO TO PPCS_IUD;
/

GRANT SELECT ON PPCS.EST_PROMEDIO TO PPCS_SEL;
/

CREATE SYNONYM CPPCS.EST_PROMEDIO FOR PPCS.EST_PROMEDIO;
/

CREATE SYNONYM EPPCS.EST_PROMEDIO FOR PPCS.EST_PROMEDIO;
/



CREATE OR REPLACE PROCEDURE PPCS.SP_ESTADISTICA_CALCULO_P is

cursor c1 is
select modulo_minutos,
round(avg(col1),2) res1,round(avg(col2),2) res2,round(avg(col3),2) res3,round(avg(col4),2) res4,round(avg(col5),2) res5,
round(avg(col6),2) res6,round(avg(col7),2) res7,round(avg(col8),2) res8,round(avg(col9),2) res9,round(avg(col10),2) res10,
round(avg(col11),2) res11,round(avg(col12),2) res12,round(avg(col13),2) res13,round(avg(col14),2) res14,round(avg(col15),2) res15,
round(avg(col16),2) res16,round(avg(col17),2) res17,round(avg(col18),2) res18
from
(select modulo_minutos,trunc(fecha_escritura),
sum(nvl(EVENTMISCELLANEOUS,0)) col1, 
sum(nvl(MSORIGINATING,0)) col2, 
sum(nvl(CALLFORWARDING,0)) col3, 
sum(nvl(MSTERMINATINGINROAMING,0)) col4, 
sum(nvl(MSORIGROAMP,0)) col5, 
sum(nvl(MSORIGROAML,0)) col6, 
sum(nvl(CALL_SMS_USSD_ENQUIRYCREDIT,0)) col7, 
sum(nvl(MSORIGINATINGSMS,0)) col8, 
sum(nvl(MSORIGINATINGINROAMING,0)) col9, 
sum(nvl(CALLFORWARDINGINROAMING,0)) col10, 
sum(nvl(MSTERMINATING,0)) col11, 
sum(nvl(MSORIGINATINGMSGUNITS,0)) col12, 
sum(nvl(MSTERMINATINGMSGUNITS,0)) col13, 
sum(nvl(CONTENTCHARGING,0)) col14, 
sum(nvl(SESSIONDIAMETER,0)) col15, 
sum(nvl(NOBALANCECALLME,0)) col16, 
sum(nvl(ENDBILLINGCYCLE,0)) col17, 
sum(nvl(PROMOTIONEVENTMODULE,0)) col18
from ppcs.estadisticas 
where trunc(FECHA_ESCRITURA) > add_months(sysdate,-3) and 
to_number(to_char(fecha_escritura,'D'))= to_number(to_char(sysdate,'D')) and 
trunc(fecha_escritura) <> trunc(sysdate) group by modulo_minutos,trunc(fecha_escritura))
group by modulo_minutos;


BEGIN

-- limpio la tabla
delete PPCS.EST_PROMEDIO;
commit;


--- inserta el total de registros en la tabla diaria
for carga in c1 loop
insert into ppcs.est_promedio(modulo_minutos,
EVENTMISCELLANEOUS,MSORIGINATING,CALLFORWARDING,MSTERMINATINGINROAMING,MSORIGROAMP,
MSORIGROAML,CALL_SMS_USSD_ENQUIRYCREDIT,MSORIGINATINGSMS,MSORIGINATINGINROAMING,CALLFORWARDINGINROAMING,
MSTERMINATING,MSORIGINATINGMSGUNITS,MSTERMINATINGMSGUNITS,CONTENTCHARGING,SESSIONDIAMETER,
NOBALANCECALLME,ENDBILLINGCYCLE,PROMOTIONEVENTMODULE)
values
(carga.modulo_minutos,
carga.res1,carga.res2,carga.res3,carga.res4,carga.res5,
carga.res6,carga.res7,carga.res8,carga.res9,carga.res10,
carga.res11,carga.res12,carga.res13,carga.res14,carga.res15,
carga.res16,carga.res17,carga.res18);

end loop;
commit;

END;
/



-----job ------------
declare
x number;
BEGIN
SYS.DBMS_JOB.SUBMIT
( job => X
,what => 'BEGIN PPCS.SP_ESTADISTICA_CALCULO_P; END;'
,next_date => trunc(sysdate+1)+1/288
,interval => 'trunc(SYSDATE)+1'
,no_parse => FALSE
);
SYS.DBMS_OUTPUT.PUT_LINE('Job Number is: ' || to_char(x));
COMMIT;
END;
/







--final
select modulo_minutos,sum(nvl(EVENTMISCELLANEOUS,0)) from ppcs.estadisticas where trunc(FECHA_ESCRITURA) > add_months(sydate,-1) and to_number(to_char(fecha_escritura,'D'))= to_number(to_char(sysdate,'D')) and trunc(fecha_escritura) <> trunc(sysdate) group by modulo_minutos;

--- pruebas


select modulo_minutos,sum(nvl(SESSIONDIAMETER,0)),count(1) from ppcs.estadisticas where trunc(FECHA_ESCRITURA) > add_months(sysdate,-3) and to_number(to_char(fecha_escritura,'D'))= to_number(to_char(sysdate,'D')) and trunc(fecha_escritura) <> trunc(sysdate) group by modulo_minutos order by 1;

select modulo_minutos,sum(nvl(SESSIONDIAMETER,0))/12,count(1) from ppcs.estadisticas where trunc(FECHA_ESCRITURA) > add_months(sysdate,-3) and to_number(to_char(fecha_escritura,'D'))= to_number(to_char(sysdate,'D')) and trunc(fecha_escritura) <> trunc(sysdate) group by modulo_minutos order by 1;

select modulo_minutos,round(avg(total),2) from
(select modulo_minutos,trunc(fecha_escritura),sum(nvl(SESSIONDIAMETER,0)) total from ppcs.estadisticas where trunc(FECHA_ESCRITURA) > add_months(sysdate,-3) and to_number(to_char(fecha_escritura,'D'))= to_number(to_char(sysdate,'D')) and trunc(fecha_escritura) <> trunc(sysdate) group by modulo_minutos,trunc(fecha_escritura))
group by modulo_minutos
order by 1;

select modulo_minutos,round(stddev(total),2) from
(select modulo_minutos,trunc(fecha_escritura),sum(nvl(SESSIONDIAMETER,0)) total from ppcs.estadisticas where trunc(FECHA_ESCRITURA) > add_months(sysdate,-3) and to_number(to_char(fecha_escritura,'D'))= to_number(to_char(sysdate,'D')) and trunc(fecha_escritura) <> trunc(sysdate) group by modulo_minutos,trunc(fecha_escritura))
group by modulo_minutos
order by 1;


select modulo_minutos,round(stddev_pop(total),2) from
(select modulo_minutos,trunc(fecha_escritura),sum(nvl(SESSIONDIAMETER,0)) total from ppcs.estadisticas where trunc(FECHA_ESCRITURA) > add_months(sysdate,-3) and to_number(to_char(fecha_escritura,'D'))= to_number(to_char(sysdate,'D')) and trunc(fecha_escritura) <> trunc(sysdate) group by modulo_minutos,trunc(fecha_escritura))
group by modulo_minutos
order by 1;

select modulo_minutos,round(stddev_samp(total),2) from
(select modulo_minutos,trunc(fecha_escritura),sum(nvl(SESSIONDIAMETER,0)) total from ppcs.estadisticas where trunc(FECHA_ESCRITURA) > add_months(sysdate,-3) and to_number(to_char(fecha_escritura,'D'))= to_number(to_char(sysdate,'D')) and trunc(fecha_escritura) <> trunc(sysdate) group by modulo_minutos,trunc(fecha_escritura))
group by modulo_minutos
order by 1;


select modulo_minutos,sum(nvl(SESSIONDIAMETER,0)),count(1) from ppcs.estadisticas where  trunc(fecha_escritura) = trunc(sysdate) group by modulo_minutos order by 1;

select modulo_minutos,sum(nvl(SESSIONDIAMETER,0)),count(1) from ppcs.estadisticas where  trunc(fecha_escritura) = to_date('19082013','DDMMYYYY') group by modulo_minutos order by 1;

hobb!t78


ssh crppcs@10.225.216.9
Temporal


ssh oracle@mcammtybesg10
oracle


export ORACLE_SID=DPPCSCR
export ORACLE_SID=F2PPCSCR


insert into ppcs.ppcs_llamadas
(MSISDN, FEH_LLAMADA, CID, FEH_INSERCION, TIP_LLAMADA, TIP_PREPAGO, TON_ORIGEN, NPI_ORIGEN, TLF_ORIGEN, TON_DESTINO, NPI_DESTINO, TLF_DESTINO, DURACION , COSTE  , GSM_SERVICIO, SDP_ID, MISMA_TRIBU, LISTAS_BN, ZONAS_TARIF,LISTA_DESTINO, MAS_INFO, IMP_CREDITO, IMP_CONSUMO, SEC_ARCHIVO)
select 
MSISDN, FEH_LLAMADA+3, CID, FEH_INSERCION+3, TIP_LLAMADA, TIP_PREPAGO, TON_ORIGEN, NPI_ORIGEN, TLF_ORIGEN, TON_DESTINO, NPI_DESTINO, TLF_DESTINO, DURACION , COSTE  , GSM_SERVICIO, SDP_ID, MISMA_TRIBU, LISTAS_BN, ZONAS_TARIF,LISTA_DESTINO, MAS_INFO, IMP_CREDITO, IMP_CONSUMO, SEC_ARCHIVO
from ppcs.ppcs_llamadas
where rownum < 2;



INSERT into ppcs.estadisticas
(FECHA_ESCRITURA,MODULO_MINUTOS,PID,MSOriginating,MSOriginating_co)
values
(sysdate, trunc(mod(sysdate-trunc(sysdate),1)*288),SYS_CONTEXT('userenv','sessionid'),1,100);




@gnitiooracle78


scp p10404530_112030_Linux-x86-64_1of7.zip oracle@fe1:backup
scp p10404530_112030_Linux-x86-64_2of7.zip oracle@fe1:backup
scp p10404530_112030_Linux-x86-64_3of7.zip oracle@fe1:backup
scp p10404530_112030_Linux-x86-64_4of7.zip oracle@fe1:backup
scp p10404530_112030_Linux-x86-64_5of7.zip oracle@fe1:backup
scp p10404530_112030_Linux-x86-64_6of7.zip oracle@fe1:backup
scp p10404530_112030_Linux-x86-64_7of7.zip oracle@fe1:backup


rm -f p10404530_112030_Linux-x86-64_1of7.zip
rm -f p10404530_112030_Linux-x86-64_2of7.zip
rm -f p10404530_112030_Linux-x86-64_3of7.zip
rm -f p10404530_112030_Linux-x86-64_4of7.zip
rm -f p10404530_112030_Linux-x86-64_5of7.zip
rm -f p10404530_112030_Linux-x86-64_6of7.zip
rm -f p10404530_112030_Linux-x86-64_7of7.zip


