---KSENROLL-12115 Remove start time from timeslot -Fix Ref Data
UPDATE KRMS_NL_TMPL_T NL
    SET NL.TMPL = '$weekdays at $startTime'
  where NL.TYP_ID =
        (SELECT TYP_ID
           FROM KRMS_TYP_T
          WHERE NM = 'kuali.krms.proposition.type.final.exam.timeslot')
    and NL.NL_USAGE_ID =
        (SELECT NL_USAGE_ID
           FROM KRMS_NL_USAGE_T
          where NM like 'kuali.krms.catalog')
/
UPDATE KRMS_NL_TMPL_T NL
    SET NL.TMPL = '$weekdays at $startTime'
  where NL.TYP_ID =
        (SELECT TYP_ID
           FROM KRMS_TYP_T
          WHERE NM = 'kuali.krms.proposition.type.final.exam.timeslot')
    and NL.NL_USAGE_ID =
        (SELECT NL_USAGE_ID
           FROM KRMS_NL_USAGE_T
          where NM like 'kuali.krms.edit')
/

DELETE FROM KRMS_TERM_PARM_T T
 where T.NM = 'kuali.term.parameter.type.timeslot.end'
/
