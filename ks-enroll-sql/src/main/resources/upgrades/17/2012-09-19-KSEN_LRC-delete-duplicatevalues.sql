--Remove duplicate result values and orphaned result values
DELETE
FROM
    KSEN_LRC_RVG_RESULT_VALUE
WHERE
    RESULT_VALUE_ID IN
    (
        SELECT
            v1.id
        FROM
            KSEN_LRC_RVG r,
            KSEN_LRC_RESULT_VALUE v1,
            KSEN_LRC_RESULT_VALUE v2,
            KSEN_LRC_RVG_RESULT_VALUE j1,
            KSEN_LRC_RVG_RESULT_VALUE j2
        WHERE
            j1.RVG_ID=r.id
        AND j1.RESULT_VALUE_ID = v1.ID
        AND j2.RVG_ID=r.id
        AND j2.RESULT_VALUE_ID = v2.ID
        AND v1.id!=v2.id
        AND v1.NAME=v2.NAME
        AND v1.CREATEID='SYSTEMLOADER'
    )
/
DELETE
FROM
    KSEN_LRC_RESULT_VALUE v
WHERE
    NOT EXISTS
    (
        SELECT
            *
        FROM
            KSEN_LRC_RVG_RESULT_VALUE
        WHERE
            v.ID=RESULT_VALUE_ID
    )
/
