--Update the references to rules to match new clus
update KRMS_REF_OBJ_KRMS_OBJ_T o set o.REF_OBJ_ID=
(SELECT
    courses.ID
FROM
    (
        SELECT
            a.REF_OBJ_KRMS_OBJ_ID,
            rownum r
        FROM
            KRMS_REF_OBJ_KRMS_OBJ_T a
        WHERE
            a.ref_dscr_typ='kuali.lu.type.CreditCourse'
    )
    ros,
    (
        SELECT
            s.*,
            rownum r
        FROM
            (
                SELECT
                    ID,
                    VER_IND_ID
                FROM
                    KSLU_CLU c
                WHERE
                    c.LUTYPE_ID='kuali.lu.type.CreditCourse'
                AND c.ST='Active'
                AND c.EXPIR_DT IS NULL
                ORDER BY
                    id ASC
            )
            s
    )
    courses
where courses.r=ros.r
and ros.REF_OBJ_KRMS_OBJ_ID = o.REF_OBJ_KRMS_OBJ_ID)
where o.ref_dscr_typ='kuali.lu.type.CreditCourse'
/
--Update Agenda names to match course codes
UPDATE
    KRMS_AGENDA_T a
SET
    a.NM=
    (
        SELECT
            ci.cd || SUBSTR(a.NM,8)
        FROM
            KRMS_REF_OBJ_KRMS_OBJ_T ro,
            KSLU_CLU c,
            KSLU_CLU_IDENT ci
        WHERE
            a.AGENDA_ID=ro.KRMS_OBJ_ID
        AND c.OFFIC_CLU_ID=ci.ID
        AND c.ID=ro.REF_OBJ_ID
    )
WHERE a.agenda_id in (SELECT ref.krms_obj_id FROM KRMS_REF_OBJ_KRMS_OBJ_T ref WHERE ref.ref_dscr_typ='kuali.lu.type.CreditCourse')
/
