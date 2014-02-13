update KREW_DOC_TYP_T set DOC_HDLR_URL = '${application.url}/kr-krad/maintenance?methodToCall=docHandler&dataObjectClassName=org.kuali.student.enrollment.class1.krms.dto.CORuleManagementWrapper&viewName=COAgendaManagementView' where DOC_TYP_NM = 'CORuleMaintenanceDocument'
/

update KREW_DOC_TYP_T set DOC_HDLR_URL = '${application.url}/kr-krad/maintenance?methodToCall=docHandler&dataObjectClassName=org.kuali.student.enrollment.class1.krms.dto.AORuleManagementWrapper&viewName=AOAgendaManagementView' where DOC_TYP_NM = 'AORuleMaintenanceDocument'
/

update KRMS_NL_TMPL_T set TMPL = 'Add courses to be taken at the same time as this course.' where NL_TMPL_ID = 'KS-KRMS-NL-TMPL-1404'
/

INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID, DOC_TYP_ID, INIT_RTE_NODE_ID, NM, INIT_IND, VER_NBR)
  VALUES ('KS-KREW-DOC-TYP-PROC-3034', (select DOC_TYP_ID from KREW_DOC_TYP_T where DOC_TYP_NM='CORuleMaintenanceDocument' and CUR_IND=1), '', 'PRIMARY', 1, 1)
/

INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID, DOC_TYP_ID, INIT_RTE_NODE_ID, NM, INIT_IND, VER_NBR)
  VALUES ('KS-KREW-DOC-TYP-PROC-3035', (select DOC_TYP_ID from KREW_DOC_TYP_T where DOC_TYP_NM='AORuleMaintenanceDocument' and CUR_IND=1), '', 'PRIMARY', 1, 1)
/