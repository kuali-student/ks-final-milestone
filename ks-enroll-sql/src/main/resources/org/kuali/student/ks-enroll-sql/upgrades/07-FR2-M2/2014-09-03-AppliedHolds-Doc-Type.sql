-- Doc type for applied hold maintenance
INSERT INTO KREW_DOC_TYP_T (ACTV_IND, BLNKT_APPR_GRP_ID, CUR_IND, DOC_HDLR_URL, DOC_TYP_DESC, DOC_TYP_ID, DOC_TYP_NM,
  DOC_TYP_VER_NBR, GRP_ID, HELP_DEF_URL, LBL, OBJ_ID, PARNT_ID, POST_PRCSR, PREV_DOC_TYP_VER_NBR, RTE_VER_NBR, VER_NBR)
  VALUES (1, null, 1, '${application.url}/kr-krad/appliedHoldMaintenance?viewName=AppliedHoldMaintenanceView&dataObjectClassName=org.kuali.student.enrollment.class1.hold.form.AppliedHoldMaintenanceForm&methodToCall=docHandler',
  'Applied Hold Maintenance', 'KS-KREW-DOC-TYP-3054', 'AppliedHoldMaintenanceDocument', 0, null, null, 'Applied Hold Maintenance Document',
  SYS_GUID(), (SELECT DOC_TYP_ID FROM KREW_DOC_TYP_T WHERE DOC_TYP_NM = 'RiceDocument' AND CUR_IND = 1),
  'org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor', null, '2', 1)
/

INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID, DOC_TYP_ID, INIT_RTE_NODE_ID, NM, INIT_IND, VER_NBR)
  VALUES ('KS-KREW-DOC-TYP-PROC-3036', (select DOC_TYP_ID from KREW_DOC_TYP_T where DOC_TYP_NM='AppliedHoldMaintenanceDocument' and CUR_IND=1),
  '', 'PRIMARY', 1, 1)
/