
--KSENROLL 6972
DELETE FROM KRMS_NL_TMPL_T WHERE TYP_ID IN (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.agenda.type.course.offering%')
/
DELETE FROM KRMS_NL_TMPL_T WHERE TYP_ID IN (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.agenda.type.activity.offering%')
/
DELETE FROM KRMS_NL_TMPL_T WHERE TYP_ID IN (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.rule.type.course.offering%')
/
DELETE FROM KRMS_NL_TMPL_T WHERE TYP_ID IN (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.rule.type.activity.offering%')
/

DELETE FROM KRMS_TYP_RELN_T WHERE FROM_TYP_ID IN (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.agenda.type.course.offering%')
/
DELETE FROM KRMS_TYP_RELN_T WHERE FROM_TYP_ID IN (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.agenda.type.activity.offering%')
/
DELETE FROM KRMS_TYP_RELN_T WHERE FROM_TYP_ID IN (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.rule.type.course.offering%')
/
DELETE FROM KRMS_TYP_RELN_T WHERE FROM_TYP_ID IN (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.rule.type.activity.offering%')
/

DELETE FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.agenda.type.course.offering%'
/
DELETE FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.agenda.type.activity.offering%'
/
DELETE FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.rule.type.course.offering%'
/
DELETE FROM KRMS_TYP_T WHERE NM LIKE '%kuali.krms.rule.type.activity.offering%'
/

--KSENROLL 7024
--This is fixed by 6972

--KSENROLL 7023
update krms_nl_tmpl_t set tmpl = 'Course that Restricts Credits'
where nl_usage_id = 'KS-KRMS-NL-USAGE-1004' and typ_id = (Select typ_id from krms_typ_t where nm = 'kuali.krms.rule.type.course.credit.restriction')

--KSENROLL 6992
--This is fixed by 6972