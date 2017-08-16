#!/bin/bash
#
# Script used to cleanup Log and trace files for Oracle 11g and 12c.
#
# Cleans:      Audit logs.
# 			   Trace Logs.
#			   Alert Logs
#              Listener Logs
# 			   Core Dump files.
# 			   Incident files.
# 			   Health Monitor files.
# 			   UTSCDMP files.
# 			   STAGE files.
# 			   SWEEP files.
# 			   User dump dest.
#              Clusterware Logs.
#
# Rotates:     Listener Logs
#
# Scheduling:  00 00 * * * /home/oracle/cleanlog.sh -d 31 > /home/oracle/cleanlog.log 2>&1
#

# Initialization of maintenance variables
RM="rm -f"
RMDIR="rm -rf"
LS="ls -l"
MV="mv"
TOUCH="touch"
TESTTOUCH="echo touch"
TESTMV="echo mv"
TESTRM=$LS
TESTRMDIR=$LS
INSTUSER=0
TNSLUSER=0
SUCCESS=0
FAILURE=1
TEST=0
HOSTNAME=`hostname -s`
ORAENV="oraenv"
TODAY=`date +%Y%m%d`
ORIGPATH=/usr/local/bin:$PATH
ORIGLD=$LD_LIBRARY_PATH
export PATH=$ORIGPATH

# ******************************
# Declaration of functions
# ******************************

# Usage function.
f_usage(){
  echo "Usage: `basename $0` -d DAYS [-a DAYS] [-b DAYS] [-c DAYS] [-e DAYS] [-f DAYS] [-g DAYS] [-i DAYS] [-j DAYS] [-k DAYS] [-l DAYS] [-m DAYS] [-n DAYS] [-t] [-h]"
  echo "       -d = Mandatory default number of days to keep log files that are not explicitly passed as parameters."
  echo "       -a = Optional number of days to keep Audit logs."
  echo "       -b = Optional number of days to keep Alert logs."
  echo "       -c = Optional number of days to keep Trace Logs."
  echo "       -e = Optional number of days to keep Listener Logs."
  echo "       -f = Optional number of days to keep Core Dump files."
  echo "       -g = Optional number of days to keep Incident files."
  echo "       -i = Optional number of days to keep Health Monitor files."
  echo "       -j = Optional number of days to keep UTSCDMP files."
  echo "       -k = Optional number of days to keep STAGE files."
  echo "       -l = Optional number of days to keep SWEEP files."
  echo "       -m = Optional number of days to keep user dumps."
  echo "       -n = Optional number of days to keep clusterware log files."
  echo "       -h = Optional help mode."
  echo "       -t = Optional test mode. Does not delete any files."
}

# Function used to check the validity of days.
f_checkdays(){
  if [ $1 -lt 1 ]; then
    echo "ERROR: Number of days is invalid."
    exit $FAILURE
  fi
  if [ $? -ne 0 ]; then
    echo "ERROR: Number of days is invalid."
    exit $FAILURE
  fi
} 

# Function used to cut log files.
f_cutlog(){
  # Set name of log file.
  LOG_FILE=$1
  CUT_FILE=${LOG_FILE}.${TODAY}
  FILESIZE=`ls -l $LOG_FILE | awk '{print $5}'`

  # Cut the log file if it has not been cut today.
  if [ -f $CUT_FILE.gz ]; then
    echo "Log Already Cut Today: $CUT_FILE.gz"
  elif [ ! -f $LOG_FILE ]; then
    echo "Log File Does Not Exist: $LOG_FILE"
  elif [ $FILESIZE -eq 0 ]; then
    echo "Log File Has Zero Size: $LOG_FILE"
  else
    # Cut file.
    echo "Cutting Log File: $LOG_FILE"
    $MV $LOG_FILE $CUT_FILE
    $TOUCH $LOG_FILE
    gzip $CUT_FILE
  fi
}

# Function used to delete log files.
f_deletelog(){
  # Set name of log file.
  CLEAN_LOG=$1
  # Set time limit and confirm it is valid.
  CLEAN_DAYS=$2
  f_checkdays $CLEAN_DAYS
  
  # Delete old log files if they exist.
  find $CLEAN_LOG.[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9].gz -type f -mtime +$CLEAN_DAYS -exec $RM {} \; 2>/dev/null
}

