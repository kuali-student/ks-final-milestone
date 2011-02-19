#!/bin/sh
#
#

#WORKSPACE=/opt/hudson/home/jobs/1.1-full-build/workspace

VERSION=$1
SVN_DIR=$2
ENVIRONMENT=$3
APP_WORKSPACE=$4
WAR_FILE=ks-embedded-$VERSION-SNAPSHOT.war
LOCAL_WAR_FILE=$APP_WORKSPACE/$SVN_DIR/ks-web/ks-embedded/target/$WAR_FILE
LOCAL_MVN_DIR=$APP_WORKSPACE/$SVN_DIR/ks-cfg-dbs/ks-embedded-db
REMOTE_DB_URL=jdbc:oracle:thin:@deploy.ks.kuali.org:1521:KS
REMOTE_DBA_PASSWORD=gw570229
REMOTE_SERVER=root@deploy.ks.kuali.org
PEM_FILE=/home/tomcat/ks-key.pem



if [ "$APP_WORKSPACE" = "" ]
then
  echo APP_WORKSPACE is empty
  echo ------------------------------------------------------------------------------------------
  echo Usage: deploy-application.sh 1.1 ks-1.1 dev /opt/hudson/home/jobs/1.1-full-build/workspace
  echo ------------------------------------------------------------------------------------------
  exit 0
fi
if [ "$VERSION" = "" ]
then
  echo VERSION is empty
  echo ------------------------------------------------------------------------------------------
  echo Usage: deploy-application.sh 1.1 ks-1.1 dev /opt/hudson/home/jobs/1.1-full-build/workspace
  echo ------------------------------------------------------------------------------------------
  exit 0
fi
if [ "$SVN_DIR" = "" ]
then
  echo SVN_DIR is empty
  echo ------------------------------------------------------------------------------------------
  echo Usage: deploy-application.sh 1.1 ks-1.1 dev /opt/hudson/home/jobs/1.1-full-build/workspace
  echo ------------------------------------------------------------------------------------------
  exit 0
fi
if [ "$ENVIRONMENT" = "dev" ]
then
  REMOTE_DB_SCHEMA=KSDEV
  REMOTE_USER=deploy
  REMOTE_USER_HOME=/usr/local/tomcat_deploy
  REMOTE_TOMCAT_HOME=/usr/local/tomcat_deploy
  REMOTE_SHUTDOWN_CMD="su - $REMOTE_USER -c '$REMOTE_TOMCAT_HOME/bin/shutdown.sh 60 -force'"
  REMOTE_CLEANUP_CMD="su - $REMOTE_USER -c $REMOTE_TOMCAT_HOME/bin/cleanup.sh"
  REMOTE_STARTUP_CMD="su - $REMOTE_USER -c $REMOTE_TOMCAT_HOME/bin/startup.sh"
elif [ "$ENVIRONMENT" = "staging" ]
then
  DB_SCHEMA=KSSTAGING
  REMOTE_USER=staging
  REMOTE_DIR=staging
  SHUTDOWN_CMD="su - $REMOTE_USER -c '$REMOTE_TOMCAT_HOME/bin/shutdown.sh 60 -force'"
  CLEANUP_CMD="su - $REMOTE_USER -c $REMOTE_TOMCAT_HOME/bin/cleanup.sh"
  STARTUP_CMD="su - $REMOTE_USER -c $REMOTE_TOMCAT_HOME/bin/startup.sh"
elif [ "$ENVIRONMENT" = "demo" ]
then
  REMOTE_SERVER=root@demo.ks.kuali.org
  REMOTE_USER_HOME=/usr/local/tomcat
  REMOTE_TOMCAT_HOME=/usr/local/tomcat
  REMOTE_DB_SCHEMA=KSDEMO
  REMOTE_DB_URL=jdbc:oracle:thin:@demo.ks.kuali.org:1521:KS
  REMOTE_SHUTDOWN_CMD="$REMOTE_TOMCAT_HOME/bin/shutdown.sh 60 -force"
  REMOTE_STARTUP_CMD=$REMOTE_TOMCAT_HOME/bin/startup.sh
  REMOTE_CLEANUP_CMD=$REMOTE_TOMCAT_HOME/bin/cleanup.sh
else
  echo Error!!  The ENVIRONMENT must be either dev, staging, or demo
  echo ------------------------------------------------------------------------------------------
  echo Usage: deploy-application.sh 1.1 ks-1.1 dev /opt/hudson/home/jobs/1.1-full-build/workspace
  echo ------------------------------------------------------------------------------------------
  exit 0
fi


REMOTE_WAR_DIR=$REMOTE_TOMCAT_HOME/webapps/ROOT
REMOTE_WAR_FILE=$REMOTE_TOMCAT_HOME/webapps/ROOT.war

# Stop Tomcat
ssh -i $PEM_FILE $REMOTE_SERVER $REMOTE_SHUTDOWN_CMD

# Remove the existing .war file and ROOT directory
ssh -i $PEM_FILE $REMOTE_SERVER rm -rf $REMOTE_WAR_DIR
ssh -i $PEM_FILE $REMOTE_SERVER rm $REMOTE_WAR_FILE

# Copy the newly generated war file into the webapps directory
scp -i $PEM_FILE $LOCAL_WAR_FILE $REMOTE_SERVER:$REMOTE_WAR_FILE

# Drop/create the remote database using Impex
M2_HOME=/opt/java/apache-maven-3.0
export M2_HOME
cd $LOCAL_MVN_DIR
mvn clean install -Pks-db,oracle -Dks.impex.username=$REMOTE_DB_SCHEMA -Dks.impex.password=$REMOTE_DB_SCHEMA -Dks.impex.dba.password=$REMOTE_DBA_PASSWORD -Dks.impex.url=$REMOTE_DB_URL

# Perform some cleanup and restart Tomcat
ssh -i $PEM_FILE $REMOTE_SERVER $REMOTE_CLEANUP_CMD
ssh -i $PEM_FILE $REMOTE_SERVER $REMOTE_STARTUP_CMD

