#!/bin/ksh

# +----------------------------------------------------------------------------+
# |                          Jeffrey M. Hunter                                 |
# |                      jhunter@idevelopment.info                             |
# |                         www.idevelopment.info                              |
# |----------------------------------------------------------------------------|
# |      Copyright (c) 1998-2011 Jeffrey M. Hunter. All rights reserved.       |
# |----------------------------------------------------------------------------|
# | DATABASE   : N/A                                                           |
# | FILE       : vmware_vm_tape_backup.ksh                                     |
# | CLASS      : UNIX Shell Scripts                                            |
# | PURPOSE    : This script is used to backup one or more VMware virtual      |
# |              machines from local disk to tape and is customized for my     |
# |              development environment at iDevelopment.info. Given that a    |
# |              VMware virtual machine is nothing more than a directory of    |
# |              O/S files that reside on the machine hosting the virtual      |
# |              machine, this script could be modified to backup any type     |
# |              of file(s) from local disk to tape. This script should be run |
# |              the from the node hosting the VMware virtual machines and is  |
# |              typically run as root.                                        |
# |                                                                            |
# | PARAMETERS :                                                               |
# |              TAPE_DEVICE_NAME     Device name for the locally attached     |
# |                                   tape drive. On the Linux platform, the   |
# |                                   first locally attached SCSI tape drive   |
# |                                   is commonly named /dev/st0 while the     |
# |                                   second tape drive (if available) is      |
# |                                   named /dev/st1, and so on. On the        |
# |                                   Solaris operating environment, the first |
# |                                   locally attached SCSI tape drive is      |
# |                                   commonly named /dev/rmt/0 while the      |
# |                                   the second tape drive (if available) is  |
# |                                   named /dev/rmt/1, and so on.             |
# |              VMWARE_VM_BASE_DIR   Provide the directory name where the     |
# |                                   VMware virtual machines to be backed up  |
# |                                   are located. Normally this would be the  |
# |                                   directory defined as the "standard"      |
# |                                   VMware Datastore.                        |
# |              BACKUP_PROFILE       A string value that indicates which      |
# |                                   backup profile to run. This script       |
# |                                   comes with three pre-written backup      |
# |                                   profiles that can be accessed through    |
# |                                   this string value which are "linux",     |
# |                                   "solaris", and "windows". This script    |
# |                                   provides an extensible framework by      |
# |                                   allowing the user to add and/or modify   |
# |                                   backup profiles. As already mentioned,   |
# |                                   this script comes with three pre-written |
# |                                   backup profiles which are are used to    |
# |                                   indicate which virtual machine platform  |
# |                                   to backup. Valid values for this         |
# |                                   parameter are "linux", "solaris", and    |
# |                                   "windows" but can be extended or         |
# |                                   modified by the user. Within each backup |
# |                                   profile, I list which virtual machines   |
# |                                   will be backed up based on their         |
# |                                   platform.                                |
# |                                                                            |
# | NOTES      : This script assumes that all tape files (manifest file and    |
# |              virtual machines) will be written at the beginning of the     |
# |              tape. An explicit rewind is performed before backing up any   |
# |              data.                                                         |
# |                                                                            |
# |              -----------------------------                                 |
# |              PERMISSIONS AND SECURITY                                      |
# |              -----------------------------                                 |
# |              This script is most often run by the root user on the machine |
# |              hosting the VMware virtual machines. To allow other UNIX      |
# |              users to run this script (i.e. oracle), verify that they are  |
# |              part of the UNIX group "disk" and that all necessary commands |
# |              like tar and mt are executable by this user. To add the       |
# |              UNIX user oracle to the "disk" group, edit the /etc/group     |
# |              file and append his name to the disk group entry as follows:  |
# |                                                                            |
# |                  disk:x:6:root,oracle                                      |
# |                                                                            |
# |              -----------------------------                                 |
# |              MANIFEST FILE                                                 |
# |              -----------------------------                                 |
# |              Each of the backup profiles defined in this script, a         |
# |              complete manifest file will be created and written as the     |
# |              first file to the tape. The file will be named manifest.txt.  |
# |                                                                            |
# |                                                                            |
# | CRON USAGE : This script can be run interactively from a command line      |
# |              interface or scheduled within CRON. Regardless of the method  |
# |              used to run this script, a log file will automatically be     |
# |              created of the form "<script_name>_<varn>.log" where <varn>   |
# |              can be any user defined variable used to identify the         |
# |              instance of the run. When scheduling this script to be run    |
# |              from CRON, ensure the crontab entry does NOT redirect its     |
# |              output to the name of the log file automatically created from |
# |              within this script. When defining the crontab entry used to   |
# |              run this script, the typical convention is to redirect its    |
# |              output to a log file with an extension of .job as illustrated |
# |              in the following example:                                     |
# |                                                                            |
# |              [time] [script_name.ksh] > [script_name.job] 2>&1             |
# |                                                                            |
# | EXAMPLE    :                                                               |
# |                                                                            |
# |     vmware_vm_tape_backup.ksh  /dev/st0 /local/vmware/Virtual_Machines_Backup solaris
# |                                                                            |
# | WARNING!   : As with any code, ensure to test this script in a development |
# |              environment before attempting to run it in production.        |
# +----------------------------------------------------------------------------+


# ----------------------------
# SCRIPT PARAMETER VARIABLES
# ----------------------------
TAPE_DEVICE_NAME=$1
VMWARE_VM_BASE_DIR=$2
BACKUP_PROFILE=$3

