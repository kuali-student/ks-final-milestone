-- Create a new Permission template that is linked to the new Kim Typ that is specific to KS-SYS
INSERT INTO KRIM_PERM_TMPL_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,PERM_TMPL_ID,VER_NBR, DESC_TXT)
  VALUES ('Y',(SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Default' AND NMSPC_CD='KUALI'),'Add Collaborator Action','KS-SYS',SYS_GUID(),KRIM_PERM_TMPL_ID_S.NEXTVAL,1, 'Used to define what actions a user can assign to collaborators')
/

--create three permissions - one for each action (FYI, Acknowledge, and Approve)
INSERT INTO KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NM='Add Collaborator Action' AND NMSPC_CD='KS-SYS'), 'KS-SYS', 'Add Collaborator with FYI', null, 'Y')
/
INSERT INTO KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NM='Add Collaborator Action' AND NMSPC_CD='KS-SYS'), 'KS-SYS', 'Add Collaborator with Acknowledge', null, 'Y')
/
INSERT INTO KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values (KRIM_PERM_ID_S.NEXTVAL, SYS_GUID(), 1, (SELECT PERM_TMPL_ID FROM KRIM_PERM_TMPL_T WHERE NM='Add Collaborator Action' AND NMSPC_CD='KS-SYS'), 'KS-SYS', 'Add Collaborator with Approve', null, 'Y')
/

-- Attach FYI Collab perm to roles
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR) VALUES ('Y',sys_guid(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD='KS-SYS' AND NM='Add Collaborator with FYI'),(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM='Derived Role: Document Editor' AND NMSPC_CD='KS-SYS'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/

-- Attach Acknowledge Collab perm to roles
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR) VALUES ('Y',sys_guid(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD='KS-SYS' AND NM='Add Collaborator with Acknowledge'),(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM='Derived Role: Document Editor' AND NMSPC_CD='KS-SYS'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/

-- Collab Role Types
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD) values (KRIM_TYP_ID_S.NEXTVAL, sys_guid(), 1, 'Derived Role: KS Completed Adhoc Action Request Role Type', 'ksAdhocDoneActionRequestDerivedRoleTypeServiceImpl', 'Y', 'KS-SYS')
/
insert into KRIM_TYP_T (KIM_TYP_ID, OBJ_ID, VER_NBR, NM, SRVC_NM, ACTV_IND, NMSPC_CD) values (KRIM_TYP_ID_S.NEXTVAL, sys_guid(), 1, 'Derived Role: KS Completed Non-Adhoc Action Request Role Type', 'ksNonAdhocDoneActionRequestDerivedRoleTypeServiceImpl', 'Y', 'KS-SYS')
/

--Create Collab Roles
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT) values (KRIM_ROLE_ID_S.NEXTVAL, sys_guid(), 1, 'Completed Approve Request Recipient', 'KS-SYS', 'Role to find users who have an Approve action request that had been active and is now complete.', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Derived Role: KS Completed Non-Adhoc Action Request Role Type' AND NMSPC_CD='KS-SYS'), 'Y', SYSDATE)
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT) values (KRIM_ROLE_ID_S.NEXTVAL, sys_guid(), 1, 'Completed Acknowledge Request Recipient', 'KS-SYS', 'Role to find users who have an Acknowledge action request that had been active and is now complete.', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Derived Role: KS Completed Non-Adhoc Action Request Role Type' AND NMSPC_CD='KS-SYS'), 'Y', SYSDATE)
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT) values (KRIM_ROLE_ID_S.NEXTVAL, sys_guid(), 1, 'Completed FYI Request Recipient', 'KS-SYS', 'Role to find users who have an FYI action request that had been active and is now complete.', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Derived Role: KS Completed Non-Adhoc Action Request Role Type' AND NMSPC_CD='KS-SYS'), 'Y', SYSDATE)
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT) values (KRIM_ROLE_ID_S.NEXTVAL, sys_guid(), 1, 'Completed Adhoc Approve Request Recipient', 'KS-SYS', 'Role to find users who have an Adhoc Approve action request that had been active and is now complete.', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Derived Role: KS Completed Adhoc Action Request Role Type' AND NMSPC_CD='KS-SYS'), 'Y', SYSDATE)
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT) values (KRIM_ROLE_ID_S.NEXTVAL, sys_guid(), 1, 'Completed Adhoc Acknowledge Request Recipient', 'KS-SYS', 'Role to find users who have an Adhoc Acknowledge action request that had been active and is now complete.', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Derived Role: KS Completed Adhoc Action Request Role Type' AND NMSPC_CD='KS-SYS'), 'Y', SYSDATE)
/
insert into KRIM_ROLE_T (ROLE_ID, OBJ_ID, VER_NBR, ROLE_NM, NMSPC_CD, DESC_TXT, KIM_TYP_ID, ACTV_IND, LAST_UPDT_DT) values (KRIM_ROLE_ID_S.NEXTVAL, sys_guid(), 1, 'Completed Adhoc FYI Request Recipient', 'KS-SYS', 'Role to find users who have an Adhoc FYI action request that had been active and is now complete.', (SELECT KIM_TYP_ID FROM KRIM_TYP_T WHERE NM='Derived Role: KS Completed Adhoc Action Request Role Type' AND NMSPC_CD='KS-SYS'), 'Y', SYSDATE)
/

-- Assign comment on document permissions to those roles
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR) VALUES ('Y',sys_guid(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD='KS-LUM' AND NM='Comment on Document'),(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM='Completed Approve Request Recipient' AND NMSPC_CD='KS-SYS'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR) VALUES ('Y',sys_guid(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD='KS-LUM' AND NM='Comment on Document'),(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM='Completed Acknowledge Request Recipient' AND NMSPC_CD='KS-SYS'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR) VALUES ('Y',sys_guid(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD='KS-LUM' AND NM='Comment on Document'),(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM='Completed FYI Request Recipient' AND NMSPC_CD='KS-SYS'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR) VALUES ('Y',sys_guid(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD='KS-LUM' AND NM='Comment on Document'),(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM='Completed Adhoc Approve Request Recipient' AND NMSPC_CD='KS-SYS'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR) VALUES ('Y',sys_guid(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD='KS-LUM' AND NM='Comment on Document'),(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM='Completed Adhoc Acknowledge Request Recipient' AND NMSPC_CD='KS-SYS'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR) VALUES ('Y',sys_guid(), (SELECT PERM_ID FROM KRIM_PERM_T WHERE NMSPC_CD='KS-LUM' AND NM='Comment on Document'),(SELECT ROLE_ID FROM KRIM_ROLE_T WHERE ROLE_NM='Completed Adhoc FYI Request Recipient' AND NMSPC_CD='KS-SYS'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/