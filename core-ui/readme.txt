To run GWT in hosted mode with maven:
--------------------------------------
	Using maven command line
	1. Run "mvn -P build-gwt test" 

	OR 
	
	Using maven eclipse:
	1. Right click on pom and chose [Run As]->[Maven build...]
	2. Under Goals enter "test"
	3. Under Profiles enter "build-gwt"
	4. Click run

Debug/Run without using maven
-----------------------------------
Must do step above once, after that you can use the following 

1. Run/debug the launch configuration "OrgGWT.launch"

If making changes to GWT servlets, you need to do mvn install again to build 
these into the war directory.  