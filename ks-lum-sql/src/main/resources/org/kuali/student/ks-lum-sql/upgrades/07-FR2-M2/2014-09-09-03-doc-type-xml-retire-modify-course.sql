-- THIS SQL MAY BREAK IMPLEMENTATIONS WHERE THE DOCUMENT TYPE XML CONFIGURATION HAS BEEN CHANGED FROM THE STOCK REF DATA VERSION
-- Recommend ingesting the document type xml manually rather than running this SQL. XML to be ingested can be found on JIRA issue KSCM-2669
-- These document types are for modify course and retire course only... this includes a brand new document type for "modify this version" functionality

-- UPDATE KREW_DOC_TYP_T SET CUR_IND = '0', VER_NBR = 3 WHERE ((DOC_TYP_ID = 'KS-1030') AND (VER_NBR = 2))
UPDATE KREW_DOC_TYP_T SET CUR_IND = '0', VER_NBR = (select (VER_NBR + 1) from KREW_DOC_TYP_T WHERE ((CUR_IND = '1') AND (DOC_TYP_NM = 'kuali.proposal.type.course.modify.admin'))) WHERE ((CUR_IND = '1') AND (DOC_TYP_NM = 'kuali.proposal.type.course.modify.admin'))
/
UPDATE KREW_DOC_TYP_T SET VER_NBR = 2 WHERE ((DOC_TYP_ID = 'KS-3026') AND (VER_NBR = 1))
/
select KREW_DOC_HDR_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID, ACTV_IND, APPL_ID, NOTIFY_ADDR, AUTHORIZER, BLNKT_APPR_PLCY, BLNKT_APPR_GRP_ID, CUR_IND, EMAIL_XSL, DOC_TYP_DESC, PARNT_ID, DOC_HDR_ID, SEC_XML, LBL, DOC_TYP_NM, OBJ_ID, POST_PRCSR, PREV_DOC_TYP_VER_NBR, RPT_GRP_ID, RTE_VER_NBR, DOC_HDLR_URL, DOC_SEARCH_HELP_URL, HELP_DEF_URL, DOC_TYP_VER_NBR, VER_NBR, GRP_ID) VALUES ('KS-3029', '1', '', '', '', '', '', '1', '', 'A proposal to modify an existing credit course that will not have curriculum review', 'KS-3026', '', '', 'Credit Course Modify Admin Proposal', 'kuali.proposal.type.course.modify.admin', '92c028a3-470d-4b20-98dc-61ce0e23799d', '', 'KS-1030', '', '2', '', '', '', 1, 1, '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2954', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.InitialNode', '', '', 'PreRoute', 'KS-3029', '')
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID, INIT_IND, VER_NBR, NM, DOC_TYP_ID, INIT_RTE_NODE_ID) VALUES ('KS-2953', 1, 1, 'PRIMARY', 'KS-3029', 'KS-2954')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2585', 'mandatoryRoute', 'false', 'KS-2954')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2586', 'finalApproval', 'false', 'KS-2954')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2584', 'activationType', 'P', 'KS-2954')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2587', 'ruleSelector', 'Template', 'KS-2954')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2588', 'contentFragment', '<start name="PreRoute"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>', 'KS-2954')
/
UPDATE KREW_DOC_TYP_T SET VER_NBR = 2 WHERE ((DOC_TYP_ID = 'KS-3025') AND (VER_NBR = 1))
/
-- UPDATE KREW_DOC_TYP_T SET CUR_IND = '0', VER_NBR = 3 WHERE ((DOC_TYP_ID = 'KS-1027') AND (VER_NBR = 2))
UPDATE KREW_DOC_TYP_T SET CUR_IND = '0', VER_NBR = (select (VER_NBR + 1) from KREW_DOC_TYP_T WHERE ((CUR_IND = '1') AND (DOC_TYP_NM = 'kuali.proposal.type.course.modify'))) WHERE ((CUR_IND = '1') AND (DOC_TYP_NM = 'kuali.proposal.type.course.modify'))
/
select KREW_DOC_HDR_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID, ACTV_IND, APPL_ID, NOTIFY_ADDR, AUTHORIZER, BLNKT_APPR_PLCY, BLNKT_APPR_GRP_ID, CUR_IND, EMAIL_XSL, DOC_TYP_DESC, PARNT_ID, DOC_HDR_ID, SEC_XML, LBL, DOC_TYP_NM, OBJ_ID, POST_PRCSR, PREV_DOC_TYP_VER_NBR, RPT_GRP_ID, RTE_VER_NBR, DOC_HDLR_URL, DOC_SEARCH_HELP_URL, HELP_DEF_URL, DOC_TYP_VER_NBR, VER_NBR, GRP_ID) VALUES ('KS-3030', '1', '', '', '', '', '', '1', '', 'A proposal to modify an existing credit course', 'KS-3026', '', '', 'Credit Course Modify Proposal', 'kuali.proposal.type.course.modify', '06baf4d4-5c32-4744-95fd-a0b591022b28', '', 'KS-1027', '', '2', '', '', '', 2, 1, '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2956', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.InitialNode', '', '', 'PreRoute', 'KS-3030', '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2957', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.RoleNode', 'RM', 'org.kuali.rice.kew.role.RoleRouteModule', 'Department Review', 'KS-3030', '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2958', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.RoleNode', 'RM', 'org.kuali.rice.kew.role.RoleRouteModule', 'Division Review', 'KS-3030', '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2960', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.RoleNode', 'RM', 'org.kuali.rice.kew.role.RoleRouteModule', 'Senate Review', 'KS-3030', '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2959', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.RoleNode', 'RM', 'org.kuali.rice.kew.role.RoleRouteModule', 'College Review', 'KS-3030', '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2961', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.RoleNode', 'RM', 'org.kuali.rice.kew.role.RoleRouteModule', 'Publication Review', 'KS-3030', '')
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID, INIT_IND, VER_NBR, NM, DOC_TYP_ID, INIT_RTE_NODE_ID) VALUES ('KS-2955', 1, 1, 'PRIMARY', 'KS-3030', 'KS-2956')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2618', 'mandatoryRoute', 'false', 'KS-2956')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2609', 'ruleSelector', 'Template', 'KS-2958')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2603', 'ruleSelector', 'Template', 'KS-2959')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2589', 'activationType', 'P', 'KS-2961')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2617', 'activationType', 'P', 'KS-2956')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2615', 'ruleSelector', 'Template', 'KS-2957')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2592', 'ruleSelector', 'Template', 'KS-2961')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2611', 'activationType', 'P', 'KS-2957')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2608', 'organizationIdQualifierKey', 'division', 'KS-2958')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2601', 'organizationTypeCode', 'kuali.org.College', 'KS-2959')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2600', 'qualifierResolverClass', 'org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver', 'KS-2959')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2596', 'organizationId', 'ORGID-141', 'KS-2960')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2613', 'organizationTypeCode', 'kuali.org.Department', 'KS-2957')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2607', 'organizationTypeCode', 'kuali.org.Division', 'KS-2958')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2612', 'qualifierResolverClass', 'org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver', 'KS-2957')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2605', 'activationType', 'P', 'KS-2958')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2597', 'ruleSelector', 'Template', 'KS-2960')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2594', 'activationType', 'P', 'KS-2960')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2619', 'finalApproval', 'false', 'KS-2956')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2591', 'organizationId', 'ORGID-176', 'KS-2961')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2595', 'qualifierResolverClass', 'org.kuali.student.lum.workflow.qualifierresolver.StaticOrgDataDictionaryQualifierResolver', 'KS-2960')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2614', 'organizationIdQualifierKey', 'department', 'KS-2957')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2606', 'qualifierResolverClass', 'org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver', 'KS-2958')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2599', 'activationType', 'P', 'KS-2959')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2620', 'ruleSelector', 'Template', 'KS-2956')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2590', 'qualifierResolverClass', 'org.kuali.student.lum.workflow.qualifierresolver.StaticOrgDataDictionaryQualifierResolver', 'KS-2961')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2602', 'organizationIdQualifierKey', 'college', 'KS-2959')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2593', 'contentFragment', '<start name="PreRoute"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>', 'KS-2956')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2598', 'contentFragment', '<role name="Department Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver</qualifierResolverClass><organizationTypeCode>kuali.org.Department</organizationTypeCode><organizationIdQualifierKey>department</organizationIdQualifierKey></role>', 'KS-2957')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2604', 'contentFragment', '<role name="Division Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver</qualifierResolverClass><organizationTypeCode>kuali.org.Division</organizationTypeCode><organizationIdQualifierKey>division</organizationIdQualifierKey></role>', 'KS-2958')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2610', 'contentFragment', '<role name="College Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver</qualifierResolverClass><organizationTypeCode>kuali.org.College</organizationTypeCode><organizationIdQualifierKey>college</organizationIdQualifierKey></role>', 'KS-2959')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2616', 'contentFragment', '<role name="Senate Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrgDataDictionaryQualifierResolver</qualifierResolverClass><organizationId>ORGID-141</organizationId></role>', 'KS-2960')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2621', 'contentFragment', '<role name="Publication Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrgDataDictionaryQualifierResolver</qualifierResolverClass><organizationId>ORGID-176</organizationId></role>', 'KS-2961')
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID, FROM_RTE_NODE_ID) VALUES ('KS-2957', 'KS-2956')
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID, FROM_RTE_NODE_ID) VALUES ('KS-2958', 'KS-2957')
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID, FROM_RTE_NODE_ID) VALUES ('KS-2959', 'KS-2958')
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID, FROM_RTE_NODE_ID) VALUES ('KS-2961', 'KS-2960')
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID, FROM_RTE_NODE_ID) VALUES ('KS-2960', 'KS-2959')
/
select KREW_DOC_HDR_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID, ACTV_IND, APPL_ID, NOTIFY_ADDR, AUTHORIZER, BLNKT_APPR_PLCY, BLNKT_APPR_GRP_ID, CUR_IND, EMAIL_XSL, DOC_TYP_DESC, PARNT_ID, DOC_HDR_ID, SEC_XML, LBL, DOC_TYP_NM, OBJ_ID, POST_PRCSR, PREV_DOC_TYP_VER_NBR, RPT_GRP_ID, RTE_VER_NBR, DOC_HDLR_URL, DOC_SEARCH_HELP_URL, HELP_DEF_URL, DOC_TYP_VER_NBR, VER_NBR, GRP_ID) VALUES ('KS-3031', '1', '', '', '', '', '', '1', '', 'A proposal to modify an existing credit course that will not have curriculum review and will not create a new version', 'KS-3026', '', '', 'Credit Course Modify Admin Proposal (No Version)', 'kuali.proposal.type.course.modify.admin.noversion', '8492013c-98fa-4c5f-9007-fe2cda8f1af6', '', '', '', '2', '', '', '', 0, 1, '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2963', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.InitialNode', '', '', 'PreRoute', 'KS-3031', '')
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID, INIT_IND, VER_NBR, NM, DOC_TYP_ID, INIT_RTE_NODE_ID) VALUES ('KS-2962', 1, 1, 'PRIMARY', 'KS-3031', 'KS-2963')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2625', 'ruleSelector', 'Template', 'KS-2963')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2622', 'activationType', 'P', 'KS-2963')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2623', 'mandatoryRoute', 'false', 'KS-2963')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2624', 'finalApproval', 'false', 'KS-2963')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2626', 'contentFragment', '<start name="PreRoute"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>', 'KS-2963')
/
select KREW_DOC_HDR_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID, ACTV_IND, APPL_ID, NOTIFY_ADDR, AUTHORIZER, BLNKT_APPR_PLCY, BLNKT_APPR_GRP_ID, CUR_IND, EMAIL_XSL, DOC_TYP_DESC, PARNT_ID, DOC_HDR_ID, SEC_XML, LBL, DOC_TYP_NM, OBJ_ID, POST_PRCSR, PREV_DOC_TYP_VER_NBR, RPT_GRP_ID, RTE_VER_NBR, DOC_HDLR_URL, DOC_SEARCH_HELP_URL, HELP_DEF_URL, DOC_TYP_VER_NBR, VER_NBR, GRP_ID) VALUES ('KS-3032', '1', '', '', '', '', '', '1', '', 'A proposal to retire an existing credit course that will not have curriculum review', 'KS-3026', '', '', 'Credit Course Retire Admin Proposal', 'kuali.proposal.type.course.retire.admin', '15339e11-3e82-478b-8eea-e99029d2968c', '', '', '', '2', '${kr.krad.url}/retireCourse?methodToCall=docHandler', '', '', 0, 1, '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2965', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.InitialNode', '', '', 'PreRoute', 'KS-3032', '')
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID, INIT_IND, VER_NBR, NM, DOC_TYP_ID, INIT_RTE_NODE_ID) VALUES ('KS-2964', 1, 1, 'PRIMARY', 'KS-3032', 'KS-2965')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2628', 'mandatoryRoute', 'false', 'KS-2965')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2630', 'ruleSelector', 'Template', 'KS-2965')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2627', 'activationType', 'P', 'KS-2965')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2629', 'finalApproval', 'false', 'KS-2965')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2631', 'contentFragment', '<start name="PreRoute"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>', 'KS-2965')
/
-- UPDATE KREW_DOC_TYP_T SET CUR_IND = '0', VER_NBR = 3 WHERE ((DOC_TYP_ID = 'KS-1026') AND (VER_NBR = 2))
UPDATE KREW_DOC_TYP_T SET CUR_IND = '0', VER_NBR = (select (VER_NBR + 1) from KREW_DOC_TYP_T WHERE ((CUR_IND = '1') AND (DOC_TYP_NM = 'kuali.proposal.type.course.retire'))) WHERE ((CUR_IND = '1') AND (DOC_TYP_NM = 'kuali.proposal.type.course.retire'))
/
select KREW_DOC_HDR_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID, ACTV_IND, APPL_ID, NOTIFY_ADDR, AUTHORIZER, BLNKT_APPR_PLCY, BLNKT_APPR_GRP_ID, CUR_IND, EMAIL_XSL, DOC_TYP_DESC, PARNT_ID, DOC_HDR_ID, SEC_XML, LBL, DOC_TYP_NM, OBJ_ID, POST_PRCSR, PREV_DOC_TYP_VER_NBR, RPT_GRP_ID, RTE_VER_NBR, DOC_HDLR_URL, DOC_SEARCH_HELP_URL, HELP_DEF_URL, DOC_TYP_VER_NBR, VER_NBR, GRP_ID) VALUES ('KS-3033', '1', '', '', '', '', '', '1', '', 'A proposal to retire an existing credit course', 'KS-3026', '', '', 'Credit Course Retire Proposal', 'kuali.proposal.type.course.retire', 'e85da146-04d0-42d5-ae99-8873667eac4f', '', 'KS-1026', '', '2', '${kr.krad.url}/retireCourse?methodToCall=docHandler', '', '', 2, 1, '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2970', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.RoleNode', 'RM', 'org.kuali.rice.kew.role.RoleRouteModule', 'College Review', 'KS-3033', '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2971', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.RoleNode', 'RM', 'org.kuali.rice.kew.role.RoleRouteModule', 'Senate Review', 'KS-3033', '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2972', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.RoleNode', 'RM', 'org.kuali.rice.kew.role.RoleRouteModule', 'Publication Review', 'KS-3033', '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2967', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.InitialNode', '', '', 'PreRoute', 'KS-3033', '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2968', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.RoleNode', 'RM', 'org.kuali.rice.kew.role.RoleRouteModule', 'Department Review', 'KS-3033', '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2969', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.RoleNode', 'RM', 'org.kuali.rice.kew.role.RoleRouteModule', 'Division Review', 'KS-3033', '')
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID, INIT_IND, VER_NBR, NM, DOC_TYP_ID, INIT_RTE_NODE_ID) VALUES ('KS-2966', 1, 1, 'PRIMARY', 'KS-3033', 'KS-2967')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2642', 'activationType', 'P', 'KS-2970')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2640', 'ruleSelector', 'Template', 'KS-2971')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2643', 'qualifierResolverClass', 'org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver', 'KS-2970')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2652', 'ruleSelector', 'Template', 'KS-2969')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2646', 'ruleSelector', 'Template', 'KS-2970')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2661', 'mandatoryRoute', 'false', 'KS-2967')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2657', 'organizationIdQualifierKey', 'department', 'KS-2968')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2637', 'activationType', 'P', 'KS-2971')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2639', 'organizationId', 'ORGID-141', 'KS-2971')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2660', 'activationType', 'P', 'KS-2967')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2663', 'ruleSelector', 'Template', 'KS-2967')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2654', 'activationType', 'P', 'KS-2968')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2645', 'organizationIdQualifierKey', 'college', 'KS-2970')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2649', 'qualifierResolverClass', 'org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver', 'KS-2969')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2651', 'organizationIdQualifierKey', 'division', 'KS-2969')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2644', 'organizationTypeCode', 'kuali.org.College', 'KS-2970')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2632', 'activationType', 'P', 'KS-2972')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2658', 'ruleSelector', 'Template', 'KS-2968')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2648', 'activationType', 'P', 'KS-2969')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2655', 'qualifierResolverClass', 'org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver', 'KS-2968')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2662', 'finalApproval', 'false', 'KS-2967')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2656', 'organizationTypeCode', 'kuali.org.Department', 'KS-2968')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2638', 'qualifierResolverClass', 'org.kuali.student.lum.workflow.qualifierresolver.StaticOrgDataDictionaryQualifierResolver', 'KS-2971')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2635', 'ruleSelector', 'Template', 'KS-2972')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2650', 'organizationTypeCode', 'kuali.org.Division', 'KS-2969')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2634', 'organizationId', 'ORGID-176', 'KS-2972')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2633', 'qualifierResolverClass', 'org.kuali.student.lum.workflow.qualifierresolver.StaticOrgDataDictionaryQualifierResolver', 'KS-2972')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2636', 'contentFragment', '<start name="PreRoute"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>', 'KS-2967')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2641', 'contentFragment', '<role name="Department Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver</qualifierResolverClass><organizationTypeCode>kuali.org.Department</organizationTypeCode><organizationIdQualifierKey>department</organizationIdQualifierKey></role>', 'KS-2968')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2647', 'contentFragment', '<role name="Division Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver</qualifierResolverClass><organizationTypeCode>kuali.org.Division</organizationTypeCode><organizationIdQualifierKey>division</organizationIdQualifierKey></role>', 'KS-2969')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2653', 'contentFragment', '<role name="College Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver</qualifierResolverClass><organizationTypeCode>kuali.org.College</organizationTypeCode><organizationIdQualifierKey>college</organizationIdQualifierKey></role>', 'KS-2970')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2659', 'contentFragment', '<role name="Senate Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrgDataDictionaryQualifierResolver</qualifierResolverClass><organizationId>ORGID-141</organizationId></role>', 'KS-2971')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2664', 'contentFragment', '<role name="Publication Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrgDataDictionaryQualifierResolver</qualifierResolverClass><organizationId>ORGID-176</organizationId></role>', 'KS-2972')
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID, FROM_RTE_NODE_ID) VALUES ('KS-2971', 'KS-2970')
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID, FROM_RTE_NODE_ID) VALUES ('KS-2972', 'KS-2971')
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID, FROM_RTE_NODE_ID) VALUES ('KS-2968', 'KS-2967')
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID, FROM_RTE_NODE_ID) VALUES ('KS-2969', 'KS-2968')
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID, FROM_RTE_NODE_ID) VALUES ('KS-2970', 'KS-2969')
/