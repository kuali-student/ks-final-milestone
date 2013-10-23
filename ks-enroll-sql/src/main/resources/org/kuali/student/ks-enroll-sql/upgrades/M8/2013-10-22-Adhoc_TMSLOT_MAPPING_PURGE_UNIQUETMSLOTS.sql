-- Create distinct adl adhoc timeslots
create table t_distinct_adhoc_tmslots_adl as (select distinct e.start_time_ms as st, e.end_time_ms as et, e.weekdays from ksen_lui a 
inner join ksen_lui_schedule b on a.id = b.lui_id 
inner join ksen_sched_cmp c on c.sched_id = b.sched_id 
inner join ksen_sched_cmp_tmslot d on d.sched_cmp_id = c.id
inner join ksen_sched_tmslot e on e.id = d.tm_slot_id 
inner join ksen_atp f on f.id = a.atp_id 
where e.start_time_ms is not null and e.end_time_ms is not null and e.weekdays is not null and e.name is null)
/
alter table t_distinct_adhoc_tmslots_adl add newtsid varchar2(255)
/
update t_distinct_adhoc_tmslots_adl set newtsid = SYS_GUID()
/
CREATE SEQUENCE t_nameSeq MINVALUE 1033 START WITH 1033 INCREMENT BY 1 NOCACHE
/
alter table t_distinct_adhoc_tmslots_adl add newname varchar2(255)
/
update t_distinct_adhoc_tmslots_adl set newname = t_nameSeq.nextval
/
INSERT INTO KSEN_SCHED_TMSLOT (CREATEID,CREATETIME,END_TIME_MS,ID,NAME,START_TIME_MS,TM_SLOT_STATE,TM_SLOT_TYPE,UPDATEID,UPDATETIME,VER_NBR,WEEKDAYS) 
select 'batchjob', TO_DATE('20131022000101', 'YYYYMMDDHH24MISS'), a.et, a.newtsid, a.newname, a.st, 'kuali.scheduling.timeslot.state.active', 'kuali.scheduling.time.slot.type.activityoffering.adhoc', 
'batchjob',TO_DATE('20131022000101', 'YYYYMMDDHH24MISS' ), 0, a.weekdays 
from t_distinct_adhoc_tmslots_adl a
/
update ksen_sched_cmp_tmslot t1
set t1.tm_slot_id = 
(select t2.newtsid  from t_distinct_adhoc_tmslots_adl t2, ksen_sched_tmslot t3 where t3.id = t1.tm_slot_id 
and t3.end_time_ms = t2.et and t3.start_time_ms = t2.st and t3.weekdays = t2.weekdays)
where exists (select null from t_distinct_adhoc_tmslots_adl t2, ksen_sched_tmslot t3 where t3.id = t1.tm_slot_id 
and t3.end_time_ms = t2.et and t3.start_time_ms = t2.st and t3.weekdays = t2.weekdays and t3.name is null)
/
create table t_all_adhoc_tmslots_rdl as (select g.start_time_ms as st, g.end_time_ms as et, g.weekdays  
from ksen_lui a inner join ksen_sched_ref_object b on b.ref_object_id = a.id 
inner join ksen_sched_rqst_set c on c.id = b.sched_rqst_set_id 
inner join ksen_sched_rqst d on d.sched_rqst_set_id = c.id 
inner join KSEN_SCHED_RQST_CMP e on e.sched_rqst_id = d.id 
inner join KSEN_SCHED_RQST_CMP_TMSLOT f on f.cmp_id = e.id
inner join KSEN_SCHED_TMSLOT g on g.id = f.tm_slot_id
inner join ksen_atp h on h.id = a.atp_id 
where g.start_time_ms is not null and g.end_time_ms is not null and g.weekdays is not null and g.name is null)
/
create table t_distinct_adhoc_tmslots_rdl as 
(select distinct st, et, weekdays from t_all_adhoc_tmslots_rdl t1 
where not exists (select null from t_distinct_adhoc_tmslots_adl t2 where t2.et = t1.et and t2.st = t1.st and t2.weekdays = t1.weekdays))
/
alter table t_distinct_adhoc_tmslots_rdl add newtsid varchar2(255)
/
update t_distinct_adhoc_tmslots_rdl set newtsid = SYS_GUID()
/
alter table t_distinct_adhoc_tmslots_rdl add newname varchar2(255)
/
update t_distinct_adhoc_tmslots_rdl set newname = t_nameSeq.nextval
/
INSERT INTO KSEN_SCHED_TMSLOT (CREATEID,CREATETIME,END_TIME_MS,ID,NAME,START_TIME_MS,TM_SLOT_STATE,TM_SLOT_TYPE,UPDATEID,UPDATETIME,VER_NBR,WEEKDAYS) 
select 'batchjob', TO_DATE('20131021000101', 'YYYYMMDDHH24MISS'), a.et, a.newtsid, a.newname, a.st, 'kuali.scheduling.timeslot.state.active', 'kuali.scheduling.time.slot.type.activityoffering.adhoc', 
'batchjob',TO_DATE('20131021000101', 'YYYYMMDDHH24MISS' ), 0, a.weekdays from t_distinct_adhoc_tmslots_rdl a
/
update ksen_sched_rqst_cmp_tmslot t1
set t1.tm_slot_id = 
(select t2.newtsid  from t_distinct_adhoc_tmslots_rdl t2, ksen_sched_tmslot t3 where t3.id = t1.tm_slot_id 
and t3.end_time_ms = t2.et and t3.start_time_ms = t2.st and t3.weekdays = t2.weekdays)
where exists (select null from t_distinct_adhoc_tmslots_rdl t2, ksen_sched_tmslot t3 where t3.id = t1.tm_slot_id 
and t3.end_time_ms = t2.et and t3.start_time_ms = t2.st and t3.weekdays = t2.weekdays and t3.name is null)
/
update ksen_sched_rqst_cmp_tmslot t1
set t1.tm_slot_id = 
(select t2.newtsid  from t_distinct_adhoc_tmslots_adl t2, ksen_sched_tmslot t3 where t3.id = t1.tm_slot_id 
and t3.end_time_ms = t2.et and t3.start_time_ms = t2.st and t3.weekdays = t2.weekdays)
where exists (select null from t_distinct_adhoc_tmslots_adl t2, ksen_sched_tmslot t3 where t3.id = t1.tm_slot_id 
and t3.end_time_ms = t2.et and t3.start_time_ms = t2.st and t3.weekdays = t2.weekdays and t3.name is null)
/
delete from ksen_sched_tmslot 
where name is null and 
id not in (select tm_slot_id from ksen_sched_cmp_tmslot) and
id not in (select tm_slot_id from KSEN_SCHED_RQST_CMP_TMSLOT)
/
alter table ksen_sched_tmslot add constraint tmslots_unique unique (END_TIME_MS, START_TIME_MS, TM_SLOT_TYPE, WEEKDAYS)
/
drop table t_distinct_adhoc_tmslots_adl
/
drop table t_all_adhoc_tmslots_rdl
/
drop table t_distinct_adhoc_tmslots_rdl
/
drop sequence t_nameSeq
/
