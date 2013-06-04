TRUNCATE TABLE KSEN_PROCESS_CHECK DROP STORAGE
/
INSERT INTO KSEN_PROCESS_CHECK (CREATEID,CREATETIME,DESCR_PLAIN,ID,NAME,OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('Admin',TO_DATE( '20120816095512', 'YYYYMMDDHH24MISS' ),'Is student alive check','kuali.process.check.is.alive','kuali.process.check.is.alive','C802EB30-D6F8-6708-E040-0D6F86708E04','kuali.process.check.state.active','kuali.process.check.type.check','Admin',TO_DATE( '20120616095512', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_PROCESS_CHECK (CREATEID,CREATETIME,DESCR_PLAIN,ID,NAME,OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('Admin',TO_DATE( '20120816095512', 'YYYYMMDDHH24MISS' ),'Check that student is not expelled','kuali.process.check.is.not.expelled','kuali.process.check.is.not.expelled','C802EB30-D6FB-6708-E040-0D6FB6708E04','kuali.process.check.state.inactive','kuali.process.check.type.hold','Admin',TO_DATE( '20120616095512', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_PROCESS_CHECK (CREATEID,CREATETIME,DESCR_PLAIN,ID,NAME,OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('Admin',TO_DATE( '20120816095512', 'YYYYMMDDHH24MISS' ),'Check that student is not suspended','kuali.process.check.is.not.suspended','kuali.process.check.is.not.suspended','C802EB30-D6FA-6708-E040-0D6FA6708E04','kuali.process.check.state.disabled','kuali.process.check.type.hold','Admin',TO_DATE( '20120616095512', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_PROCESS_CHECK (CREATEID,CREATETIME,DESCR_PLAIN,ID,NAME,OBJ_ID,PROCESS_CHECK_STATE,PROCESS_CHECK_TYPE,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('Admin',TO_DATE( '20120816095512', 'YYYYMMDDHH24MISS' ),'Check if student has paid all the necessary fees','kuali.process.check.paid.all.fees','kuali.process.check.paid.all.fees','C802EB30-D6F9-6708-E040-0D6F96708E04','kuali.process.check.state.active','kuali.process.check.type.hold','Admin',TO_DATE( '20120616095512', 'YYYYMMDDHH24MISS' ),1)
/
