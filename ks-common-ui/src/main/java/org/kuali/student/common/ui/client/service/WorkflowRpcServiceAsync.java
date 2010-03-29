package org.kuali.student.common.ui.client.service;

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

	public void getActionsRequested(String dataId, AsyncCallback<String> callback);
	
	public void submitDocumentWithData(Data data, AsyncCallback<DataSaveResult> callback);
	public void approveDocumentWithData(Data data, AsyncCallback<DataSaveResult> callback);

	public void submitDocumentWithId(String dataId, AsyncCallback<Boolean> callback);
	public void approveDocumentWithId(String dataId, AsyncCallback<Boolean> callback);
	public void disapproveDocumentWithId(String dataId, AsyncCallback<Boolean> callback);
	public void acknowledgeDocumentWithId(String dataId, AsyncCallback<Boolean> callback);
	public void fyiDocumentWithId(String dataId, AsyncCallback<Boolean> callback);
	
	public void adhocRequest(String docId, String recipientPrincipalId, RequestType requestType, String annotation, AsyncCallback<Boolean> callback);
}
