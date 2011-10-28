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

//Clu stuff

// Lu Type
INSERT INTO KSLU_LUTYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME,VER_NBR) VALUES ('kuali.lu.type.CreditCourse', 'An course offered for academic credit', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Credit Course',0)

// CluInstructor
INSERT INTO KSLU_CLU_INSTR (ID, ORG_ID, PERS_ID,VER_NBR) VALUES ('INSTR-1', 'ORG-1', 'PersonID',0)
INSERT INTO KSLU_CLU_INSTR (ID, ORG_ID, PERS_ID,VER_NBR) VALUES ('INSTR-2', 'ORG-2', 'PersonID',0)

// RichText
INSERT INTO KSLU_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-101', '<p>Desc</p>', 'Desc',0)
INSERT INTO KSLU_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-201', '<p>Marketing Desc</p>', 'Marketing Desc',0)

// CluIdentifier
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIVISION, LVL, SUFX_CD, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN,VER_NBR) VALUES ('IDENT-1', 'CHEM123', 'CHEM', '100', '123', 'Chemistry 123', 'Chem 123', null, null, null,0)
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIVISION, LVL, SUFX_CD, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN,VER_NBR) VALUES ('IDENT-2', 'CHEM456', 'CHEM', '400', '456', 'Chemistry 456', 'Chem 456', null, null, null,0)

// CluCredit
INSERT INTO KSLU_CLU_CR (ID, INSTR_UNIT, MAX_ALOW_INACV_ATP, MAX_ALOW_INACV_TMQ, MAX_TM_RSLT_RCGZ_ATP, MAX_TM_RSLT_RCGZ_TMQ, MAX_TM_TO_COMP_ATP, MAX_TM_TO_COMP_TMQ, MAX_TOT_UNIT, MIN_TM_TO_COMP_ATP, MIN_TM_TO_COMP_TMQ, MIN_TOT_UNIT, REPEAT_CNT, REPEAT_TM_ATP, REPEAT_TM_TMQ, REPEAT_UNIT,VER_NBR) VALUES ('CR-1', 0, 'ATP-INACT-1', 0, 'ATP-RECOG-1', 0, 'ATP-MAXCOMPLETE-1', 0, 0, 'ATP-MINCOMPLETE-1', 0, 0, 'Repeat Count', 'ATP-REPEAT-1', 0, 'Repeat Units',0)
INSERT INTO KSLU_CLU_CR (ID, INSTR_UNIT, MAX_ALOW_INACV_ATP, MAX_ALOW_INACV_TMQ, MAX_TM_RSLT_RCGZ_ATP, MAX_TM_RSLT_RCGZ_TMQ, MAX_TM_TO_COMP_ATP, MAX_TM_TO_COMP_TMQ, MAX_TOT_UNIT, MIN_TM_TO_COMP_ATP, MIN_TM_TO_COMP_TMQ, MIN_TOT_UNIT, REPEAT_CNT, REPEAT_TM_ATP, REPEAT_TM_TMQ, REPEAT_UNIT,VER_NBR) VALUES ('CR-2', 0, 'ATP-INACT-2', 0, 'ATP-RECOG-2', 0, 'ATP-MAXCOMPLETE-2', 0, 0, 'ATP-MINCOMPLETE-2', 0, 0, 'Repeat Count', 'ATP-REPEAT-2', 0, 'Repeat Units',0)

// CluAccounting
INSERT INTO KSLU_CLU_ACCT (ID,VER_NBR) VALUES ('ACCT-1',0)
INSERT INTO KSLU_CLU_ACCT (ID,VER_NBR) VALUES ('ACCT-2',0)

// CluFeeRecord
INSERT INTO KSLU_CLU_FEE_REC (ID, FEE_TYPE, VER_NBR) values ('FEE-REC-1','DOLLAR', 0)
INSERT INTO KSLU_CLU_FEE_REC (ID, FEE_TYPE, VER_NBR) values ('FEE-REC-2','DOLLAR', 0)
INSERT INTO KSLU_CLU_FEE_REC (ID, FEE_TYPE, VER_NBR) values ('FEE-REC-3','DOLLAR', 0)

