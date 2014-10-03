--KSENROLL-13585 student afflilations (all non instructor, non-admin users should be students)
UPDATE
    KRIM_ENTITY_AFLTN_T
SET
    KRIM_ENTITY_AFLTN_T.AFLTN_TYP_CD = 'STDNT'
WHERE
    KRIM_ENTITY_AFLTN_T.ENTITY_ID IN
    (
        SELECT
            ea.ENTITY_ID
        FROM
            KRIM_ENTITY_AFLTN_T ea,
            KRIM_PRNCPL_T p
        WHERE
            p.ENTITY_ID=ea.ENTITY_ID
        AND NOT EXISTS
            (
                SELECT
                    KRIM_ROLE_MBR_T.MBR_ID
                FROM
                    KRIM_ROLE_MBR_T
                WHERE
                    p.PRNCPL_ID= KRIM_ROLE_MBR_T.MBR_ID)
        AND NOT EXISTS
            (
                SELECT
                    KSEN_LPR.PERS_ID
                FROM
                    KSEN_LPR
                WHERE
                    KSEN_LPR.LPR_TYPE='kuali.lpr.type.instructor.main'
                AND KSEN_LPR.PERS_ID = p.PRNCPL_ID))
/
