--Namespace
insert into KRCR_NMSPC_T (NMSPC_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, APPL_ID) values ('KS-ENR', SYS_GUID(), 1, 'Kuali Student Enrollment', 'Y', 'STUDENT')
/

--Permissions
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Open View' and nmspc_cd = 'KR-KRAD'), 'KS-ENR', 'Open Views for Course Offering', 'Allows the user to Open Views for Course Offering', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Edit View' and nmspc_cd = 'KR-KRAD'), 'KS-ENR', 'Edit Views for Course Offering', 'Allows the user to Edit Views for Course Offering', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Open View' and nmspc_cd = 'KR-KRAD'), 'KS-ENR', 'Open Views for Manage Registration Groups', 'Allows the user to Open Views for  Manage Registration Groups', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Edit View' and nmspc_cd = 'KR-KRAD'), 'KS-ENR', 'Edit Views for Manage Registration Groups', 'Allows the user to Edit Views for  Manage Registration Groups', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Open View' and nmspc_cd = 'KR-KRAD'), 'KS-ENR', 'Open Views for Create Course Offering', 'Allows the user to Open Views for Create Course Offering', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Edit View' and nmspc_cd = 'KR-KRAD'), 'KS-ENR', 'Edit Views for Create Course Offering', 'Allows the user to Edit Views for Create Course Offering', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Edit Document' and nmspc_cd = 'KR-NS'), 'KS-ENR', 'Edit Views for KS-CourseOfferingEditWrapper-EditMaintenanceView', 'Allows the user to Edit Views for Edit Course Offering', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Open Document' and nmspc_cd = 'KR-NS'), 'KS-ENR', 'Open Views for KS-CourseOfferingEditWrapper-EditMaintenanceView', 'Allows the user to Open documents for Open Course Offering', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Edit Document' and nmspc_cd = 'KR-NS'), 'KS-ENR', 'Edit Views for ActivityOffering-MaintenanceView', 'Allows the user to Edit Views for Edit Activity Offering', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Open Document' and nmspc_cd = 'KR-NS'), 'KS-ENR', 'Open Views for ActivityOffering-MaintenanceView', 'Allows the user to Open documents for Open Activity Offering', 'Y')
/

--Permission Details
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open Views for Course Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'courseOfferingManagementView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Edit Views for Course Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'courseOfferingManagementView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open Views for Manage Registration Groups' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'manageRegistrationGroupsView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Edit Views for Manage Registration Groups' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'manageRegistrationGroupsView')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open Views for Create Course Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'createCourseOfferingPage')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Edit Views for Create Course Offering' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and nmspc_cd = 'KR-KRAD'), 'createCourseOfferingPage')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Edit Views for KS-CourseOfferingEditWrapper-EditMaintenanceView' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'documentTypeName' and nmspc_cd = 'KR-WKFLW'), 'CourseOfferingEditMaintenanceDocument')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open Views for KS-CourseOfferingEditWrapper-EditMaintenanceView' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'documentTypeName' and nmspc_cd = 'KR-WKFLW'), 'CourseOfferingEditMaintenanceDocument')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Edit Views for ActivityOffering-MaintenanceView' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'documentTypeName' and nmspc_cd = 'KR-WKFLW'), 'ActivityOfferingInfoMaintenanceDocument')
/
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT perm_id from krim_perm_t where nm = 'Open Views for ActivityOffering-MaintenanceView' and nmspc_cd = 'KS-ENR'), (SELECT kim_typ_id from krim_typ_t where nm = 'Default' and nmspc_cd = 'KUALI'), (SELECT MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'documentTypeName' and nmspc_cd = 'KR-WKFLW'), 'ActivityOfferingInfoMaintenanceDocument')
/
