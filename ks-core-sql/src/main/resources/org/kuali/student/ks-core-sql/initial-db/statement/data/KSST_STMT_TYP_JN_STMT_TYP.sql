TRUNCATE TABLE KSST_STMT_TYP_JN_STMT_TYP DROP STORAGE
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.course.enrollmentEligibility','1',1,'kuali.statement.type.course',0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.program.entrance','12',1,'kuali.statement.type.program',0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.program.satisfactoryProgress','13',2,'kuali.statement.type.program',0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.program.completion','14',3,'kuali.statement.type.program',0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.course.academicReadiness.studentEligibilityPrereq','2',2,'kuali.statement.type.course.enrollmentEligibility',0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.course.academicReadiness.coreq','3',3,'kuali.statement.type.course.enrollmentEligibility',0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.course.recommendedPreparation','4',4,'kuali.statement.type.course.enrollmentEligibility',0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.course.academicReadiness.antireq','5',5,'kuali.statement.type.course.enrollmentEligibility',0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.course.creditConstraints','6',6,'kuali.statement.type.course',0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.course.credit.restriction','7',7,'kuali.statement.type.course.creditConstraints',0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (CHLD_STMT_TYPE_ID,ID,SORT_ORDER,STMT_TYPE_ID,VER_NBR)
  VALUES ('kuali.statement.type.course.credit.repeatable','8',8,'kuali.statement.type.course.creditConstraints',0)
/
