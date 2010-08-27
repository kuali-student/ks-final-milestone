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

// ---------------------------------------------------------------------------
// |   Statement, Requirement Component and Natural Language Configuration   |
// ---------------------------------------------------------------------------

//STATEMENT TYPES
// Course
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.statement.type.course', 'Rules used in the evaluation of a person''s academic readiness for enrollment in an LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Overall Academic Readiness Rules')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.statement.type.course.academicReadiness.prereq', 'Pre req rules used in the evaluation of a person''s academic readiness for enrollment in an LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Academic Readiness Pre Reqs')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.statement.type.course.academicReadiness.coreq', 'Co req used in the evaluation of a person''s academic readiness for enrollment in an LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Academic Readiness Co Reqs')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.statement.type.course.academicReadiness.studentEligibility', 'Enrollment req used in the evaluation of a person''s academic readiness for enrollment in an LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Academic Readiness Enroll Reqs')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.statement.type.course.academicReadiness.antireq', 'Anti req used in the evaluation of a person''s academic readiness for enrollment in an LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Academic Readiness Anti Reqs')
// Program
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.statement.type.program', 'Rules used in the evaluation of a person''s academic readiness for acceptance into a program (e.g. major).', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Program Academic Readiness Requirements')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.statement.type.program.entrance', 'Rules used in the evaluation of a person''s entry into a program (e.g. major).', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Program Entrance Requirements')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.statement.type.program.satisfactoryProgress', 'Rules used in maintaining minimum scholarship standards.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Program Satisfactory Progress Requirements')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.statement.type.program.completion', 'Rules used in the evaluation of a person''s program (e.g. major) completion.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Program Completion Requirements')

// STMT_TYPE <-> STMT_TYPE
// Course
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.statement.type.course','kuali.statement.type.course.academicReadiness.prereq')
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.statement.type.course','kuali.statement.type.course.academicReadiness.coreq')
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.statement.type.course','kuali.statement.type.course.academicReadiness.studentEligibility')
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.statement.type.course','kuali.statement.type.course.academicReadiness.antireq')
// Program
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.statement.type.program','kuali.statement.type.program.entrance')
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.statement.type.program','kuali.statement.type.program.satisfactoryProgress')
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.statement.type.program','kuali.statement.type.program.completion')

// REQUIREMENT TYPES
// Courses
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.courseset.completed.none', 'Must not have successfully completed any courses from <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'None of required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.courseset.completed.all', 'Must have successfully completed all courses from <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'All of required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.courseset.completed.nof', 'Must have successfully completed a minimum of <n> courses from <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'N of required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.courseset.enrolled.nof', 'Must be concurrently enrolled in a minimum of <n> courses from <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'N of required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.courseset.credits.completed.nof', 'Must have successfully completed a minimum of <n> credits from <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'N of credits from courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.courseset.credits.completed.none', 'Must not have successfully completed any credits from <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'None credits from courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.courseset.credits.completed.max', 'Must successfully complete no more than  <n> credits from <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Max credit from courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.courseset.gpa.min', 'Must have earned a minimum GPA of <GPA> in <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2001-11-30 00:00:00.0'}, 'Minimum overall GPA')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.courseList.coreq.all', 'Must be enrolled in all of the following courses', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'All of required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.courseList.coreq.oneof', 'Must be enrolled in one of the following courses', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'One of required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.programList.enroll.oneof', 'Enrollment is limited to students in the following programs', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'One of required programs')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.programList.enroll.none', 'Enrollment is not available to students in the following programs', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'None of required programs')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.courseset.grade.min', 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Grade in required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.courseset.grade.max', 'Must not have earned a grade of <gradeType> <grade> or higher in <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Grade in required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.permission.org.required', 'Permission of <administering org> required', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Org perm required')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.permission.instructor.required', 'Permission of <instructor> required', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Instructor perm required')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.test.score.min', 'Must have achieved a minimum score of <score> on <tests>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Minimum score on test')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.test.score.max', 'Must have achieved a score no higher than <score> on <test> ', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Maximum score on test')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.courseset.nof.grade.min', 'Must successfully complete a minimum of <n> courses  from <courses> with a minimum grade of <gradeType> <grade>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Min grade in required courses')
// Programs
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.program.programset.completed.nof', 'Must have successfully completed a minimum of <n> programs from <programs> programs', null, null, 'Minimum of n programs from programs')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.program.programset.notcompleted.nof', 'Must not have successfully completed any of <programs> programs', null, null, 'Not completed any programs')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.program.programset.completed.all', 'Must have successfully completed all of <programs> programs', null, null, 'All of required programs')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.program.programset.coursecompleted.nof', 'Must have successfully completed a minimum of <n> courses from <programs> programs', null, null, 'Minimum courses from programs')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.program.admitted.credits', 'Must be admitted to program prior to earning <n> credits', null, null, 'Earning n credits')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.program.credits.min', 'Must have earned a minimum of <n> total credits', null, null, 'Earned minimum n credits')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.program.credits.max', 'Must not have earned more than <n> credits', null, null, 'Earned more than n credits')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.program.completion.duration', 'Must not exceed <n> <duration> without completing program', null, null, 'Not exceed n duration without completing program')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.program.candidate.status.duration', 'Must attain candidate status within <n> <duration> after program entry term', null, null, 'Candidate status within duration after program entry')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.program.completion.duration.afterentry', 'Must complete program within <n> <durations> after program entry term', null, null, 'Complete program within duration after program entry')

