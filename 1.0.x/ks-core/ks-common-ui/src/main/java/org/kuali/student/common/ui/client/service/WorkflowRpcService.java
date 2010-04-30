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

import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.core.assembly.data.Data;

public interface WorkflowRpcService {
	
	public enum RequestType{FYI,ACKNOWLEDGE,APPROVE};
	
	//Workflow Operations
	public Data getDataFromWorkflowId(String workflowId) throws OperationFailedException;
	public String getWorkflowIdFromDataId(String dataId) throws OperationFailedException;
	public String getDocumentStatus(String workflowId) throws OperationFailedException;
	public List<String> getWorkflowNodes(String workflowId) throws OperationFailedException;
	
	public String getActionsRequested(String dataId) throws OperationFailedException;
	
	//These methods should call saveData first and update the doc content with the data content
	public DataSaveResult submitDocumentWithData(Data data) throws OperationFailedException;
	public DataSaveResult approveDocumentWithData(Data data) throws OperationFailedException;

	//These methods just call the actions
	public Boolean submitDocumentWithId(String dataId) throws OperationFailedException;
	public Boolean approveDocumentWithId(String dataId) throws OperationFailedException;
	public Boolean disapproveDocumentWithId(String dataId) throws OperationFailedException;
	public Boolean acknowledgeDocumentWithId(String dataId) throws OperationFailedException;
	public Boolean fyiDocumentWithId(String dataId) throws OperationFailedException;
	public Boolean withdrawDocumentWithId(String dataId) throws OperationFailedException;
	
	public Boolean adhocRequest(String docId, String recipientPrincipalId, RequestType requestType, String annotation) throws OperationFailedException;
}
