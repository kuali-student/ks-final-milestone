@echo off
rem Dev settings
set QUALIFIER=trunk
set VERSION=

set MODULE=ks-core-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%
