-- KSENROLL-14869: Update Applied hold types
update KSEN_TYPE
   set KSEN_TYPE.TYPE_KEY = 'kuali.hold.appliedhold.type.student'
 where KSEN_TYPE.TYPE_KEY = 'kuali.hold.type.student';
/
update KSEN_HOLD
   set KSEN_HOLD.HOLD_TYPE = 'kuali.hold.appliedhold.type.student'
 where KSEN_HOLD.HOLD_TYPE = 'kuali.hold.type.student';
/