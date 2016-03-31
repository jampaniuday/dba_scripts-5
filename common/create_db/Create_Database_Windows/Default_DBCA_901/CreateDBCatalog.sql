connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\RDBMS901\assistants\dbca\logs\CreateDBCatalog.log
@C:\oracle\RDBMS901\rdbms\admin\catalog.sql;
@C:\oracle\RDBMS901\rdbms\admin\catexp7.sql;
@C:\oracle\RDBMS901\rdbms\admin\catblock.sql;
@C:\oracle\RDBMS901\rdbms\admin\catproc.sql;
@C:\oracle\RDBMS901\rdbms\admin\catoctk.sql;
@C:\oracle\RDBMS901\rdbms\admin\catobtk.sql;
@C:\oracle\RDBMS901\rdbms\admin\caths.sql;
@C:\oracle\RDBMS901\rdbms\admin\owminst.plb;
connect SYSTEM/manager
@C:\oracle\RDBMS901\sqlplus\admin\pupbld.sql;
connect SYSTEM/manager
set echo on
spool C:\oracle\RDBMS901\assistants\dbca\logs\sqlPlusHelp.log
@C:\oracle\RDBMS901\sqlplus\admin\help\hlpbld.sql helpus.sql;
spool off
spool off
exit;
