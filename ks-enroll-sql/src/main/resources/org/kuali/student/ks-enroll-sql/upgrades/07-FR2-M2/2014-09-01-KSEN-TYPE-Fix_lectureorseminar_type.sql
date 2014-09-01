--KSENROLL-14667
-- clean up the LUI Type
-- clear dependent relationship
delete from KSEN_TYPETYPE_RELTN
where
RELATED_TYPE_ID= 'kuali.lui.type.activity.offering.LectureORSeminar'
/
-- update ksen-type with proper value
update KSEN_TYPE
set TYPE_KEY = 'kuali.lui.type.activity.offering.lectureorseminar'
where
type_key = 'kuali.lui.type.activity.offering.LectureORSeminar'
/
-- add back relationship but with proper values
insert into KSEN_TYPETYPE_RELTN (CREATEID, CREATETIME, EFF_DT, EXPIR_DT, ID, OBJ_ID, OWNER_TYPE_ID, RANK, RELATED_TYPE_ID, TYPETYPE_RELTN_STATE, TYPETYPE_RELTN_TYPE, UPDATEID, UPDATETIME, VER_NBR)
values ('SYSTEMLOADER', TIMESTAMP '2012-02-29 19:00:00', null, null, 'kuali.type.type.relation.type.group.kuali.lui.type.grouping.activity.kuali.lui.type.activity.offering.lectureorseminar', null, 'kuali.lui.type.grouping.activity', 0, 'kuali.lui.type.activity.offering.lectureorseminar', 'kuali.type.type.relation.state.active', 'kuali.type.type.relation.type.group', null, null, 0);
/

-- -------------------------------------
-- Clean up the CLU TYPE
-- clear dependent relationship
delete from KSEN_TYPETYPE_RELTN
where
RELATED_TYPE_ID= 'kuali.lu.type.activity.LectureORSeminar'
/
-- update ksen-type with proper value
update KSEN_TYPE
set TYPE_KEY = 'kuali.lu.type.activity.lectureorseminar'
where
type_key = 'kuali.lu.type.activity.LectureORSeminar'
/
-- add back relationship but with proper values
insert into KSEN_TYPETYPE_RELTN (CREATEID, CREATETIME, EFF_DT, EXPIR_DT, ID, OBJ_ID, OWNER_TYPE_ID, RANK, RELATED_TYPE_ID, TYPETYPE_RELTN_STATE, TYPETYPE_RELTN_TYPE, UPDATEID, UPDATETIME, VER_NBR)
values ('SYSTEMLOADER', TIMESTAMP '2012-02-29 19:00:00', null, null, 'kuali.type.type.relation.type.group.kuali.lu.type.grouping.activity.kuali.lu.type.activity.lectureorseminar', null, 'kuali.lu.type.grouping.activity', 0, 'kuali.lu.type.activity.lectureorseminar', 'kuali.type.type.relation.state.active', 'kuali.type.type.relation.type.group', null, null, 0);
/