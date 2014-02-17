delete from KREW_RTE_NODE_T where DOC_TYP_ID in (select DOC_TYP_ID from KREW_DOC_TYP_T where DOC_TYP_NM='CourseOfferingEditMaintenanceDocument')
/
update KREW_DOC_TYP_PROC_T set INIT_RTE_NODE_ID='' where DOC_TYP_ID in (select DOC_TYP_ID from KREW_DOC_TYP_T where DOC_TYP_NM='CourseOfferingEditMaintenanceDocument')
/
delete from KREW_RTE_NODE_T where DOC_TYP_ID in (select DOC_TYP_ID from KREW_DOC_TYP_T where DOC_TYP_NM='CourseOfferingCreateMaintenanceDocument')
/
update KREW_DOC_TYP_PROC_T set INIT_RTE_NODE_ID='' where DOC_TYP_ID in (select DOC_TYP_ID from KREW_DOC_TYP_T where DOC_TYP_NM='CourseOfferingCreateMaintenanceDocument')
/
--clean up route node for edit AO.
delete from KREW_RTE_NODE_T where DOC_TYP_ID in (select DOC_TYP_ID from KREW_DOC_TYP_T where DOC_TYP_NM='ActivityOfferingInfoMaintenanceDocument')
/