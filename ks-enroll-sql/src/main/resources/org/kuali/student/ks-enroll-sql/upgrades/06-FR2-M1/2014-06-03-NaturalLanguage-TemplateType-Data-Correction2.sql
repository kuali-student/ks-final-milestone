--////////////////////////////////////////////////////////////////////////
-- KSENROLL-13037 : Correction with Subselect. Previous script changed ID
--////////////////////////////////////////////////////////////////////////

UPDATE KRMS_NL_TMPL_T T
 SET T.NL_USAGE_ID = 'KS-KRMS-NL-USAGE-1007'
WHERE t.typ_id = (Select typ.typ_id from krms_typ_t typ where typ.nm = 'kuali.krms.proposition.type.exam.freeform.text')
 AND T.NL_USAGE_ID = 'KS-KRMS-NL-USAGE-1005'
/