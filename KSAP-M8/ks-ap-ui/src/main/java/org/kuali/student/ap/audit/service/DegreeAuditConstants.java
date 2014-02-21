package org.kuali.student.ap.audit.service;

public class DegreeAuditConstants extends DegreeAuditServiceConstants {

    public static final String DEFAULT_KEY = "default";
    public static final String DEFAULT_VALUE = "Select a degree program or minor";
    public static final String SELECT_CAMPUS_TO_POPULATE_ITS_RELATED_PROGRAMS = "Select a campus (from above) to populate its related programs.";
    public static final String AUDIT_PARAM_ID = "auditId";
    public static final String AUDIT_PARAM_TYPE = "auditType";
    public static final String AUDIT_EMPTY_PAGE = "degree_audit_empty_page";
    public static final String AUDIT_NON_STUDENT_PAGE = "degree_audit_non_student_page";
    public static final String AUDIT_PAGE = "degree_audit";
    public static final String AUDIT_REPORT_SECTION = "audit_report_section";
    public static final String AUDIT_EMPTY_PAGE_SECTION = "empty_degree_audits";

    public static final String ORG_QUERY_SEARCH_BY_TYPE_REQUEST = "org.search.orgInfoByType";
    public static final String ORG_QUERY_PARAM = "org_queryParam_orgType";
    public static final String PROGRAM_TYPE_KEY = "kuali.org.Program";
    public static final String CAMPUS_LOCATION = "kuali.org.type.campus";

    public static final String AUDIT_RUN_FAILED = "ksap.text.error.message.auditIncomplete";
    public static final String AUDIT_RETRIEVAL_FAILED = "ksap.text.error.message.auditRetrievalFailed";
    public static final String AUDIT_SUMMARY_VIEW = "degree_audits_list";
    public static final String TECHNICAL_PROBLEM = "ksap.text.error.technicalProblems";
    public static final String NO_SYSTEM_KEY = "ksap.text.error.noSystemKey";
    public static final String DEGREE_AUDIT_INFO_SELECT_PROGRAM_BEFORE_SUBMIT = "ksap.text.warning.degree.audit.select.a.program";
    public static final String APPLICATION_URL = "application.url";
//
//    public static final String AUDIT_FAILED_HTML =  "<div class=\"uif-validationMessages uif-groupValidationMessages uif-pageValidationMessages " +
//                                                    "uif-pageValidationMessages-error\" style=\"\" data-messagesfor=\"degree_audit\"><h3 tabindex=\"0\" " +
//                                                    "class=\"uif-pageValidationHeader\" id=\"pageValidationHeader\">" +
//                                                    "<img class=\"uif-validationImage\" src=\"%s/krad/images/validation/error.png\" alt=\"Error\"> " +
//                                                    "This page has 1 error</h3>" +
//                                                    "<ul class=\"uif-validationMessagesList\" id=\"pageValidationList\" aria-labelledby=\"pageValidationHeader\">" +
//                                                    "<li data-messageitemfor=\"select_programParam_for_campus_310\" class=\"uif-errorMessageItem\">" +
//                                                    "Audit processing failed due to '%s'" +
//                                                    "</li>" +
//                                                    "</ul>" +
//                                                    "<input name=\"script\" type=\"hidden\" data-role=\"script\" value=\"removeCookie();\"/></div>";


    public static final String AUDIT_FAILED_HTML = "<div class=\"uif-validationMessages uif-groupValidationMessages uif-pageValidationMessages " +
            "uif-pageValidationMessages-error\" style=\"\" data-messagesfor=\"degree_audit\"><h3 tabindex=\"0\" " +
            "class=\"uif-pageValidationHeader\" id=\"pageValidationHeader\">" +
            "<img class=\"uif-validationImage\" src=\"%s/krad/images/validation/error.png\" alt=\"Error\"> " +
            "This page has 1 error</h3>" +
            "<ul class=\"uif-validationMessagesList\" id=\"pageValidationList\" aria-labelledby=\"pageValidationHeader\">" +
            "<li data-messageitemfor=\"select_programParam\" class=\"uif-errorMessageItem\">" +
            "Audit processing failed due to '%s'" +
            "</li>" +
            "</ul>" +
            "<input name=\"script\" type=\"hidden\" data-role=\"script\" value=\"removeCookie();\"/></div>";
    public static final String IS_AUDIT_SERVICE_UP = "isAuditServiceRunning";


}
