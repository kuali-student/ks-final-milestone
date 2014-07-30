--KSENROLL-13519
INSERT INTO KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
  VALUES (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Perform Action' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'KS Add a Comment', 'KS allows the user to add a comment', 'Y')
/
INSERT INTO KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
  VALUES (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Perform Action' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'KS Add a Comment for Admin', 'KS allows the user to add a comment for admin', 'Y')
/
INSERT INTO KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
  VALUES (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Perform Action' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'KS Edit a Comment', 'KS allows the user to Edit a comment', 'Y')
/
INSERT INTO KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
  VALUES (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Perform Action' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'KS Edit a Comment for Admin', 'KS allows the user to Edit a comment for admin', 'Y')
/
INSERT INTO KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
  VALUES (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Perform Action' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'KS Delete a Comment', 'KS allows the user to Delete a comment', 'Y')
/
INSERT INTO KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND)
  VALUES (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Perform Action' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'KS Delete a Comment for Admin', 'KS allows the user to Delete a comment for admin', 'Y')
/


INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  VALUES (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'KS Add a Comment' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'actionEvent' and nmspc_cd = 'KR-KRAD'), 'ksAddComment')
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  VALUES (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'KS Add a Comment' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'KS ENR Permission Expression' and nmspc_cd = 'KS-ENR' ), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'permissionExpression' and nmspc_cd = 'KS-ENR'), '{"draft","open","locked","finaledits","publishing","published"}.contains(#socState)')
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  VALUES (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'KS Add a Comment for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'actionEvent' and nmspc_cd = 'KR-KRAD'), 'ksAddComment')
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  VALUES (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'KS Edit a Comment' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'actionEvent' and nmspc_cd = 'KR-KRAD'), 'ksEditComment')
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  VALUES (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'KS Edit a Comment' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'KS ENR Permission Expression' and nmspc_cd = 'KS-ENR' ), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'permissionExpression' and nmspc_cd = 'KS-ENR'), '{"draft","open","locked","finaledits","publishing","published"}.contains(#socState)')
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  VALUES (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'KS Edit a Comment for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'actionEvent' and nmspc_cd = 'KR-KRAD'), 'ksEditComment')
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  VALUES (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'KS Delete a Comment' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'actionEvent' and nmspc_cd = 'KR-KRAD'), 'ksDeleteComment')
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  VALUES (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'KS Delete a Comment' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'KS ENR Permission Expression' and nmspc_cd = 'KS-ENR' ), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'permissionExpression' and nmspc_cd = 'KS-ENR'), '{"draft","open","locked","finaledits","publishing","published"}.contains(#socState)')
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  VALUES (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'KS Delete a Comment for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'actionEvent' and nmspc_cd = 'KR-KRAD'), 'ksDeleteComment')
/


INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  VALUES (KRIM_ROLE_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Org' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'KS Add a Comment' and nmspc_cd = 'KS-ENR'), 'Y')
/
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  VALUES (KRIM_ROLE_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Org' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'KS Edit a Comment' and nmspc_cd = 'KS-ENR'), 'Y')
/
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  VALUES (KRIM_ROLE_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Org' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'KS Delete a Comment' and nmspc_cd = 'KS-ENR'), 'Y')
/
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  VALUES (KRIM_ROLE_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'KS Add a Comment for Admin' and nmspc_cd = 'KS-ENR'), 'Y')
/
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  VALUES (KRIM_ROLE_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'KS Edit a Comment for Admin' and nmspc_cd = 'KS-ENR'), 'Y')
/
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND)
  VALUES (KRIM_ROLE_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'KS Delete a Comment for Admin' and nmspc_cd = 'KS-ENR'), 'Y')
/