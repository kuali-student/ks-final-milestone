--KSENROLL-10691 Adding a new NL usage type matrix and updating the templates accordingly.
update krms_prop_t prop
   set prop.TYP_ID = (Select typ.typ_id from krms_typ_t typ where typ.nm = 'kuali.krms.proposition.type.exam.freeform.text')
 where prop.prop_id = 'KS-KRMS-PROP-14809'
/

--KSENROLL:13037 - Adding for Fall Natural language
UPDATE krms_prop_t prop
   SET prop.TYP_ID = (Select typ.typ_id from krms_typ_t typ where typ.nm = 'kuali.krms.proposition.type.exam.freeform.text')
 WHERE prop.prop_id = 'KS-KRMS-PROP-14810'
/