--KSAP-

--setup simple AcademicPlanService permissions...

--remove any obsolete ksap permission setup data
DELETE from KRIM_ROLE_PERM_T WHERE PERM_ID in
  (select p.PERM_ID from KRIM_PERM_T p WHERE p.NMSPC_CD='KS-AP')
/
DELETE from KRIM_PERM_T WHERE NMSPC_CD='KS-AP'
/
DELETE FROM KRCR_NMSPC_T where NMSPC_CD='KS-AP'
/

INSERT INTO KRCR_NMSPC_T (ACTV_IND,APPL_ID,NM,NMSPC_CD,OBJ_ID,VER_NBR)
  VALUES ('Y','STUDENT','Kuali Student Application Planning','KS-AP','7DD4111C8C7E4ADBB6CDED76B618D08E',1)
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values
('KS-AP-PERM-0001', 'KS-AP-PERM-0001', 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Access Permission' and nmspc_cd = 'KS-SYS'), 'KS-AP', 'AcademicPlanService/getLearningPlan', 'Allows the user to execute the getLearningPlan method of the AcademicPlanService', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values
('KS-AP-PERM-0002', 'KS-AP-PERM-0002', 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Access Permission' and nmspc_cd = 'KS-SYS'), 'KS-AP', 'AcademicPlanService/getPlanItem', 'Allows the user to execute the getPlanItem method of the AcademicPlanService', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values
('KS-AP-PERM-0003', 'KS-AP-PERM-0003', 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Access Permission' and nmspc_cd = 'KS-SYS'), 'KS-AP', 'AcademicPlanService/getLearningPlansByIds', 'Allows the user to execute the getLearningPlansByIds method of the AcademicPlanService', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values
('KS-AP-PERM-0004', 'KS-AP-PERM-0004', 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Access Permission' and nmspc_cd = 'KS-SYS'), 'KS-AP', 'AcademicPlanService/getPlanItemsInPlanByType', 'Allows the user to execute the getPlanItemsInPlanByType method of the AcademicPlanService', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values
('KS-AP-PERM-0005', 'KS-AP-PERM-0005', 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Access Permission' and nmspc_cd = 'KS-SYS'), 'KS-AP', 'AcademicPlanService/getPlanItemsInPlanByCategory', 'Allows the user to execute the getPlanItemsInPlanByCategory method of the AcademicPlanService', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values
('KS-AP-PERM-0006', 'KS-AP-PERM-0006', 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Access Permission' and nmspc_cd = 'KS-SYS'), 'KS-AP', 'AcademicPlanService/getPlanItemsInPlan', 'Allows the user to execute the getPlanItemsInPlan method of the AcademicPlanService', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values
('KS-AP-PERM-0007', 'KS-AP-PERM-0007', 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Access Permission' and nmspc_cd = 'KS-SYS'), 'KS-AP', 'AcademicPlanService/getPlanItemsInPlanByTermIdByCategory', 'Allows the user to execute the getPlanItemsInPlanByTermIdByCategory method of the AcademicPlanService', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values
('KS-AP-PERM-0008', 'KS-AP-PERM-0008', 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Access Permission' and nmspc_cd = 'KS-SYS'), 'KS-AP', 'AcademicPlanService/getPlanItemsInPlanByRefObjectIdByRefObjectType', 'Allows the user to execute the getPlanItemsInPlanByRefObjectIdByRefObjectType method of the AcademicPlanService', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values
('KS-AP-PERM-0009', 'KS-AP-PERM-0009', 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Access Permission' and nmspc_cd = 'KS-SYS'), 'KS-AP', 'AcademicPlanService/getLearningPlansForStudentByType', 'Allows the user to execute the getLearningPlansForStudentByType method of the AcademicPlanService', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values
('KS-AP-PERM-0010', 'KS-AP-PERM-0010', 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Access Permission' and nmspc_cd = 'KS-SYS'), 'KS-AP', 'AcademicPlanService/createLearningPlan', 'Allows the user to execute the createLearningPlan method of the AcademicPlanService', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values
('KS-AP-PERM-0011', 'KS-AP-PERM-0011', 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Access Permission' and nmspc_cd = 'KS-SYS'), 'KS-AP', 'AcademicPlanService/createPlanItem', 'Allows the user to execute the createPlanItem method of the AcademicPlanService', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values
('KS-AP-PERM-0012', 'KS-AP-PERM-0012', 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Access Permission' and nmspc_cd = 'KS-SYS'), 'KS-AP', 'AcademicPlanService/updateLearningPlan', 'Allows the user to execute the updateLearningPlan method of the AcademicPlanService', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values
('KS-AP-PERM-0013', 'KS-AP-PERM-0013', 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Access Permission' and nmspc_cd = 'KS-SYS'), 'KS-AP', 'AcademicPlanService/updatePlanItem', 'Allows the user to execute the updatePlanItem method of the AcademicPlanService', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values
('KS-AP-PERM-0014', 'KS-AP-PERM-0014', 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Access Permission' and nmspc_cd = 'KS-SYS'), 'KS-AP', 'AcademicPlanService/deleteLearningPlan', 'Allows the user to execute the deleteLearningPlan method of the AcademicPlanService', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values
('KS-AP-PERM-0015', 'KS-AP-PERM-0015', 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Access Permission' and nmspc_cd = 'KS-SYS'), 'KS-AP', 'AcademicPlanService/deletePlanItem', 'Allows the user to execute the deletePlanItem method of the AcademicPlanService', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values
('KS-AP-PERM-0016', 'KS-AP-PERM-0016', 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Access Permission' and nmspc_cd = 'KS-SYS'), 'KS-AP', 'AcademicPlanService/validateLearningPlan', 'Allows the user to execute the validateLearningPlan method of the AcademicPlanService', 'Y')
/
insert into KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) values
('KS-AP-PERM-0017', 'KS-AP-PERM-0017', 1, (SELECT perm_tmpl_id FROM krim_perm_tmpl_t where nm = 'Access Permission' and nmspc_cd = 'KS-SYS'), 'KS-AP', 'AcademicPlanService/validatePlanItem', 'Allows the user to execute the validatePlanItem method of the AcademicPlanService', 'Y')
/

--grant ksap permission to User role   (i.e. any active KS user)
--Note: eventually a more rich role structure should be considered:  Student, Advisor, KSAP-Admin,...
insert into krim_role_perm_t (PERM_ID,ROLE_ID,ROLE_PERM_ID,ACTV_IND,VER_NBR,OBJ_ID)
select p.PERM_ID,r.ROLE_ID,p.PERM_ID||'-FOR-ROLE-ID-'||r.ROLE_ID AS ROLE_PERM_ID,'Y' as ACTV_IND, 1 VER_NBR,SYS_GUID()
from KRIM_PERM_T p, KRIM_ROLE_T r
WHERE p.NMSPC_CD='KS-AP' and r.ROLE_NM='User' and r.NMSPC_CD='KUALI'
/




