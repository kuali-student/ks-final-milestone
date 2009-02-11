To run GWT in hosted mode:
1. Run "mvn install"
2. Run "mvn gwt:gwt"

After that you can run and debug hosted mode straight from eclipse without using mvn:
1. Run/debug the launch configuration "OrgGWT.launch"

If making changes to GWT servlets, you need to do mvn install again to build 
these into the war directory.  