
-- Check Rule type. (For propositions)
INSERT INTO KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.krms.prop.type.date.in.term.kdate','KS-SYS','simplePropositionTypeService','kuali.krms.prop.type.date.in.term.kdate',0)
/


-- KRMS Rule: Rule invoked by process check
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.reg.is.open.for.term','KS-SYS',null,'kuali.rule.reg.is.open.for.term','kuali.krms.type.check',0)
/

-- KRMS Proposition
INSERT INTO KRMS_PROP_T (DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Check if the current date falls within the registration open key dates for a term','S','kuali.prop.reg.is.open.for.term','kuali.rule.reg.is.open.for.term','kuali.krms.prop.type.date.in.term.kdate',0)
/

-- Update KRMS Rule to have FK on Proposition
UPDATE KRMS_RULE_T SET PROP_ID = 'kuali.prop.reg.is.open.for.term' WHERE RULE_ID = 'kuali.rule.reg.is.open.for.term'
/


-- Generic Term Spec: Doesn't know/care about the specific keydate being checked against.
--     KRMS expects one of its term resolvers to return the NM value from the getOutput() method.
INSERT INTO KRMS_TERM_SPEC_T (ACTV, DESC_TXT, NM, NMSPC_CD, TERM_SPEC_ID, TYP, VER_NBR)
  VALUES ('Y', 'Date is in term key date range', 'DateInTermKeyDate', 'KS-SYS', 'KS-KRMS-TS-DateInTermKeyDate', 'java.lang.Boolean', 0)
/

-- Term Resolver
INSERT INTO KRMS_TERM_RSLVR_T (ACTV, NM, NMSPC_CD, OUTPUT_TERM_SPEC_ID, TERM_RSLVR_ID, TYP_ID, VER_NBR)
  VALUES ('Y', 'DateInTermKeyDate', 'KS-SYS', 'KS-KRMS-TS-DateInTermKeyDate', 'KS-KRMS-TR-RegOpenForTerm', 'kuali.krms.termresolver.type.check', 0)
/

-- Term
INSERT INTO KRMS_TERM_T (DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
  VALUES ('Registration is open for a term', 'KS-KRMS-TERM-RegOpenForTerm', 'KS-KRMS-TS-DateInTermKeyDate', 0)
/

-- Term Params: Configure the specific term key date to check against.
INSERT INTO KRMS_TERM_PARM_T (NM, TERM_ID, TERM_PARM_ID, VAL, VER_NBR)
  VALUES ('kuali.term.parameter.type.keydate.typekey', 'KS-KRMS-TERM-RegOpenForTerm', 'KS-KRMS-TP-RegOpenForTerm', 'kuali.atp.milestone.regstrationservicesopen', 0)
/


-- Proposition Parameters. This tells the proposition how to evaluate.
-- Computes to: T[erm] O[perator] C[ondition] >> RegOpenForTerm = true
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('T', 'KS-KRMS-TERM-RegOpenForTerm', 'kuali.prop.reg.is.open.for.term', 'KS-KRMS-PPT-RegOpenForTerm', 1, 0)
/
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('C', 'true', 'kuali.prop.reg.is.open.for.term', 'KS-KRMS-PPC-RegOpenForTerm', 2, 0)
/
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('O', '=', 'kuali.prop.reg.is.open.for.term', 'KS-KRMS-PPO-RegOpenForTerm', 3, 0)
/




-- KSEN Process check - ProcessService check definition (class as opposed to an instantiated object)
INSERT INTO KSEN_PROCESS_CHECK (
  CREATEID,
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
  VER_NBR
) VALUES (
  'Admin',
  TO_DATE( '2014-07-07', 'YYYY-MM-DD' ),
  'Checks that the registration is open for a term',
  'kuali.check.reg.is.open.for.term',
  'Registration is open for a term',
  SYS_GUID(),
  'kuali.process.check.state.active',
  'kuali.process.check.type.rule.direct',
  'kuali.rule.reg.is.open.for.term',
  'Admin',
  TO_DATE( '2014-07-07', 'YYYY-MM-DD' ),
  0
)
/


-- Insert process for eligibility for term
INSERT INTO KSEN_PROCESS (
  CREATEID,
  CREATETIME,
  DESCR_PLAIN,
  ID,
  NAME,
  OBJ_ID,
  OWNER_ORG_ID,
  PROCESS_STATE,
  PROCESS_TYPE,
  UPDATEID,
  UPDATETIME,
  VER_NBR
) VALUES (
  'Admin',
  TO_DATE( '2014-07-07', 'YYYY-MM-DD' ),
  'Performs all the instructions that are associated with checking that a student is Eligible for a term',
  'kuali.process.registration.eligibility.for.term',
  'Eligible for Term',
  'd4d51709-a982-4dcf-9f42-a6b34e61786f',
  '222',
  'kuali.process.process.state.active',
  'kuali.process.process.type',
  'Admin',
  TO_DATE( '2014-07-07', 'YYYY-MM-DD' ),
  0
)
/


-- Process Instruction for term eligibility check - effectively an instantiation of a process check
INSERT INTO KSEN_PROCESS_INSTRN (
  APPLD_POPULATION_ID,
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
  WARNING_IND
) VALUES (
  'kuali.population.student.key.everyone',
  'kuali.check.reg.is.open.for.term',
  'Y',
  'Admin',
  TO_DATE( '2014-07-07', 'YYYY-MM-DD' ),
  TO_DATE( '2014-07-07', 'YYYY-MM-DD' ),
  'Y',
  '0ca5a2fc-f9ba-4ca6-9fc3-73484fec2361',
  'Registration eligibility - Term Registration Period Is Open',
  '8fdfa036-ea82-4443-8461-4c428a8ee013',
  1,
  'kuali.process.registration.eligibility.for.term',
  'kuali.process.instruction.state.active',
  'kuali.process.instruction.type.instruction',
  'Admin',
  TO_DATE( '2014-07-07', 'YYYY-MM-DD' ),
  0,
  'N'
)
/