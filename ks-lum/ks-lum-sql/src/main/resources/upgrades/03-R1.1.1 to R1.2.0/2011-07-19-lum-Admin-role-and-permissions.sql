-- Create a new Permission template that is linked to the new Kim Typ that is specific to KS-SYS
  INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR)
  VALUES ('Y','19','KS Admin Screens','KS-SYS','eb16081a-49e3-430c-9851-1c3e50806058','4001',1)
/
 -- Create the new permission 
  INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR,NM, desc_txt)
  VALUES ('Y','KS-SYS','068ab713-372d-4bff-94e2-8806bb54b003','3201','4001',1,'KS Admin Screens','Determine availability of admin screens')
/
--- Create the value for the permission, ie the screen component name
  INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('4000','useCurriculumReview','115','19','fc29c39d-e478-45c5-b646-88930762f721','3201',1)
/
  -- Create the KS ADMIIN role
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT) values
('7001', '7f49d87c-5837-4b15-959e-ea2e3fd7dfe6','1','KS Admin Role', 'KS-SYS','KS Admin Role : KS Admin Screens', '1','Y',null) -- Default or should it be 18???
/ 
  -- Create the link between the role and the permission
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','cca43719-a3aa-4dbb-8bd0-d5b02f9352a3','3201','7001','6001',1)
/
  -- add link to admin person to the new KS ADMIN ROLE role 
INSERT INTO KRIM_ROLE_MBR_T (MBR_ID,MBR_TYP_CD,OBJ_ID,ROLE_ID,ROLE_MBR_ID,VER_NBR) 
  VALUES ('5000','P','357007f5-4793-46e5-bd67-4d1dc4a14e7a','7001','admin',1);
/
-- Create permission for CourseWorkflowActionList...
-- Create the new permission 
  INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR,NM, desc_txt)
  VALUES ('Y','KS-SYS','7983fc6e-50ec-4278-8009-03dc7d2f777e','3202','4001',1,'KS Admin Course Workflow','Determine availability of Course Workflow Action List Menu')
/
--- Create the value for the permission, ie the screen component name
  INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR)
  VALUES ('4000','cluModifyItem','115','19','de7477c5-9197-4357-a26d-82fe0abe351d','3202',1)
/
  -- Create the link between the role and the permission
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','cd698882-8b60-4fb5-8141-5a974d0fc91d','3202','7001','6002',1)
/