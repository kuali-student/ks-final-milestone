INSERT INTO KRIM_ROLE_T
    ( ROLE_ID,
        OBJ_ID,
        VER_NBR,
        ROLE_NM,
        NMSPC_CD,
        DESC_TXT,
        KIM_TYP_ID,
        ACTV_IND,
        LAST_UPDT_DT
    )
    VALUES
    (
        KRIM_ROLE_ID_S.NEXTVAL,
        sys_guid(),
        1,
        'KS Schedule Coordinator (open+edit view)',
        'KS-ENR',
        'Central Schedule Coordinator for the Course Offering Process (open+edit view).',
        1,
        'Y',
        SYSDATE
    )
/
INSERT
INTO
    KRIM_ROLE_PERM_T
    (
        ACTV_IND,
        OBJ_ID,
        PERM_ID,
        ROLE_ID,
        ROLE_PERM_ID,
        VER_NBR
    )
    VALUES
    (
        'Y',
        sys_guid(),
        (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM='Open Manage CO View' AND NMSPC_CD='KS-ENR'),
        (
            SELECT
                ROLE_ID
            FROM
                KRIM_ROLE_T
            WHERE
                ROLE_NM='KS Schedule Coordinator (open+edit view)'
            AND NMSPC_CD='KS-ENR'
        )
        ,KRIM_ROLE_PERM_ID_S.NEXTVAL,
        1
    )
/
INSERT
INTO
    KRIM_ROLE_PERM_T
    (
        ACTV_IND,
        OBJ_ID,
        PERM_ID,
        ROLE_ID,
        ROLE_PERM_ID,
        VER_NBR
    )
    VALUES
    (
        'Y',
        sys_guid(),
        (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM='Edit Manage CO View' AND NMSPC_CD='KS-ENR'),
        (
            SELECT
                ROLE_ID
            FROM
                KRIM_ROLE_T
            WHERE
                ROLE_NM='KS Schedule Coordinator (open+edit view)'
            AND NMSPC_CD='KS-ENR'
        ) ,
        KRIM_ROLE_PERM_ID_S.NEXTVAL,
        1
    )
/
INSERT INTO KRIM_ROLE_T
    (   ROLE_ID,
        OBJ_ID,
        VER_NBR,
        ROLE_NM,
        NMSPC_CD,
        DESC_TXT,
        KIM_TYP_ID,
        ACTV_IND,
        LAST_UPDT_DT
    )
    VALUES
    (
        KRIM_ROLE_ID_S.NEXTVAL,
        sys_guid(),
        1,
        'KS Schedule Coordinator (open view only)',
        'KS-ENR',
        'Central Schedule Coordinator for the Course Offering Process (open view only).',
        1,
        'Y',
        SYSDATE
)
/
INSERT INTO KRIM_ROLE_PERM_T
    ( ACTV_IND, OBJ_ID, PERM_ID, ROLE_ID, ROLE_PERM_ID, VER_NBR)
VALUES
    (
        'Y',
        sys_guid(),
        (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM='Open Manage CO View' AND NMSPC_CD='KS-ENR'),
        (SELECT ROLE_ID  FROM KRIM_ROLE_T WHERE ROLE_NM='KS Schedule Coordinator (open view only)' AND NMSPC_CD='KS-ENR'),
        KRIM_ROLE_PERM_ID_S.NEXTVAL,
        1
    )
/
INSERT INTO
    KRIM_ROLE_MBR_T
    (
        MBR_ID,
        MBR_TYP_CD,
        OBJ_ID,
        ROLE_ID,
        ROLE_MBR_ID,
        VER_NBR
    )
    VALUES
    (
        'frank',
        'P',
        SYS_GUID(),
        (
            SELECT
                ROLE_ID
            FROM
                KRIM_ROLE_T
            WHERE
                ROLE_NM='KS Schedule Coordinator (open+edit view)'
            AND NMSPC_CD='KS-ENR'
        )
        ,
        KRIM_ROLE_MBR_ID_S.NEXTVAL,
        1
)
/
INSERT INTO
    KRIM_ROLE_MBR_T
    (
        MBR_ID,
        MBR_TYP_CD,
        OBJ_ID,
        ROLE_ID,
        ROLE_MBR_ID,
        VER_NBR
    )
    VALUES
    (
        'admin',
        'P',
        SYS_GUID(),
        (
            SELECT
                ROLE_ID
            FROM
                KRIM_ROLE_T
            WHERE
                ROLE_NM='KS Schedule Coordinator (open+edit view)'
            AND NMSPC_CD='KS-ENR'
        ),
        KRIM_ROLE_MBR_ID_S.NEXTVAL,
        1
)
/
INSERT INTO
    KRIM_ROLE_MBR_T
    (
        MBR_ID,
        MBR_TYP_CD,
        OBJ_ID,
        ROLE_ID,
        ROLE_MBR_ID,
        VER_NBR
    )
    VALUES
    (
        'eric',
        'P',
        SYS_GUID(),
        (
            SELECT
                ROLE_ID
            FROM
                KRIM_ROLE_T
            WHERE
                ROLE_NM='KS Schedule Coordinator (open view only)'
            AND NMSPC_CD='KS-ENR'
        )
        ,
        KRIM_ROLE_MBR_ID_S.NEXTVAL,
        1
    )
/
