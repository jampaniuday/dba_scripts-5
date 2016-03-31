-- -----------------------------------------------------------------------------
-- TestOracleJVMCallSpec.sql
-- -----------------------------------------------------------------------------

set serveroutput on
call dbms_java.set_output(5000);
        
call oracle_jvm_mgr.show_user();
