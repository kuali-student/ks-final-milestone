package org.kuali.student.r2.core.constants;

import org.kuali.rice.krms.api.engine.Term;

/**
 * Created with IntelliJ IDEA.
 * User: SW Genis
 * Date: 2013/06/28
 * Time: 2:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class KSKRMSServiceConstants {

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
    public static final String KSNAMESPACE = "KR-RULE-TEST";

    // Context Types
    public static final String AGENDA_TYPE_COURSE = "kuali.krms.agenda.type.course";
    public static final String AGENDA_TYPE_PROGRAM = "kuali.krms.agenda.type.program";

    // Agenda Types
    public static final String AGENDA_TYPE_COURSE_ENROLLMENTELIGIBILITY = "kuali.krms.agenda.type.course.enrollmentEligibility";
    public static final String AGENDA_TYPE_COURSE_CREDITCONSTRAINTS = "kuali.krms.agenda.type.course.creditConstraints";
    public static final String AGENDA_TYPE_SCHEDULE_ELIGIBILITY = "kuali.krms.agenda.type.schedule.eligibility";

    //Rule Discriminator Types
    public static final String RULE_DISCR_TYPE_CREDIT = "kuali.lu.type.CreditCourse";
    public static final String RULE_DISCR_TYPE_COURSE_OFFERING = "kuali.lui.type.course.offering";

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
    public static final String PROPOSITION_TYPE_POPULATION = "kuali.krms.proposition.type.memberof.population";
    // term types
    public static final String TERM_TYPE_COURSE_ID = "kuali.krms.term.type.course.specific.id";
    public static final String TERM_TYPE_COURSE = "kuali.krms.term.type.course.specific";
    public static final String TERM_TYPE_COURSE_ID_SET = "kuali.krms.term.type.course.set.id";
    public static final String TERM_TYPE_COURSE_SET = "kuali.krms.term.type.course.set";
    public static final String TERM_TYPE_COURSE_OFFERING_ID = "kuali.krms.term.type.course.offering.specific.id";
    public static final String TERM_TYPE_COURSE_OFFERING = "kuali.krms.term.type.course.offering.specific";
    public static final String TERM_TYPE_COURSE_OFFERING_ID_SET = "kuali.krms.term.type.course.offering.set.id";
    public static final String TERM_TYPE_COURSE_OFFERING_SET = "kuali.krms.term.type.course.offering.set";
    public static final String TERM_TYPE_STUDENT_ID = "kuali.krms.term.type.person.student.specific.id";
    public static final String TERM_TYPE_STUDENT = "kuali.krms.term.type.person.student.specific";
    public static final String TERM_TYPE_START_TERM_ID = "kuali.krms.term.type.acal.term.start.id";
    public static final String TERM_TYPE_START_TERM = "kuali.krms.term.type.acal.term.start";
    public static final String TERM_TYPE_END_TERM_ID = "kuali.krms.term.type.acal.term.end.id";
    public static final String TERM_TYPE_END_TERM = "kuali.krms.term.type.acal.term.end";
    public static final String TERM_TYPE_START_DATE = "kuali.krms.term.type.date.start";
    public static final String TERM_TYPE_END_DATE = "kuali.krms.term.type.date.end";
    public static final String TERM_TYPE_ATP_ID = "kuali.krms.term.type.atp.id";
    public static final String TERM_TYPE_CURRENT_TERM_ID = "kuali.krms.term.type.atp.current.id";
    public static final String TERM_TYPE_FIELD_TYPE_PARAMETER = "kuali.krms.term.type.field";

    // term parameter types
    public static final String TERM_PARAMETER_TYPE_CLUSET_KEY = "kuali.term.parameter.type.course.cluSet.id";
    public static final String TERM_PARAMETER_TYPE_GRADE_TYPE_KEY = "kuali.term.parameter.type.gradeType.id";
    public static final String TERM_PARAMETER_TYPE_GRADE_KEY = "kuali.term.parameter.type.grade.id";
    public static final String TERM_PARAMETER_TYPE_DURATION_KEY = "kuali.term.parameter.type.duration";
    public static final String TERM_PARAMETER_TYPE_DURATION_TYPE_KEY = "kuali.term.parameter.type.durationType.id";
    public static final String TERM_PARAMETER_TYPE_ORGANIZATION_KEY = "kuali.term.parameter.type.org.id";
    public static final String TERM_PARAMETER_TYPE_CLU_KEY = "kuali.term.parameter.type.course.clu.id";
    public static final String TERM_PARAMETER_TYPE_CO_KEY = "kuali.term.parameter.type.course.offering.id";
    public static final String TERM_PARAMETER_TYPE_TERM_KEY = "kuali.term.parameter.type.Term";
    public static final String TERM_PARAMETER_TYPE_TERM2_KEY = "kuali.term.parameter.type.Term2";
    public static final String TERM_PARAMETER_TYPE_TOTAL_CREDIT_KEY = "kuali.term.parameter.type.totalCredits";
    public static final String TERM_PARAMETER_TYPE_PROGRAM_CLUSET_KEY = "kuali.term.parameter.type.program.cluSet.id";
    public static final String TERM_PARAMETER_TYPE_CLASS_STANDING_KEY = "kuali.term.parameter.type.classStanding";
    public static final String TERM_PARAMETER_TYPE_TEST_CLU_KEY = "kuali.term.parameter.type.test.clu.id";
    public static final String TERM_PARAMETER_TYPE_TERMCODE_KEY = "kuali.term.parameter.type.TermCode";
    public static final String TERM_PARAMETER_TYPE_TERMCODE2_KEY = "kuali.term.parameter.type.TermCode2";
    public static final String TERM_PARAMETER_TYPE_POPULATION_KEY = "kuali.term.parameter.type.population";

    // term parameter types for nl
    public static final String TERM_PARAMETER_TYPE_CLULIST_KEY = "kuali.term.parameter.type.course.nl.clu.list";
    public static final String TERM_PARAMETER_TYPE_CLUSETLIST_KEY = "kuali.term.parameter.type.course.nl.cluset.list";

    // term specs
    public static final String TERM_SPEC_COMPLETEDCOURSE = "CompletedCourse";
    public static final String TERM_SPEC_COMPLETEDCOURSES = "CompletedCourses";
    public static final String TERM_SPEC_NUMBEROFCOMPLETEDCOURSES = "NumberOfCompletedCourses";
    public static final String TERM_SPEC_NUMBEROFCREDITSFROMCOMPLETEDCOURSES = "NumberOfCreditsFromCompletedCourses";
    public static final String TERM_SPEC_ENROLLEDCOURSE = "EnrolledCourse";
    public static final String TERM_SPEC_ENROLLEDCOURSES = "EnrolledCourses";
    public static final String TERM_SPEC_NUMBEROFENROLLEDCOURSES = "NumberOfEnrolledCourses";
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
    public static final String TERM_RESOLVER_ENROLLEDCOURSE = "EnrolledCourse";
    public static final String TERM_RESOLVER_ENROLLEDCOURSES = "EnrolledCourses";
    public static final String TERM_RESOLVER_NUMBEROFENROLLEDCOURSES = "NumberOfEnrolledCourses";
    public static final String TERM_RESOLVER_GPAFORCOURSES = "GPAForCourses";
    public static final String TERM_RESOLVER_GRADETYPEFORCOURSES = "GradeTypeForCourses";
    public static final String TERM_RESOLVER_NUMBEROFCREDITSFROMORGANIZATION = "NumberOfCreditsFromOrganization";
    public static final String TERM_RESOLVER_NUMBEROFCREDITSFROMCOMPLETEDCOURSES = "NumberOfCreditsFromCompletedCourses";
    public static final String TERM_RESOLVER_NUMBEROFCREDITSEARNED = "NumberOfCreditsEarned";
    public static final String TERM_RESOLVER_ADMINORGANIZATIONPERMISSIONREQUIRED = "AdminOrganizationPermissionRequired";
    public static final String TERM_RESOLVER_SCOREONTEST = "ScoreOnTest";
    public static final String TERM_RESOLVER_FREEFORMTEXT = "FreeFormText";
    public static final String TERM_RESOLVER_ADMITTEDTOPROGRAMLIMITCOURSESINORGFORDURATION = "AdmittedToProgramLimitCoursesInOrgForDuration";
    public static final String TERM_RESOLVER_ADMITTEDTOPROGRAM = "AdmittedToProgram";
    public static final String TERM_RESOLVER_ADMITTEDTOPROGRAMATCOURSECAMPUS = "AdmittedToProgramAtCourseCampus";
    public static final String TERM_RESOLVER_ADMITTEDTOPROGRAMWITHCLASSSTANDING = "AdmittedToProgramWithClassStanding";
    public static final String TERM_RESOLVER_COMPLETEDCOURSEFORTERM = "CompletedCourseForTerm";
    public static final String TERM_RESOLVER_COMPLETEDCOURSEBETWEENTERMS = "CompletedCourseBetweenTerms";
    public static final String TERM_RESOLVER_POPULATION= "Population";
    public static final String TERM_RESOLVER_INSTRUCTORPERMISSION = "InstructorPermission";

    // natural language usage keys
    public static final String KRMS_NL_RULE_EDIT = "kuali.krms.edit";
    public static final String KRMS_NL_COMPOSITION = "kuali.krms.composition";
    public static final String KRMS_NL_EXAMPLE = "kuali.krms.example";
    public static final String KRMS_NL_PREVIEW = "kuali.krms.preview";
    public static final String KRMS_NL_TYPE_DESCRIPTION = "kuali.krms.type.description";
    public static final String KRMS_NL_TYPE_CATALOG = "kuali.krms.catalog";
    public static final String KRMS_NL_TYPE_INSTRUCTION = "kuali.krms.type.instruction";

    // term prerequisite
    public static final String TERM_PREREQUISITE_CONTEXTINFO = "contextInfo";
    public static final String TERM_PREREQUISITE_PERSON_ID = "personId";
    public static final String TERM_PREREQUISITE_CLU_ID = "cluId";
    public static final String TERM_PREREQUISITE_TERM_ID = "termId";


    ////////////////////////////////////////////////////////////////////////////////////////////
    // org.kuali.student.krms.util.KSKRMSConstants.java                                       //
    ////////////////////////////////////////////////////////////////////////////////////////////

    //public static final String TERM_SPEC_CREDITS = "Credits";
    //public static final String TERM_SPEC_RESOLVER_CREDITS = "creditsTermResolver";
    //public static final String CREDITS_DESCR = "Credits Term Specification";

    //public static final String TERM_SPEC_ORG_NUMBER = "Org Number";
    //public static final String TERM_SPEC_RESOLVER_ORG_NUMBER = "orgNumberTermResolver";
    //public static final String ORG_NUMBER_DESCR = "Org Number Term Specification";

    //public static final String TERM_SPEC_COURSE_SET = "Course set";
    //public static final String TERM_SPEC_RESOLVER_COURSE_SET = "courseSetTermResolver";
    //public static final String COURSE_SET_DESCR = "Course set Term Specification";

    //public static final String TERM_SPEC_COURSE_NUMBER_RANGE = "Course Number Range";
    //public static final String TERM_SPEC_RESOLVER_COURSE_NUMBER_RANGE = "courseNumberRangeTermResolver";
    //public static final String COURSE_NUMBER_RANGE_DESCR = "Course Number Range Specification";

    //public static final String TERM_SPEC_EFFECTIVE_DATE_TO = "Effective Date To";
    //public static final String TERM_SPEC_RESOLVER_EFFECTIVE_DATE_TO = "effectiveDateToTermResolver";
    //public static final String _EFFECTIVE_DATE_TO_DESCR = "Effective Date To Term Specification";

    //public static final String TERM_SPEC_EFFECTIVE_DATE_FROM = "Effective Date From";
    //public static final String TERM_SPEC_RESOLVER_EFFECTIVE_DATE_FROM = "effectiveDateFromTermResolver";
    //public static final String EFFECTIVE_DATE_FROM_DESCR = "Effective Date From Term Specification";

    //public static final String TERM_SPEC_GPA = "GPA";
    //public static final String TERM_SPEC_RESOLVER_GPA = "GPATermResolver";
    //public static final String GPA_DESCR = "GPA Term Specification";

    //public static final String TERM_SPEC_GRADE = "Grade";
    //public static final String TERM_SPEC_RESOLVER_GRADE = "gradeTermResolver";
    //public static final String GRADE_DESCR = "Grade Term Specification";

    //public static final String TERM_SPEC_GRADE_TYPE = "GradeType";
    //public static final String TERM_SPEC_RESOLVER_GRADE_TYPE = "gradeTypeTermResolver";
    //public static final String GRADE_TYPE_DESCR = "Grade Type Term Specification";

    //public static final String TERM_SPEC_LEARNING_OBJECTIVES = "Learning Objectives";
    //public static final String TERM_SPEC_RESOLVER_LEARNING_OBJECTIVES = "learningObjectivesTermResolver";
    //public static final String LEARNING_OBJECTIVES_DESCR = "Learning Objective Term Specification";

    //public static final String TERM_SPEC_SUBJECT_CODE = "Subject Code";
    //public static final String TERM_SPEC_RESOLVER_SUBJECT_CODE = "subjectCodeTermResolver";
    //public static final String SUBJECT_CODE_DESCR = "Subject Code Term Specification";

    //public static final String TERM_SPEC_FREE_TEXT = "Free Text";
    //public static final String TERM_SPEC_RESOLVER_FREE_TEXT = "freeTextTermResolver";
    //public static final String FREE_TEXT_DESCR = "Free Text Term Specification";

    //public static final String TERM_SPEC_NUMBER_OF_COURSES = "Number of courses";
    //public static final String TERM_SPEC_RESOLVER_NUMBER_OF_COURSES = "numberOfCoursesTermResolver";
    //public static final String NUMBER_OF_COURSES_DESCR = "Number of courses Term Specification";

    //public static final String TERM_SPEC_NUMBER_OF_CREDITS = "Number of credits";
    //public static final String TERM_SPEC_RESOLVER_NUMBER_OF_CREDITS = "numberOfCreditsTermResolver";
    //public static final String NUMBER_OF_CREDITS_DESCR = "Number of credits Term Specification";

    //public static final String TERM_SPEC_SCORE = "Score";
    //public static final String TERM_SPEC_RESOLVER_SCORE = "scoreTermResolver";
    //public static final String SCORE_DESCR = "Score Term Specification";

    //public static final String TERM_SPEC_TEST = "Test";
    //public static final String TERM_SPEC_RESOLVER_TEST = "testTermResolver";
    //public static final String TEST_DESCR = "Test Term Specification";

    //public static final String TERM_SPEC_DEPT_NUMBER = "Department number";
    //public static final String TERM_SPEC_RESOLVER_DEPT_NUMBER = "deptNumberTermResolver";
    //public static final String DEPT_NUMBER_DESCR = "Department number Term Specification";

    //public static final String TERM_SPEC_ADMIN_ORG_NUMBER = "Admin org number";
    //public static final String TERM_SPEC_RESOLVER_ADMIN_ORG_NUMBER = "adminOrgNumberTermResolver";
    //public static final String ADMIN_ORG_NUMBER_DESCR = "Admin org number Term Specification";

    //public static final String TERM_SPEC_COMPLETED_COURSE = "Completed course";
    //public static final String TERM_SPEC_RESOLVER_COMPLETED_COURSE = "completedCourseTermResolver";
    //public static final String COMPLETED_COURSE_DESCR = "Completed course Term Specification";

    //public static final String TERM_SPEC_COMPLETED_COURSES = "Completed courses";
    //public static final String TERM_SPEC_RESOLVER_COMPLETED_COURSES = "completedCoursesTermResolver";
    //public static final String COMPLETED_COURSES_DESCR = "Completed courses Term Specification";

    //public static final String TERM_SPEC_COMPLETED_COURSE_NUMBER = "Completed course number: subject code";
    //public static final String TERM_SPEC_RESOLVER_COMPLETED_COURSE_NUMBER = "completedCourseNumberTermResolver";
    //public static final String COMPLETED_COURSE_NUMBER_DESCR = "Completed course number: subject code Term Specification";

    //public static final String TERM_SPEC_COMPLETED_COURSE_CODE = "Completed course number: subject code";
    //public static final String TERM_SPEC_RESOLVER_COMPLETED_COURSE_CODE = "completedCourseCodeTermResolver";
    //public static final String COMPLETED_COURSE_CODE_DESCR = "Completed course number: subject code Term Specification";

    //public static final String TERM_SPEC_COMPLETED_COURSE_SET = "Completed course set";
    //public static final String TERM_SPEC_RESOLVER_COMPLETED_COURSE_SET = "completedCourseSetTermResolver";
    //public static final String COMPLETED_COURSE_SET_DESCR = "Completed course set Term Specification";

    //public static final String TERM_SPEC_COMPLETED_EFFECTIVE_DATE_FROM = "Completed effective date range: effective from";
    //public static final String TERM_SPEC_RESOLVER_COMPLETED_EFFECTIVE_DATE_FROM = "completedEffectiveDateFromTermResolver";
    //public static final String COMPLETED_EFFECTIVE_DATE_FROM_DESCR = "Completed effective date range: effective from Term Specification";

    //public static final String TERM_SPEC_COMPLETED_EFFECTIVE_DATE_TO = "Completed effective date range: effective to";
    //public static final String TERM_SPEC_RESOLVER_COMPLETED_EFFECTIVE_DATE_TO = "completedEffectiveDateToTermResolver";
    //public static final String COMPLETED_EFFECTIVE_DATE_TO_DESCR = "Completed effective date range: effective to Term Specification";

    //public static final String TERM_SPEC_COMPLETED_LEARNING_OBJ_DESCR = "Completed learning Objective: Description";
    //public static final String TERM_SPEC_RESOLVER_COMPLETED_LEARNING_OBJ_DESCR = "completedLearningObjectivesTermResolver";
    //public static final String COMPLETED_LEARNING_OBJ_DESCR_DESCR = "Completed learning Objective: Description Term Specification";

    //public static final String TERM_SPEC_ENROLLED_COURSE = "Enrolled course by term";
    //public static final String TERM_SPEC_RESOLVER_ENROLLED_COURSE = "enrolledCourseTermResolver";
    //public static final String ENROLLED_COURSE_DESCR = "Enrolled course by term Term Specification";

    //public static final String TERM_SPEC_ENROLLED_COURSE_NUMBER_RANGE = "Enrolled course number: course number range";
    //public static final String TERM_SPEC_RESOLVER_ENROLLED_COURSE_NUMBER_RANGE = "enrolledCourseNumberTermResolver";
    //public static final String ENROLLED_COURSE_NUMBER_RANGE_DESCR = "Enrolled course number: course number range Term Specification";

    //public static final String TERM_SPEC_ENROLLED_COURSE_NUMBER_SUBJECT_CODE = "Enrolled course number: subject code";
    //public static final String TERM_SPEC_RESOLVER_ENROLLED_COURSE_NUMBER_SUBJECT_CODE = "enrolledCourseCodeTermResolver";
    //public static final String ENROLLED_COURSE_NUMBER_SUBJECT_CODE_DESCR = "Enrolled course number: subject code Term Specification";

    //public static final String TERM_SPEC_ENROLLED_SET = "Enrolled set";
    //public static final String TERM_SPEC_RESOLVER_ENROLLED_SET = "enrolledCourseSet";
    //public static final String ENROLLED_SET_DESCR = "Enrolled set Term Specification";

    //public static final String TERM_SPEC_ENROLLED_COURSES = "Enrolled courses";
    //public static final String TERM_SPEC_RESOLVER_ENROLLED_COURSES = "enrolledCoursesTermResolver";
    //public static final String ENROLLED_COURSES_DESCR = "Enrolled courses Term Specification";

    //public static final String TERM_SPEC_ENROLLED_EFFECTIVE_DATE_FROM = "Enrolled effective date range: effective from";
    //public static final String TERM_SPEC_RESOLVER_ENROLLED_EFFECTIVE_DATE_FROM = "enrolledEffectiveDateFromTermResolver";
    //public static final String ENROLLED_EFFECTIVE_DATE_FROM_DESCR = "Enrolled effective date range: effective from Term Specification";

    //public static final String TERM_SPEC_ENROLLED_EFFECTIVE_DATE_TO = "Enrolled effective date range: effective to";
    //public static final String TERM_SPEC_RESOLVER_ENROLLED_EFFECTIVE_DATE_TO = "enrolledEffectiveDateToTermResolver";
    //public static final String ENROLLED_EFFECTIVE_DATE_TO_DESCR = "Enrolled effective date range: effective to Term Specification";

    //public static final String TERM_SPEC_ENROLLED_LEARNING_OBJ_DESCR = "Enrolled learning Objective: Description";
    //public static final String TERM_SPEC_RESOLVER_ENROLLED_LEARNING_OBJ_DESCR = "enrolledLearningObjectivesTermResolver";
    //public static final String ENROLLED_LEARNING_OBJ_DESCR_DESCR = "Enrolled learning Objective: Description Term Specification";

    //public static final String TERM_SPEC_ENROLLED_COURSE_BY_TERM = "Enrolled Course By Term Term Resolver";
    //public static final String TERM_SPEC_RESOLVER_ENROLLED_COURSE_BY_TERM = "enrolledCourseByTermTermResolver";
    //public static final String ENROLLED_COURSE_BY_TERM_DESCR = "Enrolled Course By Term Term Resolver Term Specification";

    //public static final String TERM_SPEC_ENROLLED_COURSES_BY_TERM = "Enrolled Courses By Term Term Resolver";
    //public static final String TERM_SPEC_RESOLVER_ENROLLED_COURSES_BY_TERM = "enrolledCoursesByTermTermResolver";
    //public static final String ENROLLED_COURSES_BY_TERM_DESCR = "Enrolled Courses By Term Term Resolver Term Specification";

    ////////////////////////////////////////////////////////////////////////////////////////////
    // org.kuali.student.krms.util.KSKRMSExecutionConstants.java                              //
    ////////////////////////////////////////////////////////////////////////////////////////////

    //public static final String STUDENT_DECEASED_TERM_NAME = "studentDeceased";
    //public static final String CURRENT_DATE_TERM_NAME = "currentDate";
    //public static final String STUDENT_ID_TERM_NAME = "studentId";
    //public static final String COURSE_ID_TO_ENROLL_TERM_NAME = "courseIdToEnroll";

    //public static final String GPA_FOR_COURSE_TERM_NAME = "gpaForCourse";
    //public static final String GRADES_FOR_COURSE_SET_TERM_NAME = "gradesForCourseSet";
    //public static final String STUDENT_COMPLETED_COURSE_IDS_TERM_NAME = "studentCompletedCourseIds";
    //public static final String STUDENT_ENROLLED_COURSE_IDS_TERM_NAME = "studentEnrolledCourseIds";
    //public static final String TEST_SET_SCORE_TERM_NAME = "testSetScore";
    //public static final String COMPLETED_CREDITS_FOR_COURSE_SET_TERM_NAME = "creditsForCourseSet";
    //public static final String REGISTRATION_TERM_TERM_NAME = "registrationTerm";
    //public static final String SUMMER_ONLY_STUDENT_TERM_NAME = "summerOnlyStudent";
    //public static final String MILESTONES_BY_TYPE_TERM_NAME = "milestonesByType";
    //public static final String MILESTONE_TERM_NAME = "milestone";
    //public static final String STUDENT_REGISTRATION_HOLDS_TERM_NAME = "studentRegistrationHolds";

    //public static final Term completedCourseIdsTerm = new Term(STUDENT_COMPLETED_COURSE_IDS_TERM_NAME);
    //public static final Term enrolledCourseIdsTerm = new Term(STUDENT_ENROLLED_COURSE_IDS_TERM_NAME);
    //public static final Term studentDeceasedTerm = new Term(STUDENT_DECEASED_TERM_NAME);

    //public static final String TEST_SET_ID_TERM_PROPERTY = "testSetIdProperty";
    //public static final String MILESTONE_TYPE_TERM_PROPERTY = "milestoneTypeProperty";
    //public static final String MILESTONE_ATP_KEY_TERM_PROPERTY = "milestoneAtpKeyProperty";
    //public static final String MILESTONE_ID_TERM_PROPERTY = "milestoneIdProperty";
    //public static final String ISSUE_KEY_TERM_PROPERTY = "issueKeyProperty";

    //public static final String STATEMENT_EVENT_NAME = "statementEvent";
    //public static final String PROCESS_EVENT_NAME = "processEvent";

    //public static final String REGISTRATION_HOLD_WARNINGS_ATTRIBUTE = "registrationHoldWarnings";

    //public static final String DOCTYPE_CONTEXT_QUALIFIER = "docTypeName";
    //public static final String STUDENT_ELIGIBILITY_DOCTYPE = "Student.Eligibility";
    //public static final String SUBPROCESS_EVALUATION_EXCEPTION = "subprocessEvaluationException";
    //public static final String SUBPROCESS_EVALUATION_RESULTS = "subprocessEvaluationResults";

    //public static final String PERSON_ID_TERM_PROPERTY = "personIdProperty";
    //public static final String COURSE_CODE_TERM_PROPERTY = "courseCodeProperty";
    //public static final String COURSE_CODES_TERM_PROPERTY = "courseCodesProperty";
    //public static final String COURSE_ID_TERM_PROPERTY = "courseIdProperty";
    //public static final String COURSESET_ID_TERM_PROPERTY = "coursesetIdProperty";
    //public static final String TERM_ID_TERM_PROPERTY = "termIdProperty";
    //public static final String CALC_TYPE_KEY_TERM_PROPERTY = "calculationTypeKeyProperty";
    //public static final String ORG_ID_TERM_PROPERTY = "orgIdProperty";
    //public static final String GRADE_TYPE_TERM_PROPERTY = "gradeTypeProperty";
    //public static final String GRADE_TERM_PROPERTY = "gradeProperty";

    //public static final String ORG_TYPE_KEY_TERM_PROPERTY = "orgTypeKey";
    //public static final String ADMIN_ORG_NUMBER_TERM_NAME = "adminOrgNumber";

    //public static final String COMPLETED_COURSE_CODE_TERM_NAME = "completedCourseCode";
    //public static final String COMPLETED_COURSE_SET_TERM_NAME = "completedCourseSet";
    //public static final String COMPLETED_COURSES_TERM_NAME = "completedCourses";
    //public static final String COMPLETED_COURSE_TERM_NAME = "completedCourse";
    //public static final String COMPLETED_COURSE_CREDITS_TERM_NAME = "completedCourseCredits";

    //public static final String COMPLETED_EFFECTIVE_DATE_FROM_TERM_NAME = "completedEffectiveDateFrom";
    //public static final String COMPLETED_EFFECTIVE_DATE_TO_TERM_NAME = "completedEffectiveDateTo";

    //public static final String COMPLETED_LEARNING_OBJECTIVES_TERM_NAME = "completedLearningObjectives";
    //public static final String COURSE_NUMBER_RANGE_TERM_NAME = "courseNumberRange";
    //public static final String COURSE_SET_TERM_NAME = "courseSet";
    //public static final String DEPT_NUMBER_TERM_NAME = "deptNumber";

    //public static final String EFFECTIVE_DATE_FROM_TERM_NAME = "effectiveDateFrom";
    //public static final String EFFECTIVE_DATE_TO_TERM_NAME = "effectiveDateTo";

    //public static final String ENROLLED_COURSE_TERM_NAME = "enrolledCourse";
    //public static final String ENROLLED_COURSE_BY_TERM_TERM_NAME = "enrolledCourseByTerm";
    //public static final String ENROLLED_COURSE_CODE_TERM_NAME = "enrolledCourseCode";
    //public static final String ENROLLED_COURSE_NUMBER_TERM_NAME = "enrolledCourseNumber";
    //public static final String ENROLLED_COURSES_BY_TERM_TERM_NAME = "enrolledCoursesByTerm";
    //public static final String ENROLLED_COURSE_SET_TERM_NAME = "enrolledCourseSet";
    //public static final String ENROLLED_COURSES_TERM_NAME = "enrolledCourses";
    //public static final String ENROLLED_EFFECTIVE_DATE_FROM_TERM_NAME = "enrolledEffectiveDateFrom";
    //public static final String ENROLLED_EFFECTIVE_DATE_TO_TERM_NAME = "enrolledEffectiveDateTo";
    //public static final String ENROLLED_LEARNING_OBJECTIVES_TERM_NAME = "enrolledLearningObjectives";

    //public static final String FREE_TEXT_TERM_NAME = "freeText";
    //public static final String GPA_TERM_NAME = "gpa";
    //public static final String GRADE_TERM_NAME = "grade";
    //public static final String GRADE_TYPE_TERM_NAME = "gradeType";

    //public static final String LEARNING_OBJECTIVES_TERM_NAME = "learningObjectives";

    //public static final String NUMBER_OF_COURSES_TERM_NAME = "numberOfCourses";
    //public static final String NUMBER_OF_CREDITS_TERM_NAME = "numberOfCredits";

    //public static final String SCORE_TERM_NAME = "score";
    //public static final String SUBJECT_CODE_TERM_NAME = "subjectCode";
    //public static final String TEST_TERM_NAME = "test";

}
