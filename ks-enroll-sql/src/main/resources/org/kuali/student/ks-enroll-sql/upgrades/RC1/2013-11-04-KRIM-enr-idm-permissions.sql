
--Add user admin to the role KS Enrollment IDM Administrator

insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT) values (KRIM_ROLE_MBR_ID_S.NEXTVAL, 1, SYS_GUID(),
(SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Enrollment IDM Administrator' and nmspc_cd = 'KS-ENR'), 'admin', 'P', '', '', TO_DATE('11/04/2013', 'MM/DD/YYYY'))
/