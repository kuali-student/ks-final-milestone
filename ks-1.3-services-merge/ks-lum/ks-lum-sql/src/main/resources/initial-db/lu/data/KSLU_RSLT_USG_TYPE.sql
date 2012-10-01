TRUNCATE TABLE KSLU_RSLT_USG_TYPE DROP STORAGE
/
INSERT INTO KSLU_RSLT_USG_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Final Grade','66CF4F9EA32B45959A91C3AEA33D0215','Final learning result for an LU. A stereotypical usage is the final grade in a course.','lrType.finalGrade',0)
/
INSERT INTO KSLU_RSLT_USG_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Midterm Grade','1811D2788C914B1088FDCD845D56B75E','Midterm learning result for an LU. A stereotypical usage is the midterm grade in a course.','lrType.midtermGrade',0)
/
