-- StateChanges for FO and CO states offered to draft --
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID)
values ('kuali.statechange.formatoffering.offered.draft', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.format.offering.state.offered', 'kuali.lui.format.offering.state.draft', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID)
values ('kuali.statechange.courseoffering.offered.draft', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.course.offering.state.offered', 'kuali.lui.course.offering.state.draft', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
-- The constraint for CO and FO states offered to draft are the same as planned to draft so link the state changes to the existing constraints
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.courseoffering.offered.draft','kuali.stateconstraint.courseoffering.planned.draft')
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.formatoffering.offered.draft','kuali.stateconstraint.formatoffering.planned.draft')
/
-- Propagate CO state offered to draft when an FO is state changed from state offered to draft
insert into KSEN_STATE_PROPAGT (ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, OBJ_ID, TARGET_STATE_CHG_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID)
values ('kuali.statepropagation.fotoco.offered.draft', 'kuali.state.propagation.type','kuali.state.propagation.state.active', null, 'kuali.statechange.courseoffering.offered.draft', 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG_PROPAGT(STATE_CHG_ID, STATE_PROPAGT_ID)values('kuali.statechange.formatoffering.offered.draft', 'kuali.statepropagation.fotoco.offered.draft')
/
