TRUNCATE TABLE KSSTMT_STMT_TYPE DROP STORAGE
/
INSERT INTO KSSTMT_STMT_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Academic Readiness Anti Reqs','Anti req used in the evaluation of a person''s academic readiness for enrollment in an LU.','kuali.luStatementType.antireqAcademicReadiness')
/
INSERT INTO KSSTMT_STMT_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Academic Readiness Co Reqs','Co req used in the evaluation of a person''s academic readiness for enrollment in an LU.','kuali.luStatementType.coreqAcademicReadiness')
/
INSERT INTO KSSTMT_STMT_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Academic Readiness Enroll Reqs','Enrollment req used in the evaluation of a person''s academic readiness for enrollment in an LU.','kuali.luStatementType.enrollAcademicReadiness')
/
INSERT INTO KSSTMT_STMT_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Academic Readiness Pre Reqs','Pre req rules used in the evaluation of a person''s academic readiness for enrollment in an LU.','kuali.luStatementType.prereqAcademicReadiness')
/