// REQUIREMENT FIELD TYPES
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('kuali.reqComponent.field.type.clu.id', 'CLU', 'CLU','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('kuali.reqComponent.field.type.cluSet.id', 'CLUSET', 'CLUSET','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('kuali.reqComponent.field.type.requiredCount', 'REQUIRED_COUNT', 'Required Count','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('kuali.reqComponent.field.type.gpa', 'GPA', 'GPA','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('kuali.reqComponent.field.type.operator', 'CLU', 'CLU','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('kuali.reqComponent.field.type.countType', 'CLU', 'CLU','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('kuali.reqComponent.field.type.grade', 'GRADE', 'GRADE','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('kuali.reqComponent.field.type.gradeType', 'GRADETYPE', 'GRADETYPE','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('kuali.reqComponent.field.type.orgid', 'ORG', 'ORG','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('kuali.reqComponent.field.type.personid', 'PERSON', 'PERSON','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('kuali.reqComponent.field.type.test.score', 'TEST', 'TEST','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('kuali.reqComponent.field.type.durationType', 'DURATIONTYPE', 'DURATIONTYPE','string',null,null,null,null,null,null,null,null,0)

// REQ_COMP_TYPE -> REQ_COMP_FIELD_TYPE
// Courses
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.completed.none','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.completed.all','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.completed.nof','kuali.reqComponent.field.type.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.completed.nof','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.enrolled.nof','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.enrolled.nof','kuali.reqComponent.field.type.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.credits.completed.nof','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.credits.completed.nof','kuali.reqComponent.field.type.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.credits.completed.none','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.credits.completed.none','kuali.reqComponent.field.type.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.credits.completed.max','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.credits.completed.max','kuali.reqComponent.field.type.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.gpa.min','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.gpa.min','kuali.reqComponent.field.type.gpa')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.courseList.coreq.all','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.courseList.coreq.oneof','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.programList.enroll.oneof','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.programList.enroll.none','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.grade.min','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.grade.min','kuali.reqComponent.field.type.grade')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.grade.min','kuali.reqComponent.field.type.gradeType')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.grade.max','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.grade.max','kuali.reqComponent.field.type.grade')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.grade.max','kuali.reqComponent.field.type.gradeType')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.permission.org.required','kuali.reqComponent.field.type.orgid')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.permission.instructor.required','kuali.reqComponent.field.type.personid')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.test.score.min','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.test.score.min','kuali.reqComponent.field.type.test.score')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.test.score.max','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.test.score.max','kuali.reqComponent.field.type.test.score')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.nof.grade.min','kuali.reqComponent.field.type.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.nof.grade.min','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.nof.grade.min','kuali.reqComponent.field.type.grade')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.course.courseset.nof.grade.min','kuali.reqComponent.field.type.gradeType')
// Programs
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.programset.completed.nof','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.programset.completed.nof','kuali.reqComponent.field.type.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.programset.notcompleted.nof','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.programset.completed.all','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.programset.coursecompleted.nof','kuali.reqComponent.field.type.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.programset.coursecompleted.nof','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.admitted.credits','kuali.reqComponent.field.type.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.credits.min','kuali.reqComponent.field.type.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.credits.max','kuali.reqComponent.field.type.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.completion.duration','kuali.reqComponent.field.type.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.completion.duration','kuali.reqComponent.field.type.durationType')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.candidate.status.duration','kuali.reqComponent.field.type.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.candidate.status.duration','kuali.reqComponent.field.type.durationType')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.completion.duration.afterentry','kuali.reqComponent.field.type.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.completion.duration.afterentry','kuali.reqComponent.field.type.durationType')

