TRUNCATE TABLE KSST_STMT_TYP_JN_STMT_TYP DROP STORAGE
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,STMT_TYPE_ID)
  VALUES ('kuali.luStatementType.prereqAcademicReadiness','kuali.luStatementType.createCourseAcademicReadiness')
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,STMT_TYPE_ID)
  VALUES ('kuali.luStatementType.coreqAcademicReadiness','kuali.luStatementType.createCourseAcademicReadiness')
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,STMT_TYPE_ID)
  VALUES ('kuali.luStatementType.enrollAcademicReadiness','kuali.luStatementType.createCourseAcademicReadiness')
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,STMT_TYPE_ID)
  VALUES ('kuali.luStatementType.antireqAcademicReadiness','kuali.luStatementType.createCourseAcademicReadiness')
/
