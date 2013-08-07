--Clear out all schedules since these point to non existant rooms/building
delete from KSEN_SCHED_CMP_TMSLOT
/
delete from KSEN_SCHED_CMP
/
delete from KSEN_SCHED
/
update KSEN_LUI set KSEN_LUI.SCHEDULE_ID = null
/
