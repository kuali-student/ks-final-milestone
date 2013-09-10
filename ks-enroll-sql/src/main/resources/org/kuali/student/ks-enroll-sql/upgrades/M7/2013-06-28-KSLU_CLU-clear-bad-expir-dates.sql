--KSENROLL-7846 The expiration dates were set for some (104) active courses when they should not have been
UPDATE KSLU_CLU c SET c.EXPIR_DT=NULL WHERE c.LUTYPE_ID='kuali.lu.type.CreditCourse' AND c.CURR_VER_END IS NULL AND c.LAST_ATP IS NULL AND expir_dt IS NOT NULL
/
