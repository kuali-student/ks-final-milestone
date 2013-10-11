-- This file was created to split the statements in 2012-10-05-KSOR-delete-dupe-orgs-for-demo.sql into appropriate modules

--Update references to point to correct org
UPDATE KSLU_CLU_ADMIN_ORG set ORG_ID='1000CHEM' where org_ID='1000117'
/
UPDATE KSLU_CLU_ADMIN_ORG set ORG_ID='1000ENGL' where org_ID='1000136'
/
UPDATE KSLU_CLU_ADMIN_ORG set ORG_ID='1000BIOL' where org_ID='1000112'
/
UPDATE KSLU_CLU_ADMIN_ORG set ORG_ID='1000LAW' where org_ID='1000852'
/