-- update the kim permission type services that have a null value to use the specific bean name
update KRIM_TYP_T set SRVC_NM = 'defaultPermissionTypeService' where SRVC_NM is NULL and NMSPC_CD like 'KS-%'
/

-- update the kim permission type services to use a common bean name
update KRIM_TYP_T set SRVC_NM = 'defaultPermissionTypeService' where SRVC_NM='kimPermissionTypeService' and NMSPC_CD like 'KS-%'
/

-- update all the KS Kim Type Services to expose them on the bus with namespaced service names
update KRIM_TYP_T set SRVC_NM = '{http://student.kuali.org/kim}defaultPermissionTypeService' where SRVC_NM='defaultPermissionTypeService' and NMSPC_CD like 'KS-%'
/
update KRIM_TYP_T set SRVC_NM = '{http://student.kuali.org/kim}kimRoleTypeService' where SRVC_NM='kimRoleTypeService' and NMSPC_CD like 'KS-%'
/
update KRIM_TYP_T set SRVC_NM = '{http://student.kuali.org/kim}permissionPermissionTypeService' where SRVC_NM='permissionPermissionTypeService' and NMSPC_CD like 'KS-%'
/
update KRIM_TYP_T set SRVC_NM = '{http://student.kuali.org/kim}ksOrganizationHierarchyRoleTypeService' where SRVC_NM='ksOrganizationHierarchyRoleTypeService' and NMSPC_CD like 'KS-%'
/
update KRIM_TYP_T set SRVC_NM = '{http://student.kuali.org/krad}ksViewActionPermissionTypeService' where SRVC_NM='ksViewActionPermissionTypeService' and NMSPC_CD like 'KS-%'
/
update KRIM_TYP_T set SRVC_NM = '{http://student.kuali.org/krad}ksViewEditModePermissionTypeService' where SRVC_NM='ksViewEditModePermissionTypeService' and NMSPC_CD like 'KS-%'
/
update KRIM_TYP_T set SRVC_NM = '{http://student.kuali.org/krad}ksViewFieldPermissionTypeService' where SRVC_NM='ksViewFieldPermissionTypeService' and NMSPC_CD like 'KS-%'
/
update KRIM_TYP_T set SRVC_NM = '{http://student.kuali.org/krad}ksViewGroupPermissionTypeService' where SRVC_NM='ksViewGroupPermissionTypeService' and NMSPC_CD like 'KS-%'
/
update KRIM_TYP_T set SRVC_NM = '{http://student.kuali.org/krad}ksViewLineActionPermissionTypeService' where SRVC_NM='ksViewLineActionPermissionTypeService' and NMSPC_CD like 'KS-%'
/
update KRIM_TYP_T set SRVC_NM = '{http://student.kuali.org/krad}ksViewLineFieldPermissionTypeService' where SRVC_NM='ksViewLineFieldPermissionTypeService' and NMSPC_CD like 'KS-%'
/
update KRIM_TYP_T set SRVC_NM = '{http://student.kuali.org/krad}ksViewPermissionTypeService' where SRVC_NM='ksViewPermissionTypeService' and NMSPC_CD like 'KS-%'
/
update KRIM_TYP_T set SRVC_NM = '{http://student.kuali.org/krad}ksViewWidgetPermissionTypeService' where SRVC_NM='ksViewWidgetPermissionTypeService' and NMSPC_CD like 'KS-%'
/