
-- KRMS Rule: Rule invoked by process check
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.drop.or.adjustment.is.open','KS-SYS',null,'kuali.drop.or.adjustment.is.open','kuali.krms.type.check',0)
/

-- KRMS Proposition
INSERT INTO KRMS_PROP_T (CMPND_OP_CD, DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('|','Must meet 1 of the following','C','kuali.prop.drop.or.adjustment.is.open','kuali.drop.or.adjustment.is.open','10077',0)
/

-- KRMS Proposition
INSERT INTO KRMS_PROP_T (CMPND_SEQ_NO,DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('1','Check if the current date falls within the drop period key dates for a term','S','kuali.prop.drop.period.is.open','kuali.drop.or.adjustment.is.open','kuali.krms.prop.type.date.in.term.kdate',0)
/

-- KRMS Proposition
INSERT INTO KRMS_PROP_T (CMPND_SEQ_NO,DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('2','Check if the current date falls within the schedule adjustment period key dates for a term','S','kuali.prop.adjustment.period.is.open','kuali.drop.or.adjustment.is.open','kuali.krms.prop.type.date.in.term.kdate',0)
/

-- Update KRMS Rule to have FK on Proposition
UPDATE KRMS_RULE_T SET PROP_ID = 'kuali.prop.drop.or.adjustment.is.open' WHERE RULE_ID = 'kuali.drop.or.adjustment.is.open'
/

-- Term
INSERT INTO KRMS_TERM_T (DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
  VALUES ('Drop period is open for a term', 'KS-KRMS-TERM-DropPeriodOpenForTerm', 'KS-KRMS-TS-CurrentCourseWithinKeydate', 0)
/

-- Term Params: Configure the specific term key date to check against.
INSERT INTO KRMS_TERM_PARM_T (NM, TERM_ID, TERM_PARM_ID, VAL, VER_NBR)
  VALUES ('kuali.term.parameter.type.keydate.typekey', 'KS-KRMS-TERM-DropPeriodOpenForTerm', 'KS-KRMS-TP-DropPeriodOpenForTerm', 'kuali.atp.milestone.dropperiod', 0)
/

-- Proposition Parameters. This tells the proposition how to evaluate.
-- Computes to: T[erm] O[perator] C[ondition] >> DropPeriodOpenForTerm = true
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('T', 'KS-KRMS-TERM-DropPeriodOpenForTerm', 'kuali.prop.drop.period.is.open', 'KS-KRMS-PPT-DropPeriodOpenForTerm', 1, 0)
/
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('C', 'true', 'kuali.prop.drop.period.is.open', 'KS-KRMS-PPC-DropPeriodOpenForTerm', 2, 0)
/
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('O', '=', 'kuali.prop.drop.period.is.open', 'KS-KRMS-PPO-DropPeriodOpenForTerm', 3, 0)
/

  -- Term
INSERT INTO KRMS_TERM_T (DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
  VALUES ('Schedule Adjustment is open for a term', 'KS-KRMS-TERM-AdjustmentOpenForTerm', 'KS-KRMS-TS-CurrentCourseWithinKeydate', 0)
/

-- Term Params: Configure the specific term key date to check against.
INSERT INTO KRMS_TERM_PARM_T (NM, TERM_ID, TERM_PARM_ID, VAL, VER_NBR)
  VALUES ('kuali.term.parameter.type.keydate.typekey', 'KS-KRMS-TERM-AdjustmentOpenForTerm', 'KS-KRMS-TP-ScheduleAdjustmentOpenForTerm', 'kuali.atp.milestone.scheduleadjustmentperiod', 0)
/

-- Proposition Parameters. This tells the proposition how to evaluate.
-- Computes to: T[erm] O[perator] C[ondition] >> ScheduleAdjustmentOpenForTerm = true
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('T', 'KS-KRMS-TERM-AdjustmentOpenForTerm', 'kuali.prop.adjustment.period.is.open', 'KS-KRMS-PPT-AdjustmentOpenForTerm', 1, 0)
/
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('C', 'true', 'kuali.prop.adjustment.period.is.open', 'KS-KRMS-PPC-AdjustmentOpenForTerm', 2, 0)
/
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('O', '=', 'kuali.prop.adjustment.period.is.open', 'KS-KRMS-PPO-AdjustmentOpenForTerm', 3, 0)
/