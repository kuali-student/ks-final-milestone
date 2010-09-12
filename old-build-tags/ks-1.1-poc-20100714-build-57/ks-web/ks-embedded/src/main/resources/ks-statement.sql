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
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.luStatementType.course', 'Rules used in the evaluation of a person''s academic readiness for enrollment in an LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Overall Academic Readiness Rules')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.luStatementType.prereqAcademicReadiness', 'Pre req rules used in the evaluation of a person''s academic readiness for enrollment in an LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Academic Readiness Pre Reqs')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.luStatementType.coreqAcademicReadiness', 'Co req used in the evaluation of a person''s academic readiness for enrollment in an LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Academic Readiness Co Reqs')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.luStatementType.enrollAcademicReadiness', 'Enrollment req used in the evaluation of a person''s academic readiness for enrollment in an LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Academic Readiness Enroll Reqs')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.luStatementType.antireqAcademicReadiness', 'Anti req used in the evaluation of a person''s academic readiness for enrollment in an LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Academic Readiness Anti Reqs')
// Program
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.luStatementType.program', 'Rules used in the evaluation of a person''s academic readiness for acceptance into a program (e.g. major).', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Program Academic Readiness Rules')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.luStatementType.programEntrance', 'Rules used in the evaluation of a person''s entry into a program (e.g. major).', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Program Entry Rules')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.luStatementType.programSatisfactoryProgress', 'Rules used in maintaining minimum scholarship standards.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Program Satisfactory Progress Rules')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.luStatementType.programCompletion', 'Rules used in the evaluation of a person''s program (e.g. major) completion.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Program Completion Rules')

// STMT_TYPE <-> STMT_TYPE
// Course
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.luStatementType.course','kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.luStatementType.course','kuali.luStatementType.coreqAcademicReadiness')
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.luStatementType.course','kuali.luStatementType.enrollAcademicReadiness')
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.luStatementType.course','kuali.luStatementType.antireqAcademicReadiness')
// Program
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.luStatementType.program','kuali.luStatementType.programEntrance')
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.luStatementType.program','kuali.luStatementType.programSatisfactoryProgress')
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.luStatementType.program','kuali.luStatementType.programCompletion')

// REQUIREMENT TYPES
// Courses
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.course.courseset.completed.none', 'Must not have successfully completed any courses from <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'None of required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.course.courseset.completed.all', 'Must have successfully completed all courses from <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'All of required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.course.courseset.completed.nof', 'Must have successfully completed a minimum of <n> courses from <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'N of required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.course.courseset.enrolled.nof', 'Must be concurrently enrolled in a minimum of <n> courses from <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'N of required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.course.courseset.credits.completed.nof', 'Must have successfully completed a minimum of <n> credits from <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'N of credits from courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.course.courseset.credits.completed.none', 'Must not have successfully completed any credits from <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'None credits from courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.course.courseset.credits.completed.max', 'Must successfully complete no more than  <n> credits from <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Max credit from courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.course.courseset.gpa.min', 'Must have earned a minimum GPA of <GPA> in <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2001-11-30 00:00:00.0'}, 'Minimum overall GPA')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.courseList.coreq.all', 'Must be enrolled in all of the following courses', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'All of required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.courseList.coreq.oneof', 'Must be enrolled in one of the following courses', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'One of required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.programList.enroll.oneof', 'Enrollment is limited to students in the following programs', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'One of required programs')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.programList.enroll.none', 'Enrollment is not available to students in the following programs', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'None of required programs')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.course.courseset.grade.min', 'Must have earned a minimum grade of <gradeType> <grade> in <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Grade in required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.course.courseset.grade.max', 'Must not have earned a grade of <gradeType> <grade> or higher in <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Grade in required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.course.permission.org.required', 'Permission of <administering org> required', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Org perm required')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.course.permission.instructor.required', 'Permission of <instructor> required', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Instructor perm required')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.course.test.score.min', 'Must have achieved a minimum score of <score> on <tests>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Minimum score on test')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.course.test.score.max', 'Must have achieved a score no higher than <score> on <test> ', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Maximum score on test')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.course.courseset.nof.grade.min', 'Must successfully complete a minimum of <n> courses  from <courses> with a minimum grade of <gradeType> <grade>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Min grade in required courses')
// Programs
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.program.programset.completed.nof', 'Must have successfully completed a minimum of <n> programs from <programs> programs', null, null, 'All of required programs')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.program.programset.notcompleted.nof', 'Must not have successfully completed any of <programs> programs', null, null, 'All of required programs')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.program.programset.completed.all', 'Must have successfully completed all of <programs> programs ', null, null, 'All of required programs')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.program.programset.coursecompleted.nof', 'Must have successfully completed a minimum of <n> courses from <programs> programs', null, null, 'All of required programs')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.program.admitted.credits', 'Must be admitted to program prior to earning <n> credits', null, null, 'Earning n credits')

