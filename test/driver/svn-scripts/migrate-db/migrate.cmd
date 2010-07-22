set BASE_URL=https://test.kuali.org/svn/student/deploymentlab/branches/ks-1.1-db
svn copy -m "Creating ks-1.1-db branch" https://test.kuali.org/svn/student/branches/ks-poc-1.1.x-br %BASE_URL%

set DB=ks-embedded-db
call migrate-one-db %BASE_URL% %DB%

pause

set DB=ks-core-db
call migrate-one-db %BASE_URL% %DB%

set DB=ks-lum-db
call migrate-one-db %BASE_URL% %DB%

set DB=ks-rice-client-db
call migrate-one-db %BASE_URL% %DB%

set DB=ks-rice-server-db
call migrate-one-db %BASE_URL% %DB%

set DB=ks-rice-db
call migrate-one-db %BASE_URL% %DB%