EXPECTED_NUM_SCRIPT_PARAMS=3

# ----------------------------
# SCRIPT NAME VARIABLES
# ----------------------------
VERSION="1.0"
SCRIPT_NAME_FULL=$0
SCRIPT_NAME=${SCRIPT_NAME_FULL##*/}
SCRIPT_NAME_NOEXT=${SCRIPT_NAME%.?*}

# ----------------------------
# SCRIPT CUSTOM VARIABLES
# ----------------------------
TAPE_DEVICE_NAME_NOREWIND="${TAPE_DEVICE_NAME%/*}/n${TAPE_DEVICE_NAME##*/}"

RUID=`/usr/bin/id|awk -F\( '{print $2}'|awk -F\) '{print $1}'`
if [ ${RUID} = "root" ];then
    ROOT_LOGIN=TRUE
else
    ROOT_LOGIN=FALSE
fi

# ----------------------------
# DATE VARIABLES
# ----------------------------
START_DATE=`date`
START_DATE_LOG=`date +"%Y%m%d_%H%M%S"`
START_DATE_PRINT=`date +"%m/%d/%Y %r %Z"`
CURRENT_YEAR=`date +"%Y"`;
CURRENT_DOW_NUM=`date +"%w"`;      # - day of week (0..6); 0 is Sunday
case ${CURRENT_DOW_NUM} in
    0) CURRENT_DOW_NAME="Sunday" ;;
    1) CURRENT_DOW_NAME="Monday" ;;
    2) CURRENT_DOW_NAME="Tuesday" ;;
    3) CURRENT_DOW_NAME="Wednesday" ;;
    4) CURRENT_DOW_NAME="Thursday" ;;
    5) CURRENT_DOW_NAME="Friday" ;;
    6) CURRENT_DOW_NAME="Saturday" ;;
    *) CURRENT_DOW_NAME="unknown" ;;
esac

# ----------------------------
# SHELL PROPERTIES
# ----------------------------
SPROP_SHELL_FLAGS=$-
SPROP_PROCESS_ID=$$
SPROP_NUM_SCRIPT_PARAMS=$#
if tty -s; then
    SPROP_SHELL_ACCESS="INTERACTIVE"
else
    SPROP_SHELL_ACCESS="NON-INTERACTIVE"
fi

# ----------------------------
# MISCELLANEOUS VARIABLES
# ----------------------------
HOST_RVAL_SUCCESS=0
HOST_RVAL_ERROR=2

# ----------------------------
# HOSTNAME VARIABLES
# ----------------------------
HOSTNAME=`hostname`
HOSTNAME_UPPER=`echo $HOSTNAME | tr '[:lower:]' '[:upper:]'`
HOSTNAME_SHORT=${HOSTNAME%%.*}
HOSTNAME_SHORT_UPPER=`echo $HOSTNAME_SHORT | tr '[:lower:]' '[:upper:]'`

# ----------------------------
# CUSTOM DIRECTORIES
# ----------------------------
#       mkdir -p /opt/dba_scripts/bin
#       mkdir -p /opt/dba_scripts/lib
#       mkdir -p /opt/dba_scripts/log
#       mkdir -p /opt/dba_scripts/out
#       mkdir -p /opt/dba_scripts/sql
#       mkdir -p /opt/dba_scripts/temp
#       chown -R root.disk /opt/dba_scripts
#       chmod -R g+w /opt/dba_scripts
# ----------------------------
CUSTOM_SCRIPTS_DIR=/opt/dba_scripts
CUSTOM_SCRIPTS_BIN_DIR=${CUSTOM_SCRIPTS_DIR}/bin
CUSTOM_SCRIPTS_LIB_DIR=${CUSTOM_SCRIPTS_DIR}/lib
CUSTOM_SCRIPTS_LOG_DIR=${CUSTOM_SCRIPTS_DIR}/log
CUSTOM_SCRIPTS_OUT_DIR=${CUSTOM_SCRIPTS_DIR}/out
CUSTOM_SCRIPTS_SQL_DIR=${CUSTOM_SCRIPTS_DIR}/sql
CUSTOM_SCRIPTS_TEMP_DIR=${CUSTOM_SCRIPTS_DIR}/temp

# ----------------------------
# LOG AND TEMP FILE VARIABLES
# ----------------------------
LOG_FILE_NAME=${CUSTOM_SCRIPTS_LOG_DIR}/${SCRIPT_NAME_NOEXT}_${BACKUP_PROFILE}_${START_DATE_LOG}.log
LOG_FILE_NAME_NODATE=${CUSTOM_SCRIPTS_LOG_DIR}/${SCRIPT_NAME_NOEXT}_${BACKUP_PROFILE}.log
LOG_FILE_ARCHIVE_OBSOLETE_DAYS=45
SQL_OUTPUT_TEMP_FILE_NAME=${CUSTOM_SCRIPTS_TEMP_DIR}/${SCRIPT_NAME_NOEXT}_${BACKUP_PROFILE}.lst

# ----------------------------
# MANIFEST FILE
# ----------------------------
MANIFEST_FILE_NAME="manifest.txt"
MANIFEST_FILE_DIR=${CUSTOM_SCRIPTS_TEMP_DIR}
MANIFEST_FILE_NAME_FULL="${MANIFEST_FILE_DIR}/${MANIFEST_FILE_NAME}"

