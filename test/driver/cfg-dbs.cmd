set QUALIFIER=tags
set VERSION=1.0.0-m5

set MODULE=ks-core-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%

set MODULE=ks-embedded
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%

set MODULE=ks-rice-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%

set MODULE=ks-lum-db
call removeThenCopyDb %MODULE% %QUALIFIER% %VERSION%
