@echo off
if "%1"=="" goto oops

set REVISION=%1

set MODULE=ks-core-db
call mergedb %MODULE% %REVISION%

set MODULE=ks-lum-db
call mergedb %MODULE% %REVISION%

set MODULE=ks-rice-db
call mergedb %MODULE% %REVISION%

set MODULE=ks-embedded-db
call mergedb %MODULE% %REVISION%

set MODULE=ks-rice-client-db
call mergedb %MODULE% %REVISION%

set MODULE=ks-rice-server-db
call mergedb %MODULE% %REVISION%

goto end

:oops

echo ----------------------------------------------------------
echo Error!!! You must supply a revision number: mergedbs 12857
echo ----------------------------------------------------------
:end