/**
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Administrator on 2014/08/15
 */
package org.kuali.student.enrollment.class1.hold.util;

import org.kuali.student.core.person.service.PersonServiceNamespace;

/**
 * This class contains all the HoldIssue Constants
 *
 * @author Kuali Student Team
 */
public class HoldsConstants {


    public static final String NAMESPACE = "http://student.kuali.org/wsdl/HoldIssueAuthorizingOrgFacade";
    public static final String SERVICE_NAME_LOCAL_PART = "HoldIssueAuthorizingOrgFacade";

    public static final String HOLD_ISSUE_DESCR_PLAIN = "descrPlain";
    public static final String HOLD_ISSUE_SEARCH_ERROR_MSG = "Error Performing Search";
    public static final String HOLD_ISSUE_PROCESS = "Process";

    public static final String HOLD_ISSUE_BASEURL = "holdIssueMaintenance";
    public static final String APPLIED_HOLD_BASEURL = "appliedHoldMaintenance";

    public static final String HOLD_ISSUE_MAINTENANCE_VIEWNAME = "HoldIssueMaintenanceView";
    public static final String APPLIED_HOLD_MAINTENANCE_VIEWNAME = "AppliedHoldMaintenanceView";

    //Hold Issue Attributes
    public static final String HOLD_ISSUE_ID = "id";
    public static final String HOLD_ISSUE_NAME = "name";
    public static final String HOLD_ISSUE_ORG_ID = "organizationId";
    public static final String HOLD_ISSUE_ORG_NAME = "orgName";
    public static final String HOLD_ISSUE_HOLD_CODE = "holdCode";
    public static final String HOLD_ISSUE_TYPE_KEY = "typeKey";
    public static final String HOLD_ISSUE_STATE_KEY = "stateKey";
    public static final String HOLD_ISSUE_DESCT_DOT_PLAIN = "descr.plain";
    public static final String HOLD_ISSUE_FIRST_APP_TERM_ID = "firstApplicationTermId";
    public static final String HOLD_ISSUE_LAST_APP_TERM_ID = "lastApplicationTermId";
    public static final String HOLD_ISSUE_FIRST_APPLIED_DATE = "firstAppliedDate";
    public static final String HOLD_ISSUE_LAST_APPLIED_DATE = "lastAppliedDate";

    //Hold Issue property names
    public static final String HOLDS_DATAOBJECT_PATH = "document.newMaintainableObject.dataObject";

    public static final String HOLD_ISSUE_PROP_NAME = "holdIssue";
    public static final String HOLD_ISSUE_HOLDISSUE_PATH = HOLDS_DATAOBJECT_PATH + "." + HOLD_ISSUE_PROP_NAME;
    public static final String HOLD_ISSUE_PROP_NAME_CODE = HOLD_ISSUE_HOLDISSUE_PATH + "." + HOLD_ISSUE_HOLD_CODE;

    public static final String HOLD_ISSUE_PROP_NAME_FIRST_APPLIED_DATE = HOLD_ISSUE_HOLDISSUE_PATH + "." + HOLD_ISSUE_FIRST_APPLIED_DATE;
    public static final String HOLD_ISSUE_PROP_NAME_LAST_APPLIED_DATE = HOLD_ISSUE_HOLDISSUE_PATH + "." + HOLD_ISSUE_LAST_APPLIED_DATE;

    public static final String HOLD_ISSUE_PROP_NAME_FIRST_TERM = HOLDS_DATAOBJECT_PATH + "." + "firstTerm";
    public static final String HOLD_ISSUE_PROP_NAME_LAST_TERM = HOLDS_DATAOBJECT_PATH + "." + "lastTerm";

    public static final String HOLD_ISSUE_PROP_NAME_AUTH_ORGS = HOLDS_DATAOBJECT_PATH + "." + "authorizedOrgs";
    public static final String HOLD_ISSUE_PROP_NAME_AUTH_ORG_NAME = "name";

    //Applied Hold property names
    public static final String APPLIED_HOLDS_PERSON_ID = "person.id";
    public static final String APPLIED_HOLDS_FIRST_APPLIED_DATE = "effectiveDate";
    public static final String APPLIED_HOLDS_FIRST_TERM = "firstTerm";
    public static final String APPLIED_HOLDS_PROP_NAME = "appliedHold";
    public static final String APPLIED_HOLDS_PATH = HOLDS_DATAOBJECT_PATH;
    public static final String APPLIED_HOLDS_PROP_NAME_CODE =  APPLIED_HOLDS_PATH + "." + HOLD_ISSUE_HOLD_CODE;
    public static final String APPLIED_HOLDS_PROP_NAME_FIRST_TERM = APPLIED_HOLDS_PATH + "." + APPLIED_HOLDS_FIRST_TERM;
    public static final String APPLIED_HOLDS_PROP_NAME_FIRST_APPLIED_DATE = APPLIED_HOLDS_PATH + "." + APPLIED_HOLDS_PROP_NAME +"."+APPLIED_HOLDS_FIRST_APPLIED_DATE;

