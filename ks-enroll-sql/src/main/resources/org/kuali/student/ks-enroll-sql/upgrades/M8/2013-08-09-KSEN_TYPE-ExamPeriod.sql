-- KSENROLL-8559
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130809000000', 'YYYYMMDDHH24MISS' ),'Exam Period','Exam Period','Exam Period','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.ExamPeriod',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130809000000', 'YYYYMMDDHH24MISS' ),'The Academic Term is associated with an exam period','The Academic Term is associated with an exam period','Term to ExamPeriod Associated','http://student.kuali.org/wsdl/atp/AtpAtpRelationInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.atp.relation.associated.term2examperiod',0)
/