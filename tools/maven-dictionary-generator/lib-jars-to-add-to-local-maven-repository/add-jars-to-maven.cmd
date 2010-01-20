rem this should add to the local maven repository
rem the google gdata files
SETLOCAL
set JAVA_HOME=C:\Program Files\Java\jdk1.6.0_16
call C:\apache-maven-2.2.1\bin\mvn install:install-file -DgroupId=com.google.gdata -DartifactId=gdata-base -Dversion=1.0 -Dpackaging=jar -Dfile=gdata-base-1.0.jar
call C:\apache-maven-2.2.1\bin\mvn install:install-file -DgroupId=com.google.gdata -DartifactId=gdata-spreadsheet -Dversion=3.0 -Dpackaging=jar -Dfile= -Dfile=gdata-spreadsheet-3.0.jar
call C:\apache-maven-2.2.1\bin\mvn install:install-file -DgroupId=com.google.gdata -DartifactId=gdata-core -Dversion=1.0 -Dpackaging=jar -Dfile=gdata-core-1.0.jar
call C:\apache-maven-2.2.1\bin\mvn install:install-file -DgroupId=com.google.gdata -DartifactId=gdata-collect -Dversion=1.0 -Dpackaging=jar -Dfile=google-collect-1.0-rc1.jar

@rem this next two jaxb-xjc contain codemodel
call C:\apache-maven-2.2.1\bin\mvn install:install-file -DgroupId=com.sun.xml.bind -DartifactId=jaxb-xjc -Dversion=2.1.8 -Dpackaging=jar -Dfile=jaxb-xjc-2.1.8.jar
call C:\apache-maven-2.2.1\bin\mvn install:install-file -DgroupId=com.sun.xml.bind -DartifactId=jaxb-xjc -Dversion=2.1.8 -Dpackaging=jar -Dfile=jaxb-xjc-2.1.8-sources.jar -Dclassifier=sources
rem call C:\apache-maven-2.2.1\bin\mvn install:install-file -DgeneratePom=true -DgroupId=com.sun.codemodel -DartifactId=codemodel -Dversion=2.2 -Dpackaging=jar -Dfile=codemodel-2.2.jar
rem call C:\apache-maven-2.2.1\bin\mvn install:install-file -DgeneratePom=true -DgroupId=com.sun.codemodel -DartifactId=codemodel -Dversion=2.2 -Dpackaging=jar -Dfile=codemodel-2.2-sources.jar -Dclassifier=sources
rem call C:\apache-maven-2.2.1\bin\mvn install:install-file -DgeneratePom=true -DgroupId=com.sun.codemodel -DartifactId=codemodel -Dversion=2.2 -Dpackaging=jar -Dfile=codemodel-2.2-javadoc.jar -Dclassifier=javadoc


pause