// REQ_COMPONENT_TYPE_NL_TEMPLATE
// Courses
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1', 'KUALI.RULEEDIT', '#if($cluSet.getCluList().size() == 1)Must not have successfully completed $cluSet.getCluSetAsCode()#{else}Must not have successfully completed any courses from $cluSet.getCluSetAsCode()#end', 'kuali.reqComponent.type.course.courseset.completed.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2', 'KUALI.RULEEDIT', '#if($cluSet.getCluList().size() == 1)Must have successfully completed $cluSet.getCluSetAsCode()#{else}Must have successfully completed all courses from $cluSet.getCluSetAsCode()#end', 'kuali.reqComponent.type.course.courseset.completed.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3', 'KUALI.RULEEDIT', '#if($expectedValue == 1 && $cluSet.getCluList().size() == 1)Must have successfully completed $cluSet.getCluSetAsCode()#{else}Must have successfully completed a minimum of $expectedValue $NLHelper.getProperGrammar($expectedValue, "course") from $cluSet.getCluSetAsCode()#end', 'kuali.reqComponent.type.course.courseset.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('4', 'KUALI.RULEEDIT', 'Must be concurrently enrolled in a minimum of $expectedValue $NLHelper.getProperGrammar($expectedValue, "course") from $cluSet.getCluSetAsCode()', 'kuali.reqComponent.type.course.courseset.enrolled.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('5', 'KUALI.RULEEDIT', 'Must have successfully completed a minimum of $expectedValue $NLHelper.getProperGrammar($expectedValue, "credit") from $cluSet.getCluSetAsCode()', 'kuali.reqComponent.type.course.courseset.credits.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('6', 'KUALI.RULEEDIT', 'Must not have successfully completed any credits from $cluSet.getCluSetAsCode()', 'kuali.reqComponent.type.course.courseset.credits.completed.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('7', 'KUALI.RULEEDIT', 'Must successfully complete no more than $expectedValue $NLHelper.getProperGrammar($expectedValue, "credit") from $cluSet.getCluSetAsCode()', 'kuali.reqComponent.type.course.courseset.credits.completed.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('8', 'KUALI.RULEEDIT', 'Must have earned a minimum GPA of $gpa in $cluSet.getCluSetAsCode()', 'kuali.reqComponent.type.course.courseset.gpa.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('9', 'KUALI.RULEEDIT', 'Must be enrolled in all of $cluSet.getCluSetAsCode()', 'kuali.reqComponent.type.courseList.coreq.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('10', 'KUALI.RULEEDIT', 'Must be enrolled in one of of $cluSet.getCluSetAsCode()', 'kuali.reqComponent.type.courseList.coreq.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('11', 'KUALI.RULEEDIT', 'Restricted to students in the $cluSet.getCluSetAsCode() only', 'kuali.reqComponent.type.programList.enroll.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('12', 'KUALI.RULEEDIT', 'Not for students in $cluSet.getCluSetAsCode()', 'kuali.reqComponent.type.programList.enroll.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('13', 'KUALI.RULEEDIT', 'Must have earned a minimum grade of $gradeType $grade in $cluSet.getCluSetAsCode()', 'kuali.reqComponent.type.course.courseset.grade.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('14', 'KUALI.RULEEDIT', 'Must not have earned a maximum grade of $gradeType $grade or higher in $cluSet.getCluSetAsCode()', 'kuali.reqComponent.type.course.courseset.grade.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('15', 'KUALI.RULEEDIT', 'Permission of $orgid required', 'kuali.reqComponent.type.course.permission.org.required', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('16', 'KUALI.RULEEDIT', 'Permission of instructor required', 'kuali.reqComponent.type.course.permission.instructor.required', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('17', 'KUALI.RULEEDIT', 'Must have achieved a minimum score of $fields.get(''kuali.reqComponent.field.type.test.score'') on $cluSet.getCluSetAsShortName()', 'kuali.reqComponent.type.course.test.score.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('18', 'KUALI.RULEEDIT', 'Must have achieved a score no higher than $fields.get(''kuali.reqComponent.field.type.test.score'') on $cluSet.getCluSetAsShortName()', 'kuali.reqComponent.type.course.test.score.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('19', 'KUALI.RULEEDIT', 'Must successfully complete a minimum of $expectedValue $NLHelper.getProperGrammar($expectedValue, "course") from $cluSet.getCluSetAsCode() with a minimum grade of $gradeType $grade', 'kuali.reqComponent.type.course.courseset.nof.grade.min', 'en')

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('101', 'KUALI.EXAMPLE', 'Must not have successfully completed any courses from (MATH111, 140, 220, or STAT100)', 'kuali.reqComponent.type.course.courseset.completed.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('102', 'KUALI.EXAMPLE', 'Must have successfully completed all courses from (MATH111, 140, 220, and STAT100)', 'kuali.reqComponent.type.course.courseset.completed.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('103', 'KUALI.EXAMPLE', 'Must have successfully completed a minimum of 1 course from (MATH111, 140, 220, or STAT100)', 'kuali.reqComponent.type.course.courseset.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('104', 'KUALI.EXAMPLE', 'Must be concurrently enrolled in a minimum of 1 course from (MATH111, 140, 220, or STAT100)', 'kuali.reqComponent.type.course.courseset.enrolled.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('105', 'KUALI.EXAMPLE', 'Must have earned a minimum of 3 credits from (MATH111, 140, 220, or STAT100)', 'kuali.reqComponent.type.course.courseset.credits.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('106', 'KUALI.EXAMPLE', 'No credits may be used from (Developmental Math courses (MATH001, 002, 003, 010, 011, 013 or 015)', 'kuali.reqComponent.type.course.courseset.credits.completed.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('107', 'KUALI.EXAMPLE', 'Must not have earned more than 6 credits from (MATH111, 140, 220, and STAT100)', 'kuali.reqComponent.type.course.courseset.credits.completed.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('108', 'KUALI.EXAMPLE', 'Must have earned a minimum GPA of 2.00 in (MATH111, 140, 220, and  STAT100)', 'kuali.reqComponent.type.course.courseset.gpa.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('109', 'KUALI.EXAMPLE', 'Must be enrolled in all of MATH100, MATH101', 'kuali.reqComponent.type.courseList.coreq.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('110', 'KUALI.EXAMPLE', 'Must be enrolled in one of MATH100, MATH101, MATH102', 'kuali.reqComponent.type.courseList.coreq.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('111', 'KUALI.EXAMPLE', 'Must be in one of the following program(s): Creative Writing Major', 'kuali.reqComponent.type.programList.enroll.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('112', 'KUALI.EXAMPLE', 'Not for students in a Music Major or Minor', 'kuali.reqComponent.type.programList.enroll.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('113', 'KUALI.EXAMPLE', 'Must have earned a minimum grade of letter B in (MATH111, 140, 220, and STAT100)', 'kuali.reqComponent.type.course.courseset.grade.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('114', 'KUALI.EXAMPLE', 'Must not have earned a maximum grade of letter C or higher in (MATH111, 140, 220, and STAT100)', 'kuali.reqComponent.type.course.courseset.grade.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('115', 'KUALI.EXAMPLE', 'Permission of English Department Required', 'kuali.reqComponent.type.course.permission.org.required', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('116', 'KUALI.EXAMPLE', 'Permission of instructor required', 'kuali.reqComponent.type.course.permission.instructor.required', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('117', 'KUALI.EXAMPLE', 'Must have achieved a minimum score of 600 on SAT Critical Reading Exam', 'kuali.reqComponent.type.course.test.score.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('118', 'KUALI.EXAMPLE', 'Must have achieved a score no higher than 5 on AP Japanese', 'kuali.reqComponent.type.course.test.score.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('119', 'KUALI.EXAMPLE', 'Must have successfully completed a minimum of 1 course from (MATH111, 140, 220, or STAT100) with a minimum grade of letter B', 'kuali.reqComponent.type.course.courseset.nof.grade.min', 'en')

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('201', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.course.courseset.completed.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('202', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.course.courseset.completed.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('203', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.requiredCount;reqCompFieldLabel=Number> from <reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.course.courseset.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('204', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.requiredCount;reqCompFieldLabel=Number> from <reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.course.courseset.enrolled.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('205', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.requiredCount;reqCompFieldLabel=Credits> from <reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.course.courseset.credits.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('206', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.course.courseset.credits.completed.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('207', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Courses;reqCompFieldLabel=Credits> from <reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.course.courseset.credits.completed.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('208', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.gpa;reqCompFieldLabel=GPA> from <reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.course.courseset.gpa.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('209', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.courseList.coreq.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('210', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.courseList.coreq.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('211', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Programs>', 'kuali.reqComponent.type.programList.enroll.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('212', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Programs>', 'kuali.reqComponent.type.programList.enroll.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('213', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.gradeType;reqCompFieldLabel=Grade Type> of <reqCompFieldType=kuali.reqComponent.field.type.grade;reqCompFieldLabel=Grade> in <reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.course.courseset.grade.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('214', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.gradeType;reqCompFieldLabel=Grade Type> of <reqCompFieldType=kuali.reqComponent.field.type.grade;reqCompFieldLabel=Grade> in <reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.course.courseset.grade.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('215', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.orgid;reqCompFieldLabel=Organization>', 'kuali.reqComponent.type.course.permission.org.required', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('216', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.personid;reqCompFieldLabel=Instructor>', 'kuali.reqComponent.type.course.permission.instructor.required', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('217', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.test.score;reqCompFieldLabel=Test Score> from <reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Tests>', 'kuali.reqComponent.type.course.test.score.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('218', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.test.score;reqCompFieldLabel=Test Score> from <reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Tests>', 'kuali.reqComponent.type.course.test.score.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('219', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.requiredCount;reqCompFieldLabel=Number> from <reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Courses> with <reqCompFieldType=kuali.reqComponent.field.type.gradeType;reqCompFieldLabel=Grade Type> of <reqCompFieldType=kuali.reqComponent.field.type.grade;reqCompFieldLabel=Grade>', 'kuali.reqComponent.type.course.courseset.nof.grade.min', 'en')

// Programs
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1001', 'KUALI.RULEEDIT', 'Must have successfully completed a minimum of $expectedValue $NLHelper.getProperGrammar($expectedValue, "program") from $cluSet.getCluSetAsShortName() $NLHelper.getProperGrammar($cluSet.getCluList().size(), "program")', 'kuali.reqComponent.type.program.programset.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1002', 'KUALI.RULEEDIT', 'Must not have successfully completed #if($cluSet.getCluList().size() > 1)any of #end$cluSet.getCluSetAsShortName() $NLHelper.getProperGrammar($cluSet.getCluList().size(), "program")', 'kuali.reqComponent.type.program.programset.notcompleted.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1003', 'KUALI.RULEEDIT', 'Must have successfully completed #if($cluSet.getCluList().size() != 1)all of #end$cluSet.getCluSetAsShortName() $NLHelper.getProperGrammar($cluSet.getCluList().size(), "program")', 'kuali.reqComponent.type.program.programset.completed.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1004', 'KUALI.RULEEDIT', 'Must have successfully completed a minimum of $expectedValue $NLHelper.getProperGrammar($expectedValue, "course") from $cluSet.getCluSetAsShortName() $NLHelper.getProperGrammar($cluSet.getCluList().size(), "program")', 'kuali.reqComponent.type.program.programset.coursecompleted.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1005', 'KUALI.RULEEDIT', 'Must be admitted to program prior to earning $expectedValue $NLHelper.getProperGrammar($expectedValue, "credit")', 'kuali.reqComponent.type.program.admitted.credits', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1006', 'KUALI.RULEEDIT', 'Must have earned a minimum of $expectedValue total $NLHelper.getProperGrammar($expectedValue, "credit")', 'kuali.reqComponent.type.program.credits.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1007', 'KUALI.RULEEDIT', 'Must not have earned more than $expectedValue $NLHelper.getProperGrammar($expectedValue, "credit")', 'kuali.reqComponent.type.program.credits.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1008', 'KUALI.RULEEDIT', 'Must not exceed $expectedValue $durationType without completing program', 'kuali.reqComponent.type.program.completion.duration', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1009', 'KUALI.RULEEDIT', 'Must attain candidate status within $expectedValue $durationType after program entry term', 'kuali.reqComponent.type.program.candidate.status.duration', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1010', 'KUALI.RULEEDIT', 'Must complete program within $expectedValue $durationType after program entry term', 'kuali.reqComponent.type.program.completion.duration.afterentry', 'en')

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2001', 'KUALI.EXAMPLE', 'Must have successfully completed a minimum of 1 program from (Geology or  Sociology) programs', 'kuali.reqComponent.type.program.programset.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2002', 'KUALI.EXAMPLE', 'Must not have successfully completed any of (Geology or Sociology) programs', 'kuali.reqComponent.type.program.programset.notcompleted.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2003', 'KUALI.EXAMPLE', 'Must have successfully completed all of (Sociology and CORE Advanced Studies)', 'kuali.reqComponent.type.program.programset.completed.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2004', 'KUALI.EXAMPLE', 'Must have successfully completed a minimum of 14 courses from ( Sociology and CORE Advanced Studies) programs', 'kuali.reqComponent.type.program.programset.coursecompleted.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2005', 'KUALI.EXAMPLE', 'Must be admitted to program prior to earning 60 credits', 'kuali.reqComponent.type.program.admitted.credits', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2006', 'KUALI.EXAMPLE', 'Must have earned a minimum of 120 total credits', 'kuali.reqComponent.type.program.credits.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2007', 'KUALI.EXAMPLE', 'Must not have earned more than 130 total credits', 'kuali.reqComponent.type.program.credits.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2008', 'KUALI.EXAMPLE', 'Must not exceed 10 semesters without completing program', 'kuali.reqComponent.type.program.completion.duration', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2009', 'KUALI.EXAMPLE', 'Must attain candidate status within 3 semesters after program entry term', 'kuali.reqComponent.type.program.candidate.status.duration', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2010', 'KUALI.EXAMPLE', 'Must complete program within 10 semesters after program entry term', 'kuali.reqComponent.type.program.completion.duration.afterentry', 'en')

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3001', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.requiredCount;reqCompFieldLabel=Number> from <reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Programs>', 'kuali.reqComponent.type.program.programset.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3002', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Programs>', 'kuali.reqComponent.type.program.programset.notcompleted.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3003', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Programs>', 'kuali.reqComponent.type.program.programset.completed.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3004', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.requiredCount;reqCompFieldLabel=Number> from <reqCompFieldType=kuali.reqComponent.field.type.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqComponent.type.program.programset.coursecompleted.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3005', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.requiredCount;reqCompFieldLabel=Credits>', 'kuali.reqComponent.type.program.admitted.credits', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3006', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.requiredCount;reqCompFieldLabel=Number>', 'kuali.reqComponent.type.program.credits.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3007', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.requiredCount;reqCompFieldLabel=Number>', 'kuali.reqComponent.type.program.credits.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3008', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.requiredCount;reqCompFieldLabel=Number>', 'kuali.reqComponent.type.program.completion.duration', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3009', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.requiredCount;reqCompFieldLabel=Number>', 'kuali.reqComponent.type.program.candidate.status.duration', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3010', 'KUALI.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.requiredCount;reqCompFieldLabel=Number>', 'kuali.reqComponent.type.program.completion.duration.afterentry', 'en')

// STMT_TYPE <-> REQ_COM_TYPE
// Courses
// Courses - Anti-req
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.antireq','kuali.reqComponent.type.course.courseset.credits.completed.none')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.antireq','kuali.reqComponent.type.course.courseset.credits.completed.max')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.antireq','kuali.reqComponent.type.course.courseset.completed.none')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.antireq','kuali.reqComponent.type.course.test.score.max')
// Courses - Pre-req
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.prereq','kuali.reqComponent.type.course.courseset.completed.all')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.prereq','kuali.reqComponent.type.course.courseset.completed.nof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.prereq','kuali.reqComponent.type.course.courseset.credits.completed.nof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.prereq','kuali.reqComponent.type.course.courseset.gpa.min')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.prereq','kuali.reqComponent.type.course.courseset.grade.min')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.prereq','kuali.reqComponent.type.course.courseset.grade.max')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.prereq','kuali.reqComponent.type.course.permission.org.required')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.prereq','kuali.reqComponent.type.course.permission.instructor.required')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.prereq','kuali.reqComponent.type.course.test.score.min')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.prereq','kuali.reqComponent.type.course.courseset.nof.grade.min')
// Courses - Co-req
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.coreq','kuali.reqComponent.type.courseList.coreq.all')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.coreq','kuali.reqComponent.type.courseList.coreq.oneof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.coreq','kuali.reqComponent.type.course.courseset.enrolled.nof')
// Courses - Enroll
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.studentEligibility','kuali.reqComponent.type.programList.enroll.oneof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.course.academicReadiness.studentEligibility','kuali.reqComponent.type.programList.enroll.none')
// Program Satisfactory Progress
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.satisfactoryProgress','kuali.reqComponent.type.program.admitted.credits')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.satisfactoryProgress','kuali.reqComponent.type.program.credits.max')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.satisfactoryProgress','kuali.reqComponent.type.program.completion.duration')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.satisfactoryProgress','kuali.reqComponent.type.program.candidate.status.duration')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.satisfactoryProgress','kuali.reqComponent.type.course.test.score.min')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.satisfactoryProgress','kuali.reqComponent.type.course.courseset.nof.grade.min')
// Program Entrance
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.entrance','kuali.reqComponent.type.program.programset.completed.nof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.entrance','kuali.reqComponent.type.program.programset.notcompleted.nof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.entrance','kuali.reqComponent.type.program.programset.completed.all')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.entrance','kuali.reqComponent.type.program.programset.coursecompleted.nof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.entrance','kuali.reqComponent.type.course.courseset.gpa.min')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.entrance','kuali.reqComponent.type.course.test.score.min')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.entrance','kuali.reqComponent.type.program.admitted.credits')
// Program Completion
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.completion','kuali.reqComponent.type.program.programset.completed.nof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.completion','kuali.reqComponent.type.program.programset.completed.all')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.completion','kuali.reqComponent.type.program.programset.coursecompleted.nof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.completion','kuali.reqComponent.type.course.courseset.gpa.min')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.completion','kuali.reqComponent.type.program.credits.min')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.completion','kuali.reqComponent.type.course.test.score.min')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.statement.type.program.completion','kuali.reqComponent.type.program.credits.max')


// KSST_NL_USAGE_TYPE
INSERT INTO KSST_NL_USAGE_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('KUALI.CATALOG', 'Kuali Catalog', 'Kuali Catalog', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'})
INSERT INTO KSST_NL_USAGE_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('KUALI.EXAMPLE', 'Kuali Rule Example', 'Rule Example', {ts '2010-01-01 01:01:01.0'}, {ts '2010-12-31 01:01:01.0'})
INSERT INTO KSST_NL_USAGE_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('KUALI.COMPOSITION', 'Kuali Rule Composition', 'Rule Composition', {ts '2010-01-01 01:01:01.0'}, {ts '2010-12-31 01:01:01.0'})
INSERT INTO KSST_NL_USAGE_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('KUALI.RULEEDIT', 'Kuali Rule Editing', 'Rule Edit', {ts '2010-01-01 01:01:01.0'}, {ts '2010-12-31 01:01:01.0'})
INSERT INTO KSST_NL_USAGE_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('KUALI.RULEVIEW', 'Kuali Rule Viewing', 'Rule View', {ts '2010-01-01 01:01:01.0'}, {ts '2010-12-31 01:01:01.0'})

// KSST_OBJECT_TYPE
INSERT INTO KSST_OBJECT_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('clu', 'Kuali CLU', 'Kuali CLU', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})

// KSST_OBJECT_SUB_TYPE
INSERT INTO KSST_OBJECT_SUB_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('course', 'Kuali Course', 'Kuali Course', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_OBJECT_SUB_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('program', 'Kuali Program', 'Kuali Program', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})

// KSST_OBJ_TYP_JN_OBJ_SUB_TYP
INSERT INTO KSST_OBJ_TYP_JN_OBJ_SUB_TYP (OBJ_TYPE_ID, OBJ_SUB_TYPE_ID) VALUES ('clu', 'course')
INSERT INTO KSST_OBJ_TYP_JN_OBJ_SUB_TYP (OBJ_TYPE_ID, OBJ_SUB_TYPE_ID) VALUES ('clu', 'program')

// KSST_REF_STMT_REL_TYPE
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('clu.prerequisites', 'CLU Prerequisites', 'CLU Prereq', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('kuali.statement.type.course', 'CLU Create Course', 'CLU Create Course', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('kuali.statement.type.course.academicReadiness.prereq', 'CLU Prerequisites', 'CLU Prerequisites', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('kuali.statement.type.course.academicReadiness.coreq', 'CLU Corequisites', 'CLU Corequisites', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('kuali.statement.type.course.academicReadiness.studentEligibility', 'CLU Enroll', 'CLU Enroll', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('kuali.statement.type.course.academicReadiness.antireq', 'CLU Antirequisites', 'CLU Antirequisites', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('clu.rule', 'CLU Rules', 'CLU Rules', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('course.rule', 'Course Rules', 'Course Rules', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})

// KSST_RSTMT_RTYP_JN_OSUB_TYP <-> KSST_OBJECT_SUB_TYPE
INSERT INTO KSST_RSTMT_RTYP_JN_OSUB_TYP (REF_STMT_REL_TYPE_ID, OBJ_SUB_TYPE_ID) VALUES ('clu.prerequisites', 'course')

// KSST_RSTMT_RTYP_JN_STMT_TYP <-> KSST_STMT_TYPE
INSERT INTO KSST_RSTMT_RTYP_JN_STMT_TYP (REF_STMT_REL_TYPE_ID, STMT_TYPE_ID) VALUES ('clu.prerequisites', 'kuali.statement.type.course.academicReadiness.prereq')