# ----------------------------
# LIST ALL ADMINISTRATIVE 
# EMAIL ADDRESSES WHO WILL BE 
# RESPONSIBLE FOR MONITORING 
# AND RECEIVING EMAIL FROM 
# THIS SCRIPT. MULTIPLE EMAIL
# ADDRESSES SHOULD ALL BE
# LISTED IN DOUBLE-QUOTES
# SEPARATED BY A SINGLE SPACE.
# ----------------------------
MAIL_USERS_EMAIL="jhunter@idevelopment.info info@idevelopment.info dba@idevelopment.info"

# ----------------------------
# EMAIL PREFERENCES
# ----------------------------
MAIL_FROM="iDevelopment.info Database Support <dba@idevelopment.info>"
MAIL_REPLYTO="iDevelopment.info Database Support <dba@idevelopment.info>"
MAIL_TEMP_FILE_NAME=${CUSTOM_SCRIPTS_TEMP_DIR}/${SCRIPT_NAME_NOEXT}_${BACKUP_PROFILE}.mhr


# +----------------------------------------------------------------------------+
# |                                                                            |
# |                       DEFINE ALL GLOBAL FUNCTIONS                          |
# |                                                                            |
# +----------------------------------------------------------------------------+

function wl {

    typeset -r L_STRING=${1}
    
    echo "${L_STRING}" >> ${LOG_FILE_NAME}
    echo "${L_STRING}"

    return

}

function startLogging {

    wl "+=========================================================================+"
    wl "|                                                                         |"
    wl "|                               START TIME                                |"
    wl "|                                                                         |"
    wl "|                      $START_DATE                       |"
    wl "|                                                                         |"
    wl "+=========================================================================+"

    return

}

function stopLogging {

    END_DATE=`date`
    wl " "
    wl "+=========================================================================+"
    wl "|                                                                         |"
    wl "|                               FINISH TIME                               |"
    wl "|                                                                         |"
    wl "|                       $END_DATE                      |"
    wl "|                                                                         |"
    wl "+=========================================================================+"

    return

}

function showSignonBanner {

    typeset -r L_VERSION=${1}
    typeset -r L_CURRENT_YEAR=${2}
    typeset -r L_WRITE_TO_LOG=${3}
    typeset    L_SHOW
    
    if [[ $L_WRITE_TO_LOG = "NOLOG" || -z $L_WRITE_TO_LOG ]]; then
        L_SHOW="echo"
    else
        L_SHOW="wl"
    fi

    $L_SHOW " "
    $L_SHOW "${SCRIPT_NAME} - Version ${L_VERSION}"
    $L_SHOW "Copyright (c) 1998-${L_CURRENT_YEAR} Jeffrey M. Hunter. All rights reserved."
    $L_SHOW " "
    
    return

}

function showUsage {

    typeset -r L_WRITE_TO_LOG=${1}
    typeset    L_SHOW

    if [[ $L_WRITE_TO_LOG = "NOLOG" || -z $L_WRITE_TO_LOG ]]; then
        L_SHOW="echo"
    else
        L_SHOW="wl"
    fi
    
    $L_SHOW " "
    $L_SHOW "Usage: ${SCRIPT_NAME} TAPE_DEVICE_NAME  VMWARE_VM_BASE_DIR  BACKUP_PROFILE"
    $L_SHOW " "

    return

}

function initializeScript {

    typeset -ru L_FIRST_PARAMETER=${1}
    typeset -r  L_VERSION=${2}
    typeset -r  L_CURRENT_YEAR=${3}

    if [[ $L_FIRST_PARAMETER = "-H" || $L_FIRST_PARAMETER = "-HELP" || $L_FIRST_PARAMETER = "--HELP" || -z $L_FIRST_PARAMETER ]]; then
        showSignonBanner $L_VERSION $L_CURRENT_YEAR "NOLOG"
        showUsage "NOLOG"
        exit $HOST_RVAL_SUCCESS
    fi

    rm -f ${LOG_FILE_NAME}
    ERRORS="NO"

    return

}

function getOSName {

    echo `uname -s`

    return

}

function getOSType {

    typeset -r L_OS_NAME=${1}
    typeset    L_OS_TYPE_RVAL

    case ${L_OS_NAME} in
        *BSD)
            L_OS_TYPE_RVAL="bsd" ;;
        SunOS)
            case `uname -r` in
                5.*) L_OS_TYPE_RVAL="solaris" ;;
                  *) L_OS_TYPE_RVAL="sunos" ;;
            esac
            ;;
        Linux)
            L_OS_TYPE_RVAL="linux" ;;
        HP-UX)
            L_OS_TYPE_RVAL="hpux" ;;
        AIX)
            L_OS_TYPE_RVAL="aix" ;;
        *) L_OS_TYPE_RVAL="unknown" ;;
    esac
    
    echo ${L_OS_TYPE_RVAL}
    
    return
    
}

function backupCurrentLogFile {
    
    cp -vf ${LOG_FILE_NAME} ${LOG_FILE_NAME_NODATE}
    
    wl " "
    wl "TRACE> Copied ${LOG_FILE_NAME} to ${LOG_FILE_NAME_NODATE}"

    return

}

