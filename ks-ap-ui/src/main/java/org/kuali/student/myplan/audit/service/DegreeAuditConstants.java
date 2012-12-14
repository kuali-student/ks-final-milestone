package org.kuali.student.myplan.audit.service;

import org.kuali.student.r2.common.dto.ContextInfo;

public class DegreeAuditConstants extends DegreeAuditServiceConstants {

    public static final ContextInfo CONTEXT_INFO = new ContextInfo();

    public static final String DEFAULT_KEY="default";
    public static final String DEFAULT_VALUE="Select a degree program or minor";
    public static final String AUDIT_PARAM_ID = "auditId";
    public static final String AUDIT_PARAM_TYPE = "auditType";
    public static final String AUDIT_EMPTY_PAGE = "degree_audit_empty_page";
    public static final String AUDIT_NON_STUDENT_PAGE = "degree_audit_non_student_page";
    public static final String AUDIT_PAGE = "degree_audit";
    public static final String AUDIT_REPORT_SECTION = "audit_report_section";
    public static final String AUDIT_EMPTY_PAGE_SECTION = "empty_degree_audits";

    public static final String ORG_QUERY_PARAM = "org_queryParam_orgType";

    public static final String CAMPUS_LOCATION = "kuali.uw.org.type.campus";

    public static final String AUDIT_RUN_FAILED = "myplan.text.error.message.auditIncomplete";
    public static final String AUDIT_RETRIEVAL_FAILED = "myplan.text.error.message.auditRetrievalFailed";
    public static final String AUDIT_SUMMARY_VIEW = "degree_audits_list";
    public static final String TECHNICAL_PROBLEM = "myplan.text.error.technicalProblems";
    public static final String NO_SYSTEM_KEY="myplan.text.error.noSystemKey";
    
    public static final String APPLICATION_URL="application.url";

    public static final String AUDIT_FAILED_HTML =  "<div class=\"uif-validationMessages uif-groupValidationMessages uif-pageValidationMessages " +
                                                    "uif-pageValidationMessages-error\" style=\"\" data-messagesfor=\"degree_audit\"><h3 tabindex=\"0\" " +
                                                    "class=\"uif-pageValidationHeader\" id=\"pageValidationHeader\">" +
                                                    "<img class=\"uif-validationImage\" src=\"%s/krad/images/validation/error.png\" alt=\"Error\"> " +
                                                    "This page has 1 error</h3>" +
                                                    "<ul class=\"uif-validationMessagesList\" id=\"pageValidationList\" aria-labelledby=\"pageValidationHeader\">" +
                                                    "<li data-messageitemfor=\"select_programParam_seattle\" class=\"uif-errorMessageItem\">" +
                                                    "Audit processing failed due to '%s'" +
                                                    "</li>" +
                                                    "</ul>" +
                                                    "<input name=\"script\" type=\"hidden\" data-role=\"script\" value=\"removeCookie();\"/></div>";
    public static final String IS_AUDIT_SERVICE_UP = "isAuditServiceRunning";




}
