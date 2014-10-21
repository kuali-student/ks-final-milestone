-- KSCM-1722 (Implement a Cancel Document permission for Course Proposal objects)

-- CluParentDocument Cancel permission
INSERT INTO KRIM_PERM_T (ACTV_IND,DESC_TXT,NM,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR) VALUES ('Y','Authorizes users to cancel a document prior to it being submitted for routing.','Cancel Document - Curriculum Management','KS-CM',SYS_GUID(),KRIM_PERM_ID_S.NEXTVAL,(select PERM_TMPL_ID from KRIM_PERM_TMPL_T where nm='Cancel Document'),1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR) VALUES (KRIM_ATTR_DATA_ID_S.NEXTVAL,'CluParentDocument','13','8',SYS_GUID(),(select PERM_ID from KRIM_PERM_T where NMSPC_CD='KS-CM' and NM='Cancel Document - Curriculum Management'),1)
/
INSERT INTO KRIM_PERM_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,PERM_ID,VER_NBR) VALUES (KRIM_ATTR_DATA_ID_S.NEXTVAL,'PreRoute','16','8',SYS_GUID(),(select PERM_ID from KRIM_PERM_T where NMSPC_CD='KS-CM' and NM='Cancel Document - Curriculum Management'),1)
/

-- Adding 'Initiator' and 'Document Editor' to the above role
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR) VALUES ('Y',SYS_GUID(),(select PERM_ID from KRIM_PERM_T where NMSPC_CD='KS-CM' and NM='Cancel Document - Curriculum Management'),(select ROLE_ID from KRIM_ROLE_T where NMSPC_CD='KR-NS' and ROLE_NM='Document Editor'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR) VALUES ('Y',SYS_GUID(),(select PERM_ID from KRIM_PERM_T where NMSPC_CD='KS-CM' and NM='Cancel Document - Curriculum Management'),(select ROLE_ID from KRIM_ROLE_T where NMSPC_CD='KR-WKFLW' and ROLE_NM='Initiator'),KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/