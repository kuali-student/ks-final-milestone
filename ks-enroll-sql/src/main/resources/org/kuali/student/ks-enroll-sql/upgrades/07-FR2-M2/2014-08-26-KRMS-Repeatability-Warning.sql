-- KSENROLL-14067: Course Repeatability - SQL Config for Not Already Taken
-- This proposition sends a warning if a student is registering for a course for the last allowed time

-- Check Rule type. (For propositions)
INSERT INTO KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.krms.proposition.type.repeatability.warning','KS-SYS','simplePropositionTypeService','kuali.krms.prop.type.repeat.warn',0)
/


-- Course already taken rule and proposition: "kuali.rule.repeatability.warning"
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.name.repeatability.warning','KS-SYS',null,'kuali.rule.repeatability.warning','kuali.krms.type.check',0)
/

INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Check if Student has already attempted a Course','S','kuali.prop.repeatability.warning','kuali.rule.repeatability.warning','kuali.krms.prop.type.repeat.warn',0)
/

UPDATE KRMS_RULE_T set PROP_ID = 'kuali.prop.repeatability.warning' where RULE_ID = 'kuali.rule.repeatability.warning'
/

--Proposition Parameters
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('T', 'KS-KRMS-TERM-CourseTotalAttempts', 'kuali.prop.repeatability.warning', 'KS-KRMS-PPT-RepeatabilityWarning', 1, 0)
/
/*
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('T', 'KS-KRMS-TERM-GesMaxRepeatable', 'kuali.prop.repeatability.warning', 'KS-KRMS-PPT-GesMaxRepeatable', 2, 0)
/
*/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('C', '1', 'kuali.prop.repeatability.warning', 'KS-KRMS-PPC-RepeatabilityWarning', 1, 0)
/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('O', '<', 'kuali.prop.repeatability.warning', 'KS-KRMS-PPO-RepeatabilityWarning', 3, 0)
/


INSERT INTO KSEN_PROCESS_CHECK
(CREATEID,
 CREATETIME,
 DESCR_PLAIN,
 ID,
 NAME,
 OBJ_ID,
 PROCESS_CHECK_STATE,
 PROCESS_CHECK_TYPE,
 AGENDA_ID,
 UPDATEID,
 UPDATETIME,
 VER_NBR)
  VALUES
  ('Admin',
   TO_DATE( '2014-08-25', 'YYYY-MM-DD' ),
   'Checks if Student has already attempted a course and will reach the max',
   'kuali.check.course.attempts.for.warning',
   'Student has already attempted Course',
   '018C3BC6-26F8-B50C-E050-007F01010B99',
   'kuali.process.check.state.active',
   'kuali.process.check.type.rule.direct',
   'kuali.rule.repeatability.warning',
   'Admin',
   TO_DATE( '2014-08-25', 'YYYY-MM-DD' ),
   0)
/

INSERT INTO KSEN_PROCESS_INSTRN
(APPLD_POPULATION_ID,
 CHECK_ID,
 CONT_ON_FAILED_IND,
 CREATEID,
 CREATETIME,
 EFF_DT,
 EXEMPTIBLE_IND,
 ID,
 MESG_PLAIN,
 MESG_FORMATTED,
 OBJ_ID,
 POSITION,
 PROCESS_ID,
 PROCESS_INSTRN_STATE,
 PROCESS_INSTRN_TYPE,
 UPDATEID,
 UPDATETIME,
 VER_NBR,
 WARNING_IND)
  VALUES (
    'kuali.population.student.key.everyone',
    'kuali.check.course.attempts.for.warning',
    'Y',
    'Admin',
    TO_DATE( '2014-08-26', 'YYYY-MM-DD' ),
    TO_DATE( '2014-08-26', 'YYYY-MM-DD' ),
    'Y',
    '018C3BC6-26F9-B50C-E050-007F01010B99',
    'Course eligibility - Already Taken Warning',
    '"messageKey":"kuali.lpr.trans.message.repeatability.warning"',
    '018C3BC6-26FA-B50C-E050-007F01010B99',
    1,
    'kuali.process.registration.eligible.for.courses',
    'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction',
    'Admin',
    TO_DATE( '2014-08-26', 'YYYY-MM-DD' ),
    0,
    'Y')
/
