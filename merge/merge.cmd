@echo off
if "%1"=="" goto oops

set REVISION=%1
echo -----------------------------------------------------
call mergemodules %REVISION%
call mergedbs %REVISION%
echo -----------------------------------------------------

goto end

:oops

echo -------------------------------------------------------
echo Error!!! You must supply a revision number: merge 12910
echo -------------------------------------------------------
:end