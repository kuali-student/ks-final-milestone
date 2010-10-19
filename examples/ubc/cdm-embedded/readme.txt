UBC Cdm Kuali Student Embedded Readme

Notes:
- configured for M6, need to run impex for ks-embedded for M6
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

3. start cdm-embedded

4. Load the custom workflow:
a. login as admin 
b. navigate to Rice --> Administration --> XML Ingester 
c. browse for: cdm-embedded\src\main\resources\cdm_workflow.xml
d. click "upload xml data"

