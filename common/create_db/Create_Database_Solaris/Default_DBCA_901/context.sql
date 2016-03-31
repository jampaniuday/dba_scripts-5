connect SYS/change_on_install as SYSDBA
set echo on
spool /u01/app/oracle/product/9.0.1/assistants/dbca/logs/context.log
@/u01/app/oracle/product/9.0.1/ctx/admin/dr0csys change_on_install DRSYS TEMP;
connect CTXSYS/change_on_install
@/u01/app/oracle/product/9.0.1/ctx/admin/dr0inst /u01/app/oracle/product/9.0.1/ctx/lib/libctxx9.so;
@/u01/app/oracle/product/9.0.1/ctx/admin/defaults/drdefus.sql;
spool off
exit;
