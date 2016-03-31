create or replace TRIGGER TC_BOFERTAS_CATEGORIA_BI01
    BEFORE INSERT ON TC_BOFERTAS_CATEGORIA  FOR EACH ROW
begin
    --- swbapps/telcasv0
	--- Modificado por Ronald Orantes <ronaldorantes@icon.com.gt> - martes 03/12/2013 6:04 p. m. - OT 2202438
    -- Asigna el identificador si la
    -- aplicaci?n cliente no lo hace.
    if (:new.TCCATEGORIAID is null) or (:new.TCCATEGORIAID = 0) then
        select TC_BOFERTAS_CATEGORIA_SQ.nextval
        into :new.TCCATEGORIAID
        from dual;
    end if;
end;

/
create or replace TRIGGER TC_BOFERTAS_USUARIO_BI01
    BEFORE INSERT ON TC_BOFERTAS_USUARIO  FOR EACH ROW
begin
    --- swbapps/telcasv0
	--- Modificado por Ronald Orantes <ronaldorantes@icon.com.gt> - martes 03/12/2013 6:04 p. m. - OT 2202438	
    -- Asigna el identificador si la
    -- aplicaci?n cliente no lo hace.
    if (:new.TCUSUARIOID is null) or (:new.TCUSUARIOID = 0) then
        select TC_BOFERTAS_USUARIO_SQ.nextval
        into :new.TCUSUARIOID
        from dual;
    end if;
end;
/
create or replace TRIGGER TC_BOFERTAS_DEPARTAMENTO_BI01
    BEFORE INSERT ON TC_BOFERTAS_DEPARTAMENTO  FOR EACH ROW
begin
    --- swbapps/telcasv0
	--- Modificado por Ronald Orantes <ronaldorantes@icon.com.gt> - martes 03/12/2013 6:04 p. m. - OT 2202438
    -- Asigna el identificador si la
    -- aplicaci?n cliente no lo hace.
    if (:new.TCDEPARTAMENTOID is null) or (:new.TCDEPARTAMENTOID = 0) then
        select TC_BOFERTAS_DEPARTAMENTO_SQ.nextval
        into :new.TCDEPARTAMENTOID
        from dual;
    end if;
end;
/
create or replace TRIGGER TC_BOFERTAS_ANUNCIO_BI01
    BEFORE INSERT ON TC_BOFERTAS_ANUNCIO  FOR EACH ROW
begin
    --- swbapps/telcasv0
	--- Modificado por Ronald Orantes <ronaldorantes@icon.com.gt> - martes 03/12/2013 6:04 p. m. - OT 2202438
    -- Asigna el identificador si la
    -- aplicaci?n cliente no lo hace.
    if (:new.TCANUNCIOID is null) or (:new.TCANUNCIOID = 0) then
        select TC_BOFERTAS_ANUNCIO_SQ.nextval
        into :new.TCANUNCIOID
        from dual;
    end if;
end;
/

create or replace TRIGGER TC_HISTORICO_SMS_BI01
    BEFORE INSERT ON TC_BOFERTAS_HISTORICO_SMS  FOR EACH ROW
begin
    --- swbapps/telcasv0
	--- Modificado por Ronald Orantes <ronaldorantes@icon.com.gt> - martes 03/12/2013 6:04 p. m. - OT 2202438
    -- Asigna el identificador si la
    -- aplicaci?n cliente no lo hace.
    if (:new.TCHISTORICOSMSID is null) or (:new.TCHISTORICOSMSID = 0) then
        select TC_BOFERTAS_HISTORICO_SQ.nextval
        into :new.TCHISTORICOSMSID
        from dual;
    end if;
end;
/
create or replace TRIGGER TC_BOFERTAS_IMAGEN_BI01
    BEFORE INSERT ON TC_BOFERTAS_IMAGEN  FOR EACH ROW
begin
    --- swbapps/telcasv0
	--- Modificado por Ronald Orantes <ronaldorantes@icon.com.gt> - martes 03/12/2013 6:04 p. m. - OT 2202438
    -- Asigna el identificador si la
    -- aplicaci?n cliente no lo hace.
    if (:new.TCIMAGENID is null) or (:new.TCIMAGENID = 0) then
        select TC_BOFERTAS_IMAGEN_SQ.nextval
        into :new.TCIMAGENID
        from dual;
    end if;
end;
/
create or replace TRIGGER TC_BOFERTAS_MUNICIPIO_BI01
    BEFORE INSERT ON TC_BOFERTAS_MUNICIPIO  FOR EACH ROW
begin
    --- swbapps/telcasv0
	--- Modificado por Ronald Orantes <ronaldorantes@icon.com.gt> - martes 03/12/2013 6:04 p. m. - OT 2202438
    -- Asigna el identificador si la
    -- aplicaci?n cliente no lo hace.
    if (:new.TCMUNICIPIOID is null) or (:new.TCMUNICIPIOID = 0) then
        select TC_BOFERTAS_MUNICIPIO_SQ.nextval
        into :new.TCMUNICIPIOID
        from dual;
    end if;
end;	
/
create or replace TRIGGER TC_BOFERTAS_ARTICULO_BI01
    BEFORE INSERT ON TC_BOFERTAS_ARTICULO  FOR EACH ROW
begin
    --- swbapps/telcasv0
	--- Modificado por Ronald Orantes <ronaldorantes@icon.com.gt> - martes 03/12/2013 6:04 p. m. - OT 2202438
    -- Asigna el identificador si la
    -- aplicaci?n cliente no lo hace.
    if (:new.TCARTICULOID is null) or (:new.TCARTICULOID = 0) then
        select TC_BOFERTAS_ARTICULO_SQ.nextval
        into :new.TCARTICULOID
        from dual;
    end if;
end;
/
create or replace TRIGGER TC_BOFERTAS_FAVORITO_BI01
    BEFORE INSERT ON TC_BOFERTAS_FAVORITO  FOR EACH ROW
begin
    --- swbapps/telcasv0
	--- Modificado por Ronald Orantes <ronaldorantes@icon.com.gt> - martes 03/12/2013 6:04 p. m. - OT 2202438
    -- Asigna el identificador si la
    -- aplicaci?n cliente no lo hace.
    if (:new.TCFAVORITOID is null) or (:new.TCFAVORITOID = 0) then
        select TC_BOFERTAS_FAVORITO_SQ.nextval
        into :new.TCFAVORITOID
        from dual;
    end if;
end;
/
create or replace TRIGGER TC_BOFERTAS_CONF_APP_BI01
    BEFORE INSERT ON TC_BOFERTAS_CONF_APLICACION  FOR EACH ROW
begin
    --- swbapps/telcasv0
	--- Modificado por Ronald Orantes <ronaldorantes@icon.com.gt> - martes 03/12/2013 6:04 p. m. - OT 2202438
    -- Asigna el identificador si la
    -- aplicaci?n cliente no lo hace.
    if (:new.TCCONFAPLICACIONID is null) or (:new.TCCONFAPLICACIONID = 0) then
        select TC_BOFERTAS_CONF_APP_SQ.nextval
        into :new.TCCONFAPLICACIONID
        from dual;
    end if;
end;
/
Show Errors

