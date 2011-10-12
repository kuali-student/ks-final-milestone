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

INSERT INTO KSEN_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.roster.type.course.grade.final', 'Final Grade Roster', 'Final grade roster.', 0)
INSERT INTO KSEN_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.type.instructor.main', 'Main Instructor', 'Main instructor(s) responsible for course or section', 0)
INSERT INTO KSEN_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.type.registrant', 'Registrant', 'Registrant who is taking the course', 0)
INSERT INTO KSEN_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.trans.type.register', 'Register', 'Register Student for a Course', 0)
INSERT INTO KSEN_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.trans.item.type.add', 'Add', 'Add student to registration group', 0)
INSERT INTO KSEN_LPR_TYPE (TYPE_KEY, NAME, TYPE_DESC, VER_NBR) VALUES ('kuali.lpr.roster.entry.type.automatic', 'Automatic', 'Don''t know what this is', 0)

INSERT INTO KSEN_LUI_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.lui.type.course.offering', 'Course Offering', 'An offering of a course for a particular term', 0)
INSERT INTO KSEN_LUI_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.lui.type.activity.offering.lecture', 'Lecture', 'Instructor presentation of course materials', 0)
INSERT INTO KSEN_LUI_TYPE(TYPE_KEY, NAME, TYPE_DESC, VER_NBR)VALUES ('kuali.lui.lui.relation.associated', 'Associated', 'The first LUI is associated with the second LUI', 0)

INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lui.state.draft', 'Draft', 'kuali.course.offering.process', 'Proposed for Departmental Planning purposed', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lui.lui.relation.state.active', 'Active', 'kuali.lui.lui.relationship.process', 'The relationship between the two LUIs is active ', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lpr.trans.item.state.new', 'New', 'kuali.lpr.process.student.course.registration', 'The student is attemptng to enroll in this course or section.', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lpr.trans.registered', 'Active', 'kuali.hold.process.student', 'This hold is active and should be enforced', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lpr.state.assigned', 'Assigned', 'kuali.lpr.process.instructor.course.assignment', 'The instructor is assigned to teach this course or section.', 0)
INSERT INTO KSEN_COMM_STATE (ID, NAME, PROCESS_KEY, DESCR, VER_NBR) VALUES ('kuali.lpr.state.registered', 'Registered', 'kuali.lpr.process.student.course.registration', 'The student is officially registered for the course or section', 0)
INSERT INTO KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) VALUES ('kuali.lpr.roster.state.ready',null,0,null,null,null,null,'Active Roster entry',null,null,'Assigned','kuali.lpr.roster.process.course.grading')
INSERT INTO KSEN_COMM_STATE (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,DESCR,EFF_DT,EXPIR_DT,NAME,PROCESS_KEY) VALUES ('kuali.roster.entry.state.active',null,0,null,null,null,null,'The roster has been created and is ready to have grades entered',null,null,'Ready','kuali.lpr.roster.process.course.grading')



