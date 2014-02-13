-- KSENROLL-7882 Create new Type Groupings to represent subterms and parent terms
INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130705', 'YYYYMMDD' ),'Gouping of subterms','Gouping of subterms','Includes','http://student.kuali.org/wsdl/atp/AtpAtpRelationInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.group.term.subterm',0)
/

INSERT INTO KSEN_TYPE (CREATEID,CREATETIME,DESCR_FORMATTED,DESCR_PLAIN,NAME,REF_OBJECT_URI,SERVICE_URI,TYPE_KEY,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '20130705', 'YYYYMMDD' ),'Gouping of subterms','Gouping of parent terms. A parent term has a subterm associated with it.','Includes','http://student.kuali.org/wsdl/atp/AtpAtpRelationInfo','http://student.kuali.org/wsdl/atp/AtpService','kuali.atp.type.group.term.parent',0)
/

-- Add the subterm relations
INSERT INTO KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID)
  VALUES ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.subterm.kuali.atp.type.HalfFall1', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term.subterm', 'kuali.atp.type.HalfFall1', 1, 0, TIMESTAMP '2012-07-05 19:00:00', 'SYSTEMLOADER', null, null)
/
INSERT INTO KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID)
  VALUES ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.subterm.kuali.atp.type.HalfFall2', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term.subterm', 'kuali.atp.type.HalfFall2', 1, 0, TIMESTAMP '2012-07-05 19:00:00', 'SYSTEMLOADER', null, null)
/

-- Add the parent term relations

-- CE TYPE RELATION DATA
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.parent.kuali.atp.type.CETerm1', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term.parent', 'kuali.atp.type.CETerm1', 1, 0, TIMESTAMP '2012-07-05 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.parent.kuali.atp.type.CETerm2', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term.parent', 'kuali.atp.type.CETerm2', 1, 0, TIMESTAMP '2012-07-05 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.parent.kuali.atp.type.CETerm3', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term.parent', 'kuali.atp.type.CETerm3', 1, 0, TIMESTAMP '2012-07-05 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.parent.kuali.atp.type.CETerm4', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term.parent', 'kuali.atp.type.CETerm4', 1, 0, TIMESTAMP '2012-07-05 19:00:00', 'SYSTEMLOADER', null, null)
/
-- Winter, Spring, Fall, Summer 1, 2
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.parent.kuali.atp.type.Winter', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term.parent', 'kuali.atp.type.Winter', 1, 0, TIMESTAMP '2012-07-05 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.parent.kuali.atp.type.Spring', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term.parent', 'kuali.atp.type.Spring', 1, 0, TIMESTAMP '2012-07-05 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.parent.kuali.atp.type.Fall', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term.parent', 'kuali.atp.type.Fall', 1, 0, TIMESTAMP '2012-07-05 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.parent.kuali.atp.type.Summer', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term.parent', 'kuali.atp.type.Summer', 1, 0, TIMESTAMP '2012-07-05 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.parent.kuali.atp.type.Summer1', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term.parent', 'kuali.atp.type.Summer1', 1, 0, TIMESTAMP '2012-07-05 19:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.parent.kuali.atp.type.Summer2', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term.parent', 'kuali.atp.type.Summer2', 1, 0, TIMESTAMP '2012-07-05 19:00:00', 'SYSTEMLOADER', null, null)
/






