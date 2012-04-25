-- KSCM-1423: Save Document Permission was never added, was never joined to role and the 
-- KIM_PRIORITY_ON_DOC_TYP_PERMS_IND was set to N instead of Y. 
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','Allows Save Document KS LUM Document','Save Document','KS-LUM',SYS_GUID(),KRIM_PERM_ID_S.NEXTVAL,
  (select PERM_TMPL_ID from KRIM_PERM_TMPL_T where NM = 'Save Document' and NMSPC_CD='KR-WKFLW'),1)
/

INSERT INTO KRIM_PERM_ATTR_DATA_T 
         (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES (KRIM_ATTR_DATA_ID_S.NEXTVAL,'KualiStudentDocument',
  (Select KIM_ATTR_DEFN_ID from KRIM_ATTR_DEFN_T where NM='documentTypeName' and NMSPC_CD='KR-WKFLW'), 
(Select KIM_TYP_ID from KRIM_TYP_T where NM='Document Type (Permission)' and NMSPC_CD='KR-SYS'),sys_guid(),KRIM_PERM_ID_S.CURRVAL,1)
/

INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y',sys_guid(),KRIM_PERM_ID_S.CURRVAL,
  (select ROLE_ID from KRIM_ROLE_T where ROLE_NM='User' and NMSPC_CD='KUALI'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/

UPDATE KRNS_PARM_T set TXT='Y' where NMSPC_CD='KR-WKFLW' and PARM_DTL_TYP_CD='All' and PARM_NM='KIM_PRIORITY_ON_DOC_TYP_PERMS_IND' and APPL_NMSPC_CD='KUALI'
/