echo ----------------------------------------------
echo %MODULE%
echo ----------------------------------------------
set MODULE=%1
set QUALIFIER=%2
set VERSION=%3
set CM=https://test.kuali.org/svn/student/deploymentlab/trunk/1.0.x/ks-cfg-dbs/%MODULE%
set DEV=https://test.kuali.org/svn/student/ks-cfg-dbs/%MODULE%/%QUALIFIER%
rem set DEV=https://test.kuali.org/svn/student/deploymentlab/%QUALIFIER%/ks-cfg-dbs-%VERSION%/%MODULE%
svn delete -m "Removing %MODULE%" %CM%
svn copy -m "Copying ks-cfg-dbs/%MODULE%/%QUALIFIER%/%MODULE%-%VERSION% from development svn" --username jcaddel --password gw570229 %DEV% %CM%
