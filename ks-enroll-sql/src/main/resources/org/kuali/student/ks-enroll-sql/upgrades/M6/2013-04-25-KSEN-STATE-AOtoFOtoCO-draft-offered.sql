-- This triggers the FO to change state from draft to offered when the AO goes from draft to offered. 
-- KSEN_STATE_CHG.sql
INSERT INTO KSEN_STATE_CHG (CREATEID,CREATETIME,FROM_STATE_ID,ID,STATE_CHG_STATE,STATE_CHG_TYPE,TO_STATE_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'kuali.lui.format.offering.state.draft','kuali.statechange.formatoffering.draft.offered','kuali.state.change.state.active','kuali.state.change.type','kuali.lui.format.offering.state.offered','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/

-- KSEN-STATE_PROPAGT.sql
INSERT INTO KSEN_STATE_PROPAGT (CREATEID,CREATETIME,ID,STATE_PROPAGT_STATE,STATE_PROPAGT_TYPE,TARGET_STATE_CHG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'kuali.statepropagation.aotofo.draft.offered','kuali.state.propagation.state.active','kuali.state.propagation.type','kuali.statechange.formatoffering.draft.offered','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/

-- KSEN_STATE_CHG_PROPAGT.sql
INSERT INTO KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID,STATE_PROPAGT_ID)
  VALUES ('kuali.statechange.activityoffering.draft.offered','kuali.statepropagation.aotofo.draft.offered')
/


-- State Change caused by the FO going from draft to offered. This causes the CO to go from draft to offered.
-- KSEN_STATE_CHG.sql
INSERT INTO KSEN_STATE_CHG (CREATEID,CREATETIME,FROM_STATE_ID,ID,STATE_CHG_STATE,STATE_CHG_TYPE,TO_STATE_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'kuali.lui.course.offering.state.draft','kuali.statechange.courseoffering.draft.offered','kuali.state.change.state.active','kuali.state.change.type','kuali.lui.course.offering.state.offered','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/

-- KSEN-STATE_PROPAGT.sql
INSERT INTO KSEN_STATE_PROPAGT (CREATEID,CREATETIME,ID,STATE_PROPAGT_STATE,STATE_PROPAGT_TYPE,TARGET_STATE_CHG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'kuali.statepropagation.fotoco.draft.offered','kuali.state.propagation.state.active','kuali.state.propagation.type','kuali.statechange.courseoffering.draft.offered','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/

-- KSEN_STATE_CHG_PROPAGT.sql
INSERT INTO KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID,STATE_PROPAGT_ID)
  VALUES ('kuali.statechange.formatoffering.draft.offered','kuali.statepropagation.fotoco.draft.offered')
/