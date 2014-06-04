-- KSENROLL-12437
DELETE FROM KSEN_STATE_CNSTRNT_ROS WHERE STATE_CNSTRNT_ID='kuali.stateconstraint.registrationgroup.offered.pending' and REL_OBJ_STATE_ID='kuali.lui.activity.offering.state.offered'
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID) VALUES ('kuali.lui.activity.offering.state.approved','kuali.stateconstraint.registrationgroup.offered.pending')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID) VALUES ('kuali.lui.activity.offering.state.draft','kuali.stateconstraint.registrationgroup.offered.pending')
/
