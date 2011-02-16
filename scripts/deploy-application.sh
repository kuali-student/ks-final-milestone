#!/bin/sh
#
#

WORKSPACE=/opt/hudson/home/jobs/1.1-full-build/workspace
VERSION=$1
SVN_DIR=$2
ENVIRONMENT=$3

if [ "$VERSION" = "" ]
then
  echo Error!!! VERSION cannot be empty
  echo -------------------------------------------
  echo Usage: deploy-application.sh 1.1 ks-1.1 dev
  echo -------------------------------------------
  exit 0
fi
if [ "$SVN_DIR" = "" ]
then
  echo Error!!! SVN_DIR cannot be empty
  echo -------------------------------------------
  echo Usage: deploy-application.sh 1.1 ks-1.1 dev
  echo -------------------------------------------
  exit 0
fi
if [ "$ENVIRONMENT" = "dev" ]
then
  DB_SCHEMA=KSDEV
  REMOTE_USER=deploy
  REMOTE_DIR=dev
elif [ "$ENVIRONMENT" = "staging" ]
then
  DB_SCHEMA=KSSTAGING
  REMOTE_USER=staging
  REMOTE_DIR=staging
else
  echo Error!!! The environment must be either dev or staging
  echo -------------------------------------------
  echo Usage: deploy-application.sh 1.1 ks-1.1 dev
  echo -------------------------------------------
  exit 0
fi

WAR_FILE=ks-embedded-$VERSION-SNAPSHOT.war
LOCAL_WAR_FILE=$WORKSPACE/$SVN_DIR/ks-web/ks-embedded/target/$WAR_FILE
REMOTE_WAR_FILE=/usr/local/student/downloads/$WAR_FILE
REMOTE_TOMCAT_DIR=/usr/local/student/embedded/$REMOTE_DIR
LOCAL_MVN_DIR=$WORKSPACE/$SVN_DIR/ks-cfg-dbs/ks-embedded-db
DB_URL=jdbc:oracle:thin:@deploy.ks.kuali.org:1521:KS
DBA_PASSWORD=gw570229
REMOTE_SERVER=root@deploy.ks.kuali.org
PEM_FILE=/home/tomcat/ks-key.pem

# Copy the newly generated war file into the downloads directory
scp -i $PEM_FILE $LOCAL_WAR_FILE $REMOTE_SERVER:$REMOTE_WAR_FILE
# Stop Tomcat
ssh -i $PEM_FILE $REMOTE_SERVER 'su - $REMOTE_USER -c /usr/local/tomcat_$REMOTE_USER/bin/shutdown.sh'
# Remove the previous backup
ssh -i $PEM_FILE $REMOTE_SERVER 'rm -rf $REMOTE_TOMCAT_DIR.bak'
# Remove the current by moving it to a directory with a .bak extension
ssh -i $PEM_FILE $REMOTE_SERVER 'mv $REMOTE_TOMCAT_DIR $REMOTE_TOMCAT_DIR.bak'
# Unzip the new WAR into the right directory
ssh -i $PEM_FILE $REMOTE_SERVER 'unzip $REMOTE_WAR_FILE -d $REMOTE_TOMCAT_DIR'


M2_HOME=/opt/java/apache-maven-3.0
export M2_HOME
cd $LOCAL_MVN_DIR
mvn clean install -Pks-db,oracle -Dks.impex.username=$DB_SCHEMA -Dks.impex.password=$DB_SCHEMA -Dks.impex.dba.password=$DBA_PASSWORD -Dks.impex.url=$DB_URL

# Perform some cleanup and restart Tomcat
ssh -i $PEM_FILE $REMOTE_SERVER 'su - $REMOTE_USER -c /usr/local/tomcat_$REMOTE_USER/bin/cleanup.sh'
ssh -i $PEM_FILE $REMOTE_SERVER 'su - $REMOTE_USER -c /usr/local/tomcat_$REMOTE_USER/bin/startup.sh'
