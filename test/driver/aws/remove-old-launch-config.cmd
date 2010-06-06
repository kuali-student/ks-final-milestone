@echo off
if "%1"=="" goto oops
set VERSION=%1

echo -----------------------------------------------------
@echo on
as-version
as-delete-launch-config kuali-nexus-lc-%VERSION%
@echo off
echo -----------------------------------------------------

goto end

:oops

echo -------------------------------------
echo Error!!! You must supply version: 001
echo -------------------------------------
:end
