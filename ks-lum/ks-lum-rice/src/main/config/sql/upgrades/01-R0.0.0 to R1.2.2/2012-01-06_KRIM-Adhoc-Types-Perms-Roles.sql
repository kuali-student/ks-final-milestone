-- *** KS DERIVED ROLE PERMISSION TYPES ***

-- Derived Role: KS Adhoc Action Request Role Type Permission Type
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR) 
  VALUES ('Y',KRIM_TYP_ID_S.NEXTVAL,'Derived Role: KS Adhoc Action Request Role Type','KS-SYS',sys_guid(),'ksAdhocActionRequestDerivedRoleTypeServiceImpl',1)
/

-- Adhoc Permission Type
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR) 
  VALUES ('Y',KRIM_TYP_ID_S.NEXTVAL,'Adhoc Permissions Type','KS-SYS',sys_guid(),'kimRoleTypeService',1)
/


-- *** KS ATTRIBUTE DEFINITIONS ***
-- Data Object Id Attribute (used when creating adhoc qualifiers)
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR,LBL) 
  VALUES ('Y','org.kuali.rice.student.bo.KualiStudentKimAttributes',KRIM_ATTR_DEFN_ID_S.NEXTVAL,'dataId','KS-SYS',sys_guid(),1, 'Data Object Id')
/

-- Organization Attribute
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR,LBL) 
  VALUES ('Y','org.kuali.rice.student.bo.KualiStudentKimAttributes',KRIM_ATTR_DEFN_ID_S.NEXTVAL,'org','KS-SYS',sys_guid(),1, 'Organization')
/

-- Section Id Attribute
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR,LBL) 
  VALUES ('Y','org.kuali.rice.student.bo.KualiStudentKimAttributes',KRIM_ATTR_DEFN_ID_S.NEXTVAL,'sectionId','KS-SYS',sys_guid(),1, 'Section Id')
/

-- Reference Type Key Attribute
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR,LBL) 
  VALUES ('Y','org.kuali.rice.student.bo.KualiStudentKimAttributes',KRIM_ATTR_DEFN_ID_S.NEXTVAL,'ksReferenceTypeKey','KS-SYS',sys_guid(),1, 'Reference Type Key')
/

-- *** KS ROLES ***

-- Adhoc Approve Request Recipient
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR,DESC_TXT) 
  VALUES ('Y',(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Derived Role: KS Adhoc Action Request Role Type') ,'KS-SYS',sys_guid(),KRIM_ROLE_ID_S.NEXTVAL,'Adhoc Approve Request Recipient',1,'This role is used to adhoc add an user as an approve recepient')
/

-- 	Adhoc Acknowledge Request Recipient
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR,DESC_TXT) 
  VALUES ('Y',(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Derived Role: KS Adhoc Action Request Role Type') ,'KS-SYS',sys_guid(),KRIM_ROLE_ID_S.NEXTVAL,'Adhoc Acknowledge Request Recipient',1,'This role is used to adhoc add an user as a acknowledge request recepient')
/

-- 	Adhoc FYI Request Recipient
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR,DESC_TXT) 
  VALUES ('Y',(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Derived Role: KS Adhoc Action Request Role Type') ,'KS-SYS',sys_guid(),KRIM_ROLE_ID_S.NEXTVAL,'Adhoc FYI Request Recipient',1,'This role is used to adhoc add an user as a FYI request recepient')
/

-- 	Adhoc Permissions: Edit Document
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR,DESC_TXT) 
  VALUES ('Y',(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Adhoc Permissions Type') ,'KS-SYS',sys_guid(),KRIM_ROLE_ID_S.NEXTVAL,'Adhoc Permissions: Edit Document',1,'This role is for use with adding adhoc permissions for those who can edit a document.')
/

-- 	Adhoc Permissions: Comment on Document
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR,DESC_TXT) 
  VALUES ('Y',(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Adhoc Permissions Type') ,'KS-SYS',sys_guid(),KRIM_ROLE_ID_S.NEXTVAL,'Adhoc Permissions: Comment on Document',1,'This role is for use with adding adhoc permissions for those who can comment on a document.')
/


-- *** KS PERMISSIONS (& ROLE ASSIGNMENTS)***

-- Assign Edit Document Permission to Adhoc Permissions: Edit Document role
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y',sys_guid(),(SELECT PERM_ID FROM KRIM_PERM_T WHERE NM='Edit Document' AND NMSPC_CD='KS-LUM'),(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM='Adhoc Permissions: Edit Document' AND NMSPC_CD='KS-SYS'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/

-- Assign Comment Document Permission to Adhoc Permissions: Comment on Document role
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y',sys_guid(),(SELECT PERM_ID FROM KRIM_PERM_T WHERE NM='Comment on Document' AND NMSPC_CD='KS-LUM'),(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM='Adhoc Permissions: Comment on Document' AND NMSPC_CD='KS-SYS'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/


