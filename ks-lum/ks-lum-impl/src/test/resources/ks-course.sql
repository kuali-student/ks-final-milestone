--
-- Copyright 2010 The Kuali Foundation Licensed under the
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

// Course
INSERT INTO KSLU_LUTYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.lu.type.activity.WebDiscussion', 'Instructor discussion of course materials via the web', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Discussion')
//INSERT INTO KSLR_RESCOMP_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.resultComponentType.credit.degree.multiple', 'This records multiple number of credits that are awarded if the student passes the course.', {ts '2000-01-01 00:00:00.0'}, {ts '2020-01-01 00:00:00.0'}, 'Multiple Number');

// LoCategory
INSERT INTO KSLO_LO_CATEGORY (ID, NAME, RT_DESCR_ID, LO_REPO_ID, LO_CATEGORY_TYPE_ID, STATE, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('category-1', 'Perception', 'RICHTEXT-14', 'kuali.loRepository.key.singleUse', 'loCategoryType.skillarea', 'active', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
INSERT INTO KSLO_LO_CATEGORY (ID, NAME, RT_DESCR_ID, LO_REPO_ID, LO_CATEGORY_TYPE_ID, STATE, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('category-2', 'Perception', 'RICHTEXT-14', 'kuali.loRepository.key.singleUse', 'loCategoryType.skillarea', 'active', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
INSERT INTO KSLO_LO_CATEGORY (ID, NAME, RT_DESCR_ID, LO_REPO_ID, LO_CATEGORY_TYPE_ID, STATE, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('category-3', 'Perception', 'RICHTEXT-14', 'kuali.loRepository.key.singleUse', 'loCategoryType.skillarea', 'active', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)


// Course Statement
INSERT INTO KSLU_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-COURSE-1', '<p>Course Statement</p>', 'Course Statement')
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIV, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN) VALUES ('IDENT-COURSE-1', 'COURSE-1', 'COURSE', '1', 'Course  Statement 1', 'COURSE  Statement1', 'State-1', 'Type-1', 'Variation-1')
INSERT INTO KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID) values ('COURSE-STMT-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'RICHTEXT-COURSE-1', 'FEE-1', 'kuali.lu.type.CreditCourse', 'IDENT-COURSE-1', 'INSTR-1')

INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('clu.course', 'Course  Statement', 'CLU Course  Statement', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL (ID, EFF_DT, EXPIR_DT, REF_OBJ_TYPE_KEY, REF_OBJ_ID, REF_STMT_REL_TYPE_ID, STMT_ID, ST, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND) VALUES ('COURSE-ref-stmt-rel-1', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'clu', 'COURSE-STMT-1', 'clu.course', 'STMT-TV-1', 'ACTIVE', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1)
