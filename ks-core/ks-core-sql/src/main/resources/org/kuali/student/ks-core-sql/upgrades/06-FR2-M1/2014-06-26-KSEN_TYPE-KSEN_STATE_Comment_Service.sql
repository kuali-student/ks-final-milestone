-- KSENROLL-13202
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140626000000', 'YYYYMMDDHH24MISS' ),'Administrative Comment','Administrative Comment','Administrative Comment','http://student.kuali.org/wsdl/comment/CommentInfo','http://student.kuali.org/wsdl/comment/CommentService','kuali.comment.comment.type.comment.administrative',0)
/
INSERT INTO KSEN_STATE_LIFECYCLE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140626000000', 'YYYYMMDDHH24MISS' ),'Comment State Lifecycle','Comment State Lifecycle','kuali.comment.comment.lifecycle','Comment State Lifecycle','SYSTEMLOADER',TO_DATE( '20140626000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140626000000', 'YYYYMMDDHH24MISS' ),'Comment State Active','Comment State Active','kuali.comment.comment.state.active','kuali.comment.comment.lifecycle','Comment State Active',0)
/