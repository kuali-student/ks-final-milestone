INSERT INTO KSPL_LRNG_PLAN_RICH_TEXT(FORMATTED,ID,PLAIN,VER_NBR)
  VALUES ('Student Learning Plan Test','testPlan2Desc','Student Learning Plan Test',0)
/

INSERT INTO KSPL_LRNG_PLAN(CREATEID,CREATETIME,ID,OBJ_ID,RT_DESCR_ID,SHARED,STUDENT_ID,TYPE_ID,VER_NBR)
  VALUES ('student',CURRENT_TIMESTAMP,'testPlan2','obj-lp2','testPlan2Desc',1,'student','kuali.academicplan.type.plan',0)
/


