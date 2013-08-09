-- Reomve records from KSEN_MSTONE and KSEN_ATP that have invalid state keys

delete from KSEN_ATPMSTONE_RELTN where id in (select id from KSEN_ATPMSTONE_RELTN WHERE MSTONE_ID in (select id from KSEN_MSTONE where MSTONE_STATE IN ('Official', 'Tentative', 'Actual')))
/
delete from KSEN_MSTONE where id in (select id from KSEN_MSTONE where MSTONE_STATE IN ('Official', 'Tentative', 'Actual'))
/
delete from KSEN_ATP where id in (select id from KSEN_ATP where atp_state = 'Official' or atp_state = 'Tentative' or atp_state = 'Actual')
/