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