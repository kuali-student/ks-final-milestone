insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.activityoffering.draft.suspended', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.activity.offering.state.draft', 'kuali.lui.activity.offering.state.suspended', null, null, 0, TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER', TO_DATE ('8/12/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER' )
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.activityoffering.approved.suspended', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.activity.offering.state.approved', 'kuali.lui.activity.offering.state.suspended', null, null, 0, TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER', TO_DATE ('8/12/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER' )
/
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.activityoffering.offered.suspended', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.activity.offering.state.offered', 'kuali.lui.activity.offering.state.suspended', null, null, 0, TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER', TO_DATE ('8/12/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER' )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.activityoffering.tostate.suspended.soc', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'NONE', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.formatoffering.tostate.suspended.1', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'ALL', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.formatoffering.tostate.suspended.2', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'EXISTS', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.courseoffering.tostate.suspended.1', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'ALL', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.courseoffering.tostate.suspended.2', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'EXISTS', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.registrationgroup.tostate.suspended.1', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'EXISTS', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.registrationgroup.tostate.suspended.2', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'NONE', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.activityoffering.tostate.suspended.soc', 'kuali.soc.state.draft' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.activityoffering.tostate.suspended.soc', 'kuali.soc.state.open' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.formatoffering.tostate.suspended.1', 'kuali.lui.activity.offering.state.suspended' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.formatoffering.tostate.suspended.1', 'kuali.lui.activity.offering.state.canceled' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.formatoffering.tostate.suspended.2', 'kuali.lui.activity.offering.state.suspended' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.courseoffering.tostate.suspended.1', 'kuali.lui.format.offering.state.suspended' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.courseoffering.tostate.suspended.1', 'kuali.lui.format.offering.state.canceled' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.courseoffering.tostate.suspended.2', 'kuali.lui.format.offering.state.suspended' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.registrationgroup.tostate.suspended.1', 'kuali.lui.activity.offering.state.suspended' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.registrationgroup.tostate.suspended.2', 'kuali.lui.activity.offering.state.canceled' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.activityoffering.draft.suspended', 'kuali.stateconstraint.activityoffering.tostate.suspended.soc' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.activityoffering.approved.suspended', 'kuali.stateconstraint.activityoffering.tostate.suspended.soc' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.activityoffering.offered.suspended', 'kuali.stateconstraint.activityoffering.tostate.suspended.soc' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.formatoffering.draft.suspended', 'kuali.stateconstraint.formatoffering.tostate.suspended.1' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.formatoffering.draft.suspended', 'kuali.stateconstraint.formatoffering.tostate.suspended.2' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.formatoffering.planned.suspended', 'kuali.stateconstraint.formatoffering.tostate.suspended.1' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.formatoffering.planned.suspended', 'kuali.stateconstraint.formatoffering.tostate.suspended.2' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.formatoffering.offered.suspended', 'kuali.stateconstraint.formatoffering.tostate.suspended.1' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.formatoffering.offered.suspended', 'kuali.stateconstraint.formatoffering.tostate.suspended.2' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.courseoffering.draft.suspended', 'kuali.stateconstraint.courseoffering.tostate.suspended.1' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.courseoffering.draft.suspended', 'kuali.stateconstraint.courseoffering.tostate.suspended.2' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.courseoffering.planned.suspended', 'kuali.stateconstraint.courseoffering.tostate.suspended.1' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.courseoffering.planned.suspended', 'kuali.stateconstraint.courseoffering.tostate.suspended.2' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.courseoffering.offered.suspended', 'kuali.stateconstraint.courseoffering.tostate.suspended.1' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.courseoffering.offered.suspended', 'kuali.stateconstraint.courseoffering.tostate.suspended.2' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.registrationgroup.pending.suspended', 'kuali.stateconstraint.registrationgroup.tostate.suspended.1' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.registrationgroup.pending.suspended', 'kuali.stateconstraint.registrationgroup.tostate.suspended.2' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.registrationgroup.offered.suspended', 'kuali.stateconstraint.registrationgroup.tostate.suspended.1' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.registrationgroup.offered.suspended', 'kuali.stateconstraint.registrationgroup.tostate.suspended.2' )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2fo.draft.suspended.2.draft.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.formatoffering.draft.suspended', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2fo.approved.suspended.2.planned.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.formatoffering.planned.suspended', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2fo.approved.suspended.2.planned.draft', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.formatoffering.planned.draft', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2fo.offered.suspended.2.offered.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.formatoffering.offered.suspended', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2fo.offered.suspended.2.offered.draft', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.formatoffering.offered.draft', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2fo.offered.suspended.2.offered.planned', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.formatoffering.offered.planned', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2rg.draft.suspended.2.pending.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.registrationgroup.pending.suspended', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2rg.approved.suspended.2.pending.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.registrationgroup.pending.suspended', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2rg.offered.suspended.2.offered.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.registrationgroup.offered.suspended', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2rg.offered.suspended.2.pending.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.registrationgroup.pending.suspended', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.draft.suspended', 'kuali.statepropagation.ao2fo.draft.suspended.2.draft.suspended' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.approved.suspended', 'kuali.statepropagation.ao2fo.approved.suspended.2.planned.suspended' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.approved.suspended', 'kuali.statepropagation.ao2fo.approved.suspended.2.planned.draft' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.offered.suspended', 'kuali.statepropagation.ao2fo.offered.suspended.2.offered.suspended' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.offered.suspended', 'kuali.statepropagation.ao2fo.offered.suspended.2.offered.draft' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.offered.suspended', 'kuali.statepropagation.ao2fo.offered.suspended.2.offered.planned' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.draft.suspended', 'kuali.statepropagation.ao2rg.draft.suspended.2.pending.suspended' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.approved.suspended', 'kuali.statepropagation.ao2rg.approved.suspended.2.pending.suspended' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.offered.suspended', 'kuali.statepropagation.ao2rg.offered.suspended.2.offered.suspended' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.offered.suspended', 'kuali.statepropagation.ao2rg.offered.suspended.2.pending.suspended' )
/
