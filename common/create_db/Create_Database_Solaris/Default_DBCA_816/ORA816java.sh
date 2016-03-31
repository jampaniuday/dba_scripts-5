#!/bin/sh
ORACLE_SID=ORA816
export ORACLE_SID

/u01/app/oracle/product/8.1.6/bin/svrmgrl << EOF
spool /u01/app/oracle/admin/ORA816/create/jvminst.log;
connect internal/oracle
@/u01/app/oracle/product/8.1.6/javavm/install/initjvm.sql;
spool off
spool /u01/app/oracle/admin/ORA816/create/initplsj.log;
@/u01/app/oracle/product/8.1.6/rdbms/admin/initplsj.sql
spool off
spool /u01/app/oracle/admin/ORA816/create/initaqjms.log;
@/u01/app/oracle/product/8.1.6/rdbms/admin/initaqjms.sql
spool off
spool /u01/app/oracle/admin/ORA816/create/initrepapi.log;
@/u01/app/oracle/product/8.1.6/rdbms/admin/initrepapi.sql
spool off
exit;

EOF
