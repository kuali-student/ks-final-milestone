select krew_doc_hdr_s.nextval from dual
/
select krew_doc_hdr_s.nextval from dual
/
select krew_doc_hdr_s.nextval from dual
/
select krew_doc_hdr_s.nextval from dual
/
select krew_doc_hdr_s.nextval from dual
/


UPDATE KREW_DOC_TYP_T SET PARNT_ID='2680',DOC_TYP_NM='KualiStudentDocument',DOC_TYP_VER_NBR='0',ACTV_IND=1,CUR_IND=0,DOC_TYP_DESC='Kuali Student Parent Document',LBL='Kuali Student Parent Document',PREV_DOC_TYP_VER_NBR='',DOC_HDR_ID='',DOC_HDLR_URL='',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='142727E5-233B-94EC-1A79-828BA4933243',VER_NBR='3' WHERE DOC_TYP_ID = '3000'  AND VER_NBR = '2'
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR)
VALUES (krew_doc_hdr_s.nextval,'2680','KualiStudentDocument','1',1,1,'Kuali Student Parent Document','Kuali Student Parent Document','3000','','','','','','','','','','2','','','','','2c6d23f4-7092-4d3b-b762-78b27b113ecb','1')
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3036',DOC_TYP_NM='CluCreditCourseParentDocument',DOC_TYP_VER_NBR='0',ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Kuali Student Credit Course Parent Document',LBL='Kuali Student Credit Course Parent Document',PREV_DOC_TYP_VER_NBR='',DOC_HDR_ID='',DOC_HDLR_URL='${lum.application.url}/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?view=COURSE_PROPOSAL&idType=documentNumber',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='DE30C093-E98A-2AFF-7DA8-5818E1A39D1C',VER_NBR='2' WHERE DOC_TYP_ID = '3001'  AND VER_NBR = '1' 
/
UPDATE KREW_DOC_TYP_ATTR_T SET DOC_TYP_ID='3001',RULE_ATTR_ID='1645',ORD_INDX='1' WHERE DOC_TYP_ATTRIB_ID = '2009' 
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='',DOC_TYP_NM='KualiDocument',DOC_TYP_VER_NBR='0',ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='KualiDocument',LBL='KualiDocument',PREV_DOC_TYP_VER_NBR='',DOC_HDR_ID='',DOC_HDLR_URL='',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='org.kuali.rice.krad.workflow.postprocessor.KualiPostProcessor',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='6166CBA1BA81644DE0404F8189D86C09',VER_NBR='1' WHERE DOC_TYP_ID = '2680'  AND VER_NBR = '0' 
/
UPDATE KREW_DOC_TYP_PLCY_RELN_T SET PLCY_NM=1,PLCY_VAL='',OBJ_ID='61BA2DCF3BE858EEE0404F8189D80CAE',VER_NBR='3' WHERE DOC_TYP_ID = '2680'  AND DOC_PLCY_NM = 'DEFAULT_APPROVE'  AND VER_NBR = '2' 
/
UPDATE KREW_DOC_TYP_PLCY_RELN_T SET PLCY_NM=1,PLCY_VAL='',OBJ_ID='61BA2DCF3BE958EEE0404F8189D80CAE',VER_NBR='3' WHERE DOC_TYP_ID = '2680'  AND DOC_PLCY_NM = 'LOOK_FUTURE'  AND VER_NBR = '2' 
/
UPDATE KREW_RTE_NODE_T SET DOC_TYP_ID='2680',NM='PreRoute',TYP='org.kuali.rice.kew.engine.node.InitialNode',RTE_MTHD_NM='',FNL_APRVR_IND=0,MNDTRY_RTE_IND=0,GRP_ID='',RTE_MTHD_CD='',ACTVN_TYP='S',BRCH_PROTO_ID='',NEXT_DOC_STAT='',VER_NBR='3' WHERE RTE_NODE_ID = '2840'  AND VER_NBR = '2' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2840',KEY_CD='contentFragment',VAL='<start name="PreRoute"><activationType>S</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>' WHERE RTE_NODE_CFG_PARM_ID = '2380' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2840',KEY_CD='activationType',VAL='S' WHERE RTE_NODE_CFG_PARM_ID = '2381' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2840',KEY_CD='mandatoryRoute',VAL=0 WHERE RTE_NODE_CFG_PARM_ID = '2382' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2840',KEY_CD='finalApproval',VAL=0 WHERE RTE_NODE_CFG_PARM_ID = '2383' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2840',KEY_CD='ruleSelector',VAL='Template' WHERE RTE_NODE_CFG_PARM_ID = '2384' 
/
UPDATE KREW_DOC_TYP_PROC_T SET DOC_TYP_ID='2680',INIT_RTE_NODE_ID='2840',NM='PRIMARY',INIT_IND=1,VER_NBR='3' WHERE DOC_TYP_PROC_ID = '2841'  AND VER_NBR = '2' 
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR)
VALUES (krew_doc_hdr_s.nextval,'3036','CluParentDocument','0',1,1,'Kuali Student Clu Parent Document','Kuali Student Clu Parent Document','','','','','','','','','','','2','','','','','c22704a1-13eb-42f4-b29e-26ba4cd25984','1')
/
INSERT INTO KREW_DOC_TYP_ATTR_T (DOC_TYP_ATTRIB_ID,DOC_TYP_ID,RULE_ATTR_ID,ORD_INDX) VALUES ('2010','3037','1645','1') 
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='2680',DOC_TYP_NM='KualiStudentDocument',DOC_TYP_VER_NBR='1',ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Kuali Student Parent Document',LBL='Kuali Student Parent Document',PREV_DOC_TYP_VER_NBR='3000',DOC_HDR_ID='',DOC_HDLR_URL='',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='2c6d23f4-7092-4d3b-b762-78b27b113ecb',VER_NBR='2' WHERE DOC_TYP_ID = '3036'  AND VER_NBR = '1' 
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3036',DOC_TYP_NM='CluCreditCourseParentDocument',DOC_TYP_VER_NBR='0',ACTV_IND=1,CUR_IND=0,DOC_TYP_DESC='Kuali Student Credit Course Parent Document',LBL='Kuali Student Credit Course Parent Document',PREV_DOC_TYP_VER_NBR='',DOC_HDR_ID='',DOC_HDLR_URL='${lum.application.url}/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?view=COURSE_PROPOSAL&idType=documentNumber',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='DE30C093-E98A-2AFF-7DA8-5818E1A39D1C',VER_NBR='3' WHERE DOC_TYP_ID = '3001'  AND VER_NBR = '2' 
/
UPDATE KREW_DOC_TYP_ATTR_T SET DOC_TYP_ID='3001',RULE_ATTR_ID='1645',ORD_INDX='1' WHERE DOC_TYP_ATTRIB_ID = '2009' 
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR)
VALUES (krew_doc_hdr_s.nextval,'3037','CluCreditCourseParentDocument','1',1,1,'Kuali Student Credit Course Parent Document','Kuali Student Credit Course Parent Document','3001','','${lum.application.url}/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?view=COURSE_PROPOSAL&idType=documentNumber','','','','','','','','2','','','','','f0c6caf6-32c5-4def-998c-826ceeb75e71','1')
/
UPDATE KRSB_QRTZ_SCHEDULER_STATE SET LAST_CHECKIN_TIME = 1346685664129 WHERE INSTANCE_NAME = 'Paul-HP1346682116829'
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3038',DOC_TYP_NM='kuali.proposal.type.course.retire',DOC_TYP_VER_NBR='0',ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Credit Course Retirement',LBL='Credit Course Retirement',PREV_DOC_TYP_VER_NBR='',DOC_HDR_ID='',DOC_HDLR_URL='',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='org.kuali.student.lum.workflow.CoursePostProcessorBase',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='6BF30ECF-C627-F88C-A4F1-EEB866D0A9B7',VER_NBR='2' WHERE DOC_TYP_ID = '3002'  AND VER_NBR = '1' 
/
UPDATE KREW_RTE_NODE_T SET DOC_TYP_ID='3002',NM='PreRoute',TYP='org.kuali.rice.kew.engine.node.InitialNode',RTE_MTHD_NM='',FNL_APRVR_IND=0,MNDTRY_RTE_IND=0,GRP_ID='',RTE_MTHD_CD='',ACTVN_TYP='P',BRCH_PROTO_ID='',NEXT_DOC_STAT='',VER_NBR='2' WHERE RTE_NODE_ID = '2912'  AND VER_NBR = '1' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2912',KEY_CD='contentFragment',VAL='<start name="PreRoute"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>' WHERE RTE_NODE_CFG_PARM_ID = '2456' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2912',KEY_CD='activationType',VAL='P' WHERE RTE_NODE_CFG_PARM_ID = '2457' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2912',KEY_CD='mandatoryRoute',VAL=0 WHERE RTE_NODE_CFG_PARM_ID = '2458' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2912',KEY_CD='finalApproval',VAL=0 WHERE RTE_NODE_CFG_PARM_ID = '2459' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2912',KEY_CD='ruleSelector',VAL='Template' WHERE RTE_NODE_CFG_PARM_ID = '2460' 
/
UPDATE KREW_DOC_TYP_PROC_T SET DOC_TYP_ID='3002',INIT_RTE_NODE_ID='2912',NM='PRIMARY',INIT_IND=1,VER_NBR='2' WHERE DOC_TYP_PROC_ID = '2915'  AND VER_NBR = '1' 
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3038',DOC_TYP_NM='kuali.proposal.type.course.modify',DOC_TYP_VER_NBR='0',ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Credit Course Modification',LBL='Credit Course Modification',PREV_DOC_TYP_VER_NBR='',DOC_HDR_ID='',DOC_HDLR_URL='',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='org.kuali.student.lum.workflow.CoursePostProcessorBase',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='2A6CD5BB-3B9D-0E31-A35C-64115FE51E05',VER_NBR='2' WHERE DOC_TYP_ID = '3003'  AND VER_NBR = '1' 
/
UPDATE KREW_RTE_NODE_T SET DOC_TYP_ID='3003',NM='PreRoute',TYP='org.kuali.rice.kew.engine.node.InitialNode',RTE_MTHD_NM='',FNL_APRVR_IND=0,MNDTRY_RTE_IND=0,GRP_ID='',RTE_MTHD_CD='',ACTVN_TYP='P',BRCH_PROTO_ID='',NEXT_DOC_STAT='',VER_NBR='2' WHERE RTE_NODE_ID = '2943'  AND VER_NBR = '1' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2943',KEY_CD='contentFragment',VAL='<start name="PreRoute"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>' WHERE RTE_NODE_CFG_PARM_ID = '2572' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2943',KEY_CD='activationType',VAL='P' WHERE RTE_NODE_CFG_PARM_ID = '2573' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2943',KEY_CD='mandatoryRoute',VAL=0 WHERE RTE_NODE_CFG_PARM_ID = '2574' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2943',KEY_CD='finalApproval',VAL=0 WHERE RTE_NODE_CFG_PARM_ID = '2575' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2943',KEY_CD='ruleSelector',VAL='Template' WHERE RTE_NODE_CFG_PARM_ID = '2576' 
/
UPDATE KREW_DOC_TYP_PROC_T SET DOC_TYP_ID='3003',INIT_RTE_NODE_ID='2943',NM='PRIMARY',INIT_IND=1,VER_NBR='2' WHERE DOC_TYP_PROC_ID = '2951'  AND VER_NBR = '1' 
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3038',DOC_TYP_NM='kuali.proposal.type.course.create',DOC_TYP_VER_NBR='0',ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Credit Course Proposal',LBL='Credit Course Proposal',PREV_DOC_TYP_VER_NBR='',DOC_HDR_ID='',DOC_HDLR_URL='',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='org.kuali.student.lum.workflow.CoursePostProcessorBase',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='C7DFC7CD-8206-2AFC-8115-75EEDAA5AA0F',VER_NBR='2' WHERE DOC_TYP_ID = '3004'  AND VER_NBR = '1' 
/
UPDATE KREW_RTE_NODE_T SET DOC_TYP_ID='3004',NM='PreRoute',TYP='org.kuali.rice.kew.engine.node.InitialNode',RTE_MTHD_NM='',FNL_APRVR_IND=0,MNDTRY_RTE_IND=0,GRP_ID='',RTE_MTHD_CD='',ACTVN_TYP='P',BRCH_PROTO_ID='',NEXT_DOC_STAT='',VER_NBR='2' WHERE RTE_NODE_ID = '2936'  AND VER_NBR = '1' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2936',KEY_CD='contentFragment',VAL='<start name="PreRoute"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>' WHERE RTE_NODE_CFG_PARM_ID = '2535' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2936',KEY_CD='activationType',VAL='P' WHERE RTE_NODE_CFG_PARM_ID = '2536'
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2936',KEY_CD='mandatoryRoute',VAL=0 WHERE RTE_NODE_CFG_PARM_ID = '2537' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2936',KEY_CD='finalApproval',VAL=0 WHERE RTE_NODE_CFG_PARM_ID = '2538' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2936',KEY_CD='ruleSelector',VAL='Template' WHERE RTE_NODE_CFG_PARM_ID = '2539' 
/
UPDATE KREW_DOC_TYP_PROC_T SET DOC_TYP_ID='3004',INIT_RTE_NODE_ID='2936',NM='PRIMARY',INIT_IND=1,VER_NBR='2' WHERE DOC_TYP_PROC_ID = '2942'  AND VER_NBR = '1' 
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3036',DOC_TYP_NM='CluParentDocument',DOC_TYP_VER_NBR='0',ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Kuali Student Clu Parent Document',LBL='Kuali Student Clu Parent Document',PREV_DOC_TYP_VER_NBR='',DOC_HDR_ID='',DOC_HDLR_URL='',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='c22704a1-13eb-42f4-b29e-26ba4cd25984',VER_NBR='2' WHERE DOC_TYP_ID = '3037'  AND VER_NBR = '1' 
/
UPDATE KREW_DOC_TYP_ATTR_T SET DOC_TYP_ID='3037',RULE_ATTR_ID='1645',ORD_INDX='1' WHERE DOC_TYP_ATTRIB_ID = '2010' 
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR)
VALUES (krew_doc_hdr_s.nextval,'3037','CluMajorDisciplineParentDocument','0',1,1,'Kuali Student Major Discipline Parent Document','Kuali Student Major Discipline Parent Document','','','${lum.application.url}/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?view=PROGRAM_PROPOSAL&idType=documentNumber','','','','','','','','2','','','','','a2d9776b-e132-42de-914c-e0e54b3a1f6d','1')
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3036',DOC_TYP_NM='CluParentDocument',DOC_TYP_VER_NBR='0',ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Kuali Student Clu Parent Document',LBL='Kuali Student Clu Parent Document',PREV_DOC_TYP_VER_NBR='',DOC_HDR_ID='',DOC_HDLR_URL='',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='c22704a1-13eb-42f4-b29e-26ba4cd25984',VER_NBR='3' WHERE DOC_TYP_ID = '3037'  AND VER_NBR = '2' 
/
UPDATE KREW_DOC_TYP_ATTR_T SET DOC_TYP_ID='3037',RULE_ATTR_ID='1645',ORD_INDX='1' WHERE DOC_TYP_ATTRIB_ID = '2010' 
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3038',DOC_TYP_NM='kuali.proposal.type.course.retire',DOC_TYP_VER_NBR='0',ACTV_IND=1,CUR_IND=0,DOC_TYP_DESC='Credit Course Retirement',LBL='Credit Course Retirement',PREV_DOC_TYP_VER_NBR='',DOC_HDR_ID='',DOC_HDLR_URL='',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='org.kuali.student.lum.workflow.CoursePostProcessorBase',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='6BF30ECF-C627-F88C-A4F1-EEB866D0A9B7',VER_NBR='3' WHERE DOC_TYP_ID = '3002'  AND VER_NBR = '2' 
/
UPDATE KREW_RTE_NODE_T SET DOC_TYP_ID='3002',NM='PreRoute',TYP='org.kuali.rice.kew.engine.node.InitialNode',RTE_MTHD_NM='',FNL_APRVR_IND=0,MNDTRY_RTE_IND=0,GRP_ID='',RTE_MTHD_CD='',ACTVN_TYP='P',BRCH_PROTO_ID='',NEXT_DOC_STAT='',VER_NBR='3' WHERE RTE_NODE_ID = '2912'  AND VER_NBR = '2' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2912',KEY_CD='contentFragment',VAL='<start name="PreRoute"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>' WHERE RTE_NODE_CFG_PARM_ID = '2456' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2912',KEY_CD='activationType',VAL='P' WHERE RTE_NODE_CFG_PARM_ID = '2457' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2912',KEY_CD='mandatoryRoute',VAL=0 WHERE RTE_NODE_CFG_PARM_ID = '2458' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2912',KEY_CD='finalApproval',VAL=0 WHERE RTE_NODE_CFG_PARM_ID = '2459' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2912',KEY_CD='ruleSelector',VAL='Template' WHERE RTE_NODE_CFG_PARM_ID = '2460' 
/
UPDATE KREW_RTE_NODE_T SET DOC_TYP_ID='3002',NM='Department Review',TYP='org.kuali.rice.kew.engine.node.RoleNode',RTE_MTHD_NM='org.kuali.rice.kew.role.RoleRouteModule',FNL_APRVR_IND=0,MNDTRY_RTE_IND=0,GRP_ID='',RTE_MTHD_CD='RM',ACTVN_TYP='P',BRCH_PROTO_ID='',NEXT_DOC_STAT='',VER_NBR='2' WHERE RTE_NODE_ID = '2913'  AND VER_NBR = '1' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2913',KEY_CD='contentFragment',VAL='<role name="Department Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.DepartmentCommitteeQualifierResolver</qualifierResolverClass></role>' WHERE RTE_NODE_CFG_PARM_ID = '2461' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2913',KEY_CD='activationType',VAL='P' WHERE RTE_NODE_CFG_PARM_ID = '2462' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2913',KEY_CD='qualifierResolverClass',VAL='org.kuali.student.lum.workflow.qualifierresolver.DepartmentCommitteeQualifierResolver' WHERE RTE_NODE_CFG_PARM_ID = '2463' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2913',KEY_CD='ruleSelector',VAL='Template' WHERE RTE_NODE_CFG_PARM_ID = '2464' 
/
UPDATE KREW_DOC_TYP_PROC_T SET DOC_TYP_ID='3002',INIT_RTE_NODE_ID='2912',NM='PRIMARY',INIT_IND=1,VER_NBR='3' WHERE DOC_TYP_PROC_ID = '2915'  AND VER_NBR = '2' 
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR)
VALUES (krew_doc_hdr_s.nextval,'3038','kuali.proposal.type.course.retire','1',0,1,'Credit Course Retirement','Credit Course Retirement','3002','','${lum.application.url}/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?view=COURSE_RETIRE_BY_PROPOSAL&idType=documentNumber','','','org.kuali.student.lum.workflow.CoursePostProcessorBase','','','','','2','','','','','c04d2afa-d83a-4d17-bfef-92ef8df0b2bb','1')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2971','3040','PreRoute','org.kuali.rice.kew.engine.node.InitialNode','',0,0,'','','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2656','2971','contentFragment','<start name="PreRoute">
<activationType>P</activationType>
<mandatoryRoute>false</mandatoryRoute>
<finalApproval>false</finalApproval>
</start>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2657','2971','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2658','2971','mandatoryRoute',0) 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2659','2971','finalApproval',0) 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2660','2971','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2972','3040','Department Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2661','2972','contentFragment','<role name="Department Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver</qualifierResolverClass>
<organizationTypeCode>kuali.org.Department</organizationTypeCode>
<organizationIdQualifierKey>department</organizationIdQualifierKey>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2662','2972','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2663','2972','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2664','2972','organizationTypeCode','kuali.org.Department') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2665','2972','organizationIdQualifierKey','department') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2666','2972','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2972','2971')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2973','3040','Division Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2667','2973','contentFragment','<role name="Division Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver</qualifierResolverClass>
<organizationTypeCode>kuali.org.Division</organizationTypeCode>
<organizationIdQualifierKey>division</organizationIdQualifierKey>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2668','2973','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2669','2973','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2670','2973','organizationTypeCode','kuali.org.Division') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2671','2973','organizationIdQualifierKey','division') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2672','2973','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2973','2972')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2974','3040','College Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2673','2974','contentFragment','<role name="College Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver</qualifierResolverClass>
<organizationTypeCode>kuali.org.College</organizationTypeCode>
<organizationIdQualifierKey>college</organizationIdQualifierKey>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2674','2974','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2675','2974','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2676','2974','organizationTypeCode','kuali.org.College') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2677','2974','organizationIdQualifierKey','college') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2678','2974','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2974','2973')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2975','3040','Senate Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2679','2975','contentFragment','<role name="Senate Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver</qualifierResolverClass>
<organizationId>141</organizationId>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2680','2975','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2681','2975','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2682','2975','organizationId','141') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2683','2975','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2975','2974')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2976','3040','Publication Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2684','2976','contentFragment','<role name="Publication Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver</qualifierResolverClass>
<organizationId>176</organizationId>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2685','2976','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2686','2976','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2687','2976','organizationId','176') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2688','2976','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2976','2975')
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('2977','3040','2971','PRIMARY',1,'1') 
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3037',DOC_TYP_NM='CluCreditCourseParentDocument',DOC_TYP_VER_NBR='1',ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Kuali Student Credit Course Parent Document',LBL='Kuali Student Credit Course Parent Document',PREV_DOC_TYP_VER_NBR='3001',DOC_HDR_ID='',DOC_HDLR_URL='${lum.application.url}/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?view=COURSE_PROPOSAL&idType=documentNumber',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='f0c6caf6-32c5-4def-998c-826ceeb75e71',VER_NBR='2' WHERE DOC_TYP_ID = '3038'  AND VER_NBR = '1' 
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3038',DOC_TYP_NM='kuali.proposal.type.course.modify',DOC_TYP_VER_NBR='0',ACTV_IND=1,CUR_IND=0,DOC_TYP_DESC='Credit Course Modification',LBL='Credit Course Modification',PREV_DOC_TYP_VER_NBR='',DOC_HDR_ID='',DOC_HDLR_URL='',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='org.kuali.student.lum.workflow.CoursePostProcessorBase',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='2A6CD5BB-3B9D-0E31-A35C-64115FE51E05',VER_NBR='3' WHERE DOC_TYP_ID = '3003'  AND VER_NBR = '2' 
/
UPDATE KREW_RTE_NODE_T SET DOC_TYP_ID='3003',NM='PreRoute',TYP='org.kuali.rice.kew.engine.node.InitialNode',RTE_MTHD_NM='',FNL_APRVR_IND=0,MNDTRY_RTE_IND=0,GRP_ID='',RTE_MTHD_CD='',ACTVN_TYP='P',BRCH_PROTO_ID='',NEXT_DOC_STAT='',VER_NBR='3' WHERE RTE_NODE_ID = '2943'  AND VER_NBR = '2' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2943',KEY_CD='contentFragment',VAL='<start name="PreRoute"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>' WHERE RTE_NODE_CFG_PARM_ID = '2572' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2943',KEY_CD='activationType',VAL='P' WHERE RTE_NODE_CFG_PARM_ID = '2573' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2943',KEY_CD='mandatoryRoute',VAL=0 WHERE RTE_NODE_CFG_PARM_ID = '2574' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2943',KEY_CD='finalApproval',VAL=0 WHERE RTE_NODE_CFG_PARM_ID = '2575' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2943',KEY_CD='ruleSelector',VAL='Template' WHERE RTE_NODE_CFG_PARM_ID = '2576' 
/
UPDATE KREW_RTE_NODE_T SET DOC_TYP_ID='3003',NM='Document Organization Review',TYP='org.kuali.rice.kew.engine.node.RoleNode',RTE_MTHD_NM='org.kuali.rice.kew.role.RoleRouteModule',FNL_APRVR_IND=0,MNDTRY_RTE_IND=0,GRP_ID='',RTE_MTHD_CD='RM',ACTVN_TYP='P',BRCH_PROTO_ID='',NEXT_DOC_STAT='',VER_NBR='2' WHERE RTE_NODE_ID = '2944'  AND VER_NBR = '1' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2944',KEY_CD='contentFragment',VAL='<role name="Document Organization Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrganizationQualifierResolver</qualifierResolverClass><organizationIdQualifierKey>orgId</organizationIdQualifierKey><organizationShortNameQualifierKey>org</organizationShortNameQualifierKey></role>' WHERE RTE_NODE_CFG_PARM_ID = '2577' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2944',KEY_CD='activationType',VAL='P' WHERE RTE_NODE_CFG_PARM_ID = '2578' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2944',KEY_CD='qualifierResolverClass',VAL='org.kuali.student.lum.workflow.qualifierresolver.CocOrganizationQualifierResolver' WHERE RTE_NODE_CFG_PARM_ID = '2579' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2944',KEY_CD='organizationIdQualifierKey',VAL='orgId' WHERE RTE_NODE_CFG_PARM_ID = '2580' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2944',KEY_CD='organizationShortNameQualifierKey',VAL='org' WHERE RTE_NODE_CFG_PARM_ID = '2581' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2944',KEY_CD='ruleSelector',VAL='Template' WHERE RTE_NODE_CFG_PARM_ID = '2582' 
/
UPDATE KREW_DOC_TYP_PROC_T SET DOC_TYP_ID='3003',INIT_RTE_NODE_ID='2943',NM='PRIMARY',INIT_IND=1,VER_NBR='3' WHERE DOC_TYP_PROC_ID = '2951'  AND VER_NBR = '2' 
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR)
VALUES (krew_doc_hdr_s.nextval,'3038','kuali.proposal.type.course.modify','1',1,1,'Credit Course Modification','Credit Course Modification','3003','','','','','org.kuali.student.lum.workflow.CoursePostProcessorBase','','','','','2','','','','','d8211168-d209-4fcd-b754-08ae15f7d642','1')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2978','3041','PreRoute','org.kuali.rice.kew.engine.node.InitialNode','',0,0,'','','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2689','2978','contentFragment','<start name="PreRoute">
<activationType>P</activationType>
<mandatoryRoute>false</mandatoryRoute>
<finalApproval>false</finalApproval>
</start>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2690','2978','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2691','2978','mandatoryRoute',0) 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2692','2978','finalApproval',0) 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2693','2978','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2979','3041','Document Organization Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2694','2979','contentFragment','<role name="Document Organization Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrganizationQualifierResolver</qualifierResolverClass>
<organizationIdQualifierKey>orgId</organizationIdQualifierKey>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2695','2979','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2696','2979','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.CocOrganizationQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2697','2979','organizationIdQualifierKey','orgId') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2698','2979','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2979','2978')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2980','3041','Publication Decision Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2699','2980','contentFragment','<role name="Publication Decision Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver</qualifierResolverClass>
<organizationId>176</organizationId>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2700','2980','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2701','2980','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2702','2980','organizationId','176') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2703','2980','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2980','2979')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2981','3041','Department Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2704','2981','contentFragment','<role name="Department Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver</qualifierResolverClass>
<organizationTypeCode>kuali.org.Department</organizationTypeCode>
<organizationIdQualifierKey>department</organizationIdQualifierKey>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2705','2981','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2706','2981','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2707','2981','organizationTypeCode','kuali.org.Department') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2708','2981','organizationIdQualifierKey','department') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2709','2981','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2981','2980')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2982','3041','Division Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2710','2982','contentFragment','<role name="Division Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver</qualifierResolverClass>
<organizationTypeCode>kuali.org.Division</organizationTypeCode>
<organizationIdQualifierKey>division</organizationIdQualifierKey>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2711','2982','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2712','2982','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2713','2982','organizationTypeCode','kuali.org.Division') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2714','2982','organizationIdQualifierKey','division') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2715','2982','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2982','2981')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2983','3041','College Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2716','2983','contentFragment','<role name="College Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver</qualifierResolverClass>
<organizationTypeCode>kuali.org.College</organizationTypeCode>
<organizationIdQualifierKey>college</organizationIdQualifierKey>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2717','2983','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2718','2983','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2719','2983','organizationTypeCode','kuali.org.College') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2720','2983','organizationIdQualifierKey','college') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2721','2983','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2983','2982')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2984','3041','Senate Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2722','2984','contentFragment','<role name="Senate Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver</qualifierResolverClass>
<organizationId>141</organizationId>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2723','2984','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2724','2984','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2725','2984','organizationId','141') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2726','2984','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2984','2983')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2985','3041','Publication Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2727','2985','contentFragment','<role name="Publication Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver</qualifierResolverClass>
<organizationId>176</organizationId>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2728','2985','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2729','2985','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2730','2985','organizationId','176') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2731','2985','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2985','2984')
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('2986','3041','2978','PRIMARY',1,'1') 
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3037',DOC_TYP_NM='CluCreditCourseParentDocument',DOC_TYP_VER_NBR='1',ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Kuali Student Credit Course Parent Document',LBL='Kuali Student Credit Course Parent Document',PREV_DOC_TYP_VER_NBR='3001',DOC_HDR_ID='',DOC_HDLR_URL='${lum.application.url}/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?view=COURSE_PROPOSAL&idType=documentNumber',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='f0c6caf6-32c5-4def-998c-826ceeb75e71',VER_NBR='3' WHERE DOC_TYP_ID = '3038'  AND VER_NBR = '2' 
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3038',DOC_TYP_NM='kuali.proposal.type.course.create',DOC_TYP_VER_NBR='0',ACTV_IND=1,CUR_IND=0,DOC_TYP_DESC='Credit Course Proposal',LBL='Credit Course Proposal',PREV_DOC_TYP_VER_NBR='',DOC_HDR_ID='',DOC_HDLR_URL='',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='org.kuali.student.lum.workflow.CoursePostProcessorBase',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='C7DFC7CD-8206-2AFC-8115-75EEDAA5AA0F',VER_NBR='3' WHERE DOC_TYP_ID = '3004'  AND VER_NBR = '2' 
/
UPDATE KREW_RTE_NODE_T SET DOC_TYP_ID='3004',NM='PreRoute',TYP='org.kuali.rice.kew.engine.node.InitialNode',RTE_MTHD_NM='',FNL_APRVR_IND=0,MNDTRY_RTE_IND=0,GRP_ID='',RTE_MTHD_CD='',ACTVN_TYP='P',BRCH_PROTO_ID='',NEXT_DOC_STAT='',VER_NBR='3' WHERE RTE_NODE_ID = '2936'  AND VER_NBR = '2' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2936',KEY_CD='contentFragment',VAL='<start name="PreRoute"><activationType>P</activationType><mandatoryRoute>false</mandatoryRoute><finalApproval>false</finalApproval></start>' WHERE RTE_NODE_CFG_PARM_ID = '2535' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2936',KEY_CD='activationType',VAL='P' WHERE RTE_NODE_CFG_PARM_ID = '2536' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2936',KEY_CD='mandatoryRoute',VAL=0 WHERE RTE_NODE_CFG_PARM_ID = '2537' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2936',KEY_CD='finalApproval',VAL=0 WHERE RTE_NODE_CFG_PARM_ID = '2538' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2936',KEY_CD='ruleSelector',VAL='Template' WHERE RTE_NODE_CFG_PARM_ID = '2539' 
/
UPDATE KREW_RTE_NODE_T SET DOC_TYP_ID='3004',NM='Department Review',TYP='org.kuali.rice.kew.engine.node.RoleNode',RTE_MTHD_NM='org.kuali.rice.kew.role.RoleRouteModule',FNL_APRVR_IND=0,MNDTRY_RTE_IND=0,GRP_ID='',RTE_MTHD_CD='RM',ACTVN_TYP='P',BRCH_PROTO_ID='',NEXT_DOC_STAT='',VER_NBR='2' WHERE RTE_NODE_ID = '2937'  AND VER_NBR = '1' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2937',KEY_CD='contentFragment',VAL='<role name="Department Review"><activationType>P</activationType><qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver</qualifierResolverClass><organizationTypeCode>kuali.org.Department</organizationTypeCode><organizationIdQualifierKey>departmentId</organizationIdQualifierKey><organizationShortNameQualifierKey>department</organizationShortNameQualifierKey></role>' WHERE RTE_NODE_CFG_PARM_ID = '2540' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2937',KEY_CD='activationType',VAL='P' WHERE RTE_NODE_CFG_PARM_ID = '2541' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2937',KEY_CD='qualifierResolverClass',VAL='org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver' WHERE RTE_NODE_CFG_PARM_ID = '2542' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2937',KEY_CD='organizationTypeCode',VAL='kuali.org.Department' WHERE RTE_NODE_CFG_PARM_ID = '2543' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2937',KEY_CD='organizationIdQualifierKey',VAL='departmentId' WHERE RTE_NODE_CFG_PARM_ID = '2544' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2937',KEY_CD='organizationShortNameQualifierKey',VAL='department' WHERE RTE_NODE_CFG_PARM_ID = '2545' 
/
UPDATE KREW_RTE_NODE_CFG_PARM_T SET RTE_NODE_ID='2937',KEY_CD='ruleSelector',VAL='Template' WHERE RTE_NODE_CFG_PARM_ID = '2546' 
/
UPDATE KREW_DOC_TYP_PROC_T SET DOC_TYP_ID='3004',INIT_RTE_NODE_ID='2936',NM='PRIMARY',INIT_IND=1,VER_NBR='3' WHERE DOC_TYP_PROC_ID = '2942'  AND VER_NBR = '2' 
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR)
VALUES (krew_doc_hdr_s.nextval,'3038','kuali.proposal.type.course.create','1',1,1,'Credit Course Proposal','Credit Course Proposal','3004','','','','','org.kuali.student.lum.workflow.CoursePostProcessorBase','','','','','2','','','','','cc274bad-8b2e-4792-8b34-56602d06fc03','1')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2987','3042','PreRoute','org.kuali.rice.kew.engine.node.InitialNode','',0,0,'','','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2732','2987','contentFragment','<start name="PreRoute">
<activationType>P</activationType>
<mandatoryRoute>false</mandatoryRoute>
<finalApproval>false</finalApproval>
</start>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2733','2987','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2734','2987','mandatoryRoute',0) 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2735','2987','finalApproval',0) 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2736','2987','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2988','3042','Department Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2737','2988','contentFragment','<role name="Department Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver</qualifierResolverClass>
<organizationTypeCode>kuali.org.Department</organizationTypeCode>
<organizationIdQualifierKey>department</organizationIdQualifierKey>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2738','2988','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2739','2988','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2740','2988','organizationTypeCode','kuali.org.Department') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2741','2988','organizationIdQualifierKey','department') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2742','2988','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2988','2987')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2989','3042','Division Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2743','2989','contentFragment','<role name="Division Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver</qualifierResolverClass>
<organizationTypeCode>kuali.org.Division</organizationTypeCode>
<organizationIdQualifierKey>division</organizationIdQualifierKey>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2744','2989','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2745','2989','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2746','2989','organizationTypeCode','kuali.org.Division') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2747','2989','organizationIdQualifierKey','division') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2748','2989','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2989','2988')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2990','3042','College Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2749','2990','contentFragment','<role name="College Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver</qualifierResolverClass>
<organizationTypeCode>kuali.org.College</organizationTypeCode>
<organizationIdQualifierKey>college</organizationIdQualifierKey>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2750','2990','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2751','2990','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2752','2990','organizationTypeCode','kuali.org.College') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2753','2990','organizationIdQualifierKey','college') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2754','2990','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2990','2989')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2991','3042','Senate Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2755','2991','contentFragment','<role name="Senate Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver</qualifierResolverClass>
<organizationId>141</organizationId>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2756','2991','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2757','2991','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2758','2991','organizationId','141') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2759','2991','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2991','2990')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2992','3042','Publication Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2760','2992','contentFragment','<role name="Publication Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver</qualifierResolverClass>
<organizationId>176</organizationId>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2761','2992','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2762','2992','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2763','2992','organizationId','176') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2764','2992','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2992','2991')
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('2993','3042','2987','PRIMARY',1,'1') 
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3037',DOC_TYP_NM='CluCreditCourseParentDocument',DOC_TYP_VER_NBR='1',ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Kuali Student Credit Course Parent Document',LBL='Kuali Student Credit Course Parent Document',PREV_DOC_TYP_VER_NBR='3001',DOC_HDR_ID='',DOC_HDLR_URL='${lum.application.url}/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?view=COURSE_PROPOSAL&idType=documentNumber',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='f0c6caf6-32c5-4def-998c-826ceeb75e71',VER_NBR='4' WHERE DOC_TYP_ID = '3038'  AND VER_NBR = '3' 
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR)
VALUES (krew_doc_hdr_s.nextval,'3038','kuali.proposal.type.course.create.admin','0',1,1,'Credit Course Admin Proposal','Credit Course Admin Proposal','','','','','','org.kuali.student.lum.workflow.CoursePostProcessorBase','','','','','2','','','','','ed80a491-cfd0-48b0-9256-ddd6b32ebc1e','1')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2994','3043','PreRoute','org.kuali.rice.kew.engine.node.InitialNode','',0,0,'','','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2765','2994','contentFragment','<start name="PreRoute">
<activationType>P</activationType>
<mandatoryRoute>false</mandatoryRoute>
<finalApproval>false</finalApproval>
</start>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2766','2994','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2767','2994','mandatoryRoute',0) 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2768','2994','finalApproval',0) 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2769','2994','ruleSelector','Template') 
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('2995','3043','2994','PRIMARY',1,'1') 
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3037',DOC_TYP_NM='CluCreditCourseParentDocument',DOC_TYP_VER_NBR='1',ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Kuali Student Credit Course Parent Document',LBL='Kuali Student Credit Course Parent Document',PREV_DOC_TYP_VER_NBR='3001',DOC_HDR_ID='',DOC_HDLR_URL='${lum.application.url}/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?view=COURSE_PROPOSAL&idType=documentNumber',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='f0c6caf6-32c5-4def-998c-826ceeb75e71',VER_NBR='5' WHERE DOC_TYP_ID = '3038'  AND VER_NBR = '4' 
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR)
VALUES (krew_doc_hdr_s.nextval,'3038','kuali.proposal.type.course.modify.admin','0',1,1,'Modify Credit Course Admin Proposal','Modify Credit Course Admin Proposal','','','','','','org.kuali.student.lum.workflow.CoursePostProcessorBase','','','','','2','','','','','9d3b6471-7208-4ef8-876b-64ee5df16b12','1')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2996','3044','PreRoute','org.kuali.rice.kew.engine.node.InitialNode','',0,0,'','','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2770','2996','contentFragment','<start name="PreRoute">
<activationType>P</activationType>
<mandatoryRoute>false</mandatoryRoute>
<finalApproval>false</finalApproval>
</start>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2771','2996','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2772','2996','mandatoryRoute',0) 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2773','2996','finalApproval',0) 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2774','2996','ruleSelector','Template') 
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('2997','3044','2996','PRIMARY',1,'1') 
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3037',DOC_TYP_NM='CluCreditCourseParentDocument',DOC_TYP_VER_NBR='1',ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Kuali Student Credit Course Parent Document',LBL='Kuali Student Credit Course Parent Document',PREV_DOC_TYP_VER_NBR='3001',DOC_HDR_ID='',DOC_HDLR_URL='${lum.application.url}/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?view=COURSE_PROPOSAL&idType=documentNumber',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='f0c6caf6-32c5-4def-998c-826ceeb75e71',VER_NBR='6' WHERE DOC_TYP_ID = '3038'  AND VER_NBR = '5' 
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR)
VALUES (krew_doc_hdr_s.nextval,'3039','kuali.proposal.type.majorDiscipline.create','0',1,1,'Create Major Discipline Proposal','Create Major Discipline Proposal','','','','','','org.kuali.student.lum.workflow.ProgramPostProcessorBase','','','','','2','','','','','eee0417e-971d-4099-a264-71fc658b4a1f','1')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2998','3045','PreRoute','org.kuali.rice.kew.engine.node.InitialNode','',0,0,'','','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2775','2998','contentFragment','<start name="PreRoute">
<activationType>P</activationType>
<mandatoryRoute>false</mandatoryRoute>
<finalApproval>false</finalApproval>
</start>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2776','2998','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2777','2998','mandatoryRoute',0) 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2778','2998','finalApproval',0) 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2779','2998','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('2999','3045','Department Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2780','2999','contentFragment','<role name="Department Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver</qualifierResolverClass>
<organizationTypeCode>kuali.org.Department</organizationTypeCode>
<organizationIdQualifierKey>department</organizationIdQualifierKey>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2781','2999','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2782','2999','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2783','2999','organizationTypeCode','kuali.org.Department') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2784','2999','organizationIdQualifierKey','department') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2785','2999','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('2999','2998')
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('3000','3045','2998','PRIMARY',1,'1') 
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3037',DOC_TYP_NM='CluMajorDisciplineParentDocument',DOC_TYP_VER_NBR='0',ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Kuali Student Major Discipline Parent Document',LBL='Kuali Student Major Discipline Parent Document',PREV_DOC_TYP_VER_NBR='',DOC_HDR_ID='',DOC_HDLR_URL='${lum.application.url}/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?view=PROGRAM_PROPOSAL&idType=documentNumber',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='a2d9776b-e132-42de-914c-e0e54b3a1f6d',VER_NBR='2' WHERE DOC_TYP_ID = '3039'  AND VER_NBR = '1' 
/
INSERT INTO KREW_DOC_TYP_T (DOC_TYP_ID,PARNT_ID,DOC_TYP_NM,DOC_TYP_VER_NBR,ACTV_IND,CUR_IND,DOC_TYP_DESC,LBL,PREV_DOC_TYP_VER_NBR,DOC_HDR_ID,DOC_HDLR_URL,HELP_DEF_URL,DOC_SEARCH_HELP_URL,POST_PRCSR,GRP_ID,BLNKT_APPR_GRP_ID,BLNKT_APPR_PLCY,RPT_GRP_ID,RTE_VER_NBR,NOTIFY_ADDR,SEC_XML,EMAIL_XSL,APPL_ID,OBJ_ID,VER_NBR)
VALUES (krew_doc_hdr_s.nextval,'3039','kuali.proposal.type.majorDiscipline.modify','0',1,1,'Modify Major Discipline Proposal','Modify Major Discipline Proposal','','','','','','org.kuali.student.lum.workflow.ProgramPostProcessorBase','','','','','2','','','','','97ec4bca-6817-44af-ae69-77d1c8231335','1')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('3001','3046','PreRoute','org.kuali.rice.kew.engine.node.InitialNode','',0,0,'','','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2786','3001','contentFragment','<start name="PreRoute">
<activationType>P</activationType>
<mandatoryRoute>false</mandatoryRoute>
<finalApproval>false</finalApproval>
</start>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2787','3001','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2788','3001','mandatoryRoute',0) 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2789','3001','finalApproval',0) 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2790','3001','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('3002','3046','Department Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2791','3002','contentFragment','<role name="Department Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver</qualifierResolverClass>
<organizationTypeCode>kuali.org.Department</organizationTypeCode>
<organizationIdQualifierKey>department</organizationIdQualifierKey>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2792','3002','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2793','3002','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2794','3002','organizationTypeCode','kuali.org.Department') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2795','3002','organizationIdQualifierKey','department') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2796','3002','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('3002','3001')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('3003','3046','Division Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2797','3003','contentFragment','<role name="Division Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver</qualifierResolverClass>
<organizationTypeCode>kuali.org.Division</organizationTypeCode>
<organizationIdQualifierKey>division</organizationIdQualifierKey>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2798','3003','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2799','3003','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2800','3003','organizationTypeCode','kuali.org.Division') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2801','3003','organizationIdQualifierKey','division') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2802','3003','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('3003','3002')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('3004','3046','College Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2803','3004','contentFragment','<role name="College Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver</qualifierResolverClass>
<organizationTypeCode>kuali.org.College</organizationTypeCode>
<organizationIdQualifierKey>college</organizationIdQualifierKey>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2804','3004','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2805','3004','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2806','3004','organizationTypeCode','kuali.org.College') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2807','3004','organizationIdQualifierKey','college') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2808','3004','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('3004','3003')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('3005','3046','Curriculum Admin Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2809','3005','contentFragment','<role name="Curriculum Admin Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver</qualifierResolverClass>
<organizationId>141</organizationId>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2810','3005','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2811','3005','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2812','3005','organizationId','141') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2813','3005','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('3005','3004')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('3006','3046','Senate Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2814','3006','contentFragment','<role name="Senate Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver</qualifierResolverClass>
<organizationId>141</organizationId>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2815','3006','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2816','3006','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2817','3006','organizationId','141') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2818','3006','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('3006','3005')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('3007','3046','Presidents Office Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2819','3007','contentFragment','<role name="Presidents Office Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver</qualifierResolverClass>
<organizationId>141</organizationId>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2820','3007','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2821','3007','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2822','3007','organizationId','141') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2823','3007','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('3007','3006')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('3008','3046','System Office Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2824','3008','contentFragment','<role name="System Office Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver</qualifierResolverClass>
<organizationId>141</organizationId>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2825','3008','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2826','3008','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2827','3008','organizationId','141') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2828','3008','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('3008','3007')
/
INSERT INTO KREW_RTE_NODE_T (RTE_NODE_ID,DOC_TYP_ID,NM,TYP,RTE_MTHD_NM,FNL_APRVR_IND,MNDTRY_RTE_IND,GRP_ID,RTE_MTHD_CD,ACTVN_TYP,BRCH_PROTO_ID,NEXT_DOC_STAT,VER_NBR) VALUES ('3009','3046','Publication Review','org.kuali.rice.kew.engine.node.RoleNode','org.kuali.rice.kew.role.RoleRouteModule',0,0,'','RM','P','','','1') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2829','3009','contentFragment','<role name="Publication Review">
<activationType>P</activationType>
<qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver</qualifierResolverClass>
<organizationId>176</organizationId>
</role>
') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2830','3009','activationType','P') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2831','3009','qualifierResolverClass','org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2832','3009','organizationId','176') 
/
INSERT INTO KREW_RTE_NODE_CFG_PARM_T (RTE_NODE_CFG_PARM_ID,RTE_NODE_ID,KEY_CD,VAL) VALUES ('2833','3009','ruleSelector','Template') 
/
INSERT INTO KREW_RTE_NODE_LNK_T (TO_RTE_NODE_ID,FROM_RTE_NODE_ID) VALUES ('3009','3008')
/
INSERT INTO KREW_DOC_TYP_PROC_T (DOC_TYP_PROC_ID,DOC_TYP_ID,INIT_RTE_NODE_ID,NM,INIT_IND,VER_NBR) VALUES ('3010','3046','3001','PRIMARY',1,'1') 
/
UPDATE KREW_DOC_TYP_T SET PARNT_ID='3037',DOC_TYP_NM='CluMajorDisciplineParentDocument',DOC_TYP_VER_NBR='0',ACTV_IND=1,CUR_IND=1,DOC_TYP_DESC='Kuali Student Major Discipline Parent Document',LBL='Kuali Student Major Discipline Parent Document',PREV_DOC_TYP_VER_NBR='',DOC_HDR_ID='',DOC_HDLR_URL='${lum.application.url}/org.kuali.student.lum.lu.ui.main.LUMMain/LUMMain.jsp?view=PROGRAM_PROPOSAL&idType=documentNumber',HELP_DEF_URL='',DOC_SEARCH_HELP_URL='',POST_PRCSR='',GRP_ID='',BLNKT_APPR_GRP_ID='',BLNKT_APPR_PLCY='',RPT_GRP_ID='',RTE_VER_NBR='2',NOTIFY_ADDR='',SEC_XML='',EMAIL_XSL='',APPL_ID='',OBJ_ID='a2d9776b-e132-42de-914c-e0e54b3a1f6d',VER_NBR='3' WHERE DOC_TYP_ID = '3039'  AND VER_NBR = '2' 
/