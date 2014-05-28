--KSENROLL-10691 Adding a new NL usage type matrix and updating the templates accordingly.
--Changing the two operators from Full Caps to lowercase.
--adding plain AND operator
UPDATE KRMS_NL_TMPL_ATTR_T TMP
   SET tmp.attr_val = 'And'
 WHERE tmp.attr_defn_id = 'KS-KRMS-ATTR-DERN-10007'
   and tmp.nl_tmpl_attr_id = 'KS-KRMS-NL-TMPL-ATTR-10000'
   and tmp.nl_tmpl_id = 'KS-KRMS-NL-TMPL-55755'
/

--adding plain OR operator
   UPDATE KRMS_NL_TMPL_ATTR_T TMP
   SET tmp.attr_val = 'Or'
 WHERE tmp.attr_defn_id = 'KS-KRMS-ATTR-DERN-10007'
   and tmp.nl_tmpl_attr_id = 'KS-KRMS-NL-TMPL-ATTR-10002'
   and tmp.nl_tmpl_id = 'KS-KRMS-NL-TMPL-55754'
/