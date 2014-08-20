
-- Check Rule type. (For propositions)
INSERT INTO KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
VALUES ('Y','kuali.krms.proposition.type.grade.for.course','KS-SYS','simplePropositionTypeService','kuali.krms.prop.type.grade.for.course',0)
/


-- Student has Grade for Course rule and proposition: "kuali.rule.grade.for.course"
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
VALUES ('Y','kuali.rule.name.grade.for.course','KS-SYS',null,'kuali.rule.grade.for.course','kuali.krms.type.check',0)
/

INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
VALUES ('Check if Student has a given Grade for a Course','S','kuali.prop.grade.for.course','kuali.rule.grade.for.course','kuali.krms.prop.type.grade.for.course',1)
/

UPDATE KRMS_RULE_T set PROP_ID = 'kuali.prop.grade.for.course' where RULE_ID = 'kuali.rule.grade.for.course'
/

-- Term Spec
insert into KRMS_TERM_SPEC_T (ACTV, DESC_TXT, NM, NMSPC_CD, TERM_SPEC_ID, TYP, VER_NBR)
values ('Y', 'Student has a given Grade for a Course', 'GradeForCourse', 'KS-SYS', 'KS-KRMS-TS-GradeForCourse', 'java.lang.Boolean', 1)
/
-- Term
insert into KRMS_TERM_T (DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
values ('Student has a given Grade for a Course Term', 'KS-KRMS-TERM-GradeForCourse', 'KS-KRMS-TS-GradeForCourse', 2)
/
-- Term Parameter: Grade Value
insert into KRMS_TERM_PARM_T (NM, TERM_ID, TERM_PARM_ID, VAL, VER_NBR)
values ('kuali.term.parameter.type.grade.id', 'KS-KRMS-TERM-GradeForCourse', 'KS-KRMS-TP-GradeForCourse-Grade', 'kuali.result.value.grade.admin.i', 2)
/
-- Term Resolver
insert into KRMS_TERM_RSLVR_T (ACTV, NM, NMSPC_CD, OUTPUT_TERM_SPEC_ID, TERM_RSLVR_ID, TYP_ID, VER_NBR)
values ('Y', 'GradeForCourse', 'KS-SYS', 'KS-KRMS-TS-GradeForCourse', 'KS-KRMS-TR-GradeForCourse', 'kuali.krms.termresolver.type.check', 0)
/


--Proposition Parameters
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
values ('C', 'true', 'kuali.prop.grade.for.course', 'KS-KRMS-PPC-GradeForCourse', 2, 5)
/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
values ('O', '=', 'kuali.prop.grade.for.course', 'KS-KRMS-PPO-GradeForCourse', 3, 5)
/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
values ('T', 'KS-KRMS-TERM-GradeForCourse', 'kuali.prop.grade.for.course', 'KS-KRMS-PPT-GradeForCourse', 1, 2)
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
   TO_DATE( '2014-08-19', 'YYYY-MM-DD' ),
   'Checks if Student has received an Incomplete for a Course',
   'kuali.check.course.with.incomplete.grade',
   'Student has an Incomplete Grade for a Course',
   'ffbc2f8f-ec40-49a6-8d9b-920f005265c0',
   'kuali.process.check.state.active',
   'kuali.process.check.type.rule.direct',
   'kuali.rule.grade.for.course',
   'Admin',
   TO_DATE( '2014-08-19', 'YYYY-MM-DD' ),
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
  'kuali.check.course.with.incomplete.grade',
  'Y',
  'Admin',
  TO_DATE( '2014-08-18', 'YYYY-MM-DD' ),
  TO_DATE( '2014-08-18', 'YYYY-MM-DD' ),
  'Y',
  '16b16397-051c-4ae2-add1-8a0351c05f65',
  'Course eligibility - Incomplete Grade for Course',
  '55014f8c-9d6d-4cc5-a15a-e5f2ee3e5e35',
  1,
  'kuali.process.registration.eligible.for.courses',
  'kuali.process.instruction.state.active',
  'kuali.process.instruction.type.instruction',
  'Admin',
  TO_DATE( '2014-08-18', 'YYYY-MM-DD' ),
  1,
  'N')
/
