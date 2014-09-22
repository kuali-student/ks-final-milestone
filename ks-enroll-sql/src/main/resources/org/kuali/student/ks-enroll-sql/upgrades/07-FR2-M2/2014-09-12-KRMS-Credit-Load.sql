-- KSENROLL-14839: Course Registration Validations -- convert BestEffortCreditLoadProposition into a Term Resolver

-- Create Proposition Type
INSERT INTO KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.krms.proposition.type.max.credits','KS-SYS','simplePropositionTypeService','kuali.krms.prop.type.max.credits',0)
/

-- Course exceeds max credits proposition: "kuali.rule.max.credits"
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.name.max.credits','KS-SYS',null,'kuali.rule.max.credits','kuali.krms.type.check',0)
/

INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Check if course will cause credit load to go over the max allowed','S','kuali.prop.max.credits','kuali.rule.max.credits','kuali.krms.prop.type.max.credits',0)
/

UPDATE KRMS_RULE_T set PROP_ID = 'kuali.prop.max.credits' where RULE_ID = 'kuali.rule.max.credits'
/

-- Term Spec
insert into KRMS_TERM_SPEC_T (ACTV, DESC_TXT, NM, NMSPC_CD, TERM_SPEC_ID, TYP, VER_NBR)
  values ('Y', 'Credit Load Exceeded', 'BestEffortCreditLoad', 'KS-SYS', 'KS-KRMS-TS-BestEffortCreditLoad', 'java.lang.Boolean', 0)
/
-- Term
insert into KRMS_TERM_T (DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
  values ('Credit Load Exceeded Term', 'KS-KRMS-TERM-BestEffortCreditLoad', 'KS-KRMS-TS-BestEffortCreditLoad', 0)
/
-- Term Resolver
insert into KRMS_TERM_RSLVR_T (ACTV, NM, NMSPC_CD, OUTPUT_TERM_SPEC_ID, TERM_RSLVR_ID, TYP_ID, VER_NBR)
  values ('Y', 'BestEffortCreditLoad', 'KS-SYS', 'KS-KRMS-TS-BestEffortCreditLoad', 'KS-KRMS-TR-BestEffortCreditLoad', 'kuali.krms.termresolver.type.check', 0)
/

--Proposition Parameters
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('T', 'KS-KRMS-TERM-BestEffortCreditLoad', 'kuali.prop.max.credits', 'KS-KRMS-PPT-BestEffortCreditLoad', 1, 0)
/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('C', 'true', 'kuali.prop.max.credits', 'KS-KRMS-PPC-BestEffortCreditLoad', 2, 0)
/
insert into KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  values ('O', '=', 'kuali.prop.max.credits', 'KS-KRMS-PPO-BestEffortCreditLoad', 3, 0)
/

-- Update process check to use the term resolver instead of the proposition
UPDATE
  KSEN_PROCESS_CHECK
SET
  AGENDA_ID='kuali.rule.max.credits',
  VER_NBR = '2',
  UPDATETIME = TO_DATE( '2014-09-12', 'YYYY-MM-DD' )
WHERE
  ID = 'kuali.check.best.effort.credit.load'
/

-- Update the message key for adds
UPDATE
  KSEN_PROCESS_INSTRN
SET
  MESG_FORMATTED='"messageKey":"kuali.lpr.trans.message.credit.load.exceeded","maxCredits":$maxCredits',
  UPDATETIME = TO_DATE( '2014-09-12', 'YYYY-MM-DD' )
WHERE
  ID in ('c199fbd4-add1-4474-ac7e-2e28c109ccea',
         '01CC67C7-6A40-82C3-E050-007F010105C1')
/

-- Update the message key for updates
UPDATE
  KSEN_PROCESS_INSTRN
SET
  MESG_FORMATTED='"messageKey":"kuali.lpr.trans.message.credit.load.reached","maxCredits":$maxCredits',
  UPDATETIME = TO_DATE( '2014-09-12', 'YYYY-MM-DD' )
WHERE
  ID in ('01CC67C7-6A58-82C3-E050-007F010105C1',
         '01CC67C7-6A53-82C3-E050-007F010105C1')
/
