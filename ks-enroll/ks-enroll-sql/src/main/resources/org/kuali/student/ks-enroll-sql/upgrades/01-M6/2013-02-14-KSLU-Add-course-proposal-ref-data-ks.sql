insert into KSPR_PROPOSAL_REFERENCE (OBJ_ID, VER_NBR, OBJECT_REFERENCE_ID, TYPE, REFERENCE_ID) values ('21fdc240-7bd3-4693-80d0-d5fe8033f5e2', 0, '07466ab6-6b05-4823-83eb-94f3ced526e3', 'kuali.proposal.referenceType.clu', 'fafb29fc-2a9b-4574-92b8-0592678a339e')
/
insert into KSPR_PROPOSAL (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, DETAIL_DESC, EFF_DT, EXPIR_DT, NAME, RATIONALE, STATE, TYPE, WORKFLOW_ID, PROPOSAL_ID) values ('2024ef1c-e22d-4086-ae4a-bea9b9580b01', 0, 'admin', TIMESTAMP '2013-02-18 13:11:27.372', 'admin', TIMESTAMP '2013-02-18 13:11:27.372', '', '', '', 'Intro to Biology', '', 'Saved', 'kuali.proposal.type.course.create', '', 'd4d0b3ce-8bb4-41c1-a629-ae26f1bd1262')
/
insert into KSPR_PROPOSAL_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('e2f6e005-dd3c-4e21-9d37-548b8e8377e0', 0, 'workflowNode', 'PreRoute', 'd4d0b3ce-8bb4-41c1-a629-ae26f1bd1262', 'dd8a232b-708d-4c2a-823f-49ef0b520d3a')
/
insert into KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) values ('d4d0b3ce-8bb4-41c1-a629-ae26f1bd1262', 'fafb29fc-2a9b-4574-92b8-0592678a339e')
/
update KSPR_PROPOSAL set OBJ_ID='2024ef1c-e22d-4086-ae4a-bea9b9580b01', VER_NBR=1, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:11:28.058', DETAIL_DESC='', EFF_DT='', EXPIR_DT='', NAME='Intro to Biology', RATIONALE='', STATE='Saved', TYPE='kuali.proposal.type.course.create', WORKFLOW_ID='3011' where PROPOSAL_ID='d4d0b3ce-8bb4-41c1-a629-ae26f1bd1262' and VER_NBR=0
/
insert into KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) values ('d4d0b3ce-8bb4-41c1-a629-ae26f1bd1262', 'fafb29fc-2a9b-4574-92b8-0592678a339e')
/
insert into KSLU_CLU_ACCT (OBJ_ID, VER_NBR, ID) values ('0b19b6ab-a7f2-419b-8a33-ebf321d54e7f', 0, '5b915ba6-0bdb-4893-a0c6-47ecf0b6c4ec')
/
insert into KSLU_CLU_FEE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, ID) values ('2e47a00d-4310-4965-b7d6-4452e0bbd6c8', 0, 'admin', TIMESTAMP '2013-02-18 13:11:27.306', 'admin', TIMESTAMP '2013-02-18 13:11:27.306', '', '9014a1df-6eee-4f3e-b99f-6c300f28cd45')
/
insert into KSLU_CLU_IDENT (OBJ_ID, VER_NBR, CD, DIVISION, LVL, LNG_NAME, ORG_ID, SHRT_NAME, ST, SUFX_CD, TYPE, VARTN, ID) values ('3bdab3cb-a0d6-4bbd-9be1-36311a95d7d5', 0, 'BSCI101', 'BSCI', '100', 'Intro to Biology', '', 'Intro to Biology', 'draft', '101', 'kuali.lu.type.CreditCourse.identifier.official', '', 'ec6b3961-4969-4994-8545-76f1d796ca4a')
/
insert into KSLU_CLU (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CURR_VER_END, CURR_VER_START, SEQ_NUM, VER_CMT, VER_IND_ID, VER_FROM_ID, ACCT_ID, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, RT_DESCR_ID, EFF_DT, IS_ENRL, EXP_FIRST_ATP, EXPIR_DT, FEE_ID, HAS_EARLY_DROP_DEDLN, IS_HAZR_DISBLD_STU, CLU_INTSTY_QTY, CLU_INTSTY_TYPE, LAST_ADMIT_ATP, LAST_ATP, LUTYPE_ID, NEXT_REVIEW_PRD, OFFIC_CLU_ID, PRI_INSTR_ID, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ID) values ('2520f535-4581-416b-8f24-d1e10b037b04', 0, 'admin', TIMESTAMP '2013-02-18 13:11:27.301', 'admin', TIMESTAMP '2013-02-18 13:11:27.301', '', TIMESTAMP '2013-02-18 13:11:27.298', 1, '', 'edb51160-c8a0-4e22-a1b1-92b558651b7d', '', '5b915ba6-0bdb-4893-a0c6-47ecf0b6c4ec', '0', 0, 0, '', '', '0', '', '', '9014a1df-6eee-4f3e-b99f-6c300f28cd45', '0', '0', '', '', '', '', 'kuali.lu.type.CreditCourse', '', 'ec6b3961-4969-4994-8545-76f1d796ca4a', '', '', 'draft', '', '', '', '07466ab6-6b05-4823-83eb-94f3ced526e3')
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('7f8f55cc-20b0-4c9a-ad45-18b0e62d6fa3', 0, '', 'Grading options', '9e379e41-0341-4566-ad5f-1e57deb83751')
/
insert into KSLU_CLU_RSLT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, CLU_ID, TYPE_KEY_ID, RT_DESCR_ID, EFF_DT, EXPIR_DT, ST, ID) values ('445c4ea9-c765-4ddd-acb3-8cfefa013c32', 0, 'admin', TIMESTAMP '2013-02-18 13:11:27.217', 'admin', TIMESTAMP '2013-02-18 13:11:27.325', '07466ab6-6b05-4823-83eb-94f3ced526e3', 'kuali.resultType.gradeCourseResult', '9e379e41-0341-4566-ad5f-1e57deb83751', TIMESTAMP '2013-02-18 13:11:27.27', '', 'draft', '1b6c1738-1cdd-4c82-9afe-d525a93136f7')
/

insert into KSLU_CLU_ADMIN_ORG (OBJ_ID, VER_NBR, CLU_ID, IS_PR, ORG_ID, TYPE, ID) values ('528e801c-625d-4da6-adda-5b70b62179ed', 0, '07466ab6-6b05-4823-83eb-94f3ced526e3', '0', '4269648307', 'kuali.adminOrg.type.CurriculumOversight', '3c99582f-0ace-4294-8d3f-a2ac66a53947')
/
update KSLU_CLU_ACCT set OBJ_ID='0b19b6ab-a7f2-419b-8a33-ebf321d54e7f', VER_NBR=1 where ID='5b915ba6-0bdb-4893-a0c6-47ecf0b6c4ec' and VER_NBR=0
/
update KSLU_CLU_FEE set OBJ_ID='2e47a00d-4310-4965-b7d6-4452e0bbd6c8', VER_NBR=1, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:11:43.615', RT_DESCR_ID='' where ID='9014a1df-6eee-4f3e-b99f-6c300f28cd45' and VER_NBR=0
/
update KSLU_CLU_IDENT set OBJ_ID='3bdab3cb-a0d6-4bbd-9be1-36311a95d7d5', VER_NBR=1, CD='BSCI101', DIVISION='BSCI', LVL='100', LNG_NAME='Intro to Biology', ORG_ID='', SHRT_NAME='Intro to Biology', ST='draft', SUFX_CD='101', TYPE='kuali.lu.type.CreditCourse.identifier.official', VARTN='' where ID='ec6b3961-4969-4994-8545-76f1d796ca4a' and VER_NBR=0
/
update KSLU_CLU set OBJ_ID='2520f535-4581-416b-8f24-d1e10b037b04', VER_NBR=1, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:11:43.615', CURR_VER_END='', CURR_VER_START=TIMESTAMP '2013-02-18 13:11:27.298', SEQ_NUM=1, VER_CMT='', VER_IND_ID='edb51160-c8a0-4e22-a1b1-92b558651b7d', VER_FROM_ID='', ACCT_ID='5b915ba6-0bdb-4893-a0c6-47ecf0b6c4ec', CAN_CREATE_LUI='0', DEF_ENRL_EST=0, DEF_MAX_ENRL=0, RT_DESCR_ID='', EFF_DT='', IS_ENRL='0', EXP_FIRST_ATP='', EXPIR_DT='', FEE_ID='9014a1df-6eee-4f3e-b99f-6c300f28cd45', HAS_EARLY_DROP_DEDLN='0', IS_HAZR_DISBLD_STU='0', CLU_INTSTY_QTY='', CLU_INTSTY_TYPE='', LAST_ADMIT_ATP='', LAST_ATP='', LUTYPE_ID='kuali.lu.type.CreditCourse', NEXT_REVIEW_PRD='', OFFIC_CLU_ID='ec6b3961-4969-4994-8545-76f1d796ca4a', PRI_INSTR_ID='', REF_URL='', ST='draft', ATP_DUR_TYP_KEY='', TM_QUANTITY='', STDY_SUBJ_AREA='' where ID='07466ab6-6b05-4823-83eb-94f3ced526e3' and VER_NBR=0
/
update KSLU_CLU_ADMIN_ORG set OBJ_ID='528e801c-625d-4da6-adda-5b70b62179ed', VER_NBR=1, CLU_ID='07466ab6-6b05-4823-83eb-94f3ced526e3', IS_PR='0', ORG_ID='4269648307', TYPE='kuali.adminOrg.type.CurriculumOversight' where ID='3c99582f-0ace-4294-8d3f-a2ac66a53947' and VER_NBR=0
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('bcb6eb81-15e2-4019-a521-878461d99819', 0, '', 'Grading options', '19fdd105-439d-496a-ab34-adbde3497f0b')
/
update KSLU_CLU_RSLT set OBJ_ID='445c4ea9-c765-4ddd-acb3-8cfefa013c32', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-02-18 13:11:27.325', CLU_ID='07466ab6-6b05-4823-83eb-94f3ced526e3', TYPE_KEY_ID='kuali.resultType.gradeCourseResult', RT_DESCR_ID='19fdd105-439d-496a-ab34-adbde3497f0b', EFF_DT=TIMESTAMP '2013-02-18 13:11:27.27', EXPIR_DT='', ST='draft' where ID='1b6c1738-1cdd-4c82-9afe-d525a93136f7' and VER_NBR=0
/

insert into KSPR_PROPOSAL_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('1f4c0007-bf60-405d-83fd-8fd3c98ac48e', 0, 'workflowNode', 'PreRoute', 'd4d0b3ce-8bb4-41c1-a629-ae26f1bd1262', '47bab146-a95e-4219-a524-41c0f9035033')
/
update KSPR_PROPOSAL set OBJ_ID='2024ef1c-e22d-4086-ae4a-bea9b9580b01', VER_NBR=2, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:11:43.83', DETAIL_DESC='', EFF_DT='', EXPIR_DT='', NAME='Intro to Biology', RATIONALE='', STATE='Saved', TYPE='kuali.proposal.type.course.create', WORKFLOW_ID='3011' where PROPOSAL_ID='d4d0b3ce-8bb4-41c1-a629-ae26f1bd1262' and VER_NBR=1
/
insert into KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) values ('d4d0b3ce-8bb4-41c1-a629-ae26f1bd1262', 'fafb29fc-2a9b-4574-92b8-0592678a339e')
/