// REQUIREMENT FIELD TYPES
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.clu.id', 'CLU', 'CLU','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.cluSet.id', 'CLUSET', 'CLUSET','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.requiredCount', 'REQUIRED_COUNT', 'Required Count','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.gpa', 'GPA', 'GPA','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.operator', 'CLU', 'CLU','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.countType', 'CLU', 'CLU','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.grade', 'GRADE', 'GRADE','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.gradeType', 'GRADETYPE', 'GRADETYPE','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.orgid', 'ORG', 'ORG','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.personid', 'PERSON', 'PERSON','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.test.score', 'TEST', 'TEST','string',null,null,null,null,null,null,null,null,0)

// REQ_COMP_TYPE -> REQ_COMP_FIELD_TYPE
// Courses
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.completed.none','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.completed.all','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.completed.nof','reqCompFieldType.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.completed.nof','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.enrolled.nof','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.enrolled.nof','reqCompFieldType.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.credits.completed.nof','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.credits.completed.nof','reqCompFieldType.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.credits.completed.none','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.credits.completed.none','reqCompFieldType.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.credits.completed.max','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.credits.completed.max','reqCompFieldType.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.gpa.min','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.gpa.min','reqCompFieldType.gpa')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.courseList.coreq.all','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.courseList.coreq.oneof','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.programList.enroll.oneof','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.programList.enroll.none','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.grade.min','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.grade.min','reqCompFieldType.grade')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.grade.min','reqCompFieldType.gradeType')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.grade.max','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.grade.max','reqCompFieldType.grade')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.grade.max','reqCompFieldType.gradeType')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.permission.org.required','reqCompFieldType.orgid')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.permission.instructor.required','reqCompFieldType.personid')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.test.score.min','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.test.score.min','reqCompFieldType.test.score')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.test.score.max','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.test.score.max','reqCompFieldType.test.score')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.nof.grade.min','reqCompFieldType.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.nof.grade.min','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.nof.grade.min','reqCompFieldType.grade')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.course.courseset.nof.grade.min','reqCompFieldType.gradeType')
// Programs
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.program.programset.completed.nof','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.program.programset.completed.nof','reqCompFieldType.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.program.programset.notcompleted.nof','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.program.programset.completed.all','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.program.programset.coursecompleted.nof','reqCompFieldType.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.program.programset.coursecompleted.nof','reqCompFieldType.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.program.admitted.credits','reqCompFieldType.requiredCount')

