#!/bin/sh


echo "mvn clean: cleaning project..."
mvn clean

echo "mvn package: building project..."
mvn package 
