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


-- ENGL627 / 201208 (from catalog)

insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('02e05493-3a5f-4ed1-802c-ec158760ff1b', 0, 'admin', TIMESTAMP '2013-06-24 09:04:47.864', 'admin', TIMESTAMP '2013-06-24 09:04:47.864', 'kuali.atp.2012Fall', 'bde29519-2459-46f4-ab48-dec00047aec1', TIMESTAMP '2012-08-28 20:00:00.0', '', '', 'kuali.lui.course.offering.state.offered', 'kuali.lui.type.course.offering', '', '', 'ENGL627 CO', '.', '', '7c87fa22-fbdf-45f7-9b7b-c6a684fc1b73')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('56f3a12c-3f0d-476b-adfc-3322bef77b57', 'kuali.attribute.wait.list.indicator', '7c87fa22-fbdf-45f7-9b7b-c6a684fc1b73', 'null', '97b2a034-c6ea-499f-adfa-124d718865c0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('29a12e22-2686-4dfb-b923-391601f038d4', 'kuali.attribute.final.exam.indicator', '7c87fa22-fbdf-45f7-9b7b-c6a684fc1b73', 'STANDARD', 'a20157b5-97f5-4d37-bfb4-45c1693e792d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('29855edb-1718-451e-ab49-60c5f3d61e6f', 'kuali.attribute.where.fees.attached.flag', '7c87fa22-fbdf-45f7-9b7b-c6a684fc1b73', 'null', '03c0794d-9aa1-4213-88cf-32c3f5b6d2c5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8ad51a9f-c1ec-4d83-8618-13d97c968c9e', 'kuali.attribute.course.number.internal.suffix', '7c87fa22-fbdf-45f7-9b7b-c6a684fc1b73', 'A', '8be1bcbc-3950-4e81-8543-c3d8419cd560')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('4a37999b-fd48-4179-8ada-031620b687f2', 'kuali.attribute.wait.list.level.type.key', '7c87fa22-fbdf-45f7-9b7b-c6a684fc1b73', 'null', 'cd215f0f-a813-4648-9589-6d8f11e8ac21')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('919eb61a-a4a1-466f-b931-621631758461', 'kuali.attribute.course.evaluation.indicator', '7c87fa22-fbdf-45f7-9b7b-c6a684fc1b73', 'null', 'ef77e653-64d3-4f3b-ab50-eb2412f628e4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e07be991-8e22-4766-85b3-8694254ce207', 'kuali.attribute.funding.source', '7c87fa22-fbdf-45f7-9b7b-c6a684fc1b73', '', '35361f48-8b6b-4893-a7bb-4248db026508')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dbdbfbb3-1629-4e78-afe7-114c924f933d', 'kuali.attribute.wait.list.type.key', '7c87fa22-fbdf-45f7-9b7b-c6a684fc1b73', 'null', 'b38260bc-55cb-4b49-a0b6-16bd1e814e7c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('976f29cd-86f3-4755-b8b2-d19da1e93d9f', 0, 'admin', TIMESTAMP '2013-06-24 09:04:47.864', 'admin', TIMESTAMP '2013-06-24 09:04:47.864', 'ENGL627', 'ENGL', '', 'Readings in American Literature, 1865-1914', '7c87fa22-fbdf-45f7-9b7b-c6a684fc1b73', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '2b751208-213f-4d72-93db-aa29c7c8a350')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('f48de214-dfea-4423-b907-546946cfcb6d', 0, 'admin', TIMESTAMP '2013-06-24 09:04:47.864', 'admin', TIMESTAMP '2013-06-24 09:04:47.864', '', '7c87fa22-fbdf-45f7-9b7b-c6a684fc1b73', '', 'kuali.lu.code.honorsOffering', '', 'f06e0768-85e7-4b2e-b0f9-739cd1694181')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('7c87fa22-fbdf-45f7-9b7b-c6a684fc1b73', '2677933260', '94f44993-0e62-43ad-8bcd-f77c85cbfb0c')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('7c87fa22-fbdf-45f7-9b7b-c6a684fc1b73', '2677933260')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('7c87fa22-fbdf-45f7-9b7b-c6a684fc1b73', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('7c87fa22-fbdf-45f7-9b7b-c6a684fc1b73', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('4ca5291c-04a3-4774-8b10-1b0f2c682a5b', 0, 'admin', TIMESTAMP '2013-06-24 09:04:48.588', 'admin', TIMESTAMP '2013-06-24 09:04:48.588', 'kuali.atp.2012Fall', 'b867e7b1-0fbf-48a4-b94d-3fae0a125754', '', '', 'Courses with Lecture Only', 'kuali.lui.format.offering.state.offered', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture Only', '', 'e9edab99-212e-41a5-bcd3-eff308dccb3e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8c59a1d8-7042-48f6-b364-429ce0114a64', 'kuali.attribute.final.exam.level.type', 'e9edab99-212e-41a5-bcd3-eff308dccb3e', 'kuali.lu.type.activity.Lecture', 'e417580c-fb1c-454f-af7f-f1709a0052d8')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2e73d2b1-b159-487b-83eb-fafb321d8132', 'kuali.attribute.grade.roster.level.type.key', 'e9edab99-212e-41a5-bcd3-eff308dccb3e', 'kuali.lu.type.activity.Lecture', 'c2a7363c-c050-40d0-abce-e5a4230415e1')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('17358a3c-aa51-4c36-a5df-021eaec85fe4', 'regCodePrefix', 'e9edab99-212e-41a5-bcd3-eff308dccb3e', '1', '689e2be6-76db-43cf-ba44-b700227ff56d')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('5d6673a3-16dd-459f-9e81-40490b1d3388', 0, 'admin', TIMESTAMP '2013-06-24 09:04:48.588', 'admin', TIMESTAMP '2013-06-24 09:04:48.588', '', '', '', 'Lecture Only', 'e9edab99-212e-41a5-bcd3-eff308dccb3e', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '9e739ef7-6985-4426-9959-6db6638b1e72')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('18dc9e10-17ef-4fff-9f52-946db3e46efd', 0, 'admin', TIMESTAMP '2013-06-24 09:04:48.588', 'admin', TIMESTAMP '2013-06-24 09:04:48.588', TIMESTAMP '2013-06-24 09:04:48.718', '', 'ENGL627-CO-FO', '7c87fa22-fbdf-45f7-9b7b-c6a684fc1b73', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'ENGL627-CO-FO', 'e9edab99-212e-41a5-bcd3-eff308dccb3e', '9ec1201a-caf4-44eb-b619-12c406378805')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('e9edab99-212e-41a5-bcd3-eff308dccb3e', 'kuali.lui.type.activity.offering.lecture')
/

-- Adding AO

insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('ff9345fc-d9d5-4400-829e-dd34ba86f688', 0, 'admin', TIMESTAMP '2013-06-24 09:07:37.679', 'admin', TIMESTAMP '2013-06-24 09:07:37.679', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'e9edab99-212e-41a5-bcd3-eff308dccb3e', 'CL 1', 'CL 1', '0c66969d-bf68-4e19-a91e-1d7894a53f45')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('d21fa78d-c567-4b4c-889b-6683afc3b09f', 'kuali.lui.type.activity.offering.lecture', '0c66969d-bf68-4e19-a91e-1d7894a53f45', 'a6146a5f-c7cf-4e6e-9df6-3dd18f8ad332')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('561f8f22-5529-4b0e-ae43-234ca403e827', 0, 'admin', TIMESTAMP '2013-06-24 09:07:37.679', 'admin', TIMESTAMP '2013-06-24 09:07:37.679', 'kuali.atp.2012HalfFall1', '5f98f34b-6839-4c27-9083-78f7eb85d9c0', '', '', '', 'kuali.lui.activity.offering.state.offered', 'kuali.lui.type.activity.offering.lecture', '', '', 'ENGL627 AO', '', '', 'cc5e8eed-9c2d-460a-9c3f-477e529e3da7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f802c36e-8241-4ca9-880d-abb5123fce7d', 'kuali.attribute.max.enrollment.is.estimate', 'cc5e8eed-9c2d-460a-9c3f-477e529e3da7', 'null', '1b820dee-e36c-4faa-9f82-01c55f823450')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8f3dad8c-876b-45e6-974b-de81afec66da', 'kuali.attribute.course.evaluation.indicator', 'cc5e8eed-9c2d-460a-9c3f-477e529e3da7', 'null', '3eb1dda6-9dd8-4708-9cab-81520fc8e68e')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('28d8464e-11b6-42b8-a639-12cfa02bdf2c', 0, 'admin', TIMESTAMP '2013-06-24 09:09:26.371', 'admin', TIMESTAMP '2013-06-24 09:09:26.371', 'A', '', '', '', 'cc5e8eed-9c2d-460a-9c3f-477e529e3da7', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'fb562dc6-62e0-4f45-a8af-3f283ac3512a')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('c32da2c9-734e-4f88-b8bd-1f58c2634f10', 0, 'admin', TIMESTAMP '2013-06-24 09:07:37.679', 'admin', TIMESTAMP '2013-06-24 09:07:37.679', '', 'cc5e8eed-9c2d-460a-9c3f-477e529e3da7', '', 'kuali.lu.code.honorsOffering', 'null', '4fc720d1-746e-4619-a801-40dd7d44f86b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('116a29bc-5363-41f3-89f7-63b87b5e1936', 0, 'admin', TIMESTAMP '2013-06-24 09:07:37.679', 'admin', TIMESTAMP '2013-06-24 09:07:37.679', TIMESTAMP '2013-06-24 09:07:39.018', '', 'ENGL627-FO-AO', 'e9edab99-212e-41a5-bcd3-eff308dccb3e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'ENGL627-FO-AO', 'cc5e8eed-9c2d-460a-9c3f-477e529e3da7', '1f5905b5-ffeb-4511-8045-8f996bf1ad5d')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('a6146a5f-c7cf-4e6e-9df6-3dd18f8ad332', 'cc5e8eed-9c2d-460a-9c3f-477e529e3da7')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('c6d7073a-8d22-4138-aaef-8b6d5de05cc3', 0, 'admin', TIMESTAMP '2013-06-24 09:07:37.679', 'admin', TIMESTAMP '2013-06-24 09:07:37.679', 'kuali.atp.2012Fall', 'b867e7b1-0fbf-48a4-b94d-3fae0a125754', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', 'b4929b59-32af-40f8-aa69-e2178f2133a0')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c3519207-a0c5-4b85-a354-e2aaec141ea5', 'kuali.attribute.registration.group.is.generated', 'b4929b59-32af-40f8-aa69-e2178f2133a0', '1', 'f9cdffe2-b2fa-499b-b3ac-fffc1f125a3a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('daec3e75-c55d-4fae-a1b6-6dd50dd20aa0', 'kuali.attribute.registration.group.aocluster.id', 'b4929b59-32af-40f8-aa69-e2178f2133a0', '0c66969d-bf68-4e19-a91e-1d7894a53f45', 'fa0baa5a-256b-44b3-a13d-dad3926edd68')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7a9ac387-4ab7-4ee7-95e6-9f107d43ebf3', 0, 'admin', TIMESTAMP '2013-06-24 09:07:37.679', 'admin', TIMESTAMP '2013-06-24 09:07:37.679', '', '', '', '', 'b4929b59-32af-40f8-aa69-e2178f2133a0', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'dba726c5-0d61-4664-95b7-8d3eb21bc387')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('daacd3d5-5153-412d-af73-49512da9822f', 0, 'admin', TIMESTAMP '2013-06-24 09:07:37.679', 'admin', TIMESTAMP '2013-06-24 09:07:37.679', TIMESTAMP '2013-06-24 09:07:40.14', '', 'ENGL627-FO-RG', 'e9edab99-212e-41a5-bcd3-eff308dccb3e', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'ENGL627-FO-RG', 'b4929b59-32af-40f8-aa69-e2178f2133a0', 'c20c2276-bad9-4ed2-9a29-fb557d8fa9fe')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('5b366aa4-c879-4fc8-ac34-179c0ead426b', 0, 'admin', TIMESTAMP '2013-06-24 09:07:37.679', 'admin', TIMESTAMP '2013-06-24 09:07:37.679', TIMESTAMP '2013-06-24 09:07:40.141', '', 'ENGL627-RG-AO', 'b4929b59-32af-40f8-aa69-e2178f2133a0', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'ENGL627-RG-AO', 'cc5e8eed-9c2d-460a-9c3f-477e529e3da7', 'e6a1d31a-6b85-4807-89b5-f8ccfb2bf648')
/


-- HIST605 / 201208 (from catalog)

insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('42c88b07-f816-4be4-9188-72595f886bb1', 0, 'admin', TIMESTAMP '2013-06-24 13:12:53.3', 'admin', TIMESTAMP '2013-06-24 13:12:53.3', 'kuali.atp.2012Fall', 'ddd89101-4cc8-4c61-90a4-d4ec5645f8ef', TIMESTAMP '2012-08-28 20:00:00.0', '', 'Classic and recent interpretations in comparative history with emphasis on current directions of scholarship and research. Students previously enrolled in HIST 605 for l credit hour may enroll.', 'kuali.lui.course.offering.state.offered', 'kuali.lui.type.course.offering', '', '', 'HIST605 CO', 'Classic and recent interpretations in comparative history with emphasis on current directions of scholarship and research. Students previously enrolled in HIST 605 for l credit hour may enroll.', '', 'e014b75a-b837-4657-86e7-f7ad87c6531a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('f95e7564-f198-45c2-bda3-bae4d75c8e5a', 'kuali.attribute.funding.source', 'e014b75a-b837-4657-86e7-f7ad87c6531a', '', 'e1e22f70-1481-4660-a6ef-48f653fa05a9')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('78344294-b3f9-41ca-a715-aed148a9cb94', 'kuali.attribute.where.fees.attached.flag', 'e014b75a-b837-4657-86e7-f7ad87c6531a', 'null', '2b02c615-9222-463e-8ccc-b10b6214b33a')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('81fd8bcf-e702-40ed-b70d-4a9b9e4df345', 'kuali.attribute.course.number.internal.suffix', 'e014b75a-b837-4657-86e7-f7ad87c6531a', 'A', 'e961da95-8c59-4089-bbb5-260a6273d367')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bb6f9985-6efd-4153-b156-94609c8e00d2', 'kuali.attribute.wait.list.type.key', 'e014b75a-b837-4657-86e7-f7ad87c6531a', 'null', 'efc64597-2b2a-4db0-8516-76a5949bf776')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('aae490b4-83a0-41bb-a9bd-f5016d051420', 'kuali.attribute.wait.list.indicator', 'e014b75a-b837-4657-86e7-f7ad87c6531a', 'null', '4e14fd46-b2ec-46c9-b072-031aa0ecb7ef')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a6a08f3f-1dd2-4f81-b887-12b3f94f3cbd', 'kuali.attribute.wait.list.level.type.key', 'e014b75a-b837-4657-86e7-f7ad87c6531a', 'null', 'f093d201-96a3-4efe-822b-452194732f06')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('438cd7b3-5857-42dd-bc80-4fa9916dad94', 'kuali.attribute.course.evaluation.indicator', 'e014b75a-b837-4657-86e7-f7ad87c6531a', 'null', '3b7e72df-71cb-410d-bc48-db2a17fec979')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c5e57b20-c356-474b-af9e-dbd1fe226ee6', 'kuali.attribute.final.exam.indicator', 'e014b75a-b837-4657-86e7-f7ad87c6531a', 'STANDARD', '3a0d709a-82e1-4c8e-a823-b84a440e5201')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('9f41fe4d-c668-4d94-8d54-1680910e6339', 0, 'admin', TIMESTAMP '2013-06-24 13:12:53.3', 'admin', TIMESTAMP '2013-06-24 13:12:53.3', 'HIST605', 'HIST', '', 'General Seminar:  World History', 'e014b75a-b837-4657-86e7-f7ad87c6531a', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c1044bfb-bf28-4357-a1b2-495e9ba2a9e9')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('d1937607-19bc-468d-934f-695d8aaf5586', 0, 'admin', TIMESTAMP '2013-06-24 13:12:53.3', 'admin', TIMESTAMP '2013-06-24 13:12:53.3', '', 'e014b75a-b837-4657-86e7-f7ad87c6531a', '', 'kuali.lu.code.honorsOffering', '', 'f40ab528-10e4-49b7-b976-e82e855adbd7')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('e014b75a-b837-4657-86e7-f7ad87c6531a', '3213375036', '8dad1350-6ecc-4805-ad6d-1733dd5e5893')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('e014b75a-b837-4657-86e7-f7ad87c6531a', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e014b75a-b837-4657-86e7-f7ad87c6531a', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('e014b75a-b837-4657-86e7-f7ad87c6531a', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('183d60a1-fdc9-4848-9c5b-5713fa23b032', 0, 'admin', TIMESTAMP '2013-06-24 13:12:56.204', 'admin', TIMESTAMP '2013-06-24 13:12:56.204', 'kuali.atp.2012Fall', '08867ede-c389-4a85-9685-857d7495904a', '', '', 'Courses with Lecture Only', 'kuali.lui.format.offering.state.offered', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture Only', '', '50fbe84c-325d-4978-8050-d0350f6a8f19')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a6bc703f-cbb0-43d4-b2a8-4895da9ea15a', 'regCodePrefix', '50fbe84c-325d-4978-8050-d0350f6a8f19', '1', '9a321d17-2212-4dde-b5ca-7424f529e1d7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('360ae889-1f6f-4146-a69a-1ee4a450c3b6', 'kuali.attribute.final.exam.level.type', '50fbe84c-325d-4978-8050-d0350f6a8f19', 'kuali.lu.type.activity.Lecture', '2869c34d-e850-4899-9cc1-65679c0af61d')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('acae6c74-44af-48e1-ba3b-eb1f0f26409d', 'kuali.attribute.grade.roster.level.type.key', '50fbe84c-325d-4978-8050-d0350f6a8f19', 'kuali.lu.type.activity.Lecture', '0abc6018-099a-41b7-b829-6de2ec0601dc')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('72100ae8-c808-4a13-86ae-c1f138a1a498', 0, 'admin', TIMESTAMP '2013-06-24 13:12:56.204', 'admin', TIMESTAMP '2013-06-24 13:12:56.204', '', '', '', 'Lecture Only', '50fbe84c-325d-4978-8050-d0350f6a8f19', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '5432bbfa-4f5e-48ec-8c46-aba197937cf8')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('102c91ab-6933-4f2d-aaaf-9e32d7c67cd1', 0, 'admin', TIMESTAMP '2013-06-24 13:12:56.204', 'admin', TIMESTAMP '2013-06-24 13:12:56.204', TIMESTAMP '2013-06-24 13:12:56.377', '', 'HIST605-CO-FO', 'e014b75a-b837-4657-86e7-f7ad87c6531a', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST605-CO-FO', '50fbe84c-325d-4978-8050-d0350f6a8f19', '9e3a93b4-b5f4-4ad7-ad93-9659866011ac')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('50fbe84c-325d-4978-8050-d0350f6a8f19', 'kuali.lui.type.activity.offering.lecture')
/

-- Adding AO

insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('4ec0830d-2573-43ec-a029-a981e4c650c4', 0, 'admin', TIMESTAMP '2013-06-24 13:13:43.523', 'admin', TIMESTAMP '2013-06-24 13:13:43.523', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '50fbe84c-325d-4978-8050-d0350f6a8f19', 'CL 1', 'CL 1', '002d3459-45ad-4f4c-824d-ddca583efb12')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('0903f96f-559c-48f9-96c1-aca566591dcc', 'kuali.lui.type.activity.offering.lecture', '002d3459-45ad-4f4c-824d-ddca583efb12', 'fec124b5-8c1e-4df9-931e-95a3c6ec547c')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('1e499f1b-f807-42ff-91df-d82b90b01930', 0, 'admin', TIMESTAMP '2013-06-24 13:13:43.523', 'admin', TIMESTAMP '2013-06-24 13:13:43.523', 'kuali.atp.2012HalfFall1', 'fd2a8a79-418f-44f2-8d75-9ea7e763c878', '', '', '', 'kuali.lui.activity.offering.state.offered', 'kuali.lui.type.activity.offering.lecture', '', '', 'HIST605 AO', '', '', '95ef4b80-f3d0-498f-b094-c643c5295106')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7ad8dbda-2832-48f0-be3e-ee3a050d800f', 'kuali.attribute.max.enrollment.is.estimate', '95ef4b80-f3d0-498f-b094-c643c5295106', 'null', '05bfe44f-2cd0-46fa-941d-9a7f2d8b15e2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('efb88246-6b3c-478b-8692-ed2bab89eeab', 'kuali.attribute.course.evaluation.indicator', '95ef4b80-f3d0-498f-b094-c643c5295106', 'null', '1aa3346a-6374-4de0-bde7-0d7395a1c30c')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c1be3b54-1e16-4408-b1d2-e1b18c72da4c', 0, 'admin', TIMESTAMP '2013-06-24 13:14:34.13', 'admin', TIMESTAMP '2013-06-24 13:14:34.13', 'A', '', '', '', '95ef4b80-f3d0-498f-b094-c643c5295106', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ef011958-5343-47d6-8983-d3b6415c1736')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('81a47a75-cabd-4486-a3ec-7bfe213711e0', 0, 'admin', TIMESTAMP '2013-06-24 13:13:43.523', 'admin', TIMESTAMP '2013-06-24 13:13:43.523', '', '95ef4b80-f3d0-498f-b094-c643c5295106', '', 'kuali.lu.code.honorsOffering', 'null', '854b67c9-ba9d-4474-aade-d3b0e3378987')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('e8d42122-c311-45ec-9ecc-b34067717ac0', 0, 'admin', TIMESTAMP '2013-06-24 13:13:43.523', 'admin', TIMESTAMP '2013-06-24 13:13:43.523', TIMESTAMP '2013-06-24 13:13:43.758', '', 'HIST605-FO-AO', '50fbe84c-325d-4978-8050-d0350f6a8f19', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST605-FO-AO', '95ef4b80-f3d0-498f-b094-c643c5295106', '3461ab89-e710-460e-848a-beac9398349d')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('fec124b5-8c1e-4df9-931e-95a3c6ec547c', '95ef4b80-f3d0-498f-b094-c643c5295106')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('709ef703-ee8e-4ff6-92da-465e390dac9f', 0, 'admin', TIMESTAMP '2013-06-24 13:13:43.523', 'admin', TIMESTAMP '2013-06-24 13:13:43.523', 'kuali.atp.2012Fall', '08867ede-c389-4a85-9685-857d7495904a', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '6fb15766-da1c-43a5-87a0-9e494d8cc914')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('201325b6-4322-4892-a48e-7b0f44250e30', 'kuali.attribute.registration.group.aocluster.id', '6fb15766-da1c-43a5-87a0-9e494d8cc914', '002d3459-45ad-4f4c-824d-ddca583efb12', '3567dd0d-27b0-4ef2-965c-11fdc02c7b56')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('8bcb8991-5d75-4936-a67a-4506f0c3cd29', 'kuali.attribute.registration.group.is.generated', '6fb15766-da1c-43a5-87a0-9e494d8cc914', '1', '8fac063d-5dd9-419f-977c-a1ca2e224da5')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c0eeca76-223a-4493-bf23-bd829a7dfe6f', 0, 'admin', TIMESTAMP '2013-06-24 13:13:43.523', 'admin', TIMESTAMP '2013-06-24 13:13:43.523', '', '', '', '', '6fb15766-da1c-43a5-87a0-9e494d8cc914', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'dbf53ebb-c9c6-48de-bfd1-d0bce1056743')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ab07d732-1a73-4b51-9b89-1c34e7be53fa', 0, 'admin', TIMESTAMP '2013-06-24 13:13:43.523', 'admin', TIMESTAMP '2013-06-24 13:13:43.523', TIMESTAMP '2013-06-24 13:13:44.527', '', 'HIST605-FO-RG', '50fbe84c-325d-4978-8050-d0350f6a8f19', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST605-FO-RG', '6fb15766-da1c-43a5-87a0-9e494d8cc914', '2291dcf6-59eb-42f1-b8f3-a202cd690980')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('345be80c-6eb8-4c6e-b65d-e3f9e1f56ce9', 0, 'admin', TIMESTAMP '2013-06-24 13:13:43.523', 'admin', TIMESTAMP '2013-06-24 13:13:43.523', TIMESTAMP '2013-06-24 13:13:44.528', '', 'HIST605-RG-AO', '6fb15766-da1c-43a5-87a0-9e494d8cc914', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST605-RG-AO', '95ef4b80-f3d0-498f-b094-c643c5295106', 'e139dd99-107d-4711-ac0e-3b857c4eb5fb')
/


-- HIST808 / 201208 (from catalog)

insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('ba4e9187-5adf-4a8f-8f45-3926c9759392', 0, 'admin', TIMESTAMP '2013-06-24 14:34:34.893', 'admin', TIMESTAMP '2013-06-24 14:34:34.893', 'kuali.atp.2012Fall', 'd94decf7-0866-42da-badd-eebe2e8b9ad9', TIMESTAMP '2012-08-28 20:00:00.0', '', '', 'kuali.lui.course.offering.state.offered', 'kuali.lui.type.course.offering', '', '', 'HIST808 CO', '.', '', '9f7ae659-31d5-42b5-b871-fd47d378d0b7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('dd88b71f-7051-47a5-94d0-6fcbaa9cd917', 'kuali.attribute.wait.list.type.key', '9f7ae659-31d5-42b5-b871-fd47d378d0b7', 'null', '7a5f17be-8bef-45db-8eb3-5f6d9c0d5b34')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('856ebeb5-bfc4-44e3-848d-83af648305f6', 'kuali.attribute.wait.list.level.type.key', '9f7ae659-31d5-42b5-b871-fd47d378d0b7', 'null', '0f5d4a1c-328d-4d93-b309-72b9f2b3d182')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('c20a20bb-50c3-41e3-9ef0-d27a2f5c00c0', 'kuali.attribute.course.number.internal.suffix', '9f7ae659-31d5-42b5-b871-fd47d378d0b7', 'A', '61ed95ea-7770-4a93-9354-315f9a33e0df')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('b0822ed3-a13a-4581-ba59-383d94ae28ab', 'kuali.attribute.funding.source', '9f7ae659-31d5-42b5-b871-fd47d378d0b7', '', '54b5a15d-7083-4a0d-b07a-c5d6d86be4c3')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('5169e18a-494a-4dff-bd57-b11188d59d4c', 'kuali.attribute.final.exam.indicator', '9f7ae659-31d5-42b5-b871-fd47d378d0b7', 'STANDARD', '410df1a5-9d23-4e5b-8fb2-b97483a7bd1e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('1d1f8162-b944-455c-830b-f12207f1c9a3', 'kuali.attribute.where.fees.attached.flag', '9f7ae659-31d5-42b5-b871-fd47d378d0b7', 'null', 'da25470d-5c12-49f8-be22-a48ae1c0fb30')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2cbe0953-9de3-43c9-8e08-acb345b7d67c', 'kuali.attribute.course.evaluation.indicator', '9f7ae659-31d5-42b5-b871-fd47d378d0b7', 'null', '15e29469-f10a-4a38-81c4-51871f0bc812')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2331c48b-65f5-4a1b-82b2-4eb790f696e5', 'kuali.attribute.wait.list.indicator', '9f7ae659-31d5-42b5-b871-fd47d378d0b7', 'null', 'd2c3ba2f-cb8a-4e0f-a384-3eafd16766f9')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('f1304dee-0752-4453-a8a4-7ba22024b31a', 0, 'admin', TIMESTAMP '2013-06-24 14:34:34.893', 'admin', TIMESTAMP '2013-06-24 14:34:34.893', 'HIST808', 'HIST', '', 'Seminar in the History of Science and Technology', '9f7ae659-31d5-42b5-b871-fd47d378d0b7', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'b5f69cc9-3185-484d-a5d4-b5d03ccf81a7')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('f0cfec12-1a07-4bb9-a145-5b2581f8d28b', 0, 'admin', TIMESTAMP '2013-06-24 14:34:34.893', 'admin', TIMESTAMP '2013-06-24 14:34:34.893', '', '9f7ae659-31d5-42b5-b871-fd47d378d0b7', '', 'kuali.lu.code.honorsOffering', '', 'c9c07540-8559-44e2-bebd-883965433335')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('9f7ae659-31d5-42b5-b871-fd47d378d0b7', '3213375036', '46e363b2-208b-4ec5-869b-34a4f645acad')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('9f7ae659-31d5-42b5-b871-fd47d378d0b7', '3213375036')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('9f7ae659-31d5-42b5-b871-fd47d378d0b7', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('9f7ae659-31d5-42b5-b871-fd47d378d0b7', 'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('bbdaa82d-3e8c-441e-a047-6bec3fed06ae', 0, 'admin', TIMESTAMP '2013-06-24 14:34:35.625', 'admin', TIMESTAMP '2013-06-24 14:34:35.625', 'kuali.atp.2012Fall', '391fccd5-0ab0-42aa-9e95-7a9e6363b830', '', '', 'Courses with Lecture Only', 'kuali.lui.format.offering.state.offered', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture Only', '', '0d7f221e-0223-4cb3-a364-906ea516a9f4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('a47fffd5-66e3-447e-84ed-13443cff4443', 'regCodePrefix', '0d7f221e-0223-4cb3-a364-906ea516a9f4', '1', 'b0185586-0ad3-4b1f-944c-a175788eee08')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6d33cbad-f9d3-4e88-9800-674cea7584a2', 'kuali.attribute.final.exam.level.type', '0d7f221e-0223-4cb3-a364-906ea516a9f4', 'kuali.lu.type.activity.Lecture', '6109d8d0-c870-42a2-becb-f838b0305f32')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('59dec1bd-c4c0-4a65-95a4-26aed508722f', 'kuali.attribute.grade.roster.level.type.key', '0d7f221e-0223-4cb3-a364-906ea516a9f4', 'kuali.lu.type.activity.Lecture', '951acfe7-35db-44f7-8c5e-aeaff448c54a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('b71215cb-f213-4520-b3d0-903c3f5046fb', 0, 'admin', TIMESTAMP '2013-06-24 14:34:35.625', 'admin', TIMESTAMP '2013-06-24 14:34:35.625', '', '', '', 'Lecture Only', '0d7f221e-0223-4cb3-a364-906ea516a9f4', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'a9221b3d-9cc8-4b74-aa76-884f7e7cf26b')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('fe06a8ba-ec7b-4a64-9c97-5492fea9c7f6', 0, 'admin', TIMESTAMP '2013-06-24 14:34:35.625', 'admin', TIMESTAMP '2013-06-24 14:34:35.625', TIMESTAMP '2013-06-24 14:34:35.738', '', 'HIST808-CO-FO', '9f7ae659-31d5-42b5-b871-fd47d378d0b7', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'HIST808-CO-FO', '0d7f221e-0223-4cb3-a364-906ea516a9f4', '7d94ddb5-4541-4b73-b44d-5b148aa22d00')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('0d7f221e-0223-4cb3-a364-906ea516a9f4', 'kuali.lui.type.activity.offering.lecture')
/

-- Adding AO

insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('d76e28cc-5a19-450b-8ad7-cfb8a9410dfe', 0, 'admin', TIMESTAMP '2013-06-24 14:36:37.082', 'admin', TIMESTAMP '2013-06-24 14:36:37.082', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', '0d7f221e-0223-4cb3-a364-906ea516a9f4', 'CL 1', 'CL 1', '952bd0bb-0e61-449b-9c87-12a66d9f9d7d')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('25ef608a-1132-470b-859b-55268a1167b5', 'kuali.lui.type.activity.offering.lecture', '952bd0bb-0e61-449b-9c87-12a66d9f9d7d', 'dde6f4ec-ece7-4eb0-890a-2cbbbc05c041')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('8dc92c5e-6117-433b-8173-93ef137f3e5f', 0, 'admin', TIMESTAMP '2013-06-24 14:36:37.082', 'admin', TIMESTAMP '2013-06-24 14:36:37.082', 'kuali.atp.2012HalfFall2', '609a8232-1811-4533-bd6e-605644493dbf', '', '', '', 'kuali.lui.activity.offering.state.offered', 'kuali.lui.type.activity.offering.lecture', '', '', 'HIST808 AO', '', '', '2bddd4dd-c4fa-468a-906e-e671fdddf7de')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('e2edb393-b9b4-41d7-b57d-5b778200613e', 'kuali.attribute.course.evaluation.indicator', '2bddd4dd-c4fa-468a-906e-e671fdddf7de', 'null', '1aa52461-9eac-45fa-adb1-ffbc33c28ab2')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('40693a35-f745-4998-822f-460d2d06222b', 'kuali.attribute.max.enrollment.is.estimate', '2bddd4dd-c4fa-468a-906e-e671fdddf7de', 'null', '58165db2-b65a-440e-974f-5c09f9a4c84b')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('12d99bcb-32df-4533-bf5a-359fbb10e487', 0, 'admin', TIMESTAMP '2013-06-24 14:37:36.477', 'admin', TIMESTAMP '2013-06-24 14:37:36.477', 'A', '', '', '', '2bddd4dd-c4fa-468a-906e-e671fdddf7de', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '595144af-4046-449b-b434-e578418944b2')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('0032b6ea-1024-4299-9c75-a90822111e19', 0, 'admin', TIMESTAMP '2013-06-24 14:36:37.082', 'admin', TIMESTAMP '2013-06-24 14:36:37.082', '', '2bddd4dd-c4fa-468a-906e-e671fdddf7de', '', 'kuali.lu.code.honorsOffering', 'null', 'f430fab9-eca3-439a-aa88-fef61f631f6c')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ffcf9b32-a740-4ed5-ba0b-a069a1ba3014', 0, 'admin', TIMESTAMP '2013-06-24 14:36:37.082', 'admin', TIMESTAMP '2013-06-24 14:36:37.082', TIMESTAMP '2013-06-24 14:36:38.5', '', 'HIST808-FO-AO', '0d7f221e-0223-4cb3-a364-906ea516a9f4', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'HIST808-FO-AO', '2bddd4dd-c4fa-468a-906e-e671fdddf7de', '9ba1f7e9-75b4-43f1-afda-28d37308b6a4')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('dde6f4ec-ece7-4eb0-890a-2cbbbc05c041', '2bddd4dd-c4fa-468a-906e-e671fdddf7de')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('2f854502-fac6-4148-b861-2a1fc8f3233a', 0, 'admin', TIMESTAMP '2013-06-24 14:36:37.082', 'admin', TIMESTAMP '2013-06-24 14:36:37.082', 'kuali.atp.2012Fall', '391fccd5-0ab0-42aa-9e95-7a9e6363b830', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '81378e30-1f96-4701-8bd0-b78b58698fb7')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6f47c808-c0e5-4a27-b939-f51bf70ae669', 'kuali.attribute.registration.group.is.generated', '81378e30-1f96-4701-8bd0-b78b58698fb7', '1', 'eba02514-0084-4ceb-bc29-2e423dfe5efe')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('edf6dd6f-d84a-4ef5-b56a-dcc8318d2f89', 'kuali.attribute.registration.group.aocluster.id', '81378e30-1f96-4701-8bd0-b78b58698fb7', '952bd0bb-0e61-449b-9c87-12a66d9f9d7d', 'c1386722-03d2-4899-9787-7d89bbdde093')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('7d59aac7-4e78-4026-980b-83fcfbc14f64', 0, 'admin', TIMESTAMP '2013-06-24 14:36:37.082', 'admin', TIMESTAMP '2013-06-24 14:36:37.082', '', '', '', '', '81378e30-1f96-4701-8bd0-b78b58698fb7', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'f5871fe3-da65-45af-842c-d3d832e6a831')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('17ae9fde-30ae-4378-80e6-5f211fc59afb', 0, 'admin', TIMESTAMP '2013-06-24 14:36:37.082', 'admin', TIMESTAMP '2013-06-24 14:36:37.082', TIMESTAMP '2013-06-24 14:36:40.793', '', 'HIST808-FO-RG', '0d7f221e-0223-4cb3-a364-906ea516a9f4', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'HIST808-FO-RG', '81378e30-1f96-4701-8bd0-b78b58698fb7', '1407f3ac-7f3d-400e-8353-8b8b80d8aaf6')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('ba4f4a4c-a6bd-44a7-b3e3-c0316c227b79', 0, 'admin', TIMESTAMP '2013-06-24 14:36:37.082', 'admin', TIMESTAMP '2013-06-24 14:36:37.082', TIMESTAMP '2013-06-24 14:36:40.795', '', 'HIST808-RG-AO', '81378e30-1f96-4701-8bd0-b78b58698fb7', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'HIST808-RG-AO', '2bddd4dd-c4fa-468a-906e-e671fdddf7de', 'd73b8e7d-6a7a-4ab3-b0a6-e1ec4a12ba2f')
/


-- BSCI122 / 201208 (from catalog)

insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('8c18787a-5008-4701-8417-27ce149d56e3', 0, 'admin', TIMESTAMP '2013-06-25 11:49:35.255', 'admin', TIMESTAMP '2013-06-25 11:49:35.255', 'kuali.atp.2012Fall', 'd1a6aed5-01f8-4147-8818-7f39d7fb103f', TIMESTAMP '2012-08-28 20:00:00.0', '', 'Introduction to the historical, societal and conceptual aspects of microbiology and biotechnology. Course not acceptable toward major requirements in the College of Chemistry and Life Sciences.', 'kuali.lui.course.offering.state.offered', 'kuali.lui.type.course.offering', '', '', 'BSCI122 CO', 'Introduction to the historical, societal and conceptual aspects of microbiology and biotechnology. Course not acceptable toward major requirements in the College of Chemistry and Life Sciences.', '', 'ba39f517-93d2-47f9-ad40-fff5273ef750')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('2d27d16d-6d76-4f16-b891-26bf95de396e', 'kuali.attribute.course.number.internal.suffix', 'ba39f517-93d2-47f9-ad40-fff5273ef750', 'A', 'c74bfa01-f472-4f9c-81c5-496dc1f4f56e')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6151c8ce-1469-4d9e-9bf6-b37f9d5e08f5', 'kuali.attribute.wait.list.level.type.key', 'ba39f517-93d2-47f9-ad40-fff5273ef750', 'null', 'cf514148-1256-484c-be5b-3dc8e22cb389')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('29d1ecce-ece2-4a00-aea7-57d419afaaca', 'kuali.attribute.where.fees.attached.flag', 'ba39f517-93d2-47f9-ad40-fff5273ef750', 'null', '1ccf4557-417b-4b69-a2d0-479fe175e56c')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9cdd0551-358a-4110-bd44-cec507d48388', 'kuali.attribute.funding.source', 'ba39f517-93d2-47f9-ad40-fff5273ef750', '', 'b707eb43-ba04-4cda-bad8-03602078a693')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('bea22128-2d49-4e47-9cd4-0934391518ac', 'kuali.attribute.final.exam.indicator', 'ba39f517-93d2-47f9-ad40-fff5273ef750', 'STANDARD', '1678907f-c663-4e72-a8dd-5eefe03ff086')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ffe1c9b9-23ae-4134-aa39-33cb68c01cc7', 'kuali.attribute.wait.list.type.key', 'ba39f517-93d2-47f9-ad40-fff5273ef750', 'null', '8199f877-39b9-4d77-ba1d-cc3068e79223')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('0f131f79-aa2c-40f8-9be8-9f44d672cc08', 'kuali.attribute.course.evaluation.indicator', 'ba39f517-93d2-47f9-ad40-fff5273ef750', 'null', 'be191005-2ffb-4b39-8a82-2bb6bade410f')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('6533b523-da21-4208-a260-02b79e03c622', 'kuali.attribute.wait.list.indicator', 'ba39f517-93d2-47f9-ad40-fff5273ef750', 'null', '4c25b267-bb54-4f67-ab1a-42ca09b8c37f')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('50e1a55c-4764-42c4-b26f-7dd1528199ac', 0, 'admin', TIMESTAMP '2013-06-25 11:49:35.255', 'admin', TIMESTAMP '2013-06-25 11:49:35.255', 'BSCI122', 'BSCI', '', 'Microbes and Society', 'ba39f517-93d2-47f9-ad40-fff5273ef750', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'c0f74ca3-5da7-4589-9089-bf8521e25dd2')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('17af1eb1-3deb-4e96-9570-99abdde8d2bd', 0, 'admin', TIMESTAMP '2013-06-25 11:49:35.255', 'admin', TIMESTAMP '2013-06-25 11:49:35.255', '', 'ba39f517-93d2-47f9-ad40-fff5273ef750', '', 'kuali.lu.code.honorsOffering', '', '4fa4b3f3-c19b-4379-8866-3ea9693ec619')
/
insert into KSEN_LUI_UNITS_DEPLOYMENT (LUI_ID, ORG_ID, ID) values ('ba39f517-93d2-47f9-ad40-fff5273ef750', '576639460', 'a7ddb5fb-a7ba-4dae-af14-4953269f9f62')
/
insert into KSEN_LUI_UNITS_CONT_OWNER (LUI_ID, ORG_ID) values ('ba39f517-93d2-47f9-ad40-fff5273ef750', '576639460')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ba39f517-93d2-47f9-ad40-fff5273ef750', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ba39f517-93d2-47f9-ad40-fff5273ef750', 'kuali.resultComponent.grade.passFail')
/
insert into KSEN_LUI_RESULT_VAL_GRP (LUI_ID, RESULT_VAL_GRP_ID) values ('ba39f517-93d2-47f9-ad40-fff5273ef750', 'kuali.creditType.credit.degree.4.0')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('6e9a3eeb-c407-4ac1-9287-5fd00bfec7f8', 0, 'admin', TIMESTAMP '2013-06-25 11:49:36.142', 'admin', TIMESTAMP '2013-06-25 11:49:36.142', 'kuali.atp.2012Fall', 'bd336eee-464c-4c40-bcfe-a5285f488bb2', '', '', 'Courses with Lecture Only', 'kuali.lui.format.offering.state.offered', 'kuali.lui.type.course.format.offering', '', '', 'Lecture Only', 'Courses with Lecture Only', '', 'f0494038-407c-49f8-99d1-574895ac9091')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ba994f34-530b-4586-9dcc-08384744dd77', 'kuali.attribute.final.exam.level.type', 'f0494038-407c-49f8-99d1-574895ac9091', 'kuali.lu.type.activity.Lecture', '8b0bf7e0-c348-4937-8c9c-60b2c1411a58')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('40347fd9-f513-4d4b-9acb-a86404f0afa6', 'kuali.attribute.grade.roster.level.type.key', 'f0494038-407c-49f8-99d1-574895ac9091', 'kuali.lui.type.course.offering', '7a44bb20-64bd-4c8f-b9f2-33be7d1009c5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('d79ef7cc-773b-4e31-a5b1-5e108a0d6681', 'regCodePrefix', 'f0494038-407c-49f8-99d1-574895ac9091', '1', 'b7cd5550-3cdc-4ba9-aef1-633ac907dc07')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('c286aab1-060a-48e0-bc89-2f1c69613c8c', 0, 'admin', TIMESTAMP '2013-06-25 11:49:36.142', 'admin', TIMESTAMP '2013-06-25 11:49:36.142', '', '', '', 'Lecture Only', 'f0494038-407c-49f8-99d1-574895ac9091', '', 'LEC', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', 'ee61eb9c-220d-4680-b2ff-ac969db72b01')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('4453e966-676a-4b0e-8669-27df9b70d4c7', 0, 'admin', TIMESTAMP '2013-06-25 11:49:36.142', 'admin', TIMESTAMP '2013-06-25 11:49:36.142', TIMESTAMP '2013-06-25 11:49:36.392', '', 'BSCI122-CO-FO', 'ba39f517-93d2-47f9-ad40-fff5273ef750', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.co2fo', 'co-fo-relation', 'BSCI122-CO-FO', 'f0494038-407c-49f8-99d1-574895ac9091', '8b53f993-d777-4b48-87b3-ec338cf38300')
/
insert into KSEN_LUI_RELATED_LUI_TYPES (LUI_ID, RELATED_LUI_TYPE) values ('f0494038-407c-49f8-99d1-574895ac9091', 'kuali.lui.type.activity.offering.lecture')
/

-- Adding AO

insert into KSEN_CO_AO_CLUSTER (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, AO_CLUSTER_STATE, AO_CLUSTER_TYPE, DESCR_FORMATTED, DESCR_PLAIN, FORMAT_OFFERING_ID, NAME, PRIVATE_NAME, ID) values ('fd7c6061-25bf-4036-a9fe-ccd27e1f569a', 0, 'admin', TIMESTAMP '2013-06-25 11:51:19.867', 'admin', TIMESTAMP '2013-06-25 11:51:19.867', 'kuali.activity.offering.cluster.state.active', 'kuali.activity.offering.cluster.type.activity.offering.cluster', '', '', 'f0494038-407c-49f8-99d1-574895ac9091', 'CL 1', 'CL 1', '69a8eee8-8f34-4304-9ceb-103787ab3e83')
/
insert into KSEN_CO_AO_CLUSTER_SET (OBJ_ID, ACTIVITY_OFFERING_TYPE, AO_CLUSTER_ID, ID) values ('02f83c13-81d0-4bc8-b825-60fc76e0ddce', 'kuali.lui.type.activity.offering.lecture', '69a8eee8-8f34-4304-9ceb-103787ab3e83', '400699c5-450f-4514-a2f7-3c335169e8c9')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('7f42a149-a09e-4fee-804c-b6c01a956bdf', 0, 'admin', TIMESTAMP '2013-06-25 11:51:19.867', 'admin', TIMESTAMP '2013-06-25 11:51:19.867', 'kuali.atp.2012HalfFall2', '7aede248-1636-4bd1-9293-8e41231076f7', '', '', '', 'kuali.lui.activity.offering.state.offered', 'kuali.lui.type.activity.offering.lecture', '', '', 'BSCI122 AO', '', '', '87907ef7-256e-4cc5-8aa2-6edb16a44c19')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('9d40486c-6532-4bdc-85f9-486e97163258', 'kuali.attribute.max.enrollment.is.estimate', '87907ef7-256e-4cc5-8aa2-6edb16a44c19', 'null', '118e3e96-3dce-4017-b544-a909bad9bdb5')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('862b34a8-6d07-4ed0-9fb0-8d59c5505d81', 'kuali.attribute.course.evaluation.indicator', '87907ef7-256e-4cc5-8aa2-6edb16a44c19', 'null', '0b53d42b-ef1e-4df5-baaa-b5a1d1f7c6be')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('2dafbc65-f8b2-4bf8-b019-28b6fc7825bf', 0, 'admin', TIMESTAMP '2013-06-25 11:52:18.495', 'admin', TIMESTAMP '2013-06-25 11:52:18.495', 'A', '', '', '', '87907ef7-256e-4cc5-8aa2-6edb16a44c19', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '1b7845b5-bbe6-4a8f-8e92-efae818dbd83')
/
insert into KSEN_LUI_LU_CD (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, DESCR_FORMATTED, LUI_ID, DESCR_PLAIN, LUI_LUCD_TYPE, VALUE, ID) values ('937744f3-ebde-416a-a051-01bb91912fd3', 0, 'admin', TIMESTAMP '2013-06-25 11:51:19.867', 'admin', TIMESTAMP '2013-06-25 11:51:19.867', '', '87907ef7-256e-4cc5-8aa2-6edb16a44c19', '', 'kuali.lu.code.honorsOffering', 'null', 'a63b164a-49f6-4d1e-a9aa-68ac1375b800')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('fa985d15-573f-42f9-a82b-d6cd7b4e3718', 0, 'admin', TIMESTAMP '2013-06-25 11:51:19.867', 'admin', TIMESTAMP '2013-06-25 11:51:19.867', TIMESTAMP '2013-06-25 11:51:20.934', '', 'BSCI122-FO-AO', 'f0494038-407c-49f8-99d1-574895ac9091', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2ao', 'fo-ao-relation', 'BSCI122-FO-AO', '87907ef7-256e-4cc5-8aa2-6edb16a44c19', '903ff419-47af-4444-9e47-90fe6f328089')
/
insert into KSEN_CO_AO_CLUSTER_SET_AO (AOC_SET_ID, ACTIVITY_OFFERING_ID) values ('400699c5-450f-4514-a2f7-3c335169e8c9', '87907ef7-256e-4cc5-8aa2-6edb16a44c19')
/
insert into KSEN_LUI (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, CLU_ID, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_STATE, LUI_TYPE, MAX_SEATS, MIN_SEATS, NAME, DESCR_PLAIN, REF_URL, ID) values ('aa52c43c-6e82-49fc-aa79-e9cc13e1984e', 0, 'admin', TIMESTAMP '2013-06-25 11:51:19.867', 'admin', TIMESTAMP '2013-06-25 11:51:19.867', 'kuali.atp.2012Fall', 'bd336eee-464c-4c40-bcfe-a5285f488bb2', '', '', '1001', 'kuali.lui.registration.group.state.pending', 'kuali.lui.type.registration.group', '', '', '1001', '1001', '', '36b60efc-9180-4d09-a5d3-64527d7d44d4')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('7e8b3ae8-456e-4f30-a14d-d9d0afff5c4d', 'kuali.attribute.registration.group.is.generated', '36b60efc-9180-4d09-a5d3-64527d7d44d4', '1', '46d02e42-60c1-487b-91b6-d3b32a5da9ad')
/
insert into KSEN_LUI_ATTR (OBJ_ID, ATTR_KEY, OWNER_ID, ATTR_VALUE, ID) values ('ebb27f06-fb22-4faa-8f2c-365d5503d1b6', 'kuali.attribute.registration.group.aocluster.id', '36b60efc-9180-4d09-a5d3-64527d7d44d4', '69a8eee8-8f34-4304-9ceb-103787ab3e83', '617ca972-3e87-4f28-943c-4722e840c86a')
/
insert into KSEN_LUI_IDENT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, LUI_CD, DIVISION, IDENT_LEVEL, LNG_NAME, LUI_ID, ORGID, SHRT_NAME, LUI_ID_STATE, SUFX_CD, LUI_ID_TYPE, VARTN, ID) values ('cac36e7d-1702-403f-87e5-cce674ff00bf', 0, 'admin', TIMESTAMP '2013-06-25 11:51:19.867', 'admin', TIMESTAMP '2013-06-25 11:51:19.867', '', '', '', '', '36b60efc-9180-4d09-a5d3-64527d7d44d4', '', '', 'kuali.lui.identifier.state.active', '', 'kuali.lui.identifier.type.official', '', '890389fc-a711-4207-b5cf-5f59df88656e')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('152299f8-7d45-4016-8455-1bb2ce163211', 0, 'admin', TIMESTAMP '2013-06-25 11:51:19.867', 'admin', TIMESTAMP '2013-06-25 11:51:19.867', TIMESTAMP '2013-06-25 11:51:22.247', '', 'BSCI122-FO-RG', 'f0494038-407c-49f8-99d1-574895ac9091', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.deliveredvia.fo2rg', 'fo-rg-relation', 'BSCI122-FO-RG', '36b60efc-9180-4d09-a5d3-64527d7d44d4', '71e83997-c6f9-4876-8dc7-78d89b40c427')
/
insert into KSEN_LUILUI_RELTN (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, EFF_DT, EXPIR_DT, DESCR_FORMATTED, LUI_ID, LUILUI_RELTN_STATE, LUILUI_RELTN_TYPE, NAME, DESCR_PLAIN, RELATED_LUI_ID, ID) values ('8ca6a4ef-c4c5-40d5-9414-6b1807302057', 0, 'admin', TIMESTAMP '2013-06-25 11:51:19.867', 'admin', TIMESTAMP '2013-06-25 11:51:19.867', TIMESTAMP '2013-06-25 11:51:22.248', '', 'BSCI122-RG-AO', '36b60efc-9180-4d09-a5d3-64527d7d44d4', 'kuali.lui.lui.relation.state.active', 'kuali.lui.lui.relation.type.registeredforvia.rg2ao', 'rg-ao-relation', 'BSCI122-RG-AO', '87907ef7-256e-4cc5-8aa2-6edb16a44c19', 'd2adc972-ac33-4912-9cae-9d4c2ba96ef7')
/