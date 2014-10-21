--KSENROLL-14998 added a canceled course to the reg cart for testing
insert into KSEN_LPR_TRANS (ATP_ID, CREATEID, CREATETIME, DESCR_FORMATTED, DESCR_PLAIN, ID, LPR_TRANS_STATE, LPR_TRANS_TYPE, NAME, OBJ_ID, REQUESTING_PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', 'R.AARONE', TIMESTAMP '2014-09-25 12:04:25', null, null, '2f4e2d49-f1d5-46a7-bfd0-5bc3f75b5803', 'kuali.lpr.trans.state.new', 'kuali.lpr.trans.type.registration.cart', null, '72cb0487-e42f-4043-ba3d-da8a68b6bdb4', 'R.AARONE', 'R.AARONE', TIMESTAMP '2014-09-25 12:07:38', 1)
/
insert into KSEN_LPR_TRANS_ITEM (CREATEID, CREATETIME, CROSSLIST, DESCR_FORMATTED, DESCR_PLAIN, EXISTING_LUI_ID, GROUP_ID, ID, LPR_TRANS_ID, LPR_TRANS_ITEM_STATE, LPR_TRANS_ITEM_TYPE, LTI_RESULTING_LPR_ID, NAME, NEW_LUI_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('R.AARONE', TIMESTAMP '2014-09-25 11:55:04', 'ENGL101', null, null, null, null, '2311a974-01b8-4692-9d6d-383b0dd27480', '2f4e2d49-f1d5-46a7-bfd0-5bc3f75b5803', 'kuali.lpr.trans.item.state.new', 'kuali.course.registration.request.item.type.add', null, null, '26d4d8ce-8c91-4ae5-9b4a-3174d45bd486', null, 'KS-6809', 'R.AARONE', TIMESTAMP '2014-09-25 11:55:04', 1)
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (ID, LPR_TRANS_ITEM_ID, OBJ_ID, OPTION_KEY, OPTION_VALUE) values ('b4d74c47-380c-4b50-b0de-40c74b577a40', '2311a974-01b8-4692-9d6d-383b0dd27480', '75abe59a-3e71-435f-8aad-cd514226cc75', 'kuali.lpr.trans.item.option.oktorepeat', null)
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (ID, LPR_TRANS_ITEM_ID, OBJ_ID, OPTION_KEY, OPTION_VALUE) values ('0dbec1ad-4d6b-4e56-a0c9-55a05b4873eb', '2311a974-01b8-4692-9d6d-383b0dd27480', 'f45c2b32-b5b3-4048-90e4-e6092e8794b8', 'kuali.lpr.trans.item.option.oktoholduntillist', 'null')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (ID, LPR_TRANS_ITEM_ID, OBJ_ID, OPTION_KEY, OPTION_VALUE) values ('bcb0cfee-c026-49f3-bbd2-801f32f6abfc', '2311a974-01b8-4692-9d6d-383b0dd27480', 'e2abedf5-28dd-4493-9f06-a9506d3999c7', 'kuali.lpr.trans.item.option.oktowaitlist', 'false')
/
insert into KSEN_LPR_TRANS_ITEM_RQST_OPT (ID, LPR_TRANS_ITEM_ID, OBJ_ID, OPTION_KEY, OPTION_VALUE) values ('fd83dc37-6609-46d8-9eb7-08f98ed033c1', '2311a974-01b8-4692-9d6d-383b0dd27480', '2e41ebd0-4482-4bb4-a721-0bc0c449d004', 'kuali.lpr.trans.item.option.effective.date', '2014-09-25')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('2311a974-01b8-4692-9d6d-383b0dd27480', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LPR_TRANS_ITEM_RVG (LPR_TRANS_ITEM_ID, RESULT_VAL_GRP_ID) values ('2311a974-01b8-4692-9d6d-383b0dd27480', 'kuali.resultComponent.grade.letter')
/
