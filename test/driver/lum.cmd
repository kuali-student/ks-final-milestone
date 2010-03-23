set QA=https://test.kuali.org/svn/student/deploymentlab/trunk/1.0.x/ks-lum
set DEV=https://test.kuali.org/svn/student/ks-lum/branches/ks-lum-dev
call removeThenCopy %QA% %DEV% ks-lum-api
call removeThenCopy %QA% %DEV% ks-lum-impl
call removeThenCopy %QA% %DEV% ks-lum-rice
call removeThenCopy %QA% %DEV% ks-lum-ui
