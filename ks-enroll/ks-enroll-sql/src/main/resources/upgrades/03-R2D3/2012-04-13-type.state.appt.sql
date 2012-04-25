
-----------------------------------------------
--- NEW APPT TYPES
-----------------------------------------------

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.appointment.window.type.slotted.uniform', null, 'Uniform Slotted Window','A window that specifies interval and end date (but not max_appts_per_slot) to create appointment slots that will be assigned appointments as uniformly as possible','A window that specifies interval and end date (but not max_appts_per_slot) to create appointment slots that will be assigned appointments as uniformly as possible', null, null, 'http://student.kuali.org/wsdl/appointment/AppointmentWindowInfo', 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.appointment.window.type.slotted.max', null, 'Max Slotted Window','A window that specifies interval and max_appts_per_slot to create appointment slots that will be assigned at most max_appts_per_slot. Specifying end date is optional (if not long enough, will get "not enough room" error.)','A window that specifies interval and max_appts_per_slot to create appointment slots that will be assigned at most max_appts_per_slot. Specifying end date is optional (if not long enough, will get "not enough room" error.)', null, null, 'http://student.kuali.org/wsdl/appointment/AppointmentWindowInfo', 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.appointment.window.type.one.slot', null, 'One Slot per Window','A window that does not specify rules but has one slot that is co-extensive with the window','A window that does not specify rules but has one slot that is co-extensive with the window', null, null, 'http://student.kuali.org/wsdl/appointment/AppointmentWindowInfo', 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.appointment.slot.type.closed', null, 'Closed slot','An appointment slot that specifies both the start and end times','An appointment slot that specifies both the start and end times', null, null, 'http://student.kuali.org/wsdl/appointment/AppointmentWindowInfo', 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_TYPE (TYPE_KEY,OBJ_ID,NAME,DESCR_PLAIN,DESCR_FORMATTED,EFF_DT,EXPIR_DT,REF_OBJECT_URI,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.appointment.type.registration', null, 'Registration appointment','An appointment for registration','An appointment for registration', null, null, 'http://student.kuali.org/wsdl/appointment/AppointmentWindowInfo', 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/


-----------------------------------------------
--- NEW APPT STATE LIFECYCLES
-----------------------------------------------

INSERT INTO KSEN_STATE_LIFECYCLE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) VALUES ('kuali.appointment.window.lifecycle', null, 'kuali.appointment.window.lifecycle','kuali.appointment.window.lifecycle state lifecycle','kuali.appointment.window.lifecycle state lifecycle', 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_STATE_LIFECYCLE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) VALUES ('kuali.appointment.slot.lifecycle', null, 'kuali.appointment.slot.lifecycle','kuali.appointment.slot.lifecycle state lifecycle','kuali.appointment.slot.lifecycle state lifecycle', 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_STATE_LIFECYCLE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) VALUES ('kuali.appointment.lifecycle', null, 'kuali.appointment.lifecycle','kuali.appointment.lifecycle state lifecycle','kuali.appointment.lifecycle state lifecycle', 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/


-----------------------------------------------
--- NEW APPT STATES
-----------------------------------------------

INSERT INTO KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) VALUES ('kuali.appointment.window.state.draft', null, 'Draft','Window has been created','Window has been created', 'kuali.appointment.window.lifecycle', null, null, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) VALUES ('kuali.appointment.window.state.assigned', null, 'Assigned','Students have been assigned to slots','Students have been assigned to slots', 'kuali.appointment.window.lifecycle', null, null, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) VALUES ('kuali.appointment.slot.state.active', null, 'Active','Slot is Active','Slot is Active', 'kuali.appointment.slot.lifecycle', null, null, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

INSERT INTO KSEN_STATE (ID, OBJ_ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, LIFECYCLE_KEY, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) VALUES ('kuali.appointment.state.active', null, 'Active','Appointment is Active','Appointment is Active', 'kuali.appointment.lifecycle', null, null, 0, to_date('2012-03-01', 'YYYY-MM_DD'), 'SYSTEMLOADER', null,null)
/

---------------------------------------------
 --- APPT. RULE TYPES
---------------------------------------------


delete from KSEM_ENUM_VAL_T where obj_id = '04112012221'
/
delete from KSEM_ENUM_VAL_T where obj_id = '04112012222'
/
delete from KSEM_ENUM_VAL_T where obj_id = '04112012223'
/
delete from KSEM_ENUM_VAL_T where obj_id = '04112012224'
/
delete from ksem_enum_t where obj_id = 'enroll'
/

insert into KSEM_ENUM_T (ENUM_KEY, DESCR, EFF_DT, EXPIR_DT, NAME, OBJ_ID, VER_NBR)
values ('kuali.enum.type.slotrule', 'Slot Code', sysdate, null, 'Slot Codes', 'enroll', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('04112012221', 'Undergrad Standard', '05', sysdate, null, 1, '2,4,6;480;960;kuali.atp.duration.Minutes;15;kuali.atp.duration.Minutes;0', 'kuali.enum.type.slotrule', '04112012221', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('04112012222', 'Undergrad Special', '06', sysdate, null, 2, '3,5;540;1020;kuali.atp.duration.Minutes;30;kuali.atp.duration.Minutes;0', 'kuali.enum.type.slotrule', '04112012222', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('04112012223', 'AdvancedRegistration', '07', sysdate, null, 1, '2,3,6;600;1080;kuali.atp.duration.Minutes;45;kuali.atp.duration.Minutes;0', 'kuali.enum.type.slotrule', '04112012223', 1)
/
insert into KSEM_ENUM_VAL_T (ID, ABBREV_VAL, CD, EFF_DT, EXPIR_DT, SORT_KEY, VAL, ENUM_KEY, OBJ_ID, VER_NBR)
values ('04112012224', 'Graduate/Profession', '08', sysdate, null, 1, '2,3,4,5;480;1020;kuali.atp.duration.Minutes;20;kuali.atp.duration.Minutes;0', 'kuali.enum.type.slotrule', '04112012224', 1)
/
