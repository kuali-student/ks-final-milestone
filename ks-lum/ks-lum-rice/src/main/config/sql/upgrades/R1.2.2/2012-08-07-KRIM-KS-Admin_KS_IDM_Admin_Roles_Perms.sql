-- Kuali Student Admin Role (For exception routing, not to be confused with existing Kuali Student CM Admin role)
INSERT INTO KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
  VALUES (KRIM_ROLE_ID_S.NEXTVAL, sys_guid(),'1','Kuali Student Admin', 'KS-SYS','This is a general KS admin role, default role for KS exception routing responsibility', '1','Y',null)
/

-- Kuali Student IDM Admin Role
INSERT INTO KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT)
  VALUES (KRIM_ROLE_ID_S.NEXTVAL, sys_guid(),'1','Kuali Student IDM Admin', 'KS-SYS','This role provides admin ability to grant roles/permissions for KS name space', '1','Y',null)
/


-- *** Assign KIM Management Permissions for KS Namespace to KS IDM Admin Role ***

-- Grant KS KIM Assign Role permission to KS IDM Admin Role
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y',sys_guid(),(SELECT PERM_ID FROM KRIM_PERM_T WHERE KRIM_PERM_T.NMSPC_CD='KS-SYS' AND NM='Assign Role'),(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM='Kuali Student IDM Admin'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/

-- Grant KIM Grant Permission Permission to KS IDM Admin Role
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y',sys_guid(),(SELECT PERM_ID FROM KRIM_PERM_T WHERE KRIM_PERM_T.NMSPC_CD='KS-SYS' AND NM='Grant Permission'),(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM='Kuali Student IDM Admin'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/

-- Grant KIM Grant Responsibility Permission to KS IDM Admin Role
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y',sys_guid(),(SELECT PERM_ID FROM KRIM_PERM_T WHERE KRIM_PERM_T.NMSPC_CD='KS-SYS' AND NM='Grant Responsibility'),(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM='Kuali Student IDM Admin'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/

-- Grant KIM Populate Group Permission to KS IDM Admin Role
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y',sys_guid(),(SELECT PERM_ID FROM KRIM_PERM_T WHERE KRIM_PERM_T.NMSPC_CD='KS-SYS' AND NM='Populate Group'),(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM='Kuali Student IDM Admin'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/


-- *** KS Workflow Exception Routing Responsibility to KS Admin Role ***

--Create responsibility for KS exception routing
INSERT INTO KRIM_RSP_T (RSP_ID,NMSPC_CD,NM,ACTV_IND,RSP_TMPL_ID,VER_NBR,DESC_TXT,OBJ_ID) 
VALUES (KRIM_RSP_ID_S.NEXTVAL,'KS-SYS','Resolve KS Exception','Y','2',1,'Responsibility for Kuali Student Exception Routing',SYS_GUID())
/

-- Set responsibility attribute to apply to only KualiStudentDocuments
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
VALUES (KRIM_ATTR_DATA_ID_S.NEXTVAL,'KualiStudentDocument','13','54',sys_guid(),KRIM_RSP_ID_S.CURRVAL,1)
/

--Assign responsibility to Kuali Student Admin Role
INSERT INTO KRIM_ROLE_RSP_T (ROLE_RSP_ID, OBJ_ID, VER_NBR, ROLE_ID, RSP_ID, ACTV_IND)
VALUES (KRIM_ROLE_RSP_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM='Kuali Student Admin'), KRIM_RSP_ID_S.CURRVAL, 'Y')
/
--Set responsibility action for Kuali Student Admin Role
INSERT INTO KRIM_ROLE_RSP_ACTN_T (ROLE_RSP_ACTN_ID, OBJ_ID, VER_NBR, ACTN_TYP_CD, PRIORITY_NBR, ACTN_PLCY_CD, ROLE_MBR_ID, ROLE_RSP_ID, FRC_ACTN)
VALUES (KRIM_ROLE_RSP_ACTN_ID_S.NEXTVAL, SYS_GUID(), 1, 'A', null, 'F', '*', krim_role_rsp_id_s.currval, 'Y')
/
--Assign responsibility to KR-SYS Technical Admin
INSERT INTO KRIM_ROLE_RSP_T (ROLE_RSP_ID, OBJ_ID, VER_NBR, ROLE_ID, RSP_ID, ACTV_IND)
VALUES (KRIM_ROLE_RSP_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM='Technical Administrator' AND NMSPC_CD='KR-SYS'), KRIM_RSP_ID_S.CURRVAL, 'Y')
/
--Set responsibility action for KR-SYS Technical Admin role
INSERT INTO KRIM_ROLE_RSP_ACTN_T (ROLE_RSP_ACTN_ID, OBJ_ID, VER_NBR, ACTN_TYP_CD, PRIORITY_NBR, ACTN_PLCY_CD, ROLE_MBR_ID, ROLE_RSP_ID, FRC_ACTN)
VALUES (KRIM_ROLE_RSP_ACTN_ID_S.NEXTVAL, SYS_GUID(), 1, 'A', null, 'F', '*', krim_role_rsp_id_s.currval, 'Y')
/


