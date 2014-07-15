--KSAP-1642 Register students E.EF, B.NANAL, B.JULIL in BSCI125, 2014 Spring, Activity "A", reg-group 1001
--note:  this script was written based on existing script 2014-04-22-KSEN-Ref-Data-fill-up-courses.sql

--hints:
--  0. use Enroll->Manage CO (for bsci125) & view links in firebug to find LuiIds for both the CO & AO
--  1. use this sql to find the reg-group luiIds for a given AO:
--
-- //List lui of reg-grp note: descr_plain has groupId like 1001
-- select * From ksen_Lui
-- where id in
-- (select lui_id from ksen_luilui_reltn where related_lui_id='fe0d0cda-b89f-4033-99e4-3cbbe488c5c9'
--   and luilui_reltn_type='kuali.lui.lui.relation.type.registeredforvia.rg2ao')
--
--  3. use this sql to generate new unique ID's for the LPR_ID fields (note the master_lpr_id is the reg-group lpr_id)
--     ...and add the hyphens after the 8th, 12t, 16th, 20th digits
--
--select lower(sys_guid()||' : '||sys_guid()||' : '||sys_guid()||' : '||sys_guid()||' : '||sys_guid()||' : '||sys_guid()) from dual
--
--  4. the lpr_ids of the CO, AO, and Reg-Group are used in the lpr_result_val_grp records (2 per)


