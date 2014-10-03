-- KSENROLL-13390
-- Half Fall 1 2012, CHEM105
-- AO A
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('78498d0e-a0bb-4bad-9e69-6eca87301f8f', 'kuali.attribute.nonstd.ts.indicator', 'a2596117-c310-4d3d-9ab0-0363715df307', '0', 'ef7e4e89-e943-4a1e-9cb0-a57c877f4509')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('3a834926-8d54-4d15-bf88-5271e8e5a6f4', 0, 'admin', TIMESTAMP '2014-07-02 11:24:35.74', 'admin', TIMESTAMP '2014-07-02 11:24:35.74', 'kuali.atp.2012HalfFall1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '74b8ee20-3bd0-465b-bc34-e35ebb440bc3')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('17aa6bf0-c8c2-44fd-b322-018c27149cc3', '0', '2246d609-9fbb-4d06-b45e-a57ef80539b7', '74b8ee20-3bd0-465b-bc34-e35ebb440bc3', '47e1fa34-4fd6-4cef-9773-8c98134aea31')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('47e1fa34-4fd6-4cef-9773-8c98134aea31', '98C64E80-5AE3-4ACC-9DB0-2DBE170E7CA4')
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('a2596117-c310-4d3d-9ab0-0363715df307', '74b8ee20-3bd0-465b-bc34-e35ebb440bc3')
/
update KSEN_LUI set OBJ_ID='f2763733-2fc2-4eeb-a2df-ca17a5645a24', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-02 14:44:25.484', ATP_ID='kuali.atp.2012Fall', CLU_ID='fbe00f3e-788a-460c-b686-3303215621fa', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='ea3ac9da-38aa-41f0-8d13-6cccfa6bae75' and VER_NBR=0
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('462ee469-077c-43d5-98c6-87df3289eaba', 0, 'admin', TIMESTAMP '2014-07-02 11:24:35.74', 'admin', TIMESTAMP '2014-07-02 11:24:35.74', TIMESTAMP '2014-07-02 11:24:36.658', '', '41912266-a903-49c9-b167-725c778adc9f-FO-EO)', '653a8a06-82f8-4d24-8727-74d47ba9b47f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', '41912266-a903-49c9-b167-725c778adc9f-FO-EO', '41912266-a903-49c9-b167-725c778adc9f', '083fbfe4-6402-4cea-807d-a6291a2a473f')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0db9f28a-0e9f-493a-8bb1-b12ed90f0745', 'AO1', '083fbfe4-6402-4cea-807d-a6291a2a473f', 'a2596117-c310-4d3d-9ab0-0363715df307', '75692c61-9f7f-4bc7-b4f6-32eb7b34193a')
/
-- AO B
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('894cc9bf-d542-4021-abeb-8304239ff55d', 'kuali.attribute.nonstd.ts.indicator', 'a03df821-38bc-4fc4-b34a-77a92405b87f', '0', '180e5d0f-a60a-4636-ab1c-6765d032e5b6')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('860f5a6f-0013-410c-93e0-a8eb56855587', 0, 'admin', TIMESTAMP '2014-07-02 14:52:20.574', 'admin', TIMESTAMP '2014-07-02 14:52:20.574', 'kuali.atp.2012HalfFall1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'e98934b1-4000-4f3b-adcd-af78e1234c1d')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('5b6329ac-6a1e-4d9e-bf87-be55a3c8cc6d', '0', '2246d609-9fbb-4d06-b45e-a57ef80539b7', 'e98934b1-4000-4f3b-adcd-af78e1234c1d', '213a9cb4-fe0e-4757-8c83-1346d9c685b7')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('213a9cb4-fe0e-4757-8c83-1346d9c685b7', 'AEC6E52D-8F4A-4E65-9084-B4305B1CC08B')
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('a03df821-38bc-4fc4-b34a-77a92405b87f', 'e98934b1-4000-4f3b-adcd-af78e1234c1d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('cf8413e7-bb99-4a77-bc88-9a93ad25eae7', 0, 'admin', TIMESTAMP '2014-07-02 14:52:20.574', 'admin', TIMESTAMP '2014-07-02 14:52:20.574', TIMESTAMP '2014-07-02 14:52:21.019', '', '41912266-a903-49c9-b167-725c778adc9f-FO-EO)', '653a8a06-82f8-4d24-8727-74d47ba9b47f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', '41912266-a903-49c9-b167-725c778adc9f-FO-EO', '41912266-a903-49c9-b167-725c778adc9f', '6c6afef0-7aea-4aea-8604-b11abd5ec8e8')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3ca4d779-2395-4797-9fd3-a81772ee0581', 'AO1', '6c6afef0-7aea-4aea-8604-b11abd5ec8e8', 'a03df821-38bc-4fc4-b34a-77a92405b87f', '2c7932a0-c90c-44e2-95e6-d629f1dbf089')
/

-- Half Fall 1 2012, HIST605
-- AO A
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f264ab99-6771-4f20-a0b4-cdb2282d2542', 'kuali.attribute.nonstd.ts.indicator', '95ef4b80-f3d0-498f-b094-c643c5295106', '0', '31c753a3-6065-46a9-984e-2a783245bbce')
/
update KSEN_LUI set OBJ_ID='1e499f1b-f807-42ff-91df-d82b90b01930', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-08 07:26:16.444', ATP_ID='kuali.atp.2012HalfFall1', CLU_ID='fd2a8a79-418f-44f2-8d75-9ea7e763c878', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=20, MIN_SEATS='', NAME='HIST605 AO', DESCR_PLAIN='', REF_URL='' where ID='95ef4b80-f3d0-498f-b094-c643c5295106' and VER_NBR=0
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('ced80de0-312a-43b0-b32c-bd1ed2c467bd', 0, 'admin', TIMESTAMP '2014-07-08 07:26:16.444', 'admin', TIMESTAMP '2014-07-08 07:26:16.444', '', '', '', 'Schedule request set for HIST605 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '09aa8f21-d91d-4509-9ee2-1c15ace08b50')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('09aa8f21-d91d-4509-9ee2-1c15ace08b50', '95ef4b80-f3d0-498f-b094-c643c5295106')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('bc4a52ca-d4c0-48ca-a22a-4be65d9cb19d', 0, 'admin', TIMESTAMP '2014-07-08 07:26:16.444', 'admin', TIMESTAMP '2014-07-08 07:26:16.444', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '09aa8f21-d91d-4509-9ee2-1c15ace08b50', '136d4959-6edd-443c-a1a4-afe4b6764741')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('dfb9cd09-4a30-4ca7-b1d0-4bc68e841840', '1', '136d4959-6edd-443c-a1a4-afe4b6764741', 'd4f105a8-219d-44b0-ad5b-b5fc4b8b441d')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('d4f105a8-219d-44b0-ad5b-b5fc4b8b441d', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('278d43aa-7ed5-4f09-ba87-611af5e023a2', 0, 'admin', TIMESTAMP '2014-07-08 07:26:16.444', 'admin', TIMESTAMP '2014-07-08 07:26:16.444', 'kuali.atp.2012HalfFall1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '0f421829-8ec5-48f8-a41e-8e4f6e363f20')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('c565c941-486d-45ba-aad9-e81b6f68cded', '1', '', '0f421829-8ec5-48f8-a41e-8e4f6e363f20', '6979e1f2-972f-4ad5-a1a6-c94080dd38b7')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('6979e1f2-972f-4ad5-a1a6-c94080dd38b7', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='1', SCHED_RQST_ID='136d4959-6edd-443c-a1a4-afe4b6764741' where ID='d4f105a8-219d-44b0-ad5b-b5fc4b8b441d'
/
update KSEN_SCHED_RQST set OBJ_ID='bc4a52ca-d4c0-48ca-a22a-4be65d9cb19d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-08 07:26:16.444', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='0f421829-8ec5-48f8-a41e-8e4f6e363f20', SCHED_RQST_SET_ID='09aa8f21-d91d-4509-9ee2-1c15ace08b50' where ID='136d4959-6edd-443c-a1a4-afe4b6764741' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='d4f105a8-219d-44b0-ad5b-b5fc4b8b441d'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('d4f105a8-219d-44b0-ad5b-b5fc4b8b441d', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('95ef4b80-f3d0-498f-b094-c643c5295106', '0f421829-8ec5-48f8-a41e-8e4f6e363f20')
/
update KSEN_LUI set OBJ_ID='709ef703-ee8e-4ff6-92da-465e390dac9f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-08 07:26:16.444', ATP_ID='kuali.atp.2012Fall', CLU_ID='08867ede-c389-4a85-9685-857d7495904a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='6fb15766-da1c-43a5-87a0-9e494d8cc914' and VER_NBR=0
/
delete from KSEN_SCHED_RQST where ID='12a2de6f-80be-4cee-b19d-40fcfa4af683' and VER_NBR=0
/
delete from KSEN_SCHED_REF_OBJECT where SCHED_RQST_SET_ID='264c28a3-3029-463e-9f40-e781de0ebdb3'
/
delete from KSEN_SCHED_RQST_SET where ID='264c28a3-3029-463e-9f40-e781de0ebdb3' and VER_NBR=0
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('b175c4bd-7744-4825-8458-7c7976eceb4e', 0, 'admin', TIMESTAMP '2014-07-08 07:26:16.444', 'admin', TIMESTAMP '2014-07-08 07:26:16.444', '', '', '', 'Exam Schedule request set', '', 'http://student.kuali.org/wsdl/examOffering/ExamOfferingInfo', 'kuali.scheduling.schedule.request.set.state.error', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '054c15f9-3422-4384-aead-db6d336d339a')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('054c15f9-3422-4384-aead-db6d336d339a', '60941a9e-f051-4238-bf66-79955300645a')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('e5411b5a-9fc9-48e9-a701-93472e83d627', 0, 'admin', TIMESTAMP '2014-07-08 07:26:16.444', 'admin', TIMESTAMP '2014-07-08 07:26:16.444', '', '', '', 'kuali.scheduling.schedule.request.state.error', 'kuali.scheduling.schedule.request.type.schedule.request', '', '054c15f9-3422-4384-aead-db6d336d339a', '8ae21e6c-bfb4-415a-87a8-afc664cd49e7')
/

-- Half Fall 1 2012, ENGL627
-- AO A
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e86a5448-0c30-4d70-a9a1-f9ae50a8b130', 'kuali.attribute.nonstd.ts.indicator', 'cc5e8eed-9c2d-460a-9c3f-477e529e3da7', '0', 'b1e392d3-e481-45a0-92e7-d44dd414f997')
/
update KSEN_LUI set OBJ_ID='561f8f22-5529-4b0e-ae43-234ca403e827', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-08 07:43:44.079', ATP_ID='kuali.atp.2012HalfFall1', CLU_ID='5f98f34b-6839-4c27-9083-78f7eb85d9c0', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=20, MIN_SEATS='', NAME='ENGL627 AO', DESCR_PLAIN='', REF_URL='' where ID='cc5e8eed-9c2d-460a-9c3f-477e529e3da7' and VER_NBR=0
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('01acd812-1075-41f7-ade6-6d6f0efd6b34', 0, 'admin', TIMESTAMP '2014-07-08 07:43:44.079', 'admin', TIMESTAMP '2014-07-08 07:43:44.079', '', '', '', 'Schedule request set for ENGL627 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'f4c9df93-e6b3-4321-a0e2-33e574fb1595')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('f4c9df93-e6b3-4321-a0e2-33e574fb1595', 'cc5e8eed-9c2d-460a-9c3f-477e529e3da7')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('ed7f1d5b-5d1c-458f-95ab-5587337163ac', 0, 'admin', TIMESTAMP '2014-07-08 07:43:44.079', 'admin', TIMESTAMP '2014-07-08 07:43:44.079', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'f4c9df93-e6b3-4321-a0e2-33e574fb1595', 'b30333ee-a114-4f7c-8080-e6f0423de146')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('5ff28050-78ce-49e2-bd91-84d55570cec6', '1', 'b30333ee-a114-4f7c-8080-e6f0423de146', '08b6388b-bc49-449e-9fa7-1ef6c4d09591')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('08b6388b-bc49-449e-9fa7-1ef6c4d09591', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('c4929a07-ad8f-4e9e-953b-33cdc19afa28', 0, 'admin', TIMESTAMP '2014-07-08 07:43:44.079', 'admin', TIMESTAMP '2014-07-08 07:43:44.079', 'kuali.atp.2012HalfFall1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '3c3fdde6-c8f9-4023-8158-1c230f45c677')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('d13bb937-7510-4ac6-8a1f-470f23ef9c1a', '1', '', '3c3fdde6-c8f9-4023-8158-1c230f45c677', '70cb5eae-a45a-4977-b6ed-a63a0c6eef65')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('70cb5eae-a45a-4977-b6ed-a63a0c6eef65', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='1', SCHED_RQST_ID='b30333ee-a114-4f7c-8080-e6f0423de146' where ID='08b6388b-bc49-449e-9fa7-1ef6c4d09591'
/
update KSEN_SCHED_RQST set OBJ_ID='ed7f1d5b-5d1c-458f-95ab-5587337163ac', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-08 07:43:44.079', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='3c3fdde6-c8f9-4023-8158-1c230f45c677', SCHED_RQST_SET_ID='f4c9df93-e6b3-4321-a0e2-33e574fb1595' where ID='b30333ee-a114-4f7c-8080-e6f0423de146' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='08b6388b-bc49-449e-9fa7-1ef6c4d09591'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('08b6388b-bc49-449e-9fa7-1ef6c4d09591', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('cc5e8eed-9c2d-460a-9c3f-477e529e3da7', '3c3fdde6-c8f9-4023-8158-1c230f45c677')
/
update KSEN_LUI set OBJ_ID='c6d7073a-8d22-4138-aaef-8b6d5de05cc3', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-08 07:43:44.079', ATP_ID='kuali.atp.2012Fall', CLU_ID='b867e7b1-0fbf-48a4-b94d-3fae0a125754', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='b4929b59-32af-40f8-aa69-e2178f2133a0' and VER_NBR=0
/
delete from KSEN_SCHED_RQST where ID='1ccb8701-9db0-44c6-94b6-251adc3a9e53' and VER_NBR=0
/
delete from KSEN_SCHED_REF_OBJECT where SCHED_RQST_SET_ID='04a75b91-1f96-4f26-a5c3-38f932cf659e'
/
delete from KSEN_SCHED_RQST_SET where ID='04a75b91-1f96-4f26-a5c3-38f932cf659e' and VER_NBR=0
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('2aa5dac2-4119-43ae-b7a6-7edc6b8f72c8', 0, 'admin', TIMESTAMP '2014-07-08 07:43:44.079', 'admin', TIMESTAMP '2014-07-08 07:43:44.079', '', '', '', 'Exam Schedule request set', '', 'http://student.kuali.org/wsdl/examOffering/ExamOfferingInfo', 'kuali.scheduling.schedule.request.set.state.error', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'de19b73a-954f-488b-8be7-6993a3be0fa2')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('de19b73a-954f-488b-8be7-6993a3be0fa2', '1b2d078c-4d43-48a9-b6b4-6e1fe87b1e13')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('03400fc8-5b1c-46ed-8499-dd7cb88dad18', 0, 'admin', TIMESTAMP '2014-07-08 07:43:44.079', 'admin', TIMESTAMP '2014-07-08 07:43:44.079', '', '', '', 'kuali.scheduling.schedule.request.state.error', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'de19b73a-954f-488b-8be7-6993a3be0fa2', '617f82af-e870-4bf9-a134-94f2376491f7')
/

-- Half Fall 2 2012, CHEM147
-- AO A
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('60dcd3a1-aea7-4674-8e01-fd056eafb076', 0, 'admin', TIMESTAMP '2014-07-02 15:06:53.527', 'admin', TIMESTAMP '2014-07-02 15:06:53.527', 'kuali.atp.2012HalfFall2', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '7cd658f5-a7e0-4b77-b64a-d4f05b3b93a9')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('7b5a1ec7-cf84-4127-b12d-3b611db4719d', '0', 'e95505b4-9172-4faa-8133-a4376945fdc5', '7cd658f5-a7e0-4b77-b64a-d4f05b3b93a9', '59fadfd8-118e-4528-b5d4-ace863db8454')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('59fadfd8-118e-4528-b5d4-ace863db8454', '0DAAF846-99D0-42E9-8C16-3FECC7760B10')
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='50cca1cc-8e5d-47a0-8bdc-c7d25eefe395'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('50cca1cc-8e5d-47a0-8bdc-c7d25eefe395', '7cd658f5-a7e0-4b77-b64a-d4f05b3b93a9')
/
delete from KSEN_SCHED_CMP_TMSLOT where SCHED_CMP_ID='cf5c07bb-0b05-4c35-a1b0-a750d104af5c'
/
delete from KSEN_SCHED_CMP where ID='cf5c07bb-0b05-4c35-a1b0-a750d104af5c'
/
delete from KSEN_SCHED where ID='92860be7-4e9b-40a8-a821-4ab174daa490' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='801a3770-80a6-416b-b0cf-3cdd84472cc8', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-02 15:06:53.527', ATP_ID='kuali.atp.2012Fall', CLU_ID='89407dcd-3efa-487c-9fd8-20d20c2f2f01', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='a822b81b-c0bc-48af-8915-3e693839e133' and VER_NBR=1
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('041336d7-49ec-4c2c-9449-cd9924818ac8', 0, 'admin', TIMESTAMP '2014-07-02 15:06:53.527', 'admin', TIMESTAMP '2014-07-02 15:06:53.527', TIMESTAMP '2014-07-02 15:06:53.918', '', '9200f9ae-aa74-477a-a6cb-b284664ccea7-FO-EO)', 'cbfd4a51-086f-4392-ae9c-6bd72efdf6c6', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', '9200f9ae-aa74-477a-a6cb-b284664ccea7-FO-EO', '9200f9ae-aa74-477a-a6cb-b284664ccea7', '60afee7a-d817-45b2-af93-f2a09409a12c')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b64213b7-e039-4e71-ae92-cc2a77bcf3da', 'AO1', '60afee7a-d817-45b2-af93-f2a09409a12c', '50cca1cc-8e5d-47a0-8bdc-c7d25eefe395', 'c00ed1c1-1b31-49bb-9425-bd5556a4f280')
/
-- AO B
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('79866109-cdb9-4ff4-98c4-f0cc13ee60a2', 'kuali.attribute.nonstd.ts.indicator', '465cf583-bc27-4f81-bd91-d8c36c68a695', '0', 'f89b7d0f-dd4e-43c8-b786-7499280890c9')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('a1c26894-2afd-4fe2-9520-08787afd9f0e', 0, 'admin', TIMESTAMP '2014-07-02 15:12:14.101', 'admin', TIMESTAMP '2014-07-02 15:12:14.101', 'kuali.atp.2012Fall', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '521c81c8-b122-4395-b07d-016ffc2673d4')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('901ec789-55c8-41bd-88fc-cd27552d3ebf', '0', 'e95505b4-9172-4faa-8133-a4376945fdc5', '521c81c8-b122-4395-b07d-016ffc2673d4', '001b4790-690d-42b4-837d-9889f2c3929b')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('001b4790-690d-42b4-837d-9889f2c3929b', '5FEA2AA2-5DB0-4AA9-9810-1B5774221FF7')
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='465cf583-bc27-4f81-bd91-d8c36c68a695'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('465cf583-bc27-4f81-bd91-d8c36c68a695', '521c81c8-b122-4395-b07d-016ffc2673d4')
/
delete from KSEN_SCHED_CMP_TMSLOT where SCHED_CMP_ID='ae01fc4d-2382-4af8-8dd6-64550c9e6580'
/
delete from KSEN_SCHED_CMP where ID='ae01fc4d-2382-4af8-8dd6-64550c9e6580'
/
delete from KSEN_SCHED where ID='b030973d-89c7-418e-8a3b-cb58ddd0ce9a' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='56d2c393-aab2-4356-93e2-f2946b6d35b1', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-02 15:12:14.101', ATP_ID='kuali.atp.2012Fall', CLU_ID='89407dcd-3efa-487c-9fd8-20d20c2f2f01', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1002', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1002', DESCR_PLAIN='1002', REF_URL='' where ID='517ca78c-76e7-44d4-849f-04d916cba1be' and VER_NBR=1
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('aed0a0bd-e6ca-4dc1-bed0-c767cc1b56e4', 0, 'admin', TIMESTAMP '2014-07-02 15:12:14.101', 'admin', TIMESTAMP '2014-07-02 15:12:14.101', '', '', '', 'Exam Schedule request set', '', 'http://student.kuali.org/wsdl/examOffering/ExamOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'fa654579-68ae-48fc-b190-89e5e28f7297')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('fa654579-68ae-48fc-b190-89e5e28f7297', '9200f9ae-aa74-477a-a6cb-b284664ccea7')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('74043b18-db17-4c57-a820-c58fcb92249e', 0, 'admin', TIMESTAMP '2014-07-02 15:12:14.101', 'admin', TIMESTAMP '2014-07-02 15:12:14.101', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'fa654579-68ae-48fc-b190-89e5e28f7297', 'b38602c2-58c1-4da4-8586-c6dd78eb8651')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('faa4649c-7cec-4844-8415-29ea22454e9e', '0', 'b38602c2-58c1-4da4-8586-c6dd78eb8651', '1bf8359f-2759-452c-85f2-0c3a212f8ddb')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('1bf8359f-2759-452c-85f2-0c3a212f8ddb', '370d2750-db69-4a98-b66c-377c20442f42')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('1bf8359f-2759-452c-85f2-0c3a212f8ddb', '4cbc813d-b674-44e3-af2c-29051a2c0fef')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('1bf8359f-2759-452c-85f2-0c3a212f8ddb', 'addb0485-b0f3-4d71-98a5-400c81563a01')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('77c98ef3-c384-4922-a707-4be58d1260de', 0, 'admin', TIMESTAMP '2014-07-02 15:12:14.101', 'admin', TIMESTAMP '2014-07-02 15:12:14.101', TIMESTAMP '2014-07-02 15:12:15.015', '', '9200f9ae-aa74-477a-a6cb-b284664ccea7-FO-EO)', 'cbfd4a51-086f-4392-ae9c-6bd72efdf6c6', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', '9200f9ae-aa74-477a-a6cb-b284664ccea7-FO-EO', '9200f9ae-aa74-477a-a6cb-b284664ccea7', '5e182152-204f-40ad-9421-2f5c15d395ee')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('eb15bd92-2c0f-4732-8629-6cd069ef988f', 'AO1', '5e182152-204f-40ad-9421-2f5c15d395ee', '465cf583-bc27-4f81-bd91-d8c36c68a695', '4582fb01-fc65-4b38-ba19-bf51393ee61a')
/

-- Half Fall 2 2012, HIST808
-- AO A
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0182efe2-605a-4739-ae0f-3f44a33c11d1', 'kuali.attribute.nonstd.ts.indicator', '2bddd4dd-c4fa-468a-906e-e671fdddf7de', '0', 'c944bd93-10e4-44e7-8cb7-4e3da5555ecb')
/
update KSEN_LUI set OBJ_ID='8dc92c5e-6117-433b-8173-93ef137f3e5f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-08 07:54:25.348', ATP_ID='kuali.atp.2012HalfFall2', CLU_ID='609a8232-1811-4533-bd6e-605644493dbf', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=20, MIN_SEATS='', NAME='HIST808 AO', DESCR_PLAIN='', REF_URL='' where ID='2bddd4dd-c4fa-468a-906e-e671fdddf7de' and VER_NBR=0
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('ad6617b0-2d63-4af5-91c9-0905a871faf1', 0, 'admin', TIMESTAMP '2014-07-08 07:54:25.348', 'admin', TIMESTAMP '2014-07-08 07:54:25.348', '', '', '', 'Schedule request set for HIST808 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '3e47c7e5-3e7d-42c6-8bc4-2bc3aa958527')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('3e47c7e5-3e7d-42c6-8bc4-2bc3aa958527', '2bddd4dd-c4fa-468a-906e-e671fdddf7de')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('05c2cb46-24e9-4ee9-aedd-d0d49a12f5a0', 0, 'admin', TIMESTAMP '2014-07-08 07:54:25.348', 'admin', TIMESTAMP '2014-07-08 07:54:25.348', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '3e47c7e5-3e7d-42c6-8bc4-2bc3aa958527', 'ac8c2557-ca16-457e-93b1-f576a4fa2674')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('f4dbfa2b-1336-494b-bf70-f85a0b9c0e0f', '1', 'ac8c2557-ca16-457e-93b1-f576a4fa2674', 'b84e30dc-6a68-4b81-8d7a-f39c4eff8b35')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('b84e30dc-6a68-4b81-8d7a-f39c4eff8b35', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('d203d0b8-04fb-4283-be8a-c071ca0196be', 0, 'admin', TIMESTAMP '2014-07-08 07:54:25.348', 'admin', TIMESTAMP '2014-07-08 07:54:25.348', 'kuali.atp.2012HalfFall2', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'a4ba49ee-abf9-4b0e-b8c0-ddf718c250e7')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('6d5d6521-b755-4757-8657-5c700b39cce4', '1', '', 'a4ba49ee-abf9-4b0e-b8c0-ddf718c250e7', '755ada6d-9336-453e-ac9c-d1490c92097c')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('755ada6d-9336-453e-ac9c-d1490c92097c', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='1', SCHED_RQST_ID='ac8c2557-ca16-457e-93b1-f576a4fa2674' where ID='b84e30dc-6a68-4b81-8d7a-f39c4eff8b35'
/
update KSEN_SCHED_RQST set OBJ_ID='05c2cb46-24e9-4ee9-aedd-d0d49a12f5a0', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-08 07:54:25.348', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='a4ba49ee-abf9-4b0e-b8c0-ddf718c250e7', SCHED_RQST_SET_ID='3e47c7e5-3e7d-42c6-8bc4-2bc3aa958527' where ID='ac8c2557-ca16-457e-93b1-f576a4fa2674' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='b84e30dc-6a68-4b81-8d7a-f39c4eff8b35'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('b84e30dc-6a68-4b81-8d7a-f39c4eff8b35', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('2bddd4dd-c4fa-468a-906e-e671fdddf7de', 'a4ba49ee-abf9-4b0e-b8c0-ddf718c250e7')
/
update KSEN_LUI set OBJ_ID='2f854502-fac6-4148-b861-2a1fc8f3233a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-08 07:54:25.348', ATP_ID='kuali.atp.2012Fall', CLU_ID='391fccd5-0ab0-42aa-9e95-7a9e6363b830', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='81378e30-1f96-4701-8bd0-b78b58698fb7' and VER_NBR=0
/
delete from KSEN_SCHED_RQST where ID='540505e2-e194-4feb-9cb6-4cd2748758cc' and VER_NBR=0
/
delete from KSEN_SCHED_REF_OBJECT where SCHED_RQST_SET_ID='e6517b61-a7ae-4d40-bb50-65f529d9195f'
/
delete from KSEN_SCHED_RQST_SET where ID='e6517b61-a7ae-4d40-bb50-65f529d9195f' and VER_NBR=0
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('69d9250d-1e42-41b6-a8dd-ffdf31a2e2ab', 0, 'admin', TIMESTAMP '2014-07-08 07:54:25.348', 'admin', TIMESTAMP '2014-07-08 07:54:25.348', '', '', '', 'Exam Schedule request set', '', 'http://student.kuali.org/wsdl/examOffering/ExamOfferingInfo', 'kuali.scheduling.schedule.request.set.state.error', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '52f35c3c-806f-4d48-96c5-7967617293b7')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('52f35c3c-806f-4d48-96c5-7967617293b7', '878792cf-6a8c-4298-a0ab-55f1ca7223b5')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('2cff5314-ad30-491c-afc4-23b689cfabc3', 0, 'admin', TIMESTAMP '2014-07-08 07:54:25.348', 'admin', TIMESTAMP '2014-07-08 07:54:25.348', '', '', '', 'kuali.scheduling.schedule.request.state.error', 'kuali.scheduling.schedule.request.type.schedule.request', '', '52f35c3c-806f-4d48-96c5-7967617293b7', 'acc8f285-6976-4020-8b14-22a5a42be106')
/

-- Half Fall 2 2012, BSCI122
-- AO A
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4d42ef1c-bb43-418a-9a39-bc2cde5f9462', 'kuali.attribute.nonstd.ts.indicator', '87907ef7-256e-4cc5-8aa2-6edb16a44c19', '0', 'ebd79edd-1c99-44e2-96d8-f0998bed8727')
/
update KSEN_LUI set OBJ_ID='7f42a149-a09e-4fee-804c-b6c01a956bdf', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-08 08:01:51.523', ATP_ID='kuali.atp.2012HalfFall2', CLU_ID='7aede248-1636-4bd1-9293-8e41231076f7', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=20, MIN_SEATS='', NAME='BSCI122 AO', DESCR_PLAIN='', REF_URL='' where ID='87907ef7-256e-4cc5-8aa2-6edb16a44c19' and VER_NBR=0
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('93c40294-c915-4a06-99a2-96355db6e172', 0, 'admin', TIMESTAMP '2014-07-08 08:01:51.523', 'admin', TIMESTAMP '2014-07-08 08:01:51.523', '', '', '', 'Schedule request set for BSCI122 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '94c5449f-ba14-4767-bf5c-44dab7c326dd')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('94c5449f-ba14-4767-bf5c-44dab7c326dd', '87907ef7-256e-4cc5-8aa2-6edb16a44c19')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('18d5d4ab-acd4-4199-8c42-ffcfb19dfc48', 0, 'admin', TIMESTAMP '2014-07-08 08:01:51.523', 'admin', TIMESTAMP '2014-07-08 08:01:51.523', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '94c5449f-ba14-4767-bf5c-44dab7c326dd', '8df13f7d-a306-4505-ae5c-5a6d45263a3f')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('371f604b-e8ea-4c55-ac67-aaac26a9d51c', '1', '8df13f7d-a306-4505-ae5c-5a6d45263a3f', 'df116679-9f2a-49e0-813e-aca1f2da8f94')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('df116679-9f2a-49e0-813e-aca1f2da8f94', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('453b25c5-ab96-4c95-9f1e-1d4f69861a6a', 0, 'admin', TIMESTAMP '2014-07-08 08:01:51.523', 'admin', TIMESTAMP '2014-07-08 08:01:51.523', 'kuali.atp.2012HalfFall2', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'f190dba5-1998-4616-bdf7-57b226e23a70')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('4f08f172-7557-430c-9e0a-729da602bab2', '1', '', 'f190dba5-1998-4616-bdf7-57b226e23a70', 'e6e806f4-53f9-43fe-9961-3ea6bc7c7382')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('e6e806f4-53f9-43fe-9961-3ea6bc7c7382', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='1', SCHED_RQST_ID='8df13f7d-a306-4505-ae5c-5a6d45263a3f' where ID='df116679-9f2a-49e0-813e-aca1f2da8f94'
/
update KSEN_SCHED_RQST set OBJ_ID='18d5d4ab-acd4-4199-8c42-ffcfb19dfc48', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-08 08:01:51.523', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='f190dba5-1998-4616-bdf7-57b226e23a70', SCHED_RQST_SET_ID='94c5449f-ba14-4767-bf5c-44dab7c326dd' where ID='8df13f7d-a306-4505-ae5c-5a6d45263a3f' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='df116679-9f2a-49e0-813e-aca1f2da8f94'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('df116679-9f2a-49e0-813e-aca1f2da8f94', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('87907ef7-256e-4cc5-8aa2-6edb16a44c19', 'f190dba5-1998-4616-bdf7-57b226e23a70')
/
update KSEN_LUI set OBJ_ID='aa52c43c-6e82-49fc-aa79-e9cc13e1984e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-08 08:01:51.523', ATP_ID='kuali.atp.2012Fall', CLU_ID='bd336eee-464c-4c40-bcfe-a5285f488bb2', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='36b60efc-9180-4d09-a5d3-64527d7d44d4' and VER_NBR=0
/
delete from KSEN_SCHED_RQST where ID='45376647-6cbf-4774-8645-1a26338e10a8' and VER_NBR=0
/
delete from KSEN_SCHED_REF_OBJECT where SCHED_RQST_SET_ID='115c67bf-31ea-47f7-858f-409e76b45f12'
/
delete from KSEN_SCHED_RQST_SET where ID='115c67bf-31ea-47f7-858f-409e76b45f12' and VER_NBR=0
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('74159d2b-b21c-453d-b722-ca120de1f5b0', 0, 'admin', TIMESTAMP '2014-07-08 08:01:51.523', 'admin', TIMESTAMP '2014-07-08 08:01:51.523', '', '', '', 'Exam Schedule request set', '', 'http://student.kuali.org/wsdl/examOffering/ExamOfferingInfo', 'kuali.scheduling.schedule.request.set.state.error', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'c964644d-2221-4da9-985e-f46162641641')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('c964644d-2221-4da9-985e-f46162641641', 'abe2ee0c-96ea-4432-9b7b-7ecc59158ab4')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('151aec8a-df57-4d0f-b963-4b6db4e3845c', 0, 'admin', TIMESTAMP '2014-07-08 08:01:51.523', 'admin', TIMESTAMP '2014-07-08 08:01:51.523', '', '', '', 'kuali.scheduling.schedule.request.state.error', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'c964644d-2221-4da9-985e-f46162641641', 'a65c02ec-48b0-4084-abbf-763ba10b9c4f')
/