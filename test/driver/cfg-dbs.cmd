@echo off
rem Dev settings
set QUALIFIER=trunk
set VERSION=

rem Deployment lab settings
rem set QUALIFIER=tags
rem set VERSION=1.0.0-rc1.1

set MODULE=ks-core-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%

set MODULE=ks-embedded-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%

set MODULE=ks-rice-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%

set MODULE=ks-lum-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%
