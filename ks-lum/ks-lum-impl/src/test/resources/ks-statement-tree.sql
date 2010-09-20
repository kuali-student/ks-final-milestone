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

-- Statement Tree
--                --------- STMT-TV-1:AND ---------
--                |                           |
--           STMT-TV-2:AND                  STMT-TV-3:OR
--           |           |                  |           |
--     REQCOMP-TV-1  REQCOMP-TV-2     REQCOMP-TV-3  REQCOMP-TV-4
--           |           |                  |           |
--         AllOf        gpa                nof    permissionOfInstructor

// Rich text
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.TV-1', '<p>Statement Tree View 1</p>', 'Statement Tree View 1')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.TV-2', '<p>Statement Tree View 2</p>', 'Statement Tree View 2')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_STMT.DESC.TV-3', '<p>Statement Tree View 3</p>', 'Statement Tree View 3')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.TV-1', '<p>RC Tree View 1</p>', 'RC Tree View 1')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.TV-2', '<p>RC Tree View 2</p>', 'RC Tree View 2')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.TV-3', '<p>RC Tree View 3</p>', 'RC Tree View 3')
INSERT INTO KSST_RICH_TEXT_T (ID, FORMATTED, PLAIN) VALUES ('KSST_REQ_COM.DESC.TV-4', '<p>RC Tree View 4</p>', 'RC Tree View 4')

// Req Component
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-TV-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.TV-1','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqComponent.type.course.courseset.completed.all')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-TV-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.TV-2','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqComponent.type.course.courseset.gpa.min')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-TV-3', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.TV-3','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqComponent.type.course.courseset.completed.nof')
INSERT INTO KSST_REQ_COM (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, RT_DESCR_ID, ST, EFF_DT, EXPIR_DT, REQ_COM_TYPE_ID) VALUES ('REQCOMP-TV-4', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'KSST_REQ_COM.DESC.TV-4','ACTIVE',{ts '2001-01-01 00:00:00.0'},{ts '2002-01-01 00:00:00.0'},'kuali.reqComponent.type.course.permission.instructor.required')

// KSST REQ COM FIELD
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_TYPE, VALUE) VALUES ('FIELD-1', 'kuali.reqComponent.field.type.cluSet.id', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_TYPE, VALUE) VALUES ('FIELD-2', 'kuali.reqComponent.field.type.gpa', '3.5')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_TYPE, VALUE) VALUES ('FIELD-3', 'kuali.reqComponent.field.type.cluSet.id', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_TYPE, VALUE) VALUES ('FIELD-4', 'kuali.reqComponent.field.type.value', '1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_TYPE, VALUE) VALUES ('FIELD-5', 'kuali.reqComponent.field.type.cluSet.id', 'CLUSET-NL-1')
INSERT INTO KSST_REQ_COM_FIELD (ID, REQ_COM_FIELD_TYPE, VALUE) VALUES ('FIELD-6', 'kuali.reqComponent.field.type.person.id', 'Smith')

INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-TV-1', 'FIELD-1')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-TV-2', 'FIELD-2')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-TV-2', 'FIELD-3')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-TV-3', 'FIELD-4')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-TV-3', 'FIELD-5')
INSERT INTO KSST_RC_JN_RC_FIELD (REQ_COM_ID, REQ_COM_FIELD_ID) VALUES ('REQCOMP-TV-4', 'FIELD-6')

// STMT
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, STMT_TYPE_ID) VALUES ('STMT-TV-1', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT TV 1','KSST_STMT.DESC.TV-1','ACTIVE','AND','kuali.statement.type.program.entrance')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, STMT_TYPE_ID) VALUES ('STMT-TV-2', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT TV 2','KSST_STMT.DESC.TV-2','ACTIVE','AND','kuali.statement.type.program.entrance')
INSERT INTO KSST_STMT (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VERSIONIND, NAME, RT_DESCR_ID, ST, OPERATOR, STMT_TYPE_ID) VALUES ('STMT-TV-3', 'CREATEID', {ts '2000-01-01 00:00:00.0'}, 'UPDATEID', {ts '2001-01-01 00:00:00.0'},1,'STMT TV 3','KSST_STMT.DESC.TV-3','ACTIVE','OR','kuali.statement.type.program.entrance')

INSERT INTO KSST_STMT_JN_STMT (STMT_ID, CHLD_STMT_ID) VALUES ('STMT-TV-1', 'STMT-TV-2')
INSERT INTO KSST_STMT_JN_STMT (STMT_ID, CHLD_STMT_ID) VALUES ('STMT-TV-1', 'STMT-TV-3')

// STMT <-> REQ COM
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-TV-1','STMT-TV-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-TV-2','STMT-TV-2')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-TV-3','STMT-TV-3')
INSERT INTO KSST_STMT_JN_REQ_COM (REQ_COM_ID, STMT_ID) VALUES ('REQCOMP-TV-4','STMT-TV-3')
