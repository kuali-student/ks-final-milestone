--This is a temporary fix for KSCM-1705. This is to make sure the Initiator has permission to edit their own document.
--Once we setup authz for course, this needs revisit based on the setup.
update KRIM_TYP_T set SRVC_NM='documentTypePermissionTypeService' where KIM_TYP_ID in (select KIM_TYP_ID from KRIM_PERM_TMPL_T where NM = 'Edit Document' and NMSPC_CD='KR-NS')
/