---KSENROLL-11784 Add reg cart type to KSEN_TYPE table

INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140204', 'YYYYMMDD' ),'Reg cart type','Reg cart type','Reg cart type','http://student.kuali.org/wsdl/lpr/LprTransactionInfo',
  'http://student.kuali.org/wsdl/lpr/LprService','kuali.lpr.trans.type.registration.cart',0)
/