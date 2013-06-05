package org.kuali.student.krms.naturallanguage.util;

/**
 * Created with IntelliJ IDEA. User: SW Date: 2013/01/10 Time: 11:50 AM To
 * change this template use File | Settings | File Templates.
 */
public class KsKrmsConstants {

    public static final String KRMS_DISCRIMINATOR_TYPE_AGENDA = "Agenda";
    // language codes
    // these should match the codes returned from browsers following the internet standard 
    // in the kuali student ContextInfo (not to be confused with the KRMS context)
    public static final String LANGUAGE_CODE_ENGLISH = "en";
    public static final String LANGUAGE_CODE_FRENCH = "fr";
    public static final String LANGUAGE_CODE_SPANISH = "es";
    public static final String LANGUAGE_CODE_AFRIKAANS = "af";
    // dynamic attributes
    public static final String ATTRIBUTE_COMPONENT_ID = "componentId";
    public static final String ATTRIBUTE_COMPONENT_BUILDER_CLASS = "componentBuilderClass";
    // context names
    public static final String CONTEXT_COURSE_REQUIREMENTS = "Course Requirements";
    public static final String CONTEXT_PROGRAM_REQUIREMENTS = "Program Requirements";
    public static final String CONTEXT_TYPE_DEFAULT = "T1004"; 
    //Namespace
    public static final String NAMESPACE_CODE = "KS-SYS";
    // Context Types
    public static final String AGENDA_TYPE_COURSE = "kuali.krms.agenda.type.course";
    public static final String AGENDA_TYPE_PROGRAM = "kuali.krms.agenda.type.program";
    // Agenda Types
    public static final String AGENDA_TYPE_COURSE_ENROLLMENTELIGIBILITY = "kuali.krms.agenda.type.course.enrollmentEligibility";
    public static final String AGENDA_TYPE_COURSE_CREDITCONSTRAINTS = "kuali.krms.agenda.type.course.creditConstraints";
    public static final String AGENDA_TYPE_SCHEDULE_ELIGIBILITY = "kuali.krms.agenda.type.schedule.eligibility";
    // rule types
    public static final String RULE_TYPE_COURSE_ACADEMICREADINESS_ANTIREQ = "kuali.krms.rule.type.course.academicReadiness.antireq";
    public static final String RULE_TYPE_COURSE_ACADEMICREADINESS_COREQ = "kuali.krms.rule.type.course.academicReadiness.coreq";
    public static final String RULE_TYPE_COURSE_RECOMMENDEDPREPARATION = "kuali.krms.rule.type.course.recommendedPreparation";
    public static final String RULE_TYPE_COURSE_ACADEMICREADINESS_STUDENTELIGIBILITY = "kuali.krms.rule.type.course.academicReadiness.studentEligibility";
    public static final String RULE_TYPE_COURSE_ACADEMICREADINESS_STUDENTELIGIBILITYPREREQ = "kuali.krms.rule.type.course.academicReadiness.studentEligibilityPrereq";
    public static final String RULE_TYPE_COURSE_CREDIT_REPEATABLE = "kuali.krms.rule.type.course.credit.repeatable";
    public static final String RULE_TYPE_COURSE_CREDIT_RESTRICTION = "kuali.krms.rule.type.course.credit.restriction";
    public static final String RULE_TYPE_PROGRAM_COMPLETION = "kuali.krms.rule.type.program.completion";
    public static final String RULE_TYPE_PROGRAM_ENTRANCE = "kuali.krms.rule.type.program.entrance";
    public static final String RULE_TYPE_PROGRAM_SATISFACTORYPROGRESS = "kuali.krms.rule.type.program.satisfactoryProgress";
    public static final String RULE_TYPE_SCHEDULE_ELIGIBILITY = "kuali.krms.rule.type.schedule.eligibility";
    // proposition types
    public static final String PROPOSITION_TYPE_SUCCESS_COMPL_COURSE = "kuali.krms.proposition.type.success.compl.course";
    public static final String PROPOSITION_TYPE_SUCCESS_COURSE_COURSESET_COMPLETED_ALL = "kuali.krms.proposition.type.success.course.courseset.completed.all";
    public static final String PROPOSITION_TYPE_SUCCESS_COURSE_COURSESET_COMPLETED_NOF = "kuali.krms.proposition.type.success.course.courseset.completed.nof";
    public static final String PROPOSITION_TYPE_COURSE_COURSESET_COMPLETED_NONE = "kuali.krms.proposition.type.course.courseset.completed.none";
    public static final String PROPOSITION_TYPE_COURSE_COURSESET_CREDITS_COMPLETED_NOF = "kuali.krms.proposition.type.course.courseset.credits.completed.nof";
    public static final String PROPOSITION_TYPE_COURSE_COURSESET_CREDITS_COMPLETED_NONE = "kuali.krms.proposition.type.course.courseset.credits.completed.none";
    public static final String PROPOSITION_TYPE_COURSE_COURSESET_ENROLLED_ALL = "kuali.krms.proposition.type.course.courseset.enrolled.all";
    public static final String PROPOSITION_TYPE_COURSE_COURSESET_ENROLLED_NOF = "kuali.krms.proposition.type.course.courseset.enrolled.nof";
    public static final String PROPOSITION_TYPE_COURSE_COURSESET_GPA_MIN = "kuali.krms.proposition.type.course.courseset.gpa.min";
    public static final String PROPOSITION_TYPE_COURSE_COURSESET_GRADE_MAX = "kuali.krms.proposition.type.course.courseset.grade.max";
    public static final String PROPOSITION_TYPE_COURSE_COURSESET_GRADE_MIN = "kuali.krms.proposition.type.course.courseset.grade.min";
    public static final String PROPOSITION_TYPE_COURSE_COURSESET_NOF_GRADE_MIN = "kuali.krms.proposition.type.course.courseset.nof.grade.min";
    public static final String PROPOSITION_TYPE_COURSE_CREDITS_REPEAT_MAX = "kuali.krms.proposition.type.course.credits.repeat.max";
    public static final String PROPOSITION_TYPE_COURSE_ENROLLED = "kuali.krms.proposition.type.course.enrolled";
    public static final String PROPOSITION_TYPE_FREEFORM_TEXT = "kuali.krms.proposition.type.freeform.text";
    public static final String PROPOSITION_TYPE_COURSE_NOTCOMPLETED = "kuali.krms.proposition.type.course.notcompleted";
    public static final String PROPOSITION_TYPE_ADMITTED_TO_PROGRAM_CAMPUS = "kuali.krms.proposition.type.admitted.to.program.campus";
    public static final String PROPOSITION_TYPE_PERMISSION_INSTRUCTOR_REQUIRED = "kuali.krms.proposition.type.permission.instructor.required";
    public static final String PROPOSITION_TYPE_PERMISSION_ADMIN_ORG = "kuali.krms.proposition.type.permission.admin.org";
    public static final String PROPOSITION_TYPE_NOTADMITTED_TO_PROGRAM = "kuali.krms.proposition.type.notadmitted.to.program";
    public static final String PROPOSITION_TYPE_COURSE_TEST_SCORE_MAX = "kuali.krms.proposition.type.course.test.score.max";
    public static final String PROPOSITION_TYPE_COURSE_TEST_SCORE_MIN = "kuali.krms.proposition.type.course.test.score.min";
    public static final String PROPOSITION_TYPE_CREDITS_EARNED_MIN = "kuali.krms.proposition.type.credits.earned.min";
    public static final String PROPOSITION_TYPE_CUMULATIVE_GPA_MIN = "kuali.krms.proposition.type.cumulative.gpa.min";
    public static final String PROPOSITION_TYPE_DURATION_CUMULATIVE_GPA_MIN = "kuali.krms.proposition.type.duration.cumulative.gpa.min";
    public static final String PROPOSITION_TYPE_DROP_MIN_CREDIT_HOURS_DUE_TO_ATTRIBUTE = "kuali.krms.proposition.type.drop.min.credit.hours.due.to.attribute";
    public static final String PROPOSITION_TYPE_DROP_MIN_CREDIT_HOURS = "kuali.krms.proposition.type.drop.min.credit.hours";
    public static final String PROPOSITION_TYPE_EXCEEDS_MINUTES_OVERLAP_ALLOWED = "kuali.krms.proposition.type.exceeds.minutes.overlap.allowed";
    public static final String PROPOSITION_TYPE_TIME_CONFLICT_START_END = "kuali.krms.proposition.type.time.conflict.start.end";
    public static final String PROPOSITION_TYPE_MAX_LIMIT_COURSES_FOR_PROGRAM = "kuali.krms.proposition.type.max.limit.courses.for.program";
    public static final String PROPOSITION_TYPE_MAX_LIMIT_CREDITS_FOR_PROGRAM = "kuali.krms.proposition.type.max.limit.credits.for.program";
    public static final String PROPOSITION_TYPE_MAX_LIMIT_COURSES_FOR_CAMPUS_DURATION = "kuali.krms.proposition.type.max.limit.courses.for.campus.duration";
    public static final String PROPOSITION_TYPE_MAX_LIMIT_CREDITS_FOR_CAMPUS_DURATION = "kuali.krms.proposition.type.max.limit.credits.for.campus.duration";
    public static final String PROPOSITION_TYPE_ADMITTED_TO_PROGRAM = "kuali.krms.proposition.type.admitted.to.program";
    public static final String PROPOSITION_TYPE_COURSE_COURSESET_COMPLETED_NOF = "kuali.krms.proposition.type.course.courseset.completed.nof";
    public static final String PROPOSITION_TYPE_SUCCESS_CREDIT_COURSESET_COMPLETED_NOF = "kuali.krms.proposition.type.success.credit.courseset.completed.nof";
    public static final String PROPOSITION_TYPE_SUCCESS_CREDITS_COURSESET_COMPLETED_NOF_ORG = "kuali.krms.proposition.type.success.credits.courseset.completed.nof.org";
    public static final String PROPOSITION_TYPE_CANT_ADD_TO_ACTIVITY_OFFERING_DUE_TO_STATE = "kuali.krms.proposition.type.cant.add.to.activity.offering.due.to.state";
    public static final String PROPOSITION_TYPE_NO_REPEAT_COURSE = "kuali.krms.proposition.type.no.repeat.course";
    public static final String PROPOSITION_TYPE_NO_REPEAT_COURSES = "kuali.krms.proposition.type.no.repeat.courses";
    public static final String PROPOSITION_TYPE_AVAIL_SEAT = "kuali.krms.proposition.type.avail.seat";
    public static final String PROPOSITION_TYPE_SUCCESS_COMPL_COURSE_AS_OF_TERM = "kuali.krms.proposition.type.success.compl.course.as.of.term";
    public static final String PROPOSITION_TYPE_SUCCESS_COMPL_PRIOR_TO_TERM = "kuali.krms.proposition.type.success.compl.prior.to.term";
    public static final String PROPOSITION_TYPE_SUCCESS_COMPL_COURSE_BETWEEN_TERMS = "kuali.krms.proposition.type.success.compl.course.between.terms";
    public static final String PROPOSITION_TYPE_NOTADMITTED_TO_PROGRAM_IN_CLASS_STANDING = "kuali.krms.proposition.type.notadmitted.to.program.in.class.standing";
    public static final String PROPOSITION_TYPE_ADMITTED_TO_PROGRAM_ORG = "kuali.krms.proposition.type.admitted.to.program.org";
    public static final String PROPOSITION_TYPE_IN_CLASS_STANDING = "kuali.krms.proposition.type.in.class.standing";
    public static final String PROPOSITION_TYPE_GREATER_THAN_CLASS_STANDING = "kuali.krms.proposition.type.greater.than.class.standing";
    public static final String PROPOSITION_TYPE_LESS_THAN_CLASS_STANDING = "kuali.krms.proposition.type.less.than.class.standing";
    public static final String PROPOSITION_TYPE_NOTIN_CLASS_STANDING = "kuali.krms.proposition.type.notin.class.standing";
    public static final String PROPOSITION_TYPE_COURSE_COURSESET_ENROLLED = "kuali.krms.proposition.type.course.courseset.enrolled";
    public static final String PROPOSITION_TYPE_NO_REPEAT_COURSE_NOF = "kuali.krms.proposition.type.no.repeat.course.nof";
    public static final String PROPOSITION_TYPE_TEST_SCORE_BETWEEN_VALUES = "kuali.krms.proposition.type.test.score.between.values";
    public static final String PROPOSITION_TYPE_TEST_SCORE = "kuali.krms.proposition.type.test.score";
    public static final String PROPOSITION_TYPE_COMPOUND_AND = "kuali.krms.proposition.type.compound.and";  
    public static final String PROPOSITION_TYPE_COMPOUND_OR = "kuali.krms.proposition.type.compound.or";  
    
