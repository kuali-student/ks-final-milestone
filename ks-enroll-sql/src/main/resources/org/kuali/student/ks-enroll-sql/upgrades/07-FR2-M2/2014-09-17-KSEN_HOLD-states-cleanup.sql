INSERT INTO ksen_state_lifecycle(ID, NAME, DESCR_PLAIN, DESCR_FORMATTED, VER_NBR, CREATETIME, CREATEID)
VALUES( 'kuali.hold.appliedhold.lifecycle', 'kuali.hold.appliedhold.lifecycle', 'kuali.hold.appliedhold state lifecycle', 'kuali.hold.appliedhold state lifecycle', 0, '17-SEP-14 11.00.00 AM', 01 )
/

UPDATE KSEN_STATE s
SET s.id = 'kuali.hold.appliedhold.state.active', s.lifecycle_key = 'kuali.hold.appliedhold.lifecycle'
WHERE s.id = 'kuali.hold.state.active'
/

UPDATE KSEN_STATE s
SET s.id = 'kuali.hold.appliedhold.state.canceled', s.lifecycle_key = 'kuali.hold.appliedhold.lifecycle'
WHERE s.id = 'kuali.hold.state.canceled'
/

UPDATE KSEN_STATE s
SET s.id = 'kuali.hold.appliedhold.state.expired', s.name = 'Expired',
  s.lifecycle_key = 'kuali.hold.appliedhold.lifecycle'
WHERE s.id = 'kuali.hold.state.released'
/

insert into KSEN_STATE
(CREATEID, CREATETIME, DESCR_PLAIN, ID, LIFECYCLE_KEY, NAME, VER_NBR)
values
  ('SYSTEMLOADER',
   TO_DATE('20140917000000', 'YYYYMMDDHH24MISS'),
   'The hold is open because the active date for the applied hold has not yet been reached.',
   'kuali.hold.appliedhold.state.open',
   'kuali.hold.appliedhold.lifecycle',
   'Open',
   0)
/

insert into KSEN_STATE
(CREATEID, CREATETIME, DESCR_PLAIN, ID, LIFECYCLE_KEY, NAME, VER_NBR)
values
  ('SYSTEMLOADER',
   TO_DATE('20140917000000', 'YYYYMMDDHH24MISS'),
   'The hold was deleted from the students record.',
   'kuali.hold.appliedhold.state.deleted',
   'kuali.hold.appliedhold.lifecycle',
   'Deleted',
   0)
/

DELETE FROM KSEN_STATE_LIFECYCLE sl
WHERE sl.id = 'kuali.hold.process.student'
/

UPDATE KSEN_HOLD h
SET h.hold_state = 'kuali.hold.appliedhold.state.active'
WHERE h.hold_state = 'kuali.hold.state.active'
/

UPDATE KSEN_HOLD h
SET h.hold_state = 'kuali.hold.appliedhold.state.active'
WHERE h.hold_state = 'kuali.hold.state.released'
/
