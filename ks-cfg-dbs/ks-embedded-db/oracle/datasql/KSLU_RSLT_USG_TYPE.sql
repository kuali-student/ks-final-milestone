TRUNCATE TABLE KSLU_RSLT_USG_TYPE DROP STORAGE
/
INSERT INTO KSLU_RSLT_USG_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Final Grade','Final learning result for an LU. A stereotypical usage is the final grade in a course.','lrType.finalGrade')
/
INSERT INTO KSLU_RSLT_USG_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Midterm Grade','Midterm learning result for an LU. A stereotypical usage is the midterm grade in a course.','lrType.midtermGrade')
/
