-- THIS SQL MAY BREAK IMPLEMENTATIONS WHERE THE DOCUMENT TYPE XML CONFIGURATION HAS BEEN CHANGED FROM THE STOCK REF DATA VERSION
-- Recommend ingesting the document type xml manually rather than running this SQL. XML to be ingested can be found on JIRA issue KSCM-1813
-- These document types are for create course only... modify and retire will be done as part of future JIRA issues

select KREW_RTE_TMPL_S.nextval from dual
/
INSERT INTO KREW_RULE_ATTR_T (RULE_ATTR_ID, APPL_ID, DESC_TXT, LBL, NM, OBJ_ID, CLS_NM, RULE_ATTR_TYP_CD, VER_NBR, XML) VALUES ('KS-1645', '', 'Data Dictionary Searchable Attribute', 'Data Dictionary Searchable Attribute', 'DataDictionarySearchableAttribute', 'bacb5361-5b44-4992-a98a-433f076dd94b', 'org.kuali.rice.krad.workflow.attribute.DataDictionarySearchableAttribute', 'SearchableAttribute', 1, '')
/

UPDATE KREW_DOC_TYP_T SET VER_NBR = 3 WHERE ((DOC_TYP_ID = 'KS-1019') AND (VER_NBR = 2))
/

-- UPDATE KREW_DOC_TYP_T SET CUR_IND = '0', VER_NBR = 4 WHERE ((DOC_TYP_ID = 'KS-1021') AND (VER_NBR = 3))
UPDATE KREW_DOC_TYP_T SET CUR_IND = '0', VER_NBR = (select (VER_NBR + 1) from KREW_DOC_TYP_T WHERE ((CUR_IND = '1') AND (DOC_TYP_NM = 'CluParentDocument'))) WHERE ((CUR_IND = '1') AND (DOC_TYP_NM = 'CluParentDocument'))
/
select KREW_DOC_HDR_S.nextval from dual
/
select KREW_DOC_TYP_ATTR_S.nextval from dual
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID, ACTV_IND, APPL_ID, NOTIFY_ADDR, AUTHORIZER, BLNKT_APPR_PLCY, BLNKT_APPR_GRP_ID, CUR_IND, EMAIL_XSL, DOC_TYP_DESC, PARNT_ID, DOC_HDR_ID, SEC_XML, LBL, DOC_TYP_NM, OBJ_ID, POST_PRCSR, PREV_DOC_TYP_VER_NBR, RPT_GRP_ID, RTE_VER_NBR, DOC_HDLR_URL, DOC_SEARCH_HELP_URL, HELP_DEF_URL, DOC_TYP_VER_NBR, VER_NBR, GRP_ID) VALUES ('KS-3025', '1', '', '', '', '', '', '1', '', 'Kuali Student Clu Parent Document', 'KS-1019', '', '', 'Kuali Student Clu Parent Document', 'CluParentDocument', 'b12d1782-b5e0-48b4-b0e9-9238071d48f0', '', 'KS-1021', '', '2', '', '', '', 1, 1, '')
/
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID, ORD_INDX, DOC_TYP_ID, RULE_ATTR_ID) VALUES ('KS-2010', 1, 'KS-3025', 'KS-1645')
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID = 'KS-3025', VER_NBR = 7 WHERE ((DOC_TYP_ID = 'KS-1022') AND (VER_NBR = 6))
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID = 'KS-3025', VER_NBR = 4 WHERE ((DOC_TYP_ID = 'KS-1031') AND (VER_NBR = 3))
/

UPDATE KREW_DOC_TYP_T SET VER_NBR = 1 WHERE ((DOC_TYP_ID = '2680') AND (VER_NBR = 0))
/

