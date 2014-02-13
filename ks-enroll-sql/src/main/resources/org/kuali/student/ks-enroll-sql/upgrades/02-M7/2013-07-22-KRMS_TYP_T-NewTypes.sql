--KSENROLL-8169
Insert into  KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
  values ('Y','kuali.krms.proposition.type.max.limit.courses.at.org.for.program','KS-SYS','simplePropositionTypeService','10078',0)
/
Insert into  KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
  values ('Y','kuali.krms.proposition.type.max.limit.courses.at.org.duration.for.program','KS-SYS','simplePropositionTypeService','10079',0)
/

Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1551','KS-KRMS-NL-USAGE-1004','Students admitted to <program> may take no more than <n> courses in the <org> in <duration><durationType>','10078',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1552','KS-KRMS-NL-USAGE-1003','Students admitted to $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) may take no more than $intValue $NLHelper.getProperGrammar($intValue, "course") in the $org.getLongName() in  $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase()','10078',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1553','KS-KRMS-NL-USAGE-1000','Students admitted to $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) may take no more than $intValue $NLHelper.getProperGrammar($intValue, "course") in the $org.getLongName() in  $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase()','10078',0)
/

Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1554','KS-KRMS-NL-USAGE-1004','Students not admitted to <program> may take no more than <n> courses in the <org> in <duration><durationType>','10079',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1555','KS-KRMS-NL-USAGE-1003','Students not admitted to $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) may take no more than $intValue $NLHelper.getProperGrammar($intValue, "course") in the $org.getLongName() in $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase()','10079',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1556','KS-KRMS-NL-USAGE-1000','Students not admitted to $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) may take no more than $intValue $NLHelper.getProperGrammar($intValue, "course") in the $org.getLongName() in $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase()','10079',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1566','KS-KRMS-NL-USAGE-1005','students not admitted to $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) may take no more than $intValue $NLHelper.getProperGrammar($intValue, "course") in the $org.getLongName() in $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase()','10079',0)
/
Insert into KRMS_NL_TMPL_T (LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TMPL,TYP_ID,VER_NBR) values ('en','KS-KRMS-NL-TMPL-1567','KS-KRMS-NL-USAGE-1005','students admitted to $NLHelper.getCluOrCluSetAsLongNames($programClu,$programCluSet) may take no more than $intValue $NLHelper.getProperGrammar($intValue, "course") in the $org.getLongName() in  $duration $NLHelper.getProperGrammar($duration, $durationType.getName()).toLowerCase()','10078',0)
/


Insert into KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  values ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'),'A',15,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.max.limit.courses.at.org.for.program'),'KS-KRMS-NL-USAGE-1162',0)
/
Insert into KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  values ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq'),'A',15,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.max.limit.courses.at.org.duration.for.program'),'KS-KRMS-NL-USAGE-1163',0)
/

