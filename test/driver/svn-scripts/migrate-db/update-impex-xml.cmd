set SRC_URL=https://test.kuali.org/svn/student/trunk/ks-cfg-dbs
set DST_URL=https://test.kuali.org/svn/student/deploymentlab/branches/ks-1.1-db/ks-cfg-dbs

call update-one-impex-xml %SRC_URL% %DST_URL% ks-core-db
call update-one-impex-xml %SRC_URL% %DST_URL% ks-lum-db
call update-one-impex-xml %SRC_URL% %DST_URL% ks-rice-db
call update-one-impex-xml %SRC_URL% %DST_URL% ks-rice-client-db
call update-one-impex-xml %SRC_URL% %DST_URL% ks-rice-server-db
call update-one-impex-xml %SRC_URL% %DST_URL% ks-embedded-db
