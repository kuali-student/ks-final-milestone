--KSENROLL-8851 Ref Data Fix CM data for Final Exam Status - in addition to 2013-08-30-KSLU_CLU_ATTR-Ref-Data-Update.sql
--and 2013-09-10-KSLU_CLU_ATTR-Ref-Data-Update.sql > all listed courses and related COs for all terms need to be updated
UPDATE KSEN_LUI_ATTR SET ATTR_VALUE = 'ALTERNATE' WHERE ATTR_KEY='kuali.attribute.final.exam.indicator' AND OWNER_ID IN
  (SELECT ID FROM KSEN_LUI LUI where LUI.CLU_ID IN
    (SELECT CLU.ID FROM KSLU_CLU CLU, KSLU_CLU_IDENT IDENT WHERE CLU.OFFIC_CLU_ID = IDENT.ID
    AND (IDENT.CD LIKE 'ENGL101%' or IDENT.CD LIKE 'ENGL391%' or IDENT.CD LIKE 'ENGL392%' or IDENT.CD LIKE 'ENGL393%' or IDENT.CD LIKE 'ENGL394%' or IDENT.CD LIKE 'ENGL395%'))
  AND LUI.LUI_TYPE LIKE 'kuali.lui.type.course.offering')
/