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
  VALUES ('SYSTEMLOADER',TO_DATE( '20131107000000', 'YYYYMMDDHH24MISS' ),'Student learning plan backup item type.','Student learning plan backup item type.','Backup Item',
  'http://student.kuali.org/wsdl/acadplan/PlanItemInfo','http://student.kuali.org/wsdl/acadplan/AcademicPlanService',
  'kuali.academicplan.item.type.backup',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20131107000000', 'YYYYMMDDHH24MISS' ),'Student learning plan planned item type.','Student learning plan planned item type.','Planned Item',
  'http://student.kuali.org/wsdl/acadplan/PlanItemInfo','http://student.kuali.org/wsdl/acadplan/AcademicPlanService',
  'kuali.academicplan.item.type.planned',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20131107000000', 'YYYYMMDDHH24MISS' ),'Student learning plan what-if item type.','Student learning plan what-if item type.','What-if Item',
  'http://student.kuali.org/wsdl/acadplan/PlanItemInfo','http://student.kuali.org/wsdl/acadplan/AcademicPlanService',
  'kuali.academicplan.item.type.whatif',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20131107000000', 'YYYYMMDDHH24MISS' ),'Student learning plan wishlist item type.','Student learning plan wishlist item type.','Wishlist Item',
  'http://student.kuali.org/wsdl/acadplan/PlanItemInfo','http://student.kuali.org/wsdl/acadplan/AcademicPlanService',
  'kuali.academicplan.item.type.wishlist',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20131107000000', 'YYYYMMDDHH24MISS' ),'Student learning plan shopping cart item type.','Student learning plan shopping cart item type.','Shopping Cart Item',
  'http://student.kuali.org/wsdl/acadplan/PlanItemInfo','http://student.kuali.org/wsdl/acadplan/AcademicPlanService',
  'kuali.academicplan.item.type.cart',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20131107000000', 'YYYYMMDDHH24MISS' ),'Term Note.','Term Note.','Term Note',
  'http://student.kuali.org/wsdl/comment/CommentInfo','http://student.kuali.org/wsdl/comment/CommnetService',
  'kuali.ap.type.note.term',0)
/
