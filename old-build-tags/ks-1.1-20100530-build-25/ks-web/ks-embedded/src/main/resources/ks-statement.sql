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

// --------------------------------------
// |   Natural Language Configuration   |
// --------------------------------------

//STATEMENT TYPES
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.luStatementType.createCourseAcademicReadiness', 'Rules used in the evaluation of a person''s academic readiness for enrollment in an LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Overall Academic Readiness Rules')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.luStatementType.prereqAcademicReadiness', 'Pre req rules used in the evaluation of a person''s academic readiness for enrollment in an LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Academic Readiness Pre Reqs')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.luStatementType.coreqAcademicReadiness', 'Co req used in the evaluation of a person''s academic readiness for enrollment in an LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Academic Readiness Co Reqs')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.luStatementType.enrollAcademicReadiness', 'Enrollment req used in the evaluation of a person''s academic readiness for enrollment in an LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Academic Readiness Enroll Reqs')
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.luStatementType.antireqAcademicReadiness', 'Anti req used in the evaluation of a person''s academic readiness for enrollment in an LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Academic Readiness Anti Reqs')

// REQUIREMENT TYPES
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.courseList.none', 'Student must have completed none of the specified courses', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'None of required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.courseList.all', 'Student must have completed all of the specified courses', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'All of required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.courseList.1of1', 'Student must take specified course', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'One required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.courseList.1of2', 'Student must take one of two specified courses', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'One of two required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.courseList.nof', 'Student must take <n> courses from the specified courses', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'N of required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.gradecheck', 'Student needs to have attained a minimum specified GPA', {ts '2000-01-01 00:00:00.0'}, {ts '2001-11-30 00:00:00.0'}, 'Minimum overall GPA')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.grdCondCourseList', 'Student must take <n> credits from the specified courses', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Course completed with minimum specified grade')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.courseList.coreq.all', 'Student must be enrolled in all of the following courses', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'All of required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.courseList.coreq.oneof', 'Student must be enrolled in one of the following courses', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'One of required courses')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.programList.enroll.oneof', 'Enrollment is limited to students in the following programs', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'One of required programs')
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqCompType.programList.enroll.none', 'Enrollment is not available to students in the following programs', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'None of required programs')

// REQUIREMENT FIELD TYPES
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.clu', 'CLU', 'CLU','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.cluSet', 'CLUSET', 'CLUSET','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.requiredCount', 'REQUIRED_COUNT', 'Required Count','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.gpa', 'GPA', 'GAP','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.totalCredits', 'TOTAL_CREDITS', 'Total Credits','number',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.operator', 'CLU', 'CLU','string',null,null,null,null,null,null,null,null,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('reqCompFieldType.countType', 'CLU', 'CLU','string',null,null,null,null,null,null,null,null,0)

// REQ_COMP_TYPE -> REQ_COMP_FIELD_TYPE
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.courseList.all','reqCompFieldType.cluSet')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.courseList.1of1','reqCompFieldType.clu')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.courseList.1of2','reqCompFieldType.clu')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.courseList.nof','reqCompFieldType.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.courseList.nof','reqCompFieldType.operator')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.courseList.nof','reqCompFieldType.cluSet')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.gradecheck','reqCompFieldType.gpa')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.grdCondCourseList','reqCompFieldType.totalCredits')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.grdCondCourseList','reqCompFieldType.cluSet')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.courseList.coreq.all','reqCompFieldType.cluSet')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.courseList.coreq.oneof','reqCompFieldType.cluSet')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.programList.enroll.oneof','reqCompFieldType.cluSet')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqCompType.programList.enroll.none','reqCompFieldType.cluSet')

