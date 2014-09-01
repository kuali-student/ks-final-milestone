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
public class HoldIssueConstants {


    public static final String NAMESPACE = "http://student.kuali.org/wsdl/HoldIssueAuthorizingOrgFacade";
    public static final String SERVICE_NAME_LOCAL_PART = "HoldIssueAuthorizingOrgFacade";

    public static final String HOLD_ISSUE_DESCR_PLAIN = "descrPlain";
    public static final String HOLD_ISSUE_SEARCH_ERROR_MSG = "Error Performing Search";
    public static final String HOLD_ISSUE_PROCESS = "Process";

    //Hold Issue Attributes
    public static final String HOLD_ISSUE_ID = "id";
    public static final String HOLD_ISSUE_NAME = "name";
    public static final String HOLD_ISSUE_ORG_ID = "organizationId";
    public static final String HOLD_ISSUE_CODE = "holdCode";
    public static final String HOLD_ISSUE_TYPE_KEY = "typeKey";
    public static final String HOLD_ISSUE_STATE_KEY = "stateKey";
    public static final String HOLD_ISSUE_FIRST_APP_TERM_ID = "firstApplicationTermId";
    public static final String HOLD_ISSUE_LAST_APP_TERM_ID = "lastApplicationTermId";
    public static final String HOLD_ISSUE_LAST_APPLIED_DATE_ID = "holdLastAppliedDate";
    public static final String HOLD_ISSUE_HOLDISSUE_ELEMENTPATH = "document.newMaintainableObject.dataObject.holdIssue";

    //UI bean ids
    public static final String HOLD_ISSUE_CREATE_PAGE = "KS-Hold-Create-Page";
    public static final String HOLD_ISSUE_MANAGE_PAGE = "KS-Hold-SearchInput-Page";

    //error Message keys
    public static final String HOLDS_ISSUE_MSG_ERROR_HOLD_ISSUE_NAME_REQUIRED = "error.hold.issue.name.required";
    public static final String HOLDS_ISSUE_MSG_ERROR_HOLD_ISSUE_TYPE_REQUIRED = "error.hold.issue.type.required";
    public static final String HOLDS_ISSUE_MSG_ERROR_HOLD_ISSUE_ORG_ID_REQUIRED = "error.hold.issue.orgId.required";
    public static final String HOLDS_ISSUE_MSG_ERROR_HOLD_ISSUE_CODE_REQUIRED = "error.hold.issue.code.required";
    public static final String HOLDS_ISSUE_MSG_ERROR_HOLDCODE_ALREADY_EXISTS = "error.hold.issue.code.exist";
    public static final String HOLDS_ISSUE_MSG_ERROR_FIRST_APPLICATION_TERMID = "error.hold.issue.first.application.term.id";
    public static final String HOLDS_ISSUE_MSG_ERROR_LAST_APPLICATION_TERMID = "error.hold.issue.last.application.term.id";
    public static final String HOLDS_ISSUE_MSG_ERROR_INVALID_DATE_RANGE = "error.hold.daterange.invalid";

    //info Message keys
    public static final String HOLDS_ISSUE_MSG_INFO_HOLD_ISSUE_SAVE_SUCCESS = "info.enroll.save.success";

    //workflow
    public static final String NEW_HOLD_ISSUE_DOCUMENT_TEXT = "New Hold Issue Document";
    public static final String MODIFY_HOLD_ISSUE_DOCUMENT_TEXT = "Modify Hold Issue Document";
    //Permissions
    public static final String APPLY_HOLD_ROLE_PERMISSION = "Hold Apply Hold Role";
    public static final String EXPIRE_APPLIED_HOLD_ROLE_PERMISSION = "Hold Expire Applied Hold Role";

}