# Check Oracle instance number and number of listeners processes per user
f_checkinst(){
  # Checking number of Oracle instance by user
  INSTUSER=`ps -fe| grep pmon|grep $USER | grep -v grep | wc -l`
  # Checking the number of listener processes per user
  TNSLUSER=`ps -fe | grep tnslsnr | grep $USER | grep -v grep | wc -l`
  # If the user is not root and does not have associated database instances
  if [ $USER != 'root' ] && [ $INSTUSER == 0 ]; then
    echo 'ERROR: User not valid for manteinance...'
    exit $FAILURE
  fi
}

# Validate if the ADRCI Purge instruction is executed based on the number of instances 
# of the current user and if is the owner home directory
f_valadr(){
AdrStat=0
if [ ! -z  $(echo $1 | grep user_$USER ) ]; then
  AdrStat=1
fi
if [ -z  $(echo $1 | grep user_ ) ] && [ $INSTUSER -gt 0 ]; then
  AdrStat=1
fi
}

# Function Purge ADR contents
f_purgeadr(){

  # Set time limit and confirm it is valid.
  CLEAN_DAYS=$1
  f_checkdays $CLEAN_DAYS
  minutes=$((1440 * $CLEAN_DAYS))

  echo "***************************************************************************"
  echo ""
  echo "INFO: adrci purge started at `date`"
  adrci exec="show homes"|grep -v : | while read file_line
  do
     f_valadr $file_line
     if [ $AdrStat == 1  ]; then
       echo "INFO: adrci purging diagnostic destination" $file_line
       echo "INFO: purging $2 older than $CLEAN_DAYS days."
       adrci exec="set homepath $file_line;purge -age $minutes -type $2"
     fi
  done
  echo ""
}
  
# Function used to get database parameter values.
f_getparameter(){
  if [ -z "$1" ]; then
    return
  fi
  PARAMETER=$1
  sqlplus -s /nolog <<EOF | awk -F= "/^a=/ {print \$2}"
set head off pagesize 0 feedback off linesize 200
whenever sqlerror exit 1
conn / as sysdba
select 'a='||value from v\$parameter where name = '$PARAMETER';
EOF
}

# Function to get unique list of directories.
f_getuniq(){
  if [ -z "$1" ]; then
    return
  fi
  dir=($1)
  e=`echo "${dir[@]}" | tr ' ' '\n' | sort -u | tr '\n' ' '`
  echo $e
}

# ********************************
# Script body start
# ********************************
if [ $# -lt 1 ]; then
  f_usage
  exit $FAILURE
fi

# Parse the command line options.
while getopts a:b:c:d:e:f:g:i:j:k:l:m:n:th OPT; do
  case $OPT in
    a) ADAYS=$OPTARG
       ;;
    b) BDAYS=$OPTARG
       ;;
    c) CDAYS=$OPTARG
       ;;
    d) DDAYS=$OPTARG
       ;;
    e) EDAYS=$OPTARG
       ;;
    f) FDAYS=$OPTARG
       ;;
    g) GDAYS=$OPTARG
       ;;
    i) IDAYS=$OPTARG
       ;;
    j) JDAYS=$OPTARG
       ;;
    k) KDAYS=$OPTARG
       ;;
    l) LDAYS=$OPTARG
       ;;
    m) MDAYS=$OPTARG
       ;;
    n) NDAYS=$OPTARG
       ;;
    t) TEST=1
       ;;
    h) f_usage
       exit 0
       ;;
    *) f_usage
       exit 2
       ;;
  esac
done
shift $(($OPTIND - 1))

# Ensure the default number of days is passed.
if [ -z "$DDAYS" ]; then
  echo "ERROR: The default days parameter is mandatory."
  f_usage
  exit $FAILURE
fi
f_checkdays $DDAYS
f_checkinst

echo "`basename $0` Started `date`."

# Use test mode if specified.
if [ $TEST -eq 1 ]
then
  RM=$TESTRM
  RMDIR=$TESTRMDIR
  MV=$TESTMV
  TOUCH=$TESTTOUCH
  echo "Running in TEST mode."
fi

