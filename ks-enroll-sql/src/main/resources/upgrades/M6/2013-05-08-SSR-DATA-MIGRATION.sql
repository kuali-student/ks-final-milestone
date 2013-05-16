-- Delete any lingering data
delete from KSEN_SCHED_RQST_SET

/

delete from KSEN_LUI_SCHEDULE

/

delete from KSEN_SCHED_REF_OBJECT

/

-- Populate KSEN_LUI_SCHEDULE join table
insert into KSEN_LUI_SCHEDULE (LUI_ID, SCHED_ID) (select ID, SCHEDULE_ID from KSEN_LUI where LUI_TYPE like 'kuali.lui.type.activity.offering%' and SCHEDULE_ID is not null)

/

-- Start with assigning KSEN_SCHED_RQST ids in rqst
-- (lot easier this way to create rqst_set in the next step)
update KSEN_SCHED_RQST set SCHED_RQST_SET_ID = SYS_GUID()

/

-- Populate KSEN_SCHED_RQST_SET table
insert into KSEN_SCHED_RQST_SET (ID, OBJ_ID, SCHED_RQST_SET_TYPE, SCHED_RQST_SET_STATE, MAX_ENRL_SHARED_IND, MAX_ENRL,
  REF_OBJECT_TYPE, VER_NBR, CREATETIME, CREATEID)  (SELECT SCHED_RQST_SET_ID, ID, 'kuali.scheduling.schedule.request.set.type.schedule.request',
  'kuali.scheduling.schedule.request.set.state.created', '0', '0', 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo', '0',
  TO_DATE( '20130503000000', 'YYYYMMDDHH24MISS' ), 'admin' from KSEN_SCHED_RQST)

/

-- Populate KSEN_SCHED_REF_OBJECT join table  with AO ids
insert into KSEN_SCHED_REF_OBJECT (REF_OBJECT_ID, SCHED_RQST_SET_ID) (select REF_OBJECT_ID, SCHED_RQST_SET_ID from KSEN_SCHED_RQST where REF_OBJECT_TYPE = 'http://student.kuali.org/wsdl/courseOffering/ActivityOfferingInfo')

/

-- Populate KSEN_SCHED_REF_OBJECT join table with AO ids from ColoSets
insert into KSEN_SCHED_REF_OBJECT (REF_OBJECT_ID, SCHED_RQST_SET_ID) (select LUI_ID, subquery.SCHED_RQST_SET_ID from KSEN_LUI_set_lui, (select REF_OBJECT_ID, SCHED_RQST_SET_ID from KSEN_SCHED_RQST where REF_OBJECT_TYPE = 'kuali.luiset.type.colocated.offering.set') subquery where subquery.REF_OBJECT_ID =  lui_set_id)

/

-- Populate SCHED_ID in KSEN_SCHED_RQST
create table SSRTEMP1_T as (select SCHED_ID, SCHED_RQST_SET_ID from KSEN_LUI_SCHEDULE a inner join KSEN_SCHED_REF_OBJECT b on a.LUI_ID = b.REF_OBJECT_ID)

/

update KSEN_SCHED_RQST a set SCHED_ID = (select SCHED_ID from SSRTEMP1_T where SSRTEMP1_T.SCHED_RQST_SET_ID = a.SCHED_RQST_SET_ID and ROWNUM = 1)

/

drop table SSRTEMP1_T

/

-- Create a temp table for colosets
create table COLOSETS_T as (select REF_OBJECT_ID, SCHED_RQST_SET_ID from KSEN_SCHED_RQST where REF_OBJECT_TYPE = 'kuali.luiset.type.colocated.offering.set')

/

-- Create temp table with SRSID, MAXIND boolean
create table MAXIND_T as (select b.SCHED_RQST_SET_ID as SRSID, a.ATTR_VALUE as MAXIND from KSEN_LUI_SET_ATTR a, COLOSETS_T b where a.OWNER_ID = b.REF_OBJECT_ID and a.ATTR_KEY = 'kuali.attribute.colocatedset.is.max.enrollment.shared')

/

-- Create temp table with SRSID, MAXENR
create table MAXENR_T as (select b.SCHED_RQST_SET_ID as SRSID, a.ATTR_VALUE as MAXENR from KSEN_LUI_SET_ATTR a, COLOSETS_T b where a.OWNER_ID = b.REF_OBJECT_ID and a.ATTR_KEY = 'kuali.attribute.colocatedset.max.enrollment')

/

drop table COLOSETS_T

/

-- Populate MAX_ENRL in KSEN_SCHED_RQST_SET
update KSEN_SCHED_RQST_SET a set MAX_ENRL = (select TO_NUMBER(TRIM(MAXENR)) from MAXENR_T where MAXENR_T.SRSID = a.ID)

/

drop table MAXENR_T

/

-- Populate MAX_ENRL_SHARED_IND in KSEN_SCHED_RQST_SET
update KSEN_SCHED_RQST_SET a set MAX_ENRL_SHARED_IND = (select
case
      when TRIM(MAXIND) = 'Y'  then 1
      when TRIM(MAXIND) = 'N' then 0
    end
from MAXIND_T b where b.SRSID = a.ID)

/

drop table MAXIND_T

/

