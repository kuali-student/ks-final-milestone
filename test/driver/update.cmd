set QA=https://test.kuali.org/svn/student/deploymentlab/branches/1.0.0.x/
set DEV=https://test.kuali.org/svn/student/
svn delete -m "Removing" %QA%/security/ks-standard-sec/src 
svn copy -m "Copying" --username jcaddel --password gw570229 %DEV%/ks-security/branches/ks-security-dev/ks-standard-sec/src %QA%/security/ks-standard-sec/src

svn delete -m "Removing" %QA%/security/ks-standard-sec/src 
svn copy -m "Copying" --username jcaddel --password gw570229 %DEV%/ks-security/branches/ks-security-dev/ks-standard-sec/src %QA%/security/ks-standard-sec/src
