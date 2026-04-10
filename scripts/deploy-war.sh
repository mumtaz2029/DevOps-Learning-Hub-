#!/bin/bash

set -euo pipefail

APP_NAME="project4-devops-app.war"
TOMCAT_WEBAPPS="/opt/tomcat/webapps"

echo "Building WAR file with Maven..."
mvn clean package

echo "Copying WAR file to Tomcat webapps directory..."
sudo cp "target/${APP_NAME}" "${TOMCAT_WEBAPPS}/${APP_NAME}"

echo "Restarting Tomcat..."
sudo systemctl restart tomcat

echo "Deployment complete. Open http://<ec2-public-ip>:8080/project4-devops-app/"