// AffiliatedOrgs
INSERT INTO KSLU_CLU_AFFIL_ORG (ID, ORG_ID, PERCT, EFF_DT, EXPIR_DT,VER_NBR) VALUES ('AFF-ORG-1', 'ORG-1', 30, {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},0)
INSERT INTO KSLU_CLU_AFFIL_ORG (ID, ORG_ID, PERCT, EFF_DT, EXPIR_DT,VER_NBR) VALUES ('AFF-ORG-2', 'ORG-1', 70, {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},0)
INSERT INTO KSLU_CLU_AFFIL_ORG (ID, ORG_ID, PERCT, EFF_DT, EXPIR_DT,VER_NBR) VALUES ('AFF-ORG-3', 'ORG-2', 40, {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},0)
INSERT INTO KSLU_CLU_AFFIL_ORG (ID, ORG_ID, PERCT, EFF_DT, EXPIR_DT,VER_NBR) VALUES ('AFF-ORG-4', 'ORG-2', 60, {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},0)

// CluFee
INSERT INTO KSLU_CLU_FEE (ID, VER_NBR) VALUES ('FEE-1', 0)
INSERT INTO KSLU_CLU_FEE (ID, VER_NBR) VALUES ('FEE-2', 0)

// CluFeeJnCluFeeRec
INSERT INTO KSLU_CLU_FEE_JN_CLU_FEE_REC (CLU_FEE_ID, CLU_FEE_REC_ID) values ('FEE-1','FEE-REC-1')
INSERT INTO KSLU_CLU_FEE_JN_CLU_FEE_REC (CLU_FEE_ID, CLU_FEE_REC_ID) values ('FEE-1','FEE-REC-2')
INSERT INTO KSLU_CLU_FEE_JN_CLU_FEE_REC (CLU_FEE_ID, CLU_FEE_REC_ID) values ('FEE-2','FEE-REC-3')

// Clu
insert into KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID,VER_IND_ID,CURR_VER_START)  values ('CLU-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'RICHTEXT-101', 'FEE-1', 'kuali.lu.type.CreditCourse', 'IDENT-1', 'INSTR-1','CLU-1', {ts '2003-01-01 00:00:00.0'})
insert into KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID,VER_IND_ID,CURR_VER_START)  values ('CLU-3', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE2', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-2', 'RICHTEXT-201', 'FEE-2', 'kuali.lu.type.CreditCourse', 'IDENT-2', 'INSTR-2','CLU-3', {ts '2003-01-01 00:00:00.0'})
insert into KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID,VER_IND_ID,CURR_VER_START)  values ('CLU-4', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'RICHTEXT-101', 'FEE-1', 'kuali.lu.type.CreditCourse','IDENT-1', 'INSTR-1','CLU-4', {ts '2003-01-01 00:00:00.0'})
insert into KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID,VER_IND_ID,CURR_VER_START)  values ('CLU-5', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'RICHTEXT-101', 'FEE-1', 'kuali.lu.type.CreditCourse','IDENT-1', 'INSTR-1','CLU-5', {ts '2003-01-01 00:00:00.0'})

//LRR types and states
INSERT INTO KSEN_LRR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lrr.type.credential.awarded ', 'Credential Awarded', 'Indicates the credential that was awarded', 0)
INSERT INTO KSEN_LRR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lrr.type.credits.earned', 'Credits Earned', 'Indicates the number of credits the student earned for successfully completing the course.  Often done via a calculation by looking at the calculated grade and either awarding the number of attempted credits if it is a passing grade.', 0)
INSERT INTO KSEN_LRR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lrr.type.final.grade.administrative', 'Administrative Final Grade', 'Administrative Grade that was recorded for this student', 0)
INSERT INTO KSEN_LRR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lrr.type.final.grade.assigned', 'Assigned Final Grade', 'Grade as it was originally submitted by the faculty member on the grade sheet.', 0)
INSERT INTO KSEN_LRR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lrr.type.final.grade.calculated', 'Calculated Final Grade', 'Grade calculated based on the scale or option chosen and the grade actually submitted by the faculty member.', 0)
INSERT INTO KSEN_LRR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lrr.type.interim.grade', 'Interim Grade', 'Grade assigned during at an interim period often used to flag students who might be struggling', 0)
INSERT INTO KSEN_LRR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lrr.type.standardized.test.ranking', 'Standardized Test Ranking', 'The ranking of the student within the peer group who took the test during the same time period.  Often recorded as a percentage or a decile', 0)
INSERT INTO KSEN_LRR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lrr.type.standardized.test.score', 'Standardized Test Score', 'A score a student got by taking standardized test.', 0)

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lrr.state.saved', 'Saved', 'kuali.lrr.course.final.grading.lifecycle', 'The result (grade) has been entered and has been saved but not yet been submitted', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lrr.state.submitted', 'Submitted', 'kuali.lrr.course.final.grading.lifecycle', 'The result (grade) has been formally submitted', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lrr.state.accepted', 'Accepted', 'kuali.lrr.course.final.grading.lifecycle', 'The result (grade) has been vetted and is ready to be published to the student', 0)

