--NOTE: THIS FILE IS TEMPORARILY LOCATED IN THE UPGRADE FOLDER, NEED TO MOVE BACK TO KS-LUM-RCE

-- Cleanup inserts from 2011-07-06-Final-Status-Viewable-Role-and-Permissions.sql 

--Remove unused KIM Type (This is not used anywhere)
DELETE FROM KRIM_TYP_T WHERE KIM_TYP_ID='3000'
/

--Alter permission template for permission "Open Document Final"
UPDATE KRIM_PERM_T
SET PERM_TMPL_ID='60'
WHERE PERM_ID='3200'
/

--Remove duplicate KS-SYS "Open Document" inserted (we will use existing one with PERM_TMPL_ID=60 defined in kim.sql)  
DELETE FROM KRIM_PERM_TMPL_T WHERE PERM_TMPL_ID='4000'
/

--Update name and description for KS CM Admin Role
UPDATE KRIM_ROLE_T
SET 
ROLE_NM='Kuali Student CM Admin',
DESC_TXT='This role provides adminstrative privileges to KS CM application.(eg. Admin Screens)'
WHERE ROLE_ID='7001'
/

--Update name and description for KS CM User Role
UPDATE KRIM_ROLE_T
SET 
ROLE_NM='Kuali Student CM User',
DESC_TXT='General Kuali Student Curriculum Management User'
WHERE ROLE_ID='899'
/

--Update name for KS Org Admin Role (To avoid confusion with KS CM Admin Role)
UPDATE KRIM_ROLE_T
SET ROLE_NM='Kuali Student Org Admin'
WHERE ROLE_ID='10000'
/

--Update name for KS Admin Screen Permission
UPDATE KRIM_PERM_TMPL_T
SET NM='Use Screen'
WHERE PERM_TMPL_ID='4001'
/

--Create New Intitiate Document Permssions & assign to either KS CM User or KS CM Admin roles
--Create Course Proposal
INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR,NM)
  VALUES ('Y','KS-SYS','KS_SYS_CREATE_COURSE_PROPOSAL',KRIM_PERM_ID_S.NEXTVAL,'10',1, 'Create Course By Proposal')
/
--Assign to user role
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','KS_SYS_USER_CREATE_COURSE_PROPOSALS', KRIM_PERM_ID_S.CURRVAL,'899',KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/
--Create Program Proposal
INSERT INTO KRIM_PERM_T (ACTV_IND,NMSPC_CD,OBJ_ID,PERM_ID,PERM_TMPL_ID,VER_NBR,NM)
  VALUES ('Y','KS-SYS','KS_SYS_CREATE_PROGRAM_PROPOSAL',KRIM_PERM_ID_S.NEXTVAL,'10',1, 'Create Program By Proposal')
/
--Assign to admin role (Do we want to do this for admin)
INSERT INTO KRIM_ROLE_PERM_T (ACTV_IND,OBJ_ID,PERM_ID,ROLE_ID,ROLE_PERM_ID,VER_NBR)
  VALUES ('Y','KS_SYS_ADMIN_CREATE_PGM_PROPOSALS', KRIM_PERM_ID_S.CURRVAL,'7001',KRIM_ROLE_PERM_ID_S.NEXTVAL,1)
/