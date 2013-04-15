--Add joint table unique constraints that should have been
ALTER TABLE
    KSST_RCTYP_JN_RCFLDTYP ADD CONSTRAINT ix1 UNIQUE (REQ_COMP_TYPE_ID,
    REQ_COMP_FIELD_TYPE_ID)
/
    
--Add some new requrement types

----------------------------	NEW REQ 1 -----
--Must have earned a minimum cumulative GPA of 2.5

INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.cumulative.gpa.min', 'Must have earned a minimum cumulative GPA of <GPA>', SYSDATE, SYSDATE, 'Minimum cumulative GPA')
/

insert into KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) values ('kuali.reqComponent.type.course.cumulative.gpa.min', 'kuali.reqComponent.field.type.gpa')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4001', '', '', 'en', 'KUALI.RULE.EXAMPLE', 'Must have earned a minimum cumulative GPA of 2.5', 'kuali.reqComponent.type.course.cumulative.gpa.min')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4101', '', '', 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.gpa;reqCompFieldLabel=GPA>', 'kuali.reqComponent.type.course.cumulative.gpa.min')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4201', '', '', 'en', 'KUALI.RULE.CATALOG', 'Must have earned a minimum cumulative GPA of $gpa', 'kuali.reqComponent.type.course.cumulative.gpa.min')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4301', '', '', 'en', 'KUALI.RULE.PREVIEW', 'Must have earned a minimum cumulative GPA of $gpa', 'kuali.reqComponent.type.course.cumulative.gpa.min')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4401', '', '', 'en', 'KUALI.RULE', 'Must have earned a minimum cumulative GPA of $gpa', 'kuali.reqComponent.type.course.cumulative.gpa.min')
/
insert into KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) values ('PR-401','kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.cumulative.gpa.min')
/
insert into KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) values ('CR-401','kuali.statement.type.course.academicReadiness.coreq', 'kuali.reqComponent.type.course.cumulative.gpa.min')
/
----------------------------	NEW REQ 2 -----
--Must have earned a minimum of 120 total credits

INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.credits.min', 'Must have earned a minimum of <n> total credits', SYSDATE, SYSDATE, 'Earned minimum n credits')
/
insert into KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) values ('kuali.reqComponent.type.course.credits.min', 'kuali.reqComponent.field.type.value.positive.integer')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4002', '', '', 'en', 'KUALI.RULE.EXAMPLE', 'Must have earned a minimum of 120 total credits', 'kuali.reqComponent.type.course.credits.min')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4102', '', '', 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Minimum Number of Credits>', 'kuali.reqComponent.type.course.credits.min')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4202', '', '', 'en', 'KUALI.RULE.CATALOG', 'Must have earned a minimum of $intValue total $NLHelper.getProperGrammar($intValue, "credit")', 'kuali.reqComponent.type.course.credits.min')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4302', '', '', 'en', 'KUALI.RULE.PREVIEW', 'Must have earned a minimum of $intValue total $NLHelper.getProperGrammar($intValue, "credit")', 'kuali.reqComponent.type.course.credits.min')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4402', '', '', 'en', 'KUALI.RULE', 'Must have earned a minimum of $intValue total $NLHelper.getProperGrammar($intValue, "credit")', 'kuali.reqComponent.type.course.credits.min')
/
insert into KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) values ('PR-402','kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.credits.min')
/
insert into KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) values ('CR-402','kuali.statement.type.course.academicReadiness.coreq', 'kuali.reqComponent.type.course.credits.min')
/
----------------------------	NEW REQ 3 -----
--Free Form Text
INSERT INTO KSST_REQ_COM_FIELD_TYPE (ID, NAME, DESCR, DATA_TYPE, MIN_VALUE, MAX_VALUE, MIN_LENGTH, MAX_LENGTH, VALID_CHARS, INVALID_CHARS, MIN_OCCURS, MAX_OCCURS, READ_ONLY) VALUES ('kuali.reqComponent.field.type.value.freeform.text', 'Free Text', 'Free Text','string',null,null,null,null,null,null,null,null,0)
/
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.course.freeform.text', 'Free Form Text', SYSDATE, SYSDATE, 'Free Text')
/
insert into KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) values ('kuali.reqComponent.type.course.freeform.text', 'kuali.reqComponent.field.type.value.freeform.text')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4003', '', '', 'en', 'KUALI.RULE.EXAMPLE', 'Students must be proficient in Excel', 'kuali.reqComponent.type.course.freeform.text')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4103', '', '', 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.value.freeform.text;reqCompFieldLabel=Text>', 'kuali.reqComponent.type.course.freeform.text')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4203', '', '', 'en', 'KUALI.RULE.CATALOG', '$fields.get("kuali.reqComponent.field.type.value.freeform.text")', 'kuali.reqComponent.type.course.freeform.text')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4303', '', '', 'en', 'KUALI.RULE.PREVIEW', '$fields.get("kuali.reqComponent.field.type.value.freeform.text")', 'kuali.reqComponent.type.course.freeform.text')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4403', '', '', 'en', 'KUALI.RULE', '$fields.get("kuali.reqComponent.field.type.value.freeform.text")', 'kuali.reqComponent.type.course.freeform.text')
/
insert into KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) values ('PR-403','kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.freeform.text')
/
insert into KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) values ('CR-403','kuali.statement.type.course.academicReadiness.coreq', 'kuali.reqComponent.type.course.freeform.text')
/