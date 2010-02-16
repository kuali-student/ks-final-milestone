TRUNCATE TABLE KSLU_STY_JN_RQTY DROP STORAGE
/
INSERT INTO KSLU_STY_JN_RQTY (LU_STMT_TYPE_ID,REQ_COM_TYPE_ID)
  VALUES ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.courseList.all')
/
INSERT INTO KSLU_STY_JN_RQTY (LU_STMT_TYPE_ID,REQ_COM_TYPE_ID)
  VALUES ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.courseList.nof')
/
INSERT INTO KSLU_STY_JN_RQTY (LU_STMT_TYPE_ID,REQ_COM_TYPE_ID)
  VALUES ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.courseList.1of2')
/
INSERT INTO KSLU_STY_JN_RQTY (LU_STMT_TYPE_ID,REQ_COM_TYPE_ID)
  VALUES ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.grdCondCourseList')
/
INSERT INTO KSLU_STY_JN_RQTY (LU_STMT_TYPE_ID,REQ_COM_TYPE_ID)
  VALUES ('kuali.luStatementType.prereqAcademicReadiness','kuali.reqCompType.gradecheck')
/
INSERT INTO KSLU_STY_JN_RQTY (LU_STMT_TYPE_ID,REQ_COM_TYPE_ID)
  VALUES ('kuali.luStatementType.coreqAcademicReadiness','kuali.reqCompType.courseList.coreq.all')
/
INSERT INTO KSLU_STY_JN_RQTY (LU_STMT_TYPE_ID,REQ_COM_TYPE_ID)
  VALUES ('kuali.luStatementType.coreqAcademicReadiness','kuali.reqCompType.courseList.coreq.oneof')
/
INSERT INTO KSLU_STY_JN_RQTY (LU_STMT_TYPE_ID,REQ_COM_TYPE_ID)
  VALUES ('kuali.luStatementType.enrollAcademicReadiness','kuali.reqCompType.programList.enroll.oneof')
/
INSERT INTO KSLU_STY_JN_RQTY (LU_STMT_TYPE_ID,REQ_COM_TYPE_ID)
  VALUES ('kuali.luStatementType.enrollAcademicReadiness','kuali.reqCompType.programList.enroll.none')
/
INSERT INTO KSLU_STY_JN_RQTY (LU_STMT_TYPE_ID,REQ_COM_TYPE_ID)
  VALUES ('kuali.luStatementType.antireqAcademicReadiness','kuali.reqCompType.courseList.none')
/
