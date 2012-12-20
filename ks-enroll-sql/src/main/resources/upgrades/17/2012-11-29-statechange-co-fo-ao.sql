-- State Change (With this all the constraints and propagation will be linked at KSEN_STATE_CHG_CNSTRNT and KSEN_STATE_CHG_PROPAGT). For simple state change, it's not needed to have either constraints
-- or propagations

-- For CO
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.courseoffering.draft.planned', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.course.offering.state.draft', 'kuali.lui.course.offering.state.planned', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.courseoffering.planned.draft', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.course.offering.state.planned', 'kuali.lui.course.offering.state.draft', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.courseoffering.planned.offered', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.course.offering.state.planned', 'kuali.lui.course.offering.state.offered', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
-- FOR FO
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.formatoffering.draft.planned', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.format.offering.state.draft', 'kuali.lui.format.offering.state.planned', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.formatoffering.planned.draft', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.format.offering.state.planned', 'kuali.lui.format.offering.state.draft', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.formatoffering.planned.offered', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.format.offering.state.planned', 'kuali.lui.format.offering.state.offered', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
-- FOR AO
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.activityoffering.draft.approved', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.activity.offering.state.draft', 'kuali.lui.activity.offering.state.approved', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.activityoffering.approved.draft', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.activity.offering.state.approved', 'kuali.lui.activity.offering.state.draft', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.activityoffering.approved.offered', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.activity.offering.state.approved', 'kuali.lui.activity.offering.state.offered', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.activityoffering.draft.offered', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.activity.offering.state.draft', 'kuali.lui.activity.offering.state.offered', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
-- FOR Reg-Group
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.registrationgroup.pending.offered', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.registration.group.state.pending', 'kuali.lui.registration.group.state.offered', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.registrationgroup.pending.invalid', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.registration.group.state.pending', 'kuali.lui.registration.group.state.invalid', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.registrationgroup.offered.invalid', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.registration.group.state.offered', 'kuali.lui.registration.group.state.invalid', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.registrationgroup.pending.canceled', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.registration.group.state.pending', 'kuali.lui.registration.group.state.canceled', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.registrationgroup.offered.canceled', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.registration.group.state.offered', 'kuali.lui.registration.group.state.canceled', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.registrationgroup.pending.suspended', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.registration.group.state.pending', 'kuali.lui.registration.group.state.suspended', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.registrationgroup.offered.suspended', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.registration.group.state.offered', 'kuali.lui.registration.group.state.suspended', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
------------------------------------------------------------------------------------------------------------------------
-- State Propagation

