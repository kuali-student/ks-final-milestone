-- assigning the new 'add a comment' permission to the existing permission derived role 'FYI Request Recipient'
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  VALUES (CONCAT('KS-KRIM-ROLE-PERM-',KRIM_ROLE_PERM_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'Derived Role: FYI Request Recipient' and nmspc_cd = 'KS-SYS'), (SELECT perm_id from krim_perm_t where nm = 'Add a Comment' and nmspc_cd = 'KS-CM'), 'Y')
/

INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  VALUES (CONCAT('KS-KRIM-ROLE-PERM-',KRIM_ROLE_PERM_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'Completed FYI Request Recipient' and nmspc_cd = 'KS-SYS'), (SELECT perm_id from krim_perm_t where nm = 'Add a Comment' and nmspc_cd = 'KS-CM'), 'Y')
/
