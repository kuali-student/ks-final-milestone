insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT) values (KRIM_ROLE_ID_S.NEXTVAL,  SYS_GUID(), 1, 'KS IDM Administrator ', 'KS-SYS', 'KS Administrator with the ability to grant and assign permissions and roles for any KS permission namespace.', '1', 'Y', TO_DATE ('11/26/2012', 'MM/DD/YYYY') )
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT) values (KRIM_ROLE_ID_S.NEXTVAL,  SYS_GUID(), 1, 'KS Enrollment System Administrator', 'KS-ENR', 'KS Administrator with global abilities for the entirety of the KS Enrollment Module', '1', 'Y', TO_DATE ('11/26/2012', 'MM/DD/YYYY') )
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT) values (KRIM_ROLE_ID_S.NEXTVAL,  SYS_GUID(), 1, 'KS Enrollment IDM Administrator', 'KS-ENR', 'KS Administrator with the ability to grant and assign permissions and roles for the KS Enrollment Module. ', '1', 'Y', TO_DATE ('11/26/2012', 'MM/DD/YYYY') )
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT) values (KRIM_ROLE_ID_S.NEXTVAL,  SYS_GUID(), 1, 'KS Schedule Coordinator ', 'KS-ENR', 'Central Schedule Coordinator for the Course Offering Process ', '1', 'Y', TO_DATE ('11/26/2012', 'MM/DD/YYYY') )
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT) values (KRIM_ROLE_ID_S.NEXTVAL,  SYS_GUID(), 1, 'KS Department Schedule Coordinator - Org ', 'KS-ENR', 'Department Schedule Coordinator -  restricted by CO administering organization - for the Course Offering Process', '114', 'Y', TO_DATE ('11/26/2012', 'MM/DD/YYYY') )
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT) values (KRIM_ROLE_ID_S.NEXTVAL,  SYS_GUID(), 1, 'KS Department Schedule Coordinator - Subj ', 'KS-ENR', 'Department Schedule Coordinator - restricted by CO subject code - for the Course Offering Process', '1', 'Y', TO_DATE ('11/26/2012', 'MM/DD/YYYY') )
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT) values (KRIM_ROLE_ID_S.NEXTVAL,  SYS_GUID(), 1, 'KS Student ', 'KS-ENR', 'A student ', '1', 'Y', TO_DATE ('11/26/2012', 'MM/DD/YYYY') )
/