// REQ_COMPONENT_TYPE_NL_TEMPLATE
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1', 'KUALI.CATALOG', 'Student must have completed none of $cluSet.getCluSetAsCode()', 'kuali.reqCompType.courseList.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2', 'KUALI.CATALOG', 'Student must have completed all of $cluSet.getCluSetAsCode()', 'kuali.reqCompType.courseList.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('3', 'KUALI.CATALOG', 'Student must have completed $cluSet.getCluAsCode(0) or $cluSet.getCluAsCode(1)', 'kuali.reqCompType.courseList.1of2', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('4', 'KUALI.CATALOG', 'Student must have completed $expectedValue of $cluSet.getCluSetAsCode()', 'kuali.reqCompType.courseList.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('5', 'KUALI.CATALOG', 'Student needs a minimum GPA of $gpa', 'kuali.reqCompType.gradecheck', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('6', 'KUALI.CATALOG', 'Students must take $totalCredits credits from $cluSet.getCluSetAsCode()', 'kuali.reqCompType.grdCondCourseList', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('7', 'KUALI.CATALOG', 'Student must be enrolled in all of $cluSet.getCluSetAsCode()', 'kuali.reqCompType.courseList.coreq.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('8', 'KUALI.CATALOG', 'Student must be enrolled in one of of $cluSet.getCluSetAsCode()', 'kuali.reqCompType.courseList.coreq.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('9', 'KUALI.CATALOG', 'Restricted to students in the $cluSet.getCluSetAsCode() only', 'kuali.reqCompType.programList.enroll.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('10', 'KUALI.CATALOG', 'Not for students in $cluSet.getCluSetAsCode()', 'kuali.reqCompType.programList.enroll.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('11', 'KUALI.CATALOG', 'Student must have completed $cluSet.getCluAsCode(0)', 'kuali.reqCompType.courseList.1of1', 'en')

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('101', 'KUALI.EXAMPLE', 'Student must have completed none of MATH100, MATH101', 'kuali.reqCompType.courseList.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('102', 'KUALI.EXAMPLE', 'Student must have completed all of MATH100, MATH101, MATH102', 'kuali.reqCompType.courseList.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('103', 'KUALI.EXAMPLE', 'Student must have completed MATH100 or MATH101', 'kuali.reqCompType.courseList.1of2', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('104', 'KUALI.EXAMPLE', 'Student must have completed 2 of MATH100, MATH101, MATH102', 'kuali.reqCompType.courseList.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('105', 'KUALI.EXAMPLE', 'Student needs a minimum GPA of 2.5', 'kuali.reqCompType.gradecheck', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('106', 'KUALI.EXAMPLE', 'Students must take 3 credits from ENGL 100, ENGL 102 or ENGL 104', 'kuali.reqCompType.grdCondCourseList', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('107', 'KUALI.EXAMPLE', 'Student must be enrolled in all of MATH100, MATH101', 'kuali.reqCompType.courseList.coreq.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('108', 'KUALI.EXAMPLE', 'Student must be enrolled in one of MATH100, MATH101, MATH102', 'kuali.reqCompType.courseList.coreq.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('109', 'KUALI.EXAMPLE', 'Students must be in one of the following program(s): Creative Writing Major', 'kuali.reqCompType.programList.enroll.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('110', 'KUALI.EXAMPLE', 'Not for students in a Music Major or Minor', 'kuali.reqCompType.programList.enroll.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('111', 'KUALI.EXAMPLE', 'Student must have completed MATH100', 'kuali.reqCompType.courseList.1of1', 'en')

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('201', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.cluSet;reqCompFieldLabel=Courses>', 'kuali.reqCompType.courseList.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('202', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.cluSet;reqCompFieldLabel=Courses>', 'kuali.reqCompType.courseList.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('203', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.clu;reqCompFieldLabel=Course 1> or <reqCompFieldType=reqCompFieldType.clu;reqCompFieldLabel=Course 2>', 'kuali.reqCompType.courseList.1of2', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('204', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.requiredCount;reqCompFieldLabel=Number> from <reqCompFieldType=reqCompFieldType.cluSet;reqCompFieldLabel=Courses>', 'kuali.reqCompType.courseList.nof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('205', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.gpa;reqCompFieldLabel=GPA>', 'kuali.reqCompType.gradecheck', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('206', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.totalCredits;reqCompFieldLabel=Credits> from <reqCompFieldType=reqCompFieldType.cluSet;reqCompFieldLabel=Courses>', 'kuali.reqCompType.grdCondCourseList', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('207', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.cluSet;reqCompFieldLabel=Courses>', 'kuali.reqCompType.courseList.coreq.all', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('208', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.cluSet;reqCompFieldLabel=Courses>', 'kuali.reqCompType.courseList.coreq.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('209', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.cluSet;reqCompFieldLabel=Programs>', 'kuali.reqCompType.programList.enroll.oneof', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('210', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.cluSet;reqCompFieldLabel=Programs>', 'kuali.reqCompType.programList.enroll.none', 'en')
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('211', 'KUALI.COMPOSITION', '<reqCompFieldType=reqCompFieldType.clu;reqCompFieldLabel=Course 1>', 'kuali.reqCompType.courseList.1of1', 'en')

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1000', 'KUALI.CATALOG', 'Student muss abgeschlossen $fields.get("reqCompFieldType.requiredCount") von $cluSet.getCluSetAsShortName()', 'kuali.reqCompType.courseList.nof', 'de')

// STATEMENT HEADER TEMPLATE
//INSERT INTO KSST_STMT_TYPE_HEADER_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('1', 'KUALI.CATALOG', '', 'kuali.luStatementType.prereqAcademicReadiness', 'en')
//INSERT INTO KSST_STMT_TYPE_HEADER_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('2', 'KUALI.CATALOG', 'Voraussetzung fur die $clu.getOfficialIdentifier().getLongName(): ', 'kuali.luStatementType.prereqAcademicReadiness', 'de')
//INSERT INTO KSST_STMT_TYPE_HEADER_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('4', 'KUALI.CATALOG', '', 'kuali.luStatementType.coreqAcademicReadiness', 'en')
//INSERT INTO KSST_STMT_TYPE_HEADER_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('5', 'KUALI.CATALOG', '', 'kuali.luStatementType.enrollAcademicReadiness', 'en')
//INSERT INTO KSST_STMT_TYPE_HEADER_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE) values ('6', 'KUALI.CATALOG', '', 'kuali.luStatementType.antireqAcademicReadiness', 'en')

// STMT_TYPE <-> REQ_COM_TYPE
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.courseList.all')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.courseList.nof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.courseList.1of1')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.courseList.1of2')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.grdCondCourseList')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.gradecheck')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.coreqAcademicReadiness','kuali.reqCompType.courseList.coreq.all')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.coreqAcademicReadiness','kuali.reqCompType.courseList.coreq.oneof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.enrollAcademicReadiness','kuali.reqCompType.programList.enroll.oneof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.enrollAcademicReadiness','kuali.reqCompType.programList.enroll.none')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) values ('kuali.luStatementType.antireqAcademicReadiness','kuali.reqCompType.courseList.none')

// STMT_TYPE <-> STMT_TYPE
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.luStatementType.createCourseAcademicReadiness','kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.luStatementType.createCourseAcademicReadiness','kuali.luStatementType.coreqAcademicReadiness')
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.luStatementType.createCourseAcademicReadiness','kuali.luStatementType.enrollAcademicReadiness')
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) values ('kuali.luStatementType.createCourseAcademicReadiness','kuali.luStatementType.antireqAcademicReadiness')

// ----------------------------------------------
// |   Natural Language Translation Test Data   |
// ----------------------------------------------

// Rich text
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.1', '<p>Requirement Component 1</p>', 'Req Comp 1')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.2', '<p>Requirement Component 2</p>', 'Req Comp 2')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.3', '<p>Requirement Component 3</p>', 'Req Comp 3')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.4', '<p>Requirement Component 4</p>', 'Req Comp 4')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.5', '<p>Requirement Component 5</p>', 'Req Comp 5')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.6', '<p>Requirement Component 6</p>', 'Req Comp 6')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.7', '<p>Requirement Component 7</p>', 'Req Comp 7')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.8', '<p>Requirement Component 8</p>', 'Req Comp 8')

// NL - Req Component
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.1','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.courseList.nof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.2','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.gradecheck')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-3', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.3','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.courseList.1of2')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-4', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.4','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.courseList.nof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-5', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.5','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.courseList.coreq.all')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-6', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.6','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.programList.enroll.oneof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-7', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.7','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.programList.enroll.none')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-NL-8', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.8','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.courseList.none')

// NL - KSST REQ COM FIELD
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-1', 'reqCompFieldType.requiredCount', '1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-2', 'reqCompFieldType.operator', 'greater_than_or_equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-3', 'reqCompFieldType.cluSet', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-4', 'reqCompFieldType.gpa', '3.5')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-5', 'reqCompFieldType.requiredCount', '1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-6', 'reqCompFieldType.operator', 'greater_than_or_equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-7', 'reqCompFieldType.clu', 'CLU-NL-3, CLU-NL-4')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-8', 'reqCompFieldType.operator', 'greater_than_or_equal_to')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-9', 'reqCompFieldType.requiredCount', '2')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-10', 'reqCompFieldType.cluSet', 'CLUSET-NL-2')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-11', 'reqCompFieldType.cluSet', 'CLUSET-NL-2')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-12', 'reqCompFieldType.cluSet', 'CLUSET-NL-4')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-13', 'reqCompFieldType.cluSet', 'CLUSET-NL-5')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('FIELD-14', 'reqCompFieldType.cluSet', 'CLUSET-NL-2')

// NL - KSST_RC_JN_RC_FIELD
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-1', 'FIELD-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-1', 'FIELD-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-1', 'FIELD-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-2', 'FIELD-4')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-3', 'FIELD-5')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-3', 'FIELD-6')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-3', 'FIELD-7')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-4', 'FIELD-8')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-4', 'FIELD-9')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-4', 'FIELD-10')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-5', 'FIELD-11')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-6', 'FIELD-12')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-7', 'FIELD-13')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-NL-8', 'FIELD-14')

