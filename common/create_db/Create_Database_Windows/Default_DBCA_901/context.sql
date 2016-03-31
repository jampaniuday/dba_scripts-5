connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\RDBMS901\assistants\dbca\logs\context.log
@C:\oracle\RDBMS901\ctx\admin\dr0csys.sql change_on_install DRSYS TEMP;
connect CTXSYS/change_on_install
@C:\oracle\RDBMS901\ctx\admin\dr0inst C:\oracle\RDBMS901\bin\oractxx9.dll;
@C:\oracle\RDBMS901\ctx\admin\defaults\drdefus.sql;
spool off
exit;
