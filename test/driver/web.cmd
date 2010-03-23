set QA=https://test.kuali.org/svn/student/deploymentlab/branches/1.0.x/web
set DEV=https://test.kuali.org/svn/student/ks-web/branches/ks-web-dev
call removeThenCopy %QA% %DEV% ks-embedded
call removeThenCopy %QA% %DEV% ks-standalone
