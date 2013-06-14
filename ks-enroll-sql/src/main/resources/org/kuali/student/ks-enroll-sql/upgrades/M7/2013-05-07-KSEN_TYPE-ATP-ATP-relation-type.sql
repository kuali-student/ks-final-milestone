-- KSENROLL-6200 ATP to ATP Types Relationships records
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130507', 'YYYYMMDD' ),'The first ATP includes in the second ATP','The first ATP includes in the second ATP','Includes','http://student.kuali.org/wsdl/atp/AtpAtpRelationInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.atp.relation.includes',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130507', 'YYYYMMDD' ),'The first ATP is associated with the second ATP','The first ATP is associated with the second ATP','Associated','http://student.kuali.org/wsdl/atp/AtpAtpRelationInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.atp.relation.associated',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130507', 'YYYYMMDD' ),'The first ATP IMMEDIATELY precedes the second ATP','The first ATP IMMEDIATELY precedes the second ATP','Precedes','http://student.kuali.org/wsdl/atp/AtpAtpRelationInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.atp.relation.precedes',0)
/