function sendEmail {

    # -------------------------------
    # POSSIBLE L_SEVERITY VALUES ARE:
    #     SUCCESSFUL
    #     WARNING
    #     FAILED
    # -------------------------------
    typeset -r L_SEVERITY=${1}
    typeset -r L_SID_NAME=${2}
    typeset    L_IMPORTANCE
    typeset    L_X_PRIORITY
    typeset    L_X_MSMAIL_PRIORITY
    typeset    L_EMAIL_ADDRESS

    case ${L_SEVERITY} in
        SUCCESSFUL)
            L_IMPORTANCE="Normal"
            L_X_PRIORITY="3"
            L_X_MSMAIL_PRIORITY="Normal"
            ;;
        WARNING)
            L_IMPORTANCE="High"
            L_X_PRIORITY="1"
            L_X_MSMAIL_PRIORITY="High"
            ;;
        FAILED)
            L_IMPORTANCE="High"
            L_X_PRIORITY="1"
            L_X_MSMAIL_PRIORITY="High"
            ;;
        *)
            L_IMPORTANCE="High"
            L_X_PRIORITY="1"
            L_X_MSMAIL_PRIORITY="High"
        ;;
    esac

    for L_EMAIL_ADDRESS in $MAIL_USERS_EMAIL; do
        {
            echo "Importance: ${L_IMPORTANCE}"
            echo "X-Priority: ${L_X_PRIORITY}"
            echo "X-MSMail-Priority: ${L_X_MSMAIL_PRIORITY}"
            echo "Subject: [${HOSTNAME_SHORT_UPPER}] - ${L_SEVERITY}: ${SCRIPT_NAME} (${L_SID_NAME})"
            echo "To: ${L_EMAIL_ADDRESS}"
            echo "From: ${MAIL_FROM}"
            echo "Reply-To: ${MAIL_REPLYTO}"
            echo ""
            cat ${LOG_FILE_NAME}
        } > ${MAIL_TEMP_FILE_NAME}
        
        /usr/lib/sendmail -v $L_EMAIL_ADDRESS < ${MAIL_TEMP_FILE_NAME} | tee -a $LOG_FILE_NAME
        
        rm -f $MAIL_TEMP_FILE_NAME | tee -a $LOG_FILE_NAME
    done

    return

}

function exitError {

    typeset L_SEVERITY=${1}
    typeset L_ENTRY_NAME=${2}


    wl " "
    wl "TRACE> +----------------------------------------------+"
    wl "TRACE> |    !!!!!!!!    CRITICAL ERROR    !!!!!!!!    |"
    wl "TRACE> +----------------------------------------------+"
    wl " "
    wl "TRACE> Exiting script (${HOST_RVAL_ERROR})."
    wl " "

    showUsage "LOG"
    stopLogging
    sendEmail ${L_SEVERITY} ${L_ENTRY_NAME}

    backupCurrentLogFile

    exit ${HOST_RVAL_ERROR}

}

function exitSuccess {

    typeset L_SEVERITY=${1}
    typeset L_ENTRY_NAME=${2}

    wl " "
    wl "TRACE> +----------------------------------------------+"
    wl "TRACE> |                  SUCCESSFUL                  |"
    wl "TRACE> +----------------------------------------------+"
    wl " "
    wl "TRACE> Exiting script (${HOST_RVAL_SUCCESS})."
    wl " "

    stopLogging
    
    sendEmail ${L_SEVERITY} ${L_ENTRY_NAME}

    backupCurrentLogFile

    exit ${HOST_RVAL_SUCCESS}

}

function checkScriptAlreadyRunning {

    typeset -r L_SPROP_SHELL_ACCESS=${1}
    typeset -r L_SCRIPT_NAME=${2}
    typeset -r L_BACKUP_PROFILE=${3}
    typeset -i L_WC
    typeset -i L_NUM_CHECK_INSTANCES
    typeset    L_COMMAND
    
    wl " "
    wl "TRACE> Check that this script (${L_SCRIPT_NAME}) is not already running on this host."

    L_COMMAND="L_WC=\$(ps -ef | grep ${L_SCRIPT_NAME} | grep -v 'grep' | wc -l)"
    wl " "
    wl "TRACE> ${L_COMMAND}"
    
    wl " "
    ps -ef | grep ${L_SCRIPT_NAME} | grep -v 'grep' | tee -a $LOG_FILE_NAME
    L_WC=$(ps -ef | grep ${L_SCRIPT_NAME} | grep -v 'grep' | wc -l)

    wl " "
    wl "TRACE> Number of instances of this script running: $L_WC"

    wl " "
    wl "TRACE> Check to see if this script is an interactive session."

    if [[ $SPROP_SHELL_ACCESS = "INTERACTIVE" ]]; then
        wl " "
        wl "TRACE> This is an INTERACTIVE session. Check number of instances of this script against > 1."
        L_NUM_CHECK_INSTANCES=1
    else
        wl " "
        wl "TRACE> This is a NON-INTERACTIVE session (i.e. CRON). Check number of instances of this script against > 2."
        L_NUM_CHECK_INSTANCES=2
    fi

    #
    # CHECK FOR INSTANCES OF THIS SCRIPT RUNNING OUT OF CRON (NON-INTERACTIVE) OR INTERACTIVE
    #
    if (( $L_WC > $L_NUM_CHECK_INSTANCES )); then
        wl " "
        wl "TRACE> WARNING: Found ${L_SCRIPT_NAME} already running on this host. Exiting script."
        exitError "WARNING" "${L_BACKUP_PROFILE}"
    else
        wl " "
        wl "TRACE> Did not find this script (${L_SCRIPT_NAME}) already running on this host. Continuing script..."
    fi
    wl " "

    return

}

