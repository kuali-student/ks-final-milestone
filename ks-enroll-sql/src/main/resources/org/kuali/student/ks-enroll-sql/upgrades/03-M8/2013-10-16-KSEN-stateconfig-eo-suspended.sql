-- KSENROLL-10193
-- Insert KSEN_STATE_CHG
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.examoffering.offered.suspended', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.exam.offering.state.offered', 'kuali.lui.exam.offering.state.suspended', null, null, 0, TO_DATE ('10/16/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER', TO_DATE ('10/16/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER' )
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.examoffering.draft.suspended', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.exam.offering.state.draft', 'kuali.lui.exam.offering.state.suspended', null, null, 0, TO_DATE ('10/16/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER', TO_DATE ('10/16/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER' )
/

-- Insert  KSEN_STATE_PROPAGT
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.draft.suspended.2.offered.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.offered.suspended', 'SYSTEMLOADER', TO_DATE ('10/16/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.approved.suspended.2.offered.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.offered.suspended', 'SYSTEMLOADER', TO_DATE ('10/16/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.offered.suspended.2.offered.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.offered.suspended', 'SYSTEMLOADER', TO_DATE ('10/16/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.draft.suspended.2.draft.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.draft.suspended', 'SYSTEMLOADER', TO_DATE ('10/16/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.approved.suspended.2.draft.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.draft.suspended', 'SYSTEMLOADER', TO_DATE ('10/16/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.offered.suspended.2.draft.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.draft.suspended', 'SYSTEMLOADER', TO_DATE ('10/16/2013', 'MM/DD/YYYY'), 0 )
/

-- KSEN_STATE_CHG_PROPAGT
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.draft.suspended', 'kuali.statepropagation.ao2eo.draft.suspended.2.offered.suspended' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.approved.suspended', 'kuali.statepropagation.ao2eo.approved.suspended.2.offered.suspended' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.offered.suspended', 'kuali.statepropagation.ao2eo.offered.suspended.2.offered.suspended' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.draft.suspended', 'kuali.statepropagation.ao2eo.draft.suspended.2.draft.suspended' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.approved.suspended', 'kuali.statepropagation.ao2eo.approved.suspended.2.draft.suspended' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.offered.suspended', 'kuali.statepropagation.ao2eo.offered.suspended.2.draft.suspended' )
/