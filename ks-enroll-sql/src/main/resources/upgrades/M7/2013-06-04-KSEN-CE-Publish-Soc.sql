update KSEN_LUI set OBJ_ID='3a57d248-de5b-4b6d-9bcc-7571fd59b20f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='1bf3adb6-8b48-441f-9d68-cc1005dd9cc6', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=46, MIN_SEATS=46, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='036a740e-f4a6-4c63-8044-4b43495303df' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='c2dd2111-6e98-487c-8b96-92b51bd9d0a6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='036a740e-f4a6-4c63-8044-4b43495303df', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='17627545-fc60-4454-bc37-ec4efacd5b7f' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='e2af45e3-fb6c-499b-8234-2a5c154f2c20', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', DESCR_FORMATTED='', LUI_ID='036a740e-f4a6-4c63-8044-4b43495303df', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='2c5efb26-aa03-4110-b1be-1605033f5333' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='9fe2fd51-a876-4387-98f1-623cc71dd5da', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='fbe00f3e-788a-460c-b686-3303215621fa', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture/Discussion', LUI_STATE='kuali.lui.format.offering.state.planned', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture/Discussion', DESCR_PLAIN='Courses with Lecture/Discussion', REF_URL='' where ID='3f00e87c-ad8f-43ae-a84e-e640a77df3e8' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='dc6a5036-f60c-4105-95c5-ad2165bd55fb', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture/Discussion', LUI_ID='3f00e87c-ad8f-43ae-a84e-e640a77df3e8', ORGID='', SHRT_NAME='LEC/DIS', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a33d63e4-723a-4ae8-87f3-9d544b347a99' and VER_NBR=0
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='3f00e87c-ad8f-43ae-a84e-e640a77df3e8'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('3f00e87c-ad8f-43ae-a84e-e640a77df3e8', 'kuali.lui.type.activity.offering.discussion')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('3f00e87c-ad8f-43ae-a84e-e640a77df3e8', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='b3f512c8-6800-498a-97b6-a2f3da58e0e7', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='31831b51-5be1-4f61-961f-a23d60ad2c53', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='The chemistry of carbon: aliphatic compounds, aromatic compounds, stere chemistry, halides, amines, and amides, acids, esters, carbohydrates, and natural products. This course is intended for students in curricula requiring only one semester of organic chemistry. Students requiring two or more years of chemistry should register for CHEM231/232 or CHEM237.', LUI_STATE='kuali.lui.course.offering.state.planned', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='CHEM105 CO', DESCR_PLAIN='The chemistry of carbon: aliphatic compounds, aromatic compounds, stere chemistry, halides, amines, and amides, acids, esters, carbohydrates, and natural products. This course is intended for students in curricula requiring only one semester of organic chemistry. Students requiring two or more years of chemistry should register for CHEM231/232 or CHEM237.', REF_URL='null' where ID='fd03541a-0e49-4725-a56c-048ca06ae3e3' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='2c785cbc-b30a-429f-b8c8-65579e063b15', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', LUI_CD='CHEM105', DIVISION='CHEM', IDENT_LEVEL='', LNG_NAME='Fundamental of Organic and Biochemistry', LUI_ID='fd03541a-0e49-4725-a56c-048ca06ae3e3', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a350733f-eb6d-407a-909a-a84f59708293' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='3bea2ae7-7037-4554-b193-0e5a5064d97d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', DESCR_FORMATTED='', LUI_ID='fd03541a-0e49-4725-a56c-048ca06ae3e3', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='f6b0ffb7-a597-40f6-a012-e0bd6fb49ed0' and VER_NBR=0
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='fd03541a-0e49-4725-a56c-048ca06ae3e3'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('fd03541a-0e49-4725-a56c-048ca06ae3e3', '4284516367')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='fd03541a-0e49-4725-a56c-048ca06ae3e3'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('fd03541a-0e49-4725-a56c-048ca06ae3e3', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('fd03541a-0e49-4725-a56c-048ca06ae3e3', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('fd03541a-0e49-4725-a56c-048ca06ae3e3', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('fd03541a-0e49-4725-a56c-048ca06ae3e3', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='e7c8dd67-047a-494a-a1bf-29c3f824da9f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='e073a783-300c-4e22-8e41-f661639d3ce5', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=46, MIN_SEATS=46, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='9bc78b34-81d7-4869-9070-d90076845599' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='3da0a94f-d347-430f-83b4-46f3ee6ee2a3', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='9bc78b34-81d7-4869-9070-d90076845599', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='0ebd17c5-e0cb-4d6a-8c11-9a8381d8c0e3' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='b951ab6f-751a-4c0a-a6c6-c338edbc6c07', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', DESCR_FORMATTED='', LUI_ID='9bc78b34-81d7-4869-9070-d90076845599', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='e0224bd6-e295-4cde-a619-28f4ef1fb13d' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='4385fbd0-ef56-4967-8d3e-0d9090016e19', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='29737d5a-57a4-49c5-a8fb-2dd1eb0ea581', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=48, MIN_SEATS=48, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='f9ecdacf-a144-4319-9a3e-35b0ed00982b' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='7a6726f5-155d-4637-bb6a-b5b5ff3109e7', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='f9ecdacf-a144-4319-9a3e-35b0ed00982b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b8c7ade6-d54b-4482-821f-909b3d4f7e31' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='1e42e9e9-d017-493b-88a3-af58870a624b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', DESCR_FORMATTED='', LUI_ID='f9ecdacf-a144-4319-9a3e-35b0ed00982b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='aa76ecbd-61fc-42a9-8822-1503973ac19a' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='b3b7f446-3734-4e9c-9ac3-ec4d74ad3046', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='25889d4f-0612-424c-87a5-e96f60f3e096', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture/Lab', LUI_STATE='kuali.lui.format.offering.state.planned', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture/Lab', DESCR_PLAIN='Courses with Lecture/Lab', REF_URL='' where ID='075fbf70-3d90-4d8f-b81f-0c8c9cdb794e' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='0062fec3-426b-4e8e-a6e6-b247da9e69d9', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture/Lab', LUI_ID='075fbf70-3d90-4d8f-b81f-0c8c9cdb794e', ORGID='', SHRT_NAME='LEC/LAB', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='c34cc25c-109a-41b7-9c48-3fb68b11cb3f' and VER_NBR=0
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='075fbf70-3d90-4d8f-b81f-0c8c9cdb794e'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('075fbf70-3d90-4d8f-b81f-0c8c9cdb794e', 'kuali.lui.type.activity.offering.lab')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('075fbf70-3d90-4d8f-b81f-0c8c9cdb794e', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='7ab68367-9925-4a20-bdd6-072dc74f4f91', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='CLUID-CHEM277-200608000000', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='Quantitative analysis, inorganic analytical chemistry, and an introduction to bio-analytical instrumentation and techniques.', LUI_STATE='kuali.lui.course.offering.state.planned', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='CHEM277 CO', DESCR_PLAIN='Quantitative analysis, inorganic analytical chemistry, and an introduction to bio-analytical instrumentation and techniques.', REF_URL='null' where ID='ca0c00f4-ff28-4bce-8ba7-faad4692f320' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='c02cd516-64fe-450c-ab7d-5abfcccb5aa6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', LUI_CD='CHEM277', DIVISION='CHEM', IDENT_LEVEL='', LNG_NAME='Fundamentals of Analytical and Bioanalytical Chemistry Laboratory', LUI_ID='ca0c00f4-ff28-4bce-8ba7-faad4692f320', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='da57f2de-c49a-4b2f-a03c-d5b96455d1f6' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='4fe60067-c0ec-4e31-8559-e1192cfea184', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', DESCR_FORMATTED='', LUI_ID='ca0c00f4-ff28-4bce-8ba7-faad4692f320', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='0c40bf5b-b799-4b66-9d86-17f272c04087' and VER_NBR=0
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='ca0c00f4-ff28-4bce-8ba7-faad4692f320'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('ca0c00f4-ff28-4bce-8ba7-faad4692f320', '4284516367')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='ca0c00f4-ff28-4bce-8ba7-faad4692f320'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ca0c00f4-ff28-4bce-8ba7-faad4692f320', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ca0c00f4-ff28-4bce-8ba7-faad4692f320', 'kuali.resultComponent.grade.letter')
/
update KSEN_LUI set OBJ_ID='fbf374ae-f7c0-40fa-814b-5be9fd9004c4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='a29cf7a2-e3f1-4074-af10-5efd16838937', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='a57312f3-d0ba-4e47-bf6a-59892353b332' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='711bec62-07f6-4d47-9f04-7e1161c86c9c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='a57312f3-d0ba-4e47-bf6a-59892353b332', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='30a87c0e-f571-4c00-b0d2-d4bfe2e88f76' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='32ee5e3e-582e-4666-9727-4086fa8d6354', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', DESCR_FORMATTED='', LUI_ID='a57312f3-d0ba-4e47-bf6a-59892353b332', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b5977d0c-6767-447c-9b05-e277cc8f6af4' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='1d6da209-8c06-46a0-8414-3cdab004c353', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='172819c3-11a4-4baf-81c5-63a08bbfb3ed', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='1240da0c-7e1d-4538-9384-9039d41c0d80' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='bbbf9fa2-cc3b-4c4f-b87a-5d9efba30f44', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', LUI_CD='C', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='1240da0c-7e1d-4538-9384-9039d41c0d80', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='651626f0-5fc5-4d5e-9228-70fde0fc6ce5' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='d5037bfc-390f-49e0-9167-1574294037cc', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:45:57.693', DESCR_FORMATTED='', LUI_ID='1240da0c-7e1d-4538-9384-9039d41c0d80', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b910cf92-c5e3-406f-8fc7-740a73ce369a' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='24b6b458-fdf2-4e30-ab14-feb8b9a95a40', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='c7ce59fa-08cf-483d-9ade-9c4dab5a94e3', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=35, MIN_SEATS=35, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='8719e6fd-34c8-409f-951c-d87d08a44a6b' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='1bbdfc31-e6f1-401a-8508-7d00f649b836', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='8719e6fd-34c8-409f-951c-d87d08a44a6b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='376165a6-8aae-4d73-bcfb-49b6e9f8db8c' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='2424104c-219f-4e41-93fa-eab17923e88a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', DESCR_FORMATTED='', LUI_ID='8719e6fd-34c8-409f-951c-d87d08a44a6b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='6f31f742-d214-4f1d-b4ec-11faf3c4445a' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='5a1732ea-5104-4773-bef8-e84d1f421da7', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='f9d59526-20d1-418c-864f-9da66959d0fd', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture only', LUI_STATE='kuali.lui.format.offering.state.planned', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture Only', DESCR_PLAIN='Courses with Lecture only', REF_URL='' where ID='f17c3c48-6bc1-42c8-9bad-5ecbace05b2a' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='e443a755-94aa-4cfd-9252-92b1e13d6708', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture Only', LUI_ID='f17c3c48-6bc1-42c8-9bad-5ecbace05b2a', ORGID='', SHRT_NAME='LEC', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='f9dfddde-2cbb-4d7f-8c35-e2b794656656' and VER_NBR=0
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='f17c3c48-6bc1-42c8-9bad-5ecbace05b2a'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('f17c3c48-6bc1-42c8-9bad-5ecbace05b2a', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='b1d7772b-39e2-45a7-9c56-8c5be1aa4c74', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='18f8deda-26ce-4cb0-a66e-631f690f1650', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='Wide range of texts, genres, and themes from ancient and medieval Western traditions. Study of cultural, historical, and artistic forces shaping traditions, and the influence and relevance of those traditions to life in twenty-first century.', LUI_STATE='kuali.lui.course.offering.state.planned', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='ENGL201 CO', DESCR_PLAIN='Wide range of texts, genres, and themes from ancient and medieval Western traditions. Study of cultural, historical, and artistic forces shaping traditions, and the influence and relevance of those traditions to life in twenty-first century.', REF_URL='null' where ID='74101915-ee0e-44d5-8780-4301345fa925' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='07f711c1-8f5a-4389-9ebe-6c4deb71afec', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', LUI_CD='ENGL201', DIVISION='ENGL', IDENT_LEVEL='', LNG_NAME='Inventing Western Literature: Ancient and Medieval Traditions', LUI_ID='74101915-ee0e-44d5-8780-4301345fa925', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='bbe4cdeb-cc5e-42db-85d0-a106112edb21' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='ee43d3ef-d4d1-432e-9fa7-24472f39c5e5', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', DESCR_FORMATTED='', LUI_ID='74101915-ee0e-44d5-8780-4301345fa925', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='3758b45f-3317-47f4-bf0a-99f0ad99f578' and VER_NBR=0
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='74101915-ee0e-44d5-8780-4301345fa925'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('74101915-ee0e-44d5-8780-4301345fa925', '2677933260')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='74101915-ee0e-44d5-8780-4301345fa925'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('74101915-ee0e-44d5-8780-4301345fa925', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('74101915-ee0e-44d5-8780-4301345fa925', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('74101915-ee0e-44d5-8780-4301345fa925', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('74101915-ee0e-44d5-8780-4301345fa925', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='6daaa8a4-5e08-4e22-a000-123c7f95f47e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='13d328e5-e4d2-4be5-b63c-3c227edb36fa', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=96, MIN_SEATS=96, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='4ed64abb-e257-4c18-af2a-edc034278b89' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='e0091f51-75e0-4de8-a972-40be94cf1516', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='4ed64abb-e257-4c18-af2a-edc034278b89', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='f2271a37-a44f-402c-a22a-4d028b32ffd4' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='a73c02d6-ac97-4ffa-914c-bce8d1cb1c6c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', DESCR_FORMATTED='', LUI_ID='4ed64abb-e257-4c18-af2a-edc034278b89', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b3a71a8e-1384-461b-badc-00dfe1644a16' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='17a94bde-0d50-4015-9fff-6c0e15fac02e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='c6a8b277-7dba-4e06-9f9c-47e5dc936a68', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture/Discussion', LUI_STATE='kuali.lui.format.offering.state.planned', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture/Discussion', DESCR_PLAIN='Courses with Lecture/Discussion', REF_URL='' where ID='c5825a7c-743a-460d-9d98-beb9d1a8e087' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='10a288b4-7150-4a6d-a776-0ff40a16d6d8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture/Discussion', LUI_ID='c5825a7c-743a-460d-9d98-beb9d1a8e087', ORGID='', SHRT_NAME='LEC/DIS', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8befee5b-8175-4dfd-9b9a-eb19d88e4d2c' and VER_NBR=0
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='c5825a7c-743a-460d-9d98-beb9d1a8e087'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('c5825a7c-743a-460d-9d98-beb9d1a8e087', 'kuali.lui.type.activity.offering.discussion')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('c5825a7c-743a-460d-9d98-beb9d1a8e087', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='1e0e699a-e95a-4fcd-9004-0192d670cebf', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='5030c77c-dcd9-4906-8d7a-18c41bf3c44e', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='A detailed study of selected major texts of American literature from th 17th century to the 20th century. Issues such as race, gender, and regionalism. Authors such as Franklin, Hawthorne, Dickinson, Hemingway, and Morrison.', LUI_STATE='kuali.lui.course.offering.state.planned', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='ENGL313 CO', DESCR_PLAIN='A detailed study of selected major texts of American literature from th 17th century to the 20th century. Issues such as race, gender, and regionalism. Authors such as Franklin, Hawthorne, Dickinson, Hemingway, and Morrison.', REF_URL='null' where ID='22b1caeb-5ad7-4de7-93a5-8db056d43984' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='d3ffe01c-5f3e-4893-a19e-bb0236335609', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', LUI_CD='ENGL313', DIVISION='ENGL', IDENT_LEVEL='', LNG_NAME='American Literature', LUI_ID='22b1caeb-5ad7-4de7-93a5-8db056d43984', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='c8470fda-8209-4e5d-82da-7bfd036df384' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='a43b65ef-5f6d-4d8d-864e-e95d089e4935', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', DESCR_FORMATTED='', LUI_ID='22b1caeb-5ad7-4de7-93a5-8db056d43984', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='67233c95-26ee-4b50-9a1e-8fc9a9a10f94' and VER_NBR=0
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='22b1caeb-5ad7-4de7-93a5-8db056d43984'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('22b1caeb-5ad7-4de7-93a5-8db056d43984', '2677933260')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='22b1caeb-5ad7-4de7-93a5-8db056d43984'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('22b1caeb-5ad7-4de7-93a5-8db056d43984', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('22b1caeb-5ad7-4de7-93a5-8db056d43984', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('22b1caeb-5ad7-4de7-93a5-8db056d43984', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('22b1caeb-5ad7-4de7-93a5-8db056d43984', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='634d25e5-a8ef-4be1-8e39-620707622a49', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='7031694d-60f2-4012-8e96-4b2f6fc7ff1c', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=24, MIN_SEATS=24, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='0e5e5ded-2722-4d46-9ac5-964b6b95006c' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='9a7930a0-8e79-4246-8af7-469b89980b93', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='0e5e5ded-2722-4d46-9ac5-964b6b95006c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1d01d910-0cfa-4d1e-869f-4d44580e6a2b' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='73488f34-6fd4-4375-82f2-ef849f26bb96', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', DESCR_FORMATTED='', LUI_ID='0e5e5ded-2722-4d46-9ac5-964b6b95006c', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='bbdc63e7-3f0d-4de2-873a-3777a6c83700' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='156442bb-9d91-4466-80d8-eb3d0ee8aba3', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='8a6d57fc-0732-47ef-9fd8-44d6318922b3', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=24, MIN_SEATS=24, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='fa925c0c-0077-4a2f-bae3-1a1be93342ba' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='c33e333b-9596-479a-86eb-347265e5bd9a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', LUI_CD='C', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='fa925c0c-0077-4a2f-bae3-1a1be93342ba', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1d14222c-c457-4373-b085-572206b9a41a' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='1cdf5baf-608f-46ce-b668-328b0172192c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', DESCR_FORMATTED='', LUI_ID='fa925c0c-0077-4a2f-bae3-1a1be93342ba', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b49fece1-5348-422d-9fcc-ae9175a36754' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='69ba85fd-1de8-4c55-b94a-a6f44b3f0cba', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='bd80c228-fc5d-4178-9a88-896fa8a1b17e', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=24, MIN_SEATS=24, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='62350ce0-2b1e-4566-bf95-ead6429383ce' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='e593635e-bf76-4520-89ab-a632efe1ba8b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', LUI_CD='D', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='62350ce0-2b1e-4566-bf95-ead6429383ce', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='efa9294f-8805-45d7-a3de-06713904b85f' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='af6af553-d900-4bad-a923-c4c358c0bd5a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', DESCR_FORMATTED='', LUI_ID='62350ce0-2b1e-4566-bf95-ead6429383ce', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='e3dd1380-a4b2-48b1-8f4b-17d1068e419b' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='745ba1f2-d943-49d8-b84f-4da00ca4ce2f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='38e4c5cd-0e98-4174-8712-9f08616c677e', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=24, MIN_SEATS=24, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='122dc077-c07c-419d-ae57-f816b712a562' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='86c0259f-32ec-49a5-a0d0-78baf5294b98', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', LUI_CD='E', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='122dc077-c07c-419d-ae57-f816b712a562', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='9abce5d8-3d5e-4084-a18b-5925be131125' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='f9b9217d-0b82-4a3e-9b88-416be14398de', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:46:41.809', DESCR_FORMATTED='', LUI_ID='122dc077-c07c-419d-ae57-f816b712a562', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='cddf1caa-6c95-4924-b4d5-a3044eadcac1' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='df57e4f6-18f2-4c5a-85c3-c394c5284696', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='25f408c6-e481-48cb-82f8-76baf92f9a31', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=180, MIN_SEATS=180, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='c186e825-424e-48b4-86ae-bfc6aeffdfee' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='d4eb7727-0f50-4557-9cbf-858e811d6829', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='c186e825-424e-48b4-86ae-bfc6aeffdfee', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='de82ae12-1824-428d-801c-c0bce308ee86' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='31e827eb-c6dd-44d9-ab02-7b596d9ce6be', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', DESCR_FORMATTED='', LUI_ID='c186e825-424e-48b4-86ae-bfc6aeffdfee', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='6e11902d-6901-4919-a3a2-0e8f60128870' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='a7c1706e-9732-42ec-98f0-fd67ae054af2', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='9c7796d1-4985-4e4f-a376-f94735348147', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture/Discussion', LUI_STATE='kuali.lui.format.offering.state.planned', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture/Discussion', DESCR_PLAIN='Courses with Lecture/Discussion', REF_URL='' where ID='56d750c7-44db-4fc0-a891-f09c6f86d55f' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='79c5c0cc-3cda-4bea-ac55-db0f48973dd4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture/Discussion', LUI_ID='56d750c7-44db-4fc0-a891-f09c6f86d55f', ORGID='', SHRT_NAME='LEC/DIS', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='40727aa6-af5e-40ba-886c-f7a810fe1d6d' and VER_NBR=0
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='56d750c7-44db-4fc0-a891-f09c6f86d55f'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('56d750c7-44db-4fc0-a891-f09c6f86d55f', 'kuali.lui.type.activity.offering.discussion')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('56d750c7-44db-4fc0-a891-f09c6f86d55f', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='73952c20-7ae1-45fc-ba3b-531e584ffa71', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='a80b4adf-214f-4e33-8492-af5b9beb48e8', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='Interpretation of select literature and art of the ancient Mediterranean world with a view to illuminating the antecedents of modern culture; religion and myth in the ancient near East; Greek philosophical, scientific, and literary invention; and the Roman tradition in politics and administration.', LUI_STATE='kuali.lui.course.offering.state.planned', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='HIST110 CO', DESCR_PLAIN='Interpretation of select literature and art of the ancient Mediterranean world with a view to illuminating the antecedents of modern culture; religion and myth in the ancient near East; Greek philosophical, scientific, and literary invention; and the Roman tradition in politics and administration.', REF_URL='null' where ID='4a11a872-7f15-4fa4-b3bb-31b02e1b945e' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='72c5123a-8268-4461-a98b-14e48b1821d6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='HIST110', DIVISION='HIST', IDENT_LEVEL='', LNG_NAME='The Ancient World', LUI_ID='4a11a872-7f15-4fa4-b3bb-31b02e1b945e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='7c0f16ca-c9e0-4bcb-aae9-bb669ce77786' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='307a9b93-2724-4982-b690-f71b0bd791ca', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', DESCR_FORMATTED='', LUI_ID='4a11a872-7f15-4fa4-b3bb-31b02e1b945e', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='dba1ae29-977e-4aed-86c2-c7285f8a3775' and VER_NBR=0
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='4a11a872-7f15-4fa4-b3bb-31b02e1b945e'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('4a11a872-7f15-4fa4-b3bb-31b02e1b945e', '3213375036')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='4a11a872-7f15-4fa4-b3bb-31b02e1b945e'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4a11a872-7f15-4fa4-b3bb-31b02e1b945e', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4a11a872-7f15-4fa4-b3bb-31b02e1b945e', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4a11a872-7f15-4fa4-b3bb-31b02e1b945e', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4a11a872-7f15-4fa4-b3bb-31b02e1b945e', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='cdc59953-6d16-46f6-ae70-3a2813d40880', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='3e116be8-1102-41ec-945f-92f8cb201300', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='ae9703d9-be4e-4e8a-9288-3df4067ba126' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='914b24d3-94db-4b38-a5a9-5f025058ef58', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='ae9703d9-be4e-4e8a-9288-3df4067ba126', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='d6c0de06-1220-4dab-8cbb-803998329bd3' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='f7a72ab6-3d8c-47b0-9cdb-b6e0fef5da5d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', DESCR_FORMATTED='', LUI_ID='ae9703d9-be4e-4e8a-9288-3df4067ba126', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='cde324ce-8569-4dda-bf27-4a0a0aa91a36' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='1ad7231a-eac1-47eb-b978-54630c9b3497', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='778c4a89-a3be-48c8-ae02-afbdf2f34970', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='c4771b96-a10a-4857-aaf8-bd4beb3c0f85' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='87aa1da0-2dad-4e86-ad6d-39bf5ff70e44', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='C', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='c4771b96-a10a-4857-aaf8-bd4beb3c0f85', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='e90a296b-b8be-4e94-a195-7459734856ab' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='04998963-bfbd-4d99-bdb5-4f6eaf31f517', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', DESCR_FORMATTED='', LUI_ID='c4771b96-a10a-4857-aaf8-bd4beb3c0f85', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='61ca476a-ce8e-40a9-9fd3-526e37d2dd45' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='42b1dc8b-3e9f-412a-ad39-d9aeef73fdd7', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='3cad8c8d-4ded-4ff1-aa0a-60f5dce6edf1', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='a3b6c14e-8615-4479-a1c0-b033d0228401' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='e233ebc2-c961-4be7-8895-baf4db0c2363', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='D', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='a3b6c14e-8615-4479-a1c0-b033d0228401', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='637d1b50-cf92-4526-be19-cfac7117666e' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='df443f6d-c787-4ecc-9918-af278a5288f0', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', DESCR_FORMATTED='', LUI_ID='a3b6c14e-8615-4479-a1c0-b033d0228401', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='af4f3036-dea3-491f-93d2-a264946ef3ea' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='a157ef3d-05de-421b-9e3d-3193ca83cec2', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='2d5222a0-843e-4c1b-9f7e-847887ab9d2c', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='bc880949-6bc5-4c4a-8e9a-ff2dc107b92a' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='2fc43111-ab2a-4f34-a69a-fbfa760e3c46', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='E', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='bc880949-6bc5-4c4a-8e9a-ff2dc107b92a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b03ea796-6c19-429a-8a8e-e10d12d0d698' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='f0f17554-d686-4b9f-b807-81f8611f7d68', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', DESCR_FORMATTED='', LUI_ID='bc880949-6bc5-4c4a-8e9a-ff2dc107b92a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='5f6d499c-9356-45de-926f-0ac339cb7a49' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='c6b0fc9d-dbbc-4da3-93c6-97d09ed1ea3a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='347af0e5-98a9-4b65-9527-2976bcf251a0', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='b2789b95-350b-4f3a-9183-7fdfd9108283' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='1aa46b9a-040f-4f43-9ef4-765b105912e9', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='F', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='b2789b95-350b-4f3a-9183-7fdfd9108283', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='3c6ab59c-bbac-456b-a321-2d697495333b' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='21c0d420-0dda-41d1-a03d-f183605e88e4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', DESCR_FORMATTED='', LUI_ID='b2789b95-350b-4f3a-9183-7fdfd9108283', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='13d63d98-a4ce-49a2-b4b6-446274ba77d7' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='1fe9043a-aec3-4f82-83f9-f05c27bdc3a8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='fece0248-468d-4b08-9fcf-955a6b6b703d', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='873a3992-bd23-4f96-90cf-9b0ffca8c0d9' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='770327d4-ecfe-4c5e-a143-8f58d2ca1f90', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='G', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='873a3992-bd23-4f96-90cf-9b0ffca8c0d9', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='4cf56318-432e-4635-8771-801a7c7d577b' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='a969ba2d-a22b-4fbd-8cdd-6538c9c9e47a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', DESCR_FORMATTED='', LUI_ID='873a3992-bd23-4f96-90cf-9b0ffca8c0d9', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='64339611-f5f1-4740-91c7-bc92cc9f9d75' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='cec659a7-2921-4f11-9861-06e9bd5c4043', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='6be4f320-fa19-46d5-b560-a756adba1c46', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='5df66739-494e-4c00-b440-9f9f4ad00e2b' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='c14dd50e-60ab-4fc0-9579-db50bf3465da', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='H', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='5df66739-494e-4c00-b440-9f9f4ad00e2b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='4fcd6cd8-cda8-4c70-8591-e3469072d3e4' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='4d3ea88a-f6cc-45e6-9531-1a2949bc5fd2', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', DESCR_FORMATTED='', LUI_ID='5df66739-494e-4c00-b440-9f9f4ad00e2b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='8c902fb2-1aed-452c-99a6-c328ed6476ab' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='ab8cce5b-6004-468a-8f84-1ead61a9408c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='8ac0fe70-2eae-4cb2-850b-e1dd9c8b7b32', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='e2222eee-de96-4e03-aed1-82a9aff7b158' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='44a7f100-8cfc-4a20-baa2-660145f74cb3', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='I', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='e2222eee-de96-4e03-aed1-82a9aff7b158', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='fee2db7a-9fc8-4a80-97f8-6617bf4f944f' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='735ed3a2-61d9-4679-93ef-fa35492b1a7b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', DESCR_FORMATTED='', LUI_ID='e2222eee-de96-4e03-aed1-82a9aff7b158', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='71292101-5f7d-4633-a469-ac41ae4c7fce' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='5b5cc087-2a11-4a02-9317-47dc8425f027', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='4e9f21d1-522f-4b8a-b003-c8807e64609e', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='935a4774-97aa-4289-ab40-0059e05f57f9' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='e121ac04-3343-48cb-bca1-a1e16ab7d3f7', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='J', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='935a4774-97aa-4289-ab40-0059e05f57f9', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='55256743-9a32-4db6-8737-bdb4857fa177' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='866501a0-85d9-4ee6-bc4d-41dbb8725fe2', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', DESCR_FORMATTED='', LUI_ID='935a4774-97aa-4289-ab40-0059e05f57f9', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='1e3fe06f-d576-47f5-a280-c43bfc54c543' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='615802c9-95b2-404a-9e0b-29cc2caba09f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='9f2b36b3-4761-4469-a11f-68064bb11909', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=80, MIN_SEATS=80, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='3aade379-d19f-4f49-ab85-ec4ff4f2bd4f' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='a1f7efe7-7a01-4851-b7fb-4dbda050c26e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='3aade379-d19f-4f49-ab85-ec4ff4f2bd4f', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b7a784bf-a73f-45dc-b5bd-ebbe1ff94a73' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='f7048aa1-bcc2-430e-87e5-582695439370', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', DESCR_FORMATTED='', LUI_ID='3aade379-d19f-4f49-ab85-ec4ff4f2bd4f', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='930a024b-04da-4c2e-ab2a-daefb08ae305' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='bc5f3d7d-020f-419e-ac58-77be494410fe', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='f1dee561-a3e7-41b5-a1e0-0d4a77d7987e', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture/Discussion', LUI_STATE='kuali.lui.format.offering.state.planned', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture/Discussion', DESCR_PLAIN='Courses with Lecture/Discussion', REF_URL='' where ID='8e8eef1c-0b14-45ff-9f3f-b25a7d00769e' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='820fe705-a7ee-491e-9f6e-827ac70c3b55', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture/Discussion', LUI_ID='8e8eef1c-0b14-45ff-9f3f-b25a7d00769e', ORGID='', SHRT_NAME='LEC/DIS', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b0b63824-a3b9-48bb-87c5-0861e7561e9f' and VER_NBR=0
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='8e8eef1c-0b14-45ff-9f3f-b25a7d00769e'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('8e8eef1c-0b14-45ff-9f3f-b25a7d00769e', 'kuali.lui.type.activity.offering.discussion')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('8e8eef1c-0b14-45ff-9f3f-b25a7d00769e', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='5ca8913d-5584-4924-a0ea-0b3f7b3a67d3', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='CLUID-HIST204-201101000000', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='An exploration of the roots of modern science from the ancient Greeks through the medieval and early modern periods. Focus on the men and women who helped to create the sciences as well as changing public perceptions of their disciplines.', LUI_STATE='kuali.lui.course.offering.state.planned', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='HIST204 CO', DESCR_PLAIN='An exploration of the roots of modern science from the ancient Greeks through the medieval and early modern periods. Focus on the men and women who helped to create the sciences as well as changing public perceptions of their disciplines.', REF_URL='null' where ID='565a6a2c-8621-4aba-a511-44348a904809' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='b6aeeb56-d2f1-46fb-bb2f-80405199622c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='HIST204', DIVISION='HIST', IDENT_LEVEL='', LNG_NAME='Introduction to the History of Science', LUI_ID='565a6a2c-8621-4aba-a511-44348a904809', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='0bef670e-1ca2-499a-87f6-4a77d31ce44e' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='c468db13-b97b-451e-9961-52332659e5a2', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', DESCR_FORMATTED='', LUI_ID='565a6a2c-8621-4aba-a511-44348a904809', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='bc83033f-bf55-4a11-8e24-2c502abec0df' and VER_NBR=0
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='565a6a2c-8621-4aba-a511-44348a904809'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('565a6a2c-8621-4aba-a511-44348a904809', '3213375036')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='565a6a2c-8621-4aba-a511-44348a904809'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('565a6a2c-8621-4aba-a511-44348a904809', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('565a6a2c-8621-4aba-a511-44348a904809', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('565a6a2c-8621-4aba-a511-44348a904809', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('565a6a2c-8621-4aba-a511-44348a904809', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='be555447-e339-4fc2-8ea1-f88e01c01064', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='e70ffd7e-977a-4241-ad53-4a98dbdb8bed', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='003cb053-a83d-4e93-b0ed-e470bed6f29a' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='4899694f-9d43-459f-bb27-1a498040bf48', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='003cb053-a83d-4e93-b0ed-e470bed6f29a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='34febd32-8c6a-4307-b88a-f08353750bcc' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='5832607f-f99a-4c44-98e9-259bf8a4c75c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', DESCR_FORMATTED='', LUI_ID='003cb053-a83d-4e93-b0ed-e470bed6f29a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='e1e51c5f-4610-4f1e-9541-2c3551411dd4' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='73736666-026d-4e1c-ae2b-840befa52a50', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='04f3e47b-d1ba-47f3-b243-5cd531b84f40', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='397f2167-77a8-4e89-ad44-79be6ae1da74' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='6aef903a-1d9c-4f9a-8915-58f339cdd3f7', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='C', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='397f2167-77a8-4e89-ad44-79be6ae1da74', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='bc3d0777-0eeb-4e45-9227-12de8aba11a9' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='82a7a8d5-4fb1-4c72-ba65-46c2a6d21bac', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', DESCR_FORMATTED='', LUI_ID='397f2167-77a8-4e89-ad44-79be6ae1da74', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='7c09b0b0-b0f7-4676-854e-f4e5adc5767f' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='d38afa57-bc17-41e3-ab5e-4955fe8e2f89', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='50f30065-8756-4f96-b186-b822f3973474', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='db7f5043-3473-4bb5-93fa-677394dc793a' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='172af769-9c8e-436b-85b5-f327d33b6349', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='D', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='db7f5043-3473-4bb5-93fa-677394dc793a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='2f2ece2e-2c63-4aa1-8ddf-390aeff8df42' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='3eb6f35d-49b9-4e55-b0a2-419fb02129f8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', DESCR_FORMATTED='', LUI_ID='db7f5043-3473-4bb5-93fa-677394dc793a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='8e263552-9308-4e30-b06e-0678d5d49e5e' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='5efc9d51-adb0-46f2-a0c8-5fad631fbe40', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='b117ee2a-8141-4a83-8bf9-c9c667db3ed7', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='7cc4db78-b0e8-40cc-8f98-33c95a969f6e' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='c4e84d51-ec82-4c68-b09e-1ab411619df1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', LUI_CD='E', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='7cc4db78-b0e8-40cc-8f98-33c95a969f6e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='bf12059f-0a29-4d3a-81b6-7ff2334abec3' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='eb76594b-4dbd-4c6e-9ac7-48ad487fef7d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:01.804', DESCR_FORMATTED='', LUI_ID='7cc4db78-b0e8-40cc-8f98-33c95a969f6e', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='6dfce852-d7f8-43dc-988a-b982497e0f65' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='01326ed6-5873-4fb6-8fa7-8625c59413f4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:38.307', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='580ebfa2-f873-4dfa-b803-f176b08c1669', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=150, MIN_SEATS=150, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='7d711c28-50cc-41a0-81c0-f87443686372' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='75f995b6-ad08-405b-9311-26522d62e4fc', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:38.307', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='7d711c28-50cc-41a0-81c0-f87443686372', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='57171047-8aad-4656-a4dc-ed2fef22fb60' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='b8b1ad6a-7833-4939-a3c9-03145acd5812', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:38.307', DESCR_FORMATTED='', LUI_ID='7d711c28-50cc-41a0-81c0-f87443686372', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='9c2361c6-fbf4-48d2-960e-70bc2d9dc4a3' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='8c828818-64d4-4515-8d74-e47229b335f5', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:38.307', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='fd49ba3c-931e-4be4-9a3e-b5200d27f44f', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture only', LUI_STATE='kuali.lui.format.offering.state.planned', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture Only', DESCR_PLAIN='Courses with Lecture only', REF_URL='' where ID='c1479830-593a-4040-8754-4feff9c0e17d' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='8f1ae1e0-a8f0-456c-93d3-548f2949b5f9', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:38.307', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture Only', LUI_ID='c1479830-593a-4040-8754-4feff9c0e17d', ORGID='', SHRT_NAME='LEC', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='92de51ef-4e9b-4734-b3e5-876e06d5759d' and VER_NBR=0
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='c1479830-593a-4040-8754-4feff9c0e17d'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('c1479830-593a-4040-8754-4feff9c0e17d', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='9fc2a7dd-215d-4a85-bcf5-a7a85cf01c0a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:38.307', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='9ece45f8-ae91-421a-8f4e-20f120ea6929', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='A study of the physical basis of sound, acoustical properties of sound, the human ear and voice, reproduction of sound, electronic music, acoustical properties of auditoriums, and other selected topics.', LUI_STATE='kuali.lui.course.offering.state.planned', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='PHYS102 CO', DESCR_PLAIN='A study of the physical basis of sound, acoustical properties of sound, the human ear and voice, reproduction of sound, electronic music, acoustical properties of auditoriums, and other selected topics.', REF_URL='null' where ID='704f2a5d-ac27-48b4-8200-8f15bc9d4763' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='5d68198e-a535-4574-8858-be4b066c67a0', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:38.307', LUI_CD='PHYS102', DIVISION='PHYS', IDENT_LEVEL='', LNG_NAME='Physics of Music', LUI_ID='704f2a5d-ac27-48b4-8200-8f15bc9d4763', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1cd5805b-eb7c-4066-94e2-9e65ed3191bc' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='980bc84a-85f8-401a-8abb-d7651a1334c3', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:38.307', DESCR_FORMATTED='', LUI_ID='704f2a5d-ac27-48b4-8200-8f15bc9d4763', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='4bf6b71e-e094-4047-8780-e29a6b1e823e' and VER_NBR=0
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='704f2a5d-ac27-48b4-8200-8f15bc9d4763'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('704f2a5d-ac27-48b4-8200-8f15bc9d4763', '9012742')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='704f2a5d-ac27-48b4-8200-8f15bc9d4763'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('704f2a5d-ac27-48b4-8200-8f15bc9d4763', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('704f2a5d-ac27-48b4-8200-8f15bc9d4763', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('704f2a5d-ac27-48b4-8200-8f15bc9d4763', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('704f2a5d-ac27-48b4-8200-8f15bc9d4763', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='e245b458-bb4f-4169-a5c5-d223e5a02cd0', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:38.307', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='52cf48ee-860e-4ed9-82af-6cf489cac69c', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=44, MIN_SEATS=44, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='39ecfe64-bdfa-4cac-9665-7be0cc719c01' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='de2019a0-2bdd-4bdb-afa5-e8099a8be81d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:38.307', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='39ecfe64-bdfa-4cac-9665-7be0cc719c01', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='45c890b1-6518-48f2-9823-7b00373d6aae' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='f4d0db38-0fa0-4c42-8176-6fd0da0c1282', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:38.307', DESCR_FORMATTED='', LUI_ID='39ecfe64-bdfa-4cac-9665-7be0cc719c01', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='f26ce4cb-5f83-49b5-bdf9-7f40152df512' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='e384c4a4-32a9-4d00-8b88-07a7cebd2f22', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:38.307', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='cfe13192-118b-471d-bd12-e91f7719e0d9', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture only', LUI_STATE='kuali.lui.format.offering.state.planned', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture Only', DESCR_PLAIN='Courses with Lecture only', REF_URL='' where ID='212fd781-6886-4400-9da9-3916f6bd36a9' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='7cebb422-114a-46f8-bcb9-016cc8659003', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:38.307', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture Only', LUI_ID='212fd781-6886-4400-9da9-3916f6bd36a9', ORGID='', SHRT_NAME='LEC', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='d3c4403f-aa7c-4a19-b3b7-ea939b52dbb8' and VER_NBR=0
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='212fd781-6886-4400-9da9-3916f6bd36a9'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('212fd781-6886-4400-9da9-3916f6bd36a9', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='66dcedd8-c1a5-438b-b975-4b2e4aa4560d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:38.307', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='98835c9e-79df-48e5-8372-c957497754ec', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='This is a course with a non-mathematical emphasis designed to study the basics of mechanical, electrical, and optical devices that are commonly found in the world around us.  The general approach would be to look inside things to observe how they work.', LUI_STATE='kuali.lui.course.offering.state.planned', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='PHYS104 CO', DESCR_PLAIN='This is a course with a non-mathematical emphasis designed to study the basics of mechanical, electrical, and optical devices that are commonly found in the world around us.  The general approach would be to look inside things to observe how they work.', REF_URL='null' where ID='3da1f033-df9b-4c9b-aaac-0525062012fd' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='821890f1-c9e4-476e-b83f-221ccf481737', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:38.307', LUI_CD='PHYS104', DIVISION='PHYS', IDENT_LEVEL='', LNG_NAME='How Things Work: Science Foundations', LUI_ID='3da1f033-df9b-4c9b-aaac-0525062012fd', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='6ac61322-e590-44f2-9db9-0166b744f889' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='80cb6f10-0531-4b25-b8b6-ce7431f8636c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:47:38.307', DESCR_FORMATTED='', LUI_ID='3da1f033-df9b-4c9b-aaac-0525062012fd', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='fc4e4ae4-12c9-4f69-bc15-b892b2b76705' and VER_NBR=0
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='3da1f033-df9b-4c9b-aaac-0525062012fd'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('3da1f033-df9b-4c9b-aaac-0525062012fd', '9012742')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='3da1f033-df9b-4c9b-aaac-0525062012fd'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('3da1f033-df9b-4c9b-aaac-0525062012fd', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('3da1f033-df9b-4c9b-aaac-0525062012fd', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('3da1f033-df9b-4c9b-aaac-0525062012fd', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('3da1f033-df9b-4c9b-aaac-0525062012fd', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='95d23b6e-6f61-4960-960b-d88ff421df17', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='5e58f009-b2dd-4770-ad41-b750dbdfe8b4', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=288, MIN_SEATS=288, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='c7db47af-65a5-4c58-a5d8-95deae522701' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='394153b5-b29a-4f56-9b8b-11d5b14d5fb9', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='c7db47af-65a5-4c58-a5d8-95deae522701', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='ac53eb83-a391-41e9-b116-481da2dc1b0f' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='3c98d5f1-05d2-486e-8c2f-568395a2cbbb', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='c7db47af-65a5-4c58-a5d8-95deae522701', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='39e2713e-b040-4ec8-994b-0b8b1cd97c39' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='d1bc521f-4f7c-44fc-9460-1041292ae20a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture/Lab', LUI_STATE='kuali.lui.format.offering.state.planned', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture/Lab', DESCR_PLAIN='Courses with Lecture/Lab', REF_URL='' where ID='49adf4bd-2a31-4054-879d-1f1a1abe3682' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='278d2019-6cb0-4b01-a461-7063520c5af8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture/Lab', LUI_ID='49adf4bd-2a31-4054-879d-1f1a1abe3682', ORGID='', SHRT_NAME='LEC/LAB', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='21d0795e-e140-4280-91ff-8ca0eca5bdce' and VER_NBR=0
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='49adf4bd-2a31-4054-879d-1f1a1abe3682'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('49adf4bd-2a31-4054-879d-1f1a1abe3682', 'kuali.lui.type.activity.offering.lab')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('49adf4bd-2a31-4054-879d-1f1a1abe3682', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='42af519d-6bb1-4570-b410-44de217be873', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='CLUID-BSCI330-200708000000', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='Biochemical and physiological mechanisms underlying cellular function. Properties of cells which make life possible and mechanisms by which cells provide energy, reproduce, and regulate and integrate with each other and their environment.', LUI_STATE='kuali.lui.course.offering.state.planned', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='BSCI330 CO', DESCR_PLAIN='Biochemical and physiological mechanisms underlying cellular function. Properties of cells which make life possible and mechanisms by which cells provide energy, reproduce, and regulate and integrate with each other and their environment.', REF_URL='null' where ID='18cccabd-f5bf-4ac7-8367-e9031640a107' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='a5c579d3-e42a-4996-8b69-4260ac6cf8d6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='BSCI330', DIVISION='BSCI', IDENT_LEVEL='', LNG_NAME='Cell Biology and Physiology', LUI_ID='18cccabd-f5bf-4ac7-8367-e9031640a107', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b406f250-85d2-414b-8027-76a2fb44f7a8' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='37d301bd-84d9-4872-bc4a-ca339f9961c9', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='18cccabd-f5bf-4ac7-8367-e9031640a107', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='c105bbdf-502e-40d6-bdf3-e7c08ba44925' and VER_NBR=0
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='18cccabd-f5bf-4ac7-8367-e9031640a107'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('18cccabd-f5bf-4ac7-8367-e9031640a107', '576639460')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='18cccabd-f5bf-4ac7-8367-e9031640a107'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('18cccabd-f5bf-4ac7-8367-e9031640a107', 'kuali.creditType.credit.degree.4.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('18cccabd-f5bf-4ac7-8367-e9031640a107', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('18cccabd-f5bf-4ac7-8367-e9031640a107', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('18cccabd-f5bf-4ac7-8367-e9031640a107', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='a749bff6-ac08-447d-92ef-1ed723dcf90e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='3d77a421-8c25-4609-86d5-1dc5e5f3ee5a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='03b568fc-aeed-4963-bfc9-4f6382d25dec' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='bc9da23d-b279-461b-a1f8-8c8c2a7c3330', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='03b568fc-aeed-4963-bfc9-4f6382d25dec', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='3e55049e-f936-4fd9-a198-9f9e76882962' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='de21fb10-0d84-41a6-8d57-a142bb4e7f31', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='03b568fc-aeed-4963-bfc9-4f6382d25dec', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b48547fb-07bd-4f8a-8fd2-50f1b65a55d4' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='c197a64f-11e9-44b4-b044-085f80050415', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='00b2aaf3-5138-4066-a4ed-0d1d78c47f71', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='980c2079-cf32-4e87-b17d-b826bff9aacd' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='9b0113ac-d6b1-4fd0-9d86-46a0c731b2a9', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='C', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='980c2079-cf32-4e87-b17d-b826bff9aacd', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='7ba0e634-847c-4876-977a-2f8d6c0fd5e4' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='293afcf9-4763-40bb-a9f0-76099c9eee34', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='980c2079-cf32-4e87-b17d-b826bff9aacd', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='64673e45-9a89-43ac-9bd4-21ee93d33762' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='84257620-3040-450b-95b9-2da3bf43cd9b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='b425bf1d-87a1-41ff-a84c-f8a2d86e5ecf', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='d6e8f5d5-3b33-4155-8ca0-78c6f983d3f5' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='7cd4b3d7-fb14-4975-a968-a6f4b74ebb41', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='D', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='d6e8f5d5-3b33-4155-8ca0-78c6f983d3f5', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8ca01d60-c00b-48bb-b5f7-44f2b0bcad35' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='dcb95a08-928e-4fb6-9e00-f09ca8ec95f7', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='d6e8f5d5-3b33-4155-8ca0-78c6f983d3f5', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='d75ca3de-2fdd-4072-b2e5-4cd366b63016' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='25648dde-a008-4d84-bb3f-3934e62431db', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='2df91c85-1d89-4796-95ec-e491454f35c3', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='7ba06dd6-9bc7-4c01-a970-b9b7b381b4d1' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='e84040a5-65fd-4773-ab56-e9dd9f498854', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='E', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='7ba06dd6-9bc7-4c01-a970-b9b7b381b4d1', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='3cf89feb-faae-4bfe-b078-3203035ebc36' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='65ec81af-1602-4374-ab17-54bb1fd33b3a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='7ba06dd6-9bc7-4c01-a970-b9b7b381b4d1', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='681eb423-7af9-4324-be3f-bb2634caa724' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='e436ac6e-e8f3-46e1-8bf9-53be4d0a2cc1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='d354a5c4-5e0c-4a42-981f-bb00109e165f', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='0e30e2b5-3461-452d-831e-8180c3c158d6' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='b592f1c7-30f5-458e-9025-b8c58e2da494', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='F', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='0e30e2b5-3461-452d-831e-8180c3c158d6', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8fa15522-1fae-490a-ac4a-f03f293d33f3' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='bffaa670-7b6e-44ec-9ab8-f3aa19f2b4d4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='0e30e2b5-3461-452d-831e-8180c3c158d6', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='60d1777f-4a89-48d0-9532-9be150915696' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='25c196be-cdbc-4d3e-8d68-d2b12bf1c383', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='4bdec9e4-f92c-4130-befc-9a5672355c0b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='51ad321e-0d2e-40e4-959d-87ff6d1e8215' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='13bdcf1a-5ecb-4c31-b82f-56187199f687', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='G', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='51ad321e-0d2e-40e4-959d-87ff6d1e8215', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8990602f-fe39-45e7-be9b-bd0f8576d9ae' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='ee673a6b-b0a2-414a-a212-cdf4005a27ae', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='51ad321e-0d2e-40e4-959d-87ff6d1e8215', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='d601bf44-451a-4619-b8f7-fb6c2b0daad0' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='5fcb4c5e-0192-4792-9e0d-bcb57e0beda6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='495b649b-0671-4e0f-9ec2-7adcdaa435e6', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='1f9559e1-30b5-4a99-ac81-ce6d7de4871a' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='f3811b61-f4b5-4860-97b5-7d1f0726f464', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='H', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='1f9559e1-30b5-4a99-ac81-ce6d7de4871a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='893da071-39ae-4d85-8754-24ad8e7a8665' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='0951c371-2aac-495b-8e7a-11dc79307902', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='1f9559e1-30b5-4a99-ac81-ce6d7de4871a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='8a53e9bb-272c-4963-884c-aee542ae362e' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='577996b2-55a0-4141-a2b9-94270cc1c2ef', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='4156f3f4-2fec-427c-9643-f5601a4c0d0a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='25719896-416f-41b0-91cf-f7dced522b4c' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='2054fcce-d7e8-4ef8-8803-e3f89ad29e7d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='I', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='25719896-416f-41b0-91cf-f7dced522b4c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='67f574fb-a84d-453f-93c0-e8019a4656d4' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='8157058f-0c39-41f4-90e4-77f601c159b1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='25719896-416f-41b0-91cf-f7dced522b4c', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='5550d375-d004-459a-913b-56fabca332ae' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='3b177dd7-dac0-4c4e-9082-5aaf383ed35e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='53b1ee34-2625-48cb-b183-c9b8893ac132', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='2d8c513f-6273-4a98-9cbd-5519c1857804' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='c1a9bb61-72e2-4ec9-8945-b2f31fdd9501', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='J', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='2d8c513f-6273-4a98-9cbd-5519c1857804', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='2e493156-63d6-4b82-8d80-79e14f380a32' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='0575eda0-d38c-48b3-9c26-fbbd2a774f92', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='2d8c513f-6273-4a98-9cbd-5519c1857804', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='432a600d-0410-4760-8a40-799d8fe20a6a' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='7576fe12-0bb8-4ea0-a431-25c479c7438f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='7f44062b-35d1-403f-ab5e-45edca85c4db', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='188f9ca9-ecbc-45a7-a2ea-95962bb9951b' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='e135cd12-32b7-4bca-a706-2928bca72c90', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='K', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='188f9ca9-ecbc-45a7-a2ea-95962bb9951b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='4d78cf26-3c69-4b1a-af77-e4c152164ccd' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='4de57da3-eda8-4f97-8f43-b0b74a2646af', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='188f9ca9-ecbc-45a7-a2ea-95962bb9951b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='26d93a3a-1198-4ba0-baec-87c8b75be3e5' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='0c0cb5a5-a94f-487b-8408-2a13564cff08', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='dbe4aeed-3eec-4369-8faf-202c25c8cd1c', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='126b8493-6398-48f9-9435-8438eb096a8c' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='3ca45e5d-e23c-4768-9bdc-f7454cee16ff', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='L', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='126b8493-6398-48f9-9435-8438eb096a8c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='c2e8d5a0-91ba-4abc-8c53-f901d3a82a7a' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='5a15076e-d8e3-4c3b-b9fa-2c85efaf9506', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='126b8493-6398-48f9-9435-8438eb096a8c', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b944f453-6bb8-4d4b-b2bf-10b9e8c0e02d' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='26a0b280-54ec-4737-beb2-9f67e943a414', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='70bbae2d-4ac6-4842-94ae-61ceea55cb0a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='22d6029e-55b6-4be8-85db-e44fda078c14' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='93c26eba-8a8d-438f-9e1e-706bddaf4a0b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='M', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='22d6029e-55b6-4be8-85db-e44fda078c14', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='e3f6c070-239f-4e9a-9e73-325e87968666' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='3fd4fc60-5e10-4fce-be2e-0a2930bd1a0a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='22d6029e-55b6-4be8-85db-e44fda078c14', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='0ed77982-7e52-4c79-8124-76dc5bab3d5d' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='834808fd-bf0a-468e-8421-1c0a87bea44a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='7f9a588a-faaa-44fe-92c1-41ecbc0fcea3', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=240, MIN_SEATS=240, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='463279c1-d545-4743-b72e-c8d84bac1dbe' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='26967026-c0c7-4937-aa33-766c0f358731', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='N', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='463279c1-d545-4743-b72e-c8d84bac1dbe', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='c219920c-cce7-4e00-9426-7fdb12f517c3' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='ba29ac2c-416e-4afd-aa2b-9297112a4003', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='463279c1-d545-4743-b72e-c8d84bac1dbe', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='1486d30e-55e8-456e-b897-ffae38ca5d6a' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='15ec6f05-c1bc-4497-aaa5-36f5e2ac0efd', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='09415b65-dae3-45c0-9829-24eed04f9dd1', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='2b13dab0-5395-4acb-b465-bdd0eb1e191b' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='4ac80643-4a6e-49dd-8d4c-d2f22065d91d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='O', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='2b13dab0-5395-4acb-b465-bdd0eb1e191b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='73b3208c-6fa5-49d0-8686-6aeb96f26248' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='e88d395c-42f5-472c-bbe1-f915c758735d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='2b13dab0-5395-4acb-b465-bdd0eb1e191b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='13dcad5b-8788-4e46-a220-f3f58247af65' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='f9994839-aad3-43cf-b8a9-e4bd23c0a925', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='3bac0164-0547-4b66-898b-58405dc8898f', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='f03379f2-4435-4a27-b21e-8f45e130c97e' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='dc38725a-cca8-4061-a926-e2e52e06b8a3', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='P', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='f03379f2-4435-4a27-b21e-8f45e130c97e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='95b89e76-4528-4661-b8c4-f14a16660d63' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='2fc0f1dc-7f16-4643-a0f5-39ea84ae08f4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='f03379f2-4435-4a27-b21e-8f45e130c97e', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='e1fe39ef-0f30-438e-b3ee-1f8beab5ef50' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='872fd732-fe85-43fa-b2b3-dae3cb11da2e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='a89ef48f-7d10-4c62-bd31-7ead4247b18b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='beed8739-feb4-412e-ab55-6c6452f0e0e4' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='1cc6b258-b64a-4f1f-b5dd-79da4810780e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='Q', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='beed8739-feb4-412e-ab55-6c6452f0e0e4', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='3368dec8-2657-41c0-8c3d-005fd4a2d9d5' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='19171b22-e777-464d-9ceb-62a93e85618f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='beed8739-feb4-412e-ab55-6c6452f0e0e4', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='238b65ed-57df-4842-a814-93ee90d0a605' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='4e52735c-ae8a-4a9f-899f-ef9a8c04fdca', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='7d91a6f7-ee9a-4d1f-b8d7-134066111f53', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='8099d225-47fd-4968-b308-76b53f6f95d8' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='24964bce-a478-44da-b99a-1f8440a376b4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='R', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='8099d225-47fd-4968-b308-76b53f6f95d8', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='9b24d147-078d-48a6-a00d-a1e59e153684' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='53924bd7-fd0b-4d05-9bfa-6792d4bd21ff', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='8099d225-47fd-4968-b308-76b53f6f95d8', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='5edf03b2-4f72-46af-9c83-83ea8e4c388d' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='223ca72d-2364-43c9-b5b2-d205cd1f80ec', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='2484136e-b057-4450-ad0a-8fa2eed95a2b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='9ac86f1a-3016-41fe-bdd1-7e4f87dadc6d' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='879154d9-3cde-46aa-ae14-5cc93ccd5ae7', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='S', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='9ac86f1a-3016-41fe-bdd1-7e4f87dadc6d', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='3b309861-03ed-4d80-bac1-49ef5232d905' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='fd9afdcd-43a7-4436-816f-7759483c976d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='9ac86f1a-3016-41fe-bdd1-7e4f87dadc6d', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='7d11e3b0-73f8-4c80-a35f-9e1acd1579e6' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='ea1ab808-c68a-4c50-b9f4-6a23b6f724fe', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='cbf096d5-a82f-4965-aceb-5e80a89ad9c9', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='85bf9f49-6a6f-4d5a-8f4d-33fe8c6aabe4' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='dc61956c-0e96-47d3-8feb-dab28ed3dc11', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='T', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='85bf9f49-6a6f-4d5a-8f4d-33fe8c6aabe4', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='70aabbec-c3aa-49bb-8832-f6711276380d' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='99b3fe6b-3f73-439b-b535-33e2040b3d5f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='85bf9f49-6a6f-4d5a-8f4d-33fe8c6aabe4', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='7809eca1-5913-4be0-835f-132a83d53cb2' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='800e14e5-2572-4ad9-913b-34584c270912', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='107dded6-6597-4211-b228-7c66c8414616', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='d41a71d8-ac39-4a4f-92cf-cbb4433234ee' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='1a7c7d4b-4599-4a73-97f8-5dbd9c5234e2', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='U', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='d41a71d8-ac39-4a4f-92cf-cbb4433234ee', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='c75a7746-5a87-466b-bf69-07f3e40f46c7' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='c1d6ef73-cd3d-4ae3-b99d-41dc2e70a07c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='d41a71d8-ac39-4a4f-92cf-cbb4433234ee', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='c73d05cf-3342-4684-acfe-254dca6f2eff' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='5ccc71bc-2baf-42cb-bf12-270a5f9cc2e1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='fd93d6b6-1a66-4cae-bcb2-23b0316a4870', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='e3c7aa5b-02c3-4416-b8c3-a069e9bf587b' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='4974b1e5-67c2-4acb-b593-0cdc5ff6a6a8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='V', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='e3c7aa5b-02c3-4416-b8c3-a069e9bf587b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='40ac6dbc-cf15-4cf7-b516-c9a2e90fb9cb' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='daf52a19-0801-4ed5-8240-c1da0a4b1b1a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='e3c7aa5b-02c3-4416-b8c3-a069e9bf587b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='cb9ed1d4-2da6-4c40-bd80-d12efd66e3e6' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='d48d2e23-7404-4d10-ac98-62012f3cbb3b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='8070ba81-9e12-46ef-a1cc-7c55b4b9052f', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='ba2bf7e8-0e81-461e-aa8f-10b70e9cbe21' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='d9533b2a-0696-4c00-bc2c-c964532fd8b7', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='W', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='ba2bf7e8-0e81-461e-aa8f-10b70e9cbe21', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8ffb2773-5a0e-43a9-8dd5-6c96f7173c1e' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='2babf542-329c-417f-acd1-dfeef0fb329d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='ba2bf7e8-0e81-461e-aa8f-10b70e9cbe21', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='23c84d4f-ea3d-48c4-9920-64383d0aedd9' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='674f8b93-e583-4a5a-8995-dd5da6d7eac5', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='f54be7b2-70cc-46c0-8204-7963b66d4af9', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='fc28b776-c0a9-4279-8429-6852f7081e5e' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='96814a6d-955a-4c0b-a458-bc01f5d5c445', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='X', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='fc28b776-c0a9-4279-8429-6852f7081e5e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='35516e9e-0bbb-4e6c-85c4-9d61a4436d37' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='6b293b92-bc9f-4c0d-940d-58e6c50f408e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='fc28b776-c0a9-4279-8429-6852f7081e5e', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='f651faa4-32ea-424a-af5e-e51017a53774' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='b951ac81-0d30-40be-b13d-12047b922f2a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='69863a58-c29e-4b1b-a2db-cdbe35597fcf', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=24, MIN_SEATS=24, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='502e1be7-57db-4218-a293-1a3e23748147' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='a1b68b1d-c5e4-48e4-9389-42d28c6c6860', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='Y', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='502e1be7-57db-4218-a293-1a3e23748147', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='5dacf6db-0e57-4785-a7bb-eaf759002f2b' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='63edd447-a856-4bd9-89d0-7308899f963d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='502e1be7-57db-4218-a293-1a3e23748147', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='8a69bae9-be41-4308-9b5b-1361c37e91bd' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='d7c9c004-06f2-45d4-ae15-af309ce43ac2', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='02b2af95-54f8-4d0a-9ec8-4d7fb31c36e1', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='0fb3f43b-5386-4656-a2e0-9ee48c187df6' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='10944cc2-9abe-4905-8fe3-0596377f6bd6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='Z', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='0fb3f43b-5386-4656-a2e0-9ee48c187df6', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='319f7cfa-82f5-48bd-b51f-69d58044ab8c' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='640dbaea-c716-43a8-ae21-b0602ed86b05', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='0fb3f43b-5386-4656-a2e0-9ee48c187df6', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='10d1e1c0-a0bd-4766-b341-01e0a0e36817' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='04cac046-af3a-410d-a3af-d16d03ff5016', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='5ab3ab70-84fd-4673-9ac6-49090dd24ca4', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=83, MIN_SEATS=83, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='be93f2be-e2ed-46e5-be65-fcbdec11b1e3' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='dd388fe8-2e11-45ef-ab7c-86a96d0ef2a4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='be93f2be-e2ed-46e5-be65-fcbdec11b1e3', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='7e68bcae-f71d-4ccd-a3e4-033424b64bfc' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='70c5e4a3-1e59-41d6-8862-ac2be5b4f53e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='be93f2be-e2ed-46e5-be65-fcbdec11b1e3', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='967316de-5492-4e24-bd0d-747646df5d89' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='40578186-64a5-4831-846c-bfe84f72cfdf', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='a6a3673f-5d87-4e8d-8be6-659b6cb99189', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture only', LUI_STATE='kuali.lui.format.offering.state.planned', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture Only', DESCR_PLAIN='Courses with Lecture only', REF_URL='' where ID='b665c20c-6cdc-4b24-8402-f0123b3c050a' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='9a7b4760-be34-4b85-b026-22e16052350e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture Only', LUI_ID='b665c20c-6cdc-4b24-8402-f0123b3c050a', ORGID='', SHRT_NAME='LEC', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='c07e7442-21a3-44e5-acbe-35abb95c4e5a' and VER_NBR=0
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='b665c20c-6cdc-4b24-8402-f0123b3c050a'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('b665c20c-6cdc-4b24-8402-f0123b3c050a', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='d4b6c86d-7235-4515-8877-8b42519487c5', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='CLUID-BSCI373-199908000000', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='Consideration of the major groups of organisms associated with the Chesapeake Bay and current issues that determine humans'' present and future uses for the Chesapeake and its biota.', LUI_STATE='kuali.lui.course.offering.state.planned', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='BSCI373 CO', DESCR_PLAIN='Consideration of the major groups of organisms associated with the Chesapeake Bay and current issues that determine humans'' present and future uses for the Chesapeake and its biota.', REF_URL='null' where ID='4df0949c-703e-4206-8961-055358a21dac' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='a61d1a0d-cfd7-4527-9f8f-c10b2d6b9c8b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', LUI_CD='BSCI373', DIVISION='BSCI', IDENT_LEVEL='', LNG_NAME='Natural History of the Chesapeake Bay', LUI_ID='4df0949c-703e-4206-8961-055358a21dac', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b4360b13-ec50-4078-beb8-08ce30ca304d' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='7c0e5d02-60ed-4ddf-9306-5f0da3d76d62', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:48:33.373', DESCR_FORMATTED='', LUI_ID='4df0949c-703e-4206-8961-055358a21dac', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='5d19cdd0-d895-4ec3-b89a-a4d8f2052487' and VER_NBR=0
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='4df0949c-703e-4206-8961-055358a21dac'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('4df0949c-703e-4206-8961-055358a21dac', '576639460')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='4df0949c-703e-4206-8961-055358a21dac'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4df0949c-703e-4206-8961-055358a21dac', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4df0949c-703e-4206-8961-055358a21dac', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4df0949c-703e-4206-8961-055358a21dac', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4df0949c-703e-4206-8961-055358a21dac', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='672cb21f-f9c7-4f5b-bae1-5f4217193140', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:49:26.192', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='d46ad097-966a-4117-b020-a99576af1e10', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=150, MIN_SEATS=150, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='4fbcfdd4-dea9-4344-98c9-a8b9c3bf2a6e' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='af58755b-5b5c-4aa9-882d-309b00490f82', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:49:26.192', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='4fbcfdd4-dea9-4344-98c9-a8b9c3bf2a6e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='65365328-8226-4488-a124-b5fba80334ee' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='26d1c892-1835-4505-aefe-ab1d5cefae86', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:49:26.192', DESCR_FORMATTED='', LUI_ID='4fbcfdd4-dea9-4344-98c9-a8b9c3bf2a6e', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='38f7764c-b002-4cf4-b2cb-01628fa25da6' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='7d599636-67a6-4834-80c4-29ecb4964721', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:49:26.192', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='394eb670-fba5-4c80-9017-bd533d73dda2', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture only', LUI_STATE='kuali.lui.format.offering.state.planned', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture Only', DESCR_PLAIN='Courses with Lecture only', REF_URL='' where ID='db443fb1-f8ee-4794-a1f5-a9707e30b479' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='49383bb4-8037-4a12-8492-539aea78a6e8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:49:26.192', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture Only', LUI_ID='db443fb1-f8ee-4794-a1f5-a9707e30b479', ORGID='', SHRT_NAME='LEC', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='dd280056-f9a9-4b83-9706-30b665cf4640' and VER_NBR=0
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='db443fb1-f8ee-4794-a1f5-a9707e30b479'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('db443fb1-f8ee-4794-a1f5-a9707e30b479', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='55ed5b55-61ba-477e-9017-0edce60f84f1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:49:26.192', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='8597c766-5cf9-4223-90bd-0c18d697ec28', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='A study of the biology, life span development, socialization, personality, mental health, and special issues of women.', LUI_STATE='kuali.lui.course.offering.state.planned', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='WMST336 CO', DESCR_PLAIN='A study of the biology, life span development, socialization, personality, mental health, and special issues of women.', REF_URL='null' where ID='d9ffe43c-6771-41bd-bf16-a41cac9c01b0' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='6440f1a8-87df-4e33-a748-8e4b91903812', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:49:26.192', LUI_CD='WMST336', DIVISION='WMST', IDENT_LEVEL='', LNG_NAME='Psychology of Women', LUI_ID='d9ffe43c-6771-41bd-bf16-a41cac9c01b0', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='d4e412fd-e08d-479f-9f02-d33761d61fd1' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='2df24b52-f9eb-4d51-be45-40b8756ca502', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:49:26.192', DESCR_FORMATTED='', LUI_ID='d9ffe43c-6771-41bd-bf16-a41cac9c01b0', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='ab225464-f02e-4d30-ab1d-d9994d20f271' and VER_NBR=0
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='d9ffe43c-6771-41bd-bf16-a41cac9c01b0'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('d9ffe43c-6771-41bd-bf16-a41cac9c01b0', '4014660630')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='d9ffe43c-6771-41bd-bf16-a41cac9c01b0'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('d9ffe43c-6771-41bd-bf16-a41cac9c01b0', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('d9ffe43c-6771-41bd-bf16-a41cac9c01b0', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('d9ffe43c-6771-41bd-bf16-a41cac9c01b0', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('d9ffe43c-6771-41bd-bf16-a41cac9c01b0', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='447bee9e-3990-4fc5-a79e-5df7b4290830', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:49:26.192', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='12a0a27c-a467-482f-8ccf-fccb66fbddb9', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=35, MIN_SEATS=35, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='73c82da9-c4a1-411b-b765-b34d5c13bca6' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='d6b67545-dcb5-4fc6-b2a9-0fce709de646', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:49:26.192', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='73c82da9-c4a1-411b-b765-b34d5c13bca6', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='3feb2dc2-2f3e-4954-b938-bb4c408d83f7' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='c770c638-9af2-4b39-bbbf-139c74d4cfa7', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:49:26.192', DESCR_FORMATTED='', LUI_ID='73c82da9-c4a1-411b-b765-b34d5c13bca6', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='19358090-0881-4c43-b4c8-e0edc1ae89dd' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='9e95e8c1-bf3a-4ca3-a6fe-92116fc1e8dd', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:49:26.192', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='8c293c08-3cd8-48d7-b65b-71073f10153b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture only', LUI_STATE='kuali.lui.format.offering.state.planned', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture Only', DESCR_PLAIN='Courses with Lecture only', REF_URL='' where ID='591d7a10-ad5d-400c-8bad-e79019aece92' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='8134f55a-1c21-4c9c-b299-99ae2e6728a6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:49:26.192', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture Only', LUI_ID='591d7a10-ad5d-400c-8bad-e79019aece92', ORGID='', SHRT_NAME='LEC', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='50d60b68-ef32-4a9b-a8c8-a9e34989715d' and VER_NBR=0
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='591d7a10-ad5d-400c-8bad-e79019aece92'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('591d7a10-ad5d-400c-8bad-e79019aece92', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='90dd3a9c-56da-4ed3-9780-52716f25ebe5', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:49:26.192', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='CLUID-WMST452-199501000000', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='Participation and portrayal of women in the mass media from colonial to contemporary times.', LUI_STATE='kuali.lui.course.offering.state.planned', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='WMST452 CO', DESCR_PLAIN='Participation and portrayal of women in the mass media from colonial to contemporary times.', REF_URL='null' where ID='92b5368e-1220-43d1-8e00-4afba2299e53' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='6dd2cd29-732b-4ed0-aad7-582f959b4ab1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:49:26.192', LUI_CD='WMST452', DIVISION='WMST', IDENT_LEVEL='', LNG_NAME='Women in the Media', LUI_ID='92b5368e-1220-43d1-8e00-4afba2299e53', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='db4e0c11-bffe-4a0b-9ca4-47a63ce24c24' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='9aa7bbe3-3fc8-4950-b548-55659fcc93b8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:49:26.192', DESCR_FORMATTED='', LUI_ID='92b5368e-1220-43d1-8e00-4afba2299e53', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='0b80a9bd-7281-4abb-9e49-72c5621656d5' and VER_NBR=0
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='92b5368e-1220-43d1-8e00-4afba2299e53'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('92b5368e-1220-43d1-8e00-4afba2299e53', '4014660630')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='92b5368e-1220-43d1-8e00-4afba2299e53'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('92b5368e-1220-43d1-8e00-4afba2299e53', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('92b5368e-1220-43d1-8e00-4afba2299e53', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('92b5368e-1220-43d1-8e00-4afba2299e53', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('92b5368e-1220-43d1-8e00-4afba2299e53', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('afb7bd79-2f24-4a9b-b07d-193f33ae1419', 'kuali.soc.state.locked', 'bc662f64-5078-431a-bc66-e4e586ef38f3', '2013-06-04T14:50:08-0400', 'f5816d7f-7456-46d0-9ca7-e0a7e1a57659')
/
update KSEN_SOC set OBJ_ID='5e67a0c4-e1e7-41d2-bf8e-3fd5dd00729e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:08.338', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.locked', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2012CETerm1', UNITS_CONTENT_OWNER_ID='' where ID='bc662f64-5078-431a-bc66-e4e586ef38f3' and VER_NBR=0
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('12406a50-6a6c-4902-8e1e-8ef584aca5c8', 'kuali.soc.scheduling.state.inprogress', 'bc662f64-5078-431a-bc66-e4e586ef38f3', '2013-06-04T14:50:18-0400', 'ff8289c5-3d4d-4807-8557-2832f9e7a624')
/
update KSEN_SOC set OBJ_ID='5e67a0c4-e1e7-41d2-bf8e-3fd5dd00729e', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.locked', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2012CETerm1', UNITS_CONTENT_OWNER_ID='' where ID='bc662f64-5078-431a-bc66-e4e586ef38f3' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('434e71f7-97be-44c5-a7d7-1ffecafc1814', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '9e9151eb-5121-4817-8b87-5239fe488efc')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('7d457bae-1ed5-4fcb-b1d0-32fd9d08b012', '0', '6c3e8fdc-f51b-40c5-b360-2e2e20f86084', '9e9151eb-5121-4817-8b87-5239fe488efc', 'b22ecbed-b55c-4b23-ac96-5ba27de0a51b')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='6d4ebf5b-f32f-4bd3-9be3-158b2aa5dc12' where ID='2aa3cfa9-ea14-447c-a661-5c71568719e6'
/
update KSEN_SCHED_RQST set OBJ_ID='f68567ca-0309-4591-a694-a18b80ac1531', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI373 - A', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='9e9151eb-5121-4817-8b87-5239fe488efc', SCHED_RQST_SET_ID='cc8bbf21-9e93-4b83-bbd6-be966b4673fe' where ID='6d4ebf5b-f32f-4bd3-9be3-158b2aa5dc12' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='2aa3cfa9-ea14-447c-a661-5c71568719e6'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('2aa3cfa9-ea14-447c-a661-5c71568719e6', 'a8639d8c-f9cf-45d6-b7cd-48208717e1b6')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='2aa3cfa9-ea14-447c-a661-5c71568719e6'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('2aa3cfa9-ea14-447c-a661-5c71568719e6', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='2aa3cfa9-ea14-447c-a661-5c71568719e6'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('2aa3cfa9-ea14-447c-a661-5c71568719e6', '6c3e8fdc-f51b-40c5-b360-2e2e20f86084')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='2aa3cfa9-ea14-447c-a661-5c71568719e6'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('2aa3cfa9-ea14-447c-a661-5c71568719e6', 'ecb9dfee-681f-42da-a0ca-2070f4649f69')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('b22ecbed-b55c-4b23-ac96-5ba27de0a51b', 'ecb9dfee-681f-42da-a0ca-2070f4649f69')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('2f131b02-97bd-44e5-acbd-a763cfa4f7ee', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'A', '', '', '', 'be93f2be-e2ed-46e5-be65-fcbdec11b1e3', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '1815d73a-bebb-4a29-9c72-0537c5c94c27')
/
update KSEN_LUI_ATTR set OBJ_ID='5c553e79-cfa9-491b-a813-830df9bc4ac6', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='be93f2be-e2ed-46e5-be65-fcbdec11b1e3', ATTR_VALUE='0' where ID='3a4c9ccd-dc51-49b2-b0be-de194e4075ea'
/
update KSEN_LUI set OBJ_ID='04cac046-af3a-410d-a3af-d16d03ff5016', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='5ab3ab70-84fd-4673-9ac6-49090dd24ca4', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=83, MIN_SEATS=83, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='be93f2be-e2ed-46e5-be65-fcbdec11b1e3' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='69362f2a-a98f-42b8-acf5-3df0676212db', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='be93f2be-e2ed-46e5-be65-fcbdec11b1e3', ATTR_VALUE='0' where ID='d9f1629b-fdec-4f16-bcd9-a39d4be3b9db'
/
update KSEN_LUI_IDENT set OBJ_ID='2f131b02-97bd-44e5-acbd-a763cfa4f7ee', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='be93f2be-e2ed-46e5-be65-fcbdec11b1e3', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1815d73a-bebb-4a29-9c72-0537c5c94c27' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='70c5e4a3-1e59-41d6-8862-ac2be5b4f53e', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='be93f2be-e2ed-46e5-be65-fcbdec11b1e3', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='967316de-5492-4e24-bd0d-747646df5d89' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='3276359c-e41d-4807-8224-be89c6bc0e09', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:50:20.614', EXPIR_DT='', LUI_ID='be93f2be-e2ed-46e5-be65-fcbdec11b1e3', PERS_ID='P.LEANNM', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='ad55d6c5-5a52-4b99-96c6-9c1c4291d56e' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('be93f2be-e2ed-46e5-be65-fcbdec11b1e3', '9e9151eb-5121-4817-8b87-5239fe488efc')
/
delete from KSEN_LUI_IDENT where ID='7e68bcae-f71d-4ccd-a3e4-033424b64bfc' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('1a1fda56-454f-4f76-8150-b38fb76acb55', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '98bbf055-26f4-4606-b753-ede9f907980a')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('aa10ef21-cd96-45bf-8f2b-c90aa986636e', '0', '75a3451c-6295-4fce-8b95-f60c9f4e4102', '98bbf055-26f4-4606-b753-ede9f907980a', '634e75e7-bfa6-4594-ba0f-ec301b8ca9c0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='aac878b2-9da5-4142-a81e-aae8f3f693b9' where ID='5a96f29e-1fc7-4946-a25c-c1beabf3db3f'
/
update KSEN_SCHED_RQST set OBJ_ID='c0249405-375e-47bc-b8ec-cde413b4b7c8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for PHYS102 - A', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='98bbf055-26f4-4606-b753-ede9f907980a', SCHED_RQST_SET_ID='9e7d4215-3ad1-4aca-a79c-99bde36aca68' where ID='aac878b2-9da5-4142-a81e-aae8f3f693b9' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='5a96f29e-1fc7-4946-a25c-c1beabf3db3f'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('5a96f29e-1fc7-4946-a25c-c1beabf3db3f', '5e11048b-5d59-4ede-b9eb-54a7dc4dd1a3')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='5a96f29e-1fc7-4946-a25c-c1beabf3db3f'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('5a96f29e-1fc7-4946-a25c-c1beabf3db3f', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='5a96f29e-1fc7-4946-a25c-c1beabf3db3f'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('5a96f29e-1fc7-4946-a25c-c1beabf3db3f', '75a3451c-6295-4fce-8b95-f60c9f4e4102')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='5a96f29e-1fc7-4946-a25c-c1beabf3db3f'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('5a96f29e-1fc7-4946-a25c-c1beabf3db3f', 'f417cb06-9ea4-4ccf-8964-838615e05334')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('634e75e7-bfa6-4594-ba0f-ec301b8ca9c0', 'f417cb06-9ea4-4ccf-8964-838615e05334')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c013366e-a184-4358-b22b-3c8bd11c6ec4', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'A', '', '', '', '7d711c28-50cc-41a0-81c0-f87443686372', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '73a9578d-a0ae-4649-9e2c-7fa69b3f69ca')
/
update KSEN_LUI_ATTR set OBJ_ID='6b9287f9-ae9b-4454-ade7-8757cf8ba86f', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='7d711c28-50cc-41a0-81c0-f87443686372', ATTR_VALUE='0' where ID='f5553929-cf88-42be-afc1-fad6017c23a8'
/
update KSEN_LUI set OBJ_ID='01326ed6-5873-4fb6-8fa7-8625c59413f4', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='580ebfa2-f873-4dfa-b803-f176b08c1669', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=150, MIN_SEATS=150, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='7d711c28-50cc-41a0-81c0-f87443686372' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='bfade492-3394-4474-82ff-eaeaa4de4d63', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='7d711c28-50cc-41a0-81c0-f87443686372', ATTR_VALUE='0' where ID='fe17d83d-b0f1-4d2e-90a0-6d6b4d5c24e6'
/
update KSEN_LUI_IDENT set OBJ_ID='c013366e-a184-4358-b22b-3c8bd11c6ec4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='7d711c28-50cc-41a0-81c0-f87443686372', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='73a9578d-a0ae-4649-9e2c-7fa69b3f69ca' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='b8b1ad6a-7833-4939-a3c9-03145acd5812', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='7d711c28-50cc-41a0-81c0-f87443686372', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='9c2361c6-fbf4-48d2-960e-70bc2d9dc4a3' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='bf4a2845-db16-4460-8a8c-cf2dd462d80a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:50:22.279', EXPIR_DT='', LUI_ID='7d711c28-50cc-41a0-81c0-f87443686372', PERS_ID='L.MATTHEWB', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='b4c05e92-82fd-4fbe-a5e0-e3d4be4fe12f' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('7d711c28-50cc-41a0-81c0-f87443686372', '98bbf055-26f4-4606-b753-ede9f907980a')
/
delete from KSEN_LUI_IDENT where ID='57171047-8aad-4656-a4dc-ed2fef22fb60' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('c1421279-7729-44cc-bf3c-8149470b7cce', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '3ca15766-1f96-4fcf-8118-1cb124027bce')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('5bb35703-0ef8-4a7c-aba8-aeb206134ba6', '0', '1b0b0d73-c24f-4d52-8532-42023a7f7996', '3ca15766-1f96-4fcf-8118-1cb124027bce', 'db8f7a1a-f7f5-4e2b-ba18-6b04cc13f3d3')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='b6040af6-3300-4c60-a2b1-e9c50e45aaf2' where ID='40094a4b-08ca-4a78-ab2d-66f7b295e964'
/
update KSEN_SCHED_RQST set OBJ_ID='91ef3617-104d-499f-bfac-3e5d43248568', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for PHYS104 - A', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='3ca15766-1f96-4fcf-8118-1cb124027bce', SCHED_RQST_SET_ID='47acc3e3-f94f-43d6-830f-4f815c789587' where ID='b6040af6-3300-4c60-a2b1-e9c50e45aaf2' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='40094a4b-08ca-4a78-ab2d-66f7b295e964'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('40094a4b-08ca-4a78-ab2d-66f7b295e964', '5e11048b-5d59-4ede-b9eb-54a7dc4dd1a3')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='40094a4b-08ca-4a78-ab2d-66f7b295e964'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('40094a4b-08ca-4a78-ab2d-66f7b295e964', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='40094a4b-08ca-4a78-ab2d-66f7b295e964'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('40094a4b-08ca-4a78-ab2d-66f7b295e964', '1b0b0d73-c24f-4d52-8532-42023a7f7996')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='40094a4b-08ca-4a78-ab2d-66f7b295e964'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('40094a4b-08ca-4a78-ab2d-66f7b295e964', '4bb2875e-aa07-4da1-bb48-ca82528d36b0')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('db8f7a1a-f7f5-4e2b-ba18-6b04cc13f3d3', '4bb2875e-aa07-4da1-bb48-ca82528d36b0')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('1bc5cc58-0460-47ad-bfc5-97b8fbce3825', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'A', '', '', '', '39ecfe64-bdfa-4cac-9665-7be0cc719c01', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '6afb6edc-f0d6-4c87-95c5-3373669ba485')
/
update KSEN_LUI_ATTR set OBJ_ID='3c4b2665-aa59-4de1-9aae-4b7dc8402bb3', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='39ecfe64-bdfa-4cac-9665-7be0cc719c01', ATTR_VALUE='0' where ID='2747d657-8c1e-42e2-ba73-ca86080f966d'
/
update KSEN_LUI set OBJ_ID='e245b458-bb4f-4169-a5c5-d223e5a02cd0', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='52cf48ee-860e-4ed9-82af-6cf489cac69c', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=44, MIN_SEATS=44, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='39ecfe64-bdfa-4cac-9665-7be0cc719c01' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='001b3f61-4c77-4e77-83d2-8a8bd90838bc', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='39ecfe64-bdfa-4cac-9665-7be0cc719c01', ATTR_VALUE='0' where ID='7200e48a-388f-48b4-ae2f-6499242677b0'
/
update KSEN_LUI_IDENT set OBJ_ID='1bc5cc58-0460-47ad-bfc5-97b8fbce3825', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='39ecfe64-bdfa-4cac-9665-7be0cc719c01', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='6afb6edc-f0d6-4c87-95c5-3373669ba485' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='f4d0db38-0fa0-4c42-8176-6fd0da0c1282', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='39ecfe64-bdfa-4cac-9665-7be0cc719c01', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='f26ce4cb-5f83-49b5-bdf9-7f40152df512' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='ccda9e6a-b604-40c8-bda5-e2641928338c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:50:24.193', EXPIR_DT='', LUI_ID='39ecfe64-bdfa-4cac-9665-7be0cc719c01', PERS_ID='L.ROBERTH', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='ecb69832-3e36-4c28-9424-a1230790a6cf' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('39ecfe64-bdfa-4cac-9665-7be0cc719c01', '3ca15766-1f96-4fcf-8118-1cb124027bce')
/
delete from KSEN_LUI_IDENT where ID='45c890b1-6518-48f2-9823-7b00373d6aae' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('6c05f197-f89b-4c66-a391-90639a504544', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '16b417a1-f7fa-4ddf-82af-5dcaa7538884')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('c338ec5e-b2b1-4185-a9d5-db823d6adf37', '0', 'f20ef4ee-7251-452d-b37c-8eddfa424f7e', '16b417a1-f7fa-4ddf-82af-5dcaa7538884', '8883c060-04fc-48e4-bad9-d8413f5a7ebd')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='f3ff281e-a55b-4b4b-99ba-8cdeb260683f' where ID='482a0a6b-58a8-4eaf-8fa0-0e42d3234308'
/
update KSEN_SCHED_RQST set OBJ_ID='de2e3472-d5b3-44c7-b85a-7112d40e418f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for WMST336 - A', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='16b417a1-f7fa-4ddf-82af-5dcaa7538884', SCHED_RQST_SET_ID='03d537d9-5ca0-4463-8479-ad1ce27243e5' where ID='f3ff281e-a55b-4b4b-99ba-8cdeb260683f' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='482a0a6b-58a8-4eaf-8fa0-0e42d3234308'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('482a0a6b-58a8-4eaf-8fa0-0e42d3234308', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='482a0a6b-58a8-4eaf-8fa0-0e42d3234308'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('482a0a6b-58a8-4eaf-8fa0-0e42d3234308', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='482a0a6b-58a8-4eaf-8fa0-0e42d3234308'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('482a0a6b-58a8-4eaf-8fa0-0e42d3234308', 'f20ef4ee-7251-452d-b37c-8eddfa424f7e')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='482a0a6b-58a8-4eaf-8fa0-0e42d3234308'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('482a0a6b-58a8-4eaf-8fa0-0e42d3234308', '4054ccf0-63a4-49cf-8c62-442c721500bf')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('8883c060-04fc-48e4-bad9-d8413f5a7ebd', '4054ccf0-63a4-49cf-8c62-442c721500bf')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('e9a5b3aa-94ab-4f4e-87bb-bb37976c546a', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'A', '', '', '', '4fbcfdd4-dea9-4344-98c9-a8b9c3bf2a6e', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'fbe1ae66-92a8-4745-9803-a01cd2eea062')
/
update KSEN_LUI_ATTR set OBJ_ID='5a518529-c139-4d11-98f3-5b97b234a9f5', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='4fbcfdd4-dea9-4344-98c9-a8b9c3bf2a6e', ATTR_VALUE='0' where ID='699ccf0b-bfec-469c-85d1-10514cae4af0'
/
update KSEN_LUI set OBJ_ID='672cb21f-f9c7-4f5b-bae1-5f4217193140', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='d46ad097-966a-4117-b020-a99576af1e10', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=150, MIN_SEATS=150, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='4fbcfdd4-dea9-4344-98c9-a8b9c3bf2a6e' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='06890490-8edc-4f2e-ad22-e71b7eafae2f', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='4fbcfdd4-dea9-4344-98c9-a8b9c3bf2a6e', ATTR_VALUE='0' where ID='9444dfc5-bb0f-47a5-98c3-39f10d72b225'
/
update KSEN_LUI_IDENT set OBJ_ID='e9a5b3aa-94ab-4f4e-87bb-bb37976c546a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='4fbcfdd4-dea9-4344-98c9-a8b9c3bf2a6e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='fbe1ae66-92a8-4745-9803-a01cd2eea062' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='26d1c892-1835-4505-aefe-ab1d5cefae86', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='4fbcfdd4-dea9-4344-98c9-a8b9c3bf2a6e', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='38f7764c-b002-4cf4-b2cb-01628fa25da6' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='59fa1540-0e92-4124-8ff9-d79cab54fd0c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:50:25.961', EXPIR_DT='', LUI_ID='4fbcfdd4-dea9-4344-98c9-a8b9c3bf2a6e', PERS_ID='A.SZEY', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='09ed9291-0781-420f-890d-de3d0450c798' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('4fbcfdd4-dea9-4344-98c9-a8b9c3bf2a6e', '16b417a1-f7fa-4ddf-82af-5dcaa7538884')
/
delete from KSEN_LUI_IDENT where ID='65365328-8226-4488-a124-b5fba80334ee' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('3bde3364-a96e-4906-8d4a-d9726fd9d57a', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '32eb4c1b-a83c-4ae8-9aa5-277d23db7363')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('9a686d9b-8164-4e9c-b2a2-a37a7ed5e193', '0', '9115e89f-cfa0-48fc-949d-8c7d1c1d5802', '32eb4c1b-a83c-4ae8-9aa5-277d23db7363', '7dc49e7b-0866-4e9a-892d-c39601408d92')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='64b8c69d-f30d-4566-8a7e-ace2c3c4d3e0' where ID='ea7aa33d-4b44-42f4-83b1-2d0e64ca86bf'
/
update KSEN_SCHED_RQST set OBJ_ID='c532a356-d212-4fb0-ab39-04427e8e79d0', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for WMST452 - A', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='32eb4c1b-a83c-4ae8-9aa5-277d23db7363', SCHED_RQST_SET_ID='84da35b0-4e18-4672-b1bb-d9ef6e624b13' where ID='64b8c69d-f30d-4566-8a7e-ace2c3c4d3e0' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='ea7aa33d-4b44-42f4-83b1-2d0e64ca86bf'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('ea7aa33d-4b44-42f4-83b1-2d0e64ca86bf', 'fc3940b4-8d26-4809-93c8-5a8ea21879f6')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='ea7aa33d-4b44-42f4-83b1-2d0e64ca86bf'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('ea7aa33d-4b44-42f4-83b1-2d0e64ca86bf', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='ea7aa33d-4b44-42f4-83b1-2d0e64ca86bf'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('ea7aa33d-4b44-42f4-83b1-2d0e64ca86bf', '9115e89f-cfa0-48fc-949d-8c7d1c1d5802')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='ea7aa33d-4b44-42f4-83b1-2d0e64ca86bf'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('ea7aa33d-4b44-42f4-83b1-2d0e64ca86bf', '514ff892-754e-4b66-bc4f-526d813685ed')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('7dc49e7b-0866-4e9a-892d-c39601408d92', '514ff892-754e-4b66-bc4f-526d813685ed')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('73164599-61b8-4587-ad90-5c83828ed22f', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'A', '', '', '', '73c82da9-c4a1-411b-b765-b34d5c13bca6', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '63e9e393-9977-419f-aee0-7c4f6337aeb5')
/
update KSEN_LUI_ATTR set OBJ_ID='b1d98fe8-b73e-4442-a869-f313ef43db49', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='73c82da9-c4a1-411b-b765-b34d5c13bca6', ATTR_VALUE='0' where ID='7cb90db5-6a5e-4a92-9928-212554d59f9e'
/
update KSEN_LUI set OBJ_ID='447bee9e-3990-4fc5-a79e-5df7b4290830', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='12a0a27c-a467-482f-8ccf-fccb66fbddb9', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=35, MIN_SEATS=35, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='73c82da9-c4a1-411b-b765-b34d5c13bca6' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='9a9e087d-e3db-4b53-a714-bf883c608e0b', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='73c82da9-c4a1-411b-b765-b34d5c13bca6', ATTR_VALUE='0' where ID='f4ef9778-a5a6-4de0-a915-a66a9275adf4'
/
update KSEN_LUI_IDENT set OBJ_ID='73164599-61b8-4587-ad90-5c83828ed22f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='73c82da9-c4a1-411b-b765-b34d5c13bca6', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='63e9e393-9977-419f-aee0-7c4f6337aeb5' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='c770c638-9af2-4b39-bbbf-139c74d4cfa7', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='73c82da9-c4a1-411b-b765-b34d5c13bca6', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='19358090-0881-4c43-b4c8-e0edc1ae89dd' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='eaaf0dbf-7dda-42b3-bf78-0804137f702f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:50:27.781', EXPIR_DT='', LUI_ID='73c82da9-c4a1-411b-b765-b34d5c13bca6', PERS_ID='C.KEITHS', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='0bff9f29-b9f1-46cb-b6e2-816bf19aa585' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('73c82da9-c4a1-411b-b765-b34d5c13bca6', '32eb4c1b-a83c-4ae8-9aa5-277d23db7363')
/
delete from KSEN_LUI_IDENT where ID='3feb2dc2-2f3e-4954-b938-bb4c408d83f7' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('00fb8b82-2e69-40a7-b30c-0848d734973d', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'd780abbd-9005-43e0-b8d7-723b038b7c7d')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('fc58161d-c345-49ee-833d-4e717a9e455d', '0', '2246d609-9fbb-4d06-b45e-a57ef80539b7', 'd780abbd-9005-43e0-b8d7-723b038b7c7d', '4e188bb7-92e3-416e-98b7-95f65d7ce0eb')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='85567321-69c0-4e8b-8c8b-e15252ecc60a' where ID='ab2bb19f-f007-452d-b543-7f51717858fd'
/
update KSEN_SCHED_RQST set OBJ_ID='a09d1450-8065-4e20-ad72-49dc3364ad60', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for CHEM105 - A', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='d780abbd-9005-43e0-b8d7-723b038b7c7d', SCHED_RQST_SET_ID='06100d05-b7f1-48cf-b48c-956d83c91a09' where ID='85567321-69c0-4e8b-8c8b-e15252ecc60a' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='ab2bb19f-f007-452d-b543-7f51717858fd'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('ab2bb19f-f007-452d-b543-7f51717858fd', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='ab2bb19f-f007-452d-b543-7f51717858fd'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('ab2bb19f-f007-452d-b543-7f51717858fd', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='ab2bb19f-f007-452d-b543-7f51717858fd'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('ab2bb19f-f007-452d-b543-7f51717858fd', '2246d609-9fbb-4d06-b45e-a57ef80539b7')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='ab2bb19f-f007-452d-b543-7f51717858fd'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('ab2bb19f-f007-452d-b543-7f51717858fd', 'f183d4dd-0d23-42d6-a2d0-55d1d8607d56')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('4e188bb7-92e3-416e-98b7-95f65d7ce0eb', 'f183d4dd-0d23-42d6-a2d0-55d1d8607d56')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a1d8f5b8-03d5-429f-a472-2d12a2409d33', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'A', '', '', '', '036a740e-f4a6-4c63-8044-4b43495303df', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '9248535b-9972-4969-8ac0-c2f29122d1d8')
/
update KSEN_LUI_ATTR set OBJ_ID='39119c8a-5cb9-4a10-bd49-fcecd9de0801', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='036a740e-f4a6-4c63-8044-4b43495303df', ATTR_VALUE='0' where ID='c91d9c15-c009-4d3b-953e-3af6b382da48'
/
update KSEN_LUI set OBJ_ID='3a57d248-de5b-4b6d-9bcc-7571fd59b20f', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='1bf3adb6-8b48-441f-9d68-cc1005dd9cc6', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=46, MIN_SEATS=46, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='036a740e-f4a6-4c63-8044-4b43495303df' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='882924c8-105a-4909-9f7c-1d3be934c284', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='036a740e-f4a6-4c63-8044-4b43495303df', ATTR_VALUE='0' where ID='c4721aa7-02bf-42fb-b7b4-d6753738f2f0'
/
update KSEN_LUI_IDENT set OBJ_ID='a1d8f5b8-03d5-429f-a472-2d12a2409d33', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='036a740e-f4a6-4c63-8044-4b43495303df', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='9248535b-9972-4969-8ac0-c2f29122d1d8' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='e2af45e3-fb6c-499b-8234-2a5c154f2c20', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='036a740e-f4a6-4c63-8044-4b43495303df', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='2c5efb26-aa03-4110-b1be-1605033f5333' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='b92fc60c-ad1e-43ee-ab15-738a6a9c42f1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:50:30.688', EXPIR_DT='', LUI_ID='036a740e-f4a6-4c63-8044-4b43495303df', PERS_ID='N.BENOITM', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='5b1e3211-c6c4-4026-8df6-e2ba162a4ce6' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('036a740e-f4a6-4c63-8044-4b43495303df', 'd780abbd-9005-43e0-b8d7-723b038b7c7d')
/
delete from KSEN_LUI_IDENT where ID='17627545-fc60-4454-bc37-ec4efacd5b7f' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('259a9d51-9c7d-43dc-98dd-5d35437aff68', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '59f01f5a-fe89-49e2-bb0a-6fd77cb382e8')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('d328ece0-6a72-499f-9596-f96e6b81bc4b', '0', '2246d609-9fbb-4d06-b45e-a57ef80539b7', '59f01f5a-fe89-49e2-bb0a-6fd77cb382e8', '4c3422c9-0bef-44e6-985f-3ebc6e2874b5')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='fe6ef321-df1d-4fe0-bcd8-6fdfa049b4b1' where ID='465e5060-4153-4f33-b3dd-9c4a22947af1'
/
update KSEN_SCHED_RQST set OBJ_ID='34da9751-3328-4be2-926b-7f6fa742f6a1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for CHEM105 - B', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='59f01f5a-fe89-49e2-bb0a-6fd77cb382e8', SCHED_RQST_SET_ID='48384abe-1875-454d-8107-ba032487d233' where ID='fe6ef321-df1d-4fe0-bcd8-6fdfa049b4b1' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='465e5060-4153-4f33-b3dd-9c4a22947af1'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('465e5060-4153-4f33-b3dd-9c4a22947af1', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='465e5060-4153-4f33-b3dd-9c4a22947af1'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('465e5060-4153-4f33-b3dd-9c4a22947af1', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='465e5060-4153-4f33-b3dd-9c4a22947af1'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('465e5060-4153-4f33-b3dd-9c4a22947af1', '2246d609-9fbb-4d06-b45e-a57ef80539b7')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='465e5060-4153-4f33-b3dd-9c4a22947af1'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('465e5060-4153-4f33-b3dd-9c4a22947af1', '74ee45cc-8ed0-4c7d-82d3-617ebfe30d8c')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('4c3422c9-0bef-44e6-985f-3ebc6e2874b5', '74ee45cc-8ed0-4c7d-82d3-617ebfe30d8c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('f38b772a-e6a9-46e1-9723-d4cbc3f10b6c', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'B', '', '', '', '9bc78b34-81d7-4869-9070-d90076845599', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '9dd2de08-311d-4bc2-b238-28559f1b986c')
/
update KSEN_LUI set OBJ_ID='e7c8dd67-047a-494a-a1bf-29c3f824da9f', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='e073a783-300c-4e22-8e41-f661639d3ce5', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=46, MIN_SEATS=46, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='9bc78b34-81d7-4869-9070-d90076845599' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='ddbd8c7b-a786-4dcb-8113-333a08d55689', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='9bc78b34-81d7-4869-9070-d90076845599', ATTR_VALUE='0' where ID='b7eb94da-7073-40e5-ad66-c70f88dc9329'
/
update KSEN_LUI_ATTR set OBJ_ID='dbf912f6-fa30-4a03-8afd-2738901ed2ae', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='9bc78b34-81d7-4869-9070-d90076845599', ATTR_VALUE='0' where ID='be0d48da-b924-42dd-91cb-9f479e2a60f5'
/
update KSEN_LUI_IDENT set OBJ_ID='f38b772a-e6a9-46e1-9723-d4cbc3f10b6c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='9bc78b34-81d7-4869-9070-d90076845599', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='9dd2de08-311d-4bc2-b238-28559f1b986c' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='b951ab6f-751a-4c0a-a6c6-c338edbc6c07', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='9bc78b34-81d7-4869-9070-d90076845599', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='e0224bd6-e295-4cde-a619-28f4ef1fb13d' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='7221fc1a-afa3-410c-a930-de595bdc7793', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:50:32.403', EXPIR_DT='', LUI_ID='9bc78b34-81d7-4869-9070-d90076845599', PERS_ID='D.RAMONAB', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='219e4641-0426-4e1c-bcb7-975668fda2d6' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('9bc78b34-81d7-4869-9070-d90076845599', '59f01f5a-fe89-49e2-bb0a-6fd77cb382e8')
/
delete from KSEN_LUI_IDENT where ID='0ebd17c5-e0cb-4d6a-8c11-9a8381d8c0e3' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('cf6f5fd1-53b0-418f-b6a7-ed9207bb311e', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '411fca89-1de0-4284-b09b-8570aa4d63a1')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('7cfcd0d7-5f9e-43d9-bd7b-091c2bdcf509', '0', 'a8ce091f-4b9d-4f52-ae15-d2eaf275d76a', '411fca89-1de0-4284-b09b-8570aa4d63a1', '72efd446-2adb-4226-bca2-5a2f32da0ebe')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='180671b7-4ebe-4b9e-a29d-ef0075817fd9' where ID='63d0b344-96bf-468f-ac47-86761702f364'
/
update KSEN_SCHED_RQST set OBJ_ID='c29a0f01-9673-41ca-af61-d0c13b8491ae', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for CHEM277 - A', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='411fca89-1de0-4284-b09b-8570aa4d63a1', SCHED_RQST_SET_ID='f98739a6-f8f0-4ba3-93d8-d9ecddd71816' where ID='180671b7-4ebe-4b9e-a29d-ef0075817fd9' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='63d0b344-96bf-468f-ac47-86761702f364'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('63d0b344-96bf-468f-ac47-86761702f364', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='63d0b344-96bf-468f-ac47-86761702f364'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('63d0b344-96bf-468f-ac47-86761702f364', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='63d0b344-96bf-468f-ac47-86761702f364'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('63d0b344-96bf-468f-ac47-86761702f364', 'a8ce091f-4b9d-4f52-ae15-d2eaf275d76a')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='63d0b344-96bf-468f-ac47-86761702f364'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('63d0b344-96bf-468f-ac47-86761702f364', 'afd9e9ab-58fe-44a3-a628-378ae8dc21b0')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('72efd446-2adb-4226-bca2-5a2f32da0ebe', 'afd9e9ab-58fe-44a3-a628-378ae8dc21b0')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('4a7c077e-b34b-4cfb-8cee-fe315de993e6', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'A', '', '', '', 'f9ecdacf-a144-4319-9a3e-35b0ed00982b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'cfad4dd2-40f0-4078-a78e-31042c87e055')
/
update KSEN_LUI_ATTR set OBJ_ID='8fc4f964-8fc0-4e77-a4b9-acd848282b2a', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='f9ecdacf-a144-4319-9a3e-35b0ed00982b', ATTR_VALUE='0' where ID='216f5af9-044e-445b-95e5-0b266a1ba375'
/
update KSEN_LUI set OBJ_ID='4385fbd0-ef56-4967-8d3e-0d9090016e19', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='29737d5a-57a4-49c5-a8fb-2dd1eb0ea581', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=48, MIN_SEATS=48, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='f9ecdacf-a144-4319-9a3e-35b0ed00982b' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='44bc7815-6791-4fff-a938-fdac430a416d', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='f9ecdacf-a144-4319-9a3e-35b0ed00982b', ATTR_VALUE='0' where ID='a2ac000d-d671-4fe5-bf90-e648c5460658'
/
update KSEN_LUI_IDENT set OBJ_ID='4a7c077e-b34b-4cfb-8cee-fe315de993e6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='f9ecdacf-a144-4319-9a3e-35b0ed00982b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='cfad4dd2-40f0-4078-a78e-31042c87e055' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='1e42e9e9-d017-493b-88a3-af58870a624b', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='f9ecdacf-a144-4319-9a3e-35b0ed00982b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='aa76ecbd-61fc-42a9-8822-1503973ac19a' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='91bab2a2-2e0a-4afb-a289-3c1d81772c89', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:50:36.424', EXPIR_DT='', LUI_ID='f9ecdacf-a144-4319-9a3e-35b0ed00982b', PERS_ID='D.YADAC', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='df04f9d9-1f89-4174-b108-b962c9065b29' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('f9ecdacf-a144-4319-9a3e-35b0ed00982b', '411fca89-1de0-4284-b09b-8570aa4d63a1')
/
delete from KSEN_LUI_IDENT where ID='b8c7ade6-d54b-4482-821f-909b3d4f7e31' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('e5aff6d6-25aa-4ce4-a4d7-bd14282d54e5', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '02daaea7-7e5f-4867-9b33-02626dfaa39a')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('fb981a6b-dc22-4e5d-87f0-1d97ed8387c1', '0', '5fcaeba7-a010-40dc-a069-568c197f0809', '02daaea7-7e5f-4867-9b33-02626dfaa39a', '0d0a03b4-80e2-4e6e-8b81-e28f3f7c1e05')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='7d03b8e0-5b68-4cb7-8c63-fb3700801413' where ID='ddec6540-8878-48df-84b4-95cfc64e38cb'
/
update KSEN_SCHED_RQST set OBJ_ID='bc6deb96-459b-427b-8c24-8376c055f69b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for CHEM277 - B', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='02daaea7-7e5f-4867-9b33-02626dfaa39a', SCHED_RQST_SET_ID='25c24cb6-9775-4f7e-b2ed-f653b12a58b9' where ID='7d03b8e0-5b68-4cb7-8c63-fb3700801413' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='ddec6540-8878-48df-84b4-95cfc64e38cb'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('ddec6540-8878-48df-84b4-95cfc64e38cb', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='ddec6540-8878-48df-84b4-95cfc64e38cb'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('ddec6540-8878-48df-84b4-95cfc64e38cb', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='ddec6540-8878-48df-84b4-95cfc64e38cb'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('ddec6540-8878-48df-84b4-95cfc64e38cb', '5fcaeba7-a010-40dc-a069-568c197f0809')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='ddec6540-8878-48df-84b4-95cfc64e38cb'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('ddec6540-8878-48df-84b4-95cfc64e38cb', '8540bc88-c449-45bd-a9c3-8cf346a1a898')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('0d0a03b4-80e2-4e6e-8b81-e28f3f7c1e05', '8540bc88-c449-45bd-a9c3-8cf346a1a898')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('da47108d-bd73-4031-baeb-12bdcb9024f7', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'B', '', '', '', 'a57312f3-d0ba-4e47-bf6a-59892353b332', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7311e247-0272-47d4-a604-f77e105bbcf9')
/
update KSEN_LUI_ATTR set OBJ_ID='5166f051-062a-412e-8c96-a02a3575ce8a', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='a57312f3-d0ba-4e47-bf6a-59892353b332', ATTR_VALUE='0' where ID='6702b1a2-eac7-44e8-8f01-3020cac1aec4'
/
update KSEN_LUI set OBJ_ID='fbf374ae-f7c0-40fa-814b-5be9fd9004c4', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='a29cf7a2-e3f1-4074-af10-5efd16838937', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='a57312f3-d0ba-4e47-bf6a-59892353b332' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='d3535356-32e3-4eff-bf83-473604af7192', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='a57312f3-d0ba-4e47-bf6a-59892353b332', ATTR_VALUE='0' where ID='1fdafb94-d302-45e7-ba57-97b3f42efb60'
/
update KSEN_LUI_IDENT set OBJ_ID='da47108d-bd73-4031-baeb-12bdcb9024f7', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='a57312f3-d0ba-4e47-bf6a-59892353b332', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='7311e247-0272-47d4-a604-f77e105bbcf9' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='32ee5e3e-582e-4666-9727-4086fa8d6354', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='a57312f3-d0ba-4e47-bf6a-59892353b332', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b5977d0c-6767-447c-9b05-e277cc8f6af4' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='57e3a643-8c2a-4c7b-a1ea-96c9d67e069a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:50:38.736', EXPIR_DT='', LUI_ID='a57312f3-d0ba-4e47-bf6a-59892353b332', PERS_ID='V.CATHERINEP', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='b2ce8499-3da3-4065-a0f6-3758da87e62b' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('a57312f3-d0ba-4e47-bf6a-59892353b332', '02daaea7-7e5f-4867-9b33-02626dfaa39a')
/
delete from KSEN_LUI_IDENT where ID='30a87c0e-f571-4c00-b0d2-d4bfe2e88f76' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('726ee2cb-becc-4ccc-8d5b-2d6f9e37ae0e', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '8bb560c2-18c3-417b-ae04-17c42bbdb657')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('a1ab452c-920f-49fb-b4e1-2138a75cb494', '0', '5fcaeba7-a010-40dc-a069-568c197f0809', '8bb560c2-18c3-417b-ae04-17c42bbdb657', '754766f2-8a93-4e84-9523-af87b0290a22')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='25151f71-13f1-4fbf-8c13-d85c3941beb6' where ID='90013d96-3e24-48a4-b5e0-b7f28d6f15cc'
/
update KSEN_SCHED_RQST set OBJ_ID='bd7774e8-b9fa-4be2-a793-00e62b5e5185', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for CHEM277 - C', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='8bb560c2-18c3-417b-ae04-17c42bbdb657', SCHED_RQST_SET_ID='4a04d9c3-138c-459f-a78b-1fb28fba14b7' where ID='25151f71-13f1-4fbf-8c13-d85c3941beb6' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='90013d96-3e24-48a4-b5e0-b7f28d6f15cc'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('90013d96-3e24-48a4-b5e0-b7f28d6f15cc', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='90013d96-3e24-48a4-b5e0-b7f28d6f15cc'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('90013d96-3e24-48a4-b5e0-b7f28d6f15cc', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='90013d96-3e24-48a4-b5e0-b7f28d6f15cc'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('90013d96-3e24-48a4-b5e0-b7f28d6f15cc', '5fcaeba7-a010-40dc-a069-568c197f0809')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='90013d96-3e24-48a4-b5e0-b7f28d6f15cc'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('90013d96-3e24-48a4-b5e0-b7f28d6f15cc', 'f7086834-699e-4db3-88a3-0686a83a6fa7')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('754766f2-8a93-4e84-9523-af87b0290a22', 'f7086834-699e-4db3-88a3-0686a83a6fa7')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d7e19c3b-0b69-4bc5-91f8-8f5de47def4c', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'C', '', '', '', '1240da0c-7e1d-4538-9384-9039d41c0d80', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '25a41b1d-8a5b-46b4-9d64-68d7fe9dc578')
/
update KSEN_LUI_ATTR set OBJ_ID='8e088560-609e-42f4-9189-e6d8ffa12b7f', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='1240da0c-7e1d-4538-9384-9039d41c0d80', ATTR_VALUE='0' where ID='4c0bdb9e-54dd-4bf0-b46e-fb9ce999e4b4'
/
update KSEN_LUI set OBJ_ID='1d6da209-8c06-46a0-8414-3cdab004c353', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='172819c3-11a4-4baf-81c5-63a08bbfb3ed', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='1240da0c-7e1d-4538-9384-9039d41c0d80' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='2d120721-421d-4d1a-9225-45a9b3128899', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='1240da0c-7e1d-4538-9384-9039d41c0d80', ATTR_VALUE='0' where ID='a0707a93-9b68-412d-a16b-de25106c4ce8'
/
update KSEN_LUI_IDENT set OBJ_ID='d7e19c3b-0b69-4bc5-91f8-8f5de47def4c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='C', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='1240da0c-7e1d-4538-9384-9039d41c0d80', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='25a41b1d-8a5b-46b4-9d64-68d7fe9dc578' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='d5037bfc-390f-49e0-9167-1574294037cc', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='1240da0c-7e1d-4538-9384-9039d41c0d80', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b910cf92-c5e3-406f-8fc7-740a73ce369a' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='70b4a8b9-5ce9-4e6b-8645-d4dbadeeab96', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:50:41.086', EXPIR_DT='', LUI_ID='1240da0c-7e1d-4538-9384-9039d41c0d80', PERS_ID='G.SONALK', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='3c19012b-be11-4a97-b3f2-b7c5e19f3f67' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('1240da0c-7e1d-4538-9384-9039d41c0d80', '8bb560c2-18c3-417b-ae04-17c42bbdb657')
/
delete from KSEN_LUI_IDENT where ID='651626f0-5fc5-4d5e-9228-70fde0fc6ce5' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('b16d0325-81b2-46ca-9991-82fd8baa24e2', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '0dafdeac-908c-45a8-b817-ad74eedbd307')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('c13ad1b0-40cf-44d1-8020-0ebf5caf60e5', '0', '374a4a42-7596-462a-87d6-f1f27a7e2922', '0dafdeac-908c-45a8-b817-ad74eedbd307', '025f9612-37aa-43cd-9b1c-76b6b70385f1')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='a8739130-b02c-40ed-936d-783114fa86e1' where ID='e4d40e10-09a7-432c-98dc-cfa0fb7269f0'
/
update KSEN_SCHED_RQST set OBJ_ID='0ae2b762-612e-44ad-ae48-99c32178cc82', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for ENGL313 - A', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='0dafdeac-908c-45a8-b817-ad74eedbd307', SCHED_RQST_SET_ID='fd4b4922-24ba-4bb6-8350-4e114cdbc072' where ID='a8739130-b02c-40ed-936d-783114fa86e1' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='e4d40e10-09a7-432c-98dc-cfa0fb7269f0'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('e4d40e10-09a7-432c-98dc-cfa0fb7269f0', '84e53b2a-f39a-4b7a-8916-731087cff42d')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='e4d40e10-09a7-432c-98dc-cfa0fb7269f0'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('e4d40e10-09a7-432c-98dc-cfa0fb7269f0', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='e4d40e10-09a7-432c-98dc-cfa0fb7269f0'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('e4d40e10-09a7-432c-98dc-cfa0fb7269f0', '374a4a42-7596-462a-87d6-f1f27a7e2922')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='e4d40e10-09a7-432c-98dc-cfa0fb7269f0'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('e4d40e10-09a7-432c-98dc-cfa0fb7269f0', '6cd0c8f0-20f6-489f-ba1c-9d729a73bbde')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('025f9612-37aa-43cd-9b1c-76b6b70385f1', '6cd0c8f0-20f6-489f-ba1c-9d729a73bbde')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('030cdc3b-745d-47db-92b9-26ac8910e0b4', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'A', '', '', '', '4ed64abb-e257-4c18-af2a-edc034278b89', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '781546b1-3f38-46b4-a79c-7cffe0885f01')
/
update KSEN_LUI_ATTR set OBJ_ID='6f02bfce-8bc3-4b99-830f-7c06101ce081', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='4ed64abb-e257-4c18-af2a-edc034278b89', ATTR_VALUE='0' where ID='bc76248e-def2-4970-af14-87efa9c3ed5d'
/
update KSEN_LUI set OBJ_ID='6daaa8a4-5e08-4e22-a000-123c7f95f47e', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='13d328e5-e4d2-4be5-b63c-3c227edb36fa', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=96, MIN_SEATS=96, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='4ed64abb-e257-4c18-af2a-edc034278b89' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='f7d4a0dd-b2f0-488c-80aa-6726e5f966a2', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='4ed64abb-e257-4c18-af2a-edc034278b89', ATTR_VALUE='0' where ID='72613bd7-e235-4fc0-a465-ebcb134e8084'
/
update KSEN_LUI_IDENT set OBJ_ID='030cdc3b-745d-47db-92b9-26ac8910e0b4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='4ed64abb-e257-4c18-af2a-edc034278b89', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='781546b1-3f38-46b4-a79c-7cffe0885f01' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='a73c02d6-ac97-4ffa-914c-bce8d1cb1c6c', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='4ed64abb-e257-4c18-af2a-edc034278b89', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b3a71a8e-1384-461b-badc-00dfe1644a16' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='09a2d78f-f19c-4c28-a641-5b16f14f98b5', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:50:47.439', EXPIR_DT='', LUI_ID='4ed64abb-e257-4c18-af2a-edc034278b89', PERS_ID='S.TONYAP', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='a645a535-9c1b-40bc-97c2-d00cd6fc477f' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('4ed64abb-e257-4c18-af2a-edc034278b89', '0dafdeac-908c-45a8-b817-ad74eedbd307')
/
delete from KSEN_LUI_IDENT where ID='f2271a37-a44f-402c-a22a-4d028b32ffd4' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('1cd182db-4aa5-4f51-b744-fb91d63bda6e', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'c904a29f-ae32-441c-936b-48d85031f530')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('de651792-79b7-4c12-9f5d-c3aeedafa3ff', '0', '191be69d-a17e-479c-9234-c919beba2424', 'c904a29f-ae32-441c-936b-48d85031f530', '4e3ff6a5-765b-4e71-85cb-a966ed228b1f')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='e5172799-1d2d-4aef-b1d4-8769f105b4f2' where ID='03929b74-82c5-4ca9-b1be-45872b5aefda'
/
update KSEN_SCHED_RQST set OBJ_ID='aacf7320-520d-4aef-bee7-eb34dd34e644', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for ENGL313 - B', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='c904a29f-ae32-441c-936b-48d85031f530', SCHED_RQST_SET_ID='a75cc16a-d6c2-452c-952a-315ab52da11c' where ID='e5172799-1d2d-4aef-b1d4-8769f105b4f2' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='03929b74-82c5-4ca9-b1be-45872b5aefda'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('03929b74-82c5-4ca9-b1be-45872b5aefda', 'e16ca603-1563-4787-88bf-d17f94032970')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='03929b74-82c5-4ca9-b1be-45872b5aefda'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('03929b74-82c5-4ca9-b1be-45872b5aefda', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='03929b74-82c5-4ca9-b1be-45872b5aefda'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('03929b74-82c5-4ca9-b1be-45872b5aefda', '191be69d-a17e-479c-9234-c919beba2424')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='03929b74-82c5-4ca9-b1be-45872b5aefda'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('03929b74-82c5-4ca9-b1be-45872b5aefda', 'dc2b0e50-8a44-4834-a5ec-50b460e09563')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('4e3ff6a5-765b-4e71-85cb-a966ed228b1f', 'dc2b0e50-8a44-4834-a5ec-50b460e09563')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('2b60170f-cf3a-4010-91de-4e52e521e82e', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'B', '', '', '', '0e5e5ded-2722-4d46-9ac5-964b6b95006c', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '96dd99e4-26d4-428d-837d-481915cf8658')
/
update KSEN_LUI set OBJ_ID='634d25e5-a8ef-4be1-8e39-620707622a49', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='7031694d-60f2-4012-8e96-4b2f6fc7ff1c', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=24, MIN_SEATS=24, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='0e5e5ded-2722-4d46-9ac5-964b6b95006c' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='80944350-55e8-4086-8d9d-32d3e13c1cdb', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='0e5e5ded-2722-4d46-9ac5-964b6b95006c', ATTR_VALUE='0' where ID='cd557af2-28d9-4abc-af25-eb916e2a68d6'
/
update KSEN_LUI_ATTR set OBJ_ID='3dadd2c3-233f-4e45-9df5-591916977c6c', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='0e5e5ded-2722-4d46-9ac5-964b6b95006c', ATTR_VALUE='0' where ID='c24e1554-4dc6-44d4-9bf6-f35a32bc8fb0'
/
update KSEN_LUI_IDENT set OBJ_ID='2b60170f-cf3a-4010-91de-4e52e521e82e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='0e5e5ded-2722-4d46-9ac5-964b6b95006c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='96dd99e4-26d4-428d-837d-481915cf8658' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='73488f34-6fd4-4375-82f2-ef849f26bb96', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='0e5e5ded-2722-4d46-9ac5-964b6b95006c', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='bbdc63e7-3f0d-4de2-873a-3777a6c83700' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='2c2d3267-d4a4-4be0-8d03-e8d996999730', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:50:50.996', EXPIR_DT='', LUI_ID='0e5e5ded-2722-4d46-9ac5-964b6b95006c', PERS_ID='S.JAMESF', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='60eced09-6d34-450d-8813-31bb6bf82d83' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('0e5e5ded-2722-4d46-9ac5-964b6b95006c', 'c904a29f-ae32-441c-936b-48d85031f530')
/
delete from KSEN_LUI_IDENT where ID='1d01d910-0cfa-4d1e-869f-4d44580e6a2b' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('ed675b97-3fe7-46a6-936e-d7de9dfd3ff3', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'd5102735-79b1-4cdb-aac7-d0dea4c8b9e4')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('e3eede10-e3fa-41af-9fdf-aa31a8c2369a', '0', 'e26b7710-1faa-4f4e-ad57-52caec27baa9', 'd5102735-79b1-4cdb-aac7-d0dea4c8b9e4', 'e079404c-d821-4b62-a16f-f6b4219f31d7')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='b5ae7b21-ca85-47b0-9286-4b0be369983d' where ID='8fe27992-0826-4e75-9e40-9c539d8ac79d'
/
update KSEN_SCHED_RQST set OBJ_ID='3fd4ccad-c0e2-4c78-8a8d-127e5787e72f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for ENGL313 - C', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='d5102735-79b1-4cdb-aac7-d0dea4c8b9e4', SCHED_RQST_SET_ID='2dff4505-9e3a-4c28-809e-ba3d84a85600' where ID='b5ae7b21-ca85-47b0-9286-4b0be369983d' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='8fe27992-0826-4e75-9e40-9c539d8ac79d'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('8fe27992-0826-4e75-9e40-9c539d8ac79d', '84e53b2a-f39a-4b7a-8916-731087cff42d')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='8fe27992-0826-4e75-9e40-9c539d8ac79d'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('8fe27992-0826-4e75-9e40-9c539d8ac79d', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='8fe27992-0826-4e75-9e40-9c539d8ac79d'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('8fe27992-0826-4e75-9e40-9c539d8ac79d', 'e26b7710-1faa-4f4e-ad57-52caec27baa9')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='8fe27992-0826-4e75-9e40-9c539d8ac79d'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('8fe27992-0826-4e75-9e40-9c539d8ac79d', '676a9d93-cc4e-4d61-af13-5aaf6e46757e')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('e079404c-d821-4b62-a16f-f6b4219f31d7', '676a9d93-cc4e-4d61-af13-5aaf6e46757e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6e77f80b-85f1-493b-bb44-0fa10cc62616', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'C', '', '', '', 'fa925c0c-0077-4a2f-bae3-1a1be93342ba', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '30d36b98-499a-4277-a7ac-4bde1858f246')
/
update KSEN_LUI set OBJ_ID='156442bb-9d91-4466-80d8-eb3d0ee8aba3', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='8a6d57fc-0732-47ef-9fd8-44d6318922b3', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=24, MIN_SEATS=24, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='fa925c0c-0077-4a2f-bae3-1a1be93342ba' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='a6b65c01-1377-47b0-a716-b7a558eae969', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='fa925c0c-0077-4a2f-bae3-1a1be93342ba', ATTR_VALUE='0' where ID='a75bad66-1046-4c4a-87ef-66b9aa803a0d'
/
update KSEN_LUI_ATTR set OBJ_ID='2435d6a5-989d-44e8-9228-0188b7773d01', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='fa925c0c-0077-4a2f-bae3-1a1be93342ba', ATTR_VALUE='0' where ID='b2c3ff90-5e15-4481-97b7-366089f924fc'
/
update KSEN_LUI_IDENT set OBJ_ID='6e77f80b-85f1-493b-bb44-0fa10cc62616', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='C', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='fa925c0c-0077-4a2f-bae3-1a1be93342ba', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='30d36b98-499a-4277-a7ac-4bde1858f246' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='1cdf5baf-608f-46ce-b668-328b0172192c', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='fa925c0c-0077-4a2f-bae3-1a1be93342ba', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b49fece1-5348-422d-9fcc-ae9175a36754' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='50c24f89-89b3-488f-a92c-14d7d5399c34', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:50:54.648', EXPIR_DT='', LUI_ID='fa925c0c-0077-4a2f-bae3-1a1be93342ba', PERS_ID='D.JUSTINM', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='17cc9f08-4dce-4eb9-a0a0-bb22ddcc923f' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('fa925c0c-0077-4a2f-bae3-1a1be93342ba', 'd5102735-79b1-4cdb-aac7-d0dea4c8b9e4')
/
delete from KSEN_LUI_IDENT where ID='1d14222c-c457-4373-b085-572206b9a41a' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('2f177234-5e32-4a02-ad2c-e52c4b2c5708', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '9b398bc1-b29b-44b1-b990-481856e7c276')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('df371cd4-cdac-4662-bd6b-b01bdc165955', '0', '4cf97f60-d7e8-4e69-a4e8-eb1294db4b26', '9b398bc1-b29b-44b1-b990-481856e7c276', 'dd6df5af-b4f5-4ba3-877c-8f213235bcbb')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='ae9fa592-466c-4750-b386-82c0e842e5c5' where ID='f3a39c71-faa0-4f0a-b2cb-f9d744139519'
/
update KSEN_SCHED_RQST set OBJ_ID='00ccad85-9d9c-465a-90be-d3a7aec3e3f9', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for ENGL313 - D', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='9b398bc1-b29b-44b1-b990-481856e7c276', SCHED_RQST_SET_ID='b2d3db14-3d74-4986-affe-ca9b5d1eb7e1' where ID='ae9fa592-466c-4750-b386-82c0e842e5c5' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='f3a39c71-faa0-4f0a-b2cb-f9d744139519'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('f3a39c71-faa0-4f0a-b2cb-f9d744139519', '84e53b2a-f39a-4b7a-8916-731087cff42d')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='f3a39c71-faa0-4f0a-b2cb-f9d744139519'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('f3a39c71-faa0-4f0a-b2cb-f9d744139519', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='f3a39c71-faa0-4f0a-b2cb-f9d744139519'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('f3a39c71-faa0-4f0a-b2cb-f9d744139519', '4cf97f60-d7e8-4e69-a4e8-eb1294db4b26')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='f3a39c71-faa0-4f0a-b2cb-f9d744139519'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('f3a39c71-faa0-4f0a-b2cb-f9d744139519', 'dd2b8785-5f56-4c86-b6c7-c77aabcc288c')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('dd6df5af-b4f5-4ba3-877c-8f213235bcbb', 'dd2b8785-5f56-4c86-b6c7-c77aabcc288c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d7b283d2-5a0d-4941-9723-bb317cea77cb', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'D', '', '', '', '62350ce0-2b1e-4566-bf95-ead6429383ce', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '8618fd34-b568-44cd-ba31-6ca48aa53642')
/
update KSEN_LUI_ATTR set OBJ_ID='e17afee8-9343-4154-8a4c-7bb702c49597', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='62350ce0-2b1e-4566-bf95-ead6429383ce', ATTR_VALUE='0' where ID='dcf4215b-2088-49c3-9d3d-a99809c8d567'
/
update KSEN_LUI set OBJ_ID='69ba85fd-1de8-4c55-b94a-a6f44b3f0cba', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='bd80c228-fc5d-4178-9a88-896fa8a1b17e', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=24, MIN_SEATS=24, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='62350ce0-2b1e-4566-bf95-ead6429383ce' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='bd4de0dc-62a2-4e69-83be-74164747269d', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='62350ce0-2b1e-4566-bf95-ead6429383ce', ATTR_VALUE='0' where ID='77164478-d1c2-469e-a2a2-ddec6c7a1328'
/
update KSEN_LUI_IDENT set OBJ_ID='d7b283d2-5a0d-4941-9723-bb317cea77cb', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='D', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='62350ce0-2b1e-4566-bf95-ead6429383ce', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8618fd34-b568-44cd-ba31-6ca48aa53642' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='af6af553-d900-4bad-a923-c4c358c0bd5a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='62350ce0-2b1e-4566-bf95-ead6429383ce', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='e3dd1380-a4b2-48b1-8f4b-17d1068e419b' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='c4ca7a96-2787-43fc-8c46-58869c9d67d1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:50:58.314', EXPIR_DT='', LUI_ID='62350ce0-2b1e-4566-bf95-ead6429383ce', PERS_ID='C.MATTHEWS', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='8ff23184-be5e-4eca-a605-65f5fca11767' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('62350ce0-2b1e-4566-bf95-ead6429383ce', '9b398bc1-b29b-44b1-b990-481856e7c276')
/
delete from KSEN_LUI_IDENT where ID='efa9294f-8805-45d7-a3de-06713904b85f' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('816d11eb-9ecf-499f-adfa-e7aec3ca7bfd', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '9166cd97-23eb-4076-a063-b8904521158e')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('8d11196d-0e9e-4657-a0f9-64e1ce886308', '0', '62e733ab-c66b-4859-b56e-3cca707cca9d', '9166cd97-23eb-4076-a063-b8904521158e', '50d75956-6640-4110-b6c1-32e207a53ccc')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='7ee2ccd8-b14d-4922-a8ff-313ea8842f06' where ID='e62c597c-4de7-42a1-8448-636523c3f77d'
/
update KSEN_SCHED_RQST set OBJ_ID='25752fbd-37f7-43f9-9ae3-5b092f20ef56', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for ENGL313 - E', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='9166cd97-23eb-4076-a063-b8904521158e', SCHED_RQST_SET_ID='a172f860-7a55-443e-ae08-1a2f8fa26622' where ID='7ee2ccd8-b14d-4922-a8ff-313ea8842f06' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='e62c597c-4de7-42a1-8448-636523c3f77d'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('e62c597c-4de7-42a1-8448-636523c3f77d', '84e53b2a-f39a-4b7a-8916-731087cff42d')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='e62c597c-4de7-42a1-8448-636523c3f77d'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('e62c597c-4de7-42a1-8448-636523c3f77d', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='e62c597c-4de7-42a1-8448-636523c3f77d'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('e62c597c-4de7-42a1-8448-636523c3f77d', '62e733ab-c66b-4859-b56e-3cca707cca9d')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='e62c597c-4de7-42a1-8448-636523c3f77d'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('e62c597c-4de7-42a1-8448-636523c3f77d', '008a4de4-839a-481b-a00f-122801aa7f75')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('50d75956-6640-4110-b6c1-32e207a53ccc', '008a4de4-839a-481b-a00f-122801aa7f75')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('db752c64-d4df-4e22-b2b5-8d79b34b5fa1', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'E', '', '', '', '122dc077-c07c-419d-ae57-f816b712a562', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '794a0f1a-17d6-470d-9d32-16236e2f8237')
/
update KSEN_LUI_ATTR set OBJ_ID='6e5e5292-3876-4993-92de-9f57b3d59ba4', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='122dc077-c07c-419d-ae57-f816b712a562', ATTR_VALUE='0' where ID='d6abd6e9-8a28-4412-9bbf-7365870e2496'
/
update KSEN_LUI set OBJ_ID='745ba1f2-d943-49d8-b84f-4da00ca4ce2f', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='38e4c5cd-0e98-4174-8712-9f08616c677e', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=24, MIN_SEATS=24, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='122dc077-c07c-419d-ae57-f816b712a562' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='a4fa67c4-41b5-498a-85b8-c3fb19fabc79', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='122dc077-c07c-419d-ae57-f816b712a562', ATTR_VALUE='0' where ID='e5ac988e-b3bc-425c-a3bd-f4c4b96d62e8'
/
update KSEN_LUI_IDENT set OBJ_ID='db752c64-d4df-4e22-b2b5-8d79b34b5fa1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='E', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='122dc077-c07c-419d-ae57-f816b712a562', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='794a0f1a-17d6-470d-9d32-16236e2f8237' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='f9b9217d-0b82-4a3e-9b88-416be14398de', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='122dc077-c07c-419d-ae57-f816b712a562', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='cddf1caa-6c95-4924-b4d5-a3044eadcac1' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='6dc152d9-fb10-42cc-bc7b-da94456f203b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:51:01.846', EXPIR_DT='', LUI_ID='122dc077-c07c-419d-ae57-f816b712a562', PERS_ID='K.NIELV', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='7d971c1d-ccc3-485f-918b-e66214c96c70' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('122dc077-c07c-419d-ae57-f816b712a562', '9166cd97-23eb-4076-a063-b8904521158e')
/
delete from KSEN_LUI_IDENT where ID='9abce5d8-3d5e-4084-a18b-5925be131125' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('99eaa6db-c802-4872-b187-7268095046bb', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '9d119ce5-be68-488b-b8e9-e0648eaca3b0')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('75bcdbe8-bf9c-4835-ad53-82945679a53d', '0', '12561fb1-edb2-4a87-ab08-8e61c7dc9e68', '9d119ce5-be68-488b-b8e9-e0648eaca3b0', 'f86185a8-3fff-4ad7-a49b-8c00a697984e')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='ebce9ae5-529b-40d6-aff4-af2e4dad34ff' where ID='773187b6-f533-4bf2-a195-6c7a961eeed3'
/
update KSEN_SCHED_RQST set OBJ_ID='2f5e94c9-e38e-4dc9-a698-ee32dba94363', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for ENGL201 - A', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='9d119ce5-be68-488b-b8e9-e0648eaca3b0', SCHED_RQST_SET_ID='9e3b3dd9-ab37-4e57-9112-994622b513d0' where ID='ebce9ae5-529b-40d6-aff4-af2e4dad34ff' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='773187b6-f533-4bf2-a195-6c7a961eeed3'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('773187b6-f533-4bf2-a195-6c7a961eeed3', 'f55b7127-c68e-4b8c-86d6-fe24f6b77d18')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='773187b6-f533-4bf2-a195-6c7a961eeed3'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('773187b6-f533-4bf2-a195-6c7a961eeed3', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='773187b6-f533-4bf2-a195-6c7a961eeed3'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('773187b6-f533-4bf2-a195-6c7a961eeed3', '12561fb1-edb2-4a87-ab08-8e61c7dc9e68')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='773187b6-f533-4bf2-a195-6c7a961eeed3'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('773187b6-f533-4bf2-a195-6c7a961eeed3', '6b485ecf-f9d5-45ff-8c08-158d2c6af95b')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('f86185a8-3fff-4ad7-a49b-8c00a697984e', '6b485ecf-f9d5-45ff-8c08-158d2c6af95b')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('dce140a2-7c1d-4249-bd31-e4c4aa019ac0', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'A', '', '', '', '8719e6fd-34c8-409f-951c-d87d08a44a6b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '61d8e02b-47a8-4ecd-9c8b-a8c958821c9b')
/
update KSEN_LUI set OBJ_ID='24b6b458-fdf2-4e30-ab14-feb8b9a95a40', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='c7ce59fa-08cf-483d-9ade-9c4dab5a94e3', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=35, MIN_SEATS=35, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='8719e6fd-34c8-409f-951c-d87d08a44a6b' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='f7e164a8-208b-47ac-9ab6-a4e28198a5bb', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='8719e6fd-34c8-409f-951c-d87d08a44a6b', ATTR_VALUE='0' where ID='9afc2657-44f9-462d-b6f3-05b8afbf01f7'
/
update KSEN_LUI_ATTR set OBJ_ID='d1686cb2-b6ab-4302-be13-1b6c5770b8fa', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='8719e6fd-34c8-409f-951c-d87d08a44a6b', ATTR_VALUE='0' where ID='3877551d-d37b-4ac0-96a7-615a22d8e0dc'
/
update KSEN_LUI_IDENT set OBJ_ID='dce140a2-7c1d-4249-bd31-e4c4aa019ac0', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='8719e6fd-34c8-409f-951c-d87d08a44a6b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='61d8e02b-47a8-4ecd-9c8b-a8c958821c9b' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='2424104c-219f-4e41-93fa-eab17923e88a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='8719e6fd-34c8-409f-951c-d87d08a44a6b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='6f31f742-d214-4f1d-b4ec-11faf3c4445a' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='7d6f6a12-2788-4a89-a326-c544b9128f95', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:51:03.827', EXPIR_DT='', LUI_ID='8719e6fd-34c8-409f-951c-d87d08a44a6b', PERS_ID='T.THOMASE', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='51a43629-58cd-452e-a127-1cc6463f6c25' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('8719e6fd-34c8-409f-951c-d87d08a44a6b', '9d119ce5-be68-488b-b8e9-e0648eaca3b0')
/
delete from KSEN_LUI_IDENT where ID='376165a6-8aae-4d73-bcfb-49b6e9f8db8c' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('e92f99f4-988b-4413-a091-1bb524bb0cb4', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '9903dd16-7d48-485d-ac59-e7eb1ce180e7')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('2c2ed1fd-d8f1-4aa1-888b-0a2c4f357be3', '0', 'b4a5b349-cdc0-4d40-992d-41a07f1cbc2c', '9903dd16-7d48-485d-ac59-e7eb1ce180e7', '28fbc766-aa6d-43d0-bc5a-e426720e770c')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='d97b9030-00ec-42fc-8ce6-4cf9b5e42261' where ID='d5faae87-1709-4dfe-a4b8-923e1651c2cc'
/
update KSEN_SCHED_RQST set OBJ_ID='32a377a0-3408-4a1d-a697-b235d915785b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for HIST204 - A', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='9903dd16-7d48-485d-ac59-e7eb1ce180e7', SCHED_RQST_SET_ID='5d9ddb1a-78dd-47cd-b61f-c82d29a80367' where ID='d97b9030-00ec-42fc-8ce6-4cf9b5e42261' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='d5faae87-1709-4dfe-a4b8-923e1651c2cc'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('d5faae87-1709-4dfe-a4b8-923e1651c2cc', 'af22fa59-42ab-4d09-9a5b-6980f8b4ae7d')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='d5faae87-1709-4dfe-a4b8-923e1651c2cc'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('d5faae87-1709-4dfe-a4b8-923e1651c2cc', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='d5faae87-1709-4dfe-a4b8-923e1651c2cc'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('d5faae87-1709-4dfe-a4b8-923e1651c2cc', 'b4a5b349-cdc0-4d40-992d-41a07f1cbc2c')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='d5faae87-1709-4dfe-a4b8-923e1651c2cc'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('d5faae87-1709-4dfe-a4b8-923e1651c2cc', '984a672e-4762-449d-a5f5-f3f035924b4c')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('28fbc766-aa6d-43d0-bc5a-e426720e770c', '984a672e-4762-449d-a5f5-f3f035924b4c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7a124cae-e9da-4405-a3eb-b84c95b9291a', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'A', '', '', '', '3aade379-d19f-4f49-ab85-ec4ff4f2bd4f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '6197f66f-be5e-4df6-9252-f615402d01cc')
/
update KSEN_LUI_ATTR set OBJ_ID='064b7f65-320d-414f-a3f5-bc4393284c0e', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='3aade379-d19f-4f49-ab85-ec4ff4f2bd4f', ATTR_VALUE='0' where ID='ae890925-9087-4964-872c-5f8d51d50780'
/
update KSEN_LUI set OBJ_ID='615802c9-95b2-404a-9e0b-29cc2caba09f', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='9f2b36b3-4761-4469-a11f-68064bb11909', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=80, MIN_SEATS=80, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='3aade379-d19f-4f49-ab85-ec4ff4f2bd4f' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='c5018e2a-71e0-4f09-912b-1ca87ee35a49', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='3aade379-d19f-4f49-ab85-ec4ff4f2bd4f', ATTR_VALUE='0' where ID='bb240aa3-3b7f-4791-85e2-12aa1f83269e'
/
update KSEN_LUI_IDENT set OBJ_ID='7a124cae-e9da-4405-a3eb-b84c95b9291a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='3aade379-d19f-4f49-ab85-ec4ff4f2bd4f', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='6197f66f-be5e-4df6-9252-f615402d01cc' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='f7048aa1-bcc2-430e-87e5-582695439370', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='3aade379-d19f-4f49-ab85-ec4ff4f2bd4f', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='930a024b-04da-4c2e-ab2a-daefb08ae305' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='f7e278e3-7295-4b5c-944f-28fe300a3c4c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:51:10.41', EXPIR_DT='', LUI_ID='3aade379-d19f-4f49-ab85-ec4ff4f2bd4f', PERS_ID='M.GLENNG', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='185b0c18-621a-4ebd-a292-004d1e616e84' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('3aade379-d19f-4f49-ab85-ec4ff4f2bd4f', '9903dd16-7d48-485d-ac59-e7eb1ce180e7')
/
delete from KSEN_LUI_IDENT where ID='b7a784bf-a73f-45dc-b5bd-ebbe1ff94a73' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('fd126770-a981-4767-9c46-ad2874858902', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '92e0ef5b-4de7-4d8c-a552-992fd2d0af26')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('689ee7b0-1e3e-4126-af8f-2511e83be408', '0', '814cca13-7f76-4657-b1b9-283f6cbb3e6a', '92e0ef5b-4de7-4d8c-a552-992fd2d0af26', '490d0af4-dfeb-4574-b56c-6305139cdcd4')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='fe6777f1-0feb-4dc9-9a23-94b1c5587cce' where ID='13b32534-402a-4428-9d84-5dc0aa06d0e4'
/
update KSEN_SCHED_RQST set OBJ_ID='4edb3b8f-0c03-48f3-8d7e-93491cf387a7', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for HIST204 - B', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='92e0ef5b-4de7-4d8c-a552-992fd2d0af26', SCHED_RQST_SET_ID='ccdf9ea2-eef0-4562-931c-5fa62466841b' where ID='fe6777f1-0feb-4dc9-9a23-94b1c5587cce' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='13b32534-402a-4428-9d84-5dc0aa06d0e4'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('13b32534-402a-4428-9d84-5dc0aa06d0e4', '9a171e1e-dde4-4095-8fc9-b8fde0da485e')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='13b32534-402a-4428-9d84-5dc0aa06d0e4'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('13b32534-402a-4428-9d84-5dc0aa06d0e4', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='13b32534-402a-4428-9d84-5dc0aa06d0e4'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('13b32534-402a-4428-9d84-5dc0aa06d0e4', '814cca13-7f76-4657-b1b9-283f6cbb3e6a')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='13b32534-402a-4428-9d84-5dc0aa06d0e4'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('13b32534-402a-4428-9d84-5dc0aa06d0e4', '3ce01fde-9493-47b8-beb8-1e33b1cea742')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('490d0af4-dfeb-4574-b56c-6305139cdcd4', '3ce01fde-9493-47b8-beb8-1e33b1cea742')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('ca66178f-42dd-4fc4-8a01-025cdded6672', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'B', '', '', '', '003cb053-a83d-4e93-b0ed-e470bed6f29a', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '88b3debe-d9b7-40b7-b2e3-607e1046fee5')
/
update KSEN_LUI_ATTR set OBJ_ID='8f3f6cfe-8498-46e4-9490-e0ad1165c0e8', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='003cb053-a83d-4e93-b0ed-e470bed6f29a', ATTR_VALUE='0' where ID='67f8e54e-b35e-4d60-8e24-706c2c420ec6'
/
update KSEN_LUI set OBJ_ID='be555447-e339-4fc2-8ea1-f88e01c01064', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='e70ffd7e-977a-4241-ad53-4a98dbdb8bed', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='003cb053-a83d-4e93-b0ed-e470bed6f29a' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='944e7d1c-1326-4687-a3b8-e087394a18a2', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='003cb053-a83d-4e93-b0ed-e470bed6f29a', ATTR_VALUE='0' where ID='1583231e-c277-47ff-811d-61ed5adc3e2e'
/
update KSEN_LUI_IDENT set OBJ_ID='ca66178f-42dd-4fc4-8a01-025cdded6672', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='003cb053-a83d-4e93-b0ed-e470bed6f29a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='88b3debe-d9b7-40b7-b2e3-607e1046fee5' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='5832607f-f99a-4c44-98e9-259bf8a4c75c', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='003cb053-a83d-4e93-b0ed-e470bed6f29a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='e1e51c5f-4610-4f1e-9541-2c3551411dd4' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='9d5b0c60-c6aa-49e5-89b7-ccb2d8a120d6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:51:14.072', EXPIR_DT='', LUI_ID='003cb053-a83d-4e93-b0ed-e470bed6f29a', PERS_ID='C.SANDRAS', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='3a08c636-f1fd-4dbd-88e0-dc45db38c417' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('003cb053-a83d-4e93-b0ed-e470bed6f29a', '92e0ef5b-4de7-4d8c-a552-992fd2d0af26')
/
delete from KSEN_LUI_IDENT where ID='34febd32-8c6a-4307-b88a-f08353750bcc' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('5a26da4b-5258-4414-9dbe-234c7688aec8', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '1d346d11-12da-4662-b630-6d4dfc3c556a')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('57cac6d7-0324-4541-86f7-824e303046fa', '0', 'fdd1e716-7c99-4cc9-afbe-d8fa2e44ec66', '1d346d11-12da-4662-b630-6d4dfc3c556a', 'a50f1a8b-7a53-4a86-bb96-410fc924151a')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='2afed63d-a055-4351-b12a-c2bbe051020d' where ID='9a3f2b6d-8712-4ecc-ba69-dfdaf82d43ff'
/
update KSEN_SCHED_RQST set OBJ_ID='7f73fa51-db66-4640-8a6b-81099e749d7d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for HIST204 - C', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='1d346d11-12da-4662-b630-6d4dfc3c556a', SCHED_RQST_SET_ID='06c95bf7-28d7-49db-bb47-740f5e75ea0d' where ID='2afed63d-a055-4351-b12a-c2bbe051020d' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='9a3f2b6d-8712-4ecc-ba69-dfdaf82d43ff'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('9a3f2b6d-8712-4ecc-ba69-dfdaf82d43ff', 'af22fa59-42ab-4d09-9a5b-6980f8b4ae7d')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='9a3f2b6d-8712-4ecc-ba69-dfdaf82d43ff'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('9a3f2b6d-8712-4ecc-ba69-dfdaf82d43ff', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='9a3f2b6d-8712-4ecc-ba69-dfdaf82d43ff'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('9a3f2b6d-8712-4ecc-ba69-dfdaf82d43ff', 'fdd1e716-7c99-4cc9-afbe-d8fa2e44ec66')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='9a3f2b6d-8712-4ecc-ba69-dfdaf82d43ff'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('9a3f2b6d-8712-4ecc-ba69-dfdaf82d43ff', '46916bec-9ac8-4cae-aa47-e749c72d8143')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('a50f1a8b-7a53-4a86-bb96-410fc924151a', '46916bec-9ac8-4cae-aa47-e749c72d8143')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('14fb646d-e6cb-481b-919e-89d70a7c04e4', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'C', '', '', '', '397f2167-77a8-4e89-ad44-79be6ae1da74', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'abf7d3be-85c6-4692-9085-438e4cfc768f')
/
update KSEN_LUI set OBJ_ID='73736666-026d-4e1c-ae2b-840befa52a50', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='04f3e47b-d1ba-47f3-b243-5cd531b84f40', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='397f2167-77a8-4e89-ad44-79be6ae1da74' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='b5c366f4-5531-44bd-a00a-112b8a08e9cc', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='397f2167-77a8-4e89-ad44-79be6ae1da74', ATTR_VALUE='0' where ID='5ebd20c8-be03-4881-a88e-9a09a01268a6'
/
update KSEN_LUI_ATTR set OBJ_ID='605bbf5d-366c-453d-9ffe-006f148321e7', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='397f2167-77a8-4e89-ad44-79be6ae1da74', ATTR_VALUE='0' where ID='c5d05480-afc0-4ccd-b82f-00aadf9dfc09'
/
update KSEN_LUI_IDENT set OBJ_ID='14fb646d-e6cb-481b-919e-89d70a7c04e4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='C', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='397f2167-77a8-4e89-ad44-79be6ae1da74', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='abf7d3be-85c6-4692-9085-438e4cfc768f' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='82a7a8d5-4fb1-4c72-ba65-46c2a6d21bac', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='397f2167-77a8-4e89-ad44-79be6ae1da74', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='7c09b0b0-b0f7-4676-854e-f4e5adc5767f' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='b4f612ee-351f-418e-9f53-afd857154314', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:51:17.7', EXPIR_DT='', LUI_ID='397f2167-77a8-4e89-ad44-79be6ae1da74', PERS_ID='F.MENGC', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='5be18470-74c7-4911-beb9-1ca94da8db70' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('397f2167-77a8-4e89-ad44-79be6ae1da74', '1d346d11-12da-4662-b630-6d4dfc3c556a')
/
delete from KSEN_LUI_IDENT where ID='bc3d0777-0eeb-4e45-9227-12de8aba11a9' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('ef0bf23e-4b29-4a9e-879e-8c62b19ec776', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'd93711a8-c1e9-4fda-aef8-a00c8956ed9f')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('bb1ceb56-f073-4777-8d67-8434201025eb', '0', 'afd18bcc-481d-4ef3-a569-4c877dfb4008', 'd93711a8-c1e9-4fda-aef8-a00c8956ed9f', '5cbe0199-d331-4a3f-b743-0694579aeba1')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='bd3c3b41-1ef4-4472-bf76-002a35d15dba' where ID='e1dbfffd-00b2-4a10-a250-2020a4af88af'
/
update KSEN_SCHED_RQST set OBJ_ID='a65d58ff-3e23-486c-8a6e-917eb41ef638', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for HIST204 - D', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='d93711a8-c1e9-4fda-aef8-a00c8956ed9f', SCHED_RQST_SET_ID='971a34f3-7264-4e06-859b-76d0e5f0b773' where ID='bd3c3b41-1ef4-4472-bf76-002a35d15dba' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='e1dbfffd-00b2-4a10-a250-2020a4af88af'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('e1dbfffd-00b2-4a10-a250-2020a4af88af', '9c7cd2c1-1c88-4b2e-98b3-aec7b1517ccf')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='e1dbfffd-00b2-4a10-a250-2020a4af88af'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('e1dbfffd-00b2-4a10-a250-2020a4af88af', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='e1dbfffd-00b2-4a10-a250-2020a4af88af'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('e1dbfffd-00b2-4a10-a250-2020a4af88af', 'afd18bcc-481d-4ef3-a569-4c877dfb4008')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='e1dbfffd-00b2-4a10-a250-2020a4af88af'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('e1dbfffd-00b2-4a10-a250-2020a4af88af', '1b7e7a62-9aff-4692-be9d-89b90122494a')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('5cbe0199-d331-4a3f-b743-0694579aeba1', '1b7e7a62-9aff-4692-be9d-89b90122494a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5a4d7d52-4319-4c65-bffd-ab9179fdba51', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'D', '', '', '', 'db7f5043-3473-4bb5-93fa-677394dc793a', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '11f8ea47-228b-4850-91de-33a8c3799d65')
/
update KSEN_LUI set OBJ_ID='d38afa57-bc17-41e3-ab5e-4955fe8e2f89', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='50f30065-8756-4f96-b186-b822f3973474', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='db7f5043-3473-4bb5-93fa-677394dc793a' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='36cbd2f3-b1e3-4234-9944-027d75443445', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='db7f5043-3473-4bb5-93fa-677394dc793a', ATTR_VALUE='0' where ID='39ba745b-5a67-480b-9a39-77ede3b04c90'
/
update KSEN_LUI_ATTR set OBJ_ID='5c4c001d-988c-4dfe-ac6c-48bb6aa5e010', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='db7f5043-3473-4bb5-93fa-677394dc793a', ATTR_VALUE='0' where ID='8da02c0d-fcd8-443a-ad47-462d000dcadc'
/
update KSEN_LUI_IDENT set OBJ_ID='5a4d7d52-4319-4c65-bffd-ab9179fdba51', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='D', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='db7f5043-3473-4bb5-93fa-677394dc793a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='11f8ea47-228b-4850-91de-33a8c3799d65' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='3eb6f35d-49b9-4e55-b0a2-419fb02129f8', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='db7f5043-3473-4bb5-93fa-677394dc793a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='8e263552-9308-4e30-b06e-0678d5d49e5e' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='5f0d1a63-4cf4-4e18-bfe9-f1489acde80f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:51:21.345', EXPIR_DT='', LUI_ID='db7f5043-3473-4bb5-93fa-677394dc793a', PERS_ID='B.JAMIES', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='b83c32ae-2887-4180-bea8-4d56d1576d68' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('db7f5043-3473-4bb5-93fa-677394dc793a', 'd93711a8-c1e9-4fda-aef8-a00c8956ed9f')
/
delete from KSEN_LUI_IDENT where ID='2f2ece2e-2c63-4aa1-8ddf-390aeff8df42' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('27526a8f-ffce-42a1-9993-5003d140be82', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'fbbbe684-d139-47d8-bb35-7e3240eb820e')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('576fc49a-4cb3-4d3e-8f33-1398b1c1655a', '0', '7cc9ed5b-f029-4886-b1dc-df4a9e2ef042', 'fbbbe684-d139-47d8-bb35-7e3240eb820e', '45d3241b-f04a-4df5-800e-c65c8a502da0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='1e808ab3-65c9-43a6-aefe-b6ddfaba6854' where ID='6287ea91-17ad-471e-a097-55e9c00ecf53'
/
update KSEN_SCHED_RQST set OBJ_ID='8bd72ad1-9ec8-4025-9def-0ffffca9cddd', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for HIST204 - E', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='fbbbe684-d139-47d8-bb35-7e3240eb820e', SCHED_RQST_SET_ID='d2c11b2d-4de5-4635-a198-de7239977145' where ID='1e808ab3-65c9-43a6-aefe-b6ddfaba6854' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='6287ea91-17ad-471e-a097-55e9c00ecf53'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('6287ea91-17ad-471e-a097-55e9c00ecf53', '5fff58a6-05a5-44e9-8c9f-f21ce039ce39')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='6287ea91-17ad-471e-a097-55e9c00ecf53'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('6287ea91-17ad-471e-a097-55e9c00ecf53', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='6287ea91-17ad-471e-a097-55e9c00ecf53'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('6287ea91-17ad-471e-a097-55e9c00ecf53', '7cc9ed5b-f029-4886-b1dc-df4a9e2ef042')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='6287ea91-17ad-471e-a097-55e9c00ecf53'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('6287ea91-17ad-471e-a097-55e9c00ecf53', '857445bd-43db-40ef-8a3f-c60c04f593e1')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('45d3241b-f04a-4df5-800e-c65c8a502da0', '857445bd-43db-40ef-8a3f-c60c04f593e1')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('f1cfeeb6-68a3-47ec-8a06-8789202b967d', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'E', '', '', '', '7cc4db78-b0e8-40cc-8f98-33c95a969f6e', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '5bb62182-78b8-4183-8417-7c8cd842ad0f')
/
update KSEN_LUI_ATTR set OBJ_ID='720c6f36-e5d3-40ac-8fbf-c97f016ac2b7', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='7cc4db78-b0e8-40cc-8f98-33c95a969f6e', ATTR_VALUE='0' where ID='7d374fee-34aa-45e8-89e3-a28f67e30fb7'
/
update KSEN_LUI set OBJ_ID='5efc9d51-adb0-46f2-a0c8-5fad631fbe40', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='b117ee2a-8141-4a83-8bf9-c9c667db3ed7', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='7cc4db78-b0e8-40cc-8f98-33c95a969f6e' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='bbefaa41-c729-4c33-8ee9-e651ba1b889b', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='7cc4db78-b0e8-40cc-8f98-33c95a969f6e', ATTR_VALUE='0' where ID='e8e7559b-d1bd-491f-99d7-7951ecd02a43'
/
update KSEN_LUI_IDENT set OBJ_ID='f1cfeeb6-68a3-47ec-8a06-8789202b967d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='E', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='7cc4db78-b0e8-40cc-8f98-33c95a969f6e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='5bb62182-78b8-4183-8417-7c8cd842ad0f' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='eb76594b-4dbd-4c6e-9ac7-48ad487fef7d', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='7cc4db78-b0e8-40cc-8f98-33c95a969f6e', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='6dfce852-d7f8-43dc-988a-b982497e0f65' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='2d5601e3-a418-4333-bd8f-006e0af62b3c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:51:24.918', EXPIR_DT='', LUI_ID='7cc4db78-b0e8-40cc-8f98-33c95a969f6e', PERS_ID='M.JULIAM', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='22428c4e-4263-45fe-afec-61e97548c083' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('7cc4db78-b0e8-40cc-8f98-33c95a969f6e', 'fbbbe684-d139-47d8-bb35-7e3240eb820e')
/
delete from KSEN_LUI_IDENT where ID='bf12059f-0a29-4d3a-81b6-7ff2334abec3' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('e09efb30-9a27-41f4-b0e8-4180596bb0e2', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '76162f86-f223-46ac-a23c-636fd8e3701e')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('460e6f50-1607-4a24-9259-19b945d7376a', '0', '7c1897ff-18ec-408d-8144-aa8c60b7b5ba', '76162f86-f223-46ac-a23c-636fd8e3701e', 'f3da98cd-7dc7-4fb7-a7b9-5640cd235bea')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='f8ec2684-523d-4b9f-bdea-d063b24b02a5' where ID='6a299727-68c0-4489-b1e8-0fe4218cd396'
/
update KSEN_SCHED_RQST set OBJ_ID='5dfd235d-1ee9-46b1-91cf-1a124bfe77ab', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for HIST110 - A', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='76162f86-f223-46ac-a23c-636fd8e3701e', SCHED_RQST_SET_ID='8d26316c-b869-4cc2-9899-bd6c3e689d03' where ID='f8ec2684-523d-4b9f-bdea-d063b24b02a5' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='6a299727-68c0-4489-b1e8-0fe4218cd396'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('6a299727-68c0-4489-b1e8-0fe4218cd396', 'af22fa59-42ab-4d09-9a5b-6980f8b4ae7d')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='6a299727-68c0-4489-b1e8-0fe4218cd396'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('6a299727-68c0-4489-b1e8-0fe4218cd396', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='6a299727-68c0-4489-b1e8-0fe4218cd396'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('6a299727-68c0-4489-b1e8-0fe4218cd396', '7c1897ff-18ec-408d-8144-aa8c60b7b5ba')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='6a299727-68c0-4489-b1e8-0fe4218cd396'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('6a299727-68c0-4489-b1e8-0fe4218cd396', 'defea910-3dac-4695-9697-40c9a6c2f884')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('f3da98cd-7dc7-4fb7-a7b9-5640cd235bea', 'defea910-3dac-4695-9697-40c9a6c2f884')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('67f6fa83-ecdd-4ba4-96c1-78ab2acb6196', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'A', '', '', '', 'c186e825-424e-48b4-86ae-bfc6aeffdfee', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '41d3c4d7-2cc6-46e9-8829-484e1104dde2')
/
update KSEN_LUI set OBJ_ID='df57e4f6-18f2-4c5a-85c3-c394c5284696', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='25f408c6-e481-48cb-82f8-76baf92f9a31', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=180, MIN_SEATS=180, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='c186e825-424e-48b4-86ae-bfc6aeffdfee' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='c85c3ff7-5699-48a0-9785-c8863596ab11', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='c186e825-424e-48b4-86ae-bfc6aeffdfee', ATTR_VALUE='0' where ID='772d7985-7b4e-4491-95a1-e70932216167'
/
update KSEN_LUI_ATTR set OBJ_ID='c942f205-28da-456c-a8f0-44a249a93724', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='c186e825-424e-48b4-86ae-bfc6aeffdfee', ATTR_VALUE='0' where ID='bb8ac3f6-7d1f-4f43-bad3-098dbca2cf4f'
/
update KSEN_LUI_IDENT set OBJ_ID='67f6fa83-ecdd-4ba4-96c1-78ab2acb6196', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='c186e825-424e-48b4-86ae-bfc6aeffdfee', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='41d3c4d7-2cc6-46e9-8829-484e1104dde2' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='31e827eb-c6dd-44d9-ab02-7b596d9ce6be', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='c186e825-424e-48b4-86ae-bfc6aeffdfee', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='6e11902d-6901-4919-a3a2-0e8f60128870' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='a57f81f2-ba74-463c-8154-2dcfd5102ab2', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:51:37.279', EXPIR_DT='', LUI_ID='c186e825-424e-48b4-86ae-bfc6aeffdfee', PERS_ID='D.KARING', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='bfdfece0-9f43-4b87-973b-7719f2b163df' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('c186e825-424e-48b4-86ae-bfc6aeffdfee', '76162f86-f223-46ac-a23c-636fd8e3701e')
/
delete from KSEN_LUI_IDENT where ID='de82ae12-1824-428d-801c-c0bce308ee86' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('3ac4cadc-33d9-4392-b83b-c813f2d0336c', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'f2dc65e8-c66f-4a29-ba96-3baa3253cd5a')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('b295abb9-d805-476c-b987-5e8412a43435', '0', '3a65af54-eb98-475e-9970-e8b8408cf750', 'f2dc65e8-c66f-4a29-ba96-3baa3253cd5a', '30fc9dfe-46e4-4553-a757-8bdda38af2a2')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='ebae068c-07b3-4257-8009-d8c67cd2d2a9' where ID='090686a6-ab5f-4ad5-9f28-6c2097d3a68b'
/
update KSEN_SCHED_RQST set OBJ_ID='932ff6d4-2b72-4ff3-a109-d6a5bc25b5f4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for HIST110 - B', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='f2dc65e8-c66f-4a29-ba96-3baa3253cd5a', SCHED_RQST_SET_ID='63970644-b871-4430-90af-be046b0175f2' where ID='ebae068c-07b3-4257-8009-d8c67cd2d2a9' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='090686a6-ab5f-4ad5-9f28-6c2097d3a68b'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('090686a6-ab5f-4ad5-9f28-6c2097d3a68b', 'af22fa59-42ab-4d09-9a5b-6980f8b4ae7d')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='090686a6-ab5f-4ad5-9f28-6c2097d3a68b'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('090686a6-ab5f-4ad5-9f28-6c2097d3a68b', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='090686a6-ab5f-4ad5-9f28-6c2097d3a68b'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('090686a6-ab5f-4ad5-9f28-6c2097d3a68b', '3a65af54-eb98-475e-9970-e8b8408cf750')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='090686a6-ab5f-4ad5-9f28-6c2097d3a68b'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('090686a6-ab5f-4ad5-9f28-6c2097d3a68b', '0c1d66ea-3ffe-439b-9e66-a1d9374fa6e1')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('30fc9dfe-46e4-4553-a757-8bdda38af2a2', '0c1d66ea-3ffe-439b-9e66-a1d9374fa6e1')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5eb92ab1-aad1-4fa6-af2f-1c8d2eb9c0f3', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'B', '', '', '', 'ae9703d9-be4e-4e8a-9288-3df4067ba126', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '5ed3dd51-df9f-4f40-be89-c42b654f155e')
/
update KSEN_LUI set OBJ_ID='cdc59953-6d16-46f6-ae70-3a2813d40880', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='3e116be8-1102-41ec-945f-92f8cb201300', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='ae9703d9-be4e-4e8a-9288-3df4067ba126' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='8bbd5810-fa14-466d-9a50-6acf439a81a4', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='ae9703d9-be4e-4e8a-9288-3df4067ba126', ATTR_VALUE='0' where ID='92d3f070-8064-4c32-83b6-ce03a380610e'
/
update KSEN_LUI_ATTR set OBJ_ID='773f2076-faa2-43d5-a0e5-f3b3f60f6121', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='ae9703d9-be4e-4e8a-9288-3df4067ba126', ATTR_VALUE='0' where ID='29b46437-b33d-46ce-b52d-14d6a7de2628'
/
update KSEN_LUI_IDENT set OBJ_ID='5eb92ab1-aad1-4fa6-af2f-1c8d2eb9c0f3', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='ae9703d9-be4e-4e8a-9288-3df4067ba126', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='5ed3dd51-df9f-4f40-be89-c42b654f155e' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='f7a72ab6-3d8c-47b0-9cdb-b6e0fef5da5d', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='ae9703d9-be4e-4e8a-9288-3df4067ba126', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='cde324ce-8569-4dda-bf27-4a0a0aa91a36' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='deb650c8-5a3a-46aa-a84d-5a28a2475fd0', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:51:43.874', EXPIR_DT='', LUI_ID='ae9703d9-be4e-4e8a-9288-3df4067ba126', PERS_ID='S.CELESTEB', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='b48c9368-8699-450f-baf1-9707287d38ec' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('ae9703d9-be4e-4e8a-9288-3df4067ba126', 'f2dc65e8-c66f-4a29-ba96-3baa3253cd5a')
/
delete from KSEN_LUI_IDENT where ID='d6c0de06-1220-4dab-8cbb-803998329bd3' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('b9e66ebb-9588-4dbe-8fae-2f8c610b165f', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '69f53e10-9bba-4f03-aa01-0cb24c53dad5')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('c1412885-7112-45d3-bdc5-4733cd1e734e', '0', '7f7f8668-b45b-4beb-a877-5b9461240b1c', '69f53e10-9bba-4f03-aa01-0cb24c53dad5', '56d2d0d7-9da9-4fb5-9db1-9d552a002884')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='258717fb-e86f-48bd-98b1-ade2426addf9' where ID='33aa6b79-bce8-43af-adf3-c0f701b39b16'
/
update KSEN_SCHED_RQST set OBJ_ID='8313597b-74da-4de1-a9a1-cd951f087313', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for HIST110 - C', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='69f53e10-9bba-4f03-aa01-0cb24c53dad5', SCHED_RQST_SET_ID='2ba81b5b-a540-4b6c-b910-9c314f5813db' where ID='258717fb-e86f-48bd-98b1-ade2426addf9' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='33aa6b79-bce8-43af-adf3-c0f701b39b16'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('33aa6b79-bce8-43af-adf3-c0f701b39b16', '099f0cbc-e9f5-4c5b-b4bf-5f8a0a6e0955')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='33aa6b79-bce8-43af-adf3-c0f701b39b16'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('33aa6b79-bce8-43af-adf3-c0f701b39b16', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='33aa6b79-bce8-43af-adf3-c0f701b39b16'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('33aa6b79-bce8-43af-adf3-c0f701b39b16', '7f7f8668-b45b-4beb-a877-5b9461240b1c')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='33aa6b79-bce8-43af-adf3-c0f701b39b16'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('33aa6b79-bce8-43af-adf3-c0f701b39b16', '38481905-7e0f-4682-ab2e-df530abd95fd')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('56d2d0d7-9da9-4fb5-9db1-9d552a002884', '38481905-7e0f-4682-ab2e-df530abd95fd')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('0347fabc-06aa-43a4-8b7b-3cd11c5f8176', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'C', '', '', '', 'c4771b96-a10a-4857-aaf8-bd4beb3c0f85', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '583e1ae6-28c1-4ddb-8de2-5d43417603c0')
/
update KSEN_LUI_ATTR set OBJ_ID='eb1c7ae1-ea62-4329-84ac-ab17d78b769a', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='c4771b96-a10a-4857-aaf8-bd4beb3c0f85', ATTR_VALUE='0' where ID='a56086fd-82e3-4094-bfaf-949079b2f545'
/
update KSEN_LUI set OBJ_ID='1ad7231a-eac1-47eb-b978-54630c9b3497', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='778c4a89-a3be-48c8-ae02-afbdf2f34970', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='c4771b96-a10a-4857-aaf8-bd4beb3c0f85' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='e90135d2-11f5-4672-b92b-77233e6b9d4e', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='c4771b96-a10a-4857-aaf8-bd4beb3c0f85', ATTR_VALUE='0' where ID='6fee2fa3-c24e-47eb-b5c3-951666fb9af7'
/
update KSEN_LUI_IDENT set OBJ_ID='0347fabc-06aa-43a4-8b7b-3cd11c5f8176', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='C', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='c4771b96-a10a-4857-aaf8-bd4beb3c0f85', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='583e1ae6-28c1-4ddb-8de2-5d43417603c0' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='04998963-bfbd-4d99-bdb5-4f6eaf31f517', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='c4771b96-a10a-4857-aaf8-bd4beb3c0f85', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='61ca476a-ce8e-40a9-9fd3-526e37d2dd45' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='9bdd99b5-eee8-47b0-ad20-8e08528f1e1e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:51:50.406', EXPIR_DT='', LUI_ID='c4771b96-a10a-4857-aaf8-bd4beb3c0f85', PERS_ID='K.LAURAR', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='936d8764-08ff-48cc-9a26-15cc9810c33d' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('c4771b96-a10a-4857-aaf8-bd4beb3c0f85', '69f53e10-9bba-4f03-aa01-0cb24c53dad5')
/
delete from KSEN_LUI_IDENT where ID='e90a296b-b8be-4e94-a195-7459734856ab' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('3eb4d8fd-c620-4db2-8577-9bb94fb74585', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '47ebac57-cec9-4f63-b825-fa8e2a8a8ab1')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('91817815-a9c9-4b6a-93b7-e988d77a7610', '0', '7f7f8668-b45b-4beb-a877-5b9461240b1c', '47ebac57-cec9-4f63-b825-fa8e2a8a8ab1', '5068f960-2893-4a48-8489-cc874d259845')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='fef6e692-e42b-4314-a4b5-9995925badb6' where ID='a8d8e1a7-a8bc-4bf8-ad78-a2d9b8a805aa'
/
update KSEN_SCHED_RQST set OBJ_ID='cef694bf-a2ab-4ffa-aaeb-52b17859b783', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for HIST110 - D', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='47ebac57-cec9-4f63-b825-fa8e2a8a8ab1', SCHED_RQST_SET_ID='d1a9444f-3dd1-4bc0-940d-ec469d19c440' where ID='fef6e692-e42b-4314-a4b5-9995925badb6' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='a8d8e1a7-a8bc-4bf8-ad78-a2d9b8a805aa'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('a8d8e1a7-a8bc-4bf8-ad78-a2d9b8a805aa', '099f0cbc-e9f5-4c5b-b4bf-5f8a0a6e0955')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='a8d8e1a7-a8bc-4bf8-ad78-a2d9b8a805aa'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('a8d8e1a7-a8bc-4bf8-ad78-a2d9b8a805aa', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='a8d8e1a7-a8bc-4bf8-ad78-a2d9b8a805aa'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('a8d8e1a7-a8bc-4bf8-ad78-a2d9b8a805aa', '7f7f8668-b45b-4beb-a877-5b9461240b1c')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='a8d8e1a7-a8bc-4bf8-ad78-a2d9b8a805aa'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('a8d8e1a7-a8bc-4bf8-ad78-a2d9b8a805aa', '36e07114-eafe-46f8-8e75-c3716f8e20f8')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('5068f960-2893-4a48-8489-cc874d259845', '36e07114-eafe-46f8-8e75-c3716f8e20f8')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('3e962b6a-7b74-4d21-b437-2d380891ceb8', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'D', '', '', '', 'a3b6c14e-8615-4479-a1c0-b033d0228401', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '351b25f9-dc7f-40d5-8c6c-e2385fb8a01b')
/
update KSEN_LUI set OBJ_ID='42b1dc8b-3e9f-412a-ad39-d9aeef73fdd7', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='3cad8c8d-4ded-4ff1-aa0a-60f5dce6edf1', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='a3b6c14e-8615-4479-a1c0-b033d0228401' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='2c9d948f-4cd2-4764-af7b-8fae688d4802', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='a3b6c14e-8615-4479-a1c0-b033d0228401', ATTR_VALUE='0' where ID='bd58d13f-5478-4fc2-a603-7e808a2708be'
/
update KSEN_LUI_ATTR set OBJ_ID='dcc45b93-1553-4d7e-bf49-9ca62e52be07', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='a3b6c14e-8615-4479-a1c0-b033d0228401', ATTR_VALUE='0' where ID='a42ba3d4-6721-4ed1-b6f2-6ab2cd09fa2f'
/
update KSEN_LUI_IDENT set OBJ_ID='3e962b6a-7b74-4d21-b437-2d380891ceb8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='D', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='a3b6c14e-8615-4479-a1c0-b033d0228401', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='351b25f9-dc7f-40d5-8c6c-e2385fb8a01b' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='df443f6d-c787-4ecc-9918-af278a5288f0', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='a3b6c14e-8615-4479-a1c0-b033d0228401', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='af4f3036-dea3-491f-93d2-a264946ef3ea' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='fd8ef88d-9b3d-4cc8-94c1-b0b973392ae7', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:51:56.924', EXPIR_DT='', LUI_ID='a3b6c14e-8615-4479-a1c0-b033d0228401', PERS_ID='J.ARIELZ', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='8ecee118-37f0-4ade-b5dc-b0811e40117e' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('a3b6c14e-8615-4479-a1c0-b033d0228401', '47ebac57-cec9-4f63-b825-fa8e2a8a8ab1')
/
delete from KSEN_LUI_IDENT where ID='637d1b50-cf92-4526-be19-cfac7117666e' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('6dee8e6d-57c0-4746-9720-47ed6f39527c', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '2157307f-32a0-405a-8d23-68fecb0daac5')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('0db48265-1d4f-4801-8688-f36c7faa1385', '0', '814cca13-7f76-4657-b1b9-283f6cbb3e6a', '2157307f-32a0-405a-8d23-68fecb0daac5', 'f0b613c3-7c71-4823-87b8-22ccfb91408b')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='8711ae11-d5d2-4908-8845-c490c71a1cb6' where ID='5bd7210d-a12e-4c33-8f96-a55a6cfcedb6'
/
update KSEN_SCHED_RQST set OBJ_ID='ad7a1d35-bc7a-470a-afa0-6c1d66edac13', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for HIST110 - E', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='2157307f-32a0-405a-8d23-68fecb0daac5', SCHED_RQST_SET_ID='9876eac8-ef9f-4c90-aad6-c3ed57f6bea9' where ID='8711ae11-d5d2-4908-8845-c490c71a1cb6' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='5bd7210d-a12e-4c33-8f96-a55a6cfcedb6'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('5bd7210d-a12e-4c33-8f96-a55a6cfcedb6', '9a171e1e-dde4-4095-8fc9-b8fde0da485e')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='5bd7210d-a12e-4c33-8f96-a55a6cfcedb6'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('5bd7210d-a12e-4c33-8f96-a55a6cfcedb6', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='5bd7210d-a12e-4c33-8f96-a55a6cfcedb6'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('5bd7210d-a12e-4c33-8f96-a55a6cfcedb6', '814cca13-7f76-4657-b1b9-283f6cbb3e6a')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='5bd7210d-a12e-4c33-8f96-a55a6cfcedb6'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('5bd7210d-a12e-4c33-8f96-a55a6cfcedb6', 'c3ba6332-f07f-4203-bda9-ba3ebeff907a')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('f0b613c3-7c71-4823-87b8-22ccfb91408b', 'c3ba6332-f07f-4203-bda9-ba3ebeff907a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('59800abb-7889-40bb-ad6b-e3017543bcde', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'E', '', '', '', 'bc880949-6bc5-4c4a-8e9a-ff2dc107b92a', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '01547aa1-5b35-49a0-9dce-3e63fc26e901')
/
update KSEN_LUI_ATTR set OBJ_ID='a0d329a6-3fc5-4916-85fc-dd52bc1d7fcd', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='bc880949-6bc5-4c4a-8e9a-ff2dc107b92a', ATTR_VALUE='0' where ID='24e46f29-10ca-46c5-a332-1846009d64fc'
/
update KSEN_LUI set OBJ_ID='a157ef3d-05de-421b-9e3d-3193ca83cec2', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='2d5222a0-843e-4c1b-9f7e-847887ab9d2c', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='bc880949-6bc5-4c4a-8e9a-ff2dc107b92a' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='355215b3-83f4-4e2d-9dc8-cb0826681f03', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='bc880949-6bc5-4c4a-8e9a-ff2dc107b92a', ATTR_VALUE='0' where ID='f2aa393b-01e1-42ad-b81d-ec232cceb3fe'
/
update KSEN_LUI_IDENT set OBJ_ID='59800abb-7889-40bb-ad6b-e3017543bcde', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='E', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='bc880949-6bc5-4c4a-8e9a-ff2dc107b92a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='01547aa1-5b35-49a0-9dce-3e63fc26e901' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='f0f17554-d686-4b9f-b807-81f8611f7d68', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='bc880949-6bc5-4c4a-8e9a-ff2dc107b92a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='5f6d499c-9356-45de-926f-0ac339cb7a49' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='07d9f40f-ebe6-4ea6-a1cf-fb987d95b3cb', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:52:03.472', EXPIR_DT='', LUI_ID='bc880949-6bc5-4c4a-8e9a-ff2dc107b92a', PERS_ID='M.SANDRAM', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='2dee1dd1-8b38-42f0-9362-95762c41f51e' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('bc880949-6bc5-4c4a-8e9a-ff2dc107b92a', '2157307f-32a0-405a-8d23-68fecb0daac5')
/
delete from KSEN_LUI_IDENT where ID='b03ea796-6c19-429a-8a8e-e10d12d0d698' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('f6f8131c-7fb6-4407-b8c2-aba74f45e8ac', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '349d247d-6c93-4271-aa3a-3d5221ef15e8')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('cac2cc51-06c1-4b3a-8544-fbf1b67f7f9e', '0', 'b1e0c71f-8c6f-4e9a-a0a9-a677efa6c56e', '349d247d-6c93-4271-aa3a-3d5221ef15e8', '91ffc333-eee9-44c8-8d4e-87d04310c5fb')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='2a5991f0-2941-491e-88c1-a82d6dae620e' where ID='37400d6d-d8be-41d8-b631-e0d47c9efed2'
/
update KSEN_SCHED_RQST set OBJ_ID='a8f1bf92-7bdb-4264-a4f9-c2256fbe358e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for HIST110 - F', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='349d247d-6c93-4271-aa3a-3d5221ef15e8', SCHED_RQST_SET_ID='235076a9-4ff8-4cd3-ab80-a6e324194c41' where ID='2a5991f0-2941-491e-88c1-a82d6dae620e' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='37400d6d-d8be-41d8-b631-e0d47c9efed2'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('37400d6d-d8be-41d8-b631-e0d47c9efed2', 'af22fa59-42ab-4d09-9a5b-6980f8b4ae7d')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='37400d6d-d8be-41d8-b631-e0d47c9efed2'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('37400d6d-d8be-41d8-b631-e0d47c9efed2', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='37400d6d-d8be-41d8-b631-e0d47c9efed2'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('37400d6d-d8be-41d8-b631-e0d47c9efed2', 'b1e0c71f-8c6f-4e9a-a0a9-a677efa6c56e')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='37400d6d-d8be-41d8-b631-e0d47c9efed2'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('37400d6d-d8be-41d8-b631-e0d47c9efed2', 'e3034238-3b3c-46ec-ab1b-66d2a8beb2ca')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('91ffc333-eee9-44c8-8d4e-87d04310c5fb', 'e3034238-3b3c-46ec-ab1b-66d2a8beb2ca')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('09e85698-97ce-414c-92a1-efcdedbab983', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'F', '', '', '', 'b2789b95-350b-4f3a-9183-7fdfd9108283', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '2ffc8c33-9a3b-4300-a218-8842e3a43831')
/
update KSEN_LUI_ATTR set OBJ_ID='eb78671c-f21d-4a9c-be6c-5428d1aab7a0', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='b2789b95-350b-4f3a-9183-7fdfd9108283', ATTR_VALUE='0' where ID='be988162-27e6-48db-9879-13f51555df05'
/
update KSEN_LUI set OBJ_ID='c6b0fc9d-dbbc-4da3-93c6-97d09ed1ea3a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='347af0e5-98a9-4b65-9527-2976bcf251a0', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='b2789b95-350b-4f3a-9183-7fdfd9108283' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='bfe511a9-f540-4629-990a-87f9682f55b0', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='b2789b95-350b-4f3a-9183-7fdfd9108283', ATTR_VALUE='0' where ID='2e7820f5-97da-4883-96a0-79b3a17cb838'
/
update KSEN_LUI_IDENT set OBJ_ID='09e85698-97ce-414c-92a1-efcdedbab983', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='F', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='b2789b95-350b-4f3a-9183-7fdfd9108283', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='2ffc8c33-9a3b-4300-a218-8842e3a43831' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='21c0d420-0dda-41d1-a03d-f183605e88e4', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='b2789b95-350b-4f3a-9183-7fdfd9108283', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='13d63d98-a4ce-49a2-b4b6-446274ba77d7' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='28128611-4ca9-4aef-a197-452ee5faac5d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:52:09.959', EXPIR_DT='', LUI_ID='b2789b95-350b-4f3a-9183-7fdfd9108283', PERS_ID='M.BONNIEL', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='e751e5ef-1877-4915-b833-978971d04447' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('b2789b95-350b-4f3a-9183-7fdfd9108283', '349d247d-6c93-4271-aa3a-3d5221ef15e8')
/
delete from KSEN_LUI_IDENT where ID='3c6ab59c-bbac-456b-a321-2d697495333b' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('3939fbc3-da0b-4513-9c3e-3c40e5c009e4', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '0c2f1dfb-0b0f-4686-a9d0-83d6781ee0a6')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('b2c7d50c-97bc-4940-bd05-0f7fef1975e5', '0', '3a65af54-eb98-475e-9970-e8b8408cf750', '0c2f1dfb-0b0f-4686-a9d0-83d6781ee0a6', 'ffd0052f-54bb-466d-a76b-61e094bb5b68')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='eb0121a6-c5e6-49d4-9663-25d3aa5dbb26' where ID='4eac5400-af5e-4d1f-b905-99583231bd38'
/
update KSEN_SCHED_RQST set OBJ_ID='69bd3237-88c5-4ed3-9f6a-2e9945ea31a2', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for HIST110 - G', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='0c2f1dfb-0b0f-4686-a9d0-83d6781ee0a6', SCHED_RQST_SET_ID='cf3eb40f-8841-4529-96e2-b5c28dc64090' where ID='eb0121a6-c5e6-49d4-9663-25d3aa5dbb26' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='4eac5400-af5e-4d1f-b905-99583231bd38'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('4eac5400-af5e-4d1f-b905-99583231bd38', 'af22fa59-42ab-4d09-9a5b-6980f8b4ae7d')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='4eac5400-af5e-4d1f-b905-99583231bd38'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('4eac5400-af5e-4d1f-b905-99583231bd38', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='4eac5400-af5e-4d1f-b905-99583231bd38'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('4eac5400-af5e-4d1f-b905-99583231bd38', '3a65af54-eb98-475e-9970-e8b8408cf750')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='4eac5400-af5e-4d1f-b905-99583231bd38'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('4eac5400-af5e-4d1f-b905-99583231bd38', '27a692a4-e2fb-4922-8f74-ec86ea005133')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('ffd0052f-54bb-466d-a76b-61e094bb5b68', '27a692a4-e2fb-4922-8f74-ec86ea005133')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a584c499-d57e-401b-9dcc-fcea05a9554a', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'G', '', '', '', '873a3992-bd23-4f96-90cf-9b0ffca8c0d9', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'f583c0ef-edda-4c2c-99c6-cc1d0d58eb8d')
/
update KSEN_LUI_ATTR set OBJ_ID='2750785d-a970-4626-bb08-9e5631c0dea0', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='873a3992-bd23-4f96-90cf-9b0ffca8c0d9', ATTR_VALUE='0' where ID='4d3176f6-02ad-48e5-8d10-9b0149f12273'
/
update KSEN_LUI set OBJ_ID='1fe9043a-aec3-4f82-83f9-f05c27bdc3a8', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='fece0248-468d-4b08-9fcf-955a6b6b703d', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='873a3992-bd23-4f96-90cf-9b0ffca8c0d9' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='06be480e-d0cd-47f7-b22b-88694f9aecb9', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='873a3992-bd23-4f96-90cf-9b0ffca8c0d9', ATTR_VALUE='0' where ID='96598611-daef-46de-87f3-036f05c32546'
/
update KSEN_LUI_IDENT set OBJ_ID='a584c499-d57e-401b-9dcc-fcea05a9554a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='G', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='873a3992-bd23-4f96-90cf-9b0ffca8c0d9', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='f583c0ef-edda-4c2c-99c6-cc1d0d58eb8d' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='a969ba2d-a22b-4fbd-8cdd-6538c9c9e47a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='873a3992-bd23-4f96-90cf-9b0ffca8c0d9', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='64339611-f5f1-4740-91c7-bc92cc9f9d75' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='6b356db9-2c8c-4cea-bc63-b333a050f3a4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:52:16.557', EXPIR_DT='', LUI_ID='873a3992-bd23-4f96-90cf-9b0ffca8c0d9', PERS_ID='L.REALD', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='3490f2f1-f84f-404a-9890-ca130369614d' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('873a3992-bd23-4f96-90cf-9b0ffca8c0d9', '0c2f1dfb-0b0f-4686-a9d0-83d6781ee0a6')
/
delete from KSEN_LUI_IDENT where ID='4cf56318-432e-4635-8771-801a7c7d577b' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('92349295-60de-4482-bccc-5ab62cee8486', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'b4e91c2b-ae04-46cb-a814-31f92c32246d')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('a9ee83fe-5e3f-4fb7-a160-eb5c9dc6c29a', '0', 'd0ae2526-c132-4654-81e0-570df12c77ed', 'b4e91c2b-ae04-46cb-a814-31f92c32246d', '37cb2d73-c63d-406e-99a2-face3efdf094')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='6ca36cf9-e8ce-43ee-b541-db02f26e4eb6' where ID='56a5e4ba-d46f-48d7-a0b8-6fb6b2eb8449'
/
update KSEN_SCHED_RQST set OBJ_ID='4d0b1631-ccc8-4ebf-be8d-eb60cdf77de0', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for HIST110 - H', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='b4e91c2b-ae04-46cb-a814-31f92c32246d', SCHED_RQST_SET_ID='51b300b2-a195-44cb-9b75-c8b652bc5694' where ID='6ca36cf9-e8ce-43ee-b541-db02f26e4eb6' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='56a5e4ba-d46f-48d7-a0b8-6fb6b2eb8449'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('56a5e4ba-d46f-48d7-a0b8-6fb6b2eb8449', 'af22fa59-42ab-4d09-9a5b-6980f8b4ae7d')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='56a5e4ba-d46f-48d7-a0b8-6fb6b2eb8449'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('56a5e4ba-d46f-48d7-a0b8-6fb6b2eb8449', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='56a5e4ba-d46f-48d7-a0b8-6fb6b2eb8449'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('56a5e4ba-d46f-48d7-a0b8-6fb6b2eb8449', 'd0ae2526-c132-4654-81e0-570df12c77ed')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='56a5e4ba-d46f-48d7-a0b8-6fb6b2eb8449'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('56a5e4ba-d46f-48d7-a0b8-6fb6b2eb8449', '9a64a987-770a-432e-9fcd-8a19e80522f7')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('37cb2d73-c63d-406e-99a2-face3efdf094', '9a64a987-770a-432e-9fcd-8a19e80522f7')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('8e09457f-401d-44e1-94d9-98c0bf2400dc', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'H', '', '', '', '5df66739-494e-4c00-b440-9f9f4ad00e2b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'db739429-b770-4807-9f53-71e5a40b03ea')
/
update KSEN_LUI_ATTR set OBJ_ID='c9b5c37f-4248-492d-af71-a83ff0cd9cd7', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='5df66739-494e-4c00-b440-9f9f4ad00e2b', ATTR_VALUE='0' where ID='ded2fbec-a259-4842-82c2-17428e5373d3'
/
update KSEN_LUI set OBJ_ID='cec659a7-2921-4f11-9861-06e9bd5c4043', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='6be4f320-fa19-46d5-b560-a756adba1c46', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='5df66739-494e-4c00-b440-9f9f4ad00e2b' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='e98dbe31-15a9-492d-a618-2e3441f37718', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='5df66739-494e-4c00-b440-9f9f4ad00e2b', ATTR_VALUE='0' where ID='98ee5f9f-2857-4ca4-b688-187890de25ee'
/
update KSEN_LUI_IDENT set OBJ_ID='8e09457f-401d-44e1-94d9-98c0bf2400dc', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='H', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='5df66739-494e-4c00-b440-9f9f4ad00e2b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='db739429-b770-4807-9f53-71e5a40b03ea' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='4d3ea88a-f6cc-45e6-9531-1a2949bc5fd2', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='5df66739-494e-4c00-b440-9f9f4ad00e2b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='8c902fb2-1aed-452c-99a6-c328ed6476ab' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='cce6e5e3-dc45-4c29-bf10-15608a732b32', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:52:23.318', EXPIR_DT='', LUI_ID='5df66739-494e-4c00-b440-9f9f4ad00e2b', PERS_ID='P.JONATHONS', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='6b471e8d-95c1-42df-b7b9-ad141ca85f95' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('5df66739-494e-4c00-b440-9f9f4ad00e2b', 'b4e91c2b-ae04-46cb-a814-31f92c32246d')
/
delete from KSEN_LUI_IDENT where ID='4fcd6cd8-cda8-4c70-8591-e3469072d3e4' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('3ad81c54-aa27-4632-aded-5dca6228af44', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'b5dd7cb2-4994-4077-bbb4-4f95cbe11e84')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('39433ec2-ebdf-4ff2-a847-a5d84d6f0e0d', '0', '3a65af54-eb98-475e-9970-e8b8408cf750', 'b5dd7cb2-4994-4077-bbb4-4f95cbe11e84', '480b422b-42fe-437c-ada5-7f477e4ef361')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='226ac461-fa5f-4ca8-b0b2-4134a956ee05' where ID='09366370-540c-4cb9-8a35-22f1452430c7'
/
update KSEN_SCHED_RQST set OBJ_ID='4cbdafe2-00fc-4c58-80a6-33591a107754', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for HIST110 - I', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='b5dd7cb2-4994-4077-bbb4-4f95cbe11e84', SCHED_RQST_SET_ID='157f333e-3ff2-4467-af29-2f8c683dff39' where ID='226ac461-fa5f-4ca8-b0b2-4134a956ee05' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='09366370-540c-4cb9-8a35-22f1452430c7'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('09366370-540c-4cb9-8a35-22f1452430c7', 'af22fa59-42ab-4d09-9a5b-6980f8b4ae7d')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='09366370-540c-4cb9-8a35-22f1452430c7'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('09366370-540c-4cb9-8a35-22f1452430c7', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='09366370-540c-4cb9-8a35-22f1452430c7'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('09366370-540c-4cb9-8a35-22f1452430c7', '3a65af54-eb98-475e-9970-e8b8408cf750')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='09366370-540c-4cb9-8a35-22f1452430c7'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('09366370-540c-4cb9-8a35-22f1452430c7', 'b7a44d85-1033-4f98-b33e-ab358cd02fe7')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('480b422b-42fe-437c-ada5-7f477e4ef361', 'b7a44d85-1033-4f98-b33e-ab358cd02fe7')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5af15209-fff1-4767-af24-c388f711fc7b', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'I', '', '', '', 'e2222eee-de96-4e03-aed1-82a9aff7b158', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '5867d06f-08c0-4d18-9d32-0ddcb6ad8dfa')
/
update KSEN_LUI_ATTR set OBJ_ID='46290b5f-8d3c-4942-b148-0353f8bd071a', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='e2222eee-de96-4e03-aed1-82a9aff7b158', ATTR_VALUE='0' where ID='c3f7dc0c-88ef-48ee-99c2-9926d8fd0c10'
/
update KSEN_LUI set OBJ_ID='ab8cce5b-6004-468a-8f84-1ead61a9408c', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='8ac0fe70-2eae-4cb2-850b-e1dd9c8b7b32', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='e2222eee-de96-4e03-aed1-82a9aff7b158' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='b2e04778-32a7-48c0-b1ef-cc3aecfc3b8f', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='e2222eee-de96-4e03-aed1-82a9aff7b158', ATTR_VALUE='0' where ID='1dad8eb0-5181-4748-9677-ed185dd1490b'
/
update KSEN_LUI_IDENT set OBJ_ID='5af15209-fff1-4767-af24-c388f711fc7b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='I', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='e2222eee-de96-4e03-aed1-82a9aff7b158', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='5867d06f-08c0-4d18-9d32-0ddcb6ad8dfa' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='735ed3a2-61d9-4679-93ef-fa35492b1a7b', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='e2222eee-de96-4e03-aed1-82a9aff7b158', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='71292101-5f7d-4633-a469-ac41ae4c7fce' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='4df75564-dc0b-4e4c-b65e-18c9c5edf8de', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:52:30.048', EXPIR_DT='', LUI_ID='e2222eee-de96-4e03-aed1-82a9aff7b158', PERS_ID='O.LEONARDR', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='225b9dc3-ba06-4dd4-8211-880f1123b1c7' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('e2222eee-de96-4e03-aed1-82a9aff7b158', 'b5dd7cb2-4994-4077-bbb4-4f95cbe11e84')
/
delete from KSEN_LUI_IDENT where ID='fee2db7a-9fc8-4a80-97f8-6617bf4f944f' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('f510e299-79a2-417c-9571-27b26e30bae5', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '3a41b34c-3e9e-4285-8a8a-0a587758569d')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('e7c6d72b-a28e-403d-9012-98babf77aab8', '0', '61e06319-cb30-4107-9893-2c17dfe4f6ff', '3a41b34c-3e9e-4285-8a8a-0a587758569d', '19516559-33b6-4305-862e-e9bb585c4c14')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='2d77f963-79f0-4fba-81d7-8471a08f21a8' where ID='541ec21e-ea95-48f0-9fec-5ae4b87d56eb'
/
update KSEN_SCHED_RQST set OBJ_ID='58607404-660f-4fb6-a84b-0996fd8e213f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for HIST110 - J', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='3a41b34c-3e9e-4285-8a8a-0a587758569d', SCHED_RQST_SET_ID='45681c2c-7a1f-40f2-a782-77b17e90f2e7' where ID='2d77f963-79f0-4fba-81d7-8471a08f21a8' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='541ec21e-ea95-48f0-9fec-5ae4b87d56eb'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('541ec21e-ea95-48f0-9fec-5ae4b87d56eb', 'f9419028-0ffb-4f49-bcfd-006a155e5581')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='541ec21e-ea95-48f0-9fec-5ae4b87d56eb'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('541ec21e-ea95-48f0-9fec-5ae4b87d56eb', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='541ec21e-ea95-48f0-9fec-5ae4b87d56eb'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('541ec21e-ea95-48f0-9fec-5ae4b87d56eb', '61e06319-cb30-4107-9893-2c17dfe4f6ff')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='541ec21e-ea95-48f0-9fec-5ae4b87d56eb'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('541ec21e-ea95-48f0-9fec-5ae4b87d56eb', 'c9bbb248-3934-4e28-a043-62038915f797')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('19516559-33b6-4305-862e-e9bb585c4c14', 'c9bbb248-3934-4e28-a043-62038915f797')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('357ebc55-e620-4874-b373-589994597380', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'J', '', '', '', '935a4774-97aa-4289-ab40-0059e05f57f9', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'fb4d0dd6-1318-40dc-aba2-57017a318c14')
/
update KSEN_LUI set OBJ_ID='5b5cc087-2a11-4a02-9317-47dc8425f027', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='4e9f21d1-522f-4b8a-b003-c8807e64609e', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='935a4774-97aa-4289-ab40-0059e05f57f9' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='53985fdf-1463-45f9-82f5-b2712979e591', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='935a4774-97aa-4289-ab40-0059e05f57f9', ATTR_VALUE='0' where ID='1cda95da-f06d-4cdf-8f75-87e3b6b9323b'
/
update KSEN_LUI_ATTR set OBJ_ID='b30a383d-d263-49c0-a8d7-40bee2ad3606', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='935a4774-97aa-4289-ab40-0059e05f57f9', ATTR_VALUE='0' where ID='3ed31517-8231-460d-b9ff-d654b7707eaf'
/
update KSEN_LUI_IDENT set OBJ_ID='357ebc55-e620-4874-b373-589994597380', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='J', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='935a4774-97aa-4289-ab40-0059e05f57f9', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='fb4d0dd6-1318-40dc-aba2-57017a318c14' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='866501a0-85d9-4ee6-bc4d-41dbb8725fe2', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='935a4774-97aa-4289-ab40-0059e05f57f9', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='1e3fe06f-d576-47f5-a280-c43bfc54c543' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='e78f1c25-89bc-42d7-81c4-b14090799a08', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:52:36.502', EXPIR_DT='', LUI_ID='935a4774-97aa-4289-ab40-0059e05f57f9', PERS_ID='M.IRWINS', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='51833ccd-1fe6-49ff-90a7-88ddb3f5b8c4' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('935a4774-97aa-4289-ab40-0059e05f57f9', '3a41b34c-3e9e-4285-8a8a-0a587758569d')
/
delete from KSEN_LUI_IDENT where ID='55256743-9a32-4db6-8737-bdb4857fa177' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('32f8708e-b356-48a2-a751-abc0a46b3d59', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'a1627020-4503-4693-9a25-6e9c95fdafa4')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('9db4339c-c821-47ea-b1a2-c8dabfc6dbe0', '0', 'de71a7e3-2be9-4292-95f9-dd363be9bca7', 'a1627020-4503-4693-9a25-6e9c95fdafa4', '47692fcb-ebf4-43e5-98cf-8acfccb8fcd1')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='d1d89b1f-e264-4b9e-b0e6-e05417440721' where ID='6d8ca124-c4f7-47c7-a12e-7d032f404c91'
/
update KSEN_SCHED_RQST set OBJ_ID='486073bf-226d-42e8-8236-087ac9789926', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - A', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='a1627020-4503-4693-9a25-6e9c95fdafa4', SCHED_RQST_SET_ID='664e68a6-5fee-4a03-b113-469d61b29129' where ID='d1d89b1f-e264-4b9e-b0e6-e05417440721' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='6d8ca124-c4f7-47c7-a12e-7d032f404c91'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('6d8ca124-c4f7-47c7-a12e-7d032f404c91', '4a382e14-f6aa-4fd1-b15a-c259475d212f')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='6d8ca124-c4f7-47c7-a12e-7d032f404c91'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('6d8ca124-c4f7-47c7-a12e-7d032f404c91', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='6d8ca124-c4f7-47c7-a12e-7d032f404c91'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('6d8ca124-c4f7-47c7-a12e-7d032f404c91', 'de71a7e3-2be9-4292-95f9-dd363be9bca7')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='6d8ca124-c4f7-47c7-a12e-7d032f404c91'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('6d8ca124-c4f7-47c7-a12e-7d032f404c91', '0cdb89ea-448b-47db-87ee-e6f2510486a5')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('47692fcb-ebf4-43e5-98cf-8acfccb8fcd1', '0cdb89ea-448b-47db-87ee-e6f2510486a5')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('bdc4bd75-d469-40e3-abcc-577d95bfa5bc', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'A', '', '', '', 'c7db47af-65a5-4c58-a5d8-95deae522701', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '6dedb62c-f5d3-4fa0-8957-9d46de8c19e6')
/
update KSEN_LUI_ATTR set OBJ_ID='a803931a-db7a-4251-8903-0d36f15a58b5', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='c7db47af-65a5-4c58-a5d8-95deae522701', ATTR_VALUE='0' where ID='34113d09-c485-4d5a-90d8-cfa201353025'
/
update KSEN_LUI set OBJ_ID='95d23b6e-6f61-4960-960b-d88ff421df17', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='5e58f009-b2dd-4770-ad41-b750dbdfe8b4', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=288, MIN_SEATS=288, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='c7db47af-65a5-4c58-a5d8-95deae522701' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='472d1723-b5e7-4330-96f4-58c31115a4ec', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='c7db47af-65a5-4c58-a5d8-95deae522701', ATTR_VALUE='0' where ID='a1b52184-3527-4bde-9c4f-744feec65c9f'
/
update KSEN_LUI_IDENT set OBJ_ID='bdc4bd75-d469-40e3-abcc-577d95bfa5bc', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='c7db47af-65a5-4c58-a5d8-95deae522701', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='6dedb62c-f5d3-4fa0-8957-9d46de8c19e6' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='3c98d5f1-05d2-486e-8c2f-568395a2cbbb', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='c7db47af-65a5-4c58-a5d8-95deae522701', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='39e2713e-b040-4ec8-994b-0b8b1cd97c39' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='e3db8ddc-5fdd-44b4-9ff1-e560dfcbe683', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:53:07.608', EXPIR_DT='', LUI_ID='c7db47af-65a5-4c58-a5d8-95deae522701', PERS_ID='G.JOHNE', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='5c39f5dc-b947-446a-951d-800a03ffa5fb' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('c7db47af-65a5-4c58-a5d8-95deae522701', 'a1627020-4503-4693-9a25-6e9c95fdafa4')
/
delete from KSEN_LUI_IDENT where ID='ac53eb83-a391-41e9-b116-481da2dc1b0f' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('f0648a20-57df-4112-a6e2-8f309b28d8f0', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '819c6961-48c3-4900-b2d9-5a5094289c55')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('bb2445ba-2fa7-4653-a29c-e83101ed47cb', '0', '361cd43a-094c-47cd-ab50-6f097191ba22', '819c6961-48c3-4900-b2d9-5a5094289c55', 'c6940752-130c-4f34-a444-529dfc0124ff')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='c224c5a7-9061-488c-a01d-85012759b6e2' where ID='184e1ab8-cb4e-4476-9868-4616cad17081'
/
update KSEN_SCHED_RQST set OBJ_ID='a3761fe0-3082-4d61-a1b7-ebcc68ee833e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - B', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='819c6961-48c3-4900-b2d9-5a5094289c55', SCHED_RQST_SET_ID='d4c53e0b-a6dd-4267-9183-9ba7f2f9745f' where ID='c224c5a7-9061-488c-a01d-85012759b6e2' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='184e1ab8-cb4e-4476-9868-4616cad17081'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('184e1ab8-cb4e-4476-9868-4616cad17081', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='184e1ab8-cb4e-4476-9868-4616cad17081'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('184e1ab8-cb4e-4476-9868-4616cad17081', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='184e1ab8-cb4e-4476-9868-4616cad17081'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('184e1ab8-cb4e-4476-9868-4616cad17081', '361cd43a-094c-47cd-ab50-6f097191ba22')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='184e1ab8-cb4e-4476-9868-4616cad17081'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('184e1ab8-cb4e-4476-9868-4616cad17081', 'fb5e86e5-f0d7-4885-9ab6-4b708150eeae')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('c6940752-130c-4f34-a444-529dfc0124ff', 'fb5e86e5-f0d7-4885-9ab6-4b708150eeae')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('9e95aed0-5ae1-46a5-85bd-305328d2bd9d', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'B', '', '', '', '03b568fc-aeed-4963-bfc9-4f6382d25dec', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '300717e6-f4df-4a87-86fa-728c33c084e4')
/
update KSEN_LUI_ATTR set OBJ_ID='5046b0b5-f9c2-4aee-b218-733c22c8b81d', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='03b568fc-aeed-4963-bfc9-4f6382d25dec', ATTR_VALUE='0' where ID='98dc5291-1414-43ba-a1e3-4268d56e90da'
/
update KSEN_LUI set OBJ_ID='a749bff6-ac08-447d-92ef-1ed723dcf90e', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='3d77a421-8c25-4609-86d5-1dc5e5f3ee5a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='03b568fc-aeed-4963-bfc9-4f6382d25dec' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='68d7bb37-940d-4657-ae47-ddee3b004317', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='03b568fc-aeed-4963-bfc9-4f6382d25dec', ATTR_VALUE='0' where ID='a03d294a-fbec-4cec-a9b8-87ce82fd9ab8'
/
update KSEN_LUI_IDENT set OBJ_ID='9e95aed0-5ae1-46a5-85bd-305328d2bd9d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='03b568fc-aeed-4963-bfc9-4f6382d25dec', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='300717e6-f4df-4a87-86fa-728c33c084e4' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='de21fb10-0d84-41a6-8d57-a142bb4e7f31', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='03b568fc-aeed-4963-bfc9-4f6382d25dec', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b48547fb-07bd-4f8a-8fd2-50f1b65a55d4' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='0042e207-b304-4e37-bc63-ab1d87ecfe5e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:53:23.275', EXPIR_DT='', LUI_ID='03b568fc-aeed-4963-bfc9-4f6382d25dec', PERS_ID='H.DEBORAHK', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='87f415c1-e6b2-4e99-b448-2fec7d4b652c' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('03b568fc-aeed-4963-bfc9-4f6382d25dec', '819c6961-48c3-4900-b2d9-5a5094289c55')
/
delete from KSEN_LUI_IDENT where ID='3e55049e-f936-4fd9-a198-9f9e76882962' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('60b38fd0-1aba-4cc9-bd95-7e4ebeea8405', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '848813f3-2b10-44ed-b2e5-a2dba5850f49')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('8cf0788e-5ee3-456f-9f82-12072e226836', '0', '361cd43a-094c-47cd-ab50-6f097191ba22', '848813f3-2b10-44ed-b2e5-a2dba5850f49', 'cb21ac47-5aa3-47ac-920f-476e563a2125')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='c6ce44bf-0c34-4233-9296-2ad0a2207736' where ID='7d58e56a-c853-4076-8129-2c9c25db82a5'
/
update KSEN_SCHED_RQST set OBJ_ID='f9c523ce-e8c6-4e48-a97e-228728dc7375', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - C', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='848813f3-2b10-44ed-b2e5-a2dba5850f49', SCHED_RQST_SET_ID='4cb1f44d-3c2b-40e2-b37c-eb7b226eb6f5' where ID='c6ce44bf-0c34-4233-9296-2ad0a2207736' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='7d58e56a-c853-4076-8129-2c9c25db82a5'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('7d58e56a-c853-4076-8129-2c9c25db82a5', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='7d58e56a-c853-4076-8129-2c9c25db82a5'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('7d58e56a-c853-4076-8129-2c9c25db82a5', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='7d58e56a-c853-4076-8129-2c9c25db82a5'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('7d58e56a-c853-4076-8129-2c9c25db82a5', '361cd43a-094c-47cd-ab50-6f097191ba22')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='7d58e56a-c853-4076-8129-2c9c25db82a5'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('7d58e56a-c853-4076-8129-2c9c25db82a5', 'acf8e3c8-628a-49f1-af66-6045b4995ab3')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('cb21ac47-5aa3-47ac-920f-476e563a2125', 'acf8e3c8-628a-49f1-af66-6045b4995ab3')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('aaef043e-5c23-43ed-8865-34f01f3760e6', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'C', '', '', '', '980c2079-cf32-4e87-b17d-b826bff9aacd', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '8ac410a2-52e3-4eee-9eea-8bc92eda06e6')
/
update KSEN_LUI_ATTR set OBJ_ID='db068962-acb8-468a-99c1-ac5af0f8ab79', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='980c2079-cf32-4e87-b17d-b826bff9aacd', ATTR_VALUE='0' where ID='2d5ccfa1-16ff-47d5-8b72-a72fc0622e24'
/
update KSEN_LUI set OBJ_ID='c197a64f-11e9-44b4-b044-085f80050415', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='00b2aaf3-5138-4066-a4ed-0d1d78c47f71', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='980c2079-cf32-4e87-b17d-b826bff9aacd' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='730645ae-0220-4759-be83-f8ab9f564245', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='980c2079-cf32-4e87-b17d-b826bff9aacd', ATTR_VALUE='0' where ID='6cde2e4c-5284-4f68-8920-1acce5a0a4f0'
/
update KSEN_LUI_IDENT set OBJ_ID='aaef043e-5c23-43ed-8865-34f01f3760e6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='C', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='980c2079-cf32-4e87-b17d-b826bff9aacd', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8ac410a2-52e3-4eee-9eea-8bc92eda06e6' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='293afcf9-4763-40bb-a9f0-76099c9eee34', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='980c2079-cf32-4e87-b17d-b826bff9aacd', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='64673e45-9a89-43ac-9bd4-21ee93d33762' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='d9e0b24b-f665-494d-88a8-7d145836d17b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:53:38.902', EXPIR_DT='', LUI_ID='980c2079-cf32-4e87-b17d-b826bff9aacd', PERS_ID='W.SARAHM', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='846ff9f0-a887-45d3-bda7-769b24f930cd' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('980c2079-cf32-4e87-b17d-b826bff9aacd', '848813f3-2b10-44ed-b2e5-a2dba5850f49')
/
delete from KSEN_LUI_IDENT where ID='7ba0e634-847c-4876-977a-2f8d6c0fd5e4' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('ccb979a2-411b-4e79-9009-1cbaa27a4836', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '2ed2e8d0-a839-4cde-a3c6-b9aaeab76aff')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('67cf5268-9c89-4f66-bd18-c244c3d022e5', '0', '361cd43a-094c-47cd-ab50-6f097191ba22', '2ed2e8d0-a839-4cde-a3c6-b9aaeab76aff', 'fbc456e6-b159-4b42-8702-fbf1a43e8794')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='bc7b4523-0f6e-4801-8ce2-f35b50e54b64' where ID='af1d12d5-24d1-4ba2-acaa-bc697c5d15b1'
/
update KSEN_SCHED_RQST set OBJ_ID='f2fbece6-1dff-49e7-93cd-f22612eadbd4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - D', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='2ed2e8d0-a839-4cde-a3c6-b9aaeab76aff', SCHED_RQST_SET_ID='2ad98fe8-5f0f-4900-987d-63f9c7e166b9' where ID='bc7b4523-0f6e-4801-8ce2-f35b50e54b64' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='af1d12d5-24d1-4ba2-acaa-bc697c5d15b1'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('af1d12d5-24d1-4ba2-acaa-bc697c5d15b1', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='af1d12d5-24d1-4ba2-acaa-bc697c5d15b1'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('af1d12d5-24d1-4ba2-acaa-bc697c5d15b1', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='af1d12d5-24d1-4ba2-acaa-bc697c5d15b1'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('af1d12d5-24d1-4ba2-acaa-bc697c5d15b1', '361cd43a-094c-47cd-ab50-6f097191ba22')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='af1d12d5-24d1-4ba2-acaa-bc697c5d15b1'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('af1d12d5-24d1-4ba2-acaa-bc697c5d15b1', '9a334036-c58b-44a5-8ae5-3719212f063a')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('fbc456e6-b159-4b42-8702-fbf1a43e8794', '9a334036-c58b-44a5-8ae5-3719212f063a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c110de6f-a6fa-4a87-a970-df58b18e83c9', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'D', '', '', '', 'd6e8f5d5-3b33-4155-8ca0-78c6f983d3f5', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'd1fd7bf3-f183-4a1a-a027-82ecb31af05e')
/
update KSEN_LUI_ATTR set OBJ_ID='c140c19e-0de6-4cb4-96d8-b65de733c8e7', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='d6e8f5d5-3b33-4155-8ca0-78c6f983d3f5', ATTR_VALUE='0' where ID='1c1401b7-366f-42bc-8018-0058445617fb'
/
update KSEN_LUI set OBJ_ID='84257620-3040-450b-95b9-2da3bf43cd9b', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='b425bf1d-87a1-41ff-a84c-f8a2d86e5ecf', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='d6e8f5d5-3b33-4155-8ca0-78c6f983d3f5' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='5d238422-934a-4125-8164-ef2a5c2965ba', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='d6e8f5d5-3b33-4155-8ca0-78c6f983d3f5', ATTR_VALUE='0' where ID='c1d9c48c-e670-4238-8f5d-c77b298a0867'
/
update KSEN_LUI_IDENT set OBJ_ID='c110de6f-a6fa-4a87-a970-df58b18e83c9', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='D', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='d6e8f5d5-3b33-4155-8ca0-78c6f983d3f5', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='d1fd7bf3-f183-4a1a-a027-82ecb31af05e' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='dcb95a08-928e-4fb6-9e00-f09ca8ec95f7', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='d6e8f5d5-3b33-4155-8ca0-78c6f983d3f5', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='d75ca3de-2fdd-4072-b2e5-4cd366b63016' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='4adaf1ac-26df-48f8-9c62-5a232d0364af', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:53:54.453', EXPIR_DT='', LUI_ID='d6e8f5d5-3b33-4155-8ca0-78c6f983d3f5', PERS_ID='B.SUZANNEW', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='0b899411-67de-4ab0-b827-9df909cb57e9' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('d6e8f5d5-3b33-4155-8ca0-78c6f983d3f5', '2ed2e8d0-a839-4cde-a3c6-b9aaeab76aff')
/
delete from KSEN_LUI_IDENT where ID='8ca01d60-c00b-48bb-b5f7-44f2b0bcad35' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('6a6d371e-4c48-4c5d-8131-709dd92d98f3', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'c6d4bddd-cea1-478d-9933-dde4b3872416')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('7237e6db-058a-4bb4-aeab-07752a7c6525', '0', '361cd43a-094c-47cd-ab50-6f097191ba22', 'c6d4bddd-cea1-478d-9933-dde4b3872416', '6ea91d44-0cc0-4573-8adc-f90ab9db6829')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='aaff5d6a-ee9f-4b75-ab0b-d5b464ef772f' where ID='e3a03a4e-29c7-42a1-9101-ea926c4c78b7'
/
update KSEN_SCHED_RQST set OBJ_ID='2c12eb51-4a34-4f56-bd13-d1c21eff61df', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - E', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='c6d4bddd-cea1-478d-9933-dde4b3872416', SCHED_RQST_SET_ID='a8eb3f2f-bdfc-43a9-b827-2fbcc0d40408' where ID='aaff5d6a-ee9f-4b75-ab0b-d5b464ef772f' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='e3a03a4e-29c7-42a1-9101-ea926c4c78b7'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('e3a03a4e-29c7-42a1-9101-ea926c4c78b7', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='e3a03a4e-29c7-42a1-9101-ea926c4c78b7'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('e3a03a4e-29c7-42a1-9101-ea926c4c78b7', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='e3a03a4e-29c7-42a1-9101-ea926c4c78b7'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('e3a03a4e-29c7-42a1-9101-ea926c4c78b7', '361cd43a-094c-47cd-ab50-6f097191ba22')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='e3a03a4e-29c7-42a1-9101-ea926c4c78b7'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('e3a03a4e-29c7-42a1-9101-ea926c4c78b7', '418b13ac-2b6d-443f-9f83-ad5e1843703a')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('6ea91d44-0cc0-4573-8adc-f90ab9db6829', '418b13ac-2b6d-443f-9f83-ad5e1843703a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7fb7adbc-35dc-45a5-9ad7-f8eb16b328cd', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'E', '', '', '', '7ba06dd6-9bc7-4c01-a970-b9b7b381b4d1', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '8f70bf13-2bfa-4273-9b27-da8214ba882d')
/
update KSEN_LUI_ATTR set OBJ_ID='619c5e9c-0160-4e35-8c48-c6f0f2ba2889', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='7ba06dd6-9bc7-4c01-a970-b9b7b381b4d1', ATTR_VALUE='0' where ID='028e1ddb-7474-426f-93a7-63d6d8f6148d'
/
update KSEN_LUI set OBJ_ID='25648dde-a008-4d84-bb3f-3934e62431db', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='2df91c85-1d89-4796-95ec-e491454f35c3', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='7ba06dd6-9bc7-4c01-a970-b9b7b381b4d1' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='33a39cb2-b73c-44e2-b995-eb089c5714b8', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='7ba06dd6-9bc7-4c01-a970-b9b7b381b4d1', ATTR_VALUE='0' where ID='3d4a58df-b9a2-4ae4-9fd7-5cb8bb899acd'
/
update KSEN_LUI_IDENT set OBJ_ID='7fb7adbc-35dc-45a5-9ad7-f8eb16b328cd', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='E', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='7ba06dd6-9bc7-4c01-a970-b9b7b381b4d1', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8f70bf13-2bfa-4273-9b27-da8214ba882d' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='65ec81af-1602-4374-ab17-54bb1fd33b3a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='7ba06dd6-9bc7-4c01-a970-b9b7b381b4d1', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='681eb423-7af9-4324-be3f-bb2634caa724' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='d73d9954-a42e-40c8-a8ee-d87672aa4730', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:54:09.315', EXPIR_DT='', LUI_ID='7ba06dd6-9bc7-4c01-a970-b9b7b381b4d1', PERS_ID='P.KIRSTENF', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='9dc8f0a9-20e6-4ad1-92bb-f9fe063a2167' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('7ba06dd6-9bc7-4c01-a970-b9b7b381b4d1', 'c6d4bddd-cea1-478d-9933-dde4b3872416')
/
delete from KSEN_LUI_IDENT where ID='3cf89feb-faae-4bfe-b078-3203035ebc36' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('49746aec-074a-4d10-9c1d-c811f2477417', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '8d7a6fc8-f33c-4607-bee5-8478e04f2807')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('bc70bb0e-0388-494a-b4b6-35b6af3bfff8', '0', '361cd43a-094c-47cd-ab50-6f097191ba22', '8d7a6fc8-f33c-4607-bee5-8478e04f2807', '4bebf05d-983f-4c03-9b56-cc7321b283c5')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='9e2e4f68-5260-4017-a1a9-fe249ec4a979' where ID='f2b5ff7b-1cea-43b0-95ae-97d23faf51c0'
/
update KSEN_SCHED_RQST set OBJ_ID='7069fa89-bc92-4174-ba22-e431b38b2c9d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - F', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='8d7a6fc8-f33c-4607-bee5-8478e04f2807', SCHED_RQST_SET_ID='c82be104-351d-4ce9-8107-cdfa03638197' where ID='9e2e4f68-5260-4017-a1a9-fe249ec4a979' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='f2b5ff7b-1cea-43b0-95ae-97d23faf51c0'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('f2b5ff7b-1cea-43b0-95ae-97d23faf51c0', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='f2b5ff7b-1cea-43b0-95ae-97d23faf51c0'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('f2b5ff7b-1cea-43b0-95ae-97d23faf51c0', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='f2b5ff7b-1cea-43b0-95ae-97d23faf51c0'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('f2b5ff7b-1cea-43b0-95ae-97d23faf51c0', '361cd43a-094c-47cd-ab50-6f097191ba22')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='f2b5ff7b-1cea-43b0-95ae-97d23faf51c0'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('f2b5ff7b-1cea-43b0-95ae-97d23faf51c0', '59fc0dac-88ed-49ad-ba55-087df2673b19')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('4bebf05d-983f-4c03-9b56-cc7321b283c5', '59fc0dac-88ed-49ad-ba55-087df2673b19')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('8e3f9855-dfa7-4aca-ae3a-0bcce5c94b3e', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'F', '', '', '', '0e30e2b5-3461-452d-831e-8180c3c158d6', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'b95f7d52-1417-4001-8e7c-ecd1dce96290')
/
update KSEN_LUI set OBJ_ID='e436ac6e-e8f3-46e1-8bf9-53be4d0a2cc1', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='d354a5c4-5e0c-4a42-981f-bb00109e165f', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='0e30e2b5-3461-452d-831e-8180c3c158d6' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='f7ff23db-fc11-4246-91f1-b8a9f38754ab', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='0e30e2b5-3461-452d-831e-8180c3c158d6', ATTR_VALUE='0' where ID='eb92cf0a-526b-4898-b678-97eef6366c41'
/
update KSEN_LUI_ATTR set OBJ_ID='1ac0e41a-cbd0-467e-a21d-d9730ef05aa7', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='0e30e2b5-3461-452d-831e-8180c3c158d6', ATTR_VALUE='0' where ID='863d58b4-41d3-4962-b89e-70f3e0a59339'
/
update KSEN_LUI_IDENT set OBJ_ID='8e3f9855-dfa7-4aca-ae3a-0bcce5c94b3e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='F', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='0e30e2b5-3461-452d-831e-8180c3c158d6', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b95f7d52-1417-4001-8e7c-ecd1dce96290' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='bffaa670-7b6e-44ec-9ab8-f3aa19f2b4d4', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='0e30e2b5-3461-452d-831e-8180c3c158d6', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='60d1777f-4a89-48d0-9532-9be150915696' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='10249f5d-f7be-44ec-9c42-b23ba6fd896f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:54:24.353', EXPIR_DT='', LUI_ID='0e30e2b5-3461-452d-831e-8180c3c158d6', PERS_ID='S.RIZZAC', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='c3d9daba-c853-475f-84d7-269e079ab361' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('0e30e2b5-3461-452d-831e-8180c3c158d6', '8d7a6fc8-f33c-4607-bee5-8478e04f2807')
/
delete from KSEN_LUI_IDENT where ID='8fa15522-1fae-490a-ac4a-f03f293d33f3' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('6263e59e-748c-49ae-a7c3-4000c63b8af3', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'b283579a-a2ff-4cec-9557-ad3ffe53f43a')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('dfac8dba-7af9-432e-a58e-44b0b6f650d8', '0', '361cd43a-094c-47cd-ab50-6f097191ba22', 'b283579a-a2ff-4cec-9557-ad3ffe53f43a', '0422c4ce-f27f-47e2-aa1c-29efc14bdc30')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='985b230f-6e4c-489e-8995-b3a246a7d68f' where ID='40a456e3-dcc7-4fbe-9a27-ae0e028dd8de'
/
update KSEN_SCHED_RQST set OBJ_ID='204fa700-6f90-41ec-bc63-70b8dd0d9641', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - G', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='b283579a-a2ff-4cec-9557-ad3ffe53f43a', SCHED_RQST_SET_ID='fd6d5b0b-4446-4d8a-9027-2974ef5def2a' where ID='985b230f-6e4c-489e-8995-b3a246a7d68f' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='40a456e3-dcc7-4fbe-9a27-ae0e028dd8de'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('40a456e3-dcc7-4fbe-9a27-ae0e028dd8de', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='40a456e3-dcc7-4fbe-9a27-ae0e028dd8de'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('40a456e3-dcc7-4fbe-9a27-ae0e028dd8de', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='40a456e3-dcc7-4fbe-9a27-ae0e028dd8de'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('40a456e3-dcc7-4fbe-9a27-ae0e028dd8de', '361cd43a-094c-47cd-ab50-6f097191ba22')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='40a456e3-dcc7-4fbe-9a27-ae0e028dd8de'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('40a456e3-dcc7-4fbe-9a27-ae0e028dd8de', '433e7ad2-88f6-409e-b153-1373c3e0ab12')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('0422c4ce-f27f-47e2-aa1c-29efc14bdc30', '433e7ad2-88f6-409e-b153-1373c3e0ab12')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('0813a3e7-dfa2-401a-93ed-d6e84d5c47a3', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'G', '', '', '', '51ad321e-0d2e-40e4-959d-87ff6d1e8215', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a796de6d-0058-4bb9-af62-d1cd35cee9ed')
/
update KSEN_LUI_ATTR set OBJ_ID='1fdba1bb-1379-46a5-beb1-a0303714f3ad', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='51ad321e-0d2e-40e4-959d-87ff6d1e8215', ATTR_VALUE='0' where ID='a4319afb-c7ac-4540-ae55-1a0b3a2a57cc'
/
update KSEN_LUI set OBJ_ID='25c196be-cdbc-4d3e-8d68-d2b12bf1c383', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='4bdec9e4-f92c-4130-befc-9a5672355c0b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='51ad321e-0d2e-40e4-959d-87ff6d1e8215' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='3ca758c1-9feb-41e9-96ac-42bb61053e58', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='51ad321e-0d2e-40e4-959d-87ff6d1e8215', ATTR_VALUE='0' where ID='48b17fd7-3833-4da4-96a7-01ccdeea219d'
/
update KSEN_LUI_IDENT set OBJ_ID='0813a3e7-dfa2-401a-93ed-d6e84d5c47a3', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='G', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='51ad321e-0d2e-40e4-959d-87ff6d1e8215', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a796de6d-0058-4bb9-af62-d1cd35cee9ed' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='ee673a6b-b0a2-414a-a212-cdf4005a27ae', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='51ad321e-0d2e-40e4-959d-87ff6d1e8215', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='d601bf44-451a-4619-b8f7-fb6c2b0daad0' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='8544b338-9bd4-4977-afbd-b9e7cf31a912', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:54:40.06', EXPIR_DT='', LUI_ID='51ad321e-0d2e-40e4-959d-87ff6d1e8215', PERS_ID='G.JEFFREYB', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='de7461dd-0e42-42be-a17e-5fafbb906d6d' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('51ad321e-0d2e-40e4-959d-87ff6d1e8215', 'b283579a-a2ff-4cec-9557-ad3ffe53f43a')
/
delete from KSEN_LUI_IDENT where ID='8990602f-fe39-45e7-be9b-bd0f8576d9ae' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('3cd84192-321d-4d3b-89b1-eb925d6e1168', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'e5a2e43c-3039-406b-8811-b96b1005cc97')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('9e8ef0bc-dd3e-460c-83c6-ae2b7c204742', '0', '361cd43a-094c-47cd-ab50-6f097191ba22', 'e5a2e43c-3039-406b-8811-b96b1005cc97', 'a0186fe7-27eb-4137-bf4e-b8128990a80d')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='7a93fdff-297d-4b06-b7b2-c9abdef70dee' where ID='9c24653b-cc06-47da-b76f-fd77ca6b5a25'
/
update KSEN_SCHED_RQST set OBJ_ID='42d07284-bb00-4108-a44f-db1f41c71a2c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - H', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='e5a2e43c-3039-406b-8811-b96b1005cc97', SCHED_RQST_SET_ID='65831eec-44e0-4d60-b430-cfa67436a446' where ID='7a93fdff-297d-4b06-b7b2-c9abdef70dee' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='9c24653b-cc06-47da-b76f-fd77ca6b5a25'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('9c24653b-cc06-47da-b76f-fd77ca6b5a25', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='9c24653b-cc06-47da-b76f-fd77ca6b5a25'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('9c24653b-cc06-47da-b76f-fd77ca6b5a25', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='9c24653b-cc06-47da-b76f-fd77ca6b5a25'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('9c24653b-cc06-47da-b76f-fd77ca6b5a25', '361cd43a-094c-47cd-ab50-6f097191ba22')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='9c24653b-cc06-47da-b76f-fd77ca6b5a25'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('9c24653b-cc06-47da-b76f-fd77ca6b5a25', '80a4b700-b893-4aac-bcdb-541efbe38d14')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('a0186fe7-27eb-4137-bf4e-b8128990a80d', '80a4b700-b893-4aac-bcdb-541efbe38d14')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('cdc31a1c-5925-45dc-982f-c2f75be8984d', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'H', '', '', '', '1f9559e1-30b5-4a99-ac81-ce6d7de4871a', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '97d16d2b-d89a-4724-bfe6-9c4cc47f43a2')
/
update KSEN_LUI set OBJ_ID='5fcb4c5e-0192-4792-9e0d-bcb57e0beda6', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='495b649b-0671-4e0f-9ec2-7adcdaa435e6', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='1f9559e1-30b5-4a99-ac81-ce6d7de4871a' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='640e0d7b-ac4d-4330-8b02-d08d6c4fa285', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='1f9559e1-30b5-4a99-ac81-ce6d7de4871a', ATTR_VALUE='0' where ID='e28a6805-06eb-43ce-b575-0e3ab345a335'
/
update KSEN_LUI_ATTR set OBJ_ID='0ad03311-2171-4e77-b7b1-10260b2e03f8', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='1f9559e1-30b5-4a99-ac81-ce6d7de4871a', ATTR_VALUE='0' where ID='e9de4b54-33b3-40cd-b4e3-8b2760909c7a'
/
update KSEN_LUI_IDENT set OBJ_ID='cdc31a1c-5925-45dc-982f-c2f75be8984d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='H', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='1f9559e1-30b5-4a99-ac81-ce6d7de4871a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='97d16d2b-d89a-4724-bfe6-9c4cc47f43a2' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='0951c371-2aac-495b-8e7a-11dc79307902', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='1f9559e1-30b5-4a99-ac81-ce6d7de4871a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='8a53e9bb-272c-4963-884c-aee542ae362e' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='e830938a-0e2a-4944-bd41-4b843f84065e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:54:56.399', EXPIR_DT='', LUI_ID='1f9559e1-30b5-4a99-ac81-ce6d7de4871a', PERS_ID='K.BOBBIEB', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='0f32c573-bf26-4bf9-b087-ffd820b883f4' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('1f9559e1-30b5-4a99-ac81-ce6d7de4871a', 'e5a2e43c-3039-406b-8811-b96b1005cc97')
/
delete from KSEN_LUI_IDENT where ID='893da071-39ae-4d85-8754-24ad8e7a8665' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('1156190f-e6f4-4d0c-b845-4cfc62aaeec7', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '89144c9f-1995-4546-813d-dc4bbee2db65')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('390ca006-4d2e-43c9-9d4d-e83cd5d4940d', '0', '361cd43a-094c-47cd-ab50-6f097191ba22', '89144c9f-1995-4546-813d-dc4bbee2db65', 'd6e857ca-7418-4e42-8697-ff04c458ce2f')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='a58aeec7-a8a8-4e90-9174-72b78180bd5f' where ID='e0b77228-e364-4081-97f0-e915ca1ac9ca'
/
update KSEN_SCHED_RQST set OBJ_ID='629cdfe2-84d7-40d2-85e5-48f2562d1f1c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - I', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='89144c9f-1995-4546-813d-dc4bbee2db65', SCHED_RQST_SET_ID='1647f67a-0029-4170-8af9-258298fbec40' where ID='a58aeec7-a8a8-4e90-9174-72b78180bd5f' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='e0b77228-e364-4081-97f0-e915ca1ac9ca'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('e0b77228-e364-4081-97f0-e915ca1ac9ca', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='e0b77228-e364-4081-97f0-e915ca1ac9ca'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('e0b77228-e364-4081-97f0-e915ca1ac9ca', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='e0b77228-e364-4081-97f0-e915ca1ac9ca'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('e0b77228-e364-4081-97f0-e915ca1ac9ca', '361cd43a-094c-47cd-ab50-6f097191ba22')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='e0b77228-e364-4081-97f0-e915ca1ac9ca'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('e0b77228-e364-4081-97f0-e915ca1ac9ca', 'ef49f2d7-ca71-4084-9649-9c74a3c09d0e')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('d6e857ca-7418-4e42-8697-ff04c458ce2f', 'ef49f2d7-ca71-4084-9649-9c74a3c09d0e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d6475fb7-785c-43a4-938e-153ef23d7524', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'I', '', '', '', '25719896-416f-41b0-91cf-f7dced522b4c', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7fffea3e-dbfa-4472-9c05-a99451fc2934')
/
update KSEN_LUI set OBJ_ID='577996b2-55a0-4141-a2b9-94270cc1c2ef', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='4156f3f4-2fec-427c-9643-f5601a4c0d0a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='25719896-416f-41b0-91cf-f7dced522b4c' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='72c88b10-65d4-4b69-9fed-a06472ff68c4', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='25719896-416f-41b0-91cf-f7dced522b4c', ATTR_VALUE='0' where ID='b776a780-37c0-4416-823d-6430e5821a44'
/
update KSEN_LUI_ATTR set OBJ_ID='b7a3dc28-3e0f-4a49-a448-186d304ea5aa', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='25719896-416f-41b0-91cf-f7dced522b4c', ATTR_VALUE='0' where ID='9581e22e-7de0-483b-b0de-74c90fc6356f'
/
update KSEN_LUI_IDENT set OBJ_ID='d6475fb7-785c-43a4-938e-153ef23d7524', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='I', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='25719896-416f-41b0-91cf-f7dced522b4c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='7fffea3e-dbfa-4472-9c05-a99451fc2934' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='8157058f-0c39-41f4-90e4-77f601c159b1', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='25719896-416f-41b0-91cf-f7dced522b4c', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='5550d375-d004-459a-913b-56fabca332ae' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='5c9e66c5-b838-4428-adfd-68586de85cae', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:55:12.427', EXPIR_DT='', LUI_ID='25719896-416f-41b0-91cf-f7dced522b4c', PERS_ID='G.THEODOREG', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='7f2f80e0-f4d2-4d40-a7ad-d7ba2a737eca' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('25719896-416f-41b0-91cf-f7dced522b4c', '89144c9f-1995-4546-813d-dc4bbee2db65')
/
delete from KSEN_LUI_IDENT where ID='67f574fb-a84d-453f-93c0-e8019a4656d4' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('eecd24a0-fa98-4206-aaa6-d154504e19b1', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '4146075b-12a9-4128-a7a4-9eb576675d02')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('df9c9a9d-efbd-4527-a270-d2447a798121', '0', '361cd43a-094c-47cd-ab50-6f097191ba22', '4146075b-12a9-4128-a7a4-9eb576675d02', '384cabda-d777-4e6f-bace-fe8286dd1c84')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='46b8fbb8-4a0d-4d7b-8394-c17bcf74de66' where ID='e2bf92ab-c33e-4d65-ae34-31781e323924'
/
update KSEN_SCHED_RQST set OBJ_ID='22b25ede-d03c-499a-8f00-2924a4e65093', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - J', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='4146075b-12a9-4128-a7a4-9eb576675d02', SCHED_RQST_SET_ID='07cab7eb-bfe4-4289-92f9-b6a693a0324c' where ID='46b8fbb8-4a0d-4d7b-8394-c17bcf74de66' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='e2bf92ab-c33e-4d65-ae34-31781e323924'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('e2bf92ab-c33e-4d65-ae34-31781e323924', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='e2bf92ab-c33e-4d65-ae34-31781e323924'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('e2bf92ab-c33e-4d65-ae34-31781e323924', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='e2bf92ab-c33e-4d65-ae34-31781e323924'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('e2bf92ab-c33e-4d65-ae34-31781e323924', '361cd43a-094c-47cd-ab50-6f097191ba22')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='e2bf92ab-c33e-4d65-ae34-31781e323924'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('e2bf92ab-c33e-4d65-ae34-31781e323924', '959e52db-4d52-424f-a0f5-3855472cde37')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('384cabda-d777-4e6f-bace-fe8286dd1c84', '959e52db-4d52-424f-a0f5-3855472cde37')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a6a822fa-08dd-4a34-9f1f-0f25c00cf469', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'J', '', '', '', '2d8c513f-6273-4a98-9cbd-5519c1857804', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a45c45ec-14ca-4ec3-af0f-f4a1e083c86e')
/
update KSEN_LUI_ATTR set OBJ_ID='5c5e3a11-eb2e-4d4a-b95d-d508a450ae5a', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='2d8c513f-6273-4a98-9cbd-5519c1857804', ATTR_VALUE='0' where ID='82595d87-947a-43ee-82fb-15f44ef2de4e'
/
update KSEN_LUI set OBJ_ID='3b177dd7-dac0-4c4e-9082-5aaf383ed35e', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='53b1ee34-2625-48cb-b183-c9b8893ac132', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='2d8c513f-6273-4a98-9cbd-5519c1857804' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='4c271ef8-4a09-4f13-abb9-3ba324fc7f39', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='2d8c513f-6273-4a98-9cbd-5519c1857804', ATTR_VALUE='0' where ID='69da64f1-524b-4ce5-a5e1-613028160f71'
/
update KSEN_LUI_IDENT set OBJ_ID='a6a822fa-08dd-4a34-9f1f-0f25c00cf469', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='J', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='2d8c513f-6273-4a98-9cbd-5519c1857804', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a45c45ec-14ca-4ec3-af0f-f4a1e083c86e' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='0575eda0-d38c-48b3-9c26-fbbd2a774f92', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='2d8c513f-6273-4a98-9cbd-5519c1857804', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='432a600d-0410-4760-8a40-799d8fe20a6a' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='3a94a0ff-7901-407c-9d8c-0f04b800cfa1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:55:28.917', EXPIR_DT='', LUI_ID='2d8c513f-6273-4a98-9cbd-5519c1857804', PERS_ID='B.NICOLEG', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='128eccd8-f65d-45ad-8f36-62a6aa377c3b' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('2d8c513f-6273-4a98-9cbd-5519c1857804', '4146075b-12a9-4128-a7a4-9eb576675d02')
/
delete from KSEN_LUI_IDENT where ID='2e493156-63d6-4b82-8d80-79e14f380a32' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('1b10ff81-f3ab-4b3f-b28a-ee1ee8cbc052', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'c1f25fb5-3049-4fc4-bb33-6836961de593')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('497f7062-1474-4962-a079-8338b0f6a304', '0', '361cd43a-094c-47cd-ab50-6f097191ba22', 'c1f25fb5-3049-4fc4-bb33-6836961de593', '94f4cd31-b3d7-4351-9d27-9417fc42822d')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='c58fd2c4-397e-4570-8a9d-1f8870d1d284' where ID='b209a591-5a76-4b6d-94fa-72fd0dc54075'
/
update KSEN_SCHED_RQST set OBJ_ID='dcb024f9-8160-4b9e-8d3a-ea60b06ca414', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - K', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='c1f25fb5-3049-4fc4-bb33-6836961de593', SCHED_RQST_SET_ID='32d3a989-b11f-4410-9174-69f8958a0173' where ID='c58fd2c4-397e-4570-8a9d-1f8870d1d284' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='b209a591-5a76-4b6d-94fa-72fd0dc54075'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('b209a591-5a76-4b6d-94fa-72fd0dc54075', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='b209a591-5a76-4b6d-94fa-72fd0dc54075'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('b209a591-5a76-4b6d-94fa-72fd0dc54075', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='b209a591-5a76-4b6d-94fa-72fd0dc54075'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('b209a591-5a76-4b6d-94fa-72fd0dc54075', '361cd43a-094c-47cd-ab50-6f097191ba22')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='b209a591-5a76-4b6d-94fa-72fd0dc54075'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('b209a591-5a76-4b6d-94fa-72fd0dc54075', '2663c361-05aa-45c6-a776-e04eb9f4c941')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('94f4cd31-b3d7-4351-9d27-9417fc42822d', '2663c361-05aa-45c6-a776-e04eb9f4c941')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('61444c59-9278-4e60-b05d-ba94a3442169', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'K', '', '', '', '188f9ca9-ecbc-45a7-a2ea-95962bb9951b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a8ab504f-c232-4949-bb73-a0ea2f4fa8a5')
/
update KSEN_LUI_ATTR set OBJ_ID='dd734e50-a6cd-4421-b5cb-fd35f31359a2', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='188f9ca9-ecbc-45a7-a2ea-95962bb9951b', ATTR_VALUE='0' where ID='3ad29dcb-3dc0-47bd-8ae7-a4e1baff489f'
/
update KSEN_LUI set OBJ_ID='7576fe12-0bb8-4ea0-a431-25c479c7438f', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='7f44062b-35d1-403f-ab5e-45edca85c4db', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='188f9ca9-ecbc-45a7-a2ea-95962bb9951b' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='4f312ab2-53cd-4859-9a86-305b9dc026e2', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='188f9ca9-ecbc-45a7-a2ea-95962bb9951b', ATTR_VALUE='0' where ID='93a8f2d8-8e99-46f3-8fd9-f7ef98b778cd'
/
update KSEN_LUI_IDENT set OBJ_ID='61444c59-9278-4e60-b05d-ba94a3442169', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='K', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='188f9ca9-ecbc-45a7-a2ea-95962bb9951b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a8ab504f-c232-4949-bb73-a0ea2f4fa8a5' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='4de57da3-eda8-4f97-8f43-b0b74a2646af', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='188f9ca9-ecbc-45a7-a2ea-95962bb9951b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='26d93a3a-1198-4ba0-baec-87c8b75be3e5' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='ba014e8c-1544-49d3-b023-2814c6577a87', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:55:45.404', EXPIR_DT='', LUI_ID='188f9ca9-ecbc-45a7-a2ea-95962bb9951b', PERS_ID='S.YANNINGW', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='389b48c2-5408-4af5-b14e-1307d3c5847b' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('188f9ca9-ecbc-45a7-a2ea-95962bb9951b', 'c1f25fb5-3049-4fc4-bb33-6836961de593')
/
delete from KSEN_LUI_IDENT where ID='4d78cf26-3c69-4b1a-af77-e4c152164ccd' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('81b6e323-ac79-4dd6-bf50-952735640312', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'c61844d4-ae58-4092-a03c-828ec7a09456')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('a14fdf5d-36e8-4504-8387-7d55192115f1', '0', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2', 'c61844d4-ae58-4092-a03c-828ec7a09456', '716cfbb6-ed9c-424b-ad1f-2e735fe211fe')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='8977cfee-d627-4ead-a103-62a92d3e04bb' where ID='bbe500a7-a85c-441f-ae6f-b046186316ba'
/
update KSEN_SCHED_RQST set OBJ_ID='fa43b8da-f180-40ae-be9f-75a586d2e289', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - L', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='c61844d4-ae58-4092-a03c-828ec7a09456', SCHED_RQST_SET_ID='6751084c-bf84-480a-88a7-422db522ebf4' where ID='8977cfee-d627-4ead-a103-62a92d3e04bb' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='bbe500a7-a85c-441f-ae6f-b046186316ba'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('bbe500a7-a85c-441f-ae6f-b046186316ba', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='bbe500a7-a85c-441f-ae6f-b046186316ba'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('bbe500a7-a85c-441f-ae6f-b046186316ba', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='bbe500a7-a85c-441f-ae6f-b046186316ba'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('bbe500a7-a85c-441f-ae6f-b046186316ba', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='bbe500a7-a85c-441f-ae6f-b046186316ba'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('bbe500a7-a85c-441f-ae6f-b046186316ba', '47738a4b-dd20-4a13-939c-a019cff9ab00')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('716cfbb6-ed9c-424b-ad1f-2e735fe211fe', '47738a4b-dd20-4a13-939c-a019cff9ab00')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('3df570ad-8e23-41d5-ba63-7b7a8186f09b', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'L', '', '', '', '126b8493-6398-48f9-9435-8438eb096a8c', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '788567a2-e91e-49c3-a945-c53bade523e5')
/
update KSEN_LUI set OBJ_ID='0c0cb5a5-a94f-487b-8408-2a13564cff08', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='dbe4aeed-3eec-4369-8faf-202c25c8cd1c', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='126b8493-6398-48f9-9435-8438eb096a8c' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='fbc65875-4a5d-4e86-b850-4474bb759417', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='126b8493-6398-48f9-9435-8438eb096a8c', ATTR_VALUE='0' where ID='81443615-fd75-4a37-be25-aaa5d4a2ab1d'
/
update KSEN_LUI_ATTR set OBJ_ID='9581ae0f-404e-47db-b250-8777a86f3d34', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='126b8493-6398-48f9-9435-8438eb096a8c', ATTR_VALUE='0' where ID='caa66020-e9c7-473f-8588-3e9ba4894c2a'
/
update KSEN_LUI_IDENT set OBJ_ID='3df570ad-8e23-41d5-ba63-7b7a8186f09b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='L', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='126b8493-6398-48f9-9435-8438eb096a8c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='788567a2-e91e-49c3-a945-c53bade523e5' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='5a15076e-d8e3-4c3b-b9fa-2c85efaf9506', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='126b8493-6398-48f9-9435-8438eb096a8c', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b944f453-6bb8-4d4b-b2bf-10b9e8c0e02d' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='5bc6238a-650d-46da-80b7-ecdba764cb18', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:56:02.129', EXPIR_DT='', LUI_ID='126b8493-6398-48f9-9435-8438eb096a8c', PERS_ID='M.KARIC', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='02cc7b60-9814-40bd-b887-700b323e49eb' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('126b8493-6398-48f9-9435-8438eb096a8c', 'c61844d4-ae58-4092-a03c-828ec7a09456')
/
delete from KSEN_LUI_IDENT where ID='c2e8d5a0-91ba-4abc-8c53-f901d3a82a7a' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('b0fb0231-fb87-417b-9b32-fed0ee39be62', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '17d26435-70a2-4780-bdea-5fc5ac632ef5')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('a3e371d5-5cd7-40ba-8238-2e9a5291c6a9', '0', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2', '17d26435-70a2-4780-bdea-5fc5ac632ef5', 'e5b2a2ae-7636-4b09-b753-dfcbc3937fbc')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='3803a755-9002-4325-ad00-6a5980620a2d' where ID='f291dc33-cb65-4fa0-a48b-50b139d7b8c9'
/
update KSEN_SCHED_RQST set OBJ_ID='e1ec528d-508d-4bc7-a886-d956a5d15eb4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - M', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='17d26435-70a2-4780-bdea-5fc5ac632ef5', SCHED_RQST_SET_ID='2887c065-9823-4164-bd13-637f6c15cd50' where ID='3803a755-9002-4325-ad00-6a5980620a2d' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='f291dc33-cb65-4fa0-a48b-50b139d7b8c9'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('f291dc33-cb65-4fa0-a48b-50b139d7b8c9', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='f291dc33-cb65-4fa0-a48b-50b139d7b8c9'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('f291dc33-cb65-4fa0-a48b-50b139d7b8c9', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='f291dc33-cb65-4fa0-a48b-50b139d7b8c9'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('f291dc33-cb65-4fa0-a48b-50b139d7b8c9', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='f291dc33-cb65-4fa0-a48b-50b139d7b8c9'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('f291dc33-cb65-4fa0-a48b-50b139d7b8c9', '86f51ebc-3567-4187-83ea-a89ab913672b')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('e5b2a2ae-7636-4b09-b753-dfcbc3937fbc', '86f51ebc-3567-4187-83ea-a89ab913672b')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('1f520ee6-ae72-4755-97a1-def50cf970bb', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'M', '', '', '', '22d6029e-55b6-4be8-85db-e44fda078c14', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'cd3142ea-dacf-4dd2-b69f-c369a292ca79')
/
update KSEN_LUI_ATTR set OBJ_ID='13747fcd-fde6-4863-8780-d7b0a19249e9', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='22d6029e-55b6-4be8-85db-e44fda078c14', ATTR_VALUE='0' where ID='df99eb9d-92a2-433a-a364-16acf1dede62'
/
update KSEN_LUI set OBJ_ID='26a0b280-54ec-4737-beb2-9f67e943a414', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='70bbae2d-4ac6-4842-94ae-61ceea55cb0a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='22d6029e-55b6-4be8-85db-e44fda078c14' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='37f70220-c804-4cf8-9f54-bf0247edb94f', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='22d6029e-55b6-4be8-85db-e44fda078c14', ATTR_VALUE='0' where ID='d66504c9-116e-4ce8-afc1-ffa0a81a2693'
/
update KSEN_LUI_IDENT set OBJ_ID='1f520ee6-ae72-4755-97a1-def50cf970bb', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='M', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='22d6029e-55b6-4be8-85db-e44fda078c14', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='cd3142ea-dacf-4dd2-b69f-c369a292ca79' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='3fd4fc60-5e10-4fce-be2e-0a2930bd1a0a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='22d6029e-55b6-4be8-85db-e44fda078c14', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='0ed77982-7e52-4c79-8124-76dc5bab3d5d' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='2b01b6c4-b836-4634-8ce5-9d41c952c460', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:56:19.197', EXPIR_DT='', LUI_ID='22d6029e-55b6-4be8-85db-e44fda078c14', PERS_ID='W.MATTM', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='b7fc5489-68f2-4361-bc43-f8309e22502c' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('22d6029e-55b6-4be8-85db-e44fda078c14', '17d26435-70a2-4780-bdea-5fc5ac632ef5')
/
delete from KSEN_LUI_IDENT where ID='e3f6c070-239f-4e9a-9e73-325e87968666' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('047c4e1c-bbf4-4a7f-80c0-d3c02dedbb2a', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'a721103b-99a3-4224-8ae4-e1b960ee0387')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('21f9de3c-5aae-4629-8566-285608ef99e1', '0', 'de71a7e3-2be9-4292-95f9-dd363be9bca7', 'a721103b-99a3-4224-8ae4-e1b960ee0387', 'ec048e40-116c-4d22-af62-d2051f3451cd')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='d695e2f0-9068-4af0-a77f-feb9e4217446' where ID='60e99bf7-9a93-4ac5-8879-3a240fc076be'
/
update KSEN_SCHED_RQST set OBJ_ID='59370fd4-aebd-4d05-ad8e-396393ae231e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - N', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='a721103b-99a3-4224-8ae4-e1b960ee0387', SCHED_RQST_SET_ID='0270491f-d122-4ecf-861d-75287db59ab7' where ID='d695e2f0-9068-4af0-a77f-feb9e4217446' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='60e99bf7-9a93-4ac5-8879-3a240fc076be'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('60e99bf7-9a93-4ac5-8879-3a240fc076be', '4a382e14-f6aa-4fd1-b15a-c259475d212f')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='60e99bf7-9a93-4ac5-8879-3a240fc076be'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('60e99bf7-9a93-4ac5-8879-3a240fc076be', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='60e99bf7-9a93-4ac5-8879-3a240fc076be'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('60e99bf7-9a93-4ac5-8879-3a240fc076be', 'de71a7e3-2be9-4292-95f9-dd363be9bca7')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='60e99bf7-9a93-4ac5-8879-3a240fc076be'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('60e99bf7-9a93-4ac5-8879-3a240fc076be', 'd4ad171b-b353-400e-a620-7b1b1c5cdbef')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('ec048e40-116c-4d22-af62-d2051f3451cd', 'd4ad171b-b353-400e-a620-7b1b1c5cdbef')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('caf6c4ed-6109-437a-9c2a-962378d9b8c9', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'N', '', '', '', '463279c1-d545-4743-b72e-c8d84bac1dbe', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '322a32cc-2811-4161-a259-2c6cc904bb8f')
/
update KSEN_LUI_ATTR set OBJ_ID='a9779da1-0dc1-482a-9529-a783ed5647c3', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='463279c1-d545-4743-b72e-c8d84bac1dbe', ATTR_VALUE='0' where ID='19809a2c-ad08-4b3a-bf54-b49d18490c3a'
/
update KSEN_LUI set OBJ_ID='834808fd-bf0a-468e-8421-1c0a87bea44a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='7f9a588a-faaa-44fe-92c1-41ecbc0fcea3', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=240, MIN_SEATS=240, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='463279c1-d545-4743-b72e-c8d84bac1dbe' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='95810750-e490-438e-aeb1-8a83731a5519', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='463279c1-d545-4743-b72e-c8d84bac1dbe', ATTR_VALUE='0' where ID='77cb4b2b-80d7-42d1-bb38-0e6e1bcf5a0d'
/
update KSEN_LUI_IDENT set OBJ_ID='caf6c4ed-6109-437a-9c2a-962378d9b8c9', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='N', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='463279c1-d545-4743-b72e-c8d84bac1dbe', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='322a32cc-2811-4161-a259-2c6cc904bb8f' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='ba29ac2c-416e-4afd-aa2b-9297112a4003', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='463279c1-d545-4743-b72e-c8d84bac1dbe', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='1486d30e-55e8-456e-b897-ffae38ca5d6a' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='7e302c75-08bd-4fbe-91de-9df6db3eb20e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:56:36.315', EXPIR_DT='', LUI_ID='463279c1-d545-4743-b72e-c8d84bac1dbe', PERS_ID='C.PAMELAP', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='3c2957ad-8930-4f36-8b62-ffb0b465928d' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('463279c1-d545-4743-b72e-c8d84bac1dbe', 'a721103b-99a3-4224-8ae4-e1b960ee0387')
/
delete from KSEN_LUI_IDENT where ID='c219920c-cce7-4e00-9426-7fdb12f517c3' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('b65ab6e4-eb10-4578-adc7-960f10592436', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'aa8ee9ba-3888-4e7e-bd94-fb9b26c5024e')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('2b9dda73-6d5c-489e-b4df-c1753af29a52', '0', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2', 'aa8ee9ba-3888-4e7e-bd94-fb9b26c5024e', 'c6f86db6-726c-43fe-be5a-cf0ec2d12f2e')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='289308de-4a0d-4aca-8210-91bfa1e29e20' where ID='f25ef008-1074-4273-aa0a-f3727be27bae'
/
update KSEN_SCHED_RQST set OBJ_ID='763f0294-a5e7-4660-b390-fd7b0c626443', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - O', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='aa8ee9ba-3888-4e7e-bd94-fb9b26c5024e', SCHED_RQST_SET_ID='7d6968aa-ade0-4783-8af0-4a91031df61d' where ID='289308de-4a0d-4aca-8210-91bfa1e29e20' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='f25ef008-1074-4273-aa0a-f3727be27bae'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('f25ef008-1074-4273-aa0a-f3727be27bae', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='f25ef008-1074-4273-aa0a-f3727be27bae'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('f25ef008-1074-4273-aa0a-f3727be27bae', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='f25ef008-1074-4273-aa0a-f3727be27bae'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('f25ef008-1074-4273-aa0a-f3727be27bae', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='f25ef008-1074-4273-aa0a-f3727be27bae'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('f25ef008-1074-4273-aa0a-f3727be27bae', '80b24792-2ab1-44e9-86b5-9aa21a17c5c0')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('c6f86db6-726c-43fe-be5a-cf0ec2d12f2e', '80b24792-2ab1-44e9-86b5-9aa21a17c5c0')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('3cc76941-06e3-4ee1-a9de-4fb873a53843', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'O', '', '', '', '2b13dab0-5395-4acb-b465-bdd0eb1e191b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '42ef84bf-efdf-4d93-8425-46442f0f2db2')
/
update KSEN_LUI set OBJ_ID='15ec6f05-c1bc-4497-aaa5-36f5e2ac0efd', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='09415b65-dae3-45c0-9829-24eed04f9dd1', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='2b13dab0-5395-4acb-b465-bdd0eb1e191b' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='8485b0ca-5dc3-4110-a9a3-8169cb2d8167', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='2b13dab0-5395-4acb-b465-bdd0eb1e191b', ATTR_VALUE='0' where ID='0f1c6400-50f9-4b91-9d6e-cba1d5b7f143'
/
update KSEN_LUI_ATTR set OBJ_ID='1ec37e01-75aa-484e-b653-6f4aa1b2e391', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='2b13dab0-5395-4acb-b465-bdd0eb1e191b', ATTR_VALUE='0' where ID='2416ba4d-89b6-4b79-b1ab-feffceb70f0f'
/
update KSEN_LUI_IDENT set OBJ_ID='3cc76941-06e3-4ee1-a9de-4fb873a53843', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='O', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='2b13dab0-5395-4acb-b465-bdd0eb1e191b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='42ef84bf-efdf-4d93-8425-46442f0f2db2' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='e88d395c-42f5-472c-bbe1-f915c758735d', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='2b13dab0-5395-4acb-b465-bdd0eb1e191b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='13dcad5b-8788-4e46-a220-f3f58247af65' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='2f2a3dc8-5f4f-4176-8521-1708883b88ad', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:56:52.846', EXPIR_DT='', LUI_ID='2b13dab0-5395-4acb-b465-bdd0eb1e191b', PERS_ID='G.MICHAELM', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='e45dae28-3c25-4e5a-8717-cc132f0d9386' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('2b13dab0-5395-4acb-b465-bdd0eb1e191b', 'aa8ee9ba-3888-4e7e-bd94-fb9b26c5024e')
/
delete from KSEN_LUI_IDENT where ID='73b3208c-6fa5-49d0-8686-6aeb96f26248' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('c4e39f31-64b5-4267-aba6-e02c796cf3a5', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'fea36130-e003-4c64-9e83-e9347b8121d8')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('7d3e22dd-119a-474c-b0fd-7de7b3f9af2e', '0', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2', 'fea36130-e003-4c64-9e83-e9347b8121d8', '07d08755-f28e-4d74-8aa4-4c09ec051109')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='b9097728-9567-4745-b34f-1c3e25aafe11' where ID='a7db780f-d69a-4c42-9699-551310e181aa'
/
update KSEN_SCHED_RQST set OBJ_ID='97cb2848-3a7b-4c93-bfbc-d6ea2bcea7fb', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - P', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='fea36130-e003-4c64-9e83-e9347b8121d8', SCHED_RQST_SET_ID='e7332963-fbbb-49cc-92e5-65a22a3f9f2d' where ID='b9097728-9567-4745-b34f-1c3e25aafe11' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='a7db780f-d69a-4c42-9699-551310e181aa'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('a7db780f-d69a-4c42-9699-551310e181aa', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='a7db780f-d69a-4c42-9699-551310e181aa'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('a7db780f-d69a-4c42-9699-551310e181aa', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='a7db780f-d69a-4c42-9699-551310e181aa'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('a7db780f-d69a-4c42-9699-551310e181aa', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='a7db780f-d69a-4c42-9699-551310e181aa'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('a7db780f-d69a-4c42-9699-551310e181aa', '30fc37d3-8c57-4c88-8988-a930241f7038')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('07d08755-f28e-4d74-8aa4-4c09ec051109', '30fc37d3-8c57-4c88-8988-a930241f7038')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('1382605c-5349-4d05-96e7-40468a5b57c6', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'P', '', '', '', 'f03379f2-4435-4a27-b21e-8f45e130c97e', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '69689451-6818-4121-9f9e-346f29986bb8')
/
update KSEN_LUI_ATTR set OBJ_ID='c72bc8a4-e588-4011-8de6-13539f32941d', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='f03379f2-4435-4a27-b21e-8f45e130c97e', ATTR_VALUE='0' where ID='3e6ff7ea-cbc9-4e09-8e7d-ee45934e55ce'
/
update KSEN_LUI set OBJ_ID='f9994839-aad3-43cf-b8a9-e4bd23c0a925', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='3bac0164-0547-4b66-898b-58405dc8898f', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='f03379f2-4435-4a27-b21e-8f45e130c97e' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='36f0bafa-58da-414a-82d7-eeaa5deebc46', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='f03379f2-4435-4a27-b21e-8f45e130c97e', ATTR_VALUE='0' where ID='58145e5a-cf70-40dc-a6c0-ad48b30de278'
/
update KSEN_LUI_IDENT set OBJ_ID='1382605c-5349-4d05-96e7-40468a5b57c6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='P', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='f03379f2-4435-4a27-b21e-8f45e130c97e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='69689451-6818-4121-9f9e-346f29986bb8' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='2fc0f1dc-7f16-4643-a0f5-39ea84ae08f4', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='f03379f2-4435-4a27-b21e-8f45e130c97e', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='e1fe39ef-0f30-438e-b3ee-1f8beab5ef50' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='3b895ee7-b28d-4aee-b213-6d0f9a5cb09e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:57:09.359', EXPIR_DT='', LUI_ID='f03379f2-4435-4a27-b21e-8f45e130c97e', PERS_ID='J.ANAGHAK', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='ccd37ef9-7313-4b69-be59-c9ec732c7dae' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('f03379f2-4435-4a27-b21e-8f45e130c97e', 'fea36130-e003-4c64-9e83-e9347b8121d8')
/
delete from KSEN_LUI_IDENT where ID='95b89e76-4528-4661-b8c4-f14a16660d63' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('53dfc6b8-90e0-4ba5-a9b2-8f6c2231ac5c', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '24acd867-c03c-4721-aae7-3258ce23b7cd')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('bf97892a-e7b5-4067-bb63-074e60b23fb0', '0', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2', '24acd867-c03c-4721-aae7-3258ce23b7cd', '578d0b8b-fed5-43fa-b260-b01047c7c78e')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='30dee6af-4dc3-4400-be6b-88aae767684c' where ID='f0df48eb-8497-48c3-8e39-acf8a2fe80df'
/
update KSEN_SCHED_RQST set OBJ_ID='0332cadf-58ab-47e3-9e8a-770fc3cb34c1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - Q', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='24acd867-c03c-4721-aae7-3258ce23b7cd', SCHED_RQST_SET_ID='b75af530-e6f5-4e2f-af3a-5e4d9c23ef2b' where ID='30dee6af-4dc3-4400-be6b-88aae767684c' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='f0df48eb-8497-48c3-8e39-acf8a2fe80df'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('f0df48eb-8497-48c3-8e39-acf8a2fe80df', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='f0df48eb-8497-48c3-8e39-acf8a2fe80df'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('f0df48eb-8497-48c3-8e39-acf8a2fe80df', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='f0df48eb-8497-48c3-8e39-acf8a2fe80df'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('f0df48eb-8497-48c3-8e39-acf8a2fe80df', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='f0df48eb-8497-48c3-8e39-acf8a2fe80df'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('f0df48eb-8497-48c3-8e39-acf8a2fe80df', 'a4014111-860c-4ce4-8478-505bfa3ed5c2')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('578d0b8b-fed5-43fa-b260-b01047c7c78e', 'a4014111-860c-4ce4-8478-505bfa3ed5c2')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('0df95ec5-d01a-44e3-908d-a89aaf6b258b', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'Q', '', '', '', 'beed8739-feb4-412e-ab55-6c6452f0e0e4', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '97d61bf2-89a3-43fd-b93f-4b2edf5b418a')
/
update KSEN_LUI_ATTR set OBJ_ID='edfb8a60-9a47-443e-a4d0-b41a2b484151', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='beed8739-feb4-412e-ab55-6c6452f0e0e4', ATTR_VALUE='0' where ID='2fc1455d-e281-4884-8433-7641bdfa82da'
/
update KSEN_LUI set OBJ_ID='872fd732-fe85-43fa-b2b3-dae3cb11da2e', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='a89ef48f-7d10-4c62-bd31-7ead4247b18b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='beed8739-feb4-412e-ab55-6c6452f0e0e4' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='a6ce904c-7b82-4167-bb18-924cce56ccd9', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='beed8739-feb4-412e-ab55-6c6452f0e0e4', ATTR_VALUE='0' where ID='3bf25758-4747-4986-84e6-2f3f2a596487'
/
update KSEN_LUI_IDENT set OBJ_ID='0df95ec5-d01a-44e3-908d-a89aaf6b258b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='Q', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='beed8739-feb4-412e-ab55-6c6452f0e0e4', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='97d61bf2-89a3-43fd-b93f-4b2edf5b418a' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='19171b22-e777-464d-9ceb-62a93e85618f', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='beed8739-feb4-412e-ab55-6c6452f0e0e4', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='238b65ed-57df-4842-a814-93ee90d0a605' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='8baeece8-845d-4a69-b4c7-87afa610fafd', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:57:26.156', EXPIR_DT='', LUI_ID='beed8739-feb4-412e-ab55-6c6452f0e0e4', PERS_ID='A.KIMBERLYD', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='1fc12111-0c9f-4031-945f-3e9576dc57e7' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('beed8739-feb4-412e-ab55-6c6452f0e0e4', '24acd867-c03c-4721-aae7-3258ce23b7cd')
/
delete from KSEN_LUI_IDENT where ID='3368dec8-2657-41c0-8c3d-005fd4a2d9d5' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('4cc7c6a1-b5f5-4302-9c98-f51640b54505', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '4a052879-a6fb-44f1-ac59-02bf7b5d54ea')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('80fbd0c8-f696-4080-b013-cb7c075a5de5', '0', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2', '4a052879-a6fb-44f1-ac59-02bf7b5d54ea', '51501733-f837-416f-b7f6-90f1c3a07d01')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='8f7ce743-760e-4c16-babb-dbb16f49a10c' where ID='5733d474-2a8e-4529-bf62-cd6f3aa7d779'
/
update KSEN_SCHED_RQST set OBJ_ID='3b534f24-a43e-444a-adbd-9842054cf7ed', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - R', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='4a052879-a6fb-44f1-ac59-02bf7b5d54ea', SCHED_RQST_SET_ID='5c3e79b4-4c7b-4a50-bebc-31dd472ff9d5' where ID='8f7ce743-760e-4c16-babb-dbb16f49a10c' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='5733d474-2a8e-4529-bf62-cd6f3aa7d779'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('5733d474-2a8e-4529-bf62-cd6f3aa7d779', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='5733d474-2a8e-4529-bf62-cd6f3aa7d779'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('5733d474-2a8e-4529-bf62-cd6f3aa7d779', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='5733d474-2a8e-4529-bf62-cd6f3aa7d779'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('5733d474-2a8e-4529-bf62-cd6f3aa7d779', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='5733d474-2a8e-4529-bf62-cd6f3aa7d779'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('5733d474-2a8e-4529-bf62-cd6f3aa7d779', '41547844-971d-4946-9584-6c551fbe2146')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('51501733-f837-416f-b7f6-90f1c3a07d01', '41547844-971d-4946-9584-6c551fbe2146')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('674e2502-8eb6-4c50-b317-1760ec0fa87b', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'R', '', '', '', '8099d225-47fd-4968-b308-76b53f6f95d8', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '16179c51-f36d-4ccf-a59e-f61e97744960')
/
update KSEN_LUI set OBJ_ID='4e52735c-ae8a-4a9f-899f-ef9a8c04fdca', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='7d91a6f7-ee9a-4d1f-b8d7-134066111f53', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='8099d225-47fd-4968-b308-76b53f6f95d8' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='a156cb90-c0a7-459f-b50c-5c8912ff987d', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='8099d225-47fd-4968-b308-76b53f6f95d8', ATTR_VALUE='0' where ID='9acfb97a-5c22-4d0c-84e2-c3a7ed746ba8'
/
update KSEN_LUI_ATTR set OBJ_ID='307da954-201d-4b6a-b01f-fc5d184757cd', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='8099d225-47fd-4968-b308-76b53f6f95d8', ATTR_VALUE='0' where ID='ddd100f6-df66-4509-a395-40ee4a36eb1b'
/
update KSEN_LUI_IDENT set OBJ_ID='674e2502-8eb6-4c50-b317-1760ec0fa87b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='R', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='8099d225-47fd-4968-b308-76b53f6f95d8', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='16179c51-f36d-4ccf-a59e-f61e97744960' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='53924bd7-fd0b-4d05-9bfa-6792d4bd21ff', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='8099d225-47fd-4968-b308-76b53f6f95d8', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='5edf03b2-4f72-46af-9c83-83ea8e4c388d' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='f49bcdb9-d3c5-4fdd-bd49-106ce8f3e343', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:57:42.874', EXPIR_DT='', LUI_ID='8099d225-47fd-4968-b308-76b53f6f95d8', PERS_ID='B.MICHELEG', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='592d62ba-f5e1-419d-9dde-a3545fe5b507' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('8099d225-47fd-4968-b308-76b53f6f95d8', '4a052879-a6fb-44f1-ac59-02bf7b5d54ea')
/
delete from KSEN_LUI_IDENT where ID='9b24d147-078d-48a6-a00d-a1e59e153684' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('b03def1b-d4a7-410f-86ef-7f82e022889d', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '10c68fbe-e9bb-4301-8a0d-80a3dc79f844')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('338cd819-f344-419b-8350-f6df8cfe7cd0', '0', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2', '10c68fbe-e9bb-4301-8a0d-80a3dc79f844', 'b84b60ab-6fb5-474f-a23c-e347b5a2601c')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='03d3e214-1e28-4591-8b14-8d871388b750' where ID='6352491c-887b-4ef2-a262-27f43fc9e4f8'
/
update KSEN_SCHED_RQST set OBJ_ID='f8e4ac60-8e0b-48cf-ad15-29c26d3cf0ce', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - S', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='10c68fbe-e9bb-4301-8a0d-80a3dc79f844', SCHED_RQST_SET_ID='6f4ba627-7360-458a-8cf3-7896fdadce78' where ID='03d3e214-1e28-4591-8b14-8d871388b750' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='6352491c-887b-4ef2-a262-27f43fc9e4f8'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('6352491c-887b-4ef2-a262-27f43fc9e4f8', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='6352491c-887b-4ef2-a262-27f43fc9e4f8'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('6352491c-887b-4ef2-a262-27f43fc9e4f8', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='6352491c-887b-4ef2-a262-27f43fc9e4f8'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('6352491c-887b-4ef2-a262-27f43fc9e4f8', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='6352491c-887b-4ef2-a262-27f43fc9e4f8'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('6352491c-887b-4ef2-a262-27f43fc9e4f8', 'd240ee44-e7a6-461d-8010-e54762fa3578')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('b84b60ab-6fb5-474f-a23c-e347b5a2601c', 'd240ee44-e7a6-461d-8010-e54762fa3578')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('04c4b3d6-9c27-412d-aa98-445171357863', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'S', '', '', '', '9ac86f1a-3016-41fe-bdd1-7e4f87dadc6d', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '5f5366de-9e35-4712-b52a-3d0af5382af1')
/
update KSEN_LUI_ATTR set OBJ_ID='7c2dd9df-b3a6-4158-8342-1e42515cf2e1', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='9ac86f1a-3016-41fe-bdd1-7e4f87dadc6d', ATTR_VALUE='0' where ID='be81cbe6-f93c-4ceb-9a5a-1db8e999cc0d'
/
update KSEN_LUI set OBJ_ID='223ca72d-2364-43c9-b5b2-d205cd1f80ec', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='2484136e-b057-4450-ad0a-8fa2eed95a2b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='9ac86f1a-3016-41fe-bdd1-7e4f87dadc6d' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='5b2ca7a2-28fd-4d6e-bb68-fb6bdb0832f6', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='9ac86f1a-3016-41fe-bdd1-7e4f87dadc6d', ATTR_VALUE='0' where ID='2e7e4336-1f75-4d20-a38f-0f03c2ea1c5f'
/
update KSEN_LUI_IDENT set OBJ_ID='04c4b3d6-9c27-412d-aa98-445171357863', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='S', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='9ac86f1a-3016-41fe-bdd1-7e4f87dadc6d', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='5f5366de-9e35-4712-b52a-3d0af5382af1' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='fd9afdcd-43a7-4436-816f-7759483c976d', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='9ac86f1a-3016-41fe-bdd1-7e4f87dadc6d', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='7d11e3b0-73f8-4c80-a35f-9e1acd1579e6' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='0a6eee4b-7a65-46b4-8fb2-fafed2e4f4ca', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:57:59.592', EXPIR_DT='', LUI_ID='9ac86f1a-3016-41fe-bdd1-7e4f87dadc6d', PERS_ID='S.LEET', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='cf3bee83-2b77-4964-8f6a-893286949430' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('9ac86f1a-3016-41fe-bdd1-7e4f87dadc6d', '10c68fbe-e9bb-4301-8a0d-80a3dc79f844')
/
delete from KSEN_LUI_IDENT where ID='3b309861-03ed-4d80-bac1-49ef5232d905' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('11b3974f-97d8-462d-ab80-15fc2279f5fd', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '6ab2ef08-df82-4b4c-99be-a0e8128938d2')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('a3ed3944-64e5-47fa-9cc0-77a823fb5f8c', '0', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2', '6ab2ef08-df82-4b4c-99be-a0e8128938d2', 'bf1dcba8-6534-48b9-b5d7-6b8e7a42e3b6')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='984bdd26-860b-46b1-9b10-3979a30fea53' where ID='99c6de73-7866-46a8-9d8b-fcb6aaf51787'
/
update KSEN_SCHED_RQST set OBJ_ID='9a15c982-39ce-4fec-b88d-65ccacdaaddc', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - T', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='6ab2ef08-df82-4b4c-99be-a0e8128938d2', SCHED_RQST_SET_ID='ed3ff313-9796-4895-9865-32608839364e' where ID='984bdd26-860b-46b1-9b10-3979a30fea53' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='99c6de73-7866-46a8-9d8b-fcb6aaf51787'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('99c6de73-7866-46a8-9d8b-fcb6aaf51787', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='99c6de73-7866-46a8-9d8b-fcb6aaf51787'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('99c6de73-7866-46a8-9d8b-fcb6aaf51787', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='99c6de73-7866-46a8-9d8b-fcb6aaf51787'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('99c6de73-7866-46a8-9d8b-fcb6aaf51787', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='99c6de73-7866-46a8-9d8b-fcb6aaf51787'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('99c6de73-7866-46a8-9d8b-fcb6aaf51787', 'c38f2875-0cda-4b67-9c13-499dd9be8eaf')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('bf1dcba8-6534-48b9-b5d7-6b8e7a42e3b6', 'c38f2875-0cda-4b67-9c13-499dd9be8eaf')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a984a97b-f992-436c-95ca-fd625cecc53f', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'T', '', '', '', '85bf9f49-6a6f-4d5a-8f4d-33fe8c6aabe4', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '1545c0d6-2f5f-47e7-9cc4-2386e4176d3d')
/
update KSEN_LUI set OBJ_ID='ea1ab808-c68a-4c50-b9f4-6a23b6f724fe', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='cbf096d5-a82f-4965-aceb-5e80a89ad9c9', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='85bf9f49-6a6f-4d5a-8f4d-33fe8c6aabe4' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='8b0e846c-e4ea-4cf2-9a4a-923a16954bc4', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='85bf9f49-6a6f-4d5a-8f4d-33fe8c6aabe4', ATTR_VALUE='0' where ID='7933eef1-e6aa-41d8-9a21-e50cad31fc1f'
/
update KSEN_LUI_ATTR set OBJ_ID='31ea84b9-1315-4329-a061-43c851aa5278', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='85bf9f49-6a6f-4d5a-8f4d-33fe8c6aabe4', ATTR_VALUE='0' where ID='98c8dbaa-3bc2-42d3-a496-ebde3b80767a'
/
update KSEN_LUI_IDENT set OBJ_ID='a984a97b-f992-436c-95ca-fd625cecc53f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='T', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='85bf9f49-6a6f-4d5a-8f4d-33fe8c6aabe4', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1545c0d6-2f5f-47e7-9cc4-2386e4176d3d' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='99b3fe6b-3f73-439b-b535-33e2040b3d5f', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='85bf9f49-6a6f-4d5a-8f4d-33fe8c6aabe4', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='7809eca1-5913-4be0-835f-132a83d53cb2' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='41b1bc77-9170-4baa-a577-4c74044b34fc', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:58:16.468', EXPIR_DT='', LUI_ID='85bf9f49-6a6f-4d5a-8f4d-33fe8c6aabe4', PERS_ID='P.ANDREWM', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='71991ece-7aa5-41a6-82cc-50ad437c1e00' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('85bf9f49-6a6f-4d5a-8f4d-33fe8c6aabe4', '6ab2ef08-df82-4b4c-99be-a0e8128938d2')
/
delete from KSEN_LUI_IDENT where ID='70aabbec-c3aa-49bb-8832-f6711276380d' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('8cca2da2-947d-4adf-bb5d-a40b03cc0ea0', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '4e122d61-2427-4c55-81cd-50d6fa356101')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('6bdebc45-3e0a-43a0-908b-09b43e83404c', '0', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2', '4e122d61-2427-4c55-81cd-50d6fa356101', '104ba245-ddb7-4dfa-8d84-c0f2720d6a3f')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='84990db7-2910-465e-bb6c-78d1629cb5e5' where ID='3555cf21-91f1-4df8-bf9b-5962e229282b'
/
update KSEN_SCHED_RQST set OBJ_ID='7e2789c2-7d72-4fb5-8505-4fb81e0793f2', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - U', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='4e122d61-2427-4c55-81cd-50d6fa356101', SCHED_RQST_SET_ID='fc93fa43-a0d4-46e8-9c73-3ede0118b285' where ID='84990db7-2910-465e-bb6c-78d1629cb5e5' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='3555cf21-91f1-4df8-bf9b-5962e229282b'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('3555cf21-91f1-4df8-bf9b-5962e229282b', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='3555cf21-91f1-4df8-bf9b-5962e229282b'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('3555cf21-91f1-4df8-bf9b-5962e229282b', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='3555cf21-91f1-4df8-bf9b-5962e229282b'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('3555cf21-91f1-4df8-bf9b-5962e229282b', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='3555cf21-91f1-4df8-bf9b-5962e229282b'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('3555cf21-91f1-4df8-bf9b-5962e229282b', '3ba7832f-33d1-4046-9a47-40a350f0430d')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('104ba245-ddb7-4dfa-8d84-c0f2720d6a3f', '3ba7832f-33d1-4046-9a47-40a350f0430d')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a78459a9-2956-4ba6-aa62-4e11b56a0364', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'U', '', '', '', 'd41a71d8-ac39-4a4f-92cf-cbb4433234ee', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ebd6f255-7ca4-4a71-89bb-a807d3e41c1d')
/
update KSEN_LUI_ATTR set OBJ_ID='9a7f9717-2d07-438c-805f-71899d6bd8aa', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='d41a71d8-ac39-4a4f-92cf-cbb4433234ee', ATTR_VALUE='0' where ID='637cdc4f-adf2-44ba-8b31-bff24586ea7c'
/
update KSEN_LUI set OBJ_ID='800e14e5-2572-4ad9-913b-34584c270912', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='107dded6-6597-4211-b228-7c66c8414616', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='d41a71d8-ac39-4a4f-92cf-cbb4433234ee' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='8f40693e-a340-4cb3-b515-5f0d1413afd8', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='d41a71d8-ac39-4a4f-92cf-cbb4433234ee', ATTR_VALUE='0' where ID='106aca1c-c42b-4fbb-ba3e-6eb901083631'
/
update KSEN_LUI_IDENT set OBJ_ID='a78459a9-2956-4ba6-aa62-4e11b56a0364', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='U', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='d41a71d8-ac39-4a4f-92cf-cbb4433234ee', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='ebd6f255-7ca4-4a71-89bb-a807d3e41c1d' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='c1d6ef73-cd3d-4ae3-b99d-41dc2e70a07c', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='d41a71d8-ac39-4a4f-92cf-cbb4433234ee', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='c73d05cf-3342-4684-acfe-254dca6f2eff' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='b5f9bcbb-1fa8-4c8e-9bfa-8df8ad413996', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:58:33.163', EXPIR_DT='', LUI_ID='d41a71d8-ac39-4a4f-92cf-cbb4433234ee', PERS_ID='H.DANIELB', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='b147626b-014b-451c-aba4-d57bf33063aa' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('d41a71d8-ac39-4a4f-92cf-cbb4433234ee', '4e122d61-2427-4c55-81cd-50d6fa356101')
/
delete from KSEN_LUI_IDENT where ID='c75a7746-5a87-466b-bf69-07f3e40f46c7' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('da064c94-2a6a-4dc4-9ce9-4e980189d83e', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '98e385e7-9909-4199-88a7-ec3bb03918e8')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('6d883b35-b603-42ce-9db5-cac29e2ac8ad', '0', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2', '98e385e7-9909-4199-88a7-ec3bb03918e8', 'b1b65b26-15e9-4e4e-8528-3f877e389a79')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='bf4e52e7-a695-44b6-9234-3af32b5daa69' where ID='35c2b6ff-4ead-4a69-bbf2-d3ea5cfd31ec'
/
update KSEN_SCHED_RQST set OBJ_ID='9e974a6e-dcf6-426e-adce-7deaaf04a398', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - V', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='98e385e7-9909-4199-88a7-ec3bb03918e8', SCHED_RQST_SET_ID='2e23cbdb-776c-4b18-b7cf-43120dd60661' where ID='bf4e52e7-a695-44b6-9234-3af32b5daa69' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='35c2b6ff-4ead-4a69-bbf2-d3ea5cfd31ec'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('35c2b6ff-4ead-4a69-bbf2-d3ea5cfd31ec', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='35c2b6ff-4ead-4a69-bbf2-d3ea5cfd31ec'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('35c2b6ff-4ead-4a69-bbf2-d3ea5cfd31ec', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='35c2b6ff-4ead-4a69-bbf2-d3ea5cfd31ec'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('35c2b6ff-4ead-4a69-bbf2-d3ea5cfd31ec', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='35c2b6ff-4ead-4a69-bbf2-d3ea5cfd31ec'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('35c2b6ff-4ead-4a69-bbf2-d3ea5cfd31ec', 'd3ecad45-6044-449b-95fd-4b1fb95da03d')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('b1b65b26-15e9-4e4e-8528-3f877e389a79', 'd3ecad45-6044-449b-95fd-4b1fb95da03d')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('2ec69aed-2286-4738-9944-5fdb0217e5ae', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'V', '', '', '', 'e3c7aa5b-02c3-4416-b8c3-a069e9bf587b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'f9ef9d20-c8e4-4ae8-b477-7713952d5b4c')
/
update KSEN_LUI set OBJ_ID='5ccc71bc-2baf-42cb-bf12-270a5f9cc2e1', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='fd93d6b6-1a66-4cae-bcb2-23b0316a4870', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='e3c7aa5b-02c3-4416-b8c3-a069e9bf587b' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='d28a7e7d-7277-4f13-ab06-b43465c21548', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='e3c7aa5b-02c3-4416-b8c3-a069e9bf587b', ATTR_VALUE='0' where ID='83d55225-9477-47db-8772-45580e6c895b'
/
update KSEN_LUI_ATTR set OBJ_ID='792b29be-9740-498c-b276-7e0face2907a', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='e3c7aa5b-02c3-4416-b8c3-a069e9bf587b', ATTR_VALUE='0' where ID='0959d9ec-7392-48f8-88c8-7df67b63c9d7'
/
update KSEN_LUI_IDENT set OBJ_ID='2ec69aed-2286-4738-9944-5fdb0217e5ae', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='V', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='e3c7aa5b-02c3-4416-b8c3-a069e9bf587b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='f9ef9d20-c8e4-4ae8-b477-7713952d5b4c' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='daf52a19-0801-4ed5-8240-c1da0a4b1b1a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='e3c7aa5b-02c3-4416-b8c3-a069e9bf587b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='cb9ed1d4-2da6-4c40-bd80-d12efd66e3e6' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='b7ed116a-09b5-4ec1-9328-1ee1094a0186', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:58:50.043', EXPIR_DT='', LUI_ID='e3c7aa5b-02c3-4416-b8c3-a069e9bf587b', PERS_ID='D.CRAIGA', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='d2a1630d-6796-41f1-8294-15cc18efb443' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('e3c7aa5b-02c3-4416-b8c3-a069e9bf587b', '98e385e7-9909-4199-88a7-ec3bb03918e8')
/
delete from KSEN_LUI_IDENT where ID='40ac6dbc-cf15-4cf7-b516-c9a2e90fb9cb' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('abccd25c-06bd-4421-b753-74eb8301c497', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'bc3db6d4-f795-471b-aa60-4f8be6ec7500')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('d20b6992-0a7a-4bdb-b652-3b85742c75e9', '0', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2', 'bc3db6d4-f795-471b-aa60-4f8be6ec7500', '03670c2a-0dcf-4707-9217-815dcf847e2c')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='3b34f5ff-3cda-434e-95d0-ddd9d2a9bab0' where ID='7711c25a-75e6-4dd3-b855-887868cabd43'
/
update KSEN_SCHED_RQST set OBJ_ID='15bcb705-1baa-4364-ab96-e436a8c1c868', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - W', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='bc3db6d4-f795-471b-aa60-4f8be6ec7500', SCHED_RQST_SET_ID='5591715b-25d1-4db4-bc59-7e0931d31d6d' where ID='3b34f5ff-3cda-434e-95d0-ddd9d2a9bab0' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='7711c25a-75e6-4dd3-b855-887868cabd43'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('7711c25a-75e6-4dd3-b855-887868cabd43', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='7711c25a-75e6-4dd3-b855-887868cabd43'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('7711c25a-75e6-4dd3-b855-887868cabd43', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='7711c25a-75e6-4dd3-b855-887868cabd43'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('7711c25a-75e6-4dd3-b855-887868cabd43', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='7711c25a-75e6-4dd3-b855-887868cabd43'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('7711c25a-75e6-4dd3-b855-887868cabd43', 'cfba52f9-56bc-451e-8049-36a2a4dec853')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('03670c2a-0dcf-4707-9217-815dcf847e2c', 'cfba52f9-56bc-451e-8049-36a2a4dec853')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a3cf70b0-d08d-47ac-a38b-8fc4b4bd7c3c', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'W', '', '', '', 'ba2bf7e8-0e81-461e-aa8f-10b70e9cbe21', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '3627dd41-1eca-47e1-bd0a-b52f6b031511')
/
update KSEN_LUI_ATTR set OBJ_ID='24720044-2af3-4ed2-bd17-6928c940261c', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='ba2bf7e8-0e81-461e-aa8f-10b70e9cbe21', ATTR_VALUE='0' where ID='71a11e11-824f-4105-afef-a8b196ae0be2'
/
update KSEN_LUI set OBJ_ID='d48d2e23-7404-4d10-ac98-62012f3cbb3b', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='8070ba81-9e12-46ef-a1cc-7c55b4b9052f', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='ba2bf7e8-0e81-461e-aa8f-10b70e9cbe21' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='6852c791-6119-416c-bda6-5223c257b2e4', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='ba2bf7e8-0e81-461e-aa8f-10b70e9cbe21', ATTR_VALUE='0' where ID='4b0bec54-06e0-42d7-8cbc-de76256b9d99'
/
update KSEN_LUI_IDENT set OBJ_ID='a3cf70b0-d08d-47ac-a38b-8fc4b4bd7c3c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='W', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='ba2bf7e8-0e81-461e-aa8f-10b70e9cbe21', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='3627dd41-1eca-47e1-bd0a-b52f6b031511' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='2babf542-329c-417f-acd1-dfeef0fb329d', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='ba2bf7e8-0e81-461e-aa8f-10b70e9cbe21', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='23c84d4f-ea3d-48c4-9920-64383d0aedd9' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='5414e8c8-29da-4da5-977e-0cd8cbd18b5e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:59:06.647', EXPIR_DT='', LUI_ID='ba2bf7e8-0e81-461e-aa8f-10b70e9cbe21', PERS_ID='P.NANCIM', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='3b7d7630-d046-4ed3-91d2-de0febe13b2c' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('ba2bf7e8-0e81-461e-aa8f-10b70e9cbe21', 'bc3db6d4-f795-471b-aa60-4f8be6ec7500')
/
delete from KSEN_LUI_IDENT where ID='8ffb2773-5a0e-43a9-8dd5-6c96f7173c1e' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('c48ae464-1304-4ba0-b880-846cea345c1f', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', 'f457f492-bd89-457f-bad2-f6a377b637bb')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('e452b598-f516-4897-b264-5d067ed463d6', '0', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2', 'f457f492-bd89-457f-bad2-f6a377b637bb', 'b98f4e10-5f83-4a3b-bb81-c00c5014a38e')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='481956c6-79a6-4b9b-a896-b908316bf332' where ID='d2592f5e-f50a-42f2-b30e-a89209fa0d0c'
/
update KSEN_SCHED_RQST set OBJ_ID='c9ec29bd-2daa-42b2-a525-83b785e23f6d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - X', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='f457f492-bd89-457f-bad2-f6a377b637bb', SCHED_RQST_SET_ID='4a11bc69-2214-4fa7-9ce6-d8d08c7c7d47' where ID='481956c6-79a6-4b9b-a896-b908316bf332' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='d2592f5e-f50a-42f2-b30e-a89209fa0d0c'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('d2592f5e-f50a-42f2-b30e-a89209fa0d0c', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='d2592f5e-f50a-42f2-b30e-a89209fa0d0c'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('d2592f5e-f50a-42f2-b30e-a89209fa0d0c', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='d2592f5e-f50a-42f2-b30e-a89209fa0d0c'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('d2592f5e-f50a-42f2-b30e-a89209fa0d0c', '125a4d96-4b0e-4ab4-9d88-4742b95ea5e2')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='d2592f5e-f50a-42f2-b30e-a89209fa0d0c'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('d2592f5e-f50a-42f2-b30e-a89209fa0d0c', '08648ae5-21d0-47df-8717-4b83597ffc06')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('b98f4e10-5f83-4a3b-bb81-c00c5014a38e', '08648ae5-21d0-47df-8717-4b83597ffc06')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7ddd36f6-c6d4-4d1a-b88b-b6bd9ca4b9d6', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'X', '', '', '', 'fc28b776-c0a9-4279-8429-6852f7081e5e', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'fd076a4e-5a68-4511-bee3-7eda4894aa3f')
/
update KSEN_LUI_ATTR set OBJ_ID='0e2b162e-6ba4-4ac4-8765-b46e8d6f62be', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='fc28b776-c0a9-4279-8429-6852f7081e5e', ATTR_VALUE='0' where ID='9cf24c16-5318-41fd-90a9-8887d27db07c'
/
update KSEN_LUI set OBJ_ID='674f8b93-e583-4a5a-8995-dd5da6d7eac5', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='f54be7b2-70cc-46c0-8204-7963b66d4af9', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='fc28b776-c0a9-4279-8429-6852f7081e5e' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='fd4168da-cb5c-4676-893f-10d7aed6047c', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='fc28b776-c0a9-4279-8429-6852f7081e5e', ATTR_VALUE='0' where ID='516a1882-bee0-4c9e-8d3d-97d4e27bb3df'
/
update KSEN_LUI_IDENT set OBJ_ID='7ddd36f6-c6d4-4d1a-b88b-b6bd9ca4b9d6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='X', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='fc28b776-c0a9-4279-8429-6852f7081e5e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='fd076a4e-5a68-4511-bee3-7eda4894aa3f' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='6b293b92-bc9f-4c0d-940d-58e6c50f408e', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='fc28b776-c0a9-4279-8429-6852f7081e5e', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='f651faa4-32ea-424a-af5e-e51017a53774' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='790d757f-34b8-4cb2-90b0-a3ba625fdfbf', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:59:22.789', EXPIR_DT='', LUI_ID='fc28b776-c0a9-4279-8429-6852f7081e5e', PERS_ID='J.BEATRICEC', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='5e6dd9a2-c183-4cc7-81ee-5a0b05b67616' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('fc28b776-c0a9-4279-8429-6852f7081e5e', 'f457f492-bd89-457f-bad2-f6a377b637bb')
/
delete from KSEN_LUI_IDENT where ID='35516e9e-0bbb-4e6c-85c4-9d61a4436d37' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('cabea765-6a5a-4884-a427-65a3acffb16e', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '0565fc7b-c2ac-4aad-b785-c3e38e65f1f1')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('69cc3912-0e93-4dc0-ac7a-5c1a6b3adca3', '0', 'b4df186c-2cda-4366-b8d9-f977b779abab', '0565fc7b-c2ac-4aad-b785-c3e38e65f1f1', '3622a1ab-7c67-4f13-aece-81fab6266ba0')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='48063389-bc77-48a9-9aee-d54e7a63d8de' where ID='58b19657-11ee-49f6-85ac-664cacd9e111'
/
update KSEN_SCHED_RQST set OBJ_ID='8e920f64-ded9-4912-a62b-cd4c5543a945', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - Y', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='0565fc7b-c2ac-4aad-b785-c3e38e65f1f1', SCHED_RQST_SET_ID='f2ec362d-74e1-4058-9cc1-10a91c5e4a28' where ID='48063389-bc77-48a9-9aee-d54e7a63d8de' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='58b19657-11ee-49f6-85ac-664cacd9e111'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('58b19657-11ee-49f6-85ac-664cacd9e111', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='58b19657-11ee-49f6-85ac-664cacd9e111'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('58b19657-11ee-49f6-85ac-664cacd9e111', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='58b19657-11ee-49f6-85ac-664cacd9e111'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('58b19657-11ee-49f6-85ac-664cacd9e111', 'b4df186c-2cda-4366-b8d9-f977b779abab')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='58b19657-11ee-49f6-85ac-664cacd9e111'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('58b19657-11ee-49f6-85ac-664cacd9e111', '0e2535aa-d203-4928-92f2-a6752c32cc35')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('3622a1ab-7c67-4f13-aece-81fab6266ba0', '0e2535aa-d203-4928-92f2-a6752c32cc35')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('634bf98b-7bdf-49e3-955d-5ca9c1460988', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'Y', '', '', '', '502e1be7-57db-4218-a293-1a3e23748147', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '892c66e1-1495-4dc5-8e34-d04840d5e0f6')
/
update KSEN_LUI_ATTR set OBJ_ID='37a47385-4cb9-4a9f-9457-9dac7ecfac2f', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='502e1be7-57db-4218-a293-1a3e23748147', ATTR_VALUE='0' where ID='ce796ea6-ba66-4a3c-aab8-720892c10669'
/
update KSEN_LUI set OBJ_ID='b951ac81-0d30-40be-b13d-12047b922f2a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='69863a58-c29e-4b1b-a2db-cdbe35597fcf', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=24, MIN_SEATS=24, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='502e1be7-57db-4218-a293-1a3e23748147' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='6292b086-f380-4de8-9d0d-7487ba44cfe4', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='502e1be7-57db-4218-a293-1a3e23748147', ATTR_VALUE='0' where ID='4d00c561-f3f7-4594-8f06-b64aca4bd399'
/
update KSEN_LUI_IDENT set OBJ_ID='634bf98b-7bdf-49e3-955d-5ca9c1460988', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='Y', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='502e1be7-57db-4218-a293-1a3e23748147', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='892c66e1-1495-4dc5-8e34-d04840d5e0f6' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='63edd447-a856-4bd9-89d0-7308899f963d', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='502e1be7-57db-4218-a293-1a3e23748147', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='8a69bae9-be41-4308-9b5b-1361c37e91bd' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='9c57b490-2a97-4cbd-ac5d-69c1529c89a1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:59:38.762', EXPIR_DT='', LUI_ID='502e1be7-57db-4218-a293-1a3e23748147', PERS_ID='C.CARROLLS', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='e6981135-fec1-4e2f-a1e6-7177b23f8a67' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('502e1be7-57db-4218-a293-1a3e23748147', '0565fc7b-c2ac-4aad-b785-c3e38e65f1f1')
/
delete from KSEN_LUI_IDENT where ID='5dacf6db-0e57-4785-a7bb-eaf759002f2b' and VER_NBR=1
/
insert into KSEN_SCHED (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_STATE, SCHED_TYPE, ID) values ('a9a1aeef-8791-49a8-981d-60267d8bf3de', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'kuali.atp.2012CETerm1', '', '', '', 'kuali.scheduling.schedule.state.active', 'kuali.scheduling.schedule.type.schedule', '7d66ee9c-62b3-4b40-8c89-053e1897c389')
/
insert into KSEN_SCHED_CMP (OBJ_ID, TBA_IND, ROOM_ID, SCHED_ID, ID) values ('9999adf7-d35b-4f86-aa13-222be853b0e4', '0', '361cd43a-094c-47cd-ab50-6f097191ba22', '7d66ee9c-62b3-4b40-8c89-053e1897c389', 'a44ca83e-ef26-4bc4-a95a-ed001da725c6')
/
update KSEN_SCHED_RQST_CMP set OBJ_ID='', TBA_IND='0', SCHED_RQST_ID='00a4d917-22b1-481b-8f14-0c6be50df26c' where ID='77d288da-8b28-4b7e-8ee4-c89f96d2677c'
/
update KSEN_SCHED_RQST set OBJ_ID='a2ce0f4e-1844-408f-9cc1-5c86ea1414f3', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', NAME='Schedule request for BSCI330 - Z', DESCR_PLAIN='', SCHED_RQST_STATE='kuali.scheduling.schedule.request.state.created', SCHED_RQST_TYPE='kuali.scheduling.schedule.request.type.schedule.request', SCHED_ID='7d66ee9c-62b3-4b40-8c89-053e1897c389', SCHED_RQST_SET_ID='c789c249-e49d-49b7-9c10-36da6f47f34c' where ID='00a4d917-22b1-481b-8f14-0c6be50df26c' and VER_NBR=0
/
delete from KSEN_SCHED_RQST_CMP_BLDG where CMP_ID='77d288da-8b28-4b7e-8ee4-c89f96d2677c'
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('77d288da-8b28-4b7e-8ee4-c89f96d2677c', '89929ead-579b-4f51-bf64-2797f963848b')
/
delete from KSEN_SCHED_RQST_CMP_CAMPUS where CMP_ID='77d288da-8b28-4b7e-8ee4-c89f96d2677c'
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('77d288da-8b28-4b7e-8ee4-c89f96d2677c', 'UMCP')
/
delete from KSEN_SCHED_RQST_CMP_ROOM where CMP_ID='77d288da-8b28-4b7e-8ee4-c89f96d2677c'
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('77d288da-8b28-4b7e-8ee4-c89f96d2677c', '361cd43a-094c-47cd-ab50-6f097191ba22')
/
delete from KSEN_SCHED_RQST_CMP_TMSLOT where CMP_ID='77d288da-8b28-4b7e-8ee4-c89f96d2677c'
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('77d288da-8b28-4b7e-8ee4-c89f96d2677c', 'df74274b-8772-4674-af6b-880d1c74c47e')
/
insert into KSEN_SCHED_CMP_TMSLOT (SCHED_CMP_ID, TM_SLOT_ID) values ('a44ca83e-ef26-4bc4-a95a-ed001da725c6', 'df74274b-8772-4674-af6b-880d1c74c47e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c0d39f3b-d3df-4778-bbbf-793b44dac417', 0, 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'admin', TIMESTAMP '2013-06-04 14:50:18.664', 'Z', '', '', '', '0fb3f43b-5386-4656-a2e0-9ee48c187df6', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '8f2ba4c6-bd9d-4dd7-874c-a46c7cb9a747')
/
update KSEN_LUI_ATTR set OBJ_ID='a98fae42-e6c3-4b6b-9866-868d4c8c571a', ATTR_KEY='kuali.attribute.course.evaluation.indicator', OWNER_ID='0fb3f43b-5386-4656-a2e0-9ee48c187df6', ATTR_VALUE='0' where ID='1abb4993-e302-4940-a28d-6a747bf501d1'
/
update KSEN_LUI set OBJ_ID='d7c9c004-06f2-45d4-ae15-af309ce43ac2', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='02b2af95-54f8-4d0a-9ec8-4d7fb31c36e1', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.approved', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='0fb3f43b-5386-4656-a2e0-9ee48c187df6' and VER_NBR=1
/
update KSEN_LUI_ATTR set OBJ_ID='bdeb2d79-ffdb-4ddc-ba96-d2113818299f', ATTR_KEY='kuali.attribute.max.enrollment.is.estimate', OWNER_ID='0fb3f43b-5386-4656-a2e0-9ee48c187df6', ATTR_VALUE='0' where ID='04dbb23e-1173-405e-aa19-3495eb8533cf'
/
update KSEN_LUI_IDENT set OBJ_ID='c0d39f3b-d3df-4778-bbbf-793b44dac417', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', LUI_CD='Z', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='0fb3f43b-5386-4656-a2e0-9ee48c187df6', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8f2ba4c6-bd9d-4dd7-874c-a46c7cb9a747' and VER_NBR=0
/
update KSEN_LUI_LU_CD set OBJ_ID='640dbaea-c716-43a8-ae21-b0602ed86b05', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', DESCR_FORMATTED='', LUI_ID='0fb3f43b-5386-4656-a2e0-9ee48c187df6', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='10d1e1c0-a0bd-4766-b341-01e0a0e36817' and VER_NBR=1
/
update KSEN_LPR set OBJ_ID='b27c8604-101c-4b7d-bac3-90474165153b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:50:18.664', COMMIT_PERCT='100.0', EFF_DT=TIMESTAMP '2013-06-04 14:59:54.552', EXPIR_DT='', LUI_ID='0fb3f43b-5386-4656-a2e0-9ee48c187df6', PERS_ID='O.DUANES', LPR_STATE='kuali.lpr.state.active', LPR_TYPE='kuali.lpr.type.instructor.main' where ID='07a93d02-bb1f-4056-ae6b-f4a352b22541' and VER_NBR=0
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('0fb3f43b-5386-4656-a2e0-9ee48c187df6', '7d66ee9c-62b3-4b40-8c89-053e1897c389')
/
delete from KSEN_LUI_IDENT where ID='319f7cfa-82f5-48bd-b51f-69d58044ab8c' and VER_NBR=1
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('47c0bcc8-4c07-40c4-ad8a-a1085f7cfc65', 'kuali.soc.scheduling.state.completed', 'bc662f64-5078-431a-bc66-e4e586ef38f3', '2013-06-04T14:59:54-0400', 'bed7c851-9ae9-45e0-bdbe-2ddc5b68e331')
/
update KSEN_SOC set OBJ_ID='5e67a0c4-e1e7-41d2-bf8e-3fd5dd00729e', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 14:59:54.733', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.locked', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2012CETerm1', UNITS_CONTENT_OWNER_ID='' where ID='bc662f64-5078-431a-bc66-e4e586ef38f3' and VER_NBR=2
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('51e1f0d9-1c89-4c50-aa11-11965fb1823b', 'kuali.soc.state.finaledits', 'bc662f64-5078-431a-bc66-e4e586ef38f3', '2013-06-04T15:06:02-0400', '8e24cf3d-b0ae-40b4-9814-caaddb081530')
/
update KSEN_SOC set OBJ_ID='5e67a0c4-e1e7-41d2-bf8e-3fd5dd00729e', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:02.933', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.finaledits', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2012CETerm1', UNITS_CONTENT_OWNER_ID='' where ID='bc662f64-5078-431a-bc66-e4e586ef38f3' and VER_NBR=3
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0738ddb3-3a5d-43b5-84a3-7419a8483bd7', 'kuali.soc.state.publishing', 'bc662f64-5078-431a-bc66-e4e586ef38f3', '2013-06-04T15:06:08-0400', '55e0ecad-4cdc-4172-af6b-5f4a26c969e4')
/
update KSEN_SOC set OBJ_ID='5e67a0c4-e1e7-41d2-bf8e-3fd5dd00729e', VER_NBR=5, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.5', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.publishing', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2012CETerm1', UNITS_CONTENT_OWNER_ID='' where ID='bc662f64-5078-431a-bc66-e4e586ef38f3' and VER_NBR=4
/
update KSEN_LUI set OBJ_ID='df57e4f6-18f2-4c5a-85c3-c394c5284696', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='25f408c6-e481-48cb-82f8-76baf92f9a31', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=180, MIN_SEATS=180, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='c186e825-424e-48b4-86ae-bfc6aeffdfee' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='67f6fa83-ecdd-4ba4-96c1-78ab2acb6196', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='c186e825-424e-48b4-86ae-bfc6aeffdfee', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='41d3c4d7-2cc6-46e9-8829-484e1104dde2' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='31e827eb-c6dd-44d9-ab02-7b596d9ce6be', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='c186e825-424e-48b4-86ae-bfc6aeffdfee', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='6e11902d-6901-4919-a3a2-0e8f60128870' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='c186e825-424e-48b4-86ae-bfc6aeffdfee'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('c186e825-424e-48b4-86ae-bfc6aeffdfee', '76162f86-f223-46ac-a23c-636fd8e3701e')
/
update KSEN_LUI set OBJ_ID='a7c1706e-9732-42ec-98f0-fd67ae054af2', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='9c7796d1-4985-4e4f-a376-f94735348147', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture/Discussion', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture/Discussion', DESCR_PLAIN='Courses with Lecture/Discussion', REF_URL='' where ID='56d750c7-44db-4fc0-a891-f09c6f86d55f' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='79c5c0cc-3cda-4bea-ac55-db0f48973dd4', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture/Discussion', LUI_ID='56d750c7-44db-4fc0-a891-f09c6f86d55f', ORGID='', SHRT_NAME='LEC/DIS', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='40727aa6-af5e-40ba-886c-f7a810fe1d6d' and VER_NBR=1
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='56d750c7-44db-4fc0-a891-f09c6f86d55f'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('56d750c7-44db-4fc0-a891-f09c6f86d55f', 'kuali.lui.type.activity.offering.discussion')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('56d750c7-44db-4fc0-a891-f09c6f86d55f', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='73952c20-7ae1-45fc-ba3b-531e584ffa71', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='a80b4adf-214f-4e33-8492-af5b9beb48e8', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='Interpretation of select literature and art of the ancient Mediterranean world with a view to illuminating the antecedents of modern culture; religion and myth in the ancient near East; Greek philosophical, scientific, and literary invention; and the Roman tradition in politics and administration.', LUI_STATE='kuali.lui.course.offering.state.offered', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='HIST110 CO', DESCR_PLAIN='Interpretation of select literature and art of the ancient Mediterranean world with a view to illuminating the antecedents of modern culture; religion and myth in the ancient near East; Greek philosophical, scientific, and literary invention; and the Roman tradition in politics and administration.', REF_URL='null' where ID='4a11a872-7f15-4fa4-b3bb-31b02e1b945e' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='72c5123a-8268-4461-a98b-14e48b1821d6', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='HIST110', DIVISION='HIST', IDENT_LEVEL='', LNG_NAME='The Ancient World', LUI_ID='4a11a872-7f15-4fa4-b3bb-31b02e1b945e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='7c0f16ca-c9e0-4bcb-aae9-bb669ce77786' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='307a9b93-2724-4982-b690-f71b0bd791ca', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='4a11a872-7f15-4fa4-b3bb-31b02e1b945e', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='dba1ae29-977e-4aed-86c2-c7285f8a3775' and VER_NBR=1
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='4a11a872-7f15-4fa4-b3bb-31b02e1b945e'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('4a11a872-7f15-4fa4-b3bb-31b02e1b945e', '3213375036')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='4a11a872-7f15-4fa4-b3bb-31b02e1b945e'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4a11a872-7f15-4fa4-b3bb-31b02e1b945e', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4a11a872-7f15-4fa4-b3bb-31b02e1b945e', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4a11a872-7f15-4fa4-b3bb-31b02e1b945e', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4a11a872-7f15-4fa4-b3bb-31b02e1b945e', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='95d23b6e-6f61-4960-960b-d88ff421df17', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='5e58f009-b2dd-4770-ad41-b750dbdfe8b4', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=288, MIN_SEATS=288, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='c7db47af-65a5-4c58-a5d8-95deae522701' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='bdc4bd75-d469-40e3-abcc-577d95bfa5bc', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='c7db47af-65a5-4c58-a5d8-95deae522701', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='6dedb62c-f5d3-4fa0-8957-9d46de8c19e6' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='3c98d5f1-05d2-486e-8c2f-568395a2cbbb', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='c7db47af-65a5-4c58-a5d8-95deae522701', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='39e2713e-b040-4ec8-994b-0b8b1cd97c39' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='c7db47af-65a5-4c58-a5d8-95deae522701'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('c7db47af-65a5-4c58-a5d8-95deae522701', 'a1627020-4503-4693-9a25-6e9c95fdafa4')
/
update KSEN_LUI set OBJ_ID='d1bc521f-4f7c-44fc-9460-1041292ae20a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture/Lab', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture/Lab', DESCR_PLAIN='Courses with Lecture/Lab', REF_URL='' where ID='49adf4bd-2a31-4054-879d-1f1a1abe3682' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='278d2019-6cb0-4b01-a461-7063520c5af8', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture/Lab', LUI_ID='49adf4bd-2a31-4054-879d-1f1a1abe3682', ORGID='', SHRT_NAME='LEC/LAB', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='21d0795e-e140-4280-91ff-8ca0eca5bdce' and VER_NBR=1
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='49adf4bd-2a31-4054-879d-1f1a1abe3682'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('49adf4bd-2a31-4054-879d-1f1a1abe3682', 'kuali.lui.type.activity.offering.lab')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('49adf4bd-2a31-4054-879d-1f1a1abe3682', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='42af519d-6bb1-4570-b410-44de217be873', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='CLUID-BSCI330-200708000000', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='Biochemical and physiological mechanisms underlying cellular function. Properties of cells which make life possible and mechanisms by which cells provide energy, reproduce, and regulate and integrate with each other and their environment.', LUI_STATE='kuali.lui.course.offering.state.offered', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='BSCI330 CO', DESCR_PLAIN='Biochemical and physiological mechanisms underlying cellular function. Properties of cells which make life possible and mechanisms by which cells provide energy, reproduce, and regulate and integrate with each other and their environment.', REF_URL='null' where ID='18cccabd-f5bf-4ac7-8367-e9031640a107' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='a5c579d3-e42a-4996-8b69-4260ac6cf8d6', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='BSCI330', DIVISION='BSCI', IDENT_LEVEL='', LNG_NAME='Cell Biology and Physiology', LUI_ID='18cccabd-f5bf-4ac7-8367-e9031640a107', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b406f250-85d2-414b-8027-76a2fb44f7a8' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='37d301bd-84d9-4872-bc4a-ca339f9961c9', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='18cccabd-f5bf-4ac7-8367-e9031640a107', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='c105bbdf-502e-40d6-bdf3-e7c08ba44925' and VER_NBR=1
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='18cccabd-f5bf-4ac7-8367-e9031640a107'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('18cccabd-f5bf-4ac7-8367-e9031640a107', '576639460')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='18cccabd-f5bf-4ac7-8367-e9031640a107'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('18cccabd-f5bf-4ac7-8367-e9031640a107', 'kuali.creditType.credit.degree.4.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('18cccabd-f5bf-4ac7-8367-e9031640a107', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('18cccabd-f5bf-4ac7-8367-e9031640a107', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('18cccabd-f5bf-4ac7-8367-e9031640a107', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='5efc9d51-adb0-46f2-a0c8-5fad631fbe40', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='b117ee2a-8141-4a83-8bf9-c9c667db3ed7', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='7cc4db78-b0e8-40cc-8f98-33c95a969f6e' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='f1cfeeb6-68a3-47ec-8a06-8789202b967d', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='E', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='7cc4db78-b0e8-40cc-8f98-33c95a969f6e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='5bb62182-78b8-4183-8417-7c8cd842ad0f' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='eb76594b-4dbd-4c6e-9ac7-48ad487fef7d', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='7cc4db78-b0e8-40cc-8f98-33c95a969f6e', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='6dfce852-d7f8-43dc-988a-b982497e0f65' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='7cc4db78-b0e8-40cc-8f98-33c95a969f6e'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('7cc4db78-b0e8-40cc-8f98-33c95a969f6e', 'fbbbe684-d139-47d8-bb35-7e3240eb820e')
/
update KSEN_LUI set OBJ_ID='bc5f3d7d-020f-419e-ac58-77be494410fe', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='f1dee561-a3e7-41b5-a1e0-0d4a77d7987e', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture/Discussion', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture/Discussion', DESCR_PLAIN='Courses with Lecture/Discussion', REF_URL='' where ID='8e8eef1c-0b14-45ff-9f3f-b25a7d00769e' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='820fe705-a7ee-491e-9f6e-827ac70c3b55', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture/Discussion', LUI_ID='8e8eef1c-0b14-45ff-9f3f-b25a7d00769e', ORGID='', SHRT_NAME='LEC/DIS', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b0b63824-a3b9-48bb-87c5-0861e7561e9f' and VER_NBR=1
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='8e8eef1c-0b14-45ff-9f3f-b25a7d00769e'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('8e8eef1c-0b14-45ff-9f3f-b25a7d00769e', 'kuali.lui.type.activity.offering.discussion')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('8e8eef1c-0b14-45ff-9f3f-b25a7d00769e', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='5ca8913d-5584-4924-a0ea-0b3f7b3a67d3', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='CLUID-HIST204-201101000000', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='An exploration of the roots of modern science from the ancient Greeks through the medieval and early modern periods. Focus on the men and women who helped to create the sciences as well as changing public perceptions of their disciplines.', LUI_STATE='kuali.lui.course.offering.state.offered', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='HIST204 CO', DESCR_PLAIN='An exploration of the roots of modern science from the ancient Greeks through the medieval and early modern periods. Focus on the men and women who helped to create the sciences as well as changing public perceptions of their disciplines.', REF_URL='null' where ID='565a6a2c-8621-4aba-a511-44348a904809' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='b6aeeb56-d2f1-46fb-bb2f-80405199622c', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='HIST204', DIVISION='HIST', IDENT_LEVEL='', LNG_NAME='Introduction to the History of Science', LUI_ID='565a6a2c-8621-4aba-a511-44348a904809', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='0bef670e-1ca2-499a-87f6-4a77d31ce44e' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='c468db13-b97b-451e-9961-52332659e5a2', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='565a6a2c-8621-4aba-a511-44348a904809', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='bc83033f-bf55-4a11-8e24-2c502abec0df' and VER_NBR=1
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='565a6a2c-8621-4aba-a511-44348a904809'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('565a6a2c-8621-4aba-a511-44348a904809', '3213375036')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='565a6a2c-8621-4aba-a511-44348a904809'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('565a6a2c-8621-4aba-a511-44348a904809', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('565a6a2c-8621-4aba-a511-44348a904809', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('565a6a2c-8621-4aba-a511-44348a904809', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('565a6a2c-8621-4aba-a511-44348a904809', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='e7c8dd67-047a-494a-a1bf-29c3f824da9f', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='e073a783-300c-4e22-8e41-f661639d3ce5', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=46, MIN_SEATS=46, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='9bc78b34-81d7-4869-9070-d90076845599' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='f38b772a-e6a9-46e1-9723-d4cbc3f10b6c', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='9bc78b34-81d7-4869-9070-d90076845599', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='9dd2de08-311d-4bc2-b238-28559f1b986c' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='b951ab6f-751a-4c0a-a6c6-c338edbc6c07', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='9bc78b34-81d7-4869-9070-d90076845599', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='e0224bd6-e295-4cde-a619-28f4ef1fb13d' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='9bc78b34-81d7-4869-9070-d90076845599'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('9bc78b34-81d7-4869-9070-d90076845599', '59f01f5a-fe89-49e2-bb0a-6fd77cb382e8')
/
update KSEN_LUI set OBJ_ID='9fe2fd51-a876-4387-98f1-623cc71dd5da', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='fbe00f3e-788a-460c-b686-3303215621fa', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture/Discussion', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture/Discussion', DESCR_PLAIN='Courses with Lecture/Discussion', REF_URL='' where ID='3f00e87c-ad8f-43ae-a84e-e640a77df3e8' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='dc6a5036-f60c-4105-95c5-ad2165bd55fb', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture/Discussion', LUI_ID='3f00e87c-ad8f-43ae-a84e-e640a77df3e8', ORGID='', SHRT_NAME='LEC/DIS', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a33d63e4-723a-4ae8-87f3-9d544b347a99' and VER_NBR=1
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='3f00e87c-ad8f-43ae-a84e-e640a77df3e8'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('3f00e87c-ad8f-43ae-a84e-e640a77df3e8', 'kuali.lui.type.activity.offering.discussion')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('3f00e87c-ad8f-43ae-a84e-e640a77df3e8', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='b3f512c8-6800-498a-97b6-a2f3da58e0e7', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='31831b51-5be1-4f61-961f-a23d60ad2c53', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='The chemistry of carbon: aliphatic compounds, aromatic compounds, stere chemistry, halides, amines, and amides, acids, esters, carbohydrates, and natural products. This course is intended for students in curricula requiring only one semester of organic chemistry. Students requiring two or more years of chemistry should register for CHEM231/232 or CHEM237.', LUI_STATE='kuali.lui.course.offering.state.offered', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='CHEM105 CO', DESCR_PLAIN='The chemistry of carbon: aliphatic compounds, aromatic compounds, stere chemistry, halides, amines, and amides, acids, esters, carbohydrates, and natural products. This course is intended for students in curricula requiring only one semester of organic chemistry. Students requiring two or more years of chemistry should register for CHEM231/232 or CHEM237.', REF_URL='null' where ID='fd03541a-0e49-4725-a56c-048ca06ae3e3' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='2c785cbc-b30a-429f-b8c8-65579e063b15', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='CHEM105', DIVISION='CHEM', IDENT_LEVEL='', LNG_NAME='Fundamental of Organic and Biochemistry', LUI_ID='fd03541a-0e49-4725-a56c-048ca06ae3e3', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a350733f-eb6d-407a-909a-a84f59708293' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='3bea2ae7-7037-4554-b193-0e5a5064d97d', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='fd03541a-0e49-4725-a56c-048ca06ae3e3', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='f6b0ffb7-a597-40f6-a012-e0bd6fb49ed0' and VER_NBR=1
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='fd03541a-0e49-4725-a56c-048ca06ae3e3'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('fd03541a-0e49-4725-a56c-048ca06ae3e3', '4284516367')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='fd03541a-0e49-4725-a56c-048ca06ae3e3'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('fd03541a-0e49-4725-a56c-048ca06ae3e3', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('fd03541a-0e49-4725-a56c-048ca06ae3e3', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('fd03541a-0e49-4725-a56c-048ca06ae3e3', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('fd03541a-0e49-4725-a56c-048ca06ae3e3', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='1ad7231a-eac1-47eb-b978-54630c9b3497', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='778c4a89-a3be-48c8-ae02-afbdf2f34970', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='c4771b96-a10a-4857-aaf8-bd4beb3c0f85' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='0347fabc-06aa-43a4-8b7b-3cd11c5f8176', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='C', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='c4771b96-a10a-4857-aaf8-bd4beb3c0f85', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='583e1ae6-28c1-4ddb-8de2-5d43417603c0' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='04998963-bfbd-4d99-bdb5-4f6eaf31f517', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='c4771b96-a10a-4857-aaf8-bd4beb3c0f85', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='61ca476a-ce8e-40a9-9fd3-526e37d2dd45' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='c4771b96-a10a-4857-aaf8-bd4beb3c0f85'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('c4771b96-a10a-4857-aaf8-bd4beb3c0f85', '69f53e10-9bba-4f03-aa01-0cb24c53dad5')
/
update KSEN_LUI set OBJ_ID='0749efa9-8c39-444c-8eef-8aeebb0835db', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='9c7796d1-4985-4e4f-a376-f94735348147', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1009', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1009', DESCR_PLAIN='1009', REF_URL='' where ID='a761a237-617e-4b18-86cb-bf48f26ba274' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='0d205ae7-9754-4f68-8c31-cffcf2d2b4e7', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='a761a237-617e-4b18-86cb-bf48f26ba274', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='87b72beb-57d2-4c3b-950b-de4430ee3398' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='c197a64f-11e9-44b4-b044-085f80050415', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='00b2aaf3-5138-4066-a4ed-0d1d78c47f71', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='980c2079-cf32-4e87-b17d-b826bff9aacd' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='aaef043e-5c23-43ed-8865-34f01f3760e6', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='C', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='980c2079-cf32-4e87-b17d-b826bff9aacd', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8ac410a2-52e3-4eee-9eea-8bc92eda06e6' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='293afcf9-4763-40bb-a9f0-76099c9eee34', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='980c2079-cf32-4e87-b17d-b826bff9aacd', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='64673e45-9a89-43ac-9bd4-21ee93d33762' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='980c2079-cf32-4e87-b17d-b826bff9aacd'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('980c2079-cf32-4e87-b17d-b826bff9aacd', '848813f3-2b10-44ed-b2e5-a2dba5850f49')
/
update KSEN_LUI set OBJ_ID='6704783d-95b2-4a46-bbc4-87d9dbe12ee7', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1006', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1006', DESCR_PLAIN='1006', REF_URL='' where ID='ef21cb78-14d6-45d4-bf41-5858febda5c6' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='ba720ca8-f621-4842-b9f7-4381f3f6b1e4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='ef21cb78-14d6-45d4-bf41-5858febda5c6', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='d99aba49-a63d-4f65-b139-96bea674b967' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='6daaa8a4-5e08-4e22-a000-123c7f95f47e', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='13d328e5-e4d2-4be5-b63c-3c227edb36fa', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=96, MIN_SEATS=96, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='4ed64abb-e257-4c18-af2a-edc034278b89' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='030cdc3b-745d-47db-92b9-26ac8910e0b4', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='4ed64abb-e257-4c18-af2a-edc034278b89', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='781546b1-3f38-46b4-a79c-7cffe0885f01' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='a73c02d6-ac97-4ffa-914c-bce8d1cb1c6c', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='4ed64abb-e257-4c18-af2a-edc034278b89', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b3a71a8e-1384-461b-badc-00dfe1644a16' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='4ed64abb-e257-4c18-af2a-edc034278b89'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('4ed64abb-e257-4c18-af2a-edc034278b89', '0dafdeac-908c-45a8-b817-ad74eedbd307')
/
update KSEN_LUI set OBJ_ID='17a94bde-0d50-4015-9fff-6c0e15fac02e', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='c6a8b277-7dba-4e06-9f9c-47e5dc936a68', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture/Discussion', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture/Discussion', DESCR_PLAIN='Courses with Lecture/Discussion', REF_URL='' where ID='c5825a7c-743a-460d-9d98-beb9d1a8e087' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='10a288b4-7150-4a6d-a776-0ff40a16d6d8', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture/Discussion', LUI_ID='c5825a7c-743a-460d-9d98-beb9d1a8e087', ORGID='', SHRT_NAME='LEC/DIS', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8befee5b-8175-4dfd-9b9a-eb19d88e4d2c' and VER_NBR=1
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='c5825a7c-743a-460d-9d98-beb9d1a8e087'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('c5825a7c-743a-460d-9d98-beb9d1a8e087', 'kuali.lui.type.activity.offering.discussion')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('c5825a7c-743a-460d-9d98-beb9d1a8e087', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='1e0e699a-e95a-4fcd-9004-0192d670cebf', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='5030c77c-dcd9-4906-8d7a-18c41bf3c44e', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='A detailed study of selected major texts of American literature from th 17th century to the 20th century. Issues such as race, gender, and regionalism. Authors such as Franklin, Hawthorne, Dickinson, Hemingway, and Morrison.', LUI_STATE='kuali.lui.course.offering.state.offered', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='ENGL313 CO', DESCR_PLAIN='A detailed study of selected major texts of American literature from th 17th century to the 20th century. Issues such as race, gender, and regionalism. Authors such as Franklin, Hawthorne, Dickinson, Hemingway, and Morrison.', REF_URL='null' where ID='22b1caeb-5ad7-4de7-93a5-8db056d43984' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='d3ffe01c-5f3e-4893-a19e-bb0236335609', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='ENGL313', DIVISION='ENGL', IDENT_LEVEL='', LNG_NAME='American Literature', LUI_ID='22b1caeb-5ad7-4de7-93a5-8db056d43984', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='c8470fda-8209-4e5d-82da-7bfd036df384' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='a43b65ef-5f6d-4d8d-864e-e95d089e4935', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='22b1caeb-5ad7-4de7-93a5-8db056d43984', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='67233c95-26ee-4b50-9a1e-8fc9a9a10f94' and VER_NBR=1
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='22b1caeb-5ad7-4de7-93a5-8db056d43984'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('22b1caeb-5ad7-4de7-93a5-8db056d43984', '2677933260')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='22b1caeb-5ad7-4de7-93a5-8db056d43984'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('22b1caeb-5ad7-4de7-93a5-8db056d43984', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('22b1caeb-5ad7-4de7-93a5-8db056d43984', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('22b1caeb-5ad7-4de7-93a5-8db056d43984', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('22b1caeb-5ad7-4de7-93a5-8db056d43984', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='4385fbd0-ef56-4967-8d3e-0d9090016e19', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='29737d5a-57a4-49c5-a8fb-2dd1eb0ea581', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=48, MIN_SEATS=48, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='f9ecdacf-a144-4319-9a3e-35b0ed00982b' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='4a7c077e-b34b-4cfb-8cee-fe315de993e6', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='f9ecdacf-a144-4319-9a3e-35b0ed00982b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='cfad4dd2-40f0-4078-a78e-31042c87e055' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='1e42e9e9-d017-493b-88a3-af58870a624b', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='f9ecdacf-a144-4319-9a3e-35b0ed00982b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='aa76ecbd-61fc-42a9-8822-1503973ac19a' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='f9ecdacf-a144-4319-9a3e-35b0ed00982b'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('f9ecdacf-a144-4319-9a3e-35b0ed00982b', '411fca89-1de0-4284-b09b-8570aa4d63a1')
/
update KSEN_LUI set OBJ_ID='b3b7f446-3734-4e9c-9ac3-ec4d74ad3046', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='25889d4f-0612-424c-87a5-e96f60f3e096', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture/Lab', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture/Lab', DESCR_PLAIN='Courses with Lecture/Lab', REF_URL='' where ID='075fbf70-3d90-4d8f-b81f-0c8c9cdb794e' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='0062fec3-426b-4e8e-a6e6-b247da9e69d9', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture/Lab', LUI_ID='075fbf70-3d90-4d8f-b81f-0c8c9cdb794e', ORGID='', SHRT_NAME='LEC/LAB', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='c34cc25c-109a-41b7-9c48-3fb68b11cb3f' and VER_NBR=1
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='075fbf70-3d90-4d8f-b81f-0c8c9cdb794e'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('075fbf70-3d90-4d8f-b81f-0c8c9cdb794e', 'kuali.lui.type.activity.offering.lab')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('075fbf70-3d90-4d8f-b81f-0c8c9cdb794e', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='7ab68367-9925-4a20-bdd6-072dc74f4f91', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='CLUID-CHEM277-200608000000', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='Quantitative analysis, inorganic analytical chemistry, and an introduction to bio-analytical instrumentation and techniques.', LUI_STATE='kuali.lui.course.offering.state.offered', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='CHEM277 CO', DESCR_PLAIN='Quantitative analysis, inorganic analytical chemistry, and an introduction to bio-analytical instrumentation and techniques.', REF_URL='null' where ID='ca0c00f4-ff28-4bce-8ba7-faad4692f320' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='c02cd516-64fe-450c-ab7d-5abfcccb5aa6', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='CHEM277', DIVISION='CHEM', IDENT_LEVEL='', LNG_NAME='Fundamentals of Analytical and Bioanalytical Chemistry Laboratory', LUI_ID='ca0c00f4-ff28-4bce-8ba7-faad4692f320', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='da57f2de-c49a-4b2f-a03c-d5b96455d1f6' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='4fe60067-c0ec-4e31-8559-e1192cfea184', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='ca0c00f4-ff28-4bce-8ba7-faad4692f320', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='0c40bf5b-b799-4b66-9d86-17f272c04087' and VER_NBR=1
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='ca0c00f4-ff28-4bce-8ba7-faad4692f320'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('ca0c00f4-ff28-4bce-8ba7-faad4692f320', '4284516367')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='ca0c00f4-ff28-4bce-8ba7-faad4692f320'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ca0c00f4-ff28-4bce-8ba7-faad4692f320', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ca0c00f4-ff28-4bce-8ba7-faad4692f320', 'kuali.resultComponent.grade.letter')
/
update KSEN_LUI set OBJ_ID='cec659a7-2921-4f11-9861-06e9bd5c4043', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='6be4f320-fa19-46d5-b560-a756adba1c46', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='5df66739-494e-4c00-b440-9f9f4ad00e2b' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='8e09457f-401d-44e1-94d9-98c0bf2400dc', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='H', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='5df66739-494e-4c00-b440-9f9f4ad00e2b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='db739429-b770-4807-9f53-71e5a40b03ea' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='4d3ea88a-f6cc-45e6-9531-1a2949bc5fd2', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='5df66739-494e-4c00-b440-9f9f4ad00e2b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='8c902fb2-1aed-452c-99a6-c328ed6476ab' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='5df66739-494e-4c00-b440-9f9f4ad00e2b'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('5df66739-494e-4c00-b440-9f9f4ad00e2b', 'b4e91c2b-ae04-46cb-a814-31f92c32246d')
/
update KSEN_LUI set OBJ_ID='8de459a7-1abd-41d8-a90f-553beb8a502a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='9c7796d1-4985-4e4f-a376-f94735348147', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1003', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1003', DESCR_PLAIN='1003', REF_URL='' where ID='2a2394dd-a67c-40e4-be46-d498ccaead5b' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='fbb0da28-98c7-48cc-acf2-57f95caa67f5', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='2a2394dd-a67c-40e4-be46-d498ccaead5b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='205ac402-e333-4198-bdac-57ff546d7f48' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='c6b0fc9d-dbbc-4da3-93c6-97d09ed1ea3a', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='347af0e5-98a9-4b65-9527-2976bcf251a0', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='b2789b95-350b-4f3a-9183-7fdfd9108283' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='09e85698-97ce-414c-92a1-efcdedbab983', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='F', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='b2789b95-350b-4f3a-9183-7fdfd9108283', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='2ffc8c33-9a3b-4300-a218-8842e3a43831' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='21c0d420-0dda-41d1-a03d-f183605e88e4', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='b2789b95-350b-4f3a-9183-7fdfd9108283', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='13d63d98-a4ce-49a2-b4b6-446274ba77d7' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='b2789b95-350b-4f3a-9183-7fdfd9108283'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('b2789b95-350b-4f3a-9183-7fdfd9108283', '349d247d-6c93-4271-aa3a-3d5221ef15e8')
/
update KSEN_LUI set OBJ_ID='cfbe652d-fa0e-424e-9c33-afbbe4bc792d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='9c7796d1-4985-4e4f-a376-f94735348147', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1008', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1008', DESCR_PLAIN='1008', REF_URL='' where ID='d2f8c574-411b-4a58-a680-c9f57acbd610' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='4969f69d-1821-4e76-91da-96cf6d02dde6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='d2f8c574-411b-4a58-a680-c9f57acbd610', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1b8683ac-5822-4385-b0f5-5140bb0bd18c' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='447bee9e-3990-4fc5-a79e-5df7b4290830', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='12a0a27c-a467-482f-8ccf-fccb66fbddb9', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=35, MIN_SEATS=35, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='73c82da9-c4a1-411b-b765-b34d5c13bca6' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='73164599-61b8-4587-ad90-5c83828ed22f', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='73c82da9-c4a1-411b-b765-b34d5c13bca6', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='63e9e393-9977-419f-aee0-7c4f6337aeb5' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='c770c638-9af2-4b39-bbbf-139c74d4cfa7', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='73c82da9-c4a1-411b-b765-b34d5c13bca6', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='19358090-0881-4c43-b4c8-e0edc1ae89dd' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='73c82da9-c4a1-411b-b765-b34d5c13bca6'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('73c82da9-c4a1-411b-b765-b34d5c13bca6', '32eb4c1b-a83c-4ae8-9aa5-277d23db7363')
/
update KSEN_LUI set OBJ_ID='9e95e8c1-bf3a-4ca3-a6fe-92116fc1e8dd', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='8c293c08-3cd8-48d7-b65b-71073f10153b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture only', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture Only', DESCR_PLAIN='Courses with Lecture only', REF_URL='' where ID='591d7a10-ad5d-400c-8bad-e79019aece92' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='8134f55a-1c21-4c9c-b299-99ae2e6728a6', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture Only', LUI_ID='591d7a10-ad5d-400c-8bad-e79019aece92', ORGID='', SHRT_NAME='LEC', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='50d60b68-ef32-4a9b-a8c8-a9e34989715d' and VER_NBR=1
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='591d7a10-ad5d-400c-8bad-e79019aece92'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('591d7a10-ad5d-400c-8bad-e79019aece92', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='90dd3a9c-56da-4ed3-9780-52716f25ebe5', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='CLUID-WMST452-199501000000', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='Participation and portrayal of women in the mass media from colonial to contemporary times.', LUI_STATE='kuali.lui.course.offering.state.offered', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='WMST452 CO', DESCR_PLAIN='Participation and portrayal of women in the mass media from colonial to contemporary times.', REF_URL='null' where ID='92b5368e-1220-43d1-8e00-4afba2299e53' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='6dd2cd29-732b-4ed0-aad7-582f959b4ab1', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='WMST452', DIVISION='WMST', IDENT_LEVEL='', LNG_NAME='Women in the Media', LUI_ID='92b5368e-1220-43d1-8e00-4afba2299e53', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='db4e0c11-bffe-4a0b-9ca4-47a63ce24c24' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='9aa7bbe3-3fc8-4950-b548-55659fcc93b8', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='92b5368e-1220-43d1-8e00-4afba2299e53', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='0b80a9bd-7281-4abb-9e49-72c5621656d5' and VER_NBR=1
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='92b5368e-1220-43d1-8e00-4afba2299e53'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('92b5368e-1220-43d1-8e00-4afba2299e53', '4014660630')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='92b5368e-1220-43d1-8e00-4afba2299e53'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('92b5368e-1220-43d1-8e00-4afba2299e53', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('92b5368e-1220-43d1-8e00-4afba2299e53', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('92b5368e-1220-43d1-8e00-4afba2299e53', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('92b5368e-1220-43d1-8e00-4afba2299e53', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='56a731e6-1a9c-4b52-8951-a7727e77375c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='8c293c08-3cd8-48d7-b65b-71073f10153b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='ab1b3237-211b-4ad8-88f9-ba961f904dba' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='d1baec0e-e5e1-4554-9417-75fa420cfd09', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='ab1b3237-211b-4ad8-88f9-ba961f904dba', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='38220654-2526-46d5-8c53-3c28d41bdc5d' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='24b6b458-fdf2-4e30-ab14-feb8b9a95a40', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='c7ce59fa-08cf-483d-9ade-9c4dab5a94e3', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=35, MIN_SEATS=35, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='8719e6fd-34c8-409f-951c-d87d08a44a6b' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='dce140a2-7c1d-4249-bd31-e4c4aa019ac0', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='8719e6fd-34c8-409f-951c-d87d08a44a6b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='61d8e02b-47a8-4ecd-9c8b-a8c958821c9b' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='2424104c-219f-4e41-93fa-eab17923e88a', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='8719e6fd-34c8-409f-951c-d87d08a44a6b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='6f31f742-d214-4f1d-b4ec-11faf3c4445a' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='8719e6fd-34c8-409f-951c-d87d08a44a6b'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('8719e6fd-34c8-409f-951c-d87d08a44a6b', '9d119ce5-be68-488b-b8e9-e0648eaca3b0')
/
update KSEN_LUI set OBJ_ID='5a1732ea-5104-4773-bef8-e84d1f421da7', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='f9d59526-20d1-418c-864f-9da66959d0fd', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture only', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture Only', DESCR_PLAIN='Courses with Lecture only', REF_URL='' where ID='f17c3c48-6bc1-42c8-9bad-5ecbace05b2a' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='e443a755-94aa-4cfd-9252-92b1e13d6708', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture Only', LUI_ID='f17c3c48-6bc1-42c8-9bad-5ecbace05b2a', ORGID='', SHRT_NAME='LEC', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='f9dfddde-2cbb-4d7f-8c35-e2b794656656' and VER_NBR=1
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='f17c3c48-6bc1-42c8-9bad-5ecbace05b2a'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('f17c3c48-6bc1-42c8-9bad-5ecbace05b2a', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='b1d7772b-39e2-45a7-9c56-8c5be1aa4c74', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='18f8deda-26ce-4cb0-a66e-631f690f1650', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='Wide range of texts, genres, and themes from ancient and medieval Western traditions. Study of cultural, historical, and artistic forces shaping traditions, and the influence and relevance of those traditions to life in twenty-first century.', LUI_STATE='kuali.lui.course.offering.state.offered', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='ENGL201 CO', DESCR_PLAIN='Wide range of texts, genres, and themes from ancient and medieval Western traditions. Study of cultural, historical, and artistic forces shaping traditions, and the influence and relevance of those traditions to life in twenty-first century.', REF_URL='null' where ID='74101915-ee0e-44d5-8780-4301345fa925' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='07f711c1-8f5a-4389-9ebe-6c4deb71afec', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='ENGL201', DIVISION='ENGL', IDENT_LEVEL='', LNG_NAME='Inventing Western Literature: Ancient and Medieval Traditions', LUI_ID='74101915-ee0e-44d5-8780-4301345fa925', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='bbe4cdeb-cc5e-42db-85d0-a106112edb21' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='ee43d3ef-d4d1-432e-9fa7-24472f39c5e5', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='74101915-ee0e-44d5-8780-4301345fa925', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='3758b45f-3317-47f4-bf0a-99f0ad99f578' and VER_NBR=1
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='74101915-ee0e-44d5-8780-4301345fa925'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('74101915-ee0e-44d5-8780-4301345fa925', '2677933260')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='74101915-ee0e-44d5-8780-4301345fa925'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('74101915-ee0e-44d5-8780-4301345fa925', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('74101915-ee0e-44d5-8780-4301345fa925', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('74101915-ee0e-44d5-8780-4301345fa925', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('74101915-ee0e-44d5-8780-4301345fa925', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='f80152d1-4b8a-47aa-b13f-623986acaea8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='f9d59526-20d1-418c-864f-9da66959d0fd', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='52d37d84-aeb6-4402-b2a5-2a54fdd9eff3' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='3d911b24-ac9b-4300-a550-02957242457a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='52d37d84-aeb6-4402-b2a5-2a54fdd9eff3', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='803f5d7e-f5ca-4941-b832-8b9be03b4730' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='615802c9-95b2-404a-9e0b-29cc2caba09f', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='9f2b36b3-4761-4469-a11f-68064bb11909', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=80, MIN_SEATS=80, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='3aade379-d19f-4f49-ab85-ec4ff4f2bd4f' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='7a124cae-e9da-4405-a3eb-b84c95b9291a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='3aade379-d19f-4f49-ab85-ec4ff4f2bd4f', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='6197f66f-be5e-4df6-9252-f615402d01cc' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='f7048aa1-bcc2-430e-87e5-582695439370', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='3aade379-d19f-4f49-ab85-ec4ff4f2bd4f', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='930a024b-04da-4c2e-ab2a-daefb08ae305' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='3aade379-d19f-4f49-ab85-ec4ff4f2bd4f'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('3aade379-d19f-4f49-ab85-ec4ff4f2bd4f', '9903dd16-7d48-485d-ac59-e7eb1ce180e7')
/
update KSEN_LUI set OBJ_ID='4e6061c3-230c-4f86-beae-c4940c10fd14', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='f1dee561-a3e7-41b5-a1e0-0d4a77d7987e', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1004', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1004', DESCR_PLAIN='1004', REF_URL='' where ID='5a09d786-e292-4ab6-8b36-4aa2e97691c4' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='c632f656-7299-4ef2-a0e3-96cd77d036cd', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='5a09d786-e292-4ab6-8b36-4aa2e97691c4', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='bee6d11c-9b80-4d55-9f83-e7e5bee55b9c' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='d38afa57-bc17-41e3-ab5e-4955fe8e2f89', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='50f30065-8756-4f96-b186-b822f3973474', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='db7f5043-3473-4bb5-93fa-677394dc793a' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='5a4d7d52-4319-4c65-bffd-ab9179fdba51', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='D', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='db7f5043-3473-4bb5-93fa-677394dc793a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='11f8ea47-228b-4850-91de-33a8c3799d65' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='3eb6f35d-49b9-4e55-b0a2-419fb02129f8', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='db7f5043-3473-4bb5-93fa-677394dc793a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='8e263552-9308-4e30-b06e-0678d5d49e5e' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='db7f5043-3473-4bb5-93fa-677394dc793a'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('db7f5043-3473-4bb5-93fa-677394dc793a', 'd93711a8-c1e9-4fda-aef8-a00c8956ed9f')
/
update KSEN_LUI set OBJ_ID='e29125ed-595b-45ae-9d5f-8654937fb226', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='f1dee561-a3e7-41b5-a1e0-0d4a77d7987e', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1002', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1002', DESCR_PLAIN='1002', REF_URL='' where ID='30982dc9-c243-41ce-a90e-ac08c1885ad5' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='294f19a4-2c09-42e4-be36-dc0f62ee833f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='30982dc9-c243-41ce-a90e-ac08c1885ad5', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='2e80ff78-1e09-4f8e-9771-088691e25be8' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='5b5cc087-2a11-4a02-9317-47dc8425f027', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='4e9f21d1-522f-4b8a-b003-c8807e64609e', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='935a4774-97aa-4289-ab40-0059e05f57f9' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='357ebc55-e620-4874-b373-589994597380', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='J', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='935a4774-97aa-4289-ab40-0059e05f57f9', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='fb4d0dd6-1318-40dc-aba2-57017a318c14' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='866501a0-85d9-4ee6-bc4d-41dbb8725fe2', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='935a4774-97aa-4289-ab40-0059e05f57f9', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='1e3fe06f-d576-47f5-a280-c43bfc54c543' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='935a4774-97aa-4289-ab40-0059e05f57f9'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('935a4774-97aa-4289-ab40-0059e05f57f9', '3a41b34c-3e9e-4285-8a8a-0a587758569d')
/
update KSEN_LUI set OBJ_ID='2a5d61c6-2be7-42f4-bcd3-43ffc7a8ae87', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='9c7796d1-4985-4e4f-a376-f94735348147', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1004', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1004', DESCR_PLAIN='1004', REF_URL='' where ID='387544a4-f2e9-4cdf-a885-656caf944f47' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='d34a4a14-e779-4e45-a4f6-8ade395a2591', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='387544a4-f2e9-4cdf-a885-656caf944f47', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b25ff687-bc3f-4e2e-9fe2-3a4d08e7c7b9' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='a749bff6-ac08-447d-92ef-1ed723dcf90e', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='3d77a421-8c25-4609-86d5-1dc5e5f3ee5a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='03b568fc-aeed-4963-bfc9-4f6382d25dec' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='9e95aed0-5ae1-46a5-85bd-305328d2bd9d', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='03b568fc-aeed-4963-bfc9-4f6382d25dec', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='300717e6-f4df-4a87-86fa-728c33c084e4' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='de21fb10-0d84-41a6-8d57-a142bb4e7f31', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='03b568fc-aeed-4963-bfc9-4f6382d25dec', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b48547fb-07bd-4f8a-8fd2-50f1b65a55d4' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='03b568fc-aeed-4963-bfc9-4f6382d25dec'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('03b568fc-aeed-4963-bfc9-4f6382d25dec', '819c6961-48c3-4900-b2d9-5a5094289c55')
/
update KSEN_LUI set OBJ_ID='95e4db1c-ebe1-4c6c-b64f-d7f47cf13098', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1010', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1010', DESCR_PLAIN='1010', REF_URL='' where ID='e0ac2fd2-8ef9-4d95-958a-2098f3d2ec00' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='5de6414c-b019-4a84-a921-69decc778dbb', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='e0ac2fd2-8ef9-4d95-958a-2098f3d2ec00', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='91890b3d-9c79-4ecd-9341-14bf108caca9' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='3b177dd7-dac0-4c4e-9082-5aaf383ed35e', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='53b1ee34-2625-48cb-b183-c9b8893ac132', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='2d8c513f-6273-4a98-9cbd-5519c1857804' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='a6a822fa-08dd-4a34-9f1f-0f25c00cf469', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='J', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='2d8c513f-6273-4a98-9cbd-5519c1857804', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a45c45ec-14ca-4ec3-af0f-f4a1e083c86e' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='0575eda0-d38c-48b3-9c26-fbbd2a774f92', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='2d8c513f-6273-4a98-9cbd-5519c1857804', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='432a600d-0410-4760-8a40-799d8fe20a6a' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='2d8c513f-6273-4a98-9cbd-5519c1857804'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('2d8c513f-6273-4a98-9cbd-5519c1857804', '4146075b-12a9-4128-a7a4-9eb576675d02')
/
update KSEN_LUI set OBJ_ID='9ed5a214-a1d7-41db-995b-7e70f998baa9', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1008', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1008', DESCR_PLAIN='1008', REF_URL='' where ID='72c9412a-55fa-4d3c-8b21-622800ff35d4' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='11278af8-b72f-4f18-9081-e54d4c5c68c8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='72c9412a-55fa-4d3c-8b21-622800ff35d4', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='fa81bc3a-cd05-47e4-ae3d-ada9b439ae47' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='f9994839-aad3-43cf-b8a9-e4bd23c0a925', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='3bac0164-0547-4b66-898b-58405dc8898f', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='f03379f2-4435-4a27-b21e-8f45e130c97e' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='1382605c-5349-4d05-96e7-40468a5b57c6', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='P', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='f03379f2-4435-4a27-b21e-8f45e130c97e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='69689451-6818-4121-9f9e-346f29986bb8' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='2fc0f1dc-7f16-4643-a0f5-39ea84ae08f4', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='f03379f2-4435-4a27-b21e-8f45e130c97e', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='e1fe39ef-0f30-438e-b3ee-1f8beab5ef50' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='f03379f2-4435-4a27-b21e-8f45e130c97e'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('f03379f2-4435-4a27-b21e-8f45e130c97e', 'fea36130-e003-4c64-9e83-e9347b8121d8')
/
update KSEN_LUI set OBJ_ID='634d25e5-a8ef-4be1-8e39-620707622a49', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='7031694d-60f2-4012-8e96-4b2f6fc7ff1c', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=24, MIN_SEATS=24, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='0e5e5ded-2722-4d46-9ac5-964b6b95006c' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='2b60170f-cf3a-4010-91de-4e52e521e82e', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='0e5e5ded-2722-4d46-9ac5-964b6b95006c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='96dd99e4-26d4-428d-837d-481915cf8658' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='73488f34-6fd4-4375-82f2-ef849f26bb96', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='0e5e5ded-2722-4d46-9ac5-964b6b95006c', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='bbdc63e7-3f0d-4de2-873a-3777a6c83700' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='0e5e5ded-2722-4d46-9ac5-964b6b95006c'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('0e5e5ded-2722-4d46-9ac5-964b6b95006c', 'c904a29f-ae32-441c-936b-48d85031f530')
/
update KSEN_LUI set OBJ_ID='e5a34d11-77ef-480c-b19d-2fa0f85aad30', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='c6a8b277-7dba-4e06-9f9c-47e5dc936a68', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1003', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1003', DESCR_PLAIN='1003', REF_URL='' where ID='263f01fc-ccc6-4b9e-85d1-03ca5140ff83' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='ffe876aa-5a06-427b-a345-95f1c9f644ee', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='263f01fc-ccc6-4b9e-85d1-03ca5140ff83', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='9be5ee0d-bbac-4585-b07f-f368c0dc2acf' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='577996b2-55a0-4141-a2b9-94270cc1c2ef', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='4156f3f4-2fec-427c-9643-f5601a4c0d0a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='25719896-416f-41b0-91cf-f7dced522b4c' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='d6475fb7-785c-43a4-938e-153ef23d7524', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='I', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='25719896-416f-41b0-91cf-f7dced522b4c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='7fffea3e-dbfa-4472-9c05-a99451fc2934' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='8157058f-0c39-41f4-90e4-77f601c159b1', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='25719896-416f-41b0-91cf-f7dced522b4c', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='5550d375-d004-459a-913b-56fabca332ae' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='25719896-416f-41b0-91cf-f7dced522b4c'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('25719896-416f-41b0-91cf-f7dced522b4c', '89144c9f-1995-4546-813d-dc4bbee2db65')
/
update KSEN_LUI set OBJ_ID='469e91d9-2438-40bd-8792-6f9339da5842', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='1f695331-fba5-4a0b-876e-b190576d3298' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='38d89c02-4247-4ffb-8517-c4cd4d09d6cf', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='1f695331-fba5-4a0b-876e-b190576d3298', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a113b10d-fc5b-4ffb-b41a-d32d60ccfb76' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='fbf374ae-f7c0-40fa-814b-5be9fd9004c4', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='a29cf7a2-e3f1-4074-af10-5efd16838937', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='a57312f3-d0ba-4e47-bf6a-59892353b332' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='da47108d-bd73-4031-baeb-12bdcb9024f7', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='a57312f3-d0ba-4e47-bf6a-59892353b332', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='7311e247-0272-47d4-a604-f77e105bbcf9' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='32ee5e3e-582e-4666-9727-4086fa8d6354', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='a57312f3-d0ba-4e47-bf6a-59892353b332', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b5977d0c-6767-447c-9b05-e277cc8f6af4' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='a57312f3-d0ba-4e47-bf6a-59892353b332'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('a57312f3-d0ba-4e47-bf6a-59892353b332', '02daaea7-7e5f-4867-9b33-02626dfaa39a')
/
update KSEN_LUI set OBJ_ID='3218e7b5-1546-4750-a273-bfca11657f49', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='25889d4f-0612-424c-87a5-e96f60f3e096', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='3b32816e-8111-4610-8eef-9a6ae603aa89' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='9efa851e-007e-4ab6-86b6-571100497f8a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='3b32816e-8111-4610-8eef-9a6ae603aa89', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1bba5835-1e3d-4fda-a4e2-3750145b2ce6' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='5fcb4c5e-0192-4792-9e0d-bcb57e0beda6', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='495b649b-0671-4e0f-9ec2-7adcdaa435e6', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='1f9559e1-30b5-4a99-ac81-ce6d7de4871a' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='cdc31a1c-5925-45dc-982f-c2f75be8984d', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='H', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='1f9559e1-30b5-4a99-ac81-ce6d7de4871a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='97d16d2b-d89a-4724-bfe6-9c4cc47f43a2' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='0951c371-2aac-495b-8e7a-11dc79307902', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='1f9559e1-30b5-4a99-ac81-ce6d7de4871a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='8a53e9bb-272c-4963-884c-aee542ae362e' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='1f9559e1-30b5-4a99-ac81-ce6d7de4871a'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('1f9559e1-30b5-4a99-ac81-ce6d7de4871a', 'e5a2e43c-3039-406b-8811-b96b1005cc97')
/
update KSEN_LUI set OBJ_ID='91b2796a-8379-41e7-a2d7-d804e11f423f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1009', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1009', DESCR_PLAIN='1009', REF_URL='' where ID='40811dc3-dc84-4b46-83ba-ced1585f09e5' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='2b0bdb05-2c7f-4ac6-be8a-bc0f4893c44b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='40811dc3-dc84-4b46-83ba-ced1585f09e5', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='08b3f67a-0106-4225-a5ff-b56214f7cc53' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='a157ef3d-05de-421b-9e3d-3193ca83cec2', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='2d5222a0-843e-4c1b-9f7e-847887ab9d2c', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='bc880949-6bc5-4c4a-8e9a-ff2dc107b92a' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='59800abb-7889-40bb-ad6b-e3017543bcde', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='E', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='bc880949-6bc5-4c4a-8e9a-ff2dc107b92a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='01547aa1-5b35-49a0-9dce-3e63fc26e901' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='f0f17554-d686-4b9f-b807-81f8611f7d68', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='bc880949-6bc5-4c4a-8e9a-ff2dc107b92a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='5f6d499c-9356-45de-926f-0ac339cb7a49' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='bc880949-6bc5-4c4a-8e9a-ff2dc107b92a'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('bc880949-6bc5-4c4a-8e9a-ff2dc107b92a', '2157307f-32a0-405a-8d23-68fecb0daac5')
/
update KSEN_LUI set OBJ_ID='9ec98381-f03d-4e45-a2e9-446801a84d1e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='9c7796d1-4985-4e4f-a376-f94735348147', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1006', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1006', DESCR_PLAIN='1006', REF_URL='' where ID='6465f6ab-8cd5-4d2c-b70b-e3a73625d3d3' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='aabc4e0f-d53e-4bda-86c4-dc910d6f4584', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='6465f6ab-8cd5-4d2c-b70b-e3a73625d3d3', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='68d935be-1744-4ba4-9cc0-8f606b83526d' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='0c0cb5a5-a94f-487b-8408-2a13564cff08', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='dbe4aeed-3eec-4369-8faf-202c25c8cd1c', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='126b8493-6398-48f9-9435-8438eb096a8c' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='3df570ad-8e23-41d5-ba63-7b7a8186f09b', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='L', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='126b8493-6398-48f9-9435-8438eb096a8c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='788567a2-e91e-49c3-a945-c53bade523e5' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='5a15076e-d8e3-4c3b-b9fa-2c85efaf9506', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='126b8493-6398-48f9-9435-8438eb096a8c', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b944f453-6bb8-4d4b-b2bf-10b9e8c0e02d' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='126b8493-6398-48f9-9435-8438eb096a8c'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('126b8493-6398-48f9-9435-8438eb096a8c', 'c61844d4-ae58-4092-a03c-828ec7a09456')
/
update KSEN_LUI set OBJ_ID='0f180a08-8afb-4583-86da-cb87a8ede887', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1012', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1012', DESCR_PLAIN='1012', REF_URL='' where ID='611dcf59-6348-4a8f-85b9-835ddbc58dab' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='74125336-c535-4527-8e8f-04703ea0c8cc', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='611dcf59-6348-4a8f-85b9-835ddbc58dab', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='105c9e60-eb6f-48b4-9940-649542ade138' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='be555447-e339-4fc2-8ea1-f88e01c01064', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='e70ffd7e-977a-4241-ad53-4a98dbdb8bed', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='003cb053-a83d-4e93-b0ed-e470bed6f29a' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='ca66178f-42dd-4fc4-8a01-025cdded6672', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='003cb053-a83d-4e93-b0ed-e470bed6f29a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='88b3debe-d9b7-40b7-b2e3-607e1046fee5' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='5832607f-f99a-4c44-98e9-259bf8a4c75c', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='003cb053-a83d-4e93-b0ed-e470bed6f29a', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='e1e51c5f-4610-4f1e-9541-2c3551411dd4' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='003cb053-a83d-4e93-b0ed-e470bed6f29a'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('003cb053-a83d-4e93-b0ed-e470bed6f29a', '92e0ef5b-4de7-4d8c-a552-992fd2d0af26')
/
update KSEN_LUI set OBJ_ID='6e5be296-025e-4065-82b3-327481a9afe1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='f1dee561-a3e7-41b5-a1e0-0d4a77d7987e', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='827f74fe-619a-48ef-b590-21abad2d6724' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='1f58adad-c315-4fcc-acaf-51a2247bcac6', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='827f74fe-619a-48ef-b590-21abad2d6724', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='db1ef8a8-0186-4cb0-8276-db3005558f96' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='223ca72d-2364-43c9-b5b2-d205cd1f80ec', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='2484136e-b057-4450-ad0a-8fa2eed95a2b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='9ac86f1a-3016-41fe-bdd1-7e4f87dadc6d' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='04c4b3d6-9c27-412d-aa98-445171357863', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='S', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='9ac86f1a-3016-41fe-bdd1-7e4f87dadc6d', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='5f5366de-9e35-4712-b52a-3d0af5382af1' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='fd9afdcd-43a7-4436-816f-7759483c976d', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='9ac86f1a-3016-41fe-bdd1-7e4f87dadc6d', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='7d11e3b0-73f8-4c80-a35f-9e1acd1579e6' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='9ac86f1a-3016-41fe-bdd1-7e4f87dadc6d'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('9ac86f1a-3016-41fe-bdd1-7e4f87dadc6d', '10c68fbe-e9bb-4301-8a0d-80a3dc79f844')
/
update KSEN_LUI set OBJ_ID='01326ed6-5873-4fb6-8fa7-8625c59413f4', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='580ebfa2-f873-4dfa-b803-f176b08c1669', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=150, MIN_SEATS=150, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='7d711c28-50cc-41a0-81c0-f87443686372' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='c013366e-a184-4358-b22b-3c8bd11c6ec4', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='7d711c28-50cc-41a0-81c0-f87443686372', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='73a9578d-a0ae-4649-9e2c-7fa69b3f69ca' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='b8b1ad6a-7833-4939-a3c9-03145acd5812', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='7d711c28-50cc-41a0-81c0-f87443686372', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='9c2361c6-fbf4-48d2-960e-70bc2d9dc4a3' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='7d711c28-50cc-41a0-81c0-f87443686372'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('7d711c28-50cc-41a0-81c0-f87443686372', '98bbf055-26f4-4606-b753-ede9f907980a')
/
update KSEN_LUI set OBJ_ID='8c828818-64d4-4515-8d74-e47229b335f5', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='fd49ba3c-931e-4be4-9a3e-b5200d27f44f', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture only', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture Only', DESCR_PLAIN='Courses with Lecture only', REF_URL='' where ID='c1479830-593a-4040-8754-4feff9c0e17d' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='8f1ae1e0-a8f0-456c-93d3-548f2949b5f9', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture Only', LUI_ID='c1479830-593a-4040-8754-4feff9c0e17d', ORGID='', SHRT_NAME='LEC', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='92de51ef-4e9b-4734-b3e5-876e06d5759d' and VER_NBR=1
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='c1479830-593a-4040-8754-4feff9c0e17d'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('c1479830-593a-4040-8754-4feff9c0e17d', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='9fc2a7dd-215d-4a85-bcf5-a7a85cf01c0a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='9ece45f8-ae91-421a-8f4e-20f120ea6929', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='A study of the physical basis of sound, acoustical properties of sound, the human ear and voice, reproduction of sound, electronic music, acoustical properties of auditoriums, and other selected topics.', LUI_STATE='kuali.lui.course.offering.state.offered', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='PHYS102 CO', DESCR_PLAIN='A study of the physical basis of sound, acoustical properties of sound, the human ear and voice, reproduction of sound, electronic music, acoustical properties of auditoriums, and other selected topics.', REF_URL='null' where ID='704f2a5d-ac27-48b4-8200-8f15bc9d4763' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='5d68198e-a535-4574-8858-be4b066c67a0', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='PHYS102', DIVISION='PHYS', IDENT_LEVEL='', LNG_NAME='Physics of Music', LUI_ID='704f2a5d-ac27-48b4-8200-8f15bc9d4763', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1cd5805b-eb7c-4066-94e2-9e65ed3191bc' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='980bc84a-85f8-401a-8abb-d7651a1334c3', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='704f2a5d-ac27-48b4-8200-8f15bc9d4763', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='4bf6b71e-e094-4047-8780-e29a6b1e823e' and VER_NBR=1
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='704f2a5d-ac27-48b4-8200-8f15bc9d4763'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('704f2a5d-ac27-48b4-8200-8f15bc9d4763', '9012742')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='704f2a5d-ac27-48b4-8200-8f15bc9d4763'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('704f2a5d-ac27-48b4-8200-8f15bc9d4763', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('704f2a5d-ac27-48b4-8200-8f15bc9d4763', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('704f2a5d-ac27-48b4-8200-8f15bc9d4763', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('704f2a5d-ac27-48b4-8200-8f15bc9d4763', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='39f66d1c-d68b-4bc4-a769-5d6a042e5994', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='fd49ba3c-931e-4be4-9a3e-b5200d27f44f', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='a9eb1971-e407-4cb9-88a6-7e984717910d' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='70db3a1d-bbbd-4314-9fae-07006da036d4', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='a9eb1971-e407-4cb9-88a6-7e984717910d', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='d73b2846-dd59-4e5c-a320-1dc5485fe1f9' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='26a0b280-54ec-4737-beb2-9f67e943a414', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='70bbae2d-4ac6-4842-94ae-61ceea55cb0a', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='22d6029e-55b6-4be8-85db-e44fda078c14' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='1f520ee6-ae72-4755-97a1-def50cf970bb', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='M', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='22d6029e-55b6-4be8-85db-e44fda078c14', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='cd3142ea-dacf-4dd2-b69f-c369a292ca79' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='3fd4fc60-5e10-4fce-be2e-0a2930bd1a0a', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='22d6029e-55b6-4be8-85db-e44fda078c14', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='0ed77982-7e52-4c79-8124-76dc5bab3d5d' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='22d6029e-55b6-4be8-85db-e44fda078c14'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('22d6029e-55b6-4be8-85db-e44fda078c14', '17d26435-70a2-4780-bdea-5fc5ac632ef5')
/
update KSEN_LUI set OBJ_ID='2d5114fe-31a5-4ade-aaa4-fd19557c88af', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1003', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1003', DESCR_PLAIN='1003', REF_URL='' where ID='ceee82c7-a08a-40bf-9b48-abf7a04319e4' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='78dab106-3229-429f-b13b-c7c72a37cc14', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='ceee82c7-a08a-40bf-9b48-abf7a04319e4', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='ec2b81e9-c3f9-4d50-9b41-2085ff08f443' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='7576fe12-0bb8-4ea0-a431-25c479c7438f', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='7f44062b-35d1-403f-ab5e-45edca85c4db', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='188f9ca9-ecbc-45a7-a2ea-95962bb9951b' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='61444c59-9278-4e60-b05d-ba94a3442169', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='K', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='188f9ca9-ecbc-45a7-a2ea-95962bb9951b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a8ab504f-c232-4949-bb73-a0ea2f4fa8a5' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='4de57da3-eda8-4f97-8f43-b0b74a2646af', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='188f9ca9-ecbc-45a7-a2ea-95962bb9951b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='26d93a3a-1198-4ba0-baec-87c8b75be3e5' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='188f9ca9-ecbc-45a7-a2ea-95962bb9951b'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('188f9ca9-ecbc-45a7-a2ea-95962bb9951b', 'c1f25fb5-3049-4fc4-bb33-6836961de593')
/
update KSEN_LUI set OBJ_ID='a4b72022-5308-4657-ac3f-9b3e366994b9', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1011', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1011', DESCR_PLAIN='1011', REF_URL='' where ID='8d4edc32-d0c5-4c6d-a2c0-5b186ccba62f' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='e94f0c44-5d22-410a-8747-41a67adcd404', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='8d4edc32-d0c5-4c6d-a2c0-5b186ccba62f', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b5f00a26-aaf6-4ae7-a58f-b3f9f022ddc1' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='674f8b93-e583-4a5a-8995-dd5da6d7eac5', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='f54be7b2-70cc-46c0-8204-7963b66d4af9', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='fc28b776-c0a9-4279-8429-6852f7081e5e' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='7ddd36f6-c6d4-4d1a-b88b-b6bd9ca4b9d6', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='X', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='fc28b776-c0a9-4279-8429-6852f7081e5e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='fd076a4e-5a68-4511-bee3-7eda4894aa3f' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='6b293b92-bc9f-4c0d-940d-58e6c50f408e', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='fc28b776-c0a9-4279-8429-6852f7081e5e', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='f651faa4-32ea-424a-af5e-e51017a53774' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='fc28b776-c0a9-4279-8429-6852f7081e5e'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('fc28b776-c0a9-4279-8429-6852f7081e5e', 'f457f492-bd89-457f-bad2-f6a377b637bb')
/
update KSEN_LUI set OBJ_ID='3a57d248-de5b-4b6d-9bcc-7571fd59b20f', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='1bf3adb6-8b48-441f-9d68-cc1005dd9cc6', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=46, MIN_SEATS=46, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='036a740e-f4a6-4c63-8044-4b43495303df' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='a1d8f5b8-03d5-429f-a472-2d12a2409d33', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='036a740e-f4a6-4c63-8044-4b43495303df', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='9248535b-9972-4969-8ac0-c2f29122d1d8' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='e2af45e3-fb6c-499b-8234-2a5c154f2c20', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='036a740e-f4a6-4c63-8044-4b43495303df', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='2c5efb26-aa03-4110-b1be-1605033f5333' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='036a740e-f4a6-4c63-8044-4b43495303df'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('036a740e-f4a6-4c63-8044-4b43495303df', 'd780abbd-9005-43e0-b8d7-723b038b7c7d')
/
update KSEN_LUI set OBJ_ID='815c1f96-49e7-4b95-831d-209cce2e7780', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='fbe00f3e-788a-460c-b686-3303215621fa', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='0f0710db-fa2d-4ba6-9b12-8ca6e9dea613' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='a1849098-025c-4d08-834f-f1ba57eef691', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='0f0710db-fa2d-4ba6-9b12-8ca6e9dea613', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1ba63443-775d-4c51-91c8-f34c71c160f4' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='15ec6f05-c1bc-4497-aaa5-36f5e2ac0efd', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='09415b65-dae3-45c0-9829-24eed04f9dd1', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='2b13dab0-5395-4acb-b465-bdd0eb1e191b' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='3cc76941-06e3-4ee1-a9de-4fb873a53843', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='O', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='2b13dab0-5395-4acb-b465-bdd0eb1e191b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='42ef84bf-efdf-4d93-8425-46442f0f2db2' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='e88d395c-42f5-472c-bbe1-f915c758735d', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='2b13dab0-5395-4acb-b465-bdd0eb1e191b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='13dcad5b-8788-4e46-a220-f3f58247af65' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='2b13dab0-5395-4acb-b465-bdd0eb1e191b'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('2b13dab0-5395-4acb-b465-bdd0eb1e191b', 'aa8ee9ba-3888-4e7e-bd94-fb9b26c5024e')
/
update KSEN_LUI set OBJ_ID='4e52735c-ae8a-4a9f-899f-ef9a8c04fdca', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='7d91a6f7-ee9a-4d1f-b8d7-134066111f53', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='8099d225-47fd-4968-b308-76b53f6f95d8' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='674e2502-8eb6-4c50-b317-1760ec0fa87b', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='R', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='8099d225-47fd-4968-b308-76b53f6f95d8', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='16179c51-f36d-4ccf-a59e-f61e97744960' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='53924bd7-fd0b-4d05-9bfa-6792d4bd21ff', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='8099d225-47fd-4968-b308-76b53f6f95d8', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='5edf03b2-4f72-46af-9c83-83ea8e4c388d' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='8099d225-47fd-4968-b308-76b53f6f95d8'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('8099d225-47fd-4968-b308-76b53f6f95d8', '4a052879-a6fb-44f1-ac59-02bf7b5d54ea')
/
update KSEN_LUI set OBJ_ID='e245b458-bb4f-4169-a5c5-d223e5a02cd0', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='52cf48ee-860e-4ed9-82af-6cf489cac69c', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=44, MIN_SEATS=44, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='39ecfe64-bdfa-4cac-9665-7be0cc719c01' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='1bc5cc58-0460-47ad-bfc5-97b8fbce3825', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='39ecfe64-bdfa-4cac-9665-7be0cc719c01', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='6afb6edc-f0d6-4c87-95c5-3373669ba485' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='f4d0db38-0fa0-4c42-8176-6fd0da0c1282', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='39ecfe64-bdfa-4cac-9665-7be0cc719c01', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='f26ce4cb-5f83-49b5-bdf9-7f40152df512' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='39ecfe64-bdfa-4cac-9665-7be0cc719c01'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('39ecfe64-bdfa-4cac-9665-7be0cc719c01', '3ca15766-1f96-4fcf-8118-1cb124027bce')
/
update KSEN_LUI set OBJ_ID='e384c4a4-32a9-4d00-8b88-07a7cebd2f22', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='cfe13192-118b-471d-bd12-e91f7719e0d9', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture only', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture Only', DESCR_PLAIN='Courses with Lecture only', REF_URL='' where ID='212fd781-6886-4400-9da9-3916f6bd36a9' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='7cebb422-114a-46f8-bcb9-016cc8659003', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture Only', LUI_ID='212fd781-6886-4400-9da9-3916f6bd36a9', ORGID='', SHRT_NAME='LEC', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='d3c4403f-aa7c-4a19-b3b7-ea939b52dbb8' and VER_NBR=1
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='212fd781-6886-4400-9da9-3916f6bd36a9'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('212fd781-6886-4400-9da9-3916f6bd36a9', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='66dcedd8-c1a5-438b-b975-4b2e4aa4560d', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='98835c9e-79df-48e5-8372-c957497754ec', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='This is a course with a non-mathematical emphasis designed to study the basics of mechanical, electrical, and optical devices that are commonly found in the world around us.  The general approach would be to look inside things to observe how they work.', LUI_STATE='kuali.lui.course.offering.state.offered', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='PHYS104 CO', DESCR_PLAIN='This is a course with a non-mathematical emphasis designed to study the basics of mechanical, electrical, and optical devices that are commonly found in the world around us.  The general approach would be to look inside things to observe how they work.', REF_URL='null' where ID='3da1f033-df9b-4c9b-aaac-0525062012fd' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='821890f1-c9e4-476e-b83f-221ccf481737', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='PHYS104', DIVISION='PHYS', IDENT_LEVEL='', LNG_NAME='How Things Work: Science Foundations', LUI_ID='3da1f033-df9b-4c9b-aaac-0525062012fd', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='6ac61322-e590-44f2-9db9-0166b744f889' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='80cb6f10-0531-4b25-b8b6-ce7431f8636c', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='3da1f033-df9b-4c9b-aaac-0525062012fd', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='fc4e4ae4-12c9-4f69-bc15-b892b2b76705' and VER_NBR=1
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='3da1f033-df9b-4c9b-aaac-0525062012fd'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('3da1f033-df9b-4c9b-aaac-0525062012fd', '9012742')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='3da1f033-df9b-4c9b-aaac-0525062012fd'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('3da1f033-df9b-4c9b-aaac-0525062012fd', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('3da1f033-df9b-4c9b-aaac-0525062012fd', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('3da1f033-df9b-4c9b-aaac-0525062012fd', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('3da1f033-df9b-4c9b-aaac-0525062012fd', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='88d5a6ed-9a7c-471c-b0a9-4a511cb96a56', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='cfe13192-118b-471d-bd12-e91f7719e0d9', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='fee8f1bd-a49b-4309-bb33-47d129c77468' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='584d8ce8-7a61-449a-8abb-fc3640f5abcb', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='fee8f1bd-a49b-4309-bb33-47d129c77468', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='db0a9ab8-beac-4276-b9e3-f50f8fec0544' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='25648dde-a008-4d84-bb3f-3934e62431db', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='2df91c85-1d89-4796-95ec-e491454f35c3', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='7ba06dd6-9bc7-4c01-a970-b9b7b381b4d1' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='7fb7adbc-35dc-45a5-9ad7-f8eb16b328cd', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='E', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='7ba06dd6-9bc7-4c01-a970-b9b7b381b4d1', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8f70bf13-2bfa-4273-9b27-da8214ba882d' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='65ec81af-1602-4374-ab17-54bb1fd33b3a', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='7ba06dd6-9bc7-4c01-a970-b9b7b381b4d1', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='681eb423-7af9-4324-be3f-bb2634caa724' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='7ba06dd6-9bc7-4c01-a970-b9b7b381b4d1'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('7ba06dd6-9bc7-4c01-a970-b9b7b381b4d1', 'c6d4bddd-cea1-478d-9933-dde4b3872416')
/
update KSEN_LUI set OBJ_ID='258da2fe-dab6-48a3-a81e-513d6c1a2436', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1005', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1005', DESCR_PLAIN='1005', REF_URL='' where ID='4898f12e-9469-47c1-b4b2-8033cdaa8c5a' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='5a632ad5-4746-4ab1-899c-c6be2447c00b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='4898f12e-9469-47c1-b4b2-8033cdaa8c5a', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='c45e9437-9595-4a31-a075-390a783341e1' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='e436ac6e-e8f3-46e1-8bf9-53be4d0a2cc1', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='d354a5c4-5e0c-4a42-981f-bb00109e165f', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='0e30e2b5-3461-452d-831e-8180c3c158d6' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='8e3f9855-dfa7-4aca-ae3a-0bcce5c94b3e', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='F', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='0e30e2b5-3461-452d-831e-8180c3c158d6', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b95f7d52-1417-4001-8e7c-ecd1dce96290' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='bffaa670-7b6e-44ec-9ab8-f3aa19f2b4d4', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='0e30e2b5-3461-452d-831e-8180c3c158d6', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='60d1777f-4a89-48d0-9532-9be150915696' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='0e30e2b5-3461-452d-831e-8180c3c158d6'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('0e30e2b5-3461-452d-831e-8180c3c158d6', '8d7a6fc8-f33c-4607-bee5-8478e04f2807')
/
update KSEN_LUI set OBJ_ID='27267f10-eaa4-4f0b-acba-54284106a018', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1004', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1004', DESCR_PLAIN='1004', REF_URL='' where ID='29924b9b-bbfd-4420-8693-2040fc09a66c' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='ef684d1d-7f2d-41cf-87db-b5a18818cdf7', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='29924b9b-bbfd-4420-8693-2040fc09a66c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='f903ca41-77e1-46f8-9120-2933913f9eb3' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='69ba85fd-1de8-4c55-b94a-a6f44b3f0cba', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='bd80c228-fc5d-4178-9a88-896fa8a1b17e', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=24, MIN_SEATS=24, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='62350ce0-2b1e-4566-bf95-ead6429383ce' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='d7b283d2-5a0d-4941-9723-bb317cea77cb', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='D', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='62350ce0-2b1e-4566-bf95-ead6429383ce', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8618fd34-b568-44cd-ba31-6ca48aa53642' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='af6af553-d900-4bad-a923-c4c358c0bd5a', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='62350ce0-2b1e-4566-bf95-ead6429383ce', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='e3dd1380-a4b2-48b1-8f4b-17d1068e419b' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='62350ce0-2b1e-4566-bf95-ead6429383ce'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('62350ce0-2b1e-4566-bf95-ead6429383ce', '9b398bc1-b29b-44b1-b990-481856e7c276')
/
update KSEN_LUI set OBJ_ID='4883be38-c1e7-4bff-936d-3c1cc6d0018f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='c6a8b277-7dba-4e06-9f9c-47e5dc936a68', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1004', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1004', DESCR_PLAIN='1004', REF_URL='' where ID='fee5e9ca-b597-4d2c-8bb7-46870e502ac4' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='086bafb9-175c-4972-932e-14b877fc6051', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='fee5e9ca-b597-4d2c-8bb7-46870e502ac4', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='db938f0c-4b82-4b58-bdfa-75c32b5cc722' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='800e14e5-2572-4ad9-913b-34584c270912', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='107dded6-6597-4211-b228-7c66c8414616', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='d41a71d8-ac39-4a4f-92cf-cbb4433234ee' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='a78459a9-2956-4ba6-aa62-4e11b56a0364', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='U', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='d41a71d8-ac39-4a4f-92cf-cbb4433234ee', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='ebd6f255-7ca4-4a71-89bb-a807d3e41c1d' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='c1d6ef73-cd3d-4ae3-b99d-41dc2e70a07c', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='d41a71d8-ac39-4a4f-92cf-cbb4433234ee', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='c73d05cf-3342-4684-acfe-254dca6f2eff' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='d41a71d8-ac39-4a4f-92cf-cbb4433234ee'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('d41a71d8-ac39-4a4f-92cf-cbb4433234ee', '4e122d61-2427-4c55-81cd-50d6fa356101')
/
update KSEN_LUI set OBJ_ID='672cb21f-f9c7-4f5b-bae1-5f4217193140', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='d46ad097-966a-4117-b020-a99576af1e10', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=150, MIN_SEATS=150, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='4fbcfdd4-dea9-4344-98c9-a8b9c3bf2a6e' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='e9a5b3aa-94ab-4f4e-87bb-bb37976c546a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='4fbcfdd4-dea9-4344-98c9-a8b9c3bf2a6e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='fbe1ae66-92a8-4745-9803-a01cd2eea062' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='26d1c892-1835-4505-aefe-ab1d5cefae86', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='4fbcfdd4-dea9-4344-98c9-a8b9c3bf2a6e', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='38f7764c-b002-4cf4-b2cb-01628fa25da6' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='4fbcfdd4-dea9-4344-98c9-a8b9c3bf2a6e'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('4fbcfdd4-dea9-4344-98c9-a8b9c3bf2a6e', '16b417a1-f7fa-4ddf-82af-5dcaa7538884')
/
update KSEN_LUI set OBJ_ID='7d599636-67a6-4834-80c4-29ecb4964721', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='394eb670-fba5-4c80-9017-bd533d73dda2', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture only', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture Only', DESCR_PLAIN='Courses with Lecture only', REF_URL='' where ID='db443fb1-f8ee-4794-a1f5-a9707e30b479' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='49383bb4-8037-4a12-8492-539aea78a6e8', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture Only', LUI_ID='db443fb1-f8ee-4794-a1f5-a9707e30b479', ORGID='', SHRT_NAME='LEC', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='dd280056-f9a9-4b83-9706-30b665cf4640' and VER_NBR=1
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='db443fb1-f8ee-4794-a1f5-a9707e30b479'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('db443fb1-f8ee-4794-a1f5-a9707e30b479', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='55ed5b55-61ba-477e-9017-0edce60f84f1', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='8597c766-5cf9-4223-90bd-0c18d697ec28', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='A study of the biology, life span development, socialization, personality, mental health, and special issues of women.', LUI_STATE='kuali.lui.course.offering.state.offered', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='WMST336 CO', DESCR_PLAIN='A study of the biology, life span development, socialization, personality, mental health, and special issues of women.', REF_URL='null' where ID='d9ffe43c-6771-41bd-bf16-a41cac9c01b0' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='6440f1a8-87df-4e33-a748-8e4b91903812', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='WMST336', DIVISION='WMST', IDENT_LEVEL='', LNG_NAME='Psychology of Women', LUI_ID='d9ffe43c-6771-41bd-bf16-a41cac9c01b0', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='d4e412fd-e08d-479f-9f02-d33761d61fd1' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='2df24b52-f9eb-4d51-be45-40b8756ca502', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='d9ffe43c-6771-41bd-bf16-a41cac9c01b0', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='ab225464-f02e-4d30-ab1d-d9994d20f271' and VER_NBR=1
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='d9ffe43c-6771-41bd-bf16-a41cac9c01b0'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('d9ffe43c-6771-41bd-bf16-a41cac9c01b0', '4014660630')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='d9ffe43c-6771-41bd-bf16-a41cac9c01b0'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('d9ffe43c-6771-41bd-bf16-a41cac9c01b0', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('d9ffe43c-6771-41bd-bf16-a41cac9c01b0', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('d9ffe43c-6771-41bd-bf16-a41cac9c01b0', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('d9ffe43c-6771-41bd-bf16-a41cac9c01b0', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='6cc3b36c-6c15-41c1-932f-a3c88bcede4e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='394eb670-fba5-4c80-9017-bd533d73dda2', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='d6edc243-5eb0-4b59-b2c7-739a24f8f3bf' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='a3f28012-6556-4bb6-997f-47c423a7a1c1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='d6edc243-5eb0-4b59-b2c7-739a24f8f3bf', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='5bbc01e0-2fcd-493b-b8db-ad21f7dce878' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='872fd732-fe85-43fa-b2b3-dae3cb11da2e', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='a89ef48f-7d10-4c62-bd31-7ead4247b18b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='beed8739-feb4-412e-ab55-6c6452f0e0e4' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='0df95ec5-d01a-44e3-908d-a89aaf6b258b', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='Q', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='beed8739-feb4-412e-ab55-6c6452f0e0e4', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='97d61bf2-89a3-43fd-b93f-4b2edf5b418a' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='19171b22-e777-464d-9ceb-62a93e85618f', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='beed8739-feb4-412e-ab55-6c6452f0e0e4', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='238b65ed-57df-4842-a814-93ee90d0a605' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='beed8739-feb4-412e-ab55-6c6452f0e0e4'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('beed8739-feb4-412e-ab55-6c6452f0e0e4', '24acd867-c03c-4721-aae7-3258ce23b7cd')
/
update KSEN_LUI set OBJ_ID='ea1ab808-c68a-4c50-b9f4-6a23b6f724fe', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='cbf096d5-a82f-4965-aceb-5e80a89ad9c9', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='85bf9f49-6a6f-4d5a-8f4d-33fe8c6aabe4' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='a984a97b-f992-436c-95ca-fd625cecc53f', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='T', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='85bf9f49-6a6f-4d5a-8f4d-33fe8c6aabe4', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1545c0d6-2f5f-47e7-9cc4-2386e4176d3d' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='99b3fe6b-3f73-439b-b535-33e2040b3d5f', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='85bf9f49-6a6f-4d5a-8f4d-33fe8c6aabe4', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='7809eca1-5913-4be0-835f-132a83d53cb2' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='85bf9f49-6a6f-4d5a-8f4d-33fe8c6aabe4'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('85bf9f49-6a6f-4d5a-8f4d-33fe8c6aabe4', '6ab2ef08-df82-4b4c-99be-a0e8128938d2')
/
update KSEN_LUI set OBJ_ID='04cac046-af3a-410d-a3af-d16d03ff5016', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='5ab3ab70-84fd-4673-9ac6-49090dd24ca4', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=83, MIN_SEATS=83, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='be93f2be-e2ed-46e5-be65-fcbdec11b1e3' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='2f131b02-97bd-44e5-acbd-a763cfa4f7ee', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='A', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='be93f2be-e2ed-46e5-be65-fcbdec11b1e3', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1815d73a-bebb-4a29-9c72-0537c5c94c27' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='70c5e4a3-1e59-41d6-8862-ac2be5b4f53e', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='be93f2be-e2ed-46e5-be65-fcbdec11b1e3', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='967316de-5492-4e24-bd0d-747646df5d89' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='be93f2be-e2ed-46e5-be65-fcbdec11b1e3'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('be93f2be-e2ed-46e5-be65-fcbdec11b1e3', '9e9151eb-5121-4817-8b87-5239fe488efc')
/
update KSEN_LUI set OBJ_ID='40578186-64a5-4831-846c-bfe84f72cfdf', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='a6a3673f-5d87-4e8d-8be6-659b6cb99189', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with Lecture only', LUI_STATE='kuali.lui.format.offering.state.offered', LUI_TYPE='kuali.lui.type.course.format.offering', MAX_SEATS='', MIN_SEATS='', NAME='Lecture Only', DESCR_PLAIN='Courses with Lecture only', REF_URL='' where ID='b665c20c-6cdc-4b24-8402-f0123b3c050a' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='9a7b4760-be34-4b85-b026-22e16052350e', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='Lecture Only', LUI_ID='b665c20c-6cdc-4b24-8402-f0123b3c050a', ORGID='', SHRT_NAME='LEC', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='c07e7442-21a3-44e5-acbe-35abb95c4e5a' and VER_NBR=1
/
delete from KSEN_LUI_RELATED_LUI_TYPES where LUI_ID='b665c20c-6cdc-4b24-8402-f0123b3c050a'
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('b665c20c-6cdc-4b24-8402-f0123b3c050a', 'kuali.lui.type.activity.offering.lecture')
/
update KSEN_LUI set OBJ_ID='d4b6c86d-7235-4515-8877-8b42519487c5', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='CLUID-BSCI373-199908000000', EFF_DT=TIMESTAMP '2011-08-26 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='Consideration of the major groups of organisms associated with the Chesapeake Bay and current issues that determine humans'' present and future uses for the Chesapeake and its biota.', LUI_STATE='kuali.lui.course.offering.state.offered', LUI_TYPE='kuali.lui.type.course.offering', MAX_SEATS='', MIN_SEATS='', NAME='BSCI373 CO', DESCR_PLAIN='Consideration of the major groups of organisms associated with the Chesapeake Bay and current issues that determine humans'' present and future uses for the Chesapeake and its biota.', REF_URL='null' where ID='4df0949c-703e-4206-8961-055358a21dac' and VER_NBR=1
/
update KSEN_LUI_IDENT set OBJ_ID='a61d1a0d-cfd7-4527-9f8f-c10b2d6b9c8b', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='BSCI373', DIVISION='BSCI', IDENT_LEVEL='', LNG_NAME='Natural History of the Chesapeake Bay', LUI_ID='4df0949c-703e-4206-8961-055358a21dac', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b4360b13-ec50-4078-beb8-08ce30ca304d' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='7c0e5d02-60ed-4ddf-9306-5f0da3d76d62', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='4df0949c-703e-4206-8961-055358a21dac', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='' where ID='5d19cdd0-d895-4ec3-b89a-a4d8f2052487' and VER_NBR=1
/
delete from KSEN_LUI_UNITS_CONT_OWNER where LUI_ID='4df0949c-703e-4206-8961-055358a21dac'
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('4df0949c-703e-4206-8961-055358a21dac', '576639460')
/
delete from KSEN_LUI_RESULT_VAL_GRP where LUI_ID='4df0949c-703e-4206-8961-055358a21dac'
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4df0949c-703e-4206-8961-055358a21dac', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4df0949c-703e-4206-8961-055358a21dac', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4df0949c-703e-4206-8961-055358a21dac', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4df0949c-703e-4206-8961-055358a21dac', 'kuali.resultComponent.grade.passFail')
/
update KSEN_LUI set OBJ_ID='ea6073cc-fce4-4cfc-8481-7101642b0d96', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='a6a3673f-5d87-4e8d-8be6-659b6cb99189', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='f13c0e6b-8fc6-4d0a-a5cc-4151613bcc11' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='6d1a9884-2af2-47b1-84ad-3deeb42fd6cd', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='f13c0e6b-8fc6-4d0a-a5cc-4151613bcc11', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='dc67ba67-1bb4-4ebe-b4c5-a3be64eb1469' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='745ba1f2-d943-49d8-b84f-4da00ca4ce2f', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='38e4c5cd-0e98-4174-8712-9f08616c677e', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=24, MIN_SEATS=24, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='122dc077-c07c-419d-ae57-f816b712a562' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='db752c64-d4df-4e22-b2b5-8d79b34b5fa1', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='E', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='122dc077-c07c-419d-ae57-f816b712a562', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='794a0f1a-17d6-470d-9d32-16236e2f8237' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='f9b9217d-0b82-4a3e-9b88-416be14398de', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='122dc077-c07c-419d-ae57-f816b712a562', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='cddf1caa-6c95-4924-b4d5-a3044eadcac1' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='122dc077-c07c-419d-ae57-f816b712a562'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('122dc077-c07c-419d-ae57-f816b712a562', '9166cd97-23eb-4076-a063-b8904521158e')
/
update KSEN_LUI set OBJ_ID='07582fb5-4d1a-4510-808c-beeaa303f1de', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='c6a8b277-7dba-4e06-9f9c-47e5dc936a68', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='ee333ab4-eb59-4b69-9982-7aa6d16c13d3' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='937a34dd-0b17-49ab-ba58-457acf8820bf', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='ee333ab4-eb59-4b69-9982-7aa6d16c13d3', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='1f11739a-a985-4b98-836e-3feadcd5e75a' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='1d6da209-8c06-46a0-8414-3cdab004c353', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='172819c3-11a4-4baf-81c5-63a08bbfb3ed', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='1240da0c-7e1d-4538-9384-9039d41c0d80' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='d7e19c3b-0b69-4bc5-91f8-8f5de47def4c', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='C', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='1240da0c-7e1d-4538-9384-9039d41c0d80', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='25a41b1d-8a5b-46b4-9d64-68d7fe9dc578' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='d5037bfc-390f-49e0-9167-1574294037cc', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='1240da0c-7e1d-4538-9384-9039d41c0d80', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b910cf92-c5e3-406f-8fc7-740a73ce369a' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='1240da0c-7e1d-4538-9384-9039d41c0d80'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('1240da0c-7e1d-4538-9384-9039d41c0d80', '8bb560c2-18c3-417b-ae04-17c42bbdb657')
/
update KSEN_LUI set OBJ_ID='f1cecfb7-b0ea-45a5-a595-4734eea07abe', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='25889d4f-0612-424c-87a5-e96f60f3e096', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1002', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1002', DESCR_PLAIN='1002', REF_URL='' where ID='297f55bf-76af-4535-82c2-39ae5867d485' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='ce702e63-a005-42fe-be2a-c08271f20cd3', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='297f55bf-76af-4535-82c2-39ae5867d485', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='562f7d05-4c4f-4c1c-a344-1fe91681c825' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='156442bb-9d91-4466-80d8-eb3d0ee8aba3', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='8a6d57fc-0732-47ef-9fd8-44d6318922b3', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=24, MIN_SEATS=24, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='fa925c0c-0077-4a2f-bae3-1a1be93342ba' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='6e77f80b-85f1-493b-bb44-0fa10cc62616', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='C', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='fa925c0c-0077-4a2f-bae3-1a1be93342ba', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='30d36b98-499a-4277-a7ac-4bde1858f246' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='1cdf5baf-608f-46ce-b668-328b0172192c', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='fa925c0c-0077-4a2f-bae3-1a1be93342ba', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='b49fece1-5348-422d-9fcc-ae9175a36754' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='fa925c0c-0077-4a2f-bae3-1a1be93342ba'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('fa925c0c-0077-4a2f-bae3-1a1be93342ba', 'd5102735-79b1-4cdb-aac7-d0dea4c8b9e4')
/
update KSEN_LUI set OBJ_ID='9c92d0ed-fe53-4735-90f3-a1e8d692df6e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='c6a8b277-7dba-4e06-9f9c-47e5dc936a68', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1002', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1002', DESCR_PLAIN='1002', REF_URL='' where ID='d44bfbea-9d74-4447-8fe0-6ba456525321' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='2ba5bf01-d6ef-4f15-853c-93a10af65033', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='d44bfbea-9d74-4447-8fe0-6ba456525321', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='d7b03947-857e-40e3-b97c-33ad57d3d450' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='1fe9043a-aec3-4f82-83f9-f05c27bdc3a8', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='fece0248-468d-4b08-9fcf-955a6b6b703d', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='873a3992-bd23-4f96-90cf-9b0ffca8c0d9' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='a584c499-d57e-401b-9dcc-fcea05a9554a', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='G', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='873a3992-bd23-4f96-90cf-9b0ffca8c0d9', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='f583c0ef-edda-4c2c-99c6-cc1d0d58eb8d' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='a969ba2d-a22b-4fbd-8cdd-6538c9c9e47a', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='873a3992-bd23-4f96-90cf-9b0ffca8c0d9', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='64339611-f5f1-4740-91c7-bc92cc9f9d75' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='873a3992-bd23-4f96-90cf-9b0ffca8c0d9'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('873a3992-bd23-4f96-90cf-9b0ffca8c0d9', '0c2f1dfb-0b0f-4686-a9d0-83d6781ee0a6')
/
update KSEN_LUI set OBJ_ID='a9bcaee5-dc82-4ab3-bfe3-66fa89d47845', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='9c7796d1-4985-4e4f-a376-f94735348147', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='30906f0f-2962-4cc0-8c67-d3015bf8b151' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='e562de8a-542f-4a62-bf16-792839b22a2f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='30906f0f-2962-4cc0-8c67-d3015bf8b151', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='0789848f-976f-489c-9913-342161b0703e' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='25c196be-cdbc-4d3e-8d68-d2b12bf1c383', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='4bdec9e4-f92c-4130-befc-9a5672355c0b', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='51ad321e-0d2e-40e4-959d-87ff6d1e8215' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='0813a3e7-dfa2-401a-93ed-d6e84d5c47a3', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='G', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='51ad321e-0d2e-40e4-959d-87ff6d1e8215', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a796de6d-0058-4bb9-af62-d1cd35cee9ed' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='ee673a6b-b0a2-414a-a212-cdf4005a27ae', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='51ad321e-0d2e-40e4-959d-87ff6d1e8215', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='d601bf44-451a-4619-b8f7-fb6c2b0daad0' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='51ad321e-0d2e-40e4-959d-87ff6d1e8215'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('51ad321e-0d2e-40e4-959d-87ff6d1e8215', 'b283579a-a2ff-4cec-9557-ad3ffe53f43a')
/
update KSEN_LUI set OBJ_ID='d6afdb47-195e-40c9-89f2-0b30f28406e8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1002', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1002', DESCR_PLAIN='1002', REF_URL='' where ID='65583122-7db9-49e6-a306-17e5d53690a9' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='762ffbe2-c34f-4421-92b0-648cfd430457', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='65583122-7db9-49e6-a306-17e5d53690a9', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='d0e55f58-62c9-494b-8b00-1136f9d4b37d' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='5ccc71bc-2baf-42cb-bf12-270a5f9cc2e1', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='fd93d6b6-1a66-4cae-bcb2-23b0316a4870', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='e3c7aa5b-02c3-4416-b8c3-a069e9bf587b' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='2ec69aed-2286-4738-9944-5fdb0217e5ae', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='V', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='e3c7aa5b-02c3-4416-b8c3-a069e9bf587b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='f9ef9d20-c8e4-4ae8-b477-7713952d5b4c' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='daf52a19-0801-4ed5-8240-c1da0a4b1b1a', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='e3c7aa5b-02c3-4416-b8c3-a069e9bf587b', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='cb9ed1d4-2da6-4c40-bd80-d12efd66e3e6' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='e3c7aa5b-02c3-4416-b8c3-a069e9bf587b'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('e3c7aa5b-02c3-4416-b8c3-a069e9bf587b', '98e385e7-9909-4199-88a7-ec3bb03918e8')
/
update KSEN_LUI set OBJ_ID='b951ac81-0d30-40be-b13d-12047b922f2a', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='69863a58-c29e-4b1b-a2db-cdbe35597fcf', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=24, MIN_SEATS=24, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='502e1be7-57db-4218-a293-1a3e23748147' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='634bf98b-7bdf-49e3-955d-5ca9c1460988', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='Y', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='502e1be7-57db-4218-a293-1a3e23748147', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='892c66e1-1495-4dc5-8e34-d04840d5e0f6' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='63edd447-a856-4bd9-89d0-7308899f963d', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='502e1be7-57db-4218-a293-1a3e23748147', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='8a69bae9-be41-4308-9b5b-1361c37e91bd' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='502e1be7-57db-4218-a293-1a3e23748147'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('502e1be7-57db-4218-a293-1a3e23748147', '0565fc7b-c2ac-4aad-b785-c3e38e65f1f1')
/
update KSEN_LUI set OBJ_ID='42b1dc8b-3e9f-412a-ad39-d9aeef73fdd7', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='3cad8c8d-4ded-4ff1-aa0a-60f5dce6edf1', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='a3b6c14e-8615-4479-a1c0-b033d0228401' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='3e962b6a-7b74-4d21-b437-2d380891ceb8', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='D', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='a3b6c14e-8615-4479-a1c0-b033d0228401', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='351b25f9-dc7f-40d5-8c6c-e2385fb8a01b' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='df443f6d-c787-4ecc-9918-af278a5288f0', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='a3b6c14e-8615-4479-a1c0-b033d0228401', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='af4f3036-dea3-491f-93d2-a264946ef3ea' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='a3b6c14e-8615-4479-a1c0-b033d0228401'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('a3b6c14e-8615-4479-a1c0-b033d0228401', '47ebac57-cec9-4f63-b825-fa8e2a8a8ab1')
/
update KSEN_LUI set OBJ_ID='9c08a65b-f4e1-4b63-be1d-8fcc83f8a2fe', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='9c7796d1-4985-4e4f-a376-f94735348147', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1005', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1005', DESCR_PLAIN='1005', REF_URL='' where ID='9889e17a-e9b0-4c21-8081-22316b8ab69f' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='a5de0495-ff3d-4dfd-9fb9-44f0772846f5', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='9889e17a-e9b0-4c21-8081-22316b8ab69f', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='a9968a12-7431-495e-9a42-30b4d9b679b0' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='d7c9c004-06f2-45d4-ae15-af309ce43ac2', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='02b2af95-54f8-4d0a-9ec8-4d7fb31c36e1', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='0fb3f43b-5386-4656-a2e0-9ee48c187df6' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='c0d39f3b-d3df-4778-bbbf-793b44dac417', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='Z', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='0fb3f43b-5386-4656-a2e0-9ee48c187df6', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8f2ba4c6-bd9d-4dd7-874c-a46c7cb9a747' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='640dbaea-c716-43a8-ae21-b0602ed86b05', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='0fb3f43b-5386-4656-a2e0-9ee48c187df6', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='10d1e1c0-a0bd-4766-b341-01e0a0e36817' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='0fb3f43b-5386-4656-a2e0-9ee48c187df6'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('0fb3f43b-5386-4656-a2e0-9ee48c187df6', '7d66ee9c-62b3-4b40-8c89-053e1897c389')
/
update KSEN_LUI set OBJ_ID='1609bc05-17bd-47ee-82b5-1ab050b6854f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1013', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1013', DESCR_PLAIN='1013', REF_URL='' where ID='20cd41f7-64ac-4b02-b0b9-f03b1f3aa707' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='4471d267-d11f-46f2-9fe4-261af44d76cc', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='20cd41f7-64ac-4b02-b0b9-f03b1f3aa707', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8e6350c6-f507-4695-9991-d1cbb8b41e90' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='84257620-3040-450b-95b9-2da3bf43cd9b', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='b425bf1d-87a1-41ff-a84c-f8a2d86e5ecf', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='d6e8f5d5-3b33-4155-8ca0-78c6f983d3f5' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='c110de6f-a6fa-4a87-a970-df58b18e83c9', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='D', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='d6e8f5d5-3b33-4155-8ca0-78c6f983d3f5', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='d1fd7bf3-f183-4a1a-a027-82ecb31af05e' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='dcb95a08-928e-4fb6-9e00-f09ca8ec95f7', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='d6e8f5d5-3b33-4155-8ca0-78c6f983d3f5', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='d75ca3de-2fdd-4072-b2e5-4cd366b63016' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='d6e8f5d5-3b33-4155-8ca0-78c6f983d3f5'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('d6e8f5d5-3b33-4155-8ca0-78c6f983d3f5', '2ed2e8d0-a839-4cde-a3c6-b9aaeab76aff')
/
update KSEN_LUI set OBJ_ID='258c4fbe-2df2-4d75-8131-e8ed54409e33', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1007', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1007', DESCR_PLAIN='1007', REF_URL='' where ID='a2b3d400-caff-4299-8c56-36647db30a2c' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='0a89588d-33d0-4757-a9ba-3138aa0af88b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='a2b3d400-caff-4299-8c56-36647db30a2c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='20b10fde-dec7-4fa2-9946-dd13415a704d' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='ab8cce5b-6004-468a-8f84-1ead61a9408c', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='8ac0fe70-2eae-4cb2-850b-e1dd9c8b7b32', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='e2222eee-de96-4e03-aed1-82a9aff7b158' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='5af15209-fff1-4767-af24-c388f711fc7b', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='I', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='e2222eee-de96-4e03-aed1-82a9aff7b158', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='5867d06f-08c0-4d18-9d32-0ddcb6ad8dfa' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='735ed3a2-61d9-4679-93ef-fa35492b1a7b', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='e2222eee-de96-4e03-aed1-82a9aff7b158', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='71292101-5f7d-4633-a469-ac41ae4c7fce' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='e2222eee-de96-4e03-aed1-82a9aff7b158'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('e2222eee-de96-4e03-aed1-82a9aff7b158', 'b5dd7cb2-4994-4077-bbb4-4f95cbe11e84')
/
update KSEN_LUI set OBJ_ID='46fb6274-6e35-4e53-ada1-f7af82af5550', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='9c7796d1-4985-4e4f-a376-f94735348147', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1007', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1007', DESCR_PLAIN='1007', REF_URL='' where ID='d119b59f-3d59-4abe-854e-dffd6cea382b' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='a079b8c7-8067-4c2c-a903-69c52275976f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='d119b59f-3d59-4abe-854e-dffd6cea382b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='be603570-a045-4695-9d6f-01ebca10ee82' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='73736666-026d-4e1c-ae2b-840befa52a50', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='04f3e47b-d1ba-47f3-b243-5cd531b84f40', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='397f2167-77a8-4e89-ad44-79be6ae1da74' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='14fb646d-e6cb-481b-919e-89d70a7c04e4', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='C', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='397f2167-77a8-4e89-ad44-79be6ae1da74', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='abf7d3be-85c6-4692-9085-438e4cfc768f' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='82a7a8d5-4fb1-4c72-ba65-46c2a6d21bac', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='397f2167-77a8-4e89-ad44-79be6ae1da74', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='7c09b0b0-b0f7-4676-854e-f4e5adc5767f' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='397f2167-77a8-4e89-ad44-79be6ae1da74'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('397f2167-77a8-4e89-ad44-79be6ae1da74', '1d346d11-12da-4662-b630-6d4dfc3c556a')
/
update KSEN_LUI set OBJ_ID='7802f5cf-eef9-4542-aeaf-999da4ecaa30', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='f1dee561-a3e7-41b5-a1e0-0d4a77d7987e', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1003', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1003', DESCR_PLAIN='1003', REF_URL='' where ID='85452bcd-6bbf-4dc8-97b8-1abbee3ea9bb' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='87a6c127-08be-4b1f-a026-08fd048f0344', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='85452bcd-6bbf-4dc8-97b8-1abbee3ea9bb', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='b97bf126-5138-488e-8a24-fcd4079b1c27' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='cdc59953-6d16-46f6-ae70-3a2813d40880', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='3e116be8-1102-41ec-945f-92f8cb201300', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with discussion', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.discussion', MAX_SEATS=20, MIN_SEATS=20, NAME='Discussion', DESCR_PLAIN='Courses with discussion', REF_URL='null' where ID='ae9703d9-be4e-4e8a-9288-3df4067ba126' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='5eb92ab1-aad1-4fa6-af2f-1c8d2eb9c0f3', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='B', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='ae9703d9-be4e-4e8a-9288-3df4067ba126', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='5ed3dd51-df9f-4f40-be89-c42b654f155e' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='f7a72ab6-3d8c-47b0-9cdb-b6e0fef5da5d', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='ae9703d9-be4e-4e8a-9288-3df4067ba126', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='cde324ce-8569-4dda-bf27-4a0a0aa91a36' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='ae9703d9-be4e-4e8a-9288-3df4067ba126'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('ae9703d9-be4e-4e8a-9288-3df4067ba126', 'f2dc65e8-c66f-4a29-ba96-3baa3253cd5a')
/
update KSEN_LUI set OBJ_ID='c3667d60-6aaf-4f1a-874a-4641a3199c26', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='9c7796d1-4985-4e4f-a376-f94735348147', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1002', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1002', DESCR_PLAIN='1002', REF_URL='' where ID='01ed534d-7d0f-4659-b931-8284bed03855' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='0e0beb07-c1fa-498c-b7cf-e9ad27ca6721', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='01ed534d-7d0f-4659-b931-8284bed03855', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='13510417-4172-45e4-a7b1-53a9b2bfdbe1' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='d48d2e23-7404-4d10-ac98-62012f3cbb3b', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='8070ba81-9e12-46ef-a1cc-7c55b4b9052f', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lab', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lab', MAX_SEATS=24, MIN_SEATS=24, NAME='Lab', DESCR_PLAIN='Courses with lab', REF_URL='null' where ID='ba2bf7e8-0e81-461e-aa8f-10b70e9cbe21' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='a3cf70b0-d08d-47ac-a38b-8fc4b4bd7c3c', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='W', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='ba2bf7e8-0e81-461e-aa8f-10b70e9cbe21', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='3627dd41-1eca-47e1-bd0a-b52f6b031511' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='2babf542-329c-417f-acd1-dfeef0fb329d', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='ba2bf7e8-0e81-461e-aa8f-10b70e9cbe21', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='23c84d4f-ea3d-48c4-9920-64383d0aedd9' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='ba2bf7e8-0e81-461e-aa8f-10b70e9cbe21'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('ba2bf7e8-0e81-461e-aa8f-10b70e9cbe21', 'bc3db6d4-f795-471b-aa60-4f8be6ec7500')
/
update KSEN_LUI set OBJ_ID='834808fd-bf0a-468e-8421-1c0a87bea44a', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='7f9a588a-faaa-44fe-92c1-41ecbc0fcea3', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=240, MIN_SEATS=240, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='463279c1-d545-4743-b72e-c8d84bac1dbe' and VER_NBR=2
/
update KSEN_LUI_IDENT set OBJ_ID='caf6c4ed-6109-437a-9c2a-962378d9b8c9', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='N', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='463279c1-d545-4743-b72e-c8d84bac1dbe', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='322a32cc-2811-4161-a259-2c6cc904bb8f' and VER_NBR=1
/
update KSEN_LUI_LU_CD set OBJ_ID='ba29ac2c-416e-4afd-aa2b-9297112a4003', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', LUI_ID='463279c1-d545-4743-b72e-c8d84bac1dbe', DESCR_PLAIN='', LUI_LUCD_TYPE='kuali.lu.code.honorsOffering', VALUE='0' where ID='1486d30e-55e8-456e-b897-ffae38ca5d6a' and VER_NBR=2
/
delete from KSEN_LUI_SCHEDULE where LUI_ID='463279c1-d545-4743-b72e-c8d84bac1dbe'
/
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) values ('463279c1-d545-4743-b72e-c8d84bac1dbe', 'a721103b-99a3-4224-8ae4-e1b960ee0387')
/
update KSEN_LUI set OBJ_ID='34722563-43b5-443b-b64a-3150abaa5310', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1018', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1018', DESCR_PLAIN='1018', REF_URL='' where ID='5fbf88bc-1179-4f9e-827b-61644ee24497' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='3f5bc0cb-5104-4e3f-9862-3f4c69d1b8d2', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='5fbf88bc-1179-4f9e-827b-61644ee24497', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='9bd1b3f0-0e6d-406b-9164-cea7ed3236b1' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='176bf63d-4362-4fb6-8bb6-4fe155cf6c32', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1019', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1019', DESCR_PLAIN='1019', REF_URL='' where ID='3c02fada-78ac-488b-8e02-131a2034ab1c' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='ae364465-2ab9-4170-9140-cec308d7ffb1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='3c02fada-78ac-488b-8e02-131a2034ab1c', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='d4e91ccc-7190-4338-ae13-2f688579af90' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='159df31d-582a-4894-8663-c9fbd908a33a', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1017', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1017', DESCR_PLAIN='1017', REF_URL='' where ID='d24fbbb7-3074-4ce3-bfa8-0e838b4eee6e' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='4984288f-c507-4d80-9970-19276a93415d', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='d24fbbb7-3074-4ce3-bfa8-0e838b4eee6e', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='0a6b6a49-d3ed-4734-ac64-78b1fb85c92a' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='118e7678-95ee-4572-9045-7efa5170e400', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1023', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1023', DESCR_PLAIN='1023', REF_URL='' where ID='b76b49a2-15a9-4fe8-bb2f-25a91fe694c6' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='55abffda-efeb-4484-8b7e-b357f55c0492', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='b76b49a2-15a9-4fe8-bb2f-25a91fe694c6', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='8dfb8b16-513c-49b6-8ca9-a724875f67cc' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='65bbcd91-02f4-4848-8ae3-0a6a1fe028f0', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1021', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1021', DESCR_PLAIN='1021', REF_URL='' where ID='d79db2d5-8818-4372-8872-6322be3b8416' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='e6152208-31f5-4dd9-be09-cdeb47dfeb33', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='d79db2d5-8818-4372-8872-6322be3b8416', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='572adc5a-2dac-40cc-a27a-4ec51fd0d490' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='5a4af571-96da-4930-abc3-656a1239d9f8', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1015', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1015', DESCR_PLAIN='1015', REF_URL='' where ID='45c948e5-1a09-4f08-b09d-88d27725dd0b' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='72f02820-9668-4953-bbd7-aae9be169ecb', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='45c948e5-1a09-4f08-b09d-88d27725dd0b', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='ac42523f-17d0-4136-9065-68a9bc876006' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='c5a60393-10dc-447f-bf82-d5cb047ea326', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1016', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1016', DESCR_PLAIN='1016', REF_URL='' where ID='8178a66a-1624-4558-8832-d092350998ab' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='d83a7318-30af-4686-a0e7-0e3f98a032a1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='8178a66a-1624-4558-8832-d092350998ab', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='c06c5650-e093-45a7-b3c5-1cc4fb33d6aa' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='a30051df-0622-44f2-9b81-5f12568c75ce', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1014', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1014', DESCR_PLAIN='1014', REF_URL='' where ID='014f2b8a-dd20-40dd-8c11-ca7871acf325' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='6503c066-dfba-472a-893c-737bb84dfa5f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='014f2b8a-dd20-40dd-8c11-ca7871acf325', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='36b684e4-2c35-4a78-9d39-4c22f839faaf' and VER_NBR=0
/
update KSEN_LUI set OBJ_ID='edf8cd96-3c8f-4aa6-ae33-aa45adf2a197', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1020', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1020', DESCR_PLAIN='1020', REF_URL='' where ID='c604a36f-6008-4a29-82de-6d0ed088561f' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='523688fc-391e-462f-ba2f-90957e5f94f7', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='c604a36f-6008-4a29-82de-6d0ed088561f', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='76a37a72-8f07-4342-8bdd-ee843d1042ac' and VER_NBR=0
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8821eeda-5efd-4f0d-b715-f93e4a1658bb', 'kuali.soc.state.published', 'bc662f64-5078-431a-bc66-e4e586ef38f3', '2013-06-04T15:06:08-0400', 'ee4b9ff4-2cf0-4e5a-884f-35b4cadc5157')
/
update KSEN_LUI set OBJ_ID='9877953c-c685-4ae6-890b-da8e8b12e04b', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', ATP_ID='kuali.atp.2012CETerm1', CLU_ID='56a7d3c8-4a55-46a6-853f-63b393b13dcc', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1022', LUI_STATE='kuali.lui.registration.group.state.offered', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1022', DESCR_PLAIN='1022', REF_URL='' where ID='c5d67f9e-c18e-43d4-9d06-dbff138b0f55' and VER_NBR=0
/
update KSEN_LUI_IDENT set OBJ_ID='75e6de57-efd6-4663-8cb4-441f2eb49f6f', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', LUI_CD='', DIVISION='', IDENT_LEVEL='', LNG_NAME='', LUI_ID='c5d67f9e-c18e-43d4-9d06-dbff138b0f55', ORGID='', SHRT_NAME='', LUI_ID_STATE='kuali.lui.identifier.state.active', SUFX_CD='', LUI_ID_TYPE='kuali.lui.identifier.type.official', VARTN='' where ID='49888c09-d09e-41d1-991d-99235ebd3d73' and VER_NBR=0
/
update KSEN_SOC set OBJ_ID='5e67a0c4-e1e7-41d2-bf8e-3fd5dd00729e', VER_NBR=6, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-06-04 15:06:08.571', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.published', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2012CETerm1', UNITS_CONTENT_OWNER_ID='' where ID='bc662f64-5078-431a-bc66-e4e586ef38f3' and VER_NBR=5
/
