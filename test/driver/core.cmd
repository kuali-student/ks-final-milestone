set QA=https://test.kuali.org/svn/student/deploymentlab/trunk/1.0.x/ks-core
set DEV=https://test.kuali.org/svn/student/ks-core/branches/ks-core-dev
call removeThenCopy %QA% %DEV% ks-common-impl
call removeThenCopy %QA% %DEV% ks-common-test
call removeThenCopy %QA% %DEV% ks-common-ui
call removeThenCopy %QA% %DEV% ks-common-util
call removeThenCopy %QA% %DEV% ks-core-api
call removeThenCopy %QA% %DEV% ks-core-impl
call removeThenCopy %QA% %DEV% ks-core-ui
