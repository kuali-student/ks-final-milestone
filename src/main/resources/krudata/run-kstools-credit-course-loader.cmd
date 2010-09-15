@echo off
echo credit course loader
echo this is the usage information
java -jar ..\..\..\..\target\ks-tools-1.0.0-SNAPSHOT.one-jar.jar
echo hit enter to actually run it
pause
rem this will run the credit course loader
java -jar ..\..\..\..\target\ks-tools-1.0.0-SNAPSHOT.one-jar.jar "courses.xls" "http://localhost:9393/ks-embedded-dev"
pause
