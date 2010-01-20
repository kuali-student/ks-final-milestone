rem this is the usage information
java -jar kuali-search-generator.jar
pause
rem this will run the dicitonary generator
java -jar kuali-search-generator.jar  "..\trunk\src\test\resources\search\Organization Search Specification.xls" "..\trunk\src\test\resources\search\organization-search-config-generated-excel.xml"
pause
