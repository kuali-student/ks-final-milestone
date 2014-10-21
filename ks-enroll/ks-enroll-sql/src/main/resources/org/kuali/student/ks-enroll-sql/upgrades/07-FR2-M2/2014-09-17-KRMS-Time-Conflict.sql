-- KSENROLL-14840: Course Registration Validations -- convert BestEffortTimeConflictProposition into a Term Resolver

-- Create Proposition Type
INSERT INTO KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.krms.proposition.type.time.conflict','KS-SYS','simplePropositionTypeService',
          'kuali.krms.prop.type.time.conflict',0)
/

-- Course in drop or adjustment period proposition: "kuali.rule.name.drop.or.adjustment.is.open"
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.name.time.conflict','KS-SYS',null,'kuali.rule.time.conflict','kuali.krms.type.check',0)
/

INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Check if course has time conflicts with another course on the schedule or waitlist','S',
          'kuali.prop.time.conflict','kuali.rule.time.conflict','kuali.krms.prop.type.time.conflict',0)
/

UPDATE KRMS_RULE_T set PROP_ID = 'kuali.prop.time.conflict' where RULE_ID = 'kuali.rule.time.conflict'
/

-- Term Spec
insert into KRMS_TERM_SPEC_T (ACTV, DESC_TXT, NM, NMSPC_CD, TERM_SPEC_ID, TYP, VER_NBR)
  values ('Y', 'Time Conflict', 'BestEffortTimeConflict', 'KS-SYS', 'KS-KRMS-TS-BestEffortTimeConflict', 'java.lang.String', 0)
/
-- Term
insert into KRMS_TERM_T (DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
  values ('Time Conflict Term', 'KS-KRMS-TERM-BestEffortTimeConflict', 'KS-KRMS-TS-BestEffortTimeConflict', 0)
/
-- Term Resolver
insert into KRMS_TERM_RSLVR_T (ACTV, NM, NMSPC_CD, OUTPUT_TERM_SPEC_ID, TERM_RSLVR_ID, TYP_ID, VER_NBR)
  values ('Y', 'BestEffortTimeConflict', 'KS-SYS', 'KS-KRMS-TS-BestEffortTimeConflict', 'KS-KRMS-TR-BestEffortTimeConflict', 'kuali.krms.termresolver.type.check', 0)
/

--Proposition Parameters
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('T', 'KS-KRMS-TERM-BestEffortTimeConflict', 'kuali.prop.time.conflict', 'KS-KRMS-PPT-BestEffortTimeConflict', 1, 0)
/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('C', '{}', 'kuali.prop.time.conflict', 'KS-KRMS-PPC-BestEffortTimeConflict', 2, 0)
/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('O', '=', 'kuali.prop.time.conflict', 'KS-KRMS-PPO-BestEffortTimeConflict', 3, 0)
/

-- Update process check to use the term resolver instead of the proposition
UPDATE
  KSEN_PROCESS_CHECK
SET
  AGENDA_ID='kuali.rule.time.conflict',
  VER_NBR = '2',
  UPDATETIME = TO_DATE( '2014-09-17', 'YYYY-MM-DD' )
WHERE
  ID = 'kuali.check.best.effort.time.conflict'
/

-- Update the message key for adds
UPDATE
  KSEN_PROCESS_INSTRN
SET
  MESG_FORMATTED='"messageKey":"kuali.lpr.trans.message.time.conflict","conflictingCourses":$BestEffortTimeConflict',
  UPDATETIME = TO_DATE( '2014-09-17', 'YYYY-MM-DD' )
WHERE
  ID in ('897a081f-f56c-4bbe-a4ba-ef16810d14e5',
         '01CC67C7-6A5C-82C3-E050-007F010105C1',
         '01CC67C7-6A3C-82C3-E050-007F010105C1')
/
