# Sube Fisica        #
######################
#!/bin/ksh

cd `dirname $0`

cd /home/oracle/n2

# export LD_ASSUME_KERNEL=2.4.1
export ORACLE_BASE=/softw/app/oracle
export CRS_HOME=${ORACLE_BASE}/product/10.2.0/crs
export ORACLE_HOME=$ORACLE_BASE/product/10.2.0/db;
export ORACLE_DATA=${ORACLE_BASE}/oradata;
export NLS_LANG=spanish_spain.WE8ISO8859P9;
export NLS_SORT=BINARY;
export ORACLE_OWNER=oracle;
export ORACLE_TERM=xterm
export PATH=${PATH}:${CRS_HOME}/bin:${ORACLE_HOME}/bin:${ORACLE_HOME}/OPatch:/usr/local/sbin:/usr/local/bin:.
#export NLS_LANG=SPANISH;
export ORA_NLS10=${ORACLE_HOME}/nls/data
export ORA_NLS33=$ORACLE_HOME/ocommon/nls/admin/data;
export LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:${CRS_HOME}/lib:${ORACLE_HOME}/lib:/usr/local/lib:/lib:/usr/lib;
export ORACLE_SID=$2

srvctl stop instance -d $1 -i $2
srvctl start instance -d $1 -i $2 -o mount

  echo "set time off;
        set timing off;
        set feedback off;
        set heading on;
        set linesize 200;
alter database recover managed standby database disconnect;
exit;
" | sqlplus -s "/ as sysdba"
