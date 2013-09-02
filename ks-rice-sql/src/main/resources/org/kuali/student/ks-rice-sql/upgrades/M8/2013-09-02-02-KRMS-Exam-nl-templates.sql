Insert into KRMS_NL_TMPL_T (VER_NBR,LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TYP_ID,TMPL) values (0,'en',CONCAT('KS-KRMS-NL-TMPL-', KS_RICE_ID_S.NEXTVAL),(SELECT NL_USAGE_ID FROM KRMS_NL_USAGE_T where NM like 'kuali.krms.type.description'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.agenda.type.final.exam.common'),'Common Final Exam:')
/
Insert into KRMS_NL_TMPL_T (VER_NBR,LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TYP_ID,TMPL) values (0,'en',CONCAT('KS-KRMS-NL-TMPL-', KS_RICE_ID_S.NEXTVAL),(SELECT NL_USAGE_ID FROM KRMS_NL_USAGE_T where NM like 'kuali.krms.type.description'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.agenda.type.final.exam.standard'),'Standard Final Exam:')
/

Insert into KRMS_NL_TMPL_T (VER_NBR,LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TYP_ID,TMPL) values (0,'en',CONCAT('KS-KRMS-NL-TMPL-', KS_RICE_ID_S.NEXTVAL),(SELECT NL_USAGE_ID FROM KRMS_NL_USAGE_T where NM like 'kuali.krms.type.description'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.final.exam.common'),'Common Final Exam')
/
Insert into KRMS_NL_TMPL_T (VER_NBR,LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TYP_ID,TMPL) values (0,'en',CONCAT('KS-KRMS-NL-TMPL-', KS_RICE_ID_S.NEXTVAL),(SELECT NL_USAGE_ID FROM KRMS_NL_USAGE_T where NM like 'kuali.krms.type.description'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.final.exam.standard'),'Standard Final Exam')
/