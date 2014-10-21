--KSENROLL-14373

-- types for Ges value and parameter
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140819000000', 'YYYYMMDDHH24MISS' ),'Generic value type','Generic value type','Value','http://student.kuali.org/wsdl/ges/ValueInfo','http://student.kuali.org/wsdl/ges/GesService','kuali.ges.value.type',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140819000000', 'YYYYMMDDHH24MISS' ),'Generic parameter type','Generic parameter type','Parameter','http://student.kuali.org/wsdl/ges/ParameterInfo','http://student.kuali.org/wsdl/ges/GesService','kuali.ges.parameter.type',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140819000000', 'YYYYMMDDHH24MISS' ),'A parameter that is applicable to a course or course offering','A parameter that is applicable to a course or course offering','Course Parameter','http://student.kuali.org/wsdl/ges/ParameterInfo','http://student.kuali.org/wsdl/ges/GesService','kuali.ges.parameter.type.course',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140819000000', 'YYYYMMDDHH24MISS' ),'Generic parameter group type','Generic parameter group type','Parameter Group','http://student.kuali.org/wsdl/ges/ParameterGroupInfo','http://student.kuali.org/wsdl/ges/GesService','kuali.ges.parametergroup.type',0)
/

-- lifecycle keys for Ges value and parameter states
INSERT INTO KSEN_STATE_LIFECYCLE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140819000000', 'YYYYMMDDHH24MISS' ),'Ges value state lifecycle','Ges value state lifecycle','kuali.ges.value.lifecycle','Ges value state lifecycle','SYSTEMLOADER',TO_DATE( '20140819000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE_LIFECYCLE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140819000000', 'YYYYMMDDHH24MISS' ),'Ges parameter state lifecycle','Ges parameter state lifecycle','kuali.ges.parameter.lifecycle','Ges parameter state lifecycle','SYSTEMLOADER',TO_DATE( '20140819000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE_LIFECYCLE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,NAME,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140819000000', 'YYYYMMDDHH24MISS' ),'Ges parameter group state lifecycle','Ges parameter group state lifecycle','kuali.ges.parametergroup.lifecycle','Ges parameter group state lifecycle','SYSTEMLOADER',TO_DATE( '20140819000000', 'YYYYMMDDHH24MISS' ),1)
/

-- states for Ges value, parameter and parameter group
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140819000000', 'YYYYMMDDHH24MISS' ),'Value state active','Value state active','kuali.ges.value.state.active','kuali.ges.value.lifecycle','Value state active',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140819000000', 'YYYYMMDDHH24MISS' ),'Value state inactive','Value state inactive','kuali.ges.value.state.inactive','kuali.ges.value.lifecycle','Value state inactive',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140819000000', 'YYYYMMDDHH24MISS' ),'Parameter state active','Parameter state active','kuali.ges.parameter.state.active','kuali.ges.parameter.lifecycle','Parameter state active',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140819000000', 'YYYYMMDDHH24MISS' ),'Parameter state inactive','Parameter state inactive','kuali.ges.parameter.state.inactive','kuali.ges.parameter.lifecycle','Parameter state inactive',0)
/
INSERT INTO KSEN_STATE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,ID,LIFECYCLE_KEY,NAME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20140819000000', 'YYYYMMDDHH24MISS' ),'Parameter group state active','Parameter group state active','kuali.ges.parametergroup.state.active','kuali.ges.parametergroup.lifecycle','Parameter group state active',0)
/

-- parameter keys
INSERT INTO KSEN_GES_PARM (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, GES_PARM_TYPE, GES_PARM_STATE, GES_VALUE_TYPE_ID, REQ_UNIQUE_PRIORITY_IND, VER_NBR, CREATEID, CREATETIME, UPDATEID,UPDATETIME)
  VALUES ('kuali.ges.parameter.key.rollover.roomassignment.include', 'Rollover include room assignment', 'Rollover include room assignment', 'Rollover include room assignment', 'kuali.ges.parameter.type.course', 'kuali.ges.parameter.state.active', 'BOOLEAN', 0, 0, 'admin', TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'admin',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ))
