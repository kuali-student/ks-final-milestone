/* Academic Plan types */
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20131107000000', 'YYYYMMDDHH24MISS' ),'Student learning plan type.','Student learning plan type.','Learning Plan',
  'http://student.kuali.org/wsdl/acadplan/LearningPlanInfo','http://student.kuali.org/wsdl/acadplan/AcademicPlanService',
  'kuali.academicplan.type.plan',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20131107000000', 'YYYYMMDDHH24MISS' ),'Student learning plan template type.','Student learning plan template type.','Learning Plan Template',
  'http://student.kuali.org/wsdl/acadplan/LearningPlanInfo','http://student.kuali.org/wsdl/acadplan/AcademicPlanService',
  'kuali.academicplan.type.plan.template',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20131107000000', 'YYYYMMDDHH24MISS' ),'Student learning plan review type.','Student learning plan review type.','Learning Plan Review',
  'http://student.kuali.org/wsdl/acadplan/LearningPlanInfo','http://student.kuali.org/wsdl/acadplan/AcademicPlanService',
  'kuali.academicplan.type.plan.review',0)
/

/* Academic Plan Item types */
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20131107000000', 'YYYYMMDDHH24MISS' ),'Student learning plan course item type.','Student learning plan course item type.','Course Item',
  'http://student.kuali.org/wsdl/acadplan/PlanItemInfo','http://student.kuali.org/wsdl/acadplan/AcademicPlanService',
  'kuali.academicplan.type.item.course',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20131107000000', 'YYYYMMDDHH24MISS' ),'Term Note.','Term Note.','Term Note',
  'http://student.kuali.org/wsdl/comment/CommentInfo','http://student.kuali.org/wsdl/comment/CommnetService',
  'kuali.ap.type.note.term',0)
/
