-- KSENROLL-14067: Course Repeatability - SQL Config for Not Already Taken

-- Check Rule type. (For propositions)
INSERT INTO KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.krms.proposition.type.course.already.taken','KS-SYS','simplePropositionTypeService','kuali.krms.prop.type.course.taken',0)
/


-- Course already taken rule and proposition: "kuali.rule.course.already.taken"
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.name.course.not.already.taken','KS-SYS',null,'kuali.rule.course.already.taken','kuali.krms.type.check',0)
/

INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Check if Student has already attempted a Course','S','kuali.prop.course.already.taken','kuali.rule.course.already.taken','kuali.krms.prop.type.course.taken',0)
/

UPDATE KRMS_RULE_T set PROP_ID = 'kuali.prop.course.already.taken' where RULE_ID = 'kuali.rule.course.already.taken'
/

-- Term Spec
insert into KRMS_TERM_SPEC_T (ACTV, DESC_TXT, NM, NMSPC_CD, TERM_SPEC_ID, TYP, VER_NBR)
  values ('Y', 'Number of times Student has attempted Course', 'CourseCompletedAttempts', 'KS-SYS', 'KS-KRMS-TS-CourseTotalAttempts', 'java.lang.Integer', 0)
/
-- Term
insert into KRMS_TERM_T (DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
  values ('Number of attempts Student has made for Course Term', 'KS-KRMS-TERM-CourseTotalAttempts', 'KS-KRMS-TS-CourseTotalAttempts', 0)
/
-- Term Resolver
insert into KRMS_TERM_RSLVR_T (ACTV, NM, NMSPC_CD, OUTPUT_TERM_SPEC_ID, TERM_RSLVR_ID, TYP_ID, VER_NBR)
  values ('Y', 'CourseTotalAttempts', 'KS-SYS', 'KS-KRMS-TS-CourseTotalAttempts', 'KS-KRMS-TR-CourseTotalAttempts', 'kuali.krms.termresolver.type.check', 0)
/


-- Term Spec
insert into KRMS_TERM_SPEC_T (ACTV, DESC_TXT, NM, NMSPC_CD, TERM_SPEC_ID, TYP, VER_NBR)
  values ('Y', 'Maximum Number of Course Attempts', 'GesIntegerValue', 'KS-SYS', 'KS-KRMS-TS-GesMaxRepeatable', 'java.lang.Integer', 0)
/
-- Term
insert into KRMS_TERM_T (DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
  values ('Maximum Number of Course Attempts Term', 'KS-KRMS-TERM-GesMaxRepeatable', 'KS-KRMS-TS-GesMaxRepeatable', 0)
/
-- Term Parameter: GES Parm
insert into KRMS_TERM_PARM_T (NM, TERM_ID, TERM_PARM_ID, VAL, VER_NBR)
  values ('kuali.term.parameter.type.ges.parameter.key', 'KS-KRMS-TERM-GesMaxRepeatable', 'KS-KRMS-TP-GesMaxRepeatable', 'kuali.ges.max.repeatable', 0)
/
-- Term Resolver
insert into KRMS_TERM_RSLVR_T (ACTV, NM, NMSPC_CD, OUTPUT_TERM_SPEC_ID, TERM_RSLVR_ID, TYP_ID, VER_NBR)
  values ('Y', 'GesIntegerValue', 'KS-SYS', 'KS-KRMS-TS-GesMaxRepeatable', 'KS-KRMS-TR-GesMaxRepeatable', 'kuali.krms.termresolver.type.check', 0)
/

--Proposition Parameters
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('T', 'KS-KRMS-TERM-CourseTotalAttempts', 'kuali.prop.course.already.taken', 'KS-KRMS-PPT-CourseTotalAttempts', 1, 0)
/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('T', 'KS-KRMS-TERM-GesMaxRepeatable', 'kuali.prop.course.already.taken', 'KS-KRMS-PPT-GesMaxRepeatable', 2, 0)
/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('O', '<', 'kuali.prop.course.already.taken', 'KS-KRMS-PPO-CourseTotalAttempts', 3, 0)
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
   'Checks if Student has already attempted a Course the max allowable times',
   'kuali.check.course.attempts',
   'Student has already taken for a Course',
   '017B05F2-8EA2-1762-E050-007F01010640',
   'kuali.process.check.state.active',
   'kuali.process.check.type.rule.direct',
   'kuali.rule.course.already.taken',
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
    'kuali.check.course.attempts',
    'Y',
    'Admin',
    TO_DATE( '2014-08-25', 'YYYY-MM-DD' ),
    TO_DATE( '2014-08-25', 'YYYY-MM-DD' ),
    'Y',
    '017B05F2-8EA3-1762-E050-007F01010640',
    'Course eligibility - Already Taken',
    '"messageKey":"kuali.lpr.trans.message.course.already.taken"',
    '017B05F2-8EA4-1762-E050-007F01010640',
    1,
    'kuali.process.registration.eligible.for.courses',
    'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction',
    'Admin',
    TO_DATE( '2014-08-25', 'YYYY-MM-DD' ),
    0,
    'N')
/
