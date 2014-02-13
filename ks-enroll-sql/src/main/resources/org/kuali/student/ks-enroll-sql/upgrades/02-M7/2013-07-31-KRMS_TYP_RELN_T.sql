--KSENROLL-8048
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.academicReadiness.coreq'),'A',3,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.credits.earned.min'),'KS-KRMS-NL-USAGE-1166',0)
/
INSERT INTO KRMS_TYP_RELN_T (ACTV,FROM_TYP_ID,RELN_TYP,SEQ_NO,TO_TYP_ID,TYP_RELN_ID,VER_NBR)
  VALUES ('Y',(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.course.academicReadiness.coreq'),'A',4,(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.cumulative.gpa.min'),'KS-KRMS-NL-USAGE-1167',0)
/

Update KRMS_TYP_RELN_T set SEQ_NO = 5
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.course.academicReadiness.coreq')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.freeform.text')
/