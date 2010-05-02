@echo off
if "%1"=="" goto oops

set REVISION=%1

call mergemodules %1
call mergedbs %1

goto end

:oops

echo -------------------------------------------------------
echo Error!!! You must supply a revision number: merge 12910
echo -------------------------------------------------------
:end