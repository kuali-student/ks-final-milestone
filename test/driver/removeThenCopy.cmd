set MODULE=%1
set QUALIFIER=%2
set VERSION=%3
set QA=https://test.kuali.org/svn/student/deploymentlab/trunk/1.0.x/%MODULE%
set DEV=https://test.kuali.org/svn/student/%MODULE%/%QUALIFIER%/%MODULE%-%VERSION%
svn delete -m "Removing %MODULE%" %QA%
svn copy -m "Copying %MODULE%/%QUALIFIER%/%MODULE%-%VERSION% from development svn" --username jcaddel --password gw570229 %DEV% %QA%
