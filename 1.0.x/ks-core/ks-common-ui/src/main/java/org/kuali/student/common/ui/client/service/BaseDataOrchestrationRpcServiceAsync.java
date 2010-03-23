package org.kuali.student.common.ui.client.service;

import java.util.Map;

import org.kuali.student.common.ui.client.service.AuthorizationRpcService.PermissionType;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BaseDataOrchestrationRpcServiceAsync extends WorkflowRpcServiceAsync{
	
	//Data operations
	public void getData(String dataId, AsyncCallback<Data> callback);
	public void getMetadata(String idType, String id, AsyncCallback<Metadata> callback);

	public void saveData(Data data, AsyncCallback<DataSaveResult> callback);
	
	public void isAuthorized(PermissionType type, Map<String,String> attributes, AsyncCallback<Boolean> callback);

}
