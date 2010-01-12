package org.kuali.student.common.ui.client.service;

import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.ui.client.service.WorkflowRpcService.RequestType;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BaseDataOrchestrationRpcServiceAsync extends
		BaseRpcServiceAsync {
	
	//Data operations
	public void getData(String dataId, AsyncCallback<Data> callback);
	public void getMetadata(AsyncCallback<Metadata> callback);

	public void saveData(Data data, AsyncCallback<DataSaveResult> callback);
	
	//Workflow Operations
	public void getDataFromWorkflowId(String workflowId, AsyncCallback<Data> callback);
	public void getWorkflowIdFromDataId(String dataId, AsyncCallback<String> callback);

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
