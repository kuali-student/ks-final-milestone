-- KSENROLL-6111 - the states all have begining and trailing spaces. The state table does not contain spaces in the keys at all
update
KSEN_SCHED_TMSLOT
set KSEN_SCHED_TMSLOT.TM_SLOT_STATE = 'kuali.scheduling.timeslot.state.active'
where
KSEN_SCHED_TMSLOT.TM_SLOT_STATE like '%kuali.scheduling.timeslot.state.active%'
/