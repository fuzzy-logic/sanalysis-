#!/bin/sh


mvn clean
mvn package
java -Xms512m -Xmx2600m -XX:PermSize=512m -jar target/sentanal-1.0-SNAPSHOT.jar server sentanal.yml

