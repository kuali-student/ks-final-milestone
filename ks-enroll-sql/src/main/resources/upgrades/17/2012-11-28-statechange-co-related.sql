insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.courseoffering.draft.planned', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.course.offering.state.draft', 'kuali.lui.course.offering.state.planned', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.courseoffering.planned.draft', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.course.offering.state.planned', 'kuali.lui.course.offering.state.draft', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.courseoffering.planned.offered', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.course.offering.state.planned', 'kuali.lui.course.offering.state.offered', null, null, 1, TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER', TIMESTAMP '1970-01-01 00:00:00', 'SYSTEMLOADER')
/
INSERT INTO KSEN_STATE_CNSTRNT (ID,OBJ_ID,STATE_CNSTRNT_TYPE,STATE_CNSTRNT_STATE,STATE_CNSTRNT_OPERATOR,AGENDA_ID,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.stateconstraint.courseoffering.draft.planned',null,'kuali.state.constraint.type.precondition','kuali.state.constraint.state.active','EXISTS',null,1,TIMESTAMP '1970-01-01 00:00:00','SYSTEMLOADER',TIMESTAMP '1970-01-01 00:00:00','SYSTEMLOADER')
/
INSERT INTO KSEN_STATE_CNSTRNT (ID,OBJ_ID,STATE_CNSTRNT_TYPE,STATE_CNSTRNT_STATE,STATE_CNSTRNT_OPERATOR,AGENDA_ID,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.stateconstraint.courseoffering.planned.draft',null,'kuali.state.constraint.type.precondition','kuali.state.constraint.state.active','EXISTS',null,1,TIMESTAMP '1970-01-01 00:00:00','SYSTEMLOADER',TIMESTAMP '1970-01-01 00:00:00','SYSTEMLOADER')
/
INSERT INTO KSEN_STATE_CNSTRNT (ID,OBJ_ID,STATE_CNSTRNT_TYPE,STATE_CNSTRNT_STATE,STATE_CNSTRNT_OPERATOR,AGENDA_ID,VER_NBR,CREATETIME,CREATEID,UPDATETIME,UPDATEID) VALUES ('kuali.stateconstraint.courseoffering.planned.offered',null,'kuali.state.constraint.type.precondition','kuali.state.constraint.state.active','EXISTS',null,1,TIMESTAMP '1970-01-01 00:00:00','SYSTEMLOADER',TIMESTAMP '1970-01-01 00:00:00','SYSTEMLOADER')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID,REL_OBJ_STATE_ID) VALUES ('kuali.stateconstraint.courseoffering.draft.planned','kuali.lui.format.offering.state.planned')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID,REL_OBJ_STATE_ID) VALUES ('kuali.stateconstraint.courseoffering.planned.draft','kuali.lui.format.offering.state.draft')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID,REL_OBJ_STATE_ID) VALUES ('kuali.stateconstraint.courseoffering.planned.offered','kuali.lui.format.offering.state.offered')
/
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID) VALUES ('kuali.statechange.courseoffering.draft.planned','kuali.stateconstraint.courseoffering.draft.planned')
/
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID) VALUES ('kuali.statechange.courseoffering.planned.draft','kuali.stateconstraint.courseoffering.planned.draft')
/
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID) VALUES ('kuali.statechange.courseoffering.planned.offered','kuali.stateconstraint.courseoffering.planned.offered')
/
