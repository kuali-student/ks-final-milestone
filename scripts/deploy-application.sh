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
REMOTE_WAR_FILE=/usr/local/student/downloads/$WAR_FILE
LOCAL_MVN_DIR=$APP_WORKSPACE/$SVN_DIR/ks-cfg-dbs/ks-embedded-db
DB_URL=jdbc:oracle:thin:@deploy.ks.kuali.org:1521:KS
DBA_PASSWORD=gw570229
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
  DB_SCHEMA=KSDEV
  REMOTE_USER=deploy
  REMOTE_DIR=dev
  SHUTDOWN_CMD="su - $REMOTE_USER -c /usr/local/tomcat_$REMOTE_USER/bin/shutdown.sh"
  CLEANUP_CMD="su - $REMOTE_USER -c /usr/local/tomcat_$REMOTE_USER/bin/cleanup.sh"
  STARTUP_CMD="su - $REMOTE_USER -c /usr/local/tomcat_$REMOTE_USER/bin/startup.sh"
elif [ "$ENVIRONMENT" = "staging" ]
then
  DB_SCHEMA=KSSTAGING
  REMOTE_USER=staging
  REMOTE_DIR=staging
  SHUTDOWN_CMD="su - $REMOTE_USER -c /usr/local/tomcat_$REMOTE_USER/bin/shutdown.sh"
  CLEANUP_CMD="su - $REMOTE_USER -c /usr/local/tomcat_$REMOTE_USER/bin/cleanup.sh"
  STARTUP_CMD="su - $REMOTE_USER -c /usr/local/tomcat_$REMOTE_USER/bin/startup.sh"
elif [ "$ENVIRONMENT" = "demo" ]
then
  DB_SCHEMA=KSDEMO
  REMOTE_USER=demo
  REMOTE_DIR=demo
  DB_URL=jdbc:oracle:thin:@demo.ks.kuali.org:1521:KS
  REMOTE_SERVER=root@demo.ks.kuali.org
  SHUTDOWN_CMD=/usr/local/tomcat/bin/shutdown.sh
  STARTUP_CMD=/usr/local/tomcat/bin/startup.sh
  CLEANUP_CMD=/usr/local/tomcat/bin/cleanup.sh
else
  echo Error!!  The ENVIRONMENT must be either dev, staging, or demo
  echo ------------------------------------------------------------------------------------------
  echo Usage: deploy-application.sh 1.1 ks-1.1 dev /opt/hudson/home/jobs/1.1-full-build/workspace
  echo ------------------------------------------------------------------------------------------
  exit 0
fi

REMOTE_TOMCAT_DIR=/usr/local/student/embedded/$REMOTE_DIR

# Copy the newly generated war file into the downloads directory
scp -i $PEM_FILE $LOCAL_WAR_FILE $REMOTE_SERVER:$REMOTE_WAR_FILE
# Stop Tomcat
ssh -i $PEM_FILE $REMOTE_SERVER $SHUTDOWN_CMD
# Remove the previous backup
ssh -i $PEM_FILE $REMOTE_SERVER rm -rf $REMOTE_TOMCAT_DIR.bak
# Remove the current by moving it to a directory with a .bak extension
ssh -i $PEM_FILE $REMOTE_SERVER mv $REMOTE_TOMCAT_DIR $REMOTE_TOMCAT_DIR.bak
# Unzip the new WAR into the right directory
ssh -i $PEM_FILE $REMOTE_SERVER unzip $REMOTE_WAR_FILE -d $REMOTE_TOMCAT_DIR


# Use maven to reload the database using Impex
M2_HOME=/opt/java/apache-maven-3.0
export M2_HOME
cd $LOCAL_MVN_DIR
mvn clean install -Pks-db,oracle -Dks.impex.username=$DB_SCHEMA -Dks.impex.password=$DB_SCHEMA -Dks.impex.dba.password=$DBA_PASSWORD -Dks.impex.url=$DB_URL

# Perform some cleanup and restart Tomcat
ssh -i $PEM_FILE $REMOTE_SERVER $CLEANUP_CMD
ssh -i $PEM_FILE $REMOTE_SERVER $STARTUP_CMD

