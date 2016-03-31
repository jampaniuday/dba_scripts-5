connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\ora920\assistants\dbca\logs\interMedia.log
@C:\oracle\ora920\ord\im\admin\iminst.sql;
spool off
exit;
