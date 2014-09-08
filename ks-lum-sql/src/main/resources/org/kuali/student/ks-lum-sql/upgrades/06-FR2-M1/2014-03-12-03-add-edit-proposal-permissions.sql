insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
  values (CONCAT('KS-KRIM-PERM-',KRIM_PERM_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Edit Document' and nmspc_cd = 'KR-NS'), 'KS-CM', 'Edit Document for Course Proposals', 'Permission that allows users to edit any Course Proposal document based on document type', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  values (CONCAT('KS-KRIM-PERM-ATTR-DATA-',KRIM_ATTR_DATA_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Edit Document for Course Proposals' and nmspc_cd = 'KS-CM'), (SELECT kim_typ_id from krim_typ_t where nm = 'Document Type & Routing Node or State' and nmspc_cd = 'KR-SYS'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'documentTypeName' and nmspc_cd = 'KR-WKFLW'), 'CluCreditCourseParentDocument')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  values (CONCAT('KS-KRIM-PERM-ATTR-DATA-',KRIM_ATTR_DATA_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Edit Document for Course Proposals' and nmspc_cd = 'KS-CM'), (SELECT kim_typ_id from krim_typ_t where nm = 'Document Type & Routing Node or State' and nmspc_cd = 'KR-SYS'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'routeStatusCode' and nmspc_cd = 'KR-WKFLW'), 'S')
/

insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  values (CONCAT('KS-KRIM-ROLE-PERM-',KRIM_ROLE_PERM_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'Initiator' and nmspc_cd = 'KR-WKFLW'), (SELECT perm_id from krim_perm_t where nm = 'Edit Document for Course Proposals' and nmspc_cd = 'KS-CM'), 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  values (CONCAT('KS-KRIM-ROLE-PERM-',KRIM_ROLE_PERM_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'Curriculum Specialist' and nmspc_cd = 'KS-SYS'), (SELECT perm_id from krim_perm_t where nm = 'Edit Document for Course Proposals' and nmspc_cd = 'KS-CM'), 'Y')
/