# Set the number of days to the default if not explicitly set.
# Clean logs only if the current user has database instances
if [ $INSTUSER -ne 0 ]; then
  ADAYS=${ADAYS:-$DDAYS}; echo "Keeping audit logs for $ADAYS days."; f_checkdays $ADAYS
  MDAYS=${MDAYS:-$DDAYS}; echo "Keeping user logs for $MDAYS days."; f_checkdays $MDAYS
  NDAYS=${NDAYS:-$DDAYS}; echo "Keeping clusterware logs for $NDAYS days."; f_checkdays $NDAYS
fi
# Clean logs only if the current user has associated listener processes
if [ $TNSLUSER -ne 0 ]; then
  EDAYS=${EDAYS:-$DDAYS}; echo "Keeping listener logs for $EDAYS days."; f_checkdays $EDAYS
fi
BDAYS=${BDAYS:-$DDAYS}; echo "Keeping alert logs for $BDAYS days."; f_checkdays $BDAYS
CDAYS=${CDAYS:-$DDAYS}; echo "Keeping trace logs for $CDAYS days."; f_checkdays $CDAYS
FDAYS=${FDAYS:-$DDAYS}; echo "Keeping core dump files for $FDAYS days."; f_checkdays $FDAYS
GDAYS=${GDAYS:-$DDAYS}; echo "Keeping incident files for $GDAYS days."; f_checkdays $GDAYS
IDAYS=${IDAYS:-$DDAYS}; echo "Keeping health monitor files for $IDAYS days."; f_checkdays $IDAYS
JDAYS=${JDAYS:-$DDAYS}; echo "Keeping UTSCDMP files for $JDAYS days."; f_checkdays $JDAYS
KDAYS=${KDAYS:-$DDAYS}; echo "Keeping STAGE files for $KDAYS days."; f_checkdays $KDAYS
LDAYS=${LDAYS:-$DDAYS}; echo "Keeping SWEEP for $LDAYS days."; f_checkdays $LDAYS

echo ""
# Check for the oratab file.
if [ -f /var/opt/oracle/oratab ]; then
  ORATAB=/var/opt/oracle/oratab
elif [ -f /etc/oratab ]; then
  ORATAB=/etc/oratab
else
  echo "ERROR: Could not find oratab file."
  exit $FAILURE
fi

# Build list of distinct Oracle Home directories.
OH=`egrep -i ":Y|:N" $ORATAB | grep -v "^#" | grep -v "\*" | cut -d":" -f2 | sort | uniq`

# Exit if there are not Oracle Home directories.
if [ -z "$OH" ]; then
  echo "No Oracle Home directories to clean."
  exit $SUCCESS
fi

# Get the list of running databases.
# List the server instances started with the current user
SIDS=`ps -fe| grep pmon|grep $USER | grep -v grep | awk -F_ '{print $3}' | sort`

