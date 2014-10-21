--KSENROLL-14750 Create data for Hold Issue Checks
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if student is on probation / dismissal during the semester for which they are trying to register.',
         'kuali.check.academic.probation', 'kuali.hold.issue.academic.probation',
         'Student is on probation / dismissal.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if student is academically blocked from registration.',
         'kuali.check.academically.ineligible', 'kuali.hold.issue.academically.ineligible',
         'Student is academically blocked from registration.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if student is administratively blocked from registration.',
         'kuali.check.administratively.ineligible', 'kuali.hold.issue.administratively.ineligible',
         'Student is administratively blocked from registration.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if student is eligible for admission.',
         'kuali.check.admissions', 'kuali.hold.issue.admissions',
         'Student is not eligible for admission.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if Student has chosen a degree / major.',
         'kuali.check.choose.degree', 'kuali.hold.issue.choose.degree',
         'Student has reached 60 credits and has not yet chosen a major, or is still listed in Letters and Sciences.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if Student is out of compliance with a financial aid regulation or rule.',
         'kuali.check.financial.aid', 'kuali.hold.issue.financial.aid',
         'Student is out of compliance with a financial aid regulation or rule.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if an outstanding student financial balance exists.',
         'kuali.check.financial.ineligible', 'kuali.hold.issue.financial.ineligible',
         'An outstanding student financial balance exists.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if student has completed a fundamental English course.',
         'kuali.check.fundamental.english', 'kuali.hold.issue.fundamental.english',
         'Student has reached 30 credits and has not completed a fundamental English course.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if student has completed a fundamental English course.',
         'kuali.check.fundamental.math', 'kuali.hold.issue.fundamental.math',
         'Student has reached 30 credits and has not completed a fundamental Math course.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if student has visa clearance.',
         'kuali.check.international.students', 'kuali.hold.issue.international.student',
         'Student may not have visa clearance.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if student has a judicial hold on their registration.',
         'kuali.check.judicially.ineligible', 'kuali.hold.issue.judicially.ineligible',
         'Student has a judicial hold on their registration.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if student was registered during the previous semester.',
         'kuali.check.last.attended', 'kuali.hold.issue.last.attended',
         'Student was not registered during the previous semester and must re-enroll.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if student is cleared by their department / advising college.',
         'kuali.check.mandatory.advising', 'kuali.hold.issue.mandatory.advising',
         'Student must meet and be cleared by their department / advising college in order to register.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if student has attended an orientation program.',
         'kuali.check.new.student.advising', 'kuali.hold.issue.new.student.advising',
         'Newly admitted students must attend an orientation program.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if student has immunization records.',
         'kuali.check.no.immunization.record', 'kuali.hold.issue.no.immunization.record',
         'The University does not have the student''s immunization records on file.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if student has exceeded the maximum number of registration transactions.',
         'kuali.check.registration.transactions.limit', 'kuali.hold.issue.registration.transactions.limit',
         'Student has exceeded the maximum number of registration transactions.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if student has been cleared by academic college advisor.',
         'kuali.check.student.athlete', 'kuali.hold.issue.student.athlete',
         'Student athletes must be cleared by their academic college advisor.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/
INSERT INTO KSEN_PROCESS_CHECK(CREATEID,CREATETIME,DESCR_PLAIN,ID,ISSUE_ID,NAME,
                               OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,
                               UPDATEID,UPDATETIME,VER_NBR)
  VALUES('Admin',SYSDATE,
         'Checks if student was admitted as Term Only.',
         'kuali.check.term.only', 'kuali.hold.issue.term.only',
         'Student was admitted as Term Only.',SYS_GUID(),
         'kuali.process.check.state.active','kuali.process.check.type.hold',
         'Admin',SYSDATE,1)
/