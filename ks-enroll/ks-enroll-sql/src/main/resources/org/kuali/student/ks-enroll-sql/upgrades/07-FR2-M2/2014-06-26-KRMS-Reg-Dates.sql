
-- Check Rule type. (For propositions)
insert into KRMS_TYP_T (ACTV, NM, NMSPC_CD, SRVC_NM, TYP_ID, VER_NBR)
values ('Y', 'kuali.krms.termresolver.type.check.name', 'KS-SYS', 'checkTermresolverTypeService', 'kuali.krms.termresolver.type.check', 0)
/
INSERT INTO KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
VALUES ('Y','kuali.krms.proposition.type.course.in.keydate','KS-SYS','simplePropositionTypeService','kuali.krms.prop.type.course.in.keydate',0)
/


-- Current Course is in Keydate rule and proposition: "kuali.rule.is.alive"
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
VALUES ('Y','kuali.rule.name.course.in.keydate','KS-SYS',null,'kuali.rule.course.in.keydate','kuali.krms.type.check',0)
/

INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
VALUES ('Check if Current Course is within it''s terms'' key date','S','kuali.prop.course.in.keydate','kuali.rule.course.in.keydate','kuali.krms.prop.type.course.in.keydate',1)
/

UPDATE KRMS_RULE_T set PROP_ID = 'kuali.prop.course.in.keydate' where RULE_ID = 'kuali.rule.course.in.keydate'
/

-- Term Spec
insert into KRMS_TERM_SPEC_T (ACTV, DESC_TXT, NM, NMSPC_CD, TERM_SPEC_ID, TYP, VER_NBR)
values ('Y', 'Current Course Within keyDate', 'CurrentCourseWithinKeydate', 'KS-SYS', 'KS-KRMS-TS-CurrentCourseWithinKeydate', 'java.lang.Boolean', 1)
/
-- Term
insert into KRMS_TERM_T (DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
values ('Current Course Within keyDate Term', 'KS-KRMS-TERM-CurrentCourseWithinKeydate', 'KS-KRMS-TS-CurrentCourseWithinKeydate', 2)
/

insert into KRMS_TERM_PARM_T (NM, TERM_ID, TERM_PARM_ID, VAL, VER_NBR)
values ('kuali.term.parameter.type.keydate.typekey', 'KS-KRMS-TERM-CurrentCourseWithinKeydate', 'KS-KRMS-TP-CurrentCourseWithinKeydate', 'kuali.atp.milestone.scheduleadjustmentperiod', 2)
/

insert into KRMS_TERM_RSLVR_T (ACTV, NM, NMSPC_CD, OUTPUT_TERM_SPEC_ID, TERM_RSLVR_ID, TYP_ID, VER_NBR)
values ('Y', 'CurrentCourseWithinKeydate', 'KS-SYS', 'KS-KRMS-TS-CurrentCourseWithinKeydate', 'KS-KRMS-TR-CurrentCourseWithinKeydate', 'kuali.krms.termresolver.type.check', 0)
/


--Proposition Parameters
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
values ('C', 'true', 'kuali.prop.course.in.keydate', 'KS-KRMS-PPC-CurrentCourseWithinKeydate', 2, 5)
/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
values ('O', '=', 'kuali.prop.course.in.keydate', 'KS-KRMS-PPO-CurrentCourseWithinKeydate', 3, 5)
/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
values ('T', 'KS-KRMS-TERM-CurrentCourseWithinKeydate', 'kuali.prop.course.in.keydate', 'KS-KRMS-PPT-CurrentCourseWithinKeydate', 1, 2)
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
   TO_DATE( '2014-07-02', 'YYYY-MM-DD' ),
   'Checks for registration open in a Reg Request',
   'kuali.check.registration.open',
   'Registration is Open for Course Offering',
   '6f42b46c-ad5b-4c05-a4c3-7defac500ff1',
   'kuali.process.check.state.active',
   'kuali.process.check.type.rule.direct',
   'kuali.rule.course.in.keydate',
   'Admin',
   TO_DATE( '2014-07-02', 'YYYY-MM-DD' ),
   1)
/

-- Add new one to replace it with hardcoded IDs (which makes it easier to update later on, rather than using SYS_GUID()
-- which alters the id each time it loads
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
  'kuali.check.registration.open',
  'Y',
  'Admin',
  TO_DATE( '2014-07-02', 'YYYY-MM-DD' ),
  TO_DATE( '2014-07-02', 'YYYY-MM-DD' ),
  'Y',
  '0f6e519c-7552-486b-aa0f-30595fa4d2ac',
  'Course eligibility - Registration is not open',
  '71ee4631-bf9f-49f5-a77d-4954376d2365',
  1,
  'kuali.process.registration.eligible.for.courses',
  'kuali.process.instruction.state.active',
  'kuali.process.instruction.type.instruction',
  'Admin',
  TO_DATE( '2014-07-02', 'YYYY-MM-DD' ),
  1,
  'N')
/
