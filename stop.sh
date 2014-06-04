#!/bin/sh

echo Stopping sentanal...
PID=`ps -ef | grep -i "target/sentanal" | grep -v grep | awk '{print $2}'`
echo Sentanal running PID=$PID
kill $PID

