-- KSENROLL-14915: Registration Group is Offered Check

-- Check Rule type. (For propositions)
INSERT INTO KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.krms.prop.type.name.reggroup.offered','KS-SYS','simplePropositionTypeService','kuali.krms.prop.type.reggroup.offered',0)
/


-- Course already registered rule and proposition: "kuali.rule.course.already.registered"
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.name.reggroup.offered','KS-SYS',null,'kuali.rule.reggroup.offered','kuali.krms.type.check',0)
/

INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Check if Registration Group is Offered','S','kuali.prop.reggroup.offered','kuali.rule.reggroup.offered','kuali.krms.prop.type.reggroup.offered',0)
/

UPDATE KRMS_RULE_T set PROP_ID = 'kuali.prop.reggroup.offered' where RULE_ID = 'kuali.rule.reggroup.offered'
/


-- Term Spec
insert into KRMS_TERM_SPEC_T (ACTV, DESC_TXT, NM, NMSPC_CD, TERM_SPEC_ID, TYP, VER_NBR)
  values ('Y', 'Get state of Registration Group', 'RegGroupState', 'KS-SYS', 'KS-KRMS-TS-RegGroupState', 'java.lang.String', 0)
/
-- Term
insert into KRMS_TERM_T (DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
  values ('Get state of Registration Group Term', 'KS-KRMS-TERM-RegGroupState', 'KS-KRMS-TS-RegGroupState', 0)
/
-- Term Resolver
insert into KRMS_TERM_RSLVR_T (ACTV, NM, NMSPC_CD, OUTPUT_TERM_SPEC_ID, TERM_RSLVR_ID, TYP_ID, VER_NBR)
  values ('Y', 'RegGroupState', 'KS-SYS', 'KS-KRMS-TS-RegGroupState', 'KS-KRMS-TR-RegGroupState', 'kuali.krms.termresolver.type.check', 0)
/


--Proposition Parameters
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('T', 'KS-KRMS-TERM-RegGroupState', 'kuali.prop.reggroup.offered', 'KS-KRMS-PPT-RegGroupState', 1, 0)
/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('C', 'kuali.lui.registration.group.state.offered', 'kuali.prop.reggroup.offered', 'KS-KRMS-PPC-RegGroupState', 2, 0)
/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('O', '=', 'kuali.prop.reggroup.offered', 'KS-KRMS-PPO-RegGroupState', 3, 0)
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
   'Checks if Registration Group is currently Offered',
   'kuali.check.reggroup.offered',
   'Registration Group is Offered',
   '0cec338d-fe09-47dc-a005-e3f09a9c0e0d',
   'kuali.process.check.state.active',
   'kuali.process.check.type.rule.direct',
   'kuali.rule.reggroup.offered',
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
    'kuali.check.reggroup.offered',
    'Y',
    'Admin',
    TO_DATE( '2014-09-17', 'YYYY-MM-DD' ),
    TO_DATE( '2014-09-17', 'YYYY-MM-DD' ),
    'Y',
    'a4feecdb-ad72-4232-867f-292a3c74b192',
    'Course eligibility - Registration Group Offered',
    '"messageKey":"kuali.lpr.trans.message.reggroup.notoffered","state":"$RegGroupState"',
    '915ec9df-80cc-49ff-b64a-e0fb68968e4a',
    1,
    'kuali.process.course.eligible.for.add',
    'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction',
    'Admin',
    TO_DATE( '2014-09-17', 'YYYY-MM-DD' ),
    0,
    'N')
/

-- Process Instruction: add from waitlist request
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
    'kuali.check.reggroup.offered',
    'Y',
    'Admin',
    TO_DATE( '2014-09-17', 'YYYY-MM-DD' ),
    TO_DATE( '2014-09-17', 'YYYY-MM-DD' ),
    'Y',
    '17d66425-e895-40ab-991b-4b8ba43d43ae',
    'Course eligibility - Registration Group Offered',
    '"messageKey":"kuali.lpr.trans.message.reggroup.notoffered","state":"$RegGroupState"',
    'ace00cbb-edbf-46d5-b7cc-2ba55ed4c47c',
    1,
    'kuali.process.course.eligible.for.add.from.waitlist',
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
    'kuali.check.reggroup.offered',
    'Y',
    'Admin',
    TO_DATE( '2014-09-17', 'YYYY-MM-DD' ),
    TO_DATE( '2014-09-17', 'YYYY-MM-DD' ),
    'Y',
    '361b6b6f-26b8-4743-8fe6-09396237bc19',
    'Course eligibility - Registration Group Offered',
    '"messageKey":"kuali.lpr.trans.message.reggroup.notoffered","state":"$RegGroupState"',
    'b3799341-9ce5-439f-b4d3-e7c5094da88f',
    1,
    'kuali.process.course.eligible.for.waitlist.add',
    'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction',
    'Admin',
    TO_DATE( '2014-09-17', 'YYYY-MM-DD' ),
    0,
    'N')
/
