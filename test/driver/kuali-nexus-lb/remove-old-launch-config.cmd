@echo off
if "%1"=="" goto oops
set VERSION=%1

echo -----------------------------------------------------
call as-version
call as-delete-launch-config kuali-nexus-lc-%VERSION%
echo -----------------------------------------------------

goto end

:oops

echo ------------------------------------
echo Error!!! You must supply version: 01
echo ------------------------------------
:end
