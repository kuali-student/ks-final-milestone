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
   TO_DATE( '2014-06-01', 'YYYY-MM-DD' ),
   'Checks that the student has not exceeded her credit limit',
   'kuali.check.best.effort.credit.load',
   'Does not exceed credit limit',
   'efea5081-31ab-43b8-aae0-68dc380a2349',
   'kuali.process.check.state.active',
   'kuali.process.check.type.rule.direct',
   'kuali.rule.best.effort.credit.load',
   'Admin',
   TO_DATE( '2014-06-01', 'YYYY-MM-DD' ),
   1)
/
-- Get rid of old initial instruction
DELETE FROM KSEN_PROCESS_INSTRN WHERE PROCESS_ID='kuali.process.registration.eligible.for.courses' AND POSITION='1'
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
    'kuali.check.best.effort.credit.load',
    'Y',
    'Admin',
    TO_DATE( '2014-06-01', 'YYYY-MM-DD' ),
    TO_DATE( '2014-06-01', 'YYYY-MM-DD' ),
    'Y',
    'c199fbd4-add1-4474-ac7e-2e28c109ccea',
    'Registration eligibility - Credit Limit Exceeded',
    '4dde773f-8296-4e0c-ba61-78e6e4c7894b',
    1,
    'kuali.process.registration.eligible.for.courses',
    'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction',
    'Admin',
    TO_DATE( '2014-06-01', 'YYYY-MM-DD' ),
    1,
    'N')
/
