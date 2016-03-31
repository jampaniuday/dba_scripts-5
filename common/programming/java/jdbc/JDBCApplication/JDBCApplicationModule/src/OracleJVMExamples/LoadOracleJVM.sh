:
# +----------------------------------------------------------------------------+
# | FILE  : LoadOracleJVM.sh                                                   |
# | NOTES : UNIX shell script to load Java objects to the Oracle JVM.          |
# |         There are two methods for connecting to the database: oci8 and     |
# |         thin. As an example, here are both methods:                        |
# |                                                                            |
# |  loadjava -user scott/tiger@jeffdb_melody.idevelopment.info -oci8 ...      |
# |  loadjava -user scott/tiger@melody.idevelopment.info:1521:JEFFDB -thin ... |
# -----------------------------------------------------------------------------+

userName=$1
userPassword=$2
serviceName=$3

echo Running loadjava for UNIX:
echo "userName     = $1"
echo "userPassword = $2"
echo "serviceName  = $3"

loadjava -user ${userName}/${userPassword}@${serviceName} -oci8 -resolve -verbose OracleConnection.java TestConnection.java
