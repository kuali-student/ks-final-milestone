INSERT INTO KRMS_NL_USAGE_T (NL_USAGE_ID, NM, NMSPC_CD, DESC_TXT, ACTV, VER_NBR) VALUES (KRMS_NL_USAGE_S.NEXTVAL, 'kuali.krms.type.instruction', 'KS-SYS', 'Kuali Rule Type Instructions', 'Y', 0)
/
INSERT INTO KRMS_NL_TMPL_T (NL_TMPL_ID, LANG_CD, NL_USAGE_ID, TYP_ID, TMPL, VER_NBR) VALUES (KRMS_NL_TMPL_S.NEXTVAL, 'en', '10006', '10010', 'Add conditions that will restrict student enrollment, addressing restrictions to majors, locations, credit level requirements, etc. or Add courses, with or without grade requirements, which a student must have completed in order to enroll.', 0)
/
INSERT INTO KRMS_NL_TMPL_T (NL_TMPL_ID, LANG_CD, NL_USAGE_ID, TYP_ID, TMPL, VER_NBR) VALUES (KRMS_NL_TMPL_S.NEXTVAL, 'en', '10006', '10006', 'Add conditions that will restrict student enrollment, addressing restrictions to majors, locations, credit level requirements, etc. or Add courses, with or without grade requirements, which a student must have completed in order to enroll. (TL: Why is this the same definition as above? It should at least specify that the restriction is based on concurrent enrolment, not completion.)', 0)
/
INSERT INTO KRMS_NL_TMPL_T (NL_TMPL_ID, LANG_CD, NL_USAGE_ID, TYP_ID, TMPL, VER_NBR) VALUES (KRMS_NL_TMPL_S.NEXTVAL, 'en', '10006', '10008', 'The courses and/or preparation added here will not prevent students from registering, but will be printed in the catalog.', 0)
/
INSERT INTO KRMS_NL_TMPL_T (NL_TMPL_ID, LANG_CD, NL_USAGE_ID, TYP_ID, TMPL, VER_NBR) VALUES (KRMS_NL_TMPL_S.NEXTVAL, 'en', '10006', '10005', 'Add courses that, if completed, would prevent a student from enrolling in this course.', 0)
/
INSERT INTO KRMS_NL_TMPL_T (NL_TMPL_ID, LANG_CD, NL_USAGE_ID, TYP_ID, TMPL, VER_NBR) VALUES (KRMS_NL_TMPL_S.NEXTVAL, 'en', '10006', '10012', 'Enrollment in or completion of another course that will restrict the credits to be awarded.', 0)
/
INSERT INTO KRMS_NL_TMPL_T (NL_TMPL_ID, LANG_CD, NL_USAGE_ID, TYP_ID, TMPL, VER_NBR) VALUES (KRMS_NL_TMPL_S.NEXTVAL, 'en', '10006', '10011', 'Course repeatable for credit.', 0)
/