-- UPDATE KREW_DOC_TYP_T SET CUR_IND = '0', VER_NBR = 8 WHERE ((DOC_TYP_ID = 'KS-1022') AND (VER_NBR = 7))
UPDATE KREW_DOC_TYP_T SET CUR_IND = '0', VER_NBR = (select (VER_NBR + 1) from KREW_DOC_TYP_T WHERE ((CUR_IND = '1') AND (DOC_TYP_NM = 'CluCreditCourseParentDocument'))) WHERE ((CUR_IND = '1') AND (DOC_TYP_NM = 'CluCreditCourseParentDocument'))
/
select KREW_DOC_HDR_S.nextval from dual
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID, ACTV_IND, APPL_ID, NOTIFY_ADDR, AUTHORIZER, BLNKT_APPR_PLCY, BLNKT_APPR_GRP_ID, CUR_IND, EMAIL_XSL, DOC_TYP_DESC, PARNT_ID, DOC_HDR_ID, SEC_XML, LBL, DOC_TYP_NM, OBJ_ID, POST_PRCSR, PREV_DOC_TYP_VER_NBR, RPT_GRP_ID, RTE_VER_NBR, DOC_HDLR_URL, DOC_SEARCH_HELP_URL, HELP_DEF_URL, DOC_TYP_VER_NBR, VER_NBR, GRP_ID) VALUES ('KS-3026', '1', '', '', '', '', '', '1', '', 'Kuali Student Credit Course Parent Document', 'KS-3025', '', '', 'Kuali Student Credit Course Parent Document', 'CluCreditCourseParentDocument', '5bd9ec21-b0cc-41e8-8b7f-cf7ff554afaa', '', 'KS-1022', '', '2', '${kr.krad.url}/courses?methodToCall=docHandler', '', '', 2, 1, '')
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID = 'KS-3026', VER_NBR = 2 WHERE ((DOC_TYP_ID = 'KS-1026') AND (VER_NBR = 1))
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID = 'KS-3026', VER_NBR = 2 WHERE ((DOC_TYP_ID = 'KS-1027') AND (VER_NBR = 1))
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID = 'KS-3026', VER_NBR = 2 WHERE ((DOC_TYP_ID = 'KS-1028') AND (VER_NBR = 1))
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID = 'KS-3026', VER_NBR = 2 WHERE ((DOC_TYP_ID = 'KS-1029') AND (VER_NBR = 1))
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID = 'KS-3026', VER_NBR = 2 WHERE ((DOC_TYP_ID = 'KS-1030') AND (VER_NBR = 1))
/

-- UPDATE KREW_DOC_TYP_T SET CUR_IND = '0', VER_NBR = 3 WHERE ((DOC_TYP_ID = 'KS-1029') AND (VER_NBR = 2))
UPDATE KREW_DOC_TYP_T SET CUR_IND = '0', VER_NBR = (select (VER_NBR + 1) from KREW_DOC_TYP_T WHERE ((CUR_IND = '1') AND (DOC_TYP_NM = 'kuali.proposal.type.course.create.admin'))) WHERE ((CUR_IND = '1') AND (DOC_TYP_NM = 'kuali.proposal.type.course.create.admin'))
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
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID, ACTV_IND, APPL_ID, NOTIFY_ADDR, AUTHORIZER, BLNKT_APPR_PLCY, BLNKT_APPR_GRP_ID, CUR_IND, EMAIL_XSL, DOC_TYP_DESC, PARNT_ID, DOC_HDR_ID, SEC_XML, LBL, DOC_TYP_NM, OBJ_ID, POST_PRCSR, PREV_DOC_TYP_VER_NBR, RPT_GRP_ID, RTE_VER_NBR, DOC_HDLR_URL, DOC_SEARCH_HELP_URL, HELP_DEF_URL, DOC_TYP_VER_NBR, VER_NBR, GRP_ID) VALUES ('KS-3027', '1', '', '', '', '', '', '1', '', 'A proposal to create a new credit course that will not have curriculum review', 'KS-3026', '', '', 'Credit Course Create Admin Proposal', 'kuali.proposal.type.course.create.admin', '8ffc31d8-da95-45be-ace5-77dcb0afcf50', '', 'KS-1029', '', '2', '', '', '', 1, 1, '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2945', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.InitialNode', '', '', 'PreRoute', 'KS-3027', '')
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID, INIT_IND, VER_NBR, NM, DOC_TYP_ID, INIT_RTE_NODE_ID) VALUES ('KS-2944', 1, 1, 'PRIMARY', 'KS-3027', 'KS-2945')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2547', 'finalApproval', 'false', 'KS-2945')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2545', 'activationType', 'P', 'KS-2945')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2548', 'ruleSelector', 'Template', 'KS-2945')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2546', 'mandatoryRoute', 'false', 'KS-2945')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2549', 'contentFragment', '<start name="PreRoute"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>', 'KS-2945')
/

