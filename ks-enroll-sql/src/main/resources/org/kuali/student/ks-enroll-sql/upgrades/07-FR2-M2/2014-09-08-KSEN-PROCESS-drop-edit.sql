-- KSENROLL-14769: Course Registration Drop/Edit -- Process and Instructions

-- Check Rule type. (For propositions)
INSERT INTO KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.krms.proposition.type.drop.or.adj.open','KS-SYS','simplePropositionTypeService','kuali.krms.prop.type.drop.adj.open',0)
/

-- Course in drop or adjustment period proposition: "kuali.rule.name.drop.or.adjustment.is.open"
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.name.drop.or.adj.is.open','KS-SYS',null,'kuali.rule.drop.or.adj.is.open','kuali.krms.type.check',0)
/

INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Check if course is in the drop or adjustment period','S','kuali.prop.drop.or.adj.is.open','kuali.rule.drop.or.adj.is.open','kuali.krms.prop.type.drop.adj.open',0)
/

UPDATE KRMS_RULE_T set PROP_ID = 'kuali.prop.drop.or.adj.is.open' where RULE_ID = 'kuali.rule.drop.or.adj.is.open'
/

-- Term Spec
insert into KRMS_TERM_SPEC_T (ACTV, DESC_TXT, NM, NMSPC_CD, TERM_SPEC_ID, TYP, VER_NBR)
  values ('Y', 'Course in Drop or Adj Period', 'CourseInDropOrAdj', 'KS-SYS', 'KS-KRMS-TS-CourseInDropOrAdj', 'java.lang.Boolean', 0)
/
-- Term
insert into KRMS_TERM_T (DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
  values ('Course in Drop or Adj Period Term', 'KS-KRMS-TERM-CourseInDropOrAdj', 'KS-KRMS-TS-CourseInDropOrAdj', 0)
/
-- Term Resolver
insert into KRMS_TERM_RSLVR_T (ACTV, NM, NMSPC_CD, OUTPUT_TERM_SPEC_ID, TERM_RSLVR_ID, TYP_ID, VER_NBR)
  values ('Y', 'CourseInDropOrAdj', 'KS-SYS', 'KS-KRMS-TS-CourseInDropOrAdj', 'KS-KRMS-TR-CourseInDropOrAdj', 'kuali.krms.termresolver.type.check', 0)
/

--Proposition Parameters
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('T', 'KS-KRMS-TERM-CourseInDropOrAdj', 'kuali.prop.drop.or.adj.is.open', 'KS-KRMS-PPT-CourseInDropOrAdj', 1, 0)
/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('C', 'true', 'kuali.prop.drop.or.adj.is.open', 'KS-KRMS-PPC-CourseInDropOrAdj', 2, 0)
/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('O', '=', 'kuali.prop.drop.or.adj.is.open', 'KS-KRMS-PPO-CourseInDropOrAdj', 3, 0)
/

-- Student Eligible to drop a registered course --
INSERT INTO KSEN_PROCESS (CREATEID, CREATETIME, DESCR_PLAIN, ID, NAME, OBJ_ID, OWNER_ORG_ID, PROCESS_STATE, PROCESS_TYPE, UPDATEID, UPDATETIME, VER_NBR )
  VALUES ('Admin',
          TO_DATE( '2014-08-29', 'YYYY-MM-DD' ),
          'Performs all the instructions that are associated with checking that a student is Eligible to drop a registered course',
          'kuali.process.course.eligible.for.drop',
          'Course Eligible for Drop',
          '01CC67C7-6A5B-82C3-E050-007F010105C1',
          '222',
          'kuali.process.process.state.active',
          'kuali.process.process.type',
          'Admin',
          TO_DATE( '2014-08-29', 'YYYY-MM-DD' ),
          0
  )
/

-- Student Eligible to drop a waitlisted course --
INSERT INTO KSEN_PROCESS (CREATEID, CREATETIME, DESCR_PLAIN, ID, NAME, OBJ_ID, OWNER_ORG_ID, PROCESS_STATE, PROCESS_TYPE, UPDATEID, UPDATETIME, VER_NBR )
  VALUES ('Admin',
          TO_DATE( '2014-08-29', 'YYYY-MM-DD' ),
          'Performs all the instructions that are associated with checking that a student is Eligible to drop a waitlisted course',
          'kuali.process.course.eligible.for.waitlist.drop',
          'Course Eligible for Drop from Waitlist',
          '01CC67C7-6A5A-82C3-E050-007F010105C1',
          '222',
          'kuali.process.process.state.active',
          'kuali.process.process.type',
          'Admin',
          TO_DATE( '2014-08-29', 'YYYY-MM-DD' ),
          0
  )
/

-- Process check to verify that the drop or adjustment period is open
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
   TO_DATE( '2014-09-08', 'YYYY-MM-DD' ),
   'Checks if either the drop or adjustment periods are open',
   'kuali.check.drop.or.adjustment.is.open',
   'Student has already attempted Course',
   '029131DA-AD5E-7808-E050-007F010124D0',
   'kuali.process.check.state.active',
   'kuali.process.check.type.rule.direct',
   'kuali.rule.drop.or.adj.is.open',
   'Admin',
   TO_DATE( '2014-09-08', 'YYYY-MM-DD' ),
   0)
/

-- Instruction to verify drop/adjustment period for registered courses
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
    'kuali.check.drop.or.adjustment.is.open',
    'Y',
    'Admin',
    TO_DATE( '2014-09-08', 'YYYY-MM-DD' ),
    TO_DATE( '2014-09-08', 'YYYY-MM-DD' ),
    'Y',
    '029131DA-AD5F-7808-E050-007F010124D0',
    'Drop eligibility - Drop Period is not open',
    '"messageKey":"kuali.lpr.trans.message.drop.period.closed"',
    '029131DA-AD60-7808-E050-007F010124D0',
    2,
    'kuali.process.course.eligible.for.drop',
    'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction',
    'Admin',
    TO_DATE( '2014-09-08', 'YYYY-MM-DD' ),
    0,
    'N')
/

-- Instruction to verify drop/adjustment period for waitlisted courses
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
    'kuali.check.drop.or.adjustment.is.open',
    'Y',
    'Admin',
    TO_DATE( '2014-09-08', 'YYYY-MM-DD' ),
    TO_DATE( '2014-09-08', 'YYYY-MM-DD' ),
    'Y',
    '029131DA-AD61-7808-E0500-07F010124D0',
    'Drop eligibility - Drop Period is not open',
    '"messageKey":"kuali.lpr.trans.message.drop.period.closed"',
    '029131DA-AD62-7808-E050-007F010124D0',
    2,
    'kuali.process.course.eligible.for.waitlist.drop',
    'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction',
    'Admin',
    TO_DATE( '2014-09-08', 'YYYY-MM-DD' ),
    0,
    'N')
/
UPDATE
  KSEN_PROCESS_INSTRN
SET
  MESG_FORMATTED='"messageKey":"kuali.lpr.trans.message.edit.period.closed"'
WHERE
  ID in ('01CC67C7-6A56-82C3-E050-007F010105C1', '01CC67C7-6A51-82C3-E050-007F010105C1')
/
