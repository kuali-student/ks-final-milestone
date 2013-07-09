-- KSENROLL-7679
-- changing activityoffering to activityoffering.standard
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),'A standard timeslot that is not restricted from being part of any type of term. It represents a "general" standardized time slot for all term types.','A standard timeslot that is not restricted from being part of any type of term. It represents a "general" standardized time slot for all term types.','Time slot','http://student.kuali.org/wsdl/scheduling/TimeSlotInfo','http://student.kuali.org/wsdl/scheduling/SchedulingService','kuali.scheduling.time.slot.type.activityoffering.standard',0)
/
UPDATE KSEN_SCHED_TMSLOT SET TM_SLOT_TYPE = 'kuali.scheduling.time.slot.type.activityoffering.standard' WHERE TM_SLOT_TYPE = 'kuali.scheduling.time.slot.type.activityoffering'
/
DELETE FROM KSEN_TYPE WHERE TYPE_KEY = 'kuali.scheduling.time.slot.type.activityoffering'
/
-- adding the rest of kuali.scheduling.time.slot.type.activityoffering... to KSEN_TYPE
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),'Time slots that are ad hoc.','Time slots that are ad hoc.','Time slot','http://student.kuali.org/wsdl/scheduling/TimeSlotInfo','http://student.kuali.org/wsdl/scheduling/SchedulingService','kuali.scheduling.time.slot.type.activityoffering.adhoc',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),'Time slots that are part of a full term, and not really unique to a particular type of full term (such as fall or winter full terms).','Time slots that are part of a full term, and not really unique to a particular type of full term (such as fall or winter full terms).','Time slot','http://student.kuali.org/wsdl/scheduling/TimeSlotInfo','http://student.kuali.org/wsdl/scheduling/SchedulingService','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),'Time slots that are part of a Fall full term.','Time slots that are part of a Fall full term.','Time slot','http://student.kuali.org/wsdl/scheduling/TimeSlotInfo','http://student.kuali.org/wsdl/scheduling/SchedulingService','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),'Time slots that are part of a Spring full term.','Time slots that are part of a Spring full term.','Time slot','http://student.kuali.org/wsdl/scheduling/TimeSlotInfo','http://student.kuali.org/wsdl/scheduling/SchedulingService','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),'Time slots that are part of a Winter full term.','Time slots that are part of a Winter full term.','Time slot','http://student.kuali.org/wsdl/scheduling/TimeSlotInfo','http://student.kuali.org/wsdl/scheduling/SchedulingService','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.winter',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),'Time slots that are part of a Summer full term.','Time slots that are part of a Summer full term.','Time slot','http://student.kuali.org/wsdl/scheduling/TimeSlotInfo','http://student.kuali.org/wsdl/scheduling/SchedulingService','kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.summer',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),'Time slots that are part of a half term, and not really unique to a particular type of half term (such as summer 1 or 2 half term).','Time slots that are part of a half term, and not really unique to a particular type of half term (such as summer 1 or 2 half term).','Time slot','http://student.kuali.org/wsdl/scheduling/TimeSlotInfo','http://student.kuali.org/wsdl/scheduling/SchedulingService','kuali.scheduling.time.slot.type.activityoffering.standard.halfterm',0)
/
-- adding relations
-- Fall
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Fall.kuali.scheduling.time.slot.type.activityoffering.standard','kuali.atp.type.Fall',1,'kuali.scheduling.time.slot.type.activityoffering.standard','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Fall.kuali.scheduling.time.slot.type.activityoffering.tba','kuali.atp.type.Fall',1,'kuali.scheduling.time.slot.type.activityoffering.tba','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Fall.kuali.scheduling.time.slot.type.activityoffering.standard.fullterm','kuali.atp.type.Fall',1,'kuali.scheduling.time.slot.type.activityoffering.standard.fullterm','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Fall.kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','kuali.atp.type.Fall',1,'kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Fall.kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.atp.type.Fall',1,'kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
-- HalfFall1
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.HalfFall1.kuali.scheduling.time.slot.type.activityoffering.standard','kuali.atp.type.HalfFall1',1,'kuali.scheduling.time.slot.type.activityoffering.standard','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.HalfFall1.kuali.scheduling.time.slot.type.activityoffering.tba','kuali.atp.type.HalfFall1',1,'kuali.scheduling.time.slot.type.activityoffering.tba','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.HalfFall1.kuali.scheduling.time.slot.type.activityoffering.standard.halfterm','kuali.atp.type.HalfFall1',1,'kuali.scheduling.time.slot.type.activityoffering.standard.halfterm','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.HalfFall1.kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.atp.type.HalfFall1',1,'kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
-- HalfFall2
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.HalfFall2.kuali.scheduling.time.slot.type.activityoffering.standard','kuali.atp.type.HalfFall2',1,'kuali.scheduling.time.slot.type.activityoffering.standard','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.HalfFall2.kuali.scheduling.time.slot.type.activityoffering.tba','kuali.atp.type.HalfFall2',1,'kuali.scheduling.time.slot.type.activityoffering.tba','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.HalfFall2.kuali.scheduling.time.slot.type.activityoffering.standard.halfterm','kuali.atp.type.HalfFall2',1,'kuali.scheduling.time.slot.type.activityoffering.standard.halfterm','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.HalfFall2.kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.atp.type.HalfFall2',1,'kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
-- Winter
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Winter.kuali.scheduling.time.slot.type.activityoffering.standard','kuali.atp.type.Winter',1,'kuali.scheduling.time.slot.type.activityoffering.standard','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Winter.kuali.scheduling.time.slot.type.activityoffering.tba','kuali.atp.type.Winter',1,'kuali.scheduling.time.slot.type.activityoffering.tba','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Winter.kuali.scheduling.time.slot.type.activityoffering.standard.fullterm','kuali.atp.type.Winter',1,'kuali.scheduling.time.slot.type.activityoffering.standard.fullterm','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Winter.kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.winter','kuali.atp.type.Winter',1,'kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.winter','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Winter.kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.atp.type.Winter',1,'kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
-- Spring
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Spring.kuali.scheduling.time.slot.type.activityoffering.standard','kuali.atp.type.Spring',1,'kuali.scheduling.time.slot.type.activityoffering.standard','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Spring.kuali.scheduling.time.slot.type.activityoffering.tba','kuali.atp.type.Spring',1,'kuali.scheduling.time.slot.type.activityoffering.tba','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Spring.kuali.scheduling.time.slot.type.activityoffering.standard.fullterm','kuali.atp.type.Spring',1,'kuali.scheduling.time.slot.type.activityoffering.standard.fullterm','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Spring.kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','kuali.atp.type.Spring',1,'kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Spring.kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.atp.type.Spring',1,'kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
-- HalfSpring1
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.HalfSpring1.kuali.scheduling.time.slot.type.activityoffering.standard','kuali.atp.type.HalfSpring1',1,'kuali.scheduling.time.slot.type.activityoffering.standard','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.HalfSpring1.kuali.scheduling.time.slot.type.activityoffering.tba','kuali.atp.type.HalfSpring1',1,'kuali.scheduling.time.slot.type.activityoffering.tba','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.HalfSpring1.kuali.scheduling.time.slot.type.activityoffering.standard.halfterm','kuali.atp.type.HalfSpring1',1,'kuali.scheduling.time.slot.type.activityoffering.standard.halfterm','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.HalfSpring1.kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.atp.type.HalfSpring1',1,'kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
-- HalfSpring2
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.HalfSpring2.kuali.scheduling.time.slot.type.activityoffering.standard','kuali.atp.type.HalfSpring2',1,'kuali.scheduling.time.slot.type.activityoffering.standard','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.HalfSpring2.kuali.scheduling.time.slot.type.activityoffering.tba','kuali.atp.type.HalfSpring2',1,'kuali.scheduling.time.slot.type.activityoffering.tba','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.HalfSpring2.kuali.scheduling.time.slot.type.activityoffering.standard.halfterm','kuali.atp.type.HalfSpring2',1,'kuali.scheduling.time.slot.type.activityoffering.standard.halfterm','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.HalfSpring2.kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.atp.type.HalfSpring2',1,'kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
-- Summer
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Summer.kuali.scheduling.time.slot.type.activityoffering.standard','kuali.atp.type.Summer',1,'kuali.scheduling.time.slot.type.activityoffering.standard','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Summer.kuali.scheduling.time.slot.type.activityoffering.tba','kuali.atp.type.Summer',1,'kuali.scheduling.time.slot.type.activityoffering.tba','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Summer.kuali.scheduling.time.slot.type.activityoffering.standard.fullterm','kuali.atp.type.Summer',1,'kuali.scheduling.time.slot.type.activityoffering.standard.fullterm','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Summer.kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.summer','kuali.atp.type.Summer',1,'kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.summer','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Summer.kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.atp.type.Summer',1,'kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
-- Adhoc
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,EXPIR_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130708000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20110101000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '21000101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.Adhoc.kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.atp.type.Adhoc',1,'kuali.scheduling.time.slot.type.activityoffering.adhoc','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/