--KSENROLL-8184
Insert into  KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
  values ('Y','kuali.krms.proposition.type.memberof.population','KS-SYS','simplePropositionTypeService','10080',0)
/

Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1568','KS-KRMS-NL-USAGE-1004','Student must be a member of <population>','10080',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1569','KS-KRMS-NL-USAGE-1003','Student must be a member of $population.name','10080',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1570','KS-KRMS-NL-USAGE-1000','Student must be a member of $population.name','10080',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1571','KS-KRMS-NL-USAGE-1005','student must be a member of $population.name','10080',0)
/


Insert into KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  values ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'),'A',15,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.memberof.population'),'KS-KRMS-NL-USAGE-1164',0)
/
Insert into KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  values ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.recommendedPreparation'),'A',15,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.memberof.population'),'KS-KRMS-NL-USAGE-1165',0)
/
