-- KSENROLL-9406
-- Insert KSEN_STATE_CHG
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.examoffering.suspended.draft', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.exam.offering.state.suspended', 'kuali.lui.exam.offering.state.draft', null, null, 0, TO_DATE ('10/17/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER', TO_DATE ('10/17/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER' )
/

-- Insert  KSEN_STATE_PROPAGT
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('10/17/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.canceled.draft.2.canceled.draft', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.canceled.draft', 'SYSTEMLOADER', TO_DATE ('10/17/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('10/17/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.suspended.draft.2.suspended.draft', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.suspended.draft', 'SYSTEMLOADER', TO_DATE ('10/17/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('10/17/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.suspended.approved.2.suspended.draft', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.suspended.draft', 'SYSTEMLOADER', TO_DATE ('10/17/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('10/17/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.suspended.offered.2.suspended.draft', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.suspended.draft', 'SYSTEMLOADER', TO_DATE ('10/17/2013', 'MM/DD/YYYY'), 0 )
/

-- KSEN_STATE_CHG_PROPAGT
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.canceled.draft', 'kuali.statepropagation.ao2eo.canceled.draft.2.canceled.draft' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.suspended.draft', 'kuali.statepropagation.ao2eo.suspended.draft.2.suspended.draft' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.suspended.approved', 'kuali.statepropagation.ao2eo.suspended.approved.2.suspended.draft' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.suspended.offered', 'kuali.statepropagation.ao2eo.suspended.offered.2.suspended.draft' )
/