// Rich text
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.1', '<p>Statement 1</p>', 'Statement 1')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.2', '<p>Statement 2</p>', 'Statement 2')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.3', '<p>Statement 3</p>', 'Statement 3')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.4', '<p>Statement 4</p>', 'Statement 4')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.5', '<p>Statement 5</p>', 'Statement 5')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.6', '<p>Statement 101</p>', 'Statement 101')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.7', '<p>Statement 102</p>', 'Statement 102')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.8', '<p>Statement 103</p>', 'Statement 103')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.9', '<p>Statement 104</p>', 'Statement 104')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.10', '<p>Statement 105</p>', 'Statement 105')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.11', '<p>Statement 106</p>', 'Statement 106')

// LU_STMT
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 1','KSST_STMT.DESC.1','ACTIVE','AND', null ,'kuali.luStatementType.createCourseAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 2','KSST_STMT.DESC.2','ACTIVE','AND','STMT-1','kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-3', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 3','KSST_STMT.DESC.3','ACTIVE','AND',null,'kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-4', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 4','KSST_STMT.DESC.4','ACTIVE','AND',null,'kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-5', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 5','KSST_STMT.DESC.5','ACTIVE','OR',null,'kuali.luStatementType.prereqAcademicReadiness')

INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-101', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 101','KSST_STMT.DESC.6','ACTIVE','AND',null,'kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-102', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 102','KSST_STMT.DESC.7','ACTIVE','OR','STMT-101','kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-103', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 103','KSST_STMT.DESC.8','ACTIVE','AND','STMT-101','kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-104', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 104','KSST_STMT.DESC.9','ACTIVE','AND','STMT-102','kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-105', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 105','KSST_STMT.DESC.10','ACTIVE','AND','STMT-102','kuali.luStatementType.prereqAcademicReadiness')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, PARENT_STMT_ID,STMT_TYPE_ID) VALUES ('STMT-106', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT 106','KSST_STMT.DESC.11','ACTIVE','OR','STMT-103','kuali.luStatementType.prereqAcademicReadiness')

// REQ COM
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.1','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.courseList.all')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.2','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.grdCondCourseList')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-3', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.3','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqCompType.gradecheck')

