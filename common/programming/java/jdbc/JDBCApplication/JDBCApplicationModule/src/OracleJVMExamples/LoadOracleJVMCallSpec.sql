-- -----------------------------------------------------------------------------
-- LoadOracleJVMCallSpec.sql
-- -----------------------------------------------------------------------------

CREATE OR REPLACE PACKAGE oracle_jvm_mgr AS
    PROCEDURE show_user;
END oracle_jvm_mgr;
/
show errors

CREATE OR REPLACE PACKAGE BODY oracle_jvm_mgr AS

    PROCEDURE show_user
        AS LANGUAGE JAVA
        NAME 'OracleJVMExamples.TestConnection.showUser()';

END oracle_jvm_mgr;
/
show errors

exit