    // term specs
    public static final String TERM_SPEC_COMPLETEDCOURSE = "CompletedCourse";
    public static final String TERM_SPEC_COMPLETEDCOURSES = "CompletedCourses";
    public static final String TERM_SPEC_NUMBEROFCOMPLETEDCOURSES = "NumberOfCompletedCourses";
    public static final String TERM_SPEC_NUMBEROFCREDITSFROMCOMPLETEDCOURSES = "NumberOfCreditsFromCompletedCourses";
    public static final String TERM_SPEC_ENROLLEDCOURSES = "EnrolledCourses";
    public static final String TERM_SPEC_GPAFORCOURSES = "GPAForCourses";
    public static final String TERM_SPEC_GRADETYPEFORCOURSES = "GradeTypeForCourses";
    public static final String TERM_SPEC_NUMBEROFCREDITS = "NumberOfCredits";
    public static final String TERM_SPEC_NUMBEROFCREDITSFROMORGANIZATION = "NumberOfCreditsFromOrganization";
    public static final String TERM_SPEC_ADMINORGANIZATIONPERMISSIONREQUIRED = "AdminOrganizationPermissionRequired";
    public static final String TERM_SPEC_SCOREONTEST = "ScoreOnTest";
    public static final String TERM_SPEC_ADMITTEDTOPROGRAM = "AdmittedToProgram";
    public static final String TERM_SPEC_ADMITTEDTOPROGRAMLIMITCOURSESINORGFORDURATION = "AdmittedToProgramLimitCoursesInOrgForDuration";
    public static final String TERM_SPEC_FREEFORMTEXT = "FreeFormText";
    // term resolvers
    public static final String TERM_RESOLVER_COMPLETEDCOURSE = "CompletedCourse";
    public static final String TERM_RESOLVER_COMPLETEDCOURSES = "CompletedCourses";
    public static final String TERM_RESOLVER_NUMBEROFCOMPLETEDCOURSES = "NumberOfCompletedCourses";
    public static final String TERM_RESOLVER_NUMBEROFCREDITSFROMCOMPLETEDCOURSES = "NumberOfCreditsFromCompletedCourses";
    public static final String TERM_RESOLVER_ENROLLEDCOURSES = "EnrolledCourses";
    public static final String TERM_RESOLVER_GPAFORCOURSES = "GPAForCourses";
    public static final String TERM_RESOLVER_GRADETYPEFORCOURSES = "GradeTypeForCourses";
    public static final String TERM_RESOLVER_NUMBEROFCREDITS = "NumberOfCredits";
    public static final String TERM_RESOLVER_NUMBEROFCREDITSFROMORGANIZATION = "NumberOfCreditsFromOrganization";
    public static final String TERM_RESOLVER_ADMINORGANIZATIONPERMISSIONREQUIRED = "AdminOrganizationPermissionRequired";
    public static final String TERM_RESOLVER_SCOREONTEST = "ScoreOnTest";
    public static final String TERM_RESOLVER_ADMITTEDTOPROGRAM = "AdmittedToProgram";
    public static final String TERM_RESOLVER_ADMITTEDTOPROGRAMLIMITCOURSESINORGFORDURATION = "AdmittedToProgramLimitCoursesInOrgForDuration";
    public static final String TERM_RESOLVER_FREEFORMTEXT = "FreeFormText";
    // rules editing
    public static final String KRMS_NL_RULE_EDIT = "kuali.krms.edit";
    public static final String KRMS_NL_COMPOSITION = "kuali.krms.composition";
    public static final String KRMS_NL_EXAMPLE = "kuali.krms.example";
    public static final String KRMS_NL_PREVIEW = "kuali.krms.preview";
    public static final String KRMS_NL_TYPE_DESCRIPTION = "kuali.krms.type.description";
    public static final String KRMS_NL_TYPE_CATALOG = "kuali.krms.catalog";
    public static final String KRMS_NL_TYPE_INSTRUCTION = "kuali.krms.type.instruction";
}
