INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  VALUES (CONCAT('KS-KRIM-ROLE-PERM-',KRIM_ROLE_PERM_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'Curriculum Specialist' and nmspc_cd = 'KS-SYS'), (SELECT perm_id from krim_perm_t where nm = 'Retire Course By Proposal' and nmspc_cd = 'KS-SYS'), 'Y')
/
