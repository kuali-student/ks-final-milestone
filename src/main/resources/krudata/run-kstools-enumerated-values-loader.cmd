@echo off
echo credit course loader
echo this is the usage information
java -jar kuali-enumerated-value-loader.jar
echo hit enter to actually run it
pause
rem this will run the credit course loader
java -jar kuali-enumerated-value-loader.jar "EnumeratedValuesCIPCode2000.xls" "http://localhost:9393/ks-embedded-dev"
pause
