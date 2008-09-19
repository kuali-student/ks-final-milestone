1. Setup oracle using 0.9.4 sql bootstrap: rice_db_bootstrap.sql

	Can find it here: https://test.kuali.org/svn/rice/branches/rice-release-0-9-4-ks-080827-br/impl/src/main/config/sql/rice_db_bootstrap.sql

2. In ks-poc-rice-war, edit ks-rice-config.xml with your oracle configuration.
	
	datasource.url
	datasource.username
	datasource.password
	
3. Right click on pom and select "Maven build as..." to create and run launch configuration with the following settings

	Goals: 
		clean tomcat:run-war
	
	Parameters: 
		name=kew.bootstrap.spring.file
		value=ks-rice-beans.xml
		
4. Once running goto http://localhost:8181/ks/. Project should have links to Kuali Student Workflow Example and
	Kuali Student POC
	
5. If you want to try the workflow example, you need to ingest a few files.

	Goto http://localhost:8181/ks/en/Portal.do, login as admin, and select XML Ingester and ingest the following
	files:

	From: https://test.kuali.org/svn/rice/branches/rice-release-0-9-4-ks-080827-br/impl/src/main/config/xml
		
		RiceBootstrapData.xml  

	From https://test.kuali.org/svn/student/sandbox/team2/ks-workflow/branches/ks-workflow-dev/src/main/config/
	
		users.xml
		course-doctype-full.xml
	
