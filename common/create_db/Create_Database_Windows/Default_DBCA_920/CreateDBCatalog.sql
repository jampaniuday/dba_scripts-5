connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\ora920\assistants\dbca\logs\CreateDBCatalog.log
@C:\oracle\ora920\rdbms\admin\catalog.sql;
@C:\oracle\ora920\rdbms\admin\catexp7.sql;
@C:\oracle\ora920\rdbms\admin\catblock.sql;
@C:\oracle\ora920\rdbms\admin\catproc.sql;
@C:\oracle\ora920\rdbms\admin\catoctk.sql;
@C:\oracle\ora920\rdbms\admin\owminst.plb;
connect SYSTEM/manager
@C:\oracle\ora920\sqlplus\admin\pupbld.sql;
connect SYSTEM/manager
set echo on
spool C:\oracle\ora920\assistants\dbca\logs\sqlPlusHelp.log
@C:\oracle\ora920\sqlplus\admin\help\hlpbld.sql helpus.sql;
spool off
spool off
exit;
