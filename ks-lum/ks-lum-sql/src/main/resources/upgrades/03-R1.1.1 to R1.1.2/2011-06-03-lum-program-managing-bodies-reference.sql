--create new orgs wfor undergrad office and acatdemic affairs with correct types (dept and college)
insert into KSOR_ORG (ID, LNG_NAME, TYPE, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, LNG_DESCR, SHRT_DESCR, SHRT_NAME, ST, OBJ_ID) values ('222', 'Academic Affairs (College)', 'kuali.org.College', 'TESTUSER', TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ), 'admin', TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ), 2, TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ), null, null, null, 'Academics', null, '5E7E3AACF7474BA9BF8667B6364531A3')
/
insert into KSOR_ORG (ID, LNG_NAME, TYPE, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, LNG_DESCR, SHRT_DESCR, SHRT_NAME, ST, OBJ_ID) values ('223', 'Office of Undergraduate Studies (Department)', 'kuali.org.Department', null, TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ), 'admin', TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ), 1, TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ), null, null, null, 'UndergraduateStudies', null, 'c1069a5a-613c-4f38-b9ba-c465cc02136b')
/
--update relationships so new orgs are in correct hierarchy
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values ('ba7b62b4-f93f-4555-84ae-f5e8308b087b', null, null, 'admin', TIMESTAMP '2010-12-13 12:10:36', 3, TIMESTAMP '2009-12-31 03:00:00', null, null, '15', '222', 'kuali.org.Contain', '98771af9-0071-4c7e-9b0d-7a6b2039f60d')
/
insert into KSOR_ORG_ORG_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, ST, ORG, RELATED_ORG, TYPE, OBJ_ID) values ('ba7b62b4-f93f-4555-84ae-f5e8308b087c', null, null, 'admin', TIMESTAMP '2010-12-13 12:10:36', 3, TIMESTAMP '2009-12-31 03:00:00', null, null, '222', '223', 'kuali.org.Contain', '98771af9-0071-4c7e-9b0d-7a6b2039f60e')
/
--update the oversight
update KSLU_CLU_ADMIN_ORG set org_id='222' where TYPE='kuali.adminOrg.type.CurriculumOversightDivision' and org_id IN('REFERENCEPROG-CORE','REFERENCEPROG-BACC')
/
update KSLU_CLU_ADMIN_ORG set org_id='222' where TYPE='kuali.adminOrg.type.StudentOversightDivision' and org_id IN('REFERENCEPROG-CORE','REFERENCEPROG-BACC')
/
update KSLU_CLU_ADMIN_ORG set org_id='223' where TYPE='kuali.adminOrg.type.CurriculumOversightUnit' and org_id IN('REFERENCEPROG-CORE','REFERENCEPROG-BACC')
/
update KSLU_CLU_ADMIN_ORG set org_id='223' where TYPE='kuali.adminOrg.type.StudentOversightUnit' and org_id IN('REFERENCEPROG-CORE','REFERENCEPROG-BACC')
/
