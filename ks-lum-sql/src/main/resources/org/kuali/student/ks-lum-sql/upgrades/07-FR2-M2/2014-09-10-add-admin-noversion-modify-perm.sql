INSERT INTO KRIM_PERM_T (ACTV_IND, DESC_TXT, NM, NMSPC_CD, OBJ_ID, PERM_ID, PERM_TMPL_ID, VER_NBR)
  VALUES ('Y', 'Authorizes users to Initiate an Admin Modify (No New Version) document.', 'Modify Course By Admin Proposal - No Version', 'KS-CM', SYS_GUID(), CONCAT('KS-KRIM-PERM-', KRIM_PERM_ID_S.NEXTVAL), (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NM = 'Initiate Document' AND NMSPC_CD = 'KR-SYS'), 1)
/

insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  values (CONCAT('KS-KRIM-PERM-ATTR-DATA-',KRIM_ATTR_DATA_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Modify Course By Admin Proposal - No Version' and nmspc_cd = 'KS-CM'), (SELECT kim_typ_id FROM KRIM_PERM_TMPL_T WHERE NM = 'Initiate Document' AND NMSPC_CD = 'KR-SYS'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'documentTypeName' and nmspc_cd = 'KR-WKFLW'), 'kuali.proposal.type.course.modify.admin.noversion')
/

insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  values (CONCAT('KS-KRIM-ROLE-PERM-',KRIM_ROLE_PERM_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'Curriculum Specialist' and nmspc_cd = 'KS-SYS'), (SELECT perm_id FROM krim_perm_t WHERE nm = 'Modify Course By Admin Proposal - No Version' and nmspc_cd = 'KS-CM'), 'Y')
/