insert into KSLU_CLU_ADMIN_ORG (OBJ_ID, VER_NBR, CLU_ID, IS_PR, ORG_ID, TYPE, ID) values ('b955f650-d71a-4ef3-8c2c-68075a72bc43', 0, '07466ab6-6b05-4823-83eb-94f3ced526e3', '0', '4269648307', 'kuali.adminOrg.type.CurriculumOversight', 'a182866b-e73b-4c37-a77b-c1745ecf9ca1')
/
update KSLU_CLU_ACCT set OBJ_ID='0b19b6ab-a7f2-419b-8a33-ebf321d54e7f', VER_NBR=2 where ID='5b915ba6-0bdb-4893-a0c6-47ecf0b6c4ec' and VER_NBR=1
/
update KSLU_CLU_FEE set OBJ_ID='2e47a00d-4310-4965-b7d6-4452e0bbd6c8', VER_NBR=2, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:12:03.972', RT_DESCR_ID='' where ID='9014a1df-6eee-4f3e-b99f-6c300f28cd45' and VER_NBR=1
/
update KSLU_CLU_IDENT set OBJ_ID='3bdab3cb-a0d6-4bbd-9be1-36311a95d7d5', VER_NBR=2, CD='BSCI101', DIVISION='BSCI', LVL='100', LNG_NAME='Intro to Biology', ORG_ID='', SHRT_NAME='Intro to Biology', ST='draft', SUFX_CD='101', TYPE='kuali.lu.type.CreditCourse.identifier.official', VARTN='' where ID='ec6b3961-4969-4994-8545-76f1d796ca4a' and VER_NBR=1
/
update KSLU_CLU set OBJ_ID='2520f535-4581-416b-8f24-d1e10b037b04', VER_NBR=2, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:12:03.973', CURR_VER_END='', CURR_VER_START=TIMESTAMP '2013-02-18 13:11:27.298', SEQ_NUM=1, VER_CMT='', VER_IND_ID='edb51160-c8a0-4e22-a1b1-92b558651b7d', VER_FROM_ID='', ACCT_ID='5b915ba6-0bdb-4893-a0c6-47ecf0b6c4ec', CAN_CREATE_LUI='0', DEF_ENRL_EST=0, DEF_MAX_ENRL=0, RT_DESCR_ID='', EFF_DT=TIMESTAMP '2015-08-31 00:00:00.0', IS_ENRL='0', EXP_FIRST_ATP='kuali.atp.2015Fall', EXPIR_DT='', FEE_ID='9014a1df-6eee-4f3e-b99f-6c300f28cd45', HAS_EARLY_DROP_DEDLN='0', IS_HAZR_DISBLD_STU='0', CLU_INTSTY_QTY='', CLU_INTSTY_TYPE='', LAST_ADMIT_ATP='', LAST_ATP='', LUTYPE_ID='kuali.lu.type.CreditCourse', NEXT_REVIEW_PRD='', OFFIC_CLU_ID='ec6b3961-4969-4994-8545-76f1d796ca4a', PRI_INSTR_ID='', REF_URL='', ST='draft', ATP_DUR_TYP_KEY='', TM_QUANTITY='', STDY_SUBJ_AREA='' where ID='07466ab6-6b05-4823-83eb-94f3ced526e3' and VER_NBR=1
/
update KSLU_CLU_ADMIN_ORG set OBJ_ID='b955f650-d71a-4ef3-8c2c-68075a72bc43', VER_NBR=1, CLU_ID='07466ab6-6b05-4823-83eb-94f3ced526e3', IS_PR='0', ORG_ID='4269648307', TYPE='kuali.adminOrg.type.CurriculumOversight' where ID='a182866b-e73b-4c37-a77b-c1745ecf9ca1' and VER_NBR=0
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('8b0cf423-3084-4f52-9953-fe0008778977', 0, '', 'Grading options', 'bd1cba93-6420-4575-866c-1e1ad9cc839c')
/
update KSLU_CLU_RSLT set OBJ_ID='445c4ea9-c765-4ddd-acb3-8cfefa013c32', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-02-18 13:11:27.325', CLU_ID='07466ab6-6b05-4823-83eb-94f3ced526e3', TYPE_KEY_ID='kuali.resultType.gradeCourseResult', RT_DESCR_ID='bd1cba93-6420-4575-866c-1e1ad9cc839c', EFF_DT=TIMESTAMP '2013-02-18 13:11:27.27', EXPIR_DT='', ST='draft' where ID='1b6c1738-1cdd-4c82-9afe-d525a93136f7' and VER_NBR=1
/

insert into KSPR_PROPOSAL_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('ed7ced8e-3120-4562-8ada-accf80b98bd8', 0, 'workflowNode', 'PreRoute', 'd4d0b3ce-8bb4-41c1-a629-ae26f1bd1262', '8b12d837-eca6-4ad0-b708-fc3c44eabc12')
/
update KSPR_PROPOSAL set OBJ_ID='2024ef1c-e22d-4086-ae4a-bea9b9580b01', VER_NBR=3, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:12:04.152', DETAIL_DESC='', EFF_DT='', EXPIR_DT='', NAME='Intro to Biology', RATIONALE='', STATE='Saved', TYPE='kuali.proposal.type.course.create', WORKFLOW_ID='3011' where PROPOSAL_ID='d4d0b3ce-8bb4-41c1-a629-ae26f1bd1262' and VER_NBR=2
/
insert into KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) values ('d4d0b3ce-8bb4-41c1-a629-ae26f1bd1262', 'fafb29fc-2a9b-4574-92b8-0592678a339e')
/

insert into KSLU_CLU_ADMIN_ORG (OBJ_ID, VER_NBR, CLU_ID, IS_PR, ORG_ID, TYPE, ID) values ('e4fca40f-277f-4e28-83bc-24a388ea2067', 0, '07466ab6-6b05-4823-83eb-94f3ced526e3', '0', '576639460', 'kuali.adminOrg.type.Administration', 'e3afca7f-632c-45fa-b4df-e9c0f35a7202')
/
insert into KSLU_CLU_ADMIN_ORG (OBJ_ID, VER_NBR, CLU_ID, IS_PR, ORG_ID, TYPE, ID) values ('0dfc1c14-9726-4a92-8c7e-88f11995edb2', 0, '07466ab6-6b05-4823-83eb-94f3ced526e3', '0', '4269648307', 'kuali.adminOrg.type.CurriculumOversight', '5b564142-97ca-4566-8216-9291cad10f1e')
/
update KSLU_CLU_ACCT set OBJ_ID='0b19b6ab-a7f2-419b-8a33-ebf321d54e7f', VER_NBR=3 where ID='5b915ba6-0bdb-4893-a0c6-47ecf0b6c4ec' and VER_NBR=2
/
update KSLU_CLU_FEE set OBJ_ID='2e47a00d-4310-4965-b7d6-4452e0bbd6c8', VER_NBR=3, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:12:37.763', RT_DESCR_ID='' where ID='9014a1df-6eee-4f3e-b99f-6c300f28cd45' and VER_NBR=2
/
update KSLU_CLU_IDENT set OBJ_ID='3bdab3cb-a0d6-4bbd-9be1-36311a95d7d5', VER_NBR=3, CD='BSCI101', DIVISION='BSCI', LVL='100', LNG_NAME='Intro to Biology', ORG_ID='', SHRT_NAME='Intro to Biology', ST='draft', SUFX_CD='101', TYPE='kuali.lu.type.CreditCourse.identifier.official', VARTN='' where ID='ec6b3961-4969-4994-8545-76f1d796ca4a' and VER_NBR=2
/
update KSLU_CLU set OBJ_ID='2520f535-4581-416b-8f24-d1e10b037b04', VER_NBR=3, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:12:37.763', CURR_VER_END='', CURR_VER_START=TIMESTAMP '2013-02-18 13:11:27.298', SEQ_NUM=1, VER_CMT='', VER_IND_ID='edb51160-c8a0-4e22-a1b1-92b558651b7d', VER_FROM_ID='', ACCT_ID='5b915ba6-0bdb-4893-a0c6-47ecf0b6c4ec', CAN_CREATE_LUI='0', DEF_ENRL_EST=0, DEF_MAX_ENRL=0, RT_DESCR_ID='', EFF_DT=TIMESTAMP '2015-08-31 00:00:00.0', IS_ENRL='0', EXP_FIRST_ATP='kuali.atp.2015Fall', EXPIR_DT='', FEE_ID='9014a1df-6eee-4f3e-b99f-6c300f28cd45', HAS_EARLY_DROP_DEDLN='0', IS_HAZR_DISBLD_STU='0', CLU_INTSTY_QTY='', CLU_INTSTY_TYPE='', LAST_ADMIT_ATP='', LAST_ATP='', LUTYPE_ID='kuali.lu.type.CreditCourse', NEXT_REVIEW_PRD='', OFFIC_CLU_ID='ec6b3961-4969-4994-8545-76f1d796ca4a', PRI_INSTR_ID='', REF_URL='', ST='draft', ATP_DUR_TYP_KEY='', TM_QUANTITY='', STDY_SUBJ_AREA='' where ID='07466ab6-6b05-4823-83eb-94f3ced526e3' and VER_NBR=2
/
update KSLU_CLU_ADMIN_ORG set OBJ_ID='e4fca40f-277f-4e28-83bc-24a388ea2067', VER_NBR=1, CLU_ID='07466ab6-6b05-4823-83eb-94f3ced526e3', IS_PR='0', ORG_ID='576639460', TYPE='kuali.adminOrg.type.Administration' where ID='e3afca7f-632c-45fa-b4df-e9c0f35a7202' and VER_NBR=0
/
update KSLU_CLU_ADMIN_ORG set OBJ_ID='0dfc1c14-9726-4a92-8c7e-88f11995edb2', VER_NBR=1, CLU_ID='07466ab6-6b05-4823-83eb-94f3ced526e3', IS_PR='0', ORG_ID='4269648307', TYPE='kuali.adminOrg.type.CurriculumOversight' where ID='5b564142-97ca-4566-8216-9291cad10f1e' and VER_NBR=0
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('1d2f59ae-8250-4876-afd3-dfaf09064410', 0, '', 'Grading options', '2c6bd33a-04e3-451c-824f-9946e55b72f3')
/
update KSLU_CLU_RSLT set OBJ_ID='445c4ea9-c765-4ddd-acb3-8cfefa013c32', VER_NBR=3, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-02-18 13:11:27.325', CLU_ID='07466ab6-6b05-4823-83eb-94f3ced526e3', TYPE_KEY_ID='kuali.resultType.gradeCourseResult', RT_DESCR_ID='2c6bd33a-04e3-451c-824f-9946e55b72f3', EFF_DT=TIMESTAMP '2013-02-18 13:11:27.27', EXPIR_DT='', ST='draft' where ID='1b6c1738-1cdd-4c82-9afe-d525a93136f7' and VER_NBR=2
/

insert into KSPR_PROPOSAL_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('83b80d92-eedf-4295-9db1-ac5c1b72cff3', 0, 'workflowNode', 'PreRoute', 'd4d0b3ce-8bb4-41c1-a629-ae26f1bd1262', '888e04fc-9381-47a4-8372-34e96916716a')
/
update KSPR_PROPOSAL set OBJ_ID='2024ef1c-e22d-4086-ae4a-bea9b9580b01', VER_NBR=4, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:12:37.961', DETAIL_DESC='', EFF_DT='', EXPIR_DT='', NAME='Intro to Biology', RATIONALE='', STATE='Saved', TYPE='kuali.proposal.type.course.create', WORKFLOW_ID='3011' where PROPOSAL_ID='d4d0b3ce-8bb4-41c1-a629-ae26f1bd1262' and VER_NBR=3
/
insert into KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) values ('d4d0b3ce-8bb4-41c1-a629-ae26f1bd1262', 'fafb29fc-2a9b-4574-92b8-0592678a339e')
/

insert into KSPR_PROPOSAL_REFERENCE (OBJ_ID, VER_NBR, OBJECT_REFERENCE_ID, TYPE, REFERENCE_ID) values ('64553165-217e-4b10-94c1-bfa88763c2e3', 0, 'e93d74d0-6419-403f-824a-01f29f80976b', 'kuali.proposal.referenceType.clu', '6ae684ac-b9f4-46a2-a17f-242e7f723fbe')
/
insert into KSPR_PROPOSAL (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, DETAIL_DESC, EFF_DT, EXPIR_DT, NAME, RATIONALE, STATE, TYPE, WORKFLOW_ID, PROPOSAL_ID) values ('aa4c4977-5f9a-4dc1-805a-b5d83a3b66d2', 0, 'admin', TIMESTAMP '2013-02-18 13:20:11.482', 'admin', TIMESTAMP '2013-02-18 13:20:11.482', '', '', '', 'Writing for Teachers', '', 'Saved', 'kuali.proposal.type.course.create', '', '20dc6b94-1d0a-4712-85ca-5846c7e82ca6')
/
insert into KSPR_PROPOSAL_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('f26d089b-2b4d-4c02-958b-2b4cb01da923', 0, 'workflowNode', 'PreRoute', '20dc6b94-1d0a-4712-85ca-5846c7e82ca6', '4b3efa21-0acf-47f0-ada5-bc9e1d035285')
/
insert into KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) values ('20dc6b94-1d0a-4712-85ca-5846c7e82ca6', '6ae684ac-b9f4-46a2-a17f-242e7f723fbe')
/
update KSPR_PROPOSAL set OBJ_ID='aa4c4977-5f9a-4dc1-805a-b5d83a3b66d2', VER_NBR=1, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:20:11.622', DETAIL_DESC='', EFF_DT='', EXPIR_DT='', NAME='Writing for Teachers', RATIONALE='', STATE='Saved', TYPE='kuali.proposal.type.course.create', WORKFLOW_ID='3012' where PROPOSAL_ID='20dc6b94-1d0a-4712-85ca-5846c7e82ca6' and VER_NBR=0
/
insert into KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) values ('20dc6b94-1d0a-4712-85ca-5846c7e82ca6', '6ae684ac-b9f4-46a2-a17f-242e7f723fbe')
/
insert into KSLU_CLU_ACCT (OBJ_ID, VER_NBR, ID) values ('5b835231-4aaf-4da8-834c-b856995fbfdc', 0, 'd78aee55-67eb-4600-8aca-d1f855a0d8c8')
/
insert into KSLU_CLU_FEE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, ID) values ('545eb8ed-74c5-4344-a0c6-86cb8574b3dc', 0, 'admin', TIMESTAMP '2013-02-18 13:20:11.457', 'admin', TIMESTAMP '2013-02-18 13:20:11.457', '', 'df83ee03-b412-4716-9265-53cc20daa6bd')
/
insert into KSLU_CLU_IDENT (OBJ_ID, VER_NBR, CD, DIVISION, LVL, LNG_NAME, ORG_ID, SHRT_NAME, ST, SUFX_CD, TYPE, VARTN, ID) values ('d1ef25d4-1c6e-4f85-8230-62a1237e5754', 0, 'ENGL200', 'ENGL', '200', 'Writing for Teachers', '', 'Writing for Teachers', 'draft', '200', 'kuali.lu.type.CreditCourse.identifier.official', '', 'c4e45b89-a3aa-49a2-919d-505c2a7938cb')
/
insert into KSLU_CLU (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CURR_VER_END, CURR_VER_START, SEQ_NUM, VER_CMT, VER_IND_ID, VER_FROM_ID, ACCT_ID, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, RT_DESCR_ID, EFF_DT, IS_ENRL, EXP_FIRST_ATP, EXPIR_DT, FEE_ID, HAS_EARLY_DROP_DEDLN, IS_HAZR_DISBLD_STU, CLU_INTSTY_QTY, CLU_INTSTY_TYPE, LAST_ADMIT_ATP, LAST_ATP, LUTYPE_ID, NEXT_REVIEW_PRD, OFFIC_CLU_ID, PRI_INSTR_ID, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ID) values ('a11a1580-e301-49fd-941b-71aab5fc8010', 0, 'admin', TIMESTAMP '2013-02-18 13:20:11.457', 'admin', TIMESTAMP '2013-02-18 13:20:11.457', '', TIMESTAMP '2013-02-18 13:20:11.457', 1, '', '024dfe1a-a4e2-42a0-b8e1-612e357bbb30', '', 'd78aee55-67eb-4600-8aca-d1f855a0d8c8', '0', 0, 0, '', '', '0', '', '', 'df83ee03-b412-4716-9265-53cc20daa6bd', '0', '0', '', '', '', '', 'kuali.lu.type.CreditCourse', '', 'c4e45b89-a3aa-49a2-919d-505c2a7938cb', '', '', 'draft', '', '', '', 'e93d74d0-6419-403f-824a-01f29f80976b')
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('e52c3fae-bf0d-4711-b013-72634b1b19d3', 0, '', 'Grading options', 'a0493ddf-b7eb-4cdb-b424-44a12eadd965')
/
insert into KSLU_CLU_RSLT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, CLU_ID, TYPE_KEY_ID, RT_DESCR_ID, EFF_DT, EXPIR_DT, ST, ID) values ('fb9cc6a2-8424-4621-8ed2-b14493d7a4db', 0, 'admin', TIMESTAMP '2013-02-18 13:20:11.436', 'admin', TIMESTAMP '2013-02-18 13:20:11.459', 'e93d74d0-6419-403f-824a-01f29f80976b', 'kuali.resultType.gradeCourseResult', 'a0493ddf-b7eb-4cdb-b424-44a12eadd965', TIMESTAMP '2013-02-18 13:20:11.454', '', 'draft', 'bf8efd20-04a7-479e-9d7a-6a9489dc6787')
/