// CLU <-> LU STMT
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-1','STMT-1')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-2','STMT-3')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-5','STMT-4')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-4','STMT-5')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-1','STMT-5')

//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-1','STMT-101')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-2','STMT-102')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-3','STMT-103')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-4','STMT-104')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-5','STMT-105')
//INSERT INTO KSST_CLU_JN_STMT (CLU_ID, STMT_ID) VALUES ('CLU-NL-1','STMT-106')

// LU STMT <-> REQ COM
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-1','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-2','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-3','STMT-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-1','STMT-3')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-2','STMT-3')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-3','STMT-4')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-1','STMT-5')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-4','STMT-5')

INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-1','STMT-104')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-2','STMT-104')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-3','STMT-105')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-1','STMT-106')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-NL-4','STMT-106')

// KSST_NL_USAGE_TYPE
INSERT INTO KSST_NL_USAGE_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('KUALI.CATALOG', 'Kuali Catalog', 'Kuali Catalog', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'})
INSERT INTO KSST_NL_USAGE_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('KUALI.EXAMPLE', 'Kuali Rule Example', 'Rule Example', {ts '2010-01-01 01:01:01.0'}, {ts '2010-12-31 01:01:01.0'})
INSERT INTO KSST_NL_USAGE_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('KUALI.COMPOSITION', 'Kuali Rule Composition', 'Rule Composition', {ts '2010-01-01 01:01:01.0'}, {ts '2010-12-31 01:01:01.0'})



// Natural Language Translation Test Data
INSERT INTO KSLU_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('NL-RICHTEXT-101', '<p>Desc</p>', 'Desc')

// NL - Lu Type
//INSERT INTO KSLU_LUTYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) values ('kuali.lu.type.CreditCourse', 'A Credit Course', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Course')

// NL - Clu - MATH152
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIV, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN) VALUES ('IDENT-NL-1', 'MATH152', 'MATH', '152', 'MATH 152 Linear Systems', 'MATH 152', 'State-1', 'Type-1', 'Variation-1')
INSERT INTO KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID) values ('CLU-NL-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'NL-RICHTEXT-101', 'FEE-1', 'kuali.lu.type.CreditCourse', 'IDENT-NL-1', 'INSTR-1')

// NL - Clu - MATH221
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIV, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN) VALUES ('IDENT-NL-2', 'MATH221', 'MATH', '221', 'MATH 221 Matrix Algebra', 'MATH 221', 'State-1', 'Type-1', 'Variation-1')
INSERT INTO KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID) values ('CLU-NL-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'NL-RICHTEXT-101', 'FEE-1', 'kuali.lu.type.CreditCourse', 'IDENT-NL-2', 'INSTR-1')

// NL - Clu - MATH180
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIV, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN) VALUES ('IDENT-NL-3', 'MATH180', 'MATH', '180', 'MATH 180 Differential Calculus with Physical Applications', 'MATH 180', 'State-1', 'Type-1', 'Variation-1')
INSERT INTO KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID) values ('CLU-NL-3', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'NL-RICHTEXT-101', 'FEE-1', 'kuali.lu.type.CreditCourse', 'IDENT-NL-3', 'INSTR-1')

