insert into KRIM_PERM_TMPL_T (PERM_TMPL_ID, OBJ_ID, VER_NBR, NMSPC_CD, NM, DESC_TXT, KIM_TYP_ID, ACTV_IND)
  values (CONCAT('KS-KRIM-PERM-TMPL-',KRIM_PERM_TMPL_ID_S.NEXTVAL), SYS_GUID(), 1, 'KS-SYS', 'Curriculum Specialist', 'Template to define who can act as a Curriculum Specialist', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Document Type (Permission)' AND NMSPC_CD='KR-SYS'), 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
  values (CONCAT('KS-KRIM-PERM-',KRIM_PERM_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Curriculum Specialist' and nmspc_cd = 'KS-SYS'), 'KS-CM', 'Curriculum Specialist for CM Documents', 'Permission that allows users to act as Curriculum Specialist users in Curriculum Management', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  values (CONCAT('KS-KRIM-PERM-ATTR-DATA-',KRIM_ATTR_DATA_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Curriculum Specialist for CM Documents' and nmspc_cd = 'KS-CM'), (SELECT kim_typ_id FROM krim_perm_tmpl_t where nm = 'Curriculum Specialist' and nmspc_cd = 'KS-SYS'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'documentTypeName' and nmspc_cd = 'KR-WKFLW'), 'CluParentDocument')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  values (CONCAT('KS-KRIM-ROLE-PERM-',KRIM_ROLE_PERM_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'Curriculum Specialist' and nmspc_cd = 'KS-SYS'), (SELECT perm_id from krim_perm_t where nm = 'Curriculum Specialist for CM Documents' and nmspc_cd = 'KS-CM'), 'Y')
/