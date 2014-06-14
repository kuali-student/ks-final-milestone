-- KSENROLL-12176
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),'Exam Offering time slots ad hoc','Exam Offering time slots ad hoc','Examoffering Timeslot Adhoc','http://student.kuali.org/wsdl/scheduling/TimeSlotInfo','http://student.kuali.org/wsdl/scheduling/SchedulingService','kuali.scheduling.time.slot.type.examoffering.adhoc',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140311000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20140311000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20140311000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.group.kuali.scheduling.type.time.slot.kuali.scheduling.time.slot.type.examoffering.adhoc','kuali.scheduling.type.time.slot',1,'kuali.scheduling.time.slot.type.examoffering.adhoc','kuali.type.type.relation.state.active','kuali.type.type.relation.type.group',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140311000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20140311000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20140311000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.atp2timeslot.kuali.atp.type.ExamPeriod.kuali.scheduling.time.slot.type.examoffering.adhoc','kuali.atp.type.ExamPeriod',1,'kuali.scheduling.time.slot.type.examoffering.adhoc','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed.atp2timeslot',0)
/
