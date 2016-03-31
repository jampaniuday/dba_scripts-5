
spool demoSchemas.log

define ORACLE_HOME = &1
define ORACLE_SID  = &2

prompt +-- RUNNING => demo\schema\human_resources\hr_main.sql ...
connect SYSTEM/manager
@%ORACLE_HOME%\demo\schema\human_resources\hr_main.sql change_on_install EXAMPLE TEMP change_on_install &ORACLE_HOME\assistants\dbca\logs\;

prompt +-- RUNNING => demo\schema\order_entry\oe_main.sql ...
connect SYSTEM/manager
@%ORACLE_HOME%\demo\schema\order_entry\oe_main.sql change_on_install EXAMPLE TEMP change_on_install change_on_install &ORACLE_HOME\assistants\dbca\logs\;

prompt +-- RUNNING => demo\schema\product_media\pm_main.sql ...
connect SYSTEM/manager
@%ORACLE_HOME%\demo\schema\product_media\pm_main.sql change_on_install EXAMPLE TEMP change_on_install change_on_install &ORACLE_HOME\demo\schema\product_media\ &ORACLE_HOME\assistants\dbca\logs\ &ORACLE_HOME\demo\schema\product_media\;

prompt +-- RUNNING => demo\schema\sales_history\sh_main.sql ...
connect SYSTEM/manager
@%ORACLE_HOME%\demo\schema\sales_history\sh_main.sql change_on_install EXAMPLE TEMP change_on_install &ORACLE_HOME\demo\schema\sales_history\ &ORACLE_HOME\assistants\dbca\logs\;
prompt +-- RUNNING => demo\schema\sales_history\sh_olp_c.sql ...
@%ORACLE_HOME%\demo\schema\sales_history\sh_olp_c.sql;

prompt +-- RUNNING => demo\schema\shipping\qs_main.sql ...
connect SYSTEM/manager
@%ORACLE_HOME%\demo\schema\shipping\qs_main.sql change_on_install EXAMPLE TEMP manager change_on_install change_on_install &ORACLE_HOME\assistants\dbca\logs\;

spool off

exit;
