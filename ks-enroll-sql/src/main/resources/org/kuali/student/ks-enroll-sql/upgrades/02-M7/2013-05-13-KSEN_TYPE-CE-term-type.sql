-- KSENROLL-6762 Continuing Education terms
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130513', 'YYYYMMDD' ),'Continuing Ed First Term','Continuing Ed First Term','Continuing Education Term 1','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.CETerm1',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130513', 'YYYYMMDD' ),'Continuing Ed Second Term','Continuing Ed Second Term','Continuing Education Term 2','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.CETerm2',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130513', 'YYYYMMDD' ),'Continuing Ed Third Term','Continuing Ed Third Term','Continuing Education Term 3','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.CETerm3',0)
/
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130513', 'YYYYMMDD' ),'Continuing Ed Fourth Term','Continuing Ed Fourth Term','Continuing Education Term 4','http://student.kuali.org/wsdl/atp/AtpInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.CETerm4',0)
/

-- CE TYPE RELATION DATA
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.kuali.atp.type.CETerm1', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term', 'kuali.atp.type.CETerm1', 1, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.kuali.atp.type.CETerm2', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term', 'kuali.atp.type.CETerm2', 1, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.kuali.atp.type.CETerm3', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term', 'kuali.atp.type.CETerm3', 1, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.kuali.atp.type.CETerm4', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term', 'kuali.atp.type.CETerm4', 1, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.CETerm1.kuali.milestone.type.group.instructional', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.CETerm1', 'kuali.milestone.type.group.instructional', 1, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.CETerm2.kuali.milestone.type.group.instructional', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.CETerm2', 'kuali.milestone.type.group.instructional', 1, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.CETerm3.kuali.milestone.type.group.instructional', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.CETerm3', 'kuali.milestone.type.group.instructional', 1, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.CETerm4.kuali.milestone.type.group.instructional', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.CETerm4', 'kuali.milestone.type.group.instructional', 1, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.CETerm1.kuali.milestone.type.group.registration', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.CETerm1', 'kuali.milestone.type.group.registration', 0, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.CETerm2.kuali.milestone.type.group.registration', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.CETerm2', 'kuali.milestone.type.group.registration', 0, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.CETerm3.kuali.milestone.type.group.registration', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.CETerm3', 'kuali.milestone.type.group.registration', 0, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.CETerm4.kuali.milestone.type.group.registration', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.CETerm4', 'kuali.milestone.type.group.registration', 0, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.AcademicCalendar.kuali.atp.type.CETerm1','kuali.atp.type.AcademicCalendar',0,'kuali.atp.type.CETerm1','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('UMDDATA',TO_DATE( '20121109000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20121101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.group.kuali.atp.type.CETerm1.kuali.atp.duration.Term','kuali.atp.type.CETerm1',0,'kuali.atp.duration.Term','kuali.type.type.relation.state.active','kuali.type.type.relation.type.group',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.AcademicCalendar.kuali.atp.type.CETerm2','kuali.atp.type.AcademicCalendar',0,'kuali.atp.type.CETerm2','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('UMDDATA',TO_DATE( '20121109000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20121101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.group.kuali.atp.type.CETerm2.kuali.atp.duration.Term','kuali.atp.type.CETerm2',0,'kuali.atp.duration.Term','kuali.type.type.relation.state.active','kuali.type.type.relation.type.group',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.AcademicCalendar.kuali.atp.type.CETerm3','kuali.atp.type.AcademicCalendar',0,'kuali.atp.type.CETerm3','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('UMDDATA',TO_DATE( '20121109000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20121101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.group.kuali.atp.type.CETerm3.kuali.atp.duration.Term','kuali.atp.type.CETerm3',0,'kuali.atp.duration.Term','kuali.type.type.relation.state.active','kuali.type.type.relation.type.group',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.AcademicCalendar.kuali.atp.type.CETerm4','kuali.atp.type.AcademicCalendar',0,'kuali.atp.type.CETerm4','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,EFF_DT,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('UMDDATA',TO_DATE( '20121109000000', 'YYYYMMDDHH24MISS' ),TO_DATE( '20121101000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.group.kuali.atp.type.CETerm4.kuali.atp.duration.Term','kuali.atp.type.CETerm4',0,'kuali.atp.duration.Term','kuali.type.type.relation.state.active','kuali.type.type.relation.type.group',0)
/