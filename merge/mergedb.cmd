set MODULE=%1
set REVISION=%2C

echo Merging %MODULE%

set BASEDIR=..\..\..\..\1.0.x\ks-cfg-dbs
set BASEURL=https://test.kuali.org/svn/student/ks-cfg-dbs

set DIR=%BASEDIR%\%MODULE%
set URL=%BASEURL%/%MODULE%/trunk
svn merge -r %REVISION%:HEAD %URL% %DIR% 

