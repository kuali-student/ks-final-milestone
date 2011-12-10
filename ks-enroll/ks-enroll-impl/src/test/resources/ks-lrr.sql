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

INSERT INTO KSEN_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.roster.entry.type.automatic', 'Automatic', 'Don''t know what this is', 0)
INSERT INTO KSEN_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.trans.item.type.add', 'Add', 'Add course', 0)
INSERT INTO KSEN_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.trans.type.register', 'Register', 'Register', 0)
INSERT INTO KSEN_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.type.registrant', 'Registrant', 'Registrant who is taking the course', 0)

INSERT INTO KSEN_LRR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lrr.type.credential.awarded ', 'Credential Awarded', 'Indicates the credential that was awarded', 0)
INSERT INTO KSEN_LRR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lrr.type.credits.earned', 'Credits Earned', 'Indicates the number of credits the student earned for successfully completing the course.  Often done via a calculation by looking at the calculated grade and either awarding the number of attempted credits if it is a passing grade.', 0)
INSERT INTO KSEN_LRR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lrr.type.final.grade.administrative', 'Administrative Final Grade', 'Administrative Grade that was recorded for this student', 0)
INSERT INTO KSEN_LRR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lrr.type.final.grade.assigned', 'Assigned Final Grade', 'Grade as it was originally submitted by the faculty member on the grade sheet.', 0)
INSERT INTO KSEN_LRR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lrr.type.final.grade.calculated', 'Calculated Final Grade', 'Grade calculated based on the scale or option chosen and the grade actually submitted by the faculty member.', 0)
INSERT INTO KSEN_LRR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lrr.type.interim.grade', 'Interim Grade', 'Grade assigned during at an interim period often used to flag students who might be struggling', 0)
INSERT INTO KSEN_LRR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lrr.type.standardized.test.ranking', 'Standardized Test Ranking', 'The ranking of the student within the peer group who took the test during the same time period.  Often recorded as a percentage or a decile', 0)
INSERT INTO KSEN_LRR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lrr.type.standardized.test.score', 'Standardized Test Score', 'A score a student got by taking standardized test.', 0)

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lpr.state.registered', 'Registered', 'kuali.lpr.process.student.course.registration', 'The student is officially registered for the course or section', 0)

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lrr.state.saved', 'Saved', 'kuali.lrr.course.final.grading.lifecycle', 'The result (grade) has been entered and has been saved but not yet been submitted', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lrr.state.submitted', 'Submitted', 'kuali.lrr.course.final.grading.lifecycle', 'The result (grade) has been formally submitted', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lrr.state.accepted', 'Accepted', 'kuali.lrr.course.final.grading.lifecycle', 'The result (grade) has been vetted and is ready to be published to the student', 0)



INSERT INTO KSEN_LPR (ID, VER_NBR, LUIID, PERSONID, COMMITMENTPERCENT, RELATION_STATE_ID, RELATION_TYPE_ID) VALUES ('student1', 0, 'Lui-1','testPersonId1', 1, 'kuali.lpr.state.registered', 'kuali.lpr.type.registrant')
INSERT INTO KSEN_LPR (ID, VER_NBR, LUIID, PERSONID, COMMITMENTPERCENT, RELATION_STATE_ID, RELATION_TYPE_ID) VALUES ('student2', 0, 'Lui-1','testPersonId2', 1, 'kuali.lpr.state.registered', 'kuali.lpr.type.registrant')

INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('grade-final-lecture-desc', '<p>Final Grade for Lecture</p>', 'Final Grade for Lecture', 0)
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('grade-interim-lecture-desc', '<p>Interim Grade for Lecture</p>', 'Interim Grade for Lecture', 0)
INSERT INTO KSEN_LRR(ID, LPR_ID, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, RESULT_VALUE_ID, VER_NBR) VALUES('student1-grade-final-lecture', 'student1', 'student1 grade final lecture', 'grade-final-lecture-desc', 'kuali.lrr.state.saved', 'kuali.lrr.type.final.grade.assigned', 'student1-grade-value-final-lecture', 0)
INSERT INTO KSEN_LRR(ID, LPR_ID, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, RESULT_VALUE_ID, VER_NBR) VALUES('student1-grade-interim-lecture', 'student1', 'student1 grade interim lecture', 'grade-interim-lecture-desc', 'kuali.lrr.state.saved', 'kuali.lrr.type.interim.grade', 'student1-grade-value-interim-lecture', 0)
INSERT INTO KSEN_LRR(ID, LPR_ID, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, RESULT_VALUE_ID, VER_NBR) VALUES('student2-grade-final-lecture', 'student2', 'student2 grade final lecture', 'grade-final-lecture-desc', 'kuali.lrr.state.saved', 'kuali.lrr.type.final.grade.assigned', 'student2-grade-value-final-lecture', 0)
INSERT INTO KSEN_LRR(ID, LPR_ID, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, RESULT_VALUE_ID, VER_NBR) VALUES('student2-grade-interim-lecture', 'student2', 'student2 grade interim lecture', 'grade-interim-lecture-desc', 'kuali.lrr.state.saved', 'kuali.lrr.type.interim.grade', 'student2-grade-value-interim-lecture', 0)

