-- KSENROLL-14461

INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
VALUES ('SYSTEMLOADER',TO_DATE( '20140826', 'YYYYMMDD' ),'Student course record type','Student course record type','Student Course Record',
        'http://student.kuali.org/wsdl/academicrecord/StudentCourseRecordInfo','http://student.kuali.org/wsdl/academicrecord/AcademicRecordService',
        'kuali.academicrecord.studentcourserecord.type.course',0)
/

INSERT INTO KSEN_STATE_LIFECYCLE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140826', 'YYYYMMDD' ),'AR Student Course Record State Lifecycle','AR Student Course Record State Lifecycle','kuali.academicrecord.studentcourserecord.lifecycle','kuali.academicrecord.studentcourserecord.lifecycle',1)
/

INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140826', 'YYYYMMDD' ),'AR Student Course Record State - Completed','AR Student Course Record State - Completed','kuali.academicrecord.studentcourserecord.state.completed','kuali.academicrecord.studentcourserecord.lifecycle','Completed',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140826', 'YYYYMMDD' ),'AR Student Course Record State - Registered','AR Student Course Record State - Registered','kuali.academicrecord.studentcourserecord.state.registered','kuali.academicrecord.studentcourserecord.lifecycle','Registered',0)
/
