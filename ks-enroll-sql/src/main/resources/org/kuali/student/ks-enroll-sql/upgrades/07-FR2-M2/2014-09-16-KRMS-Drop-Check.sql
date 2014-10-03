-- KSENROLL-14860:  add appointment window and non-appt reg period checks to drop validation

-- KRMS Proposition -- non-appointment registration period
INSERT INTO KRMS_PROP_T (CMPND_SEQ_NO,DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('3','Check if the current date falls within the non-appointment registration period key dates for a term','S',
          'kuali.prop.non.appt.period.open.for.drop','kuali.drop.or.adjustment.is.open',
          'kuali.krms.prop.type.date.in.term.kdate',0)
/
-- KRMS Proposition -- early registration period (activity window)
INSERT INTO KRMS_PROP_T (CMPND_SEQ_NO,DESC_TXT,DSCRM_TYP_CD,PROP_ID,RULE_ID,TYP_ID,VER_NBR)
  VALUES ('4','Check if the current date falls within the early registration period key dates for a term','S',
          'kuali.prop.appt.window.is.open.for.drop','kuali.drop.or.adjustment.is.open',
          'kuali.krms.prop.type.appt.window.is.open',0)
/

-- Configure compound proposition
insert into KRMS_CMPND_PROP_PROPS_T (CMPND_PROP_ID, PROP_ID) values ('kuali.prop.drop.or.adjustment.is.open',
                                                                     'kuali.prop.drop.period.is.open')
/
insert into KRMS_CMPND_PROP_PROPS_T (CMPND_PROP_ID, PROP_ID) values ('kuali.prop.drop.or.adjustment.is.open',
                                                                     'kuali.prop.adjustment.period.is.open')
/
insert into KRMS_CMPND_PROP_PROPS_T (CMPND_PROP_ID, PROP_ID) values ('kuali.prop.drop.or.adjustment.is.open',
                                                                     'kuali.prop.non.appt.period.open.for.drop')
/
insert into KRMS_CMPND_PROP_PROPS_T (CMPND_PROP_ID, PROP_ID) values ('kuali.prop.drop.or.adjustment.is.open',
                                                                     'kuali.prop.appt.window.is.open.for.drop')
/

-- Term: Non-Appointment registration term (for drop)
INSERT INTO KRMS_TERM_T (DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
  VALUES ('Non-appt reg period is open for a term for drop', 'KS-KRMS-TERM-NonApptRegOpenForTermDrop',
          'KS-KRMS-TS-CurrentCourseWithinKeydate', 0)
/

-- Term Params: Configure the specific term key date to check against.
INSERT INTO KRMS_TERM_PARM_T (NM, TERM_ID, TERM_PARM_ID, VAL, VER_NBR)
  VALUES ('kuali.term.parameter.type.keydate.typekey', 'KS-KRMS-TERM-NonApptRegOpenForTermDrop',
          'KS-KRMS-TP-NonApptRegOpenForTermDrop', 'kuali.atp.milestone.earlyregistrationperiod.nonappointment', 0)
/

-- Proposition Parameters. This tells the proposition how to evaluate.
-- Computes to: T[erm] O[perator] C[ondition] >> NonApptRegOpenForTerm = true
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('T', 'KS-KRMS-TERM-NonApptRegOpenForTermDrop', 'kuali.prop.non.appt.period.open.for.drop',
          'KS-KRMS-PPT-NonApptRegOpenForTermDrop', 1, 0)
/
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('C', 'true', 'kuali.prop.non.appt.period.open.for.drop', 'KS-KRMS-PPC-NonApptRegOpenForTermDrop', 2, 0)
/
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('O', '=', 'kuali.prop.non.appt.period.open.for.drop', 'KS-KRMS-PPO-NonApptRegOpenForTermDrop', 3, 0)
/

-- Term: Early registration term (appointment window) for drop
INSERT INTO KRMS_TERM_T (DESC_TXT, TERM_ID, TERM_SPEC_ID, VER_NBR)
  VALUES ('Early registration is open for a term for drop', 'KS-KRMS-TERM-AppointmentWindowDrop',
          'KS-KRMS-TS-AppointmentWindow', 0)
/

-- Proposition Parameters. This tells the proposition how to evaluate.
-- Computes to: T[erm] O[perator] C[ondition] >> NonApptRegOpenForTerm = true
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('T', 'KS-KRMS-TERM-AppointmentWindowDrop', 'kuali.prop.appt.window.is.open.for.drop',
          'KS-KRMS-PPT-AppointmentWindowDrop', 1, 0)
/
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('C', 'true', 'kuali.prop.appt.window.is.open.for.drop', 'KS-KRMS-PPC-AppointmentWindowDrop', 2, 0)
/
INSERT INTO KRMS_PROP_PARM_T (PARM_TYP_CD, PARM_VAL, PROP_ID, PROP_PARM_ID, SEQ_NO, VER_NBR)
  VALUES ('O', '=', 'kuali.prop.appt.window.is.open.for.drop', 'KS-KRMS-PPO-AppointmentWindowDrop', 3, 0)
/

-- Update process checks to use the new compound proposition
UPDATE
  KSEN_PROCESS_CHECK
SET
  AGENDA_ID='kuali.drop.or.adjustment.is.open',
  VER_NBR = '1',
  UPDATETIME = TO_DATE( '2014-09-16', 'YYYY-MM-DD' )
WHERE
  ID = 'kuali.check.drop.or.adjustment.is.open'
/