--KSENROLL-14122 Upload holdissue data
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'Academic Probation / Dismissal','Student is on probation / dismissal during the semester for which they are trying to register.',
  'kuali.hold.issue.state.active','kuali.hold.issue.type.academic.progress','kuali.hold.issue.academic.probation','Academic Progress Issue',null,null,null,null,0)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'Academically Ineligible','Student blocked from registration.','kuali.hold.issue.state.active','kuali.hold.issue.type.admissions',
  'kuali.hold.issue.academically.ineligible','Admissions Issues',null,null,null,null,0)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'Administratively Ineligible','Student blocked from registration.','kuali.hold.issue.state.active','kuali.hold.issue.type.admissions',
  'kuali.hold.issue.administratively.ineligible','Admissions Issues',null,null,null,null,0)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'Financially Ineligible','An outstanding student financial balance exists.','kuali.hold.issue.state.active',
  'kuali.hold.issue.type.financial','kuali.hold.issue.financial.ineligible','Financial Issue',null,null,null,null,0)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'Fundamental Studies English','Student has reached 30 credits and has not completed a fundamental English course.','kuali.hold.issue.state.active',
  'kuali.hold.issue.type.advising','kuali.hold.issue.fundamental.english','Academic Advising Issue',null,null,null,null,0)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'Fundamental Studies Math','Student has reached 30 credits and has not completed a fundamental Math course.','kuali.hold.issue.state.active',
  'kuali.hold.issue.type.advising','kuali.hold.issue.fundamental.math','Academic Advising Issue',null,null,null,null,0)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'Judicially Ineligible','Student has a judicial hold on their registration.','kuali.hold.issue.state.active','kuali.hold.issue.type.discipline',
  'kuali.hold.issue.judicially.ineligible','Disciplinary Issue',null,null,null,null,0)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'Mandatory Advising','Student must meet and be cleared by their department / advising college in order to register.','kuali.hold.issue.state.active',
  'kuali.hold.issue.type.advising','kuali.hold.issue.mandatory.advising','Academic Advising Issue',null,null,null,null,0)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'Must choose degree / major','Student has reached 60 credits and has not yet chosen a major, or is still listed in Letters and Sciences. Contact the Academic department offering the desired major to declare, or Letters and Sciences to register without a major.',
  'kuali.hold.issue.state.active','kuali.hold.issue.type.advising','kuali.hold.issue.choose.degree','Academic Advising Issue',null,null,null,null,0)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'New student requires advising','Newly admitted students must attend an orientation program and will be advised and registered at that time.',
  'kuali.hold.issue.state.active','kuali.hold.issue.type.advising','kuali.hold.issue.new.student.advising','Academic Advising Issue',null,null,null,null,0)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'No record of immunization','The University does not have the student''s immunization records on file.',
  'kuali.hold.issue.state.active','kuali.hold.issue.type.medical.immunization','kuali.hold.issue.no.immunization.record','Medical Immunization Issue',null,null,null,null,0)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'Student athlete','Due to NCAA certification requirements, student athletes must meet with their academic college advisor before registering.',
  'kuali.hold.issue.state.active','kuali.hold.issue.type.advising','kuali.hold.issue.student.athlete','Academic Advising Issue',null,null,null,null,0)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'Student has made too many registration transactions','Student has exceeded the maximum number of registration transactions and must register in person at the Registrar''s Office.',
  'kuali.hold.issue.state.active','kuali.hold.issue.type.admissions','kuali.hold.issue.registration.transactions.limit','Academic Progress Issue',null,null,null,null,0)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'Student last attended in ...','Student was not registered during the previous semester and must re-enroll.',
  'kuali.hold.issue.state.active','kuali.hold.issue.type.academic.progress','kuali.hold.issue.last.attended','Academic Progress Issue',null,null,null,null,0)
/
