TRUNCATE TABLE KSLU_STY_JN_LUSTY DROP STORAGE
/
INSERT INTO KSLU_STY_JN_LUSTY (CHLD_LU_STMT_TYPE_ID,LU_STMT_TYPE_ID)
  VALUES ('kuali.luStatementType.prereqAcademicReadiness','kuali.luStatementType.createCourseAcademicReadiness')
/
INSERT INTO KSLU_STY_JN_LUSTY (CHLD_LU_STMT_TYPE_ID,LU_STMT_TYPE_ID)
  VALUES ('kuali.luStatementType.coreqAcademicReadiness','kuali.luStatementType.createCourseAcademicReadiness')
/
INSERT INTO KSLU_STY_JN_LUSTY (CHLD_LU_STMT_TYPE_ID,LU_STMT_TYPE_ID)
  VALUES ('kuali.luStatementType.enrollAcademicReadiness','kuali.luStatementType.createCourseAcademicReadiness')
/
INSERT INTO KSLU_STY_JN_LUSTY (CHLD_LU_STMT_TYPE_ID,LU_STMT_TYPE_ID)
  VALUES ('kuali.luStatementType.antireqAcademicReadiness','kuali.luStatementType.createCourseAcademicReadiness')
/
