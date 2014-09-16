update KRIM_PERM_TMPL_T set ACTV_IND='N' where NMSPC_CD='KS-SYS' and NM='Blanket Approve'
/
update KRIM_PERM_T set ACTV_IND = 'N' where PERM_TMPL_ID in (select PERM_TMPL_ID from KRIM_PERM_TMPL_T where NMSPC_CD='KS-SYS' and NM='Blanket Approve')
/

insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
  values (CONCAT('KS-KRIM-PERM-',KRIM_PERM_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t WHERE nm = 'Blanket Approve Document' AND nmspc_cd = 'KR-WKFLW'), 'KS-CM', 'Blanket Approve - Curriculum Management', 'Blanket Approval for Curriculum Management Documents', 'Y')
/

insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  values (CONCAT('KS-KRIM-PERM-ATTR-DATA-',KRIM_ATTR_DATA_ID_S.NEXTVAL), SYS_GUID(), 1, (select perm_id from krim_perm_t where nm = 'Blanket Approve - Curriculum Management' and nmspc_cd = 'KS-CM'), (select kim_typ_id from krim_perm_tmpl_t where nm = 'Blanket Approve Document' and nmspc_cd = 'KR-WKFLW'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'documentTypeName' and nmspc_cd = 'KR-WKFLW'), 'CluParentDocument')
/

insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  values (CONCAT('KS-KRIM-ROLE-PERM-',KRIM_ROLE_PERM_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'Curriculum Specialist' and nmspc_cd = 'KS-SYS'), (SELECT perm_id from krim_perm_t where nm = 'Blanket Approve - Curriculum Management' and nmspc_cd = 'KS-CM'), 'Y')
/
