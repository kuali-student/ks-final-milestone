---Insert free form text
INSERT INTO KRMS_TYP_T (ACTV,NM,NMSPC_CD,SRVC_NM,TYP_ID,VER_NBR)
  VALUES ('Y','kuali.krms.proposition.type.exam.freeform.text','KS-SYS','simplePropositionTypeService',CONCAT('KS-KRMS-TYP-', KS_RICE_ID_S.NEXTVAL),0)
/

--Inster NL for the free form text
INSERT INTO KRMS_NL_TMPL_T (VER_NBR,LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TYP_ID,TMPL)
  VALUES (0,'en',CONCAT('KS-KRMS-NL-TMPL-', KS_RICE_ID_S.NEXTVAL),(SELECT NL_USAGE_ID FROM KRMS_NL_USAGE_T where NM like 'kuali.krms.catalog'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.exam.freeform.text'),'$freeText')
/

INSERT INTO KRMS_NL_TMPL_T (VER_NBR,LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TYP_ID,TMPL)
  VALUES (0,'en',CONCAT('KS-KRMS-NL-TMPL-', KS_RICE_ID_S.NEXTVAL),(SELECT NL_USAGE_ID FROM KRMS_NL_USAGE_T where NM like 'kuali.krms.example'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.exam.freeform.text'),'Free Form Text')
/

INSERT INTO KRMS_NL_TMPL_T (VER_NBR,LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TYP_ID,TMPL)
  VALUES (0,'en',CONCAT('KS-KRMS-NL-TMPL-', KS_RICE_ID_S.NEXTVAL),(SELECT NL_USAGE_ID FROM KRMS_NL_USAGE_T where NM like 'kuali.krms.type.description'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.exam.freeform.text'),'Free Form Text')
/

INSERT INTO KRMS_NL_TMPL_T (VER_NBR,LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TYP_ID,TMPL)
  VALUES (0,'en',CONCAT('KS-KRMS-NL-TMPL-', KS_RICE_ID_S.NEXTVAL),(SELECT NL_USAGE_ID FROM KRMS_NL_USAGE_T where NM like 'kuali.krms.preview'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.exam.freeform.text'),'$freeText')
/

INSERT INTO KRMS_NL_TMPL_T (VER_NBR,LANG_CD,NL_TMPL_ID,NL_USAGE_ID,TYP_ID,TMPL)
  VALUES (0,'en',CONCAT('KS-KRMS-NL-TMPL-', KS_RICE_ID_S.NEXTVAL),(SELECT NL_USAGE_ID FROM KRMS_NL_USAGE_T where NM like 'kuali.krms.edit'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.exam.freeform.text'),'$freeText')
/

--Delete Previous relationship with freeformText student eligibility
DELETE
  FROM KRMS_TYP_RELN_T
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.final.exam.common')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.freeform.text')
/

DELETE
  FROM KRMS_TYP_RELN_T
 WHERE FROM_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.rule.type.final.exam.standard')
   AND TO_TYP_ID =
       (SELECT TYP_ID
          FROM KRMS_TYP_T
         WHERE NM = 'kuali.krms.proposition.type.freeform.text')
/

--Insert Relationship
INSERT INTO KRMS_TYP_RELN_T (TYP_RELN_ID,FROM_TYP_ID,TO_TYP_ID,RELN_TYP,SEQ_NO,VER_NBR,ACTV) VALUES (CONCAT('KS-KRMS-TYP-RELN-', KS_RICE_ID_S.NEXTVAL),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.final.exam.common'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.exam.freeform.text'),'A',3,0,'Y')
/

INSERT INTO KRMS_TYP_RELN_T (TYP_RELN_ID,FROM_TYP_ID,TO_TYP_ID,RELN_TYP,SEQ_NO,VER_NBR,ACTV) VALUES (CONCAT('KS-KRMS-TYP-RELN-', KS_RICE_ID_S.NEXTVAL),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.rule.type.final.exam.standard'),(SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.proposition.type.exam.freeform.text'),'A',3,0,'Y')
/
---Insert TermSpec
INSERT INTO KRMS_TERM_SPEC_T(TERM_SPEC_ID,DESC_TXT, NM, NMSPC_CD, TYP, ACTV,  VER_NBR) VALUES (CONCAT('KS-KRMS-TERM-SPEC-', KS_RICE_ID_S.NEXTVAL), 'Exam Free Form Text', 'ExamFreeFormText','KS-SYS','java.lang.Boolean', 'Y', 1 )
/
--Insert term Resolver
INSERT INTO KRMS_TERM_RSLVR_T (TERM_RSLVR_ID, NM, TYP_ID, OUTPUT_TERM_SPEC_ID, ACTV, VER_NBR, NMSPC_CD) VALUES (CONCAT('KS-KRMS-TERM-RSLVR-', KS_RICE_ID_S.NEXTVAL), 'ExamFreeFormText', (SELECT TYP_ID FROM KRMS_TYP_T WHERE NM = 'kuali.krms.termresolver.type.final.exam'), (SELECT TERM_SPEC_ID FROM KRMS_TERM_SPEC_T WHERE NM ='ExamFreeFormText'), 'Y', 0, 'KS-SYS')
/

---Update Term, link conflict resolution to exam Free form text term Spec
UPDATE KRMS_TERM_T T
SET T.TERM_SPEC_ID = (SELECT TERM_SPEC_ID FROM KRMS_TERM_SPEC_T WHERE NM ='ExamFreeFormText')
WHERE T.TERM_ID = 'KS-KRMS-TERM-13489'
/

UPDATE KRMS_TERM_T T
SET T.TERM_SPEC_ID = (SELECT TERM_SPEC_ID FROM KRMS_TERM_SPEC_T WHERE NM ='ExamFreeFormText')
WHERE T.TERM_ID = 'KS-KRMS-TERM-13488'
/

