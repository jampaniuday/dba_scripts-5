connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\RDBMS901\assistants\dbca\logs\interMedia.log
@C:\oracle\RDBMS901\ord\im\admin\iminst.sql;
spool off
exit;
