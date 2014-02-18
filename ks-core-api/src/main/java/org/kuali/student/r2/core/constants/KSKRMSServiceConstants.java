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
    public static final String AGENDA_TYPE_FINAL_EXAM = "kuali.krms.agenda.type.final.exam";

    // Agenda Types
    public static final String AGENDA_TYPE_COURSE_ENROLLMENTELIGIBILITY = "kuali.krms.agenda.type.course.enrollmentEligibility";
    public static final String AGENDA_TYPE_COURSE_CREDITCONSTRAINTS = "kuali.krms.agenda.type.course.creditConstraints";
    public static final String AGENDA_TYPE_SCHEDULE_ELIGIBILITY = "kuali.krms.agenda.type.schedule.eligibility";
    public static final String AGENDA_TYPE_FINAL_EXAM_STANDARD = "kuali.krms.agenda.type.final.exam.standard";
    public static final String AGENDA_TYPE_FINAL_EXAM_COMMON = "kuali.krms.agenda.type.final.exam.common";

    public static final String AGENDA_ATTRIBUTE_FINAL_EXAM_OWNER_TERM_TYPE = "kuali.krms.agenda.attribute.owner.term.type";

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
    public static final String RULE_TYPE_FINAL_EXAM_STANDARD = "kuali.krms.rule.type.final.exam.standard";
    public static final String RULE_TYPE_FINAL_EXAM_COMMON = "kuali.krms.rule.type.final.exam.common";

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
    public static final String PROPOSITION_TYPE_FINAL_EXAM_TIMESLOT = "kuali.krms.proposition.type.final.exam.timeslot";
    public static final String PROPOSITION_TYPE_FINAL_EXAM_COURSE_OFFERING = "kuali.krms.proposition.type.final.exam.course.offering";
    public static final String PROPOSITION_TYPE_FINAL_EXAM_COURSESET = "kuali.krms.proposition.type.final.exam.courseset";

    // Action types
    public static final String ACTION_TYPE_REQUESTED_DELIVERY_LOGISTIC = "kuali.krms.action.type.requested.delivery.logistic";

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
    public static final String TERM_PARAMETER_TYPE_GPA_KEY = "kuali.term.parameter.type.gpa";
    public static final String TERM_PARAMETER_TYPE_INCLUSION_FILTER_TYPE_KEY = "kuali.term.parameter.type.inclusionFilter.type";
    public static final String TERM_PARAMETER_TYPE_INCLUSION_FILTER_VALUE_KEY = "kuali.term.parameter.type.inclusionFilter.value";
    public static final String TERM_PARAMETER_TYPE_PERSON_KEY = "kuali.term.parameter.type.person.id";
    public static final String TERM_PARAMETER_TYPE_GRADE_TYPE_KEY = "kuali.term.parameter.type.gradeType.id";
    public static final String TERM_PARAMETER_TYPE_GRADE_KEY = "kuali.term.parameter.type.grade.id";
    public static final String TERM_PARAMETER_TYPE_DURATION_KEY = "kuali.term.parameter.type.duration";
    public static final String TERM_PARAMETER_TYPE_DURATION_TYPE_KEY = "kuali.term.parameter.type.durationType.id";
    public static final String TERM_PARAMETER_TYPE_ORGANIZATION_KEY = "kuali.term.parameter.type.org.id";
    public static final String TERM_PARAMETER_TYPE_CLU_KEY = "kuali.term.parameter.type.clu.id";
    public static final String TERM_PARAMETER_TYPE_CLUSET_KEY = "kuali.term.parameter.type.cluSet.id";
    public static final String TERM_PARAMETER_TYPE_COURSE_CLU_KEY = "kuali.term.parameter.type.course.clu.id";
    public static final String TERM_PARAMETER_TYPE_COURSE_CLUSET_KEY = "kuali.term.parameter.type.course.cluSet.id";
    public static final String TERM_PARAMETER_TYPE_CO_KEY = "kuali.term.parameter.type.course.offering.id";
    public static final String TERM_PARAMETER_TYPE_TERM_KEY = "kuali.term.parameter.type.Term";
    public static final String TERM_PARAMETER_TYPE_TERM2_KEY = "kuali.term.parameter.type.Term2";
    public static final String TERM_PARAMETER_TYPE_TOTAL_CREDIT_KEY = "kuali.term.parameter.type.totalCredits";
    public static final String TERM_PARAMETER_TYPE_PROGRAM_CLU_KEY = "kuali.term.parameter.type.program.clu.id";
    public static final String TERM_PARAMETER_TYPE_PROGRAM_CLUSET_KEY = "kuali.term.parameter.type.program.cluSet.id";
    public static final String TERM_PARAMETER_TYPE_CLASS_STANDING_KEY = "kuali.term.parameter.type.classStanding";

    //note, this is a test as in assessment, not environment
    public static final String TERM_PARAMETER_TYPE_TEST_CLU_KEY = "kuali.term.parameter.type.test.clu.id";
    public static final String TERM_PARAMETER_TYPE_TEST_CLUSET_KEY = "kuali.term.parameter.type.test.cluSet.id";
    public static final String TERM_PARAMETER_TYPE_TERMCODE_KEY = "kuali.term.parameter.type.TermCode";
    public static final String TERM_PARAMETER_TYPE_TERMCODE2_KEY = "kuali.term.parameter.type.TermCode2";
    public static final String TERM_PARAMETER_TYPE_POPULATION_KEY = "kuali.term.parameter.type.population";
    public static final String TERM_PARAMETER_TYPE_FREE_TEXT_KEY = "kuali.term.parameter.type.free.text";

    public static final String TERM_PARAMETER_TYPE_TIMESLOT_WEEKDAY_STRING = "kuali.term.parameter.type.timeslot.weekday.string";
    public static final String TERM_PARAMETER_TYPE_TIMESLOT_START = "kuali.term.parameter.type.timeslot.start";
    public static final String TERM_PARAMETER_TYPE_TIMESLOT_END = "kuali.term.parameter.type.timeslot.end";

    // term resolver types
    public static final String TERM_RESOLVER_TYPE_FINAL_EXAM = "kuali.krms.termresolver.type.final.exam";

    // action parameters
    public static final String ACTION_PARAMETER_TYPE_RDL_TBA = "tba";
    public static final String ACTION_PARAMETER_TYPE_RDL_DAY = "day";
    public static final String ACTION_PARAMETER_TYPE_RDL_STARTTIME = "startTime";
    public static final String ACTION_PARAMETER_TYPE_RDL_ENDTIME = "endTime";
    public static final String ACTION_PARAMETER_TYPE_RDL_FACILITY = "facility";
    public static final String ACTION_PARAMETER_TYPE_RDL_ROOM = "room";

    // term parameter types for nl
    public static final String TERM_PARAMETER_TYPE_CLULIST_KEY = "kuali.term.parameter.type.course.nl.clu.list";
    public static final String TERM_PARAMETER_TYPE_CLUSETLIST_KEY = "kuali.term.parameter.type.course.nl.cluset.list";

    // term specs
    public static final String TERM_SPEC_COMPLETEDCOURSE = "CompletedCourse";
    public static final String TERM_SPEC_COMPLETEDCOURSES = "CompletedCourses";
    public static final String TERM_SPEC_NUMBEROFCOMPLETEDCOURSES = "NumberOfCompletedCourses";
    public static final String TERM_SPEC_ENROLLEDCOURSE = "EnrolledCourse";
    public static final String TERM_SPEC_ENROLLEDCOURSES = "EnrolledCourses";
    public static final String TERM_SPEC_NUMBEROFENROLLEDCOURSES = "NumberOfEnrolledCourses";
    public static final String TERM_SPEC_GPAFORCOURSES = "GPAForCourses";
    public static final String TERM_SPEC_GPAFORDURATION = "GPAForDuration";
    public static final String TERM_SPEC_GPA = "GPA";
    public static final String TERM_SPEC_COURSEWITHGRADE = "CourseWithGrade";
    public static final String TERM_SPEC_COURSESWITHGRADE = "CoursesWithGrade";
    public static final String TERM_SPEC_NUMBEROFCOURSESWITHGRADE = "NumberCoursesWithGrade";
    public static final String TERM_SPEC_NUMBEROFCREDITSFROMORGANIZATION = "NumberOfCreditsFromOrganization";
    public static final String TERM_SPEC_NUMBEROFCREDITSFROMCOMPLETEDCOURSES = "NumberOfCreditsFromCompletedCourses";
    public static final String TERM_SPEC_NUMBEROFCREDITSEARNED = "NumberOfCreditsEarned";
    public static final String TERM_SPEC_ADMINORGANIZATIONPERMISSIONREQUIRED = "AdminOrganizationPermissionRequired";
    public static final String TERM_SPEC_SCOREONTEST = "ScoreOnTest";
    public static final String TERM_SPEC_FREEFORMTEXT = "FreeFormText";
    public static final String TERM_SPEC_ADMITTEDTOPROGRAMLIMITCOURSESINORGFORDURATION = "AdmittedToProgramLimitCoursesInOrgForDuration";
    public static final String TERM_SPEC_ADMITTEDTOPROGRAM = "AdmittedToProgram";
    public static final String TERM_SPEC_ADMITTEDTOPROGRAMATCOURSECAMPUS = "AdmittedToProgramAtCourseCampus";
    public static final String TERM_SPEC_ADMITTEDTOPROGRAMWITHCLASSSTANDING = "AdmittedToProgramWithClassStanding";
    public static final String TERM_SPEC_COMPLETEDCOURSEFORTERM = "CompletedCourseForTerm";
    public static final String TERM_SPEC_COMPLETEDCOURSEPRIORTOTERM = "CompletedCoursePriorToTerm";
    public static final String TERM_SPEC_COMPLETEDCOURSEBETWEENTERMS = "CompletedCourseBetweenTerms";
    public static final String TERM_SPEC_POPULATION= "Population";
    public static final String TERM_SPEC_INSTRUCTORPERMISSION = "InstructorPermission";
    public static final String TERM_SPEC_MATCHINGTIMESLOT = "MatchingTimeSlot";
    public static final String TERM_SPEC_MATCHINGCOURSE = "MatchingCourse";
    public static final String TERM_SPEC_MATCHINGCOURSESET = "MatchingCourseSet";

    // term resolvers
    public static final String TERM_RESOLVER_COMPLETEDCOURSE = "CompletedCourse";
    public static final String TERM_RESOLVER_COMPLETEDCOURSES = "CompletedCourses";
    public static final String TERM_RESOLVER_NUMBEROFCOMPLETEDCOURSES = "NumberOfCompletedCourses";
    public static final String TERM_RESOLVER_ENROLLEDCOURSE = "EnrolledCourse";
    public static final String TERM_RESOLVER_ENROLLEDCOURSES = "EnrolledCourses";
    public static final String TERM_RESOLVER_NUMBEROFENROLLEDCOURSES = "NumberOfEnrolledCourses";
    public static final String TERM_RESOLVER_GPAFORCOURSES = "GPAForCourses";
    public static final String TERM_RESOLVER_GPAFORDURATION = "GPAForDuration";
    public static final String TERM_RESOLVER_GPA = "GPA";
    public static final String TERM_RESOLVER_COURSEWITHGRADE = "CourseWithGrade";
    public static final String TERM_RESOLVER_COURSESWITHGRADE = "CoursesWithGrade";
    public static final String TERM_RESOLVER_NUMBEROFCOURSESWITHGRADE = "NumberCoursesWithGrade";
    public static final String TERM_RESOLVER_NUMBEROFCREDITSFROMORGANIZATION = "NumberOfCreditsFromOrganization";
    public static final String TERM_RESOLVER_NUMBEROFCREDITSFROMCOMPLETEDCOURSES = "NumberOfCreditsFromCompletedCourses";
    public static final String TERM_RESOLVER_NUMBEROFCREDITSEARNED = "NumberOfCreditsEarned";
    public static final String TERM_RESOLVER_ADMINORGANIZATIONPERMISSIONREQUIRED = "AdminOrgPermission";
    public static final String TERM_RESOLVER_SCOREONTEST = "Score";
    public static final String TERM_RESOLVER_FREEFORMTEXT = "FreeFormText";
    public static final String TERM_RESOLVER_ADMITTEDTOPROGRAMLIMITCOURSESINORGFORDURATION = "AdmittedToProgramLimitCoursesInOrgForDuration";
    public static final String TERM_RESOLVER_ADMITTEDTOPROGRAM = "AdmittedProgram";
    public static final String TERM_RESOLVER_ADMITTEDTOPROGRAMATCOURSECAMPUS = "AdmittedToProgramAtCourseCampus";
    public static final String TERM_RESOLVER_ADMITTEDTOPROGRAMWITHCLASSSTANDING = "AdmittedToProgramWithClassStanding";
    public static final String TERM_RESOLVER_COMPLETEDCOURSEFORTERM = "CompletedCourseForTerm";
    public static final String TERM_RESOLVER_COMPLETEDCOURSEPRIORTOTERM = "CompletedCoursePriorToTerm";
    public static final String TERM_RESOLVER_COMPLETEDCOURSEBETWEENTERMS = "CompletedCourseBetweenTerms";
    public static final String TERM_RESOLVER_POPULATION= "Population";
    public static final String TERM_RESOLVER_INSTRUCTORPERMISSION = "InstructorPermission";
    public static final String TERM_RESOLVER_MATCHINGTIMESLOT = "MatchingTimeSlot";
    public static final String TERM_RESOLVER_MATCHINGCOURSE = "MatchingCourse";
    public static final String TERM_RESOLVER_MATCHINGCOURSESET = "MatchingCourseSet";

    // natural language usage keys
    public static final String KRMS_NL_RULE_EDIT = "kuali.krms.edit";
    public static final String KRMS_NL_COMPOSITION = "kuali.krms.composition";
    public static final String KRMS_NL_EXAMPLE = "kuali.krms.example";
    public static final String KRMS_NL_PREVIEW = "kuali.krms.preview";
    public static final String KRMS_NL_TYPE_DESCRIPTION = "kuali.krms.type.description";
    public static final String KRMS_NL_TYPE_CATALOG = "kuali.krms.catalog";
    public static final String KRMS_NL_TYPE_INSTRUCTION = "kuali.krms.type.instruction";

    // term prerequisite
    public static final String TERM_PREREQUISITE_CONTEXTINFO = "kuali.term.prerequisite.contextInfo";
    public static final String TERM_PREREQUISITE_PERSON_ID = "kuali.term.prerequisite.personId";
    public static final String TERM_PREREQUISITE_CLU_ID = "kuali.term.prerequisite.cluId";
    public static final String TERM_PREREQUISITE_CO_ID = "kuali.term.prerequisite.courseOfferingId";
    public static final String TERM_PREREQUISITE_CO = "kuali.term.prerequisite.courseOffering";
    public static final String TERM_PREREQUISITE_AO_ID = "kuali.term.prerequisite.activityOffering";
    public static final String TERM_PREREQUISITE_AO = "kuali.term.prerequisite.activityOffering";
    public static final String TERM_PREREQUISITE_EO_ID = "kuali.term.prerequisite.examOfferingId";
    public static final String TERM_PREREQUISITE_TERM_ID = "kuali.term.prerequisite.termId";
    public static final String TERM_PREREQUISITE_TIMESLOTS = "kuali.term.prerequisite.timeslots";
    public static final String TERM_PREREQUISITE_COURSE_VERSIONINDID = "kuali.term.prerequisite.course.versionIndId";

}