insert into KSLU_CLU_ADMIN_ORG (OBJ_ID, VER_NBR, CLU_ID, IS_PR, ORG_ID, TYPE, ID) values ('caa107b7-c328-4ad6-8c7e-06169ef7ef84', 0, 'e93d74d0-6419-403f-824a-01f29f80976b', '0', '2677933260', 'kuali.adminOrg.type.Administration', '9237bfde-665c-46a2-92bd-7863d0b294b8')
/
insert into KSLU_CLU_ADMIN_ORG (OBJ_ID, VER_NBR, CLU_ID, IS_PR, ORG_ID, TYPE, ID) values ('9e9910f6-0bee-43f7-ad4e-546149a4de82', 0, 'e93d74d0-6419-403f-824a-01f29f80976b', '0', '2677933260', 'kuali.adminOrg.type.CurriculumOversight', '1dd55009-8337-408e-a188-fc8ac462748c')
/
update KSLU_CLU_ACCT set OBJ_ID='5b835231-4aaf-4da8-834c-b856995fbfdc', VER_NBR=1 where ID='d78aee55-67eb-4600-8aca-d1f855a0d8c8' and VER_NBR=0
/
update KSLU_CLU_FEE set OBJ_ID='545eb8ed-74c5-4344-a0c6-86cb8574b3dc', VER_NBR=1, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:20:27.874', RT_DESCR_ID='' where ID='df83ee03-b412-4716-9265-53cc20daa6bd' and VER_NBR=0
/
update KSLU_CLU_IDENT set OBJ_ID='d1ef25d4-1c6e-4f85-8230-62a1237e5754', VER_NBR=1, CD='ENGL200', DIVISION='ENGL', LVL='200', LNG_NAME='Writing for Teachers', ORG_ID='', SHRT_NAME='Writing for Teachers', ST='draft', SUFX_CD='200', TYPE='kuali.lu.type.CreditCourse.identifier.official', VARTN='' where ID='c4e45b89-a3aa-49a2-919d-505c2a7938cb' and VER_NBR=0
/
update KSLU_CLU set OBJ_ID='a11a1580-e301-49fd-941b-71aab5fc8010', VER_NBR=1, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:20:27.874', CURR_VER_END='', CURR_VER_START=TIMESTAMP '2013-02-18 13:20:11.457', SEQ_NUM=1, VER_CMT='', VER_IND_ID='024dfe1a-a4e2-42a0-b8e1-612e357bbb30', VER_FROM_ID='', ACCT_ID='d78aee55-67eb-4600-8aca-d1f855a0d8c8', CAN_CREATE_LUI='0', DEF_ENRL_EST=0, DEF_MAX_ENRL=0, RT_DESCR_ID='', EFF_DT='', IS_ENRL='0', EXP_FIRST_ATP='', EXPIR_DT='', FEE_ID='df83ee03-b412-4716-9265-53cc20daa6bd', HAS_EARLY_DROP_DEDLN='0', IS_HAZR_DISBLD_STU='0', CLU_INTSTY_QTY='', CLU_INTSTY_TYPE='', LAST_ADMIT_ATP='', LAST_ATP='', LUTYPE_ID='kuali.lu.type.CreditCourse', NEXT_REVIEW_PRD='', OFFIC_CLU_ID='c4e45b89-a3aa-49a2-919d-505c2a7938cb', PRI_INSTR_ID='', REF_URL='', ST='draft', ATP_DUR_TYP_KEY='', TM_QUANTITY='', STDY_SUBJ_AREA='' where ID='e93d74d0-6419-403f-824a-01f29f80976b' and VER_NBR=0
/
update KSLU_CLU_ADMIN_ORG set OBJ_ID='caa107b7-c328-4ad6-8c7e-06169ef7ef84', VER_NBR=1, CLU_ID='e93d74d0-6419-403f-824a-01f29f80976b', IS_PR='0', ORG_ID='2677933260', TYPE='kuali.adminOrg.type.Administration' where ID='9237bfde-665c-46a2-92bd-7863d0b294b8' and VER_NBR=0
/
update KSLU_CLU_ADMIN_ORG set OBJ_ID='9e9910f6-0bee-43f7-ad4e-546149a4de82', VER_NBR=1, CLU_ID='e93d74d0-6419-403f-824a-01f29f80976b', IS_PR='0', ORG_ID='2677933260', TYPE='kuali.adminOrg.type.CurriculumOversight' where ID='1dd55009-8337-408e-a188-fc8ac462748c' and VER_NBR=0
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('2bb13931-9d75-4acd-9dc9-ca15425df512', 0, '', 'Grading options', '0028299b-4bf1-4c7c-9f5e-8b36392601af')
/
update KSLU_CLU_RSLT set OBJ_ID='fb9cc6a2-8424-4621-8ed2-b14493d7a4db', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-02-18 13:20:11.459', CLU_ID='e93d74d0-6419-403f-824a-01f29f80976b', TYPE_KEY_ID='kuali.resultType.gradeCourseResult', RT_DESCR_ID='0028299b-4bf1-4c7c-9f5e-8b36392601af', EFF_DT=TIMESTAMP '2013-02-18 13:20:11.454', EXPIR_DT='', ST='draft' where ID='bf8efd20-04a7-479e-9d7a-6a9489dc6787' and VER_NBR=0
/

insert into KSPR_PROPOSAL_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('b51db246-0590-45db-8a8c-a43db5d793fe', 0, 'workflowNode', 'PreRoute', '20dc6b94-1d0a-4712-85ca-5846c7e82ca6', 'e81f9017-d81c-4bf9-93d8-2a994cad23f2')
/
update KSPR_PROPOSAL set OBJ_ID='aa4c4977-5f9a-4dc1-805a-b5d83a3b66d2', VER_NBR=2, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:20:28.02', DETAIL_DESC='', EFF_DT='', EXPIR_DT='', NAME='Writing for Teachers', RATIONALE='', STATE='Saved', TYPE='kuali.proposal.type.course.create', WORKFLOW_ID='3012' where PROPOSAL_ID='20dc6b94-1d0a-4712-85ca-5846c7e82ca6' and VER_NBR=1
/
insert into KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) values ('20dc6b94-1d0a-4712-85ca-5846c7e82ca6', '6ae684ac-b9f4-46a2-a17f-242e7f723fbe')
/

insert into KSLU_CLU_ADMIN_ORG (OBJ_ID, VER_NBR, CLU_ID, IS_PR, ORG_ID, TYPE, ID) values ('3d4aa3a0-2f62-4936-91d7-517a25789d9f', 0, 'e93d74d0-6419-403f-824a-01f29f80976b', '0', '2677933260', 'kuali.adminOrg.type.Administration', '4cf1386c-9700-4de6-b5c1-274f5461b791')
/
insert into KSLU_CLU_ADMIN_ORG (OBJ_ID, VER_NBR, CLU_ID, IS_PR, ORG_ID, TYPE, ID) values ('ff83290d-711f-4137-a221-e4e6c18cc7a7', 0, 'e93d74d0-6419-403f-824a-01f29f80976b', '0', '2677933260', 'kuali.adminOrg.type.CurriculumOversight', '9e1cd82b-d94d-4066-b3da-f846654023f4')
/
update KSLU_CLU_ACCT set OBJ_ID='5b835231-4aaf-4da8-834c-b856995fbfdc', VER_NBR=2 where ID='d78aee55-67eb-4600-8aca-d1f855a0d8c8' and VER_NBR=1
/
update KSLU_CLU_FEE set OBJ_ID='545eb8ed-74c5-4344-a0c6-86cb8574b3dc', VER_NBR=2, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:20:41.806', RT_DESCR_ID='' where ID='df83ee03-b412-4716-9265-53cc20daa6bd' and VER_NBR=1
/
update KSLU_CLU_IDENT set OBJ_ID='d1ef25d4-1c6e-4f85-8230-62a1237e5754', VER_NBR=2, CD='ENGL200', DIVISION='ENGL', LVL='200', LNG_NAME='Writing for Teachers', ORG_ID='', SHRT_NAME='Writing for Teachers', ST='draft', SUFX_CD='200', TYPE='kuali.lu.type.CreditCourse.identifier.official', VARTN='' where ID='c4e45b89-a3aa-49a2-919d-505c2a7938cb' and VER_NBR=1
/
update KSLU_CLU set OBJ_ID='a11a1580-e301-49fd-941b-71aab5fc8010', VER_NBR=2, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:20:41.806', CURR_VER_END='', CURR_VER_START=TIMESTAMP '2013-02-18 13:20:11.457', SEQ_NUM=1, VER_CMT='', VER_IND_ID='024dfe1a-a4e2-42a0-b8e1-612e357bbb30', VER_FROM_ID='', ACCT_ID='d78aee55-67eb-4600-8aca-d1f855a0d8c8', CAN_CREATE_LUI='0', DEF_ENRL_EST=0, DEF_MAX_ENRL=0, RT_DESCR_ID='', EFF_DT=TIMESTAMP '2016-01-25 00:00:00.0', IS_ENRL='0', EXP_FIRST_ATP='kuali.atp.2016Spring', EXPIR_DT='', FEE_ID='df83ee03-b412-4716-9265-53cc20daa6bd', HAS_EARLY_DROP_DEDLN='0', IS_HAZR_DISBLD_STU='0', CLU_INTSTY_QTY='', CLU_INTSTY_TYPE='', LAST_ADMIT_ATP='', LAST_ATP='', LUTYPE_ID='kuali.lu.type.CreditCourse', NEXT_REVIEW_PRD='', OFFIC_CLU_ID='c4e45b89-a3aa-49a2-919d-505c2a7938cb', PRI_INSTR_ID='', REF_URL='', ST='draft', ATP_DUR_TYP_KEY='', TM_QUANTITY='', STDY_SUBJ_AREA='' where ID='e93d74d0-6419-403f-824a-01f29f80976b' and VER_NBR=1
/
update KSLU_CLU_ADMIN_ORG set OBJ_ID='3d4aa3a0-2f62-4936-91d7-517a25789d9f', VER_NBR=1, CLU_ID='e93d74d0-6419-403f-824a-01f29f80976b', IS_PR='0', ORG_ID='2677933260', TYPE='kuali.adminOrg.type.Administration' where ID='4cf1386c-9700-4de6-b5c1-274f5461b791' and VER_NBR=0
/
update KSLU_CLU_ADMIN_ORG set OBJ_ID='ff83290d-711f-4137-a221-e4e6c18cc7a7', VER_NBR=1, CLU_ID='e93d74d0-6419-403f-824a-01f29f80976b', IS_PR='0', ORG_ID='2677933260', TYPE='kuali.adminOrg.type.CurriculumOversight' where ID='9e1cd82b-d94d-4066-b3da-f846654023f4' and VER_NBR=0
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('7945c741-6368-4ee7-ad16-ae7d88ce3b78', 0, '', 'Grading options', '5336c830-631d-4a21-81d6-1be3eed9dd42')
/
update KSLU_CLU_RSLT set OBJ_ID='fb9cc6a2-8424-4621-8ed2-b14493d7a4db', VER_NBR=2, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-02-18 13:20:11.459', CLU_ID='e93d74d0-6419-403f-824a-01f29f80976b', TYPE_KEY_ID='kuali.resultType.gradeCourseResult', RT_DESCR_ID='5336c830-631d-4a21-81d6-1be3eed9dd42', EFF_DT=TIMESTAMP '2013-02-18 13:20:11.454', EXPIR_DT='', ST='draft' where ID='bf8efd20-04a7-479e-9d7a-6a9489dc6787' and VER_NBR=1
/