function verifyVMwareBaseDirectory {

    typeset -r L_VMWARE_VM_BASE_DIR=${1}
    typeset -r L_BACKUP_PROFILE=${2}

    wl " "
    wl "TRACE> Check that the VMware VM base directory (${L_VMWARE_VM_BASE_DIR}) exists."
    wl " "

    if [[ -d $L_VMWARE_VM_BASE_DIR ]]; then
        wl " "
        wl "TRACE> The directory ($L_VMWARE_VM_BASE_DIR) exists."    
    else
        wl " "
        wl "TRACE> The directory ($L_VMWARE_VM_BASE_DIR) does not exist."
        wl "TRACE> Restart this script with a valid VMware directory."
        exitError "FAILED" "${L_BACKUP_PROFILE}"
    fi

    return

}

function verifyTapeDeviceName {

    typeset -r L_TAPE_DEVICE_NAME=${1}
    typeset -r L_TAPE_DEVICE_NAME_NOREWIND=${2}
    typeset -r L_BACKUP_PROFILE=${3}
    typeset    L_COMMAND
    typeset -i L_TAPE_FILE_NUMBER
    
    wl " "
    wl "TRACE> Verify that the tape drive (${L_TAPE_DEVICE_NAME}) is available."
    wl " "
    wl "TRACE> NOTE: At this time, the only check I perform is to query the File"
    wl "TRACE>       Number obtained through \"mt -f TAPE_DEVICE status\". If the"
    wl "TRACE>       File Number is >= 0, the tape drive is considered available."
    wl "TRACE>       Please note that I use the \"no rewind\" tape device option"
    wl "TRACE>       to perform the status check. In future releases of this script,"
    wl "TRACE>       I intend to enhance this section by providing a number of other"
    wl "TRACE>       functions to verify the availability of the tape and tape drive."

    L_COMMAND="mt -f ${L_TAPE_DEVICE_NAME_NOREWIND} status | grep '^File number' | cut -d\",\" -f1 | cut -d\"=\" -f2"
    wl " "
    wl "TRACE> ${L_COMMAND}"
    L_TAPE_FILE_NUMBER=`mt -f ${L_TAPE_DEVICE_NAME_NOREWIND} status | grep '^File number' | cut -d"," -f1 | cut -d"=" -f2`

    wl " "
    wl "TRACE> File Number: $L_TAPE_FILE_NUMBER"

    if (( $L_TAPE_FILE_NUMBER >= 0 )); then
        wl " "
        wl "TRACE> The tape (${L_TAPE_DEVICE_NAME}) is available."
    else
        wl " "
        wl "TRACE> The tape (${L_TAPE_DEVICE_NAME}) is not available."
        wl "TRACE> Restart this script after verifying a tape is in the tape drive (${L_TAPE_DEVICE_NAME}) and available for read/write."
        exitError "FAILED" "${L_BACKUP_PROFILE}"
    fi

    return

}

function rewindTape {

    typeset -r L_TAPE_DEVICE_NAME=${1}
    typeset -r L_BACKUP_PROFILE=${2}

    wl " "
    wl "TRACE> Rewinding tape ${L_TAPE_DEVICE_NAME}"

    L_COMMAND="mt -f ${L_TAPE_DEVICE_NAME} rewind"
    wl " "
    wl "TRACE> ${L_COMMAND}"
    mt -f ${L_TAPE_DEVICE_NAME} rewind

    if (( $? == 0 )); then
        wl " "
        wl "TRACE> Successful rewind of tape (${L_TAPE_DEVICE_NAME})."
    else
        wl " "
        wl "TRACE> Unsuccessful rewind of tape (${L_TAPE_DEVICE_NAME})."
        wl "TRACE> Restart this script after verifying a tape is in the tape drive (${L_TAPE_DEVICE_NAME}) and available for read/write."
        exitError "FAILED" "${L_BACKUP_PROFILE}"
    fi

    return

}

