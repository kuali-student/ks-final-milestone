--
-- Copyright 2011 The Kuali Foundation Licensed under the
-- Educational Community License, Version 2.0 (the "License"); you may
-- not use this file except in compliance with the License. You may
-- obtain a copy of the License at
--
-- http://www.osedu.org/licenses/ECL-2.0
--
-- Unless required by applicable law or agreed to in writing,
-- software distributed under the License is distributed on an "AS IS"
-- BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
-- or implied. See the License for the specific language governing
-- permissions and limitations under the License.
--

//Term
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-101', '<p>Desc 101</p>', 'Desc 101',0)


//LuiInstructorEntity
INSERT INTO KSEN_LUI_INSTR (ID, ORG_ID, PERS_ID, PERS_OVRID, PERCT_EFFT, VER_NBR) VALUES ('LUI-INSTR-1', 'Org-1', 'Pers-1', 'Instr-1', 30.5, 0)

// LuiIdentifierEntity
INSERT INTO KSEN_LUI_IDENT (ID, LUI_CD, DIVISION, SUFX_CD, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN, VER_NBR) VALUES ('LUI-IDENT-1', 'CHEM123', 'CHEM', '123', 'Chemistry 123', 'Chem 123', null, null, null, 0)

//LuiEntity
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, TYPE_ID, STATE_ID, RT_DESCR_ID, EFF_DT, EXP_DT, OFFIC_LUI_ID, VER_NBR, ATP_ID) VALUES ('Lui-1', 'Lui one',  'cluId1', 'kuali.lui.type.course.offering', 'kuali.lui.state.draft', 'Lui-1-Desc', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 'LUI-IDENT-1', 0,'testTermId4')
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, TYPE_ID, STATE_ID, RT_DESCR_ID, EFF_DT, EXP_DT, VER_NBR, ATP_ID) VALUES ('Lui-2', 'Lui rwo',  'cluId2', 'kuali.lui.type.activity.offering.lecture', 'kuali.lui.state.draft', 'Lui-2-Desc', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0,'kuali.atp.type.Winter')
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, TYPE_ID, STATE_ID, RT_DESCR_ID, EFF_DT, EXP_DT, VER_NBR, ATP_ID) VALUES ('Lui-3', 'Lui three', 'cluId3', 'kuali.lui.type.course.offering', 'kuali.lui.state.draft', 'Lui-3-Desc', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0,'kuali.atp.type.Winter')
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, TYPE_ID, STATE_ID, RT_DESCR_ID, EFF_DT, EXP_DT, VER_NBR, ATP_ID) VALUES ('Lui-4', 'Lui four',  'cluId4', 'kuali.lui.type.activity.offering.lecture', 'kuali.lui.state.draft', 'Lui-4-Desc', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0,'kuali.atp.type.Winter')

//LuiLuiRelationEntity
INSERT INTO KSEN_LUILUI_RELTN(ID, VER_NBR, EFF_DT, EXP_DT, STATE_ID, LUI_ID, TYPE_ID, RELATED_LUI_ID) VALUES ('LUILUIREL-1', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.lui.lui.relation.state.active', 'Lui-1', 'kuali.lui.lui.relation.associated', 'Lui-2')
INSERT INTO KSEN_LUILUI_RELTN(ID, VER_NBR, EFF_DT, EXP_DT, STATE_ID, LUI_ID, TYPE_ID, RELATED_LUI_ID) VALUES ('LUILUIREL-2', 0, {ts '2011-01-01 00:00:00.0'}, {ts '2100-01-01 00:00:00.0'}, 'kuali.lui.lui.relation.state.active', 'Lui-3', 'kuali.lui.lui.relation.associated', 'Lui-4')


// LuiPersonRelationEntity
INSERT INTO KSEN_LPR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, PERSONID, LUIID, COMMITMENTPERCENT, EFFECTIVEDATE, EXPIRATIONDATE, RELATION_TYPE_ID, RELATION_STATE_ID, VER_NBR) VALUES ('testLprId1', 'admin', {ts '1900-01-01 00:00:00.0'}, 'admin', {ts '2000-01-01 00:00:00.0'}, 'testPersonId1', 'testLuiId1', .5, {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'kuali.lpr.type.registrant', 'kuali.lpr.state.registered', 0)

INSERT INTO KSEN_LPR_TRANS (ID, REQ_PERSON_ID, LPR_TYPE_ID, STATE_ID, CREATETIME, UPDATEID, UPDATETIME) VALUES ('testLprTransId1', 'admin-1', 'kuali.lpr.trans.type.register', 'kuali.lpr.trans.registered', {ts '1900-01-01 00:00:00.0'}, 'admin', {ts '2000-01-01 00:00:00.0'})

INSERT INTO KSEN_LPR_TRANS_ITEMS (ID, PERSON_ID, NEW_LUI_ID, EXIST_LUI_ID, TYPE_ID, STATE_ID, LPR_TRANS_ID) VALUES ('testLprTransItemId1', 'Student1','Lui-1',null, 'kuali.lpr.trans.item.type.add','kuali.lpr.trans.item.state.new', 'testLprTransId1')