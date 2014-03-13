--This is to remove the fix that was put in for KSCM-1705.
--Other work was done to properly set up the Edit Document permission for CM Course Proposal objects
update KRIM_TYP_T set SRVC_NM='documentTypeAndNodeOrStatePermissionTypeService' where KIM_TYP_ID in (select KIM_TYP_ID from KRIM_PERM_TMPL_T where NM = 'Edit Document' and NMSPC_CD='KR-NS')
/