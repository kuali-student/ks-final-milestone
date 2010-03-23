set QA=https://test.kuali.org/svn/student/deploymentlab/branches/1.0.x/kul-cfg-dbs
set DEV=https://test.kuali.org/svn/student/kul-cfg-dbs
call removeThenCopy %QA% %DEV% ks-core-db
call removeThenCopy %QA% %DEV% ks-embedded-db
call removeThenCopy %QA% %DEV% ks-lum-db
call removeThenCopy %QA% %DEV% ks-rice-db