-- ADD LPR record for each luiId for each of: the CO, the AO, and the Reg-Group for the given user
--
INSERT INTO KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) VALUES ('kuali.atp.2014Spring', null, 'admin', TIMESTAMP '2014-07-14 11:15:18', null, TIMESTAMP '2014-07-14 11:15:18', null, null, 'ab9323a0-bb07-448d-a0c1-79953ea2e1eb', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.activity.offering', 'fe0d0cda-b89f-4033-99e4-3cbbe488c5c9', 'ab9323a2-bb07-448d-a0c1-79953ea2e1eb', '6c780d88-a008-43b0-b105-d663d10a87ce', 'B.EF', 'admin', TIMESTAMP '2014-07-14 11:15:18', 0)
/
INSERT INTO KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) VALUES ('kuali.atp.2014Spring', null, 'admin', TIMESTAMP '2014-07-14 11:15:19', '3.0', TIMESTAMP '2014-07-14 11:15:18', null, 'kuali.resultComponent.grade.letter', 'ab9323a1-bb07-448d-a0c1-79953ea2e1eb', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.course.offering', 'ce1507ba-94a3-4206-a9ed-9ec4da0999ab', 'ab9323a2-bb07-448d-a0c1-79953ea2e1eb', 'cc6ec402-055e-447e-a329-12244efbb622', 'B.EF', 'admin', TIMESTAMP '2014-07-14 11:15:18', 0)
/
INSERT INTO KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) VALUES ('kuali.atp.2014Spring', null, 'admin', TIMESTAMP '2014-07-14 11:15:20', '3.0', TIMESTAMP '2014-07-14 11:15:18', null, 'kuali.resultComponent.grade.letter', 'ab9323a2-bb07-448d-a0c1-79953ea2e1eb', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.registration.group', 'c539173f-2b50-43e5-8576-415bad4b5f04', 'ab9323a2-bb07-448d-a0c1-79953ea2e1eb', 'a0014a07-d0a3-4c83-9d96-1966f619b638', 'B.EF', 'admin', TIMESTAMP '2014-07-14 11:15:18', 0)
/

INSERT INTO KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) VALUES ('kuali.atp.2014Spring', null, 'admin', TIMESTAMP '2014-07-14 11:15:18', null, TIMESTAMP '2014-07-14 11:15:18', null, null, '3087363a-d57e-4da7-b846-98cd6f094a3f', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.activity.offering', 'fe0d0cda-b89f-4033-99e4-3cbbe488c5c9', '7c1e7c63-9793-40c4-a89a-a4277c4be210', '2524d14d-8587-4997-9e34-b8127417ff68', 'B.NANAL', 'admin', TIMESTAMP '2014-07-14 11:15:18', 0)
/
INSERT INTO KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) VALUES ('kuali.atp.2014Spring', null, 'admin', TIMESTAMP '2014-07-14 11:15:19', '3.0', TIMESTAMP '2014-07-14 11:15:18', null, 'kuali.resultComponent.grade.letter', '515f2fa0-5746-4334-89e6-4afb73e3d5df', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.course.offering', 'ce1507ba-94a3-4206-a9ed-9ec4da0999ab', '7c1e7c63-9793-40c4-a89a-a4277c4be210', 'b83a619a-cae8-456d-ae9e-49701018a823', 'B.NANAL', 'admin', TIMESTAMP '2014-07-14 11:15:18', 0)
/
INSERT INTO KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) VALUES ('kuali.atp.2014Spring', null, 'admin', TIMESTAMP '2014-07-14 11:15:20', '3.0', TIMESTAMP '2014-07-14 11:15:18', null, 'kuali.resultComponent.grade.letter', '7c1e7c63-9793-40c4-a89a-a4277c4be210', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.registration.group', 'c539173f-2b50-43e5-8576-415bad4b5f04', '7c1e7c63-9793-40c4-a89a-a4277c4be210', 'cc13b989-69cc-4938-adc0-9458c115b6e5', 'B.NANAL', 'admin', TIMESTAMP '2014-07-14 11:15:18', 0)
/

INSERT INTO KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) VALUES ('kuali.atp.2014Spring', null, 'admin', TIMESTAMP '2014-07-14 11:15:18', null, TIMESTAMP '2014-07-14 11:15:18', null, null, '9db541b2-b542-4134-b762-620a8810d773', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.activity.offering', 'fe0d0cda-b89f-4033-99e4-3cbbe488c5c9', '575816b0-eb8c-4f7c-86e1-7bc3f83c3092', '996b0270-ec0c-4695-978b-cefb0c039351', 'B.JULIL', 'admin', TIMESTAMP '2014-07-14 11:15:18', 0)
/
INSERT INTO KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) VALUES ('kuali.atp.2014Spring', null, 'admin', TIMESTAMP '2014-07-14 11:15:19', '3.0', TIMESTAMP '2014-07-14 11:15:18', null, 'kuali.resultComponent.grade.letter', '7d8486d4-7d24-4c79-873e-9bd6a680d33a', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.course.offering', 'ce1507ba-94a3-4206-a9ed-9ec4da0999ab', '575816b0-eb8c-4f7c-86e1-7bc3f83c3092', 'cb5a9f4b-c357-4a5b-98f6-62ff3a65970f', 'B.JULIL', 'admin', TIMESTAMP '2014-07-14 11:15:18', 0)
/
INSERT INTO KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) VALUES ('kuali.atp.2014Spring', null, 'admin', TIMESTAMP '2014-07-14 11:15:20', '3.0', TIMESTAMP '2014-07-14 11:15:18', null, 'kuali.resultComponent.grade.letter', '575816b0-eb8c-4f7c-86e1-7bc3f83c3092', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.registration.group', 'c539173f-2b50-43e5-8576-415bad4b5f04', '575816b0-eb8c-4f7c-86e1-7bc3f83c3092', '71b5c905-00d4-49c8-9428-848854ea7ddb', 'B.JULIL', 'admin', TIMESTAMP '2014-07-14 11:15:18', 0)
/

--Add 2 LPR Result records (credit, and letter grade) for each lprId of each of: the CO, the AO, and the Reg-Group
INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('ab9323a0-bb07-448d-a0c1-79953ea2e1eb', 'kuali.result.value.credit.degree.3.0')
/
INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('ab9323a0-bb07-448d-a0c1-79953ea2e1eb', 'kuali.resultComponent.grade.letter')
/
INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('ab9323a1-bb07-448d-a0c1-79953ea2e1eb', 'kuali.result.value.credit.degree.3.0')
/
INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('ab9323a1-bb07-448d-a0c1-79953ea2e1eb', 'kuali.resultComponent.grade.letter')
/
INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('ab9323a2-bb07-448d-a0c1-79953ea2e1eb', 'kuali.result.value.credit.degree.3.0')
/
INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('ab9323a2-bb07-448d-a0c1-79953ea2e1eb', 'kuali.resultComponent.grade.letter')
/

INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('3087363a-d57e-4da7-b846-98cd6f094a3f', 'kuali.result.value.credit.degree.3.0')
/
INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('3087363a-d57e-4da7-b846-98cd6f094a3f', 'kuali.resultComponent.grade.letter')
/
INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('515f2fa0-5746-4334-89e6-4afb73e3d5df', 'kuali.result.value.credit.degree.3.0')
/
INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('515f2fa0-5746-4334-89e6-4afb73e3d5df', 'kuali.resultComponent.grade.letter')
/
INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('7c1e7c63-9793-40c4-a89a-a4277c4be210', 'kuali.result.value.credit.degree.3.0')
/
INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('7c1e7c63-9793-40c4-a89a-a4277c4be210', 'kuali.resultComponent.grade.letter')
/

INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('9db541b2-b542-4134-b762-620a8810d773', 'kuali.result.value.credit.degree.3.0')
/
INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('9db541b2-b542-4134-b762-620a8810d773', 'kuali.resultComponent.grade.letter')
/
INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('7d8486d4-7d24-4c79-873e-9bd6a680d33a', 'kuali.result.value.credit.degree.3.0')
/
INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('7d8486d4-7d24-4c79-873e-9bd6a680d33a', 'kuali.resultComponent.grade.letter')
/
INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('575816b0-eb8c-4f7c-86e1-7bc3f83c3092', 'kuali.result.value.credit.degree.3.0')
/
INSERT INTO KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) VALUES ('575816b0-eb8c-4f7c-86e1-7bc3f83c3092', 'kuali.resultComponent.grade.letter')
/

