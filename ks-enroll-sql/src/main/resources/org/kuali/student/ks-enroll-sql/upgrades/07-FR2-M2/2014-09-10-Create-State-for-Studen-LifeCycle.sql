--KSENROLL-14791 : Create State for Studen LifeCycle
--Create LifeCycle Key
insert into KSEN_STATE_LIFECYCLE
  (CREATEID, CREATETIME,DESCR_FORMATTED, DESCR_PLAIN, ID, NAME, VER_NBR)
values
  ('SYSTEMLOADER',
   TO_DATE('20120716000000', 'YYYYMMDDHH24MISS'),
   'kuali.hold.process.student state lifecycle',
   'kuali.hold.process.student state lifecycle',
   'kuali.hold.process.student',
   'kuali.hold.process.student',
   0)
/
--Create states for Student
insert into KSEN_STATE
  (CREATEID, CREATETIME, DESCR_PLAIN, ID, LIFECYCLE_KEY, NAME, VER_NBR)
values
  ('SYSTEMLOADER',
   TO_DATE('20120717000000', 'YYYYMMDDHH24MISS'),
   'The hold is active.',
   'kuali.hold.state.active',
   'kuali.hold.process.student',
   'Active',
   0)
/
insert into KSEN_STATE
  (CREATEID, CREATETIME, DESCR_PLAIN, ID, LIFECYCLE_KEY, NAME, VER_NBR)
values
  ('SYSTEMLOADER',
   TO_DATE('20120717000000', 'YYYYMMDDHH24MISS'),
   'The hold was active and the issue has been resolved.',
   'kuali.hold.state.released',
   'kuali.hold.process.student',
   'Released',
   0)
/
insert into KSEN_STATE
  (CREATEID, CREATETIME, DESCR_PLAIN, ID, LIFECYCLE_KEY, NAME, VER_NBR)
values
  ('SYSTEMLOADER',
   TO_DATE('20120717000000', 'YYYYMMDDHH24MISS'),
   'The hold was canceled or removed because it was originally put on in error',
   'kuali.hold.state.canceled',
   'kuali.hold.process.student',
   'Canceled',
   0)
/