--KSENROLL-14859 Adding some test users and appointments

insert into KSEN_APPT_WINDOW (APPT_WINDOW_STATE, APPT_WINDOW_TYPE, ASSIGNED_ORDER_TYPE, ASSIGNED_POPULATION_ID, CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, END_DT, ID, MAX_APPT_PER_SLOT, NAME, OBJ_ID, PRD_MSTONE_ID, SR_DUR_TIME_QTY, SR_DUR_TYPE, SR_END_TIME_MS, SR_START_INTVL_DUR_TYPE, SR_START_INTVL_TIME_QTY, SR_START_TIME_MS, SR_WEEKDAYS, START_DT, UPDATEID, UPDATETIME, VER_NBR)
  values ('kuali.appointment.window.state.assigned', 'kuali.appointment.window.type.slotted.uniform', 'DUMMY_ID', 'kuali.population.student.key.regdatecheck', 'admin', TIMESTAMP '2014-09-15 10:18:38', null, null, TIMESTAMP '2012-04-14 00:11:00', '917abaed-b1ba-4867-8269-e995ffd2ce5d', null, 'Window1', '405674a5-f46e-42d0-bfd6-d32f370a18b4', '031F39F1-883D-13BB-E050-007F010115A6', 0, 'kuali.atp.duration.Minutes', 57600000, 'kuali.atp.duration.Minutes', 15, 28800000, '2,4,6', TIMESTAMP '2012-03-14 00:11:00', 'admin', TIMESTAMP '2014-09-15 10:18:50', 2)
/

insert into KSEN_APPT_SLOT (APPT_SLOT_STATE, APPT_SLOT_TYPE, APPT_WINDOW_ID, CREATEID, CREATETIME, END_DT, ID, OBJ_ID, START_DT, UPDATEID, UPDATETIME, VER_NBR)
  values ('kuali.appointment.slots.state.active', 'kuali.appointment.window.type.slotted.uniform', '917abaed-b1ba-4867-8269-e995ffd2ce5d', 'admin', TIMESTAMP '2014-09-15 10:18:50', null, 'a8928b0a-0eb8-4717-a681-b3cdd93f7298', 'b40ff93b-744f-4f69-bde2-4e844ddc4db2', TIMESTAMP '2012-03-14 15:15:00', 'admin', TIMESTAMP '2014-09-15 10:18:50', 0)
/
insert into KSEN_APPT_SLOT (APPT_SLOT_STATE, APPT_SLOT_TYPE, APPT_WINDOW_ID, CREATEID, CREATETIME, END_DT, ID, OBJ_ID, START_DT, UPDATEID, UPDATETIME, VER_NBR)
  values ('kuali.appointment.slots.state.active', 'kuali.appointment.window.type.slotted.uniform', '917abaed-b1ba-4867-8269-e995ffd2ce5d', 'admin', TIMESTAMP '2014-09-15 10:18:50', null, 'b2f8239a-5e34-4888-938f-058d8fb51480', '24e64b97-277e-4d72-96c2-bd365ce33ea6', TIMESTAMP '2012-04-14 15:30:00', 'admin', TIMESTAMP '2014-09-15 10:18:50', 0)
/
insert into KSEN_APPT_SLOT (APPT_SLOT_STATE, APPT_SLOT_TYPE, APPT_WINDOW_ID, CREATEID, CREATETIME, END_DT, ID, OBJ_ID, START_DT, UPDATEID, UPDATETIME, VER_NBR)
  values ('kuali.appointment.slots.state.active', 'kuali.appointment.window.type.slotted.uniform', '917abaed-b1ba-4867-8269-e995ffd2ce5d', 'admin', TIMESTAMP '2014-09-15 10:18:50', null, '8bf0cbc4-6c32-40f3-96d7-a19698ce504e', '548a87a7-241a-4418-9d03-3ca8988dc80e', TIMESTAMP '2012-05-04 15:45:00', 'admin', TIMESTAMP '2014-09-15 10:18:50', 0)
/

insert into KSEN_APPT (APPT_STATE, APPT_TYPE, CREATEID, CREATETIME, EFF_DT, EXPIR_DT, ID, OBJ_ID, PERS_ID, SLOT_ID, UPDATEID, UPDATETIME, VER_NBR)
  values ('kuali.appointment.state.active', 'kuali.appointment.type.registration', 'admin', TIMESTAMP '2014-09-15 10:18:57', null, null, 'f553648a-87e2-4d0a-b061-1b914ae812f8', 'e7266289-2efa-4127-8d52-bea0e665af9a', 'KS-11755', 'a8928b0a-0eb8-4717-a681-b3cdd93f7298', 'admin', TIMESTAMP '2014-09-15 10:18:57', 0)
/
insert into KSEN_APPT (APPT_STATE, APPT_TYPE, CREATEID, CREATETIME, EFF_DT, EXPIR_DT, ID, OBJ_ID, PERS_ID, SLOT_ID, UPDATEID, UPDATETIME, VER_NBR)
  values ('kuali.appointment.state.active', 'kuali.appointment.type.registration', 'admin', TIMESTAMP '2014-09-15 10:18:57', null, null, 'd0543815-c3ba-4b99-be9d-7dbfabb78aa0', '077b919b-9aec-4779-b887-e38c23e601e0', 'KS-3986', 'b2f8239a-5e34-4888-938f-058d8fb51480', 'admin', TIMESTAMP '2014-09-15 10:18:57', 0)
/
insert into KSEN_APPT (APPT_STATE, APPT_TYPE, CREATEID, CREATETIME, EFF_DT, EXPIR_DT, ID, OBJ_ID, PERS_ID, SLOT_ID, UPDATEID, UPDATETIME, VER_NBR)
  values ('kuali.appointment.state.active', 'kuali.appointment.type.registration', 'admin', TIMESTAMP '2014-09-15 10:18:57', null, null, '9007e3f1-8aea-40e4-a9b1-d23add0c8025', '71491796-eba5-4945-af94-71b7cff440dd', 'KS-4603', '8bf0cbc4-6c32-40f3-96d7-a19698ce504e', 'admin', TIMESTAMP '2014-09-15 10:18:57', 0)
/
