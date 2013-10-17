-- Orphaned Schedules
create table t_orphanScheds as 
(select a.id as id 
from ksen_sched a 
where  not exists 
(select null 
from ksen_lui_schedule b 
where b.sched_id = a.id)
)
/
-- Orphaned Sched Comps 
create table t_orphanSchedCmp as 
(select id 
from ksen_sched_cmp 
where sched_id in 
(select id 
from t_orphanscheds)
)
/
-- Orphaned Sched Comp Tmslot 
create table t_orphanSchedCmpTm as 
(select sched_cmp_id  as id, tm_slot_id as tsid 
from ksen_sched_cmp_tmslot 
where sched_cmp_id in 
(select id 
from t_orphanSchedCmp)
)
/
delete from ksen_sched_cmp_tmslot where sched_cmp_id in (select id from t_orphanSchedCmpTm)
/
delete from ksen_sched_cmp where id in (select id from t_orphanschedcmp)
/
delete from ksen_sched where id in (select id from t_orphanscheds)
/
-- Since TBA/Adhoc mappings aren't completed yet, many otherwise orphan timeslots currently have dependency from ksen_sched_cmp_tmslot
delete from ksen_sched_tmslot where  name is null and id in (select tsid from t_orphanSchedCmpTm) and id not in (select tm_slot_id from ksen_sched_cmp_tmslot) and id not in (select tm_slot_id from KSEN_SCHED_RQST_CMP_TMSLOT)
/
drop table t_orphanScheds
/
drop table t_orphanSchedCmp
/
drop table t_orphanSchedCmpTm
/
