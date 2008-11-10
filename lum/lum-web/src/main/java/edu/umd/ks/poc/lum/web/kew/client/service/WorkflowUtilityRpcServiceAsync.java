package edu.umd.ks.poc.lum.web.kew.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.umd.ks.poc.lum.lu.dto.CluInfo;

public interface WorkflowUtilityRpcServiceAsync {
//    public void getActionItemsForUser(UuIdDTO userId, AsyncCallback<ActionItemDTO[]> callback);
//    public void getDocumentContent(Long routeHeaderId, AsyncCallback<DocumentContentDTO> callback);
	public void getClusForUser(String user,AsyncCallback<List<CluInfo>> callback);


	public void getUser(AsyncCallback<String> callback);
	public void switchUser(String userId, AsyncCallback<String> callback);
	public void getCluIdForDocument(String docId,AsyncCallback<String> callback);
}
