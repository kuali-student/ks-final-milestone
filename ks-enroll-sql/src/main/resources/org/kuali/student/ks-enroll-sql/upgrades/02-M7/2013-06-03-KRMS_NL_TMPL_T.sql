-- KSENROLL-5496
update KRMS_NL_TMPL_T
   set TMPL = 'Must have successfully completed $courseClu.getOfficialIdentifier().getCode() between $term.name and $term2.name'
 where NL_TMPL_ID IN ('KS-KRMS-NL-TMPL-1343', 'KS-KRMS-NL-TMPL-1382')
/

update KRMS_NL_TMPL_T
    set TMPL = 'Must have successfully completed $courseClu.getOfficialIdentifier().getCode() as of $term.name'
  where NL_TMPL_ID IN ('KS-KRMS-NL-TMPL-1388', 'KS-KRMS-NL-TMPL-1349')
/

update KRMS_NL_TMPL_T
    set TMPL = 'Must have successfully completed $courseClu.getOfficialIdentifier().getCode() prior to $term.name'
  where NL_TMPL_ID IN ('KS-KRMS-NL-TMPL-1396', 'KS-KRMS-NL-TMPL-1357')
/

----  KSENROLL-6599
update KRMS_NL_TMPL_T
    set TMPL = 'Must not have been admitted to the $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) Program with a class standing of $classStanding.name'
  where NL_TMPL_ID IN ('KS-KRMS-NL-TMPL-1338', 'KS-KRMS-NL-TMPL-1377')
/

----  KSENROLL-6601
update KRMS_NL_TMPL_T
   set TMPL = 'Student must be in a class standing of $classStanding.name'
 where NL_TMPL_ID IN ('KS-KRMS-NL-TMPL-1360', 'KS-KRMS-NL-TMPL-1399')
/
----  KSENROLL-6602
update KRMS_NL_TMPL_T
    set TMPL = 'Student must be in a class standing of $classStanding.name or greater'
  where NL_TMPL_ID IN ('KS-KRMS-NL-TMPL-1341', 'KS-KRMS-NL-TMPL-1380')
/
----  KSENROLL-6603
update KRMS_NL_TMPL_T
    set TMPL = 'Student must be in a class standing of $classStanding.name or less'
  where NL_TMPL_ID IN ('KS-KRMS-NL-TMPL-1358', 'KS-KRMS-NL-TMPL-1397')
/
 ----  KSENROLL-6604
update KRMS_NL_TMPL_T
    set TMPL = 'Must not be in a class standing of $classStanding.name'
  where NL_TMPL_ID IN ('KS-KRMS-NL-TMPL-1345', 'KS-KRMS-NL-TMPL-1384')
/
----KSENROLL-7046

update KRMS_NL_TMPL_T
    set TMPL = 'Must have been admitted to the <program> program'
  where NL_TMPL_ID = 'KS-KRMS-NL-TMPL-1114'
/

update KRMS_NL_TMPL_T
    set TMPL = 'Must be admitted to any program offered at the course campus location'
  where NL_TMPL_ID = 'KS-KRMS-NL-TMPL-1115'
/

update KRMS_NL_TMPL_T
    set TMPL = 'Must not have been admitted to the <program> program'
  where NL_TMPL_ID = 'KS-KRMS-NL-TMPL-1116'
/

update KRMS_NL_TMPL_T
    set TMPL = 'Must not have been admitted to the <program> program with a class standing of <class standing>'
  where NL_TMPL_ID = 'KS-KRMS-NL-TMPL-1117'
/
update KRMS_NL_TMPL_T
    set TMPL = 'Must have been admitted to a program offered by <org>'
  where NL_TMPL_ID = 'KS-KRMS-NL-TMPL-1118'
/

update KRMS_NL_TMPL_T
    set TMPL = 'Must have earned a minimum cumulative GPA of <GPA> in <duration><durationType>'
  where NL_TMPL_ID = 'KS-KRMS-NL-TMPL-1095'
/

--KSENROLL-7811
update KRMS_NL_TMPL_T
    set TMPL = '#if($intValue == 1 && $courseCluSet.getCluList().size() == 1)Must have successfully completed#{else}Must have successfully completed no more than $intValue $NLHelper.getProperGrammar($intValue, "course") from#end'
  where NL_TMPL_ID = 'KS-KRMS-NL-TMPL-1249'
  /

update KRMS_NL_TMPL_T
    set TMPL = '#if($intValue == 1 && $courseCluSet.getCluList().size() == 1)Must have successfully completed $courseCluSet.getCluSetAsCode()#{else}Must have successfully completed no more than $intValue $NLHelper.getProperGrammar($intValue, "course") from $courseCluSet.getCluSetAsCode()#end'
  where NL_TMPL_ID ='KS-KRMS-NL-TMPL-1187'
  /

 -- KSENROLL-8326
Update KRMS_NL_TMPL_T
  Set TMPL ='Must have earned a minimum cumulative GPA of $intValue in $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase()'
WHERE NL_TMPL_ID IN ('KS-KRMS-NL-TMPL-1272','KS-KRMS-NL-TMPL-1247')
/


