@echo off
if "%1"=="" goto oops

set REVISION=%1

set MODULE=ks-security
call mergemodule %MODULE% %REVISION%

set MODULE=ks-core
call mergemodule %MODULE% %REVISION%

set MODULE=ks-lum
call mergemodule %MODULE% %REVISION%

set MODULE=ks-web
call mergemodule %MODULE% %REVISION%

set MODULE=brms
call mergemodule %MODULE% %REVISION%

goto end

:oops

echo -----------------------------------------------------------
echo Error!!! You must supply a revision number: mergecode 12857
echo -----------------------------------------------------------

:end
