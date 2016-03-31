spool /u01/app/oracle/product/9.0.1/assistants/dbca/logs/demoSchemas.log
connect SYSTEM/manager
@/u01/app/oracle/product/9.0.1/demo/schema/human_resources/hr_main.sql change_on_install EXAMPLE TEMP change_on_install /u01/app/oracle/product/9.0.1/assistants/dbca/logs/;
connect SYSTEM/manager
@/u01/app/oracle/product/9.0.1/demo/schema/order_entry/oe_main.sql change_on_install EXAMPLE TEMP change_on_install change_on_install /u01/app/oracle/product/9.0.1/assistants/dbca/logs/;
connect SYSTEM/manager
@/u01/app/oracle/product/9.0.1/demo/schema/product_media/pm_main.sql change_on_install EXAMPLE TEMP change_on_install change_on_install /u01/app/oracle/product/9.0.1/demo/schema/product_media/ /u01/app/oracle/product/9.0.1/assistants/dbca/logs/ /u01/app/oracle/product/9.0.1/demo/schema/product_media/;
connect SYSTEM/manager
@/u01/app/oracle/product/9.0.1/demo/schema/sales_history/sh_main.sql change_on_install EXAMPLE TEMP change_on_install /u01/app/oracle/product/9.0.1/demo/schema/sales_history/ /u01/app/oracle/product/9.0.1/assistants/dbca/logs/;
@/u01/app/oracle/product/9.0.1/demo/schema/sales_history/sh_olp_c.sql;
connect SYSTEM/manager
@/u01/app/oracle/product/9.0.1/demo/schema/shipping/qs_main.sql change_on_install EXAMPLE TEMP manager change_on_install change_on_install /u01/app/oracle/product/9.0.1/assistants/dbca/logs/;
spool off
exit;
