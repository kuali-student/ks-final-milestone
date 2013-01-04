update KSEN_STATE_CNSTRNT_ROS set REL_OBJ_STATE_ID = 'kuali.lui.activity.offering.state.approved' where STATE_CNSTRNT_ID = 'kuali.stateconstraint.formatoffering.draft.planned'
/
update KSEN_STATE_CNSTRNT_ROS set REL_OBJ_STATE_ID = 'kuali.stateconstraint.formatoffering.planned.draft' where STATE_CNSTRNT_ID = 'kuali.lui.activity.offering.state.draft'
/