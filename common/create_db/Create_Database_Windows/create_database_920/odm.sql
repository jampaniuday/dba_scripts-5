
connect SYS/change_on_install as SYSDBA

set echo on

spool odm.log

@%ORACLE_HOME%\dm\admin\dminst.sql ODM TEMP %ORACLE_HOME%\assistants\dbca\logs\;

connect SYS/change_on_install as SYSDBA

revoke AQ_ADMINISTRATOR_ROLE from ODM;

spool off

exit;