/
INSERT INTO KSEN_GES_PARM (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, GES_PARM_TYPE, GES_PARM_STATE, GES_VALUE_TYPE_ID, REQ_UNIQUE_PRIORITY_IND, VER_NBR, CREATEID, CREATETIME, UPDATEID,UPDATETIME)
  VALUES ('kuali.ges.parameter.key.rollover.allschedulinginformation.include', 'Rollover include all schedule information', 'Rollover include all schedule information', 'Rollover include all schedule information', 'kuali.ges.parameter.type.course', 'kuali.ges.parameter.state.active', 'BOOLEAN', 0, 0, 'admin', TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'admin',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ))
/
INSERT INTO KSEN_GES_PARM (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, GES_PARM_TYPE, GES_PARM_STATE, GES_VALUE_TYPE_ID, REQ_UNIQUE_PRIORITY_IND, VER_NBR, CREATEID, CREATETIME, UPDATEID,UPDATETIME)
  VALUES ('kuali.ges.parameter.key.rollover.instructorinformation.include', 'Rollover include instructor information', 'Rollover include instructor information', 'Rollover include instructor information', 'kuali.ges.parameter.type.course', 'kuali.ges.parameter.state.active', 'BOOLEAN', 0, 0, 'admin', TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'admin',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ))
/
INSERT INTO KSEN_GES_PARM (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, GES_PARM_TYPE, GES_PARM_STATE, GES_VALUE_TYPE_ID, REQ_UNIQUE_PRIORITY_IND, VER_NBR, CREATEID, CREATETIME, UPDATEID,UPDATETIME)
  VALUES ('kuali.ges.parameter.key.rollover.activityofferingsstateofcancelled.include', 'Rollover include activity offerings with state of cancelled', 'Rollover include activity offerings with state of cancelled', 'Rollover include activity offerings with state of cancelled', 'kuali.ges.parameter.type.course', 'kuali.ges.parameter.state.active', 'BOOLEAN', 0, 0, 'admin', TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'admin',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ))
/
INSERT INTO KSEN_GES_PARM (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, GES_PARM_TYPE, GES_PARM_STATE, GES_VALUE_TYPE_ID, REQ_UNIQUE_PRIORITY_IND, VER_NBR, CREATEID, CREATETIME, UPDATEID,UPDATETIME)
  VALUES ('kuali.ges.parameter.key.rollover.activityofferingscolocation.include', 'Rollover include colocation of activity offerings', 'Rollover include colocation of activity offerings', 'Rollover include colocation of activity offerings', 'kuali.ges.parameter.type.course', 'kuali.ges.parameter.state.active', 'BOOLEAN', 0, 0, 'admin', TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'admin',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ))
/
INSERT INTO KSEN_GES_PARM (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, GES_PARM_TYPE, GES_PARM_STATE, GES_VALUE_TYPE_ID, REQ_UNIQUE_PRIORITY_IND, VER_NBR, CREATEID, CREATETIME, UPDATEID,UPDATETIME)
  VALUES ('kuali.ges.parameter.key.rollover.cluversions.include', 'Rollover include CLU versions', 'Rollover include courses with some changes in CLU versions', 'Rollover include courses with some changes in CLU versions', 'kuali.ges.parameter.type.course', 'kuali.ges.parameter.state.active', 'BOOLEAN', 0, 0, 'admin', TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'admin',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ))
/
INSERT INTO KSEN_GES_PARM (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, GES_PARM_TYPE, GES_PARM_STATE, GES_VALUE_TYPE_ID, REQ_UNIQUE_PRIORITY_IND, VER_NBR, CREATEID, CREATETIME, UPDATEID,UPDATETIME)
  VALUES ('kuali.ges.parameter.key.rollover.requisitesaddedincourseoffering.include', 'Rollover include courses with requisites edited in course offering', 'Rollover include courses with requisites edited in course offering', 'Rollover include courses with requisites edited in course offering', 'kuali.ges.parameter.type.course', 'kuali.ges.parameter.state.active', 'BOOLEAN', 0, 0, 'admin', TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'admin',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ))
/

-- parameter group keys
INSERT INTO KSEN_GES_PARM_GRP (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, GES_PARM_GRP_TYPE, GES_PARM_GRP_STATE, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME)
  VALUES ('kuali.ges.parametergroup.key.rollover', 'Parameter group for rollover', 'Parameter group for rollover', 'Parameter group for rollover', 'kuali.ges.parametergroup.type', 'kuali.ges.parametergroup.state.active', 0, 'admin', TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ),'admin',TO_DATE( '20140910000000', 'YYYYMMDDHH24MISS' ))
/