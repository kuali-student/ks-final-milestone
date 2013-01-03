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

-- RichText
INSERT INTO KSDO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RT-DESC-DOCUMENT-1', '<p>Document 1</p>', ' 1');

INSERT INTO KSDO_DOCUMENT_CATEGORY(CATEGORY_ID,NAME,RT_DESCR_ID,EFF_DT,EXPIR_DT) VALUES ('CAT_1','CATEGORY 1','RT-DESC-DOCUMENT-1',{ts '2000-01-01 00:00:00.0'},  {ts '2000-12-31 00:00:00.0'});
INSERT INTO KSDO_DOCUMENT_CATEGORY(CATEGORY_ID,NAME,RT_DESCR_ID,EFF_DT,EXPIR_DT) VALUES ('CAT_2','CATEGORY 2','RT-DESC-DOCUMENT-1',{ts '2000-01-01 00:00:00.0'},  {ts '2000-12-31 00:00:00.0'});
INSERT INTO KSDO_DOCUMENT_CATEGORY(CATEGORY_ID,NAME,RT_DESCR_ID,EFF_DT,EXPIR_DT) VALUES ('CAT_3','CATEGORY 3','RT-DESC-DOCUMENT-1',{ts '2000-01-01 00:00:00.0'},  {ts '2000-12-31 00:00:00.0'});

INSERT INTO KSDO_DOCUMENT_TYPE(TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('documentType.type1', 'PDF Document', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'PDF');

insert into KSDO_REF_OBJ_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.RefObjectType.CluInfo', 'Clu Object Type', null, null, 'CluInfo');
insert into KSDO_REF_OBJ_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.RefObjectType.ProposalInfo', 'Proposal Object Type', null, null, 'ProposalInfo');

insert into KSDO_REF_OBJ_SUB_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REF_OBJ_TYPE_KEY) values ('kuali.org.RefObjectSubType.Program', 'Sub Type for Program', null, null, 'Program', 'kuali.org.RefObjectType.CluInfo');
insert into KSDO_REF_OBJ_SUB_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REF_OBJ_TYPE_KEY) values ('kuali.org.RefObjectSubType.Course', 'Sub Type for Course', null, null, 'Course', 'kuali.org.RefObjectType.CluInfo');
insert into KSDO_REF_OBJ_SUB_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME, REF_OBJ_TYPE_KEY) values ('kuali.org.RefObjectSubType.Proposal', 'Sub Type for Proposal', null, null, 'Proposal', 'kuali.org.RefObjectType.ProposalInfo');

insert into KSDO_REF_DOC_RELTN_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.org.DocRelation.allObjectTypes', 'Relation for all known sub object types', null, null, 'All Relations');

insert into KSDO_REF_REL_TYP_JN_SUB_TYP (REF_DOC_RELTN_TYPE_KEY, REF_OBJ_SUB_TYPE_KEY) values ('kuali.org.DocRelation.allObjectTypes', 'kuali.org.RefObjectSubType.Program');
insert into KSDO_REF_REL_TYP_JN_SUB_TYP (REF_DOC_RELTN_TYPE_KEY, REF_OBJ_SUB_TYPE_KEY) values ('kuali.org.DocRelation.allObjectTypes', 'kuali.org.RefObjectSubType.Course');
insert into KSDO_REF_REL_TYP_JN_SUB_TYP (REF_DOC_RELTN_TYPE_KEY, REF_OBJ_SUB_TYPE_KEY) values ('kuali.org.DocRelation.allObjectTypes', 'kuali.org.RefObjectSubType.Proposal');


