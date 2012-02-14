TRUNCATE TABLE KSST_STMT_TYP_JN_RC_TYP
/
TRUNCATE TABLE KSST_STMT_TYP_JN_STMT_TYP
/

-- STMT_TYPE <-> STMT_TYPE
-- Course
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (ID, STMT_TYPE_ID, CHLD_STMT_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('1', 'kuali.statement.type.course','kuali.statement.type.course.enrollmentEligibility', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (ID, STMT_TYPE_ID, CHLD_STMT_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('2', 'kuali.statement.type.course.enrollmentEligibility','kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 2, 0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (ID, STMT_TYPE_ID, CHLD_STMT_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('3', 'kuali.statement.type.course.enrollmentEligibility','kuali.statement.type.course.academicReadiness.coreq', 3, 0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (ID, STMT_TYPE_ID, CHLD_STMT_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('4', 'kuali.statement.type.course.enrollmentEligibility','kuali.statement.type.course.recommendedPreparation', 4, 0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (ID, STMT_TYPE_ID, CHLD_STMT_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('5', 'kuali.statement.type.course.enrollmentEligibility','kuali.statement.type.course.academicReadiness.antireq', 5, 0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (ID, STMT_TYPE_ID, CHLD_STMT_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('6', 'kuali.statement.type.course','kuali.statement.type.course.creditConstraints', 6, 0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (ID, STMT_TYPE_ID, CHLD_STMT_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('7', 'kuali.statement.type.course.creditConstraints','kuali.statement.type.course.credit.restriction', 7, 0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (ID, STMT_TYPE_ID, CHLD_STMT_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('8', 'kuali.statement.type.course.creditConstraints','kuali.statement.type.course.credit.repeatable', 8, 0)
/
-- Program
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (ID, STMT_TYPE_ID, CHLD_STMT_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('12', 'kuali.statement.type.program','kuali.statement.type.program.entrance', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (ID, STMT_TYPE_ID, CHLD_STMT_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('13', 'kuali.statement.type.program','kuali.statement.type.program.satisfactoryProgress', 2, 0)
/
INSERT INTO KSST_STMT_TYP_JN_STMT_TYP (ID, STMT_TYPE_ID, CHLD_STMT_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('14', 'kuali.statement.type.program','kuali.statement.type.program.completion', 3, 0)
/


-- STMT_TYPE <-> REQ_COM_TYPE
-- Courses
-- Courses - Anti-req
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('1', 'kuali.statement.type.course.academicReadiness.antireq', 'kuali.reqComponent.type.course.notcompleted', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('2', 'kuali.statement.type.course.academicReadiness.antireq', 'kuali.reqComponent.type.course.courseset.completed.none', 2, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('3', 'kuali.statement.type.course.academicReadiness.antireq', 'kuali.reqComponent.type.course.courseset.credits.completed.none', 3, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('4', 'kuali.statement.type.course.academicReadiness.antireq', 'kuali.reqComponent.type.course.courseset.credits.completed.max', 4, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('5', 'kuali.statement.type.course.academicReadiness.antireq', 'kuali.reqComponent.type.course.test.score.max', 5, 0)
/
-- Courses - Pre-req
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('6', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.completed.all', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('7', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.completed.nof', 2, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('8', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.credits.completed.nof', 3, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('9', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.gpa.min', 4, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('10', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.grade.min', 5, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('11', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.grade.max', 6, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('12', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.permission.org.required', 7, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('13', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.permission.instructor.required', 8, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('14', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.test.score.min', 9, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('15', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.courseset.nof.grade.min', 10, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('16', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.program.admitted.org.duration', 11, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('17', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.org.credits.completed.min', 12, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('18', 'kuali.statement.type.course.academicReadiness.prereq', 'kuali.reqComponent.type.course.completed', 13, 0)
/
-- Courses - Co-req
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('19', 'kuali.statement.type.course.academicReadiness.coreq', 'kuali.reqComponent.type.course.enrolled', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('20', 'kuali.statement.type.course.academicReadiness.coreq', 'kuali.reqComponent.type.course.courseset.enrolled.nof', 2, 0)
/
-- Courses - Student Eligibility
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('21', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.course.program.notadmitted.org.duration', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('22', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.course.org.program.admitted', 2, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('23', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.course.program.notadmitted', 3, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('24', 'kuali.statement.type.course.academicReadiness.studentEligibility', 'kuali.reqComponent.type.course.program.admitted', 4, 0)
/
-- Courses - Student Eligibility and Pre-req
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('25', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.completed', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('26', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.courseset.completed.all', 2, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('27', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.courseset.completed.nof', 3, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('28', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.courseset.credits.completed.nof', 4, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('29', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.org.credits.completed.min', 5, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('30', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.courseset.gpa.min', 6, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('31', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.courseset.grade.min', 7, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('32', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.courseset.nof.grade.min', 8, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('33', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.courseset.grade.max', 9, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('34', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.program.admitted', 10, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('35', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.org.program.admitted', 11, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('36', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.program.notadmitted', 12, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('37', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.permission.instructor.required', 13, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('38', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.permission.org.required', 14, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('39', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.test.score.min', 15, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('40', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.program.admitted.org.duration', 16, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('41', 'kuali.statement.type.course.academicReadiness.studentEligibilityPrereq', 'kuali.reqComponent.type.course.program.notadmitted.org.duration', 17, 0)
/
-- Courses - Repeatable for Credit
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('42', 'kuali.statement.type.course.credit.repeatable', 'kuali.reqComponent.type.course.credits.repeat.max', 1, 0)
/
-- Courses - Recommended Preparation
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('43', 'kuali.statement.type.course.recommendedPreparation', 'kuali.reqComponent.type.course.completed', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('44', 'kuali.statement.type.course.recommendedPreparation', 'kuali.reqComponent.type.course.org.credits.completed.min', 2, 0)
/
-- Courses - Courses that Restrict Credit 
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('45', 'kuali.statement.type.course.credit.restriction', 'kuali.reqComponent.type.course.notcompleted', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('46', 'kuali.statement.type.course.credit.restriction', 'kuali.reqComponent.type.course.courseset.completed.none', 2, 0)
/
-- Program Satisfactory Progress
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('47', 'kuali.statement.type.program.satisfactoryProgress', 'kuali.reqComponent.type.program.admitted.credits', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('48', 'kuali.statement.type.program.satisfactoryProgress', 'kuali.reqComponent.type.program.credits.max', 2, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('49', 'kuali.statement.type.program.satisfactoryProgress', 'kuali.reqComponent.type.program.completion.duration', 3, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('50', 'kuali.statement.type.program.satisfactoryProgress', 'kuali.reqComponent.type.program.candidate.status.duration', 4, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('51', 'kuali.statement.type.program.satisfactoryProgress', 'kuali.reqComponent.type.course.courseset.nof.grade.min', 5, 0)
/
-- Program Entrance
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('52', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.program.programset.completed.nof', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('53', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.program.programset.notcompleted.nof', 2, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('54', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.program.programset.completed.all', 3, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('55', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.program.programset.coursecompleted.nof', 4, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('56', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.gpa.min', 5, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('57', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.test.score.min', 6, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('58', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.program.admitted.credits', 7, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('59', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.program.minor.admitted.classstanding', 8, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('60', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.completed.nof', 9, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('61', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.completed.none', 10, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('62', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.completed.all', 11, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('63', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.credits.completed.nof', 12, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('64', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.credits.completed.none', 13, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('65', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.credits.completed.max', 14, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('66', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.test.score.max', 15, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('67', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.grade.min', 16, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('68', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.nof.grade.min', 17, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('69', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.courseset.grade.max', 18, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('70', 'kuali.statement.type.program.entrance', 'kuali.reqComponent.type.course.completed', 19, 0)
/
-- Program Completion
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('71', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.programset.completed.nof', 1, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('72', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.programset.completed.all', 2, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('73', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.programset.coursecompleted.nof', 3, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('74', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.courseset.gpa.min', 4, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('75', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.credits.min', 5, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('76', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.residence.credits.final', 6, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('77', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.enrolled.credits.final', 7, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('78', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.completion.duration.afterentry', 8, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('79', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.program.minors.nof', 9, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('80', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.courseset.completed.nof', 10, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('81', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.courseset.completed.all', 11, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('82', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.courseset.credits.completed.nof', 12, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('83', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.courseset.grade.min', 13, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('84', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.courseset.nof.grade.min', 14, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('85', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.org.credits.completed.min', 15, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('86', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.completed', 16, 0)
/
INSERT INTO KSST_STMT_TYP_JN_RC_TYP (ID, STMT_TYPE_ID, REQ_COM_TYPE_ID, SORT_ORDER, VER_NBR) VALUES ('87', 'kuali.statement.type.program.completion', 'kuali.reqComponent.type.course.courseset.completed.max', 17, 0)
/
