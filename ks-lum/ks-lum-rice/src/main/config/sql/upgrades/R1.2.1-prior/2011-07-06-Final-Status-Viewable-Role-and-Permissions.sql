-- Create another routeStatusCode attribute linked to KS-SYS
  INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.kim.bo.impl.KimAttributes','2000','routeStatusCode','KS-SYS','05c1ece3-5d04-44d3-885c-2a90de623ba8',1)
/
-- Create a new KRIM Type that is linked to the Java service documentTypeAndNodeOrStatePermissionTypeService  
  INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','3000','Document Type Routing Node or State','KS-SYS','c4025cc8-ddd6-4a38-b6d2-c75637677810','documentTypeAndNodeOrStatePermissionTypeService',1)
/
-- Create a new Permission template that is linked to the new Kim Typ that is specific to KS-SYS
  INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','8','Open Document','KS-SYS','74491846-9e5c-4d93-ba3b-be8f98c03d7a','4000',1)
/
-- Create the new permission 
  INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR,NM, desc_txt)
  VALUES ('Y','KS-SYS','2357893b-31e3-43d6-9aa2-d615dab32751','3200','4000',1,'Open Document FINAL','Open Document, with status FINAL')
/
  -- Create Type Attribute connection... 
  INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR)
  VALUES (  'Y',  '2000',  '5000',  '8',  '81955ec4-63f0-4e73-8a58-0c79d259ca60',  'c',  1)
/
-- Insert Type for the affiliation service
 INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','3001','Derived Role:Affiliation','KS-SYS','4d19e0c0-34fb-48a9-942e-8a1771c9d4c0','ksAffiliationService',1)
/
  -- Create the derived role
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT) values
('7000', 'c6699d07-33a3-4cf4-b1b6-030a3ca6a05d','1','Derived Role : Affiliation New', 'KS-SYS','Derived Role : Affiliation', '3001','Y',null)
/
-- Create the link between the derived role and the permission
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','0bd38c98-bffa-4a41-b804-07e9b8a06642','3200','7000','6000',1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('20000','F','2000','8','c98a9937-ccf1-4eb7-8523-0a20cc3e38ef','3200',1)
/
  