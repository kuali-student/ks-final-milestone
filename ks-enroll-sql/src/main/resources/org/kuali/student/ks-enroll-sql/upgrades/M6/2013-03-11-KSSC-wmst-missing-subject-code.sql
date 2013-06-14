--KSCM-718
--Add missing WMST subject code
insert into KSSC_SUBJ_CD (ID, CREATEID, CREATETIME, UPDATEID, UPDATETIME, VER_NBR, NAME, STATE, CD, TYPE, OBJ_ID) values ('65', 'kr', TIMESTAMP '2011-04-12 20:00:00', 'kr', TIMESTAMP '2011-04-12 20:00:00', 0, 'Womens Studies', 'Actual', 'WMST', 'ks.core.subjectcode.usage.one', '4e164672-6ae3-4028-9ba5-c3dcd16a2ecf')
/
--Add missing org links to WMST
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('07211853-ca6c-4efa-a105-d67436adc2fc', '4014660630', '65', 1, null, null, '2e2012b4-f5db-4dc6-a908-4ae1e0a421ab')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('f1d2ff72-1eb6-4489-81fc-197e57fc5473', '3213375036', '65', 1, null, null, '4a10f61f-c78b-4eb5-9da6-51b9ec5138bd')
/
insert into KSSC_SUBJ_CD_JN_ORG (ID, ORG_ID, SUBJ_CD_ID, VER_NBR, EFF_DT, EXPIR_DT, OBJ_ID) values ('9641e6e2-cdc9-47d8-b908-972c773354bb', '2677933260', '65', 1, null, null, '64bf1537-b1ff-4347-9bc7-6ef945a8b4df')
/
