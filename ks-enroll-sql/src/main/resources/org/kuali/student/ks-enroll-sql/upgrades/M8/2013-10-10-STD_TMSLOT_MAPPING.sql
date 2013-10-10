-- Identify non-tba timeslots used as ADL
create table t_used_tmslots_adl as 
(Select distinct d.sched_cmp_id as cmpId, f.atp_type as type, e.id as tsId, e.start_time_ms as st, e.end_time_ms as et, e.weekdays 
from ksen_lui a 
inner join ksen_lui_schedule b on a.id = b.lui_id 
inner join ksen_sched_cmp c on c.sched_id = b.sched_id 
inner join ksen_sched_cmp_tmslot d on d.sched_cmp_id = c.id
inner join ksen_sched_tmslot e on e.id = d.tm_slot_id 
inner join ksen_atp f on f.id = a.atp_id 
where e.start_time_ms is not null and e.end_time_ms is not null and e.weekdays is not null) 
/
-- Identify non-tba timeslots used as RDL
create table t_used_tmslots_rdl as 
(select distinct f.cmp_id as cmpId, h.atp_type as type, g.id as tsId, g.start_time_ms as st, g.end_time_ms as et, g.weekdays  
from ksen_lui a 
inner join ksen_sched_ref_object b on b.ref_object_id = a.id 
inner join ksen_sched_rqst_set c on c.id = b.sched_rqst_set_id 
inner join ksen_sched_rqst d on d.sched_rqst_set_id = c.id 
inner join KSEN_SCHED_RQST_CMP e on e.sched_rqst_id = d.id 
inner join KSEN_SCHED_RQST_CMP_TMSLOT f on f.cmp_id = e.id
inner join KSEN_SCHED_TMSLOT g on g.id = f.tm_slot_id
inner join ksen_atp h on h.id = a.atp_id 
where g.start_time_ms is not null and g.end_time_ms is not null and g.weekdays is not null)
/
-- Itentify matching Fall standard timeslots for ADL
create table t_new_mappings_fall_adl as 
(select a.cmpId, a.tsId, b.id  from t_used_tmslots_adl a, ksen_sched_tmslot b 
where a.type = 'kuali.atp.type.Fall' and 
b.tm_slot_type = 'kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall' and 
a.st = b.start_time_ms and 
a.et = b.end_time_ms and 
a.weekdays = b.weekdays and 
b.name is not null)
/
-- Itentify matching Spring standard timeslots for ADL
create table t_new_mappings_spring_adl as 
(select a.cmpId, a.tsId, b.id  from t_used_tmslots_adl a, ksen_sched_tmslot b 
where a.type = 'kuali.atp.type.Spring' and 
b.tm_slot_type = 'kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring' and 
a.st = b.start_time_ms and 
a.et = b.end_time_ms and 
a.weekdays = b.weekdays and 
b.name is not null)
/
-- Itentify matching Fall standard timeslots for RDL
create table t_new_mappings_fall_rdl as 
(select a.cmpId, a.tsId, b.id  from t_used_tmslots_rdl a, ksen_sched_tmslot b 
where a.type = 'kuali.atp.type.Fall' and 
b.tm_slot_type = 'kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.fall' and 
a.st = b.start_time_ms and 
a.et = b.end_time_ms and 
a.weekdays = b.weekdays and 
b.name is not null)
/
-- Itentify matching Spring standard timeslots for RDL
create table t_new_mappings_spring_rdl as 
(select a.cmpId, a.tsId, b.id  from t_used_tmslots_rdl a, ksen_sched_tmslot b 
where a.type = 'kuali.atp.type.Spring' and 
b.tm_slot_type = 'kuali.scheduling.time.slot.type.activityoffering.standard.fullterm.spring' and 
a.st = b.start_time_ms and 
a.et = b.end_time_ms and 
a.weekdays = b.weekdays and 
b.name is not null)
/
-- Map Fall ADL to new standard timeslots
update KSEN_SCHED_CMP_TMSLOT a set a.tm_slot_id = 
(select id from t_new_mappings_fall_adl b 
where b.cmpId = a.sched_cmp_id) 
where exists (select null from t_new_mappings_fall_adl c where c.cmpId = a.sched_cmp_id)
/
-- Map Spring ADL to new standard timeslots
update KSEN_SCHED_CMP_TMSLOT a set a.tm_slot_id = 
(select id from t_new_mappings_spring_adl b 
where b.cmpId = a.sched_cmp_id) 
where exists (select null from t_new_mappings_spring_adl c where c.cmpId = a.sched_cmp_id)
/
-- Map Fall RDL to new standard timeslots
update KSEN_SCHED_RQST_CMP_TMSLOT a set a.tm_slot_id = 
(select id from t_new_mappings_fall_rdl b 
where b.cmpId = a.cmp_id) 
where exists (select null from t_new_mappings_fall_rdl c where c.cmpId = a.cmp_id)
/
-- Map Spring RDL to new standard timeslots
update KSEN_SCHED_RQST_CMP_TMSLOT a set a.tm_slot_id = (
select id from t_new_mappings_spring_rdl b 
where b.cmpId = a.cmp_id) 
where exists (select null from t_new_mappings_spring_rdl c where c.cmpId = a.cmp_id)
/
drop table t_used_tmslots_adl
/
drop table t_used_tmslots_rdl
/
drop table t_new_mappings_fall_adl
/
drop table t_new_mappings_spring_adl
/
drop table t_new_mappings_fall_rdl
/
drop table t_new_mappings_spring_rdl
/
