
//RichTextEntity
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-101', '<p>Desc 101</p>', 'Desc 101',0)

//AtpEntity
INSERT INTO KSEN_ATP (ID, NAME, START_DT, END_DT, ATP_TYPE, ATP_STATE, DESCR_PLAIN, VER_NBR) VALUES ('testAtpId1', 'testAtp1', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'kuali.atp.type.Fall', 'kuali.atp.state.Draft', 'DESC-101', 0)


//LuiEntity
INSERT INTO KSEN_LUI (ID, NAME, ATP_ID, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_PLAIN, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('Lui-1', 'Lui one', 'testAtpId1','cluId1', 'kuali.lui.type.course.offering', 'kuali.lui.state.draft', 'Lui-1-Desc', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0)
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_PLAIN, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('Lui-2', 'Lui rwo', 'cluId2', 'kuali.lui.type.activity.offering.lecture', 'kuali.lui.state.draft', 'Lui-2-Desc', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0)
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_PLAIN, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('Lui-3', 'Lui three', 'cluId3', 'kuali.lui.type.course.offering', 'kuali.lui.state.draft', 'Lui-3-Desc', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0)
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, LUI_TYPE, LUI_STATE, DESCR_PLAIN, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('Lui-4', 'Lui four', 'cluId4', 'kuali.lui.type.activity.offering.lecture', 'kuali.lui.state.draft', 'Lui-4-Desc', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0)

//LuiLuiRelationEntity
INSERT INTO KSEN_LUILUI_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, LUILUI_RELTN_STATE, LUI_ID, LUILUI_RELTN_TYPE, RELATED_LUI_ID) VALUES ('LUILUIREL-1', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.lui.lui.relation.state.active', 'Lui-1', 'kuali.lui.lui.relation.associated', 'Lui-2')
INSERT INTO KSEN_LUILUI_RELTN(ID, VER_NBR, EFF_DT, EXPIR_DT, LUILUI_RELTN_STATE, LUI_ID, LUILUI_RELTN_TYPE, RELATED_LUI_ID) VALUES ('LUILUIREL-2', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.lui.lui.relation.state.active', 'Lui-3', 'kuali.lui.lui.relation.associated', 'Lui-4')
