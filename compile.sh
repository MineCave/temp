#!bin/bash
cd /home/theo/Desktop/Fast_Java_Stuff/
#mvn clean package
mvn clean compile assembly:single
mv target/Dummy1-1-jar-with-dependencies.jar /home/theo/Desktop/Plugin/Dummy1.jar