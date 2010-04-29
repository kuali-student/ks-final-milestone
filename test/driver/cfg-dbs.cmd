set QUALIFIER=tags
set VERSION=1.0.0-rc1.1

set MODULE=ks-core-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%

set MODULE=ks-embedded-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%

set MODULE=ks-rice-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%

set MODULE=ks-lum-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%
