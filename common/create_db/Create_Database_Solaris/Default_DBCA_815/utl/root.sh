#!/usr/bin/sh
#
# $Header
# $Copyright
#
#
# root.sh
#
# This script is intended to be run by root.  The script contains
# all the product installation actions that require root privileges.
#

# IMPORTANT NOTES - READ BEFORE RUNNING SCRIPT
#
# (1) ORACLE_HOME, ORACLE_OWNER, and ORACLE_SID can be defined in user's
#     environment to override default values defined in this script.
#
# (2) The environment variable LBIN (defined within the script) points to
#     the default local bin area.  Three executables will be moved there as
#     part of this script execution.
#
# (3) Define (if desired) LOG variable to name of log file.
#

#
# If LOG is not set, then send output to /dev/null
#

if [ x${LOG} = x ] -o [ ${LOG} = "" ];then
  LOG=/dev/null
else
  cp $LOG ${LOG}0 2>/dev/null
  echo "" > $LOG
fi

#
# Display abort message on interrupt.
#

trap 'echo "Oracle8 root.sh execution aborted!"|tee -a $LOG;exit' 1 2 3 15

#
# Enter log message
#

echo "Running Oracle8 root.sh script..."|tee -a $LOG

CHOWN=/usr/bin/chown
CHMOD=/usr/bin/chmod
AWK=/usr/bin/awk
ORATABLOC=/var/opt/oracle
ORATAB=${ORATABLOC}/oratab
LBIN=/usr/local/bin
TMPORATB=/var/tmp/oratab$$

#
# Default values set by Installer
#

ORACLE_SID_INST=ORA815
ORACLE_HOME=/u01/app/oracle/product/8.1.5
ORACLE_OWNER=73

#
# If this value is set by the installer are empty
# then use the corresponding environment variable
#

if [ x${ORACLE_SID_INST} = x ] -o [ ${ORACLE_SID_INST} = "" ]; then
  ORACLE_SID=${ORACLE_SID}
else
  ORACLE_SID=${ORACLE_SID_INST}
fi

# 
# It's ok if ORACLE_SID is not set -- it means a database 
# was not created in this ORACLE_HOME
#
if [ x${ORACLE_SID} = x ] -o [ ${ORACLE_SID} = "" ];then
  echo "ORACLE_SID is not set."|tee -a $LOG
fi

RUID=`/usr/bin/id|$AWK -F\( '{print $2}'|$AWK -F\) '{print $1}`
if [ ${RUID} != "root" ];then
  echo "You must be logged in as root to run root.sh."|tee -a $LOG
  echo "Log in as root and restart root.sh execution."|tee -a $LOG
  exit 1
fi

#
# Determine how to suppress newline with echo command.
#

case ${N}$C in
  "") if echo "\c"|grep c >/dev/null 2>&1;then
        N='-n'
      else
        C='\c'
      fi;;
esac

#

echo "\nThe following environment variables are set as:"|tee -a $LOG
echo "    ORACLE_OWNER= $ORACLE_OWNER"|tee -a $LOG
echo "    ORACLE_HOME=  $ORACLE_HOME"|tee -a $LOG
echo "    ORACLE_SID=   $ORACLE_SID"|tee -a $LOG

#
# Get name of local bin directory
#

echo ""
echo $N "Enter the full pathname of the local bin directory: $C"
DEFLT=${LBIN}; . $ORACLE_HOME/install/utl/read.sh; LBIN=$RDVAR
if [ ! -d $LBIN ];then
  echo "Creating ${LBIN} directory..."|tee -a $LOG
  mkdir -p ${LBIN} 2>&1|tee -a $LOG
  chmod 777 ${LBIN} 2>&1|tee -a $LOG
fi

#
# Make sure an oratab file exists on this system
#

if [ ! -s ${ORATAB} ];then
  echo "\nCreating ${ORATAB} file..."|tee -a $LOG
  if [ ! -d ${ORATABLOC} ];then
    mkdir -p ${ORATABLOC}
  fi

  cat <<!>> ${ORATAB}
#

# This file is used by ORACLE utilities.  It is created by root.sh
# and updated by the Database Configuration Assistant when creating
# a database.

# A colon, ':', is used as the field terminator.  A new line terminates
# the entry.  Lines beginning with a pound sign, '#', are comments.
#
# Entries are of the form:
#   \$ORACLE_SID:\$ORACLE_HOME:<N|Y>:
#
# The first and second fields are the system identifier and home
# directory of the database respectively.  The third filed indicates
# to the dbstart utility that the database should , "Y", or should not,
# "N", be brought up at system boot time.
#
# Multiple entries with the same \$ORACLE_SID are not allowed.
#
#
!

fi

$CHOWN $ORACLE_OWNER ${ORATAB}
chmod 664 ${ORATAB}

#
# If an entry exists in $ORATAB for this $SID and this $ORACLE_HOME,
# don't do anything.
#

FOUND=`grep "^${SID}:${ORACLE_HOME}:" ${ORATAB}`
if [ -z "${FOUND}" ];then

  #
  # If there is an old entry with no sid and same oracle home,
  # that entry will be marked as a comment.
  #
  
  FOUND_OLD=`grep "^*:${ORACLE_HOME}:" ${ORATAB}`
  if [ -n "${FOUND_OLD}" ];then
    sed -e "s?^*:$ORACLE_HOME:?# *:$ORACLE_HOME:?" $ORATAB > $TMPORATB
    cat $TMPORATB > $ORATAB
    rm -f $TMPORATB 2>/dev/null
  fi

  #
  # Only add oratab entry if ORACLE_SID = "".  Otherwise, Database
  # Configuration Assistant will add the entry when the database
  # is actually created.
  #

  if [ x${ORACLE_SID} = x ] -o [ ${ORACLE_SID} = "" ];then
     echo "Adding entry to ${ORATAB} file..."|tee -a $LOG
  cat <<!>> ${ORATAB}
*:$ORACLE_HOME:N
!
  else
     echo "Entry will be added to the ${ORATAB} file by"|tee -a $LOG
     echo "Database Configuration Assistant when a database is created"|tee -a $LOG
  fi 

else

  echo "Entry already exists in the ${ORATAB} file for this"|tee -a $LOG
  echo "ORACLE_SID and ORACLE_HOME.  No changes made to file."|tee -a $LOG
fi

#
# Move files to LBIN, and set permissions
#

DBHOME=$ORACLE_HOME/bin/dbhome
ORAENV=$ORACLE_HOME/bin/oraenv
CORAENV=$ORACLE_HOME/bin/coraenv
FILES="$DBHOME $ORAENV $CORAENV"

for f in $FILES ; do
  if [ -f $f ] ; then
    $CHMOD 755 $f  2>&1 2>> $LOG
    cp $f $LBIN  2>&1 2>>  $LOG    
    $CHOWN $ORACLE_OWNER $LBIN/`echo $f | $AWK -F/ '{print $NF}'` 2>&1 2>> $LOG
  fi
done

echo "Finished running generic part of root.sh script."|tee -a $LOG
echo "Now product-specific root actions will be performed."|tee -a $LOG



