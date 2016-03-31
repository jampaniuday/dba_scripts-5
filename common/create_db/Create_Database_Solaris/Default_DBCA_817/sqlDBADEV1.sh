#!/bin/sh
ORACLE_SID=DBADEV1
export ORACLE_SID


ORACLE_HOME=/u01/app/oracle/product/8.1.7
export ORACLE_HOME

./DBADEV1run.sh
./DBADEV1run1.sh
./DBADEV1run2.sh
./DBADEV1replicate.sh
./DBADEV1java.sh
./DBADEV1ordinst.sh
./DBADEV1iMedia.sh
./DBADEV1drsys.sh
./DBADEV1context.sh
./DBADEV1spatial1.sh
./DBADEV1sqlplus.sh
./DBADEV1timeseries.sh
./DBADEV1virage.sh
./DBADEV1alterTablespace.sh
