set QA=https://test.kuali.org/svn/student/deploymentlab/branches/1.0.0.x/brms
set DEV=https://test.kuali.org/svn/student/brms/branches/brms-1.0.0-m4
call removeThenCopy %QA% %DEV% ks-brms-api
call removeThenCopy %QA% %DEV% ks-brms-execution-api
call removeThenCopy %QA% %DEV% ks-brms-execution-impl
call removeThenCopy %QA% %DEV% ks-brms-impl
call removeThenCopy %QA% %DEV% ks-brms-ui
call removeThenCopy %QA% %DEV% ks-statement-api
call removeThenCopy %QA% %DEV% ks-statement-impl
