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

// LuiPersonRelationStateEntity
// - Course and Section - Student - LPR States
INSERT INTO KSLP_LPR_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.lpr.state.planned', 'Planned', 'The student plans on taking this course or program', 0)
INSERT INTO KSLP_LPR_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.lpr.state.registered', 'Registered', 'The student is officially registered for the course or section', 0)
INSERT INTO KSLP_LPR_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.lpr.state.not.paid', 'Fee Not Paid', 'The student has registered for the course by has not paid the fee', 0)
INSERT INTO KSLP_LPR_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.lpr.state.waitlisted', 'Waitlisted', 'The student attempted to join but has been put on the waitlist', 0)
INSERT INTO KSLP_LPR_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.lpr.state.dropped.early', 'Dropped', 'The student was registered but subsequently dropped the course or section within the normally allotted time period', 0)
INSERT INTO KSLP_LPR_STATE (ID, NAME, DESCR, VER_NBR) VALUES ('kuali.lpr.state.dropped.late', 'Dropped Late', 'The student was registered but subsequently dropped the course or section past the normally allotted time period, typically resulting in a special grade or mark to so indicate', 0)
// - Course and Section - Instructor - LPR States
// - Program - Student - LPR States

// LuiPersonRelationTypeEntity
// - Course and Section Ð Instructor - LPR Types
INSERT INTO KSLP_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.type.instructor.main', 'Main Instructor', 'Main instructor(s) responsible for course or section', 0)
INSERT INTO KSLP_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.type.instructor.assistant', 'Assistant Instructor', 'Person who assists the main instructor but is still considered an "instructor"', 0)
INSERT INTO KSLP_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.type.instructor.support', 'Support instructor', 'Persons who support the course but not in any official teaching role', 0)
// - Course and Section Ð Student Ð LPR Types, 0
INSERT INTO KSLP_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.type.registrant', 'Registrant', 'Registrant who is taking the course', 0)
// - Program LPR Types
INSERT INTO KSLP_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.enrollee', 'Enrollee', 'Enrollee in the program', 0)
INSERT INTO KSLP_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.advisor', 'Advisor', 'Advisor to students in the program', 0)
// - Experiential Learning LPR Types
INSERT INTO KSLP_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.instructor.supervisor', 'Supervisor', 'Supervisor', 0)
// - Tests and Exam LPR Types
INSERT INTO KSLP_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.exam.proctor', 'Proctor', 'Person who administers a test or examination', 0)

// LuiPersonRelationEntity
INSERT INTO KSLP_LPR (ID, PERSONID, LUIID, EFFECTIVEDATE, EXPIRATIONDATE, RELATION_TYPE_ID, RELATION_STATE_ID, VER_NBR) VALUES ('testLprId1', 'testPersonId1', 'testLuiId1', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'kuali.lpr.type.registrant', 'kuali.lpr.state.registered', 0) 
