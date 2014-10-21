-- KSENROLL-13484
-- Fall 2011, BSCI410
-- AO A
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('695c6618-4194-4ca4-8051-2f5d24b19837', 'kuali.attribute.nonstd.ts.indicator', '603af8c0-a2e8-49d5-8580-7d6499946610', '0', 'c4984192-197d-4e93-a2c5-ddf4bf65b798')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('64d11712-420e-4382-b2b8-f658df72ba6e', 0, 'admin', TIMESTAMP '2014-07-11 11:28:34.149', 'admin', TIMESTAMP '2014-07-11 11:28:34.149', 'kuali.atp.2011Fall', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '270f6db7-5a4b-490d-9805-027ecdbbabe9')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('61b01595-8182-4788-928a-e857a23670ab', '0', 'b1e0c71f-8c6f-4e9a-a0a9-a677efa6c56e', '270f6db7-5a4b-490d-9805-027ecdbbabe9', 'afdc9146-9b13-44d1-a1c7-ca0809091b49')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('afdc9146-9b13-44d1-a1c7-ca0809091b49', '8BD8EDBD-29DA-43B6-B8EE-455DB6A1D4D5')
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('603af8c0-a2e8-49d5-8580-7d6499946610', '270f6db7-5a4b-490d-9805-027ecdbbabe9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('10a705bc-b286-42bb-994e-5fa8b4f4cc82', 'kuali.attribute.nonstd.ts.indicator', '525f08d0-8839-4719-aa1a-ac4771488ae0', '0', 'a5a37480-f23b-44ab-814d-8f01be6e9be9')
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('525f08d0-8839-4719-aa1a-ac4771488ae0', '270f6db7-5a4b-490d-9805-027ecdbbabe9')
/

-- Spring 2011, BSCI105M
-- AO A
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7961efe4-a7eb-449d-95ed-127924b7ed0e', 'kuali.attribute.nonstd.ts.indicator', '5bd5a898-954c-4a0e-977f-007fb48a8b78', '0', 'edf45f56-43da-4672-b470-d9425749b47a')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('11c5ca37-0aa4-404a-8818-a4fe820f7082', 0, 'admin', TIMESTAMP '2014-07-13 19:57:22.159', 'admin', TIMESTAMP '2014-07-13 19:57:22.159', 'kuali.atp.2011Spring', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '305f6d3e-a472-4d5f-a0fd-dc4ff87e00a9')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('904468ab-d393-4c3e-8927-8114d62fc91c', '0', 'de71a7e3-2be9-4292-95f9-dd363be9bca7', '305f6d3e-a472-4d5f-a0fd-dc4ff87e00a9', '91a520b1-7fb3-4b81-a821-c1b1d1b7f809')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('91a520b1-7fb3-4b81-a821-c1b1d1b7f809', '63D17753-1E28-4B87-8561-8FB918B416A0')
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('5bd5a898-954c-4a0e-977f-007fb48a8b78', '305f6d3e-a472-4d5f-a0fd-dc4ff87e00a9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c3191de9-dfd7-4383-bb40-ec797abc4dfc', 'kuali.attribute.nonstd.ts.indicator', 'bd6f5ad7-1cb5-4c7d-b540-bf34cc8b5ed0', '0', 'c5d02442-891a-461f-92e8-28ce1b63db3e')
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('bd6f5ad7-1cb5-4c7d-b540-bf34cc8b5ed0', '305f6d3e-a472-4d5f-a0fd-dc4ff87e00a9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2ef26687-b529-49d1-ba64-a0d9638792d3', 'kuali.attribute.nonstd.ts.indicator', 'ae8080fd-0d79-4fa2-ba28-583a204ef0a8', '0', '72a6a1bb-d9c0-4ca4-990b-1504b31d1574')
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('ae8080fd-0d79-4fa2-ba28-583a204ef0a8', '305f6d3e-a472-4d5f-a0fd-dc4ff87e00a9')
/

-- Fall 2011, BSCI105
-- AO A
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ca21ebcb-6cc4-443a-bb68-f00a473a3012', 'kuali.attribute.nonstd.ts.indicator', '8ad03a20-7c49-48f9-bc3f-f8cd2ba8bee9', '0', '1c869741-cd0c-4751-b65d-1813bf8b342b')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('fc04754f-9a8d-4710-9f50-a3a85960fbd6', 0, 'admin', TIMESTAMP '2014-07-13 20:04:15.067', 'admin', TIMESTAMP '2014-07-13 20:04:15.067', '', '', '', 'Schedule request set for BSCI105 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '5f103e0a-0682-4768-88f6-b97f7c104c97')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('5f103e0a-0682-4768-88f6-b97f7c104c97', '8ad03a20-7c49-48f9-bc3f-f8cd2ba8bee9')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('3b8faa31-3f9b-4f78-86df-90611d03aa68', 0, 'admin', TIMESTAMP '2014-07-13 20:04:15.067', 'admin', TIMESTAMP '2014-07-13 20:04:15.067', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '5f103e0a-0682-4768-88f6-b97f7c104c97', '85dfe00c-2819-4f1b-a420-3e868119b923')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('35cfa933-1b51-4895-a3a7-ed20174d5081', '1', '85dfe00c-2819-4f1b-a420-3e868119b923', 'abf7c851-8114-4675-a540-2959f35caa9f')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('abf7c851-8114-4675-a540-2959f35caa9f', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('18723e53-4f0a-4f22-a3ea-45ee4b82164b', 0, 'admin', TIMESTAMP '2014-07-13 20:04:15.067', 'admin', TIMESTAMP '2014-07-13 20:04:15.067', 'kuali.atp.2011Fall', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '7fab196b-f166-4111-a364-e2eea2e3067d')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('04483700-d98a-4a5f-9622-6223bf28065c', '1', '', '7fab196b-f166-4111-a364-e2eea2e3067d', 'f4b253c6-66b1-40db-aa1b-18546f4194cf')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('f4b253c6-66b1-40db-aa1b-18546f4194cf', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('8ad03a20-7c49-48f9-bc3f-f8cd2ba8bee9', '7fab196b-f166-4111-a364-e2eea2e3067d')
/

-- Fall 2011, BSCI105M
-- AO A
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ad26bfe5-18d5-4117-8c32-5b7b4136806f', 'kuali.attribute.nonstd.ts.indicator', '9324dd89-0f48-4c43-add3-0714467bbf3a', '0', '1e3cc5b2-5f36-425d-8ef8-6c8fbb2f57f2')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('5af75f8b-1a4c-4f3b-9d9c-9b9d8f4dbacb', 0, 'admin', TIMESTAMP '2014-07-13 22:12:16.993', 'admin', TIMESTAMP '2014-07-13 22:12:16.993', '', '', '', 'Schedule request set for BSCI105M - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '845ca82e-29d0-49e1-bddb-898d0e6bfaee')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('845ca82e-29d0-49e1-bddb-898d0e6bfaee', '9324dd89-0f48-4c43-add3-0714467bbf3a')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('ee10e583-bbc1-43db-ad2b-3eb7436a76eb', 0, 'admin', TIMESTAMP '2014-07-13 22:12:16.993', 'admin', TIMESTAMP '2014-07-13 22:12:16.993', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '845ca82e-29d0-49e1-bddb-898d0e6bfaee', 'bed704a6-d213-4f36-a4e9-f62b425cdef7')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('80db0955-7e18-4622-a255-c5f205976c0c', '1', 'bed704a6-d213-4f36-a4e9-f62b425cdef7', '03bdace0-21e6-42ce-ab8a-278103dad81a')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('03bdace0-21e6-42ce-ab8a-278103dad81a', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('cf8e41a7-8641-4db9-ac32-0ae443e9f5c2', 0, 'admin', TIMESTAMP '2014-07-13 22:12:16.993', 'admin', TIMESTAMP '2014-07-13 22:12:16.993', 'kuali.atp.2011Fall', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '42f3566d-40b8-4f4c-8402-6f1ea5df0321')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('66290d94-c916-4f68-86fd-9798d953dc58', '1', '', '42f3566d-40b8-4f4c-8402-6f1ea5df0321', '3615d234-3f79-4be7-82c8-0b7c058208cc')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('3615d234-3f79-4be7-82c8-0b7c058208cc', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('9324dd89-0f48-4c43-add3-0714467bbf3a', '42f3566d-40b8-4f4c-8402-6f1ea5df0321')
/

-- Spring 2012, HIST404
-- AO A
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('309185f6-4511-4ac6-b96f-8363f2f66b2b', 'kuali.attribute.nonstd.ts.indicator', 'a0e93741-c6e5-47f7-ab2b-edc001ec409b', '0', '8b5be15b-084b-430f-82bf-4e0569d7161c')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('ed86a728-4143-448d-8385-b689cf7c37f5', 0, 'admin', TIMESTAMP '2014-07-13 22:19:17.776', 'admin', TIMESTAMP '2014-07-13 22:19:17.776', 'kuali.atp.2012Spring', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'a9ce3c8d-187d-4536-98d3-32f26ddeba99')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('1f4a7be9-aae7-4153-a326-53c7ff74d249', '0', '04cf2cf8-0305-4cf0-9bcc-00027c2e3262', 'a9ce3c8d-187d-4536-98d3-32f26ddeba99', '041be5af-eb21-4fe4-b67e-444685b3c52d')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('041be5af-eb21-4fe4-b67e-444685b3c52d', '22F92F0D-E1F8-4833-B534-04362A4496D1')
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('a0e93741-c6e5-47f7-ab2b-edc001ec409b', 'a9ce3c8d-187d-4536-98d3-32f26ddeba99')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1ecd59db-4c55-45ad-b31f-5252fce5b734', 'kuali.attribute.nonstd.ts.indicator', '53975fdf-40f3-4981-ba3c-8d1a32b6324f', '0', '5b116abc-7c3c-4cc8-b999-fb89420723e0')
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('53975fdf-40f3-4981-ba3c-8d1a32b6324f', 'a9ce3c8d-187d-4536-98d3-32f26ddeba99')
/

-- Spring 2012, BSCI105M
-- AO C
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3596e495-64a4-4135-9eee-eb0c662d3a11', 'kuali.attribute.nonstd.ts.indicator', 'dcea3844-6a2a-410d-b209-9f686713c2d3', '0', '613e77b3-0114-4ace-abab-6ba7f3d6afc4')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('e6634901-44b4-4a0e-a516-4205fb27e9f2', 0, 'admin', TIMESTAMP '2014-07-13 22:34:10.805', 'admin', TIMESTAMP '2014-07-13 22:34:10.805', 'kuali.atp.2012Spring', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'beba9be1-6d09-48f3-b374-18446761c14c')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('7516c520-8433-4339-bc3b-5983767c17d1', '0', '8d3d6cfd-6e37-4fac-bf71-a7b48419250b', 'beba9be1-6d09-48f3-b374-18446761c14c', 'ccd28ccf-7b74-4d9d-99e4-8220774a844a')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('ccd28ccf-7b74-4d9d-99e4-8220774a844a', '9BE2638B-EAB5-4374-B4B0-098FFC83D9B2')
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('dcea3844-6a2a-410d-b209-9f686713c2d3', 'beba9be1-6d09-48f3-b374-18446761c14c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5c687811-1450-414d-8066-bf350c036b39', 'kuali.attribute.nonstd.ts.indicator', 'f7f1ea68-8b42-4201-872c-a08bd302c4c1', '0', '49d66817-3b99-4e96-a3fa-f3a9de0fb2ad')
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('f7f1ea68-8b42-4201-872c-a08bd302c4c1', 'beba9be1-6d09-48f3-b374-18446761c14c')
/