# programacion del backup
# autor Luis Pedro
# 20081126 primera version

#carga variables de ambiente
PATH=/usr/bin:/etc:/usr/sbin:/usr/ucb:$HOME/bin:/usr/bin/X11:/sbin:.
export PATH
export DISPLAY=172.23.17.36:0.0
export AIXTHREAD_SCOPE=S
export ORACLE_SID=MUNI1
export ORACLE_BASE=/u01/app/oracle
export ORACLE_HOME=$ORACLE_BASE/product/10.2.0/db_1
export ORA_ASM_HOME=$ORACLE_BASE/product/10.2.0/asm
export ORA_CRS_HOME=/u01/crs/oracle/product/crs
export PATH=$ORACLE_HOME/bin:$ORA_ASM_HOME/bin:$ORA_CRS_HOME/bin:$PATH:.
export NLS_LANG=AMERICAN_AMERICA.WE8ISO8859P1



#ejecuta el backup
rman nocatalog << EOF
connect target /
crosscheck archivelog all;
delete noprompt expired archivelog all;
crosscheck backupset;
delete noprompt expired backupset;
crosscheck copy;
delete noprompt expired copy;
report obsolete;
delete noprompt obsolete;
delete noprompt archivelog until time 'sysdate -4';
exit
EOF


