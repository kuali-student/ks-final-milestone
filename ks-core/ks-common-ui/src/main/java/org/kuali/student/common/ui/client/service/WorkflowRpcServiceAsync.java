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

package org.kuali.student.common.ui.client.service;

import java.util.List;

import org.kuali.student.common.ui.client.service.WorkflowRpcService.RequestType;
import org.kuali.student.core.assembly.data.Data;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 *  Aysnc methods to make workflow rpc service calls. 
 * 
 */
public interface WorkflowRpcServiceAsync {

	public void getDataFromWorkflowId(String workflowId, AsyncCallback<Data> callback);
	public void getWorkflowIdFromDataId(String dataId, AsyncCallback<String> callback);
	public void getDocumentStatus(String workflowId, AsyncCallback<String> callback);
	public void getWorkflowNodes(String workflowId, AsyncCallback<List<String>> callback);

	public void getActionsRequested(String dataId, AsyncCallback<String> callback);
	
	public void submitDocumentWithData(Data data, AsyncCallback<DataSaveResult> callback);
	public void approveDocumentWithData(Data data, AsyncCallback<DataSaveResult> callback);

	public void submitDocumentWithId(String dataId, AsyncCallback<Boolean> callback);
	public void approveDocumentWithId(String dataId, AsyncCallback<Boolean> callback);
	public void disapproveDocumentWithId(String dataId, AsyncCallback<Boolean> callback);
	public void acknowledgeDocumentWithId(String dataId, AsyncCallback<Boolean> callback);
	public void fyiDocumentWithId(String dataId, AsyncCallback<Boolean> callback);
	public void withdrawDocumentWithId(String dataId, AsyncCallback<Boolean> callback);
	
	public void adhocRequest(String docId, String recipientPrincipalId, RequestType requestType, String annotation, AsyncCallback<Boolean> callback);
}
