--SOC related

-- SOC State Change
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.soc.draft.open', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.soc.state.draft', 'kuali.soc.state.open', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.soc.open.locked', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.soc.state.open', 'kuali.soc.state.locked', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.soc.locked.finaledits', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.soc.state.locked', 'kuali.soc.state.finaledits', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.soc.finaledits.publishing', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.soc.state.finaledits', 'kuali.soc.state.publishing', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.soc.publishing.published', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.soc.state.publishing', 'kuali.soc.state.published', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.soc.publishing.finaledits', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.soc.state.publishing', 'kuali.soc.state.finaledits', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/

-- SOC Scheduling State Change
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.soc.locked.sched-inprogress', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.soc.state.locked', 'kuali.soc.scheduling.state.inprogress', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.soc.locked.sched-completed', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.soc.state.locked', 'kuali.soc.scheduling.state.completed', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.soc.scheduling.notstarted.inprogress', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.soc.scheduling.state.notstarted', 'kuali.soc.scheduling.state.inprogress', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.soc.scheduling.inprogress.completed', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.soc.scheduling.state.inprogress', 'kuali.soc.scheduling.state.completed', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/

-- State Constraints
INSERT INTO KSEN_STATE_CNSTRNT (ID,OBJ_ID,STATE_CNSTRNT_TYPE,STATE_CNSTRNT_STATE,STATE_CNSTRNT_OPERATOR,AGENDA_ID,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.stateconstraint.activityoffering.approved.offered',null,'kuali.state.constraint.type.precondition','kuali.state.constraint.state.active','EXISTS',null,1,TIMESTAMP '1970-01-01 00:00:00','SYSTEMLOADER',TIMESTAMP '1970-01-01 00:00:00','SYSTEMLOADER')
/

-- State Constraint Related Objects
INSERT INTO KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID,REL_OBJ_STATE_ID) VALUES ('kuali.stateconstraint.activityoffering.approved.offered','kuali.lui.activity.offering.scheduling.state.exempt')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID,REL_OBJ_STATE_ID) VALUES ('kuali.stateconstraint.activityoffering.approved.offered','kuali.lui.activity.offering.scheduling.state.scheduled')
/

-- Linking State Constraints with State Change
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID) VALUES ('kuali.statechange.activityoffering.approved.offered','kuali.stateconstraint.activityoffering.approved.offered')
/

-- State Propagation
insert into KSEN_STATE_PROPAGT (ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, OBJ_ID, TARGET_STATE_CHG_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statepropagation.soctoao.published.offered', 'kuali.state.propagation.type','kuali.state.propagation.state.active', null, 'kuali.statechange.activityoffering.approved.offered', 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/

-- Linking State Propagation with State Change
insert into KSEN_STATE_CHG_PROPAGT(STATE_CHG_ID, STATE_PROPAGT_ID)values('kuali.statechange.soc.publishing.published', 'kuali.statepropagation.soctoao.published.offered')
/