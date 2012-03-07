TRUNCATE TABLE KSST_STMT_TYP_JN_STMT_TYP DROP STORAGE
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,STMT_TYPE_ID)
  VALUES ('kuali.statement.type.course.enrollmentEligibility','kuali.statement.type.course')
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,STMT_TYPE_ID)
  VALUES ('kuali.statement.type.program.entrance','kuali.statement.type.program')
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,STMT_TYPE_ID)
  VALUES ('kuali.statement.type.program.satisfactoryProgress','kuali.statement.type.program')
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,STMT_TYPE_ID)
  VALUES ('kuali.statement.type.program.completion','kuali.statement.type.program')
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,STMT_TYPE_ID)
  VALUES ('kuali.statement.type.course.academicReadiness.studentEligibilityPrereq','kuali.statement.type.course.enrollmentEligibility')
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,STMT_TYPE_ID)
  VALUES ('kuali.statement.type.course.academicReadiness.coreq','kuali.statement.type.course.enrollmentEligibility')
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,STMT_TYPE_ID)
  VALUES ('kuali.statement.type.course.recommendedPreparation','kuali.statement.type.course.enrollmentEligibility')
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,STMT_TYPE_ID)
  VALUES ('kuali.statement.type.course.academicReadiness.antireq','kuali.statement.type.course.enrollmentEligibility')
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,STMT_TYPE_ID)
  VALUES ('kuali.statement.type.course.creditConstraints','kuali.statement.type.course')
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,STMT_TYPE_ID)
  VALUES ('kuali.statement.type.course.credit.restriction','kuali.statement.type.course.creditConstraints')
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,STMT_TYPE_ID)
  VALUES ('kuali.statement.type.course.credit.repeatable','kuali.statement.type.course.creditConstraints')
/
