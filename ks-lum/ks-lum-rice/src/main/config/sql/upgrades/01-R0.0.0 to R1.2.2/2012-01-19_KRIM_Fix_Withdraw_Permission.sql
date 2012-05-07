--Update the withdraw permission template to use the proper template
UPDATE KRIM_PERM_TMPL_T SET KIM_TYP_ID = (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Document Type & Routing Node or State' AND NMSPC_CD='KR-SYS') WHERE NM='Withdraw Document' AND NMSPC_CD='KS-SYS'
/
-- This will allow for the system user to administer routing which will fix the withdrawl error
--Make a new permission for KS administer routing	
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NM='Administer Routing for Document' AND NMSPC_CD='KR-WKFLW'), 'KS-SYS', 'Administer Routing for Document', null, 'Y')
/
-- add the parent KS doc type to the permission
insert into KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID, OBJ_ID, VER_NBR, PERM_ID, KIM_TYP_ID, KIM_ATTR_DEFN_ID, ATTR_VAL) values (KRIM_ATTR_DATA_ID_S.NEXTVAL, SYS_GUID(), 1, KRIM_PERM_ID_S.CURRVAL, (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Document Type (Permission)' AND NMSPC_CD='KR-SYS'), (SELECT KIM_ATTR_DEFN_ID FROM KRIM_ATTR_DEFN_T where NM='documentTypeName' AND NMSPC_CD='KR-WKFLW'), 'KualiStudentDocument')
/
-- assign the permission to the system role
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y',sys_guid(), KRIM_PERM_ID_S.CURRVAL,(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM='Student System User Role' AND NMSPC_CD='KS-SYS'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/
