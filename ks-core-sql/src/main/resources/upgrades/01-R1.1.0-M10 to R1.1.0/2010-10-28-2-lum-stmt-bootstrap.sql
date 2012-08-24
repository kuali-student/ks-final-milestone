INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, NAME, TYPE_DESC, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('kuali.reqComponent.type.program.cumulative.gpa.min',              'Minimum cumulative GPA', 'Must have earned a minimum cumulative GPA of <GPA>', to_date('2000-01-01', 'yyyy-mm-dd'), to_date('2037-12-31', 'yyyy-mm-dd'), 0)
/
INSERT INTO KSST_REQ_COM_TYPE (TYPE_KEY, NAME, TYPE_DESC, EFF_DT, EXPIR_DT, VER_NBR) VALUES ('kuali.reqComponent.type.program.duration.gpa.min',                'Minimum duration GPA', 'Must have earned a minimum <duration> GPA of <GPA>', to_date('2000-01-01', 'yyyy-mm-dd'), to_date('2037-12-31', 'yyyy-mm-dd'), 0)
/

INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.cumulative.gpa.min','kuali.reqComponent.field.type.gpa')
/
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.duration.gpa.min','kuali.reqComponent.field.type.gpa')
/
INSERT INTO KSST_RCTYP_JN_RCFLDTYP (REQ_COMP_TYPE_ID,REQ_COMP_FIELD_TYPE_ID) VALUES ('kuali.reqComponent.type.program.duration.gpa.min','kuali.reqComponent.field.type.durationType.id')
/

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE, VER_NBR) VALUES ('1016', 'KUALI.RULE', 'Must have earned a minimum cumulative GPA of $gpa', 'kuali.reqComponent.type.program.cumulative.gpa.min', 'en', 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE, VER_NBR) VALUES ('1017', 'KUALI.RULE', 'Must have earned a minimum $durationType.getName().toLowerCase() GPA of $gpa', 'kuali.reqComponent.type.program.duration.gpa.min', 'en', 0)
/

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE, VER_NBR) VALUES ('1116', 'KUALI.RULE.PREVIEW', 'Must have earned a minimum cumulative GPA of $gpa', 'kuali.reqComponent.type.program.cumulative.gpa.min', 'en', 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE, VER_NBR) VALUES ('1117', 'KUALI.RULE.PREVIEW', 'Must have earned a minimum $durationType.getName().toLowerCase() GPA of $gpa', 'kuali.reqComponent.type.program.duration.gpa.min', 'en', 0)
/

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE, VER_NBR) VALUES ('2016', 'KUALI.RULE.EXAMPLE', 'Must have earned a minimum cumulative GPA of 2.5', 'kuali.reqComponent.type.program.cumulative.gpa.min', 'en', 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE, VER_NBR) VALUES ('2017', 'KUALI.RULE.EXAMPLE', 'Must have earned a minimum semester GPA of 3.0', 'kuali.reqComponent.type.program.duration.gpa.min', 'en', 0)
/

INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE, VER_NBR) VALUES ('3016', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.gpa;reqCompFieldLabel=GPA>', 'kuali.reqComponent.type.program.cumulative.gpa.min', 'en', 0)
/
INSERT INTO KSST_REQ_COM_TYPE_NL_TMPL (ID, NL_USUAGE_TYPE_KEY, TEMPLATE, OWNER, LANGUAGE, VER_NBR) VALUES ('3017', 'KUALI.RULE.COMPOSITION', '<reqCompFieldType=kuali.reqComponent.field.type.durationType.id;reqCompFieldLabel=Duration Type> <reqCompFieldType=kuali.reqComponent.field.type.gpa;reqCompFieldLabel=GPA>', 'kuali.reqComponent.type.program.duration.gpa.min', 'en', 0)
/

TRUNCATE TABLE KSST_STMT_TYP_JN_RC_TYP
/

-- STMT_TYPE <-> REQ_COM_TYPE
-- Courses
-- Courses - Anti-req
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CA-1', 'kuali.statement.type.course.academicReadiness.antireq', 'kuali.reqComponent.type.course.notcompleted', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CA-2', 'kuali.statement.type.course.academicReadiness.antireq', 'kuali.reqComponent.type.course.courseset.completed.none', 2, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CA-3', 'kuali.statement.type.course.academicReadiness.antireq', 'kuali.reqComponent.type.course.courseset.credits.completed.none', 3, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CA-4', 'kuali.statement.type.course.academicReadiness.antireq', 'kuali.reqComponent.type.course.courseset.credits.completed.max', 4, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CA-5', 'kuali.statement.type.course.academicReadiness.antireq', 'kuali.reqComponent.type.course.test.score.max', 5, 0)
/
-- Courses - Pre-req
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CP-1', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.completed.all', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CP-2', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.completed.nof', 2, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CP-3', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.credits.completed.nof', 3, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CP-4', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.gpa.min', 4, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CP-5', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.grade.min', 5, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CP-6', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.grade.max', 6, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CP-7', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.permission.org.required', 7, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CP-8', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.permission.instructor.required', 8, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CP-9', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.test.score.min', 9, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CP-10', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.nof.grade.min', 10, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CP-11', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.program.admitted.org.duration', 11, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CP-12', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.org.credits.completed.min', 12, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CP-13', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.completed', 13, 0)
/
-- Courses - Co-req
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CC-1', 'kuali.statement.type.course.academicReadiness.coreq', 'kuali.reqComponent.type.course.enrolled', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CC-2', 'kuali.statement.type.course.academicReadiness.coreq', 'kuali.reqComponent.type.course.courseset.enrolled.nof', 2, 0)
/
-- Courses - Student Eligibility
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CS-1', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.course.program.notadmitted.org.duration', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CS-2', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.course.org.program.admitted', 2, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CS-3', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.course.program.notadmitted', 3, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CS-4', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.course.program.admitted', 4, 0)
/
-- Courses - Student Eligibility and Pre-req
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CSP-1', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.completed', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CSP-2', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.courseset.completed.all', 2, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CSP-3', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.courseset.completed.nof', 3, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CSP-4', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.courseset.credits.completed.nof', 4, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CSP-5', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.org.credits.completed.min', 5, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CSP-6', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.courseset.gpa.min', 6, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CSP-7', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.courseset.grade.min', 7, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CSP-8', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.courseset.nof.grade.min', 8, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CSP-9', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.courseset.grade.max', 9, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CSP-10', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.program.admitted', 10, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CSP-11', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.org.program.admitted', 11, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CSP-12', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.program.notadmitted', 12, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CSP-13', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.permission.instructor.required', 13, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CSP-14', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.permission.org.required', 14, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CSP-15', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.test.score.min', 15, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CSP-16', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.program.admitted.org.duration', 16, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CSP-17', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.program.notadmitted.org.duration', 17, 0)
/
-- Courses - Repeatable for Credit
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CRC-1', 'kuali.statement.type.course.credit.repeatable', 'kuali.reqComponent.type.course.credits.repeat.max', 1, 0)
/
-- Courses - Recommended Preparation
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CRP-1', 'kuali.statement.type.course.recommendedPreparation', 'kuali.reqComponent.type.course.completed', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CRP-2', 'kuali.statement.type.course.recommendedPreparation', 'kuali.reqComponent.type.course.org.credits.completed.min', 2, 0)
/
-- Courses - Courses that Restrict Credit 
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CCRC-1', 'kuali.statement.type.course.credit.restriction', 'kuali.reqComponent.type.course.notcompleted', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('CCRC-2', 'kuali.statement.type.course.credit.restriction', 'kuali.reqComponent.type.course.courseset.completed.none', 2, 0)
/
-- Program Satisfactory Progress
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PSP-1', 'kuali.statement.type.program.satisfactoryProgress', 'kuali.reqComponent.type.program.admitted.credits', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PSP-2', 'kuali.statement.type.program.satisfactoryProgress', 'kuali.reqComponent.type.program.credits.max', 2, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PSP-3', 'kuali.statement.type.program.satisfactoryProgress', 'kuali.reqComponent.type.program.completion.duration', 3, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PSP-4', 'kuali.statement.type.program.satisfactoryProgress', 'kuali.reqComponent.type.program.candidate.status.duration', 4, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PSP-5', 'kuali.statement.type.program.satisfactoryProgress', 'kuali.reqComponent.type.course.courseset.nof.grade.min', 5, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PSP-6', 'kuali.statement.type.program.satisfactoryProgress', 'kuali.reqComponent.type.program.cumulative.gpa.min', 6, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PSP-7', 'kuali.statement.type.program.satisfactoryProgress', 'kuali.reqComponent.type.program.duration.gpa.min', 7, 0)
/
-- Program Entrance
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-1', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.program.programset.completed.nof', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-2', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.program.programset.notcompleted.nof', 2, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-3', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.program.programset.completed.all', 3, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-4', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.program.programset.coursecompleted.nof', 4, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-5', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.gpa.min', 5, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-6', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.test.score.min', 6, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-7', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.program.admitted.credits', 7, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-8', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.program.minor.admitted.classstanding', 8, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-9', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.completed.nof', 9, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-10', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.completed.none', 10, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-11', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.completed.all', 11, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-12', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.credits.completed.nof', 12, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-13', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.credits.completed.none', 13, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-14', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.credits.completed.max', 14, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-15', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.test.score.max', 15, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-16', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.grade.min', 16, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-17', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.nof.grade.min', 17, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-18', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.grade.max', 18, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PE-19', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.completed', 19, 0)
/
-- Program Completion
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-1', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.programset.completed.nof', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-2', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.programset.completed.all', 2, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-3', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.programset.coursecompleted.nof', 3, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-4', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.courseset.gpa.min', 4, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-5', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.credits.min', 5, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-6', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.residence.credits.final', 6, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-7', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.enrolled.credits.final', 7, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-8', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.completion.duration.afterentry', 8, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-9', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.minors.nof', 9, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-10', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.courseset.completed.nof', 10, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-11', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.courseset.completed.all', 11, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-12', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.courseset.credits.completed.nof', 12, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-13', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.courseset.grade.min', 13, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-14', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.courseset.nof.grade.min', 14, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-15', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.org.credits.completed.min', 15, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-16', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.completed', 16, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-17', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.courseset.completed.max', 17, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('PC-20', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.cumulative.gpa.min', 18, 0)
/
