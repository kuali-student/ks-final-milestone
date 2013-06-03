-- KSENROLL-5496
update KRMS_NL_TMPL_T
   set TMPL = 'Must have successfully completed $courseClu.getOfficialIdentifier().getCode() between <term1> and <term2>'
 where NL_TMPL_ID IN ('KS-KRMS-NL-TMPL-1343', 'KS-KRMS-NL-TMPL-1382')
/

update KRMS_NL_TMPL_T
    set TMPL = 'Must have successfully completed $courseClu.getOfficialIdentifier().getCode() as of <term>'
  where NL_TMPL_ID IN ('KS-KRMS-NL-TMPL-1388', 'KS-KRMS-NL-TMPL-1349')
/

update KRMS_NL_TMPL_T
    set TMPL = 'Must have successfully completed $courseClu.getOfficialIdentifier().getCode() prior to <term>'
  where NL_TMPL_ID IN ('KS-KRMS-NL-TMPL-1396', 'KS-KRMS-NL-TMPL-1357')
/
