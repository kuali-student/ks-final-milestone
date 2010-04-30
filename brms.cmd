rem
rem BRMS is slightly different.  The svn directory is "brms"
rem but the svn tag is "ks-brms"
rem

set MODULE=brms
set QUALIFIER=branches
set VERSION=dev
set CM=https://test.kuali.org/svn/student/deploymentlab/trunk/1.0.x/%MODULE%
set DEV=https://test.kuali.org/svn/student/%MODULE%/%QUALIFIER%/ks-%MODULE%-%VERSION%
svn delete -m "Removing %MODULE%" %CM%
svn copy -m "Copying %MODULE%/%QUALIFIER%/%MODULE%-%VERSION% from development svn" --username jcaddel --password gw570229 %DEV% %CM%


set MODULE=brms
set QUALIFIER=branches
set VERSION=dev
call removeThenCopy %MODULE% %QUALIFIER% %VERSION%
