--
-- Copyright 2010 The Kuali Foundation Licensed under the
-- Educational Community License, Version 2.0 (the "License"); you may
-- not use this file except in compliance with the License. You may
-- obtain a copy of the License at
--
-- http://www.osedu.org/licenses/ECL-2.0
--
-- Unless required by applicable law or agreed to in writing,
-- software distributed under the License is distributed on an "AS IS"
-- BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
-- or implied. See the License for the specific language governing
-- permissions and limitations under the License.
--

-- Hierarchy dervied role type service
INSERT INTO KRIM_TYP_T (ACTV_IND,KIM_TYP_ID,NM,NMSPC_CD,OBJ_ID,SRVC_NM,VER_NBR)
  VALUES ('Y','200','Hierarchy Derived Role Type','KS-SYS','HIERARCHYDERIVED00000000Q0000TYP','ksHierarchyDerivedRoleType',1);

-- Hierarchy derived role
INSERT INTO KRIM_ROLE_T (ACTV_IND,KIM_TYP_ID,NMSPC_CD,OBJ_ID,ROLE_ID,ROLE_NM,VER_NBR, DESC_TXT) 
  VALUES ('Y','200','KS-SYS','HIERARCHYReviewer000000Q0000ROLE','800','Hierarchy Derived Role',1, 'Role to Test the Hierarchy type routing functions'); 

-- Responsibility for test dummy document node
INSERT INTO KRIM_RSP_T (RSP_ID,NMSPC_CD,NM,ACTV_IND,RSP_TMPL_ID,VER_NBR,DESC_TXT,OBJ_ID) 
  VALUES ('100','KS-SYS','Review','Y','1',0,'Responsibility for Hierarchy Node','DepartmentReview0000QQQ000000RSP');
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('100','KualiStudentDocument','13','7','DepartmentReview000QQ00RSPATTR01','100',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('101','HierarchyNode','16','7','DepartmentReview000QQ00RSPATTR02','100',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('102','false','41','7','DepartmentReview00QQQ00RSPATTR03','100',1);
INSERT INTO KRIM_RSP_ATTR_DATA_T (ATTR_DATA_ID,ATTR_VAL,KIM_ATTR_DEFN_ID,KIM_TYP_ID,OBJ_ID,RSP_ID,VER_NBR) 
  VALUES ('103','false','40','7','DepartmentReview00QQQQ0RSPATTR04','100',1);

-- linking of responsibility and hierarchy role
INSERT INTO KRIM_ROLE_RSP_T (ACTV_IND,OBJ_ID,RSP_ID,ROLE_ID,ROLE_RSP_ID,VER_NBR) 
  VALUES ('Y','DepartmentReview00QQQ00ROLERSP01','100','800','100',1);
INSERT INTO KRIM_ROLE_RSP_ACTN_T (ROLE_RSP_ACTN_ID,OBJ_ID,VER_NBR,ACTN_TYP_CD,ACTN_PLCY_CD,ROLE_MBR_ID,ROLE_RSP_ID,FRC_ACTN) 
  VALUES ('100','DepartmentReview0Q0ROLERSPACTN01',1,'A','F','*','100','Y');
