@echo off
echo organization loader
pause
java -jar kuali-organization-loader.jar "AccreditingBodies.xls" "http://localhost:9393/ks-embedded-dev"
pause
