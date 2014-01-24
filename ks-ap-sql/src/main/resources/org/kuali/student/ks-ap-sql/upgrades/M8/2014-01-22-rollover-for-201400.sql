insert into KSEN_SOC_ROR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ITEMS_EXPECTED, ITEMS_PROCESSED, MESG_FORMATTED, MESG_PLAIN, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_SOC_ID, TARGET_SOC_ID, TARGET_TERM_ID, ID) values ('971d59cc-eb7c-4504-ac92-ec51f651c326', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'kuali.soc.rollover.state.submitted', 'kuali.soc.rollover.results.type.rollover', '03192a24-765a-4b9d-b638-5158430e09c8', '59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', 'kuali.atp.2014Winter', '5b88a612-d027-429f-a9f3-b4241ed736c7')
/
insert into KSEN_SOC_ROR_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f67f498e-dc02-4303-9f4e-b16daa8a55ae', 'kuali.soc.rollover.result.dynattr.course.offerings.skipped', '5b88a612-d027-429f-a9f3-b4241ed736c7', '0', '48c198c5-8435-4e5c-8f4c-6c3456086b72')
/
insert into KSEN_SOC_ROR_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8abd863d-e848-4b63-b7ef-0432d6bc93b0', 'kuali.soc.rollover.result.dynattr.course.offerings.created', '5b88a612-d027-429f-a9f3-b4241ed736c7', '0', 'c930c107-524f-46cb-97d3-5704d68f5d66')
/
insert into KSEN_SOC_ROR_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4f0f98e0-b8b3-4789-958c-990ffb96e4e8', 'kuali.soc.rollover.result.dynattr.date.initiated', '5b88a612-d027-429f-a9f3-b4241ed736c7', '2014 Jan 23 20:00:31 America/New_York', '58ce25c8-83d5-46e4-a568-023ab1fc6c28')
/
insert into KSEN_SOC_ROR_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e729a305-7459-483c-9d8c-e1f31d60029e', 'kuali.soc.rollover.result.dynattr.activity.offerings.created', '5b88a612-d027-429f-a9f3-b4241ed736c7', '0', 'adbeabec-721f-44c5-b814-d94179ba6da9')
/
insert into KSEN_SOC_ROR_ATTR ( OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9fb88ec8-bcb0-4670-ad8c-3df5cd54f001', 'kuali.soc.rollover.result.dynattr.activity.offerings.skipped', '5b88a612-d027-429f-a9f3-b4241ed736c7', '0', '75a2daf2-4894-469a-b1e4-f3833afafcc2')
/
insert into KSEN_SOC_ROR_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e2dae705-59ea-4cea-a1b8-97963890ed4e', 'kuali.soc.rollover.result.dynattr.date.completed', '5b88a612-d027-429f-a9f3-b4241ed736c7', '2014 Jan 23 20:00:31 America/New_York', '289b7901-b122-4239-94fd-e4dd1e0066cf')
/
insert into KSEN_SOC_ROR_OPTION (OPTION_ID, ROR_ID, ID) values ('kuali.rollover.option.targetterm.validated', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'e17c2c93-002d-4003-8149-2af97ebaf643')
/
insert into KSEN_SOC_ROR_OPTION (OPTION_ID, ROR_ID, ID) values ('kuali.rollover.whatcourses.ifnonewversion', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'f0b57343-288c-4042-b354-f96e6a723250')
/
insert into KSEN_SOC_ROR_OPTION (OPTION_ID, ROR_ID, ID) values ('kuali.rollover.processing.log.successes', '5b88a612-d027-429f-a9f3-b4241ed736c7', '15046f22-96bc-4853-b077-db793fcfa80f')
/
update KSEN_SOC_ROR set OBJ_ID='971d59cc-eb7c-4504-ac92-ec51f651c326', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ITEMS_EXPECTED='', ITEMS_PROCESSED='', MESG_FORMATTED='', MESG_PLAIN='', SOC_ROR_STATE='kuali.soc.rollover.state.running', SOC_ROR_TYPE='kuali.soc.rollover.results.type.rollover', SOURCE_SOC_ID='03192a24-765a-4b9d-b638-5158430e09c8', TARGET_SOC_ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', TARGET_TERM_ID='kuali.atp.2014Winter' where ID='5b88a612-d027-429f-a9f3-b4241ed736c7' and VER_NBR=0
/
update KSEN_SOC_ROR set OBJ_ID='971d59cc-eb7c-4504-ac92-ec51f651c326', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ITEMS_EXPECTED=48, ITEMS_PROCESSED=0, MESG_FORMATTED='', MESG_PLAIN='', SOC_ROR_STATE='kuali.soc.rollover.state.running', SOC_ROR_TYPE='kuali.soc.rollover.results.type.rollover', SOURCE_SOC_ID='03192a24-765a-4b9d-b638-5158430e09c8', TARGET_SOC_ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', TARGET_TERM_ID='kuali.atp.2014Winter' where ID='5b88a612-d027-429f-a9f3-b4241ed736c7' and VER_NBR=1
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('fbd2f847-4629-4428-9d0e-b6af65182f1c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'CLUID-CHEM640-198708000000', TIMESTAMP '2014-01-01 19:00:00.0', '', 'A tutorial type course dealing with the basic description of the fundamentals of writing organic reaction mechanisms.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'CHEM640 CO', 'A tutorial type course dealing with the basic description of the fundamentals of writing organic reaction mechanisms.', null, 'a6a56051-9efb-4b0b-a16e-b80dc26957b8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('59a5ba4f-06df-45a7-8bb2-bbb66b8d9f89', 'kuali.attribute.wait.list.type.key', 'a6a56051-9efb-4b0b-a16e-b80dc26957b8', null, '9f20d326-e1dc-4c53-a26e-c398372fc618')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8f6ebd59-81b3-404b-a842-dd67a43f0410', 'kuali.attribute.course.number.internal.suffix', 'a6a56051-9efb-4b0b-a16e-b80dc26957b8', 'A', 'b001a5ff-82a0-43f2-8ee7-1a6f25288cfe')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c0471186-5102-4491-92f2-3d88bbe50688', 'kuali.attribute.course.evaluation.indicator', 'a6a56051-9efb-4b0b-a16e-b80dc26957b8', 0, '8080df58-e7b7-42f5-9e2b-3b16535c1d80')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5e6c49b5-82d3-4b91-ad06-c8583eb6a6fe', 'kuali.attribute.final.exam.driver', 'a6a56051-9efb-4b0b-a16e-b80dc26957b8', 'kuali.lu.exam.driver.ActivityOffering', '44daf3a3-6676-45c5-afc1-6e493306eac2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('17a5d6d8-9312-4d73-b825-9e1765661403', 'kuali.attribute.final.exam.indicator', 'a6a56051-9efb-4b0b-a16e-b80dc26957b8', 'STANDARD', 'f2288130-ca6a-41e2-9faa-ec5c936bf3ba')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('edc53014-4fd5-4b61-a683-8a2df997814a', 'kuali.attribute.funding.source', 'a6a56051-9efb-4b0b-a16e-b80dc26957b8', '', 'd376048f-2f07-4b84-a689-0d5f227ad3e6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('01f49a35-eaf4-4937-bffb-f54436fbcd84', 'kuali.attribute.wait.list.level.type.key', 'a6a56051-9efb-4b0b-a16e-b80dc26957b8', 'AO', 'a9ba5a7f-bf83-447e-ba43-07b4a3c2f682')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d66c2810-4384-4477-b006-0b8c3b79e181', 'kuali.attribute.wait.list.indicator', 'a6a56051-9efb-4b0b-a16e-b80dc26957b8', 1, 'baa788d1-ae97-4962-ae91-8dfa55eb37d1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b42ada9a-29ae-45c2-bc99-e0c0750a0964', 'kuali.attribute.final.exam.use.matrix', 'a6a56051-9efb-4b0b-a16e-b80dc26957b8', 1, '8468181d-de36-494f-8040-55dcd28c43dd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('52fc2881-3223-4be4-bdae-7b321549fbff', 'kuali.attribute.where.fees.attached.flag', 'a6a56051-9efb-4b0b-a16e-b80dc26957b8', 0, 'c49959ba-6dc3-4ba1-be5a-b586432c3d47')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('0ea5794b-dd43-407d-9f4d-db75bd32cc8e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'CHEM640', 'CHEM', '', 'Problems in Organic Reaction Mechanisms', 'a6a56051-9efb-4b0b-a16e-b80dc26957b8', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7b98ecf6-0477-4243-8182-c3555f517dd3')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('ffd3a3e0-be7b-4924-b24a-73116f328b9e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'a6a56051-9efb-4b0b-a16e-b80dc26957b8', '', 'kuali.lu.code.honorsOffering', '', 'dfbeb029-3c44-43ee-96ac-802ada096d6e')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('a6a56051-9efb-4b0b-a16e-b80dc26957b8', '4284516367', 'e90bbfbb-7a53-4f95-8d2d-c9191d3b0c77')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('a6a56051-9efb-4b0b-a16e-b80dc26957b8', '4284516367')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('a6a56051-9efb-4b0b-a16e-b80dc26957b8', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('a6a56051-9efb-4b0b-a16e-b80dc26957b8', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('a6a56051-9efb-4b0b-a16e-b80dc26957b8', 'kuali.creditType.credit.degree.1.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('29a8e0cf-14b4-4468-93ca-8f7f146058fb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '46d57760-5695-452e-9789-e0ecb8704e76', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '8dd6d666-59bd-4ef9-98e8-fb2d80b549af')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8e97556e-e635-449d-b89b-b318d16ae7a4', 'kuali.attribute.grade.roster.level.type.key', '8dd6d666-59bd-4ef9-98e8-fb2d80b549af', 'kuali.lui.type.activity.offering.lecture', 'c8d523af-3a5a-4d73-89a4-2f8b3f0a5218')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cfa66b8f-1039-400f-b18c-5f5ff0579599', 'regCodePrefix', '8dd6d666-59bd-4ef9-98e8-fb2d80b549af', '1', 'cd20b0d5-b4a6-40d3-9bf8-fd0ba42f676a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b59799e6-5eab-4cb3-a13c-8a0f03f79ed6', 'kuali.attribute.final.exam.level.type', '8dd6d666-59bd-4ef9-98e8-fb2d80b549af', 'kuali.lu.type.activity.Lecture', '2f5eb80a-74ff-4983-8c71-ea3726e25643')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c7968473-adb9-402b-87e6-fa9ca047d80a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '8dd6d666-59bd-4ef9-98e8-fb2d80b549af', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '0102a6f1-cc4b-454b-ab4c-c7b540217f62')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('8dd6d666-59bd-4ef9-98e8-fb2d80b549af', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('c60aa251-95ce-4cca-87ba-dbf1a93f8a80', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:39.985', '', 'CHEM640-CO-FO', 'a6a56051-9efb-4b0b-a16e-b80dc26957b8', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'CHEM640-CO-FO', '8dd6d666-59bd-4ef9-98e8-fb2d80b549af', 'ec38ae9c-33bc-422a-b208-68c2327cc02d')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d74fa7b6-99c2-4423-b9ad-2e821a912ce8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '0843b8ef-6b97-4c97-a26b-11cbfbad831f', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 30, 30, 'Lecture', 'Courses with lecture', null, '0f986b36-4e4c-4669-9109-57ca7a39329f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('41cdb1ff-e7d2-4f18-9cdc-ecadc683a82d', 'kuali.attribute.course.evaluation.indicator', '0f986b36-4e4c-4669-9109-57ca7a39329f', 0, 'f688ca2c-0e53-431c-865a-86e5e2ce066a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e23b1577-665c-40c9-acde-7ce63d8d6b44', 'kuali.attribute.max.enrollment.is.estimate', '0f986b36-4e4c-4669-9109-57ca7a39329f', 0, 'c8cfbd85-12f7-4cb4-9577-70f13f4390f9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9acdc5bd-d165-4452-a833-c252fc31abe8', 'kuali.attribute.nonstd.ts.indicator', '0f986b36-4e4c-4669-9109-57ca7a39329f', 0, '85b83f59-c564-4c45-972c-25c7f2aa0916')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('70b9ab69-de11-44e8-90ed-701bd824963b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '0f986b36-4e4c-4669-9109-57ca7a39329f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '4ec08265-db78-456a-9d29-a70b80680940')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('04f64318-7d0c-4ec0-ab55-092c79b7075d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '0f986b36-4e4c-4669-9109-57ca7a39329f', '', 'kuali.lu.code.honorsOffering', 0, 'c377f454-33e1-4a7f-9e73-e0d2880eb848')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('9031297b-9fd0-4736-b357-3273a80168e3', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:00:40.206', '', '', '0f986b36-4e4c-4669-9109-57ca7a39329f', '', 'R.JULIEP', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '34cc5397-2bea-4728-8afb-7db5aef15230')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('2d4297ae-7d6e-4f71-9eeb-b30d01288344', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:40.298', '', 'CHEM640-FO-AO', '8dd6d666-59bd-4ef9-98e8-fb2d80b549af', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM640-FO-AO', '0f986b36-4e4c-4669-9109-57ca7a39329f', '38fbda33-09b8-4d6c-99b7-2def3d7af637')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('dfb53604-3815-4f31-97ef-6a0146060fd8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for CHEM640 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '893ffe33-ede6-4b75-a88c-2fb6c3c3f236')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('59dc5808-3b7a-4d0a-bd33-54047d0ab707', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for CHEM640 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '893ffe33-ede6-4b75-a88c-2fb6c3c3f236', 'de12fed9-9d8a-4449-9d4e-d1c1dc215aff')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('2e08754d-0e4f-4662-9fa9-9f0242ecb48a', 0, 'de12fed9-9d8a-4449-9d4e-d1c1dc215aff', '4f150f51-42c0-4588-9870-97604629705f')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('893ffe33-ede6-4b75-a88c-2fb6c3c3f236', '0f986b36-4e4c-4669-9109-57ca7a39329f')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('4f150f51-42c0-4588-9870-97604629705f', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('4f150f51-42c0-4588-9870-97604629705f', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('4f150f51-42c0-4588-9870-97604629705f', '2246d609-9fbb-4d06-b45e-a57ef80539b7')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('4f150f51-42c0-4588-9870-97604629705f', 'F0AA72D6DC26F650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('668d866b-8619-47ae-ae37-4663f014fdc3', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'a97bfe9f-0449-4c13-8908-2cfeb619532b')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('a97bfe9f-0449-4c13-8908-2cfeb619532b', '0f986b36-4e4c-4669-9109-57ca7a39329f')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('a97bfe9f-0449-4c13-8908-2cfeb619532b', '8dd6d666-59bd-4ef9-98e8-fb2d80b549af')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('6a984ed0-c40c-4623-afa9-cacb3a0f8b84', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '8dd6d666-59bd-4ef9-98e8-fb2d80b549af', 'CL 1', 'CL 1', 'bcd53a54-95c2-4702-9244-b4d7cb6056d0')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('cf98db2e-d74b-403f-ba66-349083f376b9', 'kuali.lui.type.activity.offering.lecture', 'bcd53a54-95c2-4702-9244-b4d7cb6056d0', '6382c6dd-cc3c-441b-98bb-b8e5746c6672')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('6382c6dd-cc3c-441b-98bb-b8e5746c6672', '0f986b36-4e4c-4669-9109-57ca7a39329f')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='bcd53a54-95c2-4702-9244-b4d7cb6056d0' where ID='6382c6dd-cc3c-441b-98bb-b8e5746c6672'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('c58bf3a0-1ab7-466e-8ba6-d9bc3609f900', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '46d57760-5695-452e-9789-e0ecb8704e76', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '2abc1c2a-52f6-4c42-ac8a-b5633be82a1b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8f59decb-8dac-4420-9cd6-b4d17ab4d195', 'kuali.attribute.registration.group.aocluster.id', '2abc1c2a-52f6-4c42-ac8a-b5633be82a1b', 'bcd53a54-95c2-4702-9244-b4d7cb6056d0', '4de5b9f4-3560-4e6e-9f21-c22ca1786b0e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f86fc8ba-5c0c-474d-8a52-4d9004845efe', 'kuali.attribute.registration.group.is.generated', '2abc1c2a-52f6-4c42-ac8a-b5633be82a1b', 1, '4fba5b9a-18b6-4cbf-91d1-4a16a2619912')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('934b192a-f8b1-478b-865f-49ee78177d39', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '2abc1c2a-52f6-4c42-ac8a-b5633be82a1b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '6298a45e-be31-4e12-a178-1a3c2b883a31')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f22efb70-d5d3-4ea2-b35c-fb4d90bcf40a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:41.773', '', 'CHEM640-FO-RG', '8dd6d666-59bd-4ef9-98e8-fb2d80b549af', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM640-FO-RG', '2abc1c2a-52f6-4c42-ac8a-b5633be82a1b', 'f0f6aa1b-5b97-458b-abd8-12ce466c6f04')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a923e8d1-78fc-469e-80c6-cfebcd63d7b9', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:41.788', '', 'CHEM640-RG-AO', '2abc1c2a-52f6-4c42-ac8a-b5633be82a1b', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM640-RG-AO', '0f986b36-4e4c-4669-9109-57ca7a39329f', '6dde254c-d5c0-427a-8590-9e17fb6744cf')
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='e2dae705-59ea-4cea-a1b8-97963890ed4e', ATTR_KEY='kuali.soc.rollover.result.dynattr.date.completed', OWNER_ID='5b88a612-d027-429f-a9f3-b4241ed736c7', ATTR_VALUE='2014 Jan 23 20:00:41 America/New_York' where ID='289b7901-b122-4239-94fd-e4dd1e0066cf'
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('9bab4094-64e8-41cb-8dd7-396d923b3df1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '0567769d-f680-464d-8f9b-adb118a53696', 'a6a56051-9efb-4b0b-a16e-b80dc26957b8', 'b73b1437-6894-41de-8959-02180d127833')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d1e1eb0b-ccad-4fbd-bb8b-1bd7d49fe683', 'activityOfferingsCreated', 'b73b1437-6894-41de-8959-02180d127833', '1', 'db0fbd6f-2fee-46ac-b5a5-1a3c794a1ec0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('0167f5d9-504f-4134-b92c-e9f6bddf67b0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '5dac01fa-f1c8-4f8d-a6be-70adf6889e2b', TIMESTAMP '2014-01-01 19:00:00.0', '', 'An argument for the broad continuity between the revolutionary and Napoleonic wars.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST436 CO', 'An argument for the broad continuity between the revolutionary and Napoleonic wars.', null, '7e57d564-919b-498e-9db8-d2b8ffb84a1d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bc7c97b8-fbf5-4629-8ba0-1dcb9e6a8837', 'kuali.attribute.course.evaluation.indicator', '7e57d564-919b-498e-9db8-d2b8ffb84a1d', 0, 'f2bde989-b262-4a0c-9319-3c173727d829')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9b963202-80c6-4070-809c-b60ea4d06279', 'kuali.attribute.final.exam.driver', '7e57d564-919b-498e-9db8-d2b8ffb84a1d', 'kuali.lu.exam.driver.ActivityOffering', '277fe6be-7b1f-48bd-a99b-25adde9c5a80')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0723f697-d365-402d-98fe-ab34f056910f', 'kuali.attribute.final.exam.use.matrix', '7e57d564-919b-498e-9db8-d2b8ffb84a1d', 1, '23e2cb4a-4af5-4a69-8bbd-030b565ef19a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('187770e3-d277-463a-9902-0e9127b1d929', 'kuali.attribute.where.fees.attached.flag', '7e57d564-919b-498e-9db8-d2b8ffb84a1d', 0, 'cd117b26-f374-4a85-931c-b82e7351fb54')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('39c57376-4d8f-4b06-9d53-54f1ab8fb7b3', 'kuali.attribute.wait.list.level.type.key', '7e57d564-919b-498e-9db8-d2b8ffb84a1d', 'AO', 'd099032e-6ba9-44c1-8413-8be186ed3f66')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('11ff58a3-7467-4653-81c1-da06931aca73', 'kuali.attribute.course.number.internal.suffix', '7e57d564-919b-498e-9db8-d2b8ffb84a1d', 'A', '9d49e289-b144-4039-90e6-a24afc4ee6a5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d1ef439b-917b-4845-9c8e-98931e2c039d', 'kuali.attribute.funding.source', '7e57d564-919b-498e-9db8-d2b8ffb84a1d', '', '8a9da247-5754-40b9-a624-e5e14937e3f5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('28a728eb-c389-448d-aaa0-ab75d7b6e4ce', 'kuali.attribute.wait.list.type.key', '7e57d564-919b-498e-9db8-d2b8ffb84a1d', null, 'dc6d437f-bd26-4358-bbdd-9cdfa1fd46fa')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5a194c84-609d-45ef-8856-fae7496953b2', 'kuali.attribute.wait.list.indicator', '7e57d564-919b-498e-9db8-d2b8ffb84a1d', 1, '935ba647-1a57-48d3-8f18-312e2b747805')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b0adc256-a8bf-4055-b12b-5eba2ad70a74', 'kuali.attribute.final.exam.indicator', '7e57d564-919b-498e-9db8-d2b8ffb84a1d', 'STANDARD', '25ef8281-0a92-406e-9d73-fc91c0ca2cd7')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6b313827-af5a-4d83-aa68-dd4b71487823', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'HIST436', 'HIST', '', 'Napoleon, the French Revolution and the World', '7e57d564-919b-498e-9db8-d2b8ffb84a1d', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '13e7aecf-4fa6-475f-9433-02396a1f4cac')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('d45fd9b3-1d15-4a2b-9198-5aa658721e5f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '7e57d564-919b-498e-9db8-d2b8ffb84a1d', '', 'kuali.lu.code.honorsOffering', '', 'a5a72271-0d85-4f72-b039-5fa43450d9c3')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('7e57d564-919b-498e-9db8-d2b8ffb84a1d', '3213375036', 'f7170f2a-0c5f-44c0-9909-af10ac66178b')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('7e57d564-919b-498e-9db8-d2b8ffb84a1d', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('7e57d564-919b-498e-9db8-d2b8ffb84a1d', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('7e57d564-919b-498e-9db8-d2b8ffb84a1d', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('7e57d564-919b-498e-9db8-d2b8ffb84a1d', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('7e57d564-919b-498e-9db8-d2b8ffb84a1d', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('9bea5108-9b68-47f6-ab01-077395870273', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'f0d82d9c-9834-4aa8-bb79-ee89d58eb8c9', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '84535045-cb46-4bd3-92c4-f9d5fde47917')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0b3075cf-ecd7-417f-bc9b-b6aba2405331', 'regCodePrefix', '84535045-cb46-4bd3-92c4-f9d5fde47917', '1', '9c4ec330-a5e6-4518-be3f-27fd1324a5b5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3b5cbba5-3b2d-43a4-b5a0-5042df4ebebd', 'kuali.attribute.final.exam.level.type', '84535045-cb46-4bd3-92c4-f9d5fde47917', 'kuali.lu.type.activity.Lecture', '1491f1a5-550d-4bb1-ad63-0ef15999bb03')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('58ae81fb-562e-45c8-9e1b-8e0815ec423e', 'kuali.attribute.grade.roster.level.type.key', '84535045-cb46-4bd3-92c4-f9d5fde47917', 'kuali.lui.type.activity.offering.lecture', '6cc4399c-36f8-4888-8232-e8867b84b2fd')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5b552323-dfd6-4931-89cb-acaa91a13965', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '84535045-cb46-4bd3-92c4-f9d5fde47917', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '10d7bc24-4952-4740-9b8e-35d05797b153')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('84535045-cb46-4bd3-92c4-f9d5fde47917', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('55685347-c89a-446a-856b-6818ca726797', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:48.088', '', 'HIST436-CO-FO', '7e57d564-919b-498e-9db8-d2b8ffb84a1d', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST436-CO-FO', '84535045-cb46-4bd3-92c4-f9d5fde47917', '17254269-7db1-48be-8622-120d8e66f4a0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ea890d04-eee3-47f6-972d-3ff3817cba3b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '3764a8ad-49ec-4cc0-8481-9ab0663491ac', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, 'f7a76afc-5956-4129-a953-9655800da5f7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bdcc1beb-156d-4a03-a26f-ce6b8e444f87', 'kuali.attribute.max.enrollment.is.estimate', 'f7a76afc-5956-4129-a953-9655800da5f7', 0, 'ec8f8f47-800a-4b5f-b674-276f2531410d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('17abdac1-5b25-44e7-988e-249452963a52', 'kuali.attribute.nonstd.ts.indicator', 'f7a76afc-5956-4129-a953-9655800da5f7', 0, '689d986c-a7d5-40c2-9346-b4d27848fa88')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('aa3ba1a9-67cc-4dc9-a0d0-354315400328', 'kuali.attribute.course.evaluation.indicator', 'f7a76afc-5956-4129-a953-9655800da5f7', 0, '78a1edef-4dba-4f96-8e04-cf173da1728a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('85403bb3-708a-4209-82a6-ef22756102f2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', 'f7a76afc-5956-4129-a953-9655800da5f7', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'd6ae9979-e529-44a3-9fa0-6c38feb5afe1')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('5109829b-caa3-479b-affe-9ca2519f120d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'f7a76afc-5956-4129-a953-9655800da5f7', '', 'kuali.lu.code.honorsOffering', 0, 'd04e65a6-72cf-4804-8b34-59511e7acde7')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('85dd7f81-7099-481c-b3e1-adfc1f220908', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:00:48.259', '', '', 'f7a76afc-5956-4129-a953-9655800da5f7', '', 'K.HECTORR', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'dd2a314f-5d27-48ca-bbc2-3b3ab1d30980')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('68b954c7-4977-4742-9264-57cf46b45911', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:48.322', '', 'HIST436-FO-AO', '84535045-cb46-4bd3-92c4-f9d5fde47917', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST436-FO-AO', 'f7a76afc-5956-4129-a953-9655800da5f7', 'ad063a20-741f-4649-9714-bb9489f054e9')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('5a6d38f9-6058-4392-9d62-ebac12a0482b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for HIST436 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '044f2e3e-ef4b-4129-9238-7f9ef803c79c')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('9072e94d-8dbe-4348-ba29-99f73a54c836', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for HIST436 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '044f2e3e-ef4b-4129-9238-7f9ef803c79c', '572ead0e-879f-4894-af0a-8af28a28b1db')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('782747c1-d491-40c7-b009-0e48a04d9603', 0, '572ead0e-879f-4894-af0a-8af28a28b1db', 'a28e9387-d6c3-4af7-917f-7d9a6e5c713b')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('044f2e3e-ef4b-4129-9238-7f9ef803c79c', 'f7a76afc-5956-4129-a953-9655800da5f7')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('a28e9387-d6c3-4af7-917f-7d9a6e5c713b', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('a28e9387-d6c3-4af7-917f-7d9a6e5c713b', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('a28e9387-d6c3-4af7-917f-7d9a6e5c713b', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('a28e9387-d6c3-4af7-917f-7d9a6e5c713b', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('ffec66fb-97d5-4f34-9706-3701176a81a8', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Athlete Seatpool', '1c2e7786-a2ab-4778-8e81-ea540c60b9b2', 1, 2, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '9694a5e4-2f7d-4de3-b8c9-99c099295979')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('ef4da336-d6b3-48ed-a6d3-3cbd93309c48', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Junior Seatpool', '4bae9f12-5901-4db9-b4a8-4dcc20781db5', 2, 4, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'c8a29d1c-1273-4488-9cd0-ff3b6c2ef9b7')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='ffec66fb-97d5-4f34-9706-3701176a81a8', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='f7a76afc-5956-4129-a953-9655800da5f7', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Athlete Seatpool', POPULATION_ID='1c2e7786-a2ab-4778-8e81-ea540c60b9b2', PROCESSING_PRIORITY=1, SEAT_LIMIT=2, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='9694a5e4-2f7d-4de3-b8c9-99c099295979' and VER_NBR=1
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('ca1fe06a-8c89-41b3-929b-be34d0c52ba5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '3313d010-126a-48a7-a5a6-a6a9342df44c')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='ef4da336-d6b3-48ed-a6d3-3cbd93309c48', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='f7a76afc-5956-4129-a953-9655800da5f7', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Junior Seatpool', POPULATION_ID='4bae9f12-5901-4db9-b4a8-4dcc20781db5', PROCESSING_PRIORITY=2, SEAT_LIMIT=4, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='c8a29d1c-1273-4488-9cd0-ff3b6c2ef9b7' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('3313d010-126a-48a7-a5a6-a6a9342df44c', 'f7a76afc-5956-4129-a953-9655800da5f7')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('3313d010-126a-48a7-a5a6-a6a9342df44c', '84535045-cb46-4bd3-92c4-f9d5fde47917')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('f91abb81-0ac8-4c90-bf82-9b518db63d21', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '84535045-cb46-4bd3-92c4-f9d5fde47917', 'CL 1', 'CL 1', '8b7ec8c4-a1ee-45ea-8d1e-1bacd5320e86')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('3cab7c08-216e-4ce1-a23a-f1d168598eda', 'kuali.lui.type.activity.offering.lecture', '8b7ec8c4-a1ee-45ea-8d1e-1bacd5320e86', 'ba3da69a-ec82-4ccb-99da-5320ffc37d4b')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('ba3da69a-ec82-4ccb-99da-5320ffc37d4b', 'f7a76afc-5956-4129-a953-9655800da5f7')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='8b7ec8c4-a1ee-45ea-8d1e-1bacd5320e86' where ID='ba3da69a-ec82-4ccb-99da-5320ffc37d4b'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('aaa33f97-028b-4c0c-b5af-77212a8c603c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'f0d82d9c-9834-4aa8-bb79-ee89d58eb8c9', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '5c316798-cf2e-4217-a2a8-edb787946553')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2daac7b4-ae45-4e15-acf7-78063403890c', 'kuali.attribute.registration.group.is.generated', '5c316798-cf2e-4217-a2a8-edb787946553', 1, '715b9825-5bc9-4849-b7e0-745af3a70108')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('098dde9c-f4d8-4fbd-a5f4-a2c2e6d67251', 'kuali.attribute.registration.group.aocluster.id', '5c316798-cf2e-4217-a2a8-edb787946553', '8b7ec8c4-a1ee-45ea-8d1e-1bacd5320e86', '5d350b1f-5449-4712-b200-c7cf4f438634')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('164f2bd4-5843-429e-ac99-8dffe7f0f709', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '5c316798-cf2e-4217-a2a8-edb787946553', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '77ec6b52-5278-4d33-a868-6d0dd9ac54ac')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e81c591d-f94f-485b-98c6-422c60870935', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:49.272', '', 'HIST436-FO-RG', '84535045-cb46-4bd3-92c4-f9d5fde47917', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST436-FO-RG', '5c316798-cf2e-4217-a2a8-edb787946553', '7eb8b9be-a8f5-4030-8268-6ed9d0080ceb')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('21718079-9033-4dfe-964d-ec8d070da3a8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:49.287', '', 'HIST436-RG-AO', '5c316798-cf2e-4217-a2a8-edb787946553', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST436-RG-AO', 'f7a76afc-5956-4129-a953-9655800da5f7', 'd3678789-9e6b-4f2b-943a-058918b0aed2')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d9e10045-b3b3-4784-83c9-3d1e32094685', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'CLUID-CHEM277-200608000000', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Quantitative analysis, inorganic analytical chemistry, and an introduction to bio-analytical instrumentation and techniques.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'CHEM277 CO', 'Quantitative analysis, inorganic analytical chemistry, and an introduction to bio-analytical instrumentation and techniques.', null, '4654e390-7bd2-4683-9d07-02480f8887c3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ff48320e-45d1-4761-a003-188748453346', 'kuali.attribute.where.fees.attached.flag', '4654e390-7bd2-4683-9d07-02480f8887c3', 0, '05fc5334-3fa5-4fa6-a4d8-dc300ec1b4e1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bf13fa38-9d51-4d30-bb99-4987be5cc6ac', 'kuali.attribute.wait.list.level.type.key', '4654e390-7bd2-4683-9d07-02480f8887c3', 'AO', 'ce7a94e3-771a-4dc1-880c-8473d663071a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b83d9bd2-327e-49d9-bf45-4fb6e4e44ecd', 'kuali.attribute.course.number.internal.suffix', '4654e390-7bd2-4683-9d07-02480f8887c3', 'A', '34bf691a-cd0d-4c4d-ba08-6b466fff78ac')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('64854c8f-8ca4-4882-a3f5-185eb758a240', 'kuali.attribute.wait.list.indicator', '4654e390-7bd2-4683-9d07-02480f8887c3', 1, 'f73e777e-14b1-4f33-b453-a5f3249d1fc8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fed1f0ab-9d9a-4e46-83fc-0aa854f02bd8', 'kuali.attribute.final.exam.indicator', '4654e390-7bd2-4683-9d07-02480f8887c3', 'STANDARD', '3043e806-6627-45c9-adf4-9525992faab4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c3b2bf11-c4e1-4530-b9f3-ca70efacbff1', 'kuali.attribute.course.evaluation.indicator', '4654e390-7bd2-4683-9d07-02480f8887c3', 0, '9888db2f-3636-47dc-b582-6f8f0debd0d8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f8de6d4b-987c-49ae-9e5f-bcce7adf31ef', 'kuali.attribute.funding.source', '4654e390-7bd2-4683-9d07-02480f8887c3', '', '718d3bbd-04af-4dfc-b5ab-0f9daecc5e10')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('03b7b4f5-7d77-4c9d-bd69-16420b04dbd2', 'kuali.attribute.final.exam.use.matrix', '4654e390-7bd2-4683-9d07-02480f8887c3', 1, '61cde008-d0ab-4496-99da-e8ca4eb35fc8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7269158d-d0ee-428f-bcdb-6ac3c259179a', 'kuali.attribute.wait.list.type.key', '4654e390-7bd2-4683-9d07-02480f8887c3', null, '913003a6-a863-4b2a-83a0-12efc62c39f4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e51074c8-5d28-4343-912a-a6972dedd158', 'kuali.attribute.final.exam.driver', '4654e390-7bd2-4683-9d07-02480f8887c3', 'kuali.lu.exam.driver.ActivityOffering', '0cb0a41c-eb76-4626-bdde-f81305ccd52e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('f7a68d6a-a091-45df-b391-360739369ab9', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'CHEM277', 'CHEM', '', 'Fundamentals of Analytical and Bioanalytical Chemistry Laboratory', '4654e390-7bd2-4683-9d07-02480f8887c3', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '4119b77e-f0ed-4f95-ade9-ff15cc7bea95')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('846a453c-4875-448c-b487-a8da594351f2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '4654e390-7bd2-4683-9d07-02480f8887c3', '', 'kuali.lu.code.honorsOffering', '', '5a862d44-69c5-4d23-9c63-e96f5f4ee749')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('4654e390-7bd2-4683-9d07-02480f8887c3', '4284516367', 'f50033d8-63ce-483c-a4d0-89cd28ef2db1')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('4654e390-7bd2-4683-9d07-02480f8887c3', '4284516367')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4654e390-7bd2-4683-9d07-02480f8887c3', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('4654e390-7bd2-4683-9d07-02480f8887c3', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('200ce2a6-1331-48db-b840-2bd83e9fdc2c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '25889d4f-0612-424c-87a5-e96f60f3e096', '', '', 'Courses with Lecture/Lab', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture/Lab', 'Courses with Lecture/Lab', '', '82fd0b14-9cd0-444d-9033-f04144687f63')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d2cc33c3-18db-4595-bb85-8e8df133c129', 'regCodePrefix', '82fd0b14-9cd0-444d-9033-f04144687f63', '1', 'f20cf612-ca5f-46cb-8ce3-ba22eea2eb84')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2b3a041a-b89d-449a-a62f-def788e4f96e', 'kuali.attribute.grade.roster.level.type.key', '82fd0b14-9cd0-444d-9033-f04144687f63', 'kuali.lui.type.activity.offering.lab', '2115e18c-d213-49e0-bbde-8593f5bf1dec')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6e1b0e90-ebc1-4d04-bca9-4337549ce92d', 'kuali.attribute.final.exam.level.type', '82fd0b14-9cd0-444d-9033-f04144687f63', 'kuali.lu.type.activity.Lecture', '18340d55-f13a-4304-b26e-d1fb9d2d3899')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7046bb50-8c50-41b8-b565-ae245a3145a4', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture/Lab', '82fd0b14-9cd0-444d-9033-f04144687f63', '', 'LEC/LAB', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '8b72a6b3-c313-4e1d-931a-016a58447baf')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('82fd0b14-9cd0-444d-9033-f04144687f63', 'kuali.lui.type.activity.offering.lab')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('82fd0b14-9cd0-444d-9033-f04144687f63', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('0d6e5de4-a42e-4a15-a410-c322601e87a4', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:53.744', '', 'CHEM277-CO-FO', '4654e390-7bd2-4683-9d07-02480f8887c3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'CHEM277-CO-FO', '82fd0b14-9cd0-444d-9033-f04144687f63', '53302c60-000d-4a80-bd05-89cfbb81989e')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('c44703d1-46d5-4386-897a-e7b2d767a44a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'ef2963d7-9647-4ce7-ac63-9523204dd654', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 48, 48, 'Lecture', 'Courses with lecture', null, 'ce9556d2-9c91-413d-8742-73d2ebaab140')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('201ddfa3-efab-4575-a078-3a1d1e6b89d4', 'kuali.attribute.max.enrollment.is.estimate', 'ce9556d2-9c91-413d-8742-73d2ebaab140', 0, 'b5cf7585-76b8-42f2-a602-23c1cbde2b7c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b27b3635-48af-4cfe-aa4c-9ea879921f0d', 'kuali.attribute.course.evaluation.indicator', 'ce9556d2-9c91-413d-8742-73d2ebaab140', 0, 'bfb68c76-7f4e-4181-88a6-e21f376ede9b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bc4fcbaa-64c1-4037-97c6-b54d2e51f647', 'kuali.attribute.nonstd.ts.indicator', 'ce9556d2-9c91-413d-8742-73d2ebaab140', 0, 'ec001bf3-62c1-4c65-84aa-2fb2d5bed2c8')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('af623270-af77-451f-aab3-106d7ef4a938', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', 'ce9556d2-9c91-413d-8742-73d2ebaab140', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'bb4c971b-3774-461c-bbf7-228f1c209225')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('0033b465-041d-4453-af1a-09761b895313', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'ce9556d2-9c91-413d-8742-73d2ebaab140', '', 'kuali.lu.code.honorsOffering', 0, '8ee88f76-bd0e-4183-aa08-ec5456d7d125')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('ffb10c81-66f0-430f-81f9-abd5d3f75b25', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:00:53.857', '', '', 'ce9556d2-9c91-413d-8742-73d2ebaab140', '', 'W.TIMOTHYC', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '03698ae8-7027-462a-9861-68cd0f0cf48f')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('40502960-3fd2-4773-973a-488ebe1f0bd9', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:53.9', '', 'CHEM277-FO-AO', '82fd0b14-9cd0-444d-9033-f04144687f63', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM277-FO-AO', 'ce9556d2-9c91-413d-8742-73d2ebaab140', '38152e29-b31e-4dae-b5bf-9f42202fa6a3')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('ca0791e0-a839-4009-b18f-a042f415bf95', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for CHEM277 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '8fd5c124-2db9-4ed4-8ac4-535344bea345')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('51611168-bf67-414e-b630-0984c49ed676', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule reqeust for CHEM277 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '8fd5c124-2db9-4ed4-8ac4-535344bea345', 'a239c0f9-12c9-4bfd-8e94-a24e63e7d3fe')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('cc8ed84b-566e-404c-9f10-c62af0f88132', 0, 'a239c0f9-12c9-4bfd-8e94-a24e63e7d3fe', '97f1ab99-c815-4b79-bc50-74512f3426f8')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('8fd5c124-2db9-4ed4-8ac4-535344bea345', 'ce9556d2-9c91-413d-8742-73d2ebaab140')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('97f1ab99-c815-4b79-bc50-74512f3426f8', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('97f1ab99-c815-4b79-bc50-74512f3426f8', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('97f1ab99-c815-4b79-bc50-74512f3426f8', 'a8ce091f-4b9d-4f52-ae15-d2eaf275d76a')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('97f1ab99-c815-4b79-bc50-74512f3426f8', 'F0AA72D6DCFEF650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('11f9be1a-ffee-476c-b68a-5b10a04f1e37', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, '', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '8afb8674-aefe-4853-ba9c-0b6b4bb817b3')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('8afb8674-aefe-4853-ba9c-0b6b4bb817b3', 'ce9556d2-9c91-413d-8742-73d2ebaab140')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('8afb8674-aefe-4853-ba9c-0b6b4bb817b3', '82fd0b14-9cd0-444d-9033-f04144687f63')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('5fc0a907-8906-430d-8717-a557457fefe1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '9b0d7d87-31ec-498b-bfe4-31a4f1e92021', '', '', 'Courses with lab', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lab', 20, 24, 'Lab', 'Courses with lab', null, '883fe601-9f49-4bd6-8845-286893ef0d15')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a2452f18-ff88-4e82-adad-3066936e4583', 'kuali.attribute.nonstd.ts.indicator', '883fe601-9f49-4bd6-8845-286893ef0d15', 0, '8381e409-f614-4844-87a5-231d90d72f08')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ee7d9a8d-9049-45a7-a8ce-8adc382cf967', 'kuali.attribute.max.enrollment.is.estimate', '883fe601-9f49-4bd6-8845-286893ef0d15', 0, '3854f1a5-61fa-4c88-a47c-7800d1cc1f79')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f759ddbc-82e7-4e6d-afe9-7b6af1ef42fd', 'kuali.attribute.course.evaluation.indicator', '883fe601-9f49-4bd6-8845-286893ef0d15', 0, '03426754-6d4a-47fe-be7b-62f5dd27ae67')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('24436c60-ae58-480f-8cbe-ff7f0c7dff12', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'B', '', '', '', '883fe601-9f49-4bd6-8845-286893ef0d15', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7cdae3fe-6999-4bd3-8408-b4e16ace059e')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('ca270a0c-9d5f-460a-8333-677cd997535d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '883fe601-9f49-4bd6-8845-286893ef0d15', '', 'kuali.lu.code.honorsOffering', 0, '45fb4fe1-0273-4e54-b0fb-5f1faab3f444')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('468ebe41-f5ab-4966-bf0c-b06ca86dfba9', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:00:54.368', '', '', '883fe601-9f49-4bd6-8845-286893ef0d15', '', 'J.JRH', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '6a1e3489-ad97-4081-a16a-53cc9ab47183')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('5755b20f-795d-47cb-a21e-ebdb35a546b5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:54.395', '', 'CHEM277-FO-AO', '82fd0b14-9cd0-444d-9033-f04144687f63', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM277-FO-AO', '883fe601-9f49-4bd6-8845-286893ef0d15', '23e2ff0e-7a1a-48f5-8da6-59ddcf361cab')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('7c8c02de-ee56-4a2a-92f9-1552ccb862f8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for CHEM277 - B', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '1a470d1f-a186-4945-a820-faefe4a1f4e1')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('e5085b8a-e195-4cba-98fa-a0685ba57990', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for CHEM277 - B', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '1a470d1f-a186-4945-a820-faefe4a1f4e1', 'e672f00b-9202-4144-9a6a-6b2458689b12')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('c196556e-1955-4f2f-82f4-dfeffe5175f2', 0, 'e672f00b-9202-4144-9a6a-6b2458689b12', 'c1ae0b10-2df3-48e0-af5a-e1eb177f4c5b')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('1a470d1f-a186-4945-a820-faefe4a1f4e1', '883fe601-9f49-4bd6-8845-286893ef0d15')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('c1ae0b10-2df3-48e0-af5a-e1eb177f4c5b', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('c1ae0b10-2df3-48e0-af5a-e1eb177f4c5b', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('c1ae0b10-2df3-48e0-af5a-e1eb177f4c5b', '5fcaeba7-a010-40dc-a069-568c197f0809')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('c1ae0b10-2df3-48e0-af5a-e1eb177f4c5b', 'F0AA72D6DBD9F650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('533d7e43-cc3e-4b2b-9c14-79df66f22b11', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, '', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '28dd909c-f394-46fe-a9df-d8e037c3ea12')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('28dd909c-f394-46fe-a9df-d8e037c3ea12', '883fe601-9f49-4bd6-8845-286893ef0d15')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('28dd909c-f394-46fe-a9df-d8e037c3ea12', '82fd0b14-9cd0-444d-9033-f04144687f63')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('2e00a456-2bae-4ea9-8c28-42bb589b785e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '6138a014-70d6-4716-a817-d096d4c79371', '', '', 'Courses with lab', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lab', 24, 24, 'Lab', 'Courses with lab', null, '27b98013-2952-4251-ab1f-d0cc09630d79')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ec4315d8-8a96-43b3-ae8d-001210cf8195', 'kuali.attribute.course.evaluation.indicator', '27b98013-2952-4251-ab1f-d0cc09630d79', 0, '3c6dc966-5c8e-4e6b-8e19-84ea614e93ab')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4bbb2143-4afd-444d-990d-78b15a6cf9ae', 'kuali.attribute.max.enrollment.is.estimate', '27b98013-2952-4251-ab1f-d0cc09630d79', 0, 'f65a5388-9644-4c24-9ac4-a80c37bf3451')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b958005c-20be-40d6-ba36-8c14c6dc3b78', 'kuali.attribute.nonstd.ts.indicator', '27b98013-2952-4251-ab1f-d0cc09630d79', 0, 'c0915809-08fc-4e5e-b07f-ba3514893f68')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('71916a19-2106-4583-8e48-52fa12bd1b7f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'C', '', '', '', '27b98013-2952-4251-ab1f-d0cc09630d79', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '6618448d-b0d0-4719-b5c9-d0dd939ebb8e')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('35b6a170-1384-417b-b904-c76febf70f9c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '27b98013-2952-4251-ab1f-d0cc09630d79', '', 'kuali.lu.code.honorsOffering', 0, 'dd291cf3-f449-4196-b440-eb87d314cacb')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('3c25c073-b432-4afd-b206-fcad76bae45c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:00:54.883', '', '', '27b98013-2952-4251-ab1f-d0cc09630d79', '', 'C.PHILIPPES', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'e6cfdb71-b4df-445d-8a4a-bceb0e898b0b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('977cc156-6f3d-4464-bd33-550734ddf49f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:54.943', '', 'CHEM277-FO-AO', '82fd0b14-9cd0-444d-9033-f04144687f63', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM277-FO-AO', '27b98013-2952-4251-ab1f-d0cc09630d79', 'ff26198c-f4c8-4666-9b12-cc7f3aeec1ea')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('a6925128-900d-416d-98fe-3e64519915e2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for CHEM277 - C', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '8a7fccdd-3d8f-48c9-b65f-76bc9a4bcedd')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('b9fabb1b-c81e-4849-a006-2c76221c0421', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule reqeust for CHEM277 - C', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '8a7fccdd-3d8f-48c9-b65f-76bc9a4bcedd', 'fd0215d2-6b81-4320-aba4-3bdbb168454e')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('3ae996ea-303c-4dda-a331-842b4e5b461a', 0, 'fd0215d2-6b81-4320-aba4-3bdbb168454e', '7977e59c-db63-4539-b55b-745b817eb824')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('8a7fccdd-3d8f-48c9-b65f-76bc9a4bcedd', '27b98013-2952-4251-ab1f-d0cc09630d79')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('7977e59c-db63-4539-b55b-745b817eb824', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('7977e59c-db63-4539-b55b-745b817eb824', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('7977e59c-db63-4539-b55b-745b817eb824', '5fcaeba7-a010-40dc-a069-568c197f0809')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('7977e59c-db63-4539-b55b-745b817eb824', 'F0AA72D6DCA5F650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('944433ab-921e-4235-b826-30453b29d25b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, '', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'd50e3ef6-8e4f-46a9-8b32-79911430f0db')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('d50e3ef6-8e4f-46a9-8b32-79911430f0db', '27b98013-2952-4251-ab1f-d0cc09630d79')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('d50e3ef6-8e4f-46a9-8b32-79911430f0db', '82fd0b14-9cd0-444d-9033-f04144687f63')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('5590a601-bfe8-4ee8-8622-53161a00df6d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'ef2963d7-9647-4ce7-ac63-9523204dd654', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 48, 48, 'Lecture', 'Courses with lecture', null, 'b8cdf7a0-2033-49d5-94e1-410bbc8584d2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6920d2ea-6fdd-4a8b-8e8f-3d6134bd080a', 'kuali.attribute.nonstd.ts.indicator', 'b8cdf7a0-2033-49d5-94e1-410bbc8584d2', 0, '4a79bbb6-33bb-410a-8752-63d05f8a7e87')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('44fec992-09e8-4004-8f37-9aedb72db7fe', 'kuali.attribute.max.enrollment.is.estimate', 'b8cdf7a0-2033-49d5-94e1-410bbc8584d2', 0, '73c6419c-f8ef-433f-8254-0bd3e96cee76')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fac345df-6f90-491c-bf22-2afc396437f0', 'kuali.attribute.course.evaluation.indicator', 'b8cdf7a0-2033-49d5-94e1-410bbc8584d2', 0, 'd0d4ebef-9f2e-46bc-85c7-ec763f2e8572')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('4620054b-037d-49b9-9507-b44f217e80ca', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'D', '', '', '', 'b8cdf7a0-2033-49d5-94e1-410bbc8584d2', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '716fbf71-ac90-4ea1-81a2-8c9b62bebf82')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('d2c8afcc-4b9a-4285-8875-d54e8661b28a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'b8cdf7a0-2033-49d5-94e1-410bbc8584d2', '', 'kuali.lu.code.honorsOffering', 0, 'c772ff67-d828-4f75-8ff6-ec57530e730a')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('c12c69ab-9888-4fcc-8242-a82027aa2f4e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:00:55.536', '', '', 'b8cdf7a0-2033-49d5-94e1-410bbc8584d2', '', 'W.TIMOTHYC', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'e0bc3a4e-9348-4fb0-84ee-e9e3a4c61544')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f9c78cc7-a39f-4f0a-8baa-84633a08f3f7', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:55.558', '', 'CHEM277-FO-AO', '82fd0b14-9cd0-444d-9033-f04144687f63', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM277-FO-AO', 'b8cdf7a0-2033-49d5-94e1-410bbc8584d2', 'edc765eb-a440-418c-a25e-c1f0c15f63ba')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('edc01359-c0f0-4461-a9b6-eed29539c724', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for CHEM277 - D', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '2aead032-5155-44bb-a462-09e71f3094e0')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('5040127f-a19e-4a90-80a5-70a7a692277f', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for CHEM277 - D', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '2aead032-5155-44bb-a462-09e71f3094e0', '14301fd6-a4b9-4f52-a686-6d0b9cf86cde')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('b5e77bb6-ff9e-4f02-8920-a9483f1c8ff8', 0, '14301fd6-a4b9-4f52-a686-6d0b9cf86cde', '072a8ed6-fc18-4e34-b20d-71e489d10b00')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('2aead032-5155-44bb-a462-09e71f3094e0', 'b8cdf7a0-2033-49d5-94e1-410bbc8584d2')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('072a8ed6-fc18-4e34-b20d-71e489d10b00', '000134ee-051b-403a-940e-eff45c3a987b')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('072a8ed6-fc18-4e34-b20d-71e489d10b00', 'F0AA72D6DD4EF650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('5cd4a494-121c-4645-b31a-3fc9dc3fe968', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, '', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'b8aa27a4-8b49-4d7b-be63-4a9a9584db6d')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('b8aa27a4-8b49-4d7b-be63-4a9a9584db6d', 'b8cdf7a0-2033-49d5-94e1-410bbc8584d2')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('b8aa27a4-8b49-4d7b-be63-4a9a9584db6d', '82fd0b14-9cd0-444d-9033-f04144687f63')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('355411cd-c236-4950-b290-4a6980e54f49', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '9b0d7d87-31ec-498b-bfe4-31a4f1e92021', '', '', 'Courses with lab', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lab', 24, 24, 'Lab', 'Courses with lab', null, '0f621f74-b5aa-4c09-982d-c8a824b2fecd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4450aa17-1a2c-4920-aebb-406165a53b55', 'kuali.attribute.max.enrollment.is.estimate', '0f621f74-b5aa-4c09-982d-c8a824b2fecd', 0, '4e65784a-6cc5-4e4d-b768-754ab86a36aa')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('26488623-5f2d-46b9-979e-1c3104c0bad2', 'kuali.attribute.course.evaluation.indicator', '0f621f74-b5aa-4c09-982d-c8a824b2fecd', 0, '3d7a484a-d0db-4118-96df-9b19fe66fbcd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e188bf52-f937-4e36-9875-ea72b1caf2d9', 'kuali.attribute.nonstd.ts.indicator', '0f621f74-b5aa-4c09-982d-c8a824b2fecd', 0, 'aa067cc3-11d2-45a0-9802-a60b7ba344d2')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6f3ad283-a099-47ed-9a62-4b06a2f453cb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'E', '', '', '', '0f621f74-b5aa-4c09-982d-c8a824b2fecd', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '26a58772-6660-4053-9267-1b12ac1ece91')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('b90e027e-5559-4f03-ac65-58ba6a7ecf5a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '0f621f74-b5aa-4c09-982d-c8a824b2fecd', '', 'kuali.lu.code.honorsOffering', 0, 'f6edaf37-abc3-4f76-b768-604439386a0a')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('b3eb37ed-ebee-431d-b1d9-26928e3e32b8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:00:56.055', '', '', '0f621f74-b5aa-4c09-982d-c8a824b2fecd', '', 'J.JRH', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '0757917e-f05b-46a7-98b7-0e06cb35c92e')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('3860cbfb-e8bb-4133-9d34-222d5029ed59', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:56.109', '', 'CHEM277-FO-AO', '82fd0b14-9cd0-444d-9033-f04144687f63', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM277-FO-AO', '0f621f74-b5aa-4c09-982d-c8a824b2fecd', '65a103d0-8005-436e-8336-a6568ea79a64')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('47aed103-b0f9-452c-97d1-7b1baf3bae08', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for CHEM277 - E', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '0d22c898-ff6c-4fd3-98f3-7dec4690e8e9')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('f0aec69f-b2b1-4f0e-a980-d3a43d879769', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule reqeust for CHEM277 - E', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '0d22c898-ff6c-4fd3-98f3-7dec4690e8e9', '5f0a62d7-d228-4e1a-8c90-c8dfd505d9fa')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('7bae91ca-fc22-4e22-99fa-fdfb132731f2', 0, '5f0a62d7-d228-4e1a-8c90-c8dfd505d9fa', '7c3d0571-366f-4d20-8dbf-47b420cda7a0')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('0d22c898-ff6c-4fd3-98f3-7dec4690e8e9', '0f621f74-b5aa-4c09-982d-c8a824b2fecd')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('7c3d0571-366f-4d20-8dbf-47b420cda7a0', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('7c3d0571-366f-4d20-8dbf-47b420cda7a0', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('7c3d0571-366f-4d20-8dbf-47b420cda7a0', '5fcaeba7-a010-40dc-a069-568c197f0809')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('7c3d0571-366f-4d20-8dbf-47b420cda7a0', 'F0AA72D6DBD9F650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('535312dd-f8a9-4b35-8b2c-88b3c2075c4d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, '', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '51af3080-f861-4ea4-83f2-9813b677494c')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('51af3080-f861-4ea4-83f2-9813b677494c', '0f621f74-b5aa-4c09-982d-c8a824b2fecd')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('51af3080-f861-4ea4-83f2-9813b677494c', '82fd0b14-9cd0-444d-9033-f04144687f63')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('036767c0-9412-45ec-9e4c-b70e30c73f50', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '6138a014-70d6-4716-a817-d096d4c79371', '', '', 'Courses with lab', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lab', 24, 24, 'Lab', 'Courses with lab', null, '1edeaa93-11a0-4313-a7ce-7bc2a5dced41')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e0791ad6-320e-46e2-9d54-c1df6bb607e0', 'kuali.attribute.max.enrollment.is.estimate', '1edeaa93-11a0-4313-a7ce-7bc2a5dced41', 0, 'f019ca65-a1ed-4284-a6dc-405dbd27e4f4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f188ca6b-9837-4674-aa90-9eca1efa684a', 'kuali.attribute.nonstd.ts.indicator', '1edeaa93-11a0-4313-a7ce-7bc2a5dced41', 0, 'c30344d9-5d5d-49ae-82f3-ff2e9a85aa6f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b6be7319-a2ad-4c23-a209-d6a4776fb762', 'kuali.attribute.course.evaluation.indicator', '1edeaa93-11a0-4313-a7ce-7bc2a5dced41', 0, '2f1c6ce3-9918-4962-aae0-daec3ae00de7')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('26da3902-f9dd-4d3c-8330-8b88d7a54286', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'F', '', '', '', '1edeaa93-11a0-4313-a7ce-7bc2a5dced41', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '0a9894af-21ea-4a8d-b4b1-ba0384747c5d')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('3af6546a-871e-4e10-8b1d-e73f4a992fbb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '1edeaa93-11a0-4313-a7ce-7bc2a5dced41', '', 'kuali.lu.code.honorsOffering', 0, '01d5cfa4-1c50-4a1d-a451-0871bc0a612b')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('5fab3ac0-27c7-4c8c-bde8-94ae425a8b3b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:00:56.63', '', '', '1edeaa93-11a0-4313-a7ce-7bc2a5dced41', '', 'J.JRH', 'kuali.lpr.state.tentative', 'kuali.lpr.type.instructor.main', '07cd81ab-1efc-4d9b-86ef-2f9265f5a3b0')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('321ba1dc-12fc-4394-bf9e-4a9c4b8b18f2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:56.661', '', 'CHEM277-FO-AO', '82fd0b14-9cd0-444d-9033-f04144687f63', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM277-FO-AO', '1edeaa93-11a0-4313-a7ce-7bc2a5dced41', 'b0b6d8b5-a87f-4c6b-85ef-8a9effb0e644')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('f358b54b-2430-453f-b5dc-6601b06affc0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for CHEM277 - F', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '5bb18b86-d6f1-435c-a15d-2028aba6b174')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('9e9c63d4-38ce-442e-9835-fd0083cbf7f9', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for CHEM277 - F', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '5bb18b86-d6f1-435c-a15d-2028aba6b174', '56b26220-b2bd-4a91-ae3a-32613fadf846')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('47f6a5f7-c151-4d54-adcd-c8b11426bbdd', 0, '56b26220-b2bd-4a91-ae3a-32613fadf846', 'a6d057d7-564e-4a33-8eaa-66821343c752')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('5bb18b86-d6f1-435c-a15d-2028aba6b174', '1edeaa93-11a0-4313-a7ce-7bc2a5dced41')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('a6d057d7-564e-4a33-8eaa-66821343c752', '5fcaeba7-a010-40dc-a069-568c197f0809')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('a6d057d7-564e-4a33-8eaa-66821343c752', 'F0AA72D6DBD9F650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('4282a3a8-45ca-44ed-9334-d9ca0a66c582', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, '', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'cf20069b-5438-4278-82dd-b340b261ea8f')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('cf20069b-5438-4278-82dd-b340b261ea8f', '1edeaa93-11a0-4313-a7ce-7bc2a5dced41')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('cf20069b-5438-4278-82dd-b340b261ea8f', '82fd0b14-9cd0-444d-9033-f04144687f63')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('59a1b981-4f21-4645-8cde-e73f87f7274c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '82fd0b14-9cd0-444d-9033-f04144687f63', 'CL 1 Conflict', 'CL 1 Conflict', '50f77eed-61c2-4b27-8cce-dab36a4b67cc')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('9814d7e3-3aca-4963-8d2f-0fe09cc48d90', 'kuali.lui.type.activity.offering.lab', '50f77eed-61c2-4b27-8cce-dab36a4b67cc', '1fc498c3-6dfe-44a1-8f12-5a59c5d1649e')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('1007b4aa-de4f-4b75-a2be-fe6b4eb9a169', 'kuali.lui.type.activity.offering.lecture', '50f77eed-61c2-4b27-8cce-dab36a4b67cc', '821ab558-467c-4e40-abfe-9beafae6e929')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('1fc498c3-6dfe-44a1-8f12-5a59c5d1649e', '1edeaa93-11a0-4313-a7ce-7bc2a5dced41')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('1fc498c3-6dfe-44a1-8f12-5a59c5d1649e', '0f621f74-b5aa-4c09-982d-c8a824b2fecd')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('821ab558-467c-4e40-abfe-9beafae6e929', 'b8cdf7a0-2033-49d5-94e1-410bbc8584d2')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='50f77eed-61c2-4b27-8cce-dab36a4b67cc' where ID='1fc498c3-6dfe-44a1-8f12-5a59c5d1649e'
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='50f77eed-61c2-4b27-8cce-dab36a4b67cc' where ID='821ab558-467c-4e40-abfe-9beafae6e929'
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('df9d0190-d20b-4c3b-8303-3e1fd4a7de54', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '82fd0b14-9cd0-444d-9033-f04144687f63', 'CL 1', 'CL 1', 'dcbba6cf-6a8a-4b23-a195-2f4f7d49fb7a')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('90f9daba-4280-4dc9-b8a1-68d90e3d4e25', 'kuali.lui.type.activity.offering.lab', 'dcbba6cf-6a8a-4b23-a195-2f4f7d49fb7a', 'cb5838cc-2a92-46cf-b789-24181f1a3a45')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('6de6b690-3a59-4484-a98b-d4d674d254ba', 'kuali.lui.type.activity.offering.lecture', 'dcbba6cf-6a8a-4b23-a195-2f4f7d49fb7a', 'de8a61e8-1187-4f12-92da-4cf5d8b85591')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('cb5838cc-2a92-46cf-b789-24181f1a3a45', '27b98013-2952-4251-ab1f-d0cc09630d79')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('cb5838cc-2a92-46cf-b789-24181f1a3a45', '883fe601-9f49-4bd6-8845-286893ef0d15')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('de8a61e8-1187-4f12-92da-4cf5d8b85591', 'ce9556d2-9c91-413d-8742-73d2ebaab140')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='dcbba6cf-6a8a-4b23-a195-2f4f7d49fb7a' where ID='cb5838cc-2a92-46cf-b789-24181f1a3a45'
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='dcbba6cf-6a8a-4b23-a195-2f4f7d49fb7a' where ID='de8a61e8-1187-4f12-92da-4cf5d8b85591'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('127a4bce-7c05-477d-a8a9-86e9710f7185', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '25889d4f-0612-424c-87a5-e96f60f3e096', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'abd7b255-e0e4-4ee1-b522-1762307ead4f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e6be7bf4-9d83-4bdd-a9e8-9b1ed7fb6a04', 'kuali.attribute.registration.group.is.generated', 'abd7b255-e0e4-4ee1-b522-1762307ead4f', 1, '794a0704-9789-42b6-a347-9c748cd16e2f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('65ee7c11-c621-41e9-a41b-7c7994d28144', 'kuali.attribute.registration.group.aocluster.id', 'abd7b255-e0e4-4ee1-b522-1762307ead4f', '50f77eed-61c2-4b27-8cce-dab36a4b67cc', 'e781a575-b053-4fd6-9a60-878a9b79fab6')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('35b79784-b59d-4ce6-bc21-86b4e299743c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'abd7b255-e0e4-4ee1-b522-1762307ead4f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c32cf0bf-1755-471d-93a6-ce9d29a8494d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('bd45e69f-b015-4730-ad66-abf42cf7c2d0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:57.404', '', 'CHEM277-FO-RG', '82fd0b14-9cd0-444d-9033-f04144687f63', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM277-FO-RG', 'abd7b255-e0e4-4ee1-b522-1762307ead4f', 'dfc75724-2420-4e63-a329-d01cf837261b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('eec6374f-c525-45b7-9b31-33b7d0846192', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:57.49', '', 'CHEM277-RG-AO', 'abd7b255-e0e4-4ee1-b522-1762307ead4f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM277-RG-AO', 'b8cdf7a0-2033-49d5-94e1-410bbc8584d2', 'eb08e956-7f3c-4843-a024-31814c333663')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('5a496ba8-3c5b-4b1a-af6d-200218eb16c1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:57.507', '', 'CHEM277-RG-AO', 'abd7b255-e0e4-4ee1-b522-1762307ead4f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM277-RG-AO', '0f621f74-b5aa-4c09-982d-c8a824b2fecd', '3bdf642a-4a3f-4738-b559-634437fb0482')
/
update KSEN_LUI set OBJ_ID='127a4bce-7c05-477d-a8a9-86e9710f7185', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ATP_ID='kuali.atp.2014Winter', CLU_ID='25889d4f-0612-424c-87a5-e96f60f3e096', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1001', LUI_STATE='kuali.lui.registration.group.state.invalid', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1001', DESCR_PLAIN='1001', REF_URL='' where ID='abd7b255-e0e4-4ee1-b522-1762307ead4f' and VER_NBR=0
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('135e3fac-a317-4d59-a5dc-468d07cfb6e2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '25889d4f-0612-424c-87a5-e96f60f3e096', '', '', '1002', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1002', '1002', '', 'b0e6988b-f412-4bf0-9fca-e349ad25dd0f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9780f802-b208-4dd8-9595-7c96041e4f16', 'kuali.attribute.registration.group.aocluster.id', 'b0e6988b-f412-4bf0-9fca-e349ad25dd0f', '50f77eed-61c2-4b27-8cce-dab36a4b67cc', '4095ec2c-fecc-4668-8d2e-199c8279ab62')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('74cbde58-9257-4212-8da3-357012461e8c', 'kuali.attribute.registration.group.is.generated', 'b0e6988b-f412-4bf0-9fca-e349ad25dd0f', 1, 'c34ea57d-1831-4018-96c9-510f88f0f1e9')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('2fd9729d-c363-401f-9389-a860301b0688', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'b0e6988b-f412-4bf0-9fca-e349ad25dd0f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '9577e414-34b3-449c-912c-1a3b1726131c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a223d457-c7f1-439e-89ee-82324d40ec94', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:57.819', '', 'CHEM277-FO-RG', '82fd0b14-9cd0-444d-9033-f04144687f63', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM277-FO-RG', 'b0e6988b-f412-4bf0-9fca-e349ad25dd0f', '40606d04-9043-4f3e-a32b-f4c520e51983')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('341f2a20-c790-4f18-a081-c00eeb2c5db5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:57.839', '', 'CHEM277-RG-AO', 'b0e6988b-f412-4bf0-9fca-e349ad25dd0f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM277-RG-AO', 'b8cdf7a0-2033-49d5-94e1-410bbc8584d2', '5f9851b6-eadb-483a-8776-3ba27eae0202')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('0b8d31fe-7ec1-41ef-89ac-846980e6bcd0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:57.854', '', 'CHEM277-RG-AO', 'b0e6988b-f412-4bf0-9fca-e349ad25dd0f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM277-RG-AO', '1edeaa93-11a0-4313-a7ce-7bc2a5dced41', '66167ddb-3b78-4fba-aa8a-4ceda5d74706')
/
update KSEN_LUI set OBJ_ID='135e3fac-a317-4d59-a5dc-468d07cfb6e2', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ATP_ID='kuali.atp.2014Winter', CLU_ID='25889d4f-0612-424c-87a5-e96f60f3e096', EFF_DT='', EXPIR_DT='', DESCR_FORMATTED='1002', LUI_STATE='kuali.lui.registration.group.state.invalid', LUI_TYPE='kuali.lui.type.registration.group', MAX_SEATS='', MIN_SEATS='', NAME='1002', DESCR_PLAIN='1002', REF_URL='' where ID='b0e6988b-f412-4bf0-9fca-e349ad25dd0f' and VER_NBR=0
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('636ae58b-ab9a-4a33-a01a-b886a29b9dfa', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '25889d4f-0612-424c-87a5-e96f60f3e096', '', '', '1003', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1003', '1003', '', '3c50aad5-67be-46b4-812a-580287f832b2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9ed717e4-01e6-4f38-9612-05b9ce767514', 'kuali.attribute.registration.group.aocluster.id', '3c50aad5-67be-46b4-812a-580287f832b2', 'dcbba6cf-6a8a-4b23-a195-2f4f7d49fb7a', '59d45fe5-1c15-4284-bc30-ebd78e7aa9d2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('edd34040-a598-44bc-884a-acbe3689a32b', 'kuali.attribute.registration.group.is.generated', '3c50aad5-67be-46b4-812a-580287f832b2', 1, 'e9cb79ec-6c1c-461f-9a49-da8df089dd55')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('191c8a34-2534-4ac5-9a7a-701aaacabb96', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '3c50aad5-67be-46b4-812a-580287f832b2', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '343cd00e-9481-455d-80a1-be384bb57eb8')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('20d2cd7e-f24f-43a2-8656-7d0e5885da7c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:58.02', '', 'CHEM277-FO-RG', '82fd0b14-9cd0-444d-9033-f04144687f63', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM277-FO-RG', '3c50aad5-67be-46b4-812a-580287f832b2', '83514e08-a87b-4d54-a2aa-90ec6535775a')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a205271b-674e-4735-8a69-82422d8ad8e6', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:58.038', '', 'CHEM277-RG-AO', '3c50aad5-67be-46b4-812a-580287f832b2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM277-RG-AO', 'ce9556d2-9c91-413d-8742-73d2ebaab140', '594a423e-f9cf-4dcc-ad90-6a99939a0371')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('5b9ee2ca-3446-4d95-9836-a7a65ddf33d4', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:58.049', '', 'CHEM277-RG-AO', '3c50aad5-67be-46b4-812a-580287f832b2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM277-RG-AO', '883fe601-9f49-4bd6-8845-286893ef0d15', 'c779e9ad-6dcc-4282-a0b6-90e2f3c211d3')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('20fcadf1-0825-446b-8cf6-82f035b1b1d2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '25889d4f-0612-424c-87a5-e96f60f3e096', '', '', '1004', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1004', '1004', '', '84173768-ce75-45c2-bf29-5205a93523bf')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('342cdef7-26a9-4ba2-b44f-5d5d658ceb66', 'kuali.attribute.registration.group.aocluster.id', '84173768-ce75-45c2-bf29-5205a93523bf', 'dcbba6cf-6a8a-4b23-a195-2f4f7d49fb7a', 'a821d78c-f85c-48ca-ac5a-17b744993165')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7412484e-8a61-4cec-88c2-a26d4e2e8176', 'kuali.attribute.registration.group.is.generated', '84173768-ce75-45c2-bf29-5205a93523bf', 1, '151310e8-501f-47fb-9fed-5d6f3c7a0a45')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('fb92e274-3925-4d74-8226-ae6608fdb813', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '84173768-ce75-45c2-bf29-5205a93523bf', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'fdcdf949-afcf-48bd-b98c-c06665885491')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('caae51db-c7ad-4bf6-9816-5c75ef17e55a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:58.169', '', 'CHEM277-FO-RG', '82fd0b14-9cd0-444d-9033-f04144687f63', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM277-FO-RG', '84173768-ce75-45c2-bf29-5205a93523bf', '7489cbf1-d821-4647-9330-69e843c7efb1')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e1c4774b-2df4-4e0a-8e3e-028c5c84d70d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:58.182', '', 'CHEM277-RG-AO', '84173768-ce75-45c2-bf29-5205a93523bf', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM277-RG-AO', 'ce9556d2-9c91-413d-8742-73d2ebaab140', 'a96ad352-4a24-4dd7-b3bd-0f95d86c87c8')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('93fd9bbf-ec12-4a2f-9d35-1d3b845e143c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:00:58.186', '', 'CHEM277-RG-AO', '84173768-ce75-45c2-bf29-5205a93523bf', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM277-RG-AO', '27b98013-2952-4251-ab1f-d0cc09630d79', '28c3efde-3608-43a9-93c7-0ef6fe5366fc')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('98ed1601-afd2-4f70-9355-06cf4cd49e62', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'CLUID-ENGL329-201008000000', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Studies in various periods and genres of film.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL329B CO', 'Studies in various periods and genres of film.', null, 'd4a00c7a-b5dd-444d-919b-b987bee58745')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d31a9bbf-1886-4ade-9168-bb149f68b12a', 'kuali.attribute.wait.list.indicator', 'd4a00c7a-b5dd-444d-919b-b987bee58745', 1, '604a5a1f-d06e-4297-bda4-dc3f1140f23e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('36ce2c51-0257-418a-b7bd-7b04f0825b3a', 'kuali.attribute.final.exam.driver', 'd4a00c7a-b5dd-444d-919b-b987bee58745', 'kuali.lu.exam.driver.ActivityOffering', '9f1c2121-3587-487e-9837-f37002f6f0ce')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bc5aeab4-f3c5-446a-b272-edfc72c2733a', 'kuali.attribute.final.exam.indicator', 'd4a00c7a-b5dd-444d-919b-b987bee58745', 'STANDARD', '9e724930-0d66-42e1-a69e-b456fbb77b76')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dd694135-c8d0-4db8-a881-25c9293d69d4', 'kuali.attribute.course.evaluation.indicator', 'd4a00c7a-b5dd-444d-919b-b987bee58745', 0, '4ad187cf-88d3-4459-afc6-950e2c83e18d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('56e82559-a36b-460d-9ac0-3a33765d43d3', 'kuali.attribute.final.exam.use.matrix', 'd4a00c7a-b5dd-444d-919b-b987bee58745', 1, '28b7cf24-55ce-4b69-81aa-2e27badc173a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2e015b4d-b29e-48d9-aa91-cc24096493ea', 'kuali.attribute.course.number.internal.suffix', 'd4a00c7a-b5dd-444d-919b-b987bee58745', 'A', '768fbf32-dd47-4071-9553-017ce02ff519')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8c565428-00b0-4518-9398-fb97fe8909c3', 'kuali.attribute.funding.source', 'd4a00c7a-b5dd-444d-919b-b987bee58745', '', '43a02747-057d-464c-9ecd-b8e295a0a093')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f2fa45bd-2a69-4912-8649-72100d18380f', 'kuali.attribute.where.fees.attached.flag', 'd4a00c7a-b5dd-444d-919b-b987bee58745', 0, '53d0245d-8786-4ce8-ae5f-c04006747fc7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b08ede61-5c8b-4c4e-8fcb-b86ae4dbe2ae', 'kuali.attribute.wait.list.type.key', 'd4a00c7a-b5dd-444d-919b-b987bee58745', null, 'a9e9dba0-39cf-4f5f-902f-fc8e645fb749')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b5669207-aab9-4969-a46b-76a69dd7f759', 'kuali.attribute.wait.list.level.type.key', 'd4a00c7a-b5dd-444d-919b-b987bee58745', 'AO', 'c1b13e9a-3f75-4226-8c43-2ebcc3694bd2')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7b761401-1ad0-4e50-b50e-cf068ca0105c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'ENGL329B', 'ENGL', '', 'Special Topics in Film Studies', 'd4a00c7a-b5dd-444d-919b-b987bee58745', '', '', 'kuali.lui.identifier.state.active', 'B', 'kuali.lui.identifier.type.official', '', '79388747-c4fe-4ab0-b2b0-7b4d0b4a3f7f')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('bcb5ddaa-1b8b-40a8-ab3c-87129676b567', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'd4a00c7a-b5dd-444d-919b-b987bee58745', '', 'kuali.lu.code.honorsOffering', '', 'f0aee6d4-bd49-44e6-9a06-b7aeb63789a5')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('d4a00c7a-b5dd-444d-919b-b987bee58745', '2677933260', 'fd61b79b-1ca9-48bf-ad64-ddbbb47d3b39')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('d4a00c7a-b5dd-444d-919b-b987bee58745', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('d4a00c7a-b5dd-444d-919b-b987bee58745', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('d4a00c7a-b5dd-444d-919b-b987bee58745', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('d4a00c7a-b5dd-444d-919b-b987bee58745', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('d4a00c7a-b5dd-444d-919b-b987bee58745', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('52633d25-10a0-4b25-aeda-f3e8a0dc1ed0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '190b475a-475d-4246-a23d-6e260996a916', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '9254f3e3-da09-40ba-8ff2-d8d93779fb2d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ce2120d4-4760-4639-846f-2d8231365cc2', 'kuali.attribute.final.exam.level.type', '9254f3e3-da09-40ba-8ff2-d8d93779fb2d', 'kuali.lu.type.activity.Lecture', '86b9298e-dd14-4240-aceb-865b4c4ddf10')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6f280186-0499-4075-a41a-296d6dbf5694', 'regCodePrefix', '9254f3e3-da09-40ba-8ff2-d8d93779fb2d', '1', '550e2856-96f9-4929-a1be-ca1148311396')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('81007241-8520-42ae-b4a2-9daccff01dc6', 'kuali.attribute.grade.roster.level.type.key', '9254f3e3-da09-40ba-8ff2-d8d93779fb2d', 'kuali.lui.type.activity.offering.lecture', '61a98aa4-caf0-483e-a217-cec44f68ce9b')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d447e52f-efc4-4ec5-8434-227217953401', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '9254f3e3-da09-40ba-8ff2-d8d93779fb2d', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '265a435a-1c81-400f-be18-9d8b607bb5c1')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('9254f3e3-da09-40ba-8ff2-d8d93779fb2d', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e718c779-3bc2-4aff-96f4-c112c0cecfae', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:01.406', '', 'ENGL329B-CO-FO', 'd4a00c7a-b5dd-444d-919b-b987bee58745', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL329B-CO-FO', '9254f3e3-da09-40ba-8ff2-d8d93779fb2d', '0e290675-9c77-49a3-a8b1-bc86ee94371b')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('2f7ff3cb-e60f-4714-870e-9faf41ac0af6', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '4134e3cc-9ba5-47e0-a834-99ae05582b4f', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, '41b4c0be-164d-468e-a5de-ac2b756ee5de')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ae10ac27-e22e-42ef-8f3d-48fda3d5627e', 'kuali.attribute.max.enrollment.is.estimate', '41b4c0be-164d-468e-a5de-ac2b756ee5de', 0, 'cb27e4cd-3e8f-4adc-96dc-fbd93a9263ea')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('08059ef6-5c3f-4c70-aea9-01c0cb7c2e20', 'kuali.attribute.nonstd.ts.indicator', '41b4c0be-164d-468e-a5de-ac2b756ee5de', 0, '57822d79-eb14-43c0-8583-375e2e10304d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d499ac1f-8b73-4279-a333-3d996c4ad078', 'kuali.attribute.course.evaluation.indicator', '41b4c0be-164d-468e-a5de-ac2b756ee5de', 0, 'f21b5f00-1447-452d-b807-a4de8453df8f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('05ebf55c-8261-411d-98d7-eb25de628f39', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '41b4c0be-164d-468e-a5de-ac2b756ee5de', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '886efdad-7ded-41fd-bdbc-b2690c5f74f6')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('878f2428-81f0-4914-a8a7-a017899b270a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '41b4c0be-164d-468e-a5de-ac2b756ee5de', '', 'kuali.lu.code.honorsOffering', 0, 'fa269841-a355-497c-8670-6d1609f1f09e')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('8a1e4765-3b96-43a6-9b82-f6dd8cb104e6', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:01:01.679', '', '', '41b4c0be-164d-468e-a5de-ac2b756ee5de', '', 'C.GUOZHONGC', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'cd879863-fc26-4d2d-953c-f256c00d24f6')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('98946b2b-31cf-43ff-875f-d3dd4f15e347', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:01.774', '', 'ENGL329B-FO-AO', '9254f3e3-da09-40ba-8ff2-d8d93779fb2d', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL329B-FO-AO', '41b4c0be-164d-468e-a5de-ac2b756ee5de', 'a07b3d43-7089-45e8-9eef-a5c38f3273d9')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('6b9ebc60-e817-4873-8984-1968bf66e173', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for ENGL329B - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '667dcf9a-535c-4ba5-9c35-6bee1da1db58')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('9d107f8a-516a-4588-bde5-b757881de797', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for ENGL329B - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '667dcf9a-535c-4ba5-9c35-6bee1da1db58', 'a16f85ff-92d6-4051-b204-1c63ad1b6e98')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('10100b94-c8d5-4564-b2ff-f1555a7d7f32', 0, 'a16f85ff-92d6-4051-b204-1c63ad1b6e98', '7aea5de6-4afa-4e06-8515-74cba964f7d3')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('667dcf9a-535c-4ba5-9c35-6bee1da1db58', '41b4c0be-164d-468e-a5de-ac2b756ee5de')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('7aea5de6-4afa-4e06-8515-74cba964f7d3', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('7aea5de6-4afa-4e06-8515-74cba964f7d3', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('7aea5de6-4afa-4e06-8515-74cba964f7d3', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('7aea5de6-4afa-4e06-8515-74cba964f7d3', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('008ba99c-042c-43db-885c-6d88ae607c1f', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '6fb4b8eb-da11-4f0f-8417-5960aaecfd23')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('decb304e-3e86-4227-8dcd-a34ebed298c9', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'a032481b-6714-400d-ad1c-e167e7b059bf')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='008ba99c-042c-43db-885c-6d88ae607c1f', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='41b4c0be-164d-468e-a5de-ac2b756ee5de', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='6fb4b8eb-da11-4f0f-8417-5960aaecfd23' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('a032481b-6714-400d-ad1c-e167e7b059bf', '41b4c0be-164d-468e-a5de-ac2b756ee5de')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('a032481b-6714-400d-ad1c-e167e7b059bf', '9254f3e3-da09-40ba-8ff2-d8d93779fb2d')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('7949eedc-9f9b-448e-9419-4e0a89d0e6da', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '9254f3e3-da09-40ba-8ff2-d8d93779fb2d', 'CL 1', 'CL 1', 'd3bdec6c-add4-4582-9ef8-4457b07f4480')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('135b8b57-8fdc-4eeb-900f-280877657d60', 'kuali.lui.type.activity.offering.lecture', 'd3bdec6c-add4-4582-9ef8-4457b07f4480', 'a452e388-975b-4e9d-bc0d-ea5a04facc02')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('a452e388-975b-4e9d-bc0d-ea5a04facc02', '41b4c0be-164d-468e-a5de-ac2b756ee5de')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='d3bdec6c-add4-4582-9ef8-4457b07f4480' where ID='a452e388-975b-4e9d-bc0d-ea5a04facc02'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('a56145a4-fb29-4836-8c15-2e8edfc813c2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '190b475a-475d-4246-a23d-6e260996a916', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '15326096-3f5f-4d59-a5ba-4e14ff127330')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d7f100b9-7b33-4adb-8922-3d266c127365', 'kuali.attribute.registration.group.is.generated', '15326096-3f5f-4d59-a5ba-4e14ff127330', 1, '539390a7-c10f-4e0a-96cb-7090d5ac6d3a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('58c9b493-6951-4f5f-bbe5-02bfb5f897cc', 'kuali.attribute.registration.group.aocluster.id', '15326096-3f5f-4d59-a5ba-4e14ff127330', 'd3bdec6c-add4-4582-9ef8-4457b07f4480', '550ae375-b4e0-4baa-81ab-1c977b8e7958')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('42daeda0-a1cd-4b17-85b8-99a0361b184e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '15326096-3f5f-4d59-a5ba-4e14ff127330', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '893ed666-93d4-45cb-9f29-121ecff66b2a')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('1d2bab40-0fe9-4c61-91a3-49e1778efd41', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:02.368', '', 'ENGL329B-FO-RG', '9254f3e3-da09-40ba-8ff2-d8d93779fb2d', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL329B-FO-RG', '15326096-3f5f-4d59-a5ba-4e14ff127330', 'a31fa5a4-01f1-49b3-be78-39286ccf314e')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('39e66fa1-d56c-40b7-b152-b3c782170c3b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:02.373', '', 'ENGL329B-RG-AO', '15326096-3f5f-4d59-a5ba-4e14ff127330', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL329B-RG-AO', '41b4c0be-164d-468e-a5de-ac2b756ee5de', 'b75c1a1b-3f4d-4688-9c90-d7b133d9a286')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('31ef3c80-a2ff-4f61-a8cd-d022b0ed36fb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '88e00da8-c4df-42b6-be3b-51ad25a919f3', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Surveys American writing from the Civil War through the Cold War. Authors such as Clemens, Frost, Hurston, Bellow.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL222 CO', 'Surveys American writing from the Civil War through the Cold War. Authors such as Clemens, Frost, Hurston, Bellow.', null, '508ac76d-401e-4326-87df-7c99baf7bd79')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e3fbf27d-455a-4ad0-b64f-41ebfd7bde34', 'kuali.attribute.wait.list.level.type.key', '508ac76d-401e-4326-87df-7c99baf7bd79', 'AO', '1249ae2f-5b43-40d5-beac-00e81ce6fd86')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('47ea56ea-6baa-4984-92f8-007104a3d61b', 'kuali.attribute.final.exam.use.matrix', '508ac76d-401e-4326-87df-7c99baf7bd79', 1, '2be9ee01-2ac2-471c-83e2-021e2b5db64b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('51650649-d31a-4000-80dc-43f430da827a', 'kuali.attribute.wait.list.type.key', '508ac76d-401e-4326-87df-7c99baf7bd79', null, '4650fbe2-955f-4cf8-bf14-b90870c01eff')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('49319eec-59a4-458a-9efa-d8304bef0e3a', 'kuali.attribute.funding.source', '508ac76d-401e-4326-87df-7c99baf7bd79', '', 'c99cf36a-02f3-4436-9060-640c33d7f693')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('98476f6b-5ddf-47f4-a87a-588a342358ca', 'kuali.attribute.final.exam.driver', '508ac76d-401e-4326-87df-7c99baf7bd79', 'kuali.lu.exam.driver.ActivityOffering', 'da14cfd2-00bd-43b1-a980-513e2e4bedba')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a3377387-ef71-424c-8cfa-07e767648f5a', 'kuali.attribute.course.evaluation.indicator', '508ac76d-401e-4326-87df-7c99baf7bd79', 0, 'de52d9c6-4758-48f5-bb8f-c8c571870e60')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('63e9fdae-3394-4bc5-b885-82443025a282', 'kuali.attribute.wait.list.indicator', '508ac76d-401e-4326-87df-7c99baf7bd79', 1, '9d5d5079-ac8c-46be-9056-092b97dc825f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5119b5e9-800c-48b3-9d2b-9f934542e8cc', 'kuali.attribute.where.fees.attached.flag', '508ac76d-401e-4326-87df-7c99baf7bd79', 0, '15cd5793-a937-47b3-9d94-b9870c529970')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('702f69ca-7e3c-45fd-990c-5447f0ad0517', 'kuali.attribute.final.exam.indicator', '508ac76d-401e-4326-87df-7c99baf7bd79', 'STANDARD', '5cbe3eaf-d17c-44da-bd5b-92c19a5491ae')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bea54690-ffc2-4465-b8ec-d0bad3f78385', 'kuali.attribute.course.number.internal.suffix', '508ac76d-401e-4326-87df-7c99baf7bd79', 'A', '3ed8bae3-5795-4b21-a18e-53373b101478')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5839a2c2-b603-445a-a0eb-b8561199cd09', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'ENGL222', 'ENGL', '', 'American Literature: 1865 to Present', '508ac76d-401e-4326-87df-7c99baf7bd79', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'de0df878-6614-4e72-9804-6a9797b25055')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('94168ddf-d46f-441b-8525-17a02ee25861', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '508ac76d-401e-4326-87df-7c99baf7bd79', '', 'kuali.lu.code.honorsOffering', '', '9e8bdafd-737b-445c-9c49-d908bc9dbc01')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('508ac76d-401e-4326-87df-7c99baf7bd79', '2677933260', '423af563-8ee4-4367-af81-b9228ba47ede')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('508ac76d-401e-4326-87df-7c99baf7bd79', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('508ac76d-401e-4326-87df-7c99baf7bd79', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('508ac76d-401e-4326-87df-7c99baf7bd79', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('508ac76d-401e-4326-87df-7c99baf7bd79', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('508ac76d-401e-4326-87df-7c99baf7bd79', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('7bbab8a5-dca5-49f7-8607-6cd83b640bf3', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'db9a6986-9b28-47e9-b1f1-9fdb5f4562df', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '0c4cc8d5-364f-4fc4-8338-11d278e53ae0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5f08a1c5-7675-4d24-8c6f-1b77f1754aa9', 'kuali.attribute.grade.roster.level.type.key', '0c4cc8d5-364f-4fc4-8338-11d278e53ae0', 'kuali.lui.type.activity.offering.lecture', '4cb1a2a6-425f-4541-a391-38ba39f57a44')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('51b70547-ddd5-4f58-95e9-acb7f849ccab', 'kuali.attribute.final.exam.level.type', '0c4cc8d5-364f-4fc4-8338-11d278e53ae0', 'kuali.lu.type.activity.Lecture', '4773ba71-3964-4bbc-964c-ecadf149025d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('434b0d9b-3d54-4087-9ee9-3882ec04646a', 'regCodePrefix', '0c4cc8d5-364f-4fc4-8338-11d278e53ae0', '1', '203035aa-41bb-49da-8d6b-203203ecea97')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('55674e85-5aa4-4b36-b1fe-43aeea90220f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '0c4cc8d5-364f-4fc4-8338-11d278e53ae0', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'f8878e74-1c3f-46cc-b281-4173232e5cb5')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('0c4cc8d5-364f-4fc4-8338-11d278e53ae0', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('4ddd967a-83c0-4672-b4e8-6e88f9862229', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:06.459', '', 'ENGL222-CO-FO', '508ac76d-401e-4326-87df-7c99baf7bd79', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL222-CO-FO', '0c4cc8d5-364f-4fc4-8338-11d278e53ae0', 'b66f4c26-fded-451a-b9ef-b1b94665b181')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('63657d45-de18-4ea7-b7cc-c7ad48c65c6d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '9194e508-3a2b-4429-9a62-852dfc612977', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, 'dc6a3f7e-2b05-44dd-8c0a-d59a75d45e14')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('506be6df-cd56-40b7-9e79-87791a0b8006', 'kuali.attribute.nonstd.ts.indicator', 'dc6a3f7e-2b05-44dd-8c0a-d59a75d45e14', 0, '780d920c-13bd-4b0e-bbbb-d56b4b6a2a0f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ff6cd718-c5fa-43a9-a7fd-2c49b47e9976', 'kuali.attribute.max.enrollment.is.estimate', 'dc6a3f7e-2b05-44dd-8c0a-d59a75d45e14', 0, '0f310c84-8a3c-4233-9b25-0ee83b2b397b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b4838b6e-6184-419d-8733-f45fd88f456a', 'kuali.attribute.course.evaluation.indicator', 'dc6a3f7e-2b05-44dd-8c0a-d59a75d45e14', 0, '63bde2f9-cfe6-43cb-a5ef-e8f77192d86c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('edd6551b-7fc0-4aae-840d-99df1ff4cae4', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', 'dc6a3f7e-2b05-44dd-8c0a-d59a75d45e14', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c43ab5ca-a24d-4364-ba71-d6038164a998')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('a1815593-51de-4f06-943b-1f0ff8c05cff', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'dc6a3f7e-2b05-44dd-8c0a-d59a75d45e14', '', 'kuali.lu.code.honorsOffering', 0, '70cd701a-fea3-4f39-a560-1e09e6219949')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('5a23e73a-c1df-4b81-b20f-a64bd3917684', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:01:06.58', '', '', 'dc6a3f7e-2b05-44dd-8c0a-d59a75d45e14', '', 'R.BRADLEYM', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '613bcd6a-ab17-479d-ac8b-17d83c531be2')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e0db0d9d-3692-49f6-86b4-1c086ccad539', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:06.612', '', 'ENGL222-FO-AO', '0c4cc8d5-364f-4fc4-8338-11d278e53ae0', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL222-FO-AO', 'dc6a3f7e-2b05-44dd-8c0a-d59a75d45e14', 'f0806305-54b1-48da-a159-9dae85e79d63')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('973f27e4-b9a3-4419-864f-0d76d73ecada', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for ENGL222 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'f9bd47f6-b092-47e2-855f-f50021e78206')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('d1528280-11c3-4f32-851e-4c897f8f836d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for ENGL222 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'f9bd47f6-b092-47e2-855f-f50021e78206', 'db24872f-9954-4145-b714-6623b7c992c3')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('2fd00916-a484-4143-87b0-71eae046b699', 0, 'db24872f-9954-4145-b714-6623b7c992c3', 'f5e11b70-af29-4868-9bd8-5157a677df4d')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('f9bd47f6-b092-47e2-855f-f50021e78206', 'dc6a3f7e-2b05-44dd-8c0a-d59a75d45e14')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('f5e11b70-af29-4868-9bd8-5157a677df4d', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('f5e11b70-af29-4868-9bd8-5157a677df4d', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('f5e11b70-af29-4868-9bd8-5157a677df4d', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('f5e11b70-af29-4868-9bd8-5157a677df4d', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('0421b5f0-d0f7-4914-b19b-ec94217e52b9', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '0b5d86c5-a809-4c5c-bfb5-2cfce445e98f')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('0b5d86c5-a809-4c5c-bfb5-2cfce445e98f', 'dc6a3f7e-2b05-44dd-8c0a-d59a75d45e14')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('0b5d86c5-a809-4c5c-bfb5-2cfce445e98f', '0c4cc8d5-364f-4fc4-8338-11d278e53ae0')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('d33e8903-9602-417f-aa8c-4383dd049c0b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '0c4cc8d5-364f-4fc4-8338-11d278e53ae0', 'CL 1', 'CL 1', '429d0c69-4d30-4ca5-9ccb-7ee916b5c9e4')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('ad7382e3-fe32-40e8-b9be-1a05894d563a', 'kuali.lui.type.activity.offering.lecture', '429d0c69-4d30-4ca5-9ccb-7ee916b5c9e4', '46e0422c-b8a6-46e4-ab21-0bc23f8b5957')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('46e0422c-b8a6-46e4-ab21-0bc23f8b5957', 'dc6a3f7e-2b05-44dd-8c0a-d59a75d45e14')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='429d0c69-4d30-4ca5-9ccb-7ee916b5c9e4' where ID='46e0422c-b8a6-46e4-ab21-0bc23f8b5957'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('c95841ed-9746-43f4-82d2-3f04d3b8af07', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'db9a6986-9b28-47e9-b1f1-9fdb5f4562df', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'ac809973-d500-4b3a-8094-442d06d67a77')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4667de92-7036-4178-aead-f2d6e6ca8a45', 'kuali.attribute.registration.group.is.generated', 'ac809973-d500-4b3a-8094-442d06d67a77', 1, '57e008de-3a32-400b-88da-96800f6d6505')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('68599bfe-b26d-47e3-bb61-c64d9df7efcd', 'kuali.attribute.registration.group.aocluster.id', 'ac809973-d500-4b3a-8094-442d06d67a77', '429d0c69-4d30-4ca5-9ccb-7ee916b5c9e4', '50039ebb-678c-4751-9a68-b50bf036374d')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('41d50d9f-21db-4e35-a5fd-1b40fc3d2f5c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'ac809973-d500-4b3a-8094-442d06d67a77', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '67968013-2648-4628-95ea-baab67c45884')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e66b0437-eb4d-4074-96af-e009ac8c571b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:07.164', '', 'ENGL222-FO-RG', '0c4cc8d5-364f-4fc4-8338-11d278e53ae0', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL222-FO-RG', 'ac809973-d500-4b3a-8094-442d06d67a77', '28954a99-2f71-44d7-980f-d0ee32ab0b71')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('eafede68-47ac-4b44-b3bb-029f689e4893', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:07.182', '', 'ENGL222-RG-AO', 'ac809973-d500-4b3a-8094-442d06d67a77', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL222-RG-AO', 'dc6a3f7e-2b05-44dd-8c0a-d59a75d45e14', '0ebcdc9e-17cb-44cc-b34d-1e80c010aa55')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('4de7f9a5-2d4b-4e5d-9946-fe0de2a9b739', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '974c7253-0198-40ab-9e50-35a5ba2f98e5', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Special topics course taken as part of an approved study abroad program', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL369D CO', 'Special topics course taken as part of an approved study abroad program', null, 'f5a67618-4532-4f2f-aa0b-541a19f3f9a2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('00b84784-4520-48b1-9e2e-e917a028c915', 'kuali.attribute.wait.list.type.key', 'f5a67618-4532-4f2f-aa0b-541a19f3f9a2', null, 'e7b1a38f-054d-41d3-abd0-2d3e71f9f638')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4ebb27db-9830-43b8-b4e5-5b3b9d403d44', 'kuali.attribute.final.exam.driver', 'f5a67618-4532-4f2f-aa0b-541a19f3f9a2', 'kuali.lu.exam.driver.ActivityOffering', 'c9f944a9-fcd4-4783-bfd0-8aabfe83d8a0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('23306a86-4f21-4196-9758-8b679b2f25d0', 'kuali.attribute.wait.list.indicator', 'f5a67618-4532-4f2f-aa0b-541a19f3f9a2', 1, 'c5908c72-2348-4207-8985-a87269b7b467')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0d979249-82d1-403b-80d5-489d71c1b86f', 'kuali.attribute.funding.source', 'f5a67618-4532-4f2f-aa0b-541a19f3f9a2', '', 'e5ceddc6-d27b-4121-b82f-f4faaa401ef0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6a5d91a2-4545-4c63-a3f3-ff84cc0bdc63', 'kuali.attribute.final.exam.indicator', 'f5a67618-4532-4f2f-aa0b-541a19f3f9a2', 'STANDARD', '81b2448a-5714-4b65-8adf-36d977805c3e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('45e8d88e-a025-48e6-8fac-0b67be1b51c6', 'kuali.attribute.course.number.internal.suffix', 'f5a67618-4532-4f2f-aa0b-541a19f3f9a2', 'A', 'f82bd129-7c94-4883-9d6d-6f774e980815')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('be5c3d13-68b3-4850-a7af-4243d646cfe3', 'kuali.attribute.wait.list.level.type.key', 'f5a67618-4532-4f2f-aa0b-541a19f3f9a2', 'AO', 'd09e94e4-a7ce-44a2-b364-1d2bcceb4cc9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c9166aff-f627-4ef3-a752-4c01dc706700', 'kuali.attribute.course.evaluation.indicator', 'f5a67618-4532-4f2f-aa0b-541a19f3f9a2', 0, 'e8efac71-6768-4c7c-9fd8-b6a34074e251')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ff1c8e16-fc51-4f63-9fbf-c98b646b4aa6', 'kuali.attribute.where.fees.attached.flag', 'f5a67618-4532-4f2f-aa0b-541a19f3f9a2', 0, '8b228ee1-5112-4347-a16a-0fc639161009')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5295904b-f0e5-44b8-8329-96e59fef5718', 'kuali.attribute.final.exam.use.matrix', 'f5a67618-4532-4f2f-aa0b-541a19f3f9a2', 1, '15d54725-e0b6-4565-861b-1abe6a7f2ba0')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('e4926a44-6e48-40a3-be18-5f729afa8c09', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'ENGL369D', 'ENGL', '', 'Special Topics in Study Abroad III', 'f5a67618-4532-4f2f-aa0b-541a19f3f9a2', '', '', 'kuali.lui.identifier.state.active', 'D', 'kuali.lui.identifier.type.official', '', '1481bd58-6951-40ae-838d-7960732f395a')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('4e5671c5-695c-4284-806b-30892a06b340', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'f5a67618-4532-4f2f-aa0b-541a19f3f9a2', '', 'kuali.lu.code.honorsOffering', '', '1867db8b-18a0-4218-98b9-94b06d795306')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('f5a67618-4532-4f2f-aa0b-541a19f3f9a2', '2677933260', '9e6afb9f-eafa-403a-a41f-aa5aeb20992c')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('f5a67618-4532-4f2f-aa0b-541a19f3f9a2', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('f5a67618-4532-4f2f-aa0b-541a19f3f9a2', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('f5a67618-4532-4f2f-aa0b-541a19f3f9a2', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('f5a67618-4532-4f2f-aa0b-541a19f3f9a2', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('f5a67618-4532-4f2f-aa0b-541a19f3f9a2', 'kuali.creditType.credit.degree.1-6.by.0.5')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('473d36fc-bf22-4a66-bdeb-6e40ac17b1fa', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'ce6b6491-798d-4fb9-b0e2-ae2f725a5188', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '72a907b9-201c-4e3f-8f2c-23766fd3ad49')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('20b847f3-eb19-4a4f-b008-73b4cbabcb10', 'regCodePrefix', '72a907b9-201c-4e3f-8f2c-23766fd3ad49', '1', '85498fe2-9c4a-4a94-928f-5639ff3bc480')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2abe9090-9e74-4bd7-8247-696ca464e0ef', 'kuali.attribute.grade.roster.level.type.key', '72a907b9-201c-4e3f-8f2c-23766fd3ad49', 'kuali.lui.type.activity.offering.lecture', 'adffe8c7-fa52-4291-9ddf-44a3ba4544af')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c2860914-5c2b-43c0-867f-dcd17de2e58f', 'kuali.attribute.final.exam.level.type', '72a907b9-201c-4e3f-8f2c-23766fd3ad49', 'kuali.lu.type.activity.Lecture', '89b83874-210d-4844-97bf-4c51ba3f92c6')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('1c83086c-4038-4b59-9b0f-84a5806bc9b9', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '72a907b9-201c-4e3f-8f2c-23766fd3ad49', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'dcb23fba-e734-4060-b982-5b558f7018d6')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('72a907b9-201c-4e3f-8f2c-23766fd3ad49', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('573bc28e-c191-480e-8f74-a9aebcb71b4d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:10.04', '', 'ENGL369D-CO-FO', 'f5a67618-4532-4f2f-aa0b-541a19f3f9a2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL369D-CO-FO', '72a907b9-201c-4e3f-8f2c-23766fd3ad49', '64379645-7332-4c3b-bc6f-1441f319fe9b')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('90a5b329-2305-4e13-9d51-ded77b76e5fc', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'a6f69c11-b55f-4cb0-bcf6-3e8fab0e0019', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 1, 1, 'Lecture', 'Courses with lecture', null, 'f3dc5b7a-952f-4054-af85-912f69e09f0c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1359963a-cc88-47ec-9391-b988745ae384', 'kuali.attribute.course.evaluation.indicator', 'f3dc5b7a-952f-4054-af85-912f69e09f0c', 0, '13fba1f1-446b-4214-8f3c-07ffcfd7e33a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9ffadcbf-b3e7-4358-8b54-ff702dcf8ba5', 'kuali.attribute.max.enrollment.is.estimate', 'f3dc5b7a-952f-4054-af85-912f69e09f0c', 0, '84e266c1-5392-4252-ab80-08d84fc9fbc7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ad689824-8b00-45cc-adea-d88493e976b0', 'kuali.attribute.nonstd.ts.indicator', 'f3dc5b7a-952f-4054-af85-912f69e09f0c', 0, 'e7bae8a7-8fd9-4e7c-8d04-7cf8b79f62fe')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('8b6d308d-ceba-4fc5-87e3-8e01f6a884b7', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', 'f3dc5b7a-952f-4054-af85-912f69e09f0c', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ad060770-ca55-45c4-9461-99fbaa88603c')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('caccd3b6-4ae3-49f9-8978-f7de2465f443', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'f3dc5b7a-952f-4054-af85-912f69e09f0c', '', 'kuali.lu.code.honorsOffering', 0, 'ee14aa17-8b11-4d7c-981d-3cabef8780ee')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('8b0443d4-840c-4d99-8461-410c3b278374', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:01:10.17', '', '', 'f3dc5b7a-952f-4054-af85-912f69e09f0c', '', 'M.INDREB', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '3f5eb97c-3cd6-4250-a2e5-982dbc52d774')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('217a21a4-cf62-42c3-82b4-fd66473b8372', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:10.225', '', 'ENGL369D-FO-AO', '72a907b9-201c-4e3f-8f2c-23766fd3ad49', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL369D-FO-AO', 'f3dc5b7a-952f-4054-af85-912f69e09f0c', '4212e4c5-90fc-465d-ba1f-6ff3599ef708')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('4774f86c-b262-41c6-97f9-a64f0865968a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for ENGL369D - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '17abee7a-861e-4b63-83e8-2e567e395489')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('80dc2c3d-e401-4a77-8302-cd18e4e00ba5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for ENGL369D - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '17abee7a-861e-4b63-83e8-2e567e395489', '125c407b-b395-4dd0-a93b-5f95caae8f9d')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('518439f7-c111-46ee-9ef7-0e9989449aaf', 1, '125c407b-b395-4dd0-a93b-5f95caae8f9d', '80f99777-7ca8-47ac-af93-53276916deb3')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('17abee7a-861e-4b63-83e8-2e567e395489', 'f3dc5b7a-952f-4054-af85-912f69e09f0c')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('80f99777-7ca8-47ac-af93-53276916deb3', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('23f1da85-f289-4fb5-b641-464170115ead', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '0f38834b-d2d5-475c-89b8-8b1f6c84b5cb')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('81bf1802-2b8d-4c81-b2b0-a34f1de23f9e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '5d82ecae-8116-40a1-89da-318abf43da45')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='23f1da85-f289-4fb5-b641-464170115ead', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='f3dc5b7a-952f-4054-af85-912f69e09f0c', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='0f38834b-d2d5-475c-89b8-8b1f6c84b5cb' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('5d82ecae-8116-40a1-89da-318abf43da45', 'f3dc5b7a-952f-4054-af85-912f69e09f0c')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('5d82ecae-8116-40a1-89da-318abf43da45', '72a907b9-201c-4e3f-8f2c-23766fd3ad49')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('67a43cd2-4ad1-4dd1-9068-fe4f5ee7442b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '72a907b9-201c-4e3f-8f2c-23766fd3ad49', 'CL 1', 'CL 1', '310609a8-efa9-40b1-ae2d-74c409ba9755')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('faa2f81b-259f-4f37-8fee-19e040132b67', 'kuali.lui.type.activity.offering.lecture', '310609a8-efa9-40b1-ae2d-74c409ba9755', '13b6ca39-1642-4038-b8fa-58146ff05e68')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('13b6ca39-1642-4038-b8fa-58146ff05e68', 'f3dc5b7a-952f-4054-af85-912f69e09f0c')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='310609a8-efa9-40b1-ae2d-74c409ba9755' where ID='13b6ca39-1642-4038-b8fa-58146ff05e68'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('cbd5cb5e-d4e4-4539-b01f-f6c310b695fa', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'ce6b6491-798d-4fb9-b0e2-ae2f725a5188', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '1a9588c4-2f74-4017-8562-84c681234645')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d6475f46-2261-41eb-bdab-445666bd4de0', 'kuali.attribute.registration.group.is.generated', '1a9588c4-2f74-4017-8562-84c681234645', 1, '199dac4a-5fb7-4906-bdbe-80650712a199')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6bcbebc9-4a4d-4f44-8151-b20a8536466a', 'kuali.attribute.registration.group.aocluster.id', '1a9588c4-2f74-4017-8562-84c681234645', '310609a8-efa9-40b1-ae2d-74c409ba9755', '033bfbda-90ee-4572-a5d6-a68fbe95bf24')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6073dfe2-fd83-46c2-8d9d-5cb8d6642b89', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '1a9588c4-2f74-4017-8562-84c681234645', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '48fa77f5-ebca-46a5-a015-a68bb72c9ec3')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('cbe893e2-cf26-4d58-bbb2-9967f5c02d04', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:11.444', '', 'ENGL369D-FO-RG', '72a907b9-201c-4e3f-8f2c-23766fd3ad49', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL369D-FO-RG', '1a9588c4-2f74-4017-8562-84c681234645', '494c7772-31f3-4326-a21f-8a979398e31f')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('77e23d4a-df7f-41d0-a853-be45fb59e2c9', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:11.456', '', 'ENGL369D-RG-AO', '1a9588c4-2f74-4017-8562-84c681234645', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL369D-RG-AO', 'f3dc5b7a-952f-4054-af85-912f69e09f0c', 'a999a4b8-3747-42e6-9ad4-721c01d0fcbe')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('91861177-4320-46d1-b17f-eadd043d310c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '9fb75593-9445-44ce-8d82-53e1634ae52d', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'WMST498M CO', '.', null, '53c1a45a-55ec-4fc3-a24b-f87cae6e4566')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('79382467-52d5-40cc-b693-6eb06dd9f186', 'kuali.attribute.course.evaluation.indicator', '53c1a45a-55ec-4fc3-a24b-f87cae6e4566', 0, 'fc90520d-602c-4bed-b1e0-746ed81a2d87')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bbf0739e-744c-4aba-bd29-a3dfeeba08ef', 'kuali.attribute.funding.source', '53c1a45a-55ec-4fc3-a24b-f87cae6e4566', '', 'ddbfd5a0-af4a-43f1-82e5-3433d38c37c2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7b5ab1ce-8bda-42eb-beee-325c96e04378', 'kuali.attribute.wait.list.indicator', '53c1a45a-55ec-4fc3-a24b-f87cae6e4566', 1, '07672230-ede5-4e88-b5f0-10aa5e4a2626')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('892687fd-0c3e-4cf1-8035-66460328996f', 'kuali.attribute.final.exam.use.matrix', '53c1a45a-55ec-4fc3-a24b-f87cae6e4566', 1, '1d1b96d0-46cc-4494-bf5f-1a5fe64dfae0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c7c97277-ae87-44d7-8796-90e4f3b41986', 'kuali.attribute.where.fees.attached.flag', '53c1a45a-55ec-4fc3-a24b-f87cae6e4566', 0, 'd930e86d-9b06-4b9e-be46-e4e1ed7d6d68')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c0d26aea-c7ac-4717-924a-b835ecdb095c', 'kuali.attribute.wait.list.level.type.key', '53c1a45a-55ec-4fc3-a24b-f87cae6e4566', 'AO', 'ea64ff0d-79fa-4916-bc2d-c1b19c5ba16d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('88a115fe-4bec-4d69-a52a-2c485f074cce', 'kuali.attribute.final.exam.indicator', '53c1a45a-55ec-4fc3-a24b-f87cae6e4566', 'STANDARD', '1c0b99e1-13fa-473f-8f11-6582fdac8901')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('58440f6f-c611-433c-9e6a-8ebdaaf91ffe', 'kuali.attribute.wait.list.type.key', '53c1a45a-55ec-4fc3-a24b-f87cae6e4566', null, '4728b9a5-32d2-4e6b-9ca1-10449efe85af')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5ce18ecb-cc07-4569-82fe-e0c40896fc73', 'kuali.attribute.final.exam.driver', '53c1a45a-55ec-4fc3-a24b-f87cae6e4566', 'kuali.lu.exam.driver.ActivityOffering', '5bac730c-8fd6-4eaf-ad05-e712fbe86728')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c9b549a6-3ea3-4f58-9003-d476ea3d0d35', 'kuali.attribute.course.number.internal.suffix', '53c1a45a-55ec-4fc3-a24b-f87cae6e4566', 'A', '8e1a461d-d828-4b3d-8c77-b5d572c9f367')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('65902ff6-f711-49bd-9388-dc2dcaafa981', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'WMST498M', 'WMST', '', 'Advanced Special Topics in Women''s Studies', '53c1a45a-55ec-4fc3-a24b-f87cae6e4566', '', '', 'kuali.lui.identifier.state.active', 'M', 'kuali.lui.identifier.type.official', '', '4f725836-1698-4839-b6b2-f3b58d56f87d')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('6ca8ceb8-c724-49bf-8ca2-b5ae77890a44', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '53c1a45a-55ec-4fc3-a24b-f87cae6e4566', '', 'kuali.lu.code.honorsOffering', '', 'b3a0ee42-e523-4cc5-b0e7-360e8fb2d901')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('53c1a45a-55ec-4fc3-a24b-f87cae6e4566', '4014660630', '534bd4a3-879e-483e-a8a0-c384e9b0cad9')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('53c1a45a-55ec-4fc3-a24b-f87cae6e4566', '4014660630')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('53c1a45a-55ec-4fc3-a24b-f87cae6e4566', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('53c1a45a-55ec-4fc3-a24b-f87cae6e4566', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('53c1a45a-55ec-4fc3-a24b-f87cae6e4566', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('53c1a45a-55ec-4fc3-a24b-f87cae6e4566', 'kuali.creditType.credit.degree.1.0,1.5,2.0,2.5,3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('da66d203-5b0a-4922-83e4-29a91aa9e00c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '4ddd26da-68e3-406f-b62f-1b36beb96f0f', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '5b370502-fb98-4159-a8c6-2307dd5db4cb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6912de10-64f7-4472-a0dc-b94ad4b2a0e4', 'regCodePrefix', '5b370502-fb98-4159-a8c6-2307dd5db4cb', '1', 'ed76b8a9-bf2b-43b6-8f6e-041b80d63116')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e5321edc-4040-4f20-8d28-13a7a0ab8080', 'kuali.attribute.final.exam.level.type', '5b370502-fb98-4159-a8c6-2307dd5db4cb', 'kuali.lu.type.activity.Lecture', 'd4a5b2d1-302d-40bb-93d6-51b84fb61c36')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dc654e8d-931a-4df9-84e3-94578231cb78', 'kuali.attribute.grade.roster.level.type.key', '5b370502-fb98-4159-a8c6-2307dd5db4cb', 'kuali.lui.type.activity.offering.lecture', '0870b96e-180a-4ddc-993a-f9c4099fd670')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('bdee35db-3507-4c53-ae98-7a884ca6562d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '5b370502-fb98-4159-a8c6-2307dd5db4cb', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '1a3668f9-96a0-408f-b4ec-14ef0ec7c6c2')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('5b370502-fb98-4159-a8c6-2307dd5db4cb', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e742e77b-8e8a-4fb0-a9d4-ce34a4cc2b64', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:14.693', '', 'WMST498M-CO-FO', '53c1a45a-55ec-4fc3-a24b-f87cae6e4566', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'WMST498M-CO-FO', '5b370502-fb98-4159-a8c6-2307dd5db4cb', 'c1742148-5467-436d-92c9-d59944296643')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('5b61f788-1385-45b2-8622-d305da345013', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '44ad3dfb-8855-4c58-ab14-33afc312293b', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, '4b8339cc-be2b-45dd-9f33-2c6a3a81aa43')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ee367678-0feb-4c96-91f3-7ad06e526484', 'kuali.attribute.nonstd.ts.indicator', '4b8339cc-be2b-45dd-9f33-2c6a3a81aa43', 0, '90a72356-2f30-49f2-b58f-5b51965b3496')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f79c7a6f-a9ff-419c-9336-94d35fb27329', 'kuali.attribute.max.enrollment.is.estimate', '4b8339cc-be2b-45dd-9f33-2c6a3a81aa43', 0, '07c83141-fbb3-4925-9811-69653b49ddbd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1af2fbd6-f3a6-43d4-aaea-eff8afd44092', 'kuali.attribute.course.evaluation.indicator', '4b8339cc-be2b-45dd-9f33-2c6a3a81aa43', 0, '03ceb01b-19e3-4786-bab8-172be901dab5')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('fe8a7bd2-518c-4600-bbde-c33ee8c081e6', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '4b8339cc-be2b-45dd-9f33-2c6a3a81aa43', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '6799fae1-5c86-41a2-98b9-5133d68685c3')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('d3ac0b96-78cc-4c8a-bd35-a2408da8d5a7', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '4b8339cc-be2b-45dd-9f33-2c6a3a81aa43', '', 'kuali.lu.code.honorsOffering', 0, 'e9f7331c-19df-49f9-b59e-b12cf3999e85')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('826bbe48-04fe-4acd-8ca3-b435c9cc7293', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:01:14.859', '', '', '4b8339cc-be2b-45dd-9f33-2c6a3a81aa43', '', 'N.ANNED', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'd4028630-d3ff-4590-864a-9963f5ec81f4')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('8bbd812d-001b-4339-bcbe-7e1d82b847b3', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:14.902', '', 'WMST498M-FO-AO', '5b370502-fb98-4159-a8c6-2307dd5db4cb', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'WMST498M-FO-AO', '4b8339cc-be2b-45dd-9f33-2c6a3a81aa43', '25a0ee49-914b-4706-a6f3-b2da4eb819b0')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('dff5f567-26a9-4ebd-83fe-6ec2690c2959', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for WMST498M - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '6c1f5735-fa7e-495b-ac5d-bd2c7faeb2d8')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('9158d851-ba4c-4618-a165-7f6c178b8b69', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for WMST498M - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '6c1f5735-fa7e-495b-ac5d-bd2c7faeb2d8', '1b6d8bb4-e7a6-47cb-9d00-8ee87fcc15f5')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('5bcb542d-353d-4b3a-92a9-ffaff17ac6d9', 0, '1b6d8bb4-e7a6-47cb-9d00-8ee87fcc15f5', '33562f4e-15df-4509-91cc-fbe534cd0f72')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('6c1f5735-fa7e-495b-ac5d-bd2c7faeb2d8', '4b8339cc-be2b-45dd-9f33-2c6a3a81aa43')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('33562f4e-15df-4509-91cc-fbe534cd0f72', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('33562f4e-15df-4509-91cc-fbe534cd0f72', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('33562f4e-15df-4509-91cc-fbe534cd0f72', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('33562f4e-15df-4509-91cc-fbe534cd0f72', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('cc01b3a6-d9fe-48e5-95ff-3bb7a2b14e6c', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Athlete Seatpool', '1c2e7786-a2ab-4778-8e81-ea540c60b9b2', 1, 2, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'a2faa6b6-086c-46a4-b341-25e2107b5895')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('09a01072-1955-4996-b795-acf1b9181f67', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Junior Seatpool', '4bae9f12-5901-4db9-b4a8-4dcc20781db5', 2, 4, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'e1cc98a1-8529-4b5e-9847-310727dfde0e')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='cc01b3a6-d9fe-48e5-95ff-3bb7a2b14e6c', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='4b8339cc-be2b-45dd-9f33-2c6a3a81aa43', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Athlete Seatpool', POPULATION_ID='1c2e7786-a2ab-4778-8e81-ea540c60b9b2', PROCESSING_PRIORITY=1, SEAT_LIMIT=2, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='a2faa6b6-086c-46a4-b341-25e2107b5895' and VER_NBR=1
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('df93ac30-9704-4419-804e-51a93b271bf0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '90b70b64-7c64-40d3-8266-fe3e9c1bf4be')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='09a01072-1955-4996-b795-acf1b9181f67', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='4b8339cc-be2b-45dd-9f33-2c6a3a81aa43', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Junior Seatpool', POPULATION_ID='4bae9f12-5901-4db9-b4a8-4dcc20781db5', PROCESSING_PRIORITY=2, SEAT_LIMIT=4, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='e1cc98a1-8529-4b5e-9847-310727dfde0e' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('90b70b64-7c64-40d3-8266-fe3e9c1bf4be', '4b8339cc-be2b-45dd-9f33-2c6a3a81aa43')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('90b70b64-7c64-40d3-8266-fe3e9c1bf4be', '5b370502-fb98-4159-a8c6-2307dd5db4cb')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('e4d33774-10d8-4da6-8a5c-ebe9b6d664e2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '5b370502-fb98-4159-a8c6-2307dd5db4cb', 'CL 1', 'CL 1', '908143c1-32ce-4cc3-b8f8-5db9ba415a3d')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('79a719ff-f6c7-4c94-94df-e7959eeb7bbd', 'kuali.lui.type.activity.offering.lecture', '908143c1-32ce-4cc3-b8f8-5db9ba415a3d', '5bf27a28-eaf7-4116-b26a-60eb7f6fd968')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('5bf27a28-eaf7-4116-b26a-60eb7f6fd968', '4b8339cc-be2b-45dd-9f33-2c6a3a81aa43')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='908143c1-32ce-4cc3-b8f8-5db9ba415a3d' where ID='5bf27a28-eaf7-4116-b26a-60eb7f6fd968'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('9d7f258f-aafa-4ba6-8931-6d78678585f1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '4ddd26da-68e3-406f-b62f-1b36beb96f0f', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '07b5487c-65bb-44ff-b4c1-5a1df10c5150')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d4a05456-eb37-410e-9072-4cb32092098d', 'kuali.attribute.registration.group.is.generated', '07b5487c-65bb-44ff-b4c1-5a1df10c5150', 1, 'a1a288e4-ad2e-4c74-bc41-bb66cb3c2ce0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3b5c2df8-446b-416e-9cdc-3e788b93e160', 'kuali.attribute.registration.group.aocluster.id', '07b5487c-65bb-44ff-b4c1-5a1df10c5150', '908143c1-32ce-4cc3-b8f8-5db9ba415a3d', 'bb420064-7b9f-485d-bcd9-3c2c0dc7bb9d')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d19e6f15-bd6b-4f7c-932d-1a244b3e86ce', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '07b5487c-65bb-44ff-b4c1-5a1df10c5150', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '20192386-2351-4e75-8b27-0175f9b3c049')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a4858db8-4d93-4da2-a241-a7ed54f3afc3', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:15.731', '', 'WMST498M-FO-RG', '5b370502-fb98-4159-a8c6-2307dd5db4cb', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'WMST498M-FO-RG', '07b5487c-65bb-44ff-b4c1-5a1df10c5150', 'e3f5a862-313c-446f-9aaf-93dd0a87c13e')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ee0292fc-507a-462d-a043-f847d08a896c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:15.745', '', 'WMST498M-RG-AO', '07b5487c-65bb-44ff-b4c1-5a1df10c5150', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'WMST498M-RG-AO', '4b8339cc-be2b-45dd-9f33-2c6a3a81aa43', '1eacc134-d79a-4ac7-abab-fdc28da22fb4')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d25673b5-cc06-4f11-a4b6-3ac9bf5565ba', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'CLUID-ENGL278-198001000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL278E CO', '.', null, 'e7ccf32c-fd29-4074-bc2d-12dcbf55be21')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('79106cbc-32f1-4337-8556-9c2d253d41c4', 'kuali.attribute.course.evaluation.indicator', 'e7ccf32c-fd29-4074-bc2d-12dcbf55be21', 0, '19fc51c9-2e9f-4fbb-bf11-f29d1dd37c45')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('31f5f8f1-49ff-4355-98a1-5491dc5b1e22', 'kuali.attribute.where.fees.attached.flag', 'e7ccf32c-fd29-4074-bc2d-12dcbf55be21', 0, 'e9bb24bb-ab73-4cd6-aaab-c2f91c70d650')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8e38aadc-f90b-4cd4-bebe-d7f6a56604a3', 'kuali.attribute.final.exam.indicator', 'e7ccf32c-fd29-4074-bc2d-12dcbf55be21', 'STANDARD', '2c5b4bfa-274d-469d-a86f-8e88ddbd05cd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('55858b5e-e068-4004-9d45-42ef3c555e49', 'kuali.attribute.course.number.internal.suffix', 'e7ccf32c-fd29-4074-bc2d-12dcbf55be21', 'A', 'a77501b4-6fb1-4d7a-949c-1b0e4e7b7dfb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('23b7eb1e-875d-4ca5-8f9c-745a627eb6b9', 'kuali.attribute.wait.list.indicator', 'e7ccf32c-fd29-4074-bc2d-12dcbf55be21', 1, 'c1c7dbc3-7074-406b-bfd1-d6a58eb5610e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b7924db5-08f2-49c5-817a-091c8558c9ee', 'kuali.attribute.wait.list.type.key', 'e7ccf32c-fd29-4074-bc2d-12dcbf55be21', null, 'f6f170d3-60bf-4829-929e-f474019827b6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('befd19df-2721-4cba-b400-bfd88bccb1bc', 'kuali.attribute.funding.source', 'e7ccf32c-fd29-4074-bc2d-12dcbf55be21', '', '4d1c19c8-5a3d-483a-8650-145dc95c3c9e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b9c9e8f5-1647-4d27-b160-da87814d86cd', 'kuali.attribute.final.exam.driver', 'e7ccf32c-fd29-4074-bc2d-12dcbf55be21', 'kuali.lu.exam.driver.ActivityOffering', '895aede8-81c3-46cd-9e91-d991f3af0208')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fc226d55-2225-45d6-b07c-95c9fd5ddd59', 'kuali.attribute.final.exam.use.matrix', 'e7ccf32c-fd29-4074-bc2d-12dcbf55be21', 1, '45d1acf7-ab4a-4e8a-907c-c2637143e545')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2f728eb0-44b2-491f-acc0-abb3219817ac', 'kuali.attribute.wait.list.level.type.key', 'e7ccf32c-fd29-4074-bc2d-12dcbf55be21', 'AO', '4f9a1b3c-9f8d-4087-aed3-ae0ec2261d58')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('e3a5e294-7b6a-4537-86ce-860ef71b2412', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'ENGL278E', 'ENGL', '', 'Special Topics in Literature', 'e7ccf32c-fd29-4074-bc2d-12dcbf55be21', '', '', 'kuali.lui.identifier.state.active', 'E', 'kuali.lui.identifier.type.official', '', '9ff36ada-92f9-489c-a9bd-ea570e437935')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('ac155c39-93e8-4db7-8c2f-216cb155848a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'e7ccf32c-fd29-4074-bc2d-12dcbf55be21', '', 'kuali.lu.code.honorsOffering', '', '5d763f83-95bb-44fc-9c6f-91bf0befdad8')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('e7ccf32c-fd29-4074-bc2d-12dcbf55be21', '2677933260', '5329cbeb-25e7-414f-b28d-cea79945236a')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('e7ccf32c-fd29-4074-bc2d-12dcbf55be21', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e7ccf32c-fd29-4074-bc2d-12dcbf55be21', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e7ccf32c-fd29-4074-bc2d-12dcbf55be21', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e7ccf32c-fd29-4074-bc2d-12dcbf55be21', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e7ccf32c-fd29-4074-bc2d-12dcbf55be21', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('2dc514ea-fcd7-4d02-98c9-9115a15a13a8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'ed9e10ff-8739-40d0-83da-a2b92489e055', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '2920c408-2643-4694-8875-d3add8852180')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e488ab4d-6c2c-4d94-9132-19d97785e829', 'kuali.attribute.final.exam.level.type', '2920c408-2643-4694-8875-d3add8852180', 'kuali.lu.type.activity.Lecture', '45d9cc13-06dc-4ef3-9ef1-49c100f07d01')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5df4fdfe-a0ea-47b3-b2ed-0a291c5c142c', 'regCodePrefix', '2920c408-2643-4694-8875-d3add8852180', '1', '149cb4c5-8369-4e12-a971-9610b45d8841')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7928a1a5-c86f-4252-b626-f90d29f9115d', 'kuali.attribute.grade.roster.level.type.key', '2920c408-2643-4694-8875-d3add8852180', 'kuali.lui.type.activity.offering.lecture', '47e4ed31-8888-47db-9af0-2623c4179057')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d9f0af4b-650e-4857-81d6-a3dfcc08697f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '2920c408-2643-4694-8875-d3add8852180', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '127fd17d-c1a9-490d-af88-89a762b881e3')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('2920c408-2643-4694-8875-d3add8852180', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('4755544a-e094-4a47-a77e-59ca3173d911', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:18.86', '', 'ENGL278E-CO-FO', 'e7ccf32c-fd29-4074-bc2d-12dcbf55be21', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL278E-CO-FO', '2920c408-2643-4694-8875-d3add8852180', '67d587c3-4c4e-45dd-afe0-370cc5a5892d')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('6f468e7a-6470-42d3-8537-4a23c1f63f59', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'c4eab8f6-4f3e-4561-ae1c-b40dd59c517f', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, 'fc6e113b-4a8b-463c-85f7-3baa808f465f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ff2663d4-da8c-408e-ae87-ac3cdb215ef9', 'kuali.attribute.max.enrollment.is.estimate', 'fc6e113b-4a8b-463c-85f7-3baa808f465f', 0, '40303fd3-d2b3-4df5-b841-47eb69450596')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('390c8002-5eab-4aaa-9535-6ed48cb8d078', 'kuali.attribute.course.evaluation.indicator', 'fc6e113b-4a8b-463c-85f7-3baa808f465f', 0, '6d9d6eae-a0cd-4a71-bc70-2d49fd03efbd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('14aa3fea-9ace-4ac6-9a42-023aaa05a8c6', 'kuali.attribute.nonstd.ts.indicator', 'fc6e113b-4a8b-463c-85f7-3baa808f465f', 0, '9e15c8f9-3109-469b-8638-edfc63885030')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('532dd6d6-733c-46a5-a32b-c5ed6af4f479', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', 'fc6e113b-4a8b-463c-85f7-3baa808f465f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '9afc00cc-efbe-46ba-8e3d-b81225166031')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('a2f474e0-6c21-454a-8cf4-dcf0eb52750d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'fc6e113b-4a8b-463c-85f7-3baa808f465f', '', 'kuali.lu.code.honorsOffering', 0, '4c2a6c69-ff6a-4e35-ad26-5f871736add9')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('6f3366cd-120a-48c5-a8af-3935c61b7b48', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:01:18.972', '', '', 'fc6e113b-4a8b-463c-85f7-3baa808f465f', '', 'O.DUANES', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'f24f8742-af2c-4a74-a490-f7798c361fc6')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('91fd1ed1-4bda-4296-a292-b723afcc39c0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:19.031', '', 'ENGL278E-FO-AO', '2920c408-2643-4694-8875-d3add8852180', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL278E-FO-AO', 'fc6e113b-4a8b-463c-85f7-3baa808f465f', 'b5776970-fd01-4c18-975f-2b51c3470465')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('a2b2d400-cf68-4251-979d-e4d521577bb2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for ENGL278E - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '94b0b905-43d3-4e02-b00a-e98c45b3580f')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('fe178473-8a12-458d-ad86-453fbe9a81d7', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A Schedule Request for ENGL278E section WB11', 'A Schedule Request for ENGL278E section WB11', 'A Schedule Request for ENGL278E section WB11', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', 'ba5e092f-0bfd-493e-810f-a982f43b44e4', '94b0b905-43d3-4e02-b00a-e98c45b3580f', '942e505e-d131-4593-8609-4b918aa8bad5')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('7cee8c99-4775-40ed-9edc-fdb715b025d6', 0, '942e505e-d131-4593-8609-4b918aa8bad5', 'ad2db379-a93c-45bc-ada3-1ac332b6d904')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('94b0b905-43d3-4e02-b00a-e98c45b3580f', 'fc6e113b-4a8b-463c-85f7-3baa808f465f')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('ad2db379-a93c-45bc-ada3-1ac332b6d904', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('ad2db379-a93c-45bc-ada3-1ac332b6d904', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('ad2db379-a93c-45bc-ada3-1ac332b6d904', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('eb7b024a-16be-4de1-abef-a11319100cfd', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '9bb79b74-22a5-4e51-bcd2-dfa0cb369b3e')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('9bb79b74-22a5-4e51-bcd2-dfa0cb369b3e', 'fc6e113b-4a8b-463c-85f7-3baa808f465f')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('9bb79b74-22a5-4e51-bcd2-dfa0cb369b3e', '2920c408-2643-4694-8875-d3add8852180')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('7626cb3f-86ec-4b06-8976-b25516107361', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '2920c408-2643-4694-8875-d3add8852180', 'CL 1', 'CL 1', '697b8ee8-5e7f-4376-a04c-d1b03b878921')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('5d2cc9d5-ad22-4537-b8c8-a1ada2ace347', 'kuali.lui.type.activity.offering.lecture', '697b8ee8-5e7f-4376-a04c-d1b03b878921', 'd42a218c-6bd7-44de-ac53-91666217140f')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('d42a218c-6bd7-44de-ac53-91666217140f', 'fc6e113b-4a8b-463c-85f7-3baa808f465f')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='697b8ee8-5e7f-4376-a04c-d1b03b878921' where ID='d42a218c-6bd7-44de-ac53-91666217140f'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('0b527e67-512b-4408-bc96-0971edddb881', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'ed9e10ff-8739-40d0-83da-a2b92489e055', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'adaa6cb1-1088-4b45-993b-e737636d35e9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7f32f0bf-f8b9-4b5d-8b33-70e2e0930b8a', 'kuali.attribute.registration.group.is.generated', 'adaa6cb1-1088-4b45-993b-e737636d35e9', 1, 'efff13ed-7fba-4cf0-a5a5-47e20ceea5c4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('740ca749-202a-4e71-ab7c-9712a2062c7f', 'kuali.attribute.registration.group.aocluster.id', 'adaa6cb1-1088-4b45-993b-e737636d35e9', '697b8ee8-5e7f-4376-a04c-d1b03b878921', '38b6b812-9102-4cb1-89a0-10d6d08e9d7a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('cd19ea87-0ba5-475e-8d4d-aec6c566e89a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'adaa6cb1-1088-4b45-993b-e737636d35e9', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'afd0cd64-50b3-4759-9abc-b3cee6b835ef')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('2af2a95e-6a39-431d-a202-512bfc7e0d94', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:19.477', '', 'ENGL278E-FO-RG', '2920c408-2643-4694-8875-d3add8852180', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL278E-FO-RG', 'adaa6cb1-1088-4b45-993b-e737636d35e9', 'c583f6b8-78dc-47d3-8937-94b2a4822cb0')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('b4a3861b-75e6-419c-8b70-72a9ef0c04ef', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:19.486', '', 'ENGL278E-RG-AO', 'adaa6cb1-1088-4b45-993b-e737636d35e9', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL278E-RG-AO', 'fc6e113b-4a8b-463c-85f7-3baa808f465f', '112c4a6b-69a2-4dc4-ac33-2ef7a83dd46d')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('19c903b5-6ca2-46e8-8cfa-8e2e3add6ac5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '6f72557a-cd52-4af3-9f7d-93aef829c882', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Introduction to changing nature of books, texts, and narrative in Information Age. Role of book in relation to other media, history of computers and writing, influence of computing on contemporary literature and culture, emerging forms of digital narrative and reading. Practical skills taught range from how to find digital literature and other texts online to using Web media to create literary works.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL295 CO', 'Introduction to changing nature of books, texts, and narrative in Information Age. Role of book in relation to other media, history of computers and writing, influence of computing on contemporary literature and culture, emerging forms of digital narrative and reading. Practical skills taught range from how to find digital literature and other texts online to using Web media to create literary works.', null, '5af9d4f3-87db-4941-b87f-f26e18c8aa80')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('15d4548a-b487-4d82-8d85-228b3ea79c22', 'kuali.attribute.wait.list.level.type.key', '5af9d4f3-87db-4941-b87f-f26e18c8aa80', 'AO', 'faf5ec65-1284-4dbe-a5ce-b29cffb7f5b5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e9d333f0-4eb1-404a-ac8c-3850d2d88959', 'kuali.attribute.wait.list.type.key', '5af9d4f3-87db-4941-b87f-f26e18c8aa80', null, '56878da7-7de4-4175-9a85-cf3d4d7ea474')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2861c983-ec7d-48f7-b681-1c6cbce0461d', 'kuali.attribute.final.exam.indicator', '5af9d4f3-87db-4941-b87f-f26e18c8aa80', 'STANDARD', '124655a4-c67e-4972-b5b3-4af662e90ca7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a9771320-6248-4ad1-ae4e-10b96278ca15', 'kuali.attribute.where.fees.attached.flag', '5af9d4f3-87db-4941-b87f-f26e18c8aa80', 0, '2eec310d-3016-4579-94a5-4b2613b7637d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d6077467-9fe7-4991-8a21-6d5c396bbd2a', 'kuali.attribute.wait.list.indicator', '5af9d4f3-87db-4941-b87f-f26e18c8aa80', 1, '59b02af2-b203-4bb3-a638-f830eb79b76c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8a6d5972-28ba-45f3-afb1-0c9be718cb10', 'kuali.attribute.course.evaluation.indicator', '5af9d4f3-87db-4941-b87f-f26e18c8aa80', 0, '1e6a3570-a767-4603-b81a-422260c51ebb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d981944e-493f-417a-9c9a-64f5981d8526', 'kuali.attribute.course.number.internal.suffix', '5af9d4f3-87db-4941-b87f-f26e18c8aa80', 'A', 'ad7b5055-8263-41b0-988d-66a6d3d0470c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9fd01e58-2da6-44f4-b74b-a81f54ed956a', 'kuali.attribute.final.exam.driver', '5af9d4f3-87db-4941-b87f-f26e18c8aa80', 'kuali.lu.exam.driver.ActivityOffering', '191e2597-e606-4a2e-9bae-368599757d4d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('71a70b73-1d59-41d9-b40c-f88da3d5a163', 'kuali.attribute.final.exam.use.matrix', '5af9d4f3-87db-4941-b87f-f26e18c8aa80', 1, '1c545690-afa7-495d-ba51-b137a856d161')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('70fc7654-f236-4ea3-89eb-60658c15af04', 'kuali.attribute.funding.source', '5af9d4f3-87db-4941-b87f-f26e18c8aa80', '', '3364c119-9359-48c5-adf2-b50fa5f7bdbf')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('235b4677-10e0-40e0-99f6-c85b46032261', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'ENGL295', 'ENGL', '', 'Literature in a Wired World', '5af9d4f3-87db-4941-b87f-f26e18c8aa80', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '0071519a-6690-440e-88b5-2f44c8698d29')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('e0c82859-7234-4573-b543-39ea52050aa5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '5af9d4f3-87db-4941-b87f-f26e18c8aa80', '', 'kuali.lu.code.honorsOffering', '', '438f9c4d-c468-4b2e-a60d-93863eafe5cd')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('5af9d4f3-87db-4941-b87f-f26e18c8aa80', '2677933260', '15adfe67-3ffa-48ed-b0a1-d5296aebf69b')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('5af9d4f3-87db-4941-b87f-f26e18c8aa80', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('5af9d4f3-87db-4941-b87f-f26e18c8aa80', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('5af9d4f3-87db-4941-b87f-f26e18c8aa80', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('5af9d4f3-87db-4941-b87f-f26e18c8aa80', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('5af9d4f3-87db-4941-b87f-f26e18c8aa80', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('edf41e5c-37ce-4068-b266-e0d35626faf2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '1af2eff3-2602-4e67-9e64-9f62ae7f932e', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '3e194dae-c0cf-407d-a7f5-8edb3c973a87')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b30640f0-1878-4d5d-8240-6fcaab17dd1c', 'regCodePrefix', '3e194dae-c0cf-407d-a7f5-8edb3c973a87', '1', '5af36355-1b5d-4e4d-9d3d-f13f45dad0b1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1bfc2ea3-0f0a-4efb-b64e-3956725e7c87', 'kuali.attribute.final.exam.level.type', '3e194dae-c0cf-407d-a7f5-8edb3c973a87', 'kuali.lu.type.activity.Lecture', '64410d2b-efd8-47fa-bb53-7bed16c85591')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a401c533-5d91-4be0-8d8a-a55917c4e721', 'kuali.attribute.grade.roster.level.type.key', '3e194dae-c0cf-407d-a7f5-8edb3c973a87', 'kuali.lui.type.activity.offering.lecture', '6c45aeec-847c-419d-b178-22452400ed29')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('509e5ddd-4cbc-4ea0-b987-c811cc5b3ac5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '3e194dae-c0cf-407d-a7f5-8edb3c973a87', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '815e852b-d15d-4892-9aa4-4a65292ab5d9')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('3e194dae-c0cf-407d-a7f5-8edb3c973a87', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('dd9811e7-52be-4700-8da7-e63483c11d9e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:22.401', '', 'ENGL295-CO-FO', '5af9d4f3-87db-4941-b87f-f26e18c8aa80', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL295-CO-FO', '3e194dae-c0cf-407d-a7f5-8edb3c973a87', '6ea3527c-331e-4228-ad42-89d6821a4c74')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('8dc59308-a662-4165-b9d2-29ca158e44fc', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'ac92256a-2100-48b4-a938-b2c614a8ebce', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, 'f13592b7-589d-4580-8ffa-9b603481834a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7dfb2e34-e0fc-4a42-9cb3-0bc03a307bb1', 'kuali.attribute.max.enrollment.is.estimate', 'f13592b7-589d-4580-8ffa-9b603481834a', 0, '5415bd4f-7edd-4b1d-a5dc-22f88768397c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5c41b098-18f6-42a2-b325-c92b619f5876', 'kuali.attribute.course.evaluation.indicator', 'f13592b7-589d-4580-8ffa-9b603481834a', 0, '831363d3-8943-4665-9dc5-9810ce760da1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e943eb94-afbe-491f-b962-77dcfe465ebe', 'kuali.attribute.nonstd.ts.indicator', 'f13592b7-589d-4580-8ffa-9b603481834a', 0, '86838eb9-35a2-4fd4-8795-cae60c23fae7')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('fcff474e-c1fb-4650-88c2-e51df368d18a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', 'f13592b7-589d-4580-8ffa-9b603481834a', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'b32cb0fa-2f35-44f1-8678-64e63ff2f4ca')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('9163b491-165f-44e9-9665-bb75bac3873a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'f13592b7-589d-4580-8ffa-9b603481834a', '', 'kuali.lu.code.honorsOffering', 0, '6b775a97-ea64-4daa-b545-ed299c8e8c67')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('bb58885a-ae36-4f28-b169-f978cd3fee3b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:01:22.522', '', '', 'f13592b7-589d-4580-8ffa-9b603481834a', '', 'S.JULIAG', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '1b463181-b5ae-40b4-8db0-2f025e16390b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('63eba598-cd59-43a4-9612-4492fa2c5ad9', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:22.556', '', 'ENGL295-FO-AO', '3e194dae-c0cf-407d-a7f5-8edb3c973a87', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL295-FO-AO', 'f13592b7-589d-4580-8ffa-9b603481834a', '122d51ed-4805-483a-a7d6-582ba62c550a')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('6cf324d8-8f89-479d-869e-5806c58d211f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for ENGL295 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '466fb318-23ce-494d-985d-8ab00a9e610a')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('1249d0e7-f27a-43a2-841d-895ebdaece5a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A Schedule Request for ENGL295 section WB11', 'A Schedule Request for ENGL295 section WB11', 'A Schedule Request for ENGL295 section WB11', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', 'd6e3e821-aae5-43f7-a9ea-63f5351c59d8', '466fb318-23ce-494d-985d-8ab00a9e610a', '1c27a87a-e70a-4ab1-94f2-eafd23fb0868')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('8651ae6b-d75c-4dc2-9f51-f80e30547a5e', 0, '1c27a87a-e70a-4ab1-94f2-eafd23fb0868', 'fd2f96b6-bcac-45b1-b4c9-42c5e4533b24')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('466fb318-23ce-494d-985d-8ab00a9e610a', 'f13592b7-589d-4580-8ffa-9b603481834a')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('fd2f96b6-bcac-45b1-b4c9-42c5e4533b24', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('fd2f96b6-bcac-45b1-b4c9-42c5e4533b24', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('fd2f96b6-bcac-45b1-b4c9-42c5e4533b24', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('faefc6fb-1ec2-4ca6-b31c-f56373eff17c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '50fc00c9-1eff-4a55-aa0f-bd7e85b25430')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('50fc00c9-1eff-4a55-aa0f-bd7e85b25430', 'f13592b7-589d-4580-8ffa-9b603481834a')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('50fc00c9-1eff-4a55-aa0f-bd7e85b25430', '3e194dae-c0cf-407d-a7f5-8edb3c973a87')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('0c3e6dc0-9a4e-4cb9-b0ae-5ef1079e2f4d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '074091f2-c391-4e95-9784-950f2ca7770b', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, 'f9d4342c-57f1-4683-a727-b8256edd4179')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('53217373-a7f5-44d9-add8-d965dbb073d7', 'kuali.attribute.max.enrollment.is.estimate', 'f9d4342c-57f1-4683-a727-b8256edd4179', 0, 'b8262756-5055-4347-97ec-c91426be4cfd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a3b029b2-2243-416e-9ec4-c9fc7df47e9c', 'kuali.attribute.nonstd.ts.indicator', 'f9d4342c-57f1-4683-a727-b8256edd4179', 0, '0c1eca6f-e324-4019-9641-039d0b226aa6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('98b61041-007f-4894-9b88-d4b4d52993a7', 'kuali.attribute.course.evaluation.indicator', 'f9d4342c-57f1-4683-a727-b8256edd4179', 0, 'eb0ff55b-78a3-4d5c-ab69-a08ec0930392')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('af2e4d01-8ef7-4a79-a70b-554fdfdf5341', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'B', '', '', '', 'f9d4342c-57f1-4683-a727-b8256edd4179', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a43da0c8-39d9-4dac-b62d-7b1c357c6897')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('1697d7e7-81f0-4ce8-b8d2-28ab27aa6eee', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'f9d4342c-57f1-4683-a727-b8256edd4179', '', 'kuali.lu.code.honorsOffering', 0, '6651792b-f31a-4e67-a4a7-fcf950c5fd44')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('e10a9cbe-b213-4567-8369-ec0d57102613', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:01:22.975', '', '', 'f9d4342c-57f1-4683-a727-b8256edd4179', '', 'C.CORNELISB', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '9a6cd320-547c-4443-ace6-14daa0aab2a3')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('7157f2d1-47cb-47f7-a95e-9fbebdcda3f8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:23.018', '', 'ENGL295-FO-AO', '3e194dae-c0cf-407d-a7f5-8edb3c973a87', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL295-FO-AO', 'f9d4342c-57f1-4683-a727-b8256edd4179', '03ffc1ff-ccb6-4c54-bcac-f4676964898c')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('ae88f37c-e244-4573-9338-f9cedf72466c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for ENGL295 - B', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '2053df37-1e87-4341-a966-d73b093f69d3')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('5acfa0de-c944-4280-b82c-12186702e42b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for ENGL295 - B', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '2053df37-1e87-4341-a966-d73b093f69d3', '5f79f744-bb0b-407a-b0af-f4012a865d59')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('f97568ea-b553-475d-bdd7-4382aab76399', 0, '5f79f744-bb0b-407a-b0af-f4012a865d59', '90fad9a5-e84a-4f92-b9be-9a60563a481b')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('2053df37-1e87-4341-a966-d73b093f69d3', 'f9d4342c-57f1-4683-a727-b8256edd4179')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('90fad9a5-e84a-4f92-b9be-9a60563a481b', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('90fad9a5-e84a-4f92-b9be-9a60563a481b', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('90fad9a5-e84a-4f92-b9be-9a60563a481b', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('90fad9a5-e84a-4f92-b9be-9a60563a481b', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('323692c3-a23e-4172-90f7-b03b1096f24c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '35d892a5-bab8-40e4-9323-c1ed25577258')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('35d892a5-bab8-40e4-9323-c1ed25577258', 'f9d4342c-57f1-4683-a727-b8256edd4179')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('35d892a5-bab8-40e4-9323-c1ed25577258', '3e194dae-c0cf-407d-a7f5-8edb3c973a87')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('bdbcb20e-47c2-4ab3-9680-58eb65b4f55b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '3e194dae-c0cf-407d-a7f5-8edb3c973a87', 'CL 1', 'CL 1', '85d8b520-60b0-4e06-b104-63352bd48fb0')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('b427633c-9041-496f-89e0-03893bf2dbd0', 'kuali.lui.type.activity.offering.lecture', '85d8b520-60b0-4e06-b104-63352bd48fb0', '14f34b76-cb83-4465-9162-298fc223f038')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('14f34b76-cb83-4465-9162-298fc223f038', 'f13592b7-589d-4580-8ffa-9b603481834a')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('14f34b76-cb83-4465-9162-298fc223f038', 'f9d4342c-57f1-4683-a727-b8256edd4179')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='85d8b520-60b0-4e06-b104-63352bd48fb0' where ID='14f34b76-cb83-4465-9162-298fc223f038'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('da0c3a5b-9a16-4003-9632-8dc0aabe7b92', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '1af2eff3-2602-4e67-9e64-9f62ae7f932e', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '4efb877f-0592-4e5d-bbda-8594db93aa01')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('54c97946-a8ee-49ed-848b-c6b04a1b4f7c', 'kuali.attribute.registration.group.aocluster.id', '4efb877f-0592-4e5d-bbda-8594db93aa01', '85d8b520-60b0-4e06-b104-63352bd48fb0', '90b59640-a28a-47f5-b78e-804e202efc11')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('76ec4218-77a9-473a-83f2-6456c5f9c295', 'kuali.attribute.registration.group.is.generated', '4efb877f-0592-4e5d-bbda-8594db93aa01', 1, 'cd52a8eb-d8ad-4235-aaa3-c2ad35694fca')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('98e42ad2-11cf-453e-97e5-4d8d91cb12a2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '4efb877f-0592-4e5d-bbda-8594db93aa01', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '797f1be3-262e-4070-9e02-ed4823cf9fcf')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('45226e64-cee8-4707-9742-c7727becdef1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:23.488', '', 'ENGL295-FO-RG', '3e194dae-c0cf-407d-a7f5-8edb3c973a87', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL295-FO-RG', '4efb877f-0592-4e5d-bbda-8594db93aa01', '052b0857-449b-4e76-931b-981cd2f7470e')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('b4c33f7a-0066-4907-8505-14c13c583280', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:23.516', '', 'ENGL295-RG-AO', '4efb877f-0592-4e5d-bbda-8594db93aa01', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL295-RG-AO', 'f13592b7-589d-4580-8ffa-9b603481834a', '0c046c20-38a8-495b-82a7-aa08639de1d5')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('a669caad-ab28-4faa-9894-7979ccee86b5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '1af2eff3-2602-4e67-9e64-9f62ae7f932e', '', '', '1002', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1002', '1002', '', 'fc24bdec-0fd7-4409-af02-7d78ca7bf3f5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0cb6f301-82b1-42a3-8bf1-1bfcdd58dd9b', 'kuali.attribute.registration.group.aocluster.id', 'fc24bdec-0fd7-4409-af02-7d78ca7bf3f5', '85d8b520-60b0-4e06-b104-63352bd48fb0', 'e7b23732-2a40-4bdf-8d6a-d1ef5ee609f5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0766adb6-a0cb-44da-a54e-3196402d19a2', 'kuali.attribute.registration.group.is.generated', 'fc24bdec-0fd7-4409-af02-7d78ca7bf3f5', 1, 'e74b74bc-5d31-4b28-932c-ff5257141135')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('9a4aec69-be5c-4adc-abd0-e98484de4bbe', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'fc24bdec-0fd7-4409-af02-7d78ca7bf3f5', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '66062b8f-8528-464a-8d40-ae1ed8e2264b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('3b0fb618-aade-4d8b-9085-5fb373ee630e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:23.593', '', 'ENGL295-FO-RG', '3e194dae-c0cf-407d-a7f5-8edb3c973a87', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL295-FO-RG', 'fc24bdec-0fd7-4409-af02-7d78ca7bf3f5', 'a67b72fb-5a2b-404f-900b-1456af3f5991')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('6f374f49-9db6-4137-ae5a-50b051e8005c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:23.596', '', 'ENGL295-RG-AO', 'fc24bdec-0fd7-4409-af02-7d78ca7bf3f5', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL295-RG-AO', 'f9d4342c-57f1-4683-a727-b8256edd4179', '006ca04e-d205-456b-83d9-156a366a4b9d')
/
update KSEN_SOC_ROR set OBJ_ID='971d59cc-eb7c-4504-ac92-ec51f651c326', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ITEMS_EXPECTED=48, ITEMS_PROCESSED=10, MESG_FORMATTED='', MESG_PLAIN='', SOC_ROR_STATE='kuali.soc.rollover.state.running', SOC_ROR_TYPE='kuali.soc.rollover.results.type.rollover', SOURCE_SOC_ID='03192a24-765a-4b9d-b638-5158430e09c8', TARGET_SOC_ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', TARGET_TERM_ID='kuali.atp.2014Winter' where ID='5b88a612-d027-429f-a9f3-b4241ed736c7' and VER_NBR=2
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='e2dae705-59ea-4cea-a1b8-97963890ed4e', ATTR_KEY='kuali.soc.rollover.result.dynattr.date.completed', OWNER_ID='5b88a612-d027-429f-a9f3-b4241ed736c7', ATTR_VALUE='2014 Jan 23 20:01:23 America/New_York' where ID='289b7901-b122-4239-94fd-e4dd1e0066cf'
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('479b9433-f182-4a1e-a8ba-0001ab740626', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', '0bc105c1-ce4b-4f15-9594-36a8adbaaf37', '', 'b540aa6f-9c3c-4e28-853b-678eefa638f3')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('3fd142e5-22ba-48f5-b774-ae5da5577774', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '0e638164-54b0-48f5-8e6d-b43fc908ccad', '7e57d564-919b-498e-9db8-d2b8ffb84a1d', '199b1fe3-90cc-4a64-9d58-38be8115d1e1')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0d1093ab-d300-4ae2-95f7-e5348954611e', 'activityOfferingsCreated', '199b1fe3-90cc-4a64-9d58-38be8115d1e1', '1', '98789862-df0f-4e0f-b8bb-824f7a39854e')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('f853174f-fb48-440e-82d0-9472805ad0ef', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '08345864-2994-4101-aedd-a1ead352f249', '4654e390-7bd2-4683-9d07-02480f8887c3', '79efcb68-c0b6-4967-853e-feccc4742945')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7d515de9-d72d-41d4-9c0a-419b30324188', 'activityOfferingsCreated', '79efcb68-c0b6-4967-853e-feccc4742945', '6', 'c6c4d909-ccc8-4c08-a24c-0eb8f0a9e172')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('617cd1ec-bc7f-40e4-b3ba-a2650b3eb497', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', '1046086a-9448-4fc8-b8ea-f0c39a0d44c8', '', 'eab94e61-1235-47b9-9257-350a450161d2')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('aa39eb66-4f05-48a7-a191-b988c3a80af4', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '104cf59f-09d0-46e4-839a-10b3d03bd99a', 'd4a00c7a-b5dd-444d-919b-b987bee58745', '93b20a6a-8870-46d6-95f9-d2e11fc27949')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3397453a-b87f-4060-ab07-e56d868beea0', 'activityOfferingsCreated', '93b20a6a-8870-46d6-95f9-d2e11fc27949', '1', '745e12a8-2317-4d81-bc59-ec7a9d3ee2e0')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('7db826fa-ec4d-4f60-82af-3d44c4dfc46b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '257c1c89-c045-4818-a6a0-8b7862bffc65', '508ac76d-401e-4326-87df-7c99baf7bd79', '241e1204-7dbb-40b3-ba1c-84ee1ef8460a')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e8fe2c42-a017-4d76-ba02-1658cbcbbf2e', 'activityOfferingsCreated', '241e1204-7dbb-40b3-ba1c-84ee1ef8460a', '1', 'cabf7f86-a83b-4113-b6c5-4405565c163d')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('fc26da17-e038-4c2d-b8ac-d21c000d891a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '21c36421-1317-4c9a-bd25-1badb0fc7322', 'f5a67618-4532-4f2f-aa0b-541a19f3f9a2', '90678d28-443b-4368-ba51-ed8035fef236')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6c17589e-0b35-4aac-86f3-1410dd7143fd', 'activityOfferingsCreated', '90678d28-443b-4368-ba51-ed8035fef236', '1', '81ad3ee2-8590-4c5f-af78-c8ea85ca1938')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('3943630b-f4a5-4c59-910a-23638e033922', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '2d71a80b-edf3-42f2-bafd-bba2cf9bdcbc', '53c1a45a-55ec-4fc3-a24b-f87cae6e4566', '9c6b2aca-6df2-4397-9a3a-bd3d27a543b9')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('88a11f38-674d-4b50-bcee-f567615c01ab', 'activityOfferingsCreated', '9c6b2aca-6df2-4397-9a3a-bd3d27a543b9', '1', '8ebe38bf-e26a-4653-92c3-f4a387916a8a')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('d021178b-1e9a-4089-84cf-ee43b44c2c6b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '2f94897d-75c7-49b3-a3fc-2484abc38dc7', 'e7ccf32c-fd29-4074-bc2d-12dcbf55be21', '4c32bc47-1c81-46d3-a891-3a595eb6423e')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3c6aa24b-72bb-4b83-9e89-e93e434b43ab', 'activityOfferingsCreated', '4c32bc47-1c81-46d3-a891-3a595eb6423e', '1', 'c79087d6-098c-4e14-bf9e-d2f249acf521')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('262134b2-a11a-4aaa-a8d1-dc5139a2baa2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '2ff43080-cf67-4ff4-b4ae-2fd62b5a72f1', '5af9d4f3-87db-4941-b87f-f26e18c8aa80', 'bcb6bef8-8d7b-4e68-acd2-b9e3d5b14af3')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('38e83028-3ca9-4bfd-a76c-6cbfc602ff3d', 'activityOfferingsCreated', 'bcb6bef8-8d7b-4e68-acd2-b9e3d5b14af3', '2', 'a4297360-1015-4ced-ae25-a01b425c0697')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ac22dacb-0ccd-4b7b-854c-eee4eaf49d77', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'CLUID-WMST469-201008000000', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Special topics course taken as part of an approved study abroad program', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'WMST469T CO', 'Special topics course taken as part of an approved study abroad program', null, '697a0587-c8b5-47e8-9431-f06fb937cc13')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ac339612-633e-49d6-94ca-441784c05d23', 'kuali.attribute.where.fees.attached.flag', '697a0587-c8b5-47e8-9431-f06fb937cc13', 0, 'ebdba90f-47e8-4748-8eb0-4817b5452413')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('41fa2498-6b28-40f2-8237-57957d99499e', 'kuali.attribute.course.number.internal.suffix', '697a0587-c8b5-47e8-9431-f06fb937cc13', 'A', '67691e34-9b1b-4122-b9c9-dd2a98a37d4c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ed089139-5259-4e96-92e1-3159c3d661ce', 'kuali.attribute.course.evaluation.indicator', '697a0587-c8b5-47e8-9431-f06fb937cc13', 0, '2bebb40c-b72e-4c18-ac4e-57b9827ce3a8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('968bd7e7-b43b-431d-a353-917dafe85283', 'kuali.attribute.final.exam.driver', '697a0587-c8b5-47e8-9431-f06fb937cc13', 'kuali.lu.exam.driver.ActivityOffering', 'e46587b6-24ee-4e84-a207-0550994bc61b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('12126786-2211-464d-938e-87dfb5d29977', 'kuali.attribute.final.exam.indicator', '697a0587-c8b5-47e8-9431-f06fb937cc13', 'STANDARD', '4cc3c1a6-295e-4cb6-b42a-ffb403b133b7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fdc6167d-ae3a-4235-bd23-953dc811c396', 'kuali.attribute.wait.list.level.type.key', '697a0587-c8b5-47e8-9431-f06fb937cc13', 'AO', 'bb4202d4-4a29-44a3-af9a-0818a39520cb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c25f36e5-2318-40e1-bee0-868745c4fcf2', 'kuali.attribute.wait.list.type.key', '697a0587-c8b5-47e8-9431-f06fb937cc13', null, '0859e818-6ab4-49e4-92e1-08ab73b8af6c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c4ef8920-8286-4f97-8a52-987525e1af7b', 'kuali.attribute.wait.list.indicator', '697a0587-c8b5-47e8-9431-f06fb937cc13', 1, '50b1ab9e-282e-4db9-aff2-6d6ddb0ca4f9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ea804a41-63fb-445b-8054-8adc51a51536', 'kuali.attribute.funding.source', '697a0587-c8b5-47e8-9431-f06fb937cc13', '', 'd9fbc7c6-b62e-4541-b3df-cbf019e4c6c3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ca7dccfd-0ee1-4a8c-a10d-adb74fb0167b', 'kuali.attribute.final.exam.use.matrix', '697a0587-c8b5-47e8-9431-f06fb937cc13', 1, 'd5587d2f-a2f4-431d-af72-50a20894de2c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('bb4060a7-aec6-4bc7-8ba0-113692d0783e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'WMST469T', 'WMST', '', 'Study Abroad Special Topics IV', '697a0587-c8b5-47e8-9431-f06fb937cc13', '', '', 'kuali.lui.identifier.state.active', 'T', 'kuali.lui.identifier.type.official', '', 'cdcfbbbc-b4bd-4c52-8f77-9bcb83a8ecc0')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('5b9c83a4-7e95-40f2-a757-10eb38fd0e1a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '697a0587-c8b5-47e8-9431-f06fb937cc13', '', 'kuali.lu.code.honorsOffering', '', 'c37ed73c-2933-4e0f-8a96-872d7656d3dd')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('697a0587-c8b5-47e8-9431-f06fb937cc13', '4014660630', 'ada5778d-c99f-4a03-9d99-97fb78576055')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('697a0587-c8b5-47e8-9431-f06fb937cc13', '4014660630')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('697a0587-c8b5-47e8-9431-f06fb937cc13', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('697a0587-c8b5-47e8-9431-f06fb937cc13', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('697a0587-c8b5-47e8-9431-f06fb937cc13', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('697a0587-c8b5-47e8-9431-f06fb937cc13', 'kuali.creditType.credit.degree.1-6.by.0.5')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('f690bdf2-3415-49f3-b7c3-54b1d9230181', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '4f5e5387-81cb-46b6-8b3f-fef5aeb90638', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '5ab256dd-03d4-4349-b3a6-e97883e1e49d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3a2baa17-76df-40f7-9a1f-b98a58e07d6e', 'kuali.attribute.grade.roster.level.type.key', '5ab256dd-03d4-4349-b3a6-e97883e1e49d', 'kuali.lui.type.activity.offering.lecture', '2dbc0e47-078d-4544-b6ec-db102ab3ee5e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7dfd15a1-11ba-444d-a892-97f75209c728', 'regCodePrefix', '5ab256dd-03d4-4349-b3a6-e97883e1e49d', '1', '81319c31-1bdd-4cf2-9247-575f9c2427f6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('376576f0-f401-4d70-aeeb-41da298c57a2', 'kuali.attribute.final.exam.level.type', '5ab256dd-03d4-4349-b3a6-e97883e1e49d', 'kuali.lu.type.activity.Lecture', 'be44614c-46b3-49ee-a5c1-e47ae0f1a35c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('dc1b354d-ec40-4aac-96d6-9ed522ecd22e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '5ab256dd-03d4-4349-b3a6-e97883e1e49d', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '8e971e83-184e-4a4c-bdcc-b46076457ba7')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('5ab256dd-03d4-4349-b3a6-e97883e1e49d', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('39adddfe-b0cc-462a-a18e-531f136a0c45', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:26.742', '', 'WMST469T-CO-FO', '697a0587-c8b5-47e8-9431-f06fb937cc13', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'WMST469T-CO-FO', '5ab256dd-03d4-4349-b3a6-e97883e1e49d', '7921f902-9654-408d-85ef-28c2f1ccfc06')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('c4a5847e-ce5e-4773-ad65-d66a2733bfec', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '0b5c76ff-9f50-41d6-a505-bc39ccba5f8c', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 1, '', 'Lecture', 'Courses with lecture', null, 'b1477dab-cb77-42f8-810d-b6def26c98ea')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('05c95bb5-150a-48e4-ab34-f367c9f27428', 'kuali.attribute.nonstd.ts.indicator', 'b1477dab-cb77-42f8-810d-b6def26c98ea', 0, '256c3924-1966-4e50-852a-82c347f348ff')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5a02af64-69d3-45d4-9002-39d9d35783bd', 'kuali.attribute.max.enrollment.is.estimate', 'b1477dab-cb77-42f8-810d-b6def26c98ea', 0, 'b66caf1b-1eda-419e-8995-fd7e2cc42e86')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('94cbd3f8-8652-4d93-8e8a-0c6d542ec11b', 'kuali.attribute.course.evaluation.indicator', 'b1477dab-cb77-42f8-810d-b6def26c98ea', 0, 'b4aa7bba-0472-4697-889a-d7a5e2e12beb')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('513acf06-803e-49ea-b0ed-963f050c52e9', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', 'b1477dab-cb77-42f8-810d-b6def26c98ea', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'f3d82864-08c1-4d22-a7ed-ca868d13fbc1')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('7775b3d0-3735-4402-88c2-839d8a0c6bbb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'b1477dab-cb77-42f8-810d-b6def26c98ea', '', 'kuali.lu.code.honorsOffering', 0, '5d14fb4d-87d1-4ffd-b8e3-56e1414874b8')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('1d81ca99-c46e-421b-aa6e-57570b86e1e6', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:01:26.85', '', '', 'b1477dab-cb77-42f8-810d-b6def26c98ea', '', 'D.ALEXD', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'fecb5b34-5324-466f-809d-e818a37e416c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('eb0fe0e3-ff30-4ba5-aec9-1a682a04fbde', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:26.887', '', 'WMST469T-FO-AO', '5ab256dd-03d4-4349-b3a6-e97883e1e49d', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'WMST469T-FO-AO', 'b1477dab-cb77-42f8-810d-b6def26c98ea', '5025a791-cac1-4e84-b747-56e81c06bb72')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('6c1f3c8b-60a0-4ef4-b8d6-4028fe96fafb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for WMST469T - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'b732da35-ff98-4269-8635-f1ccbcf6fd66')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('8feec731-e62e-4d84-9d82-1df3c3aadad1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for WMST469T - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'b732da35-ff98-4269-8635-f1ccbcf6fd66', '4817387c-87c1-45ac-a025-dc19fb9b7c6f')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('bd8ee577-b9bd-464b-9131-52aedc137624', 1, '4817387c-87c1-45ac-a025-dc19fb9b7c6f', '6ac3c306-338b-44fd-81c3-7e30651da0b1')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('b732da35-ff98-4269-8635-f1ccbcf6fd66', 'b1477dab-cb77-42f8-810d-b6def26c98ea')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('6ac3c306-338b-44fd-81c3-7e30651da0b1', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('e08d1008-953b-4dab-833e-3d5f5d0bae6b', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Athlete Seatpool', '1c2e7786-a2ab-4778-8e81-ea540c60b9b2', 1, 2, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'eed3cb11-f2c8-4768-9ef5-b3a20e75cce8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('b0c2f3d8-7913-4021-972c-398aeebf5774', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Junior Seatpool', '4bae9f12-5901-4db9-b4a8-4dcc20781db5', 2, 4, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'f994af6a-4d77-4504-9544-9534c05934ae')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='e08d1008-953b-4dab-833e-3d5f5d0bae6b', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='b1477dab-cb77-42f8-810d-b6def26c98ea', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Athlete Seatpool', POPULATION_ID='1c2e7786-a2ab-4778-8e81-ea540c60b9b2', PROCESSING_PRIORITY=1, SEAT_LIMIT=2, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='eed3cb11-f2c8-4768-9ef5-b3a20e75cce8' and VER_NBR=1
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('f362b62e-364b-4940-a38f-a64fa30f2b3d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '3ef3421a-8b7e-4f24-bad6-90c813e6b601')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='b0c2f3d8-7913-4021-972c-398aeebf5774', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='b1477dab-cb77-42f8-810d-b6def26c98ea', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Junior Seatpool', POPULATION_ID='4bae9f12-5901-4db9-b4a8-4dcc20781db5', PROCESSING_PRIORITY=2, SEAT_LIMIT=4, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='f994af6a-4d77-4504-9544-9534c05934ae' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('3ef3421a-8b7e-4f24-bad6-90c813e6b601', 'b1477dab-cb77-42f8-810d-b6def26c98ea')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('3ef3421a-8b7e-4f24-bad6-90c813e6b601', '5ab256dd-03d4-4349-b3a6-e97883e1e49d')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('727e5682-1adc-470b-9aa1-1aadc7c394c2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '5ab256dd-03d4-4349-b3a6-e97883e1e49d', 'CL 1', 'CL 1', '5fb4513f-1441-4a4b-9122-11349c2bef85')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('e3a11e40-5f73-4b18-b210-4fbc4d10d11a', 'kuali.lui.type.activity.offering.lecture', '5fb4513f-1441-4a4b-9122-11349c2bef85', '6a722ffc-ed3f-470c-ba91-ac159a48701e')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('6a722ffc-ed3f-470c-ba91-ac159a48701e', 'b1477dab-cb77-42f8-810d-b6def26c98ea')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='5fb4513f-1441-4a4b-9122-11349c2bef85' where ID='6a722ffc-ed3f-470c-ba91-ac159a48701e'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('cadc8bc0-d103-417f-ad6a-d98f1370dd83', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '4f5e5387-81cb-46b6-8b3f-fef5aeb90638', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '26bea175-c719-40d7-ad38-556d6693e945')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cc9251d8-2230-4a23-8f92-d5ef11dfdd39', 'kuali.attribute.registration.group.aocluster.id', '26bea175-c719-40d7-ad38-556d6693e945', '5fb4513f-1441-4a4b-9122-11349c2bef85', '22bd1cd4-7bab-4533-bf11-40e26ca144dd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('38d8da24-5c3b-4c6b-a5ae-4098f7806d58', 'kuali.attribute.registration.group.is.generated', '26bea175-c719-40d7-ad38-556d6693e945', 1, 'bae21511-c6f1-409d-8be7-d2373e60eaae')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('63e0b489-86ca-47ea-8e30-2ebb48a018d1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '26bea175-c719-40d7-ad38-556d6693e945', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7473e6b3-8581-4a57-a050-75aff89515d3')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a6e38017-2fb7-46ba-b032-5e70b239e9c2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:27.603', '', 'WMST469T-FO-RG', '5ab256dd-03d4-4349-b3a6-e97883e1e49d', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'WMST469T-FO-RG', '26bea175-c719-40d7-ad38-556d6693e945', '976d91ce-97aa-4f43-832b-a66d255c0075')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('cfa73864-5655-4e47-8258-b63f1958b6be', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:27.615', '', 'WMST469T-RG-AO', '26bea175-c719-40d7-ad38-556d6693e945', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'WMST469T-RG-AO', 'b1477dab-cb77-42f8-810d-b6def26c98ea', '9c4afa77-51e9-4dd7-aa1d-a4ab6186afc4')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('0a428965-21dc-41f9-a385-68be39bc9138', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '8149aa68-9bfa-4d95-bcfe-b17b2d36d763', TIMESTAMP '2014-01-01 19:00:00.0', '', 'An exploration of arguably the most complex, profound, and ubiquitous expression of human experience. Study through close reading of significant forms and conventions of Western poetic tradition. Poetry''s roots in oral and folk traditions and connections to popular song forms.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL243 CO', 'An exploration of arguably the most complex, profound, and ubiquitous expression of human experience. Study through close reading of significant forms and conventions of Western poetic tradition. Poetry''s roots in oral and folk traditions and connections to popular song forms.', null, '5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1b458c50-ebec-4901-922f-9ee4fe1f8703', 'kuali.attribute.wait.list.level.type.key', '5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', 'AO', '62483f94-8e70-47ac-8309-62483599cad8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c4c09f35-fbb4-4f0e-9439-d9112fde09a9', 'kuali.attribute.wait.list.type.key', '5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', null, '278b3655-2a26-404d-87cc-4a0abc691fc2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8f9d03ee-4410-4428-afd2-853b98008bc3', 'kuali.attribute.final.exam.indicator', '5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', 'STANDARD', 'b735e127-e8a8-43bb-806a-b5212683a535')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4209830a-945e-490f-b1be-043b192d2cdb', 'kuali.attribute.funding.source', '5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', '', '38b02110-b178-42de-b4ba-d11596a63cd1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bfc9b283-e6c0-49c5-b10f-1924913b805b', 'kuali.attribute.course.evaluation.indicator', '5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', 0, '30db5f41-3b58-46c6-9a04-7145570c2f16')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('daa5a66e-0828-4f68-a703-d2e6918c5e3a', 'kuali.attribute.final.exam.use.matrix', '5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', 1, '32b17fde-58e5-4f76-8547-9d6ffc40db56')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('875ad8bf-d0ff-4ffb-837b-475a63c1e520', 'kuali.attribute.wait.list.indicator', '5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', 0, '611e408e-d330-47b3-93ee-0dccee109403')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b87c402f-44ac-42ff-8281-a7767b9e687f', 'kuali.attribute.where.fees.attached.flag', '5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', 0, '9129bfca-3d2e-4913-8723-77a04416efc7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('88b2e12d-360c-4176-af17-babf30749989', 'kuali.attribute.final.exam.driver', '5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', 'kuali.lu.exam.driver.ActivityOffering', 'd1fa163c-c57a-4b63-bc8e-77691fafc93b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6cc7a40e-0ff8-43da-8ef8-770cd08634e5', 'kuali.attribute.course.number.internal.suffix', '5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', 'A', '56e3388e-5648-4c53-b411-1ba9a83a95b2')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a575608f-e860-42c0-a428-ae6b51e4357c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'ENGL243', 'ENGL', '', 'What is Poetry?', '5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '6053477c-0d31-409c-b1ca-8315a4cb9a2d')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('b8734b10-544a-4ecd-8d61-343067fcb27c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', '', 'kuali.lu.code.honorsOffering', '', 'ca0df926-cbf6-40a9-9c58-77e186ce1770')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', '2677933260', 'e605ec1a-5da3-4048-92bd-553e708a65f5')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ddc25589-d24f-4579-9f6a-bdb3ee1ffabb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'b3d8efe2-cbe7-4e50-a72f-8f4b2b4ea8ad', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '194c5b31-1213-4aa0-a7cf-a5b8bba54192')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8e7bba19-37bb-436b-8feb-8e231d070b47', 'regCodePrefix', '194c5b31-1213-4aa0-a7cf-a5b8bba54192', '1', 'b624f33f-9ecc-49f9-98d3-33c683f397e4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f777c406-6f8a-4b49-8183-feaf194f80d7', 'kuali.attribute.grade.roster.level.type.key', '194c5b31-1213-4aa0-a7cf-a5b8bba54192', 'kuali.lui.type.activity.offering.lecture', '61a23125-9c3c-42f6-85aa-9840764404a5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('74a77067-5225-422d-b684-06c84440b5ee', 'kuali.attribute.final.exam.level.type', '194c5b31-1213-4aa0-a7cf-a5b8bba54192', 'kuali.lu.type.activity.Lecture', 'ce9b739a-ecd8-4a87-8bc1-9ea7f6feedc8')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('f3daa8be-aed8-492f-81e5-e7faed269f4e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '194c5b31-1213-4aa0-a7cf-a5b8bba54192', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '65bd0ab7-5b3d-4b0c-9d6f-679fcb8f2851')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('194c5b31-1213-4aa0-a7cf-a5b8bba54192', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('d479f5aa-7791-435d-ae42-755955c06c3c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:31.55', '', 'ENGL243-CO-FO', '5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL243-CO-FO', '194c5b31-1213-4aa0-a7cf-a5b8bba54192', '2be61803-14c1-45bc-8941-650aded63902')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('7418f19a-bc5d-4197-b258-b9a272ea8474', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '69f87d52-5ab8-4c76-85d1-d753266cbeca', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 30, 30, 'Lecture', 'Courses with lecture', null, '96be442b-7368-4fa9-9bd8-a6061eabf6e0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0a303bc9-2c59-469e-a36f-abf5a2f24e9f', 'kuali.attribute.max.enrollment.is.estimate', '96be442b-7368-4fa9-9bd8-a6061eabf6e0', 0, 'c15c0124-bfe2-4035-8187-bbcf6329aec5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('aa7aa2f4-1729-4775-a487-5dcc16b83fe0', 'kuali.attribute.course.evaluation.indicator', '96be442b-7368-4fa9-9bd8-a6061eabf6e0', 0, 'd7b378ef-8a4d-499c-b0b3-aca8936c4340')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('58c62d0b-723b-45e9-8a89-8abf5b131dce', 'kuali.attribute.nonstd.ts.indicator', '96be442b-7368-4fa9-9bd8-a6061eabf6e0', 0, '384f6c59-3cc0-460b-b335-e6de8584a186')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5c06438c-0293-477d-ab0c-338b13ed4993', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '96be442b-7368-4fa9-9bd8-a6061eabf6e0', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '0518022d-1fb5-4206-89db-f2250cd0049f')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('17da3937-de5b-45a0-b5a5-56b30f83612c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '96be442b-7368-4fa9-9bd8-a6061eabf6e0', '', 'kuali.lu.code.honorsOffering', 0, 'a0a3e24c-ffaf-4589-ad81-4b8993d5c779')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('00009896-86d2-4c49-b0d7-2b255a60040d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:01:31.665', '', '', '96be442b-7368-4fa9-9bd8-a6061eabf6e0', '', 'C.TERRYE', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '9066865d-ed21-43ef-9d08-96cc38f16d2e')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('eecd70a2-2e12-4e02-889f-a11d920e886c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:31.706', '', 'ENGL243-FO-AO', '194c5b31-1213-4aa0-a7cf-a5b8bba54192', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL243-FO-AO', '96be442b-7368-4fa9-9bd8-a6061eabf6e0', 'dbe1bcec-5328-423b-82f2-0720225d148e')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('ed06d56d-c07c-459c-a1a1-57962fff1fdf', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for ENGL243 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'b81aa2f9-f3e9-4e8d-ad68-7bcf52a96bd4')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('6663e2c2-cdf5-4927-97c9-71dceeac6e0f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A Schedule Request for ENGL243 section 0102', 'A Schedule Request for ENGL243 section 0102', 'A Schedule Request for ENGL243 section 0102', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', 'acecda74-05ea-49e1-8504-058e6e954d37', 'b81aa2f9-f3e9-4e8d-ad68-7bcf52a96bd4', 'fd5d151e-0966-4189-9d23-16afb7a5e300')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('844fc48f-cabf-4472-9fa9-9f8da8312798', 0, 'fd5d151e-0966-4189-9d23-16afb7a5e300', 'ccd3540e-e897-4640-a0e2-fa52f663deb9')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('b81aa2f9-f3e9-4e8d-ad68-7bcf52a96bd4', '96be442b-7368-4fa9-9bd8-a6061eabf6e0')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('ccd3540e-e897-4640-a0e2-fa52f663deb9', '84e53b2a-f39a-4b7a-8916-731087cff42d')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('ccd3540e-e897-4640-a0e2-fa52f663deb9', 'e26b7710-1faa-4f4e-ad57-52caec27baa9')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('ccd3540e-e897-4640-a0e2-fa52f663deb9', 'F0AA72D6DC55F650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('0fa08ce5-1719-4e21-94f8-c16cd208bc6f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '1a43bed3-b7d8-4911-a860-e50e80d60d25')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('1a43bed3-b7d8-4911-a860-e50e80d60d25', '96be442b-7368-4fa9-9bd8-a6061eabf6e0')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('1a43bed3-b7d8-4911-a860-e50e80d60d25', '194c5b31-1213-4aa0-a7cf-a5b8bba54192')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('169c4dd4-42a0-4daf-8437-9151bb3887f8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '668d892b-5335-490e-8883-b913c8c4818f', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 30, 30, 'Lecture', 'Courses with lecture', null, '1a0b91be-e456-4125-bfc3-993e424d2a5d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dde6e3c3-0349-4b74-b922-ccc7ea95085b', 'kuali.attribute.max.enrollment.is.estimate', '1a0b91be-e456-4125-bfc3-993e424d2a5d', 0, 'cbae9900-7bee-4765-a1af-a30fae6c2dc4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ed2d206a-0ff2-440b-a772-bc2abb256095', 'kuali.attribute.course.evaluation.indicator', '1a0b91be-e456-4125-bfc3-993e424d2a5d', 0, '1958e12f-3f3f-4b5d-92d1-76de5f21f15f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6094e4f9-9da3-4a14-a056-5963f6372147', 'kuali.attribute.nonstd.ts.indicator', '1a0b91be-e456-4125-bfc3-993e424d2a5d', 0, '7b3f35b3-8da0-4ab4-af39-2fd0661046d5')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('455040f8-5c24-4e90-a998-9c3d578cb522', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'B', '', '', '', '1a0b91be-e456-4125-bfc3-993e424d2a5d', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '32b0bf4c-afa1-49b8-bf35-bda0d4ee6d7c')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('09a09220-005a-4fbd-8075-7069ec3db153', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '1a0b91be-e456-4125-bfc3-993e424d2a5d', '', 'kuali.lu.code.honorsOffering', 0, '25a50e1d-e0a2-4c15-a6dc-57c9d7d2bf2d')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('5e422d37-e63a-4b47-93ca-5d611a3d53fc', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:01:32.143', '', '', '1a0b91be-e456-4125-bfc3-993e424d2a5d', '', 'H.VICKIP', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '72af57d5-283b-45ca-9b33-5bf266758fc6')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a7f01742-d544-4a1a-b2f9-90261e13ac17', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:32.19', '', 'ENGL243-FO-AO', '194c5b31-1213-4aa0-a7cf-a5b8bba54192', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL243-FO-AO', '1a0b91be-e456-4125-bfc3-993e424d2a5d', '7ca5e82e-8825-438c-958e-93918ad224d0')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('e8d3971c-9e52-4cfe-ad10-e8ce805f7cd5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for ENGL243 - B', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '72d42230-ad59-4d8c-aa1d-c0efd6666032')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('1eaacba4-3c7d-43bc-ac06-df3a6a3c242d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for ENGL243 - B', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '72d42230-ad59-4d8c-aa1d-c0efd6666032', '9d410d02-5167-471d-8058-ae9ee452332f')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('59b30421-b4d5-46c9-869e-2ecbcb1d437f', 0, '9d410d02-5167-471d-8058-ae9ee452332f', 'fd2d93c6-4cb7-44f8-9df9-c7a026f1edcf')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('72d42230-ad59-4d8c-aa1d-c0efd6666032', '1a0b91be-e456-4125-bfc3-993e424d2a5d')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('fd2d93c6-4cb7-44f8-9df9-c7a026f1edcf', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('fd2d93c6-4cb7-44f8-9df9-c7a026f1edcf', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('fd2d93c6-4cb7-44f8-9df9-c7a026f1edcf', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('fd2d93c6-4cb7-44f8-9df9-c7a026f1edcf', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('f8201092-7f5a-4d59-811e-88eb9ce62ae7', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '1c054088-a3c8-4c1f-9c3a-4f57d7336159')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('1c054088-a3c8-4c1f-9c3a-4f57d7336159', '1a0b91be-e456-4125-bfc3-993e424d2a5d')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('1c054088-a3c8-4c1f-9c3a-4f57d7336159', '194c5b31-1213-4aa0-a7cf-a5b8bba54192')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('349e0013-b3e1-4442-9e78-b4698dbe8e84', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '194c5b31-1213-4aa0-a7cf-a5b8bba54192', 'CL 1', 'CL 1', 'da0a75ed-ca6b-49a7-8620-180405910045')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('66c7151f-a273-4f79-8ddd-39a5e17f99f9', 'kuali.lui.type.activity.offering.lecture', 'da0a75ed-ca6b-49a7-8620-180405910045', '97624a73-667b-41b9-b8f4-a63b5103f800')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('97624a73-667b-41b9-b8f4-a63b5103f800', '96be442b-7368-4fa9-9bd8-a6061eabf6e0')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('97624a73-667b-41b9-b8f4-a63b5103f800', '1a0b91be-e456-4125-bfc3-993e424d2a5d')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='da0a75ed-ca6b-49a7-8620-180405910045' where ID='97624a73-667b-41b9-b8f4-a63b5103f800'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('7d3a3360-a64b-43ab-81fe-909767145774', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'b3d8efe2-cbe7-4e50-a72f-8f4b2b4ea8ad', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '1bbb71ee-47ec-4ef1-937b-a4ea8036ff65')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fcd2b6c5-7ab6-4dde-86d8-74dd32fcfeae', 'kuali.attribute.registration.group.aocluster.id', '1bbb71ee-47ec-4ef1-937b-a4ea8036ff65', 'da0a75ed-ca6b-49a7-8620-180405910045', '7cb7e209-cdf3-4c80-8a60-60398b03f5cf')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('72a831c4-5be7-4aa3-805f-79f1bc7b5298', 'kuali.attribute.registration.group.is.generated', '1bbb71ee-47ec-4ef1-937b-a4ea8036ff65', 1, 'b5843c86-f57b-4d31-92dc-20b33e733b41')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('53c54004-1de7-4073-9933-46e4ee2ef1d0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '1bbb71ee-47ec-4ef1-937b-a4ea8036ff65', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '81bfbbb3-7f3e-4626-a192-9beb961cf121')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('8e4cfa0e-ebc4-4f2b-9330-282b5c0e6679', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:32.679', '', 'ENGL243-FO-RG', '194c5b31-1213-4aa0-a7cf-a5b8bba54192', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL243-FO-RG', '1bbb71ee-47ec-4ef1-937b-a4ea8036ff65', 'd472957a-7132-4c47-bb1d-3c9c48217880')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('578a86f8-848b-4c7e-ba1e-e644e7f8e1e0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:32.694', '', 'ENGL243-RG-AO', '1bbb71ee-47ec-4ef1-937b-a4ea8036ff65', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL243-RG-AO', '96be442b-7368-4fa9-9bd8-a6061eabf6e0', 'b678d250-eb17-4c11-8740-c7bcad36f8e7')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('eb5fe71f-42f0-4d1d-b381-10ed68ee2206', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'b3d8efe2-cbe7-4e50-a72f-8f4b2b4ea8ad', '', '', '1002', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1002', '1002', '', 'd688da9c-9b8f-4792-8ce3-9853be11116f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ad5ce911-8f8d-4fad-8f42-5f72f40b36e0', 'kuali.attribute.registration.group.aocluster.id', 'd688da9c-9b8f-4792-8ce3-9853be11116f', 'da0a75ed-ca6b-49a7-8620-180405910045', '944c8cad-0c0b-4a67-8d79-29e4760ae077')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c654e58b-603f-4fc5-8b23-a0445ad73d37', 'kuali.attribute.registration.group.is.generated', 'd688da9c-9b8f-4792-8ce3-9853be11116f', 1, '34509141-cde0-4ee5-91af-36ddbe4450cc')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('dab1c6cb-7a31-43a4-847e-4c4d7137191d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'd688da9c-9b8f-4792-8ce3-9853be11116f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '00c3adab-ac28-41e6-8b05-c760cf162a90')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('d1403eac-8a68-4cd1-8257-5df6649696a4', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:32.758', '', 'ENGL243-FO-RG', '194c5b31-1213-4aa0-a7cf-a5b8bba54192', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL243-FO-RG', 'd688da9c-9b8f-4792-8ce3-9853be11116f', 'b53f6426-8f5d-46f3-83c6-4b8e6a3bc3b4')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('505769b7-f2ca-4e79-8cd9-a2ef13c57ec5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:32.774', '', 'ENGL243-RG-AO', 'd688da9c-9b8f-4792-8ce3-9853be11116f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL243-RG-AO', '1a0b91be-e456-4125-bfc3-993e424d2a5d', '7fbfbbbe-48d2-4547-8e70-08e21e9524e4')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('23abe0c2-e3dd-4ecf-a964-3fb18e2e48ef', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'fbaef52c-442b-4761-85e7-5ab3b238c627', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Introduction to film as art form and how films create meaning. Basic film terminology; fundamental principles of film form, film narrative, and film history. Examination of film technique and style over past one hundred years. Social and economic functions of film within broader institutional, economic, and cultural contexts.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL245 CO', 'Introduction to film as art form and how films create meaning. Basic film terminology; fundamental principles of film form, film narrative, and film history. Examination of film technique and style over past one hundred years. Social and economic functions of film within broader institutional, economic, and cultural contexts.', null, '6aab8f79-d921-49e1-98c4-f3d031f24081')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('12ad2b88-1942-4d76-80a3-9f07f5e4819d', 'kuali.attribute.course.number.internal.suffix', '6aab8f79-d921-49e1-98c4-f3d031f24081', 'A', 'b3b19319-8959-4738-9432-b33cb8bacdb8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f29cc57b-4042-43db-ab9d-9ef557194fb6', 'kuali.attribute.wait.list.type.key', '6aab8f79-d921-49e1-98c4-f3d031f24081', null, 'd57f8362-aad3-498b-ac48-92f78a436a9a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4e1c1785-5caa-411c-a177-487378c71f0e', 'kuali.attribute.final.exam.driver', '6aab8f79-d921-49e1-98c4-f3d031f24081', 'kuali.lu.exam.driver.ActivityOffering', '70ae1d18-2e1c-428c-a8ec-39509fd7cac5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('511bcc91-c4e6-40ce-b037-ca8c8df64874', 'kuali.attribute.final.exam.use.matrix', '6aab8f79-d921-49e1-98c4-f3d031f24081', 1, 'c98f3f64-4816-406f-a535-626e6a6c813e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0d7093f5-afd9-4913-98cc-eeceeb0199f9', 'kuali.attribute.wait.list.indicator', '6aab8f79-d921-49e1-98c4-f3d031f24081', 0, '50833bd3-75b0-4895-aa9d-5ab6ac8333ff')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b715799a-90d5-4792-91e9-5a493450a7b0', 'kuali.attribute.where.fees.attached.flag', '6aab8f79-d921-49e1-98c4-f3d031f24081', 0, '25202823-35fc-4d95-b45f-bde585fc6fb6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1dbb5a11-9e11-4bcc-a7f8-053ce24d2187', 'kuali.attribute.funding.source', '6aab8f79-d921-49e1-98c4-f3d031f24081', '', '3f42992e-7bcb-48f4-97c2-434d680fab82')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('49c68072-056a-4ce0-8e15-aa670d6705c1', 'kuali.attribute.wait.list.level.type.key', '6aab8f79-d921-49e1-98c4-f3d031f24081', 'AO', '7b6f9b4e-4e7f-4e91-99d6-973806be455e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f594547e-47df-4553-b363-d941aa21fb33', 'kuali.attribute.course.evaluation.indicator', '6aab8f79-d921-49e1-98c4-f3d031f24081', 0, '907ed4fd-7432-4a86-bf18-5a3f852867f2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('975eaa07-ada0-44d5-bccd-d64cc6625dbc', 'kuali.attribute.final.exam.indicator', '6aab8f79-d921-49e1-98c4-f3d031f24081', 'STANDARD', '73cf087d-d381-479b-80c1-3d9a7e40b084')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('ba4526e9-5835-412b-8337-8c1a899d5448', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'ENGL245', 'ENGL', '', 'Film Form and Culture', '6aab8f79-d921-49e1-98c4-f3d031f24081', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7ea42e67-17d4-4ecb-8aa3-0d5894980653')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('c3ad9262-e3f6-448e-a379-dcb8f05cc9e0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '6aab8f79-d921-49e1-98c4-f3d031f24081', '', 'kuali.lu.code.honorsOffering', '', '4ae5119e-899a-479d-ac19-c138d1ca660b')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('6aab8f79-d921-49e1-98c4-f3d031f24081', '2677933260', '088919c6-7b8d-46db-981d-db81a6658b79')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('6aab8f79-d921-49e1-98c4-f3d031f24081', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('6aab8f79-d921-49e1-98c4-f3d031f24081', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('6aab8f79-d921-49e1-98c4-f3d031f24081', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('6aab8f79-d921-49e1-98c4-f3d031f24081', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('6aab8f79-d921-49e1-98c4-f3d031f24081', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('e7b5829f-8d7f-4ddc-a87c-8041306dcd1a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '2fb81cbf-36e1-4096-ae43-541d964af238', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'c7dbe9c7-e8a6-423a-b862-96777b4199a2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('637a9fe1-d1a4-4411-a21b-15d4004061f2', 'regCodePrefix', 'c7dbe9c7-e8a6-423a-b862-96777b4199a2', '1', '01fcd5bd-8848-4b61-abc3-9e287cebd196')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('17681095-b725-413c-b48f-eed7e5e9a7f9', 'kuali.attribute.final.exam.level.type', 'c7dbe9c7-e8a6-423a-b862-96777b4199a2', 'kuali.lu.type.activity.Lecture', 'df6c5725-8f12-4e5b-987e-cdcd1b568982')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ba4a3f84-def9-42df-ae43-d901cccecd61', 'kuali.attribute.grade.roster.level.type.key', 'c7dbe9c7-e8a6-423a-b862-96777b4199a2', 'kuali.lui.type.activity.offering.lecture', '6f378aa6-990b-489e-b335-34c4426c82a2')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('101735b1-dde6-467d-8098-599a1231359b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', 'c7dbe9c7-e8a6-423a-b862-96777b4199a2', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7edb3522-f2e6-4ead-a869-a073417a3261')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('c7dbe9c7-e8a6-423a-b862-96777b4199a2', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('991462fa-e6e1-4a83-9116-5b59c729714c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:37.126', '', 'ENGL245-CO-FO', '6aab8f79-d921-49e1-98c4-f3d031f24081', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL245-CO-FO', 'c7dbe9c7-e8a6-423a-b862-96777b4199a2', 'd3a11916-a7dc-4c4a-b0f0-43cc01282259')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('b2376bec-da12-42f8-9569-7bfbc592fd1b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '3853b95e-b7b9-49f7-b981-10a1202d9c49', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, '0fccb902-c2c1-4d33-9c03-8227a4d11063')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b2e627a6-03d0-4cc0-b1e4-8a75af558867', 'kuali.attribute.max.enrollment.is.estimate', '0fccb902-c2c1-4d33-9c03-8227a4d11063', 0, '60c6fdc4-0fd7-4232-b8a8-5c9cf25dd3ac')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ab8ba2b9-502f-418c-b508-1913f08ec06f', 'kuali.attribute.course.evaluation.indicator', '0fccb902-c2c1-4d33-9c03-8227a4d11063', 0, 'b6f03ee9-bfd0-4c8a-b35f-907d1b8c44de')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b8788716-610b-41a5-9fe6-83997cc64179', 'kuali.attribute.nonstd.ts.indicator', '0fccb902-c2c1-4d33-9c03-8227a4d11063', 0, '98bc1b6a-a1d9-4bc8-93d6-b7d767c33bef')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('cfec5bff-5740-40c2-b895-1efd7d49851b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '0fccb902-c2c1-4d33-9c03-8227a4d11063', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '34742d49-9219-4f19-b7d7-84154df062c1')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('4605b011-a348-417d-8f47-c0b2f971fdd4', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '0fccb902-c2c1-4d33-9c03-8227a4d11063', '', 'kuali.lu.code.honorsOffering', 0, '32695370-52f7-4d87-b9e6-c9a8a62bcccc')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('5517bbb6-2223-4e28-a4b6-e9efdac2a87e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:01:37.224', '', '', '0fccb902-c2c1-4d33-9c03-8227a4d11063', '', 'H.KEITHN', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '0d7624d1-5016-43c7-bc45-95f9c1150fff')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('6cd68305-5824-4efd-bdc5-716978adc96f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:37.257', '', 'ENGL245-FO-AO', 'c7dbe9c7-e8a6-423a-b862-96777b4199a2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL245-FO-AO', '0fccb902-c2c1-4d33-9c03-8227a4d11063', '8690a45e-05e5-446a-b7e2-1b4a8d7dfe28')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('4e73dbd7-a07e-47ef-a2e6-fe214ba7ae2e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for ENGL245 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'dba43c7e-3899-4189-9ae6-187476411543')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('1fae7128-6317-4959-88a9-87694826a17c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for ENGL245 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'dba43c7e-3899-4189-9ae6-187476411543', '26ed204f-4ee0-44fb-9292-9c24ff0fa400')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('25450452-4022-47e7-9a69-eedc02b3b409', 0, '26ed204f-4ee0-44fb-9292-9c24ff0fa400', '54f17cef-abc1-417b-8c5f-a7219dd604e0')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('dba43c7e-3899-4189-9ae6-187476411543', '0fccb902-c2c1-4d33-9c03-8227a4d11063')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('54f17cef-abc1-417b-8c5f-a7219dd604e0', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('54f17cef-abc1-417b-8c5f-a7219dd604e0', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('54f17cef-abc1-417b-8c5f-a7219dd604e0', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('54f17cef-abc1-417b-8c5f-a7219dd604e0', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('1cd30eb3-c7d3-4d0e-a852-138985fdaa01', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', 'cbc74895-26ce-441c-a5b3-8dcc375765f3')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('cbc74895-26ce-441c-a5b3-8dcc375765f3', '0fccb902-c2c1-4d33-9c03-8227a4d11063')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('cbc74895-26ce-441c-a5b3-8dcc375765f3', 'c7dbe9c7-e8a6-423a-b862-96777b4199a2')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('fac28a15-73df-4112-9c5f-c4535f69ca13', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '46190799-c191-458f-a242-7084e92ec5da', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 20, 20, 'Lecture', 'Courses with lecture', null, '00c8a4c1-e0ae-4d13-ba2c-161c1b317868')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d486b207-4449-4ff7-9790-201b9f12d4d3', 'kuali.attribute.nonstd.ts.indicator', '00c8a4c1-e0ae-4d13-ba2c-161c1b317868', 0, 'e64c9fa7-b7f0-490e-bacd-fca47e8aa738')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e2bde051-fa56-4ec9-a61a-6370f3ffb043', 'kuali.attribute.course.evaluation.indicator', '00c8a4c1-e0ae-4d13-ba2c-161c1b317868', 0, 'b7449570-1939-4997-8489-2032a8df96c6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('44ecdc99-7f10-4abb-9acf-1dfb1a53091b', 'kuali.attribute.max.enrollment.is.estimate', '00c8a4c1-e0ae-4d13-ba2c-161c1b317868', 0, '93183c70-ebe0-43dd-8182-b188f3d62300')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('1b7fc813-7b81-43e1-a904-9887f967ba12', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'B', '', '', '', '00c8a4c1-e0ae-4d13-ba2c-161c1b317868', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '0bc37b83-735e-4da7-b7b0-58adda570006')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('f8fcb4a2-7154-46a4-b52c-34b5ddbdc7fc', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '00c8a4c1-e0ae-4d13-ba2c-161c1b317868', '', 'kuali.lu.code.honorsOffering', 0, 'd72e64d8-d853-4c3e-a11a-cc9386463295')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('53aa63ee-b4e6-48df-9afd-ec390e1628b5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:01:37.655', '', '', '00c8a4c1-e0ae-4d13-ba2c-161c1b317868', '', 'F.SYDNEYL', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'c0a1bbe4-1786-4c0c-bdb8-7da91c98b96f')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('8ba8bbb8-d67f-47de-8ea0-89bb84642217', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:37.697', '', 'ENGL245-FO-AO', 'c7dbe9c7-e8a6-423a-b862-96777b4199a2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL245-FO-AO', '00c8a4c1-e0ae-4d13-ba2c-161c1b317868', '7c4848fe-b7b7-47d1-a5ab-1bace5f4173e')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('067e8d54-13df-4659-b579-fcff04009309', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for ENGL245 - B', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '2f4eea26-4d6c-4538-a59c-ebb1bcd4a06e')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('b5851dc1-a767-48b0-a28d-b84bede6ab9a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for ENGL245 - B', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '2f4eea26-4d6c-4538-a59c-ebb1bcd4a06e', 'dc9f0675-c0e9-47ea-8533-2ecf405327da')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('b1cac0d6-39ab-4f9f-b404-7d6ab53c13ed', 0, 'dc9f0675-c0e9-47ea-8533-2ecf405327da', 'd610961e-465a-4323-9937-000351b6bfbe')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('2f4eea26-4d6c-4538-a59c-ebb1bcd4a06e', '00c8a4c1-e0ae-4d13-ba2c-161c1b317868')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('d610961e-465a-4323-9937-000351b6bfbe', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('d610961e-465a-4323-9937-000351b6bfbe', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('d610961e-465a-4323-9937-000351b6bfbe', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('d610961e-465a-4323-9937-000351b6bfbe', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('81dd4d85-58a8-49fb-88bc-2fe798fa6bdc', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '6cda9369-624e-4519-be2a-47f1895011a2')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('6cda9369-624e-4519-be2a-47f1895011a2', '00c8a4c1-e0ae-4d13-ba2c-161c1b317868')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('6cda9369-624e-4519-be2a-47f1895011a2', 'c7dbe9c7-e8a6-423a-b862-96777b4199a2')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('a1a3e3d5-ee19-4d90-b479-10a1f7f35935', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'c7dbe9c7-e8a6-423a-b862-96777b4199a2', 'CL 1', 'CL 1', '68bb1a89-ce78-4c42-af48-637eb72816df')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('5dfa1a2b-e1bc-4acf-850a-662d2d4f7ecf', 'kuali.lui.type.activity.offering.lecture', '68bb1a89-ce78-4c42-af48-637eb72816df', 'b5cea2be-87c8-40cb-8f45-f916b76aa9c0')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('b5cea2be-87c8-40cb-8f45-f916b76aa9c0', '0fccb902-c2c1-4d33-9c03-8227a4d11063')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('b5cea2be-87c8-40cb-8f45-f916b76aa9c0', '00c8a4c1-e0ae-4d13-ba2c-161c1b317868')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='68bb1a89-ce78-4c42-af48-637eb72816df' where ID='b5cea2be-87c8-40cb-8f45-f916b76aa9c0'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('6f00cc10-cf09-4121-85e3-3972d7eb170c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '2fb81cbf-36e1-4096-ae43-541d964af238', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'd46e8a2f-a329-4a61-b2c9-510f4250f895')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('aca9786b-9654-4e76-9c17-31d2e0151505', 'kuali.attribute.registration.group.aocluster.id', 'd46e8a2f-a329-4a61-b2c9-510f4250f895', '68bb1a89-ce78-4c42-af48-637eb72816df', 'df509c05-bcdd-4512-84f7-26bc4f5c6aa6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0535d8c6-6a69-4a72-a4a3-8b7bf2acd702', 'kuali.attribute.registration.group.is.generated', 'd46e8a2f-a329-4a61-b2c9-510f4250f895', 1, '683c24ec-e9a2-4985-98ee-f06df25552c9')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('81b9a831-70b1-4ea7-af83-a93f558c6cc0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'd46e8a2f-a329-4a61-b2c9-510f4250f895', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a1c80b26-b5d5-4f9e-9a14-7c62630eeef3')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('af92f771-1217-4829-889d-4394ae8df64b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:38.708', '', 'ENGL245-FO-RG', 'c7dbe9c7-e8a6-423a-b862-96777b4199a2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL245-FO-RG', 'd46e8a2f-a329-4a61-b2c9-510f4250f895', '07733f52-63fb-4aee-89bf-6c45a35317d4')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('d0fca4ad-eb30-4607-a6a5-97a3750d5697', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:38.729', '', 'ENGL245-RG-AO', 'd46e8a2f-a329-4a61-b2c9-510f4250f895', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL245-RG-AO', '0fccb902-c2c1-4d33-9c03-8227a4d11063', 'f7371283-b109-4e8b-b7ac-0e7c9702076b')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('075aadff-3fea-4eaa-a6f7-e78f448aa6c4', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '2fb81cbf-36e1-4096-ae43-541d964af238', '', '', '1002', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1002', '1002', '', '71fac1b0-7097-45c3-8740-0d3e7452a1f8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('94ccf826-fd3f-4e06-b531-c737a85588ea', 'kuali.attribute.registration.group.aocluster.id', '71fac1b0-7097-45c3-8740-0d3e7452a1f8', '68bb1a89-ce78-4c42-af48-637eb72816df', '9c183f02-c889-4bab-bbeb-2e0eac0bdb25')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d53e1a7f-8047-4349-b415-3c6a11cb354e', 'kuali.attribute.registration.group.is.generated', '71fac1b0-7097-45c3-8740-0d3e7452a1f8', 1, 'f0091576-cb3b-4ece-ace1-7d0ce64e7cef')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('653cd328-6c6a-4df8-829a-f9e06de2fb6b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '71fac1b0-7097-45c3-8740-0d3e7452a1f8', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '4c623246-801c-45ef-b7f9-00e614191ca2')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('9a41fa04-d455-47bd-b9c2-a4b7cd9efe96', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:38.808', '', 'ENGL245-FO-RG', 'c7dbe9c7-e8a6-423a-b862-96777b4199a2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL245-FO-RG', '71fac1b0-7097-45c3-8740-0d3e7452a1f8', 'a1918903-549d-45b7-8338-2567e5df2b60')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f453d80d-1e2c-44f7-b5e2-f1e3173f3c4d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:38.821', '', 'ENGL245-RG-AO', '71fac1b0-7097-45c3-8740-0d3e7452a1f8', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL245-RG-AO', '00c8a4c1-e0ae-4d13-ba2c-161c1b317868', 'c37226b6-6c5c-44e9-990b-829fa0eebbd7')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ebbea67e-1d00-4574-875c-05aaf9c4e3e5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'CLUID-HIST619-198101000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST619A CO', '.', null, 'b0e0e702-047f-4d60-98a9-29d39133f277')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('71515485-d5f2-4c6e-868e-b448f06eae49', 'kuali.attribute.final.exam.indicator', 'b0e0e702-047f-4d60-98a9-29d39133f277', 'STANDARD', 'e7395ecf-c64c-49f3-b57a-cff25a0841b6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('430d8dc5-4ade-4821-b922-23a083b738fa', 'kuali.attribute.course.number.internal.suffix', 'b0e0e702-047f-4d60-98a9-29d39133f277', 'A', 'abf23f37-0f50-4b54-ab49-b30c920d92b5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2bff5d79-20c3-4265-aca1-bb4642ea65ff', 'kuali.attribute.wait.list.type.key', 'b0e0e702-047f-4d60-98a9-29d39133f277', null, '7d2e7dda-4d28-411e-a309-e589b0240f70')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('63c15d4a-aa02-43c5-8031-fc3784b44e32', 'kuali.attribute.where.fees.attached.flag', 'b0e0e702-047f-4d60-98a9-29d39133f277', 0, '415e35a4-520f-4e9d-8ad7-aa224a8a5085')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a3cb5676-5607-49a0-ab29-b9fcf457f1ae', 'kuali.attribute.wait.list.indicator', 'b0e0e702-047f-4d60-98a9-29d39133f277', 1, '41ea82e4-cc52-4372-99a3-ea980c8c4817')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8ba85bd2-f138-4ec4-bdb6-cadefd61d387', 'kuali.attribute.final.exam.use.matrix', 'b0e0e702-047f-4d60-98a9-29d39133f277', 1, 'e67fe998-c668-4776-bde9-8c08df844b7e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e1264eba-3afb-4f00-81c8-ac8c657218f6', 'kuali.attribute.course.evaluation.indicator', 'b0e0e702-047f-4d60-98a9-29d39133f277', 0, '36489bac-eef0-4741-95ac-74916b9fec57')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('502d5f9b-fcd2-4371-9dbd-27ef7b65ff4a', 'kuali.attribute.wait.list.level.type.key', 'b0e0e702-047f-4d60-98a9-29d39133f277', 'AO', '6b8c4caf-5050-489c-b720-01a1b8611b32')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('adcfc6d2-a1bb-443f-87eb-fba9e5b899dc', 'kuali.attribute.funding.source', 'b0e0e702-047f-4d60-98a9-29d39133f277', '', 'a94c60a2-6170-43ef-9aef-5aef7bcc8196')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4f65b1bd-d55d-4824-a849-ef6927492532', 'kuali.attribute.final.exam.driver', 'b0e0e702-047f-4d60-98a9-29d39133f277', 'kuali.lu.exam.driver.ActivityOffering', '4217a83f-cbec-4552-9299-6ca34b2d2807')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('adb38d62-42bd-49c8-a045-13e89d807eff', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'HIST619A', 'HIST', '', 'Special Topics in History', 'b0e0e702-047f-4d60-98a9-29d39133f277', '', '', 'kuali.lui.identifier.state.active', 'A', 'kuali.lui.identifier.type.official', '', 'dd7091c4-ff6a-48b5-b023-bbd5a3b3731b')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('37c49a33-9436-461e-ba2a-40b23f22b13a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'b0e0e702-047f-4d60-98a9-29d39133f277', '', 'kuali.lu.code.honorsOffering', '', '54860d54-cf3f-4fd3-9dfe-077fb7ac013d')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('b0e0e702-047f-4d60-98a9-29d39133f277', '3213375036', '5fd50a9b-6e49-4c71-a774-d7ed41ed1ae3')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('b0e0e702-047f-4d60-98a9-29d39133f277', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('b0e0e702-047f-4d60-98a9-29d39133f277', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('b0e0e702-047f-4d60-98a9-29d39133f277', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('b0e0e702-047f-4d60-98a9-29d39133f277', 'kuali.creditType.credit.degree.1.0,1.5,2.0,2.5,3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('f109679c-6797-40b5-820c-132a77e612d3', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '0daac3c5-3477-401a-b7ca-8a1968eab2f7', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'd0ea88ee-5e35-48cd-b408-442b902584ff')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c1ae6064-4c40-44a5-8a49-29a92a375e87', 'kuali.attribute.final.exam.level.type', 'd0ea88ee-5e35-48cd-b408-442b902584ff', 'kuali.lu.type.activity.Lecture', '26c9a4b3-a370-4d39-b7e9-bb3290b1a26f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0e514426-e29d-4a51-87a0-ecef84e4330d', 'kuali.attribute.grade.roster.level.type.key', 'd0ea88ee-5e35-48cd-b408-442b902584ff', 'kuali.lui.type.activity.offering.lecture', '1ee72060-3bb0-4713-9eac-269446ade10d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('09d455cd-094c-4973-81ce-a7715aa3252f', 'regCodePrefix', 'd0ea88ee-5e35-48cd-b408-442b902584ff', '1', '4c61fab8-f345-4e06-8b4a-db0528500d35')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7b0455cb-6aa7-40c6-8ffc-6b16c2e2bddf', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', 'd0ea88ee-5e35-48cd-b408-442b902584ff', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '18795c10-0710-4bdf-b989-f631aad6b3b8')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('d0ea88ee-5e35-48cd-b408-442b902584ff', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('d99287e3-30a5-4ebc-a2e7-57e3d0b07ff1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:42.012', '', 'HIST619A-CO-FO', 'b0e0e702-047f-4d60-98a9-29d39133f277', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST619A-CO-FO', 'd0ea88ee-5e35-48cd-b408-442b902584ff', '44394acd-506b-438b-aa2c-b69b9f277694')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('dde4b64b-b02c-4440-ba8e-983b3e842b59', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '82a1e5ca-81ac-4a6b-8fc1-5f4cb0000660', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 10, 10, 'Lecture', 'Courses with lecture', null, '0022b87a-cb6c-43ee-bc3a-db4ac10c1190')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a78da956-6059-4721-b42a-0fdd59d95749', 'kuali.attribute.nonstd.ts.indicator', '0022b87a-cb6c-43ee-bc3a-db4ac10c1190', 0, '00e2616d-c084-4d8d-a3d9-ce125c872d75')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7445e0e9-2bec-49ab-ba07-3f8d5d49802d', 'kuali.attribute.max.enrollment.is.estimate', '0022b87a-cb6c-43ee-bc3a-db4ac10c1190', 0, 'a255d795-abc1-4079-9f41-2f9c9e7fe597')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6b59140e-ae66-4711-9776-71a16ccab4df', 'kuali.attribute.course.evaluation.indicator', '0022b87a-cb6c-43ee-bc3a-db4ac10c1190', 0, '3c992048-8584-4f44-87b6-fa027ccbd1dc')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('dfd87624-eabb-44cc-9d4d-4a4b9742d937', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '0022b87a-cb6c-43ee-bc3a-db4ac10c1190', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '5157d266-a11e-4259-be18-c9bd49c6a123')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('4c3e07f7-b2d1-473c-99e7-c8bba07829a1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '0022b87a-cb6c-43ee-bc3a-db4ac10c1190', '', 'kuali.lu.code.honorsOffering', 0, '8ecdb624-7480-42ff-bed6-2498a18cdbea')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('f3a726db-45aa-4e98-9669-e09e9f81b410', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:01:42.437', '', '', '0022b87a-cb6c-43ee-bc3a-db4ac10c1190', '', 'S.KENNETHI', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'ab53c13c-178d-4b93-ae57-c85cce436f93')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('2bfff767-5f16-4997-ace8-b9060c0cc0f7', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:42.476', '', 'HIST619A-FO-AO', 'd0ea88ee-5e35-48cd-b408-442b902584ff', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST619A-FO-AO', '0022b87a-cb6c-43ee-bc3a-db4ac10c1190', '1e21188f-aacd-41c6-a57a-1b3fe7476afb')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('d1bb5c31-2f0f-46e6-bbef-0cba4631fd3f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for HIST619A - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'd3c81596-dae2-4f72-91a3-cff89fddfc8f')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('17f9ddf6-5693-4155-9960-dd3b8a9bfde4', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for HIST619A - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'd3c81596-dae2-4f72-91a3-cff89fddfc8f', '2c263e8b-afbe-4f77-9b19-fde1cde18823')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('1899f5b2-fe96-4809-80ce-1a45dd4b70e4', 1, '2c263e8b-afbe-4f77-9b19-fde1cde18823', 'af2917bc-fbe0-4c33-99d6-1383313145ba')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('d3c81596-dae2-4f72-91a3-cff89fddfc8f', '0022b87a-cb6c-43ee-bc3a-db4ac10c1190')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('af2917bc-fbe0-4c33-99d6-1383313145ba', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('3a3b18dc-1981-4364-9944-d102bd2a1830', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'a998f338-a882-4d5d-bc82-e8d23695dcef')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('a998f338-a882-4d5d-bc82-e8d23695dcef', '0022b87a-cb6c-43ee-bc3a-db4ac10c1190')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('a998f338-a882-4d5d-bc82-e8d23695dcef', 'd0ea88ee-5e35-48cd-b408-442b902584ff')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('22d09a21-0633-4cde-a836-a646a9b3d7a1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'd0ea88ee-5e35-48cd-b408-442b902584ff', 'CL 1', 'CL 1', '856a8dac-cd91-4fa4-ad7d-6203fd9b6a58')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('be10718f-9d5e-4db6-8871-63adc6ee84ee', 'kuali.lui.type.activity.offering.lecture', '856a8dac-cd91-4fa4-ad7d-6203fd9b6a58', 'e36dd797-519f-4fd2-b441-983bdc4ab853')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('e36dd797-519f-4fd2-b441-983bdc4ab853', '0022b87a-cb6c-43ee-bc3a-db4ac10c1190')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='856a8dac-cd91-4fa4-ad7d-6203fd9b6a58' where ID='e36dd797-519f-4fd2-b441-983bdc4ab853'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('91ddf9ae-2ba8-466a-bc31-412c81be50f8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '0daac3c5-3477-401a-b7ca-8a1968eab2f7', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '24dd9be4-cb00-4265-b666-f839602cdfde')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('25e98f49-c42c-4aa0-b61e-65666a44617e', 'kuali.attribute.registration.group.aocluster.id', '24dd9be4-cb00-4265-b666-f839602cdfde', '856a8dac-cd91-4fa4-ad7d-6203fd9b6a58', '830bf520-432e-49a1-a4da-26a6e9414eec')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('52d29057-86ff-4d7e-9461-dbba97ef4be2', 'kuali.attribute.registration.group.is.generated', '24dd9be4-cb00-4265-b666-f839602cdfde', 1, 'fba03bec-ed06-4a07-bc9f-cbf8d3097739')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('056aa55b-8a24-492b-9ae5-972dc38c0caf', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '24dd9be4-cb00-4265-b666-f839602cdfde', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '3eb4d679-346a-45a9-9845-bb0b1e8a7d8a')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('65c2f3eb-0218-41d8-9c9a-b8e3b8168fb6', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:43.033', '', 'HIST619A-FO-RG', 'd0ea88ee-5e35-48cd-b408-442b902584ff', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST619A-FO-RG', '24dd9be4-cb00-4265-b666-f839602cdfde', '83436654-3e25-43ac-9927-1c78c43210b5')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f2d569a9-532f-4153-b575-654045e269b9', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:43.054', '', 'HIST619A-RG-AO', '24dd9be4-cb00-4265-b666-f839602cdfde', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST619A-RG-AO', '0022b87a-cb6c-43ee-bc3a-db4ac10c1190', 'a91a3327-6668-458a-8bbb-dd807e7f016d')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('049d65c5-3918-4ffa-a931-87aad9381278', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'CLUID-HIST319-198001000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST319D CO', '.', null, 'e51868ab-ca03-4bee-bc8a-a025df5e47d5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2cbb3f8e-bab3-4582-b75e-3cd070f7afb7', 'kuali.attribute.course.number.internal.suffix', 'e51868ab-ca03-4bee-bc8a-a025df5e47d5', 'A', '25b0a483-3b99-4356-bfea-fb6ff7d358ae')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d23a8cf9-8896-4dbe-a369-4e21b039add8', 'kuali.attribute.final.exam.use.matrix', 'e51868ab-ca03-4bee-bc8a-a025df5e47d5', 1, '346056d4-0485-4668-b538-c5a19bc98706')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ae939673-05f5-4b7b-8653-86e5df9a0eee', 'kuali.attribute.where.fees.attached.flag', 'e51868ab-ca03-4bee-bc8a-a025df5e47d5', 0, '3a68b599-1186-4a32-875b-9d75f3ab435d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9187bb79-0b41-46ad-84e6-f77f0544b451', 'kuali.attribute.wait.list.level.type.key', 'e51868ab-ca03-4bee-bc8a-a025df5e47d5', 'AO', '5a7b402c-ae34-4203-86ca-adb29b9dd60c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('97f1949e-592b-4486-a1a7-c09775954c31', 'kuali.attribute.course.evaluation.indicator', 'e51868ab-ca03-4bee-bc8a-a025df5e47d5', 0, '898709fc-094b-41dd-866d-ea008c7c0b1e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fcbe80c0-e9db-4be3-b06e-1717bc30c196', 'kuali.attribute.final.exam.driver', 'e51868ab-ca03-4bee-bc8a-a025df5e47d5', 'kuali.lu.exam.driver.ActivityOffering', 'cbd8c566-4377-4585-9c7d-1e087f745498')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f3a98c00-597d-4209-b7dc-d03448904f12', 'kuali.attribute.wait.list.type.key', 'e51868ab-ca03-4bee-bc8a-a025df5e47d5', null, 'a223d005-5ce8-419d-8391-566753e45144')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('01a83039-3dba-4c95-8d36-0931ec7ef878', 'kuali.attribute.final.exam.indicator', 'e51868ab-ca03-4bee-bc8a-a025df5e47d5', 'STANDARD', '2760fdff-d84c-4793-84c6-7b62a9f4f142')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2069e45a-0494-4aa3-b0fb-168efa0bfc72', 'kuali.attribute.funding.source', 'e51868ab-ca03-4bee-bc8a-a025df5e47d5', '', '03401838-ba06-493c-b40c-c2c8aafaf425')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('97216e82-b485-4ecc-8fe1-116f4e53d1dd', 'kuali.attribute.wait.list.indicator', 'e51868ab-ca03-4bee-bc8a-a025df5e47d5', 1, '9da07839-cac3-422a-a6fa-f03185a2f66f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('721aca0d-a4a8-43eb-ac85-241d310f14ae', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'HIST319D', 'HIST', '', 'Special Topics in History', 'e51868ab-ca03-4bee-bc8a-a025df5e47d5', '', '', 'kuali.lui.identifier.state.active', 'D', 'kuali.lui.identifier.type.official', '', '05090e68-788b-4b58-8409-49ccd9ab8c18')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('f1e0d909-ce6c-4263-8d30-a676b1aa9000', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'e51868ab-ca03-4bee-bc8a-a025df5e47d5', '', 'kuali.lu.code.honorsOffering', '', 'fd95590e-02db-4259-bc1f-229a420082b1')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('e51868ab-ca03-4bee-bc8a-a025df5e47d5', '3213375036', 'dd8335da-b1f4-4f2e-9444-7b1224941123')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('e51868ab-ca03-4bee-bc8a-a025df5e47d5', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e51868ab-ca03-4bee-bc8a-a025df5e47d5', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e51868ab-ca03-4bee-bc8a-a025df5e47d5', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e51868ab-ca03-4bee-bc8a-a025df5e47d5', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e51868ab-ca03-4bee-bc8a-a025df5e47d5', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('fb494343-0dd3-401c-8039-d9b90434ab1a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'ccb3c17e-6eaf-4095-85b7-648120717da9', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '0aac25b5-f8c1-40f3-9a36-1f452399e562')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f1915432-c533-4e3a-9ad2-0f882e559856', 'kuali.attribute.final.exam.level.type', '0aac25b5-f8c1-40f3-9a36-1f452399e562', 'kuali.lu.type.activity.Lecture', 'e461e80f-d57a-447e-a774-6b94d32a8b11')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('36135532-e730-4a1e-8c9b-ef3bd459c8c3', 'regCodePrefix', '0aac25b5-f8c1-40f3-9a36-1f452399e562', '1', '66482927-7df0-4127-aadd-dcf39c760d7a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0472a109-af70-4e8f-a181-7564d6a9c05b', 'kuali.attribute.grade.roster.level.type.key', '0aac25b5-f8c1-40f3-9a36-1f452399e562', 'kuali.lui.type.activity.offering.lecture', 'cf695752-8937-4950-9c46-3770bdc2655b')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c969f387-c773-4e19-8dd8-ac4b10f9fba1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '0aac25b5-f8c1-40f3-9a36-1f452399e562', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '08fbb6dd-ad8c-4107-92b4-b94fd6e638d6')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('0aac25b5-f8c1-40f3-9a36-1f452399e562', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f4ef2602-e92e-4c10-b7bf-7630ad784fdc', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:45.213', '', 'HIST319D-CO-FO', 'e51868ab-ca03-4bee-bc8a-a025df5e47d5', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST319D-CO-FO', '0aac25b5-f8c1-40f3-9a36-1f452399e562', '462f6503-5958-4b25-8b00-c7f572b612f1')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('66e0ca05-9848-49dd-9d93-e4481adf761a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'dc4eb45c-86c1-439b-9b36-78c9f97bb5a2', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, '531ac831-6dba-43f4-bc36-bb37520663dc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2ae2d46c-1529-4127-b4fe-6eaecd2cb2d8', 'kuali.attribute.max.enrollment.is.estimate', '531ac831-6dba-43f4-bc36-bb37520663dc', 0, '3688558c-7412-4e09-8c49-2a9e01cdc92e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('eac63171-f3dd-4232-b4fa-5ca385b40b36', 'kuali.attribute.nonstd.ts.indicator', '531ac831-6dba-43f4-bc36-bb37520663dc', 0, '12e96371-fd2c-489d-9c6c-2456efc3c10e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('95aae59c-92cf-40d6-a637-11df6e0b6e54', 'kuali.attribute.course.evaluation.indicator', '531ac831-6dba-43f4-bc36-bb37520663dc', 0, 'e7b1db3d-1b7a-48b0-a025-c2cbe7843e96')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('9174b4b5-8ba4-4788-93b7-c60e9b6652d3', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '531ac831-6dba-43f4-bc36-bb37520663dc', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c2a3f5eb-eb4a-4d2b-acf5-680197db8948')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('5ea35eb3-2f6f-4886-8651-d609b756609c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '531ac831-6dba-43f4-bc36-bb37520663dc', '', 'kuali.lu.code.honorsOffering', 0, '32ab416d-07dd-45d6-af34-41e20816cc8a')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('6f729f7b-a696-4187-baf6-1ad112344fd7', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:01:45.311', '', '', '531ac831-6dba-43f4-bc36-bb37520663dc', '', 'D.ALEXD', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '32784a58-be6b-4829-b608-4ebbb8454d77')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('5e3d7fcb-c0ff-402c-9265-6369848c5d46', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:45.346', '', 'HIST319D-FO-AO', '0aac25b5-f8c1-40f3-9a36-1f452399e562', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST319D-FO-AO', '531ac831-6dba-43f4-bc36-bb37520663dc', 'c5322125-1802-4e8e-a81c-80f88236c947')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('0e2941ad-95c2-4b6e-a2b0-4cf2194d48c3', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for HIST319D - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '87db5c4c-1644-4f10-8c59-eb8076e337cd')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('d579f241-0d0f-4edb-8d28-bde08494a388', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A Schedule Request for HIST319D section 0101', 'A Schedule Request for HIST319D section 0101', 'A Schedule Request for HIST319D section 0101', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', 'd2b2aed2-8360-4f0d-a151-4ff438c60253', '87db5c4c-1644-4f10-8c59-eb8076e337cd', '5db5a8e9-c976-4d78-aac0-236d1061bed7')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('0340423c-1131-45ed-9e4e-3386b297df9c', 0, '5db5a8e9-c976-4d78-aac0-236d1061bed7', '2673fa4a-8131-42c9-97ec-844884033f9c')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('87db5c4c-1644-4f10-8c59-eb8076e337cd', '531ac831-6dba-43f4-bc36-bb37520663dc')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('2673fa4a-8131-42c9-97ec-844884033f9c', 'af22fa59-42ab-4d09-9a5b-6980f8b4ae7d')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('2673fa4a-8131-42c9-97ec-844884033f9c', 'b4a5b349-cdc0-4d40-992d-41a07f1cbc2c')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('2673fa4a-8131-42c9-97ec-844884033f9c', 'F0AA72D6DCAFF650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('d39be12d-f301-4f9c-8ba8-d8bf7ac9fee3', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '38574487-216e-4df4-baf6-4b3b8c564ec6')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('eb3a9f0c-35f1-4d3a-b0f4-76f58bdd691e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'd0836a8f-4ff7-448a-9ab6-48f8259b6b77')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='d39be12d-f301-4f9c-8ba8-d8bf7ac9fee3', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='531ac831-6dba-43f4-bc36-bb37520663dc', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='38574487-216e-4df4-baf6-4b3b8c564ec6' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('d0836a8f-4ff7-448a-9ab6-48f8259b6b77', '531ac831-6dba-43f4-bc36-bb37520663dc')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('d0836a8f-4ff7-448a-9ab6-48f8259b6b77', '0aac25b5-f8c1-40f3-9a36-1f452399e562')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('261bc504-1442-47fc-bde9-b77a530fee93', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '0aac25b5-f8c1-40f3-9a36-1f452399e562', 'CL 1', 'CL 1', '248925d0-e84a-4c22-8a07-3ecd6a738f2e')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('e41a46c3-8a8c-4eb3-b073-4184e40260e2', 'kuali.lui.type.activity.offering.lecture', '248925d0-e84a-4c22-8a07-3ecd6a738f2e', '3f8c7e85-e9a5-4749-b077-06c55880842c')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('3f8c7e85-e9a5-4749-b077-06c55880842c', '531ac831-6dba-43f4-bc36-bb37520663dc')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='248925d0-e84a-4c22-8a07-3ecd6a738f2e' where ID='3f8c7e85-e9a5-4749-b077-06c55880842c'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('04c4f558-5980-4526-be3b-1358249072b7', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'ccb3c17e-6eaf-4095-85b7-648120717da9', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'e5f370a2-495a-4480-a268-f95d62bef622')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('571cc787-43d1-4df9-b455-7cc433c5741a', 'kuali.attribute.registration.group.aocluster.id', 'e5f370a2-495a-4480-a268-f95d62bef622', '248925d0-e84a-4c22-8a07-3ecd6a738f2e', '6f32a28b-d51d-4ce5-ae38-65535f34c142')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('43a96093-76e6-4f8e-9373-bb6f4154236a', 'kuali.attribute.registration.group.is.generated', 'e5f370a2-495a-4480-a268-f95d62bef622', 1, 'd40d7194-c1ea-469b-82af-fa368aa14ce3')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6233db07-6d67-433d-b5b9-75bd6d6dfc7a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'e5f370a2-495a-4480-a268-f95d62bef622', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '397dd098-9872-44c5-9516-22338005beaa')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('709eedfb-332f-45da-8532-063e56a89f8e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:46.259', '', 'HIST319D-FO-RG', '0aac25b5-f8c1-40f3-9a36-1f452399e562', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST319D-FO-RG', 'e5f370a2-495a-4480-a268-f95d62bef622', '73fcbfda-0d18-4d47-827c-0df14b182dbc')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a2863344-daa3-4045-8e25-9380d05b8b4f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:46.262', '', 'HIST319D-RG-AO', 'e5f370a2-495a-4480-a268-f95d62bef622', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST319D-RG-AO', '531ac831-6dba-43f4-bc36-bb37520663dc', '63c46d6b-c647-47f3-9580-63ef471a4c36')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d1b8be09-a9c3-428c-96bf-bbf537319a8c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'CLUID-HIST357-199501000000', TIMESTAMP '2014-01-01 19:00:00.0', '', 'American history from the inauguration of Harry S. Truman to the presen with emphasis upon politics and foreign relations, but with consideration of special topics such as radicalism, conservatism, and labor.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST357 CO', 'American history from the inauguration of Harry S. Truman to the presen with emphasis upon politics and foreign relations, but with consideration of special topics such as radicalism, conservatism, and labor.', null, 'b8b6c7d2-8d18-4582-8146-d8f685a9fffb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('30928932-869d-468e-9cc4-6f53f53ede59', 'kuali.attribute.wait.list.level.type.key', 'b8b6c7d2-8d18-4582-8146-d8f685a9fffb', 'AO', 'e26ed1bb-a170-4f44-9bc8-ff3e29585621')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7d2b4689-8532-4a65-82b9-85d197c23ab0', 'kuali.attribute.where.fees.attached.flag', 'b8b6c7d2-8d18-4582-8146-d8f685a9fffb', 0, 'c99e2ad7-f258-4619-9866-ba51dc03ca09')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9ef7af6f-9fc5-416e-9b36-3106300a2419', 'kuali.attribute.wait.list.type.key', 'b8b6c7d2-8d18-4582-8146-d8f685a9fffb', null, '8eea6c59-242c-420d-8483-d4e80a5d8585')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0a5d5114-00eb-4a36-b78d-14ec1f25d06f', 'kuali.attribute.funding.source', 'b8b6c7d2-8d18-4582-8146-d8f685a9fffb', '', '3d23845f-53e0-4df2-8403-8ab92c1b893c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('31bec341-74ef-40d3-9f2a-41237b743656', 'kuali.attribute.wait.list.indicator', 'b8b6c7d2-8d18-4582-8146-d8f685a9fffb', 1, 'beda23be-b585-453c-a6f1-828e8ba8fc62')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d70a093e-e43f-4342-8b65-959ee42a4bf5', 'kuali.attribute.final.exam.driver', 'b8b6c7d2-8d18-4582-8146-d8f685a9fffb', 'kuali.lu.exam.driver.ActivityOffering', 'b44774de-7d79-464c-9b0d-1566c5973cb8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9f73bfe1-442f-4d07-981d-3236128eaff3', 'kuali.attribute.course.number.internal.suffix', 'b8b6c7d2-8d18-4582-8146-d8f685a9fffb', 'A', '16d0c22d-2ee5-4fae-9100-90946a07775c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bc9fc65b-5ae2-46df-bb00-fdc1c7745103', 'kuali.attribute.final.exam.indicator', 'b8b6c7d2-8d18-4582-8146-d8f685a9fffb', 'STANDARD', '0e64904d-1894-49f3-911f-4f5b7384d6d8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d470064f-d849-4ef2-a9a3-7be660eb0e44', 'kuali.attribute.course.evaluation.indicator', 'b8b6c7d2-8d18-4582-8146-d8f685a9fffb', 0, '36239063-8e1e-4cf8-b868-aa261754f3d5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f0602a45-cd0f-4127-98c0-de17de25888a', 'kuali.attribute.final.exam.use.matrix', 'b8b6c7d2-8d18-4582-8146-d8f685a9fffb', 1, '7b562fcb-6fd5-4cce-acab-3118e8ea2cd0')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d7754afb-f76a-4b28-a1c8-5b8b812acba7', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'HIST357', 'HIST', '', 'Recent America: 1945-Present', 'b8b6c7d2-8d18-4582-8146-d8f685a9fffb', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '012d6baa-b59a-4112-8a98-125ad0862ffa')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('c63eb419-0ffa-4e85-8c46-a1bae4d9be72', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'b8b6c7d2-8d18-4582-8146-d8f685a9fffb', '', 'kuali.lu.code.honorsOffering', '', '3313d480-756d-41a4-9ad4-5706c818ea67')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('b8b6c7d2-8d18-4582-8146-d8f685a9fffb', '3213375036', '307935fc-e4e4-4699-8fd6-a89a77c5a78c')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('b8b6c7d2-8d18-4582-8146-d8f685a9fffb', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('b8b6c7d2-8d18-4582-8146-d8f685a9fffb', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('b8b6c7d2-8d18-4582-8146-d8f685a9fffb', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('b8b6c7d2-8d18-4582-8146-d8f685a9fffb', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('b8b6c7d2-8d18-4582-8146-d8f685a9fffb', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d80e202d-5678-4384-9bb0-1fb98b70abf4', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'd48e4b15-08ca-494a-a187-4647da1d2b27', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '2fcdacd4-1004-4fc1-912c-15c05f8c91b9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b398ca28-9f29-4590-9aee-50e33f5f3ada', 'kuali.attribute.final.exam.level.type', '2fcdacd4-1004-4fc1-912c-15c05f8c91b9', 'kuali.lu.type.activity.Lecture', '3d29f7e3-b4aa-4544-b1ae-933b131a7056')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('546603f0-9eb1-4ed2-b83c-1554aa4232e2', 'regCodePrefix', '2fcdacd4-1004-4fc1-912c-15c05f8c91b9', '1', '3586aa61-5bef-455c-8946-dec0b0b4f01a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('426d42f9-6d62-42d9-b58b-92a3aa34ee83', 'kuali.attribute.grade.roster.level.type.key', '2fcdacd4-1004-4fc1-912c-15c05f8c91b9', 'kuali.lui.type.activity.offering.lecture', 'c62448b4-747f-4e87-871d-b224033e43aa')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('1c28091d-0d09-458b-a72b-cd8fc36728a2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '2fcdacd4-1004-4fc1-912c-15c05f8c91b9', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '3f1f17b4-dd8a-4223-b148-116c4c142813')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('2fcdacd4-1004-4fc1-912c-15c05f8c91b9', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a9e4961c-5887-4236-86b4-d5826a0b7ae3', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:48.225', '', 'HIST357-CO-FO', 'b8b6c7d2-8d18-4582-8146-d8f685a9fffb', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST357-CO-FO', '2fcdacd4-1004-4fc1-912c-15c05f8c91b9', 'bf023923-77b8-43e7-91f2-53c5db8a4ad0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('f2a06003-c4bf-4d21-a188-c5cd8763541c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '156c39bd-7086-40a5-8158-8402f885b33b', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, '91f6fa24-7fb6-4907-b86f-352213d3075b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8db71d44-7bdb-4e09-9413-d90bd0b14e49', 'kuali.attribute.max.enrollment.is.estimate', '91f6fa24-7fb6-4907-b86f-352213d3075b', 0, '9d3432cd-9cdf-4e23-b71f-30f88d890306')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9a6a0eb4-dfe1-4e4b-b630-ad778883e16c', 'kuali.attribute.nonstd.ts.indicator', '91f6fa24-7fb6-4907-b86f-352213d3075b', 0, 'daae8fa2-7d62-4ca9-b5c9-8a1acd7f6031')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('428798be-bd0b-4bb8-af01-6746fdcc0439', 'kuali.attribute.course.evaluation.indicator', '91f6fa24-7fb6-4907-b86f-352213d3075b', 0, '040e072a-8f5e-4414-a277-d3e93466a371')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a2a1ac38-5f02-47ae-8e6b-8b958f5a026e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '91f6fa24-7fb6-4907-b86f-352213d3075b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '303345f4-bf8a-4156-8a87-8e521c745495')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('eda75ab6-2c87-44d2-83ae-1ae02bd6c6c0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '91f6fa24-7fb6-4907-b86f-352213d3075b', '', 'kuali.lu.code.honorsOffering', 0, '6fc6a2e6-e5ac-4d57-b2a1-d338042a3990')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('2b60caa4-7358-46ea-ad8e-02fdc192528b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:01:48.33', '', '', '91f6fa24-7fb6-4907-b86f-352213d3075b', '', 'B.WESLEYM', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '80112282-5150-4c45-9a8b-fc2e34c4e900')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('84429093-1f1e-4cf2-8fe6-25a46fc8eb66', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:48.372', '', 'HIST357-FO-AO', '2fcdacd4-1004-4fc1-912c-15c05f8c91b9', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST357-FO-AO', '91f6fa24-7fb6-4907-b86f-352213d3075b', '316b8c97-6904-4e0b-aa60-c4a2d5fdf2dd')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('5a1813de-5fe5-4580-a0b4-27426aaafda8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for HIST357 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '28a81361-0e1b-4076-b8da-3a60731b5c86')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('1e036468-51d2-41cc-b3de-bb0f1fcf1a81', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for HIST357 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '28a81361-0e1b-4076-b8da-3a60731b5c86', '3046de97-3381-4e60-8854-de0d856954cf')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('0349e27c-8e7b-4139-aefe-fc01f5aceee0', 0, '3046de97-3381-4e60-8854-de0d856954cf', '24272bbb-903b-4696-96b8-9aa1bf207116')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('28a81361-0e1b-4076-b8da-3a60731b5c86', '91f6fa24-7fb6-4907-b86f-352213d3075b')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('24272bbb-903b-4696-96b8-9aa1bf207116', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('24272bbb-903b-4696-96b8-9aa1bf207116', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('24272bbb-903b-4696-96b8-9aa1bf207116', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('24272bbb-903b-4696-96b8-9aa1bf207116', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('86cb5253-da86-4521-8821-dff45a366fe2', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '9792ca2e-d2c1-457f-88bc-956da7292f7a')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('f94fee65-66b5-4bf2-93e8-851fba74e7ed', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'b322331a-0b67-40a8-aa5c-2940a7f3c4c1')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='86cb5253-da86-4521-8821-dff45a366fe2', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='91f6fa24-7fb6-4907-b86f-352213d3075b', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='9792ca2e-d2c1-457f-88bc-956da7292f7a' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('b322331a-0b67-40a8-aa5c-2940a7f3c4c1', '91f6fa24-7fb6-4907-b86f-352213d3075b')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('b322331a-0b67-40a8-aa5c-2940a7f3c4c1', '2fcdacd4-1004-4fc1-912c-15c05f8c91b9')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('5055926e-0fca-47e4-80a6-98a395003c32', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '2fcdacd4-1004-4fc1-912c-15c05f8c91b9', 'CL 1', 'CL 1', '3ebd5160-66a7-49d1-8f31-b1e762ee8973')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('75d8e23e-dfd4-42c9-8357-4e564c3a3b88', 'kuali.lui.type.activity.offering.lecture', '3ebd5160-66a7-49d1-8f31-b1e762ee8973', '72bf7e9e-2b2d-461b-982e-bf74be42e7fd')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('72bf7e9e-2b2d-461b-982e-bf74be42e7fd', '91f6fa24-7fb6-4907-b86f-352213d3075b')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='3ebd5160-66a7-49d1-8f31-b1e762ee8973' where ID='72bf7e9e-2b2d-461b-982e-bf74be42e7fd'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d3001b5a-ff71-44a1-a03a-a529f96bdc58', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'd48e4b15-08ca-494a-a187-4647da1d2b27', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '6c8c8c8c-fe0f-496c-a054-e051ca152b4d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1122681e-f6d3-494b-8663-b1c782e225c7', 'kuali.attribute.registration.group.is.generated', '6c8c8c8c-fe0f-496c-a054-e051ca152b4d', 1, 'e64a3de7-e04d-4b61-84cf-b205640e29aa')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8e8232ef-b84c-4f13-90a9-ccbe70054dce', 'kuali.attribute.registration.group.aocluster.id', '6c8c8c8c-fe0f-496c-a054-e051ca152b4d', '3ebd5160-66a7-49d1-8f31-b1e762ee8973', '9704a469-d709-4b6b-83e4-fb752af24e9a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d8fe2502-e11c-4d20-a370-7e46dfc4c838', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '6c8c8c8c-fe0f-496c-a054-e051ca152b4d', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '2c6b2561-4053-452b-ae58-7cbf8be7b51b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('952068aa-2b99-4b7e-9995-f5b875b79a97', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:49.253', '', 'HIST357-FO-RG', '2fcdacd4-1004-4fc1-912c-15c05f8c91b9', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST357-FO-RG', '6c8c8c8c-fe0f-496c-a054-e051ca152b4d', 'ff35971d-48e9-4a5e-9967-4fe24e509f30')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('72e4f0d3-d547-4df7-a745-70a95066d8a5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:49.258', '', 'HIST357-RG-AO', '6c8c8c8c-fe0f-496c-a054-e051ca152b4d', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST357-RG-AO', '91f6fa24-7fb6-4907-b86f-352213d3075b', '2213091a-c871-4e45-bd47-bff603107406')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('eb21df66-4760-4fc8-a16e-9c57c923e26a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'edc7c6fb-651a-4c4e-a905-fcacf3a04c76', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Twentieth century U.S. civil rights movement from the vantage point of women, considering both women''s involvement in the legal campaigns and political protests and the impact of civil rights struggles on women''s condiition, status, and identity.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST360 CO', 'Twentieth century U.S. civil rights movement from the vantage point of women, considering both women''s involvement in the legal campaigns and political protests and the impact of civil rights struggles on women''s condiition, status, and identity.', null, '95f4ffdf-e04b-486b-86eb-588953493cd8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8582407e-8b34-4f57-ac16-5ec1d7ec0cac', 'kuali.attribute.wait.list.level.type.key', '95f4ffdf-e04b-486b-86eb-588953493cd8', 'AO', '87dcfd3b-da32-4a08-b82e-48984e660e21')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4e0b9259-ba3b-463d-be6b-da87cd1ab9c9', 'kuali.attribute.course.number.internal.suffix', '95f4ffdf-e04b-486b-86eb-588953493cd8', 'A', '9e420770-8a5c-4cd8-b48e-4638b9a7e0f9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('03d7a7f3-25b7-40ee-a174-f999f61aa2ad', 'kuali.attribute.wait.list.indicator', '95f4ffdf-e04b-486b-86eb-588953493cd8', 1, 'e8a4c9a3-ca2e-4d97-9e98-14656d392fac')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dac2b65c-8a9e-4cb8-b7f9-64ea80e1d6cc', 'kuali.attribute.where.fees.attached.flag', '95f4ffdf-e04b-486b-86eb-588953493cd8', 0, '126d9ee6-e006-4b07-9ab5-bcb3d52f3599')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('868ccaaa-49fc-4d87-8cf6-ad931e2cbf15', 'kuali.attribute.final.exam.driver', '95f4ffdf-e04b-486b-86eb-588953493cd8', 'kuali.lu.exam.driver.ActivityOffering', '05614228-5e1b-4014-8566-73848c3212b1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0aa7b62d-d0d2-4b78-82e5-ad156ebdfbe8', 'kuali.attribute.final.exam.indicator', '95f4ffdf-e04b-486b-86eb-588953493cd8', 'STANDARD', '56ad0ec6-d85e-4d41-b1c8-c6c5b53a0283')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4c41f3db-3d36-42a0-9bc0-5b342eb24f3f', 'kuali.attribute.wait.list.type.key', '95f4ffdf-e04b-486b-86eb-588953493cd8', null, 'b89e2623-75fd-44b6-a0ad-405f1321cd46')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('11d68e65-9573-45c6-9606-7658f57eb320', 'kuali.attribute.final.exam.use.matrix', '95f4ffdf-e04b-486b-86eb-588953493cd8', 1, '379fd7c9-5b98-4431-a52c-9240f67f8b45')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7362cc6b-44d5-45e2-a758-2feda88862cd', 'kuali.attribute.course.evaluation.indicator', '95f4ffdf-e04b-486b-86eb-588953493cd8', 0, 'c1aa508c-8005-439b-98fa-f87fd30d51ab')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6ee35167-345a-4019-9b58-76718fa90ffd', 'kuali.attribute.funding.source', '95f4ffdf-e04b-486b-86eb-588953493cd8', '', 'c443c43a-ef9c-4da1-bde5-63d19e6725b6')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('13f64d2d-40bd-41bc-89c3-74ecdd34250a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'HIST360', 'HIST', '', 'Women and the Civil Rights Movement', '95f4ffdf-e04b-486b-86eb-588953493cd8', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '2d469960-b941-49e9-82de-b4bdc5c35b86')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('a0b05343-e0f4-4b8c-9c0d-069b802de56b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '95f4ffdf-e04b-486b-86eb-588953493cd8', '', 'kuali.lu.code.honorsOffering', '', '31d4cb76-152b-460c-b623-d798081d233e')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('95f4ffdf-e04b-486b-86eb-588953493cd8', '3213375036', '5b307613-426c-4621-8fb8-89a1d19b5775')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('95f4ffdf-e04b-486b-86eb-588953493cd8', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('95f4ffdf-e04b-486b-86eb-588953493cd8', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('95f4ffdf-e04b-486b-86eb-588953493cd8', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('95f4ffdf-e04b-486b-86eb-588953493cd8', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('95f4ffdf-e04b-486b-86eb-588953493cd8', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('8a2b149f-5ba5-45e4-a918-6283395c0f87', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'f534f531-03b9-418f-996c-8eb89a2e1af8', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '089a612d-4f6f-407e-945b-d0dd8f01f20b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f4618fbf-2b83-4b0e-ac6e-4fc205f4d46b', 'kuali.attribute.grade.roster.level.type.key', '089a612d-4f6f-407e-945b-d0dd8f01f20b', 'kuali.lui.type.activity.offering.lecture', '463d4366-af1a-4ec4-b5da-b3779511e5e4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('33de36b0-e9ac-434a-9970-8e1c940613a2', 'regCodePrefix', '089a612d-4f6f-407e-945b-d0dd8f01f20b', '1', 'b2329483-3cf6-4056-80d6-dc0abdd4496e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('363f5bea-964d-423c-94e8-964c2a0724fd', 'kuali.attribute.final.exam.level.type', '089a612d-4f6f-407e-945b-d0dd8f01f20b', 'kuali.lu.type.activity.Lecture', '36db7238-49ec-4c59-921f-690d5b296322')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('70b9631e-adab-4a93-b850-b546ba38516a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '089a612d-4f6f-407e-945b-d0dd8f01f20b', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '6fd10d1c-fe76-4711-a161-cac835cc9a50')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('089a612d-4f6f-407e-945b-d0dd8f01f20b', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('255d41e4-5d18-4591-9145-246f9a573f72', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:52.086', '', 'HIST360-CO-FO', '95f4ffdf-e04b-486b-86eb-588953493cd8', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST360-CO-FO', '089a612d-4f6f-407e-945b-d0dd8f01f20b', '33516669-d997-405d-8baf-ea7033dd5370')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('f8d28cad-3b35-4c5f-9c6b-8c16a97b3137', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '9a7b3050-799a-43e0-8a19-535717f3e32d', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, '4cbf9ef5-890a-4743-8a19-f69fc872f9db')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('80559d74-12f1-492e-bf39-cee87fbc8ffd', 'kuali.attribute.course.evaluation.indicator', '4cbf9ef5-890a-4743-8a19-f69fc872f9db', 0, 'd8b31293-1957-466f-b441-cdf2834317d7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8603b6a6-920b-4384-8b04-5d9d80a1bff6', 'kuali.attribute.nonstd.ts.indicator', '4cbf9ef5-890a-4743-8a19-f69fc872f9db', 0, 'ae9673b3-d185-4726-a008-088e541c060d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c67ab65a-982b-4c3b-af2b-ce799a060af5', 'kuali.attribute.max.enrollment.is.estimate', '4cbf9ef5-890a-4743-8a19-f69fc872f9db', 0, '488833e0-2e9e-470d-8d18-a5831126231e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('17b18350-a5bc-4bbc-8cd8-2748b563b282', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '4cbf9ef5-890a-4743-8a19-f69fc872f9db', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ea6741f2-1e13-497b-93dd-ac3afb1fb959')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('188df0fa-668f-4134-a430-c336079f506f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '4cbf9ef5-890a-4743-8a19-f69fc872f9db', '', 'kuali.lu.code.honorsOffering', 0, '18c53c44-d87a-4d78-8168-06eaf5c38708')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('852ce592-a448-41f6-872f-68b35aa440df', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:01:52.323', '', '', '4cbf9ef5-890a-4743-8a19-f69fc872f9db', '', 'S.CLANCYW', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '639d2102-49f9-4d1a-a0c0-66c62e8a9031')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('0e6e4812-92cd-4d73-9c04-5e6b92a83fb9', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:52.35', '', 'HIST360-FO-AO', '089a612d-4f6f-407e-945b-d0dd8f01f20b', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST360-FO-AO', '4cbf9ef5-890a-4743-8a19-f69fc872f9db', '8e1cac3c-55bd-45da-806f-0dfb9aa73fb3')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('302ddfb3-0048-4494-9fb6-47568aa8fe39', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for HIST360 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'b25e0e0c-0d0d-4ed4-9615-832b1923b54e')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('d1764253-91fc-4305-babb-0e87a7021681', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for HIST360 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'b25e0e0c-0d0d-4ed4-9615-832b1923b54e', '48ee47dc-2bc0-4ce5-acdb-049be950bd7a')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('f4023d61-19ac-4381-ac0c-65d604f7c077', 0, '48ee47dc-2bc0-4ce5-acdb-049be950bd7a', 'cab67a64-c347-441a-8d53-9796dc483269')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('b25e0e0c-0d0d-4ed4-9615-832b1923b54e', '4cbf9ef5-890a-4743-8a19-f69fc872f9db')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('cab67a64-c347-441a-8d53-9796dc483269', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('cab67a64-c347-441a-8d53-9796dc483269', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('cab67a64-c347-441a-8d53-9796dc483269', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('cab67a64-c347-441a-8d53-9796dc483269', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('f3f3a1f8-0fd4-4018-8a31-962f46033890', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'c9d21c7b-74c0-4dfc-b9e2-60ca91123c2b')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('4aabf3a7-5937-476c-b670-8fa233021ef1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '4226247f-eaa8-4f5b-b2e7-24443bdca171')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='f3f3a1f8-0fd4-4018-8a31-962f46033890', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='4cbf9ef5-890a-4743-8a19-f69fc872f9db', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='c9d21c7b-74c0-4dfc-b9e2-60ca91123c2b' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('4226247f-eaa8-4f5b-b2e7-24443bdca171', '4cbf9ef5-890a-4743-8a19-f69fc872f9db')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('4226247f-eaa8-4f5b-b2e7-24443bdca171', '089a612d-4f6f-407e-945b-d0dd8f01f20b')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('71f484a5-bca6-47d9-9e51-a9fbb1b0ee8b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '089a612d-4f6f-407e-945b-d0dd8f01f20b', 'CL 1', 'CL 1', '53b704e7-a580-4852-8dbf-ac611aa04d64')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('b29fac17-1abb-4aed-bdd9-548963dcf87a', 'kuali.lui.type.activity.offering.lecture', '53b704e7-a580-4852-8dbf-ac611aa04d64', '6f4b8a16-bcb8-4f3f-b8b8-6a610137f14b')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('6f4b8a16-bcb8-4f3f-b8b8-6a610137f14b', '4cbf9ef5-890a-4743-8a19-f69fc872f9db')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='53b704e7-a580-4852-8dbf-ac611aa04d64' where ID='6f4b8a16-bcb8-4f3f-b8b8-6a610137f14b'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('92355ea8-ce27-4175-bb9b-0e544f8ab774', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'f534f531-03b9-418f-996c-8eb89a2e1af8', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '7b6d34f8-823e-4920-a6cb-6a3b12c6272f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('333cef6c-0987-4b34-a64e-bdf17bc260f7', 'kuali.attribute.registration.group.is.generated', '7b6d34f8-823e-4920-a6cb-6a3b12c6272f', 1, '631aa035-01de-4d83-bd83-c5c292e08018')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('355dd5fb-5b37-483d-b379-9ad6dc519f9d', 'kuali.attribute.registration.group.aocluster.id', '7b6d34f8-823e-4920-a6cb-6a3b12c6272f', '53b704e7-a580-4852-8dbf-ac611aa04d64', 'ffa2e867-3687-4d38-8595-5333d7e40231')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6a7e3330-93c3-48d4-936b-3d4ff2c41415', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '7b6d34f8-823e-4920-a6cb-6a3b12c6272f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c259b77c-d611-4db0-94fd-922e5e40d41a')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('d043f816-be44-4ec0-baa1-e73d35062e6f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:52.811', '', 'HIST360-FO-RG', '089a612d-4f6f-407e-945b-d0dd8f01f20b', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST360-FO-RG', '7b6d34f8-823e-4920-a6cb-6a3b12c6272f', 'b71c92d8-9909-444c-bf65-212d6e79a269')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('80dba3d3-4e21-4a71-87a7-79e2824ae0c6', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:52.814', '', 'HIST360-RG-AO', '7b6d34f8-823e-4920-a6cb-6a3b12c6272f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST360-RG-AO', '4cbf9ef5-890a-4743-8a19-f69fc872f9db', 'd034ba77-1d14-47de-8101-e12e95444885')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('39b81b3c-727c-45d7-aee8-ac61c2ae6e59', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'a1e31e75-3d71-4ebf-96c5-12a25d55d23a', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Covers a wide range of topics in professional development for new graduate students.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'CHEM611 CO', 'Covers a wide range of topics in professional development for new graduate students.', null, '0ca2a7a0-7356-40b7-ab36-7531ea809094')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('69b9125b-a7f6-448a-9afe-2a28ec11393f', 'kuali.attribute.wait.list.type.key', '0ca2a7a0-7356-40b7-ab36-7531ea809094', null, '453afec2-9eab-4859-bc0c-03711deb15d2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('66c06f1d-b7f6-4300-a347-19859c97f472', 'kuali.attribute.funding.source', '0ca2a7a0-7356-40b7-ab36-7531ea809094', '', '3e10a262-cc26-4005-8c51-4ed15ce5fc29')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e755e059-5ed1-429e-8cb3-ff7e451e62a7', 'kuali.attribute.where.fees.attached.flag', '0ca2a7a0-7356-40b7-ab36-7531ea809094', 0, '8f81a8e9-d2d2-4419-b1a4-6fee1ee77cc6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c3b24e4c-1b45-41ff-8777-a404ec4aeb87', 'kuali.attribute.final.exam.use.matrix', '0ca2a7a0-7356-40b7-ab36-7531ea809094', 1, '88d095d2-3fa7-46cc-a4ec-e750948cd201')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c7bc16a3-1f3c-4987-84c9-cc27f37e987c', 'kuali.attribute.course.number.internal.suffix', '0ca2a7a0-7356-40b7-ab36-7531ea809094', 'A', 'dcbfb5e6-4023-4059-8f5b-db350f3ae117')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('eb27c6d2-892b-43c1-816f-1e88c3dd484a', 'kuali.attribute.course.evaluation.indicator', '0ca2a7a0-7356-40b7-ab36-7531ea809094', 0, 'f0e908a8-b5d9-4222-93dc-257a912690a7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0757ab5e-5fd0-491f-a9d6-623669eed6d1', 'kuali.attribute.final.exam.indicator', '0ca2a7a0-7356-40b7-ab36-7531ea809094', 'STANDARD', 'c595b48e-8f6d-4804-b194-843cfca5bede')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9c14b029-220d-45e3-bebd-379567d1005a', 'kuali.attribute.wait.list.level.type.key', '0ca2a7a0-7356-40b7-ab36-7531ea809094', 'AO', '3356bedd-9502-4135-9890-c6aed55c4bea')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('64c85687-d2e0-4b05-80b7-36ef969098be', 'kuali.attribute.final.exam.driver', '0ca2a7a0-7356-40b7-ab36-7531ea809094', 'kuali.lu.exam.driver.ActivityOffering', '5470613a-6631-4b87-902e-cf19d0afe6d3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0719bd1b-f717-4c75-83ef-ee92b81e0bd2', 'kuali.attribute.wait.list.indicator', '0ca2a7a0-7356-40b7-ab36-7531ea809094', 1, '072a6a30-4cd4-4718-be0c-7b0838ac052b')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('27809143-382b-4f4d-adfb-67ae4f858b51', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'CHEM611', 'CHEM', '', 'Professional Skills for New Graduate Students', '0ca2a7a0-7356-40b7-ab36-7531ea809094', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7ed19a83-0388-4e1e-9f07-b2e93bdcdd62')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('46fccf2f-df82-4ad3-9d56-c1148e75dd88', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '0ca2a7a0-7356-40b7-ab36-7531ea809094', '', 'kuali.lu.code.honorsOffering', '', 'b3948286-fac6-4151-9f5a-a7838286cd57')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('0ca2a7a0-7356-40b7-ab36-7531ea809094', '4284516367', '59465ce1-1489-4b0a-bba6-0e424f27652b')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('0ca2a7a0-7356-40b7-ab36-7531ea809094', '4284516367')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0ca2a7a0-7356-40b7-ab36-7531ea809094', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0ca2a7a0-7356-40b7-ab36-7531ea809094', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0ca2a7a0-7356-40b7-ab36-7531ea809094', 'kuali.creditType.credit.degree.1.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('5b79b4df-f9dc-4286-a165-24f83cecb68d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'c5ce342e-1099-4eb7-9d13-3eb727150060', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '7ac910e8-a85e-4710-8df0-588543ed22f6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f5804e32-0756-4559-8ab2-01c4d2beb48e', 'regCodePrefix', '7ac910e8-a85e-4710-8df0-588543ed22f6', '1', '3cc26e6b-319a-472c-bc29-4e3616317c7e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8f198d4e-88bf-4a69-afab-423fe4b241bd', 'kuali.attribute.final.exam.level.type', '7ac910e8-a85e-4710-8df0-588543ed22f6', 'kuali.lu.type.activity.Lecture', '78fdad56-82a3-420c-819a-12d4db8e97a7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c7bb25d5-12f7-437a-bd27-82a861f597d1', 'kuali.attribute.grade.roster.level.type.key', '7ac910e8-a85e-4710-8df0-588543ed22f6', 'kuali.lui.type.activity.offering.lecture', 'c588803d-440b-479e-84b8-b7b42b83a6e9')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5557ca54-93fb-48af-9bc4-dd8d4ca65f69', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '7ac910e8-a85e-4710-8df0-588543ed22f6', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '74b9b8c0-f7e0-48f1-9967-ecbc2b3c7620')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('7ac910e8-a85e-4710-8df0-588543ed22f6', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('4d7efc9c-b7ae-4295-b040-e13264bfa2b4', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:56.848', '', 'CHEM611-CO-FO', '0ca2a7a0-7356-40b7-ab36-7531ea809094', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'CHEM611-CO-FO', '7ac910e8-a85e-4710-8df0-588543ed22f6', '1466a63f-4157-4d3c-8a6b-6639bc12fd29')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('2dac1777-bf6e-4140-ab95-12a2872ba0c6', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '3c719070-e600-425e-befc-1eb4b5aa3b08', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 10, 10, 'Lecture', 'Courses with lecture', null, 'ee71c22f-dbdb-4499-89b5-bec45bee88d7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b888f856-e393-4f40-8847-e807db43b04c', 'kuali.attribute.course.evaluation.indicator', 'ee71c22f-dbdb-4499-89b5-bec45bee88d7', 0, '0f3bbf05-ae61-4707-820f-93f0dd376979')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f4f3a2a9-89d8-4e21-812b-4162ca941df0', 'kuali.attribute.max.enrollment.is.estimate', 'ee71c22f-dbdb-4499-89b5-bec45bee88d7', 0, '5e3e887b-2cf2-419b-8818-13b289a45529')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ba8ffc59-e96e-467c-8157-85398a3d949a', 'kuali.attribute.nonstd.ts.indicator', 'ee71c22f-dbdb-4499-89b5-bec45bee88d7', 0, '0a1bff33-d68b-4fcc-b86b-f2aa8aa1baf7')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5472efe1-8423-466f-9598-96ebc35a6b68', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', 'ee71c22f-dbdb-4499-89b5-bec45bee88d7', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '3075cc03-5db4-4cd5-90dd-b67888817a77')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('0b05c996-2b95-4dbd-844d-bf74e302e5e5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'ee71c22f-dbdb-4499-89b5-bec45bee88d7', '', 'kuali.lu.code.honorsOffering', 0, '9a772282-885c-40ce-adaa-b0c52461ffe4')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('042a0bff-2b71-467e-a361-8117a815cc73', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:01:56.957', '', '', 'ee71c22f-dbdb-4499-89b5-bec45bee88d7', '', 'L.ALPHAD', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '18944613-d44c-47af-9672-b8cfa2a3e8d1')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('6fc6ab89-6797-4cf3-841f-5b1e76d53913', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:57.004', '', 'CHEM611-FO-AO', '7ac910e8-a85e-4710-8df0-588543ed22f6', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM611-FO-AO', 'ee71c22f-dbdb-4499-89b5-bec45bee88d7', 'a32bfa97-e76c-49c7-9860-8eea7442d3f6')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('f98dff41-bf77-4fe7-b69e-7642627fa94f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for CHEM611 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'ee0019b4-89a5-4dbb-b617-ee7f8c17e68c')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('ed3d77df-92c4-482b-aef7-5e518395d994', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for CHEM611 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'ee0019b4-89a5-4dbb-b617-ee7f8c17e68c', 'd60c198e-6aab-4b81-ace9-b69d0158d3e6')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('ca86c962-fefc-4c7f-9e07-39df1b068814', 0, 'd60c198e-6aab-4b81-ace9-b69d0158d3e6', 'fc0cfc16-0210-4104-9c1a-a6f3688e3edc')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('ee0019b4-89a5-4dbb-b617-ee7f8c17e68c', 'ee71c22f-dbdb-4499-89b5-bec45bee88d7')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('fc0cfc16-0210-4104-9c1a-a6f3688e3edc', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('fc0cfc16-0210-4104-9c1a-a6f3688e3edc', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('fc0cfc16-0210-4104-9c1a-a6f3688e3edc', '2246d609-9fbb-4d06-b45e-a57ef80539b7')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('fc0cfc16-0210-4104-9c1a-a6f3688e3edc', 'F0AA72D6DC5CF650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('2c3de141-d840-4e86-a59e-95ed6b500a4d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '8bd83898-b3e4-4573-9b97-ccc56fd21e95')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('8bd83898-b3e4-4573-9b97-ccc56fd21e95', 'ee71c22f-dbdb-4499-89b5-bec45bee88d7')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('8bd83898-b3e4-4573-9b97-ccc56fd21e95', '7ac910e8-a85e-4710-8df0-588543ed22f6')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('552fda52-aefd-40a2-b264-258a432ba989', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '7ac910e8-a85e-4710-8df0-588543ed22f6', 'CL 1', 'CL 1', '1804619d-afcc-44af-a509-b77baa97e733')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('154677d1-3e35-4028-bef3-ff0959da206d', 'kuali.lui.type.activity.offering.lecture', '1804619d-afcc-44af-a509-b77baa97e733', '45a3df25-7d44-4d39-8d23-a68a0eeeea35')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('45a3df25-7d44-4d39-8d23-a68a0eeeea35', 'ee71c22f-dbdb-4499-89b5-bec45bee88d7')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='1804619d-afcc-44af-a509-b77baa97e733' where ID='45a3df25-7d44-4d39-8d23-a68a0eeeea35'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('a57f44f8-3562-494c-b4b1-da96340f1ef7', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'c5ce342e-1099-4eb7-9d13-3eb727150060', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '5ad7d75a-2b9d-4dee-b714-cb8059b440eb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3dcc59c4-4bfb-48b7-81fc-6afb10784977', 'kuali.attribute.registration.group.is.generated', '5ad7d75a-2b9d-4dee-b714-cb8059b440eb', 1, '2bd30639-012d-4a95-8a32-072115b78482')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a290e349-f028-443e-a124-b76640b9ac70', 'kuali.attribute.registration.group.aocluster.id', '5ad7d75a-2b9d-4dee-b714-cb8059b440eb', '1804619d-afcc-44af-a509-b77baa97e733', '5e29dcbc-aeb5-4c5d-953e-67e495e2b155')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7e720512-eb33-4834-ac53-13d9f525b614', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '5ad7d75a-2b9d-4dee-b714-cb8059b440eb', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '0816b238-e4dc-4929-9397-2e301fff7aa7')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('156cba57-f4d5-4c45-833d-8242b3259403', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:57.434', '', 'CHEM611-FO-RG', '7ac910e8-a85e-4710-8df0-588543ed22f6', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM611-FO-RG', '5ad7d75a-2b9d-4dee-b714-cb8059b440eb', '54882d1a-d8c1-4d94-9406-7a002bd876b6')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('90e810ec-a234-4b21-b815-1b5c2168b3fc', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:01:57.452', '', 'CHEM611-RG-AO', '5ad7d75a-2b9d-4dee-b714-cb8059b440eb', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM611-RG-AO', 'ee71c22f-dbdb-4499-89b5-bec45bee88d7', 'df8bed8b-8736-4cf9-b3fb-9cb9a21a83f3')
/
update KSEN_SOC_ROR set OBJ_ID='971d59cc-eb7c-4504-ac92-ec51f651c326', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ITEMS_EXPECTED=48, ITEMS_PROCESSED=20, MESG_FORMATTED='', MESG_PLAIN='', SOC_ROR_STATE='kuali.soc.rollover.state.running', SOC_ROR_TYPE='kuali.soc.rollover.results.type.rollover', SOURCE_SOC_ID='03192a24-765a-4b9d-b638-5158430e09c8', TARGET_SOC_ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', TARGET_TERM_ID='kuali.atp.2014Winter' where ID='5b88a612-d027-429f-a9f3-b4241ed736c7' and VER_NBR=3
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='e2dae705-59ea-4cea-a1b8-97963890ed4e', ATTR_KEY='kuali.soc.rollover.result.dynattr.date.completed', OWNER_ID='5b88a612-d027-429f-a9f3-b4241ed736c7', ATTR_VALUE='2014 Jan 23 20:01:57 America/New_York' where ID='289b7901-b122-4239-94fd-e4dd1e0066cf'
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('ab42e22e-5217-42a5-8a1f-c63250a398fa', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '36819558-e390-4681-9d8e-5eed0642c03a', '697a0587-c8b5-47e8-9431-f06fb937cc13', 'f32ebd48-be19-4c8f-a109-8b3c17ed2bef')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4799fd5d-d34b-45f9-b765-ebd366439b29', 'activityOfferingsCreated', 'f32ebd48-be19-4c8f-a109-8b3c17ed2bef', '1', '7a80c18d-039c-430e-a661-38a144bd5fd8')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('1bcfc3f9-8ed6-4c60-bda2-e3882640551f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '36f41c50-1321-4f93-aa8a-e5440943aeb2', '5d9f7ed0-eb62-428c-b5ec-e36fe9a19af0', '077bbd2c-49cb-4235-b680-b7123fe73bb2')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('aa83ecc3-2486-453e-bb6d-a812c9a027bd', 'activityOfferingsCreated', '077bbd2c-49cb-4235-b680-b7123fe73bb2', '2', '4642bc94-6840-4d6d-bc1f-649ba92e6296')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('e24bac58-1916-4dee-8194-42a9b7554a5c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '41172f6c-3925-4d40-acb3-9024c817e168', '6aab8f79-d921-49e1-98c4-f3d031f24081', '53b49d15-449f-4a45-a1d8-338a4ad87ed3')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c551edbb-fa5d-4ea2-b477-562949759eed', 'activityOfferingsCreated', '53b49d15-449f-4a45-a1d8-338a4ad87ed3', '2', 'ee043c8b-4f30-42d0-b28f-ff9782d4b840')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('64850c02-50c9-4c81-9fb4-ae00a6227fa5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', '4db733fc-b8cc-46f2-acc1-b82dc5803334', '', '24baec00-6e27-4bc4-b207-70846a59d191')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('443d5f5b-638f-47e4-a7c5-7c2039e441bf', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '4edb69b1-b2e6-4ee4-a5a9-3c4f4a965b0f', 'b0e0e702-047f-4d60-98a9-29d39133f277', '4a3d8b9d-b1d4-44b6-b1c9-49a5be6a72b1')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b8006471-82a6-45ef-994b-1ef1335db80c', 'activityOfferingsCreated', '4a3d8b9d-b1d4-44b6-b1c9-49a5be6a72b1', '1', 'b4bc92b5-56ca-45f6-b890-0f57486fb745')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('4d024da7-1429-467d-8907-a33d7c1d5e0a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '5931b2d4-b41c-499e-b45e-f8baafc0aa3a', 'e51868ab-ca03-4bee-bc8a-a025df5e47d5', '3c669392-cfdc-4314-87b5-ec804a6a0a24')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8b4d4aa2-201f-480a-8d62-3cc9f0210ad0', 'activityOfferingsCreated', '3c669392-cfdc-4314-87b5-ec804a6a0a24', '1', 'fa5e6965-ba97-41bc-85f5-e31df24411f1')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('bd05077b-895b-4835-9118-d03239e1076b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '56cd1552-174e-4a30-8420-0dab3c8a9aa2', 'b8b6c7d2-8d18-4582-8146-d8f685a9fffb', '3f532e0f-f1a0-44a6-9912-176a9f1fd97b')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('012411b6-39c1-4265-a0d3-a681f9f42e42', 'activityOfferingsCreated', '3f532e0f-f1a0-44a6-9912-176a9f1fd97b', '1', 'f642dc04-dea8-423f-b05e-8a9452481700')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('96e81a0b-29e7-4a51-b398-f98bcc89e777', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '5e8b6fff-a478-4720-b8f1-2df0746434ce', '95f4ffdf-e04b-486b-86eb-588953493cd8', '6ed2fa55-e429-4af8-b723-41cb84ffba84')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0a64c852-7c6e-479f-b29c-5d6bf5d63d93', 'activityOfferingsCreated', '6ed2fa55-e429-4af8-b723-41cb84ffba84', '1', 'c2a46fb1-cf75-4d1e-b6d8-8815462e7fb3')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('669cfd4f-1461-407f-8047-884fe76ff98d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', '64ef7564-6c70-41ad-b41a-fdbc613f36d9', '', '0e4da911-b153-4a32-8b26-c4d6b828ede6')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('1c548a7a-311b-4f4f-a55f-b1eccdcaa2cc', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '6f8aeb25-965f-4f68-a9b8-68e821e98117', '0ca2a7a0-7356-40b7-ab36-7531ea809094', '4c8675ce-edfc-4ff7-9f11-9406b5da5850')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a72696c7-8e90-4507-b666-bc58a8ffc423', 'activityOfferingsCreated', '4c8675ce-edfc-4ff7-9f11-9406b5da5850', '1', '895eecb4-ea5e-4044-bde1-2195ae0786ed')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('c23f8bf9-f2e1-4e2d-8203-f0d1416a2d46', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'CLUID-WMST698-199008000000', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Advanced worik in selected topics in Women''s Studies.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'WMST698R CO', 'Advanced worik in selected topics in Women''s Studies.', null, '0bf52bd3-83eb-4594-be14-453dbe36510d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4c103b16-1cc2-425a-af06-6bae3000c168', 'kuali.attribute.course.number.internal.suffix', '0bf52bd3-83eb-4594-be14-453dbe36510d', 'A', '30b34d9c-cc9a-486d-8dbb-086f333946de')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1e7471de-ee6b-4553-ac6e-88d413a893fc', 'kuali.attribute.wait.list.level.type.key', '0bf52bd3-83eb-4594-be14-453dbe36510d', 'AO', '63c4e459-f859-4a93-9598-41807930b5e9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('47062215-6383-4fcc-9b08-42a847468e61', 'kuali.attribute.final.exam.indicator', '0bf52bd3-83eb-4594-be14-453dbe36510d', 'STANDARD', '2f2b169d-1b4d-4323-a832-0c2687824b9a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cf724e5b-3f26-4f59-be20-25bc5755a32a', 'kuali.attribute.course.evaluation.indicator', '0bf52bd3-83eb-4594-be14-453dbe36510d', 0, '1f16d01a-6323-43c3-bd22-ed138417843d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a852c5b3-b622-42d4-b0e6-0815bc20deff', 'kuali.attribute.funding.source', '0bf52bd3-83eb-4594-be14-453dbe36510d', '', 'a98450ad-a1a7-46ef-831b-aec9016e01de')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6b92b844-aa8e-4681-926f-71d7f59efe70', 'kuali.attribute.final.exam.use.matrix', '0bf52bd3-83eb-4594-be14-453dbe36510d', 1, 'ba5a4303-555b-43a3-bda0-38da45b10adc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1a7825cf-7706-485e-b5d1-f17ccecce3e4', 'kuali.attribute.wait.list.indicator', '0bf52bd3-83eb-4594-be14-453dbe36510d', 1, '5eec6da5-dd2d-4467-8a8f-732a37de7bc4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('827b3f5f-1f4d-4cd9-b13c-1a11e358012e', 'kuali.attribute.final.exam.driver', '0bf52bd3-83eb-4594-be14-453dbe36510d', 'kuali.lu.exam.driver.ActivityOffering', '9dc7ad7a-204a-4e16-b68c-523bc37c9c5b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0c686a16-9843-4d61-84d1-7227733dcc40', 'kuali.attribute.where.fees.attached.flag', '0bf52bd3-83eb-4594-be14-453dbe36510d', 0, '86e96f6f-57e7-45a8-9fdf-b65ea42ee6c6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4a4beedf-8297-4510-9538-a15db5220690', 'kuali.attribute.wait.list.type.key', '0bf52bd3-83eb-4594-be14-453dbe36510d', null, '6891b43a-0fa4-42ef-af23-9ab57707e3ea')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c07e4c47-1e49-4d8d-b49d-1b86064db43d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'WMST698R', 'WMST', '', 'Special Topics in Women''s Studies', '0bf52bd3-83eb-4594-be14-453dbe36510d', '', '', 'kuali.lui.identifier.state.active', 'R', 'kuali.lui.identifier.type.official', '', '0882f1da-d33d-45f8-a2ea-ef8420ef0341')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('6fd78e67-8c29-43d4-af04-6e6749257f92', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '0bf52bd3-83eb-4594-be14-453dbe36510d', '', 'kuali.lu.code.honorsOffering', '', '58a678ee-7d26-4cfe-85ad-f6b05ea9d85c')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('0bf52bd3-83eb-4594-be14-453dbe36510d', '4014660630', '70ee3aa5-fba8-41b2-a4d0-8d0bcecf939b')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('0bf52bd3-83eb-4594-be14-453dbe36510d', '4014660630')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0bf52bd3-83eb-4594-be14-453dbe36510d', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0bf52bd3-83eb-4594-be14-453dbe36510d', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0bf52bd3-83eb-4594-be14-453dbe36510d', 'kuali.creditType.credit.degree.1.0,1.5,2.0,2.5,3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('e3e051cb-2ad5-44bd-b8a8-63dc49039fce', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'c0e2e9e4-ef2c-40c1-8a69-6b8a268207c2', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '12944350-9db0-4343-aa77-1df4ea554005')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7306364d-6243-4a9b-bd4e-60ef07066878', 'regCodePrefix', '12944350-9db0-4343-aa77-1df4ea554005', '1', 'f8a3aaed-b76e-44b3-827b-854973afdbe4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('af31b484-2c12-4f47-b9a0-bcb620e1e022', 'kuali.attribute.final.exam.level.type', '12944350-9db0-4343-aa77-1df4ea554005', 'kuali.lu.type.activity.Lecture', '52ab011e-947e-422c-93bb-ff58a9278444')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8fd77a48-ee2e-4eb5-a831-7c73beed007a', 'kuali.attribute.grade.roster.level.type.key', '12944350-9db0-4343-aa77-1df4ea554005', 'kuali.lui.type.activity.offering.lecture', '9e7f2b1a-003f-4fe1-b177-7a78eda44aa6')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d3f98e44-6f9a-4765-84f8-ee27877a9427', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '12944350-9db0-4343-aa77-1df4ea554005', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'd2add524-74ce-425b-a2bc-c6743b3fbeaa')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('12944350-9db0-4343-aa77-1df4ea554005', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('68142216-c061-45a4-b690-6f4e2b526235', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:00.116', '', 'WMST698R-CO-FO', '0bf52bd3-83eb-4594-be14-453dbe36510d', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'WMST698R-CO-FO', '12944350-9db0-4343-aa77-1df4ea554005', 'c18c11dd-7694-4c86-81c6-ecd81513d849')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('296962e8-1166-4176-88ee-70873fdb76ef', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '23b9ac95-4555-4d33-a802-97a6206bcd83', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 15, 15, 'Lecture', 'Courses with lecture', null, '5644a040-3915-4fd7-9f41-daf9ba6c143c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('04ea646f-cfd6-4578-b548-e189ec2a106d', 'kuali.attribute.max.enrollment.is.estimate', '5644a040-3915-4fd7-9f41-daf9ba6c143c', 0, '6814ad44-c5c1-4f46-8604-d76d9c519d3f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1ef696ba-7026-414a-880b-f006ed46a10a', 'kuali.attribute.nonstd.ts.indicator', '5644a040-3915-4fd7-9f41-daf9ba6c143c', 0, '67a6562e-46a6-48b7-bb9f-10eed4bdee1e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3a165426-adc5-4ee6-b7b7-481c0db620d4', 'kuali.attribute.course.evaluation.indicator', '5644a040-3915-4fd7-9f41-daf9ba6c143c', 0, '03847489-2edd-4713-a489-17d6187c0e80')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('91396b31-c68d-48b2-83a9-d241cb5301ea', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '5644a040-3915-4fd7-9f41-daf9ba6c143c', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'd72afdcb-eea6-4732-8491-74d269c49437')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('37ef1ba8-a0b9-46ee-aca4-792bed57c080', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '5644a040-3915-4fd7-9f41-daf9ba6c143c', '', 'kuali.lu.code.honorsOffering', 0, '7d0b631e-9125-4b7b-a13b-f05df057e51a')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('f8b0fd32-38f0-4678-8f70-55cf97fe4ab8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:00.232', '', '', '5644a040-3915-4fd7-9f41-daf9ba6c143c', '', 'B.LOUISG', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '2b100b8d-8820-4ffd-a238-58256f31d17f')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('133b4c94-81f6-4e86-ae46-1285435d1880', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:00.273', '', 'WMST698R-FO-AO', '12944350-9db0-4343-aa77-1df4ea554005', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'WMST698R-FO-AO', '5644a040-3915-4fd7-9f41-daf9ba6c143c', '45853747-a094-437c-b4d7-46b8bc64dae7')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('cc217628-bdae-49cd-9e0d-f426f4b66963', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for WMST698R - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '393f147a-bb06-46a3-b74f-0e42e3e183b1')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('e479ceb6-c5aa-4b32-ac2e-436f6490eb40', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A Schedule Request for WMST698R section HY11', 'A Schedule Request for WMST698R section HY11', 'A Schedule Request for WMST698R section HY11', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', 'a689f63c-bbb8-4117-995f-f1e932fc42ff', '393f147a-bb06-46a3-b74f-0e42e3e183b1', 'ddaa6c6f-35a8-4307-ad86-b0f6b6e8a9c2')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('88484f00-ead6-48c2-8446-1d5dec3c2a31', 0, 'ddaa6c6f-35a8-4307-ad86-b0f6b6e8a9c2', '219a182a-6307-4e60-a972-45da98004d7c')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('393f147a-bb06-46a3-b74f-0e42e3e183b1', '5644a040-3915-4fd7-9f41-daf9ba6c143c')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('219a182a-6307-4e60-a972-45da98004d7c', '9a171e1e-dde4-4095-8fc9-b8fde0da485e')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('219a182a-6307-4e60-a972-45da98004d7c', '3228d416-6a16-4c95-9fa6-ab16a1599b01')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('219a182a-6307-4e60-a972-45da98004d7c', 'F0AA72D6DD5CF650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('2e523611-aec1-4f01-a190-ce6588184683', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '3fe7b7fb-1b7a-4978-b502-184d62448a2a')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('3fe7b7fb-1b7a-4978-b502-184d62448a2a', '5644a040-3915-4fd7-9f41-daf9ba6c143c')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('3fe7b7fb-1b7a-4978-b502-184d62448a2a', '12944350-9db0-4343-aa77-1df4ea554005')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('481c6bbb-a1e7-4f0a-9e51-f1fe16c8ebba', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '62008487-a82a-4149-8660-3a1579a662f6', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 15, 15, 'Lecture', 'Courses with lecture', null, '33eb5591-9aa0-491c-804b-5a815eb7c7ab')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8146bfb1-bd20-4d95-a96d-4f3efe1a50e6', 'kuali.attribute.max.enrollment.is.estimate', '33eb5591-9aa0-491c-804b-5a815eb7c7ab', 0, '08d9070a-95ed-4def-b95e-6a4ef79a1875')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cb291ee2-34f7-4432-8fb5-6b06f6092492', 'kuali.attribute.nonstd.ts.indicator', '33eb5591-9aa0-491c-804b-5a815eb7c7ab', 0, '9b6ccdb5-ee2e-49d0-99a2-03a4b6f393b8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fe924969-f39e-483c-a5a1-4aa08b7b7c21', 'kuali.attribute.course.evaluation.indicator', '33eb5591-9aa0-491c-804b-5a815eb7c7ab', 0, '66fd5d63-b717-448a-bf5d-aaf5daa9ba60')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6aad19fa-23ed-486b-a3de-d646791f0182', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'B', '', '', '', '33eb5591-9aa0-491c-804b-5a815eb7c7ab', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '25edef2b-92e4-476f-b56e-cb9df3096387')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('f818c568-bcaa-4844-b66b-541382a83b4f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '33eb5591-9aa0-491c-804b-5a815eb7c7ab', '', 'kuali.lu.code.honorsOffering', 0, 'a5001c88-79bc-4e4c-8800-e243490c5008')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('e9eda26a-1829-4202-bf9e-4d3e42ee57f3', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:00.726', '', '', '33eb5591-9aa0-491c-804b-5a815eb7c7ab', '', 'H.DONALDC', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '957013d8-866e-4a34-b3dc-2de487b23800')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('b069440f-2096-4091-a9f2-aa50ea2077e5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:00.759', '', 'WMST698R-FO-AO', '12944350-9db0-4343-aa77-1df4ea554005', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'WMST698R-FO-AO', '33eb5591-9aa0-491c-804b-5a815eb7c7ab', '78b661d0-0e51-4f2c-829f-2a99f516149b')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('2c450a31-0d88-4ea5-ad62-61b0531c81bf', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for WMST698R - B', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '9358122a-ec59-4ce9-8770-a13b34007255')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('bfb12d89-87a5-4d7f-be2d-841a7bf64161', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A Schedule Request for WMST698R section HY11', 'A Schedule Request for WMST698R section HY11', 'A Schedule Request for WMST698R section HY11', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '6cb01cdc-7a72-40b6-ab14-797afd4fe61b', '9358122a-ec59-4ce9-8770-a13b34007255', '6fbcba00-c427-4361-87b5-db03e83a1bb8')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('a1f5214a-f175-41fd-b420-dfe763fe7025', 0, '6fbcba00-c427-4361-87b5-db03e83a1bb8', '2852d654-4d18-4ada-af38-9a9f75d08384')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('9358122a-ec59-4ce9-8770-a13b34007255', '33eb5591-9aa0-491c-804b-5a815eb7c7ab')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('2852d654-4d18-4ada-af38-9a9f75d08384', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('2852d654-4d18-4ada-af38-9a9f75d08384', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('2852d654-4d18-4ada-af38-9a9f75d08384', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('711510f7-8a64-4ec0-b412-fa7d58a4c461', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '321c39d2-81d4-4a61-a481-c342c7e6e441')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('321c39d2-81d4-4a61-a481-c342c7e6e441', '33eb5591-9aa0-491c-804b-5a815eb7c7ab')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('321c39d2-81d4-4a61-a481-c342c7e6e441', '12944350-9db0-4343-aa77-1df4ea554005')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('804843a5-2a35-48a1-af58-2edbe1f20381', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '12944350-9db0-4343-aa77-1df4ea554005', 'CL 1', 'CL 1', '8f55d144-c22a-4775-be0f-fd139ec87575')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('b51faf5f-df42-43cb-976b-9e8ca9e7caa6', 'kuali.lui.type.activity.offering.lecture', '8f55d144-c22a-4775-be0f-fd139ec87575', '014a3ea6-5744-48f4-8d84-e1f1244aa456')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('014a3ea6-5744-48f4-8d84-e1f1244aa456', '33eb5591-9aa0-491c-804b-5a815eb7c7ab')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('014a3ea6-5744-48f4-8d84-e1f1244aa456', '5644a040-3915-4fd7-9f41-daf9ba6c143c')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='8f55d144-c22a-4775-be0f-fd139ec87575' where ID='014a3ea6-5744-48f4-8d84-e1f1244aa456'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ca91f2b7-3f1b-4bfd-b59f-5bb0c1b6e939', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'c0e2e9e4-ef2c-40c1-8a69-6b8a268207c2', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '0d6e4dae-6d66-407d-be32-4e1faab59c91')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c5ba576e-e59c-49c9-92c6-664ff02e82a6', 'kuali.attribute.registration.group.aocluster.id', '0d6e4dae-6d66-407d-be32-4e1faab59c91', '8f55d144-c22a-4775-be0f-fd139ec87575', '82696b84-be39-4790-80ae-d71ecd3874f1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('db8f8369-04df-4573-b099-f12fcbefff05', 'kuali.attribute.registration.group.is.generated', '0d6e4dae-6d66-407d-be32-4e1faab59c91', 1, 'e48e70a5-c57e-4c0a-ae62-a076f03be2bc')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('777cd5e6-a61a-419a-8e75-54db1fdf9c01', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '0d6e4dae-6d66-407d-be32-4e1faab59c91', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ba51409d-b2f0-4577-b173-b4a5b951bcb5')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ae7c7731-8cc9-4148-8ce5-3d2668603d09', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:01.201', '', 'WMST698R-FO-RG', '12944350-9db0-4343-aa77-1df4ea554005', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'WMST698R-FO-RG', '0d6e4dae-6d66-407d-be32-4e1faab59c91', '3216f635-ae50-41c7-885d-6df811502feb')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('25d0570d-4e74-4927-b485-94b209dd8722', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:01.214', '', 'WMST698R-RG-AO', '0d6e4dae-6d66-407d-be32-4e1faab59c91', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'WMST698R-RG-AO', '5644a040-3915-4fd7-9f41-daf9ba6c143c', 'ee146692-205e-443a-9ad4-1d5bfc24af15')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('712aa12c-c9c4-4e77-b277-6778e01ffaf7', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'c0e2e9e4-ef2c-40c1-8a69-6b8a268207c2', '', '', '1002', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1002', '1002', '', '09e6f4bf-25ef-4db3-88d0-bda552e75c18')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7c5e53f0-93e7-4bba-885a-3d0d795dd395', 'kuali.attribute.registration.group.aocluster.id', '09e6f4bf-25ef-4db3-88d0-bda552e75c18', '8f55d144-c22a-4775-be0f-fd139ec87575', '599ecb08-cbb0-4a2b-a38d-43c6351be859')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('aebb25e2-f80c-4cee-8c0c-2185bee3d7f7', 'kuali.attribute.registration.group.is.generated', '09e6f4bf-25ef-4db3-88d0-bda552e75c18', 1, '8d8d37e6-65e2-4c13-b5c5-37a7391ba46a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('8e0128cf-821c-40af-b585-2546d0f3a67f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '09e6f4bf-25ef-4db3-88d0-bda552e75c18', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '0fea2d6b-f956-4904-a30d-b50166aeb981')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('1c446a6a-153a-423a-9aac-8d6d3dbb8bc5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:01.307', '', 'WMST698R-FO-RG', '12944350-9db0-4343-aa77-1df4ea554005', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'WMST698R-FO-RG', '09e6f4bf-25ef-4db3-88d0-bda552e75c18', 'f4425a0b-e5e5-4e3a-81c5-b791e8d740c1')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('b7c7b0c7-0177-4ab7-93a9-f62f4f194736', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:01.31', '', 'WMST698R-RG-AO', '09e6f4bf-25ef-4db3-88d0-bda552e75c18', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'WMST698R-RG-AO', '33eb5591-9aa0-491c-804b-5a815eb7c7ab', '7ac702e6-406b-4a23-b9a6-41afe221cd31')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('bd71df8d-533d-4e3f-bf07-8bebd59a1204', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'CLUID-HIST429-200801000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST429G CO', '.', null, 'db406537-1427-4ef2-a727-7310504960c8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f45db4ec-7ba2-417d-92c4-c568f8139432', 'kuali.attribute.wait.list.indicator', 'db406537-1427-4ef2-a727-7310504960c8', 1, '02879ddb-fc36-4e32-92c9-faeeeecc84a1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5835140b-356a-40e7-a17e-ffb85944f67a', 'kuali.attribute.funding.source', 'db406537-1427-4ef2-a727-7310504960c8', '', 'fd387c11-790c-4c27-9755-ce4907793d7a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d2592022-db10-484f-a2f1-a8817bd732a6', 'kuali.attribute.final.exam.use.matrix', 'db406537-1427-4ef2-a727-7310504960c8', 1, '2aef6cde-9783-43db-9430-fd875e7e9b48')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0032946e-9936-4854-a3bf-117186a5894d', 'kuali.attribute.wait.list.level.type.key', 'db406537-1427-4ef2-a727-7310504960c8', 'AO', '6417a592-aaf5-4f56-b696-040c023b09ba')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9c6b8014-26c3-40fd-bbf1-7a05277e6bf3', 'kuali.attribute.where.fees.attached.flag', 'db406537-1427-4ef2-a727-7310504960c8', 0, '692ff7b2-1c2d-4c8f-a330-f8f9b3252f56')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9f0dd023-56a9-48db-b530-79e51893a5e1', 'kuali.attribute.course.number.internal.suffix', 'db406537-1427-4ef2-a727-7310504960c8', 'A', '1928fa5a-8725-478d-ba0e-68f00d494d5a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('33fe9cdd-d222-4ea0-97d9-5b70247e0ebd', 'kuali.attribute.course.evaluation.indicator', 'db406537-1427-4ef2-a727-7310504960c8', 0, 'dead117d-8475-4c12-93e8-661fe41f8c53')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cab4e0d6-e921-47f5-bcbe-0522fdf803f6', 'kuali.attribute.wait.list.type.key', 'db406537-1427-4ef2-a727-7310504960c8', null, 'c9e95946-e0f3-4811-9527-3e8394d6e00e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('13977650-9e55-41c5-882b-30f426f84809', 'kuali.attribute.final.exam.driver', 'db406537-1427-4ef2-a727-7310504960c8', 'kuali.lu.exam.driver.ActivityOffering', 'e0dd51d5-3dfd-46c5-b9fd-9c1ba61f165c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1732b8fc-f388-4df7-8412-95649e3dacd9', 'kuali.attribute.final.exam.indicator', 'db406537-1427-4ef2-a727-7310504960c8', 'STANDARD', '7895ccaf-4316-442d-afde-ce9a1664bdc0')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('ddc22fe0-4ce8-4295-bb3e-61cc1a329261', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'HIST429G', 'HIST', '', 'Special Topics in History', 'db406537-1427-4ef2-a727-7310504960c8', '', '', 'kuali.lui.identifier.state.active', 'G', 'kuali.lui.identifier.type.official', '', '1d1d8fb3-060f-46c5-8925-3e330421cc24')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('dca92f78-db17-4a05-b660-397871693a44', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'db406537-1427-4ef2-a727-7310504960c8', '', 'kuali.lu.code.honorsOffering', '', '623603fb-c3c4-4ff5-b2e6-a6296d1b0d9e')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('db406537-1427-4ef2-a727-7310504960c8', '3213375036', '39953060-6048-4212-aca1-13395a3b4bc2')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('db406537-1427-4ef2-a727-7310504960c8', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('db406537-1427-4ef2-a727-7310504960c8', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('db406537-1427-4ef2-a727-7310504960c8', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('db406537-1427-4ef2-a727-7310504960c8', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('db406537-1427-4ef2-a727-7310504960c8', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('f43aab15-cd54-411f-a656-f7d227b56bba', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '2acda501-2afd-4b9e-b08f-76d97c038024', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'ed47f17b-75f0-4d7f-9f19-09e6022f9380')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4983c243-ba1b-4fba-a24f-b2a02b91e8e5', 'regCodePrefix', 'ed47f17b-75f0-4d7f-9f19-09e6022f9380', '1', 'af16cf0d-1fb5-4e47-ba07-077cd4cca3b1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d76794c2-acec-41b1-a414-f1d595e647c1', 'kuali.attribute.final.exam.level.type', 'ed47f17b-75f0-4d7f-9f19-09e6022f9380', 'kuali.lu.type.activity.Lecture', 'df22f2a7-345e-451e-8078-8bff3bb04574')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('482441b9-6e2f-462f-8346-db03a932d55b', 'kuali.attribute.grade.roster.level.type.key', 'ed47f17b-75f0-4d7f-9f19-09e6022f9380', 'kuali.lui.type.activity.offering.lecture', '2d9c09d2-e30b-4ffc-b687-c4918677277e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('588b36f2-03a6-4dc9-adde-5a29e476dc32', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', 'ed47f17b-75f0-4d7f-9f19-09e6022f9380', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c5eaad06-90b3-401f-b82c-ca28e6334e6f')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('ed47f17b-75f0-4d7f-9f19-09e6022f9380', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f7d3146a-b0af-48aa-bcbd-eee86c948089', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:04.412', '', 'HIST429G-CO-FO', 'db406537-1427-4ef2-a727-7310504960c8', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST429G-CO-FO', 'ed47f17b-75f0-4d7f-9f19-09e6022f9380', 'e6bd3d96-c3dc-4897-8418-03d22786d556')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('4456c7e3-376d-41f8-83be-77fbe6c6a006', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'b924f9c0-90f0-4eb0-a19e-ca064dc891e4', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, 'b3ffbad5-9f15-4c20-9646-27a46a58fff7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0b08cbcf-55d4-4bba-8f19-73af414305c3', 'kuali.attribute.max.enrollment.is.estimate', 'b3ffbad5-9f15-4c20-9646-27a46a58fff7', 0, 'dd399253-802c-42d5-bd19-d725c6a7faf2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f1129b42-5b83-412a-83bb-d24c291c79d8', 'kuali.attribute.nonstd.ts.indicator', 'b3ffbad5-9f15-4c20-9646-27a46a58fff7', 0, '38f1ecb1-93da-41a1-ac52-9dd7ffcf1c58')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('53588c09-0118-4024-81bf-f2fb32b73268', 'kuali.attribute.course.evaluation.indicator', 'b3ffbad5-9f15-4c20-9646-27a46a58fff7', 0, 'c7f45551-4817-4666-ab65-e825b5d85d54')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6038482c-bbe4-47b9-a763-1782f42ce999', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', 'b3ffbad5-9f15-4c20-9646-27a46a58fff7', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'e33a6bb8-afb4-4a9e-8815-2da727335aa6')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('e749a735-fd8c-4e42-8515-0983c967b145', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'b3ffbad5-9f15-4c20-9646-27a46a58fff7', '', 'kuali.lu.code.honorsOffering', 0, '1b08ef85-8496-4bc0-ba9b-3225f2546cda')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('2eac4dfd-273b-4af5-9b0f-1ef5ca54a3f2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:04.546', '', '', 'b3ffbad5-9f15-4c20-9646-27a46a58fff7', '', 'L.REALD', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '85e5eb71-8cd7-4521-9417-a5ef7867ab19')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('7d32a362-140a-4b41-995d-531c00c7ce1a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:04.591', '', 'HIST429G-FO-AO', 'ed47f17b-75f0-4d7f-9f19-09e6022f9380', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST429G-FO-AO', 'b3ffbad5-9f15-4c20-9646-27a46a58fff7', '3a10427b-f89d-41d8-88bf-6071eff9a2df')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('9c709ead-62f2-46f1-8fb8-37f5b3bfc967', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for HIST429G - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '873a40b8-f171-43da-9503-7ecd091f6558')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('ddb6797e-4777-4461-9f51-1b9638516d73', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A Schedule Request for HIST429G section 0101', 'A Schedule Request for HIST429G section 0101', 'A Schedule Request for HIST429G section 0101', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', 'd2c71028-c2dc-443d-b51d-9426441b0111', '873a40b8-f171-43da-9503-7ecd091f6558', '0aabd04d-6f2d-4b29-8fa3-5dc84922c794')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('d7e4546c-da50-47a1-a9f0-328fb7e6ac60', 0, '0aabd04d-6f2d-4b29-8fa3-5dc84922c794', 'c75fbf0d-bc4a-4ff5-82a5-63ad940c2db2')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('873a40b8-f171-43da-9503-7ecd091f6558', 'b3ffbad5-9f15-4c20-9646-27a46a58fff7')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('c75fbf0d-bc4a-4ff5-82a5-63ad940c2db2', 'ee115d93-579f-4c14-b3b1-917b820d14e1')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('c75fbf0d-bc4a-4ff5-82a5-63ad940c2db2', 'bde3fd74-77ee-4de2-9d55-7b4b1fb0f3ce')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('c75fbf0d-bc4a-4ff5-82a5-63ad940c2db2', 'F0AA72D6DD61F650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('f386b747-402d-4c3a-8136-621bc708eb82', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Athlete Seatpool', '1c2e7786-a2ab-4778-8e81-ea540c60b9b2', 1, 2, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'b6a03a70-662b-4440-acfb-1473898d62f9')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('a388aba2-b883-4c14-80c4-947774cd4d82', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Junior Seatpool', '4bae9f12-5901-4db9-b4a8-4dcc20781db5', 2, 4, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'aec70d8f-1c79-4854-bb2c-10da2728073a')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='f386b747-402d-4c3a-8136-621bc708eb82', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='b3ffbad5-9f15-4c20-9646-27a46a58fff7', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Athlete Seatpool', POPULATION_ID='1c2e7786-a2ab-4778-8e81-ea540c60b9b2', PROCESSING_PRIORITY=1, SEAT_LIMIT=2, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='b6a03a70-662b-4440-acfb-1473898d62f9' and VER_NBR=1
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('5708180d-1157-4ce1-a715-7a0c7267e6e1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'ad76ecec-e20d-4ed9-9d16-0b06e9fa2e43')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='a388aba2-b883-4c14-80c4-947774cd4d82', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='b3ffbad5-9f15-4c20-9646-27a46a58fff7', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Junior Seatpool', POPULATION_ID='4bae9f12-5901-4db9-b4a8-4dcc20781db5', PROCESSING_PRIORITY=2, SEAT_LIMIT=4, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='aec70d8f-1c79-4854-bb2c-10da2728073a' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('ad76ecec-e20d-4ed9-9d16-0b06e9fa2e43', 'b3ffbad5-9f15-4c20-9646-27a46a58fff7')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('ad76ecec-e20d-4ed9-9d16-0b06e9fa2e43', 'ed47f17b-75f0-4d7f-9f19-09e6022f9380')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('78033ac4-1fa4-4464-964e-5b25421b27c8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'ed47f17b-75f0-4d7f-9f19-09e6022f9380', 'CL 1', 'CL 1', '813313da-8566-4836-b0da-4b7c21df943e')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('b72608a0-6b37-4163-ab77-b44977a738db', 'kuali.lui.type.activity.offering.lecture', '813313da-8566-4836-b0da-4b7c21df943e', '871ce751-508b-4d61-a094-1b2a2b073cee')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('871ce751-508b-4d61-a094-1b2a2b073cee', 'b3ffbad5-9f15-4c20-9646-27a46a58fff7')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='813313da-8566-4836-b0da-4b7c21df943e' where ID='871ce751-508b-4d61-a094-1b2a2b073cee'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d5fa60b3-1890-4fb7-a62d-d3aa37ca67d1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '2acda501-2afd-4b9e-b08f-76d97c038024', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'f63fa7a5-a0a7-4283-bf74-5d4171c3c737')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d331508d-c5d7-4988-b97e-d224c098ab31', 'kuali.attribute.registration.group.aocluster.id', 'f63fa7a5-a0a7-4283-bf74-5d4171c3c737', '813313da-8566-4836-b0da-4b7c21df943e', '3b24c7c2-4065-4e1c-9277-b580387a6ff1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('75de6fcd-d5b2-42ed-ac10-388290b31123', 'kuali.attribute.registration.group.is.generated', 'f63fa7a5-a0a7-4283-bf74-5d4171c3c737', 1, '653bb58b-b202-4c1b-a9dc-ff8ad063a8fd')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('1edafcef-ae79-44e8-829e-d1fd041e9fe9', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'f63fa7a5-a0a7-4283-bf74-5d4171c3c737', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a47ca1af-619d-4ab3-9d7f-04e7f2e321ea')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('c45ce49a-faec-4b95-9efa-cecca3cdf85d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:05.166', '', 'HIST429G-FO-RG', 'ed47f17b-75f0-4d7f-9f19-09e6022f9380', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST429G-FO-RG', 'f63fa7a5-a0a7-4283-bf74-5d4171c3c737', '555b38f8-97da-4167-aadf-79d46566f210')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('3338d135-e7b1-4374-ad10-ec5606998c17', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:05.183', '', 'HIST429G-RG-AO', 'f63fa7a5-a0a7-4283-bf74-5d4171c3c737', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST429G-RG-AO', 'b3ffbad5-9f15-4c20-9646-27a46a58fff7', 'f85fb7a9-a4f9-405c-a87f-98172588332f')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('635b4f4f-0033-4c46-a257-de52502c1f89', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '73acba95-b7ab-494c-a726-6accd2de5257', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Basic (chemical) research conducted under the supervision of a faculty member.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'CHEM399B CO', 'Basic (chemical) research conducted under the supervision of a faculty member.', null, '2ae395bc-6ede-4070-9379-b939f4711316')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('962a1c9c-fe3c-4296-9334-30d2f86dbd7f', 'kuali.attribute.course.evaluation.indicator', '2ae395bc-6ede-4070-9379-b939f4711316', 0, '39accf00-c77d-47c9-a72d-3d29da3a54f0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ef779cda-2d5f-4dd6-864e-e276e46593de', 'kuali.attribute.final.exam.driver', '2ae395bc-6ede-4070-9379-b939f4711316', 'kuali.lu.exam.driver.ActivityOffering', '16ac5bea-15a1-4797-bc59-a41b33b5c91f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dc1b9ac6-5234-42ad-a3d1-f6b9e95f01a7', 'kuali.attribute.course.number.internal.suffix', '2ae395bc-6ede-4070-9379-b939f4711316', 'A', 'd8394d5e-f1a8-4385-b09a-9b9788926231')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c9e7ab93-4372-4099-9ff0-ca52c5c794b0', 'kuali.attribute.wait.list.type.key', '2ae395bc-6ede-4070-9379-b939f4711316', null, '7f6a364f-9f8f-4c2b-8632-2d397c35b03e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ca3a47a7-3c20-49d0-99f7-5ffe5c3cc151', 'kuali.attribute.wait.list.level.type.key', '2ae395bc-6ede-4070-9379-b939f4711316', 'AO', '02ac15ad-e7b5-4c56-a1b9-1898d52441eb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('449c24ee-c5aa-42c6-94ff-449bbdd0fe72', 'kuali.attribute.wait.list.indicator', '2ae395bc-6ede-4070-9379-b939f4711316', 1, '84a91a1f-d0ac-4c63-857c-062c063b1670')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9970fcbe-b13c-4a46-a31b-8a6a3be55bf8', 'kuali.attribute.where.fees.attached.flag', '2ae395bc-6ede-4070-9379-b939f4711316', 0, '2d4480bb-88fe-457c-af00-7f2b84a3ee78')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ebb9d56e-8124-4f8e-b349-342232261789', 'kuali.attribute.final.exam.use.matrix', '2ae395bc-6ede-4070-9379-b939f4711316', 1, '1d0dd1de-d6d8-4820-af3d-ff4428e658e4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b5f82fa1-45ca-40b7-a95c-d386f86db10d', 'kuali.attribute.final.exam.indicator', '2ae395bc-6ede-4070-9379-b939f4711316', 'STANDARD', '763f5247-bc66-432a-b527-3dc87bd8a5c0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4f9e3304-7674-4486-b437-b6f9d0628038', 'kuali.attribute.funding.source', '2ae395bc-6ede-4070-9379-b939f4711316', '', 'ce23795e-0431-473c-9125-a3221bea7c7a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('67908a3a-82b3-48d2-913e-240e8eb3caf4', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'CHEM399B', 'CHEM', '', 'Introduction to Chemical Research', '2ae395bc-6ede-4070-9379-b939f4711316', '', '', 'kuali.lui.identifier.state.active', 'B', 'kuali.lui.identifier.type.official', '', '9dd7cf75-61a5-4298-a50e-9499f3b8d676')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('3c397471-6d8c-4b2d-a69e-b8adcde446a3', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '2ae395bc-6ede-4070-9379-b939f4711316', '', 'kuali.lu.code.honorsOffering', '', 'a5537776-3483-493b-9339-ad29823a52e8')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('2ae395bc-6ede-4070-9379-b939f4711316', '4284516367', 'bc87a678-24fe-4cd5-977b-434a74709b88')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('2ae395bc-6ede-4070-9379-b939f4711316', '4284516367')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('2ae395bc-6ede-4070-9379-b939f4711316', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('2ae395bc-6ede-4070-9379-b939f4711316', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('2ae395bc-6ede-4070-9379-b939f4711316', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('2ae395bc-6ede-4070-9379-b939f4711316', 'kuali.creditType.credit.degree.1.0,1.5,2.0,2.5,3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('92ca5bb9-4673-4c35-8d24-104f79c26f05', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '0ae12d77-775c-499c-ade2-a4e2b60984a9', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '2b2d36d7-3700-4290-a4d3-5cea950fd4d8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c873ffec-e6b6-4b89-8364-fea035dd19bc', 'kuali.attribute.grade.roster.level.type.key', '2b2d36d7-3700-4290-a4d3-5cea950fd4d8', 'kuali.lui.type.activity.offering.lecture', '33fb73f3-a4d0-4e4f-9e33-25c0bc28b084')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a9f91916-d156-4533-bd92-c5734b0b7979', 'kuali.attribute.final.exam.level.type', '2b2d36d7-3700-4290-a4d3-5cea950fd4d8', 'kuali.lu.type.activity.Lecture', '1d828817-fbb0-4e8c-8c7c-29cfc749c446')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('391f7221-e67a-441f-b191-779eb48cbe5f', 'regCodePrefix', '2b2d36d7-3700-4290-a4d3-5cea950fd4d8', '1', 'f8d787b6-f01e-4ae2-9bb7-e2ab235154c1')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('f328def2-06a1-4817-8007-eea8e7a439f2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '2b2d36d7-3700-4290-a4d3-5cea950fd4d8', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a6567c91-6552-4ead-876f-2aab48321ba0')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('2b2d36d7-3700-4290-a4d3-5cea950fd4d8', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a0aa3507-4b35-4b6a-8ffd-7260369a31ec', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:08.709', '', 'CHEM399B-CO-FO', '2ae395bc-6ede-4070-9379-b939f4711316', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'CHEM399B-CO-FO', '2b2d36d7-3700-4290-a4d3-5cea950fd4d8', '5483c0c2-769e-48a0-81ad-8af0dc912426')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('c5c80241-dc9d-4e5b-8538-952dee18b466', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '020b3c6c-20f2-42f0-b1aa-fe16d1530339', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 5, 5, 'Lecture', 'Courses with lecture', null, '462920ef-df80-42f5-8eba-ed0091c9563d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('76eb12ff-e48a-4ab7-9f89-e7986f4338ed', 'kuali.attribute.max.enrollment.is.estimate', '462920ef-df80-42f5-8eba-ed0091c9563d', 0, '0fcad4bc-c2eb-431d-8d82-ae5c3d5594e7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4aca8c48-9758-4e13-83d1-75ec1a302b60', 'kuali.attribute.course.evaluation.indicator', '462920ef-df80-42f5-8eba-ed0091c9563d', 0, '029e1100-6fac-4474-8241-e36b1415ec9a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9da3e30d-6730-4600-be07-d98c546019ff', 'kuali.attribute.nonstd.ts.indicator', '462920ef-df80-42f5-8eba-ed0091c9563d', 0, '4474d455-d6f2-4825-84ae-8c538fe9894c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('9099f599-e57f-4d68-aec4-0ab8f6fe4e40', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '462920ef-df80-42f5-8eba-ed0091c9563d', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'd3d7b2df-83f5-4892-aaab-6dd1169e5363')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('c445310f-4eb8-4e90-93cd-4faa2f72c572', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '462920ef-df80-42f5-8eba-ed0091c9563d', '', 'kuali.lu.code.honorsOffering', 0, '3b30c1e8-3eb1-4b51-8deb-bb06fd8aaefd')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('1fc55d8c-fcb1-4407-bd7a-91a2c502fe40', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:08.804', '', '', '462920ef-df80-42f5-8eba-ed0091c9563d', '', 'B.GOUGHM', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '5b7a64e2-103f-4cf9-ba57-0eca75abe118')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('0a87b64e-7483-4751-b6de-74d33dd47650', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:08.851', '', 'CHEM399B-FO-AO', '2b2d36d7-3700-4290-a4d3-5cea950fd4d8', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM399B-FO-AO', '462920ef-df80-42f5-8eba-ed0091c9563d', 'cb2da5a7-364a-435c-a1dc-5bac3c6bb9eb')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('4bd5f5d8-6d61-4ccd-81be-35c3c02cf4bb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for CHEM399B - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'bec1bee9-495d-4bc2-9968-aa1077865197')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('4eb23e75-48bf-4f30-aea7-076065d2062a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for CHEM399B - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'bec1bee9-495d-4bc2-9968-aa1077865197', '8dd03ba1-6393-40c8-baa4-7f7a673ab455')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('4bfe5ca4-0c7c-4589-a97b-c0a8c5d68f00', 1, '8dd03ba1-6393-40c8-baa4-7f7a673ab455', '43a171d9-c5bc-44fa-b6e0-82ccb960405f')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('bec1bee9-495d-4bc2-9968-aa1077865197', '462920ef-df80-42f5-8eba-ed0091c9563d')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('43a171d9-c5bc-44fa-b6e0-82ccb960405f', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('e5b04e60-33b0-4cf8-8716-1121ef6194f3', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '84b9cb61-6621-4eec-9fe5-b2829cc041f7')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('f80af768-a173-4217-abce-9710d908e52d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'f4506eb3-4abf-49c2-abc0-8d9102a50980')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='e5b04e60-33b0-4cf8-8716-1121ef6194f3', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='462920ef-df80-42f5-8eba-ed0091c9563d', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='84b9cb61-6621-4eec-9fe5-b2829cc041f7' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('f4506eb3-4abf-49c2-abc0-8d9102a50980', '462920ef-df80-42f5-8eba-ed0091c9563d')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('f4506eb3-4abf-49c2-abc0-8d9102a50980', '2b2d36d7-3700-4290-a4d3-5cea950fd4d8')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('3c1ae075-3cb4-412e-88b1-5da8e73d78b0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '2b2d36d7-3700-4290-a4d3-5cea950fd4d8', 'CL 1', 'CL 1', '4930666d-141a-4dc9-a4f5-7cae1a651c94')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('7caa213d-6aa2-4d6f-9fb1-932ce0671a2b', 'kuali.lui.type.activity.offering.lecture', '4930666d-141a-4dc9-a4f5-7cae1a651c94', '1864b342-4939-466b-9f9b-6fd36d7e6c4a')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('1864b342-4939-466b-9f9b-6fd36d7e6c4a', '462920ef-df80-42f5-8eba-ed0091c9563d')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='4930666d-141a-4dc9-a4f5-7cae1a651c94' where ID='1864b342-4939-466b-9f9b-6fd36d7e6c4a'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('48e85593-03c5-4c81-ac59-72d71bf8fadf', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '0ae12d77-775c-499c-ade2-a4e2b60984a9', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'cea3e781-0dcf-4ff8-a3b3-1db18d3c7341')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8fb2b905-6f56-4802-bfb7-a1a661ebd6dd', 'kuali.attribute.registration.group.aocluster.id', 'cea3e781-0dcf-4ff8-a3b3-1db18d3c7341', '4930666d-141a-4dc9-a4f5-7cae1a651c94', '3646edfd-0128-4811-8518-d8396a4e5d11')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('11d178fd-059f-4c29-a1c0-a8330a5a22fa', 'kuali.attribute.registration.group.is.generated', 'cea3e781-0dcf-4ff8-a3b3-1db18d3c7341', 1, '430ac8cb-0f0e-42a9-ba5a-8d2c843bf505')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('acafee55-0688-402d-9cb7-c9ee5d8ecc4d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'cea3e781-0dcf-4ff8-a3b3-1db18d3c7341', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '8a948e53-0691-4227-bb59-cf8607e04180')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('c34a23db-16a0-48ec-a46e-0ded018c7e97', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:09.43', '', 'CHEM399B-FO-RG', '2b2d36d7-3700-4290-a4d3-5cea950fd4d8', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM399B-FO-RG', 'cea3e781-0dcf-4ff8-a3b3-1db18d3c7341', 'dc1f9f68-432e-4c48-9401-c74bd552a577')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('cfbf0f31-e1e5-4e7b-b389-63836b57d2b1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:09.462', '', 'CHEM399B-RG-AO', 'cea3e781-0dcf-4ff8-a3b3-1db18d3c7341', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM399B-RG-AO', '462920ef-df80-42f5-8eba-ed0091c9563d', '0da3d460-ca3f-459b-9f74-3523a2cef830')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('9c8df443-3d9c-48ad-8849-ec3d20a9fd40', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '303bf3d1-e960-42bf-843b-4d5bff305a7b', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Political, cultural and economic developments in 20th-century Europe.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST240 CO', 'Political, cultural and economic developments in 20th-century Europe.', null, '55cb2921-c9d1-4239-897e-b8637d708890')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a5db28fc-94a8-4d50-a499-cb586f28981c', 'kuali.attribute.funding.source', '55cb2921-c9d1-4239-897e-b8637d708890', '', '3a39c331-d2bc-4470-8d8d-7ccc2a9ca1c6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ad3cef0d-512f-47ce-acde-f41d98d9df9c', 'kuali.attribute.wait.list.indicator', '55cb2921-c9d1-4239-897e-b8637d708890', 1, 'e234032d-8673-4d9c-b161-0c1981198678')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('98044930-8647-42db-b8e0-5dcbb381d9f9', 'kuali.attribute.where.fees.attached.flag', '55cb2921-c9d1-4239-897e-b8637d708890', 0, '25164143-7c4f-4418-b13d-0dc63ff9f0ab')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e96cfa89-6c60-4e60-b699-97632bf9228c', 'kuali.attribute.final.exam.use.matrix', '55cb2921-c9d1-4239-897e-b8637d708890', 1, '1877808d-2f08-44ad-93e6-02510ec03ac0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('08003dac-1064-495e-b327-4c8530863bbc', 'kuali.attribute.course.number.internal.suffix', '55cb2921-c9d1-4239-897e-b8637d708890', 'A', '632ddefe-7881-4046-9f73-1985b61c42bf')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0a5e14b9-dc18-4188-b30a-50e81bd75986', 'kuali.attribute.course.evaluation.indicator', '55cb2921-c9d1-4239-897e-b8637d708890', 0, '371b5664-2ae8-455d-9bf8-6bc210e2d071')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('76b92bba-6b03-414a-9f25-2b605fb167e1', 'kuali.attribute.final.exam.indicator', '55cb2921-c9d1-4239-897e-b8637d708890', 'STANDARD', '2bb42393-8e69-4c8e-8f6a-def721568d41')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d97424f6-841c-4ce1-9309-a208f44d9d91', 'kuali.attribute.final.exam.driver', '55cb2921-c9d1-4239-897e-b8637d708890', 'kuali.lu.exam.driver.ActivityOffering', '2263f9f9-163f-4ddb-896e-96bee2207a76')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e646de7a-188e-400e-bf45-e165a71930ad', 'kuali.attribute.wait.list.type.key', '55cb2921-c9d1-4239-897e-b8637d708890', null, 'c0854886-716e-4b10-8783-6481e7dd214d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7250a473-35d7-4bdf-9388-2fdb797e1e12', 'kuali.attribute.wait.list.level.type.key', '55cb2921-c9d1-4239-897e-b8637d708890', 'AO', '9e109469-e7cb-44fe-a783-773d9a4b30bc')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d49f6f1c-3323-4987-be89-8bd72f3a7abe', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'HIST240', 'HIST', '', 'Europe in the Twentieth Century', '55cb2921-c9d1-4239-897e-b8637d708890', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '8be9f021-d67c-4fb1-ae5c-a40f8d09e947')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('f194c9b7-30a3-4df8-86b7-3d134f4407fd', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '55cb2921-c9d1-4239-897e-b8637d708890', '', 'kuali.lu.code.honorsOffering', '', '80edcbf2-77f5-4b64-b00a-f63c1d90c88d')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('55cb2921-c9d1-4239-897e-b8637d708890', '3213375036', 'fa8f60b6-ab3a-4741-8093-14083905c52c')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('55cb2921-c9d1-4239-897e-b8637d708890', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('55cb2921-c9d1-4239-897e-b8637d708890', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('55cb2921-c9d1-4239-897e-b8637d708890', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('55cb2921-c9d1-4239-897e-b8637d708890', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('55cb2921-c9d1-4239-897e-b8637d708890', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('748e1f5c-e39a-499b-a806-93a934ea2cb0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'e9b70810-8160-4c01-917b-6cd011536a5a', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'bef849d6-effb-4511-b575-865b54373353')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('34779ce4-d8ce-49a7-8ad1-71241585c3d4', 'kuali.attribute.grade.roster.level.type.key', 'bef849d6-effb-4511-b575-865b54373353', 'kuali.lui.type.activity.offering.lecture', '30ddeb71-cec3-40a6-a2a2-83bc03e9a048')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9bd7de80-5f9c-488b-acce-d90de0481e79', 'kuali.attribute.final.exam.level.type', 'bef849d6-effb-4511-b575-865b54373353', 'kuali.lu.type.activity.Lecture', '66240f9e-e25f-4534-a620-77e245000793')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('af7a60dd-e0c4-42a6-9d05-dab7ab1e5802', 'regCodePrefix', 'bef849d6-effb-4511-b575-865b54373353', '1', 'c476ef86-18fd-42f4-8e57-ab7d5a1664a2')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('3d47604e-4dce-444e-8c92-8f9ee5b4a365', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', 'bef849d6-effb-4511-b575-865b54373353', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '3ab13dd7-898d-4508-a075-70919256d4be')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('bef849d6-effb-4511-b575-865b54373353', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('bcb7dd23-bffe-4521-9205-910c5bc7c8a3', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:12.484', '', 'HIST240-CO-FO', '55cb2921-c9d1-4239-897e-b8637d708890', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST240-CO-FO', 'bef849d6-effb-4511-b575-865b54373353', '2e4cef6c-f51f-42dc-83e0-47a8d6459323')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d22b9742-57dc-418b-b8aa-a296fd19023b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '2ed3cb96-4219-4f5f-8825-b410addbfa5c', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, '2256aaff-346e-4e62-8710-711fdcf023e2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9bc9af0b-5958-4656-8fa0-50e76c5e8580', 'kuali.attribute.max.enrollment.is.estimate', '2256aaff-346e-4e62-8710-711fdcf023e2', 0, '2d2a8fb3-bc2a-4115-b560-0ee6fd10ed11')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e26f6f24-783c-41d1-9806-9a48b355dd95', 'kuali.attribute.course.evaluation.indicator', '2256aaff-346e-4e62-8710-711fdcf023e2', 0, '45853857-5185-4ca6-9178-f980fa2cf4cf')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2f1f96ec-8c1d-46e0-b027-c0d388e16a93', 'kuali.attribute.nonstd.ts.indicator', '2256aaff-346e-4e62-8710-711fdcf023e2', 0, '87d4c825-309d-438b-971e-3f82a5aebdf6')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('cbbf652d-7e0f-4fa2-bb21-4e2b4d047505', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '2256aaff-346e-4e62-8710-711fdcf023e2', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'e6bc86e6-dbab-4ad4-8d61-c939c863b524')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('65f7aa59-fdb3-47e8-9527-82ed0e3cbdef', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '2256aaff-346e-4e62-8710-711fdcf023e2', '', 'kuali.lu.code.honorsOffering', 0, '70504fe1-6ab1-46fb-a40b-13d8d303fcad')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('8696859e-f749-4809-a355-3e53605f933e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:12.696', '', '', '2256aaff-346e-4e62-8710-711fdcf023e2', '', 'S.PHILIPW', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '3da2013b-2fe4-4a0d-bcd6-48f359373bb1')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a5badd0d-01e9-446c-b241-38ab5cbe8a0a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:12.791', '', 'HIST240-FO-AO', 'bef849d6-effb-4511-b575-865b54373353', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST240-FO-AO', '2256aaff-346e-4e62-8710-711fdcf023e2', '8e1346d0-70bf-44f3-9538-29a9e5a8eeaf')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('425dcec3-0dda-4dc8-a7e5-09bda2f0cc59', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for HIST240 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '59004964-2d7d-48d3-909c-a7c75d13a236')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('18534387-df2f-40ae-ad27-77f4c1852ac8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for HIST240 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '59004964-2d7d-48d3-909c-a7c75d13a236', '8faaf3f3-a358-432c-8a32-f6320de265cc')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('8f70bec5-3c03-437f-b0ed-7f89920cec36', 0, '8faaf3f3-a358-432c-8a32-f6320de265cc', 'cd9d27ef-e4d1-4fb8-9880-de814543b985')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('59004964-2d7d-48d3-909c-a7c75d13a236', '2256aaff-346e-4e62-8710-711fdcf023e2')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('cd9d27ef-e4d1-4fb8-9880-de814543b985', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('cd9d27ef-e4d1-4fb8-9880-de814543b985', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('cd9d27ef-e4d1-4fb8-9880-de814543b985', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('cd9d27ef-e4d1-4fb8-9880-de814543b985', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('2ebb3f40-22cd-41a9-874c-ce6210d04214', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '3248e1e0-60f5-4d82-83b9-d408d1d3bc5c')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('3248e1e0-60f5-4d82-83b9-d408d1d3bc5c', '2256aaff-346e-4e62-8710-711fdcf023e2')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('3248e1e0-60f5-4d82-83b9-d408d1d3bc5c', 'bef849d6-effb-4511-b575-865b54373353')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('a5831eb2-ad9d-4a9d-b7f1-9e31b6c3e6f7', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'bef849d6-effb-4511-b575-865b54373353', 'CL 1', 'CL 1', '50aa6e22-9a08-4bda-895c-08e27963f358')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('254039c9-7c8d-4fb7-b86e-b88e06a05614', 'kuali.lui.type.activity.offering.lecture', '50aa6e22-9a08-4bda-895c-08e27963f358', 'ae251575-a66a-4f4c-8138-15df71fff517')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('ae251575-a66a-4f4c-8138-15df71fff517', '2256aaff-346e-4e62-8710-711fdcf023e2')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='50aa6e22-9a08-4bda-895c-08e27963f358' where ID='ae251575-a66a-4f4c-8138-15df71fff517'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('6e7d36c7-933d-4ee3-a06a-9578950c563d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'e9b70810-8160-4c01-917b-6cd011536a5a', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'a402cfee-bae5-497c-be0b-22e8ac36f6dc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c7a28a8b-4e0a-497f-ba6c-b8f887b2f34d', 'kuali.attribute.registration.group.is.generated', 'a402cfee-bae5-497c-be0b-22e8ac36f6dc', 1, '62c009cc-a6e0-4d4b-b73b-856526c5de44')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c4411d6e-7330-43a2-98f0-6cb990dea9bb', 'kuali.attribute.registration.group.aocluster.id', 'a402cfee-bae5-497c-be0b-22e8ac36f6dc', '50aa6e22-9a08-4bda-895c-08e27963f358', 'a05ca62e-affb-4902-99e0-09e9d44c3e9e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('1b3c461b-50bc-4cbd-8216-ffd2992740e9', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'a402cfee-bae5-497c-be0b-22e8ac36f6dc', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7189b89a-6ae1-4855-8bd0-c31fd1144820')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('36a83dda-f6dc-4197-9fe1-88b29331d5a2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:13.297', '', 'HIST240-FO-RG', 'bef849d6-effb-4511-b575-865b54373353', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST240-FO-RG', 'a402cfee-bae5-497c-be0b-22e8ac36f6dc', '3233efc9-981a-4372-b0b4-676bdcef9ac2')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('28991970-d2f2-418d-81e4-eb1f4ae9f538', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:13.312', '', 'HIST240-RG-AO', 'a402cfee-bae5-497c-be0b-22e8ac36f6dc', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST240-RG-AO', '2256aaff-346e-4e62-8710-711fdcf023e2', '6aef6384-b756-437b-a1ed-6a7547726cd3')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('b1c789fb-eb88-49a1-b684-c65ad75fbd71', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'CLUID-HIST329-200801000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST329N CO', '.', null, '94528acf-67ba-4d07-9776-a062ab92690a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('65d1b414-0622-4a51-a937-a8ca2a52e0a7', 'kuali.attribute.course.number.internal.suffix', '94528acf-67ba-4d07-9776-a062ab92690a', 'A', '149a094c-0707-4bf5-8392-0717fe136e49')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e4c9f478-6233-423a-a6e0-391d5127cfff', 'kuali.attribute.wait.list.level.type.key', '94528acf-67ba-4d07-9776-a062ab92690a', 'AO', '9dd5d804-8ee3-4eac-b4ba-2ed4f2ae70cc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('70b8f7ce-d627-4fdd-bfe6-2f35c41cd26b', 'kuali.attribute.final.exam.driver', '94528acf-67ba-4d07-9776-a062ab92690a', 'kuali.lu.exam.driver.ActivityOffering', 'dcff3e0a-8363-402c-9944-3c5dbd22ce14')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e6cea2ae-300c-4f84-8cd8-e448c9a29779', 'kuali.attribute.final.exam.use.matrix', '94528acf-67ba-4d07-9776-a062ab92690a', 1, 'f13f7d79-b045-4051-9aed-34172e653125')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('694b6368-5f0a-47df-9c4d-0efe121a1c9e', 'kuali.attribute.final.exam.indicator', '94528acf-67ba-4d07-9776-a062ab92690a', 'STANDARD', '7546b73c-f1f3-49b4-83bd-6b4bb128fa5d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('238813c0-59c6-42ca-8fc8-bdec492b7eeb', 'kuali.attribute.funding.source', '94528acf-67ba-4d07-9776-a062ab92690a', '', '0e97b769-e582-4e73-8ba3-cf7703df4cc0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('10908881-a363-4afa-9d55-c8369bcb7e7a', 'kuali.attribute.wait.list.indicator', '94528acf-67ba-4d07-9776-a062ab92690a', 1, 'dc7bc6c5-ded2-4420-b34c-4541d774ed5d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9553fb36-107c-4c25-9cf5-7defd96bb4fb', 'kuali.attribute.wait.list.type.key', '94528acf-67ba-4d07-9776-a062ab92690a', null, '4b21c6fd-999e-48b1-aed6-990e1412df62')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a66c3af3-62b4-49ed-8317-c960f302cfec', 'kuali.attribute.course.evaluation.indicator', '94528acf-67ba-4d07-9776-a062ab92690a', 0, 'd1b8dce2-0ff1-4ce8-828b-c451bd109500')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('069c83fb-14a0-4541-aec3-08c0f7e65521', 'kuali.attribute.where.fees.attached.flag', '94528acf-67ba-4d07-9776-a062ab92690a', 0, '549ea3d8-12e9-48ba-b18f-2db47f104b43')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('bf7b793b-f987-459f-9ba3-8d3deb0e2166', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'HIST329N', 'HIST', '', 'Special Topics in History', '94528acf-67ba-4d07-9776-a062ab92690a', '', '', 'kuali.lui.identifier.state.active', 'N', 'kuali.lui.identifier.type.official', '', '99ad5e75-9efa-42e0-90d3-4a5776a50148')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('4f0fa005-a5df-4405-93ea-365452760730', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '94528acf-67ba-4d07-9776-a062ab92690a', '', 'kuali.lu.code.honorsOffering', '', '8764264a-d4dd-4651-a7a6-206f00010777')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('94528acf-67ba-4d07-9776-a062ab92690a', '3213375036', 'bec73659-3e04-4968-975d-1da2df025a48')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('94528acf-67ba-4d07-9776-a062ab92690a', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('94528acf-67ba-4d07-9776-a062ab92690a', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('94528acf-67ba-4d07-9776-a062ab92690a', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('94528acf-67ba-4d07-9776-a062ab92690a', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('94528acf-67ba-4d07-9776-a062ab92690a', 'kuali.creditType.credit.degree.1.0,1.5,2.0,2.5,3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('dd76f96a-9b1f-429c-8d3b-d93d26a52ca6', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'cd1c5479-a556-45c2-a267-e3738132babe', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'd60acbee-e81f-42a8-9ff0-96dc6d9d522e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('38ff8c63-d4ec-438e-bf0a-45682f1354e4', 'regCodePrefix', 'd60acbee-e81f-42a8-9ff0-96dc6d9d522e', '1', '9a34f014-2c38-47c1-a3b9-cf010007fd90')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a019bd0a-743e-4a5e-9ccf-7f7c0c5a37ff', 'kuali.attribute.final.exam.level.type', 'd60acbee-e81f-42a8-9ff0-96dc6d9d522e', 'kuali.lu.type.activity.Lecture', '309121fc-a9ce-4902-8083-b6c87cf9bfe3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2a123cde-520c-49f1-94f7-687ed482f496', 'kuali.attribute.grade.roster.level.type.key', 'd60acbee-e81f-42a8-9ff0-96dc6d9d522e', 'kuali.lui.type.activity.offering.lecture', '38422526-cc3d-4eee-97af-54346bbf9422')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('2bf30b96-87b2-44b9-a143-4caebde794cb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', 'd60acbee-e81f-42a8-9ff0-96dc6d9d522e', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '381e7c6b-21df-4673-9724-ce3567857cf1')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('d60acbee-e81f-42a8-9ff0-96dc6d9d522e', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('afcca2e0-2c5d-4a05-95dc-85250857ef21', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:15.562', '', 'HIST329N-CO-FO', '94528acf-67ba-4d07-9776-a062ab92690a', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST329N-CO-FO', 'd60acbee-e81f-42a8-9ff0-96dc6d9d522e', '3d858622-1675-4e83-948a-6aa68add0d1b')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('376206c4-58a1-4cb9-8178-e9662295f05d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'ce5a1d8e-ab44-4a98-a3bc-73e328376eea', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 1, 1, 'Lecture', 'Courses with lecture', null, '9d4c19d7-ff73-43bc-8852-34986b1c8e52')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dbc0a3b0-bbe8-4ab7-99a8-4d19a4ab5869', 'kuali.attribute.max.enrollment.is.estimate', '9d4c19d7-ff73-43bc-8852-34986b1c8e52', 0, '3860aece-20d1-4a53-9bf6-2c9b6e7da781')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('756345d4-523e-461d-85f9-58f2133de8d3', 'kuali.attribute.course.evaluation.indicator', '9d4c19d7-ff73-43bc-8852-34986b1c8e52', 0, '85b666b6-f179-4132-9aaf-45b2d74d321d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('786ffac1-83a9-47f9-83d3-e277862ef6b1', 'kuali.attribute.nonstd.ts.indicator', '9d4c19d7-ff73-43bc-8852-34986b1c8e52', 0, '2a0562c9-e24e-4cb6-9c1b-6d6b4ba78917')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('67a7295c-88f3-4f15-a6a0-2657d189d124', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '9d4c19d7-ff73-43bc-8852-34986b1c8e52', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'f794bbe5-b01b-4158-abb9-3e15a1eeb05a')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('798fbb5d-b24c-4ff4-ac94-85342a9e8600', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '9d4c19d7-ff73-43bc-8852-34986b1c8e52', '', 'kuali.lu.code.honorsOffering', 0, '0100a560-ad2f-4f8a-a3b8-71ae3e2783c7')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('f7f805e8-e224-4e56-92d0-52a26da3e305', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:15.721', '', '', '9d4c19d7-ff73-43bc-8852-34986b1c8e52', '', 'P.FRANKJ', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'ca894c80-6661-414e-bad2-e8014dda484b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('067079f2-68ff-4b2d-b9c4-62d7ff9a53f8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:15.862', '', 'HIST329N-FO-AO', 'd60acbee-e81f-42a8-9ff0-96dc6d9d522e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST329N-FO-AO', '9d4c19d7-ff73-43bc-8852-34986b1c8e52', '5050e690-d9bf-4ce6-8490-9f686b983ad7')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('d527ae4d-29dc-49a0-9555-99062f9f3736', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for HIST329N - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '880cb98c-309b-4556-97f3-526ee59e10d4')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('e2dd182f-e9e1-4241-93e0-43e17f3872e2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for HIST329N - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '880cb98c-309b-4556-97f3-526ee59e10d4', '9a37b139-d39d-472b-a3d2-4252d769223d')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('73297038-3b2b-4179-9ac3-51847284c0ae', 1, '9a37b139-d39d-472b-a3d2-4252d769223d', 'ac7c0151-2180-4cdc-a050-5af05d0b9a6b')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('880cb98c-309b-4556-97f3-526ee59e10d4', '9d4c19d7-ff73-43bc-8852-34986b1c8e52')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('ac7c0151-2180-4cdc-a050-5af05d0b9a6b', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('6ba1f735-327b-402c-b58f-e90e795ffaa3', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '3331ee2e-93a7-4a1b-9737-49eed5f6968a')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('c6345dff-671b-4ddf-bce2-f6108c85a7c8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '788b637a-50e7-4242-b930-0ba97f4a5823')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='6ba1f735-327b-402c-b58f-e90e795ffaa3', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='9d4c19d7-ff73-43bc-8852-34986b1c8e52', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='3331ee2e-93a7-4a1b-9737-49eed5f6968a' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('788b637a-50e7-4242-b930-0ba97f4a5823', '9d4c19d7-ff73-43bc-8852-34986b1c8e52')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('788b637a-50e7-4242-b930-0ba97f4a5823', 'd60acbee-e81f-42a8-9ff0-96dc6d9d522e')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('9c5e6426-49c1-4e96-b842-77fb3a97239a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'd60acbee-e81f-42a8-9ff0-96dc6d9d522e', 'CL 1', 'CL 1', '47022c25-6266-45eb-8a16-132c00d23e18')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('4776345f-212f-453b-8268-5c4a7e052038', 'kuali.lui.type.activity.offering.lecture', '47022c25-6266-45eb-8a16-132c00d23e18', 'ec0216c1-0c22-47f1-9ead-70d3bd4439e3')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('ec0216c1-0c22-47f1-9ead-70d3bd4439e3', '9d4c19d7-ff73-43bc-8852-34986b1c8e52')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='47022c25-6266-45eb-8a16-132c00d23e18' where ID='ec0216c1-0c22-47f1-9ead-70d3bd4439e3'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('c8eae4d9-9860-4555-8931-98541bb4735d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'cd1c5479-a556-45c2-a267-e3738132babe', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '84bb8db0-a182-47d1-8c1c-d38ee1edf193')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('96f7c942-7599-4b2c-8377-8127feffec67', 'kuali.attribute.registration.group.is.generated', '84bb8db0-a182-47d1-8c1c-d38ee1edf193', 1, 'a3a33ad0-63c0-4ad4-b449-861a6a79d7ad')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8ce3c672-d5fe-4371-881b-7f620764a33d', 'kuali.attribute.registration.group.aocluster.id', '84bb8db0-a182-47d1-8c1c-d38ee1edf193', '47022c25-6266-45eb-8a16-132c00d23e18', '6e7288f4-ce00-4225-92ec-cc23ac6b239f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6a259535-e8c7-4a3d-b48b-366870a81151', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '84bb8db0-a182-47d1-8c1c-d38ee1edf193', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'b62fd1d9-3c1e-4af6-9da0-9bdc89e7450c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('9586498f-682b-48d4-bcec-66e84c8c42fb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:16.331', '', 'HIST329N-FO-RG', 'd60acbee-e81f-42a8-9ff0-96dc6d9d522e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST329N-FO-RG', '84bb8db0-a182-47d1-8c1c-d38ee1edf193', '4cd45240-27bf-43e3-9d88-c7e28e53cc63')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ee410eff-2539-4b13-ad32-b2b6233ae9a9', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:16.364', '', 'HIST329N-RG-AO', '84bb8db0-a182-47d1-8c1c-d38ee1edf193', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST329N-RG-AO', '9d4c19d7-ff73-43bc-8852-34986b1c8e52', '2f8efe18-bf31-417b-8feb-405d12eecd7f')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('4adf096b-f10e-429b-9c7e-c02ccecbbacf', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '0d58db7f-de33-4ab1-8d4a-7d8d9a7fce2b', TIMESTAMP '2014-01-01 19:00:00.0', '', 'A cross-section of American folk and popular songs in their cultural contexts; artists from Bill Monroe to Robert Johnson.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL462 CO', 'A cross-section of American folk and popular songs in their cultural contexts; artists from Bill Monroe to Robert Johnson.', null, '2d5149da-ab75-4478-b946-e78403fa1c50')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b5a46ee5-e073-487c-8d2c-40a8acd88640', 'kuali.attribute.where.fees.attached.flag', '2d5149da-ab75-4478-b946-e78403fa1c50', 0, '84c420c8-5736-4fcf-b4ec-aebbb4db47d6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('af5f4255-0cd1-43a8-b1a4-88fa220e4338', 'kuali.attribute.course.number.internal.suffix', '2d5149da-ab75-4478-b946-e78403fa1c50', 'A', 'e2d01ddb-6cf9-4f17-ae51-cf91c624e980')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6d000425-8039-41a4-a090-6557406d8fb7', 'kuali.attribute.wait.list.level.type.key', '2d5149da-ab75-4478-b946-e78403fa1c50', 'AO', 'd7f204ed-d03e-4e88-9415-68532f48af4e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2221b3d4-0f72-4567-a640-dfb9284bb7a1', 'kuali.attribute.wait.list.type.key', '2d5149da-ab75-4478-b946-e78403fa1c50', null, 'c2aea40e-5382-46cd-9a4e-2b0f09581272')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a5938339-97dc-40e4-9b85-ce0e10e06f06', 'kuali.attribute.final.exam.driver', '2d5149da-ab75-4478-b946-e78403fa1c50', 'kuali.lu.exam.driver.ActivityOffering', '4307d3bd-7ae4-4ca3-99b1-97ed95554a2b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5288892e-7f6f-4c7a-b3ba-8a7e9a6321ad', 'kuali.attribute.final.exam.indicator', '2d5149da-ab75-4478-b946-e78403fa1c50', 'STANDARD', 'd848cf3f-1f21-4a30-ad0f-8be6092b38ec')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('697b7349-831c-416a-af3f-76a763bd1ae9', 'kuali.attribute.final.exam.use.matrix', '2d5149da-ab75-4478-b946-e78403fa1c50', 1, '2c2e2e6c-443a-4839-8c3f-4a7899fca628')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('774db015-e763-4b35-8341-a74614cc85dc', 'kuali.attribute.course.evaluation.indicator', '2d5149da-ab75-4478-b946-e78403fa1c50', 0, 'a64351ef-6c7b-4073-85c9-a3da93f9a9ec')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('90307f2a-8d87-4630-bfb2-b793dada6475', 'kuali.attribute.wait.list.indicator', '2d5149da-ab75-4478-b946-e78403fa1c50', 1, '79823406-0dab-46d2-8dfa-840c81b84529')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('170bec33-a981-42ca-a27b-fc168ec0c3f7', 'kuali.attribute.funding.source', '2d5149da-ab75-4478-b946-e78403fa1c50', '', 'e546d93d-27e5-45dc-ace1-9b4d524faff2')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c995f844-70b3-4a00-905f-cc9fbd794543', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'ENGL462', 'ENGL', '', 'Folksong and Ballad', '2d5149da-ab75-4478-b946-e78403fa1c50', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7e2fc36f-bf11-419c-9496-8afeb8899ee9')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('6b150acd-6fcf-42a6-b7df-ed26aa5b4cba', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '2d5149da-ab75-4478-b946-e78403fa1c50', '', 'kuali.lu.code.honorsOffering', '', 'd8b096d7-8fad-48a5-aa56-7eed3ef51b07')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('2d5149da-ab75-4478-b946-e78403fa1c50', '2677933260', '32b7354d-1840-4d17-906f-f96cd71ee18d')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('2d5149da-ab75-4478-b946-e78403fa1c50', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('2d5149da-ab75-4478-b946-e78403fa1c50', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('2d5149da-ab75-4478-b946-e78403fa1c50', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('2d5149da-ab75-4478-b946-e78403fa1c50', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('2d5149da-ab75-4478-b946-e78403fa1c50', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('edb8cf88-4124-4f6e-a9d2-02905b449bbe', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '85d23c02-4b65-44bd-8718-3e0f409a7015', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '46c92cbe-1005-4df7-be1b-e8cb7f06bf64')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3b557f78-48dc-4356-8fdd-ab0aca2a9175', 'kuali.attribute.grade.roster.level.type.key', '46c92cbe-1005-4df7-be1b-e8cb7f06bf64', 'kuali.lui.type.activity.offering.lecture', '9ad7e7af-1f1d-4274-9e04-478d7057c7a0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('33ae0b28-7861-46fc-98cb-cc919c971097', 'kuali.attribute.final.exam.level.type', '46c92cbe-1005-4df7-be1b-e8cb7f06bf64', 'kuali.lu.type.activity.Lecture', '20e57370-a17e-402b-9510-46ce6837c4af')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e36f1695-7979-47d2-85b4-e6d0bdd2515f', 'regCodePrefix', '46c92cbe-1005-4df7-be1b-e8cb7f06bf64', '1', 'c0565499-f666-499e-905f-831b28825685')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('99551baf-6b6a-4f86-96ba-033df96442b6', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '46c92cbe-1005-4df7-be1b-e8cb7f06bf64', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '8148e647-1958-49e4-9595-90d32c68a5a0')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('46c92cbe-1005-4df7-be1b-e8cb7f06bf64', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('361d5254-358e-4112-a918-ebaee0d3c89e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:19.067', '', 'ENGL462-CO-FO', '2d5149da-ab75-4478-b946-e78403fa1c50', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL462-CO-FO', '46c92cbe-1005-4df7-be1b-e8cb7f06bf64', '5126db00-2c9e-4f88-9392-8030718eda0d')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('31d75250-b268-4c84-a405-715681ae184b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '702e6aba-fe80-4713-9ec7-caa1759a74e8', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, '7bd71262-84cf-4535-a22a-d1083457a048')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3e7cd087-f852-470c-9204-08068e317b28', 'kuali.attribute.max.enrollment.is.estimate', '7bd71262-84cf-4535-a22a-d1083457a048', 0, 'e532ad71-8c5d-4814-ab09-57361e5fe410')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b352f532-61f5-42a5-9ecb-73236245c477', 'kuali.attribute.nonstd.ts.indicator', '7bd71262-84cf-4535-a22a-d1083457a048', 0, '73ed03cc-6feb-4e5d-ad38-cab48f524efb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1bc58903-3af3-4234-a027-bfa8e2758fe4', 'kuali.attribute.course.evaluation.indicator', '7bd71262-84cf-4535-a22a-d1083457a048', 0, 'c0344fe1-31f3-46e7-825b-11c8acab75b7')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('700bb808-7ecb-48ce-a58b-3b33ce306d21', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '7bd71262-84cf-4535-a22a-d1083457a048', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'd74d11be-58a5-40bc-86cc-f92703da2568')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('13bf6b7a-b8c8-49a3-8a3d-b85109d89480', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '7bd71262-84cf-4535-a22a-d1083457a048', '', 'kuali.lu.code.honorsOffering', 0, '559f124e-22c5-4300-b6ff-0edf5e9914fe')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('d058952a-528c-41bf-ad5a-2582614daa8a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:19.161', '', '', '7bd71262-84cf-4535-a22a-d1083457a048', '', 'P.BENJAMINS', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '790a48db-7f3f-4e89-a582-c3f9f09b3178')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('6cdac170-ee94-4f55-af7d-22b47ca2b746', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:19.223', '', 'ENGL462-FO-AO', '46c92cbe-1005-4df7-be1b-e8cb7f06bf64', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL462-FO-AO', '7bd71262-84cf-4535-a22a-d1083457a048', '6d08cf28-a90c-49a5-89f1-80c2e41e4c9c')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('5d194b40-dffa-49e8-b0b4-b75a850b879f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for ENGL462 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '91d1ba3f-90b3-4404-a93c-c734d3bee71a')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('1df10c15-10a4-4339-8955-fdbfc2b959bb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for ENGL462 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '91d1ba3f-90b3-4404-a93c-c734d3bee71a', '850603c7-1018-4177-bea9-8bdeb304e75e')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('79979f93-f029-4c02-acd4-32707059bcd0', 0, '850603c7-1018-4177-bea9-8bdeb304e75e', '1d47602d-ba85-4b95-b289-a756d3169000')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('91d1ba3f-90b3-4404-a93c-c734d3bee71a', '7bd71262-84cf-4535-a22a-d1083457a048')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('1d47602d-ba85-4b95-b289-a756d3169000', '84e53b2a-f39a-4b7a-8916-731087cff42d')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('1d47602d-ba85-4b95-b289-a756d3169000', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('1d47602d-ba85-4b95-b289-a756d3169000', 'f0c251ff-584a-434f-8cf3-9e4e71a9254d')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('1d47602d-ba85-4b95-b289-a756d3169000', 'F0AA72D6DD03F650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('23048e9f-e555-499a-b791-beb760c82f65', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Athlete Seatpool', '1c2e7786-a2ab-4778-8e81-ea540c60b9b2', 1, 2, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'e66dbbb9-cf7d-4cbb-8592-dda2f4e7fab0')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('722594f1-a443-4178-900b-cef20dd5787e', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Junior Seatpool', '4bae9f12-5901-4db9-b4a8-4dcc20781db5', 2, 4, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '14163a52-cea3-41c6-9937-b64617dabb68')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='23048e9f-e555-499a-b791-beb760c82f65', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='7bd71262-84cf-4535-a22a-d1083457a048', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Athlete Seatpool', POPULATION_ID='1c2e7786-a2ab-4778-8e81-ea540c60b9b2', PROCESSING_PRIORITY=1, SEAT_LIMIT=2, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='e66dbbb9-cf7d-4cbb-8592-dda2f4e7fab0' and VER_NBR=1
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('70966c13-887b-4913-8829-e0a723830f23', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '0e5bb998-6ccf-4e50-9b64-012f0f3e5147')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='722594f1-a443-4178-900b-cef20dd5787e', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='7bd71262-84cf-4535-a22a-d1083457a048', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Junior Seatpool', POPULATION_ID='4bae9f12-5901-4db9-b4a8-4dcc20781db5', PROCESSING_PRIORITY=2, SEAT_LIMIT=4, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='14163a52-cea3-41c6-9937-b64617dabb68' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('0e5bb998-6ccf-4e50-9b64-012f0f3e5147', '7bd71262-84cf-4535-a22a-d1083457a048')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('0e5bb998-6ccf-4e50-9b64-012f0f3e5147', '46c92cbe-1005-4df7-be1b-e8cb7f06bf64')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('ac1a6d26-cea1-472c-9529-805348ee74d0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '46c92cbe-1005-4df7-be1b-e8cb7f06bf64', 'CL 1', 'CL 1', '08e38ba5-3bc2-4333-8b2a-c96eb918ef06')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('7e7adae8-7545-43a9-b05b-f161f4e10142', 'kuali.lui.type.activity.offering.lecture', '08e38ba5-3bc2-4333-8b2a-c96eb918ef06', '70e97667-43d7-42df-baf0-671877cfb12e')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('70e97667-43d7-42df-baf0-671877cfb12e', '7bd71262-84cf-4535-a22a-d1083457a048')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='08e38ba5-3bc2-4333-8b2a-c96eb918ef06' where ID='70e97667-43d7-42df-baf0-671877cfb12e'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('3d17ea3c-9d45-445c-b095-07283a9b26e2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '85d23c02-4b65-44bd-8718-3e0f409a7015', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'ca8dc50d-729f-4087-b800-9fb242831ff6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fd62108f-93ca-4e83-9ea2-102c69ca492e', 'kuali.attribute.registration.group.aocluster.id', 'ca8dc50d-729f-4087-b800-9fb242831ff6', '08e38ba5-3bc2-4333-8b2a-c96eb918ef06', '84389967-77bd-498d-a733-fdc26d067e14')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bcb0e6da-08c9-4bb6-8ba6-52b3c058b1c6', 'kuali.attribute.registration.group.is.generated', 'ca8dc50d-729f-4087-b800-9fb242831ff6', 1, 'da186451-e219-472c-bd6f-dad7d89413dd')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('b3f7aadf-a81b-48cd-8c01-7880a16e8f97', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'ca8dc50d-729f-4087-b800-9fb242831ff6', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '33b84265-3a36-4606-91aa-3fcc4ba563da')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('b5586bcb-4a34-42ed-b1c4-f0b8ec1ba0d4', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:19.77', '', 'ENGL462-FO-RG', '46c92cbe-1005-4df7-be1b-e8cb7f06bf64', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL462-FO-RG', 'ca8dc50d-729f-4087-b800-9fb242831ff6', 'd769e24d-0d24-4228-bbf8-6e878a706214')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e435e940-983d-4f0a-99bd-e89ee3536512', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:19.805', '', 'ENGL462-RG-AO', 'ca8dc50d-729f-4087-b800-9fb242831ff6', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL462-RG-AO', '7bd71262-84cf-4535-a22a-d1083457a048', '6945f696-5a2e-4ea1-a55d-ddf3766f98d5')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('ef2920ac-aa81-439d-9ac5-2abd9d26f481', '37ea9b90-8228-490f-a34e-18b36b5b17d6', 'courseOfferingCode', 'HIST329N-P', '311332d7-6aa8-4540-92fe-9887fd86f39c')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('d0a4115f-54bb-4e8a-9f90-ac784792f94a', 'A', 'courseOfferingCode', 'HIST329N', '90d37a2f-48b0-4d38-a688-b2fbafe023a6')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='37ea9b90-8228-490f-a34e-18b36b5b17d6' and UNIQUE_KEY='HIST329N-P' and NAMESPACE='courseOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='HIST329N' and NAMESPACE='courseOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='HIST329N-P' and codegenera1_.NAMESPACE='courseOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('67e3b612-13a5-42b6-9dd9-aee554cb876e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'CLUID-HIST329-200801000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST329K CO', '.', null, '06db6874-69ff-4491-8d93-3703f850af36')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1eda2169-e3ae-48af-ad13-5767d5d7a3dc', 'kuali.attribute.final.exam.driver', '06db6874-69ff-4491-8d93-3703f850af36', 'kuali.lu.exam.driver.ActivityOffering', '232a8709-ef55-447f-b2fd-ff6d217275fc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('381c0169-87f9-4455-8ae8-7088adaf3520', 'kuali.attribute.course.number.internal.suffix', '06db6874-69ff-4491-8d93-3703f850af36', 'A', '4c33c63e-977f-4dfd-8832-c863f9400e89')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('11dfdaa1-efe3-492a-b6dc-2f398860b538', 'kuali.attribute.where.fees.attached.flag', '06db6874-69ff-4491-8d93-3703f850af36', 0, '29f3ce92-e453-407d-ad6f-2607ddee6fb9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e5c290bd-245e-497c-85a2-ed84a98590dd', 'kuali.attribute.wait.list.type.key', '06db6874-69ff-4491-8d93-3703f850af36', null, '0bb6397e-998b-4d00-b8aa-c746c5d200a5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('06488da7-61cc-4da0-9411-d685e36f8dac', 'kuali.attribute.final.exam.use.matrix', '06db6874-69ff-4491-8d93-3703f850af36', 1, 'd097c1e3-ec05-4757-8ef4-4d6381a493c9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9f0c4804-eacc-4179-963b-e66b6d5e2140', 'kuali.attribute.final.exam.indicator', '06db6874-69ff-4491-8d93-3703f850af36', 'STANDARD', '20bb1d19-81d6-4520-b48e-3eac97e70c20')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2023d8a3-b07b-4874-be22-b9663226b5a1', 'kuali.attribute.wait.list.indicator', '06db6874-69ff-4491-8d93-3703f850af36', 1, 'f36dfb9f-8e20-4910-ba64-51088aa1379e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8f37df22-05ce-429b-b7b8-76db3891c380', 'kuali.attribute.wait.list.level.type.key', '06db6874-69ff-4491-8d93-3703f850af36', 'AO', '056b5205-4703-47e1-bb60-67e4a5515220')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('490f619f-0c67-419c-b15d-6caf113524b9', 'kuali.attribute.course.evaluation.indicator', '06db6874-69ff-4491-8d93-3703f850af36', 0, '13cd28ec-67b0-43b4-8b9e-d5608a4b047f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fe490d79-77f1-4cec-918b-b53a3ddacd06', 'kuali.attribute.funding.source', '06db6874-69ff-4491-8d93-3703f850af36', '', 'f1430506-3d83-4536-bdd7-0843331b2497')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c9c00f1e-2f77-471e-93a2-82c303fb5980', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'HIST329K', 'HIST', '', 'Special Topics in History', '06db6874-69ff-4491-8d93-3703f850af36', '', '', 'kuali.lui.identifier.state.active', 'K', 'kuali.lui.identifier.type.official', '', 'a3b5740c-235c-469d-a4bc-8b5d5a806704')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('a9f81377-1523-4f71-bca0-c49d024d4412', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '06db6874-69ff-4491-8d93-3703f850af36', '', 'kuali.lu.code.honorsOffering', '', '6b352df5-7e51-4b0a-a166-d781b16273a5')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('06db6874-69ff-4491-8d93-3703f850af36', '3213375036', 'c183776e-b981-46db-ba4f-e32d1737aa0d')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('06db6874-69ff-4491-8d93-3703f850af36', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('06db6874-69ff-4491-8d93-3703f850af36', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('06db6874-69ff-4491-8d93-3703f850af36', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('06db6874-69ff-4491-8d93-3703f850af36', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('06db6874-69ff-4491-8d93-3703f850af36', 'kuali.creditType.credit.degree.1.0,1.5,2.0,2.5,3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ec4fa7a2-cfd0-4509-a10b-55b583bcbb1c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'cd1c5479-a556-45c2-a267-e3738132babe', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'dc939b93-d355-42e9-9c7a-44f109f07768')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3f9dab7f-2052-47ff-8301-b37c103b0ce0', 'kuali.attribute.final.exam.level.type', 'dc939b93-d355-42e9-9c7a-44f109f07768', 'kuali.lu.type.activity.Lecture', '7fe8eb15-0971-47e3-b705-62d351809670')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('72c8ac7d-2a58-428e-bb87-ce44481b5065', 'kuali.attribute.grade.roster.level.type.key', 'dc939b93-d355-42e9-9c7a-44f109f07768', 'kuali.lui.type.activity.offering.lecture', '8125fcd6-8662-4b70-8567-5af1c03350fd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('be553a42-a033-4def-883c-b6d6009ba2a8', 'regCodePrefix', 'dc939b93-d355-42e9-9c7a-44f109f07768', '1', '802b9082-f05a-4027-a033-47a84b2b631c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5bbc519b-e77f-4dcf-a0a4-c95f6a0a31a0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', 'dc939b93-d355-42e9-9c7a-44f109f07768', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'd85d982c-5520-43f2-b24f-0305e2aa3fc4')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('dc939b93-d355-42e9-9c7a-44f109f07768', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('2ace467e-adca-4b7d-8c3c-9dedfd0ae602', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:22.262', '', 'HIST329K-CO-FO', '06db6874-69ff-4491-8d93-3703f850af36', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST329K-CO-FO', 'dc939b93-d355-42e9-9c7a-44f109f07768', '7344d159-0cfb-437f-80b9-c07bf52f434d')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('da2161d3-9fee-4402-985d-1114ef743367', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'e4d0b217-a16e-4efe-96c7-87e307bfffc4', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, '71b0225e-f58a-46e5-9bfa-b746a7f3edba')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0c37a55d-cbf9-4868-b4fb-7c819cabb836', 'kuali.attribute.nonstd.ts.indicator', '71b0225e-f58a-46e5-9bfa-b746a7f3edba', 0, '61ed996e-db76-4c24-ad8f-415120dc47fa')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1380a619-fc55-4113-b2f2-a348cce4897d', 'kuali.attribute.course.evaluation.indicator', '71b0225e-f58a-46e5-9bfa-b746a7f3edba', 0, '11375357-09cc-48fd-8687-c514206bb344')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5f5d6621-2fe4-4fcd-a85f-ca3676eed100', 'kuali.attribute.max.enrollment.is.estimate', '71b0225e-f58a-46e5-9bfa-b746a7f3edba', 0, '3276971b-df84-427f-bd3c-eeb4ceed105c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('2ae0dbf8-f41e-412d-9db9-0f2ef9cbe88d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '71b0225e-f58a-46e5-9bfa-b746a7f3edba', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '599972c3-ee2e-4800-8d5f-2b4c0a8071c8')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('5cbe8365-63f4-4308-8306-a932384286b2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '71b0225e-f58a-46e5-9bfa-b746a7f3edba', '', 'kuali.lu.code.honorsOffering', 0, '5add0688-a6a8-44c6-8d06-958d39e08f57')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('55f21bff-8843-461f-bcbc-c00938edb952', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:22.393', '', '', '71b0225e-f58a-46e5-9bfa-b746a7f3edba', '', 'L.GEJ', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'b1459d3c-5f15-428e-988b-6f0239a2ec92')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('54fa0668-930f-4285-8137-1629f716b499', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:22.454', '', 'HIST329K-FO-AO', 'dc939b93-d355-42e9-9c7a-44f109f07768', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST329K-FO-AO', '71b0225e-f58a-46e5-9bfa-b746a7f3edba', 'c129f6bc-9163-4610-a107-52c3334dbf4c')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('2ddcb06c-bd52-426a-80e7-8b376de56ec3', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for HIST329K - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '250675da-ea39-4af9-aeb7-75b752673beb')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('8da09bd2-165d-4321-8a9c-1cc1cfff0bd0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for HIST329K - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '250675da-ea39-4af9-aeb7-75b752673beb', '31ccb801-f9b9-4215-9a9c-427b1691949b')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('95f4345d-f312-40f7-8db6-180cd12c517a', 0, '31ccb801-f9b9-4215-9a9c-427b1691949b', '86ad34de-4190-4581-84ff-bb089bdf7543')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('250675da-ea39-4af9-aeb7-75b752673beb', '71b0225e-f58a-46e5-9bfa-b746a7f3edba')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('86ad34de-4190-4581-84ff-bb089bdf7543', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('86ad34de-4190-4581-84ff-bb089bdf7543', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('86ad34de-4190-4581-84ff-bb089bdf7543', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('86ad34de-4190-4581-84ff-bb089bdf7543', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('c8c7a29f-aed6-44ba-82ae-3b3824b66a62', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'aea5716b-66e3-475b-b658-42123c84b196')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('103868f7-35eb-402c-86d2-39dce047e711', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'f05d8fa4-251f-47f3-8584-25eb0df6c00e')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='c8c7a29f-aed6-44ba-82ae-3b3824b66a62', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='71b0225e-f58a-46e5-9bfa-b746a7f3edba', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='aea5716b-66e3-475b-b658-42123c84b196' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('f05d8fa4-251f-47f3-8584-25eb0df6c00e', '71b0225e-f58a-46e5-9bfa-b746a7f3edba')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('f05d8fa4-251f-47f3-8584-25eb0df6c00e', 'dc939b93-d355-42e9-9c7a-44f109f07768')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('a9408144-229b-40c5-b9eb-f751a5fb073c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'dc939b93-d355-42e9-9c7a-44f109f07768', 'CL 1', 'CL 1', '0c9f4275-f246-4663-8577-93bc4779bc3b')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('d6f6df9c-b2a4-4a43-9f44-ad9d2946553a', 'kuali.lui.type.activity.offering.lecture', '0c9f4275-f246-4663-8577-93bc4779bc3b', 'd8ee0703-4775-4f85-b0ed-f79f58fb12c9')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('d8ee0703-4775-4f85-b0ed-f79f58fb12c9', '71b0225e-f58a-46e5-9bfa-b746a7f3edba')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='0c9f4275-f246-4663-8577-93bc4779bc3b' where ID='d8ee0703-4775-4f85-b0ed-f79f58fb12c9'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('c779cc91-4783-4023-9090-7af213d0a10f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'cd1c5479-a556-45c2-a267-e3738132babe', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'be26afe4-cece-4ee5-9f9d-eca4c287802d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('04c8a3de-c576-4700-9842-02e5455cc94c', 'kuali.attribute.registration.group.aocluster.id', 'be26afe4-cece-4ee5-9f9d-eca4c287802d', '0c9f4275-f246-4663-8577-93bc4779bc3b', 'f228c56b-7a1a-49fb-9518-b116f19f9d6e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1e07b727-dc88-4bae-8865-1e12500c2ecf', 'kuali.attribute.registration.group.is.generated', 'be26afe4-cece-4ee5-9f9d-eca4c287802d', 1, 'bf284b63-ae6c-4008-bbbf-592a531fafc2')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a362a930-7c4a-4b85-9838-b96a8393f759', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'be26afe4-cece-4ee5-9f9d-eca4c287802d', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'f0200894-af7c-4a08-9854-ee6d7086e88b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('040cb342-f0c3-4ea7-9871-a480dcbd0447', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:22.972', '', 'HIST329K-FO-RG', 'dc939b93-d355-42e9-9c7a-44f109f07768', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST329K-FO-RG', 'be26afe4-cece-4ee5-9f9d-eca4c287802d', '48b7cd8f-6467-468b-8956-d07aa146c312')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('5d524951-84ad-4f11-b1a3-72a783be26ff', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:22.991', '', 'HIST329K-RG-AO', 'be26afe4-cece-4ee5-9f9d-eca4c287802d', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST329K-RG-AO', '71b0225e-f58a-46e5-9bfa-b746a7f3edba', '12803e98-53e6-4131-abb8-b30892ebe069')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('f870bdca-de69-49d1-9f95-9886a9cfd736', 'b3a23a4c-0cde-43b4-8435-21a6af156407', 'courseOfferingCode', 'CHEM399B-P', '275c8968-aa26-4330-b64b-64ed148cb2b0')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('aaf1d714-e3a4-4b9b-9cd6-01cd95401af5', 'A', 'courseOfferingCode', 'CHEM399B', '80b6e299-30ec-4ae6-8de6-50155e1512fd')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='b3a23a4c-0cde-43b4-8435-21a6af156407' and UNIQUE_KEY='CHEM399B-P' and NAMESPACE='courseOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='CHEM399B' and NAMESPACE='courseOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='CHEM399B-P' and codegenera1_.NAMESPACE='courseOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('674f5433-4e26-4604-aeeb-d7d38ed94649', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '73acba95-b7ab-494c-a726-6accd2de5257', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Basic (chemical) research conducted under the supervision of a faculty member.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'CHEM399A CO', 'Basic (chemical) research conducted under the supervision of a faculty member.', null, 'b34ef216-94c8-413e-a34f-c08b6d78fb0e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a78ce2e8-ad4c-460a-a42c-7e8160d504f3', 'kuali.attribute.final.exam.indicator', 'b34ef216-94c8-413e-a34f-c08b6d78fb0e', 'STANDARD', '7a6d7cb2-4ddf-4850-a231-8ae815f5961e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('304596da-4e60-424a-a4c9-e7bea0b9bfa2', 'kuali.attribute.course.number.internal.suffix', 'b34ef216-94c8-413e-a34f-c08b6d78fb0e', 'A', 'f5854ac6-3f3e-47a3-a3fb-ae277e9d4364')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ccbbffbf-95c6-4ab3-bcb5-654368b7112a', 'kuali.attribute.course.evaluation.indicator', 'b34ef216-94c8-413e-a34f-c08b6d78fb0e', 0, 'f69d5b70-6684-4819-8e35-cfa0320266aa')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b0032e9f-3c7f-4e29-916d-ea10ba7876a1', 'kuali.attribute.final.exam.driver', 'b34ef216-94c8-413e-a34f-c08b6d78fb0e', 'kuali.lu.exam.driver.ActivityOffering', '8192c52b-468e-4721-aaaf-27f80c414084')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('66abe1c9-cf4f-4572-91b4-f2e0dc5ff47f', 'kuali.attribute.where.fees.attached.flag', 'b34ef216-94c8-413e-a34f-c08b6d78fb0e', 0, '4e3a81c7-eb70-4ee8-9bd7-467a42becf12')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e87b5232-42a6-49bf-8209-b4d351110805', 'kuali.attribute.wait.list.type.key', 'b34ef216-94c8-413e-a34f-c08b6d78fb0e', null, '78f9829b-03c6-491a-9011-7e2b23359611')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4473ff10-6649-453b-87cd-596ed06cff85', 'kuali.attribute.wait.list.indicator', 'b34ef216-94c8-413e-a34f-c08b6d78fb0e', 1, '2c4df7f5-3936-4262-8032-a0a03ca69cd3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('12738a3f-64aa-446c-8db0-07842f76fa48', 'kuali.attribute.funding.source', 'b34ef216-94c8-413e-a34f-c08b6d78fb0e', '', 'deecb647-ee01-47b0-a6e0-df9e8dcc8940')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dbdf1843-25c9-49a2-86c4-ef47155659c5', 'kuali.attribute.wait.list.level.type.key', 'b34ef216-94c8-413e-a34f-c08b6d78fb0e', 'AO', '1e64dcdc-1882-477a-98b3-3d1681fbb127')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9dd0dad9-0fa6-46ba-928b-bedefaeb5204', 'kuali.attribute.final.exam.use.matrix', 'b34ef216-94c8-413e-a34f-c08b6d78fb0e', 1, 'edf03dd9-18f3-4741-97c6-ac7b2a08d6c9')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('ae175c6f-cb5f-4928-86cc-0f1edcb3546d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'CHEM399A', 'CHEM', '', 'Introduction to Chemical Research', 'b34ef216-94c8-413e-a34f-c08b6d78fb0e', '', '', 'kuali.lui.identifier.state.active', 'A', 'kuali.lui.identifier.type.official', '', '03f98c59-563d-40ef-b46c-57c258f8b6b6')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('fd951b4a-28f0-4080-b24c-9125c78c72dc', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'b34ef216-94c8-413e-a34f-c08b6d78fb0e', '', 'kuali.lu.code.honorsOffering', '', 'bec542b8-3178-4cb9-9513-fe6e7292c081')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('b34ef216-94c8-413e-a34f-c08b6d78fb0e', '4284516367', 'fdb23fd1-c1d2-42b1-b3c2-c391bb621d66')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('b34ef216-94c8-413e-a34f-c08b6d78fb0e', '4284516367')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('b34ef216-94c8-413e-a34f-c08b6d78fb0e', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('b34ef216-94c8-413e-a34f-c08b6d78fb0e', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('b34ef216-94c8-413e-a34f-c08b6d78fb0e', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('b34ef216-94c8-413e-a34f-c08b6d78fb0e', 'kuali.creditType.credit.degree.1.0,1.5,2.0,2.5,3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('5a2c77e3-f679-44d2-9ae9-d34a9175592b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '0ae12d77-775c-499c-ade2-a4e2b60984a9', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '66b844f3-c8d3-4f3b-a83d-6e6117a2acfc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1f3f8a58-aa58-4814-98ad-0b07e1cc0d7c', 'regCodePrefix', '66b844f3-c8d3-4f3b-a83d-6e6117a2acfc', '1', '2eed1248-a5d4-40cd-b824-59faa00f8a6f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('26f29635-d42b-406f-9b89-2f7e99846750', 'kuali.attribute.grade.roster.level.type.key', '66b844f3-c8d3-4f3b-a83d-6e6117a2acfc', 'kuali.lui.type.activity.offering.lecture', 'ebb6ba64-fc9d-41bd-9e5d-9a54ecf5eb0e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('69b26f7b-0aac-4be7-bcde-367f13a3543c', 'kuali.attribute.final.exam.level.type', '66b844f3-c8d3-4f3b-a83d-6e6117a2acfc', 'kuali.lu.type.activity.Lecture', 'da0ae7fd-58d4-41a4-84a0-3aa6ce95a056')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('af6f2e1c-a53b-4a9f-9d4d-c79652e308a7', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '66b844f3-c8d3-4f3b-a83d-6e6117a2acfc', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '3ad0bab2-0497-456e-a82b-b9b1276419ba')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('66b844f3-c8d3-4f3b-a83d-6e6117a2acfc', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('010f7f70-111b-47e5-98ab-3fd02f743928', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:26.09', '', 'CHEM399A-CO-FO', 'b34ef216-94c8-413e-a34f-c08b6d78fb0e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'CHEM399A-CO-FO', '66b844f3-c8d3-4f3b-a83d-6e6117a2acfc', '68071439-3773-4312-a9f4-408179f04778')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('56610d9b-e0f5-45d3-9bbc-3030ad6d6911', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'adb8910d-8560-46b5-8306-540e4d3cf0a8', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 5, 5, 'Lecture', 'Courses with lecture', null, 'dd057f3a-9970-4f0c-9b07-f056ad931a5d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0fb8dd0e-190e-4a2f-8c8f-71ca8c0b6874', 'kuali.attribute.nonstd.ts.indicator', 'dd057f3a-9970-4f0c-9b07-f056ad931a5d', 0, '55407c36-694d-4dc2-ad5e-771ba9767d95')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fc149a36-cea0-4d33-9573-1e5496e371e9', 'kuali.attribute.max.enrollment.is.estimate', 'dd057f3a-9970-4f0c-9b07-f056ad931a5d', 0, '0748a696-2c63-4b9f-97a4-01adb1e8fa67')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('71ab97c6-fb8a-471e-97e4-cb018415f314', 'kuali.attribute.course.evaluation.indicator', 'dd057f3a-9970-4f0c-9b07-f056ad931a5d', 0, '7dd891b0-74f8-45dc-8cd7-4628b50d1298')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('37671440-e327-473b-9886-3e1f778f6427', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', 'dd057f3a-9970-4f0c-9b07-f056ad931a5d', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '8e5f9b9f-bdd3-492a-99b1-abc682e73490')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('082589a2-8908-4b01-9d67-d385ca59c7f7', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'dd057f3a-9970-4f0c-9b07-f056ad931a5d', '', 'kuali.lu.code.honorsOffering', 0, 'b91294fd-65f7-4e7b-93b3-8220c339c383')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('437ad0c1-8ec6-4021-bf0c-fa40462aac69', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:26.224', '', '', 'dd057f3a-9970-4f0c-9b07-f056ad931a5d', '', 'J.PATRICIAT', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '498c3b88-e577-4453-b454-fd4ae216b146')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('c2d45ed5-e29c-484f-9b11-cf6f923f681f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:26.275', '', 'CHEM399A-FO-AO', '66b844f3-c8d3-4f3b-a83d-6e6117a2acfc', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM399A-FO-AO', 'dd057f3a-9970-4f0c-9b07-f056ad931a5d', 'c1c1a7e3-f664-451d-964e-1ed8d72db0cd')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('6532c37b-770a-4422-85f5-933fba134f56', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for CHEM399A - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '6f2f9e2e-3eb3-4d25-b14d-8da2ccd331c7')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('88ea5911-737a-4564-8ea0-7f06df80ee1b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for CHEM399A - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '6f2f9e2e-3eb3-4d25-b14d-8da2ccd331c7', 'afabec81-7c07-415b-8071-4293d9d95cf8')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('93b0ac2d-6027-46ae-956a-141ebc1b3e3c', 1, 'afabec81-7c07-415b-8071-4293d9d95cf8', '3022a18b-8dac-4dc1-86c3-fe7d97a475c5')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('6f2f9e2e-3eb3-4d25-b14d-8da2ccd331c7', 'dd057f3a-9970-4f0c-9b07-f056ad931a5d')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('3022a18b-8dac-4dc1-86c3-fe7d97a475c5', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('cd2b8f5d-e4b0-4b8c-9026-a5d85541e21c', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'ed66ba41-f66e-4983-9898-0d599730da49')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('6d4ca42f-3b5c-494f-9fde-5e1cc498ab57', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '8f9569c8-4e3a-41b5-85d9-e2e424fb6cb7')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='cd2b8f5d-e4b0-4b8c-9026-a5d85541e21c', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='dd057f3a-9970-4f0c-9b07-f056ad931a5d', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='ed66ba41-f66e-4983-9898-0d599730da49' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('8f9569c8-4e3a-41b5-85d9-e2e424fb6cb7', 'dd057f3a-9970-4f0c-9b07-f056ad931a5d')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('8f9569c8-4e3a-41b5-85d9-e2e424fb6cb7', '66b844f3-c8d3-4f3b-a83d-6e6117a2acfc')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('ed088e27-5aef-4392-8fb7-d6885c5cac87', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '66b844f3-c8d3-4f3b-a83d-6e6117a2acfc', 'CL 1', 'CL 1', '64ef7465-ccb5-4556-a5fd-c8a568292d11')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('36408dee-4b5b-456a-8763-00a6d1af0fda', 'kuali.lui.type.activity.offering.lecture', '64ef7465-ccb5-4556-a5fd-c8a568292d11', '47ab3eb4-9425-4199-b6c5-3edf75baf1dc')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('47ab3eb4-9425-4199-b6c5-3edf75baf1dc', 'dd057f3a-9970-4f0c-9b07-f056ad931a5d')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='64ef7465-ccb5-4556-a5fd-c8a568292d11' where ID='47ab3eb4-9425-4199-b6c5-3edf75baf1dc'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('9b45acf6-16c9-4e72-bccf-3e4cba77cacb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '0ae12d77-775c-499c-ade2-a4e2b60984a9', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'd0a76241-70c3-4703-83a7-5de4c169a552')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cf0f5139-f535-4921-a32f-e83cb5a77f76', 'kuali.attribute.registration.group.aocluster.id', 'd0a76241-70c3-4703-83a7-5de4c169a552', '64ef7465-ccb5-4556-a5fd-c8a568292d11', 'a88cbd1d-d20a-493b-b03f-97bcde5aeae9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9db613df-2490-4455-8752-ad9c6db6c55f', 'kuali.attribute.registration.group.is.generated', 'd0a76241-70c3-4703-83a7-5de4c169a552', 1, 'bf2d5600-571b-474b-96af-fec21639f1d0')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('0ec2ec53-ef85-4781-971f-5fc1e9ef1c54', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'd0a76241-70c3-4703-83a7-5de4c169a552', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '66573403-95fc-48b4-b8a1-a83ed9815a69')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ae0aeee5-3860-4fc3-b5e2-c3ef118ea366', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:26.687', '', 'CHEM399A-FO-RG', '66b844f3-c8d3-4f3b-a83d-6e6117a2acfc', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM399A-FO-RG', 'd0a76241-70c3-4703-83a7-5de4c169a552', 'f9ee445c-0e6e-4546-8f59-4957500cf0bd')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ba05c8b4-aa17-439c-a139-807fa10de56d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:26.703', '', 'CHEM399A-RG-AO', 'd0a76241-70c3-4703-83a7-5de4c169a552', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM399A-RG-AO', 'dd057f3a-9970-4f0c-9b07-f056ad931a5d', '2cd042af-c9e7-4bf4-b24f-0c37f36d4a6a')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ba30fa92-849b-4bd2-afd8-8369a551dd83', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'CLUID-HIST219-198001000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST219K CO', '.', null, 'cb0d25d8-088e-4de2-9bbc-ec1a8e01b978')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ed15b7ba-908a-4b3b-b249-8ca082306396', 'kuali.attribute.course.evaluation.indicator', 'cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', 0, '4abed256-895b-4297-9f95-f953d2d7dffd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('723cf992-dd44-479e-885d-b589585fb7fa', 'kuali.attribute.final.exam.use.matrix', 'cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', 1, 'd979e776-7249-4a76-80ff-a46af15c0b54')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ad854ab8-788d-46ab-9734-36d60b52caf2', 'kuali.attribute.wait.list.type.key', 'cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', null, '65019d86-9b8b-48cb-8548-08e639c57bb7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a240aabf-752b-47e0-ac77-f84d1c219b98', 'kuali.attribute.where.fees.attached.flag', 'cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', 0, '67fc53fd-d617-4b08-a59f-d26fcc61280f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b06e13b5-5938-49ac-88fa-16ba04a0c2d1', 'kuali.attribute.final.exam.indicator', 'cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', 'STANDARD', 'c7948a4d-6901-469d-acfd-bbe083f15a71')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c87ad53d-859e-4ce3-9248-a295e5941272', 'kuali.attribute.wait.list.indicator', 'cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', 1, 'a54ae529-abc5-4225-9cbc-f24b3ea6950d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0136af8d-0c74-45b2-8e31-9474ddebbb3a', 'kuali.attribute.final.exam.driver', 'cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', 'kuali.lu.exam.driver.ActivityOffering', '1564888a-6174-4c8a-b5db-bd0a11ea2244')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('917e67da-661d-492f-93f2-3ed22b8edd59', 'kuali.attribute.wait.list.level.type.key', 'cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', 'AO', 'cbb1a189-ea1c-41de-a0c8-caf97bc0cae6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8688bd67-b865-4846-b39e-75daef3affd2', 'kuali.attribute.course.number.internal.suffix', 'cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', 'A', '57f8b466-b66b-4236-962c-a607aafe5577')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('281033be-7c36-45fe-b552-fb079c1f6edb', 'kuali.attribute.funding.source', 'cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', '', 'b21d987f-d3a9-41fa-999a-2598182e3038')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('148d3ea5-691e-43a6-8464-465b5c6dd1dd', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'HIST219K', 'HIST', '', 'Special Topics in History', 'cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', '', '', 'kuali.lui.identifier.state.active', 'K', 'kuali.lui.identifier.type.official', '', '5d0fb2e5-5423-415a-b718-7042daec11cc')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('5f12d5ca-5a49-4749-8fd1-77dccd7c1fb8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', '', 'kuali.lu.code.honorsOffering', '', 'b8204af4-519d-44c7-bfcb-835921ed6132')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', '3213375036', '54aef358-4912-4050-8102-5f09c2a408bf')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('bb559b27-6aa0-4f75-b9e9-8619fff5e827', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '0d8fb0a0-2053-4b0a-a7b8-354463deb594', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '199ee9f5-b02a-48dd-9dce-31c89f2ac907')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('70ad27ef-c60e-4b52-94e0-498ba06cef82', 'regCodePrefix', '199ee9f5-b02a-48dd-9dce-31c89f2ac907', '1', '4dffe6d5-ca04-4ae7-ab42-5516fa001148')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('df6005ae-f817-489b-8e1f-82f892c8689f', 'kuali.attribute.final.exam.level.type', '199ee9f5-b02a-48dd-9dce-31c89f2ac907', 'kuali.lu.type.activity.Lecture', '92cd101c-e34d-4953-b299-7ca10778f35d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bfef8f78-a869-4297-a56c-844522531828', 'kuali.attribute.grade.roster.level.type.key', '199ee9f5-b02a-48dd-9dce-31c89f2ac907', 'kuali.lui.type.activity.offering.lecture', 'b43ad4af-e15b-4ac8-8c9b-fd78b46be43a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('4bfa7d87-538f-46d0-88a2-fc09fd3e7203', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '199ee9f5-b02a-48dd-9dce-31c89f2ac907', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'e84ce39a-52c6-464e-9df2-2632dc0eadb0')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('199ee9f5-b02a-48dd-9dce-31c89f2ac907', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('c4975059-2a48-4df0-a0ea-229b1ff2e59b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:29.028', '', 'HIST219K-CO-FO', 'cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST219K-CO-FO', '199ee9f5-b02a-48dd-9dce-31c89f2ac907', 'b709cd0d-b267-44e1-8417-39b997f00766')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d35ece2c-ebd7-48aa-9fde-b1067c4e7bab', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '315f56ce-d69d-44a2-a745-5a611e4ac066', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 1, '', 'Lecture', 'Courses with lecture', null, '68290f16-a64f-4f71-a256-28734a86fbce')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4adde3cb-e26f-4abe-a299-ea9c5cacd2e1', 'kuali.attribute.nonstd.ts.indicator', '68290f16-a64f-4f71-a256-28734a86fbce', 0, '7cda3e37-7c41-4654-b16c-3823a7e386f0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f50634ed-3dac-4297-90f9-0e7c2616afa8', 'kuali.attribute.max.enrollment.is.estimate', '68290f16-a64f-4f71-a256-28734a86fbce', 0, 'b15f6fc7-32d3-482f-9281-e6cef65b660a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1b0f9ab0-3e48-4b66-97fe-234a08e8a312', 'kuali.attribute.course.evaluation.indicator', '68290f16-a64f-4f71-a256-28734a86fbce', 0, 'c62c6f13-e2f7-4256-943b-50747a4d5813')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('f42b9c55-a52e-4024-bfef-308d0439a949', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '68290f16-a64f-4f71-a256-28734a86fbce', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '4e8cbade-1749-4427-8cdd-db08c4626a68')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('302f54b4-2968-41e9-b46d-829cc6f29f15', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '68290f16-a64f-4f71-a256-28734a86fbce', '', 'kuali.lu.code.honorsOffering', 0, 'cfc9597c-30ee-4a7a-874e-e997c01cd089')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('1aa858b4-7c27-40af-8021-03aabd01f37b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:29.114', '', '', '68290f16-a64f-4f71-a256-28734a86fbce', '', 'P.MARYH', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '08d0d7cf-708b-42b7-961d-a5b52c5e2cb0')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('76cd62d6-c083-47be-89f2-a9b5ebf64a2b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:29.147', '', 'HIST219K-FO-AO', '199ee9f5-b02a-48dd-9dce-31c89f2ac907', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST219K-FO-AO', '68290f16-a64f-4f71-a256-28734a86fbce', 'fdbf9f78-422f-4710-8584-1a4df8eb1564')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('8942c1dc-1305-4690-876e-8207a0a9d25f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for HIST219K - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '4af86f68-2abe-4050-9276-7bcf80c6348d')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('58d4400e-1a96-411d-9e62-c3ddd5c4bd04', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for HIST219K - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '4af86f68-2abe-4050-9276-7bcf80c6348d', '547f2ea1-0f63-4758-871a-572b0a98ef5c')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('179a742e-74c8-4a70-8d66-95fa98f6601f', 1, '547f2ea1-0f63-4758-871a-572b0a98ef5c', 'b0d92a34-3fcd-4159-ab40-c21489ce6d6a')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('4af86f68-2abe-4050-9276-7bcf80c6348d', '68290f16-a64f-4f71-a256-28734a86fbce')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('b0d92a34-3fcd-4159-ab40-c21489ce6d6a', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('3f117f2a-717c-4f58-a139-361d67aa54da', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '99bdb5c2-233a-46be-af84-586b3e7637b4')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('99bdb5c2-233a-46be-af84-586b3e7637b4', '68290f16-a64f-4f71-a256-28734a86fbce')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('99bdb5c2-233a-46be-af84-586b3e7637b4', '199ee9f5-b02a-48dd-9dce-31c89f2ac907')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('de071f35-7314-4018-8097-194362f21a6b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '199ee9f5-b02a-48dd-9dce-31c89f2ac907', 'CL 1', 'CL 1', 'f73e1cc9-4903-4235-b7a7-9eadd0aa7d8f')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('0743d70c-4b96-4dcb-85fa-2dff20e02096', 'kuali.lui.type.activity.offering.lecture', 'f73e1cc9-4903-4235-b7a7-9eadd0aa7d8f', '57c29b10-eee5-42dc-a78d-c7c573e08b61')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('57c29b10-eee5-42dc-a78d-c7c573e08b61', '68290f16-a64f-4f71-a256-28734a86fbce')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='f73e1cc9-4903-4235-b7a7-9eadd0aa7d8f' where ID='57c29b10-eee5-42dc-a78d-c7c573e08b61'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('e0b6db30-ef9b-42d2-9cef-b25fe38310f1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '0d8fb0a0-2053-4b0a-a7b8-354463deb594', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '31aab55c-0996-4b2a-b0da-3e7ce1c7d9c5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bac4e942-d46c-4f85-af5f-bf0f72f316fe', 'kuali.attribute.registration.group.is.generated', '31aab55c-0996-4b2a-b0da-3e7ce1c7d9c5', 1, '0e2bcd9e-8ce4-49ad-aaa6-8b6fb73933a9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('28d7dd1c-48e7-450c-a259-c01954f5b371', 'kuali.attribute.registration.group.aocluster.id', '31aab55c-0996-4b2a-b0da-3e7ce1c7d9c5', 'f73e1cc9-4903-4235-b7a7-9eadd0aa7d8f', '4c025cff-b886-4fb2-bc19-bdb2c0ffb50e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('4719b821-8ecc-4db7-9d65-9e6307a08bc3', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '31aab55c-0996-4b2a-b0da-3e7ce1c7d9c5', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '186382c9-1843-4a18-aef0-0bc75f543a7d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ce0a69cd-3bfc-4dfd-9ae5-994e8fd3f3e3', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:29.528', '', 'HIST219K-FO-RG', '199ee9f5-b02a-48dd-9dce-31c89f2ac907', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST219K-FO-RG', '31aab55c-0996-4b2a-b0da-3e7ce1c7d9c5', 'b9c44ea1-2187-442b-a602-9473cb4ec7a2')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('00372204-16b4-43e2-b85d-1332e1eb9cf4', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:29.538', '', 'HIST219K-RG-AO', '31aab55c-0996-4b2a-b0da-3e7ce1c7d9c5', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST219K-RG-AO', '68290f16-a64f-4f71-a256-28734a86fbce', 'b9d79271-cc24-43da-a8a5-807380bbad8d')
/
update KSEN_SOC_ROR set OBJ_ID='971d59cc-eb7c-4504-ac92-ec51f651c326', VER_NBR=5, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ITEMS_EXPECTED=48, ITEMS_PROCESSED=30, MESG_FORMATTED='', MESG_PLAIN='', SOC_ROR_STATE='kuali.soc.rollover.state.running', SOC_ROR_TYPE='kuali.soc.rollover.results.type.rollover', SOURCE_SOC_ID='03192a24-765a-4b9d-b638-5158430e09c8', TARGET_SOC_ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', TARGET_TERM_ID='kuali.atp.2014Winter' where ID='5b88a612-d027-429f-a9f3-b4241ed736c7' and VER_NBR=4
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='e2dae705-59ea-4cea-a1b8-97963890ed4e', ATTR_KEY='kuali.soc.rollover.result.dynattr.date.completed', OWNER_ID='5b88a612-d027-429f-a9f3-b4241ed736c7', ATTR_VALUE='2014 Jan 23 20:02:29 America/New_York' where ID='289b7901-b122-4239-94fd-e4dd1e0066cf'
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('8655b911-ccdf-4c48-92f0-14e9483d9aab', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '78e93646-38b3-4784-8e80-99f705d86b82', '0bf52bd3-83eb-4594-be14-453dbe36510d', '751fd9c1-8e06-42e4-8667-4862873f4195')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fc0ee8b3-deb6-475a-94e8-f4688fcdc9b5', 'activityOfferingsCreated', '751fd9c1-8e06-42e4-8667-4862873f4195', '2', 'ac0793f2-b0d6-4d04-9b6a-d652a2b6d41a')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('8d39cde3-ff3a-4ea5-8881-220d2611db22', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '761a3a32-2eb9-4816-a93b-444bd13003ac', 'db406537-1427-4ef2-a727-7310504960c8', '50386a0c-89dd-4bae-b3db-31b220115cbc')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b96882c5-0726-4032-9db0-c4c5b649b52d', 'activityOfferingsCreated', '50386a0c-89dd-4bae-b3db-31b220115cbc', '1', 'c8561175-85bb-4891-8be9-79c15ba21281')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('1cdf066e-efa2-4599-9163-29315fb57aeb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '89d88bce-f84d-49bb-bc99-e66a73e76010', '2ae395bc-6ede-4070-9379-b939f4711316', 'a0c7d73d-d8b3-4b18-ad22-f29f083aadc4')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0eef06c8-75be-4913-9a14-c281aefebca8', 'activityOfferingsCreated', 'a0c7d73d-d8b3-4b18-ad22-f29f083aadc4', '1', '4f24f57c-8d77-4564-ba3a-d0e97ef59c27')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('117e9ec7-4aa3-47d2-a04c-965e34039a55', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '89e1b4c8-e6df-4090-ae1c-5dee44dda61c', '55cb2921-c9d1-4239-897e-b8637d708890', '9a211afb-c978-42a4-af1c-264b9bd10867')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('799bd244-6828-47c7-8553-845d8e0422ba', 'activityOfferingsCreated', '9a211afb-c978-42a4-af1c-264b9bd10867', '1', 'cf098ec8-20ea-4e4c-8847-b7bd67b1d16b')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('42dd8abf-cc3d-40db-9f8c-b9f0f049a745', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '9172e0c7-853f-44ca-a345-c478416eec71', '94528acf-67ba-4d07-9776-a062ab92690a', '94933f3c-affe-41ee-b5c6-2b4d02738fd9')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('de58a827-754d-4340-959a-3ede800674dc', 'activityOfferingsCreated', '94933f3c-affe-41ee-b5c6-2b4d02738fd9', '1', 'd02ee9a4-e205-4a0a-bf2c-340fb4dd64c2')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('ff628e61-baaf-40d7-974a-1bf5588d282e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '8dc6e8ff-6003-4b81-975c-5e620ab58eb2', '2d5149da-ab75-4478-b946-e78403fa1c50', 'a64a317d-d3c1-4b4f-87b0-b2f9995ced5c')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0524c99e-b8cf-40c7-a70d-915ca18e4ebc', 'activityOfferingsCreated', 'a64a317d-d3c1-4b4f-87b0-b2f9995ced5c', '1', '9e6d7aa1-997c-435e-b66e-b817e6bff085')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('f75a1ca8-52e7-4ae3-98e0-d8431e886cfa', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '983dcf89-3d39-442e-8983-453012736170', '06db6874-69ff-4491-8d93-3703f850af36', '93292ae6-592b-47bf-a244-aca2cc13d37c')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0a774cb2-549c-40f0-84a9-9db4121c1310', 'activityOfferingsCreated', '93292ae6-592b-47bf-a244-aca2cc13d37c', '1', '52f519c5-64e1-4601-8615-11bb473a9a7a')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('0fa33cfa-e25a-41b7-9cf7-7cae3ff1ce87', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '95f644a2-ed1a-4b12-b07e-725000413f9b', 'b34ef216-94c8-413e-a34f-c08b6d78fb0e', 'afeeb99c-4e53-4cd5-96f1-3fa593fc7f8a')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('19cb8a7f-5e0e-4b3d-8d5a-e423a49f89d7', 'activityOfferingsCreated', 'afeeb99c-4e53-4cd5-96f1-3fa593fc7f8a', '1', '59134402-5d2e-4a1e-92e0-18a94ed9ebb8')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('2468424c-d9ae-4c10-b672-3c0cd253f031', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'a0cd7b8f-3da7-404b-809f-9ed9c17501be', 'cb0d25d8-088e-4de2-9bbc-ec1a8e01b978', '2fe98a0a-f956-4fbb-864e-26cc0f872083')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b6192964-93f3-4ad4-99a3-972379073ba9', 'activityOfferingsCreated', '2fe98a0a-f956-4fbb-864e-26cc0f872083', '1', 'bf524202-2b2b-4a3b-b3f6-12aa222e999a')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('a92099b4-2cba-42f9-b23c-e604ae7d294b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', '9bfa392a-e3f2-4a5e-ac8f-2f4f8278a805', '', 'c1b1b10a-823d-46e3-851c-ef867e53a5d9')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('c9f3a3ec-8c48-4192-aed7-a2fd572836df', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'fb83fb33-973f-420d-aa79-252c47bcbf21', TIMESTAMP '2014-01-01 19:00:00.0', '', 'An examination of women''s creative powers as expressed in selected examples of music, film, art, drama, poetry, fiction, and other literature. Explores women''s creativity in relation to families, religion, education, ethnicity, class, sexuality, and within a cultural tradition shaped by women.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'WMST250 CO', 'An examination of women''s creative powers as expressed in selected examples of music, film, art, drama, poetry, fiction, and other literature. Explores women''s creativity in relation to families, religion, education, ethnicity, class, sexuality, and within a cultural tradition shaped by women.', null, 'aafe640f-f2e5-44e8-b887-ce64d92186a2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7265ec2e-fe8e-48b5-af06-923eb9b801ec', 'kuali.attribute.final.exam.use.matrix', 'aafe640f-f2e5-44e8-b887-ce64d92186a2', 1, 'fe9375b7-73fc-49d3-967f-fde5df9b913e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('87cc3232-4be5-438a-9d7c-d5b3f936a0ee', 'kuali.attribute.course.number.internal.suffix', 'aafe640f-f2e5-44e8-b887-ce64d92186a2', 'A', 'd59205cc-9266-4dd9-8c6c-374d19111371')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ec211ed6-78ca-4227-b1b3-dedf482b067b', 'kuali.attribute.final.exam.indicator', 'aafe640f-f2e5-44e8-b887-ce64d92186a2', 'STANDARD', 'beeaf07e-4e85-4c3d-85af-a111146ffc9d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('319ee3b1-e367-407f-a42a-0b30fefff3cf', 'kuali.attribute.wait.list.type.key', 'aafe640f-f2e5-44e8-b887-ce64d92186a2', null, '3276564a-cd00-4729-b225-156d13d4214d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5b6ea46b-af67-4e3f-9baa-fca403c666d1', 'kuali.attribute.final.exam.driver', 'aafe640f-f2e5-44e8-b887-ce64d92186a2', 'kuali.lu.exam.driver.ActivityOffering', '1e81cafd-2499-4680-9f2c-1a04a94dd490')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f925f10b-85d7-4e0c-ad36-21d25852c5c9', 'kuali.attribute.where.fees.attached.flag', 'aafe640f-f2e5-44e8-b887-ce64d92186a2', 0, '9311211c-d2fc-4896-9c8a-d16a5b128949')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('723d026d-2a44-47a4-a0b5-53ec52421a79', 'kuali.attribute.funding.source', 'aafe640f-f2e5-44e8-b887-ce64d92186a2', '', 'c60aeea3-86f5-4359-b0b9-e89cbfd7b0c8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fef6bdd7-d109-4144-b6eb-7c48b1be37da', 'kuali.attribute.wait.list.level.type.key', 'aafe640f-f2e5-44e8-b887-ce64d92186a2', 'AO', '771af54c-c272-4c57-ae45-8d2704abef9a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b9c26a43-167d-4e34-bd17-7d7c1e0ad402', 'kuali.attribute.course.evaluation.indicator', 'aafe640f-f2e5-44e8-b887-ce64d92186a2', 0, '8d6d2b97-2f9f-4046-9bf5-cc9d6195bd58')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6eb8f91a-c0a0-45ae-8953-eb6cd68b7229', 'kuali.attribute.wait.list.indicator', 'aafe640f-f2e5-44e8-b887-ce64d92186a2', 1, '813e3c32-d790-48c6-8e68-c38978015c3f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('3a55abeb-6a4d-4e93-a57a-48fc87db5505', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'WMST250', 'WMST', '', 'Introduction to Women''s Studies:  Women, Art and Culture', 'aafe640f-f2e5-44e8-b887-ce64d92186a2', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '9736b880-9228-4e5c-a74f-7f843a056a03')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('633b1d7a-46cc-4799-9e5c-ca3df70ef153', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'aafe640f-f2e5-44e8-b887-ce64d92186a2', '', 'kuali.lu.code.honorsOffering', '', 'bab41ec6-4b3f-42cc-85ec-e848030e515e')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('aafe640f-f2e5-44e8-b887-ce64d92186a2', '4014660630', '2ae56101-94ee-471d-bd6a-98a846dc4ff0')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('aafe640f-f2e5-44e8-b887-ce64d92186a2', '4014660630')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('aafe640f-f2e5-44e8-b887-ce64d92186a2', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('aafe640f-f2e5-44e8-b887-ce64d92186a2', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('aafe640f-f2e5-44e8-b887-ce64d92186a2', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('aafe640f-f2e5-44e8-b887-ce64d92186a2', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('cee28bc7-57ba-4608-bb70-7e5402e8a49b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '53f70802-c152-40a6-bbc8-51e9c0851622', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '1bb27f8c-6aa4-4a89-b2fe-b88b4249ecaa')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('38817c25-10ce-4578-ac13-e8260d33d536', 'regCodePrefix', '1bb27f8c-6aa4-4a89-b2fe-b88b4249ecaa', '1', 'e2fdb8cb-0f2c-42f0-ab7e-f0d71443adb7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('69f3d2ba-5f22-43c5-898a-cb098d683d8c', 'kuali.attribute.grade.roster.level.type.key', '1bb27f8c-6aa4-4a89-b2fe-b88b4249ecaa', 'kuali.lui.type.activity.offering.lecture', 'b8713702-7710-4d46-85d6-7a569a2bd9de')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4c532ed4-227f-47b1-b9cd-8e2f6207fd14', 'kuali.attribute.final.exam.level.type', '1bb27f8c-6aa4-4a89-b2fe-b88b4249ecaa', 'kuali.lu.type.activity.Lecture', '5164bb78-a793-4449-b22f-48514ba5ac63')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('47503273-9db8-4b41-94db-fea46eccd09c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '1bb27f8c-6aa4-4a89-b2fe-b88b4249ecaa', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c0a2ca3f-97d5-466d-bde7-3f452eb458bf')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('1bb27f8c-6aa4-4a89-b2fe-b88b4249ecaa', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('8cb62098-21f4-4145-a42c-6e0320161cab', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:34.225', '', 'WMST250-CO-FO', 'aafe640f-f2e5-44e8-b887-ce64d92186a2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'WMST250-CO-FO', '1bb27f8c-6aa4-4a89-b2fe-b88b4249ecaa', '98e4a2ca-ec47-48f1-90dc-ea1d240de6c6')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('89d3062f-a03c-4aa2-a793-44b943af69f6', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'ef9d80d1-2429-49b7-af3c-ecadab6eb60f', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, '4ea2be59-83fc-4dc9-b28e-d4e9658805da')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7ce5f335-66cb-4b14-a89c-0c344d707fb0', 'kuali.attribute.max.enrollment.is.estimate', '4ea2be59-83fc-4dc9-b28e-d4e9658805da', 0, 'b402af10-d4b9-4f63-87c5-8cf496ca7c1f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6ede29bf-a9a4-442f-906f-43a7cf818ee4', 'kuali.attribute.nonstd.ts.indicator', '4ea2be59-83fc-4dc9-b28e-d4e9658805da', 0, 'f98ce720-ab61-4f07-979c-4052b4068a68')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d32ff9ab-ed18-455f-931b-1eaa41828132', 'kuali.attribute.course.evaluation.indicator', '4ea2be59-83fc-4dc9-b28e-d4e9658805da', 0, '18bed590-4059-4139-9f3f-e7e003cd488f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('84fd0488-266d-4ec6-b611-86a0692d302f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '4ea2be59-83fc-4dc9-b28e-d4e9658805da', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'eb93d488-186f-4547-af7c-b8ce0723343d')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('70e64a3f-8a74-4bbb-9535-75ca07c8191e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '4ea2be59-83fc-4dc9-b28e-d4e9658805da', '', 'kuali.lu.code.honorsOffering', 0, '1f71a6be-7a3f-42e5-bf9f-b9dc36673bf0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('f14f22b8-cca0-49b4-8f85-cdbd4839a88f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:34.333', '', '', '4ea2be59-83fc-4dc9-b28e-d4e9658805da', '', 'C.SUSANB', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'e179c55b-9809-4b16-add2-cc7ac56bf00e')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('c440d7f1-f93d-4415-bfd1-129ac9aa810f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:34.382', '', 'WMST250-FO-AO', '1bb27f8c-6aa4-4a89-b2fe-b88b4249ecaa', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'WMST250-FO-AO', '4ea2be59-83fc-4dc9-b28e-d4e9658805da', '79612eda-1f4f-4ccf-8108-6660921ff835')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('81d11829-c92a-4bd5-b27d-8b289d96a332', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for WMST250 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '44b8178d-552a-4577-8ebc-e19517603a9b')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('9edefbf0-8052-4fc9-bfc4-0728a50462dc', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for WMST250 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '44b8178d-552a-4577-8ebc-e19517603a9b', '90390685-96f3-4e42-8790-bc0def2abf0a')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('6955fc9d-835c-415e-8772-8686316f3a95', 0, '90390685-96f3-4e42-8790-bc0def2abf0a', 'f8c99394-f325-468e-bb48-22fac118cfca')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('44b8178d-552a-4577-8ebc-e19517603a9b', '4ea2be59-83fc-4dc9-b28e-d4e9658805da')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('f8c99394-f325-468e-bb48-22fac118cfca', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('f8c99394-f325-468e-bb48-22fac118cfca', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('f8c99394-f325-468e-bb48-22fac118cfca', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('f8c99394-f325-468e-bb48-22fac118cfca', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('3d031d77-caed-401e-b21f-b0c0b2d04c58', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '9ec01823-8d50-4233-be2e-8151112d55c2')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('9ec01823-8d50-4233-be2e-8151112d55c2', '4ea2be59-83fc-4dc9-b28e-d4e9658805da')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('9ec01823-8d50-4233-be2e-8151112d55c2', '1bb27f8c-6aa4-4a89-b2fe-b88b4249ecaa')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('8a4a02af-8b3d-48d2-83b8-a24fca85e893', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '1bb27f8c-6aa4-4a89-b2fe-b88b4249ecaa', 'CL 1', 'CL 1', 'df64140b-7fd7-4a22-a591-f8f85bb5e087')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('572bb70a-d1f1-4036-b14c-13b7e7d8717b', 'kuali.lui.type.activity.offering.lecture', 'df64140b-7fd7-4a22-a591-f8f85bb5e087', '3ad70360-db0d-43ae-9bcc-344f8a416ddb')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('3ad70360-db0d-43ae-9bcc-344f8a416ddb', '4ea2be59-83fc-4dc9-b28e-d4e9658805da')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='df64140b-7fd7-4a22-a591-f8f85bb5e087' where ID='3ad70360-db0d-43ae-9bcc-344f8a416ddb'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('b59c1371-f1bf-4604-a314-a0da8f587510', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '53f70802-c152-40a6-bbc8-51e9c0851622', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '39c1caf9-ce68-4763-8a6c-151d2f45d6f8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2f306e96-907a-4cf2-beef-100823e84979', 'kuali.attribute.registration.group.aocluster.id', '39c1caf9-ce68-4763-8a6c-151d2f45d6f8', 'df64140b-7fd7-4a22-a591-f8f85bb5e087', 'ed214a6f-0e8a-432c-874f-c8cda074dfdf')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7c138057-5791-408b-9a3d-3a9c93025a63', 'kuali.attribute.registration.group.is.generated', '39c1caf9-ce68-4763-8a6c-151d2f45d6f8', 1, '6f9600f6-8d6a-48ff-9ee8-9c5b05c84f4f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('590d7bf9-3991-4f78-8bd5-832ec4c10924', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '39c1caf9-ce68-4763-8a6c-151d2f45d6f8', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'f9f85004-c2f9-4433-afd3-6d1fe51b9eec')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('4f4adc95-f3e0-4d35-85d7-21bb4ad62140', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:34.948', '', 'WMST250-FO-RG', '1bb27f8c-6aa4-4a89-b2fe-b88b4249ecaa', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'WMST250-FO-RG', '39c1caf9-ce68-4763-8a6c-151d2f45d6f8', '9a79e408-3879-4e10-b012-3ef40f76760a')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a0ccff44-39d7-49bd-a304-1378d069e286', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:34.962', '', 'WMST250-RG-AO', '39c1caf9-ce68-4763-8a6c-151d2f45d6f8', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'WMST250-RG-AO', '4ea2be59-83fc-4dc9-b28e-d4e9658805da', 'b82d8004-538b-4baf-ab9d-7700dab076aa')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('0e71647f-b1ac-4177-8024-18eac110d2b8', '9f766dd0-8c6f-4adc-b113-9ac62a7204b9', 'courseOfferingCode', 'HIST319D-P', '4c92036e-ae60-44c7-9f48-fda1275d0818')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('47e890de-30e4-44a2-b563-a67ae8ac4254', 'A', 'courseOfferingCode', 'HIST319D', 'acb5291e-5f34-40f0-ac8e-4698ec6edba5')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='9f766dd0-8c6f-4adc-b113-9ac62a7204b9' and UNIQUE_KEY='HIST319D-P' and NAMESPACE='courseOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='HIST319D' and NAMESPACE='courseOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='HIST319D-P' and codegenera1_.NAMESPACE='courseOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('7ac5269f-681f-411f-acaf-ed9788e86e6c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'CLUID-HIST319-198001000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST319O CO', '.', null, '51834b55-898a-4654-b4f1-66594453157f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('19c1fe60-500b-4d97-9f9e-fd6b46521dcd', 'kuali.attribute.final.exam.driver', '51834b55-898a-4654-b4f1-66594453157f', 'kuali.lu.exam.driver.ActivityOffering', 'a3de5517-ecd2-4e97-a59f-6433dc87aa24')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ad81a559-a65a-4058-ba7b-36ca1d1c5232', 'kuali.attribute.where.fees.attached.flag', '51834b55-898a-4654-b4f1-66594453157f', 0, 'e2b901f2-4f56-4c2b-92a9-36f6f43901a8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9839d9f1-af67-45ed-a48f-9cb771ccd6d1', 'kuali.attribute.final.exam.use.matrix', '51834b55-898a-4654-b4f1-66594453157f', 1, 'd733470e-fe43-44b1-a332-2dc9a4a41622')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('61b3f28e-d9bd-4c09-8b97-dc8de3916647', 'kuali.attribute.final.exam.indicator', '51834b55-898a-4654-b4f1-66594453157f', 'STANDARD', 'b502fbd2-66a1-481d-ba89-015aae87470f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ba5d0b2a-e039-418c-87e5-333e9af98338', 'kuali.attribute.wait.list.level.type.key', '51834b55-898a-4654-b4f1-66594453157f', 'AO', '5557d876-1adf-4019-a2f5-a49de1c07e15')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a3d10b0c-e9c3-4b4a-886f-b07ae42905d2', 'kuali.attribute.wait.list.type.key', '51834b55-898a-4654-b4f1-66594453157f', null, 'ba61f5dc-debe-4966-a4ae-25db86d415ec')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e0e36644-8443-4edf-ad87-00d877f62991', 'kuali.attribute.wait.list.indicator', '51834b55-898a-4654-b4f1-66594453157f', 1, '5f6db6e2-e265-483a-bac2-e75b28a9d373')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8fbec170-80ca-43bf-8193-9836fd484768', 'kuali.attribute.funding.source', '51834b55-898a-4654-b4f1-66594453157f', '', 'cbbe4305-b397-422a-a61a-9c022de3ce0b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3e8c86eb-9c45-4d05-a257-d550c8a5aa74', 'kuali.attribute.course.number.internal.suffix', '51834b55-898a-4654-b4f1-66594453157f', 'A', '123c9aab-7f6f-49e9-a0d0-243d8c41bee2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7703c96e-0526-49a6-ac78-2d7391943034', 'kuali.attribute.course.evaluation.indicator', '51834b55-898a-4654-b4f1-66594453157f', 0, 'e28c9ebb-12c3-4257-afb6-cafbd425765d')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('ca9adb26-e2d3-476a-8481-8442423922eb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'HIST319O', 'HIST', '', 'Special Topics in History', '51834b55-898a-4654-b4f1-66594453157f', '', '', 'kuali.lui.identifier.state.active', 'O', 'kuali.lui.identifier.type.official', '', '2c58cbef-da51-4c7c-923c-e70333650980')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('9e26e70b-ce83-4da1-8c5c-5168fbe11b6a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '51834b55-898a-4654-b4f1-66594453157f', '', 'kuali.lu.code.honorsOffering', '', 'b0728a3d-3d9f-4ffa-a86f-da1c54787120')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('51834b55-898a-4654-b4f1-66594453157f', '3213375036', '4fe69946-7014-492a-9ecd-f9fd605896db')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('51834b55-898a-4654-b4f1-66594453157f', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('51834b55-898a-4654-b4f1-66594453157f', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('51834b55-898a-4654-b4f1-66594453157f', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('51834b55-898a-4654-b4f1-66594453157f', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('51834b55-898a-4654-b4f1-66594453157f', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('c673f6f4-4665-4f84-8955-243001a51cc1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'ccb3c17e-6eaf-4095-85b7-648120717da9', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '18aabc85-5a48-496c-9cda-3a008cab88fc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('919ada7d-c255-4757-aedb-f1634c86d12b', 'regCodePrefix', '18aabc85-5a48-496c-9cda-3a008cab88fc', '1', '3aa26145-2986-465c-bc85-92e3b907b0c7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ce37022d-90f5-4ec6-8063-a2d750f9e115', 'kuali.attribute.grade.roster.level.type.key', '18aabc85-5a48-496c-9cda-3a008cab88fc', 'kuali.lui.type.activity.offering.lecture', '33c946a9-b96f-4714-b9a0-e449d8c42a8f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a1b147c6-9f1b-4564-a53b-c6810bf6aa63', 'kuali.attribute.final.exam.level.type', '18aabc85-5a48-496c-9cda-3a008cab88fc', 'kuali.lu.type.activity.Lecture', '7c8c727e-d0d9-4a41-801f-20c9b5d6634e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('54acc863-1399-49e3-806a-27d7f82097ff', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '18aabc85-5a48-496c-9cda-3a008cab88fc', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '5e0d97c2-c95b-42e5-9967-927ae4655d46')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('18aabc85-5a48-496c-9cda-3a008cab88fc', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('5ee69b70-9b27-481f-b2a6-c175fd9af7cd', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:37.12', '', 'HIST319O-CO-FO', '51834b55-898a-4654-b4f1-66594453157f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST319O-CO-FO', '18aabc85-5a48-496c-9cda-3a008cab88fc', 'bd31a579-de1e-4549-b35a-e05ee8df1335')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('25571491-c3ff-457d-89b8-affc890de4d2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '55fda0fc-cb22-4b3b-a7e0-5a0ceeaaf89d', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, 'fc7ded21-5f5a-49cb-abcf-ec6f15fe6246')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6fef7cd5-b030-4f9e-ad02-b45b64a42f23', 'kuali.attribute.max.enrollment.is.estimate', 'fc7ded21-5f5a-49cb-abcf-ec6f15fe6246', 0, '94c45585-78fc-4246-a60b-e9d668150afb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('937aecb3-51a4-493e-9ce5-1ad9f1bd7bdc', 'kuali.attribute.course.evaluation.indicator', 'fc7ded21-5f5a-49cb-abcf-ec6f15fe6246', 0, '25c89b01-a69c-4913-815e-ed7686a01621')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d558dfb4-cf83-4b50-9814-e401ef4ea362', 'kuali.attribute.nonstd.ts.indicator', 'fc7ded21-5f5a-49cb-abcf-ec6f15fe6246', 0, '2a72c345-521b-4e81-a512-5fcecdb773a0')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('07d78f8b-5e89-4ab2-8fba-347f31f92a40', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', 'fc7ded21-5f5a-49cb-abcf-ec6f15fe6246', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '79fabd8c-099d-4196-ae39-6aad980ef812')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('7bd0b410-f527-41a3-92c0-6154ae107ea6', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'fc7ded21-5f5a-49cb-abcf-ec6f15fe6246', '', 'kuali.lu.code.honorsOffering', 0, '7b22c153-b201-4e5f-9be0-01c36b9e7711')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('b31806c8-27d3-42bc-87bf-4e2e6c73b552', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:37.28', '', '', 'fc7ded21-5f5a-49cb-abcf-ec6f15fe6246', '', 'S.JOSEPHE', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '49054d2b-a2c1-408a-ba20-0ec32f1568bc')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e6196abc-ab0b-48f4-aa0f-a94ea43252dc', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:37.305', '', 'HIST319O-FO-AO', '18aabc85-5a48-496c-9cda-3a008cab88fc', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST319O-FO-AO', 'fc7ded21-5f5a-49cb-abcf-ec6f15fe6246', 'b8ec356c-e83a-4dd1-aff7-08623338ab95')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('81c286c7-4c29-4703-a084-e25fa49d8cf0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for HIST319O - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '0dd38761-8c02-43fd-ae31-6f4ba0986f87')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('4998ca1a-86b0-49d7-b973-6bed7ec8d792', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for HIST319O - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '0dd38761-8c02-43fd-ae31-6f4ba0986f87', 'cdcebfe0-0b5e-4732-ab6e-44cc8312db7c')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('40a9ea0d-255e-4ddd-8760-93fd9714c22d', 0, 'cdcebfe0-0b5e-4732-ab6e-44cc8312db7c', '87f4210f-2b6d-4b0d-9b3f-69fe2c060fbe')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('0dd38761-8c02-43fd-ae31-6f4ba0986f87', 'fc7ded21-5f5a-49cb-abcf-ec6f15fe6246')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('87f4210f-2b6d-4b0d-9b3f-69fe2c060fbe', 'af22fa59-42ab-4d09-9a5b-6980f8b4ae7d')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('87f4210f-2b6d-4b0d-9b3f-69fe2c060fbe', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('87f4210f-2b6d-4b0d-9b3f-69fe2c060fbe', 'fdd1e716-7c99-4cc9-afbe-d8fa2e44ec66')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('87f4210f-2b6d-4b0d-9b3f-69fe2c060fbe', 'F0AA72D6DB31F650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('3e561399-8489-4969-b016-30ef746d5be7', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '3d44baa9-ae5b-4055-bf9a-61cc6863604d')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('7c15705c-8cea-4382-8543-d1fcd23fc5a1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'ff690d7a-f45c-49cb-ae89-e838730b7886')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='3e561399-8489-4969-b016-30ef746d5be7', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='fc7ded21-5f5a-49cb-abcf-ec6f15fe6246', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='3d44baa9-ae5b-4055-bf9a-61cc6863604d' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('ff690d7a-f45c-49cb-ae89-e838730b7886', 'fc7ded21-5f5a-49cb-abcf-ec6f15fe6246')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('ff690d7a-f45c-49cb-ae89-e838730b7886', '18aabc85-5a48-496c-9cda-3a008cab88fc')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('dc449ec4-e4a2-4b47-8670-79edb1590a85', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '18aabc85-5a48-496c-9cda-3a008cab88fc', 'CL 1', 'CL 1', '6c1a4fa9-595a-4eec-9c04-77549a2fed5d')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('542c8a2a-49f5-4c55-a016-67619802a844', 'kuali.lui.type.activity.offering.lecture', '6c1a4fa9-595a-4eec-9c04-77549a2fed5d', 'ad0198c1-2073-48e4-9799-8e4055c74731')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('ad0198c1-2073-48e4-9799-8e4055c74731', 'fc7ded21-5f5a-49cb-abcf-ec6f15fe6246')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='6c1a4fa9-595a-4eec-9c04-77549a2fed5d' where ID='ad0198c1-2073-48e4-9799-8e4055c74731'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('cfaa554b-ada7-4eeb-9d68-943f9a5d6daa', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'ccb3c17e-6eaf-4095-85b7-648120717da9', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '93e50269-b982-48d4-b1a2-f6db09bc3a0c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('57ea025e-9999-49e6-94f1-71bc87688011', 'kuali.attribute.registration.group.aocluster.id', '93e50269-b982-48d4-b1a2-f6db09bc3a0c', '6c1a4fa9-595a-4eec-9c04-77549a2fed5d', '23165e83-a29a-4442-8081-15203b96c7e5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c13df13a-3c36-40fe-989b-c668c739a0f2', 'kuali.attribute.registration.group.is.generated', '93e50269-b982-48d4-b1a2-f6db09bc3a0c', 1, '64efb174-0925-49ab-be59-589588bef0b5')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5a9ae998-6c18-4298-8dc1-d2755275c1f8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '93e50269-b982-48d4-b1a2-f6db09bc3a0c', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'bf9a8328-f77b-44b2-95ae-868e961dd312')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('1e1df5c2-e214-444d-a2d9-5806cc5671bc', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:37.886', '', 'HIST319O-FO-RG', '18aabc85-5a48-496c-9cda-3a008cab88fc', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST319O-FO-RG', '93e50269-b982-48d4-b1a2-f6db09bc3a0c', 'edb4d244-cbba-4323-94fc-9077c4dfc71d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f9c734b7-cc1b-43df-928c-ffe458bfdecf', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:37.905', '', 'HIST319O-RG-AO', '93e50269-b982-48d4-b1a2-f6db09bc3a0c', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST319O-RG-AO', 'fc7ded21-5f5a-49cb-abcf-ec6f15fe6246', '444a8d47-94ea-4bfc-b6d0-7dc5c5354713')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('8afd8c75-e08e-4bf2-9767-3ea71d40286f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'CLUID-WMST326-199501000000', TIMESTAMP '2014-01-01 19:00:00.0', '', 'The biology of the reproductive system with emphasis on mammals and, in particular, on human reproduction. Hormone actions, sperm production, ovulation, sexual differentiation, sexual behavior, contraception, pregnancy, lactation, maternal behavior and menopause.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'WMST326 CO', 'The biology of the reproductive system with emphasis on mammals and, in particular, on human reproduction. Hormone actions, sperm production, ovulation, sexual differentiation, sexual behavior, contraception, pregnancy, lactation, maternal behavior and menopause.', null, '0c1ad016-2be6-434f-bc5d-6cff347cec16')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d84880f7-1404-4ad6-b9c5-ba0327c39791', 'kuali.attribute.final.exam.driver', '0c1ad016-2be6-434f-bc5d-6cff347cec16', 'kuali.lu.exam.driver.ActivityOffering', 'ebbadc20-0626-4fa2-822e-4d8ee2c595a3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('610f4d1b-5cb1-49d4-9a7b-bcec52b4d774', 'kuali.attribute.course.evaluation.indicator', '0c1ad016-2be6-434f-bc5d-6cff347cec16', 0, 'e9ad65d4-651e-410b-887a-a978a22cb7a8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0aa70b66-37ba-4cba-8e09-1ed3fcfcb468', 'kuali.attribute.wait.list.type.key', '0c1ad016-2be6-434f-bc5d-6cff347cec16', null, '43a2b9c1-7c67-4c46-9080-4a93d15067d9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a96ce428-89f2-41b2-aa13-91eabef24fb1', 'kuali.attribute.wait.list.indicator', '0c1ad016-2be6-434f-bc5d-6cff347cec16', 1, 'adf7c77f-d3aa-4011-83bf-9abe4e4fd36b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4fe8eceb-9232-4cda-9732-f0b236d80e98', 'kuali.attribute.funding.source', '0c1ad016-2be6-434f-bc5d-6cff347cec16', '', 'a10a61ba-274a-4e79-9037-9d8cf70241cd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9cf002e1-a31e-4fbc-b0cf-4a5c89a62e0d', 'kuali.attribute.final.exam.use.matrix', '0c1ad016-2be6-434f-bc5d-6cff347cec16', 1, 'e97ac3f2-5d37-454c-be8f-7c859cadd79f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('760933fb-adbb-4631-97f9-439e3a1770a5', 'kuali.attribute.where.fees.attached.flag', '0c1ad016-2be6-434f-bc5d-6cff347cec16', 0, '89582df6-b627-4170-aec8-747d5ff445f4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('14170875-2b34-460f-89a1-c530993167a3', 'kuali.attribute.course.number.internal.suffix', '0c1ad016-2be6-434f-bc5d-6cff347cec16', 'A', '7f95d7db-9e73-4d5f-bb1e-6a8fc7a53c45')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2c1ec5b0-2e28-43e5-8d4a-cf834f5701fe', 'kuali.attribute.final.exam.indicator', '0c1ad016-2be6-434f-bc5d-6cff347cec16', 'STANDARD', '91941639-65c9-44ea-afe9-7bcff70efac6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2fb4fe4e-3b2f-448b-aac1-91c83d90bd32', 'kuali.attribute.wait.list.level.type.key', '0c1ad016-2be6-434f-bc5d-6cff347cec16', 'AO', '54aa4bcd-3a88-4c7b-9695-2b8c0d5d34db')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('40af6010-fe6a-4149-8029-dd764a2ab421', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'WMST326', 'WMST', '', 'Biology of Reproduction', '0c1ad016-2be6-434f-bc5d-6cff347cec16', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7493e562-fae7-49aa-bb37-9d300d548d5c')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('43be513e-4078-438a-bbef-dd5320e687ef', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '0c1ad016-2be6-434f-bc5d-6cff347cec16', '', 'kuali.lu.code.honorsOffering', '', '2dcdcffb-65ff-4106-bf62-067a2a8b1b1a')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('0c1ad016-2be6-434f-bc5d-6cff347cec16', '4014660630', 'e7d02915-257d-4a6a-9f7b-9e0f218cfaad')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('0c1ad016-2be6-434f-bc5d-6cff347cec16', '4014660630')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0c1ad016-2be6-434f-bc5d-6cff347cec16', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0c1ad016-2be6-434f-bc5d-6cff347cec16', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0c1ad016-2be6-434f-bc5d-6cff347cec16', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0c1ad016-2be6-434f-bc5d-6cff347cec16', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('e1d69627-2ba4-41c8-b4f7-f9ae446bf7f0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '365c3c5f-8422-4b70-8bd7-949a708229d8', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '66b8e5d3-c091-4f22-953a-693fd0481cd2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('75390078-f052-407d-8c1f-ba86b39e3f1e', 'kuali.attribute.grade.roster.level.type.key', '66b8e5d3-c091-4f22-953a-693fd0481cd2', 'kuali.lui.type.activity.offering.lecture', '18363d3b-e6d8-417d-907d-5bfba2ac221a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9097aa25-3c17-4d89-8c15-6ed152d5bbe8', 'regCodePrefix', '66b8e5d3-c091-4f22-953a-693fd0481cd2', '1', '2fde2d2b-449b-428c-8b82-bcc8bd508b36')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b84223be-73b1-4d8c-8773-862f8702f070', 'kuali.attribute.final.exam.level.type', '66b8e5d3-c091-4f22-953a-693fd0481cd2', 'kuali.lu.type.activity.Lecture', 'fff9ac25-fad5-4477-a17e-e5ba48c9481b')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('17d65a2a-ca24-4e9b-8860-0aed557d87bb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '66b8e5d3-c091-4f22-953a-693fd0481cd2', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '8ea95388-e022-48f7-9d0f-66dc4bf45cab')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('66b8e5d3-c091-4f22-953a-693fd0481cd2', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('63461a59-03a7-411f-9950-7e9372be6cd6', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:41.836', '', 'WMST326-CO-FO', '0c1ad016-2be6-434f-bc5d-6cff347cec16', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'WMST326-CO-FO', '66b8e5d3-c091-4f22-953a-693fd0481cd2', 'f7e7548a-601f-47ed-841e-ba1fbba8b224')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('f9e602d3-d911-4043-b286-71a9f5649abf', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '90813efb-60bf-4b2d-90fd-1866376bbffa', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 75, 75, 'Lecture', 'Courses with lecture', null, '1f24398f-66f5-460e-a1a5-a6ced3689e13')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4772efed-419f-4ad2-a75e-2f70801a960a', 'kuali.attribute.nonstd.ts.indicator', '1f24398f-66f5-460e-a1a5-a6ced3689e13', 0, 'c0be844e-8078-4d5d-9472-12f38fa039dc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('47aca356-7ca0-4918-a9db-d178dd5f1ab7', 'kuali.attribute.max.enrollment.is.estimate', '1f24398f-66f5-460e-a1a5-a6ced3689e13', 0, 'c75b566f-7f55-4fa8-8986-359a699d2c6d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1c8913b2-3b52-45c4-9d86-07a783fa110b', 'kuali.attribute.course.evaluation.indicator', '1f24398f-66f5-460e-a1a5-a6ced3689e13', 0, 'b72cae05-4a65-4ee9-b1b3-6f672e995ef9')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('0c2ef5f6-4c50-4121-9088-a7670fdde113', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '1f24398f-66f5-460e-a1a5-a6ced3689e13', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'b52098f2-f589-4d4e-a5f1-584c926513c1')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('c3ccb62a-88d2-43a0-8379-5bd404d7babf', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '1f24398f-66f5-460e-a1a5-a6ced3689e13', '', 'kuali.lu.code.honorsOffering', 0, '1acb73b8-12be-4d9b-8eac-9864bc9560f1')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('5fda6ad5-41b8-45dd-b494-470cae31aacf', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:41.954', '', '', '1f24398f-66f5-460e-a1a5-a6ced3689e13', '', 'H.HONGYANZ', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '2d8ef9b9-6fa7-44dd-aee9-d01d3f4e8218')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('b21ee585-35b7-4d85-9adf-308eeaf1f462', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:41.991', '', 'WMST326-FO-AO', '66b8e5d3-c091-4f22-953a-693fd0481cd2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'WMST326-FO-AO', '1f24398f-66f5-460e-a1a5-a6ced3689e13', 'dc380f47-4f9d-4fc2-9a6c-c9b07e1632ba')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('834e79d6-fbb0-40ae-aa09-d2425d6339fb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for WMST326 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'ddd5f772-dfcc-4bc7-927e-443a068916e1')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('eb48cda3-a348-434a-b0c7-4e502ea36295', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for WMST326 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'ddd5f772-dfcc-4bc7-927e-443a068916e1', 'c82a8df4-d67e-4c8b-8016-f7d689915e28')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('0db4215b-7887-49e2-b752-c62ce1a505ec', 0, 'c82a8df4-d67e-4c8b-8016-f7d689915e28', 'e8f9d4f4-5c98-4c9a-936d-e0e4168126a8')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('ddd5f772-dfcc-4bc7-927e-443a068916e1', '1f24398f-66f5-460e-a1a5-a6ced3689e13')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('e8f9d4f4-5c98-4c9a-936d-e0e4168126a8', '89929ead-579b-4f51-bf64-2797f963848b')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('e8f9d4f4-5c98-4c9a-936d-e0e4168126a8', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('e8f9d4f4-5c98-4c9a-936d-e0e4168126a8', 'b8e731ee-f0c0-4479-8413-1e0cc483071d')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('e8f9d4f4-5c98-4c9a-936d-e0e4168126a8', 'F0AA72D6DB70F650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('3783769e-4259-4ed0-bee0-5c8b100c7f79', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '958c26d3-cfe6-444a-bd9b-5ad266c70945')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('a845f91c-5fd6-4c82-8954-b25632e5efe4', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '97844566-bce4-43d0-9b61-d76c5b30b2e3')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='3783769e-4259-4ed0-bee0-5c8b100c7f79', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='1f24398f-66f5-460e-a1a5-a6ced3689e13', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='958c26d3-cfe6-444a-bd9b-5ad266c70945' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('97844566-bce4-43d0-9b61-d76c5b30b2e3', '1f24398f-66f5-460e-a1a5-a6ced3689e13')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('97844566-bce4-43d0-9b61-d76c5b30b2e3', '66b8e5d3-c091-4f22-953a-693fd0481cd2')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('97741a7f-db15-4598-aec8-677f4d39cec5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '66b8e5d3-c091-4f22-953a-693fd0481cd2', 'CL 1', 'CL 1', '01f895f0-08c2-40f7-a589-a2e1a7151d6e')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('55f3ef11-1a28-49a9-ab60-808d6f03d877', 'kuali.lui.type.activity.offering.lecture', '01f895f0-08c2-40f7-a589-a2e1a7151d6e', '11c8415a-4ea7-451f-90d1-aac9c059cafb')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('11c8415a-4ea7-451f-90d1-aac9c059cafb', '1f24398f-66f5-460e-a1a5-a6ced3689e13')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='01f895f0-08c2-40f7-a589-a2e1a7151d6e' where ID='11c8415a-4ea7-451f-90d1-aac9c059cafb'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('a312c28f-4387-4206-99c8-ddb741c986eb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '365c3c5f-8422-4b70-8bd7-949a708229d8', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'de65a894-56f2-4880-9d89-a88e327d8472')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d6a627c5-2c15-4323-9b95-e96be3c30ab2', 'kuali.attribute.registration.group.is.generated', 'de65a894-56f2-4880-9d89-a88e327d8472', 1, '43a7f841-14b6-4ca4-8193-35f26dd7d029')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fca0eb86-c723-47a0-a54c-95e565e82524', 'kuali.attribute.registration.group.aocluster.id', 'de65a894-56f2-4880-9d89-a88e327d8472', '01f895f0-08c2-40f7-a589-a2e1a7151d6e', '2f34b956-8940-45c3-927e-ab795fafedeb')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('8c09c2d3-c5b0-48c5-8462-f8db48a74559', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'de65a894-56f2-4880-9d89-a88e327d8472', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '6a13f5f7-51f0-4854-a691-6d0b15b49561')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('0d1732c2-1b43-4742-825c-73f47a6e026a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:42.403', '', 'WMST326-FO-RG', '66b8e5d3-c091-4f22-953a-693fd0481cd2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'WMST326-FO-RG', 'de65a894-56f2-4880-9d89-a88e327d8472', '48746c4e-a4b1-4f07-9579-8595f7169691')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('8d5078a2-fd28-4f8c-90af-91019a01dbb8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:42.423', '', 'WMST326-RG-AO', 'de65a894-56f2-4880-9d89-a88e327d8472', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'WMST326-RG-AO', '1f24398f-66f5-460e-a1a5-a6ced3689e13', 'bc2f0961-a68a-46d3-8f69-744683e6fc46')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('41c4716d-a867-49ee-9b25-f49c9755c420', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'CLUID-BSCI379-199908000000', TIMESTAMP '2014-01-01 19:00:00.0', '', 'This course is arranged to provide qualified majors an opportunity to pursue research problems under the supervision of a member of the department.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'BSCI379G CO', 'This course is arranged to provide qualified majors an opportunity to pursue research problems under the supervision of a member of the department.', null, '33eec9c6-f8c8-4609-931b-f4d60cb0d636')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('66e57974-4f61-4f0f-a012-1b621a633a8c', 'kuali.attribute.where.fees.attached.flag', '33eec9c6-f8c8-4609-931b-f4d60cb0d636', 0, '8ab16438-7d8d-4b57-99d3-49e1092ff54f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5102ec3d-c28e-4600-bf16-da71453d28f5', 'kuali.attribute.course.number.internal.suffix', '33eec9c6-f8c8-4609-931b-f4d60cb0d636', 'A', '6d7f3c59-d1c5-4116-b7fa-142a673c1fdf')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8da0e400-6802-4f57-9ee7-98ca06fffe18', 'kuali.attribute.funding.source', '33eec9c6-f8c8-4609-931b-f4d60cb0d636', '', 'ff1988f9-e3b9-4e6f-85c7-754a8262ca50')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('84ad4720-3a40-4335-880a-29f40e381792', 'kuali.attribute.final.exam.use.matrix', '33eec9c6-f8c8-4609-931b-f4d60cb0d636', 1, '0357d8f2-8b60-4d54-aa62-059eb85a3578')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1fa05ab9-0a8b-4a85-9847-24a4d082f1f4', 'kuali.attribute.wait.list.indicator', '33eec9c6-f8c8-4609-931b-f4d60cb0d636', 1, 'd3b76530-c164-4510-bf8e-0a0ecccfb9c5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6a890604-74f5-433e-b4f5-2ed672c7c55e', 'kuali.attribute.final.exam.indicator', '33eec9c6-f8c8-4609-931b-f4d60cb0d636', 'STANDARD', '81085885-4e7a-49da-abd0-d82112e82fab')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6a107268-d067-48c6-bbee-2d76fa5bd740', 'kuali.attribute.wait.list.type.key', '33eec9c6-f8c8-4609-931b-f4d60cb0d636', null, '2ae24d38-ef2d-448c-a80b-645118b16892')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f1c9a92a-0041-4dcb-bd49-08a39d476f86', 'kuali.attribute.wait.list.level.type.key', '33eec9c6-f8c8-4609-931b-f4d60cb0d636', 'AO', '6efe7f77-6d04-4201-ad1f-0028157174cb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c4462631-7e34-4bd5-867e-2320c2c42d7b', 'kuali.attribute.final.exam.driver', '33eec9c6-f8c8-4609-931b-f4d60cb0d636', 'kuali.lu.exam.driver.ActivityOffering', '25ad3ec3-6d7c-4516-a572-63e31d60b8e7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4902d89b-fdeb-4b97-84b0-9a1e54aa8198', 'kuali.attribute.course.evaluation.indicator', '33eec9c6-f8c8-4609-931b-f4d60cb0d636', 0, '350e7f77-3192-42e3-805a-57991497997b')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('051a1d7d-248e-40ff-af38-52ba80db3f06', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'BSCI379G', 'BSCI', '', 'Cell Biology and Molecular Genetics Department Research', '33eec9c6-f8c8-4609-931b-f4d60cb0d636', '', '', 'kuali.lui.identifier.state.active', 'G', 'kuali.lui.identifier.type.official', '', '38adf17f-37e3-4659-997d-c3af6133f5f6')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('0cda215e-689a-4727-96aa-30bcbccb9127', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '33eec9c6-f8c8-4609-931b-f4d60cb0d636', '', 'kuali.lu.code.honorsOffering', '', '3befd80b-1c2b-4f5d-a26c-715762fa271e')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('33eec9c6-f8c8-4609-931b-f4d60cb0d636', '576639460', 'bf2fe7fe-7f7d-4f17-98a5-0634f1c089e1')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('33eec9c6-f8c8-4609-931b-f4d60cb0d636', '576639460')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('33eec9c6-f8c8-4609-931b-f4d60cb0d636', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('33eec9c6-f8c8-4609-931b-f4d60cb0d636', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('33eec9c6-f8c8-4609-931b-f4d60cb0d636', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('33eec9c6-f8c8-4609-931b-f4d60cb0d636', 'kuali.creditType.credit.degree.1.0,1.5,2.0,2.5,3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('8077259a-71b1-4c6c-8efb-5f3ac29ab761', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '4838b255-888b-4946-b32a-b168313b5e7f', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'c8c08a21-95de-4c1f-bb31-54092d88b276')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8b16d65a-744d-4609-b736-709e5d07e58f', 'kuali.attribute.final.exam.level.type', 'c8c08a21-95de-4c1f-bb31-54092d88b276', 'kuali.lu.type.activity.Lecture', 'fe82e82f-9a16-47af-8956-bffbd1569c80')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('14d0ba90-7c1b-409a-b9bf-ee4c065d9768', 'kuali.attribute.grade.roster.level.type.key', 'c8c08a21-95de-4c1f-bb31-54092d88b276', 'kuali.lui.type.activity.offering.lecture', '2df12ac4-6eee-47c3-8dab-cbafdd1966bb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('957718dc-b49b-4af6-9ae8-252e69b71962', 'regCodePrefix', 'c8c08a21-95de-4c1f-bb31-54092d88b276', '1', 'ab60e8b9-9e2f-4182-bcaa-5c7d3918da5c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7833621f-e63f-472c-83d2-466794a695d6', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', 'c8c08a21-95de-4c1f-bb31-54092d88b276', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ca2b4653-8869-426d-87df-a4e1123d7c09')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('c8c08a21-95de-4c1f-bb31-54092d88b276', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('9cdb4766-0e3f-4c6d-9b3c-e82a301eb372', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:45.909', '', 'BSCI379G-CO-FO', '33eec9c6-f8c8-4609-931b-f4d60cb0d636', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'BSCI379G-CO-FO', 'c8c08a21-95de-4c1f-bb31-54092d88b276', 'f5a92db6-93e5-4741-a5c8-532609e86cb6')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('6a5903ed-1234-4fdc-9f52-01192f431399', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'f0ce3b8b-062f-4fd0-91d0-bf99659d3b2c', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 5, 5, 'Lecture', 'Courses with lecture', null, 'bc979d4b-3e9f-4657-80f9-10c999eb60ec')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('79aac0d9-0c16-46e3-acb8-7d07652b0816', 'kuali.attribute.max.enrollment.is.estimate', 'bc979d4b-3e9f-4657-80f9-10c999eb60ec', 0, '63a4a996-5975-4b42-b550-203a1348bd7b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('49c6084e-7acd-47e9-84f6-b51c2cfc42b0', 'kuali.attribute.course.evaluation.indicator', 'bc979d4b-3e9f-4657-80f9-10c999eb60ec', 0, '8decb6b4-122c-4659-8fc7-8a9806a4cfca')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('508f9f17-a211-4765-9314-53040c699979', 'kuali.attribute.nonstd.ts.indicator', 'bc979d4b-3e9f-4657-80f9-10c999eb60ec', 0, '857a6b27-1ea1-4968-94c2-9e93cc22d56e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('68b5a079-0c45-447a-9bb8-c69c7ce4301e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', 'bc979d4b-3e9f-4657-80f9-10c999eb60ec', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '07adeadc-d1ae-4bf2-b5f2-b069089d8335')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('aa1a453e-f9cf-4ec6-9e1c-0941b937ca86', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'bc979d4b-3e9f-4657-80f9-10c999eb60ec', '', 'kuali.lu.code.honorsOffering', 0, '36bad732-cac8-408e-a208-2bb63e55395a')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('dd6a61ea-97d1-443a-8cf9-19f1118ce1b5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:46.02', '', '', 'bc979d4b-3e9f-4657-80f9-10c999eb60ec', '', 'C.SANDRAS', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '0db9f901-2d14-47db-bf59-3fe016ef599b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f0ac0d9e-62bf-4a48-af9a-1e6290f17cc5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:46.053', '', 'BSCI379G-FO-AO', 'c8c08a21-95de-4c1f-bb31-54092d88b276', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'BSCI379G-FO-AO', 'bc979d4b-3e9f-4657-80f9-10c999eb60ec', 'fa06ae48-d70c-458d-b443-0928d72d0a08')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('501d005e-5282-4e17-a8a0-1a990624a4ab', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for BSCI379G - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '5702fd2e-6959-4c8c-92e7-e06fb9687400')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('910ac92a-13ba-45f8-bffc-673846b73710', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for BSCI379G - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '5702fd2e-6959-4c8c-92e7-e06fb9687400', '2ddcd1a4-cdd0-40d1-bc9f-e55d1bb857da')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('a7bc5231-b22c-49c3-baac-74d5dccf4158', 1, '2ddcd1a4-cdd0-40d1-bc9f-e55d1bb857da', '4d351830-00d2-40d0-a86f-89e7638869a2')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('5702fd2e-6959-4c8c-92e7-e06fb9687400', 'bc979d4b-3e9f-4657-80f9-10c999eb60ec')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('4d351830-00d2-40d0-a86f-89e7638869a2', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('ed4df98f-cc32-4d5a-926d-b557fc3b15eb', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '022182ba-2dd9-41f1-9e53-3b2dfd6598da')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('df3891f3-8542-4ad3-a0b2-bc7daafbc25f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '1624a324-c3b7-4f57-af53-ad7522b6b7ff')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='ed4df98f-cc32-4d5a-926d-b557fc3b15eb', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='bc979d4b-3e9f-4657-80f9-10c999eb60ec', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='022182ba-2dd9-41f1-9e53-3b2dfd6598da' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('1624a324-c3b7-4f57-af53-ad7522b6b7ff', 'bc979d4b-3e9f-4657-80f9-10c999eb60ec')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('1624a324-c3b7-4f57-af53-ad7522b6b7ff', 'c8c08a21-95de-4c1f-bb31-54092d88b276')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('e0d083f3-7c4f-4bab-8f98-1bba87f641f8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'c8c08a21-95de-4c1f-bb31-54092d88b276', 'CL 1', 'CL 1', '71c10953-4099-4f6c-a689-8dd0b345686d')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('7875cda6-2242-486f-a8fa-2f985d4eb794', 'kuali.lui.type.activity.offering.lecture', '71c10953-4099-4f6c-a689-8dd0b345686d', 'b15a93f3-85dc-4a2f-b827-7b0788cc8095')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('b15a93f3-85dc-4a2f-b827-7b0788cc8095', 'bc979d4b-3e9f-4657-80f9-10c999eb60ec')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='71c10953-4099-4f6c-a689-8dd0b345686d' where ID='b15a93f3-85dc-4a2f-b827-7b0788cc8095'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('75727e6e-e165-4f82-8801-d880454c555b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '4838b255-888b-4946-b32a-b168313b5e7f', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '7cd163a7-0fe3-4982-9588-2f0a4473addd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('adbab9b8-bd9f-44a4-851d-00c34914a66c', 'kuali.attribute.registration.group.is.generated', '7cd163a7-0fe3-4982-9588-2f0a4473addd', 1, '790f1630-22ed-4b33-995d-16d66d3889de')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('951d33db-4719-45d6-bb20-d004f57d20fd', 'kuali.attribute.registration.group.aocluster.id', '7cd163a7-0fe3-4982-9588-2f0a4473addd', '71c10953-4099-4f6c-a689-8dd0b345686d', '758de082-9d5d-40e3-9e2b-6c9302d1dbb2')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('69690ac4-89dd-4251-994e-410920bcdbc0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '7cd163a7-0fe3-4982-9588-2f0a4473addd', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c3298db3-9261-4cc8-999f-87ec0aa7ea70')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e62484c0-1b4c-4e1f-986c-4d43d98eeb1b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:46.482', '', 'BSCI379G-FO-RG', 'c8c08a21-95de-4c1f-bb31-54092d88b276', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'BSCI379G-FO-RG', '7cd163a7-0fe3-4982-9588-2f0a4473addd', 'c7eefcea-e32d-4e90-9e7b-ff1bf37f9b0b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('88045d49-7b78-4091-ba15-3c392a529e95', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:46.485', '', 'BSCI379G-RG-AO', '7cd163a7-0fe3-4982-9588-2f0a4473addd', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'BSCI379G-RG-AO', 'bc979d4b-3e9f-4657-80f9-10c999eb60ec', '53e8c6cb-0abf-41c1-97ea-2451b641fa8f')
/
update KSEN_SOC_ROR set OBJ_ID='971d59cc-eb7c-4504-ac92-ec51f651c326', VER_NBR=6, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ITEMS_EXPECTED=48, ITEMS_PROCESSED=40, MESG_FORMATTED='', MESG_PLAIN='', SOC_ROR_STATE='kuali.soc.rollover.state.running', SOC_ROR_TYPE='kuali.soc.rollover.results.type.rollover', SOURCE_SOC_ID='03192a24-765a-4b9d-b638-5158430e09c8', TARGET_SOC_ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', TARGET_TERM_ID='kuali.atp.2014Winter' where ID='5b88a612-d027-429f-a9f3-b4241ed736c7' and VER_NBR=5
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='e2dae705-59ea-4cea-a1b8-97963890ed4e', ATTR_KEY='kuali.soc.rollover.result.dynattr.date.completed', OWNER_ID='5b88a612-d027-429f-a9f3-b4241ed736c7', ATTR_VALUE='2014 Jan 23 20:02:46 America/New_York' where ID='289b7901-b122-4239-94fd-e4dd1e0066cf'
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('ac96d3b6-dd26-4374-a259-84abaa088815', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'a832e862-04bc-4924-8789-ffb5be5242f5', '', '395f4a6c-bf4e-440c-811a-5a0d2ff66d7d')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('c18bda5e-8790-42f5-935a-20395f8af684', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'a3051367-d387-4e58-92cd-6409dd8b83ab', 'aafe640f-f2e5-44e8-b887-ce64d92186a2', 'dcd273c7-cc7d-4c20-96ba-a3ef3fd2c8ba')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7ccdd948-95a4-491f-be09-e4c4c51c2773', 'activityOfferingsCreated', 'dcd273c7-cc7d-4c20-96ba-a3ef3fd2c8ba', '1', '8bea51ef-537d-4024-b989-813ec7e53a5a')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('a8c3d344-a159-422c-9d35-21e251095d57', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'aa2a3f73-98d9-4131-9a3d-df5663792212', '51834b55-898a-4654-b4f1-66594453157f', '71a60e85-2655-4ec6-b3bb-518b994d9d7e')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b00a308d-f6d0-48f6-a953-b7126325345d', 'activityOfferingsCreated', '71a60e85-2655-4ec6-b3bb-518b994d9d7e', '1', '260ce7dd-a431-485f-bf9c-640ea15cfe1e')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('a12dcfd5-4910-4571-96af-47871bcebf1e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'b2123e45-d226-4b55-9779-8b1666d2eb58', '', '2ebccfe1-1157-49f7-81ea-2c858b328f9c')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('d6443690-d3df-42c0-a226-e48aa96d00cc', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'b2610a4f-953d-48e7-9aa3-7415dd032e73', '', 'c9c89961-0ce5-4f77-956b-197e1811e964')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('6e272ec1-9f40-4083-9fd5-c5cf210416da', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'c7974cf4-5d32-4af2-a8e3-a9727cf04960', '', '5d6f111c-a982-4742-bc2d-dc194650a483')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('d7381050-8a93-4742-8577-def2035f2e69', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'cc8d76a8-448e-493c-904f-0924319a3818', '0c1ad016-2be6-434f-bc5d-6cff347cec16', 'b7ca9545-5822-4c1a-a263-e842c59e6286')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6f42e691-cf83-4c9b-9179-745c96d9489a', 'activityOfferingsCreated', 'b7ca9545-5822-4c1a-a263-e842c59e6286', '1', '07014e72-85b2-478a-a073-e1e7a7f2866a')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('dcadc07f-699c-453e-8dfd-2377e9e3a8f1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'cc3f9686-4ff8-4521-a1cc-0d2031ebed8e', '', 'fd901ab9-984f-4202-b577-c7be7c9c0329')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('9fea22ea-c915-4e02-9e85-6e616342369a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'c995d3e9-ba68-4f87-b9c2-62214e192781', '', 'db28fe2e-f6d9-4d0f-89b7-950ac7c31f2f')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('d20f7676-30ea-4fb6-9b20-5ee37a03d81d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'db271f45-4aee-4d7a-b746-25398a78b3fb', '33eec9c6-f8c8-4609-931b-f4d60cb0d636', 'fdfd5d65-6062-4df7-8ea0-4db9386b6477')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c01393f5-a248-43c0-8454-48e138080898', 'activityOfferingsCreated', 'fdfd5d65-6062-4df7-8ea0-4db9386b6477', '1', 'f926f1d7-f7c0-4b76-bfa5-a1861ac6f04f')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('4bdee1c7-77aa-4f2c-ab1e-76c4a67af35b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'd1c291df-38c6-4bb7-9c62-fec470897c8d', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Workshops will cover all aspects of giving scientific presentations. Each student will give a presentation based oon the topic of his/her final paper in CHEM611. Presentations will be critiqued by peers and faculty members.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'CHEM612 CO', 'Workshops will cover all aspects of giving scientific presentations. Each student will give a presentation based oon the topic of his/her final paper in CHEM611. Presentations will be critiqued by peers and faculty members.', null, '10164c43-4d2d-438f-a763-9508f786968c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a351896b-840e-45a6-b8d4-a83299392e4d', 'kuali.attribute.course.evaluation.indicator', '10164c43-4d2d-438f-a763-9508f786968c', 0, 'f2c0a868-9208-48fd-894a-2423de3ac366')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ee12a4ec-8c64-46c6-bbec-7a842ff54dff', 'kuali.attribute.wait.list.type.key', '10164c43-4d2d-438f-a763-9508f786968c', null, '81e2a1f7-2df7-42ed-aaee-478996e1a8c5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5beb8e2f-4789-4322-8394-f5aad54cdc1b', 'kuali.attribute.final.exam.use.matrix', '10164c43-4d2d-438f-a763-9508f786968c', 1, 'cc9a57d2-5570-4c40-960b-a9ad2399940a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('642796ad-c923-4701-842b-d03001e3d4ed', 'kuali.attribute.wait.list.level.type.key', '10164c43-4d2d-438f-a763-9508f786968c', 'AO', 'f7b0f500-9596-420f-81fd-5581c6f4f8b0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5cf33b47-7b5e-47e7-a990-2f9db3f3ea73', 'kuali.attribute.where.fees.attached.flag', '10164c43-4d2d-438f-a763-9508f786968c', 0, 'b984287e-f218-46cc-bb3d-5907bd83fd07')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0c47ccbb-c794-40d1-a55b-9eb32030c801', 'kuali.attribute.course.number.internal.suffix', '10164c43-4d2d-438f-a763-9508f786968c', 'A', '72e8cf31-77d1-4f6e-a06a-97b4d8aafd9a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7c24192c-9ba1-4ef8-a8a0-bf4c99600417', 'kuali.attribute.wait.list.indicator', '10164c43-4d2d-438f-a763-9508f786968c', 1, 'e9f4a800-05af-4cf5-a2a8-ea24a85b6e4e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('28b82a9e-746f-40d7-8225-8eaebddfc1c5', 'kuali.attribute.final.exam.driver', '10164c43-4d2d-438f-a763-9508f786968c', 'kuali.lu.exam.driver.ActivityOffering', 'bddf639e-c186-4994-b8fb-8b769ba60735')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1259d098-8c20-4da3-b237-2d6b53c2d8ad', 'kuali.attribute.funding.source', '10164c43-4d2d-438f-a763-9508f786968c', '', '9c18c0cf-a412-4b35-b3f5-2f7b92492d70')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a005ef2a-11b4-4ac6-9cdc-1d42872e4b95', 'kuali.attribute.final.exam.indicator', '10164c43-4d2d-438f-a763-9508f786968c', 'STANDARD', '54884171-2cf1-448c-a85c-0a3160f10eb9')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('1e836567-03f2-4a31-b32d-e61143008e5b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'CHEM612', 'CHEM', '', 'Scientific Presentations', '10164c43-4d2d-438f-a763-9508f786968c', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'd20962af-c949-49cf-97ad-06104cbf82a6')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('7f9c1fe1-cb9c-4128-aac2-e0336654bda0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '10164c43-4d2d-438f-a763-9508f786968c', '', 'kuali.lu.code.honorsOffering', '', '5d7cc72f-8020-48e4-8119-a569c38c3166')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('10164c43-4d2d-438f-a763-9508f786968c', '4284516367', '8028d502-1243-4c9f-bb13-7c6e0b1802df')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('10164c43-4d2d-438f-a763-9508f786968c', '4284516367')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('10164c43-4d2d-438f-a763-9508f786968c', 'kuali.resultComponent.grade.satisfactory')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('10164c43-4d2d-438f-a763-9508f786968c', 'kuali.creditType.credit.degree.1.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('11fd1e75-92b4-4069-b70f-7715c47d46ba', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '76f029e8-5b80-46ae-b984-6e770aa6ff6a', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '47518962-9a7f-4206-bf39-406bd1f836ed')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8d462504-1f89-4bcc-8cd8-983a58ee9171', 'kuali.attribute.grade.roster.level.type.key', '47518962-9a7f-4206-bf39-406bd1f836ed', 'kuali.lui.type.activity.offering.lecture', '654f6d65-96c5-46b3-8206-92a6e3e824fc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bd0876a7-d5f5-4146-a15f-cfaf603c041b', 'kuali.attribute.final.exam.level.type', '47518962-9a7f-4206-bf39-406bd1f836ed', 'kuali.lu.type.activity.Lecture', '624b871a-8523-4524-a057-ac870171f2fc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a8d93124-f485-4ba6-9715-70ba253d0ad7', 'regCodePrefix', '47518962-9a7f-4206-bf39-406bd1f836ed', '1', 'ec7bdad3-c3c4-449a-a80c-098265cef65a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('2f10fcec-ea3a-4439-a46a-e154532983ac', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '47518962-9a7f-4206-bf39-406bd1f836ed', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'fb4ba18a-cff6-4843-bbcc-317504a66e27')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('47518962-9a7f-4206-bf39-406bd1f836ed', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('d8c9457c-1900-489b-802a-c21bc710ffeb', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:49.0', '', 'CHEM612-CO-FO', '10164c43-4d2d-438f-a763-9508f786968c', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'CHEM612-CO-FO', '47518962-9a7f-4206-bf39-406bd1f836ed', '4d024c97-7d92-44ca-a6f5-27721a714aa0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('3de7ed89-d486-43a6-89cd-4c89a3bc78cc', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'ee17574c-1834-4c17-bf27-85d0da160d61', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, '9338c442-e4d8-4bd5-8873-2d1870aa0a6c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0bb9f5d0-1a83-43bf-b2fb-ffe96b32789b', 'kuali.attribute.max.enrollment.is.estimate', '9338c442-e4d8-4bd5-8873-2d1870aa0a6c', 0, '8ae930cc-4669-462a-998f-abccc1c24c01')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e82d3d48-3f5c-4567-85ae-d862a4bd1e8b', 'kuali.attribute.course.evaluation.indicator', '9338c442-e4d8-4bd5-8873-2d1870aa0a6c', 0, 'e8f4c85b-206e-4e87-8cbf-59b3666a97d6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9b281eb5-8f21-4e2f-9c75-44db24da242e', 'kuali.attribute.nonstd.ts.indicator', '9338c442-e4d8-4bd5-8873-2d1870aa0a6c', 0, '00d90476-0913-4cd5-bf45-73b9406e5a45')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5154ec27-101b-4590-96bd-af066f795a78', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '9338c442-e4d8-4bd5-8873-2d1870aa0a6c', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a920dd16-abf1-47af-b338-0631bb1ef0ea')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('b5b37d1c-44e6-458f-b45c-45f642d97719', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '9338c442-e4d8-4bd5-8873-2d1870aa0a6c', '', 'kuali.lu.code.honorsOffering', 0, '5a68775e-f00a-43a6-88ed-06190f550bfe')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('18c36f89-4e8e-44c1-8ba9-c1c9c7f929f6', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:49.115', '', '', '9338c442-e4d8-4bd5-8873-2d1870aa0a6c', '', 'B.JAMESJ', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '75275f49-0995-4865-9f50-48fbfd4affae')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('4657e2c8-641a-4cc8-ab76-ef6d3e013863', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:49.156', '', 'CHEM612-FO-AO', '47518962-9a7f-4206-bf39-406bd1f836ed', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM612-FO-AO', '9338c442-e4d8-4bd5-8873-2d1870aa0a6c', 'b4ef22bf-bb23-42dc-850c-11dd01d7ea81')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('5fcf2741-fcbb-4030-9058-b29172b85162', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for CHEM612 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '6cc654ce-e7d0-4b75-a5eb-bf5c400ac982')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('9283e9db-1e58-4780-b823-4793ade54935', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for CHEM612 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '6cc654ce-e7d0-4b75-a5eb-bf5c400ac982', '9f32fb8c-4a8d-4128-b93e-209db9ebbcca')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('97bd8eb8-6738-4383-af86-9dcbcff3ed04', 0, '9f32fb8c-4a8d-4128-b93e-209db9ebbcca', '6f70f2c7-574f-420b-83b5-3d603ad8c77d')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('6cc654ce-e7d0-4b75-a5eb-bf5c400ac982', '9338c442-e4d8-4bd5-8873-2d1870aa0a6c')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('6f70f2c7-574f-420b-83b5-3d603ad8c77d', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('6f70f2c7-574f-420b-83b5-3d603ad8c77d', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('6f70f2c7-574f-420b-83b5-3d603ad8c77d', '2246d609-9fbb-4d06-b45e-a57ef80539b7')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('6f70f2c7-574f-420b-83b5-3d603ad8c77d', 'F0AA72D6DAEAF650E040440A9B9A11B8')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('ca7ba5c6-a704-4035-abe3-c0c9edf83f21', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '09e3cb49-bf02-4a45-a120-5ec70d51e67e')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('09e3cb49-bf02-4a45-a120-5ec70d51e67e', '9338c442-e4d8-4bd5-8873-2d1870aa0a6c')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('09e3cb49-bf02-4a45-a120-5ec70d51e67e', '47518962-9a7f-4206-bf39-406bd1f836ed')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('57d06765-1230-49bc-a360-df01393ff14b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '47518962-9a7f-4206-bf39-406bd1f836ed', 'CL 1', 'CL 1', '6a721079-083f-47c3-b05a-9402ecbac7d4')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('924ce2f8-0fd2-4f17-a47e-bee0bcee7367', 'kuali.lui.type.activity.offering.lecture', '6a721079-083f-47c3-b05a-9402ecbac7d4', '6af917cd-00a1-4c4b-abe0-8594e270b92b')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('6af917cd-00a1-4c4b-abe0-8594e270b92b', '9338c442-e4d8-4bd5-8873-2d1870aa0a6c')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='6a721079-083f-47c3-b05a-9402ecbac7d4' where ID='6af917cd-00a1-4c4b-abe0-8594e270b92b'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ffb7d3d4-29f0-40a5-aeaf-c2ea88be1626', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '76f029e8-5b80-46ae-b984-6e770aa6ff6a', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '67e855ee-a50b-46c7-831b-41a7bf641982')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c27dc37b-97e3-41b9-97ac-93fd0006e676', 'kuali.attribute.registration.group.aocluster.id', '67e855ee-a50b-46c7-831b-41a7bf641982', '6a721079-083f-47c3-b05a-9402ecbac7d4', '953de8ce-018f-4867-8217-68dbbb1dd580')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7cdb6ce4-69e2-4936-a508-e65ecd56d441', 'kuali.attribute.registration.group.is.generated', '67e855ee-a50b-46c7-831b-41a7bf641982', 1, '2b61e6cb-844b-4375-b045-f92e711db7ce')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('133f037b-351d-4544-8755-bb88ce8a3942', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '67e855ee-a50b-46c7-831b-41a7bf641982', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '54ce38cb-dbac-4ae9-b766-f25ac3dea488')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('9192e1c1-6499-4359-8dc2-0362dd98c46e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:49.663', '', 'CHEM612-FO-RG', '47518962-9a7f-4206-bf39-406bd1f836ed', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM612-FO-RG', '67e855ee-a50b-46c7-831b-41a7bf641982', '54caf80f-7335-45e8-8d71-73a411b31b3d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('1d0ff35a-fa9b-4535-945e-1bc29fa5db52', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:49.677', '', 'CHEM612-RG-AO', '67e855ee-a50b-46c7-831b-41a7bf641982', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM612-RG-AO', '9338c442-e4d8-4bd5-8873-2d1870aa0a6c', 'd8e3c3a6-2627-47cd-86f9-03edd99ba323')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('a77b80ad-da64-4b15-b2a8-06ff5f7bdbee', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '9f1f9e6e-e87e-4646-b25f-723b313339f9', TIMESTAMP '2014-01-01 19:00:00.0', '', 'A history of religion, religious movements, and churches in America from the early Colonial period to the present, with special attention to the relation between church and society.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST306 CO', 'A history of religion, religious movements, and churches in America from the early Colonial period to the present, with special attention to the relation between church and society.', null, '0a80670a-864b-46e1-8546-f460cbd1925b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4ededd27-c909-46b8-aa6a-1b511ac3645e', 'kuali.attribute.final.exam.use.matrix', '0a80670a-864b-46e1-8546-f460cbd1925b', 1, 'e52894de-a4d3-4bd7-ab96-a76ce594ff03')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('93ec17a2-c1e7-4226-b7ed-3c439fd95806', 'kuali.attribute.course.evaluation.indicator', '0a80670a-864b-46e1-8546-f460cbd1925b', 0, '50019511-a259-4753-8e50-18172d0b8d22')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f4134dba-0f89-4890-844b-641507b5ec02', 'kuali.attribute.wait.list.type.key', '0a80670a-864b-46e1-8546-f460cbd1925b', null, 'af349dd0-aca6-4266-beeb-1d54a326963a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('78d0de36-767c-45e1-815a-e5a53304dd01', 'kuali.attribute.course.number.internal.suffix', '0a80670a-864b-46e1-8546-f460cbd1925b', 'A', '97f31c6c-a8f0-4a31-882d-6f9ee29be915')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e4cb3e61-e224-4dee-b974-c7259ed4e32c', 'kuali.attribute.final.exam.indicator', '0a80670a-864b-46e1-8546-f460cbd1925b', 'STANDARD', '5d213cc2-b07e-40d5-8002-f6de2c067857')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('685c4214-670d-42b5-8060-00ccd63f0306', 'kuali.attribute.wait.list.level.type.key', '0a80670a-864b-46e1-8546-f460cbd1925b', 'AO', '4c2990f6-a1f7-44bd-9729-8f4c6b41f282')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8b2cc771-462e-48b4-8feb-cf591b19113b', 'kuali.attribute.wait.list.indicator', '0a80670a-864b-46e1-8546-f460cbd1925b', 1, 'd6459099-cb4a-4ac1-bd1d-e9b73b6d35cb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0656462e-82c1-4453-a1d2-932932323910', 'kuali.attribute.funding.source', '0a80670a-864b-46e1-8546-f460cbd1925b', '', 'ccc17e84-6898-4f5a-b346-a6d19174505a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('078d4f53-e85c-43b5-a266-2ed49c615528', 'kuali.attribute.final.exam.driver', '0a80670a-864b-46e1-8546-f460cbd1925b', 'kuali.lu.exam.driver.ActivityOffering', '3a466ecb-3a0f-46d2-8534-e8ab7c31cc21')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('048e152c-8ac2-4d95-a25f-dc8b8941d69b', 'kuali.attribute.where.fees.attached.flag', '0a80670a-864b-46e1-8546-f460cbd1925b', 0, 'c09edaa2-1c1b-4c21-a3db-5c9a909c3eb2')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('03dedd4b-9cfb-4cf9-825d-08ea3a488e55', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'HIST306', 'HIST', '', 'History of Religion in America', '0a80670a-864b-46e1-8546-f460cbd1925b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '2d5ca538-7837-45d6-b536-c35b3243fa0a')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('73d4c92b-4688-48a5-a34f-8684c5060ea6', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '0a80670a-864b-46e1-8546-f460cbd1925b', '', 'kuali.lu.code.honorsOffering', '', '1808516f-cbca-4dbf-ae3a-7c89fd87708f')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('0a80670a-864b-46e1-8546-f460cbd1925b', '3213375036', '15913b33-d3eb-4c73-9d50-3e47a0d827ef')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('0a80670a-864b-46e1-8546-f460cbd1925b', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0a80670a-864b-46e1-8546-f460cbd1925b', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0a80670a-864b-46e1-8546-f460cbd1925b', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0a80670a-864b-46e1-8546-f460cbd1925b', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0a80670a-864b-46e1-8546-f460cbd1925b', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ccce7101-73af-4681-b482-f177ea7db96f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'c0a7ccc8-be5f-404f-8e26-7fe95ef917b6', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '958687db-7677-45e3-b6a0-3202ab789f63')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('86bd66b7-971e-453e-ac15-60e014bd6913', 'kuali.attribute.grade.roster.level.type.key', '958687db-7677-45e3-b6a0-3202ab789f63', 'kuali.lui.type.activity.offering.lecture', '7b51740f-e8e7-464f-bce0-482f97e497cd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5c387da1-6211-4a24-8262-c53b2eb3d6e0', 'regCodePrefix', '958687db-7677-45e3-b6a0-3202ab789f63', '1', '7ab53ddb-9008-4fa3-8ef6-89079b158581')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0a72d71d-5dee-42e4-a740-fc4d55b02edf', 'kuali.attribute.final.exam.level.type', '958687db-7677-45e3-b6a0-3202ab789f63', 'kuali.lu.type.activity.Lecture', '715d3907-f89a-4db2-a980-025cdf0cfb0d')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('172845bb-4a09-4a9d-a7b8-03d3b4b6ef91', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '958687db-7677-45e3-b6a0-3202ab789f63', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ab6ae39b-a9b9-4a8b-bab5-624e6229f133')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('958687db-7677-45e3-b6a0-3202ab789f63', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('3c7a62ee-1bff-468e-b96e-92f83ef9e3e1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:52.367', '', 'HIST306-CO-FO', '0a80670a-864b-46e1-8546-f460cbd1925b', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST306-CO-FO', '958687db-7677-45e3-b6a0-3202ab789f63', 'faa91cfe-2544-4cac-995b-9a7a39f45e16')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d6d67f91-9f04-4467-80dc-099ab7287a4f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '9dfb4813-ad36-4549-9292-1375ddc7c8c1', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, '2d628e30-0bc8-4adb-82b6-4ee4a63138e7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e994d73c-03a5-4b5b-8aa4-db614a48ab04', 'kuali.attribute.course.evaluation.indicator', '2d628e30-0bc8-4adb-82b6-4ee4a63138e7', 0, 'c1d90b6d-2cd1-4cb8-b0cf-3dacbd7891e5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('129479c6-111c-4cf4-9094-0ab883d6a80d', 'kuali.attribute.max.enrollment.is.estimate', '2d628e30-0bc8-4adb-82b6-4ee4a63138e7', 0, '8a239223-5e59-44d1-a192-ceca8cc242f5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('39d7e212-0a38-4dd6-833c-e4fd7d56bb97', 'kuali.attribute.nonstd.ts.indicator', '2d628e30-0bc8-4adb-82b6-4ee4a63138e7', 0, '264a829a-ab28-4c01-aa08-d14005dcc42f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7d81945f-25b3-4669-afcd-67818f8a490c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '2d628e30-0bc8-4adb-82b6-4ee4a63138e7', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '480d16c0-3fcc-489b-9d0f-7a56fc033b9b')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('57f7b96e-43e8-4765-975e-84f037f51cf3', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '2d628e30-0bc8-4adb-82b6-4ee4a63138e7', '', 'kuali.lu.code.honorsOffering', 0, '7ced492e-da3a-4d95-bcf2-e5fa0058a532')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('a4aa6a88-d117-41df-9b5d-e3ccaf20baa1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:52.448', '', '', '2d628e30-0bc8-4adb-82b6-4ee4a63138e7', '', 'G.ERICK', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'd35736b8-13fa-413f-b43f-362bf101507d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('92f621e7-b2b1-4c47-960f-8e8e8d9562b4', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:52.54', '', 'HIST306-FO-AO', '958687db-7677-45e3-b6a0-3202ab789f63', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST306-FO-AO', '2d628e30-0bc8-4adb-82b6-4ee4a63138e7', '61c4d543-d2b2-4549-924b-4d6082c1814f')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('cfc5363c-482e-45a5-ac0b-681d7eb902fc', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for HIST306 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '5ce17729-a0c8-4b21-a0ee-c50ab0397582')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('3d5ecaf9-2a7c-43ac-89b9-a8502299e220', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for HIST306 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '5ce17729-a0c8-4b21-a0ee-c50ab0397582', '2629dc7d-a782-4f8b-9cb7-c21dd3ec9ef4')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('8e14bdbc-3af2-4fe7-a982-3d82cc73b501', 0, '2629dc7d-a782-4f8b-9cb7-c21dd3ec9ef4', '5109ffb2-bf7f-441f-a8e7-3b9d9ccdb29d')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('5ce17729-a0c8-4b21-a0ee-c50ab0397582', '2d628e30-0bc8-4adb-82b6-4ee4a63138e7')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('5109ffb2-bf7f-441f-a8e7-3b9d9ccdb29d', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('5109ffb2-bf7f-441f-a8e7-3b9d9ccdb29d', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('5109ffb2-bf7f-441f-a8e7-3b9d9ccdb29d', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('5109ffb2-bf7f-441f-a8e7-3b9d9ccdb29d', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('5362da57-8d4a-4af9-81a0-95ec25261e56', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'bf4887c6-21ac-4213-b491-27270601960b')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('e87e8ebe-befc-49b5-8ff7-2e61fa166559', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '1e064b5e-90b1-428f-b5d9-3b73d9c3c56c')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='5362da57-8d4a-4af9-81a0-95ec25261e56', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='2d628e30-0bc8-4adb-82b6-4ee4a63138e7', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='bf4887c6-21ac-4213-b491-27270601960b' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('1e064b5e-90b1-428f-b5d9-3b73d9c3c56c', '2d628e30-0bc8-4adb-82b6-4ee4a63138e7')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('1e064b5e-90b1-428f-b5d9-3b73d9c3c56c', '958687db-7677-45e3-b6a0-3202ab789f63')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('3b0620b9-315b-4555-8a53-b74894552ae9', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '958687db-7677-45e3-b6a0-3202ab789f63', 'CL 1', 'CL 1', '3bc8a50a-d053-4109-b73b-ec7a6551b098')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('e9129ed0-7e63-49c5-bf52-7b1292959094', 'kuali.lui.type.activity.offering.lecture', '3bc8a50a-d053-4109-b73b-ec7a6551b098', 'd78aa4f1-0434-4a5f-aaeb-2aa681d46163')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('d78aa4f1-0434-4a5f-aaeb-2aa681d46163', '2d628e30-0bc8-4adb-82b6-4ee4a63138e7')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='3bc8a50a-d053-4109-b73b-ec7a6551b098' where ID='d78aa4f1-0434-4a5f-aaeb-2aa681d46163'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('cb2fb2a0-261d-4197-b2b7-f6b4b8e0808f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'c0a7ccc8-be5f-404f-8e26-7fe95ef917b6', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'ab92b797-7de4-4c7c-b8e6-10f570f39494')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5f4f2d41-46f8-40fd-be2a-59c823167811', 'kuali.attribute.registration.group.aocluster.id', 'ab92b797-7de4-4c7c-b8e6-10f570f39494', '3bc8a50a-d053-4109-b73b-ec7a6551b098', '3d711cc2-eb44-4da8-a74c-8871c95db690')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5045543d-5da8-48a8-9eac-01a575ab7915', 'kuali.attribute.registration.group.is.generated', 'ab92b797-7de4-4c7c-b8e6-10f570f39494', 1, '302de2fe-9bc5-45dc-ac53-1d3b0e53b689')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('8d31c471-7a7b-4925-bff5-bc12f71b4b54', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'ab92b797-7de4-4c7c-b8e6-10f570f39494', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '25c1cdb0-4bdc-4dd9-b624-e16c6c52e78c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('815d79ec-5b86-42b9-b77a-30811418e1b0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:53.038', '', 'HIST306-FO-RG', '958687db-7677-45e3-b6a0-3202ab789f63', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST306-FO-RG', 'ab92b797-7de4-4c7c-b8e6-10f570f39494', '9800c99a-99b7-4b52-9a58-7cccc09eed1b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('62891437-908b-4420-898a-919897b947a2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:53.056', '', 'HIST306-RG-AO', 'ab92b797-7de4-4c7c-b8e6-10f570f39494', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST306-RG-AO', '2d628e30-0bc8-4adb-82b6-4ee4a63138e7', '94243fd6-d301-4a21-9868-c3b6064aba4c')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('7c077b5e-4da0-4f8a-91e4-e4bf7276abac', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'e9a4e168-0dfe-4e19-a88c-c9b9999e27d9', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Political and literary traditions that intersect in the fiction, poetry, and drama written in English by Caribbean writers, primarily during the 20th century.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL362 CO', 'Political and literary traditions that intersect in the fiction, poetry, and drama written in English by Caribbean writers, primarily during the 20th century.', null, '6e2f9333-05ab-4aea-9fed-da7581b6f135')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('335a7b03-9dfa-408c-bfa4-233f6972575a', 'kuali.attribute.wait.list.type.key', '6e2f9333-05ab-4aea-9fed-da7581b6f135', null, '468d6180-0024-4b06-97f6-1df7a5aa07a3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f19cbd34-fef7-4a93-93c6-ae9fa7299ec9', 'kuali.attribute.wait.list.indicator', '6e2f9333-05ab-4aea-9fed-da7581b6f135', 1, '9272eba2-18ff-4cff-a2b1-c6c618d9147d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('62b56834-2d19-41b8-b43f-455b46bbd72a', 'kuali.attribute.funding.source', '6e2f9333-05ab-4aea-9fed-da7581b6f135', '', 'b35239ef-6864-4f52-a083-540b74c5d520')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('52c5ba06-4d80-4873-a652-2694624e77bf', 'kuali.attribute.final.exam.indicator', '6e2f9333-05ab-4aea-9fed-da7581b6f135', 'STANDARD', '232c8e78-b1a4-4066-b3ef-5f82e031e41a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('101a6b77-49f2-4e96-85e1-2fb80f51c1c0', 'kuali.attribute.wait.list.level.type.key', '6e2f9333-05ab-4aea-9fed-da7581b6f135', 'AO', '8201a21c-0d1c-4a1f-85e3-143bce411256')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('97d2d190-5992-4f10-a1fb-4d5cf350da28', 'kuali.attribute.course.number.internal.suffix', '6e2f9333-05ab-4aea-9fed-da7581b6f135', 'A', '82dc1f21-4e20-44ec-82a4-5ba54d6a638c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4a345d33-5b29-4492-96da-0818a215d0d6', 'kuali.attribute.final.exam.use.matrix', '6e2f9333-05ab-4aea-9fed-da7581b6f135', 1, '9b6626d7-fa4a-4015-9337-aa08e82aa9a5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('83f46090-4583-44a8-90cf-f572ec343f1e', 'kuali.attribute.course.evaluation.indicator', '6e2f9333-05ab-4aea-9fed-da7581b6f135', 0, 'b10a07b3-ab64-41ba-858f-2a170436b6dd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('31dd8380-3270-41ad-af59-ebb91a7b5c48', 'kuali.attribute.where.fees.attached.flag', '6e2f9333-05ab-4aea-9fed-da7581b6f135', 0, 'cceb8ac5-d901-4641-ba40-d801a48b0a91')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4a160e08-a0c4-4f5c-a6b6-82a91d5853a2', 'kuali.attribute.final.exam.driver', '6e2f9333-05ab-4aea-9fed-da7581b6f135', 'kuali.lu.exam.driver.ActivityOffering', '554d874d-3fd3-4e58-8b77-4109dabd936a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('eea6944a-9b18-44e7-9369-deb22994aaa6', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'ENGL362', 'ENGL', '', 'Caribbean Literature in English', '6e2f9333-05ab-4aea-9fed-da7581b6f135', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '45257f67-7a1c-4a1a-9c65-49e1b6ae9f55')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('63b7477a-0c7a-46e5-ab1f-7fb54d76135c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '6e2f9333-05ab-4aea-9fed-da7581b6f135', '', 'kuali.lu.code.honorsOffering', '', 'a49fb3fa-6a01-449a-95f2-36bc3c306b36')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('6e2f9333-05ab-4aea-9fed-da7581b6f135', '2677933260', 'ae69b937-7cca-4d15-b61e-d5686c082e17')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('6e2f9333-05ab-4aea-9fed-da7581b6f135', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('6e2f9333-05ab-4aea-9fed-da7581b6f135', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('6e2f9333-05ab-4aea-9fed-da7581b6f135', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('6e2f9333-05ab-4aea-9fed-da7581b6f135', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('6e2f9333-05ab-4aea-9fed-da7581b6f135', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('f9fe3b9c-d884-4fce-82e7-0088d754448e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'e0246130-0245-420e-8771-ef8b6bebf6b0', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '91a1d5bc-992b-4ffd-884f-d003f237cddb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5fd78298-0865-4f5c-9e7a-9570bb4f5f7e', 'kuali.attribute.final.exam.level.type', '91a1d5bc-992b-4ffd-884f-d003f237cddb', 'kuali.lu.type.activity.Lecture', 'cab05273-91d2-4bff-a98c-ec0722904142')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('eb4a7cca-0e81-45aa-8b52-302ecc917de7', 'regCodePrefix', '91a1d5bc-992b-4ffd-884f-d003f237cddb', '1', '28adcf1b-d3e1-4801-acb6-c800040b1264')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0ffcbe7f-c7c8-4a22-94d3-f7e1b11f8d90', 'kuali.attribute.grade.roster.level.type.key', '91a1d5bc-992b-4ffd-884f-d003f237cddb', 'kuali.lui.type.activity.offering.lecture', 'e98f119b-126a-44ec-9515-522b0a02a37f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('f9862016-843a-4570-b914-c2befd5a0eec', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '91a1d5bc-992b-4ffd-884f-d003f237cddb', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'f19951dc-d3db-4468-a762-35012ae0c3a9')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('91a1d5bc-992b-4ffd-884f-d003f237cddb', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('95512f57-fd86-48de-9bcd-c78764166077', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:56.414', '', 'ENGL362-CO-FO', '6e2f9333-05ab-4aea-9fed-da7581b6f135', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL362-CO-FO', '91a1d5bc-992b-4ffd-884f-d003f237cddb', '02c12f6b-7da2-4010-842e-44cc70faba05')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('4e426cbb-bef4-410d-8850-23504ac230ca', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '193c61ab-4faf-403e-a3d8-dbea6af59cc6', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, 'ca7fb32a-fb14-4975-9810-69b0c62fa1a0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bc8d3f9f-47d3-4b3f-9637-8cab0cbf32fe', 'kuali.attribute.course.evaluation.indicator', 'ca7fb32a-fb14-4975-9810-69b0c62fa1a0', 0, '76d6c4e8-1b77-476b-947d-1a5ea02d88c4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2b5d7db6-282f-4b10-a698-6a6258b11101', 'kuali.attribute.max.enrollment.is.estimate', 'ca7fb32a-fb14-4975-9810-69b0c62fa1a0', 0, '4e3172dd-6693-4156-8b2a-8b4c40baaf02')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('146acf18-da88-4563-9e3f-903ad8bf75f0', 'kuali.attribute.nonstd.ts.indicator', 'ca7fb32a-fb14-4975-9810-69b0c62fa1a0', 0, '8e4ee789-aead-4aa8-b708-bd61f39c34af')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6ee33db7-f6f3-48b5-aa46-1fd14404506e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', 'ca7fb32a-fb14-4975-9810-69b0c62fa1a0', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'dba6e48a-500d-453b-9370-e3f250aad8ba')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('39a650ea-a7b0-43a5-9a1e-5ee3861a696d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'ca7fb32a-fb14-4975-9810-69b0c62fa1a0', '', 'kuali.lu.code.honorsOffering', 0, '90ada55c-1733-4d29-a848-a76f034ff5d1')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('46147aa3-1a84-44e4-8df5-c0af0087a27c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:56.554', '', '', 'ca7fb32a-fb14-4975-9810-69b0c62fa1a0', '', 'M.THEODOREK', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '02cbe0d0-d59f-4f46-95a7-232c98bd9fff')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('2c2c5671-1c38-48de-b66e-a7c9b60bde03', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:56.614', '', 'ENGL362-FO-AO', '91a1d5bc-992b-4ffd-884f-d003f237cddb', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL362-FO-AO', 'ca7fb32a-fb14-4975-9810-69b0c62fa1a0', '53fceb2f-ccc2-40bb-a1ad-4c49cad9a42b')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('e9621886-e15f-40ea-a24e-ee250c08716f', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for ENGL362 - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'b6d2a493-8421-4b58-b69c-6fc8ae3295b4')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('0a1ce506-b0b0-4198-ad1e-8a5a010073d7', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'Schedule request for ENGL362 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'b6d2a493-8421-4b58-b69c-6fc8ae3295b4', 'd450c62c-8d6c-4da0-8b05-0645dd66668f')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('a837fe5f-7990-46bc-b517-46b236a8eeee', 0, 'd450c62c-8d6c-4da0-8b05-0645dd66668f', '278388e4-d3b1-42a2-842a-0b50c7900877')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('b6d2a493-8421-4b58-b69c-6fc8ae3295b4', 'ca7fb32a-fb14-4975-9810-69b0c62fa1a0')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('278388e4-d3b1-42a2-842a-0b50c7900877', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('278388e4-d3b1-42a2-842a-0b50c7900877', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('278388e4-d3b1-42a2-842a-0b50c7900877', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('278388e4-d3b1-42a2-842a-0b50c7900877', 'F0AA72D6DADDF650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('aaf3a3e9-1070-4abc-8e45-1dda1f15c704', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '7ef45cfa-b792-47b8-a970-4424add2f330')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('414523de-15b4-4505-8fa9-5ae5d76464f0', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '00d76437-903c-47aa-a00a-52ca86c39c55')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='aaf3a3e9-1070-4abc-8e45-1dda1f15c704', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='ca7fb32a-fb14-4975-9810-69b0c62fa1a0', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='7ef45cfa-b792-47b8-a970-4424add2f330' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('00d76437-903c-47aa-a00a-52ca86c39c55', 'ca7fb32a-fb14-4975-9810-69b0c62fa1a0')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('00d76437-903c-47aa-a00a-52ca86c39c55', '91a1d5bc-992b-4ffd-884f-d003f237cddb')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('b47bd8bf-ae2f-42d7-9554-06ac93a4bb88', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '91a1d5bc-992b-4ffd-884f-d003f237cddb', 'CL 1', 'CL 1', '46a32be3-032d-4d47-91ea-712742783ec3')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('eef64bbf-8cd3-4e11-8161-41ed3f496005', 'kuali.lui.type.activity.offering.lecture', '46a32be3-032d-4d47-91ea-712742783ec3', 'bc984f48-32ae-4215-bcc7-9701a9d609ce')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('bc984f48-32ae-4215-bcc7-9701a9d609ce', 'ca7fb32a-fb14-4975-9810-69b0c62fa1a0')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='46a32be3-032d-4d47-91ea-712742783ec3' where ID='bc984f48-32ae-4215-bcc7-9701a9d609ce'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('fd692a10-cf80-4243-a0d4-a4236a19ccab', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'e0246130-0245-420e-8771-ef8b6bebf6b0', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '43084c36-33c1-4d11-81c4-7557b68013d2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f22ab332-ef7a-47c5-8dae-774f8a1ed876', 'kuali.attribute.registration.group.aocluster.id', '43084c36-33c1-4d11-81c4-7557b68013d2', '46a32be3-032d-4d47-91ea-712742783ec3', '16e7bc60-4ced-417a-b6db-19fc081324c9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f2596345-e187-43e3-8f78-c792fb77852e', 'kuali.attribute.registration.group.is.generated', '43084c36-33c1-4d11-81c4-7557b68013d2', 1, '835537e3-7273-4034-9fbd-f00ca7fe470e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('ae621c0c-5a86-4373-99f5-5707b95b9757', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', '43084c36-33c1-4d11-81c4-7557b68013d2', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '0ca96202-d5f0-4cf0-b8b7-0437d8f9915c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ca1ea01f-4d3a-4324-94f3-e3025c5ea562', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:57.112', '', 'ENGL362-FO-RG', '91a1d5bc-992b-4ffd-884f-d003f237cddb', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL362-FO-RG', '43084c36-33c1-4d11-81c4-7557b68013d2', 'e293fea4-e410-4348-9b13-c9f3fe234225')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('7b3a34f1-8613-45c0-a084-816361bce7d8', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:57.139', '', 'ENGL362-RG-AO', '43084c36-33c1-4d11-81c4-7557b68013d2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL362-RG-AO', 'ca7fb32a-fb14-4975-9810-69b0c62fa1a0', '50f0e7ca-9e0c-4b91-8755-6c36c2560838')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('401febd4-8caf-4396-a17e-0528eaad5859', '12fcf454-c6f4-4082-935e-69e65e738044', 'courseOfferingCode', 'HIST429G-P', '7e24eb9c-79c5-4777-ac1b-c07be256d653')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('0ca23c7a-55ed-4ee9-b837-ef01b204dabc', 'A', 'courseOfferingCode', 'HIST429G', '06f7157c-0972-4ba3-be01-c548eb03e943')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='12fcf454-c6f4-4082-935e-69e65e738044' and UNIQUE_KEY='HIST429G-P' and NAMESPACE='courseOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='HIST429G' and NAMESPACE='courseOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='HIST429G-P' and codegenera1_.NAMESPACE='courseOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('e78c0046-374f-4b9f-8dab-5979fd6bcb51', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'CLUID-HIST429-200801000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST429A CO', '.', null, '16726df6-1e6a-47c6-ad70-139c14fc3aed')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ea865dbf-24c7-4b47-8084-b513615f9082', 'kuali.attribute.where.fees.attached.flag', '16726df6-1e6a-47c6-ad70-139c14fc3aed', 0, '9986d327-15cd-46e5-8ba8-53d0cfc1f685')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9da58eee-bd28-439e-817b-96f1a89bfc1f', 'kuali.attribute.course.number.internal.suffix', '16726df6-1e6a-47c6-ad70-139c14fc3aed', 'A', 'da83a6d5-caba-4934-8c24-4e30d62d937d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('39ce26e5-cd92-44e6-a6dc-13f4aa172dca', 'kuali.attribute.wait.list.level.type.key', '16726df6-1e6a-47c6-ad70-139c14fc3aed', 'AO', '2de85cea-fbca-49f2-9631-028cefcecf7d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5db88854-bb91-47d0-9eb1-c7271c6b4dc2', 'kuali.attribute.wait.list.type.key', '16726df6-1e6a-47c6-ad70-139c14fc3aed', null, '7ae326b8-8242-4641-814f-a51e36df3170')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1d6a0b2f-d098-4088-a198-b5737fe3130c', 'kuali.attribute.final.exam.driver', '16726df6-1e6a-47c6-ad70-139c14fc3aed', 'kuali.lu.exam.driver.ActivityOffering', '38b0b8d3-a6c0-40f5-acf3-caa8aec4f329')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0a20f97e-275f-45a7-b475-c01a22ca3fd1', 'kuali.attribute.wait.list.indicator', '16726df6-1e6a-47c6-ad70-139c14fc3aed', 1, 'e12c0c98-c6f4-4527-87e7-4989b79b56b9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d91807f6-cc35-47d3-b727-869114feba61', 'kuali.attribute.course.evaluation.indicator', '16726df6-1e6a-47c6-ad70-139c14fc3aed', 0, '6874d0ce-17e4-419a-a782-73da9badc0d0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('222a1c67-3800-4553-8dae-b496700efe9b', 'kuali.attribute.funding.source', '16726df6-1e6a-47c6-ad70-139c14fc3aed', '', 'f35acd01-772b-42c1-9a3c-e61bbaabf213')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f3dff776-4a6c-4505-b203-00b035105ed5', 'kuali.attribute.final.exam.use.matrix', '16726df6-1e6a-47c6-ad70-139c14fc3aed', 1, '6a8cdbe1-c432-4145-8268-124662da89f3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('082e1b31-220c-4cfc-bb6b-f492b40ccac5', 'kuali.attribute.final.exam.indicator', '16726df6-1e6a-47c6-ad70-139c14fc3aed', 'STANDARD', '6c7f2926-064a-4040-8c1f-5662e6ab5856')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('8ad05bcd-571e-485a-83b3-74f098956baa', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'HIST429A', 'HIST', '', 'Special Topics in History', '16726df6-1e6a-47c6-ad70-139c14fc3aed', '', '', 'kuali.lui.identifier.state.active', 'A', 'kuali.lui.identifier.type.official', '', 'fc01902b-50dd-4b4b-a932-dfb0bfad8c2b')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('81a5a09a-788d-4b7b-af75-a827deef5f78', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '16726df6-1e6a-47c6-ad70-139c14fc3aed', '', 'kuali.lu.code.honorsOffering', '', '275f4335-14f0-48ba-9ae0-9ad76c762b6c')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('16726df6-1e6a-47c6-ad70-139c14fc3aed', '3213375036', '8b6ec121-b7f0-4329-9e15-16da136a4dad')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('16726df6-1e6a-47c6-ad70-139c14fc3aed', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('16726df6-1e6a-47c6-ad70-139c14fc3aed', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('16726df6-1e6a-47c6-ad70-139c14fc3aed', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('16726df6-1e6a-47c6-ad70-139c14fc3aed', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('16726df6-1e6a-47c6-ad70-139c14fc3aed', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('235e1645-9e9f-44c3-942e-ee72dbf6bc61', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '2acda501-2afd-4b9e-b08f-76d97c038024', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '1c233eeb-066f-4213-b70d-634bf544d2f4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1dac00b7-18c7-4d0b-a3dc-27905257de7a', 'kuali.attribute.grade.roster.level.type.key', '1c233eeb-066f-4213-b70d-634bf544d2f4', 'kuali.lui.type.activity.offering.lecture', '4a08dc3b-84d8-48e4-b06c-83a3585f9734')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('eb6e2205-3b37-41de-aec2-aebbefd68c68', 'kuali.attribute.final.exam.level.type', '1c233eeb-066f-4213-b70d-634bf544d2f4', 'kuali.lu.type.activity.Lecture', '1743994c-fce0-411c-84f5-49cbc014bfec')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0c50d924-2e27-4563-9f51-4954af274de9', 'regCodePrefix', '1c233eeb-066f-4213-b70d-634bf544d2f4', '1', '13e2381f-cdcc-48f9-a278-b455838d8929')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('8dc00173-f9e5-468c-a213-87dbfe7cb57c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', 'Lecture Only', '1c233eeb-066f-4213-b70d-634bf544d2f4', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '4ed7de4a-7a2b-46c6-9d4b-08c0f472f25c')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('1c233eeb-066f-4213-b70d-634bf544d2f4', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('de12bf13-c7af-4e4b-9585-0af89f712be3', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:59.388', '', 'HIST429A-CO-FO', '16726df6-1e6a-47c6-ad70-139c14fc3aed', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST429A-CO-FO', '1c233eeb-066f-4213-b70d-634bf544d2f4', 'fd179281-097f-4f02-9223-95c41009a9da')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('7dec0749-227b-41a1-8725-7c2b49cd21b2', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', 'ace106f6-cab1-4d92-96ee-00cca6d66e73', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, '59440874-680e-4cab-8ce6-e3b3f22f7758')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bfe56c3c-cace-472b-871f-9022f46db2fc', 'kuali.attribute.course.evaluation.indicator', '59440874-680e-4cab-8ce6-e3b3f22f7758', 0, '6e872719-0cb7-46b3-a3eb-d8a388357b83')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6395bd5d-c96b-4ad5-8d77-f4c9746e646c', 'kuali.attribute.nonstd.ts.indicator', '59440874-680e-4cab-8ce6-e3b3f22f7758', 0, '15822255-3ff0-4697-9efe-83a64f84fba5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d358bbaf-2ec6-4153-bce0-72c3028a4671', 'kuali.attribute.max.enrollment.is.estimate', '59440874-680e-4cab-8ce6-e3b3f22f7758', 0, 'f013dc40-2a6f-41eb-9fae-ccf0d2f191a4')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('674f5d4f-9678-47c3-93bf-7cf05b707e9c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A', '', '', '', '59440874-680e-4cab-8ce6-e3b3f22f7758', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '27b571d8-9bb6-4b64-b4ef-8e1844f92e14')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('af009fe3-10aa-46f5-aa23-b78ec2c43d64', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '59440874-680e-4cab-8ce6-e3b3f22f7758', '', 'kuali.lu.code.honorsOffering', 0, '315805dc-3da3-41cb-9972-b92fe21f46ba')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('9a661707-c8be-46ce-b3ed-f721967e56e1', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '100.00', '', TIMESTAMP '2014-01-23 20:02:59.489', '', '', '59440874-680e-4cab-8ce6-e3b3f22f7758', '', 'M.BONNIEL', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'e08b0a1e-911a-47ef-babc-0e6a9a997f0d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('163fdc32-0ddd-4513-803d-82d35b81113e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:59.53', '', 'HIST429A-FO-AO', '1c233eeb-066f-4213-b70d-634bf544d2f4', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST429A-FO-AO', '59440874-680e-4cab-8ce6-e3b3f22f7758', '7449ecdb-dd98-455f-a815-4140b2d36bae')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('c63c5828-72d8-4324-b5ef-68b1db06707a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 0, '', 'Schedule request set for HIST429A - A', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.set.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'd521e21e-2452-4c01-adc4-2e3963af0e61')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('7d4f703c-601b-424c-ad03-9c07001413aa', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'A Schedule Request for HIST429A section 0101', 'A Schedule Request for HIST429A section 0101', 'A Schedule Request for HIST429A section 0101', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', 'bf7b001f-fc61-4f06-8c85-80603f06bac5', 'd521e21e-2452-4c01-adc4-2e3963af0e61', '3bfbcf22-bc17-474a-a865-48d26782e5f6')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('f8b1b160-7cd1-4089-8f0b-3ae084f2e97a', 0, '3bfbcf22-bc17-474a-a865-48d26782e5f6', 'b2ea10dc-8e3a-40cd-abf1-4e244e5be069')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('d521e21e-2452-4c01-adc4-2e3963af0e61', '59440874-680e-4cab-8ce6-e3b3f22f7758')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('b2ea10dc-8e3a-40cd-abf1-4e244e5be069', 'af22fa59-42ab-4d09-9a5b-6980f8b4ae7d')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('b2ea10dc-8e3a-40cd-abf1-4e244e5be069', 'fdd1e716-7c99-4cc9-afbe-d8fa2e44ec66')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('b2ea10dc-8e3a-40cd-abf1-4e244e5be069', 'F0AA72D6DD71F650E040440A9B9A11B8')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('e340c404-098b-4308-ac2c-7508646ebbd5', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Athlete Seatpool', '1c2e7786-a2ab-4778-8e81-ea540c60b9b2', 1, 2, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '2fb7e5f7-b103-441d-b8a0-0717db597c5c')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('24bee530-0ec5-44c5-a348-017786135f09', 1, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Junior Seatpool', '4bae9f12-5901-4db9-b4a8-4dcc20781db5', 2, 4, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'a1e53e82-f6fa-4535-9bda-df70df778ed6')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='e340c404-098b-4308-ac2c-7508646ebbd5', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='59440874-680e-4cab-8ce6-e3b3f22f7758', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Athlete Seatpool', POPULATION_ID='1c2e7786-a2ab-4778-8e81-ea540c60b9b2', PROCESSING_PRIORITY=1, SEAT_LIMIT=2, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='2fb7e5f7-b103-441d-b8a0-0717db597c5c' and VER_NBR=1
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('6a044e16-8c6c-471f-9c25-30cfb329df1a', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'ebf5e90c-bb78-4701-a04e-ec95aad16c52')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='24bee530-0ec5-44c5-a348-017786135f09', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ACTIVITY_OFFERING_ID='59440874-680e-4cab-8ce6-e3b3f22f7758', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Junior Seatpool', POPULATION_ID='4bae9f12-5901-4db9-b4a8-4dcc20781db5', PROCESSING_PRIORITY=2, SEAT_LIMIT=4, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='a1e53e82-f6fa-4535-9bda-df70df778ed6' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('ebf5e90c-bb78-4701-a04e-ec95aad16c52', '59440874-680e-4cab-8ce6-e3b3f22f7758')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('ebf5e90c-bb78-4701-a04e-ec95aad16c52', '1c233eeb-066f-4213-b70d-634bf544d2f4')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('6ced06d7-6106-468b-bf05-3b57558e5c85', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '1c233eeb-066f-4213-b70d-634bf544d2f4', 'CL 1', 'CL 1', '2d918df3-0f2c-4862-a05c-f909a3e66f0c')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('0da28c85-6e12-4de4-80b4-e147b3bcd3eb', 'kuali.lui.type.activity.offering.lecture', '2d918df3-0f2c-4862-a05c-f909a3e66f0c', '8bc9e4dd-4ee9-4d6c-9e86-443553d6deb2')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('8bc9e4dd-4ee9-4d6c-9e86-443553d6deb2', '59440874-680e-4cab-8ce6-e3b3f22f7758')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='2d918df3-0f2c-4862-a05c-f909a3e66f0c' where ID='8bc9e4dd-4ee9-4d6c-9e86-443553d6deb2'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('305d4dfd-f154-449a-893c-d87b6de1612d', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'kuali.atp.2014Winter', '2acda501-2afd-4b9e-b08f-76d97c038024', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'a71d4f0c-73ea-44a7-ad79-118003a8e440')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('90704edf-7e10-47aa-91c1-b064204fa830', 'kuali.attribute.registration.group.aocluster.id', 'a71d4f0c-73ea-44a7-ad79-118003a8e440', '2d918df3-0f2c-4862-a05c-f909a3e66f0c', '350bf495-cedc-40fb-8930-3f3e28077e2a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7a39794a-77a3-45fa-9edb-77941b585800', 'kuali.attribute.registration.group.is.generated', 'a71d4f0c-73ea-44a7-ad79-118003a8e440', 1, '912a411c-c2d3-40b0-90da-1cdc4162d980')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('f72377fe-136a-44a5-adb4-0e3a0e79f37b', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '', '', 'a71d4f0c-73ea-44a7-ad79-118003a8e440', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'bf7ec4fa-24f9-4f80-9407-4b0224dd06c5')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('3721162e-3ce4-4519-89a5-3f68525e6858', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:59.995', '', 'HIST429A-FO-RG', '1c233eeb-066f-4213-b70d-634bf544d2f4', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST429A-FO-RG', 'a71d4f0c-73ea-44a7-ad79-118003a8e440', 'd0e216bf-2abc-4deb-a16c-f3304fb77814')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ff446cad-48aa-4061-bd51-41561aa94593', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', TIMESTAMP '2014-01-23 20:02:59.997', '', 'HIST429A-RG-AO', 'a71d4f0c-73ea-44a7-ad79-118003a8e440', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST429A-RG-AO', '59440874-680e-4cab-8ce6-e3b3f22f7758', 'bfa71b19-11eb-4bb5-aa24-98486f4a946d')
/
update KSEN_SOC_ROR set OBJ_ID='971d59cc-eb7c-4504-ac92-ec51f651c326', VER_NBR=7, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ITEMS_EXPECTED=48, ITEMS_PROCESSED=34, MESG_FORMATTED='', MESG_PLAIN='', SOC_ROR_STATE='kuali.soc.rollover.state.running', SOC_ROR_TYPE='kuali.soc.rollover.results.type.rollover', SOURCE_SOC_ID='03192a24-765a-4b9d-b638-5158430e09c8', TARGET_SOC_ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', TARGET_TERM_ID='kuali.atp.2014Winter' where ID='5b88a612-d027-429f-a9f3-b4241ed736c7' and VER_NBR=6
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='e2dae705-59ea-4cea-a1b8-97963890ed4e', ATTR_KEY='kuali.soc.rollover.result.dynattr.date.completed', OWNER_ID='5b88a612-d027-429f-a9f3-b4241ed736c7', ATTR_VALUE='2014 Jan 23 20:03:01 America/New_York' where ID='289b7901-b122-4239-94fd-e4dd1e0066cf'
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('7be42a9f-c18c-4306-9bb6-442309982c7e', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'd8bb13c5-c97f-4df3-97d2-5e945631129c', '10164c43-4d2d-438f-a763-9508f786968c', '1a7700c9-494e-458c-aafd-d21b0ac6f9a1')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b2f5860d-7fe8-4c79-a9c1-dcbfc23eeb66', 'activityOfferingsCreated', '1a7700c9-494e-458c-aafd-d21b0ac6f9a1', '1', '5dc1c648-cd12-426a-bbca-8dde4d36a151')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('07d3ba62-745a-49a4-9d26-3bdcb67fc881', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'ebd67086-fe4d-4454-a715-92175e293fcc', '0a80670a-864b-46e1-8546-f460cbd1925b', 'd235266f-0932-4518-ab24-7f0e5d4e67b4')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ad4b81c8-6700-45ff-a502-3fdec8799ea5', 'activityOfferingsCreated', 'd235266f-0932-4518-ab24-7f0e5d4e67b4', '1', 'b5a922b9-fda5-46d8-8926-689a78eec61c')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('8cdcac32-d2f8-42fd-8e03-244beb0c707c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'edac0ed5-af7d-4241-ac2a-80e0f7fe70a2', '', 'dd220b5c-411c-4dd3-a34b-ef641d5ec35c')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('6087ff4d-9475-4bf1-b6ec-54e8ebeb46c5', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'efc7d4f5-12b8-4cf4-8ab2-18dc3b8b75c6', '6e2f9333-05ab-4aea-9fed-da7581b6f135', '5129561d-821e-4cda-860c-8ffc715c053f')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ebf98eba-c4d6-4d13-92c7-e630bbbe7430', 'activityOfferingsCreated', '5129561d-821e-4cda-860c-8ffc715c053f', '1', '20cc136f-be59-4042-a099-0cf9bdc9c575')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('dd60a39e-2d05-4f16-a881-bfcff3263d38', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', '', '', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'f8e0de1e-fdc9-4a1f-bcad-9ba3d31459c3', '16726df6-1e6a-47c6-ad70-139c14fc3aed', '3f773971-1619-4bf5-8d09-478b0cfc6d6f')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d18001bb-b2ea-417b-84d9-5780b22a0e8c', 'activityOfferingsCreated', '3f773971-1619-4bf5-8d09-478b0cfc6d6f', '1', 'cc943788-406a-4185-b537-c6101fb12193')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('c04cabde-ea87-40d9-8c9b-59193b1b7ef9', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'f970d440-1f3c-408a-bce3-73c20bfdff97', '', 'bd776ca6-c846-4132-8a58-69fd3e0defaa')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('9040b0eb-180b-4d5a-99f1-1930d39f746c', 0, 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'admin', TIMESTAMP '2014-01-23 20:00:30.052', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '5b88a612-d027-429f-a9f3-b4241ed736c7', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'fef218cf-70cd-4dfb-9d54-6a8bc6a7dbc4', '', '0524e20c-0b13-4d65-9535-1bd1db249b2f')
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='f67f498e-dc02-4303-9f4e-b16daa8a55ae', ATTR_KEY='kuali.soc.rollover.result.dynattr.course.offerings.skipped', OWNER_ID='5b88a612-d027-429f-a9f3-b4241ed736c7', ATTR_VALUE='14' where ID='48c198c5-8435-4e5c-8f4c-6c3456086b72'
/
update KSEN_SOC_ROR set OBJ_ID='971d59cc-eb7c-4504-ac92-ec51f651c326', VER_NBR=8, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:00:30.052', ITEMS_EXPECTED=48, ITEMS_PROCESSED=34, MESG_FORMATTED='', MESG_PLAIN='', SOC_ROR_STATE='kuali.soc.rollover.state.finished', SOC_ROR_TYPE='kuali.soc.rollover.results.type.rollover', SOURCE_SOC_ID='03192a24-765a-4b9d-b638-5158430e09c8', TARGET_SOC_ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', TARGET_TERM_ID='kuali.atp.2014Winter' where ID='5b88a612-d027-429f-a9f3-b4241ed736c7' and VER_NBR=7
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='8abd863d-e848-4b63-b7ef-0432d6bc93b0', ATTR_KEY='kuali.soc.rollover.result.dynattr.course.offerings.created', OWNER_ID='5b88a612-d027-429f-a9f3-b4241ed736c7', ATTR_VALUE='34' where ID='c930c107-524f-46cb-97d3-5704d68f5d66'
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='e729a305-7459-483c-9d8c-e1f31d60029e', ATTR_KEY='kuali.soc.rollover.result.dynattr.activity.offerings.created', OWNER_ID='5b88a612-d027-429f-a9f3-b4241ed736c7', ATTR_VALUE='43' where ID='adbeabec-721f-44c5-b814-d94179ba6da9'
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7d15d943-0603-43f4-886d-b1fd64778d1a', 'kuali.soc.state.open', '59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', '2014-01-23T20:05:27.717-0500', '55839704-ed81-42ca-9325-e3760f12371a')
/
update KSEN_SOC set OBJ_ID='181AD498-CF85-43A3-AC46-EA023C8751F9', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:05:27.717', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.open', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2014Winter', UNITS_CONTENT_OWNER_ID='' where ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0' and VER_NBR=0
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('acd28b67-fc79-430a-878a-91a3023c99a0', 'kuali.soc.state.locked', '59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', '2014-01-23T20:07:11.593-0500', 'f544001a-bbb3-44cb-90a1-e71813f1236a')
/
update KSEN_SOC set OBJ_ID='181AD498-CF85-43A3-AC46-EA023C8751F9', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:07:11.593', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.locked', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2014Winter', UNITS_CONTENT_OWNER_ID='' where ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0' and VER_NBR=1
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e788bb6f-120c-4a9a-b5b5-7a936a459d37', 'kuali.soc.scheduling.state.inprogress', '59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', '2014-01-23T20:07:22.607-0500', 'f19c2ac4-97ca-4e68-8181-279070e08e61')
/
update KSEN_SOC set OBJ_ID='181AD498-CF85-43A3-AC46-EA023C8751F9', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:07:22.607', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.locked', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2014Winter', UNITS_CONTENT_OWNER_ID='' where ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0' and VER_NBR=2
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bc5b7c43-c57b-42e1-8612-e5ba2437339c', 'kuali.soc.scheduling.state.completed', '59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', '2014-01-23T20:07:28.076-0500', 'de0c3f77-98ad-45de-9046-961406fa96aa')
/
update KSEN_SOC set OBJ_ID='181AD498-CF85-43A3-AC46-EA023C8751F9', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:07:28.076', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.locked', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2014Winter', UNITS_CONTENT_OWNER_ID='' where ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0' and VER_NBR=3
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('beb98f22-d90f-4746-807e-0529bb631c07', 'kuali.soc.state.finaledits', '59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', '2014-01-23T20:09:55.541-0500', '93cbc755-99f8-4dbf-9496-4a88f431f63b')
/
update KSEN_SOC set OBJ_ID='181AD498-CF85-43A3-AC46-EA023C8751F9', VER_NBR=5, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:09:55.541', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.finaledits', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2014Winter', UNITS_CONTENT_OWNER_ID='' where ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0' and VER_NBR=4
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fad3aac3-6ba9-4b66-8178-a7558c9e0459', 'kuali.soc.state.publishing', '59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', '2014-01-23T20:10:10.887-0500', 'f95407d3-9945-4adb-8856-5acc46ac7d29')
/
update KSEN_SOC set OBJ_ID='181AD498-CF85-43A3-AC46-EA023C8751F9', VER_NBR=6, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:10:10.887', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.publishing', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2014Winter', UNITS_CONTENT_OWNER_ID='' where ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0' and VER_NBR=5
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bfd31810-a15d-4352-82a0-0dd40d9f28a3', 'kuali.soc.state.published', '59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', '2014-01-23T20:10:13.084-0500', 'e6946773-8a93-4ae8-b329-a68f418088bb')
/
update KSEN_SOC set OBJ_ID='181AD498-CF85-43A3-AC46-EA023C8751F9', VER_NBR=7, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-23 20:10:13.084', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.published', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2014Winter', UNITS_CONTENT_OWNER_ID='' where ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0' and VER_NBR=6
/
