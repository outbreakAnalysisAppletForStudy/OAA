#! /bin/bash

git pull
rm -f /home/damaoooo/apache-tomcat-7.0.104/webapps/Client.war
cp Client.war /home/damaoooo/apache-tomcat-7.0.104/webapps/
bash /home/damaoooo/apache-tomcat-7.0.104/bin/shutdown.sh
bash /home/damaoooo/apache-tomcat-7.0.104/bin/startup.sh
