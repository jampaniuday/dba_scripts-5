:
#
# $Header: read.sh@@/main/2 \
# Checked in on Wed Dec 13 17:21:53 PST 1995 by vkordest \
# Copyright (c) 1995 by Oracle Corporation. All Rights Reserved. \
# $ Copyr (c) 1992 Oracle
#
# Read an input or use a default value with optional escape to sh.
#       usage:    DEFLT=<default> ; . read.sh ; SH_VAR=$RDVAR
#  Use a ! to escape to a sub-shell. When you exit from the
#  sub-shell you may still enter a new value or use the displayed
#  default.  If the variable ORACLE_DEFAULT is set to T, then don't
#  do a read, but just take the DEFLT value.
#

case $ORACLE_DEFAULT in
    T)	RDVAR=$DEFLT ; echo "$DEFLT" ;;
    *)	while :
	do
	    echo $N "[${DEFLT}]: $C"
	    read RDVAR
	    case $RDVAR in
		"")	RDVAR=$DEFLT ; break ;;
		!*)	${SHELL-/bin/sh} ; echo ;;
		*)	break ;;
	    esac
	done ;;
esac
