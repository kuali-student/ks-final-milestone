TRUNCATE TABLE KSLR_RESCOMP_TYPE DROP STORAGE
/
INSERT INTO KSLR_RESCOMP_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Certificate','BFE500B4290445B3B0D09A14D1AFE040','This records an awarded certificate','kuali.resultComponentType.certificate',0)
/
INSERT INTO KSLR_RESCOMP_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Credits, Fixed','7AECF0492B24496CA7FCDA083095E917','This records a single fixed number of credits that are awarded if the student passes the course.','kuali.resultComponentType.credit.degree.fixed',0)
/
INSERT INTO KSLR_RESCOMP_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Credits, Multiple','AE5F2B5E42D14478884A141637B5D920','This records multiple numbers of credits that can be awarded for this course.','kuali.resultComponentType.credit.degree.multiple',0)
/
INSERT INTO KSLR_RESCOMP_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Credits, Variable','E3822A13EC1A42AF9305EEE1B3224FAB','This records a range of number of credits that can be awarded for this course.','kuali.resultComponentType.credit.degree.range',0)
/
INSERT INTO KSLR_RESCOMP_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Degree','0E192F1BA58149169AFB1C4A53546B64','This records a degree is awarded if the student completes the program','kuali.resultComponentType.degree',0)
/
INSERT INTO KSLR_RESCOMP_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Overall GPA ','A905E79D8BAC4A04A2610013490182AD','This records the overall GPA of a student in a program ','kuali.resultComponentType.gpa',0)
/
INSERT INTO KSLR_RESCOMP_TYPE (EFF_DT,NAME,OBJ_ID,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),'Final Grade','93923DA1E3874217B817D09C1BF3F20F','This records that a final grade is a result for this course','kuali.resultComponentType.grade.finalGrade',0)
/
