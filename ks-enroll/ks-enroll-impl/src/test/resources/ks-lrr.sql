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

INSERT INTO KSEN_LPR (ID, VER_NBR, LUIID, PERSONID, COMMITMENTPERCENT, RELATION_STATE_ID, RELATION_TYPE_ID) VALUES ('student1', 0, 'Lui-1','testPersonId1', 1, 'kuali.lpr.state.registered', 'kuali.lpr.type.registrant')
INSERT INTO KSEN_LPR (ID, VER_NBR, LUIID, PERSONID, COMMITMENTPERCENT, RELATION_STATE_ID, RELATION_TYPE_ID) VALUES ('student2', 0, 'Lui-1','testPersonId2', 1, 'kuali.lpr.state.registered', 'kuali.lpr.type.registrant')

INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('grade-final-lecture-desc', '<p>Final Grade for Lecture</p>', 'Final Grade for Lecture', 0)
INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('grade-interim-lecture-desc', '<p>Interim Grade for Lecture</p>', 'Interim Grade for Lecture', 0)
INSERT INTO KSEN_LRR(ID, LPR_ID, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, RESULT_VALUE_ID, VER_NBR) VALUES('student1-grade-final-lecture', 'student1', 'student1 grade final lecture', 'grade-final-lecture-desc', 'kuali.lrr.state.saved', 'kuali.lrr.type.final.grade.assigned', 'student1-grade-value-final-lecture', 0)
INSERT INTO KSEN_LRR(ID, LPR_ID, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, RESULT_VALUE_ID, VER_NBR) VALUES('student1-grade-interim-lecture', 'student1', 'student1 grade interim lecture', 'grade-interim-lecture-desc', 'kuali.lrr.state.saved', 'kuali.lrr.type.interim.grade', 'student1-grade-value-interim-lecture', 0)
INSERT INTO KSEN_LRR(ID, LPR_ID, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, RESULT_VALUE_ID, VER_NBR) VALUES('student2-grade-final-lecture', 'student2', 'student2 grade final lecture', 'grade-final-lecture-desc', 'kuali.lrr.state.saved', 'kuali.lrr.type.final.grade.assigned', 'student2-grade-value-final-lecture', 0)
INSERT INTO KSEN_LRR(ID, LPR_ID, NAME, RT_DESCR_ID, STATE_ID, TYPE_ID, RESULT_VALUE_ID, VER_NBR) VALUES('student2-grade-interim-lecture', 'student2', 'student2 grade interim lecture', 'grade-interim-lecture-desc', 'kuali.lrr.state.saved', 'kuali.lrr.type.interim.grade', 'student2-grade-value-interim-lecture', 0)

