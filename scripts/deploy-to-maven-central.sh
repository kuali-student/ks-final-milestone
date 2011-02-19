#!/bin/sh
#
#

M2_HOME=/opt/java/apache-maven-3.0
export M2_HOME
ARTIFACT_ID=$1
cd $WORKSPACE/$ARTIFACT_ID/target/checkout
#mvn deploy -Pkuali-release,sonatype-oss-release -Dmaven.test.skip=true -X -Dnexus.verboseDebug=true
mvn deploy nexus:staging-close nexus:staging-release -Pkuali-release,sonatype-oss-release -Dmaven.test.skip=true

