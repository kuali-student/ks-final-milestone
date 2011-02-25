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
insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('kuali.lu.adminOrg', 'Organization Types for Clu Admin Org', '2001-02-01 00:00:00.0', null, 'Clu Admin Org Types', '77534766-F76F-D61F-1A1C-FC091CD6E1C8', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('b75a9fac-fa91-4cf1-93ee-890189a454f9', 'Curriculum Oversight', 'kuali.adminOrg.type.CurriculumOversight', '2001-02-01 00:00:00.0', null, 0, 'Curriculum Oversight', 'kuali.lu.adminOrg', 'C2EE8902-F36E-7DB6-95B1-31D9CE7DEC2C', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('a5d39acc-80d8-4cba-a8bb-13cce6561ab8', 'Administering Org', 'kuali.adminOrg.type.Administration', '2001-02-01 00:00:00.0', null, 0, 'Admistering Org', 'kuali.lu.adminOrg', 'B9635CBC-F773-C9BA-57B0-60277A9F945B', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('d88d37fa-cdb0-458b-9e1f-c51100d51172', 'Curriculum Oversight Division', 'kuali.adminOrg.type.CurriculumOversightDivision', '2001-02-01 00:00:00.0', null, 0, 'Curriculum Oversight Division', 'kuali.lu.adminOrg', 'D55C4CD4-13FA-4295-C573-CE110C0EF0A1', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4fb74c25-84e1-4df4-acd5-c9d9a8e393ee', 'Student Oversight Division', 'kuali.adminOrg.type.StudentOversightDivision', '2001-02-01 00:00:00.0', null, 0, 'Student Oversight Division', 'kuali.lu.adminOrg', '77E2A1C9-72D0-3B89-6FD7-1AD3F1A6C422', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3c934273-0fce-4090-bb37-8343882b9dc4', 'Deployment Division', 'kuali.adminOrg.type.DeploymentDivision', '2001-02-01 00:00:00.0', null, 0, 'Deployment Division', 'kuali.lu.adminOrg', '50EE3DA1-151C-4759-4068-D8E228757233', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('596d5399-4899-4519-bff2-9067f2e027d6', 'Financial Resources Division', 'kuali.adminOrg.type.FinancialResourcesDivision', '2001-02-01 00:00:00.0', null, 0, 'Financial Resources Division', 'kuali.lu.adminOrg', 'FF157841-B3E1-1C4C-B90D-4A751BE612A7', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('abb0a915-1400-4faf-8295-6baf28ff008d', 'Financial Control Division', 'kuali.adminOrg.type.FinancialControlDivision', '2001-02-01 00:00:00.0', null, 0, 'Financial Control Division', 'kuali.lu.adminOrg', '0E8CDEEA-D855-F46E-22F0-6FAB1849D94B', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4fa57988-a40c-45c6-8b21-3a419389cc9c', 'Curriculum Oversight Unit', 'kuali.adminOrg.type.CurriculumOversightUnit', '2001-02-01 00:00:00.0', null, 0, 'Curriculum Oversight Unit', 'kuali.lu.adminOrg', '3F440BA9-687A-F635-EC2C-74AB26722FF6', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('3322abe6-a514-46b9-a502-1799c9030906', 'Student Oversight Unit', 'kuali.adminOrg.type.StudentOversightUnit', '2001-02-01 00:00:00.0', null, 0, 'Student Oversight Unit', 'kuali.lu.adminOrg', 'F4BD1055-1FC8-CEE6-3EF3-F68DCA136C60', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('91c476ec-89f5-4386-a52f-ab69064d0caa', 'Deployment Unit', 'kuali.adminOrg.type.DeploymentUnit', '2001-02-01 00:00:00.0', null, 0, 'Deployment Unit', 'kuali.lu.adminOrg', '32E1BA4A-C282-48C6-7C09-53C2A28AAC4B', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('7db97425-d6bb-45b2-9873-1905d228f203', 'Financial Resources Unit', 'kuali.adminOrg.type.FinancialResourcesUnit', '2001-02-01 00:00:00.0', null, 0, 'Financial Resources Unit', 'kuali.lu.adminOrg', 'CE74C025-1B22-FDDE-3033-8FB07F30BDCF', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('4bd0e397-0764-4f7a-a010-d9191e7c7af9', 'Financial Control Unit', 'kuali.adminOrg.type.FinancialControlUnit', '2001-02-01 00:00:00.0', null, 0, 'Financial Control Unit', 'kuali.lu.adminOrg', '722C4F6E-03B7-DE9D-C11E-A8651C6C401B', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR) values ('67e99363-5be4-4996-aeef-aad3172c314f', 'Institution', 'kuali.adminOrg.type.Institution', '2001-02-01 00:00:00.0', null, 0, 'Institution', 'kuali.lu.adminOrg', '38FB30CE-E9C8-F362-F1CD-3C01AE0FCF67', 1)
/
insert into KSEM_CTX_T (ID, CTX_KEY, CTX_VAL, OBJ_ID, VER_NBR) values ('9', 'kuali.lu.adminOrg.courseProgramCurriculumOrg', 'default', '26CF642F33B4406AA80E1B2728F39810', 0)
/
insert into KSEM_CTX_T (ID, CTX_KEY, CTX_VAL, OBJ_ID, VER_NBR) values ('7', 'kuali.lu.adminOrg.courseCurriculumOrg', 'default', 'EDEF94613FC94E0997ABE89742135756', 0)
/
insert into KSEM_CTX_T (ID, CTX_KEY, CTX_VAL, OBJ_ID, VER_NBR) values ('8', 'kuali.lu.adminOrg.programCurriculumOrg', 'default', '44D6BD7C7149459CA8E87DE10BC65A5A', 0)
/
insert into KSEM_CTX_JN_ENUM_VAL_T (ENUM_VAL_ID, CTX_ID) values ('b75a9fac-fa91-4cf1-93ee-890189a454f9', '9')
/
insert into KSEM_CTX_JN_ENUM_VAL_T (ENUM_VAL_ID, CTX_ID) values ('4fa57988-a40c-45c6-8b21-3a419389cc9c', '9')
/
insert into KSEM_CTX_JN_ENUM_VAL_T (ENUM_VAL_ID, CTX_ID) values ('b75a9fac-fa91-4cf1-93ee-890189a454f9', '7')
/
insert into KSEM_CTX_JN_ENUM_VAL_T (ENUM_VAL_ID, CTX_ID) values ('4fa57988-a40c-45c6-8b21-3a419389cc9c', '8')
/