function performVMBackup {

    typeset -r L_TAPE_DEVICE_NAME=${1}
    typeset -r L_TAPE_DEVICE_NAME_NOREWIND=${2}
    typeset -r L_VMWARE_VM_BASE_DIR=${3}
    typeset -r L_MANIFEST_FILE_NAME=${4}
    typeset -r L_MANIFEST_FILE_DIR=${5}
    typeset -r L_MANIFEST_FILE_NAME_FULL=${6}
    typeset -r L_BACKUP_PROFILE=${7}
    typeset    L_VM_MACHINES
    typeset    L_VM_MACHINE
    typeset -i L_COUNTER
    typeset    L_DATE_PRINT_LOG


    wl " "
    wl "TRACE> Running VMware backup profile for ${L_BACKUP_PROFILE} to copy from ${L_VMWARE_VM_BASE_DIR} to ${L_TAPE_DEVICE_NAME}"


    case ${L_BACKUP_PROFILE} in
        linux)
            L_VM_MACHINES="vmlinux1 vmlinux2 vmlinux3"
            ;;
        solaris)
            L_VM_MACHINES="vmsolaris1 vmsolaris2"
            ;;
        windows)
            L_VM_MACHINES="vmwindows1 vmwindows2 vmwindows3"
            ;;
        *)  wl " "
            wl "TRACE> JMA-0001: Invalid VMware Backup Profile - ${BACKUP_PROFILE}"
            wl "TRACE> JMA-0000: Restart this script with a valid Backup Profile value."
            return
            ;;
    esac


    # ---------------------------------------------
    # CREATE MANIFEST FILE
    # ---------------------------------------------
    wl " "
    wl "TRACE> Creating manifest file"
    L_COUNTER=0
    {
        echo "------------------------------"
        echo "VMware Manifest File"
        echo "------------------------------"
    } > ${L_MANIFEST_FILE_NAME_FULL}
    echo "File Number [$L_COUNTER] : $L_MANIFEST_FILE_NAME" >> ${L_MANIFEST_FILE_NAME_FULL}
    L_COUNTER=`expr $L_COUNTER + 1`
    for L_VM_MACHINE in $L_VM_MACHINES; do
        echo "File Number [$L_COUNTER] : $L_VM_MACHINE" >> ${L_MANIFEST_FILE_NAME_FULL}
        L_COUNTER=`expr $L_COUNTER + 1`
    done

    wl "TRACE> Successfully created manifest file (${L_MANIFEST_FILE_NAME_FULL})."
        

    # ---------------------------------------------
    # PRINT MANIFEST FILE TO LOG
    # ---------------------------------------------
    wl " "
    wl "TRACE> Print manifest file to log"
    wl " "
    cat ${L_MANIFEST_FILE_NAME_FULL} >> ${LOG_FILE_NAME}
    
    
    # ---------------------------------------------
    # BACKUP MANIFEST FILE
    # ---------------------------------------------
    wl " "
    wl "TRACE> Backup $L_MANIFEST_FILE_NAME file"
    (cd $L_MANIFEST_FILE_DIR; tar cvf $L_TAPE_DEVICE_NAME_NOREWIND $L_MANIFEST_FILE_NAME)

    if (( $? == 0 )); then
        wl "TRACE> Successfully backed up manifest file (${L_MANIFEST_FILE_NAME}) to (${L_TAPE_DEVICE_NAME_NOREWIND})."
    else
        wl " "
        wl "TRACE> Failed to backup manifest file (${L_MANIFEST_FILE_NAME}) to (${L_TAPE_DEVICE_NAME_NOREWIND})."
        wl "TRACE> Exiting script."
        exitError "FAILED" "${L_BACKUP_PROFILE}"
    fi

    # ---------------------------------------------
    # BACKUP VIRTUAL MACHINES
    # ---------------------------------------------
    wl " "
    wl "TRACE> Backup virtual machines"

    cd ${L_VMWARE_VM_BASE_DIR}
    
    for L_VM_MACHINE in $L_VM_MACHINES; do
        L_DATE_PRINT_LOG=`date "+%m/%d/%Y %H:%M:%S"`
        wl " "
        wl "TRACE> Starting backup of virtual machine (${L_VM_MACHINE}) at (${L_DATE_PRINT_LOG})"
        tar cvf $L_TAPE_DEVICE_NAME_NOREWIND $L_VM_MACHINE
        if (( $? == 0 )); then
            L_DATE_PRINT_LOG=`date "+%m/%d/%Y %H:%M:%S"`
            wl "TRACE> Finished backup of virtual machine (${L_VM_MACHINE}) at (${L_DATE_PRINT_LOG})"
        else
            L_DATE_PRINT_LOG=`date "+%m/%d/%Y %H:%M:%S"`
            wl "TRACE> Failed to backup of virtual machine (${L_VM_MACHINE}) at (${L_DATE_PRINT_LOG})"
            wl "TRACE> Exiting script."
            exitError "FAILED" "${L_BACKUP_PROFILE}"
        fi
    done

    return

}



# +----------------------------------------------------------------------------+
# |                                                                            |
# |                            SCRIPT STARTS HERE                              |
# |                                                                            |
# +----------------------------------------------------------------------------+

initializeScript "${1}" $VERSION $CURRENT_YEAR

startLogging

showSignonBanner $VERSION $CURRENT_YEAR "LOG"

DATE_PRINT_LOG=`date "+%m/%d/%Y %H:%M:%S"`
wl " "
wl "+-------------------------------------------------------------------------+"
wl "| ${DATE_PRINT_LOG}                                                     |"
wl "|-------------------------------------------------------------------------|"
wl "| VALIDATE COMMAND-LINE ARGUMENTS.                                        |"
wl "+-------------------------------------------------------------------------+"

# ------------------------------

wl " "
wl "TRACE> Number of script parameters passed to this script = $SPROP_NUM_SCRIPT_PARAMS"
wl "TRACE> Number of expected script parameters to this script = $EXPECTED_NUM_SCRIPT_PARAMS"
wl " "

if (( $SPROP_NUM_SCRIPT_PARAMS != $EXPECTED_NUM_SCRIPT_PARAMS )); then
    wl "TRACE> Invalid number of parameters."
    exitError "FAILED" "INVALID_PARAMS"
fi

# ------------------------------

if [[ -n $TAPE_DEVICE_NAME ]]; then
    wl "TRACE> TAPE_DEVICE_NAME set to $TAPE_DEVICE_NAME"
else
    wl " "
    wl "TAPE_DEVICE_NAME undefined."
    exitError "FAILED" "INVALID_PARAMS"
fi

if [[ -n $VMWARE_VM_BASE_DIR ]]; then
    wl "TRACE> VMWARE_VM_BASE_DIR set to $VMWARE_VM_BASE_DIR"
else
    wl " "
    wl "VMWARE_VM_BASE_DIR undefined."
    exitError "FAILED" "INVALID_PARAMS"
fi

