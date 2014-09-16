INSERT INTO KSEN_PROCESS_CHECK (CREATEID, CREATETIME, DESCR_PLAIN, ID, NAME, OBJ_ID,
 PROCESS_CHECK_STATE, PROCESS_CHECK_TYPE, AGENDA_ID, UPDATEID, UPDATETIME, VER_NBR)
  VALUES
  ('Admin', TO_DATE( '2014-06-01', 'YYYY-MM-DD' ),
   'Checks that the student is passing all requisites.',
   'kuali.check.requisites',
   'Check course requisites',
   '94dacfc5-288a-4ea1-afb6-6d9c0b030229',
   'kuali.process.check.state.active',
   'kuali.process.check.type.rule.direct',
   'kuali.rule.course.requisites',
   'Admin',
   TO_DATE( '2014-06-01', 'YYYY-MM-DD' ),
   1)
/

INSERT INTO KSEN_PROCESS_INSTRN (APPLD_POPULATION_ID, CHECK_ID, CONT_ON_FAILED_IND, CREATEID, CREATETIME,
 EFF_DT, EXEMPTIBLE_IND, ID, MESG_PLAIN, OBJ_ID, POSITION, PROCESS_ID, PROCESS_INSTRN_STATE, PROCESS_INSTRN_TYPE,
 UPDATEID, UPDATETIME, VER_NBR, WARNING_IND)
  VALUES (
    'kuali.population.student.key.everyone',
    'kuali.check.requisites',
    'Y',
    'Admin',
    TO_DATE( '2014-06-01', 'YYYY-MM-DD' ),
    TO_DATE( '2014-06-01', 'YYYY-MM-DD' ),
    'Y',
    'kuali.eligibility.requisites.instruction',
    'Registration eligibility - Requisites',
    '807cc529-58df-4800-a0ec-a31decb0ed86',
    7,
    'kuali.process.course.eligible.for.add',
    'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction',
    'Admin',
    TO_DATE( '2014-06-01', 'YYYY-MM-DD' ),
    1,
    'N')
/