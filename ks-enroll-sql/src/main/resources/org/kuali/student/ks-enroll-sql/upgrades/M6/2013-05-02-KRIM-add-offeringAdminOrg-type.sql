--Create type/attributes for offeringAdminOrgId
insert into KRIM_ATTR_DEFN_T (KIM_ATTR_DEFN_ID, OBJ_ID, VER_NBR, NM, LBL, ACTV_IND, NMSPC_CD, CMPNT_NM) values ('KS-KRIM-ATTR-DEFN-1002', SYS_GUID(), 1, 'offeringAdminOrgId', 'CO Admin Org Id', 'Y', 'KS-ENR', 'org.kuali.rice.student.bo.KualiStudentKimAttributes')
/
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD) values ('KS-KRIM-TYP-1010', SYS_GUID(), 1, 'KS CO Admin Org Role Type', 'kimRoleTypeService', 'Y', 'KS-ENR')
/
insert into KRIM_TYP_ATTR_T (KIM_TYP_ATTR_ID, OBJ_ID, VER_NBR, SORT_CD, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ACTV_IND) values ('KS-KRIM-TYP-ATTR-1026', SYS_GUID(), 1, 'a', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='KS CO Admin Org Role Type'), (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM='offeringAdminOrgId' and NMSPC_CD='KS-ENR'), 'Y')
/
--Update the 'KS Department Schedule Coordinator - Org' role to point to the new type
update KRIM_ROLE_T set KIM_TYP_ID=(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='KS CO Admin Org Role Type' AND NMSPC_CD='KS-ENR') where ROLE_NM='KS Department Schedule Coordinator - Org' AND NMSPC_CD='KS-ENR'
/
--Translate attributes to new format
update KRIM_ROLE_MBR_ATTR_DATA_T set KIM_TYP_ID=(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='KS CO Admin Org Role Type' AND NMSPC_CD='KS-ENR'), KIM_ATTR_DEFN_ID=(SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM='offeringAdminOrgId' and NMSPC_CD='KS-ENR')
where ROLE_MBR_ID in (select ROLE_MBR_ID from KRIM_ROLE_MBR_T where ROLE_ID = (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM='KS Department Schedule Coordinator - Org' AND NMSPC_CD='KS-ENR')) AND KIM_ATTR_DEFN_ID IN (SELECT
KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM='org' and NMSPC_CD='KS-SYS')
/
--Remove extra role-member attributes
DELETE FROM KRIM_ROLE_MBR_ATTR_DATA_T
where ROLE_MBR_ID in (select ROLE_MBR_ID from KRIM_ROLE_MBR_T where ROLE_ID = (SELECT ROLE_ID FROM KRIM_ROLE_T where ROLE_NM='KS Department Schedule Coordinator - Org' AND NMSPC_CD='KS-ENR')) AND KIM_ATTR_DEFN_ID IN (SELECT
KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T WHERE NM='descendHierarchy' and NMSPC_CD='KS-SYS')
/