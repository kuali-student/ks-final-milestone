TRUNCATE TABLE KRSB_QRTZ_JOB_DETAILS DROP STORAGE
/
INSERT INTO KRSB_QRTZ_JOB_DETAILS (IS_DURABLE,IS_STATEFUL,IS_VOLATILE,JOB_CLASS_NAME,JOB_DATA,JOB_GROUP,JOB_NAME,REQUESTS_RECOVERY)
  VALUES ('0','0','0','org.kuali.rice.kew.mail.DailyEmailJob',oracle.sql.BLOB@90ed81,'Email Batch','Daily Email','0')
/
INSERT INTO KRSB_QRTZ_JOB_DETAILS (IS_DURABLE,IS_STATEFUL,IS_VOLATILE,JOB_CLASS_NAME,JOB_DATA,JOB_GROUP,JOB_NAME,REQUESTS_RECOVERY)
  VALUES ('0','0','0','org.kuali.rice.kew.mail.WeeklyEmailJob',oracle.sql.BLOB@d8c3ee,'Email Batch','Weekly Email','0')
/
INSERT INTO KRSB_QRTZ_JOB_DETAILS (DESCRIPTION,IS_DURABLE,IS_STATEFUL,IS_VOLATILE,JOB_CLASS_NAME,JOB_DATA,JOB_GROUP,JOB_NAME,REQUESTS_RECOVERY)
  VALUES ('
                Job that handles asynchronous delivery and dismissal of MessageDeliveries
            ','1','1','0','org.kuali.rice.kcb.quartz.MessageProcessingJob',oracle.sql.BLOB@1277a30,'KCB-Delivery','MessageProcessingJobDetail','1')
/
