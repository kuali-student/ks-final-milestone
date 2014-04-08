--KSENROLL-10691 Adding a new NL usage type matrix and updating the templates accordingly.
update krms_prop_t prop
   set prop.TYP_ID = 'KS-KRMS-TYP-55748'
 where prop.prop_id = 'KS-KRMS-PROP-14809'
/