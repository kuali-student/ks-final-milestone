set MODULE=%1
set QUALIFIER=%2
set VERSION=%3
set CM=https://test.kuali.org/svn/student/deploymentlab/trunk/1.0.x/ks-cfg-dbs/%MODULE%/src/main/resources/impex
set DEV=https://test.kuali.org/svn/student/ks-cfg-dbs/%MODULE%/%QUALIFIER%
echo ----------------------------------------------
echo From: %DEV%
echo To  : %CM%
echo ----------------------------------------------
svn delete -m "Removing %MODULE%" %CM%
svn copy -m "Copying ks-cfg-dbs/%MODULE%/%QUALIFIER%/%MODULE%-%VERSION% from development svn" %DEV% %CM%
