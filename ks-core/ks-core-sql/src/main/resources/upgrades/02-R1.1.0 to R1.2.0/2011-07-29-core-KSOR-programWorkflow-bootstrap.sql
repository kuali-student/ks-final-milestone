--Update orgs and relations

insert into KSOR_ORG_PERS_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR) values ('kuali.org.PersonRelation.CurriculumManager', 'Curriculum Manager', TIMESTAMP '2000-01-01 00:00:00', null, 'Curriculum Manager', '3de6e68c-24bd-42c8-bb40-4a16673cef44', 0)
/
insert into KSOR_ORG_POS_RESTR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, DESCR, MAX_NUM_RELTN, MIN_NUM_RELTN, ATP_DUR_TYP_KEY, TM_QUANTITY, TTL, ORG, PERS_RELTN_TYPE, OBJ_ID) values ('cc473647-536d-4688-a33f-e7b00c118cd2', 'TESTUSER', TIMESTAMP '2010-12-13 12:26:11', 'TESTUSER', TIMESTAMP '2010-12-13 12:26:11', 0, 'Curriculum Manager', '100', 1, null, null, 'Curriculum Manager', '141', 'kuali.org.PersonRelation.CurriculumManager', '0fa77c54-b315-4b4a-8d98-dbe50504dd41')
/
insert into KSOR_ORG_PERS_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, EFF_DT, EXPIR_DT, PERS_ID, ST, ORG, ORG_PERS_RELTN_TYPE, OBJ_ID) values ('10817a3c-ae5a-45ee-8dbc-aaa786029e2e', null, null, 'admin', TIMESTAMP '2010-12-14 11:12:26', 2, TIMESTAMP '1999-12-31 03:00:00', null, 'dev2', null, '141', 'kuali.org.PersonRelation.CurriculumManager', '61057ffa-eaf7-459a-989c-543ae517b833')
/
