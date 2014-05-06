#!/bin/sh

PID=`ps -ef | grep -i "target/sentanal" | grep -v grep | awk '{print $2}'`
echo Sentanal running PID=$IPD
kill $PID

