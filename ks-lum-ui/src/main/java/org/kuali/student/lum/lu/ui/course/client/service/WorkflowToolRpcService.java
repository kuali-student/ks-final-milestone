package org.kuali.student.lum.lu.ui.course.client.service;

import java.util.List;

import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcService;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.lum.lu.dto.workflow.WorkflowPersonInfo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpcservices/WorkflowToolRpcService")
public interface WorkflowToolRpcService extends BaseDataOrchestrationRpcService{
	
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
	
	public Boolean addCollaborator(String docId, String dataId, String dataTitle, String recipientPrincipalId, String selectedPermission, String actionRequestTypeCode, boolean participationRequired, String respondBy) throws OperationFailedException;
    public List<WorkflowPersonInfo> getCollaborators(String docId) throws OperationFailedException;
    public Metadata getMetadata(String idType, String id) throws OperationFailedException;
}
