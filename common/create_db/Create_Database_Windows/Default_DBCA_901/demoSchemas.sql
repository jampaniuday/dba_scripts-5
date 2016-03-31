spool C:\oracle\RDBMS901\assistants\dbca\logs\demoSchemas.log
connect SYSTEM/manager
@C:\oracle\RDBMS901\demo\schema\human_resources\hr_main.sql change_on_install EXAMPLE TEMP change_on_install C:\oracle\RDBMS901\assistants\dbca\logs\;
connect SYSTEM/manager
@C:\oracle\RDBMS901\demo\schema\order_entry\oe_main.sql change_on_install EXAMPLE TEMP change_on_install change_on_install C:\oracle\RDBMS901\assistants\dbca\logs\;
connect SYSTEM/manager
@C:\oracle\RDBMS901\demo\schema\product_media\pm_main.sql change_on_install EXAMPLE TEMP change_on_install change_on_install C:\oracle\RDBMS901\demo\schema\product_media\ C:\oracle\RDBMS901\assistants\dbca\logs\ C:\oracle\RDBMS901\demo\schema\product_media\;
connect SYSTEM/manager
@C:\oracle\RDBMS901\demo\schema\sales_history\sh_main.sql change_on_install EXAMPLE TEMP change_on_install C:\oracle\RDBMS901\demo\schema\sales_history\ C:\oracle\RDBMS901\assistants\dbca\logs\;
@C:\oracle\RDBMS901\demo\schema\sales_history\sh_olp_c.sql;
connect SYSTEM/manager
@C:\oracle\RDBMS901\demo\schema\shipping\qs_main.sql change_on_install EXAMPLE TEMP manager change_on_install change_on_install C:\oracle\RDBMS901\assistants\dbca\logs\;
spool off
exit;