insert into KSPR_PROPOSAL_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('b8672ad0-3b2f-443f-ace0-745dbe75b510', 0, 'workflowNode', 'PreRoute', '20dc6b94-1d0a-4712-85ca-5846c7e82ca6', '74d3a75e-f885-4c60-b0e7-59f08264d3a1')
/
update KSPR_PROPOSAL set OBJ_ID='aa4c4977-5f9a-4dc1-805a-b5d83a3b66d2', VER_NBR=3, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:20:41.955', DETAIL_DESC='', EFF_DT='', EXPIR_DT='', NAME='Writing for Teachers', RATIONALE='', STATE='Saved', TYPE='kuali.proposal.type.course.create', WORKFLOW_ID='3012' where PROPOSAL_ID='20dc6b94-1d0a-4712-85ca-5846c7e82ca6' and VER_NBR=2
/
insert into KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) values ('20dc6b94-1d0a-4712-85ca-5846c7e82ca6', '6ae684ac-b9f4-46a2-a17f-242e7f723fbe')
/

insert into KSLU_CLU_ACCT (OBJ_ID, VER_NBR, ID) values ('a04d24a6-221f-418d-a3f5-dd874d9e78a2', 0, '11df3280-8f2e-43f3-9e76-3dac3974d462')
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('f84cebb1-7227-4577-bbb3-7027d33d7de1', 0, 'Honors projects for undergraduate students.', 'Honors projects for undergraduate students.', 'bd5c8f8d-9e3f-4a2b-a075-fd1900feeddd')
/
insert into KSLU_CLU_FEE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, ID) values ('57748ce7-bdcc-4080-8f99-0563803fc132', 0, 'admin', TIMESTAMP '2013-02-18 13:23:33.82', 'admin', TIMESTAMP '2013-02-18 13:23:33.82', '', '8cfdb2f0-3367-44f1-9306-47133c2a9a85')
/
insert into KSLU_CLU_IDENT (OBJ_ID, VER_NBR, CD, DIVISION, LVL, LNG_NAME, ORG_ID, SHRT_NAME, ST, SUFX_CD, TYPE, VARTN, ID) values ('3c5f4ca9-1fa0-4a33-a758-2d56ff12d889', 0, 'CHEM398', 'CHEM', '300', 'Special Projects', '', 'SPECIAL PROJECTS', 'Active', '398', 'kuali.lu.type.CreditCourse.identifier.official', '', '322cbc1f-173d-4d8a-93b3-1dc7e4ebdb98')
/
insert into KSLU_CLU (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CURR_VER_END, CURR_VER_START, SEQ_NUM, VER_CMT, VER_IND_ID, VER_FROM_ID, ACCT_ID, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, RT_DESCR_ID, EFF_DT, IS_ENRL, EXP_FIRST_ATP, EXPIR_DT, FEE_ID, HAS_EARLY_DROP_DEDLN, IS_HAZR_DISBLD_STU, CLU_INTSTY_QTY, CLU_INTSTY_TYPE, LAST_ADMIT_ATP, LAST_ATP, LUTYPE_ID, NEXT_REVIEW_PRD, OFFIC_CLU_ID, PRI_INSTR_ID, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ID) values ('c868e1b7-74d8-427c-a40d-abbeb6c67a61', 0, 'admin', TIMESTAMP '2013-02-18 13:23:33.82', 'admin', TIMESTAMP '2013-02-18 13:23:33.82', '', '', 2, 'versionComment', '2d1096c9-e3b8-46a9-bcd5-bcbd4bb5ff1b', 'CLUID-CHEM398-198001000000', '11df3280-8f2e-43f3-9e76-3dac3974d462', '0', 0, 0, 'bd5c8f8d-9e3f-4a2b-a075-fd1900feeddd', TIMESTAMP '1980-01-10 00:00:00.0', '0', 'kuali.atp.1980Spring', '', '8cfdb2f0-3367-44f1-9306-47133c2a9a85', '0', '0', '', '', '', '', 'kuali.lu.type.CreditCourse', '', '322cbc1f-173d-4d8a-93b3-1dc7e4ebdb98', '', '', 'Draft', '', '', '', 'c52d6daa-6ce2-4237-874b-1286a7640057')
/
insert into KSLU_CLU_ADMIN_ORG (OBJ_ID, VER_NBR, CLU_ID, IS_PR, ORG_ID, TYPE, ID) values ('d5b3e7aa-7117-426c-94cd-5f175d0f0907', 0, 'c52d6daa-6ce2-4237-874b-1286a7640057', '0', '4284516367', 'kuali.adminOrg.type.CurriculumOversight', '521aa346-cbb3-45a6-b2db-8134bee433b1')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('7a4eac2f-24c0-4882-ab35-b0bdb8c43baf', 0, 'audit', '1', 'c52d6daa-6ce2-4237-874b-1286a7640057', 'c2c9dfff-6ed1-4743-97c4-040bfa644117')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('7c1a5ddd-bf2e-4e31-8e34-3af1afed5881', 0, 'activityTotalContactHours', '30', 'c52d6daa-6ce2-4237-874b-1286a7640057', '81b770f0-0a93-4041-972c-21e111bae10b')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('59e62e55-68fe-434d-be26-9016bb3956b6', 0, 'finalExamStatus', 'STD', 'c52d6daa-6ce2-4237-874b-1286a7640057', '96e32642-4f6f-4156-bfc3-43a06a6cf0cb')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('00c6a7c1-19f6-4c75-bd0a-d2d8c2489fb1', 0, 'semesterType', 'semesterType.standard', 'c52d6daa-6ce2-4237-874b-1286a7640057', 'a1de0e7d-defb-4dde-8c90-9ea59e7c9225')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('79e18a0a-36f1-4829-b600-33cad026b65f', 0, 'activityTypeExplanation', '.', 'c52d6daa-6ce2-4237-874b-1286a7640057', '2d9699fa-e127-4534-9d89-c8424c43ae5d')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('11e1002f-dc4d-426f-a7e3-b49050176f11', 0, 'activityTotalCredits', '2', 'c52d6daa-6ce2-4237-874b-1286a7640057', '1c39d583-eab3-4b81-b6e6-480020d1faf1')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('ddd2c85b-e3ed-4573-81d8-7a515b520dde', 0, 'passFail', '1', 'c52d6daa-6ce2-4237-874b-1286a7640057', '0bd9da93-e924-41f7-9970-1d3e36f88afb')
/
insert into KSLU_CLU_JN_CAMP_LOC (OBJ_ID, VER_NBR, CAMP_LOC, CLU_ID, ID) values ('99b98e0c-652f-4417-8bc4-cdcc1baf1f0e', 0, 'NO', 'c52d6daa-6ce2-4237-874b-1286a7640057', '9264b8fc-5d8f-42f2-9a90-09d4fcff059e')
/
insert into KSLU_CLU_ADMIN_ORG (OBJ_ID, VER_NBR, CLU_ID, IS_PR, ORG_ID, TYPE, ID) values ('65a7e4a9-059c-4e37-90da-287840a1c9c7', 0, 'c52d6daa-6ce2-4237-874b-1286a7640057', '0', '4284516367', 'kuali.adminOrg.type.CurriculumOversight', '121ae0e2-f952-4df0-bbb7-b4b3e5de5093')
/
update KSLU_CLU set OBJ_ID='c868e1b7-74d8-427c-a40d-abbeb6c67a61', VER_NBR=1, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:23:33.85', CURR_VER_END='', CURR_VER_START='', SEQ_NUM=2, VER_CMT='versionComment', VER_IND_ID='2d1096c9-e3b8-46a9-bcd5-bcbd4bb5ff1b', VER_FROM_ID='CLUID-CHEM398-198001000000', ACCT_ID='11df3280-8f2e-43f3-9e76-3dac3974d462', CAN_CREATE_LUI='0', DEF_ENRL_EST=0, DEF_MAX_ENRL=0, RT_DESCR_ID='bd5c8f8d-9e3f-4a2b-a075-fd1900feeddd', EFF_DT=TIMESTAMP '1980-01-10 00:00:00.0', IS_ENRL='0', EXP_FIRST_ATP='', EXPIR_DT='', FEE_ID='8cfdb2f0-3367-44f1-9306-47133c2a9a85', HAS_EARLY_DROP_DEDLN='0', IS_HAZR_DISBLD_STU='0', CLU_INTSTY_QTY='', CLU_INTSTY_TYPE='', LAST_ADMIT_ATP='', LAST_ATP='', LUTYPE_ID='kuali.lu.type.CreditCourse', NEXT_REVIEW_PRD='', OFFIC_CLU_ID='322cbc1f-173d-4d8a-93b3-1dc7e4ebdb98', PRI_INSTR_ID='', REF_URL='', ST='Draft', ATP_DUR_TYP_KEY='', TM_QUANTITY='', STDY_SUBJ_AREA='' where ID='c52d6daa-6ce2-4237-874b-1286a7640057' and VER_NBR=0
/
update KSLU_CLU_ACCT set OBJ_ID='a04d24a6-221f-418d-a3f5-dd874d9e78a2', VER_NBR=1 where ID='11df3280-8f2e-43f3-9e76-3dac3974d462' and VER_NBR=0
/
update KSLU_CLU_FEE set OBJ_ID='57748ce7-bdcc-4080-8f99-0563803fc132', VER_NBR=1, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:23:33.85', RT_DESCR_ID='' where ID='8cfdb2f0-3367-44f1-9306-47133c2a9a85' and VER_NBR=0
/
update KSLU_CLU_IDENT set OBJ_ID='3c5f4ca9-1fa0-4a33-a758-2d56ff12d889', VER_NBR=1, CD='CHEM398', DIVISION='CHEM', LVL='300', LNG_NAME='Special Projects', ORG_ID='', SHRT_NAME='SPECIAL PROJECTS', ST='Draft', SUFX_CD='398', TYPE='kuali.lu.type.CreditCourse.identifier.official', VARTN='' where ID='322cbc1f-173d-4d8a-93b3-1dc7e4ebdb98' and VER_NBR=0
/
update KSLU_CLU_ADMIN_ORG set OBJ_ID='65a7e4a9-059c-4e37-90da-287840a1c9c7', VER_NBR=1, CLU_ID='c52d6daa-6ce2-4237-874b-1286a7640057', IS_PR='0', ORG_ID='4284516367', TYPE='kuali.adminOrg.type.CurriculumOversight' where ID='121ae0e2-f952-4df0-bbb7-b4b3e5de5093' and VER_NBR=0
/

