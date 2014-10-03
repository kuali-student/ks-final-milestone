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
   TO_DATE( '2014-06-04', 'YYYY-MM-DD' ),
   'Checks for time conflicts in a Reg Request',
   'kuali.check.best.effort.time.conflict',
   'Does not have time conflicts',
   '73eb9dd9-9ea2-4340-9f85-6269e0efc421',
   'kuali.process.check.state.active',
   'kuali.process.check.type.rule.direct',
   'kuali.rule.best.effort.time.conflict',
   'Admin',
   TO_DATE( '2014-06-04', 'YYYY-MM-DD' ),
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
    'kuali.check.best.effort.time.conflict',
    'Y',
    'Admin',
    TO_DATE( '2014-06-04', 'YYYY-MM-DD' ),
    TO_DATE( '2014-06-04', 'YYYY-MM-DD' ),
    'Y',
    '897a081f-f56c-4bbe-a4ba-ef16810d14e5',
    'Registration eligibility - Time Conflict',
    'e5f88c09-fc47-4c0d-8b66-c04f1337054d',
    1,
    'kuali.process.registration.eligible.for.courses',
    'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction',
    'Admin',
    TO_DATE( '2014-06-04', 'YYYY-MM-DD' ),
    1,
    'N')
/
