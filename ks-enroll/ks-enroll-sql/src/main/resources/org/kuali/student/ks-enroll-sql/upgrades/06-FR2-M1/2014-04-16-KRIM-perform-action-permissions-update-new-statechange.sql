--KSENROLL-12195

-- AO delete: allow DSC to delete an offered AO when SOC is in "open" or "finaledits" --
DELETE FROM KRIM_PERM_ATTR_DATA_T WHERE ATTR_DATA_ID='KS-KRIM-PERM-ATTR-DATA-1021'
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  VALUES (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action for Deleting Activity Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'KS ENR Permission Expression' and nmspc_cd = 'KS-ENR'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'permissionExpression' and nmspc_cd = 'KS-ENR'), '({"open","finaledits"}.contains(#socState) and {"draft","approved","canceled","suspended","offered"}.contains(#aoState)) or ({"published"}.contains(#socState) and {"draft"}.contains(#aoState) and #termClassStartDateLater=="true")')
/

-- CO delete: allow DSC to delete an offered CO when SOC is in "open" or "finaledits"  --
DELETE FROM KRIM_PERM_ATTR_DATA_T WHERE ATTR_DATA_ID='KS-KRIM-PERM-ATTR-DATA-1018'
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  VALUES (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action for Deleting Course Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'KS ENR Permission Expression' and nmspc_cd = 'KS-ENR'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'permissionExpression' and nmspc_cd = 'KS-ENR'), '({"open","finaledits"}.contains(#socState) and {"draft","planned","suspended","cancelled","offered"}.contains(#coState)) or ({"published"}.contains(#socState) and {"draft"}.contains(#coState) and #termClassStartDateLater=="true") or ({"published"}.contains(#socState) and {"planned"}.contains(#coState) and #termRegStartDateLater=="true" and #termClassStartDateLater=="true")')
/

-- AO approve: allow DSC to approve an AO when SOC is in "open","finaledits" or"published" --
DELETE FROM KRIM_PERM_ATTR_DATA_T WHERE ATTR_DATA_ID='KS-KRIM-PERM-ATTR-DATA-1058'
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  VALUES (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Actions for Approve Activity Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'KS ENR Permission Expression' and nmspc_cd = 'KS-ENR' ), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'permissionExpression' and nmspc_cd = 'KS-ENR'), '({"open","finaledits","published"}.contains(#socState) and #aoState=="draft")')
/

-- CO approve: allow DSC to approve an CO when SOC is in "open","finaledits" or"published" --
DELETE FROM KRIM_PERM_ATTR_DATA_T WHERE ATTR_DATA_ID='KS-KRIM-PERM-ATTR-DATA-1031'
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL)
  VALUES (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Actions for Approve Course Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'KS ENR Permission Expression' and nmspc_cd = 'KS-ENR' ), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'permissionExpression' and nmspc_cd = 'KS-ENR'), '{"open","finaledits","published"}.contains(#socState)')
/