// NL - Clu - MATH200
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIV, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN) VALUES ('IDENT-NL-4', 'MATH200', 'MATH', '200', 'MATH 200 Calculus III', 'MATH 200', 'State-1', 'Type-1', 'Variation-1')
INSERT INTO KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID) values ('CLU-NL-4', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'NL-RICHTEXT-101', 'FEE-1', 'kuali.lu.type.CreditCourse', 'IDENT-NL-4', 'INSTR-1')

// NL - Clu - MATH215
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIV, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN) VALUES ('IDENT-NL-5', 'MATH215', 'MATH', '215', 'MATH 215 Elementary Differential Equations I', 'MATH 215', 'State-1', 'Type-1', 'Variation-1')
INSERT INTO KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID) values ('CLU-NL-5', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'NL-RICHTEXT-101', 'FEE-1', 'kuali.lu.type.CreditCourse', 'IDENT-NL-5', 'INSTR-1')

// NL - Clu - Creative Writing major
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIV, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN) VALUES ('IDENT-NL-6', 'Major Eng CW', 'CW', 'Major', 'Major in Creative Writing ', 'Major Eng CW', 'State-1', 'Type-1', 'Variation-1')
INSERT INTO KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID) values ('CLU-NL-6', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'NL-RICHTEXT-101', 'FEE-1', 'kuali.lu.type.CreditCourse', 'IDENT-NL-6', 'INSTR-1')

// NL - Clu - Music Minor
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIV, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN) VALUES ('IDENT-NL-7', 'Minor Music', 'Music', 'Minor', 'Minor in Music', 'Minor Music', 'State-1', 'Type-1', 'Variation-1')
INSERT INTO KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID) values ('CLU-NL-7', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'NL-RICHTEXT-101', 'FEE-1', 'kuali.lu.type.CreditCourse', 'IDENT-NL-7', 'INSTR-1')

