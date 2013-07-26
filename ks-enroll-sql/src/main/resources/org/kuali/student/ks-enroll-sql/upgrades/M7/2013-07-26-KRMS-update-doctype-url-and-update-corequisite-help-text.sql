update KREW_DOC_TYP_T set DOC_HDLR_URL = '${application.url}/kr-krad/maintenance?methodToCall=docHandler&dataObjectClassName=org.kuali.student.enrollment.class1.krms.dto.CORuleManagementWrapper&viewName=COAgendaManagementView' where DOC_TYP_NM = 'CORuleMaintenanceDocument'
/

update KREW_DOC_TYP_T set DOC_HDLR_URL = '${application.url}/kr-krad/maintenance?methodToCall=docHandler&dataObjectClassName=org.kuali.student.enrollment.class1.krms.dto.AORuleManagementWrapper&viewName=AOAgendaManagementView' where DOC_TYP_NM = 'AORuleMaintenanceDocument'
/

update KRMS_NL_TMPL_T set TMPL = 'Add courses to be taken at the same time as this course.' where NL_TMPL_ID = 'KS-KRMS-NL-TMPL-1404'
/