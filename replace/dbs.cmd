@echo off
set QUALIFIER=trunk
set VERSION=

set MODULE=ks-core-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%

set MODULE=ks-lum-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%

set MODULE=ks-rice-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%

set MODULE=ks-embedded-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%

set MODULE=ks-rice-client-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%

set MODULE=ks-rice-server-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%

