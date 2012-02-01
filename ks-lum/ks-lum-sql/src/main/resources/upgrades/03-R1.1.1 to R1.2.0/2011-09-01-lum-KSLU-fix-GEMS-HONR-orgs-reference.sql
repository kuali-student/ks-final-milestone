--Changes
--1. GEMS 100
--
--  Remove existing curriculum org value
--  Add College of Behavioral and Social Science AND Office of Undergraduate Studies (Department) for curriculum org
--
--2. HONR 100
--
--  Remove existing curriculum org value
--  Add Office of Undergraduate Studies (Department) for curriculum org

UPDATE KSLU_CLU_ADMIN_ORG set ORG_ID='223' where CLU_ID IN('REFERENCECOURSEHONR100','REFERENCECOURSEGEMS100')
/
INSERT INTO KSLU_CLU_ADMIN_ORG (VER_NBR, CLU_ID, OBJ_ID, ID, IS_PR, TYPE, ORG_ID) VALUES (0, 'REFERENCECOURSEGEMS100', '3492763a-8f55-4c46-a20a-2fa195dec0f6', '804c7c0c-056d-4cac-b043-cd3501fc233b', 0, 'kuali.adminOrg.type.CurriculumOversight', '29')
/
