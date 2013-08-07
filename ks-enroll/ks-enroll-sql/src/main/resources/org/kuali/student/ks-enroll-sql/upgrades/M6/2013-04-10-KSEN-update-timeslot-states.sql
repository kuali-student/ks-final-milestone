--Fix bug where there were extra spaces in the data

UPDATE
    KSEN_SCHED_TMSLOT
SET
    TM_SLOT_STATE='kuali.scheduling.timeslot.state.active'
WHERE
    TM_SLOT_STATE LIKE '%kuali.scheduling.timeslot.state.active%'
/
