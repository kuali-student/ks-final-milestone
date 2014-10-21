-- KSENROLL-11782
-- AO state change Approved->Offered
-- Insert a new state change constraint on AO state change (Approved->Offered)
INSERT INTO KSEN_STATE_CNSTRNT (CREATEID,CREATETIME,ID,STATE_CNSTRNT_OPERATOR,STATE_CNSTRNT_STATE,STATE_CNSTRNT_TYPE,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'kuali.stateconstraint.activityoffering.approved.offered.soc.lifecycle','ALL','kuali.state.constraint.state.active','kuali.state.constraint.type.precondition','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
-- Decouple the current state change constraint on AO state change (Approved->Offered)
DELETE FROM KSEN_STATE_CHG_CNSTRNT where STATE_CHG_ID='kuali.statechange.activityoffering.approved.offered' and state_cnstrnt_id='kuali.stateconstraint.activityoffering.approved.offered.soc'
/
-- Configure the related object states for the state constraint on AO state change (Approved->Offered)
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.locked','kuali.stateconstraint.activityoffering.approved.offered.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.finaledits','kuali.stateconstraint.activityoffering.approved.offered.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.publishing','kuali.stateconstraint.activityoffering.approved.offered.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.published','kuali.stateconstraint.activityoffering.approved.offered.soc.lifecycle')
/
-- Apply the new state change constraint on AO state change (Approved->Offered)
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.statechange.activityoffering.approved.offered','kuali.stateconstraint.activityoffering.approved.offered.soc.lifecycle')
/


-- AO state change Draft->Offered
-- Insert a new state change constraint on AO state change (Draft->Offered)
INSERT INTO KSEN_STATE_CNSTRNT (CREATEID,CREATETIME,ID,STATE_CNSTRNT_OPERATOR,STATE_CNSTRNT_STATE,STATE_CNSTRNT_TYPE,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'kuali.stateconstraint.activityoffering.draft.offered.soc.lifecycle','ALL','kuali.state.constraint.state.active','kuali.state.constraint.type.precondition','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
-- Decouple the current state change constraint on AO state change (Draft->Offered)
DELETE FROM KSEN_STATE_CHG_CNSTRNT where STATE_CHG_ID='kuali.statechange.activityoffering.draft.offered' and state_cnstrnt_id='kuali.stateconstraint.activityoffering.draft.offered.soc'
/
-- Configure the related object states for the state constraint on AO state change (Draft->Offered)
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.locked','kuali.stateconstraint.activityoffering.draft.offered.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.finaledits','kuali.stateconstraint.activityoffering.draft.offered.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.publishing','kuali.stateconstraint.activityoffering.draft.offered.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.published','kuali.stateconstraint.activityoffering.draft.offered.soc.lifecycle')
/
-- Apply the new state change constraint on AO state change (Draft->Offered)
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.statechange.activityoffering.draft.offered','kuali.stateconstraint.activityoffering.draft.offered.soc.lifecycle')
/

-- AO state change Offered->Draft
-- We won't have the AO state change Offered->Draft. But since this state change has been configured in DB and also in case it may be allowed in the future, instead of removing this state change from DB,
-- we configure state change constraints to make this state change not to happen in all SOC states
-- Insert a new state change constraint on AO state change (Offered->Draft)
INSERT INTO KSEN_STATE_CNSTRNT (CREATEID,CREATETIME,ID,STATE_CNSTRNT_OPERATOR,STATE_CNSTRNT_STATE,STATE_CNSTRNT_TYPE,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'kuali.stateconstraint.activityoffering.offered.draft.soc.lifecycle','NONE','kuali.state.constraint.state.active','kuali.state.constraint.type.precondition','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
-- Configure the related object states for the state constraint on AO state change (Offered->Draft)
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.draft','kuali.stateconstraint.activityoffering.offered.draft.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.open','kuali.stateconstraint.activityoffering.offered.draft.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.locked','kuali.stateconstraint.activityoffering.offered.draft.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.finaledits','kuali.stateconstraint.activityoffering.offered.draft.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.publishing','kuali.stateconstraint.activityoffering.offered.draft.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.published','kuali.stateconstraint.activityoffering.offered.draft.soc.lifecycle')
/
-- Apply the new state change constraint on AO state change (Offered->Draft)
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.statechange.activityoffering.offered.draft','kuali.stateconstraint.activityoffering.offered.draft.soc.lifecycle')
/

-- AO state change Draft->Approved
-- Insert a new state change constraint on AO state change (Draft->Approved)
INSERT INTO KSEN_STATE_CNSTRNT (CREATEID,CREATETIME,ID,STATE_CNSTRNT_OPERATOR,STATE_CNSTRNT_STATE,STATE_CNSTRNT_TYPE,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'kuali.stateconstraint.activityoffering.draft.approved.soc.lifecycle','ALL','kuali.state.constraint.state.active','kuali.state.constraint.type.precondition','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
-- Configure the related object states for the state constraint on AO state change (Draft->Approved)
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.draft','kuali.stateconstraint.activityoffering.draft.approved.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.open','kuali.stateconstraint.activityoffering.draft.approved.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.locked','kuali.stateconstraint.activityoffering.draft.approved.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.finaledits','kuali.stateconstraint.activityoffering.draft.approved.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.publishing','kuali.stateconstraint.activityoffering.draft.approved.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.published','kuali.stateconstraint.activityoffering.draft.approved.soc.lifecycle')
/
-- Apply the new state change constraint on AO state change (Draft->Approved)
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.statechange.activityoffering.draft.approved','kuali.stateconstraint.activityoffering.draft.approved.soc.lifecycle')
/



-- AO state change Approved->Draft
-- Insert a new state change constraint on AO state change (Approved->Draft)
INSERT INTO KSEN_STATE_CNSTRNT (CREATEID,CREATETIME,ID,STATE_CNSTRNT_OPERATOR,STATE_CNSTRNT_STATE,STATE_CNSTRNT_TYPE,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'kuali.stateconstraint.activityoffering.approved.draft.soc.lifecycle','EXISTS','kuali.state.constraint.state.active','kuali.state.constraint.type.precondition','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
-- Configure the related object states for the state constraint on AO state change (Approved->Draft)
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.draft','kuali.stateconstraint.activityoffering.approved.draft.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.open','kuali.stateconstraint.activityoffering.approved.draft.soc.lifecycle')
/
INSERT INTO KSEN_STATE_CNSTRNT_ROS (REL_OBJ_STATE_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.soc.state.locked','kuali.stateconstraint.activityoffering.approved.draft.soc.lifecycle')
/
-- Apply the new state change constraint on AO state change (Approved->Draft)
INSERT INTO KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID,STATE_CNSTRNT_ID)
  VALUES ('kuali.statechange.activityoffering.approved.draft','kuali.stateconstraint.activityoffering.approved.draft.soc.lifecycle')
/


-- KSENROLL-11778
-- Remove the state propogation from SOC (Publishing->Published) to AO (Approved->Offered)
DELETE FROM KSEN_STATE_CHG_PROPAGT WHERE STATE_CHG_ID='kuali.statechange.soc.publishing.published' and STATE_PROPAGT_ID='kuali.statepropagation.soctoao.published.offered'
/
