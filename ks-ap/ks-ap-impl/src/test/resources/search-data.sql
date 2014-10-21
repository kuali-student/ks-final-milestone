
//RichTextEntity
INSERT INTO KSLU_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-101', '<p>Desc 101</p>', 'Desc 101',0)

//AtpEntity
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE, ATP_STATE, DESCR_PLAIN, VER_NBR, CREATEID, CREATETIME) VALUES ('testAtpId1', 'testAtp1', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'kuali.atp.type.Fall', 'kuali.atp.state.Draft', 'DESC-101', 0, 'batch', {ts '2011-01-01 00:00:00.0'})


//LuiEntity
INSERT INTO KSEN_LUI (ID, NAME, ATP_ID, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_PLAIN, EFF_DT, EXPIR_DT, VER_NBR, CREATEID, CREATETIME) VALUES ('Lui-1', 'Lui one', 'testAtpId1','cluId1', 'kuali.lui.type.course.offering', 'kuali.lui.course.offering.state.offered', 'Lui-1-Desc', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0, 'batch', {ts '2011-01-01 00:00:00.0'})
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_PLAIN, EFF_DT, EXPIR_DT, VER_NBR, CREATEID, CREATETIME) VALUES ('Lui-2', 'Lui two', 'cluId2', 'kuali.lui.type.activity.offering.lecture', 'kuali.lui.state.draft', 'Lui-2-Desc', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0, 'batch', {ts '2011-01-01 00:00:00.0'})
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_PLAIN, EFF_DT, EXPIR_DT, VER_NBR, CREATEID, CREATETIME) VALUES ('Lui-3', 'Lui three', 'cluId3', 'kuali.lui.type.course.offering', 'kuali.lui.state.draft', 'Lui-3-Desc', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0, 'batch', {ts '2011-01-01 00:00:00.0'})
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_PLAIN, EFF_DT, EXPIR_DT, VER_NBR, CREATEID, CREATETIME) VALUES ('Lui-4', 'Lui four', 'cluId4', 'kuali.lui.type.activity.offering.lecture', 'kuali.lui.state.draft', 'Lui-4-Desc', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0, 'batch', {ts '2011-01-01 00:00:00.0'})

//LuiLuiRelationEntity
INSERT INTO KSEN_LUILUI_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, LUILUI_RELTN_STATE, LUI_ID, LUILUI_RELTN_TYPE, RELATED_LUI_ID, CREATEID, CREATETIME) VALUES ('LUILUIREL-1', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.lui.lui.relation.state.active', 'Lui-1', 'kuali.lui.lui.relation.associated', 'Lui-2', 'batch', {ts '2011-01-01 00:00:00.0'})
INSERT INTO KSEN_LUILUI_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, LUILUI_RELTN_STATE, LUI_ID, LUILUI_RELTN_TYPE, RELATED_LUI_ID, CREATEID, CREATETIME) VALUES ('LUILUIREL-2', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.lui.lui.relation.state.active', 'Lui-3', 'kuali.lui.lui.relation.associated', 'Lui-4', 'batch', {ts '2011-01-01 00:00:00.0'})

INSERT INTO KSLU_LUTYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME,VER_NBR) VALUES ('kuali.lu.type.CreditCourse', 'An course offered for academic credit', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Credit Course',0)

INSERT INTO KSLU_CLU_IDENT (ID, CD, DIVISION, LVL, SUFX_CD, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN,VER_NBR) VALUES ('IDENT-1', 'CHEM123', 'CHEM', '100', '123', 'Chemistry 123', 'Chem 123', null, null, null,0)

// CluInstructor
INSERT INTO KSLU_CLU_INSTR (ID, ORG_ID, PERS_ID,VER_NBR) VALUES ('INSTR-1', 'ORG-1', 'PersonID',0)

// CluAccounting
INSERT INTO KSLU_CLU_ACCT (ID,VER_NBR) VALUES ('ACCT-1',0)

// CluFee
INSERT INTO KSLU_CLU_FEE (ID, VER_NBR) VALUES ('FEE-1', 0)

// Clu
insert into KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID,VER_IND_ID,CURR_VER_START)  values ('cluId1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'RICHTEXT-101', 'FEE-1', 'kuali.lu.type.CreditCourse', 'IDENT-1', 'INSTR-1','CLU-1', {ts '2003-01-01 00:00:00.0'})
