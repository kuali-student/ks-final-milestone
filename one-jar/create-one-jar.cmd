rem this should create the one jar that can be executed
rmdir /q /s build
erase /q kuali-dictionary-generator.jar
mkdir build
xcopy /s one-jar-classes build
mkdir build\main
xcopy ..\trunk\target\maven-ksdictionary-generator-plugin-0.0.1-SNAPSHOT.jar build\main
mkdir build\lib
xcopy ..\lib-jars-to-add-to-local-maven-repository\*.jar build\lib
xcopy "C:\Documents and Settings\nwright\.m2\repository\net\sourceforge\jexcelapi\jxl\2.6\*.jar" build\lib
cd build
call jar -cvmf ..\MANIFEST.MF ..\kuali-dictionary-generator.jar *
pause