if [[ -n $BACKUP_PROFILE ]]; then
    wl "TRACE> BACKUP_PROFILE set to $BACKUP_PROFILE"
else
    wl " "
    wl "BACKUP_PROFILE undefined."
    exitError "FAILED" "INVALID_PARAMS"
fi


DATE_PRINT_LOG=`date "+%m/%d/%Y %H:%M:%S"`
wl " "
wl "+-------------------------------------------------------------------------+"
wl "| ${DATE_PRINT_LOG}                                                     |"
wl "|-------------------------------------------------------------------------|"
wl "| DISPLAY ALL SCRIPT ENVIRONMENT VARIABLES.                               |"
wl "+-------------------------------------------------------------------------+"

wl " "
wl "==========================================================="
wl "                 GLOBAL SCRIPT VARIABLES                   "
wl "==========================================================="
wl "SCRIPT                          : $SCRIPT_NAME"
wl "VERSION                         : $VERSION"
wl "START DATE/TIME                 : $START_DATE"
wl "CURRENT_DOW_NUM                 : $CURRENT_DOW_NUM"
wl "CURRENT_DOW_NAME                : $CURRENT_DOW_NAME"
wl "SHELL ACCESS                    : $SPROP_SHELL_ACCESS"
wl "SHELL FLAGS                     : $SPROP_SHELL_FLAGS"
wl "PROCESS ID                      : $SPROP_PROCESS_ID"
wl "# OF SCRIPT PARAMETERS          : $SPROP_NUM_SCRIPT_PARAMS"
wl "# OF EXPECTED SCRIPT PARAMETERS : $EXPECTED_NUM_SCRIPT_PARAMS"
wl "HOST NAME                       : $HOSTNAME"
wl "HOST NAME (UPPER)               : $HOSTNAME_UPPER"
wl "HOST NAME (SHORT)               : $HOSTNAME_SHORT"
wl "HOST NAME (SHORT/UPPER)         : $HOSTNAME_SHORT_UPPER"
wl "LOG FILE NAME                   : $LOG_FILE_NAME"
wl "LOG FILE NAME NODATE            : $LOG_FILE_NAME_NODATE"
wl "LOG FILE ARCHIVE OBSOLETE DAYS  : $LOG_FILE_ARCHIVE_OBSOLETE_DAYS"
wl "EMAIL RECIPIENTS                : ... <LIST> ..."
for email_address in $MAIL_USERS_EMAIL; do
    wl "                                  $email_address"
done
wl " "
wl "==========================================================="
wl "                  COMMAND-LINE ARGUMENTS                   "
wl "==========================================================="
wl "TAPE_DEVICE_NAME     (P1)       : $TAPE_DEVICE_NAME"
wl "VMWARE_VM_BASE_DIR   (P2)       : $VMWARE_VM_BASE_DIR"
wl "BACKUP_PROFILE       (P3)       : $BACKUP_PROFILE"
wl " "
wl "==========================================================="
wl "                  CUSTOM SCRIPT VARIABLES                  "
wl "==========================================================="
wl "ROOT_LOGIN?                     : $ROOT_LOGIN"
wl "TAPE_DEVICE_NAME_NOREWIND       : $TAPE_DEVICE_NAME_NOREWIND"
wl "MANIFEST_FILE_NAME              : $MANIFEST_FILE_NAME"
wl "MANIFEST_FILE_DIR               : $MANIFEST_FILE_DIR"
wl "MANIFEST_FILE_NAME_FULL         : $MANIFEST_FILE_NAME_FULL"


DATE_PRINT_LOG=`date "+%m/%d/%Y %H:%M:%S"`
wl " "
wl "+-------------------------------------------------------------------------+"
wl "| ${DATE_PRINT_LOG}                                                     |"
wl "|-------------------------------------------------------------------------|"
wl "| VERIFY AN INSTANCE OF THIS SCRIPT IS NOT ALREADY RUNNING.               |"
wl "+-------------------------------------------------------------------------+"

checkScriptAlreadyRunning $SPROP_SHELL_ACCESS $SCRIPT_NAME $BACKUP_PROFILE


DATE_PRINT_LOG=`date "+%m/%d/%Y %H:%M:%S"`
wl " "
wl "+-------------------------------------------------------------------------+"
wl "| ${DATE_PRINT_LOG}                                                     |"
wl "|-------------------------------------------------------------------------|"
wl "| GET O/S NAME, O/S TYPE, AND ORATAB FILE.                                |"
wl "+-------------------------------------------------------------------------+"

OS_NAME=`getOSName`
OS_TYPE=`getOSType $OS_NAME`

wl " "
wl "TRACE> O/S Name                : ${OS_NAME}"
wl "TRACE> O/S Type                : ${OS_TYPE}"


DATE_PRINT_LOG=`date "+%m/%d/%Y %H:%M:%S"`
wl " "
wl "+-------------------------------------------------------------------------+"
wl "| ${DATE_PRINT_LOG}                                                     |"
wl "|-------------------------------------------------------------------------|"
wl "| VERIFY THE VMware BASE DIRECTORY EXISTS.                                |"
wl "+-------------------------------------------------------------------------+"

verifyVMwareBaseDirectory $VMWARE_VM_BASE_DIR $BACKUP_PROFILE


DATE_PRINT_LOG=`date "+%m/%d/%Y %H:%M:%S"`
wl " "
wl "+-------------------------------------------------------------------------+"
wl "| ${DATE_PRINT_LOG}                                                     |"
wl "|-------------------------------------------------------------------------|"
wl "| VERIFY THE TAPE DEVICE NAME IS VALID.                                   |"
wl "+-------------------------------------------------------------------------+"

