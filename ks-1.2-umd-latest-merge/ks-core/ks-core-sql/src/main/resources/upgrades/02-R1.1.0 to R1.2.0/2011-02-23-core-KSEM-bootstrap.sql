-- Update to States
UPDATE KSEM_ENUM_VAL_T SET ABBREV_VAL='Active', CD='Active',SORT_KEY='01' WHERE ID='45'
/
UPDATE KSEM_ENUM_VAL_T SET ABBREV_VAL='Approved', CD='Approved',SORT_KEY='02' WHERE ID='43'
/
UPDATE KSEM_ENUM_VAL_T SET ABBREV_VAL='Draft', CD='Draft',SORT_KEY='03' WHERE ID='39'
/
UPDATE KSEM_ENUM_VAL_T SET ABBREV_VAL='Inactive', CD='Inactive',SORT_KEY='04', VAL='Approved but is temporarily not eligible for offering'  WHERE ID='44'
/
UPDATE KSEM_ENUM_VAL_T SET ABBREV_VAL='Submitted', CD='Submitted',SORT_KEY='05' WHERE ID='38'
/
UPDATE KSEM_ENUM_VAL_T SET ABBREV_VAL='Superseded', CD='Superseded',SORT_KEY='06', VAL='Version has been superceded by newer version' WHERE ID='38'
/
UPDATE KSEM_ENUM_VAL_T SET ABBREV_VAL='Retired', CD='Retired',SORT_KEY='07' WHERE ID='46'
/
UPDATE KSEM_ENUM_VAL_T SET ABBREV_VAL='Withdrawn', CD='Withdrawn',SORT_KEY='08' WHERE ID='42'
/

-- Admin Org Type Enumeration
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('kuali.lu.adminOrg', 'Organization Types for Clu Admin Org', to_date('2000-01-01', 'yyyy-mm-dd'), null, 'Clu Admin Org Types', '77534766-F76F-D61F-1A1C-FC091CD6E1C8', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b75a9fac-fa91-4cf1-93ee-890189a454f9', 'Curriculum Oversight', 'kuali.adminOrg.type.CurriculumOversight', to_date('2000-01-01', 'yyyy-mm-dd'), null, 0, 'Curriculum Oversight', 'kuali.lu.adminOrg', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a5d39acc-80d8-4cba-a8bb-13cce6561ab8', 'Administering Org', 'kuali.adminOrg.type.Administration', to_date('2000-01-01', 'yyyy-mm-dd'), null, 0, 'Admistering Org', 'kuali.lu.adminOrg', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d88d37fa-cdb0-458b-9e1f-c51100d51172', 'Curriculum Oversight Division', 'kuali.adminOrg.type.CurriculumOversightDivision', to_date('2000-01-01', 'yyyy-mm-dd'), null, 0, 'Curriculum Oversight Division', 'kuali.lu.adminOrg', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4fb74c25-84e1-4df4-acd5-c9d9a8e393ee', 'Student Oversight Division', 'kuali.adminOrg.type.StudentOversightDivision', to_date('2000-01-01', 'yyyy-mm-dd'), null, 0, 'Student Oversight Division', 'kuali.lu.adminOrg', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3c934273-0fce-4090-bb37-8343882b9dc4', 'Deployment Division', 'kuali.adminOrg.type.DeploymentDivision', to_date('2000-01-01', 'yyyy-mm-dd'), null, 0, 'Deployment Division', 'kuali.lu.adminOrg', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('596d5399-4899-4519-bff2-9067f2e027d6', 'Financial Resources Division', 'kuali.adminOrg.type.FinancialResourcesDivision', to_date('2000-01-01', 'yyyy-mm-dd'), null, 0, 'Financial Resources Division', 'kuali.lu.adminOrg', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('abb0a915-1400-4faf-8295-6baf28ff008d', 'Financial Control Division', 'kuali.adminOrg.type.FinancialControlDivision', to_date('2000-01-01', 'yyyy-mm-dd'), null, 0, 'Financial Control Division', 'kuali.lu.adminOrg', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4fa57988-a40c-45c6-8b21-3a419389cc9c', 'Curriculum Oversight Unit', 'kuali.adminOrg.type.CurriculumOversightUnit', to_date('2000-01-01', 'yyyy-mm-dd'), null, 0, 'Curriculum Oversight Unit', 'kuali.lu.adminOrg', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3322abe6-a514-46b9-a502-1799c9030906', 'Student Oversight Unit', 'kuali.adminOrg.type.StudentOversightUnit', to_date('2000-01-01', 'yyyy-mm-dd'), null, 0, 'Student Oversight Unit', 'kuali.lu.adminOrg', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('91c476ec-89f5-4386-a52f-ab69064d0caa', 'Deployment Unit', 'kuali.adminOrg.type.DeploymentUnit', to_date('2000-01-01', 'yyyy-mm-dd'), null, 0, 'Deployment Unit', 'kuali.lu.adminOrg', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7db97425-d6bb-45b2-9873-1905d228f203', 'Financial Resources Unit', 'kuali.adminOrg.type.FinancialResourcesUnit', to_date('2000-01-01', 'yyyy-mm-dd'), null, 0, 'Financial Resources Unit', 'kuali.lu.adminOrg', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4bd0e397-0764-4f7a-a010-d9191e7c7af9', 'Financial Control Unit', 'kuali.adminOrg.type.FinancialControlUnit', to_date('2000-01-01', 'yyyy-mm-dd'), null, 0, 'Financial Control Unit', 'kuali.lu.adminOrg', SYS_GUID(), 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('67e99363-5be4-4996-aeef-aad3172c314f', 'Institution', 'kuali.adminOrg.type.Institution', to_date('2000-01-01', 'yyyy-mm-dd'), null, 0, 'Institution', 'kuali.lu.adminOrg', SYS_GUID(), 1)
/
insert into KSEM_CTX_T (ID, CTX_KEY, CTX_VAL, OBJ_ID, VER_NBR) values ('9', 'kuali.lu.adminOrg.courseProgramCurriculumOrg', 'default', SYS_GUID(), 0)
/
insert into KSEM_CTX_T (ID, CTX_KEY, CTX_VAL, OBJ_ID, VER_NBR) values ('7', 'kuali.lu.adminOrg.courseCurriculumOrg', 'default', SYS_GUID(), 0)
/
insert into KSEM_CTX_T (ID, CTX_KEY, CTX_VAL, OBJ_ID, VER_NBR) values ('8', 'kuali.lu.adminOrg.programCurriculumOrg', 'default', SYS_GUID(), 0)
/
insert into KSEM_CTX_JN_ENUM_VAL_T (ENUM_VAL_ID, CTX_ID) values ('b75a9fac-fa91-4cf1-93ee-890189a454f9', '9')
/
insert into KSEM_CTX_JN_ENUM_VAL_T (ENUM_VAL_ID, CTX_ID) values ('4fa57988-a40c-45c6-8b21-3a419389cc9c', '9')
/
insert into KSEM_CTX_JN_ENUM_VAL_T (ENUM_VAL_ID, CTX_ID) values ('b75a9fac-fa91-4cf1-93ee-890189a454f9', '7')
/
insert into KSEM_CTX_JN_ENUM_VAL_T (ENUM_VAL_ID, CTX_ID) values ('4fa57988-a40c-45c6-8b21-3a419389cc9c', '8')
/
