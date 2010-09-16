@echo off
echo enumerated values loader
pause
@rem this will run the credit course loader
@rem java -jar kuali-enumerated-value-loader.jar "EnumeratedValuesCIPCode2000.xls" "http://localhost:9393/ks-embedded-dev"
@rem java -jar kuali-enumerated-value-loader.jar "EnumeratedValuesCIPCode2010.xls" "http://localhost:9393/ks-embedded-dev"
@rem java -jar kuali-enumerated-value-loader.jar "EnumeratedValuesUniversityClassification.xls" "http://localhost:9393/ks-embedded-dev"
java -jar kuali-enumerated-value-loader.jar "EnumeratedValuesProgramLevel.xls" "http://localhost:9393/ks-embedded-dev"
pause
