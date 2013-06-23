-- KSENROLL-7038
-- CREATING CHEM105 201208 from CHEM105 201101

insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('1ec8e891-88f2-4929-81d8-e44fbd8b3529', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'kuali.atp.2012Fall', '31831b51-5be1-4f61-961f-a23d60ad2c53', TIMESTAMP '2012-08-28 20:00:00.0', '', 'The chemistry of carbon: aliphatic compounds, aromatic compounds, stere chemistry, halides, amines, and amides, acids, esters, carbohydrates, and natural products. This course is intended for students in curricula requiring only one semester of organic chemistry. Students requiring two or more years of chemistry should register for CHEM231/232 or CHEM237.', 'kuali.lui.course.offering.state.offered', 'kuali.lui.type.course.offering', '', '', 'CHEM105 CO', 'The chemistry of carbon: aliphatic compounds, aromatic compounds, stere chemistry, halides, amines, and amides, acids, esters, carbohydrates,  and natural products. This course is intended for students in curricula requiring only one semester of organic chemistry. Students requiring two or more years of chemistry should register for CHEM231/232 or CHEM237.', 'null', '9e89ed85-66ba-4a9c-9765-c36e78929051')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('39bdd0b1-f39e-4d88-b23c-06653cd6de10', 'kuali.attribute.where.fees.attached.flag', '9e89ed85-66ba-4a9c-9765-c36e78929051', '0', 'de5c5943-58f8-4430-a807-0cd294c8cc83')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('41707ba6-4e62-436b-ba6c-0c1250acf029', 'kuali.attribute.course.number.internal.suffix', '9e89ed85-66ba-4a9c-9765-c36e78929051', 'A', 'cb45ac4d-cf3b-4329-b81a-6c6d15831a30')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5cf75e44-0c23-492b-bd87-2adf6975982c', 'kuali.attribute.wait.list.level.type.key', '9e89ed85-66ba-4a9c-9765-c36e78929051', 'AO', '9899a5de-26b8-4972-a334-d54a2e6f0706')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('fdbbf213-c172-4b9a-88d1-5f273261e4fb', 'kuali.attribute.final.exam.indicator', '9e89ed85-66ba-4a9c-9765-c36e78929051', 'STANDARD', 'eeb11c39-6665-43b2-b04b-55e3c3e8e5c5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c1e90d29-e2c7-4b78-9f63-5f7d757f140b', 'kuali.attribute.course.evaluation.indicator', '9e89ed85-66ba-4a9c-9765-c36e78929051', '0', 'ecc232f7-085f-4910-8f40-32eea340628a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a87bb28c-e1d5-4ada-bc97-716c7da0117e', 'kuali.attribute.wait.list.type.key', '9e89ed85-66ba-4a9c-9765-c36e78929051', 'kuali.waitlist.type.semiautomatic', 'f556e284-77b7-4004-91ea-4b30f20abb9c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e038f63d-931e-4d83-8fe3-c0dd78ee9ae9', 'kuali.attribute.wait.list.indicator', '9e89ed85-66ba-4a9c-9765-c36e78929051', '1', '47a3b4c4-cf83-4364-bca5-3441fd090486')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cb923e31-619f-486b-94f6-6bb6ad42ea20', 'kuali.attribute.funding.source', '9e89ed85-66ba-4a9c-9765-c36e78929051', '', '826698b4-fd08-46c2-9e75-63e5137af772')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('68b39ec7-bfd0-45b9-95fd-28bb58c5da1d', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'CHEM105', 'CHEM', '', 'Fundamental of Organic and Biochemistry', '9e89ed85-66ba-4a9c-9765-c36e78929051', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'b0dedf9f-860b-40ba-a196-82edbcd51c46')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('3afb7a51-3ff6-4323-ad06-3dc9f4d37e3f', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', '', '9e89ed85-66ba-4a9c-9765-c36e78929051', '', 'kuali.lu.code.honorsOffering', '', '96752161-b358-4048-80b1-303fec6f5b88')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('9e89ed85-66ba-4a9c-9765-c36e78929051', '4284516367', 'cf9769aa-00c6-4d5c-9d64-1ea3589d8650')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('9e89ed85-66ba-4a9c-9765-c36e78929051', '4284516367')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('9e89ed85-66ba-4a9c-9765-c36e78929051', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('9e89ed85-66ba-4a9c-9765-c36e78929051', 'kuali.resultComponent.grade.audit')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('9e89ed85-66ba-4a9c-9765-c36e78929051', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('9e89ed85-66ba-4a9c-9765-c36e78929051', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('8a2f44a6-2e01-49b9-9223-9ba052f03b03', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'kuali.atp.2012Fall', 'fbe00f3e-788a-460c-b686-3303215621fa', '', '', 'Courses with Lecture/Discussion', 'kuali.lui.format.offering.state.offered', 'kuali.lui.type.course.format.offering', '', '', 'Lecture/Discussion', 'Courses with Lecture/Discussion', '', '653a8a06-82f8-4d24-8727-74d47ba9b47f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('19075c2d-0900-4a31-95fc-628904cbecfe', 'kuali.attribute.grade.roster.level.type.key', '653a8a06-82f8-4d24-8727-74d47ba9b47f', 'kuali.lui.type.activity.offering.discussion', 'c2cf0d7a-472e-42d5-a90f-360baa1a67df')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('cc71a86c-f162-42a0-8a6f-dbd18b4ce1a2', 'regCodePrefix', '653a8a06-82f8-4d24-8727-74d47ba9b47f', '1', 'bb55a6d8-8b18-46db-8b74-e8522227721b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('51fd4cd0-2811-4f6c-9cf1-b27c9f21a887', 'kuali.attribute.final.exam.level.type', '653a8a06-82f8-4d24-8727-74d47ba9b47f', 'kuali.lui.type.activity.offering.discussion', 'bedccf08-71fb-429f-883d-424b63352cd7')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7addcb30-0f4f-4a08-8375-da31442b9ecd', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', '', '', '', 'Lecture/Discussion', '653a8a06-82f8-4d24-8727-74d47ba9b47f', '', 'LEC/DIS', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '221eebe8-657e-4b76-ac8f-c40f40fdd7db')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ce0ba3c3-4a30-4af4-bb2a-44769b5156df', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', TIMESTAMP '2013-06-21 11:16:01.889', '', 'CHEM105-CO-FO', '9e89ed85-66ba-4a9c-9765-c36e78929051', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'CHEM105-CO-FO', '653a8a06-82f8-4d24-8727-74d47ba9b47f', 'b121b8dd-69f4-4f69-938e-a1049ae18287')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('653a8a06-82f8-4d24-8727-74d47ba9b47f', 'kuali.lui.type.activity.offering.discussion')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('653a8a06-82f8-4d24-8727-74d47ba9b47f', 'kuali.lui.type.activity.offering.lecture')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ae681900-ffa1-41a2-80d6-38cbc1db74c5', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'kuali.atp.2012HalfFall1', '1bf3adb6-8b48-441f-9d68-cc1005dd9cc6', '', '', 'Courses with lecture', 'kuali.lui.activity.offering.state.offered', 'kuali.lui.type.activity.offering.lecture', 46, 46, 'Lecture', 'Courses with lecture', 'null', 'a2596117-c310-4d3d-9ab0-0363715df307')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9cd6406f-d2fe-4420-b80a-a747e9cdfee9', 'kuali.attribute.max.enrollment.is.estimate', 'a2596117-c310-4d3d-9ab0-0363715df307', '0', '256d5a88-bda7-40f5-bfd7-c7aadfb922ed')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('268d048f-d840-4f5a-8dce-6ba2d64066d3', 'kuali.attribute.course.evaluation.indicator', 'a2596117-c310-4d3d-9ab0-0363715df307', '0', '128cf34e-854d-4f5c-b1b8-942bee76839a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d6f19ef7-5800-400d-a5e0-9ee7c897e848', 'isWaitlistCheckinRequired', 'a2596117-c310-4d3d-9ab0-0363715df307', '0', '6886c631-da16-4d31-9245-8db5fffebedb')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('b97428fe-6ddf-4258-ab16-e82a768aa590', 0, 'admin', TIMESTAMP '2013-06-21 11:45:51.926', 'admin', TIMESTAMP '2013-06-21 11:45:51.926', 'A', '', '', '', 'a2596117-c310-4d3d-9ab0-0363715df307', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a030f883-8c06-420a-a532-22c183c33479')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('201eb8b1-9519-426c-a6d6-ad39ba8ca19a', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', '', 'a2596117-c310-4d3d-9ab0-0363715df307', '', 'kuali.lu.code.honorsOffering', '0', '98d12c90-f96e-4b79-8f09-a7f86f57593d')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('5c84ef89-c772-4cf0-b731-7027c576cab6', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', '100.0', TIMESTAMP '2013-06-21 11:16:03.642', '', 'a2596117-c310-4d3d-9ab0-0363715df307', 'N.BENOITM', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '97140065-9b51-4efe-a792-2a119cbeb41a')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('532f60a4-eef1-4f2e-a9e2-ab1c71ef09cb', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', TIMESTAMP '2013-06-21 11:16:03.642', '', 'CHEM105-FO-AO', '653a8a06-82f8-4d24-8727-74d47ba9b47f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM105-FO-AO', 'a2596117-c310-4d3d-9ab0-0363715df307', 'fcb49055-24bf-453f-80fa-27f9c12a8758')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('4c78f7a8-e36d-45ee-805a-04c981b535cc', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', '', '', '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '3a0d2c15-bbb3-4529-b8e8-a5ae84f9e06d')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('5dd6f9dc-feee-4f88-ae18-fe246c6cb1ff', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', '', 'Schedule request for CHEM105 - A', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '3a0d2c15-bbb3-4529-b8e8-a5ae84f9e06d', '1a8b68e6-ad81-4638-8b91-4f8570798ac8')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('6c19047c-8858-4361-bbbf-f5fd3a2c68e4', '0', '1a8b68e6-ad81-4638-8b91-4f8570798ac8', '3617757d-5e4f-4ea4-8235-e76419fd26e0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('503f0a5f-31fc-44c6-b235-2b99a749a39b', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'kuali.atp.2012HalfFall1', 'e073a783-300c-4e22-8e41-f661639d3ce5', '', '', 'Courses with discussion', 'kuali.lui.activity.offering.state.offered', 'kuali.lui.type.activity.offering.discussion', 46, 46, 'Discussion', 'Courses with discussion', 'null', 'a03df821-38bc-4fc4-b34a-77a92405b87f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9ded9a6d-c2b7-41a5-8c66-4ed534bf4a72', 'kuali.attribute.course.evaluation.indicator', 'a03df821-38bc-4fc4-b34a-77a92405b87f', '0', 'ff0f1e96-12ef-44de-9a42-4f86b896c8e6')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('316f8910-faee-447e-9729-881fa96a13e8', 'kuali.attribute.max.enrollment.is.estimate', 'a03df821-38bc-4fc4-b34a-77a92405b87f', '0', '341bbb5e-4db5-448a-9c7b-92bd52a3a74b')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c8af0510-eb53-43ef-aff3-cb8303958519', 'isWaitlistCheckinRequired', 'a03df821-38bc-4fc4-b34a-77a92405b87f', '0', '67d7c4cd-ce1e-4fb0-bc5a-141a2fec6e2c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('f17c6423-90ca-40e3-a974-d61e08b48720', 0, 'admin', TIMESTAMP '2013-06-21 14:28:05.859', 'admin', TIMESTAMP '2013-06-21 14:28:05.859', 'B', '', '', '', 'a03df821-38bc-4fc4-b34a-77a92405b87f', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a12ca9c3-d5ef-474b-b57b-d711e34f9945')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('a65be1cc-1ed7-4792-9c68-b1c8c03182e1', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', '', 'a03df821-38bc-4fc4-b34a-77a92405b87f', '', 'kuali.lu.code.honorsOffering', '0', '3d5c3a86-5cc0-4d39-8a16-c654aa2e4770')
/
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, COMMIT_PERCT, EFF_DT, EXPIR_DT, LUI_ID, PERS_ID, LPR_STATE, LPR_TYPE, ID) values ('15dc407b-0368-4423-86ce-9b0e5e4a27ce', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', '100.0', TIMESTAMP '2013-06-21 11:16:04.036', '', 'a03df821-38bc-4fc4-b34a-77a92405b87f', 'D.RAMONAB', 'kuali.lpr.state.active', 'kuali.lpr.type.instructor.main', '83e19fd6-d9ae-49c2-bd66-379d0eb0f2d8')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('0432dc10-f423-46f4-9866-8b5911fae6d6', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', TIMESTAMP '2013-06-21 11:16:04.036', '', 'CHEM105-FO-AO', '653a8a06-82f8-4d24-8727-74d47ba9b47f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'CHEM105-FO-AO', 'a03df821-38bc-4fc4-b34a-77a92405b87f', '6e686b40-e42a-408d-8365-284222557d4b')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('3a0d2c15-bbb3-4529-b8e8-a5ae84f9e06d', 'a2596117-c310-4d3d-9ab0-0363715df307')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('3617757d-5e4f-4ea4-8235-e76419fd26e0', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('3617757d-5e4f-4ea4-8235-e76419fd26e0', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('3617757d-5e4f-4ea4-8235-e76419fd26e0', '2246d609-9fbb-4d06-b45e-a57ef80539b7')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('3617757d-5e4f-4ea4-8235-e76419fd26e0', 'f183d4dd-0d23-42d6-a2d0-55d1d8607d56')
/
insert into KSEN_SCHED_RQST_SET (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, MAX_ENRL_SHARED_IND, MAX_ENRL, NAME, DESCR_PLAIN, REF_OBJECT_TYPE, SCHED_RQST_SET_STATE, SCHED_RQST_SET_TYPE, ID) values ('89d9dcb3-613b-4529-ba5d-96c648526b05', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', '', '', '', '', '', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.set.type.schedule.request.set', '66ed2143-a52a-4b29-9c94-b36184038d7a')
/
insert into KSEN_SCHED_RQST (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, NAME, DESCR_PLAIN, SCHED_RQST_STATE, SCHED_RQST_TYPE, SCHED_ID, SCHED_RQST_SET_ID, ID) values ('88a672e0-ee8c-40a0-85c5-67e826cb48ac', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', '', 'Schedule request for CHEM105 - B', '', 'kuali.scheduling.schedule.request.state.created', 'kuali.scheduling.schedule.request.type.schedule.request', '', '66ed2143-a52a-4b29-9c94-b36184038d7a', '40294f91-9ac2-4d92-8aa6-67705d83c750')
/
insert into KSEN_SCHED_RQST_CMP (OBJ_ID, TBA_IND, SCHED_RQST_ID, ID) values ('fcc6d9eb-daf0-44c3-be95-ba9f0c402e02', '0', '40294f91-9ac2-4d92-8aa6-67705d83c750', '65e9340a-f81e-48b8-8539-59841acdbf87')
/
insert into KSEN_SCHED_REF_OBJECT (SCHED_RQST_SET_ID, REF_OBJECT_ID) values ('66ed2143-a52a-4b29-9c94-b36184038d7a', 'a03df821-38bc-4fc4-b34a-77a92405b87f')
/
insert into KSEN_SCHED_RQST_CMP_BLDG (CMP_ID, BUILDING_ID) values ('65e9340a-f81e-48b8-8539-59841acdbf87', '6f2e8f26-95bc-4bb1-a3d0-3206324cad4a')
/
insert into KSEN_SCHED_RQST_CMP_CAMPUS (CMP_ID, CAMPUS_ID) values ('65e9340a-f81e-48b8-8539-59841acdbf87', 'UMCP')
/
insert into KSEN_SCHED_RQST_CMP_ROOM (CMP_ID, ROOM_ID) values ('65e9340a-f81e-48b8-8539-59841acdbf87', '2246d609-9fbb-4d06-b45e-a57ef80539b7')
/
insert into KSEN_SCHED_RQST_CMP_TMSLOT (CMP_ID, TM_SLOT_ID) values ('65e9340a-f81e-48b8-8539-59841acdbf87', '74ee45cc-8ed0-4c7d-82d3-617ebfe30d8c')
/
insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('c8dca02e-6151-41c9-804d-0ed0a5cccd52', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '653a8a06-82f8-4d24-8727-74d47ba9b47f', 'CL 1', 'CL 1', '10f2991f-ed50-4267-af3e-386c02c2e53f')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('3eaaf0bc-885f-43fe-9b48-a3d11ebad808', 'kuali.lui.type.activity.offering.lecture', '10f2991f-ed50-4267-af3e-386c02c2e53f', '4d46c958-5fb2-4d58-92d0-c86544e9acd4')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('50bef025-e23a-4cb9-9ea6-80a101af3c4a', 'kuali.lui.type.activity.offering.discussion', '10f2991f-ed50-4267-af3e-386c02c2e53f', '383d9cd1-626e-4bfb-81fe-a7c79335493e')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('4d46c958-5fb2-4d58-92d0-c86544e9acd4', 'a2596117-c310-4d3d-9ab0-0363715df307')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('383d9cd1-626e-4bfb-81fe-a7c79335493e', 'a03df821-38bc-4fc4-b34a-77a92405b87f')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('f2763733-2fc2-4eeb-a2df-ca17a5645a24', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'kuali.atp.2012Fall', 'fbe00f3e-788a-460c-b686-3303215621fa', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'ea3ac9da-38aa-41f0-8d13-6cccfa6bae75')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9b7badfb-cfe2-4b47-a5f5-5bacd4a5ace3', 'kuali.attribute.registration.group.aocluster.id', 'ea3ac9da-38aa-41f0-8d13-6cccfa6bae75', '10f2991f-ed50-4267-af3e-386c02c2e53f', '4046776a-091d-4b60-ab4a-95b59507ed33')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0f9864b5-173f-46ae-b92a-947e7a7f65a4', 'kuali.attribute.registration.group.is.generated', 'ea3ac9da-38aa-41f0-8d13-6cccfa6bae75', '1', '69f4cf83-e38c-4149-8145-ab118ec0c4e5')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('dea47c02-04ce-4cbe-8c29-f5ef19d27281', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', '', '', '', '', 'ea3ac9da-38aa-41f0-8d13-6cccfa6bae75', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '54818e46-f992-4461-95ff-4bb4c61c0b95')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('cb611c6a-f079-425b-820e-208949dc3ed2', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', TIMESTAMP '2013-06-21 11:16:05.444', '', 'CHEM105-FO-RG', '653a8a06-82f8-4d24-8727-74d47ba9b47f', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'CHEM105-FO-RG', 'ea3ac9da-38aa-41f0-8d13-6cccfa6bae75', 'e7d20a98-2350-46fd-ad68-a02a42c8b7b5')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('1bf41b67-81db-4181-b608-b5418bb95900', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', TIMESTAMP '2013-06-21 11:16:05.445', '', 'CHEM105-RG-AO', 'ea3ac9da-38aa-41f0-8d13-6cccfa6bae75', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM105-RG-AO', 'a2596117-c310-4d3d-9ab0-0363715df307', 'b4afad3f-867e-4a4b-8440-cea91afde484')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('558115d3-15cc-45e6-a716-b93172ead3ee', 0, 'admin', TIMESTAMP '2013-06-21 11:15:54.266', 'admin', TIMESTAMP '2013-06-21 11:15:54.266', TIMESTAMP '2013-06-21 11:16:05.446', '', 'CHEM105-RG-AO', 'ea3ac9da-38aa-41f0-8d13-6cccfa6bae75', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'CHEM105-RG-AO', 'a03df821-38bc-4fc4-b34a-77a92405b87f', '6c9e32eb-1337-4e5a-9b2e-53040bf5b17a')
/