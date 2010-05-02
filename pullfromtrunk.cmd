@echo off
if "%1"=="" goto oops

set REVISION=%1
@echo on
svn merge -r %REVISION%:HEAD https://test.kuali.org/svn/student/deploymentlab/trunk/1.0.x
@echo off

goto end

:oops

echo ---------------------------------------------------------------
echo Error!!! You must supply a revision number: pullfromtrunk 12967
echo ---------------------------------------------------------------
:end