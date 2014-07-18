package org.kuali.student.enrollment.class2.registration.admin.util;

/**
 * Created with IntelliJ IDEA.
 * User: Blue Team (SA)
 * Date: 17 July 2014
 *
 * Admin Registration User Interface constants.
 */
public class AdminRegConstants {

    //Admin Reg States
    public static class ClientStates{
        public static final String OPEN = "kuali.admin.reg.state.open";
        public static final String INITIALIZED = "kuali.admin.reg.state.initialized";
        public static final String READY = "kuali.admin.reg.state.ready";
        public static final String REGISTERING = "kuali.admin.reg.state.registering";
    }

    // Collection component ids.
    public static final String REG_COLL_ID = "KS-AdminRegistration-Registered";
    public static final String WAITLIST_COLL_ID = "KS-AdminRegistration-Waitlist";
    public static final String ISSUES_COLL_ID = "KS-AdminRegistration-Issues";

    // Dialog ids.
    public static final String REG_CONFIRM_DIALOG = "registerConfirmDialog";
    public static final String DROP_COURSE_DIALOG = "dropCourseDialog";
    public static final String DROP_WAITLISTED_DIALOG = "dropWaitlistedDialog";

    //Message keys
    public static final String ADMIN_REG_MSG_ERROR_INVALID_STUDENT = "error.admin.reg.studentId.invalid";
    public static final String ADMIN_REG_MSG_ERROR_STUDENT_REQUIRED = "error.admin.reg.studentId.required";
    public static final String ADMIN_REG_MSG_ERROR_STUDENT_ROLE_NOT_FOUND = "error.admin.reg.studentId.required";
    public static final String ADMIN_REG_MSG_ERROR_MULTIPLE_TERMS = "error.admin.reg.multiple.terms";
    public static final String ADMIN_REG_MSG_ERROR_INVALID_TERM = "error.admin.reg.term.invalid";
    public static final String ADMIN_REG_MSG_ERROR_TERM_CODE_REQUIRED = "error.admin.reg.termcode.required";
    public static final String ADMIN_REG_MSG_ERROR_COURSE_CODE_TERM_INVALID = "error.admin.reg.courseoffering.code.term.invalid";
    public static final String ADMIN_REG_MSG_ERROR_COURSE_CODE_INVALID = "error.admin.reg.courseoffering.code.invalid";
    public static final String ADMIN_REG_MSG_ERROR_SECTION_CODE_INVALID = "error.admin.reg.section.code.invalid";

    //sections ids
    public static final String STUDENT_INFO_SECTION = "KS-AdminRegistration-StudentInfo";

    //form property names
    public static final String PERSON_ID = "person.id";
    public static final String TERM_CODE = "term.code";
    public static final String PENDING_COURSES = "pendingCourses";
    public static final String REGISTERED_COURSES = "registeredCourses";
    public static final String WAITLISTED_COURSES = "waitlistedCourses";
    public static final String CODE = "code";
    public static final String SECTION = "section";

    //Affiliation Types
    public static final String STUDENT_AFFILIATION_TYPE_CODE= "STDNT";

}