verifyTapeDeviceName $TAPE_DEVICE_NAME $TAPE_DEVICE_NAME_NOREWIND $BACKUP_PROFILE


DATE_PRINT_LOG=`date "+%m/%d/%Y %H:%M:%S"`
wl " "
wl "+-------------------------------------------------------------------------+"
wl "| ${DATE_PRINT_LOG}                                                     |"
wl "|-------------------------------------------------------------------------|"
wl "| REWIND THE TAPE.                                                        |"
wl "+-------------------------------------------------------------------------+"

rewindTape $TAPE_DEVICE_NAME $BACKUP_PROFILE


DATE_PRINT_LOG=`date "+%m/%d/%Y %H:%M:%S"`
wl " "
wl "+-------------------------------------------------------------------------+"
wl "| ${DATE_PRINT_LOG}                                                     |"
wl "|-------------------------------------------------------------------------|"
wl "| PERFORM VMware VIRTUAL MACHINE BACKUP PROFILE.                          |"
wl "+-------------------------------------------------------------------------+"

wl " "
wl "TRACE> VMware Backup Profile ${BACKUP_PROFILE}"

case ${BACKUP_PROFILE} in
    linux|solaris|windows)
            # ------------------------------------------
            # BACKUPSET / INCREMENTAL / PRIMARY DATABASE
            # ------------------------------------------
            performVMBackup $TAPE_DEVICE_NAME $TAPE_DEVICE_NAME_NOREWIND $VMWARE_VM_BASE_DIR $MANIFEST_FILE_NAME $MANIFEST_FILE_DIR $MANIFEST_FILE_NAME_FULL $BACKUP_PROFILE
            ;;
    *)      wl " "
            wl "TRACE> JMA-0001: Invalid VMware Backup Profile - ${BACKUP_PROFILE}"
            wl "TRACE> JMA-0000: Restart this script with a valid Backup Profile value."
            ;;
esac


DATE_PRINT_LOG=`date "+%m/%d/%Y %H:%M:%S"`
wl " "
wl "+-------------------------------------------------------------------------+"
wl "| ${DATE_PRINT_LOG}                                                     |"
wl "|-------------------------------------------------------------------------|"
wl "| REWIND THE TAPE.                                                        |"
wl "+-------------------------------------------------------------------------+"

rewindTape $TAPE_DEVICE_NAME $BACKUP_PROFILE


DATE_PRINT_LOG=`date "+%m/%d/%Y %H:%M:%S"`
wl " "
wl "+-------------------------------------------------------------------------+"
wl "| ${DATE_PRINT_LOG}                                                     |"
wl "|-------------------------------------------------------------------------|"
wl "| SCAN LOG FILE FOR ERRORS. IGNORE KNOWN EXCEPTIONS.                      |"
wl "+-------------------------------------------------------------------------+"

egrep 'JMA-' $LOG_FILE_NAME

if (( $? == 0 ))
then
    wl " "
    wl "+----------------------------------------------+"
    wl "| !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!|"
    wl "|                                              |"
    wl "|   --->        ERRORS WERE FOUND       <---   |"
    wl "|                                              |"
    wl "| !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!|"
    wl "+----------------------------------------------+"
    ERRORS="YES"
else
    wl " "
    wl "TRACE> No errors were found."
    ERRORS="NO"
fi


DATE_PRINT_LOG=`date "+%m/%d/%Y %H:%M:%S"`
wl " "
wl "+-------------------------------------------------------------------------+"
wl "| ${DATE_PRINT_LOG}                                                     |"
wl "|-------------------------------------------------------------------------|"
wl "| REMOVING OBSOLETE SCRIPT LOG FILES (greater than $LOG_FILE_ARCHIVE_OBSOLETE_DAYS days old)           |"
wl "|   (${CUSTOM_SCRIPTS_LOG_DIR}/${SCRIPT_NAME_NOEXT}_${BACKUP_PROFILE}_*.log)"
wl "+-------------------------------------------------------------------------+"

find ${CUSTOM_SCRIPTS_LOG_DIR}/ -name "${SCRIPT_NAME_NOEXT}_${BACKUP_PROFILE}_*.log" -mtime +${LOG_FILE_ARCHIVE_OBSOLETE_DAYS} -exec ls -l {} \; | tee -a $LOG_FILE_NAME
find ${CUSTOM_SCRIPTS_LOG_DIR}/ -name "${SCRIPT_NAME_NOEXT}_${BACKUP_PROFILE}_*.log" -mtime +${LOG_FILE_ARCHIVE_OBSOLETE_DAYS} -exec rm -rf {} \; | tee -a $LOG_FILE_NAME


DATE_PRINT_LOG=`date "+%m/%d/%Y %H:%M:%S"`
wl " "
wl "+-------------------------------------------------------------------------+"
wl "| ${DATE_PRINT_LOG}                                                     |"
wl "|-------------------------------------------------------------------------|"
wl "| ABOUT TO EXIT SCRIPT.                                                   |"
wl "+-------------------------------------------------------------------------+"
wl " "

if [[ $ERRORS = "YES" ]]; then
    exitError "FAILED" "${BACKUP_PROFILE}"
else
    exitSuccess "SUCCESSFUL" "${BACKUP_PROFILE}"
fi
