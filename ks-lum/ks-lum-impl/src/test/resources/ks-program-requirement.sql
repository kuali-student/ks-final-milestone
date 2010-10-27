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

// Program Requirement
INSERT INTO KSLU_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-PROGREQ-1', '<p>Program Requirement</p>', 'Program Requirement',0)
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIV, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN,VER_NBR) VALUES ('IDENT-PROGREQ-1', 'PROGREQ-1', 'PROGREQ', '1', 'Program Requirement 1', 'PROGREQ 1', 'State-1', 'Type-1', 'Variation-1',0)
INSERT INTO KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID) values ('PROGREQ-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'RICHTEXT-PROGREQ-1', 'FEE-1', 'kuali.lu.type.Requirement', 'IDENT-PROGREQ-1', 'INSTR-1')

INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT,VER_NBR) VALUES ('clu.programrequirements', 'CLU Program Requirements', 'CLU Progreq', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'},0)
INSERT INTO KSST_REF_STMT_REL (ID, EFF_DT, EXPIR_DT, REF_OBJ_TYPE_KEY, REF_OBJ_ID, REF_STMT_REL_TYPE_ID, STMT_ID, ST, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR) VALUES ('PROGREQ-ref-stmt-rel-1', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'kuali.lu.type.Requirement', 'PROGREQ-1', 'clu.programrequirements', 'STMT-TV-1', 'ACTIVE', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1)

INSERT INTO KSLU_CLU_LO_RELTN (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, LO_ID, CLU_ID, EFF_DT, EXPIR_DT, ST, TYPE) VALUES ('PROGREQ-LO-1', 'CREATEID', {ts '2009-12-01 09:00:00.0'}, 'UPDATEID', {ts '2009-12-01 10:00:00.0'},1,'81abea67-3bcc-4088-8348-e265f3670145', 'PROGREQ-1', {ts '2003-01-01 00:00:00.0'}, null, 'active', 'kuali.lu.lo.relation.type.includes')
