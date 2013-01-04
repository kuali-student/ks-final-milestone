-- State Change
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.atp.draft.official', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.atp.state.Draft', 'kuali.atp.state.Official', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.milestone.draft.official', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.milestone.state.Draft', 'kuali.milestone.state.Official', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/

-- Cal State Propagation to Milestone
insert into KSEN_STATE_PROPAGT (ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, OBJ_ID, TARGET_STATE_CHG_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statepropagation.atpToMilestone.draft.official', 'kuali.state.propagation.type','kuali.state.propagation.state.active', null, 'kuali.statechange.milestone.draft.official', 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/

-- Associate Cal state propagation with the state change
insert into KSEN_STATE_CHG_PROPAGT(STATE_CHG_ID, STATE_PROPAGT_ID)values('kuali.statechange.atp.draft.official', 'kuali.statepropagation.atpToMilestone.draft.official')
/