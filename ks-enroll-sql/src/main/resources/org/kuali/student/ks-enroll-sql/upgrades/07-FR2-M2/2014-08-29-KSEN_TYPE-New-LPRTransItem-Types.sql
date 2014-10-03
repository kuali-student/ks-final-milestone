-- KSENROLL-14076 Configure LPR Transaction Item Types

-- Put in add to waitlist, add from waitlist, drop from waitlist, update waitlist
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140829', 'YYYYMMDD' ),'Reg request item ADD TO WAITLIST type','Reg request item ADD TO WAITLIST type','Reg request item ADD TO WAITLIST type','http://student.kuali.org/wsdl/lpr/LprTransactionItemInfo',
  'http://student.kuali.org/wsdl/lpr/LprService','kuali.course.registration.request.item.type.add.to.waitlist',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140829', 'YYYYMMDD' ),'Reg request item ADD FROM WAITLIST type','Reg request item ADD FROM WAITLIST type','Reg request item ADD FROM WAITLIST type','http://student.kuali.org/wsdl/lpr/LprTransactionItemInfo',
  'http://student.kuali.org/wsdl/lpr/LprService','kuali.course.registration.request.item.type.add.from.waitlist',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140829', 'YYYYMMDD' ),'Reg request item DROP WAITLIST type','Reg request item DROP WAITLIST type','Reg request item DROP WAITLIST type','http://student.kuali.org/wsdl/lpr/LprTransactionItemInfo',
  'http://student.kuali.org/wsdl/lpr/LprService','kuali.course.registration.request.item.type.drop.waitlist',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140829', 'YYYYMMDD' ),'Reg request item UPDATE WAITLIST type','Reg request item UPDATE WAITLIST type','Reg request item UPDATE WAITLIST type','http://student.kuali.org/wsdl/lpr/LprTransactionItemInfo',
  'http://student.kuali.org/wsdl/lpr/LprService','kuali.course.registration.request.item.type.update.waitlist',0)
/