// NL - Clu - Music Minor 2
INSERT INTO KSLU_CLU_IDENT (ID, CD, DIV, LVL, LNG_NAME, SHRT_NAME, ST, TYPE, VARTN) VALUES ('IDENT-NL-8', 'Minor Music 2', 'Music', 'Minor', 'Minor in Music 2', 'Minor Music 2', 'State-1', 'Type-1', 'Variation-1')
INSERT INTO KSLU_CLU (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, EFF_DT, EXPIR_DT, HAS_EARLY_DROP_DEDLN, IS_ENRL, IS_HAZR_DISBLD_STU, NEXT_REVIEW_PRD, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ACCT_ID, RT_DESCR_ID, FEE_ID, LUTYPE_ID, OFFIC_CLU_ID, PRI_INSTR_ID) values ('CLU-NL-8', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1, 1, 1, 42, {ts '2002-01-01 00:00:00.0'}, {ts '2003-01-01 00:00:00.0'}, 1, 1, 0, 'NEXT_REVIEW_PRD', 'REF_URL', 'STATE1', 'ATP_DUR_TYP_KEY', 3, 'STDY_SUBJ_AREA', 'ACCT-1', 'NL-RICHTEXT-101', 'FEE-1', 'kuali.lu.type.CreditCourse', 'IDENT-NL-8', 'INSTR-1')

