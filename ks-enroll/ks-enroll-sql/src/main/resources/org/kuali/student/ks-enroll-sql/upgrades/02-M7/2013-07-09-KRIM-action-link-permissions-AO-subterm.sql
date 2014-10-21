-- KSENROLL-7469
-- AO Subterm link for DSC  --
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values ('KS-KRIM-PERM-1104', SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Perform Action' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Perform Action to Change Activity Offering Subterm', 'Allows the user to Change Activity Offering Subterm', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-1228', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Change Activity Offering Subterm' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'ActivityOffering-MaintenanceView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-1229', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Change Activity Offering Subterm' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'actionEvent' and nmspc_cd = 'KR-KRAD'), 'changeSubtermAO')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-1230', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Change Activity Offering Subterm' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'KS ENR Permission Expression' and nmspc_cd = 'KS-ENR'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'permissionExpression' and nmspc_cd = 'KS-ENR'), '{"open","finaledits"}.contains(#socState)')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values ('KS-KRIM-ROLE-PERM-1148', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Org' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Change Activity Offering Subterm' and nmspc_cd = 'KS-ENR'), 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values ('KS-KRIM-ROLE-PERM-1149', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Subj' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Change Activity Offering Subterm' and nmspc_cd = 'KS-ENR'), 'Y')
/

-- AO Subterm link for Admin --
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values ('KS-KRIM-PERM-1105', SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Perform Action' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Perform Action to Change Activity Offering Subterm for Admin', 'Allows the Admin to to Change Activity Offering Subterm', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-1231', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Change Activity Offering Subterm for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'ActivityOffering-MaintenanceView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-1232', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Change Activity Offering Subterm for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'actionEvent' and nmspc_cd = 'KR-KRAD'), 'changeSubtermAO')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values ('KS-KRIM-ROLE-PERM-1150', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Change Activity Offering Subterm for Admin' and nmspc_cd = 'KS-ENR'), 'Y')
/