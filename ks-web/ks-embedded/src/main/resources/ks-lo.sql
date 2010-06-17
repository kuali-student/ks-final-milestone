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

// RichText
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-6', '<p>Desc</p>', 'Desc')
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-7', '<p>Desc2</p>', 'Desc2')
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-8', '<p>Desc3</p>', 'Desc3')
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-9', '<p>Desc4</p>', 'Desc4')
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-10', '<p>Learning objectives defined by faculty that are specific to a course</p>','Learning objectives defined by faculty that are specific to a course')
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-11', '<p>Default Learning Objective type</p>', 'Default Learning Objective type')
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-12', '<p>Basic Learning Objective type</p>', 'Basic Learning Objective type')
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-13', '<p>Advanced Learning Objective type</p>', 'Advanced Learning Objective type')
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-14', 'The ability to use sensory cues to ...', 'The ability to use sensory cues to guide motor activity.  This ranges from sensory stimulation, through cue selection, to translation.')
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-15', '<p>Create Wiki</p>', 'Create Wiki')
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-16', '<p>Update Wiki</p>', 'Update Wiki')
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-17', '<p>Modify Wiki</p>', 'Modify Wiki')
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-18', '<p>Learning objectives mandated by the Institution. They represent learning objectives of the institutions general education and are inherited by all programs</p>', 'Learning objectives mandated by the Institution. They represent learning objectives of the institutions general education and are inherited by all programs')
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-19', '<p>Empty Test LoCategory</p>', 'Empty Test LoCategory')
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-20', '<p>Destroy Wiki</p>', 'Destroy Wiki')
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-21', '<p>Annihilate Wiki</p>', 'Annihilate Wiki')
INSERT INTO KSLO_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('RICHTEXT-22', '<p>Learning objectives mandated by the state</p>', 'Learning objectives mandated by the state')

