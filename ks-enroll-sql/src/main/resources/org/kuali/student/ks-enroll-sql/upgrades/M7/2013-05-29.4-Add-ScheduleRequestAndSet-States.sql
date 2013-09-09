--Configure ScheduleRequestSet state
insert into KSEN_STATE_LIFECYCLE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, REF_OBJECT_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID)
values ('kuali.scheduling.schedule.request.set.lifecycle', null, 'kuali.scheduling.schedule.request.set.lifecycle', 'kuali.scheduling.schedule.request.set.lifecycle state lifecycle', 'kuali.scheduling.schedule.request.set.lifecycle state lifecycle', null, 1, TIMESTAMP '2013-05-07 20:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_STATE (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID, OBJ_ID)
values ('kuali.scheduling.schedule.request.set.state.created','Created','The set has been created', 'The set has been created', 'kuali.scheduling.schedule.request.set.lifecycle', null, null, 0, TIMESTAMP '2013-05-07 20:00:00', 'SYSTEMLOADER', null, null,null)
/
insert into KSEN_STATE (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID, OBJ_ID)
values ('kuali.scheduling.schedule.request.set.state.modified','Modified', 'The set has been modified and scheduling has not been re-initiated','The set has been modified and scheduling has not been re-initiated', 'kuali.scheduling.schedule.request.set.lifecycle', null, null, 0, TIMESTAMP '2013-05-07 20:00:00', 'SYSTEMLOADER', null, null,null)
/
insert into KSEN_STATE (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID, OBJ_ID)
values ('kuali.scheduling.schedule.request.set.state.requested','Requested','The associated schedule requests are in requested states and sent to the scheduler','The associated schedule requests are in requested states and sent to the scheduler', 'kuali.scheduling.schedule.request.set.lifecycle', null, null, 0, TIMESTAMP '2013-05-07 20:00:00', 'SYSTEMLOADER', null, null,null)
/
insert into KSEN_STATE (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID, OBJ_ID)
values ('kuali.scheduling.schedule.request.set.state.partial','Partial', 'At least one associated schedule request has been processed and one or more are outstanding','At least one associated schedule request has been processed and one or more are outstanding', 'kuali.scheduling.schedule.request.set.lifecycle', null, null, 0, TIMESTAMP '2013-05-07 20:00:00', 'SYSTEMLOADER', null, null,null)
/
insert into KSEN_STATE (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID, OBJ_ID)
values ('kuali.scheduling.schedule.request.set.state.processed','Processed','The associated schedule requests have all been processed','The associated schedule requests have all been processed', 'kuali.scheduling.schedule.request.set.lifecycle', null, null, 0, TIMESTAMP '2013-05-07 20:00:00', 'SYSTEMLOADER', null, null,null)
/
insert into KSEN_STATE (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID, OBJ_ID)
values ('kuali.scheduling.schedule.request.set.state.error','Error','One or more of the associated schedule requests are in error','One or more of the associated schedule requests are in error', 'kuali.scheduling.schedule.request.set.lifecycle', null, null, 0, TIMESTAMP '2013-05-07 20:00:00', 'SYSTEMLOADER', null, null,null)
/

--Configure ScheduleRequest state
insert into KSEN_STATE (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID, OBJ_ID)
values ('kuali.scheduling.schedule.request.state.modified','Modified','The request has been modified','The request has been modified', 'kuali.scheduling.schedule.request.lifecycle', null, null, 0, TIMESTAMP '2013-05-07 20:00:00', 'SYSTEMLOADER', null, null,null)
/
insert into KSEN_STATE (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID, OBJ_ID)
values ('kuali.scheduling.schedule.request.state.requested','Requested','The request has been made and sent to the scheduler','The request has been made and sent to the scheduler', 'kuali.scheduling.schedule.request.lifecycle', null, null, 0, TIMESTAMP '2013-05-07 20:00:00', 'SYSTEMLOADER', null, null,null)
/
insert into KSEN_STATE (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID, OBJ_ID)
values ('kuali.scheduling.schedule.request.state.processed','Processed','The associated schedule requests have all been processed','The associated schedule requests have all been processed', 'kuali.scheduling.schedule.request.lifecycle', null, null, 0, TIMESTAMP '2013-05-07 20:00:00', 'SYSTEMLOADER', null, null,null)
/
insert into KSEN_STATE (ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID, OBJ_ID)
values ('kuali.scheduling.schedule.request.state.error','Error','One or more of the associated schedule requests are in error','One or more of the associated schedule requests are in error', 'kuali.scheduling.schedule.request.lifecycle', null, null, 0, TIMESTAMP '2013-05-07 20:00:00', 'SYSTEMLOADER', null, null,null)
/
