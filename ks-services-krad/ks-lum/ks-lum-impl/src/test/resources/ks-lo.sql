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
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-6', '<p>Desc</p>', 'Desc',0)
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-7', '<p>Desc2</p>', 'Desc2',0)
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-8', '<p>Desc3</p>', 'Desc3',0)
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-9', '<p>Desc4</p>', 'Desc4',0)
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-10', '<p>Learning objectives defined by faculty that are specific to a course</p>','Learning objectives defined by faculty that are specific to a course',0)
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-11', '<p>Default Learning Objective type</p>', 'Default Learning Objective type',0)
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-12', '<p>Basic Learning Objective type</p>', 'Basic Learning Objective type',0)
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-13', '<p>Advanced Learning Objective type</p>', 'Advanced Learning Objective type',0)
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-14', 'The ability to use sensory cues to ...', 'The ability to use sensory cues to guide motor activity.  This ranges from sensory stimulation, through cue selection, to translation.',0)
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-15', '<p>Create Wiki</p>', 'Create Wiki',0)
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-16', '<p>Update Wiki</p>', 'Update Wiki',0)
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-17', '<p>Modify Wiki</p>', 'Modify Wiki',0)
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-18', '<p>Learning objectives mandated by the Institution. They represent learning objectives of the institutions general education and are inherited by all programs</p>', 'Learning objectives mandated by the Institution. They represent learning objectives of the institutions general education and are inherited by all programs',0)
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-19', '<p>Empty Test LoCategory</p>', 'Empty Test LoCategory',0)
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-20', '<p>Destroy Wiki</p>', 'Destroy Wiki',0)
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-21', '<p>Annihilate Wiki</p>', 'Annihilate Wiki',0)
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-22', '<p>Learning objectives mandated by the state</p>', 'Learning objectives mandated by the state',0)
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('RICHTEXT-23', '<p>Core Program Learning objectives</p>', 'Core Program Learning objectives',0)

-- LoType
INSERT INTO KSLO_LO_TYPE (ID, NAME, DESCR, EFF_DT, EXPIR_DT,VER_NBR) VALUES ('kuali.lo.type.governed', 'Governed','LO governed by an organization external to department, e.g., the college at large, or a state or accrediting organization', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},0)
INSERT INTO KSLO_LO_TYPE (ID, NAME, DESCR, EFF_DT, EXPIR_DT,VER_NBR) VALUES ('kuali.lo.type.singleUse', 'Single use','LO created in support of programs or courses, e.g., faculty-inspired additional LO for a course', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},0)