// LoType
INSERT INTO KSLU_LO_TYPE (ID, NAME, DESCR, EFF_DT, EXPIR_DT) VALUES ('kuali.lo.type.governed', 'Governed','LO governed by an organization external to department, e.g., the college at large, or a state or accrediting organization', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'})
INSERT INTO KSLU_LO_TYPE (ID, NAME, DESCR, EFF_DT, EXPIR_DT) VALUES ('kuali.lo.type.singleUse', 'Single use','LO created in support of programs or courses, e.g., faculty-inspired additional LO for a course', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'})

// LoRepository
INSERT INTO KSLU_LO_REPOSITORY (ID, NAME, RT_DESCR_ID, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('kuali.loRepository.key.singleUse', 'singleUse', 'RICHTEXT-10', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
// no LO's, one LoCategory
INSERT INTO KSLU_LO_REPOSITORY (ID, NAME, RT_DESCR_ID, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('kuali.loRepository.key.institution', 'Institution', 'RICHTEXT-18', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
// no LO's, no LoCategory's
INSERT INTO KSLU_LO_REPOSITORY (ID, NAME, RT_DESCR_ID, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('kuali.loRepository.key.state', 'State', 'RICHTEXT-22', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)

// Lo-Lo Relation Type
INSERT INTO KSLU_LOLO_RELTN_TYPE (ID, NAME, DESCR, REV_NAME, REV_DESCR, EFF_DT, EXPIR_DT) values ('kuali.lo.relation.type.includes', 'includes', 'Parent-child relationship between a parent LO and sub LO. Currently used in the context of LOs that are related within a single CLU.', 'is-included-by', 'Child-parent relationship between a child LO and an LO that includes the child.  Currently used in the context of LOs that are related within a single CLU.', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'})
INSERT INTO KSLU_LOLO_RELTN_TYPE (ID, NAME, DESCR, REV_NAME, REV_DESCR, EFF_DT, EXPIR_DT) values ('kuali.lo.relation.type.inSupportOf', 'inSupportOf', '', 'supports', '', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'})

// Allowed Lo-Lo Relation Type 
INSERT INTO KSLU_LOLO_ALLOWED_RELTN_TYPE (ID, LOLO_RELTN_TYPE_ID, LO_TYPE_ID, LO_REL_TYPE_ID, EFF_DT, EXPIR_DT, VERSIONIND) values ('DEC1364F-EFF0-41C6-9803-06A56CA5F192', 'kuali.lo.relation.type.includes', 'kuali.lo.type.singleUse', 'kuali.lo.type.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 1)

// LoCategoryType
INSERT INTO KSLU_LO_CATEGORY_TYPE (ID, NAME, DESCR, EFF_DT, EXPIR_DT) VALUES ('loCategoryType.skillarea', 'Skill', 'Skill', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'})
INSERT INTO KSLU_LO_CATEGORY_TYPE (ID, NAME, DESCR, EFF_DT, EXPIR_DT) VALUES ('loCategoryType.accreditation', 'Accreditation', 'Accreditation', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'})
INSERT INTO KSLU_LO_CATEGORY_TYPE (ID, NAME, DESCR, EFF_DT, EXPIR_DT) VALUES ('loCategoryType.subject', 'Subject', 'Subject', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'})

// LoCategory
INSERT INTO KSLU_LO_CATEGORY (ID, NAME, RT_DESCR_ID, LO_REPO_ID, LO_CATEGORY_TYPE_ID, STATE, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('550e8400-e29b-41d4-a716-446655440000', 'Perception', 'RICHTEXT-14', 'kuali.loRepository.key.singleUse', 'loCategoryType.skillarea', 'active', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
INSERT INTO KSLU_LO_CATEGORY (ID, NAME, RT_DESCR_ID, LO_REPO_ID, LO_CATEGORY_TYPE_ID, STATE, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('054CAA88-C21D-4496-8287-36A311A11D68', 'Test Category 2', 'RICHTEXT-6', 'kuali.loRepository.key.singleUse', 'loCategoryType.subject', 'active', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
INSERT INTO KSLU_LO_CATEGORY (ID, NAME, RT_DESCR_ID, LO_REPO_ID, LO_CATEGORY_TYPE_ID, STATE, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('162979A3-25B9-4921-BC8F-C861B2267A73', 'Test Category 3', 'RICHTEXT-7', 'kuali.loRepository.key.singleUse', 'loCategoryType.skillarea', 'active', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
INSERT INTO KSLU_LO_CATEGORY (ID, NAME, RT_DESCR_ID, LO_REPO_ID, LO_CATEGORY_TYPE_ID, STATE, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('7114D2A4-F66D-4D3A-9D41-A7AA4299C797', 'Test Category 4', 'RICHTEXT-8', 'kuali.loRepository.key.singleUse', 'loCategoryType.subject', 'active', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)
INSERT INTO KSLU_LO_CATEGORY (ID, NAME, RT_DESCR_ID, LO_REPO_ID, LO_CATEGORY_TYPE_ID, STATE, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('F2F02922-4E77-4144-AA07-8C2C956370DC', 'Empty Test Category', 'RICHTEXT-19', 'kuali.loRepository.key.singleUse', 'loCategoryType.skillarea', 'active', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'},1)

// Lo 
INSERT INTO KSLU_LO (ID, NAME, RT_DESCR_ID, LO_REPO_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VERSIONIND) VALUES ('81ABEA67-3BCC-4088-8348-E265F3670145', 'Edit Wiki Message Structure', 'RICHTEXT-9', 'kuali.loRepository.key.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'kuali.lo.type.singleUse', 'draft', 1)
INSERT INTO KSLU_LO (ID, NAME, RT_DESCR_ID, LO_REPO_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VERSIONIND) VALUES ('E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF', 'Navigate Wiki', 'RICHTEXT-12', 'kuali.loRepository.key.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'kuali.lo.type.singleUse', 'draft', 1)
INSERT INTO KSLU_LO (ID, NAME, RT_DESCR_ID, LO_REPO_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VERSIONIND) VALUES ('DD0658D2-FDC9-48FA-9578-67A2CE53BF8A', 'Install Wiki Engine', 'RICHTEXT-13', 'kuali.loRepository.key.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'kuali.lo.type.singleUse', 'draft', 1)
INSERT INTO KSLU_LO (ID, NAME, RT_DESCR_ID, LO_REPO_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VERSIONIND) VALUES ('ABD8AE21-34E9-4858-A714-B04134F55D68', 'Create Wiki', 'RICHTEXT-15', 'kuali.loRepository.key.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'kuali.lo.type.singleUse', 'draft', 1)
INSERT INTO KSLU_LO (ID, NAME, RT_DESCR_ID, LO_REPO_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VERSIONIND) VALUES ('E0619A90-66D6-4AF4-B357-E73AE44F7E88', 'Update Wiki', 'RICHTEXT-16', 'kuali.loRepository.key.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'kuali.lo.type.singleUse', 'draft', 1)
INSERT INTO KSLU_LO (ID, NAME, RT_DESCR_ID, LO_REPO_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VERSIONIND) VALUES ('7BCD7C0E-3E6B-4527-AC55-254C58CECC22', 'Modify Wiki', 'RICHTEXT-17', 'kuali.loRepository.key.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'kuali.lo.type.singleUse', 'draft', 1)
INSERT INTO KSLU_LO (ID, NAME, RT_DESCR_ID, LO_REPO_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VERSIONIND) VALUES ('91A91860-D796-4A17-976B-A6165B1A0B05', 'Destroy Wiki', 'RICHTEXT-20', 'kuali.loRepository.key.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'kuali.lo.type.singleUse', 'draft', 1)
INSERT INTO KSLU_LO (ID, NAME, RT_DESCR_ID, LO_REPO_ID, EFF_DT, EXPIR_DT, LOTYPE_ID, ST, VERSIONIND) VALUES ('FDE6421E-64B4-41AF-BAC5-269005101C2A', 'Annihilate Wiki', 'RICHTEXT-21', 'kuali.loRepository.key.singleUse', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 'kuali.lo.type.singleUse', 'draft', 1)

// Lo-Lo relation join table
INSERT INTO KSLU_LOLO_RELTN (ID, LO_ID, RELATED_LO_ID, LO_LO_RELATION_TYPE_ID, ST, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('4ABF0D00-37D3-4178-B52B-32BD48728FC7', '81ABEA67-3BCC-4088-8348-E265F3670145', 'E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF', 'kuali.lo.relation.type.includes', 'draft', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 1)
INSERT INTO KSLU_LOLO_RELTN (ID, LO_ID, RELATED_LO_ID, LO_LO_RELATION_TYPE_ID, ST, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('61FF5B2C-5D2F-464B-B6D8-082FBF671FCB', '81ABEA67-3BCC-4088-8348-E265F3670145', 'DD0658D2-FDC9-48FA-9578-67A2CE53BF8A', 'kuali.lo.relation.type.includes', 'draft', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 1)
INSERT INTO KSLU_LOLO_RELTN (ID, LO_ID, RELATED_LO_ID, LO_LO_RELATION_TYPE_ID, ST, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('87F115B5-2C0A-4B0A-A7DD-8759AB5A9E17', 'E0B456B2-62CB-4BD3-8867-A0D59FD8F2CF', 'ABD8AE21-34E9-4858-A714-B04134F55D68', 'kuali.lo.relation.type.includes', 'draft', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 1)
INSERT INTO KSLU_LOLO_RELTN (ID, LO_ID, RELATED_LO_ID, LO_LO_RELATION_TYPE_ID, ST, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('6BBD79C4-3208-433E-BF58-8D1D06DBEFEB', 'DD0658D2-FDC9-48FA-9578-67A2CE53BF8A', 'ABD8AE21-34E9-4858-A714-B04134F55D68', 'kuali.lo.relation.type.includes', 'draft', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 1)
INSERT INTO KSLU_LOLO_RELTN (ID, LO_ID, RELATED_LO_ID, LO_LO_RELATION_TYPE_ID, ST, EFF_DT, EXPIR_DT, VERSIONIND) VALUES ('63F06B7A-1A51-4D2F-B581-98BDD70F47F8', '91A91860-D796-4A17-976B-A6165B1A0B05', 'FDE6421E-64B4-41AF-BAC5-269005101C2A', 'kuali.lo.relation.type.includes', 'draft', {ts '2008-01-01 00:00:00.0'}, {ts '2010-01-01 00:00:00.0'}, 1)
// Lo-LoCategory join table
INSERT INTO KSLU_LO_JN_LOCATEGORY (LO_ID, LOCATEGORY_ID) VALUES ('81ABEA67-3BCC-4088-8348-E265F3670145', '550e8400-e29b-41d4-a716-446655440000')
INSERT INTO KSLU_LO_JN_LOCATEGORY (LO_ID, LOCATEGORY_ID) VALUES ('DD0658D2-FDC9-48FA-9578-67A2CE53BF8A', '550e8400-e29b-41d4-a716-446655440000')
INSERT INTO KSLU_LO_JN_LOCATEGORY (LO_ID, LOCATEGORY_ID) VALUES ('DD0658D2-FDC9-48FA-9578-67A2CE53BF8A', '7114D2A4-F66D-4D3A-9D41-A7AA4299C797')
INSERT INTO KSLU_LO_JN_LOCATEGORY (LO_ID, LOCATEGORY_ID) VALUES ('E0619A90-66D6-4AF4-B357-E73AE44F7E88', '162979A3-25B9-4921-BC8F-C861B2267A73')