    //error Message keys
    public static final String HOLDS_ISSUE_MSG_ERROR_HOLD_ISSUE_NAME_REQUIRED = "error.hold.issue.name.required";
    public static final String HOLDS_ISSUE_MSG_ERROR_HOLD_ISSUE_TYPE_REQUIRED = "error.hold.issue.type.required";
    public static final String HOLDS_ISSUE_MSG_ERROR_HOLD_ISSUE_ORG_ID_REQUIRED = "error.hold.issue.orgId.required";
    public static final String HOLDS_ISSUE_MSG_ERROR_HOLD_ISSUE_CODE_REQUIRED = "error.hold.issue.code.required";
    public static final String HOLDS_ISSUE_MSG_ERROR_HOLDCODE_ALREADY_EXISTS = "error.hold.issue.code.exist";
    public static final String HOLDS_ISSUE_MSG_ERROR_FIRST_APPLICATION_TERMID = "error.hold.issue.first.application.term.id";
    public static final String HOLDS_ISSUE_MSG_ERROR_LAST_APPLICATION_TERMID = "error.hold.issue.last.application.term.id";
    public static final String HOLDS_ISSUE_MSG_ERROR_INVALID_DATE_RANGE = "error.hold.daterange.invalid";
    public static final String HOLDS_ISSUE_MSG_ERROR_AUTHORIZED_ORG = "error.hold.issue.org.no.permission.selected";
    public static final String HOLDS_ISSUE_MSG_ERROR_AUTHORIZED_ORG_REQUIRED = "error.hold.issue.org.required";
    public static final String HOLDS_ISSUE_MSG_ERROR_AUTHORIZED_ORG_DUPLICATE = "error.hold.issue.org.duplicate";
    public static final String HOLDS_ISSUE_MSG_ERROR_INVALID_TERM = "error.hold.issue.term.invalid";
    public static final String HOLDS_ISSUE_MSG_ERROR_FIRST_TERM_REQUIRED = "error.hold.issue.first.term.required";

    //applied holds error msg
    public static final String APPLIED_HOLDS_MSG_ERROR_STUDENT_REQUIRED = "error.admin.reg.studentId.required";
    public static final String APPLIED_HOLDS_MSG_ERROR_INVALID_STUDENT = "error.admin.reg.studentId.invalid";
    public static final String APPLIED_HOLDS_MSG_ERROR_NO_STUDENT_AFFILIATION = "error.admin.reg.studentId.no.affiliation";
    public static final String APPLIED_HOLDS_MSG_ERROR_HOLD_CODE_INVALID = "error.hold.issue.applied.code.invalid";
    public static final String APPLIED_HOLDS_MSG_ERROR_HOLD_CODE_ALREADY_APPLIED = "error.hold.issue.applied.code.exist";
    public static final String APPLIED_HOLDS_MSG_ERROR_FIRST_TERM_REQUIRED = "error.hold.issue.first.term.required";
    public static final String APPLIED_HOLDS_MSG_ERROR_FIRST_APPLIED_DATE_REQUIRED = "error.hold.issue.applied.first.date.required";
    public static final String APPLIED_HOLDS_MSG_ERROR_UNAUTHORIZED_APPLY = "error.hold.issue.applied.unauthorized.apply";
    public static final String APPLIED_HOLDS_MSG_ERROR_UNAUTHORIZED_EXPIRE = "error.hold.issue.applied.unauthorized.expire";


    //info Message keys
    public static final String HOLDS_ISSUE_MSG_INFO_HOLD_ISSUE_SAVE_SUCCESS = "info.enroll.save.success";

    //workflow
    public static final String NEW_HOLD_ISSUE_DOCUMENT_TEXT = "New Hold Issue Document";
    public static final String MODIFY_HOLD_ISSUE_DOCUMENT_TEXT = "Modify Hold Issue Document";

    //Permissions
    public static final String APPLY_HOLD_ROLE_PERMISSION = "Hold Apply Hold Role";
    public static final String EXPIRE_APPLIED_HOLD_ROLE_PERMISSION = "Hold Expire Applied Hold Role";
    public static final String APPLIED_HOLD_ACTION_EVENT_APPLY_HOLD = "Apply Hold";
    public static final String APPLIED_HOLD_ACTION_EVENT_EXPIRE_HOLD = "Expire Hold";

}
