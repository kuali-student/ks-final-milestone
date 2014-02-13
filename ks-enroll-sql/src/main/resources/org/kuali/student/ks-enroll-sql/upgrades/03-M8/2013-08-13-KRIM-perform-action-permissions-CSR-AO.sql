-- CSR authz were defined in M6 although expression is wrong
delete from KRIM_ROLE_PERM_T where PERM_ID in ('KS-KRIM-PERM-1028', 'KS-KRIM-PERM-1034', 'KS-KRIM-PERM-1027', 'KS-KRIM-PERM-1033', 'KS-KRIM-PERM-1029', 'KS-KRIM-PERM-1035')
/
delete from KRIM_PERM_ATTR_DATA_T where PERM_ID in ('KS-KRIM-PERM-1028', 'KS-KRIM-PERM-1034', 'KS-KRIM-PERM-1027', 'KS-KRIM-PERM-1033', 'KS-KRIM-PERM-1029', 'KS-KRIM-PERM-1035')
/
delete from KRIM_PERM_T where PERM_ID in ('KS-KRIM-PERM-1028', 'KS-KRIM-PERM-1034', 'KS-KRIM-PERM-1027', 'KS-KRIM-PERM-1033', 'KS-KRIM-PERM-1029', 'KS-KRIM-PERM-1035')
/

-- KSENROLL-8577 Cancel AO --
-- Carol
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Perform Action' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Perform Action to Cancel Activity Offering', 'Allows the user to Cancel Activity Offering', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Cancel Activity Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'courseOfferingManagementView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Cancel Activity Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'actionEvent' and nmspc_cd = 'KR-KRAD'), 'cancelAO')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Cancel Activity Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'KS ENR Permission Expression' and nmspc_cd = 'KS-ENR'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'permissionExpression' and nmspc_cd = 'KS-ENR'), '{"open","finaledits"}.contains(#socState)')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Org' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Cancel Activity Offering' and nmspc_cd = 'KS-ENR'), 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Subj' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Cancel Activity Offering' and nmspc_cd = 'KS-ENR'), 'Y')
/
-- Admin
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Perform Action' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Perform Action to Cancel Activity Offering for Admin', 'Allows the Admin to Cancel Activity Offering', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Cancel Activity Offering for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'courseOfferingManagementView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Cancel Activity Offering for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'actionEvent' and nmspc_cd = 'KR-KRAD'), 'cancelAO')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Cancel Activity Offering for Admin' and nmspc_cd = 'KS-ENR'), 'Y')
/

-- KSENROLL-8646 Suspend AO --
-- Carol
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Perform Action' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Perform Action to Suspend Activity Offering', 'Allows the user to Suspend Activity Offering', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Suspend Activity Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'courseOfferingManagementView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Suspend Activity Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'actionEvent' and nmspc_cd = 'KR-KRAD'), 'suspendAO')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Suspend Activity Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'KS ENR Permission Expression' and nmspc_cd = 'KS-ENR'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'permissionExpression' and nmspc_cd = 'KS-ENR'), '#socState=="finaledits"')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Org' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Suspend Activity Offering' and nmspc_cd = 'KS-ENR'), 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Subj' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Suspend Activity Offering' and nmspc_cd = 'KS-ENR'), 'Y')
/
-- Admin
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Perform Action' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Perform Action to Suspend Activity Offering for Admin', 'Allows the Admin to Suspend Activity Offering', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Suspend Activity Offering for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'courseOfferingManagementView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Suspend Activity Offering for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'actionEvent' and nmspc_cd = 'KR-KRAD'), 'suspendAO')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Suspend Activity Offering for Admin' and nmspc_cd = 'KS-ENR'), 'Y')
/

-- KSENROLL-8645 Reinstate AO --
-- Carol
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Perform Action' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Perform Action to Reinstate Activity Offering', 'Allows the user to Reinstate Activity Offering', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Reinstate Activity Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'courseOfferingManagementView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Reinstate Activity Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'actionEvent' and nmspc_cd = 'KR-KRAD'), 'reinstateAO')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Reinstate Activity Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'KS ENR Permission Expression' and nmspc_cd = 'KS-ENR'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'permissionExpression' and nmspc_cd = 'KS-ENR'), '{"open","finaledits"}.contains(#socState)')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Org' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Reinstate Activity Offering' and nmspc_cd = 'KS-ENR'), 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Subj' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Reinstate Activity Offering' and nmspc_cd = 'KS-ENR'), 'Y')
/
-- Admin
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Perform Action' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Perform Action to Reinstate Activity Offering for Admin', 'Allows the Admin to Reinstate Activity Offering', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Reinstate Activity Offering for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'courseOfferingManagementView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Reinstate Activity Offering for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'actionEvent' and nmspc_cd = 'KR-KRAD'), 'reinstateAO')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values (CONCAT('KS-', KS_RICE_ID_S.NEXTVAL), SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Perform Action to Reinstate Activity Offering for Admin' and nmspc_cd = 'KS-ENR'), 'Y')
/
