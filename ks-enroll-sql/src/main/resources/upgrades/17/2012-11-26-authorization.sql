INSERT INTO KRCR_NMSPC_T (NMSPC_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, APPL_ID) VALUES ('KS-ENR',  sys_guid(), 1, 'Kuali Student System', 'Y', 'STUDENT')
/
insert into krim_perm_t
(perm_id, perm_tmpl_id, nmspc_cd, nm, desc_txt, actv_ind, ver_nbr, obj_id)
values (KRIM_PERM_ID_S.NEXTVAL,
        (select perm_tmpl_id from krim_perm_tmpl_t where nm = 'Open View' and nmspc_cd = 'KR-KRAD'),
        'KS-ENR', 'Open Manage CO View', 'Allows users to open Manage Course Offerings','Y',1,sys_guid())
/
insert into krim_perm_attr_data_t
(attr_data_id, perm_id, kim_typ_id, kim_attr_defn_id, attr_val, ver_nbr, obj_id)
values (KRIM_ATTR_DATA_ID_S.NEXTVAL,
        (select perm_id from krim_perm_t where nm = 'Open Manage CO View' and nmspc_cd = 'KS-ENR'),
        (select kim_typ_id from krim_typ_t where nm = 'View' and nmspc_cd = 'KR-KRAD'),
        (select MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and NMSPC_CD='KR-KRAD'),
        'courseOfferingManagementView',1,sys_guid())
/
insert into krim_perm_t
(perm_id, perm_tmpl_id, nmspc_cd, nm, desc_txt, actv_ind, ver_nbr, obj_id)
values (KRIM_PERM_ID_S.NEXTVAL,
        (select perm_tmpl_id from krim_perm_tmpl_t where nm = 'Edit View' and nmspc_cd = 'KR-KRAD'),
        'KS-ENR', 'Edit Manage CO View', 'Allows users to edit Manage Course Offerings','Y',1,sys_guid())
/
insert into krim_perm_attr_data_t
(attr_data_id, perm_id, kim_typ_id, kim_attr_defn_id, attr_val, ver_nbr, obj_id)
values (KRIM_ATTR_DATA_ID_S.NEXTVAL,
        (select perm_id from krim_perm_t where nm = 'Edit Manage CO View' and nmspc_cd = 'KS-ENR'),
        (select kim_typ_id from krim_typ_t where nm = 'View' and nmspc_cd = 'KR-KRAD'),
        (select MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and NMSPC_CD='KR-KRAD'),
        'courseOfferingManagementView',1,sys_guid())
/
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
