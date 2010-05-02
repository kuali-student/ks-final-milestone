@echo off
if "%1"=="" goto oops

set REVISION=%1
set BASE=..\..\..\..\1.0.x

set DIR=%BASE%\ks-security
svn merge -r %REVISION%:HEAD https://test.kuali.org/svn/student/ks-security/branches/ks-security-dev
cd ..

cd ks-core
svn merge -r %LAST_MERGE_REVISION%:HEAD https://test.kuali.org/svn/student/ks-core/branches/ks-core-dev
cd ..

cd brms
svn merge -r %LAST_MERGE_REVISION%:HEAD https://test.kuali.org/svn/student/brms/branches/branches-dev
cd ..

cd ks-lum
svn merge -r %LAST_MERGE_REVISION%:HEAD https://test.kuali.org/svn/student/ks-lum/branches/ks-lum-dev
cd ..

cd ks-web
svn merge -r %LAST_MERGE_REVISION%:HEAD https://test.kuali.org/svn/student/ks-web/branches/ks-lum-web
cd ..

goto end
:oops

echo -----------------------------------------------------------
echo Error!!! You must supply a revision number: mergecode 12857
echo -----------------------------------------------------------

:end
cd ..\test\driver\trunk