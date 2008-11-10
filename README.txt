Standalone Build
================
mvn install with gwt-rice profile (This will fail on the lum-web module but that's ok)
go to /lum-web and run mvn install again

Setup DB
========
have oracle running on localhost:1521:xe
create oracle user rice094br with password kuali
init with impl/src/main/config/sql/rice_db_bootstrap.sql from ks branch
  https://test.kuali.org/svn/rice/branches/rice-release-0-9-4-ks-080827-br/

Run Standalone on Tomcat
========================
use mvn build... from lum-overlay/pom.xml to setup run
goal: tomcat:run-war
profile: gwt-rice
click JRE tab and add the following to VM Args section
  -XX:MaxPermSize=128m

Configure Workflow (one time setup)
===================================
Once tomcat started point browser at
  http://localhost:8080/lum-umd/en
click Workflow -> Workflow Admin
login as admin
choose XML Injester from lower left menu and ingest the following files from project
injest:
  https://test.kuali.org/svn/rice/branches/rice-release-0-9-4-ks-080827-br/impl/src/main/config/xml/KEWBootstrap.xml
  lum-overlay/src/main/config/CluDocument.xml
if you don't want to create users & rules by hand ingest the following files from project:
  lum-overlay/src/main/config/WorkflowUsers.xml
  lum-overlay/src/main/config/WorkflowRules.xml
 

Stop Standalone on Tomcat
=========================
until we figure out why the goal tomcat:stop isn't working just click
  red button on console.  You will have to delete lum-overlay/target/logs/*
  between runs.