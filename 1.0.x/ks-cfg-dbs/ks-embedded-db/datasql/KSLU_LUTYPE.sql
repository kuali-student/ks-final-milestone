TRUNCATE TABLE KSLU_LUTYPE DROP STORAGE
/
INSERT INTO KSLU_LUTYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Credit Course','An course offered for academic credit','kuali.lu.type.CreditCourse')
/
INSERT INTO KSLU_LUTYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Credit Course Format Shell','A shell for course formats','kuali.lu.type.CreditCourseFormatShell')
/
INSERT INTO KSLU_LUTYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Non-Credit Course','A course that is not offered for regular academic credit','kuali.lu.type.NonCreditCourse')
/
INSERT INTO KSLU_LUTYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Discussion','The exchange of opinions or questions on course material, directed by the instructor.','kuali.lu.type.activity.Directed')
/
INSERT INTO KSLU_LUTYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Discussion','The exchange of opinions or questions on course material.','kuali.lu.type.activity.Discussion')
/
INSERT INTO KSLU_LUTYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Lab','Student working on projects in a defined laboratory space.  Instructors are on-hand for students to ask questions and guidance.','kuali.lu.type.activity.Lab')
/
INSERT INTO KSLU_LUTYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Lecture','Instructor presentation of course materials.','kuali.lu.type.activity.Lecture')
/
INSERT INTO KSLU_LUTYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Tutorial','Instructor or assistant walking through a learning practice.','kuali.lu.type.activity.Tutorial')
/
INSERT INTO KSLU_LUTYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Lecture','Instructor presentation of course materials via the web','kuali.lu.type.activity.WebLecture')
/
INSERT INTO KSLU_LUTYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Course','A Shell Course','luType.shell.course')
/
INSERT INTO KSLU_LUTYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Program','A Shell Program','luType.shell.program')
/
