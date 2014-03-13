-- we need to alter the existing GWT "KS-SYS/Open Document" permission template so that we can create a new "KS-SYS/Open Document" permission that uses built in KRAD permission type services
update KRIM_PERM_TMPL_T set NM='Open Document (GWT)' where NMSPC_CD='KR-SYS' and NM='Open Document'
/

-- permission for who can open any CM document type document instance (currently being set to allow any principal in the system access)
insert into KRIM_PERM_TMPL_T (PERM_TMPL_ID, OBJ_ID, VER_NBR, NMSPC_CD, NM, DESC_TXT, KIM_TYP_ID, ACTV_IND)
  values (CONCAT('KS-KRIM-PERM-TMPL-',KRIM_PERM_TMPL_ID_S.NEXTVAL), SYS_GUID(), 1, 'KS-SYS', 'Open Document', 'Template to define a custom KS Open Document Permission based on document type and route status', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Document Type & Routing Node or State' AND NMSPC_CD='KR-SYS'), 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
  values (CONCAT('KS-KRIM-PERM-',KRIM_PERM_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Open Document' and nmspc_cd = 'KS-SYS'), 'KS-CM', 'Open Document for CM Documents', 'Permission that allows users to open any CM document based on document type', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  values (CONCAT('KS-KRIM-PERM-ATTR-DATA-',KRIM_ATTR_DATA_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open Document for Course Proposal' and nmspc_cd = 'KS-CM'), (SELECT kim_typ_id from krim_typ_t where nm = 'Document Type & Routing Node or State' and nmspc_cd = 'KR-SYS'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'documentTypeName' and nmspc_cd = 'KR-WKFLW'), 'CluParentDocument')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  values (CONCAT('KS-KRIM-ROLE-PERM-',KRIM_ROLE_PERM_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'User' and nmspc_cd = 'KUALI'), (SELECT perm_id from krim_perm_t where nm = 'Open View for Course Proposal Lookup' and nmspc_cd = 'KS-CM'), 'Y')
/