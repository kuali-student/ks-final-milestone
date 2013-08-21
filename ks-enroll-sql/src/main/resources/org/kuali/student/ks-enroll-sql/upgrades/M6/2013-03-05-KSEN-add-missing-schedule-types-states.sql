--Configure scheduling types (KSENROLL-5788)
insert into KSEN_TYPE (TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.scheduling.schedule.transaction.type.request', SYS_GUID(), 'Request Transaction', 'Normal Request', 'Normal Request', null, null, 'http://student.kuali.org/wsdl/scheduling/ScheduleTransactionInfo', 'http://student.kuali.org/wsdl/scheduling/SchedulingService', 0, TIMESTAMP '2012-11-02 00:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPE (TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.scheduling.schedule.batch.type.schedule.batch', SYS_GUID(), 'Batch', 'Batch', 'Batch', null, null, 'http://student.kuali.org/wsdl/scheduling/ScheduleBatchInfo', 'http://student.kuali.org/wsdl/scheduling/SchedulingService', 0, TIMESTAMP '2012-11-02 00:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_TYPE (TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.scheduling.schedule.type.schedule', SYS_GUID(), 'Schedule', 'Normal Schedule', 'Normal Schedule', null, null, 'http://student.kuali.org/wsdl/scheduling/ScheduleInfo', 'http://student.kuali.org/wsdl/scheduling/SchedulingService', 0, TIMESTAMP '2012-11-02 00:00:00', 'SYSTEMLOADER', null, null)
/

--Configure missing states
insert into KSEN_STATE_LIFECYCLE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, REF_OBJECT_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.scheduling.schedule.transaction.lifecycle', null, 'kuali.scheduling.schedule.transaction.lifecycle', 'kuali.scheduling.schedule.transaction.lifecycle state lifecycle', 'kuali.scheduling.schedule.transaction.lifecycle state lifecycle', null, 1, TIMESTAMP '2012-07-29 20:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.scheduling.schedule.transaction.state.completed', null, 'Completed', 'The transaction completed successfully', 'The transaction completed successfully', 'kuali.scheduling.schedule.transaction.lifecycle', null, null, 0, TIMESTAMP '2012-11-01 20:00:00', 'SYSTEMLOADER', null, null)
/

insert into KSEN_STATE_LIFECYCLE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, REF_OBJECT_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.scheduling.schedulebatch.lifecycle', null, 'kuali.scheduling.schedulebatch.lifecycle', 'kuali.scheduling.schedulebatch.lifecycle state lifecycle', 'kuali.scheduling.schedulebatch.lifecycle state lifecycle', null, 1, TIMESTAMP '2012-07-29 20:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.scheduling.schedulebatch.state.completed', null, 'Completed', 'The batch completed successfully', 'The batch completed successfully', 'kuali.scheduling.schedulebatch.lifecycle', null, null, 0, TIMESTAMP '2012-11-01 20:00:00', 'SYSTEMLOADER', null, null)
/

insert into KSEN_STATE_LIFECYCLE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, REF_OBJECT_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.scheduling.schedule.lifecycle', null, 'kuali.scheduling.schedule.lifecycle', 'kuali.scheduling.schedule.lifecycle state lifecycle', 'kuali.scheduling.schedule.lifecycle state lifecycle', null, 1, TIMESTAMP '2012-07-29 20:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.scheduling.schedule.state.active', null, 'Active', 'The schedule is active', 'The schedule is active', 'kuali.scheduling.schedule.lifecycle', null, null, 0, TIMESTAMP '2012-11-01 20:00:00', 'SYSTEMLOADER', null, null)
/

insert into KSEN_STATE_LIFECYCLE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, REF_OBJECT_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.scheduling.timeslot.lifecycle', null, 'kuali.scheduling.timeslot.lifecycle', 'kuali.scheduling.timeslot.lifecycle state lifecycle', 'kuali.scheduling.timeslot.lifecycle state lifecycle', null, 1, TIMESTAMP '2012-07-29 20:00:00', 'SYSTEMLOADER', null, null)
/
insert into KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.scheduling.timeslot.state.active', null, 'Active', 'The time slot is active', 'The time slot is active', 'kuali.scheduling.timeslot.lifecycle', null, null, 0, TIMESTAMP '2012-11-01 20:00:00', 'SYSTEMLOADER', null, null)
/

