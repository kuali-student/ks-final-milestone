-- KSENROLL-7923
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130709000000', 'YYYYMMDDHH24MISS' ),'A generic canonical Exam.','A generic canonical Exam.','Exam','http://student.kuali.org/wsdl/exam/ExamInfo','http://student.kuali.org/wsdl/exam/ExamService','kuali.lu.type.exam',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130709000000', 'YYYYMMDDHH24MISS' ),'A canonical exam that will be used to instantiate final exam offerings.','A canonical exam that will be used to instantiate final exam offerings.','Exam','http://student.kuali.org/wsdl/exam/ExamInfo','http://student.kuali.org/wsdl/exam/ExamService','kuali.lu.type.exam.final',0)
/