/**
 * Copyright 2010 The Kuali Foundation Licensed under the
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
 */

package org.kuali.student.core.workflow.ui.client.service;

import java.util.List;

import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.workflow.dto.WorkflowPersonInfo;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpcservices/WorkflowToolRpcService")
public interface WorkflowToolRpcService extends RemoteService{
	
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
    public Boolean isAuthorizedAddReviewer(String docId);
}
