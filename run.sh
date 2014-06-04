#!/bin/sh


echo "mvn clean: cleaning project..."
mvn clean > /spaas/mvn.log
cat  /spaas/mvn.log
echo "mvn package: building project..."
mvn package > /spaas/mvn.log
if [ "$?" != "0" ]
then 
  echo build run ok
  ls -al target/
else 
  tail -20 /spaas/mvn.log
  echo "build failed :-("
fi

#at  /spaas/mvn.log
echo
echo "****************************************************************************"
echo "running sentanal server"
echo "****************************************************************************"
echo
java -Xms2048m -Xmx14000m -XX:PermSize=1024m -jar target/sentanal-1.0-SNAPSHOT.jar server sentanal.yml

