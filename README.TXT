  The ks-impex-launcher is a self contained maven wrapper for the Kuali impex project, you
 will not need to checkout impex or any database files manually.  The project is preconfigured
 for Oracle and if you use the standard install url you will not need to change any settings.
 
   In order for impex to drop/create users and assign database roles you must first prepare your
 Oracle database by running a profile one time.
 
  1. Right click on the pom and choose Run As -> Maven build...
  2. In the Goals section of the run dialog enter: verify
  3. In the Profiles section of the run dialog enter: ks.setup.oracle
  4. Click the Add button next to the Parameters section
  5. In the Add Paramter dialog box enter a name of: import.admin.password and for the value
     enter your system password for oracle.
  6. Click Run.
  
  When impex is run it will svn checkout/update the data project, drop and recreate the database user, then create
and load the tables.  To update or reset your database just re-run the impex project.  After the first time
it is run you will see an error message early in the job stating that it could not create the user, this is normal
and can be ingnored (it should drop/create the user shortly after the error message).
  To run impex do the following

  1. Right click on the pom and choose Run As -> Maven build...
  2. In the Goals section of the run dialog enter: verify
  3. Click Run.
  
  The most commonly changed poperties are located in src/main/resources/ks.custom.properties.  You can modify
these if you have a different db url or would like to use a different dataset.

=====

  The ks-embedded-launcher project will download ks-embedded.war from the maven repository and launch it
without building or compiling.  The project will not redownload the war for subsequent runs, you will need
to run mvn clean to trigger a download.  The impex project is configured to create a database matching
the default connection properties for the ks-embedded project.

To launch ks-embedded-launcher 
  
  1. Right click on the pom and choose Run As -> Maven build...
  2. In the Goals section of the run dialog enter: tomcat:run-war
  3. Click on the JRE tab and enter the following in the VM arguments box: -Xmx1024m -XX:MaxPermSize=256m
  4. Click Run