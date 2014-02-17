-- KSENROLL-9257 add constraints for state Foo->offered/pending
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.registrationgroup.tostate.offered.all', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'ALL', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.registrationgroup.tostate.pending.exists', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'EXISTS', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_CNSTRNT (AGENDA_ID, CREATEID, CREATETIME, OBJ_ID, ID, STATE_CNSTRNT_TYPE, STATE_CNSTRNT_STATE, STATE_CNSTRNT_OPERATOR, UPDATEID, UPDATETIME, VER_NBR) values (null, 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), null, 'kuali.stateconstraint.registrationgroup.tostate.pending.none', 'kuali.state.constraint.type.precondition', 'kuali.state.constraint.state.active', 'NONE', 'SYSTEMLOADER', TO_DATE ('8/11/2013', 'MM/DD/YYYY'), 0 )
/

insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.registrationgroup.tostate.offered.all', 'kuali.lui.activity.offering.state.offered' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.registrationgroup.tostate.pending.exists', 'kuali.lui.activity.offering.state.approved' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.registrationgroup.tostate.pending.exists', 'kuali.lui.activity.offering.state.draft' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.registrationgroup.tostate.pending.none', 'kuali.lui.activity.offering.state.suspended' )
/
insert into KSEN_STATE_CNSTRNT_ROS (STATE_CNSTRNT_ID, REL_OBJ_STATE_ID) values ('kuali.stateconstraint.registrationgroup.tostate.pending.none', 'kuali.lui.activity.offering.state.canceled' )
/

insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.registrationgroup.suspended.offered', 'kuali.stateconstraint.registrationgroup.tostate.offered.all' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.registrationgroup.suspended.pending', 'kuali.stateconstraint.registrationgroup.tostate.pending.exists' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.registrationgroup.suspended.pending', 'kuali.stateconstraint.registrationgroup.tostate.pending.none' )
/