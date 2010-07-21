svn copy -m "Migrating Security" https://test.kuali.org/svn/student/ks-security/branches/ks-security-dev https://test.kuali.org/svn/student/trunk/ks-security
svn copy -m "Migrating Core" https://test.kuali.org/svn/student/ks-core/branches/ks-core-dev https://test.kuali.org/svn/student/trunk/ks-core
svn copy -m "Migrating BRMS" https://test.kuali.org/svn/student/brms/branches/brms-dev https://test.kuali.org/svn/student/trunk/brms
svn copy -m "Migrating LUM" https://test.kuali.org/svn/student/ks-lum/branches/ks-lum-dev https://test.kuali.org/svn/student/trunk/ks-lum
svn copy -m "Migrating Web" https://test.kuali.org/svn/student/ks-web/branches/ks-web-dev https://test.kuali.org/svn/student/trunk/ks-web

svn mkdir -m "Creating kul-cfg-dbs folder" https://test.kuali.org/svn/student/trunk/ks-cfg-dbs 

svn copy -m "Migrating ks-core-db" https://test.kuali.org/svn/student/ks-cfg-dbs/ks-core-db/trunk https://test.kuali.org/svn/student/trunk/ks-cfg-dbs/ks-core-db
svn copy -m "Migrating ks-lum-db" https://test.kuali.org/svn/student/ks-cfg-dbs/ks-lum-db/trunk https://test.kuali.org/svn/student/trunk/ks-cfg-dbs/ks-lum-db
svn copy -m "Migrating ks-rice-db" https://test.kuali.org/svn/student/ks-cfg-dbs/ks-rice-db/trunk https://test.kuali.org/svn/student/trunk/ks-cfg-dbs/ks-rice-db
svn copy -m "Migrating ks-rice-client-db" https://test.kuali.org/svn/student/ks-cfg-dbs/ks-rice-client-db/trunk https://test.kuali.org/svn/student/trunk/ks-cfg-dbs/ks-rice-client-db
svn copy -m "Migrating ks-rice-server-db" https://test.kuali.org/svn/student/ks-cfg-dbs/ks-rice-server-db/trunk https://test.kuali.org/svn/student/trunk/ks-cfg-dbs/ks-rice-server-db
