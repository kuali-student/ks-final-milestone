package org.kuali.student.core.rice;

//TODO: This class needs to be combined with org.kuali.student.StudentWorkflowConstants class found in ks-lum-rice
public class StudentWorkflowConstants {
	
	public static final String DEFAULT_WORKFLOW_DOCUMENT_START_NODE_NAME = "PreRoute";

	public static final String ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAMESPACE = "KS-SYS";
	public static final String ROLE_NAME_ADHOC_EDIT_PERMISSIONS_ROLE_NAME = "Adhoc Permissions: Edit Document";
	public static final String ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAMESPACE = "KS-SYS";
	public static final String ROLE_NAME_ADHOC_ADD_COMMENT_PERMISSIONS_ROLE_NAME = "Adhoc Permissions: Comment on Document";
	
	public enum ActionRequestType {
		COMPLETE("C", "Complete"), 
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
