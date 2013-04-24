INSERT INTO KRMS_NL_USAGE_T (NL_USAGE_ID, NM, NMSPC_CD, DESC_TXT, ACTV, VER_NBR)
  VALUES ('KS-KRMS-NL-USAGE-1006', 'kuali.krms.type.instruction', 'KS-SYS', 'Kuali Rule Type Instructions', 'Y', 0)
/

INSERT INTO KRMS_NL_TMPL_T (NL_TMPL_ID, LANG_CD, NL_USAGE_ID, TYP_ID, TMPL, VER_NBR)
  VALUES ('KS-KRMS-NL-TMPL-1403', 'en', 'KS-KRMS-NL-USAGE-1006', (SELECT TYP_ID FROM KRMS_TYP_T where NM = 'kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq' AND NMSPC_CD = 'KS-SYS'), 'Add conditions that will restrict student enrollment, addressing restrictions to majors, locations, credit level requirements, etc. or Add courses, with or without grade requirements, which a student must have completed in order to enroll.', 0)
/
INSERT INTO KRMS_NL_TMPL_T (NL_TMPL_ID, LANG_CD, NL_USAGE_ID, TYP_ID, TMPL, VER_NBR)
  VALUES ('KS-KRMS-NL-TMPL-1404', 'en', 'KS-KRMS-NL-USAGE-1006', (SELECT TYP_ID FROM KRMS_TYP_T where NM = 'kuali.krms.rule.type.course.academicReadiness.coreq' AND NMSPC_CD = 'KS-SYS'), 'Add conditions that will restrict student enrollment, addressing restrictions to majors, locations, credit level requirements, etc. or Add courses, with or without grade requirements, which a student must have completed in order to enroll.', 0)
/
INSERT INTO KRMS_NL_TMPL_T (NL_TMPL_ID, LANG_CD, NL_USAGE_ID, TYP_ID, TMPL, VER_NBR)
  VALUES ('KS-KRMS-NL-TMPL-1405', 'en', 'KS-KRMS-NL-USAGE-1006', (SELECT TYP_ID FROM KRMS_TYP_T where NM = 'kuali.krms.rule.type.course.recommendedPreparation' AND NMSPC_CD = 'KS-SYS'), 'The courses and/or preparation added here will not prevent students from registering, but will be printed in the catalog.', 0)
/
INSERT INTO KRMS_NL_TMPL_T (NL_TMPL_ID, LANG_CD, NL_USAGE_ID, TYP_ID, TMPL, VER_NBR)
  VALUES ('KS-KRMS-NL-TMPL-1406', 'en', 'KS-KRMS-NL-USAGE-1006', (SELECT TYP_ID FROM KRMS_TYP_T where NM = 'kuali.krms.rule.type.course.academicReadiness.antireq' AND NMSPC_CD = 'KS-SYS'), 'Add courses that, if completed, would prevent a student from enrolling in this course.', 0)
/
INSERT INTO KRMS_NL_TMPL_T (NL_TMPL_ID, LANG_CD, NL_USAGE_ID, TYP_ID, TMPL, VER_NBR)
  VALUES ('KS-KRMS-NL-TMPL-1407', 'en', 'KS-KRMS-NL-USAGE-1006', (SELECT TYP_ID FROM KRMS_TYP_T where NM = 'kuali.krms.rule.type.course.credit.restriction' AND NMSPC_CD = 'KS-SYS'), 'Enrollment in or completion of another course that will restrict the credits to be awarded.', 0)
/
INSERT INTO KRMS_NL_TMPL_T (NL_TMPL_ID, LANG_CD, NL_USAGE_ID, TYP_ID, TMPL, VER_NBR)
  VALUES ('KS-KRMS-NL-TMPL-1408', 'en', 'KS-KRMS-NL-USAGE-1006', (SELECT TYP_ID FROM KRMS_TYP_T where NM = 'kuali.krms.rule.type.course.credit.repeatable' AND NMSPC_CD = 'KS-SYS'), 'Course repeatable for credit.', 0)
/