set MODULE=%1
set REVISION=%2

set BASEDIR=..\..\..\..\1.0.x\ks-cfg-dbs
set BASEURL=https://test.kuali.org/svn/student/ks-cfg-dbs

set DIR=%BASEDIR%\%MODULE%\src\main\resources\impex
set URL=%BASEURL%/%MODULE%/trunk

echo %URL%
svn merge -r %REVISION%:HEAD %URL% %DIR% 

