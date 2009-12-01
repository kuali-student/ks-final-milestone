rem this should create the one jar that can be executed
@setlocal
set JAVA_HOME="C:\Program Files\Java\jdk1.6.0_16"
rmdir /q /s build
erase /q kuali-search-generator.jar
mkdir build
copy one-jar-boot-0.96.jar kuali-search-generator.jar
@rem xcopy /s one-jar-classes build
mkdir build\main
xcopy ..\trunk\target\maven-ksdictionary-generator-plugin-1.0.0-SNAPSHOT.jar build\main
mkdir build\lib
xcopy ..\lib-jars-to-add-to-local-maven-repository\*.jar build\lib
xcopy "C:\m2\repository\net\sourceforge\jexcelapi\jxl\2.6\*.jar" build\lib
@rem had to use copy instead of xcopy to avoid it prompting me if it is a file or directory
copy SearchMANIFEST.MF build\MANIFEST.MF
cd build
call %JAVA_HOME%\bin\jar -uvmf MANIFEST.MF ..\kuali-search-generator.jar *
pause
