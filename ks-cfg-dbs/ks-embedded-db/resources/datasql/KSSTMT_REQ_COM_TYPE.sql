TRUNCATE TABLE KSSTMT_REQ_COM_TYPE DROP STORAGE
/
INSERT INTO KSSTMT_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'One of two required courses','Student must take one of two specified courses','kuali.reqCompType.courseList.1of2')
/
INSERT INTO KSSTMT_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'All of required courses','Student must have completed all of the specified courses','kuali.reqCompType.courseList.all')
/
INSERT INTO KSSTMT_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'All of required courses','Student must be enrolled in all of the following courses','kuali.reqCompType.courseList.coreq.all')
/
INSERT INTO KSSTMT_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'One of required courses','Student must be enrolled in one of the following courses','kuali.reqCompType.courseList.coreq.oneof')
/
INSERT INTO KSSTMT_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'N of required courses','Student must take <n> courses from the specified courses','kuali.reqCompType.courseList.nof')
/
INSERT INTO KSSTMT_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'None of required courses','Student must have completed none of the specified courses','kuali.reqCompType.courseList.none')
/
INSERT INTO KSSTMT_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20011130000000', 'YYYYMMDDHH24MISS' ),'Minimum overall GPA','Student needs to have attained a minimum specified GPA','kuali.reqCompType.gradecheck')
/
INSERT INTO KSSTMT_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'Course completed with minimum specified grade','Student must take <n> credits from the specified courses','kuali.reqCompType.grdCondCourseList')
/
INSERT INTO KSSTMT_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'None of required programs','Enrollment is not available to students in the following programs','kuali.reqCompType.programList.enroll.none')
/
INSERT INTO KSSTMT_REQ_COM_TYPE (EFF_DT,EXPIR_DT,NAME,TYPE_DESC,TYPE_KEY)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20001231000000', 'YYYYMMDDHH24MISS' ),'One of required programs','Enrollment is limited to students in the following programs','kuali.reqCompType.programList.enroll.oneof')
/
