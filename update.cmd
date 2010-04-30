@echo off
date /t 
time /t
set QUALIFIER=branches
set VERSION=dev
call removeThenCopy ks-security %QUALIFIER% %VERSION%
call removeThenCopy ks-core %QUALIFIER% %VERSION%
call removeThenCopy brms %QUALIFIER% %VERSION%
call removeThenCopy ks-lum %QUALIFIER% %VERSION%
call removeThenCopy ks-web %QUALIFIER% %VERSION%
call cfg-dbs
date /t 
time /t
