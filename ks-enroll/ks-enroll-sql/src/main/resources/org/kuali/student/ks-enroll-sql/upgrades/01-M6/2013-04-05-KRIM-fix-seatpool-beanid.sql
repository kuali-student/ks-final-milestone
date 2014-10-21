--KSENROLL-6260
update KRIM_PERM_ATTR_DATA_T set ATTR_VAL='ActivityOfferingEdit-SeatPool' where ATTR_VAL='ActivityOfferingEdit-MainPage-SeatPool'
/

update KRIM_PERM_T set NM='View Group for ActivityOfferingEdit-SeatPool' where NM='View Group for ActivityOfferingEdit-MainPage-SeatPool'
/