set QA=https://test.kuali.org/svn/student/deploymentlab/trunk/1.0.x/ks-cfg-dbs
set DEV=https://test.kuali.org/svn/student/ks-cfg-dbs
call removeThenCopyDb %QA% %DEV% ks-core-db
call removeThenCopyDb %QA% %DEV% ks-embedded-db
call removeThenCopyDb %QA% %DEV% ks-lum-db
call removeThenCopyDb %QA% %DEV% ks-rice-db
