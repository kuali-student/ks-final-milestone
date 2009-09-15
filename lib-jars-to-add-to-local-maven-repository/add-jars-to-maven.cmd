rem this should add to the local maven repository
rem the google gdata files
call C:\apache-maven-2.1.0\bin\mvn install:install-file -DgroupId=com.google.gdata -DartifactId=gdata-base -Dversion=1.0 -Dpackaging=jar -Dfile=gdata-base-1.0.jar
call C:\apache-maven-2.1.0\bin\mvn install:install-file -DgroupId=com.google.gdata -DartifactId=gdata-spreadsheet -Dversion=3.0 -Dpackaging=jar -Dfile= -Dfile=gdata-spreadsheet-3.0.jar
call C:\apache-maven-2.1.0\bin\mvn install:install-file -DgroupId=com.google.gdata -DartifactId=gdata-core -Dversion=1.0 -Dpackaging=jar -Dfile=gdata-core-1.0.jar
call C:\apache-maven-2.1.0\bin\mvn install:install-file -DgroupId=com.google.gdata -DartifactId=gdata-collect -Dversion=1.0 -Dpackaging=jar -Dfile=google-collect-1.0-rc1.jar

pause
