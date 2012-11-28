insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.activityoffering.draft.approved', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.activity.offering.state.draft', 'kuali.lui.activity.offering.state.approved', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.activityoffering.approved.draft', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.activity.offering.state.approved', 'kuali.lui.activity.offering.state.draft', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.formatoffering.draft.planned', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.format.offering.state.draft', 'kuali.lui.format.offering.state.planned', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.formatoffering.planned.draft', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.format.offering.state.planned', 'kuali.lui.format.offering.state.draft', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.courseoffering.draft.planned', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.course.offering.state.draft', 'kuali.lui.course.offering.state.planned', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.courseoffering.planned.draft', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.course.offering.state.planned', 'kuali.lui.course.offering.state.draft', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_PROPAGT (ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, OBJ_ID, TARGET_STATE_CHG_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statepropagation.aotofo.approved.draft', 'kuali.state.propagation.type','kuali.state.propagation.state.active', null, 'kuali.statechange.formatoffering.planned.draft', 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_PROPAGT (ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, OBJ_ID, TARGET_STATE_CHG_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statepropagation.aotofo.draft.submitted', 'kuali.state.propagation.type','kuali.state.propagation.state.active', null, 'kuali.statechange.formatoffering.draft.planned', 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_PROPAGT (ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, OBJ_ID, TARGET_STATE_CHG_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statepropagation.aotofo.draft.approved', 'kuali.state.propagation.type','kuali.state.propagation.state.active', null, 'kuali.statechange.formatoffering.draft.planned', 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_PROPAGT (ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, OBJ_ID, TARGET_STATE_CHG_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statepropagation.fotoco.planned.draft', 'kuali.state.propagation.type','kuali.state.propagation.state.active', null, 'kuali.statechange.courseoffering.planned.draft', 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_PROPAGT (ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, OBJ_ID, TARGET_STATE_CHG_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statepropagation.fotoco.draft.planned', 'kuali.state.propagation.type','kuali.state.propagation.state.active', null, 'kuali.statechange.courseoffering.draft.planned', 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG_PROPAGT(STATE_CHG_ID, STATE_PROPAGT_ID)values('kuali.statechange.activityoffering.draft.approved', 'kuali.statepropagation.aotofo.draft.approved')
/
insert into KSEN_STATE_CHG_PROPAGT(STATE_CHG_ID, STATE_PROPAGT_ID)values('kuali.statechange.activityoffering.approved.draft', 'kuali.statepropagation.aotofo.approved.draft')
/
insert into KSEN_STATE_CHG_PROPAGT(STATE_CHG_ID, STATE_PROPAGT_ID)values('kuali.statechange.formatoffering.draft.planned', 'kuali.statepropagation.fotoco.draft.planned')
/
insert into KSEN_STATE_CHG_PROPAGT(STATE_CHG_ID, STATE_PROPAGT_ID)values('kuali.statechange.formatoffering.planned.draft', 'kuali.statepropagation.fotoco.planned.draft')
/
