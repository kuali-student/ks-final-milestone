insert into KSEN_SOC_ROR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ITEMS_EXPECTED, ITEMS_PROCESSED, MESG_FORMATTED, MESG_PLAIN, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_SOC_ID, TARGET_SOC_ID, TARGET_TERM_ID, ID) values ('086ca850-8a66-4720-b0d4-cf7f5955aab1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'kuali.soc.rollover.state.submitted', 'kuali.soc.rollover.results.type.rollover', '03192a24-765a-4b9d-b638-5158430e09c8', '59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', 'kuali.atp.2014Winter', '8eb89914-1b01-4d50-af48-dab273b4554d')
/
insert into KSEN_SOC_ROR_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('aa22addb-51dc-4b4b-9ab6-19af629aa47d', 'kuali.soc.rollover.result.dynattr.date.completed', '8eb89914-1b01-4d50-af48-dab273b4554d', '2014 Jan 22 12:49:24 America/New_York', '401f9b67-014c-4a31-b85e-67ae4de802be')
/
insert into KSEN_SOC_ROR_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('001343af-8ae2-4bef-84aa-0f46a39280f6', 'kuali.soc.rollover.result.dynattr.course.offerings.created', '8eb89914-1b01-4d50-af48-dab273b4554d', '0', 'c3a496ed-8b78-447a-94a8-5843e064427e')
/
insert into KSEN_SOC_ROR_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4b4d72f9-54d1-4bf1-80f5-101940449c72', 'kuali.soc.rollover.result.dynattr.activity.offerings.created', '8eb89914-1b01-4d50-af48-dab273b4554d', '0', '529895cd-f5e9-4656-a4f9-ce81ab8fc373')
/
insert into KSEN_SOC_ROR_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('acb959e8-25c4-472b-a3d8-8ed5c65a486f', 'kuali.soc.rollover.result.dynattr.activity.offerings.skipped', '8eb89914-1b01-4d50-af48-dab273b4554d', '0', '2da75d2f-41c4-46ee-b4a9-ec76c6196fe7')
/
insert into KSEN_SOC_ROR_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0a86e475-40e4-4b2b-8019-e3006f04b230', 'kuali.soc.rollover.result.dynattr.course.offerings.skipped', '8eb89914-1b01-4d50-af48-dab273b4554d', '0', '79cd13a5-7639-4590-8682-e29980587eaa')
/
insert into KSEN_SOC_ROR_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('38a4e033-eba2-45d4-be4c-32ae60bcda20', 'kuali.soc.rollover.result.dynattr.date.initiated', '8eb89914-1b01-4d50-af48-dab273b4554d', '2014 Jan 22 12:49:24 America/New_York', '26a5fd2a-25e3-49e2-ad74-d9c9a81744de')
/
insert into KSEN_SOC_ROR_OPTION (OPTION_ID, ROR_ID, ID) values ('kuali.rollover.whatcourses.ifnonewversion', '8eb89914-1b01-4d50-af48-dab273b4554d', 'd2503a95-6929-463a-987d-4618725afd11')
/
insert into KSEN_SOC_ROR_OPTION (OPTION_ID, ROR_ID, ID) values ('kuali.rollover.option.targetterm.validated', '8eb89914-1b01-4d50-af48-dab273b4554d', '0c82f428-04fa-49ba-a3f3-905cea4300d3')
/
insert into KSEN_SOC_ROR_OPTION (OPTION_ID, ROR_ID, ID) values ('kuali.rollover.processing.log.successes', '8eb89914-1b01-4d50-af48-dab273b4554d', '9b941920-1c92-4cc6-bf00-2cbd1986e708')
/
update KSEN_SOC_ROR set OBJ_ID='086ca850-8a66-4720-b0d4-cf7f5955aab1', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ITEMS_EXPECTED='', ITEMS_PROCESSED='', MESG_FORMATTED='', MESG_PLAIN='', SOC_ROR_STATE='kuali.soc.rollover.state.running', SOC_ROR_TYPE='kuali.soc.rollover.results.type.rollover', SOURCE_SOC_ID='03192a24-765a-4b9d-b638-5158430e09c8', TARGET_SOC_ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', TARGET_TERM_ID='kuali.atp.2014Winter' where ID='8eb89914-1b01-4d50-af48-dab273b4554d' and VER_NBR=0
/
update KSEN_SOC_ROR set OBJ_ID='086ca850-8a66-4720-b0d4-cf7f5955aab1', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ITEMS_EXPECTED=48, ITEMS_PROCESSED=0, MESG_FORMATTED='', MESG_PLAIN='', SOC_ROR_STATE='kuali.soc.rollover.state.running', SOC_ROR_TYPE='kuali.soc.rollover.results.type.rollover', SOURCE_SOC_ID='03192a24-765a-4b9d-b638-5158430e09c8', TARGET_SOC_ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', TARGET_TERM_ID='kuali.atp.2014Winter' where ID='8eb89914-1b01-4d50-af48-dab273b4554d' and VER_NBR=1
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('09722652-f06c-4e0e-bf37-aca48bef4e9e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'CLUID-CHEM640-198708000000', TIMESTAMP '2014-01-01 19:00:00.0', '', 'A tutorial type course dealing with the basic description of the fundamentals of writing organic reaction mechanisms.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'CHEM640 CO', 'A tutorial type course dealing with the basic description of the fundamentals of writing organic reaction mechanisms.', null, '1085a09a-a085-4d3b-95b8-6786502a84e4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1720df9f-a82b-4b2b-9a59-c02d35068abc', 'kuali.attribute.course.evaluation.indicator', '1085a09a-a085-4d3b-95b8-6786502a84e4', 0, 'a516b933-ed80-4924-9196-c26985cc8e48')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6004ef20-5b96-4c6c-8f27-2df8e576c24c', 'kuali.attribute.wait.list.level.type.key', '1085a09a-a085-4d3b-95b8-6786502a84e4', 'AO', '2967787b-d865-4d04-87cc-1a13a2154720')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('53bcd6d2-d0b1-4c8c-aa4d-a997bbffbb3c', 'kuali.attribute.wait.list.type.key', '1085a09a-a085-4d3b-95b8-6786502a84e4', null, 'eb657fd3-5791-41fa-97c3-fab3e40f021a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('25891c4a-8c45-4605-b001-a8cbca8a795b', 'kuali.attribute.final.exam.driver', '1085a09a-a085-4d3b-95b8-6786502a84e4', 'kuali.lu.exam.driver.ActivityOffering', 'da6f4e73-d862-45dd-9ba7-c6f5621f987b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1905b420-0aa3-4ba0-898a-367fe3db860e', 'kuali.attribute.where.fees.attached.flag', '1085a09a-a085-4d3b-95b8-6786502a84e4', 0, 'b05914b2-e4f7-4406-9847-35f51375e78c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7858faf8-1903-46fc-ac6d-ed20c0be3054', 'kuali.attribute.course.number.internal.suffix', '1085a09a-a085-4d3b-95b8-6786502a84e4', 'A', '9bdb5477-a461-4791-8c46-d93a5af87c62')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('43af6af0-d7fd-4af4-8fd6-1847e53bd998', 'kuali.attribute.wait.list.indicator', '1085a09a-a085-4d3b-95b8-6786502a84e4', 1, '37ca5d19-c9c5-41c8-81b7-cc80d62d8519')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('93f4b31b-114d-42e1-bc5b-7c8be42bcfc9', 'kuali.attribute.funding.source', '1085a09a-a085-4d3b-95b8-6786502a84e4', '', '1690cf7f-229f-4217-ad28-e402bee171f9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('02ba0d5a-5426-4f20-872f-c968a382afdf', 'kuali.attribute.final.exam.indicator', '1085a09a-a085-4d3b-95b8-6786502a84e4', 'STANDARD', '0a7a92de-7b81-4364-8a9c-a586c6ee23f6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0a68005f-b044-4f75-bdd4-a1316f3b2428', 'kuali.attribute.final.exam.use.matrix', '1085a09a-a085-4d3b-95b8-6786502a84e4', 1, '3ce1ff71-4735-4bde-bcc3-23dfa7f0bbde')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('759bb47d-298b-42e6-b7f4-8c2b955120df', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'CHEM640', 'CHEM', '', 'Problems in Organic Reaction Mechanisms', '1085a09a-a085-4d3b-95b8-6786502a84e4', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '9e48b82f-30ca-452a-8a11-e15a561687bf')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('6863493c-b03d-49c8-b861-fea9b5a0f9bd', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '1085a09a-a085-4d3b-95b8-6786502a84e4', '', 'kuali.lu.code.honorsOffering', '', '7ebd5734-db61-475a-8f68-4d6e75a8d8c7')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('1085a09a-a085-4d3b-95b8-6786502a84e4', '4284516367', '93234c43-887d-48b4-975a-b4367131dcef')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('1085a09a-a085-4d3b-95b8-6786502a84e4', '4284516367')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('1085a09a-a085-4d3b-95b8-6786502a84e4', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('1085a09a-a085-4d3b-95b8-6786502a84e4', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('1085a09a-a085-4d3b-95b8-6786502a84e4', 'kuali.creditType.credit.degree.1.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('dcccd447-c8b2-4633-ad14-72b0d6cea501', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '46d57760-5695-452e-9789-e0ecb8704e76', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '75d01c41-7066-4a94-8e58-3fc5435b195a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('69200a33-20c9-491f-bc39-29dedeec4a0b', 'kuali.attribute.grade.roster.level.type.key', '75d01c41-7066-4a94-8e58-3fc5435b195a', 'kuali.lui.type.activity.offering.lecture', 'f5258b48-18ea-4a6e-abeb-dac2e71b5388')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2b301bf3-e12f-4828-84a7-7a428626035d', 'kuali.attribute.final.exam.level.type', '75d01c41-7066-4a94-8e58-3fc5435b195a', 'kuali.lu.type.activity.Lecture', '20c9fb61-1b66-4d87-b505-221818cd5283')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('42364ab7-ba7f-4c65-933b-341ba5c597eb', 'regCodePrefix', '75d01c41-7066-4a94-8e58-3fc5435b195a', '1', 'ee728cf6-a121-46a0-8c30-bb8cc7205c74')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('3a2638a0-da0a-4dbb-af7f-c21d52fd01fc', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '75d01c41-7066-4a94-8e58-3fc5435b195a', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'f278862c-7820-4d48-a126-7e94ec054412')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('2496ce29-5a88-46dd-9136-d55ae2ef50b7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:30.513', '', 'CHEM640-CO-FO', '1085a09a-a085-4d3b-95b8-6786502a84e4', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'CHEM640-CO-FO', '75d01c41-7066-4a94-8e58-3fc5435b195a', '418b0d01-c4f7-416d-b6b9-e8b40dba418c')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('75d01c41-7066-4a94-8e58-3fc5435b195a', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('6755f9c3-3af9-4baf-a70a-c73cfca3b2a8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '0843b8ef-6b97-4c97-a26b-11cbfbad831f', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 30, 30, 'Lecture', 'Courses with lecture', null, '4d54b132-1aa3-43c4-bd2d-2ca09cbc6760')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('eb10b138-c31c-4641-a729-4e0ba1fa2674', 'kuali.attribute.course.evaluation.indicator', '4d54b132-1aa3-43c4-bd2d-2ca09cbc6760', 0, 'a0447085-ca0f-46c0-889f-d5f70bd12649')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8459123e-5ed0-42ff-8e6c-657a5778e207', 'kuali.attribute.nonstd.ts.indicator', '4d54b132-1aa3-43c4-bd2d-2ca09cbc6760', 0, '7886d427-135f-4be0-bf34-fe12f8615e94')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3dd70d68-14b5-4466-a935-08ffc5af83a5', 'kuali.attribute.max.enrollment.is.estimate', '4d54b132-1aa3-43c4-bd2d-2ca09cbc6760', 0, 'c2226648-427f-49ee-a17e-72d688540442')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('f266c4d1-2125-4646-bdd3-a5b6b4077475', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '4d54b132-1aa3-43c4-bd2d-2ca09cbc6760', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '0857d7ec-8b33-4284-ab6c-1f7ba7a89a53')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('25b698e2-a0d0-4b9c-8391-d4abe4fa0636', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '4d54b132-1aa3-43c4-bd2d-2ca09cbc6760', '', 'kuali.lu.code.honorsOffering', 0, '9ab6a9bf-4620-4b06-9a51-5ff4db1ddc6c')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('d2026cf1-9635-45b6-b56d-0b07872603db', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:49:30.768', '', '4d54b132-1aa3-43c4-bd2d-2ca09cbc6760', 'R.JULIEP', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '109357b2-9bb8-4e94-9db5-646b906c1d11')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('779e04a8-b58a-4fae-b145-b3d6007bbfb7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:30.769', '', 'CHEM640-FO-AO', '75d01c41-7066-4a94-8e58-3fc5435b195a', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM640-FO-AO', '4d54b132-1aa3-43c4-bd2d-2ca09cbc6760', '0be3d50e-ea03-438c-944a-822eb719cadb')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('33ce129b-74c4-4d33-a7c9-497952cac958', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '23124bba-f14e-4ff2-8fd6-b4ee9212d289')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('29f36538-458a-4bd3-a4bc-18c512bf396f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for CHEM640 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '23124bba-f14e-4ff2-8fd6-b4ee9212d289', '0866b1af-71ca-4570-9098-1cdb517f7a4b')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('e0567755-53d0-4db0-868f-365d4e8eba1d', 0, '0866b1af-71ca-4570-9098-1cdb517f7a4b', '773704e2-265b-4d95-9a49-18eed299d260')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('4049ebbd-b6c7-41ca-a747-238d3a07165a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '6c6346ec-947a-47fa-b8b7-0a10cf620356')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('4259e3ab-ae29-44e1-9656-33b6b897b786', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '75d01c41-7066-4a94-8e58-3fc5435b195a', 'CL 1', 'CL 1', '119dd643-aae2-4149-82ca-965c55f54e3f')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('07b59dcd-8fab-4a59-a6e3-d8dfa9bd7a6d', 'kuali.lui.type.activity.offering.lecture', '119dd643-aae2-4149-82ca-965c55f54e3f', '47a542e7-99a0-4f72-a864-8bf712e56ab6')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('23124bba-f14e-4ff2-8fd6-b4ee9212d289', '4d54b132-1aa3-43c4-bd2d-2ca09cbc6760')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('773704e2-265b-4d95-9a49-18eed299d260', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('773704e2-265b-4d95-9a49-18eed299d260', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('773704e2-265b-4d95-9a49-18eed299d260', '2246d609-9fbb-4d06-b45e-a57ef80539b7')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('773704e2-265b-4d95-9a49-18eed299d260', 'F08200F10671315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('6c6346ec-947a-47fa-b8b7-0a10cf620356', '4d54b132-1aa3-43c4-bd2d-2ca09cbc6760')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('6c6346ec-947a-47fa-b8b7-0a10cf620356', '75d01c41-7066-4a94-8e58-3fc5435b195a')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('47a542e7-99a0-4f72-a864-8bf712e56ab6', '4d54b132-1aa3-43c4-bd2d-2ca09cbc6760')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='119dd643-aae2-4149-82ca-965c55f54e3f' where ID='47a542e7-99a0-4f72-a864-8bf712e56ab6'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('5ca6e4fd-8f8f-4225-9499-f119ee75f3c1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '46d57760-5695-452e-9789-e0ecb8704e76', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '7763cd58-1328-42b9-8f40-a8acdb60aeb5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('912496a3-ee75-46a8-ab0d-6abaa9a7b543', 'kuali.attribute.registration.group.aocluster.id', '7763cd58-1328-42b9-8f40-a8acdb60aeb5', '119dd643-aae2-4149-82ca-965c55f54e3f', 'ff65f2da-6b6b-4854-9dbb-c44accc53ee6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fcfad93d-c432-4ad5-94dd-1a6ecc417692', 'kuali.attribute.registration.group.is.generated', '7763cd58-1328-42b9-8f40-a8acdb60aeb5', 1, '43aa6c64-e34a-49b3-b46b-aaa2038bd9b0')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c32f5b57-249f-4c9a-9936-69a4b1a39839', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', '7763cd58-1328-42b9-8f40-a8acdb60aeb5', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '080216c3-599e-4753-8c7d-afb74f199ca7')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('bac6b430-d33a-425a-bc17-cbe54b1eeee0', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:32.887', '', 'CHEM640-FO-RG', '75d01c41-7066-4a94-8e58-3fc5435b195a', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM640-FO-RG', '7763cd58-1328-42b9-8f40-a8acdb60aeb5', '6e681f3d-dd6b-4600-9b57-adbf8c940d03')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('2c6c6a61-3e94-45e4-b8a1-90df7877c074', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:32.888', '', 'CHEM640-RG-AO', '7763cd58-1328-42b9-8f40-a8acdb60aeb5', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM640-RG-AO', '4d54b132-1aa3-43c4-bd2d-2ca09cbc6760', '4e4948cf-c751-46b8-a51e-0c8655a0d5ae')
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='aa22addb-51dc-4b4b-9ab6-19af629aa47d', ATTR_KEY='kuali.soc.rollover.result.dynattr.date.completed', OWNER_ID='8eb89914-1b01-4d50-af48-dab273b4554d', ATTR_VALUE='2014 Jan 22 12:49:33 America/New_York' where ID='401f9b67-014c-4a31-b85e-67ae4de802be'
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('5f00b270-c61a-4e56-8c0b-d478fd9e6921', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '0567769d-f680-464d-8f9b-adb118a53696', '1085a09a-a085-4d3b-95b8-6786502a84e4', 'f30f7cc2-d287-4e05-a90d-7b515db64813')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d13b1550-5d03-4145-9dd2-064f4761fe43', 'activityOfferingsCreated', 'f30f7cc2-d287-4e05-a90d-7b515db64813', '1', 'd308fceb-f3b8-4270-a429-eb49bd47c9dd')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('368c425a-c969-4a3d-a0ec-326a42f15616', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'CLUID-CHEM277-200608000000', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Quantitative analysis, inorganic analytical chemistry, and an introduction to bio-analytical instrumentation and techniques.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'CHEM277 CO', 'Quantitative analysis, inorganic analytical chemistry, and an introduction to bio-analytical instrumentation and techniques.', null, '07eb8950-9ec6-406d-9574-962fdb747196')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('58966534-4be3-481a-900a-3b551ae33f3c', 'kuali.attribute.where.fees.attached.flag', '07eb8950-9ec6-406d-9574-962fdb747196', 0, 'fa49c01d-0d43-44fa-a22c-35709b53ff97')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5c981f75-07c4-487c-b43d-a6203086d8bb', 'kuali.attribute.course.number.internal.suffix', '07eb8950-9ec6-406d-9574-962fdb747196', 'A', 'b3007979-14fb-4daf-8a1c-eb8505535a5f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('585e7fa4-7492-4d8f-8f60-7c9802e779f0', 'kuali.attribute.course.evaluation.indicator', '07eb8950-9ec6-406d-9574-962fdb747196', 0, 'ac4fa59e-2ca3-4c26-b9d2-136a49790b08')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('00d92661-5c26-4723-8800-0bfe77b989cf', 'kuali.attribute.wait.list.type.key', '07eb8950-9ec6-406d-9574-962fdb747196', null, '0c526e05-240a-425e-b265-16ba5ce55891')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('29ea297d-ee91-4dbd-b214-19b7faacd05c', 'kuali.attribute.wait.list.indicator', '07eb8950-9ec6-406d-9574-962fdb747196', 1, '23f78f24-4552-401a-9c93-10323b773ba8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ad83aa9e-b523-47a4-8b79-9ef84860744f', 'kuali.attribute.final.exam.indicator', '07eb8950-9ec6-406d-9574-962fdb747196', 'STANDARD', '65ec5b7d-7ca9-4605-b893-42c5946d3c67')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2da7dc35-9415-475a-96a9-693b96c2bedd', 'kuali.attribute.final.exam.driver', '07eb8950-9ec6-406d-9574-962fdb747196', 'kuali.lu.exam.driver.ActivityOffering', '473eb2c0-555d-4339-ae93-69af24d138c1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('60fb00cc-be88-4da8-ad64-60124413bf6b', 'kuali.attribute.final.exam.use.matrix', '07eb8950-9ec6-406d-9574-962fdb747196', 1, '30b20439-d74c-458a-9735-b095ff87af06')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c404080b-8d17-4398-b088-2be6ce4171b4', 'kuali.attribute.wait.list.level.type.key', '07eb8950-9ec6-406d-9574-962fdb747196', 'AO', '96535860-d3ee-463d-9dc0-70ca27829f36')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fb624f5a-4bf9-44a9-8180-1677fef58f01', 'kuali.attribute.funding.source', '07eb8950-9ec6-406d-9574-962fdb747196', '', 'f2f2958d-d08b-428f-8d11-c59615bb6b91')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('87ed19c1-d5a7-412a-bb0b-657d8e9957f7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'CHEM277', 'CHEM', '', 'Fundamentals of Analytical and Bioanalytical Chemistry Laboratory', '07eb8950-9ec6-406d-9574-962fdb747196', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '0e6f5f88-0753-4b52-93c1-5a5bfc6256dd')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('71304b98-c2b1-4ade-83d6-4099a15d612a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '07eb8950-9ec6-406d-9574-962fdb747196', '', 'kuali.lu.code.honorsOffering', '', '7822514e-f965-417f-a423-a069a55a2243')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('07eb8950-9ec6-406d-9574-962fdb747196', '4284516367', '0a83afe0-ac1d-43f9-b0fd-01e61d4fb1b2')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('07eb8950-9ec6-406d-9574-962fdb747196', '4284516367')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('07eb8950-9ec6-406d-9574-962fdb747196', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('07eb8950-9ec6-406d-9574-962fdb747196', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('96b92da2-a07d-4cef-b2da-e0a55e170ae1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '25889d4f-0612-424c-87a5-e96f60f3e096', '', '', 'Courses with Lecture/Lab', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture/Lab', 'Courses with Lecture/Lab', '', '792b2c3d-0b9d-4214-813b-cf882c9b74f3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a32852a4-4cad-49a6-94f4-121335398700', 'regCodePrefix', '792b2c3d-0b9d-4214-813b-cf882c9b74f3', '1', '057fa203-0d50-48e4-b314-d9ac859e4af7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7eff464f-ed66-4449-bd52-bc46f624150b', 'kuali.attribute.final.exam.level.type', '792b2c3d-0b9d-4214-813b-cf882c9b74f3', 'kuali.lu.type.activity.Lecture', '9470d8b3-f142-4e25-b490-7909b3611be2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dd09731d-3d44-4da3-bdd9-58bd9a16a9a3', 'kuali.attribute.grade.roster.level.type.key', '792b2c3d-0b9d-4214-813b-cf882c9b74f3', 'kuali.lui.type.activity.offering.lab', 'dedb0d36-67b6-429f-9cf3-82e89997d7df')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('ee9b8933-79d9-4426-b425-b551f40def3e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture/Lab', '792b2c3d-0b9d-4214-813b-cf882c9b74f3', '', 'LEC/LAB', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'edc728f0-c43b-4b5f-82d9-71fee2dd7443')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('d51afb8f-f707-48d1-9428-254c0940fc8b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:37.607', '', 'CHEM277-CO-FO', '07eb8950-9ec6-406d-9574-962fdb747196', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'CHEM277-CO-FO', '792b2c3d-0b9d-4214-813b-cf882c9b74f3', 'd243ad99-3005-4923-9583-f98f990467dc')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('792b2c3d-0b9d-4214-813b-cf882c9b74f3', 'kuali.lui.type.activity.offering.lab')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('792b2c3d-0b9d-4214-813b-cf882c9b74f3', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('096b9649-1908-497c-85b6-c93775404f2c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'ef2963d7-9647-4ce7-ac63-9523204dd654', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 48, 48, 'Lecture', 'Courses with lecture', null, '4f865a71-cbeb-4ab2-96d0-951a8f76bdde')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b86b6b28-e887-4e5e-8b96-120e8d524a98', 'kuali.attribute.course.evaluation.indicator', '4f865a71-cbeb-4ab2-96d0-951a8f76bdde', 0, '378c1583-127e-4a5d-b25f-ae0a7e0a8f0f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a265ba75-64cd-42e4-9562-2dbe1ba20269', 'kuali.attribute.max.enrollment.is.estimate', '4f865a71-cbeb-4ab2-96d0-951a8f76bdde', 0, '01ef8f6a-5078-458f-8066-08874fd85f4b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fb4e613c-707d-42b7-abe7-2d4069010edf', 'kuali.attribute.nonstd.ts.indicator', '4f865a71-cbeb-4ab2-96d0-951a8f76bdde', 0, '08bc2dbc-48bc-4764-89f2-0e3b5f3e0dc6')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('9270eb1d-aa77-418f-98d7-bfec7039a0fb', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '4f865a71-cbeb-4ab2-96d0-951a8f76bdde', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '83dbb1ec-6b76-47e2-b2df-fce9385f8be2')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('c4272877-2880-4c57-9832-fd1d58a2d46a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '4f865a71-cbeb-4ab2-96d0-951a8f76bdde', '', 'kuali.lu.code.honorsOffering', 0, '259eed22-c839-4af7-bc6a-ee3cb8db6267')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('991f18f5-341c-4666-b90a-5115e9c406e6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:49:37.745', '', '4f865a71-cbeb-4ab2-96d0-951a8f76bdde', 'W.TIMOTHYC', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '4a934137-006a-4d77-991d-a1febd4ae49d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('d0d75f41-3b0e-4f1f-8743-c3a063fc73aa', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:37.746', '', 'CHEM277-FO-AO', '792b2c3d-0b9d-4214-813b-cf882c9b74f3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM277-FO-AO', '4f865a71-cbeb-4ab2-96d0-951a8f76bdde', '28497d9a-26b2-4cbd-b6ac-ec2ee13f1549')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('6d0f2cf1-a0f3-49ac-b516-da76d2d53477', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '882b7435-31b1-4d08-9bb3-c6651087a312')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('96f0efde-dfd7-428b-8c6b-af614aa3e9ee', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for CHEM277 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '882b7435-31b1-4d08-9bb3-c6651087a312', '12d05d59-deba-4907-b390-75aaf9f1da9f')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('f503f524-0894-4997-b25e-8db23e3bfdc9', 0, '12d05d59-deba-4907-b390-75aaf9f1da9f', '15b2d2fc-6718-4d02-8480-3da7e3f1ab47')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('0a0a8a21-d16d-4770-9220-ad8e9c39862e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, '', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '1cab3840-a457-4546-9322-f64a443606f7')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('e6d46bf6-bd83-4546-89d0-9a66a455c638', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '9b0d7d87-31ec-498b-bfe4-31a4f1e92021', '', '', 'Courses with lab', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lab', 20, 24, 'Lab', 'Courses with lab', null, '8f8f0364-d2a7-4167-98c2-b6704263264f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('be5d40d6-fbfc-44e3-8904-2d917c3cefbe', 'kuali.attribute.nonstd.ts.indicator', '8f8f0364-d2a7-4167-98c2-b6704263264f', 0, 'e7dc16cd-dc56-4b8a-bbd6-90162a14851e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cda30da2-f5db-453c-8c29-51999841c2da', 'kuali.attribute.course.evaluation.indicator', '8f8f0364-d2a7-4167-98c2-b6704263264f', 0, '4a2ab770-bee6-48a8-8f76-c7a518df8348')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a671128c-0f1d-4637-b803-7f5e875da337', 'kuali.attribute.max.enrollment.is.estimate', '8f8f0364-d2a7-4167-98c2-b6704263264f', 0, '7da0e9a5-0939-4031-8281-ed5b25ad8b3b')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6a64b4f6-90ad-4077-a166-1fe750761c9b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'B', '', '', '', '8f8f0364-d2a7-4167-98c2-b6704263264f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '90d9e3ea-17d3-47c9-b22f-f8e47bf511df')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('7ca20726-95a7-45e3-b11b-47607b0673ff', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '8f8f0364-d2a7-4167-98c2-b6704263264f', '', 'kuali.lu.code.honorsOffering', 0, '16c6a050-23d6-473b-8dca-8fa38d0a907c')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('681349b0-d8a0-4c49-a217-4052c6dc4827', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:49:38.065', '', '8f8f0364-d2a7-4167-98c2-b6704263264f', 'J.JRH', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'ba38da8c-5b24-423e-b61e-b65cc2ec7b1f')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a1f71e8c-ccdb-44ba-b00a-2e684290b0f4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:38.065', '', 'CHEM277-FO-AO', '792b2c3d-0b9d-4214-813b-cf882c9b74f3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM277-FO-AO', '8f8f0364-d2a7-4167-98c2-b6704263264f', 'f04e8931-2633-43e4-a321-d921e6016836')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('882b7435-31b1-4d08-9bb3-c6651087a312', '4f865a71-cbeb-4ab2-96d0-951a8f76bdde')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('15b2d2fc-6718-4d02-8480-3da7e3f1ab47', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('15b2d2fc-6718-4d02-8480-3da7e3f1ab47', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('15b2d2fc-6718-4d02-8480-3da7e3f1ab47', 'a8ce091f-4b9d-4f52-ae15-d2eaf275d76a')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('15b2d2fc-6718-4d02-8480-3da7e3f1ab47', 'F08200F10745315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('1cab3840-a457-4546-9322-f64a443606f7', '4f865a71-cbeb-4ab2-96d0-951a8f76bdde')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('1cab3840-a457-4546-9322-f64a443606f7', '792b2c3d-0b9d-4214-813b-cf882c9b74f3')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('4521ead9-039f-41db-a8b7-4f9fcdc02e79', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'dcc3be7c-eb2d-477c-acd6-6b4c6fe5fc2a')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('e4e360bb-470f-4aed-8934-349306f2d010', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for CHEM277 - B', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'dcc3be7c-eb2d-477c-acd6-6b4c6fe5fc2a', 'a3b9ed78-9ffa-404c-bcef-b8e2dff4da17')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('fe2e1f1b-f7ef-4446-be27-7c94cb10759e', 0, 'a3b9ed78-9ffa-404c-bcef-b8e2dff4da17', '938b5d3f-1a7a-4280-bfda-147b948defb3')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('fc90014f-7fea-47d6-9c95-cffc507e3a2d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, '', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'fad4a1a3-224b-4155-b138-456e3a061fc8')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('a2a56259-b6d1-478a-95d8-495bca35a6ce', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '6138a014-70d6-4716-a817-d096d4c79371', '', '', 'Courses with lab', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lab', 24, 24, 'Lab', 'Courses with lab', null, '7c589a1a-ecc9-4ecb-a260-559e20618e1e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('109cadcd-a489-4877-9727-0760290f8783', 'kuali.attribute.course.evaluation.indicator', '7c589a1a-ecc9-4ecb-a260-559e20618e1e', 0, 'e9ea4a8b-8fb3-48e7-9cfd-2e1100ec7bd4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('591f8064-47df-4274-9cf6-d8d022549dcf', 'kuali.attribute.max.enrollment.is.estimate', '7c589a1a-ecc9-4ecb-a260-559e20618e1e', 0, '07dca61b-213d-4412-9ef8-c1f966843a5f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b239540f-458c-45f3-a265-6f8362a1cbd6', 'kuali.attribute.nonstd.ts.indicator', '7c589a1a-ecc9-4ecb-a260-559e20618e1e', 0, 'eea62e8b-6d28-4250-aa7b-b09550ef354b')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c6cfe337-5f31-47ef-934f-215661c38940', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'C', '', '', '', '7c589a1a-ecc9-4ecb-a260-559e20618e1e', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '91020908-328b-4302-bb25-88481adfc037')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('df92e5d4-74f3-4eab-a288-dd85ddd760ad', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '7c589a1a-ecc9-4ecb-a260-559e20618e1e', '', 'kuali.lu.code.honorsOffering', 0, '87e9663e-da33-46d6-a158-ba2ed5d01787')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('d85b31fa-1caf-4e37-9a6e-802ad5cd42f6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:49:38.598', '', '7c589a1a-ecc9-4ecb-a260-559e20618e1e', 'C.PHILIPPES', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'f50c8195-756a-4805-ab56-f98ed2052557')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('eacfba2d-bf0d-45a9-ac54-d8391eddc987', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:38.598', '', 'CHEM277-FO-AO', '792b2c3d-0b9d-4214-813b-cf882c9b74f3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM277-FO-AO', '7c589a1a-ecc9-4ecb-a260-559e20618e1e', 'cb829225-8252-486c-bb25-f11d263577df')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('dcc3be7c-eb2d-477c-acd6-6b4c6fe5fc2a', '8f8f0364-d2a7-4167-98c2-b6704263264f')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('938b5d3f-1a7a-4280-bfda-147b948defb3', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('938b5d3f-1a7a-4280-bfda-147b948defb3', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('938b5d3f-1a7a-4280-bfda-147b948defb3', '5fcaeba7-a010-40dc-a069-568c197f0809')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('938b5d3f-1a7a-4280-bfda-147b948defb3', 'F08200F10623315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('fad4a1a3-224b-4155-b138-456e3a061fc8', '8f8f0364-d2a7-4167-98c2-b6704263264f')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('fad4a1a3-224b-4155-b138-456e3a061fc8', '792b2c3d-0b9d-4214-813b-cf882c9b74f3')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('1775f878-43a2-4ba8-87bb-01b8dc3bf8a1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'fc0b483d-a5bb-40b4-b576-1920d4fea866')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('360080cf-185f-4a4a-9d39-7913ff31cf16', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for CHEM277 - C', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'fc0b483d-a5bb-40b4-b576-1920d4fea866', 'f0264fae-1cf7-482c-8356-10e6c1dc4f1d')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('55fd582c-a3f9-4ccd-b669-1d202d27ca82', 0, 'f0264fae-1cf7-482c-8356-10e6c1dc4f1d', 'c382a24e-9ab1-4c58-8e28-f7a119a44ff5')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('fed7b95d-5812-48b7-a549-c7a185cf544d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, '', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '3de6afe6-a0b7-4f32-aae5-c13d600a5234')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('55314685-e9c1-48da-9af8-7591ad307523', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'ef2963d7-9647-4ce7-ac63-9523204dd654', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 48, 48, 'Lecture', 'Courses with lecture', null, '888f7c63-1cc2-454e-b795-87806b2c6e90')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8ef89334-d7ea-443e-9450-17f140c481bf', 'kuali.attribute.course.evaluation.indicator', '888f7c63-1cc2-454e-b795-87806b2c6e90', 0, 'ee3cf41d-0d35-42e9-9dce-486eb10a67cc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4d53c85c-01ea-41c0-b47a-06a079593f3a', 'kuali.attribute.max.enrollment.is.estimate', '888f7c63-1cc2-454e-b795-87806b2c6e90', 0, 'ba08372f-619c-4367-a186-e99057c71c1e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f84ba23a-fcf5-471b-98ae-15ff3568b5f4', 'kuali.attribute.nonstd.ts.indicator', '888f7c63-1cc2-454e-b795-87806b2c6e90', 0, '99fc604d-2446-4d37-aa5c-a172b757b2b4')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('f9edfaba-eddb-4d3b-bf0f-9c01fd169daf', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'D', '', '', '', '888f7c63-1cc2-454e-b795-87806b2c6e90', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '76c66ed4-4c74-43a6-a2a2-01ef18a62b6d')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('fd0001fc-641a-4584-9c60-e805ad79e0ba', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '888f7c63-1cc2-454e-b795-87806b2c6e90', '', 'kuali.lu.code.honorsOffering', 0, 'bcb8098c-5d57-4f58-bd40-032c19eabea8')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('2dc5a6a8-d40b-4555-9e50-7eb85b89b4dd', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:49:39.065', '', '888f7c63-1cc2-454e-b795-87806b2c6e90', 'W.TIMOTHYC', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '6aa74caa-cbe8-4e79-afa7-02675449294b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('8de6aac2-161d-4691-8402-504ed803e63a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:39.065', '', 'CHEM277-FO-AO', '792b2c3d-0b9d-4214-813b-cf882c9b74f3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM277-FO-AO', '888f7c63-1cc2-454e-b795-87806b2c6e90', 'acdadccb-815f-48ad-93ed-7e5349339240')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('fc0b483d-a5bb-40b4-b576-1920d4fea866', '7c589a1a-ecc9-4ecb-a260-559e20618e1e')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('c382a24e-9ab1-4c58-8e28-f7a119a44ff5', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('c382a24e-9ab1-4c58-8e28-f7a119a44ff5', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('c382a24e-9ab1-4c58-8e28-f7a119a44ff5', '5fcaeba7-a010-40dc-a069-568c197f0809')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('c382a24e-9ab1-4c58-8e28-f7a119a44ff5', 'F08200F106F0315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('3de6afe6-a0b7-4f32-aae5-c13d600a5234', '7c589a1a-ecc9-4ecb-a260-559e20618e1e')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('3de6afe6-a0b7-4f32-aae5-c13d600a5234', '792b2c3d-0b9d-4214-813b-cf882c9b74f3')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('fb8619de-5a31-4256-930e-82ecd2b10680', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '0fa80438-128d-4378-aed7-342a1ade988a')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('80aad1a6-83cb-4774-add2-92742c6f52fe', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for CHEM277 - D', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '0fa80438-128d-4378-aed7-342a1ade988a', '63274444-e72b-4e28-bf9a-797eec06e63a')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('27fa1bf5-fc2b-4309-997a-02f1da7e04df', 0, '63274444-e72b-4e28-bf9a-797eec06e63a', '00fff4c3-f1d9-4b9e-bb2e-976af6715747')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('05f29db1-6145-45ae-9add-bc9ce9729c67', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, '', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'eb653889-e0d4-448b-b405-97725329bc30')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('52644954-9b63-4620-8690-70653a03cf37', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '9b0d7d87-31ec-498b-bfe4-31a4f1e92021', '', '', 'Courses with lab', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lab', 24, 24, 'Lab', 'Courses with lab', null, 'b59f9751-3e99-486a-a449-62d923254580')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('06842e47-4b64-49f9-89bd-406fcf64d375', 'kuali.attribute.nonstd.ts.indicator', 'b59f9751-3e99-486a-a449-62d923254580', 0, '5c67dadd-cf15-4d6b-a05e-ece359bbeacf')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('51848c85-41ca-4770-abf4-c4e7fe8b8e5b', 'kuali.attribute.max.enrollment.is.estimate', 'b59f9751-3e99-486a-a449-62d923254580', 0, '9e03921e-8524-4b88-885d-1bdbd9b27f58')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('55e4cbe1-c05d-4aee-9781-45b42a0a9f7e', 'kuali.attribute.course.evaluation.indicator', 'b59f9751-3e99-486a-a449-62d923254580', 0, '973dd0f2-e0ef-4fd4-910d-a2b6d6281efe')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('9e7160ac-1c6f-4fc7-8f1e-c24829eb611c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'E', '', '', '', 'b59f9751-3e99-486a-a449-62d923254580', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'e3c6de4e-4889-4fd7-9fd1-23e18ffca0fa')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('f881e869-46bc-4333-afce-9db90a7d3968', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'b59f9751-3e99-486a-a449-62d923254580', '', 'kuali.lu.code.honorsOffering', 0, '9d678176-adb1-4bad-b1f5-ca5067c133cc')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('404d7a7d-4c2d-4940-a23e-ee6c7f4ced48', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:49:39.614', '', 'b59f9751-3e99-486a-a449-62d923254580', 'J.JRH', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '46f68c26-dd0b-40b7-84ff-843a0e330153')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('10b1343c-5099-4461-8f87-47763f857663', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:39.615', '', 'CHEM277-FO-AO', '792b2c3d-0b9d-4214-813b-cf882c9b74f3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM277-FO-AO', 'b59f9751-3e99-486a-a449-62d923254580', '5feff32b-5f80-4f21-85d3-f22296ed0783')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('0fa80438-128d-4378-aed7-342a1ade988a', '888f7c63-1cc2-454e-b795-87806b2c6e90')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('00fff4c3-f1d9-4b9e-bb2e-976af6715747', '000134ee-051b-403a-940e-eff45c3a987b')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('00fff4c3-f1d9-4b9e-bb2e-976af6715747', 'F08200F10799315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('eb653889-e0d4-448b-b405-97725329bc30', '888f7c63-1cc2-454e-b795-87806b2c6e90')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('eb653889-e0d4-448b-b405-97725329bc30', '792b2c3d-0b9d-4214-813b-cf882c9b74f3')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('80aab60a-eb25-4362-bff6-30a05cc12b92', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'fe9b317a-9bd9-444a-823a-6a96e468a1da')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('560c3c6e-cb28-4b5c-a88d-00eafa23f8aa', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for CHEM277 - E', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'fe9b317a-9bd9-444a-823a-6a96e468a1da', '50f95e83-9700-4c8f-a743-27b015c691be')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('75b7d8ab-542b-4217-bf4e-698e14303eb5', 0, '50f95e83-9700-4c8f-a743-27b015c691be', 'c1cd6c74-d1e0-415d-b6b2-3dc2f5bd7cb9')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('bd4e922c-b8bf-4a4a-907d-6dc34d1c3b2b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, '', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '761ecad2-9c9d-4348-8677-f6d0410014a0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('6905fe52-ae5c-4c16-b7b2-9f45613e9b74', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '6138a014-70d6-4716-a817-d096d4c79371', '', '', 'Courses with lab', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lab', 24, 24, 'Lab', 'Courses with lab', null, 'baf29f8e-7024-47d5-96c8-c0d36de0e5ef')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ebe9c5ed-80ae-456d-aa97-60feb0240da5', 'kuali.attribute.max.enrollment.is.estimate', 'baf29f8e-7024-47d5-96c8-c0d36de0e5ef', 0, 'dc402f20-3f27-4081-84b9-9c8331336757')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('223da8e8-f449-462b-9375-9ab457ab4364', 'kuali.attribute.course.evaluation.indicator', 'baf29f8e-7024-47d5-96c8-c0d36de0e5ef', 0, 'c4f388b0-642e-45c9-84da-be40da60aaed')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fac9e666-5dda-492b-afe3-8402be3407ef', 'kuali.attribute.nonstd.ts.indicator', 'baf29f8e-7024-47d5-96c8-c0d36de0e5ef', 0, '41c139a7-3c33-43d2-8aea-d8872b072f2f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('3e17923d-715f-40cf-ac71-e24574e00b90', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'F', '', '', '', 'baf29f8e-7024-47d5-96c8-c0d36de0e5ef', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7b6862c9-5cba-49cf-bf77-928f0c216d3a')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('5405dbcd-8a31-4833-bf2a-b833a3611548', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'baf29f8e-7024-47d5-96c8-c0d36de0e5ef', '', 'kuali.lu.code.honorsOffering', 0, '712fa495-1842-4664-a48a-866509f7542b')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('771b387e-00d4-427a-b88e-0ff3699e61a5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:49:40.021', '', 'baf29f8e-7024-47d5-96c8-c0d36de0e5ef', 'J.JRH', 'kuali.lpr.state.tentative', 'kuali.lpr.type.instructor.main', '2c64d1e1-1178-49b2-bfa8-852a49159339')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('eb185d6a-c439-497d-acc0-190179186291', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:40.022', '', 'CHEM277-FO-AO', '792b2c3d-0b9d-4214-813b-cf882c9b74f3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM277-FO-AO', 'baf29f8e-7024-47d5-96c8-c0d36de0e5ef', '1c50b23f-c797-40bc-9e65-102902abb280')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('fe9b317a-9bd9-444a-823a-6a96e468a1da', 'b59f9751-3e99-486a-a449-62d923254580')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('c1cd6c74-d1e0-415d-b6b2-3dc2f5bd7cb9', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('c1cd6c74-d1e0-415d-b6b2-3dc2f5bd7cb9', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('c1cd6c74-d1e0-415d-b6b2-3dc2f5bd7cb9', '5fcaeba7-a010-40dc-a069-568c197f0809')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('c1cd6c74-d1e0-415d-b6b2-3dc2f5bd7cb9', 'F08200F10623315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('761ecad2-9c9d-4348-8677-f6d0410014a0', 'b59f9751-3e99-486a-a449-62d923254580')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('761ecad2-9c9d-4348-8677-f6d0410014a0', '792b2c3d-0b9d-4214-813b-cf882c9b74f3')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('14f83e61-300d-4dcd-892b-e3db6b8f027e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '831d7464-6242-44ba-9893-b669e472dc17')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('f1193111-bae6-48f7-8ba3-bd379a6ee3f7', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for CHEM277 - F', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '831d7464-6242-44ba-9893-b669e472dc17', 'eeda6053-2f7b-4b0b-97af-a0bbc55ca146')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('f38ac2b8-e9c1-41bf-a7df-95dbde7ce9a6', 0, 'eeda6053-2f7b-4b0b-97af-a0bbc55ca146', '9f2f4cf9-0062-493e-8dd2-47ce5373b82b')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('a04f3184-84ac-4fc7-8546-bd8f0fff1074', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, '', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '7f2fe59c-94ec-4a4c-84b1-404fa456c471')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('bbace946-bf3a-4d29-a54f-7a9e16b1a09c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '792b2c3d-0b9d-4214-813b-cf882c9b74f3', 'CL 1', 'CL 1', '4ee0cfa9-7577-421e-ab3f-edfb54658c6c')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('0dc8a03f-d15f-4004-bcd3-74811ebed58a', 'kuali.lui.type.activity.offering.lab', '4ee0cfa9-7577-421e-ab3f-edfb54658c6c', '3d70290e-e1e5-4bf9-8da7-d1c1eacaa1d3')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('e92129fd-c22b-48fc-ac26-19eb8da39296', 'kuali.lui.type.activity.offering.lecture', '4ee0cfa9-7577-421e-ab3f-edfb54658c6c', 'e403fe6a-35a9-4285-9e0a-92cd65cb2af3')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('831d7464-6242-44ba-9893-b669e472dc17', 'baf29f8e-7024-47d5-96c8-c0d36de0e5ef')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('9f2f4cf9-0062-493e-8dd2-47ce5373b82b', '5fcaeba7-a010-40dc-a069-568c197f0809')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('9f2f4cf9-0062-493e-8dd2-47ce5373b82b', 'F08200F10623315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('7f2fe59c-94ec-4a4c-84b1-404fa456c471', 'baf29f8e-7024-47d5-96c8-c0d36de0e5ef')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('7f2fe59c-94ec-4a4c-84b1-404fa456c471', '792b2c3d-0b9d-4214-813b-cf882c9b74f3')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('3d70290e-e1e5-4bf9-8da7-d1c1eacaa1d3', '8f8f0364-d2a7-4167-98c2-b6704263264f')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('3d70290e-e1e5-4bf9-8da7-d1c1eacaa1d3', '7c589a1a-ecc9-4ecb-a260-559e20618e1e')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('e403fe6a-35a9-4285-9e0a-92cd65cb2af3', '4f865a71-cbeb-4ab2-96d0-951a8f76bdde')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='4ee0cfa9-7577-421e-ab3f-edfb54658c6c' where ID='3d70290e-e1e5-4bf9-8da7-d1c1eacaa1d3'
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='4ee0cfa9-7577-421e-ab3f-edfb54658c6c' where ID='e403fe6a-35a9-4285-9e0a-92cd65cb2af3'
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('db9108dd-3150-4b95-a510-70b1ace786c3', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '792b2c3d-0b9d-4214-813b-cf882c9b74f3', 'CL 1 Conflict', 'CL 1 Conflict', '86a6abbf-eb21-474a-a949-9afa3329fe58')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('5b3f84c5-c2cb-4c86-b7e6-8f686d553466', 'kuali.lui.type.activity.offering.lecture', '86a6abbf-eb21-474a-a949-9afa3329fe58', '740fb063-2dc0-449d-a8ca-02014034f79b')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('78d27fa3-5f5b-4890-91ca-67536adb2c25', 'kuali.lui.type.activity.offering.lab', '86a6abbf-eb21-474a-a949-9afa3329fe58', 'b1c81e1c-c5bd-424a-a10e-9490db0f0be4')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('740fb063-2dc0-449d-a8ca-02014034f79b', '888f7c63-1cc2-454e-b795-87806b2c6e90')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('b1c81e1c-c5bd-424a-a10e-9490db0f0be4', 'b59f9751-3e99-486a-a449-62d923254580')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('b1c81e1c-c5bd-424a-a10e-9490db0f0be4', 'baf29f8e-7024-47d5-96c8-c0d36de0e5ef')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='86a6abbf-eb21-474a-a949-9afa3329fe58' where ID='740fb063-2dc0-449d-a8ca-02014034f79b'
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='86a6abbf-eb21-474a-a949-9afa3329fe58' where ID='b1c81e1c-c5bd-424a-a10e-9490db0f0be4'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('f419960f-1860-46b0-80ff-f805148bb6db', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '25889d4f-0612-424c-87a5-e96f60f3e096', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'ab6550d8-4da4-4b39-8a38-514fffce2849')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('01fd318d-609a-4282-aa2e-a835b897aec3', 'kuali.attribute.registration.group.aocluster.id', 'ab6550d8-4da4-4b39-8a38-514fffce2849', '4ee0cfa9-7577-421e-ab3f-edfb54658c6c', '12bf4b7c-ed1e-41e1-9de9-306c8a63ff38')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b96f39b3-3dd6-44e7-b074-ec281918fbc0', 'kuali.attribute.registration.group.is.generated', 'ab6550d8-4da4-4b39-8a38-514fffce2849', 1, 'bbaeb9c1-e7cf-4a97-8e7c-b747012e9ede')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('2015a7ea-177f-44c0-9a2f-0d33749a8211', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'ab6550d8-4da4-4b39-8a38-514fffce2849', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '85363987-91b1-40e1-80af-b122ab35f3bb')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('553c3337-cfe0-41f8-84e1-d6ee7818241a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:40.793', '', 'CHEM277-FO-RG', '792b2c3d-0b9d-4214-813b-cf882c9b74f3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM277-FO-RG', 'ab6550d8-4da4-4b39-8a38-514fffce2849', '70664ed0-f72a-4a09-9023-0a9093a54d9d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('38a16fca-e9c0-4d06-b303-2ff596ad2992', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:40.794', '', 'CHEM277-RG-AO', 'ab6550d8-4da4-4b39-8a38-514fffce2849', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM277-RG-AO', '4f865a71-cbeb-4ab2-96d0-951a8f76bdde', 'f163a355-c227-4ced-96f1-320b557e8e67')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('4e459056-9a05-48a5-9345-b1ce249b6444', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:40.795', '', 'CHEM277-RG-AO', 'ab6550d8-4da4-4b39-8a38-514fffce2849', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM277-RG-AO', '8f8f0364-d2a7-4167-98c2-b6704263264f', 'd29eb24e-69d3-49bf-9523-fd048f71f468')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('3dc7bdff-ce00-4452-8248-77d623ea0a9d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '25889d4f-0612-424c-87a5-e96f60f3e096', '', '', '1002', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1002', '1002', '', 'c095843f-1925-4c49-8f86-f2ae3234d600')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('db18fa0b-99cb-477e-8732-069b7b24aaab', 'kuali.attribute.registration.group.is.generated', 'c095843f-1925-4c49-8f86-f2ae3234d600', 1, '7a149ae3-aa5a-48ab-9a06-d3a1b742e434')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e5a84df0-cf45-49dc-9665-529c9632ece7', 'kuali.attribute.registration.group.aocluster.id', 'c095843f-1925-4c49-8f86-f2ae3234d600', '4ee0cfa9-7577-421e-ab3f-edfb54658c6c', '3dc29246-6d06-474d-8ffe-e10ad89fb2f5')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('aa3875c8-e6cc-405c-b388-f433e91c5e6a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'c095843f-1925-4c49-8f86-f2ae3234d600', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '123b797e-1c72-493b-8ea0-3f5972115a13')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e1c937d3-8cf3-479d-8226-5f019796beb2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:40.927', '', 'CHEM277-FO-RG', '792b2c3d-0b9d-4214-813b-cf882c9b74f3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM277-FO-RG', 'c095843f-1925-4c49-8f86-f2ae3234d600', 'cceacf68-f2f1-44c0-b8fb-4e22fc813329')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('7c3f108f-0bbb-4584-9cd3-932b272a303c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:40.928', '', 'CHEM277-RG-AO', 'c095843f-1925-4c49-8f86-f2ae3234d600', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM277-RG-AO', '4f865a71-cbeb-4ab2-96d0-951a8f76bdde', '755a1013-7a9d-4934-b1fa-841738fb6397')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('6e251e8d-1916-4d14-9353-6e222d4d5365', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:40.929', '', 'CHEM277-RG-AO', 'c095843f-1925-4c49-8f86-f2ae3234d600', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM277-RG-AO', '7c589a1a-ecc9-4ecb-a260-559e20618e1e', '18a56864-4ac3-4888-9ae7-2498c04338f0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('8d3fea04-ebfd-4544-997a-5cfbd4bf0f5e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '25889d4f-0612-424c-87a5-e96f60f3e096', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'e79a7682-f7d6-49e6-9a2c-4426aa19361f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('22502241-cc21-42e5-8041-f47fe654721e', 'kuali.attribute.registration.group.aocluster.id', 'e79a7682-f7d6-49e6-9a2c-4426aa19361f', '86a6abbf-eb21-474a-a949-9afa3329fe58', '9db51321-127b-4ecd-92db-0c63e69cda78')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a5c55d44-1ce4-4662-b6e2-b54faaffc08b', 'kuali.attribute.registration.group.is.generated', 'e79a7682-f7d6-49e6-9a2c-4426aa19361f', 1, 'bf068754-f2cf-4961-91ed-cda460f5ab3d')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('790c52ec-e1b4-4ddc-830b-ae252d4f0044', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'e79a7682-f7d6-49e6-9a2c-4426aa19361f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ad387a3f-6a1c-4160-a91e-b2d654eec360')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('d86a7036-3983-4324-965a-eb0f87dea77b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:41.028', '', 'CHEM277-FO-RG', '792b2c3d-0b9d-4214-813b-cf882c9b74f3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM277-FO-RG', 'e79a7682-f7d6-49e6-9a2c-4426aa19361f', '8f1e34a3-b60e-41c5-88e2-62ef51d2a60d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('4debafba-90be-4833-bad9-265b03cab624', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:41.029', '', 'CHEM277-RG-AO', 'e79a7682-f7d6-49e6-9a2c-4426aa19361f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM277-RG-AO', '888f7c63-1cc2-454e-b795-87806b2c6e90', 'a1059a39-edcd-45b1-bb41-a089be784dec')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ef3547a5-24a1-423b-81e2-f2319a46a521', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:41.029', '', 'CHEM277-RG-AO', 'e79a7682-f7d6-49e6-9a2c-4426aa19361f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM277-RG-AO', 'b59f9751-3e99-486a-a449-62d923254580', 'b8081cfc-5185-4c67-83e3-ee4d250ed8bf')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('1b63e6e0-5567-4f3a-aea9-43f28694cfab', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '25889d4f-0612-424c-87a5-e96f60f3e096', '', '', '1002', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1002', '1002', '', 'f1ade02b-4468-47e1-9218-518f16c0b20b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c5b7d7fa-3e6f-4fb4-ab66-70b4b901aacc', 'kuali.attribute.registration.group.aocluster.id', 'f1ade02b-4468-47e1-9218-518f16c0b20b', '86a6abbf-eb21-474a-a949-9afa3329fe58', 'bd1d710a-eef1-4d67-a49e-a17982ddbe0a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dcd96344-2414-4b0b-af67-2b7c27be5182', 'kuali.attribute.registration.group.is.generated', 'f1ade02b-4468-47e1-9218-518f16c0b20b', 1, '136fc809-b647-41ac-b3d6-1bd899d43b9f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d594ca70-2781-46f7-8e42-8ecbf3eca4cb', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'f1ade02b-4468-47e1-9218-518f16c0b20b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'd334916c-0d01-461a-9d9f-26d765d5273d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('b659b740-2b93-4090-9e93-bd55836f30f9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:41.166', '', 'CHEM277-FO-RG', '792b2c3d-0b9d-4214-813b-cf882c9b74f3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM277-FO-RG', 'f1ade02b-4468-47e1-9218-518f16c0b20b', '156f82ec-525c-49e4-8119-0a418092d715')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('22fbe4ff-5955-4328-9c24-18d55b69b60c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:41.167', '', 'CHEM277-RG-AO', 'f1ade02b-4468-47e1-9218-518f16c0b20b', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM277-RG-AO', '888f7c63-1cc2-454e-b795-87806b2c6e90', 'c0cb5929-28e4-415a-849b-d855914de9b6')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ceef2393-7cdd-4a8a-b5c6-a9f3f0420939', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:41.167', '', 'CHEM277-RG-AO', 'f1ade02b-4468-47e1-9218-518f16c0b20b', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM277-RG-AO', 'baf29f8e-7024-47d5-96c8-c0d36de0e5ef', 'e94b2d1b-4608-4cc0-80e6-793648e03cc1')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ba9cdaa2-6985-4f59-bef0-ce4454f2afc2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'CLUID-ENGL329-201008000000', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Studies in various periods and genres of film.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL329B CO', 'Studies in various periods and genres of film.', null, 'e1091908-3ddf-4855-8926-b75eae92b522')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a4b6c08d-6d73-4e6d-af98-71627bffa803', 'kuali.attribute.wait.list.level.type.key', 'e1091908-3ddf-4855-8926-b75eae92b522', 'AO', '4052cba6-6ed4-4e4f-8428-be4df07fe0b7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5301c412-6693-48b5-9391-d5eaddc4fd46', 'kuali.attribute.wait.list.type.key', 'e1091908-3ddf-4855-8926-b75eae92b522', null, '5c29872c-e470-46b4-ac50-15c453597e78')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4f2f2b6f-8ba5-424f-b1b7-11c8cfb90f82', 'kuali.attribute.course.evaluation.indicator', 'e1091908-3ddf-4855-8926-b75eae92b522', 0, 'f9bf243a-5fc2-41bc-b304-b19e187833d6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a00e35ee-cd3b-43c5-943b-578a7de29ea2', 'kuali.attribute.final.exam.driver', 'e1091908-3ddf-4855-8926-b75eae92b522', 'kuali.lu.exam.driver.ActivityOffering', '3f1c95a7-e135-4df7-9c9c-11bcdcb6c0a5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4dc57c7c-56a9-4486-a6f6-5e243ac01200', 'kuali.attribute.funding.source', 'e1091908-3ddf-4855-8926-b75eae92b522', '', '280579c3-6ba3-4de3-800c-6ee4b59020e2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3eb7f9d3-b139-40c0-a894-1df109423974', 'kuali.attribute.final.exam.indicator', 'e1091908-3ddf-4855-8926-b75eae92b522', 'STANDARD', '06bda6ba-dd0c-4af7-8691-ac9bad33ec51')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7402d88f-0fe5-4249-aca4-412eb174a457', 'kuali.attribute.wait.list.indicator', 'e1091908-3ddf-4855-8926-b75eae92b522', 1, 'cf10ce11-1cbb-4168-b0ce-d190b8ded436')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0e6efcc2-34a6-4a97-b8d4-22415ce93402', 'kuali.attribute.course.number.internal.suffix', 'e1091908-3ddf-4855-8926-b75eae92b522', 'A', '56f5790a-5ee1-4635-90e0-311b8638d556')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('85f43000-ba75-4434-acd8-a20c48bc90e8', 'kuali.attribute.final.exam.use.matrix', 'e1091908-3ddf-4855-8926-b75eae92b522', 1, '4644a918-6da1-4ac7-a681-195785cb6dff')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9e5baf22-0eb0-41f5-80d8-ee91d92a6e37', 'kuali.attribute.where.fees.attached.flag', 'e1091908-3ddf-4855-8926-b75eae92b522', 0, '89d74c91-38a9-416a-ae96-6606931c8368')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('542e2a4a-558d-4e2d-a542-bfdae7f635c3', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'ENGL329B', 'ENGL', '', 'Special Topics in Film Studies', 'e1091908-3ddf-4855-8926-b75eae92b522', '', '', 'kuali.lui.identifier.state.active', 'B', 'kuali.lui.identifier.type.official', '', 'e0fcb3a6-3d0c-4ac5-a45a-476a2604c9f2')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('fbafa03e-7628-46c2-8cd9-de6a0d55fb31', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'e1091908-3ddf-4855-8926-b75eae92b522', '', 'kuali.lu.code.honorsOffering', '', '6f056bce-d47d-41ee-b3da-2175f925257a')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('e1091908-3ddf-4855-8926-b75eae92b522', '2677933260', '629e831e-eac9-42fd-8ced-e4e2dd32e98e')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('e1091908-3ddf-4855-8926-b75eae92b522', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e1091908-3ddf-4855-8926-b75eae92b522', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e1091908-3ddf-4855-8926-b75eae92b522', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e1091908-3ddf-4855-8926-b75eae92b522', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e1091908-3ddf-4855-8926-b75eae92b522', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('547e2435-9755-4326-b745-94599ab9fa8f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '190b475a-475d-4246-a23d-6e260996a916', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'f7076699-e2a9-4ec3-bc58-0fb1a774b60c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b814aa03-d3a8-42d9-b245-d68d2de1f542', 'regCodePrefix', 'f7076699-e2a9-4ec3-bc58-0fb1a774b60c', '1', 'e2ec1d44-ce43-47e5-b09e-a849bfc649df')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b6f3460e-e73f-4e6c-80e3-f8f88ee7c84f', 'kuali.attribute.final.exam.level.type', 'f7076699-e2a9-4ec3-bc58-0fb1a774b60c', 'kuali.lu.type.activity.Lecture', '71cb304c-6c0e-4826-a662-1f80da81f286')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dc05f41b-5a9f-42db-b4d3-e657fd1844e9', 'kuali.attribute.grade.roster.level.type.key', 'f7076699-e2a9-4ec3-bc58-0fb1a774b60c', 'kuali.lui.type.activity.offering.lecture', 'c12282fc-5e18-4dd6-9af5-b61c64f5b998')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('54bb3a04-5312-4ad1-86ec-78ec95e6651d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', 'f7076699-e2a9-4ec3-bc58-0fb1a774b60c', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'be081cd6-4708-465d-b28b-d54991c21a8a')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('7afae2be-2605-40a0-8f3f-e037061b1f2f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:45.542', '', 'ENGL329B-CO-FO', 'e1091908-3ddf-4855-8926-b75eae92b522', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL329B-CO-FO', 'f7076699-e2a9-4ec3-bc58-0fb1a774b60c', '4d5c6fd9-89f5-42ae-946c-2f7984815ee6')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('f7076699-e2a9-4ec3-bc58-0fb1a774b60c', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('a6131a7e-39fe-42b1-8140-805cf88b3b4b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '4134e3cc-9ba5-47e0-a834-99ae05582b4f', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, 'cbc8b0d9-e7d1-4892-bc2d-80676b6fdc89')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('54bd6e50-5ec0-42de-9504-793aca47ddba', 'kuali.attribute.max.enrollment.is.estimate', 'cbc8b0d9-e7d1-4892-bc2d-80676b6fdc89', 0, '15ea6d8f-c9ce-4d82-8f3c-b6bcccc193e1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('be0d231e-8dd3-407e-b9b9-157a76d6268b', 'kuali.attribute.nonstd.ts.indicator', 'cbc8b0d9-e7d1-4892-bc2d-80676b6fdc89', 0, 'ad819fcd-52da-4652-8c5d-b5f55fd2bbc2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a809d18d-eb3a-48f8-a22d-21c32823306b', 'kuali.attribute.course.evaluation.indicator', 'cbc8b0d9-e7d1-4892-bc2d-80676b6fdc89', 0, '1d7c400b-2d5a-4e0c-8bd7-b27458663271')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7d932240-a253-439d-a41a-2925b4bac9e9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', 'cbc8b0d9-e7d1-4892-bc2d-80676b6fdc89', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'e256183c-086f-46d3-9a53-84420985eb0d')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('fa24c6a7-4968-400a-9ab2-ef8e2f0a4d71', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'cbc8b0d9-e7d1-4892-bc2d-80676b6fdc89', '', 'kuali.lu.code.honorsOffering', 0, '2074e560-17ee-4531-9e58-71480b52c61d')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('12cfddbd-33f8-4e6f-b237-07c2bf7486f1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:49:45.646', '', 'cbc8b0d9-e7d1-4892-bc2d-80676b6fdc89', 'C.GUOZHONGC', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '19fb00ee-d293-4c4e-a9ad-0713c48c022b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ce31dbae-7fee-46d9-bcca-e037e2c857c6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:45.646', '', 'ENGL329B-FO-AO', 'f7076699-e2a9-4ec3-bc58-0fb1a774b60c', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL329B-FO-AO', 'cbc8b0d9-e7d1-4892-bc2d-80676b6fdc89', 'a704074d-7c96-4420-a87e-e3d9c6e45ba2')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('f4cf99a1-858c-4260-a030-44891c618531', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'f626694c-e069-431f-9d60-20d2b7fdd772')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('fa4b2c02-eb14-41f7-b0ea-b2c07e89b39c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for ENGL329B - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'f626694c-e069-431f-9d60-20d2b7fdd772', '382d90ca-aa91-4c04-9e7e-e41308928f06')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('dba14b30-03d1-4f24-a0eb-8ed32f0d0b8b', 0, '382d90ca-aa91-4c04-9e7e-e41308928f06', '3e7d1501-ed90-403f-9cf0-c89d199f2d05')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('8c567341-5e57-4ce3-b728-3ca638a76d00', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'b901af31-ae59-4708-b771-382bb15ceb58')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('f626694c-e069-431f-9d60-20d2b7fdd772', 'cbc8b0d9-e7d1-4892-bc2d-80676b6fdc89')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('3e7d1501-ed90-403f-9cf0-c89d199f2d05', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('3e7d1501-ed90-403f-9cf0-c89d199f2d05', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('3e7d1501-ed90-403f-9cf0-c89d199f2d05', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('3e7d1501-ed90-403f-9cf0-c89d199f2d05', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('d35de821-b675-4ed3-8a2d-41c1c047d2ec', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '1827532d-7512-4e7e-a8ba-03faf3a233dd')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('15d8915f-a277-4f3b-ad5c-3a81197780ab', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'f7076699-e2a9-4ec3-bc58-0fb1a774b60c', 'CL 1', 'CL 1', 'fc948a3d-69cc-4411-abeb-ec24672dd7df')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('23425f79-b242-4e30-9f13-fd9416ca9a58', 'kuali.lui.type.activity.offering.lecture', 'fc948a3d-69cc-4411-abeb-ec24672dd7df', '5d1264d6-3d84-41c6-bdaf-8bd9b32e6672')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='8c567341-5e57-4ce3-b728-3ca638a76d00', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='cbc8b0d9-e7d1-4892-bc2d-80676b6fdc89', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='b901af31-ae59-4708-b771-382bb15ceb58' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('1827532d-7512-4e7e-a8ba-03faf3a233dd', 'cbc8b0d9-e7d1-4892-bc2d-80676b6fdc89')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('1827532d-7512-4e7e-a8ba-03faf3a233dd', 'f7076699-e2a9-4ec3-bc58-0fb1a774b60c')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('5d1264d6-3d84-41c6-bdaf-8bd9b32e6672', 'cbc8b0d9-e7d1-4892-bc2d-80676b6fdc89')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='fc948a3d-69cc-4411-abeb-ec24672dd7df' where ID='5d1264d6-3d84-41c6-bdaf-8bd9b32e6672'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('821330c8-1838-47d8-a515-76615a2677e7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '190b475a-475d-4246-a23d-6e260996a916', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'f32cf3a8-7ec1-4582-89b8-6913348ad364')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8ac4c181-9493-481e-bd55-0df01e7ddb60', 'kuali.attribute.registration.group.is.generated', 'f32cf3a8-7ec1-4582-89b8-6913348ad364', 1, '0b5eaa77-c888-4538-8308-b70a484bc8c5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c8e25efe-5146-448f-9648-a5c2c2beb720', 'kuali.attribute.registration.group.aocluster.id', 'f32cf3a8-7ec1-4582-89b8-6913348ad364', 'fc948a3d-69cc-4411-abeb-ec24672dd7df', '424e79d5-ae84-4e98-9d42-9d9927124fa1')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('4cd21b77-5c58-4e94-9806-f737f666b93f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'f32cf3a8-7ec1-4582-89b8-6913348ad364', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'd5b502e8-8b3d-48f4-891d-cf661e924e6c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('c9845cea-d478-4e7b-852d-fa915ef3625f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:46.393', '', 'ENGL329B-FO-RG', 'f7076699-e2a9-4ec3-bc58-0fb1a774b60c', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL329B-FO-RG', 'f32cf3a8-7ec1-4582-89b8-6913348ad364', 'e891b4ac-a24f-4531-80af-10137ef55496')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f7631ab8-5570-4215-afb7-e509d193f329', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:46.393', '', 'ENGL329B-RG-AO', 'f32cf3a8-7ec1-4582-89b8-6913348ad364', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL329B-RG-AO', 'cbc8b0d9-e7d1-4892-bc2d-80676b6fdc89', '5e339955-30be-474b-ae8e-de4a3decab4d')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('4deb4115-80b5-424e-b934-a41cc6c19b31', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '5dac01fa-f1c8-4f8d-a6be-70adf6889e2b', TIMESTAMP '2014-01-01 19:00:00.0', '', 'An argument for the broad continuity between the revolutionary and Napoleonic wars.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST436 CO', 'An argument for the broad continuity between the revolutionary and Napoleonic wars.', null, '160ab1c3-bb50-4825-ba40-ee6a9a49d432')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c4c4f528-327b-4bbe-92ae-78ce83e6da33', 'kuali.attribute.course.number.internal.suffix', '160ab1c3-bb50-4825-ba40-ee6a9a49d432', 'A', '2341cdc7-8538-415f-8097-cf3d59e59666')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('eae07072-b6bb-4add-9dd7-c0c929fc02c2', 'kuali.attribute.course.evaluation.indicator', '160ab1c3-bb50-4825-ba40-ee6a9a49d432', 0, 'b45fd360-4e4c-4a78-ae1f-f19676848132')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0ada6e81-1f59-424b-8c35-7058059d0550', 'kuali.attribute.final.exam.use.matrix', '160ab1c3-bb50-4825-ba40-ee6a9a49d432', 1, '12ad23de-21f0-4221-afa8-0bacdfbd21d0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('20906376-95b7-4649-a189-3e9e831ab69a', 'kuali.attribute.funding.source', '160ab1c3-bb50-4825-ba40-ee6a9a49d432', '', 'dfe7faee-d428-461d-a69a-d525d70739ca')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('371338d0-89af-49ab-8490-14244c56c36f', 'kuali.attribute.wait.list.indicator', '160ab1c3-bb50-4825-ba40-ee6a9a49d432', 1, '36f222a9-715b-4676-811f-63ae3003b441')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4987057c-d3d8-454d-9a53-e871810fc51f', 'kuali.attribute.final.exam.driver', '160ab1c3-bb50-4825-ba40-ee6a9a49d432', 'kuali.lu.exam.driver.ActivityOffering', 'be019e39-3ca4-4617-81f0-c2086d808662')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('52d2cfbf-0e71-49f2-8d5a-931bd3f8426b', 'kuali.attribute.wait.list.level.type.key', '160ab1c3-bb50-4825-ba40-ee6a9a49d432', 'AO', '2d5339b3-2bf9-4efb-8fdd-5bda9ca98c5a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b7271688-2dcc-4771-ae90-48d5626d6d66', 'kuali.attribute.final.exam.indicator', '160ab1c3-bb50-4825-ba40-ee6a9a49d432', 'STANDARD', '3639a564-8c10-4a74-b7d8-ef26d70e31d1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('823ef947-8a5a-4fd1-aa89-3672cb9dd468', 'kuali.attribute.wait.list.type.key', '160ab1c3-bb50-4825-ba40-ee6a9a49d432', null, 'e3fbc4ff-9c68-4813-a85a-b3fb188cc9cf')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4375a3b8-97a0-4f43-9d96-4ac2073393b8', 'kuali.attribute.where.fees.attached.flag', '160ab1c3-bb50-4825-ba40-ee6a9a49d432', 0, 'c9adeafc-bbeb-4e19-9f98-f3542fe0541f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('26714b71-420c-4baf-b7e8-531054bf2d27', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'HIST436', 'HIST', '', 'Napoleon, the French Revolution and the World', '160ab1c3-bb50-4825-ba40-ee6a9a49d432', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'd788701e-7c75-43fd-b005-2d44fef600a7')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('99fea9bd-379d-464f-9e2d-69a275113aa4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '160ab1c3-bb50-4825-ba40-ee6a9a49d432', '', 'kuali.lu.code.honorsOffering', '', '4189a0aa-1819-4d7e-b583-1a85cde09973')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('160ab1c3-bb50-4825-ba40-ee6a9a49d432', '3213375036', '2ba6c46f-446c-44cb-beb8-9360db60f39c')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('160ab1c3-bb50-4825-ba40-ee6a9a49d432', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('160ab1c3-bb50-4825-ba40-ee6a9a49d432', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('160ab1c3-bb50-4825-ba40-ee6a9a49d432', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('160ab1c3-bb50-4825-ba40-ee6a9a49d432', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('160ab1c3-bb50-4825-ba40-ee6a9a49d432', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('69821ea3-c025-439c-8fc8-1e4e77219a14', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'f0d82d9c-9834-4aa8-bb79-ee89d58eb8c9', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '564d13be-3ce0-4a34-8b83-147dc26c203e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ad8bf876-acf9-4165-9d6d-97311d80a608', 'regCodePrefix', '564d13be-3ce0-4a34-8b83-147dc26c203e', '1', '231f4559-3f13-4837-b5aa-5c68258cc774')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cfc5a841-5ad4-48ca-9406-72c3e953ed97', 'kuali.attribute.final.exam.level.type', '564d13be-3ce0-4a34-8b83-147dc26c203e', 'kuali.lu.type.activity.Lecture', 'd972b7fe-214c-4cf6-aa79-eaa6a85afd54')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a4e28217-5f6a-4b2d-a8ca-39a6fc1cda2b', 'kuali.attribute.grade.roster.level.type.key', '564d13be-3ce0-4a34-8b83-147dc26c203e', 'kuali.lui.type.activity.offering.lecture', 'facd97af-5e07-4e97-8bf2-8c91944f0bfa')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('8fc8e33b-a0e9-484b-bcf8-f8438e2b1b41', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '564d13be-3ce0-4a34-8b83-147dc26c203e', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '8e3650bc-7b05-4acd-b975-04ddb2a32141')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('0b4660aa-37df-4241-b8d1-bbe23dfd0f79', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:48.36', '', 'HIST436-CO-FO', '160ab1c3-bb50-4825-ba40-ee6a9a49d432', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST436-CO-FO', '564d13be-3ce0-4a34-8b83-147dc26c203e', '641c2679-fa30-44d3-bc7b-dff508412eaa')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('564d13be-3ce0-4a34-8b83-147dc26c203e', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('7d9b220f-84c2-42a1-926d-db1d52b0ee6a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '3764a8ad-49ec-4cc0-8481-9ab0663491ac', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, 'fa90af66-108a-4764-9a5e-ebd832b356f0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f9078607-0cef-425c-b4e2-e720fe266e7f', 'kuali.attribute.course.evaluation.indicator', 'fa90af66-108a-4764-9a5e-ebd832b356f0', 0, 'b844e228-a3ea-49d1-a97b-0a7a8b0ac0e9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8ebfec15-0b96-4dec-b421-00acb1d150a0', 'kuali.attribute.nonstd.ts.indicator', 'fa90af66-108a-4764-9a5e-ebd832b356f0', 0, '92e85407-af9a-4cf6-8d2f-5f66a31f16e1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d8c7a75e-5e00-4d13-983b-850d1bac0c0b', 'kuali.attribute.max.enrollment.is.estimate', 'fa90af66-108a-4764-9a5e-ebd832b356f0', 0, '80849ea0-6818-49cb-af06-140c33c1cc1c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('06179e4d-d668-44e0-901b-c2da3f493499', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', 'fa90af66-108a-4764-9a5e-ebd832b356f0', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '593f87c3-524d-4db5-87fe-a8cf57f8c7e5')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('4927a7e8-b97e-431a-80b4-54fd86edcb1a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'fa90af66-108a-4764-9a5e-ebd832b356f0', '', 'kuali.lu.code.honorsOffering', 0, 'f78c52a3-f3e6-4c33-9392-d83eb291a868')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('3cd4c92e-160a-4c17-a827-62431f136195', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:49:48.462', '', 'fa90af66-108a-4764-9a5e-ebd832b356f0', 'K.HECTORR', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'fdf75033-1a40-4255-a32b-f31f0e6fb79f')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('45f032b6-51d0-453f-8a08-6c45f01ca54e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:48.462', '', 'HIST436-FO-AO', '564d13be-3ce0-4a34-8b83-147dc26c203e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST436-FO-AO', 'fa90af66-108a-4764-9a5e-ebd832b356f0', '4d304a68-1c5b-4e19-9a6a-0fea88c4952b')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('40536a6d-c43a-4439-8c22-c3aa9c766251', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'af3b077d-c9d3-406b-9254-3fed520df4e3')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('0f46cfd9-f34b-4328-b35e-4d7059a29f00', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for HIST436 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'af3b077d-c9d3-406b-9254-3fed520df4e3', 'bad6969f-5832-4c0e-9b72-5e9be0ee5aa3')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('8d0098de-14a5-4545-9049-3c8e3e54f8b0', 0, 'bad6969f-5832-4c0e-9b72-5e9be0ee5aa3', '71bb11ec-c920-4f87-a4fc-c875c1058fd7')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('9d0f73b4-4048-46c8-baad-26ba5a3cdb4f', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Athlete Seatpool', '1c2e7786-a2ab-4778-8e81-ea540c60b9b2', 1, 2, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '7b395bd0-8df0-49be-b394-e72feba52ced')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('af3b077d-c9d3-406b-9254-3fed520df4e3', 'fa90af66-108a-4764-9a5e-ebd832b356f0')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('71bb11ec-c920-4f87-a4fc-c875c1058fd7', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('71bb11ec-c920-4f87-a4fc-c875c1058fd7', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('71bb11ec-c920-4f87-a4fc-c875c1058fd7', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('71bb11ec-c920-4f87-a4fc-c875c1058fd7', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('aef1d79a-8ba8-471b-ace1-d96c01a77c45', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Junior Seatpool', '4bae9f12-5901-4db9-b4a8-4dcc20781db5', 2, 4, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '4ef1d61e-753b-480e-8e00-38eb6989d81a')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='9d0f73b4-4048-46c8-baad-26ba5a3cdb4f', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='fa90af66-108a-4764-9a5e-ebd832b356f0', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Athlete Seatpool', POPULATION_ID='1c2e7786-a2ab-4778-8e81-ea540c60b9b2', PROCESSING_PRIORITY=1, SEAT_LIMIT=2, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='7b395bd0-8df0-49be-b394-e72feba52ced' and VER_NBR=1
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('87c01726-5239-4dc8-8aab-f4713fb480ed', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '1b99c9c2-527e-4882-a559-287431274b9d')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('0bbd9ce4-f211-475d-bdee-22b6f45b13d5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '564d13be-3ce0-4a34-8b83-147dc26c203e', 'CL 1', 'CL 1', '3fbad589-8f45-42a3-b05f-f431801089a3')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('937ff81d-60de-4c17-88ca-4a4b918c2ca1', 'kuali.lui.type.activity.offering.lecture', '3fbad589-8f45-42a3-b05f-f431801089a3', '410643da-2349-4c1f-a1ed-54f76705d5cd')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='aef1d79a-8ba8-471b-ace1-d96c01a77c45', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='fa90af66-108a-4764-9a5e-ebd832b356f0', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Junior Seatpool', POPULATION_ID='4bae9f12-5901-4db9-b4a8-4dcc20781db5', PROCESSING_PRIORITY=2, SEAT_LIMIT=4, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='4ef1d61e-753b-480e-8e00-38eb6989d81a' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('1b99c9c2-527e-4882-a559-287431274b9d', 'fa90af66-108a-4764-9a5e-ebd832b356f0')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('1b99c9c2-527e-4882-a559-287431274b9d', '564d13be-3ce0-4a34-8b83-147dc26c203e')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('410643da-2349-4c1f-a1ed-54f76705d5cd', 'fa90af66-108a-4764-9a5e-ebd832b356f0')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='3fbad589-8f45-42a3-b05f-f431801089a3' where ID='410643da-2349-4c1f-a1ed-54f76705d5cd'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ba2799e2-7ba8-4af1-a11c-ac6e25212371', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'f0d82d9c-9834-4aa8-bb79-ee89d58eb8c9', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'a99948bb-a56a-4592-89a3-87c2ad0f7d53')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1057a649-f5c9-429b-b7be-8f1c007b89cc', 'kuali.attribute.registration.group.aocluster.id', 'a99948bb-a56a-4592-89a3-87c2ad0f7d53', '3fbad589-8f45-42a3-b05f-f431801089a3', 'ef2a2074-c0cf-4dbf-9c46-4af8954cb918')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8fa4a620-4551-4d12-97df-224ad0c0627d', 'kuali.attribute.registration.group.is.generated', 'a99948bb-a56a-4592-89a3-87c2ad0f7d53', 1, '5134c458-f127-47cc-ab75-486d0a860962')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('40f1413b-d06d-4718-88b0-1aefddfdf02a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'a99948bb-a56a-4592-89a3-87c2ad0f7d53', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '5f02f98f-eef4-47ad-a211-1ea2357d09b1')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f77bf3bb-397b-4777-b9ca-8bc0552d0d9f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:49.109', '', 'HIST436-FO-RG', '564d13be-3ce0-4a34-8b83-147dc26c203e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST436-FO-RG', 'a99948bb-a56a-4592-89a3-87c2ad0f7d53', 'e8e8a8b3-f4d9-44c6-ac62-3cd2473f0d57')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ef149bfa-ad74-4b5a-8249-35f8bc565d84', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:49.11', '', 'HIST436-RG-AO', 'a99948bb-a56a-4592-89a3-87c2ad0f7d53', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST436-RG-AO', 'fa90af66-108a-4764-9a5e-ebd832b356f0', 'fb58df35-d7ee-4c49-9301-6becfa3e3706')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('465d00cc-4877-4d54-a30e-5795a856f7e9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '974c7253-0198-40ab-9e50-35a5ba2f98e5', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Special topics course taken as part of an approved study abroad program', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL369D CO', 'Special topics course taken as part of an approved study abroad program', null, '6276fa96-379e-404f-bab0-d0c2b118ccd5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('df5023d5-9c25-4ac5-aa77-11aac69a7e82', 'kuali.attribute.course.evaluation.indicator', '6276fa96-379e-404f-bab0-d0c2b118ccd5', 0, '1dd9790b-d5ba-45e5-a6b2-dae1cb6d91a8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('623b599b-2d7c-4e49-8170-f290be2b22b5', 'kuali.attribute.course.number.internal.suffix', '6276fa96-379e-404f-bab0-d0c2b118ccd5', 'A', '9903b7d5-90e7-48d6-9704-bac212df92fc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7a6c7baf-7c72-4445-9b0f-936c5e1bb6d7', 'kuali.attribute.wait.list.indicator', '6276fa96-379e-404f-bab0-d0c2b118ccd5', 1, 'a2212e2c-9c2f-4d83-9a5b-42f8f1582a67')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a7e6ace3-000b-40e8-a4da-412d33b6184d', 'kuali.attribute.where.fees.attached.flag', '6276fa96-379e-404f-bab0-d0c2b118ccd5', 0, 'cd41e901-ddf4-4c75-9502-9b5e91b9cd65')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cea1a513-2e78-4039-93f6-bcd62c35f7a2', 'kuali.attribute.funding.source', '6276fa96-379e-404f-bab0-d0c2b118ccd5', '', '2ae952b4-9ba6-4e81-b30b-657b82aa336b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('248d5b4b-72b9-4b72-bf27-036f3cc3e388', 'kuali.attribute.wait.list.level.type.key', '6276fa96-379e-404f-bab0-d0c2b118ccd5', 'AO', '400e0ef5-c0c0-4c0d-a1bb-1f40aa615f00')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7a0fd4fb-71a6-46d3-9162-0a9ec30cf3aa', 'kuali.attribute.final.exam.use.matrix', '6276fa96-379e-404f-bab0-d0c2b118ccd5', 1, '99e25420-0a13-4cc2-be63-a00266ce2e4a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e5c01703-1ab7-49f6-98ee-5969facd847b', 'kuali.attribute.final.exam.driver', '6276fa96-379e-404f-bab0-d0c2b118ccd5', 'kuali.lu.exam.driver.ActivityOffering', 'e6a3301c-a704-46ab-9d62-e2d807a5d9f0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('76e14b1a-bae4-4bd2-ad42-8e5be31ace0c', 'kuali.attribute.final.exam.indicator', '6276fa96-379e-404f-bab0-d0c2b118ccd5', 'STANDARD', 'c7a1e48f-167d-423d-87ed-020132887f54')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4324f24d-bbb9-463f-945c-0f6624397cd3', 'kuali.attribute.wait.list.type.key', '6276fa96-379e-404f-bab0-d0c2b118ccd5', null, 'ebb3044b-23f9-4e1a-b198-2f31f38f7700')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7be2c0ea-39c0-4d03-9c76-1b84cb25a6a5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'ENGL369D', 'ENGL', '', 'Special Topics in Study Abroad III', '6276fa96-379e-404f-bab0-d0c2b118ccd5', '', '', 'kuali.lui.identifier.state.active', 'D', 'kuali.lui.identifier.type.official', '', 'df85447d-116f-4b2f-85bb-07f2c19d916e')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('222210f4-1db3-4e7f-b127-573b6ce01218', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '6276fa96-379e-404f-bab0-d0c2b118ccd5', '', 'kuali.lu.code.honorsOffering', '', 'd0ecc5e1-c3fe-4cfc-bab1-4f315ef393b5')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('6276fa96-379e-404f-bab0-d0c2b118ccd5', '2677933260', 'eee2f3ef-9eaa-48a5-98ae-9410ae5d6faf')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('6276fa96-379e-404f-bab0-d0c2b118ccd5', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('6276fa96-379e-404f-bab0-d0c2b118ccd5', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('6276fa96-379e-404f-bab0-d0c2b118ccd5', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('6276fa96-379e-404f-bab0-d0c2b118ccd5', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('6276fa96-379e-404f-bab0-d0c2b118ccd5', 'kuali.creditType.credit.degree.1-6.by.0.5')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ad6c8837-774d-4a43-8774-394bcaf4c139', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'ce6b6491-798d-4fb9-b0e2-ae2f725a5188', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '453d8e2f-10ba-416f-bc0a-7244cfd2964e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e3185f92-d45f-4b94-88f0-abee09ac2bca', 'kuali.attribute.grade.roster.level.type.key', '453d8e2f-10ba-416f-bc0a-7244cfd2964e', 'kuali.lui.type.activity.offering.lecture', 'bbab9cf0-32e6-4c1b-9d03-51840d05247d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('242cdd5c-7686-445d-8a03-04eb67ba9b77', 'regCodePrefix', '453d8e2f-10ba-416f-bc0a-7244cfd2964e', '1', '33792bfb-caab-476a-9fb5-0ce92d34656e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('085f8073-3061-41ac-aad9-9d8265b77d97', 'kuali.attribute.final.exam.level.type', '453d8e2f-10ba-416f-bc0a-7244cfd2964e', 'kuali.lu.type.activity.Lecture', '3ce3f0c2-0949-42fb-89bc-dc4a04dc1818')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('fdd1bc5c-d1c1-4666-841c-ca58e02bb664', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '453d8e2f-10ba-416f-bc0a-7244cfd2964e', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '56855407-eb0d-4a61-9930-e7c291849e00')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('37282a84-181e-4ba7-a50d-b0a8fa0fe575', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:50.983', '', 'ENGL369D-CO-FO', '6276fa96-379e-404f-bab0-d0c2b118ccd5', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL369D-CO-FO', '453d8e2f-10ba-416f-bc0a-7244cfd2964e', 'e95db2be-458e-4ba1-aaa4-5a8dd341f074')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('453d8e2f-10ba-416f-bc0a-7244cfd2964e', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d00b5861-f1c0-4e0b-a3b2-73610b61000f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'a6f69c11-b55f-4cb0-bcf6-3e8fab0e0019', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 1, 1, 'Lecture', 'Courses with lecture', null, '8c454009-f8de-4022-a6cb-b5fe6cbb5c5f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('293bd07b-0456-4e15-bdc6-d69c8e8ceb96', 'kuali.attribute.course.evaluation.indicator', '8c454009-f8de-4022-a6cb-b5fe6cbb5c5f', 0, '6fbd3a7c-452d-4e9d-9f9e-03efce4cf43c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5c60ad68-2c55-409d-8217-37de4be11b8b', 'kuali.attribute.max.enrollment.is.estimate', '8c454009-f8de-4022-a6cb-b5fe6cbb5c5f', 0, '8aca3d2c-b2d5-42db-95bf-203b92a96df9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5ab790f2-8bdf-4dbc-97d0-53e0c80c15d1', 'kuali.attribute.nonstd.ts.indicator', '8c454009-f8de-4022-a6cb-b5fe6cbb5c5f', 0, 'f2aeebcd-9d22-4ec0-b093-49a5e42e4ec3')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('cf492e79-1db8-492f-b852-11abbfedc9eb', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '8c454009-f8de-4022-a6cb-b5fe6cbb5c5f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'b8e0088d-2bea-49d0-baa8-936893d0d743')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('029d6649-0b97-4b11-a4cc-8253c2dc148f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '8c454009-f8de-4022-a6cb-b5fe6cbb5c5f', '', 'kuali.lu.code.honorsOffering', 0, '37ce718d-6833-4235-b429-3bb165ca599f')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('62f8fec0-ba90-45e5-b055-edb93d91c67a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:49:51.053', '', '8c454009-f8de-4022-a6cb-b5fe6cbb5c5f', 'M.INDREB', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '151a53b3-aa48-4e99-8549-0b34e3a180f4')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e6fb8ae2-60ca-4c6f-b78f-8dec579cf4ad', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:51.053', '', 'ENGL369D-FO-AO', '453d8e2f-10ba-416f-bc0a-7244cfd2964e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL369D-FO-AO', '8c454009-f8de-4022-a6cb-b5fe6cbb5c5f', 'b199783b-3c13-4589-bf4b-339d7b16f338')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('ebe1b123-5bc5-4c17-92ec-fd299985f006', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '41e4199f-bd30-420c-b97e-e4f0436d54cc')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('8fb1773e-b642-453a-a48c-e52bb1896282', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for ENGL369D - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '41e4199f-bd30-420c-b97e-e4f0436d54cc', '8a0f3780-6290-41aa-ad12-368083d9a969')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('4077c4fe-ed3d-4105-abcb-56bca24aa64f', 1, '8a0f3780-6290-41aa-ad12-368083d9a969', '3c12d505-8869-4671-a19b-8ed45a01bd1a')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('5288c6a6-1fb9-45a4-81c0-03a504a40f22', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '712fe1f1-9fb3-4632-8b07-eb8c2ac17385')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('41e4199f-bd30-420c-b97e-e4f0436d54cc', '8c454009-f8de-4022-a6cb-b5fe6cbb5c5f')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('3c12d505-8869-4671-a19b-8ed45a01bd1a', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('4f611c4c-ef1d-4a30-a5a0-91119b4bb736', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'e827a830-d8af-4ca8-8e75-d80f3b02c535')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('fc742e74-5de9-4109-a9d7-f4ea8277ab19', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '453d8e2f-10ba-416f-bc0a-7244cfd2964e', 'CL 1', 'CL 1', 'fb371724-e6ff-46bc-b52d-3d0d559fc4b1')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('e2c14d6c-f501-41d5-8f27-76db4ca720b1', 'kuali.lui.type.activity.offering.lecture', 'fb371724-e6ff-46bc-b52d-3d0d559fc4b1', 'ea2dc0d9-2fe0-4cbc-b103-036e9e54a10f')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='5288c6a6-1fb9-45a4-81c0-03a504a40f22', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='8c454009-f8de-4022-a6cb-b5fe6cbb5c5f', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='712fe1f1-9fb3-4632-8b07-eb8c2ac17385' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('e827a830-d8af-4ca8-8e75-d80f3b02c535', '8c454009-f8de-4022-a6cb-b5fe6cbb5c5f')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('e827a830-d8af-4ca8-8e75-d80f3b02c535', '453d8e2f-10ba-416f-bc0a-7244cfd2964e')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('ea2dc0d9-2fe0-4cbc-b103-036e9e54a10f', '8c454009-f8de-4022-a6cb-b5fe6cbb5c5f')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='fb371724-e6ff-46bc-b52d-3d0d559fc4b1' where ID='ea2dc0d9-2fe0-4cbc-b103-036e9e54a10f'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d081ec2e-c121-461d-a2ce-42de03bf1fe8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'ce6b6491-798d-4fb9-b0e2-ae2f725a5188', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '54427903-789e-42fe-8545-a01ef6ef4b95')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b82ec119-f818-436f-9933-127d405c2d6f', 'kuali.attribute.registration.group.is.generated', '54427903-789e-42fe-8545-a01ef6ef4b95', 1, '2d492643-87bc-4351-b490-5507d84b8240')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bc27b1dc-e839-48af-b97c-82a58ccecb06', 'kuali.attribute.registration.group.aocluster.id', '54427903-789e-42fe-8545-a01ef6ef4b95', 'fb371724-e6ff-46bc-b52d-3d0d559fc4b1', 'af1c9d63-9f46-4e73-a42e-026368a86a94')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('12941bbe-31b8-493d-a93f-08fc763cf4ed', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', '54427903-789e-42fe-8545-a01ef6ef4b95', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'fd1e1260-f6c5-4a8f-a2cc-25570b3734a8')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('1ffa1db0-a14f-408f-a8e3-2cf88d9ce1be', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:51.498', '', 'ENGL369D-FO-RG', '453d8e2f-10ba-416f-bc0a-7244cfd2964e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL369D-FO-RG', '54427903-789e-42fe-8545-a01ef6ef4b95', 'a9140647-3df6-4775-a47a-06ccd033df5e')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('0a1f2fd2-9bf2-490c-89b2-d47c54fa5a79', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:51.498', '', 'ENGL369D-RG-AO', '54427903-789e-42fe-8545-a01ef6ef4b95', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL369D-RG-AO', '8c454009-f8de-4022-a6cb-b5fe6cbb5c5f', 'b9f130ba-752e-4d51-ab6e-dc4492de0a75')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('29d32f59-841d-46b1-b256-5ddca278a891', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '88e00da8-c4df-42b6-be3b-51ad25a919f3', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Surveys American writing from the Civil War through the Cold War. Authors such as Clemens, Frost, Hurston, Bellow.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL222 CO', 'Surveys American writing from the Civil War through the Cold War. Authors such as Clemens, Frost, Hurston, Bellow.', null, '126b3de4-c5bb-49af-9821-db04b9eb0b9f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1c6a0330-6bd6-4d24-8cda-e46d8095d337', 'kuali.attribute.course.number.internal.suffix', '126b3de4-c5bb-49af-9821-db04b9eb0b9f', 'A', '7ba81293-b606-46b1-83e3-39f5c79ecdb9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7075cce9-0fed-4f74-b70c-900495ac6601', 'kuali.attribute.final.exam.use.matrix', '126b3de4-c5bb-49af-9821-db04b9eb0b9f', 1, '1ba903b9-42cc-41b6-aebf-8bd77aefc696')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('63cd249d-92bf-41fd-ac38-264723effae6', 'kuali.attribute.wait.list.level.type.key', '126b3de4-c5bb-49af-9821-db04b9eb0b9f', 'AO', '84815e0f-a0bb-4b6b-b2b5-9b908f2d3f19')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ac173e2b-a136-413e-be1f-abbffa563761', 'kuali.attribute.funding.source', '126b3de4-c5bb-49af-9821-db04b9eb0b9f', '', '3243af2f-7955-4465-be8e-b957d0590540')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('792d6c36-154d-4bca-ae9b-5ea9b8ade376', 'kuali.attribute.where.fees.attached.flag', '126b3de4-c5bb-49af-9821-db04b9eb0b9f', 0, '7e7b2815-86df-43ef-bc58-4d386ed09d6b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8fe216d6-361e-4be8-82a6-5faa461a6e7c', 'kuali.attribute.course.evaluation.indicator', '126b3de4-c5bb-49af-9821-db04b9eb0b9f', 0, 'ef425f29-9993-4a4c-be1a-a98153304f11')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('22e1b5a7-2243-4114-b18b-36d43eff6665', 'kuali.attribute.final.exam.indicator', '126b3de4-c5bb-49af-9821-db04b9eb0b9f', 'STANDARD', 'c4b904f0-2f43-4d0e-b2c9-de56914b886f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7c14a7de-6701-4808-b52e-c18a7548724f', 'kuali.attribute.wait.list.type.key', '126b3de4-c5bb-49af-9821-db04b9eb0b9f', null, '454b2b1d-bd7d-4ecb-93d6-d6a218ccc011')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c0dbdb9a-0516-4fb0-949b-76a9182c4428', 'kuali.attribute.wait.list.indicator', '126b3de4-c5bb-49af-9821-db04b9eb0b9f', 1, 'fee07bb5-6a81-4e5d-a273-6d215dadd34a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('912afbe1-a332-4727-bbae-1bb0f7db20da', 'kuali.attribute.final.exam.driver', '126b3de4-c5bb-49af-9821-db04b9eb0b9f', 'kuali.lu.exam.driver.ActivityOffering', '74543736-ade5-40a9-a889-0962b8b25d2e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d669d44a-066e-4ff7-ab21-0517bcde3406', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'ENGL222', 'ENGL', '', 'American Literature: 1865 to Present', '126b3de4-c5bb-49af-9821-db04b9eb0b9f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '846e4237-58c8-4641-83de-d63e57de7e61')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('078c4a59-298f-4f20-b97b-8ae2f64ab8cd', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '126b3de4-c5bb-49af-9821-db04b9eb0b9f', '', 'kuali.lu.code.honorsOffering', '', '0598716a-40a3-4a02-ad5b-0dc437a453c9')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('126b3de4-c5bb-49af-9821-db04b9eb0b9f', '2677933260', 'b47e3d11-634a-44a7-9e1b-9135f94c7b61')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('126b3de4-c5bb-49af-9821-db04b9eb0b9f', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('126b3de4-c5bb-49af-9821-db04b9eb0b9f', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('126b3de4-c5bb-49af-9821-db04b9eb0b9f', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('126b3de4-c5bb-49af-9821-db04b9eb0b9f', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('126b3de4-c5bb-49af-9821-db04b9eb0b9f', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('5dd7f25e-ee70-40f0-a772-dbce97c3f0a0', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'db9a6986-9b28-47e9-b1f1-9fdb5f4562df', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '06f7819f-43a7-4f7a-a939-aaf7a653d0ec')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('428d0681-4734-43f0-a5bd-6b88646aae01', 'kuali.attribute.grade.roster.level.type.key', '06f7819f-43a7-4f7a-a939-aaf7a653d0ec', 'kuali.lui.type.activity.offering.lecture', '67d2ef29-9a6d-4d1e-86a4-617d33263ed3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5b9b9d11-28ff-4f25-b902-4bebff83799a', 'regCodePrefix', '06f7819f-43a7-4f7a-a939-aaf7a653d0ec', '1', 'aaccdb63-4b6d-413a-8ed3-430900a3824d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('654cac92-ea46-4950-846e-4ce827d353ad', 'kuali.attribute.final.exam.level.type', '06f7819f-43a7-4f7a-a939-aaf7a653d0ec', 'kuali.lu.type.activity.Lecture', 'a225fdb2-27e4-4a56-8975-a8b13d9d53ed')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('8c410a55-1777-4648-86ff-2bdbb5b58ae8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '06f7819f-43a7-4f7a-a939-aaf7a653d0ec', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '53389e15-6262-4104-bd41-66f121d485b5')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('8cd23b30-1d84-4a4b-bfd8-b0bb18f77c69', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:54.396', '', 'ENGL222-CO-FO', '126b3de4-c5bb-49af-9821-db04b9eb0b9f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL222-CO-FO', '06f7819f-43a7-4f7a-a939-aaf7a653d0ec', '2a62d214-ea47-40b4-b8bf-41b71beb44bc')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('06f7819f-43a7-4f7a-a939-aaf7a653d0ec', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('03fbf00a-6a9e-4c29-ac32-520f0adf0199', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '9194e508-3a2b-4429-9a62-852dfc612977', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, '7b120ae5-ea76-42b8-955d-4a9696e3108e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ca8f3782-aa3a-4b3d-82e8-c6d03c92c59e', 'kuali.attribute.nonstd.ts.indicator', '7b120ae5-ea76-42b8-955d-4a9696e3108e', 0, '2c33fb5d-6fe0-46d0-9b9c-cfba788263e8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('586c41dd-b536-4df6-a72f-6f39333d0fbd', 'kuali.attribute.max.enrollment.is.estimate', '7b120ae5-ea76-42b8-955d-4a9696e3108e', 0, 'a0ceaaff-0d5c-40ea-879e-25057bdf4976')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('393a6078-c6d0-434c-8904-21a64f43223b', 'kuali.attribute.course.evaluation.indicator', '7b120ae5-ea76-42b8-955d-4a9696e3108e', 0, '941ddb65-8080-49a4-907c-3899fd7540c7')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('2dd28901-1580-452b-b20e-20f58564aaf0', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '7b120ae5-ea76-42b8-955d-4a9696e3108e', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'e0d935eb-ecbb-47ba-8fd5-82396a679312')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('e6de1e2f-9715-444a-91e1-73b0023d9cb2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '7b120ae5-ea76-42b8-955d-4a9696e3108e', '', 'kuali.lu.code.honorsOffering', 0, '543d0f44-33c3-42d8-a898-753e9c75203f')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('1c0b2a8c-12d3-4140-9f4a-ac5eb129f4de', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:49:54.515', '', '7b120ae5-ea76-42b8-955d-4a9696e3108e', 'R.BRADLEYM', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '53b704b7-c11f-4683-b67d-2ad8a3b9430c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e751e2d1-aff8-469c-9ee0-86448d8430d2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:54.516', '', 'ENGL222-FO-AO', '06f7819f-43a7-4f7a-a939-aaf7a653d0ec', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL222-FO-AO', '7b120ae5-ea76-42b8-955d-4a9696e3108e', '3f6e2317-cfac-40eb-b365-247e0e3e7dd1')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('54d4715e-a32f-4db8-ae7e-5c443cbf6c24', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '39580f17-26aa-4dd5-be45-834550d8bf23')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('6263fe9a-e1f5-4342-b620-8a26305b21fe', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for ENGL222 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '39580f17-26aa-4dd5-be45-834550d8bf23', 'feab2e56-b613-4558-bf20-aed07082ff6d')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('c8a7c2fa-e645-4581-b69a-fee97420f17d', 0, 'feab2e56-b613-4558-bf20-aed07082ff6d', '6fe7a7e0-6b4c-4f2f-8428-6f2e64c22f1d')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('41aca2e6-6e41-4e42-aeb6-4072bed298b5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '5cc1c3c8-395c-4c1a-a3cb-ebd618048a3f')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('af7a8ca1-f1cc-4e7f-a00b-6957fb9955c9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '06f7819f-43a7-4f7a-a939-aaf7a653d0ec', 'CL 1', 'CL 1', 'c2ff2dbd-9863-46a9-ad17-0118431e96fa')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('28135d72-826d-403d-af20-785ac7cfa2a6', 'kuali.lui.type.activity.offering.lecture', 'c2ff2dbd-9863-46a9-ad17-0118431e96fa', '3fe4bd6d-686e-4ff1-8345-5e653d5a20aa')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('39580f17-26aa-4dd5-be45-834550d8bf23', '7b120ae5-ea76-42b8-955d-4a9696e3108e')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('6fe7a7e0-6b4c-4f2f-8428-6f2e64c22f1d', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('6fe7a7e0-6b4c-4f2f-8428-6f2e64c22f1d', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('6fe7a7e0-6b4c-4f2f-8428-6f2e64c22f1d', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('6fe7a7e0-6b4c-4f2f-8428-6f2e64c22f1d', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('5cc1c3c8-395c-4c1a-a3cb-ebd618048a3f', '7b120ae5-ea76-42b8-955d-4a9696e3108e')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('5cc1c3c8-395c-4c1a-a3cb-ebd618048a3f', '06f7819f-43a7-4f7a-a939-aaf7a653d0ec')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('3fe4bd6d-686e-4ff1-8345-5e653d5a20aa', '7b120ae5-ea76-42b8-955d-4a9696e3108e')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='c2ff2dbd-9863-46a9-ad17-0118431e96fa' where ID='3fe4bd6d-686e-4ff1-8345-5e653d5a20aa'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('402b6770-0ece-4e16-8e1e-d561cf4ad97b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'db9a6986-9b28-47e9-b1f1-9fdb5f4562df', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'd2e74f99-7dc9-49c2-852f-a923a191786d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d4d2c08a-e2fc-4a0e-8fac-663677b11640', 'kuali.attribute.registration.group.aocluster.id', 'd2e74f99-7dc9-49c2-852f-a923a191786d', 'c2ff2dbd-9863-46a9-ad17-0118431e96fa', 'b59085d3-045a-4f1e-a8d4-9261fe92476e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('04fd4cd1-1bd3-4861-bca8-fbac0bb185af', 'kuali.attribute.registration.group.is.generated', 'd2e74f99-7dc9-49c2-852f-a923a191786d', 1, 'ba936a38-85a4-47f8-95aa-235af2b3bdaf')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('8dfa6b7c-a2e1-4c3a-86eb-b288e835b3e6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'd2e74f99-7dc9-49c2-852f-a923a191786d', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'f378bfff-a23f-4850-a240-618d78fecbc7')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('6d6bda9c-b3e4-4a82-a79f-287d7a4bbcd6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:54.976', '', 'ENGL222-FO-RG', '06f7819f-43a7-4f7a-a939-aaf7a653d0ec', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL222-FO-RG', 'd2e74f99-7dc9-49c2-852f-a923a191786d', 'bbbc762c-58a6-4f44-9f11-7d876acf0ca0')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('63e4fe94-bd05-4020-b2bc-58ea89a0be3d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:54.977', '', 'ENGL222-RG-AO', 'd2e74f99-7dc9-49c2-852f-a923a191786d', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL222-RG-AO', '7b120ae5-ea76-42b8-955d-4a9696e3108e', '1088ce87-c5af-4a20-a309-51d7c120f658')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('a204f1da-7ab9-4f0b-96c2-a3cc9c50ad25', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'CLUID-ENGL278-198001000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL278E CO', '.', null, 'ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('049a1aa0-8089-4767-8728-bc7f069237b9', 'kuali.attribute.wait.list.level.type.key', 'ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', 'AO', 'e02259aa-eeef-439e-ac95-ca1ce8cc7731')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a400c68b-cbd5-437b-bd9d-511144bafa06', 'kuali.attribute.course.number.internal.suffix', 'ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', 'A', '6fe82192-931d-48ce-b910-b228f56015fb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0d96dd57-b09a-4d08-9f85-87302c36046a', 'kuali.attribute.funding.source', 'ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', '', '49a75d7c-daaa-44df-9e67-30dd4db2e0f0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1295128d-ea49-4a43-b9b3-eb148ce84cad', 'kuali.attribute.where.fees.attached.flag', 'ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', 0, 'e61c894f-b394-4fc2-9380-4d0f7640dd23')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dec4d8c9-6520-429c-9831-782b134386ce', 'kuali.attribute.course.evaluation.indicator', 'ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', 0, '946271c9-a718-4211-aa0a-cb9003cc58c6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2c7ee694-3f3f-4623-8fd3-020c43edc870', 'kuali.attribute.wait.list.indicator', 'ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', 1, 'b79d433d-289f-40cb-9b9a-59fc05165f2f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('809e1227-a510-4d00-9fb7-723950eb5a7a', 'kuali.attribute.final.exam.driver', 'ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', 'kuali.lu.exam.driver.ActivityOffering', '0909774c-727d-4d38-a168-edaf312cc7bc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a2b88c7c-74e1-4b52-b09d-8710cfb32a53', 'kuali.attribute.wait.list.type.key', 'ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', null, '0797ecea-972e-425e-bd51-32b4b882fe39')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('793a4043-9e69-4506-9608-6fe67567e8c2', 'kuali.attribute.final.exam.indicator', 'ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', 'STANDARD', 'd3ea8ca7-5882-4d15-8a08-7313976fa06c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('686ce143-2b82-46ab-961a-4b404d1c472c', 'kuali.attribute.final.exam.use.matrix', 'ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', 1, '1ff2d841-0da4-43e9-ac39-ee2354873ab6')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('1df6cf19-0e25-490d-93d0-6d6128b34744', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'ENGL278E', 'ENGL', '', 'Special Topics in Literature', 'ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', '', '', 'kuali.lui.identifier.state.active', 'E', 'kuali.lui.identifier.type.official', '', '8770c7f9-d882-4735-bc56-f4f3625d9bad')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('6722fb48-4ad2-48ed-a3d3-c01ceec732b8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', '', 'kuali.lu.code.honorsOffering', '', 'c4b4ae8a-8857-4713-8905-e8b381f34efc')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', '2677933260', 'bc7abb13-d80f-48c5-afbc-3c8f28934028')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('58a1ed7a-097d-4904-95d0-e00943157aad', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'ed9e10ff-8739-40d0-83da-a2b92489e055', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '9ec5704b-7e85-4fcc-ac2b-f817d8106514')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1e7dfb1f-3887-4d0f-932a-c40f5a6c7280', 'regCodePrefix', '9ec5704b-7e85-4fcc-ac2b-f817d8106514', '1', 'e0a0807b-2887-4e07-a9ae-fcc24776701e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('35bef116-38dc-45c6-ae1b-2bfba0d9f8a5', 'kuali.attribute.final.exam.level.type', '9ec5704b-7e85-4fcc-ac2b-f817d8106514', 'kuali.lu.type.activity.Lecture', 'de8eb2d0-b387-4f75-9d40-38d9164d608e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dd12a77f-23a6-4d46-ac43-4b31ecf216c8', 'kuali.attribute.grade.roster.level.type.key', '9ec5704b-7e85-4fcc-ac2b-f817d8106514', 'kuali.lui.type.activity.offering.lecture', 'a7ca654c-31c0-4a8a-a19b-f6612a79865f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d393ddfb-7ef0-41ce-963d-0b6e942e2fc2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '9ec5704b-7e85-4fcc-ac2b-f817d8106514', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '5e0988ed-3e71-4273-a125-15fb60affaa9')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('79b6c2f2-9b28-4870-a9b3-cd6091a97072', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:57.497', '', 'ENGL278E-CO-FO', 'ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL278E-CO-FO', '9ec5704b-7e85-4fcc-ac2b-f817d8106514', '94a21d4f-c627-4cc8-9654-a7f87cfb8151')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('9ec5704b-7e85-4fcc-ac2b-f817d8106514', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('bee977e5-76d2-4a7c-bcc5-bb689fff91f2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'c4eab8f6-4f3e-4561-ae1c-b40dd59c517f', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, '6634f351-971b-414b-a7b1-c07655eda9e9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('60ced859-776a-45aa-bd2e-b31257affe99', 'kuali.attribute.nonstd.ts.indicator', '6634f351-971b-414b-a7b1-c07655eda9e9', 0, 'f1f6ea06-9bc5-472e-827c-36adb4dd1064')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('62606c6e-4892-4b3f-8f44-f923904790e4', 'kuali.attribute.course.evaluation.indicator', '6634f351-971b-414b-a7b1-c07655eda9e9', 0, '902d7162-f813-47b3-86cc-b46ddf82c9e0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d64b32b6-adfe-4f3c-be08-a89e563fe8a8', 'kuali.attribute.max.enrollment.is.estimate', '6634f351-971b-414b-a7b1-c07655eda9e9', 0, '8d2b1f5b-e7bf-4125-b894-dd6adca6e8aa')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('e493f18f-cc41-43af-bc69-662ad6068627', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '6634f351-971b-414b-a7b1-c07655eda9e9', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'f7c7e59d-3982-4a22-bf9d-7e52dadfde27')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('9ff785a5-653f-4541-99e7-5b83a6c3562f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '6634f351-971b-414b-a7b1-c07655eda9e9', '', 'kuali.lu.code.honorsOffering', 0, '27b76aa9-874f-4299-863a-50196b34d61c')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('d1e3d90c-2ce7-422c-af5e-dba5da3a6661', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:49:57.853', '', '6634f351-971b-414b-a7b1-c07655eda9e9', 'O.DUANES', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '230e3ae6-28bf-4e45-9af0-2c0b82d75a59')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('1ea49ce6-dadd-490e-b5c0-3c0cd9ddd6e7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:57.853', '', 'ENGL278E-FO-AO', '9ec5704b-7e85-4fcc-ac2b-f817d8106514', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL278E-FO-AO', '6634f351-971b-414b-a7b1-c07655eda9e9', '9ac362d0-f3f6-4e5a-9f20-edf1fff269c5')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('831a1913-5d9a-409a-8d79-38d5bfde0e2a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '08b7e59d-ed39-4086-924b-52e0884230b0')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('b44ac956-58f7-4825-8a3d-a6ae03c60a2e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A Schedule Request for ENGL278E section WB11', 'Schedule request for ENGL278E - A', 'A Schedule Request for ENGL278E section WB11', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', 'ba5e092f-0bfd-493e-810f-a982f43b44e4', '08b7e59d-ed39-4086-924b-52e0884230b0', '382b06ee-6309-439c-ae82-f1282f2de062')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('c3d30409-e35d-4a01-afc5-6c22e30cd98e', 0, '382b06ee-6309-439c-ae82-f1282f2de062', '2beac07e-cfd4-4ef2-a92e-fc102a1b7c75')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('cc0315fe-a2fa-4d5a-8e1a-a70a732b84be', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '78b46138-b804-4c65-ba5a-5a1350c70b18')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('d7bde016-6474-4169-ba8b-1953e02701d4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '9ec5704b-7e85-4fcc-ac2b-f817d8106514', 'CL 1', 'CL 1', 'cbe679d1-040a-4250-bc20-884b48d986ca')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('f1bbba24-fc5d-40a4-a8cd-d48ca77ec4da', 'kuali.lui.type.activity.offering.lecture', 'cbe679d1-040a-4250-bc20-884b48d986ca', '7dcd6d13-f0a1-4e69-b627-89f4cce903c5')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('08b7e59d-ed39-4086-924b-52e0884230b0', '6634f351-971b-414b-a7b1-c07655eda9e9')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('2beac07e-cfd4-4ef2-a92e-fc102a1b7c75', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('2beac07e-cfd4-4ef2-a92e-fc102a1b7c75', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('2beac07e-cfd4-4ef2-a92e-fc102a1b7c75', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('78b46138-b804-4c65-ba5a-5a1350c70b18', '6634f351-971b-414b-a7b1-c07655eda9e9')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('78b46138-b804-4c65-ba5a-5a1350c70b18', '9ec5704b-7e85-4fcc-ac2b-f817d8106514')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('7dcd6d13-f0a1-4e69-b627-89f4cce903c5', '6634f351-971b-414b-a7b1-c07655eda9e9')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='cbe679d1-040a-4250-bc20-884b48d986ca' where ID='7dcd6d13-f0a1-4e69-b627-89f4cce903c5'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('5d1d94a5-a3b8-4203-90ad-f1a2643dac84', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'ed9e10ff-8739-40d0-83da-a2b92489e055', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '5b652c39-d4a9-4d51-a139-947f34806f1c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cca6ca24-be4c-4a33-85a9-057e8fe3a584', 'kuali.attribute.registration.group.aocluster.id', '5b652c39-d4a9-4d51-a139-947f34806f1c', 'cbe679d1-040a-4250-bc20-884b48d986ca', '14b455b9-851a-4ea9-bf64-dcb67eab2834')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7a1b3e90-bbf4-4317-960b-69132d84aec0', 'kuali.attribute.registration.group.is.generated', '5b652c39-d4a9-4d51-a139-947f34806f1c', 1, '3c4e4335-2f07-491d-a126-5f4fcdb349dd')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('b213e900-4cee-4a23-96a1-acb6a89584cf', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', '5b652c39-d4a9-4d51-a139-947f34806f1c', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'fdcb34d0-ce7a-4c4b-b9a8-83ff09d1e53f')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('4c7cca08-e66b-474b-a97c-2c1fa31def9a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:58.335', '', 'ENGL278E-FO-RG', '9ec5704b-7e85-4fcc-ac2b-f817d8106514', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL278E-FO-RG', '5b652c39-d4a9-4d51-a139-947f34806f1c', 'ba588fd0-a7bd-4dad-a404-4f69c5f9d25d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('79bc1125-3937-4242-970d-7dac406531e4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:49:58.336', '', 'ENGL278E-RG-AO', '5b652c39-d4a9-4d51-a139-947f34806f1c', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL278E-RG-AO', '6634f351-971b-414b-a7b1-c07655eda9e9', '090e5b97-2f5b-418d-8896-ff02b5a2e736')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('32834ef2-e0e0-4d49-8123-ea2c835dddf6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '9fb75593-9445-44ce-8d82-53e1634ae52d', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'WMST498M CO', '.', null, 'ba9c247e-ab95-4a64-819b-2f3fdb2609b5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('feac429d-5d9c-4594-8b75-f42ec8c3f71f', 'kuali.attribute.final.exam.use.matrix', 'ba9c247e-ab95-4a64-819b-2f3fdb2609b5', 1, '2158e70f-2f19-444e-8b08-b4469bea486d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('816bfacc-b87a-41b8-b1de-7ceb467982d5', 'kuali.attribute.final.exam.indicator', 'ba9c247e-ab95-4a64-819b-2f3fdb2609b5', 'STANDARD', '2f0e1dee-e7dd-4556-98b8-f862b1ac8901')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1f61ecfc-3b52-418a-aa20-053b4f14bc36', 'kuali.attribute.course.number.internal.suffix', 'ba9c247e-ab95-4a64-819b-2f3fdb2609b5', 'A', '5475f7be-be95-44a3-866a-a825acb7e439')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('501b0ff4-dfcc-439c-b913-baeb7807d95b', 'kuali.attribute.where.fees.attached.flag', 'ba9c247e-ab95-4a64-819b-2f3fdb2609b5', 0, '04ca923d-6b3f-48dd-860d-b7a2621cc48f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('77c9f44b-ae8a-4eef-95c1-88e7e20ac89f', 'kuali.attribute.wait.list.type.key', 'ba9c247e-ab95-4a64-819b-2f3fdb2609b5', null, '4d0faf72-3203-4a97-b65d-43bae1a286ea')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('688c6907-f037-4616-af74-8c229d732045', 'kuali.attribute.wait.list.indicator', 'ba9c247e-ab95-4a64-819b-2f3fdb2609b5', 1, '60bf7b52-8e17-4c30-ae17-ec48d1f6bea8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f7f56b9e-1ac1-46f5-96d2-114b1fe737c5', 'kuali.attribute.wait.list.level.type.key', 'ba9c247e-ab95-4a64-819b-2f3fdb2609b5', 'AO', '6badc6b0-bd3f-4068-9735-6a7836e0892c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('86586b9d-1362-4f8d-8305-2bf59ade7d12', 'kuali.attribute.course.evaluation.indicator', 'ba9c247e-ab95-4a64-819b-2f3fdb2609b5', 0, '9a7f1b05-9019-4057-b490-ec814f7abbba')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c1e581a1-d176-471a-8f49-ff02f2a1155e', 'kuali.attribute.final.exam.driver', 'ba9c247e-ab95-4a64-819b-2f3fdb2609b5', 'kuali.lu.exam.driver.ActivityOffering', 'f6aa3cb5-a602-4f11-a01a-b99dc1bce1ed')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('34166c2d-c059-4837-adf1-143026c6e175', 'kuali.attribute.funding.source', 'ba9c247e-ab95-4a64-819b-2f3fdb2609b5', '', 'dc0e4368-c8f6-4f37-a01f-ffb560fd9c1f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('f6dd411a-b42e-4ef2-b22b-c9fd0d22973e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'WMST498M', 'WMST', '', 'Advanced Special Topics in Women''s Studies', 'ba9c247e-ab95-4a64-819b-2f3fdb2609b5', '', '', 'kuali.lui.identifier.state.active', 'M', 'kuali.lui.identifier.type.official', '', '51356722-08db-4096-9937-c1f9f71668ca')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('5224fcd2-c23d-47f1-a80e-a784425820a7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'ba9c247e-ab95-4a64-819b-2f3fdb2609b5', '', 'kuali.lu.code.honorsOffering', '', 'c4b54067-c2de-4a96-bafe-38716d1fe68f')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('ba9c247e-ab95-4a64-819b-2f3fdb2609b5', '4014660630', 'a6163eab-f838-456e-8818-7d3d1712b4b7')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('ba9c247e-ab95-4a64-819b-2f3fdb2609b5', '4014660630')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ba9c247e-ab95-4a64-819b-2f3fdb2609b5', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ba9c247e-ab95-4a64-819b-2f3fdb2609b5', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ba9c247e-ab95-4a64-819b-2f3fdb2609b5', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ba9c247e-ab95-4a64-819b-2f3fdb2609b5', 'kuali.creditType.credit.degree.1.0,1.5,2.0,2.5,3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('81c5e2f9-911a-46a4-8953-d1f71dabf220', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '4ddd26da-68e3-406f-b62f-1b36beb96f0f', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '098dec27-c3d2-44c5-ac66-d3223b1ce109')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a55c1aa5-4be1-4cd6-bd6b-302b5250fae6', 'kuali.attribute.final.exam.level.type', '098dec27-c3d2-44c5-ac66-d3223b1ce109', 'kuali.lu.type.activity.Lecture', '464de983-9956-46e5-999f-9fd363a53d0a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bc9fd6c1-4a49-407e-b353-847a5aff050c', 'kuali.attribute.grade.roster.level.type.key', '098dec27-c3d2-44c5-ac66-d3223b1ce109', 'kuali.lui.type.activity.offering.lecture', '43e2283a-4aa4-4c99-a04f-10483fc5bf60')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e3e7d1aa-d5f2-4df7-8671-1eade3a8e8ea', 'regCodePrefix', '098dec27-c3d2-44c5-ac66-d3223b1ce109', '1', '05a14d31-0c29-4e36-992e-50d306fb95b2')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('90059581-7b53-4830-8333-9331006e20e4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '098dec27-c3d2-44c5-ac66-d3223b1ce109', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c4ff688e-e8bb-4c39-ac74-5720c547b330')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('10d75354-3a59-48ee-b47e-5394d5efaa3d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:01.059', '', 'WMST498M-CO-FO', 'ba9c247e-ab95-4a64-819b-2f3fdb2609b5', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'WMST498M-CO-FO', '098dec27-c3d2-44c5-ac66-d3223b1ce109', '6552babc-b279-41b1-b322-5306a85fec05')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('098dec27-c3d2-44c5-ac66-d3223b1ce109', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('81e29ffb-ce0e-475f-9371-8dd52d92c200', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '44ad3dfb-8855-4c58-ab14-33afc312293b', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, '9df6e80c-6a2a-452b-8659-d0d4068d1d68')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('67f2956e-24c8-4c3f-ba0c-2a8c173b0cdd', 'kuali.attribute.course.evaluation.indicator', '9df6e80c-6a2a-452b-8659-d0d4068d1d68', 0, '52d53986-ba33-47e3-a770-49e70388e6d1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b05a3d8e-c8f4-47df-b9eb-046883716285', 'kuali.attribute.nonstd.ts.indicator', '9df6e80c-6a2a-452b-8659-d0d4068d1d68', 0, 'bd4b1cd5-8b0f-4754-9f23-c335bb59a636')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b97d2578-6e59-4d83-9d33-2da0d57bc724', 'kuali.attribute.max.enrollment.is.estimate', '9df6e80c-6a2a-452b-8659-d0d4068d1d68', 0, 'b13c3bc3-4053-45c8-8c35-80eb6f78b5a6')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('97cb9f46-0d5b-48cd-ac37-4907e14b303c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '9df6e80c-6a2a-452b-8659-d0d4068d1d68', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '21ddd932-83d0-4564-afe6-6dea39160b89')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('7775b7db-97a9-489d-babf-46437ae24a7d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '9df6e80c-6a2a-452b-8659-d0d4068d1d68', '', 'kuali.lu.code.honorsOffering', 0, '76055a19-ee98-426f-9a99-ab28c96adb86')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('1b9c036f-7b7d-47ee-abdc-71ebfa0538e8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:01.141', '', '9df6e80c-6a2a-452b-8659-d0d4068d1d68', 'N.ANNED', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '0ab31f88-4993-4ec9-9dca-9d19fb1e5495')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('71ccc64a-15a5-4029-9e08-cb87b01aed3b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:01.141', '', 'WMST498M-FO-AO', '098dec27-c3d2-44c5-ac66-d3223b1ce109', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'WMST498M-FO-AO', '9df6e80c-6a2a-452b-8659-d0d4068d1d68', 'ebb9f793-83ba-40ab-9749-eb8a5fad46ce')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('73d8a098-b3c5-4a08-a049-9030348f2432', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'f6ed6cff-affc-468b-8393-6e39d336c3bb')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('74bdf067-460c-42bf-a4a3-23826a468c68', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for WMST498M - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'f6ed6cff-affc-468b-8393-6e39d336c3bb', '52dad939-d1b7-4de3-aa4a-d917ed28f6a5')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('df7d44a5-3522-48cc-a662-5d39d14c031f', 0, '52dad939-d1b7-4de3-aa4a-d917ed28f6a5', '72972a42-83bc-409d-9c47-99c8a1a4e85a')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('574890fc-3080-4407-8e6b-b212ca64533f', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Athlete Seatpool', '1c2e7786-a2ab-4778-8e81-ea540c60b9b2', 1, 2, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'e146c7d7-7f8d-4990-96ec-2d95c48f4ead')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('f6ed6cff-affc-468b-8393-6e39d336c3bb', '9df6e80c-6a2a-452b-8659-d0d4068d1d68')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('72972a42-83bc-409d-9c47-99c8a1a4e85a', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('72972a42-83bc-409d-9c47-99c8a1a4e85a', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('72972a42-83bc-409d-9c47-99c8a1a4e85a', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('72972a42-83bc-409d-9c47-99c8a1a4e85a', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('8b96bf4b-c868-4f94-b224-0edfcc42f3f0', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Junior Seatpool', '4bae9f12-5901-4db9-b4a8-4dcc20781db5', 2, 4, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'b8d1f07b-e571-45b2-9b65-f6dcd0664fa6')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='574890fc-3080-4407-8e6b-b212ca64533f', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='9df6e80c-6a2a-452b-8659-d0d4068d1d68', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Athlete Seatpool', POPULATION_ID='1c2e7786-a2ab-4778-8e81-ea540c60b9b2', PROCESSING_PRIORITY=1, SEAT_LIMIT=2, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='e146c7d7-7f8d-4990-96ec-2d95c48f4ead' and VER_NBR=1
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('c509eee3-eec5-4f3a-8c85-52b779accddd', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '1251e8da-ea5f-4a1f-9e9b-a73cd965d898')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('a1d78800-9413-4cc9-a541-9a6fd6e809b8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '098dec27-c3d2-44c5-ac66-d3223b1ce109', 'CL 1', 'CL 1', '495dfdfb-b1f1-4815-84c1-0eab6bd464e6')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('7a325a68-b1e5-4a77-97e1-b963bd68c530', 'kuali.lui.type.activity.offering.lecture', '495dfdfb-b1f1-4815-84c1-0eab6bd464e6', 'b080dad8-d6dc-4574-89cf-877cab7fcc77')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='8b96bf4b-c868-4f94-b224-0edfcc42f3f0', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='9df6e80c-6a2a-452b-8659-d0d4068d1d68', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Junior Seatpool', POPULATION_ID='4bae9f12-5901-4db9-b4a8-4dcc20781db5', PROCESSING_PRIORITY=2, SEAT_LIMIT=4, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='b8d1f07b-e571-45b2-9b65-f6dcd0664fa6' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('1251e8da-ea5f-4a1f-9e9b-a73cd965d898', '9df6e80c-6a2a-452b-8659-d0d4068d1d68')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('1251e8da-ea5f-4a1f-9e9b-a73cd965d898', '098dec27-c3d2-44c5-ac66-d3223b1ce109')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('b080dad8-d6dc-4574-89cf-877cab7fcc77', '9df6e80c-6a2a-452b-8659-d0d4068d1d68')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='495dfdfb-b1f1-4815-84c1-0eab6bd464e6' where ID='b080dad8-d6dc-4574-89cf-877cab7fcc77'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('780c9c43-3a25-4955-8c4d-7686dace9fa8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '4ddd26da-68e3-406f-b62f-1b36beb96f0f', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '3a25ebd1-ef49-4319-a33c-14e0f580f920')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d3067f23-fd48-4289-ad79-1f4d884a73c7', 'kuali.attribute.registration.group.aocluster.id', '3a25ebd1-ef49-4319-a33c-14e0f580f920', '495dfdfb-b1f1-4815-84c1-0eab6bd464e6', '84ebfbcf-96c0-48f6-925a-dc39e79b36f2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('35daf9a2-0d86-4bda-b79e-3bac7800222d', 'kuali.attribute.registration.group.is.generated', '3a25ebd1-ef49-4319-a33c-14e0f580f920', 1, '91397d1f-1cbf-459f-9635-85d3ad86face')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('4c6fd93d-b763-4290-b77b-828514daa12b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', '3a25ebd1-ef49-4319-a33c-14e0f580f920', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'f0e8b762-1f9b-4d36-ac9c-f9dea88de6ad')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('760cdcd5-ecae-48d2-bcc7-f81828b657c6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:01.757', '', 'WMST498M-FO-RG', '098dec27-c3d2-44c5-ac66-d3223b1ce109', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'WMST498M-FO-RG', '3a25ebd1-ef49-4319-a33c-14e0f580f920', 'c7bad21d-fe42-40a7-91f1-5c9dcb900d3c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('277829e5-81be-4a19-8c9a-106ee9c544a5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:01.758', '', 'WMST498M-RG-AO', '3a25ebd1-ef49-4319-a33c-14e0f580f920', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'WMST498M-RG-AO', '9df6e80c-6a2a-452b-8659-d0d4068d1d68', 'e2ecf00c-6723-47a0-adec-ba7e214912ef')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('6ec9b441-1a9d-4cce-b12d-67e3c6b14eb1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '6f72557a-cd52-4af3-9f7d-93aef829c882', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Introduction to changing nature of books, texts, and narrative in Information Age. Role of book in relation to other media, history of computers and writing, influence of computing on contemporary literature and culture, emerging forms of digital narrative and reading. Practical skills taught range from how to find digital literature and other texts online to using Web media to create literary works.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL295 CO', 'Introduction to changing nature of books, texts, and narrative in Information Age. Role of book in relation to other media, history of computers and writing, influence of computing on contemporary literature and culture, emerging forms of digital narrative and reading. Practical skills taught range from how to find digital literature and other texts online to using Web media to create literary works.', null, 'acebaa26-3d5d-43ac-b882-79c7614a335a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3f1ed71b-c267-4890-a071-99ce089c5139', 'kuali.attribute.final.exam.indicator', 'acebaa26-3d5d-43ac-b882-79c7614a335a', 'STANDARD', 'cb6c211b-0fc4-4084-82a6-aca25251a3dd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c3cd02bb-3e00-4190-a0f1-22437243284b', 'kuali.attribute.where.fees.attached.flag', 'acebaa26-3d5d-43ac-b882-79c7614a335a', 0, '7d6bc259-12e8-4448-8d85-1dd7e19fca1b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8bc3c868-65a3-4da6-bb87-3008abdeb806', 'kuali.attribute.final.exam.use.matrix', 'acebaa26-3d5d-43ac-b882-79c7614a335a', 1, '5c2f74ff-c263-4794-a859-8be8fa355d10')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('83c5a364-875d-4f97-b2d9-a232bd529243', 'kuali.attribute.final.exam.driver', 'acebaa26-3d5d-43ac-b882-79c7614a335a', 'kuali.lu.exam.driver.ActivityOffering', 'bf7fa354-cff7-4cfb-8a58-251d4bb1e6e3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('699caca7-c348-4645-950e-1447d0e55a34', 'kuali.attribute.course.evaluation.indicator', 'acebaa26-3d5d-43ac-b882-79c7614a335a', 0, '9aaa0d8c-1849-44f4-b38d-71878964ac14')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('13ad54c9-ad56-45ad-bf05-11898e5df50a', 'kuali.attribute.wait.list.level.type.key', 'acebaa26-3d5d-43ac-b882-79c7614a335a', 'AO', 'a31ea681-b707-4b49-ad9c-ff12d0e13b30')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('790f3868-5137-4c49-9ea7-96a7f86009cb', 'kuali.attribute.course.number.internal.suffix', 'acebaa26-3d5d-43ac-b882-79c7614a335a', 'A', 'cd680297-1a63-4496-8062-4c44aabcf9f3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('75a60e17-4133-4fff-aaaa-ba108202f332', 'kuali.attribute.funding.source', 'acebaa26-3d5d-43ac-b882-79c7614a335a', '', '169de646-991e-4246-acb1-312c70ee92e8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('33d77d24-540e-4f1e-9159-15b547695bef', 'kuali.attribute.wait.list.type.key', 'acebaa26-3d5d-43ac-b882-79c7614a335a', null, '058e3a65-7eb8-4d7f-b26b-31822fe7d5fd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a9438b56-e3fe-4750-9460-95e9c1f916f5', 'kuali.attribute.wait.list.indicator', 'acebaa26-3d5d-43ac-b882-79c7614a335a', 1, '88636b6c-57fa-432d-8a35-6c276d60f9ef')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('3016ad69-5960-4348-94cb-36e32de022a5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'ENGL295', 'ENGL', '', 'Literature in a Wired World', 'acebaa26-3d5d-43ac-b882-79c7614a335a', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c2aac5e5-6c3d-4cb0-ae07-653760c2450a')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('6b6fe131-4878-4981-b78c-b85307eb0af8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'acebaa26-3d5d-43ac-b882-79c7614a335a', '', 'kuali.lu.code.honorsOffering', '', 'f56a91a3-4686-4e87-8b0f-d51e7f7546e2')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('acebaa26-3d5d-43ac-b882-79c7614a335a', '2677933260', '620b6e30-647b-4ff7-8107-31e2d88b8560')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('acebaa26-3d5d-43ac-b882-79c7614a335a', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('acebaa26-3d5d-43ac-b882-79c7614a335a', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('acebaa26-3d5d-43ac-b882-79c7614a335a', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('acebaa26-3d5d-43ac-b882-79c7614a335a', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('acebaa26-3d5d-43ac-b882-79c7614a335a', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('05cc9807-5afe-44a9-a523-6fd433d23874', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '1af2eff3-2602-4e67-9e64-9f62ae7f932e', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'f4917908-1824-448a-b67f-6b4426d7668b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dc21ac18-e9f0-42b8-8788-3ef37412893e', 'kuali.attribute.final.exam.level.type', 'f4917908-1824-448a-b67f-6b4426d7668b', 'kuali.lu.type.activity.Lecture', '6fb5a6fd-4cb0-46ae-8dcb-2c150c124f27')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a90f6153-ce5e-48d4-a733-5e062d71ecbf', 'kuali.attribute.grade.roster.level.type.key', 'f4917908-1824-448a-b67f-6b4426d7668b', 'kuali.lui.type.activity.offering.lecture', '7bb378b9-42bf-4efa-87f2-51dab9d9b691')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('655d74ad-18ba-4300-bcee-c6f2445b8d9e', 'regCodePrefix', 'f4917908-1824-448a-b67f-6b4426d7668b', '1', '4c713a94-38cd-4d6f-bc97-a0b2f80a9ca1')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('953282bf-6e79-40ba-a6e2-7759914047f8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', 'f4917908-1824-448a-b67f-6b4426d7668b', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'af094361-e1fa-4273-b8db-ba1f04ad38d5')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('c91b5a56-9f79-47a6-85fe-7ead2ac87cf2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:04.267', '', 'ENGL295-CO-FO', 'acebaa26-3d5d-43ac-b882-79c7614a335a', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL295-CO-FO', 'f4917908-1824-448a-b67f-6b4426d7668b', 'b0535e5b-b2f7-40e0-b10e-5a8a65f12d4d')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('f4917908-1824-448a-b67f-6b4426d7668b', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('3ff5a413-d26b-4b21-a83c-a89e03c8dc18', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'ac92256a-2100-48b4-a938-b2c614a8ebce', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, '6cd422b7-d7b6-4791-9bad-8794f4a7fc60')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('51f193a7-dc02-4b98-9e98-6e91f5f40306', 'kuali.attribute.nonstd.ts.indicator', '6cd422b7-d7b6-4791-9bad-8794f4a7fc60', 0, 'bb14f95d-468f-4d3c-a676-f6b71189b600')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e3bc74c2-d187-4c6a-9c7e-8d0aed61d55f', 'kuali.attribute.course.evaluation.indicator', '6cd422b7-d7b6-4791-9bad-8794f4a7fc60', 0, '0acae6c8-6b09-4c17-885d-91dd7d0e9d5f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('113c1dac-53e2-4045-b271-91f32976f672', 'kuali.attribute.max.enrollment.is.estimate', '6cd422b7-d7b6-4791-9bad-8794f4a7fc60', 0, 'a3730947-454a-4647-b283-e6cb254f2aee')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('2179e512-c65b-460d-9045-13277ecfaf41', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '6cd422b7-d7b6-4791-9bad-8794f4a7fc60', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ba2055c7-b72b-47c8-9be0-555e5a455b3b')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('b4bb2147-220a-40ec-a047-696afccf6574', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '6cd422b7-d7b6-4791-9bad-8794f4a7fc60', '', 'kuali.lu.code.honorsOffering', 0, '38b5ecee-b1bd-4c0a-abff-e1ac074fad4b')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('80788799-1e87-4b0e-afbb-5824a9dcb09a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:04.351', '', '6cd422b7-d7b6-4791-9bad-8794f4a7fc60', 'S.JULIAG', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'd5506573-f2f9-4dbd-a45e-357e94e74e54')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('29e10f88-08a4-4cd0-9bdd-77216584d358', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:04.351', '', 'ENGL295-FO-AO', 'f4917908-1824-448a-b67f-6b4426d7668b', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL295-FO-AO', '6cd422b7-d7b6-4791-9bad-8794f4a7fc60', '9c8a8e8e-72af-4923-865c-76546cf9ff5e')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('e1b752b3-5969-4b40-9018-6c799799dca1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '21aaecc6-5c57-4f99-b732-9caeb879d855')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('8d930789-0c8f-45a7-8e61-ade17062f0b1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A Schedule Request for ENGL295 section WB11', 'Schedule request for ENGL295 - A', 'A Schedule Request for ENGL295 section WB11', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', 'd6e3e821-aae5-43f7-a9ea-63f5351c59d8', '21aaecc6-5c57-4f99-b732-9caeb879d855', '5eaa15c9-ddf2-47ef-99a4-6dafd2dc5cea')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('2d75b6ae-f976-4edf-a47a-489a655471b8', 0, '5eaa15c9-ddf2-47ef-99a4-6dafd2dc5cea', '693a1a7b-568e-43d2-8fd5-629a45e89cec')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('046b3a34-0f36-454f-b956-44fb79861c40', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'ee9ae19f-aaff-4246-846a-a596f5ee6b41')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ff25b532-4eff-4dd4-9062-f2c255021d23', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '074091f2-c391-4e95-9784-950f2ca7770b', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, 'd30166f7-18b5-4b3c-8364-a1705feaccf4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bbf9d681-1cb1-4760-87f3-cad70b82738e', 'kuali.attribute.course.evaluation.indicator', 'd30166f7-18b5-4b3c-8364-a1705feaccf4', 0, '6dd42370-def0-4071-8be2-97285cc7d17b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fd8e1354-b67b-4861-92ad-4b182acc92fd', 'kuali.attribute.nonstd.ts.indicator', 'd30166f7-18b5-4b3c-8364-a1705feaccf4', 0, 'c13df5d5-98ff-4781-b4ea-b94ca496486c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c9ec9af6-a9e5-4740-949f-d927f36666ac', 'kuali.attribute.max.enrollment.is.estimate', 'd30166f7-18b5-4b3c-8364-a1705feaccf4', 0, 'ffbbbbe8-fe99-4fb4-81cf-264eddcc24b4')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('66c12610-9685-4b37-a964-b11f45e3078a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'B', '', '', '', 'd30166f7-18b5-4b3c-8364-a1705feaccf4', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'e098c65d-18c0-4af8-a8e2-9cc4b08f25f8')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('e194314d-6e14-49ff-896c-b013dda61468', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'd30166f7-18b5-4b3c-8364-a1705feaccf4', '', 'kuali.lu.code.honorsOffering', 0, 'd4580906-0dde-4f4a-a37d-e0a7403cee15')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('56512d69-c312-488c-9d16-47c5cfad83cb', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:04.74', '', 'd30166f7-18b5-4b3c-8364-a1705feaccf4', 'C.CORNELISB', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'c553b26c-f441-49d5-bedb-4a3c66ddb670')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('58812853-5242-4e48-9df2-9433cf07667f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:04.74', '', 'ENGL295-FO-AO', 'f4917908-1824-448a-b67f-6b4426d7668b', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL295-FO-AO', 'd30166f7-18b5-4b3c-8364-a1705feaccf4', '94d34b55-b9ea-4059-81b2-6ee842bc354c')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('21aaecc6-5c57-4f99-b732-9caeb879d855', '6cd422b7-d7b6-4791-9bad-8794f4a7fc60')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('693a1a7b-568e-43d2-8fd5-629a45e89cec', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('693a1a7b-568e-43d2-8fd5-629a45e89cec', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('693a1a7b-568e-43d2-8fd5-629a45e89cec', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('ee9ae19f-aaff-4246-846a-a596f5ee6b41', '6cd422b7-d7b6-4791-9bad-8794f4a7fc60')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('ee9ae19f-aaff-4246-846a-a596f5ee6b41', 'f4917908-1824-448a-b67f-6b4426d7668b')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('38eb9e6a-4ad2-4e1f-aa56-17385dfe0795', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'f495c7a3-8048-4dd3-b366-0b0faa27907a')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('33ca1002-6bf9-400d-8db3-a80d550cb2f1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for ENGL295 - B', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'f495c7a3-8048-4dd3-b366-0b0faa27907a', 'f444524b-30c6-489b-95b9-05d4202810fa')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('a785957e-0160-481d-8699-95c407b97b3f', 0, 'f444524b-30c6-489b-95b9-05d4202810fa', '50a2fbfc-7601-4ec0-8940-ad49372d6463')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('4d7d7c21-0575-4bc3-afb8-1ecc663b3f2b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '7b5519f6-8bd6-4eb9-939f-a925304a583d')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('45678db5-d8f6-467e-909a-654826b0c31e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'f4917908-1824-448a-b67f-6b4426d7668b', 'CL 1', 'CL 1', 'f5cfdc23-9bfe-4fa6-8da4-dcb318c5b13c')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('12e6daf2-06a1-4cbf-8d2a-2f0132133a90', 'kuali.lui.type.activity.offering.lecture', 'f5cfdc23-9bfe-4fa6-8da4-dcb318c5b13c', '332108e7-d6f0-41f3-a272-efe57ba8b90a')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('f495c7a3-8048-4dd3-b366-0b0faa27907a', 'd30166f7-18b5-4b3c-8364-a1705feaccf4')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('50a2fbfc-7601-4ec0-8940-ad49372d6463', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('50a2fbfc-7601-4ec0-8940-ad49372d6463', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('50a2fbfc-7601-4ec0-8940-ad49372d6463', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('50a2fbfc-7601-4ec0-8940-ad49372d6463', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('7b5519f6-8bd6-4eb9-939f-a925304a583d', 'd30166f7-18b5-4b3c-8364-a1705feaccf4')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('7b5519f6-8bd6-4eb9-939f-a925304a583d', 'f4917908-1824-448a-b67f-6b4426d7668b')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('332108e7-d6f0-41f3-a272-efe57ba8b90a', '6cd422b7-d7b6-4791-9bad-8794f4a7fc60')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('332108e7-d6f0-41f3-a272-efe57ba8b90a', 'd30166f7-18b5-4b3c-8364-a1705feaccf4')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='f5cfdc23-9bfe-4fa6-8da4-dcb318c5b13c' where ID='332108e7-d6f0-41f3-a272-efe57ba8b90a'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('3b3c03a9-d600-4868-8dd9-12fe3c83c553', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '1af2eff3-2602-4e67-9e64-9f62ae7f932e', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'b60fe6c1-fe01-4b5f-a04d-a7543f580968')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ba1f0555-b733-47b2-b9ac-35c9235615e0', 'kuali.attribute.registration.group.is.generated', 'b60fe6c1-fe01-4b5f-a04d-a7543f580968', 1, '7bcc85fc-d22f-441d-9a5c-aaad67c0ba9d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('81a917db-9215-4045-aeb3-da5dd18452d1', 'kuali.attribute.registration.group.aocluster.id', 'b60fe6c1-fe01-4b5f-a04d-a7543f580968', 'f5cfdc23-9bfe-4fa6-8da4-dcb318c5b13c', 'd3950791-292a-498f-ae68-76a5bba05499')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('1ee394d2-bf60-49b5-a97b-76af2737fe95', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'b60fe6c1-fe01-4b5f-a04d-a7543f580968', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '9a516ad7-0131-4511-b0bd-18820b261ff0')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('36be4237-f042-4746-bb0a-d2843af12388', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:05.245', '', 'ENGL295-FO-RG', 'f4917908-1824-448a-b67f-6b4426d7668b', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL295-FO-RG', 'b60fe6c1-fe01-4b5f-a04d-a7543f580968', '8c438e9f-7647-4903-bd4a-0c6c683825fc')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e37ad523-1d38-4cc0-8828-c54d3d7f7dd8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:05.245', '', 'ENGL295-RG-AO', 'b60fe6c1-fe01-4b5f-a04d-a7543f580968', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL295-RG-AO', '6cd422b7-d7b6-4791-9bad-8794f4a7fc60', 'd9c40fee-c744-4029-a7b4-ed6fd5e3090d')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('5342f1e0-00d7-49e9-8386-f47144815fd2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '1af2eff3-2602-4e67-9e64-9f62ae7f932e', '', '', '1002', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1002', '1002', '', 'edf8dc8a-75bb-45d4-bc30-58f65bc73dea')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d67b8ccc-9e6c-4422-90e9-71b5b9eeafa6', 'kuali.attribute.registration.group.aocluster.id', 'edf8dc8a-75bb-45d4-bc30-58f65bc73dea', 'f5cfdc23-9bfe-4fa6-8da4-dcb318c5b13c', '11e124fc-5f1b-4fc5-b94c-ae793bd8df5f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f40fc215-1d8a-474a-8f9b-02036f1d3fb3', 'kuali.attribute.registration.group.is.generated', 'edf8dc8a-75bb-45d4-bc30-58f65bc73dea', 1, '2794043d-d78f-4673-ab52-3ee487c71c12')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('0e752d28-7e58-477c-ad10-9016bdb61b61', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'edf8dc8a-75bb-45d4-bc30-58f65bc73dea', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c5035c97-689f-4d49-b87d-bb644feeb44c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('3e8c32e4-b77c-485d-83ac-55565395e3ba', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:05.322', '', 'ENGL295-FO-RG', 'f4917908-1824-448a-b67f-6b4426d7668b', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL295-FO-RG', 'edf8dc8a-75bb-45d4-bc30-58f65bc73dea', '11b93193-fe75-4e44-89c4-e3fa0ce5dd76')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('2360c751-dedf-4878-b298-dc0eb7af82ba', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:05.322', '', 'ENGL295-RG-AO', 'edf8dc8a-75bb-45d4-bc30-58f65bc73dea', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL295-RG-AO', 'd30166f7-18b5-4b3c-8364-a1705feaccf4', '07958b71-b93f-46e7-b2cf-032e29f7f780')
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='aa22addb-51dc-4b4b-9ab6-19af629aa47d', ATTR_KEY='kuali.soc.rollover.result.dynattr.date.completed', OWNER_ID='8eb89914-1b01-4d50-af48-dab273b4554d', ATTR_VALUE='2014 Jan 22 12:50:05 America/New_York' where ID='401f9b67-014c-4a31-b85e-67ae4de802be'
/
update KSEN_SOC_ROR set OBJ_ID='086ca850-8a66-4720-b0d4-cf7f5955aab1', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ITEMS_EXPECTED=48, ITEMS_PROCESSED=10, MESG_FORMATTED='', MESG_PLAIN='', SOC_ROR_STATE='kuali.soc.rollover.state.running', SOC_ROR_TYPE='kuali.soc.rollover.results.type.rollover', SOURCE_SOC_ID='03192a24-765a-4b9d-b638-5158430e09c8', TARGET_SOC_ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', TARGET_TERM_ID='kuali.atp.2014Winter' where ID='8eb89914-1b01-4d50-af48-dab273b4554d' and VER_NBR=2
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('567a1ae3-ba18-4baf-9c2c-1cec1d1816e2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '08345864-2994-4101-aedd-a1ead352f249', '07eb8950-9ec6-406d-9574-962fdb747196', 'ff7d2610-f078-4b59-92ef-300dcf0d2eae')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cbdaee8e-70c6-4f8e-ab17-1ec00d4ec48c', 'activityOfferingsCreated', 'ff7d2610-f078-4b59-92ef-300dcf0d2eae', '6', 'b471423c-f9d6-4782-879b-1373f34572dc')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('df13dcf1-357f-4716-b957-385d383b1af5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', '0bc105c1-ce4b-4f15-9594-36a8adbaaf37', '', '9b2e519e-3bcf-4249-a63d-11e406920092')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('e7043128-e410-44a6-8a9e-b3548d1e794e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', '1046086a-9448-4fc8-b8ea-f0c39a0d44c8', '', '6d80141a-700f-49df-86a5-4c1418704d47')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('6ff0e46e-cba8-4988-8f44-6e901b609674', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '104cf59f-09d0-46e4-839a-10b3d03bd99a', 'e1091908-3ddf-4855-8926-b75eae92b522', '06a0cf56-95f0-4bc5-beef-7c78099af14d')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('77e39e56-1eae-4815-a786-479dff4999be', 'activityOfferingsCreated', '06a0cf56-95f0-4bc5-beef-7c78099af14d', '1', '78010a42-e90c-47f1-a8fe-041d9f25e622')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('941996ba-acd9-4aef-b61a-640bf4aa61df', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '0e638164-54b0-48f5-8e6d-b43fc908ccad', '160ab1c3-bb50-4825-ba40-ee6a9a49d432', 'd03fa76a-dd21-4cb8-8640-8d13d778e5da')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9652687c-3ee8-4617-93f4-9d627d4eb818', 'activityOfferingsCreated', 'd03fa76a-dd21-4cb8-8640-8d13d778e5da', '1', '40481c2b-0e6b-4d5d-84dc-9bdd844b9b54')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('ba630d2a-6f7a-4cb1-a54e-8ef57da6f5f9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '21c36421-1317-4c9a-bd25-1badb0fc7322', '6276fa96-379e-404f-bab0-d0c2b118ccd5', '4e4f6277-27c6-47ab-9660-3ae770f6a722')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f7880a90-9b63-44b1-8eba-5bf4818e7fd2', 'activityOfferingsCreated', '4e4f6277-27c6-47ab-9660-3ae770f6a722', '1', '769b3acd-dfdf-4c49-83ce-1dbde89db680')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('1fdfd6b2-6456-401d-bd2f-890c205781d9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '257c1c89-c045-4818-a6a0-8b7862bffc65', '126b3de4-c5bb-49af-9821-db04b9eb0b9f', 'db8ec14e-8ad7-4574-b8ba-fa302dc34c85')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('53d89ecf-dc78-48b4-946d-ffe9ce51f42a', 'activityOfferingsCreated', 'db8ec14e-8ad7-4574-b8ba-fa302dc34c85', '1', '912d1684-c0bc-46c0-a38a-4dfbdb0af6d8')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('46dbb69e-ec51-4947-89cd-0f71198b77b9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '2f94897d-75c7-49b3-a3fc-2484abc38dc7', 'ec2e84d3-2bc5-49a6-b55d-9c94d9c936ab', '81e6b4bf-65b2-4019-ac52-5175dc91c5d9')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d1fc514e-2672-4469-89b4-259f41fec9c1', 'activityOfferingsCreated', '81e6b4bf-65b2-4019-ac52-5175dc91c5d9', '1', '348fa63e-08f2-45c9-ba80-302c8673a7d6')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('8f06632b-4be8-4d59-a7e1-08d8ecc7f806', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '2d71a80b-edf3-42f2-bafd-bba2cf9bdcbc', 'ba9c247e-ab95-4a64-819b-2f3fdb2609b5', 'e9f00b78-8f4c-4d5e-aa93-aa47c20289fe')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('94fe291e-28fc-4129-9a99-2d2aa5fd2052', 'activityOfferingsCreated', 'e9f00b78-8f4c-4d5e-aa93-aa47c20289fe', '1', 'e49e73cd-f31d-480e-868b-18ec67b0ecdd')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('07ddd47f-2ae7-4fab-affc-729ed04bb870', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '2ff43080-cf67-4ff4-b4ae-2fd62b5a72f1', 'acebaa26-3d5d-43ac-b882-79c7614a335a', 'b082e7fb-0f1d-4925-a73a-246bd9a379d6')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('660638b7-25bb-4c9d-8a9e-70bd8da14713', 'activityOfferingsCreated', 'b082e7fb-0f1d-4925-a73a-246bd9a379d6', '2', '74da41c8-d508-4ad6-b8aa-acd2417bad0a')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('1d9b4e35-7dce-45bc-8ecc-be6e70d1fff1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '8149aa68-9bfa-4d95-bcfe-b17b2d36d763', TIMESTAMP '2014-01-01 19:00:00.0', '', 'An exploration of arguably the most complex, profound, and ubiquitous expression of human experience. Study through close reading of significant forms and conventions of Western poetic tradition. Poetry''s roots in oral and folk traditions and connections to popular song forms.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL243 CO', 'An exploration of arguably the most complex, profound, and ubiquitous expression of human experience. Study through close reading of significant forms and conventions of Western poetic tradition. Poetry''s roots in oral and folk traditions and connections to popular song forms.', null, '480aa452-84c2-41c6-83b0-35daf587531e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c33165ec-2a28-4db2-9860-10c7d246004b', 'kuali.attribute.course.evaluation.indicator', '480aa452-84c2-41c6-83b0-35daf587531e', 0, 'df1253d4-d88c-4174-8e4e-ee09fbaac055')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('37e70bf2-8f99-4b90-aa7b-26abe4b1597c', 'kuali.attribute.final.exam.driver', '480aa452-84c2-41c6-83b0-35daf587531e', 'kuali.lu.exam.driver.ActivityOffering', '4dfdf7c5-e84d-4177-a7ee-18175a6363e2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('92a595f8-3a32-4992-baa0-2169ee2e6f88', 'kuali.attribute.final.exam.use.matrix', '480aa452-84c2-41c6-83b0-35daf587531e', 1, '7a26b6be-fe71-44bf-9356-3fee5bffd567')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('265fb8ce-3480-4a27-919b-ea415b9093fc', 'kuali.attribute.course.number.internal.suffix', '480aa452-84c2-41c6-83b0-35daf587531e', 'A', '3450b179-dc32-4d81-b6c2-d11c6293a6c1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1458bddc-21f8-4473-9b06-894ab2f75362', 'kuali.attribute.final.exam.indicator', '480aa452-84c2-41c6-83b0-35daf587531e', 'STANDARD', '9decc148-3015-4259-9e6d-4323f69e8078')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2457fe07-ec53-4434-bcea-1c1b7d19f84f', 'kuali.attribute.wait.list.type.key', '480aa452-84c2-41c6-83b0-35daf587531e', null, 'bda0e3f0-a83d-4ed5-8dcd-c857a5c82935')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c78d70f7-287f-4077-b9c0-cf9d5469ec04', 'kuali.attribute.wait.list.level.type.key', '480aa452-84c2-41c6-83b0-35daf587531e', 'AO', 'c6de5f0b-def9-4e63-849a-f30e0443608d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fb78b9a9-adc6-419e-be18-3bb8238c901c', 'kuali.attribute.where.fees.attached.flag', '480aa452-84c2-41c6-83b0-35daf587531e', 0, 'ae5319e8-a343-494f-93a5-3d451793c25b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ae322181-828a-4457-ad9e-ed1eb6caee88', 'kuali.attribute.wait.list.indicator', '480aa452-84c2-41c6-83b0-35daf587531e', 0, '9c9b6dd7-5531-442c-952d-b66395587e66')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3aa8de51-10d7-4bff-a15f-0e1ee7f06059', 'kuali.attribute.funding.source', '480aa452-84c2-41c6-83b0-35daf587531e', '', '954e9c48-520b-4e0c-a9dc-c862df066769')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('890098a1-5538-4c80-9627-4b9b828e6d27', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'ENGL243', 'ENGL', '', 'What is Poetry?', '480aa452-84c2-41c6-83b0-35daf587531e', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '93f72a5d-dd65-404b-93d2-bb5afba471b4')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('10c35886-b657-4971-a49c-3c57f9ca672c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '480aa452-84c2-41c6-83b0-35daf587531e', '', 'kuali.lu.code.honorsOffering', '', '4f610900-88ae-4d05-9933-f43659052f4f')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('480aa452-84c2-41c6-83b0-35daf587531e', '2677933260', 'ec11e0f4-bfcc-4b10-ac05-ac0770e5f184')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('480aa452-84c2-41c6-83b0-35daf587531e', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('480aa452-84c2-41c6-83b0-35daf587531e', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('480aa452-84c2-41c6-83b0-35daf587531e', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('480aa452-84c2-41c6-83b0-35daf587531e', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('480aa452-84c2-41c6-83b0-35daf587531e', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('676c9d44-3819-4e6c-b82d-22d266791c29', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'b3d8efe2-cbe7-4e50-a72f-8f4b2b4ea8ad', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'b2e30f74-4bce-4027-90f0-c2721c791453')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('592ae385-e50b-4bc5-9215-8a236c5b87c6', 'kuali.attribute.final.exam.level.type', 'b2e30f74-4bce-4027-90f0-c2721c791453', 'kuali.lu.type.activity.Lecture', '64ede61a-04fa-4aaa-95ab-404a6dbf5e37')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7f6ba8e6-9759-4456-b282-6df22749b044', 'kuali.attribute.grade.roster.level.type.key', 'b2e30f74-4bce-4027-90f0-c2721c791453', 'kuali.lui.type.activity.offering.lecture', '642fc046-eb19-4e04-a5d1-dcc943c9e9a0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('94f659e1-65d4-4a88-bae5-5887aef443d7', 'regCodePrefix', 'b2e30f74-4bce-4027-90f0-c2721c791453', '1', 'e75e4ced-5be3-40b0-b8fd-de571cb28147')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('043c0906-0942-467d-8a0b-43b4e111c6ce', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', 'b2e30f74-4bce-4027-90f0-c2721c791453', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a54d0ac5-8a1d-4d49-bf04-6c2dd28b97de')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('69ab4f1d-ba10-4bc1-9969-60bddd8bbb4d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:09.065', '', 'ENGL243-CO-FO', '480aa452-84c2-41c6-83b0-35daf587531e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL243-CO-FO', 'b2e30f74-4bce-4027-90f0-c2721c791453', '1e11bbd6-7e41-4e88-9eeb-417eef236be9')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('b2e30f74-4bce-4027-90f0-c2721c791453', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('c1902933-59f8-4dda-92eb-ce8d01d90e04', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '69f87d52-5ab8-4c76-85d1-d753266cbeca', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 30, 30, 'Lecture', 'Courses with lecture', null, '1eb79e4b-d8e2-4886-95f3-e1aecf0a06d6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('40d645a9-d481-43a8-87d0-3ffd20e831b6', 'kuali.attribute.max.enrollment.is.estimate', '1eb79e4b-d8e2-4886-95f3-e1aecf0a06d6', 0, 'f0e25af9-d5cb-43b2-b687-15f3f251736d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a7931209-a84a-4a17-ab57-60637506d234', 'kuali.attribute.course.evaluation.indicator', '1eb79e4b-d8e2-4886-95f3-e1aecf0a06d6', 0, '655292f3-9e05-4f8b-bfa7-90eff0b02131')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ac85b974-6ca2-44c4-8c90-359d518479a8', 'kuali.attribute.nonstd.ts.indicator', '1eb79e4b-d8e2-4886-95f3-e1aecf0a06d6', 0, '7eefea9f-6c3e-41f1-a466-ec7fab0a560c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7eb910e5-ab9c-4733-a92a-b12cf465b082', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '1eb79e4b-d8e2-4886-95f3-e1aecf0a06d6', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ec131d4e-afdf-4c34-8e63-2cc735788161')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('1f9d3771-e271-4662-966e-3e8da29981d6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '1eb79e4b-d8e2-4886-95f3-e1aecf0a06d6', '', 'kuali.lu.code.honorsOffering', 0, 'eb598f55-708a-4c06-8b20-c59b1307f0c6')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('48cfd4ae-19f7-4c3e-b644-6d5b5eb6b14a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:09.195', '', '1eb79e4b-d8e2-4886-95f3-e1aecf0a06d6', 'C.TERRYE', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'a66855c9-b2b7-49f4-a1d0-03a3393276fa')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('3e8da31b-fc52-4e39-b840-4fa862c2e983', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:09.196', '', 'ENGL243-FO-AO', 'b2e30f74-4bce-4027-90f0-c2721c791453', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL243-FO-AO', '1eb79e4b-d8e2-4886-95f3-e1aecf0a06d6', '462b98d6-d2e1-49a4-b043-5810ce673edc')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('e844eedf-5e55-47b8-b77a-0b0985c08831', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '9af6e01c-fa01-4352-9bed-09f9fcb9ce73')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('a54499cb-60c1-4490-a432-fc9a80659aee', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A Schedule Request for ENGL243 section 0102', 'Schedule request for ENGL243 - A', 'A Schedule Request for ENGL243 section 0102', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', 'acecda74-05ea-49e1-8504-058e6e954d37', '9af6e01c-fa01-4352-9bed-09f9fcb9ce73', 'cf5e5f9d-b012-405c-a139-3ab308ebb735')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('d826e9f7-d4cf-41dd-b28d-644dd456fad8', 0, 'cf5e5f9d-b012-405c-a139-3ab308ebb735', '5ce9c661-c5b8-432e-9ace-948df93c795c')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('11c2be2b-de4c-496f-b365-6627ee48f8df', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '35cfda68-1832-43c3-93e4-a0c4dd2bd2f7')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('05012140-cf96-487b-a6af-7fc0a493545b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '668d892b-5335-490e-8883-b913c8c4818f', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 30, 30, 'Lecture', 'Courses with lecture', null, '74c56077-591e-437c-94d5-13a3abef7c17')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9feebb32-3cb6-4e61-8d27-71048048e090', 'kuali.attribute.course.evaluation.indicator', '74c56077-591e-437c-94d5-13a3abef7c17', 0, '13b1d558-0d58-48a5-9b52-a7bde689f465')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6b3dcd57-518b-459e-b312-f4568aa6dcba', 'kuali.attribute.max.enrollment.is.estimate', '74c56077-591e-437c-94d5-13a3abef7c17', 0, '2119e5ce-9986-44b0-b009-dedc3bd4c927')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8eccb31e-c13e-4baf-807d-571474466e56', 'kuali.attribute.nonstd.ts.indicator', '74c56077-591e-437c-94d5-13a3abef7c17', 0, '50164915-ce27-4953-a71e-aaa021f968ec')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('cad62b52-65ab-4ba4-b80b-9744d3315b8e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'B', '', '', '', '74c56077-591e-437c-94d5-13a3abef7c17', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'bb9059bb-395c-45c9-917b-96c0b07e9424')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('11a1e047-8da3-4023-a2fd-0524b16ba827', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '74c56077-591e-437c-94d5-13a3abef7c17', '', 'kuali.lu.code.honorsOffering', 0, '02174be2-d4aa-4a14-b8ce-b809b066e454')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('62432e34-6463-4982-8b3e-5c74249bdf9e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:09.445', '', '74c56077-591e-437c-94d5-13a3abef7c17', 'H.VICKIP', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '5fa962b2-c03f-4ef7-a14d-47f0048e0546')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('1a6fd13e-6329-4996-8086-04f9180ca917', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:09.445', '', 'ENGL243-FO-AO', 'b2e30f74-4bce-4027-90f0-c2721c791453', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL243-FO-AO', '74c56077-591e-437c-94d5-13a3abef7c17', '3d72c54c-e6ab-42c5-8333-16c5c8406460')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('9af6e01c-fa01-4352-9bed-09f9fcb9ce73', '1eb79e4b-d8e2-4886-95f3-e1aecf0a06d6')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('5ce9c661-c5b8-432e-9ace-948df93c795c', '84e53b2a-f39a-4b7a-8916-731087cff42d')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('5ce9c661-c5b8-432e-9ace-948df93c795c', 'e26b7710-1faa-4f4e-ad57-52caec27baa9')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('5ce9c661-c5b8-432e-9ace-948df93c795c', 'F08200F106A3315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('35cfda68-1832-43c3-93e4-a0c4dd2bd2f7', '1eb79e4b-d8e2-4886-95f3-e1aecf0a06d6')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('35cfda68-1832-43c3-93e4-a0c4dd2bd2f7', 'b2e30f74-4bce-4027-90f0-c2721c791453')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('2a68153c-67a9-4035-8ed7-8779a0c82d01', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '14741cf5-470e-4739-9803-1c9d1675b2fe')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('8b846ef6-48c6-4b49-9b27-8b22830b4642', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for ENGL243 - B', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '14741cf5-470e-4739-9803-1c9d1675b2fe', '4859f81b-e719-4d76-92d0-bade54829be5')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('40f84a76-c386-410f-970c-1c817365017c', 0, '4859f81b-e719-4d76-92d0-bade54829be5', '4ef1a766-e766-4d09-b1be-7288c8b66453')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('d0e13a8c-c5e1-437b-a3d9-df437b0d2c2a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '1201dc2c-2a50-43a8-851c-9dfad13414cc')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('ca7e77eb-7cab-4898-a38a-8ea093cd1631', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'b2e30f74-4bce-4027-90f0-c2721c791453', 'CL 1', 'CL 1', '4427d3d0-2a7b-4d34-b92b-9c243f897f1f')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('8601f41a-9063-4f19-97a3-b4603f3f1b67', 'kuali.lui.type.activity.offering.lecture', '4427d3d0-2a7b-4d34-b92b-9c243f897f1f', '452a1389-ed19-4d5d-a339-c42005c18711')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('14741cf5-470e-4739-9803-1c9d1675b2fe', '74c56077-591e-437c-94d5-13a3abef7c17')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('4ef1a766-e766-4d09-b1be-7288c8b66453', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('4ef1a766-e766-4d09-b1be-7288c8b66453', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('4ef1a766-e766-4d09-b1be-7288c8b66453', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('4ef1a766-e766-4d09-b1be-7288c8b66453', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('1201dc2c-2a50-43a8-851c-9dfad13414cc', '74c56077-591e-437c-94d5-13a3abef7c17')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('1201dc2c-2a50-43a8-851c-9dfad13414cc', 'b2e30f74-4bce-4027-90f0-c2721c791453')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('452a1389-ed19-4d5d-a339-c42005c18711', '74c56077-591e-437c-94d5-13a3abef7c17')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('452a1389-ed19-4d5d-a339-c42005c18711', '1eb79e4b-d8e2-4886-95f3-e1aecf0a06d6')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='4427d3d0-2a7b-4d34-b92b-9c243f897f1f' where ID='452a1389-ed19-4d5d-a339-c42005c18711'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('73c950de-cca0-4b5f-aa7a-eb8bfaf6c4ec', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'b3d8efe2-cbe7-4e50-a72f-8f4b2b4ea8ad', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'fe38cc38-60b3-42d9-9515-b22348a7fa3c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7f62499d-0650-46f4-ad7f-27d3d09872e4', 'kuali.attribute.registration.group.is.generated', 'fe38cc38-60b3-42d9-9515-b22348a7fa3c', 1, 'dd918c5f-b1e5-4aa1-9210-62766c1dcb33')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a6ac5f5c-ac29-49f8-8d12-4032ed0cc567', 'kuali.attribute.registration.group.aocluster.id', 'fe38cc38-60b3-42d9-9515-b22348a7fa3c', '4427d3d0-2a7b-4d34-b92b-9c243f897f1f', '22976da4-1a2f-48be-b1ce-003dbd6afb0e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('446f9571-b782-440a-b673-cf661070c78f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'fe38cc38-60b3-42d9-9515-b22348a7fa3c', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'efb6d3bb-326c-4e67-95b3-cf6a2d4cbb5b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('1418920a-c0bf-4811-b429-b7412279dbda', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:09.994', '', 'ENGL243-FO-RG', 'b2e30f74-4bce-4027-90f0-c2721c791453', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL243-FO-RG', 'fe38cc38-60b3-42d9-9515-b22348a7fa3c', 'b122df44-8340-48f2-a1b9-3b30da82c366')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a802d77e-0c45-4df4-8178-249a6c4bbc29', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:09.995', '', 'ENGL243-RG-AO', 'fe38cc38-60b3-42d9-9515-b22348a7fa3c', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL243-RG-AO', '1eb79e4b-d8e2-4886-95f3-e1aecf0a06d6', '891a7e22-991e-47ac-98e1-9342764f3f73')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('0afd5ea0-0c9d-425a-9b43-72ee85dc1f9d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'b3d8efe2-cbe7-4e50-a72f-8f4b2b4ea8ad', '', '', '1002', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1002', '1002', '', '930f44e8-8fbc-4b43-b851-468c993c6baa')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('921b0503-a642-42fd-b538-4999c2c4306c', 'kuali.attribute.registration.group.aocluster.id', '930f44e8-8fbc-4b43-b851-468c993c6baa', '4427d3d0-2a7b-4d34-b92b-9c243f897f1f', 'd762da68-4a26-4573-9d11-93ab40821a58')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1a42d4ac-dcbe-4106-aef2-5dd2a5e05ed6', 'kuali.attribute.registration.group.is.generated', '930f44e8-8fbc-4b43-b851-468c993c6baa', 1, 'f5c7fb15-8f10-418e-84be-2093643e578b')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('1970a0b2-a077-4a1c-9f36-d1144f796922', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', '930f44e8-8fbc-4b43-b851-468c993c6baa', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '0ce9b03d-f043-415c-bc5e-c38844820e02')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('11b52b1d-2de5-4fc2-b8ed-c0c0d5d70e3e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:10.076', '', 'ENGL243-FO-RG', 'b2e30f74-4bce-4027-90f0-c2721c791453', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL243-FO-RG', '930f44e8-8fbc-4b43-b851-468c993c6baa', 'b7675243-895e-41cd-9eba-9ff95ce2a340')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('7fb35730-d861-4319-b967-ff849bc1d671', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:10.077', '', 'ENGL243-RG-AO', '930f44e8-8fbc-4b43-b851-468c993c6baa', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL243-RG-AO', '74c56077-591e-437c-94d5-13a3abef7c17', '6ec39148-f095-4c9c-b0dc-2673465d48ab')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('b54c2bc6-536c-4dc8-9d71-af1c6c2d68f5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'CLUID-WMST469-201008000000', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Special topics course taken as part of an approved study abroad program', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'WMST469T CO', 'Special topics course taken as part of an approved study abroad program', null, '8ea0c9ca-35be-4d81-8020-f33e20f024f6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7e7cb9c5-02b3-4032-85f0-2dd37756c250', 'kuali.attribute.final.exam.indicator', '8ea0c9ca-35be-4d81-8020-f33e20f024f6', 'STANDARD', '5e26a165-0e0b-4ef3-839d-b440672d7a14')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dc22f800-d978-47bd-87d9-b02465d39aba', 'kuali.attribute.final.exam.use.matrix', '8ea0c9ca-35be-4d81-8020-f33e20f024f6', 1, 'c2357dcf-05c3-4833-b9aa-f31ce3de0dcc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('33e9b8fe-1d73-4210-b87d-4cce019e420d', 'kuali.attribute.final.exam.driver', '8ea0c9ca-35be-4d81-8020-f33e20f024f6', 'kuali.lu.exam.driver.ActivityOffering', 'a90e908d-dbbc-4f3f-9fbd-386fd4bc9596')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('09381dd8-5fbd-47ce-a0a9-d6b4f1f2c71d', 'kuali.attribute.funding.source', '8ea0c9ca-35be-4d81-8020-f33e20f024f6', '', '55663739-e88f-4c17-8034-46bc9d76a3c3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('483e0404-3eb6-4096-b9e4-bffed32d476b', 'kuali.attribute.wait.list.type.key', '8ea0c9ca-35be-4d81-8020-f33e20f024f6', null, 'b8cb35b4-9f16-4f53-b641-3df5eaff0144')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f05c493f-f555-4f43-9d82-a82c3b4539ab', 'kuali.attribute.where.fees.attached.flag', '8ea0c9ca-35be-4d81-8020-f33e20f024f6', 0, '9ded10f6-353c-4292-975d-addc36e9384e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1e1d7585-62ce-4d61-8105-da910cf56bb3', 'kuali.attribute.course.evaluation.indicator', '8ea0c9ca-35be-4d81-8020-f33e20f024f6', 0, '91eee683-2a03-4ae8-9b93-eab387f6d5ef')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('70d684d8-4682-4b45-a0da-29abfd1cf828', 'kuali.attribute.wait.list.level.type.key', '8ea0c9ca-35be-4d81-8020-f33e20f024f6', 'AO', '15719dfc-0823-4a1f-af82-0b12382487d2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('18b10f17-e4fd-4628-b475-5e12b0b1c629', 'kuali.attribute.course.number.internal.suffix', '8ea0c9ca-35be-4d81-8020-f33e20f024f6', 'A', 'ccfcf3c2-a0e4-46df-be13-f845ec482ae8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d094ea1e-a9d9-4616-9bed-9b4588236115', 'kuali.attribute.wait.list.indicator', '8ea0c9ca-35be-4d81-8020-f33e20f024f6', 1, 'e9b99fa8-097a-44fd-a4df-4d56d7f7d04a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6003c673-be63-420e-b654-03b72fd07d78', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'WMST469T', 'WMST', '', 'Study Abroad Special Topics IV', '8ea0c9ca-35be-4d81-8020-f33e20f024f6', '', '', 'kuali.lui.identifier.state.active', 'T', 'kuali.lui.identifier.type.official', '', '466f8aa5-4a5d-4166-8938-98015e989acd')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('0de39281-62bf-4de8-a8d3-5762df54b54a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '8ea0c9ca-35be-4d81-8020-f33e20f024f6', '', 'kuali.lu.code.honorsOffering', '', '34e04e25-1d6a-4401-99ba-c85241fc283b')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('8ea0c9ca-35be-4d81-8020-f33e20f024f6', '4014660630', 'a0968e8e-e6c3-42e7-966f-52c5672a7f81')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('8ea0c9ca-35be-4d81-8020-f33e20f024f6', '4014660630')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('8ea0c9ca-35be-4d81-8020-f33e20f024f6', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('8ea0c9ca-35be-4d81-8020-f33e20f024f6', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('8ea0c9ca-35be-4d81-8020-f33e20f024f6', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('8ea0c9ca-35be-4d81-8020-f33e20f024f6', 'kuali.creditType.credit.degree.1-6.by.0.5')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('e8d2a3e1-b3bc-4492-bfa0-f6b787f64216', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '4f5e5387-81cb-46b6-8b3f-fef5aeb90638', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '20c4d9df-31ee-41ca-9b9b-510c594af79f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('89f2e149-ea42-493f-839b-6ce81f526961', 'regCodePrefix', '20c4d9df-31ee-41ca-9b9b-510c594af79f', '1', '970f16ea-bdb2-43ac-bd3d-6fa46a758803')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f3d319af-985f-4f4c-8b41-c1287b8c4592', 'kuali.attribute.grade.roster.level.type.key', '20c4d9df-31ee-41ca-9b9b-510c594af79f', 'kuali.lui.type.activity.offering.lecture', '873603fc-6f41-4dfb-9a24-adb9414b02f5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a0030491-4401-467d-8760-30b76148c3e2', 'kuali.attribute.final.exam.level.type', '20c4d9df-31ee-41ca-9b9b-510c594af79f', 'kuali.lu.type.activity.Lecture', '062ed631-dba7-4a67-861d-40c5b448012e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('69f1c71d-8726-4bd7-b750-16c1f26b1626', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '20c4d9df-31ee-41ca-9b9b-510c594af79f', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '1825c697-4e0d-4bc6-bed7-869277618569')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('5aecfea0-fe3f-46cf-9ef6-8cff8666e8df', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:11.819', '', 'WMST469T-CO-FO', '8ea0c9ca-35be-4d81-8020-f33e20f024f6', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'WMST469T-CO-FO', '20c4d9df-31ee-41ca-9b9b-510c594af79f', 'a87f865d-a0aa-4990-a77c-e27e34a89613')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('20c4d9df-31ee-41ca-9b9b-510c594af79f', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('acab1afd-50b9-405a-9ece-f6e28da7d2ff', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '0b5c76ff-9f50-41d6-a505-bc39ccba5f8c', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 1, '', 'Lecture', 'Courses with lecture', null, '0367160d-8d98-4c54-962a-367cfe559972')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3046a3d5-bdfa-4612-8d97-7efec0d824c9', 'kuali.attribute.max.enrollment.is.estimate', '0367160d-8d98-4c54-962a-367cfe559972', 0, '9532d458-9c95-4a35-b3bb-e40e341cf790')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b941a284-ab1c-4677-9fbc-fe6171fefcff', 'kuali.attribute.nonstd.ts.indicator', '0367160d-8d98-4c54-962a-367cfe559972', 0, 'dc95e272-8a06-4458-9a79-2f55172098a1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('899c0086-b5f8-4e78-afe8-0ccdf4efdba2', 'kuali.attribute.course.evaluation.indicator', '0367160d-8d98-4c54-962a-367cfe559972', 0, 'e44566be-26e4-4226-aa8b-0c8cbc109e6f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5fa3062b-7ad8-49b8-bd47-0894da84418f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '0367160d-8d98-4c54-962a-367cfe559972', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7e1d4eb7-9816-4ddc-a5c6-bbd8d890e2bc')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('977261b4-8e4b-4edf-b55b-ffd7303bf71f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '0367160d-8d98-4c54-962a-367cfe559972', '', 'kuali.lu.code.honorsOffering', 0, 'de852848-f0f7-419e-b56d-4015ffc76091')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('96829301-e7b6-4955-b5e1-a5c08a5bccd2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:11.933', '', '0367160d-8d98-4c54-962a-367cfe559972', 'D.ALEXD', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'a0d32b85-5b5b-46c4-b807-7a657bfa098d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('960146d4-4966-47e6-8627-028eaa8c4829', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:11.933', '', 'WMST469T-FO-AO', '20c4d9df-31ee-41ca-9b9b-510c594af79f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'WMST469T-FO-AO', '0367160d-8d98-4c54-962a-367cfe559972', '3f649d95-e720-42d7-8241-9886303c8d1c')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('a769d8df-2fd7-4249-8659-2c5776be7013', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '8e2e5526-4439-4b7d-8f0d-913f88343e56')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('d292e795-82de-4555-bb03-8d3dc07763a2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for WMST469T - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '8e2e5526-4439-4b7d-8f0d-913f88343e56', 'b23f3a85-4aba-42cc-965b-fabada4798c0')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('75025a86-5e97-42bd-b6c9-9dbb8ff28cf7', 1, 'b23f3a85-4aba-42cc-965b-fabada4798c0', 'b00c23de-f7ca-4bae-8d10-6911c5914b0f')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('60148a82-ed38-43b6-8c3c-39e7e5e36a64', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Athlete Seatpool', '1c2e7786-a2ab-4778-8e81-ea540c60b9b2', 1, 2, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '37118506-c901-4d90-8d41-76348313578a')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('8e2e5526-4439-4b7d-8f0d-913f88343e56', '0367160d-8d98-4c54-962a-367cfe559972')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('b00c23de-f7ca-4bae-8d10-6911c5914b0f', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('4bce76f1-3514-4021-8664-851447999d06', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Junior Seatpool', '4bae9f12-5901-4db9-b4a8-4dcc20781db5', 2, 4, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '11432c0e-a233-418b-b4a6-d2efea7ce4b2')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='60148a82-ed38-43b6-8c3c-39e7e5e36a64', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='0367160d-8d98-4c54-962a-367cfe559972', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Athlete Seatpool', POPULATION_ID='1c2e7786-a2ab-4778-8e81-ea540c60b9b2', PROCESSING_PRIORITY=1, SEAT_LIMIT=2, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='37118506-c901-4d90-8d41-76348313578a' and VER_NBR=1
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('6586c0c0-c99b-4a02-890d-db3ad05721a9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '1f25e821-7966-40f9-a3e3-8d54bb72ec95')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('a13eb0f4-9057-4249-8da9-ee1826b19136', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '20c4d9df-31ee-41ca-9b9b-510c594af79f', 'CL 1', 'CL 1', '92891ba5-3af9-4ac9-8cce-75ca9ec211e5')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('ed926651-66d2-48b0-ade4-f895da31623c', 'kuali.lui.type.activity.offering.lecture', '92891ba5-3af9-4ac9-8cce-75ca9ec211e5', 'f257bf8b-50d9-49cd-b3a4-f0f1bc4936bf')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='4bce76f1-3514-4021-8664-851447999d06', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='0367160d-8d98-4c54-962a-367cfe559972', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Junior Seatpool', POPULATION_ID='4bae9f12-5901-4db9-b4a8-4dcc20781db5', PROCESSING_PRIORITY=2, SEAT_LIMIT=4, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='11432c0e-a233-418b-b4a6-d2efea7ce4b2' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('1f25e821-7966-40f9-a3e3-8d54bb72ec95', '0367160d-8d98-4c54-962a-367cfe559972')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('1f25e821-7966-40f9-a3e3-8d54bb72ec95', '20c4d9df-31ee-41ca-9b9b-510c594af79f')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('f257bf8b-50d9-49cd-b3a4-f0f1bc4936bf', '0367160d-8d98-4c54-962a-367cfe559972')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='92891ba5-3af9-4ac9-8cce-75ca9ec211e5' where ID='f257bf8b-50d9-49cd-b3a4-f0f1bc4936bf'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('049da654-add6-4995-9489-3c36c6a1ea89', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '4f5e5387-81cb-46b6-8b3f-fef5aeb90638', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'dc8c61f3-09fd-48d9-9e1b-5c4bb0535a21')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('15f6bd39-4f2a-407c-b4b3-f1b5de10c94f', 'kuali.attribute.registration.group.is.generated', 'dc8c61f3-09fd-48d9-9e1b-5c4bb0535a21', 1, '1730a147-aa87-406e-9337-e9863692cfc2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2b4d5d22-2c47-4bf0-9d86-1152cf5e0625', 'kuali.attribute.registration.group.aocluster.id', 'dc8c61f3-09fd-48d9-9e1b-5c4bb0535a21', '92891ba5-3af9-4ac9-8cce-75ca9ec211e5', '159d92a1-6696-40f1-bcc0-1a7d2ef77aa5')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('721dfc4f-62ef-4c34-8ab6-8cc5072ca30d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'dc8c61f3-09fd-48d9-9e1b-5c4bb0535a21', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a79a636c-07af-4e8c-a540-b0fa03916642')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('75c0348e-7e84-46f7-a9d2-6f6ddaac35fc', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:12.397', '', 'WMST469T-FO-RG', '20c4d9df-31ee-41ca-9b9b-510c594af79f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'WMST469T-FO-RG', 'dc8c61f3-09fd-48d9-9e1b-5c4bb0535a21', 'ffe2b845-6203-446f-8e63-fee467125de9')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('9bae0396-93d2-421f-b3ca-3e881f48dc0d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:12.398', '', 'WMST469T-RG-AO', 'dc8c61f3-09fd-48d9-9e1b-5c4bb0535a21', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'WMST469T-RG-AO', '0367160d-8d98-4c54-962a-367cfe559972', 'e26774e2-f849-47da-8bf0-906ab7d3bcab')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('9b924dea-d2eb-497d-b20a-f96077c66fef', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'fbaef52c-442b-4761-85e7-5ab3b238c627', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Introduction to film as art form and how films create meaning. Basic film terminology; fundamental principles of film form, film narrative, and film history. Examination of film technique and style over past one hundred years. Social and economic functions of film within broader institutional, economic, and cultural contexts.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL245 CO', 'Introduction to film as art form and how films create meaning. Basic film terminology; fundamental principles of film form, film narrative, and film history. Examination of film technique and style over past one hundred years. Social and economic functions of film within broader institutional, economic, and cultural contexts.', null, '89c447f2-65f8-4986-9239-0fd083402241')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('19a43ddf-4493-4405-9fbe-30b16599f3bb', 'kuali.attribute.wait.list.type.key', '89c447f2-65f8-4986-9239-0fd083402241', null, '069f5a0b-fd42-4e75-bbc3-c1485cc6fbd2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b74c41a0-98cf-4751-a518-51709a529b9e', 'kuali.attribute.wait.list.indicator', '89c447f2-65f8-4986-9239-0fd083402241', 0, 'c2bcb8dc-564b-4527-a36c-67dcf1de790d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4d8257df-f9cb-42b6-8f1b-b84f3955ddf2', 'kuali.attribute.funding.source', '89c447f2-65f8-4986-9239-0fd083402241', '', '7a8e0c4d-436d-4fc9-8563-70beb7a24b22')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c3631b04-759a-4b28-92c5-dd909f74c184', 'kuali.attribute.course.number.internal.suffix', '89c447f2-65f8-4986-9239-0fd083402241', 'A', 'e568a2ed-92d9-4267-9d4a-e909dbe87c37')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bd33a038-d5a8-4469-b55a-d9eb68437e51', 'kuali.attribute.final.exam.driver', '89c447f2-65f8-4986-9239-0fd083402241', 'kuali.lu.exam.driver.ActivityOffering', 'dd3453be-fe86-4f08-9623-c80093ea6b1e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b63696c5-f15b-45ff-b9bb-c9aa9627e495', 'kuali.attribute.wait.list.level.type.key', '89c447f2-65f8-4986-9239-0fd083402241', 'AO', '4a8a552a-3a44-4282-aa18-014e1fc18072')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1fe8adea-8d6a-44b7-887f-5a923aad62f9', 'kuali.attribute.final.exam.indicator', '89c447f2-65f8-4986-9239-0fd083402241', 'STANDARD', 'c51980dd-5d31-4f96-91d7-d8e96cb5017d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4fa24411-380c-440b-b524-8fe5bd5b87e5', 'kuali.attribute.final.exam.use.matrix', '89c447f2-65f8-4986-9239-0fd083402241', 1, 'a18cf9c9-39e0-4d71-9e1d-d0153033fc0b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fec8b1ca-043e-4368-a1a5-1299c7caced6', 'kuali.attribute.where.fees.attached.flag', '89c447f2-65f8-4986-9239-0fd083402241', 0, '28d49a82-cda3-4fc3-a64c-aa151de154ec')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('aed9e3e9-9ebc-4290-9d30-31a0ef9a6917', 'kuali.attribute.course.evaluation.indicator', '89c447f2-65f8-4986-9239-0fd083402241', 0, '1239b4f2-fa66-45f9-9ac7-c0aff3b9e435')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('ff611fcc-80a6-4679-bb61-5b6f57b747a5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'ENGL245', 'ENGL', '', 'Film Form and Culture', '89c447f2-65f8-4986-9239-0fd083402241', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '5692c0ab-241e-4489-962d-22db9b7bb142')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('f8ede5d3-34b5-473a-9584-3d190d8140fb', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '89c447f2-65f8-4986-9239-0fd083402241', '', 'kuali.lu.code.honorsOffering', '', '7824f8de-ddd2-479f-b79a-d656c23ac3de')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('89c447f2-65f8-4986-9239-0fd083402241', '2677933260', '309ed94b-b09d-4c87-a05c-b88d75a3f744')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('89c447f2-65f8-4986-9239-0fd083402241', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('89c447f2-65f8-4986-9239-0fd083402241', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('89c447f2-65f8-4986-9239-0fd083402241', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('89c447f2-65f8-4986-9239-0fd083402241', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('89c447f2-65f8-4986-9239-0fd083402241', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('49ff5b2a-055c-4fff-b53e-2f3cb2caeb82', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '2fb81cbf-36e1-4096-ae43-541d964af238', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '049361a3-ef8e-4979-a6f8-23ef0555a289')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d52a61df-fffe-400d-a765-98d0cd56ded3', 'kuali.attribute.grade.roster.level.type.key', '049361a3-ef8e-4979-a6f8-23ef0555a289', 'kuali.lui.type.activity.offering.lecture', '901bfb0d-05e4-4d36-b61d-172ad683145e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8ed4f111-7792-48f0-8874-438bf1c59ee8', 'regCodePrefix', '049361a3-ef8e-4979-a6f8-23ef0555a289', '1', '4b6266ba-53a7-4af8-a26e-1061ab0ded8b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d29d4ca5-4900-4e70-a6f7-30107337a08b', 'kuali.attribute.final.exam.level.type', '049361a3-ef8e-4979-a6f8-23ef0555a289', 'kuali.lu.type.activity.Lecture', 'c3d00a36-c0c6-401c-a910-09ba0c66551e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5bfd6341-2da7-437a-9e3b-486601614a11', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '049361a3-ef8e-4979-a6f8-23ef0555a289', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '1262e4e6-092b-4a22-b978-ef5f43a3accb')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('244090a0-e8c0-4580-91c5-0249e8134d2a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:15.371', '', 'ENGL245-CO-FO', '89c447f2-65f8-4986-9239-0fd083402241', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL245-CO-FO', '049361a3-ef8e-4979-a6f8-23ef0555a289', '8c6873ee-7251-4e77-865d-6a97d79f423d')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('049361a3-ef8e-4979-a6f8-23ef0555a289', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('c6bfdd7f-f173-46cb-9499-60378a5344cc', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '3853b95e-b7b9-49f7-b981-10a1202d9c49', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, '01c54f2c-72fc-4bef-84f7-a251c6fb7f16')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1edcdc39-24d5-4430-a3d2-9ee8ce127b1e', 'kuali.attribute.nonstd.ts.indicator', '01c54f2c-72fc-4bef-84f7-a251c6fb7f16', 0, '4143eb75-553d-4868-91aa-58b852f3e785')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b0eddfab-ddd9-43ef-a0f4-87ef97d10370', 'kuali.attribute.max.enrollment.is.estimate', '01c54f2c-72fc-4bef-84f7-a251c6fb7f16', 0, '0c52472c-f400-4c1d-ab94-f14505752bf8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('db939df7-a34b-4a2c-98e3-67f26ce4e2b3', 'kuali.attribute.course.evaluation.indicator', '01c54f2c-72fc-4bef-84f7-a251c6fb7f16', 0, '824608d5-ea5f-4926-9375-63f0355b4776')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('efa92a1f-9ccc-4443-a5ba-c73667f265e1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '01c54f2c-72fc-4bef-84f7-a251c6fb7f16', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '2d7cd2b6-71d1-4d47-af9c-32eb5d9a9768')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('fed3026b-da09-4e3f-9d4a-6857f82fe042', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '01c54f2c-72fc-4bef-84f7-a251c6fb7f16', '', 'kuali.lu.code.honorsOffering', 0, '14b4acdb-4c61-45aa-98ca-580f1440b6d8')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('df013249-100c-4157-9c68-8cc4d610d39a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:15.46', '', '01c54f2c-72fc-4bef-84f7-a251c6fb7f16', 'H.KEITHN', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '0ca8b29c-f706-4f34-b4a7-7b0e22eefdd1')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('1964273c-c16c-4caf-a62c-49dc86d971f2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:15.46', '', 'ENGL245-FO-AO', '049361a3-ef8e-4979-a6f8-23ef0555a289', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL245-FO-AO', '01c54f2c-72fc-4bef-84f7-a251c6fb7f16', '9a5e7742-e01c-4e52-b939-3e91dc2c8891')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('f0542839-422e-43a8-a535-110e3f2c208b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'bcab3fb0-2dd1-4cd5-ba9e-d900fbcbca37')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('9d17f8b6-943c-41ca-911b-c61e0791f59a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for ENGL245 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'bcab3fb0-2dd1-4cd5-ba9e-d900fbcbca37', '638455b8-f9f5-464d-ad64-7bdf5a8b2034')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('8fcc0856-4260-420c-b7c3-f72ceeca0af3', 0, '638455b8-f9f5-464d-ad64-7bdf5a8b2034', '3509d7b6-883e-4c09-8dba-6bc58aa8e699')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('e0a36b7f-d304-463c-9901-dc05f063c5ab', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '5edbf942-20d1-48df-8dc0-e9cd848df2db')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('09247ad8-8416-4424-9f9a-5cf18d535679', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '46190799-c191-458f-a242-7084e92ec5da', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 20, 20, 'Lecture', 'Courses with lecture', null, '74bd7cae-fefe-43c8-aafa-5cda56ba0df1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('da73535e-90ec-4329-9bf7-488dd7eeaaef', 'kuali.attribute.course.evaluation.indicator', '74bd7cae-fefe-43c8-aafa-5cda56ba0df1', 0, '7c76abec-3819-4834-98a5-c252a7845c0c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c70238a1-2073-4567-9479-9cc8e7125dbf', 'kuali.attribute.nonstd.ts.indicator', '74bd7cae-fefe-43c8-aafa-5cda56ba0df1', 0, '3aa19057-b8bb-4495-8f22-0affc625bf53')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c152f0e7-4b8f-4e23-989d-911f50beb542', 'kuali.attribute.max.enrollment.is.estimate', '74bd7cae-fefe-43c8-aafa-5cda56ba0df1', 0, '471e13a9-0e90-4bc3-8fb2-5c01f13dc9de')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a564a874-1fa3-43fc-b612-87d7375bb9dc', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'B', '', '', '', '74bd7cae-fefe-43c8-aafa-5cda56ba0df1', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '05a6312f-6a0f-45da-866f-b1ddca57f5c4')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('3bc1124f-c78c-40b3-93a2-1713b455e7fe', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '74bd7cae-fefe-43c8-aafa-5cda56ba0df1', '', 'kuali.lu.code.honorsOffering', 0, 'e609a2ef-0b63-4d00-9b5c-59c5ee45ebf2')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('4fd1d22d-ebb8-4def-981a-5704770587c7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:15.943', '', '74bd7cae-fefe-43c8-aafa-5cda56ba0df1', 'F.SYDNEYL', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'b116687c-c3f5-4201-b078-595f56b33c19')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e41d3e65-96be-4c1f-b33b-8ecbb989e72c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:15.943', '', 'ENGL245-FO-AO', '049361a3-ef8e-4979-a6f8-23ef0555a289', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL245-FO-AO', '74bd7cae-fefe-43c8-aafa-5cda56ba0df1', '80d8e728-96f7-4d52-b49f-a3972ffc5259')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('bcab3fb0-2dd1-4cd5-ba9e-d900fbcbca37', '01c54f2c-72fc-4bef-84f7-a251c6fb7f16')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('3509d7b6-883e-4c09-8dba-6bc58aa8e699', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('3509d7b6-883e-4c09-8dba-6bc58aa8e699', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('3509d7b6-883e-4c09-8dba-6bc58aa8e699', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('3509d7b6-883e-4c09-8dba-6bc58aa8e699', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('5edbf942-20d1-48df-8dc0-e9cd848df2db', '01c54f2c-72fc-4bef-84f7-a251c6fb7f16')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('5edbf942-20d1-48df-8dc0-e9cd848df2db', '049361a3-ef8e-4979-a6f8-23ef0555a289')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('9786fdd9-18cb-416e-ab3f-d4381b9a9e2b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '2c47517a-1a15-42be-8f4e-fead16c381a0')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('008bc180-6ef9-439c-9389-5cb9b528ae42', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for ENGL245 - B', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '2c47517a-1a15-42be-8f4e-fead16c381a0', '3495f872-9569-4835-92bb-9563506001cb')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('5c0edfbb-8710-4a46-8aab-1e7efbf7adff', 0, '3495f872-9569-4835-92bb-9563506001cb', 'ee1c240e-ec5a-44fb-bb70-4f8a29d80b7c')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('17616bc6-3b3a-422b-a966-c5792fdc039f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.inactive', 'kuali.course.waitlist.type.course.waitlist', '8b0e2a48-4652-4474-83c3-e9803ff933a5')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('0843b4e5-f90a-432d-b626-84fa89f04af9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '049361a3-ef8e-4979-a6f8-23ef0555a289', 'CL 1', 'CL 1', '7058329d-bd49-4f7d-b4cb-db1fc41ac9b2')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('f5e27ca6-fafd-4a67-9116-05415ed98029', 'kuali.lui.type.activity.offering.lecture', '7058329d-bd49-4f7d-b4cb-db1fc41ac9b2', '2b4ca4ea-c4df-4062-b834-263db63525ef')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('2c47517a-1a15-42be-8f4e-fead16c381a0', '74bd7cae-fefe-43c8-aafa-5cda56ba0df1')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('ee1c240e-ec5a-44fb-bb70-4f8a29d80b7c', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('ee1c240e-ec5a-44fb-bb70-4f8a29d80b7c', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('ee1c240e-ec5a-44fb-bb70-4f8a29d80b7c', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('ee1c240e-ec5a-44fb-bb70-4f8a29d80b7c', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('8b0e2a48-4652-4474-83c3-e9803ff933a5', '74bd7cae-fefe-43c8-aafa-5cda56ba0df1')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('8b0e2a48-4652-4474-83c3-e9803ff933a5', '049361a3-ef8e-4979-a6f8-23ef0555a289')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('2b4ca4ea-c4df-4062-b834-263db63525ef', '74bd7cae-fefe-43c8-aafa-5cda56ba0df1')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('2b4ca4ea-c4df-4062-b834-263db63525ef', '01c54f2c-72fc-4bef-84f7-a251c6fb7f16')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='7058329d-bd49-4f7d-b4cb-db1fc41ac9b2' where ID='2b4ca4ea-c4df-4062-b834-263db63525ef'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('58c949c9-3fa4-46c7-9b47-6bfd249555ec', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '2fb81cbf-36e1-4096-ae43-541d964af238', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '9c2a30a3-9941-4df4-b605-98bd32d07a0e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f2e9b17b-af4b-4140-9489-df3e1c9be7eb', 'kuali.attribute.registration.group.aocluster.id', '9c2a30a3-9941-4df4-b605-98bd32d07a0e', '7058329d-bd49-4f7d-b4cb-db1fc41ac9b2', '921127be-6a10-415d-9a30-360d4c0f587a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('30841166-0644-4db5-a75d-e43c01454cde', 'kuali.attribute.registration.group.is.generated', '9c2a30a3-9941-4df4-b605-98bd32d07a0e', 1, 'dfe34242-6901-47c5-970c-28d81ee725a3')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('98ba3c6d-be79-42ce-9cc7-8f20bb366f43', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', '9c2a30a3-9941-4df4-b605-98bd32d07a0e', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '80a8c7bc-9083-4ea4-8fc8-ec2ec5269e06')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e2a913df-06c7-4651-98d5-f742126fe5b4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:16.547', '', 'ENGL245-FO-RG', '049361a3-ef8e-4979-a6f8-23ef0555a289', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL245-FO-RG', '9c2a30a3-9941-4df4-b605-98bd32d07a0e', 'eaa31536-e412-4e46-beb3-66880a0de936')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('d3d2bbbd-f396-4996-a826-c45d1aea483b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:16.548', '', 'ENGL245-RG-AO', '9c2a30a3-9941-4df4-b605-98bd32d07a0e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL245-RG-AO', '01c54f2c-72fc-4bef-84f7-a251c6fb7f16', '16841c2a-9a33-4430-b531-709f474c50a4')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('bc7357cf-6919-4b49-969e-98878de53abb', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '2fb81cbf-36e1-4096-ae43-541d964af238', '', '', '1002', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1002', '1002', '', 'd6aa22e1-0ead-410d-a3e2-b843a461536b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('05c3bcd5-f51f-4c31-ad09-0b8315693eb6', 'kuali.attribute.registration.group.aocluster.id', 'd6aa22e1-0ead-410d-a3e2-b843a461536b', '7058329d-bd49-4f7d-b4cb-db1fc41ac9b2', '04472ddb-8476-4a24-a349-9252b9ffb62c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ef508bb9-9786-4951-82d9-34b07cda4c12', 'kuali.attribute.registration.group.is.generated', 'd6aa22e1-0ead-410d-a3e2-b843a461536b', 1, '36ccde13-3dcb-4360-8a3b-9d4561383588')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('b24adb34-b6f5-40f8-8366-8e4059ed130a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'd6aa22e1-0ead-410d-a3e2-b843a461536b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7a9d224f-94c5-4c1a-913b-552fb8c2d173')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('58fd95d7-c15e-4638-a58d-35b0a6646fff', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:16.651', '', 'ENGL245-FO-RG', '049361a3-ef8e-4979-a6f8-23ef0555a289', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL245-FO-RG', 'd6aa22e1-0ead-410d-a3e2-b843a461536b', 'f0598076-ea7b-439e-aefe-996d0c9affe4')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('044832a7-151d-462e-8b0f-532baa8ac4a4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:16.652', '', 'ENGL245-RG-AO', 'd6aa22e1-0ead-410d-a3e2-b843a461536b', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL245-RG-AO', '74bd7cae-fefe-43c8-aafa-5cda56ba0df1', '7d993fe2-9770-4f33-b806-593c40dcd051')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d4983ffe-66ce-4db6-b5dc-5e778ece52bc', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'CLUID-HIST619-198101000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST619A CO', '.', null, '5730e112-8cb6-4125-99f9-763d70bf9872')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4aafe7e5-98ff-4251-90f5-8ed84104b940', 'kuali.attribute.wait.list.level.type.key', '5730e112-8cb6-4125-99f9-763d70bf9872', 'AO', 'c19deb16-8cc4-4d4e-bd31-694fc51d126d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fc0051a4-1a0a-443c-9e5c-035a58d6eb67', 'kuali.attribute.final.exam.indicator', '5730e112-8cb6-4125-99f9-763d70bf9872', 'STANDARD', '91c19027-b75d-423a-803a-b7614d0faaaf')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8fe11b47-39cd-4e12-acc9-8a2592d590f3', 'kuali.attribute.wait.list.type.key', '5730e112-8cb6-4125-99f9-763d70bf9872', null, '5d58630c-6337-4b7c-9bf1-835996bf986c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d5003b4d-e3f4-4816-8b7f-8f863632baf0', 'kuali.attribute.funding.source', '5730e112-8cb6-4125-99f9-763d70bf9872', '', 'd718da0f-6421-486c-afce-61b137d4015f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dbf56af8-c52b-4b20-b862-0aa0ae532691', 'kuali.attribute.course.evaluation.indicator', '5730e112-8cb6-4125-99f9-763d70bf9872', 0, 'd0c33c4d-0b6d-4a21-afee-8bd381d01192')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6565a174-cad6-446b-91b6-9828473581c1', 'kuali.attribute.course.number.internal.suffix', '5730e112-8cb6-4125-99f9-763d70bf9872', 'A', '29830511-3814-4a15-9c68-56b760cfda59')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ac5b4b1e-ab2f-4873-b196-e1c5f3c44f69', 'kuali.attribute.wait.list.indicator', '5730e112-8cb6-4125-99f9-763d70bf9872', 1, '5474db6f-8f07-4d36-884c-8377f2f0b754')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e0b611ea-a1bb-4968-b1a6-2213687c69dc', 'kuali.attribute.final.exam.use.matrix', '5730e112-8cb6-4125-99f9-763d70bf9872', 1, 'eb7c0aff-d737-47ff-a12e-f71ec26d35af')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2e3d03d0-2cc3-45a4-97f9-edd718ebc0fb', 'kuali.attribute.where.fees.attached.flag', '5730e112-8cb6-4125-99f9-763d70bf9872', 0, 'f1b3c72d-7298-4432-9041-cba831337842')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('671a753d-ad18-40ed-9d2a-fe92ae4b19ad', 'kuali.attribute.final.exam.driver', '5730e112-8cb6-4125-99f9-763d70bf9872', 'kuali.lu.exam.driver.ActivityOffering', '4afe9f1f-47d7-44b2-aa38-735e6d4e43f0')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('657c95c0-0c49-4b9d-91ef-ebb3fa5d0063', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'HIST619A', 'HIST', '', 'Special Topics in History', '5730e112-8cb6-4125-99f9-763d70bf9872', '', '', 'kuali.lui.identifier.state.active', 'A', 'kuali.lui.identifier.type.official', '', 'db64676d-5f0d-47ec-913e-7a8addb4039a')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('85f8fa87-1538-4caf-bc3c-1889f0ac2df9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '5730e112-8cb6-4125-99f9-763d70bf9872', '', 'kuali.lu.code.honorsOffering', '', '217086e7-b7ec-4f51-80e8-cadc259dcd3a')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('5730e112-8cb6-4125-99f9-763d70bf9872', '3213375036', '7310bba4-05c1-49d1-9f8c-fb7cf0d6ca5a')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('5730e112-8cb6-4125-99f9-763d70bf9872', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('5730e112-8cb6-4125-99f9-763d70bf9872', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('5730e112-8cb6-4125-99f9-763d70bf9872', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('5730e112-8cb6-4125-99f9-763d70bf9872', 'kuali.creditType.credit.degree.1.0,1.5,2.0,2.5,3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('645fe777-6454-4434-a1f7-3f7c91f890c1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '0daac3c5-3477-401a-b7ca-8a1968eab2f7', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '034b9859-aced-4c62-a3a5-a85f40d95a15')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('aa110959-daed-4f86-bdd6-2813080a41c9', 'kuali.attribute.grade.roster.level.type.key', '034b9859-aced-4c62-a3a5-a85f40d95a15', 'kuali.lui.type.activity.offering.lecture', '8442e745-18cc-478a-94c6-95f4c25db5b2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e99a804b-325b-4c19-aa19-7eca9ec818ec', 'kuali.attribute.final.exam.level.type', '034b9859-aced-4c62-a3a5-a85f40d95a15', 'kuali.lu.type.activity.Lecture', 'a470c8d2-c2a7-411c-b7fa-b171ea8f7f34')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8eb37fc4-baeb-4f04-9a0f-72422dd6f620', 'regCodePrefix', '034b9859-aced-4c62-a3a5-a85f40d95a15', '1', '5e5bdff5-711a-4dd7-97b5-1fcb73dd3b71')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('b8bc40d4-b577-4017-8937-156895455289', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '034b9859-aced-4c62-a3a5-a85f40d95a15', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '39cfb1da-152c-44f0-b8b7-726eacf1092b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('85ec1220-f7ae-4333-81da-fe6d80b90135', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:18.945', '', 'HIST619A-CO-FO', '5730e112-8cb6-4125-99f9-763d70bf9872', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST619A-CO-FO', '034b9859-aced-4c62-a3a5-a85f40d95a15', 'b31b0499-ded1-4d2b-8bd2-51ddc1c29a47')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('034b9859-aced-4c62-a3a5-a85f40d95a15', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('13c9a4e1-3491-4ad1-b840-94e72d3f5962', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '82a1e5ca-81ac-4a6b-8fc1-5f4cb0000660', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 10, 10, 'Lecture', 'Courses with lecture', null, 'b3c2a1fe-dc4b-47f0-ab6a-a77ed7e42551')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('294bf7d5-811e-471f-9a32-5db3dc8d368e', 'kuali.attribute.course.evaluation.indicator', 'b3c2a1fe-dc4b-47f0-ab6a-a77ed7e42551', 0, '35107147-0404-4a09-a9d9-2dd00051d2b1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('92377983-3336-48b2-8d24-1d7c7637bb5c', 'kuali.attribute.nonstd.ts.indicator', 'b3c2a1fe-dc4b-47f0-ab6a-a77ed7e42551', 0, '03956414-9175-4ab3-864c-06a96c4faa61')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('52f153a1-54f6-426c-abb9-4ae36c304a2b', 'kuali.attribute.max.enrollment.is.estimate', 'b3c2a1fe-dc4b-47f0-ab6a-a77ed7e42551', 0, '3c9145ee-6dea-4b40-af1c-5efa388ffe80')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d7e510ac-9734-4acf-b117-0799db3be5c6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', 'b3c2a1fe-dc4b-47f0-ab6a-a77ed7e42551', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '3f58d718-0c1a-4a8c-a3c4-0eddf47c7e30')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('cd5f5f76-08fb-485d-b4a7-cdb31eec6f2c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'b3c2a1fe-dc4b-47f0-ab6a-a77ed7e42551', '', 'kuali.lu.code.honorsOffering', 0, '7f799bc6-bbca-422e-b6eb-dfd81b4e7170')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('d22c1c45-8c84-4066-96b3-cbe50d00bac1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:19.028', '', 'b3c2a1fe-dc4b-47f0-ab6a-a77ed7e42551', 'S.KENNETHI', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '3e05af77-5398-4d3b-99a4-8fa5cacdec31')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('95c673eb-2c2f-4032-ac2c-1ffd17282641', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:19.028', '', 'HIST619A-FO-AO', '034b9859-aced-4c62-a3a5-a85f40d95a15', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST619A-FO-AO', 'b3c2a1fe-dc4b-47f0-ab6a-a77ed7e42551', '62570c34-c1ac-4579-85dd-e6710776f11e')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('578cdf4b-1a65-4131-9d26-34d220b50642', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '2a26f9bd-1780-4c3d-81dd-f7019883db9e')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('ba157be3-c694-444a-9506-00e0190f177e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for HIST619A - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '2a26f9bd-1780-4c3d-81dd-f7019883db9e', '9c476268-a01d-41a5-b0bb-196a8101d1de')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('72b59be4-afbe-4ad4-b5d9-e137ce64f18a', 1, '9c476268-a01d-41a5-b0bb-196a8101d1de', 'fde6e708-45b0-4136-b7ba-6b1303b38c99')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('4057f9ae-e561-4c28-943f-52d1dc3e4e1a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '65ef36be-670c-44d3-bae8-a4acec17cbce')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('4a63788a-2c45-4468-aa05-4b8fa5922d36', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '034b9859-aced-4c62-a3a5-a85f40d95a15', 'CL 1', 'CL 1', 'e87dad44-9fd9-4773-87d1-12076d90bdfc')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('358c464a-f9eb-4d0a-818d-b5ac934e260d', 'kuali.lui.type.activity.offering.lecture', 'e87dad44-9fd9-4773-87d1-12076d90bdfc', '3d013ddd-f30e-4f1c-9c85-359f4289ebe2')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('2a26f9bd-1780-4c3d-81dd-f7019883db9e', 'b3c2a1fe-dc4b-47f0-ab6a-a77ed7e42551')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('fde6e708-45b0-4136-b7ba-6b1303b38c99', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('65ef36be-670c-44d3-bae8-a4acec17cbce', 'b3c2a1fe-dc4b-47f0-ab6a-a77ed7e42551')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('65ef36be-670c-44d3-bae8-a4acec17cbce', '034b9859-aced-4c62-a3a5-a85f40d95a15')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('3d013ddd-f30e-4f1c-9c85-359f4289ebe2', 'b3c2a1fe-dc4b-47f0-ab6a-a77ed7e42551')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='e87dad44-9fd9-4773-87d1-12076d90bdfc' where ID='3d013ddd-f30e-4f1c-9c85-359f4289ebe2'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('0d9db2a7-0e95-4d39-a95b-0af1c787d6d5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '0daac3c5-3477-401a-b7ca-8a1968eab2f7', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'c6e23bdc-04f7-4b7c-a9c9-6f6ce9439f8b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b9f188f7-c60d-4b20-a6b7-dd8e2558b643', 'kuali.attribute.registration.group.aocluster.id', 'c6e23bdc-04f7-4b7c-a9c9-6f6ce9439f8b', 'e87dad44-9fd9-4773-87d1-12076d90bdfc', '67336022-2011-480e-83d9-67200ee09106')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a093422e-b7c4-4c92-a1a1-31ad98e4890a', 'kuali.attribute.registration.group.is.generated', 'c6e23bdc-04f7-4b7c-a9c9-6f6ce9439f8b', 1, '948f5eed-958f-418b-ba2d-e3da02202c01')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('8043bb38-01b3-49d7-a6e0-0046ced71fe2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'c6e23bdc-04f7-4b7c-a9c9-6f6ce9439f8b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '1286af10-8d57-44e1-aa07-d2b796f9b5ae')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('32d6fcda-ef8f-4821-822c-d2bdc34c35a5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:19.516', '', 'HIST619A-FO-RG', '034b9859-aced-4c62-a3a5-a85f40d95a15', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST619A-FO-RG', 'c6e23bdc-04f7-4b7c-a9c9-6f6ce9439f8b', 'bdc4eeb6-0a6b-45ba-acb1-0b192bc9f953')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('eac0f9ac-3596-4b0c-aef5-1623a0e6ef08', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:19.517', '', 'HIST619A-RG-AO', 'c6e23bdc-04f7-4b7c-a9c9-6f6ce9439f8b', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST619A-RG-AO', 'b3c2a1fe-dc4b-47f0-ab6a-a77ed7e42551', '94d80c4f-7110-4df5-beaa-bf1772a3d815')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('fd288d6d-81cc-49c1-873a-5185280f2636', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'CLUID-HIST357-199501000000', TIMESTAMP '2014-01-01 19:00:00.0', '', 'American history from the inauguration of Harry S. Truman to the presen with emphasis upon politics and foreign relations, but with consideration of special topics such as radicalism, conservatism, and labor.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST357 CO', 'American history from the inauguration of Harry S. Truman to the presen with emphasis upon politics and foreign relations, but with consideration of special topics such as radicalism, conservatism, and labor.', null, 'ecc9c922-3e9a-4dc4-beec-b28ad096dc88')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('15a337c2-5c2f-431d-a0d9-6dbc9006b719', 'kuali.attribute.funding.source', 'ecc9c922-3e9a-4dc4-beec-b28ad096dc88', '', '840d0e9b-6de4-4c79-9915-3111cd7ae725')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('50df8c80-1fd3-45ce-b538-0bf685a5d4ed', 'kuali.attribute.course.number.internal.suffix', 'ecc9c922-3e9a-4dc4-beec-b28ad096dc88', 'A', '4e86916f-045c-4f8d-917e-9a6f59ed1d8a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c85d702b-887b-44bf-b7cc-e390b3a30274', 'kuali.attribute.wait.list.indicator', 'ecc9c922-3e9a-4dc4-beec-b28ad096dc88', 1, 'd1816d3d-c0bf-4580-b191-94c76e832ad0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d66b118e-f8a5-4706-9d88-92e0f31119a4', 'kuali.attribute.where.fees.attached.flag', 'ecc9c922-3e9a-4dc4-beec-b28ad096dc88', 0, '293cc056-91a4-456f-b07f-d52b6e12b0d8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0a0da8c3-f24d-4ee9-b316-72a166d6c589', 'kuali.attribute.wait.list.type.key', 'ecc9c922-3e9a-4dc4-beec-b28ad096dc88', null, '8eee4706-9f77-48a1-93fe-bd9662c4540d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('72b2cbf8-d0ca-4cdb-957b-986934b15a96', 'kuali.attribute.final.exam.driver', 'ecc9c922-3e9a-4dc4-beec-b28ad096dc88', 'kuali.lu.exam.driver.ActivityOffering', 'f6777a90-96bb-41f8-96fa-add9c841034f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0e243c53-726b-467d-82d5-90795f14695e', 'kuali.attribute.wait.list.level.type.key', 'ecc9c922-3e9a-4dc4-beec-b28ad096dc88', 'AO', '5753ef50-4d35-489c-b2a6-bf32b8636f2e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('38f67535-cf9c-46b0-8b80-42a96b750084', 'kuali.attribute.final.exam.use.matrix', 'ecc9c922-3e9a-4dc4-beec-b28ad096dc88', 1, 'd5574ddd-e611-4758-bb10-79d3d499d8df')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('59993c02-35b4-4942-89d3-7f3b0727cdbe', 'kuali.attribute.final.exam.indicator', 'ecc9c922-3e9a-4dc4-beec-b28ad096dc88', 'STANDARD', '78799ddd-e1f1-46d3-9c1b-5cd6d553b2ac')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a1af4d04-d20b-4c2c-b9cb-dfa666aff8f2', 'kuali.attribute.course.evaluation.indicator', 'ecc9c922-3e9a-4dc4-beec-b28ad096dc88', 0, 'aa8897eb-0bdc-4f41-abb6-9d77f36f1bda')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('2b502af7-fddd-41f3-9b92-0dec3d602471', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'HIST357', 'HIST', '', 'Recent America: 1945-Present', 'ecc9c922-3e9a-4dc4-beec-b28ad096dc88', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'adb11d99-6f59-4686-8b19-77f2fc009592')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('11a09dd6-18ab-4111-a561-d5c7ce923480', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'ecc9c922-3e9a-4dc4-beec-b28ad096dc88', '', 'kuali.lu.code.honorsOffering', '', 'd6b5d2ea-331c-4d74-a51f-884ff327da23')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('ecc9c922-3e9a-4dc4-beec-b28ad096dc88', '3213375036', 'd6013905-9e7b-48d9-bb2c-9d2e429b59c5')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('ecc9c922-3e9a-4dc4-beec-b28ad096dc88', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ecc9c922-3e9a-4dc4-beec-b28ad096dc88', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ecc9c922-3e9a-4dc4-beec-b28ad096dc88', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ecc9c922-3e9a-4dc4-beec-b28ad096dc88', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ecc9c922-3e9a-4dc4-beec-b28ad096dc88', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('8e136db3-b0c4-44dc-a4df-19edb45c2509', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'd48e4b15-08ca-494a-a187-4647da1d2b27', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '480f396b-7ed9-4e2f-a7f7-e7cec8bda24c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('99d51480-a7e5-4169-b965-4851654a3a7d', 'kuali.attribute.grade.roster.level.type.key', '480f396b-7ed9-4e2f-a7f7-e7cec8bda24c', 'kuali.lui.type.activity.offering.lecture', 'f45cfdd2-e0c7-4a98-ba87-5dd44bf46378')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('51768d67-a5d2-405e-9fec-0975fcbb8c8a', 'kuali.attribute.final.exam.level.type', '480f396b-7ed9-4e2f-a7f7-e7cec8bda24c', 'kuali.lu.type.activity.Lecture', '0cf12270-7bf9-40e6-8ef6-84d288cfa38e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('68deef92-2518-4268-9514-04cad223f115', 'regCodePrefix', '480f396b-7ed9-4e2f-a7f7-e7cec8bda24c', '1', 'b3f9bded-f8f1-4f0b-b3f9-2b68f10c5f71')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('07b97e6f-156d-476e-8369-5d0f114816a7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '480f396b-7ed9-4e2f-a7f7-e7cec8bda24c', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '1d4918c3-999a-46f0-a167-db51a1f8c22c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('99f40f62-29d9-4fe9-b212-af130d3d7289', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:21.286', '', 'HIST357-CO-FO', 'ecc9c922-3e9a-4dc4-beec-b28ad096dc88', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST357-CO-FO', '480f396b-7ed9-4e2f-a7f7-e7cec8bda24c', '68fd1d96-6641-4692-8cf4-08b81f742675')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('480f396b-7ed9-4e2f-a7f7-e7cec8bda24c', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('10b83740-1f5c-49f6-ac4d-72eefc1398b8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '156c39bd-7086-40a5-8158-8402f885b33b', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, 'c9e12e14-5177-4471-b64d-e7ac7153dc44')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ab34e4d3-b414-46ae-a9f8-e312da191d30', 'kuali.attribute.nonstd.ts.indicator', 'c9e12e14-5177-4471-b64d-e7ac7153dc44', 0, 'cde216d9-3a5f-4e63-86b5-6619db456549')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2971735d-32d8-4ec5-a348-94a82d74eed9', 'kuali.attribute.course.evaluation.indicator', 'c9e12e14-5177-4471-b64d-e7ac7153dc44', 0, '11435046-acf0-43f1-ba96-3ec17c12d300')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('30cfba98-992a-4e6f-95c8-f2467fb729ce', 'kuali.attribute.max.enrollment.is.estimate', 'c9e12e14-5177-4471-b64d-e7ac7153dc44', 0, '75175892-63e6-4168-a3ee-d60ec3b6f30b')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('78185c2e-8f50-4915-959f-8d45eec9e470', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', 'c9e12e14-5177-4471-b64d-e7ac7153dc44', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '89f57874-1ce6-4d95-ab90-6159062c8ab8')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('3b2cc2af-071b-403c-a6e1-d6d60520d211', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'c9e12e14-5177-4471-b64d-e7ac7153dc44', '', 'kuali.lu.code.honorsOffering', 0, '01d29791-0a58-40ca-92c5-0f521fb90f6b')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('831561a0-ba9c-4afb-89a2-b600bb2338c1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:21.365', '', 'c9e12e14-5177-4471-b64d-e7ac7153dc44', 'B.WESLEYM', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'a121f2d8-4015-4739-981b-11bcb3c8d8e1')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('b83cbacc-7110-4fc6-a8fc-cd90fca039a4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:21.365', '', 'HIST357-FO-AO', '480f396b-7ed9-4e2f-a7f7-e7cec8bda24c', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST357-FO-AO', 'c9e12e14-5177-4471-b64d-e7ac7153dc44', 'c953216a-b7df-4736-9209-b3cfb0e98e86')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('3041619f-e0bf-4ed5-b452-ce364d984208', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'ecfba7e9-5182-48b5-a29d-6028371fd8f9')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('df3d68e0-4ece-4c16-8f41-871e2b761c20', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for HIST357 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'ecfba7e9-5182-48b5-a29d-6028371fd8f9', '2f3e3d34-f77a-4bc5-bcbf-bf96c00164d3')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('98b6afca-94b6-49e5-a20c-e5062fbbcd77', 0, '2f3e3d34-f77a-4bc5-bcbf-bf96c00164d3', 'adb1e2d8-2f6e-412f-b478-8f661542ee91')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('17f25246-f709-432f-99f0-e6d246e7bdd5', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '686c3272-844f-42b6-b744-c9a6505b0e1f')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('ecfba7e9-5182-48b5-a29d-6028371fd8f9', 'c9e12e14-5177-4471-b64d-e7ac7153dc44')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('adb1e2d8-2f6e-412f-b478-8f661542ee91', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('adb1e2d8-2f6e-412f-b478-8f661542ee91', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('adb1e2d8-2f6e-412f-b478-8f661542ee91', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('adb1e2d8-2f6e-412f-b478-8f661542ee91', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('870e8761-3fc5-442f-951c-1228ba1309b0', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '73282100-a4ab-4695-85a0-ccfead35d26c')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('e0eee1b7-8142-40c9-b94b-6b25cf8e2c2f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '480f396b-7ed9-4e2f-a7f7-e7cec8bda24c', 'CL 1', 'CL 1', 'd7f8b571-6738-4b5a-aea4-31c7cd9d8757')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('6421bade-b38c-4abb-9bec-a274f0aa7f1a', 'kuali.lui.type.activity.offering.lecture', 'd7f8b571-6738-4b5a-aea4-31c7cd9d8757', 'c528dd8e-29f1-42fa-817d-394f6c6b3b23')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='17f25246-f709-432f-99f0-e6d246e7bdd5', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='c9e12e14-5177-4471-b64d-e7ac7153dc44', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='686c3272-844f-42b6-b744-c9a6505b0e1f' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('73282100-a4ab-4695-85a0-ccfead35d26c', 'c9e12e14-5177-4471-b64d-e7ac7153dc44')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('73282100-a4ab-4695-85a0-ccfead35d26c', '480f396b-7ed9-4e2f-a7f7-e7cec8bda24c')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('c528dd8e-29f1-42fa-817d-394f6c6b3b23', 'c9e12e14-5177-4471-b64d-e7ac7153dc44')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='d7f8b571-6738-4b5a-aea4-31c7cd9d8757' where ID='c528dd8e-29f1-42fa-817d-394f6c6b3b23'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('3e7897df-ec7e-45d0-83ae-544da1eb40ed', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'd48e4b15-08ca-494a-a187-4647da1d2b27', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'a7380127-0155-4754-8fa6-ba051a8aa22b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9ba3fa3c-c0a3-4a21-90f0-87735db8863f', 'kuali.attribute.registration.group.aocluster.id', 'a7380127-0155-4754-8fa6-ba051a8aa22b', 'd7f8b571-6738-4b5a-aea4-31c7cd9d8757', '9d961dda-d8d7-4430-bc69-b679ed964dc9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4e43eb88-5254-4103-8475-172757675abf', 'kuali.attribute.registration.group.is.generated', 'a7380127-0155-4754-8fa6-ba051a8aa22b', 1, '92b48f40-1f0c-43b2-a368-426ac37aa396')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('cf367da3-3d87-4c59-a7e8-b3217b95aca0', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'a7380127-0155-4754-8fa6-ba051a8aa22b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'da08214c-69f5-44fe-9ce5-e5ac4f8f30bd')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ba56a350-4b14-4f0d-9d6d-2e06fb042395', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:21.859', '', 'HIST357-FO-RG', '480f396b-7ed9-4e2f-a7f7-e7cec8bda24c', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST357-FO-RG', 'a7380127-0155-4754-8fa6-ba051a8aa22b', 'ae545f6d-97eb-4b19-81eb-6b2cb3422300')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('b3be2b16-730e-42d4-b08a-a0ae872732ee', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:21.859', '', 'HIST357-RG-AO', 'a7380127-0155-4754-8fa6-ba051a8aa22b', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST357-RG-AO', 'c9e12e14-5177-4471-b64d-e7ac7153dc44', 'a2005658-716e-40cf-ab49-1c81f48d7ac3')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('489e769d-3063-4f3f-8eda-de4a767456df', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'CLUID-HIST319-198001000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST319D CO', '.', null, 'b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9d35b744-915a-4b36-83f5-d080fd1ed32e', 'kuali.attribute.final.exam.indicator', 'b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', 'STANDARD', 'b65425ca-7763-4c83-8408-afedd1dac015')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('81594a88-fe2b-4b6a-aa25-71ebdf48ba03', 'kuali.attribute.wait.list.type.key', 'b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', null, '230d3457-7c10-4e96-9ff9-d715574d2f55')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5351ee5d-5767-4413-b0cd-1654814718ae', 'kuali.attribute.final.exam.driver', 'b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', 'kuali.lu.exam.driver.ActivityOffering', '82085f45-743c-4f15-81c0-12c8699bc3e3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5e40fbc7-eff4-48c5-8e6f-92e0725e4cf1', 'kuali.attribute.where.fees.attached.flag', 'b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', 0, '7a0fad60-f188-4627-b44e-2c3edb61471c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7d374e52-4612-44d0-b63a-c78804b15a41', 'kuali.attribute.wait.list.indicator', 'b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', 1, 'b44f5ec6-7e0f-43a3-8885-b812737fc65b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5c39f7fb-71ed-44f2-9ae4-db20406c0da9', 'kuali.attribute.course.number.internal.suffix', 'b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', 'A', 'e9b23390-1808-4ef7-87f1-af462cd0512b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a95a2b9a-5b8b-41ef-bad3-0aa357626e0f', 'kuali.attribute.course.evaluation.indicator', 'b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', 0, '43a6fdf4-0324-4a00-8738-c2b84e76764e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a20d67e6-e8d2-4f45-a6e2-a95286d9bfcd', 'kuali.attribute.funding.source', 'b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', '', '02d0b465-3c5c-4c61-bf71-3a89d897146b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('318fd950-e86c-4dcc-a028-f89123893ee2', 'kuali.attribute.wait.list.level.type.key', 'b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', 'AO', 'a77da936-05ce-4f91-b9ad-63191be7475f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('103cd00c-8ad4-4c62-9859-7e9115734cfd', 'kuali.attribute.final.exam.use.matrix', 'b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', 1, 'bdf85b46-8d1b-4606-a8c5-5ba64800f418')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6242d375-c0dd-4217-9049-c9294a2acb07', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'HIST319D', 'HIST', '', 'Special Topics in History', 'b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', '', '', 'kuali.lui.identifier.state.active', 'D', 'kuali.lui.identifier.type.official', '', 'd52095ec-22e2-4b5a-b11a-fab2ad141cd8')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('6bdc317c-7cb4-4a0e-8247-6cab5ea8d701', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', '', 'kuali.lu.code.honorsOffering', '', '998546fc-7f68-4035-a9ec-75994a515d9b')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', '3213375036', 'b18bfba4-156c-4779-94ef-a5192dcd0fbb')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('5142e284-5a15-455d-904a-dcb000be3346', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'ccb3c17e-6eaf-4095-85b7-648120717da9', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '17cfff2c-07ba-457c-a590-532986865f19')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2f0a7c50-45ea-4f19-9796-289bcd4b2560', 'kuali.attribute.grade.roster.level.type.key', '17cfff2c-07ba-457c-a590-532986865f19', 'kuali.lui.type.activity.offering.lecture', '7d8f5fe0-1ed1-4a47-9dce-2ab810d4460c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3b36d114-e023-4968-af81-cea35891d85a', 'regCodePrefix', '17cfff2c-07ba-457c-a590-532986865f19', '1', 'f4d227a5-ec5f-4619-bf3c-c9ab82a9542d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5bffb31a-6e98-41f0-9093-393cc86d7d22', 'kuali.attribute.final.exam.level.type', '17cfff2c-07ba-457c-a590-532986865f19', 'kuali.lu.type.activity.Lecture', 'ce2e9198-a130-43f5-b3c1-d825f52d420a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('34fa8153-294e-4c0d-9544-cadd68ea729e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '17cfff2c-07ba-457c-a590-532986865f19', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a9327aa3-3084-4fd7-b86b-452c5b1c168d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e2e83de7-8a6f-4873-bc61-b30f97af1a69', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:23.805', '', 'HIST319D-CO-FO', 'b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST319D-CO-FO', '17cfff2c-07ba-457c-a590-532986865f19', '40375360-77f7-42e0-8762-17c122659662')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('17cfff2c-07ba-457c-a590-532986865f19', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('10846831-5594-4555-b64f-9c47c1b71c33', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'dc4eb45c-86c1-439b-9b36-78c9f97bb5a2', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, '7f9c52fd-ef55-4507-98bd-6b11eebceb1a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('085cfd85-411a-4098-b799-965a6b1d7c2c', 'kuali.attribute.nonstd.ts.indicator', '7f9c52fd-ef55-4507-98bd-6b11eebceb1a', 0, '73b8a853-6c8b-4b48-98a2-bd57f61b6405')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('940ca35e-507f-447b-aee3-37c3f72ecc32', 'kuali.attribute.course.evaluation.indicator', '7f9c52fd-ef55-4507-98bd-6b11eebceb1a', 0, 'b0d750d1-e662-4f98-858b-63471aad31ca')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2ff489d6-fdfc-456b-9f14-8ed47cdb453f', 'kuali.attribute.max.enrollment.is.estimate', '7f9c52fd-ef55-4507-98bd-6b11eebceb1a', 0, 'b23e6c7f-4cf0-47b3-8952-828c20b7afe2')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('0d4e42a1-532d-437b-9c66-3d86d9c4dfb6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '7f9c52fd-ef55-4507-98bd-6b11eebceb1a', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '75da3b3b-f21d-4f14-ad32-b50a6413089c')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('c7dcadf4-643a-4f32-9d7e-2404323839ed', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '7f9c52fd-ef55-4507-98bd-6b11eebceb1a', '', 'kuali.lu.code.honorsOffering', 0, '5ae23490-f1df-4fb5-8e03-0ef9ef8c2a94')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('bc8145de-94ad-4757-b3b2-21c234ed2832', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:23.917', '', '7f9c52fd-ef55-4507-98bd-6b11eebceb1a', 'D.ALEXD', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '999b179d-f07f-4c69-87be-1592ed23255c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('0b35e455-37a0-45bd-9f9c-59109d09c90b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:23.917', '', 'HIST319D-FO-AO', '17cfff2c-07ba-457c-a590-532986865f19', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST319D-FO-AO', '7f9c52fd-ef55-4507-98bd-6b11eebceb1a', 'c02409e2-13ff-4fb6-8271-70c4f58d225d')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('9b74ca83-e02a-45bb-b14a-4289ad18bd70', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '21de8592-dc40-4bbd-98de-2f930a0833f4')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('8c38e895-2e7e-4a5c-83fb-f700b01394f2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A Schedule Request for HIST319D section 0101', 'Schedule request for HIST319D - A', 'A Schedule Request for HIST319D section 0101', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', 'd2b2aed2-8360-4f0d-a151-4ff438c60253', '21de8592-dc40-4bbd-98de-2f930a0833f4', 'd6896821-b316-49ce-a1d2-180744ba5f89')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('c20f1aa6-f2c7-460c-a601-1fd9541fcaa9', 0, 'd6896821-b316-49ce-a1d2-180744ba5f89', '37303b12-160e-4b68-a6a6-92ab19ce7598')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('5b4b7195-1dff-47fb-bf25-049e63683f6e', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'b12f4370-a6fb-4e81-b774-ab0fee42fdf6')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('21de8592-dc40-4bbd-98de-2f930a0833f4', '7f9c52fd-ef55-4507-98bd-6b11eebceb1a')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('37303b12-160e-4b68-a6a6-92ab19ce7598', 'af22fa59-42ab-4d09-9a5b-6980f8b4ae7d')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('37303b12-160e-4b68-a6a6-92ab19ce7598', 'b4a5b349-cdc0-4d40-992d-41a07f1cbc2c')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('37303b12-160e-4b68-a6a6-92ab19ce7598', 'F08200F106FA315CE040440A9B9A3EDC')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('3a4440ff-806d-48b9-bd1a-4d0430c161de', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '48a774c5-59ff-4b90-93f1-0a501666a77e')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('e13156cc-a093-48e5-8fb7-eb2a9c94ee91', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '17cfff2c-07ba-457c-a590-532986865f19', 'CL 1', 'CL 1', 'b396637c-6307-4a23-a62b-5a58992a2893')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('f9208cfb-4ed9-4464-9b00-3c35741e0904', 'kuali.lui.type.activity.offering.lecture', 'b396637c-6307-4a23-a62b-5a58992a2893', 'e8b668c0-fe15-484d-a166-d0c3a212268b')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='5b4b7195-1dff-47fb-bf25-049e63683f6e', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='7f9c52fd-ef55-4507-98bd-6b11eebceb1a', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='b12f4370-a6fb-4e81-b774-ab0fee42fdf6' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('48a774c5-59ff-4b90-93f1-0a501666a77e', '7f9c52fd-ef55-4507-98bd-6b11eebceb1a')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('48a774c5-59ff-4b90-93f1-0a501666a77e', '17cfff2c-07ba-457c-a590-532986865f19')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('e8b668c0-fe15-484d-a166-d0c3a212268b', '7f9c52fd-ef55-4507-98bd-6b11eebceb1a')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='b396637c-6307-4a23-a62b-5a58992a2893' where ID='e8b668c0-fe15-484d-a166-d0c3a212268b'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('e30031ed-d07b-47c1-8bf5-8657e9cf5377', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'ccb3c17e-6eaf-4095-85b7-648120717da9', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'da98ceab-4dce-4768-b193-625ae1bb2224')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3c6505b5-de20-45a6-9bd2-57f956b8695b', 'kuali.attribute.registration.group.is.generated', 'da98ceab-4dce-4768-b193-625ae1bb2224', 1, '72e757d1-b97b-49b8-9e5b-565477e8b3e4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9059e82a-3514-4b30-9616-d6bb5eef32a2', 'kuali.attribute.registration.group.aocluster.id', 'da98ceab-4dce-4768-b193-625ae1bb2224', 'b396637c-6307-4a23-a62b-5a58992a2893', '875c4ced-d782-4024-bd80-da5c3fb4d028')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6ce318af-ee47-4da1-b606-41d759b29427', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'da98ceab-4dce-4768-b193-625ae1bb2224', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '46dd9ffd-cb06-47a2-9922-8c898402c0b2')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('b531c3b8-e8f3-4829-b140-67190247e79a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:24.656', '', 'HIST319D-FO-RG', '17cfff2c-07ba-457c-a590-532986865f19', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST319D-FO-RG', 'da98ceab-4dce-4768-b193-625ae1bb2224', '0c6a0473-0b01-4010-981c-970c8573c569')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('30ff8040-1629-49d9-855d-55d3f1836fea', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:24.656', '', 'HIST319D-RG-AO', 'da98ceab-4dce-4768-b193-625ae1bb2224', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST319D-RG-AO', '7f9c52fd-ef55-4507-98bd-6b11eebceb1a', '7a74d6b2-1ab1-4de7-8b36-2fe23559dd98')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('7c7dd1db-322a-4590-9b2c-71d09f03f4a2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'edc7c6fb-651a-4c4e-a905-fcacf3a04c76', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Twentieth century U.S. civil rights movement from the vantage point of women, considering both women''s involvement in the legal campaigns and political protests and the impact of civil rights struggles on women''s condiition, status, and identity.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST360 CO', 'Twentieth century U.S. civil rights movement from the vantage point of women, considering both women''s involvement in the legal campaigns and political protests and the impact of civil rights struggles on women''s condiition, status, and identity.', null, '08ef9d01-c637-4b89-bd51-0f37bb326c41')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('edbe3c92-42a4-4660-b168-2347916e5c4e', 'kuali.attribute.final.exam.driver', '08ef9d01-c637-4b89-bd51-0f37bb326c41', 'kuali.lu.exam.driver.ActivityOffering', '571d15ba-1877-4ca1-bf5a-2abd99a368ba')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1183a8a4-151d-40a1-b7f4-55dd0f977479', 'kuali.attribute.course.number.internal.suffix', '08ef9d01-c637-4b89-bd51-0f37bb326c41', 'A', 'ffb7b19c-aa29-4fec-968f-22f542fcb785')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ef878928-42f9-4660-962a-eb30a60a33da', 'kuali.attribute.course.evaluation.indicator', '08ef9d01-c637-4b89-bd51-0f37bb326c41', 0, '833e58aa-58eb-48b6-96cb-d295c807461c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('caee4be8-1820-4fcc-be6e-e332437b100d', 'kuali.attribute.final.exam.indicator', '08ef9d01-c637-4b89-bd51-0f37bb326c41', 'STANDARD', 'c0ce28fc-a4e5-4825-8ef8-c6181f07f0aa')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f63d4369-f07a-47a2-be2a-644f79af4aa0', 'kuali.attribute.where.fees.attached.flag', '08ef9d01-c637-4b89-bd51-0f37bb326c41', 0, '5b0be92c-0a32-462d-ac67-5675bd1f842a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('724dda6c-cca0-4585-8f4b-e4b8a36579d8', 'kuali.attribute.funding.source', '08ef9d01-c637-4b89-bd51-0f37bb326c41', '', 'd85290de-65ff-4251-a541-80cfc9f9991f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4680de23-c760-41e2-9fe8-e2f64f77adde', 'kuali.attribute.final.exam.use.matrix', '08ef9d01-c637-4b89-bd51-0f37bb326c41', 1, 'baa38226-b270-42d9-9989-bddee91c4d71')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fd2e8c8e-9750-4485-95a5-7dcd465831ed', 'kuali.attribute.wait.list.indicator', '08ef9d01-c637-4b89-bd51-0f37bb326c41', 1, 'd53ba238-1adc-4318-9aa6-3ce9aa8d3b2f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4f32eab9-a858-4107-8daf-860c8ce88b96', 'kuali.attribute.wait.list.level.type.key', '08ef9d01-c637-4b89-bd51-0f37bb326c41', 'AO', '901ff3e2-b44b-448f-a6c7-d03666c460b3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ccdc7004-2fd8-467e-b8b7-ac01a9d03102', 'kuali.attribute.wait.list.type.key', '08ef9d01-c637-4b89-bd51-0f37bb326c41', null, '5b7b3bb2-d286-4154-9f92-02b22ca12a98')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('74a25bbe-f978-4887-b2c8-9d9521afb3c6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'HIST360', 'HIST', '', 'Women and the Civil Rights Movement', '08ef9d01-c637-4b89-bd51-0f37bb326c41', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '279ce211-8305-4f6f-89d4-780db6245180')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('0385563e-1729-41d5-9930-3c0d77371da7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '08ef9d01-c637-4b89-bd51-0f37bb326c41', '', 'kuali.lu.code.honorsOffering', '', 'e0323d10-3ba4-4d3f-ab10-c4dbc6dd2dc7')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('08ef9d01-c637-4b89-bd51-0f37bb326c41', '3213375036', '86a1c753-6fad-46cc-88b6-515fa0390519')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('08ef9d01-c637-4b89-bd51-0f37bb326c41', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('08ef9d01-c637-4b89-bd51-0f37bb326c41', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('08ef9d01-c637-4b89-bd51-0f37bb326c41', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('08ef9d01-c637-4b89-bd51-0f37bb326c41', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('08ef9d01-c637-4b89-bd51-0f37bb326c41', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ac4eb67d-97f4-4782-b96e-53833d7ce22b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'f534f531-03b9-418f-996c-8eb89a2e1af8', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '340c9d24-3ecd-452c-b466-231e40bb356e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ccce7c8e-f9b7-4d8c-b0cc-1a18c09c0e5d', 'kuali.attribute.final.exam.level.type', '340c9d24-3ecd-452c-b466-231e40bb356e', 'kuali.lu.type.activity.Lecture', 'fee55530-3d16-4c73-b8e1-e0a0ec2a2707')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('916ea79b-8cc7-4c07-aef5-e47926a2d709', 'kuali.attribute.grade.roster.level.type.key', '340c9d24-3ecd-452c-b466-231e40bb356e', 'kuali.lui.type.activity.offering.lecture', 'e7b05b64-476c-44b4-99c5-fe702214532a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7eba57ff-0c92-44da-b27b-fec344e97d42', 'regCodePrefix', '340c9d24-3ecd-452c-b466-231e40bb356e', '1', '7d40db27-f6b3-43a4-8395-015830b85cd3')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('efb4b27c-b998-40f1-9dcf-576afc7356c2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '340c9d24-3ecd-452c-b466-231e40bb356e', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '3080c9ef-1544-4116-a5b1-6c9283a586f7')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('61f14b7f-e5de-4cec-a18a-f6cab162e401', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:26.447', '', 'HIST360-CO-FO', '08ef9d01-c637-4b89-bd51-0f37bb326c41', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST360-CO-FO', '340c9d24-3ecd-452c-b466-231e40bb356e', 'd447670e-2bb8-4a96-b4f8-c4b18107571d')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('340c9d24-3ecd-452c-b466-231e40bb356e', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('29883747-1f2f-4d74-86c4-b38d105ca801', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '9a7b3050-799a-43e0-8a19-535717f3e32d', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, '6841e959-6df8-4dbf-b897-49321ecc687f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b21001ea-875e-40b5-b93b-121de04555b9', 'kuali.attribute.max.enrollment.is.estimate', '6841e959-6df8-4dbf-b897-49321ecc687f', 0, 'a9051f57-9ba4-49c4-bc59-6edf85e178e3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3d43ddd1-4aa5-4741-bb55-6b4140f288d0', 'kuali.attribute.nonstd.ts.indicator', '6841e959-6df8-4dbf-b897-49321ecc687f', 0, '9269c64e-3f63-4be3-b34c-89053546dd13')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4ccc2943-6f1a-45e6-abe0-2a22ac35de58', 'kuali.attribute.course.evaluation.indicator', '6841e959-6df8-4dbf-b897-49321ecc687f', 0, 'ba4f5f02-7d0c-4deb-bbbf-64888b18c4c9')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a6e81796-4a5a-4d71-8789-df98116fe121', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '6841e959-6df8-4dbf-b897-49321ecc687f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'd9500216-138c-449d-ae21-18efaec73dc0')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('19af9d68-79c0-4e06-bf34-596bc767d0fa', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '6841e959-6df8-4dbf-b897-49321ecc687f', '', 'kuali.lu.code.honorsOffering', 0, '9a9ac671-3d05-4d78-bbba-078429428366')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('61fa9a1c-e39d-4a20-a6c6-e48d1f95666b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:26.516', '', '6841e959-6df8-4dbf-b897-49321ecc687f', 'S.CLANCYW', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'fe99b3d6-48d3-464b-aa20-be25a79d79e5')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('06e44485-7cd4-4f10-9dad-f1d55874991d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:26.517', '', 'HIST360-FO-AO', '340c9d24-3ecd-452c-b466-231e40bb356e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST360-FO-AO', '6841e959-6df8-4dbf-b897-49321ecc687f', '746cfe2e-014f-448d-aabf-ca30331af744')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('c8ea0ceb-b1d9-4207-a65c-b546d3273583', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '6550dbcd-4e8b-4485-abdb-9412a87c4227')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('ce78fa05-5acd-438b-af44-17464de1858e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for HIST360 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '6550dbcd-4e8b-4485-abdb-9412a87c4227', '582ce4e2-180b-43fc-8643-beffbbe69690')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('b298a5a7-2323-47ca-8e54-8aea45759f90', 0, '582ce4e2-180b-43fc-8643-beffbbe69690', '35a2f36a-37f9-4db4-838d-d71ec61fb8b3')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('69d4874c-6c6a-4a19-a30b-44bd37390442', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'c5d7416e-d6e4-4ee6-a97b-95868f13d965')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('6550dbcd-4e8b-4485-abdb-9412a87c4227', '6841e959-6df8-4dbf-b897-49321ecc687f')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('35a2f36a-37f9-4db4-838d-d71ec61fb8b3', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('35a2f36a-37f9-4db4-838d-d71ec61fb8b3', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('35a2f36a-37f9-4db4-838d-d71ec61fb8b3', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('35a2f36a-37f9-4db4-838d-d71ec61fb8b3', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('2c0a50e6-cdb5-4934-a9da-7e76de1adafa', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '55d17eaa-6eaa-41cb-a641-3afac2e15c70')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('55c06412-8680-47da-a614-07df0aa7551f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '340c9d24-3ecd-452c-b466-231e40bb356e', 'CL 1', 'CL 1', '61e74255-1891-4694-8b59-6c6057f60512')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('7e9fbe2f-9297-4ddd-9a9b-a5928ea707b4', 'kuali.lui.type.activity.offering.lecture', '61e74255-1891-4694-8b59-6c6057f60512', '45c57373-c5f8-4ec1-9ef3-599a37950231')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='69d4874c-6c6a-4a19-a30b-44bd37390442', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='6841e959-6df8-4dbf-b897-49321ecc687f', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='c5d7416e-d6e4-4ee6-a97b-95868f13d965' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('55d17eaa-6eaa-41cb-a641-3afac2e15c70', '6841e959-6df8-4dbf-b897-49321ecc687f')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('55d17eaa-6eaa-41cb-a641-3afac2e15c70', '340c9d24-3ecd-452c-b466-231e40bb356e')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('45c57373-c5f8-4ec1-9ef3-599a37950231', '6841e959-6df8-4dbf-b897-49321ecc687f')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='61e74255-1891-4694-8b59-6c6057f60512' where ID='45c57373-c5f8-4ec1-9ef3-599a37950231'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('acfb6d0b-00c4-43ea-9ce9-798497404da4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'f534f531-03b9-418f-996c-8eb89a2e1af8', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '16c81595-fbfe-462e-aa41-38724692ac37')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('46ed3766-8535-46bc-97a8-41a0832b7781', 'kuali.attribute.registration.group.aocluster.id', '16c81595-fbfe-462e-aa41-38724692ac37', '61e74255-1891-4694-8b59-6c6057f60512', 'd7f3e6c9-ab31-408e-b07d-37bdde16f5aa')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b5a21503-eb83-4b89-85de-c909a0faffd7', 'kuali.attribute.registration.group.is.generated', '16c81595-fbfe-462e-aa41-38724692ac37', 1, 'a5ac6c05-ec6c-425c-be88-d09594fd6538')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('64efc16a-96c6-401f-b477-bce22f876cb0', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', '16c81595-fbfe-462e-aa41-38724692ac37', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '4ef158aa-5dfd-49b8-878d-1f1b85b23c4c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('2d3a84f3-2317-44b5-8fc9-b8a2ef8e3458', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:27.13', '', 'HIST360-FO-RG', '340c9d24-3ecd-452c-b466-231e40bb356e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST360-FO-RG', '16c81595-fbfe-462e-aa41-38724692ac37', '0a7bbcc5-4a2e-4675-a67d-6abea0a4c7f2')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('6ea5c6db-2bae-4827-9ff9-498af3064402', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:27.131', '', 'HIST360-RG-AO', '16c81595-fbfe-462e-aa41-38724692ac37', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST360-RG-AO', '6841e959-6df8-4dbf-b897-49321ecc687f', '6474b6bc-79e1-41a4-b4cb-a1ccef5050bb')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('0a8f3904-781d-4aae-9442-ba2f23635b00', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'a1e31e75-3d71-4ebf-96c5-12a25d55d23a', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Covers a wide range of topics in professional development for new graduate students.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'CHEM611 CO', 'Covers a wide range of topics in professional development for new graduate students.', null, '35849f0f-d078-4daf-9ac0-60228b8f31cf')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('08ae7c5f-29d9-424b-9e52-1d31b5bb1d62', 'kuali.attribute.funding.source', '35849f0f-d078-4daf-9ac0-60228b8f31cf', '', '3a3e7013-2665-4a7c-b39d-eadece4973df')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('eafba381-e445-4375-947c-375749b5cb7e', 'kuali.attribute.wait.list.type.key', '35849f0f-d078-4daf-9ac0-60228b8f31cf', null, 'cc8f8f29-8f92-4c41-ba63-7fcf59af9982')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('143d9699-3db7-4418-85aa-d12977889bab', 'kuali.attribute.final.exam.driver', '35849f0f-d078-4daf-9ac0-60228b8f31cf', 'kuali.lu.exam.driver.ActivityOffering', '6ec75ad1-9e4c-4456-a071-a33541513e88')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2b5008b3-6877-4bea-805c-35dfac0eb49b', 'kuali.attribute.wait.list.indicator', '35849f0f-d078-4daf-9ac0-60228b8f31cf', 1, '8ebf1948-522c-4469-be58-9c64d5a1b8d4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('48e4837a-fed7-4081-8dc0-1bbd97ae20fb', 'kuali.attribute.course.evaluation.indicator', '35849f0f-d078-4daf-9ac0-60228b8f31cf', 0, 'cbab0768-22ca-4563-ab79-3952b6f9e343')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1e0a51bc-3bba-463a-833d-6ba2fdb8d4a5', 'kuali.attribute.where.fees.attached.flag', '35849f0f-d078-4daf-9ac0-60228b8f31cf', 0, '181fd958-e7e3-4b93-9061-dfb902a95b0f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('683ab9a6-8e9c-4f49-8fc2-52ea56e371eb', 'kuali.attribute.final.exam.indicator', '35849f0f-d078-4daf-9ac0-60228b8f31cf', 'STANDARD', '5d6507c8-062e-4da9-b71e-248b295351ab')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b35b3072-7a20-4e91-8e1f-fdb1234069e3', 'kuali.attribute.final.exam.use.matrix', '35849f0f-d078-4daf-9ac0-60228b8f31cf', 1, '90694726-e066-48d6-ba9f-1be5242327d0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('61c84e7b-2a85-438c-af70-fc11d5fe5cb6', 'kuali.attribute.course.number.internal.suffix', '35849f0f-d078-4daf-9ac0-60228b8f31cf', 'A', 'edb4f0b6-38e4-4fcd-a393-f092657dfd8e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('54288d6d-fe0b-4a3d-8142-4306acb7759e', 'kuali.attribute.wait.list.level.type.key', '35849f0f-d078-4daf-9ac0-60228b8f31cf', 'AO', 'b4eadd6e-88b3-442c-a359-d385c5c4d4fd')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('cc6b10ad-693a-4266-af6c-f0f2a7e996b7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'CHEM611', 'CHEM', '', 'Professional Skills for New Graduate Students', '35849f0f-d078-4daf-9ac0-60228b8f31cf', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ab06213a-b95a-45d5-9b4e-4c330c785d0e')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('7026a9d7-92e3-4b4c-8533-d23e86ea276a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '35849f0f-d078-4daf-9ac0-60228b8f31cf', '', 'kuali.lu.code.honorsOffering', '', '21d2fb16-b718-4aac-b32b-eab91705f2bf')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('35849f0f-d078-4daf-9ac0-60228b8f31cf', '4284516367', '3c400e90-b06e-4522-99a3-2d80be1f9e6b')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('35849f0f-d078-4daf-9ac0-60228b8f31cf', '4284516367')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('35849f0f-d078-4daf-9ac0-60228b8f31cf', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('35849f0f-d078-4daf-9ac0-60228b8f31cf', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('35849f0f-d078-4daf-9ac0-60228b8f31cf', 'kuali.creditType.credit.degree.1.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('e7417234-ac0e-4f98-8839-edc2cb138019', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'c5ce342e-1099-4eb7-9d13-3eb727150060', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '7b6352a5-000c-4b13-bdc7-ed4124ab14d2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6e619b82-b3a3-49c6-ac5f-b3a2d36312da', 'kuali.attribute.final.exam.level.type', '7b6352a5-000c-4b13-bdc7-ed4124ab14d2', 'kuali.lu.type.activity.Lecture', '5179c70b-1a43-41a0-8e82-cb69d00c3af8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c1e4e5de-3993-415d-a8f2-5ef203401d42', 'regCodePrefix', '7b6352a5-000c-4b13-bdc7-ed4124ab14d2', '1', 'cf924bce-2d10-4822-833a-83ca88288e7b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6ece618e-89dd-4316-8049-b4e628364d8b', 'kuali.attribute.grade.roster.level.type.key', '7b6352a5-000c-4b13-bdc7-ed4124ab14d2', 'kuali.lui.type.activity.offering.lecture', '9de1430a-bbef-415f-9c61-8a240dcb4f33')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('94ec2b35-751e-4a83-aa58-40b999061edf', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '7b6352a5-000c-4b13-bdc7-ed4124ab14d2', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '515be634-efde-46a4-b881-16ea934b06c9')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('260630ca-fccd-46cf-ad38-60cb5a38e3bf', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:31.096', '', 'CHEM611-CO-FO', '35849f0f-d078-4daf-9ac0-60228b8f31cf', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'CHEM611-CO-FO', '7b6352a5-000c-4b13-bdc7-ed4124ab14d2', 'b1581ac2-36dc-4b0e-bb41-a0c9a8876924')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('7b6352a5-000c-4b13-bdc7-ed4124ab14d2', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('fffd063f-b7e0-4fe2-b3f4-976e2cedb691', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '3c719070-e600-425e-befc-1eb4b5aa3b08', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 10, 10, 'Lecture', 'Courses with lecture', null, '4e8c93a2-6361-4e5a-922f-601204aef0dc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0003c9b7-dffc-4212-b796-7841a065cbca', 'kuali.attribute.max.enrollment.is.estimate', '4e8c93a2-6361-4e5a-922f-601204aef0dc', 0, 'f3e7afcb-bfa4-43e0-b1bf-259ea8a64faa')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8d31cb24-50fb-4f82-badb-9fd01422187f', 'kuali.attribute.nonstd.ts.indicator', '4e8c93a2-6361-4e5a-922f-601204aef0dc', 0, '68d85205-a1fe-476f-8b19-e05454bc4e2a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8dc57d3c-c5ec-4c0d-9d10-cb3a4eae3c0b', 'kuali.attribute.course.evaluation.indicator', '4e8c93a2-6361-4e5a-922f-601204aef0dc', 0, '7b0d4395-fca8-40b1-af68-4b685c9771bd')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('74c8c547-fcc7-4de1-ab92-7bd2863b2693', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '4e8c93a2-6361-4e5a-922f-601204aef0dc', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'e36bb880-274b-4643-b38a-efe22cc7ee7f')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('e9facbef-21a1-4236-ad1f-604a645a197f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '4e8c93a2-6361-4e5a-922f-601204aef0dc', '', 'kuali.lu.code.honorsOffering', 0, '4b47d950-15e6-4a9d-bf53-5f96ceac2f60')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('799689f1-090a-4391-a28e-b4014b5475b7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:31.167', '', '4e8c93a2-6361-4e5a-922f-601204aef0dc', 'L.ALPHAD', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'cd43d7a1-b001-4c3c-bf69-837d688776c0')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a36b3438-3d13-4060-9862-28bbe186d6ad', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:31.167', '', 'CHEM611-FO-AO', '7b6352a5-000c-4b13-bdc7-ed4124ab14d2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM611-FO-AO', '4e8c93a2-6361-4e5a-922f-601204aef0dc', '1856bfb0-ec5c-4986-96f8-e213f81fc09c')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('723c634c-5eb8-4603-bb45-9732cb9857aa', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'd28d0e3e-f31e-4c2e-8d1a-239a76c8dda2')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('c416047e-b61d-49e1-b229-487b8a7da1a4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for CHEM611 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'd28d0e3e-f31e-4c2e-8d1a-239a76c8dda2', '4f1b10b0-40c1-409a-98f9-baeed4f65219')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('567a8646-4d6b-4615-afe0-d65aebdba59e', 0, '4f1b10b0-40c1-409a-98f9-baeed4f65219', '109c5760-0c73-430f-b322-270e861bd28e')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('671ecc31-d303-4195-95b1-aea9526b8b6f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '19bbfb25-d8d4-4968-a54b-8c85b8324632')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('51201116-3a70-4471-8e81-6a309d730080', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '7b6352a5-000c-4b13-bdc7-ed4124ab14d2', 'CL 1', 'CL 1', '9f241b54-ab86-4b12-b316-651577d7f8a6')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('b02b8c37-b4a1-464f-a6e9-5ebafdc56e68', 'kuali.lui.type.activity.offering.lecture', '9f241b54-ab86-4b12-b316-651577d7f8a6', 'ba4ebd28-d496-469f-90eb-a08807f46868')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('d28d0e3e-f31e-4c2e-8d1a-239a76c8dda2', '4e8c93a2-6361-4e5a-922f-601204aef0dc')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('109c5760-0c73-430f-b322-270e861bd28e', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('109c5760-0c73-430f-b322-270e861bd28e', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('109c5760-0c73-430f-b322-270e861bd28e', '2246d609-9fbb-4d06-b45e-a57ef80539b7')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('109c5760-0c73-430f-b322-270e861bd28e', 'F08200F106A8315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('19bbfb25-d8d4-4968-a54b-8c85b8324632', '4e8c93a2-6361-4e5a-922f-601204aef0dc')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('19bbfb25-d8d4-4968-a54b-8c85b8324632', '7b6352a5-000c-4b13-bdc7-ed4124ab14d2')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('ba4ebd28-d496-469f-90eb-a08807f46868', '4e8c93a2-6361-4e5a-922f-601204aef0dc')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='9f241b54-ab86-4b12-b316-651577d7f8a6' where ID='ba4ebd28-d496-469f-90eb-a08807f46868'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('dc58034f-5db4-4f55-a51f-0e14d4debaad', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'c5ce342e-1099-4eb7-9d13-3eb727150060', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '871be72d-2e1b-496c-9132-9279f79cdb18')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b41621a8-b8a4-41bf-98fd-5ec5cb7b7c2a', 'kuali.attribute.registration.group.aocluster.id', '871be72d-2e1b-496c-9132-9279f79cdb18', '9f241b54-ab86-4b12-b316-651577d7f8a6', '621ac753-cb70-4c8c-baef-cf49e698c496')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('49374ab6-b9c0-428f-acf1-8b39b10584d0', 'kuali.attribute.registration.group.is.generated', '871be72d-2e1b-496c-9132-9279f79cdb18', 1, '9a0909a4-1629-4359-b0fe-82db1e701c25')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('90fbccf9-a956-4066-a94a-07a225617bd6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', '871be72d-2e1b-496c-9132-9279f79cdb18', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '164dbcc6-e819-40a2-879d-8aa997f2c2c0')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('1ec957df-68f8-423e-8db6-fd876abe4860', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:31.738', '', 'CHEM611-FO-RG', '7b6352a5-000c-4b13-bdc7-ed4124ab14d2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM611-FO-RG', '871be72d-2e1b-496c-9132-9279f79cdb18', 'b2b649b4-e28b-44bd-9389-ef2b90469d7b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('0cc2d04c-403c-4a0f-b08c-b515650eb170', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:31.739', '', 'CHEM611-RG-AO', '871be72d-2e1b-496c-9132-9279f79cdb18', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM611-RG-AO', '4e8c93a2-6361-4e5a-922f-601204aef0dc', '3501c869-33e4-4262-8d1d-f6941cdabbfb')
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='aa22addb-51dc-4b4b-9ab6-19af629aa47d', ATTR_KEY='kuali.soc.rollover.result.dynattr.date.completed', OWNER_ID='8eb89914-1b01-4d50-af48-dab273b4554d', ATTR_VALUE='2014 Jan 22 12:50:31 America/New_York' where ID='401f9b67-014c-4a31-b85e-67ae4de802be'
/
update KSEN_SOC_ROR set OBJ_ID='086ca850-8a66-4720-b0d4-cf7f5955aab1', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ITEMS_EXPECTED=48, ITEMS_PROCESSED=20, MESG_FORMATTED='', MESG_PLAIN='', SOC_ROR_STATE='kuali.soc.rollover.state.running', SOC_ROR_TYPE='kuali.soc.rollover.results.type.rollover', SOURCE_SOC_ID='03192a24-765a-4b9d-b638-5158430e09c8', TARGET_SOC_ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', TARGET_TERM_ID='kuali.atp.2014Winter' where ID='8eb89914-1b01-4d50-af48-dab273b4554d' and VER_NBR=3
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('0519887e-f2be-4003-b22e-c65c2cb57d88', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '36f41c50-1321-4f93-aa8a-e5440943aeb2', '480aa452-84c2-41c6-83b0-35daf587531e', '3962cadd-b4db-4c8a-b954-3cc91922e527')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ee139daf-7626-4c08-ba78-421eda3ea582', 'activityOfferingsCreated', '3962cadd-b4db-4c8a-b954-3cc91922e527', '2', 'b846c057-2b2c-458f-a91e-04842f810862')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('27459b6b-80e0-4ef8-b26a-583ada89306a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '36819558-e390-4681-9d8e-5eed0642c03a', '8ea0c9ca-35be-4d81-8020-f33e20f024f6', '249ae5f8-cd9d-4307-92ff-1b8714e8d221')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('345fc7e8-f1f9-4f56-93d4-70fd816fafd2', 'activityOfferingsCreated', '249ae5f8-cd9d-4307-92ff-1b8714e8d221', '1', 'f2503eba-b1d6-4264-a99d-db68ffa4bb03')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('bd11bcdd-f702-4a6a-99a4-0d39043acbc6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '41172f6c-3925-4d40-acb3-9024c817e168', '89c447f2-65f8-4986-9239-0fd083402241', '2396cccc-b602-4bc6-ac5c-8fda1e810594')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('411b21b3-572b-4c8c-9251-dcb12bf5898b', 'activityOfferingsCreated', '2396cccc-b602-4bc6-ac5c-8fda1e810594', '2', 'a5131192-d8c8-4b98-9682-47271aa4ccf6')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('efcb5759-eda3-48b9-8c87-d414bd9fa8c9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', '4db733fc-b8cc-46f2-acc1-b82dc5803334', '', 'e66cde5d-5405-4fba-aeba-8bd9c76407f1')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('17b519fd-eec1-4773-9eb1-be8cbaa425dd', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '4edb69b1-b2e6-4ee4-a5a9-3c4f4a965b0f', '5730e112-8cb6-4125-99f9-763d70bf9872', '45025021-1b3e-46b2-ba72-65b3023044aa')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('eee97119-fb28-42be-989e-9c38099d82ec', 'activityOfferingsCreated', '45025021-1b3e-46b2-ba72-65b3023044aa', '1', '02bcb273-cab2-4864-b099-8cc9eba84661')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('3283e806-b40e-4e0d-aae5-6175c812b062', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '56cd1552-174e-4a30-8420-0dab3c8a9aa2', 'ecc9c922-3e9a-4dc4-beec-b28ad096dc88', 'ae71abd6-33ee-4218-8e24-9d262ed97b37')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0115f471-7723-4221-be61-50416b24f22c', 'activityOfferingsCreated', 'ae71abd6-33ee-4218-8e24-9d262ed97b37', '1', '65aa5b62-4c98-40a1-b6fb-981fba309d08')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('d604d7ba-8713-4859-afb2-874792705790', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '5931b2d4-b41c-499e-b45e-f8baafc0aa3a', 'b3a0b6e2-cf51-4a20-87e1-e3abc4fa87c0', 'a81bdd64-56be-4abb-8add-cf1d6f16eb63')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1b3c124b-893b-4594-ae33-20857eccabea', 'activityOfferingsCreated', 'a81bdd64-56be-4abb-8add-cf1d6f16eb63', '1', 'bf779ded-89c2-42dc-8380-d98fb5438191')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('a234168f-a60a-4281-b3a1-2dcc39baa372', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '5e8b6fff-a478-4720-b8f1-2df0746434ce', '08ef9d01-c637-4b89-bd51-0f37bb326c41', 'f44b3748-4023-4c28-b545-c9f3609a37cd')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9679272a-7acd-4389-a837-b67b5665f7d9', 'activityOfferingsCreated', 'f44b3748-4023-4c28-b545-c9f3609a37cd', '1', '0e85996a-2122-4e89-b3b5-3ef15272b801')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('86e3d27e-138b-4dde-96ec-970797e593df', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', '64ef7564-6c70-41ad-b41a-fdbc613f36d9', '', 'ebe0c88a-bf48-4165-bcd5-db1d34017711')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('814f5648-ab26-4b62-99cf-3787032e1fcc', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '6f8aeb25-965f-4f68-a9b8-68e821e98117', '35849f0f-d078-4daf-9ac0-60228b8f31cf', '7d10936c-896c-49b2-857e-d97e88fa3807')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f7b40d49-a82c-42d1-9ab8-2fe29c94a358', 'activityOfferingsCreated', '7d10936c-896c-49b2-857e-d97e88fa3807', '1', 'd77c0a80-2e01-4094-9e0e-302b4ac976f1')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('80129e22-f2ed-4abe-9497-9d64c196a645', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'CLUID-HIST429-200801000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST429G CO', '.', null, 'adc9f5f3-9952-41bb-8df9-9389d9b608e7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7e348535-fcf4-4afe-9457-09c5be2268be', 'kuali.attribute.wait.list.indicator', 'adc9f5f3-9952-41bb-8df9-9389d9b608e7', 1, 'd89c4be0-f2b3-4ce2-ad0e-3759ec5d798c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('21fb8c9b-f1cb-4077-ae71-15c87cbc21e0', 'kuali.attribute.final.exam.use.matrix', 'adc9f5f3-9952-41bb-8df9-9389d9b608e7', 1, '9c33e339-97a1-4151-8c37-9f0380dec464')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e98b3389-c989-4860-beb1-3f19144cf03b', 'kuali.attribute.course.evaluation.indicator', 'adc9f5f3-9952-41bb-8df9-9389d9b608e7', 0, '6cc06cc8-544f-47ea-a5d9-350ceef19ff1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('96f30a0a-c93d-4c4a-8465-fbedc5b1b673', 'kuali.attribute.wait.list.type.key', 'adc9f5f3-9952-41bb-8df9-9389d9b608e7', null, '50639da1-cdba-44ff-a203-95947456ca02')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f5e00450-935c-4e69-94d5-5dc1ec4879fe', 'kuali.attribute.funding.source', 'adc9f5f3-9952-41bb-8df9-9389d9b608e7', '', 'db1241fb-fd82-4628-a6f3-9ee0516a958a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bb863085-68da-465b-9e5a-580d5a984243', 'kuali.attribute.wait.list.level.type.key', 'adc9f5f3-9952-41bb-8df9-9389d9b608e7', 'AO', '7a94a8e0-e7a9-44c0-9d19-9e0e23b796d4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ab6ea046-4786-4f3b-8bf7-149debb12a81', 'kuali.attribute.course.number.internal.suffix', 'adc9f5f3-9952-41bb-8df9-9389d9b608e7', 'A', 'ed746dc6-6ee5-4436-b843-cdec905f00ec')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cdf2218d-901a-475f-973b-3e5937e2e680', 'kuali.attribute.final.exam.driver', 'adc9f5f3-9952-41bb-8df9-9389d9b608e7', 'kuali.lu.exam.driver.ActivityOffering', 'ed98b774-fee5-4e5c-8646-1b36b918c1f7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b60bea6a-cc61-4572-b2c2-e34f65aa7543', 'kuali.attribute.where.fees.attached.flag', 'adc9f5f3-9952-41bb-8df9-9389d9b608e7', 0, '957dc255-6220-4c1c-8ed2-3116ba25c9dd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8bd98338-86b4-40e1-962d-ea39aa98b871', 'kuali.attribute.final.exam.indicator', 'adc9f5f3-9952-41bb-8df9-9389d9b608e7', 'STANDARD', '04e2b3f4-6887-4216-9bf9-5efdd7b9cfb6')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('b087792d-f2b0-47a7-8e53-ade3acf806c3', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'HIST429G', 'HIST', '', 'Special Topics in History', 'adc9f5f3-9952-41bb-8df9-9389d9b608e7', '', '', 'kuali.lui.identifier.state.active', 'G', 'kuali.lui.identifier.type.official', '', '82a032ea-26e4-40d9-98ce-43dfa14b26e9')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('b1b71b51-0baa-47c9-baf8-e7cd5d3ce2ec', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'adc9f5f3-9952-41bb-8df9-9389d9b608e7', '', 'kuali.lu.code.honorsOffering', '', 'bf5346f3-4c63-4e3e-9de4-14b7e66d1d57')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('adc9f5f3-9952-41bb-8df9-9389d9b608e7', '3213375036', 'b894d971-8711-4802-8fdc-a1eb90575aa9')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('adc9f5f3-9952-41bb-8df9-9389d9b608e7', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('adc9f5f3-9952-41bb-8df9-9389d9b608e7', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('adc9f5f3-9952-41bb-8df9-9389d9b608e7', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('adc9f5f3-9952-41bb-8df9-9389d9b608e7', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('adc9f5f3-9952-41bb-8df9-9389d9b608e7', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('2f25d51d-204d-48bb-a721-33673758ddfb', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '2acda501-2afd-4b9e-b08f-76d97c038024', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'c7ac64c5-4e90-45e5-abb4-609b074008e8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('290e8726-bcba-4d9e-b0ba-a4006fcde4fc', 'kuali.attribute.grade.roster.level.type.key', 'c7ac64c5-4e90-45e5-abb4-609b074008e8', 'kuali.lui.type.activity.offering.lecture', '7dd1b3b1-16ca-4ad0-a440-2c2c9bf6f7af')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f9659094-e4d5-4ae2-93dd-3fbdabe094c4', 'kuali.attribute.final.exam.level.type', 'c7ac64c5-4e90-45e5-abb4-609b074008e8', 'kuali.lu.type.activity.Lecture', '478d4b80-278e-4642-a703-eba66b8eafe6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('914ff9b7-725a-492b-aa44-8aec21da9696', 'regCodePrefix', 'c7ac64c5-4e90-45e5-abb4-609b074008e8', '1', 'd53c093e-2c60-4220-81f4-228929119c3d')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('8bb9df4f-6488-452c-a407-a64731ac24a0', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', 'c7ac64c5-4e90-45e5-abb4-609b074008e8', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'e95459bf-d448-4c6a-b595-0e176cf9ea71')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('93b7bcc6-14cf-4af4-8523-f4a597d80281', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:34.634', '', 'HIST429G-CO-FO', 'adc9f5f3-9952-41bb-8df9-9389d9b608e7', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST429G-CO-FO', 'c7ac64c5-4e90-45e5-abb4-609b074008e8', 'ef932551-3d25-4e2c-a765-8ef5ac8380cb')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('c7ac64c5-4e90-45e5-abb4-609b074008e8', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('3699ef8d-2c5b-4d9e-be49-d970f4189e34', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'b924f9c0-90f0-4eb0-a19e-ca064dc891e4', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, 'ff80d76d-40dc-4087-8736-c10ec3c16a8b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('95e0cee4-18eb-4226-a8a3-dd8d99283952', 'kuali.attribute.nonstd.ts.indicator', 'ff80d76d-40dc-4087-8736-c10ec3c16a8b', 0, 'e4b8a969-8e1f-4885-960f-5dab5d6df4af')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('93c82e09-decd-4f93-90d3-81278850ab37', 'kuali.attribute.course.evaluation.indicator', 'ff80d76d-40dc-4087-8736-c10ec3c16a8b', 0, 'a4f2b2fd-d6d1-47cb-98a0-93333d1de546')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4960f8f5-b4b1-44c5-a89e-f1d65e874f23', 'kuali.attribute.max.enrollment.is.estimate', 'ff80d76d-40dc-4087-8736-c10ec3c16a8b', 0, '0f54116c-776b-4692-8049-41fa9ecd1458')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('b10a5207-15d5-4da3-a7f5-3cd31d239b0c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', 'ff80d76d-40dc-4087-8736-c10ec3c16a8b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '2b16ebee-d471-4951-909f-1b6fd996fe93')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('ce500d39-8254-4ad5-9cb3-21d06535fcbd', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'ff80d76d-40dc-4087-8736-c10ec3c16a8b', '', 'kuali.lu.code.honorsOffering', 0, '1fe914ab-2fa7-405e-90f1-864df6dbfe31')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('0c9cc9ce-4762-441c-8ea9-f1fdbefb2822', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:34.716', '', 'ff80d76d-40dc-4087-8736-c10ec3c16a8b', 'L.REALD', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'bc95edc5-fd02-40fc-b88f-4de117c72e7e')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ae1c66be-fae5-4bda-bc55-0c97235e872a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:34.716', '', 'HIST429G-FO-AO', 'c7ac64c5-4e90-45e5-abb4-609b074008e8', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST429G-FO-AO', 'ff80d76d-40dc-4087-8736-c10ec3c16a8b', '8c7b2490-30b9-48ef-a7da-763229656643')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('1733876a-dea8-4d75-86f0-dd649500a617', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'ccfc0f7a-8eb6-48b5-b8e8-486414d84695')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('2ca7e1c1-f254-4f83-8959-a39361d5747b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A Schedule Request for HIST429G section 0101', 'Schedule request for HIST429G - A', 'A Schedule Request for HIST429G section 0101', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', 'd2c71028-c2dc-443d-b51d-9426441b0111', 'ccfc0f7a-8eb6-48b5-b8e8-486414d84695', '3d692f8b-6a2b-4414-9257-e1b6f6cd935d')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('e7ad64f6-21a3-4af3-bb0f-e587f375b2b0', 0, '3d692f8b-6a2b-4414-9257-e1b6f6cd935d', 'bf4d63c7-6a5b-43bd-9b8b-080fde2b036a')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('975b0169-4863-4946-92f8-3488526ff3c5', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Athlete Seatpool', '1c2e7786-a2ab-4778-8e81-ea540c60b9b2', 1, 2, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'fd68d4d5-66ae-49d4-bae5-f79c09586389')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('ccfc0f7a-8eb6-48b5-b8e8-486414d84695', 'ff80d76d-40dc-4087-8736-c10ec3c16a8b')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('bf4d63c7-6a5b-43bd-9b8b-080fde2b036a', 'ee115d93-579f-4c14-b3b1-917b820d14e1')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('bf4d63c7-6a5b-43bd-9b8b-080fde2b036a', 'bde3fd74-77ee-4de2-9d55-7b4b1fb0f3ce')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('bf4d63c7-6a5b-43bd-9b8b-080fde2b036a', 'F08200F107AC315CE040440A9B9A3EDC')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('7fef1888-aeb9-4811-a150-95708d7da1fd', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Junior Seatpool', '4bae9f12-5901-4db9-b4a8-4dcc20781db5', 2, 4, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'a6badcc6-21ee-4f20-821a-51a85d80d328')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='975b0169-4863-4946-92f8-3488526ff3c5', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='ff80d76d-40dc-4087-8736-c10ec3c16a8b', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Athlete Seatpool', POPULATION_ID='1c2e7786-a2ab-4778-8e81-ea540c60b9b2', PROCESSING_PRIORITY=1, SEAT_LIMIT=2, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='fd68d4d5-66ae-49d4-bae5-f79c09586389' and VER_NBR=1
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('c3e3945b-6db0-4369-9da5-6bf70f23adbf', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '0b195d50-e8f5-4f78-bc33-b992d9d1a4ce')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('2cde400a-59cc-48ce-ab1f-14042379fe09', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'c7ac64c5-4e90-45e5-abb4-609b074008e8', 'CL 1', 'CL 1', '2aa88b17-6d96-4990-a9ee-cc84f2a44216')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('7357b60a-52c8-4dbb-8bbd-e83c3e882801', 'kuali.lui.type.activity.offering.lecture', '2aa88b17-6d96-4990-a9ee-cc84f2a44216', '98673667-8ccd-4efe-b5af-e8130896adc0')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='7fef1888-aeb9-4811-a150-95708d7da1fd', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='ff80d76d-40dc-4087-8736-c10ec3c16a8b', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Junior Seatpool', POPULATION_ID='4bae9f12-5901-4db9-b4a8-4dcc20781db5', PROCESSING_PRIORITY=2, SEAT_LIMIT=4, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='a6badcc6-21ee-4f20-821a-51a85d80d328' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('0b195d50-e8f5-4f78-bc33-b992d9d1a4ce', 'ff80d76d-40dc-4087-8736-c10ec3c16a8b')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('0b195d50-e8f5-4f78-bc33-b992d9d1a4ce', 'c7ac64c5-4e90-45e5-abb4-609b074008e8')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('98673667-8ccd-4efe-b5af-e8130896adc0', 'ff80d76d-40dc-4087-8736-c10ec3c16a8b')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='2aa88b17-6d96-4990-a9ee-cc84f2a44216' where ID='98673667-8ccd-4efe-b5af-e8130896adc0'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('0d6ea730-49c3-4530-821d-e5f683e0123d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '2acda501-2afd-4b9e-b08f-76d97c038024', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '8c7a5ca5-7c55-4221-9898-3e478368b141')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bcb9ca91-b6be-4621-8f95-1f67507a638f', 'kuali.attribute.registration.group.aocluster.id', '8c7a5ca5-7c55-4221-9898-3e478368b141', '2aa88b17-6d96-4990-a9ee-cc84f2a44216', '6fca0708-4b83-4807-a753-c975e0a06b1b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6d889b94-24e9-4afb-8021-daadb601c231', 'kuali.attribute.registration.group.is.generated', '8c7a5ca5-7c55-4221-9898-3e478368b141', 1, '3dd51508-c044-4a45-8f18-86c13531d86c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('89e24dad-f31e-4cae-9530-50bea38b3cd4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', '8c7a5ca5-7c55-4221-9898-3e478368b141', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c32fb2b4-89ac-4f98-8e72-79e3a88e0974')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('7f87fc9f-67ee-4efe-bf37-e5edca9b77f0', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:35.265', '', 'HIST429G-FO-RG', 'c7ac64c5-4e90-45e5-abb4-609b074008e8', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST429G-FO-RG', '8c7a5ca5-7c55-4221-9898-3e478368b141', 'b4733c9d-58ae-476e-abb6-1cf928ae6e7e')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('50a70351-f6fd-47ef-9ee6-e1c67caa6fa9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:35.266', '', 'HIST429G-RG-AO', '8c7a5ca5-7c55-4221-9898-3e478368b141', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST429G-RG-AO', 'ff80d76d-40dc-4087-8736-c10ec3c16a8b', '8e4eea3a-0281-4863-ae0d-5834768ff0c5')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('670e9477-65ce-4a1f-838d-62bcbedecc36', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'CLUID-WMST698-199008000000', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Advanced worik in selected topics in Women''s Studies.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'WMST698R CO', 'Advanced worik in selected topics in Women''s Studies.', null, 'bc91bc17-4bac-4f65-8158-cbc1bf95ef23')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3c0ecff1-846b-48e5-8140-4a191ccc1f1d', 'kuali.attribute.course.evaluation.indicator', 'bc91bc17-4bac-4f65-8158-cbc1bf95ef23', 0, 'cee5bdd5-4d37-4dbd-b6e9-72e46ad6d6c3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b8c89c5a-d297-48fe-8a2d-5cddd181863f', 'kuali.attribute.where.fees.attached.flag', 'bc91bc17-4bac-4f65-8158-cbc1bf95ef23', 0, 'cf241e54-53e5-4d91-8292-357ead365c32')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('02a2f034-0910-47ef-8693-4c13b8abc87c', 'kuali.attribute.funding.source', 'bc91bc17-4bac-4f65-8158-cbc1bf95ef23', '', '985cedf8-70b0-48a9-b01a-48d2d5ecaa88')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f1750a8f-d486-4e88-a821-881373bb766b', 'kuali.attribute.final.exam.use.matrix', 'bc91bc17-4bac-4f65-8158-cbc1bf95ef23', 1, '2ab8d151-317f-4bad-b3a6-6af5fe94b970')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('aa513413-3110-4f13-8383-ef64f20cfa51', 'kuali.attribute.wait.list.level.type.key', 'bc91bc17-4bac-4f65-8158-cbc1bf95ef23', 'AO', '81fbe2c1-6248-4970-a136-2d492d8ccb44')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3a7a51e9-ed3b-472a-b8b2-dff2dc9a3b5b', 'kuali.attribute.course.number.internal.suffix', 'bc91bc17-4bac-4f65-8158-cbc1bf95ef23', 'A', '6cac6b9e-89fe-420c-b5a4-e889e43bdb08')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('896ebbe0-46ed-4280-80c0-06f776854f6a', 'kuali.attribute.wait.list.type.key', 'bc91bc17-4bac-4f65-8158-cbc1bf95ef23', null, 'fb3fe13e-241f-4863-80e1-e2841800d484')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('58d65133-2970-47e0-9b34-621c73447844', 'kuali.attribute.final.exam.driver', 'bc91bc17-4bac-4f65-8158-cbc1bf95ef23', 'kuali.lu.exam.driver.ActivityOffering', 'ab8fd798-4c52-4b82-abc0-7cbb5b6080f0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6bcf8f4c-2efd-4b87-a014-d9dd3cfd654b', 'kuali.attribute.final.exam.indicator', 'bc91bc17-4bac-4f65-8158-cbc1bf95ef23', 'STANDARD', '233ef013-aeae-4b27-a620-037ed5b7ddb2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('984962bb-7c55-478b-84e9-1253f47feb68', 'kuali.attribute.wait.list.indicator', 'bc91bc17-4bac-4f65-8158-cbc1bf95ef23', 1, 'bd28d9ff-701c-42d2-805d-0d671f5b12cb')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('80c5ab5a-b819-47d8-b64c-a561c0588922', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'WMST698R', 'WMST', '', 'Special Topics in Women''s Studies', 'bc91bc17-4bac-4f65-8158-cbc1bf95ef23', '', '', 'kuali.lui.identifier.state.active', 'R', 'kuali.lui.identifier.type.official', '', '8a924191-9f71-4957-acb9-5c3a6030c5b2')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('8393a34c-98e1-48a2-821f-ce746408e28d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'bc91bc17-4bac-4f65-8158-cbc1bf95ef23', '', 'kuali.lu.code.honorsOffering', '', '816d14af-ad6d-48e3-bf7a-98e2be1ec9b7')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('bc91bc17-4bac-4f65-8158-cbc1bf95ef23', '4014660630', '27814697-016c-4a99-864b-b0d559859e8b')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('bc91bc17-4bac-4f65-8158-cbc1bf95ef23', '4014660630')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('bc91bc17-4bac-4f65-8158-cbc1bf95ef23', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('bc91bc17-4bac-4f65-8158-cbc1bf95ef23', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('bc91bc17-4bac-4f65-8158-cbc1bf95ef23', 'kuali.creditType.credit.degree.1.0,1.5,2.0,2.5,3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ce3d6c6b-85c5-4d75-9d32-019006062621', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'c0e2e9e4-ef2c-40c1-8a69-6b8a268207c2', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'f10e06ae-60b5-4dca-93b8-0411389b718c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7d96e004-520d-4763-85e8-c91b394ef371', 'regCodePrefix', 'f10e06ae-60b5-4dca-93b8-0411389b718c', '1', '1d558abf-ffeb-4e03-8eb4-1afb3e8abdb9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2c0b8871-9d0b-45a6-9bff-90bac97a43c2', 'kuali.attribute.grade.roster.level.type.key', 'f10e06ae-60b5-4dca-93b8-0411389b718c', 'kuali.lui.type.activity.offering.lecture', 'bb4ab6ae-975d-41ef-b9af-809880852917')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7191f734-bd0e-418d-b75e-ae9289954a78', 'kuali.attribute.final.exam.level.type', 'f10e06ae-60b5-4dca-93b8-0411389b718c', 'kuali.lu.type.activity.Lecture', '7055e96a-f76b-4561-af86-4a3433eec4e5')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('0b11bfa6-9b5f-4fed-b9d8-9f1d830b49aa', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', 'f10e06ae-60b5-4dca-93b8-0411389b718c', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a22d2919-51a3-46fc-be71-7a8c2c710b62')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('41a7a42d-9d50-4d95-84be-01e4f94b7764', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:38.32', '', 'WMST698R-CO-FO', 'bc91bc17-4bac-4f65-8158-cbc1bf95ef23', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'WMST698R-CO-FO', 'f10e06ae-60b5-4dca-93b8-0411389b718c', 'f6dfe416-da8b-404c-bb2f-1b856c9cae3f')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('f10e06ae-60b5-4dca-93b8-0411389b718c', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('4ba5fd74-c685-465e-8842-3af7a1d5077a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '23b9ac95-4555-4d33-a802-97a6206bcd83', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 15, 15, 'Lecture', 'Courses with lecture', null, '22376529-aa8c-4803-adff-aa274ac3aa26')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0accf775-2352-44cd-b26b-6f6c0377e39e', 'kuali.attribute.course.evaluation.indicator', '22376529-aa8c-4803-adff-aa274ac3aa26', 0, 'a18fadab-a6e4-42cb-afea-f945751171e7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('88f9db29-e86d-4b01-a928-bf1b0bd50168', 'kuali.attribute.nonstd.ts.indicator', '22376529-aa8c-4803-adff-aa274ac3aa26', 0, 'caf819e2-8f97-4255-8478-8c82020afb05')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('43d611d0-ae21-4e6d-9411-4050696b0622', 'kuali.attribute.max.enrollment.is.estimate', '22376529-aa8c-4803-adff-aa274ac3aa26', 0, 'dffdd5bd-f5fc-4091-ba53-78d7ef2b4da7')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('62599949-3ffe-457c-84fe-76b880f1c757', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '22376529-aa8c-4803-adff-aa274ac3aa26', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'e8ee6e0c-2332-4fab-ba05-b2469f29f3f3')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('ebbdd549-c2ba-4f21-9e9c-af6fce84bb5e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '22376529-aa8c-4803-adff-aa274ac3aa26', '', 'kuali.lu.code.honorsOffering', 0, '4afcafb9-0cd4-441f-86da-16f6cc71d169')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('1a7581c5-5c71-44cb-8a31-4e6202aa8892', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:38.419', '', '22376529-aa8c-4803-adff-aa274ac3aa26', 'B.LOUISG', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '426f8637-f0ca-4a4d-9d44-43663decfcb0')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f0ac0968-2b13-479d-beb2-2891139932a5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:38.42', '', 'WMST698R-FO-AO', 'f10e06ae-60b5-4dca-93b8-0411389b718c', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'WMST698R-FO-AO', '22376529-aa8c-4803-adff-aa274ac3aa26', '8257a85c-e1c5-4797-acb7-afce66f79fa1')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('3b6e9054-4250-477d-941c-075044275749', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '56f5677d-7751-418d-8864-bdc9d1f79cb2')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('888f0257-befb-42d7-be93-3cfa7ff4996b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A Schedule Request for WMST698R section HY11', 'Schedule request for WMST698R - A', 'A Schedule Request for WMST698R section HY11', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', 'a689f63c-bbb8-4117-995f-f1e932fc42ff', '56f5677d-7751-418d-8864-bdc9d1f79cb2', 'dd888d26-58ee-4cff-86c2-2439c3fe19d4')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('6e108913-ce4e-4b79-a530-b8375353fbf4', 0, 'dd888d26-58ee-4cff-86c2-2439c3fe19d4', 'd40fa895-a5b7-4f4b-a6e3-5a2207bf836c')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('5a9f3c72-cf3a-4f70-bd81-3c8bbcfda3e1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '8ab2b185-6e3b-4b14-b0df-652e7049c1e0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('b6fe9147-89b2-4fe0-8223-7c55872ed6d9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '62008487-a82a-4149-8660-3a1579a662f6', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 15, 15, 'Lecture', 'Courses with lecture', null, '290d41b1-eae8-4bac-9837-73f360e9dcbc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5644ba93-176b-47f3-b1dd-4af394e04880', 'kuali.attribute.nonstd.ts.indicator', '290d41b1-eae8-4bac-9837-73f360e9dcbc', 0, '3db5ea7b-8eed-4a8e-8e69-4baecba75c59')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('46858ad9-9cd7-4bc7-a2e8-0d99adda7496', 'kuali.attribute.max.enrollment.is.estimate', '290d41b1-eae8-4bac-9837-73f360e9dcbc', 0, 'b4bb5fa5-cf7e-4789-9e08-7d8d905e0616')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('72ea4695-1bb6-49a5-b0c7-7aa1385db962', 'kuali.attribute.course.evaluation.indicator', '290d41b1-eae8-4bac-9837-73f360e9dcbc', 0, '25eb5f7d-2b62-4fbe-822c-d10cf0c84796')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('97d40d2c-7d31-460d-8193-9b804ffd3b08', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'B', '', '', '', '290d41b1-eae8-4bac-9837-73f360e9dcbc', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'd97561ab-8235-46c0-ab68-9bb6033a69a1')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('6369cfdf-e4aa-4a59-ad31-44191efb9ca2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '290d41b1-eae8-4bac-9837-73f360e9dcbc', '', 'kuali.lu.code.honorsOffering', 0, 'b1149c8a-b2b2-4e46-b0be-fe32306471ea')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('175eb951-149b-4f5e-b1e9-781e61bb3378', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:38.621', '', '290d41b1-eae8-4bac-9837-73f360e9dcbc', 'H.DONALDC', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '8fc3fc65-d130-42bd-8590-2caa963d20e8')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('876a8d77-a2ec-4f81-83c1-a523750c7764', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:38.621', '', 'WMST698R-FO-AO', 'f10e06ae-60b5-4dca-93b8-0411389b718c', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'WMST698R-FO-AO', '290d41b1-eae8-4bac-9837-73f360e9dcbc', '0619b6e6-b37b-4953-aee7-13a25a2c8442')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('56f5677d-7751-418d-8864-bdc9d1f79cb2', '22376529-aa8c-4803-adff-aa274ac3aa26')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('d40fa895-a5b7-4f4b-a6e3-5a2207bf836c', '9a171e1e-dde4-4095-8fc9-b8fde0da485e')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('d40fa895-a5b7-4f4b-a6e3-5a2207bf836c', '3228d416-6a16-4c95-9fa6-ab16a1599b01')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('d40fa895-a5b7-4f4b-a6e3-5a2207bf836c', 'F08200F107A7315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('8ab2b185-6e3b-4b14-b0df-652e7049c1e0', '22376529-aa8c-4803-adff-aa274ac3aa26')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('8ab2b185-6e3b-4b14-b0df-652e7049c1e0', 'f10e06ae-60b5-4dca-93b8-0411389b718c')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('3b48451f-9d22-4762-a19c-2147016e4695', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '339db856-a290-4d44-bb26-e2f18484bcf1')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('53bf18cb-6ec3-4288-a576-0e9b9327e4c3', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A Schedule Request for WMST698R section HY11', 'Schedule request for WMST698R - B', 'A Schedule Request for WMST698R section HY11', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '6cb01cdc-7a72-40b6-ab14-797afd4fe61b', '339db856-a290-4d44-bb26-e2f18484bcf1', '9872b32a-6542-41ca-bb77-11442d867fbd')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('a8571808-715d-419a-80a6-ca7d56a75113', 0, '9872b32a-6542-41ca-bb77-11442d867fbd', 'b99033fb-921d-461a-8050-0af637ddd201')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('5eda32ab-192b-4773-9e89-8eb49805919e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'fdb2c466-cac8-4fb5-b16f-53e0a95cd7e7')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('7c5e84db-ede9-4eb8-9b9e-0b0a256fa32e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'f10e06ae-60b5-4dca-93b8-0411389b718c', 'CL 1', 'CL 1', '68f61039-4211-4952-a7ba-29019ae99916')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('cb35bc41-e284-48c2-92cb-746793641b92', 'kuali.lui.type.activity.offering.lecture', '68f61039-4211-4952-a7ba-29019ae99916', '4ca479f2-e003-4d40-a402-4a6456acc371')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('339db856-a290-4d44-bb26-e2f18484bcf1', '290d41b1-eae8-4bac-9837-73f360e9dcbc')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('b99033fb-921d-461a-8050-0af637ddd201', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('b99033fb-921d-461a-8050-0af637ddd201', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('b99033fb-921d-461a-8050-0af637ddd201', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('fdb2c466-cac8-4fb5-b16f-53e0a95cd7e7', '290d41b1-eae8-4bac-9837-73f360e9dcbc')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('fdb2c466-cac8-4fb5-b16f-53e0a95cd7e7', 'f10e06ae-60b5-4dca-93b8-0411389b718c')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('4ca479f2-e003-4d40-a402-4a6456acc371', '22376529-aa8c-4803-adff-aa274ac3aa26')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('4ca479f2-e003-4d40-a402-4a6456acc371', '290d41b1-eae8-4bac-9837-73f360e9dcbc')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='68f61039-4211-4952-a7ba-29019ae99916' where ID='4ca479f2-e003-4d40-a402-4a6456acc371'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('1bc6dc96-9a1c-460c-bef8-dedb649c4378', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'c0e2e9e4-ef2c-40c1-8a69-6b8a268207c2', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'cddffb34-67d8-4218-a656-699d39c32d09')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e995c26c-bfb0-40b3-9bd1-7bb7ccb968e3', 'kuali.attribute.registration.group.aocluster.id', 'cddffb34-67d8-4218-a656-699d39c32d09', '68f61039-4211-4952-a7ba-29019ae99916', '42c37a0b-5c4a-498a-8045-831373ac08fa')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6d31dd4d-50b1-46d5-8226-cf36fe951ac3', 'kuali.attribute.registration.group.is.generated', 'cddffb34-67d8-4218-a656-699d39c32d09', 1, 'ca444168-c94c-413f-a30d-64876caea7b0')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('1ac6b4c2-1001-449c-aa53-7ad7c322637f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'cddffb34-67d8-4218-a656-699d39c32d09', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a089f44f-07a3-43eb-8b35-d5f2af0b0969')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('006b05c3-96af-451c-a422-7faa31564320', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:39.24', '', 'WMST698R-FO-RG', 'f10e06ae-60b5-4dca-93b8-0411389b718c', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'WMST698R-FO-RG', 'cddffb34-67d8-4218-a656-699d39c32d09', '8adbfc36-6643-46d5-a274-dc19bbb26b68')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('829ac994-2839-49eb-b05d-88336948f971', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:39.241', '', 'WMST698R-RG-AO', 'cddffb34-67d8-4218-a656-699d39c32d09', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'WMST698R-RG-AO', '22376529-aa8c-4803-adff-aa274ac3aa26', '6b4fbc68-94cc-464e-92b9-b1fb9e5c38b6')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('3076e55a-5032-4554-b4d7-3c4b31d8d616', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'c0e2e9e4-ef2c-40c1-8a69-6b8a268207c2', '', '', '1002', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1002', '1002', '', 'c8612f3e-9ec9-45e4-8989-25c02177bd62')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f65c5ded-8c49-469d-ab51-434f1395638d', 'kuali.attribute.registration.group.aocluster.id', 'c8612f3e-9ec9-45e4-8989-25c02177bd62', '68f61039-4211-4952-a7ba-29019ae99916', '119dd420-c52b-48ce-af2e-4591f7ea5fef')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7c59c256-c9ba-47c6-97f3-2d8447ac7bca', 'kuali.attribute.registration.group.is.generated', 'c8612f3e-9ec9-45e4-8989-25c02177bd62', 1, '343962f7-669e-4e43-8ad7-1b911e687498')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('70ef1330-93e4-4a90-8277-da2b484ed2f9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'c8612f3e-9ec9-45e4-8989-25c02177bd62', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'd86d74cc-5dbe-4664-9609-db06a23b28fc')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('03547683-9d70-449f-9687-776e4b4ab3be', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:39.344', '', 'WMST698R-FO-RG', 'f10e06ae-60b5-4dca-93b8-0411389b718c', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'WMST698R-FO-RG', 'c8612f3e-9ec9-45e4-8989-25c02177bd62', 'aaf44be2-c0e9-4afd-8e9e-90af4ca6ff5d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('05ceea25-62d7-4a08-99c0-27bcb1777ab1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:39.344', '', 'WMST698R-RG-AO', 'c8612f3e-9ec9-45e4-8989-25c02177bd62', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'WMST698R-RG-AO', '290d41b1-eae8-4bac-9837-73f360e9dcbc', '648c40c2-bd20-4442-99e2-b97a9c2eb679')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('473ea753-7fc5-48cf-8827-ec9a277d5983', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '73acba95-b7ab-494c-a726-6accd2de5257', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Basic (chemical) research conducted under the supervision of a faculty member.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'CHEM399B CO', 'Basic (chemical) research conducted under the supervision of a faculty member.', null, 'ab2ef727-9a04-42ef-94fb-22bc2b1581aa')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e87124e7-8915-4ec1-9c5c-08863736bb1b', 'kuali.attribute.wait.list.level.type.key', 'ab2ef727-9a04-42ef-94fb-22bc2b1581aa', 'AO', 'ed3bee45-d186-49cb-b2aa-7294ad1308f6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bfd045e3-8c25-4394-8990-ee62fe5376db', 'kuali.attribute.wait.list.type.key', 'ab2ef727-9a04-42ef-94fb-22bc2b1581aa', null, '665e9806-47fe-429f-882a-1bb6b13fc10e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3a710f6f-b1c4-4c1f-af8f-aeabb96ac6c6', 'kuali.attribute.final.exam.driver', 'ab2ef727-9a04-42ef-94fb-22bc2b1581aa', 'kuali.lu.exam.driver.ActivityOffering', '17d275b8-fe05-41ea-993f-0a0ceb0f6207')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0f72b409-c29a-4713-a917-fc4f266b5b32', 'kuali.attribute.final.exam.indicator', 'ab2ef727-9a04-42ef-94fb-22bc2b1581aa', 'STANDARD', 'c114152e-78db-4fe7-9f68-ff9b07443ac6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('74593db9-8658-452c-a9b8-c352b0913316', 'kuali.attribute.where.fees.attached.flag', 'ab2ef727-9a04-42ef-94fb-22bc2b1581aa', 0, 'ced02e7b-d6ce-4883-833c-6910c1894114')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('23a8b1cc-49a0-4251-b8db-b4ec520bfb6a', 'kuali.attribute.course.number.internal.suffix', 'ab2ef727-9a04-42ef-94fb-22bc2b1581aa', 'A', 'd9c2a37c-143f-4f51-8c96-b5c7c1aeb1a7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cf4f3153-d5ea-425c-8d75-128a1505e7b6', 'kuali.attribute.course.evaluation.indicator', 'ab2ef727-9a04-42ef-94fb-22bc2b1581aa', 0, '365c9f4b-c5c2-4324-a360-c44df85304c6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('39a1daf7-b778-40d7-8729-6af355d80955', 'kuali.attribute.wait.list.indicator', 'ab2ef727-9a04-42ef-94fb-22bc2b1581aa', 1, 'ffb9e76c-77f2-45da-9c88-3ee3ba1b10fc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b7723195-10f7-4019-8e74-824c4066849f', 'kuali.attribute.funding.source', 'ab2ef727-9a04-42ef-94fb-22bc2b1581aa', '', '3f320cf2-ac8c-447c-90a2-ef86b0b762d7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d96f871d-d4f9-4800-bd0c-aaa2114f47d7', 'kuali.attribute.final.exam.use.matrix', 'ab2ef727-9a04-42ef-94fb-22bc2b1581aa', 1, '14265295-8319-446d-84f9-c67518524b55')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6632b1f3-de11-479e-92c2-25106462cff7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'CHEM399B', 'CHEM', '', 'Introduction to Chemical Research', 'ab2ef727-9a04-42ef-94fb-22bc2b1581aa', '', '', 'kuali.lui.identifier.state.active', 'B', 'kuali.lui.identifier.type.official', '', '66b61c1c-1cc4-4be8-bc19-179c5817b2ee')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('37219ded-013b-48d3-a1a4-6ec5d31306be', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'ab2ef727-9a04-42ef-94fb-22bc2b1581aa', '', 'kuali.lu.code.honorsOffering', '', '169fef84-64cf-4dea-a9cd-b1ca3884ff68')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('ab2ef727-9a04-42ef-94fb-22bc2b1581aa', '4284516367', '2aab92e3-69c1-461e-82f0-f0265e5c6ebd')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('ab2ef727-9a04-42ef-94fb-22bc2b1581aa', '4284516367')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ab2ef727-9a04-42ef-94fb-22bc2b1581aa', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ab2ef727-9a04-42ef-94fb-22bc2b1581aa', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ab2ef727-9a04-42ef-94fb-22bc2b1581aa', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ab2ef727-9a04-42ef-94fb-22bc2b1581aa', 'kuali.creditType.credit.degree.1.0,1.5,2.0,2.5,3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('9f5ed5c0-4f3a-4c5b-a7a4-10e8219b5bf4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '0ae12d77-775c-499c-ade2-a4e2b60984a9', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'e470c6e5-17e7-444f-b67c-291c63322034')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d664c9e1-9dea-4138-8c39-01cb193c390a', 'kuali.attribute.grade.roster.level.type.key', 'e470c6e5-17e7-444f-b67c-291c63322034', 'kuali.lui.type.activity.offering.lecture', 'eb5f214c-7928-479a-b9f1-ed522b7c89df')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d2428e21-db79-4318-bcb7-5c7e4b9c244c', 'regCodePrefix', 'e470c6e5-17e7-444f-b67c-291c63322034', '1', '9efec79b-2f96-4ee9-b558-70144ad29aae')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('39b20f55-a1b0-482f-863b-93667204ef56', 'kuali.attribute.final.exam.level.type', 'e470c6e5-17e7-444f-b67c-291c63322034', 'kuali.lu.type.activity.Lecture', '2684f47d-5a9f-4771-a964-9ae63564afaa')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('efa5de53-6168-46ae-bf6d-89fe1e7295f7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', 'e470c6e5-17e7-444f-b67c-291c63322034', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '101953c7-7670-46a0-9bd2-7cbbe848fdc9')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ccac419a-4a82-4083-a616-1e142cc586d5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:45.848', '', 'CHEM399B-CO-FO', 'ab2ef727-9a04-42ef-94fb-22bc2b1581aa', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'CHEM399B-CO-FO', 'e470c6e5-17e7-444f-b67c-291c63322034', '1dd7fa9b-493b-4699-8ff0-c2998b9e759b')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('e470c6e5-17e7-444f-b67c-291c63322034', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d8330a64-174d-42fc-a793-f911cc65555c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '020b3c6c-20f2-42f0-b1aa-fe16d1530339', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 5, 5, 'Lecture', 'Courses with lecture', null, 'b9ac54ab-092f-4b32-a778-37463af533c4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a4784e7a-7321-48de-a108-626b4ab136f3', 'kuali.attribute.nonstd.ts.indicator', 'b9ac54ab-092f-4b32-a778-37463af533c4', 0, 'cd080146-9666-488e-a0d7-1d5e432ddf08')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('23de42a6-0eea-4b80-a06d-10e5c41c2947', 'kuali.attribute.max.enrollment.is.estimate', 'b9ac54ab-092f-4b32-a778-37463af533c4', 0, '1363559d-7429-4165-bcc7-9c29e31d0876')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bdce77ea-04f9-47de-9da6-b5ea3c12e30e', 'kuali.attribute.course.evaluation.indicator', 'b9ac54ab-092f-4b32-a778-37463af533c4', 0, '9f43b0ca-8087-45ae-8a3e-f3384f59f27a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('3dfb4d7a-732b-455d-a997-5471a8c6db1b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', 'b9ac54ab-092f-4b32-a778-37463af533c4', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '5e74a598-e3f0-47a1-a787-7813860e6517')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('02035a6b-5e32-4477-b6fd-22ed2fb2d35b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'b9ac54ab-092f-4b32-a778-37463af533c4', '', 'kuali.lu.code.honorsOffering', 0, 'ea74aa72-cdd5-4784-8530-82c7d7a54d8b')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('640e7d22-7ace-4c78-9b50-527488eadbe5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:45.942', '', 'b9ac54ab-092f-4b32-a778-37463af533c4', 'B.GOUGHM', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '5451fec0-2501-4a02-9ed7-90833febc09c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('4adfb7f2-ed79-49c2-bef7-bfb14b710312', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:45.942', '', 'CHEM399B-FO-AO', 'e470c6e5-17e7-444f-b67c-291c63322034', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM399B-FO-AO', 'b9ac54ab-092f-4b32-a778-37463af533c4', 'b3364192-7526-43c4-92b4-e1c982938b2c')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('87d2c7ed-f3a2-4892-9ff4-4d2b611b959f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'c28cc0ac-8b97-4ea7-95d8-0b715bb1d806')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('2e18f18d-5696-48b6-9915-89045cd1cab4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for CHEM399B - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'c28cc0ac-8b97-4ea7-95d8-0b715bb1d806', 'caf02396-5409-4738-b7b6-96bff49bd38e')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('e6aef6a6-cff1-4692-84fb-34b5cfac44b6', 1, 'caf02396-5409-4738-b7b6-96bff49bd38e', 'c8c0350c-f49e-4572-bc38-b215835b5f31')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('1a1e20f0-74a5-41cf-8bc8-76187bd4afec', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'e5f42806-261a-485c-b867-c90522fece03')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('c28cc0ac-8b97-4ea7-95d8-0b715bb1d806', 'b9ac54ab-092f-4b32-a778-37463af533c4')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('c8c0350c-f49e-4572-bc38-b215835b5f31', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('0b18bdd2-4f04-4413-9a89-695cf8108f07', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'c13aa2bf-f282-47be-8d0b-3533d6db30e7')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('47261f02-9482-4385-8cb7-a6f0254949f3', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'e470c6e5-17e7-444f-b67c-291c63322034', 'CL 1', 'CL 1', 'c5ce8768-05ab-4ac2-a202-bc76aa66c6d7')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('3d468a47-d716-4f48-869e-460a57910c9b', 'kuali.lui.type.activity.offering.lecture', 'c5ce8768-05ab-4ac2-a202-bc76aa66c6d7', 'cb1d87d3-a0fa-45b4-9900-89f88aed46ef')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='1a1e20f0-74a5-41cf-8bc8-76187bd4afec', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='b9ac54ab-092f-4b32-a778-37463af533c4', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='e5f42806-261a-485c-b867-c90522fece03' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('c13aa2bf-f282-47be-8d0b-3533d6db30e7', 'b9ac54ab-092f-4b32-a778-37463af533c4')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('c13aa2bf-f282-47be-8d0b-3533d6db30e7', 'e470c6e5-17e7-444f-b67c-291c63322034')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('cb1d87d3-a0fa-45b4-9900-89f88aed46ef', 'b9ac54ab-092f-4b32-a778-37463af533c4')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='c5ce8768-05ab-4ac2-a202-bc76aa66c6d7' where ID='cb1d87d3-a0fa-45b4-9900-89f88aed46ef'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('88bb2505-b079-4201-a458-a520df909bca', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '0ae12d77-775c-499c-ade2-a4e2b60984a9', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '50bd00a0-e166-4492-9ae8-a8849827ba67')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b24a01c5-b48e-4eeb-aedb-27ee8b11f73b', 'kuali.attribute.registration.group.aocluster.id', '50bd00a0-e166-4492-9ae8-a8849827ba67', 'c5ce8768-05ab-4ac2-a202-bc76aa66c6d7', 'ac792e6e-6fbc-4d3a-aaae-13a2892bd856')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e1a7bd71-45a4-4de6-94cb-d9b0713d18cc', 'kuali.attribute.registration.group.is.generated', '50bd00a0-e166-4492-9ae8-a8849827ba67', 1, '4f76c1fc-a101-4487-a302-4e09a0114861')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('26fb35ae-9d86-4ee2-b697-43f061e31044', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', '50bd00a0-e166-4492-9ae8-a8849827ba67', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '23a1cc80-d015-4f1a-bf67-ad5052d84a6f')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ee1516a1-5c84-4dd3-9c15-c5c95bed9575', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:46.352', '', 'CHEM399B-FO-RG', 'e470c6e5-17e7-444f-b67c-291c63322034', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM399B-FO-RG', '50bd00a0-e166-4492-9ae8-a8849827ba67', 'a96428cc-5434-4e19-8d26-f8a7ada2aabe')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('45cfb021-d292-4cd3-add5-095bba543f5e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:46.352', '', 'CHEM399B-RG-AO', '50bd00a0-e166-4492-9ae8-a8849827ba67', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM399B-RG-AO', 'b9ac54ab-092f-4b32-a778-37463af533c4', '0847d847-30c3-491c-88ca-bf85e722f6e9')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('f3b08386-89b9-4496-b922-d46b1c4ad9ea', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '303bf3d1-e960-42bf-843b-4d5bff305a7b', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Political, cultural and economic developments in 20th-century Europe.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST240 CO', 'Political, cultural and economic developments in 20th-century Europe.', null, '9419ad65-926d-4aa9-aeee-05ad81cea3ca')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('72598763-b69d-4b20-8625-7c818ae54f46', 'kuali.attribute.course.evaluation.indicator', '9419ad65-926d-4aa9-aeee-05ad81cea3ca', 0, '6a60f448-6f83-4243-b06b-d18b29071e81')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6776c2e3-c04f-4d17-9cbe-1be0664f9b80', 'kuali.attribute.final.exam.driver', '9419ad65-926d-4aa9-aeee-05ad81cea3ca', 'kuali.lu.exam.driver.ActivityOffering', 'e7bea0df-5cdb-4327-bffb-a4441f00d8f1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5657f081-d110-44f5-a5e5-c2c332299747', 'kuali.attribute.course.number.internal.suffix', '9419ad65-926d-4aa9-aeee-05ad81cea3ca', 'A', 'e0abd07e-f56a-425e-80d8-63740cc1ea7d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('52645afa-4ed7-4d79-8b2e-1c4812ccf743', 'kuali.attribute.wait.list.type.key', '9419ad65-926d-4aa9-aeee-05ad81cea3ca', null, '60a0e851-5fc7-427e-af53-4fc036a8e7d6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d43fdb7b-0d52-4387-a422-0e9767df646c', 'kuali.attribute.final.exam.use.matrix', '9419ad65-926d-4aa9-aeee-05ad81cea3ca', 1, 'c92c6950-8835-4d2b-b670-f0fb4d36ae86')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5df01caf-0de1-49c1-9225-c79625c49e85', 'kuali.attribute.wait.list.indicator', '9419ad65-926d-4aa9-aeee-05ad81cea3ca', 1, '95453619-08bf-466b-8ebe-4647b1ab7b6d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dc916d9a-8953-4a70-b9c9-e9c7ed74690d', 'kuali.attribute.where.fees.attached.flag', '9419ad65-926d-4aa9-aeee-05ad81cea3ca', 0, '25c9c922-bf41-4784-b12b-af6fad67ccbb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3bdd72ee-b15f-4f76-b96a-066a74ed6f51', 'kuali.attribute.funding.source', '9419ad65-926d-4aa9-aeee-05ad81cea3ca', '', '7c538e77-642e-4a64-ab4d-759e61006433')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6aec261d-f952-4191-b2c3-b60c04d5bcb5', 'kuali.attribute.wait.list.level.type.key', '9419ad65-926d-4aa9-aeee-05ad81cea3ca', 'AO', '622711b8-cd53-432a-966b-cb7544c01685')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cf3c7a51-b1f6-44fa-aaa7-c433b9c8ec99', 'kuali.attribute.final.exam.indicator', '9419ad65-926d-4aa9-aeee-05ad81cea3ca', 'STANDARD', 'd0361428-db42-4119-b419-0051058b4882')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('39117f7b-07be-459e-9625-ffbe5dfd364c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'HIST240', 'HIST', '', 'Europe in the Twentieth Century', '9419ad65-926d-4aa9-aeee-05ad81cea3ca', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a8674b15-62d7-41a7-9df0-238afbd25d2d')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('e7a1b118-2d98-4988-86ab-1a6c5a03d85d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '9419ad65-926d-4aa9-aeee-05ad81cea3ca', '', 'kuali.lu.code.honorsOffering', '', '28091cd3-37f5-417f-a9e9-95e6bb2cc110')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('9419ad65-926d-4aa9-aeee-05ad81cea3ca', '3213375036', 'ca7b74f8-19e1-4f96-a18e-43df5926507e')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('9419ad65-926d-4aa9-aeee-05ad81cea3ca', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('9419ad65-926d-4aa9-aeee-05ad81cea3ca', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('9419ad65-926d-4aa9-aeee-05ad81cea3ca', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('9419ad65-926d-4aa9-aeee-05ad81cea3ca', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('9419ad65-926d-4aa9-aeee-05ad81cea3ca', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('63859ecd-3123-4959-93ae-f5a35655bf54', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'e9b70810-8160-4c01-917b-6cd011536a5a', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '2af5f200-feb4-4717-8300-a148625b4b55')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e68fc301-fa99-461b-9da5-45df90121d86', 'kuali.attribute.grade.roster.level.type.key', '2af5f200-feb4-4717-8300-a148625b4b55', 'kuali.lui.type.activity.offering.lecture', 'b9464998-d700-4f89-84e2-2cd309d63acd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('afb9e55a-85ce-4937-8354-659eb533a70d', 'regCodePrefix', '2af5f200-feb4-4717-8300-a148625b4b55', '1', '656317a7-1e02-4a62-856e-e913a5967abd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4d3b2bb9-6f09-4b24-99a2-c3c40d66df76', 'kuali.attribute.final.exam.level.type', '2af5f200-feb4-4717-8300-a148625b4b55', 'kuali.lu.type.activity.Lecture', '15f76934-709e-4ab3-958a-f2c5ba242e47')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('ba9c27d8-7b2f-4f1f-8f6a-11df17222491', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '2af5f200-feb4-4717-8300-a148625b4b55', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7fc4c7ca-943b-4512-a58d-c643924665ab')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('517ab086-19fe-4dfc-934a-2cd74db8f246', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:48.912', '', 'HIST240-CO-FO', '9419ad65-926d-4aa9-aeee-05ad81cea3ca', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST240-CO-FO', '2af5f200-feb4-4717-8300-a148625b4b55', '70972cfc-8283-4653-b71a-94930ee2e034')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('2af5f200-feb4-4717-8300-a148625b4b55', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('721d07b9-f83d-445c-98b3-ab87767bf368', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '2ed3cb96-4219-4f5f-8825-b410addbfa5c', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, '5fba278a-6ac3-4c28-a454-be43ae1523c1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7a44f630-b956-4101-846a-824c58c549e3', 'kuali.attribute.nonstd.ts.indicator', '5fba278a-6ac3-4c28-a454-be43ae1523c1', 0, 'dfdcb9eb-27d9-4444-8052-f37e85ec6052')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8aaa24fd-72da-445a-a6e8-2bc8a7ec237a', 'kuali.attribute.max.enrollment.is.estimate', '5fba278a-6ac3-4c28-a454-be43ae1523c1', 0, '65701d85-8db4-43e4-afe2-a5dbadf01ed6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('848a2d81-025a-4806-85b5-d5f27596d067', 'kuali.attribute.course.evaluation.indicator', '5fba278a-6ac3-4c28-a454-be43ae1523c1', 0, 'b75e30fd-6492-4d00-b1b2-c79cdc98473c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5ae687ae-19f0-4d60-b470-efc97730ef0b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '5fba278a-6ac3-4c28-a454-be43ae1523c1', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '38cce3c0-254b-4ecc-ba10-376f31a433b8')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('e486142c-e120-494a-a14f-9e4b87a74b55', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '5fba278a-6ac3-4c28-a454-be43ae1523c1', '', 'kuali.lu.code.honorsOffering', 0, '180009f3-abea-4baf-89c4-90ebc2ce836d')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('9e66647f-f65d-4154-977d-1890ce1a7705', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:49.327', '', '5fba278a-6ac3-4c28-a454-be43ae1523c1', 'S.PHILIPW', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'bc8fb61e-7404-4dc2-a255-b79450faff0d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('d747ccfc-c8fb-4c88-a916-82b74b337f4f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:49.327', '', 'HIST240-FO-AO', '2af5f200-feb4-4717-8300-a148625b4b55', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST240-FO-AO', '5fba278a-6ac3-4c28-a454-be43ae1523c1', '21d5816e-f79e-4bc1-8cf7-9efa74a9d05c')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('916323ea-5f67-4e51-b1c5-3a5dc60e94cd', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'c5c15d10-e639-4f0c-9c06-0d72602ceaee')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('45d1a519-402d-4511-8879-1fae28a66ead', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for HIST240 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'c5c15d10-e639-4f0c-9c06-0d72602ceaee', 'ca45a169-8ed3-4e27-902e-89eefc2fc891')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('de9f6538-2046-4c06-8aaa-34af2219db2c', 0, 'ca45a169-8ed3-4e27-902e-89eefc2fc891', '6a224d9b-4c93-4d5b-b3e3-a00465079dbe')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('ddb7be2d-989a-4af0-b8e3-c12bd9605665', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '73176016-6f8d-475a-a309-88a18084d25a')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('6c011025-4950-4253-911c-464e2634db03', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '2af5f200-feb4-4717-8300-a148625b4b55', 'CL 1', 'CL 1', 'c1d9d787-6bcd-4feb-811a-d13363d279d0')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('497ae1be-c59b-410f-a75c-cc77ddad3ad5', 'kuali.lui.type.activity.offering.lecture', 'c1d9d787-6bcd-4feb-811a-d13363d279d0', 'ef59196e-d7ff-4f87-927a-91e839879e0a')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('c5c15d10-e639-4f0c-9c06-0d72602ceaee', '5fba278a-6ac3-4c28-a454-be43ae1523c1')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('6a224d9b-4c93-4d5b-b3e3-a00465079dbe', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('6a224d9b-4c93-4d5b-b3e3-a00465079dbe', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('6a224d9b-4c93-4d5b-b3e3-a00465079dbe', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('6a224d9b-4c93-4d5b-b3e3-a00465079dbe', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('73176016-6f8d-475a-a309-88a18084d25a', '5fba278a-6ac3-4c28-a454-be43ae1523c1')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('73176016-6f8d-475a-a309-88a18084d25a', '2af5f200-feb4-4717-8300-a148625b4b55')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('ef59196e-d7ff-4f87-927a-91e839879e0a', '5fba278a-6ac3-4c28-a454-be43ae1523c1')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='c1d9d787-6bcd-4feb-811a-d13363d279d0' where ID='ef59196e-d7ff-4f87-927a-91e839879e0a'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('e6ec4e7c-e35c-46c9-9b1e-4e599bc9a3d7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'e9b70810-8160-4c01-917b-6cd011536a5a', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'eea375ec-ecf3-417e-8f0e-3a21ff17c645')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('abd9fbec-f82a-4d27-b706-6ad3d1671aa7', 'kuali.attribute.registration.group.aocluster.id', 'eea375ec-ecf3-417e-8f0e-3a21ff17c645', 'c1d9d787-6bcd-4feb-811a-d13363d279d0', '300e906c-de98-42bb-ad8b-9f2b00dfef80')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('81dd7c66-dfac-4fbb-950e-0431cc094c31', 'kuali.attribute.registration.group.is.generated', 'eea375ec-ecf3-417e-8f0e-3a21ff17c645', 1, '65ef50c7-a2d1-4a3e-a47a-2fc216872acf')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7b5eca0a-292f-4c1f-b229-d92b7a9d0f1e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'eea375ec-ecf3-417e-8f0e-3a21ff17c645', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '6f9fafe4-cbb3-4a78-9dee-d47c17d1e908')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('569320ed-9350-4759-a797-b262819f1f09', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:49.732', '', 'HIST240-FO-RG', '2af5f200-feb4-4717-8300-a148625b4b55', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST240-FO-RG', 'eea375ec-ecf3-417e-8f0e-3a21ff17c645', 'c98ed7ef-d99a-49e9-8940-6ab592cbaf09')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a987b1fa-b665-4406-9c78-ca62d069e09e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:49.732', '', 'HIST240-RG-AO', 'eea375ec-ecf3-417e-8f0e-3a21ff17c645', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST240-RG-AO', '5fba278a-6ac3-4c28-a454-be43ae1523c1', '12a44a44-ec7f-4d65-8d94-3185d4911287')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('7e64106f-e891-4db3-9eeb-61f7fecca75e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '0d58db7f-de33-4ab1-8d4a-7d8d9a7fce2b', TIMESTAMP '2014-01-01 19:00:00.0', '', 'A cross-section of American folk and popular songs in their cultural contexts; artists from Bill Monroe to Robert Johnson.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL462 CO', 'A cross-section of American folk and popular songs in their cultural contexts; artists from Bill Monroe to Robert Johnson.', null, 'c41bee14-5f90-44b4-b389-0c70ea2320df')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('27145a99-bfdd-473f-9dfd-ab4c940af77d', 'kuali.attribute.where.fees.attached.flag', 'c41bee14-5f90-44b4-b389-0c70ea2320df', 0, '1226ad69-db6d-4d68-8bba-ce013ad69dba')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b44b0627-6d3f-4069-b688-c408cb65d479', 'kuali.attribute.course.number.internal.suffix', 'c41bee14-5f90-44b4-b389-0c70ea2320df', 'A', '71405921-3118-4b52-9838-cea3b879dfb5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bd4c6bed-c649-4a75-8967-20a82caf02b7', 'kuali.attribute.wait.list.indicator', 'c41bee14-5f90-44b4-b389-0c70ea2320df', 1, '67e23ca9-8ec9-4d56-844e-d5839dc0b6d4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ffab0630-a0ba-47b9-a8f7-24fc2c8b2244', 'kuali.attribute.wait.list.level.type.key', 'c41bee14-5f90-44b4-b389-0c70ea2320df', 'AO', '47ffe5c3-ce4e-4b14-bfbf-87136fe4d93a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('424f781b-8aac-4211-97cf-68e83e66ae99', 'kuali.attribute.course.evaluation.indicator', 'c41bee14-5f90-44b4-b389-0c70ea2320df', 0, 'cd4dab5c-b25c-43d3-9691-41941b74e397')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7fc646c6-915d-46e3-bbad-25727a9a2ab8', 'kuali.attribute.wait.list.type.key', 'c41bee14-5f90-44b4-b389-0c70ea2320df', null, 'e38d1cff-5ebf-42ca-8b85-81deaf9300ac')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d6e2376c-0458-45b3-baf4-ca81ec4f3eda', 'kuali.attribute.final.exam.use.matrix', 'c41bee14-5f90-44b4-b389-0c70ea2320df', 1, 'f74bc1ab-c0fb-491f-8eb4-5599e992cca6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1a6293b6-2549-4b11-a3b3-47add6de64ac', 'kuali.attribute.final.exam.indicator', 'c41bee14-5f90-44b4-b389-0c70ea2320df', 'STANDARD', '971f14df-4d49-4d00-88d5-309f3ac12a05')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e8574540-f3d0-4789-8b21-af91f9675a06', 'kuali.attribute.funding.source', 'c41bee14-5f90-44b4-b389-0c70ea2320df', '', 'bbede23b-a995-47a3-8761-d67a4bc8f04b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('06357d58-fbab-4fd0-972f-fe2b26d36653', 'kuali.attribute.final.exam.driver', 'c41bee14-5f90-44b4-b389-0c70ea2320df', 'kuali.lu.exam.driver.ActivityOffering', '6a665137-c316-40fc-8288-baa70e338659')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5a86f797-ea6d-4752-bb93-0e971121bb1a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'ENGL462', 'ENGL', '', 'Folksong and Ballad', 'c41bee14-5f90-44b4-b389-0c70ea2320df', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'bef73cab-07be-4143-ad4f-549c967666d4')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('646ea17f-1c71-43fa-8e2c-a7bb5b527e7f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'c41bee14-5f90-44b4-b389-0c70ea2320df', '', 'kuali.lu.code.honorsOffering', '', '112e0eed-45fb-4b95-926a-2d11257454d5')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('c41bee14-5f90-44b4-b389-0c70ea2320df', '2677933260', 'd2181416-75b2-4d44-969d-d46bf85cde50')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('c41bee14-5f90-44b4-b389-0c70ea2320df', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('c41bee14-5f90-44b4-b389-0c70ea2320df', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('c41bee14-5f90-44b4-b389-0c70ea2320df', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('c41bee14-5f90-44b4-b389-0c70ea2320df', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('c41bee14-5f90-44b4-b389-0c70ea2320df', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('32ca2cc8-70b4-426a-8186-b3fd756946fe', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '85d23c02-4b65-44bd-8718-3e0f409a7015', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'e8847bcf-e155-432f-a164-8732d77b09cc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bc6e2ae2-5bf7-40fc-a917-aa6a576ae8d1', 'kuali.attribute.grade.roster.level.type.key', 'e8847bcf-e155-432f-a164-8732d77b09cc', 'kuali.lui.type.activity.offering.lecture', '3b806e27-10ce-40e3-8ed7-afc701cc6e30')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1b6d0dee-d02b-4125-9850-6415bba00a1c', 'kuali.attribute.final.exam.level.type', 'e8847bcf-e155-432f-a164-8732d77b09cc', 'kuali.lu.type.activity.Lecture', '6c589f89-37fb-474f-9af1-77522a77beee')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2263b46e-1883-45b9-9abd-ecde0f39e2c0', 'regCodePrefix', 'e8847bcf-e155-432f-a164-8732d77b09cc', '1', '936908ee-388b-4277-b05f-e84673d3d045')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d0705f87-2c76-4a65-b63c-6257a88db06b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', 'e8847bcf-e155-432f-a164-8732d77b09cc', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '808232ef-e6ce-409c-8b2b-02ccd02861b1')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f89f7bde-3226-438b-a464-07640a725885', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:54.815', '', 'ENGL462-CO-FO', 'c41bee14-5f90-44b4-b389-0c70ea2320df', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL462-CO-FO', 'e8847bcf-e155-432f-a164-8732d77b09cc', 'ea6003d1-6de8-4e6e-97e8-1a7aa381a440')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('e8847bcf-e155-432f-a164-8732d77b09cc', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('048c033a-d85d-436d-a4ef-6a45264b3462', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '702e6aba-fe80-4713-9ec7-caa1759a74e8', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, '27b0d1f7-a387-446e-bcd9-8baf4e94181b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('730c9afd-135a-4372-b495-59b6a5cdb082', 'kuali.attribute.max.enrollment.is.estimate', '27b0d1f7-a387-446e-bcd9-8baf4e94181b', 0, '73c957d5-b483-435c-8a14-113e46577ff1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('aa424b96-c344-4546-b13e-3aa24be50e99', 'kuali.attribute.course.evaluation.indicator', '27b0d1f7-a387-446e-bcd9-8baf4e94181b', 0, 'd900f354-0797-4729-93c4-ff8a5411ce4d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fc753461-e52c-475d-a0bc-cd43e7a739ea', 'kuali.attribute.nonstd.ts.indicator', '27b0d1f7-a387-446e-bcd9-8baf4e94181b', 0, 'd57f6014-9cab-46d2-8a05-867e430713e5')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7835373c-ed18-4ebe-bcf4-40ee7c7897d8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '27b0d1f7-a387-446e-bcd9-8baf4e94181b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '118070d6-5ae3-4d97-b1f3-e290cc2baa6b')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('3d7d9eac-cd73-401e-a9bc-672394288562', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '27b0d1f7-a387-446e-bcd9-8baf4e94181b', '', 'kuali.lu.code.honorsOffering', 0, 'dd788045-6e74-4d9f-91e5-1a4ae5b115a8')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('1f9bc08e-611c-451f-af3a-6ded4682cdaf', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:50:55.288', '', '27b0d1f7-a387-446e-bcd9-8baf4e94181b', 'P.BENJAMINS', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'c790eaf2-bdf5-431b-b3bb-5e01c34e90d2')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('3dc5b243-f4bc-499f-ac71-fa50b3651d8d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:55.288', '', 'ENGL462-FO-AO', 'e8847bcf-e155-432f-a164-8732d77b09cc', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL462-FO-AO', '27b0d1f7-a387-446e-bcd9-8baf4e94181b', '7f26c662-eed1-43df-8ad6-cae6147bf645')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('6f5fcc93-568e-467b-9c2f-3e9f3b7d9ff0', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '26281556-37be-4187-a40e-a9897de29230')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('cac84f28-f8e3-478f-9ae8-87125bf0495d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for ENGL462 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '26281556-37be-4187-a40e-a9897de29230', 'e6239b46-705b-4260-a96f-1f12748b0325')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('9c0832c4-7f45-447b-ad59-300b4cbd969a', 0, 'e6239b46-705b-4260-a96f-1f12748b0325', '365f1946-00ae-4a99-b532-3930e14da81a')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('103a0337-858d-4a43-9297-9f25709716bf', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Athlete Seatpool', '1c2e7786-a2ab-4778-8e81-ea540c60b9b2', 1, 2, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'bb861cf9-6916-4eae-9e9c-52ad8b981a62')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('26281556-37be-4187-a40e-a9897de29230', '27b0d1f7-a387-446e-bcd9-8baf4e94181b')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('365f1946-00ae-4a99-b532-3930e14da81a', '84e53b2a-f39a-4b7a-8916-731087cff42d')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('365f1946-00ae-4a99-b532-3930e14da81a', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('365f1946-00ae-4a99-b532-3930e14da81a', 'f0c251ff-584a-434f-8cf3-9e4e71a9254d')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('365f1946-00ae-4a99-b532-3930e14da81a', 'F08200F10751315CE040440A9B9A3EDC')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('36c1e92e-a477-4af1-9dbe-85a9b35d20b6', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Junior Seatpool', '4bae9f12-5901-4db9-b4a8-4dcc20781db5', 2, 4, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'a8a425fb-3dc8-4e98-99fd-c2a8be18e820')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='103a0337-858d-4a43-9297-9f25709716bf', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='27b0d1f7-a387-446e-bcd9-8baf4e94181b', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Athlete Seatpool', POPULATION_ID='1c2e7786-a2ab-4778-8e81-ea540c60b9b2', PROCESSING_PRIORITY=1, SEAT_LIMIT=2, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='bb861cf9-6916-4eae-9e9c-52ad8b981a62' and VER_NBR=1
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('f9d1564a-503a-4b85-9370-4df46b5a2363', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '8292d64d-ffeb-457b-8b0e-5c35c7896e63')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('00609354-358c-4ac8-81b5-9873500789ba', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'e8847bcf-e155-432f-a164-8732d77b09cc', 'CL 1', 'CL 1', '3ff0f480-5920-4c74-be37-9f8cd24fbc4e')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('3c5a3f31-87e4-4640-8c09-ed44a2c3d609', 'kuali.lui.type.activity.offering.lecture', '3ff0f480-5920-4c74-be37-9f8cd24fbc4e', 'da0172f4-04f1-43ff-8b6a-fd80953f5467')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='36c1e92e-a477-4af1-9dbe-85a9b35d20b6', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='27b0d1f7-a387-446e-bcd9-8baf4e94181b', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Junior Seatpool', POPULATION_ID='4bae9f12-5901-4db9-b4a8-4dcc20781db5', PROCESSING_PRIORITY=2, SEAT_LIMIT=4, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='a8a425fb-3dc8-4e98-99fd-c2a8be18e820' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('8292d64d-ffeb-457b-8b0e-5c35c7896e63', '27b0d1f7-a387-446e-bcd9-8baf4e94181b')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('8292d64d-ffeb-457b-8b0e-5c35c7896e63', 'e8847bcf-e155-432f-a164-8732d77b09cc')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('da0172f4-04f1-43ff-8b6a-fd80953f5467', '27b0d1f7-a387-446e-bcd9-8baf4e94181b')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='3ff0f480-5920-4c74-be37-9f8cd24fbc4e' where ID='da0172f4-04f1-43ff-8b6a-fd80953f5467'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('0827a909-4269-4f6b-a5cd-9973c9adfced', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '85d23c02-4b65-44bd-8718-3e0f409a7015', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'd948724c-4774-4130-9c52-e14aa3efc057')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('edc67548-0459-401b-aab7-fe31cea7f263', 'kuali.attribute.registration.group.is.generated', 'd948724c-4774-4130-9c52-e14aa3efc057', 1, '65b2800a-f4da-4900-ab0b-b425146dd26b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a5960f80-72e4-431f-abef-e51ed98fee96', 'kuali.attribute.registration.group.aocluster.id', 'd948724c-4774-4130-9c52-e14aa3efc057', '3ff0f480-5920-4c74-be37-9f8cd24fbc4e', '322f251d-e655-4719-aec3-b0f7c7e9e070')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('bab10707-7ec3-4970-99d8-df3d34682484', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'd948724c-4774-4130-9c52-e14aa3efc057', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c253887e-122c-495b-91eb-e8c97759ada6')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('5be4ea71-8cf5-47ea-a3dd-ad76b6ef263f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:57.546', '', 'ENGL462-FO-RG', 'e8847bcf-e155-432f-a164-8732d77b09cc', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL462-FO-RG', 'd948724c-4774-4130-9c52-e14aa3efc057', '1fe16818-76d3-4ad5-9499-bdb223fef1db')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('863b3349-a7d5-49c0-95ee-fdeb3f8a2528', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:50:57.547', '', 'ENGL462-RG-AO', 'd948724c-4774-4130-9c52-e14aa3efc057', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL462-RG-AO', '27b0d1f7-a387-446e-bcd9-8baf4e94181b', '1277d66a-3f5f-465d-a27e-0220e13510f7')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('93d644b5-33c0-4cbb-8eff-2bda0daf1c39', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'CLUID-HIST329-200801000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST329N CO', '.', null, 'aab73adc-70d0-48a4-af98-0b18a8171caa')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5d69bba9-e92a-4844-9452-eff13208f69b', 'kuali.attribute.wait.list.indicator', 'aab73adc-70d0-48a4-af98-0b18a8171caa', 1, '3fc4ebaa-8d6a-4c81-8234-08730b504ca5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9c892889-a192-48af-b28c-d1e730cda411', 'kuali.attribute.funding.source', 'aab73adc-70d0-48a4-af98-0b18a8171caa', '', '64d72227-49b3-4ac9-a6ea-0c0350f59efe')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7682715c-9c07-4051-94e2-4de0fe589480', 'kuali.attribute.final.exam.indicator', 'aab73adc-70d0-48a4-af98-0b18a8171caa', 'STANDARD', 'affbd0fc-4798-46d1-94a5-18ac65546aa2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8e7c1e05-1895-42b2-8b58-c5679a76bbf7', 'kuali.attribute.course.number.internal.suffix', 'aab73adc-70d0-48a4-af98-0b18a8171caa', 'A', 'e0afddd7-17b9-46bf-b19b-47c4d4c4d5b4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f1d4d745-75a8-43af-a88a-4d53553aac17', 'kuali.attribute.course.evaluation.indicator', 'aab73adc-70d0-48a4-af98-0b18a8171caa', 0, '1a11ecb2-a958-440f-aca6-2656a1117cd3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7c18da4e-13d2-4206-ae63-1623d68070b3', 'kuali.attribute.where.fees.attached.flag', 'aab73adc-70d0-48a4-af98-0b18a8171caa', 0, 'e55e6a4b-e509-4404-b664-2d51df2f5c5a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d72ef6b7-f27c-48d6-9cfd-b54ba0cef8cf', 'kuali.attribute.wait.list.type.key', 'aab73adc-70d0-48a4-af98-0b18a8171caa', null, '8d662af5-da8b-4634-90d0-de49c24533ba')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f486b0ba-4aef-4dae-a27c-011a80324398', 'kuali.attribute.final.exam.use.matrix', 'aab73adc-70d0-48a4-af98-0b18a8171caa', 1, '9409b32f-596d-4fff-8f86-5338878be6ef')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f875c10a-6388-433e-a8ed-8cf35e11c45f', 'kuali.attribute.wait.list.level.type.key', 'aab73adc-70d0-48a4-af98-0b18a8171caa', 'AO', '668feb9b-f2ec-4978-96de-a8dc1506f905')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('855a1629-cc00-472e-b2b5-4e58d226a088', 'kuali.attribute.final.exam.driver', 'aab73adc-70d0-48a4-af98-0b18a8171caa', 'kuali.lu.exam.driver.ActivityOffering', 'efed2be5-9ab9-41df-a170-74769a7b4e46')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('822c8491-a3da-4703-b3c4-f661ef055624', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'HIST329N', 'HIST', '', 'Special Topics in History', 'aab73adc-70d0-48a4-af98-0b18a8171caa', '', '', 'kuali.lui.identifier.state.active', 'N', 'kuali.lui.identifier.type.official', '', 'bbdc2fd6-122a-4820-84d7-be9f2f3cde9b')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('c2a3d063-27fb-4fa8-8f84-096ebe02ff4e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'aab73adc-70d0-48a4-af98-0b18a8171caa', '', 'kuali.lu.code.honorsOffering', '', 'c50a26a0-d9d2-472b-bd11-30f71a83a44d')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('aab73adc-70d0-48a4-af98-0b18a8171caa', '3213375036', '9f8af2ca-6b10-428b-b3bc-54d4b7ea897b')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('aab73adc-70d0-48a4-af98-0b18a8171caa', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('aab73adc-70d0-48a4-af98-0b18a8171caa', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('aab73adc-70d0-48a4-af98-0b18a8171caa', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('aab73adc-70d0-48a4-af98-0b18a8171caa', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('aab73adc-70d0-48a4-af98-0b18a8171caa', 'kuali.creditType.credit.degree.1.0,1.5,2.0,2.5,3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('49009a2f-032e-48cc-9827-a1bae9a8c709', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'cd1c5479-a556-45c2-a267-e3738132babe', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'a1c68c88-ae89-4ba1-9917-4baf160764e3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a6e7a656-9f76-444f-96fd-9ff35d174954', 'kuali.attribute.grade.roster.level.type.key', 'a1c68c88-ae89-4ba1-9917-4baf160764e3', 'kuali.lui.type.activity.offering.lecture', '286aa26a-a835-4087-973e-68f2761bcf3e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bd8c899f-7dee-4042-8ac6-7d11fb257f28', 'kuali.attribute.final.exam.level.type', 'a1c68c88-ae89-4ba1-9917-4baf160764e3', 'kuali.lu.type.activity.Lecture', '12e66de0-9bcf-4b18-a98a-c2d52067b243')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('359d9abd-1ee5-43bf-966f-f86baf6fbc39', 'regCodePrefix', 'a1c68c88-ae89-4ba1-9917-4baf160764e3', '1', '0cf20431-e2e9-4a4c-865d-41c7ca6e3459')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('00a0c304-795b-4d3f-a44f-2778c7ab4836', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', 'a1c68c88-ae89-4ba1-9917-4baf160764e3', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '4848d2ff-f1af-47cd-be9b-1d08075bed7c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('4e3e53e5-4df0-4ac9-882c-3c858f50e408', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:03.164', '', 'HIST329N-CO-FO', 'aab73adc-70d0-48a4-af98-0b18a8171caa', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST329N-CO-FO', 'a1c68c88-ae89-4ba1-9917-4baf160764e3', '40d1c105-e9df-4f61-ac0c-178cdf92da27')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('a1c68c88-ae89-4ba1-9917-4baf160764e3', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('4fd63fad-7db2-489e-9ba3-80f46a06e7ac', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'ce5a1d8e-ab44-4a98-a3bc-73e328376eea', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 1, 1, 'Lecture', 'Courses with lecture', null, '2c813935-8849-4afc-a09f-67a56654a4df')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('44cf5298-1565-45f5-83df-9190927b78a7', 'kuali.attribute.nonstd.ts.indicator', '2c813935-8849-4afc-a09f-67a56654a4df', 0, 'af18ca2d-6822-4020-a254-67bfa7c06864')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('25e4006c-84fb-480c-aea4-598e8bf02aec', 'kuali.attribute.course.evaluation.indicator', '2c813935-8849-4afc-a09f-67a56654a4df', 0, '48073d5c-6c1a-43cb-8f48-fa2313e48e46')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8781b545-5e54-451c-946f-48ab201e9e63', 'kuali.attribute.max.enrollment.is.estimate', '2c813935-8849-4afc-a09f-67a56654a4df', 0, 'cc4a7832-d84c-4b2d-b6bb-15b9877c1bc7')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('bba6e0bf-e18a-4afa-9fa2-5dfd019a392b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '2c813935-8849-4afc-a09f-67a56654a4df', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '07d9b8c2-8f43-4ac9-85e4-d4615d054e8e')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('4772d232-9b96-4384-b83b-c3ab749b049e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '2c813935-8849-4afc-a09f-67a56654a4df', '', 'kuali.lu.code.honorsOffering', 0, '6d979232-79a5-4b8b-9a37-418b34c3c72c')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('ea86c37e-6545-4e90-a711-690a058d3c18', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:51:03.627', '', '2c813935-8849-4afc-a09f-67a56654a4df', 'P.FRANKJ', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '40c4b3e5-e594-4402-ba0f-91df3af94fb6')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('245abdba-ddd6-4666-b417-acc547733e61', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:03.627', '', 'HIST329N-FO-AO', 'a1c68c88-ae89-4ba1-9917-4baf160764e3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST329N-FO-AO', '2c813935-8849-4afc-a09f-67a56654a4df', 'b3b60932-e0c4-44a4-b295-035382e0c4bd')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('80afc382-311d-44e5-823a-67a3edd3e109', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '94042cd1-69f7-43ba-9f21-b367d9d7a964')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('dee511c2-f47b-4803-a825-9b6b2dd7df2e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for HIST329N - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '94042cd1-69f7-43ba-9f21-b367d9d7a964', '29f2c278-f786-405a-b538-40b35f1dd553')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('252ad893-1c6f-4930-a57b-fec4af1480ad', 1, '29f2c278-f786-405a-b538-40b35f1dd553', 'c64ff14f-40b0-4756-9fd9-c44725a3e51f')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('b3684308-40c4-4299-be9a-fae1333665a2', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '5ab1113b-0e71-417a-8754-c702c82e12cb')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('94042cd1-69f7-43ba-9f21-b367d9d7a964', '2c813935-8849-4afc-a09f-67a56654a4df')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('c64ff14f-40b0-4756-9fd9-c44725a3e51f', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('809b15fc-a203-42da-840d-3f2029a918c6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '0a47eda5-97db-446d-9f67-3449ed900f21')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('f0ee2208-d50b-44aa-9299-ad8621e31f6c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'a1c68c88-ae89-4ba1-9917-4baf160764e3', 'CL 1', 'CL 1', 'a9cffbf9-e65d-447b-83e2-ada1398ac01a')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('a3071509-a720-483d-a06c-dfd24baec9e7', 'kuali.lui.type.activity.offering.lecture', 'a9cffbf9-e65d-447b-83e2-ada1398ac01a', 'bb19b6ab-d2e3-47e8-b1d0-d4a08bef8985')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='b3684308-40c4-4299-be9a-fae1333665a2', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='2c813935-8849-4afc-a09f-67a56654a4df', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='5ab1113b-0e71-417a-8754-c702c82e12cb' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('0a47eda5-97db-446d-9f67-3449ed900f21', '2c813935-8849-4afc-a09f-67a56654a4df')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('0a47eda5-97db-446d-9f67-3449ed900f21', 'a1c68c88-ae89-4ba1-9917-4baf160764e3')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('bb19b6ab-d2e3-47e8-b1d0-d4a08bef8985', '2c813935-8849-4afc-a09f-67a56654a4df')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='a9cffbf9-e65d-447b-83e2-ada1398ac01a' where ID='bb19b6ab-d2e3-47e8-b1d0-d4a08bef8985'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('6f2ae595-cd1d-4bde-aee9-abcb2550bdbb', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'cd1c5479-a556-45c2-a267-e3738132babe', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'e6cba3e5-ae22-4d45-92dd-cca62b3adcd1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5cbfdd5f-d675-4416-831b-cfbf3c061d4f', 'kuali.attribute.registration.group.aocluster.id', 'e6cba3e5-ae22-4d45-92dd-cca62b3adcd1', 'a9cffbf9-e65d-447b-83e2-ada1398ac01a', 'd63fc7f9-b4b5-4230-b92f-d3f0801be2e5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('edd77684-3abe-4443-9f24-6dadb7f8006d', 'kuali.attribute.registration.group.is.generated', 'e6cba3e5-ae22-4d45-92dd-cca62b3adcd1', 1, 'ccc6aa8d-a2a1-4d1f-a06e-456fbe44d3aa')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('9855e9cd-6252-45e5-9435-39b4008ea6fb', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'e6cba3e5-ae22-4d45-92dd-cca62b3adcd1', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '817dfa83-e737-467e-aace-cbd597339d71')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('3278165c-ded6-423a-a24b-3eea1f4ddcd8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:05.828', '', 'HIST329N-FO-RG', 'a1c68c88-ae89-4ba1-9917-4baf160764e3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST329N-FO-RG', 'e6cba3e5-ae22-4d45-92dd-cca62b3adcd1', '89fd8f6c-ef77-4f85-a4e3-d7fdc6bb51eb')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('c44fca52-f5c6-4a4d-af05-09efd29fe4b9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:05.829', '', 'HIST329N-RG-AO', 'e6cba3e5-ae22-4d45-92dd-cca62b3adcd1', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST329N-RG-AO', '2c813935-8849-4afc-a09f-67a56654a4df', '7b206ede-f8d1-48a7-8111-333eeb9a1a41')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('4e0b805e-141b-4b8f-94bc-c80d6206829c', 'e7a38ba0-d45b-42ea-b229-5346b4e9fd02', 'courseOfferingCode', 'HIST329N-P', '8d86f78f-fefc-4ebf-b50c-d13852a9748b')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('38faaf7f-000c-4883-b747-bc9abfb8e3c0', 'A', 'courseOfferingCode', 'HIST329N', '0814f1eb-8710-469c-9180-f366beee035e')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='e7a38ba0-d45b-42ea-b229-5346b4e9fd02' and UNIQUE_KEY='HIST329N-P' and NAMESPACE='courseOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='HIST329N' and NAMESPACE='courseOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='HIST329N-P' and codegenera1_.NAMESPACE='courseOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('db0dab59-c6b8-435b-8439-d6d6cce00806', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'CLUID-HIST329-200801000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST329K CO', '.', null, 'ced498e0-67eb-4b4a-acf2-3f649d463b92')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('430e6a66-57c5-4891-ab43-8117b88fce99', 'kuali.attribute.where.fees.attached.flag', 'ced498e0-67eb-4b4a-acf2-3f649d463b92', 0, '906d1242-a45f-485d-9c09-3a7fabd68e17')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('eb9b7bcc-e6e8-44d4-b309-b25227a0c718', 'kuali.attribute.final.exam.use.matrix', 'ced498e0-67eb-4b4a-acf2-3f649d463b92', 1, 'f4416f1b-d32b-47c3-bced-a61837650f3b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1355358d-b7ea-45b5-a675-e12b874d86b1', 'kuali.attribute.course.evaluation.indicator', 'ced498e0-67eb-4b4a-acf2-3f649d463b92', 0, 'd395ab25-088f-4b3b-8be6-c6729f2e0551')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('59139e41-a954-4b87-8911-5e2de278e2bd', 'kuali.attribute.wait.list.indicator', 'ced498e0-67eb-4b4a-acf2-3f649d463b92', 1, '7f8220b5-e21a-4d77-9ce9-43d500b9f495')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d3b66f69-8c50-4700-bdb1-d5cfb4ff9aab', 'kuali.attribute.course.number.internal.suffix', 'ced498e0-67eb-4b4a-acf2-3f649d463b92', 'A', 'dac23227-e86e-4968-8ab8-6b774d26ef31')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('722e2347-ecd2-46f2-9e95-d1c532bd179c', 'kuali.attribute.wait.list.type.key', 'ced498e0-67eb-4b4a-acf2-3f649d463b92', null, '06ec70b7-121d-4550-954d-970a54e0ed90')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1902a666-7452-4fd3-8600-4a5b852f177c', 'kuali.attribute.final.exam.indicator', 'ced498e0-67eb-4b4a-acf2-3f649d463b92', 'STANDARD', '3e2ceba4-19e1-42a6-a3ef-de344c24b270')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b8bdedd8-5b88-4382-8b36-703e377bcb05', 'kuali.attribute.wait.list.level.type.key', 'ced498e0-67eb-4b4a-acf2-3f649d463b92', 'AO', 'b7419d08-a0dd-42da-9481-e13419c0139d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4a5ba1be-d580-4e07-a48d-7504f65dbe26', 'kuali.attribute.funding.source', 'ced498e0-67eb-4b4a-acf2-3f649d463b92', '', '0f052bea-2a37-4037-af4c-acea3f401041')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7c8aa018-e7a4-4fe3-963a-fe42254c74af', 'kuali.attribute.final.exam.driver', 'ced498e0-67eb-4b4a-acf2-3f649d463b92', 'kuali.lu.exam.driver.ActivityOffering', '7dd90662-9c3b-4013-8bd2-5506a3fca53f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('eb7e2059-a1b1-4e91-93bb-0959d4540b06', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'HIST329K', 'HIST', '', 'Special Topics in History', 'ced498e0-67eb-4b4a-acf2-3f649d463b92', '', '', 'kuali.lui.identifier.state.active', 'K', 'kuali.lui.identifier.type.official', '', '4724e18d-573a-474c-bb5a-cc820e69eae1')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('353cb5cf-966b-4927-a126-1a3348a107bf', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'ced498e0-67eb-4b4a-acf2-3f649d463b92', '', 'kuali.lu.code.honorsOffering', '', 'dccf5bcd-c916-450b-a1f9-a51cbdc93f08')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('ced498e0-67eb-4b4a-acf2-3f649d463b92', '3213375036', '4160948b-8a12-4ba6-ab72-ccb2aad512c8')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('ced498e0-67eb-4b4a-acf2-3f649d463b92', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ced498e0-67eb-4b4a-acf2-3f649d463b92', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ced498e0-67eb-4b4a-acf2-3f649d463b92', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ced498e0-67eb-4b4a-acf2-3f649d463b92', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ced498e0-67eb-4b4a-acf2-3f649d463b92', 'kuali.creditType.credit.degree.1.0,1.5,2.0,2.5,3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('be7af200-edbd-4ebd-918f-c9502e7a295a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'cd1c5479-a556-45c2-a267-e3738132babe', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'f11d25a3-894c-4e0f-b1f2-b4eaadcfdfcd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0e526d78-5dff-4857-84c7-0a2fcbd471af', 'kuali.attribute.final.exam.level.type', 'f11d25a3-894c-4e0f-b1f2-b4eaadcfdfcd', 'kuali.lu.type.activity.Lecture', '9adaa03f-3c44-44a7-9c5a-4fcae16aaa2a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0e8ed0b3-47ba-4b08-8e27-a15328c2a4ed', 'kuali.attribute.grade.roster.level.type.key', 'f11d25a3-894c-4e0f-b1f2-b4eaadcfdfcd', 'kuali.lui.type.activity.offering.lecture', '0ab074e0-e03a-49e8-ba5f-9a271464ec4b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6c3dac3a-b0a6-4219-b872-524a4da234a7', 'regCodePrefix', 'f11d25a3-894c-4e0f-b1f2-b4eaadcfdfcd', '1', 'b5f14116-4e7a-4b37-8b04-0570ef77e372')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('484498e1-ecfa-438d-8c06-bd2ec51ec7bb', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', 'f11d25a3-894c-4e0f-b1f2-b4eaadcfdfcd', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'db6e2a82-11c7-43de-bd5c-92d876b2ab2b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e7b8b7da-21d6-4405-bf34-d380ad20196c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:10.612', '', 'HIST329K-CO-FO', 'ced498e0-67eb-4b4a-acf2-3f649d463b92', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST329K-CO-FO', 'f11d25a3-894c-4e0f-b1f2-b4eaadcfdfcd', 'bd56f50b-a4fa-4995-a991-a07f37eeba8b')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('f11d25a3-894c-4e0f-b1f2-b4eaadcfdfcd', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('c3d76bba-83b8-424b-ba94-045fa8083150', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'e4d0b217-a16e-4efe-96c7-87e307bfffc4', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, '312ec176-d3b8-4859-8e0e-41268f7402a8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d7581060-cd5f-43e0-8423-8d0d2f9ecff9', 'kuali.attribute.course.evaluation.indicator', '312ec176-d3b8-4859-8e0e-41268f7402a8', 0, '39b971bb-1bd3-4c44-9460-0c95b098d9a1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1b838973-b9b1-449e-8cc4-07daa5e61c3e', 'kuali.attribute.nonstd.ts.indicator', '312ec176-d3b8-4859-8e0e-41268f7402a8', 0, 'f69cd331-b38c-4413-a6f6-22c0b4a0fe48')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c6f597d7-6a29-4196-a256-cce32ca5b206', 'kuali.attribute.max.enrollment.is.estimate', '312ec176-d3b8-4859-8e0e-41268f7402a8', 0, '610ff5e0-e8dc-40e1-9200-fd1b4d368b5a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('6e4650e4-ce6d-4ca0-8811-352390d7df62', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '312ec176-d3b8-4859-8e0e-41268f7402a8', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '864f12df-096f-4d28-b8d0-a1d027928c70')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('7e290100-b242-4d8a-adb2-dd1f0eeb2d80', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '312ec176-d3b8-4859-8e0e-41268f7402a8', '', 'kuali.lu.code.honorsOffering', 0, '638862e2-b6c8-423d-9352-14f941ff9d93')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('e6b7d9e1-923f-4d0a-b1bb-5768ed5eabbf', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:51:10.76', '', '312ec176-d3b8-4859-8e0e-41268f7402a8', 'L.GEJ', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'c3b87b21-76e4-4e27-bcd4-dcd50c7697c2')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('dd8d19aa-6c25-430a-a171-aaf6116cf899', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:10.76', '', 'HIST329K-FO-AO', 'f11d25a3-894c-4e0f-b1f2-b4eaadcfdfcd', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST329K-FO-AO', '312ec176-d3b8-4859-8e0e-41268f7402a8', '4a8ef895-5aa3-442c-bd00-96197ff85dc9')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('42fea071-f043-4fef-a19a-351d3aad499f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '37beaf01-889f-4192-8200-cc2bb9efbc50')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('f70c12eb-019e-4398-ae69-cd1cc0e23d64', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for HIST329K - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '37beaf01-889f-4192-8200-cc2bb9efbc50', 'a7aa7821-1548-4c08-852e-cc50002011d1')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('80c7853f-790d-4522-bf9e-303c25b187de', 0, 'a7aa7821-1548-4c08-852e-cc50002011d1', 'a03323ea-88f9-4f44-bb9e-52fc3aa986ba')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('4aca6413-d2a3-4aff-b53f-e3b2052f7fcb', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '839eba52-51b9-449b-a394-7cce2096763c')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('37beaf01-889f-4192-8200-cc2bb9efbc50', '312ec176-d3b8-4859-8e0e-41268f7402a8')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('a03323ea-88f9-4f44-bb9e-52fc3aa986ba', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('a03323ea-88f9-4f44-bb9e-52fc3aa986ba', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('a03323ea-88f9-4f44-bb9e-52fc3aa986ba', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('a03323ea-88f9-4f44-bb9e-52fc3aa986ba', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('006f4dbf-d638-4564-ad27-b5ec7e947219', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'afe19f6d-8126-4089-8169-8ffb6a4f1535')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('57ea3804-d0dc-4858-85eb-6c6421e75746', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'f11d25a3-894c-4e0f-b1f2-b4eaadcfdfcd', 'CL 1', 'CL 1', '26b47c4e-2a68-4a1e-8d58-34c9a644c21c')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('cc20ef0b-8a65-40d2-bf7c-e238a739bdcf', 'kuali.lui.type.activity.offering.lecture', '26b47c4e-2a68-4a1e-8d58-34c9a644c21c', '54e4722e-e953-4560-83fa-47a10ca4cf1e')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='4aca6413-d2a3-4aff-b53f-e3b2052f7fcb', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='312ec176-d3b8-4859-8e0e-41268f7402a8', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='839eba52-51b9-449b-a394-7cce2096763c' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('afe19f6d-8126-4089-8169-8ffb6a4f1535', '312ec176-d3b8-4859-8e0e-41268f7402a8')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('afe19f6d-8126-4089-8169-8ffb6a4f1535', 'f11d25a3-894c-4e0f-b1f2-b4eaadcfdfcd')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('54e4722e-e953-4560-83fa-47a10ca4cf1e', '312ec176-d3b8-4859-8e0e-41268f7402a8')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='26b47c4e-2a68-4a1e-8d58-34c9a644c21c' where ID='54e4722e-e953-4560-83fa-47a10ca4cf1e'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('3f1e0fca-7697-4d63-85b4-dfbb89c1f184', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'cd1c5479-a556-45c2-a267-e3738132babe', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '258d6762-0d00-4bf3-b16e-bb1709f3172e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e8674ce2-c11b-4488-8316-d076ddb66ac8', 'kuali.attribute.registration.group.aocluster.id', '258d6762-0d00-4bf3-b16e-bb1709f3172e', '26b47c4e-2a68-4a1e-8d58-34c9a644c21c', '53df2948-48ff-4d30-8895-ab1295baefc2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('51fdafa1-9342-49b0-9092-fd0190d85ad6', 'kuali.attribute.registration.group.is.generated', '258d6762-0d00-4bf3-b16e-bb1709f3172e', 1, '4f4d95eb-517f-4fef-a3b8-0ade07043d29')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('94226c0f-ace7-4c98-b7d2-50bed6730cb0', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', '258d6762-0d00-4bf3-b16e-bb1709f3172e', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'e89733c1-cea4-4e99-9894-42c1b74bcc06')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e4580a38-86b0-41db-88c0-19800f409216', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:13.418', '', 'HIST329K-FO-RG', 'f11d25a3-894c-4e0f-b1f2-b4eaadcfdfcd', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST329K-FO-RG', '258d6762-0d00-4bf3-b16e-bb1709f3172e', '3cc5948f-eebd-4ff2-a78c-ecdb56d59ddc')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('c9bba971-ea88-439d-9f14-6cc6c072f2ea', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:13.419', '', 'HIST329K-RG-AO', '258d6762-0d00-4bf3-b16e-bb1709f3172e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST329K-RG-AO', '312ec176-d3b8-4859-8e0e-41268f7402a8', '06ff8d6a-0b0b-4cbc-a862-dd76f3c67656')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('61e6872c-cb5a-4cb8-8f2d-15862487857a', '446e5401-437e-4b56-9b47-fb0a5dc59ad2', 'courseOfferingCode', 'CHEM399B-P', 'dadf7d0d-5756-4a3c-8a36-6e58bcdec77b')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('bf92ec20-a684-48f4-8e71-e100ecea7f9d', 'A', 'courseOfferingCode', 'CHEM399B', '25edc884-6795-4e18-814b-a59646cc6589')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='446e5401-437e-4b56-9b47-fb0a5dc59ad2' and UNIQUE_KEY='CHEM399B-P' and NAMESPACE='courseOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='CHEM399B' and NAMESPACE='courseOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='CHEM399B-P' and codegenera1_.NAMESPACE='courseOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('62d21323-9364-41a9-834a-7eecfce05956', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '73acba95-b7ab-494c-a726-6accd2de5257', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Basic (chemical) research conducted under the supervision of a faculty member.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'CHEM399A CO', 'Basic (chemical) research conducted under the supervision of a faculty member.', null, 'c9d56792-0051-4054-b951-6508a21a07e6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ae8a3dca-0ea3-406f-871b-ab3bc048bf99', 'kuali.attribute.wait.list.indicator', 'c9d56792-0051-4054-b951-6508a21a07e6', 1, '1eb7bdc2-ca12-4111-9c4e-8d1eda3189d3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('efff59b0-e789-4404-8259-13a117889a81', 'kuali.attribute.final.exam.driver', 'c9d56792-0051-4054-b951-6508a21a07e6', 'kuali.lu.exam.driver.ActivityOffering', 'd985e714-a3f7-4a35-b7e6-f68633aa09a5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('de917ec0-a8bd-4d9f-bb3e-ba80600da666', 'kuali.attribute.final.exam.use.matrix', 'c9d56792-0051-4054-b951-6508a21a07e6', 1, 'f05b0e07-b75f-4134-bf3b-ba787f1d6bcf')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fbb4ea2a-c570-477c-9be4-45e8815650a3', 'kuali.attribute.final.exam.indicator', 'c9d56792-0051-4054-b951-6508a21a07e6', 'STANDARD', '094238a3-ff6b-49f4-8843-3b4bef15faa9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2c8f797f-9f93-433e-9263-49f82a68813c', 'kuali.attribute.wait.list.level.type.key', 'c9d56792-0051-4054-b951-6508a21a07e6', 'AO', '4916778d-7e5a-47b1-8cee-bd69ebba93bf')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cd5cc09e-fc75-4b11-a2e1-9306a6faace3', 'kuali.attribute.funding.source', 'c9d56792-0051-4054-b951-6508a21a07e6', '', 'dc8adf1c-1432-45c4-82ce-b36fecde844e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3db80657-4a5b-4b27-b72a-6d0fbddca93d', 'kuali.attribute.course.number.internal.suffix', 'c9d56792-0051-4054-b951-6508a21a07e6', 'A', '69a880a3-c0bc-4df3-88cf-0475476b5cd8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f3b660de-ad2f-4f13-8bc7-e4307d089e37', 'kuali.attribute.course.evaluation.indicator', 'c9d56792-0051-4054-b951-6508a21a07e6', 0, '5b374112-6473-4e1b-8702-b5fe333b99d3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9db2562c-f212-4e5b-8e10-152e8e187aef', 'kuali.attribute.where.fees.attached.flag', 'c9d56792-0051-4054-b951-6508a21a07e6', 0, '27d1ba93-d172-48b3-9df0-caa54c5b8014')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0977a830-8af9-4e74-9928-fa40bca38c30', 'kuali.attribute.wait.list.type.key', 'c9d56792-0051-4054-b951-6508a21a07e6', null, 'a25cc47e-ac48-4f0c-9b37-c540ab4db9aa')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('e8958def-58fc-494c-ae2a-abe871e59968', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'CHEM399A', 'CHEM', '', 'Introduction to Chemical Research', 'c9d56792-0051-4054-b951-6508a21a07e6', '', '', 'kuali.lui.identifier.state.active', 'A', 'kuali.lui.identifier.type.official', '', 'afd521ca-47cb-4aaf-bea4-60e41a551553')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('a395558c-883d-45c1-9e73-7de2d2549b26', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'c9d56792-0051-4054-b951-6508a21a07e6', '', 'kuali.lu.code.honorsOffering', '', '0ee3434f-74f8-499a-8fa5-cac7531e8e30')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('c9d56792-0051-4054-b951-6508a21a07e6', '4284516367', '98b5d719-e0ee-4323-8db7-3d7fca9247c2')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('c9d56792-0051-4054-b951-6508a21a07e6', '4284516367')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('c9d56792-0051-4054-b951-6508a21a07e6', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('c9d56792-0051-4054-b951-6508a21a07e6', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('c9d56792-0051-4054-b951-6508a21a07e6', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('c9d56792-0051-4054-b951-6508a21a07e6', 'kuali.creditType.credit.degree.1.0,1.5,2.0,2.5,3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('9a5ebf34-95dd-4b01-8a34-613d2fd6e3f3', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '0ae12d77-775c-499c-ade2-a4e2b60984a9', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '7eb42628-fda5-43df-bad9-3ee9fc5d5b95')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0a9114c7-c047-4e7b-b460-f1c465cb3e8f', 'regCodePrefix', '7eb42628-fda5-43df-bad9-3ee9fc5d5b95', '1', '4add13d4-35c7-47a1-a6ad-734df05654fc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('67c6d1b4-0768-47e7-8f23-7c1fb6cc1670', 'kuali.attribute.grade.roster.level.type.key', '7eb42628-fda5-43df-bad9-3ee9fc5d5b95', 'kuali.lui.type.activity.offering.lecture', 'd3b0c916-0f7d-4a61-8a03-33cecb00926c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('aa103ad7-5889-4051-aaa4-4cf3a55b74f4', 'kuali.attribute.final.exam.level.type', '7eb42628-fda5-43df-bad9-3ee9fc5d5b95', 'kuali.lu.type.activity.Lecture', '4af2bb29-aeb9-4359-a97b-406abc786cee')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a94f7dca-8757-45eb-8006-07cec75952cd', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '7eb42628-fda5-43df-bad9-3ee9fc5d5b95', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '1ccd0d4a-0589-49a8-a463-8428868e9402')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('81b485b2-4eea-4f3a-b93d-e69d71a03da8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:19.424', '', 'CHEM399A-CO-FO', 'c9d56792-0051-4054-b951-6508a21a07e6', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'CHEM399A-CO-FO', '7eb42628-fda5-43df-bad9-3ee9fc5d5b95', 'bb980a2b-6b56-4832-bb47-5a19bea0f5f6')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('7eb42628-fda5-43df-bad9-3ee9fc5d5b95', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('f03132ee-960e-4507-bc47-365834defa63', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'adb8910d-8560-46b5-8306-540e4d3cf0a8', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 5, 5, 'Lecture', 'Courses with lecture', null, '418d106b-b11d-4c0e-a05d-c9f551b5a4bc')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b3d48939-6bc5-4445-8b6c-a6edf84241ec', 'kuali.attribute.course.evaluation.indicator', '418d106b-b11d-4c0e-a05d-c9f551b5a4bc', 0, '8e726695-9aba-4ad4-90de-944c17ed31cb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b958d4d3-c62f-4f77-85f4-32e70a5a8fad', 'kuali.attribute.nonstd.ts.indicator', '418d106b-b11d-4c0e-a05d-c9f551b5a4bc', 0, 'fffe6270-88f8-48e2-8c87-af08a68f088c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2711d1ea-aeaa-4c4e-8e21-c9fa47dc8dc4', 'kuali.attribute.max.enrollment.is.estimate', '418d106b-b11d-4c0e-a05d-c9f551b5a4bc', 0, 'fbd9e1f7-4c39-4bc7-8bd5-7ad46aa08f4f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('79b9da46-42eb-49c6-b495-d2a040512549', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '418d106b-b11d-4c0e-a05d-c9f551b5a4bc', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '8e48f3b5-513b-47fe-93ea-6b499a0e3f7e')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('30fcf24c-d596-4994-ac13-84007afe3d27', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '418d106b-b11d-4c0e-a05d-c9f551b5a4bc', '', 'kuali.lu.code.honorsOffering', 0, 'fc54e08f-f01f-48fe-82ca-d81a9bb0dff4')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('b507b96b-0248-4f96-a35d-2f3ecdc3f7e6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:51:19.476', '', '418d106b-b11d-4c0e-a05d-c9f551b5a4bc', 'J.PATRICIAT', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '0a8d041b-14a4-4cb8-8033-6b3bc83c23e2')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('b8190965-8491-4e89-9237-4725aadc45c4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:19.476', '', 'CHEM399A-FO-AO', '7eb42628-fda5-43df-bad9-3ee9fc5d5b95', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM399A-FO-AO', '418d106b-b11d-4c0e-a05d-c9f551b5a4bc', '4f251cfc-bb55-4a88-82a2-afb2a9fba330')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('e5e0a09c-53c4-4569-bea3-7b437a2a636b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '0886bd0f-8a3c-4d3d-a165-0d77f4b1d081')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('1b245bf7-7fbd-410a-9274-b44e83fb793f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for CHEM399A - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '0886bd0f-8a3c-4d3d-a165-0d77f4b1d081', '3d0cd883-3cec-4dc5-be60-23dfb6885985')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('1e959355-d709-4812-8d69-bfa38e1fb855', 1, '3d0cd883-3cec-4dc5-be60-23dfb6885985', '20d4c6dc-e673-4fdd-a898-b4afd7e2f51b')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('6b9b04fd-bc08-438f-ace3-950e60809839', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '1799c0b5-1d8d-40ec-840e-ceefc8eee9a6')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('0886bd0f-8a3c-4d3d-a165-0d77f4b1d081', '418d106b-b11d-4c0e-a05d-c9f551b5a4bc')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('20d4c6dc-e673-4fdd-a898-b4afd7e2f51b', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('1ac3bc1d-e097-4e91-8536-ffa13d41b5a4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '0cff9cd8-e28c-4f94-93c5-829d81deab67')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('ae098866-870d-455c-814a-41d49b01276e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '7eb42628-fda5-43df-bad9-3ee9fc5d5b95', 'CL 1', 'CL 1', '1069bcf4-ed15-4193-8600-1f1ef3043df0')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('81461e50-c401-43b5-bdf4-df03b6345ffd', 'kuali.lui.type.activity.offering.lecture', '1069bcf4-ed15-4193-8600-1f1ef3043df0', '08145bb7-51ca-4775-ad1e-cac3f94ab86c')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='6b9b04fd-bc08-438f-ace3-950e60809839', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='418d106b-b11d-4c0e-a05d-c9f551b5a4bc', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='1799c0b5-1d8d-40ec-840e-ceefc8eee9a6' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('0cff9cd8-e28c-4f94-93c5-829d81deab67', '418d106b-b11d-4c0e-a05d-c9f551b5a4bc')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('0cff9cd8-e28c-4f94-93c5-829d81deab67', '7eb42628-fda5-43df-bad9-3ee9fc5d5b95')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('08145bb7-51ca-4775-ad1e-cac3f94ab86c', '418d106b-b11d-4c0e-a05d-c9f551b5a4bc')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='1069bcf4-ed15-4193-8600-1f1ef3043df0' where ID='08145bb7-51ca-4775-ad1e-cac3f94ab86c'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('bbf2f528-4d9b-4694-9050-01b76eea5140', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '0ae12d77-775c-499c-ade2-a4e2b60984a9', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'b53b45ee-03f1-4d10-8cb8-98b6edbcd6ef')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b6e9d629-395e-471a-9c11-747639e0e29b', 'kuali.attribute.registration.group.aocluster.id', 'b53b45ee-03f1-4d10-8cb8-98b6edbcd6ef', '1069bcf4-ed15-4193-8600-1f1ef3043df0', '63594cd2-47d8-4c04-9449-410e400059d2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dafaa51a-73f5-4c4a-8951-b7a759854363', 'kuali.attribute.registration.group.is.generated', 'b53b45ee-03f1-4d10-8cb8-98b6edbcd6ef', 1, '553913ab-0a71-4aab-b012-0f9df5bb6495')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('db60ee6c-3ebf-457b-951a-e6f3e7757189', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'b53b45ee-03f1-4d10-8cb8-98b6edbcd6ef', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '9b8d880d-e8a0-491f-ab71-bf1e2deb784d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('1ba38e40-3e89-457f-b000-d2d562b3c8c0', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:19.828', '', 'CHEM399A-FO-RG', '7eb42628-fda5-43df-bad9-3ee9fc5d5b95', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM399A-FO-RG', 'b53b45ee-03f1-4d10-8cb8-98b6edbcd6ef', 'b245e2f8-6657-4035-8a33-75e4203d39a5')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('20eec80b-b8f6-4f89-b67d-488794a6c2ae', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:19.829', '', 'CHEM399A-RG-AO', 'b53b45ee-03f1-4d10-8cb8-98b6edbcd6ef', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM399A-RG-AO', '418d106b-b11d-4c0e-a05d-c9f551b5a4bc', '5145077c-31e8-4eab-a519-b19ca16e575d')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('72a5ae52-f32c-46a2-9b1e-b860c636940e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'CLUID-HIST219-198001000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST219K CO', '.', null, '900d8b55-5fbb-4740-a010-9ae984c374fb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('308340d9-02cc-4977-b2f7-15fae4330757', 'kuali.attribute.final.exam.driver', '900d8b55-5fbb-4740-a010-9ae984c374fb', 'kuali.lu.exam.driver.ActivityOffering', '59a2152e-93e3-4c1b-a85f-58ad4b38afd5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2c069325-0971-4edd-be98-3bfef5ce4db5', 'kuali.attribute.course.number.internal.suffix', '900d8b55-5fbb-4740-a010-9ae984c374fb', 'A', '1d3baeae-5d1f-4dec-848c-0c723386b1ea')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('388e6db0-97ae-4e47-bff6-f8bcf639c78f', 'kuali.attribute.wait.list.indicator', '900d8b55-5fbb-4740-a010-9ae984c374fb', 1, '59abbe05-4c56-4384-b612-aab0e56b5746')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('121df0bf-bc83-42bb-9de5-9143b2714280', 'kuali.attribute.course.evaluation.indicator', '900d8b55-5fbb-4740-a010-9ae984c374fb', 0, 'e7f5057b-31a3-479d-a334-d750e2a9995d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('927014c9-c7c1-4b82-b26d-ab8ce78f8846', 'kuali.attribute.where.fees.attached.flag', '900d8b55-5fbb-4740-a010-9ae984c374fb', 0, '86c2ef24-acbf-4a88-9c51-4f990b9f7768')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8c8d3165-6664-44f6-8d5b-b5c6ab1152ae', 'kuali.attribute.final.exam.indicator', '900d8b55-5fbb-4740-a010-9ae984c374fb', 'STANDARD', 'e14575ee-f091-4236-a8b9-f727b02c903d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4ffc5d3f-900a-4ad7-98a4-57fde159e0ba', 'kuali.attribute.funding.source', '900d8b55-5fbb-4740-a010-9ae984c374fb', '', 'da148915-9c8a-4509-8c36-421edd2a51be')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6068f055-1420-43a2-9532-b44c6120a6c1', 'kuali.attribute.final.exam.use.matrix', '900d8b55-5fbb-4740-a010-9ae984c374fb', 1, '0e83257c-56d1-45b1-b0ee-82d4163aa698')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e29ea5ab-ec4d-4cef-93c5-fad46df743ec', 'kuali.attribute.wait.list.level.type.key', '900d8b55-5fbb-4740-a010-9ae984c374fb', 'AO', 'bcec6a8d-9ccb-4567-87be-dcc029bc430c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b7aa5875-5a46-4c87-b1b4-f644381f41d6', 'kuali.attribute.wait.list.type.key', '900d8b55-5fbb-4740-a010-9ae984c374fb', null, '1fd71932-a63f-4fc0-9143-a0f5c87829e6')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('fd080e66-7935-44f1-9793-35eeab9e6632', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'HIST219K', 'HIST', '', 'Special Topics in History', '900d8b55-5fbb-4740-a010-9ae984c374fb', '', '', 'kuali.lui.identifier.state.active', 'K', 'kuali.lui.identifier.type.official', '', '240c884b-37ac-49d6-94e9-d4fb3b01fa81')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('0618a432-e3c1-4b73-ad37-9187833d8e80', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '900d8b55-5fbb-4740-a010-9ae984c374fb', '', 'kuali.lu.code.honorsOffering', '', '4577aa16-5603-4f1f-b4a4-e72276e5db9a')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('900d8b55-5fbb-4740-a010-9ae984c374fb', '3213375036', 'f306419e-3317-43ba-ab87-f47d4332868d')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('900d8b55-5fbb-4740-a010-9ae984c374fb', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('900d8b55-5fbb-4740-a010-9ae984c374fb', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('900d8b55-5fbb-4740-a010-9ae984c374fb', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('900d8b55-5fbb-4740-a010-9ae984c374fb', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('900d8b55-5fbb-4740-a010-9ae984c374fb', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ae307517-c0d8-4fb9-9996-d46493c6cdd5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '0d8fb0a0-2053-4b0a-a7b8-354463deb594', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '389e4f0f-ffdb-4025-9c07-d0e63f7e990e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a72b3ae5-0629-45d6-aa45-fb2e15786055', 'regCodePrefix', '389e4f0f-ffdb-4025-9c07-d0e63f7e990e', '1', '61f680c5-ab61-4053-85f7-a4b431e96218')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2142e876-69f6-4690-95f5-9a4ea41d71bc', 'kuali.attribute.final.exam.level.type', '389e4f0f-ffdb-4025-9c07-d0e63f7e990e', 'kuali.lu.type.activity.Lecture', 'dc489315-4d33-47cb-b353-c52076a19293')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cb6eace8-eaa5-4ec9-b034-fc7cc3516591', 'kuali.attribute.grade.roster.level.type.key', '389e4f0f-ffdb-4025-9c07-d0e63f7e990e', 'kuali.lui.type.activity.offering.lecture', '27f316b2-0f83-4b84-af2c-d2727c284770')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('8a48c96c-a3d6-400a-a388-e84235a98258', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '389e4f0f-ffdb-4025-9c07-d0e63f7e990e', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '07c68286-86e8-49c2-ab27-d549ccaa64dd')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e292de96-0c35-41c6-b597-ce95621705d1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:22.111', '', 'HIST219K-CO-FO', '900d8b55-5fbb-4740-a010-9ae984c374fb', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST219K-CO-FO', '389e4f0f-ffdb-4025-9c07-d0e63f7e990e', 'b7182d28-5e5c-4aa5-b9a9-abc2654d131e')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('389e4f0f-ffdb-4025-9c07-d0e63f7e990e', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('661c62cf-93d1-45d3-acc7-f9e2784759fa', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '315f56ce-d69d-44a2-a745-5a611e4ac066', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 1, '', 'Lecture', 'Courses with lecture', null, 'f2e1a064-f5cf-4d34-818f-40491adbde5f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a5ff85d0-cd1d-43a9-a3bb-c3f1a03741d3', 'kuali.attribute.course.evaluation.indicator', 'f2e1a064-f5cf-4d34-818f-40491adbde5f', 0, '07c50c10-11dc-490c-bea6-8ef0c808d4d1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5e70132e-9c39-4d4d-9f89-9d7eb7618ff5', 'kuali.attribute.nonstd.ts.indicator', 'f2e1a064-f5cf-4d34-818f-40491adbde5f', 0, '7f307357-af9c-4cc3-bb24-dfdb888b57ed')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0d137727-f0ac-448e-8496-c0d4d9ec8864', 'kuali.attribute.max.enrollment.is.estimate', 'f2e1a064-f5cf-4d34-818f-40491adbde5f', 0, 'a0f59420-e457-4e36-b31a-591f1d154286')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('285da85a-62a4-4cfa-9650-942ddda1634f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', 'f2e1a064-f5cf-4d34-818f-40491adbde5f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '463aa9fc-70aa-4ac1-9afd-685b3c300e00')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('9bce0a4e-ba0c-40d2-8c04-838742b9d0b5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'f2e1a064-f5cf-4d34-818f-40491adbde5f', '', 'kuali.lu.code.honorsOffering', 0, 'd89184e3-6cda-4608-9e94-b35caea73504')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('1f90dcf8-8c6f-4ac9-9258-fcfc29608211', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:51:22.165', '', 'f2e1a064-f5cf-4d34-818f-40491adbde5f', 'P.MARYH', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '52b54711-cf50-4855-855d-f8eea5b8988e')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('23a01a4d-e74c-46b6-826d-57d24c3aeebe', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:22.165', '', 'HIST219K-FO-AO', '389e4f0f-ffdb-4025-9c07-d0e63f7e990e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST219K-FO-AO', 'f2e1a064-f5cf-4d34-818f-40491adbde5f', '2e6858c5-30de-49a1-8961-5fa995d9f68b')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('c0a12337-2a69-4c6f-b71e-b0a559a0ce9e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'd7a29395-e693-468c-852d-c53b1b5b50b4')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('0bea9f3b-bf2c-4e04-bb3c-bffe3fb47e23', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for HIST219K - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'd7a29395-e693-468c-852d-c53b1b5b50b4', '3711ee46-d6fd-460b-a51f-c3442832f03d')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('6b3f0577-3b7b-4462-86bc-1311a4016d6d', 1, '3711ee46-d6fd-460b-a51f-c3442832f03d', '8acda826-9393-47f8-b64a-14766cbda898')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('9561057f-dbb8-4b26-b168-e5c2ce09ec5c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'cbc128b5-7f9e-449b-9197-f0a5309aa7bd')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('45920de8-51b2-4eeb-bd61-1f40ee229b9c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '389e4f0f-ffdb-4025-9c07-d0e63f7e990e', 'CL 1', 'CL 1', '4bfb224c-b5dc-464a-a63e-974e164ab25e')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('98044ede-ea7c-4d25-bd5b-46cad81e8d80', 'kuali.lui.type.activity.offering.lecture', '4bfb224c-b5dc-464a-a63e-974e164ab25e', '60e89047-ec5d-49c2-a58d-97426380521f')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('d7a29395-e693-468c-852d-c53b1b5b50b4', 'f2e1a064-f5cf-4d34-818f-40491adbde5f')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('8acda826-9393-47f8-b64a-14766cbda898', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('cbc128b5-7f9e-449b-9197-f0a5309aa7bd', 'f2e1a064-f5cf-4d34-818f-40491adbde5f')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('cbc128b5-7f9e-449b-9197-f0a5309aa7bd', '389e4f0f-ffdb-4025-9c07-d0e63f7e990e')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('60e89047-ec5d-49c2-a58d-97426380521f', 'f2e1a064-f5cf-4d34-818f-40491adbde5f')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='4bfb224c-b5dc-464a-a63e-974e164ab25e' where ID='60e89047-ec5d-49c2-a58d-97426380521f'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('f217d414-f194-45d2-aed3-1320ba38bdb8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '0d8fb0a0-2053-4b0a-a7b8-354463deb594', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '4f1be100-f3da-4c1f-a9e7-9f873574bb3e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('11c1aac2-8a43-46ad-8a2f-705bd8f8a32e', 'kuali.attribute.registration.group.aocluster.id', '4f1be100-f3da-4c1f-a9e7-9f873574bb3e', '4bfb224c-b5dc-464a-a63e-974e164ab25e', '4d608897-7dec-48dc-aa2c-54761f3efd91')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('227ca7eb-9b67-4d90-85a0-20415800c6d7', 'kuali.attribute.registration.group.is.generated', '4f1be100-f3da-4c1f-a9e7-9f873574bb3e', 1, '271bbed1-cafb-413e-aaaa-31e756382383')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('919cb042-c461-4dc0-b1ef-bd8e7479de60', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', '4f1be100-f3da-4c1f-a9e7-9f873574bb3e', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'd79db799-75e7-489f-8bc6-d7535195d5ba')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e28bf213-0a0c-4e25-b373-2a27a6aaf121', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:22.534', '', 'HIST219K-FO-RG', '389e4f0f-ffdb-4025-9c07-d0e63f7e990e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST219K-FO-RG', '4f1be100-f3da-4c1f-a9e7-9f873574bb3e', 'b4b273cb-5d1b-478d-94f6-566bf35df274')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('72bb2f7e-d32b-4951-a942-c6b5512a977e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:22.535', '', 'HIST219K-RG-AO', '4f1be100-f3da-4c1f-a9e7-9f873574bb3e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST219K-RG-AO', 'f2e1a064-f5cf-4d34-818f-40491adbde5f', '3411264f-3404-470f-acb6-72e8a3e8f7f8')
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='aa22addb-51dc-4b4b-9ab6-19af629aa47d', ATTR_KEY='kuali.soc.rollover.result.dynattr.date.completed', OWNER_ID='8eb89914-1b01-4d50-af48-dab273b4554d', ATTR_VALUE='2014 Jan 22 12:51:22 America/New_York' where ID='401f9b67-014c-4a31-b85e-67ae4de802be'
/
update KSEN_SOC_ROR set OBJ_ID='086ca850-8a66-4720-b0d4-cf7f5955aab1', VER_NBR=5, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ITEMS_EXPECTED=48, ITEMS_PROCESSED=30, MESG_FORMATTED='', MESG_PLAIN='', SOC_ROR_STATE='kuali.soc.rollover.state.running', SOC_ROR_TYPE='kuali.soc.rollover.results.type.rollover', SOURCE_SOC_ID='03192a24-765a-4b9d-b638-5158430e09c8', TARGET_SOC_ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', TARGET_TERM_ID='kuali.atp.2014Winter' where ID='8eb89914-1b01-4d50-af48-dab273b4554d' and VER_NBR=4
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('c6565f93-6270-4d26-af9f-852f6b7de17d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '761a3a32-2eb9-4816-a93b-444bd13003ac', 'adc9f5f3-9952-41bb-8df9-9389d9b608e7', '9e3804fc-d683-427c-8352-1f5ff0c35e15')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cc3c5dcc-9520-474a-bb98-08fb058634c6', 'activityOfferingsCreated', '9e3804fc-d683-427c-8352-1f5ff0c35e15', '1', 'afb4d1e6-0833-49a9-b7a5-ed97bf76a693')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('28907b64-45fd-417a-b9cc-fdee62be1fe5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '78e93646-38b3-4784-8e80-99f705d86b82', 'bc91bc17-4bac-4f65-8158-cbc1bf95ef23', '16146944-1fad-404c-bb53-8b750d217125')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a6b5eb57-6545-4cfe-b8f7-0cec2d221ff2', 'activityOfferingsCreated', '16146944-1fad-404c-bb53-8b750d217125', '2', '77103f91-e906-4b62-88df-6e22b186bdba')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('6173ab90-fddc-412c-a7f2-a46904a66487', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '89d88bce-f84d-49bb-bc99-e66a73e76010', 'ab2ef727-9a04-42ef-94fb-22bc2b1581aa', '3e18de85-9f35-4acb-880a-6d28b39f1593')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1ca785de-60ef-4b53-a86b-669637261104', 'activityOfferingsCreated', '3e18de85-9f35-4acb-880a-6d28b39f1593', '1', '37be5d6d-c401-4307-945e-8993c8642a14')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('39c7a1db-4a13-407e-abd3-6c909031efa9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '89e1b4c8-e6df-4090-ae1c-5dee44dda61c', '9419ad65-926d-4aa9-aeee-05ad81cea3ca', 'e61fcb3c-6e9f-4ebc-8d16-14bb2d837be2')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('27b684bc-204b-4a76-990b-af8458376d6f', 'activityOfferingsCreated', 'e61fcb3c-6e9f-4ebc-8d16-14bb2d837be2', '1', 'b0c8a615-e69f-4b48-867e-146520b6aaa0')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('6228e4a9-40c1-4dc6-a505-2315f3266587', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '8dc6e8ff-6003-4b81-975c-5e620ab58eb2', 'c41bee14-5f90-44b4-b389-0c70ea2320df', '1ba21fe2-ad28-4a49-adcf-a7c1a6989172')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('aa3520a9-2fac-4532-8fe4-3623db7db23c', 'activityOfferingsCreated', '1ba21fe2-ad28-4a49-adcf-a7c1a6989172', '1', '0896841b-a7eb-4124-a966-30368aaad799')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('6c29401e-2b67-4e3c-b71f-b8b5e74d371d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '9172e0c7-853f-44ca-a345-c478416eec71', 'aab73adc-70d0-48a4-af98-0b18a8171caa', '90c25cb7-1c7f-4c30-80e2-49324e43c57e')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5ec525c0-222d-44a7-b0d1-e864d472dfd0', 'activityOfferingsCreated', '90c25cb7-1c7f-4c30-80e2-49324e43c57e', '1', '4948873e-2d91-410f-bb6b-6f0a02f560fe')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('fdafa65c-8836-4ba4-beaa-9751c9f52d7d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '983dcf89-3d39-442e-8983-453012736170', 'ced498e0-67eb-4b4a-acf2-3f649d463b92', '25116c54-d345-4316-b86b-b60d2f59d575')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ce1fe173-2764-474e-bd89-a785b1726579', 'activityOfferingsCreated', '25116c54-d345-4316-b86b-b60d2f59d575', '1', '1dd31f90-16bd-4862-bf7a-0cd7088aceb5')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('f2173dce-ba02-478a-bc79-89d776025b6d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', '95f644a2-ed1a-4b12-b07e-725000413f9b', 'c9d56792-0051-4054-b951-6508a21a07e6', '0bfec737-5fb7-4f5c-8364-c2915dabeb12')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('65f1f863-bf7a-40ac-8c43-999a87073c44', 'activityOfferingsCreated', '0bfec737-5fb7-4f5c-8364-c2915dabeb12', '1', 'b0683e8b-e7f2-4a5b-b7d2-c8f52aa3bc18')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('31abd807-5669-4e37-9381-c5d5db50c25a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', '9bfa392a-e3f2-4a5e-ac8f-2f4f8278a805', '', '91848727-c32c-4ac6-bab9-ae710c15e36f')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('31b31e47-4d18-43f6-90e5-1c4f21aadbe1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'a0cd7b8f-3da7-404b-809f-9ed9c17501be', '900d8b55-5fbb-4740-a010-9ae984c374fb', 'a203c609-48d2-40a4-816d-5c793a82dbc7')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('403b4a84-bcf0-4144-82ca-a004146d40aa', 'activityOfferingsCreated', 'a203c609-48d2-40a4-816d-5c793a82dbc7', '1', '7d679546-b6fb-43c0-abb5-5717d8daceca')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('66d4dd9f-d544-4683-b409-0b3c48304839', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'fb83fb33-973f-420d-aa79-252c47bcbf21', TIMESTAMP '2014-01-01 19:00:00.0', '', 'An examination of women''s creative powers as expressed in selected examples of music, film, art, drama, poetry, fiction, and other literature. Explores women''s creativity in relation to families, religion, education, ethnicity, class, sexuality, and within a cultural tradition shaped by women.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'WMST250 CO', 'An examination of women''s creative powers as expressed in selected examples of music, film, art, drama, poetry, fiction, and other literature. Explores women''s creativity in relation to families, religion, education, ethnicity, class, sexuality, and within a cultural tradition shaped by women.', null, '0d94e955-7a01-4bb5-9f1c-59ec6afda32c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ac25340d-6892-4dc7-b11c-018dca2b61d2', 'kuali.attribute.wait.list.indicator', '0d94e955-7a01-4bb5-9f1c-59ec6afda32c', 1, '7e5988b3-4fb5-4a52-b816-8e8b2969def9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d50a1cfb-e7fb-44bf-9242-8b3681213566', 'kuali.attribute.wait.list.level.type.key', '0d94e955-7a01-4bb5-9f1c-59ec6afda32c', 'AO', 'bc64499a-f429-4177-b336-666437d0b061')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2d17d214-08fd-436e-993d-88c313e0ad67', 'kuali.attribute.course.number.internal.suffix', '0d94e955-7a01-4bb5-9f1c-59ec6afda32c', 'A', '55d0b539-2e9e-412a-b969-f0cad91f8ca2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e7cba3a3-9ee5-49e4-b4c0-4cdb5f116340', 'kuali.attribute.final.exam.driver', '0d94e955-7a01-4bb5-9f1c-59ec6afda32c', 'kuali.lu.exam.driver.ActivityOffering', '7dc1f288-2474-4562-8417-0839a5c1d85d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2bd32bce-be40-432e-b1c7-185e4617785a', 'kuali.attribute.where.fees.attached.flag', '0d94e955-7a01-4bb5-9f1c-59ec6afda32c', 0, 'e80ff3d8-3003-4b53-915c-2bf75d04689c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2a70169f-a124-421f-b32e-3f6ee628c916', 'kuali.attribute.final.exam.use.matrix', '0d94e955-7a01-4bb5-9f1c-59ec6afda32c', 1, 'baa72721-c243-4576-af36-055ec793acf4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6ba4d622-b2a9-4539-b5a3-dd6e9d22ca3d', 'kuali.attribute.final.exam.indicator', '0d94e955-7a01-4bb5-9f1c-59ec6afda32c', 'STANDARD', '05774b96-c78d-4976-8864-4e4b5b08e0c8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f098581a-2493-42b0-9d9a-3690866e5045', 'kuali.attribute.funding.source', '0d94e955-7a01-4bb5-9f1c-59ec6afda32c', '', 'f6740be8-2759-4575-a206-ea2649309e5b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('12df653e-5796-45b4-9fa6-121fc5f14b61', 'kuali.attribute.wait.list.type.key', '0d94e955-7a01-4bb5-9f1c-59ec6afda32c', null, '2e5a1d1f-4a64-49f2-8084-bed8ef49d431')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c54fe1f6-eb71-427a-b76f-615ec7cc8562', 'kuali.attribute.course.evaluation.indicator', '0d94e955-7a01-4bb5-9f1c-59ec6afda32c', 0, 'f24a27d7-6e61-4c20-b62f-1996463b77f6')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('026bf235-1c1b-4564-90e3-b4980d95141e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'WMST250', 'WMST', '', 'Introduction to Women''s Studies:  Women, Art and Culture', '0d94e955-7a01-4bb5-9f1c-59ec6afda32c', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7b5f58db-1b87-48bf-a730-9736f189410e')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('c796818c-95b3-4231-bd65-166f29e2b7df', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '0d94e955-7a01-4bb5-9f1c-59ec6afda32c', '', 'kuali.lu.code.honorsOffering', '', 'd91a34de-120f-40ff-97a7-209638c107f1')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('0d94e955-7a01-4bb5-9f1c-59ec6afda32c', '4014660630', '5abf028d-c8b9-40fc-94f6-398188fea29f')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('0d94e955-7a01-4bb5-9f1c-59ec6afda32c', '4014660630')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0d94e955-7a01-4bb5-9f1c-59ec6afda32c', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0d94e955-7a01-4bb5-9f1c-59ec6afda32c', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0d94e955-7a01-4bb5-9f1c-59ec6afda32c', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0d94e955-7a01-4bb5-9f1c-59ec6afda32c', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('f4b8f369-7672-4e45-9d8f-420573f4c740', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '53f70802-c152-40a6-bbc8-51e9c0851622', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '3435e6e6-4622-4139-97d2-725c1a166b2f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('41be6249-560a-4d56-8bab-02abe9f027a7', 'kuali.attribute.final.exam.level.type', '3435e6e6-4622-4139-97d2-725c1a166b2f', 'kuali.lu.type.activity.Lecture', '40f48750-a2d3-4db7-9c6d-d492fb59a36b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7806b420-7a2d-4544-9219-1967b32d0d5e', 'regCodePrefix', '3435e6e6-4622-4139-97d2-725c1a166b2f', '1', '0602d4e8-e385-4e38-9a64-e542fd3b9447')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bbb78878-4cf1-4d86-80b5-f4e9025613ad', 'kuali.attribute.grade.roster.level.type.key', '3435e6e6-4622-4139-97d2-725c1a166b2f', 'kuali.lui.type.activity.offering.lecture', '236c4f5b-6aa9-4008-b655-9362bb4bd1e3')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('d5023326-1e97-4b6d-a456-49967a53f65e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '3435e6e6-4622-4139-97d2-725c1a166b2f', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '34a22b3b-7416-40ce-b8e0-8c3621292854')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('11f4bc12-e22b-4930-908c-8d737d4e8fac', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:25.433', '', 'WMST250-CO-FO', '0d94e955-7a01-4bb5-9f1c-59ec6afda32c', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'WMST250-CO-FO', '3435e6e6-4622-4139-97d2-725c1a166b2f', '872674f9-30b9-434e-b06e-51d54d064cc1')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('3435e6e6-4622-4139-97d2-725c1a166b2f', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('8c6d6205-afe4-43a7-965e-eb10e1bea0bf', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'ef9d80d1-2429-49b7-af3c-ecadab6eb60f', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, 'd1a90b6c-726a-4b24-9006-10276ac4e7a1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('295cc7f3-f493-40d5-8f48-9d8abf06b001', 'kuali.attribute.course.evaluation.indicator', 'd1a90b6c-726a-4b24-9006-10276ac4e7a1', 0, '93fb5088-f243-450a-a306-04658916c9f7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f048be9a-cb18-4b34-a9c9-6c5df36c229b', 'kuali.attribute.nonstd.ts.indicator', 'd1a90b6c-726a-4b24-9006-10276ac4e7a1', 0, 'd12df10d-986f-4e07-9745-b7ad01f52462')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('21e6dcf0-9733-4042-9454-aa932235afb5', 'kuali.attribute.max.enrollment.is.estimate', 'd1a90b6c-726a-4b24-9006-10276ac4e7a1', 0, 'e691afac-cf87-4aff-9f10-01815bf76653')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('ef41a321-a0a5-4fe0-804e-ad0677ad6848', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', 'd1a90b6c-726a-4b24-9006-10276ac4e7a1', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '6ab38595-138f-4900-941d-bf566e50b2f4')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('f7442341-fda6-4c4c-8759-95c08a36a49b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'd1a90b6c-726a-4b24-9006-10276ac4e7a1', '', 'kuali.lu.code.honorsOffering', 0, '39bc3784-5ae5-42d2-bbeb-404b6363a2bc')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('a2246eb9-1a7f-40af-8820-32fa4d89fc23', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:51:25.519', '', 'd1a90b6c-726a-4b24-9006-10276ac4e7a1', 'C.SUSANB', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'a6021a4a-bc54-4574-b856-16cba686079c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('02487fc5-bea2-4db7-a675-6c96ebed8e62', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:25.519', '', 'WMST250-FO-AO', '3435e6e6-4622-4139-97d2-725c1a166b2f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'WMST250-FO-AO', 'd1a90b6c-726a-4b24-9006-10276ac4e7a1', 'e99d0315-5d15-4996-9b5f-2a09d8cd00cf')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('eeffb8e1-ebb9-4972-83b6-0af9f9430b3f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '85e1c888-cd0d-4fe0-b0eb-6164bf890713')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('b3dc765d-e3f8-4e01-afcb-d58bba96a9a6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for WMST250 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '85e1c888-cd0d-4fe0-b0eb-6164bf890713', 'c81186df-dfa8-4282-95ee-b8d30402749b')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('8d4eeb3c-8c05-4895-ac97-72133126b1b0', 0, 'c81186df-dfa8-4282-95ee-b8d30402749b', '0f947884-fc67-4591-9f57-9a14b01a81fd')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('c920ed8b-929a-467e-ad3a-8be7dbc64de1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '20541757-cf3a-41d5-8781-a4602805ed3b')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('bc53cc1a-c6e7-414c-9073-4de5a213fdf6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '3435e6e6-4622-4139-97d2-725c1a166b2f', 'CL 1', 'CL 1', '81bbeebf-fa21-4834-8a9b-ce85e4699cd4')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('cfe98e21-6b24-44f5-b658-6945959ae24a', 'kuali.lui.type.activity.offering.lecture', '81bbeebf-fa21-4834-8a9b-ce85e4699cd4', 'ea59c4f5-a531-4843-a5ee-278205971927')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('85e1c888-cd0d-4fe0-b0eb-6164bf890713', 'd1a90b6c-726a-4b24-9006-10276ac4e7a1')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('0f947884-fc67-4591-9f57-9a14b01a81fd', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('0f947884-fc67-4591-9f57-9a14b01a81fd', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('0f947884-fc67-4591-9f57-9a14b01a81fd', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('0f947884-fc67-4591-9f57-9a14b01a81fd', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('20541757-cf3a-41d5-8781-a4602805ed3b', 'd1a90b6c-726a-4b24-9006-10276ac4e7a1')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('20541757-cf3a-41d5-8781-a4602805ed3b', '3435e6e6-4622-4139-97d2-725c1a166b2f')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('ea59c4f5-a531-4843-a5ee-278205971927', 'd1a90b6c-726a-4b24-9006-10276ac4e7a1')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='81bbeebf-fa21-4834-8a9b-ce85e4699cd4' where ID='ea59c4f5-a531-4843-a5ee-278205971927'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('58852b70-30cc-46f2-8152-df1f9c0c10d8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '53f70802-c152-40a6-bbc8-51e9c0851622', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '4496d359-3218-4b03-b193-dcd425b3b900')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('416bf0dd-f064-4274-b57b-46fc7cb0b61a', 'kuali.attribute.registration.group.aocluster.id', '4496d359-3218-4b03-b193-dcd425b3b900', '81bbeebf-fa21-4834-8a9b-ce85e4699cd4', '0ab613f0-a83b-4bd2-b48b-47e32c844c91')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('361048ae-e5d0-451d-be52-6f4913b41364', 'kuali.attribute.registration.group.is.generated', '4496d359-3218-4b03-b193-dcd425b3b900', 1, '84fc3248-4928-4edd-8ea7-b320b59a8393')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a388b3b5-a7dd-4707-9997-01803dfc97df', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', '4496d359-3218-4b03-b193-dcd425b3b900', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '362f1ecd-f781-44ac-9015-c75e54c55ac2')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('7a1bde0a-0457-4715-912c-dcb614a05ef9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:25.977', '', 'WMST250-FO-RG', '3435e6e6-4622-4139-97d2-725c1a166b2f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'WMST250-FO-RG', '4496d359-3218-4b03-b193-dcd425b3b900', '38bc31b2-9e8e-4b93-9dae-aaabe8850537')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e210b11e-a006-4717-a6f7-d6ccc10a704d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:25.977', '', 'WMST250-RG-AO', '4496d359-3218-4b03-b193-dcd425b3b900', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'WMST250-RG-AO', 'd1a90b6c-726a-4b24-9006-10276ac4e7a1', '37c153c3-a6da-4db3-9e34-b952410278ca')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('6eaa4f71-ba67-4080-8ea8-efc9488312a2', '62e4b7c5-bf23-4b7b-9752-930ab674e029', 'courseOfferingCode', 'HIST319D-P', 'e01660b2-12ae-40ee-8da2-9dff0b595f07')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('6d6067c8-b65a-42f9-ae66-da7d6076c90b', 'A', 'courseOfferingCode', 'HIST319D', '981bb4b7-aff6-4aeb-b692-d660fe306cd7')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='62e4b7c5-bf23-4b7b-9752-930ab674e029' and UNIQUE_KEY='HIST319D-P' and NAMESPACE='courseOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='HIST319D' and NAMESPACE='courseOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='HIST319D-P' and codegenera1_.NAMESPACE='courseOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('86e44e29-10e6-4c0b-a508-38d23d01dc0e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'CLUID-HIST319-198001000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST319O CO', '.', null, 'f8b1c348-8b1f-46be-b2b0-29735667e8e2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d2ffbbdb-23d6-4a32-b712-a46fa06cfa2c', 'kuali.attribute.final.exam.indicator', 'f8b1c348-8b1f-46be-b2b0-29735667e8e2', 'STANDARD', '57506702-5c73-4384-ad66-7adb7997319e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5dc89afa-4112-4611-9b34-e351227715b7', 'kuali.attribute.course.evaluation.indicator', 'f8b1c348-8b1f-46be-b2b0-29735667e8e2', 0, '7f470e54-0a03-4f5f-b096-8fa3bae2246e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8e6959d4-34ef-473e-a364-0d1b5258d2e0', 'kuali.attribute.where.fees.attached.flag', 'f8b1c348-8b1f-46be-b2b0-29735667e8e2', 0, 'e3818044-79d8-49dd-a196-cf7c55856903')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('17bd27ba-06ff-4af5-b92f-7b8a1b61fc63', 'kuali.attribute.funding.source', 'f8b1c348-8b1f-46be-b2b0-29735667e8e2', '', 'e21855fd-db5e-4f1b-8e21-3f7d389fb550')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ef583170-9d0e-4589-a68a-7700a9b9bea4', 'kuali.attribute.final.exam.driver', 'f8b1c348-8b1f-46be-b2b0-29735667e8e2', 'kuali.lu.exam.driver.ActivityOffering', '132598f9-5878-4b39-9954-db200f81bc6a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c9ada6b6-c54e-498a-b397-5ddc869390d8', 'kuali.attribute.wait.list.level.type.key', 'f8b1c348-8b1f-46be-b2b0-29735667e8e2', 'AO', 'e63f13a7-40e3-4b1c-80bd-13b283c4a3b6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1fb1b634-1afd-406b-ad10-74301fa94393', 'kuali.attribute.final.exam.use.matrix', 'f8b1c348-8b1f-46be-b2b0-29735667e8e2', 1, '7c2fba65-f0e3-4908-a909-5a1312d08835')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fcce0f0f-10e6-4fa0-95d7-d79a49599ee0', 'kuali.attribute.wait.list.indicator', 'f8b1c348-8b1f-46be-b2b0-29735667e8e2', 1, '70dbf1c4-ea4e-4b62-bc5a-86865994f531')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dec0a201-046e-476a-85d1-f5a9c7851a0e', 'kuali.attribute.course.number.internal.suffix', 'f8b1c348-8b1f-46be-b2b0-29735667e8e2', 'A', '28f6c982-8343-4360-9025-3fc05a503075')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('799edd47-dd1d-49a1-a174-ffd741ccd637', 'kuali.attribute.wait.list.type.key', 'f8b1c348-8b1f-46be-b2b0-29735667e8e2', null, 'edf320ac-4480-432e-b04b-2bccf928844f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('58f807b6-66d1-448e-9c94-79a6d797dfe4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'HIST319O', 'HIST', '', 'Special Topics in History', 'f8b1c348-8b1f-46be-b2b0-29735667e8e2', '', '', 'kuali.lui.identifier.state.active', 'O', 'kuali.lui.identifier.type.official', '', '0dc10d23-fa8d-4f8e-aa54-99b6df5e4d44')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('cdda2283-c90c-42ae-b447-6a22337069a8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'f8b1c348-8b1f-46be-b2b0-29735667e8e2', '', 'kuali.lu.code.honorsOffering', '', '76d48a21-67e8-47f7-8c42-94a100d7a960')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('f8b1c348-8b1f-46be-b2b0-29735667e8e2', '3213375036', '0dfbec6d-3ea9-407b-81e1-8947fbf2a808')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('f8b1c348-8b1f-46be-b2b0-29735667e8e2', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('f8b1c348-8b1f-46be-b2b0-29735667e8e2', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('f8b1c348-8b1f-46be-b2b0-29735667e8e2', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('f8b1c348-8b1f-46be-b2b0-29735667e8e2', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('f8b1c348-8b1f-46be-b2b0-29735667e8e2', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('937dc9d6-b537-4243-b91b-4a6a874fb780', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'ccb3c17e-6eaf-4095-85b7-648120717da9', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'fc5ba5e6-b6f7-459a-98ed-59605929b660')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('eb831162-e54f-41ae-9b90-ea371a964a03', 'regCodePrefix', 'fc5ba5e6-b6f7-459a-98ed-59605929b660', '1', '76c3f0fd-e69f-41dd-85be-1b4c17a92678')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2cf4202f-ee4e-4720-bed2-bad4c02a4075', 'kuali.attribute.grade.roster.level.type.key', 'fc5ba5e6-b6f7-459a-98ed-59605929b660', 'kuali.lui.type.activity.offering.lecture', '50237c48-dccc-42fa-ac1d-2c1497f8576f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d07b907e-5311-412c-9a17-374b7bd958b2', 'kuali.attribute.final.exam.level.type', 'fc5ba5e6-b6f7-459a-98ed-59605929b660', 'kuali.lu.type.activity.Lecture', 'e588301d-75f7-4bd0-97af-5885f268a207')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('141985c6-50fb-40a6-b9a5-90cda1837a48', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', 'fc5ba5e6-b6f7-459a-98ed-59605929b660', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'f89a173e-0b42-4a74-a420-58351d706226')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('3c0dea9a-4b43-460d-a5fa-b02abd57c858', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:28.176', '', 'HIST319O-CO-FO', 'f8b1c348-8b1f-46be-b2b0-29735667e8e2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST319O-CO-FO', 'fc5ba5e6-b6f7-459a-98ed-59605929b660', '81a5dd6b-7266-4aba-9f6d-33aa3c8f80ba')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('fc5ba5e6-b6f7-459a-98ed-59605929b660', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('24182efa-8873-4d30-a295-725d65105b74', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '55fda0fc-cb22-4b3b-a7e0-5a0ceeaaf89d', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, '2ae49c43-c169-4a00-a619-b7b3d23763df')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d85d8d58-34b3-42b8-8998-1d810760306d', 'kuali.attribute.nonstd.ts.indicator', '2ae49c43-c169-4a00-a619-b7b3d23763df', 0, '80a786d4-5d46-4049-9c8a-a53ae1d58cd6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c1fd1c6b-c360-4a62-b736-359fa0ea0883', 'kuali.attribute.max.enrollment.is.estimate', '2ae49c43-c169-4a00-a619-b7b3d23763df', 0, '44ff74ee-1cb0-4b24-b3aa-a4382b79d709')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9c2704a7-d53b-44f3-bba6-8ad78a170af7', 'kuali.attribute.course.evaluation.indicator', '2ae49c43-c169-4a00-a619-b7b3d23763df', 0, '5f1b910f-fc4e-46e5-8c91-7645957be006')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('fac6f113-2536-4788-834e-9e29e7bd0bbd', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '2ae49c43-c169-4a00-a619-b7b3d23763df', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '125e20df-90ef-4f0f-bf68-b73caeb5e5ed')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('f18d928b-cd67-4a3c-955c-28607e5b5c77', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '2ae49c43-c169-4a00-a619-b7b3d23763df', '', 'kuali.lu.code.honorsOffering', 0, '43160209-159f-4ff7-bbf4-8cf4e779cabe')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('96062b6a-812c-4778-b197-e68fc9ebf654', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:51:28.249', '', '2ae49c43-c169-4a00-a619-b7b3d23763df', 'S.JOSEPHE', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '44dae688-6e47-4d89-9a7b-b6ea983bad58')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('b1acf2c0-b24e-4f73-a9a5-1f9858b8797e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:28.249', '', 'HIST319O-FO-AO', 'fc5ba5e6-b6f7-459a-98ed-59605929b660', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST319O-FO-AO', '2ae49c43-c169-4a00-a619-b7b3d23763df', 'f488964e-2383-40df-9ef8-2a1627c7e434')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('9a3cc661-981d-4f5a-b78b-c454b768630a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '20919170-5ae9-458c-b204-91820440fed8')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('54de5380-6cd3-4e98-a2c3-55a53611f210', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for HIST319O - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '20919170-5ae9-458c-b204-91820440fed8', '620e6298-74b2-4581-b619-92f2e57d544c')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('4e9bb206-d678-468e-82ae-0f9027f4e53f', 0, '620e6298-74b2-4581-b619-92f2e57d544c', 'bbad4131-2cdd-4e53-be20-1f9c9a232e7d')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('333d4476-d543-4e43-9805-60a79a60ac96', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '823b1feb-59bb-4062-a027-1eebfe74ab0c')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('20919170-5ae9-458c-b204-91820440fed8', '2ae49c43-c169-4a00-a619-b7b3d23763df')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('bbad4131-2cdd-4e53-be20-1f9c9a232e7d', 'af22fa59-42ab-4d09-9a5b-6980f8b4ae7d')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('bbad4131-2cdd-4e53-be20-1f9c9a232e7d', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('bbad4131-2cdd-4e53-be20-1f9c9a232e7d', 'fdd1e716-7c99-4cc9-afbe-d8fa2e44ec66')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('bbad4131-2cdd-4e53-be20-1f9c9a232e7d', 'F08200F10579315CE040440A9B9A3EDC')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('497c0e1c-dede-49d0-a516-77f20457cc32', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'cb3b2731-b798-4745-8f33-bb2c5c181205')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('a0c8d916-360d-4b5c-b5e0-35968ca60578', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'fc5ba5e6-b6f7-459a-98ed-59605929b660', 'CL 1', 'CL 1', '82043d0b-d6b4-498d-9f05-04fc5e7ae8e1')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('2aab2615-21aa-4005-8c40-edf127f73694', 'kuali.lui.type.activity.offering.lecture', '82043d0b-d6b4-498d-9f05-04fc5e7ae8e1', '4925230c-c8e7-4d53-9d64-f29b8fe4fb49')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='333d4476-d543-4e43-9805-60a79a60ac96', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='2ae49c43-c169-4a00-a619-b7b3d23763df', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='823b1feb-59bb-4062-a027-1eebfe74ab0c' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('cb3b2731-b798-4745-8f33-bb2c5c181205', '2ae49c43-c169-4a00-a619-b7b3d23763df')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('cb3b2731-b798-4745-8f33-bb2c5c181205', 'fc5ba5e6-b6f7-459a-98ed-59605929b660')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('4925230c-c8e7-4d53-9d64-f29b8fe4fb49', '2ae49c43-c169-4a00-a619-b7b3d23763df')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='82043d0b-d6b4-498d-9f05-04fc5e7ae8e1' where ID='4925230c-c8e7-4d53-9d64-f29b8fe4fb49'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('c99a90fb-7388-4332-8a0d-79ca463dc74d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'ccb3c17e-6eaf-4095-85b7-648120717da9', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'bf5a7217-436d-4d2a-939e-acbff220f66f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('616d6cca-612d-4e76-a249-43b2bc43346d', 'kuali.attribute.registration.group.aocluster.id', 'bf5a7217-436d-4d2a-939e-acbff220f66f', '82043d0b-d6b4-498d-9f05-04fc5e7ae8e1', 'd40de3e2-31d4-49c5-a06d-405e494d98e9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9043f2d4-d303-4467-b98b-8a320dc7fcd2', 'kuali.attribute.registration.group.is.generated', 'bf5a7217-436d-4d2a-939e-acbff220f66f', 1, '19a96435-a22a-4f0d-a83d-7e9fdc1046dc')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c8ee264a-a3ab-436e-a603-01cc222fdbb0', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'bf5a7217-436d-4d2a-939e-acbff220f66f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'e2f2a9f1-c892-477a-908d-911d19dd0295')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a19814bf-4f65-41f8-9533-309627b4d008', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:28.69', '', 'HIST319O-FO-RG', 'fc5ba5e6-b6f7-459a-98ed-59605929b660', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST319O-FO-RG', 'bf5a7217-436d-4d2a-939e-acbff220f66f', '77c542d6-b738-4566-91ea-500aa0774369')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('3602f117-07aa-48f4-80a0-49ebd5dcb252', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:28.691', '', 'HIST319O-RG-AO', 'bf5a7217-436d-4d2a-939e-acbff220f66f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST319O-RG-AO', '2ae49c43-c169-4a00-a619-b7b3d23763df', 'b6e6916b-b4e1-46d4-9734-7c48cc13f7d2')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('dc5ac2e5-479f-4fd3-9483-5df35bb95d0c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'CLUID-WMST326-199501000000', TIMESTAMP '2014-01-01 19:00:00.0', '', 'The biology of the reproductive system with emphasis on mammals and, in particular, on human reproduction. Hormone actions, sperm production, ovulation, sexual differentiation, sexual behavior, contraception, pregnancy, lactation, maternal behavior and menopause.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'WMST326 CO', 'The biology of the reproductive system with emphasis on mammals and, in particular, on human reproduction. Hormone actions, sperm production, ovulation, sexual differentiation, sexual behavior, contraception, pregnancy, lactation, maternal behavior and menopause.', null, '8bb9ee5b-7de3-4f58-a499-03224bc0686c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('69cbca75-7cb2-47da-ac1f-36e6c2369fe4', 'kuali.attribute.wait.list.level.type.key', '8bb9ee5b-7de3-4f58-a499-03224bc0686c', 'AO', 'f09cb11f-5e66-43d1-8215-bb4776429662')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('847fa157-032b-4371-b8e6-56203c114677', 'kuali.attribute.where.fees.attached.flag', '8bb9ee5b-7de3-4f58-a499-03224bc0686c', 0, '329fc70f-d12c-49a4-8910-e1e0e7c199ab')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d2cf47d6-c572-46e1-8eea-a1f3b5cde444', 'kuali.attribute.course.evaluation.indicator', '8bb9ee5b-7de3-4f58-a499-03224bc0686c', 0, '8defe329-d56a-44d6-a438-1d6ed1c9c25d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1bec5e5f-816f-4b3d-8c44-2869b18ee017', 'kuali.attribute.wait.list.indicator', '8bb9ee5b-7de3-4f58-a499-03224bc0686c', 1, '7b42f52e-3dbb-4c0b-9e68-91118d3a7327')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('82db89a6-3544-4e87-9ee6-e967775cb2ba', 'kuali.attribute.final.exam.indicator', '8bb9ee5b-7de3-4f58-a499-03224bc0686c', 'STANDARD', '2c1a7c03-3b2c-4230-b737-68bdda2517e7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('84173394-f701-4dc2-829c-b27f248e4915', 'kuali.attribute.funding.source', '8bb9ee5b-7de3-4f58-a499-03224bc0686c', '', 'd9ffef46-246f-4753-9d09-be8fa7f3ad7a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9f44019e-bb6d-43b8-8b89-a6aeab8b7074', 'kuali.attribute.wait.list.type.key', '8bb9ee5b-7de3-4f58-a499-03224bc0686c', null, '14e894a7-e811-48be-8b67-75865a730d63')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('15a36fc1-6443-409e-90da-5449228e20a5', 'kuali.attribute.final.exam.use.matrix', '8bb9ee5b-7de3-4f58-a499-03224bc0686c', 1, '3e2ee328-b299-43cb-a153-cd933e699f0d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('028bb0d4-3b2a-42d3-8d12-241a65b65d34', 'kuali.attribute.course.number.internal.suffix', '8bb9ee5b-7de3-4f58-a499-03224bc0686c', 'A', 'b6d3f2f8-5e4f-476c-b4a3-5b4f46e68b86')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('08e07751-c6a7-4d9a-804e-5ca75d76c82d', 'kuali.attribute.final.exam.driver', '8bb9ee5b-7de3-4f58-a499-03224bc0686c', 'kuali.lu.exam.driver.ActivityOffering', 'ad6564ec-eac1-4a7e-b044-ac78b1b6edb2')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('40e52741-fdae-4191-80c8-bc604dd6a7d5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'WMST326', 'WMST', '', 'Biology of Reproduction', '8bb9ee5b-7de3-4f58-a499-03224bc0686c', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'cdf98682-4232-40ba-b7e4-2f1f535605f6')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('17ef24c0-2ed6-430a-b4bf-c47beebd7fd3', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '8bb9ee5b-7de3-4f58-a499-03224bc0686c', '', 'kuali.lu.code.honorsOffering', '', 'f1cfdd89-99c8-41c9-a4e0-5811d608c44a')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('8bb9ee5b-7de3-4f58-a499-03224bc0686c', '4014660630', 'f6b8a77a-47f2-4cf7-a26d-9d04d9eee528')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('8bb9ee5b-7de3-4f58-a499-03224bc0686c', '4014660630')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('8bb9ee5b-7de3-4f58-a499-03224bc0686c', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('8bb9ee5b-7de3-4f58-a499-03224bc0686c', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('8bb9ee5b-7de3-4f58-a499-03224bc0686c', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('8bb9ee5b-7de3-4f58-a499-03224bc0686c', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('e16ec587-1bbe-4418-b12c-6f9c3f29a281', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '365c3c5f-8422-4b70-8bd7-949a708229d8', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '8e5e0e95-3676-469b-b1a6-7ed541b8cf69')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cb9e780a-5182-4c26-9d9e-c5c6195f0438', 'kuali.attribute.grade.roster.level.type.key', '8e5e0e95-3676-469b-b1a6-7ed541b8cf69', 'kuali.lui.type.activity.offering.lecture', '80087cd4-80bf-4a8d-9219-a1f0c950e979')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6c96bae8-e365-4fbe-b533-5b3d561102a5', 'kuali.attribute.final.exam.level.type', '8e5e0e95-3676-469b-b1a6-7ed541b8cf69', 'kuali.lu.type.activity.Lecture', '715081fa-73e2-4c73-8986-5a5314b4b997')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6b0babe0-3194-472e-bb75-65f578f57005', 'regCodePrefix', '8e5e0e95-3676-469b-b1a6-7ed541b8cf69', '1', 'c1265fbd-850b-4ba6-9368-fb0791fcd98c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('aeeb4b59-ef54-446c-974f-712493b7cdd2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '8e5e0e95-3676-469b-b1a6-7ed541b8cf69', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'dbe89a30-2532-4a1c-9995-e7458a822c7d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f0805eba-8be8-4c19-8a45-d82497a72763', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:32.064', '', 'WMST326-CO-FO', '8bb9ee5b-7de3-4f58-a499-03224bc0686c', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'WMST326-CO-FO', '8e5e0e95-3676-469b-b1a6-7ed541b8cf69', '96f62289-c2a9-44d9-bcff-73a41908d9bd')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('8e5e0e95-3676-469b-b1a6-7ed541b8cf69', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('688462f2-abac-4d78-9d52-e07fe774a9a0', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '90813efb-60bf-4b2d-90fd-1866376bbffa', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 75, 75, 'Lecture', 'Courses with lecture', null, '40146bc5-9a15-4a31-94c7-ab82b0d5df5b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('39be35fb-b8c6-4d54-a6e4-a2b1533cee98', 'kuali.attribute.course.evaluation.indicator', '40146bc5-9a15-4a31-94c7-ab82b0d5df5b', 0, '9ad24572-2719-455d-9d1e-d6ced363ec64')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e5d22b21-e988-403f-b553-9551dcfdcf96', 'kuali.attribute.max.enrollment.is.estimate', '40146bc5-9a15-4a31-94c7-ab82b0d5df5b', 0, '259a0deb-e8c1-4277-8637-611312041620')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0d8b5187-dbe0-4875-955c-eb3f4732d926', 'kuali.attribute.nonstd.ts.indicator', '40146bc5-9a15-4a31-94c7-ab82b0d5df5b', 0, 'a9cdc3d9-8ea0-47fc-8b00-2c2488e84a96')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('e1b74449-fea9-40f4-a3c1-9b93732f5bf3', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '40146bc5-9a15-4a31-94c7-ab82b0d5df5b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '005da532-e44b-45da-b50e-ddffea7bc320')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('e415e340-6f11-4cc7-9279-9a731ca41f4d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '40146bc5-9a15-4a31-94c7-ab82b0d5df5b', '', 'kuali.lu.code.honorsOffering', 0, '1105cdb2-1557-46d6-a13f-2b0f78af4127')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('27242eef-8312-49d7-9d65-d5e536ab94d6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:51:32.137', '', '40146bc5-9a15-4a31-94c7-ab82b0d5df5b', 'H.HONGYANZ', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '476ca4e5-425e-4162-994e-b983646d5b4a')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('c2223135-7152-4357-bf34-fd084eadb705', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:32.137', '', 'WMST326-FO-AO', '8e5e0e95-3676-469b-b1a6-7ed541b8cf69', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'WMST326-FO-AO', '40146bc5-9a15-4a31-94c7-ab82b0d5df5b', '6fa9921c-1dc6-4677-8edc-8f92a075ec0d')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('b17829db-625f-4bca-a150-a92d18af5ba5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '9fcda1ad-72a4-443a-b347-60f2e00d35ed')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('199ff43d-56db-46ce-8211-442f48a52c41', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for WMST326 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '9fcda1ad-72a4-443a-b347-60f2e00d35ed', '59cd708a-1376-4e46-a076-e9e5bf81083e')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('79cbfc95-4e01-41d2-8aa9-e0e3250ac35d', 0, '59cd708a-1376-4e46-a076-e9e5bf81083e', '3594a666-f8cf-4604-a0a7-a20c2d8b5017')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('71ab0f59-d662-469f-8fbb-f86c9aa90e8e', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'cbe3eb93-b51f-4a02-8101-3f58dc7e286b')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('9fcda1ad-72a4-443a-b347-60f2e00d35ed', '40146bc5-9a15-4a31-94c7-ab82b0d5df5b')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('3594a666-f8cf-4604-a0a7-a20c2d8b5017', '89929ead-579b-4f51-bf64-2797f963848b')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('3594a666-f8cf-4604-a0a7-a20c2d8b5017', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('3594a666-f8cf-4604-a0a7-a20c2d8b5017', 'b8e731ee-f0c0-4479-8413-1e0cc483071d')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('3594a666-f8cf-4604-a0a7-a20c2d8b5017', 'F08200F105B9315CE040440A9B9A3EDC')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('8902bd5b-e822-41c7-9830-bace2b8f8ccc', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'ebf23893-eb15-4cc3-bdca-5dd7c4903d57')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('a1e4a58f-14fe-422a-b07e-3cf79587adf7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '8e5e0e95-3676-469b-b1a6-7ed541b8cf69', 'CL 1', 'CL 1', 'f6727d84-4941-4d44-9873-c55530cd7896')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('e5f1fb04-9be7-4c48-beda-482bfb43b4f6', 'kuali.lui.type.activity.offering.lecture', 'f6727d84-4941-4d44-9873-c55530cd7896', '9c6ff3c7-9458-45d4-8f6c-79146edf1284')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='71ab0f59-d662-469f-8fbb-f86c9aa90e8e', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='40146bc5-9a15-4a31-94c7-ab82b0d5df5b', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='cbe3eb93-b51f-4a02-8101-3f58dc7e286b' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('ebf23893-eb15-4cc3-bdca-5dd7c4903d57', '40146bc5-9a15-4a31-94c7-ab82b0d5df5b')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('ebf23893-eb15-4cc3-bdca-5dd7c4903d57', '8e5e0e95-3676-469b-b1a6-7ed541b8cf69')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('9c6ff3c7-9458-45d4-8f6c-79146edf1284', '40146bc5-9a15-4a31-94c7-ab82b0d5df5b')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='f6727d84-4941-4d44-9873-c55530cd7896' where ID='9c6ff3c7-9458-45d4-8f6c-79146edf1284'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d36c7502-c950-41dd-aa36-f91960ad52ac', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '365c3c5f-8422-4b70-8bd7-949a708229d8', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'f9cc8518-8a01-408a-9d56-78e48ca9653d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d72f96a7-8c51-45f3-849f-25a7a8f42646', 'kuali.attribute.registration.group.is.generated', 'f9cc8518-8a01-408a-9d56-78e48ca9653d', 1, '1ddc878e-4195-46c5-bff2-3fef080ab95d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c6a64cdc-9b7b-453f-9248-e54613a40820', 'kuali.attribute.registration.group.aocluster.id', 'f9cc8518-8a01-408a-9d56-78e48ca9653d', 'f6727d84-4941-4d44-9873-c55530cd7896', 'ad75eb9a-d051-4290-92a2-259e2ab8222a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('1f95631f-eca0-491c-8538-1e0731d62cbf', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'f9cc8518-8a01-408a-9d56-78e48ca9653d', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '9937b737-2164-4538-a164-dcb700e5989f')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('232c1835-d9c8-4fa2-989a-58d43c06ebad', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:32.549', '', 'WMST326-FO-RG', '8e5e0e95-3676-469b-b1a6-7ed541b8cf69', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'WMST326-FO-RG', 'f9cc8518-8a01-408a-9d56-78e48ca9653d', '2d72d27a-868b-4c31-a3d0-b21e1c6d3235')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('0b80f573-09d8-4a68-8fbb-1a18d8f085b7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:32.549', '', 'WMST326-RG-AO', 'f9cc8518-8a01-408a-9d56-78e48ca9653d', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'WMST326-RG-AO', '40146bc5-9a15-4a31-94c7-ab82b0d5df5b', '5e2449fc-99e7-4d6b-a166-c15847483809')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('7d3fe372-e2c9-4dff-aaf4-f83010ef72e2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'd1c291df-38c6-4bb7-9c62-fec470897c8d', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Workshops will cover all aspects of giving scientific presentations. Each student will give a presentation based oon the topic of his/her final paper in CHEM611. Presentations will be critiqued by peers and faculty members.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'CHEM612 CO', 'Workshops will cover all aspects of giving scientific presentations. Each student will give a presentation based oon the topic of his/her final paper in CHEM611. Presentations will be critiqued by peers and faculty members.', null, 'd98165d6-5041-46d4-8454-70deb50ba635')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('887777c7-dc8d-48f5-bbc4-fdfd1373c47f', 'kuali.attribute.wait.list.indicator', 'd98165d6-5041-46d4-8454-70deb50ba635', 1, '4f6485d8-58e7-4532-945c-eddd405a29af')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('82c34b13-b989-466e-9303-0a05e0a5063d', 'kuali.attribute.final.exam.indicator', 'd98165d6-5041-46d4-8454-70deb50ba635', 'STANDARD', '514a1726-9d00-4899-8443-1066d183ed7b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('03321356-c928-496c-9a07-bae78abf4c56', 'kuali.attribute.wait.list.level.type.key', 'd98165d6-5041-46d4-8454-70deb50ba635', 'AO', '2c059a4e-c77c-4e2b-8c2e-89d4775d1483')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('672961e6-909a-4866-8ead-640afd19585c', 'kuali.attribute.wait.list.type.key', 'd98165d6-5041-46d4-8454-70deb50ba635', null, '202b5fa9-1a83-4978-a7f7-c21ef6eaf615')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c7b1858d-1313-4b73-b2b3-4d28aedd5b2a', 'kuali.attribute.course.evaluation.indicator', 'd98165d6-5041-46d4-8454-70deb50ba635', 0, '56bd2faf-40cd-472f-83c6-5d3c41d48b01')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('620780e3-807f-425f-84d3-21b8ece808b7', 'kuali.attribute.funding.source', 'd98165d6-5041-46d4-8454-70deb50ba635', '', '1d936421-7ee5-4089-b9fe-7c319f171db5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6b0c555c-d18b-47ed-b156-9efdb82cbfd2', 'kuali.attribute.final.exam.driver', 'd98165d6-5041-46d4-8454-70deb50ba635', 'kuali.lu.exam.driver.ActivityOffering', 'dc801852-e1e9-4d46-8920-229ccbb1c63d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7b4b01fd-2628-4f70-99ce-6667718d8075', 'kuali.attribute.where.fees.attached.flag', 'd98165d6-5041-46d4-8454-70deb50ba635', 0, 'a0cf92ad-5bc4-4312-aac9-2adede32ea09')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('43450d6a-c9be-4a11-9a63-f95868e6ebeb', 'kuali.attribute.final.exam.use.matrix', 'd98165d6-5041-46d4-8454-70deb50ba635', 1, '98229708-fa7b-4770-bbe2-58848564a1bd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7ae2e201-7906-4945-a015-6a3e37aedee3', 'kuali.attribute.course.number.internal.suffix', 'd98165d6-5041-46d4-8454-70deb50ba635', 'A', '5296cefa-74c4-4626-abf0-fcf4aab7a933')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c6e4c7c6-da69-4c83-a7b7-17fbad665e0e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'CHEM612', 'CHEM', '', 'Scientific Presentations', 'd98165d6-5041-46d4-8454-70deb50ba635', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '1169f2f3-c21f-477c-8081-ca0240d18a23')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('c44a8f38-b25e-4697-b67a-ebef3a88981b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'd98165d6-5041-46d4-8454-70deb50ba635', '', 'kuali.lu.code.honorsOffering', '', '6afbc2a9-0ad6-4e96-b4f9-a51319f20e99')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('d98165d6-5041-46d4-8454-70deb50ba635', '4284516367', 'aa86fe8c-f030-44a3-9d57-5740330fde5d')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('d98165d6-5041-46d4-8454-70deb50ba635', '4284516367')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('d98165d6-5041-46d4-8454-70deb50ba635', 'kuali.resultComponent.grade.satisfactory')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('d98165d6-5041-46d4-8454-70deb50ba635', 'kuali.creditType.credit.degree.1.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('1a634db7-43c0-4338-ac8d-43f53e31de46', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '76f029e8-5b80-46ae-b984-6e770aa6ff6a', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'faa016f4-ff31-49d7-b477-6d3017a022dd')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e9b5b492-e26e-4bf3-92c0-3e032bee1077', 'kuali.attribute.final.exam.level.type', 'faa016f4-ff31-49d7-b477-6d3017a022dd', 'kuali.lu.type.activity.Lecture', 'de083df3-9b61-45ba-935c-98455876f9ae')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d1dc0228-5d45-49b6-8550-4ce343304439', 'regCodePrefix', 'faa016f4-ff31-49d7-b477-6d3017a022dd', '1', '9dadf49c-8ab1-4984-a050-de02d20eb197')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e06b1a31-915a-4815-a916-3b93b88d2878', 'kuali.attribute.grade.roster.level.type.key', 'faa016f4-ff31-49d7-b477-6d3017a022dd', 'kuali.lui.type.activity.offering.lecture', '37e808b3-a60e-4550-872d-43bbc5a1ead1')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('b3c2034d-f63a-4316-813a-b937b02f4bad', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', 'faa016f4-ff31-49d7-b477-6d3017a022dd', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a61ffb2e-645d-41f5-825a-ec680a196d9b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('4293ea1f-d438-4be1-9d08-300d299b5f73', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:35.09', '', 'CHEM612-CO-FO', 'd98165d6-5041-46d4-8454-70deb50ba635', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'CHEM612-CO-FO', 'faa016f4-ff31-49d7-b477-6d3017a022dd', 'cc011b7e-5ec7-4683-bdcd-eb8daaf2fbbd')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('faa016f4-ff31-49d7-b477-6d3017a022dd', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('47917f46-3b8d-4b71-8c85-f3686d03bf70', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'ee17574c-1834-4c17-bf27-85d0da160d61', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, 'f4efb84f-5f20-45cd-a0e5-7e504518c63d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f4399551-b4bf-4d08-a34c-3c01bb02e635', 'kuali.attribute.course.evaluation.indicator', 'f4efb84f-5f20-45cd-a0e5-7e504518c63d', 0, '4a77b2be-60c2-41c1-b13d-e278a7fb11d5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7d482e26-389b-4f96-bb69-30a7af6d8c60', 'kuali.attribute.nonstd.ts.indicator', 'f4efb84f-5f20-45cd-a0e5-7e504518c63d', 0, '1f87da6e-35d8-4663-8831-9e79c9bc0926')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bc4fa628-0039-40f7-a2bc-727fa286a1a7', 'kuali.attribute.max.enrollment.is.estimate', 'f4efb84f-5f20-45cd-a0e5-7e504518c63d', 0, 'c920ec57-81d7-4a4a-a03a-61d06b5a86bb')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('3c4572d6-9764-48c1-bc1e-0c30daca5728', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', 'f4efb84f-5f20-45cd-a0e5-7e504518c63d', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '05851203-d3f9-4b45-ad4c-73412d6ce00b')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('bd1056d9-7061-4eee-b1b0-a7efdc2742d1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'f4efb84f-5f20-45cd-a0e5-7e504518c63d', '', 'kuali.lu.code.honorsOffering', 0, '50079a53-d201-4830-9f87-e871e9343ae0')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('7bbba109-7b8c-4a55-98da-66945e8078fc', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:51:35.172', '', 'f4efb84f-5f20-45cd-a0e5-7e504518c63d', 'B.JAMESJ', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '2c370e4c-e7d1-4634-8113-153f9b2d60fd')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('45e840a6-8fdb-45a8-baa7-599353e3c0ec', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:35.172', '', 'CHEM612-FO-AO', 'faa016f4-ff31-49d7-b477-6d3017a022dd', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM612-FO-AO', 'f4efb84f-5f20-45cd-a0e5-7e504518c63d', '09d15ec0-a036-45d0-8d15-30c8463ca190')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('4f4914a1-d0ab-4f0a-a988-ccecff83780b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '82c2d4cc-b2a0-41b4-8e92-e9f3feaf52e2')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('f06a4e26-caec-4107-9df1-9f381d4dfc9a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for CHEM612 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '82c2d4cc-b2a0-41b4-8e92-e9f3feaf52e2', 'f898b742-8b8a-425f-8263-3c22834b136f')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('88b45186-4889-4de0-ab61-28834540ef3e', 0, 'f898b742-8b8a-425f-8263-3c22834b136f', '8a999a90-e88d-455c-b036-c73ba6df422e')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('a21a9c3c-acbd-4660-9d4d-aea9231e77c4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '1c5f91b4-ba64-4061-86c2-9584b8a23727')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('b697512f-c3e1-453c-8ddc-af8905d11664', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'faa016f4-ff31-49d7-b477-6d3017a022dd', 'CL 1', 'CL 1', 'f07c3b67-6542-4910-90db-ef2e9b0546ac')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('8f8e8667-14c3-42bf-856c-3a775ad02355', 'kuali.lui.type.activity.offering.lecture', 'f07c3b67-6542-4910-90db-ef2e9b0546ac', '64641f86-28e6-40f3-9d46-827847349f95')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('82c2d4cc-b2a0-41b4-8e92-e9f3feaf52e2', 'f4efb84f-5f20-45cd-a0e5-7e504518c63d')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('8a999a90-e88d-455c-b036-c73ba6df422e', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('8a999a90-e88d-455c-b036-c73ba6df422e', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('8a999a90-e88d-455c-b036-c73ba6df422e', '2246d609-9fbb-4d06-b45e-a57ef80539b7')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('8a999a90-e88d-455c-b036-c73ba6df422e', 'F08200F10532315CE040440A9B9A3EDC')
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('1c5f91b4-ba64-4061-86c2-9584b8a23727', 'f4efb84f-5f20-45cd-a0e5-7e504518c63d')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('1c5f91b4-ba64-4061-86c2-9584b8a23727', 'faa016f4-ff31-49d7-b477-6d3017a022dd')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('64641f86-28e6-40f3-9d46-827847349f95', 'f4efb84f-5f20-45cd-a0e5-7e504518c63d')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='f07c3b67-6542-4910-90db-ef2e9b0546ac' where ID='64641f86-28e6-40f3-9d46-827847349f95'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d2b1b92e-ac52-4a69-8d31-00690cf7d4d2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '76f029e8-5b80-46ae-b984-6e770aa6ff6a', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'e05d7eaf-51ef-4e75-80a0-f5081be083f3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a34e14eb-6d91-4f6f-aea7-fd5664e7949a', 'kuali.attribute.registration.group.aocluster.id', 'e05d7eaf-51ef-4e75-80a0-f5081be083f3', 'f07c3b67-6542-4910-90db-ef2e9b0546ac', '132c4f87-ec78-4b8c-82d0-d7c57adf1661')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d6378b61-b265-419c-9121-2bc546dbd2fe', 'kuali.attribute.registration.group.is.generated', 'e05d7eaf-51ef-4e75-80a0-f5081be083f3', 1, '17191065-19ee-4282-8e3e-5c63d005222f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('e717ab58-0928-4163-b2ae-293a9cc7a39b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'e05d7eaf-51ef-4e75-80a0-f5081be083f3', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'd59edea3-d22e-4ca5-9176-80938f2e46c3')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('a03121c1-7b3d-43cb-9090-6286b31f8a8f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:35.659', '', 'CHEM612-FO-RG', 'faa016f4-ff31-49d7-b477-6d3017a022dd', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM612-FO-RG', 'e05d7eaf-51ef-4e75-80a0-f5081be083f3', '05cc2786-2f0f-4f0f-a3e6-9e5bfe7d4a40')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('cc2a1758-71c5-4fb9-a5bc-664f0f935bcc', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:35.659', '', 'CHEM612-RG-AO', 'e05d7eaf-51ef-4e75-80a0-f5081be083f3', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM612-RG-AO', 'f4efb84f-5f20-45cd-a0e5-7e504518c63d', 'fe6fdbe5-2380-454b-933e-2baccb428abb')
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='aa22addb-51dc-4b4b-9ab6-19af629aa47d', ATTR_KEY='kuali.soc.rollover.result.dynattr.date.completed', OWNER_ID='8eb89914-1b01-4d50-af48-dab273b4554d', ATTR_VALUE='2014 Jan 22 12:51:35 America/New_York' where ID='401f9b67-014c-4a31-b85e-67ae4de802be'
/
update KSEN_SOC_ROR set OBJ_ID='086ca850-8a66-4720-b0d4-cf7f5955aab1', VER_NBR=6, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ITEMS_EXPECTED=48, ITEMS_PROCESSED=40, MESG_FORMATTED='', MESG_PLAIN='', SOC_ROR_STATE='kuali.soc.rollover.state.running', SOC_ROR_TYPE='kuali.soc.rollover.results.type.rollover', SOURCE_SOC_ID='03192a24-765a-4b9d-b638-5158430e09c8', TARGET_SOC_ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', TARGET_TERM_ID='kuali.atp.2014Winter' where ID='8eb89914-1b01-4d50-af48-dab273b4554d' and VER_NBR=5
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('5975d1dc-677c-4b2a-87a7-10649a8879a6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'a3051367-d387-4e58-92cd-6409dd8b83ab', '0d94e955-7a01-4bb5-9f1c-59ec6afda32c', '40d4099d-ea6e-4f1e-9292-cd5fac2d482a')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('421847fb-5b9c-4833-bef6-93cb987b286f', 'activityOfferingsCreated', '40d4099d-ea6e-4f1e-9292-cd5fac2d482a', '1', 'b40e16e1-516b-431d-b86c-b1aae92177a1')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('ef5927c2-4a54-4a40-9296-ea51b9bcb57d', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'a832e862-04bc-4924-8789-ffb5be5242f5', '', '3a4ba1ff-064e-4e7d-b0bb-b98efc06a26d')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('0dab544f-e431-4c4f-bc03-13fbabd431c8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'aa2a3f73-98d9-4131-9a3d-df5663792212', 'f8b1c348-8b1f-46be-b2b0-29735667e8e2', 'd4a54691-1a87-4016-a89c-e3caa5ee5c52')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('91f36da7-5f47-4c0d-a75d-cac33987ad1c', 'activityOfferingsCreated', 'd4a54691-1a87-4016-a89c-e3caa5ee5c52', '1', '697cab37-cb24-41bd-9a1d-9fb09750dc4e')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('c4ddc7ac-4c28-4d3a-b971-9d7649510492', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'b2123e45-d226-4b55-9779-8b1666d2eb58', '', 'eff824f8-e3a6-49b1-88ee-708d9cfb6cdc')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('3eb69b0f-0eeb-4baf-ab7c-fbce1f3d2ad8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'b2610a4f-953d-48e7-9aa3-7415dd032e73', '', '95b51e49-6394-4df9-bb92-b0094fcf2044')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('4dbe7a0b-281f-458f-bae4-41f1ea0cde67', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'c995d3e9-ba68-4f87-b9c2-62214e192781', '', 'c29abbbe-9040-49fa-8aa2-74f5a9a0fb93')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('b38a50cc-4b8a-464a-ab13-9454671f76fe', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'c7974cf4-5d32-4af2-a8e3-a9727cf04960', '', '9681db7f-ecae-441e-a863-8581643578a6')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('49865df7-cd34-431b-91a1-71403b65d6c7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'cc8d76a8-448e-493c-904f-0924319a3818', '8bb9ee5b-7de3-4f58-a499-03224bc0686c', 'af86c0df-8574-4f20-8951-8df6d5200078')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3a80ebfb-3282-4ae8-9936-aabaabd07bbd', 'activityOfferingsCreated', 'af86c0df-8574-4f20-8951-8df6d5200078', '1', '32c1e483-de1b-4827-9dc0-701ae71f4e59')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('7818f601-f3b3-491e-af4c-14228a36e133', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'cc3f9686-4ff8-4521-a1cc-0d2031ebed8e', '', '9d5c187c-37de-4f64-8ebb-e9e2186fad91')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('6dac4c73-0a7e-411f-b369-2883631bcc4f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'd8bb13c5-c97f-4df3-97d2-5e945631129c', 'd98165d6-5041-46d4-8454-70deb50ba635', '11280001-63ef-4d52-8237-79be61f285bb')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('63eb8c78-9a03-4ac5-9742-49a3bab5a8ba', 'activityOfferingsCreated', '11280001-63ef-4d52-8237-79be61f285bb', '1', '43bf2b76-919c-4018-82af-3afc829abe55')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('8a877262-4b8d-4039-bbd9-eb0ed286227f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'CLUID-BSCI379-199908000000', TIMESTAMP '2014-01-01 19:00:00.0', '', 'This course is arranged to provide qualified majors an opportunity to pursue research problems under the supervision of a member of the department.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'BSCI379G CO', 'This course is arranged to provide qualified majors an opportunity to pursue research problems under the supervision of a member of the department.', null, 'e058cdb8-ce0d-4223-896b-a9ccd8e978d2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('02713ae2-9bb8-4a84-99a1-dd2384e5a0c9', 'kuali.attribute.funding.source', 'e058cdb8-ce0d-4223-896b-a9ccd8e978d2', '', 'f0cc473d-77eb-494b-bde3-aea8a81e8865')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ae034209-7a15-416f-8345-32f72599b983', 'kuali.attribute.course.number.internal.suffix', 'e058cdb8-ce0d-4223-896b-a9ccd8e978d2', 'A', 'c65add90-5b68-4d0c-9d6c-8c54671e8f8a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8253cd37-2a0d-4b96-927d-b604cd3bba97', 'kuali.attribute.final.exam.indicator', 'e058cdb8-ce0d-4223-896b-a9ccd8e978d2', 'STANDARD', 'cdda26d1-416c-4e4a-baa2-58fd5e7583fe')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dba9c6bc-b5f9-402d-ab2d-06b04480de7f', 'kuali.attribute.wait.list.type.key', 'e058cdb8-ce0d-4223-896b-a9ccd8e978d2', null, '18ff57af-16ea-4a71-885f-c5a00dd245d9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2e556392-e237-4c12-81f4-13ed0db94206', 'kuali.attribute.final.exam.driver', 'e058cdb8-ce0d-4223-896b-a9ccd8e978d2', 'kuali.lu.exam.driver.ActivityOffering', '13b3a523-a329-40aa-83ea-62a7703f2078')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c2fcea73-99c1-4f31-b069-982269429382', 'kuali.attribute.final.exam.use.matrix', 'e058cdb8-ce0d-4223-896b-a9ccd8e978d2', 1, 'd6213e6b-f8eb-4f94-88cc-dd232f0dba3e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0a35b6a2-ac84-4566-914f-e61a1b66109d', 'kuali.attribute.wait.list.indicator', 'e058cdb8-ce0d-4223-896b-a9ccd8e978d2', 1, 'eac88404-91fe-45c6-8f05-1b3a65d334b5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b28e8608-5297-4e43-b714-7ad851bbba64', 'kuali.attribute.wait.list.level.type.key', 'e058cdb8-ce0d-4223-896b-a9ccd8e978d2', 'AO', '0554dee1-21c1-4d88-b9d8-1bf5ab5a696f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7568f590-a286-4153-a2e4-3009c6c3b9ed', 'kuali.attribute.where.fees.attached.flag', 'e058cdb8-ce0d-4223-896b-a9ccd8e978d2', 0, '23e02f90-c294-4371-8645-a092befaefad')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('27eb41fb-8223-4a67-99ad-9542850189db', 'kuali.attribute.course.evaluation.indicator', 'e058cdb8-ce0d-4223-896b-a9ccd8e978d2', 0, '33409a71-89c8-473b-8b17-edeb5f527a26')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c8249bc7-e8d3-46a0-8065-dd7a49442eae', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'BSCI379G', 'BSCI', '', 'Cell Biology and Molecular Genetics Department Research', 'e058cdb8-ce0d-4223-896b-a9ccd8e978d2', '', '', 'kuali.lui.identifier.state.active', 'G', 'kuali.lui.identifier.type.official', '', 'd07d4738-656f-45af-909b-1b559cb657a4')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('f7e4fae6-7f0f-4e8d-94aa-d65d97733828', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'e058cdb8-ce0d-4223-896b-a9ccd8e978d2', '', 'kuali.lu.code.honorsOffering', '', 'f10c6197-e729-426b-904e-a7663c920eb8')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('e058cdb8-ce0d-4223-896b-a9ccd8e978d2', '576639460', '84e23fe5-fb0a-4f65-a23f-9df78749c273')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('e058cdb8-ce0d-4223-896b-a9ccd8e978d2', '576639460')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e058cdb8-ce0d-4223-896b-a9ccd8e978d2', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e058cdb8-ce0d-4223-896b-a9ccd8e978d2', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e058cdb8-ce0d-4223-896b-a9ccd8e978d2', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e058cdb8-ce0d-4223-896b-a9ccd8e978d2', 'kuali.creditType.credit.degree.1.0,1.5,2.0,2.5,3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('cff691d8-31b7-4697-b7d5-19ef25d19d88', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '4838b255-888b-4946-b32a-b168313b5e7f', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '7d8308a9-cd2f-41cb-9f26-0ac4fe55231b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8f8ee73c-4b0f-42a2-940a-8331c58759e2', 'regCodePrefix', '7d8308a9-cd2f-41cb-9f26-0ac4fe55231b', '1', '4416e5d6-37de-4b37-abe0-59dde0a0ae04')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('df7b3f29-5ee4-43a1-9b73-6de4fe19b7c1', 'kuali.attribute.grade.roster.level.type.key', '7d8308a9-cd2f-41cb-9f26-0ac4fe55231b', 'kuali.lui.type.activity.offering.lecture', 'bcdeb3e5-98ec-4b16-b778-e24cd6e6e0ab')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8afee22b-47cb-4794-a670-1e33574efc50', 'kuali.attribute.final.exam.level.type', '7d8308a9-cd2f-41cb-9f26-0ac4fe55231b', 'kuali.lu.type.activity.Lecture', 'c41a88a0-6e53-45bb-b478-dca95cdd2dc0')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('f73dd1a2-ed84-4d22-bee3-6e0ba83dbd58', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '7d8308a9-cd2f-41cb-9f26-0ac4fe55231b', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '10cce986-57a2-4d8b-a823-eb658411c865')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('0dff9ded-d886-48cd-9a62-1ee5a61f0949', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:37.584', '', 'BSCI379G-CO-FO', 'e058cdb8-ce0d-4223-896b-a9ccd8e978d2', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'BSCI379G-CO-FO', '7d8308a9-cd2f-41cb-9f26-0ac4fe55231b', '9066ff4e-0ca9-4509-a029-8d9032495441')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('7d8308a9-cd2f-41cb-9f26-0ac4fe55231b', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('62ab12b4-f77c-495e-b825-1564a3a49a70', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'f0ce3b8b-062f-4fd0-91d0-bf99659d3b2c', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 5, 5, 'Lecture', 'Courses with lecture', null, '0c6afdf2-62d4-4be9-9087-2b0d4f3a75df')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8218775a-ba6f-4e1a-b2b8-06569aa644b6', 'kuali.attribute.max.enrollment.is.estimate', '0c6afdf2-62d4-4be9-9087-2b0d4f3a75df', 0, '57c4bd39-0152-4f34-80d5-c47c7933fc63')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0abfe634-c524-43c7-8392-f0c4ada278bf', 'kuali.attribute.course.evaluation.indicator', '0c6afdf2-62d4-4be9-9087-2b0d4f3a75df', 0, '07429a12-0f4f-4b69-a66b-6f688c169f4b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0c4879bc-9b87-4228-a4c9-d73b369ac0da', 'kuali.attribute.nonstd.ts.indicator', '0c6afdf2-62d4-4be9-9087-2b0d4f3a75df', 0, '3b9da151-0278-4b7c-9480-623c7a4bfeb2')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('759a0bb3-3108-449c-93c2-12826011a046', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '0c6afdf2-62d4-4be9-9087-2b0d4f3a75df', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '4f083c9e-b786-4a64-aa7a-904773bda133')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('412d671a-af9e-4b39-b79f-a8c41ffe3827', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '0c6afdf2-62d4-4be9-9087-2b0d4f3a75df', '', 'kuali.lu.code.honorsOffering', 0, 'b0d9705d-9e13-4a22-890b-44f5c02cc76e')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('5c306f74-6b5d-40ae-b27b-51f303f3f991', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:51:37.689', '', '0c6afdf2-62d4-4be9-9087-2b0d4f3a75df', 'C.SANDRAS', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '4afda796-bda3-4fb2-ba7f-58460065aa60')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('2e4a55f2-1fee-4f3c-8d30-bd76da324558', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:37.689', '', 'BSCI379G-FO-AO', '7d8308a9-cd2f-41cb-9f26-0ac4fe55231b', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'BSCI379G-FO-AO', '0c6afdf2-62d4-4be9-9087-2b0d4f3a75df', 'aa0e3969-aa6e-4570-9b62-c945a524926d')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('9e4b1872-07ab-4f22-b344-87b9276ce0bb', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '86d4e9ad-0955-4a06-b32a-e719529446f7')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('2b263895-0a50-4a5a-9c85-b8ef577ddb90', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for BSCI379G - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '86d4e9ad-0955-4a06-b32a-e719529446f7', 'ba11a58c-68e7-4184-b3a0-61859c59e55d')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('c9bee21f-c814-4e45-b9cd-1a534dbe0946', 1, 'ba11a58c-68e7-4184-b3a0-61859c59e55d', 'd5119004-188f-4e40-9f90-015140a8abaf')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('27abce3b-e97f-4551-8a6b-1a0e0915978e', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', 'cad6d6da-07b0-4f1c-9f1f-2513c5093986')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('86d4e9ad-0955-4a06-b32a-e719529446f7', '0c6afdf2-62d4-4be9-9087-2b0d4f3a75df')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('d5119004-188f-4e40-9f90-015140a8abaf', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('b6717978-8b3d-4dad-9e18-909f1b2ab474', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', 'b6f4f625-7d3a-4bed-b026-47d353c92804')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('5046e1e4-ebc2-4650-9b1f-32bf5f97bb53', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '7d8308a9-cd2f-41cb-9f26-0ac4fe55231b', 'CL 1', 'CL 1', 'fc903e6e-edd5-4acc-91b7-1890a215d84a')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('4436485e-c4b3-4daf-bb65-5ea18d5682be', 'kuali.lui.type.activity.offering.lecture', 'fc903e6e-edd5-4acc-91b7-1890a215d84a', 'a34e0e58-9366-4f2e-9496-c5721947ceba')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='27abce3b-e97f-4551-8a6b-1a0e0915978e', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='0c6afdf2-62d4-4be9-9087-2b0d4f3a75df', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='cad6d6da-07b0-4f1c-9f1f-2513c5093986' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('b6f4f625-7d3a-4bed-b026-47d353c92804', '0c6afdf2-62d4-4be9-9087-2b0d4f3a75df')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('b6f4f625-7d3a-4bed-b026-47d353c92804', '7d8308a9-cd2f-41cb-9f26-0ac4fe55231b')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('a34e0e58-9366-4f2e-9496-c5721947ceba', '0c6afdf2-62d4-4be9-9087-2b0d4f3a75df')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='fc903e6e-edd5-4acc-91b7-1890a215d84a' where ID='a34e0e58-9366-4f2e-9496-c5721947ceba'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('bf3c79bd-0d5b-427d-bfc9-15fdfca883f3', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '4838b255-888b-4946-b32a-b168313b5e7f', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '39b11f29-1ec5-4605-9515-d2e58c48ed93')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a7b2c8bf-bab3-42ff-9deb-a50cc52fe3a2', 'kuali.attribute.registration.group.is.generated', '39b11f29-1ec5-4605-9515-d2e58c48ed93', 1, '3900c54f-e813-479e-932d-3d16abb82bd7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8bae7526-1183-4fa6-86bb-df144db89c11', 'kuali.attribute.registration.group.aocluster.id', '39b11f29-1ec5-4605-9515-d2e58c48ed93', 'fc903e6e-edd5-4acc-91b7-1890a215d84a', '39ea15d9-cecc-4b50-98a9-b19179756aee')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('04ad7fb8-0904-49e8-927c-c06b1c5ffde0', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', '39b11f29-1ec5-4605-9515-d2e58c48ed93', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '21ce8d99-19b0-4e1a-aeaf-e7ec90d62a1b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('72c916f7-d6ef-4283-8a12-500a2531c4ab', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:38.104', '', 'BSCI379G-FO-RG', '7d8308a9-cd2f-41cb-9f26-0ac4fe55231b', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'BSCI379G-FO-RG', '39b11f29-1ec5-4605-9515-d2e58c48ed93', '7f07302e-b521-4dc7-ab82-2dbb48e4962b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('f73f9941-8bb4-46dd-801e-a0210901d1b8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:38.104', '', 'BSCI379G-RG-AO', '39b11f29-1ec5-4605-9515-d2e58c48ed93', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'BSCI379G-RG-AO', '0c6afdf2-62d4-4be9-9087-2b0d4f3a75df', '9acb1617-8a39-4230-9ac7-2c4e0452e5a5')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('d9af8fc3-2362-4da8-8e1b-5754543a7f09', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '9f1f9e6e-e87e-4646-b25f-723b313339f9', TIMESTAMP '2014-01-01 19:00:00.0', '', 'A history of religion, religious movements, and churches in America from the early Colonial period to the present, with special attention to the relation between church and society.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST306 CO', 'A history of religion, religious movements, and churches in America from the early Colonial period to the present, with special attention to the relation between church and society.', null, '8af41e78-075c-4766-aba7-934a2bdf3543')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ba013713-92f1-49be-a93b-bdf9d37c7f8d', 'kuali.attribute.funding.source', '8af41e78-075c-4766-aba7-934a2bdf3543', '', 'd55c012c-c7e2-4c23-b967-cd6a0cab2024')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1a29c88f-3de7-4bfb-95eb-2a0c9cc627c2', 'kuali.attribute.final.exam.indicator', '8af41e78-075c-4766-aba7-934a2bdf3543', 'STANDARD', '3f0519e2-bf23-4066-a34c-ae803a56595e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('595b7c07-fb63-40e8-a756-256c653c3e96', 'kuali.attribute.course.number.internal.suffix', '8af41e78-075c-4766-aba7-934a2bdf3543', 'A', '5cc9ba17-0476-43f0-94b9-77ab4e88ad6c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cee457ef-e68d-4122-a9a4-e835f3bf5cbc', 'kuali.attribute.wait.list.indicator', '8af41e78-075c-4766-aba7-934a2bdf3543', 1, 'cc283d86-cd4f-4193-86ca-b878caa428ae')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a5d6b13d-57c2-48eb-aadc-5820b5c01ea7', 'kuali.attribute.where.fees.attached.flag', '8af41e78-075c-4766-aba7-934a2bdf3543', 0, '6d7dfa3e-f66e-4ed6-a608-f2321406029e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('212e238a-a927-4fce-b19e-e54b0e4d3b2a', 'kuali.attribute.final.exam.use.matrix', '8af41e78-075c-4766-aba7-934a2bdf3543', 1, '65b71b09-0145-418f-a7f0-5ac205989171')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ab95c5b9-0369-4483-a7cc-7aac48692c75', 'kuali.attribute.wait.list.level.type.key', '8af41e78-075c-4766-aba7-934a2bdf3543', 'AO', '418aab5e-ead4-4ab8-a47c-a2ecce089ddb')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bbf384dd-7af5-4bd7-865d-1e859b5fa51b', 'kuali.attribute.final.exam.driver', '8af41e78-075c-4766-aba7-934a2bdf3543', 'kuali.lu.exam.driver.ActivityOffering', '774a9f63-bf8b-4624-b759-1852aaaa91ae')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9d638fc4-56fd-4f05-9e76-c90c3112a342', 'kuali.attribute.course.evaluation.indicator', '8af41e78-075c-4766-aba7-934a2bdf3543', 0, '38cd38da-b4ce-4930-a8c9-24f2455ce818')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bd50d902-f001-4cad-ad0d-0979802bfdbd', 'kuali.attribute.wait.list.type.key', '8af41e78-075c-4766-aba7-934a2bdf3543', null, '6c766201-34c2-4cdd-95d1-44f10a927fd8')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('e87d2992-5f1f-4fc9-a0bb-42c56d911aed', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'HIST306', 'HIST', '', 'History of Religion in America', '8af41e78-075c-4766-aba7-934a2bdf3543', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '0a8ecc8c-5e05-46ee-a823-23eaaf06f5ac')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('65c6420a-412c-4c09-9d44-43d3e7f241a1', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '8af41e78-075c-4766-aba7-934a2bdf3543', '', 'kuali.lu.code.honorsOffering', '', 'b13f0ba5-2606-4452-aa81-5202e3cba98a')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('8af41e78-075c-4766-aba7-934a2bdf3543', '3213375036', '695bae14-3af4-47f2-b0c5-d0d1a5f94c32')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('8af41e78-075c-4766-aba7-934a2bdf3543', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('8af41e78-075c-4766-aba7-934a2bdf3543', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('8af41e78-075c-4766-aba7-934a2bdf3543', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('8af41e78-075c-4766-aba7-934a2bdf3543', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('8af41e78-075c-4766-aba7-934a2bdf3543', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('31ca5ffb-9c11-4ed1-8052-5b2223fc4f1a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'c0a7ccc8-be5f-404f-8e26-7fe95ef917b6', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '33cfb260-c838-4ee0-9ea3-0d27adda1a47')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('501d5254-0600-4753-acaa-8367c90e5e4d', 'kuali.attribute.grade.roster.level.type.key', '33cfb260-c838-4ee0-9ea3-0d27adda1a47', 'kuali.lui.type.activity.offering.lecture', '01f163a3-207d-4c2d-91d5-7c5864ece381')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e872162f-4fd2-4fa4-8f32-b94b77396296', 'kuali.attribute.final.exam.level.type', '33cfb260-c838-4ee0-9ea3-0d27adda1a47', 'kuali.lu.type.activity.Lecture', '2fc4d5e9-da16-4d0a-8cea-3bf90b2d2157')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fb5c1b3c-4638-4c2e-bba6-7700b25bf5d8', 'regCodePrefix', '33cfb260-c838-4ee0-9ea3-0d27adda1a47', '1', '5cbc99ec-7a00-4d01-80d3-4dbe88695952')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('61c2ef82-8700-4144-9521-d59c61e9ef83', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '33cfb260-c838-4ee0-9ea3-0d27adda1a47', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '5b7d149c-e2b8-4911-9c5b-10f61ca3c8da')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('7a54c674-efec-46e9-b998-84c66d9ed1cd', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:39.971', '', 'HIST306-CO-FO', '8af41e78-075c-4766-aba7-934a2bdf3543', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST306-CO-FO', '33cfb260-c838-4ee0-9ea3-0d27adda1a47', 'c98236a4-717f-4a90-aa01-b8ccdca32496')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('33cfb260-c838-4ee0-9ea3-0d27adda1a47', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('9205882d-b259-44e7-af1a-07d2f6872b93', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '9dfb4813-ad36-4549-9292-1375ddc7c8c1', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, '68d7e9ea-c068-4ccc-94cd-b8eae7cf43cf')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('eef67ed8-cdae-4cb0-894c-9b9db1ca8fee', 'kuali.attribute.max.enrollment.is.estimate', '68d7e9ea-c068-4ccc-94cd-b8eae7cf43cf', 0, '64024ba7-0342-4aea-bbcf-023f8600a54b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4038fefb-2930-4d12-b394-8c5c86adb6c9', 'kuali.attribute.course.evaluation.indicator', '68d7e9ea-c068-4ccc-94cd-b8eae7cf43cf', 0, 'd20c2f18-cdf0-46c0-9af0-1762f670db42')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3d399802-2825-4187-b17b-bdbac804ca30', 'kuali.attribute.nonstd.ts.indicator', '68d7e9ea-c068-4ccc-94cd-b8eae7cf43cf', 0, '31b81e35-c0fe-4f09-b852-ba12d634c816')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('edc56e3d-ec66-4321-8e1e-9c92793b0d94', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '68d7e9ea-c068-4ccc-94cd-b8eae7cf43cf', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '2084912d-fc02-40c5-a367-c69b5cf65b3e')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('c4fa7dc4-2d0c-43d5-835e-9f0fb547b084', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '68d7e9ea-c068-4ccc-94cd-b8eae7cf43cf', '', 'kuali.lu.code.honorsOffering', 0, '09c0483a-4132-4f85-953b-e53716ec19bf')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('5a899d2e-a1ee-47bf-bd21-4285ae2cf77c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:51:40.04', '', '68d7e9ea-c068-4ccc-94cd-b8eae7cf43cf', 'G.ERICK', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', 'd708a9ff-f6a1-4348-ab12-af3d7e855418')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('bd015a4b-0a21-4b17-b65b-f6eb9d3a501c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:40.04', '', 'HIST306-FO-AO', '33cfb260-c838-4ee0-9ea3-0d27adda1a47', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST306-FO-AO', '68d7e9ea-c068-4ccc-94cd-b8eae7cf43cf', '4929e16a-956d-4542-b3de-41d29fe5e37e')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('0e25540d-a8cf-4a59-a9dd-a11662bb4b1e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'fb3cd048-3818-41dd-8253-cfd2640d3a7c')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('0dd22717-121d-4e67-81b2-d5dd99ce1f06', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for HIST306 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'fb3cd048-3818-41dd-8253-cfd2640d3a7c', '5c5a5565-289a-4811-9681-34b6be843606')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('56678ee3-39d4-4536-9bdf-e19daf611ec1', 0, '5c5a5565-289a-4811-9681-34b6be843606', '46e5b81b-9f83-4bbd-9ccf-42f83be08504')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('3f31c7d2-b080-468d-8c91-c11da3605821', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '0d53c8b3-620a-47b2-86a6-b93879fec82d')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('fb3cd048-3818-41dd-8253-cfd2640d3a7c', '68d7e9ea-c068-4ccc-94cd-b8eae7cf43cf')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('46e5b81b-9f83-4bbd-9ccf-42f83be08504', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('46e5b81b-9f83-4bbd-9ccf-42f83be08504', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('46e5b81b-9f83-4bbd-9ccf-42f83be08504', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('46e5b81b-9f83-4bbd-9ccf-42f83be08504', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('9ae868a0-1e5e-4e95-95d0-e68c65bc4819', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '685eddfd-e0f3-424e-91c1-3d9ea1ec2540')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('4e31ea69-d75c-4ada-8473-0f98dc4c3034', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '33cfb260-c838-4ee0-9ea3-0d27adda1a47', 'CL 1', 'CL 1', 'd6b954ad-d6f8-452d-b598-e286e85ccc6c')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('24d8ed8d-cfab-4043-829f-965187eb53fe', 'kuali.lui.type.activity.offering.lecture', 'd6b954ad-d6f8-452d-b598-e286e85ccc6c', '6706ed15-c21a-4365-ad91-7785d406f492')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='3f31c7d2-b080-468d-8c91-c11da3605821', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='68d7e9ea-c068-4ccc-94cd-b8eae7cf43cf', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='0d53c8b3-620a-47b2-86a6-b93879fec82d' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('685eddfd-e0f3-424e-91c1-3d9ea1ec2540', '68d7e9ea-c068-4ccc-94cd-b8eae7cf43cf')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('685eddfd-e0f3-424e-91c1-3d9ea1ec2540', '33cfb260-c838-4ee0-9ea3-0d27adda1a47')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('6706ed15-c21a-4365-ad91-7785d406f492', '68d7e9ea-c068-4ccc-94cd-b8eae7cf43cf')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='d6b954ad-d6f8-452d-b598-e286e85ccc6c' where ID='6706ed15-c21a-4365-ad91-7785d406f492'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('349be611-642d-4890-9150-af844b85b565', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'c0a7ccc8-be5f-404f-8e26-7fe95ef917b6', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '6542ed2f-f0ab-4fbe-a425-2a60053f1dff')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('00c28e2c-9273-4e80-886e-c98ac92e0c7b', 'kuali.attribute.registration.group.aocluster.id', '6542ed2f-f0ab-4fbe-a425-2a60053f1dff', 'd6b954ad-d6f8-452d-b598-e286e85ccc6c', '9cc5ebbb-32cf-4fe8-ba68-53f57b6de3a9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ac698ed7-427d-47e3-b38c-08bf04e6e722', 'kuali.attribute.registration.group.is.generated', '6542ed2f-f0ab-4fbe-a425-2a60053f1dff', 1, '29df9830-32a3-4fbc-870e-2a21569961e3')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('f3db6843-d570-4457-8f07-b496b911f1b7', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', '6542ed2f-f0ab-4fbe-a425-2a60053f1dff', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '29202707-8338-4bb0-8d73-7e533c230a39')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('71e8b7e1-e99c-4991-8bcc-d451be0a7867', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:40.638', '', 'HIST306-FO-RG', '33cfb260-c838-4ee0-9ea3-0d27adda1a47', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST306-FO-RG', '6542ed2f-f0ab-4fbe-a425-2a60053f1dff', 'b275ab22-54d7-46ab-89ba-da9c674398d2')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('941ece92-2941-4865-8492-a392b47598d4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:40.638', '', 'HIST306-RG-AO', '6542ed2f-f0ab-4fbe-a425-2a60053f1dff', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST306-RG-AO', '68d7e9ea-c068-4ccc-94cd-b8eae7cf43cf', 'da26d095-743c-4b5f-b4a9-e3f80040ed97')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('4ed81345-a35c-41b7-ba91-8c82ea7680e9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'e9a4e168-0dfe-4e19-a88c-c9b9999e27d9', TIMESTAMP '2014-01-01 19:00:00.0', '', 'Political and literary traditions that intersect in the fiction, poetry, and drama written in English by Caribbean writers, primarily during the 20th century.', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'ENGL362 CO', 'Political and literary traditions that intersect in the fiction, poetry, and drama written in English by Caribbean writers, primarily during the 20th century.', null, 'f550bb7b-c55e-4531-9d6d-fa005b19baad')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('41bac8fc-1bcf-4d46-8f10-5664eb3af9e6', 'kuali.attribute.course.evaluation.indicator', 'f550bb7b-c55e-4531-9d6d-fa005b19baad', 0, '984a89d2-9b94-40ae-9c6d-10da2a95f561')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fefb90ea-153f-4e1d-80d8-2cd3b7903dfe', 'kuali.attribute.wait.list.indicator', 'f550bb7b-c55e-4531-9d6d-fa005b19baad', 1, '1c27cb67-fcf1-4e18-bb3b-5db99a50435f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e1f2a8a8-8912-4eed-865c-894de5950781', 'kuali.attribute.wait.list.type.key', 'f550bb7b-c55e-4531-9d6d-fa005b19baad', null, '8f82a1dd-67d9-4ab6-b009-bd04a02882e3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9f531995-252b-435a-aff0-6a7599c59910', 'kuali.attribute.wait.list.level.type.key', 'f550bb7b-c55e-4531-9d6d-fa005b19baad', 'AO', 'b92ec7aa-7a43-433c-9fd1-84f25ba18a7c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('26eec364-8c7d-4444-a26e-c73a7fe6ad0a', 'kuali.attribute.course.number.internal.suffix', 'f550bb7b-c55e-4531-9d6d-fa005b19baad', 'A', 'c70bb96d-3421-4134-8083-17975b77e44b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3e56f0a2-5e87-460e-8c44-0817ba63dfa6', 'kuali.attribute.funding.source', 'f550bb7b-c55e-4531-9d6d-fa005b19baad', '', '98f2d620-4b7a-40f5-8233-4586daf347b3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e8de5e12-4759-41ce-a68d-5c786925f81d', 'kuali.attribute.where.fees.attached.flag', 'f550bb7b-c55e-4531-9d6d-fa005b19baad', 0, 'd6e611cc-d454-4d3a-8362-673e33ff456f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e2b882d1-33d2-4af8-ae01-b08ed2c366bd', 'kuali.attribute.final.exam.driver', 'f550bb7b-c55e-4531-9d6d-fa005b19baad', 'kuali.lu.exam.driver.ActivityOffering', '5ef9dd06-3040-470e-9172-5c28daea97e4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c5d5d958-0f09-4930-af1a-96b3e0b5cef6', 'kuali.attribute.final.exam.use.matrix', 'f550bb7b-c55e-4531-9d6d-fa005b19baad', 1, 'ce4a9415-5c07-4bea-8c7e-f360a9814636')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e7971e0a-f70c-4b73-b7e7-c0638e902172', 'kuali.attribute.final.exam.indicator', 'f550bb7b-c55e-4531-9d6d-fa005b19baad', 'STANDARD', '180e134e-ee66-4cf0-891e-576c6fc13d36')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c9b44114-fd19-437a-b76a-bb6a9438ebe5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'ENGL362', 'ENGL', '', 'Caribbean Literature in English', 'f550bb7b-c55e-4531-9d6d-fa005b19baad', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '7fa94920-bd12-47f3-acb7-52b34291a8dd')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('6c8b429c-ecce-46a1-a1c6-1d7f9fe145f9', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'f550bb7b-c55e-4531-9d6d-fa005b19baad', '', 'kuali.lu.code.honorsOffering', '', '2da19542-7b52-4b6f-b928-2b829b5e05ab')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('f550bb7b-c55e-4531-9d6d-fa005b19baad', '2677933260', '8413b7ca-93eb-4aa9-86eb-5981f70dc758')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('f550bb7b-c55e-4531-9d6d-fa005b19baad', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('f550bb7b-c55e-4531-9d6d-fa005b19baad', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('f550bb7b-c55e-4531-9d6d-fa005b19baad', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('f550bb7b-c55e-4531-9d6d-fa005b19baad', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('f550bb7b-c55e-4531-9d6d-fa005b19baad', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('215cffec-54d7-45f9-a181-67e4d32eaa64', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'e0246130-0245-420e-8771-ef8b6bebf6b0', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', 'acb0981e-865e-4b50-81aa-d4b36d52971d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d4af0375-3172-4b5e-a312-ed3b573d4cc9', 'kuali.attribute.grade.roster.level.type.key', 'acb0981e-865e-4b50-81aa-d4b36d52971d', 'kuali.lui.type.activity.offering.lecture', '1f0c7cb4-2dc4-4c45-aec5-d3e37201d351')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('83646c0e-f76c-4875-8894-aa739c34c933', 'kuali.attribute.final.exam.level.type', 'acb0981e-865e-4b50-81aa-d4b36d52971d', 'kuali.lu.type.activity.Lecture', '368aa085-99fa-432e-873a-66dd24f5fe2f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('baeaa045-0106-4c72-b66a-0d8dc6f2252a', 'regCodePrefix', 'acb0981e-865e-4b50-81aa-d4b36d52971d', '1', '98c2d456-52ab-45df-8296-2593d585d6a5')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('aecf0b9c-6f44-4c2e-b90b-fa9d2dfcdaf8', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', 'acb0981e-865e-4b50-81aa-d4b36d52971d', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '4d99f491-eae3-4abc-a029-6ef928e0ccfe')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('10aabefb-2de6-4e71-a714-2dee46fe3ca5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:43.604', '', 'ENGL362-CO-FO', 'f550bb7b-c55e-4531-9d6d-fa005b19baad', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL362-CO-FO', 'acb0981e-865e-4b50-81aa-d4b36d52971d', 'b21ca3b2-2839-4b0d-abea-d2170007583f')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('acb0981e-865e-4b50-81aa-d4b36d52971d', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('c127cfc4-8ad8-4b90-86f1-10f4911f9770', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '193c61ab-4faf-403e-a3d8-dbea6af59cc6', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 25, 25, 'Lecture', 'Courses with lecture', null, 'dd55d0cd-1fb9-42f0-be84-fbac30d8f409')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('becc0fba-459b-46b0-add3-d97a61e14ffe', 'kuali.attribute.nonstd.ts.indicator', 'dd55d0cd-1fb9-42f0-be84-fbac30d8f409', 0, 'f577eee2-f715-4034-a0cd-d426532f996a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dada37f6-9492-42e4-a040-c8e9783de918', 'kuali.attribute.course.evaluation.indicator', 'dd55d0cd-1fb9-42f0-be84-fbac30d8f409', 0, '90040813-160a-48c6-b4c1-015eddbdb413')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5fc69297-4602-47dd-981f-05f2cfaa3ec0', 'kuali.attribute.max.enrollment.is.estimate', 'dd55d0cd-1fb9-42f0-be84-fbac30d8f409', 0, '204cde98-6914-47ae-9799-380ce41b0262')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('a5f3f74f-ba20-40ed-a17d-41fde0819da2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', 'dd55d0cd-1fb9-42f0-be84-fbac30d8f409', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'fb518fee-4343-4bbe-b5cf-28c5a75b826e')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('930f0faa-6061-4e17-8133-4433d1168b39', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'dd55d0cd-1fb9-42f0-be84-fbac30d8f409', '', 'kuali.lu.code.honorsOffering', 0, '9e43a2d1-d2f0-4822-8039-3a788f6cc863')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('0f410fea-5446-4c20-ab1f-d17a3b06d55c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:51:43.674', '', 'dd55d0cd-1fb9-42f0-be84-fbac30d8f409', 'M.THEODOREK', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '32cfb531-96d0-4750-b09c-c2f833e2976d')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('380e0cf0-9912-4793-81ec-09ecc6a79844', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:43.674', '', 'ENGL362-FO-AO', 'acb0981e-865e-4b50-81aa-d4b36d52971d', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL362-FO-AO', 'dd55d0cd-1fb9-42f0-be84-fbac30d8f409', '925a27fd-e627-4653-8176-d52c01dfa241')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('429c4b83-e0f1-418b-b79a-b953da1e571c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'e812eb26-070e-41a6-b66a-459f46feba2b')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('c138bdcb-d68c-42b2-bcf5-937dec70609a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'Schedule request for ENGL362 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', 'e812eb26-070e-41a6-b66a-459f46feba2b', '58221042-ff92-4d8d-bd89-97e6d7f8e742')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('1ce7fbad-e4c3-4ced-b281-864c416466c3', 0, '58221042-ff92-4d8d-bd89-97e6d7f8e742', 'ae47cf5f-30cd-408a-9a98-8ec807b8d70a')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('fe2045ad-e8cf-4e1a-a4f3-14f96947fa10', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '300-level Senior Seatpool', '4160a52b-d353-4ab3-b72d-a3c2f9969783', 1, 5, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '44f7d506-c302-4874-93da-e837e1f1da68')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('e812eb26-070e-41a6-b66a-459f46feba2b', 'dd55d0cd-1fb9-42f0-be84-fbac30d8f409')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('ae47cf5f-30cd-408a-9a98-8ec807b8d70a', 'd5138c2b-01ee-4e81-b526-38ed2a7c60a3')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('ae47cf5f-30cd-408a-9a98-8ec807b8d70a', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('ae47cf5f-30cd-408a-9a98-8ec807b8d70a', '6dd715ea-cc9b-4cd1-9a34-f96628650406')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('ae47cf5f-30cd-408a-9a98-8ec807b8d70a', 'F08200F10528315CE040440A9B9A3EDC')
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('4777eb64-46b3-4eb4-bbd3-7f3f5469c9e6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '36da81da-df29-4e83-915b-4f786147be9e')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('03f92146-82c1-434c-891c-15b9b94aa8e5', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'acb0981e-865e-4b50-81aa-d4b36d52971d', 'CL 1', 'CL 1', 'e760d67e-e9a1-4db1-8739-77e91c39c63b')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('32b09590-b21f-4f0d-b4ca-e6032ac3e45e', 'kuali.lui.type.activity.offering.lecture', 'e760d67e-e9a1-4db1-8739-77e91c39c63b', '3248b74f-42d1-4e57-8b72-71fa6a56f9ea')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='fe2045ad-e8cf-4e1a-a4f3-14f96947fa10', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='dd55d0cd-1fb9-42f0-be84-fbac30d8f409', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='300-level Senior Seatpool', POPULATION_ID='4160a52b-d353-4ab3-b72d-a3c2f9969783', PROCESSING_PRIORITY=1, SEAT_LIMIT=5, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='44f7d506-c302-4874-93da-e837e1f1da68' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('36da81da-df29-4e83-915b-4f786147be9e', 'dd55d0cd-1fb9-42f0-be84-fbac30d8f409')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('36da81da-df29-4e83-915b-4f786147be9e', 'acb0981e-865e-4b50-81aa-d4b36d52971d')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('3248b74f-42d1-4e57-8b72-71fa6a56f9ea', 'dd55d0cd-1fb9-42f0-be84-fbac30d8f409')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='e760d67e-e9a1-4db1-8739-77e91c39c63b' where ID='3248b74f-42d1-4e57-8b72-71fa6a56f9ea'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ba14265f-c57d-41b2-8605-ec3045862e17', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'e0246130-0245-420e-8771-ef8b6bebf6b0', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'ca18908b-9983-4507-ae16-bd86edb83598')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3783e2e5-abb9-44c5-88bc-f40a9502fd18', 'kuali.attribute.registration.group.aocluster.id', 'ca18908b-9983-4507-ae16-bd86edb83598', 'e760d67e-e9a1-4db1-8739-77e91c39c63b', '0b6845c2-a0e4-4222-a2bf-b8e1bd4b642a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('80316393-fdfd-40ab-a12d-c987b0e9671e', 'kuali.attribute.registration.group.is.generated', 'ca18908b-9983-4507-ae16-bd86edb83598', 1, '341050c6-cc01-451b-baff-1913807334e7')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('34a20b62-ec2d-4044-853d-92d045cbb1bf', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', 'ca18908b-9983-4507-ae16-bd86edb83598', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '15ffd40c-8921-4c8b-b800-4d3f30cc0bc9')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('5f36a52f-e8c5-42c9-8279-ebf0949789e6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:44.137', '', 'ENGL362-FO-RG', 'acb0981e-865e-4b50-81aa-d4b36d52971d', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL362-FO-RG', 'ca18908b-9983-4507-ae16-bd86edb83598', '8be483ec-4d95-40ce-8ce6-d1894d9c0e25')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ced8f1b7-e015-4bc0-b911-814945c3d013', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:44.137', '', 'ENGL362-RG-AO', 'ca18908b-9983-4507-ae16-bd86edb83598', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL362-RG-AO', 'dd55d0cd-1fb9-42f0-be84-fbac30d8f409', '683da769-6aec-4596-a548-23d57b52d093')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('ae65d34e-d24b-4dc3-b3d8-515e76eafdca', '6fc9a5ca-42b7-41b6-84fc-ddd4fd9adffa', 'courseOfferingCode', 'HIST429G-P', '36ba0c7d-d9b6-40d0-afa9-3d85f859151f')
/
insert into KSEN_CODE_GENERATOR_LOCKS (OBJ_ID, CODE, NAMESPACE, UNIQUE_KEY, ID) values ('82602e7d-beb6-4b62-85bc-1f089f3b5c61', 'A', 'courseOfferingCode', 'HIST429G', '31f93833-5aa4-4687-a272-95ae652425e5')
/
delete from KSEN_CODE_GENERATOR_LOCKS where CODE='6fc9a5ca-42b7-41b6-84fc-ddd4fd9adffa' and UNIQUE_KEY='HIST429G-P' and NAMESPACE='courseOfferingCode'
/
delete from KSEN_CODE_GENERATOR_LOCKS where UNIQUE_KEY='HIST429G' and NAMESPACE='courseOfferingCode' and 0=(select count(*) from KSEN_CODE_GENERATOR_LOCKS codegenera1_ where codegenera1_.UNIQUE_KEY='HIST429G-P' and codegenera1_.NAMESPACE='courseOfferingCode')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('bba00a66-a31d-44ec-a990-8b6dc4a45b96', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'CLUID-HIST429-200801000000', TIMESTAMP '2014-01-01 19:00:00.0', '', '', 'kuali.lui.course.offering.state.draft', 'kuali.lui.type.course.offering', '', '', 'HIST429A CO', '.', null, '0aa2a70d-9338-46ef-a988-ee3b3e1469ab')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('49e72549-926e-4372-a06d-dc8f3155f163', 'kuali.attribute.wait.list.level.type.key', '0aa2a70d-9338-46ef-a988-ee3b3e1469ab', 'AO', '9f17c569-a232-48ce-ae6f-970e7ad30e6e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fa78405e-eec7-4ebb-908b-b6d582286c27', 'kuali.attribute.final.exam.indicator', '0aa2a70d-9338-46ef-a988-ee3b3e1469ab', 'STANDARD', '92efdb70-c59c-4ae9-a528-1107b8a76a01')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cb167095-6984-4d77-80c5-24ee2c6d33d7', 'kuali.attribute.funding.source', '0aa2a70d-9338-46ef-a988-ee3b3e1469ab', '', '30772fc6-5679-43fd-8415-f2463148a22d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0b4ff98a-a72a-46df-b731-f229d5d85ddc', 'kuali.attribute.course.number.internal.suffix', '0aa2a70d-9338-46ef-a988-ee3b3e1469ab', 'A', 'd2048edb-b03d-4cb3-9b11-ec6514513168')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5531ed2a-4dac-48e1-a074-fac641b2052a', 'kuali.attribute.final.exam.driver', '0aa2a70d-9338-46ef-a988-ee3b3e1469ab', 'kuali.lu.exam.driver.ActivityOffering', 'e18079f9-794a-4363-b7f7-03386dfbd339')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7912e288-6cc9-429d-b8c8-aeaca80a73f9', 'kuali.attribute.course.evaluation.indicator', '0aa2a70d-9338-46ef-a988-ee3b3e1469ab', 0, '1294f6de-bf2f-484d-bc8b-6d68438928b6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a4a74ecf-cd53-471a-afc8-c690bd20c5e4', 'kuali.attribute.final.exam.use.matrix', '0aa2a70d-9338-46ef-a988-ee3b3e1469ab', 1, '474ea4dc-83e5-4e24-b7e7-79329c804433')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('adcd8b8e-2f32-4318-bb8a-b4f00059d0d8', 'kuali.attribute.wait.list.indicator', '0aa2a70d-9338-46ef-a988-ee3b3e1469ab', 1, '7d162a59-df8b-4605-986a-99083bdca93c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('baa849d5-aa1d-45f8-a947-78150ad2acb9', 'kuali.attribute.wait.list.type.key', '0aa2a70d-9338-46ef-a988-ee3b3e1469ab', null, '4bd3767e-877d-4377-9f1c-8fa1f4797a80')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4eec0664-fdce-498e-b258-7e3e5e9fa7cb', 'kuali.attribute.where.fees.attached.flag', '0aa2a70d-9338-46ef-a988-ee3b3e1469ab', 0, '02cf0aee-3e3e-4de9-b850-66ffacf6c5c9')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('f2ef32b4-e654-4ae7-b393-28bd3ee6321e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'HIST429A', 'HIST', '', 'Special Topics in History', '0aa2a70d-9338-46ef-a988-ee3b3e1469ab', '', '', 'kuali.lui.identifier.state.active', 'A', 'kuali.lui.identifier.type.official', '', '4e0f5ab8-a0b4-4e44-ac68-4f1eb84038d0')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('914cbb82-fc6c-476a-98c6-1580123ab9e0', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '0aa2a70d-9338-46ef-a988-ee3b3e1469ab', '', 'kuali.lu.code.honorsOffering', '', 'f97bc509-0b74-4c82-aa8b-d38c8a1fa035')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('0aa2a70d-9338-46ef-a988-ee3b3e1469ab', '3213375036', 'd703bfcd-a00a-416d-885e-d6ccec97abe3')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('0aa2a70d-9338-46ef-a988-ee3b3e1469ab', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0aa2a70d-9338-46ef-a988-ee3b3e1469ab', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0aa2a70d-9338-46ef-a988-ee3b3e1469ab', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0aa2a70d-9338-46ef-a988-ee3b3e1469ab', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('0aa2a70d-9338-46ef-a988-ee3b3e1469ab', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('85dde58e-614a-49dc-b937-3550ba243e7b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '2acda501-2afd-4b9e-b08f-76d97c038024', '', '', 'Courses with Lecture only', 'kuali.lui.format.offering.state.draft', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture only', '', '46f9949d-bdd7-45b3-8327-bf83ea814574')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3f32a4f7-ea0b-4c69-ac60-c66d10206703', 'kuali.attribute.final.exam.level.type', '46f9949d-bdd7-45b3-8327-bf83ea814574', 'kuali.lu.type.activity.Lecture', '92b8c3ae-ff32-4f1a-b03e-a0709f2d954c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('28d955f3-0ed1-4119-8852-dfbbe8c2a05c', 'regCodePrefix', '46f9949d-bdd7-45b3-8327-bf83ea814574', '1', '6c61968a-eb22-48e9-afb7-982002ca1ef0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('54ece1d9-9ea6-4960-8609-588eacabe575', 'kuali.attribute.grade.roster.level.type.key', '46f9949d-bdd7-45b3-8327-bf83ea814574', 'kuali.lui.type.activity.offering.lecture', '597d61d1-32fb-4c65-8eb2-fd152a9f8600')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7f3689cc-1a9f-483f-bc18-ccf6781c375e', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', 'Lecture Only', '46f9949d-bdd7-45b3-8327-bf83ea814574', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'd539f308-885b-4d55-8143-915c968cd993')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ca05bf97-50d4-4759-81c7-eed5536aec81', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:46.851', '', 'HIST429A-CO-FO', '0aa2a70d-9338-46ef-a988-ee3b3e1469ab', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST429A-CO-FO', '46f9949d-bdd7-45b3-8327-bf83ea814574', '72b006d2-771c-454a-9273-97e6aa5e6041')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('46f9949d-bdd7-45b3-8327-bf83ea814574', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('27bfaed7-98d5-4efa-b215-55817b4dd010', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', 'ace106f6-cab1-4d92-96ee-00cca6d66e73', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.draft', 'kuali.lui.type.activity.offering.lecture', 40, 40, 'Lecture', 'Courses with lecture', null, '2e65f3ba-27d0-41f1-b168-8d508b56168b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c4e92368-8db9-4ea3-a9bc-f5617ffcc8ce', 'kuali.attribute.nonstd.ts.indicator', '2e65f3ba-27d0-41f1-b168-8d508b56168b', 0, 'ff7d6df7-eff7-43ba-b978-3af263dc606c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('067b8e65-a5e1-48a8-b3d7-da6b93b35d63', 'kuali.attribute.course.evaluation.indicator', '2e65f3ba-27d0-41f1-b168-8d508b56168b', 0, '9a909640-30a2-4df8-9c30-034e909a3ff4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ced72cda-418c-42d6-9c80-975fc33c94cf', 'kuali.attribute.max.enrollment.is.estimate', '2e65f3ba-27d0-41f1-b168-8d508b56168b', 0, 'b584adaf-df17-421c-88df-09749c48d576')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('98dbcb79-9781-4f61-b8d5-353c83a1a038', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A', '', '', '', '2e65f3ba-27d0-41f1-b168-8d508b56168b', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c128d0bb-ab0b-40cf-ba9f-6a2c1dcbdb5e')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('8aa05dce-d45e-4667-903f-495f2e720aba', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '2e65f3ba-27d0-41f1-b168-8d508b56168b', '', 'kuali.lu.code.honorsOffering', 0, '7c58faaa-bb9f-4246-a4ae-7be9712b53cd')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('1f525edc-5507-4edc-93d0-0b8001358205', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '100.0', TIMESTAMP '2014-01-22 12:51:46.922', '', '2e65f3ba-27d0-41f1-b168-8d508b56168b', 'M.BONNIEL', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '16a0b59e-b988-4e42-9bf8-a1bdb68f718e')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ef0e8ca4-a369-4cfe-8e21-9a524aaf450c', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:46.922', '', 'HIST429A-FO-AO', '46f9949d-bdd7-45b3-8327-bf83ea814574', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST429A-FO-AO', '2e65f3ba-27d0-41f1-b168-8d508b56168b', 'e5ff7a6d-162d-4376-95ec-04e969ef174c')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('174256ef-8939-44ce-89a9-347c56e8a727', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 0, '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', 'ba7667cc-5889-4f32-8086-5f3ec28f9022')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('5290985c-20fe-4907-829f-74c9e9d26e4f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'A Schedule Request for HIST429A section 0101', 'Schedule request for HIST429A - A', 'A Schedule Request for HIST429A section 0101', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', 'bf7b001f-fc61-4f06-8c85-80603f06bac5', 'ba7667cc-5889-4f32-8086-5f3ec28f9022', 'b47995e5-63b7-4370-a744-dc4e0bf51a12')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('5295853b-3086-4677-b172-54744365713b', 0, 'b47995e5-63b7-4370-a744-dc4e0bf51a12', '09d309dd-af98-4aa3-af8e-d3c993cfbe12')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('17442e1a-0c91-4a51-99b8-58179d786226', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Athlete Seatpool', '1c2e7786-a2ab-4778-8e81-ea540c60b9b2', 1, 2, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '30710615-b92e-482e-8784-84aebf0cc854')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('ba7667cc-5889-4f32-8086-5f3ec28f9022', '2e65f3ba-27d0-41f1-b168-8d508b56168b')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('09d309dd-af98-4aa3-af8e-d3c993cfbe12', 'af22fa59-42ab-4d09-9a5b-6980f8b4ae7d')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('09d309dd-af98-4aa3-af8e-d3c993cfbe12', 'fdd1e716-7c99-4cc9-afbe-d8fa2e44ec66')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('09d309dd-af98-4aa3-af8e-d3c993cfbe12', 'F08200F107B9315CE040440A9B9A3EDC')
/
insert into KSEN_CO_SEAT_POOL_DEFN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ACTIVITY_OFFERING_ID, EXPIR_MSTONE_TYPE, PERCENTAGE_IND, NAME, POPULATION_ID, PROCESSING_PRIORITY, SEAT_LIMIT, SEAT_POOL_DEFN_STATE, SEAT_POOL_DEFN_TYPE, ID) values ('8c4d563f-133b-4598-9451-ea43a91d7319', 1, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', 'kuali.atp.milestone.firstdayofclasses', 0, '400-level Junior Seatpool', '4bae9f12-5901-4db9-b4a8-4dcc20781db5', 2, 4, 'kuali.lui.capacity.state.active', 'kuali.lui.capacity.type.seatpool', '33fd476a-f6bc-4304-8029-d8b40114817f')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='17442e1a-0c91-4a51-99b8-58179d786226', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='2e65f3ba-27d0-41f1-b168-8d508b56168b', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Athlete Seatpool', POPULATION_ID='1c2e7786-a2ab-4778-8e81-ea540c60b9b2', PROCESSING_PRIORITY=1, SEAT_LIMIT=2, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='30710615-b92e-482e-8784-84aebf0cc854' and VER_NBR=1
/
insert into KSEN_CWL (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ALLOW_HOLD_UTIL_ENTRIES_IND, AUTO_PROCESSED_IND, CHECK_IN_REQ_IND, CONF_REQ_IND, EFF_DT, EXPIR_DT, MAX_SIZE, REG_IN_FIRST_AAO_IND, STD_DUR_TIME_QTY, STD_DUR_TYPE, CWL_STATE, CWL_TYPE, ID) values ('95de1968-c91a-4e2e-a561-b8870b3b6d71', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 1, 1, 0, 1, TIMESTAMP '2013-01-01 19:00:00.0', '', '', 1, '', '', 'kuali.course.waitlist.state.active', 'kuali.course.waitlist.type.course.waitlist', '204a5cba-7b5b-45a3-94e7-1b8b1513e869')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('594c5730-e0b4-4335-b480-3fee1aab8a57', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '46f9949d-bdd7-45b3-8327-bf83ea814574', 'CL 1', 'CL 1', 'ed1de7a1-4b63-40e7-a7b9-d66bf5357b8e')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('183a7d59-3b16-4097-9d00-99c557990c25', 'kuali.lui.type.activity.offering.lecture', 'ed1de7a1-4b63-40e7-a7b9-d66bf5357b8e', 'c4a80e56-9bba-4a94-9858-a0964677c1cf')
/
update KSEN_CO_SEAT_POOL_DEFN set OBJ_ID='8c4d563f-133b-4598-9451-ea43a91d7319', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ACTIVITY_OFFERING_ID='2e65f3ba-27d0-41f1-b168-8d508b56168b', EXPIR_MSTONE_TYPE='kuali.atp.milestone.firstdayofclasses', PERCENTAGE_IND=0, NAME='400-level Junior Seatpool', POPULATION_ID='4bae9f12-5901-4db9-b4a8-4dcc20781db5', PROCESSING_PRIORITY=2, SEAT_LIMIT=4, SEAT_POOL_DEFN_STATE='kuali.lui.capacity.state.active', SEAT_POOL_DEFN_TYPE='kuali.lui.capacity.type.seatpool' where ID='33fd476a-f6bc-4304-8029-d8b40114817f' and VER_NBR=1
/
insert into KSEN_CWL_ACTIV_OFFER (CWL_ID, ACTIV_OFFER_ID) values ('204a5cba-7b5b-45a3-94e7-1b8b1513e869', '2e65f3ba-27d0-41f1-b168-8d508b56168b')
/
insert into KSEN_CWL_FORMAT_OFFER (CWL_ID, FORMAT_OFFER_ID) values ('204a5cba-7b5b-45a3-94e7-1b8b1513e869', '46f9949d-bdd7-45b3-8327-bf83ea814574')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('c4a80e56-9bba-4a94-9858-a0964677c1cf', '2e65f3ba-27d0-41f1-b168-8d508b56168b')
/
update KSEN_CO_AO_CLUSTER_SET set AO_CLUSTER_ID='ed1de7a1-4b63-40e7-a7b9-d66bf5357b8e' where ID='c4a80e56-9bba-4a94-9858-a0964677c1cf'
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('f9bac2d1-1b4f-4d05-96b9-4afad0831e98', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'kuali.atp.2014Winter', '2acda501-2afd-4b9e-b08f-76d97c038024', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '08fa01bc-1528-4cb5-a254-95e8c2f75f02')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1b703861-faa7-43ec-8322-c639f52f876c', 'kuali.attribute.registration.group.is.generated', '08fa01bc-1528-4cb5-a254-95e8c2f75f02', 1, '691901e4-0827-4b13-9588-dde291c417e6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('778e84ed-9c1e-4b74-883b-d89944d782a4', 'kuali.attribute.registration.group.aocluster.id', '08fa01bc-1528-4cb5-a254-95e8c2f75f02', 'ed1de7a1-4b63-40e7-a7b9-d66bf5357b8e', '28927255-bc77-4252-b6c5-f78df41848f3')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('467a4281-9f78-4c8c-9c36-895bca4daff4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '', '', '08fa01bc-1528-4cb5-a254-95e8c2f75f02', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '35602ee7-1a9d-4dd7-89cd-9ace580ad561')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('15f93e3a-d0d5-429d-b7f7-5f057bb5e2e2', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:47.371', '', 'HIST429A-FO-RG', '46f9949d-bdd7-45b3-8327-bf83ea814574', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST429A-FO-RG', '08fa01bc-1528-4cb5-a254-95e8c2f75f02', 'fda12e4d-9808-4b41-877f-0e12ea3dcdc2')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('3165c826-82c3-42b5-be0b-b37ca5099d86', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', TIMESTAMP '2014-01-22 12:51:47.371', '', 'HIST429A-RG-AO', '08fa01bc-1528-4cb5-a254-95e8c2f75f02', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST429A-RG-AO', '2e65f3ba-27d0-41f1-b168-8d508b56168b', '93af480b-0d86-4562-aca8-0f6cd5921bae')
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='aa22addb-51dc-4b4b-9ab6-19af629aa47d', ATTR_KEY='kuali.soc.rollover.result.dynattr.date.completed', OWNER_ID='8eb89914-1b01-4d50-af48-dab273b4554d', ATTR_VALUE='2014 Jan 22 12:51:47 America/New_York' where ID='401f9b67-014c-4a31-b85e-67ae4de802be'
/
update KSEN_SOC_ROR set OBJ_ID='086ca850-8a66-4720-b0d4-cf7f5955aab1', VER_NBR=7, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ITEMS_EXPECTED=48, ITEMS_PROCESSED=34, MESG_FORMATTED='', MESG_PLAIN='', SOC_ROR_STATE='kuali.soc.rollover.state.running', SOC_ROR_TYPE='kuali.soc.rollover.results.type.rollover', SOURCE_SOC_ID='03192a24-765a-4b9d-b638-5158430e09c8', TARGET_SOC_ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', TARGET_TERM_ID='kuali.atp.2014Winter' where ID='8eb89914-1b01-4d50-af48-dab273b4554d' and VER_NBR=6
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('56a9aa19-ce89-41ef-954e-46f0cb4a5ec6', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'db271f45-4aee-4d7a-b746-25398a78b3fb', 'e058cdb8-ce0d-4223-896b-a9ccd8e978d2', 'aeab754e-fb0d-4a0e-86f5-ef539c4d77ad')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('3a9c84e3-0325-4d0d-9b50-5e4315be5c63', 'activityOfferingsCreated', 'aeab754e-fb0d-4a0e-86f5-ef539c4d77ad', '1', '3bc16605-1ce5-431b-9aa9-d67e166c55d3')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('4d5df824-22d2-47a5-bc01-4724287f398f', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'ebd67086-fe4d-4454-a715-92175e293fcc', '8af41e78-075c-4766-aba7-934a2bdf3543', '3900b847-e6db-4a47-a17f-e68a8be45c3f')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('eaf8fddf-a2a7-42db-904f-948810841723', 'activityOfferingsCreated', '3900b847-e6db-4a47-a17f-e68a8be45c3f', '1', '453a12b3-2f57-42c7-92a5-9de73dbf526e')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('070dd430-66a5-4c20-be19-07a22fde645a', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'edac0ed5-af7d-4241-ac2a-80e0f7fe70a2', '', 'dfda28f7-8184-4b67-9020-b73ed4247622')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('51bdc879-2853-4b49-b214-73a713321e8b', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'efc7d4f5-12b8-4cf4-8ab2-18dc3b8b75c6', 'f550bb7b-c55e-4531-9d6d-fa005b19baad', '240c304e-75d3-43f8-bb25-abce97ecea64')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('691cdfb7-8f71-44ff-b2fe-69022239266b', 'activityOfferingsCreated', '240c304e-75d3-43f8-bb25-abce97ecea64', '1', '98caf241-3727-436d-97bc-35342221b3b8')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('0b4d784b-3948-4c90-a460-5ad49a731bf4', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'f970d440-1f3c-408a-bce3-73c20bfdff97', '', '84530aba-77af-4ef3-9da6-6916940c3f70')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('a2b1d5fc-d55d-469d-80fd-04162dcdffbf', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', '', '', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.processed.created', 'kuali.soc.rollover.result.item.type.create', 'f8e0de1e-fdc9-4a1f-bcad-9ba3d31459c3', '0aa2a70d-9338-46ef-a988-ee3b3e1469ab', '71c700bd-5ed2-4777-a5d6-22333165ea0c')
/
insert into KSEN_SOC_ROR_ITEM_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e162d219-6529-474b-9cbd-a2e802dacf37', 'activityOfferingsCreated', '71c700bd-5ed2-4777-a5d6-22333165ea0c', '1', '75991287-9baf-47e4-934f-d3f53942d09c')
/
insert into KSEN_SOC_ROR_ITEM (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, MESG_FORMATTED, MESG_PLAIN, ROR_ID, SOC_ROR_STATE, SOC_ROR_TYPE, SOURCE_CO_ID, TARGET_CO_ID, ID) values ('a70d5eeb-576c-46a2-b5f1-b26b7ac2e732', 0, 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'admin', TIMESTAMP '2014-01-22 12:49:24.356', 'skipped because there is a new version of the canonical course', 'skipped because there is a new version of the canonical course', '8eb89914-1b01-4d50-af48-dab273b4554d', 'kuali.soc.rollover.result.item.state.error', 'kuali.soc.rollover.result.item.type.create', 'fef218cf-70cd-4dfb-9d54-6a8bc6a7dbc4', '', 'ea35e3b9-e4f4-4cda-9b2f-553b09c3d59a')
/
update KSEN_SOC_ROR set OBJ_ID='086ca850-8a66-4720-b0d4-cf7f5955aab1', VER_NBR=8, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:49:24.356', ITEMS_EXPECTED=48, ITEMS_PROCESSED=34, MESG_FORMATTED='', MESG_PLAIN='', SOC_ROR_STATE='kuali.soc.rollover.state.finished', SOC_ROR_TYPE='kuali.soc.rollover.results.type.rollover', SOURCE_SOC_ID='03192a24-765a-4b9d-b638-5158430e09c8', TARGET_SOC_ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', TARGET_TERM_ID='kuali.atp.2014Winter' where ID='8eb89914-1b01-4d50-af48-dab273b4554d' and VER_NBR=7
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='001343af-8ae2-4bef-84aa-0f46a39280f6', ATTR_KEY='kuali.soc.rollover.result.dynattr.course.offerings.created', OWNER_ID='8eb89914-1b01-4d50-af48-dab273b4554d', ATTR_VALUE='34' where ID='c3a496ed-8b78-447a-94a8-5843e064427e'
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='4b4d72f9-54d1-4bf1-80f5-101940449c72', ATTR_KEY='kuali.soc.rollover.result.dynattr.activity.offerings.created', OWNER_ID='8eb89914-1b01-4d50-af48-dab273b4554d', ATTR_VALUE='43' where ID='529895cd-f5e9-4656-a4f9-ce81ab8fc373'
/
update KSEN_SOC_ROR_ATTR set OBJ_ID='0a86e475-40e4-4b2b-8019-e3006f04b230', ATTR_KEY='kuali.soc.rollover.result.dynattr.course.offerings.skipped', OWNER_ID='8eb89914-1b01-4d50-af48-dab273b4554d', ATTR_VALUE='14' where ID='79cd13a5-7639-4590-8682-e29980587eaa'
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('63cbd7e7-9034-4a69-8027-160d5e711c96', 'kuali.soc.state.open', '59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', '2014-01-22T12:52:03-0500', '3fa71e35-80d7-4c53-8165-01f27b1d2815')
/
update KSEN_SOC set OBJ_ID='181AD498-CF85-43A3-AC46-EA023C8751F9', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:52:03.213', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.open', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2014Winter', UNITS_CONTENT_OWNER_ID='' where ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0' and VER_NBR=0
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('450a7efd-f3a9-4e1f-a009-2d982b9271a5', 'kuali.soc.state.locked', '59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', '2014-01-22T12:52:52-0500', 'd1234b9f-6e95-4fd8-ad5e-e445043851c9')
/
update KSEN_SOC set OBJ_ID='181AD498-CF85-43A3-AC46-EA023C8751F9', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:52:52.529', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.locked', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2014Winter', UNITS_CONTENT_OWNER_ID='' where ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0' and VER_NBR=1
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c1a4cbfd-2d7e-4588-be18-45320b481fae', 'kuali.soc.scheduling.state.inprogress', '59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', '2014-01-22T12:53:04-0500', '0ba35128-b7ee-4512-a538-02e72076532c')
/
update KSEN_SOC set OBJ_ID='181AD498-CF85-43A3-AC46-EA023C8751F9', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:53:04.027', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.locked', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2014Winter', UNITS_CONTENT_OWNER_ID='' where ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0' and VER_NBR=2
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('84ea2a47-16f8-4707-9d4e-4c9f3aecaf6c', 'kuali.soc.scheduling.state.completed', '59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', '2014-01-22T12:53:10-0500', '28c5875a-4c00-4a09-b3c1-cd7c273675a0')
/
update KSEN_SOC set OBJ_ID='181AD498-CF85-43A3-AC46-EA023C8751F9', VER_NBR=4, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:53:10.329', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.locked', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2014Winter', UNITS_CONTENT_OWNER_ID='' where ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0' and VER_NBR=3
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ebc319f8-961a-4f25-873b-113d284f4f16', 'kuali.soc.state.finaledits', '59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', '2014-01-22T12:53:21-0500', 'e4453ddc-2144-43c1-aaeb-c3913fbcf232')
/
update KSEN_SOC set OBJ_ID='181AD498-CF85-43A3-AC46-EA023C8751F9', VER_NBR=5, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:53:21.09', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.finaledits', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2014Winter', UNITS_CONTENT_OWNER_ID='' where ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0' and VER_NBR=4
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('724a4cd3-5e4d-457a-bd81-6d63e4825a11', 'kuali.soc.state.publishing', '59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', '2014-01-22T12:53:54-0500', '809557f1-98e7-4897-85ff-ca2301ab10b4')
/
update KSEN_SOC set OBJ_ID='181AD498-CF85-43A3-AC46-EA023C8751F9', VER_NBR=6, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:53:54.321', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.publishing', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2014Winter', UNITS_CONTENT_OWNER_ID='' where ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0' and VER_NBR=5
/
insert into KSEN_SOC_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('aa7f1268-e39b-4a9e-9b72-7c170c828609', 'kuali.soc.state.published', '59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0', '2014-01-22T12:53:54-0500', '23ed55c7-d9d0-4c33-9234-5e5d220409e6')
/
update KSEN_SOC set OBJ_ID='181AD498-CF85-43A3-AC46-EA023C8751F9', VER_NBR=7, UPDATEID='admin', UPDATETIME=TIMESTAMP '2014-01-22 12:53:54.379', DESCR_FORMATTED='', NAME='', DESCR_PLAIN='', SOC_STATE='kuali.soc.state.published', SOC_TYPE='kuali.soc.type.main', SUBJECT_AREA='', TERM_ID='kuali.atp.2014Winter', UNITS_CONTENT_OWNER_ID='' where ID='59BBF768-1CB7-4C50-93AD-C6D92D6DC7A0' and VER_NBR=6
/
