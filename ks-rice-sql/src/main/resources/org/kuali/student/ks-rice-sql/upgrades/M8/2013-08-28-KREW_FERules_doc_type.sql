INSERT INTO KREW_DOC_TYP_T (ACTV_IND, BLNKT_APPR_GRP_ID, CUR_IND, DOC_HDLR_URL, DOC_TYP_DESC, DOC_TYP_ID, DOC_TYP_NM, DOC_TYP_VER_NBR, GRP_ID, HELP_DEF_URL, LBL, OBJ_ID, PARNT_ID, POST_PRCSR, PREV_DOC_TYP_VER_NBR, RTE_VER_NBR, VER_NBR)
  VALUES (1, '1', 1, ' 	${application.url}/kr-krad/maintenance?methodToCall=docHandler&dataObjectClassName=org.kuali.student.enrollment.class1.krms.dto.FERuleManagementWrapper&viewName=FEAgendaManagementView ', 
  'Create a KRMS Final Exam Matrix Rule', 'KS-KREW-DOC-TYP-1036', 'FERuleMaintenanceDocument', 0, '1', null, 'KRMS Final Exam Rule Maintenance Document', '874ef5b0-e448-4e52-90df-7478e3f6e1d9',
  (SELECT DOC_TYP_ID FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'RiceDocument' AND CUR_IND = 1), 'org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor', null, '2', 1)
/

INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_ID, DOC_TYP_PROC_ID, INIT_IND, NM, VER_NBR)
  VALUES ('KS-KREW-DOC-TYP-1036', 'KS-KREW-DOC-TYP-PROC-1036', 1, 'PRIMARY', 2)
/