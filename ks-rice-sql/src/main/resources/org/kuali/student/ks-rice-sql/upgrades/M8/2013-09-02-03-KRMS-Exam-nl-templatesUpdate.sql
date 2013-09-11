
--Propositions
update KRMS_NL_TMPL_T
set TMPL = '$weekdays at $startTime - $endTime'
where NL_TMPL_ID IN('KS-KRMS-NL-TMPL-55693','KS-KRMS-NL-TMPL-55695')
/