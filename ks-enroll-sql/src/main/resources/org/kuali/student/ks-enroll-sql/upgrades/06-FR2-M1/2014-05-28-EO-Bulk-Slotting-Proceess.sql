
INSERT INTO KSEN_STATE_LIFECYCLE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120905000000', 'YYYYMMDDHH24MISS' ),'Exam Offering bulk slotting State Lifecycle','Exam Offering Bulk Slotting State Lifecycle','kuali.eo.slotting.process','kuali.eo.slotting.process',1)
/

INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120905000000', 'YYYYMMDDHH24MISS' ),'Exam Offering bulk slotting process completed','Exam Offering bulk slotting process completed','kuali.eo.slotting.state.completed','kuali.eo.slotting.process','Completed',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120905000000', 'YYYYMMDDHH24MISS' ),'Exam Offering bulk slotting process is in progress','Exam Offering bulk slotting process is in progress','kuali.eo.slotting.state.inprogress','kuali.eo.slotting.process','In Progress',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120905000000', 'YYYYMMDDHH24MISS' ),'Exam Offering bulk slotting has not started','Exam Offering bulk slotting has not started','kuali.eo.slotting.state.notstarted','kuali.eo.slotting.process','Not Started',0)
/
