--Fix the types in the ref data to match the code
UPDATE
    KSEN_SCHED_RQST_SET
SET
    SCHED_RQST_SET_TYPE='kuali.scheduling.schedule.request.set.type.schedule.request.set'
WHERE
    SCHED_RQST_SET_TYPE='kuali.scheduling.schedule.request.set.type.schedule.request'
/
