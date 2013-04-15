-- Room types
insert into KSEN_TYPE (TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.room.type.classroom', null, 'Classroom', 'Classroom (Room Type)', 'Classroom (Room Type)', null, null, 'http://student.kuali.org/wsdl/room/RoomInfo', 'http://student.kuali.org/wsdl/room/RoomService', 0, to_date('2012-12-11', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPE (TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.room.type.classroom.general', null, 'General Classroom', 'General Classroom (Room Type)', 'General Classroom (Room Type)', null, null, 'http://student.kuali.org/wsdl/room/RoomInfo', 'http://student.kuali.org/wsdl/room/RoomService', 0, to_date('2012-12-11', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
-- Org Room Responsibility types
insert into KSEN_TYPE (TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.room.org.responsibility.scheduling', null, 'Scheduling', 'Scheduling (Org Room Responsibility Type)', 'Scheduling (Org Room Responsibility Type)', null, null, 'http://student.kuali.org/wsdl/room/RoomInfo', 'http://student.kuali.org/wsdl/room/RoomService', 0, to_date('2012-12-11', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
-- Facility types
insert into KSEN_TYPE (TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.room.facility.type.building', null, 'Building', 'Building (Facility Type)', 'Building (Facility Type)', null, null, 'http://student.kuali.org/wsdl/room/RoomInfo', 'http://student.kuali.org/wsdl/room/RoomService', 0, to_date('2012-12-11', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPE (TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.room.facility.type.region', null, 'Region', 'Region (Facility Type)', 'Building (Facility Type)', null, null, 'http://student.kuali.org/wsdl/room/RoomInfo', 'http://student.kuali.org/wsdl/room/RoomService', 0, to_date('2012-12-11', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPE (TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.room.facility.type.partition', null, 'Partition', 'Partition (Facility Type)', 'Partition (Facility Type))', null, null, 'http://student.kuali.org/wsdl/room/RoomInfo', 'http://student.kuali.org/wsdl/room/RoomService', 0, to_date('2012-12-11', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
