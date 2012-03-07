-- Add required managing bodies to Core Program
insert into KSLU_CLU_ADMIN_ORG (ID, IS_PR, ORG_ID, TYPE, CLU_ID, OBJ_ID, VER_NBR) values (SYS_GUID(), '1', '182', 'kuali.adminOrg.type.CurriculumOversightUnit', '00f5f8c5-fff1-4c8b-92fc-789b891e0849', SYS_GUID(), '0')
/
insert into KSLU_CLU_ADMIN_ORG (ID, IS_PR, ORG_ID, TYPE, CLU_ID, OBJ_ID, VER_NBR) values (SYS_GUID(), '1', '142', 'kuali.adminOrg.type.CurriculumOversightDivision', '00f5f8c5-fff1-4c8b-92fc-789b891e0849', SYS_GUID(), '0')
/

-- Add required learning objective to Core Program
insert into KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, OBJ_ID, VER_NBR) values ('969f654a-16d0-4bf6-9255-dded24c6b8bc', 'Critical Thinking', 'Critical Thinking', SYS_GUID(), 0)
/
insert into KSLO_LO (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, NAME, ST, RT_DESCR_ID, LO_REPO_ID, LOTYPE_ID, OBJ_ID) values ('4beee444-ac19-4063-85e3-61df2662ceab', 'admin', to_date('2010-11-29 12:56:26', 'yyyy-mm-dd hh:mi:ss'), 'admin', to_date('2010-11-29 12:56:26', 'yyyy-mm-dd hh:mi:ss'), 1, to_date('2010-11-29 12:56:26', 'yyyy-mm-dd hh:mi:ss'), null, 'SINGLE USE LO', 'Active', '969f654a-16d0-4bf6-9255-dded24c6b8bc', 'kuali.loRepository.key.singleUse', 'kuali.lo.type.singleUse', SYS_GUID())
/
insert into KSLU_CLU_LO_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, LO_ID, ST, TYPE, CLU_ID, OBJ_ID) values (SYS_GUID(), 'admin', to_date('2010-11-29 12:56:26', 'yyyy-mm-dd hh:mi:ss'), 'admin', to_date('2010-11-29 12:56:26', 'yyyy-mm-dd hh:mi:ss'), 0, to_date('2010-11-29 12:56:26', 'yyyy-mm-dd hh:mi:ss'), null, '4beee444-ac19-4063-85e3-61df2662ceab', 'Active', 'kuali.lu.lo.relation.type.includes', '00f5f8c5-fff1-4c8b-92fc-789b891e0849', SYS_GUID())
/
insert into KSLO_LO_CATEGORY (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, NAME, STATE, RT_DESCR_ID, LO_CATEGORY_TYPE_ID, LO_REPO_ID, OBJ_ID) values ('b76e8153-f04b-4ce5-a560-a90d93aa07a8', 'admin', to_date('2010-11-29 12:56:26', 'yyyy-mm-dd hh:mi:ss'), 'admin', to_date('2010-11-29 12:56:26', 'yyyy-mm-dd hh:mi:ss'), 0, to_date('2010-11-29 12:56:26', 'yyyy-mm-dd hh:mi:ss'), null, 'Core Learning Objectives', 'active', null, 'loCategoryType.skillarea', 'kuali.loRepository.key.singleUse', SYS_GUID())
/
insert into KSLO_LO_JN_LOCATEGORY (ID, LO_ID, LOCATEGORY_ID, OBJ_ID, VER_NBR) values (SYS_GUID(), '4beee444-ac19-4063-85e3-61df2662ceab', 'b76e8153-f04b-4ce5-a560-a90d93aa07a8', SYS_GUID(), 0)
/

-- Fix Core Program's description
insert into KSLU_RICH_TEXT_T (ID, FORMATTED, PLAIN, OBJ_ID, VER_NBR) values ('459f2c80-5ed7-4383-a966-fcc8431a535e', 'Core Program', 'Core Program', SYS_GUID(), 0)
/
update KSLU_CLU set RT_DESCR_ID = '459f2c80-5ed7-4383-a966-fcc8431a535e' where ID = '00f5f8c5-fff1-4c8b-92fc-789b891e0849'
/

-- Fix the single Credential Program's description while we're at it
update KSLU_RICH_TEXT_T set FORMATTED = 'Baccalaureate', PLAIN = 'Baccalaureate' where ID='9623c41c-ae72-473a-a1ac-6874c433967d'
/

