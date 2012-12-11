-- Building lifecycle
insert into KSEN_STATE_LIFECYCLE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, REF_OBJECT_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.room.building.lifecycle', null, 'Lifecycle for building', 'Lifecycle for building', 'Lifecycle for building', null, 1, to_date('2012-12-15', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
-- Building states
insert into KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.room.building.state.active', null, 'Online', 'Building is online and available for business processes', 'Building is online and available for business processes', 'kuali.room.building.lifecycle', null, null, 0, to_date('2012-12-11', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
insert into KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.room.building.state.inactive', null, 'Offline', 'Building is offline and not available for use', 'Building is offline and not available for use', 'kuali.room.building.lifecycle', null, null, 0, to_date('2012-12-11', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
-- Room lifecycle
insert into KSEN_STATE_LIFECYCLE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, REF_OBJECT_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.room.room.lifecycle', null, 'Lifecycle for room', 'Lifecycle for room', 'Lifecycle for room', null, 1, to_date('2012-12-15', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
-- Room states
insert into KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.room.room.state.active', null, 'Online', 'The room is online and available for business processes', 'The room is online and available for business processes', 'kuali.room.room.lifecycle', null, null, 0, to_date('2012-12-11', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
insert into KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.room.room.state.inactive', null, 'Offline', 'The room is offline and unavailable for use', 'The room is offline and unavailable for use', 'kuali.room.room.lifecycle', null, null, 0, to_date('2012-12-11', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
-- Facility lifecycle
insert into KSEN_STATE_LIFECYCLE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, REF_OBJECT_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.room.facility.lifecycle', null, 'Lifecycle for facility', 'Lifecycle for facility', 'Lifecycle for facility', null, 1, to_date('2012-12-15', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
-- Facility states
insert into KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.room.facility.state.online', null, 'Online', 'The facility is online and consumable by business processes', 'The facility is online and consumable by business processes', 'kuali.room.facility.lifecycle', null, null, 0, to_date('2012-12-11', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/
insert into KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.room.facility.state.offline', null, 'Offline', 'The facility is offline and not available for use', 'The facility is offline and not available for use', 'kuali.room.facility.lifecycle', null, null, 0, to_date('2012-12-11', 'YYYY-MM-DD'), 'SYSTEMLOADER', null, null)
/