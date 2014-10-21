-- Delete the old requisite rule.
UPDATE KRMS_RULE_T set PROP_ID = null where RULE_ID like 'kuali.rule.course.requisites'
/
DELETE from krms_rule_t where RULE_ID like 'kuali.rule.course.requisites'
/
DELETE from krms_prop_t where RULE_ID like 'kuali.rule.course.requisites'
/

-- Insert the check and instruction for antirequisites
INSERT INTO KSEN_PROCESS_CHECK (CREATEID, CREATETIME, DESCR_PLAIN, ID, NAME, OBJ_ID,
 PROCESS_CHECK_STATE, PROCESS_CHECK_TYPE, AGENDA_ID, UPDATEID, UPDATETIME, VER_NBR)
VALUES ('Admin', TO_DATE( '2014-06-01', 'YYYY-MM-DD' ), 'Checks that the student is passing all requisites.',
   'kuali.check.eligibility.antirequisites', 'Check course antirequisites', '94dacfc5-288a-4ea1-afb6-6d9c0b030229',
   'kuali.process.check.state.active', 'kuali.process.check.type.rule.direct', 'kuali.rule.course.antirequisites',
   'Admin', TO_DATE( '2014-06-01', 'YYYY-MM-DD' ), 1)
/

INSERT INTO KSEN_PROCESS_INSTRN (APPLD_POPULATION_ID, CHECK_ID, CONT_ON_FAILED_IND, CREATEID, CREATETIME,
 EFF_DT, EXEMPTIBLE_IND, ID, MESG_FORMATTED, MESG_PLAIN, OBJ_ID, POSITION, PROCESS_ID, PROCESS_INSTRN_STATE, PROCESS_INSTRN_TYPE,
 UPDATEID, UPDATETIME, VER_NBR, WARNING_IND)
VALUES ('kuali.population.student.key.everyone', 'kuali.check.eligibility.antirequisites', 'Y', 'Admin',
    TO_DATE( '2014-06-01', 'YYYY-MM-DD' ), TO_DATE( '2014-06-01', 'YYYY-MM-DD' ), 'Y', 'kuali.eligibility.antirequisites.instruction',
    '"messageKey":"kuali.lpr.trans.message.failed.antirequisites"', 'Registration eligibility - Anti-Requisites',
    '807cc529-58df-4800-a0ec-a31decb0ed86', 7, 'kuali.process.course.eligible.for.add', 'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction', 'Admin', TO_DATE( '2014-06-01', 'YYYY-MM-DD' ), 1, 'N')
/

-- Course Requisites check rule and proposition: "kuali.rule.course.requisites"
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.course.antirequisites','KS-SYS',null,'kuali.rule.course.antirequisites','kuali.krms.type.check',0)
/

INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Check course eligibility - anitrequisites','S','kuali.prop.course.antirequisites','kuali.rule.course.antirequisites','kuali.krms.type.custom',1)
/

UPDATE KRMS_RULE_T set PROP_ID = 'kuali.prop.course.antirequisites' where RULE_ID like 'kuali.rule.course.antirequisites'
/