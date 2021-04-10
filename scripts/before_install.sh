#! /bin/bash
rm -rf /tomcat/apache-tomcat-4.1.40/webapps/buptlab
rm /tomcat/apache-tomcat-4.1.40/webapps/buptlab.war
chmod -R 777 /opt/codedeploy-agent/deployment-root/$DEPLOYMENT_GROUP_ID/$DEPLOYMENT_ID/deployment-archive/scripts