//Rich text for LRRs
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('grade-final-lecture-desc', '<p>Final Grade for Lecture</p>', 'Final Grade for Lecture', 0)
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('grade-interim-lecture-desc', '<p>Interim Grade for Lecture</p>', 'Interim Grade for Lecture', 0)

INSERT INTO KSEN_LUI_RICH_TEXT (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('CO-1-Desc', '<p>CO Desc 101</p>', 'CO Desc 101',0)
INSERT INTO KSEN_LUI_RICH_TEXT (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('CO-2-Desc', '<p>CO Desc 201</p>', 'CO Desc 201',0)
INSERT INTO KSEN_LUI_RICH_TEXT (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('CO-3-Desc', '<p>CO Desc 301</p>', 'CO Desc 301',0)

//Course offerings for elegibility checking
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, TYPE_ID, STATE_ID, RT_DESCR_ID, EFF_DT, EXP_DT, VER_NBR, ATP_ID) VALUES ('courseOffering1', 'Lui one',  'CLU-1', 'kuali.lui.type.course.offering', 'kuali.lui.state.draft', 'CO-1-Desc', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0,'testAtpId1')
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, TYPE_ID, STATE_ID, RT_DESCR_ID, EFF_DT, EXP_DT, VER_NBR, ATP_ID) VALUES ('courseOffering2', 'Lui two',  'CLU-4', 'kuali.lui.type.course.offering', 'kuali.lui.state.draft', 'CO-2-Desc', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0,'testAtpId1')
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, TYPE_ID, STATE_ID, RT_DESCR_ID, EFF_DT, EXP_DT, VER_NBR, ATP_ID) VALUES ('courseOffering3', 'Lui three', 'CLU-3', 'kuali.lui.type.course.offering', 'kuali.lui.state.draft', 'CO-3-Desc', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 0,'testAtpId1')

