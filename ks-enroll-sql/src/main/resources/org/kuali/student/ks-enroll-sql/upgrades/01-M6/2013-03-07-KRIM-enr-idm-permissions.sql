--KSENROLL-5843 add grant role perm to ENR IDM role and add martha to ENR IDM role

insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
  values ('KS-KRIM-PERM-1069', SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Assign Role' and nmspc_cd = 'KR-IDM'), 'KS-ENR', 'Assign Roles for KS-ENR Roles', 'Allows the user to Assign Roles for KS-ENR Roles', 'Y')
/

insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  values ('KS-KRIM-PERM-ATTR-DATA-1161', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Assign Roles for KS-ENR Roles' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Role' and nmspc_cd = 'KR-IDM'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'namespaceCode' and nmspc_cd = 'KR-NS'), 'KS-ENR')
/

insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  values ('KS-KRIM-ROLE-PERM-1084', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Enrollment IDM Administrator' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Assign Roles for KS-ENR Roles' and nmspc_cd = 'KS-ENR'), 'Y')
/

insert into KRIM_ROLE_MBR_T (ROLE_MBR_ID, VER_NBR, OBJ_ID, ROLE_ID, MBR_ID, MBR_TYP_CD, ACTV_FRM_DT, ACTV_TO_DT, LAST_UPDT_DT)
  values ('KS-KRIM-ROLE-MBR-1002', 1, SYS_GUID(), (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Enrollment IDM Administrator' and nmspc_cd = 'KS-ENR'), 'martha', 'P', '', '', TO_DATE('10/24/2012', 'MM/DD/YYYY'))
/

--Lock down who can edit permissions
--Create two new permissions to lock down all KRIM document initialization, and a specific one for role documents
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
  values ('KS-KRIM-PERM-1070', SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Initiate Document' and nmspc_cd = 'KR-SYS'), 'KS-ENR', 'Initiate Documents for IdentityManagementDocument', 'Allows the user to Initiate Documents for IdentityManagementDocument', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
  values ('KS-KRIM-PERM-1071', SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Initiate Document' and nmspc_cd = 'KR-SYS'), 'KS-ENR', 'Initiate Documents for IdentityManagementRoleDocument', 'Allows the user to Initiate Documents for IdentityManagementRoleDocument', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  values ('KS-KRIM-PERM-ATTR-DATA-1162', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Initiate Documents for IdentityManagementDocument' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Document Type & Routing Node or State' and nmspc_cd = 'KR-SYS'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'documentTypeName' and nmspc_cd = 'KR-WKFLW'), 'IdentityManagementDocument')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  values ('KS-KRIM-PERM-ATTR-DATA-1163', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Initiate Documents for IdentityManagementRoleDocument' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Document Type & Routing Node or State' and nmspc_cd = 'KR-SYS'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'documentTypeName' and nmspc_cd = 'KR-WKFLW'), 'IdentityManagementRoleDocument')
/
--Assign all permissions to tech admin, and only one to enrollment admin
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  values ('KS-KRIM-ROLE-PERM-1085', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'Technical Administrator' and nmspc_cd = 'KR-SYS'), (SELECT perm_id from krim_perm_t where nm = 'Initiate Documents for IdentityManagementDocument' and nmspc_cd = 'KS-ENR'), 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  values ('KS-KRIM-ROLE-PERM-1086', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'Technical Administrator' and nmspc_cd = 'KR-SYS'), (SELECT perm_id from krim_perm_t where nm = 'Initiate Documents for IdentityManagementRoleDocument' and nmspc_cd = 'KS-ENR'), 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  values ('KS-KRIM-ROLE-PERM-1087', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Enrollment IDM Administrator' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Initiate Documents for IdentityManagementRoleDocument' and nmspc_cd = 'KS-ENR'), 'Y')
/