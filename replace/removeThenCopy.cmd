set MODULE=%1
set QUALIFIER=%2
set VERSION=%3
set CM=https://test.kuali.org/svn/student/deploymentlab/trunk/1.0.x/%MODULE%
set DEV=https://test.kuali.org/svn/student/%MODULE%/%QUALIFIER%/%MODULE%-%VERSION%
echo ----------------------------------------------
echo From: %DEV%
echo To  : %CM%
echo ----------------------------------------------
svn delete -m "Removing %MODULE%" %CM%
svn copy -m "Copying %MODULE%/%QUALIFIER%/%MODULE%-%VERSION% from development svn" %DEV% %CM%
