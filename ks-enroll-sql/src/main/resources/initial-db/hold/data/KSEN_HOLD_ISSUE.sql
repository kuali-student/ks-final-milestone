TRUNCATE TABLE KSEN_HOLD_ISSUE DROP STORAGE
/
INSERT INTO KSEN_HOLD_ISSUE (CREATEID,CREATETIME,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('Admin',TO_DATE( '20120718095512', 'YYYYMMDDHH24MISS' ),'The student has been assessed a library fine that has not been paid','kuali.hold.issue.state.active','kuali.hold.issue.type.library','07e7f378-736c-45f1-aa2d-14c8e144c973','Unpaid Library Fine','80acf06f-d51a-4859-a869-675cb78a636b','1','Admin',TO_DATE( '20120718095512', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_HOLD_ISSUE (CREATEID,CREATETIME,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('Admin',TO_DATE( '20120718095512', 'YYYYMMDDHH24MISS' ),'The student has tuition from a previous term has not been paid','kuali.hold.issue.state.active','kuali.hold.issue.type.financial','0bda3895-4b5b-426a-91dc-d19eb127d721','Unpaid Tuition Prior Term','a1237c40-d48d-4da7-ae0a-9a48ff186154','1','Admin',TO_DATE( '20120718095512', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_HOLD_ISSUE (CREATEID,CREATETIME,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('Admin',TO_DATE( '20120718095512', 'YYYYMMDDHH24MISS' ),'The student has been suspended for disciplinary reasons','kuali.hold.issue.state.active','kuali.hold.issue.type.discipline','71f5b767-2946-4059-817a-1f47f84b22a2','Disciplinary Suspension','f6e6358b-7658-44f8-b15a-66a257e20703','94','Admin',TO_DATE( '20120718095512', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_HOLD_ISSUE (CREATEID,CREATETIME,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('Admin',TO_DATE( '20120718095512', 'YYYYMMDDHH24MISS' ),'The student has library materials checked out that are overdue','kuali.hold.issue.state.active','kuali.hold.issue.type.library','735a2978-2d22-4d67-bd06-84702bf2c6d7','Book Overdue','79950325-fa50-44fc-928e-73158c61f640','1','Admin',TO_DATE( '20120718095512', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_HOLD_ISSUE (CREATEID,CREATETIME,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('Admin',TO_DATE( '20120718095512', 'YYYYMMDDHH24MISS' ),'The students account has been sent to collections','kuali.hold.issue.state.active','kuali.hold.issue.type.financial','91d1a1da-47ac-4a18-aac9-97d4bcf43be7','Collections','7b7d1f5a-4a90-4591-a0e6-28e5e925aac0','1','Admin',TO_DATE( '20120718095512', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_HOLD_ISSUE (CREATEID,CREATETIME,DESCR_PLAIN,HOLD_ISSUE_STATE,HOLD_ISSUE_TYPE,ID,NAME,OBJ_ID,ORG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('Admin',TO_DATE( '20120718095512', 'YYYYMMDDHH24MISS' ),'The student has been expelled for disciplinary reasons','kuali.hold.issue.state.active','kuali.hold.issue.type.discipline','d941f75e-5e50-4622-845d-794dbd00fc07','Disciplinary Expulsion','58b90c69-b412-4e39-a15a-4d1e3178133f','94','Admin',TO_DATE( '20120718095512', 'YYYYMMDDHH24MISS' ),1)
/
