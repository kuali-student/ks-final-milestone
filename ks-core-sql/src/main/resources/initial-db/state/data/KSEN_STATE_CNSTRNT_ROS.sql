TRUNCATE TABLE KSEN_STATE_CNSTRNT_ROS DROP STORAGE
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.lui.activity.offering.scheduling.state.exempt','kuali.stateconstraint.activityoffering.approved.offered')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.lui.activity.offering.scheduling.state.scheduled','kuali.stateconstraint.activityoffering.approved.offered')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.lui.activity.offering.state.approved','kuali.stateconstraint.formatoffering.draft.planned')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.lui.activity.offering.state.approved','kuali.stateconstraint.formatoffering.planned.draft')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.lui.activity.offering.state.offered','kuali.stateconstraint.formatoffering.planned.offered')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.lui.activity.offering.state.offered','kuali.stateconstraint.registrationgroup.pending.offered')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.lui.format.offering.state.offered','kuali.stateconstraint.courseoffering.planned.offered')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.lui.format.offering.state.planned','kuali.stateconstraint.courseoffering.draft.planned')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.lui.format.offering.state.planned','kuali.stateconstraint.courseoffering.planned.draft')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.lui.activity.offering.state.offered','kuali.stateconstraint.registrationgroup.invalid.offered')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.lui.activity.offering.state.draft','kuali.stateconstraint.registrationgroup.invalid.pending')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.lui.activity.offering.state.approved','kuali.stateconstraint.registrationgroup.invalid.pending')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.lui.activity.offering.state.offered','kuali.stateconstraint.registrationgroup.offered.pending')
/