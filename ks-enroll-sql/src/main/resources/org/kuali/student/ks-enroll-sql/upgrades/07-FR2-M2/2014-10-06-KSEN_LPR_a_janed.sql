--  KSENROLL-15188

-- User A.JANED (KS-8152) registered for 201208/CHEM699/1001 (picked 2 credits + audit)
-- reg group
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS,
                      CROSSLIST, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE,
                      LPR_TYPE, ID) values ('1261d8ae-9993-420a-98dc-30efbec9b3c3', 0, 'A.JANED',
                                            TIMESTAMP '2014-10-06 13:58:25.771', 'A.JANED',
                                            TIMESTAMP '2014-10-06 13:58:25.771', 'kuali.atp.2012Fall', '', '2.0',
                                            'CHEM699',
                                            TIMESTAMP '2014-10-06 00:00:00.000', '',
                                            'kuali.resultComponent.grade.audit',
                                            '0691bb96-4254-425f-88cc-c223bd659d44',
                                            '7df48928-a968-4198-8632-a634b3edd775', 'KS-8152', 'kuali.lpr.state.active',
                                            'kuali.lpr.type.registrant.registration.group',
                                            '7df48928-a968-4198-8632-a634b3edd775')
/
-- course offering
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS,
                      CROSSLIST, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE,
                      LPR_TYPE, ID) values ('0956851c-5ba9-4e18-a552-c24e610a6ac5', 0, 'A.JANED',
                                            TIMESTAMP '2014-10-06 13:58:25.771', 'A.JANED',
                                            TIMESTAMP '2014-10-06 13:58:25.771', 'kuali.atp.2012Fall', '', '2.0',
                                            'CHEM699',
                                            TIMESTAMP '2014-10-06 00:00:00.000', '',
                                            'kuali.resultComponent.grade.audit',
                                            'ce30b18d-5003-430c-9b8d-07d320785f4d',
                                            '7df48928-a968-4198-8632-a634b3edd775', 'KS-8152', 'kuali.lpr.state.active',
                                            'kuali.lpr.type.registrant.course.offering',
                                            'b2f09c81-3542-47e2-a5d4-f804e478f1b3')
/
-- activity offering
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS,
                      CROSSLIST, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE,
                      LPR_TYPE, ID) values ('7764cc03-6b60-4594-b849-6340d9336d44', 0, 'A.JANED',
                                            TIMESTAMP '2014-10-06 13:58:25.771', 'A.JANED',
                                            TIMESTAMP '2014-10-06 13:58:25.771', 'kuali.atp.2012Fall', '', '',
                                            'CHEM699',
                                            TIMESTAMP '2014-10-06 00:00:00.000', '',
                                            '',
                                            '47fd1950-ac29-4a8b-99e2-1d2a329df74d',
                                            '7df48928-a968-4198-8632-a634b3edd775', 'KS-8152', 'kuali.lpr.state.active',
                                            'kuali.lpr.type.registrant.activity.offering',
                                            '70414514-7eb9-4062-80b7-9e9bee26015a')
/
-- result value groups
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('7df48928-a968-4198-8632-a634b3edd775',
                                                                        'kuali.resultComponent.grade.audit')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('7df48928-a968-4198-8632-a634b3edd775',
                                                                        'kuali.result.value.credit.degree.2.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('b2f09c81-3542-47e2-a5d4-f804e478f1b3',
                                                                        'kuali.resultComponent.grade.audit')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('b2f09c81-3542-47e2-a5d4-f804e478f1b3',
                                                                        'kuali.result.value.credit.degree.2.0')
/

-- User A.JANED (KS-8152) waitlisted for 201208/HIST266/1001 (picked letter)
-- waitlist reg group
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS,
                      CROSSLIST, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE,
                      LPR_TYPE, ID) values ('1261d8ae-9993-420a-98dc-30efbec9b3c3', 0, 'A.JANED',
                                            TIMESTAMP '2014-10-06 14:47:28.390', 'A.JANED',
                                            TIMESTAMP '2014-10-06 14:47:28.390', 'kuali.atp.2012Fall', '', '3.0',
                                            'HIST266',
                                            TIMESTAMP '2014-10-06 00:00:00.000', '',
                                            'kuali.resultComponent.grade.letter',
                                            'ba0ce58c-c546-4039-a85a-ae75b1a7575b',
                                            'f15b0919-e126-45fc-89ef-e657ca320f3b', 'KS-8152', 'kuali.lpr.state.active',
                                            'kuali.lpr.type.waitlist.registration.group',
                                            'f15b0919-e126-45fc-89ef-e657ca320f3b')
/
-- waitlist course offering
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS,
                      CROSSLIST, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE,
                      LPR_TYPE, ID) values ('0956851c-5ba9-4e18-a552-c24e610a6ac5', 0, 'A.JANED',
                                            TIMESTAMP '2014-10-06 14:47:28.390', 'A.JANED',
                                            TIMESTAMP '2014-10-06 14:47:28.390', 'kuali.atp.2012Fall', '', '3.0',
                                            'HIST266',
                                            TIMESTAMP '2014-10-06 00:00:00.000', '',
                                            'kuali.resultComponent.grade.letter',
                                            '2ee27af5-2150-42a5-8fd9-52e6305d51c5',
                                            'f15b0919-e126-45fc-89ef-e657ca320f3b', 'KS-8152', 'kuali.lpr.state.active',
                                            'kuali.lpr.type.waitlist.course.offering',
                                            'e5936027-f867-4688-8c37-6f07a3889824')
/
-- waitlist activity offering
insert into KSEN_LPR (OBJ_ID, VER_NBR, CREATEID, CREATETIME, UPDATEID, UPDATETIME, ATP_ID, COMMIT_PERCT, CREDITS,
                      CROSSLIST, EFF_DT, EXPIR_DT, GRADING_OPT_ID, LUI_ID, MASTER_LPR_ID, PERS_ID, LPR_STATE,
                      LPR_TYPE, ID) values ('7764cc03-6b60-4594-b849-6340d9336d44', 0, 'A.JANED',
                                            TIMESTAMP '2014-10-06 14:47:28.390', 'A.JANED',
                                            TIMESTAMP '2014-10-06 14:47:28.390', 'kuali.atp.2012Fall', '', '',
                                            'HIST266',
                                            TIMESTAMP '2014-10-06 00:00:00.000', '',
                                            '',
                                            '5732c691-ef9c-4b30-bbda-9fa5decaa41f',
                                            'f15b0919-e126-45fc-89ef-e657ca320f3b', 'KS-8152', 'kuali.lpr.state.active',
                                            'kuali.lpr.type.waitlist.activity.offering',
                                            '6b30b7b2-17ba-46c9-bc06-d142ab7602e2')
/
-- result value groups
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('e5936027-f867-4688-8c37-6f07a3889824',
                                                                        'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('e5936027-f867-4688-8c37-6f07a3889824',
                                                                        'kuali.creditType.credit.degree.3.0')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('f15b0919-e126-45fc-89ef-e657ca320f3b',
                                                                        'kuali.resultComponent.grade.letter')
/
insert into KSEN_LPR_RESULT_VAL_GRP (LPR_ID, RESULT_VAL_GRP_ID) values ('f15b0919-e126-45fc-89ef-e657ca320f3b',
                                                                        'kuali.creditType.credit.degree.3.0')
/