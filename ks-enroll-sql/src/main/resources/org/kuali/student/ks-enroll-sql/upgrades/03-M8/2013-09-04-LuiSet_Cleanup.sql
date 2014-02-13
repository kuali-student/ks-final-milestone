-- LuiSet/KSEN_LUI_SET and related tables were used for full colocation of AOs prior to the scheduling service refactor,
-- but the ref data was never removed. These tables aren't currently used for anything else so all data can be clobbered.
delete from KSEN_LUI_SET_LUI
/
delete from KSEN_LUI_SET_ATTR
/
delete from KSEN_LUI_SET
/
