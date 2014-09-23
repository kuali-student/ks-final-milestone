-- KSENROLL-14916: Term Repeatability Check

-- Check Rule type. (For propositions)
INSERT INTO KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.krms.proposition.type.course.already.registered','KS-SYS','simplePropositionTypeService','kuali.krms.prop.type.course.registered',0)
/


-- Course already registered rule and proposition: "kuali.rule.course.already.registered"
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.name.course.not.already.registered','KS-SYS',null,'kuali.rule.course.already.registered','kuali.krms.type.check',0)
/

INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Check if Student has already registered for a Course','S','kuali.prop.course.already.registered','kuali.rule.course.already.registered','kuali.krms.prop.type.course.registered',0)
/

UPDATE KRMS_RULE_T set PROP_ID = 'kuali.prop.course.already.registered' where RULE_ID = 'kuali.rule.course.already.registered'
/

-- Term Spec
insert into KRMS_TERM_SPEC_T (ACTV, DESC_TXT, NM, NMSPC_CD, TERM_SPEC_ID, TYP, VER_NBR)
  values ('Y', 'Number of Registrations Student has for Course', 'CourseRegisteredCount', 'KS-SYS', 'KS-KRMS-TS-CourseRegisteredCount', 'java.lang.Integer', 0)
/
-- Term
insert into KRMS_TERM_T (DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
  values ('Number of Registrations Student has for Course Term', 'KS-KRMS-TERM-CourseRegisteredCount', 'KS-KRMS-TS-CourseRegisteredCount', 0)
/
-- Term Resolver
insert into KRMS_TERM_RSLVR_T (ACTV, NM, NMSPC_CD, OUTPUT_TERM_SPEC_ID, TERM_RSLVR_ID, TYP_ID, VER_NBR)
  values ('Y', 'CourseRegisteredCount', 'KS-SYS', 'KS-KRMS-TS-CourseRegisteredCount', 'KS-KRMS-TR-CourseRegisteredCount', 'kuali.krms.termresolver.type.check', 0)
/


--Proposition Parameters
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('T', 'KS-KRMS-TERM-CourseRegisteredCount', 'kuali.prop.course.already.registered', 'KS-KRMS-PPT-CourseRegisteredCount', 1, 0)
/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('C', '0', 'kuali.prop.course.already.registered', 'KS-KRMS-PPC-CourseRegisteredCount', 2, 0)
/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('O', '=', 'kuali.prop.course.already.registered', 'KS-KRMS-PPO-CourseRegisteredCount', 3, 0)
/

-- Process check
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
   TO_DATE( '2014-09-17', 'YYYY-MM-DD' ),
   'Checks if Student has already registered for a Course',
   'kuali.check.course.for.registration',
   'Student has already registered for a Course',
   '01785E5F-7AEB-6945-E050-007F01011133',
   'kuali.process.check.state.active',
   'kuali.process.check.type.rule.direct',
   'kuali.rule.course.already.registered',
   'Admin',
   TO_DATE( '2014-09-17', 'YYYY-MM-DD' ),
   0)
/

-- Process Instruction: add request
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
    'kuali.check.course.for.registration',
    'Y',
    'Admin',
    TO_DATE( '2014-09-17', 'YYYY-MM-DD' ),
    TO_DATE( '2014-09-17', 'YYYY-MM-DD' ),
    'Y',
    '0175C507-5D98-1DD7-E050-007F0101090E',
    'Course eligibility - Already Registered',
    '"messageKey":"kuali.lpr.trans.message.course.already.registered"',
    '0175C507-5D99-1DD7-E050-007F0101090E',
    1,
    'kuali.process.course.eligible.for.add',
    'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction',
    'Admin',
    TO_DATE( '2014-09-17', 'YYYY-MM-DD' ),
    0,
    'N')
/

-- Process Instruction: add to waitlist request
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
    'kuali.check.course.for.registration',
    'Y',
    'Admin',
    TO_DATE( '2014-09-17', 'YYYY-MM-DD' ),
    TO_DATE( '2014-09-17', 'YYYY-MM-DD' ),
    'Y',
    '03461691-5C4D-9DFE-E050-007F0101283B',
    'Course eligibility - Already Registered',
    '"messageKey":"kuali.lpr.trans.message.course.already.registered"',
    '03461691-5C4E-9DFE-E050-007F0101283B',
    1,
    'kuali.process.course.eligible.for.waitlist.add',
    'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction',
    'Admin',
    TO_DATE( '2014-09-17', 'YYYY-MM-DD' ),
    0,
    'N')
/
