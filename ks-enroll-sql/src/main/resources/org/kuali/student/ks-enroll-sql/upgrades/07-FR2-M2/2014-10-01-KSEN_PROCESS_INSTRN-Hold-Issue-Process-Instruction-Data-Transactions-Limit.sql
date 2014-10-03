--KSENROLL-15132 Create instruction data for Hold Issue Checks
INSERT INTO KSEN_PROCESS_INSTRN (APPLD_POPULATION_ID, CHECK_ID, CONT_ON_FAILED_IND, CREATEID, CREATETIME,
 EFF_DT, EXEMPTIBLE_IND, ID, MESG_FORMATTED, MESG_PLAIN, OBJ_ID, POSITION, PROCESS_ID, PROCESS_INSTRN_STATE, PROCESS_INSTRN_TYPE,
 UPDATEID, UPDATETIME, VER_NBR, WARNING_IND)
VALUES ('kuali.population.student.key.everyone', 'kuali.check.registration.transactions.limit', 'Y', 'Admin',
    SYSDATE, SYSDATE, 'Y', SYS_GUID(),
    '"messageKey":"kuali.lpr.trans.message.failed.holds.transactions.limit"', 'Registration eligibility - Holds',
    SYS_GUID(), 7, 'kuali.process.course.eligible.for.add', 'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction', 'Admin', SYSDATE, 1, 'N')
/
INSERT INTO KSEN_PROCESS_INSTRN (APPLD_POPULATION_ID, CHECK_ID, CONT_ON_FAILED_IND, CREATEID, CREATETIME,
 EFF_DT, EXEMPTIBLE_IND, ID, MESG_FORMATTED, MESG_PLAIN, OBJ_ID, POSITION, PROCESS_ID, PROCESS_INSTRN_STATE, PROCESS_INSTRN_TYPE,
 UPDATEID, UPDATETIME, VER_NBR, WARNING_IND)
VALUES ('kuali.population.student.key.everyone', 'kuali.check.registration.transactions.limit', 'Y', 'Admin',
    SYSDATE, SYSDATE, 'Y', SYS_GUID(),
    '"messageKey":"kuali.lpr.trans.message.failed.holds.transactions.limit"', 'Registration eligibility - Holds',
    SYS_GUID(), 7, 'kuali.process.course.eligible.for.waitlist.add', 'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction', 'Admin', SYSDATE, 1, 'N')
/
INSERT INTO KSEN_PROCESS_INSTRN (APPLD_POPULATION_ID, CHECK_ID, CONT_ON_FAILED_IND, CREATEID, CREATETIME,
 EFF_DT, EXEMPTIBLE_IND, ID, MESG_FORMATTED, MESG_PLAIN, OBJ_ID, POSITION, PROCESS_ID, PROCESS_INSTRN_STATE, PROCESS_INSTRN_TYPE,
 UPDATEID, UPDATETIME, VER_NBR, WARNING_IND)
VALUES ('kuali.population.student.key.everyone', 'kuali.check.registration.transactions.limit', 'Y', 'Admin',
    SYSDATE, SYSDATE, 'Y', SYS_GUID(),
    '"messageKey":"kuali.lpr.trans.message.failed.holds.transactions.limit"', 'Registration eligibility - Holds',
    SYS_GUID(), 7, 'kuali.process.course.eligible.for.edit', 'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction', 'Admin', SYSDATE, 1, 'N')
/
INSERT INTO KSEN_PROCESS_INSTRN (APPLD_POPULATION_ID, CHECK_ID, CONT_ON_FAILED_IND, CREATEID, CREATETIME,
 EFF_DT, EXEMPTIBLE_IND, ID, MESG_FORMATTED, MESG_PLAIN, OBJ_ID, POSITION, PROCESS_ID, PROCESS_INSTRN_STATE, PROCESS_INSTRN_TYPE,
 UPDATEID, UPDATETIME, VER_NBR, WARNING_IND)
VALUES ('kuali.population.student.key.everyone', 'kuali.check.registration.transactions.limit', 'Y', 'Admin',
    SYSDATE, SYSDATE, 'Y', SYS_GUID(),
    '"messageKey":"kuali.lpr.trans.message.failed.holds.transactions.limit"', 'Registration eligibility - Holds',
    SYS_GUID(), 7, 'kuali.process.course.eligible.for.waitlist.edit', 'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction', 'Admin', SYSDATE, 1, 'N')
/
INSERT INTO KSEN_PROCESS_INSTRN (APPLD_POPULATION_ID, CHECK_ID, CONT_ON_FAILED_IND, CREATEID, CREATETIME,
 EFF_DT, EXEMPTIBLE_IND, ID, MESG_FORMATTED, MESG_PLAIN, OBJ_ID, POSITION, PROCESS_ID, PROCESS_INSTRN_STATE, PROCESS_INSTRN_TYPE,
 UPDATEID, UPDATETIME, VER_NBR, WARNING_IND)
VALUES ('kuali.population.student.key.everyone', 'kuali.check.registration.transactions.limit', 'Y', 'Admin',
    SYSDATE, SYSDATE, 'Y', SYS_GUID(),
    '"messageKey":"kuali.lpr.trans.message.failed.holds.transactions.limit"', 'Registration eligibility - Holds',
    SYS_GUID(), 7, 'kuali.process.course.eligible.for.drop', 'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction', 'Admin', SYSDATE, 1, 'N')
/
INSERT INTO KSEN_PROCESS_INSTRN (APPLD_POPULATION_ID, CHECK_ID, CONT_ON_FAILED_IND, CREATEID, CREATETIME,
 EFF_DT, EXEMPTIBLE_IND, ID, MESG_FORMATTED, MESG_PLAIN, OBJ_ID, POSITION, PROCESS_ID, PROCESS_INSTRN_STATE, PROCESS_INSTRN_TYPE,
 UPDATEID, UPDATETIME, VER_NBR, WARNING_IND)
VALUES ('kuali.population.student.key.everyone', 'kuali.check.registration.transactions.limit', 'Y', 'Admin',
    SYSDATE, SYSDATE, 'Y', SYS_GUID(),
    '"messageKey":"kuali.lpr.trans.message.failed.holds.transactions.limit"', 'Registration eligibility - Holds',
    SYS_GUID(), 7, 'kuali.process.course.eligible.for.waitlist.drop', 'kuali.process.instruction.state.active',
    'kuali.process.instruction.type.instruction', 'Admin', SYSDATE, 1, 'N')
/