// NL - CluSet - Math 158,221 CLU Set
INSERT INTO KSLU_CLU_SET (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, NAME, RT_DESCR_ID, CRIT_SET, TYPE, ST, ADMIN_ORG_ID, REUSABLE) VALUES ('CLUSET-NL-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 0, {ts '2003-01-01 00:00:00.0'}, {ts '2004-01-01 00:00:00.0'}, 'Math 158,221 CLU Set', 'RICHTEXT-501', 0, 'kuali.cluSet.type.creditCourse', 'active', '63', 1)

// NL - CluSet - Math 158,221,180 CLU Set
INSERT INTO KSLU_CLU_SET (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, NAME, RT_DESCR_ID, CRIT_SET, TYPE, ST, ADMIN_ORG_ID, REUSABLE) VALUES ('CLUSET-NL-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 0, {ts '2003-01-01 00:00:00.0'}, {ts '2004-01-01 00:00:00.0'}, 'Math 158,221,180 CLU Set', 'RICHTEXT-501', 0, 'kuali.cluSet.type.creditCourse', 'active', '63', 1)

// NL - CluSet - Math 221,180,200,215 CLU Set
INSERT INTO KSLU_CLU_SET (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, NAME, RT_DESCR_ID, CRIT_SET, TYPE, ST, ADMIN_ORG_ID, REUSABLE) VALUES ('CLUSET-NL-3', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 0, {ts '2003-01-01 00:00:00.0'}, {ts '2004-01-01 00:00:00.0'}, 'Math 158,221,180 CLU Set', 'RICHTEXT-501', 0, 'kuali.cluSet.type.creditCourse', 'active', '63', 1)

// NL - CluSet - Creative Wriging major CLU Set
INSERT INTO KSLU_CLU_SET (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, NAME, RT_DESCR_ID, CRIT_SET, TYPE, ST, ADMIN_ORG_ID, REUSABLE) VALUES ('CLUSET-NL-4', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 0, {ts '2003-01-01 00:00:00.0'}, {ts '2004-01-01 00:00:00.0'}, 'Creative Writing major Set', 'RICHTEXT-501', 0, 'kuali.cluSet.type.creditCourse', 'active', '63', 1)

// NL - CluSet - Music Minor CLU Set
INSERT INTO KSLU_CLU_SET (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, EFF_DT, EXPIR_DT, NAME, RT_DESCR_ID, CRIT_SET, TYPE, ST, ADMIN_ORG_ID, REUSABLE) VALUES ('CLUSET-NL-5', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 0, {ts '2003-01-01 00:00:00.0'}, {ts '2004-01-01 00:00:00.0'}, 'Music minor Set', 'RICHTEXT-501', 0, 'kuali.cluSet.type.creditCourse', 'active', '50', 1)

// NL - Clu <-> CluSet join
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-1', 'CLU-NL-1')
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-1', 'CLU-NL-3')

// NL - Clu <-> CluSet join
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-2', 'CLU-NL-1')
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-2', 'CLU-NL-2')
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-2', 'CLU-NL-3')

// NL - Clu <-> CluSet join
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-3', 'CLU-NL-2')
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-3', 'CLU-NL-3')
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-3', 'CLU-NL-4')
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-3', 'CLU-NL-5')

// NL - Clu <-> CluSet join
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-4', 'CLU-NL-6')

// NL - Clu <-> CluSet join
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-5', 'CLU-NL-7')
INSERT INTO KSLU_CLU_SET_JN_CLU (CLU_SET_ID, CLU_ID) VALUES ('CLUSET-NL-5', 'CLU-NL-8')






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
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('kuali.luStatementType.createCourseAcademicReadiness', 'CLU Create Course', 'CLU Create Course', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('kuali.luStatementType.prereqAcademicReadiness', 'CLU Prerequisites', 'CLU Prerequisites', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('kuali.luStatementType.coreqAcademicReadiness', 'CLU Corequisites', 'CLU Corequisites', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('kuali.luStatementType.enrollAcademicReadiness', 'CLU Enroll', 'CLU Enroll', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('kuali.luStatementType.antireqAcademicReadiness', 'CLU Antirequisites', 'CLU Antirequisites', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT) VALUES ('clu.rule', 'CLU Rule', 'CLU Rule', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'})

// KSST_RSTMT_RTYP_JN_OSUB_TYP <-> KSST_OBJECT_SUB_TYPE
INSERT INTO KSST_RSTMT_RTYP_JN_OSUB_TYP (REF_STMT_REL_TYPE_ID, OBJ_SUB_TYPE_ID) VALUES ('clu.prerequisites', 'course')

// KSST_RSTMT_RTYP_JN_STMT_TYP <-> KSST_STMT_TYPE
INSERT INTO KSST_RSTMT_RTYP_JN_STMT_TYP (REF_STMT_REL_TYPE_ID, STMT_TYPE_ID) VALUES ('clu.prerequisites', 'kuali.luStatementType.prereqAcademicReadiness')

// KSST_REF_STMT_REL
INSERT INTO KSST_REF_STMT_REL (ID, EFF_DT, EXPIR_DT, REF_OBJ_TYPE_KEY, REF_OBJ_ID, REF_STMT_REL_TYPE_ID, STMT_ID, ST, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND) VALUES ('ref-stmt-rel-1', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'clu', 'CLU-NL-1', 'clu.prerequisites', 'STMT-1', 'ACTIVE', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1)
INSERT INTO KSST_REF_STMT_REL (ID, EFF_DT, EXPIR_DT, REF_OBJ_TYPE_KEY, REF_OBJ_ID, REF_STMT_REL_TYPE_ID, STMT_ID, ST, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND) VALUES ('ref-stmt-rel-5', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'}, 'clu', 'CLU-NL-1', 'clu.prerequisites', 'STMT-5', 'ACTIVE', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'}, 1)

// Test data for View Course
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('aaf0dbc1-a2fc-4f4c-9417-d6047a035dee', '<p>Formatted rich text test</p>', 'Plain rich text test');
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_KEY, VALUE) VALUES ('aa828ce4-67e2-4a49-bebd-1a3642541dd8', 'reqCompFieldType.cluSet', 'CLUSET-NL-2');
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT,  REQ_COM_TYPE_ID) VALUES ('aa81b95e-6183-409d-956e-a57399e833a4', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 1,'aaf0dbc1-a2fc-4f4c-9417-d6047a035dee','ACTIVE', {ts '2001-01-01 00:00:00.0'},'kuali.reqCompType.courseList.all');
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('aa81b95e-6183-409d-956e-a57399e833a4', 'aa828ce4-67e2-4a49-bebd-1a3642541dd8');
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, VERSIONIND,  OPERATOR,STMT_TYPE_ID) VALUES ('aae41735-366f-494f-a34d-c8506df1da30', 'CREATEID', {ts '2001-01-01 00:00:00.0'},1,'AND','kuali.luStatementType.prereqAcademicReadiness');
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('aa81b95e-6183-409d-956e-a57399e833a4', 'aae41735-366f-494f-a34d-c8506df1da30');
INSERT INTO KSST_REF_STMT_REL (ID,  CREATEID, CREATETIME, EFF_DT, REF_OBJ_TYPE_KEY, REF_OBJ_ID, REF_STMT_REL_TYPE_ID, STMT_ID, ST, VERSIONIND) VALUES ('ab56bc76-99e9-4333-9b88-15db83056e2c',  'CREATEID', {ts '2000-01-01 00:00:00.0'},  {ts '2000-01-01 00:00:00.0'}, 'clu', '2f68e422-a54f-4f10-9cf2-37dd4083ce5d', 'clu.rule', 'aae41735-366f-494f-a34d-c8506df1da30', 'ACTIVE', 1);

