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
/
--update the term params to match existing clus
UPDATE
    KRMS_TERM_PARM_T o
SET
    o.VAL=
    (
        SELECT
            courses.ID
        FROM
            (
                SELECT
                    a.TERM_PARM_ID,
                    rownum r
                FROM
                    KRMS_TERM_PARM_T a
                WHERE
                    a.NM='kuali.reqComponent.field.type.course.clu.id'
            )
            tps,
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
        WHERE
            courses.r=tps.r+(SELECT count(*) from KRMS_REF_OBJ_KRMS_OBJ_T)
        AND tps.TERM_PARM_ID = o.TERM_PARM_ID
    )
WHERE
    NM='kuali.reqComponent.field.type.course.clu.id'
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
/
