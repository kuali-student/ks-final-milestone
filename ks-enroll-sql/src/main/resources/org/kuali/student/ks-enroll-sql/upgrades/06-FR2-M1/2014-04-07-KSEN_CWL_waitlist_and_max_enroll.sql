-- KSENROLL-12329
-- 201208 (Fall 2012) ENGL101 (1009) - no WL
update KSEN_CWL set CWL_STATE = 'kuali.course.waitlist.state.inactive' where ID = (select CWL_ID from KSEN_CWL_ACTIV_OFFER where ACTIV_OFFER_ID = '4de6b9e2-57af-4eb4-8f69-6c83c2074c58')
/
-- 201208 (Fall 2012) ENGL101 (1001) - max enroll for AO = 1
update KSEN_LUI set MAX_SEATS = 1, MIN_SEATS = 1 where ID = '35828d6a-8adb-491a-9861-c06f12de963e'
/
-- 201208 (Fall 2012) ENGL101 (1003) - max enroll for AO = 1, WL = 1
update KSEN_LUI set MAX_SEATS = 1, MIN_SEATS = 1 where ID = 'dd5e40c7-db6a-4018-a119-68c9b4ed07de'
/
update KSEN_CWL set MAX_SIZE = 1 where ID = (select CWL_ID from KSEN_CWL_ACTIV_OFFER where ACTIV_OFFER_ID = 'dd5e40c7-db6a-4018-a119-68c9b4ed07de')
/
