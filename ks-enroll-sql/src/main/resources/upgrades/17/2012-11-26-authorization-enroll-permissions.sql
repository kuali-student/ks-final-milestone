INSERT INTO KRCR_NMSPC_T (NMSPC_CD, OBJ_ID, VER_NBR, NM, ACTV_IND, APPL_ID) VALUES ('KS-ENR',  sys_guid(), 1, 'Kuali Student System', 'Y', 'STUDENT')
/
insert into krim_perm_t
(perm_id, perm_tmpl_id, nmspc_cd, nm, desc_txt, actv_ind, ver_nbr, obj_id)
values (KRIM_PERM_ID_S.NEXTVAL,
        (select perm_tmpl_id from krim_perm_tmpl_t where nm = 'Open View' and nmspc_cd = 'KR-KRAD'),
        'KS-ENR', 'Open Manage CO View', 'Allows users to open Manage Course Offerings','Y',1,sys_guid())
/
insert into krim_perm_attr_data_t
(attr_data_id, perm_id, kim_typ_id, kim_attr_defn_id, attr_val, ver_nbr, obj_id)
values (KRIM_ATTR_DATA_ID_S.NEXTVAL,
        (select perm_id from krim_perm_t where nm = 'Open Manage CO View' and nmspc_cd = 'KS-ENR'),
        (select kim_typ_id from krim_typ_t where nm = 'View' and nmspc_cd = 'KR-KRAD'),
        (select MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and NMSPC_CD='KR-KRAD'),
        'courseOfferingManagementView',1,sys_guid())
/
insert into krim_perm_t
(perm_id, perm_tmpl_id, nmspc_cd, nm, desc_txt, actv_ind, ver_nbr, obj_id)
values (KRIM_PERM_ID_S.NEXTVAL,
        (select perm_tmpl_id from krim_perm_tmpl_t where nm = 'Edit View' and nmspc_cd = 'KR-KRAD'),
        'KS-ENR', 'Edit Manage CO View', 'Allows users to edit Manage Course Offerings','Y',1,sys_guid())
/
insert into krim_perm_attr_data_t
(attr_data_id, perm_id, kim_typ_id, kim_attr_defn_id, attr_val, ver_nbr, obj_id)
values (KRIM_ATTR_DATA_ID_S.NEXTVAL,
        (select perm_id from krim_perm_t where nm = 'Edit Manage CO View' and nmspc_cd = 'KS-ENR'),
        (select kim_typ_id from krim_typ_t where nm = 'View' and nmspc_cd = 'KR-KRAD'),
        (select MIN(TO_NUMBER(kim_attr_defn_id)) from krim_attr_defn_t where nm = 'viewId' and NMSPC_CD='KR-KRAD'),
        'courseOfferingManagementView',1,sys_guid())
/