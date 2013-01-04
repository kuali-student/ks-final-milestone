insert into KSLU_RICH_TEXT_T (ID, FORMATTED, PLAIN, OBJ_ID, VER_NBR) values ('MAJOR-103', 'Bachelor of Music', 'Bachelor of Music', SYS_GUID(), '0')
/

insert into KSLU_RSLT_OPT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, RES_COMP_ID, ST, RT_DESCR_ID, OBJ_ID) values ('59285620-00b3-4569-8732-804e55e84106', 'admin', to_date('2010-11-24 12:56:26','yyyy-mm-dd hh:mi:ss'), 'admin', to_date('2010-11-24 12:56:26','yyyy-mm-dd hh:mi:ss'), '0', 'kuali.resultComponent.degree.ba', 'active', 'MAJOR-101', SYS_GUID())
/

insert into KSLU_RSLT_OPT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, RES_COMP_ID, ST, RT_DESCR_ID, OBJ_ID) values ('930dae17-c35a-4a28-85e3-a962c963b421', 'admin', to_date('2010-11-24 12:56:26','yyyy-mm-dd hh:mi:ss'), 'admin', to_date('2010-11-24 12:56:26','yyyy-mm-dd hh:mi:ss'), '0', 'kuali.resultComponent.degree.bsc', 'active', 'MAJOR-102', SYS_GUID())
/

insert into KSLU_RSLT_OPT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, RES_COMP_ID, ST, RT_DESCR_ID, OBJ_ID) values ('fdc4eec6-4191-41c7-866e-d18a5f5b5ba2', 'admin', to_date('2010-11-24 12:56:26','yyyy-mm-dd hh:mi:ss'), 'admin', to_date('2010-11-24 12:56:26','yyyy-mm-dd hh:mi:ss'), '0', 'kuali.resultComponent.degree.bmus', 'active', 'MAJOR-103', SYS_GUID())
/

insert into KSLU_CLU_RSLT (ID, CREATEID, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, ST, CLU_ID, TYPE_KEY_ID, OBJ_ID) values ('6199a404-7b3c-4dcc-ba4c-2558f8f3ed25', 'admin', 'admin', to_date('2010-11-24 12:56:26','yyyy-mm-dd hh:mi:ss'), '0', to_date('2010-11-24 12:56:26','yyyy-mm-dd hh:mi:ss'), 'Active', 'd02dbbd3-20e2-410d-ab52-1bd6d362748b', 'kuali.resultType.degree', SYS_GUID())
/

insert into KSLU_CLURES_JN_RESOPT (CLU_RES_ID, RES_OPT_ID) values ('6199a404-7b3c-4dcc-ba4c-2558f8f3ed25', '59285620-00b3-4569-8732-804e55e84106')
/

insert into KSLU_CLURES_JN_RESOPT (CLU_RES_ID, RES_OPT_ID) values ('6199a404-7b3c-4dcc-ba4c-2558f8f3ed25', '930dae17-c35a-4a28-85e3-a962c963b421')
/

insert into KSLU_CLURES_JN_RESOPT (CLU_RES_ID, RES_OPT_ID) values ('6199a404-7b3c-4dcc-ba4c-2558f8f3ed25', 'fdc4eec6-4191-41c7-866e-d18a5f5b5ba2')
/

insert into KSLU_CLU_ADMIN_ORG (ID, IS_PR, ORG_ID, TYPE, CLU_ID, OBJ_ID, VER_NBR) values (SYS_GUID(), '1', '182', 'kuali.adminOrg.type.CurriculumOversightUnit', 'd02dbbd3-20e2-410d-ab52-1bd6d362748b', SYS_GUID(), '0')
/

insert into KSLU_CLU_ADMIN_ORG (ID, IS_PR, ORG_ID, TYPE, CLU_ID, OBJ_ID, VER_NBR) values (SYS_GUID(), '1', '142', 'kuali.adminOrg.type.CurriculumOversightDivision', 'd02dbbd3-20e2-410d-ab52-1bd6d362748b', SYS_GUID(), '0')
/

update KSLU_CLU_ADMIN_ORG set ORG_ID='4' where ID='Maryland'
/

