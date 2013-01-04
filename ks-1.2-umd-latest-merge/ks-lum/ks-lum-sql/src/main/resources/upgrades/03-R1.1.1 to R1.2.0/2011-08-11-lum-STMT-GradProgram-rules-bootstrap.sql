
--<n> credits from courses approved by advisor
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.program.credits.advisor', 'Must have <n> credits from courses approved by advisor', SYSDATE, SYSDATE, 'Minimum advisor approved credits')
/
insert into KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) values ('kuali.reqComponent.type.program.credits.advisor', 'kuali.reqComponent.field.type.value.positive.integer')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4004', '', '', 'en', 'KUALI.RULE.EXAMPLE', 'Must have 35 credits from courses approved by advisor', 'kuali.reqComponent.type.program.credits.advisor')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4104', '', '', 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Minimum Number of Credits Approved By Advisor>', 'kuali.reqComponent.type.program.credits.advisor')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4204', '', '', 'en', 'KUALI.RULE.CATALOG', 'Must have $intValue $NLHelper.getProperGrammar($intValue, "credit") from courses approved by advisor', 'kuali.reqComponent.type.program.credits.advisor')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4304', '', '', 'en', 'KUALI.RULE.PREVIEW', 'Must have $intValue $NLHelper.getProperGrammar($intValue, "credit") from courses approved by advisor', 'kuali.reqComponent.type.program.credits.advisor')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4404', '', '', 'en', 'KUALI.RULE', 'Must have $intValue $NLHelper.getProperGrammar($intValue, "credit") from courses approved by advisor', 'kuali.reqComponent.type.program.credits.advisor')
/
insert into KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) values ('PC-404','kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.credits.advisor')
/

--<n> courses from courses approved by advisor
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.program.courses.advisor', 'Must have <n> courses from courses approved by advisor', SYSDATE, SYSDATE, 'Minimum advisor approved courses')
/
insert into KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) values ('kuali.reqComponent.type.program.courses.advisor', 'kuali.reqComponent.field.type.value.positive.integer')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4005', '', '', 'en', 'KUALI.RULE.EXAMPLE', 'Must have 6 courses from courses approved by advisor', 'kuali.reqComponent.type.program.courses.advisor')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4105', '', '', 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Minimum Number of Courses Approved By Advisor>', 'kuali.reqComponent.type.program.courses.advisor')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4205', '', '', 'en', 'KUALI.RULE.CATALOG', 'Must have $intValue $NLHelper.getProperGrammar($intValue, "course") from courses approved by advisor', 'kuali.reqComponent.type.program.courses.advisor')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4305', '', '', 'en', 'KUALI.RULE.PREVIEW', 'Must have $intValue $NLHelper.getProperGrammar($intValue, "course") from courses approved by advisor', 'kuali.reqComponent.type.program.courses.advisor')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4405', '', '', 'en', 'KUALI.RULE', 'Must have $intValue $NLHelper.getProperGrammar($intValue, "course") from courses approved by advisor', 'kuali.reqComponent.type.program.courses.advisor')
/
insert into KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) values ('PC-405','kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.courses.advisor')
/

--Student must successfully complete <n> credits from <theme>
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.program.credits.theme', 'Student must successfully complete <n> credits from <theme>', SYSDATE, SYSDATE, 'Minimum theme approved credits')
/
insert into KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) values ('kuali.reqComponent.type.program.credits.theme', 'kuali.reqComponent.field.type.value.positive.integer')
/
insert into KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) values ('kuali.reqComponent.type.program.credits.theme', 'kuali.reqComponent.field.type.value.freeform.text')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4006', '', '', 'en', 'KUALI.RULE.EXAMPLE', 'Student must successfully complete 35 credits in music theory or analysis', 'kuali.reqComponent.type.program.credits.theme')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4106', '', '', 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Minimum Number of Credits> in <reqCompFieldType=kuali.reqComponent.field.type.value.freeform.text;reqCompFieldLabel=Theme>', 'kuali.reqComponent.type.program.credits.theme')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4206', '', '', 'en', 'KUALI.RULE.CATALOG', 'Student must successfully complete $intValue $NLHelper.getProperGrammar($intValue, "credit") in $fields.get("kuali.reqComponent.field.type.value.freeform.text")', 'kuali.reqComponent.type.program.credits.theme')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4306', '', '', 'en', 'KUALI.RULE.PREVIEW', 'Student must successfully complete $intValue $NLHelper.getProperGrammar($intValue, "credit") in $fields.get("kuali.reqComponent.field.type.value.freeform.text")', 'kuali.reqComponent.type.program.credits.theme')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4406', '', '', 'en', 'KUALI.RULE', 'Student must successfully complete $intValue $NLHelper.getProperGrammar($intValue, "credit") in $fields.get("kuali.reqComponent.field.type.value.freeform.text")', 'kuali.reqComponent.type.program.credits.theme')
/
insert into KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) values ('PC-406','kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.credits.theme')
/

--Student must successfully complete <n> courses from <theme>
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, TYPE_DESC, EFF_DT, EXPIR_DT, NAME) VALUES ('kuali.reqComponent.type.program.courses.theme', 'Student must successfully complete <n> courses from <theme>', SYSDATE, SYSDATE, 'Minimum theme approved courses')
/
insert into KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) values ('kuali.reqComponent.type.program.courses.theme', 'kuali.reqComponent.field.type.value.positive.integer')
/
insert into KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) values ('kuali.reqComponent.type.program.courses.theme', 'kuali.reqComponent.field.type.value.freeform.text')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4007', '', '', 'en', 'KUALI.RULE.EXAMPLE', 'Student must successfully complete 9 courses in music theory or analysis', 'kuali.reqComponent.type.program.courses.theme')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4107', '', '', 'en', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.value.positive.integer;reqCompFieldLabel=Minimum Number of Courses> in <reqCompFieldType=kuali.reqComponent.field.type.value.freeform.text;reqCompFieldLabel=Theme>', 'kuali.reqComponent.type.program.courses.theme')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4207', '', '', 'en', 'KUALI.RULE.CATALOG', 'Student must successfully complete $intValue $NLHelper.getProperGrammar($intValue, "course") in $fields.get("kuali.reqComponent.field.type.value.freeform.text")', 'kuali.reqComponent.type.program.courses.theme')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4307', '', '', 'en', 'KUALI.RULE.PREVIEW', 'Student must successfully complete $intValue $NLHelper.getProperGrammar($intValue, "course") in $fields.get("kuali.reqComponent.field.type.value.freeform.text")', 'kuali.reqComponent.type.program.courses.theme')
/
insert into KSST_REQ_COM_TYPE_NL_TMPL (ID, ATTR_NAME, ATTR_VALUE, LANGUAGE, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER) values ('4407', '', '', 'en', 'KUALI.RULE', 'Student must successfully complete $intValue $NLHelper.getProperGrammar($intValue, "course") in $fields.get("kuali.reqComponent.field.type.value.freeform.text")', 'kuali.reqComponent.type.program.courses.theme')
/
insert into KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID) values ('PC-407','kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.courses.theme')
/

