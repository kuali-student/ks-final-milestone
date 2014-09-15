-- KSENROLL-14857: Add/Edit Date Check

-- Early Registration (appointment window) Rule type.
INSERT INTO KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.krms.proposition.type.appointment.window.is.open','KS-SYS','simplePropositionTypeService',
          'kuali.krms.prop.type.appt.window.is.open',0)
/

-- KRMS Rule: Rule invoked by process check
INSERT INTO KRMS_RULE_T (ACTV,NM,NMSPC_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.rule.name.add.edit.date','KS-SYS',null,'kuali.rule.add.edit.date','kuali.krms.type.check',0)
/

-- KRMS Proposition -- OR operator
INSERT INTO KRMS_PROP_T (CMPND_OP_CD, DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('|','Must meet 1 of the following','C','kuali.prop.add.edit.date','kuali.rule.add.edit.date','10077',0)
/
-- KRMS Proposition -- schedule adjustment period
INSERT INTO KRMS_PROP_T (CMPND_SEQ_NO,DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('1','Check if the current date falls within the schedule adjustment period key dates for a term','S',
          'kuali.prop.adj.period.is.open.for.add','kuali.rule.add.edit.date',
          'kuali.krms.prop.type.date.in.term.kdate',0)
/
-- KRMS Proposition -- non-appointment registration period
INSERT INTO KRMS_PROP_T (CMPND_SEQ_NO,DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('2','Check if the current date falls within the non-appointment registration period key dates for a term','S',
          'kuali.prop.non.appt.period.is.open','kuali.rule.add.edit.date',
          'kuali.krms.prop.type.date.in.term.kdate',0)
/
-- KRMS Proposition -- early registration period (activity window)
INSERT INTO KRMS_PROP_T (CMPND_SEQ_NO,DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('3','Check if the current date falls within the early registration period key dates for a term','S',
          'kuali.prop.appointment.window.is.open','kuali.rule.add.edit.date','kuali.krms.prop.type.appt.window.is.open',
          0)
/

-- Configure compound proposition
insert into KRMS_CMPND_PROP_PROPS_T (CMPND_PROP_ID, PROP_ID) values ('kuali.prop.add.edit.date',
                                                                     'kuali.prop.adjustment.period.is.open')
/
insert into KRMS_CMPND_PROP_PROPS_T (CMPND_PROP_ID, PROP_ID) values ('kuali.prop.add.edit.date',
                                                                     'kuali.prop.non.appt.period.is.open')
/
insert into KRMS_CMPND_PROP_PROPS_T (CMPND_PROP_ID, PROP_ID) values ('kuali.prop.add.edit.date',
                                                                     'kuali.prop.appointment.window.is.open')
/

-- Update KRMS Rule to have FK on Proposition
UPDATE KRMS_RULE_T SET PROP_ID = 'kuali.prop.add.edit.date' WHERE RULE_ID = 'kuali.rule.add.edit.date'
/

-- Schedule Adjustment Term (for add/edit)
INSERT INTO KRMS_TERM_T (DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
  VALUES ('Schedule Adjustment is open for a term (add)', 'KS-KRMS-TERM-AdjustmentOpenForTermAdd',
          'KS-KRMS-TS-DateInTermKeyDate', 0)
/

-- Term Params: Configure the specific term key date to check against.
INSERT INTO KRMS_TERM_PARM_T (NM, TERM_ID, TERM_PARM_ID, VAL, VER_NBR)
  VALUES ('kuali.term.parameter.type.keydate.typekey', 'KS-KRMS-TERM-AdjustmentOpenForTermAdd',
          'KS-KRMS-TP-AdjustmentOpenForTermAdd', 'kuali.atp.milestone.scheduleadjustmentperiod', 0)
/

-- Proposition Parameters. This tells the proposition how to evaluate.
-- Computes to: T[erm] O[perator] C[ondition] >> ScheduleAdjustmentOpenForTerm = true
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('T', 'KS-KRMS-TERM-AdjustmentOpenForTermAdd', 'kuali.prop.adj.period.is.open.for.add',
          'KS-KRMS-PPT-AdjustmentOpenForTermAdd', 1, 0)
/
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('C', 'true', 'kuali.prop.adj.period.is.open.for.add', 'KS-KRMS-PPC-AdjustmentOpenForTermAdd', 2, 0)
/
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('O', '=', 'kuali.prop.adj.period.is.open.for.add', 'KS-KRMS-PPO-AdjustmentOpenForTermAdd', 3, 0)
/

-- Non-Appointment registration term
INSERT INTO KRMS_TERM_T (DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
  VALUES ('Non-appt reg period is open for a term', 'KS-KRMS-TERM-NonApptRegOpenForTerm',
          'KS-KRMS-TS-DateInTermKeyDate', 0)
/

-- Term Params: Configure the specific term key date to check against.
INSERT INTO KRMS_TERM_PARM_T (NM, TERM_ID, TERM_PARM_ID, VAL, VER_NBR)
  VALUES ('kuali.term.parameter.type.keydate.typekey', 'KS-KRMS-TERM-NonApptRegOpenForTerm',
          'KS-KRMS-TP-NonApptRegOpenForTerm', 'kuali.atp.milestone.earlyregistrationperiod.nonappointment', 0)
/

-- Proposition Parameters. This tells the proposition how to evaluate.
-- Computes to: T[erm] O[perator] C[ondition] >> NonApptRegOpenForTerm = true
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('T', 'KS-KRMS-TERM-NonApptRegOpenForTerm', 'kuali.prop.non.appt.period.is.open',
          'KS-KRMS-PPT-NonApptRegOpenForTerm', 1, 0)
/
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('C', 'true', 'kuali.prop.non.appt.period.is.open', 'KS-KRMS-PPC-NonApptRegOpenForTerm', 2, 0)
/
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('O', '=', 'kuali.prop.non.appt.period.is.open', 'KS-KRMS-PPO-NonApptRegOpenForTerm', 3, 0)
/

-- Term Spec: Early registration term (appointment window)
insert into KRMS_TERM_SPEC_T (ACTV, DESC_TXT, NM, NMSPC_CD, TERM_SPEC_ID, TYP, VER_NBR)
  values ('Y', 'Early registration is open for a term', 'AppointmentWindow', 'KS-SYS', 'KS-KRMS-TS-AppointmentWindow',
          'java.lang.Boolean', 0)
/
-- Term: Early registration term (appointment window)
INSERT INTO KRMS_TERM_T (DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
  VALUES ('Early registration is open for a term', 'KS-KRMS-TERM-AppointmentWindow',
          'KS-KRMS-TS-AppointmentWindow', 0)
/
-- Term Resolver: Early registration term (appointment window)
insert into KRMS_TERM_RSLVR_T (ACTV, NM, NMSPC_CD, OUTPUT_TERM_SPEC_ID, TERM_RSLVR_ID, TYP_ID, VER_NBR)
  values ('Y', 'AppointmentWindow', 'KS-SYS', 'KS-KRMS-TS-AppointmentWindow', 'KS-KRMS-TR-AppointmentWindow',
          'kuali.krms.termresolver.type.check', 0)
/

-- Proposition Parameters. This tells the proposition how to evaluate.
-- Computes to: T[erm] O[perator] C[ondition] >> NonApptRegOpenForTerm = true
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('T', 'KS-KRMS-TERM-AppointmentWindow', 'kuali.prop.appointment.window.is.open',
          'KS-KRMS-PPT-AppointmentWindow', 1, 0)
/
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('C', 'true', 'kuali.prop.appointment.window.is.open', 'KS-KRMS-PPC-AppointmentWindow', 2, 0)
/
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('O', '=', 'kuali.prop.appointment.window.is.open', 'KS-KRMS-PPO-AppointmentWindow', 3, 0)
/

-- Update process checks to use the new compound proposition
UPDATE
  KSEN_PROCESS_CHECK
SET
  AGENDA_ID='kuali.rule.add.edit.date',
  VER_NBR = '2',
  DESCR_PLAIN = 'Checks for registration open for add/edit in a Reg Request',
  UPDATETIME = TO_DATE( '2014-09-15', 'YYYY-MM-DD' )
WHERE
  ID = 'kuali.check.registration.open'
/