# Gather information for each running database.
for ORACLE_SID in `echo $SIDS`
do
  # Set the Oracle environment.
  ORAENV_ASK=NO
  export ORAENV_ASK
  # Temporarily stores the Oracle_home variable to allow renaming if it is in RAC
  SID=$ORACLE_SID
  
  # Uncomment the following 7 lines if the server is in RAC
  if [ -z  $(echo $ORACLE_SID|grep ASM) ] && [ -z $(echo $ORACLE_SID|grep MGMTD) ];then 
    if [ ! -z $(echo $ORACLE_SID | grep '1') ]; then
      ORACLE_SID=`echo $ORACLE_SID | sed -e 's/1//g'`
    elif [ ! -z $(echo $ORACLE_SID | grep '2') ]; then
      ORACLE_SID=`echo $ORACLE_SID | sed -e 's/2//g'`
    fi
  fi
  export ORACLE_SID
  . $ORAENV
  export ORACLE_SID=$SID
  
  if [ $? -ne 0 ]; then
    echo "Could not set Oracle environment for $ORACLE_SID."
  else
    export LD_LIBRARY_PATH=$ORACLE_HOME/lib:$ORIGLD
    ORAENV_ASK=YES
    echo "ORACLE_SID: $ORACLE_SID"

    # Get the audit_dump_dest.
    ADUMPDEST=`f_getparameter audit_file_dest`
    if [ ! -z "$ADUMPDEST" ] && [ -d "$ADUMPDEST" 2>/dev/null ]; then
      echo "  Audit Dump Dest: $ADUMPDEST"
      ADUMPDIRS="$ADUMPDIRS $ADUMPDEST"
    fi

    # Get the standard audit directory if present.
    if [ -d $ORACLE_HOME/rdbms/audit ]; then
      ADUMPDIRS="$ADUMPDIRS $ORACLE_HOME/rdbms/audit"
    fi
	
    # Get the user_dump_dest.
    MDUMPDEST=`f_getparameter user_dump_dest`
    echo "  User Dump Dest: $MDUMPDEST"
    if [ ! -z "$MDUMPDEST" ] && [ -d "$MDUMPDEST" ]; then
      MDUMPDIRS="$MDUMPDIRS $MDUMPDEST"
    fi
	
    # Get the Clusterware log directory.
    if [ -d $ORACLE_HOME/log/$HOSTNAME ]; then
      echo "  Clusterware Dir: $ORACLE_HOME/log/$HOSTNAME"
    fi
	
    # Get the Cluster Ready Services Daemon (crsd) log directory if present.
    if [ -d $ORACLE_HOME/log/$HOSTNAME/crsd ]; then
      CRSLOGDIRS="$CRSLOGDIRS $ORACLE_HOME/log/$HOSTNAME/crsd"
    fi
    
    # Get the  Oracle Cluster Registry (OCR) log directory if present.
    if [ -d $ORACLE_HOME/log/$HOSTNAME/client ]; then
      OCRLOGDIRS="$OCRLOGDIRS $ORACLE_HOME/log/$HOSTNAME/client"
    fi
    
    # Get the Cluster Synchronization Services (CSS) log directory if present.
    if [ -d $ORACLE_HOME/log/$HOSTNAME/cssd ]; then
      CSSLOGDIRS="$CSSLOGDIRS $ORACLE_HOME/log/$HOSTNAME/cssd"
    fi
    
    # Get the Event Manager (EVM) log directory if present.
    if [ -d $ORACLE_HOME/log/$HOSTNAME/evmd ]; then
      EVMLOGDIRS="$EVMLOGDIRS $ORACLE_HOME/log/$HOSTNAME/evmd"
    fi
    
    # Get the RACG log directory if present.
    if [ -d $ORACLE_HOME/log/$HOSTNAME/racg ]; then
      RACGLOGDIRS="$RACGLOGDIRS $ORACLE_HOME/log/$HOSTNAME/racg"
    fi
  fi
  
done
echo ""

# Do cleanup for each Oracle Home.
# If current User SO have instances of Oracle database
if [ $INSTUSER -gt 0 ]; then

  # Clean the audit_file_dest directories.
  if [ ! -z "$ADUMPDIRS" ]; then
    for DIR in `f_getuniq "$ADUMPDIRS"`; do
      if [ -d $DIR ]; then
        echo "Cleaning Audit File Directory: $DIR"
        find $DIR -type f -name "*.aud" -mtime +$ADAYS -exec $RM {} \; 2>/dev/null
      fi
    done
  fi
  
  # Clean the user_dump_dest directories.
  if [ ! -z "$MDUMPDIRS" ]; then
    for DIR in `f_getuniq "$MDUMPDIRS"`; do
      if [ -d $DIR ]; then
        echo "Cleaning User Dump Destination: $DIR"
        find $DIR -type f -name "*.trc" -mtime +$MDAYS -exec $RM {} \; 2>/dev/null
      fi
    done
  fi
  
  # Cluster Ready Services Daemon (crsd) Log Files
  for DIR in `f_getuniq "$CRSLOGDIRS $OCRLOGDIRS $CSSLOGDIRS $EVMLOGDIRS $RACGLOGDIRS"`; do
    if [ -d $DIR ]; then
      echo "Cleaning Clusterware Directory: $DIR"
      find $DIR -type f -name "*.log" -mtime +$NDAYS -exec $RM {} \; 2>/dev/null
    fi
  done
  echo ""
fi

