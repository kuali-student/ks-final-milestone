--   KSENROLL-9261 - the CO was getting changed from planned->draft incorrectly. Adding new constraints.
-- remove the old constraints. they're incorrect
delete from KSEN_STATE_CHG_CNSTRNT where STATE_CHG_ID = 'kuali.statechange.courseoffering.offered.draft' and STATE_CNSTRNT_ID = 'kuali.stateconstraint.courseoffering.planned.draft'
/
delete from KSEN_STATE_CHG_CNSTRNT where STATE_CHG_ID = 'kuali.statechange.courseoffering.planned.draft' and STATE_CNSTRNT_ID = 'kuali.stateconstraint.courseoffering.planned.draft'
/

-- add new + correct constraints.
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.courseoffering.offered.draft', 'kuali.stateconstraint.courseoffering.tostate.draft.1' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.courseoffering.offered.draft', 'kuali.stateconstraint.courseoffering.tostate.draft.2' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.courseoffering.planned.draft', 'kuali.stateconstraint.courseoffering.tostate.draft.1' )
/
insert into KSEN_STATE_CHG_CNSTRNT (STATE_CHG_ID, STATE_CNSTRNT_ID) values ('kuali.statechange.courseoffering.planned.draft', 'kuali.stateconstraint.courseoffering.tostate.draft.2' )
/