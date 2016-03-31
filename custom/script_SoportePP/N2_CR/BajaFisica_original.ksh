# Detener aplicacion y abrir en read only #
###########################################
#!/bin/ksh

cd `dirname $0`

cd /home/oracle/scripts

export LD_ASSUME_KERNEL=2.4.1
export ORACLE_BASE=/softw/app/oracle
export ORACLE_HOME=/softw/app/oracle/product/10.2.0/db
export ORACLE_DATA=${ORACLE_BASE}/oradata;
export NLS_LANG=spanish_spain.WE8ISO8859P1;
export NLS_SORT=BINARY;
export ORACLE_OWNER=oracle;
export ORACLE_TERM=xterm
export PATH=$ORACLE_HOME/bin:$PATH
export NLS_LANG=SPANISH;
export ORA_NLS33=$ORACLE_HOME/ocommon/nls/admin/data;
export LD_LIBRARY_PATH=$ORACLE_HOME/lib:/lib:/usr/lib;
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/usr/local/lib:$LD_LIBRARY_PATH
export ORACLE_SID=$2

  echo "set time off;
        set timing off;
        set feedback off;
        set heading on;
        set linesize 200;
alter database recover managed standby database cancel;
exit;
" | sqlplus -s "/ as sysdba"
srvctl stop instance -d $1 -i $2
srvctl start instance -d $1 -i $2
