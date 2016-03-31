#!/bin/ksh

# --------------------------------------------------------
# FILE   : backup_tape_vmwindows.ksh
# AUTHOR : Jeffrey M. Hunter <jhunter@idevelopment.info>
# DATE   : 22-MAR-2010
# --------------------------------------------------------

PLATFORM="Windows"
MACHINES="vmwindows1 vmwindows2 vmwindows3"
BASE_BACKUP_DIR="/local/vmware/Virtual_Machines_Backup"
TAPE_DRIVE="/dev/st0"
TAPE_DRIVE_NOREWIND="/dev/nst0"
ADMIN_EMAIL="Jeffrey M. Hunter <jhunter@idevelopment.info>"

SCRIPT_NAME_FULL=$0
SCRIPT_NAME=${SCRIPT_NAME_FULL##*/}
SCRIPT_NAME_NOEXT=${SCRIPT_NAME%.?*}
LOG_FILE_NAME=${SCRIPT_NAME_NOEXT}.log
MAIL_TEMP_FILE_NAME=${SCRIPT_NAME_NOEXT}.mhr

function wl {

    typeset -r L_STRING=${1}
    
    echo "${L_STRING}" >> ${LOG_FILE_NAME}
    echo "${L_STRING}"

    return

}

rm -f ${LOG_FILE_NAME}
wl "-------------------------------------------------"
wl "SCRIPT: $SCRIPT_NAME"
wl "-------------------------------------------------"
wl " "

START_TIME=`date +"%x %X"`
wl "Starting $PLATFORM Tape Backup at $START_TIME"
wl " "

wl "Rewind $TAPE_DRIVE"
wl " "
mt -f $TAPE_DRIVE rewind

wl "Change to base directory: ${BASE_BACKUP_DIR}"
wl " "
cd ${BASE_BACKUP_DIR}

for machine_name in $MACHINES; do

    temp_date=`date +"%x %X"`
    wl "Working on $machine_name at $temp_date"
    wl " "

    wl "Status of $TAPE_DRIVE_NOREWIND"
    wl " "
    mt -f $TAPE_DRIVE_NOREWIND status

    tar cvf $TAPE_DRIVE_NOREWIND $machine_name

    temp_date=`date +"%x %X"`
    wl "Done working on $machine_name at $temp_date"
    wl " "

done

wl "-------------------------------------------------"
wl "RESTORE INSTRUCTIONS"
wl "-------------------------------------------------"
wl " "
wl "mt -f $TAPE_DRIVE rewind"
wl "cd ${BASE_BACKUP_DIR}"
for machine_name in $MACHINES; do
    wl "tar xvf $TAPE_DRIVE_NOREWIND"
done
wl " "

{
    echo "Importance: Normal"
    echo "X-Priority: 3"
    echo "X-MSMail-Priority: Normal"
    echo "Subject: [VMware Tape Backup] - $PLATFORM"
    echo "To: $ADMIN_EMAIL"
    echo "From: $ADMIN_EMAIL"
    echo "Reply-To: $ADMIN_EMAIL"
    echo ""
    cat ${LOG_FILE_NAME}
} > ${MAIL_TEMP_FILE_NAME}

/usr/lib/sendmail -v $ADMIN_EMAIL < ${MAIL_TEMP_FILE_NAME} | tee -a $LOG_FILE_NAME

rm -f $MAIL_TEMP_FILE_NAME | tee -a $LOG_FILE_NAME
wl " "

END_TIME=`date +"%x %X"`
wl "Ending $PLATFORM Tape Backup at $END_TIME"
