-- KSENROLL-12603 updated Fall2012 ENGL101 sections 1010 and 1012 to have max seats of 3, 3 people in WL and 3 in the course
-- users B.EF, B.EHSANF, B.EILEENB are all registered for the two sections, and B.EILEENL, E.DMITRYL, B.ELEANORB are waitlisted
-- IN ('B.EF', 'B.EHSANF', 'B.EILEENB', 'B.EILEENL', 'E.DMITRYL', 'B.ELEANORB')

update KSEN_LUI set MAX_SEATS=3, MIN_SEATS=3 where ID in ('c110ae7c-adc7-44a3-853c-5d18e4c56e99','da60e729-e825-49f6-a6ca-512c2c5f1333')
/


insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:18', null, TIMESTAMP '2014-04-22 11:15:18', null, null, 'ad857620-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.activity.offering', 'da60e729-e825-49f6-a6ca-512c2c5f1333', 'ad857622-ca31-11e3-9c1a-0800200c9a66', 'ad857632-ca31-11e3-9c1a-0800200c9a66', 'B.EF', 'admin', TIMESTAMP '2014-04-22 11:15:18', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:19', '3.0', TIMESTAMP '2014-04-22 11:15:19', null, 'kuali.resultComponent.grade.letter', 'ad857621-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.course.offering', 'b8a9ef75-4530-40dc-b211-a088217a783c', 'ad857622-ca31-11e3-9c1a-0800200c9a66', 'ad857633-ca31-11e3-9c1a-0800200c9a66', 'B.EF', 'admin', TIMESTAMP '2014-04-22 11:15:19', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:20', '3.0', TIMESTAMP '2014-04-22 11:15:20', null, 'kuali.resultComponent.grade.letter', 'ad857622-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.registration.group', 'd76cbd0c-16c6-4254-ab91-b93fa358302a', 'ad857622-ca31-11e3-9c1a-0800200c9a66', 'ad857634-ca31-11e3-9c1a-0800200c9a66', 'B.EF', 'admin', TIMESTAMP '2014-04-22 11:15:20', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:21', null, TIMESTAMP '2014-04-22 11:15:21', null, null, 'ad857623-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.activity.offering', 'c110ae7c-adc7-44a3-853c-5d18e4c56e99', 'ad857625-ca31-11e3-9c1a-0800200c9a66', 'ad857635-ca31-11e3-9c1a-0800200c9a66', 'B.EF', 'admin', TIMESTAMP '2014-04-22 11:15:21', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:22', '3.0', TIMESTAMP '2014-04-22 11:15:22', null, 'kuali.resultComponent.grade.letter', 'ad857624-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.course.offering', 'b8a9ef75-4530-40dc-b211-a088217a783c', 'ad857625-ca31-11e3-9c1a-0800200c9a66', 'ad857636-ca31-11e3-9c1a-0800200c9a66', 'B.EF', 'admin', TIMESTAMP '2014-04-22 11:15:22', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:23', '3.0', TIMESTAMP '2014-04-22 11:15:23', null, 'kuali.resultComponent.grade.letter', 'ad857625-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.registration.group', '7ccfc40c-3ec3-417a-b439-70aeb4d3d997', 'ad857625-ca31-11e3-9c1a-0800200c9a66', 'ad857637-ca31-11e3-9c1a-0800200c9a66', 'B.EF', 'admin', TIMESTAMP '2014-04-22 11:15:23', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:24', null, TIMESTAMP '2014-04-22 11:15:24', null, null, 'ad857626-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.activity.offering', 'da60e729-e825-49f6-a6ca-512c2c5f1333', 'ad857628-ca31-11e3-9c1a-0800200c9a66', 'ad857638-ca31-11e3-9c1a-0800200c9a66', 'B.EHSANF', 'admin', TIMESTAMP '2014-04-22 11:15:45', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:25', '3.0', TIMESTAMP '2014-04-22 11:15:25', null, 'kuali.resultComponent.grade.letter', 'ad857627-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.course.offering', 'b8a9ef75-4530-40dc-b211-a088217a783c', 'ad857628-ca31-11e3-9c1a-0800200c9a66', 'ad857639-ca31-11e3-9c1a-0800200c9a66', 'B.EHSANF', 'admin', TIMESTAMP '2014-04-22 11:15:25', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:26', '3.0', TIMESTAMP '2014-04-22 11:15:26', null, 'kuali.resultComponent.grade.letter', 'ad857628-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.registration.group', 'd76cbd0c-16c6-4254-ab91-b93fa358302a', 'ad857628-ca31-11e3-9c1a-0800200c9a66', 'ad85763a-ca31-11e3-9c1a-0800200c9a66', 'B.EHSANF', 'admin', TIMESTAMP '2014-04-22 11:15:26', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:27', null, TIMESTAMP '2014-04-22 11:15:27', null, null, 'ad857629-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.activity.offering', 'c110ae7c-adc7-44a3-853c-5d18e4c56e99', 'ad85762b-ca31-11e3-9c1a-0800200c9a66', 'ad85763b-ca31-11e3-9c1a-0800200c9a66', 'B.EHSANF', 'admin', TIMESTAMP '2014-04-22 11:15:27', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:28', '3.0', TIMESTAMP '2014-04-22 11:15:28', null, 'kuali.resultComponent.grade.letter', 'ad85762a-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.course.offering', 'b8a9ef75-4530-40dc-b211-a088217a783c', 'ad85762b-ca31-11e3-9c1a-0800200c9a66', 'ad85763c-ca31-11e3-9c1a-0800200c9a66', 'B.EHSANF', 'admin', TIMESTAMP '2014-04-22 11:15:28', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:29', '3.0', TIMESTAMP '2014-04-22 11:15:29', null, 'kuali.resultComponent.grade.letter', 'ad85762b-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.registration.group', '7ccfc40c-3ec3-417a-b439-70aeb4d3d997', 'ad85762b-ca31-11e3-9c1a-0800200c9a66', 'ad859d30-ca31-11e3-9c1a-0800200c9a66', 'B.EHSANF', 'admin', TIMESTAMP '2014-04-22 11:15:29', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:30', null, TIMESTAMP '2014-04-22 11:15:30', null, null, 'ad85762c-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.activity.offering', 'da60e729-e825-49f6-a6ca-512c2c5f1333', 'ad85762e-ca31-11e3-9c1a-0800200c9a66', 'ad859d31-ca31-11e3-9c1a-0800200c9a66', 'B.EILEENB', 'admin', TIMESTAMP '2014-04-22 11:15:30', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:31', '3.0', TIMESTAMP '2014-04-22 11:15:31', null, 'kuali.resultComponent.grade.letter', 'ad85762d-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.course.offering', 'b8a9ef75-4530-40dc-b211-a088217a783c', 'ad85762e-ca31-11e3-9c1a-0800200c9a66', 'ad859d32-ca31-11e3-9c1a-0800200c9a66', 'B.EILEENB', 'admin', TIMESTAMP '2014-04-22 11:15:31', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:32', '3.0', TIMESTAMP '2014-04-22 11:15:32', null, 'kuali.resultComponent.grade.letter', 'ad85762e-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.registration.group', 'd76cbd0c-16c6-4254-ab91-b93fa358302a', 'ad85762e-ca31-11e3-9c1a-0800200c9a66', 'ad859d33-ca31-11e3-9c1a-0800200c9a66', 'B.EILEENB', 'admin', TIMESTAMP '2014-04-22 11:15:32', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:33', null, TIMESTAMP '2014-04-22 11:15:33', null, null, 'ad85762f-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.activity.offering', 'c110ae7c-adc7-44a3-853c-5d18e4c56e99', 'ad857631-ca31-11e3-9c1a-0800200c9a66', 'ad859d34-ca31-11e3-9c1a-0800200c9a66', 'B.EILEENB', 'admin', TIMESTAMP '2014-04-22 11:15:33', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:34', '3.0', TIMESTAMP '2014-04-22 11:15:34', null, 'kuali.resultComponent.grade.letter', 'ad857630-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.course.offering', 'b8a9ef75-4530-40dc-b211-a088217a783c', 'ad857631-ca31-11e3-9c1a-0800200c9a66', 'ad859d35-ca31-11e3-9c1a-0800200c9a66', 'B.EILEENB', 'admin', TIMESTAMP '2014-04-22 11:15:34', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:35', '3.0', TIMESTAMP '2014-04-22 11:15:35', null, 'kuali.resultComponent.grade.letter', 'ad857631-ca31-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.registrant.registration.group', '7ccfc40c-3ec3-417a-b439-70aeb4d3d997', 'ad857631-ca31-11e3-9c1a-0800200c9a66', 'ad859d36-ca31-11e3-9c1a-0800200c9a66', 'B.EILEENB', 'admin', TIMESTAMP '2014-04-22 11:15:35', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:36', null, TIMESTAMP '2014-04-22 11:15:36', null, null, '83553870-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.activity.offering', 'c110ae7c-adc7-44a3-853c-5d18e4c56e99', '83553872-ca33-11e3-9c1a-0800200c9a66', '83555f8e-ca33-11e3-9c1a-0800200c9a66', 'B.EILEENL', 'admin', TIMESTAMP '2014-04-22 11:15:36', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:37', '3.0', TIMESTAMP '2014-04-22 11:15:37', null, 'kuali.resultComponent.grade.letter', '83553871-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.course.offering', 'b8a9ef75-4530-40dc-b211-a088217a783c', '83553872-ca33-11e3-9c1a-0800200c9a66', '83555f8f-ca33-11e3-9c1a-0800200c9a66', 'B.EILEENL', 'admin', TIMESTAMP '2014-04-22 11:15:37', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:38', '3.0', TIMESTAMP '2014-04-22 11:15:38', null, 'kuali.resultComponent.grade.letter', '83553872-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.registration.group', '7ccfc40c-3ec3-417a-b439-70aeb4d3d997', '83553872-ca33-11e3-9c1a-0800200c9a66', '83555f90-ca33-11e3-9c1a-0800200c9a66', 'B.EILEENL', 'admin', TIMESTAMP '2014-04-22 11:15:38', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:39', null, TIMESTAMP '2014-04-22 11:15:39', null, null, '83553873-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.activity.offering', 'da60e729-e825-49f6-a6ca-512c2c5f1333', '83555f81-ca33-11e3-9c1a-0800200c9a66', '83555f91-ca33-11e3-9c1a-0800200c9a66', 'B.EILEENL', 'admin', TIMESTAMP '2014-04-22 11:15:39', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:40', '3.0', TIMESTAMP '2014-04-22 11:15:40', null, 'kuali.resultComponent.grade.letter', '83555f80-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.course.offering', 'b8a9ef75-4530-40dc-b211-a088217a783c', '83555f81-ca33-11e3-9c1a-0800200c9a66', '83555f92-ca33-11e3-9c1a-0800200c9a66', 'B.EILEENL', 'admin', TIMESTAMP '2014-04-22 11:15:40', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:41', '3.0', TIMESTAMP '2014-04-22 11:15:41', null, 'kuali.resultComponent.grade.letter', '83555f81-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.registration.group', 'd76cbd0c-16c6-4254-ab91-b93fa358302a', '83555f81-ca33-11e3-9c1a-0800200c9a66', '83555f93-ca33-11e3-9c1a-0800200c9a66', 'B.EILEENL', 'admin', TIMESTAMP '2014-04-22 11:15:41', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:42', null, TIMESTAMP '2014-04-22 11:15:42', null, null, '83555f82-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.activity.offering', 'c110ae7c-adc7-44a3-853c-5d18e4c56e99', '83555f84-ca33-11e3-9c1a-0800200c9a66', '83555f94-ca33-11e3-9c1a-0800200c9a66', 'E.DMITRYL', 'admin', TIMESTAMP '2014-04-22 11:15:42', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:43', '3.0', TIMESTAMP '2014-04-22 11:15:43', null, 'kuali.resultComponent.grade.letter', '83555f83-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.course.offering', 'b8a9ef75-4530-40dc-b211-a088217a783c', '83555f84-ca33-11e3-9c1a-0800200c9a66', '83555f95-ca33-11e3-9c1a-0800200c9a66', 'E.DMITRYL', 'admin', TIMESTAMP '2014-04-22 11:15:43', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:44', '3.0', TIMESTAMP '2014-04-22 11:15:44', null, 'kuali.resultComponent.grade.letter', '83555f84-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.registration.group', '7ccfc40c-3ec3-417a-b439-70aeb4d3d997', '83555f84-ca33-11e3-9c1a-0800200c9a66', '83555f96-ca33-11e3-9c1a-0800200c9a66', 'E.DMITRYL', 'admin', TIMESTAMP '2014-04-22 11:15:44', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:45', null, TIMESTAMP '2014-04-22 11:15:45', null, null, '83555f85-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.activity.offering', 'da60e729-e825-49f6-a6ca-512c2c5f1333', '83555f87-ca33-11e3-9c1a-0800200c9a66', '83555f97-ca33-11e3-9c1a-0800200c9a66', 'E.DMITRYL', 'admin', TIMESTAMP '2014-04-22 11:15:45', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:46', '3.0', TIMESTAMP '2014-04-22 11:15:46', null, 'kuali.resultComponent.grade.letter', '83555f86-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.course.offering', 'b8a9ef75-4530-40dc-b211-a088217a783c', '83555f87-ca33-11e3-9c1a-0800200c9a66', '83555f98-ca33-11e3-9c1a-0800200c9a66', 'E.DMITRYL', 'admin', TIMESTAMP '2014-04-22 11:15:46', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:47', '3.0', TIMESTAMP '2014-04-22 11:15:47', null, 'kuali.resultComponent.grade.letter', '83555f87-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.registration.group', 'd76cbd0c-16c6-4254-ab91-b93fa358302a', '83555f87-ca33-11e3-9c1a-0800200c9a66', '83555f99-ca33-11e3-9c1a-0800200c9a66', 'E.DMITRYL', 'admin', TIMESTAMP '2014-04-22 11:15:47', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:48', null, TIMESTAMP '2014-04-22 11:15:48', null, null, '83555f88-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.activity.offering', 'c110ae7c-adc7-44a3-853c-5d18e4c56e99', '83555f8a-ca33-11e3-9c1a-0800200c9a66', '83555f9a-ca33-11e3-9c1a-0800200c9a66', 'B.ELEANORB', 'admin', TIMESTAMP '2014-04-22 11:15:48', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:49', '3.0', TIMESTAMP '2014-04-22 11:15:49', null, 'kuali.resultComponent.grade.letter', '83555f89-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.course.offering', 'b8a9ef75-4530-40dc-b211-a088217a783c', '83555f8a-ca33-11e3-9c1a-0800200c9a66', '83555f9b-ca33-11e3-9c1a-0800200c9a66', 'B.ELEANORB', 'admin', TIMESTAMP '2014-04-22 11:15:49', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:50', '3.0', TIMESTAMP '2014-04-22 11:15:50', null, 'kuali.resultComponent.grade.letter', '83555f8a-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.registration.group', '7ccfc40c-3ec3-417a-b439-70aeb4d3d997', '83555f8a-ca33-11e3-9c1a-0800200c9a66', '83555f9c-ca33-11e3-9c1a-0800200c9a66', 'B.ELEANORB', 'admin', TIMESTAMP '2014-04-22 11:15:50', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:51', null, TIMESTAMP '2014-04-22 11:15:51', null, null, '83555f8b-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.activity.offering', 'da60e729-e825-49f6-a6ca-512c2c5f1333', '83555f8d-ca33-11e3-9c1a-0800200c9a66', '83555f9d-ca33-11e3-9c1a-0800200c9a66', 'B.ELEANORB', 'admin', TIMESTAMP '2014-04-22 11:15:51', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:52', '3.0', TIMESTAMP '2014-04-22 11:15:52', null, 'kuali.resultComponent.grade.letter', '83555f8c-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.course.offering', 'b8a9ef75-4530-40dc-b211-a088217a783c', '83555f8d-ca33-11e3-9c1a-0800200c9a66', '83555f9e-ca33-11e3-9c1a-0800200c9a66', 'B.ELEANORB', 'admin', TIMESTAMP '2014-04-22 11:15:52', 0)
/
insert into KSEN_LPR (ATP_ID, COMMIT_PERCT, CREATEID, CREATETIME, CREDITS, EFF_DT, EXPIR_DT, GRADING_OPT_ID, ID, LPR_STATE, LPR_TYPE, LUI_ID, MASTER_LPR_ID, OBJ_ID, PERS_ID, UPDATEID, UPDATETIME, VER_NBR) values ('kuali.atp.2012Fall', null, 'admin', TIMESTAMP '2014-04-22 11:15:53', '3.0', TIMESTAMP '2014-04-22 11:15:53', null, 'kuali.resultComponent.grade.letter', '83555f8d-ca33-11e3-9c1a-0800200c9a66', 'kuali.lpr.state.active', 'kuali.lpr.type.waitlist.registration.group', 'd76cbd0c-16c6-4254-ab91-b93fa358302a', '83555f8d-ca33-11e3-9c1a-0800200c9a66', '83555f9f-ca33-11e3-9c1a-0800200c9a66', 'B.ELEANORB', 'admin', TIMESTAMP '2014-04-22 11:15:53', 0)
/


insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857620-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857620-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857621-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857621-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857622-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857622-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857623-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857623-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857624-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857624-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857625-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857625-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857626-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857626-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857627-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857627-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857628-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857628-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857629-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857629-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad85762a-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad85762a-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad85762b-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad85762b-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad85762c-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad85762c-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad85762d-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad85762d-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad85762e-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad85762e-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad85762f-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad85762f-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857630-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857630-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857631-ca31-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('ad857631-ca31-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83553870-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83553870-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83553871-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83553871-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83553872-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83553872-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83553873-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83553873-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f80-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f80-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f81-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f81-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f82-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f82-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f83-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f83-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f84-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f84-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f85-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f85-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f86-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f86-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f87-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f87-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f88-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f88-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f89-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f89-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f8a-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f8a-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f8b-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f8b-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f8c-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f8c-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f8d-ca33-11e3-9c1a-0800200c9a66', 'kuali.result.value.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('83555f8d-ca33-11e3-9c1a-0800200c9a66', 'kuali.resultComponent.grade.letter')
/
