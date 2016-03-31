#!/bin/bash
ps -ef | egrep "sqlplus" | grep -v grep | while read a b c; do kill -9 $b;sleep 2;done