insert into KSPR_PROPOSAL_REFERENCE (OBJ_ID, VER_NBR, OBJECT_REFERENCE_ID, TYPE, REFERENCE_ID) values ('94a5ee93-2f1c-45e9-bdbd-3ab644b65b84', 0, 'c52d6daa-6ce2-4237-874b-1286a7640057', 'kuali.proposal.referenceType.clu', '254128dd-62cc-4fcb-9405-4bf3b4f923d6')
/
insert into KSPR_PROPOSAL (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, DETAIL_DESC, EFF_DT, EXPIR_DT, NAME, RATIONALE, STATE, TYPE, WORKFLOW_ID, PROPOSAL_ID) values ('5aa29f7d-fd37-42fb-8864-a8be8940e786', 0, 'admin', TIMESTAMP '2013-02-18 13:23:34.031', 'admin', TIMESTAMP '2013-02-18 13:23:34.031', '', '', '', 'Modify: Special Projects', '', 'Saved', 'kuali.proposal.type.course.modify', '', 'd8b6cb16-0c95-4994-925d-b8061afb4d27')
/
insert into KSPR_PROPOSAL_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('60c7693b-4c6f-4c28-8b32-23d57a5cb8f5', 0, 'workflowNode', 'PreRoute', 'd8b6cb16-0c95-4994-925d-b8061afb4d27', '647b080e-daa2-4d6d-93c7-d9dfc28afc03')
/
insert into KSPR_PROPOSAL_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('a9cc9f66-4103-4ca1-bc15-7d63c0903a5a', 0, 'prevStartTerm', 'kuali.atp.1980Spring', 'd8b6cb16-0c95-4994-925d-b8061afb4d27', 'cc4f2903-c009-4e12-8ac2-c961cc97f8fc')
/
insert into KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) values ('d8b6cb16-0c95-4994-925d-b8061afb4d27', '254128dd-62cc-4fcb-9405-4bf3b4f923d6')
/
update KSPR_PROPOSAL set OBJ_ID='5aa29f7d-fd37-42fb-8864-a8be8940e786', VER_NBR=1, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:23:34.18', DETAIL_DESC='', EFF_DT='', EXPIR_DT='', NAME='Modify: Special Projects', RATIONALE='', STATE='Saved', TYPE='kuali.proposal.type.course.modify', WORKFLOW_ID='3013' where PROPOSAL_ID='d8b6cb16-0c95-4994-925d-b8061afb4d27' and VER_NBR=0
/
insert into KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) values ('d8b6cb16-0c95-4994-925d-b8061afb4d27', '254128dd-62cc-4fcb-9405-4bf3b4f923d6')
/
insert into KSLU_CLU (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CURR_VER_END, CURR_VER_START, SEQ_NUM, VER_CMT, VER_IND_ID, VER_FROM_ID, ACCT_ID, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, RT_DESCR_ID, EFF_DT, IS_ENRL, EXP_FIRST_ATP, EXPIR_DT, FEE_ID, HAS_EARLY_DROP_DEDLN, IS_HAZR_DISBLD_STU, CLU_INTSTY_QTY, CLU_INTSTY_TYPE, LAST_ADMIT_ATP, LAST_ATP, LUTYPE_ID, NEXT_REVIEW_PRD, OFFIC_CLU_ID, PRI_INSTR_ID, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ID) values ('bc63ecc5-3cd0-4cd8-969c-1b9e1b657794', 0, 'admin', TIMESTAMP '2013-02-18 13:23:33.928', 'admin', TIMESTAMP '2013-02-18 13:23:33.928', '', TIMESTAMP '2013-02-18 13:23:33.928', 1, '', 'de09dadc-c921-4251-9dc9-43a01d1748a2', '', '', '0', 0, 0, '', '', '0', '', '', '', '0', '0', '', '', '', '', 'kuali.lu.type.CreditCourseFormatShell', '', '', '', '', 'Draft', '', '', '', '11fb7dd1-d1b5-4168-bd16-5e32256ad4c5')
/
insert into KSLU_CLU (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CURR_VER_END, CURR_VER_START, SEQ_NUM, VER_CMT, VER_IND_ID, VER_FROM_ID, ACCT_ID, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, RT_DESCR_ID, EFF_DT, IS_ENRL, EXP_FIRST_ATP, EXPIR_DT, FEE_ID, HAS_EARLY_DROP_DEDLN, IS_HAZR_DISBLD_STU, CLU_INTSTY_QTY, CLU_INTSTY_TYPE, LAST_ADMIT_ATP, LAST_ATP, LUTYPE_ID, NEXT_REVIEW_PRD, OFFIC_CLU_ID, PRI_INSTR_ID, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ID) values ('07a6a82c-4cc1-420f-8d2b-ed664f424b16', 0, 'admin', TIMESTAMP '2013-02-18 13:23:33.956', 'admin', TIMESTAMP '2013-02-18 13:23:33.956', '', TIMESTAMP '2013-02-18 13:23:33.955', 1, '', '592c026b-2056-41cb-9173-e83084d9b456', '', '', '0', 0, 0, '', '', '0', '', '', '', '0', '0', '3', 'kuali.atp.duration.week', '', '', 'kuali.lu.type.activity.Lecture', '', '', '', '', 'Draft', '', '', '', '387b2f8c-f9ce-44c6-bc3e-2915e6fd4adf')
/
insert into KSLU_CLUCLU_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CLU_ID, CLU_RELTN_REQ, EFF_DT, EXPIR_DT, LU_RELTN_TYPE_ID, RELATED_CLU_ID, ST, ID) values ('7611b6e7-19b2-47ce-acea-499223b028e3', 0, 'admin', TIMESTAMP '2013-02-18 13:23:33.962', 'admin', TIMESTAMP '2013-02-18 13:23:33.962', '11fb7dd1-d1b5-4168-bd16-5e32256ad4c5', '1', '', '', 'luLuRelationType.contains', '387b2f8c-f9ce-44c6-bc3e-2915e6fd4adf', 'Draft', '5122d127-af6f-4a28-bcbf-4a5ff0771583')
/
insert into KSLU_CLUCLU_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CLU_ID, CLU_RELTN_REQ, EFF_DT, EXPIR_DT, LU_RELTN_TYPE_ID, RELATED_CLU_ID, ST, ID) values ('cc9f50b5-5303-4a56-8f0b-87544903d7c9', 0, 'admin', TIMESTAMP '2013-02-18 13:23:33.962', 'admin', TIMESTAMP '2013-02-18 13:23:33.962', 'c52d6daa-6ce2-4237-874b-1286a7640057', '1', '', '', 'luLuRelationType.hasCourseFormat', '11fb7dd1-d1b5-4168-bd16-5e32256ad4c5', 'Draft', '88925c01-c1d1-41fb-8c2b-6add8d63af5a')
/
insert into KSLU_RSLT_OPT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, RT_DESCR_ID, EFF_DT, EXPIR_DT, RES_COMP_ID, RES_USAGE_ID, ST, ID) values ('86de2632-4905-45f8-86fa-d73833aecd42', 0, 'admin', TIMESTAMP '2013-02-18 13:23:33.708', 'admin', TIMESTAMP '2013-02-18 13:23:33.964', '', '', '', 'kuali.creditType.credit.degree.2.0', '', 'Draft', '9329eadc-8e6f-4f5e-882e-76dacad5d9be')
/
insert into KSLU_CLU_RSLT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, CLU_ID, TYPE_KEY_ID, RT_DESCR_ID, EFF_DT, EXPIR_DT, ST, ID) values ('b26ac2f5-48a4-477d-b36b-3d06dceb57f8', 0, 'admin', TIMESTAMP '2013-02-18 13:23:33.708', 'admin', TIMESTAMP '2013-02-18 13:23:33.965', 'c52d6daa-6ce2-4237-874b-1286a7640057', 'kuali.resultType.creditCourseResult', '', '', '', 'Draft', 'c1c333d5-a042-4cff-b1ee-73aa4f81aed4')
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('967b1cd5-58ba-4b21-9081-98cf990e77e8', 0, '', 'Grading option', '203dc257-91ec-4f11-9adf-f97d67a54f3f')
/
insert into KSLU_RSLT_OPT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, RT_DESCR_ID, EFF_DT, EXPIR_DT, RES_COMP_ID, RES_USAGE_ID, ST, ID) values ('0a78c67b-5ce0-4d62-939b-7d7166e99425', 0, 'admin', TIMESTAMP '2013-02-18 13:23:33.708', 'admin', TIMESTAMP '2013-02-18 13:23:33.967', '203dc257-91ec-4f11-9adf-f97d67a54f3f', '', '', 'kuali.resultComponent.grade.letter', '', 'Draft', '965d3569-e39e-4713-962c-b9d8dec33547')
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('9b1a0739-cc70-4836-b2a5-fa52e167b4ec', 0, '', 'Grading option', '5458ee69-9850-44c4-aafb-9552c294ed04')
/
insert into KSLU_RSLT_OPT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, RT_DESCR_ID, EFF_DT, EXPIR_DT, RES_COMP_ID, RES_USAGE_ID, ST, ID) values ('32b8fc74-35c9-47d0-8a6a-ae598a9ae748', 0, 'admin', TIMESTAMP '2013-02-18 13:23:33.708', 'admin', TIMESTAMP '2013-02-18 13:23:33.967', '5458ee69-9850-44c4-aafb-9552c294ed04', '', '', 'kuali.resultComponent.grade.passFail', '', 'Draft', '658cf5c4-2b2b-4930-bf7b-83204f687389')
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('fe91dcf4-1dd0-4806-95c0-487ce4c39100', 0, '', 'Grading option', 'ca8c978b-223f-4b79-9aec-75f4af748eba')
/
insert into KSLU_RSLT_OPT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, RT_DESCR_ID, EFF_DT, EXPIR_DT, RES_COMP_ID, RES_USAGE_ID, ST, ID) values ('538ada1f-8111-44d7-ad85-6c707999cbe2', 0, 'admin', TIMESTAMP '2013-02-18 13:23:33.708', 'admin', TIMESTAMP '2013-02-18 13:23:33.967', 'ca8c978b-223f-4b79-9aec-75f4af748eba', '', '', 'kuali.resultComponent.grade.audit', '', 'Draft', '63cf6f9a-8455-49eb-b28b-2b3b2769c77c')
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('0c62a23f-d2d9-46ae-bc2d-be3d1771933f', 0, '', 'Grading options', 'fa33aa3c-ef4a-420c-8327-2b3152173d19')
/
insert into KSLU_CLU_RSLT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, CLU_ID, TYPE_KEY_ID, RT_DESCR_ID, EFF_DT, EXPIR_DT, ST, ID) values ('0d18a63a-c817-4d4d-98ca-fed37d17796e', 0, 'admin', TIMESTAMP '2013-02-18 13:23:33.708', 'admin', TIMESTAMP '2013-02-18 13:23:33.967', 'c52d6daa-6ce2-4237-874b-1286a7640057', 'kuali.resultType.gradeCourseResult', 'fa33aa3c-ef4a-420c-8327-2b3152173d19', TIMESTAMP '2013-02-18 13:23:33.838', '', 'Draft', 'c8636e8e-e36e-4d7c-b53a-30f328eeb42f')
/
insert into KSLU_CLURES_JN_RESOPT (CLU_RES_ID, RES_OPT_ID) values ('c1c333d5-a042-4cff-b1ee-73aa4f81aed4', '9329eadc-8e6f-4f5e-882e-76dacad5d9be')
/
insert into KSLU_CLURES_JN_RESOPT (CLU_RES_ID, RES_OPT_ID) values ('c8636e8e-e36e-4d7c-b53a-30f328eeb42f', '965d3569-e39e-4713-962c-b9d8dec33547')
/
insert into KSLU_CLURES_JN_RESOPT (CLU_RES_ID, RES_OPT_ID) values ('c8636e8e-e36e-4d7c-b53a-30f328eeb42f', '658cf5c4-2b2b-4930-bf7b-83204f687389')
/
insert into KSLU_CLURES_JN_RESOPT (CLU_RES_ID, RES_OPT_ID) values ('c8636e8e-e36e-4d7c-b53a-30f328eeb42f', '63cf6f9a-8455-49eb-b28b-2b3b2769c77c')
/

