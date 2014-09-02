--KSENROLL-14122 Upload new holdissue data
DELETE KSEN_HOLD_ISSUE
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'Student is on probation / dismissal during the semester for which they are trying to register. Contact Academic Adivising College.',
  'Student is on probation / dismissal during the semester for which they are trying to register. Contact Academic Adivising College.',
  TO_DATE( '20110822', 'YYYYMMDD' ),null,'Acad01','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.academic.progress',
  'kuali.hold.issue.academic.probation',null,null,0,'Academic Probation / Dismissal',SYS_GUID(),'ORGID-178386427',null,null,1)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'Student blocked from registration. Contact Office of the Registrar at 123-456-7890.',
  'Student blocked from registration. Contact Office of the Registrar at 123-456-7890.',
  TO_DATE( '2011-08-22', 'YYYY-MM-DD' ),null,'Acad02','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.academic.progress',
  'kuali.hold.issue.academically.ineligible',null,null,0,'Academically Ineligible',SYS_GUID(),'ORGID-178386427',null,null,1)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'Student blocked from registration. Contact Office of the Registrar at 123-456-7890.',
  'Student blocked from registration. Contact Office of the Registrar at 123-456-7890.',
  TO_DATE( '2011-08-22', 'YYYY-MM-DD' ),null,'Acad03','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.academic.progress',
  'kuali.hold.issue.administratively.ineligible',null,null,0,'Administratively Ineligible',SYS_GUID(),'ORGID-3152206838',null,null,1)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'An outstanding student financial balance exists. Contact Financial Service Center at 123-456-8888, or email thisperson@xyz.edu if you have questions.',
  'An outstanding student financial balance exists. Contact Financial Service Center at 123-456-8888, or email thisperson@xyz.edu if you have questions.',
  TO_DATE( '2011-08-22', 'YYYY-MM-DD' ),null,'Fin01','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.financial',
  'kuali.hold.issue.financial.ineligible',null,null,0,'Financially Ineligible',SYS_GUID(),'ORGID-3401975017',null,null,1)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'Student has reached 30 credits and has not completed a fundamental English course. Registration must be completed in person at the Office of the Registrar. Contact Office of the Registrar at 123-456-7890.',
  'Student has reached 30 credits and has not completed a fundamental English course. Registration must be completed in person at the Office of the Registrar. Contact Office of the Registrar at 123-456-7890.',
  TO_DATE( '2011-08-22', 'YYYY-MM-DD' ),null,'Acad04','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.academic.progress',
  'kuali.hold.issue.fundamental.english',null,null,0,'Fundamental Studies English',SYS_GUID(),'ORGID-178386427',null,null,1)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'Student has reached 30 credits and has not completed a fundamental Math course. Registration must be completed in person at the Office of the Registrar. Contact Office of the Registrar at 123-456-7890.',
  'Student has reached 30 credits and has not completed a fundamental Math course. Registration must be completed in person at the Office of the Registrar. Contact Office of the Registrar at 123-456-7890.',
  TO_DATE( '2011-08-22', 'YYYY-MM-DD' ),null,'Acad05','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.academic.progress',
  'kuali.hold.issue.fundamental.math',null,null,0,'Fundamental Studies Math',SYS_GUID(),'ORGID-178386427',null,null,1)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'Student has a judicial hold on their registration. Contact Office of Student Conduct at 123-456-7777.',
  'Student has a judicial hold on their registration. Contact Office of Student Conduct at 123-456-7777.',
  TO_DATE( '2011-08-22', 'YYYY-MM-DD' ),null,'Disp01','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.discipline',
  'kuali.hold.issue.judicially.ineligible',null,null,0,'Judicially Ineligible',SYS_GUID(),'ORGID-178386427',null,null,1)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'Student must meet and be cleared by their department / advising college in order to register. Contact Academic Advising College, or department as appropriate.',
  'Student must meet and be cleared by their department / advising college in order to register. Contact Academic Advising College, or department as appropriate.',
  TO_DATE( '2011-08-22', 'YYYY-MM-DD' ),null,'Adv01','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.advising',
  'kuali.hold.issue.mandatory.advising',null,null,0,'Mandatory Advising',SYS_GUID(),'ORGID-3589481728',null,null,1)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'Student has reached 60 credits and has not yet chosen a major, or is still listed in Letters and Sciences. Contact the Academic department offering the desired major to declare, or Letters and Sciences to register without a major.',
  'Student has reached 60 credits and has not yet chosen a major, or is still listed in Letters and Sciences. Contact the Academic department offering the desired major to declare, or Letters and Sciences to register without a major.',
  TO_DATE( '2011-08-22', 'YYYY-MM-DD' ),null,'Acad06','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.academic.progress',
  'kuali.hold.issue.choose.degree',null,null,0,'Must choose degree / major',SYS_GUID(),'ORGID-178386427',null,null,1)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'Newly admitted students must attend an orientation program and will be advised and registered at that time. Contact Orientation Office at 123-456-9999.',
  'Newly admitted students must attend an orientation program and will be advised and registered at that time. Contact Orientation Office at 123-456-9999.',
  TO_DATE( '2011-08-22', 'YYYY-MM-DD' ),null,'Admis01','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.admissions',
  'kuali.hold.issue.new.student.advising',null,null,0,'New student requires advising',SYS_GUID(),'ORGID-3589481728',null,null,1)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'The University does not have the student''s immunization records on file. Contact University Health Center (123-456-6666).',
  'The University does not have the student''s immunization records on file. Contact University Health Center (123-456-6666).',
  TO_DATE( '2011-08-22', 'YYYY-MM-DD' ),null,'Med01','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.medical.immunization',
  'kuali.hold.issue.no.immunization.record',null,null,0,'No record of immunization',SYS_GUID(),'ORGID-2228992216',null,null,1)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'Due to NCAA certification requirements, student athletes must meet with their academic college advisor before registering.',
  'Due to NCAA certification requirements, student athletes must meet with their academic college advisor before registering.',
  TO_DATE( '2011-08-22', 'YYYY-MM-DD' ),null,'Adv02','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.advising',
  'kuali.hold.issue.student.athlete',null,null,0,'Student athlete',SYS_GUID(),'ORGID-3589481728',null,null,1)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'Student has exceeded the maximum number of registration transactions and must register in person at the Registrar''s Office. Contact Registrar''s Office at 123-456-7890.',
  'Student has exceeded the maximum number of registration transactions and must register in person at the Registrar''s Office. Contact Registrar''s Office at 123-456-7890.',
  TO_DATE( '2011-08-22', 'YYYY-MM-DD' ),null,'Acad07','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.academic.progress',
  'kuali.hold.issue.registration.transactions.limit',null,null,0,'Student has made too many registration transactions',SYS_GUID(),'ORGID-178386427',null,null,1)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'Student was not registered during the previous semester and must re-enroll. Contact Office of Student Success at thisDept@xyz.edu.',
  'Student was not registered during the previous semester and must re-enroll. Contact Office of Student Success at thisDept@xyz.edu.',
  TO_DATE( '2011-08-22', 'YYYY-MM-DD' ),null,'Acad08','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.academic.progress',
  'kuali.hold.issue.last.attended',null,null,0,'Student last attended in ...',SYS_GUID(),'ORGID-178386427',null,null,1)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'Student was admitted as Term Only and must apply to be granted full admission. Contact Office of the Registrar at 123-456-7890.',
  'Student was admitted as Term Only and must apply to be granted full admission. Contact Office of the Registrar at 123-456-7890.',
  TO_DATE( '2011-08-22', 'YYYY-MM-DD' ),null,'Acad09','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.academic.progress',
  'kuali.hold.issue.term.only',null,null,0,'Term Only',SYS_GUID(),'ORGID-178386427',null,null,1)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'Student may not have visa clearance. Contact Office of the Registrar at 123-456-7890.',
  'Student may not have visa clearance. Contact Office of the Registrar at 123-456-7890.',
  TO_DATE( '2011-08-22', 'YYYY-MM-DD' ),null,'Admis02','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.international.students',
  'kuali.hold.issue.international.student',null,null,0,'International Student',SYS_GUID(),'ORGID-3589481728',null,null,1)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'Student is out of compliance with a financial aid regulation or rule. Contact Office of the Registrar at 123-456-7890.',
  'Student is out of compliance with a financial aid regulation or rule. Contact Office of the Registrar at 123-456-7890.',
  TO_DATE( '2011-08-22', 'YYYY-MM-DD' ),null,'Fin02','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.financial.aid',
  'kuali.hold.issue.financial.aid',null,null,0,'Financial Aid',SYS_GUID(),'ORGID-3401975017',null,null,1)
/
INSERT INTO KSEN_HOLD_ISSUE(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,
                            FIRST_APPLIED_DT,FIRST_APP_TERM_ID,HOLD_CD,HOLD_ISSUE_STATE,
                            HOLD_ISSUE_TERM_BASED_IND,HOLD_ISSUE_TYPE,ID,LAST_APPLIED_DT,
                            LAST_APP_TERM_ID,MAINT_HIST_OF_APP_OF_HOLD_IND,NAME,
                            OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,
  'Student is not eligible for admission. Contact Office of the Registrar at 123-456-7890.',
  'Student is not eligible for admission. Contact Office of the Registrar at 123-456-7890.',
  TO_DATE( '2011-08-22', 'YYYY-MM-DD' ),null,'Admis03','kuali.hold.issue.state.active',0,'kuali.hold.issue.type.admissions',
  'kuali.hold.issue.admissions',null,null,0,'Admissions Issues',SYS_GUID(),'ORGID-3589481728',null,null,1)
/