# Test for adrci is not permit temporaly...
if [ $TEST -eq 0 ]; then

  # Clean Alert Log files
  echo "Cleaning Alert Log Destination..."
  f_purgeadr $BDAYS "ALERT"

  # Clean Trace Log files
  echo "Cleaning Trace Log Destination..."
  f_purgeadr $CDAYS "TRACE"

  # Clean Core Dump files
  echo "Cleaning Core Dump files Destination..."
  f_purgeadr $FDAYS "CDUMP"

  # Clean Incident files
  echo "Cleaning Incident files Destination..."
  f_purgeadr $GDAYS "INCIDENT"

  # Clean Health Monitor files
  echo "Cleaning Health Monitor files Destination..."
  f_purgeadr $IDAYS "HM"

  # Clean UTSCDMP files
  echo "Cleaning UTSCDMP files Destination..."
  f_purgeadr $JDAYS "UTSCDMP"

  # Clean STAGE files
  echo "Cleaning STAGE files Destination..."
  f_purgeadr $KDAYS "STAGE"
	
  # Clean SWEEP files
  echo "Cleaning SWEEP files Destination..."
  f_purgeadr $LDAYS "SWEEP"
  echo "***************************************************************************"
  echo ""
fi

# Clean Listener Log Files.
# Get the list of running listeners. It is assumed that if the listener is not running, the log file does not need to be cut.
ps -fe | grep tnslsnr | grep $USER | grep -v grep |awk '{print $8,$9,$10,$11}' | while read LSNR; do
     
  # Derive the lsnrctl path from the tnslsnr process path.
  TNSLSNR=`echo $LSNR | awk '{print $1}'`
  ORACLE_PATH=`dirname $TNSLSNR`
  ORACLE_HOME=`dirname $ORACLE_PATH`
  PATH=$ORACLE_PATH:$ORIGPATH
  LD_LIBRARY_PATH=$ORACLE_HOME/lib:$ORIGLD
  LSNRCTL=$ORACLE_PATH/lsnrctl
  echo "Listener Control Command: $LSNRCTL"
  
  # Derive the listener name from the running process.
  LSNRNAME=`echo $LSNR | awk '{print $2}' | tr "[:upper:]" "[:lower:]"`
  echo "Listener Name: $LSNRNAME"
  
  # Get the listener version.
  LSNRVER=`$LSNRCTL version | grep "LSNRCTL" | grep "Version" | awk '{print $5}' | awk -F. '{print $1}'`
  echo "Listener Version: $LSNRVER"

  # Get the TNS_ADMIN variable.
  echo "Initial TNS_ADMIN: $TNS_ADMIN"
  unset TNS_ADMIN
  TNS_ADMIN=`$LSNRCTL status $LSNRNAME | grep "Listener Parameter File" | awk '{print $4}'`
  # If tns_admin not empty
  if [ ! -z $TNS_ADMIN ]; then
    export TNS_ADMIN=`dirname $TNS_ADMIN`
  # If empty tns_admin
  else
    export TNS_ADMIN=$ORACLE_HOME/network/admin
  fi
  echo "Network Admin Directory: $TNS_ADMIN"

  # If the listener is 11g or 12c, get the diagnostic dest, etc...
  if [ $LSNRVER -ge 11 ] || [ $LSNRVER -ge 12 ]; then
    # Get the listener trace file name.
    LSNRLOG=`lsnrctl<<EOF | grep trc_directory | awk '{print $6"/"$1".log"}'
set current_listener $LSNRNAME
show trc_directory
EOF`
    echo "Listener Log File: $LSNRLOG"

  # If 10g or lower, do not use diagnostic dest.
  else
    # Get the listener log file location.
    LSNRLOG=`$LSNRCTL status $LSNRNAME | grep "Listener Log File" | awk '{print $4}'`
  fi

  # See if the listener is logging.
  if [ -z "$LSNRLOG" ]; then
    echo "Listener Logging is OFF. Not rotating the listener log."
  # See if the listener log exists.
  elif  [ ! -r "$LSNRLOG" ]; then
    echo "Listener Log Does Not Exist: $LSNRLOG"
  # See if the listener log has been cut today.
  elif [ -f $LSNRLOG.$TODAY.gz ]; then
    echo "Listener Log Already Cut Today: $LSNRLOG.$TODAY.gz"
  # Cut the listener log if the previous two conditions were not met.
  else  
    # Disable logging.
    $LSNRCTL <<EOF
set current_listener $LSNRNAME
set log_status off
EOF

    # Cut the listener log file.
    f_cutlog $LSNRLOG

    # Enable logging.
    $LSNRCTL <<EOF
set current_listener $LSNRNAME
set log_status on
EOF

    # Delete old listener logs.
    f_deletelog $LSNRLOG $EDAYS

  fi
  echo ""
  echo ""
done

echo "`basename $0` Finished `date`."

exit