insert into KSLU_CLU_ADMIN_ORG (OBJ_ID, VER_NBR, CLU_ID, IS_PR, ORG_ID, TYPE, ID) values ('dafdf2e0-1908-41cd-b75b-87c21923b0f6', 0, 'c52d6daa-6ce2-4237-874b-1286a7640057', '0', '4284516367', 'kuali.adminOrg.type.CurriculumOversight', '7a3a1d3b-319d-452d-a66c-5dfa4afc5f4d')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('645fa5e2-e1da-4cc2-a29c-37f2c6ff7c3d', 0, 'audit', '1', 'c52d6daa-6ce2-4237-874b-1286a7640057', 'dd218380-71f3-4932-9c09-d434dc1faa4f')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('a685ed9b-c2bf-4cc6-a5c2-9745846b6bc1', 0, 'finalExamStatus', 'STD', 'c52d6daa-6ce2-4237-874b-1286a7640057', 'e41fa360-a68e-4771-80ab-65ca0f95e1b7')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('61a25968-5f44-4833-a514-8a6fafc61129', 0, 'passFail', '1', 'c52d6daa-6ce2-4237-874b-1286a7640057', '64c27b82-0f6a-4c68-b056-e2c7fb5dc975')
/
update KSLU_CLU_ACCT set OBJ_ID='a04d24a6-221f-418d-a3f5-dd874d9e78a2', VER_NBR=2 where ID='11df3280-8f2e-43f3-9e76-3dac3974d462' and VER_NBR=1
/
update KSLU_CLU_FEE set OBJ_ID='57748ce7-bdcc-4080-8f99-0563803fc132', VER_NBR=2, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:23:54.913', RT_DESCR_ID='' where ID='8cfdb2f0-3367-44f1-9306-47133c2a9a85' and VER_NBR=1
/
update KSLU_CLU_IDENT set OBJ_ID='3c5f4ca9-1fa0-4a33-a758-2d56ff12d889', VER_NBR=2, CD='CHEM398', DIVISION='CHEM', LVL='300', LNG_NAME='Special Projects', ORG_ID='', SHRT_NAME='SPECIAL PROJECTS', ST='Draft', SUFX_CD='398', TYPE='kuali.lu.type.CreditCourse.identifier.official', VARTN='' where ID='322cbc1f-173d-4d8a-93b3-1dc7e4ebdb98' and VER_NBR=1
/
update KSLU_CLU set OBJ_ID='c868e1b7-74d8-427c-a40d-abbeb6c67a61', VER_NBR=2, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:23:54.913', CURR_VER_END='', CURR_VER_START='', SEQ_NUM=2, VER_CMT='versionComment', VER_IND_ID='2d1096c9-e3b8-46a9-bcd5-bcbd4bb5ff1b', VER_FROM_ID='CLUID-CHEM398-198001000000', ACCT_ID='11df3280-8f2e-43f3-9e76-3dac3974d462', CAN_CREATE_LUI='0', DEF_ENRL_EST=0, DEF_MAX_ENRL=0, RT_DESCR_ID='bd5c8f8d-9e3f-4a2b-a075-fd1900feeddd', EFF_DT=TIMESTAMP '2015-08-31 00:00:00.0', IS_ENRL='0', EXP_FIRST_ATP='kuali.atp.2015Fall', EXPIR_DT='', FEE_ID='8cfdb2f0-3367-44f1-9306-47133c2a9a85', HAS_EARLY_DROP_DEDLN='0', IS_HAZR_DISBLD_STU='0', CLU_INTSTY_QTY='', CLU_INTSTY_TYPE='', LAST_ADMIT_ATP='', LAST_ATP='', LUTYPE_ID='kuali.lu.type.CreditCourse', NEXT_REVIEW_PRD='', OFFIC_CLU_ID='322cbc1f-173d-4d8a-93b3-1dc7e4ebdb98', PRI_INSTR_ID='', REF_URL='', ST='Draft', ATP_DUR_TYP_KEY='', TM_QUANTITY='', STDY_SUBJ_AREA='' where ID='c52d6daa-6ce2-4237-874b-1286a7640057' and VER_NBR=1
/
update KSLU_CLU_ADMIN_ORG set OBJ_ID='dafdf2e0-1908-41cd-b75b-87c21923b0f6', VER_NBR=1, CLU_ID='c52d6daa-6ce2-4237-874b-1286a7640057', IS_PR='0', ORG_ID='4284516367', TYPE='kuali.adminOrg.type.CurriculumOversight' where ID='7a3a1d3b-319d-452d-a66c-5dfa4afc5f4d' and VER_NBR=0
/
update KSLU_CLU set OBJ_ID='bc63ecc5-3cd0-4cd8-969c-1b9e1b657794', VER_NBR=1, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:23:54.932', CURR_VER_END='', CURR_VER_START=TIMESTAMP '2013-02-18 13:23:33.928', SEQ_NUM=1, VER_CMT='', VER_IND_ID='de09dadc-c921-4251-9dc9-43a01d1748a2', VER_FROM_ID='', ACCT_ID='', CAN_CREATE_LUI='0', DEF_ENRL_EST=0, DEF_MAX_ENRL=0, RT_DESCR_ID='', EFF_DT='', IS_ENRL='0', EXP_FIRST_ATP='', EXPIR_DT='', FEE_ID='', HAS_EARLY_DROP_DEDLN='0', IS_HAZR_DISBLD_STU='0', CLU_INTSTY_QTY='', CLU_INTSTY_TYPE='', LAST_ADMIT_ATP='', LAST_ATP='', LUTYPE_ID='kuali.lu.type.CreditCourseFormatShell', NEXT_REVIEW_PRD='', OFFIC_CLU_ID='', PRI_INSTR_ID='', REF_URL='', ST='Draft', ATP_DUR_TYP_KEY='', TM_QUANTITY='', STDY_SUBJ_AREA='' where ID='11fb7dd1-d1b5-4168-bd16-5e32256ad4c5' and VER_NBR=0
/
update KSLU_CLU set OBJ_ID='07a6a82c-4cc1-420f-8d2b-ed664f424b16', VER_NBR=1, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:23:54.941', CURR_VER_END='', CURR_VER_START=TIMESTAMP '2013-02-18 13:23:33.955', SEQ_NUM=1, VER_CMT='', VER_IND_ID='592c026b-2056-41cb-9173-e83084d9b456', VER_FROM_ID='', ACCT_ID='', CAN_CREATE_LUI='0', DEF_ENRL_EST=0, DEF_MAX_ENRL=0, RT_DESCR_ID='', EFF_DT='', IS_ENRL='0', EXP_FIRST_ATP='', EXPIR_DT='', FEE_ID='', HAS_EARLY_DROP_DEDLN='0', IS_HAZR_DISBLD_STU='0', CLU_INTSTY_QTY='3', CLU_INTSTY_TYPE='kuali.atp.duration.week', LAST_ADMIT_ATP='', LAST_ATP='', LUTYPE_ID='kuali.lu.type.activity.Lecture', NEXT_REVIEW_PRD='', OFFIC_CLU_ID='', PRI_INSTR_ID='', REF_URL='', ST='Draft', ATP_DUR_TYP_KEY='', TM_QUANTITY='', STDY_SUBJ_AREA='' where ID='387b2f8c-f9ce-44c6-bc3e-2915e6fd4adf' and VER_NBR=0
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('b0a7dc4d-0443-4d60-80e6-c9f1a020ed48', 0, '', 'Grading options', 'c1154803-2afc-4580-8e57-211e791e6e46')
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('cee3e113-932b-464d-869f-41c72b160739', 0, '', 'Grading option', '3ddba9c9-6cf6-4e95-baa4-4b7625e34329')
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('8e7c914f-4fa1-4fd0-ba50-18c9a45d632d', 0, '', 'Grading option', '432c92c2-71dd-47d7-bc64-9dd992e6bde8')
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('79663500-0e82-4f0b-a323-16fd6ee469b5', 0, '', 'Grading option', 'b25159c2-789b-4341-bd58-76bc87c38f6c')
/
update KSLU_CLU_RSLT set OBJ_ID='0d18a63a-c817-4d4d-98ca-fed37d17796e', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-02-18 13:23:33.967', CLU_ID='c52d6daa-6ce2-4237-874b-1286a7640057', TYPE_KEY_ID='kuali.resultType.gradeCourseResult', RT_DESCR_ID='c1154803-2afc-4580-8e57-211e791e6e46', EFF_DT=TIMESTAMP '2013-02-18 13:23:33.838', EXPIR_DT='', ST='Draft' where ID='c8636e8e-e36e-4d7c-b53a-30f328eeb42f' and VER_NBR=0
/
update KSLU_RSLT_OPT set OBJ_ID='0a78c67b-5ce0-4d62-939b-7d7166e99425', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-02-18 13:23:33.967', RT_DESCR_ID='3ddba9c9-6cf6-4e95-baa4-4b7625e34329', EFF_DT='', EXPIR_DT='', RES_COMP_ID='kuali.resultComponent.grade.letter', RES_USAGE_ID='', ST='Draft' where ID='965d3569-e39e-4713-962c-b9d8dec33547' and VER_NBR=0
/
update KSLU_RSLT_OPT set OBJ_ID='32b8fc74-35c9-47d0-8a6a-ae598a9ae748', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-02-18 13:23:33.967', RT_DESCR_ID='432c92c2-71dd-47d7-bc64-9dd992e6bde8', EFF_DT='', EXPIR_DT='', RES_COMP_ID='kuali.resultComponent.grade.passFail', RES_USAGE_ID='', ST='Draft' where ID='658cf5c4-2b2b-4930-bf7b-83204f687389' and VER_NBR=0
/
update KSLU_RSLT_OPT set OBJ_ID='538ada1f-8111-44d7-ad85-6c707999cbe2', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-02-18 13:23:33.967', RT_DESCR_ID='b25159c2-789b-4341-bd58-76bc87c38f6c', EFF_DT='', EXPIR_DT='', RES_COMP_ID='kuali.resultComponent.grade.audit', RES_USAGE_ID='', ST='Draft' where ID='63cf6f9a-8455-49eb-b28b-2b3b2769c77c' and VER_NBR=0
/

insert into KSPR_PROPOSAL_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('76049fa0-4011-4fed-988c-3650c41eb082', 0, 'workflowNode', 'PreRoute', 'd8b6cb16-0c95-4994-925d-b8061afb4d27', '115ba5fd-877c-4032-ad87-a7df692a57ed')
/
insert into KSPR_PROPOSAL_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('0196e22a-4bbe-4966-82bb-76dd387f8e60', 0, 'prevStartTerm', 'kuali.atp.1980Spring', 'd8b6cb16-0c95-4994-925d-b8061afb4d27', 'e8c28adf-4ad1-4b4e-adbf-e83d512a19ee')
/
update KSPR_PROPOSAL set OBJ_ID='5aa29f7d-fd37-42fb-8864-a8be8940e786', VER_NBR=2, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:23:55.112', DETAIL_DESC='', EFF_DT='', EXPIR_DT='', NAME='Modify: Special Projects', RATIONALE='', STATE='Saved', TYPE='kuali.proposal.type.course.modify', WORKFLOW_ID='3013' where PROPOSAL_ID='d8b6cb16-0c95-4994-925d-b8061afb4d27' and VER_NBR=1
/
insert into KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) values ('d8b6cb16-0c95-4994-925d-b8061afb4d27', '254128dd-62cc-4fcb-9405-4bf3b4f923d6')
/

insert into KSLU_CLU_ACCT (OBJ_ID, VER_NBR, ID) values ('d5711df9-70b9-43f0-800c-382588428b0e', 0, 'c1008b5a-4b66-47f7-bea4-1deb0ccadb43')
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('db2bf38b-4fd3-4811-a275-cbf46c3debf5', 0, 'Causes and consequences of neoplastic transformations at the biochemica and cellular levels.', 'Causes and consequences of neoplastic transformations at the biochemica and cellular levels.', '20bc8481-a9cd-4d6c-83ad-5235ed41bddd')
/
insert into KSLU_CLU_FEE (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, RT_DESCR_ID, ID) values ('280aba3b-b459-472b-b9bd-8d764c47a0c8', 0, 'admin', TIMESTAMP '2013-02-18 13:26:06.922', 'admin', TIMESTAMP '2013-02-18 13:26:06.922', '', '9b78c41c-22e3-4d5b-8096-848307b86fc2')
/
insert into KSLU_CLU_IDENT (OBJ_ID, VER_NBR, CD, DIVISION, LVL, LNG_NAME, ORG_ID, SHRT_NAME, ST, SUFX_CD, TYPE, VARTN, ID) values ('840bd17c-5a2b-41c4-b8ff-c1df42419afe', 0, 'BSCI433', 'BSCI', '400', 'Biology of Cancer', '', 'BIOLOGY OF CANCER', 'Active', '433', 'kuali.lu.type.CreditCourse.identifier.official', '', 'd3233f27-aebf-4201-8a46-8b804a3aff06')
/
insert into KSLU_CLU (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CURR_VER_END, CURR_VER_START, SEQ_NUM, VER_CMT, VER_IND_ID, VER_FROM_ID, ACCT_ID, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, RT_DESCR_ID, EFF_DT, IS_ENRL, EXP_FIRST_ATP, EXPIR_DT, FEE_ID, HAS_EARLY_DROP_DEDLN, IS_HAZR_DISBLD_STU, CLU_INTSTY_QTY, CLU_INTSTY_TYPE, LAST_ADMIT_ATP, LAST_ATP, LUTYPE_ID, NEXT_REVIEW_PRD, OFFIC_CLU_ID, PRI_INSTR_ID, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ID) values ('b5356130-4482-4d52-810e-45ae7c1bbaf7', 0, 'admin', TIMESTAMP '2013-02-18 13:26:06.922', 'admin', TIMESTAMP '2013-02-18 13:26:06.922', '', '', 3, 'versionComment', 'cc61d77d-39fe-496d-8b43-042525466055', 'c417fdc9-3f58-40f8-828f-68900e24ead9', 'c1008b5a-4b66-47f7-bea4-1deb0ccadb43', '0', 0, 0, '20bc8481-a9cd-4d6c-83ad-5235ed41bddd', TIMESTAMP '2012-08-29 00:00:00.0', '0', 'kuali.atp.2012Fall', '', '9b78c41c-22e3-4d5b-8096-848307b86fc2', '0', '0', '', '', '', '', 'kuali.lu.type.CreditCourse', '', 'd3233f27-aebf-4201-8a46-8b804a3aff06', '', '', 'Draft', '', '', '', '73a7f904-9e76-44cf-9c96-02a023230405')
/
insert into KSLU_CLU_ADMIN_ORG (OBJ_ID, VER_NBR, CLU_ID, IS_PR, ORG_ID, TYPE, ID) values ('83c37fe6-0a26-45b3-aa6a-5c2a32b14006', 0, '73a7f904-9e76-44cf-9c96-02a023230405', '0', '576639460', 'kuali.adminOrg.type.CurriculumOversight', '57467d92-71c8-4d36-8685-d1967da9d8cd')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('ebf967a3-8151-48ff-9eb9-58d8a7ec4364', 0, 'semesterType', 'semesterType.standard', '73a7f904-9e76-44cf-9c96-02a023230405', '24cccade-b836-4d05-b4a2-f91819ff6a49')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('147b3601-c790-4445-90e9-defaa762dbba', 0, 'activityTotalCredits', '3', '73a7f904-9e76-44cf-9c96-02a023230405', '3d22b969-9ea4-4d8c-9ea1-92b67a2683a4')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('67902b41-48b3-4774-b0e7-4433aaaa9d6d', 0, 'APandIB', '0', '73a7f904-9e76-44cf-9c96-02a023230405', '7704a0cd-b4e7-4ea8-b891-bccb62a35a45')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('51c72482-87e7-47d2-be4a-778aec274e6b', 0, 'activityTypeExplanation', '<null>', '73a7f904-9e76-44cf-9c96-02a023230405', '68722f4b-0736-441a-9f8c-482ec9ac31c3')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('1abbe207-4c4f-4af7-8e19-de6ee705cbfa', 0, 'finalExamStatus', 'STD', '73a7f904-9e76-44cf-9c96-02a023230405', '90e9ea6e-ab8a-43f3-82ac-2d4631c96405')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('9642a5c4-ccb3-4a6a-94f3-71ffa5b870d8', 0, 'passFail', '1', '73a7f904-9e76-44cf-9c96-02a023230405', '649fc431-5d0a-4d87-8bc6-0f9bdf05d0cb')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('389cee92-4432-4dbf-96d4-efa063e04df9', 0, 'repeatableIfContentDiffers', '0', '73a7f904-9e76-44cf-9c96-02a023230405', '6f8c4b62-7770-45d4-9dc4-d8da4dec0864')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('9f908764-c767-4556-8ba5-c278745fad3e', 0, 'coreGeneralEducation', '', '73a7f904-9e76-44cf-9c96-02a023230405', '452384a9-9e7b-4b72-952f-d453b9b32621')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('ad5150d2-eb76-4f89-85a9-31500ebfc77c', 0, 'audit', '1', '73a7f904-9e76-44cf-9c96-02a023230405', '7a2845f6-25a6-4e70-a316-50c8656db7e2')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('b97022fe-119e-46fb-9b17-1f9e1a3e6bb0', 0, 'formerly', '', '73a7f904-9e76-44cf-9c96-02a023230405', '2cd74ecf-cd87-4bcf-913a-351c8a55d01c')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('c6187ce1-939a-43b3-ba18-d06997559540', 0, 'activityTotalContactHours', '45', '73a7f904-9e76-44cf-9c96-02a023230405', '705b3efe-d405-412f-ad06-640db8d78fcc')
/
insert into KSLU_CLU_JN_CAMP_LOC (OBJ_ID, VER_NBR, CAMP_LOC, CLU_ID, ID) values ('9cd1a05f-21a8-4a87-a5e9-bc1d902dbcd5', 0, 'NO', '73a7f904-9e76-44cf-9c96-02a023230405', 'bc8b4be7-2f1e-4c06-96c6-521a5312d17e')
/
insert into KSLU_CLU_ADMIN_ORG (OBJ_ID, VER_NBR, CLU_ID, IS_PR, ORG_ID, TYPE, ID) values ('30a7e081-b16c-43a2-98a3-d2b8c89a1382', 0, '73a7f904-9e76-44cf-9c96-02a023230405', '0', '576639460', 'kuali.adminOrg.type.CurriculumOversight', '86e39dd4-e418-4eb6-bee3-e96a8c364b7f')
/
update KSLU_CLU set OBJ_ID='b5356130-4482-4d52-810e-45ae7c1bbaf7', VER_NBR=1, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:26:06.95', CURR_VER_END='', CURR_VER_START='', SEQ_NUM=3, VER_CMT='versionComment', VER_IND_ID='cc61d77d-39fe-496d-8b43-042525466055', VER_FROM_ID='c417fdc9-3f58-40f8-828f-68900e24ead9', ACCT_ID='c1008b5a-4b66-47f7-bea4-1deb0ccadb43', CAN_CREATE_LUI='0', DEF_ENRL_EST=0, DEF_MAX_ENRL=0, RT_DESCR_ID='20bc8481-a9cd-4d6c-83ad-5235ed41bddd', EFF_DT=TIMESTAMP '2012-08-29 00:00:00.0', IS_ENRL='0', EXP_FIRST_ATP='', EXPIR_DT='', FEE_ID='9b78c41c-22e3-4d5b-8096-848307b86fc2', HAS_EARLY_DROP_DEDLN='0', IS_HAZR_DISBLD_STU='0', CLU_INTSTY_QTY='', CLU_INTSTY_TYPE='', LAST_ADMIT_ATP='', LAST_ATP='', LUTYPE_ID='kuali.lu.type.CreditCourse', NEXT_REVIEW_PRD='', OFFIC_CLU_ID='d3233f27-aebf-4201-8a46-8b804a3aff06', PRI_INSTR_ID='', REF_URL='', ST='Draft', ATP_DUR_TYP_KEY='', TM_QUANTITY='', STDY_SUBJ_AREA='' where ID='73a7f904-9e76-44cf-9c96-02a023230405' and VER_NBR=0
/
update KSLU_CLU_ACCT set OBJ_ID='d5711df9-70b9-43f0-800c-382588428b0e', VER_NBR=1 where ID='c1008b5a-4b66-47f7-bea4-1deb0ccadb43' and VER_NBR=0
/
update KSLU_CLU_FEE set OBJ_ID='280aba3b-b459-472b-b9bd-8d764c47a0c8', VER_NBR=1, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:26:06.95', RT_DESCR_ID='' where ID='9b78c41c-22e3-4d5b-8096-848307b86fc2' and VER_NBR=0
/
update KSLU_CLU_IDENT set OBJ_ID='840bd17c-5a2b-41c4-b8ff-c1df42419afe', VER_NBR=1, CD='BSCI433', DIVISION='BSCI', LVL='400', LNG_NAME='Biology of Cancer', ORG_ID='', SHRT_NAME='BIOLOGY OF CANCER', ST='Draft', SUFX_CD='433', TYPE='kuali.lu.type.CreditCourse.identifier.official', VARTN='' where ID='d3233f27-aebf-4201-8a46-8b804a3aff06' and VER_NBR=0
/
update KSLU_CLU_ADMIN_ORG set OBJ_ID='30a7e081-b16c-43a2-98a3-d2b8c89a1382', VER_NBR=1, CLU_ID='73a7f904-9e76-44cf-9c96-02a023230405', IS_PR='0', ORG_ID='576639460', TYPE='kuali.adminOrg.type.CurriculumOversight' where ID='86e39dd4-e418-4eb6-bee3-e96a8c364b7f' and VER_NBR=0
/

