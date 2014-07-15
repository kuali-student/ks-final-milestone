-- KSENROLL-13570
-- Fall 2012, HIST266 -> changing max enroll to 4
update KSEN_LUI set OBJ_ID='68a310c6-da92-4789-9f0b-1b507476edf9', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-07-14 14:31:51.576', ATP_ID='kuali.atp.2012Fall', CLU_ID='cd841f27-7cd7-46ce-bcc4-6e04debdcfc4', EFF_DT=TIMESTAMP '2012-08-28 20:00:00.0', EXPIR_DT='', DESCR_FORMATTED='Courses with lecture', LUI_STATE='kuali.lui.activity.offering.state.offered', LUI_TYPE='kuali.lui.type.activity.offering.lecture', MAX_SEATS=4, MIN_SEATS=4, NAME='Lecture', DESCR_PLAIN='Courses with lecture', REF_URL='null' where ID='5732c691-ef9c-4b30-bbda-9fa5decaa41f' and VER_NBR=1
/

-- b.janek registered
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('76e7b8dd-73fb-4f2f-aad6-c84ab5b98427', 0, 'B.JANEK', TIMESTAMP '2014-07-15 09:41:49.935', 'B.JANEK', TIMESTAMP '2014-07-15 09:41:49.935', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.registration.cart', '', 'B.JANEK', 'fa7b5acf-de98-4e87-a52c-d7937d821e13')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('ef6dcda7-a2a0-4127-9302-424455756d7a', 0, 'B.JANEK', TIMESTAMP '2014-07-15 09:42:03.624', 'B.JANEK', TIMESTAMP '2014-07-15 09:42:03.624', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', 'fa7b5acf-de98-4e87-a52c-d7937d821e13', 'B.JANEK', '', '0847d700-f91d-4eb5-a16a-560044991512')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('d55bf649-6704-4e84-9d42-8f1a63833ee3', '0847d700-f91d-4eb5-a16a-560044991512', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '78d7ccbf-b12b-47f7-841c-c34b7707a88b')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('faee6acf-aca9-4756-866e-0ca6a8e221dc', '0847d700-f91d-4eb5-a16a-560044991512', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '01418a6b-2b0c-4ac2-acda-37e935e128f1')
/
update KSEN_LPR_TRANS set OBJ_ID='76e7b8dd-73fb-4f2f-aad6-c84ab5b98427', VER_NBR=1, UPDATEID='B.JANEK', UPDATETIME=TIMESTAMP '2014-07-15 09:42:03.574', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.new', LPR_TRANS_TYPE='kuali.lpr.trans.type.registration.cart', NAME='', REQUESTING_PERS_ID='B.JANEK' where ID='fa7b5acf-de98-4e87-a52c-d7937d821e13' and VER_NBR=0
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='fa7b5acf-de98-4e87-a52c-d7937d821e13' where ID='0847d700-f91d-4eb5-a16a-560044991512'
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('0847d700-f91d-4eb5-a16a-560044991512', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('0847d700-f91d-4eb5-a16a-560044991512', 'kuali.result.value.credit.degree.3.0')
/
update KSEN_LPR_TRANS set OBJ_ID='76e7b8dd-73fb-4f2f-aad6-c84ab5b98427', VER_NBR=2, UPDATEID='B.JANEK', UPDATETIME=TIMESTAMP '2014-07-15 09:42:08.604', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.new', LPR_TRANS_TYPE='kuali.lpr.trans.type.registration.cart', NAME='', REQUESTING_PERS_ID='B.JANEK' where ID='fa7b5acf-de98-4e87-a52c-d7937d821e13' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RVG where LPR_TRANS_ITEM_ID='0847d700-f91d-4eb5-a16a-560044991512'
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID=null where LPR_TRANS_ID='fa7b5acf-de98-4e87-a52c-d7937d821e13'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='78d7ccbf-b12b-47f7-841c-c34b7707a88b'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='01418a6b-2b0c-4ac2-acda-37e935e128f1'
/
delete from KSEN_LPR_TRANS_ITEM where ID='0847d700-f91d-4eb5-a16a-560044991512' and VER_NBR=0
/
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('a8798388-5373-4d3d-91e0-9e4d199cceea', 0, 'B.JANEK', TIMESTAMP '2014-07-15 09:42:08.604', 'B.JANEK', TIMESTAMP '2014-07-15 09:42:08.604', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.register', '', 'B.JANEK', 'b28dfe44-9a2c-4bd8-98d0-d1b1029fc52d')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('cefb1363-e911-415d-9402-f5116057e1f3', 0, 'B.JANEK', TIMESTAMP '2014-07-15 09:42:03.624', 'B.JANEK', TIMESTAMP '2014-07-15 09:42:08.604', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', 'b28dfe44-9a2c-4bd8-98d0-d1b1029fc52d', 'B.JANEK', '', '0847d700-f91d-4eb5-a16a-560044991512')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('b8048aaf-e8a3-4981-9429-8c04a1362570', '0847d700-f91d-4eb5-a16a-560044991512', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '507574eb-8ad7-47f6-88dc-3c4457f5bd52')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('8910f066-6cbd-4ae1-840c-61bd6d1d6d01', '0847d700-f91d-4eb5-a16a-560044991512', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '3b089969-986c-42ce-bdac-4c6dbea3bc14')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('0847d700-f91d-4eb5-a16a-560044991512', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('0847d700-f91d-4eb5-a16a-560044991512', 'kuali.result.value.credit.degree.3.0')
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='b28dfe44-9a2c-4bd8-98d0-d1b1029fc52d' where ID='0847d700-f91d-4eb5-a16a-560044991512'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('a8c0070c-b779-4e8a-8c17-005203d0ffbc', '0847d700-f91d-4eb5-a16a-560044991512', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '0bd0fd7a-c202-4501-94c8-7654999eaca3')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('be699934-21c3-48b0-8627-727754b486f6', '0847d700-f91d-4eb5-a16a-560044991512', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '72d85d46-d717-4d8e-ac9d-1d3f6117093e')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=1, UPDATEID='B.JANEK', UPDATETIME=TIMESTAMP '2014-07-15 09:42:08.604', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.processing', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.JANEK', LTI_RESULTING_LPR_ID='' where ID='0847d700-f91d-4eb5-a16a-560044991512' and VER_NBR=0
/
update KSEN_LPR_TRANS set OBJ_ID='a8798388-5373-4d3d-91e0-9e4d199cceea', VER_NBR=1, UPDATEID='B.JANEK', UPDATETIME=TIMESTAMP '2012-09-05 00:01:00.0', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.processing', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.JANEK' where ID='b28dfe44-9a2c-4bd8-98d0-d1b1029fc52d' and VER_NBR=0
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='507574eb-8ad7-47f6-88dc-3c4457f5bd52'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='3b089969-986c-42ce-bdac-4c6dbea3bc14'
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('05d4c9fe-ab62-4151-8f12-88ffe4c88e8d', 0, 'B.JANEK', TIMESTAMP '2014-07-15 09:42:09.095', 'B.JANEK', TIMESTAMP '2014-07-15 09:42:09.095', 'kuali.atp.2012Fall', '', '3.0', TIMESTAMP '2014-07-15 09:42:09.113', '', 'kuali.resultComponent.grade.letter', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', 'c9f898d5-9d6d-4513-92cc-f09bc666a3fa', 'B.JANEK', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.registration.group', 'c9f898d5-9d6d-4513-92cc-f09bc666a3fa')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('c9f898d5-9d6d-4513-92cc-f09bc666a3fa', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('c9f898d5-9d6d-4513-92cc-f09bc666a3fa', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('455cf5bc-c622-46b3-ab7e-b7aedf1de588', 0, 'B.JANEK', TIMESTAMP '2014-07-15 09:42:09.095', 'B.JANEK', TIMESTAMP '2014-07-15 09:42:09.095', 'kuali.atp.2012Fall', '', '3.0', TIMESTAMP '2014-07-15 09:42:09.113', '', 'kuali.resultComponent.grade.letter', '2ee27af5-2150-42a5-8fd9-52e6305d51c5', 'c9f898d5-9d6d-4513-92cc-f09bc666a3fa', 'B.JANEK', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.course.offering', 'd8e5e344-4d76-409f-855f-25a340ef4a29')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('d8e5e344-4d76-409f-855f-25a340ef4a29', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('d8e5e344-4d76-409f-855f-25a340ef4a29', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('1b12a996-c6a3-4ef2-bda1-5bc0050f49a5', 0, 'B.JANEK', TIMESTAMP '2014-07-15 09:42:09.095', 'B.JANEK', TIMESTAMP '2014-07-15 09:42:09.095', 'kuali.atp.2012Fall', '', '', TIMESTAMP '2014-07-15 09:42:09.113', '', '', '5732c691-ef9c-4b30-bbda-9fa5decaa41f', 'c9f898d5-9d6d-4513-92cc-f09bc666a3fa', 'B.JANEK', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.activity.offering', '70b1f5f9-5bb5-4ec5-bdbb-b8e9067f986a')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('7af39499-dbb9-4d65-a5cb-e8b1922d7f3b', '0847d700-f91d-4eb5-a16a-560044991512', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '5e1237c2-0f42-44ff-bed2-6a9ac1ff56e1')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('e175277c-32ec-43e4-b9f7-fa1226392852', '0847d700-f91d-4eb5-a16a-560044991512', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '46d83fd8-f6d0-4da9-9924-fdd5be05c07a')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=2, UPDATEID='B.JANEK', UPDATETIME=TIMESTAMP '2014-07-15 09:42:09.095', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.succeeded', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.JANEK', LTI_RESULTING_LPR_ID='' where ID='0847d700-f91d-4eb5-a16a-560044991512' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='72d85d46-d717-4d8e-ac9d-1d3f6117093e'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='0bd0fd7a-c202-4501-94c8-7654999eaca3'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('9f67ac7d-a642-497f-90d8-7687540f06e1', '0847d700-f91d-4eb5-a16a-560044991512', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '12b35dfd-0ba9-4bc3-9148-82b2ffd0a277')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('093b1e6a-8583-41ea-9d55-211adae41ff8', '0847d700-f91d-4eb5-a16a-560044991512', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '96288510-6911-4ad7-bb94-60e8eb9c08c8')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=3, UPDATEID='B.JANEK', UPDATETIME=TIMESTAMP '2014-07-15 09:42:09.095', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.succeeded', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.JANEK', LTI_RESULTING_LPR_ID='' where ID='0847d700-f91d-4eb5-a16a-560044991512' and VER_NBR=2
/
update KSEN_LPR_TRANS set OBJ_ID='a8798388-5373-4d3d-91e0-9e4d199cceea', VER_NBR=2, UPDATEID='B.JANEK', UPDATETIME=TIMESTAMP '2014-07-15 09:42:09.248', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.succeeded', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.JANEK' where ID='b28dfe44-9a2c-4bd8-98d0-d1b1029fc52d' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='46d83fd8-f6d0-4da9-9924-fdd5be05c07a'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='5e1237c2-0f42-44ff-bed2-6a9ac1ff56e1'
/

-- r.nelsonv registered
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('86a9c991-65e6-4679-aed4-67db4e81762e', 0, 'R.NELSONV', TIMESTAMP '2014-07-15 09:47:33.443', 'R.NELSONV', TIMESTAMP '2014-07-15 09:47:33.443', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.registration.cart', '', 'R.NELSONV', '9d0d6fce-0480-4b14-ac91-4a3f55b9f85e')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('d357b2fe-c5a1-4658-a957-8779528469be', 0, 'R.NELSONV', TIMESTAMP '2014-07-15 09:47:44.456', 'R.NELSONV', TIMESTAMP '2014-07-15 09:47:44.456', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '9d0d6fce-0480-4b14-ac91-4a3f55b9f85e', 'R.NELSONV', '', '951f4651-32d4-4cd8-a6c9-4f14d6952844')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('a1111dfc-18ad-459d-b06f-7b71b02506f1', '951f4651-32d4-4cd8-a6c9-4f14d6952844', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'aa67b979-0077-4c18-b2af-9afbfa80980a')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('9e8cf7df-94fd-4ea0-85ae-572f61723dd0', '951f4651-32d4-4cd8-a6c9-4f14d6952844', 'kuali.lpr.trans.item.option.oktowaitlist', '0', 'b732421f-07a1-4b1c-946f-524aebb68fbe')
/
update KSEN_LPR_TRANS set OBJ_ID='86a9c991-65e6-4679-aed4-67db4e81762e', VER_NBR=1, UPDATEID='R.NELSONV', UPDATETIME=TIMESTAMP '2014-07-15 09:47:44.439', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.new', LPR_TRANS_TYPE='kuali.lpr.trans.type.registration.cart', NAME='', REQUESTING_PERS_ID='R.NELSONV' where ID='9d0d6fce-0480-4b14-ac91-4a3f55b9f85e' and VER_NBR=0
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='9d0d6fce-0480-4b14-ac91-4a3f55b9f85e' where ID='951f4651-32d4-4cd8-a6c9-4f14d6952844'
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('951f4651-32d4-4cd8-a6c9-4f14d6952844', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('951f4651-32d4-4cd8-a6c9-4f14d6952844', 'kuali.result.value.credit.degree.3.0')
/
update KSEN_LPR_TRANS set OBJ_ID='86a9c991-65e6-4679-aed4-67db4e81762e', VER_NBR=2, UPDATEID='R.NELSONV', UPDATETIME=TIMESTAMP '2014-07-15 09:47:49.041', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.new', LPR_TRANS_TYPE='kuali.lpr.trans.type.registration.cart', NAME='', REQUESTING_PERS_ID='R.NELSONV' where ID='9d0d6fce-0480-4b14-ac91-4a3f55b9f85e' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RVG where LPR_TRANS_ITEM_ID='951f4651-32d4-4cd8-a6c9-4f14d6952844'
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID=null where LPR_TRANS_ID='9d0d6fce-0480-4b14-ac91-4a3f55b9f85e'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='aa67b979-0077-4c18-b2af-9afbfa80980a'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='b732421f-07a1-4b1c-946f-524aebb68fbe'
/
delete from KSEN_LPR_TRANS_ITEM where ID='951f4651-32d4-4cd8-a6c9-4f14d6952844' and VER_NBR=0
/
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('2cb082cb-755c-4d78-b650-7694a8d829a4', 0, 'R.NELSONV', TIMESTAMP '2014-07-15 09:47:49.041', 'R.NELSONV', TIMESTAMP '2014-07-15 09:47:49.041', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.register', '', 'R.NELSONV', '6d5d286e-48a1-4c05-85ff-2a8055c3c098')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('3cb5e930-e4c3-48c8-acdd-f897db089ab8', 0, 'R.NELSONV', TIMESTAMP '2014-07-15 09:47:44.456', 'R.NELSONV', TIMESTAMP '2014-07-15 09:47:49.041', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '6d5d286e-48a1-4c05-85ff-2a8055c3c098', 'R.NELSONV', '', '951f4651-32d4-4cd8-a6c9-4f14d6952844')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('6253c051-5f28-4b37-a9ab-fbe717b98611', '951f4651-32d4-4cd8-a6c9-4f14d6952844', 'kuali.lpr.trans.item.option.oktowaitlist', '0', 'e9f6a2d7-9def-4e46-9cdc-478ee7cc7c1b')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('22cb10d1-259e-4057-b02b-2a9b7de4936d', '951f4651-32d4-4cd8-a6c9-4f14d6952844', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'fffd4ca0-560f-4f63-9b28-d3a74fa2bc30')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('951f4651-32d4-4cd8-a6c9-4f14d6952844', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('951f4651-32d4-4cd8-a6c9-4f14d6952844', 'kuali.result.value.credit.degree.3.0')
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='6d5d286e-48a1-4c05-85ff-2a8055c3c098' where ID='951f4651-32d4-4cd8-a6c9-4f14d6952844'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('84ff7365-6a10-4935-94b4-27ccdd4d51f8', '951f4651-32d4-4cd8-a6c9-4f14d6952844', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'b8b95928-85be-4a9e-92aa-ab000b2683fb')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('71e9ac28-181a-439a-a773-c110772331e8', '951f4651-32d4-4cd8-a6c9-4f14d6952844', 'kuali.lpr.trans.item.option.oktowaitlist', '0', 'bda092d7-8c2d-4590-8af6-63d003031369')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=1, UPDATEID='R.NELSONV', UPDATETIME=TIMESTAMP '2014-07-15 09:47:49.041', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.processing', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='R.NELSONV', LTI_RESULTING_LPR_ID='' where ID='951f4651-32d4-4cd8-a6c9-4f14d6952844' and VER_NBR=0
/
update KSEN_LPR_TRANS set OBJ_ID='2cb082cb-755c-4d78-b650-7694a8d829a4', VER_NBR=1, UPDATEID='R.NELSONV', UPDATETIME=TIMESTAMP '2012-09-05 00:01:00.0', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.processing', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='R.NELSONV' where ID='6d5d286e-48a1-4c05-85ff-2a8055c3c098' and VER_NBR=0
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='fffd4ca0-560f-4f63-9b28-d3a74fa2bc30'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='e9f6a2d7-9def-4e46-9cdc-478ee7cc7c1b'
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('46f59bbb-3aa5-4290-802b-af2cf900b269', 0, 'R.NELSONV', TIMESTAMP '2014-07-15 09:47:49.209', 'R.NELSONV', TIMESTAMP '2014-07-15 09:47:49.209', 'kuali.atp.2012Fall', '', '3.0', TIMESTAMP '2014-07-15 09:47:49.213', '', 'kuali.resultComponent.grade.letter', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '931693b0-8a8b-4688-bb56-57a7e4e964a6', 'R.NELSONV', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.registration.group', '931693b0-8a8b-4688-bb56-57a7e4e964a6')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('931693b0-8a8b-4688-bb56-57a7e4e964a6', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('931693b0-8a8b-4688-bb56-57a7e4e964a6', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('77ef4ec5-f504-46fa-8af3-da12ff8f0597', 0, 'R.NELSONV', TIMESTAMP '2014-07-15 09:47:49.209', 'R.NELSONV', TIMESTAMP '2014-07-15 09:47:49.209', 'kuali.atp.2012Fall', '', '3.0', TIMESTAMP '2014-07-15 09:47:49.213', '', 'kuali.resultComponent.grade.letter', '2ee27af5-2150-42a5-8fd9-52e6305d51c5', '931693b0-8a8b-4688-bb56-57a7e4e964a6', 'R.NELSONV', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.course.offering', 'ce4b1639-572b-4936-84eb-34d77b07744a')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ce4b1639-572b-4936-84eb-34d77b07744a', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ce4b1639-572b-4936-84eb-34d77b07744a', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('a263e5c3-21d9-4f7e-a23b-efef58976ce3', 0, 'R.NELSONV', TIMESTAMP '2014-07-15 09:47:49.209', 'R.NELSONV', TIMESTAMP '2014-07-15 09:47:49.209', 'kuali.atp.2012Fall', '', '', TIMESTAMP '2014-07-15 09:47:49.213', '', '', '5732c691-ef9c-4b30-bbda-9fa5decaa41f', '931693b0-8a8b-4688-bb56-57a7e4e964a6', 'R.NELSONV', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.activity.offering', 'a1ac89cb-0763-440f-9400-143c2812b2c7')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('754131e4-d91e-414f-8890-58ce6a6797f5', '951f4651-32d4-4cd8-a6c9-4f14d6952844', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '8a8929b4-7cc4-4235-859c-dfce13addf6b')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('a1d2b1ee-6301-4214-96ac-e3180b0068f7', '951f4651-32d4-4cd8-a6c9-4f14d6952844', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '24f148de-ae8c-4393-bd01-d17c3f2db40b')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=2, UPDATEID='R.NELSONV', UPDATETIME=TIMESTAMP '2014-07-15 09:47:49.209', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.succeeded', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='R.NELSONV', LTI_RESULTING_LPR_ID='' where ID='951f4651-32d4-4cd8-a6c9-4f14d6952844' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='bda092d7-8c2d-4590-8af6-63d003031369'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='b8b95928-85be-4a9e-92aa-ab000b2683fb'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('6948ca4d-23f6-45c0-80e2-52f52ae7a6e2', '951f4651-32d4-4cd8-a6c9-4f14d6952844', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '29b343f0-be05-4775-8757-a7dd3e6901bc')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('77b1fc33-97a8-44c8-ba5f-38113e3a1fed', '951f4651-32d4-4cd8-a6c9-4f14d6952844', 'kuali.lpr.trans.item.option.oktowaitlist', '0', 'ce8cab05-219a-42dc-a7f0-2fbd1b6edd7e')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=3, UPDATEID='R.NELSONV', UPDATETIME=TIMESTAMP '2014-07-15 09:47:49.209', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.succeeded', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='R.NELSONV', LTI_RESULTING_LPR_ID='' where ID='951f4651-32d4-4cd8-a6c9-4f14d6952844' and VER_NBR=2
/
update KSEN_LPR_TRANS set OBJ_ID='2cb082cb-755c-4d78-b650-7694a8d829a4', VER_NBR=2, UPDATEID='R.NELSONV', UPDATETIME=TIMESTAMP '2014-07-15 09:47:49.258', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.succeeded', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='R.NELSONV' where ID='6d5d286e-48a1-4c05-85ff-2a8055c3c098' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='24f148de-ae8c-4393-bd01-d17c3f2db40b'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='8a8929b4-7cc4-4235-859c-dfce13addf6b'
/

-- b.corab registered
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('9cbb2827-08c9-48aa-85e8-22c52fb92823', 0, 'B.CORAB', TIMESTAMP '2014-07-15 09:50:50.613', 'B.CORAB', TIMESTAMP '2014-07-15 09:50:50.613', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.registration.cart', '', 'B.CORAB', '9bcb1377-ffe3-4c40-83bd-e17ea4c00f30')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('15908508-d451-4eaa-a015-3773a3bf97b6', 0, 'B.CORAB', TIMESTAMP '2014-07-15 09:50:58.496', 'B.CORAB', TIMESTAMP '2014-07-15 09:50:58.496', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '9bcb1377-ffe3-4c40-83bd-e17ea4c00f30', 'B.CORAB', '', 'c8902abb-66ff-4881-9cef-3e170d83caa5')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('98400c29-72d4-4031-ab8e-90c4d540ee6f', 'c8902abb-66ff-4881-9cef-3e170d83caa5', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'fcac426f-636e-4822-98ed-6f0cc7cc54af')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('09f32a19-fb31-4bb4-8107-27f47777267a', 'c8902abb-66ff-4881-9cef-3e170d83caa5', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '2699b116-09da-4c77-97bf-8e383d0d148e')
/
update KSEN_LPR_TRANS set OBJ_ID='9cbb2827-08c9-48aa-85e8-22c52fb92823', VER_NBR=1, UPDATEID='B.CORAB', UPDATETIME=TIMESTAMP '2014-07-15 09:50:58.481', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.new', LPR_TRANS_TYPE='kuali.lpr.trans.type.registration.cart', NAME='', REQUESTING_PERS_ID='B.CORAB' where ID='9bcb1377-ffe3-4c40-83bd-e17ea4c00f30' and VER_NBR=0
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='9bcb1377-ffe3-4c40-83bd-e17ea4c00f30' where ID='c8902abb-66ff-4881-9cef-3e170d83caa5'
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('c8902abb-66ff-4881-9cef-3e170d83caa5', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('c8902abb-66ff-4881-9cef-3e170d83caa5', 'kuali.result.value.credit.degree.3.0')
/
update KSEN_LPR_TRANS set OBJ_ID='9cbb2827-08c9-48aa-85e8-22c52fb92823', VER_NBR=2, UPDATEID='B.CORAB', UPDATETIME=TIMESTAMP '2014-07-15 09:51:01.586', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.new', LPR_TRANS_TYPE='kuali.lpr.trans.type.registration.cart', NAME='', REQUESTING_PERS_ID='B.CORAB' where ID='9bcb1377-ffe3-4c40-83bd-e17ea4c00f30' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RVG where LPR_TRANS_ITEM_ID='c8902abb-66ff-4881-9cef-3e170d83caa5'
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID=null where LPR_TRANS_ID='9bcb1377-ffe3-4c40-83bd-e17ea4c00f30'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='fcac426f-636e-4822-98ed-6f0cc7cc54af'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='2699b116-09da-4c77-97bf-8e383d0d148e'
/
delete from KSEN_LPR_TRANS_ITEM where ID='c8902abb-66ff-4881-9cef-3e170d83caa5' and VER_NBR=0
/
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('96ecf77c-f79f-4605-b58b-69e1eb9de243', 0, 'B.CORAB', TIMESTAMP '2014-07-15 09:51:01.586', 'B.CORAB', TIMESTAMP '2014-07-15 09:51:01.586', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.register', '', 'B.CORAB', '1804dc7b-780c-404d-82a7-b2b6775f7087')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('59c3678a-f20e-4c0e-a2d1-6462b53df8dc', 0, 'B.CORAB', TIMESTAMP '2014-07-15 09:50:58.496', 'B.CORAB', TIMESTAMP '2014-07-15 09:51:01.586', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '1804dc7b-780c-404d-82a7-b2b6775f7087', 'B.CORAB', '', 'c8902abb-66ff-4881-9cef-3e170d83caa5')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('e82c6c49-32ae-4c7f-b084-63e66428dc7f', 'c8902abb-66ff-4881-9cef-3e170d83caa5', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'ba6048d4-a2ca-4361-87da-d7ce4962cc2d')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('0fd641d5-2d02-40bf-af0b-9593d1a247bb', 'c8902abb-66ff-4881-9cef-3e170d83caa5', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '1881864a-5b1c-453c-8aa2-930a26f82d87')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('c8902abb-66ff-4881-9cef-3e170d83caa5', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('c8902abb-66ff-4881-9cef-3e170d83caa5', 'kuali.result.value.credit.degree.3.0')
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='1804dc7b-780c-404d-82a7-b2b6775f7087' where ID='c8902abb-66ff-4881-9cef-3e170d83caa5'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('08d555e8-52d9-417f-aca0-8058861e4f5e', 'c8902abb-66ff-4881-9cef-3e170d83caa5', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'ef71bdac-1d6c-4fbc-80ec-b4665e090f79')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('d12db3f0-8cce-48a8-b157-37c4b88d28c9', 'c8902abb-66ff-4881-9cef-3e170d83caa5', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '46dd6983-9cf0-44ce-ac61-7d509ce9d30a')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=1, UPDATEID='B.CORAB', UPDATETIME=TIMESTAMP '2014-07-15 09:51:01.586', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.processing', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.CORAB', LTI_RESULTING_LPR_ID='' where ID='c8902abb-66ff-4881-9cef-3e170d83caa5' and VER_NBR=0
/
update KSEN_LPR_TRANS set OBJ_ID='96ecf77c-f79f-4605-b58b-69e1eb9de243', VER_NBR=1, UPDATEID='B.CORAB', UPDATETIME=TIMESTAMP '2012-09-05 00:01:00.0', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.processing', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.CORAB' where ID='1804dc7b-780c-404d-82a7-b2b6775f7087' and VER_NBR=0
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='1881864a-5b1c-453c-8aa2-930a26f82d87'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='ba6048d4-a2ca-4361-87da-d7ce4962cc2d'
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('7545f8ec-dc11-49fe-a7d4-1c2db6d1187f', 0, 'B.CORAB', TIMESTAMP '2014-07-15 09:51:01.725', 'B.CORAB', TIMESTAMP '2014-07-15 09:51:01.725', 'kuali.atp.2012Fall', '', '3.0', TIMESTAMP '2014-07-15 09:51:01.729', '', 'kuali.resultComponent.grade.letter', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '27de7305-c603-4b5a-a1cc-6a4ffe20b013', 'B.CORAB', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.registration.group', '27de7305-c603-4b5a-a1cc-6a4ffe20b013')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('27de7305-c603-4b5a-a1cc-6a4ffe20b013', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('27de7305-c603-4b5a-a1cc-6a4ffe20b013', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('eda9553a-89e5-473d-b246-d0ff58850556', 0, 'B.CORAB', TIMESTAMP '2014-07-15 09:51:01.725', 'B.CORAB', TIMESTAMP '2014-07-15 09:51:01.725', 'kuali.atp.2012Fall', '', '3.0', TIMESTAMP '2014-07-15 09:51:01.729', '', 'kuali.resultComponent.grade.letter', '2ee27af5-2150-42a5-8fd9-52e6305d51c5', '27de7305-c603-4b5a-a1cc-6a4ffe20b013', 'B.CORAB', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.course.offering', '0856dacd-cc33-48cd-8619-2254fdd266a7')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('0856dacd-cc33-48cd-8619-2254fdd266a7', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('0856dacd-cc33-48cd-8619-2254fdd266a7', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('5e93ce94-ce04-4af4-a818-204ab4a51cd9', 0, 'B.CORAB', TIMESTAMP '2014-07-15 09:51:01.725', 'B.CORAB', TIMESTAMP '2014-07-15 09:51:01.725', 'kuali.atp.2012Fall', '', '', TIMESTAMP '2014-07-15 09:51:01.729', '', '', '5732c691-ef9c-4b30-bbda-9fa5decaa41f', '27de7305-c603-4b5a-a1cc-6a4ffe20b013', 'B.CORAB', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.activity.offering', '6857169b-42de-4c76-ab23-7791c94ffca1')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('325e348c-995e-41b3-82c6-e86e9f343b6d', 'c8902abb-66ff-4881-9cef-3e170d83caa5', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'f1b09986-cb42-4992-b100-13fc36841d9f')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('8c80f8a2-1419-44b3-bc48-6404d3489e71', 'c8902abb-66ff-4881-9cef-3e170d83caa5', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '1cac54eb-ef57-4231-8867-6dd13ca71176')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=2, UPDATEID='B.CORAB', UPDATETIME=TIMESTAMP '2014-07-15 09:51:01.725', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.succeeded', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.CORAB', LTI_RESULTING_LPR_ID='' where ID='c8902abb-66ff-4881-9cef-3e170d83caa5' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='ef71bdac-1d6c-4fbc-80ec-b4665e090f79'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='46dd6983-9cf0-44ce-ac61-7d509ce9d30a'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('e963bd9c-8164-4cd6-93fc-fa9d9e947ea3', 'c8902abb-66ff-4881-9cef-3e170d83caa5', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'b579ea90-d100-4d0d-8c44-e4aa8c6fb89c')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('362004c3-720f-4a70-93fe-65a774230b4a', 'c8902abb-66ff-4881-9cef-3e170d83caa5', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '91663dca-47c8-4ed0-8c18-bfbdce718f12')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=3, UPDATEID='B.CORAB', UPDATETIME=TIMESTAMP '2014-07-15 09:51:01.725', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.succeeded', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.CORAB', LTI_RESULTING_LPR_ID='' where ID='c8902abb-66ff-4881-9cef-3e170d83caa5' and VER_NBR=2
/
update KSEN_LPR_TRANS set OBJ_ID='96ecf77c-f79f-4605-b58b-69e1eb9de243', VER_NBR=2, UPDATEID='B.CORAB', UPDATETIME=TIMESTAMP '2014-07-15 09:51:01.77', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.succeeded', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.CORAB' where ID='1804dc7b-780c-404d-82a7-b2b6775f7087' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='f1b09986-cb42-4992-b100-13fc36841d9f'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='1cac54eb-ef57-4231-8867-6dd13ca71176'
/

-- b.emilyc registered
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('dc05446d-c19a-4a52-8771-c1f4668489b3', 0, 'B.EMILYC', TIMESTAMP '2014-07-15 09:53:52.52', 'B.EMILYC', TIMESTAMP '2014-07-15 09:53:52.52', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.registration.cart', '', 'B.EMILYC', '50c0d3a3-3bb7-48f0-93b3-3f3faff9cd30')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('d9e7dc48-57f7-42f5-a233-0bda6c594d53', 0, 'B.EMILYC', TIMESTAMP '2014-07-15 09:54:02.991', 'B.EMILYC', TIMESTAMP '2014-07-15 09:54:02.991', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '50c0d3a3-3bb7-48f0-93b3-3f3faff9cd30', 'B.EMILYC', '', '4b628de7-bfe9-4f06-a293-e2e3d07f082f')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('62e6abb2-4313-47a2-a6bb-5387fee1b4e0', '4b628de7-bfe9-4f06-a293-e2e3d07f082f', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'e526b881-2ed0-4208-8415-6c57ba92ac0b')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('e46ce6c6-1ad2-4b8f-9f67-e9c1fb452d0a', '4b628de7-bfe9-4f06-a293-e2e3d07f082f', 'kuali.lpr.trans.item.option.oktowaitlist', '0', 'e72bcd1d-fdaf-4252-b9d6-d9634aacf1c5')
/
update KSEN_LPR_TRANS set OBJ_ID='dc05446d-c19a-4a52-8771-c1f4668489b3', VER_NBR=1, UPDATEID='B.EMILYC', UPDATETIME=TIMESTAMP '2014-07-15 09:54:02.973', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.new', LPR_TRANS_TYPE='kuali.lpr.trans.type.registration.cart', NAME='', REQUESTING_PERS_ID='B.EMILYC' where ID='50c0d3a3-3bb7-48f0-93b3-3f3faff9cd30' and VER_NBR=0
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='50c0d3a3-3bb7-48f0-93b3-3f3faff9cd30' where ID='4b628de7-bfe9-4f06-a293-e2e3d07f082f'
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('4b628de7-bfe9-4f06-a293-e2e3d07f082f', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('4b628de7-bfe9-4f06-a293-e2e3d07f082f', 'kuali.result.value.credit.degree.3.0')
/
update KSEN_LPR_TRANS set OBJ_ID='dc05446d-c19a-4a52-8771-c1f4668489b3', VER_NBR=2, UPDATEID='B.EMILYC', UPDATETIME=TIMESTAMP '2014-07-15 09:54:05.461', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.new', LPR_TRANS_TYPE='kuali.lpr.trans.type.registration.cart', NAME='', REQUESTING_PERS_ID='B.EMILYC' where ID='50c0d3a3-3bb7-48f0-93b3-3f3faff9cd30' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RVG where LPR_TRANS_ITEM_ID='4b628de7-bfe9-4f06-a293-e2e3d07f082f'
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID=null where LPR_TRANS_ID='50c0d3a3-3bb7-48f0-93b3-3f3faff9cd30'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='e72bcd1d-fdaf-4252-b9d6-d9634aacf1c5'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='e526b881-2ed0-4208-8415-6c57ba92ac0b'
/
delete from KSEN_LPR_TRANS_ITEM where ID='4b628de7-bfe9-4f06-a293-e2e3d07f082f' and VER_NBR=0
/
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('2bdec951-1804-45f1-9cbc-1cef34634e2d', 0, 'B.EMILYC', TIMESTAMP '2014-07-15 09:54:05.461', 'B.EMILYC', TIMESTAMP '2014-07-15 09:54:05.461', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.register', '', 'B.EMILYC', '61f5db22-8100-4df1-9b9a-ca32216aec79')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('91346be8-70a6-4aec-ab8d-ca826e112a6e', 0, 'B.EMILYC', TIMESTAMP '2014-07-15 09:54:02.991', 'B.EMILYC', TIMESTAMP '2014-07-15 09:54:05.461', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '61f5db22-8100-4df1-9b9a-ca32216aec79', 'B.EMILYC', '', '4b628de7-bfe9-4f06-a293-e2e3d07f082f')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('1b9b7b18-ca34-4f48-a78e-aad8a5ebee99', '4b628de7-bfe9-4f06-a293-e2e3d07f082f', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'd13358ff-09db-4747-ae5d-462af746707a')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('dc0b966c-bf7e-44a1-9bb7-3d1086d92239', '4b628de7-bfe9-4f06-a293-e2e3d07f082f', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '122b00dd-8d29-49c1-8707-0d347da55f6a')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('4b628de7-bfe9-4f06-a293-e2e3d07f082f', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('4b628de7-bfe9-4f06-a293-e2e3d07f082f', 'kuali.result.value.credit.degree.3.0')
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='61f5db22-8100-4df1-9b9a-ca32216aec79' where ID='4b628de7-bfe9-4f06-a293-e2e3d07f082f'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('5c68eef5-4254-46a1-8c57-debd50005588', '4b628de7-bfe9-4f06-a293-e2e3d07f082f', 'kuali.lpr.trans.item.option.oktowaitlist', '0', 'fc47002b-6820-4940-a55a-4ebf1824740a')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('d6cee2cf-1bb9-4507-a492-25627a89b321', '4b628de7-bfe9-4f06-a293-e2e3d07f082f', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '3a581363-9529-4409-8c4e-3efab697c925')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=1, UPDATEID='B.EMILYC', UPDATETIME=TIMESTAMP '2014-07-15 09:54:05.461', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.processing', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.EMILYC', LTI_RESULTING_LPR_ID='' where ID='4b628de7-bfe9-4f06-a293-e2e3d07f082f' and VER_NBR=0
/
update KSEN_LPR_TRANS set OBJ_ID='2bdec951-1804-45f1-9cbc-1cef34634e2d', VER_NBR=1, UPDATEID='B.EMILYC', UPDATETIME=TIMESTAMP '2012-09-05 00:01:00.0', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.processing', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.EMILYC' where ID='61f5db22-8100-4df1-9b9a-ca32216aec79' and VER_NBR=0
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='d13358ff-09db-4747-ae5d-462af746707a'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='122b00dd-8d29-49c1-8707-0d347da55f6a'
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('ed76e642-3cd9-48d5-820a-6ecfabb0f0fe', 0, 'B.EMILYC', TIMESTAMP '2014-07-15 09:54:05.638', 'B.EMILYC', TIMESTAMP '2014-07-15 09:54:05.638', 'kuali.atp.2012Fall', '', '3.0', TIMESTAMP '2014-07-15 09:54:05.642', '', 'kuali.resultComponent.grade.letter', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '3c047eab-d6a4-4dcb-9bd7-68f3378c3b19', 'B.EMILYC', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.registration.group', '3c047eab-d6a4-4dcb-9bd7-68f3378c3b19')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('3c047eab-d6a4-4dcb-9bd7-68f3378c3b19', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('3c047eab-d6a4-4dcb-9bd7-68f3378c3b19', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('aa9a31df-46db-46ae-940a-0dfbfb45e7d9', 0, 'B.EMILYC', TIMESTAMP '2014-07-15 09:54:05.638', 'B.EMILYC', TIMESTAMP '2014-07-15 09:54:05.638', 'kuali.atp.2012Fall', '', '3.0', TIMESTAMP '2014-07-15 09:54:05.642', '', 'kuali.resultComponent.grade.letter', '2ee27af5-2150-42a5-8fd9-52e6305d51c5', '3c047eab-d6a4-4dcb-9bd7-68f3378c3b19', 'B.EMILYC', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.course.offering', '9e81e359-ef41-41c7-8757-490fcaad45e2')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('9e81e359-ef41-41c7-8757-490fcaad45e2', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('9e81e359-ef41-41c7-8757-490fcaad45e2', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('6932d4de-eaa5-4991-b811-bd42fc375288', 0, 'B.EMILYC', TIMESTAMP '2014-07-15 09:54:05.638', 'B.EMILYC', TIMESTAMP '2014-07-15 09:54:05.638', 'kuali.atp.2012Fall', '', '', TIMESTAMP '2014-07-15 09:54:05.642', '', '', '5732c691-ef9c-4b30-bbda-9fa5decaa41f', '3c047eab-d6a4-4dcb-9bd7-68f3378c3b19', 'B.EMILYC', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.activity.offering', '9004fe34-61b9-4150-a451-ae3871c6942a')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('0b0fb300-92a0-478e-83f1-5af2e4ce1925', '4b628de7-bfe9-4f06-a293-e2e3d07f082f', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '5b64bfee-6085-41e6-8d2b-48b385a4ef3c')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('e10d32ad-0c1a-4307-bb87-49e87bf16de0', '4b628de7-bfe9-4f06-a293-e2e3d07f082f', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '783faa60-fb1d-445a-a7a3-00854d01cb9e')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=2, UPDATEID='B.EMILYC', UPDATETIME=TIMESTAMP '2014-07-15 09:54:05.638', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.succeeded', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.EMILYC', LTI_RESULTING_LPR_ID='' where ID='4b628de7-bfe9-4f06-a293-e2e3d07f082f' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='fc47002b-6820-4940-a55a-4ebf1824740a'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='3a581363-9529-4409-8c4e-3efab697c925'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('13360a52-f062-4d99-8e65-d4235d744cb8', '4b628de7-bfe9-4f06-a293-e2e3d07f082f', 'kuali.lpr.trans.item.option.oktowaitlist', '0', 'c5590041-6619-40ba-826d-047fd602d94c')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('1a22a73d-63a9-4ca5-8ac2-7e12c5950583', '4b628de7-bfe9-4f06-a293-e2e3d07f082f', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '5c79c862-f0eb-4c23-a206-9480d509ee50')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=3, UPDATEID='B.EMILYC', UPDATETIME=TIMESTAMP '2014-07-15 09:54:05.638', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.succeeded', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.EMILYC', LTI_RESULTING_LPR_ID='' where ID='4b628de7-bfe9-4f06-a293-e2e3d07f082f' and VER_NBR=2
/
update KSEN_LPR_TRANS set OBJ_ID='2bdec951-1804-45f1-9cbc-1cef34634e2d', VER_NBR=2, UPDATEID='B.EMILYC', UPDATETIME=TIMESTAMP '2014-07-15 09:54:05.686', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.succeeded', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.EMILYC' where ID='61f5db22-8100-4df1-9b9a-ca32216aec79' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='5b64bfee-6085-41e6-8d2b-48b385a4ef3c'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='783faa60-fb1d-445a-a7a3-00854d01cb9e'
/

-- b.jasmink waitlisted
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('37236999-7a08-45d8-8cbb-43addd21ac0a', 0, 'B.JASMINK', TIMESTAMP '2014-07-15 09:57:54.137', 'B.JASMINK', TIMESTAMP '2014-07-15 09:57:54.137', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.registration.cart', '', 'B.JASMINK', 'bc3daf96-427d-4ca8-aa52-30c4ab466de8')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('f98aff4b-2245-4e8f-a70e-d698399a01bc', 0, 'B.JASMINK', TIMESTAMP '2014-07-15 09:58:06.725', 'B.JASMINK', TIMESTAMP '2014-07-15 09:58:06.725', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', 'bc3daf96-427d-4ca8-aa52-30c4ab466de8', 'B.JASMINK', '', '0caf7419-13cb-4e54-9766-3cff293c4fc2')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('8eaaaad9-29d9-4c9a-9c01-11f1f171188a', '0caf7419-13cb-4e54-9766-3cff293c4fc2', 'kuali.lpr.trans.item.option.oktowaitlist', '0', 'ea68b792-01e6-438a-9bc6-7198accf0ddd')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('707a2e37-5ede-4125-9c7a-7c9954f10a1e', '0caf7419-13cb-4e54-9766-3cff293c4fc2', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'af50e2af-5bc4-476a-83ef-2928171e8c7c')
/
update KSEN_LPR_TRANS set OBJ_ID='37236999-7a08-45d8-8cbb-43addd21ac0a', VER_NBR=1, UPDATEID='B.JASMINK', UPDATETIME=TIMESTAMP '2014-07-15 09:58:06.709', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.new', LPR_TRANS_TYPE='kuali.lpr.trans.type.registration.cart', NAME='', REQUESTING_PERS_ID='B.JASMINK' where ID='bc3daf96-427d-4ca8-aa52-30c4ab466de8' and VER_NBR=0
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='bc3daf96-427d-4ca8-aa52-30c4ab466de8' where ID='0caf7419-13cb-4e54-9766-3cff293c4fc2'
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('0caf7419-13cb-4e54-9766-3cff293c4fc2', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('0caf7419-13cb-4e54-9766-3cff293c4fc2', 'kuali.result.value.credit.degree.3.0')
/
update KSEN_LPR_TRANS set OBJ_ID='37236999-7a08-45d8-8cbb-43addd21ac0a', VER_NBR=2, UPDATEID='B.JASMINK', UPDATETIME=TIMESTAMP '2014-07-15 09:58:09.465', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.new', LPR_TRANS_TYPE='kuali.lpr.trans.type.registration.cart', NAME='', REQUESTING_PERS_ID='B.JASMINK' where ID='bc3daf96-427d-4ca8-aa52-30c4ab466de8' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RVG where LPR_TRANS_ITEM_ID='0caf7419-13cb-4e54-9766-3cff293c4fc2'
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID=null where LPR_TRANS_ID='bc3daf96-427d-4ca8-aa52-30c4ab466de8'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='ea68b792-01e6-438a-9bc6-7198accf0ddd'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='af50e2af-5bc4-476a-83ef-2928171e8c7c'
/
delete from KSEN_LPR_TRANS_ITEM where ID='0caf7419-13cb-4e54-9766-3cff293c4fc2' and VER_NBR=0
/
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('ec54d3b0-86a7-4605-949c-d381b85034a8', 0, 'B.JASMINK', TIMESTAMP '2014-07-15 09:58:09.465', 'B.JASMINK', TIMESTAMP '2014-07-15 09:58:09.465', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.register', '', 'B.JASMINK', '8a28a3b0-ad6f-4ab6-b462-7b13d402b8ae')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('c354e44e-80cc-4ef7-bf5d-d522f3bfb350', 0, 'B.JASMINK', TIMESTAMP '2014-07-15 09:58:06.725', 'B.JASMINK', TIMESTAMP '2014-07-15 09:58:09.465', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '8a28a3b0-ad6f-4ab6-b462-7b13d402b8ae', 'B.JASMINK', '', '0caf7419-13cb-4e54-9766-3cff293c4fc2')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('58d4cb29-60a4-4575-8c0c-1268f2cf55da', '0caf7419-13cb-4e54-9766-3cff293c4fc2', 'kuali.lpr.trans.item.option.oktowaitlist', '0', 'd0934cdd-a4df-4fc6-81ba-197c6b98487f')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('2f081fa3-c2e1-4490-86f4-76edd9400227', '0caf7419-13cb-4e54-9766-3cff293c4fc2', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '86250e91-6c73-42b2-857d-50024a8fed78')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('0caf7419-13cb-4e54-9766-3cff293c4fc2', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('0caf7419-13cb-4e54-9766-3cff293c4fc2', 'kuali.result.value.credit.degree.3.0')
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='8a28a3b0-ad6f-4ab6-b462-7b13d402b8ae' where ID='0caf7419-13cb-4e54-9766-3cff293c4fc2'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('6a8ee722-e38a-4845-85d5-036ffcbb306d', '0caf7419-13cb-4e54-9766-3cff293c4fc2', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '77450522-355d-4b43-926e-5f66ff8a5f90')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('160d5b36-75aa-4f6b-a1b1-f5fc40c6bcc6', '0caf7419-13cb-4e54-9766-3cff293c4fc2', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '9f9910ab-e1d4-472c-9237-9d0c53623f97')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=1, UPDATEID='B.JASMINK', UPDATETIME=TIMESTAMP '2014-07-15 09:58:09.465', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.processing', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.JASMINK', LTI_RESULTING_LPR_ID='' where ID='0caf7419-13cb-4e54-9766-3cff293c4fc2' and VER_NBR=0
/
update KSEN_LPR_TRANS set OBJ_ID='ec54d3b0-86a7-4605-949c-d381b85034a8', VER_NBR=1, UPDATEID='B.JASMINK', UPDATETIME=TIMESTAMP '2012-09-05 00:01:00.0', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.processing', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.JASMINK' where ID='8a28a3b0-ad6f-4ab6-b462-7b13d402b8ae' and VER_NBR=0
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='d0934cdd-a4df-4fc6-81ba-197c6b98487f'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='86250e91-6c73-42b2-857d-50024a8fed78'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('4ecba3f1-9820-4a75-b695-bf4f2d45c5fb', '0caf7419-13cb-4e54-9766-3cff293c4fc2', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '18a685da-7aca-4c28-9397-05e01775f9ce')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('5df152d7-c40a-4d5d-8ba7-750c8e3c8489', '0caf7419-13cb-4e54-9766-3cff293c4fc2', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '2373c7db-f917-4451-91e2-af6f291ac2d3')
/
insert into KSEN_LPR_TRANS_ITEM_VR (ELEMENT, ERROR_LEVEL_CD, VR_MESSAGE, LPR_TRANS_ITEM_ID, ID) values ('', 'ERROR', '{"messageKey":"kuali.lpr.trans.message.waitlist.available"}', '0caf7419-13cb-4e54-9766-3cff293c4fc2', 'f49808ad-beeb-416f-8ca0-655a29915eb6')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=2, UPDATEID='B.JASMINK', UPDATETIME=TIMESTAMP '2014-07-15 09:58:09.595', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.waitlistActionAvailable', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.JASMINK', LTI_RESULTING_LPR_ID='' where ID='0caf7419-13cb-4e54-9766-3cff293c4fc2' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='77450522-355d-4b43-926e-5f66ff8a5f90'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='9f9910ab-e1d4-472c-9237-9d0c53623f97'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('2ea3e4e2-5346-411e-b4db-d6520dd033cb', '0caf7419-13cb-4e54-9766-3cff293c4fc2', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '112e6337-78a4-450b-af3b-6f5dff46b26e')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('a994aef9-8a23-4009-9e15-1409ea3a7545', '0caf7419-13cb-4e54-9766-3cff293c4fc2', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '3d3216ac-cb76-4838-9b8c-9c6db6065fda')
/
insert into KSEN_LPR_TRANS_ITEM_VR (ELEMENT, ERROR_LEVEL_CD, VR_MESSAGE, LPR_TRANS_ITEM_ID, ID) values ('', 'ERROR', '{"messageKey":"kuali.lpr.trans.message.waitlist.available"}', '0caf7419-13cb-4e54-9766-3cff293c4fc2', '3242dd25-eee5-4176-b5d9-ab61fddc171a')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=3, UPDATEID='B.JASMINK', UPDATETIME=TIMESTAMP '2014-07-15 09:58:09.595', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.waitlistActionAvailable', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.JASMINK', LTI_RESULTING_LPR_ID='' where ID='0caf7419-13cb-4e54-9766-3cff293c4fc2' and VER_NBR=2
/
update KSEN_LPR_TRANS set OBJ_ID='ec54d3b0-86a7-4605-949c-d381b85034a8', VER_NBR=2, UPDATEID='B.JASMINK', UPDATETIME=TIMESTAMP '2014-07-15 09:58:09.62', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.succeeded', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.JASMINK' where ID='8a28a3b0-ad6f-4ab6-b462-7b13d402b8ae' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='18a685da-7aca-4c28-9397-05e01775f9ce'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='2373c7db-f917-4451-91e2-af6f291ac2d3'
/
delete from KSEN_LPR_TRANS_ITEM_VR where ID='f49808ad-beeb-416f-8ca0-655a29915eb6'
/
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('5c3f30f8-8cff-483c-9876-7f0d49f4b116', 0, 'B.JASMINK', TIMESTAMP '2014-07-15 09:58:13.322', 'B.JASMINK', TIMESTAMP '2014-07-15 09:58:13.322', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.register', '', 'B.JASMINK', '52b0757f-1922-48bd-a3dd-7c373558dde7')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('2f43962f-871d-4a9e-8859-3830dae042ca', 0, 'B.JASMINK', TIMESTAMP '2014-07-15 09:58:13.322', 'B.JASMINK', TIMESTAMP '2014-07-15 09:58:13.322', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '52b0757f-1922-48bd-a3dd-7c373558dde7', 'B.JASMINK', '', '70698b79-5183-47fb-b74b-165a0e56d3f5')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('ee6a32be-8808-408a-aeec-2fade529db14', '70698b79-5183-47fb-b74b-165a0e56d3f5', 'kuali.lpr.trans.item.option.oktowaitlist', '1', '3e44af57-4ccf-4e5c-afa9-7b0d0fb7b9e0')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('a2f87e43-b89d-4bbe-a46a-0c5543ce73bb', '70698b79-5183-47fb-b74b-165a0e56d3f5', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'acc5bb6b-5df4-4d04-9c5b-f1ae8b67ae9f')
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='52b0757f-1922-48bd-a3dd-7c373558dde7' where ID='70698b79-5183-47fb-b74b-165a0e56d3f5'
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('70698b79-5183-47fb-b74b-165a0e56d3f5', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('70698b79-5183-47fb-b74b-165a0e56d3f5', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('ecbe4d81-3553-470c-9b8e-6d997de37520', '70698b79-5183-47fb-b74b-165a0e56d3f5', 'kuali.lpr.trans.item.option.oktowaitlist', '1', 'ca759312-6792-4bab-974e-d09ea621a94e')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('26ff44a1-ff88-4cb5-8d39-7a1b26cf490a', '70698b79-5183-47fb-b74b-165a0e56d3f5', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '8ee3e362-67cc-415c-8e7d-ae8c8f9a756d')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=1, UPDATEID='B.JASMINK', UPDATETIME=TIMESTAMP '2014-07-15 09:58:13.322', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.processing', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.JASMINK', LTI_RESULTING_LPR_ID='' where ID='70698b79-5183-47fb-b74b-165a0e56d3f5' and VER_NBR=0
/
update KSEN_LPR_TRANS set OBJ_ID='5c3f30f8-8cff-483c-9876-7f0d49f4b116', VER_NBR=1, UPDATEID='B.JASMINK', UPDATETIME=TIMESTAMP '2012-09-05 00:01:00.0', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.processing', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.JASMINK' where ID='52b0757f-1922-48bd-a3dd-7c373558dde7' and VER_NBR=0
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='acc5bb6b-5df4-4d04-9c5b-f1ae8b67ae9f'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='3e44af57-4ccf-4e5c-afa9-7b0d0fb7b9e0'
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('ce23c024-dff1-436b-b1c0-14adaa108d22', 0, 'B.JASMINK', TIMESTAMP '2014-07-15 09:58:13.485', 'B.JASMINK', TIMESTAMP '2014-07-15 09:58:13.485', 'kuali.atp.2012Fall', '', '3.0', TIMESTAMP '2014-07-15 09:58:13.487', '', 'kuali.resultComponent.grade.letter', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '98350181-500a-406e-bf7c-28a6a0cb35b4', 'B.JASMINK', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.registration.group', '98350181-500a-406e-bf7c-28a6a0cb35b4')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('98350181-500a-406e-bf7c-28a6a0cb35b4', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('98350181-500a-406e-bf7c-28a6a0cb35b4', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('8aaff0ca-3c0a-48c0-b1d6-b8980ed92b59', 0, 'B.JASMINK', TIMESTAMP '2014-07-15 09:58:13.485', 'B.JASMINK', TIMESTAMP '2014-07-15 09:58:13.485', 'kuali.atp.2012Fall', '', '3.0', TIMESTAMP '2014-07-15 09:58:13.487', '', 'kuali.resultComponent.grade.letter', '2ee27af5-2150-42a5-8fd9-52e6305d51c5', '98350181-500a-406e-bf7c-28a6a0cb35b4', 'B.JASMINK', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.course.offering', '734274c2-8fde-4a70-b69f-2921905c89de')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('734274c2-8fde-4a70-b69f-2921905c89de', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('734274c2-8fde-4a70-b69f-2921905c89de', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('e815b346-b295-4e37-b4e9-1ebe49820a62', 0, 'B.JASMINK', TIMESTAMP '2014-07-15 09:58:13.485', 'B.JASMINK', TIMESTAMP '2014-07-15 09:58:13.485', 'kuali.atp.2012Fall', '', '', TIMESTAMP '2014-07-15 09:58:13.487', '', '', '5732c691-ef9c-4b30-bbda-9fa5decaa41f', '98350181-500a-406e-bf7c-28a6a0cb35b4', 'B.JASMINK', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.activity.offering', '434f0754-92e3-46b8-b1bc-17a2d91008c7')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('10e55e3f-0f8d-41f4-b533-28a86ff0bc56', '70698b79-5183-47fb-b74b-165a0e56d3f5', 'kuali.lpr.trans.item.option.oktowaitlist', '1', 'bbeb9b15-9314-4b9b-8cc8-b3e0d9eb3e1f')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('5b49897f-cee7-4db4-b262-f544235179dc', '70698b79-5183-47fb-b74b-165a0e56d3f5', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'c815584f-837f-429f-b420-17a9b0566137')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=2, UPDATEID='B.JASMINK', UPDATETIME=TIMESTAMP '2014-07-15 09:58:13.485', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.waitlist', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.JASMINK', LTI_RESULTING_LPR_ID='' where ID='70698b79-5183-47fb-b74b-165a0e56d3f5' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='ca759312-6792-4bab-974e-d09ea621a94e'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='8ee3e362-67cc-415c-8e7d-ae8c8f9a756d'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('acd8de97-a128-49bd-8948-900ce3f76823', '70698b79-5183-47fb-b74b-165a0e56d3f5', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '19c3248d-b23d-4592-8d5c-acab074833de')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('6c0d5585-1bc6-4674-bf75-f3a912b42061', '70698b79-5183-47fb-b74b-165a0e56d3f5', 'kuali.lpr.trans.item.option.oktowaitlist', '1', '28ff2287-b5e4-4347-afbb-7cf58443e202')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=3, UPDATEID='B.JASMINK', UPDATETIME=TIMESTAMP '2014-07-15 09:58:13.485', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.waitlist', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.JASMINK', LTI_RESULTING_LPR_ID='' where ID='70698b79-5183-47fb-b74b-165a0e56d3f5' and VER_NBR=2
/
update KSEN_LPR_TRANS set OBJ_ID='5c3f30f8-8cff-483c-9876-7f0d49f4b116', VER_NBR=2, UPDATEID='B.JASMINK', UPDATETIME=TIMESTAMP '2014-07-15 09:58:13.526', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.succeeded', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.JASMINK' where ID='52b0757f-1922-48bd-a3dd-7c373558dde7' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='c815584f-837f-429f-b420-17a9b0566137'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='bbeb9b15-9314-4b9b-8cc8-b3e0d9eb3e1f'
/

-- b.jeffreyg waitlisted
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('9390e506-9ec2-44db-b5f1-df083183211d', 0, 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:21.87', 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:21.87', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.registration.cart', '', 'B.JEFFREYG', '68171e69-3c2e-4605-9981-c789b9652b84')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('8e5f3525-71ad-405c-b2be-90d3bdd1f4a8', 0, 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:29.451', 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:29.451', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '68171e69-3c2e-4605-9981-c789b9652b84', 'B.JEFFREYG', '', '56645499-940f-4c92-8ca6-67c74be5f017')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('63702900-ba49-4f3d-8a28-aa374c5d50f6', '56645499-940f-4c92-8ca6-67c74be5f017', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '3b3e9134-dcb9-4c25-a0b6-1e1b51d44029')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('50766a27-8e92-4369-baad-4cbe37af0fff', '56645499-940f-4c92-8ca6-67c74be5f017', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '214162e3-b940-4511-833c-9efd3035827e')
/
update KSEN_LPR_TRANS set OBJ_ID='9390e506-9ec2-44db-b5f1-df083183211d', VER_NBR=1, UPDATEID='B.JEFFREYG', UPDATETIME=TIMESTAMP '2014-07-15 10:00:29.405', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.new', LPR_TRANS_TYPE='kuali.lpr.trans.type.registration.cart', NAME='', REQUESTING_PERS_ID='B.JEFFREYG' where ID='68171e69-3c2e-4605-9981-c789b9652b84' and VER_NBR=0
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='68171e69-3c2e-4605-9981-c789b9652b84' where ID='56645499-940f-4c92-8ca6-67c74be5f017'
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('56645499-940f-4c92-8ca6-67c74be5f017', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('56645499-940f-4c92-8ca6-67c74be5f017', 'kuali.result.value.credit.degree.3.0')
/
update KSEN_LPR_TRANS set OBJ_ID='9390e506-9ec2-44db-b5f1-df083183211d', VER_NBR=2, UPDATEID='B.JEFFREYG', UPDATETIME=TIMESTAMP '2014-07-15 10:00:34.094', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.new', LPR_TRANS_TYPE='kuali.lpr.trans.type.registration.cart', NAME='', REQUESTING_PERS_ID='B.JEFFREYG' where ID='68171e69-3c2e-4605-9981-c789b9652b84' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RVG where LPR_TRANS_ITEM_ID='56645499-940f-4c92-8ca6-67c74be5f017'
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID=null where LPR_TRANS_ID='68171e69-3c2e-4605-9981-c789b9652b84'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='3b3e9134-dcb9-4c25-a0b6-1e1b51d44029'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='214162e3-b940-4511-833c-9efd3035827e'
/
delete from KSEN_LPR_TRANS_ITEM where ID='56645499-940f-4c92-8ca6-67c74be5f017' and VER_NBR=0
/
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('c5392bc5-5d2e-4676-ba8d-0aca0bbd1fd0', 0, 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:34.094', 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:34.094', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.register', '', 'B.JEFFREYG', '62076920-168c-4f25-ab92-d2d76c681780')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('e734c7f8-738b-4fe9-9c82-c91eeebf01a4', 0, 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:29.451', 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:34.094', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '62076920-168c-4f25-ab92-d2d76c681780', 'B.JEFFREYG', '', '56645499-940f-4c92-8ca6-67c74be5f017')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('bd623363-edb9-4366-9d1e-d6566bde9915', '56645499-940f-4c92-8ca6-67c74be5f017', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '9b78a70a-18b9-4175-a53b-0d571a1c4c12')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('c4acb317-f41b-4ac7-b8f7-87df2147105b', '56645499-940f-4c92-8ca6-67c74be5f017', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '34f18303-c647-4d3e-ade4-db1ec0b6b1da')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('56645499-940f-4c92-8ca6-67c74be5f017', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('56645499-940f-4c92-8ca6-67c74be5f017', 'kuali.result.value.credit.degree.3.0')
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='62076920-168c-4f25-ab92-d2d76c681780' where ID='56645499-940f-4c92-8ca6-67c74be5f017'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('d92baa5e-d636-4470-b79b-0a5246b93b91', '56645499-940f-4c92-8ca6-67c74be5f017', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '0f81852c-9328-43f3-b649-846beec2f678')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('34879cc6-34ed-4083-acfd-9f532960c225', '56645499-940f-4c92-8ca6-67c74be5f017', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'aaa4d2f4-4108-4d9c-9d06-1ee5680d4ac8')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=1, UPDATEID='B.JEFFREYG', UPDATETIME=TIMESTAMP '2014-07-15 10:00:34.094', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.processing', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.JEFFREYG', LTI_RESULTING_LPR_ID='' where ID='56645499-940f-4c92-8ca6-67c74be5f017' and VER_NBR=0
/
update KSEN_LPR_TRANS set OBJ_ID='c5392bc5-5d2e-4676-ba8d-0aca0bbd1fd0', VER_NBR=1, UPDATEID='B.JEFFREYG', UPDATETIME=TIMESTAMP '2012-09-05 00:01:00.0', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.processing', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.JEFFREYG' where ID='62076920-168c-4f25-ab92-d2d76c681780' and VER_NBR=0
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='9b78a70a-18b9-4175-a53b-0d571a1c4c12'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='34f18303-c647-4d3e-ade4-db1ec0b6b1da'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('bc2490e1-82d9-4f4c-8f5b-fa03cee02a64', '56645499-940f-4c92-8ca6-67c74be5f017', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '2ab02bcc-7cb1-4ac0-be0a-5f40d95b6b63')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('277593dd-3424-48b7-8018-624eb150aa37', '56645499-940f-4c92-8ca6-67c74be5f017', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'f3375bb4-612b-4f13-9a0f-14ca5daeec48')
/
insert into KSEN_LPR_TRANS_ITEM_VR (ELEMENT, ERROR_LEVEL_CD, VR_MESSAGE, LPR_TRANS_ITEM_ID, ID) values ('', 'ERROR', '{"messageKey":"kuali.lpr.trans.message.waitlist.available"}', '56645499-940f-4c92-8ca6-67c74be5f017', '931b0e17-a757-43e0-af2c-3e5eb617ddea')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=2, UPDATEID='B.JEFFREYG', UPDATETIME=TIMESTAMP '2014-07-15 10:00:34.218', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.waitlistActionAvailable', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.JEFFREYG', LTI_RESULTING_LPR_ID='' where ID='56645499-940f-4c92-8ca6-67c74be5f017' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='0f81852c-9328-43f3-b649-846beec2f678'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='aaa4d2f4-4108-4d9c-9d06-1ee5680d4ac8'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('84f92c93-8659-4de9-97c2-3437ff30a275', '56645499-940f-4c92-8ca6-67c74be5f017', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'b362059f-6950-4ab9-b460-db2b4d941826')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('298e2a81-b71f-484f-86a4-defaff5c0da4', '56645499-940f-4c92-8ca6-67c74be5f017', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '532d0355-2252-43bd-9f13-82596e881031')
/
insert into KSEN_LPR_TRANS_ITEM_VR (ELEMENT, ERROR_LEVEL_CD, VR_MESSAGE, LPR_TRANS_ITEM_ID, ID) values ('', 'ERROR', '{"messageKey":"kuali.lpr.trans.message.waitlist.available"}', '56645499-940f-4c92-8ca6-67c74be5f017', 'fc19ad2f-fc85-46f7-9d85-80b69f76f688')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=3, UPDATEID='B.JEFFREYG', UPDATETIME=TIMESTAMP '2014-07-15 10:00:34.218', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.waitlistActionAvailable', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.JEFFREYG', LTI_RESULTING_LPR_ID='' where ID='56645499-940f-4c92-8ca6-67c74be5f017' and VER_NBR=2
/
update KSEN_LPR_TRANS set OBJ_ID='c5392bc5-5d2e-4676-ba8d-0aca0bbd1fd0', VER_NBR=2, UPDATEID='B.JEFFREYG', UPDATETIME=TIMESTAMP '2014-07-15 10:00:34.241', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.succeeded', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.JEFFREYG' where ID='62076920-168c-4f25-ab92-d2d76c681780' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='f3375bb4-612b-4f13-9a0f-14ca5daeec48'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='2ab02bcc-7cb1-4ac0-be0a-5f40d95b6b63'
/
delete from KSEN_LPR_TRANS_ITEM_VR where ID='931b0e17-a757-43e0-af2c-3e5eb617ddea'
/
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('e1b4c36e-be85-405b-8863-a6f736afd0dd', 0, 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:37.304', 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:37.304', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.register', '', 'B.JEFFREYG', '5d1a15e2-e606-4789-a0ee-be166495dd41')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('66dec9f1-9e11-4e13-8c0f-63a000d9837d', 0, 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:37.304', 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:37.304', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '5d1a15e2-e606-4789-a0ee-be166495dd41', 'B.JEFFREYG', '', 'c9497848-d4ec-4eff-8092-c96521725107')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('84166032-48bb-4505-be45-baef3a0723ea', 'c9497848-d4ec-4eff-8092-c96521725107', 'kuali.lpr.trans.item.option.oktowaitlist', '1', '367c339e-f5f1-424b-89bb-7719998c05bf')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('b3f3f66a-d63a-4eb7-ac37-cbcccb40b0d9', 'c9497848-d4ec-4eff-8092-c96521725107', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '0da1d1e2-e472-42ad-8f07-9250e2d41aff')
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='5d1a15e2-e606-4789-a0ee-be166495dd41' where ID='c9497848-d4ec-4eff-8092-c96521725107'
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('c9497848-d4ec-4eff-8092-c96521725107', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('c9497848-d4ec-4eff-8092-c96521725107', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('b3d20d8e-6348-4112-94c7-a42f6502602b', 'c9497848-d4ec-4eff-8092-c96521725107', 'kuali.lpr.trans.item.option.oktowaitlist', '1', '67bceccb-a56f-4d6a-bd87-bcd78bc9d78d')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('56eac359-e73d-41d0-a4e7-8f2b333b61c2', 'c9497848-d4ec-4eff-8092-c96521725107', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'a9e1c83b-69de-431f-aa0d-ed45826c5928')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=1, UPDATEID='B.JEFFREYG', UPDATETIME=TIMESTAMP '2014-07-15 10:00:37.304', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.processing', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.JEFFREYG', LTI_RESULTING_LPR_ID='' where ID='c9497848-d4ec-4eff-8092-c96521725107' and VER_NBR=0
/
update KSEN_LPR_TRANS set OBJ_ID='e1b4c36e-be85-405b-8863-a6f736afd0dd', VER_NBR=1, UPDATEID='B.JEFFREYG', UPDATETIME=TIMESTAMP '2012-09-05 00:01:00.0', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.processing', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.JEFFREYG' where ID='5d1a15e2-e606-4789-a0ee-be166495dd41' and VER_NBR=0
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='0da1d1e2-e472-42ad-8f07-9250e2d41aff'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='367c339e-f5f1-424b-89bb-7719998c05bf'
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('e433c48a-20a4-4eb1-8aa7-cda93831df90', 0, 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:37.419', 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:37.419', 'kuali.atp.2012Fall', '', '3.0', TIMESTAMP '2014-07-15 10:00:37.421', '', 'kuali.resultComponent.grade.letter', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '2e87cd9d-3384-4fa5-acbf-fe712f25ebc3', 'B.JEFFREYG', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.registration.group', '2e87cd9d-3384-4fa5-acbf-fe712f25ebc3')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('2e87cd9d-3384-4fa5-acbf-fe712f25ebc3', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('2e87cd9d-3384-4fa5-acbf-fe712f25ebc3', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('b99fad21-7d78-42f9-b713-078c9d866a01', 0, 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:37.419', 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:37.419', 'kuali.atp.2012Fall', '', '3.0', TIMESTAMP '2014-07-15 10:00:37.421', '', 'kuali.resultComponent.grade.letter', '2ee27af5-2150-42a5-8fd9-52e6305d51c5', '2e87cd9d-3384-4fa5-acbf-fe712f25ebc3', 'B.JEFFREYG', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.course.offering', '31fa682f-10d3-4327-bad9-a5cbdb555341')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('31fa682f-10d3-4327-bad9-a5cbdb555341', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('31fa682f-10d3-4327-bad9-a5cbdb555341', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('af7c9c85-ad52-42a1-b2cb-87276cb59ec1', 0, 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:37.419', 'B.JEFFREYG', TIMESTAMP '2014-07-15 10:00:37.419', 'kuali.atp.2012Fall', '', '', TIMESTAMP '2014-07-15 10:00:37.421', '', '', '5732c691-ef9c-4b30-bbda-9fa5decaa41f', '2e87cd9d-3384-4fa5-acbf-fe712f25ebc3', 'B.JEFFREYG', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.activity.offering', 'df335493-8ec7-4cc1-8fb2-03e57891a001')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('bc558dfe-2734-4c1d-a428-f5f7fa63a23e', 'c9497848-d4ec-4eff-8092-c96521725107', 'kuali.lpr.trans.item.option.oktowaitlist', '1', '176d86b5-feb4-44c6-85cd-8d5518d17e17')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('66069988-ab00-4f3d-bdbe-1fbf217e3809', 'c9497848-d4ec-4eff-8092-c96521725107', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'b84d5db7-c9ee-4184-bbf2-9a88e18a65cf')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=2, UPDATEID='B.JEFFREYG', UPDATETIME=TIMESTAMP '2014-07-15 10:00:37.419', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.waitlist', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.JEFFREYG', LTI_RESULTING_LPR_ID='' where ID='c9497848-d4ec-4eff-8092-c96521725107' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='67bceccb-a56f-4d6a-bd87-bcd78bc9d78d'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='a9e1c83b-69de-431f-aa0d-ed45826c5928'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('b9954629-ef87-4323-8570-8e5181c84346', 'c9497848-d4ec-4eff-8092-c96521725107', 'kuali.lpr.trans.item.option.oktowaitlist', '1', '471d6b48-27f4-493a-b60b-c5823d4e1d62')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('e0a673ce-0ed5-4ade-9da0-09e1b68a876b', 'c9497848-d4ec-4eff-8092-c96521725107', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '960b1366-fbb6-462e-a9b5-abbbc724979e')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=3, UPDATEID='B.JEFFREYG', UPDATETIME=TIMESTAMP '2014-07-15 10:00:37.419', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.waitlist', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.JEFFREYG', LTI_RESULTING_LPR_ID='' where ID='c9497848-d4ec-4eff-8092-c96521725107' and VER_NBR=2
/
update KSEN_LPR_TRANS set OBJ_ID='e1b4c36e-be85-405b-8863-a6f736afd0dd', VER_NBR=2, UPDATEID='B.JEFFREYG', UPDATETIME=TIMESTAMP '2014-07-15 10:00:37.461', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.succeeded', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.JEFFREYG' where ID='5d1a15e2-e606-4789-a0ee-be166495dd41' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='b84d5db7-c9ee-4184-bbf2-9a88e18a65cf'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='176d86b5-feb4-44c6-85cd-8d5518d17e17'
/

-- b.hugos waitlisted
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('31f09c39-f29f-40fe-875c-aca361519ea7', 0, 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:33.744', 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:33.744', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.registration.cart', '', 'B.HUGOS', '755d0143-31c9-46b9-bc92-fb92f902cef4')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('c78c6812-7bdf-44ca-92fe-2dba3a53bbc0', 0, 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:41.074', 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:41.074', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '755d0143-31c9-46b9-bc92-fb92f902cef4', 'B.HUGOS', '', 'e19e2568-e888-4009-9afa-2c72baea8dfc')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('36c0f188-0409-47c7-8dff-d6bb568eedbd', 'e19e2568-e888-4009-9afa-2c72baea8dfc', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '5b0d0d9c-360a-48ef-9c2b-73c104cc671d')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('28246879-44a8-4843-a89f-2d7e49313315', 'e19e2568-e888-4009-9afa-2c72baea8dfc', 'kuali.lpr.trans.item.option.oktowaitlist', '0', 'f7cb4385-7a74-496e-a7ae-e6e85bfafbbd')
/
update KSEN_LPR_TRANS set OBJ_ID='31f09c39-f29f-40fe-875c-aca361519ea7', VER_NBR=1, UPDATEID='B.HUGOS', UPDATETIME=TIMESTAMP '2014-07-15 10:02:41.06', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.new', LPR_TRANS_TYPE='kuali.lpr.trans.type.registration.cart', NAME='', REQUESTING_PERS_ID='B.HUGOS' where ID='755d0143-31c9-46b9-bc92-fb92f902cef4' and VER_NBR=0
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='755d0143-31c9-46b9-bc92-fb92f902cef4' where ID='e19e2568-e888-4009-9afa-2c72baea8dfc'
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('e19e2568-e888-4009-9afa-2c72baea8dfc', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('e19e2568-e888-4009-9afa-2c72baea8dfc', 'kuali.result.value.credit.degree.3.0')
/
update KSEN_LPR_TRANS set OBJ_ID='31f09c39-f29f-40fe-875c-aca361519ea7', VER_NBR=2, UPDATEID='B.HUGOS', UPDATETIME=TIMESTAMP '2014-07-15 10:02:43.658', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.new', LPR_TRANS_TYPE='kuali.lpr.trans.type.registration.cart', NAME='', REQUESTING_PERS_ID='B.HUGOS' where ID='755d0143-31c9-46b9-bc92-fb92f902cef4' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RVG where LPR_TRANS_ITEM_ID='e19e2568-e888-4009-9afa-2c72baea8dfc'
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID=null where LPR_TRANS_ID='755d0143-31c9-46b9-bc92-fb92f902cef4'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='5b0d0d9c-360a-48ef-9c2b-73c104cc671d'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='f7cb4385-7a74-496e-a7ae-e6e85bfafbbd'
/
delete from KSEN_LPR_TRANS_ITEM where ID='e19e2568-e888-4009-9afa-2c72baea8dfc' and VER_NBR=0
/
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('724bfb28-937d-4504-8a01-caa8037e570b', 0, 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:43.658', 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:43.658', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.register', '', 'B.HUGOS', 'fbd6c30a-1bcb-4645-b109-0520747ffbc1')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('fc55abac-979b-49b7-92c0-a99d5fe38b97', 0, 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:41.074', 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:43.658', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', 'fbd6c30a-1bcb-4645-b109-0520747ffbc1', 'B.HUGOS', '', 'e19e2568-e888-4009-9afa-2c72baea8dfc')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('69d9216a-a6ec-4c55-8284-f8bff6175bad', 'e19e2568-e888-4009-9afa-2c72baea8dfc', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '1ae05af5-2f99-466f-8423-0c59989cf75d')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('e38f3a80-1153-4634-ba81-b707ba951edb', 'e19e2568-e888-4009-9afa-2c72baea8dfc', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'f9483e45-9dd9-447d-88a3-78e5d1f1c821')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('e19e2568-e888-4009-9afa-2c72baea8dfc', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('e19e2568-e888-4009-9afa-2c72baea8dfc', 'kuali.result.value.credit.degree.3.0')
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='fbd6c30a-1bcb-4645-b109-0520747ffbc1' where ID='e19e2568-e888-4009-9afa-2c72baea8dfc'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('a5a31825-57e3-4e03-8fc5-6ac3fc64ac8d', 'e19e2568-e888-4009-9afa-2c72baea8dfc', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '4278050a-926d-4e77-a016-f0c31528db77')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('ad49ddd6-8f35-4a29-ab30-83d966f15224', 'e19e2568-e888-4009-9afa-2c72baea8dfc', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '0324bfaa-7e10-4e6d-b14c-735923761ca5')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=1, UPDATEID='B.HUGOS', UPDATETIME=TIMESTAMP '2014-07-15 10:02:43.658', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.processing', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.HUGOS', LTI_RESULTING_LPR_ID='' where ID='e19e2568-e888-4009-9afa-2c72baea8dfc' and VER_NBR=0
/
update KSEN_LPR_TRANS set OBJ_ID='724bfb28-937d-4504-8a01-caa8037e570b', VER_NBR=1, UPDATEID='B.HUGOS', UPDATETIME=TIMESTAMP '2012-09-05 00:01:00.0', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.processing', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.HUGOS' where ID='fbd6c30a-1bcb-4645-b109-0520747ffbc1' and VER_NBR=0
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='1ae05af5-2f99-466f-8423-0c59989cf75d'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='f9483e45-9dd9-447d-88a3-78e5d1f1c821'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('8d362cec-efc9-4bf6-a850-a5a7a06288a1', 'e19e2568-e888-4009-9afa-2c72baea8dfc', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '5d5aa2ed-540c-4515-b51e-b24082817391')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('d6730b59-b719-4a0c-aed8-24153ddc5eb8', 'e19e2568-e888-4009-9afa-2c72baea8dfc', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '73fc1f57-ff66-4360-add1-3b2e694d8a67')
/
insert into KSEN_LPR_TRANS_ITEM_VR (ELEMENT, ERROR_LEVEL_CD, VR_MESSAGE, LPR_TRANS_ITEM_ID, ID) values ('', 'ERROR', '{"messageKey":"kuali.lpr.trans.message.waitlist.available"}', 'e19e2568-e888-4009-9afa-2c72baea8dfc', 'a8f15b20-82b5-4bce-8bdd-ab6e70f4a22a')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=2, UPDATEID='B.HUGOS', UPDATETIME=TIMESTAMP '2014-07-15 10:02:43.778', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.waitlistActionAvailable', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.HUGOS', LTI_RESULTING_LPR_ID='' where ID='e19e2568-e888-4009-9afa-2c72baea8dfc' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='0324bfaa-7e10-4e6d-b14c-735923761ca5'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='4278050a-926d-4e77-a016-f0c31528db77'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('1debb21c-2bb4-4787-9f9c-fcb66e99eba8', 'e19e2568-e888-4009-9afa-2c72baea8dfc', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '4eb55372-3d3f-4af2-b5ac-8e2fff111f89')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('b7b5524c-e12a-4a2a-abd6-34716d28afc4', 'e19e2568-e888-4009-9afa-2c72baea8dfc', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'b0b1c3e3-4e01-4427-8e4e-2faf19e7b2cd')
/
insert into KSEN_LPR_TRANS_ITEM_VR (ELEMENT, ERROR_LEVEL_CD, VR_MESSAGE, LPR_TRANS_ITEM_ID, ID) values ('', 'ERROR', '{"messageKey":"kuali.lpr.trans.message.waitlist.available"}', 'e19e2568-e888-4009-9afa-2c72baea8dfc', '8447dd53-a092-480e-9b8c-a46bb689791a')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=3, UPDATEID='B.HUGOS', UPDATETIME=TIMESTAMP '2014-07-15 10:02:43.778', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.waitlistActionAvailable', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.HUGOS', LTI_RESULTING_LPR_ID='' where ID='e19e2568-e888-4009-9afa-2c72baea8dfc' and VER_NBR=2
/
update KSEN_LPR_TRANS set OBJ_ID='724bfb28-937d-4504-8a01-caa8037e570b', VER_NBR=2, UPDATEID='B.HUGOS', UPDATETIME=TIMESTAMP '2014-07-15 10:02:43.801', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.succeeded', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.HUGOS' where ID='fbd6c30a-1bcb-4645-b109-0520747ffbc1' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='5d5aa2ed-540c-4515-b51e-b24082817391'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='73fc1f57-ff66-4360-add1-3b2e694d8a67'
/
delete from KSEN_LPR_TRANS_ITEM_VR where ID='a8f15b20-82b5-4bce-8bdd-ab6e70f4a22a'
/
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('301c0c67-d664-46f8-8e43-5dafb01dfe94', 0, 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:46.737', 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:46.737', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.register', '', 'B.HUGOS', '4530495c-e122-48ad-a072-a1b75ee44b56')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('c2db3eab-febf-4008-bd06-ebd86f700ae9', 0, 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:46.737', 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:46.737', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '4530495c-e122-48ad-a072-a1b75ee44b56', 'B.HUGOS', '', 'c72d0b21-60f6-46aa-8a79-5cf69157da66')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('2ab8252e-c6ca-4799-89df-9ae8ab42ea21', 'c72d0b21-60f6-46aa-8a79-5cf69157da66', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '5c0c0f37-9f97-407b-b1ce-3e958619f413')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('0a771c89-ab68-4b7f-9089-6b1d42782047', 'c72d0b21-60f6-46aa-8a79-5cf69157da66', 'kuali.lpr.trans.item.option.oktowaitlist', '1', '8532d965-a143-4260-a500-14064464bd31')
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='4530495c-e122-48ad-a072-a1b75ee44b56' where ID='c72d0b21-60f6-46aa-8a79-5cf69157da66'
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('c72d0b21-60f6-46aa-8a79-5cf69157da66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('c72d0b21-60f6-46aa-8a79-5cf69157da66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('14aeab52-9f52-4a26-8129-3a206583eae3', 'c72d0b21-60f6-46aa-8a79-5cf69157da66', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'e8313a1c-6308-46a0-aba6-7cb7ed3ccd33')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('78e7d9c7-70fb-4e45-b0c0-f6ed6cebe280', 'c72d0b21-60f6-46aa-8a79-5cf69157da66', 'kuali.lpr.trans.item.option.oktowaitlist', '1', 'c428f976-fd89-4b76-b325-ce83759d617a')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=1, UPDATEID='B.HUGOS', UPDATETIME=TIMESTAMP '2014-07-15 10:02:46.737', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.processing', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.HUGOS', LTI_RESULTING_LPR_ID='' where ID='c72d0b21-60f6-46aa-8a79-5cf69157da66' and VER_NBR=0
/
update KSEN_LPR_TRANS set OBJ_ID='301c0c67-d664-46f8-8e43-5dafb01dfe94', VER_NBR=1, UPDATEID='B.HUGOS', UPDATETIME=TIMESTAMP '2012-09-05 00:01:00.0', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.processing', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.HUGOS' where ID='4530495c-e122-48ad-a072-a1b75ee44b56' and VER_NBR=0
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='5c0c0f37-9f97-407b-b1ce-3e958619f413'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='8532d965-a143-4260-a500-14064464bd31'
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('10ba850c-6ff3-4dcb-839b-cad876b15154', 0, 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:46.87', 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:46.87', 'kuali.atp.2012Fall', '', '3.0', TIMESTAMP '2014-07-15 10:02:46.872', '', 'kuali.resultComponent.grade.letter', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '4573fbfa-88e8-47b7-848b-3608263787bc', 'B.HUGOS', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.registration.group', '4573fbfa-88e8-47b7-848b-3608263787bc')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('4573fbfa-88e8-47b7-848b-3608263787bc', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('4573fbfa-88e8-47b7-848b-3608263787bc', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('c7dd603d-f0f5-451f-8357-13570816b8c0', 0, 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:46.87', 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:46.87', 'kuali.atp.2012Fall', '', '3.0', TIMESTAMP '2014-07-15 10:02:46.872', '', 'kuali.resultComponent.grade.letter', '2ee27af5-2150-42a5-8fd9-52e6305d51c5', '4573fbfa-88e8-47b7-848b-3608263787bc', 'B.HUGOS', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.course.offering', '17700a98-e5f9-46f2-8c77-7f840b80ee71')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('17700a98-e5f9-46f2-8c77-7f840b80ee71', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('17700a98-e5f9-46f2-8c77-7f840b80ee71', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('a48625cf-ca3c-41d1-9e87-aa857d2db117', 0, 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:46.87', 'B.HUGOS', TIMESTAMP '2014-07-15 10:02:46.87', 'kuali.atp.2012Fall', '', '', TIMESTAMP '2014-07-15 10:02:46.872', '', '', '5732c691-ef9c-4b30-bbda-9fa5decaa41f', '4573fbfa-88e8-47b7-848b-3608263787bc', 'B.HUGOS', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.activity.offering', '243a18b1-443e-4b67-8efb-31ebbc3298d9')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('e991caac-0610-4790-9eb3-03093229d258', 'c72d0b21-60f6-46aa-8a79-5cf69157da66', 'kuali.lpr.trans.item.option.oktowaitlist', '1', '9f5a45d5-8a87-4f50-ba33-4ddacc6db84d')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('476bafbc-d0b2-420a-a45e-28d8e5969bb4', 'c72d0b21-60f6-46aa-8a79-5cf69157da66', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '25adccf0-e738-4e72-8fe3-9c692b394cb9')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=2, UPDATEID='B.HUGOS', UPDATETIME=TIMESTAMP '2014-07-15 10:02:46.87', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.waitlist', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.HUGOS', LTI_RESULTING_LPR_ID='' where ID='c72d0b21-60f6-46aa-8a79-5cf69157da66' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='e8313a1c-6308-46a0-aba6-7cb7ed3ccd33'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='c428f976-fd89-4b76-b325-ce83759d617a'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('e9b6b8df-6ba4-43f6-a11a-1e4c8b88bbe6', 'c72d0b21-60f6-46aa-8a79-5cf69157da66', 'kuali.lpr.trans.item.option.oktowaitlist', '1', 'cf9b6062-b4ec-45f0-9d30-1728e021ec27')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('eada3e6b-52ac-4882-8048-82dd9505c231', 'c72d0b21-60f6-46aa-8a79-5cf69157da66', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'dcedd74f-eb86-4ce0-89f4-563f6fd38c23')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=3, UPDATEID='B.HUGOS', UPDATETIME=TIMESTAMP '2014-07-15 10:02:46.87', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.waitlist', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.HUGOS', LTI_RESULTING_LPR_ID='' where ID='c72d0b21-60f6-46aa-8a79-5cf69157da66' and VER_NBR=2
/
update KSEN_LPR_TRANS set OBJ_ID='301c0c67-d664-46f8-8e43-5dafb01dfe94', VER_NBR=2, UPDATEID='B.HUGOS', UPDATETIME=TIMESTAMP '2014-07-15 10:02:46.913', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.succeeded', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.HUGOS' where ID='4530495c-e122-48ad-a072-a1b75ee44b56' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='9f5a45d5-8a87-4f50-ba33-4ddacc6db84d'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='25adccf0-e738-4e72-8fe3-9c692b394cb9'
/

-- b.philipb waitlisted
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('7ead60db-b6bc-4557-9c62-66592fc09d13', 0, 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:35.451', 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:35.451', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.registration.cart', '', 'B.PHILIPB', 'e19970af-dd46-4fd8-8c95-c9036c519851')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('8a82446c-49d8-4f11-b64e-7eaef3f240e9', 0, 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:42.407', 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:42.407', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', 'e19970af-dd46-4fd8-8c95-c9036c519851', 'B.PHILIPB', '', '56d6d081-2bc1-43bb-a60f-63cf944f0fc3')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('31ef4e82-705c-4459-994a-02652e3e5f18', '56d6d081-2bc1-43bb-a60f-63cf944f0fc3', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '7e9175b5-a4ef-4166-88c8-6791b34df0b5')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('e36b89ff-c5e9-4197-bf81-96ca0370e247', '56d6d081-2bc1-43bb-a60f-63cf944f0fc3', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '97124eff-43d3-4be0-840b-b8bc5053ffab')
/
update KSEN_LPR_TRANS set OBJ_ID='7ead60db-b6bc-4557-9c62-66592fc09d13', VER_NBR=1, UPDATEID='B.PHILIPB', UPDATETIME=TIMESTAMP '2014-07-15 10:04:42.392', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.new', LPR_TRANS_TYPE='kuali.lpr.trans.type.registration.cart', NAME='', REQUESTING_PERS_ID='B.PHILIPB' where ID='e19970af-dd46-4fd8-8c95-c9036c519851' and VER_NBR=0
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='e19970af-dd46-4fd8-8c95-c9036c519851' where ID='56d6d081-2bc1-43bb-a60f-63cf944f0fc3'
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('56d6d081-2bc1-43bb-a60f-63cf944f0fc3', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('56d6d081-2bc1-43bb-a60f-63cf944f0fc3', 'kuali.result.value.credit.degree.3.0')
/
update KSEN_LPR_TRANS set OBJ_ID='7ead60db-b6bc-4557-9c62-66592fc09d13', VER_NBR=2, UPDATEID='B.PHILIPB', UPDATETIME=TIMESTAMP '2014-07-15 10:04:45.945', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.new', LPR_TRANS_TYPE='kuali.lpr.trans.type.registration.cart', NAME='', REQUESTING_PERS_ID='B.PHILIPB' where ID='e19970af-dd46-4fd8-8c95-c9036c519851' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RVG where LPR_TRANS_ITEM_ID='56d6d081-2bc1-43bb-a60f-63cf944f0fc3'
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID=null where LPR_TRANS_ID='e19970af-dd46-4fd8-8c95-c9036c519851'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='7e9175b5-a4ef-4166-88c8-6791b34df0b5'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='97124eff-43d3-4be0-840b-b8bc5053ffab'
/
delete from KSEN_LPR_TRANS_ITEM where ID='56d6d081-2bc1-43bb-a60f-63cf944f0fc3' and VER_NBR=0
/
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('95b6b57c-a111-4ff8-9296-a4617e6855b9', 0, 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:45.945', 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:45.945', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.register', '', 'B.PHILIPB', '6d2c8f41-13cd-45ba-bcca-c517c8d45c7d')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('81567bcd-c597-4ff3-bd2d-5e7aa71127d4', 0, 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:42.407', 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:45.945', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '6d2c8f41-13cd-45ba-bcca-c517c8d45c7d', 'B.PHILIPB', '', '56d6d081-2bc1-43bb-a60f-63cf944f0fc3')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('95362378-63b1-4259-a832-1a24754574e9', '56d6d081-2bc1-43bb-a60f-63cf944f0fc3', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '15e619f9-a2d8-4b87-8f89-839724ca9be5')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('cd4a098a-7e8d-4923-872d-b8aea20a1cc4', '56d6d081-2bc1-43bb-a60f-63cf944f0fc3', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '83758df8-4f6e-4e76-81a9-209c46ac2102')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('56d6d081-2bc1-43bb-a60f-63cf944f0fc3', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('56d6d081-2bc1-43bb-a60f-63cf944f0fc3', 'kuali.result.value.credit.degree.3.0')
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='6d2c8f41-13cd-45ba-bcca-c517c8d45c7d' where ID='56d6d081-2bc1-43bb-a60f-63cf944f0fc3'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('2f9e2768-0b57-479f-ab11-626ad3c6ba17', '56d6d081-2bc1-43bb-a60f-63cf944f0fc3', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '392c35ad-bfeb-4023-9ae1-59fd7ee54b26')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('8c2d1262-7a79-42b0-8543-e763266124d5', '56d6d081-2bc1-43bb-a60f-63cf944f0fc3', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '6cb4a722-c5b3-4ec9-81a2-d0e2d0cb3879')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=1, UPDATEID='B.PHILIPB', UPDATETIME=TIMESTAMP '2014-07-15 10:04:45.945', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.processing', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.PHILIPB', LTI_RESULTING_LPR_ID='' where ID='56d6d081-2bc1-43bb-a60f-63cf944f0fc3' and VER_NBR=0
/
update KSEN_LPR_TRANS set OBJ_ID='95b6b57c-a111-4ff8-9296-a4617e6855b9', VER_NBR=1, UPDATEID='B.PHILIPB', UPDATETIME=TIMESTAMP '2012-09-05 00:01:00.0', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.processing', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.PHILIPB' where ID='6d2c8f41-13cd-45ba-bcca-c517c8d45c7d' and VER_NBR=0
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='83758df8-4f6e-4e76-81a9-209c46ac2102'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='15e619f9-a2d8-4b87-8f89-839724ca9be5'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('3597974e-a666-4fd2-8ddf-b8729c4dcf0c', '56d6d081-2bc1-43bb-a60f-63cf944f0fc3', 'kuali.lpr.trans.item.option.oktowaitlist', '0', 'db2ef757-08c4-4ec4-82fe-6853ff754153')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('af317ed0-17f1-4433-b67e-248fdffd03bb', '56d6d081-2bc1-43bb-a60f-63cf944f0fc3', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '69a264ca-c06d-4078-a37e-4ebd7097ec2f')
/
insert into KSEN_LPR_TRANS_ITEM_VR (ELEMENT, ERROR_LEVEL_CD, VR_MESSAGE, LPR_TRANS_ITEM_ID, ID) values ('', 'ERROR', '{"messageKey":"kuali.lpr.trans.message.waitlist.available"}', '56d6d081-2bc1-43bb-a60f-63cf944f0fc3', 'ec03fe91-5444-41e1-a67e-d85ac8dbce0a')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=2, UPDATEID='B.PHILIPB', UPDATETIME=TIMESTAMP '2014-07-15 10:04:46.151', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.waitlistActionAvailable', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.PHILIPB', LTI_RESULTING_LPR_ID='' where ID='56d6d081-2bc1-43bb-a60f-63cf944f0fc3' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='6cb4a722-c5b3-4ec9-81a2-d0e2d0cb3879'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='392c35ad-bfeb-4023-9ae1-59fd7ee54b26'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('90f88b79-88ba-42ac-9622-4017387cde82', '56d6d081-2bc1-43bb-a60f-63cf944f0fc3', 'kuali.lpr.trans.item.option.oktowaitlist', '0', '6bb56564-39d0-4116-8c74-c2ed623b4f2d')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('f95d7045-7e2f-462d-adb9-2432c4b05ea6', '56d6d081-2bc1-43bb-a60f-63cf944f0fc3', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '1f69a95f-0fe4-490c-b5c2-7292afce770b')
/
insert into KSEN_LPR_TRANS_ITEM_VR (ELEMENT, ERROR_LEVEL_CD, VR_MESSAGE, LPR_TRANS_ITEM_ID, ID) values ('', 'ERROR', '{"messageKey":"kuali.lpr.trans.message.waitlist.available"}', '56d6d081-2bc1-43bb-a60f-63cf944f0fc3', '156685b5-227c-4c44-9f0f-e7847fca7b2b')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=3, UPDATEID='B.PHILIPB', UPDATETIME=TIMESTAMP '2014-07-15 10:04:46.151', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.waitlistActionAvailable', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.PHILIPB', LTI_RESULTING_LPR_ID='' where ID='56d6d081-2bc1-43bb-a60f-63cf944f0fc3' and VER_NBR=2
/
update KSEN_LPR_TRANS set OBJ_ID='95b6b57c-a111-4ff8-9296-a4617e6855b9', VER_NBR=2, UPDATEID='B.PHILIPB', UPDATETIME=TIMESTAMP '2014-07-15 10:04:46.175', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.succeeded', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.PHILIPB' where ID='6d2c8f41-13cd-45ba-bcca-c517c8d45c7d' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='db2ef757-08c4-4ec4-82fe-6853ff754153'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='69a264ca-c06d-4078-a37e-4ebd7097ec2f'
/
delete from KSEN_LPR_TRANS_ITEM_VR where ID='ec03fe91-5444-41e1-a67e-d85ac8dbce0a'
/
insert into KSEN_LPR_TRANS (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, DESCR_FORMATTED, DESCR_PLAIN, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, REQUESTING_PERS_ID, ID) values ('995465c2-8fe6-4cd3-b64e-8d6d1a5fd894', 0, 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:48.386', 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:48.386', 'kuali.atp.2012Fall', '', '', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.register', '', 'B.PHILIPB', 'a22e816f-5c75-4975-9f3d-4ace86cea49a')
/
insert into KSEN_LPR_TRANS_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, NAME, NEW_LUI_ID, LPR_TRANS_ID, PERS_ID, LTI_RESULTING_LPR_ID, ID) values ('a40ba51b-3deb-490d-b68e-24bb884fc704', 0, 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:48.386', 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:48.386', '', '', '', '', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', '', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', 'a22e816f-5c75-4975-9f3d-4ace86cea49a', 'B.PHILIPB', '', '283a4419-85f0-43df-ae6f-e91af36c24d9')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('b0381d2c-8184-41c3-9552-cba14675bed0', '283a4419-85f0-43df-ae6f-e91af36c24d9', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '991df5a4-c74e-495f-95cc-755a9a25e076')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('92840d64-1849-4973-9a30-c2d4fdf71f83', '283a4419-85f0-43df-ae6f-e91af36c24d9', 'kuali.lpr.trans.item.option.oktowaitlist', '1', '3547383e-af0c-40f9-97f4-f42ec4c562e5')
/
update KSEN_LPR_TRANS_ITEM set LPR_TRANS_ID='a22e816f-5c75-4975-9f3d-4ace86cea49a' where ID='283a4419-85f0-43df-ae6f-e91af36c24d9'
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('283a4419-85f0-43df-ae6f-e91af36c24d9', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('283a4419-85f0-43df-ae6f-e91af36c24d9', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('876f80fa-b6c4-4079-91f2-11fde7e35714', '283a4419-85f0-43df-ae6f-e91af36c24d9', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '86da5564-3a5e-44a5-a3c9-45f0bf174a48')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('7d624272-1c61-4c55-9b1c-32434bfa5265', '283a4419-85f0-43df-ae6f-e91af36c24d9', 'kuali.lpr.trans.item.option.oktowaitlist', '1', '88de1576-f959-4464-8547-4d082c0f16da')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=1, UPDATEID='B.PHILIPB', UPDATETIME=TIMESTAMP '2014-07-15 10:04:48.386', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.processing', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.PHILIPB', LTI_RESULTING_LPR_ID='' where ID='283a4419-85f0-43df-ae6f-e91af36c24d9' and VER_NBR=0
/
update KSEN_LPR_TRANS set OBJ_ID='995465c2-8fe6-4cd3-b64e-8d6d1a5fd894', VER_NBR=1, UPDATEID='B.PHILIPB', UPDATETIME=TIMESTAMP '2012-09-05 00:01:00.0', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.processing', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.PHILIPB' where ID='a22e816f-5c75-4975-9f3d-4ace86cea49a' and VER_NBR=0
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='3547383e-af0c-40f9-97f4-f42ec4c562e5'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='991df5a4-c74e-495f-95cc-755a9a25e076'
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('11b033c8-b16c-4c97-89a0-ffe1788e6336', 0, 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:48.495', 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:48.495', 'kuali.atp.2012Fall', '', '3.0', TIMESTAMP '2014-07-15 10:04:48.497', '', 'kuali.resultComponent.grade.letter', 'ba0ce58c-c546-4039-a85a-ae75b1a7575b', '9f42ab3a-5184-48c3-a61f-c8391acbbf87', 'B.PHILIPB', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.registration.group', '9f42ab3a-5184-48c3-a61f-c8391acbbf87')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('9f42ab3a-5184-48c3-a61f-c8391acbbf87', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('9f42ab3a-5184-48c3-a61f-c8391acbbf87', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('e1b228b5-1f50-41fd-b54c-832206395de0', 0, 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:48.495', 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:48.495', 'kuali.atp.2012Fall', '', '3.0', TIMESTAMP '2014-07-15 10:04:48.497', '', 'kuali.resultComponent.grade.letter', '2ee27af5-2150-42a5-8fd9-52e6305d51c5', '9f42ab3a-5184-48c3-a61f-c8391acbbf87', 'B.PHILIPB', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.course.offering', 'bc22217c-08b1-4147-bd53-6d1736d74b8f')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('bc22217c-08b1-4147-bd53-6d1736d74b8f', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('bc22217c-08b1-4147-bd53-6d1736d74b8f', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('64250e14-c727-4d41-b856-9bc94c46ff66', 0, 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:48.495', 'B.PHILIPB', TIMESTAMP '2014-07-15 10:04:48.495', 'kuali.atp.2012Fall', '', '', TIMESTAMP '2014-07-15 10:04:48.497', '', '', '5732c691-ef9c-4b30-bbda-9fa5decaa41f', '9f42ab3a-5184-48c3-a61f-c8391acbbf87', 'B.PHILIPB', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.activity.offering', 'ccd562af-2eae-4d60-ae17-aab9587f6466')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('dd22a11e-302d-40cb-b74a-e7505161b989', '283a4419-85f0-43df-ae6f-e91af36c24d9', 'kuali.lpr.trans.item.option.oktowaitlist', '1', 'c27118b1-ae1c-43a9-adad-638368d1d819')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('e6b43ccc-fff9-447b-ad70-0cd55377f6a3', '283a4419-85f0-43df-ae6f-e91af36c24d9', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', '8b453df3-5002-4fad-82d1-faf5cd86dd0d')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=2, UPDATEID='B.PHILIPB', UPDATETIME=TIMESTAMP '2014-07-15 10:04:48.495', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.waitlist', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.PHILIPB', LTI_RESULTING_LPR_ID='' where ID='283a4419-85f0-43df-ae6f-e91af36c24d9' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='86da5564-3a5e-44a5-a3c9-45f0bf174a48'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='88de1576-f959-4464-8547-4d082c0f16da'
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('a8569ea3-4646-4780-8dbb-0eaf37cd36fe', '283a4419-85f0-43df-ae6f-e91af36c24d9', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null', 'c5c5e320-ef9f-4646-8b3d-98115ec7eb42')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (OBJ_ID, LPR_TRANS_ITEM_ID, OPTION_KEY, OPTION_VALUE, ID) values ('ad73657f-b7b0-48cc-a43e-381abfab399c', '283a4419-85f0-43df-ae6f-e91af36c24d9', 'kuali.lpr.trans.item.option.oktowaitlist', '1', 'bd45a32e-e30c-48b4-ae2d-4759e3788146')
/
update KSEN_LPR_TRANS_ITEM set OBJ_ID='', VER_NBR=3, UPDATEID='B.PHILIPB', UPDATETIME=TIMESTAMP '2014-07-15 10:04:48.495', DESCR_FORMATTED='', DESCR_PLAIN='', EXISTING_LUI_ID='', GROUP_ID='', LPR_TRANS_ITEM_STATE='kuali.lpr.trans.item.state.waitlist', LPR_TRANS_ITEM_TYPE='kuali.course.registration.request.item.type.add', NAME='', NEW_LUI_ID='ba0ce58c-c546-4039-a85a-ae75b1a7575b', PERS_ID='B.PHILIPB', LTI_RESULTING_LPR_ID='' where ID='283a4419-85f0-43df-ae6f-e91af36c24d9' and VER_NBR=2
/
update KSEN_LPR_TRANS set OBJ_ID='995465c2-8fe6-4cd3-b64e-8d6d1a5fd894', VER_NBR=2, UPDATEID='B.PHILIPB', UPDATETIME=TIMESTAMP '2014-07-15 10:04:48.536', ATP_ID='kuali.atp.2012Fall', DESCR_FORMATTED='', DESCR_PLAIN='', LPR_TRANS_STATE='kuali.lpr.trans.state.succeeded', LPR_TRANS_TYPE='kuali.lpr.trans.type.register', NAME='', REQUESTING_PERS_ID='B.PHILIPB' where ID='a22e816f-5c75-4975-9f3d-4ace86cea49a' and VER_NBR=1
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='8b453df3-5002-4fad-82d1-faf5cd86dd0d'
/
delete from KSEN_LPR_TRANS_ITEM_RQST_OPT where ID='c27118b1-ae1c-43a9-adad-638368d1d819'
/
