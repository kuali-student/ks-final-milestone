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
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME,VER_NBR) VALUES ('kuali.statement.type.course', 'Rules used in the evaluation of a person''s academic readiness for enrollment in a LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Overall Academic Readiness Rules',0)
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME,VER_NBR) VALUES ('kuali.statement.type.course.academicReadiness.prereq', 'Pre req rules used in the evaluation of a person''s academic readiness for enrollment in a LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Academic Readiness Pre Reqs',0)
INSERT INTO KSST_STMT_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME,VER_NBR) VALUES ('kuali.statement.type.course.academicReadiness.coreq', 'Co req used in the evaluation of a person''s academic readiness for enrollment in a LU.', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Academic Readiness Co Reqs',0)

// REQUIREMENT TYPES
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME,VER_NBR) VALUES ('kuali.reqComponent.type.courseList.none', 'Student must have completed none of <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'None of required courses',0)
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME,VER_NBR) VALUES ('kuali.reqComponent.type.courseList.all', 'Student must have completed all of <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'All of required courses',0)
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME,VER_NBR) VALUES ('kuali.reqComponent.type.courseList.1of1', 'Student must have completed <course>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'One required course',0)
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME,VER_NBR) VALUES ('kuali.reqComponent.type.courseList.1of2', 'Student must have completed <course-1> or <course-2>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'One of two required courses',0)
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME,VER_NBR) VALUES ('kuali.reqComponent.type.courseList.nof', 'Student needs <n> courses from the following <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'N of required courses',0)
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME,VER_NBR) VALUES ('kuali.reqComponent.type.gradecheck', 'Student needs a minimum GPA of <GPA>', {ts '2000-01-01 00:00:00.0'}, {ts '2001-11-30 00:00:00.0'}, 'Minimum overall GPA',0)
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME,VER_NBR) VALUES ('kuali.reqComponent.type.grdCondCourseList', 'Student needs a <credits> credits from the following <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'Course completed with minimum specified grade',0)
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME,VER_NBR) VALUES ('kuali.reqComponent.type.courseList.coreq.all', 'Student must be enrolled in all of the following <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'All of required courses',0)
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME,VER_NBR) VALUES ('kuali.reqComponent.type.courseList.coreq.oneof', 'Student must be enrolled in one of the following <courses>', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'}, 'One of required courses',0)

// REQUIREMENT FIELD TYPES
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY,VER_NBR) VALUES ('kuali.reqComponent.field.type.clu.id', 'CLU', 'CLU','string',null,null,null,null,null,null,null,null,0,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY,VER_NBR) VALUES ('kuali.reqComponent.field.type.cluSet.id', 'CLUSET', 'CLUSET','string',null,null,null,null,null,null,null,null,0,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY,VER_NBR) VALUES ('kuali.reqComponent.field.type.requiredCount', 'REQUIRED_COUNT', 'Required Count','string',null,null,null,null,null,null,null,null,0,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY,VER_NBR) VALUES ('kuali.reqComponent.field.type.gpa', 'GPA', 'GAP','string',null,null,null,null,null,null,null,null,0,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY,VER_NBR) VALUES ('kuali.reqComponent.field.type.totalCredits', 'TOTAL_CREDITS', 'Total Credits','number',null,null,null,null,null,null,null,null,0,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY,VER_NBR) VALUES ('kuali.reqComponent.field.type.operator', 'CLU', 'CLU','string',null,null,null,null,null,null,null,null,0,0)
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY,VER_NBR) VALUES ('kuali.reqComponent.field.type.countType', 'CLU', 'CLU','string',null,null,null,null,null,null,null,null,0,0)

// REQ_COMP_TYPE -> REQ_COMP_FIELD_TYPE
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.courseList.all','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.courseList.1of1','kuali.reqComponent.field.type.clu.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.courseList.1of2','kuali.reqComponent.field.type.clu.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.courseList.nof','kuali.reqComponent.field.type.requiredCount')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.courseList.nof','kuali.reqComponent.field.type.operator')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.courseList.nof','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.gradecheck','kuali.reqComponent.field.type.gpa')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.grdCondCourseList','kuali.reqComponent.field.type.totalCredits')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.grdCondCourseList','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.courseList.coreq.all','kuali.reqComponent.field.type.cluSet.id')
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.courseList.coreq.oneof','kuali.reqComponent.field.type.cluSet.id')

