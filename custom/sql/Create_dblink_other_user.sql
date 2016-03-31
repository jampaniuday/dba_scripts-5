CREATE PROCEDURE MGT03141.cre_db_lnk AS
BEGIN
    EXECUTE IMMEDIATE 'CREATE DATABASE LINK LNK_PPGA '
            ||'CONNECT TO EPPGA IDENTIFIED BY temporal '
            ||'USING ''ppga''';
END cre_db_lnk;
/

--ANTES
SELECT owner, db_link, username, host, created FROM dba_db_links where owner = UPPER('MGT03141');

GRANT create database link TO MGT03141;
exec MGT03141.cre_db_lnk;
REVOKE create database link FROM MGT03141; 
DROP PROCEDURE MGT03141.cre_db_lnk;

--DESPUES
SELECT owner, db_link, username, host, created FROM dba_db_links where owner = UPPER('MGT03141');

commit;