INSERT INTO KSEN_LUI_RICH_TEXT (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('Lui-1-Desc', '<p>Lui Desc 101</p>', 'Lui Desc 101',0)
INSERT INTO KSEN_LUI_RICH_TEXT (ID, FORMATTED, PLAIN, VER_NBR) VALUES ('Lui-2-Desc', '<p>Lui Desc 201</p>', 'Lui Desc 201',0)

INSERT INTO KSEN_LUI_IDENT (ID, CD, DIVISION, SUFX_CD, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN, VER_NBR) VALUES ('LUI-IDENT-1', 'CHEM123', 'CHEM', '123', 'Chemistry 123', 'Chem 123', null, null, null, 0)
INSERT INTO KSEN_LUI (ID, NAME, CLU_ID, TYPE_ID, STATE_ID, RT_DESCR_ID, EFF_DT, EXP_DT, OFFIC_LUI_ID, VER_NBR, ATP_ID) VALUES ('Lui-1', 'Lui one',  'cluId1', 'kuali.lui.type.course.offering', 'kuali.lui.state.draft', 'Lui-1-Desc', {ts '2011-01-01 00:00:00.0'}, {ts '2011-12-31 00:00:00.0'}, 'LUI-IDENT-1', 0,'testTermId4')

INSERT INTO KSEN_LPR_TRANS (ID, REQ_PERSON_ID, LPR_TYPE_ID, STATE_ID, CREATETIME, UPDATEID, UPDATETIME) VALUES ('testLprTransId1', 'admin-1', 'kuali.lpr.trans.type.register', 'kuali.lpr.trans.registered', {ts '1900-01-01 00:00:00.0'}, 'admin', {ts '2000-01-01 00:00:00.0'})
INSERT INTO KSEN_LPR_TRANS_ITEMS (ID, PERSON_ID, NEW_LUI_ID, EXIST_LUI_ID, TYPE_ID, STATE_ID, LPR_TRANS_ID) VALUES ('testLprTransItemId1', 'Student1','Lui-1',null, 'kuali.lpr.trans.item.type.add','kuali.lpr.trans.item.state.new', 'testLprTransId1')

INSERT INTO KSEN_RICH_TEXT_T (ID, FORMATTED, PLAIN,VER_NBR) VALUES ('RICHTEXT-TRT-9', '<p>Roster</p>', 'Roster',0)
INSERT INTO KSEN_LPR_ROSTER (ID,CREATEID,CREATETIME,UPDATEID,UPDATETIME,OBJ_ID,VER_NBR,NAME,RT_DESCR_ID,MAX_CAPACITY,CHECK_IN_REQ,STATE_ID,TYPE_ID,ATP_DUR_TYP_KEY,TM_QUANTITY) VALUES ('testLprRoster1',null,null,null,null,null,null,'Chemistry I Roster','RICHTEXT-TRT-9',5,0,'kuali.lpr.roster.state.ready','kuali.lpr.roster.type.course.grade.final','testTermId1',null)
INSERT INTO KSEN_LPRROSTER_LUI_RELTN (LPRROSTER_ID,LUI_ID) VALUES ('testLprRoster1','Lui-1')

INSERT INTO KSEN_LPR_ROSTER_ENTRY (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,EFFECTIVEDATE,EXPIRATIONDATE,LPRROSTER_ID,LPR_ID,POSITION,RELATION_STATE_ID,RELATION_TYPE_ID) VALUES ('lprRosterEntry1',null,null,null,null,null,null,null,null,'testLprRoster1','instructor1','1','kuali.roster.entry.state.active','kuali.lpr.roster.entry.type.automatic')
INSERT INTO KSEN_LPR_ROSTER_ENTRY (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,EFFECTIVEDATE,EXPIRATIONDATE,LPRROSTER_ID,LPR_ID,POSITION,RELATION_STATE_ID,RELATION_TYPE_ID) VALUES ('lprRosterEntry2',null,null,null,null,null,null,null,null,'testLprRoster1','student1','1','kuali.roster.entry.state.active','kuali.lpr.roster.entry.type.automatic')

INSERT INTO KSEN_LPR (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,EFFECTIVEDATE,EXPIRATIONDATE,LUIID,PERSONID,COMMITMENTPERCENT,RELATION_STATE_ID,RELATION_TYPE_ID) VALUES ('instructor1',null,0,'admin',{ts '2012-06-18 00:00:00'},'admin',{ts '2012-06-18 00:00:00'},{ts '2000-01-01 00:00:00'},{ts '2000-12-31 00:00:00'},'Lui-1','admin',1,'kuali.lpr.state.assigned','kuali.lpr.type.instructor.main')
INSERT INTO KSEN_LPR (ID,OBJ_ID,VER_NBR,CREATEID,CREATETIME,UPDATEID,UPDATETIME,EFFECTIVEDATE,EXPIRATIONDATE,LUIID,PERSONID,COMMITMENTPERCENT,RELATION_STATE_ID,RELATION_TYPE_ID) VALUES ('student1',null,0,'admin',{ts '2012-06-18 00:00:00'},'admin',{ts '2012-06-18 00:00:00'},{ts '2000-01-01 00:00:00'},{ts '2000-12-31 00:00:00'},'Lui-1','testPersonId1',1,'kuali.lpr.state.registered','kuali.lpr.type.registrant')



