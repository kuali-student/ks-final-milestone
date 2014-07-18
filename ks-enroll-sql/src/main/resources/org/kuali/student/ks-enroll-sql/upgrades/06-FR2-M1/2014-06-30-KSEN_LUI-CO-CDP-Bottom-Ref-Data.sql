update KSEN_LUI_ATTR set OBJ_ID='e5ac5927-a6fe-4011-8b5e-503a9a5e823b', ATTR_KEY='kuali.attribute.grade.roster.level.type.key', OWNER_ID='f06db5de-a40a-4f96-a488-fb80727d2a15', ATTR_VALUE='kuali.lu.type.activity.Lab' where ID='3ad7afce-40dc-4718-9c1f-3f057342138b'
/
update KSEN_LUI set OBJ_ID='7e72aa14-210c-4a62-bb02-3f56cbb8b996', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:32:34.866', ATP_ID='kuali.atp.2014Spring', CLU_ID='1dc220c1-33a2-490c-8a17-849b0ebdf50a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lab only', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lab Only', DESCR_PLAIN='Courses with Lab only', REF_URL='' where ID='f06db5de-a40a-4f96-a488-fb80727d2a15' and VER_NBR=2
/
update KSEN_LUI_ATTR set OBJ_ID='6779301f-1c75-445d-8e32-6ccfe341c1d2', ATTR_KEY='kuali.attribute.final.exam.level.type', OWNER_ID='f06db5de-a40a-4f96-a488-fb80727d2a15', ATTR_VALUE='kuali.lu.type.activity.Lab' where ID='f4f3c3cd-2ee3-402f-8d8a-7144f390da65'
/
update KSEN_LUI_IDENT set OBJ_ID='3d651ff2-c30f-47ed-9c83-aabc2edd0a23', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:32:34.866', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lab Only', LUI_ID='f06db5de-a40a-4f96-a488-fb80727d2a15', ORGID='', SHRT_NAME='LAB', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='c1bf7bd0-949c-425d-96ed-73b85055dd46' and VER_NBR=2
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='f06db5de-a40a-4f96-a488-fb80727d2a15'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('f06db5de-a40a-4f96-a488-fb80727d2a15', 'kuali.lui.type.activity.offering.lab')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('741a59e9-a5d4-4130-b2c2-e717c9b3f6ab', 0, 'admin', TIMESTAMP '2014-07-01 09:32:34.866', 'admin', TIMESTAMP '2014-07-01 09:32:34.866', 'kuali.atp.2014Spring', 'cf596c5d-a14b-4485-9bcb-5a9fc53f8927', '', '', 'Courses with Lecture/Lab', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture/Lab', 'Courses with Lecture/Lab', '', '4a11821e-f4f8-4624-b232-236c171c7129')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fb341a22-d57c-49db-ba37-0f3b001ebf8e', 'regCodePrefix', '4a11821e-f4f8-4624-b232-236c171c7129', '2', '7f908e4d-13a4-441b-a972-2f12aa461de4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('63bf031a-8ec3-4c67-b725-7e3d68322ed3', 'kuali.attribute.final.exam.level.type', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lu.type.activity.Lecture', '51153a7c-7658-4087-a9b7-6df936afe3aa')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0b8bf251-ebd4-42da-b6b7-20b1408bc868', 'kuali.attribute.grade.roster.level.type.key', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lu.type.activity.Lecture', 'ad2d0d99-b3d1-4354-bbf2-ac4e4fd5e7b1')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5978561d-a4bf-4853-b526-011003f3d8df', 0, 'admin', TIMESTAMP '2014-07-01 09:32:34.866', 'admin', TIMESTAMP '2014-07-01 09:32:34.866', '', '', '', 'Lecture/Lab', '4a11821e-f4f8-4624-b232-236c171c7129', '', 'LEC/LAB', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '6b653923-f719-4d49-8ff6-2f6fee0262b6')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.type.activity.offering.lab')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('3a54de56-b6ec-4207-bec6-0cb1b04adb87', 0, 'admin', TIMESTAMP '2014-07-01 09:32:34.866', 'admin', TIMESTAMP '2014-07-01 09:32:34.866', TIMESTAMP '2014-07-01 09:32:35.795', '', 'CHEM232S-CO-FO', 'a768dc4d-167a-4fd8-80d4-1fe880a78300', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'CHEM232S-CO-FO', '4a11821e-f4f8-4624-b232-236c171c7129', 'a6d5ff23-7e2e-460f-bf58-68272d35128a')
/
update KSEN_LUI_ATTR set OBJ_ID='7ac0ba47-777b-4acb-b689-9859a1577466', ATTR_KEY='kuali.attribute.final.exam.use.matrix', OWNER_ID='a768dc4d-167a-4fd8-80d4-1fe880a78300', ATTR_VALUE=1 where ID='055a7b57-0348-4779-a564-9db278efc97d'
/
update KSEN_LUI set OBJ_ID='cfe79aaf-0bf3-4f1b-8cc5-632cb428f8e6', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:32:36.079', ATP_ID='kuali.atp.2014Spring', CLU_ID='CLUID-CHEM232-200408000000', EFF_DT=TIMESTAMP '2014-01-26 19:00:00.0', EXPIR_DT='', DESCR_FORMATTED='Provides experience in developing some basic laboratory techniques, recrystallizaton, distillation, extraction, chromatography.', LUI_STATE='kuali.lui.course.offering.state.offered', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='CHEM232S CO', DESCR_PLAIN='Provides experience in developing some basic laboratory techniques, recrystallizaton, distillation, extraction, chromatography.', REF_URL='' where ID='a768dc4d-167a-4fd8-80d4-1fe880a78300' and VER_NBR=2
/
update KSEN_LUI_ATTR set OBJ_ID='eefc2b7f-132c-4c0f-a6d0-aeead39ae5f6', ATTR_KEY='kuali.attribute.where.fees.attached.flag', OWNER_ID='a768dc4d-167a-4fd8-80d4-1fe880a78300', ATTR_VALUE=0 where ID='16aff99b-30f0-4ce8-a823-cee070dc7aa6'
/
update KSEN_LUI_ATTR set OBJ_ID='906f70b0-10a4-47c2-8c09-7e868eb629a0', ATTR_KEY='kuali.attribute.wait.list.indicator', OWNER_ID='a768dc4d-167a-4fd8-80d4-1fe880a78300', ATTR_VALUE=0 where ID='2c60957d-9b46-43e2-aed4-b4b57afc1623'
/
update KSEN_LUI_ATTR set OBJ_ID='4294df2b-657f-4845-9fbb-8ec53d0e60e3', ATTR_KEY='kuali.attribute.wait.list.type.key', OWNER_ID='a768dc4d-167a-4fd8-80d4-1fe880a78300', ATTR_VALUE=null where ID='8b0b5f82-22d5-4ffd-ba4d-ad4d401e9339'
/
update KSEN_LUI_ATTR set OBJ_ID='3d4a39ea-726f-4b6d-a9c1-55f5ff16f57f', ATTR_KEY='kuali.attribute.final.exam.driver', OWNER_ID='a768dc4d-167a-4fd8-80d4-1fe880a78300', ATTR_VALUE='kuali.lu.exam.driver.ActivityOffering' where ID='cd17b97c-cc68-489c-b3c9-c3b611c5c80d'
/
update KSEN_LUI_ATTR set OBJ_ID='36a05255-acb0-40ae-a5d1-77099fc85845', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='a768dc4d-167a-4fd8-80d4-1fe880a78300', ATTR_VALUE=0 where ID='dda65606-90f5-4d46-b638-dcc1a8fa361e'
/
update KSEN_LUI_IDENT set OBJ_ID='f0b5cf85-6f6f-4a99-9a8c-eba77c39c015', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:32:36.079', LUI_CD='CHEM232S', DIVISION='CHEM', IDENT_LEVEL='', LNG_NAME='Organic Chemistry Laboratory I', LUI_ID='a768dc4d-167a-4fd8-80d4-1fe880a78300', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='S', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='7cd8d01a-165f-4360-a9b5-1d7c78179403' and VER_NBR=2
/
update KSEN_LUI_LU_CD set OBJ_ID='e7b566ae-e79e-4a59-914d-43b44fbdd269', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:32:36.079', DESCR_FORMATTED='', LUI_ID='a768dc4d-167a-4fd8-80d4-1fe880a78300', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='6a971bba-76ef-43fd-b7f2-822e80841ed3' and VER_NBR=2
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='a768dc4d-167a-4fd8-80d4-1fe880a78300'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('a768dc4d-167a-4fd8-80d4-1fe880a78300', 'ORGID-4284516367')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='a768dc4d-167a-4fd8-80d4-1fe880a78300'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('a768dc4d-167a-4fd8-80d4-1fe880a78300', 'kuali.creditType.credit.degree.1.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('34d3631d-af05-4e0a-a9a4-4b311d027002', 0, 'admin', TIMESTAMP '2014-07-01 09:32:36.079', 'admin', TIMESTAMP '2014-07-01 09:32:36.079', 'kuali.atp.ExamPeriod.2014Spring', 'FCCF08F7BF135E91E040440A9B9A420D', '', '', '', 'kuali.lui.exam.offering.state.draft', 'kuali.lui.type.exam.offering.final', '', '', '', '', '', '316935fd-3e81-4418-9b42-44782b2f9d3f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8504ee15-b482-405c-84d4-5c6e38b05f6a', 'kuali.attribute.final.exam.driver', '316935fd-3e81-4418-9b42-44782b2f9d3f', 'PER_AO', '9907b3c8-de9f-4337-8ce0-f945e7befada')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c80e0a8b-06cd-4299-b4bc-5b0b860b8c96', 'kuali.attribute.final.exam.activity.driver', '316935fd-3e81-4418-9b42-44782b2f9d3f', 'kuali.lu.type.activity.Lab', 'f9a12d87-93dd-4c6f-b584-c6a1bc42b3ba')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('cadd8751-86d9-452c-b9a3-a103f1298ab6', 0, 'admin', TIMESTAMP '2014-07-01 09:32:36.079', 'admin', TIMESTAMP '2014-07-01 09:32:36.079', '', '', '', '', '316935fd-3e81-4418-9b42-44782b2f9d3f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '8f1cff08-33ac-4c92-923d-20192838538c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ce44a6d9-38b4-4762-a958-690b7ef3d70e', 0, 'admin', TIMESTAMP '2014-07-01 09:32:36.079', 'admin', TIMESTAMP '2014-07-01 09:32:36.079', TIMESTAMP '2014-07-01 09:32:40.868', '', '316935fd-3e81-4418-9b42-44782b2f9d3f-FO-EO)', 'f06db5de-a40a-4f96-a488-fb80727d2a15', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', '316935fd-3e81-4418-9b42-44782b2f9d3f-FO-EO', '316935fd-3e81-4418-9b42-44782b2f9d3f', '0a9bf43b-4e39-4ba2-b163-3f22d49496a3')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3967ac59-992c-462d-a555-51d3047dc04a', 'AO1', '0a9bf43b-4e39-4ba2-b163-3f22d49496a3', '327c75b4-e3be-4606-a724-63c50ee1e5aa', 'af230edf-c2f1-4664-9e9c-d1ffa0e07416')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('a3a94ed7-ad51-44b4-a302-129b013f7b2e', 0, 'admin', TIMESTAMP '2014-07-01 09:32:36.079', 'admin', TIMESTAMP '2014-07-01 09:32:36.079', 'kuali.atp.ExamPeriod.2014Spring', 'FCCF08F7BF135E91E040440A9B9A420D', '', '', '', 'kuali.lui.exam.offering.state.draft', 'kuali.lui.type.exam.offering.final', '', '', '', '', '', '21494697-4720-4fda-b389-0acc92f234e0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8aca4c6d-f63e-45a2-ad61-ab58293251cc', 'kuali.attribute.final.exam.driver', '21494697-4720-4fda-b389-0acc92f234e0', 'PER_AO', '09e0c6e5-a270-4376-b3f7-3f9d32926101')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('caf62c3e-c37c-4257-aec6-f11c107c15a3', 'kuali.attribute.final.exam.activity.driver', '21494697-4720-4fda-b389-0acc92f234e0', 'kuali.lu.type.activity.Lab', '900d2f70-cb42-4be2-9cec-c9640549210d')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7c657587-c369-43a7-ba6a-152e4cfb834a', 0, 'admin', TIMESTAMP '2014-07-01 09:32:36.079', 'admin', TIMESTAMP '2014-07-01 09:32:36.079', '', '', '', '', '21494697-4720-4fda-b389-0acc92f234e0', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ee34bbf1-d121-4739-b40d-424d3edca9d5')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('b61fd266-e511-4f26-8da7-ecd58e39ca0d', 0, 'admin', TIMESTAMP '2014-07-01 09:32:36.079', 'admin', TIMESTAMP '2014-07-01 09:32:36.079', TIMESTAMP '2014-07-01 09:32:46.905', '', '21494697-4720-4fda-b389-0acc92f234e0-FO-EO)', 'f06db5de-a40a-4f96-a488-fb80727d2a15', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', '21494697-4720-4fda-b389-0acc92f234e0-FO-EO', '21494697-4720-4fda-b389-0acc92f234e0', '221e8ff5-9592-43f0-b8a0-1aa7e05a5dee')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('abee5794-b0cf-4fd3-966f-f0a664b7d7bf', 'AO1', '221e8ff5-9592-43f0-b8a0-1aa7e05a5dee', '71b19b70-8f65-483e-9a4b-2292ca4bdbb2', '010fac51-6004-4e02-a08a-daee51376237')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('dd794286-63c3-4926-954d-61b2eaed2878', 0, 'admin', TIMESTAMP '2014-07-01 09:32:36.079', 'admin', TIMESTAMP '2014-07-01 09:32:36.079', 'kuali.atp.ExamPeriod.2014Spring', 'FCCF08F7BF135E91E040440A9B9A420D', '', '', '', 'kuali.lui.exam.offering.state.draft', 'kuali.lui.type.exam.offering.final', '', '', '', '', '', 'e15e8176-84b3-4fcb-be01-4953ea93d39f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3b225b07-62d9-489f-bcce-8a8b642832c2', 'kuali.attribute.final.exam.driver', 'e15e8176-84b3-4fcb-be01-4953ea93d39f', 'PER_AO', 'b28d5bf1-d726-443e-8cb1-2f239b546218')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8bdb5502-8744-453d-88ec-b72b1c8c62da', 'kuali.attribute.final.exam.activity.driver', 'e15e8176-84b3-4fcb-be01-4953ea93d39f', 'kuali.lu.type.activity.Lab', 'e308b22d-f2aa-4134-9840-7673b2b90150')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('b803441a-ec47-4051-a0b4-291f3b30abb3', 0, 'admin', TIMESTAMP '2014-07-01 09:32:36.079', 'admin', TIMESTAMP '2014-07-01 09:32:36.079', '', '', '', '', 'e15e8176-84b3-4fcb-be01-4953ea93d39f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ce35a7ef-d1f3-4818-af80-f084ff432400')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('6dd2e3a9-f213-4bb7-ae11-7a85529206b4', 0, 'admin', TIMESTAMP '2014-07-01 09:32:36.079', 'admin', TIMESTAMP '2014-07-01 09:32:36.079', TIMESTAMP '2014-07-01 09:32:47.377', '', 'e15e8176-84b3-4fcb-be01-4953ea93d39f-FO-EO)', 'f06db5de-a40a-4f96-a488-fb80727d2a15', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', 'e15e8176-84b3-4fcb-be01-4953ea93d39f-FO-EO', 'e15e8176-84b3-4fcb-be01-4953ea93d39f', 'd2052631-0594-4662-a47f-60b05c19e28e')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('96548e07-bb05-46db-8464-a67e9567f175', 'AO1', 'd2052631-0594-4662-a47f-60b05c19e28e', '88dc317f-2eda-4778-b03e-dff121c2b355', 'c1218a33-69f7-48ad-9655-e94b38ba61d0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('48860c91-807a-4ce0-b7c6-aa87bd70c7f0', 0, 'admin', TIMESTAMP '2014-07-01 09:32:36.079', 'admin', TIMESTAMP '2014-07-01 09:32:36.079', 'kuali.atp.ExamPeriod.2014Spring', 'FCCF08F7BF135E91E040440A9B9A420D', '', '', '', 'kuali.lui.exam.offering.state.draft', 'kuali.lui.type.exam.offering.final', '', '', '', '', '', 'db0a811c-a760-43e0-a582-f33c6cbc7b8d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cce3bc57-6373-4403-a467-76778eb03e44', 'kuali.attribute.final.exam.driver', 'db0a811c-a760-43e0-a582-f33c6cbc7b8d', 'PER_AO', '9dffd42e-1952-4279-b546-088f375a3063')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fb511444-f02e-421d-abbe-6ce33fc50217', 'kuali.attribute.final.exam.activity.driver', 'db0a811c-a760-43e0-a582-f33c6cbc7b8d', 'kuali.lu.type.activity.Lab', '7c3ea9cd-c070-4ddd-a7e2-fa8bf429aa6a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6493b29a-8d6b-47d0-8b21-dac7039ef6a1', 0, 'admin', TIMESTAMP '2014-07-01 09:32:36.079', 'admin', TIMESTAMP '2014-07-01 09:32:36.079', '', '', '', '', 'db0a811c-a760-43e0-a582-f33c6cbc7b8d', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c29641e2-dfdf-4148-a60c-9c07eb5a7a39')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('1bcf9277-6864-407f-ac9c-69dbf0e16500', 0, 'admin', TIMESTAMP '2014-07-01 09:32:36.079', 'admin', TIMESTAMP '2014-07-01 09:32:36.079', TIMESTAMP '2014-07-01 09:32:48.229', '', 'db0a811c-a760-43e0-a582-f33c6cbc7b8d-FO-EO)', 'f06db5de-a40a-4f96-a488-fb80727d2a15', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', 'db0a811c-a760-43e0-a582-f33c6cbc7b8d-FO-EO', 'db0a811c-a760-43e0-a582-f33c6cbc7b8d', '5fa0b3fc-9cc5-42f4-a1a6-2a8aaa3e0195')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4b1c2b13-8946-483e-8fc8-fc69877acda5', 'AO1', '5fa0b3fc-9cc5-42f4-a1a6-2a8aaa3e0195', 'd5e5ec0e-a898-4eab-9a32-8c5a9a718b1e', '4b1f8fc0-6b73-423b-8292-dcf47d01fa22')
/
update KSEN_LUI set OBJ_ID='2f2a050e-3ba9-450a-b3ab-7cba90cafb26', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:32:36.079', ATP_ID='kuali.atp.ExamPeriod.2014Spring', CLU_ID='F4F97F80D40EE585E040007F01013294', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.exam.offering.state.canceled', LUI_TYPE='kuali.lui.type.exam.offering.final', MAX_SEATS='', MIN_SEATS='', NAME='', DESCR_PLAIN='', REF_URL='' where ID='a5e3d6f7-3229-4a16-8d44-88aaa2652534' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='9ab658b3-c4fa-49af-98a1-7ced0d88a533', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:32:36.079', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='a5e3d6f7-3229-4a16-8d44-88aaa2652534', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='7cc3f93f-9ce3-4934-99a3-4a2df3fe0f0f' and VER_NBR=0
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('e81b1796-b762-4cc8-90b0-b398bfd4f9b2', 0, 'admin', TIMESTAMP '2014-07-01 09:33:44.662', 'admin', TIMESTAMP '2014-07-01 09:33:44.662', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '4a11821e-f4f8-4624-b232-236c171c7129', '2', '2', 'e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('94f341a9-4ccb-4acf-95ca-37caff29cfa3', 'kuali.lui.type.activity.offering.lab', 'e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7', '9061d415-489b-419e-96a3-4a34da4b35cf')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('42000226-0e30-4de5-adb8-8e8743d47b45', 'kuali.lui.type.activity.offering.lecture', 'e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7', '1760a100-da83-46ae-8626-5035b5053bd9')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7' where ID='1760a100-da83-46ae-8626-5035b5053bd9'
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('e55508f7-2264-4b4b-b08b-ae6d211b0ae2', 0, 'admin', TIMESTAMP '2014-07-01 09:34:16.104', 'admin', TIMESTAMP '2014-07-01 09:34:16.104', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '4a11821e-f4f8-4624-b232-236c171c7129', '3', '3', '65a41861-d49c-4d35-9cf2-b69d03c038c7')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('0a80c7fd-2c9a-4a5b-bfd2-0ec28fdc72c0', 'kuali.lui.type.activity.offering.lab', '65a41861-d49c-4d35-9cf2-b69d03c038c7', '4b8aa73a-b04e-4c0b-9582-65aaf931260f')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('db26c8a1-dd56-43c6-961a-bf6b2f95f700', 'kuali.lui.type.activity.offering.lecture', '65a41861-d49c-4d35-9cf2-b69d03c038c7', 'dc493778-9838-4d06-8bfa-980fa78e9ca5')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='65a41861-d49c-4d35-9cf2-b69d03c038c7' where ID='dc493778-9838-4d06-8bfa-980fa78e9ca5'
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('81cf3bfe-c5da-41fe-93cd-54ad204f17cd', 'cb85be62-5b0d-45fc-aa32-1ef8c91b763f', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300-P', '3b40912d-6878-4a49-8799-4b59a7c09363')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('44a400c1-9fa5-4d47-aec4-f66c6458d3b0', 'E', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300', 'e1582fd1-dc64-4c75-9bb9-58db483e3158')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='cb85be62-5b0d-45fc-aa32-1ef8c91b763f' and UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('336bac6e-a744-4bf6-830f-18806a700981', 0, 'admin', TIMESTAMP '2014-07-01 09:35:04.374', 'admin', TIMESTAMP '2014-07-01 09:35:04.374', 'kuali.atp.2014Spring', '14b8a6c2-8074-419e-be8c-d200a4537969', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', '', '', 'CHEM232S AO', '', '', '2d87be27-1d4d-4866-b161-6725de375f2a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('25978abd-d3d4-438c-a80f-7c7a90f03660', 'kuali.attribute.course.evaluation.indicator', '2d87be27-1d4d-4866-b161-6725de375f2a', null, '5b8edbce-5788-436f-bfe7-693d6f6b2160')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6206b194-4168-4e20-b72c-a95d92cb5b93', 'kuali.attribute.nonstd.ts.indicator', '2d87be27-1d4d-4866-b161-6725de375f2a', 0, 'cdb8ab77-9dbb-41f2-b706-c0761e1e5b08')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4f06809b-33bb-4f9b-9533-bf0339b11ef1', 'kuali.attribute.max.enrollment.is.estimate', '2d87be27-1d4d-4866-b161-6725de375f2a', null, '1903dd5a-1d4f-4cb0-8b5b-fdf7c5d8bfc4')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('327270ef-86b6-4b93-8fd9-f0b0e6aa06da', 0, 'admin', TIMESTAMP '2014-07-01 09:35:04.374', 'admin', TIMESTAMP '2014-07-01 09:35:04.374', 'E', '', '', '', '2d87be27-1d4d-4866-b161-6725de375f2a', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'fdb806fb-894a-4a0f-a24b-17f0954c889a')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('89be3460-4806-4038-b3eb-effe73422221', 0, 'admin', TIMESTAMP '2014-07-01 09:35:04.374', 'admin', TIMESTAMP '2014-07-01 09:35:04.374', '', '2d87be27-1d4d-4866-b161-6725de375f2a', '', 'kuali.lu.code.honorsOffering', null, '318c2d5a-31e1-43e6-9297-89ce7052340b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('0a99e62f-d46e-41fa-abe6-c4d7dff2a773', 0, 'admin', TIMESTAMP '2014-07-01 09:35:04.374', 'admin', TIMESTAMP '2014-07-01 09:35:04.374', TIMESTAMP '2014-07-01 09:35:06.154', '', 'CHEM232S-FO-AO', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM232S-FO-AO', '2d87be27-1d4d-4866-b161-6725de375f2a', '3e4304cf-70b7-4f00-b3d3-dfc0b47d9a35')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='e81b1796-b762-4cc8-90b0-b398bfd4f9b2', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:35:04.374', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='4a11821e-f4f8-4624-b232-236c171c7129', NAME='2', PRIVATE_NAME='2' where ID='e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7' and VER_NBR=0
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('1760a100-da83-46ae-8626-5035b5053bd9', '2d87be27-1d4d-4866-b161-6725de375f2a')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('2d6f93c6-5bdd-4d5b-95ac-0cf2c3f6286c', 0, 'admin', TIMESTAMP '2014-07-01 09:35:04.374', 'admin', TIMESTAMP '2014-07-01 09:35:04.374', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '73f717f5-2d54-4370-a78d-aa9597704bc6')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('73f717f5-2d54-4370-a78d-aa9597704bc6', '2d87be27-1d4d-4866-b161-6725de375f2a')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('73f717f5-2d54-4370-a78d-aa9597704bc6', '4a11821e-f4f8-4624-b232-236c171c7129')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('a31b1705-ab99-4703-b305-b6414f2b7d30', 0, 'admin', TIMESTAMP '2014-07-01 09:35:04.374', 'admin', TIMESTAMP '2014-07-01 09:35:04.374', 'kuali.atp.ExamPeriod.2014Spring', 'FCCF08F7BF135E91E040440A9B9A420D', '', '', '', 'kuali.lui.exam.offering.state.draft', 'kuali.lui.type.exam.offering.final', '', '', '', '', '', 'ec49ec37-a8a3-4c12-bcb9-c313fcd38af8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('32807ed2-773d-4f03-a5ed-bc126d805657', 'kuali.attribute.final.exam.driver', 'ec49ec37-a8a3-4c12-bcb9-c313fcd38af8', 'PER_AO', '914abee5-77f8-4511-8d1f-0343e5e91c3e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2e33641f-e69d-427d-8eff-ce451c5b9b9f', 'kuali.attribute.final.exam.activity.driver', 'ec49ec37-a8a3-4c12-bcb9-c313fcd38af8', 'kuali.lu.type.activity.Lecture', '44e19b42-d431-45d2-bd15-a6a3abf1cc56')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6cca4b00-d613-4093-8011-38e56ef069ad', 0, 'admin', TIMESTAMP '2014-07-01 09:35:04.374', 'admin', TIMESTAMP '2014-07-01 09:35:04.374', '', '', '', '', 'ec49ec37-a8a3-4c12-bcb9-c313fcd38af8', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '245f6308-115c-40b2-9a1a-206d6adeb6ba')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('37671515-65a9-4640-b119-e8f115390fae', 0, 'admin', TIMESTAMP '2014-07-01 09:35:04.374', 'admin', TIMESTAMP '2014-07-01 09:35:04.374', TIMESTAMP '2014-07-01 09:35:08.513', '', 'ec49ec37-a8a3-4c12-bcb9-c313fcd38af8-FO-EO)', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', 'ec49ec37-a8a3-4c12-bcb9-c313fcd38af8-FO-EO', 'ec49ec37-a8a3-4c12-bcb9-c313fcd38af8', 'dfe18445-ef56-4709-bf7a-7f6eba91b629')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9d47ff94-99a0-4b92-ab5a-4ed80305b46a', 'AO1', 'dfe18445-ef56-4709-bf7a-7f6eba91b629', '2d87be27-1d4d-4866-b161-6725de375f2a', 'fec7b2f9-aea7-4b13-894e-5ea2f4879f15')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('57ef1506-d885-4418-9153-c86ff413d6ac', '62386584-6c75-471c-8935-b2ce5322c98c', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300-P', 'b489873e-d0d8-4afd-b331-8fac17b2ab3f')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('1133bfb6-4446-4dea-b7e9-7470f6477f67', 'F', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300', '95a310fc-4e73-4b66-ae41-64af1877013f')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='62386584-6c75-471c-8935-b2ce5322c98c' and UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('14ad0153-4d97-4499-9095-c4f8d5651740', 0, 'admin', TIMESTAMP '2014-07-01 09:35:35.682', 'admin', TIMESTAMP '2014-07-01 09:35:35.682', 'kuali.atp.2014Spring', '14b8a6c2-8074-419e-be8c-d200a4537969', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', '', '', 'CHEM232S AO', '', '', 'db3e8b36-fe91-4ab6-8e31-0f795d88149a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f2f21003-4b8a-49cd-b79d-143d7ced169f', 'kuali.attribute.course.evaluation.indicator', 'db3e8b36-fe91-4ab6-8e31-0f795d88149a', null, '43f62e37-e41c-4ca7-95a7-3b2e64808a29')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('71d81772-5f7f-47e0-b0a9-bd73a3c9ac76', 'kuali.attribute.nonstd.ts.indicator', 'db3e8b36-fe91-4ab6-8e31-0f795d88149a', 0, 'c35377e6-9a0a-4c58-8767-261e0e153329')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d122eb1f-8b31-4837-8ddf-379815ff5603', 'kuali.attribute.max.enrollment.is.estimate', 'db3e8b36-fe91-4ab6-8e31-0f795d88149a', null, '4438aca0-f3d4-48f4-b76a-c352fc56104d')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c729aab7-950d-4b5a-9c96-0c295a53ea41', 0, 'admin', TIMESTAMP '2014-07-01 09:35:35.682', 'admin', TIMESTAMP '2014-07-01 09:35:35.682', 'F', '', '', '', 'db3e8b36-fe91-4ab6-8e31-0f795d88149a', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c682dafa-0a99-491c-a078-f54853b56f51')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('9dda3443-21fe-44b0-b5e8-aeb6b28769b1', 0, 'admin', TIMESTAMP '2014-07-01 09:35:35.682', 'admin', TIMESTAMP '2014-07-01 09:35:35.682', '', 'db3e8b36-fe91-4ab6-8e31-0f795d88149a', '', 'kuali.lu.code.honorsOffering', null, '07f26f90-c7a3-4203-aad1-800cbc7893ff')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('1cc2d3b9-2f2b-432a-b7cf-f640d06dd65a', 0, 'admin', TIMESTAMP '2014-07-01 09:35:35.682', 'admin', TIMESTAMP '2014-07-01 09:35:35.682', TIMESTAMP '2014-07-01 09:35:36.308', '', 'CHEM232S-FO-AO', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM232S-FO-AO', 'db3e8b36-fe91-4ab6-8e31-0f795d88149a', '66805f7a-0e33-48aa-a7e5-65405579d00b')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='e81b1796-b762-4cc8-90b0-b398bfd4f9b2', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:35:35.682', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='4a11821e-f4f8-4624-b232-236c171c7129', NAME='2', PRIVATE_NAME='2' where ID='e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7' and VER_NBR=1
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('1760a100-da83-46ae-8626-5035b5053bd9', 'db3e8b36-fe91-4ab6-8e31-0f795d88149a')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('e953c8b2-a46a-4217-9123-fecdfe48530b', 0, 'admin', TIMESTAMP '2014-07-01 09:35:35.682', 'admin', TIMESTAMP '2014-07-01 09:35:35.682', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', 'e9862f7e-574c-4467-9180-7a4d546ab9a3')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('e9862f7e-574c-4467-9180-7a4d546ab9a3', 'db3e8b36-fe91-4ab6-8e31-0f795d88149a')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('e9862f7e-574c-4467-9180-7a4d546ab9a3', '4a11821e-f4f8-4624-b232-236c171c7129')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('cf12dce5-da4d-4764-a74b-9b2e9bf4fec4', 0, 'admin', TIMESTAMP '2014-07-01 09:35:35.682', 'admin', TIMESTAMP '2014-07-01 09:35:35.682', 'kuali.atp.ExamPeriod.2014Spring', 'FCCF08F7BF135E91E040440A9B9A420D', '', '', '', 'kuali.lui.exam.offering.state.draft', 'kuali.lui.type.exam.offering.final', '', '', '', '', '', '2eb87fe6-44e3-4eb2-9ff0-af95ff6d5c04')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('07944efd-1a0d-4f84-8a97-2ed59d827005', 'kuali.attribute.final.exam.driver', '2eb87fe6-44e3-4eb2-9ff0-af95ff6d5c04', 'PER_AO', '58c1fbbc-8206-409d-a4f0-ce776cbaccfb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1f9b6c70-42d4-4cf6-8f47-1707e284583e', 'kuali.attribute.final.exam.activity.driver', '2eb87fe6-44e3-4eb2-9ff0-af95ff6d5c04', 'kuali.lu.type.activity.Lecture', '39e73977-438d-403b-83a3-3349db05a34b')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('0364499a-5446-46e7-b348-ba890f5e4c64', 0, 'admin', TIMESTAMP '2014-07-01 09:35:35.682', 'admin', TIMESTAMP '2014-07-01 09:35:35.682', '', '', '', '', '2eb87fe6-44e3-4eb2-9ff0-af95ff6d5c04', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '9254786d-b12d-42dd-a216-5871df20f41c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('95165806-60f0-4b37-8ea0-b3f8ee67a841', 0, 'admin', TIMESTAMP '2014-07-01 09:35:35.682', 'admin', TIMESTAMP '2014-07-01 09:35:35.682', TIMESTAMP '2014-07-01 09:35:37.714', '', '2eb87fe6-44e3-4eb2-9ff0-af95ff6d5c04-FO-EO)', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', '2eb87fe6-44e3-4eb2-9ff0-af95ff6d5c04-FO-EO', '2eb87fe6-44e3-4eb2-9ff0-af95ff6d5c04', '2b93588c-37c5-4aa1-b374-01c08cf56aa4')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e3e38628-fc4b-4c2e-9a0e-5e70ff2406dd', 'AO1', '2b93588c-37c5-4aa1-b374-01c08cf56aa4', 'db3e8b36-fe91-4ab6-8e31-0f795d88149a', 'ff019799-ed1d-4291-8ee2-a47da44bafc2')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('f861c338-ab9a-45e8-a16a-bb6f744066bc', '345a7afd-0163-4fe3-83df-5321165ef89f', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300-P', '64f40c31-2308-473b-959a-b73266ca4460')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('f163defe-10dd-4eab-9b31-958492b5c2f4', 'G', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300', '8198c3e4-d4c4-4a3b-805e-d3e832df1a55')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='345a7afd-0163-4fe3-83df-5321165ef89f' and UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('2860f0d5-fc65-4434-9a43-a15933e79929', 0, 'admin', TIMESTAMP '2014-07-01 09:36:06.608', 'admin', TIMESTAMP '2014-07-01 09:36:06.608', 'kuali.atp.2014Spring', '14b8a6c2-8074-419e-be8c-d200a4537969', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', '', '', 'CHEM232S AO', '', '', 'af9f338a-1256-4f88-b977-8da3cb860f6a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('18468572-96c2-47be-9f7f-d6de6e7b0c86', 'kuali.attribute.course.evaluation.indicator', 'af9f338a-1256-4f88-b977-8da3cb860f6a', null, '0d68d47b-eaba-4ed1-a0b2-b99d3b829247')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2d72a5d7-c2e6-4e14-a258-9176d7094a4a', 'kuali.attribute.nonstd.ts.indicator', 'af9f338a-1256-4f88-b977-8da3cb860f6a', 0, '96f02336-2943-4b4b-8289-965fc9f0a802')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dbf7d311-05b7-495b-abe5-3469ef0e6e57', 'kuali.attribute.max.enrollment.is.estimate', 'af9f338a-1256-4f88-b977-8da3cb860f6a', null, 'c56f7885-5996-4c0d-8bfc-20581ab38ced')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('19c52b2a-0aff-4c57-9c71-77ee7aa5eead', 0, 'admin', TIMESTAMP '2014-07-01 09:36:06.608', 'admin', TIMESTAMP '2014-07-01 09:36:06.608', 'G', '', '', '', 'af9f338a-1256-4f88-b977-8da3cb860f6a', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '17622a72-7816-4a1b-ab31-9e9d50f6af5a')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('1b0d9a2b-9ebb-486c-9b34-2807e348779f', 0, 'admin', TIMESTAMP '2014-07-01 09:36:06.608', 'admin', TIMESTAMP '2014-07-01 09:36:06.608', '', 'af9f338a-1256-4f88-b977-8da3cb860f6a', '', 'kuali.lu.code.honorsOffering', null, '00a49514-7012-496d-aa8b-46f92e54927b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f2755250-f1dd-4b10-8c2e-58d85fd6ed0f', 0, 'admin', TIMESTAMP '2014-07-01 09:36:06.608', 'admin', TIMESTAMP '2014-07-01 09:36:06.608', TIMESTAMP '2014-07-01 09:36:07.342', '', 'CHEM232S-FO-AO', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM232S-FO-AO', 'af9f338a-1256-4f88-b977-8da3cb860f6a', '7eb94397-869f-4638-ae8a-0f9222605632')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='e55508f7-2264-4b4b-b08b-ae6d211b0ae2', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:36:06.608', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='4a11821e-f4f8-4624-b232-236c171c7129', NAME='3', PRIVATE_NAME='3' where ID='65a41861-d49c-4d35-9cf2-b69d03c038c7' and VER_NBR=0
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('dc493778-9838-4d06-8bfa-980fa78e9ca5', 'af9f338a-1256-4f88-b977-8da3cb860f6a')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('ecbce4cf-8801-4af9-ae55-b4012c351420', 0, 'admin', TIMESTAMP '2014-07-01 09:36:06.608', 'admin', TIMESTAMP '2014-07-01 09:36:06.608', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', 'e764caf5-e082-4d28-90ea-35c7a37d4f68')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('e764caf5-e082-4d28-90ea-35c7a37d4f68', 'af9f338a-1256-4f88-b977-8da3cb860f6a')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('e764caf5-e082-4d28-90ea-35c7a37d4f68', '4a11821e-f4f8-4624-b232-236c171c7129')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('75ac34ec-3b43-489f-ad1e-6921e02b0aac', 0, 'admin', TIMESTAMP '2014-07-01 09:36:06.608', 'admin', TIMESTAMP '2014-07-01 09:36:06.608', 'kuali.atp.ExamPeriod.2014Spring', 'FCCF08F7BF135E91E040440A9B9A420D', '', '', '', 'kuali.lui.exam.offering.state.draft', 'kuali.lui.type.exam.offering.final', '', '', '', '', '', '696b1e1d-aff2-45fd-80c1-cf49f55be616')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('00c2d906-db79-415a-aae5-435065a62780', 'kuali.attribute.final.exam.driver', '696b1e1d-aff2-45fd-80c1-cf49f55be616', 'PER_AO', 'b47316b9-27fc-4017-9516-b31e1f6b2542')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0895e0b8-e240-4ce1-8bd5-1d430dcf7320', 'kuali.attribute.final.exam.activity.driver', '696b1e1d-aff2-45fd-80c1-cf49f55be616', 'kuali.lu.type.activity.Lecture', 'a6dcd026-43b2-4d71-b70b-ef5ea8bffac3')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('8c0fdda9-5d53-4a71-b544-1b66d3391d81', 0, 'admin', TIMESTAMP '2014-07-01 09:36:06.608', 'admin', TIMESTAMP '2014-07-01 09:36:06.608', '', '', '', '', '696b1e1d-aff2-45fd-80c1-cf49f55be616', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '578fe2c2-d8b6-493c-9cd5-f66a45cf6450')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a240b80b-870e-4435-a255-ba43a9d8a737', 0, 'admin', TIMESTAMP '2014-07-01 09:36:06.608', 'admin', TIMESTAMP '2014-07-01 09:36:06.608', TIMESTAMP '2014-07-01 09:36:07.93', '', '696b1e1d-aff2-45fd-80c1-cf49f55be616-FO-EO)', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', '696b1e1d-aff2-45fd-80c1-cf49f55be616-FO-EO', '696b1e1d-aff2-45fd-80c1-cf49f55be616', '9f22353f-cd47-47bc-aa74-ae6b36026e6d')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b015fa2a-7ed6-4060-9c8f-8201d1eabeaa', 'AO1', '9f22353f-cd47-47bc-aa74-ae6b36026e6d', 'af9f338a-1256-4f88-b977-8da3cb860f6a', 'b060135a-28bd-4251-8727-b641313aa6e3')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('b842a102-b1da-468d-b1c9-27089638453e', 'd5ccf0b6-ed9a-4ee3-aea8-d6504ffa3cd9', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300-P', 'cbd0da3f-851b-48d8-aabb-ecd35fdd210e')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('d2930f44-dcd6-4425-b621-f86805607ebd', 'H', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300', '40686415-ff18-4eb8-9a59-1c5cd1300be9')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='d5ccf0b6-ed9a-4ee3-aea8-d6504ffa3cd9' and UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d618d44a-80d4-4192-a930-04dd401e0e5c', 0, 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'kuali.atp.2014Spring', '50f30135-2d2e-4bd5-afcd-1f697d38174a', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lab', '', '', 'CHEM232S AO', '', '', '030e8905-94ae-4f0c-9dbd-f0b39aa81ff0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b7a6f172-895f-4e72-aea7-c2d63867807e', 'kuali.attribute.nonstd.ts.indicator', '030e8905-94ae-4f0c-9dbd-f0b39aa81ff0', 0, '1fc6e1f7-75f0-471e-878c-812e493867ff')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3559f5eb-9e13-4259-a36f-060f10691278', 'kuali.attribute.course.evaluation.indicator', '030e8905-94ae-4f0c-9dbd-f0b39aa81ff0', null, '3b7e80ce-899f-4cb0-96f4-1b9c82a23c9c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ef6e3be2-c284-4670-8c06-fd3c7e463320', 'kuali.attribute.max.enrollment.is.estimate', '030e8905-94ae-4f0c-9dbd-f0b39aa81ff0', null, 'afe6391b-1a24-43b3-83b9-906338773c16')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('940b006b-0908-4e20-9a53-81eaa5a55172', 0, 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'H', '', '', '', '030e8905-94ae-4f0c-9dbd-f0b39aa81ff0', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'b7ba7228-07d8-488a-b7f7-ea2219452e52')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('b0c84fcd-eea7-474a-9d46-766bfcabf0e6', 0, 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'admin', TIMESTAMP '2014-07-01 09:36:39.293', '', '030e8905-94ae-4f0c-9dbd-f0b39aa81ff0', '', 'kuali.lu.code.honorsOffering', null, 'da01a149-60fb-4364-850a-2628a713395a')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('cc95bd3e-2c96-4726-a67b-829f12447595', 0, 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'admin', TIMESTAMP '2014-07-01 09:36:39.293', TIMESTAMP '2014-07-01 09:36:39.673', '', 'CHEM232S-FO-AO', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM232S-FO-AO', '030e8905-94ae-4f0c-9dbd-f0b39aa81ff0', 'ffdfebf5-078a-4e7d-ab41-6460d4254f6d')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='e81b1796-b762-4cc8-90b0-b398bfd4f9b2', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:36:39.293', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='4a11821e-f4f8-4624-b232-236c171c7129', NAME='2', PRIVATE_NAME='2' where ID='e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7' and VER_NBR=2
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('9061d415-489b-419e-96a3-4a34da4b35cf', '030e8905-94ae-4f0c-9dbd-f0b39aa81ff0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('7f4c9f7d-0455-4fcf-a7ed-9b45da5a79a5', 0, 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'kuali.atp.2014Spring', 'cf596c5d-a14b-4485-9bcb-5a9fc53f8927', '', '', '2001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2001', '2001', '', '1481af5c-8633-4a1f-86ce-bd75b6d0a62e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9d5b35de-edde-488a-ad73-0de2dd17327e', 'kuali.attribute.registration.group.aocluster.id', '1481af5c-8633-4a1f-86ce-bd75b6d0a62e', 'e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7', '96931e64-55d7-40bf-a80d-179103784dac')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('153ead70-9e52-44d3-bfc4-ab98f0e5c332', 'kuali.attribute.registration.group.is.generated', '1481af5c-8633-4a1f-86ce-bd75b6d0a62e', 1, '191b1a0a-7c29-47c7-9973-f18a03d94c77')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('880054bd-669d-4d20-a8ee-4cfd73783588', 0, 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'admin', TIMESTAMP '2014-07-01 09:36:39.293', '', '', '', '', '1481af5c-8633-4a1f-86ce-bd75b6d0a62e', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '1fc15212-8683-4624-8361-2286b2e5c305')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('57d11cce-7b90-4251-93bb-438ea8254c19', 0, 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'admin', TIMESTAMP '2014-07-01 09:36:39.293', TIMESTAMP '2014-07-01 09:36:40.25', '', 'CHEM232S-FO-RG', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM232S-FO-RG', '1481af5c-8633-4a1f-86ce-bd75b6d0a62e', '13a1a1cb-0a31-4eee-bdda-a8fb0fdefa97')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('54134c0e-e2c7-4795-be6d-6d4a58c9a53c', 0, 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'admin', TIMESTAMP '2014-07-01 09:36:39.293', TIMESTAMP '2014-07-01 09:36:40.317', '', 'CHEM232S-RG-AO', '1481af5c-8633-4a1f-86ce-bd75b6d0a62e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM232S-RG-AO', '030e8905-94ae-4f0c-9dbd-f0b39aa81ff0', '9de05a60-1068-4f67-818a-eb5de39ecf29')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('c77541ff-11a7-4af5-8ad8-0b9388185e3b', 0, 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'kuali.atp.2014Spring', 'cf596c5d-a14b-4485-9bcb-5a9fc53f8927', '', '', '2002', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2002', '2002', '', 'cecb403c-2a71-4940-bbc6-f3cab758747e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8cfa7eee-ef5c-4ebe-9957-c6a66d32fb0b', 'kuali.attribute.registration.group.is.generated', 'cecb403c-2a71-4940-bbc6-f3cab758747e', 1, 'bfbaf08a-4cf1-4090-b1a7-6ec03c647b29')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('91e6822d-b32d-44ef-bc56-13580f9e2904', 'kuali.attribute.registration.group.aocluster.id', 'cecb403c-2a71-4940-bbc6-f3cab758747e', 'e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7', '76c62f09-ea7c-4d6f-8c72-cf88877e28d3')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('e9436466-52a3-42c1-b443-f23f92761f9a', 0, 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'admin', TIMESTAMP '2014-07-01 09:36:39.293', '', '', '', '', 'cecb403c-2a71-4940-bbc6-f3cab758747e', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '93c2d74b-3c2b-46b1-8d73-6e14926e9809')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('b19dad09-e0c9-4bf5-bb00-48f8a207a539', 0, 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'admin', TIMESTAMP '2014-07-01 09:36:39.293', TIMESTAMP '2014-07-01 09:36:40.711', '', 'CHEM232S-FO-RG', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM232S-FO-RG', 'cecb403c-2a71-4940-bbc6-f3cab758747e', 'b3364044-49ae-4a5d-8a66-ffea70a8fe71')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('db23787c-8fca-4a20-9798-b402c9c09466', 0, 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'admin', TIMESTAMP '2014-07-01 09:36:39.293', TIMESTAMP '2014-07-01 09:36:40.76', '', 'CHEM232S-RG-AO', 'cecb403c-2a71-4940-bbc6-f3cab758747e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM232S-RG-AO', 'db3e8b36-fe91-4ab6-8e31-0f795d88149a', '27bbea01-909d-4f83-b73e-a30bfccf947e')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('d8ecee26-0f24-470a-971d-173ab7a1b343', 0, 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'admin', TIMESTAMP '2014-07-01 09:36:39.293', TIMESTAMP '2014-07-01 09:36:40.828', '', 'CHEM232S-RG-AO', 'cecb403c-2a71-4940-bbc6-f3cab758747e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM232S-RG-AO', '030e8905-94ae-4f0c-9dbd-f0b39aa81ff0', 'ccd8902e-e705-4871-aac7-38c187c1494f')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('d5e2b717-c43b-4a4c-bb89-5c022148a0bd', 0, 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 'admin', TIMESTAMP '2014-07-01 09:36:39.293', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '4cfa0025-c4b3-4daf-9ff9-8f44edb9f427')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('4cfa0025-c4b3-4daf-9ff9-8f44edb9f427', '030e8905-94ae-4f0c-9dbd-f0b39aa81ff0')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('4cfa0025-c4b3-4daf-9ff9-8f44edb9f427', '4a11821e-f4f8-4624-b232-236c171c7129')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('345df0be-7d0f-4dd1-8fd6-a2062907ccdc', '1e07606f-29f3-4dc7-82a5-ebe8442408db', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300-P', '3c725dc2-26ce-4ab6-b521-1b2fb1ec5ee7')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('9f85a0ed-19e5-4dc9-bf71-d5fd498a3272', 'I', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300', '3e937bb0-bff2-4ef8-85a4-12196f328eb8')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='1e07606f-29f3-4dc7-82a5-ebe8442408db' and UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ea5386cf-9bb1-4d80-a9bc-efe81d8aac2d', 0, 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 'kuali.atp.2014Spring', '50f30135-2d2e-4bd5-afcd-1f697d38174a', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lab', '', '', 'CHEM232S AO', '', '', 'a1e9a112-c342-4544-84a0-5bedcc44e707')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9138308a-6a4e-4390-8eec-351ddd132811', 'kuali.attribute.max.enrollment.is.estimate', 'a1e9a112-c342-4544-84a0-5bedcc44e707', null, '2704fdd0-55bb-42ec-8305-43895708dc86')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('601842b3-bec7-4e85-b869-a5e2d59c99f4', 'kuali.attribute.course.evaluation.indicator', 'a1e9a112-c342-4544-84a0-5bedcc44e707', null, '28bd0758-908b-48ca-b7f2-7536fabee417')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b0877f10-a6ed-4ecf-b49b-c5a036e5ec16', 'kuali.attribute.nonstd.ts.indicator', 'a1e9a112-c342-4544-84a0-5bedcc44e707', 0, '2763c688-05ed-4f39-afa2-9c78c4a97fd2')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('58968f61-ac4f-4ca6-a6d2-0f4e26164a17', 0, 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 'I', '', '', '', 'a1e9a112-c342-4544-84a0-5bedcc44e707', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'acac24ae-da9a-430b-b5cf-16c0ebd0c8ea')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('817c7dae-abce-48f9-8039-0874c771d0ab', 0, 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 'admin', TIMESTAMP '2014-07-01 09:37:03.595', '', 'a1e9a112-c342-4544-84a0-5bedcc44e707', '', 'kuali.lu.code.honorsOffering', null, '5495a06f-dfb4-46cc-bd6a-038cc853ba61')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('08688edf-b542-45f2-a1bd-e16705dd44bf', 0, 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 'admin', TIMESTAMP '2014-07-01 09:37:03.595', TIMESTAMP '2014-07-01 09:37:03.977', '', 'CHEM232S-FO-AO', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM232S-FO-AO', 'a1e9a112-c342-4544-84a0-5bedcc44e707', 'cfec95f0-07ab-4dae-8051-e4439f968ac7')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='e81b1796-b762-4cc8-90b0-b398bfd4f9b2', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:37:03.595', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='4a11821e-f4f8-4624-b232-236c171c7129', NAME='2', PRIVATE_NAME='2' where ID='e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7' and VER_NBR=3
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('9061d415-489b-419e-96a3-4a34da4b35cf', 'a1e9a112-c342-4544-84a0-5bedcc44e707')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('12a54eb2-4b68-42f6-ae82-08f70b97d8d6', 0, 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 'kuali.atp.2014Spring', 'cf596c5d-a14b-4485-9bcb-5a9fc53f8927', '', '', '2003', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2003', '2003', '', '1a16844d-4dbb-47fa-8ab6-313b4fd5906a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0bdae71e-f3b1-4941-a385-bc548bfbe946', 'kuali.attribute.registration.group.is.generated', '1a16844d-4dbb-47fa-8ab6-313b4fd5906a', 1, '4aefbf6c-873d-4325-a41c-1eefc179e685')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d712cf50-7bcf-403e-a295-3dcbce599450', 'kuali.attribute.registration.group.aocluster.id', '1a16844d-4dbb-47fa-8ab6-313b4fd5906a', 'e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7', 'f81beabb-9590-4164-9483-28be5badffde')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('763571e7-f297-4781-a22d-74eda81d4fb4', 0, 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 'admin', TIMESTAMP '2014-07-01 09:37:03.595', '', '', '', '', '1a16844d-4dbb-47fa-8ab6-313b4fd5906a', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '3bc20288-1dc9-4e67-a753-c7a15e134396')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('40df2acc-cf10-4b8d-9a75-b14728fce818', 0, 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 'admin', TIMESTAMP '2014-07-01 09:37:03.595', TIMESTAMP '2014-07-01 09:37:04.511', '', 'CHEM232S-FO-RG', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM232S-FO-RG', '1a16844d-4dbb-47fa-8ab6-313b4fd5906a', '8d41fd13-ce03-43f3-bcd4-083a9db2ae6e')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('eb75ecbc-0eed-43ee-8dc3-c26494a84d19', 0, 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 'admin', TIMESTAMP '2014-07-01 09:37:03.595', TIMESTAMP '2014-07-01 09:37:04.602', '', 'CHEM232S-RG-AO', '1a16844d-4dbb-47fa-8ab6-313b4fd5906a', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM232S-RG-AO', 'a1e9a112-c342-4544-84a0-5bedcc44e707', '45f1956e-e6fa-4ec3-8367-6afed26ac4dc')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('bb322894-a115-4a51-8602-b9f6093cf163', 0, 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 'kuali.atp.2014Spring', 'cf596c5d-a14b-4485-9bcb-5a9fc53f8927', '', '', '2004', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2004', '2004', '', '13fc1ad0-d935-41fd-b4ae-2b684448868a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('38dfaf65-0df4-477b-8d8a-56a850ff8702', 'kuali.attribute.registration.group.is.generated', '13fc1ad0-d935-41fd-b4ae-2b684448868a', 1, '005340c1-489a-4b01-9168-dd1e82139964')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f9e9f90f-ac60-4bb5-b722-ef1be8f4a86e', 'kuali.attribute.registration.group.aocluster.id', '13fc1ad0-d935-41fd-b4ae-2b684448868a', 'e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7', 'e4bb1e13-5d42-4261-808e-474631e80e0a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a4042b51-080e-4dd8-a461-c0ea2f0f214d', 0, 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 'admin', TIMESTAMP '2014-07-01 09:37:03.595', '', '', '', '', '13fc1ad0-d935-41fd-b4ae-2b684448868a', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '88a95a59-4994-4c6d-be40-b619410a62c2')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('3bbe95a4-095c-407b-953e-d62c9b3df01c', 0, 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 'admin', TIMESTAMP '2014-07-01 09:37:03.595', TIMESTAMP '2014-07-01 09:37:04.9', '', 'CHEM232S-FO-RG', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM232S-FO-RG', '13fc1ad0-d935-41fd-b4ae-2b684448868a', '6c0597bc-a019-453c-9859-089c3aa04903')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('26bb3557-1a67-4ec0-a75e-dc1022d4b695', 0, 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 'admin', TIMESTAMP '2014-07-01 09:37:03.595', TIMESTAMP '2014-07-01 09:37:04.96', '', 'CHEM232S-RG-AO', '13fc1ad0-d935-41fd-b4ae-2b684448868a', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM232S-RG-AO', 'a1e9a112-c342-4544-84a0-5bedcc44e707', 'c220da97-5e20-4f04-8f68-f1a5067e3b9a')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('6968689c-cc05-414d-887b-ef50e984d96a', 0, 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 'admin', TIMESTAMP '2014-07-01 09:37:03.595', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', 'e3d1cb4a-75a9-4b56-a22c-4c971ebbecc1')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('e3d1cb4a-75a9-4b56-a22c-4c971ebbecc1', 'a1e9a112-c342-4544-84a0-5bedcc44e707')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('e3d1cb4a-75a9-4b56-a22c-4c971ebbecc1', '4a11821e-f4f8-4624-b232-236c171c7129')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('d277758e-5e0b-4c7b-aa7f-2d0b4cc2f6a0', '8806943b-e198-4091-b75d-7859eee96a50', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300-P', '34ecd801-fefe-465f-aeb8-009e66d5229f')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('4e54d8bb-e7a0-4d92-a9e5-fbbd3d1069a4', 'J', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300', '13060239-02cb-4f6f-8f23-97520ef081af')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='8806943b-e198-4091-b75d-7859eee96a50' and UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d3a8d1a0-d469-46a9-a3f9-d480a76c36f8', 0, 'admin', TIMESTAMP '2014-07-01 09:37:31.499', 'admin', TIMESTAMP '2014-07-01 09:37:31.499', 'kuali.atp.2014Spring', '50f30135-2d2e-4bd5-afcd-1f697d38174a', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lab', '', '', 'CHEM232S AO', '', '', 'b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4006b7e1-3426-414a-96b7-3ee3e9daf840', 'kuali.attribute.max.enrollment.is.estimate', 'b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db', null, '99da06ff-0f3d-4d7e-afbd-3ee02244d466')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f064444a-6eaf-4118-a281-443e896c7741', 'kuali.attribute.course.evaluation.indicator', 'b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db', null, '72e521c2-9309-42f5-892b-4222a5c58fe5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('50adbd6f-bb45-4aba-b854-5a69fd73f94f', 'kuali.attribute.nonstd.ts.indicator', 'b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db', 0, '11053a24-302f-4420-b4e6-4829b8ab37a7')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('3520aeaa-e8d7-4708-858d-2ab54c45c5c8', 0, 'admin', TIMESTAMP '2014-07-01 09:37:31.499', 'admin', TIMESTAMP '2014-07-01 09:37:31.499', 'J', '', '', '', 'b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '2703269e-324d-46f3-ab4b-6c02669cfcff')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('24444b61-86b1-4d2e-88c4-516d20d8b512', 0, 'admin', TIMESTAMP '2014-07-01 09:37:31.499', 'admin', TIMESTAMP '2014-07-01 09:37:31.499', '', 'b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db', '', 'kuali.lu.code.honorsOffering', null, 'cfbc4536-27e3-4301-bddd-1c79e380f0f9')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('1aa4b6ea-064f-4bd4-9095-379b3d4fd923', 0, 'admin', TIMESTAMP '2014-07-01 09:37:31.499', 'admin', TIMESTAMP '2014-07-01 09:37:31.499', TIMESTAMP '2014-07-01 09:37:31.733', '', 'CHEM232S-FO-AO', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM232S-FO-AO', 'b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db', '7af62118-da20-40f0-808f-a722a11d70df')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='e55508f7-2264-4b4b-b08b-ae6d211b0ae2', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:37:31.499', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='4a11821e-f4f8-4624-b232-236c171c7129', NAME='3', PRIVATE_NAME='3' where ID='65a41861-d49c-4d35-9cf2-b69d03c038c7' and VER_NBR=1
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('4b8aa73a-b04e-4c0b-9582-65aaf931260f', 'b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('a747e746-f94f-46ea-80d5-af375a490811', 0, 'admin', TIMESTAMP '2014-07-01 09:37:31.499', 'admin', TIMESTAMP '2014-07-01 09:37:31.499', 'kuali.atp.2014Spring', 'cf596c5d-a14b-4485-9bcb-5a9fc53f8927', '', '', '2005', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2005', '2005', '', 'b296e0ea-ad05-476b-8a01-da1ec9d6756d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4cfb6c18-8d49-48e1-aba5-af52bed0308c', 'kuali.attribute.registration.group.is.generated', 'b296e0ea-ad05-476b-8a01-da1ec9d6756d', 1, 'c78b8c64-5139-4914-8f79-4805cd98cdeb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fe0a271b-189c-47b1-af62-81559dbabf02', 'kuali.attribute.registration.group.aocluster.id', 'b296e0ea-ad05-476b-8a01-da1ec9d6756d', '65a41861-d49c-4d35-9cf2-b69d03c038c7', 'a0f60994-1250-43e8-b0b3-50e664206bdf')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('0d196de9-1ea8-4fb7-91da-2eeaf637f849', 0, 'admin', TIMESTAMP '2014-07-01 09:37:31.499', 'admin', TIMESTAMP '2014-07-01 09:37:31.499', '', '', '', '', 'b296e0ea-ad05-476b-8a01-da1ec9d6756d', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '5c123812-8128-418b-a8fe-48af53619ed8')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('9f0285d9-8d78-4127-a3b8-3d2e0f6d622b', 0, 'admin', TIMESTAMP '2014-07-01 09:37:31.499', 'admin', TIMESTAMP '2014-07-01 09:37:31.499', TIMESTAMP '2014-07-01 09:37:32.807', '', 'CHEM232S-FO-RG', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM232S-FO-RG', 'b296e0ea-ad05-476b-8a01-da1ec9d6756d', 'b8175ed5-60d8-48aa-8d53-4b8efe1746b4')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('422d7203-e9fe-435c-97cf-904a0842597f', 0, 'admin', TIMESTAMP '2014-07-01 09:37:31.499', 'admin', TIMESTAMP '2014-07-01 09:37:31.499', TIMESTAMP '2014-07-01 09:37:32.902', '', 'CHEM232S-RG-AO', 'b296e0ea-ad05-476b-8a01-da1ec9d6756d', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM232S-RG-AO', 'b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db', '818cb188-69a5-4294-b972-8f08022421fb')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('ec6ab4c8-70d9-4289-a48a-a4ec9fff4e27', 0, 'admin', TIMESTAMP '2014-07-01 09:37:31.499', 'admin', TIMESTAMP '2014-07-01 09:37:31.499', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '069a8602-f19c-4837-8476-d618ef4011ec')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('069a8602-f19c-4837-8476-d618ef4011ec', 'b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('069a8602-f19c-4837-8476-d618ef4011ec', '4a11821e-f4f8-4624-b232-236c171c7129')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('5ccdb321-dbde-4b5d-aedd-79d619ed5991', '1ef9bd27-068d-4358-9d69-398cfe5ccff0', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300-P', '6e1fb3b4-43b1-4fad-9e9a-638d7b3a0584')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('5cd9c000-61b1-487a-bc3c-5102e6e449cd', 'K', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300', '35b66032-9aba-4604-8db5-d6e7ac5c0bcc')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='1ef9bd27-068d-4358-9d69-398cfe5ccff0' and UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('e95aa333-57e4-47ee-a3a0-4026dd1d3283', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'kuali.atp.2014Spring', '50f30135-2d2e-4bd5-afcd-1f697d38174a', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lab', '', '', 'CHEM232S AO', '', '', '8eb799c0-6415-4591-9e3a-c9ca77c01e9f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('df47ab1e-c037-4ef4-8e5b-70291099b9ed', 'kuali.attribute.course.evaluation.indicator', '8eb799c0-6415-4591-9e3a-c9ca77c01e9f', null, 'f02e77be-adcc-42da-89fe-2471381e3693')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fb3866b1-f09e-4a01-bede-a60ce00a8596', 'kuali.attribute.nonstd.ts.indicator', '8eb799c0-6415-4591-9e3a-c9ca77c01e9f', 0, 'ee80839e-bea4-4239-9ea7-0f7b1ab0296a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ba8cec5b-4b9d-40fa-9d81-95281139752e', 'kuali.attribute.max.enrollment.is.estimate', '8eb799c0-6415-4591-9e3a-c9ca77c01e9f', null, 'bf04a6ea-bf94-40e4-a57f-489054c71f23')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('fbf69c62-06fe-49dd-a674-416e2354b358', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'K', '', '', '', '8eb799c0-6415-4591-9e3a-c9ca77c01e9f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '2b59522a-25a8-4b19-83e8-946ca9f4220a')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('70241ec5-767a-484f-aa00-d9b070301dfb', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', '', '8eb799c0-6415-4591-9e3a-c9ca77c01e9f', '', 'kuali.lu.code.honorsOffering', null, '1b0f9ef1-ea01-4217-94e7-7c951a1a5b78')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('9a9981f1-3b3a-4bf2-b388-67b64d20ae83', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', TIMESTAMP '2014-07-01 09:37:56.925', '', 'CHEM232S-FO-AO', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM232S-FO-AO', '8eb799c0-6415-4591-9e3a-c9ca77c01e9f', '84e78abb-b044-4908-adfd-8a40f1fa4689')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='e81b1796-b762-4cc8-90b0-b398bfd4f9b2', VER_NBR=5, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:37:56.553', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='4a11821e-f4f8-4624-b232-236c171c7129', NAME='2', PRIVATE_NAME='2' where ID='e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7' and VER_NBR=4
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('9061d415-489b-419e-96a3-4a34da4b35cf', '8eb799c0-6415-4591-9e3a-c9ca77c01e9f')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ef74e936-2056-4216-b4a3-d12711920eb0', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'kuali.atp.2014Spring', 'cf596c5d-a14b-4485-9bcb-5a9fc53f8927', '', '', '2006', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2006', '2006', '', '6d5226c1-92f0-4009-b3ed-5e5d1a8c9d61')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fd12e5d9-808e-400d-b0e1-75f477559ad6', 'kuali.attribute.registration.group.aocluster.id', '6d5226c1-92f0-4009-b3ed-5e5d1a8c9d61', 'e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7', '0ef77542-bad4-4a86-92a3-0673ed2324f0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d274ea4e-953e-4ac4-be3b-9a74d78a0067', 'kuali.attribute.registration.group.is.generated', '6d5226c1-92f0-4009-b3ed-5e5d1a8c9d61', 1, '6e37a7a5-4064-408b-a1d4-f577a25f98d1')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('60203ce4-340d-402a-99bf-0e2043bda550', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', '', '', '', '', '6d5226c1-92f0-4009-b3ed-5e5d1a8c9d61', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '9b084bfb-dc73-4130-a771-0066eb0c5c7b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('4e2cf75b-c9ad-4c6f-b38c-73e75b28d817', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', TIMESTAMP '2014-07-01 09:37:58.035', '', 'CHEM232S-FO-RG', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM232S-FO-RG', '6d5226c1-92f0-4009-b3ed-5e5d1a8c9d61', '714a108f-1ac9-4d17-b5a0-0de387067347')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('677b55ab-6fab-4ae2-9fd9-19c3042a3a3c', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', TIMESTAMP '2014-07-01 09:37:58.129', '', 'CHEM232S-RG-AO', '6d5226c1-92f0-4009-b3ed-5e5d1a8c9d61', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM232S-RG-AO', '8eb799c0-6415-4591-9e3a-c9ca77c01e9f', '6255fdad-b1f5-4e39-b183-eb8f99fcc315')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('123ddfaa-5b70-4422-bc73-ff0a4a235b3c', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'kuali.atp.2014Spring', 'cf596c5d-a14b-4485-9bcb-5a9fc53f8927', '', '', '2007', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2007', '2007', '', 'ec611dde-1aef-47b8-8d04-4c1a1a475679')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b881beca-55d0-4be4-b4f4-bab132ef2851', 'kuali.attribute.registration.group.is.generated', 'ec611dde-1aef-47b8-8d04-4c1a1a475679', 1, 'e048aeae-621c-45c6-9322-96767ad1d742')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6b2a026f-54ce-48b1-a2a9-264f68041bc9', 'kuali.attribute.registration.group.aocluster.id', 'ec611dde-1aef-47b8-8d04-4c1a1a475679', 'e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7', '1b523f1b-a6ae-4d76-9e16-f67dd30fa83d')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('0246ded1-59ab-438c-839c-68d7d84f9d97', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', '', '', '', '', 'ec611dde-1aef-47b8-8d04-4c1a1a475679', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'afce07f7-e5da-4b27-bcf3-6e2bd3e79d1e')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('aedf38e8-8912-475b-8366-2075c0f26650', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', TIMESTAMP '2014-07-01 09:37:58.486', '', 'CHEM232S-FO-RG', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM232S-FO-RG', 'ec611dde-1aef-47b8-8d04-4c1a1a475679', 'addbf2ac-7030-4260-ba1d-f68af8c45763')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('95fc5776-1751-4aaa-9a22-dc61db819448', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', TIMESTAMP '2014-07-01 09:37:58.551', '', 'CHEM232S-RG-AO', 'ec611dde-1aef-47b8-8d04-4c1a1a475679', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM232S-RG-AO', '8eb799c0-6415-4591-9e3a-c9ca77c01e9f', '967b9d74-2188-4c01-b714-35c1fa1a10a3')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('998b49f7-8b0f-47e1-ad0e-f05e9f97f351', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '801d5b0b-40a8-4baf-8903-2f3a05764d64')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('801d5b0b-40a8-4baf-8903-2f3a05764d64', '8eb799c0-6415-4591-9e3a-c9ca77c01e9f')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('801d5b0b-40a8-4baf-8903-2f3a05764d64', '4a11821e-f4f8-4624-b232-236c171c7129')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('3e2253d8-67df-4cc2-a137-57e91581f8e9', 'aaded187-ca81-4319-adfd-f31b68ef1b36', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300-P', '8f300712-6878-4d76-84b4-273d4a4e5721')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('0ba8f65a-facb-4928-a19f-9993d670c8bc', 'L', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300', '0db92d3e-d894-49f6-8ea4-8bebb4de4dd1')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='aaded187-ca81-4319-adfd-f31b68ef1b36' and UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('a5070210-a070-46ca-8afb-2874be26ae11', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'kuali.atp.2014Spring', '50f30135-2d2e-4bd5-afcd-1f697d38174a', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lab', '', '', 'CHEM232S AO', '', '', '22689825-e32c-4184-869b-c4a30868ec5a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('889dc812-a3e0-4388-be26-22a3b09dd588', 'kuali.attribute.nonstd.ts.indicator', '22689825-e32c-4184-869b-c4a30868ec5a', 0, 'c50587a9-8ec6-4b77-bbab-439584822ffd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4bc13054-30cb-455b-994d-319907761b9c', 'kuali.attribute.max.enrollment.is.estimate', '22689825-e32c-4184-869b-c4a30868ec5a', null, 'f021a15a-875c-444b-be00-a3c5853d47e7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b36f862f-dfee-4d2e-a8a0-8209ba4a3d4d', 'kuali.attribute.course.evaluation.indicator', '22689825-e32c-4184-869b-c4a30868ec5a', null, 'e8f7aaea-3bf7-4de8-9097-17016aa3c9ec')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('e74b3c42-4320-4c0a-bee7-04f0eb1c4633', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'L', '', '', '', '22689825-e32c-4184-869b-c4a30868ec5a', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a7693a0e-a608-4960-82eb-4b92cdec6e99')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('5976a61b-a73a-467e-b438-60eecf1721cc', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', '', '22689825-e32c-4184-869b-c4a30868ec5a', '', 'kuali.lu.code.honorsOffering', null, '34d2b85a-dbfd-48ec-9cf1-7a0a9a7c7f11')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a74c6088-c029-4a4e-8b23-ebeeb7251738', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', TIMESTAMP '2014-07-01 09:37:59.534', '', 'CHEM232S-FO-AO', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM232S-FO-AO', '22689825-e32c-4184-869b-c4a30868ec5a', '70c88e5e-bbed-4b00-ae64-023606f4cfd7')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='e81b1796-b762-4cc8-90b0-b398bfd4f9b2', VER_NBR=6, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:37:56.553', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='4a11821e-f4f8-4624-b232-236c171c7129', NAME='2', PRIVATE_NAME='2' where ID='e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7' and VER_NBR=5
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('9061d415-489b-419e-96a3-4a34da4b35cf', '22689825-e32c-4184-869b-c4a30868ec5a')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d8c14f0e-9d83-4870-a15e-c7c7e2dfda75', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'kuali.atp.2014Spring', 'cf596c5d-a14b-4485-9bcb-5a9fc53f8927', '', '', '2008', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2008', '2008', '', 'd47e06a5-44e8-47fc-a8c7-11a9e9f774b2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('23e5865c-9fa1-42de-91d4-9dd7f6c750c7', 'kuali.attribute.registration.group.is.generated', 'd47e06a5-44e8-47fc-a8c7-11a9e9f774b2', 1, 'ac6cbc30-f673-439c-bb4a-af9c5a0b632a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f19d798a-c2fc-4d67-b715-2192da78b2f1', 'kuali.attribute.registration.group.aocluster.id', 'd47e06a5-44e8-47fc-a8c7-11a9e9f774b2', 'e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7', '0b213d67-75fe-4ce6-a88d-a7ccca916b9a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('e581a61d-d51f-47c2-8c96-a3eb0d7dc810', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', '', '', '', '', 'd47e06a5-44e8-47fc-a8c7-11a9e9f774b2', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'fcd412bc-6c1f-4ca6-9288-59cc2da74d66')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('982d101b-3aaa-4103-9752-d315ff0dced6', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', TIMESTAMP '2014-07-01 09:38:00.532', '', 'CHEM232S-FO-RG', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM232S-FO-RG', 'd47e06a5-44e8-47fc-a8c7-11a9e9f774b2', 'ba041bca-67e4-4c14-b5d0-cc2c93422e5a')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('fec0a09a-362d-4c77-9cbe-8f34a88083f2', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', TIMESTAMP '2014-07-01 09:38:00.601', '', 'CHEM232S-RG-AO', 'd47e06a5-44e8-47fc-a8c7-11a9e9f774b2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM232S-RG-AO', '22689825-e32c-4184-869b-c4a30868ec5a', 'f539342f-6770-4514-b2c5-532ecb9f4bf4')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('916f3cdd-1f87-4e94-adf7-d11fc03fd9ed', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'kuali.atp.2014Spring', 'cf596c5d-a14b-4485-9bcb-5a9fc53f8927', '', '', '2009', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2009', '2009', '', 'b7f50fa2-d758-48bb-a4f8-77a0757419ce')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cc747ad4-a3dc-4eab-8cbb-645887e1068b', 'kuali.attribute.registration.group.aocluster.id', 'b7f50fa2-d758-48bb-a4f8-77a0757419ce', 'e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7', '90447ecb-eea0-4ce2-8cc7-639f6fc00aab')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7548e406-d641-492e-861a-4ae32c7e5d79', 'kuali.attribute.registration.group.is.generated', 'b7f50fa2-d758-48bb-a4f8-77a0757419ce', 1, '34340029-632c-455e-8126-c938e0149ada')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('e7d34dc9-3069-4e82-8041-c7161a7b3fcd', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', '', '', '', '', 'b7f50fa2-d758-48bb-a4f8-77a0757419ce', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '5e4e2daa-4234-4dfc-8c45-798b4297bdb1')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('8d75fa69-7233-49ff-b439-6e28af5a068f', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', TIMESTAMP '2014-07-01 09:38:00.998', '', 'CHEM232S-FO-RG', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM232S-FO-RG', 'b7f50fa2-d758-48bb-a4f8-77a0757419ce', '467b8d5d-35d4-40ac-940f-41f3ad5f907d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('71a670b5-3a8f-4b6f-ad94-94019127ddf4', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', TIMESTAMP '2014-07-01 09:38:01.096', '', 'CHEM232S-RG-AO', 'b7f50fa2-d758-48bb-a4f8-77a0757419ce', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM232S-RG-AO', '22689825-e32c-4184-869b-c4a30868ec5a', '8b16415e-fdaf-4cdc-a671-e089dc1c761a')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('432c6ac0-52ee-43af-8f76-c6817cb50e4a', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', 'c9dac172-8f12-4be0-b5cb-3f3e5234dfa8')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('c9dac172-8f12-4be0-b5cb-3f3e5234dfa8', '22689825-e32c-4184-869b-c4a30868ec5a')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('c9dac172-8f12-4be0-b5cb-3f3e5234dfa8', '4a11821e-f4f8-4624-b232-236c171c7129')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('39b92321-2414-4b2a-950f-ae69f5d2df52', 'b8583e7e-0e4a-4271-a855-1465f6aa2a57', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300-P', '6164b893-fe0d-4bdf-a1ce-72245a862d14')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('2501a7d9-d63c-4b0b-a6c7-36d55971d369', 'M', 'activityOfferingCode', 'a768dc4d-167a-4fd8-80d4-1fe880a78300', '09f0609c-f41f-4ea9-b9c9-d24bbe29361e')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='b8583e7e-0e4a-4271-a855-1465f6aa2a57' and UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='a768dc4d-167a-4fd8-80d4-1fe880a78300-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ef736749-d061-4955-9010-89dfa58dc2e2', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'kuali.atp.2014Spring', '50f30135-2d2e-4bd5-afcd-1f697d38174a', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lab', '', '', 'CHEM232S AO', '', '', '46efc2ec-89bd-470c-94df-28437f17c83c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('aa0bdd65-9d56-47da-b089-a25c34c16dcf', 'kuali.attribute.max.enrollment.is.estimate', '46efc2ec-89bd-470c-94df-28437f17c83c', null, 'c8a4bccd-86b4-46f8-b436-8657b1bcbdac')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fb41145c-8f7c-4597-9ef0-08f192e0ebf9', 'kuali.attribute.course.evaluation.indicator', '46efc2ec-89bd-470c-94df-28437f17c83c', null, 'b64813c3-f163-40ff-ab43-74622b5b6ba0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('18143c9a-b453-470b-834e-06c799ea6e53', 'kuali.attribute.nonstd.ts.indicator', '46efc2ec-89bd-470c-94df-28437f17c83c', 0, '6b0b3ce9-480a-4134-a66e-9ce3fc6bf769')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('3070cd3c-0855-4041-870b-e8ab3a171b27', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'M', '', '', '', '46efc2ec-89bd-470c-94df-28437f17c83c', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'e278d453-82d6-4120-9ad7-58fcd2a2423c')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('68cd700c-6df9-403e-a7fc-f66780cb58f3', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', '', '46efc2ec-89bd-470c-94df-28437f17c83c', '', 'kuali.lu.code.honorsOffering', null, '0225610a-5a71-440e-b2e8-0f109787f67f')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ebe368ef-5b3d-4957-b5e4-ca95cdd3ffa5', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', TIMESTAMP '2014-07-01 09:38:01.886', '', 'CHEM232S-FO-AO', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM232S-FO-AO', '46efc2ec-89bd-470c-94df-28437f17c83c', '221f9d7e-5a63-48ac-8e11-4053461318f7')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='e81b1796-b762-4cc8-90b0-b398bfd4f9b2', VER_NBR=7, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:37:56.553', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='4a11821e-f4f8-4624-b232-236c171c7129', NAME='2', PRIVATE_NAME='2' where ID='e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7' and VER_NBR=6
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('9061d415-489b-419e-96a3-4a34da4b35cf', '46efc2ec-89bd-470c-94df-28437f17c83c')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('6599f6d0-1a4e-4908-999b-3005c9289ee4', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'kuali.atp.2014Spring', 'cf596c5d-a14b-4485-9bcb-5a9fc53f8927', '', '', '2010', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2010', '2010', '', '7317fa63-c4c5-45ab-b968-6908f9e89809')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d5e20895-c67a-448e-82f8-0d1a4d6383a8', 'kuali.attribute.registration.group.is.generated', '7317fa63-c4c5-45ab-b968-6908f9e89809', 1, '1282f97b-47a5-4c07-8312-c491976f38c6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('22802635-7ae9-47d4-90e1-de4f5e9f90b1', 'kuali.attribute.registration.group.aocluster.id', '7317fa63-c4c5-45ab-b968-6908f9e89809', 'e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7', '5e254a68-716d-4fc1-ba2d-1cea17eaa0f1')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('80076a0b-aede-4a6f-9274-378faa86b366', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', '', '', '', '', '7317fa63-c4c5-45ab-b968-6908f9e89809', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a3eac8ae-f1ec-4e3a-b377-5d81064c6ebd')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('dcb086f9-5c2a-456d-9f94-e91b582c0f7c', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', TIMESTAMP '2014-07-01 09:38:02.984', '', 'CHEM232S-FO-RG', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM232S-FO-RG', '7317fa63-c4c5-45ab-b968-6908f9e89809', '26df72d5-6c43-49d9-8c81-9d41ee69d4dc')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('16a642ad-65ce-4e20-ba09-d40c879b9033', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', TIMESTAMP '2014-07-01 09:38:03.057', '', 'CHEM232S-RG-AO', '7317fa63-c4c5-45ab-b968-6908f9e89809', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM232S-RG-AO', '46efc2ec-89bd-470c-94df-28437f17c83c', '9abe2b57-35a0-4042-b27a-8ff3f66abb43')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('e7e2add0-ee51-4c66-b5e0-bc8d1f6162ed', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'kuali.atp.2014Spring', 'cf596c5d-a14b-4485-9bcb-5a9fc53f8927', '', '', '2011', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2011', '2011', '', 'f3e634b3-98c5-4fdf-850f-74e5100ed7ba')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fe8106d3-d5db-4ed1-a911-c71b129be321', 'kuali.attribute.registration.group.is.generated', 'f3e634b3-98c5-4fdf-850f-74e5100ed7ba', 1, '65b31196-9937-4933-88e2-4298c89d1c5a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('44e5428d-a910-471a-a81a-fceb4aceadcf', 'kuali.attribute.registration.group.aocluster.id', 'f3e634b3-98c5-4fdf-850f-74e5100ed7ba', 'e48d4ee3-a602-4d58-aa9b-5be7dc5b45f7', 'deafadf3-c94e-453c-b4b9-520117ad4533')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d0cb1e7b-0324-44f6-a572-ec564da2fe49', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', '', '', '', '', 'f3e634b3-98c5-4fdf-850f-74e5100ed7ba', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '2cb4bd78-53c7-4871-be12-0d3c16d57e22')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('61368dbe-4795-4627-a1d9-f303a5fcd8c5', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', TIMESTAMP '2014-07-01 09:38:03.355', '', 'CHEM232S-FO-RG', '4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM232S-FO-RG', 'f3e634b3-98c5-4fdf-850f-74e5100ed7ba', '12ba2f96-4384-413a-a63c-de215338a6ce')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('07b8154c-126b-4486-aed8-6d40b7043bad', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', TIMESTAMP '2014-07-01 09:38:03.419', '', 'CHEM232S-RG-AO', 'f3e634b3-98c5-4fdf-850f-74e5100ed7ba', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM232S-RG-AO', '46efc2ec-89bd-470c-94df-28437f17c83c', '88060fd2-4910-490c-ac40-cd7ce51b5676')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('0aac46c8-010d-416f-a4b6-73f814636d9d', 0, 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 'admin', TIMESTAMP '2014-07-01 09:37:56.553', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '633dd4fb-94bd-4d48-9923-df00fc002295')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('633dd4fb-94bd-4d48-9923-df00fc002295', '46efc2ec-89bd-470c-94df-28437f17c83c')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('633dd4fb-94bd-4d48-9923-df00fc002295', '4a11821e-f4f8-4624-b232-236c171c7129')
/
update KSEN_LUI set OBJ_ID='336bac6e-a744-4bf6-830f-18806a700981', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', ATP_ID='kuali.atp.2014Spring', CLU_ID='14b8a6c2-8074-419e-be8c-d200a4537969', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS='', MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='2d87be27-1d4d-4866-b161-6725de375f2a' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='327270ef-86b6-4b93-8fd9-f0b0e6aa06da', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', LUI_CD='E', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='2d87be27-1d4d-4866-b161-6725de375f2a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='fdb806fb-894a-4a0f-a24b-17f0954c889a' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='89be3460-4806-4038-b3eb-effe73422221', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', DESCR_FORMATTED='', LUI_ID='2d87be27-1d4d-4866-b161-6725de375f2a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='318c2d5a-31e1-43e6-9297-89ce7052340b' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='741a59e9-a5d4-4130-b2c2-e717c9b3f6ab', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture/Lab', LUI_STATE='kuali.lui.format.offering.state.planned', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture/Lab', DESCR_PLAIN='Courses with Lecture/Lab', REF_URL='' where ID='4a11821e-f4f8-4624-b232-236c171c7129' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='5978561d-a4bf-4853-b526-011003f3d8df', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture/Lab', LUI_ID='4a11821e-f4f8-4624-b232-236c171c7129', ORGID='', SHRT_NAME='LEC/LAB', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='6b653923-f719-4d49-8ff6-2f6fee0262b6' and VER_NBR=0
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='4a11821e-f4f8-4624-b232-236c171c7129'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.type.activity.offering.lab')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='14ad0153-4d97-4499-9095-c4f8d5651740', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', ATP_ID='kuali.atp.2014Spring', CLU_ID='14b8a6c2-8074-419e-be8c-d200a4537969', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS='', MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='db3e8b36-fe91-4ab6-8e31-0f795d88149a' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='c729aab7-950d-4b5a-9c96-0c295a53ea41', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', LUI_CD='F', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='db3e8b36-fe91-4ab6-8e31-0f795d88149a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='c682dafa-0a99-491c-a078-f54853b56f51' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='9dda3443-21fe-44b0-b5e8-aeb6b28769b1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', DESCR_FORMATTED='', LUI_ID='db3e8b36-fe91-4ab6-8e31-0f795d88149a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='07f26f90-c7a3-4203-aad1-800cbc7893ff' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='2860f0d5-fc65-4434-9a43-a15933e79929', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', ATP_ID='kuali.atp.2014Spring', CLU_ID='14b8a6c2-8074-419e-be8c-d200a4537969', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS='', MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='af9f338a-1256-4f88-b977-8da3cb860f6a' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='19c52b2a-0aff-4c57-9c71-77ee7aa5eead', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', LUI_CD='G', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='af9f338a-1256-4f88-b977-8da3cb860f6a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='17622a72-7816-4a1b-ab31-9e9d50f6af5a' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='1b0d9a2b-9ebb-486c-9b34-2807e348779f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', DESCR_FORMATTED='', LUI_ID='af9f338a-1256-4f88-b977-8da3cb860f6a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='00a49514-7012-496d-aa8b-46f92e54927b' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='d618d44a-80d4-4192-a930-04dd401e0e5c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', ATP_ID='kuali.atp.2014Spring', CLU_ID='50f30135-2d2e-4bd5-afcd-1f697d38174a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS='', MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='030e8905-94ae-4f0c-9dbd-f0b39aa81ff0' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='940b006b-0908-4e20-9a53-81eaa5a55172', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', LUI_CD='H', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='030e8905-94ae-4f0c-9dbd-f0b39aa81ff0', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b7ba7228-07d8-488a-b7f7-ea2219452e52' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='b0c84fcd-eea7-474a-9d46-766bfcabf0e6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', DESCR_FORMATTED='', LUI_ID='030e8905-94ae-4f0c-9dbd-f0b39aa81ff0', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='da01a149-60fb-4364-850a-2628a713395a' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='ea5386cf-9bb1-4d80-a9bc-efe81d8aac2d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', ATP_ID='kuali.atp.2014Spring', CLU_ID='50f30135-2d2e-4bd5-afcd-1f697d38174a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS='', MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='a1e9a112-c342-4544-84a0-5bedcc44e707' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='58968f61-ac4f-4ca6-a6d2-0f4e26164a17', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', LUI_CD='I', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='a1e9a112-c342-4544-84a0-5bedcc44e707', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='acac24ae-da9a-430b-b5cf-16c0ebd0c8ea' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='817c7dae-abce-48f9-8039-0874c771d0ab', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', DESCR_FORMATTED='', LUI_ID='a1e9a112-c342-4544-84a0-5bedcc44e707', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='5495a06f-dfb4-46cc-bd6a-038cc853ba61' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='d3a8d1a0-d469-46a9-a3f9-d480a76c36f8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', ATP_ID='kuali.atp.2014Spring', CLU_ID='50f30135-2d2e-4bd5-afcd-1f697d38174a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS='', MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='3520aeaa-e8d7-4708-858d-2ab54c45c5c8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', LUI_CD='J', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='2703269e-324d-46f3-ab4b-6c02669cfcff' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='24444b61-86b1-4d2e-88c4-516d20d8b512', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', DESCR_FORMATTED='', LUI_ID='b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='cfbc4536-27e3-4301-bddd-1c79e380f0f9' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='e95aa333-57e4-47ee-a3a0-4026dd1d3283', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', ATP_ID='kuali.atp.2014Spring', CLU_ID='50f30135-2d2e-4bd5-afcd-1f697d38174a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS='', MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='8eb799c0-6415-4591-9e3a-c9ca77c01e9f' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='fbf69c62-06fe-49dd-a674-416e2354b358', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', LUI_CD='K', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='8eb799c0-6415-4591-9e3a-c9ca77c01e9f', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='2b59522a-25a8-4b19-83e8-946ca9f4220a' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='70241ec5-767a-484f-aa00-d9b070301dfb', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', DESCR_FORMATTED='', LUI_ID='8eb799c0-6415-4591-9e3a-c9ca77c01e9f', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='1b0f9ef1-ea01-4217-94e7-7c951a1a5b78' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='a5070210-a070-46ca-8afb-2874be26ae11', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', ATP_ID='kuali.atp.2014Spring', CLU_ID='50f30135-2d2e-4bd5-afcd-1f697d38174a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS='', MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='22689825-e32c-4184-869b-c4a30868ec5a' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='e74b3c42-4320-4c0a-bee7-04f0eb1c4633', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', LUI_CD='L', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='22689825-e32c-4184-869b-c4a30868ec5a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a7693a0e-a608-4960-82eb-4b92cdec6e99' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='5976a61b-a73a-467e-b438-60eecf1721cc', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', DESCR_FORMATTED='', LUI_ID='22689825-e32c-4184-869b-c4a30868ec5a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='34d2b85a-dbfd-48ec-9cf1-7a0a9a7c7f11' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='ef736749-d061-4955-9010-89dfa58dc2e2', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', ATP_ID='kuali.atp.2014Spring', CLU_ID='50f30135-2d2e-4bd5-afcd-1f697d38174a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS='', MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='46efc2ec-89bd-470c-94df-28437f17c83c' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='3070cd3c-0855-4041-870b-e8ab3a171b27', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', LUI_CD='M', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='46efc2ec-89bd-470c-94df-28437f17c83c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='e278d453-82d6-4120-9ad7-58fcd2a2423c' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='68cd700c-6df9-403e-a7fc-f66780cb58f3', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:38:27.618', DESCR_FORMATTED='', LUI_ID='46efc2ec-89bd-470c-94df-28437f17c83c', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='0225610a-5a71-440e-b2e8-0f109787f67f' and VER_NBR=0
/
update KSEN_LUI_ATTR set OBJ_ID='f2f21003-4b8a-49cd-b79d-143d7ced169f', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='db3e8b36-fe91-4ab6-8e31-0f795d88149a', ATTR_VALUE=0 where ID='43f62e37-e41c-4ca7-95a7-3b2e64808a29'
/
update KSEN_LUI set OBJ_ID='14ad0153-4d97-4499-9095-c4f8d5651740', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', ATP_ID='kuali.atp.2014Spring', CLU_ID='14b8a6c2-8074-419e-be8c-d200a4537969', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='db3e8b36-fe91-4ab6-8e31-0f795d88149a' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='d122eb1f-8b31-4837-8ddf-379815ff5603', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='db3e8b36-fe91-4ab6-8e31-0f795d88149a', ATTR_VALUE=0 where ID='4438aca0-f3d4-48f4-b76a-c352fc56104d'
/
update KSEN_LUI_IDENT set OBJ_ID='c729aab7-950d-4b5a-9c96-0c295a53ea41', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', LUI_CD='F', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='db3e8b36-fe91-4ab6-8e31-0f795d88149a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='c682dafa-0a99-491c-a078-f54853b56f51' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='9dda3443-21fe-44b0-b5e8-aeb6b28769b1', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', DESCR_FORMATTED='', LUI_ID='db3e8b36-fe91-4ab6-8e31-0f795d88149a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='07f26f90-c7a3-4203-aad1-800cbc7893ff' and VER_NBR=1
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('0289425b-7587-4b66-a57f-8153452a1009', 0, 'admin', TIMESTAMP '2014-07-01 09:39:21.071', 'admin', TIMESTAMP '2014-07-01 09:39:21.071', '', '', '', 'Schedule request set for CHEM232S - F', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '1ca3573f-0ad1-4f9e-b4f1-625b0dd849d0')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('1ca3573f-0ad1-4f9e-b4f1-625b0dd849d0', 'db3e8b36-fe91-4ab6-8e31-0f795d88149a')
/
insert into KSEN_SCHED_TMSLOT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, END_TIME_MS, NAME, START_TIME_MS, TM_SLOT_STATE, TM_SLOT_TYPE, WEEKDAYS, ID) values ('8d9dd391-e9f7-49a4-bdb7-dcef1b033ecd', 0, 'admin', TIMESTAMP '2014-07-01 09:39:21.071', 'admin', TIMESTAMP '2014-07-01 09:39:21.071', '', '', 0, '1723', 0, 'kuali.scheduling.timeslot.state.active', 'kuali.scheduling.time.slot.type.activityoffering.tba', '', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('1780858d-e79a-4eb6-86a7-d2e7e152896a', 0, 'admin', TIMESTAMP '2014-07-01 09:39:21.071', 'admin', TIMESTAMP '2014-07-01 09:39:21.071', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '1ca3573f-0ad1-4f9e-b4f1-625b0dd849d0', '9cbbe4bd-4d4a-4b42-a8a4-73611a64f00b')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('fa0297f1-4340-407f-a0cb-ceb87bc236b9', 1, '9cbbe4bd-4d4a-4b42-a8a4-73611a64f00b', '25d87d98-338e-4f85-9923-2adc84cf9d71')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('25d87d98-338e-4f85-9923-2adc84cf9d71', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('dab667e5-83fb-4d5a-844f-c8518d5845f5', 0, 'admin', TIMESTAMP '2014-07-01 09:39:21.071', 'admin', TIMESTAMP '2014-07-01 09:39:21.071', 'kuali.atp.2014Spring', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'ec7b4a21-5a5c-4287-b9f4-b20ea8cdcba3')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('14faf71a-8536-497f-a2d6-1c5c2b648ca4', 1, '', 'ec7b4a21-5a5c-4287-b9f4-b20ea8cdcba3', 'bc4d08f6-8582-4eed-bf05-7f12f273d63c')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('bc4d08f6-8582-4eed-bf05-7f12f273d63c', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND=1, SCHED_RQST_ID='9cbbe4bd-4d4a-4b42-a8a4-73611a64f00b' where ID='25d87d98-338e-4f85-9923-2adc84cf9d71'
/
update KSEN_SCHED_RQST set OBJ_ID='1780858d-e79a-4eb6-86a7-d2e7e152896a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='ec7b4a21-5a5c-4287-b9f4-b20ea8cdcba3', SCHED_RQST_SET_ID='1ca3573f-0ad1-4f9e-b4f1-625b0dd849d0' where ID='9cbbe4bd-4d4a-4b42-a8a4-73611a64f00b' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='25d87d98-338e-4f85-9923-2adc84cf9d71'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('25d87d98-338e-4f85-9923-2adc84cf9d71', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_LUI set OBJ_ID='14ad0153-4d97-4499-9095-c4f8d5651740', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', ATP_ID='kuali.atp.2014Spring', CLU_ID='14b8a6c2-8074-419e-be8c-d200a4537969', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='db3e8b36-fe91-4ab6-8e31-0f795d88149a' and VER_NBR=2
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('db3e8b36-fe91-4ab6-8e31-0f795d88149a', 'ec7b4a21-5a5c-4287-b9f4-b20ea8cdcba3')
/
update KSEN_LUI set OBJ_ID='14ad0153-4d97-4499-9095-c4f8d5651740', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', ATP_ID='kuali.atp.2014Spring', CLU_ID='14b8a6c2-8074-419e-be8c-d200a4537969', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='db3e8b36-fe91-4ab6-8e31-0f795d88149a' and VER_NBR=3
/
update KSEN_LUI set OBJ_ID='741a59e9-a5d4-4130-b2c2-e717c9b3f6ab', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture/Lab', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture/Lab', DESCR_PLAIN='Courses with Lecture/Lab', REF_URL='' where ID='4a11821e-f4f8-4624-b232-236c171c7129' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='5978561d-a4bf-4853-b526-011003f3d8df', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture/Lab', LUI_ID='4a11821e-f4f8-4624-b232-236c171c7129', ORGID='', SHRT_NAME='LEC/LAB', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='6b653923-f719-4d49-8ff6-2f6fee0262b6' and VER_NBR=1
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='4a11821e-f4f8-4624-b232-236c171c7129'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.type.activity.offering.lab')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('4a11821e-f4f8-4624-b232-236c171c7129', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='c77541ff-11a7-4af5-8ad8-0b9388185e3b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2002', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2002', DESCR_PLAIN='2002', REF_URL='' where ID='cecb403c-2a71-4940-bbc6-f3cab758747e' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='e9436466-52a3-42c1-b443-f23f92761f9a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='cecb403c-2a71-4940-bbc6-f3cab758747e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='93c2d74b-3c2b-46b1-8d73-6e14926e9809' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='bb322894-a115-4a51-8602-b9f6093cf163', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2004', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2004', DESCR_PLAIN='2004', REF_URL='' where ID='13fc1ad0-d935-41fd-b4ae-2b684448868a' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='a4042b51-080e-4dd8-a461-c0ea2f0f214d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='13fc1ad0-d935-41fd-b4ae-2b684448868a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='88a95a59-4994-4c6d-be40-b619410a62c2' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='123ddfaa-5b70-4422-bc73-ff0a4a235b3c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2007', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2007', DESCR_PLAIN='2007', REF_URL='' where ID='ec611dde-1aef-47b8-8d04-4c1a1a475679' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='0246ded1-59ab-438c-839c-68d7d84f9d97', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='ec611dde-1aef-47b8-8d04-4c1a1a475679', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='afce07f7-e5da-4b27-bcf3-6e2bd3e79d1e' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='916f3cdd-1f87-4e94-adf7-d11fc03fd9ed', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2009', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2009', DESCR_PLAIN='2009', REF_URL='' where ID='b7f50fa2-d758-48bb-a4f8-77a0757419ce' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='e7d34dc9-3069-4e82-8041-c7161a7b3fcd', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='b7f50fa2-d758-48bb-a4f8-77a0757419ce', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='5e4e2daa-4234-4dfc-8c45-798b4297bdb1' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='e7e2add0-ee51-4c66-b5e0-bc8d1f6162ed', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2011', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2011', DESCR_PLAIN='2011', REF_URL='' where ID='f3e634b3-98c5-4fdf-850f-74e5100ed7ba' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='d0cb1e7b-0324-44f6-a572-ec564da2fe49', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='f3e634b3-98c5-4fdf-850f-74e5100ed7ba', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='2cb4bd78-53c7-4871-be12-0d3c16d57e22' and VER_NBR=0
/
update KSEN_CWL set OBJ_ID='e953c8b2-a46a-4217-9123-fecdfe48530b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:39:21.071', ALLOW_HOLD_UTIL_ENTRIES_IND=0, AUTO_PROCESSED_IND=1, CHECK_IN_REQ_IND=0, CONF_REQ_IND=1, EFF_DT='', EXPIR_DT='', MAX_SIZE='', REG_IN_FIRST_AAO_IND='', STD_DUR_TIME_QTY='', STD_DUR_TYPE='', CWL_STATE='kuali.course.waitlist.state.inactive', CWL_TYPE='kuali.course.waitlist.type.course.waitlist' where ID='e9862f7e-574c-4467-9180-7a4d546ab9a3' and VER_NBR=0
/
update KSEN_LUI_ATTR set OBJ_ID='25978abd-d3d4-438c-a80f-7c7a90f03660', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='2d87be27-1d4d-4866-b161-6725de375f2a', ATTR_VALUE=0 where ID='5b8edbce-5788-436f-bfe7-693d6f6b2160'
/
update KSEN_LUI set OBJ_ID='336bac6e-a744-4bf6-830f-18806a700981', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:40:29.446', ATP_ID='kuali.atp.2014Spring', CLU_ID='14b8a6c2-8074-419e-be8c-d200a4537969', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='2d87be27-1d4d-4866-b161-6725de375f2a' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='4f06809b-33bb-4f9b-9533-bf0339b11ef1', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='2d87be27-1d4d-4866-b161-6725de375f2a', ATTR_VALUE=0 where ID='1903dd5a-1d4f-4cb0-8b5b-fdf7c5d8bfc4'
/
update KSEN_LUI_IDENT set OBJ_ID='327270ef-86b6-4b93-8fd9-f0b0e6aa06da', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:40:29.446', LUI_CD='E', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='2d87be27-1d4d-4866-b161-6725de375f2a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='fdb806fb-894a-4a0f-a24b-17f0954c889a' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='89be3460-4806-4038-b3eb-effe73422221', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:40:29.446', DESCR_FORMATTED='', LUI_ID='2d87be27-1d4d-4866-b161-6725de375f2a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='318c2d5a-31e1-43e6-9297-89ce7052340b' and VER_NBR=1
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('753179b3-a5a9-43d8-88ca-e0b38f37efe7', 0, 'admin', TIMESTAMP '2014-07-01 09:40:29.446', 'admin', TIMESTAMP '2014-07-01 09:40:29.446', '', '', '', 'Schedule request set for CHEM232S - E', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '4f128a20-471a-4603-938c-114d168177ae')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('4f128a20-471a-4603-938c-114d168177ae', '2d87be27-1d4d-4866-b161-6725de375f2a')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('49432963-31f0-4eae-a8bc-f1fe7f53a8bf', 0, 'admin', TIMESTAMP '2014-07-01 09:40:29.446', 'admin', TIMESTAMP '2014-07-01 09:40:29.446', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '4f128a20-471a-4603-938c-114d168177ae', 'f8471976-d2db-4000-9f1d-797300bd81e4')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('8bb717da-ca1e-4c9a-888b-31a9a3858807', 1, 'f8471976-d2db-4000-9f1d-797300bd81e4', '0147e3ff-5b6b-493a-9065-0c121efe8c1e')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('0147e3ff-5b6b-493a-9065-0c121efe8c1e', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('8ec00db2-0cae-481b-be72-1f8da167b29b', 0, 'admin', TIMESTAMP '2014-07-01 09:40:29.446', 'admin', TIMESTAMP '2014-07-01 09:40:29.446', 'kuali.atp.2014Spring', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '8fa3d7ea-845f-43f4-b1f9-303325842f39')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('2d33df3a-0e8c-4e96-9a81-5720aa1d46b8', 1, '', '8fa3d7ea-845f-43f4-b1f9-303325842f39', '7fd1ab94-f3f9-408a-883b-5f792522cab4')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('7fd1ab94-f3f9-408a-883b-5f792522cab4', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND=1, SCHED_RQST_ID='f8471976-d2db-4000-9f1d-797300bd81e4' where ID='0147e3ff-5b6b-493a-9065-0c121efe8c1e'
/
update KSEN_SCHED_RQST set OBJ_ID='49432963-31f0-4eae-a8bc-f1fe7f53a8bf', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:40:29.446', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='8fa3d7ea-845f-43f4-b1f9-303325842f39', SCHED_RQST_SET_ID='4f128a20-471a-4603-938c-114d168177ae' where ID='f8471976-d2db-4000-9f1d-797300bd81e4' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='0147e3ff-5b6b-493a-9065-0c121efe8c1e'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('0147e3ff-5b6b-493a-9065-0c121efe8c1e', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_LUI set OBJ_ID='336bac6e-a744-4bf6-830f-18806a700981', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:40:29.446', ATP_ID='kuali.atp.2014Spring', CLU_ID='14b8a6c2-8074-419e-be8c-d200a4537969', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='2d87be27-1d4d-4866-b161-6725de375f2a' and VER_NBR=2
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('2d87be27-1d4d-4866-b161-6725de375f2a', '8fa3d7ea-845f-43f4-b1f9-303325842f39')
/
update KSEN_LUI set OBJ_ID='336bac6e-a744-4bf6-830f-18806a700981', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:40:29.446', ATP_ID='kuali.atp.2014Spring', CLU_ID='14b8a6c2-8074-419e-be8c-d200a4537969', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='2d87be27-1d4d-4866-b161-6725de375f2a' and VER_NBR=3
/
update KSEN_LUI set OBJ_ID='7f4c9f7d-0455-4fcf-a7ed-9b45da5a79a5', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:40:29.446', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2001', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2001', DESCR_PLAIN='2001', REF_URL='' where ID='1481af5c-8633-4a1f-86ce-bd75b6d0a62e' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='880054bd-669d-4d20-a8ee-4cfd73783588', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:40:29.446', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='1481af5c-8633-4a1f-86ce-bd75b6d0a62e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1fc15212-8683-4624-8361-2286b2e5c305' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='12a54eb2-4b68-42f6-ae82-08f70b97d8d6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:40:29.446', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2003', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2003', DESCR_PLAIN='2003', REF_URL='' where ID='1a16844d-4dbb-47fa-8ab6-313b4fd5906a' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='763571e7-f297-4781-a22d-74eda81d4fb4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:40:29.446', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='1a16844d-4dbb-47fa-8ab6-313b4fd5906a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='3bc20288-1dc9-4e67-a753-c7a15e134396' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='ef74e936-2056-4216-b4a3-d12711920eb0', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:40:29.446', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2006', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2006', DESCR_PLAIN='2006', REF_URL='' where ID='6d5226c1-92f0-4009-b3ed-5e5d1a8c9d61' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='60203ce4-340d-402a-99bf-0e2043bda550', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:40:29.446', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='6d5226c1-92f0-4009-b3ed-5e5d1a8c9d61', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='9b084bfb-dc73-4130-a771-0066eb0c5c7b' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='d8c14f0e-9d83-4870-a15e-c7c7e2dfda75', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:40:29.446', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2008', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2008', DESCR_PLAIN='2008', REF_URL='' where ID='d47e06a5-44e8-47fc-a8c7-11a9e9f774b2' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='e581a61d-d51f-47c2-8c96-a3eb0d7dc810', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:40:29.446', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='d47e06a5-44e8-47fc-a8c7-11a9e9f774b2', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='fcd412bc-6c1f-4ca6-9288-59cc2da74d66' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='6599f6d0-1a4e-4908-999b-3005c9289ee4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:40:29.446', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2010', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2010', DESCR_PLAIN='2010', REF_URL='' where ID='7317fa63-c4c5-45ab-b968-6908f9e89809' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='80076a0b-aede-4a6f-9274-378faa86b366', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:40:29.446', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='7317fa63-c4c5-45ab-b968-6908f9e89809', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a3eac8ae-f1ec-4e3a-b377-5d81064c6ebd' and VER_NBR=0
/
update KSEN_CWL set OBJ_ID='2d6f93c6-5bdd-4d5b-95ac-0cf2c3f6286c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:40:29.446', ALLOW_HOLD_UTIL_ENTRIES_IND=0, AUTO_PROCESSED_IND=1, CHECK_IN_REQ_IND=0, CONF_REQ_IND=1, EFF_DT='', EXPIR_DT='', MAX_SIZE='', REG_IN_FIRST_AAO_IND='', STD_DUR_TIME_QTY='', STD_DUR_TYPE='', CWL_STATE='kuali.course.waitlist.state.inactive', CWL_TYPE='kuali.course.waitlist.type.course.waitlist' where ID='73f717f5-2d54-4370-a78d-aa9597704bc6' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='d618d44a-80d4-4192-a930-04dd401e0e5c', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:41:26.596', ATP_ID='kuali.atp.2014Spring', CLU_ID='50f30135-2d2e-4bd5-afcd-1f697d38174a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='030e8905-94ae-4f0c-9dbd-f0b39aa81ff0' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='3559f5eb-9e13-4259-a36f-060f10691278', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='030e8905-94ae-4f0c-9dbd-f0b39aa81ff0', ATTR_VALUE=0 where ID='3b7e80ce-899f-4cb0-96f4-1b9c82a23c9c'
/
update KSEN_LUI_ATTR set OBJ_ID='ef6e3be2-c284-4670-8c06-fd3c7e463320', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='030e8905-94ae-4f0c-9dbd-f0b39aa81ff0', ATTR_VALUE=0 where ID='afe6391b-1a24-43b3-83b9-906338773c16'
/
update KSEN_LUI_IDENT set OBJ_ID='940b006b-0908-4e20-9a53-81eaa5a55172', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:41:26.596', LUI_CD='H', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='030e8905-94ae-4f0c-9dbd-f0b39aa81ff0', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b7ba7228-07d8-488a-b7f7-ea2219452e52' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='b0c84fcd-eea7-474a-9d46-766bfcabf0e6', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:41:26.596', DESCR_FORMATTED='', LUI_ID='030e8905-94ae-4f0c-9dbd-f0b39aa81ff0', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='da01a149-60fb-4364-850a-2628a713395a' and VER_NBR=1
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('c5aedd01-da12-412e-8070-f6c976823c06', 0, 'admin', TIMESTAMP '2014-07-01 09:41:26.596', 'admin', TIMESTAMP '2014-07-01 09:41:26.596', '', '', '', 'Schedule request set for CHEM232S - H', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '330c9074-0174-4f39-8874-992db78ef885')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('330c9074-0174-4f39-8874-992db78ef885', '030e8905-94ae-4f0c-9dbd-f0b39aa81ff0')
/
update KSEN_LUI set OBJ_ID='d618d44a-80d4-4192-a930-04dd401e0e5c', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:41:26.596', ATP_ID='kuali.atp.2014Spring', CLU_ID='50f30135-2d2e-4bd5-afcd-1f697d38174a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='030e8905-94ae-4f0c-9dbd-f0b39aa81ff0' and VER_NBR=2
/
update KSEN_LUI set OBJ_ID='7f4c9f7d-0455-4fcf-a7ed-9b45da5a79a5', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:41:26.596', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2001', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2001', DESCR_PLAIN='2001', REF_URL='' where ID='1481af5c-8633-4a1f-86ce-bd75b6d0a62e' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='880054bd-669d-4d20-a8ee-4cfd73783588', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:41:26.596', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='1481af5c-8633-4a1f-86ce-bd75b6d0a62e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1fc15212-8683-4624-8361-2286b2e5c305' and VER_NBR=1
/
update KSEN_LUI set OBJ_ID='c77541ff-11a7-4af5-8ad8-0b9388185e3b', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:41:26.596', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2002', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2002', DESCR_PLAIN='2002', REF_URL='' where ID='cecb403c-2a71-4940-bbc6-f3cab758747e' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='e9436466-52a3-42c1-b443-f23f92761f9a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:41:26.596', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='cecb403c-2a71-4940-bbc6-f3cab758747e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='93c2d74b-3c2b-46b1-8d73-6e14926e9809' and VER_NBR=1
/
update KSEN_CWL set OBJ_ID='d5e2b717-c43b-4a4c-bb89-5c022148a0bd', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:41:26.596', ALLOW_HOLD_UTIL_ENTRIES_IND=0, AUTO_PROCESSED_IND=1, CHECK_IN_REQ_IND=0, CONF_REQ_IND=1, EFF_DT='', EXPIR_DT='', MAX_SIZE='', REG_IN_FIRST_AAO_IND='', STD_DUR_TIME_QTY='', STD_DUR_TYPE='', CWL_STATE='kuali.course.waitlist.state.inactive', CWL_TYPE='kuali.course.waitlist.type.course.waitlist' where ID='4cfa0025-c4b3-4daf-9ff9-8f44edb9f427' and VER_NBR=0
/
update KSEN_LUI_ATTR set OBJ_ID='aa0bdd65-9d56-47da-b089-a25c34c16dcf', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='46efc2ec-89bd-470c-94df-28437f17c83c', ATTR_VALUE=0 where ID='c8a4bccd-86b4-46f8-b436-8657b1bcbdac'
/
update KSEN_LUI set OBJ_ID='ef736749-d061-4955-9010-89dfa58dc2e2', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:42:11.353', ATP_ID='kuali.atp.2014Spring', CLU_ID='50f30135-2d2e-4bd5-afcd-1f697d38174a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='46efc2ec-89bd-470c-94df-28437f17c83c' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='fb41145c-8f7c-4597-9ef0-08f192e0ebf9', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='46efc2ec-89bd-470c-94df-28437f17c83c', ATTR_VALUE=0 where ID='b64813c3-f163-40ff-ab43-74622b5b6ba0'
/
update KSEN_LUI_IDENT set OBJ_ID='3070cd3c-0855-4041-870b-e8ab3a171b27', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:42:11.353', LUI_CD='M', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='46efc2ec-89bd-470c-94df-28437f17c83c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='e278d453-82d6-4120-9ad7-58fcd2a2423c' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='68cd700c-6df9-403e-a7fc-f66780cb58f3', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:42:11.353', DESCR_FORMATTED='', LUI_ID='46efc2ec-89bd-470c-94df-28437f17c83c', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='0225610a-5a71-440e-b2e8-0f109787f67f' and VER_NBR=1
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('ee1b1aca-7b01-4099-8e3a-8d210d71a1e1', 0, 'admin', TIMESTAMP '2014-07-01 09:42:11.353', 'admin', TIMESTAMP '2014-07-01 09:42:11.353', '', '', '', 'Schedule request set for CHEM232S - M', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '3a5657d9-eef1-4df6-8d20-2f208b59a23f')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('3a5657d9-eef1-4df6-8d20-2f208b59a23f', '46efc2ec-89bd-470c-94df-28437f17c83c')
/
update KSEN_LUI set OBJ_ID='ef736749-d061-4955-9010-89dfa58dc2e2', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:42:11.353', ATP_ID='kuali.atp.2014Spring', CLU_ID='50f30135-2d2e-4bd5-afcd-1f697d38174a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='46efc2ec-89bd-470c-94df-28437f17c83c' and VER_NBR=2
/
update KSEN_LUI set OBJ_ID='6599f6d0-1a4e-4908-999b-3005c9289ee4', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:42:11.353', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2010', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2010', DESCR_PLAIN='2010', REF_URL='' where ID='7317fa63-c4c5-45ab-b968-6908f9e89809' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='80076a0b-aede-4a6f-9274-378faa86b366', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:42:11.353', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='7317fa63-c4c5-45ab-b968-6908f9e89809', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a3eac8ae-f1ec-4e3a-b377-5d81064c6ebd' and VER_NBR=1
/
update KSEN_LUI set OBJ_ID='e7e2add0-ee51-4c66-b5e0-bc8d1f6162ed', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:42:11.353', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2011', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2011', DESCR_PLAIN='2011', REF_URL='' where ID='f3e634b3-98c5-4fdf-850f-74e5100ed7ba' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='d0cb1e7b-0324-44f6-a572-ec564da2fe49', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:42:11.353', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='f3e634b3-98c5-4fdf-850f-74e5100ed7ba', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='2cb4bd78-53c7-4871-be12-0d3c16d57e22' and VER_NBR=1
/
update KSEN_CWL set OBJ_ID='0aac46c8-010d-416f-a4b6-73f814636d9d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:42:11.353', ALLOW_HOLD_UTIL_ENTRIES_IND=0, AUTO_PROCESSED_IND=1, CHECK_IN_REQ_IND=0, CONF_REQ_IND=1, EFF_DT='', EXPIR_DT='', MAX_SIZE='', REG_IN_FIRST_AAO_IND='', STD_DUR_TIME_QTY='', STD_DUR_TYPE='', CWL_STATE='kuali.course.waitlist.state.inactive', CWL_TYPE='kuali.course.waitlist.type.course.waitlist' where ID='633dd4fb-94bd-4d48-9923-df00fc002295' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='d618d44a-80d4-4192-a930-04dd401e0e5c', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:43:29.371', ATP_ID='kuali.atp.2014Spring', CLU_ID='50f30135-2d2e-4bd5-afcd-1f697d38174a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='030e8905-94ae-4f0c-9dbd-f0b39aa81ff0' and VER_NBR=3
/
update KSEN_LUI_IDENT set OBJ_ID='940b006b-0908-4e20-9a53-81eaa5a55172', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:43:29.371', LUI_CD='H', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='030e8905-94ae-4f0c-9dbd-f0b39aa81ff0', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b7ba7228-07d8-488a-b7f7-ea2219452e52' and VER_NBR=2
/
update KSEN_LUI_LU_CD set OBJ_ID='b0c84fcd-eea7-474a-9d46-766bfcabf0e6', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:43:29.371', DESCR_FORMATTED='', LUI_ID='030e8905-94ae-4f0c-9dbd-f0b39aa81ff0', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='da01a149-60fb-4364-850a-2628a713395a' and VER_NBR=2
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('95056950-28f9-4832-862e-393faa789c78', 0, 'admin', TIMESTAMP '2014-07-01 09:43:29.371', 'admin', TIMESTAMP '2014-07-01 09:43:29.371', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '330c9074-0174-4f39-8874-992db78ef885', '6ec0c776-8a7a-43a1-8390-c55f6ca19b13')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('e921a674-90b2-4a7f-9fd9-7b92296f174c', 1, '6ec0c776-8a7a-43a1-8390-c55f6ca19b13', '382a7ee6-bf6d-4be4-8357-7fffdaa35bed')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('382a7ee6-bf6d-4be4-8357-7fffdaa35bed', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('cae8ebdb-0673-4b54-b25d-f3e0cb08d00a', 0, 'admin', TIMESTAMP '2014-07-01 09:43:29.371', 'admin', TIMESTAMP '2014-07-01 09:43:29.371', 'kuali.atp.2014Spring', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'e70d808c-6d7d-44d8-a595-be7a80c56135')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('377c380a-fcd1-4baf-9479-a5c6df052f5f', 1, '', 'e70d808c-6d7d-44d8-a595-be7a80c56135', '40c6fbe3-e2c6-4977-89c5-684a18efe748')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('40c6fbe3-e2c6-4977-89c5-684a18efe748', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND=1, SCHED_RQST_ID='6ec0c776-8a7a-43a1-8390-c55f6ca19b13' where ID='382a7ee6-bf6d-4be4-8357-7fffdaa35bed'
/
update KSEN_SCHED_RQST set OBJ_ID='95056950-28f9-4832-862e-393faa789c78', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:43:29.371', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='e70d808c-6d7d-44d8-a595-be7a80c56135', SCHED_RQST_SET_ID='330c9074-0174-4f39-8874-992db78ef885' where ID='6ec0c776-8a7a-43a1-8390-c55f6ca19b13' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='382a7ee6-bf6d-4be4-8357-7fffdaa35bed'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('382a7ee6-bf6d-4be4-8357-7fffdaa35bed', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_LUI set OBJ_ID='d618d44a-80d4-4192-a930-04dd401e0e5c', VER_NBR=5, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:43:29.371', ATP_ID='kuali.atp.2014Spring', CLU_ID='50f30135-2d2e-4bd5-afcd-1f697d38174a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='030e8905-94ae-4f0c-9dbd-f0b39aa81ff0' and VER_NBR=4
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('030e8905-94ae-4f0c-9dbd-f0b39aa81ff0', 'e70d808c-6d7d-44d8-a595-be7a80c56135')
/
update KSEN_LUI set OBJ_ID='c77541ff-11a7-4af5-8ad8-0b9388185e3b', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:43:29.371', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2002', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2002', DESCR_PLAIN='2002', REF_URL='' where ID='cecb403c-2a71-4940-bbc6-f3cab758747e' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='e9436466-52a3-42c1-b443-f23f92761f9a', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:43:29.371', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='cecb403c-2a71-4940-bbc6-f3cab758747e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='93c2d74b-3c2b-46b1-8d73-6e14926e9809' and VER_NBR=2
/
update KSEN_LUI set OBJ_ID='7f4c9f7d-0455-4fcf-a7ed-9b45da5a79a5', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:43:29.371', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2001', DESCR_PLAIN='2001', REF_URL='' where ID='1481af5c-8633-4a1f-86ce-bd75b6d0a62e' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='880054bd-669d-4d20-a8ee-4cfd73783588', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:43:29.371', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='1481af5c-8633-4a1f-86ce-bd75b6d0a62e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1fc15212-8683-4624-8361-2286b2e5c305' and VER_NBR=2
/
update KSEN_LUI set OBJ_ID='c77541ff-11a7-4af5-8ad8-0b9388185e3b', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:43:29.371', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2002', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2002', DESCR_PLAIN='2002', REF_URL='' where ID='cecb403c-2a71-4940-bbc6-f3cab758747e' and VER_NBR=3
/
update KSEN_CWL set OBJ_ID='d5e2b717-c43b-4a4c-bb89-5c022148a0bd', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:43:29.371', ALLOW_HOLD_UTIL_ENTRIES_IND=0, AUTO_PROCESSED_IND=1, CHECK_IN_REQ_IND=0, CONF_REQ_IND=1, EFF_DT='', EXPIR_DT='', MAX_SIZE='', REG_IN_FIRST_AAO_IND='', STD_DUR_TIME_QTY='', STD_DUR_TYPE='', CWL_STATE='kuali.course.waitlist.state.inactive', CWL_TYPE='kuali.course.waitlist.type.course.waitlist' where ID='4cfa0025-c4b3-4daf-9ff9-8f44edb9f427' and VER_NBR=1
/
update KSEN_LUI set OBJ_ID='ef736749-d061-4955-9010-89dfa58dc2e2', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:44:39.636', ATP_ID='kuali.atp.2014Spring', CLU_ID='50f30135-2d2e-4bd5-afcd-1f697d38174a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='46efc2ec-89bd-470c-94df-28437f17c83c' and VER_NBR=3
/
update KSEN_LUI_IDENT set OBJ_ID='3070cd3c-0855-4041-870b-e8ab3a171b27', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:44:39.636', LUI_CD='M', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='46efc2ec-89bd-470c-94df-28437f17c83c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='e278d453-82d6-4120-9ad7-58fcd2a2423c' and VER_NBR=2
/
update KSEN_LUI_LU_CD set OBJ_ID='68cd700c-6df9-403e-a7fc-f66780cb58f3', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:44:39.636', DESCR_FORMATTED='', LUI_ID='46efc2ec-89bd-470c-94df-28437f17c83c', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='0225610a-5a71-440e-b2e8-0f109787f67f' and VER_NBR=2
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('5118e22b-86dc-4e2b-9ca4-c02298097efd', 0, 'admin', TIMESTAMP '2014-07-01 09:44:39.636', 'admin', TIMESTAMP '2014-07-01 09:44:39.636', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '3a5657d9-eef1-4df6-8d20-2f208b59a23f', 'dd478365-9780-4dac-a322-5cf48a657894')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('96156711-6b51-4d2e-a467-9f468add8fd2', 1, 'dd478365-9780-4dac-a322-5cf48a657894', '08f1c7bf-a56c-45f3-84d7-791d5131a109')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('08f1c7bf-a56c-45f3-84d7-791d5131a109', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('66d73a48-a2c1-4ebe-8e75-8a2ed969a8cc', 0, 'admin', TIMESTAMP '2014-07-01 09:44:39.636', 'admin', TIMESTAMP '2014-07-01 09:44:39.636', 'kuali.atp.2014Spring', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'ad225379-a15e-4baa-9cbf-56c92290ccb1')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('2f64e1c9-7882-416f-8b8b-85b88f92acfc', 1, '', 'ad225379-a15e-4baa-9cbf-56c92290ccb1', '0c94cf50-6c2f-453c-9e9f-d619c564b92d')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('0c94cf50-6c2f-453c-9e9f-d619c564b92d', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND=1, SCHED_RQST_ID='dd478365-9780-4dac-a322-5cf48a657894' where ID='08f1c7bf-a56c-45f3-84d7-791d5131a109'
/
update KSEN_SCHED_RQST set OBJ_ID='5118e22b-86dc-4e2b-9ca4-c02298097efd', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:44:39.636', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='ad225379-a15e-4baa-9cbf-56c92290ccb1', SCHED_RQST_SET_ID='3a5657d9-eef1-4df6-8d20-2f208b59a23f' where ID='dd478365-9780-4dac-a322-5cf48a657894' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='08f1c7bf-a56c-45f3-84d7-791d5131a109'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('08f1c7bf-a56c-45f3-84d7-791d5131a109', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_LUI set OBJ_ID='ef736749-d061-4955-9010-89dfa58dc2e2', VER_NBR=5, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:44:39.636', ATP_ID='kuali.atp.2014Spring', CLU_ID='50f30135-2d2e-4bd5-afcd-1f697d38174a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='46efc2ec-89bd-470c-94df-28437f17c83c' and VER_NBR=4
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('46efc2ec-89bd-470c-94df-28437f17c83c', 'ad225379-a15e-4baa-9cbf-56c92290ccb1')
/
update KSEN_LUI set OBJ_ID='e7e2add0-ee51-4c66-b5e0-bc8d1f6162ed', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:44:39.636', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2011', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2011', DESCR_PLAIN='2011', REF_URL='' where ID='f3e634b3-98c5-4fdf-850f-74e5100ed7ba' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='d0cb1e7b-0324-44f6-a572-ec564da2fe49', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:44:39.636', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='f3e634b3-98c5-4fdf-850f-74e5100ed7ba', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='2cb4bd78-53c7-4871-be12-0d3c16d57e22' and VER_NBR=2
/
update KSEN_LUI set OBJ_ID='6599f6d0-1a4e-4908-999b-3005c9289ee4', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:44:39.636', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2010', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2010', DESCR_PLAIN='2010', REF_URL='' where ID='7317fa63-c4c5-45ab-b968-6908f9e89809' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='80076a0b-aede-4a6f-9274-378faa86b366', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:44:39.636', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='7317fa63-c4c5-45ab-b968-6908f9e89809', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a3eac8ae-f1ec-4e3a-b377-5d81064c6ebd' and VER_NBR=2
/
update KSEN_LUI set OBJ_ID='e7e2add0-ee51-4c66-b5e0-bc8d1f6162ed', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:44:39.636', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2011', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2011', DESCR_PLAIN='2011', REF_URL='' where ID='f3e634b3-98c5-4fdf-850f-74e5100ed7ba' and VER_NBR=3
/
update KSEN_CWL set OBJ_ID='0aac46c8-010d-416f-a4b6-73f814636d9d', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:44:39.636', ALLOW_HOLD_UTIL_ENTRIES_IND=0, AUTO_PROCESSED_IND=1, CHECK_IN_REQ_IND=0, CONF_REQ_IND=1, EFF_DT='', EXPIR_DT='', MAX_SIZE='', REG_IN_FIRST_AAO_IND='', STD_DUR_TIME_QTY='', STD_DUR_TYPE='', CWL_STATE='kuali.course.waitlist.state.inactive', CWL_TYPE='kuali.course.waitlist.type.course.waitlist' where ID='633dd4fb-94bd-4d48-9923-df00fc002295' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='18468572-96c2-47be-9f7f-d6de6e7b0c86', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='af9f338a-1256-4f88-b977-8da3cb860f6a', ATTR_VALUE=0 where ID='0d68d47b-eaba-4ed1-a0b2-b99d3b829247'
/
update KSEN_LUI set OBJ_ID='2860f0d5-fc65-4434-9a43-a15933e79929', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:46:02.58', ATP_ID='kuali.atp.2014Spring', CLU_ID='14b8a6c2-8074-419e-be8c-d200a4537969', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='af9f338a-1256-4f88-b977-8da3cb860f6a' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='dbf7d311-05b7-495b-abe5-3469ef0e6e57', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='af9f338a-1256-4f88-b977-8da3cb860f6a', ATTR_VALUE=0 where ID='c56f7885-5996-4c0d-8bfc-20581ab38ced'
/
update KSEN_LUI_IDENT set OBJ_ID='19c52b2a-0aff-4c57-9c71-77ee7aa5eead', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:46:02.58', LUI_CD='G', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='af9f338a-1256-4f88-b977-8da3cb860f6a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='17622a72-7816-4a1b-ab31-9e9d50f6af5a' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='1b0d9a2b-9ebb-486c-9b34-2807e348779f', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:46:02.58', DESCR_FORMATTED='', LUI_ID='af9f338a-1256-4f88-b977-8da3cb860f6a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='00a49514-7012-496d-aa8b-46f92e54927b' and VER_NBR=1
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('a5172832-666f-4bff-ad0e-7df45f3a2e01', 0, 'admin', TIMESTAMP '2014-07-01 09:46:02.58', 'admin', TIMESTAMP '2014-07-01 09:46:02.58', '', '', '', 'Schedule request set for CHEM232S - G', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '29f62cbf-209c-4235-89f0-939f42d7ccda')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('29f62cbf-209c-4235-89f0-939f42d7ccda', 'af9f338a-1256-4f88-b977-8da3cb860f6a')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('07378ea8-2549-479c-809f-16c1bdef77f8', 0, 'admin', TIMESTAMP '2014-07-01 09:46:02.58', 'admin', TIMESTAMP '2014-07-01 09:46:02.58', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '29f62cbf-209c-4235-89f0-939f42d7ccda', '8a1721e9-a7f4-47e0-802e-6e6e3f7f9598')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('afe59c32-5bb5-438c-a061-0c0613ca1207', 1, '8a1721e9-a7f4-47e0-802e-6e6e3f7f9598', '0ef452e1-ab11-42dc-8008-2c9d3854039b')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('0ef452e1-ab11-42dc-8008-2c9d3854039b', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('24fa3dc8-66d9-4d32-8b0b-76944f80b4e5', 0, 'admin', TIMESTAMP '2014-07-01 09:46:02.58', 'admin', TIMESTAMP '2014-07-01 09:46:02.58', 'kuali.atp.2014Spring', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'fe376c3a-6505-4d17-8aad-cc6b700f0d9e')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('96380769-71d6-4928-9489-cf459e245fb7', 1, '', 'fe376c3a-6505-4d17-8aad-cc6b700f0d9e', '4fd06413-4bb5-4dee-9f08-697fd9e4d266')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('4fd06413-4bb5-4dee-9f08-697fd9e4d266', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND=1, SCHED_RQST_ID='8a1721e9-a7f4-47e0-802e-6e6e3f7f9598' where ID='0ef452e1-ab11-42dc-8008-2c9d3854039b'
/
update KSEN_SCHED_RQST set OBJ_ID='07378ea8-2549-479c-809f-16c1bdef77f8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:46:02.58', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='fe376c3a-6505-4d17-8aad-cc6b700f0d9e', SCHED_RQST_SET_ID='29f62cbf-209c-4235-89f0-939f42d7ccda' where ID='8a1721e9-a7f4-47e0-802e-6e6e3f7f9598' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='0ef452e1-ab11-42dc-8008-2c9d3854039b'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('0ef452e1-ab11-42dc-8008-2c9d3854039b', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_LUI set OBJ_ID='2860f0d5-fc65-4434-9a43-a15933e79929', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:46:02.58', ATP_ID='kuali.atp.2014Spring', CLU_ID='14b8a6c2-8074-419e-be8c-d200a4537969', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='af9f338a-1256-4f88-b977-8da3cb860f6a' and VER_NBR=2
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('af9f338a-1256-4f88-b977-8da3cb860f6a', 'fe376c3a-6505-4d17-8aad-cc6b700f0d9e')
/
update KSEN_LUI set OBJ_ID='2860f0d5-fc65-4434-9a43-a15933e79929', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:46:02.58', ATP_ID='kuali.atp.2014Spring', CLU_ID='14b8a6c2-8074-419e-be8c-d200a4537969', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='af9f338a-1256-4f88-b977-8da3cb860f6a' and VER_NBR=3
/
update KSEN_LUI set OBJ_ID='a747e746-f94f-46ea-80d5-af375a490811', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:46:02.58', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2005', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2005', DESCR_PLAIN='2005', REF_URL='' where ID='b296e0ea-ad05-476b-8a01-da1ec9d6756d' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='0d196de9-1ea8-4fb7-91da-2eeaf637f849', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:46:02.58', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='b296e0ea-ad05-476b-8a01-da1ec9d6756d', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='5c123812-8128-418b-a8fe-48af53619ed8' and VER_NBR=0
/
update KSEN_CWL set OBJ_ID='ecbce4cf-8801-4af9-ae55-b4012c351420', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:46:02.58', ALLOW_HOLD_UTIL_ENTRIES_IND=0, AUTO_PROCESSED_IND=1, CHECK_IN_REQ_IND=0, CONF_REQ_IND=1, EFF_DT='', EXPIR_DT='', MAX_SIZE='', REG_IN_FIRST_AAO_IND='', STD_DUR_TIME_QTY='', STD_DUR_TYPE='', CWL_STATE='kuali.course.waitlist.state.inactive', CWL_TYPE='kuali.course.waitlist.type.course.waitlist' where ID='e764caf5-e082-4d28-90ea-35c7a37d4f68' and VER_NBR=0
/
update KSEN_LUI_ATTR set OBJ_ID='4006b7e1-3426-414a-96b7-3ee3e9daf840', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db', ATTR_VALUE=0 where ID='99da06ff-0f3d-4d7e-afbd-3ee02244d466'
/
update KSEN_LUI set OBJ_ID='d3a8d1a0-d469-46a9-a3f9-d480a76c36f8', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:47:48.525', ATP_ID='kuali.atp.2014Spring', CLU_ID='50f30135-2d2e-4bd5-afcd-1f697d38174a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='f064444a-6eaf-4118-a281-443e896c7741', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db', ATTR_VALUE=0 where ID='72e521c2-9309-42f5-892b-4222a5c58fe5'
/
update KSEN_LUI_IDENT set OBJ_ID='3520aeaa-e8d7-4708-858d-2ab54c45c5c8', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:47:48.525', LUI_CD='J', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='2703269e-324d-46f3-ab4b-6c02669cfcff' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='24444b61-86b1-4d2e-88c4-516d20d8b512', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:47:48.525', DESCR_FORMATTED='', LUI_ID='b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='cfbc4536-27e3-4301-bddd-1c79e380f0f9' and VER_NBR=1
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('04bfc8cd-af75-45ec-bc2c-1569f60f114f', 0, 'admin', TIMESTAMP '2014-07-01 09:47:48.525', 'admin', TIMESTAMP '2014-07-01 09:47:48.525', '', '', '', 'Schedule request set for CHEM232S - J', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'c0c4f477-7553-46c0-9acc-e8d45e43e423')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('c0c4f477-7553-46c0-9acc-e8d45e43e423', 'b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('96334455-09b7-4d84-a9eb-65f7b324f15f', 0, 'admin', TIMESTAMP '2014-07-01 09:47:48.525', 'admin', TIMESTAMP '2014-07-01 09:47:48.525', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'c0c4f477-7553-46c0-9acc-e8d45e43e423', 'b2c7d5b5-b9b7-4b8a-848e-c6902aa8b96f')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('58919277-b1da-4966-944b-052be477a0f5', 1, 'b2c7d5b5-b9b7-4b8a-848e-c6902aa8b96f', 'f9a80d6b-450a-4aeb-bb41-bca38e136207')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('f9a80d6b-450a-4aeb-bb41-bca38e136207', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('8ca633e0-3f0a-4c9b-83fb-d5408899ee7d', 0, 'admin', TIMESTAMP '2014-07-01 09:47:48.525', 'admin', TIMESTAMP '2014-07-01 09:47:48.525', 'kuali.atp.2014Spring', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'c72d1950-6221-4c34-91b2-86636195a609')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('f5990878-4149-4eac-94fb-ebdc3b62243e', 1, '', 'c72d1950-6221-4c34-91b2-86636195a609', '895ec77b-6a16-43b6-99c7-f2ec2dbfad90')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('895ec77b-6a16-43b6-99c7-f2ec2dbfad90', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND=1, SCHED_RQST_ID='b2c7d5b5-b9b7-4b8a-848e-c6902aa8b96f' where ID='f9a80d6b-450a-4aeb-bb41-bca38e136207'
/
update KSEN_SCHED_RQST set OBJ_ID='96334455-09b7-4d84-a9eb-65f7b324f15f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:47:48.525', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='c72d1950-6221-4c34-91b2-86636195a609', SCHED_RQST_SET_ID='c0c4f477-7553-46c0-9acc-e8d45e43e423' where ID='b2c7d5b5-b9b7-4b8a-848e-c6902aa8b96f' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='f9a80d6b-450a-4aeb-bb41-bca38e136207'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('f9a80d6b-450a-4aeb-bb41-bca38e136207', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_LUI set OBJ_ID='d3a8d1a0-d469-46a9-a3f9-d480a76c36f8', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:47:48.525', ATP_ID='kuali.atp.2014Spring', CLU_ID='50f30135-2d2e-4bd5-afcd-1f697d38174a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=1, MIN_SEATS='', NAME='CHEM232S AO', DESCR_PLAIN='', REF_URL='' where ID='b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db' and VER_NBR=2
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('b6c5f13e-f92f-4e3c-a7db-cdd5bb9c59db', 'c72d1950-6221-4c34-91b2-86636195a609')
/
update KSEN_LUI set OBJ_ID='a747e746-f94f-46ea-80d5-af375a490811', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:47:48.525', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2005', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2005', DESCR_PLAIN='2005', REF_URL='' where ID='b296e0ea-ad05-476b-8a01-da1ec9d6756d' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='0d196de9-1ea8-4fb7-91da-2eeaf637f849', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:47:48.525', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='b296e0ea-ad05-476b-8a01-da1ec9d6756d', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='5c123812-8128-418b-a8fe-48af53619ed8' and VER_NBR=1
/
update KSEN_LUI set OBJ_ID='a747e746-f94f-46ea-80d5-af375a490811', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:47:48.525', ATP_ID='kuali.atp.2014Spring', CLU_ID='cf596c5d-a14b-4485-9bcb-5a9fc53f8927', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2005', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2005', DESCR_PLAIN='2005', REF_URL='' where ID='b296e0ea-ad05-476b-8a01-da1ec9d6756d' and VER_NBR=2
/
update KSEN_CWL set OBJ_ID='ec6ab4c8-70d9-4289-a48a-a4ec9fff4e27', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:47:48.525', ALLOW_HOLD_UTIL_ENTRIES_IND=0, AUTO_PROCESSED_IND=1, CHECK_IN_REQ_IND=0, CONF_REQ_IND=1, EFF_DT='', EXPIR_DT='', MAX_SIZE='', REG_IN_FIRST_AAO_IND='', STD_DUR_TIME_QTY='', STD_DUR_TYPE='', CWL_STATE='kuali.course.waitlist.state.inactive', CWL_TYPE='kuali.course.waitlist.type.course.waitlist' where ID='069a8602-f19c-4837-8476-d618ef4011ec' and VER_NBR=0
/
update KSEN_LUI_ATTR set OBJ_ID='49ad13d7-df0f-4ac5-ae2b-b95543bd8e60', ATTR_KEY='kuali.attribute.grade.roster.level.type.key', OWNER_ID='53cd288e-720b-46da-a34b-15259916a8aa', ATTR_VALUE='kuali.lu.type.activity.Lecture' where ID='1497a3b8-1ab3-4863-9600-9eae71933931'
/
update KSEN_LUI set OBJ_ID='0769c8e8-00f6-4f05-be68-6f068edc9b65', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:50:23.184', ATP_ID='kuali.atp.2014Spring', CLU_ID='ed9e10ff-8739-40d0-83da-a2b92489e055', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture only', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture Only', DESCR_PLAIN='Courses with Lecture only', REF_URL='' where ID='53cd288e-720b-46da-a34b-15259916a8aa' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='0c701c03-480c-4b1e-968f-792d6bc45eed', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:50:23.184', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture Only', LUI_ID='53cd288e-720b-46da-a34b-15259916a8aa', ORGID='', SHRT_NAME='LEC', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='df2cb885-5f7d-42aa-97df-646372789246' and VER_NBR=2
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='53cd288e-720b-46da-a34b-15259916a8aa'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('53cd288e-720b-46da-a34b-15259916a8aa', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('da9ab6ff-d972-4b1d-af74-123ec6224d91', 0, 'admin', TIMESTAMP '2014-07-01 09:50:23.184', 'admin', TIMESTAMP '2014-07-01 09:50:23.184', 'kuali.atp.2014Spring', '48f2aa1e-830c-4b79-9164-48dc19c85a95', '', '', 'Courses with Discussion/Lecture', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Discussion/Lecture', 'Courses with Discussion/Lecture', '', '76b0c953-d465-4b56-88c1-3bdfec356f12')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c504489a-7ce4-4e32-87f7-6114a4808683', 'regCodePrefix', '76b0c953-d465-4b56-88c1-3bdfec356f12', '2', 'a1c346c1-8db6-40ad-a398-938ca03323d0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1cad868e-bbfa-4b41-a16c-b84a8c79e969', 'kuali.attribute.grade.roster.level.type.key', '76b0c953-d465-4b56-88c1-3bdfec356f12', 'kuali.lu.type.activity.Discussion', 'f1bb4d36-9b7d-4815-9d51-fdb02b879f33')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8aa30472-c675-400f-a4a7-dd2a60a3daa9', 'kuali.attribute.final.exam.level.type', '76b0c953-d465-4b56-88c1-3bdfec356f12', 'kuali.lu.type.activity.Discussion', 'e99dbcad-19c4-4ecf-9eee-e450f996913a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('b9ab0536-4e7c-4fe9-b9df-1051862b815b', 0, 'admin', TIMESTAMP '2014-07-01 09:50:23.184', 'admin', TIMESTAMP '2014-07-01 09:50:23.184', '', '', '', 'Discussion/Lecture', '76b0c953-d465-4b56-88c1-3bdfec356f12', '', 'DIS/LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '520e3e61-5a68-4412-a436-9dfeed7f07cc')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('76b0c953-d465-4b56-88c1-3bdfec356f12', 'kuali.lui.type.activity.offering.discussion')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('76b0c953-d465-4b56-88c1-3bdfec356f12', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a816a93d-f8c8-426d-8283-d21d319b7796', 0, 'admin', TIMESTAMP '2014-07-01 09:50:23.184', 'admin', TIMESTAMP '2014-07-01 09:50:23.184', TIMESTAMP '2014-07-01 09:50:24.092', '', 'ENGL278R-CO-FO', '462d4a79-8cbc-459b-85f3-e391a303b31b', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL278R-CO-FO', '76b0c953-d465-4b56-88c1-3bdfec356f12', '8644cbec-74f3-49d6-9ee9-9883ce8f0883')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('f4efd6e7-b24f-46bc-9b39-34dd463e1d1d', 0, 'admin', TIMESTAMP '2014-07-01 09:50:23.184', 'admin', TIMESTAMP '2014-07-01 09:50:23.184', 'kuali.atp.2014Spring', '64c5a912-c558-42d6-b4b7-c073bcae2b3b', '', '', 'Courses with Lecture/Lab', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture/Lab', 'Courses with Lecture/Lab', '', '6a4b4618-7cb9-4721-94bd-5a05c315d9de')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('52a56984-33c7-4d33-9165-f2cc70c8eaf2', 'kuali.attribute.final.exam.level.type', '6a4b4618-7cb9-4721-94bd-5a05c315d9de', 'kuali.lu.type.activity.Lecture', 'd33e7e23-ea4f-45d1-8de1-5180ba722015')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('edabaac4-5391-427f-9e1e-41634e47f658', 'kuali.attribute.grade.roster.level.type.key', '6a4b4618-7cb9-4721-94bd-5a05c315d9de', 'kuali.lu.type.activity.Lecture', '8409fe73-69bf-4c1b-a87f-6bc3212eaa91')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ff65bf96-5fbc-4c04-a99a-db2b383d751f', 'regCodePrefix', '6a4b4618-7cb9-4721-94bd-5a05c315d9de', '3', 'cd60fc2e-2d0f-4e30-9cc6-67bee6af23ea')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7bf019f8-6886-4b84-844b-3a9fc0428616', 0, 'admin', TIMESTAMP '2014-07-01 09:50:23.184', 'admin', TIMESTAMP '2014-07-01 09:50:23.184', '', '', '', 'Lecture/Lab', '6a4b4618-7cb9-4721-94bd-5a05c315d9de', '', 'LEC/LAB', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'dd8a8dc5-4b81-47a1-b8a1-e745f41ba58d')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('6a4b4618-7cb9-4721-94bd-5a05c315d9de', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('6a4b4618-7cb9-4721-94bd-5a05c315d9de', 'kuali.lui.type.activity.offering.lab')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('3a0621f0-c43a-4ba9-8bb3-3d20290defe6', 0, 'admin', TIMESTAMP '2014-07-01 09:50:23.184', 'admin', TIMESTAMP '2014-07-01 09:50:23.184', TIMESTAMP '2014-07-01 09:50:24.451', '', 'ENGL278R-CO-FO', '462d4a79-8cbc-459b-85f3-e391a303b31b', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL278R-CO-FO', '6a4b4618-7cb9-4721-94bd-5a05c315d9de', 'e4d4f5d3-454f-4cdc-afa9-c7c5cd284e45')
/
update KSEN_LUI_ATTR set OBJ_ID='89aebf4d-81c0-46ac-9ec6-59e80e6d17c7', ATTR_KEY='kuali.attribute.where.fees.attached.flag', OWNER_ID='462d4a79-8cbc-459b-85f3-e391a303b31b', ATTR_VALUE=0 where ID='03da36f4-55ed-49dd-b038-48eab6b29b5e'
/
update KSEN_LUI set OBJ_ID='fa5785d4-177a-49c6-a1c7-e3ccf29cffab', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:50:24.483', ATP_ID='kuali.atp.2014Spring', CLU_ID='CLUID-ENGL278-198001000000', EFF_DT=TIMESTAMP '2014-01-26 19:00:00.0', EXPIR_DT='', DESCR_FORMATTED='A deeper look into the myths and legends of ancient Greece including The Oydessy, Battle of Tory, numerous others. ', LUI_STATE='kuali.lui.course.offering.state.offered', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='ENGL278R CO', DESCR_PLAIN='A deeper look into the myths and legends of ancient Greece including The Oydessy, Battle of Tory, numerous others. ', REF_URL='' where ID='462d4a79-8cbc-459b-85f3-e391a303b31b' and VER_NBR=2
/
update KSEN_LUI_ATTR set OBJ_ID='4413ac60-1fa7-4c08-b71a-4d96fb1ad567', ATTR_KEY='kuali.attribute.wait.list.type.key', OWNER_ID='462d4a79-8cbc-459b-85f3-e391a303b31b', ATTR_VALUE=null where ID='4195c975-be3d-49e7-97b7-672254476df8'
/
update KSEN_LUI_ATTR set OBJ_ID='be1978ac-8d3c-4d6d-bb2b-7b5a5cb2cd80', ATTR_KEY='kuali.attribute.wait.list.indicator', OWNER_ID='462d4a79-8cbc-459b-85f3-e391a303b31b', ATTR_VALUE=0 where ID='8379bc7b-d273-486c-89f4-f861c37b929c'
/
update KSEN_LUI_ATTR set OBJ_ID='85a03ccb-85dc-4d7f-ac28-c10511d1492f', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='462d4a79-8cbc-459b-85f3-e391a303b31b', ATTR_VALUE=0 where ID='b4ad3c0a-e862-458d-bb05-c6f9ce6e1eb4'
/
update KSEN_LUI_ATTR set OBJ_ID='e8de78f4-44fb-4d3f-aa83-a3461b7e851b', ATTR_KEY='kuali.attribute.final.exam.use.matrix', OWNER_ID='462d4a79-8cbc-459b-85f3-e391a303b31b', ATTR_VALUE=0 where ID='bdd3023d-ffbb-4ccc-a502-3a919dea1c23'
/
update KSEN_LUI_IDENT set OBJ_ID='53899a62-0b04-45e2-8306-f24229c5ca3b', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:50:24.483', LUI_CD='ENGL278R', DIVISION='ENGL', IDENT_LEVEL='', LNG_NAME='Special Topics in Literature: Greek Mythology', LUI_ID='462d4a79-8cbc-459b-85f3-e391a303b31b', ORGID='', SHRT_NAME='Special Topics in Literature: Greek Mythology', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='R', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='aab794a0-9a9c-452d-ac75-1f2fc5088f2a' and VER_NBR=2
/
update KSEN_LUI_LU_CD set OBJ_ID='8f52e4d9-3b4d-4504-86f9-ae380330a720', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:50:24.483', DESCR_FORMATTED='', LUI_ID='462d4a79-8cbc-459b-85f3-e391a303b31b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='a44aadbc-7c55-44cb-bb12-5dfdb3517693' and VER_NBR=2
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='462d4a79-8cbc-459b-85f3-e391a303b31b'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('462d4a79-8cbc-459b-85f3-e391a303b31b', 'ORGID-2677933260')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='462d4a79-8cbc-459b-85f3-e391a303b31b'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('462d4a79-8cbc-459b-85f3-e391a303b31b', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('462d4a79-8cbc-459b-85f3-e391a303b31b', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('462d4a79-8cbc-459b-85f3-e391a303b31b', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('462d4a79-8cbc-459b-85f3-e391a303b31b', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('cce40410-de52-4401-aead-a0e97b207ed1', 0, 'admin', TIMESTAMP '2014-07-01 09:51:27.672', 'admin', TIMESTAMP '2014-07-01 09:51:27.672', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '76b0c953-d465-4b56-88c1-3bdfec356f12', '2', '2', '22fb977a-ad2c-49aa-a018-d12c887ff60b')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('b4574e32-59f6-4fe7-b7e7-49535e311930', 'kuali.lui.type.activity.offering.discussion', '22fb977a-ad2c-49aa-a018-d12c887ff60b', 'd0d56d9d-5914-4806-9fea-7fbabc9e036b')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('02454017-2e87-431d-972b-cb34f931bb4c', 'kuali.lui.type.activity.offering.lecture', '22fb977a-ad2c-49aa-a018-d12c887ff60b', '369a44cc-3737-47a4-a001-d6c97e4e37d0')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='22fb977a-ad2c-49aa-a018-d12c887ff60b' where ID='369a44cc-3737-47a4-a001-d6c97e4e37d0'
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('3f6a9d8c-3403-42ce-9ba2-4aa110308a1c', 0, 'admin', TIMESTAMP '2014-07-01 09:51:53.379', 'admin', TIMESTAMP '2014-07-01 09:51:53.379', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '6a4b4618-7cb9-4721-94bd-5a05c315d9de', '3', '3', '1a2e89da-4d1a-41fe-b360-4dfdcc3a290f')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('be36855e-6d58-41bb-ae8d-2a65277896c7', 'kuali.lui.type.activity.offering.lab', '1a2e89da-4d1a-41fe-b360-4dfdcc3a290f', '2ea79f4d-4e1c-44c8-bb01-48f9d3fa6246')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('932481c1-ad24-4dce-b99a-3d86a576cf06', 'kuali.lui.type.activity.offering.lecture', '1a2e89da-4d1a-41fe-b360-4dfdcc3a290f', '19e5f30b-7ca4-4298-a000-5a4c05ba1062')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='1a2e89da-4d1a-41fe-b360-4dfdcc3a290f' where ID='19e5f30b-7ca4-4298-a000-5a4c05ba1062'
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('4dc990e2-0116-4191-befa-c072c511bce0', 'ecc6ac89-2165-4a58-ba9d-2ca7d2b026af', 'activityOfferingCode', '462d4a79-8cbc-459b-85f3-e391a303b31b-P', 'cb3fbbe9-351c-40ba-a4c3-a75c51794a5a')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('c2dcb09f-e121-4dac-bcec-1a9cd8772e0e', 'B', 'activityOfferingCode', '462d4a79-8cbc-459b-85f3-e391a303b31b', 'f74864a0-d892-4526-a777-dc4fa0c61158')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='ecc6ac89-2165-4a58-ba9d-2ca7d2b026af' and UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('fa88f36d-c959-4f6f-8452-9636e448100a', 0, 'admin', TIMESTAMP '2014-07-01 09:53:24.342', 'admin', TIMESTAMP '2014-07-01 09:53:24.342', 'kuali.atp.2014Spring', '65c39350-c26d-45ac-a627-214a9737a16a', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', '', '', 'ENGL278R AO', '', '', 'f18fdf19-8ee4-4052-8efa-d2efdf20201d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7b3413be-5550-408e-9237-d87f8c84a187', 'kuali.attribute.course.evaluation.indicator', 'f18fdf19-8ee4-4052-8efa-d2efdf20201d', null, 'e81c303b-66e7-4ef4-9bd5-cdd8c5a077d4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('276a90c6-aeb9-4a11-ac61-70a19d4cb40a', 'kuali.attribute.max.enrollment.is.estimate', 'f18fdf19-8ee4-4052-8efa-d2efdf20201d', null, 'a9393a70-204c-4ba9-9e58-493e8a4777d7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('96b13a76-bd98-458f-9be9-5e6d2cd6b048', 'kuali.attribute.nonstd.ts.indicator', 'f18fdf19-8ee4-4052-8efa-d2efdf20201d', 0, 'e8971860-d226-41e1-9e62-c24cae644b6c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a4c35cb0-01aa-4ebe-bb0d-e5165e42329c', 0, 'admin', TIMESTAMP '2014-07-01 09:53:24.342', 'admin', TIMESTAMP '2014-07-01 09:53:24.342', 'B', '', '', '', 'f18fdf19-8ee4-4052-8efa-d2efdf20201d', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '66e8bdb8-c665-439d-9a59-480e8b043212')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('b6ee1006-18bb-4a32-bb7b-6f62bdcbfe94', 0, 'admin', TIMESTAMP '2014-07-01 09:53:24.342', 'admin', TIMESTAMP '2014-07-01 09:53:24.342', '', 'f18fdf19-8ee4-4052-8efa-d2efdf20201d', '', 'kuali.lu.code.honorsOffering', null, '5b60658f-cfcb-4ae7-b053-5acab38999de')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('41d8a212-8c43-49cd-9b47-08f10bd7b2c3', 0, 'admin', TIMESTAMP '2014-07-01 09:53:24.342', 'admin', TIMESTAMP '2014-07-01 09:53:24.342', TIMESTAMP '2014-07-01 09:53:25.168', '', 'ENGL278R-FO-AO', '53cd288e-720b-46da-a34b-15259916a8aa', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL278R-FO-AO', 'f18fdf19-8ee4-4052-8efa-d2efdf20201d', '22fb769c-f789-4107-9981-9407e44ec377')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='18251eb0-256e-4f68-ac68-fde1e963423d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:53:24.342', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='53cd288e-720b-46da-a34b-15259916a8aa', NAME='CL 1', PRIVATE_NAME='CL 1' where ID='85332542-deb1-4d61-a780-004d77b6d511' and VER_NBR=0
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('2b3de55d-4dcb-41b0-b157-d94fc226f264', 'f18fdf19-8ee4-4052-8efa-d2efdf20201d')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ee51db65-c9f7-40cf-9e81-392ed8782a71', 0, 'admin', TIMESTAMP '2014-07-01 09:53:24.342', 'admin', TIMESTAMP '2014-07-01 09:53:24.342', 'kuali.atp.2014Spring', 'ed9e10ff-8739-40d0-83da-a2b92489e055', '', '', '1002', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1002', '1002', '', 'f15c8404-7764-4420-99c0-0351aff1319e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bfbe4a23-e8d0-4cc2-b6dc-bfdb07ed4769', 'kuali.attribute.registration.group.aocluster.id', 'f15c8404-7764-4420-99c0-0351aff1319e', '85332542-deb1-4d61-a780-004d77b6d511', '9a5f499c-d414-444b-9442-6bfc9a8b40f7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e514b48e-3d95-4647-8a60-c5f53f363045', 'kuali.attribute.registration.group.is.generated', 'f15c8404-7764-4420-99c0-0351aff1319e', 1, '11bd0900-b155-48ed-8a09-b3c6e7e01c03')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d034a2fd-075a-4f13-9a1f-dc4b94f860c8', 0, 'admin', TIMESTAMP '2014-07-01 09:53:24.342', 'admin', TIMESTAMP '2014-07-01 09:53:24.342', '', '', '', '', 'f15c8404-7764-4420-99c0-0351aff1319e', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'cb837686-9e52-42ac-8226-12f3deae627c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ccbaf371-81ea-4528-bd92-54e50021a0a3', 0, 'admin', TIMESTAMP '2014-07-01 09:53:24.342', 'admin', TIMESTAMP '2014-07-01 09:53:24.342', TIMESTAMP '2014-07-01 09:53:26.484', '', 'ENGL278R-FO-RG', '53cd288e-720b-46da-a34b-15259916a8aa', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL278R-FO-RG', 'f15c8404-7764-4420-99c0-0351aff1319e', 'f8a25b86-5993-4966-930c-7d39223a6ab4')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('2be252b4-e7bc-406e-89d9-e3ae93bc067f', 0, 'admin', TIMESTAMP '2014-07-01 09:53:24.342', 'admin', TIMESTAMP '2014-07-01 09:53:24.342', TIMESTAMP '2014-07-01 09:53:26.529', '', 'ENGL278R-RG-AO', 'f15c8404-7764-4420-99c0-0351aff1319e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL278R-RG-AO', 'f18fdf19-8ee4-4052-8efa-d2efdf20201d', '2173f277-513a-402c-8163-816a1d65f922')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('dcf6606f-2dbf-46f3-8024-166948e39ff0', 0, 'admin', TIMESTAMP '2014-07-01 09:53:24.342', 'admin', TIMESTAMP '2014-07-01 09:53:24.342', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '9f61ed54-d1f9-4d8e-905a-9d5463457b27')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('9f61ed54-d1f9-4d8e-905a-9d5463457b27', 'f18fdf19-8ee4-4052-8efa-d2efdf20201d')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('9f61ed54-d1f9-4d8e-905a-9d5463457b27', '53cd288e-720b-46da-a34b-15259916a8aa')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('1c6e73fd-57d3-4cc0-8985-c786c8731a48', 0, 'admin', TIMESTAMP '2014-07-01 09:53:24.342', 'admin', TIMESTAMP '2014-07-01 09:53:24.342', 'kuali.atp.ExamPeriod.2014Spring', 'FCCF08F7BF135E91E040440A9B9A420D', '', '', '', 'kuali.lui.exam.offering.state.draft', 'kuali.lui.type.exam.offering.final', '', '', '', '', '', '06b67f10-e5e1-4911-9fe5-dded61a07afa')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('be0ea6dd-a776-4776-a9eb-852056eb8012', 'kuali.attribute.final.exam.activity.driver', '06b67f10-e5e1-4911-9fe5-dded61a07afa', 'kuali.lu.type.activity.Lecture', 'da1956c3-bad7-4d0b-ad79-b509b1d841a0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('866e4acd-dd26-434a-b11c-64284bcc085e', 'kuali.attribute.final.exam.driver', '06b67f10-e5e1-4911-9fe5-dded61a07afa', 'PER_AO', 'd6262170-9e0e-499d-8335-dc33a6f72bf7')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('277d6994-2aa3-4d1e-ad9d-f01a47ba27ca', 0, 'admin', TIMESTAMP '2014-07-01 09:53:24.342', 'admin', TIMESTAMP '2014-07-01 09:53:24.342', '', '', '', '', '06b67f10-e5e1-4911-9fe5-dded61a07afa', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '419159dd-ce54-4d7b-a101-404bea40643e')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('3b33b13f-6780-4f99-bb3e-509ed7d04622', 0, 'admin', TIMESTAMP '2014-07-01 09:53:24.342', 'admin', TIMESTAMP '2014-07-01 09:53:24.342', TIMESTAMP '2014-07-01 09:53:27.646', '', '06b67f10-e5e1-4911-9fe5-dded61a07afa-FO-EO)', '53cd288e-720b-46da-a34b-15259916a8aa', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', '06b67f10-e5e1-4911-9fe5-dded61a07afa-FO-EO', '06b67f10-e5e1-4911-9fe5-dded61a07afa', '18a8c5d8-b415-4836-8127-1f6901fe0a2c')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f88d3db4-4932-466d-98bc-1661aae17054', 'AO1', '18a8c5d8-b415-4836-8127-1f6901fe0a2c', 'f18fdf19-8ee4-4052-8efa-d2efdf20201d', '037caaad-1e4a-4138-94d9-7da89647c7c3')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('2e4e2f77-e2f7-4b4e-8e4a-ddb37589ed00', '78ec37a6-050d-454a-aec6-ebb8b5726d14', 'activityOfferingCode', '462d4a79-8cbc-459b-85f3-e391a303b31b-P', '0b50df39-4eb9-4210-90e5-dcce0c45bde7')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('857c89d4-6597-4e63-8010-1333b53659c3', 'C', 'activityOfferingCode', '462d4a79-8cbc-459b-85f3-e391a303b31b', 'cedb8d99-0f47-4e61-9ce5-e36cf5fed3c4')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='78ec37a6-050d-454a-aec6-ebb8b5726d14' and UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('980f26ba-d502-4ed3-ba02-780bd013f574', 0, 'admin', TIMESTAMP '2014-07-01 09:54:15.138', 'admin', TIMESTAMP '2014-07-01 09:54:15.138', 'kuali.atp.2014Spring', 'a1ee5006-ecdd-4cfb-ba26-66fc6892e1ab', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.discussion', '', '', 'ENGL278R AO', '', '', '82009d0f-1623-47b1-a0d5-13b483012e8b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6410071f-4a32-4cbf-89f6-915e44f858ed', 'kuali.attribute.max.enrollment.is.estimate', '82009d0f-1623-47b1-a0d5-13b483012e8b', null, 'bc0f6898-d8ed-4ffd-8243-12bcbfad3016')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a7cf0fc7-29a2-4aea-9eea-9f1476a890a0', 'kuali.attribute.nonstd.ts.indicator', '82009d0f-1623-47b1-a0d5-13b483012e8b', 0, '3c10d7a1-e650-4410-95dd-6fe8894c96d6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('239799f9-baac-4862-b23c-ccada4b3c2e6', 'kuali.attribute.course.evaluation.indicator', '82009d0f-1623-47b1-a0d5-13b483012e8b', null, 'f8013e74-159b-453d-8e42-48d775271459')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5ef09701-902e-4385-b713-76f3726b5a69', 0, 'admin', TIMESTAMP '2014-07-01 09:54:15.138', 'admin', TIMESTAMP '2014-07-01 09:54:15.138', 'C', '', '', '', '82009d0f-1623-47b1-a0d5-13b483012e8b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '926c36bd-9212-44e3-9c2e-b8291000d565')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('b6c5c681-ca6e-4c54-99e2-faa1a4fe5af9', 0, 'admin', TIMESTAMP '2014-07-01 09:54:15.138', 'admin', TIMESTAMP '2014-07-01 09:54:15.138', '', '82009d0f-1623-47b1-a0d5-13b483012e8b', '', 'kuali.lu.code.honorsOffering', null, 'bfadf6b7-9b0d-4246-a72e-0bfc7c04bac5')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('9ed190da-e013-4058-9262-aba964b216be', 0, 'admin', TIMESTAMP '2014-07-01 09:54:15.138', 'admin', TIMESTAMP '2014-07-01 09:54:15.138', TIMESTAMP '2014-07-01 09:54:16.07', '', 'ENGL278R-FO-AO', '76b0c953-d465-4b56-88c1-3bdfec356f12', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL278R-FO-AO', '82009d0f-1623-47b1-a0d5-13b483012e8b', 'a19e1a25-cbb0-4b80-8d58-bc7701422db4')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='cce40410-de52-4401-aead-a0e97b207ed1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:54:15.138', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='76b0c953-d465-4b56-88c1-3bdfec356f12', NAME='2', PRIVATE_NAME='2' where ID='22fb977a-ad2c-49aa-a018-d12c887ff60b' and VER_NBR=0
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('d0d56d9d-5914-4806-9fea-7fbabc9e036b', '82009d0f-1623-47b1-a0d5-13b483012e8b')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('bfff55d2-df53-4783-8d1e-940c45f2983c', 0, 'admin', TIMESTAMP '2014-07-01 09:54:15.138', 'admin', TIMESTAMP '2014-07-01 09:54:15.138', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '350c3161-40cb-45ad-b969-a93f2a1c42bd')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('350c3161-40cb-45ad-b969-a93f2a1c42bd', '82009d0f-1623-47b1-a0d5-13b483012e8b')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('350c3161-40cb-45ad-b969-a93f2a1c42bd', '76b0c953-d465-4b56-88c1-3bdfec356f12')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('10290c12-2293-4957-a409-9a0ddc201109', 0, 'admin', TIMESTAMP '2014-07-01 09:54:15.138', 'admin', TIMESTAMP '2014-07-01 09:54:15.138', 'kuali.atp.ExamPeriod.2014Spring', 'FCCF08F7BF135E91E040440A9B9A420D', '', '', '', 'kuali.lui.exam.offering.state.draft', 'kuali.lui.type.exam.offering.final', '', '', '', '', '', 'a4ac00ff-7f38-445e-a5e6-8d6674612b30')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('40f9cad3-8f33-41cd-8e48-8606300f79b2', 'kuali.attribute.final.exam.driver', 'a4ac00ff-7f38-445e-a5e6-8d6674612b30', 'PER_AO', '6b6cea21-9636-41d4-bdae-96e4a11b7bc0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('097ded0f-9121-4842-a486-0023a37171f3', 'kuali.attribute.final.exam.activity.driver', 'a4ac00ff-7f38-445e-a5e6-8d6674612b30', 'kuali.lu.type.activity.Discussion', 'f40ece6a-1d07-4cd3-a446-3442d1e4337b')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('625eddd1-8adc-41fa-99b6-d19f1f1efec2', 0, 'admin', TIMESTAMP '2014-07-01 09:54:15.138', 'admin', TIMESTAMP '2014-07-01 09:54:15.138', '', '', '', '', 'a4ac00ff-7f38-445e-a5e6-8d6674612b30', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '978a0061-e76d-470e-b90a-b11f6816dc9d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('9e097ce7-7b4d-4f10-bc93-ca0d500de986', 0, 'admin', TIMESTAMP '2014-07-01 09:54:15.138', 'admin', TIMESTAMP '2014-07-01 09:54:15.138', TIMESTAMP '2014-07-01 09:54:18.202', '', 'a4ac00ff-7f38-445e-a5e6-8d6674612b30-FO-EO)', '76b0c953-d465-4b56-88c1-3bdfec356f12', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', 'a4ac00ff-7f38-445e-a5e6-8d6674612b30-FO-EO', 'a4ac00ff-7f38-445e-a5e6-8d6674612b30', 'cf075101-0498-472d-82c1-6ecf98d8b2fb')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1d8cee00-7dd3-462a-99f0-25bd383d4fc7', 'AO1', 'cf075101-0498-472d-82c1-6ecf98d8b2fb', '82009d0f-1623-47b1-a0d5-13b483012e8b', '4ca3cb51-2498-4d7b-85c3-13fadc7bc8fb')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('fb368df2-9f94-4ddf-832e-6568ad040e41', '8e0bf98e-1dac-4d6a-adcf-313f57b3ca71', 'activityOfferingCode', '462d4a79-8cbc-459b-85f3-e391a303b31b-P', '01cbbc01-8d5d-436b-b922-d07428b36715')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('5bf51aeb-b967-49a0-b74d-1d62681a8a27', 'D', 'activityOfferingCode', '462d4a79-8cbc-459b-85f3-e391a303b31b', '47eaf5e6-7fb9-4104-9ded-bc50ab7f568f')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='8e0bf98e-1dac-4d6a-adcf-313f57b3ca71' and UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('61d08f23-fe4e-485b-8354-843c0670800a', 0, 'admin', TIMESTAMP '2014-07-01 09:55:02.302', 'admin', TIMESTAMP '2014-07-01 09:55:02.302', 'kuali.atp.2014Spring', '80545951-efc2-444a-9baa-933736e20b39', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', '', '', 'ENGL278R AO', '', '', '0e16e53f-5c29-4474-b498-46f4292a2151')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a8c9aad5-e1bf-4c37-bf1c-c9970d379166', 'kuali.attribute.course.evaluation.indicator', '0e16e53f-5c29-4474-b498-46f4292a2151', null, '181c2f8a-cc79-4bed-9118-e8be83021def')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('76f668a0-d829-4f58-8002-f24487bdceda', 'kuali.attribute.nonstd.ts.indicator', '0e16e53f-5c29-4474-b498-46f4292a2151', 0, '5f60d558-d289-4fc4-a2a3-b885c8f57d24')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d78045a5-cc3e-445b-b38a-4f30f93e68b4', 'kuali.attribute.max.enrollment.is.estimate', '0e16e53f-5c29-4474-b498-46f4292a2151', null, 'df5ce441-c95a-4b1b-baa6-5b0e4c964228')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('ac709b17-24ea-4edc-acd8-d939a5e7d924', 0, 'admin', TIMESTAMP '2014-07-01 09:55:02.302', 'admin', TIMESTAMP '2014-07-01 09:55:02.302', 'D', '', '', '', '0e16e53f-5c29-4474-b498-46f4292a2151', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '8b8c1ab7-8612-4c5b-9866-0231f96a9c6e')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('eda9ec64-4241-4fe9-8d34-359b112e24c1', 0, 'admin', TIMESTAMP '2014-07-01 09:55:02.302', 'admin', TIMESTAMP '2014-07-01 09:55:02.302', '', '0e16e53f-5c29-4474-b498-46f4292a2151', '', 'kuali.lu.code.honorsOffering', null, '2dc2e895-6cd6-4bfd-aae5-e4c326057f1c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f005593c-81fc-4026-8e66-0de7021967b4', 0, 'admin', TIMESTAMP '2014-07-01 09:55:02.302', 'admin', TIMESTAMP '2014-07-01 09:55:02.302', TIMESTAMP '2014-07-01 09:55:03.224', '', 'ENGL278R-FO-AO', '76b0c953-d465-4b56-88c1-3bdfec356f12', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL278R-FO-AO', '0e16e53f-5c29-4474-b498-46f4292a2151', 'd151ab50-2eef-4da0-bd3a-b87ee4d8c342')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='cce40410-de52-4401-aead-a0e97b207ed1', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:55:02.302', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='76b0c953-d465-4b56-88c1-3bdfec356f12', NAME='2', PRIVATE_NAME='2' where ID='22fb977a-ad2c-49aa-a018-d12c887ff60b' and VER_NBR=1
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('369a44cc-3737-47a4-a001-d6c97e4e37d0', '0e16e53f-5c29-4474-b498-46f4292a2151')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('9f816e97-b902-4edc-8c48-bcacca680dfb', 0, 'admin', TIMESTAMP '2014-07-01 09:55:02.302', 'admin', TIMESTAMP '2014-07-01 09:55:02.302', 'kuali.atp.2014Spring', '48f2aa1e-830c-4b79-9164-48dc19c85a95', '', '', '2001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2001', '2001', '', 'b9233229-e9d0-4893-b81a-2a3be0a70981')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7ae79142-2bfe-46a1-8046-711502556241', 'kuali.attribute.registration.group.is.generated', 'b9233229-e9d0-4893-b81a-2a3be0a70981', 1, '2066931f-5396-496b-9557-22757f1fe493')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('29c3f083-0550-4e42-b41d-e0f7d82f61e0', 'kuali.attribute.registration.group.aocluster.id', 'b9233229-e9d0-4893-b81a-2a3be0a70981', '22fb977a-ad2c-49aa-a018-d12c887ff60b', '0126c051-7d9e-48d7-b4d4-dc2a41efb416')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('b570f919-df58-4937-b7a7-73712edbd0a5', 0, 'admin', TIMESTAMP '2014-07-01 09:55:02.302', 'admin', TIMESTAMP '2014-07-01 09:55:02.302', '', '', '', '', 'b9233229-e9d0-4893-b81a-2a3be0a70981', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a9748c25-10cf-4368-b304-e7a35b8b5666')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('9b378e6e-0702-4bdd-8fc3-6929528160a3', 0, 'admin', TIMESTAMP '2014-07-01 09:55:02.302', 'admin', TIMESTAMP '2014-07-01 09:55:02.302', TIMESTAMP '2014-07-01 09:55:03.981', '', 'ENGL278R-FO-RG', '76b0c953-d465-4b56-88c1-3bdfec356f12', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL278R-FO-RG', 'b9233229-e9d0-4893-b81a-2a3be0a70981', 'b37d231e-7ee9-46a3-b70b-b93926bb3c86')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('8dc135fd-7f5e-4c80-a24c-beec7f2ca0fd', 0, 'admin', TIMESTAMP '2014-07-01 09:55:02.302', 'admin', TIMESTAMP '2014-07-01 09:55:02.302', TIMESTAMP '2014-07-01 09:55:04.102', '', 'ENGL278R-RG-AO', 'b9233229-e9d0-4893-b81a-2a3be0a70981', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL278R-RG-AO', '0e16e53f-5c29-4474-b498-46f4292a2151', 'c27ac8e0-ace6-483e-bde8-5b4636294841')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('ba663752-13e1-4bf3-a469-fd46762b1d6b', 0, 'admin', TIMESTAMP '2014-07-01 09:55:02.302', 'admin', TIMESTAMP '2014-07-01 09:55:02.302', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '20eab7e5-c970-40d5-91bd-4e32cf02088b')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('20eab7e5-c970-40d5-91bd-4e32cf02088b', '0e16e53f-5c29-4474-b498-46f4292a2151')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('20eab7e5-c970-40d5-91bd-4e32cf02088b', '76b0c953-d465-4b56-88c1-3bdfec356f12')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('9664b48f-8a24-46ee-b807-84f76c5406e4', '1a8bf915-aa3d-4d5a-9371-c74931b47a20', 'activityOfferingCode', '462d4a79-8cbc-459b-85f3-e391a303b31b-P', 'b71f4b41-e02d-4789-8fe7-1543e2850faf')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('5fa99d4f-1dd8-439e-b43b-d9a202902aa4', 'E', 'activityOfferingCode', '462d4a79-8cbc-459b-85f3-e391a303b31b', '70d073dc-58dc-4ce9-a1df-27b67c2c78de')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='1a8bf915-aa3d-4d5a-9371-c74931b47a20' and UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('91e46856-cc8e-42a7-984f-069878b28cbd', 0, 'admin', TIMESTAMP '2014-07-01 09:55:52.586', 'admin', TIMESTAMP '2014-07-01 09:55:52.586', 'kuali.atp.2014Spring', '80545951-efc2-444a-9baa-933736e20b39', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', '', '', 'ENGL278R AO', '', '', '5f33f49d-31ae-4f1f-b3f5-37219b8a814b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fcda3505-f785-426f-8300-0523426d4f1b', 'kuali.attribute.max.enrollment.is.estimate', '5f33f49d-31ae-4f1f-b3f5-37219b8a814b', null, 'd9faddca-66f8-47d3-9567-d58f05b6da53')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('53edffee-6abc-4230-9f33-1f56cc09538a', 'kuali.attribute.course.evaluation.indicator', '5f33f49d-31ae-4f1f-b3f5-37219b8a814b', null, 'fec10ea7-f799-4167-9587-0d41829f8fc9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c2183c96-a719-439f-98cb-1bfb22abe20f', 'kuali.attribute.nonstd.ts.indicator', '5f33f49d-31ae-4f1f-b3f5-37219b8a814b', 0, '68946221-66a1-420f-8be8-1731980d885a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('62e30c29-285a-43b0-b190-4ec174a88400', 0, 'admin', TIMESTAMP '2014-07-01 09:55:52.586', 'admin', TIMESTAMP '2014-07-01 09:55:52.586', 'E', '', '', '', '5f33f49d-31ae-4f1f-b3f5-37219b8a814b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '1dfdd32b-d82e-49b4-b8da-a5aadd66ac64')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('900767f1-f3db-4556-be66-eef2fe2be5fc', 0, 'admin', TIMESTAMP '2014-07-01 09:55:52.586', 'admin', TIMESTAMP '2014-07-01 09:55:52.586', '', '5f33f49d-31ae-4f1f-b3f5-37219b8a814b', '', 'kuali.lu.code.honorsOffering', null, '996b13cb-571f-4074-9b10-456fc19c4c87')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('208649c9-d61f-4c06-877d-b4093681abe2', 0, 'admin', TIMESTAMP '2014-07-01 09:55:52.586', 'admin', TIMESTAMP '2014-07-01 09:55:52.586', TIMESTAMP '2014-07-01 09:55:53.521', '', 'ENGL278R-FO-AO', '76b0c953-d465-4b56-88c1-3bdfec356f12', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL278R-FO-AO', '5f33f49d-31ae-4f1f-b3f5-37219b8a814b', 'a246f352-e6a2-4035-b866-94310578ce18')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='cce40410-de52-4401-aead-a0e97b207ed1', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:55:52.586', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='76b0c953-d465-4b56-88c1-3bdfec356f12', NAME='2', PRIVATE_NAME='2' where ID='22fb977a-ad2c-49aa-a018-d12c887ff60b' and VER_NBR=2
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('369a44cc-3737-47a4-a001-d6c97e4e37d0', '5f33f49d-31ae-4f1f-b3f5-37219b8a814b')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('90106b02-046e-451d-b04d-1d6390a93616', 0, 'admin', TIMESTAMP '2014-07-01 09:55:52.586', 'admin', TIMESTAMP '2014-07-01 09:55:52.586', 'kuali.atp.2014Spring', '48f2aa1e-830c-4b79-9164-48dc19c85a95', '', '', '2002', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2002', '2002', '', '19669f31-b4b2-484e-81b5-133a5f651501')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5f798730-769c-4807-8e30-2607aa4492dc', 'kuali.attribute.registration.group.aocluster.id', '19669f31-b4b2-484e-81b5-133a5f651501', '22fb977a-ad2c-49aa-a018-d12c887ff60b', '4b490888-59dc-4d25-9644-7ace2d6820fa')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('96e0e552-22ad-4862-bb31-0f27d6ba1e5e', 'kuali.attribute.registration.group.is.generated', '19669f31-b4b2-484e-81b5-133a5f651501', 1, '48701285-2726-4645-8778-37273067adb0')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('111e893e-29bd-485c-856b-c42450fe3c33', 0, 'admin', TIMESTAMP '2014-07-01 09:55:52.586', 'admin', TIMESTAMP '2014-07-01 09:55:52.586', '', '', '', '', '19669f31-b4b2-484e-81b5-133a5f651501', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'fb705467-b122-408a-bb3c-6e6e090c033e')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('de6d205f-a761-4109-b688-4d36d4d72334', 0, 'admin', TIMESTAMP '2014-07-01 09:55:52.586', 'admin', TIMESTAMP '2014-07-01 09:55:52.586', TIMESTAMP '2014-07-01 09:55:54.826', '', 'ENGL278R-FO-RG', '76b0c953-d465-4b56-88c1-3bdfec356f12', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL278R-FO-RG', '19669f31-b4b2-484e-81b5-133a5f651501', 'bac79af7-a837-4706-8698-a2e81dda4922')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('830c9e1b-17cd-44a1-9935-f374264504d6', 0, 'admin', TIMESTAMP '2014-07-01 09:55:52.586', 'admin', TIMESTAMP '2014-07-01 09:55:52.586', TIMESTAMP '2014-07-01 09:55:54.914', '', 'ENGL278R-RG-AO', '19669f31-b4b2-484e-81b5-133a5f651501', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL278R-RG-AO', '5f33f49d-31ae-4f1f-b3f5-37219b8a814b', '3a00de58-ac01-40ec-abb6-6ae2610b188e')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('aaf0b1bb-b394-41c6-8757-3d378bff9289', 0, 'admin', TIMESTAMP '2014-07-01 09:55:52.586', 'admin', TIMESTAMP '2014-07-01 09:55:52.586', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '93f8e62e-e436-4095-b036-e6bb7dd4c111')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('93f8e62e-e436-4095-b036-e6bb7dd4c111', '5f33f49d-31ae-4f1f-b3f5-37219b8a814b')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('93f8e62e-e436-4095-b036-e6bb7dd4c111', '76b0c953-d465-4b56-88c1-3bdfec356f12')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('82c9515f-b092-4840-97f4-449c8d3cfb39', '4cc824c2-2804-4f2e-9006-2548fd7594d6', 'activityOfferingCode', '462d4a79-8cbc-459b-85f3-e391a303b31b-P', '909513b8-cf01-4870-9f18-9533c4facb27')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('a571442f-e0be-457a-8cd8-ba111c718426', 'F', 'activityOfferingCode', '462d4a79-8cbc-459b-85f3-e391a303b31b', 'a97d46bb-7615-4352-a69b-9e802288bb6d')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='4cc824c2-2804-4f2e-9006-2548fd7594d6' and UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('97803917-b884-468d-afdc-3c3ab9ea1a82', 0, 'admin', TIMESTAMP '2014-07-01 09:57:16.861', 'admin', TIMESTAMP '2014-07-01 09:57:16.861', 'kuali.atp.2014Spring', '227f2322-3ad8-4886-9b09-d6289ce30d93', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', '', '', 'ENGL278R AO', '', '', 'ec68731a-42de-4b5b-9c21-27cdd573ed6c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1c5b3672-5d46-43cc-a9e0-dbc35d6f7648', 'kuali.attribute.nonstd.ts.indicator', 'ec68731a-42de-4b5b-9c21-27cdd573ed6c', 0, '9df4223a-2893-4f49-9a8a-1f206e9c72b7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e162e236-9621-427a-9dba-ccdf38f23591', 'kuali.attribute.max.enrollment.is.estimate', 'ec68731a-42de-4b5b-9c21-27cdd573ed6c', null, 'c2a907ea-60a9-4623-b5cd-61b83b44b7bf')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d0a9f7cc-4731-4dfd-ac1a-59484a0dd0b4', 'kuali.attribute.course.evaluation.indicator', 'ec68731a-42de-4b5b-9c21-27cdd573ed6c', null, '962576bd-0cf7-4fa0-8104-b1d71c67be3e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('af7d675f-f1b0-4c1c-b9f6-58a926449fb8', 0, 'admin', TIMESTAMP '2014-07-01 09:57:16.861', 'admin', TIMESTAMP '2014-07-01 09:57:16.861', 'F', '', '', '', 'ec68731a-42de-4b5b-9c21-27cdd573ed6c', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '387f5662-301c-4679-a5a9-8b6ba8a43513')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('f5399bc3-cf66-422d-b8bf-c184b21a6e8f', 0, 'admin', TIMESTAMP '2014-07-01 09:57:16.861', 'admin', TIMESTAMP '2014-07-01 09:57:16.861', '', 'ec68731a-42de-4b5b-9c21-27cdd573ed6c', '', 'kuali.lu.code.honorsOffering', null, '829b6605-1b55-4a17-b7d9-76b3115fc9e6')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ddc8ca15-e51e-4009-b785-b9ec34ba98e5', 0, 'admin', TIMESTAMP '2014-07-01 09:57:16.861', 'admin', TIMESTAMP '2014-07-01 09:57:16.861', TIMESTAMP '2014-07-01 09:57:17.811', '', 'ENGL278R-FO-AO', '6a4b4618-7cb9-4721-94bd-5a05c315d9de', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL278R-FO-AO', 'ec68731a-42de-4b5b-9c21-27cdd573ed6c', 'dbf76a47-4155-4eed-abc5-557b2f9d226f')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='3f6a9d8c-3403-42ce-9ba2-4aa110308a1c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:57:16.861', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='6a4b4618-7cb9-4721-94bd-5a05c315d9de', NAME='3', PRIVATE_NAME='3' where ID='1a2e89da-4d1a-41fe-b360-4dfdcc3a290f' and VER_NBR=0
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('19e5f30b-7ca4-4298-a000-5a4c05ba1062', 'ec68731a-42de-4b5b-9c21-27cdd573ed6c')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('2df593ba-bc2c-4c69-be04-eceb435f1aed', 0, 'admin', TIMESTAMP '2014-07-01 09:57:16.861', 'admin', TIMESTAMP '2014-07-01 09:57:16.861', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '77b2e4e3-8d22-4f4b-b5a1-f7cb78f0fa55')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('77b2e4e3-8d22-4f4b-b5a1-f7cb78f0fa55', 'ec68731a-42de-4b5b-9c21-27cdd573ed6c')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('77b2e4e3-8d22-4f4b-b5a1-f7cb78f0fa55', '6a4b4618-7cb9-4721-94bd-5a05c315d9de')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('4dcfe85e-f5cd-41ce-819d-202e5bfb687e', 0, 'admin', TIMESTAMP '2014-07-01 09:57:16.861', 'admin', TIMESTAMP '2014-07-01 09:57:16.861', 'kuali.atp.ExamPeriod.2014Spring', 'FCCF08F7BF135E91E040440A9B9A420D', '', '', '', 'kuali.lui.exam.offering.state.draft', 'kuali.lui.type.exam.offering.final', '', '', '', '', '', 'ea6816e6-e04e-4d98-a5f0-7d93d11aad94')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7271dcd6-cfc2-4dd3-9178-4d0ab03ea7cd', 'kuali.attribute.final.exam.activity.driver', 'ea6816e6-e04e-4d98-a5f0-7d93d11aad94', 'kuali.lu.type.activity.Lecture', '556669af-783c-4ba2-b5f6-90b8984bca52')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('19c350a1-f685-4d37-ad9c-c4f912502a9f', 'kuali.attribute.final.exam.driver', 'ea6816e6-e04e-4d98-a5f0-7d93d11aad94', 'PER_AO', 'c1575e12-366b-4915-b391-63a4bd0dc3db')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('0cbc11ab-4b71-4af4-bfa6-7a257ee9f7a9', 0, 'admin', TIMESTAMP '2014-07-01 09:57:16.861', 'admin', TIMESTAMP '2014-07-01 09:57:16.861', '', '', '', '', 'ea6816e6-e04e-4d98-a5f0-7d93d11aad94', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '2df1e72d-a160-4198-8c7e-67e312362029')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f30c3b14-4062-47f2-81fd-cd7929e3856c', 0, 'admin', TIMESTAMP '2014-07-01 09:57:16.861', 'admin', TIMESTAMP '2014-07-01 09:57:16.861', TIMESTAMP '2014-07-01 09:57:19.608', '', 'ea6816e6-e04e-4d98-a5f0-7d93d11aad94-FO-EO)', '6a4b4618-7cb9-4721-94bd-5a05c315d9de', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', 'ea6816e6-e04e-4d98-a5f0-7d93d11aad94-FO-EO', 'ea6816e6-e04e-4d98-a5f0-7d93d11aad94', 'f6357e86-af04-497f-b92e-3feb2ea40246')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a4a1a251-c370-4bcd-a32a-6c08dbb13fd3', 'AO1', 'f6357e86-af04-497f-b92e-3feb2ea40246', 'ec68731a-42de-4b5b-9c21-27cdd573ed6c', '97d49a8c-2edb-449e-a49e-c9e1991985b8')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('bef9b3f0-ced4-42c1-b558-c6a1fa0d2051', 'a3be7fe7-142c-4243-b521-2e07275998d2', 'activityOfferingCode', '462d4a79-8cbc-459b-85f3-e391a303b31b-P', '838017c8-87f1-4290-ba07-b457f2e29a63')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('4c30aa9c-f2a8-4f81-9c3b-4df2b21d60bb', 'G', 'activityOfferingCode', '462d4a79-8cbc-459b-85f3-e391a303b31b', 'fb6f5c40-0086-4720-b333-1fbfbba4a30c')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='a3be7fe7-142c-4243-b521-2e07275998d2' and UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('f57e7484-3ee3-4d55-98c5-55aec56a14d6', 0, 'admin', TIMESTAMP '2014-07-01 09:58:04.101', 'admin', TIMESTAMP '2014-07-01 09:58:04.101', 'kuali.atp.2014Spring', 'e4d9e600-e577-481c-8cc3-67b404ada089', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lab', '', '', 'ENGL278R AO', '', '', 'a820361a-f343-4d43-857f-3c12eca83353')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('751dd888-4d0b-4dcf-980d-5d4e681d0987', 'kuali.attribute.max.enrollment.is.estimate', 'a820361a-f343-4d43-857f-3c12eca83353', null, '2adb78c6-99a9-4b19-aa37-ba1814e3e884')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5a93cae6-163a-4ad8-b5a9-97f2223df5e3', 'kuali.attribute.course.evaluation.indicator', 'a820361a-f343-4d43-857f-3c12eca83353', null, '552d2e78-983a-4cc8-b676-b41b0b17736c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('33f67a70-13fa-4a21-b4a7-6c329f3ecd1a', 'kuali.attribute.nonstd.ts.indicator', 'a820361a-f343-4d43-857f-3c12eca83353', 0, 'f9db2a11-eeec-44b7-89d3-f684d06a43f3')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('1032a95d-c2cf-47b4-9cb1-0742ef8b99f6', 0, 'admin', TIMESTAMP '2014-07-01 09:58:04.101', 'admin', TIMESTAMP '2014-07-01 09:58:04.101', 'G', '', '', '', 'a820361a-f343-4d43-857f-3c12eca83353', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '95a0eef2-8a8b-43c8-977d-33983d80ef62')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('4d2a209f-5224-4f7b-9a7b-e88a88c3bc94', 0, 'admin', TIMESTAMP '2014-07-01 09:58:04.101', 'admin', TIMESTAMP '2014-07-01 09:58:04.101', '', 'a820361a-f343-4d43-857f-3c12eca83353', '', 'kuali.lu.code.honorsOffering', null, '9b7831ce-61f6-47cc-a1a4-446c0eeebaa9')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('392b7715-560c-4453-8a9f-87656102c96c', 0, 'admin', TIMESTAMP '2014-07-01 09:58:04.101', 'admin', TIMESTAMP '2014-07-01 09:58:04.101', TIMESTAMP '2014-07-01 09:58:05.278', '', 'ENGL278R-FO-AO', '6a4b4618-7cb9-4721-94bd-5a05c315d9de', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL278R-FO-AO', 'a820361a-f343-4d43-857f-3c12eca83353', '5ab7070f-2886-43e0-9b98-4b4e7e6d82ee')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='3f6a9d8c-3403-42ce-9ba2-4aa110308a1c', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:58:04.101', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='6a4b4618-7cb9-4721-94bd-5a05c315d9de', NAME='3', PRIVATE_NAME='3' where ID='1a2e89da-4d1a-41fe-b360-4dfdcc3a290f' and VER_NBR=1
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('2ea79f4d-4e1c-44c8-bb01-48f9d3fa6246', 'a820361a-f343-4d43-857f-3c12eca83353')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('99cd3fc2-5b27-4b3d-955c-26d9ac768111', 0, 'admin', TIMESTAMP '2014-07-01 09:58:04.101', 'admin', TIMESTAMP '2014-07-01 09:58:04.101', 'kuali.atp.2014Spring', '64c5a912-c558-42d6-b4b7-c073bcae2b3b', '', '', '3001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '3001', '3001', '', '683ac70d-df0e-47e4-b670-01989e1ff2aa')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ddeeadc4-014a-431f-8bfe-c2838a81d32f', 'kuali.attribute.registration.group.is.generated', '683ac70d-df0e-47e4-b670-01989e1ff2aa', 1, '6bd66c57-c6af-4b58-9a7d-229e4f55e3c8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('92f43f97-a82b-4336-beeb-1d4439bdb184', 'kuali.attribute.registration.group.aocluster.id', '683ac70d-df0e-47e4-b670-01989e1ff2aa', '1a2e89da-4d1a-41fe-b360-4dfdcc3a290f', '11987d64-f316-4804-a8a7-0ba6ed6fc48f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('1e59dfe1-431a-492e-87cb-9d840917d930', 0, 'admin', TIMESTAMP '2014-07-01 09:58:04.101', 'admin', TIMESTAMP '2014-07-01 09:58:04.101', '', '', '', '', '683ac70d-df0e-47e4-b670-01989e1ff2aa', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '895680c3-8ebc-4dee-84a9-eb598996d6c7')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('0c6f7278-5ad2-44c2-ab58-1d21e09d15a5', 0, 'admin', TIMESTAMP '2014-07-01 09:58:04.101', 'admin', TIMESTAMP '2014-07-01 09:58:04.101', TIMESTAMP '2014-07-01 09:58:06.221', '', 'ENGL278R-FO-RG', '6a4b4618-7cb9-4721-94bd-5a05c315d9de', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL278R-FO-RG', '683ac70d-df0e-47e4-b670-01989e1ff2aa', 'a6cb0d95-5d7a-4033-9bd3-a1b832adab6c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a5441050-5bbb-42ac-89a3-726bf70080a7', 0, 'admin', TIMESTAMP '2014-07-01 09:58:04.101', 'admin', TIMESTAMP '2014-07-01 09:58:04.101', TIMESTAMP '2014-07-01 09:58:06.336', '', 'ENGL278R-RG-AO', '683ac70d-df0e-47e4-b670-01989e1ff2aa', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL278R-RG-AO', 'a820361a-f343-4d43-857f-3c12eca83353', '7fd6c4d7-5c09-4ff8-b5b8-d991ac69224e')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('b66ac326-dd88-4770-b65b-b890a8346c5d', 0, 'admin', TIMESTAMP '2014-07-01 09:58:04.101', 'admin', TIMESTAMP '2014-07-01 09:58:04.101', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '9d0be568-ce0c-401a-b945-80659815b016')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('9d0be568-ce0c-401a-b945-80659815b016', 'a820361a-f343-4d43-857f-3c12eca83353')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('9d0be568-ce0c-401a-b945-80659815b016', '6a4b4618-7cb9-4721-94bd-5a05c315d9de')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('728da912-b07e-40e4-8ca6-4ab6aa08073f', '3bf4cce1-6654-48a6-99a1-5efe4593b4ff', 'activityOfferingCode', '462d4a79-8cbc-459b-85f3-e391a303b31b-P', '62c5d724-71d9-4be1-99cb-3039b183a339')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('49bfd3ff-e3f4-4649-af4c-2b97f50eb1f0', 'H', 'activityOfferingCode', '462d4a79-8cbc-459b-85f3-e391a303b31b', '61cc9775-632a-409c-9287-68b1a3fd5bd8')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='3bf4cce1-6654-48a6-99a1-5efe4593b4ff' and UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='462d4a79-8cbc-459b-85f3-e391a303b31b-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('979b3606-f912-4c74-9f3a-fea8186b8de5', 0, 'admin', TIMESTAMP '2014-07-01 09:59:26.983', 'admin', TIMESTAMP '2014-07-01 09:59:26.983', 'kuali.atp.2014Spring', 'e4d9e600-e577-481c-8cc3-67b404ada089', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lab', '', '', 'ENGL278R AO', '', '', '13e0ccf1-e3a7-4e2a-afce-1896563c39e8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f879c97c-e787-48be-806f-fce4e2638bfa', 'kuali.attribute.nonstd.ts.indicator', '13e0ccf1-e3a7-4e2a-afce-1896563c39e8', 0, 'f459c131-2e50-4372-9357-6650eef8bfb9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c913f785-441e-4155-9245-d5c6e1b66f8a', 'kuali.attribute.course.evaluation.indicator', '13e0ccf1-e3a7-4e2a-afce-1896563c39e8', null, '857dff50-2e74-42ec-8935-270de942de95')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b129d574-43ac-438f-9a6e-220cb14e7d47', 'kuali.attribute.max.enrollment.is.estimate', '13e0ccf1-e3a7-4e2a-afce-1896563c39e8', null, 'a38495f7-0951-4228-9be1-25e47e96690d')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('8f36bd96-8e04-4e8c-a8e4-6cfd32965f07', 0, 'admin', TIMESTAMP '2014-07-01 09:59:26.983', 'admin', TIMESTAMP '2014-07-01 09:59:26.983', 'H', '', '', '', '13e0ccf1-e3a7-4e2a-afce-1896563c39e8', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '2b5a5988-45af-4662-9fd1-1fa56dbb57ea')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('ee316581-3471-47ac-a0c4-db7eab4dfd1a', 0, 'admin', TIMESTAMP '2014-07-01 09:59:26.983', 'admin', TIMESTAMP '2014-07-01 09:59:26.983', '', '13e0ccf1-e3a7-4e2a-afce-1896563c39e8', '', 'kuali.lu.code.honorsOffering', null, '73118636-9b08-4f82-9060-01cb41cb0fac')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('047fe56d-55c0-4511-a1cb-4e876ce98092', 0, 'admin', TIMESTAMP '2014-07-01 09:59:26.983', 'admin', TIMESTAMP '2014-07-01 09:59:26.983', TIMESTAMP '2014-07-01 09:59:27.852', '', 'ENGL278R-FO-AO', '6a4b4618-7cb9-4721-94bd-5a05c315d9de', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL278R-FO-AO', '13e0ccf1-e3a7-4e2a-afce-1896563c39e8', '9f714c4d-869f-4fff-89f9-16d6cef4a178')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='3f6a9d8c-3403-42ce-9ba2-4aa110308a1c', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 09:59:26.983', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='6a4b4618-7cb9-4721-94bd-5a05c315d9de', NAME='3', PRIVATE_NAME='3' where ID='1a2e89da-4d1a-41fe-b360-4dfdcc3a290f' and VER_NBR=2
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('2ea79f4d-4e1c-44c8-bb01-48f9d3fa6246', '13e0ccf1-e3a7-4e2a-afce-1896563c39e8')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('45ec3972-1b65-44b0-8702-1d3436364cdc', 0, 'admin', TIMESTAMP '2014-07-01 09:59:26.983', 'admin', TIMESTAMP '2014-07-01 09:59:26.983', 'kuali.atp.2014Spring', '64c5a912-c558-42d6-b4b7-c073bcae2b3b', '', '', '3002', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '3002', '3002', '', '0510d1dc-3943-4904-a7ba-4329855f578c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6b875996-2a76-47a8-9b9e-35d857d8e955', 'kuali.attribute.registration.group.aocluster.id', '0510d1dc-3943-4904-a7ba-4329855f578c', '1a2e89da-4d1a-41fe-b360-4dfdcc3a290f', 'daa03211-0b0d-4573-940a-667832ae3e70')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('72b3db62-94ef-4641-8648-fdf06c976529', 'kuali.attribute.registration.group.is.generated', '0510d1dc-3943-4904-a7ba-4329855f578c', 1, '6b78d0d7-5144-46dd-b569-da8f70fd949b')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('e51cbb6f-1fd8-4ee0-beb9-3fa9a4f12417', 0, 'admin', TIMESTAMP '2014-07-01 09:59:26.983', 'admin', TIMESTAMP '2014-07-01 09:59:26.983', '', '', '', '', '0510d1dc-3943-4904-a7ba-4329855f578c', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ab714da2-08c8-4a0a-af6e-2c77cea3fa97')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('0e833f2d-d9d7-4770-a450-1521115b5634', 0, 'admin', TIMESTAMP '2014-07-01 09:59:26.983', 'admin', TIMESTAMP '2014-07-01 09:59:26.983', TIMESTAMP '2014-07-01 09:59:28.852', '', 'ENGL278R-FO-RG', '6a4b4618-7cb9-4721-94bd-5a05c315d9de', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL278R-FO-RG', '0510d1dc-3943-4904-a7ba-4329855f578c', '3e59f625-0adf-44a3-b8e7-ce5e238063fb')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f64b9450-e97e-4e3b-88c0-c1fae2d201da', 0, 'admin', TIMESTAMP '2014-07-01 09:59:26.983', 'admin', TIMESTAMP '2014-07-01 09:59:26.983', TIMESTAMP '2014-07-01 09:59:28.923', '', 'ENGL278R-RG-AO', '0510d1dc-3943-4904-a7ba-4329855f578c', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL278R-RG-AO', '13e0ccf1-e3a7-4e2a-afce-1896563c39e8', '17d2d2ce-4432-458b-a4a2-1946c83401f1')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('04e6dcfd-e691-4096-a357-1bace6052898', 0, 'admin', TIMESTAMP '2014-07-01 09:59:26.983', 'admin', TIMESTAMP '2014-07-01 09:59:26.983', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', 'e0451bb0-a35f-4a36-b3ef-6954cd6123bd')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('e0451bb0-a35f-4a36-b3ef-6954cd6123bd', '13e0ccf1-e3a7-4e2a-afce-1896563c39e8')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('e0451bb0-a35f-4a36-b3ef-6954cd6123bd', '6a4b4618-7cb9-4721-94bd-5a05c315d9de')
/
update KSEN_LUI set OBJ_ID='fa88f36d-c959-4f6f-8452-9636e448100a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', ATP_ID='kuali.atp.2014Spring', CLU_ID='65c39350-c26d-45ac-a627-214a9737a16a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS='', MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='f18fdf19-8ee4-4052-8efa-d2efdf20201d' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='a4c35cb0-01aa-4ebe-bb0d-e5165e42329c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='f18fdf19-8ee4-4052-8efa-d2efdf20201d', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='66e8bdb8-c665-439d-9a59-480e8b043212' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='b6ee1006-18bb-4a32-bb7b-6f62bdcbfe94', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', DESCR_FORMATTED='', LUI_ID='f18fdf19-8ee4-4052-8efa-d2efdf20201d', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='5b60658f-cfcb-4ae7-b053-5acab38999de' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='61d08f23-fe4e-485b-8354-843c0670800a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', ATP_ID='kuali.atp.2014Spring', CLU_ID='80545951-efc2-444a-9baa-933736e20b39', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS='', MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='0e16e53f-5c29-4474-b498-46f4292a2151' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='ac709b17-24ea-4edc-acd8-d939a5e7d924', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', LUI_CD='D', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='0e16e53f-5c29-4474-b498-46f4292a2151', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8b8c1ab7-8612-4c5b-9866-0231f96a9c6e' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='eda9ec64-4241-4fe9-8d34-359b112e24c1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', DESCR_FORMATTED='', LUI_ID='0e16e53f-5c29-4474-b498-46f4292a2151', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='2dc2e895-6cd6-4bfd-aae5-e4c326057f1c' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='da9ab6ff-d972-4b1d-af74-123ec6224d91', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', ATP_ID='kuali.atp.2014Spring', CLU_ID='48f2aa1e-830c-4b79-9164-48dc19c85a95', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Discussion/Lecture', LUI_STATE='kuali.lui.format.offering.state.planned', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Discussion/Lecture', DESCR_PLAIN='Courses with Discussion/Lecture', REF_URL='' where ID='76b0c953-d465-4b56-88c1-3bdfec356f12' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='b9ab0536-4e7c-4fe9-b9df-1051862b815b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Discussion/Lecture', LUI_ID='76b0c953-d465-4b56-88c1-3bdfec356f12', ORGID='', SHRT_NAME='DIS/LEC', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='520e3e61-5a68-4412-a436-9dfeed7f07cc' and VER_NBR=0
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='76b0c953-d465-4b56-88c1-3bdfec356f12'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('76b0c953-d465-4b56-88c1-3bdfec356f12', 'kuali.lui.type.activity.offering.discussion')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('76b0c953-d465-4b56-88c1-3bdfec356f12', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='91e46856-cc8e-42a7-984f-069878b28cbd', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', ATP_ID='kuali.atp.2014Spring', CLU_ID='80545951-efc2-444a-9baa-933736e20b39', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS='', MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='5f33f49d-31ae-4f1f-b3f5-37219b8a814b' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='62e30c29-285a-43b0-b190-4ec174a88400', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', LUI_CD='E', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='5f33f49d-31ae-4f1f-b3f5-37219b8a814b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1dfdd32b-d82e-49b4-b8da-a5aadd66ac64' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='900767f1-f3db-4556-be66-eef2fe2be5fc', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', DESCR_FORMATTED='', LUI_ID='5f33f49d-31ae-4f1f-b3f5-37219b8a814b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='996b13cb-571f-4074-9b10-456fc19c4c87' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='97803917-b884-468d-afdc-3c3ab9ea1a82', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', ATP_ID='kuali.atp.2014Spring', CLU_ID='227f2322-3ad8-4886-9b09-d6289ce30d93', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS='', MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='ec68731a-42de-4b5b-9c21-27cdd573ed6c' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='af7d675f-f1b0-4c1c-b9f6-58a926449fb8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', LUI_CD='F', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='ec68731a-42de-4b5b-9c21-27cdd573ed6c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='387f5662-301c-4679-a5a9-8b6ba8a43513' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='f5399bc3-cf66-422d-b8bf-c184b21a6e8f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', DESCR_FORMATTED='', LUI_ID='ec68731a-42de-4b5b-9c21-27cdd573ed6c', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='829b6605-1b55-4a17-b7d9-76b3115fc9e6' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='f4efd6e7-b24f-46bc-9b39-34dd463e1d1d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', ATP_ID='kuali.atp.2014Spring', CLU_ID='64c5a912-c558-42d6-b4b7-c073bcae2b3b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture/Lab', LUI_STATE='kuali.lui.format.offering.state.planned', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture/Lab', DESCR_PLAIN='Courses with Lecture/Lab', REF_URL='' where ID='6a4b4618-7cb9-4721-94bd-5a05c315d9de' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='7bf019f8-6886-4b84-844b-3a9fc0428616', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture/Lab', LUI_ID='6a4b4618-7cb9-4721-94bd-5a05c315d9de', ORGID='', SHRT_NAME='LEC/LAB', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='dd8a8dc5-4b81-47a1-b8a1-e745f41ba58d' and VER_NBR=0
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='6a4b4618-7cb9-4721-94bd-5a05c315d9de'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('6a4b4618-7cb9-4721-94bd-5a05c315d9de', 'kuali.lui.type.activity.offering.lab')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('6a4b4618-7cb9-4721-94bd-5a05c315d9de', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='f57e7484-3ee3-4d55-98c5-55aec56a14d6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', ATP_ID='kuali.atp.2014Spring', CLU_ID='e4d9e600-e577-481c-8cc3-67b404ada089', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS='', MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='a820361a-f343-4d43-857f-3c12eca83353' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='1032a95d-c2cf-47b4-9cb1-0742ef8b99f6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', LUI_CD='G', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='a820361a-f343-4d43-857f-3c12eca83353', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='95a0eef2-8a8b-43c8-977d-33983d80ef62' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='4d2a209f-5224-4f7b-9a7b-e88a88c3bc94', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', DESCR_FORMATTED='', LUI_ID='a820361a-f343-4d43-857f-3c12eca83353', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='9b7831ce-61f6-47cc-a1a4-446c0eeebaa9' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='979b3606-f912-4c74-9f3a-fea8186b8de5', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', ATP_ID='kuali.atp.2014Spring', CLU_ID='e4d9e600-e577-481c-8cc3-67b404ada089', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS='', MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='13e0ccf1-e3a7-4e2a-afce-1896563c39e8' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='8f36bd96-8e04-4e8c-a8e4-6cfd32965f07', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', LUI_CD='H', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='13e0ccf1-e3a7-4e2a-afce-1896563c39e8', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='2b5a5988-45af-4662-9fd1-1fa56dbb57ea' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='ee316581-3471-47ac-a0c4-db7eab4dfd1a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', DESCR_FORMATTED='', LUI_ID='13e0ccf1-e3a7-4e2a-afce-1896563c39e8', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='73118636-9b08-4f82-9060-01cb41cb0fac' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='980f26ba-d502-4ed3-ba02-780bd013f574', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', ATP_ID='kuali.atp.2014Spring', CLU_ID='a1ee5006-ecdd-4cfb-ba26-66fc6892e1ab', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS='', MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='82009d0f-1623-47b1-a0d5-13b483012e8b' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='5ef09701-902e-4385-b713-76f3726b5a69', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', LUI_CD='C', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='82009d0f-1623-47b1-a0d5-13b483012e8b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='926c36bd-9212-44e3-9c2e-b8291000d565' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='b6c5c681-ca6e-4c54-99e2-faa1a4fe5af9', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:00:48.296', DESCR_FORMATTED='', LUI_ID='82009d0f-1623-47b1-a0d5-13b483012e8b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='bfadf6b7-9b0d-4246-a72e-0bfc7c04bac5' and VER_NBR=0
/
update KSEN_LUI_ATTR set OBJ_ID='6410071f-4a32-4cbf-89f6-915e44f858ed', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='82009d0f-1623-47b1-a0d5-13b483012e8b', ATTR_VALUE=0 where ID='bc0f6898-d8ed-4ffd-8243-12bcbfad3016'
/
update KSEN_LUI set OBJ_ID='980f26ba-d502-4ed3-ba02-780bd013f574', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:02:25.116', ATP_ID='kuali.atp.2014Spring', CLU_ID='a1ee5006-ecdd-4cfb-ba26-66fc6892e1ab', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=6, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='82009d0f-1623-47b1-a0d5-13b483012e8b' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='239799f9-baac-4862-b23c-ccada4b3c2e6', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='82009d0f-1623-47b1-a0d5-13b483012e8b', ATTR_VALUE=0 where ID='f8013e74-159b-453d-8e42-48d775271459'
/
update KSEN_LUI_IDENT set OBJ_ID='5ef09701-902e-4385-b713-76f3726b5a69', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:02:25.116', LUI_CD='C', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='82009d0f-1623-47b1-a0d5-13b483012e8b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='926c36bd-9212-44e3-9c2e-b8291000d565' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='b6c5c681-ca6e-4c54-99e2-faa1a4fe5af9', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:02:25.116', DESCR_FORMATTED='', LUI_ID='82009d0f-1623-47b1-a0d5-13b483012e8b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='bfadf6b7-9b0d-4246-a72e-0bfc7c04bac5' and VER_NBR=1
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('0ecdcaec-98b2-47f0-b7fa-285da31e65db', 0, 'admin', TIMESTAMP '2014-07-01 10:02:25.116', 'admin', TIMESTAMP '2014-07-01 10:02:25.116', '', '', '', 'Schedule request set for ENGL278R - C', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'f43fb2d5-e77e-4afb-9492-81c1eb4161a9')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('f43fb2d5-e77e-4afb-9492-81c1eb4161a9', '82009d0f-1623-47b1-a0d5-13b483012e8b')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('372eaa27-c696-426f-95fb-75fc92eef9a3', 0, 'admin', TIMESTAMP '2014-07-01 10:02:25.116', 'admin', TIMESTAMP '2014-07-01 10:02:25.116', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'f43fb2d5-e77e-4afb-9492-81c1eb4161a9', 'e73ac843-7c54-4cc3-9d95-756ef3632f0c')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('c4f457af-47ea-4741-a997-33cde8dc07ce', 1, 'e73ac843-7c54-4cc3-9d95-756ef3632f0c', 'd0572616-4a31-4fcb-b667-b83703c7da24')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('d0572616-4a31-4fcb-b667-b83703c7da24', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('0c60fd48-395b-4fbf-92fe-cc8a4aa2e0af', 0, 'admin', TIMESTAMP '2014-07-01 10:02:25.116', 'admin', TIMESTAMP '2014-07-01 10:02:25.116', 'kuali.atp.2014Spring', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '12f5a41f-4258-4576-b55f-36813719db9c')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('69692719-eda9-4c7e-951a-4ab184786646', 1, '', '12f5a41f-4258-4576-b55f-36813719db9c', '6f2e215a-58ed-440b-96c5-9679961a456b')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('6f2e215a-58ed-440b-96c5-9679961a456b', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND=1, SCHED_RQST_ID='e73ac843-7c54-4cc3-9d95-756ef3632f0c' where ID='d0572616-4a31-4fcb-b667-b83703c7da24'
/
update KSEN_SCHED_RQST set OBJ_ID='372eaa27-c696-426f-95fb-75fc92eef9a3', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:02:25.116', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='12f5a41f-4258-4576-b55f-36813719db9c', SCHED_RQST_SET_ID='f43fb2d5-e77e-4afb-9492-81c1eb4161a9' where ID='e73ac843-7c54-4cc3-9d95-756ef3632f0c' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='d0572616-4a31-4fcb-b667-b83703c7da24'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('d0572616-4a31-4fcb-b667-b83703c7da24', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_LUI set OBJ_ID='980f26ba-d502-4ed3-ba02-780bd013f574', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:02:25.116', ATP_ID='kuali.atp.2014Spring', CLU_ID='a1ee5006-ecdd-4cfb-ba26-66fc6892e1ab', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=6, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='82009d0f-1623-47b1-a0d5-13b483012e8b' and VER_NBR=2
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('82009d0f-1623-47b1-a0d5-13b483012e8b', '12f5a41f-4258-4576-b55f-36813719db9c')
/
update KSEN_LUI set OBJ_ID='980f26ba-d502-4ed3-ba02-780bd013f574', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:02:25.116', ATP_ID='kuali.atp.2014Spring', CLU_ID='a1ee5006-ecdd-4cfb-ba26-66fc6892e1ab', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=6, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='82009d0f-1623-47b1-a0d5-13b483012e8b' and VER_NBR=3
/
update KSEN_LUI set OBJ_ID='da9ab6ff-d972-4b1d-af74-123ec6224d91', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:02:25.116', ATP_ID='kuali.atp.2014Spring', CLU_ID='48f2aa1e-830c-4b79-9164-48dc19c85a95', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Discussion/Lecture', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Discussion/Lecture', DESCR_PLAIN='Courses with Discussion/Lecture', REF_URL='' where ID='76b0c953-d465-4b56-88c1-3bdfec356f12' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='b9ab0536-4e7c-4fe9-b9df-1051862b815b', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:02:25.116', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Discussion/Lecture', LUI_ID='76b0c953-d465-4b56-88c1-3bdfec356f12', ORGID='', SHRT_NAME='DIS/LEC', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='520e3e61-5a68-4412-a436-9dfeed7f07cc' and VER_NBR=1
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='76b0c953-d465-4b56-88c1-3bdfec356f12'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('76b0c953-d465-4b56-88c1-3bdfec356f12', 'kuali.lui.type.activity.offering.discussion')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('76b0c953-d465-4b56-88c1-3bdfec356f12', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='9f816e97-b902-4edc-8c48-bcacca680dfb', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:02:25.116', ATP_ID='kuali.atp.2014Spring', CLU_ID='48f2aa1e-830c-4b79-9164-48dc19c85a95', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2001', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2001', DESCR_PLAIN='2001', REF_URL='' where ID='b9233229-e9d0-4893-b81a-2a3be0a70981' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='b570f919-df58-4937-b7a7-73712edbd0a5', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:02:25.116', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='b9233229-e9d0-4893-b81a-2a3be0a70981', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a9748c25-10cf-4368-b304-e7a35b8b5666' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='90106b02-046e-451d-b04d-1d6390a93616', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:02:25.116', ATP_ID='kuali.atp.2014Spring', CLU_ID='48f2aa1e-830c-4b79-9164-48dc19c85a95', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2002', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2002', DESCR_PLAIN='2002', REF_URL='' where ID='19669f31-b4b2-484e-81b5-133a5f651501' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='111e893e-29bd-485c-856b-c42450fe3c33', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:02:25.116', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='19669f31-b4b2-484e-81b5-133a5f651501', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='fb705467-b122-408a-bb3c-6e6e090c033e' and VER_NBR=0
/
update KSEN_CWL set OBJ_ID='bfff55d2-df53-4783-8d1e-940c45f2983c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:02:25.116', ALLOW_HOLD_UTIL_ENTRIES_IND=0, AUTO_PROCESSED_IND=1, CHECK_IN_REQ_IND=0, CONF_REQ_IND=1, EFF_DT='', EXPIR_DT='', MAX_SIZE='', REG_IN_FIRST_AAO_IND='', STD_DUR_TIME_QTY='', STD_DUR_TYPE='', CWL_STATE='kuali.course.waitlist.state.inactive', CWL_TYPE='kuali.course.waitlist.type.course.waitlist' where ID='350c3161-40cb-45ad-b969-a93f2a1c42bd' and VER_NBR=0
/
update KSEN_LUI_ATTR set OBJ_ID='fcda3505-f785-426f-8300-0523426d4f1b', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='5f33f49d-31ae-4f1f-b3f5-37219b8a814b', ATTR_VALUE=0 where ID='d9faddca-66f8-47d3-9567-d58f05b6da53'
/
update KSEN_LUI set OBJ_ID='91e46856-cc8e-42a7-984f-069878b28cbd', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:06:23.46', ATP_ID='kuali.atp.2014Spring', CLU_ID='80545951-efc2-444a-9baa-933736e20b39', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=3, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='5f33f49d-31ae-4f1f-b3f5-37219b8a814b' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='53edffee-6abc-4230-9f33-1f56cc09538a', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='5f33f49d-31ae-4f1f-b3f5-37219b8a814b', ATTR_VALUE=0 where ID='fec10ea7-f799-4167-9587-0d41829f8fc9'
/
update KSEN_LUI_IDENT set OBJ_ID='62e30c29-285a-43b0-b190-4ec174a88400', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:06:23.46', LUI_CD='E', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='5f33f49d-31ae-4f1f-b3f5-37219b8a814b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1dfdd32b-d82e-49b4-b8da-a5aadd66ac64' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='900767f1-f3db-4556-be66-eef2fe2be5fc', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:06:23.46', DESCR_FORMATTED='', LUI_ID='5f33f49d-31ae-4f1f-b3f5-37219b8a814b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='996b13cb-571f-4074-9b10-456fc19c4c87' and VER_NBR=1
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('84b71cd4-fda3-4b5a-8693-65f44c720ad9', 0, 'admin', TIMESTAMP '2014-07-01 10:06:23.46', 'admin', TIMESTAMP '2014-07-01 10:06:23.46', '', '', '', 'Schedule request set for ENGL278R - E', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '4130bc7c-3366-4a5e-ade1-be6170da4a5b')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('4130bc7c-3366-4a5e-ade1-be6170da4a5b', '5f33f49d-31ae-4f1f-b3f5-37219b8a814b')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('79790afd-01d0-4818-95e6-3250c8c3fab3', 0, 'admin', TIMESTAMP '2014-07-01 10:06:23.46', 'admin', TIMESTAMP '2014-07-01 10:06:23.46', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '4130bc7c-3366-4a5e-ade1-be6170da4a5b', 'd8bcfb95-02f1-4183-ad01-2483bb301c58')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('78869bb0-e37b-466f-bc82-4025aade78e5', 1, 'd8bcfb95-02f1-4183-ad01-2483bb301c58', 'cb9a78ca-55f1-45b0-b44b-ea2c22a0f7a8')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('cb9a78ca-55f1-45b0-b44b-ea2c22a0f7a8', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('b21b0d29-4852-43fb-a260-26656e333490', 0, 'admin', TIMESTAMP '2014-07-01 10:06:23.46', 'admin', TIMESTAMP '2014-07-01 10:06:23.46', 'kuali.atp.2014Spring', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '90755108-03d5-4245-831d-f59a2269c74b')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('b1f9776d-ba6e-4a29-b254-828fce067855', 1, '', '90755108-03d5-4245-831d-f59a2269c74b', '46f3facb-7562-415d-87ad-0a46d726532d')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('46f3facb-7562-415d-87ad-0a46d726532d', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND=1, SCHED_RQST_ID='d8bcfb95-02f1-4183-ad01-2483bb301c58' where ID='cb9a78ca-55f1-45b0-b44b-ea2c22a0f7a8'
/
update KSEN_SCHED_RQST set OBJ_ID='79790afd-01d0-4818-95e6-3250c8c3fab3', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:06:23.46', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='90755108-03d5-4245-831d-f59a2269c74b', SCHED_RQST_SET_ID='4130bc7c-3366-4a5e-ade1-be6170da4a5b' where ID='d8bcfb95-02f1-4183-ad01-2483bb301c58' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='cb9a78ca-55f1-45b0-b44b-ea2c22a0f7a8'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('cb9a78ca-55f1-45b0-b44b-ea2c22a0f7a8', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_LUI set OBJ_ID='91e46856-cc8e-42a7-984f-069878b28cbd', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:06:23.46', ATP_ID='kuali.atp.2014Spring', CLU_ID='80545951-efc2-444a-9baa-933736e20b39', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=3, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='5f33f49d-31ae-4f1f-b3f5-37219b8a814b' and VER_NBR=2
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('5f33f49d-31ae-4f1f-b3f5-37219b8a814b', '90755108-03d5-4245-831d-f59a2269c74b')
/
update KSEN_LUI set OBJ_ID='91e46856-cc8e-42a7-984f-069878b28cbd', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:06:23.46', ATP_ID='kuali.atp.2014Spring', CLU_ID='80545951-efc2-444a-9baa-933736e20b39', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=3, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='5f33f49d-31ae-4f1f-b3f5-37219b8a814b' and VER_NBR=3
/
update KSEN_LUI set OBJ_ID='90106b02-046e-451d-b04d-1d6390a93616', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:06:23.46', ATP_ID='kuali.atp.2014Spring', CLU_ID='48f2aa1e-830c-4b79-9164-48dc19c85a95', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2002', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2002', DESCR_PLAIN='2002', REF_URL='' where ID='19669f31-b4b2-484e-81b5-133a5f651501' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='111e893e-29bd-485c-856b-c42450fe3c33', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:06:23.46', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='19669f31-b4b2-484e-81b5-133a5f651501', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='fb705467-b122-408a-bb3c-6e6e090c033e' and VER_NBR=1
/
update KSEN_LUI set OBJ_ID='90106b02-046e-451d-b04d-1d6390a93616', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:06:23.46', ATP_ID='kuali.atp.2014Spring', CLU_ID='48f2aa1e-830c-4b79-9164-48dc19c85a95', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2002', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2002', DESCR_PLAIN='2002', REF_URL='' where ID='19669f31-b4b2-484e-81b5-133a5f651501' and VER_NBR=2
/
update KSEN_CWL set OBJ_ID='aaf0b1bb-b394-41c6-8757-3d378bff9289', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:06:23.46', ALLOW_HOLD_UTIL_ENTRIES_IND=0, AUTO_PROCESSED_IND=1, CHECK_IN_REQ_IND=0, CONF_REQ_IND=1, EFF_DT='', EXPIR_DT='', MAX_SIZE='', REG_IN_FIRST_AAO_IND='', STD_DUR_TIME_QTY='', STD_DUR_TYPE='', CWL_STATE='kuali.course.waitlist.state.inactive', CWL_TYPE='kuali.course.waitlist.type.course.waitlist' where ID='93f8e62e-e436-4095-b036-e6bb7dd4c111' and VER_NBR=0
/
update KSEN_LUI_ATTR set OBJ_ID='a8c9aad5-e1bf-4c37-bf1c-c9970d379166', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='0e16e53f-5c29-4474-b498-46f4292a2151', ATTR_VALUE=0 where ID='181c2f8a-cc79-4bed-9118-e8be83021def'
/
update KSEN_LUI set OBJ_ID='61d08f23-fe4e-485b-8354-843c0670800a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:08:28.305', ATP_ID='kuali.atp.2014Spring', CLU_ID='80545951-efc2-444a-9baa-933736e20b39', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=3, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='0e16e53f-5c29-4474-b498-46f4292a2151' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='d78045a5-cc3e-445b-b38a-4f30f93e68b4', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='0e16e53f-5c29-4474-b498-46f4292a2151', ATTR_VALUE=0 where ID='df5ce441-c95a-4b1b-baa6-5b0e4c964228'
/
update KSEN_LUI_IDENT set OBJ_ID='ac709b17-24ea-4edc-acd8-d939a5e7d924', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:08:28.305', LUI_CD='D', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='0e16e53f-5c29-4474-b498-46f4292a2151', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8b8c1ab7-8612-4c5b-9866-0231f96a9c6e' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='eda9ec64-4241-4fe9-8d34-359b112e24c1', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:08:28.305', DESCR_FORMATTED='', LUI_ID='0e16e53f-5c29-4474-b498-46f4292a2151', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='2dc2e895-6cd6-4bfd-aae5-e4c326057f1c' and VER_NBR=1
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('bc8ad51d-7803-462e-823d-d86a929a1b52', 0, 'admin', TIMESTAMP '2014-07-01 10:08:28.305', 'admin', TIMESTAMP '2014-07-01 10:08:28.305', '', '', '', 'Schedule request set for ENGL278R - D', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '7a6a5ac7-c4cc-47bc-b4fd-19722734613d')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('7a6a5ac7-c4cc-47bc-b4fd-19722734613d', '0e16e53f-5c29-4474-b498-46f4292a2151')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('b04e8983-b232-4b7a-bdeb-8587ab49f62a', 0, 'admin', TIMESTAMP '2014-07-01 10:08:28.305', 'admin', TIMESTAMP '2014-07-01 10:08:28.305', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '7a6a5ac7-c4cc-47bc-b4fd-19722734613d', '6da3eb05-8231-4563-a1f8-3019b8f1efdb')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('89f5abe5-737b-4668-970c-49fffc91bc3c', 1, '6da3eb05-8231-4563-a1f8-3019b8f1efdb', '63260a00-9233-4d96-a3eb-81a11cd226b1')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('63260a00-9233-4d96-a3eb-81a11cd226b1', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('287af509-92c7-47d4-8204-00ca615ffb42', 0, 'admin', TIMESTAMP '2014-07-01 10:08:28.305', 'admin', TIMESTAMP '2014-07-01 10:08:28.305', 'kuali.atp.2014Spring', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'd3ace9fc-2606-4dd6-95e4-ed1a670ed893')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('8036f285-9f3e-4be1-b5b0-e80f0ef2ab00', 1, '', 'd3ace9fc-2606-4dd6-95e4-ed1a670ed893', '3b43d615-1f12-4673-9c59-0dfedda12587')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('3b43d615-1f12-4673-9c59-0dfedda12587', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND=1, SCHED_RQST_ID='6da3eb05-8231-4563-a1f8-3019b8f1efdb' where ID='63260a00-9233-4d96-a3eb-81a11cd226b1'
/
update KSEN_SCHED_RQST set OBJ_ID='b04e8983-b232-4b7a-bdeb-8587ab49f62a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:08:28.305', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='d3ace9fc-2606-4dd6-95e4-ed1a670ed893', SCHED_RQST_SET_ID='7a6a5ac7-c4cc-47bc-b4fd-19722734613d' where ID='6da3eb05-8231-4563-a1f8-3019b8f1efdb' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='63260a00-9233-4d96-a3eb-81a11cd226b1'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('63260a00-9233-4d96-a3eb-81a11cd226b1', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_LUI set OBJ_ID='61d08f23-fe4e-485b-8354-843c0670800a', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:08:28.305', ATP_ID='kuali.atp.2014Spring', CLU_ID='80545951-efc2-444a-9baa-933736e20b39', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=3, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='0e16e53f-5c29-4474-b498-46f4292a2151' and VER_NBR=2
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('0e16e53f-5c29-4474-b498-46f4292a2151', 'd3ace9fc-2606-4dd6-95e4-ed1a670ed893')
/
update KSEN_LUI set OBJ_ID='61d08f23-fe4e-485b-8354-843c0670800a', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:08:28.305', ATP_ID='kuali.atp.2014Spring', CLU_ID='80545951-efc2-444a-9baa-933736e20b39', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=3, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='0e16e53f-5c29-4474-b498-46f4292a2151' and VER_NBR=3
/
update KSEN_LUI set OBJ_ID='9f816e97-b902-4edc-8c48-bcacca680dfb', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:08:28.305', ATP_ID='kuali.atp.2014Spring', CLU_ID='48f2aa1e-830c-4b79-9164-48dc19c85a95', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2001', DESCR_PLAIN='2001', REF_URL='' where ID='b9233229-e9d0-4893-b81a-2a3be0a70981' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='b570f919-df58-4937-b7a7-73712edbd0a5', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:08:28.305', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='b9233229-e9d0-4893-b81a-2a3be0a70981', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a9748c25-10cf-4368-b304-e7a35b8b5666' and VER_NBR=1
/
update KSEN_LUI set OBJ_ID='9f816e97-b902-4edc-8c48-bcacca680dfb', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:08:28.305', ATP_ID='kuali.atp.2014Spring', CLU_ID='48f2aa1e-830c-4b79-9164-48dc19c85a95', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2001', DESCR_PLAIN='2001', REF_URL='' where ID='b9233229-e9d0-4893-b81a-2a3be0a70981' and VER_NBR=2
/
update KSEN_CWL set OBJ_ID='ba663752-13e1-4bf3-a469-fd46762b1d6b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:08:28.305', ALLOW_HOLD_UTIL_ENTRIES_IND=0, AUTO_PROCESSED_IND=1, CHECK_IN_REQ_IND=0, CONF_REQ_IND=1, EFF_DT='', EXPIR_DT='', MAX_SIZE='', REG_IN_FIRST_AAO_IND='', STD_DUR_TIME_QTY='', STD_DUR_TYPE='', CWL_STATE='kuali.course.waitlist.state.inactive', CWL_TYPE='kuali.course.waitlist.type.course.waitlist' where ID='20eab7e5-c970-40d5-91bd-4e32cf02088b' and VER_NBR=0
/
update KSEN_LUI_ATTR set OBJ_ID='7b3413be-5550-408e-9237-d87f8c84a187', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='f18fdf19-8ee4-4052-8efa-d2efdf20201d', ATTR_VALUE=0 where ID='e81c303b-66e7-4ef4-9bd5-cdd8c5a077d4'
/
update KSEN_LUI set OBJ_ID='fa88f36d-c959-4f6f-8452-9636e448100a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:09:41.693', ATP_ID='kuali.atp.2014Spring', CLU_ID='65c39350-c26d-45ac-a627-214a9737a16a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=6, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='f18fdf19-8ee4-4052-8efa-d2efdf20201d' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='276a90c6-aeb9-4a11-ac61-70a19d4cb40a', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='f18fdf19-8ee4-4052-8efa-d2efdf20201d', ATTR_VALUE=0 where ID='a9393a70-204c-4ba9-9e58-493e8a4777d7'
/
update KSEN_LUI_IDENT set OBJ_ID='a4c35cb0-01aa-4ebe-bb0d-e5165e42329c', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:09:41.693', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='f18fdf19-8ee4-4052-8efa-d2efdf20201d', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='66e8bdb8-c665-439d-9a59-480e8b043212' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='b6ee1006-18bb-4a32-bb7b-6f62bdcbfe94', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:09:41.693', DESCR_FORMATTED='', LUI_ID='f18fdf19-8ee4-4052-8efa-d2efdf20201d', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='5b60658f-cfcb-4ae7-b053-5acab38999de' and VER_NBR=1
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('648b2b18-81e5-490a-8c7d-e4dd18e431f7', 0, 'admin', TIMESTAMP '2014-07-01 10:09:41.693', 'admin', TIMESTAMP '2014-07-01 10:09:41.693', '', '', '', 'Schedule request set for ENGL278R - B', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'f135e427-4cc5-460d-862f-035a52851c93')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('f135e427-4cc5-460d-862f-035a52851c93', 'f18fdf19-8ee4-4052-8efa-d2efdf20201d')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('7ab6869d-dc1e-4745-a183-a82d57f7d739', 0, 'admin', TIMESTAMP '2014-07-01 10:09:41.693', 'admin', TIMESTAMP '2014-07-01 10:09:41.693', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'f135e427-4cc5-460d-862f-035a52851c93', '00f9ef4a-d933-4daf-9ee5-ed69c8e91812')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('7e101bee-7ff3-4532-bd60-3f5a86f1682e', 1, '00f9ef4a-d933-4daf-9ee5-ed69c8e91812', '357333d4-6b93-4e52-bd60-3ab5b156e5b0')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('357333d4-6b93-4e52-bd60-3ab5b156e5b0', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('91194933-ffaa-444b-af7c-6eaf976f7cd7', 0, 'admin', TIMESTAMP '2014-07-01 10:09:41.693', 'admin', TIMESTAMP '2014-07-01 10:09:41.693', 'kuali.atp.2014Spring', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '8c2304cc-6bc0-4050-94df-ebc9eefb1e93')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('10b5ccb9-111b-4914-9c77-42bc838dd32a', 1, '', '8c2304cc-6bc0-4050-94df-ebc9eefb1e93', 'f1fde4dc-43a1-4f46-a42d-67a70c524ce8')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('f1fde4dc-43a1-4f46-a42d-67a70c524ce8', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND=1, SCHED_RQST_ID='00f9ef4a-d933-4daf-9ee5-ed69c8e91812' where ID='357333d4-6b93-4e52-bd60-3ab5b156e5b0'
/
update KSEN_SCHED_RQST set OBJ_ID='7ab6869d-dc1e-4745-a183-a82d57f7d739', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:09:41.693', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='8c2304cc-6bc0-4050-94df-ebc9eefb1e93', SCHED_RQST_SET_ID='f135e427-4cc5-460d-862f-035a52851c93' where ID='00f9ef4a-d933-4daf-9ee5-ed69c8e91812' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='357333d4-6b93-4e52-bd60-3ab5b156e5b0'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('357333d4-6b93-4e52-bd60-3ab5b156e5b0', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_LUI set OBJ_ID='fa88f36d-c959-4f6f-8452-9636e448100a', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:09:41.693', ATP_ID='kuali.atp.2014Spring', CLU_ID='65c39350-c26d-45ac-a627-214a9737a16a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=6, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='f18fdf19-8ee4-4052-8efa-d2efdf20201d' and VER_NBR=2
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('f18fdf19-8ee4-4052-8efa-d2efdf20201d', '8c2304cc-6bc0-4050-94df-ebc9eefb1e93')
/
update KSEN_LUI set OBJ_ID='fa88f36d-c959-4f6f-8452-9636e448100a', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:09:41.693', ATP_ID='kuali.atp.2014Spring', CLU_ID='65c39350-c26d-45ac-a627-214a9737a16a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=6, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='f18fdf19-8ee4-4052-8efa-d2efdf20201d' and VER_NBR=3
/
update KSEN_LUI set OBJ_ID='ee51db65-c9f7-40cf-9e81-392ed8782a71', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:09:41.693', ATP_ID='kuali.atp.2014Spring', CLU_ID='ed9e10ff-8739-40d0-83da-a2b92489e055', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1002', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1002', DESCR_PLAIN='1002', REF_URL='' where ID='f15c8404-7764-4420-99c0-0351aff1319e' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='d034a2fd-075a-4f13-9a1f-dc4b94f860c8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:09:41.693', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='f15c8404-7764-4420-99c0-0351aff1319e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='cb837686-9e52-42ac-8226-12f3deae627c' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='ee51db65-c9f7-40cf-9e81-392ed8782a71', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:09:41.693', ATP_ID='kuali.atp.2014Spring', CLU_ID='ed9e10ff-8739-40d0-83da-a2b92489e055', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1002', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1002', DESCR_PLAIN='1002', REF_URL='' where ID='f15c8404-7764-4420-99c0-0351aff1319e' and VER_NBR=1
/
update KSEN_CWL set OBJ_ID='dcf6606f-2dbf-46f3-8024-166948e39ff0', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:09:41.693', ALLOW_HOLD_UTIL_ENTRIES_IND=0, AUTO_PROCESSED_IND=1, CHECK_IN_REQ_IND=0, CONF_REQ_IND=1, EFF_DT='', EXPIR_DT='', MAX_SIZE='', REG_IN_FIRST_AAO_IND='', STD_DUR_TIME_QTY='', STD_DUR_TYPE='', CWL_STATE='kuali.course.waitlist.state.inactive', CWL_TYPE='kuali.course.waitlist.type.course.waitlist' where ID='9f61ed54-d1f9-4d8e-905a-9d5463457b27' and VER_NBR=0
/
update KSEN_LUI_ATTR set OBJ_ID='751dd888-4d0b-4dcf-980d-5d4e681d0987', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='a820361a-f343-4d43-857f-3c12eca83353', ATTR_VALUE=0 where ID='2adb78c6-99a9-4b19-aa37-ba1814e3e884'
/
update KSEN_LUI set OBJ_ID='f57e7484-3ee3-4d55-98c5-55aec56a14d6', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:12:06.82', ATP_ID='kuali.atp.2014Spring', CLU_ID='e4d9e600-e577-481c-8cc3-67b404ada089', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=3, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='a820361a-f343-4d43-857f-3c12eca83353' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='5a93cae6-163a-4ad8-b5a9-97f2223df5e3', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='a820361a-f343-4d43-857f-3c12eca83353', ATTR_VALUE=0 where ID='552d2e78-983a-4cc8-b676-b41b0b17736c'
/
update KSEN_LUI_IDENT set OBJ_ID='1032a95d-c2cf-47b4-9cb1-0742ef8b99f6', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:12:06.82', LUI_CD='G', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='a820361a-f343-4d43-857f-3c12eca83353', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='95a0eef2-8a8b-43c8-977d-33983d80ef62' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='4d2a209f-5224-4f7b-9a7b-e88a88c3bc94', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:12:06.82', DESCR_FORMATTED='', LUI_ID='a820361a-f343-4d43-857f-3c12eca83353', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='9b7831ce-61f6-47cc-a1a4-446c0eeebaa9' and VER_NBR=1
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('e4cd3396-cd0f-4578-ad84-ebe7fedc03c9', 0, 'admin', TIMESTAMP '2014-07-01 10:12:06.82', 'admin', TIMESTAMP '2014-07-01 10:12:06.82', '', '', '', 'Schedule request set for ENGL278R - G', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '6335ff7a-5510-449d-98dc-8f2318d661d7')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('6335ff7a-5510-449d-98dc-8f2318d661d7', 'a820361a-f343-4d43-857f-3c12eca83353')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('c34434aa-c732-4198-baa6-946b3070111f', 0, 'admin', TIMESTAMP '2014-07-01 10:12:06.82', 'admin', TIMESTAMP '2014-07-01 10:12:06.82', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '6335ff7a-5510-449d-98dc-8f2318d661d7', 'f6f3b9ce-36de-4b81-8352-97804f007cb9')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('8a1bd354-931a-4ac7-8c10-8c8cd7a4632a', 1, 'f6f3b9ce-36de-4b81-8352-97804f007cb9', '7e4bce21-3078-4eba-ba09-7a940b526d79')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('7e4bce21-3078-4eba-ba09-7a940b526d79', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('5eed5f9e-49f5-4627-b6c9-96a87c29c767', 0, 'admin', TIMESTAMP '2014-07-01 10:12:06.82', 'admin', TIMESTAMP '2014-07-01 10:12:06.82', 'kuali.atp.2014Spring', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'ba268d0e-ba8e-4ef0-8185-df8eca7bcfd2')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('e2cc1c90-df39-4e29-8739-3c818d734b51', 1, '', 'ba268d0e-ba8e-4ef0-8185-df8eca7bcfd2', '070afb94-ae76-410b-9ceb-ec284e54c0c8')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('070afb94-ae76-410b-9ceb-ec284e54c0c8', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND=1, SCHED_RQST_ID='f6f3b9ce-36de-4b81-8352-97804f007cb9' where ID='7e4bce21-3078-4eba-ba09-7a940b526d79'
/
update KSEN_SCHED_RQST set OBJ_ID='c34434aa-c732-4198-baa6-946b3070111f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:12:06.82', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='ba268d0e-ba8e-4ef0-8185-df8eca7bcfd2', SCHED_RQST_SET_ID='6335ff7a-5510-449d-98dc-8f2318d661d7' where ID='f6f3b9ce-36de-4b81-8352-97804f007cb9' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='7e4bce21-3078-4eba-ba09-7a940b526d79'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('7e4bce21-3078-4eba-ba09-7a940b526d79', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_LUI set OBJ_ID='f57e7484-3ee3-4d55-98c5-55aec56a14d6', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:12:06.82', ATP_ID='kuali.atp.2014Spring', CLU_ID='e4d9e600-e577-481c-8cc3-67b404ada089', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=3, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='a820361a-f343-4d43-857f-3c12eca83353' and VER_NBR=2
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('a820361a-f343-4d43-857f-3c12eca83353', 'ba268d0e-ba8e-4ef0-8185-df8eca7bcfd2')
/
update KSEN_LUI set OBJ_ID='f57e7484-3ee3-4d55-98c5-55aec56a14d6', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:12:06.82', ATP_ID='kuali.atp.2014Spring', CLU_ID='e4d9e600-e577-481c-8cc3-67b404ada089', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=3, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='a820361a-f343-4d43-857f-3c12eca83353' and VER_NBR=3
/
update KSEN_LUI set OBJ_ID='f4efd6e7-b24f-46bc-9b39-34dd463e1d1d', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:12:06.82', ATP_ID='kuali.atp.2014Spring', CLU_ID='64c5a912-c558-42d6-b4b7-c073bcae2b3b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture/Lab', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture/Lab', DESCR_PLAIN='Courses with Lecture/Lab', REF_URL='' where ID='6a4b4618-7cb9-4721-94bd-5a05c315d9de' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='7bf019f8-6886-4b84-844b-3a9fc0428616', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:12:06.82', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture/Lab', LUI_ID='6a4b4618-7cb9-4721-94bd-5a05c315d9de', ORGID='', SHRT_NAME='LEC/LAB', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='dd8a8dc5-4b81-47a1-b8a1-e745f41ba58d' and VER_NBR=1
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='6a4b4618-7cb9-4721-94bd-5a05c315d9de'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('6a4b4618-7cb9-4721-94bd-5a05c315d9de', 'kuali.lui.type.activity.offering.lab')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('6a4b4618-7cb9-4721-94bd-5a05c315d9de', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='99cd3fc2-5b27-4b3d-955c-26d9ac768111', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:12:06.82', ATP_ID='kuali.atp.2014Spring', CLU_ID='64c5a912-c558-42d6-b4b7-c073bcae2b3b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='3001', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='3001', DESCR_PLAIN='3001', REF_URL='' where ID='683ac70d-df0e-47e4-b670-01989e1ff2aa' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='1e59dfe1-431a-492e-87cb-9d840917d930', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:12:06.82', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='683ac70d-df0e-47e4-b670-01989e1ff2aa', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='895680c3-8ebc-4dee-84a9-eb598996d6c7' and VER_NBR=0
/
update KSEN_CWL set OBJ_ID='b66ac326-dd88-4770-b65b-b890a8346c5d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:12:06.82', ALLOW_HOLD_UTIL_ENTRIES_IND=0, AUTO_PROCESSED_IND=1, CHECK_IN_REQ_IND=0, CONF_REQ_IND=1, EFF_DT='', EXPIR_DT='', MAX_SIZE='', REG_IN_FIRST_AAO_IND='', STD_DUR_TIME_QTY='', STD_DUR_TYPE='', CWL_STATE='kuali.course.waitlist.state.inactive', CWL_TYPE='kuali.course.waitlist.type.course.waitlist' where ID='9d0be568-ce0c-401a-b945-80659815b016' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='97803917-b884-468d-afdc-3c3ab9ea1a82', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:13:22.564', ATP_ID='kuali.atp.2014Spring', CLU_ID='227f2322-3ad8-4886-9b09-d6289ce30d93', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=6, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='ec68731a-42de-4b5b-9c21-27cdd573ed6c' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='e162e236-9621-427a-9dba-ccdf38f23591', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='ec68731a-42de-4b5b-9c21-27cdd573ed6c', ATTR_VALUE=0 where ID='c2a907ea-60a9-4623-b5cd-61b83b44b7bf'
/
update KSEN_LUI_ATTR set OBJ_ID='d0a9f7cc-4731-4dfd-ac1a-59484a0dd0b4', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='ec68731a-42de-4b5b-9c21-27cdd573ed6c', ATTR_VALUE=0 where ID='962576bd-0cf7-4fa0-8104-b1d71c67be3e'
/
update KSEN_LUI_IDENT set OBJ_ID='af7d675f-f1b0-4c1c-b9f6-58a926449fb8', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:13:22.564', LUI_CD='F', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='ec68731a-42de-4b5b-9c21-27cdd573ed6c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='387f5662-301c-4679-a5a9-8b6ba8a43513' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='f5399bc3-cf66-422d-b8bf-c184b21a6e8f', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:13:22.564', DESCR_FORMATTED='', LUI_ID='ec68731a-42de-4b5b-9c21-27cdd573ed6c', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='829b6605-1b55-4a17-b7d9-76b3115fc9e6' and VER_NBR=1
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('9fee7daa-0031-441a-ab7c-dafbb9e56252', 0, 'admin', TIMESTAMP '2014-07-01 10:13:22.564', 'admin', TIMESTAMP '2014-07-01 10:13:22.564', '', '', '', 'Schedule request set for ENGL278R - F', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'b89cf26d-1eb3-4736-8400-0df0df8080fa')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('b89cf26d-1eb3-4736-8400-0df0df8080fa', 'ec68731a-42de-4b5b-9c21-27cdd573ed6c')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('19b1d64b-9438-40cf-a9e4-5cea956be2af', 0, 'admin', TIMESTAMP '2014-07-01 10:13:22.564', 'admin', TIMESTAMP '2014-07-01 10:13:22.564', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'b89cf26d-1eb3-4736-8400-0df0df8080fa', 'ce2478a6-2df5-4e99-ab8a-b5996b7ba85f')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('69693200-fbf2-42a3-ad8a-5173345e7539', 1, 'ce2478a6-2df5-4e99-ab8a-b5996b7ba85f', '84d5f80b-31ac-4a7b-97c0-88e280de6f91')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('84d5f80b-31ac-4a7b-97c0-88e280de6f91', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('2292af90-e00e-45cd-97d4-52fc3408e875', 0, 'admin', TIMESTAMP '2014-07-01 10:13:22.564', 'admin', TIMESTAMP '2014-07-01 10:13:22.564', 'kuali.atp.2014Spring', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'a11b1f92-e108-4524-b653-8d60c7dcc265')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('e5503b13-680a-483f-bd45-6053a60d4b41', 1, '', 'a11b1f92-e108-4524-b653-8d60c7dcc265', 'db30080a-95d7-45ac-be27-34ad947abe1e')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('db30080a-95d7-45ac-be27-34ad947abe1e', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND=1, SCHED_RQST_ID='ce2478a6-2df5-4e99-ab8a-b5996b7ba85f' where ID='84d5f80b-31ac-4a7b-97c0-88e280de6f91'
/
update KSEN_SCHED_RQST set OBJ_ID='19b1d64b-9438-40cf-a9e4-5cea956be2af', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:13:22.564', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='a11b1f92-e108-4524-b653-8d60c7dcc265', SCHED_RQST_SET_ID='b89cf26d-1eb3-4736-8400-0df0df8080fa' where ID='ce2478a6-2df5-4e99-ab8a-b5996b7ba85f' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='84d5f80b-31ac-4a7b-97c0-88e280de6f91'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('84d5f80b-31ac-4a7b-97c0-88e280de6f91', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_LUI set OBJ_ID='97803917-b884-468d-afdc-3c3ab9ea1a82', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:13:22.564', ATP_ID='kuali.atp.2014Spring', CLU_ID='227f2322-3ad8-4886-9b09-d6289ce30d93', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=6, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='ec68731a-42de-4b5b-9c21-27cdd573ed6c' and VER_NBR=2
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('ec68731a-42de-4b5b-9c21-27cdd573ed6c', 'a11b1f92-e108-4524-b653-8d60c7dcc265')
/
update KSEN_LUI set OBJ_ID='97803917-b884-468d-afdc-3c3ab9ea1a82', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:13:22.564', ATP_ID='kuali.atp.2014Spring', CLU_ID='227f2322-3ad8-4886-9b09-d6289ce30d93', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=6, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='ec68731a-42de-4b5b-9c21-27cdd573ed6c' and VER_NBR=3
/
update KSEN_LUI set OBJ_ID='99cd3fc2-5b27-4b3d-955c-26d9ac768111', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:13:22.564', ATP_ID='kuali.atp.2014Spring', CLU_ID='64c5a912-c558-42d6-b4b7-c073bcae2b3b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='3001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='3001', DESCR_PLAIN='3001', REF_URL='' where ID='683ac70d-df0e-47e4-b670-01989e1ff2aa' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='1e59dfe1-431a-492e-87cb-9d840917d930', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:13:22.564', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='683ac70d-df0e-47e4-b670-01989e1ff2aa', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='895680c3-8ebc-4dee-84a9-eb598996d6c7' and VER_NBR=1
/
update KSEN_LUI set OBJ_ID='45ec3972-1b65-44b0-8702-1d3436364cdc', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:13:22.564', ATP_ID='kuali.atp.2014Spring', CLU_ID='64c5a912-c558-42d6-b4b7-c073bcae2b3b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='3002', LUI_STATE='kuali.lui.registration.group.state.pending', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='3002', DESCR_PLAIN='3002', REF_URL='' where ID='0510d1dc-3943-4904-a7ba-4329855f578c' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='e51cbb6f-1fd8-4ee0-beb9-3fa9a4f12417', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:13:22.564', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='0510d1dc-3943-4904-a7ba-4329855f578c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='ab714da2-08c8-4a0a-af6e-2c77cea3fa97' and VER_NBR=0
/
update KSEN_CWL set OBJ_ID='2df593ba-bc2c-4c69-be04-eceb435f1aed', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:13:22.564', ALLOW_HOLD_UTIL_ENTRIES_IND=0, AUTO_PROCESSED_IND=1, CHECK_IN_REQ_IND=0, CONF_REQ_IND=1, EFF_DT='', EXPIR_DT='', MAX_SIZE='', REG_IN_FIRST_AAO_IND='', STD_DUR_TIME_QTY='', STD_DUR_TYPE='', CWL_STATE='kuali.course.waitlist.state.inactive', CWL_TYPE='kuali.course.waitlist.type.course.waitlist' where ID='77b2e4e3-8d22-4f4b-b5a1-f7cb78f0fa55' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='979b3606-f912-4c74-9f3a-fea8186b8de5', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:15:07.646', ATP_ID='kuali.atp.2014Spring', CLU_ID='e4d9e600-e577-481c-8cc3-67b404ada089', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=3, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='13e0ccf1-e3a7-4e2a-afce-1896563c39e8' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='c913f785-441e-4155-9245-d5c6e1b66f8a', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='13e0ccf1-e3a7-4e2a-afce-1896563c39e8', ATTR_VALUE=0 where ID='857dff50-2e74-42ec-8935-270de942de95'
/
update KSEN_LUI_ATTR set OBJ_ID='b129d574-43ac-438f-9a6e-220cb14e7d47', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='13e0ccf1-e3a7-4e2a-afce-1896563c39e8', ATTR_VALUE=0 where ID='a38495f7-0951-4228-9be1-25e47e96690d'
/
update KSEN_LUI_IDENT set OBJ_ID='8f36bd96-8e04-4e8c-a8e4-6cfd32965f07', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:15:07.646', LUI_CD='H', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='13e0ccf1-e3a7-4e2a-afce-1896563c39e8', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='2b5a5988-45af-4662-9fd1-1fa56dbb57ea' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='ee316581-3471-47ac-a0c4-db7eab4dfd1a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:15:07.646', DESCR_FORMATTED='', LUI_ID='13e0ccf1-e3a7-4e2a-afce-1896563c39e8', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='73118636-9b08-4f82-9060-01cb41cb0fac' and VER_NBR=1
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('11e209ff-ee53-49ae-aec2-e7ba9919adf1', 0, 'admin', TIMESTAMP '2014-07-01 10:15:07.646', 'admin', TIMESTAMP '2014-07-01 10:15:07.646', '', '', '', 'Schedule request set for ENGL278R - H', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '1a985bbc-b1a3-44df-93ec-7b8cd2c1c42e')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('1a985bbc-b1a3-44df-93ec-7b8cd2c1c42e', '13e0ccf1-e3a7-4e2a-afce-1896563c39e8')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('924cfc71-70f9-4fd7-a08d-4868bf42a9a9', 0, 'admin', TIMESTAMP '2014-07-01 10:15:07.646', 'admin', TIMESTAMP '2014-07-01 10:15:07.646', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '1a985bbc-b1a3-44df-93ec-7b8cd2c1c42e', 'b2093954-f6bf-439a-8107-60b995cf8b5a')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('7848f4c9-eae2-4ac6-9295-57f43bbea0a8', 1, 'b2093954-f6bf-439a-8107-60b995cf8b5a', 'f8e83979-cae6-4674-8260-064b6607266c')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('f8e83979-cae6-4674-8260-064b6607266c', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('ea16851b-850c-447c-ac2e-b01d7da5c243', 0, 'admin', TIMESTAMP '2014-07-01 10:15:07.646', 'admin', TIMESTAMP '2014-07-01 10:15:07.646', 'kuali.atp.2014Spring', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'baff18d9-0d0f-42a1-9220-ea832dcc355a')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('a085776a-6fec-4e82-832b-a480272bd058', 1, '', 'baff18d9-0d0f-42a1-9220-ea832dcc355a', '28c3abe3-320c-42f6-aad2-fb04e73c7526')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('28c3abe3-320c-42f6-aad2-fb04e73c7526', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND=1, SCHED_RQST_ID='b2093954-f6bf-439a-8107-60b995cf8b5a' where ID='f8e83979-cae6-4674-8260-064b6607266c'
/
update KSEN_SCHED_RQST set OBJ_ID='924cfc71-70f9-4fd7-a08d-4868bf42a9a9', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:15:07.646', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='baff18d9-0d0f-42a1-9220-ea832dcc355a', SCHED_RQST_SET_ID='1a985bbc-b1a3-44df-93ec-7b8cd2c1c42e' where ID='b2093954-f6bf-439a-8107-60b995cf8b5a' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='f8e83979-cae6-4674-8260-064b6607266c'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('f8e83979-cae6-4674-8260-064b6607266c', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_LUI set OBJ_ID='979b3606-f912-4c74-9f3a-fea8186b8de5', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:15:07.646', ATP_ID='kuali.atp.2014Spring', CLU_ID='e4d9e600-e577-481c-8cc3-67b404ada089', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=3, MIN_SEATS='', NAME='ENGL278R AO', DESCR_PLAIN='', REF_URL='' where ID='13e0ccf1-e3a7-4e2a-afce-1896563c39e8' and VER_NBR=2
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('13e0ccf1-e3a7-4e2a-afce-1896563c39e8', 'baff18d9-0d0f-42a1-9220-ea832dcc355a')
/
update KSEN_LUI set OBJ_ID='45ec3972-1b65-44b0-8702-1d3436364cdc', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:15:07.646', ATP_ID='kuali.atp.2014Spring', CLU_ID='64c5a912-c558-42d6-b4b7-c073bcae2b3b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='3002', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='3002', DESCR_PLAIN='3002', REF_URL='' where ID='0510d1dc-3943-4904-a7ba-4329855f578c' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='e51cbb6f-1fd8-4ee0-beb9-3fa9a4f12417', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:15:07.646', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='0510d1dc-3943-4904-a7ba-4329855f578c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='ab714da2-08c8-4a0a-af6e-2c77cea3fa97' and VER_NBR=1
/
update KSEN_LUI set OBJ_ID='45ec3972-1b65-44b0-8702-1d3436364cdc', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:15:07.646', ATP_ID='kuali.atp.2014Spring', CLU_ID='64c5a912-c558-42d6-b4b7-c073bcae2b3b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='3002', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='3002', DESCR_PLAIN='3002', REF_URL='' where ID='0510d1dc-3943-4904-a7ba-4329855f578c' and VER_NBR=2
/
update KSEN_CWL set OBJ_ID='04e6dcfd-e691-4096-a357-1bace6052898', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:15:07.646', ALLOW_HOLD_UTIL_ENTRIES_IND=0, AUTO_PROCESSED_IND=1, CHECK_IN_REQ_IND=0, CONF_REQ_IND=1, EFF_DT='', EXPIR_DT='', MAX_SIZE='', REG_IN_FIRST_AAO_IND='', STD_DUR_TIME_QTY='', STD_DUR_TYPE='', CWL_STATE='kuali.course.waitlist.state.inactive', CWL_TYPE='kuali.course.waitlist.type.course.waitlist' where ID='e0451bb0-a35f-4a36-b3ef-6954cd6123bd' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='94262ed4-563c-41bc-9b0f-c04941635a84', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:16:54.401', ATP_ID='kuali.atp.2014Spring', CLU_ID='6c622f2d-842f-4487-ad06-3d3d46d6fd4e', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecure/Lab/Discussion', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture/Lab/Discussion', DESCR_PLAIN='Courses with Lecure/Lab/Discussion', REF_URL='' where ID='9cddb1af-6bc4-4e5d-8b44-d2583d41c9f6' and VER_NBR=2
/
update KSEN_LUI_ATTR set OBJ_ID='d6ad8676-78a0-4838-9381-fb16b8e48019', ATTR_KEY='kuali.attribute.grade.roster.level.type.key', OWNER_ID='9cddb1af-6bc4-4e5d-8b44-d2583d41c9f6', ATTR_VALUE='kuali.lu.type.activity.Lab' where ID='827cb4b7-d0d5-45a1-9baf-db0d6d288d38'
/
update KSEN_LUI_IDENT set OBJ_ID='2fd154b9-ba9e-45e5-bbd9-e37c913d3db9', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:16:54.401', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture/Lab/Discussion', LUI_ID='9cddb1af-6bc4-4e5d-8b44-d2583d41c9f6', ORGID='', SHRT_NAME='LEC/LAB/DIS', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b6d9e821-7b6f-466e-8714-f589247e830f' and VER_NBR=2
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='9cddb1af-6bc4-4e5d-8b44-d2583d41c9f6'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('9cddb1af-6bc4-4e5d-8b44-d2583d41c9f6', 'kuali.lui.type.activity.offering.discussion')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('9cddb1af-6bc4-4e5d-8b44-d2583d41c9f6', 'kuali.lui.type.activity.offering.lab')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('9cddb1af-6bc4-4e5d-8b44-d2583d41c9f6', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('54513f85-c7e4-4b66-8535-915ad6ae4fcb', 0, 'admin', TIMESTAMP '2014-07-01 10:16:54.401', 'admin', TIMESTAMP '2014-07-01 10:16:54.401', 'kuali.atp.2014Spring', '2d9a2baf-dba8-4414-92d8-1a57a6f3e1e0', '', '', 'Courses with Lecture Only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture Only', '', '7bd32717-04f1-434b-b567-59a9108c3970')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('304fd34c-1415-4739-a78b-689314f49d93', 'kuali.attribute.final.exam.level.type', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lu.type.activity.Lecture', '7ffdcd3d-3d03-4694-b918-f87e6e2e93a7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ba8beb5c-2258-4ede-839a-35aa0fd7108a', 'kuali.attribute.grade.roster.level.type.key', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lu.type.activity.Lecture', '093f8671-bfe8-4923-b86f-3133d2c61902')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7c279c3d-3833-4cd2-ac99-1ab1e8c42659', 'regCodePrefix', '7bd32717-04f1-434b-b567-59a9108c3970', '2', 'e338ced2-3378-4b5d-91cb-4de3aac04f07')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('072ed3f0-fe77-4a38-a149-58466a72dd28', 0, 'admin', TIMESTAMP '2014-07-01 10:16:54.401', 'admin', TIMESTAMP '2014-07-01 10:16:54.401', '', '', '', 'Lecture Only', '7bd32717-04f1-434b-b567-59a9108c3970', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '73c96249-1c11-49f0-9315-8e242159a8be')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e8dd815f-b8b3-4499-8b6b-505b2f2b17b7', 0, 'admin', TIMESTAMP '2014-07-01 10:16:54.401', 'admin', TIMESTAMP '2014-07-01 10:16:54.401', TIMESTAMP '2014-07-01 10:16:54.867', '', 'CHEM237-CO-FO', 'a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'CHEM237-CO-FO', '7bd32717-04f1-434b-b567-59a9108c3970', 'b950f524-b718-4844-93ff-60112ae24028')
/
update KSEN_LUI set OBJ_ID='d402c555-0f6a-477c-9ed6-fcecac027d45', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:16:55.055', ATP_ID='kuali.atp.2014Spring', CLU_ID='CLUID-CHEM237-199508000000', EFF_DT=TIMESTAMP '2014-01-26 19:00:00.0', EXPIR_DT='', DESCR_FORMATTED='The chemistry of carbons: aliphatic compounds, aromatic compounds, stereochemistry, arenes, halides, alcohols, esters, and spectroscopy.', LUI_STATE='kuali.lui.course.offering.state.offered', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='CHEM237 CO', DESCR_PLAIN='The chemistry of carbons: aliphatic compounds, aromatic compounds, stereochemistry, arenes, halides, alcohols, esters, and spectroscopy.', REF_URL='' where ID='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec' and VER_NBR=2
/
update KSEN_LUI_ATTR set OBJ_ID='83c2b73c-1be5-46b0-a6cc-fc266d380284', ATTR_KEY='kuali.attribute.where.fees.attached.flag', OWNER_ID='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', ATTR_VALUE=0 where ID='137ba49a-bfdd-4c92-bc09-1f69d6d82617'
/
update KSEN_LUI_ATTR set OBJ_ID='e861a7c5-17af-45da-92d0-141b550bf9bd', ATTR_KEY='kuali.attribute.wait.list.type.key', OWNER_ID='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', ATTR_VALUE=null where ID='54f987b8-c5bc-47dd-a3fe-2c06aa2922dc'
/
update KSEN_LUI_ATTR set OBJ_ID='947cea3b-90db-43f9-8cf9-e29a77d91bd4', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', ATTR_VALUE=0 where ID='a8a84dc0-de75-4c37-8ad6-ff05b9369960'
/
update KSEN_LUI_ATTR set OBJ_ID='59f4bb62-5ebc-4e8e-ad6d-8ff4b9d4d781', ATTR_KEY='kuali.attribute.final.exam.use.matrix', OWNER_ID='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', ATTR_VALUE=0 where ID='b7076aa3-9a65-4f64-ad0c-39d64002bd84'
/
update KSEN_LUI_ATTR set OBJ_ID='87ad0d30-913d-4117-b465-0be4d13a91da', ATTR_KEY='kuali.attribute.wait.list.indicator', OWNER_ID='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', ATTR_VALUE=0 where ID='e8044c58-2f04-41cc-aaf1-f6531a2eeef8'
/
update KSEN_LUI_IDENT set OBJ_ID='a66653a8-f296-4439-9333-51444a40f39f', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:16:55.055', LUI_CD='CHEM237', DIVISION='CHEM', IDENT_LEVEL='', LNG_NAME='Principles of Organic Chemistry I', LUI_ID='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='83498921-d381-41cd-aee5-9c5f6361e0ed' and VER_NBR=2
/
update KSEN_LUI_LU_CD set OBJ_ID='c4e7a92c-13fe-402a-bdc7-05057b3ea0fc', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:16:55.055', DESCR_FORMATTED='', LUI_ID='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='5dde5ca4-a274-4a78-a04f-6cba22184466' and VER_NBR=2
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', 'ORGID-4284516367')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', 'kuali.creditType.credit.degree.4.0')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='ecbf361d-f840-4b45-b499-eb17b791dd2a'
/
delete from KSEN_SCHED_RQST_CMP where ID='ecbf361d-f840-4b45-b499-eb17b791dd2a'
/
delete from KSEN_SCHED_RQST where ID='35b0d0a0-23c6-41c4-b4a4-033a04fb1b23' and VER_NBR=0
/
delete from KSEN_SCHED_REF_OBJECT where SCHED_RQST_SET_ID='1b528cdf-3cd3-4e2b-8f51-b4fa036f9632'
/
delete from KSEN_SCHED_RQST_SET where ID='1b528cdf-3cd3-4e2b-8f51-b4fa036f9632' and VER_NBR=0
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('ac95823d-b05f-4b4a-9935-2697632d957d', 0, 'admin', TIMESTAMP '2014-07-01 10:17:36.858', 'admin', TIMESTAMP '2014-07-01 10:17:36.858', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '7bd32717-04f1-434b-b567-59a9108c3970', '1', '1', '2250c7b1-00c8-4b63-a3ab-e699ef5b878e')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('a559c5da-517d-4b2f-a3b0-1e7da3474d6d', 'kuali.lui.type.activity.offering.lecture', '2250c7b1-00c8-4b63-a3ab-e699ef5b878e', '9d5d4cfa-837a-4a60-9e1b-180bf4db319d')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='2250c7b1-00c8-4b63-a3ab-e699ef5b878e' where ID='9d5d4cfa-837a-4a60-9e1b-180bf4db319d'
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('33001cb9-7d57-401b-8b3b-fd3aed0c65d3', '7644dcfb-84ef-4105-98ef-5fe84f5718b4', 'activityOfferingCode', 'a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P', '4d91cdfb-5561-43a0-9f3e-1e0fded21fdb')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('bfb1cd2b-3886-4a6c-9d98-dc3094c10abe', 'F', 'activityOfferingCode', 'a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', '20476daf-b6fd-4398-9a1e-fc57ad26668d')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='7644dcfb-84ef-4105-98ef-5fe84f5718b4' and UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('be71860a-0f9c-451b-9a3e-0cac555ef6d8', 0, 'admin', TIMESTAMP '2014-07-01 10:18:12.944', 'admin', TIMESTAMP '2014-07-01 10:18:12.944', 'kuali.atp.2014Spring', '676591d3-8703-499c-8333-637a15e46134', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', '', '', 'CHEM237 AO', '', '', '7942cedd-e702-470d-86c9-eb82fdd919a3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2b7f4d04-6f69-42a0-8149-77b0009fa624', 'kuali.attribute.max.enrollment.is.estimate', '7942cedd-e702-470d-86c9-eb82fdd919a3', null, '20d8d9a1-d1f7-422d-a76e-ba0f6f356102')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4991d36a-e282-464d-8a86-337815294cb1', 'kuali.attribute.nonstd.ts.indicator', '7942cedd-e702-470d-86c9-eb82fdd919a3', 0, 'c6ef6f02-1010-4427-a0c9-2551378c0c97')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2792f763-4e28-4263-a467-f7a80b7a90f2', 'kuali.attribute.course.evaluation.indicator', '7942cedd-e702-470d-86c9-eb82fdd919a3', null, '7440142e-b5ed-4214-a82d-7707dbe290ab')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('e9197573-8eaf-4f4e-b4e9-b980db778a2d', 0, 'admin', TIMESTAMP '2014-07-01 10:18:12.944', 'admin', TIMESTAMP '2014-07-01 10:18:12.944', 'F', '', '', '', '7942cedd-e702-470d-86c9-eb82fdd919a3', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '1709ddc9-6249-4337-b661-9e6fc64e9f86')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('1fdab529-24b1-415a-8b42-7a142f2f7f97', 0, 'admin', TIMESTAMP '2014-07-01 10:18:12.944', 'admin', TIMESTAMP '2014-07-01 10:18:12.944', '', '7942cedd-e702-470d-86c9-eb82fdd919a3', '', 'kuali.lu.code.honorsOffering', null, 'd9b197b2-1414-4f1f-94c0-3602225b5004')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('90051e9a-dcc3-4c60-b73a-4fac0e77e86a', 0, 'admin', TIMESTAMP '2014-07-01 10:18:12.944', 'admin', TIMESTAMP '2014-07-01 10:18:12.944', TIMESTAMP '2014-07-01 10:18:13.329', '', 'CHEM237-FO-AO', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM237-FO-AO', '7942cedd-e702-470d-86c9-eb82fdd919a3', '10d9b3cd-643b-4525-8ded-946c40881353')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='ac95823d-b05f-4b4a-9935-2697632d957d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:18:12.944', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='7bd32717-04f1-434b-b567-59a9108c3970', NAME='1', PRIVATE_NAME='1' where ID='2250c7b1-00c8-4b63-a3ab-e699ef5b878e' and VER_NBR=0
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('9d5d4cfa-837a-4a60-9e1b-180bf4db319d', '7942cedd-e702-470d-86c9-eb82fdd919a3')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('b5fbf534-3064-4146-bafb-0a027b3587d5', 0, 'admin', TIMESTAMP '2014-07-01 10:18:12.944', 'admin', TIMESTAMP '2014-07-01 10:18:12.944', 'kuali.atp.2014Spring', '2d9a2baf-dba8-4414-92d8-1a57a6f3e1e0', '', '', '2001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2001', '2001', '', '4b0f0289-de68-4808-8bf0-a3a7421200e7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1647afe0-233f-4ddd-85a6-c86682175439', 'kuali.attribute.registration.group.is.generated', '4b0f0289-de68-4808-8bf0-a3a7421200e7', 1, '37340305-70cb-46fb-a7dd-b2fa3f59fd99')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b5b4ece9-38f0-481a-8a54-9f0410a36134', 'kuali.attribute.registration.group.aocluster.id', '4b0f0289-de68-4808-8bf0-a3a7421200e7', '2250c7b1-00c8-4b63-a3ab-e699ef5b878e', '53b43536-e844-4e56-ad27-7c99d3e67901')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('51af3112-6257-444c-a257-e2799c73fe76', 0, 'admin', TIMESTAMP '2014-07-01 10:18:12.944', 'admin', TIMESTAMP '2014-07-01 10:18:12.944', '', '', '', '', '4b0f0289-de68-4808-8bf0-a3a7421200e7', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '6fd06c76-5526-4cf0-b128-732eeb706699')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('44de0384-17ca-4827-9d50-1d9bafd39cc3', 0, 'admin', TIMESTAMP '2014-07-01 10:18:12.944', 'admin', TIMESTAMP '2014-07-01 10:18:12.944', TIMESTAMP '2014-07-01 10:18:13.694', '', 'CHEM237-FO-RG', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM237-FO-RG', '4b0f0289-de68-4808-8bf0-a3a7421200e7', '4a0b1d1d-2de5-48c5-b9e8-72bd8fe4f16f')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('bf030a9f-b2a8-4011-8640-d1db9265956c', 0, 'admin', TIMESTAMP '2014-07-01 10:18:12.944', 'admin', TIMESTAMP '2014-07-01 10:18:12.944', TIMESTAMP '2014-07-01 10:18:13.735', '', 'CHEM237-RG-AO', '4b0f0289-de68-4808-8bf0-a3a7421200e7', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM237-RG-AO', '7942cedd-e702-470d-86c9-eb82fdd919a3', 'ab34204b-1712-45b2-9e21-9fa71d0b78e4')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('e6849e4c-b905-42aa-b457-150b80e1a260', 0, 'admin', TIMESTAMP '2014-07-01 10:18:12.944', 'admin', TIMESTAMP '2014-07-01 10:18:12.944', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', 'da0a4d8e-0d8d-49ad-ab34-610280186f6a')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('da0a4d8e-0d8d-49ad-ab34-610280186f6a', '7942cedd-e702-470d-86c9-eb82fdd919a3')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('da0a4d8e-0d8d-49ad-ab34-610280186f6a', '7bd32717-04f1-434b-b567-59a9108c3970')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('648a9315-eacb-4fd8-bda5-16f863121db4', 0, 'admin', TIMESTAMP '2014-07-01 10:18:12.944', 'admin', TIMESTAMP '2014-07-01 10:18:12.944', 'kuali.atp.ExamPeriod.2014Spring', 'FCCF08F7BF135E91E040440A9B9A420D', '', '', '', 'kuali.lui.exam.offering.state.draft', 'kuali.lui.type.exam.offering.final', '', '', '', '', '', 'c689af4d-f9ee-4ad2-8799-7617d333a5db')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cd244d8b-a595-4910-84b6-3c7e53eeb960', 'kuali.attribute.final.exam.activity.driver', 'c689af4d-f9ee-4ad2-8799-7617d333a5db', 'kuali.lu.type.activity.Lecture', '47b64539-1fcc-403a-ad6e-7df18c6f4963')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('abb2af40-3da4-446a-8599-00f471c8c6c1', 'kuali.attribute.final.exam.driver', 'c689af4d-f9ee-4ad2-8799-7617d333a5db', 'PER_AO', 'be0f1f9a-ec10-4004-b2d0-48621df41a97')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('37c80e0c-c225-4c3d-8ba6-01ef0157b58c', 0, 'admin', TIMESTAMP '2014-07-01 10:18:12.944', 'admin', TIMESTAMP '2014-07-01 10:18:12.944', '', '', '', '', 'c689af4d-f9ee-4ad2-8799-7617d333a5db', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '76e93268-fcc4-488d-8528-ee247b35700b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('76e9e7fd-f211-4042-8d91-6160a04786a3', 0, 'admin', TIMESTAMP '2014-07-01 10:18:12.944', 'admin', TIMESTAMP '2014-07-01 10:18:12.944', TIMESTAMP '2014-07-01 10:18:14.843', '', 'c689af4d-f9ee-4ad2-8799-7617d333a5db-FO-EO)', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', 'c689af4d-f9ee-4ad2-8799-7617d333a5db-FO-EO', 'c689af4d-f9ee-4ad2-8799-7617d333a5db', '99641fdc-9282-4395-b669-e1cee12e1e23')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9691d561-4ae6-48a2-b74e-85884a12aef9', 'AO1', '99641fdc-9282-4395-b669-e1cee12e1e23', '7942cedd-e702-470d-86c9-eb82fdd919a3', '27ae3776-af66-489d-9d39-52981317f6aa')
/
update KSEN_LUI set OBJ_ID='be71860a-0f9c-451b-9a3e-0cac555ef6d8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:18:31.011', ATP_ID='kuali.atp.2014Spring', CLU_ID='676591d3-8703-499c-8333-637a15e46134', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS='', MIN_SEATS='', NAME='CHEM237 AO', DESCR_PLAIN='', REF_URL='' where ID='7942cedd-e702-470d-86c9-eb82fdd919a3' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='e9197573-8eaf-4f4e-b4e9-b980db778a2d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:18:31.011', LUI_CD='F', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='7942cedd-e702-470d-86c9-eb82fdd919a3', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1709ddc9-6249-4337-b661-9e6fc64e9f86' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='1fdab529-24b1-415a-8b42-7a142f2f7f97', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:18:31.011', DESCR_FORMATTED='', LUI_ID='7942cedd-e702-470d-86c9-eb82fdd919a3', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='d9b197b2-1414-4f1f-94c0-3602225b5004' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='54513f85-c7e4-4b66-8535-915ad6ae4fcb', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:18:31.011', ATP_ID='kuali.atp.2014Spring', CLU_ID='2d9a2baf-dba8-4414-92d8-1a57a6f3e1e0', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture Only', LUI_STATE='kuali.lui.format.offering.state.planned', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture Only', DESCR_PLAIN='Courses with Lecture Only', REF_URL='' where ID='7bd32717-04f1-434b-b567-59a9108c3970' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='072ed3f0-fe77-4a38-a149-58466a72dd28', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:18:31.011', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture Only', LUI_ID='7bd32717-04f1-434b-b567-59a9108c3970', ORGID='', SHRT_NAME='LEC', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='73c96249-1c11-49f0-9315-8e242159a8be' and VER_NBR=0
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='7bd32717-04f1-434b-b567-59a9108c3970'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI_ATTR set OBJ_ID='2b7f4d04-6f69-42a0-8149-77b0009fa624', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='7942cedd-e702-470d-86c9-eb82fdd919a3', ATTR_VALUE=0 where ID='20d8d9a1-d1f7-422d-a76e-ba0f6f356102'
/
update KSEN_LUI set OBJ_ID='be71860a-0f9c-451b-9a3e-0cac555ef6d8', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:19:15.06', ATP_ID='kuali.atp.2014Spring', CLU_ID='676591d3-8703-499c-8333-637a15e46134', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=18, MIN_SEATS='', NAME='CHEM237 AO', DESCR_PLAIN='', REF_URL='' where ID='7942cedd-e702-470d-86c9-eb82fdd919a3' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='2792f763-4e28-4263-a467-f7a80b7a90f2', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='7942cedd-e702-470d-86c9-eb82fdd919a3', ATTR_VALUE=0 where ID='7440142e-b5ed-4214-a82d-7707dbe290ab'
/
update KSEN_LUI_IDENT set OBJ_ID='e9197573-8eaf-4f4e-b4e9-b980db778a2d', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:19:15.06', LUI_CD='F', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='7942cedd-e702-470d-86c9-eb82fdd919a3', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1709ddc9-6249-4337-b661-9e6fc64e9f86' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='1fdab529-24b1-415a-8b42-7a142f2f7f97', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:19:15.06', DESCR_FORMATTED='', LUI_ID='7942cedd-e702-470d-86c9-eb82fdd919a3', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=0 where ID='d9b197b2-1414-4f1f-94c0-3602225b5004' and VER_NBR=1
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('37b8e9cd-8d60-4677-9e61-84eb3737c699', 0, 'admin', TIMESTAMP '2014-07-01 10:19:15.06', 'admin', TIMESTAMP '2014-07-01 10:19:15.06', '', '', '', 'Schedule request set for CHEM237 - F', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '3b69f489-9763-4f40-a4c3-71e152adb933')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('3b69f489-9763-4f40-a4c3-71e152adb933', '7942cedd-e702-470d-86c9-eb82fdd919a3')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('8a29ae65-1bba-40f6-9acc-4f3ff9135d9c', 0, 'admin', TIMESTAMP '2014-07-01 10:19:15.06', 'admin', TIMESTAMP '2014-07-01 10:19:15.06', '', '', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '3b69f489-9763-4f40-a4c3-71e152adb933', '3b04a59f-6366-432e-9d8d-866198ccbb49')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('bc4bf734-7678-4afd-929d-1c01da63497c', 1, '3b04a59f-6366-432e-9d8d-866198ccbb49', '532faa63-1069-4e7a-b2a2-6263dfad4394')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('532faa63-1069-4e7a-b2a2-6263dfad4394', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('f2e86eec-b8cf-4e00-a214-e765d8dbacc1', 0, 'admin', TIMESTAMP '2014-07-01 10:19:15.06', 'admin', TIMESTAMP '2014-07-01 10:19:15.06', 'kuali.atp.2014Spring', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '4fccbcc3-6d85-4805-9f78-aa24d2e2b440')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('b71ccee9-a99b-4392-8413-72a50e173104', 1, '', '4fccbcc3-6d85-4805-9f78-aa24d2e2b440', 'dcda5dff-6da5-421e-909d-1ae57cbb208b')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('dcda5dff-6da5-421e-909d-1ae57cbb208b', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND=1, SCHED_RQST_ID='3b04a59f-6366-432e-9d8d-866198ccbb49' where ID='532faa63-1069-4e7a-b2a2-6263dfad4394'
/
update KSEN_SCHED_RQST set OBJ_ID='8a29ae65-1bba-40f6-9acc-4f3ff9135d9c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:19:15.06', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='4fccbcc3-6d85-4805-9f78-aa24d2e2b440', SCHED_RQST_SET_ID='3b69f489-9763-4f40-a4c3-71e152adb933' where ID='3b04a59f-6366-432e-9d8d-866198ccbb49' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='532faa63-1069-4e7a-b2a2-6263dfad4394'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('532faa63-1069-4e7a-b2a2-6263dfad4394', '329dae32-419f-4777-be9c-fdb6ad97ffb0')
/
update KSEN_LUI set OBJ_ID='be71860a-0f9c-451b-9a3e-0cac555ef6d8', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:19:15.06', ATP_ID='kuali.atp.2014Spring', CLU_ID='676591d3-8703-499c-8333-637a15e46134', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=18, MIN_SEATS='', NAME='CHEM237 AO', DESCR_PLAIN='', REF_URL='' where ID='7942cedd-e702-470d-86c9-eb82fdd919a3' and VER_NBR=2
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('7942cedd-e702-470d-86c9-eb82fdd919a3', '4fccbcc3-6d85-4805-9f78-aa24d2e2b440')
/
update KSEN_LUI set OBJ_ID='be71860a-0f9c-451b-9a3e-0cac555ef6d8', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:19:15.06', ATP_ID='kuali.atp.2014Spring', CLU_ID='676591d3-8703-499c-8333-637a15e46134', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=18, MIN_SEATS='', NAME='CHEM237 AO', DESCR_PLAIN='', REF_URL='' where ID='7942cedd-e702-470d-86c9-eb82fdd919a3' and VER_NBR=3
/
update KSEN_LUI set OBJ_ID='54513f85-c7e4-4b66-8535-915ad6ae4fcb', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:19:15.06', ATP_ID='kuali.atp.2014Spring', CLU_ID='2d9a2baf-dba8-4414-92d8-1a57a6f3e1e0', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture Only', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture Only', DESCR_PLAIN='Courses with Lecture Only', REF_URL='' where ID='7bd32717-04f1-434b-b567-59a9108c3970' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='072ed3f0-fe77-4a38-a149-58466a72dd28', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:19:15.06', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture Only', LUI_ID='7bd32717-04f1-434b-b567-59a9108c3970', ORGID='', SHRT_NAME='LEC', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='73c96249-1c11-49f0-9315-8e242159a8be' and VER_NBR=1
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='7bd32717-04f1-434b-b567-59a9108c3970'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='b5fbf534-3064-4146-bafb-0a027b3587d5', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:19:15.06', ATP_ID='kuali.atp.2014Spring', CLU_ID='2d9a2baf-dba8-4414-92d8-1a57a6f3e1e0', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2001', DESCR_PLAIN='2001', REF_URL='' where ID='4b0f0289-de68-4808-8bf0-a3a7421200e7' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='51af3112-6257-444c-a257-e2799c73fe76', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:19:15.06', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='4b0f0289-de68-4808-8bf0-a3a7421200e7', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='6fd06c76-5526-4cf0-b128-732eeb706699' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='b5fbf534-3064-4146-bafb-0a027b3587d5', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:19:15.06', ATP_ID='kuali.atp.2014Spring', CLU_ID='2d9a2baf-dba8-4414-92d8-1a57a6f3e1e0', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='2001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='2001', DESCR_PLAIN='2001', REF_URL='' where ID='4b0f0289-de68-4808-8bf0-a3a7421200e7' and VER_NBR=1
/
update KSEN_CWL set OBJ_ID='e6849e4c-b905-42aa-b457-150b80e1a260', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:19:15.06', ALLOW_HOLD_UTIL_ENTRIES_IND=0, AUTO_PROCESSED_IND=1, CHECK_IN_REQ_IND=0, CONF_REQ_IND=1, EFF_DT='', EXPIR_DT='', MAX_SIZE='', REG_IN_FIRST_AAO_IND='', STD_DUR_TIME_QTY='', STD_DUR_TYPE='', CWL_STATE='kuali.course.waitlist.state.inactive', CWL_TYPE='kuali.course.waitlist.type.course.waitlist' where ID='da0a4d8e-0d8d-49ad-ab34-610280186f6a' and VER_NBR=0
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('8e1cb369-18fd-413b-8cfc-2799b00a59f3', '4f2dc459-495a-424e-8522-3cb05bd52fb1', 'activityOfferingCode', 'a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P', '68bfbe4c-e3c5-41be-8168-ada92835c817')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('139cec05-36aa-4b0f-917c-e1a341a6803a', 'G', 'activityOfferingCode', 'a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', 'dbbf9b39-d947-4249-a248-76a9ec238784')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='4f2dc459-495a-424e-8522-3cb05bd52fb1' and UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('83f24127-d704-4760-b9d3-63972b5f256b', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'kuali.atp.2014Spring', '676591d3-8703-499c-8333-637a15e46134', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', '', '', 'CHEM237 AO', '', '', 'd89bca70-3b2b-4db3-b6d2-df7ce08b8ecf')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b7d998eb-70fc-499b-bced-ebdb4b89c4fa', 'kuali.attribute.nonstd.ts.indicator', 'd89bca70-3b2b-4db3-b6d2-df7ce08b8ecf', 0, '2ee5f164-6041-49bd-8fd4-602c46730244')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('19130c1d-bade-412a-869a-05304413c6de', 'kuali.attribute.course.evaluation.indicator', 'd89bca70-3b2b-4db3-b6d2-df7ce08b8ecf', null, '42d09fb1-1dc7-4ae0-bb04-d2ba85f85ae5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0371fa53-5b3a-44bf-bf98-8554b79ef3ca', 'kuali.attribute.max.enrollment.is.estimate', 'd89bca70-3b2b-4db3-b6d2-df7ce08b8ecf', null, '6ab2e0ae-b6db-461b-942b-f1bf87c923e5')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('79951f21-1b37-4b2b-946a-cbae24fc5fa2', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'G', '', '', '', 'd89bca70-3b2b-4db3-b6d2-df7ce08b8ecf', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ab267af1-7907-4410-a882-743fc2734f95')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('a96ebb22-af68-4714-8012-ba9fb4d63da6', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', '', 'd89bca70-3b2b-4db3-b6d2-df7ce08b8ecf', '', 'kuali.lu.code.honorsOffering', null, '0bfc1ab0-2936-4f57-b35a-9b612ec3286f')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('c6e3a6e2-1d43-4b16-841d-4492ec8f3504', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:29.79', '', 'CHEM237-FO-AO', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM237-FO-AO', 'd89bca70-3b2b-4db3-b6d2-df7ce08b8ecf', '8266f3db-eede-449f-8a99-adb9169d44a7')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='ac95823d-b05f-4b4a-9935-2697632d957d', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:20:29.433', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='7bd32717-04f1-434b-b567-59a9108c3970', NAME='1', PRIVATE_NAME='1' where ID='2250c7b1-00c8-4b63-a3ab-e699ef5b878e' and VER_NBR=1
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('9d5d4cfa-837a-4a60-9e1b-180bf4db319d', 'd89bca70-3b2b-4db3-b6d2-df7ce08b8ecf')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('9f92ab8b-f634-4468-96e1-06af773ba87b', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'kuali.atp.2014Spring', '2d9a2baf-dba8-4414-92d8-1a57a6f3e1e0', '', '', '2002', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2002', '2002', '', '757812f6-0b6f-4900-8faa-a376bb5edc51')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ae5d298d-f288-4f93-9369-ad8869f7451f', 'kuali.attribute.registration.group.is.generated', '757812f6-0b6f-4900-8faa-a376bb5edc51', 1, '1d323b38-d882-43f9-9b9f-7fa9142d29e0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8d3bd7fc-4220-4c03-bebf-0a41e6f0c886', 'kuali.attribute.registration.group.aocluster.id', '757812f6-0b6f-4900-8faa-a376bb5edc51', '2250c7b1-00c8-4b63-a3ab-e699ef5b878e', 'd0bec22f-afc3-4b36-85a6-8a28f62dda8e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('1e133bba-2558-4404-89c9-affb425b9567', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', '', '', '', '', '757812f6-0b6f-4900-8faa-a376bb5edc51', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '56ea77f5-9ee6-4884-974f-77615d211310')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e709c3a6-0f27-4920-8550-349f132b65f5', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:30.477', '', 'CHEM237-FO-RG', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM237-FO-RG', '757812f6-0b6f-4900-8faa-a376bb5edc51', '78a70830-25d8-4003-8e4c-ae2f5bf4ca47')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('93eaa3d5-b8a0-4255-afdb-471e32c8246d', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:30.515', '', 'CHEM237-RG-AO', '757812f6-0b6f-4900-8faa-a376bb5edc51', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM237-RG-AO', 'd89bca70-3b2b-4db3-b6d2-df7ce08b8ecf', '8dcbeb61-4a8b-4655-81fb-a184cdb2d0d5')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('3037fc26-1804-43a2-97f5-e494cd4c21d4', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '78a4e9bd-b4a8-4ee8-ab8d-11e75fd488da')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('78a4e9bd-b4a8-4ee8-ab8d-11e75fd488da', 'd89bca70-3b2b-4db3-b6d2-df7ce08b8ecf')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('78a4e9bd-b4a8-4ee8-ab8d-11e75fd488da', '7bd32717-04f1-434b-b567-59a9108c3970')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('a6b77b61-45ab-4526-940d-d7387170ae4f', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'kuali.atp.ExamPeriod.2014Spring', 'FCCF08F7BF135E91E040440A9B9A420D', '', '', '', 'kuali.lui.exam.offering.state.draft', 'kuali.lui.type.exam.offering.final', '', '', '', '', '', '1ea7d99a-99d2-47fd-88b2-dbe4c94ef2ce')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('70dc416e-3985-4154-9365-11b3a9918aef', 'kuali.attribute.final.exam.activity.driver', '1ea7d99a-99d2-47fd-88b2-dbe4c94ef2ce', 'kuali.lu.type.activity.Lecture', '57f63c32-f1bc-4b6e-b2d2-b61ca2cdda5f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('27dce857-ef9c-4e60-9810-fd95ffcba382', 'kuali.attribute.final.exam.driver', '1ea7d99a-99d2-47fd-88b2-dbe4c94ef2ce', 'PER_AO', '8882e155-cbd3-4ac8-8eef-95ef65c95ed7')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c6b45f97-8ab4-4a7d-87a6-3636673075c3', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', '', '', '', '', '1ea7d99a-99d2-47fd-88b2-dbe4c94ef2ce', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ebe58197-be56-4d8c-a122-323aa4e112ed')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('2e68d940-2f86-4c40-8a6c-81e70cdccdbf', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:31.007', '', '1ea7d99a-99d2-47fd-88b2-dbe4c94ef2ce-FO-EO)', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', '1ea7d99a-99d2-47fd-88b2-dbe4c94ef2ce-FO-EO', '1ea7d99a-99d2-47fd-88b2-dbe4c94ef2ce', 'fae9a651-1faa-47b5-90ff-494fdabe9610')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('49a281d3-ed55-40e6-b0a8-efe43972cb25', 'AO1', 'fae9a651-1faa-47b5-90ff-494fdabe9610', 'd89bca70-3b2b-4db3-b6d2-df7ce08b8ecf', '310a8e3c-d791-4fc0-b882-6283a7a3b7cb')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('38bc16a6-94c9-425b-8f1c-b162cf75b0e0', 'c9edae71-b582-4c5b-8473-4d83e65f1112', 'activityOfferingCode', 'a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P', '04f29cf3-a81f-4f39-9f78-17c982e52c57')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('9def50a4-7c78-403e-a72d-6e08c633c6e8', 'H', 'activityOfferingCode', 'a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', '673812a0-ec25-4a1c-afda-08260de89995')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='c9edae71-b582-4c5b-8473-4d83e65f1112' and UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('586f62fa-6364-4015-8484-8dbfb7846c48', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'kuali.atp.2014Spring', '676591d3-8703-499c-8333-637a15e46134', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', '', '', 'CHEM237 AO', '', '', 'f0249a73-cf15-4210-a3d8-ec55464bc1b4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a2c87535-c418-4cf5-ba64-f5180460130b', 'kuali.attribute.max.enrollment.is.estimate', 'f0249a73-cf15-4210-a3d8-ec55464bc1b4', null, 'e88e297e-09fc-48ed-a46e-1006b73c2953')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d5fa5f12-fd28-4787-9a15-2d4cbcdf674a', 'kuali.attribute.nonstd.ts.indicator', 'f0249a73-cf15-4210-a3d8-ec55464bc1b4', 0, 'b3a6cba1-53fe-469d-9b0a-23adadd1fcb5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f9af5b96-44c3-4735-8c1c-5c387f46cad2', 'kuali.attribute.course.evaluation.indicator', 'f0249a73-cf15-4210-a3d8-ec55464bc1b4', null, '6a7cd590-60c1-48e4-b491-851393420aaf')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('dba43ef3-cf04-42b7-84d8-3e3ed5c4e8fa', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'H', '', '', '', 'f0249a73-cf15-4210-a3d8-ec55464bc1b4', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'e7369fe8-cef3-4c55-8837-327d9e2ea985')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('24ff43fe-54b4-4241-b78c-c923b66c8f73', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', '', 'f0249a73-cf15-4210-a3d8-ec55464bc1b4', '', 'kuali.lu.code.honorsOffering', null, '4cec10d1-940c-436d-bcdc-7794806bc379')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('b8f314d3-503c-4123-a376-1f6c04077ea3', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:31.265', '', 'CHEM237-FO-AO', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM237-FO-AO', 'f0249a73-cf15-4210-a3d8-ec55464bc1b4', '877399e1-4a59-433e-bf6d-2b32da92c72f')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='ac95823d-b05f-4b4a-9935-2697632d957d', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:20:29.433', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='7bd32717-04f1-434b-b567-59a9108c3970', NAME='1', PRIVATE_NAME='1' where ID='2250c7b1-00c8-4b63-a3ab-e699ef5b878e' and VER_NBR=2
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('9d5d4cfa-837a-4a60-9e1b-180bf4db319d', 'f0249a73-cf15-4210-a3d8-ec55464bc1b4')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('243ba333-bb1a-4d37-892a-8667b120b807', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'kuali.atp.2014Spring', '2d9a2baf-dba8-4414-92d8-1a57a6f3e1e0', '', '', '2003', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2003', '2003', '', '26577d7c-d726-4e9c-b657-edd7d36c7eeb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fc898d87-0ff5-470b-b875-1daba3bfa640', 'kuali.attribute.registration.group.aocluster.id', '26577d7c-d726-4e9c-b657-edd7d36c7eeb', '2250c7b1-00c8-4b63-a3ab-e699ef5b878e', 'dd82eb29-7203-4232-8835-d794133019fd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('22a48097-61a4-4106-890b-e8de1aa5cd88', 'kuali.attribute.registration.group.is.generated', '26577d7c-d726-4e9c-b657-edd7d36c7eeb', 1, 'b7435cef-5b32-4c94-8cd3-e34acfd47492')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a5a4690a-3bf8-4005-a776-39eb96cf285a', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', '', '', '', '', '26577d7c-d726-4e9c-b657-edd7d36c7eeb', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '0dcf139d-8531-4b51-a8a3-a8e5700bb156')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('37416959-7f6a-47a8-be98-b7f93aa461fa', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:31.497', '', 'CHEM237-FO-RG', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM237-FO-RG', '26577d7c-d726-4e9c-b657-edd7d36c7eeb', 'c28ee7a2-4a98-4298-91a1-c141a0dde930')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('02e33fb6-ab11-4a3f-ad46-8cef868fc465', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:31.517', '', 'CHEM237-RG-AO', '26577d7c-d726-4e9c-b657-edd7d36c7eeb', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM237-RG-AO', 'f0249a73-cf15-4210-a3d8-ec55464bc1b4', '59b23523-b345-4cd6-bcfd-c137878bfb14')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('f1fa9634-25cc-49e2-aacf-bcf842d090e0', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', 'd45de824-25c3-4b39-920a-120bb903f03d')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('d45de824-25c3-4b39-920a-120bb903f03d', 'f0249a73-cf15-4210-a3d8-ec55464bc1b4')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('d45de824-25c3-4b39-920a-120bb903f03d', '7bd32717-04f1-434b-b567-59a9108c3970')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ed729a75-68bb-47e2-84ee-7009c606c0d7', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'kuali.atp.ExamPeriod.2014Spring', 'FCCF08F7BF135E91E040440A9B9A420D', '', '', '', 'kuali.lui.exam.offering.state.draft', 'kuali.lui.type.exam.offering.final', '', '', '', '', '', '49b0f96e-7735-40c5-b07e-e3f38a662dd2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1b556695-9f2d-4ba4-866e-f23681802df4', 'kuali.attribute.final.exam.driver', '49b0f96e-7735-40c5-b07e-e3f38a662dd2', 'PER_AO', '967e0299-22f1-4755-9a75-72b0e8868cfc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('59eddb57-ef3d-49c5-85be-fbe693f23e0d', 'kuali.attribute.final.exam.activity.driver', '49b0f96e-7735-40c5-b07e-e3f38a662dd2', 'kuali.lu.type.activity.Lecture', '41e381b7-c327-4884-bc20-51e70e1b6a79')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('21a77fd6-5828-4ff4-b5dc-6fb57515bc8d', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', '', '', '', '', '49b0f96e-7735-40c5-b07e-e3f38a662dd2', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'db42cdcf-c5f7-4f9e-9a12-edb12b4183a4')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('c4d5fb01-c11f-49fb-990d-d28de405a0de', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:31.873', '', '49b0f96e-7735-40c5-b07e-e3f38a662dd2-FO-EO)', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', '49b0f96e-7735-40c5-b07e-e3f38a662dd2-FO-EO', '49b0f96e-7735-40c5-b07e-e3f38a662dd2', '54d7986d-dcb0-4fcb-a6ee-05da47b077b0')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e25a8211-43fc-4092-b0f8-caba1678e92f', 'AO1', '54d7986d-dcb0-4fcb-a6ee-05da47b077b0', 'f0249a73-cf15-4210-a3d8-ec55464bc1b4', 'e2cf87e8-8dd5-40d9-ac52-8806b40ed283')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('fef3a500-5980-42ae-9eb2-b3580b1c27e1', '84b3ceb0-9925-462b-9f40-6c875e6413e9', 'activityOfferingCode', 'a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P', 'c05dd2e5-d33e-41bc-9a9a-9d320fb6bf93')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('81553b1c-5db7-4065-804c-e296946c6b2b', 'I', 'activityOfferingCode', 'a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', '5399a870-7525-4aeb-840d-d92c2f5a4c0a')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='84b3ceb0-9925-462b-9f40-6c875e6413e9' and UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('24b0b262-95df-4e7b-932c-17d94e51047c', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'kuali.atp.2014Spring', '676591d3-8703-499c-8333-637a15e46134', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', '', '', 'CHEM237 AO', '', '', 'c0429a1c-60c9-4a2b-b1cd-31ecd61bf647')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('af29c142-67f1-4a51-afcb-4e013910d174', 'kuali.attribute.course.evaluation.indicator', 'c0429a1c-60c9-4a2b-b1cd-31ecd61bf647', null, '06c22d6b-5b8c-4669-b3b9-c4477b0963bb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6a86e5ff-a1d5-4f5a-bd0a-6b48d3a4e236', 'kuali.attribute.nonstd.ts.indicator', 'c0429a1c-60c9-4a2b-b1cd-31ecd61bf647', 0, '44d6be39-0d58-4a18-992f-89fe3c73e3b1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('95172a9d-83f7-4266-86d4-15108316c867', 'kuali.attribute.max.enrollment.is.estimate', 'c0429a1c-60c9-4a2b-b1cd-31ecd61bf647', null, 'cade98d6-3588-4754-ac78-ce6e6a6db05f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('9e21f3b7-a828-41af-9c4c-53c4b9a3c4cf', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'I', '', '', '', 'c0429a1c-60c9-4a2b-b1cd-31ecd61bf647', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ac7de7c8-cc4c-46b5-a783-bef90fc8a46a')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('42860c7b-8879-432f-be47-9c0af7d303de', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', '', 'c0429a1c-60c9-4a2b-b1cd-31ecd61bf647', '', 'kuali.lu.code.honorsOffering', null, '77e77925-f740-45ef-8b59-ce5ff4beb169')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('68a626ab-c78f-4362-9dc4-a4eacca801fd', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:32.086', '', 'CHEM237-FO-AO', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM237-FO-AO', 'c0429a1c-60c9-4a2b-b1cd-31ecd61bf647', '7fe7c5c4-89b1-4398-a3ca-430e8388e37c')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='ac95823d-b05f-4b4a-9935-2697632d957d', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:20:29.433', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='7bd32717-04f1-434b-b567-59a9108c3970', NAME='1', PRIVATE_NAME='1' where ID='2250c7b1-00c8-4b63-a3ab-e699ef5b878e' and VER_NBR=3
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('9d5d4cfa-837a-4a60-9e1b-180bf4db319d', 'c0429a1c-60c9-4a2b-b1cd-31ecd61bf647')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('112a502e-0c90-44f4-bded-c19090952e9b', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'kuali.atp.2014Spring', '2d9a2baf-dba8-4414-92d8-1a57a6f3e1e0', '', '', '2004', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2004', '2004', '', '032d749e-096d-4059-b347-3208e1af8203')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8839e82d-c6c4-434f-a024-017f3c6eb0b3', 'kuali.attribute.registration.group.is.generated', '032d749e-096d-4059-b347-3208e1af8203', 1, '0954aeeb-7695-4ad2-b014-7fb23fe544ec')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8c8bb356-8f26-4b6e-a7be-fe1b6b1688d0', 'kuali.attribute.registration.group.aocluster.id', '032d749e-096d-4059-b347-3208e1af8203', '2250c7b1-00c8-4b63-a3ab-e699ef5b878e', '73f41eaf-cf4f-446f-bcb0-c52d7b2dfe00')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('08c4d2a6-d456-42ee-9136-32ab5f706f1b', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', '', '', '', '', '032d749e-096d-4059-b347-3208e1af8203', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '0cb88995-8562-4c53-91a0-2f102f72b18a')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('2d0b4073-0d77-4a88-957a-e33ecf5c7290', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:32.38', '', 'CHEM237-FO-RG', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM237-FO-RG', '032d749e-096d-4059-b347-3208e1af8203', '86d22ebf-e7ac-4eae-8f95-1a8c54bd1788')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('d4fe911f-1dbe-402f-aab8-a85b619f4fd2', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:32.396', '', 'CHEM237-RG-AO', '032d749e-096d-4059-b347-3208e1af8203', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM237-RG-AO', 'c0429a1c-60c9-4a2b-b1cd-31ecd61bf647', '39639539-8ead-4c72-990f-d5a9465f0767')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('979d0421-ff97-4441-8e1f-125961466a47', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '5f76e24c-0383-46c4-b5c6-33efe77eceea')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('5f76e24c-0383-46c4-b5c6-33efe77eceea', 'c0429a1c-60c9-4a2b-b1cd-31ecd61bf647')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('5f76e24c-0383-46c4-b5c6-33efe77eceea', '7bd32717-04f1-434b-b567-59a9108c3970')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('311d6d67-bad7-474d-b020-acaa55970446', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'kuali.atp.ExamPeriod.2014Spring', 'FCCF08F7BF135E91E040440A9B9A420D', '', '', '', 'kuali.lui.exam.offering.state.draft', 'kuali.lui.type.exam.offering.final', '', '', '', '', '', 'f1384752-f342-4b87-be00-d6619b02fd52')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b7dbea78-ee6b-42f3-b4ef-96edec706c18', 'kuali.attribute.final.exam.activity.driver', 'f1384752-f342-4b87-be00-d6619b02fd52', 'kuali.lu.type.activity.Lecture', '715264c3-952f-45dc-89fd-9aac752ec79a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1fc41dbf-fa13-4230-a8ee-2c982e2f8f87', 'kuali.attribute.final.exam.driver', 'f1384752-f342-4b87-be00-d6619b02fd52', 'PER_AO', 'c52b8ef7-9ace-4f06-9015-6e3bb86599d9')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('3c8ff6fc-5944-4a2d-93a6-006077b086f7', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', '', '', '', '', 'f1384752-f342-4b87-be00-d6619b02fd52', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7b2b3c31-771e-4382-882d-047dc415d771')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('15ec74dd-91bc-4a95-8d13-6a301374c2b5', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:32.886', '', 'f1384752-f342-4b87-be00-d6619b02fd52-FO-EO)', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', 'f1384752-f342-4b87-be00-d6619b02fd52-FO-EO', 'f1384752-f342-4b87-be00-d6619b02fd52', '76c3495f-a69c-4fa0-9201-f2d48ba6305d')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d7f92aa8-a89d-47c1-8ba1-6bc61c9505c2', 'AO1', '76c3495f-a69c-4fa0-9201-f2d48ba6305d', 'c0429a1c-60c9-4a2b-b1cd-31ecd61bf647', 'c6f39272-c878-48e8-bac6-536d142b4783')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('3992fd00-0856-411a-a0cf-991409b2beb1', '618b0dc7-994f-460d-9895-db27137564e2', 'activityOfferingCode', 'a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P', 'd9e6b38f-657c-4314-a4a9-d9fb414df62c')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('ccbee8cb-fb3c-42e4-89ab-dfd7a41425d2', 'J', 'activityOfferingCode', 'a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', '4ece5b3b-080d-446e-87a7-31e4b35167e5')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='618b0dc7-994f-460d-9895-db27137564e2' and UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('bfde0562-267a-4fde-80db-63b271bd4418', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'kuali.atp.2014Spring', '676591d3-8703-499c-8333-637a15e46134', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', '', '', 'CHEM237 AO', '', '', '29f51280-f9ef-47e5-a746-3985d1bb2f37')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('70de303e-19b9-42c1-812a-17e47864d827', 'kuali.attribute.max.enrollment.is.estimate', '29f51280-f9ef-47e5-a746-3985d1bb2f37', null, 'ae5ba8a5-725a-4e1b-9549-35594c69b399')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('45c40549-b9a9-4eda-9558-77a99aeb04a2', 'kuali.attribute.nonstd.ts.indicator', '29f51280-f9ef-47e5-a746-3985d1bb2f37', 0, 'b8428146-2c16-4ec9-83ca-be439f0b848c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('af6a7565-36ff-408d-b6d5-ec80c0737069', 'kuali.attribute.course.evaluation.indicator', '29f51280-f9ef-47e5-a746-3985d1bb2f37', null, 'e2c43f85-35a7-4c89-9bdf-8de8b1249f3c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('f7c814f7-9de1-4953-8484-f90f8ce1e582', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'J', '', '', '', '29f51280-f9ef-47e5-a746-3985d1bb2f37', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '3d0e24ec-45bb-45b7-9cc7-78da9a201463')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('5e514e1c-71f3-4ca4-b789-40ee513f70f9', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', '', '29f51280-f9ef-47e5-a746-3985d1bb2f37', '', 'kuali.lu.code.honorsOffering', null, 'dc7e5710-db88-4dc4-965d-020a78e3541b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a86487be-d8dc-4377-b3c7-4713fd3eb13a', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:33.095', '', 'CHEM237-FO-AO', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM237-FO-AO', '29f51280-f9ef-47e5-a746-3985d1bb2f37', '25e742fa-4a27-4a93-846f-33f4fb4d2a62')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='ac95823d-b05f-4b4a-9935-2697632d957d', VER_NBR=5, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:20:29.433', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='7bd32717-04f1-434b-b567-59a9108c3970', NAME='1', PRIVATE_NAME='1' where ID='2250c7b1-00c8-4b63-a3ab-e699ef5b878e' and VER_NBR=4
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('9d5d4cfa-837a-4a60-9e1b-180bf4db319d', '29f51280-f9ef-47e5-a746-3985d1bb2f37')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('20b76a1e-1ab6-49e0-adf5-2c4e45b210d3', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'kuali.atp.2014Spring', '2d9a2baf-dba8-4414-92d8-1a57a6f3e1e0', '', '', '2005', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2005', '2005', '', '0d01408d-61a6-4085-abe8-28c9192209c0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('128a1df2-17f5-4436-88ef-266a428095ab', 'kuali.attribute.registration.group.aocluster.id', '0d01408d-61a6-4085-abe8-28c9192209c0', '2250c7b1-00c8-4b63-a3ab-e699ef5b878e', '78f3699f-052c-45ac-b0fc-cedbbf2a5be2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a8b3aac7-38b7-47ee-8079-7f6794e17783', 'kuali.attribute.registration.group.is.generated', '0d01408d-61a6-4085-abe8-28c9192209c0', 1, '3bdf4450-9758-4949-bf27-f49bfa621d10')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a2dd4350-4a1b-42e7-be40-cf5abb6196e8', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', '', '', '', '', '0d01408d-61a6-4085-abe8-28c9192209c0', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '9bd6be17-0a09-47ef-a937-d9032900f7cd')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('740e2435-c193-4636-9d94-694ed4e7a31a', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:33.304', '', 'CHEM237-FO-RG', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM237-FO-RG', '0d01408d-61a6-4085-abe8-28c9192209c0', 'df79de21-1848-44be-b5f1-fba4678fbf0f')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('585b8a35-a2dc-45c7-97f5-ce9062a09084', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:33.342', '', 'CHEM237-RG-AO', '0d01408d-61a6-4085-abe8-28c9192209c0', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM237-RG-AO', '29f51280-f9ef-47e5-a746-3985d1bb2f37', 'd29267df-7937-4b32-aa47-8860346ead9a')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('d535f3b0-4594-4258-b306-15c7c0eb0b25', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', 'bf4cd064-a206-462d-aea0-b82a3e8c72d0')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('bf4cd064-a206-462d-aea0-b82a3e8c72d0', '29f51280-f9ef-47e5-a746-3985d1bb2f37')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('bf4cd064-a206-462d-aea0-b82a3e8c72d0', '7bd32717-04f1-434b-b567-59a9108c3970')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('e39bb56b-457e-4807-b4fe-1d221f6d613d', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'kuali.atp.ExamPeriod.2014Spring', 'FCCF08F7BF135E91E040440A9B9A420D', '', '', '', 'kuali.lui.exam.offering.state.draft', 'kuali.lui.type.exam.offering.final', '', '', '', '', '', '199c0237-4c4f-4356-a75f-49c5af1039b4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cc037e9d-6e30-48ce-b1b3-31096ad539a9', 'kuali.attribute.final.exam.driver', '199c0237-4c4f-4356-a75f-49c5af1039b4', 'PER_AO', '7b0ae23c-19eb-4a22-b3f3-4c3b29771163')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0dc1da0e-6a1f-4a1e-a77d-4f67a48fb0bb', 'kuali.attribute.final.exam.activity.driver', '199c0237-4c4f-4356-a75f-49c5af1039b4', 'kuali.lu.type.activity.Lecture', 'b388b6e5-f0bb-467e-be78-6b1c630efb23')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c3e5debd-924f-4586-8c74-d69d200cac32', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', '', '', '', '', '199c0237-4c4f-4356-a75f-49c5af1039b4', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '56d6abf2-b48b-442d-96c2-be9f8b92dffa')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('d183f7c3-231a-49ec-bf47-2bc64a4c1c50', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:33.612', '', '199c0237-4c4f-4356-a75f-49c5af1039b4-FO-EO)', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', '199c0237-4c4f-4356-a75f-49c5af1039b4-FO-EO', '199c0237-4c4f-4356-a75f-49c5af1039b4', 'f49504a8-e62e-433a-b2e3-08378ce9bae0')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3a19552a-1686-4c17-8e55-640ae8b25fd8', 'AO1', 'f49504a8-e62e-433a-b2e3-08378ce9bae0', '29f51280-f9ef-47e5-a746-3985d1bb2f37', 'fc7d914e-1a70-408a-b46e-f6dc3b164522')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('dd323cb1-5bbe-4593-a5b8-9a3595a50706', 'c7d82c56-15d2-475f-9e12-d8c42651b212', 'activityOfferingCode', 'a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P', 'b5d00a23-7b27-4a99-ae44-0a4218282eac')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('73a0d0e4-44a9-4c1e-a930-ca4f3d76879d', 'K', 'activityOfferingCode', 'a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec', '7425bb9e-0047-4c8d-9bd8-b14d8b6d2934')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='c7d82c56-15d2-475f-9e12-d8c42651b212' and UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P' and NAMESPACE='activityOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec' and NAMESPACE='activityOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='a46e9dae-9e0f-4cfd-bfaf-9f842bdc57ec-P' and codegenera1_.NAMESPACE='activityOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('2e40830c-b249-405a-bcbc-8a449d68d9da', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'kuali.atp.2014Spring', '676591d3-8703-499c-8333-637a15e46134', '', '', '', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', '', '', 'CHEM237 AO', '', '', '7f7fe758-82df-4894-aee9-7b1d67218980')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9ab6c9c4-835c-4b6d-bfd8-0c2d144fac98', 'kuali.attribute.course.evaluation.indicator', '7f7fe758-82df-4894-aee9-7b1d67218980', null, '641678e5-a256-4a2d-b8dd-9be1f2a8c146')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bc5c6285-7019-4ad5-a4fc-e3c9912ae781', 'kuali.attribute.nonstd.ts.indicator', '7f7fe758-82df-4894-aee9-7b1d67218980', 0, 'e881232a-37ab-43bf-a797-fec800c1a30b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('28e92a4c-7d87-44e5-88c9-20fb2b5133fb', 'kuali.attribute.max.enrollment.is.estimate', '7f7fe758-82df-4894-aee9-7b1d67218980', null, '2510c478-1bce-4bf7-af51-653dbd745a66')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('8a6b0507-9c4f-41b5-b07c-c082a7177150', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'K', '', '', '', '7f7fe758-82df-4894-aee9-7b1d67218980', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c4b1b35d-fb76-4372-a3a1-7a7d18a76dc7')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('951b6e78-d7e6-4eae-85e2-bd5618855810', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', '', '7f7fe758-82df-4894-aee9-7b1d67218980', '', 'kuali.lu.code.honorsOffering', null, 'c7d8b915-e4cc-4efc-98fe-b6a622c6132c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('5e119450-5b84-4ce5-8161-c973dad4d047', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:33.888', '', 'CHEM237-FO-AO', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM237-FO-AO', '7f7fe758-82df-4894-aee9-7b1d67218980', '81ad765c-5bdb-4c2b-8f00-9723dd3075bf')
/
update KSEN_CO_AO_CLUSTER set OBJ_ID='ac95823d-b05f-4b4a-9935-2697632d957d', VER_NBR=6, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:20:29.433', AO_CLUSTER_STATE='kuali.activity.offering.cluster.state.active', AO_CLUSTER_TYPE='kuali.activity.offering.cluster.type.activity.offering.cluster', DESCR_FORMATTED='', DESCR_PLAIN='', FORMAT_OFFERING_ID='7bd32717-04f1-434b-b567-59a9108c3970', NAME='1', PRIVATE_NAME='1' where ID='2250c7b1-00c8-4b63-a3ab-e699ef5b878e' and VER_NBR=5
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('9d5d4cfa-837a-4a60-9e1b-180bf4db319d', '7f7fe758-82df-4894-aee9-7b1d67218980')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ce694f9f-e4c0-43b2-aff6-3dc1640557b6', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'kuali.atp.2014Spring', '2d9a2baf-dba8-4414-92d8-1a57a6f3e1e0', '', '', '2006', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '2006', '2006', '', '15cf8c73-32fd-4413-acab-f2813e268102')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('eb932ae5-e12a-449e-ab6b-f2f9b2a9661e', 'kuali.attribute.registration.group.aocluster.id', '15cf8c73-32fd-4413-acab-f2813e268102', '2250c7b1-00c8-4b63-a3ab-e699ef5b878e', 'f8eab99d-793b-4532-999c-7e2636378fa0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c147519d-a224-4130-97f7-14f0ec7f2d61', 'kuali.attribute.registration.group.is.generated', '15cf8c73-32fd-4413-acab-f2813e268102', 1, '73137502-bd44-42ae-9140-c74f2f4e29c2')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7be27d6a-0d2f-435b-9295-4d6876c28458', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', '', '', '', '', '15cf8c73-32fd-4413-acab-f2813e268102', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c481e609-2ac4-4830-8a8b-db44acb2615f')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('599022f4-f6e5-4c69-8319-0b9da82c2ecc', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:34.228', '', 'CHEM237-FO-RG', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM237-FO-RG', '15cf8c73-32fd-4413-acab-f2813e268102', '5dcbe36c-76e9-4b66-ba5d-2b06851081bc')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('85f37114-2671-4594-990d-53ebfc3604ae', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:34.273', '', 'CHEM237-RG-AO', '15cf8c73-32fd-4413-acab-f2813e268102', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM237-RG-AO', '7f7fe758-82df-4894-aee9-7b1d67218980', 'cd4d8935-02fb-4f77-a8b5-97c26821a9e7')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('95bf2ba5-8967-4da8-9344-364856838e86', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 0, 1, 0, 1, '', '', '', '', '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', 'cecf6b12-884b-4908-9656-938f8ce3702d')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('cecf6b12-884b-4908-9656-938f8ce3702d', '7f7fe758-82df-4894-aee9-7b1d67218980')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('cecf6b12-884b-4908-9656-938f8ce3702d', '7bd32717-04f1-434b-b567-59a9108c3970')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('3c0ca907-ac09-4c01-8a36-bd320c3237f9', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'kuali.atp.ExamPeriod.2014Spring', 'FCCF08F7BF135E91E040440A9B9A420D', '', '', '', 'kuali.lui.exam.offering.state.draft', 'kuali.lui.type.exam.offering.final', '', '', '', '', '', '0a68c6f3-9b88-4654-86de-47c28b2bda20')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4fb142d0-34a7-441a-b1c5-bec6b6a3237b', 'kuali.attribute.final.exam.activity.driver', '0a68c6f3-9b88-4654-86de-47c28b2bda20', 'kuali.lu.type.activity.Lecture', '8ded58d5-2227-499e-9948-74058d950d11')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('89856d3a-26a7-42ab-bbfd-16860e309cac', 'kuali.attribute.final.exam.driver', '0a68c6f3-9b88-4654-86de-47c28b2bda20', 'PER_AO', '9998a47e-b106-413f-ba41-c0856f7f148c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('08e92be8-cc92-41e3-ab85-bfa22bdb419c', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', '', '', '', '', '0a68c6f3-9b88-4654-86de-47c28b2bda20', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7a18799b-e2f7-43c7-b061-378722fbce3f')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e3dd592c-1bad-4957-994e-3aa38848dd39', 0, 'admin', TIMESTAMP '2014-07-01 10:20:29.433', 'admin', TIMESTAMP '2014-07-01 10:20:29.433', TIMESTAMP '2014-07-01 10:20:34.606', '', '0a68c6f3-9b88-4654-86de-47c28b2bda20-FO-EO)', '7bd32717-04f1-434b-b567-59a9108c3970', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2eo', 'Fo-Eo-relation', '0a68c6f3-9b88-4654-86de-47c28b2bda20-FO-EO', '0a68c6f3-9b88-4654-86de-47c28b2bda20', '5a6aae05-dc29-49ab-b1d8-021cfadd8bf6')
/
insert into KSEN_LUILUI_RELTN_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('67d48f6d-4912-48a0-9c6d-5084694a920f', 'AO1', '5a6aae05-dc29-49ab-b1d8-021cfadd8bf6', '7f7fe758-82df-4894-aee9-7b1d67218980', 'd76c37dc-cb3b-45a0-a2be-12c41e22121d')
/
update KSEN_LUI set OBJ_ID='83f24127-d704-4760-b9d3-63972b5f256b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:21:02.766', ATP_ID='kuali.atp.2014Spring', CLU_ID='676591d3-8703-499c-8333-637a15e46134', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS='', MIN_SEATS='', NAME='CHEM237 AO', DESCR_PLAIN='', REF_URL='' where ID='d89bca70-3b2b-4db3-b6d2-df7ce08b8ecf' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='79951f21-1b37-4b2b-946a-cbae24fc5fa2', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:21:02.766', LUI_CD='G', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='d89bca70-3b2b-4db3-b6d2-df7ce08b8ecf', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='ab267af1-7907-4410-a882-743fc2734f95' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='a96ebb22-af68-4714-8012-ba9fb4d63da6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:21:02.766', DESCR_FORMATTED='', LUI_ID='d89bca70-3b2b-4db3-b6d2-df7ce08b8ecf', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='0bfc1ab0-2936-4f57-b35a-9b612ec3286f' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='586f62fa-6364-4015-8484-8dbfb7846c48', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:21:02.766', ATP_ID='kuali.atp.2014Spring', CLU_ID='676591d3-8703-499c-8333-637a15e46134', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS='', MIN_SEATS='', NAME='CHEM237 AO', DESCR_PLAIN='', REF_URL='' where ID='f0249a73-cf15-4210-a3d8-ec55464bc1b4' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='dba43ef3-cf04-42b7-84d8-3e3ed5c4e8fa', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:21:02.766', LUI_CD='H', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='f0249a73-cf15-4210-a3d8-ec55464bc1b4', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='e7369fe8-cef3-4c55-8837-327d9e2ea985' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='24ff43fe-54b4-4241-b78c-c923b66c8f73', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:21:02.766', DESCR_FORMATTED='', LUI_ID='f0249a73-cf15-4210-a3d8-ec55464bc1b4', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='4cec10d1-940c-436d-bcdc-7794806bc379' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='24b0b262-95df-4e7b-932c-17d94e51047c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:21:02.766', ATP_ID='kuali.atp.2014Spring', CLU_ID='676591d3-8703-499c-8333-637a15e46134', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS='', MIN_SEATS='', NAME='CHEM237 AO', DESCR_PLAIN='', REF_URL='' where ID='c0429a1c-60c9-4a2b-b1cd-31ecd61bf647' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='9e21f3b7-a828-41af-9c4c-53c4b9a3c4cf', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:21:02.766', LUI_CD='I', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='c0429a1c-60c9-4a2b-b1cd-31ecd61bf647', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='ac7de7c8-cc4c-46b5-a783-bef90fc8a46a' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='42860c7b-8879-432f-be47-9c0af7d303de', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:21:02.766', DESCR_FORMATTED='', LUI_ID='c0429a1c-60c9-4a2b-b1cd-31ecd61bf647', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='77e77925-f740-45ef-8b59-ce5ff4beb169' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='bfde0562-267a-4fde-80db-63b271bd4418', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:21:02.766', ATP_ID='kuali.atp.2014Spring', CLU_ID='676591d3-8703-499c-8333-637a15e46134', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS='', MIN_SEATS='', NAME='CHEM237 AO', DESCR_PLAIN='', REF_URL='' where ID='29f51280-f9ef-47e5-a746-3985d1bb2f37' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='f7c814f7-9de1-4953-8484-f90f8ce1e582', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:21:02.766', LUI_CD='J', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='29f51280-f9ef-47e5-a746-3985d1bb2f37', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='3d0e24ec-45bb-45b7-9cc7-78da9a201463' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='5e514e1c-71f3-4ca4-b789-40ee513f70f9', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:21:02.766', DESCR_FORMATTED='', LUI_ID='29f51280-f9ef-47e5-a746-3985d1bb2f37', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='dc7e5710-db88-4dc4-965d-020a78e3541b' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='2e40830c-b249-405a-bcbc-8a449d68d9da', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:21:02.766', ATP_ID='kuali.atp.2014Spring', CLU_ID='676591d3-8703-499c-8333-637a15e46134', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS='', MIN_SEATS='', NAME='CHEM237 AO', DESCR_PLAIN='', REF_URL='' where ID='7f7fe758-82df-4894-aee9-7b1d67218980' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='8a6b0507-9c4f-41b5-b07c-c082a7177150', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:21:02.766', LUI_CD='K', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='7f7fe758-82df-4894-aee9-7b1d67218980', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='c4b1b35d-fb76-4372-a3a1-7a7d18a76dc7' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='951b6e78-d7e6-4eae-85e2-bd5618855810', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-01 10:21:02.766', DESCR_FORMATTED='', LUI_ID='7f7fe758-82df-4894-aee9-7b1d67218980', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE=null where ID='c7d8b915-e4cc-4efc-98fe-b6a622c6132c' and VER_NBR=0
/