-- UPDATE KREW_DOC_TYP_T SET CUR_IND = '0', VER_NBR = 3 WHERE ((DOC_TYP_ID = 'KS-1028') AND (VER_NBR = 2))
UPDATE KREW_DOC_TYP_T SET CUR_IND = '0', VER_NBR = (select (VER_NBR + 1) from KREW_DOC_TYP_T WHERE ((CUR_IND = '1') AND (DOC_TYP_NM = 'kuali.proposal.type.course.create'))) WHERE ((CUR_IND = '1') AND (DOC_TYP_NM = 'kuali.proposal.type.course.create'))
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
select KREW_RTE_NODE_CFG_PARM_S.nextval from dual
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID, ACTV_IND, APPL_ID, NOTIFY_ADDR, AUTHORIZER, BLNKT_APPR_PLCY, BLNKT_APPR_GRP_ID, CUR_IND, EMAIL_XSL, DOC_TYP_DESC, PARNT_ID, DOC_HDR_ID, SEC_XML, LBL, DOC_TYP_NM, OBJ_ID, POST_PRCSR, PREV_DOC_TYP_VER_NBR, RPT_GRP_ID, RTE_VER_NBR, DOC_HDLR_URL, DOC_SEARCH_HELP_URL, HELP_DEF_URL, DOC_TYP_VER_NBR, VER_NBR, GRP_ID) VALUES ('KS-3028', '1', '', '', '', '', '', '1', '', 'A proposal to create a new credit course', 'KS-3026', '', '', 'Credit Course Create Proposal', 'kuali.proposal.type.course.create', 'a1c1029d-3621-4da3-804d-120a39446b08', '', 'KS-1028', '', '2', '', '', '', 2, 1, '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2951', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.RoleNode', 'RM', 'org.kuali.rice.kew.role.RoleRouteModule', 'Senate Review', 'KS-3028', '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2950', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.RoleNode', 'RM', 'org.kuali.rice.kew.role.RoleRouteModule', 'College Review', 'KS-3028', '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2952', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.RoleNode', 'RM', 'org.kuali.rice.kew.role.RoleRouteModule', 'Publication Review', 'KS-3028', '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2947', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.InitialNode', '', '', 'PreRoute', 'KS-3028', '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2948', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.RoleNode', 'RM', 'org.kuali.rice.kew.role.RoleRouteModule', 'Department Review', 'KS-3028', '')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID, ACTVN_TYP, GRP_ID, FNL_APRVR_IND, VER_NBR, MNDTRY_RTE_IND, NEXT_DOC_STAT, TYP, RTE_MTHD_CD, RTE_MTHD_NM, NM, DOC_TYP_ID, BRCH_PROTO_ID) VALUES ('KS-2949', 'P', '', '0', 1, '0', '', 'org.kuali.rice.kew.engine.node.RoleNode', 'RM', 'org.kuali.rice.kew.role.RoleRouteModule', 'Division Review', 'KS-3028', '')
/
INSERT INTO KREW_DOC_TYP_PLCY_RELN_T (DOC_PLCY_NM, OBJ_ID, PLCY_VAL, PLCY_NM, VER_NBR, DOC_TYP_ID) VALUES ('DEFAULT_APPROVE', '8d891937-ed95-4b55-b28c-cd3a2be92b4e', '', '0', 1, 'KS-3028')
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID, INIT_IND, VER_NBR, NM, DOC_TYP_ID, INIT_RTE_NODE_ID) VALUES ('KS-2946', 1, 1, 'PRIMARY', 'KS-3028', 'KS-2947')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2554', 'contentFragment', '<start name="PreRoute"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>', 'KS-2947')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2559', 'contentFragment', '<role name="Department Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver</qualifierResolverClass><organizationTypeCode>kuali.org.Department</organizationTypeCode><organizationIdQualifierKey>department</organizationIdQualifierKey></role>', 'KS-2948')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2565', 'contentFragment', '<role name="Division Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver</qualifierResolverClass><organizationTypeCode>kuali.org.Division</organizationTypeCode><organizationIdQualifierKey>division</organizationIdQualifierKey></role>', 'KS-2949')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2571', 'contentFragment', '<role name="College Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver</qualifierResolverClass><organizationTypeCode>kuali.org.College</organizationTypeCode><organizationIdQualifierKey>college</organizationIdQualifierKey></role>', 'KS-2950')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2577', 'contentFragment', '<role name="Senate Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrgDataDictionaryQualifierResolver</qualifierResolverClass><organizationId>ORGID-141</organizationId></role>', 'KS-2951')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2582', 'contentFragment', '<role name="Publication Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrgDataDictionaryQualifierResolver</qualifierResolverClass><organizationId>ORGID-176</organizationId></role>', 'KS-2952')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2557', 'organizationId', 'ORGID-141', 'KS-2951')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2563', 'organizationIdQualifierKey', 'college', 'KS-2950')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2566', 'activationType', 'P', 'KS-2949')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2579', 'mandatoryRoute', 'false', 'KS-2947')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2553', 'ruleSelector', 'Template', 'KS-2952')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2560', 'activationType', 'P', 'KS-2950')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2572', 'activationType', 'P', 'KS-2948')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2570', 'ruleSelector', 'Template', 'KS-2949')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2561', 'qualifierResolverClass', 'org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver', 'KS-2950')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2556', 'qualifierResolverClass', 'org.kuali.student.lum.workflow.qualifierresolver.StaticOrgDataDictionaryQualifierResolver', 'KS-2951')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2555', 'activationType', 'P', 'KS-2951')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2578', 'activationType', 'P', 'KS-2947')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2551', 'qualifierResolverClass', 'org.kuali.student.lum.workflow.qualifierresolver.StaticOrgDataDictionaryQualifierResolver', 'KS-2952')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2562', 'organizationTypeCode', 'kuali.org.College', 'KS-2950')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2573', 'qualifierResolverClass', 'org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver', 'KS-2948')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2580', 'finalApproval', 'false', 'KS-2947')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2581', 'ruleSelector', 'Template', 'KS-2947')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2575', 'organizationIdQualifierKey', 'department', 'KS-2948')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2567', 'qualifierResolverClass', 'org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver', 'KS-2949')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2568', 'organizationTypeCode', 'kuali.org.Division', 'KS-2949')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2558', 'ruleSelector', 'Template', 'KS-2951')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2552', 'organizationId', 'ORGID-176', 'KS-2952')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2569', 'organizationIdQualifierKey', 'division', 'KS-2949')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2576', 'ruleSelector', 'Template', 'KS-2948')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2564', 'ruleSelector', 'Template', 'KS-2950')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2550', 'activationType', 'P', 'KS-2952')
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID, KEY_CD, VAL, RTE_NODE_ID) VALUES ('KS-2574', 'organizationTypeCode', 'kuali.org.Department', 'KS-2948')
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID, FROM_RTE_NODE_ID) VALUES ('KS-2952', 'KS-2951')
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID, FROM_RTE_NODE_ID) VALUES ('KS-2951', 'KS-2950')
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID, FROM_RTE_NODE_ID) VALUES ('KS-2948', 'KS-2947')
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID, FROM_RTE_NODE_ID) VALUES ('KS-2949', 'KS-2948')
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID, FROM_RTE_NODE_ID) VALUES ('KS-2950', 'KS-2949')
/