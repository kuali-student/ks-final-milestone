--KSENROLL-12440
update KSEN_LPR set LPR_STATE='kuali.lpr.state.active' where LPR_STATE='kuali.lpr.state.registered'
/
--KSENROLL-12441
delete from KSEN_TYPE where TYPE_KEY='kuali.lpr.type.waitlist.format.offering'
/
