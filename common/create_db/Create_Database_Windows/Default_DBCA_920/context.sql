connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\ora920\assistants\dbca\logs\context.log
@C:\oracle\ora920\ctx\admin\dr0csys change_on_install DRSYS TEMP;
connect CTXSYS/change_on_install
@C:\oracle\ora920\ctx\admin\dr0inst C:\oracle\ora920\bin\oractxx9.dll;
@C:\oracle\ora920\ctx\admin\defaults\dr0defin.sql AMERICAN;
spool off
exit;
