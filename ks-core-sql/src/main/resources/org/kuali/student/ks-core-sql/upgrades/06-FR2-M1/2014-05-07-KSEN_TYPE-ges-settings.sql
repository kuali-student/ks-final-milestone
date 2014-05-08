/* Ges types */
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20131107000000', 'YYYYMMDDHH24MISS' ),'Generic ges parameter type.','Generic ges parameter type.','Paramter',
  'http://student.kuali.org/wsdl/ges/ParameterInfo','http://student.kuali.org/wsdl/ges/GesService',
  'kuali.ges.parameter.type',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20131107000000', 'YYYYMMDDHH24MISS' ),'Generic ges value type.','Generic ges value type.','Value',
  'http://student.kuali.org/wsdl/ges/ValueInfo','http://student.kuali.org/wsdl/ges/GesService',
  'kuali.ges.value.type',0)