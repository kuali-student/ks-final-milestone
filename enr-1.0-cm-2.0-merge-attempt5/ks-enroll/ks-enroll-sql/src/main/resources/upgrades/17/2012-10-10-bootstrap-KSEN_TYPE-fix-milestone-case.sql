--Delete any duplicates that exist in both cases
delete
FROM
    KSEN_TYPE t1
WHERE
    TYPE_KEY LIKE '%.Milestone.%'
AND EXISTS
    (
        SELECT
            *
        FROM
            KSEN_TYPE t2
        WHERE
            t2.TYPE_KEY=REGEXP_REPLACE(t1.TYPE_KEY,'(.*)\.Milestone\.(.*)','\1.milestone.\2')
    )
/
--Insert lowercase versions
INSERT
INTO
    KSEN_TYPE
    (
        TYPE_KEY,
        OBJ_ID,
        NAME,
        DESCR_PLAIN,
        DESCR_FORMATTED,
        EFF_DT,
        EXPIR_DT,
        REF_OBJECT_URI,
        SERVICE_URI,
        VER_NBR,
        CREATETIME,
        CREATEID,
        UPDATETIME,
        UPDATEID
    )
SELECT
    REGEXP_REPLACE(TYPE_KEY,'(.*)\.Milestone\.(.*)','\1.milestone.\2'),
    OBJ_ID,
    NAME,
    DESCR_PLAIN,
    DESCR_FORMATTED,
    EFF_DT,
    EXPIR_DT,
    REF_OBJECT_URI,
    SERVICE_URI,
    VER_NBR,
    CREATETIME,
    CREATEID,
    UPDATETIME,
    UPDATEID
FROM
    KSEN_TYPE
WHERE
    KSEN_TYPE.TYPE_KEY LIKE '%.Milestone.%'
    /
--Delete the uppercase versions
DELETE
FROM
    KSEN_TYPE
WHERE
    KSEN_TYPE.TYPE_KEY LIKE '%.Milestone.%'
    /
