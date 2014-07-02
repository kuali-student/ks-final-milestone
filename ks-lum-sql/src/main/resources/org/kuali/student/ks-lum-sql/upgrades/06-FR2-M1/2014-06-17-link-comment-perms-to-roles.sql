-- assigning the new 'add a comment' permission to the existing permission derived role 'document editor'
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  VALUES (CONCAT('KS-KRIM-ROLE-PERM-',KRIM_ROLE_PERM_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'Document Editor' and nmspc_cd = 'KR-NS'), (SELECT perm_id from krim_perm_t where nm = 'Add a Comment' and nmspc_cd = 'KS-CM'), 'Y')
/

-- assigning the new 'edit a comment' permission to the new ks derived role 'comment author'
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  VALUES (CONCAT('KS-KRIM-ROLE-PERM-',KRIM_ROLE_PERM_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'Comment Author' and nmspc_cd = 'KS-SYS'), (SELECT perm_id from krim_perm_t where nm = 'Edit a Comment' and nmspc_cd = 'KS-CM'), 'Y')
/

-- assigning the new 'delete a comment' permission to the new ks derived role 'comment author'
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  VALUES (CONCAT('KS-KRIM-ROLE-PERM-',KRIM_ROLE_PERM_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'Comment Author' and nmspc_cd = 'KS-SYS'), (SELECT perm_id from krim_perm_t where nm = 'Delete a Comment' and nmspc_cd = 'KS-CM'), 'Y')
/
