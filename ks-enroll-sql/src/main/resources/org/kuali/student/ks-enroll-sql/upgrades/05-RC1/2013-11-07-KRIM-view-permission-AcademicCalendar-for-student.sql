-- KSENROLL-10051
-- View permission for Academic Calendar for student
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values (KRIM_ROLE_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Student' and nmspc_cd = 'KS-SYS'), (SELECT perm_id from krim_perm_t where nm = 'View Group for View Academic Calendar' and nmspc_cd = 'KS-ENR'), 'Y')
/