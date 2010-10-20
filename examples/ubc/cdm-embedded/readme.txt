UBC CDM Kuali Student Embedded Readme

Notes:
- configured for M8, need to run impex for ks-embedded for M8
- ks-embedded-config is located in src\main\resources, place that in {user_home}\kuali\main\dev
- pom.xml is configured to compile instituion specific GWT entry point: src\main\java\ca\ubc\student\kuali\lum\lu\ui\main\CDMLUMMain.gwt.xml

Instructions:
Install as you would ks-embedded, but with the following changes

1. load ks-embedded-db and cdm-embedded-db via impex.  cdm-embedded-db is a second overlayed data project.  Apply these settings in your impex-build.properties:
svnroot.2=https://test.kuali.org/svn/
svn.module.2=cdm-embedded-db
svn.base.2=trunk
torque.schema.dir.2=../../ks-cfg-dbs/${svn.module.2}
torque.sql.dir.2=${torque.schema.dir.2}/sql
run.schema.sql.2=false 
truncate.data.sql.2=false

2. execute  the following SQL scripts located in cdm-embedded-db
cdm_setup.sql
sync_sequences.sql

3. Edit your ks-embedded-config.xml to include/update:
  <param name="appserver.url">http://localhost:8080</param>
  <param name="app.context.name">cdm-embedded</param>
  <param name="ks.institutional.context">classpath:CdmSpringBeans.xml</param>
  <param name="ks.lum.dictionary.serviceContextLocations">classpath:lu-dictionary-config.xml,classpath:lo-dictionary-config.xml,classpath:cdm-lu-dictionary-config.xml,classpath:cdm-lo-dictionary-config.xml</param> 
  <param name="ks.lum.orchestration.dictionaryContextLocations">classpath:lum-orchestration-dictionary.xml,classpath:cdm-lum-orchestration-dictionary.xml</param>
  <param name="ks.lum.ui.lookupContextLocations">classpath:cdm-lum-ui-lookup-context.xml</param>  
  <param name="ks.core.dictionary.serviceContextLocations">classpath:comment-dictionary-config.xml,classpath:document-dictionary-config.xml,classpath:organization-dictionary-config.xml,classpath:cdm-core-dictionary-config.xml</param>
  <param name="ks.core.orchestration.dictionaryContextLocations">classpath:org-orchestration-dictionary.xml,classpath:cdm-org-orchestration-dictionary.xml</param>
  <param name="encryption.key">yourencryptionkey</param>
  
4. start cdm-embedded

5. Load the custom workflow:
a. login as admin 
b. navigate to Rice --> Administration --> XML Ingester 
c. browse for: cdm-embedded\src\main\resources\cdm_workflow.xml
d. click "upload xml data"

