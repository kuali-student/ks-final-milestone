-- *** KS WORKFLOW ROLE TYPES ***
-- Add all these role types only if intend to use all these roles in your institutional workflow

--College User Role Type
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR) 
  VALUES ('Y', KRIM_TYP_ID_S.NEXTVAL, 'College Type','KS-LUM',sys_guid(),'kimRoleTypeService',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,LBL,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','org.kuali.rice.student.bo.KualiStudentKimAttributes',KRIM_ATTR_DEFN_ID_S.NEXTVAL,'college','College','KS-LUM',sys_guid(),1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR) 
  VALUES ('Y',KRIM_ATTR_DEFN_ID_S.CURRVAL,KRIM_TYP_ATTR_ID_S.NEXTVAL,KRIM_TYP_ID_S.CURRVAL,sys_guid(),'a',1)
/


--Department User Role Type
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR) 
  VALUES ('Y',KRIM_TYP_ID_S.NEXTVAL,'Department Type','KS-LUM',sys_guid(),'kimRoleTypeService',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,LBL,NMSPC_CD,OBJ_ID,VER_NBR) 
  VALUES ('Y','org.kuali.rice.student.bo.KualiStudentKimAttributes',KRIM_ATTR_DEFN_ID_S.NEXTVAL,'department','Department','KS-LUM',sys_guid(),1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR) 
  VALUES ('Y',KRIM_ATTR_DEFN_ID_S.CURRVAL,KRIM_TYP_ATTR_ID_S.NEXTVAL,KRIM_TYP_ID_S.CURRVAL,sys_guid(),'a',1)
/

--Division User Role Type
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR) 
  VALUES ('Y',KRIM_TYP_ID_S.NEXTVAL,'Division Type','KS-LUM',sys_guid(),'kimRoleTypeService',1)
/
INSERT INTO KRIM_ATTR_DEFN_T (ACTV_IND,CMPNT_NM,KIM_ATTR_DEFN_ID,NM,LBL,NMSPC_CD,OBJ_ID,VER_NBR) 
  VALUES ('Y','org.kuali.rice.student.bo.KualiStudentKimAttributes',KRIM_ATTR_DEFN_ID_S.NEXTVAL,'division','Division','KS-LUM',sys_guid(),1)
/
INSERT INTO KRIM_TYP_ATTR_T (ACTV_IND,KIM_ATTR_DEFN_ID,KIM_TYP_ATTR_ID,KIM_TYP_ID,OBJ_ID,SORT_CD,VER_NBR) 
  VALUES ('Y',KRIM_ATTR_DEFN_ID_S.CURRVAL,KRIM_TYP_ATTR_ID_S.NEXTVAL,KRIM_TYP_ID_S.CURRVAL,sys_guid(),'a',1)
/

--OrgAdmin dervied role Type
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR) 
  VALUES ('Y',KRIM_TYP_ID_S.NEXTVAL,'Derived Role: Org Admin Type','KS-SYS',sys_guid(),'ksOrgAdminRoleTypeService',1)
/

--OrgCommittee derived role Type  
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR) 
  VALUES ('Y',KRIM_TYP_ID_S.NEXTVAL,'Derived Role: Org Committee Type','KS-SYS',sys_guid(),'ksOrgCommitteeRoleTypeService',1)
/
