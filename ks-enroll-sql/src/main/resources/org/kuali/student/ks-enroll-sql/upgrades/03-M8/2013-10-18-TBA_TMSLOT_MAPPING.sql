-- Identify TBA entities on the ADL side of scheduling
create table t_used_tba_tmslots_adl as (Select distinct  d.sched_cmp_id as cmpId, f.atp_type as type, e.id as tsId, e.start_time_ms as st, e.end_time_ms as et, e.weekdays from ksen_lui a 
inner join ksen_lui_schedule b on a.id = b.lui_id 
inner join ksen_sched_cmp c on c.sched_id = b.sched_id 
inner join ksen_sched_cmp_tmslot d on d.sched_cmp_id = c.id
inner join ksen_sched_tmslot e on e.id = d.tm_slot_id 
inner join ksen_atp f on f.id = a.atp_id 
where e.start_time_ms is null or e.end_time_ms is null or e.weekdays is null)
/
-- Identify TBA entities on the RDL side of scheduling
create table t_used_tba_tmslots_rdl as (select distinct f.cmp_id as cmpId, h.atp_type as type, g.id as tsId, g.start_time_ms as st, g.end_time_ms as et, g.weekdays  from ksen_lui a inner join ksen_sched_ref_object b on b.ref_object_id = a.id 
inner join ksen_sched_rqst_set c on c.id = b.sched_rqst_set_id 
inner join ksen_sched_rqst d on d.sched_rqst_set_id = c.id 
inner join KSEN_SCHED_RQST_CMP e on e.sched_rqst_id = d.id 
inner join KSEN_SCHED_RQST_CMP_TMSLOT f on f.cmp_id = e.id
inner join KSEN_SCHED_TMSLOT g on g.id = f.tm_slot_id
inner join ksen_atp h on h.id = a.atp_id 
where g.start_time_ms is  null or g.end_time_ms is  null or g.weekdays is null)
/
-- Upon investigation, all the current TBA values were found to be copies of the following
INSERT INTO KSEN_SCHED_TMSLOT (CREATEID,CREATETIME,ID,NAME,TM_SLOT_STATE,TM_SLOT_TYPE,UPDATEID,UPDATETIME,VER_NBR,WEEKDAYS) 
VALUES ('batchjob', TO_DATE('20131018000101', 'YYYYMMDDHH24MISS'), SYS_GUID(), 
'1032', 'kuali.scheduling.timeslot.state.active', 'kuali.scheduling.time.slot.type.activityoffering.tba', 
'batchjob',TO_DATE('20131018000101', 'YYYYMMDDHH24MISS' ), 0, 'T')
/
-- Update ADL with the new TBA timeslot
update KSEN_SCHED_CMP_TMSLOT t1 
set t1.tm_slot_id = (select id from KSEN_SCHED_TMSLOT where name = '1032') 
where exists (select null from t_used_tba_tmslots_adl t2 where t2.cmpId = t1.sched_cmp_id) 
/
-- Update RDL with the new TBA timeslot
update KSEN_SCHED_RQST_CMP_TMSLOT t1 
set t1.tm_slot_id = (select id from KSEN_SCHED_TMSLOT where name = '1032') 
where exists (select null from t_used_tba_tmslots_rdl t2 where t2.cmpId = t1.cmp_id)
/
drop table t_used_tba_tmslots_adl
/
drop table t_used_tba_tmslots_rdl
/
