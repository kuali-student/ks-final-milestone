package org.kuali.student.common.ui.client.service;

import org.kuali.student.common.ui.client.service.WorkflowRpcService.RequestType;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BaseDataOrchestrationRpcServiceAsync {
	
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

//	void acknowledgeDocumentWithId(java.lang.String dataId, com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Boolean> arg2);
//	void adhocRequest(java.lang.String docId, java.lang.String recipientPrincipalId, org.kuali.student.common.ui.client.service.WorkflowRpcService.RequestType requestType, java.lang.String annotation, com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Boolean> arg5);
//	void approveDocumentWithData(org.kuali.student.common.assembly.client.Data data, com.google.gwt.user.client.rpc.AsyncCallback<org.kuali.student.common.ui.client.service.DataSaveResult> arg2);
//	void approveDocumentWithId(java.lang.String dataId, com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Boolean> arg2);
//	void disapproveDocumentWithId(java.lang.String dataId, com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Boolean> arg2);
//	void fyiDocumentWithId(java.lang.String dataId, com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Boolean> arg2);
//	void getActionsRequested(java.lang.String dataId, com.google.gwt.user.client.rpc.AsyncCallback<java.lang.String> arg2);
//	void getData(java.lang.String dataId, com.google.gwt.user.client.rpc.AsyncCallback<org.kuali.student.common.assembly.client.Data> arg2);
//	void getDataFromWorkflowId(java.lang.String workflowId, com.google.gwt.user.client.rpc.AsyncCallback<org.kuali.student.common.assembly.client.Data> arg2);
//	void getMetadata(com.google.gwt.user.client.rpc.AsyncCallback<org.kuali.student.common.assembly.client.Metadata> arg1);
//	void getWorkflowIdFromDataId(java.lang.String dataId, com.google.gwt.user.client.rpc.AsyncCallback<java.lang.String> arg2);
//	void saveData(org.kuali.student.common.assembly.client.Data data, com.google.gwt.user.client.rpc.AsyncCallback<org.kuali.student.common.ui.client.service.DataSaveResult> arg2);
//	void submitDocumentWithData(org.kuali.student.common.assembly.client.Data data, com.google.gwt.user.client.rpc.AsyncCallback<org.kuali.student.common.ui.client.service.DataSaveResult> arg2);
//	void submitDocumentWithId(java.lang.String dataId, com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Boolean> arg2);

}
