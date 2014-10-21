-- KSENROLL-8098
-- deleting existing permission (need different for Admin vs DSC
delete from KRIM_ROLE_PERM_T where PERM_ID = (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Open Views for Create Course Offering' AND NMSPC_CD = 'KS-ENR')
/
delete from KRIM_PERM_ATTR_DATA_T where PERM_ID = (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Open Views for Create Course Offering' AND NMSPC_CD = 'KS-ENR')
/
delete from KRIM_PERM_T where PERM_ID = (SELECT PERM_ID FROM KRIM_PERM_T WHERE NM = 'Open Views for Create Course Offering' AND NMSPC_CD = 'KS-ENR')
/
-- deleting existing permissions for Create CO - not used anymore
delete from KRIM_ROLE_PERM_T where ROLE_PERM_ID in ('KS-KRIM-ROLE-PERM-1060', 'KS-KRIM-ROLE-PERM-1061', 'KS-KRIM-ROLE-PERM-1062')
/
delete from KRIM_PERM_ATTR_DATA_T where ATTR_DATA_ID in ('KS-KRIM-PERM-ATTR-DATA-1139', 'KS-KRIM-PERM-ATTR-DATA-1140', 'KS-KRIM-PERM-ATTR-DATA-1141', 'KS-KRIM-PERM-ATTR-DATA-1142', 'KS-KRIM-PERM-ATTR-DATA-1143')
/
delete from KRIM_PERM_T where PERM_ID in ('KS-KRIM-PERM-1059', 'KS-KRIM-PERM-1060')
/
-- creating new view Create CO permissions for DSC
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values ('KS-KRIM-PERM-3017', SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Open View' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Open View for Create New Course Offering', 'Allows the user to Open View for Create New Course Offering', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-3034', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open View for Create New Course Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'createCourseOfferingPage')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-3035', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open View for Create New Course Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'KS ENR Permission Expression' and nmspc_cd = 'KS-ENR'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'permissionExpression' and nmspc_cd = 'KS-ENR'), '!{"draft","locked","published"}.contains(#socState)')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values ('KS-KRIM-ROLE-PERM-3017', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Org' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Open View for Create New Course Offering' and nmspc_cd = 'KS-ENR'), 'Y')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values ('KS-KRIM-ROLE-PERM-3018', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Department Schedule Coordinator - Subj' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Open View for Create New Course Offering' and nmspc_cd = 'KS-ENR'), 'Y')
/
-- creating new view Create CO permissions for DSC
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values ('KS-KRIM-PERM-3018', SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Open View' and nmspc_cd = 'KS-ENR'), 'KS-ENR', 'Open View for Create New Course Offering for Admin', 'Allows the Admin to Open View for Create New Course Offering', 'Y')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values ('KS-KRIM-PERM-ATTR-DATA-3036', SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open View for Create New Course Offering for Admin' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT kim_attr_defn_id from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'createCourseOfferingPage')
/
insert into KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, VER_NBR, ROLE_ID, PERM_ID, ACTV_IND) values ('KS-KRIM-ROLE-PERM-3019', SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM = 'KS Schedule Coordinator' and nmspc_cd = 'KS-ENR'), (SELECT perm_id from krim_perm_t where nm = 'Open View for Create New Course Offering for Admin' and nmspc_cd = 'KS-ENR'), 'Y')
/