insert into KSPR_PROPOSAL_REFERENCE (OBJ_ID, VER_NBR, OBJECT_REFERENCE_ID, TYPE, REFERENCE_ID) values ('11818cf0-e17b-4a27-8a18-0957bc9186a7', 0, '73a7f904-9e76-44cf-9c96-02a023230405', 'kuali.proposal.referenceType.clu', '10f44af3-9309-4760-a4f8-3c5856e12b67')
/


insert into KSPR_PROPOSAL (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, DETAIL_DESC, EFF_DT, EXPIR_DT, NAME, RATIONALE, STATE, TYPE, WORKFLOW_ID, PROPOSAL_ID) values ('16526b71-ac68-451a-8d2e-859c6ed8d361', 0, 'admin', TIMESTAMP '2013-02-18 13:26:07.098', 'admin', TIMESTAMP '2013-02-18 13:26:07.098', '', '', '', 'Modify: Biology of Cancer', '', 'Saved', 'kuali.proposal.type.course.modify', '', 'cee1f753-a3d8-49b8-a1f7-12fbf62f7226')
/
insert into KSPR_PROPOSAL_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('4e115dc4-c2ee-411f-bc59-cc920308056f', 0, 'workflowNode', 'PreRoute', 'cee1f753-a3d8-49b8-a1f7-12fbf62f7226', 'f3f207bb-63d1-47da-ad16-f058fbf59f6f')
/
insert into KSPR_PROPOSAL_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('9ee25d74-645f-425e-af8b-ae774e8d1a1a', 0, 'prevStartTerm', 'kuali.atp.2012Fall', 'cee1f753-a3d8-49b8-a1f7-12fbf62f7226', '3fb97e15-07d6-4e60-b443-211778c22b78')
/
insert into KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) values ('cee1f753-a3d8-49b8-a1f7-12fbf62f7226', '10f44af3-9309-4760-a4f8-3c5856e12b67')
/
update KSPR_PROPOSAL set OBJ_ID='16526b71-ac68-451a-8d2e-859c6ed8d361', VER_NBR=1, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:26:07.237', DETAIL_DESC='', EFF_DT='', EXPIR_DT='', NAME='Modify: Biology of Cancer', RATIONALE='', STATE='Saved', TYPE='kuali.proposal.type.course.modify', WORKFLOW_ID='3014' where PROPOSAL_ID='cee1f753-a3d8-49b8-a1f7-12fbf62f7226' and VER_NBR=0
/
insert into KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) values ('cee1f753-a3d8-49b8-a1f7-12fbf62f7226', '10f44af3-9309-4760-a4f8-3c5856e12b67')
/
insert into KSPR_PROPOSAL_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('63805a2b-b92f-492b-9fc6-4e3839c8a930', 0, 'workflowNode', 'PreRoute', 'cee1f753-a3d8-49b8-a1f7-12fbf62f7226', '34653125-7982-4920-a2f8-221be1f10c84')
/
insert into KSPR_PROPOSAL_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('463bc05b-9ad6-4598-9c26-2d2f2daebb4c', 0, 'prevStartTerm', 'kuali.atp.2012Fall', 'cee1f753-a3d8-49b8-a1f7-12fbf62f7226', 'b51c7999-98f1-448d-92fa-529bedd057e4')
/
update KSPR_PROPOSAL set OBJ_ID='16526b71-ac68-451a-8d2e-859c6ed8d361', VER_NBR=2, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:26:17.12', DETAIL_DESC='', EFF_DT='', EXPIR_DT='', NAME='Modify: Biology of Cancer', RATIONALE='', STATE='Saved', TYPE='kuali.proposal.type.course.modify', WORKFLOW_ID='3014' where PROPOSAL_ID='cee1f753-a3d8-49b8-a1f7-12fbf62f7226' and VER_NBR=1
/
insert into KSPR_PROPOSAL_JN_REFERENCE (PROPOSAL_ID, REFERENCE_ID) values ('cee1f753-a3d8-49b8-a1f7-12fbf62f7226', '10f44af3-9309-4760-a4f8-3c5856e12b67')
/

insert into KSLU_CLU (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CURR_VER_END, CURR_VER_START, SEQ_NUM, VER_CMT, VER_IND_ID, VER_FROM_ID, ACCT_ID, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, RT_DESCR_ID, EFF_DT, IS_ENRL, EXP_FIRST_ATP, EXPIR_DT, FEE_ID, HAS_EARLY_DROP_DEDLN, IS_HAZR_DISBLD_STU, CLU_INTSTY_QTY, CLU_INTSTY_TYPE, LAST_ADMIT_ATP, LAST_ATP, LUTYPE_ID, NEXT_REVIEW_PRD, OFFIC_CLU_ID, PRI_INSTR_ID, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ID) values ('2a73514d-3867-42c2-a24d-01594f2df255', 0, 'admin', TIMESTAMP '2013-02-18 13:26:07.022', 'admin', TIMESTAMP '2013-02-18 13:26:07.022', '', TIMESTAMP '2013-02-18 13:26:07.022', 1, '', 'a3edc7a6-0c18-485c-b8ea-85418a9a1da2', '', '', '0', 0, 0, '', '', '0', '', '', '', '0', '0', '', '', '', '', 'kuali.lu.type.CreditCourseFormatShell', '', '', '', '', 'Draft', '', '', '', 'e5124293-9503-4a2b-a29f-4eec58ba46e4')
/
insert into KSLU_CLU (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CURR_VER_END, CURR_VER_START, SEQ_NUM, VER_CMT, VER_IND_ID, VER_FROM_ID, ACCT_ID, CAN_CREATE_LUI, DEF_ENRL_EST, DEF_MAX_ENRL, RT_DESCR_ID, EFF_DT, IS_ENRL, EXP_FIRST_ATP, EXPIR_DT, FEE_ID, HAS_EARLY_DROP_DEDLN, IS_HAZR_DISBLD_STU, CLU_INTSTY_QTY, CLU_INTSTY_TYPE, LAST_ADMIT_ATP, LAST_ATP, LUTYPE_ID, NEXT_REVIEW_PRD, OFFIC_CLU_ID, PRI_INSTR_ID, REF_URL, ST, ATP_DUR_TYP_KEY, TM_QUANTITY, STDY_SUBJ_AREA, ID) values ('291c54fa-a7fc-4e70-a9e0-f6460c1218a0', 0, 'admin', TIMESTAMP '2013-02-18 13:26:07.047', 'admin', TIMESTAMP '2013-02-18 13:26:07.047', '', TIMESTAMP '2013-02-18 13:26:07.047', 1, '', 'c26a0e65-8a97-41e3-b2f9-87c1bd5eee56', '', '', '0', 0, 0, '', '', '0', '', '', '', '0', '0', '3', 'kuali.atp.duration.week', '', '', 'kuali.lu.type.activity.Lecture', '', '', '', '', 'Draft', '', '', '', 'e2a6dc71-aaa9-48dd-a5fc-73af9725c849')
/
insert into KSLU_CLUCLU_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CLU_ID, CLU_RELTN_REQ, EFF_DT, EXPIR_DT, LU_RELTN_TYPE_ID, RELATED_CLU_ID, ST, ID) values ('8412fa1b-8f2c-41bf-ace1-55a037da87be', 0, 'admin', TIMESTAMP '2013-02-18 13:26:07.05', 'admin', TIMESTAMP '2013-02-18 13:26:07.05', 'e5124293-9503-4a2b-a29f-4eec58ba46e4', '1', '', '', 'luLuRelationType.contains', 'e2a6dc71-aaa9-48dd-a5fc-73af9725c849', 'Draft', 'cfef76db-c94c-4ff6-8df9-d9c46386f835')
/
insert into KSLU_CLUCLU_RELTN (OBJ_ID, VER_NBR, createId, createTime, updateId, updateTime, CLU_ID, CLU_RELTN_REQ, EFF_DT, EXPIR_DT, LU_RELTN_TYPE_ID, RELATED_CLU_ID, ST, ID) values ('6419968a-d3c1-4453-a663-7b67009db61a', 0, 'admin', TIMESTAMP '2013-02-18 13:26:07.05', 'admin', TIMESTAMP '2013-02-18 13:26:07.05', '73a7f904-9e76-44cf-9c96-02a023230405', '1', '', '', 'luLuRelationType.hasCourseFormat', 'e5124293-9503-4a2b-a29f-4eec58ba46e4', 'Draft', '8f7292a7-f74e-4ebd-86f0-d13128644db2')
/
insert into KSLU_RSLT_OPT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, RT_DESCR_ID, EFF_DT, EXPIR_DT, RES_COMP_ID, RES_USAGE_ID, ST, ID) values ('2fb6c6e7-406d-4531-af07-b3f0da8749b1', 0, 'admin', TIMESTAMP '2013-02-18 13:26:06.841', 'admin', TIMESTAMP '2013-02-18 13:26:07.051', '', '', '', 'kuali.creditType.credit.degree.3.0', '', 'Draft', '548874ef-febc-4150-8ac8-bbd416bcbcf0')
/
insert into KSLU_CLU_RSLT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, CLU_ID, TYPE_KEY_ID, RT_DESCR_ID, EFF_DT, EXPIR_DT, ST, ID) values ('c3b15b79-0519-46f8-b980-56fae3a1ef20', 0, 'admin', TIMESTAMP '2013-02-18 13:26:06.841', 'admin', TIMESTAMP '2013-02-18 13:26:07.051', '73a7f904-9e76-44cf-9c96-02a023230405', 'kuali.resultType.creditCourseResult', '', '', '', 'Draft', '611f55c6-90d1-4e38-89c1-f31da6d80800')
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('724d86e7-bbc2-4875-a0f2-ffd03dd21447', 0, '', 'Grading option', 'cd922004-0378-49af-8cdc-55328fe5f35d')
/
insert into KSLU_RSLT_OPT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, RT_DESCR_ID, EFF_DT, EXPIR_DT, RES_COMP_ID, RES_USAGE_ID, ST, ID) values ('40d635d9-ec8a-48e5-ad6d-fdf7cef4e82c', 0, 'admin', TIMESTAMP '2013-02-18 13:26:06.841', 'admin', TIMESTAMP '2013-02-18 13:26:07.052', 'cd922004-0378-49af-8cdc-55328fe5f35d', '', '', 'kuali.resultComponent.grade.letter', '', 'Draft', '20a0e9d9-b533-47a4-9419-04ccc22b9609')
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('514e1eb8-981b-4e5b-94ca-af61d6cacf10', 0, '', 'Grading option', 'c2fffb3f-53be-4ce0-9e8b-bd770c26605e')
/
insert into KSLU_RSLT_OPT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, RT_DESCR_ID, EFF_DT, EXPIR_DT, RES_COMP_ID, RES_USAGE_ID, ST, ID) values ('64b174d5-43bc-4abc-ac2f-491ff3beccbc', 0, 'admin', TIMESTAMP '2013-02-18 13:26:06.841', 'admin', TIMESTAMP '2013-02-18 13:26:07.052', 'c2fffb3f-53be-4ce0-9e8b-bd770c26605e', '', '', 'kuali.resultComponent.grade.audit', '', 'Draft', '71ddff84-f25e-40c0-8a33-0825e6d0cc61')
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('faac8a0a-50d4-4e0b-b596-2c297e565a64', 0, '', 'Grading options', '4fd6a9bc-16bf-4dbf-bf29-87f95d10e072')
/
insert into KSLU_CLU_RSLT (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, CLU_ID, TYPE_KEY_ID, RT_DESCR_ID, EFF_DT, EXPIR_DT, ST, ID) values ('a0ea02bb-43d0-43ac-8ab9-be8dbe0bddad', 0, 'admin', TIMESTAMP '2013-02-18 13:26:06.841', 'admin', TIMESTAMP '2013-02-18 13:26:07.052', '73a7f904-9e76-44cf-9c96-02a023230405', 'kuali.resultType.gradeCourseResult', '4fd6a9bc-16bf-4dbf-bf29-87f95d10e072', TIMESTAMP '2013-02-18 13:26:06.937', '', 'Draft', '5aba9c06-defa-438a-957d-9583850bb185')
/
insert into KSLU_CLURES_JN_RESOPT (CLU_RES_ID, RES_OPT_ID) values ('611f55c6-90d1-4e38-89c1-f31da6d80800', '548874ef-febc-4150-8ac8-bbd416bcbcf0')
/
insert into KSLU_CLURES_JN_RESOPT (CLU_RES_ID, RES_OPT_ID) values ('5aba9c06-defa-438a-957d-9583850bb185', '20a0e9d9-b533-47a4-9419-04ccc22b9609')
/
insert into KSLU_CLURES_JN_RESOPT (CLU_RES_ID, RES_OPT_ID) values ('5aba9c06-defa-438a-957d-9583850bb185', '71ddff84-f25e-40c0-8a33-0825e6d0cc61')
/

