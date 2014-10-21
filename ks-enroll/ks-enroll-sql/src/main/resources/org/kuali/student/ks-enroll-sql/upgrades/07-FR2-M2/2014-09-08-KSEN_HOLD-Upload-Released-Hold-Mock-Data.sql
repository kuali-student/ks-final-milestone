--KSENROLL-14748 Update Hold issue states to Hold states
UPDATE KSEN_HOLD h
SET h.hold_state = 'kuali.hold.state.active'
WHERE h.hold_state = 'kuali.hold.issue.state.active'
/
--KSENROLL-14748 Create Released Holds for Students with Applied Holds
INSERT INTO KSEN_HOLD(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,
                      EXPIR_DT,HOLD_STATE,HOLD_TYPE,ID,ISSUE_ID,NAME,OBJ_ID,
                      PERS_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'This is a hold that has expired for a student.',
         'This is a hold that has expired for a student.',TO_DATE( '2011-01-01',
         'YYYY-MM-DD' ),TO_DATE( '2011-07-01', 'YYYY-MM-DD' ),'kuali.hold.state.released',
         'kuali.hold.type.student','kuali.released.hold.F.ELENAB','kuali.hold.issue.academically.ineligible',
         'Student Hold',SYS_GUID(),'KS-7196',null,null,1)
/
INSERT INTO KSEN_HOLD(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,
                      EXPIR_DT,HOLD_STATE,HOLD_TYPE,ID,ISSUE_ID,NAME,OBJ_ID,
                      PERS_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'This is a hold that has expired for a student.',
         'This is a hold that has expired for a student.',TO_DATE( '2011-01-01',
         'YYYY-MM-DD' ),TO_DATE( '2011-07-01', 'YYYY-MM-DD' ),'kuali.hold.state.released',
         'kuali.hold.type.student','kuali.released.hold.R.JEFFREYB','kuali.hold.issue.administratively.ineligible',
         'Student Hold',SYS_GUID(),'KS-1675',null,null,1)
/
INSERT INTO KSEN_HOLD(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,
                      EXPIR_DT,HOLD_STATE,HOLD_TYPE,ID,ISSUE_ID,NAME,OBJ_ID,
                      PERS_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'This is a hold that has expired for a student.',
         'This is a hold that has expired for a student.',TO_DATE( '2011-01-01',
         'YYYY-MM-DD' ),TO_DATE( '2011-07-01', 'YYYY-MM-DD' ),'kuali.hold.state.released',
         'kuali.hold.type.student','kuali.released.hold.F.BETHA','kuali.hold.issue.financial.ineligible',
         'Student Hold',SYS_GUID(),'KS-6279',null,null,1)
/
INSERT INTO KSEN_HOLD(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,
                      EXPIR_DT,HOLD_STATE,HOLD_TYPE,ID,ISSUE_ID,NAME,OBJ_ID,
                      PERS_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'This is a hold that has expired for a student.',
         'This is a hold that has expired for a student.',TO_DATE( '2011-01-01',
         'YYYY-MM-DD' ),TO_DATE( '2011-07-01', 'YYYY-MM-DD' ),'kuali.hold.state.released',
         'kuali.hold.type.student','kuali.released.hold.F.ANNAH','kuali.hold.issue.international.student',
         'Student Hold',SYS_GUID(),'KS-6509',null,null,1)
/
INSERT INTO KSEN_HOLD(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,
                      EXPIR_DT,HOLD_STATE,HOLD_TYPE,ID,ISSUE_ID,NAME,OBJ_ID,
                      PERS_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'This is a hold that has expired for a student.',
         'This is a hold that has expired for a student.',TO_DATE( '2011-01-01',
         'YYYY-MM-DD' ),TO_DATE( '2011-07-01', 'YYYY-MM-DD' ),'kuali.hold.state.released',
         'kuali.hold.type.student','kuali.released.hold.R.JANEN','kuali.hold.issue.mandatory.advising',
         'Student Hold',SYS_GUID(),'KS-7603',null,null,1)
/
INSERT INTO KSEN_HOLD(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,
                      EXPIR_DT,HOLD_STATE,HOLD_TYPE,ID,ISSUE_ID,NAME,OBJ_ID,
                      PERS_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'This is a hold that has expired for a student.',
         'This is a hold that has expired for a student.',TO_DATE( '2011-01-01',
         'YYYY-MM-DD' ),TO_DATE( '2011-07-01', 'YYYY-MM-DD' ),'kuali.hold.state.released',
         'kuali.hold.type.student','kuali.released.hold.F.DAVIDK','kuali.hold.issue.no.immunization.record',
         'Student Hold',SYS_GUID(),'KS-3272',null,null,1)
/
INSERT INTO KSEN_HOLD(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,
                      EXPIR_DT,HOLD_STATE,HOLD_TYPE,ID,ISSUE_ID,NAME,OBJ_ID,
                      PERS_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'This is a hold that has expired for a student.',
         'This is a hold that has expired for a student.',TO_DATE( '2011-01-01',
         'YYYY-MM-DD' ),TO_DATE( '2011-07-01', 'YYYY-MM-DD' ),'kuali.hold.state.released',
         'kuali.hold.type.student','kuali.released.hold.F.BARBARAH','kuali.hold.issue.student.athlete',
         'Student Hold',SYS_GUID(),'KS-6573',null,null,1)
