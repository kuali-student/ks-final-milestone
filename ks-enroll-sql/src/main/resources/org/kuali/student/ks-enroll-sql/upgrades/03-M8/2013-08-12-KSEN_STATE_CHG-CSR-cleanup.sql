insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.formatoffering.offered.planned', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.format.offering.state.offered', 'kuali.lui.format.offering.state.planned', null, null, 0, TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER', TO_DATE ('8/12/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER' )
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.courseoffering.offered.planned', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.course.offering.state.offered', 'kuali.lui.course.offering.state.planned', null, null, 0, TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER', TO_DATE ('8/12/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER' )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.formatoffering.tostate.draft.1', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'NONE', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.formatoffering.tostate.draft.2', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'EXISTS', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.formatoffering.tostate.offered', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'EXISTS', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.formatoffering.tostate.planned.2', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'NONE', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.formatoffering.tostate.planned.1', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'EXISTS', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.courseoffering.tostate.draft.1', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'NONE', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.courseoffering.tostate.draft.2', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'EXISTS', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.courseoffering.tostate.offered', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'EXISTS', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.courseoffering.tostate.planned.1', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'EXISTS', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.courseoffering.tostate.planned.2', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'NONE', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.formatoffering.tostate.draft.1', 'kuali.lui.activity.offering.state.approved' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.formatoffering.tostate.draft.1', 'kuali.lui.activity.offering.state.offered' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.courseoffering.tostate.draft.1', 'kuali.lui.format.offering.state.planned' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.courseoffering.tostate.draft.1', 'kuali.lui.format.offering.state.offered' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.formatoffering.tostate.draft.2', 'kuali.lui.activity.offering.state.draft' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.courseoffering.tostate.draft.2', 'kuali.lui.format.offering.state.draft' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.formatoffering.tostate.offered', 'kuali.lui.activity.offering.state.offered' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.courseoffering.tostate.offered', 'kuali.lui.format.offering.state.offered' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.formatoffering.tostate.planned.1', 'kuali.lui.activity.offering.state.approved' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.formatoffering.tostate.planned.2', 'kuali.lui.activity.offering.state.offered' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.courseoffering.tostate.planned.1', 'kuali.lui.format.offering.state.planned' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.courseoffering.tostate.planned.2', 'kuali.lui.format.offering.state.offered' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.formatoffering.offered.planned', 'kuali.stateconstraint.formatoffering.tostate.planned.1' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.formatoffering.offered.planned', 'kuali.stateconstraint.formatoffering.tostate.planned.2' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.courseoffering.offered.planned', 'kuali.stateconstraint.courseoffering.tostate.planned.1' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.courseoffering.offered.planned', 'kuali.stateconstraint.courseoffering.tostate.planned.2' )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.fo2co.offered.planned.2.offered.planned', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.courseoffering.offered.planned', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.fo2co.offered.draft.2.offered.planned', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.courseoffering.offered.planned', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.fo2co.draft.offered.2.planned.offered', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.courseoffering.planned.offered', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.formatoffering.offered.draft', 'kuali.statepropagation.fo2co.offered.draft.2.offered.planned' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.formatoffering.offered.planned', 'kuali.statepropagation.fo2co.offered.planned.2.offered.planned' )
/
