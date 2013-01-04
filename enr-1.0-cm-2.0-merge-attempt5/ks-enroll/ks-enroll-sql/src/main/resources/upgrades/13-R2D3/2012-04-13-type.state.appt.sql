
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

