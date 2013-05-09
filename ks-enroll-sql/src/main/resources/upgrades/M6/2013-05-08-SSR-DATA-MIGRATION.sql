-- Delete any lingering data
delete from ksen_sched_rqst_set

/

delete from ksen_lui_schedule

/

delete from ksen_sched_rqst_set_ref_obj

/

-- Populate ksen_lui_schedule join table
insert into ksen_lui_schedule (lui_id, sched_id) (select id, schedule_id from ksen_lui where lui_type like 'kuali.lui.type.activity.offering%' and schedule_id is not null)

/

-- Start with assigning ksen_sched_rqst ids in rqst
-- (lot easier this way to create rqst_set in the next step)
update ksen_sched_rqst set SCHED_RQST_SET_ID = SYS_GUID()

/

-- Populate ksen_sched_rqst_set table
Insert into ksen_sched_rqst_set (ID, OBJ_ID, SCHED_RQST_SET_TYPE, SCHED_RQST_SET_STATE, MAX_ENROLLMENT_SHARED_IND, MAX_ENROLLMENT,
  REF_OBJ_TYPE, VER_NBR, CREATETIME, CREATEID)  (SELECT SCHED_RQST_SET_ID, id, 'kuali.scheduling.schedule.request.set.type.schedule.request',
  'kuali.scheduling.schedule.request.set.state.created', '0', '0', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', '0',
  TO_DATE( '20130503000000', 'YYYYMMDDHH24MISS' ), 'admin' from ksen_sched_rqst)

/

-- Populate ksen_sched_rqst_set_ref_obj join table  with AO ids
insert into ksen_sched_rqst_set_ref_obj (ref_obj_id, sched_rqst_set_id) (select ref_object_id, sched_rqst_set_id from ksen_sched_rqst where ref_object_type = 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo')

/

-- Populate ksen_sched_rqst_set_ref_obj join table with AO ids from ColoSets
insert into ksen_sched_rqst_set_ref_obj (ref_obj_id, sched_rqst_set_id) (select lui_id, subquery.sched_rqst_set_id from ksen_lui_set_lui, (select ref_object_id, sched_rqst_set_id from ksen_sched_rqst where ref_object_type = 'kuali.luiset.type.colocated.offering.set') subquery where subquery.ref_object_id =  lui_set_id)

/

-- Populate sched_id in ksen_sched_rqst
create table ssrtemp1_t as (select sched_id, sched_rqst_set_id from ksen_lui_schedule a inner join ksen_sched_rqst_set_ref_obj b on a.lui_id = b.ref_obj_id)

/

update ksen_sched_rqst a set sched_id = (select sched_id from ssrtemp1_t where ssrtemp1_t.sched_rqst_set_id = a.sched_rqst_set_id and rownum = 1)

/

drop table ssrtemp1_t

/

-- Create a temp table for colosets
create table colosets_t as (select ref_object_id, sched_rqst_set_id from ksen_sched_rqst where ref_object_type = 'kuali.luiset.type.colocated.offering.set')

/

-- Create temp table with srsid, maxind boolean
create table maxind_t as (select b.sched_rqst_set_id as srsid, a.attr_value as maxind from ksen_lui_set_attr a, colosets_t b where a.owner_id = b.ref_object_id and a.attr_key = 'kuali.attribute.colocatedset.is.max.enrollment.shared')

/

-- Create temp table with srsid, maxenr
create table maxenr_t as (select b.sched_rqst_set_id as srsid, a.attr_value as maxenr from ksen_lui_set_attr a, colosets_t b where a.owner_id = b.ref_object_id and a.attr_key = 'kuali.attribute.colocatedset.max.enrollment')

/

drop table colosets_t

/

-- Populate MAX_ENROLLMENT in ksen_sched_rqst_set
update ksen_sched_rqst_set a set max_enrollment = (select to_number(trim(maxenr)) from maxenr_t where maxenr_t.srsid = a.id)

/

drop table maxenr_t

/

-- Populate MAX_ENROLLMENT_SHARED_IND in ksen_sched_rqst_set
update ksen_sched_rqst_set a set max_enrollment_shared_ind = (select
case
      when trim(maxind) = 'Y'  then 1
      when trim(maxind) = 'N' then 0
    end
from maxind_t b where b.srsid = a.id)

/

drop table maxind_t

/

-- Drop redundant columns from KSEN_SCHED_RQST and KSEN_LUI
-- ALTER TABLE KSEN_SCHED_RQST DROP (REF_OBJECT_ID, REF_OBJECT_TYPE)
-- ALTER TABLE KSEN_LUI DROP COLUMN SCHEDULE_ID