// REQ_COMPONENT_TYPE_NL_TEMPLATE
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE,VER_NBR) VALUES ('1', 'KUALI.RULE', 'Student must have completed all of $cluSet.getCluSetAsShortName()', 'kuali.reqComponent.type.courseList.all', 'en',0)
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE,VER_NBR) VALUES ('2', 'KUALI.RULE', 'Student must have completed $expectedValue of $cluSet.getCluSetAsShortName()', 'kuali.reqComponent.type.courseList.nof', 'en',0)
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE,VER_NBR) VALUES ('3', 'KUALI.RULE', 'Student must have completed $cluSet.getCluAsShortName(0)', 'kuali.reqComponent.type.courseList.1of1', 'en',0)
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE,VER_NBR) VALUES ('4', 'KUALI.RULE', 'Student must have completed $cluSet.getCluAsShortName(0) or $cluSet.getCluAsShortName(1)', 'kuali.reqComponent.type.courseList.1of2', 'en',0)
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE,VER_NBR) VALUES ('5', 'KUALI.RULE', 'Student needs a minimum GPA of $gpa in $cluSet.getCluSetAsShortName()', 'kuali.reqComponent.type.gradecheck', 'en',0)

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE,VER_NBR) VALUES ('6', 'KUALI.EXAMPLE', 'Student must have completed all of MATH100, MATH101, MATH102', 'kuali.reqComponent.type.courseList.all', 'en',0)
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE,VER_NBR) VALUES ('7', 'KUALI.EXAMPLE', 'Student must have completed 2 of MATH100, MATH101, MATH102', 'kuali.reqComponent.type.courseList.nof', 'en',0)
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE,VER_NBR) VALUES ('8', 'KUALI.EXAMPLE', 'Student must have MATH100 or MATH101', 'kuali.reqComponent.type.courseList.1of2', 'en',0)
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE,VER_NBR) VALUES ('9', 'KUALI.EXAMPLE', 'Student needs a minimum GPA of 2.5', 'kuali.reqComponent.type.gradecheck', 'en',0)

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE,VER_NBR) VALUES ('10', 'KUALI.RULE', 'Student must have completed none of $cluSet.getCluSetAsShortName()', 'kuali.reqComponent.type.courseList.none', 'en',0)
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE,VER_NBR) VALUES ('11', 'KUALI.RULE', 'Students must take $totalCredits credits from $cluSet.getCluSetAsShortName()', 'kuali.reqComponent.type.grdCondCourseList', 'en',0)

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE,VER_NBR) VALUES ('12', 'KUALI.RULE', 'Student muss abgeschlossen $expectedValue von $cluSet.getCluSetAsShortName()', 'kuali.reqComponent.type.courseList.nof', 'de',0)

// STATEMENT HEADER TEMPLATE
//INSERT INTO KSST_STMT_TYPE_HEADER_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE,VER_NBR) VALUES ('1', 'KUALI.RULE', 'Requirement for $clu.getLongName(): ', 'kuali.statement.type.course.academicReadiness.prereq', 'en',0)
//INSERT INTO KSST_STMT_TYPE_HEADER_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE,VER_NBR) VALUES ('2', 'KUALI.RULE', 'Voraussetzung fur die $clu.getLongName(): ', 'kuali.statement.type.course.academicReadiness.prereq', 'de',0)

// STMT_TYPE <-> REQ_COM_TYPE
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) VALUES ('kuali.statement.type.course.academicReadiness.prereq','kuali.reqComponent.type.courseList.all')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) VALUES ('kuali.statement.type.course.academicReadiness.prereq','kuali.reqComponent.type.courseList.nof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) VALUES ('kuali.statement.type.course.academicReadiness.prereq','kuali.reqComponent.type.courseList.1of1')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) VALUES ('kuali.statement.type.course.academicReadiness.prereq','kuali.reqComponent.type.courseList.1of2')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) VALUES ('kuali.statement.type.course.academicReadiness.prereq','kuali.reqComponent.type.grdCondCourseList')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) VALUES ('kuali.statement.type.course.academicReadiness.prereq','kuali.reqComponent.type.gradecheck')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) VALUES ('kuali.statement.type.course.academicReadiness.coreq','kuali.reqComponent.type.courseList.coreq.all')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) VALUES ('kuali.statement.type.course.academicReadiness.coreq','kuali.reqComponent.type.courseList.coreq.oneof')
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (STMT_TYPE_ID,REQ_COM_TYPE_ID) VALUES ('kuali.statement.type.course.academicReadiness.coreq','kuali.reqComponent.type.gradecheck')

// STMT_TYPE <-> STMT_TYPE
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) VALUES ('kuali.statement.type.course','kuali.statement.type.course.academicReadiness.prereq')
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (STMT_TYPE_ID, CHLD_STMT_TYPE_ID) VALUES ('kuali.statement.type.course','kuali.statement.type.course.academicReadiness.coreq')

