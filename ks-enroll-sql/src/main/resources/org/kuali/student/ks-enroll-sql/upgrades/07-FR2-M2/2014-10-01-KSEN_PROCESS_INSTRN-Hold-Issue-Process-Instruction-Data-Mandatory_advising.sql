--KSENROLL-15088 Create instruction data for Hold Issue Checks
INSERT INTO KSEN_PROCESS_INSTRN (APPLD_POPULATION_ID, CHECK_ID, CONT_ON_FAILED_IND, CREATEID, CREATETIME,
 EFF_DT, EXEMPTIBLE_IND, ID, MESG_FORMATTED, MESG_PLAIN, OBJ_ID, POSITION, PROCESS_ID, PROCESS_INSTRN_STATE, PROCESS_INSTRN_TYPE,
 UPDATEID, UPDATETIME, VER_NBR, WARNING_IND)
VALUES ('kuali.population.student.key.everyone', 'kuali.check.mandatory.advising', 'Y', 'Admin',
    SYSDATE, SYSDATE, 'Y', SYS_GUID(),
    '"messageKey":"kuali.lpr.trans.message.failed.holds.mandatory.advising"', 'Registration eligibility - Holds',
    SYS_GUID(), 7, 'kuali.process.registration.eligible.for.term', 'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction', 'Admin', SYSDATE, 1, 'N')
/
