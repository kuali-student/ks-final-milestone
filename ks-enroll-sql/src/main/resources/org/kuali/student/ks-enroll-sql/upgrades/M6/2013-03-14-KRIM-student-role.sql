--KS Student Role
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
  values ('KS-KRIM-ROLE-1000',  SYS_GUID(), 1, 'KS Student', 'KS-SYS', 'A student ', '1', 'Y', TO_DATE ('11/26/2012', 'MM/DD/YYYY') )
/
insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
  values ('KS-KRIM-ROLE-MBR-1003', 1, SYS_GUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Student' and nmspc_cd = 'KS-SYS'), 'student', 'P', '', '', TO_DATE('10/24/2012', 'MM/DD/YYYY'))
/
