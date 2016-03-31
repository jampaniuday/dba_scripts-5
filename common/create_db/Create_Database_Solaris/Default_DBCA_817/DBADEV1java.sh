#!/bin/sh
ORACLE_SID=DBADEV1
export ORACLE_SID

/u01/app/oracle/product/8.1.7/bin/svrmgrl << EOF
spool /u01/app/oracle/admin/DBADEV1/create/jvminst.log;
connect internal/oracle
@/u01/app/oracle/product/8.1.7/javavm/install/initjvm.sql;
spool off
spool /u01/app/oracle/admin/DBADEV1/create/initxml.log;
@/u01/app/oracle/product/8.1.7/oracore/admin/initxml.sql
spool off
spool /u01/app/oracle/admin/DBADEV1/create/catxsu.log;
@/u01/app/oracle/product/8.1.7/rdbms/admin/catxsu.sql
spool off
spool /u01/app/oracle/admin/DBADEV1/create/init_jis.log;
@/u01/app/oracle/product/8.1.7/javavm/install/init_jis.sql
spool off
spool /u01/app/oracle/admin/DBADEV1/create/jisja.log;
@/u01/app/oracle/product/8.1.7/javavm/install/jisja.sql
spool off
spool /u01/app/oracle/admin/DBADEV1/create/jisaephc.log;
@/u01/app/oracle/product/8.1.7/javavm/install/jisaephc.sql
spool off
spool /u01/app/oracle/admin/DBADEV1/create/initplgs.log;
@/u01/app/oracle/product/8.1.7/rdbms/admin/initplgs.sql
spool off
spool /u01/app/oracle/admin/DBADEV1/create/initjsp.log;
@/u01/app/oracle/product/8.1.7/jsp/install/initjsp.sql
spool off
spool /u01/app/oracle/admin/DBADEV1/create/jspja.log;
@/u01/app/oracle/product/8.1.7/jsp/install/jspja.sql
spool off
spool /u01/app/oracle/admin/DBADEV1/create/initplsj.log;
@/u01/app/oracle/product/8.1.7/rdbms/admin/initplsj.sql
spool off
spool /u01/app/oracle/admin/DBADEV1/create/initjms.log;
@/u01/app/oracle/product/8.1.7/rdbms/admin/initjms.sql
spool off
spool /u01/app/oracle/admin/DBADEV1/create/initrepapi.log;
@/u01/app/oracle/product/8.1.7/rdbms/admin/initrepapi.sql
spool off
spool /u01/app/oracle/admin/DBADEV1/create/initsoxx.log;
@/u01/app/oracle/product/8.1.7/rdbms/admin/initsoxx.sql
spool off
exit;

EOF
