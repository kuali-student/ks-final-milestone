package org.kuali.student.r2.core.constants;

import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.core.comment.dto.CommentInfo;

/**
 * This class holds the constants used by the Comment service
 *
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 *
 */
public class CommentServiceConstants {
    /**
     * Reference Object URI's
     */
    public static final String NAMESPACE = CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "comment";
    public static final String REF_OBJECT_URI_COMMENT = NAMESPACE + "/" + CommentInfo.class.getSimpleName();

    //comment types
    public static final String COMMENT_ADMINISTRATIVE_TYPE_KEY = "kuali.comment.comment.type.comment.administrative";

    public static final String COMMENT_ACKNOWLEDGE_WORKFLOW_DECISION_TYPE_KEY = "kuali.comment.type.workflowDecisionRationale.acknowledge";
    public static final String COMMENT_APPROVE_WORKFLOW_DECISION_TYPE_KEY = "kuali.comment.type.workflowDecisionRationale.approve";
    public static final String COMMENT_BLANKET_APPROVE_WORKFLOW_DECISION_TYPE_KEY = "kuali.comment.type.workflowDecisionRationale.blanketApprove";
    public static final String COMMENT_CANCEL_WORKFLOW_DECISION_TYPE_KEY = "kuali.comment.type.workflowDecisionRationale.cancelWorkflow";
    public static final String COMMENT_FYI_WORKFLOW_DECISION_TYPE_KEY = "kuali.comment.type.workflowDecisionRationale.fyi";
    public static final String COMMENT_REJECT_WORKFLOW_DECISION_TYPE_KEY = "kuali.comment.type.workflowDecisionRationale.reject";
    public static final String COMMENT_RETURN_WORKFLOW_DECISION_TYPE_KEY = "kuali.comment.type.workflowDecisionRationale.return";
    public static final String COMMENT_WITHDRAW_WORKFLOW_DECISION_TYPE_KEY = "kuali.comment.type.workflowDecisionRationale.withdraw";

    public static enum WORKFLOW_DECISIONS {
        APPROVE(COMMENT_APPROVE_WORKFLOW_DECISION_TYPE_KEY, "Approved"),
        REJECT(COMMENT_REJECT_WORKFLOW_DECISION_TYPE_KEY, "Rejected"),
        RETURN_TO_PREVIOUS(COMMENT_RETURN_WORKFLOW_DECISION_TYPE_KEY, "Sent for Revisions"),
        WITHDRAW(COMMENT_WITHDRAW_WORKFLOW_DECISION_TYPE_KEY, "Withdrawn"),
        ACKNOWLEDGE(COMMENT_ACKNOWLEDGE_WORKFLOW_DECISION_TYPE_KEY, "Acknowledged"),
        FYI(COMMENT_FYI_WORKFLOW_DECISION_TYPE_KEY, "FYI"),
        CANCEL_WORKFLOW(COMMENT_CANCEL_WORKFLOW_DECISION_TYPE_KEY, "Cancelled"),
        BLANKET_APPROVE(COMMENT_BLANKET_APPROVE_WORKFLOW_DECISION_TYPE_KEY, "Blanket Approved")
        ;

        private String type = "";
        private String label = "";



        private WORKFLOW_DECISIONS(String type, String label) {
            this.type = type;
            this.label = label;
        }

        public String getType() {
            return type;
        }

        public String getLabel() {
            return label;
        }

        public static WORKFLOW_DECISIONS getByType(String type) {
            for (WORKFLOW_DECISIONS detail : WORKFLOW_DECISIONS.values()) {
                if (detail.getType().equals(type)) {
                    return detail;
                }
            }
            return null;
        }

    }

    //comment states
    public static final String COMMENT_ACTIVE_STATE_KEY = "kuali.comment.comment.state.active";
    public static final String COMMENT_INACTIVE_STATE_KEY = "kuali.comment.comment.state.inactive";

}
