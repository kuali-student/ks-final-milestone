-- KSENROLL-10565

------ Insert KSEN_STATE_CHG to create EO state change
insert into KSEN_STATE_CHG (ID, STATE_CHG_TYPE, STATE_CHG_STATE, OBJ_ID, FROM_STATE_ID, TO_STATE_ID, EFF_DT, EXPIR_DT, VER_NBR, CREATETIME, CREATEID, UPDATETIME, UPDATEID) values ('kuali.statechange.examoffering.offered.canceled', 'kuali.state.change.type', 'kuali.state.change.state.active', null, 'kuali.lui.exam.offering.state.offered', 'kuali.lui.exam.offering.state.canceled', null, null, 0, TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 'SYSTEMLOADER' )
/

------ Insert  KSEN_STATE_PROPAGT
-- cancel AO
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.approved.canceled.2.offered.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.offered.canceled', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.offered.canceled.2.offered.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.offered.canceled', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.draft.canceled.2.suspended.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.suspended.canceled', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.approved.canceled.2.suspended.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.suspended.canceled', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.ao2eo.offered.canceled.2.suspended.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.suspended.canceled', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
-- cancel CO
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.planned.canceled.2.draft.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.draft.canceled', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.planned.canceled.2.offered.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.offered.canceled', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.offered.canceled.2.offered.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.offered.canceled', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.draft.canceled.2.suspended.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.suspended.canceled', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.planned.canceled.2.suspended.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.suspended.canceled', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.offered.canceled.2.suspended.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.suspended.canceled', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.suspended.canceled.2.suspended.canceled', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.suspended.canceled', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
-- suspend CO
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.draft.suspended.2.draft.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.draft.suspended', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.planned.suspended.2.draft.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.draft.suspended', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.offered.suspended.2.draft.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.draft.suspended', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.planned.suspended.2.offered.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.offered.suspended', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.offered.suspended.2.offered.suspended', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.offered.suspended', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
-- reinstate CO
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.canceled.draft.2.canceled.draft', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.canceled.draft', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.suspended.draft.2.suspended.draft', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.suspended.draft', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.suspended.planned.2.suspended.draft', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.suspended.draft', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/
insert into KSEN_STATE_PROPAGT (CREATEID, CREATETIME, OBJ_ID, ID, STATE_PROPAGT_TYPE, STATE_PROPAGT_STATE, TARGET_STATE_CHG_ID, UPDATEID, UPDATETIME, VER_NBR) values ('SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), null, 'kuali.statepropagation.co2eo.suspended.offered.2.suspended.draft', 'kuali.state.propagation.type', 'kuali.state.propagation.state', 'kuali.statechange.examoffering.suspended.draft', 'SYSTEMLOADER', TO_DATE ('11/01/2013', 'MM/DD/YYYY'), 0 )
/

------ insert KSEN_STATE_CHG_PROPAGT
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.approved.canceled', 'kuali.statepropagation.ao2eo.approved.canceled.2.suspended.canceled' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.activityoffering.offered.canceled', 'kuali.statepropagation.ao2eo.offered.canceled.2.suspended.canceled' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.planned.canceled', 'kuali.statepropagation.co2eo.planned.canceled.2.draft.canceled' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.planned.canceled', 'kuali.statepropagation.co2eo.planned.canceled.2.offered.canceled' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.offered.canceled', 'kuali.statepropagation.co2eo.offered.canceled.2.offered.canceled' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.draft.canceled', 'kuali.statepropagation.co2eo.draft.canceled.2.suspended.canceled' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.planned.canceled', 'kuali.statepropagation.co2eo.planned.canceled.2.suspended.canceled' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.offered.canceled', 'kuali.statepropagation.co2eo.offered.canceled.2.suspended.canceled' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.suspended.canceled', 'kuali.statepropagation.co2eo.suspended.canceled.2.suspended.canceled' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.draft.suspended', 'kuali.statepropagation.co2eo.draft.suspended.2.draft.suspended' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.planned.suspended', 'kuali.statepropagation.co2eo.planned.suspended.2.draft.suspended' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.offered.suspended', 'kuali.statepropagation.co2eo.offered.suspended.2.draft.suspended' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.planned.suspended', 'kuali.statepropagation.co2eo.planned.suspended.2.offered.suspended' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.offered.suspended', 'kuali.statepropagation.co2eo.offered.suspended.2.offered.suspended' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.canceled.draft', 'kuali.statepropagation.co2eo.canceled.draft.2.canceled.draft' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.suspended.draft', 'kuali.statepropagation.co2eo.suspended.draft.2.suspended.draft' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.suspended.planned', 'kuali.statepropagation.co2eo.suspended.planned.2.suspended.draft' )
/
insert into KSEN_STATE_CHG_PROPAGT (STATE_CHG_ID, STATE_PROPAGT_ID) values ('kuali.statechange.courseoffering.suspended.offered', 'kuali.statepropagation.co2eo.suspended.offered.2.suspended.draft' )
/

