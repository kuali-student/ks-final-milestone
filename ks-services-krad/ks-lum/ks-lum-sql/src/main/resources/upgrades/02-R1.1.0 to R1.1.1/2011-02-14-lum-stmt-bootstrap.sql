INSERT INTO KSST_REQ_COM_TYPE (EFF_DT,NAME,TYPE_DESC,TYPE_KEY,VER_NBR)
  VALUES (TO_DATE( '20000101000000', 'YYYYMMDDHH24MISS' ), 'Enrolled in all required courses', 'Must be concurrently enrolled in all courses from <courses>', 'kuali.reqComponent.type.course.courseset.enrolled.all', 0)
/


INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CC-3', 'kuali.statement.type.course.academicReadiness.coreq', 'kuali.reqComponent.type.course.courseset.enrolled.all', 3, 0)
/

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('9','en','KUALI.RULE','kuali.reqComponent.type.course.courseset.enrolled.all','#if($courseCluSet.getCluList().size() == 1)Must be concurrently enrolled in $courseCluSet.getCluSetAsCode()#{else}Must be concurrently enrolled in all courses from $courseCluSet.getCluSetAsCode()#end',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('109','en','KUALI.RULE.PREVIEW','kuali.reqComponent.type.course.courseset.enrolled.all','#if($courseCluSet.getCluList().size() == 1)Must be concurrently enrolled in#{else}Must be concurrently enrolled in all courses from#end',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('209','en','KUALI.RULE.EXAMPLE','kuali.reqComponent.type.course.courseset.enrolled.all','Must be concurrently enrolled in all courses from (MATH111, 140, 220, and STAT100)',0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID,LANGUAGE,NL_USUAGE_TYPE_KEY,OWNER,TEMPLATE,VER_NBR)
  VALUES ('309','en','KUALI.RULE.COMPOSITION','kuali.reqComponent.type.course.courseset.enrolled.all','<reqCompFieldType=kuali.reqComponent.field.type.course.cluSet.id;reqCompFieldLabel=Courses>',0)
/

INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_FIELD_TYPE_ID,REQ_COMP_TYPE_ID)
  VALUES ('kuali.reqComponent.field.type.course.cluSet.id','kuali.reqComponent.type.course.courseset.enrolled.all')
/