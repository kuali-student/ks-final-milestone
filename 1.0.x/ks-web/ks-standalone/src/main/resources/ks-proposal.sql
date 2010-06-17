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

// Proposal Type
INSERT INTO KSPR_PROPOSAL_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.proposal.type.course.create', 'A create course proposal type', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Create Course Proposal')
/

// Proposal Reference Types
INSERT INTO KSPR_PROPOSAL_REFTYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.proposal.referenceType.clu', 'Clu proposal reference type', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Clu Proposal Reference')
/
INSERT INTO kspr_proposal_docrel_type (ID, description, eff_dt, expir_dt, NAME) VALUES ('kuali.proposal.docRelationType.attachment', 'Default proposal document relation type', {ts '2000-01-01 00:00:00.0'}, null, 'kuali.proposal.docRelationType.attachment')
/