// LuiPersonRelationEntity
INSERT INTO KSEN_LPR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, PERSONID, LUIID, COMMITMENTPERCENT, EFFECTIVEDATE, EXPIRATIONDATE, RELATION_TYPE_ID, RELATION_STATE_ID, VER_NBR) VALUES ('testStudent1Co1', 'admin', {ts '1900-01-01 00:00:00.0'}, 'admin', {ts '2000-01-01 00:00:00.0'}, 'testStudentId1', 'courseOffering1', .5, {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'kuali.lpr.type.registrant', 'kuali.lpr.state.registered', 0)
INSERT INTO KSEN_LPR (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, PERSONID, LUIID, COMMITMENTPERCENT, EFFECTIVEDATE, EXPIRATIONDATE, RELATION_TYPE_ID, RELATION_STATE_ID, VER_NBR) VALUES ('testStudent2Co1', 'admin', {ts '1900-01-01 00:00:00.0'}, 'admin', {ts '2000-01-01 00:00:00.0'}, 'testStudentId2', 'courseOffering2', .5, {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'kuali.lpr.type.registrant', 'kuali.lpr.state.registered', 0)

//Learning Result Records
INSERT INTO KSEN_LRR(ID, LPR_ID, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, RESULT_VALUE_ID, VER_NBR) VALUES('student1-grade-final-lecture', 'testStudent1Co1', 'student1 grade final lecture', 'grade-final-lecture-desc', 'kuali.lrr.state.saved', 'kuali.lrr.type.final.grade.assigned', 'student1-grade-value-final-lecture', 0)
INSERT INTO KSEN_LRR(ID, LPR_ID, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, RESULT_VALUE_ID, VER_NBR) VALUES('student1-grade-interim-lecture', 'testStudent1Co1', 'student1 grade interim lecture', 'grade-interim-lecture-desc', 'kuali.lrr.state.saved', 'kuali.lrr.type.interim.grade', 'student1-grade-value-interim-lecture', 0)
--INSERT INTO KSEN_LRR(ID, LPR_ID, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, RESULT_VALUE_ID, VER_NBR) VALUES('student2-grade-final-lecture', 'testStudent2Co1', 'student2 grade final lecture', 'grade-final-lecture-desc', 'kuali.lrr.state.saved', 'kuali.lrr.type.final.grade.assigned', 'student2-grade-value-final-lecture', 0)
--INSERT INTO KSEN_LRR(ID, LPR_ID, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, RESULT_VALUE_ID, VER_NBR) VALUES('student2-grade-interim-lecture', 'testStudent2Co1', 'student2 grade interim lecture', 'grade-interim-lecture-desc', 'kuali.lrr.state.saved', 'kuali.lrr.type.interim.grade', 'student2-grade-value-interim-lecture', 0)

// One statement tree for CLU-3, with a single requirement: Must have completed course CLU-1
INSERT INTO KSST_RICH_TEXT_T (ID, OBJ_ID, VER_NBR) VALUES('stmt-1-desc', 'stmt-richtext-obj-1', 0)
INSERT INTO KSST_RICH_TEXT_T (ID, OBJ_ID, VER_NBR) VALUES('reqCom-1-desc', 'stmt-richtext-obj-2', 0)

INSERT INTO KSST_STMT(CREATEID, CREATETIME, ID, OBJ_ID, OPERATOR, RT_DESCR_ID, ST, STMT_TYPE_ID, UPDATEID, UPDATETIME, VER_NBR) VALUES ('admin',{ts '1900-01-01 00:00:00.0'},'stmt-1','stmt-obj-1','AND','stmt-1-desc','Active','kuali.statement.type.course.academicReadiness.studentEligibilityPrereq','admin',{ts '1900-01-01 00:00:00.0'}, 0)

INSERT INTO KSST_REQ_COM(ID, CREATEID, CREATETIME, OBJ_ID, REQ_COM_TYPE_ID, RT_DESCR_ID, ST, UPDATEID, UPDATETIME, VER_NBR) VALUES('reqCom-1','admin',{ts '1900-01-01 00:00:00.0'},'reqCom-obj-1','kuali.reqComponent.type.course.completed','reqCom-1-desc', 'Active','admin',{ts '1900-01-01 00:00:00.0'},0)

INSERT INTO KSST_REQ_COM_FIELD (ID, OBJ_ID, REQ_COM_FIELD_TYPE, VALUE, VER_NBR) VALUES('reqComField-1', 'reqComField-obj-1', 'kuali.reqComponent.field.type.course.clu.id', 'CLU-1', 0)

INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES('reqCom-1','stmt-1')

INSERT INTO KSST_RC_JN_RC_FIELD(REQ_COM_FIELD_ID, REQ_COM_ID) VALUES('reqComField-1', 'reqCom-1')

INSERT INTO KSST_REF_STMT_REL(ID, CREATEID, CREATETIME, OBJ_ID, REF_OBJ_ID, REF_OBJ_TYPE_KEY, REF_STMT_REL_TYPE_ID, ST, STMT_ID, UPDATEID, UPDATETIME, VER_NBR) VALUES('refStmtRel-1','admin', {ts '1900-01-01 00:00:00.0'},'refStmtRel-obj-1','CLU-3','kuali.lu.type.CreditCourse','kuali.referenceType.CLU','Active','stmt-1','admin',{ts '2010-12-14 09:39:09.0'},0)


