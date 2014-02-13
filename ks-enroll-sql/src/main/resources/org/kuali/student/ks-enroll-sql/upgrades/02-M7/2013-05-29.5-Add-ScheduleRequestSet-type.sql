--Configure ScheduleRequestSet types
insert into KSEN_TYPE (TYPE_KEY, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, EFF_DT, EXPIR_DT, REF_OBJECT_URI, SERVICE_URI, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID)
values ('kuali.scheduling.schedule.request.set.type.schedule.request.set', SYS_GUID(), 'Schedule Request Set', 'Set of Schedule Requests that are colocated', 'Set of Schedule Requests that are colocated',
null, null, 'http://student.kuali.org/wsdl/scheduling/ScheduleRequestSetInfo', 'http://student.kuali.org/wsdl/scheduling/SchedulingService', 0, TIMESTAMP '2013-05-07 00:00:00', 'SYSTEMLOADER', null, null)
/