// REQ_COMPONENT_TYPE_NL_TEMPLATE
// Courses
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1', 'KUALI.CATALOG', '#if($cluSet.getCluList().size() == 1)Must not have successfully completed $cluSet.getCluSetAsCode()#{else}Must not have successfully completed any courses from $cluSet.getCluSetAsCode()#end', 'kuali.reqCompType.course.courseset.completed.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2', 'KUALI.CATALOG', '#if($cluSet.getCluList().size() == 1)Must have successfully completed $cluSet.getCluSetAsCode()#{else}Must have successfully completed all courses from $cluSet.getCluSetAsCode()#end', 'kuali.reqCompType.course.courseset.completed.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3', 'KUALI.CATALOG', '#if($expectedValue == 1 && $cluSet.getCluList().size() == 1)Must have successfully completed $cluSet.getCluSetAsCode()#{else}Must have successfully completed a minimum of $expectedValue $NLHelper.getProperGrammar($expectedValue, "course") from $cluSet.getCluSetAsCode()#end', 'kuali.reqCompType.course.courseset.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('4', 'KUALI.CATALOG', 'Must be concurrently enrolled in a minimum of $expectedValue $NLHelper.getProperGrammar($expectedValue, "course") from $cluSet.getCluSetAsCode()', 'kuali.reqCompType.course.courseset.enrolled.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('5', 'KUALI.CATALOG', 'Must have successfully completed a minimum of $expectedValue $NLHelper.getProperGrammar($expectedValue, "credit") from $cluSet.getCluSetAsCode()', 'kuali.reqCompType.course.courseset.credits.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('6', 'KUALI.CATALOG', 'Must not have successfully completed any credits from $cluSet.getCluSetAsCode()', 'kuali.reqCompType.course.courseset.credits.completed.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('7', 'KUALI.CATALOG', 'Must successfully complete no more than $expectedValue $NLHelper.getProperGrammar($expectedValue, "credit") from $cluSet.getCluSetAsCode()', 'kuali.reqCompType.course.courseset.credits.completed.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('8', 'KUALI.CATALOG', 'Must have earned a minimum GPA of $gpa in $cluSet.getCluSetAsCode()', 'kuali.reqCompType.course.courseset.gpa.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('9', 'KUALI.CATALOG', 'Must be enrolled in all of $cluSet.getCluSetAsCode()', 'kuali.reqCompType.courseList.coreq.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('10', 'KUALI.CATALOG', 'Must be enrolled in one of of $cluSet.getCluSetAsCode()', 'kuali.reqCompType.courseList.coreq.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('11', 'KUALI.CATALOG', 'Restricted to students in the $cluSet.getCluSetAsCode() only', 'kuali.reqCompType.programList.enroll.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('12', 'KUALI.CATALOG', 'Not for students in $cluSet.getCluSetAsCode()', 'kuali.reqCompType.programList.enroll.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('13', 'KUALI.CATALOG', 'Must have earned a minimum grade of $gradeType $grade in $cluSet.getCluSetAsCode()', 'kuali.reqCompType.course.courseset.grade.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('14', 'KUALI.CATALOG', 'Must not have earned a maximum grade of $gradeType $grade or higher in $cluSet.getCluSetAsCode()', 'kuali.reqCompType.course.courseset.grade.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('15', 'KUALI.CATALOG', 'Permission of $orgid required', 'kuali.reqCompType.course.permission.org.required', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('16', 'KUALI.CATALOG', 'Permission of instructor required', 'kuali.reqCompType.course.permission.instructor.required', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('17', 'KUALI.CATALOG', 'Must have achieved a minimum score of $fields.get(''reqCompFieldType.test.score'') on $cluSet.getCluSetAsShortName()', 'kuali.reqCompType.course.test.score.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('18', 'KUALI.CATALOG', 'Must have achieved a score no higher than $fields.get(''reqCompFieldType.test.score'') on $cluSet.getCluSetAsShortName()', 'kuali.reqCompType.course.test.score.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('19', 'KUALI.CATALOG', 'Must successfully complete a minimum of $expectedValue $NLHelper.getProperGrammar($expectedValue, "course") from $cluSet.getCluSetAsCode() with a minimum grade of $gradeType $grade', 'kuali.reqCompType.course.courseset.nof.grade.min', 'en')

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('101', 'KUALI.EXAMPLE', 'Must not have successfully completed any courses from (MATH111, 140, 220, or STAT100)', 'kuali.reqCompType.course.courseset.completed.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('102', 'KUALI.EXAMPLE', 'Must have successfully completed all courses from (MATH111, 140, 220, and STAT100)', 'kuali.reqCompType.course.courseset.completed.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('103', 'KUALI.EXAMPLE', 'Must have successfully completed a minimum of 1 course from (MATH111, 140, 220, or STAT100)', 'kuali.reqCompType.course.courseset.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('104', 'KUALI.EXAMPLE', 'Must be concurrently enrolled in a minimum of 1 course from (MATH111, 140, 220, or STAT100)', 'kuali.reqCompType.course.courseset.enrolled.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('105', 'KUALI.EXAMPLE', 'Must have earned a minimum of 3 credits from (MATH111, 140, 220, or STAT100)', 'kuali.reqCompType.course.courseset.credits.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('106', 'KUALI.EXAMPLE', 'No credits may be used from (Developmental Math courses (MATH001, 002, 003, 010, 011, 013 or 015)', 'kuali.reqCompType.course.courseset.credits.completed.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('107', 'KUALI.EXAMPLE', 'Must not have earned more than 6 credits from (MATH111, 140, 220, and STAT100)', 'kuali.reqCompType.course.courseset.credits.completed.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('108', 'KUALI.EXAMPLE', 'Must have earned a minimum GPA of 2.00 in (MATH111, 140, 220, and  STAT100)', 'kuali.reqCompType.course.courseset.gpa.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('109', 'KUALI.EXAMPLE', 'Must be enrolled in all of MATH100, MATH101', 'kuali.reqCompType.courseList.coreq.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('110', 'KUALI.EXAMPLE', 'Must be enrolled in one of MATH100, MATH101, MATH102', 'kuali.reqCompType.courseList.coreq.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('111', 'KUALI.EXAMPLE', 'Must be in one of the following program(s): Creative Writing Major', 'kuali.reqCompType.programList.enroll.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('112', 'KUALI.EXAMPLE', 'Not for students in a Music Major or Minor', 'kuali.reqCompType.programList.enroll.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('113', 'KUALI.EXAMPLE', 'Must have earned a minimum grade of letter B in (MATH111, 140, 220, and STAT100)', 'kuali.reqCompType.course.courseset.grade.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('114', 'KUALI.EXAMPLE', 'Must not have earned a maximum grade of letter C or higher in (MATH111, 140, 220, and STAT100)', 'kuali.reqCompType.course.courseset.grade.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('115', 'KUALI.EXAMPLE', 'Permission of English Department Required', 'kuali.reqCompType.course.permission.org.required', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('116', 'KUALI.EXAMPLE', 'Permission of instructor required', 'kuali.reqCompType.course.permission.instructor.required', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('117', 'KUALI.EXAMPLE', 'Must have achieved a minimum score of 600 on SAT Critical Reading Exam', 'kuali.reqCompType.course.test.score.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('118', 'KUALI.EXAMPLE', 'Must have achieved a score no higher than 5 on AP Japanese', 'kuali.reqCompType.course.test.score.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('119', 'KUALI.EXAMPLE', 'Must have successfully completed a minimum of 1 course from (MATH111, 140, 220, or STAT100) with a minimum grade of letter B', 'kuali.reqCompType.course.courseset.nof.grade.min', 'en')

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('201', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqCompType.course.courseset.completed.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('202', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqCompType.course.courseset.completed.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('203', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.requiredCount;reqCompFieldLabel=Number> from <reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqCompType.course.courseset.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('204', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.requiredCount;reqCompFieldLabel=Number> from <reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqCompType.course.courseset.enrolled.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('205', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.requiredCount;reqCompFieldLabel=Credits> from <reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqCompType.course.courseset.credits.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('206', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqCompType.course.courseset.credits.completed.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('207', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Courses;reqCompFieldLabel=Credits> from <reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqCompType.course.courseset.credits.completed.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('208', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.gpa;reqCompFieldLabel=GPA> from <reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqCompType.course.courseset.gpa.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('209', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqCompType.courseList.coreq.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('210', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqCompType.courseList.coreq.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('211', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Programs>', 'kuali.reqCompType.programList.enroll.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('212', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Programs>', 'kuali.reqCompType.programList.enroll.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('213', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.gradeType;reqCompFieldLabel=Grade Type> of <reqCompFieldType=reqCompFieldType.grade;reqCompFieldLabel=Grade> in <reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqCompType.course.courseset.grade.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('214', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.gradeType;reqCompFieldLabel=Grade Type> of <reqCompFieldType=reqCompFieldType.grade;reqCompFieldLabel=Grade> in <reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqCompType.course.courseset.grade.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('215', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.orgid;reqCompFieldLabel=Organization>', 'kuali.reqCompType.course.permission.org.required', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('216', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.personid;reqCompFieldLabel=Instructor>', 'kuali.reqCompType.course.permission.instructor.required', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('217', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.test.score;reqCompFieldLabel=Test Score> from <reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Tests>', 'kuali.reqCompType.course.test.score.min', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('218', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.test.score;reqCompFieldLabel=Test Score> from <reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Tests>', 'kuali.reqCompType.course.test.score.max', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('219', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.requiredCount;reqCompFieldLabel=Number> from <reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Courses> with <reqCompFieldType=reqCompFieldType.gradeType;reqCompFieldLabel=Grade Type> of <reqCompFieldType=reqCompFieldType.grade;reqCompFieldLabel=Grade>', 'kuali.reqCompType.course.courseset.nof.grade.min', 'en')

// Programs
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1001', 'KUALI.CATALOG', 'Must have successfully completed a minimum of $expectedValue $NLHelper.getProperGrammar($expectedValue, "program") from $cluSet.getCluSetAsShortName() $NLHelper.getProperGrammar($cluSet.getCluList().size(), "program")', 'kuali.reqCompType.program.programset.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1002', 'KUALI.CATALOG', 'Must not have successfully completed #if($cluSet.getCluList().size() > 1)any of #end$cluSet.getCluSetAsShortName() $NLHelper.getProperGrammar($cluSet.getCluList().size(), "program")', 'kuali.reqCompType.program.programset.notcompleted.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1003', 'KUALI.CATALOG', 'Must have successfully completed #if($cluSet.getCluList().size() != 1)all of #end$cluSet.getCluSetAsShortName() $NLHelper.getProperGrammar($cluSet.getCluList().size(), "program")', 'kuali.reqCompType.program.programset.completed.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1004', 'KUALI.CATALOG', 'Must have successfully completed a minimum of $expectedValue $NLHelper.getProperGrammar($expectedValue, "course") from $cluSet.getCluSetAsShortName() $NLHelper.getProperGrammar($cluSet.getCluList().size(), "program")', 'kuali.reqCompType.program.programset.coursecompleted.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1005', 'KUALI.CATALOG', 'Must be admitted to program prior to earning $expectedValue $NLHelper.getProperGrammar($expectedValue, "credit")', 'kuali.reqCompType.program.admitted.credits', 'en')

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2001', 'KUALI.EXAMPLE', 'Must have successfully completed a minimum of 1 program from (Geology or  Sociology) programs', 'kuali.reqCompType.program.programset.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2002', 'KUALI.EXAMPLE', 'Must not have successfully completed any of (Geology or Sociology) programs', 'kuali.reqCompType.program.programset.notcompleted.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2003', 'KUALI.EXAMPLE', 'Must have successfully completed all of (Sociology and CORE Advanced Studies)', 'kuali.reqCompType.program.programset.completed.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2004', 'KUALI.EXAMPLE', 'Must have successfully completed a minimum of 14 courses from ( Sociology and CORE Advanced Studies) programs', 'kuali.reqCompType.program.programset.coursecompleted.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2005', 'KUALI.EXAMPLE', 'Must be admitted to program prior to earning 60 credits', 'kuali.reqCompType.program.admitted.credits', 'en')

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3001', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.requiredCount;reqCompFieldLabel=Number> from <reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Programs>', 'kuali.reqCompType.program.programset.completed.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3002', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Programs>', 'kuali.reqCompType.program.programset.notcompleted.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3003', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Programs>', 'kuali.reqCompType.program.programset.completed.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3004', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.requiredCount;reqCompFieldLabel=Number> from <reqCompFieldType=reqCompFieldType.cluSet.id;reqCompFieldLabel=Courses>', 'kuali.reqCompType.program.programset.coursecompleted.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3005', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.requiredCount;reqCompFieldLabel=Credits>', 'kuali.reqCompType.program.admitted.credits', 'en')

// STMT_TYPE <-> REQ_COM_TYPE
// Courses
// Courses - Anti-req
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.antireqAcademicReadiness','kuali.reqCompType.course.courseset.credits.completed.none')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.antireqAcademicReadiness','kuali.reqCompType.course.courseset.credits.completed.max')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.antireqAcademicReadiness','kuali.reqCompType.course.courseset.completed.none')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.antireqAcademicReadiness','kuali.reqCompType.course.test.score.max')
// Courses - Pre-req
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.course.courseset.completed.all')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.course.courseset.completed.nof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.course.courseset.credits.completed.nof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.course.courseset.gpa.min')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.course.courseset.grade.min')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.course.courseset.grade.max')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.course.permission.org.required')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.course.permission.instructor.required')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.course.test.score.min')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.course.courseset.nof.grade.min')
// Courses - Co-req
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.coreqAcademicReadiness','kuali.reqCompType.courseList.coreq.all')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.coreqAcademicReadiness','kuali.reqCompType.courseList.coreq.oneof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.coreqAcademicReadiness','kuali.reqCompType.course.courseset.enrolled.nof')
// Courses - Enroll
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.enrollAcademicReadiness','kuali.reqCompType.programList.enroll.oneof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.enrollAcademicReadiness','kuali.reqCompType.programList.enroll.none')
// Program Satisfactory Progress
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.programSatisfactoryProgress','kuali.reqCompType.program.admitted.credits')
// Program Entrance
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.programEntrance','kuali.reqCompType.program.programset.completed.nof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.programEntrance','kuali.reqCompType.program.programset.notcompleted.nof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.programEntrance','kuali.reqCompType.program.programset.completed.all')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.programEntrance','kuali.reqCompType.program.programset.coursecompleted.nof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.programEntrance','kuali.reqCompType.course.courseset.gpa.min')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.programEntrance','kuali.reqCompType.course.test.score.min')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.programEntrance','kuali.reqCompType.program.admitted.credits')
// Program Completion
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.programCompletion','kuali.reqCompType.program.programset.completed.nof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.programCompletion','kuali.reqCompType.program.programset.completed.all')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.programCompletion','kuali.reqCompType.program.programset.coursecompleted.nof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.programCompletion','kuali.reqCompType.course.courseset.gpa.min')

// KSST_NL_USAGE_TYPE
INSERT INTO KSST_NL_USAGE_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('KUALI.CATALOG', 'Kuali Catalog', 'Kuali Catalog', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'})
INSERT INTO KSST_NL_USAGE_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('KUALI.EXAMPLE', 'Kuali Rule Example', 'Rule Example', {ts '2010-01-01 01:01:01.0'}, {ts '2010-12-31 01:01:01.0'})
INSERT INTO KSST_NL_USAGE_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('KUALI.COMPOSITION', 'Kuali Rule Composition', 'Rule Composition', {ts '2010-01-01 01:01:01.0'}, {ts '2010-12-31 01:01:01.0'})

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
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('kuali.luStatementType.course', 'CLU Create Course', 'CLU Create Course', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('kuali.luStatementType.prereqAcademicReadiness', 'CLU Prerequisites', 'CLU Prerequisites', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('kuali.luStatementType.coreqAcademicReadiness', 'CLU Corequisites', 'CLU Corequisites', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('kuali.luStatementType.enrollAcademicReadiness', 'CLU Enroll', 'CLU Enroll', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('kuali.luStatementType.antireqAcademicReadiness', 'CLU Antirequisites', 'CLU Antirequisites', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('clu.rule', 'CLU Rules', 'CLU Rules', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('course.rule', 'Course Rules', 'Course Rules', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})

// KSST_RSTMT_RTYP_JN_OSUB_TYP <-> KSST_OBJECT_SUB_TYPE
INSERT INTO KSST_RSTMT_RTYP_JN_OSUB_TYP (REF_STMT_REL_TYPE_ID, OBJ_SUB_TYPE_ID) VALUES ('clu.prerequisites', 'course')

// KSST_RSTMT_RTYP_JN_STMT_TYP <-> KSST_STMT_TYPE
INSERT INTO KSST_RSTMT_RTYP_JN_STMT_TYP (REF_STMT_REL_TYPE_ID, STMT_TYPE_ID) VALUES ('clu.prerequisites', 'kuali.luStatementType.prereqAcademicReadiness')
