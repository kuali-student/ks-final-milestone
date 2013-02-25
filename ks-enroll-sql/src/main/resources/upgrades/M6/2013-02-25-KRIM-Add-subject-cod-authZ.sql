--New KIM Type
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD) values (KRIM_TYP_ID_S.NEXTVAL, SYS_GUID(), 1, 'KS Subject Area Role Type', 'kimRoleTypeService', 'Y', 'KS-SYS')
/
--New Attribute Definition
insert into KRIM_ATTR_DEFN_T (KIM_ATTR_DEFN_ID, OBJ_ID, VER_NBR, NM, LBL, ACTV_IND, NMSPC_CD, CMPNT_NM) values (KRIM_ATTR_DEFN_ID_S.NEXTVAL, SYS_GUID(), 1, 'subjectArea', 'Subject Area', 'Y', 'KS-SYS', 'org.kuali.rice.student.bo.KualiStudentKimAttributes')
/
--Join attribute to type
insert into KRIM_TYP_ATTR_T (KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND) values (KRIM_TYP_ATTR_ID_S.NEXTVAL, SYS_GUID(), 1, 'a', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='KS Subject Area Role Type'), (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM='subjectArea' and NMSPC_CD='KS-SYS'), 'Y')
/
--Update the subject code role to use the new type
update KRIM_ROLE_T SET KIM_TYP_ID = (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='KS Subject Area Role Type') WHERE ROLE_NM = 'KS Department Schedule Coordinator - Subj' AND NMSPC_CD = 'KS-ENR'
/
--Assign carl as a CHEM subject code scheduling admin
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT) values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, SYS_GUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Org View Only' and nmspc_cd = 'KS-ENR'), 'carl', 'P', '', '', TO_DATE('10/24/2012', 'MM/DD/YYYY'))
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT) values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, SYS_GUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Subj' and nmspc_cd = 'KS-ENR'), 'carl', 'P', '', '', TO_DATE('10/24/2012', 'MM/DD/YYYY'))
/
insert into KRIM_ROLE_MBR_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, ROLE_MBR_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL,  SYS_GUID(), 1, (SELECT ROLE_MBR_ID from KRIM_ROLE_MBR_T where ROLE_ID = (SELECT ROLE_ID FROM KRIM_ROLE_T where role_nm = 'KS Department Schedule Coordinator - Subj' and nmspc_cd = 'KS-ENR') AND MBR_ID = 'carl'), (SELECT kim_typ_id from krim_typ_t where nm = 'KS Subject Area Role Type' and nmspc_cd = 'KS-SYS'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'subjectArea' and nmspc_cd = 'KS-SYS'), 'CHEM')
/

--Copy all the permissions from DSC-org to DSC-subj
INSERT
INTO
    KRIM_ROLE_PERM_T
    (
        ROLE_PERM_ID,
        OBJ_ID,
        VER_NBR,
        ROLE_ID,
        PERM_ID,
        ACTV_IND
    )
SELECT
    KRIM_ROLE_PERM_ID_S.NEXTVAL,
    sys_guid(),
    VER_NBR,
    (
        SELECT
            ROLE_ID
        FROM
            KRIM_ROLE_T
        WHERE
            ROLE_NM = 'KS Department Schedule Coordinator - Subj'
        AND nmspc_cd = 'KS-ENR'
    )
    ,
    PERM_ID,
    ACTV_IND
FROM
    KRIM_ROLE_PERM_T
WHERE
    ROLE_ID =
    (
        SELECT
            ROLE_ID
        FROM
            KRIM_ROLE_T
        WHERE
            ROLE_NM = 'KS Department Schedule Coordinator - Org'
        AND nmspc_cd = 'KS-ENR'
    )
/