-- AO to FO
insert into KSEN_STATE_PROPAGT (ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, OBJ_ID, TARGET_STATE_CHG_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statepropagation.aotofo.draft.approved', 'kuali.state.propagation.type','kuali.state.propagation.state.active', null, 'kuali.statechange.formatoffering.draft.planned', 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_PROPAGT (ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, OBJ_ID, TARGET_STATE_CHG_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statepropagation.aotofo.approved.draft', 'kuali.state.propagation.type','kuali.state.propagation.state.active', null, 'kuali.statechange.formatoffering.planned.draft', 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_PROPAGT (ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, OBJ_ID, TARGET_STATE_CHG_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statepropagation.aotofo.approved.offered', 'kuali.state.propagation.type','kuali.state.propagation.state.active', null, 'kuali.statechange.formatoffering.planned.offered', 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/

-- FO to CO
insert into KSEN_STATE_PROPAGT (ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, OBJ_ID, TARGET_STATE_CHG_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statepropagation.fotoco.draft.planned', 'kuali.state.propagation.type','kuali.state.propagation.state.active', null, 'kuali.statechange.courseoffering.draft.planned', 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_PROPAGT (ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, OBJ_ID, TARGET_STATE_CHG_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statepropagation.fotoco.planned.draft', 'kuali.state.propagation.type','kuali.state.propagation.state.active', null, 'kuali.statechange.courseoffering.planned.draft', 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_PROPAGT (ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, OBJ_ID, TARGET_STATE_CHG_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statepropagation.fotoco.planned.offered', 'kuali.state.propagation.type','kuali.state.propagation.state.active', null, 'kuali.statechange.courseoffering.planned.offered', 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/

-- AO to Reg-Group
insert into KSEN_STATE_PROPAGT (ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, OBJ_ID, TARGET_STATE_CHG_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statepropagation.aotorg.approved.offered', 'kuali.state.propagation.type','kuali.state.propagation.state.active', null, 'kuali.statechange.registrationgroup.pending.offered', 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/

------------------------------------------------------------------------------------------------------------------------
-- Linking State Propagation with State Change

-- AOs
insert into KSEN_STATE_CHG_PROPAGT(STATE_CHG_ID, STATE_PROPAGT_ID)values('kuali.statechange.activityoffering.draft.approved', 'kuali.statepropagation.aotofo.draft.approved')
/
insert into KSEN_STATE_CHG_PROPAGT(STATE_CHG_ID, STATE_PROPAGT_ID)values('kuali.statechange.activityoffering.approved.draft', 'kuali.statepropagation.aotofo.approved.draft')
/
insert into KSEN_STATE_CHG_PROPAGT(STATE_CHG_ID, STATE_PROPAGT_ID)values('kuali.statechange.activityoffering.approved.offered', 'kuali.statepropagation.aotofo.approved.offered')
/

-- FOs
insert into KSEN_STATE_CHG_PROPAGT(STATE_CHG_ID, STATE_PROPAGT_ID)values('kuali.statechange.formatoffering.draft.planned', 'kuali.statepropagation.fotoco.draft.planned')
/
insert into KSEN_STATE_CHG_PROPAGT(STATE_CHG_ID, STATE_PROPAGT_ID)values('kuali.statechange.formatoffering.planned.draft', 'kuali.statepropagation.fotoco.planned.draft')
/
insert into KSEN_STATE_CHG_PROPAGT(STATE_CHG_ID, STATE_PROPAGT_ID)values('kuali.statechange.formatoffering.planned.offered', 'kuali.statepropagation.fotoco.planned.offered')
/

-- RGs
insert into KSEN_STATE_CHG_PROPAGT(STATE_CHG_ID, STATE_PROPAGT_ID)values('kuali.statechange.activityoffering.approved.offered', 'kuali.statepropagation.aotorg.approved.offered')
/

------------------------------------------------------------------------------------------------------------------------
-- State Constraints

-- COs
INSERT INTO KSEN_STATE_CNSTRNT (ID,OBJ_ID,STATE_CNSTRNT_TYPE,STATE_CNSTRNT_STATE,STATE_CNSTRNT_OPERATOR,AGENDA_ID,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.stateconstraint.courseoffering.draft.planned',null,'kuali.state.constraint.type.precondition','kuali.state.constraint.state.active','EXISTS',null,1,TIMESTAMP '1970-01-01 00:00:00','SYSTEMLOADER',TIMESTAMP '1970-01-01 00:00:00','SYSTEMLOADER')
/
INSERT INTO KSEN_STATE_CNSTRNT (ID,OBJ_ID,STATE_CNSTRNT_TYPE,STATE_CNSTRNT_STATE,STATE_CNSTRNT_OPERATOR,AGENDA_ID,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.stateconstraint.courseoffering.planned.draft',null,'kuali.state.constraint.type.precondition','kuali.state.constraint.state.active','NONE',null,1,TIMESTAMP '1970-01-01 00:00:00','SYSTEMLOADER',TIMESTAMP '1970-01-01 00:00:00','SYSTEMLOADER')
/
INSERT INTO KSEN_STATE_CNSTRNT (ID,OBJ_ID,STATE_CNSTRNT_TYPE,STATE_CNSTRNT_STATE,STATE_CNSTRNT_OPERATOR,AGENDA_ID,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.stateconstraint.courseoffering.planned.offered',null,'kuali.state.constraint.type.precondition','kuali.state.constraint.state.active','EXISTS',null,1,TIMESTAMP '1970-01-01 00:00:00','SYSTEMLOADER',TIMESTAMP '1970-01-01 00:00:00','SYSTEMLOADER')
/

--FOs
insert into KSEN_STATE_CNSTRNT (ID, OBJ_ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, AGENDA_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.stateconstraint.formatoffering.draft.planned', null, 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'EXISTS', null, 1, to_date('2012-11-26', 'YYYY-MM-DD'), 'SYSTEMLOADER', to_date('2012-11-26', 'YYYY-MM-DD'), 'SYSTEMLOADER')
/
insert into KSEN_STATE_CNSTRNT (ID, OBJ_ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, AGENDA_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.stateconstraint.formatoffering.planned.draft', null, 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'NONE', null, 1, to_date('2012-11-26', 'YYYY-MM-DD'), 'SYSTEMLOADER', to_date('2012-11-26', 'YYYY-MM-DD'), 'SYSTEMLOADER')
/
insert into KSEN_STATE_CNSTRNT (ID, OBJ_ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, AGENDA_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.stateconstraint.formatoffering.planned.offered', null, 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'EXISTS', null, 1, to_date('2012-11-26', 'YYYY-MM-DD'), 'SYSTEMLOADER', to_date('2012-11-26', 'YYYY-MM-DD'), 'SYSTEMLOADER')
/

--Reg-Groups
insert into KSEN_STATE_CNSTRNT (ID, OBJ_ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, AGENDA_ID, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.stateconstraint.registrationgroup.pending.offered', null, 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'ALL', null, 1, to_date('2012-11-26', 'YYYY-MM-DD'), 'SYSTEMLOADER', to_date('2012-11-26', 'YYYY-MM-DD'), 'SYSTEMLOADER')
/

------------------------------------------------------------------------------------------------------------------------
-- State Constraint Related Objects

-- COs
INSERT INTO KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID,REL_OBJ_STATE_ID) VALUES ('kuali.stateconstraint.courseoffering.draft.planned','kuali.lui.format.offering.state.planned')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID,REL_OBJ_STATE_ID) VALUES ('kuali.stateconstraint.courseoffering.planned.draft','kuali.lui.format.offering.state.planned')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID,REL_OBJ_STATE_ID) VALUES ('kuali.stateconstraint.courseoffering.planned.offered','kuali.lui.format.offering.state.offered')
/

--FOs
insert into KSEN_STATE_CNSTRNT_ROS(STATE_CNSTRNT_ID, REL_OBJ_STATE_ID)  values ('kuali.stateconstraint.formatoffering.draft.planned', 'kuali.lui.activity.offering.state.approved')
/
insert into KSEN_STATE_CNSTRNT_ROS(STATE_CNSTRNT_ID, REL_OBJ_STATE_ID)  values ('kuali.stateconstraint.formatoffering.planned.draft', 'kuali.lui.activity.offering.state.approved')
/
insert into KSEN_STATE_CNSTRNT_ROS(STATE_CNSTRNT_ID, REL_OBJ_STATE_ID)  values ('kuali.stateconstraint.formatoffering.planned.offered', 'kuali.lui.activity.offering.state.offered')
/

--Reg-Groups
insert into KSEN_STATE_CNSTRNT_ROS(STATE_CNSTRNT_ID, REL_OBJ_STATE_ID)  values ('kuali.stateconstraint.registrationgroup.pending.offered', 'kuali.lui.activity.offering.state.offered')
/

------------------------------------------------------------------------------------------------------------------------
-- Linking State Constraints with State Change

-- COs
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID) VALUES ('kuali.statechange.courseoffering.draft.planned','kuali.stateconstraint.courseoffering.draft.planned')
/
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID) VALUES ('kuali.statechange.courseoffering.planned.draft','kuali.stateconstraint.courseoffering.planned.draft')
/
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID) VALUES ('kuali.statechange.courseoffering.planned.offered','kuali.stateconstraint.courseoffering.planned.offered')
/

-- FOs
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID) VALUES ('kuali.statechange.formatoffering.draft.planned','kuali.stateconstraint.formatoffering.draft.planned')
/
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID) VALUES ('kuali.statechange.formatoffering.planned.draft','kuali.stateconstraint.formatoffering.planned.draft')
/
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID) VALUES ('kuali.statechange.formatoffering.planned.offered','kuali.stateconstraint.formatoffering.planned.offered')
/

-- Reg-Groups
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID) VALUES ('kuali.statechange.registrationgroup.pending.offered','kuali.stateconstraint.registrationgroup.pending.offered')
/
