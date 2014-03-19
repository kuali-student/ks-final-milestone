-- KSENROLL-12248
-- This is to configure the state propagation from AO (draft->offered) to FO (planned->offered)
INSERT INTO KSEN_STATE_PROPAGT (CREATEID,CREATETIME,ID,STATE_PROPAGT_STATE,STATE_PROPAGT_TYPE,TARGET_STATE_CHG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'kuali.statepropagation.ao2fo.draft.offered.2.draft.offered','kuali.state.propagation.state.active','kuali.state.propagation.type','kuali.statechange.formatoffering.draft.offered','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE_PROPAGT (CREATEID,CREATETIME,ID,STATE_PROPAGT_STATE,STATE_PROPAGT_TYPE,TARGET_STATE_CHG_ID,UPDATEID,UPDATETIME,VER_NBR)
  VALUES ('SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),'kuali.statepropagation.ao2fo.draft.offered.2.planned.offered','kuali.state.propagation.state.active','kuali.state.propagation.type','kuali.statechange.formatoffering.planned.offered','SYSTEMLOADER',TO_DATE( '19700101000000', 'YYYYMMDDHH24MISS' ),1)
/
INSERT INTO KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID,STATE_PROPAGT_ID)
  VALUES ('kuali.statechange.activityoffering.draft.offered','kuali.statepropagation.ao2fo.draft.offered.2.draft.offered')
/
INSERT INTO KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID,STATE_PROPAGT_ID)
  VALUES ('kuali.statechange.activityoffering.draft.offered','kuali.statepropagation.ao2fo.draft.offered.2.planned.offered')
/
DELETE FROM KSEN_STATE_PROPAGT WHERE ID='kuali.statepropagation.aotofo.draft.offered'
/
DELETE FROM KSEN_STATE_CHG_PROPAGT WHERE STATE_CHG_ID='kuali.statechange.activityoffering.draft.offered' AND STATE_PROPAGT_ID='kuali.statepropagation.aotofo.draft.offered'
/

