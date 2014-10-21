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

DELETE FROM KSPR_RICH_TEXT_T;
DELETE FROM KSPR_PROPOSAL_JN_REFERENCE;
DELETE FROM KSPR_PROPOSAL_JN_PERSON;
DELETE FROM KSPR_PROPOSAL;

INSERT INTO KSPR_RICH_TEXT_T (ID, PLAIN, FORMATTED) VALUES ('PROPOSAL-RT-1', 'Document 1', '<p>Document 1</p>');
INSERT INTO KSPR_RICH_TEXT_T (ID, PLAIN, FORMATTED) VALUES ('PROPOSAL-RT-2', 'Document type 1', '<p>Document type 1</p>');

INSERT INTO KSPR_PROPOSAL_TYPE (TYPE_KEY, NAME, TYPE_DESC, EFF_DT, EXPIR_DT) VALUES ('proposalType.courseCorrection', 'Course Correction', 'A proposed minor change to a course which would not result in the creation of a new cluId.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'});
INSERT INTO KSPR_PROPOSAL_TYPE (TYPE_KEY, NAME, TYPE_DESC, EFF_DT, EXPIR_DT) VALUES ('proposalType.newCourse', 'New Course', 'A new Course proposal.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'});


INSERT INTO KSPR_PROPOSAL (PROPOSAL_ID, NAME, RATIONALE, DETAIL_DESC, EFF_DT, EXPIR_DT, TYPE, STATE, VER_NBR) VALUES ('PROPOSAL-1', 'Change "De-Cal" to "Democracy at Cal"', 'Faculty senate officially recommended changing the titles rendering on the transcript from "De-Cal"', 'Change De-Cal to Democracy at Cal in the short name field of the official identifier.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'proposalType.courseCorrection', 'active', 1);
INSERT INTO KSPR_PROPOSAL (PROPOSAL_ID, NAME, RATIONALE, DETAIL_DESC, EFF_DT, EXPIR_DT, TYPE, STATE, VER_NBR) VALUES ('PROPOSAL-2', 'Name 2', 'Rational 2', 'Detailed description 2', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'proposalType.courseCorrection', 'active', 1);
INSERT INTO KSPR_PROPOSAL (PROPOSAL_ID, NAME, RATIONALE, DETAIL_DESC, EFF_DT, EXPIR_DT, TYPE, STATE, VER_NBR) VALUES ('PROPOSAL-3', 'Name 3', 'Rational 2', 'Detailed description 3', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'proposalType.newCourse', 'active', 1);


INSERT INTO KSPR_PROPOSAL_REFTYPE (TYPE_KEY, NAME, TYPE_DESC, EFF_DT, EXPIR_DT) VALUES ('REFTYPE-1', 'Clu', 'Reference type for a Clu', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'});

INSERT INTO KSPR_PROPOSAL_REFERENCE (REFERENCE_ID, OBJECT_REFERENCE_ID, TYPE) VALUES ('OBJREF-1', 'REMOTEREF-1', 'REFTYPE-1')
INSERT INTO KSPR_PROPOSAL_REFERENCE (REFERENCE_ID, OBJECT_REFERENCE_ID, TYPE) VALUES ('OBJREF-2', 'REMOTEREF-2', 'REFTYPE-1')
INSERT INTO KSPR_PROPOSAL_REFERENCE (REFERENCE_ID, OBJECT_REFERENCE_ID, TYPE) VALUES ('OBJREF-3', 'REMOTEREF-3', 'REFTYPE-1')
INSERT INTO KSPR_PROPOSAL_REFERENCE (REFERENCE_ID, OBJECT_REFERENCE_ID, TYPE) VALUES ('OBJREF-4', 'REMOTEREF-4', 'REFTYPE-1')

INSERT INTO KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) VALUES ('PROPOSAL-1', 'OBJREF-1');
INSERT INTO KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) VALUES ('PROPOSAL-1', 'OBJREF-2');
INSERT INTO KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) VALUES ('PROPOSAL-1', 'OBJREF-3');
INSERT INTO KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) VALUES ('PROPOSAL-1', 'OBJREF-4');
INSERT INTO KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) VALUES ('PROPOSAL-2', 'OBJREF-1');
INSERT INTO KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) VALUES ('PROPOSAL-3', 'OBJREF-3');

INSERT INTO KSPR_PROPOSAL_JN_PERSON (ID, PROPOSAL_ID, PERSONREF_ID) VALUES ('PERSON-1', 'PROPOSAL-1', 'PERSONREF-1');