-- LoRepository
INSERT INTO KSLO_LO_REPOSITORY (ID, NAME, RT_DESCR_ID, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('kuali.loRepository.key.singleUse', 'singleUse', 'RICHTEXT-10', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
-- no LO's, one LoCategory
INSERT INTO KSLO_LO_REPOSITORY (ID, NAME, RT_DESCR_ID, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('kuali.loRepository.key.institution', 'Institution', 'RICHTEXT-18', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
-- no LO's, no LoCategory's
INSERT INTO KSLO_LO_REPOSITORY (ID, NAME, RT_DESCR_ID, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('kuali.loRepository.key.state', 'State', 'RICHTEXT-22', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)

-- Lo-Lo Relation Type
INSERT INTO KSLO_LO_RELTN_TYPE (ID, NAME, DESCR, REV_NAME, REV_DESCR, EFF_DT, EXPIR_DT, VER_NBR) values ('kuali.lo.relation.type.includes', 'includes', 'Parent-child relationship between a parent LO and sub LO. Currently used in the context of LOs that are related within a single CLU.', 'is-included-by', 'Child-parent relationship between a child LO and an LO that includes the child.  Currently used in the context of LOs that are related within a single CLU.', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},0)
INSERT INTO KSLO_LO_RELTN_TYPE (ID, NAME, DESCR, REV_NAME, REV_DESCR, EFF_DT, EXPIR_DT, VER_NBR) values ('kuali.lo.relation.type.inSupportOf', 'inSupportOf', '', 'supports', '', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},0)

-- Allowed Lo-Lo Relation Type 
INSERT INTO KSLO_LO_ALLOWED_RELTN_TYPE (ID, LOLO_RELTN_TYPE_ID, LO_TYPE_ID, LO_REL_TYPE_ID, EFF_DT, EXPIR_DT, VER_NBR) values ('DEC1364F-EFF0-41C6-9803-06A56CA5F192', 'kuali.lo.relation.type.includes', 'kuali.lo.type.singleUse', 'kuali.lo.type.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 1)

-- LoCategoryType
INSERT INTO KSLO_LO_CATEGORY_TYPE (ID, NAME, DESCR, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('loCategoryType.skillarea', 'Skill', 'Skill', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},0)
INSERT INTO KSLO_LO_CATEGORY_TYPE (ID, NAME, DESCR, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('loCategoryType.accreditation', 'Accreditation', 'Accreditation', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},0)
INSERT INTO KSLO_LO_CATEGORY_TYPE (ID, NAME, DESCR, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('loCategoryType.subject', 'Subject', 'Subject', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},0)

-- LoCategory
INSERT INTO KSLO_LO_CATEGORY (ID, NAME, RT_DESCR_ID, LO_REPO_ID, LO_CATEGORY_TYPE_ID, STATE, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('550e8400-e29b-41d4-a716-446655440000', 'Perception', 'RICHTEXT-14', 'kuali.loRepository.key.singleUse', 'loCategoryType.skillarea', 'active', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
INSERT INTO KSLO_LO_CATEGORY (ID, NAME, RT_DESCR_ID, LO_REPO_ID, LO_CATEGORY_TYPE_ID, STATE, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('054caa88-c21d-4496-8287-36a311a11d68', 'Test Category 2', 'RICHTEXT-6', 'kuali.loRepository.key.singleUse', 'loCategoryType.subject', 'active', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
INSERT INTO KSLO_LO_CATEGORY (ID, NAME, RT_DESCR_ID, LO_REPO_ID, LO_CATEGORY_TYPE_ID, STATE, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('162979a3-25b9-4921-bc8f-c861b2267a73', 'Test Category 3', 'RICHTEXT-7', 'kuali.loRepository.key.singleUse', 'loCategoryType.skillarea', 'active', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
INSERT INTO KSLO_LO_CATEGORY (ID, NAME, RT_DESCR_ID, LO_REPO_ID, LO_CATEGORY_TYPE_ID, STATE, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('7114d2a4-f66d-4d3a-9d41-a7aa4299c797', 'Test Category 4', 'RICHTEXT-8', 'kuali.loRepository.key.singleUse', 'loCategoryType.subject', 'active', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
INSERT INTO KSLO_LO_CATEGORY (ID, NAME, RT_DESCR_ID, LO_REPO_ID, LO_CATEGORY_TYPE_ID, STATE, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('f2f02922-4e77-4144-aa07-8c2c956370dc', 'Empty Test Category', 'RICHTEXT-19', 'kuali.loRepository.key.singleUse', 'loCategoryType.skillarea', 'active', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)

-- Lo 
INSERT INTO KSLO_LO (ID, NAME, RT_DESCR_ID, LO_REPO_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VER_NBR) VALUES ('81abea67-3bcc-4088-8348-e265f3670145', 'Edit Wiki Message Structure', 'RICHTEXT-9', 'kuali.loRepository.key.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'kuali.lo.type.singleUse', 'draft', 1)
INSERT INTO KSLO_LO (ID, NAME, RT_DESCR_ID, LO_REPO_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VER_NBR) VALUES ('e0b456b2-62cb-4bd3-8867-a0d59fd8f2cf', 'Navigate Wiki', 'RICHTEXT-12', 'kuali.loRepository.key.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'kuali.lo.type.singleUse', 'draft', 1)
INSERT INTO KSLO_LO (ID, NAME, RT_DESCR_ID, LO_REPO_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VER_NBR) VALUES ('dd0658d2-fdc9-48fa-9578-67a2ce53bf8a', 'Install Wiki Engine', 'RICHTEXT-13', 'kuali.loRepository.key.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'kuali.lo.type.singleUse', 'draft', 1)
INSERT INTO KSLO_LO (ID, NAME, RT_DESCR_ID, LO_REPO_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VER_NBR) VALUES ('abd8ae21-34e9-4858-a714-b04134f55d68', 'Create Wiki', 'RICHTEXT-15', 'kuali.loRepository.key.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'kuali.lo.type.singleUse', 'draft', 1)
INSERT INTO KSLO_LO (ID, NAME, RT_DESCR_ID, LO_REPO_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VER_NBR) VALUES ('e0619a90-66d6-4af4-b357-e73ae44f7e88', 'Update Wiki', 'RICHTEXT-16', 'kuali.loRepository.key.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'kuali.lo.type.singleUse', 'draft', 1)
INSERT INTO KSLO_LO (ID, NAME, RT_DESCR_ID, LO_REPO_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VER_NBR) VALUES ('7bcd7c0e-3e6b-4527-ac55-254c58cecc22', 'Modify Wiki', 'RICHTEXT-17', 'kuali.loRepository.key.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'kuali.lo.type.singleUse', 'draft', 1)
INSERT INTO KSLO_LO (ID, NAME, RT_DESCR_ID, LO_REPO_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VER_NBR) VALUES ('91a91860-d796-4a17-976b-a6165b1a0b05', 'Destroy Wiki', 'RICHTEXT-20', 'kuali.loRepository.key.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'kuali.lo.type.singleUse', 'draft', 1)
INSERT INTO KSLO_LO (ID, NAME, RT_DESCR_ID, LO_REPO_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VER_NBR) VALUES ('fde6421e-64b4-41af-bac5-269005101c2a', 'Annihilate Wiki', 'RICHTEXT-21', 'kuali.loRepository.key.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'kuali.lo.type.singleUse', 'draft', 1)
INSERT INTO KSLO_LO (ID, NAME, RT_DESCR_ID, LO_REPO_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VER_NBR) VALUES ('LO-111', 'Core Program LO', 'RICHTEXT-23', 'kuali.loRepository.key.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'kuali.lo.type.singleUse', 'draft', 1)

-- Lo-Lo relation join table
INSERT INTO KSLO_LO_RELTN (ID, LO_ID, RELATED_LO_ID, LO_LO_RELATION_TYPE_ID, ST, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('4abf0d00-37d3-4178-b52b-32bd48728fc7', '81abea67-3bcc-4088-8348-e265f3670145', 'e0b456b2-62cb-4bd3-8867-a0d59fd8f2cf', 'kuali.lo.relation.type.includes', 'draft', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 1)
INSERT INTO KSLO_LO_RELTN (ID, LO_ID, RELATED_LO_ID, LO_LO_RELATION_TYPE_ID, ST, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('61ff5b2c-5d2f-464b-b6d8-082fbf671fcb', '81abea67-3bcc-4088-8348-e265f3670145', 'dd0658d2-fdc9-48fa-9578-67a2ce53bf8a', 'kuali.lo.relation.type.includes', 'draft', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 1)
INSERT INTO KSLO_LO_RELTN (ID, LO_ID, RELATED_LO_ID, LO_LO_RELATION_TYPE_ID, ST, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('87f115b5-2c0a-4b0a-a7dd-8759ab5a9e17', 'e0b456b2-62cb-4bd3-8867-a0d59fd8f2cf', 'abd8ae21-34e9-4858-a714-b04134f55d68', 'kuali.lo.relation.type.includes', 'draft', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 1)
INSERT INTO KSLO_LO_RELTN (ID, LO_ID, RELATED_LO_ID, LO_LO_RELATION_TYPE_ID, ST, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('6bbd79c4-3208-433e-bf58-8d1d06dbefeb', 'dd0658d2-fdc9-48fa-9578-67a2ce53bf8a', 'abd8ae21-34e9-4858-a714-b04134f55d68', 'kuali.lo.relation.type.includes', 'draft', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 1)
INSERT INTO KSLO_LO_RELTN (ID, LO_ID, RELATED_LO_ID, LO_LO_RELATION_TYPE_ID, ST, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('63f06b7a-1a51-4d2f-b581-98bdd70f47f8', '91a91860-d796-4a17-976b-a6165b1a0b05', 'fde6421e-64b4-41af-bac5-269005101c2a', 'kuali.lo.relation.type.includes', 'draft', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 1)

-- Lo-LoCategory join table
INSERT INTO KSLO_LO_JN_LOCATEGORY (ID, LO_ID, LOCATEGORY_ID, VER_NBR) VALUES ('LO-LOCAT-1','81abea67-3bcc-4088-8348-e265f3670145', '550e8400-e29b-41d4-a716-446655440000',0)
INSERT INTO KSLO_LO_JN_LOCATEGORY (ID, LO_ID, LOCATEGORY_ID, VER_NBR) VALUES ('LO-LOCAT-2','dd0658d2-fdc9-48fa-9578-67a2ce53bf8a', '550e8400-e29b-41d4-a716-446655440000',0)
INSERT INTO KSLO_LO_JN_LOCATEGORY (ID, LO_ID, LOCATEGORY_ID, VER_NBR) VALUES ('LO-LOCAT-3','dd0658d2-fdc9-48fa-9578-67a2ce53bf8a', '7114d2a4-f66d-4d3a-9d41-a7aa4299c797',0)
INSERT INTO KSLO_LO_JN_LOCATEGORY (ID, LO_ID, LOCATEGORY_ID, VER_NBR) VALUES ('LO-LOCAT-4','e0619a90-66d6-4af4-b357-e73ae44f7e88', '162979a3-25b9-4921-bc8f-c861b2267a73',0)
