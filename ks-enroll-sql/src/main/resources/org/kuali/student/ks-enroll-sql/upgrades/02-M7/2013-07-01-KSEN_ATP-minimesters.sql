INSERT INTO KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID)
  VALUES ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.kuali.atp.type.Mini-mester1A', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester1A', 1, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID)
  VALUES ('kuali.type.type.relation.type.allowed.kuali.atp.type.Mini-mester1A.kuali.milestone.type.group.instructional', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Mini-mester1A', 'kuali.milestone.type.group.instructional', 1, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID)
  VALUES ('kuali.type.type.relation.type.allowed.kuali.atp.type.Mini-mester1A.kuali.milestone.type.group.registration', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Mini-mester1A', 'kuali.milestone.type.group.registration', 0, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID)
  VALUES ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.kuali.atp.type.Mini-mester1B', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term', 'kuali.atp.type.Mini-mester1B', 1, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID)
  VALUES ('kuali.type.type.relation.type.allowed.kuali.atp.type.Mini-mester1B.kuali.milestone.type.group.instructional', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Mini-mester1B', 'kuali.milestone.type.group.instructional', 1, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID)
  VALUES ('kuali.type.type.relation.type.allowed.kuali.atp.type.Mini-mester1B.kuali.milestone.type.group.registration', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Mini-mester1B', 'kuali.milestone.type.group.registration', 0, 0, TIMESTAMP '2012-05-29 19:00:00', 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.AcademicCalendar.kuali.atp.type.Mini-mester1A','kuali.atp.type.AcademicCalendar',0,'kuali.atp.type.Mini-mester1A','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
INSERT INTO KSEN_TYPETYPE_RELTN (CREATEID,CREATETIME,ID,OWNER_TYPE_ID,RANK,RELATED_TYPE_ID,TYPETYPE_RELTN_STATE,TYPETYPE_RELTN_TYPE,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20120301000000', 'YYYYMMDDHH24MISS' ),'kuali.type.type.relation.type.allowed.kuali.atp.type.AcademicCalendar.kuali.atp.type.Mini-mester1B','kuali.atp.type.AcademicCalendar',0,'kuali.atp.type.Mini-mester1B','kuali.type.type.relation.state.active','kuali.type.type.relation.type.allowed',0)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.contains.kuali.atp.type.Fall.kuali.atp.type.Mini-mester1A', null, 'kuali.type.type.relation.type.contains', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Summer1', 'kuali.atp.type.Mini-mester1A', 1, 0, TIMESTAMP '2012-06-13 15:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.contains.kuali.atp.type.Fall.kuali.atp.type.Mini-mester1B', null, 'kuali.type.type.relation.type.contains', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Summer1', 'kuali.atp.type.Mini-mester1B', 1, 0, TIMESTAMP '2012-06-13 15:00:00', 'SYSTEMLOADER', null, null)
/
