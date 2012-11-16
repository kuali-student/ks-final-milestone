-- Update Summer1 and Summer2 term types
update KSEN_TYPE SET REF_OBJECT_URI = 'http://student.kuali.org/wsdl/atp/AtpInfo', SERVICE_URI='http://student.kuali.org/wsdl/atp/AtpService', CREATETIME = TIMESTAMP '2012-11-09 00:00:00', CREATEID = 'UMDDATA' WHERE TYPE_KEY = 'kuali.atp.type.Summer1';
/
update KSEN_TYPE SET REF_OBJECT_URI = 'http://student.kuali.org/wsdl/atp/AtpInfo', SERVICE_URI='http://student.kuali.org/wsdl/atp/AtpService', CREATETIME = TIMESTAMP '2012-11-09 00:00:00', CREATEID = 'UMDDATA' WHERE TYPE_KEY = 'kuali.atp.type.Summer2';
/

-- Add type-type relation for Summer 1
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.Summer1.kuali.atp.duration.Term', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Summer1', 'kuali.atp.duration.Term', 0, 0, TIMESTAMP '2012-09-12 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.kuali.atp.type.Summer1', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term', 'kuali.atp.type.Summer1', 2, 0, TIMESTAMP '2012-03-01 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.AcademicCalendar.kuali.atp.type.Summer1', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Summer1', 2, 0, TIMESTAMP '2012-03-01 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.Summer1.kuali.milestone.type.group.instructional', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Summer1', 'kuali.milestone.type.group.instructional', 1, 0, TIMESTAMP '2012-03-01 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.Summer1.kuali.milestone.type.group.registration', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Summer1', 'kuali.milestone.type.group.registration', 0, 0, TIMESTAMP '2012-03-01 00:00:00', 'UMDDATA', null, null);
/

-- Add type-type relation for Summer 2
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.Summer2.kuali.atp.duration.Term', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Summer2', 'kuali.atp.duration.Term', 0, 0, TIMESTAMP '2012-09-12 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.group.term.kuali.atp.type.Summer2', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.group.term', 'kuali.atp.type.Summer2', 2, 0, TIMESTAMP '2012-03-01 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.AcademicCalendar.kuali.atp.type.Summer2', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.AcademicCalendar', 'kuali.atp.type.Summer2', 2, 0, TIMESTAMP '2012-03-01 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.Summer2.kuali.milestone.type.group.instructional', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Summer2', 'kuali.milestone.type.group.instructional', 1, 0, TIMESTAMP '2012-03-01 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.allowed.kuali.atp.type.Summer2.kuali.milestone.type.group.registration', null, 'kuali.type.type.relation.type.allowed', 'kuali.type.type.relation.state.active', null, null, 'kuali.atp.type.Summer2', 'kuali.milestone.type.group.registration', 0, 0, TIMESTAMP '2012-03-01 00:00:00', 'UMDDATA', null, null);
/

-- Remove the season type relations
delete from KSEN_TYPETYPE_RELTN where ID='kuali.type.type.relation.type.group.kuali.atp.type.Spring.kuali.atp.season.Spring';
/
delete from KSEN_TYPETYPE_RELTN where ID='kuali.type.type.relation.type.group.kuali.atp.type.Summer.kuali.atp.season.Summer';
/
delete from KSEN_TYPETYPE_RELTN where ID='kuali.type.type.relation.type.group.kuali.atp.type.Winter.kuali.atp.season.Winter';
/
delete from KSEN_TYPETYPE_RELTN where ID='kuali.type.type.relation.type.group.kuali.atp.type.Fall.kuali.atp.season.Fall';
/

-- Delete type-type for relation between ATP type and duration type
delete from KSEN_TYPETYPE_RELTN where ID='kuali.type.type.relation.type.group.kuali.atp.type.Fall.kuali.atp.duration.Semester';
/
delete from KSEN_TYPETYPE_RELTN where ID='kuali.type.type.relation.type.group.kuali.atp.type.Spring.kuali.atp.duration.Semester';
/
delete from KSEN_TYPETYPE_RELTN where ID='kuali.type.type.relation.type.group.kuali.atp.type.Winter.kuali.atp.duration.Period';
/
delete from KSEN_TYPETYPE_RELTN where ID='kuali.type.type.relation.type.group.kuali.atp.type.Summer.kuali.atp.duration.Term';
/

-- Insert type-type relation between ATP type and duration type
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.Spring.kuali.atp.duration.Term', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', TIMESTAMP '2012-11-01 00:00:00', null, 'kuali.atp.type.Spring', 'kuali.atp.duration.Term', 0, 0, TIMESTAMP '2012-11-09 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.Summer.kuali.atp.duration.Term', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', TIMESTAMP '2012-11-01 00:00:00', null, 'kuali.atp.type.Summer', 'kuali.atp.duration.Term', 0, 0, TIMESTAMP '2012-11-09 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.Fall.kuali.atp.duration.Term', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', TIMESTAMP '2012-11-01 00:00:00', null, 'kuali.atp.type.Fall', 'kuali.atp.duration.Term', 0, 0, TIMESTAMP '2012-11-09 00:00:00', 'UMDDATA', null, null);
/
insert into KSEN_TYPETYPE_RELTN (ID, OBJ_ID, TYPETYPE_RELTN_TYPE, TYPETYPE_RELTN_STATE, EFF_DT, EXPIR_DT, OWNER_TYPE_ID, RELATED_TYPE_ID, RANK, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.type.type.relation.type.group.kuali.atp.type.Winter.kuali.atp.duration.Term', null, 'kuali.type.type.relation.type.group', 'kuali.type.type.relation.state.active', TIMESTAMP '2012-11-01 00:00:00', null, 'kuali.atp.type.Winter', 'kuali.atp.duration.Term', 0, 0, TIMESTAMP '2012-11-09 00:00:00', 'UMDDATA', null, null);
/
