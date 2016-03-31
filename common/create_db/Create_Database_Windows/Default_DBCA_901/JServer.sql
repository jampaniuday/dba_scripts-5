connect SYS/change_on_install as SYSDBA
set echo on
spool C:\oracle\RDBMS901\assistants\dbca\logs\JServer.log
@C:\oracle\RDBMS901\javavm\install\initjvm.sql;
@C:\oracle\RDBMS901\xdk\admin\initxml.sql;
@C:\oracle\RDBMS901\xdk\admin\xmlja.sql;
@C:\oracle\RDBMS901\javavm\install\init_jis.sql C:\oracle\RDBMS901;
@C:\oracle\RDBMS901\javavm\install\jisaephc.sql C:\oracle\RDBMS901;
@C:\oracle\RDBMS901\javavm\install\jisja.sql C:\oracle\RDBMS901;
@C:\oracle\RDBMS901\javavm\install\jisdr.sql 2481 2482;
@C:\oracle\RDBMS901\jsp\install\initjsp.sql;
@C:\oracle\RDBMS901\jsp\install\jspja.sql;
@C:\oracle\RDBMS901\rdbms\admin\initjms.sql;
@C:\oracle\RDBMS901\rdbms\admin\initrapi.sql;
@C:\oracle\RDBMS901\rdbms\admin\initsoxx.sql;
@C:\oracle\RDBMS901\rdbms\admin\initapcx.sql;
@C:\oracle\RDBMS901\rdbms\admin\initcdc.sql;
@C:\oracle\RDBMS901\rdbms\admin\initqsma.sql;
@C:\oracle\RDBMS901\rdbms\admin\initsjty.sql;
@C:\oracle\RDBMS901\rdbms\admin\initaqhp.sql;
@C:\oracle\RDBMS901\com\java\loadlib.sql;
spool off
exit;
