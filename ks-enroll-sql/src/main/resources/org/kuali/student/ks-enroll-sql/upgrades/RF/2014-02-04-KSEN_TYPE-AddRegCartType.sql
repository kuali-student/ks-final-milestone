---KSENROLL-11784 Add reg cart type to KSEN_TYPE table

INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140204', 'YYYYMMDD' ),'Reg cart type','Reg cart type','Reg cart type','http://student.kuali.org/wsdl/lpr/LprTransactionInfo',
  'http://student.kuali.org/wsdl/lpr/LprService','kuali.lpr.trans.type.registration.cart',0)
/

-- Delete the ones that exist
DELETE FROM KSEN_TYPE WHERE TYPE_KEY = 'kuali.lpr.trans.item.type.add'
/

DELETE FROM KSEN_TYPE WHERE TYPE_KEY = 'kuali.lpr.trans.item.type.drop'
/

-- Put in add, drop, update, swap
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140204', 'YYYYMMDD' ),'Reg request item ADD type','Reg request item ADD type','Reg request item ADD type','http://student.kuali.org/wsdl/lpr/LprTransactionItemInfo',
  'http://student.kuali.org/wsdl/lpr/LprService','kuali.course.registration.request.item.type.add',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140204', 'YYYYMMDD' ),'Reg request item DROP type','Reg request item DROP type','Reg request item DROP type','http://student.kuali.org/wsdl/lpr/LprTransactionItemInfo',
  'http://student.kuali.org/wsdl/lpr/LprService','kuali.course.registration.request.item.type.drop',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140204', 'YYYYMMDD' ),'Reg request item UPDATE type','Reg request item UPDATE type','Reg request item UPDATE type','http://student.kuali.org/wsdl/lpr/LprTransactionItemInfo',
  'http://student.kuali.org/wsdl/lpr/LprService','kuali.course.registration.request.item.type.update',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140204', 'YYYYMMDD' ),'Reg request item SWAP type','Reg request item SWAP type','Reg request item SWAP type','http://student.kuali.org/wsdl/lpr/LprTransactionItemInfo',
  'http://student.kuali.org/wsdl/lpr/LprService','kuali.course.registration.request.item.type.swap',0)
/