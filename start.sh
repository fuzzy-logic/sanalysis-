#!/bin/sh

nohup java -Xms2048m -Xmx14000m -XX:PermSize=1024m -jar target/sentanal-1.0-SNAPSHOT.jar server sentanal.yml &

tail -f nohup.out 