insert into KSLU_CLU_ADMIN_ORG (OBJ_ID, VER_NBR, CLU_ID, IS_PR, ORG_ID, TYPE, ID) values ('827610b3-40f3-4678-9305-dcb14674b98d', 0, '73a7f904-9e76-44cf-9c96-02a023230405', '0', '576639460', 'kuali.adminOrg.type.CurriculumOversight', 'cb001321-c014-4ac9-88f0-5ab4d09a5f05')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('9a8427b8-1dee-484a-9db5-b0316bb012a4', 0, 'finalExamStatus', 'STD', '73a7f904-9e76-44cf-9c96-02a023230405', 'fa40ee05-ac33-4b1e-a63d-033006d33637')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('81b1925a-23ff-4995-859b-6fedb7cd09fa', 0, 'passFail', '1', '73a7f904-9e76-44cf-9c96-02a023230405', 'daa96d81-9666-467f-9c54-c278768ae694')
/
insert into KSLU_CLU_ATTR (OBJ_ID, VER_NBR, ATTR_NAME, ATTR_VALUE, OWNER, ID) values ('d6c8fc27-f83f-468b-844b-fad6a449e9c7', 0, 'audit', '1', '73a7f904-9e76-44cf-9c96-02a023230405', 'c4ce47ea-1ee9-4bea-8824-b418a70bb172')
/
update KSLU_CLU_ACCT set OBJ_ID='d5711df9-70b9-43f0-800c-382588428b0e', VER_NBR=2 where ID='c1008b5a-4b66-47f7-bea4-1deb0ccadb43' and VER_NBR=1
/
update KSLU_CLU_FEE set OBJ_ID='280aba3b-b459-472b-b9bd-8d764c47a0c8', VER_NBR=2, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:26:16.952', RT_DESCR_ID='' where ID='9b78c41c-22e3-4d5b-8096-848307b86fc2' and VER_NBR=1
/
update KSLU_CLU_IDENT set OBJ_ID='840bd17c-5a2b-41c4-b8ff-c1df42419afe', VER_NBR=2, CD='BSCI433', DIVISION='BSCI', LVL='400', LNG_NAME='Biology of Cancer', ORG_ID='', SHRT_NAME='BIOLOGY OF CANCER', ST='Draft', SUFX_CD='433', TYPE='kuali.lu.type.CreditCourse.identifier.official', VARTN='' where ID='d3233f27-aebf-4201-8a46-8b804a3aff06' and VER_NBR=1
/
update KSLU_CLU set OBJ_ID='b5356130-4482-4d52-810e-45ae7c1bbaf7', VER_NBR=2, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:26:16.952', CURR_VER_END='', CURR_VER_START='', SEQ_NUM=3, VER_CMT='versionComment', VER_IND_ID='cc61d77d-39fe-496d-8b43-042525466055', VER_FROM_ID='c417fdc9-3f58-40f8-828f-68900e24ead9', ACCT_ID='c1008b5a-4b66-47f7-bea4-1deb0ccadb43', CAN_CREATE_LUI='0', DEF_ENRL_EST=0, DEF_MAX_ENRL=0, RT_DESCR_ID='20bc8481-a9cd-4d6c-83ad-5235ed41bddd', EFF_DT=TIMESTAMP '2016-01-25 00:00:00.0', IS_ENRL='0', EXP_FIRST_ATP='kuali.atp.2016Spring', EXPIR_DT='', FEE_ID='9b78c41c-22e3-4d5b-8096-848307b86fc2', HAS_EARLY_DROP_DEDLN='0', IS_HAZR_DISBLD_STU='0', CLU_INTSTY_QTY='', CLU_INTSTY_TYPE='', LAST_ADMIT_ATP='', LAST_ATP='', LUTYPE_ID='kuali.lu.type.CreditCourse', NEXT_REVIEW_PRD='', OFFIC_CLU_ID='d3233f27-aebf-4201-8a46-8b804a3aff06', PRI_INSTR_ID='', REF_URL='', ST='Draft', ATP_DUR_TYP_KEY='', TM_QUANTITY='', STDY_SUBJ_AREA='' where ID='73a7f904-9e76-44cf-9c96-02a023230405' and VER_NBR=1
/
update KSLU_CLU_ADMIN_ORG set OBJ_ID='827610b3-40f3-4678-9305-dcb14674b98d', VER_NBR=1, CLU_ID='73a7f904-9e76-44cf-9c96-02a023230405', IS_PR='0', ORG_ID='576639460', TYPE='kuali.adminOrg.type.CurriculumOversight' where ID='cb001321-c014-4ac9-88f0-5ab4d09a5f05' and VER_NBR=0
/
update KSLU_CLU set OBJ_ID='2a73514d-3867-42c2-a24d-01594f2df255', VER_NBR=1, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:26:16.966', CURR_VER_END='', CURR_VER_START=TIMESTAMP '2013-02-18 13:26:07.022', SEQ_NUM=1, VER_CMT='', VER_IND_ID='a3edc7a6-0c18-485c-b8ea-85418a9a1da2', VER_FROM_ID='', ACCT_ID='', CAN_CREATE_LUI='0', DEF_ENRL_EST=0, DEF_MAX_ENRL=0, RT_DESCR_ID='', EFF_DT='', IS_ENRL='0', EXP_FIRST_ATP='', EXPIR_DT='', FEE_ID='', HAS_EARLY_DROP_DEDLN='0', IS_HAZR_DISBLD_STU='0', CLU_INTSTY_QTY='', CLU_INTSTY_TYPE='', LAST_ADMIT_ATP='', LAST_ATP='', LUTYPE_ID='kuali.lu.type.CreditCourseFormatShell', NEXT_REVIEW_PRD='', OFFIC_CLU_ID='', PRI_INSTR_ID='', REF_URL='', ST='Draft', ATP_DUR_TYP_KEY='', TM_QUANTITY='', STDY_SUBJ_AREA='' where ID='e5124293-9503-4a2b-a29f-4eec58ba46e4' and VER_NBR=0
/
update KSLU_CLU set OBJ_ID='291c54fa-a7fc-4e70-a9e0-f6460c1218a0', VER_NBR=1, updateId='admin', updateTime=TIMESTAMP '2013-02-18 13:26:16.976', CURR_VER_END='', CURR_VER_START=TIMESTAMP '2013-02-18 13:26:07.047', SEQ_NUM=1, VER_CMT='', VER_IND_ID='c26a0e65-8a97-41e3-b2f9-87c1bd5eee56', VER_FROM_ID='', ACCT_ID='', CAN_CREATE_LUI='0', DEF_ENRL_EST=0, DEF_MAX_ENRL=0, RT_DESCR_ID='', EFF_DT='', IS_ENRL='0', EXP_FIRST_ATP='', EXPIR_DT='', FEE_ID='', HAS_EARLY_DROP_DEDLN='0', IS_HAZR_DISBLD_STU='0', CLU_INTSTY_QTY='3', CLU_INTSTY_TYPE='kuali.atp.duration.week', LAST_ADMIT_ATP='', LAST_ATP='', LUTYPE_ID='kuali.lu.type.activity.Lecture', NEXT_REVIEW_PRD='', OFFIC_CLU_ID='', PRI_INSTR_ID='', REF_URL='', ST='Draft', ATP_DUR_TYP_KEY='', TM_QUANTITY='', STDY_SUBJ_AREA='' where ID='e2a6dc71-aaa9-48dd-a5fc-73af9725c849' and VER_NBR=0
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('551ddfaa-1cf4-4f1a-8b4e-6c454f1f6b41', 0, '', 'Grading options', '61a85e33-0979-45f9-b317-b762266fcd33')
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('c32d3d91-bd16-47a4-a079-688bcae61748', 0, '', 'Grading option', '984ca92f-5305-45b7-95a8-e0f74a259035')
/
insert into KSLU_RICH_TEXT_T (OBJ_ID, VER_NBR, FORMATTED, PLAIN, ID) values ('7189d2e1-6d44-4ccd-9264-358a9b938de6', 0, '', 'Grading option', 'bcacd775-faa5-4912-95d3-9936d7f53d0b')
/
update KSLU_CLU_RSLT set OBJ_ID='a0ea02bb-43d0-43ac-8ab9-be8dbe0bddad', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-02-18 13:26:07.052', CLU_ID='73a7f904-9e76-44cf-9c96-02a023230405', TYPE_KEY_ID='kuali.resultType.gradeCourseResult', RT_DESCR_ID='61a85e33-0979-45f9-b317-b762266fcd33', EFF_DT=TIMESTAMP '2013-02-18 13:26:06.937', EXPIR_DT='', ST='Draft' where ID='5aba9c06-defa-438a-957d-9583850bb185' and VER_NBR=0
/
update KSLU_RSLT_OPT set OBJ_ID='40d635d9-ec8a-48e5-ad6d-fdf7cef4e82c', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-02-18 13:26:07.052', RT_DESCR_ID='984ca92f-5305-45b7-95a8-e0f74a259035', EFF_DT='', EXPIR_DT='', RES_COMP_ID='kuali.resultComponent.grade.letter', RES_USAGE_ID='', ST='Draft' where ID='20a0e9d9-b533-47a4-9419-04ccc22b9609' and VER_NBR=0
/
update KSLU_RSLT_OPT set OBJ_ID='64b174d5-43bc-4abc-ac2f-491ff3beccbc', VER_NBR=1, UPDATEID='admin', UPDATETIME=TIMESTAMP '2013-02-18 13:26:07.052', RT_DESCR_ID='bcacd775-faa5-4912-95d3-9936d7f53d0b', EFF_DT='', EXPIR_DT='', RES_COMP_ID='kuali.resultComponent.grade.audit', RES_USAGE_ID='', ST='Draft' where ID='71ddff84-f25e-40c0-8a33-0825e6d0cc61' and VER_NBR=0
/
