-- KSENROLL-4979 Convert Subject Code data into Organization and OrgOrgRelation data

INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130207', 'YYYYMMDD' ),'Subject Code (Organization Type)','Subject Code (Organization Type)','Subject Code','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.SubjectCode',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130207', 'YYYYMMDD' ),'Subject Code To Org (Org-Org Type)','Subject Code To Org (Org-Org Type)','Subject Code To Org (Org-Org Type)','http://student.kuali.org/wsdl/organization/OrgInfo','http://student.kuali.org/wsdl/organization/OrganizationService','kuali.org.org.relation.type.subjectcode2org',0)
/
