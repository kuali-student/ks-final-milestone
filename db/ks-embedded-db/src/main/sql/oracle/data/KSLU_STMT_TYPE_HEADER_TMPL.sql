TRUNCATE TABLE KSLU_STMT_TYPE_HEADER_TMPL DROP STORAGE
/
INSERT INTO KSLU_STMT_TYPE_HEADER_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER)
  VALUES ('1','en','KUALI.CATALOG','kuali.luStatementType.prereqAcademicReadiness')
/
INSERT INTO KSLU_STMT_TYPE_HEADER_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('2','de','KUALI.CATALOG','kuali.luStatementType.prereqAcademicReadiness','Voraussetzung fur die $clu.getOfficialIdentifier().getLongName(): ')
/
INSERT INTO KSLU_STMT_TYPE_HEADER_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE)
  VALUES ('3','jp','KUALI.CATALOG','kuali.luStatementType.prereqAcademicReadiness','$clu.getOfficialIdentifier().getLongName() ã?¯å¿&é Æç§ ç:®: ')
/
INSERT INTO KSLU_STMT_TYPE_HEADER_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER)
  VALUES ('4','en','KUALI.CATALOG','kuali.luStatementType.coreqAcademicReadiness')
/
INSERT INTO KSLU_STMT_TYPE_HEADER_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER)
  VALUES ('5','en','KUALI.CATALOG','kuali.luStatementType.enrollAcademicReadiness')
/
INSERT INTO KSLU_STMT_TYPE_HEADER_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER)
  VALUES ('6','en','KUALI.CATALOG','kuali.luStatementType.antireqAcademicReadiness')
/
