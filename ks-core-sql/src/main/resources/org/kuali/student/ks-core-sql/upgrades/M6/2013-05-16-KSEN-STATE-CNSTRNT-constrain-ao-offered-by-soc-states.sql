
-- Add two new rows to the KSEN_STATE_CNSTRNT table
INSERT INTO KSEN_STATE_CNSTRNT (CREATEID,CREATETIME,ID,STATE_CNSTRNT_OPERATOR,STATE_CNSTRNT_STATE,STATE_CNSTRNT_TYPE,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'kuali.stateconstraint.activityoffering.approved.offered.soc','EXISTS','kuali.state.constraint.state.active','kuali.state.constraint.type.precondition','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE_CNSTRNT (CREATEID,CREATETIME,ID,STATE_CNSTRNT_OPERATOR,STATE_CNSTRNT_STATE,STATE_CNSTRNT_TYPE,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'kuali.stateconstraint.activityoffering.draft.offered.soc','EXISTS','kuali.state.constraint.state.active','kuali.state.constraint.type.precondition','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/

-- Add two new rows to the KSEN_STATE_CHG_CNSTRNT table
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.statechange.activityoffering.approved.offered','kuali.stateconstraint.activityoffering.approved.offered.soc')
/
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.statechange.activityoffering.draft.offered','kuali.stateconstraint.activityoffering.draft.offered.soc')
/

-- Add the two required states to the constraints in the KSEN_STATE_CNSTRNT_ROS table
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.published','kuali.stateconstraint.activityoffering.approved.offered.soc')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.publishing','kuali.stateconstraint.activityoffering.approved.offered.soc')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.published','kuali.stateconstraint.activityoffering.draft.offered.soc')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.publishing','kuali.stateconstraint.activityoffering.draft.offered.soc')
/
