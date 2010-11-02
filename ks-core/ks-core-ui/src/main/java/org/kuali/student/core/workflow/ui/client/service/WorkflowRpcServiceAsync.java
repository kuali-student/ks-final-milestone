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

import org.kuali.student.core.rice.StudentWorkflowConstants.ActionRequestType;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *  Aysnc methods to make workflow rpc service calls. 
 * 
 */
public interface WorkflowRpcServiceAsync {

	public void getWorkflowIdFromDataId(String workflowDocType, String dataId, AsyncCallback<String> callback);
	public void getDataIdFromWorkflowId(String workflowId, AsyncCallback<String> callback);
	public void getDocumentStatus(String workflowId, AsyncCallback<String> callback);
	public void getWorkflowNodes(String workflowId, AsyncCallback<List<String>> callback);

	public void getActionsRequested(String workflowId, AsyncCallback<String> callback);
	
	public void submitDocumentWithId(String workflowId, AsyncCallback<Boolean> callback);
    public void cancelDocumentWithId(String workflowId, AsyncCallback<Boolean> callback);
	public void approveDocumentWithId(String workflowId, AsyncCallback<Boolean> callback);
	public void disapproveDocumentWithId(String workflowId, AsyncCallback<Boolean> callback);
	public void acknowledgeDocumentWithId(String workflowId, AsyncCallback<Boolean> callback);
	public void fyiDocumentWithId(String workflowId, AsyncCallback<Boolean> callback);
	public void withdrawDocumentWithId(String workflowId, AsyncCallback<Boolean> callback);
    public void blanketApproveDocumentWithId(String workflowId, AsyncCallback<Boolean> callback);
    public void returnDocumentWithId(String workflowId, String nodeName, AsyncCallback<Boolean> callback);
    public void getPreviousRouteNodeNames(String workflowId, AsyncCallback<List<String>> callback);
	
	public void adhocRequest(String workflowId, String recipientPrincipalId, ActionRequestType requestType, String annotation, AsyncCallback<Boolean> callback);
	
    public void isAuthorizedAddReviewer(String docId, AsyncCallback<Boolean> callback);
    public void isAuthorizedRemoveReviewers(String docId, AsyncCallback<Boolean> callback);
}
