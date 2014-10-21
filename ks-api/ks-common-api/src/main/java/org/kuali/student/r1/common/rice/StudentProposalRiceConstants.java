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
 * Created by delyea on 8/20/14
 */
package org.kuali.student.r1.common.rice;

/**
 * This class hold constants that deal with Rice elements used by Kuali Student Proposals
 */
public class StudentProposalRiceConstants {

    public static final String DEFAULT_WORKFLOW_DOCUMENT_START_NODE_NAME = "PreRoute";

    public static final String ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAMESPACE = "KS-SYS";
    public static final String ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAME = "Adhoc Permissions: Edit Document";
    public static final String ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAMESPACE = "KS-SYS";
    public static final String ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAME = "Adhoc Permissions: Comment on Document";

    public enum ActionRequestType {
        COMPLETE(org.kuali.rice.kew.api.action.ActionRequestType.COMPLETE.getCode(), "Complete"),
        APPROVE("A", "Approve"),
        ACKNOWLEDGE("K", "Acknowledge"),
        FYI("F", "FYI");

        private String actionRequestCode;
        private String actionRequestLabel;

        private ActionRequestType(String actionRequestCode, String actionRequestLabel) {
            this.actionRequestCode = actionRequestCode;
            this.actionRequestLabel = actionRequestLabel;
        }

        public String getActionRequestCode() {
            return actionRequestCode;
        }

        public void setActionRequestCode(String actionRequestCode) {
            this.actionRequestCode = actionRequestCode;
        }

        public String getActionRequestLabel() {
            return actionRequestLabel;
        }

        public void setActionRequestLabel(String actionRequestLabel) {
            this.actionRequestLabel = actionRequestLabel;
        }

        public static ActionRequestType getByCode(String actionRequestCode) {
            for (ActionRequestType type : ActionRequestType.values()) {
                if (type.getActionRequestCode().equals(actionRequestCode)) {
                    return type;
                }
            }
            return null;
        }
    }

}
