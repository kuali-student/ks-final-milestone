@echo off
set SRC_URL=https://test.kuali.org/svn/student/trunk/ks-cfg-dbs
set DST_URL=https://test.kuali.org/svn/student/deploymentlab/branches/ks-1.1-db/ks-cfg-dbs

echo KS Core
call update-one-impex-xml %SRC_URL% %DST_URL% ks-core-db

echo KS LUM
call update-one-impex-xml %SRC_URL% %DST_URL% ks-lum-db

echo KS Rice
call update-one-impex-xml %SRC_URL% %DST_URL% ks-rice-db

echo KS Rice Client
call update-one-impex-xml %SRC_URL% %DST_URL% ks-rice-client-db

echo KS Rice Server
call update-one-impex-xml %SRC_URL% %DST_URL% ks-rice-server-db

echo KS Embedded
call update-one-impex-xml %SRC_URL% %DST_URL% ks-embedded-db
