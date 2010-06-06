@echo off
if "%1"=="" goto oops
if "%2"=="" goto oops
set VERSION=%1
set AMI=%2

echo -------------------------------------------------------
call as-version
call as-create-launch-config kuali-nexus-lc-%VERSION% --image-id %AMI% --instance-type m1.small --group nexus.ks,default --key ks-key
call as-update-auto-scaling-group kuali-nexus-as --launch-configuration kuali-nexus-lc-%VERSION%
echo -------------------------------------------------------

goto end

:oops

echo ------------------------------------------------------------
echo Error!!! You must supply version and AMI Id: 01 ami-c4bf57ad
echo ------------------------------------------------------------
:end