/
INSERT INTO KSEN_HOLD(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,
                      EXPIR_DT,HOLD_STATE,HOLD_TYPE,ID,ISSUE_ID,NAME,OBJ_ID,
                      PERS_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'This is a hold that has expired for a student.',
         'This is a hold that has expired for a student.',TO_DATE( '2011-01-01',
         'YYYY-MM-DD' ),TO_DATE( '2011-07-01', 'YYYY-MM-DD' ),'kuali.hold.state.released',
         'kuali.hold.type.student','kuali.released.hold.L.VICTORP','kuali.hold.issue.financial.aid',
         'Student Hold',SYS_GUID(),'KS-4993',null,null,1)
/
INSERT INTO KSEN_HOLD(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,
                      EXPIR_DT,HOLD_STATE,HOLD_TYPE,ID,ISSUE_ID,NAME,OBJ_ID,
                      PERS_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'This is a hold that has expired for a student.',
         'This is a hold that has expired for a student.',TO_DATE( '2011-01-01',
         'YYYY-MM-DD' ),TO_DATE( '2011-07-01', 'YYYY-MM-DD' ),'kuali.hold.state.released',
         'kuali.hold.type.student','kuali.released.hold.F.CYNTHIAD','kuali.hold.issue.mandatory.advising',
         'Student Hold',SYS_GUID(),'KS-2563',null,null,1)
/
INSERT INTO KSEN_HOLD(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,
                      EXPIR_DT,HOLD_STATE,HOLD_TYPE,ID,ISSUE_ID,NAME,OBJ_ID,
                      PERS_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'This is a hold that has expired for a student.',
         'This is a hold that has expired for a student.',TO_DATE( '2011-01-01',
         'YYYY-MM-DD' ),TO_DATE( '2011-07-01', 'YYYY-MM-DD' ),'kuali.hold.state.released',
         'kuali.hold.type.student','kuali.released.hold.F.CHARLESW1500','kuali.hold.issue.academically.ineligible',
         'Student Hold',SYS_GUID(),'KS-2813',null,null,1)
/
--KSENROLL-14748 Create Released Holds for Students
INSERT INTO KSEN_HOLD(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,
                      EXPIR_DT,HOLD_STATE,HOLD_TYPE,ID,ISSUE_ID,NAME,OBJ_ID,
                      PERS_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'This is a hold that has expired for a student.',
         'This is a hold that has expired for a student.',TO_DATE( '2011-01-01',
         'YYYY-MM-DD' ),TO_DATE( '2011-07-01', 'YYYY-MM-DD' ),'kuali.hold.state.released',
         'kuali.hold.type.student','kuali.released.hold.A.HAILINGL','kuali.hold.issue.academically.ineligible',
         'Student Hold',SYS_GUID(),'KS-8584',null,null,1)
/
INSERT INTO KSEN_HOLD(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,
                      EXPIR_DT,HOLD_STATE,HOLD_TYPE,ID,ISSUE_ID,NAME,OBJ_ID,
                      PERS_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'This is a hold that has expired for a student.',
         'This is a hold that has expired for a student.',TO_DATE( '2011-01-01',
         'YYYY-MM-DD' ),TO_DATE( '2011-07-01', 'YYYY-MM-DD' ),'kuali.hold.state.released',
         'kuali.hold.type.student','kuali.released.hold.A.JACOBC','kuali.hold.issue.mandatory.advising',
         'Student Hold',SYS_GUID(),'KS-3917',null,null,1)
/
INSERT INTO KSEN_HOLD(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,
                      EXPIR_DT,HOLD_STATE,HOLD_TYPE,ID,ISSUE_ID,NAME,OBJ_ID,
                      PERS_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'This is a hold that has expired for a student.',
         'This is a hold that has expired for a student.',TO_DATE( '2011-01-01',
         'YYYY-MM-DD' ),TO_DATE( '2011-07-01', 'YYYY-MM-DD' ),'kuali.hold.state.released',
         'kuali.hold.type.student','kuali.released.hold.C.STANLEYB','kuali.hold.issue.financial.aid',
         'Student Hold',SYS_GUID(),'KS-9920',null,null,1)
/
INSERT INTO KSEN_HOLD(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,
                      EXPIR_DT,HOLD_STATE,HOLD_TYPE,ID,ISSUE_ID,NAME,OBJ_ID,
                      PERS_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'This is a hold that has expired for a student.',
         'This is a hold that has expired for a student.',TO_DATE( '2011-01-01',
         'YYYY-MM-DD' ),TO_DATE( '2011-07-01', 'YYYY-MM-DD' ),'kuali.hold.state.released',
         'kuali.hold.type.student','kuali.released.hold.F.DAVIDJ','kuali.hold.issue.administratively.ineligible',
         'Student Hold',SYS_GUID(),'KS-7827',null,null,1)
/
INSERT INTO KSEN_HOLD(CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,EFF_DT,
                      EXPIR_DT,HOLD_STATE,HOLD_TYPE,ID,ISSUE_ID,NAME,OBJ_ID,
                      PERS_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES('SYSTEMLOADER',SYSDATE,'This is a hold that has expired for a student.',
         'This is a hold that has expired for a student.',TO_DATE( '2011-01-01',
         'YYYY-MM-DD' ),TO_DATE( '2011-07-01', 'YYYY-MM-DD' ),'kuali.hold.state.released',
         'kuali.hold.type.student','kuali.released.hold.H.ALANK','kuali.hold.issue.international.student',
         'Student Hold',SYS_GUID(),'KS-8848',null,null,1)
/