--Type Relationship
DELETE FROM KRMS_TYP_RELN_T WHERE TO_TYP_ID = (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.notadmitted.to.program.in.class.standing')
/
DELETE FROM KRMS_TYP_RELN_T WHERE TO_TYP_ID = (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.in.class.standing')
/
DELETE FROM KRMS_TYP_RELN_T WHERE TO_TYP_ID = (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.greater.than.class.standing')
/
DELETE FROM KRMS_TYP_RELN_T WHERE TO_TYP_ID = (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.less.than.class.standing')
/
DELETE FROM KRMS_TYP_RELN_T WHERE TO_TYP_ID = (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.notin.class.standing')
/
--National Language
DELETE FROM KRMS_NL_TMPL_T WHERE TYP_ID = (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.notadmitted.to.program.in.class.standing')
/
DELETE FROM KRMS_NL_TMPL_T WHERE TYP_ID = (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.in.class.standing')
/
DELETE FROM KRMS_NL_TMPL_T WHERE TYP_ID = (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.greater.than.class.standing')
/
DELETE FROM KRMS_NL_TMPL_T WHERE TYP_ID = (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.less.than.class.standing')
/
DELETE FROM KRMS_NL_TMPL_T WHERE TYP_ID = (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.notin.class.standing')
/