// KSST_NL_USAGE_TYPE
INSERT INTO KSST_NL_USAGE_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT,VER_NBR) VALUES ('KUALI.CATALOG', 'Kuali Catalog', 'Kuali Catalog', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},0)
INSERT INTO KSST_NL_USAGE_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT,VER_NBR) VALUES ('KUALI.EXAMPLE', 'Kuali Rule Example', 'Rule Example', {ts '2010-01-01 01:01:01.0'}, {ts '2010-12-31 01:01:01.0'},0)
INSERT INTO KSST_NL_USAGE_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT,VER_NBR) VALUES ('KUALI.RULE', 'Kuali Rule Editing', 'Rule Edit', {ts '2010-01-01 01:01:01.0'}, {ts '2010-12-31 01:01:01.0'},0)
INSERT INTO KSST_NL_USAGE_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT,VER_NBR) VALUES ('KUALI.COURSE.CATALOG', 'Full Kuali Course Catalog', 'Kuali Course Catalog', {ts '2000-01-01 00:00:00.0'}, {ts '2000-12-31 00:00:00.0'},0)
INSERT INTO KSST_NL_USAGE_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT,VER_NBR) VALUES ('KUALI.REQCOMP.EXAMPLE', 'Kuali Requirement Component Rule Example', 'Requirement Component Example', {ts '2010-01-01 01:01:01.0'}, {ts '2010-12-31 01:01:01.0'},0)

// KSST_OBJECT_TYPE
INSERT INTO KSST_OBJECT_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT,VER_NBR) VALUES ('clu', 'Kuali CLU', 'Kuali CLU', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'},0)

// KSST_OBJECT_SUB_TYPE
INSERT INTO KSST_OBJECT_SUB_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT,VER_NBR) VALUES ('course', 'Kuali Course', 'Kuali Course', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'},0)
INSERT INTO KSST_OBJECT_SUB_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT,VER_NBR) VALUES ('program', 'Kuali Program', 'Kuali Program', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'},0)

// KSST_OBJ_TYP_JN_OBJ_SUB_TYP
INSERT INTO KSST_OBJ_TYP_JN_OBJ_SUB_TYP (OBJ_TYPE_ID, OBJ_SUB_TYPE_ID) VALUES ('clu', 'course')
INSERT INTO KSST_OBJ_TYP_JN_OBJ_SUB_TYP (OBJ_TYPE_ID, OBJ_SUB_TYPE_ID) VALUES ('clu', 'program')

// KSST_REF_STMT_REL_TYPE
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT,VER_NBR) VALUES ('clu.prerequisites', 'CLU Prerequisites', 'CLU Prereq', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'},0)
INSERT INTO KSST_REF_STMT_REL_TYPE (TYPE_KEY, TYPE_DESC, NAME, EFF_DT, EXPIR_DT,VER_NBR) VALUES ('clu.corequisites', 'CLU Corequisites', 'CLU Coreq', {ts '2000-01-01 00:00:00.0'}, {ts '2100-12-31 00:00:00.0'},0)

// KSST_RSTMT_RTYP_JN_OSUB_TYP <-> KSST_OBJECT_SUB_TYPE
INSERT INTO KSST_RSTMT_RTYP_JN_OSUB_TYP (REF_STMT_REL_TYPE_ID, OBJ_SUB_TYPE_ID) VALUES ('clu.prerequisites', 'course')

// KSST_RSTMT_RTYP_JN_STMT_TYP <-> KSST_STMT_TYPE
INSERT INTO KSST_RSTMT_RTYP_JN_STMT_TYP (REF_STMT_REL_TYPE_ID, STMT_TYPE_ID) VALUES ('clu.prerequisites', 'kuali.statement.type.course.academicReadiness.prereq')
