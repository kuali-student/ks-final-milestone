-- KSENROLL-9763 Emergency Timeslot ref data munging for UX/AFT work  LUID: a48435b3-a975-4897-9d3e-a406be211579 kuali.atp.2012Fall ENGL398L section 0301
update ksen_sched_cmp_tmslot set tm_slot_id = (select id from ksen_sched_tmslot where start_time_ms = '39600000' and tm_slot_type = 'kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall' and weekdays='MWF'and rownum = 1) where sched_cmp_id = '568f28c8-bc2e-4ae9-89c8-5f8871164a8c'
/
update ksen_sched_rqst_cmp_tmslot set  tm_slot_id = (select id from ksen_sched_tmslot where start_time_ms = '39600000' and tm_slot_type = 'kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall' and weekdays='MWF'and rownum = 1) where cmp_id = '6cbcb85d-84b8-4952-a2bf-d6a30dc53f02'
/
