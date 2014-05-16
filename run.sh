#!/bin/sh

echo "mvn clean: cleaning project..."
mvn clean
echo "mvn package: building project..."
mvn package
echo
echo "****************************************************************************"
echo "running sentanal server"
echo "****************************************************************************"
echo
java -Xms512m -Xmx2600m -XX:PermSize=512m -jar target/sentanal-1.0-SNAPSHOT.jar server sentanal.yml

