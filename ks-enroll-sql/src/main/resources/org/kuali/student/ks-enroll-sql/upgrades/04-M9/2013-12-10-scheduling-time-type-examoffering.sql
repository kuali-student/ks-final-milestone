-- adding kuali.scheduling.time.slot.type.examoffering to KSEN_TYPE
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),'Exam Offering Time slots','Exam Offering Time slots','Exam Offering','http://student.kuali.org/wsdl/scheduling/TimeSlotInfo','http://student.kuali.org/wsdl/scheduling/SchedulingService','kuali.scheduling.time.slot.type.exam',0)
/


