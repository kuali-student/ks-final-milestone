-- Insert KSEN_STATE_CHG
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.examoffering.canceled.draft', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.exam.offering.state.canceled', 'kuali.lui.exam.offering.state.draft', null, null, 0, TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER', TO_DATE ('8/12/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER' )
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.examoffering.draft.canceled', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.exam.offering.state.draft', 'kuali.lui.exam.offering.state.canceled', null, null, 0, TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER', TO_DATE ('8/12/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER' )
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.examoffering.suspended.canceled', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.exam.offering.state.suspended', 'kuali.lui.exam.offering.state.canceled', null, null, 0, TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER', TO_DATE ('8/12/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER' )
/

-- Insert  KSEN_STATE_PROPAGT
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.offered.canceled.2.draft.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.draft.canceled', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.draft.canceled.2.draft.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.draft.canceled', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.approved.canceled.2.draft.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.draft.canceled', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.suspended.canceled.2.draft.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.draft.canceled', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.suspended.canceled.2.suspended.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.suspended.canceled', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.offered.canceled.2.draft.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.draft.canceled', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.draft.canceled.2.draft.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.draft.canceled', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/


-- KSEN_STATE_CHG_PROPAGT
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.offered.canceled', 'kuali.statepropagation.ao2eo.offered.canceled.2.draft.canceled' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.draft.canceled', 'kuali.statepropagation.ao2eo.draft.canceled.2.draft.canceled' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.approved.canceled', 'kuali.statepropagation.ao2eo.approved.canceled.2.draft.canceled' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.suspended.canceled', 'kuali.statepropagation.ao2eo.suspended.canceled.2.suspended.canceled' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.offered.canceled', 'kuali.statepropagation.co2eo.offered.canceled.2.draft.canceled' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.draft.canceled', 'kuali.statepropagation.co2eo.draft.canceled.2.draft.canceled' )
/
