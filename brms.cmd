rem
rem BRMS is slightly different.  The svn directory is "brms"
rem but the svn tag is "ks-brms"
rem

set MODULE=brms
set QUALIFIER=branches
set VERSION=dev
set QA=https://test.kuali.org/svn/student/deploymentlab/trunk/1.0.x/%MODULE%
set DEV=https://test.kuali.org/svn/student/%MODULE%/%QUALIFIER%/ks-%MODULE%-%VERSION%
svn delete -m "Removing %MODULE%" %QA%
svn copy -m "Copying %MODULE%/%QUALIFIER%/%MODULE%-%VERSION% from development svn" --username jcaddel --password gw570229 %DEV% %QA%
